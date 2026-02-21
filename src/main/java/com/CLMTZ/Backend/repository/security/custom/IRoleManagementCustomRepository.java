package com.CLMTZ.Backend.repository.security.custom;

import java.util.List;

import com.CLMTZ.Backend.dto.security.Response.RoleListManagementResponseDTO;

public interface IRoleManagementCustomRepository {
    List<RoleListManagementResponseDTO> listRolesManagement(String filter,Boolean state);
}
