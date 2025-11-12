package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import model.dto.OrderDetail;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class InvoiceGenerator {

    public static void generateInvoice(String invoiceNo, String orderId, List<OrderDetail> cartList, double totalAmount) {
        Document doc = new Document();

        try {
            // Get the Downloads folder of the current user
            String userHome = System.getProperty("user.home");
            File downloadsDir = new File(userHome + "/Downloads");
            if (!downloadsDir.exists()) {
                downloadsDir.mkdir();
            }

            // Full path for PDF
            String filePath = downloadsDir.getAbsolutePath() + "/Invoice_" + invoiceNo + ".pdf";
            PdfWriter.getInstance(doc, new FileOutputStream(filePath));

            doc.open();

            // Title
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("INVOICE", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph("\n"));

            // Invoice and Order info
            doc.add(new Paragraph("Invoice No: " + invoiceNo));
            doc.add(new Paragraph("Order ID: " + orderId));
            doc.add(new Paragraph(" "));

            // Table
            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Table headers
            Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
            table.addCell(new PdfPCell(new Phrase("Product ID", headFont)));
            table.addCell(new PdfPCell(new Phrase("Product Name", headFont)));
            table.addCell(new PdfPCell(new Phrase("Quantity", headFont)));
            table.addCell(new PdfPCell(new Phrase("Total (Rs.)", headFont)));

            // Table data
            for (OrderDetail od : cartList) {
                table.addCell(od.getProductId());
                table.addCell(od.getProductName());
                table.addCell(String.valueOf(od.getOrderQty()));
                table.addCell(String.format("%.2f", od.getTotal()));
            }

            doc.add(table);

            // Total Amount
            Paragraph total = new Paragraph("\nTotal Amount: Rs. " + String.format("%.2f", totalAmount),
                    new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
            total.setAlignment(Element.ALIGN_RIGHT);
            doc.add(total);

            doc.close();

            System.out.println("Invoice saved to: " + filePath);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
