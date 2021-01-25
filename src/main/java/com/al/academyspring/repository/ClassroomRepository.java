package com.al.academyspring.repository;

import com.al.academyspring.model.Classroom;
import com.al.academyspring.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface ClassroomRepository extends JpaRepository<Classroom, Integer> {

    @Query("select c from Classroom c where c.capacity >= ?1")
    List<Classroom> findCandidateClassrooms(int capacity);
}
