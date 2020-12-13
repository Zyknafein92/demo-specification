package com.example.demo.model;

import lombok.*;


import javax.persistence.*;
import java.util.stream.IntStream;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Enumerated(EnumType.STRING)
    private ProductType type;
    private double price;

    //A ne pas faire !!

    public static void main(String[] args) {
        IntStream.range(1, 150).forEach(i -> {
            String name = "Product" + i;
            ProductType type = ProductType.values()[i%ProductType.values().length];
            double price = Math.random() * 100 * i;

            System.out.println("INSERT INTO Product (id, name, type, price) values (" + i + ", '" + name + "', '" + type.toString() + "', " + price + ");");
        });
    }
}
