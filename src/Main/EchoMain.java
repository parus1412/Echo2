package Main;

import EchoServer.EchoServer;
import MyEchoException.MyEchoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class EchoMain {

    public static void main(String[] args) throws IOException, MyEchoException {
        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
        String userServer;
        EchoServer echoServer = new EchoServer(54321);
        Thread server = new Thread(echoServer);
        boolean work = true;

        server.start();

        userInput = new BufferedReader(new InputStreamReader(System.in));

        while (((userServer = userInput.readLine()) != null) && !userServer.contains("Stop")) {
            System.out.println("Server user input: " + userServer);
        }
//        server.interrupt();
        echoServer.stop(server);
//        server.interrupt();



        System.out.println("FINISH ...");

//        System.out.println("sdhjkdfhjf");
//
//        while (work) {
//            if (((userServer = in.readLine()) != null) && userServer.contains("stop")) {
//                work = false;
//                server.stop();
//            }
//        }
//
//        server.stop();
        return;
    }
}
