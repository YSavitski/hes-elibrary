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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service("bookService")
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Value("${book.store}")
    private String bookContentStorePath;

    @Override
    public Book save(Book book) {
        if(!book.getBookImage().isEmpty()){
            book.setHasImage(true);
        } else {
            book.setHasImage(false);
        }

        Book savedBook = bookRepository.save(book);
        if(savedBook.isHasImage()){
            saveBookImage(book.getBookImage(), savedBook.getId());
        }
        return savedBook;
    }

    private void checkDirectoriesAndFiles(String imageName){
        Path pathToBookDirectory = Paths.get(bookContentStorePath);

        try {
            if(Files.notExists(pathToBookDirectory)){
                Files.createDirectories(pathToBookDirectory);
            } else {
                Files.deleteIfExists(Paths.get(bookContentStorePath.concat(imageName)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveBookImage(MultipartFile file, String bookId) {
        String imageName = bookId.concat(".png");
        checkDirectoriesAndFiles(imageName);

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

    @Override
    public Book findOne(String bookId) {
        return bookRepository.findOne(bookId);
    }
}
