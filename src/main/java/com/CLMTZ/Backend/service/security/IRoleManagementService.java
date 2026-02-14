package com.CLMTZ.Backend.service.security;

import java.util.List;
import com.CLMTZ.Backend.dto.security.RoleManagementDTO;

public interface IRoleManagementService {
    List<RoleManagementDTO> findAll();
    RoleManagementDTO findById(Integer id);
    RoleManagementDTO save(RoleManagementDTO dto);
    RoleManagementDTO update(Integer id, RoleManagementDTO dto);
    void deleteById(Integer id);
}
