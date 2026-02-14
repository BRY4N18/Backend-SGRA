package com.CLMTZ.Backend.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersRolesDTO {
    private Integer userRolesId;
    private Boolean state;
    private Integer roleId;
    private Integer userId;
}
