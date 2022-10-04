package com.zjj.config;

import org.springframework.stereotype.Component;

@Component
public class TestConfig {
    {
        // spring会初始化创建每个bean
        System.out.println("匿名代码块");
    }
}
