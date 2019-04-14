package model.prank;

import config.ConfigReader;
import config.IConfig;
import model.mail.Person;
import parser.SimpleParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrankGen {

    IConfig Config;


    public List<Prank> generate() throws IOException {
        List<Prank> pranks = new ArrayList<>();

        Config = new ConfigReader("config.properties");

        int nbGroup = Config.getNbGroups();

        // People

        List<String> ListVictims = new SimpleParser("\n").parse("victimes.utf8");

        if (ListVictims.size() / nbGroup < 3) {
            throw new IOException("Incompatible group size and quantity of victimes");
        }

        List<Person> people = new ArrayList<>();

        for (String v: ListVictims) {
            people.add(new Person(v));
        }

        Collections.shuffle(people);

        // Prank

        int i = 0;
        int j = -1;
        for (Person person: people) {
            if (i % nbGroup == 0) {
                pranks.add(new Prank());
                ++j;
                pranks.get(j).setVictimSender(person);
            } else {
                pranks.get(j).addVictimRecipients(person);
            }
            ++i;
        }

        for (Prank prank : pranks) {
            prank.addWitness(new Person(Config.getWitness()));
        }

        return pranks;
    }

    public static void main(String... args) throws IOException {
        PrankGen pg = new PrankGen();

        pg.generate();
    }
}