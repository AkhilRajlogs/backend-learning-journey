package oops.payment;

public class CreditCardPayment extends BasePayment {

    public CreditCardPayment() {
        super("TXN1001");        
    }

    @Override
    protected void processPayment(double amount) {
        System.out.println("Processing credit card payment of : " + amount);
        System.out.println("Charging card : 1234-XXXX");
        System.out.println(" Payment Successful");
    }

}
