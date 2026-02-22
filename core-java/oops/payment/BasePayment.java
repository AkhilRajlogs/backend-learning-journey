package oops.payment;

public abstract class BasePayment implements PaymentMethod {
    protected String transactionId;
    private static int counter = 1;

    public BasePayment() {
        this.transactionId = "TXN00" + counter++;
    }

    public final void pay(double amount) {
        processPayment(amount);
        printTransactionId();
    }


    public void printTransactionId() {
        System.out.println("Transaction ID: " + transactionId);
    }

    protected abstract void processPayment(double amount);

}
