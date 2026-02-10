package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        // kosong, repository masih in-memory
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();

        assertTrue(productIterator.hasNext());
        assertEquals(product1.getProductId(), productIterator.next().getProductId());
        assertEquals(product2.getProductId(), productIterator.next().getProductId());
        assertFalse(productIterator.hasNext());
    }

    // ================= EDIT PRODUCT =================

    @Test
    void editProduct_positiveCase() {
        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Laptop");
        product.setProductQuantity(10);

        productRepository.create(product);

        Product updated = new Product();
        updated.setProductId("1");
        updated.setProductName("Gaming Laptop");
        updated.setProductQuantity(5);

        productRepository.update(updated);

        Product result = productRepository.findById("1");
        assertEquals("Gaming Laptop", result.getProductName());
        assertEquals(5, result.getProductQuantity());
    }

    @Test
    void editProduct_negativeCase_productNotFound() {
        Product updated = new Product();
        updated.setProductId("999");
        updated.setProductName("Ghost Product");
        updated.setProductQuantity(1);

        assertThrows(
                IllegalArgumentException.class,
                () -> productRepository.update(updated)
        );
    }

    // ================= DELETE PRODUCT =================

    @Test
    void deleteProduct_positiveCase() {
        Product product = new Product();
        product.setProductId("2");
        product.setProductName("Mouse");
        product.setProductQuantity(3);

        productRepository.create(product);
        productRepository.deleteById("2");

        assertNull(productRepository.findById("2"));
    }

    @Test
    void deleteProduct_negativeCase_productNotFound() {
        assertThrows(
                IllegalArgumentException.class,
                () -> productRepository.deleteById("404")
        );
    }
}
