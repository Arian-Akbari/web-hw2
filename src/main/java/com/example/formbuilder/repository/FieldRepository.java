package com.example.formbuilder.repository;

import com.example.formbuilder.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {
    List<Field> findByFormId(Long formId);
}