package EchoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static EchoServer.EchoServer.conectionsCounter;

public class MultiConnectionsHandler extends Thread {

    private Socket clientSocket = null;
    private boolean isConnected;
    private int connectionNumber;

    public MultiConnectionsHandler(Socket clientSocket, int connectionNumber) {
        this.clientSocket = clientSocket;
        this.isConnected = true;
        this.connectionNumber = connectionNumber;
    }

    public void run() {
        BufferedReader in = null;
        PrintWriter echo = null;
        String clientInput;

        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            echo = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            while (((clientInput = in.readLine()) != null) && !clientInput.contains("disconnect")) {
                echo.println("Echo: " + clientInput);
                System.out.println("Client input: " + clientInput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (clientSocket.isClosed()) {
            return;
        }
        System.out.println("Client " + clientSocket.toString() + " disconnected");
        EchoServer.conectionsCounter--;
        System.out.println("Now connected: " + conectionsCounter);
//        interrupt();
    }

    public void closeConnection() {
        try {
            clientSocket.close();
//            interrupt();
            System.out.println("Connection: " + connectionNumber + " closed ");
            interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    public void interrupt() {
//        try {
//            clientSocket.close();
//            System.out.println("Connection: " + connectionNumber + " closed ");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }




}
