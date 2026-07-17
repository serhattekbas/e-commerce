package org.example.inventoryms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = "org.example.inventoryms")

public class InventoryMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryMsApplication.class, args);
    }

}
