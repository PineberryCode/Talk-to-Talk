package User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class User implements Runnable {

    private Socket user;
    private BufferedReader in;
    private PrintWriter out;
    private boolean done;

    @Override
    public void run() {
        try {
            user = new Socket("127.0.0.1", 5000);
            out = new PrintWriter(user.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(user.getInputStream()));

            InputHandler inputHandler = new InputHandler();
            Thread thread = new Thread(inputHandler);
            thread.start();

            String inMsg;
            while ((inMsg = in.readLine()) != null) {
                System.out.println(inMsg);
            }
        } catch (IOException e) {
            shutdown();
        }
    }

    public void shutdown() {
        done = true;
        try {
            in.close();
            out.close();
            if (!user.isClosed()) {
                user.close();
            }
        } catch (IOException e) {}
    }

    class InputHandler implements Runnable {

        @Override
        public void run() {
            try (BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in))) {
                while (!done) {
                    String msg = inReader.readLine();
                    if (msg.equals("--quit")) {
                        out.println(msg);
                        shutdown();
                    } else {
                        out.println(msg);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }
    }
    public static void main(String[] args) {
        User user = new User();
        user.run();
    }
}
