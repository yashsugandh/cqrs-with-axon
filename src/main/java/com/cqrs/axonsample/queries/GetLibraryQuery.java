package com.cqrs.axonsample.queries;

import lombok.Data;

@Data
public class GetLibraryQuery {
    private final Integer libraryId;
}
