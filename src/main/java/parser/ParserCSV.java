package parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ParserCSV {

    private static ParserCSV instance;


    public List<String> parse (String path) {

        List<String> victimes = new ArrayList<>();

        InputStream inputStream = ParserCSV.class.getClassLoader().getResourceAsStream(path);

        Scanner scanner = new Scanner(inputStream);

        while (scanner.hasNext()) {

            victimes.add(scanner.next());
        }

        return victimes;
    }

    public static ParserCSV getInstance() {

        if (instance == null) {
            instance = new ParserCSV();
        }

        return instance;
    }
}