package com.CLMTZ.Backend.service.security.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.security.UserManagementDTO;
import com.CLMTZ.Backend.model.security.UserManagement;
import com.CLMTZ.Backend.repository.security.IUserManagementRepository;
import com.CLMTZ.Backend.repository.general.IUserRepository;
import com.CLMTZ.Backend.service.security.IUserManagementService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserManagementServiceImpl implements IUserManagementService {

    private final IUserManagementRepository repository;
    private final IUserRepository userRepository;

    @Override
    public List<UserManagementDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public UserManagementDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("UserManagement not found with id: " + id)); }

    @Override
    public UserManagementDTO save(UserManagementDTO dto) {
        UserManagement e = new UserManagement();
        e.setUser(dto.getUser()); e.setPassword(dto.getPassword()); e.setState(dto.getState() != null ? dto.getState() : true);
        if (dto.getUserId() != null) e.setUserId(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public UserManagementDTO update(Integer id, UserManagementDTO dto) {
        UserManagement e = repository.findById(id).orElseThrow(() -> new RuntimeException("UserManagement not found with id: " + id));
        e.setUser(dto.getUser()); e.setPassword(dto.getPassword()); e.setState(dto.getState());
        if (dto.getUserId() != null) e.setUserId(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private UserManagementDTO toDTO(UserManagement e) {
        UserManagementDTO d = new UserManagementDTO();
        d.setUserGId(e.getUserGId()); d.setUser(e.getUser()); d.setPassword(e.getPassword()); d.setState(e.getState());
        d.setUserId(e.getUserId() != null ? e.getUserId().getUserId() : null);
        return d;
    }
}
