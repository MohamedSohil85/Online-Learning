package com.mohamed.onlinecourse.repositories;


import com.mohamed.onlinecourse.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {

    Optional<User>findRegistretedUserByUserNameAndPassword(String username,String password);
    User findRegistretedUserByUserName(String username);
    User findUserByEmail(String email);
    Optional<User> findUserByConfirmationToken(String token);
    Optional<User>findByLastNameAndFirstName(String lastName,String firstName);

}