package hes.example.bookstore.utility;

import hes.example.bookstore.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MailConstructor {
    @Autowired
    private Environment environment;

    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user, String password) {
        String url = contextPath + "/newUser?token=" + token;
        String message = ("\nPlease click on this link to verify your email and edit your personal information." +
                "\nYour password is: ").concat(password);
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Le's Bookstore - User Credentials");
        email.setText(url+message);
        email.setFrom(environment.getProperty("support.email"));
        return email;
    }
}
