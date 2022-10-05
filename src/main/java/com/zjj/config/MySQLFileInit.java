package com.zjj.config;
// 初始化运行数据库
// 注意，resource目录下的sql文件名要和连接的数据库名相等
import com.zjj.Info.DataBaseInfo;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;


@DependsOn("dataBaseInfo")
@Component
@Data
public class MySQLFileInit {

    // 先将数据库的相关信息拿到
    DataBaseInfo dataBaseInfo;

    // 对属性赋值
    private  String databaseName;
    // 驱动
    private  String driver;
    // 要连接的地址
    private  String url;
    // 用户名
    private  String username;
    // 密码
    private  String password;

    // 注入进来,并且赋值
    @Autowired
    public MySQLFileInit(DataBaseInfo dataBaseInfo) {
        this.dataBaseInfo=dataBaseInfo;
        this.databaseName=dataBaseInfo.getName();
        this.driver=dataBaseInfo.getDrive();
        this.url=dataBaseInfo.getUrl();
        this.username=dataBaseInfo.getUsername();
        this.password=dataBaseInfo.getPassword();
    }


    // 如果检查到数据库中没有对应的数据库表
    // 那就向数据库中自动执行sql文件
    // 实例化后执行
    @PostConstruct
    private void checkDatabase()  {
        System.out.println("正在检查要连接数据库是否存在");
        // 注意将sql文件放在resources文件夹下,核心还是想获取项目目录
        String userDir= String.valueOf(Objects.requireNonNull(MySQLFileInit.class.getResource("/")).getPath());
        // 获取到sql文件的目录
        String path=userDir+databaseName+".sql";
        path=path.substring(1);
        // 查看全部的数据库
        String sql="show databases";
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        // 在最开始我们连接的是mysql这个数据库,先通过它先来运行命令
        try (Connection connection= DriverManager.getConnection(url.replace(databaseName,"mysql"), username, password)){
            Statement st= connection.createStatement();
            ResultSet rs=st.executeQuery(sql);
            // 列表里面存储的是当前数据库里所有数据库的名字集合
            ArrayList<String> allDatabase=new ArrayList<>();
            while(rs.next())
            {
                allDatabase.add(rs.getString(1));
            }
            // 如果原来的数据库里没有这个数据库
            if (!allDatabase.contains(databaseName)){
                System.out.println("数据库不存在，开始自动生成");
                try {
                    runSqlFile(path);
                }catch (Exception e){
                    System.out.println("运行sql文件失败");
                    e.printStackTrace();
                    return;
                }
                // 初始化数据库完成
                System.out.println("创建数据库完成");
            }else {
                System.out.println("数据库已存在");
            }
            // 数据库准备完毕
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void runSqlFile(String path) throws Exception {
        // 先读取sql文件，使用; 分割存到一个列表里
        ArrayList<String> sqls=readFileByLines(path);
        // 再批量执行
//        System.out.println(sqls);
        for (String sql:sqls){
            System.out.println(sql);
        }
        batchDate(sqls);

    }

    // 批量执行sql文件
    private void batchDate(ArrayList<String> sqls) throws SQLException {
        System.out.println("开始批量执行sql");
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection connection=DriverManager.getConnection(url.replace(databaseName,"mysql"), username, password)){
            connection.setAutoCommit(false);
            Statement st = connection.createStatement();
            for (String sql : sqls) {
                System.out.println(sql);
                st.execute(sql);
                connection.commit();
            }
        }
    }

    // 读取文件,以分号（;）为分割，将sql添加到一个列表里，之后再读取
    private  ArrayList<String> readFileByLines(String filePath) throws Exception {
        System.out.println("正在解析"+databaseName+".sql文件");
        ArrayList<String> listStr=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                // 这里是以utf-8 格式读取的
            Files.newInputStream(Paths.get(filePath)), StandardCharsets.UTF_8))) {
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

}