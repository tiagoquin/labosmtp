package model.mail;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class MessageTest {


    @Test
    public void gettersShouldWork() {

        String[] to = {"Henry Dufour", "Jacaques Chirac"};
        String[] cc = {"Marco Polo"};

        Message message = new Message("monsieur", to, cc, "Subject", "Body");

        assertEquals("monsieur", message.getFrom());
        assertEquals("Subject", message.getSubject());
        assertEquals("Body", message.getBody());

    }
}
