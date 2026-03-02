package com.CLMTZ.Backend.dto.security;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailSettingsDTO {
    private Integer emailSettingId;
    private Integer userId;
    private String emailSender;
    private String applicationPassword;
    private LocalDateTime createdAt;
    private Boolean state;
}
