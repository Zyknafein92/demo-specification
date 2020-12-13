package com.example.demo.controller;

import com.example.demo.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ProductSpecification {

    public Specification<Product> priceSpecification(Double minPrice, Double maxPrice) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if(minPrice != null && maxPrice != null) return criteriaBuilder.between(root.get("price"), minPrice, maxPrice);
            if(minPrice != null) return criteriaBuilder.lessThanOrEqualTo(root.get("price"), minPrice);
            if(maxPrice != null) return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), maxPrice);
            return criteriaBuilder.and();//tjrs vrai
            //return criteriaBuilder.or() tjrs faux
        };
    }

    public Specification<Product> nameSpecification(String name) {
        return (root, criteria, criteriaBuilder) -> {
            if(name != null) return criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + name.toUpperCase() + "%");

            return criteriaBuilder.and();
        };
    }

    public Specification<Product> querySpecification(String query) {
        if(query == null) return (root, criteriaQuery, criteriaBuilder) ->   criteriaBuilder.and();

        Specification<Product> typeSpecfication = (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("type")), "%" + query.toUpperCase() + "%");

        Specification<Product> descriptionSpecification = (root, criteriaQuery, criteriaBuilder) ->
         criteriaBuilder.like(criteriaBuilder.upper(root.get("description")), "%" + query.toUpperCase() + "%");

        return typeSpecfication.or(descriptionSpecification);
    }
}
