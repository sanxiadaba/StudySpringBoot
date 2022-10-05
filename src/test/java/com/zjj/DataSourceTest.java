package com.zjj;


import com.zjj.Info.DataBaseInfo;
import com.zjj.config.MySQLFileInit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class DataSourceTest {


    @Autowired
    DataBaseInfo dataBaseInfo;

    @Autowired
    MySQLFileInit mySQLFileInit;


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