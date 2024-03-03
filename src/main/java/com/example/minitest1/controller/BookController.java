package com.example.minitest1.controller;

import com.example.minitest1.model.Book;
import com.example.minitest1.model.BookForm;
import com.example.minitest1.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import java.io.File;
import java.io.IOException;
import java.util.List;


@Controller
@RequestMapping("/books")
@PropertySource("classpath:upload_file.properties")
public class BookController {
    @Value("${upload_file}")
    private String upload_file;
    @Autowired
    private IBookService iBookService;

    @GetMapping("")
    public String list(Model model) {
        List<Book> bookList = iBookService.findAll();
        model.addAttribute("books", bookList);
        return "list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("books", new BookForm());
        return "create";
    }

    @PostMapping("/save")
    public String save(BookForm bookForm) throws IOException {
        MultipartFile file = bookForm.getImg();
        String nameImg = file.getOriginalFilename();
        FileCopyUtils.copy(file.getBytes(), new File(upload_file + nameImg));
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setAuthor(bookForm.getAuthor());
        book.setPrice(bookForm.getPrice());
        book.setImg(nameImg);
        iBookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String update(@PathVariable int id, Model model) {
        model.addAttribute("books", iBookService.findById(id));
        return "edit";
    }

    @PostMapping("/edit")
    public String edit(BookForm bookForm) throws IOException {
        MultipartFile file = bookForm.getImg();
        String fileName = file.getOriginalFilename();
        FileCopyUtils.copy(file.getBytes(), new File(upload_file + fileName));
        Book book = new Book();
        book.setId(bookForm.getId());
        book.setName(bookForm.getName());
        book.setAuthor(bookForm.getAuthor());
        book.setPrice(bookForm.getPrice());
        book.setImg(fileName);
        iBookService.edit(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable int id, Model model) {
        model.addAttribute("books", iBookService.findById(id));
        return "delete";
    }

    @PostMapping("/delete")
    public String delete(Book book, RedirectAttributes redirect) {
        iBookService.delete(book.getId());
        redirect.addFlashAttribute("success", "Xóa thành công");
        return "redirect:/books";

    }

    @GetMapping("/{id}/view")
    public String view(@PathVariable int id, Model model) {
        model.addAttribute("books", iBookService.findById(id));
        return "view";
    }
}
