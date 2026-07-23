package org.example.cartms.repository;

import org.example.cartms.model.shoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface cartRepository extends JpaRepository<shoppingCart,Long> {
    Optional<shoppingCart> findByProductId(Long productId);
}
