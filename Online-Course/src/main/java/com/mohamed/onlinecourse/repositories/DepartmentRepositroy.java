package com.mohamed.onlinecourse.repositories;

import com.mohamed.onlinecourse.entities.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepositroy extends CrudRepository<Department,Long> {


}
