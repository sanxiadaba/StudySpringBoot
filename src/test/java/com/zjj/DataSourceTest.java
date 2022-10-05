package com.zjj;


import com.zjj.Info.DataBaseInfo;
import com.zjj.config.MySQLFileInit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;


@SpringBootTest
public class DataSourceTest {

    @Autowired
    DataSource dataSource;

    @Autowired
    DataBaseInfo dataBaseInfo;

    @Autowired
    MySQLFileInit mySQLFileInit;


    @Test
    public void test01() throws SQLException {
        System.out.println(dataSource.getClass());
        System.out.println(dataSource.getConnection());
    }

    @Test
    public void test02(){
        System.out.println(dataBaseInfo);
        System.out.println(dataBaseInfo.getName());
    }

    @Test
    public void test03(){
        System.out.println(mySQLFileInit);
    }


}