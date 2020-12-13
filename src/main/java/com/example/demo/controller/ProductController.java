package com.example.demo.controller;

import com.example.demo.service.ProductService;
import lombok.AllArgsConstructor;
import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    public Page<Product> getProducts(@RequestParam(value="query", required = false)String query,
                                     @RequestParam(value="price", required = false)String price,
    Pageable pageable) {
        return service.getProducts(query, price, pageable);
    }


    @GetMapping("/list")
    public Page<Product> getProductList(SearchCriteria searchCriteria, Pageable pageable) {
     return service.getProductsBySpecification(searchCriteria, pageable);
    }
}
