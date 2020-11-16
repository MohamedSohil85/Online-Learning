package com.mohamed.onlinecourse.SecurityConfig;

import com.mohamed.onlinecourse.entities.User;
import com.mohamed.onlinecourse.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user=userRepository.findRegistretedUserByUserName(s);
        CustomUserDetails userDetails = null;

        if(user !=null){
            userDetails=new CustomUserDetails();
            userDetails.setUser(user);
        }else {
            throw new UsernameNotFoundException("User Object not found !");
        }
        return userDetails;
    }
}

