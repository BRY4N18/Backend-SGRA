package com.CLMTZ.Backend.service.security.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

import com.CLMTZ.Backend.dto.security.Request.RoleRequestDTO;
import com.CLMTZ.Backend.model.security.Role;
import com.CLMTZ.Backend.repository.security.IRoleRepository;
import com.CLMTZ.Backend.service.security.IRoleService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository repository;

    @Override
    public List<RoleRequestDTO> findAll() { return repository.findAll().stream().map(this::toDTO).collect(Collectors.toList()); }

    @Override
    public RoleRequestDTO findById(Integer id) { return repository.findById(id).map(this::toDTO).orElseThrow(() -> new RuntimeException("Role not found with id: " + id)); }

    @Override
    public RoleRequestDTO save(RoleRequestDTO dto) {
        Role e = new Role();
        e.setRole(dto.getRole()); e.setState(dto.getState() != null ? dto.getState() : true);
        return toDTO(repository.save(e));
    }

    @Override
    public RoleRequestDTO update(Integer id, RoleRequestDTO dto) {
        Role e = repository.findById(id).orElseThrow(() -> new RuntimeException("Role not found with id: " + id));
        e.setRole(dto.getRole()); e.setState(dto.getState());
        return toDTO(repository.save(e));
    }

    @Override
    public void deleteById(Integer id) { repository.deleteById(id); }

    private RoleRequestDTO toDTO(Role e) { RoleRequestDTO d = new RoleRequestDTO(); d.setRoleId(e.getRoleId()); d.setRole(e.getRole()); d.setState(e.getState()); return d; }
}
