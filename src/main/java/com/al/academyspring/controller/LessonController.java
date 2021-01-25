package com.al.academyspring.controller;


import com.al.academyspring.DTO.LessonDTO;
import com.al.academyspring.exception.*;
import com.al.academyspring.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/lesson")
public class LessonController {

    private LessonService lessonService;

    @Autowired
    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("")
    public ResponseEntity<Collection<LessonDTO>> listAll() {
        return status(HttpStatus.OK).body(lessonService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDTO> findById(@PathVariable int id) {
        try{
            return status(HttpStatus.OK).body(lessonService.findById(id));

        } catch (LessonNotFoundException e) {
            return status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("")
    public ResponseEntity<LessonDTO> add(@RequestBody LessonDTO l) {
        try{
            return status(HttpStatus.CREATED).body(lessonService.add(l));
        } catch (CourseNotFoundException | ClassroomNotFoundException x) {
            return status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonDTO> update(@PathVariable int id, @RequestBody LessonDTO l) {
        l.setId(id);
        try {
            lessonService.update(l);
            return status(HttpStatus.NO_CONTENT).body(null);
        } catch (LessonNotFoundException x) {
            return status(HttpStatus.CREATED).body(lessonService.add(l));
        } catch (CourseNotFoundException | ClassroomNotFoundException x) {
            return status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LessonDTO> delete(@PathVariable int id) {
        try{
            return status(HttpStatus.OK).body(lessonService.delete(id));
        }catch (LessonNotFoundException e) {
            return status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
