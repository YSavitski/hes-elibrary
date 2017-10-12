package hes.example.bookstore.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class UserShipping implements Serializable{
    private static final long serialVersionUID = 872834951369823192L;

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "book_id")
    private String id;

    private String name;

    private String street1;

    private String street2;

    private String city;

    private String state;

    private String country;

    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
