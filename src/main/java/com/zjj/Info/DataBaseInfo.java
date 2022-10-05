package com.zjj.Info;
// 获取连接池以及数据库的信息
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DataBaseInfo {
    // 连接池名字
    private String type;

    // 数据库连接驱动
    @Value("${spring.datasource.driver-class-name}")
    private String drive;

    // 数据库名字
    private String name;

    // 地址
    private String url;

    // 用户名
    private String username;

    // 密码
    private String password;
}
