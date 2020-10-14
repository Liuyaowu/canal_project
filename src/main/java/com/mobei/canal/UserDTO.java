package com.mobei.canal;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String nick;
    private String phone;
    private String password;
    private String email;
    private String account;
}
