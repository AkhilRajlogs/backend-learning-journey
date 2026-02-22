package oops.payment;

public class WalletPayment extends BasePayment {
    
    private double walletBalance = 12000; 
    
    public WalletPayment() {
        super("TXN1003");
    }

    @Override
    protected void processPayment(double amount){
        System.out.println("Available Wallet balance : Rs: " + walletBalance );

        if (amount <= walletBalance) {
                   
            System.out.println("Making wallet payment of Rs. " + amount);
            System.out.println("Payment Successful");
            walletBalance -= amount;
            System.out.println("Available Wallet balance : Rs: " + walletBalance );

        } else {
            System.out.println("Payment failed! Insufficient balance");
        }
    }
    
}
