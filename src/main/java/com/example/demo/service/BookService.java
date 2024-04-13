package com.example.demo.service;

import org.springframework.web.bind.annotation.RequestBody;
import com.example.demo.model.Book;
import java.util.ArrayList;
import java.util.List;

public class BookService {

    private List<Book> bookList = new ArrayList<>();

    public List<Book> getAllBooks() {
        return bookList;
    }

    public static Book addBook(@RequestBody Book book) {
        return BookService.addBook(book);
    }

}
