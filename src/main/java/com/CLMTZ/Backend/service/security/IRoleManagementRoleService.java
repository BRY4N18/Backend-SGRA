package com.CLMTZ.Backend.service.security;

import java.util.List;

import com.CLMTZ.Backend.dto.security.Request.RoleManagementRoleRequestDTO;

public interface IRoleManagementRoleService {
    List<RoleManagementRoleRequestDTO> findAll();
    RoleManagementRoleRequestDTO findById(Integer id);
    RoleManagementRoleRequestDTO save(RoleManagementRoleRequestDTO dto);
    RoleManagementRoleRequestDTO update(Integer id, RoleManagementRoleRequestDTO dto);
    void deleteById(Integer id);
}
