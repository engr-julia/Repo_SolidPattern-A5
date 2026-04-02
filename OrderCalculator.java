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