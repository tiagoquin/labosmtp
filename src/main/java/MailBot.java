import config.ConfigReader;
import model.mail.Message;
import model.prank.Prank;
import model.prank.PrankGen;
import parser.SimpleParser;
import smtp.ISmtpClient;
import smtp.SmtpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MailBot {

    final static String SEPARATOR_JOKE = "\n-..-\n";
    final static String SEPARATOR_TITLE = "\n-.-\n";


    public static void main(String ... args) throws IOException {

        ConfigReader configReader = new ConfigReader("config.properties");

        // Add jokes
        List<String> lines = new SimpleParser(SEPARATOR_JOKE).parse("jokes.utf8");

        List<Prank> pranks = new PrankGen().generate();
        List<Message> messages = new ArrayList<>();

        int i = 0;
        for (Prank prank : pranks) {
            String[] str = lines.get(i++).split(SEPARATOR_TITLE);

            Message message = prank.generateMessage();

            message.setSubject(str[0]);
            message.setBody(str[1]);

            messages.add(message);
        }

        for (Message message: messages) {
            ISmtpClient smtpClient = new SmtpClient(configReader.getAddress(), configReader.getPort());

            System.out.println("\n --- \n");

            smtpClient.sendMessage(message);
        }
    }
}
