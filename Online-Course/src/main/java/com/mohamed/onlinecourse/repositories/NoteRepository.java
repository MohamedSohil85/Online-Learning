package com.mohamed.onlinecourse.repositories;

import com.mohamed.onlinecourse.entities.Note;
import org.springframework.data.repository.CrudRepository;

public interface NoteRepository extends CrudRepository<Note,Long> {

}
