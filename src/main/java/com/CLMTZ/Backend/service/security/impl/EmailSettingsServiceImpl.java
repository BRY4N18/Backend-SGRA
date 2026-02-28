package com.CLMTZ.Backend.service.security.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.security.EmailSettingsDTO;
import com.CLMTZ.Backend.model.general.User;
import com.CLMTZ.Backend.model.security.EmailSettings;
import com.CLMTZ.Backend.repository.general.IUserRepository;
import com.CLMTZ.Backend.repository.security.IEmailSettingsRepository;
import com.CLMTZ.Backend.service.security.IEmailSettingsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailSettingsServiceImpl implements IEmailSettingsService {

    private final IEmailSettingsRepository repository;
    private final IUserRepository userRepository;

    @Override
    public List<EmailSettingsDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public EmailSettingsDTO findById(Integer id) {
        return repository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("EmailSettings not found with id: " + id));
    }

    @Override
    public EmailSettingsDTO save(EmailSettingsDTO dto) {
        EmailSettings e = new EmailSettings();
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
            e.setUserId(user);
        }
        e.setEmailSender(dto.getEmailSender());
        e.setApplicationPassword(dto.getApplicationPassword());
        e.setCreatedAt(dto.getCreatedAt() != null ? dto.getCreatedAt() : LocalDateTime.now());
        e.setState(dto.getState() != null ? dto.getState() : true);
        return toDTO(repository.save(e));
    }

    @Override
    public EmailSettingsDTO update(Integer id, EmailSettingsDTO dto) {
        EmailSettings e = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EmailSettings not found with id: " + id));
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
            e.setUserId(user);
        }
        e.setEmailSender(dto.getEmailSender());
        e.setApplicationPassword(dto.getApplicationPassword());
        e.setState(dto.getState());
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    private EmailSettingsDTO toDTO(EmailSettings e) {
        EmailSettingsDTO d = new EmailSettingsDTO();
        d.setEmailSettingId(e.getEmailSettingId());
        d.setUserId(e.getUserId() != null ? e.getUserId().getUserId() : null);
        d.setEmailSender(e.getEmailSender());
        d.setApplicationPassword(e.getApplicationPassword());
        d.setCreatedAt(e.getCreatedAt());
        d.setState(e.getState());
        return d;
    }
}
