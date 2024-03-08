package com.thinkstu;

import lombok.extern.slf4j.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.cache.annotation.*;
import org.springframework.transaction.annotation.*;

@Slf4j
@SpringBootApplication
@EnableTransactionManagement
@EnableCaching
public class Takeout {
    public static void main(String[] args) {
        SpringApplication.run(Takeout.class, args);
        log.info("=============成功运行=============");
    }

}
