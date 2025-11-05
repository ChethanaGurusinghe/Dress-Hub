package model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private String userId;
    private String fullName;
    private String email;
    private String phoneNo;
    private String userName;
    private String password;
    private String role;
}
