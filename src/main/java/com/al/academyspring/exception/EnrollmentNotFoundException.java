package com.al.academyspring.exception;

import java.util.NoSuchElementException;

public class EnrollmentNotFoundException extends NoSuchElementException {
    public EnrollmentNotFoundException(String s) {
        super(s);
    }
}
