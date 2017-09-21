package EchoServer;

import MyEchoException.MyEchoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static com.sun.jndi.ldap.LdapCtx.DEFAULT_PORT;

public class EchoServer implements Runnable {
    private int port;
    public static int conectionsCounter = 0;
    private static boolean isRunning = false;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ArrayList<MultiConnectionsHandler> connectionsList = new ArrayList<MultiConnectionsHandler>();

    private boolean isRunning() {
        return isRunning;
    }

    public EchoServer() throws MyEchoException, IOException {
        this(DEFAULT_PORT);
    }

    public EchoServer(int port) throws IOException {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void run() {
        MultiConnectionsHandler multiConnectionsHandler;
        BufferedReader in = null;

        System.out.println("Starting Echo server ...");

        this.isRunning = true;


        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Echo Server started ...");


        while ( isRunning() ) {
            System.out.println("Waiting for next connection ...");
            try {
                this.clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if ( isRunning() ) {
                System.out.println("Client " + clientSocket.toString() + " was connected.");
                conectionsCounter++;
                System.out.println("Now connected: " + conectionsCounter);

                multiConnectionsHandler = new MultiConnectionsHandler(this.clientSocket, conectionsCounter);
                connectionsList.add(multiConnectionsHandler);

                multiConnectionsHandler.start();
//                isRunning = false;
            }
        }

//            in = new BufferedReader(new InputStreamReader(System.in));
//            String userInput;

//            while ((userInput = in.readLine()) != null ) {
//                if ( userInput.contains("stop_server") ) {
////                    multiServerSocket.interrupt();
//                    stop();
//                }
//                System.out.println("Server user input: " + userInput);
//            }


    }

    public void stop(Thread server) throws IOException {
        if ( isRunning() ) {
            for (MultiConnectionsHandler multiConnectionsHandler : connectionsList) {
                multiConnectionsHandler.closeConnection();
                multiConnectionsHandler.interrupt();
            }
            this.serverSocket.close();
            server.interrupt();
            this.isRunning = false;
        }
        System.out.println("Stop Echo Server");
    }
}
