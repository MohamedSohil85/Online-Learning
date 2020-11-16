package com.mohamed.onlinecourse.repositories;


import com.mohamed.onlinecourse.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role,Long> {
}
