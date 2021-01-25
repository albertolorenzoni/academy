package com.al.academyspring.service;

import com.al.academyspring.DTO.EnrollmentDTO;
import com.al.academyspring.model.Enrollment;

import java.util.Collection;

public interface EnrollmentService {
    Collection<EnrollmentDTO> findAll();
    EnrollmentDTO findById(int id);
    EnrollmentDTO add(EnrollmentDTO e);
    EnrollmentDTO update(EnrollmentDTO e);
    EnrollmentDTO delete(int id);
}
