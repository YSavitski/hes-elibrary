package hes.example.bookstore.service.impl;

import hes.example.bookstore.domain.User;
import hes.example.bookstore.domain.security.PasswordResetToken;
import hes.example.bookstore.domain.security.UserRole;
import hes.example.bookstore.repository.PasswordResetTokenRepository;
import hes.example.bookstore.repository.RoleRepository;
import hes.example.bookstore.repository.UserRepository;
import hes.example.bookstore.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service("userService")
public class UserServiceImpl implements UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);


    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws RuntimeException {
        User localUser = userRepository.findByUsername(user.getUsername());

        if(localUser != null){
            log.info("User {} already exists. Nothing will be done.", localUser.getUsername());
            return null;
        } else {
            userRoles.forEach(userRole -> roleRepository.save(userRole.getRole()));
            user.getUserRoles().addAll(userRoles);
            localUser = userRepository.save(user);
            return localUser;
        }
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
