package com.example.minitest1.model;

import org.springframework.web.multipart.MultipartFile;

public class BookForm {
    private int id;
    private String name;
    private String author;
    private double price;
    private MultipartFile img;

    public BookForm() {
    }

    public BookForm(String name, String author, double price, MultipartFile img) {
        this.name = name;
        this.author = author;
        this.price = price;
        this.img = img;
    }

    public MultipartFile getImg() {
        return img;
    }

    public void setImg(MultipartFile img) {
        this.img = img;
    }

    public BookForm(int id, String name, String author, double price, MultipartFile img) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.price = price;
        this.img = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
