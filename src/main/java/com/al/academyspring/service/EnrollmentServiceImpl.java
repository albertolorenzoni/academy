package com.al.academyspring.service;

import com.al.academyspring.DTO.EnrollmentDTO;
import com.al.academyspring.dtoMapper.EnrollmentMapper;
import com.al.academyspring.exception.CourseNotFoundException;
import com.al.academyspring.exception.EnrollmentNotFoundException;
import com.al.academyspring.exception.StudentNotFoundException;
import com.al.academyspring.model.Course;
import com.al.academyspring.model.Enrollment;
import com.al.academyspring.model.Student;
import com.al.academyspring.repository.CourseRepository;
import com.al.academyspring.repository.EnrollmentRepository;
import com.al.academyspring.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements  EnrollmentService{

    private EnrollmentRepository enrollmentRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private EnrollmentMapper enrollmentMapper;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository, EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<EnrollmentDTO> findAll() {
        return enrollmentRepository.findAll().stream()
                .map(enrollmentMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EnrollmentDTO findById(int id) {
        Enrollment e = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment not found with id: " + id));
        return enrollmentMapper.mapToDto(e);
    }

    @Override
    @Transactional
    public EnrollmentDTO add(EnrollmentDTO e) {
        Student s = studentRepository.findById(e.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + e.getStudentId()));
        Course c = courseRepository.findById(e.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + e.getCourseId()));
        Enrollment addedEnrollment = enrollmentMapper.mapDtoToEnrollment(e, s, c);
        return enrollmentMapper.mapToDto(enrollmentRepository.save(addedEnrollment));
    }

    @Override
    @Transactional
    public EnrollmentDTO update(EnrollmentDTO e) {
        Enrollment oldEnrollment = enrollmentRepository.findById(e.getId())
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment not found with id: " + e.getId()));
        Student updatedStudent = studentRepository.findById(e.getStudentId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + e.getStudentId()));
        Course updatedCourse = courseRepository.findById(e.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + e.getCourseId()));
        return enrollmentMapper.mapToDto(enrollmentRepository.save(enrollmentMapper.mapDtoToEnrollment(e, updatedStudent, updatedCourse)));
    }

    @Override
    @Transactional
    public EnrollmentDTO delete(int id) {
        Enrollment deletedEnrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EnrollmentNotFoundException("Enrollment not found with id: " + id));
        enrollmentRepository.delete(deletedEnrollment);
        return enrollmentMapper.mapToDto(deletedEnrollment);
    }
}
