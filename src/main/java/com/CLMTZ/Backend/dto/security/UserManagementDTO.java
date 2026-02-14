package com.CLMTZ.Backend.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserManagementDTO {
    private Integer userGId;
    private String user;
    private String password;
    private Boolean state;
    private Integer userId;
}
