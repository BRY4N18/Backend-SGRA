package com.CLMTZ.Backend.service.security;

import java.util.List;
import com.CLMTZ.Backend.dto.security.UserRoleManagementDTO;

public interface IUserRoleManagementService {
    List<UserRoleManagementDTO> findAll();
    UserRoleManagementDTO findById(Integer id);
    UserRoleManagementDTO save(UserRoleManagementDTO dto);
    UserRoleManagementDTO update(Integer id, UserRoleManagementDTO dto);
    void deleteById(Integer id);
}
