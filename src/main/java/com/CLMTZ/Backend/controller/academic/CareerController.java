package com.CLMTZ.Backend.controller.academic;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.CLMTZ.Backend.dto.academic.CareerDTO;
import com.CLMTZ.Backend.service.academic.ICareerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/academic/careers")
@RequiredArgsConstructor
public class CareerController {

    private final ICareerService service;

    @GetMapping
    public ResponseEntity<List<CareerDTO>> findAll() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/{id}")
    public ResponseEntity<CareerDTO> findById(@PathVariable Integer id) { return ResponseEntity.ok(service.findById(id)); }

    @PostMapping
    public ResponseEntity<CareerDTO> save(@RequestBody CareerDTO dto) { return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED); }

    @PutMapping("/{id}")
    public ResponseEntity<CareerDTO> update(@PathVariable Integer id, @RequestBody CareerDTO dto) { return ResponseEntity.ok(service.update(id, dto)); }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) { service.deleteById(id); return ResponseEntity.noContent().build(); }
}
