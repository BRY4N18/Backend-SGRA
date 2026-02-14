package com.CLMTZ.Backend.controller.academic;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.CLMTZ.Backend.dto.academic.ClassScheduleDTO;
import com.CLMTZ.Backend.service.academic.IClassScheduleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/academic/class-schedules")
@RequiredArgsConstructor
public class ClassScheduleController {

    private final IClassScheduleService service;

    @GetMapping
    public ResponseEntity<List<ClassScheduleDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<ClassScheduleDTO> findById(@PathVariable Integer id) { return ResponseEntity.ok(service.findById(id)); }

    @PostMapping
    public ResponseEntity<ClassScheduleDTO> save(@RequestBody ClassScheduleDTO dto) { return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED); }

    @PutMapping("/{id}")
    public ResponseEntity<ClassScheduleDTO> update(@PathVariable Integer id, @RequestBody ClassScheduleDTO dto) { return ResponseEntity.ok(service.update(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) { service.deleteById(id); return ResponseEntity.noContent().build(); }
}
