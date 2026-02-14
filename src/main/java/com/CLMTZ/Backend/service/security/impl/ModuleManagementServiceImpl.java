package com.CLMTZ.Backend.service.security.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.CLMTZ.Backend.dto.security.ModuleManagementDTO;
import com.CLMTZ.Backend.model.security.ModuleManagement;
import com.CLMTZ.Backend.repository.security.IModuleManagementRepository;
import com.CLMTZ.Backend.service.security.IModuleManagementService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ModuleManagementServiceImpl implements IModuleManagementService {

    private final IModuleManagementRepository repository;

    @Override
    public List<ModuleManagementDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public ModuleManagementDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("ModuleManagement not found with id: " + id)); }

    @Override
    public ModuleManagementDTO save(ModuleManagementDTO dto) {
        ModuleManagement e = new ModuleManagement();
        e.setModuleG(dto.getModuleG()); e.setState(dto.getState() != null ? dto.getState() : true);
        return toDTO(repository.save(e));
    }

    @Override
    public ModuleManagementDTO update(Integer id, ModuleManagementDTO dto) {
        ModuleManagement e = repository.findById(id).orElseThrow(() -> new RuntimeException("ModuleManagement not found with id: " + id));
        e.setModuleG(dto.getModuleG()); e.setState(dto.getState());
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private ModuleManagementDTO toDTO(ModuleManagement e) { ModuleManagementDTO d = new ModuleManagementDTO(); d.setRoleGId(e.getRoleGId()); d.setModuleG(e.getModuleG()); d.setState(e.getState()); return d; }
}
