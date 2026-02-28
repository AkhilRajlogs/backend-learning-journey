package exceptionhandling;

public class MultipleCatchDemo {

    public static void main(String[] args) {

        try {
            String text = null;
            System.out.println(text.length());  // NullPointerException

            int result = 10 / 0;               // ArithmeticException (won't execute)
            System.out.println(result);

        } catch (NullPointerException e) {
            System.out.println("Null value encountered.");
        } catch (ArithmeticException e) {
            System.out.println("Arithmetic error occurred.");
        }

        System.out.println("Handled successfully.");
    }
}
