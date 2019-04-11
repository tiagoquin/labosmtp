package smtp;

import model.mail.Message;

import java.io.*;
import java.net.Socket;

public class SmtpClient implements ISmtpClient {

    // Some SMTP response codes
    static final String SERVICE_READY = "220";
    static final String REQUEST_OKAY = "250";
    static final String START_INPUT = "354";

    static final String CRLF = "\r\n";

    private String address;
    private int port;

    private PrintWriter writer;
    private BufferedReader reader ;

    private String currentLine;

    Socket socket;

    Message message;

    public SmtpClient(String address, int port) throws IOException {
        this.address = address;
        this.port = port;

        socket = new Socket(this.address, this.port);

        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
    }

    public void sendMessage(Message message) throws IOException {

        this.message = message;

        System.out.println("Sending a message");

        this.contactSMTPServer();

        if (!currentLine.startsWith(REQUEST_OKAY)) {
            throw new IOException("SMTP server didn't say okay : " + currentLine);
        }

       /* while (currentLine.startsWith(REQUEST_OKAY + "-")) {
            currentLine = reader.readLine();
            System.out.println(currentLine);
        }
*/
        this.writeMessage();

        writer.close();
        reader.close();
        socket.close();

    }

    private void contactSMTPServer() throws IOException {

        this.readOneLine();

        writer.printf("EHLO tiago.povoaquinteiro@heig-vd.ch" + CRLF); // We say Hello to the server.

        this.readOneLine();
    }

    private void writeMessage () throws IOException {

        this.readOneLine();

        // From: MAIL FROM
        writer.write(String.format("MAIL FROM: %s%s", message.getFrom(), CRLF));
        writer.flush();

        this.readOneLine();

        // To: RCPT TO
        writer.write(String.format("RCPT TO: %s%s", "tiago.povoaquinteiro@heig-vd.ch", CRLF)); // TODO: 2019-04-10 plusieurs
        writer.flush();

        this.readOneLine();

        // CC ?

        // Data
        writer.write("DATA" + CRLF);
        writer.flush();

        this.readOneLine();

        writer.write(String.format("Content-Type: text/plain; charset=\"utf-8\" %s", CRLF));


/*        currentLine = reader.readLine();
        if(!currentLine.startsWith(START_INPUT)) {
            throw new IOException("SMTP Not Happy");
        }*/

        writer.write(String.format("From: %s %s", message.getFrom(), CRLF));

        writer.write(String.format("To: %s %s", "tiago.povoaquinteiro@heig-vd.ch", CRLF)); // TODO: 2019-04-10 plusieurs

        writer.write(String.format("Subject: %s %s", message.getSubject(), CRLF));

        writer.write(String.format("%s %s", CRLF, message.getBody()));
        writer.flush();

        // Data over
        writer.write(CRLF + '.' + CRLF);
        writer.flush();

        this.readOneLine();

    }

    private void readOneLine () throws IOException {
        currentLine = reader.readLine();
        System.out.println(currentLine);
    }


    public static void main(String ... args) throws IOException {
        SmtpClient smtpClient = new SmtpClient("localhost", 2525);

        String[] to = {"camille@mail.com"};
        String[] cc = {};

        smtpClient.sendMessage(new Message("tiago.povoaquinteiro@heig-vd.ch", to, cc, "étoiles, magie Voyances", "ça va ? Moi aussi"));
    }

}
