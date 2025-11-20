package model.dto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

    public String getCategory() {
        if (categoryId == null) return "Unknown";

        switch (categoryId.trim().toUpperCase()) {
            case "I001":
                return "Ladies";
            case "I002":
                return "Gents";
            case "I003":
                return "Kids";
            default:
                return "Unknown";
        }
    }

    public StringProperty categoryProperty() {
        return new SimpleStringProperty(getCategory());
    }
}



