package com.cqrs.axonsample.controller;

import com.cqrs.axonsample.aggregate.Library;
import com.cqrs.axonsample.command.RegisterBookCommand;
import com.cqrs.axonsample.command.RegisterLibraryCommand;
import com.cqrs.axonsample.model.Book;
import com.cqrs.axonsample.model.LibraryBean;
import com.cqrs.axonsample.queries.GetBooksQuery;
import com.cqrs.axonsample.queries.GetLibraryQuery;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class LibraryController {
    private final CommandGateway commandGateway;
    private final QueryGateway queryGateway;

    public LibraryController(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @PostMapping("/api/library")
    public String addLibrary(@RequestBody LibraryBean libraryBean) {
        commandGateway.send(new RegisterLibraryCommand(libraryBean.getLibraryId(), libraryBean.getName()));
        return "Saved";
    }

    @GetMapping("/api/library/{library}")
    public Library getLibrary(@PathVariable Integer library) throws InterruptedException, ExecutionException {
        CompletableFuture<Library> future = queryGateway.query(new GetLibraryQuery(library), Library.class);
        return future.get();
    }

    @PostMapping("/api/library/{library}/book")
    public String addBook(@PathVariable Integer library, @RequestBody Book book) {
        commandGateway.send(new RegisterBookCommand(library, book.getIsbn(), book.getTitle()));
        return "Saved";
    }

    @GetMapping("/api/library/{library}/book")
    public List<Book> addBook(@PathVariable Integer library) throws InterruptedException, ExecutionException {
        return queryGateway.query(new GetBooksQuery(library), ResponseTypes.multipleInstancesOf(Book.class)).get();
    }
}
