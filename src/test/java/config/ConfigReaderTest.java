package config;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConfigReaderTest {

    ConfigReader configReader;

    @Test
    public void shouldFindConfig () {
        try {
            configReader = new ConfigReader("config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldGetAddress () {

        try {
            configReader = new ConfigReader("config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertNotNull(configReader.getAddress());
    }

    @Test
    public void shouldGetPort () {

        try {
            configReader = new ConfigReader("config.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }


        assertNotNull(configReader.getPort());
    }

}