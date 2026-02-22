package oops.payment;

public class UPIPayment extends BasePayment {

    public UPIPayment() {
        super();        
    }

    @Override
    protected void processPayment(double amount) {
        System.out.println("Processing UPI payment of : " + amount);
        System.out.println("Sending to UPI: akhil@upi");
        System.out.println(" Payment Successful");
    }

}
