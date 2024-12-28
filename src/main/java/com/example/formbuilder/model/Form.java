package com.example.formbuilder.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Form {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private boolean published;
    
    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL)
    private List<Field> fields = new ArrayList<>();
    
    private String submitMethod;
    private String submitUrl;
}