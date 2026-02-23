package oops.payment;

public class CreditCardPayment extends BasePayment implements Refundable {

    public CreditCardPayment() {
        super();        
    }

    @Override
    protected void processPayment(double amount) {
        System.out.println("Processing credit card payment of : " + amount);
        System.out.println("Charging card : 1234-XXXX");
        System.out.println(" Payment Successful");
    }

    @Override
    public void refund(double amount) {
        
        System.out.println("Refund accepted. \nProcessing credit card refund of : " + amount);
        
    }

}
