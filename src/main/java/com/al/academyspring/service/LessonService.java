package com.al.academyspring.service;

import com.al.academyspring.DTO.LessonDTO;
import com.al.academyspring.model.CalendarConfig;

import java.util.Collection;

public interface LessonService {
    Collection<LessonDTO> findAll();
    LessonDTO findById(int id);
    LessonDTO add(LessonDTO l);
    LessonDTO update(LessonDTO l);
    LessonDTO delete(int id);
    Collection<LessonDTO> createCalendar(int courseId, CalendarConfig config);
    Collection<LessonDTO> getCourseCalendar(int courseId);
}
