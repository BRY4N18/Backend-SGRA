package com.CLMTZ.Backend.service.security;

import java.util.List;
import com.CLMTZ.Backend.dto.security.UserUserManagementDTO;

public interface IUserUserManagementService {
    List<UserUserManagementDTO> findAll();
    UserUserManagementDTO findById(Integer id);
    UserUserManagementDTO save(UserUserManagementDTO dto);
    UserUserManagementDTO update(Integer id, UserUserManagementDTO dto);
    void deleteById(Integer id);
}
