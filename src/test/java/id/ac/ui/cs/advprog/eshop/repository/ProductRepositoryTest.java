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

    @Test
    void testCreateWithNullId() {
        Product product = new Product();
        product.setProductName("Product Tanpa ID");
        product.setProductQuantity(10);

        productRepository.create(product);
        assertNotNull(product.getProductId());
        assertFalse(product.getProductId().isEmpty());
    }

    @Test
    void testEditProduct() {
        Product product = new Product();
        product.setProductId("id-lama");
        product.setProductName("Nama Lama");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("id-lama");
        updatedProduct.setProductName("Nama Baru");
        updatedProduct.setProductQuantity(20);

        productRepository.update(updatedProduct.getProductId(), updatedProduct);

        Iterator<Product> iterator = productRepository.findAll();
        Product result = iterator.next();
        assertEquals("Nama Baru", result.getProductName());
        assertEquals(20, result.getProductQuantity());
    }

    @Test
    void testFindByIdSuccess() {
        Product product = new Product();
        product.setProductId("id-123");
        productRepository.create(product);

        Product foundProduct = productRepository.findById("id-123");
        assertEquals(product.getProductId(), foundProduct.getProductId());
    }

    @Test
    void testFindByIdNotFound() {
        Product product = new Product();
        product.setProductId("id-123");
        productRepository.create(product);

        Product foundProduct = productRepository.findById("id-nyasar");
        assertNull(foundProduct);
    }

    @Test
    void testUpdateNotFound() {
        Product product = new Product();
        product.setProductId("id-ada");
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("id-tidak-ada");
        updatedProduct.setProductName("Nama Baru");

        Product result = productRepository.update(updatedProduct.getProductId(), updatedProduct);
        assertNull(result);
    }

    @Test
    void testDeleteById() {
        Product product = new Product();
        product.setProductId("id-123");
        productRepository.create(product);

        productRepository.deleteById("id-123");

        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }
}
