package com.al.academyspring.exception;

import java.util.NoSuchElementException;

public class LessonNotFoundException extends NoSuchElementException {
    public LessonNotFoundException(String s) {
        super(s);
    }
}
