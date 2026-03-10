package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create")
    public String createOrderPage(Model model) {
        return "orderCreate";
    }

    @PostMapping("/create")
    public String createOrderPost(@RequestParam String author, @RequestParam(required = false, defaultValue = "Produk Default") String productName) {

        Product dummyProduct = new Product();
        dummyProduct.setProductId(UUID.randomUUID().toString());
        dummyProduct.setProductName(productName);
        dummyProduct.setProductQuantity(1);

        List<Product> dummyProductList = new ArrayList<>();
        dummyProductList.add(dummyProduct);

        Order newOrder = new Order(UUID.randomUUID().toString(), dummyProductList, System.currentTimeMillis(), author);

        orderService.createOrder(newOrder);
        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String historyPage() {
        return "orderHistory";
    }

    @PostMapping("/history")
    public String historyPost(@RequestParam String author, Model model) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        return "orderList";
    }

    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "paymentForm";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrderPost(@PathVariable String orderId, @RequestParam String method, @RequestParam Map<String, String> paymentData, Model model) {
        Order order = orderService.findById(orderId);
        Payment payment = paymentService.addPayment(order, method, paymentData);
        model.addAttribute("paymentId", payment.getId());
        return "paymentSuccess";
    }
}