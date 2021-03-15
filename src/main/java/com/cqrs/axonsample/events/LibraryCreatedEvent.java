package com.cqrs.axonsample.events;

import lombok.Data;

@Data
public class LibraryCreatedEvent {
    private final Integer libraryId;
    private final String name;
}
