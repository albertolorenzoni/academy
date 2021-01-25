package com.al.academyspring.dtoMapper;

import com.al.academyspring.DTO.EnrollmentDTO;
import com.al.academyspring.model.Course;
import com.al.academyspring.model.Enrollment;
import com.al.academyspring.model.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnrollmentMapper {
    @Mapping( target = "studentId", expression = "java(e.getStudent().getId())")
    @Mapping(target = "studentName",
            expression = "java(e.getStudent().getFirstName() + \" \" + e.getStudent().getLastName())")
    @Mapping(target = "courseId", expression = "java(e.getCourse().getId())")
    @Mapping(target = "courseName", expression = "java(e.getCourse().getName())")
    EnrollmentDTO mapToDto(Enrollment e);

    @Mapping(target = "id", expression = "java(enrollmentDto.getId())")
    @Mapping(target = "student", source = "student")
    @Mapping(target = "course", source = "course")
    Enrollment mapDtoToEnrollment(EnrollmentDTO enrollmentDto, Student student, Course course);
}
