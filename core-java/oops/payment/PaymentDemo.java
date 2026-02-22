package oops.payment;

public class PaymentDemo {
    public static void main(String[] args) {
        PaymentMethod[] payments = {
            new CreditCardPayment(),
            new UPIPayment(),
            new WalletPayment(), 
            new NetBankingPayment()
        };

        for (PaymentMethod payment : payments) {
            payment.pay(2000);
            System.out.println("----------------------");
        }
    }
        
}
