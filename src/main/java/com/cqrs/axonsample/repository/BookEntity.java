package com.cqrs.axonsample.repository;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class BookEntity {
    @Id
    private String isbn;
    @Column
    private int libraryId;
    @Column
    private String title;
}