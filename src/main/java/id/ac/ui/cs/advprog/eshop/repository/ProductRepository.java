package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String productId) {
        for (Product product : productData) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public void update(Product product) {
        for (Product p : productData) {
            if (p.getProductId().equals(product.getProductId())) {
                p.setProductName(product.getProductName());
                p.setProductQuantity(product.getProductQuantity());
                return;
            }
        }
        throw new IllegalArgumentException("Product not found");
    }

    public void deleteById(String id) {
        boolean removed = productData.removeIf(
                p -> p.getProductId().equals(id)
        );

        if (!removed) {
            throw new IllegalArgumentException("Product not found");
        }
    }



}