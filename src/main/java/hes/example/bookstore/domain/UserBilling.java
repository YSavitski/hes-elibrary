package hes.example.bookstore.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Entity
@Data
@NoArgsConstructor
public class UserBilling implements Serializable {
    private static final long serialVersionUID = -1241979055599154100L;

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

    @OneToOne(cascade = CascadeType.ALL)
    private UserPayment userPayment;
}
