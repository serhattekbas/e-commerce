package org.example.inventoryms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org.example.inventoryms")

public class InventoryMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryMsApplication.class, args);
    }

}
