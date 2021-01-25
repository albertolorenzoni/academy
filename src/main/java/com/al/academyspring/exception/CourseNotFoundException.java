package com.al.academyspring.exception;

import java.util.NoSuchElementException;

public class CourseNotFoundException extends NoSuchElementException {
    public CourseNotFoundException(String s) {
        super(s);
    }
}
