package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {
    private ArrayList<ConnectionHandler> userConnections;
    private ServerSocket server;
    private boolean done;
    private ExecutorService pool;
    protected int PORT = 5000;

    public Server () {
        userConnections = new ArrayList<>();
        done = false;
    }

    @Override
    public void run() {
        try {
            server = new ServerSocket(PORT);
            pool = Executors.newCachedThreadPool();
            while (!done) {
                Socket user = server.accept();
                ConnectionHandler handler = new ConnectionHandler(user);
                userConnections.add(handler);
                pool.execute(handler);
            }
        } catch (Exception e) {shutdown();}
    }

    public void broadcast(String msg) {
        for (ConnectionHandler connectionHandler : userConnections) {
            if (connectionHandler != null) {
                connectionHandler.sendMessage(msg);
            }
        }
    }

    public void shutdown() {
        done = true;
        try {
            pool.shutdown();
            if (!server.isClosed()) {
                server.close();
            }
            for (ConnectionHandler connectionHandler : userConnections) {
                connectionHandler.shutdown();
            }
        } catch (IOException e) {}
    }

    public class ConnectionHandler implements Runnable {

        private Socket user;
        private BufferedReader in;
        private PrintWriter out;
        private String nickname;

        public ConnectionHandler (Socket user) {this.user = user;}

        @Override
        public void run() {
            try {
                out = new PrintWriter(user.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(user.getInputStream()));
                sendMessage("Enter your nickname: ");
                nickname = in.readLine(); // <- = ...next();
                sendMessage("Your are Welcome "+nickname+"!");
                System.out.println(nickname+" connected");
                broadcast(nickname + " joined the chat");
                String message;
                while ((message = in.readLine()) != null) {
                    if (message.endsWith("--nick")) {
                        String[] messageSplit = message.split(" ", 2);
                        if (messageSplit.length == 2) {
                            broadcast(nickname + " renamed themselves to " + messageSplit[0]);
                            System.out.println(nickname + " renamed themselves to " + messageSplit[0]);
                            nickname = messageSplit[0];
                            sendMessage("Successfully changed nickname to " + nickname);
                        } else {
                            sendMessage("No nickname provided");
                        }
                    } else if (message.endsWith("--quit")){
                        broadcast(nickname + " left the room");
                        shutdown();
                    } else {
                        broadcast(nickname + ": "+message);
                    }
                }
            } catch (IOException e) {
                shutdown();
            }
        }

        public void sendMessage(String msg) {
            out.println(msg);
        }

        public void shutdown() {
            try {
                in.close();
                out.close();
                if (!user.isClosed()) {
                    user.close();
                }
            } catch (IOException e) {}
        }
    }
    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }
}
