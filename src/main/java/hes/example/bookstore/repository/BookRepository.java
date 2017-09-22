package hes.example.bookstore.repository;

import hes.example.bookstore.domain.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<Book, String> {
}
