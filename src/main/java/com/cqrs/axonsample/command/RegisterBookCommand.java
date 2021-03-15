package com.cqrs.axonsample.command;

import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
public class RegisterBookCommand {
    @TargetAggregateIdentifier
    private final Integer libraryId;
    private final String isbn;
    private final String title;
}
