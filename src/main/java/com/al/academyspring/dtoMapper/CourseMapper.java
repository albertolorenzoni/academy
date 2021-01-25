package com.al.academyspring.dtoMapper;

import com.al.academyspring.DTO.CourseDTO;
import com.al.academyspring.model.Course;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {EnrollmentMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR )
public interface CourseMapper {

    @Mapping(target = "prefClassroomName",
            expression = "java(c.getPrefClassroom() == null ? null : c.getPrefClassroom().getName())")
    @Mapping(target = "fundingCompanyName",
            expression = "java(c.getFundingCompany() == null ? null : c.getFundingCompany().getName())")
    @Mapping(target = "fundingAgencyName",
            expression = "java(c.getFundingAgency() == null ? null : c.getFundingAgency().getName())")
    CourseDTO mapToDto(Course c);



    @Mapping(target = "prefClassroom", ignore = true)
    @Mapping(target = "fundingCompany", ignore = true)
    @Mapping(target = "fundingAgency", ignore = true)
    @Mapping(target = "enrollments", ignore = true)
    Course mapDtoToCourse(CourseDTO courseDTO);
}
