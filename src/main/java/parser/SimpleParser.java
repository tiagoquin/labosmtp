package parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SimpleParser {

    final String DELIMITER;

    public SimpleParser(String delimiter) {
        this.DELIMITER = delimiter;
    }

    public List<String> parse (String path) {

        List<String> text = new ArrayList<>();

        InputStream inputStream = SimpleParser.class.getClassLoader().getResourceAsStream(path);

        Scanner scanner = new Scanner(inputStream);

        scanner.useDelimiter(DELIMITER);

        while (scanner.hasNext()) {

            text.add(scanner.next());
        }

        return text;
    }
}
