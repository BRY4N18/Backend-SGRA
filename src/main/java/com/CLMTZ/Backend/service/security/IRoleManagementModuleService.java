package com.CLMTZ.Backend.service.security;

import java.util.List;
import com.CLMTZ.Backend.dto.security.RoleManagementModuleDTO;

public interface IRoleManagementModuleService {
    List<RoleManagementModuleDTO> findAll();
    RoleManagementModuleDTO findById(Integer id);
    RoleManagementModuleDTO save(RoleManagementModuleDTO dto);
    RoleManagementModuleDTO update(Integer id, RoleManagementModuleDTO dto);
    void deleteById(Integer id);
}
