package com.al.academyspring.DTO;

import com.al.academyspring.model.Enrollment;
import com.al.academyspring.model.Student;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
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
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dateOfBirth;
    private String education;
    private List<EnrollmentDTO> enrollments;

}
