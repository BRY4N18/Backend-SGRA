package com.CLMTZ.Backend.service.security.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.security.RoleManagementRoleDTO;
import com.CLMTZ.Backend.model.security.Role;
import com.CLMTZ.Backend.model.security.RoleManagement;
import com.CLMTZ.Backend.model.security.RoleManagementRole;
import com.CLMTZ.Backend.repository.security.IRoleManagementRepository;
import com.CLMTZ.Backend.repository.security.IRoleManagementRoleRepository;
import com.CLMTZ.Backend.repository.security.IRoleRepository;
import com.CLMTZ.Backend.service.security.IRoleManagementRoleService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleManagementRoleServiceImpl implements IRoleManagementRoleService {

    private final IRoleManagementRoleRepository repository;
    private final IRoleManagementRepository roleManagementRepository;
    private final IRoleRepository roleRepository;

    @Override
    public List<RoleManagementRoleDTO> findAll() {
        return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public RoleManagementRoleDTO findById(Integer id) {
        return repository.findById(id).map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("RoleManagementRole not found with id: " + id));
    }

    @Override
    public RoleManagementRoleDTO save(RoleManagementRoleDTO dto) {
        RoleManagementRole e = new RoleManagementRole();
        if (dto.getRoleManagementId() != null) {
            RoleManagement rm = roleManagementRepository.findById(dto.getRoleManagementId())
                    .orElseThrow(() -> new RuntimeException("RoleManagement not found with id: " + dto.getRoleManagementId()));
            e.setRoleManagementId(rm);
        }
        if (dto.getRoleId() != null) {
            Role role = roleRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + dto.getRoleId()));
            e.setRoleId(role);
        }
        e.setState(dto.getState() != null ? dto.getState() : true);
        return toDTO(repository.save(e));
    }

    @Override
    public RoleManagementRoleDTO update(Integer id, RoleManagementRoleDTO dto) {
        RoleManagementRole e = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("RoleManagementRole not found with id: " + id));
        if (dto.getRoleManagementId() != null) {
            RoleManagement rm = roleManagementRepository.findById(dto.getRoleManagementId())
                    .orElseThrow(() -> new RuntimeException("RoleManagement not found with id: " + dto.getRoleManagementId()));
            e.setRoleManagementId(rm);
        }
        if (dto.getRoleId() != null) {
            Role role = roleRepository.findById(dto.getRoleId())
                    .orElseThrow(() -> new RuntimeException("Role not found with id: " + dto.getRoleId()));
            e.setRoleId(role);
        }
        e.setState(dto.getState());
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    private RoleManagementRoleDTO toDTO(RoleManagementRole e) {
        RoleManagementRoleDTO d = new RoleManagementRoleDTO();
        d.setRoleManagementRoleId(e.getRoleManagementRoleId());
        d.setRoleManagementId(e.getRoleManagementId() != null ? e.getRoleManagementId().getRoleGId() : null);
        d.setRoleId(e.getRoleId() != null ? e.getRoleId().getRoleId() : null);
        d.setState(e.getState());
        return d;
    }
}
