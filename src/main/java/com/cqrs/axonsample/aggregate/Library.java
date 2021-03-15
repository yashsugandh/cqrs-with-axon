package com.cqrs.axonsample.aggregate;

import com.cqrs.axonsample.command.RegisterBookCommand;
import com.cqrs.axonsample.command.RegisterLibraryCommand;
import com.cqrs.axonsample.events.BookCreatedEvent;
import com.cqrs.axonsample.events.LibraryCreatedEvent;
import lombok.Getter;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.util.Assert;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.ArrayList;
import java.util.List;

@Aggregate
@Getter
public class Library {

    //It is used to identify the aggregate that is being targeted by the command
    @AggregateIdentifier
    private Integer libraryId;

    private String name;

    private List<String> isbnBooks;

    protected Library() {
        // For Axon instantiation
    }

    @CommandHandler
    public Library(RegisterLibraryCommand registerLibraryCommand) {
        Assert.notNull(registerLibraryCommand.getLibraryId(), "ID should not be null");
        Assert.notNull(registerLibraryCommand.getName(), "Name should not be null");

        AggregateLifecycle.apply(new LibraryCreatedEvent(registerLibraryCommand.getLibraryId(), registerLibraryCommand.getName()));
    }

    @CommandHandler
    public void addBook(RegisterBookCommand registerBookCommand) {
        Assert.notNull(registerBookCommand.getLibraryId(), "ID should not be null");
        Assert.notNull(registerBookCommand.getIsbn(), "Book ISBN should not be null");

        AggregateLifecycle.apply(new BookCreatedEvent(registerBookCommand.getLibraryId(), registerBookCommand.getIsbn(), registerBookCommand.getTitle()));
    }

    @EventSourcingHandler
    private void handleCreatedEvent(LibraryCreatedEvent event) {
        libraryId = event.getLibraryId();
        name = event.getName();
        isbnBooks = new ArrayList<>();
    }

    @EventSourcingHandler
    private void addBook(BookCreatedEvent event) {
        isbnBooks.add(event.getIsbn());
    }
}
