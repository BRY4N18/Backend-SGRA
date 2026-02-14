package com.CLMTZ.Backend.service.security.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.security.UserRoleManagementDTO;
import com.CLMTZ.Backend.model.security.UserRoleManagement;
import com.CLMTZ.Backend.repository.security.IUserRoleManagementRepository;
import com.CLMTZ.Backend.repository.security.IUserManagementRepository;
import com.CLMTZ.Backend.repository.security.IRoleManagementRepository;
import com.CLMTZ.Backend.service.security.IUserRoleManagementService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserRoleManagementServiceImpl implements IUserRoleManagementService {

    private final IUserRoleManagementRepository repository;
    private final IUserManagementRepository userManagementRepository;
    private final IRoleManagementRepository roleManagementRepository;

    @Override
    public List<UserRoleManagementDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public UserRoleManagementDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("UserRoleManagement not found with id: " + id)); }

    @Override
    public UserRoleManagementDTO save(UserRoleManagementDTO dto) {
        UserRoleManagement e = new UserRoleManagement();
        e.setState(dto.getState() != null ? dto.getState() : true);
        if (dto.getUserManagementId() != null) e.setUserManagement(userManagementRepository.findById(dto.getUserManagementId()).orElseThrow(() -> new RuntimeException("UserManagement not found")));
        if (dto.getRoleManagementId() != null) e.setRoleManagement(roleManagementRepository.findById(dto.getRoleManagementId()).orElseThrow(() -> new RuntimeException("RoleManagement not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public UserRoleManagementDTO update(Integer id, UserRoleManagementDTO dto) {
        UserRoleManagement e = repository.findById(id).orElseThrow(() -> new RuntimeException("UserRoleManagement not found with id: " + id));
        e.setState(dto.getState());
        if (dto.getUserManagementId() != null) e.setUserManagement(userManagementRepository.findById(dto.getUserManagementId()).orElseThrow(() -> new RuntimeException("UserManagement not found")));
        if (dto.getRoleManagementId() != null) e.setRoleManagement(roleManagementRepository.findById(dto.getRoleManagementId()).orElseThrow(() -> new RuntimeException("RoleManagement not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private UserRoleManagementDTO toDTO(UserRoleManagement e) {
        UserRoleManagementDTO d = new UserRoleManagementDTO();
        d.setUserRoleGId(e.getUserRoleGId()); d.setState(e.getState());
        d.setUserManagementId(e.getUserManagement() != null ? e.getUserManagement().getUserGId() : null);
        d.setRoleManagementId(e.getRoleManagement() != null ? e.getRoleManagement().getRoleGId() : null);
        return d;
    }
}
