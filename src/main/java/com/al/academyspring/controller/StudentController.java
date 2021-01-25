package com.al.academyspring.controller;

import com.al.academyspring.DTO.StudentDTO;
import com.al.academyspring.exception.StudentNotFoundException;
import com.al.academyspring.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    private StudentService service;

    @Autowired
    public StudentController(StudentService service) {
        this.service = service;
    }

    @GetMapping("")
    public ResponseEntity<Collection<StudentDTO>> listAll() {
        return status(HttpStatus.OK).body(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> findById(@PathVariable int id) {
        try{
            return status(HttpStatus.OK).body(service.findById(id));

        } catch (StudentNotFoundException e) {
            return status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<StudentDTO>> complexSearch(@RequestParam Map<String, String> params) {
        return status(HttpStatus.OK).body(service.complexSearch(params));
    }

    @PostMapping("")
    public ResponseEntity<StudentDTO> add(@RequestBody StudentDTO s) {
        return status(HttpStatus.CREATED).body(service.add(s));
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable int id, @RequestBody StudentDTO s) {
        s.setId(id);
        try {
            service.update(s);
            return status(HttpStatus.NO_CONTENT).body(null);
        } catch (StudentNotFoundException e) {
            return status(HttpStatus.CREATED).body(service.add(s));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<StudentDTO> delete(@PathVariable int id) {
        try{
            return status(HttpStatus.OK).body(service.delete(id));
        }catch (StudentNotFoundException e) {
            return status(HttpStatus.NOT_FOUND).body(null);
        }
    }


}
