package com.mohamed.onlinecourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
public class OnlinecourseApplication {



    public static void main(String[] args) {
        SpringApplication.run(OnlinecourseApplication.class, args);
    }


}
