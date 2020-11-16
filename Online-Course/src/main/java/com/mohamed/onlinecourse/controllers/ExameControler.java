package com.mohamed.onlinecourse.controllers;

import com.mohamed.onlinecourse.entities.Exame;
import com.mohamed.onlinecourse.entities.User;
import com.mohamed.onlinecourse.repositories.CourseRepository;
import com.mohamed.onlinecourse.repositories.ExameRepository;
import com.mohamed.onlinecourse.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ExameControler {
    final CourseRepository courseRepository;
    final ExameRepository exameRepository;
    final UserRepository userRepository;


    public ExameControler(CourseRepository courseRepository, ExameRepository exameRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.exameRepository = exameRepository;
        this.userRepository = userRepository;
    }
    @RequestMapping(value = "/saveExameByCourseName/{courseName}",produces = MediaType.APPLICATION_JSON_VALUE,method= RequestMethod.POST)
    ResponseEntity<?>saveExamenation(@PathVariable("courseName")String courseName, @RequestBody Exame exame){
        return courseRepository.findCourseByCourseName(courseName).map(course -> {
            course.setExame(exame);
            exame.setCourse(course);
            return new ResponseEntity<>(exameRepository.save(exame), HttpStatus.CREATED);
        }).orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
    @RequestMapping(value = "/bookToExameByToken",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    ResponseEntity<?>saveToExame(@RequestParam("token")String token,@RequestParam("exameId")Long id){
        return exameRepository.findById(id).map(exame -> {
            Optional<User>optionalUser=userRepository.findUserByConfirmationToken(token);
            exame.setUser(optionalUser.get());
            return new ResponseEntity<>(exameRepository.save(exame),HttpStatus.CREATED);
        }).orElse(ResponseEntity.noContent().build());
    }
}
