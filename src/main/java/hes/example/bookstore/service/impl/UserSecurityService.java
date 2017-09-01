package hes.example.bookstore.service.impl;

import hes.example.bookstore.domain.User;
import hes.example.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityService  implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //User user = userRepository.findByUsername(username);
        User user = userRepository.findByUsername(username);
        if(user == null){
            throw new UsernameNotFoundException(String.format("Username \'%s\' not found", username));
        }
        return user;
    }
}
