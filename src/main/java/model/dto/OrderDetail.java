package model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderDetail {

    private String productId;
    private String productName;
    private double unitPrice;
    private int orderQty;

    public double getTotal() {
        return unitPrice * orderQty;
    }

}
