package hes.example.bookstore.controller;

import hes.example.bookstore.domain.Book;
import hes.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("")
    public String browseBooks(Model model){
        List<Book> books = bookService.findAllBooks();
        model.addAttribute("emptyList", books.isEmpty());
        model.addAttribute("books", books);

        return "bookShelf";
    }
}
