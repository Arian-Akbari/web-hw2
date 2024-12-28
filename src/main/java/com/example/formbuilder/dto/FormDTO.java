package com.example.formbuilder.dto;

import lombok.Data;
import java.util.List;

@Data
public class FormDTO {
    private Long id;
    private String name;
    private boolean published;
    private List<FieldDTO> fields;
    private String submitMethod;
    private String submitUrl;
}