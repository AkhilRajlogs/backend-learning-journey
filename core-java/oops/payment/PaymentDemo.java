package oops.payment;

public class PaymentDemo {
    public static void main(String[] args) {
        PaymentMethod payment;
        payment = new CreditCardPayment();
        payment.pay(5000);

        payment = new UPIPayment();
        payment.pay(3000);
    }
        
}
