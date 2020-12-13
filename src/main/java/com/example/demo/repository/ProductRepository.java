package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository <Product, Long>, JpaSpecificationExecutor {

    @Query(value = "Select * from Product where name like %?1%", countQuery = "Select count(*) from Product where name like %?1%", nativeQuery= true)
    Page<Product> findAllByQuery(String query, Pageable pageable);

    @Query(value = "Select * from Product where price < ?2", countQuery = "Select count(*) from Product where price < ?2", nativeQuery= true)
    Page<Product> findAllByMaxPrice(Double maxPrice, Pageable pageable);

    @Query(value = "Select * from Product where price > ?1", countQuery = "Select count(*) from Product where price > ?1", nativeQuery= true)
    Page<Product> findAllByMinPrice(Double minPrice, Pageable pageable);

    @Query(value = "Select * from Product where price > ?1 and price < ?2", countQuery = "Select count(*) from Product where price > ?1 and price < ?2", nativeQuery= true)
    Page<Product> findAllByMinMaxPrice(Double minPrice, Double maxPrice, Pageable pageable);
}
