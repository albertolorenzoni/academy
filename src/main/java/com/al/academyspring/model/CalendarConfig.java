package com.al.academyspring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class CalendarConfig {

    private LocalDate startDate;
    private List<CalendarDaysPref> daysPrefs;
}
