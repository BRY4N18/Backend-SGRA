package com.CLMTZ.Backend.service.security;

import java.util.List;
import com.CLMTZ.Backend.dto.security.ModuleManagementDTO;

public interface IModuleManagementService {
    List<ModuleManagementDTO> findAll();
    ModuleManagementDTO findById(Integer id);
    ModuleManagementDTO save(ModuleManagementDTO dto);
    ModuleManagementDTO update(Integer id, ModuleManagementDTO dto);
    void deleteById(Integer id);
}
