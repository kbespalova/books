package com.example.demo.controller;

import com.example.demo.service.BookService;
import com.example.demo.model.Book;
import com.example.demo.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
public class BookController {

    @Autowired
    private ExcelService excelService;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return excelService.getAllBooks();
    }

    @PostMapping("/books")
    public void addBook(@RequestBody Book book) throws IOException {
        String title = book.getTitle();
        String author = book.getAuthor();
        double price = book.getPrice();
        excelService.addBook(title, author, price);
    }
}
