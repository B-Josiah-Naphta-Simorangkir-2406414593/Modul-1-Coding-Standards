package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Kecap");
        product.setProductQuantity(1);
        products.add(product);

        Order order = new Order("ord-1", products, 1L, "Josiah");
        payment = new Payment("pay-1", "VOUCHER_CODE", "SUCCESS", new HashMap<>(), order);
    }

    @Test
    void testSaveAndFindById() {
        paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById("pay-1");
        assertNotNull(findResult);
        assertEquals(payment.getId(), findResult.getId());
    }

    @Test
    void testGetAllPayments() {
        paymentRepository.save(payment);
        assertEquals(1, paymentRepository.getAllPayments().size());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById("zczc");
        assertNull(findResult);
    }
}