package parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserCSVTest {

    @Test
    public void shoudlFindTheFiles() {

        assertNotNull(new ParserCSV().parse("victimes.csv"));
    }

}