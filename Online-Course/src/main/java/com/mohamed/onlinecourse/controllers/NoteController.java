package com.mohamed.onlinecourse.controllers;

import com.mohamed.onlinecourse.entities.Note;
import com.mohamed.onlinecourse.entities.User;
import com.mohamed.onlinecourse.repositories.ExameRepository;
import com.mohamed.onlinecourse.repositories.NoteRepository;
import com.mohamed.onlinecourse.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;


@RestController
public class NoteController {
    final NoteRepository noteRepository;
    final ExameRepository exameRepository;
    final UserRepository userRepository;

    public NoteController(NoteRepository noteRepository, ExameRepository exameRepository, UserRepository userRepository) {
        this.noteRepository = noteRepository;
        this.exameRepository = exameRepository;
        this.userRepository = userRepository;
    }
    @RequestMapping(value = "/Teacher/saveNote",produces = MediaType.APPLICATION_JSON_VALUE,method = RequestMethod.POST)
    ResponseEntity<?>saveNote(@RequestParam("exameId")Long exameId,@RequestParam("token")String token ,@RequestBody Note note){
        return exameRepository.findById(exameId).map(exame -> {
            Optional<User>userOptional=userRepository.findUserByConfirmationToken(token);
            note.setAppliedDate(new Date());
            exame.setUser(userOptional.get());
            note.setExame(exame);
            return new ResponseEntity(noteRepository.save(note), HttpStatus.CREATED);
        }).orElse(new ResponseEntity(HttpStatus.NOT_FOUND));
    }
}
