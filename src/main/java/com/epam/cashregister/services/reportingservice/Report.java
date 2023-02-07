package com.epam.cashregister.services.reportingservice;

import com.epam.cashregister.entities.OrderBean;
import com.epam.cashregister.services.utils.PropertiesManager;
import com.epam.cashregister.services.utils.ResourcesUtil;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Report {

    public enum Type {
        X, Z
    }

    private Document document;
    private String path;
    private Type type;
    private String dateTime;
    private OrderBean[] income;
    private OrderBean[] outcome;
    private OrderBean[] scrapped;

    public Report(Type type) {
        this.type = type;
    }

    public Report(Type type, String dateTime, OrderBean[] income, OrderBean[] outcome, OrderBean[] scrapped) {
        this(type);
        this.dateTime = dateTime;
        this.income = income;
        this.outcome = outcome;
        this.scrapped = scrapped;
    }

    public void createReport() throws IOException, DocumentException {

        //Get path
        path = PropertiesManager.getPropertyFile("report.properties").getProperty( (type == Type.X ? "x" : "z") + ".reports.dir") + dateTime.replaceAll(":", "_") + ".pdf";
        //Create Document instance.
        Document document = new Document();
        //Create OutputStream instance.
        OutputStream outputStream = new FileOutputStream(path);
        //Create PDFWriter instance.
        PdfWriter.getInstance(document, outputStream);
        //Open the document.
        document.open();


        BaseFont bf = BaseFont.createFont(ResourcesUtil.getFileFromResources("/fonts/arial.ttf").getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font rusFont = new Font(bf,10,Font.NORMAL);
        Font font = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);

        Paragraph title = new Paragraph( (type == Type.X ? "X" : "Z") + "-REPORT", font);
        title.setSpacingAfter(32);
        document.add(title);

        Paragraph dateTitle = new Paragraph("DATE:", font);
        dateTitle.setSpacingAfter(10);
        document.add(dateTitle);

        Paragraph date = new Paragraph(dateTime, font);
        date.setSpacingAfter(32);
        document.add(date);

        Paragraph incomeTitle = new Paragraph("INCOME:", font);
        incomeTitle.setSpacingAfter(10);
        document.add(incomeTitle);

        //Create Table object, Here 5 specify the no. of columns
        PdfPTable incomeTable = new PdfPTable(5);

        incomeTable.addCell(new PdfPCell(new Paragraph("GOOD CODE", rusFont)));
        incomeTable.addCell(new PdfPCell(new Paragraph("TITLE", rusFont)));
        incomeTable.addCell(new PdfPCell(new Paragraph("QUANTITY", rusFont)));
        incomeTable.addCell(new PdfPCell(new Paragraph("STORAGE ADDRESS", rusFont)));
        incomeTable.addCell(new PdfPCell(new Paragraph("DATE-TIME", rusFont)));

        float incomeTotalPrice = 0f;
        for (OrderBean incomeBean : income) {
            incomeTotalPrice += incomeBean.getGoodPrice() * incomeBean.getQuantity();

            incomeTable.addCell(new PdfPCell(new Paragraph(incomeBean.getGoodCode(), rusFont)));
            incomeTable.addCell(new PdfPCell(new Paragraph(incomeBean.getGoodTitle(), rusFont)));
            incomeTable.addCell(new PdfPCell(new Paragraph(String.valueOf(incomeBean.getQuantity()), rusFont)));
            incomeTable.addCell(new PdfPCell(new Paragraph(incomeBean.getStorageAddress(), rusFont)));
            incomeTable.addCell(new PdfPCell(new Paragraph(incomeBean.getDateTime(), rusFont)));
        }

        document.add(incomeTable);

        Paragraph outcomeTitle = new Paragraph("OUTCOME:", font);
        outcomeTitle.setSpacingAfter(10);
        document.add(outcomeTitle);

        //Create Table object, Here 5 specify the no. of columns
        PdfPTable outcomeTable = new PdfPTable(5);

        outcomeTable.addCell(new PdfPCell(new Paragraph("GOOD CODE", rusFont)));
        outcomeTable.addCell(new PdfPCell(new Paragraph("TITLE", rusFont)));
        outcomeTable.addCell(new PdfPCell(new Paragraph("QUANTITY", rusFont)));
        outcomeTable.addCell(new PdfPCell(new Paragraph("STORAGE ADDRESS", rusFont)));
        outcomeTable.addCell(new PdfPCell(new Paragraph("DATE-TIME", rusFont)));

        float outcomeTotalPrice = 0f;
        for (OrderBean outcomeBean : outcome) {
            outcomeTotalPrice += outcomeBean.getGoodPrice() * outcomeBean.getQuantity();

            outcomeTable.addCell(new PdfPCell(new Paragraph(outcomeBean.getGoodCode(), rusFont)));
            outcomeTable.addCell(new PdfPCell(new Paragraph(outcomeBean.getGoodTitle(), rusFont)));
            outcomeTable.addCell(new PdfPCell(new Paragraph(String.valueOf(outcomeBean.getQuantity()), rusFont)));
            outcomeTable.addCell(new PdfPCell(new Paragraph(outcomeBean.getStorageAddress(), rusFont)));
            outcomeTable.addCell(new PdfPCell(new Paragraph(outcomeBean.getDateTime(), rusFont)));
        }

        document.add(outcomeTable);

        Paragraph scrappedTitle = new Paragraph("SCRAPPED:", font);
        scrappedTitle.setSpacingAfter(10);
        document.add(scrappedTitle);

        //Create Table object, Here 5 specify the no. of columns
        PdfPTable scrappedTable = new PdfPTable(5);

        scrappedTable.addCell(new PdfPCell(new Paragraph("GOOD CODE", rusFont)));
        scrappedTable.addCell(new PdfPCell(new Paragraph("TITLE", rusFont)));
        scrappedTable.addCell(new PdfPCell(new Paragraph("QUANTITY", rusFont)));
        scrappedTable.addCell(new PdfPCell(new Paragraph("STORAGE ADDRESS", rusFont)));
        scrappedTable.addCell(new PdfPCell(new Paragraph("DATE-TIME", rusFont)));

        float scrappedTotalPrice = 0f;
        for (OrderBean scrappedBean : scrapped) {
            scrappedTotalPrice += scrappedBean.getGoodPrice() * scrappedBean.getQuantity();

            scrappedTable.addCell(new PdfPCell(new Paragraph(scrappedBean.getGoodCode(), rusFont)));
            scrappedTable.addCell(new PdfPCell(new Paragraph(scrappedBean.getGoodTitle(), rusFont)));
            scrappedTable.addCell(new PdfPCell(new Paragraph(String.valueOf(scrappedBean.getQuantity()), rusFont)));
            scrappedTable.addCell(new PdfPCell(new Paragraph(scrappedBean.getStorageAddress(), rusFont)));
            scrappedTable.addCell(new PdfPCell(new Paragraph(scrappedBean.getDateTime(), rusFont)));
        }

        document.add(scrappedTable);

        Paragraph totalIncomePriceTitle = new Paragraph("TOTAL INCOME PRICE:", font);
        totalIncomePriceTitle.setSpacingAfter(10);
        document.add(totalIncomePriceTitle);

        Paragraph totalIncomePrice = new Paragraph(String.valueOf(incomeTotalPrice), rusFont);
        totalIncomePrice.setSpacingAfter(20);
        document.add(totalIncomePrice);


        Paragraph totalOutcomePriceTitle = new Paragraph("TOTAL OUTCOME PRICE:", font);
        totalOutcomePriceTitle.setSpacingAfter(10);
        document.add(totalOutcomePriceTitle);

        Paragraph totalOutcomePrice = new Paragraph(String.valueOf(outcomeTotalPrice), rusFont);
        totalOutcomePrice.setSpacingAfter(20);
        document.add(totalOutcomePrice);


        Paragraph totalScrappedPriceTitle = new Paragraph("TOTAL SCRAPPED PRICE:", font);
        totalScrappedPriceTitle.setSpacingAfter(10);
        document.add(totalScrappedPriceTitle);

        Paragraph totalScrappedPrice = new Paragraph(String.valueOf(scrappedTotalPrice), rusFont);
        totalScrappedPrice.setSpacingAfter(20);
        document.add(totalScrappedPrice);


        document.close();
    }

    public Type getType() {
        return type;
    }

    public String getDateTime() {
        return dateTime;
    }

    public OrderBean[] getIncome() {
        return income;
    }

    public OrderBean[] getOutcome() {
        return outcome;
    }

    public OrderBean[] getScrapped() {
        return scrapped;
    }

    public String getPath() {
        return path;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public void setIncome(OrderBean[] income) {
        this.income = income;
    }

    public void setOutcome(OrderBean[] outcome) {
        this.outcome = outcome;
    }

    public void setScrapped(OrderBean[] scrapped) {
        this.scrapped = scrapped;
    }
}
