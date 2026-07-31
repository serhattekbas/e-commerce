package org.example.inventoryms.repository;

import java.util.Optional;

import org.example.inventoryms.model.inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface inventoryRepository extends JpaRepository<inventory, Long> {
    Optional<inventory> findByProductId(Long productId);
}
