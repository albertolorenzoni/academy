package com.al.academyspring.controller;

import com.al.academyspring.DTO.CourseDTO;
import com.al.academyspring.DTO.LessonDTO;
import com.al.academyspring.exception.CourseNotFoundException;
import com.al.academyspring.model.CalendarConfig;
import com.al.academyspring.service.CourseService;
import com.al.academyspring.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/course")
public class CourseController {

    private CourseService courseService;
    private LessonService lessonService;

    @Autowired
    public CourseController(CourseService courseService, LessonService lessonService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
    }

    @GetMapping("")
    public ResponseEntity<Collection<CourseDTO>> listAll() {
        return status(HttpStatus.OK).body(courseService.findAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> findById(@PathVariable int id) {
        try{
            return status(HttpStatus.OK).body(courseService.findById(id));

        } catch (CourseNotFoundException e) {
            return status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("")
    public ResponseEntity<CourseDTO> add(@RequestBody CourseDTO c) {
        return status(HttpStatus.CREATED).body(courseService.add(c));
    }

    @GetMapping("/{courseId}/calendar")
    public ResponseEntity<Collection<LessonDTO>> getCourseCalendar (@PathVariable int courseId) {
        return status(HttpStatus.OK).body(lessonService.getCourseCalendar(courseId));
        //todo trycatch per corso non esistente
    }

    @PostMapping("/{courseId}/calendar")
    public ResponseEntity<Collection<LessonDTO>> createCalendar(@PathVariable int courseId, @RequestBody CalendarConfig config) {
        return status(HttpStatus.OK).body(lessonService.createCalendar(courseId, config));
        //TODO trycatch
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> update(@PathVariable int id, @RequestBody CourseDTO c) {
        c.setId(id);
        try {
            courseService.update(c);
            return status(HttpStatus.NO_CONTENT).body(null);
        } catch (CourseNotFoundException e) {
            return status(HttpStatus.CREATED).body(courseService.add(c));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CourseDTO> delete(@PathVariable int id) {
        try{
            return status(HttpStatus.OK).body(courseService.delete(id));
        }catch (CourseNotFoundException e) {
            return status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
