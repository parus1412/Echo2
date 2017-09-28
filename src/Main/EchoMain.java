package Main;

import EchoServer.EchoServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EchoMain {

    public static void main(String[] args) throws IOException {
        BufferedReader userInput;
        String userServer;
        EchoServer echoServer = new EchoServer(54321);
        Thread server = new Thread(echoServer);

        server.start();

        userInput = new BufferedReader(new InputStreamReader(System.in));

        while (((userServer = userInput.readLine()) != null) && !userServer.contains("Stop")) {
            System.out.println("Server user input: " + userServer);
        }

        echoServer.stop(server);
    }
}