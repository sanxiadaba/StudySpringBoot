package com.zjj.dao;

import com.zjj.pojo.Book;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BookDao {
    List<Book> allBook();
}
