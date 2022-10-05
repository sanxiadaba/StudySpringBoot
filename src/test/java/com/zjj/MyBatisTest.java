package com.zjj;


import com.zjj.dao.BookDao;
import com.zjj.pojo.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisTest {

    @Autowired
    BookDao bookDao;

    @Test
    public void test01(){
        for (Book book:bookDao.allBook()){
            System.out.println(book
            );
        }
    }
}
