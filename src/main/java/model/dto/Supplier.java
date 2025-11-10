package model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Supplier {

    private String supCode;
    private String compName;
    private String supName;
    private String phone;
    private String email;
    private String notes;
}
