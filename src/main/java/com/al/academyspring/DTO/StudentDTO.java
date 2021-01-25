package com.al.academyspring.DTO;

import com.al.academyspring.model.Enrollment;
import com.al.academyspring.model.Student;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class StudentDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private String education;
    private List<EnrollmentDTO> enrollments;

}
