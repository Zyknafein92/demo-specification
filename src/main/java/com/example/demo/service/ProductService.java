package com.example.demo.service;

import com.example.demo.controller.ProductSpecification;
import com.example.demo.controller.SearchCriteria;
import com.example.demo.repository.ProductRepository;
import lombok.AllArgsConstructor;
import com.example.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final ProductSpecification specification;

    public Page<Product> getProducts(String query, String price, Pageable pageable) {
        if(query != null && price != null){
            return repository.findAllByQuery(query, pageable);
        }
        if(query != null) {
            return repository.findAllByQuery(query, pageable);
        }

        if(price != null) {
            String[] prices = price.split("-");
            Double minPrice = convertForPrice(prices[0]);
            Double maxPrice = convertForPrice(prices[1]);
            if(minPrice != null && maxPrice != null) {
                return repository.findAllByMinMaxPrice(minPrice, maxPrice, pageable);
            }
            if(maxPrice != null) {
                return repository.findAllByMaxPrice(maxPrice, pageable);
            }
            return repository.findAllByMinPrice(minPrice, pageable);
        }

        return repository.findAll(pageable);
    }

    private Double convertForPrice(String price) {
        try {
            return Double.parseDouble(price);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    // Favoriser cette méthode

    public Page<Product> getProductsBySpecification(SearchCriteria searchCriteria, Pageable pageable) {
        Specification<Product> priceSpecification = specification.priceSpecification(searchCriteria.getMinPrice(),searchCriteria.getMaxPrice());
        Specification<Product> nameSpecification = specification.nameSpecification(searchCriteria.getName());
        Specification<Product> querySpecification = specification.querySpecification(searchCriteria.getQuery());
        Specification<Product> totalSpecification = priceSpecification.and(nameSpecification).and(querySpecification);
        return repository.findAll(totalSpecification,pageable);
    }



}
