package com.CLMTZ.Backend.service.security.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.security.UserUserManagementDTO;
import com.CLMTZ.Backend.model.security.UserUserManagement;
import com.CLMTZ.Backend.repository.security.IUserUserManagementRepository;
import com.CLMTZ.Backend.repository.security.IUserManagementRepository;
import com.CLMTZ.Backend.repository.general.IUserRepository;
import com.CLMTZ.Backend.service.security.IUserUserManagementService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserUserManagementServiceImpl implements IUserUserManagementService {

    private final IUserUserManagementRepository repository;
    private final IUserRepository userRepository;
    private final IUserManagementRepository userManagementRepository;

    @Override
    public List<UserUserManagementDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public UserUserManagementDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("UserUserManagement not found with id: " + id)); }

    @Override
    public UserUserManagementDTO save(UserUserManagementDTO dto) {
        UserUserManagement e = new UserUserManagement();
        e.setState(dto.getState() != null ? dto.getState() : true);
        if (dto.getUserId() != null) e.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        if (dto.getUserManagementId() != null) e.setUserManagement(userManagementRepository.findById(dto.getUserManagementId()).orElseThrow(() -> new RuntimeException("UserManagement not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public UserUserManagementDTO update(Integer id, UserUserManagementDTO dto) {
        UserUserManagement e = repository.findById(id).orElseThrow(() -> new RuntimeException("UserUserManagement not found with id: " + id));
        e.setState(dto.getState());
        if (dto.getUserId() != null) e.setUser(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        if (dto.getUserManagementId() != null) e.setUserManagement(userManagementRepository.findById(dto.getUserManagementId()).orElseThrow(() -> new RuntimeException("UserManagement not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private UserUserManagementDTO toDTO(UserUserManagement e) {
        UserUserManagementDTO d = new UserUserManagementDTO();
        d.setUserUserGId(e.getUserUserGId());
        d.setUserId(e.getUser() != null ? e.getUser().getUserId() : null);
        d.setUserManagementId(e.getUserManagement() != null ? e.getUserManagement().getUserGId() : null);
        d.setState(e.getState());
        return d;
    }
}
