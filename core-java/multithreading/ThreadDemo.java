package multithreading;

public class ThreadDemo extends Thread {

    @Override
    public void run() {

        for (int i = 1; i <= 5; i++) {
            System.out.println("Child Thread: " + i);
        }

    }


    public static void main(String[] args) {

        ThreadDemo thread = new ThreadDemo();
        thread.start();

        for (int i = 1; i <= 5; i++) {
            System.out.println("Main Thread: " + i);
        }

    }

}



/*Output sample obtained on running:

Child Thread: 1
Child Thread: 2
Main Thread: 1
Main Thread: 2
Child Thread: 3
Main Thread: 3
Main Thread: 4
Main Thread: 5
Child Thread: 4
Child Thread: 5

*/