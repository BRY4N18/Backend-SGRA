package com.CLMTZ.Backend.dto.security.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessRequestDTO {
    private Integer accessId;
    private String username;
    private String password;
    private Boolean state;
    private Integer userId;
}
