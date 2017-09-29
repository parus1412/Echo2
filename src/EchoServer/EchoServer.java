package EchoServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static com.sun.jndi.ldap.LdapCtx.DEFAULT_PORT;

public class EchoServer implements Runnable {
    static int connectionsCounter = 0;
    static ArrayList<MultiConnectionsHandler> connectionsList = new ArrayList<MultiConnectionsHandler>();
    private int port;
    private static boolean isRunning = false;
    private ServerSocket serverSocket;

    private boolean isRunning() {
        return isRunning;
    }

    public EchoServer() throws IOException {
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
        Socket clientSocket = null;

        System.out.println("Starting Echo server ...");

        isRunning = !isRunning;

        try {
            this.serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Echo Server started ...");


        while ( isRunning ) {
            System.out.println("Waiting for next connection ...");
            try {
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.out.println("Echo Server stopped");
            }
            if ( isRunning ) {
                System.out.println("Client " + clientSocket.toString() + " was connected.");
                connectionsCounter += 1;
                System.out.println("Now connected: " + connectionsCounter);

                multiConnectionsHandler = new MultiConnectionsHandler(clientSocket);
                connectionsList.add(multiConnectionsHandler);

                multiConnectionsHandler.start();
            }
        }
    }

    public void stop(Thread server) throws IOException {
        if ( isRunning ) {
            for (MultiConnectionsHandler multiConnectionsHandler : connectionsList) {
                multiConnectionsHandler.closeConnection();
                multiConnectionsHandler.interrupt();
            }
            serverSocket.close();
            server.interrupt();
            isRunning = !isRunning;;
        }
        System.out.println("Socket closed");
    }
}
