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

## Refactored Design (Applying SOLID)

### Key Improvements

* Split large interface into smaller, specific interfaces
* Separate responsibilities into dedicated classes
* Use abstraction for flexibility and scalability
* Promote loose coupling

---

## Refactored Interfaces

```java
public interface OrderCalculator {
    double calculateTotal(double price, int quantity);
}

public interface OrderPlacer {
    void placeOrder(String customerName, String address);
}

public interface InvoiceGenerator {
    void generateInvoice(String fileName);
}

public interface NotificationService {
    void sendNotification(String message);
}
```

---

## Refactored Implementations

```java
public class BasicOrderCalculator implements OrderCalculator {
    @Override
    public double calculateTotal(double price, int quantity) {
        return price * quantity;
    }
}

public class BasicOrderPlacer implements OrderPlacer {
    @Override
    public void placeOrder(String customerName, String address) {
        System.out.println("Order placed for " + customerName + " at " + address);
    }
}

public class PDFInvoiceGenerator implements InvoiceGenerator {
    @Override
    public void generateInvoice(String fileName) {
        System.out.println("Invoice generated: " + fileName);
    }
}

public class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification(String message) {
        System.out.println("Email sent: " + message);
    }
}
```

---

## High-Level Order Service (DIP Applied)

```java
public class OrderService {

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
```

---

## Test Class

```java
public class OrderTest {
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
```

---

## Summary of SOLID Application

| Principle | Implementation                                            |
| --------- | --------------------------------------------------------- |
| SRP       | Each class has one responsibility                         |
| ISP       | Interfaces are segregated                                 |
| OCP       | New features can be added without modifying existing code |
| DIP       | High-level modules depend on abstractions                 |

---

## Key Takeaway

This refactoring makes the system:

* More scalable
* Easier to maintain
* Flexible for future extensions (e.g., SMS notifications, different invoice formats)
