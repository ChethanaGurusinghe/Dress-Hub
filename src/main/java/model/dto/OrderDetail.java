package model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetail {

    private String orderId;
    private String productId;
    private int orderQty;

    public OrderDetail(String productId, int orderQty) {
        this.productId = productId;
        this.orderQty = orderQty;
    }
}
