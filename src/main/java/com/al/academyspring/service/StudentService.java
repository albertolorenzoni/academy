package com.al.academyspring.service;

import com.al.academyspring.DTO.StudentDTO;

import java.util.Collection;
import java.util.Map;

public interface StudentService {
    Collection<StudentDTO> findAll();
    StudentDTO findById(Integer id);
    StudentDTO add(StudentDTO student);
    StudentDTO update(StudentDTO student);
    StudentDTO delete(Integer id);
    Collection<StudentDTO> complexSearch(Map<String, String> params);
}
