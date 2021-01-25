package com.al.academyspring.repository;

import com.al.academyspring.model.Classroom;
import com.al.academyspring.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;

public interface LessonRepository extends JpaRepository<Lesson, Integer> {
    @Query("select l from Lesson l  where l.classroom.id = ?3 and (l.startTime between ?1 and ?2" +
            "or l.endTime between ?1 and ?2 or (l.startTime < ?1 and l.endTime > ?2))")
    Collection<Lesson> overlappingLessons(LocalDateTime startDateTime, LocalDateTime endDateTime, int classroomId);
    Collection<Lesson> findAllByCourseId(int courseId);
}
