package com.zjj;

import com.zjj.pojo.DateEntity;
import com.zjj.utility.FormatDate;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class MyTest {

    @Test
    public void test01(){
        System.out.println(1);
    }

    @Test
    public void test02(){
        DateEntity dateEntity = new DateEntity();
        dateEntity.setDate(new Date());
        System.out.println(dateEntity.getDate());
    }

    @Test
    public void test03(){
        Date date = new Date();
        System.out.println(FormatDate.format(date));
    }
}
