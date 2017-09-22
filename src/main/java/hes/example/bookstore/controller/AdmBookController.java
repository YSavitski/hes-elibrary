package hes.example.bookstore.controller;

import hes.example.bookstore.domain.Book;
import hes.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/adminportal/books")
public class AdmBookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String getAllBooks(Model model){
        List<Book> books = bookService.findAllBooks();
        return "bookList";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String openAddBookForm(Model model){
        Book book = new Book();
        model.addAttribute("book", book);
        return "addBook";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addBook(
            @ModelAttribute("book") Book book,
            HttpServletRequest request)
    {
        bookService.save(book);
        String imageName = book.getId().toString().concat(".png");
        bookService.saveBookImage(book.getBookImage(), imageName);
        return "redirect:/adminportal/books";
    }
}
