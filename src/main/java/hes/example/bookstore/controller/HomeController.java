package hes.example.bookstore.controller;

import hes.example.bookstore.domain.User;
import hes.example.bookstore.domain.UserBilling;
import hes.example.bookstore.domain.UserPayment;
import hes.example.bookstore.domain.UserShipping;
import hes.example.bookstore.domain.security.PasswordResetToken;
import hes.example.bookstore.domain.security.Role;
import hes.example.bookstore.domain.security.UserRole;
import hes.example.bookstore.service.UserService;
import hes.example.bookstore.service.impl.UserSecurityService;
import hes.example.bookstore.utility.MailConstructor;
import hes.example.bookstore.utility.SecurityUtility;
import hes.example.bookstore.utility.USConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserSecurityService userSecurityService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    /*@RequestMapping("/myAccount")
    public String myAccount(){
        return "myAccount";
    }*/

    @RequestMapping(value = "/login")
    public String login(Model model){
        model.addAttribute("classActiveLogin", true);
        return "myAccount";
    }

    @RequestMapping(value = "/forgetPassword")
    public String ForgetPassword(
            HttpServletRequest request,
            @ModelAttribute("email") String email,
            Model model){
        User user = userService.findByEmail(email);
        model.addAttribute("classActiveForgetPassword", true);

        if(user == null){
            model.addAttribute("emailNotExist", true);
            return "myAccount";
        }

        String password = SecurityUtility.randomPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        userService.save(user);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://"
                .concat(request.getServerName())
                .concat(":")
                .concat(Integer.toString(request.getServerPort()))
                .concat(request.getContextPath());

        SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(
                appUrl,  request.getLocale(), token, user, password);

        mailSender.send(newEmail);

        model.addAttribute("forgetPasswordEmailSent", true);
        return "myAccount";
    }

    @RequestMapping("/myProfile")
    public String myProfile(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        /*model.addAttribute("orderList", user.getOrderList());*/

        UserShipping userShipping = new UserShipping();
        model.addAttribute("userShipping", userShipping);

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("listOfShippingAddresses", true);

        List<String> stateList = USConstants.listOfUSStatesCode;
        Collections.sort(stateList);
        model.addAttribute("stateList", stateList);
        model.addAttribute("classActiveEdit", true);

        return "myProfile";
    }

    @RequestMapping("/listOfCreditCards")
    public String listOfCreditCards(Model model, Principal principal, HttpServletRequest request){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        /*model.addAttribute("orderList", user.getOrderList());*/

        model.addAttribute("listOfCreditCards", true);
        model.addAttribute("classActiveBilling", true);
        model.addAttribute("listOfShippingAddresses", true);

        return "myProfile";
    }

    @RequestMapping("/addNewCreditCard")
    public String addNewCreditCard(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("addNewCreditCard", true);
        model.addAttribute("classActiveBilling", true);

        UserBilling userBilling = new UserBilling();
        UserPayment userPayment = new UserPayment();

        model.addAttribute("userBilling", userBilling);
        model.addAttribute("userPayment", userPayment);

        List<String> stateList = USConstants.listOfUSStatesCode;
        Collections.sort(stateList);
        model.addAttribute("StateList", stateList);

        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        /*model.addAttribute("orderList", user.getOrderList());*/

        return "myProfile";
    }

    @RequestMapping("/addNewShippingAddress")
    public String addNewShippingAddress(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("addNewShippingAddress", true);
        model.addAttribute("classActiveShipping", true);

        UserShipping userShipping = new UserShipping();
        model.addAttribute("userShipping", userShipping);

        List<String> stateList = USConstants.listOfUSStatesCode;
        Collections.sort(stateList);
        model.addAttribute("StateList", stateList);

        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        /*model.addAttribute("orderList", user.getOrderList());*/

        return "myProfile";
    }

    @RequestMapping("/listOfShippingAddresses")
    public String listOfShippingAddresses(Model model, Principal principal, HttpServletRequest request){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userPaymentList", user.getUserPaymentList());
        model.addAttribute("userShippingList", user.getUserShippingList());
        /*model.addAttribute("orderList", user.getOrderList());*/

        return "myProfile";
    }

    @RequestMapping(value = "/newUser", method = RequestMethod.POST)
    public String newUserPost(
            HttpServletRequest request,
            @ModelAttribute("email") String userEmail,
            @ModelAttribute("username") String username,
            Model model
    ){
        model.addAttribute("classActiveNewUser", true);
        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);

        if(userService.findByUsername(username) != null){
            model.addAttribute("usernameExists", true);
            return "myAccount";
        }

        if(userService.findByEmail(userEmail) != null){
            model.addAttribute("emailExists", true);
            return "myAccount";
        }

        User user = new User();
        user.setUsername(username);
        user.setEmail(userEmail);
        user.setCreated(LocalDateTime.now());

        String password = SecurityUtility.randomPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassword(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1);
        role.setName("ROLE_USER");
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));

        userService.createUser(user, userRoles);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://"
                .concat(request.getServerName())
                .concat(":")
                .concat(Integer.toString(request.getServerPort()))
                .concat(request.getContextPath());

        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(
                appUrl,  request.getLocale(), token, user, password);

        mailSender.send(email);
        model.addAttribute("emailSent", true);
        return "myAccount";
    }



    @RequestMapping(value = "/newUser")
    public String newUser(
            Locale locale,
            @RequestParam("token") String token,
            Model model){
        PasswordResetToken passToken = userService.getPasswordResetToken(token);
        if(passToken == null){
            model.addAttribute("message", "Invalid Token.");
            return "redirect:/badRequest";
        }

        User user = passToken.getUser();
        String userName = user.getUsername();
        UserDetails userDetails = userSecurityService.loadUserByUsername(userName);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("classActiveEdit", true);
        model.addAttribute("user", user);
        return "myProfile";
    }
}
