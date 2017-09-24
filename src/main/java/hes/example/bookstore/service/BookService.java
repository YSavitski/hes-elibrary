package hes.example.bookstore.service;

import hes.example.bookstore.domain.Book;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BookService {
    Book save(Book book);

    void saveBookImage(MultipartFile file, String imageName);

    List<Book> findAllBooks();

    Book findOne(String bookId);
}
