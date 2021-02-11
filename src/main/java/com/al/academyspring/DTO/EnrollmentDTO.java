package com.al.academyspring.DTO;

import com.al.academyspring.model.Enrollment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class EnrollmentDTO {

    private int id;
    private int studentId;
    private String studentName;
    private int courseId;
    private String courseName;
    private Integer studentRating;
    private Integer courseRating;

    public EnrollmentDTO(Enrollment e) {
        this.id = e.getId();
        this.studentId = e.getStudent().getId();
        this.studentName = e.getStudent().getFirstName() + " " + e.getStudent().getLastName();
        this.courseId = e.getCourse().getId();
        this.courseName = e.getCourse().getName();
        this.studentRating = e.getStudentRating();
        this.courseRating = e.getCourseRating();
    }

}
