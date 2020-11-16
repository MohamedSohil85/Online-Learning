package com.mohamed.onlinecourse.controllers;

import com.mohamed.onlinecourse.entities.Course;
import com.mohamed.onlinecourse.repositories.CourseRepository;
import com.mohamed.onlinecourse.repositories.UserRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class CourseController {
    final CourseRepository courseRepository;
    final UserRepository userRepository;

    public CourseController(CourseRepository courseRepository, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }
    @RequestMapping(value = "/course",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity saveCourse(@RequestBody Course course){
        Optional<Course>optionalCourse=courseRepository.findCourseByCourseName(course.getCourseName());
        if(optionalCourse.isPresent()){
            return new ResponseEntity(HttpStatus.FOUND);
        }
        return new ResponseEntity(courseRepository.save(course),HttpStatus.CREATED);
    }
    @RequestMapping(value = "/Courses",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Course> getCourses(){
        List<Course>courses=(List)courseRepository.findAll();
        return courses;
    }
    @RequestMapping(value = "/getCourseByDate",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity findCoursesByDate(@RequestParam("courseBegin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date courseBegin){
    List<Course>Courses=courseRepository.findCoursesByCourseBegin(courseBegin);
    if(Courses.isEmpty()){
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity(Courses,HttpStatus.OK);
    }
    @RequestMapping(value = "/getCourseByCourseBeginn",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity getCourseBycourseBegin(@RequestParam("courseBegin") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)Date courseBegin) {
        List<Course>courses=(List)courseRepository.findAll();
        Optional<Course>optionalCourse=courses.stream()
                .filter(course -> course.getCourseBegin().equals(courseBegin))
                .findFirst();
        return optionalCourse.isPresent()? new ResponseEntity(optionalCourse.get(),HttpStatus.OK) : new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    @RequestMapping(value = "/registerCourseByToken",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    ResponseEntity<?>registerCourseByToken(@RequestParam("confirmationToken")String confirmationToken, @RequestParam("courseName")String courseName){
        return userRepository.findUserByConfirmationToken(confirmationToken).map(user -> {
            Optional<Course>courseOptional=courseRepository.findCourseByCourseName(courseName);
            user.getCourses().add(courseOptional.get());
            courseOptional.get().setUser(user);
            return new ResponseEntity<>(courseRepository.save(courseOptional.get()),HttpStatus.CREATED);
        }).orElse(ResponseEntity.noContent().build());

    }
    @RequestMapping(value = "/registerToSemsterByToken",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    ResponseEntity<?>registertoSemster(@RequestParam("confirmationToken")String confirmationToken,@RequestBody String semster ){
        return userRepository.findUserByConfirmationToken(confirmationToken).map(user -> {
            List courses=courseRepository.findCoursesBySemster(semster);
            user.getCourses().addAll(courses);
            return new ResponseEntity<>(userRepository.save(user),HttpStatus.CREATED);
        }).orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
    }


}
