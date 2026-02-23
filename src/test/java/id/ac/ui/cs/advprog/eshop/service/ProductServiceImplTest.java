package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void testCreate() {
        Product product = new Product();
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);

        Product result = productService.create(product);

        assertNotNull(result.getProductId());
        assertEquals("Sampo Cap Bambang", result.getProductName());
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAll() {
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        productList.add(product);

        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);

        List<Product> result = productService.findAll();

        assertEquals(1, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        when(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6")).thenReturn(product);

        Product result = productService.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");

        assertEquals(product.getProductId(), result.getProductId());
        verify(productRepository, times(1)).findById(anyString());
    }

    @Test
    void testUpdate() {
        Product product = new Product();
        when(productRepository.update(product)).thenReturn(product);

        Product result = productService.update(product);

        assertEquals(product, result);
        verify(productRepository, times(1)).update(product);
    }

    @Test
    void testDelete() {
        String productId = "eb558e9f-1c39-460e-8860-71af6af63bd6";

        productService.deleteById(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }
}