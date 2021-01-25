package com.al.academyspring.dtoMapper;

import com.al.academyspring.DTO.StudentDTO;
import com.al.academyspring.model.Student;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EnrollmentMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface StudentMapper {
    StudentDTO mapToDto(Student s);

    @Mapping(target = "enrollments", ignore = true)
    Student mapDtoToStudent(StudentDTO s);
}
