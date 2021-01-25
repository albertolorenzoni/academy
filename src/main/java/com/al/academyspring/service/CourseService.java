package com.al.academyspring.service;

import com.al.academyspring.DTO.CourseDTO;

import java.util.Collection;

public interface CourseService {
    Collection<CourseDTO> findAll();
    CourseDTO findById(int id);
    CourseDTO add(CourseDTO c);
    CourseDTO update(CourseDTO c);
    CourseDTO delete(int id);
}
