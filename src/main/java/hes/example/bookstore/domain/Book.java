package hes.example.bookstore.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book {
    @Id
    /*@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;*/
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "book_id")
    private String id;
    private String title;

    @Column(columnDefinition = "text")
    private String description;
    private String author;
    private String publisher;
    private String publicationDate;
    private String language;
    private String category;
    private int numberOfPages;
    private String format;
    private int isbn;
    private double shippingWeight;
    private BigDecimal listPrice = BigDecimal.valueOf(0.00);
    private BigDecimal ourPrice = BigDecimal.valueOf(0.00);
    private boolean active=true;
    private int inStockNumber;
    private boolean hasImage;

    @Transient
    private MultipartFile bookImage;
}
