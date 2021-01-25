package com.al.academyspring.controller;

import com.al.academyspring.DTO.EnrollmentDTO;
import com.al.academyspring.exception.CourseNotFoundException;
import com.al.academyspring.exception.EnrollmentNotFoundException;
import com.al.academyspring.exception.StudentNotFoundException;
import com.al.academyspring.service.EnrollmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/enrollment")
public class EnrollmentController {

    private EnrollmentService service;

    @Autowired
    public EnrollmentController(EnrollmentService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<Collection<EnrollmentDTO>> listAll() {
        return status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> findById(@PathVariable int id) {
        try{
            return status(HttpStatus.OK).body(service.findById(id));
        } catch (EnrollmentNotFoundException e) {
            return status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    @PostMapping("")
    public ResponseEntity<EnrollmentDTO> add(@RequestBody EnrollmentDTO e) {
        try{
            return status(HttpStatus.CREATED).body(service.add(e));
        } catch (CourseNotFoundException | StudentNotFoundException x) {
            return status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> update(@PathVariable int id, @RequestBody EnrollmentDTO e) {
        e.setId(id);
        try {
            service.update(e);
            return status(HttpStatus.NO_CONTENT).body(null);
        } catch (EnrollmentNotFoundException x) {
            return status(HttpStatus.CREATED).body(service.add(e));
        } catch (StudentNotFoundException | CourseNotFoundException x) {
            return status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EnrollmentDTO> delete(@PathVariable int id) {
        try{
            return status(HttpStatus.OK).body(service.delete(id));
        }catch (EnrollmentNotFoundException e) {
            return status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
