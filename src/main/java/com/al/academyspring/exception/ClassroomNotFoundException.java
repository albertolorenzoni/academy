package com.al.academyspring.exception;

import java.util.NoSuchElementException;

public class ClassroomNotFoundException extends NoSuchElementException {
    public ClassroomNotFoundException(String s) {
        super(s);
    }
}
