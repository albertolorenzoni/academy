package com.al.academyspring.exception;

import java.util.NoSuchElementException;

public class StudentNotFoundException extends NoSuchElementException {
    public StudentNotFoundException(String s) {
        super(s);
    }
}
