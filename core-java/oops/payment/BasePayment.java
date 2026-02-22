package oops.payment;

public abstract class BasePayment implements PaymentMethod {
    protected String transactionId;

    public BasePayment(String transactionId) {
        this.transactionId = transactionId;
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
