package com.CLMTZ.Backend.service.security.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.CLMTZ.Backend.dto.security.Request.RoleManagementModuleRequestDTO;
import com.CLMTZ.Backend.model.security.RoleManagementModule;
import com.CLMTZ.Backend.repository.security.IRoleManagementModuleRepository;
import com.CLMTZ.Backend.repository.security.IRoleManagementRepository;
import com.CLMTZ.Backend.repository.security.IModuleManagementRepository;
import com.CLMTZ.Backend.service.security.IRoleManagementModuleService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleManagementModuleServiceImpl implements IRoleManagementModuleService {

    private final IRoleManagementModuleRepository repository;
    private final IRoleManagementRepository roleManagementRepository;
    private final IModuleManagementRepository moduleManagementRepository;

    @Override
    public List<RoleManagementModuleRequestDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public RoleManagementModuleRequestDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("RoleManagementModule not found with id: " + id)); }

    @Override
    public RoleManagementModuleRequestDTO save(RoleManagementModuleRequestDTO dto) {
        RoleManagementModule e = new RoleManagementModule();
        e.setState(dto.getState() != null ? dto.getState() : true);
        if (dto.getRoleManagementId() != null) e.setRoleManagement(roleManagementRepository.findById(dto.getRoleManagementId()).orElseThrow(() -> new RuntimeException("RoleManagement not found")));
        if (dto.getModuleManagementId() != null) e.setModuleManagement(moduleManagementRepository.findById(dto.getModuleManagementId()).orElseThrow(() -> new RuntimeException("ModuleManagement not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public RoleManagementModuleRequestDTO update(Integer id, RoleManagementModuleRequestDTO dto) {
        RoleManagementModule e = repository.findById(id).orElseThrow(() -> new RuntimeException("RoleManagementModule not found with id: " + id));
        e.setState(dto.getState());
        if (dto.getRoleManagementId() != null) e.setRoleManagement(roleManagementRepository.findById(dto.getRoleManagementId()).orElseThrow(() -> new RuntimeException("RoleManagement not found")));
        if (dto.getModuleManagementId() != null) e.setModuleManagement(moduleManagementRepository.findById(dto.getModuleManagementId()).orElseThrow(() -> new RuntimeException("ModuleManagement not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private RoleManagementModuleRequestDTO toDTO(RoleManagementModule e) {
        RoleManagementModuleRequestDTO d = new RoleManagementModuleRequestDTO();
        d.setRoleModuleGId(e.getRoleModuleGId()); d.setState(e.getState());
        d.setRoleManagementId(e.getRoleManagement() != null ? e.getRoleManagement().getRoleGId() : null);
        d.setModuleManagementId(e.getModuleManagement() != null ? e.getModuleManagement().getRoleGId() : null);
        return d;
    }
}
