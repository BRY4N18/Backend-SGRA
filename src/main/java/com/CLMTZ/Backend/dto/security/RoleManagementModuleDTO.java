package com.CLMTZ.Backend.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleManagementModuleDTO {
    private Integer roleModuleGId;
    private Boolean state;
    private Integer roleManagementId;
    private Integer moduleManagementId;
}
