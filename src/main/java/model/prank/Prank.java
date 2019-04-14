package model.prank;

import lombok.Getter;
import lombok.Setter;
import model.mail.Message;
import model.mail.Person;

import java.util.ArrayList;
import java.util.List;

public class Prank {

    @Getter
    @Setter
    private Person victimSender;

    @Getter
    private final List<Person> victimRecipients = new ArrayList<>();

    @Getter
    private final List<Person> witnesses = new ArrayList<>();

    @Getter
    @Setter
    private String message;

    public void addVictimRecipients (Person person) {
        victimRecipients.add(person);
    }

    public void addWitness (Person person) {
        witnesses.add(person);
    }

    public Message generateMessage() {

        Message message = new Message();

        message.setFrom(victimSender.getAddress());

        List<String> victimes = new ArrayList<>();

        for (Person p: victimRecipients) {
            victimes.add(p.getAddress());
        }

        message.setTo(victimes.toArray(new String[victimes.size()]));

        List<String> ccWitness = new ArrayList<>();

        for (Person p: witnesses) {
            ccWitness.add(p.getAddress());
        }

        message.setCc(ccWitness.toArray(new String[ccWitness.size()]));

        return message;
    }

}
