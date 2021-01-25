package com.al.academyspring.repository;

import com.al.academyspring.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer>, StudentRepositoryCustom {
}
