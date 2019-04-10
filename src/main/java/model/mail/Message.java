package model.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Message {

    @Getter
    private String from;
    @Getter
    private String[] to;
    @Getter
    private String[] cc;
    @Getter
    private String subject;
    @Getter
    private String body;

}
