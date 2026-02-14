package com.CLMTZ.Backend.controller.security;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.CLMTZ.Backend.dto.security.RoleManagementDTO;
import com.CLMTZ.Backend.service.security.IRoleManagementService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/security/role-managements")
@RequiredArgsConstructor
public class RoleManagementController {

    private final IRoleManagementService service;

    @GetMapping
    public ResponseEntity<List<RoleManagementDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<RoleManagementDTO> findById(@PathVariable Integer id) { return ResponseEntity.ok(service.findById(id)); }

    @PostMapping
    public ResponseEntity<RoleManagementDTO> save(@RequestBody RoleManagementDTO dto) { return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED); }

    @PutMapping("/{id}")
    public ResponseEntity<RoleManagementDTO> update(@PathVariable Integer id, @RequestBody RoleManagementDTO dto) { return ResponseEntity.ok(service.update(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) { service.deleteById(id); return ResponseEntity.noContent().build(); }
}
