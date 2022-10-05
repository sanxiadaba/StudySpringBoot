package com.zjj;

import com.zjj.pojo.DateEntity;
import com.zjj.utility.FormatDate;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
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

    @Test
    public void test04() throws Exception {
        ArrayList<String> strings = readFileByLines();
        System.out.println(strings.size());
        for (String sql:strings){
            System.out.println(sql);
        }
    }

    @Test
    public void test05(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection= DriverManager.getConnection("jdbc:mysql://localhost/mysql?serverTimezone=Asia/Shanghai&allowMultiQueries=true&rewriteBatchedStatements=true", "root", "123456")){
            connection.setAutoCommit(false);
            Statement st = connection.createStatement();
            ArrayList<String> sqls = readFileByLines();
            for (String sql : sqls) {
                System.out.println(sql);
                st.execute(sql);
                connection.commit();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private ArrayList<String> readFileByLines() throws Exception {
        ArrayList<String> listStr=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                // 这里是以utf-8 格式读取的
                Files.newInputStream(Paths.get("C:\\Code\\Github\\StudySpringBoot\\src\\main\\resources\\ssm_book.sql")), StandardCharsets.UTF_8))) {
            // 读取指定目录下的文件
            String tempString;
            // 读取文件的基本逻辑是，flag判断缓冲区里面是否还有语句
            // 等于0代表没有
            int flag = 0;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 空行跳过
                if (tempString.trim().equals(""))
                    continue;
                // 注释行，跳过
                if (tempString.trim().startsWith("--") || tempString.trim().startsWith("#")){
                    continue;
                }
                // 结尾是分号
                if (tempString.endsWith(";")) {
                    // 缓冲区还有数据
                    if (flag == 1) {
                        // 加上原来的
                        sb.append(tempString);
                        listStr.add(sb.toString());
                        // 清空缓冲区
                        sb.delete(0, sb.length());
                        flag = 0;
                    }
                    // 没有数据，直接添加
                    else
                        listStr.add(tempString);
                } else {
                    flag = 1;
                    sb.append(tempString);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
        // 打印sql语句
        // for (String str:listStr){
        //     System.out.println(str);
        // }
        return listStr;
    }

    @Test
    public void test06(){
        Date date = new Date();
        System.out.println(FormatDate.format(date));
    }
}
