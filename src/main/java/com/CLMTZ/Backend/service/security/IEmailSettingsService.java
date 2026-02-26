package com.CLMTZ.Backend.service.security;

import java.util.List;
import com.CLMTZ.Backend.dto.security.EmailSettingsDTO;

public interface IEmailSettingsService {
    List<EmailSettingsDTO> findAll();
    EmailSettingsDTO findById(Integer id);
    EmailSettingsDTO save(EmailSettingsDTO dto);
    EmailSettingsDTO update(Integer id, EmailSettingsDTO dto);
    void deleteById(Integer id);
}
