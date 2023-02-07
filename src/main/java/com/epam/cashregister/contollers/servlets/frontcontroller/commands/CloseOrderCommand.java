package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.GoodBean;
import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.entities.ReceiptGoodBean;
import com.epam.cashregister.entities.UserBean;
import com.epam.cashregister.services.EmailSenderService;
import com.epam.cashregister.services.dao.impl.OrderDaoImpl;
import com.epam.cashregister.services.exceptions.ValidatorException;
import com.epam.cashregister.services.reportingservice.Receipt;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.epam.cashregister.services.validateservice.ValidatorBuilder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 The CloseOrderCommand class is a subclass of FrontCommand that is responsible for closing the order.
 */
public class CloseOrderCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        int orderId = -1;
        try { orderId = Integer.parseInt(request.getParameter("orderId")); }
        catch (Exception ignore) { }

        String email = request.getParameter("email");

        OrderBean orderBean = new OrderBean();
        orderBean.setOrderId(orderId);

        try {
            new ValidatorBuilder().validateOrderId(orderId);

            OrderBean[] orderBeans = new OrderDaoImpl().getOrderedGoods(orderBean);

            if (orderBeans.length >= 1) {
                String[] goodCodes = new String[orderBeans.length];
                for (int i = 0; i < orderBeans.length; i++) goodCodes[i] = orderBeans[i].getGoodCode();
                OrderBean[] orderBeans1 = new OrderDaoImpl().getSumOfOrderedGoods(goodCodes);
                HashMap<String, Float> orderedHashMap = new HashMap<>();
                HashMap<String, Float> warehouseHashMap = new HashMap<>();

                for (OrderBean orderBean1 : orderBeans) orderedHashMap.put(orderBean1.getGoodCode(), orderBean1.getQuantity());
                for (OrderBean orderBean1 : orderBeans1) warehouseHashMap.put(orderBean1.getGoodCode(), orderBean1.getQuantity());

                String error = null;
                for (Map.Entry<String, Float> set : orderedHashMap.entrySet()) {
                    if (set.getValue() > warehouseHashMap.get(set.getKey())) {
                        error = "Attempt to buy more good than what is in warehouse.\ngood code: " + set.getKey() + "\nquantity in stock: " + warehouseHashMap.get(set.getKey()) + "\nattempt quantity: " + set.getValue();
                        break;
                    }
                }
                if (error == null) {
                    if (new OrderDaoImpl().closeOrder(orderBeans)) {
                        Receipt receipt = new Receipt();
                        UserBean user = (UserBean) request.getSession().getAttribute("USER");

                        OrderBean[] orderBeans2 = new OrderDaoImpl().getOrderedGoods(orderBean);

                        receipt.setOrderId(orderId);
                        receipt.setCashier(user.getFirstName() + " " + user.getLastName());
                        receipt.setDateTime(String.valueOf(new Timestamp(System.currentTimeMillis())));
                        receipt.setGoods(orderBeans2);

                        try {
                            receipt.createCheck();

                            response.setContentType("application/pdf");
                            response.setHeader("Content-Disposition", "attachment; filename=\"test.pdf\"");

                            File filePDF = new File(receipt.getPath());
                            byte[] inFileBytes = Files.readAllBytes(Paths.get(filePDF.toURI()));
                            byte[] encoded = java.util.Base64.getEncoder().encode(inFileBytes);

                            //SEND EMAIL
                            if (email.length() != 0) {
                                EmailSenderService emailSendService = EmailSenderService.getInstance();
                                emailSendService.sendReceipt(email, receipt.getPath(), "receipt.pdf");
                            }

                            try (OutputStream os = java.util.Base64.getEncoder().wrap(response.getOutputStream());
                                 FileInputStream fis = new FileInputStream(filePDF)) {
                                 byte[] bytes = new byte[1024];
                                 int read;
                                 while ((read = fis.read(bytes)) > -1) {
                                     os.write(bytes, 0, read);
                                 }
                                 os.flush();
                            } catch (Exception e) {
                                logger.warn("Failed to create receipt");
                                AjaxResponseWriter.write(response, 400, Map.of("status", "Failed to create receipt."));
                            }

                            //logger.warn("Receipt created successfully");
                            //AjaxResponseWriter.write(response, 200, Map.of("status", "Order closed successfully."));
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.warn("Failed to create receipt");
                            AjaxResponseWriter.write(response, 400, Map.of("status", "Failed to create receipt."));
                        }

                    } else {
                        logger.warn("Failed to close order.");
                        AjaxResponseWriter.write(response, 400, Map.of("status", "Something went wrong..."));
                    }
                } else {
                    logger.warn("Failed to close order (Attempt to buy more good than what is in warehouse).");
                    AjaxResponseWriter.write(response, 400, Map.of("status", error));
                }
            } else {
                logger.warn("Failed to close order (zero goods in the order).");
                AjaxResponseWriter.write(response, 400, Map.of("status", "You can't close an order that has no goods."));
            }

        } catch (ValidatorException e) {
            logger.warn(e);
            AjaxResponseWriter.write(response, 400, Map.of("status", e.getMessage()));
        }
    }

}
