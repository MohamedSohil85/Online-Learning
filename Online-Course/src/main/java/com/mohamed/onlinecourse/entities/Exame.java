package com.mohamed.onlinecourse.entities;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Exame implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long exameId;
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "Europe/Berlin")
    @Temporal(TemporalType.DATE)
    private Date exameEnd;
    @JsonFormat(pattern = "yyyy-MM-dd" ,timezone = "Europe/Berlin")
    @Temporal(TemporalType.DATE)
    private Date exameStart;
    @OneToOne
    private Course course;
    @ManyToOne
    private User user;
    private String room;
    public Exame() {
    }

    public Long getExameId() {
        return exameId;
    }

    public void setExameId(Long exameId) {
        this.exameId = exameId;
    }

    public Date getExameEnd() {
        return exameEnd;
    }

    public void setExameEnd(Date exameEnd) {
        this.exameEnd = exameEnd;
    }

    public Date getExameStart() {
        return exameStart;
    }

    public void setExameStart(Date exameStart) {
        this.exameStart = exameStart;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
