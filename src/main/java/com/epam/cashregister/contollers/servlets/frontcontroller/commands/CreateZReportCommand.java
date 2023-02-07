package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.dao.impl.OrderDaoImpl;
import com.epam.cashregister.services.dao.impl.ReportDaoImpl;
import com.epam.cashregister.services.reportingservice.Report;
import com.epam.cashregister.services.utils.AjaxResponseWriter;

import javax.servlet.ServletException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Map;

/**
 The CreateZReportCommand class is a subclass of FrontCommand that is responsible for creating Z report and storing it in the backup folder.
 Z report - the Z-Report is essentially the “start-over” report. It's the last thing you'll review to end the day in order to begin the next day with a fresh set of zeroes.
 */
public class CreateZReportCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {
        OrderBean[] orderBeans = new OrderDaoImpl().getOrdersWhichAreInProcess();
        if (orderBeans != null) {
            if (orderBeans.length == 0) {

                OrderBean[] incomeBeans = new ReportDaoImpl().getIncomeGoods();
                OrderBean[] outcomeBeans = new ReportDaoImpl().getOutcomeGoods();
                OrderBean[] scrappedBeans = new ReportDaoImpl().getScrappedGoods();


                if (incomeBeans != null && outcomeBeans != null && scrappedBeans != null) {

                    Report xReport = new Report(Report.Type.Z, new Timestamp(System.currentTimeMillis()).toString(), incomeBeans, outcomeBeans, scrappedBeans);
                    try {
                        xReport.createReport();

                        try (OutputStream os = java.util.Base64.getEncoder().wrap(response.getOutputStream());
                             FileInputStream fis = new FileInputStream(xReport.getPath())) {
                             byte[] bytes = new byte[1024];
                             int read;
                             while ((read = fis.read(bytes)) > -1) {
                                 os.write(bytes, 0, read);
                             }
                             os.flush();

                            if (new ReportDaoImpl().closeDay()) {
                                logger.debug("Day closed successfully");
                            } else {
                                logger.warn("Failed to close day");
                            }

                        } catch (Exception e) {
                            logger.warn("Failed to send z-report");
                            AjaxResponseWriter.write(response, 400, Map.of("status", "Failed to send z-report."));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.warn("Failed to create z-report");
                        AjaxResponseWriter.write(response, 400, Map.of("status", "Something went wrong..."));
                    }

                } else {
                    logger.warn("Failed to get goods");
                    AjaxResponseWriter.write(response, 400, Map.of("status", "Something went wrong..."));
                }

            } else {
                logger.warn("Attempt to create z-report with unclosed orders");
                AjaxResponseWriter.write(response, 400, Map.of("status", "Cannot create z-report, there are pending orders."));
            }
        } else {
            logger.warn("Failed to get orders which are in process");
            AjaxResponseWriter.write(response, 400, Map.of("status", "Something went wrong..."));
        }
    }
}
