package com.mohamed.onlinecourse.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
@Entity
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Long courseId;
   private String courseName;
   @Enumerated(EnumType.STRING)
   private CourseStatus courseStatus;
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "Europe/Berlin")
    @Temporal(TemporalType.DATE)
    private Date courseBegin;
    private String semster;
    private String courseLanguage;
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "Europe/Berlin")
    @Temporal(TemporalType.DATE)
    private Date courseEnd;
   @ManyToOne
   private User user;
    private String room;
   @OneToOne
    private Exame exame;
   @ManyToOne
    private Department department;

    public Course() {
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public CourseStatus getCourseStatus() {
        return courseStatus;
    }

    public void setCourseStatus(CourseStatus courseStatus) {
        this.courseStatus = courseStatus;
    }

    public Date getCourseBegin() {
        return courseBegin;
    }

    public void setCourseBegin(Date courseBegin) {
        this.courseBegin = courseBegin;
    }

    public Date getCourseEnd() {
        return courseEnd;
    }

    public void setCourseEnd(Date courseEnd) {
        this.courseEnd = courseEnd;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Exame getExame() {
        return exame;
    }

    public void setExame(Exame exame) {
        this.exame = exame;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getSemster() {
        return semster;
    }

    public void setSemster(String semster) {
        this.semster = semster;
    }
}
