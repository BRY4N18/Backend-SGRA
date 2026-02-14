package com.CLMTZ.Backend.controller.security;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.CLMTZ.Backend.dto.security.UsersRolesDTO;
import com.CLMTZ.Backend.service.security.IUsersRolesService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/security/users-roles")
@RequiredArgsConstructor
public class UsersRolesController {

    private final IUsersRolesService service;

    @GetMapping
    public ResponseEntity<List<UsersRolesDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<UsersRolesDTO> findById(@PathVariable Integer id) { return ResponseEntity.ok(service.findById(id)); }

    @PostMapping
    public ResponseEntity<UsersRolesDTO> save(@RequestBody UsersRolesDTO dto) { return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED); }

    @PutMapping("/{id}")
    public ResponseEntity<UsersRolesDTO> update(@PathVariable Integer id, @RequestBody UsersRolesDTO dto) { return ResponseEntity.ok(service.update(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) { service.deleteById(id); return ResponseEntity.noContent().build(); }
}
