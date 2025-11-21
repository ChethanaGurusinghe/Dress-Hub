package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import model.dto.OrderDetail;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class InvoiceGenerator {

    public static void generateInvoice(String invoiceNo, String orderId, List<OrderDetail> cartList, double totalAmount) {

        Document doc = new Document(PageSize.A4, 40, 40, 40, 40);

        try {
            // Downloads folder
            String userHome = System.getProperty("user.home");
            File downloadsDir = new File(userHome + "/Downloads");
            if (!downloadsDir.exists()) downloadsDir.mkdir();

            String filePath = downloadsDir.getAbsolutePath() + "/Invoice_" + invoiceNo + ".pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(filePath));
            doc.open();

            // Logo
            try {
                Image logo = Image.getInstance("src/main/resources/img/logo.png"); // adjust path
                logo.scaleToFit(84, 70);
                logo.setAlignment(Element.ALIGN_CENTER);
                doc.add(logo);
            } catch (Exception e) {
                System.out.println("Logo not found: " + e.getMessage());
            }

            // Company Info
            Font companyFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD, BaseColor.ORANGE);
            Paragraph companyName = new Paragraph("DRESSHUB", companyFont);
            companyName.setAlignment(Element.ALIGN_CENTER);
            doc.add(companyName);

            Font infoFont = new Font(Font.FontFamily.HELVETICA, 12);
            Paragraph address = new Paragraph("No.123, Main Rd, Colombo", infoFont);
            address.setAlignment(Element.ALIGN_CENTER);
            doc.add(address);

            Paragraph tel = new Paragraph("Tel : 077 1234567", infoFont);
            tel.setAlignment(Element.ALIGN_CENTER);
            doc.add(tel);

            doc.add(Chunk.NEWLINE);

            // Bill Info
            PdfPTable infoTable = new PdfPTable(2);
            infoTable.setWidthPercentage(60);
            infoTable.setHorizontalAlignment(Element.ALIGN_LEFT);
            infoTable.setSpacingBefore(10f);
            infoTable.setSpacingAfter(10f);

            infoTable.addCell(createCell("Bill Number", true));
            infoTable.addCell(createCell(invoiceNo, false));

            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());

            infoTable.addCell(createCell("Date", true));
            infoTable.addCell(createCell(currentDate, false));

            infoTable.addCell(createCell("Time", true));
            infoTable.addCell(createCell(currentTime, false));

            doc.add(infoTable);

            // Line separator
            LineSeparator ls = new LineSeparator();
            ls.setLineColor(BaseColor.BLACK);
            doc.add(new Chunk(ls));

            // Products Table
            PdfPTable table = new PdfPTable(new float[]{3, 1, 2});
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

            table.addCell(new PdfPCell(new Phrase("Product", headFont)));
            table.addCell(new PdfPCell(new Phrase("QTY", headFont)));
            table.addCell(new PdfPCell(new Phrase("Price(Rs)", headFont)));

            for (OrderDetail od : cartList) {
                table.addCell(od.getProductName());
                table.addCell(String.valueOf(od.getOrderQty()));
                table.addCell(String.format("%.2f", od.getTotal()));
            }

            doc.add(table);

            // Line separator
            doc.add(new Chunk(ls));

            // Total Amount
            Paragraph total = new Paragraph("Total Amount : " + String.format("%.2f", totalAmount),
                    new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
            total.setAlignment(Element.ALIGN_RIGHT);
            total.setSpacingBefore(10f);
            total.setSpacingAfter(10f);
            doc.add(total);

            // Footer line
            doc.add(new Chunk(ls));

            // Footer message
            Paragraph footerMsg = new Paragraph("Thank You ! Come Again...\nDressHub",
                    new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD));
            footerMsg.setAlignment(Element.ALIGN_CENTER);
            footerMsg.setSpacingBefore(10f);
            doc.add(footerMsg);

            doc.close();

            System.out.println("Invoice saved to: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PdfPCell createCell(String text, boolean bold) {
        Font font = bold ? new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD) :
                new Font(Font.FontFamily.HELVETICA, 12);
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
