package learn.spring.boot.nardiyansah.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Book {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    private String author;

    private Integer publishedYear;

}
