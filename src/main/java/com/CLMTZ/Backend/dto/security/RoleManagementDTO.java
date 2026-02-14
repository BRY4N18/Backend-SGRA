package com.CLMTZ.Backend.dto.security;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleManagementDTO {
    private Integer roleGId;
    private String roleG;
    private String serverRole;
    private String description;
    private LocalDateTime createdAt;
    private Boolean state;
}
