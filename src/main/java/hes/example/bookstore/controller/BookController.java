package hes.example.bookstore.controller;

import hes.example.bookstore.domain.Book;
import hes.example.bookstore.domain.User;
import hes.example.bookstore.service.BookService;
import hes.example.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.websocket.server.PathParam;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @RequestMapping("/bookshelf")
    public String browseBookshelf(Model model){
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("emptyList", books.isEmpty());
        model.addAttribute("books", books);
        return "bookshelf";
    }

    @RequestMapping("bookDetail")
    public String bookDetail(
            @PathParam("id") String id,
            Model model,
            Principal principal
    ){
        if(principal != null){
            String username = principal.getName();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }

        Book book = bookService.findOne(id);
        model.addAttribute("book", book);

        model.addAttribute("qtyList", Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        model.addAttribute("qty", 1);

        return "bookDetail";
    }
}
