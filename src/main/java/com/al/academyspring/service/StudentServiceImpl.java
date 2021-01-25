package com.al.academyspring.service;

import com.al.academyspring.DTO.StudentDTO;
import com.al.academyspring.dtoMapper.StudentMapper;
import com.al.academyspring.exception.StudentNotFoundException;
import com.al.academyspring.model.Student;
import com.al.academyspring.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class StudentServiceImpl implements StudentService{

    private StudentRepository repo;
    private StudentMapper studentMapper;
    private EntityManager em;

    @Autowired
    public StudentServiceImpl(StudentRepository repo, StudentMapper studentMapper, EntityManager em) {
        this.repo = repo;
        this.studentMapper = studentMapper;
        this.em = em;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<StudentDTO> findAll() {
        return repo.findAll().stream().map(studentMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public StudentDTO findById(Integer id) {
        Student student = repo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id.toString()));
        return studentMapper.mapToDto(student);
    }

    @Override
    @Transactional
    public StudentDTO add(StudentDTO studentDTO) {
        Student addedStudent = repo.save(studentMapper.mapDtoToStudent(studentDTO));
        return studentMapper.mapToDto(addedStudent);
    }


    @Override
    @Transactional
    public StudentDTO update(StudentDTO studentDto) {
        Student oldStudent = repo.findById(studentDto.getId())
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + studentDto.getId()));
        return studentMapper.mapToDto(repo.save(studentMapper.mapDtoToStudent(studentDto)));
    }

    @Override
    @Transactional
    public StudentDTO delete(Integer id) {
        Student student = repo.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id.toString()));
        repo.delete(student);
        return studentMapper.mapToDto(student);

    }

    @Override
    @Transactional(readOnly = true)
    public Collection<StudentDTO> complexSearch(Map<String, String> params) {
        String queryString = "select s from Student s where ";
        boolean firstClause = true;
        if(params.containsKey("maxAge")) {
            queryString += "s.dateOfBirth > :compareDate ";
            firstClause = false;
        }
        if(params.containsKey("education")) {
            if (!firstClause) {
                queryString += "and ";
            }
            queryString += "s.education = :education ";
            firstClause = false;
        }
        if(params.containsKey("lastName")) {
            if (!firstClause) {
                queryString += "and ";
            }
            queryString += "lastName like :lastNameRegx ";
        }
        TypedQuery<Student> typedQuery = em.createQuery(queryString, Student.class);
        if(params.containsKey("maxAge")) {
            LocalDate compareDate = LocalDate.now().minusYears(Long.parseLong(params.get("maxAge")));
            typedQuery.setParameter("compareDate", compareDate);
        }
        if(params.containsKey("education")) {
            String education = params.get("education");
            typedQuery.setParameter("education", education);
        }
        if(params.containsKey("lastName")) {
            String lastName = params.get("lastName");
            typedQuery.setParameter("lastNameRegx", "%"+lastName+"%");
        }

        return repo.complexSearch(typedQuery).stream().map(studentMapper::mapToDto).collect(Collectors.toList());
    }
}
