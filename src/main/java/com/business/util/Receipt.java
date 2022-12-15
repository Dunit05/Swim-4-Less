// Tommy
// Dec 13, 2022
// Swim 4 Less App

package com.business.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;

import com.business.pool.SwimmingPool;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.WebColors;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border3D;
import com.itextpdf.layout.borders.DottedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

public class Receipt {

    // Constants for receipt
    public static final float COL = 300f, DOUBLE_COL = 600f, TWO_COL = 350f, SEC_TWO_COL = 150f, THREE_COL = 190f;
    public static final int MAX_ID = 1000000, ONE_HUNDRED = 100, BORDER_SIZE = 1, FONT_SIZE = 10,
            IMG_WIDTH = 190,
            IMG_HEIGHT = 100, TAX_RATE = 13, CONTRACTOR_RATE = 20;
    public static int receiptNum = 0;

    // Prints the receipt
    public void print(ArrayList<SwimmingPool> pools, String repName, boolean isContractor, double totalCost,
            String[] customerInfo) {
        // Formatting for receipt columns
        float[] twoColWidth = { TWO_COL, SEC_TWO_COL };
        float[] twoColWidthForInfo = { COL, COL };
        float[] threeColWidth = { THREE_COL, THREE_COL, THREE_COL };
        float[] itemsColWidth = { COL, DOUBLE_COL, COL };

        // Try catch to create the receipt
        try {
            // Generate receipt number
            receiptNum();
            // File setup
            FileOutputStream fos = new FileOutputStream(receiptNum + ".pdf");
            PdfWriter writer = new PdfWriter(fos);
            PdfDocument pdf = new PdfDocument(writer);
            pdf.setDefaultPageSize(PageSize.A4);
            Document document = new Document(pdf);

            // Receipt tebles and lines and space objects
            Table receiptInfoTable = new Table(twoColWidth);
            Table nestedReciptInfoTable = new Table(twoColWidth);
            Table informationTable = new Table(twoColWidthForInfo);
            Table nestedInfoTo = new Table(twoColWidthForInfo);
            Table nestedInfoFrom = new Table(twoColWidthForInfo);
            Table items = new Table(itemsColWidth);
            Table nestedItems = new Table(itemsColWidth);
            Table paymentInfo = new Table(itemsColWidth);
            Table line1 = new Table(threeColWidth);
            Table line2 = new Table(threeColWidth);
            Paragraph space = new Paragraph("\n");
            SolidBorder sb = new SolidBorder(BORDER_SIZE);
            line1.setBorder(sb);
            DottedBorder db = new DottedBorder(BORDER_SIZE);
            line2.setBorder(db);

            // Get logo
            Image image = new Image(ImageDataFactory.create("logo.png"));
            image.scaleAbsolute(IMG_WIDTH, IMG_HEIGHT);
            receiptInfoTable.addCell(new Cell().add(image).setBorder(Border3D.NO_BORDER));

            // Start adding the receipt information
            nestedReciptInfoTable.addCell(documentText("\nInvoice No.:"));
            nestedReciptInfoTable.addCell(documentText("\n" + receiptNum));
            nestedReciptInfoTable.addCell(documentText("Date:"));
            nestedReciptInfoTable.addCell(documentText(getDate()));
            nestedReciptInfoTable.addCell(documentText("Sales Rep:"));
            nestedReciptInfoTable.addCell(documentText(repName));
            nestedReciptInfoTable.addCell(documentText("Contractor:"));
            nestedReciptInfoTable.addCell(documentText(isContractor ? "Yes" : "No"));

            receiptInfoTable.addCell(new Cell().add(nestedReciptInfoTable).setBorder(Border3D.NO_BORDER));

            informationTable.addCell(documentText("Receipt To:"));
            informationTable.addCell(documentText("Receipt From:"));

            nestedInfoTo.addCell(documentText("First Name:"));
            nestedInfoTo.addCell(documentText(customerInfo[0]));
            nestedInfoTo.addCell(documentText("Last Name:"));
            nestedInfoTo.addCell(documentText(customerInfo[1]));
            nestedInfoTo.addCell(documentText("Phone:"));
            nestedInfoTo.addCell(documentText(customerInfo[2]));
            nestedInfoTo.addCell(documentText("Email:"));
            nestedInfoTo.addCell(documentText(customerInfo[3]));

            nestedInfoFrom.addCell(documentText("Name:"));
            nestedInfoFrom.addCell(documentText("Swim 4 Less"));
            nestedInfoFrom.addCell(documentText("Email:"));
            nestedInfoFrom.addCell(documentText("info@swim4less.ca"));
            nestedInfoFrom.addCell(documentText("Delivery Method:"));
            nestedInfoFrom.addCell(documentText("Pickup"));

            informationTable.addCell(new Cell().add(nestedInfoTo).setBorder(Border3D.NO_BORDER));
            informationTable.addCell(new Cell().add(nestedInfoFrom).setBorder(Border3D.NO_BORDER));

            // Add the item column headers
            items.setBackgroundColor(WebColors.getRGBColor("#CFD2CF"), 1f);
            items.addCell(documentText("Pool ID#"));
            items.addCell(documentText("Description"));
            items.addCell(documentText("Price $"));

            // loop through the pools and add them to the receipt
            for (SwimmingPool pool : pools) {
                nestedItems.addCell(documentText(parse(pool.getPoolId())));
                nestedItems.addCell(documentText("" + pool + "\nCost of pool: $" + pool.getPoolCost()
                        + "\nAmount of concrete: " + pool.getConcreteArea() + " sq. ft.\nPrice: $"
                        + pool.getConcreteCost() + "\nLength of fence: " + pool.getFenceLength()
                        + " linear ft.\nPrice: $"
                        + pool.getFenceCost()));
                nestedItems.addCell(documentText("" + pool.getTotalCost()));
            }

            // Adding the final transaction information
            paymentInfo.addCell(documentText(""));
            paymentInfo.addCell(documentText("Total Quantity:"));

            paymentInfo.addCell(
                    documentText("" + pools.size() + (pools.size() > 1 ? " pools" : " pool")));
            paymentInfo.addCell(documentText(""));
            paymentInfo.addCell(documentText("Discount Rate:"));
            if (isContractor && pools.size() > 3) {
                paymentInfo.addCell(documentText("-" + CONTRACTOR_RATE + "%"));
            } else {
                paymentInfo.addCell(documentText("-0%"));
            }
            paymentInfo.addCell(documentText(""));
            paymentInfo.addCell(documentText("Tax:"));
            paymentInfo.addCell(documentText(TAX_RATE + "%"));
            paymentInfo.addCell(documentText(""));
            paymentInfo.addCell(documentText("Total:"));
            paymentInfo.addCell(documentText("$" + totalCost));

            // Add all the tables to the document
            document.add(receiptInfoTable);
            document.add(space);
            document.add(line1);
            document.add(space);
            document.add(informationTable);
            document.add(space);
            document.add(line2);
            document.add(space);
            document.add(items);
            document.add(nestedItems);
            document.add(space);
            document.add(line2);
            document.add(space);
            document.add(paymentInfo);
            document.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (MalformedURLException e) {
            System.out.println("Malformed URL");
        }

    }

    // Generate a random receipt number
    public static int receiptNum() {
        receiptNum = (int) (Math.random() * MAX_ID) + ONE_HUNDRED;
        return receiptNum;
    }

    // Get the current date
    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        return month + "/" + day + "/" + year;
    }

    // Create a cell with the text
    public static Cell documentText(String text) {
        Cell cell = new Cell().add(new Paragraph(text)).setBorder(Border3D.NO_BORDER).setFontSize(FONT_SIZE).setBold();
        return cell;
    }

    // Convert an int to a string
    public static String parse(int value) {
        try {
            return Integer.toString(value);
        } catch (NumberFormatException e) {
            return "0";
        }
    }
}
