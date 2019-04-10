package model.mail;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class MessageTest {

    String[] to = {"Henry Dufour", "Jacaques Chirac"};
    String[] cc = {"Marco Polo"};

    @Test
    public void constructorShoudlWork() {


        Message message = new Message("monsieur", to, cc, "Subject", "Body");
    }

    @Test
    public void gettersShouldWork() {
        Message message = new Message("monsieur", to, cc, "Subject", "Body");

        assertEquals("monsieur", message.getFrom());
        assertEquals("Subject", message.getSubject());
        assertEquals("Body", message.getBody());

    }
}
