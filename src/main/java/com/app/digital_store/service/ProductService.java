package com.app.digital_store.service;

import com.app.digital_store.entity.Product;
import com.app.digital_store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(product -> System.out.println("Product: " + product.getProductName())); // Logging products
        return products;
    }

    public Optional<Product> findById(Integer id){
        return productRepository.findById(id);
    }

    public void deleteProductById(int id){
        productRepository.deleteById(id);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }
}
