package com.CLMTZ.Backend.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleManagementDTO {
    private Integer userRoleGId;
    private Boolean state;
    private Integer userManagementId;
    private Integer roleManagementId;
}
