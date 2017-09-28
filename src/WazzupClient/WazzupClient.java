package WazzupClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class WazzupClient {
    private Socket serverSocket;
    private boolean isConnected;

    public WazzupClient() {
        this.isConnected = false;
    }

    public void connect(InetAddress address, int port) throws IOException {
        BufferedReader in = null;
        PrintWriter out = null;
        BufferedReader userInput = null;
        PrintWriter userOut = null;
        String input;
        String response;


        if (!isConnected()) {
            this.isConnected = !this.isConnected;
            try {
                this.serverSocket = new Socket(address, port);
                System.out.println("Connected to " + serverSocket.toString());
                in = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
                out = new PrintWriter(serverSocket.getOutputStream(), true);

                userInput = new BufferedReader(new InputStreamReader(System.in));
                userOut = new PrintWriter(System.out, true);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        while( (input = userInput.readLine()) != null && !input.contains("disconnect") ) {
            out.println(input);

            if ( ((response = in.readLine()) == null) || serverSocket.isClosed() ) {
                disconnect();
                return;
            } else {
                userOut.println(response);
            }
        }

        System.out.println("Disconnected from " + serverSocket.toString());
    }

    private boolean isConnected() {
        return this.isConnected;
    }

    private void disconnect() throws IOException {
        System.out.println("No response from : " + serverSocket.toString());
        this.serverSocket.close();
        this.isConnected = !this.isConnected;
    }
}
