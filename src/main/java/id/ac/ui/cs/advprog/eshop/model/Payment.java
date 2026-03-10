package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
public class Payment {
    private String id;
    private String method;
    @Setter
    private String status;
    private Map<String, String> paymentData;
    private Order order;

    public Payment(String id, String method, String status, Map<String, String> paymentData, Order order) {
        if (paymentData == null) {
            throw new IllegalArgumentException("Payment data cannot be null");
        }
        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
        this.order = order;
    }
}
