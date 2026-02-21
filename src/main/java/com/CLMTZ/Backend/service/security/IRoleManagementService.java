package com.CLMTZ.Backend.service.security;

import java.util.List;
import com.CLMTZ.Backend.dto.security.RoleManagementDTO;
import com.CLMTZ.Backend.dto.security.Response.RoleListManagementResponseDTO;
import com.CLMTZ.Backend.dto.security.Response.SpResponseDTO;

public interface IRoleManagementService {
    List<RoleManagementDTO> findAll();
    RoleManagementDTO findById(Integer id);
    RoleManagementDTO save(RoleManagementDTO dto);
    RoleManagementDTO update(Integer id, RoleManagementDTO dto);
    void deleteById(Integer id);

    SpResponseDTO createRoleManagement(RoleManagementDTO roleRequest);
    SpResponseDTO updateRoleManagement(RoleManagementDTO rolRequest);
    List<RoleListManagementResponseDTO> listRolesManagement(String filter, Boolean state);
    List<RoleManagementDTO> listRoleNames();
}
