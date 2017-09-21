package Main;

import MyEchoException.MyEchoException;
import WazzupClient.WazzupClient;

import java.io.IOException;
import java.net.Inet4Address;

public class ClientMain {

    public static void main(String[] args) throws IOException, MyEchoException {
        WazzupClient client = new WazzupClient();
        client.connect(Inet4Address.getByName("127.0.0.1"), 54321);


    }

}
