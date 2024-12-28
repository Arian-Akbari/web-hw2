package com.example.formbuilder.service;

import com.example.formbuilder.dto.FieldDTO;
import com.example.formbuilder.dto.FormDTO;
import com.example.formbuilder.model.Field;
import com.example.formbuilder.model.Form;
import com.example.formbuilder.repository.FieldRepository;
import com.example.formbuilder.repository.FormRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormService {
    private final FormRepository formRepository;
    private final FieldRepository fieldRepository;

    public List<FormDTO> getAllForms() {
        return formRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public FormDTO createForm(FormDTO formDTO) {
        Form form = new Form();
        form.setName(formDTO.getName());
        form.setSubmitMethod(formDTO.getSubmitMethod());
        form.setSubmitUrl(formDTO.getSubmitUrl());
        
        if (formDTO.getFields() != null) {
            List<Field> fields = formDTO.getFields().stream()
                .map(fieldDTO -> {
                    Field field = new Field();
                    field.setFieldName(fieldDTO.getFieldName());
                    field.setLabel(fieldDTO.getLabel());
                    field.setType(fieldDTO.getType());
                    field.setDefaultValue(fieldDTO.getDefaultValue());
                    field.setForm(form);
                    return field;
                })
                .collect(Collectors.toList());
            form.setFields(fields);
        }
        
        return convertToDTO(formRepository.save(form));
    }

    public FormDTO getForm(Long id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Form not found"));
        return convertToDTO(form);
    }

    @Transactional
    public FormDTO updateForm(Long id, FormDTO formDTO) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Form not found"));
        form.setName(formDTO.getName());
        form.setSubmitMethod(formDTO.getSubmitMethod());
        form.setSubmitUrl(formDTO.getSubmitUrl());
        return convertToDTO(formRepository.save(form));
    }

    public void deleteForm(Long id) {
        formRepository.deleteById(id);
    }

    @Transactional
    public FormDTO togglePublishStatus(Long id) {
        Form form = formRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Form not found"));
        form.setPublished(!form.isPublished());
        return convertToDTO(formRepository.save(form));
    }

    public List<Field> getFieldsByFormId(Long formId) {
        return fieldRepository.findByFormId(formId);
    }

    private FormDTO convertToDTO(Form form) {
        FormDTO dto = new FormDTO();
        dto.setId(form.getId());
        dto.setName(form.getName());
        dto.setPublished(form.isPublished());
        dto.setSubmitMethod(form.getSubmitMethod());
        dto.setSubmitUrl(form.getSubmitUrl());
        dto.setFields(form.getFields().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private FieldDTO convertToDTO(Field field) {
        FieldDTO dto = new FieldDTO();
        dto.setId(field.getId());
        dto.setFieldName(field.getFieldName());
        dto.setLabel(field.getLabel());
        dto.setType(field.getType());
        dto.setDefaultValue(field.getDefaultValue());
        return dto;
    }

    public List<FieldDTO> getFormFields(Long formId) {
        Form form = formRepository.findById(formId)
                .orElseThrow(() -> new EntityNotFoundException("Form not found"));
                
        return form.getFields().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<FieldDTO> updateFormFields(Long formId, List<FieldDTO> fieldDTOs) {
        Form form = formRepository.findById(formId)
            .orElseThrow(() -> new EntityNotFoundException("Form not found"));
        
        form.getFields().clear();
        
        List<Field> fields = fieldDTOs.stream()
            .map(dto -> {
                Field field = new Field();
                field.setFieldName(dto.getFieldName());
                field.setLabel(dto.getLabel());
                field.setType(dto.getType());
                field.setDefaultValue(dto.getDefaultValue());
                field.setForm(form);
                return field;
            })
            .collect(Collectors.toList());
        
        form.setFields(fields);
        formRepository.save(form);
        
        return fieldDTOs;
    }
    public List<FormDTO> getPublishedForms() {
    return formRepository.findByPublished(true).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
}