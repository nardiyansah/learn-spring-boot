package learn.spring.boot.nardiyansah.repository;

import learn.spring.boot.nardiyansah.model.Book;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
