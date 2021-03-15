package com.cqrs.axonsample.repository;

import com.cqrs.axonsample.events.BookCreatedEvent;
import com.cqrs.axonsample.model.Book;
import com.cqrs.axonsample.queries.GetBooksQuery;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookRepositoryProjector {

    private final BookRepository bookRepository;

    public BookRepositoryProjector(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @EventHandler
    public void addBook(BookCreatedEvent event) throws Exception {
        BookEntity book = new BookEntity();
        book.setIsbn(event.getIsbn());
        book.setLibraryId(event.getLibraryId());
        book.setTitle(event.getTitle());
        bookRepository.save(book);
    }

    @QueryHandler
    public List<Book> getBooks(GetBooksQuery query) {
        return bookRepository.findByLibraryId(query.getLibraryId()).stream().map(toBook()).collect(Collectors.toList());
    }

    private Function<BookEntity, Book> toBook() {
        return e -> new Book(e.getIsbn(), e.getTitle());
    }
}
