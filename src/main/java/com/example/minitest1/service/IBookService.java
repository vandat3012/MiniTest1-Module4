package com.example.minitest1.service;

import com.example.minitest1.model.Book;

import java.util.List;

public interface IBookService {
    List<Book> findAll();
    void save (Book book);
    Book findById(int id);
    void edit (Book book);
    void delete (int id);
}
