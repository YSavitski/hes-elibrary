package hes.example.bookstore.controller;

import hes.example.bookstore.domain.Book;
import hes.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        model.addAttribute("books", books);
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
        return "redirect:/adminportal/books";
    }

    @RequestMapping("/bookInfo")
    public String bookInfo(@RequestParam("id") String id, Model model){
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        return "bookInfo";
    }

    @RequestMapping("/edit")
    public String editBook(@RequestParam("id") String id, Model model){
        Book book = bookService.findOne(id);
        model.addAttribute("book", book);
        return "editBook";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateBook(@ModelAttribute("book") Book book){
        bookService.save(book);
        return "redirect:/adminportal/books/bookInfo?id=".concat(book.getId());
    }
}
