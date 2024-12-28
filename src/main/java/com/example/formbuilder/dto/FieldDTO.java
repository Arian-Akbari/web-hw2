package com.example.formbuilder.dto;

import lombok.Data;

@Data
public class FieldDTO {
    private Long id;
    private String fieldName;
    private String label;
    private String type;
    private String defaultValue;
}