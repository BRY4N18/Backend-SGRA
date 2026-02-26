package com.CLMTZ.Backend.controller.security;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.CLMTZ.Backend.dto.security.RoleManagementRoleDTO;
import com.CLMTZ.Backend.service.security.IRoleManagementRoleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/security/role-management-role")
@RequiredArgsConstructor
public class RoleManagementRoleController {

    private final IRoleManagementRoleService service;

    @GetMapping
    public ResponseEntity<List<RoleManagementRoleDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleManagementRoleDTO> findById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<RoleManagementRoleDTO> save(@RequestBody RoleManagementRoleDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoleManagementRoleDTO> update(@PathVariable("id") Integer id, @RequestBody RoleManagementRoleDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
