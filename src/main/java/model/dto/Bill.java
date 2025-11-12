package model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Bill {
    private String invoiceNo;
    private String orderId;
    private Timestamp createdAt;

    // Constructor
    public Bill(String invoiceNo, String orderId) {
        this.invoiceNo = invoiceNo;
        this.orderId = orderId;
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
