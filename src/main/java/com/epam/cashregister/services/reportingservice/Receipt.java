package com.epam.cashregister.services.reportingservice;

import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.utils.GraphicNumber;
import com.epam.cashregister.services.utils.PropertiesManager;
import com.epam.cashregister.services.utils.ResourcesUtil;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Receipt {

    private final Document receipt;
    private int orderId;
    private String dateTime;
    private String cashier;
    private OrderBean[] goods;
    private String path;

    public Receipt() {
        this.receipt = new Document();
    }

    public Receipt(int orderId, String dateTime, String cashier, OrderBean[] goods) {
        this();
        this.orderId = orderId;
        this.dateTime = dateTime;
        this.cashier = cashier;
        this.goods = goods;
    }


    public void createCheck() throws DocumentException, IOException {
        path = PropertiesManager.getPropertyFile("report.properties").getProperty("receipt.storage.dir") + dateTime.replaceAll(":", "_") + ".pdf";
        PdfWriter.getInstance(receipt, new FileOutputStream(path));
        receipt.open();

        BaseFont bf = BaseFont.createFont(ResourcesUtil.getFileFromResources("/fonts/arial.ttf").getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font rusFont = new Font(bf,10,Font.NORMAL);
        Font font = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);

        Paragraph receiptIdTitle = new Paragraph("RECEIPT ID:", font);
        receiptIdTitle.setSpacingAfter(10);
        receipt.add(receiptIdTitle);

        Paragraph receiptId = new Paragraph(GraphicNumber.getInstance().render(orderId), font);
        receiptId.setSpacingAfter(32);
        receipt.add(receiptId);

        Paragraph receiptDateTitle = new Paragraph("DATE:", font);
        receiptDateTitle.setSpacingAfter(10);
        receipt.add(receiptDateTitle);

        Paragraph receiptDate = new Paragraph(dateTime, font);
        receiptDate.setSpacingAfter(32);
        receipt.add(receiptDate);

        Paragraph receiptCashierTitle = new Paragraph("CASHIER:", font);
        receiptCashierTitle.setSpacingAfter(10);
        receipt.add(receiptCashierTitle);

        Paragraph receiptCashier = new Paragraph(cashier, rusFont);
        receiptCashier.setSpacingAfter(32);
        receipt.add(receiptCashier);

        Paragraph receiptGoodsTitle = new Paragraph("-------GOODS-------", font);
        receiptGoodsTitle.setSpacingAfter(32);
        receipt.add(receiptGoodsTitle);

        float totalPrice = 0f;
        for (OrderBean orderBean : goods) {
            totalPrice += orderBean.getQuantity() * orderBean.getGoodPrice();

            Paragraph goodCodeTitle = new Paragraph("CODE:", font);
            goodCodeTitle.setSpacingAfter(5);
            receipt.add(goodCodeTitle);

            Paragraph goodCode = new Paragraph(orderBean.getGoodCode(), rusFont);
            goodCode.setSpacingAfter(10);
            receipt.add(goodCode);

            Paragraph goodTitleTitle = new Paragraph("TITLE:", font);
            goodTitleTitle.setSpacingAfter(5);
            receipt.add(goodTitleTitle);

            Paragraph goodTitle = new Paragraph(orderBean.getGoodTitle(), rusFont);
            goodTitle.setSpacingAfter(10);
            receipt.add(goodTitle);

            Paragraph goodPriceTitle = new Paragraph("PRICE:", font);
            goodPriceTitle.setSpacingAfter(5);
            receipt.add(goodPriceTitle);

            Paragraph goodPrice = new Paragraph(orderBean.getGoodPrice() + " uah", rusFont);
            goodPrice.setSpacingAfter(10);
            receipt.add(goodPrice);

            Paragraph goodQuantityTitle = new Paragraph("QUANTITY:", font);
            goodQuantityTitle.setSpacingAfter(5);
            receipt.add(goodQuantityTitle);

            Paragraph goodQuantity = new Paragraph("x" + orderBean.getQuantity(), rusFont);
            goodQuantity.setSpacingAfter(32);
            receipt.add(goodQuantity);
        }

        Paragraph receiptTotalPriceTitle = new Paragraph("TOTAL:", font);
        receiptTotalPriceTitle.setSpacingAfter(10);
        receipt.add(receiptTotalPriceTitle);

        Paragraph receiptTotalPrice = new Paragraph(totalPrice + " uah", rusFont);
        receiptTotalPrice.setSpacingAfter(10);
        receipt.add(receiptTotalPrice);

        receipt.close();
    }

    public Document getCheck() {
        return receipt;
    }

    public String getPath() {
        return path;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setCashier(String cashier) {
        this.cashier = cashier;
    }

    public void setGoods(OrderBean[] goods) {
        this.goods = goods;
    }

    public static void main(String[] args) throws DocumentException, IOException {
        Receipt receipt = new Receipt();
        receipt.setOrderId(123);
        receipt.setCashier("John Smite");
        receipt.setDateTime("2017/11/06 12:11:58");

        OrderBean[] receiptGoodBeans = new OrderBean[3];

        OrderBean receiptGoodBean_1 = new OrderBean();
        receiptGoodBean_1.setGoodCode("00000001");
        receiptGoodBean_1.setGoodTitle("Samsung S+ Ultra 200GB");
        receiptGoodBean_1.setGoodPrice(25_000f);
        receiptGoodBean_1.setQuantity(5f);

        OrderBean receiptGoodBean_2 = new OrderBean();
        receiptGoodBean_2.setGoodCode("00000002");
        receiptGoodBean_2.setGoodTitle("Iphone 11 128Gb min");
        receiptGoodBean_2.setGoodPrice(40_000f);
        receiptGoodBean_2.setQuantity(2f);

        OrderBean receiptGoodBean_3 = new OrderBean();
        receiptGoodBean_3.setGoodCode("00000003");
        receiptGoodBean_3.setGoodTitle("Xiaomi Red-Mi Note-Pade");
        receiptGoodBean_3.setGoodPrice(30_000f);
        receiptGoodBean_3.setQuantity(1f);

        receiptGoodBeans[0] = receiptGoodBean_1;
        receiptGoodBeans[1] = receiptGoodBean_2;
        receiptGoodBeans[2] = receiptGoodBean_3;

        receipt.setGoods(receiptGoodBeans);

        receipt.createCheck();
    }
}
