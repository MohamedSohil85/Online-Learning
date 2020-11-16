package com.mohamed.onlinecourse.controllers;

import com.mohamed.onlinecourse.entities.EmailSenderService;
import com.mohamed.onlinecourse.entities.PasswordValidation;
import com.mohamed.onlinecourse.entities.Role;
import com.mohamed.onlinecourse.entities.User;
import com.mohamed.onlinecourse.repositories.RoleRepository;
import com.mohamed.onlinecourse.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
public class RegistretedUserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailSenderService emailSenderService;

    public RegistretedUserController(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, RoleRepository roleRepository, EmailSenderService emailSenderService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;

        this.emailSenderService = emailSenderService;
    }

    @RequestMapping(value = "/secure/getUsers", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List getUsers()  {
        List<User> users = (List) userRepository.findAll();

        return users;
    }

    @RequestMapping(value = "/saveNewUser", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity saveNewUser(@RequestBody User user) {

        List<User> users = (List) userRepository.findAll();
        for (User value : users) {
            if (value.getLastName().equalsIgnoreCase(user.getLastName()))
                if (value.getEmail().equalsIgnoreCase(user.getEmail()))
                    if (value.getUserName().equals(user.getUserName()))
                        return new ResponseEntity(HttpStatus.FOUND);
        }
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        String token= UUID.randomUUID().toString();
        user.setConfirmationToken(token);
        simpleMailMessage.setFrom("mohamedsohil1985@gmail.com");
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Confirm your Registration !");
        simpleMailMessage.setText("To continue your Registration ,click the following Link"+"http://localhost:8080/confirm-register?confirmationToken="+user.getConfirmationToken()+"\n " +
                "\n Notification : this secound Link  to registrate new Course "+"http://localhost:8080/confirm-register?confirmationToken="+user.getConfirmationToken());
        emailSenderService.sendEmail(simpleMailMessage);
        return new ResponseEntity(userRepository.save(user), HttpStatus.CREATED);
    }

    @RequestMapping(value = "/confirm-register",method =RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity confirmByToken(@RequestParam("confirmationToken")String confirmationToken,@RequestBody User user){
        return userRepository.findUserByConfirmationToken(confirmationToken).map(existUser -> {
            String password=user.getPassword();
            String encodePassword=passwordEncoder.encode(password);
            existUser.setPassword(encodePassword);
           existUser.setConfirmpassword(user.getConfirmpassword());
           return new ResponseEntity(userRepository.save(existUser),HttpStatus.CREATED);
        }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));


    }



    @RequestMapping(value = "/createRoleByUserId/{userId}",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity createRoleByUserId(@PathVariable("userId")Long userId, @RequestBody Role role){
        return userRepository.findById(userId).map(registretedUser -> {
            role.setUser(registretedUser);
            registretedUser.getRoleList().add(role);
            return new ResponseEntity(roleRepository.save(role),HttpStatus.CREATED);
        }).orElse(new ResponseEntity(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/deleteById/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUserName(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
    }


}

