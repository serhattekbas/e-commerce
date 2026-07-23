package org.example.cartms.feign;

import org.example.cartms.dto.InventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "inventory-ms")
public interface IInventoryClient {

    @GetMapping("/api/inventory/list")
    List<InventoryDto> listInventory();
}
