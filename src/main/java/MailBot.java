import config.ConfigReader;
import parser.ParserCSV;
import smtp.ISmtpClient;
import smtp.SmtpClient;

import java.io.IOException;
import java.util.List;


public class MailBot {

    public static void main(String ... args) throws IOException {

        ConfigReader configReader = new ConfigReader("config.properties");

        List<String> victimes = ParserCSV.getInstance().parse("victimes.csv");

        ISmtpClient smtpClient = new SmtpClient(configReader.getAddress(), configReader.getPort());

    }
}
