package com.CLMTZ.Backend.service.security;

import java.util.List;

import com.CLMTZ.Backend.dto.security.Request.UsersRolesRequestDTO;

public interface IUsersRolesService {
    List<UsersRolesRequestDTO> findAll();
    UsersRolesRequestDTO findById(Integer id);
    UsersRolesRequestDTO save(UsersRolesRequestDTO dto);
    UsersRolesRequestDTO update(Integer id, UsersRolesRequestDTO dto);
    void deleteById(Integer id);
}
