package learn.spring.boot.nardiyansah.controller;

import learn.spring.boot.nardiyansah.model.Book;
import learn.spring.boot.nardiyansah.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @PostMapping("/add")
    public String add(
            @RequestParam String title,
            @RequestParam String author,
            @RequestParam Integer publishedYear
    ) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublishedYear(publishedYear);
        bookRepository.save(book);
        return "new book saved";
    }

    @GetMapping("/all")
    public Iterable<Book> getAll() {
        return bookRepository.findAll();
    }
}
