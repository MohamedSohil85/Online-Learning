package com.mohamed.onlinecourse.controllers;

import com.mohamed.onlinecourse.entities.Department;
import com.mohamed.onlinecourse.repositories.DepartmentRepositroy;
import com.mohamed.onlinecourse.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DepartmentController {

    private final DepartmentRepositroy departmentRepositroy;
    private final UserRepository userRepository;

    public DepartmentController(DepartmentRepositroy departmentRepositroy, UserRepository userRepository) {
        this.departmentRepositroy = departmentRepositroy;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/Department",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity saveNewDepartment(@RequestBody Department department){

     return new ResponseEntity(departmentRepositroy.save(department), HttpStatus.CREATED);
    }
    @RequestMapping(value = "/Departments",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity getDepartments(){
        List<Department> departmentList=(List)departmentRepositroy.findAll();
        return new ResponseEntity(departmentList,HttpStatus.OK);
    }
    @RequestMapping(value = "/savetoUserById/{id}/Department",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity saveUserToDepartment(@PathVariable("id")Long id,@RequestBody Department department){
        return userRepository.findById(id).map(user -> {
            user.setDepartment(department);
            department.getUserList().add(user);
            return new  ResponseEntity(departmentRepositroy.save(department),HttpStatus.CREATED);
        }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }

}
