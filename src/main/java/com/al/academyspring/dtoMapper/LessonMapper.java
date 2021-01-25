package com.al.academyspring.dtoMapper;

import com.al.academyspring.DTO.LessonDTO;
import com.al.academyspring.model.Classroom;
import com.al.academyspring.model.Course;
import com.al.academyspring.model.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LessonMapper {

    @Mapping(target = "courseId", expression = "java(l.getCourse().getId())")
    @Mapping(target = "courseName", expression = "java(l.getCourse().getName())")
    @Mapping(target = "classroomId", expression = "java(l.getClassroom().getId())")
    @Mapping(target = "classroomName", expression = "java(l.getClassroom().getName())")
    LessonDTO mapToDto(Lesson l);

    @Mapping(target = "id", expression = "java(l.getId())")
    @Mapping(target = "course", source = "course")
    @Mapping(target = "classroom", source = "classroom")
    Lesson mapDtoToLesson(LessonDTO l, Course course, Classroom classroom);
}
