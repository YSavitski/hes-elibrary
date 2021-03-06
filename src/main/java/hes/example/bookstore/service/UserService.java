package hes.example.bookstore.service;

import hes.example.bookstore.domain.User;
import hes.example.bookstore.domain.security.PasswordResetToken;
import hes.example.bookstore.domain.security.UserRole;

import java.util.Set;

public interface UserService {
    PasswordResetToken getPasswordResetToken(final String token);

    void createPasswordResetTokenForUser(final User user, final String token);

    User findByUsername(String username);

    User findByEmail(String email);

    User createUser(User user, Set<UserRole> userRoles) throws RuntimeException;

    User save(User user);
}
