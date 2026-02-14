package com.CLMTZ.Backend.service.security.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.security.RoleManagementDTO;
import com.CLMTZ.Backend.model.security.RoleManagement;
import com.CLMTZ.Backend.repository.security.IRoleManagementRepository;
import com.CLMTZ.Backend.service.security.IRoleManagementService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleManagementServiceImpl implements IRoleManagementService {

    private final IRoleManagementRepository repository;

    @Override
    public List<RoleManagementDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public RoleManagementDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("RoleManagement not found with id: " + id)); }

    @Override
    public RoleManagementDTO save(RoleManagementDTO dto) {
        RoleManagement e = new RoleManagement();
        e.setRoleG(dto.getRoleG()); e.setServerRole(dto.getServerRole()); e.setDescription(dto.getDescription());
        e.setCreatedAt(dto.getCreatedAt()); e.setState(dto.getState() != null ? dto.getState() : true);
        return toDTO(repository.save(e));
    }

    @Override
    public RoleManagementDTO update(Integer id, RoleManagementDTO dto) {
        RoleManagement e = repository.findById(id).orElseThrow(() -> new RuntimeException("RoleManagement not found with id: " + id));
        e.setRoleG(dto.getRoleG()); e.setServerRole(dto.getServerRole()); e.setDescription(dto.getDescription());
        e.setCreatedAt(dto.getCreatedAt()); e.setState(dto.getState());
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private RoleManagementDTO toDTO(RoleManagement e) {
        RoleManagementDTO d = new RoleManagementDTO();
        d.setRoleGId(e.getRoleGId()); d.setRoleG(e.getRoleG()); d.setServerRole(e.getServerRole());
        d.setDescription(e.getDescription()); d.setCreatedAt(e.getCreatedAt()); d.setState(e.getState());
        return d;
    }
}
