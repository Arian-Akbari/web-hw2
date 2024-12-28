package com.example.formbuilder.controller;

import com.example.formbuilder.dto.FieldDTO;
import com.example.formbuilder.dto.FormDTO;
import com.example.formbuilder.service.FormService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class FormController {
    private final FormService formService;

    @GetMapping
    public ResponseEntity<List<FormDTO>> getAllForms() {
        return ResponseEntity.ok(formService.getAllForms());
    }

    @PostMapping
    public ResponseEntity<FormDTO> createForm(@RequestBody FormDTO formDTO) {
        return ResponseEntity.ok(formService.createForm(formDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormDTO> getForm(@PathVariable Long id) {
        return ResponseEntity.ok(formService.getForm(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormDTO> updateForm(@PathVariable Long id, @RequestBody FormDTO formDTO) {
        return ResponseEntity.ok(formService.updateForm(id, formDTO));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteForm(@PathVariable Long id) {
        formService.deleteForm(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity<FormDTO> togglePublishStatus(@PathVariable Long id) {
        return ResponseEntity.ok(formService.togglePublishStatus(id));
    }

    @GetMapping("/{id}/fields")
    public ResponseEntity<List<FieldDTO>> getFormFields(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(formService.getFormFields(id));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/fields")
    public ResponseEntity<List<FieldDTO>> updateFormFields(@PathVariable Long id, @RequestBody List<FieldDTO> fields) {
        return ResponseEntity.ok(formService.updateFormFields(id, fields));
    }

    @GetMapping("/published")
    public ResponseEntity<List<FormDTO>> getPublishedForms() {
        return ResponseEntity.ok(formService.getPublishedForms());
    }
}