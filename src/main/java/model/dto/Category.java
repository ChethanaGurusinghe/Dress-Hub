package model.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category {

    private String categoryId;
    private String name;
    private String description;

}
