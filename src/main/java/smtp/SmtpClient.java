package smtp;

import model.mail.Message;

import java.io.*;
import java.net.Socket;

public class SmtpClient implements ISmtpClient {

    // Some SMTP response codes
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

        socket = new Socket(address, port);

        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
    }

    public void sendMessage(Message message) throws IOException {

        this.message = message;

        System.out.println("Sending a message");

        if (!this.contactSMTPServer() ) {
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

    private boolean contactSMTPServer() throws IOException {

        currentLine = reader.readLine();

        writer.printf("EHLO itsamemario" + CRLF); // We say Hello to the server.

        currentLine = reader.readLine();
        System.out.println(currentLine);


        return currentLine.startsWith(REQUEST_OKAY);
    }

    private void writeMessage () throws IOException {

        currentLine = reader.readLine();
        System.out.println(currentLine);


        // From: MAIL FROM
        writer.write(String.format("MAIL FROM:%s%s", message.getFrom(), CRLF));
        writer.flush();

        // To: RCPT TO
        writer.write(String.format("RCPT TO:%s%s", "Camille", CRLF)); // TODO: 2019-04-10 plusieurs
        writer.flush();

        // CC ?

        // Data
        writer.write("DATA" + CRLF);
        writer.flush();

/*        currentLine = reader.readLine();
        if(!currentLine.startsWith(START_INPUT)) {
            throw new IOException("SMTP Not Happy");
        }*/

        writer.write(String.format("From:%s%s", "Chaton", CRLF));

        writer.write(String.format("To:%s%s", "Chaton", CRLF)); // TODO: 2019-04-10 plusieurs

        writer.write(String.format("From:%s%s", "Chaton", CRLF));

        writer.write(String.format("Subject:%s%s", message.getSubject(), CRLF));

        writer.write(String.format("Body:%s%s", message.getBody(), CRLF));
        writer.flush();

        // Data over
        writer.write(CRLF + '.' + CRLF);
        writer.flush();

    }


    public static void main(String ... args) throws IOException {
        SmtpClient smtpClient = new SmtpClient("localhost", 2525);

        String[] to = {"Camille"};
        String[] cc = {};

        smtpClient.sendMessage(new Message("Rémy", to, cc, "Coucou", "ça va ?"));
    }

}
