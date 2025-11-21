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

    public User(String userId, String fullName, String email, String phoneNo, String role) {
        this.userId = userId;
        this.fullName = fullName;
        this.email = email;
        this.phoneNo = phoneNo;
        this.role = role;
    }
}
