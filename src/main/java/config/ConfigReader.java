package config;

import lombok.Getter;
import parser.ParserCSV;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    String path;

    @Getter
    String address;

    @Getter
    int port;

    public ConfigReader(String path) throws IOException {

        this.path = path;

        InputStream inputStream = ParserCSV.class.getClassLoader().getResourceAsStream(this.path);

        Properties props = new Properties();
        props.load(inputStream);

        address = props.getProperty("Address");

        port = Integer.parseInt(props.getProperty("Port"));


    }
}
