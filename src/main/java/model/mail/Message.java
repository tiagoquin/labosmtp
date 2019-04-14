package model.mail;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Getter
    @Setter
    private String from;

    @Getter
    @Setter
    private String[] to;

    @Getter
    @Setter
    private String[] cc;

    @Getter
    @Setter
    private String subject;

    @Getter
    @Setter
    private String body;
}
