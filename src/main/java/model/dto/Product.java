package model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product {

    private String productId;
    private String description;
    private double unitPrice;
    private int quantity;
    private String categoryId;
    private String supplierId;
}
