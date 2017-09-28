package EchoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static EchoServer.EchoServer.connectionsCounter;

public class MultiConnectionsHandler extends Thread {

    private Socket clientSocket = null;
    private static int threadCounter = 0;
    private int threadNumber;

    MultiConnectionsHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        threadCounter += 1;
        this.threadNumber = threadCounter;
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
            if (clientSocket.isClosed()) {
                return;
            }
            e.printStackTrace();
        }

        System.out.println("Client " + clientSocket.toString() + " disconnected");
        closeConnection();
        EchoServer.connectionsList.remove(this);
        EchoServer.connectionsCounter--;
        System.out.println("Now connected: " + connectionsCounter);
    }

    void closeConnection() {
        try {
            clientSocket.close();
            System.out.println("Tread: " + threadNumber + " closed ");
            interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
