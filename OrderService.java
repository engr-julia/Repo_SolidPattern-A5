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