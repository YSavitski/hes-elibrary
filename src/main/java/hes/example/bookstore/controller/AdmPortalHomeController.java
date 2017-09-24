package hes.example.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adminportal")
public class AdmPortalHomeController {
    private static final String admPath = "/adminportal";

    @RequestMapping(value = {"", "/", "/home"})
    public String index(){
        return "admportal-home";
    }
}
