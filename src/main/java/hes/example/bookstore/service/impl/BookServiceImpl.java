package hes.example.bookstore.service.impl;

import hes.example.bookstore.domain.Book;
import hes.example.bookstore.repository.BookRepository;
import hes.example.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Value("${book.store.content}")
    private String bookContentStorePath;

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void saveBookImage(MultipartFile file, String imageName) {
        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(
                new File(bookContentStorePath.concat(imageName))))) {
            byte[] bytes = file.getBytes();
            stream.write(bytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> findAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }
}
