package com.zjj.controller;

import com.zjj.annotation.OptLog;
import com.zjj.pojo.Book;
import com.zjj.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BookController {

    private BookService bookService;

    // set方法注入
    @Autowired
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @OptLog(optType = "所有书籍")
    @RequestMapping("/getAllBook")
    public List<Book> getAllBook(){
        String name="小明";
        return bookService.allBook();
    }
}
