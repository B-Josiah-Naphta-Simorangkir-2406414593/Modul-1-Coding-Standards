package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private static final String METHOD_VOUCHER = "VOUCHER";
    private static final String METHOD_COD = "CASH_ON_DELIVERY";
    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_REJECTED = "REJECTED";

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        String paymentId = UUID.randomUUID().toString();
        String status = STATUS_REJECTED;

        if (METHOD_VOUCHER.equals(method)) {
            status = validateVoucher(paymentData) ? STATUS_SUCCESS : STATUS_REJECTED;
        } else if (METHOD_COD.equals(method)) {
            status = validateCashOnDelivery(paymentData) ? STATUS_SUCCESS : STATUS_REJECTED;
        }

        Payment payment = new Payment(paymentId, method, status, paymentData, order);
        paymentRepository.save(payment);
        return payment;
    }

    private boolean validateVoucher(Map<String, String> paymentData) {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode != null && voucherCode.length() == 16 && voucherCode.startsWith("ESHOP")) {
            int numCount = 0;
            for (char c : voucherCode.toCharArray()) {
                if (Character.isDigit(c)) numCount++;
            }
            return numCount == 8;
        }
        return false;
    }

    private boolean validateCashOnDelivery(Map<String, String> paymentData) {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");
        return address != null && !address.trim().isEmpty() && deliveryFee != null && !deliveryFee.trim().isEmpty();
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);

        // Menggunakan enum OrderStatus yang sudah ada
        if (STATUS_SUCCESS.equals(status)) {
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else if (STATUS_REJECTED.equals(status)) {
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }
}