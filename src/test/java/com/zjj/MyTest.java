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
                // ????????????utf-8 ???????????????
                Files.newInputStream(Paths.get("C:\\Code\\Github\\StudySpringBoot\\src\\main\\resources\\ssm_book.sql")), StandardCharsets.UTF_8))) {
            // ??????????????????????????????
            String tempString;
            // ?????????????????????????????????flag???????????????????????????????????????
            // ??????0????????????
            int flag = 0;
            // ?????????????????????????????????null???????????????
            while ((tempString = reader.readLine()) != null) {
                // ????????????
                if (tempString.trim().equals(""))
                    continue;
                // ??????????????????
                if (tempString.trim().startsWith("--") || tempString.trim().startsWith("#")){
                    continue;
                }
                // ???????????????
                if (tempString.endsWith(";")) {
                    // ?????????????????????
                    if (flag == 1) {
                        // ???????????????
                        sb.append(tempString);
                        listStr.add(sb.toString());
                        // ???????????????
                        sb.delete(0, sb.length());
                        flag = 0;
                    }
                    // ???????????????????????????
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
        // ??????sql??????
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
