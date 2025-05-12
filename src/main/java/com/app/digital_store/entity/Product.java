package com.app.digital_store.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String productName;

    @Enumerated(EnumType.STRING)
    private ProductType productType;

    private String productImagePath;
    private String productDownloadPath;
    private double productPrice;

    @Override
    public String toString() {
        return "Product{" +
                "name='" + productName + '\'' +
                ", type='" + productType + '\'' +
                ", imagePath='" + productImagePath + '\'' +
                ", price=" + productPrice +
                '}';
    }

}
