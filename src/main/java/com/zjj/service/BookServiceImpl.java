package com.zjj.service;

import com.zjj.dao.BookDao;
import com.zjj.pojo.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService{

    private final BookDao bookDao;

    // 构造方法注入
    public BookServiceImpl(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    @Override
    public List<Book> allBook() {
        System.out.println("查询全部");
        return bookDao.allBook();
    }
}
