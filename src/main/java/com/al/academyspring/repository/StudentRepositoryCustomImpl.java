package com.al.academyspring.repository;

import com.al.academyspring.model.Student;

import javax.persistence.TypedQuery;
import java.util.Collection;

public class StudentRepositoryCustomImpl implements StudentRepositoryCustom{
    @Override
    public Collection<Student> complexSearch(TypedQuery<Student> typedQuery) {
        return typedQuery.getResultList();
    }
}
