package org.example.cartms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class CartMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartMsApplication.class, args);
    }

}
