package smtp;

import lombok.AllArgsConstructor;
import model.mail.Message;

import java.io.IOException;
import java.net.Socket;

@AllArgsConstructor
public class SmtpClient implements ISmtpClient {

    private String address;
    private int port;

    public void sendMessage(Message message) throws IOException {

        System.out.println("Sending a mail");

        Socket socket = new Socket(address, port);
    }
}
