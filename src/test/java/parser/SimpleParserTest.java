package parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleParserTest {

    @Test
    public void shoudlFindTheFiles() {

        assertNotNull(new SimpleParser("\n").parse("victimes.utf8"));
    }

}