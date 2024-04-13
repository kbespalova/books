package com.example.demo.service;

import com.example.demo.model.Book;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelService {

    private static final String FILE_NAME = "bookstore.xlsx";

    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        try (FileInputStream excelFile = new FileInputStream(FILE_NAME);
             Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String title = row.getCell(0).getStringCellValue();
                String author = row.getCell(1).getStringCellValue();
                double price = row.getCell(2).getNumericCellValue();
                Book book = new Book(title, author, price);
                bookList.add(book);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookList;
    }

    public void addBook(String title, String author, double price) throws IOException {
        try (FileInputStream excelFile = new FileInputStream(FILE_NAME);
             Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet sheet = workbook.getSheetAt(0);
            int rowCount = sheet.getLastRowNum();
            Row row = sheet.createRow(++rowCount);
            row.createCell(0).setCellValue(title);
            row.createCell(1).setCellValue(author);
            row.createCell(2).setCellValue(price);
            try (FileOutputStream outputStream = new FileOutputStream(FILE_NAME)) {
                workbook.write(outputStream);
            }
        }
    }


    public void updateBook(String oldTitle, String oldAuthor, String newTitle, String newAuthor, double newPrice) throws IOException {
        try (FileInputStream excelFile = new FileInputStream(FILE_NAME);
             Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String title = row.getCell(0).getStringCellValue();
                String author = row.getCell(1).getStringCellValue();
                if (title.equals(oldTitle) && author.equals(oldAuthor)) {
                    row.getCell(0).setCellValue(newTitle);
                    row.getCell(1).setCellValue(newAuthor);
                    row.getCell(2).setCellValue(newPrice);
                    break;
                }
            }
            try (FileOutputStream outputStream = new FileOutputStream(FILE_NAME)) {
                workbook.write(outputStream);
            }
        }
    }

    // Метод для видалення книги з Excel файлу
    public void deleteBook(String title, String author) throws IOException {
        try (FileInputStream excelFile = new FileInputStream(FILE_NAME);
             Workbook workbook = new XSSFWorkbook(excelFile)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                String cellTitle = row.getCell(0).getStringCellValue();
                String cellAuthor = row.getCell(1).getStringCellValue();
                if (cellTitle.equals(title) && cellAuthor.equals(author)) {
                    sheet.removeRow(row);
                    break;
                }
            }
            try (FileOutputStream outputStream = new FileOutputStream(FILE_NAME)) {
                workbook.write(outputStream);
            }
        }
    }

}
