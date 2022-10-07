package com.zjj;

import com.zjj.pojo.SpringUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringStudyApplicationTests {

    @Autowired
    SpringUser user1;

    SpringUser user2;

    SpringUser user3;

    @Autowired
    public SpringStudyApplicationTests(SpringUser user2) {
        System.out.println("构造方法注入");
        this.user2 = user2;
    }

    @Autowired
    public void setUser1(SpringUser user3) {
        System.out.println("setter方法注入");
        this.user3 = user3;
    }

    @Test
    void contextLoads() {
        System.out.println(user1);
        System.out.println(user2);
        System.out.println(user3);
        System.out.println("=============================");
        SpringStudyApplicationTests springStudyApplicationTests = new SpringStudyApplicationTests(new SpringUser());
        // 输出为null
        System.out.println(springStudyApplicationTests.user1);
    }

}
