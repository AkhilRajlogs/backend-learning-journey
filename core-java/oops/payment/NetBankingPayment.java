package oops.payment;

public class NetBankingPayment extends BasePayment {
    
    public NetBankingPayment() {
        super(); // This is implicit, but for better readability while my learning phase, I prefer to keep it.
    }

    
    @Override
    protected void processPayment(double amount){
        System.out.println("Processing Net Banking payment of: " + amount);
        System.out.println("Connecting to bank server...");
        System.out.println("Payment Successful");
    }

}
