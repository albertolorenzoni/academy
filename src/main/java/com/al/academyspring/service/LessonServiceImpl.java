package com.al.academyspring.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.al.academyspring.DTO.LessonDTO;
import com.al.academyspring.dtoMapper.LessonMapper;
import com.al.academyspring.exception.ClassroomNotFoundException;
import com.al.academyspring.exception.CourseNotFoundException;
import com.al.academyspring.exception.LessonNotFoundException;
import com.al.academyspring.model.*;
import com.al.academyspring.repository.ClassroomRepository;
import com.al.academyspring.repository.CourseRepository;
import com.al.academyspring.repository.LessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LessonServiceImpl implements LessonService {

    private LessonRepository lessonRepository;
    private CourseRepository courseRepository;
    private ClassroomRepository classroomRepository;
    private LessonMapper lessonMapper;

    @Autowired
    public LessonServiceImpl(LessonRepository lessonRepository, CourseRepository courseRepository,
                             ClassroomRepository classroomRepository, LessonMapper lessonMapper) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.classroomRepository = classroomRepository;
        this.lessonMapper = lessonMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<LessonDTO> findAll() {
        return lessonRepository.findAll().stream()
                .map(lessonMapper::mapToDto).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public LessonDTO findById(int id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new LessonNotFoundException("Lesson not found with id: " + id));
        return lessonMapper.mapToDto(lesson);
    }

    @Override
    @Transactional
    public LessonDTO add(LessonDTO l) {
        Course course = courseRepository.findById(l.getCourseId())
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + l.getCourseId()));
        Classroom classroom = classroomRepository.findById(l.getClassroomId())
                .orElseThrow(() -> new ClassroomNotFoundException("Classroom not found with id: " + l.getCourseId()));
        Lesson addedLesson = lessonMapper.mapDtoToLesson(l, course, classroom);
        return lessonMapper.mapToDto(lessonRepository.save(addedLesson));

    }
//TODO
    @Override
    @Transactional
    public LessonDTO update(LessonDTO l) {
        //controllo lezione esistente
        //se esiste devo controllare corso e classe
        //se nuova se ne occupa add ?
        return null;
    }

    @Override
    @Transactional
    public LessonDTO delete(int id) {
        //controllo lezione esistente
        return null;
    }

    @Override
    public Collection<LessonDTO> createCalendar(int courseId, CalendarConfig config) {
       Course course = courseRepository.findById(courseId)
               .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));
       Classroom prefClassroom;
       List<Classroom> candidateClassrooms = classroomRepository
               .findCandidateClassrooms(course.getCapacity());
       int classroomIndex = 0; //needed to select next possible classroom from list
       int assignedClassroomIndex = 0;

        if (course.getPrefClassroom() != null) {
           prefClassroom = course.getPrefClassroom();
       }
       else {
           prefClassroom = candidateClassrooms.get(classroomIndex++); //prima classe candidata
           assignedClassroomIndex = classroomIndex; //salvo per poterlo resettare dopo aver cercato prossima aula libera
       }
       List<CalendarDaysPref> prefList = config.getDaysPrefs();
       List<Lesson> lessons = new ArrayList<>();
       int totalHours = course.getTotalHours();
       LocalDate currentDate = config.getStartDate();

       while (totalHours > 0) {
           for (CalendarDaysPref pref : prefList) {
               if (totalHours > 0) {
                   currentDate = currentDate.with(TemporalAdjusters.nextOrSame(pref.getDay()));
                   LocalDateTime startDateTime = (LocalDateTime.of(currentDate, pref.getStartTime()));
                   LocalDateTime endDateTime = (LocalDateTime.of(currentDate, pref.getEndTime()));
                   Lesson lesson = new Lesson();
                   classroomIndex = assignedClassroomIndex;

                   if(lessonRepository.overlappingLessons(startDateTime,endDateTime,prefClassroom.getId()).isEmpty()) {
                       lesson.setClassroom(prefClassroom);
                   } else {
                       boolean lessonTimeFound = false;
                       while(classroomIndex < candidateClassrooms.size() && !lessonTimeFound) {
                           Classroom nextClassroom = candidateClassrooms.get(classroomIndex);
                           if(lessonRepository.overlappingLessons(startDateTime,endDateTime,nextClassroom.getId()).isEmpty()) {
                               lesson.setClassroom(nextClassroom);
                               lessonTimeFound = true;
                           }
                           else {
                               classroomIndex++;
                           }
                       }
                      if(lesson.getClassroom() == null) {
                          continue;
                      }
                   }
                   lesson.setCourse(course);
                   lesson.setStartTime(startDateTime);
                   lesson.setEndTime(endDateTime);
                   lessonRepository.save(lesson);
                   lessons.add(lesson);
                   totalHours = totalHours - (int) (Duration.between(pref.getStartTime(), pref.getEndTime()).toHours());
               }
               else { //ho esaurito le ore
                   break; //esco dal foreach sulle preferenze
               }
           }
        }
        lessons.sort(Comparator.comparing(Lesson::getStartTime));
        return lessons.stream().map(lessonMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    public Collection<LessonDTO> getCourseCalendar(int courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found with id: " + courseId));

        return lessonRepository.findAllByCourseId(course.getId()).stream()
                .map(lessonMapper::mapToDto).collect(Collectors.toList());
    }
}
