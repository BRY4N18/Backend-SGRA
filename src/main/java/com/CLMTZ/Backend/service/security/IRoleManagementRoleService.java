package com.CLMTZ.Backend.service.security;

import java.util.List;
import com.CLMTZ.Backend.dto.security.RoleManagementRoleDTO;

public interface IRoleManagementRoleService {
    List<RoleManagementRoleDTO> findAll();
    RoleManagementRoleDTO findById(Integer id);
    RoleManagementRoleDTO save(RoleManagementRoleDTO dto);
    RoleManagementRoleDTO update(Integer id, RoleManagementRoleDTO dto);
    void deleteById(Integer id);
}
