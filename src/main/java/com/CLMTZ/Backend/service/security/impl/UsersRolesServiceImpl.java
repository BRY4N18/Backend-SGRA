package com.CLMTZ.Backend.service.security.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.CLMTZ.Backend.dto.security.Request.UsersRolesRequestDTO;
import com.CLMTZ.Backend.model.security.UsersRoles;
import com.CLMTZ.Backend.repository.security.IUsersRolesRepository;
import com.CLMTZ.Backend.repository.security.IRoleRepository;
import com.CLMTZ.Backend.repository.general.IUserRepository;
import com.CLMTZ.Backend.service.security.IUsersRolesService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsersRolesServiceImpl implements IUsersRolesService {

    private final IUsersRolesRepository repository;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;

    @Override
    public List<UsersRolesRequestDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public UsersRolesRequestDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("UsersRoles not found with id: " + id)); }

    @Override
    public UsersRolesRequestDTO save(UsersRolesRequestDTO dto) {
        UsersRoles e = new UsersRoles();
        e.setState(dto.getState() != null ? dto.getState() : true);
        if (dto.getRoleId() != null) e.setRoleId(roleRepository.findById(dto.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found")));
        if (dto.getUserId() != null) e.setUserId(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public UsersRolesRequestDTO update(Integer id, UsersRolesRequestDTO dto) {
        UsersRoles e = repository.findById(id).orElseThrow(() -> new RuntimeException("UsersRoles not found with id: " + id));
        e.setState(dto.getState());
        if (dto.getRoleId() != null) e.setRoleId(roleRepository.findById(dto.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found")));
        if (dto.getUserId() != null) e.setUserId(userRepository.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private UsersRolesRequestDTO toDTO(UsersRoles e) {
        UsersRolesRequestDTO d = new UsersRolesRequestDTO();
        d.setUserRolesId(e.getUserRolesId()); d.setState(e.getState());
        d.setRoleId(e.getRoleId() != null ? e.getRoleId().getRoleId() : null);
        d.setUserId(e.getUserId() != null ? e.getUserId().getUserId() : null);
        return d;
    }
}
