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