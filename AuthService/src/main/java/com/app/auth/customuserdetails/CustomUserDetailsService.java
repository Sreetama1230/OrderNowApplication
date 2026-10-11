package com.app.auth.customuserdetails;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.app.auth.dao.UserDao;
import com.app.auth.entities.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userRepo;

    public CustomUserDetailsService(UserDao u) {
        this.userRepo = u;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

      
       GrantedAuthority authorities = new SimpleGrantedAuthority("ROLE_"+user.getRole().name());
        		

        return new CustomUserDetails(
                user.getUsername(),
                user.getPassword(),
                List.of(authorities),
                user.getId()
        );
    }
}
