package com.CLMTZ.Backend.service.security;

import java.util.List;
import com.CLMTZ.Backend.dto.security.UsersRolesDTO;

public interface IUsersRolesService {
    List<UsersRolesDTO> findAll();
    UsersRolesDTO findById(Integer id);
    UsersRolesDTO save(UsersRolesDTO dto);
    UsersRolesDTO update(Integer id, UsersRolesDTO dto);
    void deleteById(Integer id);
}
