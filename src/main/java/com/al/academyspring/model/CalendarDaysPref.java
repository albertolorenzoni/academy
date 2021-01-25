package com.al.academyspring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CalendarDaysPref {

    private DayOfWeek day;
    private LocalTime startTime;
    private LocalTime endTime;
}
