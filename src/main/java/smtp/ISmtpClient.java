package smtp;

import model.mail.Message;

public interface ISmtpClient {
    public void sendMessage(Message message);
}
