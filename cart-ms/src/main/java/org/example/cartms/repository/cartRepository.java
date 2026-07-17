package org.example.cartms.repository;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cartRepository extends JpaRepository<cartRepository,Long> {
}
