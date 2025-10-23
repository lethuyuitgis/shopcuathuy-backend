package com.shopcuathuy;

import org.springframework.boot.SpringApplication;

/**
 * Main application class for ShopCuaThuy Backend
 * 
 * @author ShopCuaThuy Team
 * @version 1.0.0
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableCaching
@EnableAsync
@EnableScheduling
public class ShopCuaThuyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopCuaThuyBackendApplication.class, args);
    }
}
