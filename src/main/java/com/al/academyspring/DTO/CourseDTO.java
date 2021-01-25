package com.al.academyspring.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter @Setter
public class CourseDTO {

    private int id;
    private String name;
    private String description;
    private int minEnrollments;
    private int capacity;
    private boolean funded;
    private String prefClassroomName;
    private int totalHours;
    private String fundingCompanyName;
    private String fundingAgencyName;
    private List<EnrollmentDTO> enrollments;
}
