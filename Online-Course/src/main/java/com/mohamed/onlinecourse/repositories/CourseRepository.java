package com.mohamed.onlinecourse.repositories;

import com.mohamed.onlinecourse.entities.Course;
import com.mohamed.onlinecourse.entities.DepartmentName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findCoursesByCourseBegin(Date coursebegin);
    Optional<Course>findCourseByCourseName(String name);
    List findCoursesByDepartment_DepartmentName(DepartmentName departmentName);
    List findCoursesBySemster(String semster);


}
