package topic;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class TopicSender {

    private TopicConnection topicConnection;

    public void sendMessage(String thematicListName, String comment, String toUser) {
        try {
            Properties props = new Properties();
            props.setProperty("java.naming.factory.url.pkgs", "org.jboss.naming");
            Context context = new InitialContext(props);
            TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup("ConnectionFactory");
            Topic topic = (Topic) context.lookup("java:/jms/topic/SOA_Test");
            topicConnection = topicConnectionFactory.createTopicConnection();
            TopicSession topicSession = topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            topicConnection.start();
            TopicPublisher topicPublisher = topicSession.createPublisher(topic);

            Message message = topicSession.createTextMessage();
            message.setStringProperty("name", thematicListName);
            message.setStringProperty("comment", comment);
            message.setStringProperty("toUser", toUser);
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

}
