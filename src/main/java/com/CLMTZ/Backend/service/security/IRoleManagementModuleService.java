package com.CLMTZ.Backend.service.security;

import java.util.List;

import com.CLMTZ.Backend.dto.security.Request.RoleManagementModuleRequestDTO;

public interface IRoleManagementModuleService {
    List<RoleManagementModuleRequestDTO> findAll();
    RoleManagementModuleRequestDTO findById(Integer id);
    RoleManagementModuleRequestDTO save(RoleManagementModuleRequestDTO dto);
    RoleManagementModuleRequestDTO update(Integer id, RoleManagementModuleRequestDTO dto);
    void deleteById(Integer id);
}
