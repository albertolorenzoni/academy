package com.al.academyspring.repository;

import com.al.academyspring.model.Student;

import javax.persistence.TypedQuery;
import java.util.Collection;

public interface StudentRepositoryCustom {
    Collection<Student> complexSearch(TypedQuery<Student> typedQuery);
}
