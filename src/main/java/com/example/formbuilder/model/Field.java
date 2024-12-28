package com.example.formbuilder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Field {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String fieldName;
    private String label;
    private String type;
    private String defaultValue;
    
    @ManyToOne
    @JoinColumn(name = "form_id")
    private Form form;
}