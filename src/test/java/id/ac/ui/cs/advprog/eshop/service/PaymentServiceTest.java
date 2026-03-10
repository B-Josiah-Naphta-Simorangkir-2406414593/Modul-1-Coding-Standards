package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
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
        order = new Order("ord-1", null, 1L, "Josiah");
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
}
