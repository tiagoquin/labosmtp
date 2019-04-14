package smtp;

import model.mail.Message;

import java.io.*;
import java.net.Socket;

public class SmtpClient implements ISmtpClient {

    // Some SMTP response codes.
    // Use it to check if the exchange is going well
    static final String SERVICE_READY = "220";
    static final String REQUEST_OKAY = "250";
    static final String START_INPUT = "354";
    static final String CLOSING = "221";

    // End of a line
    static final String CRLF = "\r\n";

    private String address;
    private int port;

    private PrintWriter writer;
    private BufferedReader reader ;

    private String currentLine;

    Socket socket;

    Message message;

    /**
     * Init the SmtpClient. You should be able to use sendmessage after it
     * @param address
     * @param port
     * @throws IOException
     */
    public SmtpClient(String address, int port) throws IOException {
        this.address = address;
        this.port = port;

        socket = new Socket(this.address, this.port);

        writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
    }

    /**
     * Send a Message to a SMTP server
     * @param message
     * @throws IOException
     */
    public void sendMessage(Message message) throws IOException {

        this.message = message;

        System.out.println(" - Sending a message");

        this.contactSMTPServer();

        System.out.println(" - Ready to start");

        while (currentLine.startsWith(REQUEST_OKAY + "-")) {
            this.readOneLine(REQUEST_OKAY);
        }

        System.out.println(" - Writing header:");

        this.writeHeader();

        System.out.println(" - Writing message:");

        this.writeMessage();

        writer.write(String.format("%s %s", "QUIT", CRLF));
        writer.flush();

        this.readOneLine(CLOSING);

        writer.close();
        reader.close();
        socket.close();

    }

    /**
     * Contacting the server SMTP
     * @throws IOException
     */
    private void contactSMTPServer() throws IOException {

        this.readOneLine(SERVICE_READY);

        writer.printf("EHLO itsmemario" + CRLF); // We say Hello to the server.

        this.readOneLine(REQUEST_OKAY);

    }

    /**
     * Writing the Header
     * @throws IOException
     */
    private void writeHeader () throws IOException {

        // From: MAIL FROM
        writer.write(String.format("MAIL FROM:<%s>%s", message.getFrom(), CRLF));
        writer.flush();

        this.readOneLine(REQUEST_OKAY);

        // To: RCPT TO
        for (String to : message.getTo()) {
            writer.write(String.format("RCPT TO:<%s>%s", to, CRLF));
            writer.flush();

            this.readOneLine(REQUEST_OKAY);
        }

        // CC : RCPT TO
        for (String to : message.getCc()) {
            writer.write(String.format("RCPT TO:<%s>%s", to, CRLF));
            writer.flush();

            this.readOneLine(REQUEST_OKAY);
        }
    }

    /**
     * Writing the DATA
     * @throws IOException
     */
    private void writeMessage () throws IOException {

        // Data
        writer.write("DATA" + CRLF);
        writer.flush();

        this.readOneLine(START_INPUT);

        writer.write(String.format("Content-Type: text/plain; charset=\"utf-8\" %s", CRLF));

        writer.write(String.format("From:%s %s", message.getFrom(), CRLF));

        // To
        for (String to:message.getTo()) {
            writer.write(String.format("To:%s %s", to, CRLF));

        }

        // Cc
        for (String to:message.getCc()) {
            writer.write(String.format("Cc:%s %s", to, CRLF));

        }

        // Subject
        writer.write(String.format("Subject: %s %s", message.getSubject(), CRLF));

        // Body
        writer.write(String.format("%s %s", CRLF, message.getBody()));
        writer.flush();

        // Data over
        writer.write(CRLF + '.' + CRLF);
        writer.flush();

        this.readOneLine(REQUEST_OKAY);

    }

    /**
     * Read one line from the server and verifies if the response is correct
     * @param replyCode The code you expect to get
     * @throws IOException
     */
    private void readOneLine (String replyCode) throws IOException {
        currentLine = reader.readLine();
        System.out.println(currentLine);

        if (!currentLine.startsWith(replyCode)) {
            throw new IOException("SMTP-server : " + currentLine);
        }
    }


    /**
     * Little test
     * @param args
     * @throws IOException
     */
    public static void main(String ... args) throws IOException {
        SmtpClient smtpClient = new SmtpClient("localhost", 2525);

        String[] to = {"camille@mail.com", "vache@meuh.lait", "virgule@point.com"};
        String[] cc = {"Emilie.loiseau@regard.ch"};

        smtpClient.sendMessage(new Message("chat@heig-vd.ch", to, cc, "maow", "ça va ? Moi aussi"));
    }

}
