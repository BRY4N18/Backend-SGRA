package com.CLMTZ.Backend.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUserManagementDTO {
    private Integer userUserGId;
    private Integer userId;
    private Integer userManagementId;
    private Boolean state;
}
