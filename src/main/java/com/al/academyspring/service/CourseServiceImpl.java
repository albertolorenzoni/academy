package com.al.academyspring.service;

import com.al.academyspring.DTO.CourseDTO;
import com.al.academyspring.dtoMapper.CourseMapper;
import com.al.academyspring.exception.CourseNotFoundException;
import com.al.academyspring.model.Course;
import com.al.academyspring.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;
    private CourseMapper courseMapper;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<CourseDTO> findAll() {
        return courseRepository.findAll().stream()
                .map(c -> courseMapper.mapToDto(c)).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CourseDTO findById(int id) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
        return courseMapper.mapToDto(c);
    }

    @Override
    @Transactional
    public CourseDTO add(CourseDTO c) {
        Course addedCourse = courseMapper.mapDtoToCourse(c);
        return courseMapper.mapToDto(courseRepository.save(addedCourse));
    }

    @Override
    @Transactional
    public CourseDTO update(CourseDTO c) {
        courseRepository.findById(c.getId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + c.getId()));
        return courseMapper.mapToDto(courseRepository.save(courseMapper.mapDtoToCourse(c)));
    }

    @Override
    @Transactional
    public CourseDTO delete(int id) {
        Course deletedCourse = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + id));
        courseRepository.delete(deletedCourse);
        return courseMapper.mapToDto(deletedCourse);
    }

    public String test() {
        return "";
    }
}
