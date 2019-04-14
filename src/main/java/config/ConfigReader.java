package config;

import lombok.Getter;
import parser.SimpleParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader implements IConfig{

    String path;

    @Getter
    String address;

    @Getter
    int port;

    @Getter
    int nbGroups;

    @Getter
    String Witness;

    public ConfigReader(String path) throws IOException {

        this.path = path;

        InputStream inputStream = SimpleParser.class.getClassLoader().getResourceAsStream(this.path);

        Properties props = new Properties();
        props.load(inputStream);

        address = props.getProperty("Address");

        port = Integer.parseInt(props.getProperty("Port"));

        nbGroups = Integer.parseInt(props.getProperty("NbGroups"));

        Witness = props.getProperty("Witness");
    }
}
