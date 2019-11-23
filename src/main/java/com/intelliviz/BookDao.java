package com.intelliviz;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BookDao {
    private Map<String, Book> books;

    BookDao() {
        books = new ConcurrentHashMap<String, Book>();
    }

    Collection<Book> getBooks() {
        return books.values();
    }

    Book getBook(String id) {
        return books.get(id);
    }

    Book addBook(Book book) {
        book.setId(UUID.randomUUID().toString());
        books.put(book.getId(), book);
        return book;
    }
}
