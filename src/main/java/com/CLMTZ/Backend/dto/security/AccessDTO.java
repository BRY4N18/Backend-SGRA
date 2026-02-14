package com.CLMTZ.Backend.dto.security;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessDTO {
    private Integer accessId;
    private String username;
    private String password;
    private Boolean state;
    private Integer userId;
}
