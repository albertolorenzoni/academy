package com.al.academyspring.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class LessonDTO {
    private int id;
    private int courseId;
    private String courseName;
    private int classroomId;
    private String classroomName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
