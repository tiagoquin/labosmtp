package model.mail;

import java.util.ArrayList;
import java.util.List;

public class Group {
    private final List<Person> people = new ArrayList<>();

    public void addPerson (Person person) {
        people.add(person);
    }

    public List<Person> getPeople () {
        return new ArrayList<>(people);
    }
}
