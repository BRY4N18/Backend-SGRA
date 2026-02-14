package com.CLMTZ.Backend.controller.security;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.CLMTZ.Backend.dto.security.UserManagementDTO;
import com.CLMTZ.Backend.service.security.IUserManagementService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/security/user-managements")
@RequiredArgsConstructor
public class UserManagementController {

    private final IUserManagementService service;

    @GetMapping
    public ResponseEntity<List<UserManagementDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<UserManagementDTO> findById(@PathVariable Integer id) { return ResponseEntity.ok(service.findById(id)); }

    @PostMapping
    public ResponseEntity<UserManagementDTO> save(@RequestBody UserManagementDTO dto) { return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED); }

    @PutMapping("/{id}")
    public ResponseEntity<UserManagementDTO> update(@PathVariable Integer id, @RequestBody UserManagementDTO dto) { return ResponseEntity.ok(service.update(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) { service.deleteById(id); return ResponseEntity.noContent().build(); }
}
