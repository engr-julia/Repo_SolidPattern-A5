# SOLID Refactoring: Order System

## Problem Description

The original implementation violates multiple SOLID principles:

### Issues Identified

1. Single Responsibility Principle (SRP) Violation

   * The `OrderAction` class handles multiple responsibilities:
     * Order calculation
     * Order placement
     * Invoice generation
     * Email notification

2. Interface Segregation Principle (ISP) Violation

   * The `Order` interface forces all implementing classes to define methods they may not need.

3. Open/Closed Principle (OCP) Violation

   * Adding new behaviors (e.g., SMS notification) requires modifying existing classes.

4. Dependency Inversion Principle (DIP) Violation

   * High-level modules depend directly on concrete implementations instead of abstractions.

---

## UML Class Diagram

![Order System UML](Order%20System.jpg)

---

## Refactored Design (Applying SOLID)

### Key Improvements

* Split large interface into smaller, specific interfaces
* Separate responsibilities into dedicated classes
* Use abstraction for flexibility and scalability
* Promote loose coupling

---

## ✅ Full Java Implementation (All-in-One)

> You can copy this into a single file named `OrderSystem.java` for quick testing.

```java
// =======================
// INTERFACES
// =======================

interface OrderCalculator {
    double calculateTotal(double price, int quantity);
}

interface OrderPlacer {
    void placeOrder(String customerName, String address);
}

interface InvoiceGenerator {
    void generateInvoice(String fileName);
}

interface NotificationService {
    void sendNotification(String message);
}

// =======================
// IMPLEMENTATIONS
// =======================

class BasicOrderCalculator implements OrderCalculator {

    @Override
    public double calculateTotal(double price, int quantity) {
        return price * quantity;
    }
}

class BasicOrderPlacer implements OrderPlacer {

    @Override
    public void placeOrder(String customerName, String address) {
        System.out.println("Order placed for " + customerName + " at " + address);
    }
}

class PDFInvoiceGenerator implements InvoiceGenerator {

    @Override
    public void generateInvoice(String fileName) {
        System.out.println("Invoice generated: " + fileName);
    }
}

class EmailNotificationService implements NotificationService {

    @Override
    public void sendNotification(String message) {
        System.out.println("Email sent: " + message);
    }
}

// =======================
// HIGH-LEVEL SERVICE (DIP)
// =======================

class OrderService {

    private OrderCalculator calculator;
    private OrderPlacer placer;
    private InvoiceGenerator invoiceGenerator;
    private NotificationService notificationService;

    public OrderService(OrderCalculator calculator,
                        OrderPlacer placer,
                        InvoiceGenerator invoiceGenerator,
                        NotificationService notificationService) {
        this.calculator = calculator;
        this.placer = placer;
        this.invoiceGenerator = invoiceGenerator;
        this.notificationService = notificationService;
    }

    public void processOrder(double price, int quantity, String name, String address) {
        double total = calculator.calculateTotal(price, quantity);
        System.out.println("Total: $" + total);

        placer.placeOrder(name, address);
        invoiceGenerator.generateInvoice("order.pdf");
        notificationService.sendNotification("Your order has been placed.");
    }
}

// =======================
// TEST CLASS (MAIN)
// =======================

public class OrderSystem {

    public static void main(String[] args) {

        OrderCalculator calculator = new BasicOrderCalculator();
        OrderPlacer placer = new BasicOrderPlacer();
        InvoiceGenerator invoice = new PDFInvoiceGenerator();
        NotificationService notification = new EmailNotificationService();

        OrderService orderService = new OrderService(
                calculator,
                placer,
                invoice,
                notification
        );

        orderService.processOrder(10.0, 2, "John Doe", "123 Main St");
    }
}
