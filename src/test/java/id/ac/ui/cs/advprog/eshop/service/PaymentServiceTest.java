package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    Order order;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Kecap");
        product.setProductQuantity(1);
        products.add(product);

        order = new Order("ord-1", products, 1L, "Josiah");
        order.setStatus("WAITING_PAYMENT");
    }

    @Test
    void testAddPaymentVoucherValid() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");
        doReturn(new Payment("p1", "VOUCHER", "SUCCESS", data, order)).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testAddPaymentVoucherInvalid() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP123");
        doReturn(new Payment("p1", "VOUCHER", "REJECTED", data, order)).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testAddPaymentCODValid() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "Fasilkom UI");
        data.put("deliveryFee", "10000");
        doReturn(new Payment("p1", "CASH_ON_DELIVERY", "SUCCESS", data, order)).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentService.addPayment(order, "CASH_ON_DELIVERY", data);
        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testAddPaymentCODInvalid() {
        Map<String, String> data = new HashMap<>();
        data.put("address", "");
        doReturn(new Payment("p1", "CASH_ON_DELIVERY", "REJECTED", data, order)).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentService.addPayment(order, "CASH_ON_DELIVERY", data);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusSuccessUpdatesOrder() {
        Payment payment = new Payment("p1", "VOUCHER", "WAITING_PAYMENT", new HashMap<>(), order);
        doReturn(payment).when(paymentRepository).save(payment);

        paymentService.setStatus(payment, "SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testAddPaymentVoucherInvalidPrefix() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "KUPON1234ABC5678");
        doReturn(new Payment("p1", "VOUCHER", "REJECTED", data, order)).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testAddPaymentVoucherInvalidNumCount() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC567D");
        doReturn(new Payment("p1", "VOUCHER", "REJECTED", data, order)).when(paymentRepository).save(any(Payment.class));

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);
        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusRejectedUpdatesOrder() {
        Payment payment = new Payment("p1", "VOUCHER", "WAITING_PAYMENT", new HashMap<>(), order);
        doReturn(payment).when(paymentRepository).save(payment);

        paymentService.setStatus(payment, "REJECTED");
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testGetPayment() {
        Payment payment = new Payment("p1", "VOUCHER", "SUCCESS", new HashMap<>(), order);
        doReturn(payment).when(paymentRepository).findById("p1");

        Payment result = paymentService.getPayment("p1");
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetAllPayments() {
        Payment payment = new Payment("p1", "VOUCHER", "SUCCESS", new HashMap<>(), order);
        List<Payment> paymentList = new ArrayList<>();
        paymentList.add(payment);
        doReturn(paymentList).when(paymentRepository).getAllPayments();

        List<Payment> result = paymentService.getAllPayments();
        assertEquals(1, result.size());
    }
}
