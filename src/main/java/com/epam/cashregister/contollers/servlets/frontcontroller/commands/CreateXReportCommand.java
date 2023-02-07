package com.epam.cashregister.contollers.servlets.frontcontroller.commands;

import com.epam.cashregister.contollers.servlets.frontcontroller.FrontCommand;
import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.dao.impl.ReportDaoImpl;
import com.epam.cashregister.services.reportingservice.Report;
import com.epam.cashregister.services.utils.AjaxResponseWriter;
import com.itextpdf.text.DocumentException;

import javax.servlet.ServletException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Map;

/**
 The CreateXReportCommand class is a subclass of FrontCommand that is responsible for creating X report and storing it in the backup folder.
 X report - An X Report is a detailed history of the transactions for a point of sale register up until that time.
 */
public class CreateXReportCommand extends FrontCommand {

    @Override
    public void process() throws ServletException, IOException {

        OrderBean[] incomeBeans = new ReportDaoImpl().getIncomeGoods();
        OrderBean[] outcomeBeans = new ReportDaoImpl().getOutcomeGoods();
        OrderBean[] scrappedBeans = new ReportDaoImpl().getScrappedGoods();


        if (incomeBeans != null && outcomeBeans != null && scrappedBeans != null) {

            Report xReport = new Report(Report.Type.X, new Timestamp(System.currentTimeMillis()).toString(), incomeBeans, outcomeBeans, scrappedBeans);
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
                } catch (Exception e) {
                    logger.warn("Failed to send x-report");
                    AjaxResponseWriter.write(response, 400, Map.of("status", "Failed to send x-report."));
                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.warn("Failed to create x-report");
                AjaxResponseWriter.write(response, 400, Map.of("status", "Something went wrong..."));
            }

        } else {
            logger.warn("Failed to get goods");
            AjaxResponseWriter.write(response, 400, Map.of("status", "Something went wrong..."));
        }

    }

}
