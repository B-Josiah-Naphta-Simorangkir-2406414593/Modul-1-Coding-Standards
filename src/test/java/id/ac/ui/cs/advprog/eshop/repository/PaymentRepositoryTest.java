package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        payment = new Payment("pay-1", "VOUCHER_CODE", "SUCCESS", new HashMap<>(), new Order("ord-1", null, 1L, "Josiah"));
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
}