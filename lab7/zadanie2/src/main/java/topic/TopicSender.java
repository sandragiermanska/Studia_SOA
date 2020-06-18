package topic;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class TopicSender {

    private Topic topic;
    private TopicConnection topicConnection;


    public void sendMessage(NotificationType notificationType, String text, int id) {
        try {
            Properties props = new Properties();
            props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
            Context context = new InitialContext(props);
            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup("ConnectionFactory");
            topic = (Topic) context.lookup("java:/jms/topic/SOA_Test");
            topicConnection = topicConnectionFactory.createTopicConnection();
            TopicSession topicSession = topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            topicConnection.start();
            TopicPublisher topicPublisher = topicSession.createPublisher(topic);

            Message message = topicSession.createTextMessage();
            message.setStringProperty("notificationType", notificationType.name());
            message.setStringProperty("text", text);
            message.setIntProperty("id", id);
            topicPublisher.send(message);

        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            if (topicConnection != null) {
                try {
                    topicConnection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public enum NotificationType {
        newBook, returnBook, confirmation
    }

}
