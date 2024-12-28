package com.example.formbuilder.repository;

import com.example.formbuilder.model.Form;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FormRepository extends JpaRepository<Form, Long> {
    List<Form> findByPublished(boolean published);
}