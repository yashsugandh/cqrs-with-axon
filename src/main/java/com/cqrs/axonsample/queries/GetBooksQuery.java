package com.cqrs.axonsample.queries;

import lombok.Data;

@Data
public class GetBooksQuery {
    private final Integer libraryId;
}
