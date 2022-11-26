package weblogic.apache.org.apache.log.output.jms;

import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import weblogic.apache.org.apache.log.LogEvent;

public class JMSTopicTarget extends AbstractJMSTarget {
   private TopicConnectionFactory m_factory;
   private Topic m_topic;
   private TopicSession m_session;
   private TopicPublisher m_publisher;
   private TopicConnection m_connection;

   public JMSTopicTarget(MessageBuilder builder, TopicConnectionFactory factory, Topic topic) {
      super(builder);
      this.m_factory = factory;
      this.m_topic = topic;
      this.open();
   }

   protected void send(Message message) {
      try {
         this.m_publisher.publish(message);
      } catch (Exception var3) {
         this.getErrorHandler().error("Error publishing message", var3, (LogEvent)null);
      }

   }

   protected Session getSession() {
      return this.m_session;
   }

   protected synchronized void openConnection() {
      try {
         this.m_connection = this.m_factory.createTopicConnection();
         this.m_connection.start();
         this.m_session = this.m_connection.createTopicSession(false, 1);
         this.m_publisher = this.m_session.createPublisher(this.m_topic);
      } catch (Exception var2) {
         this.getErrorHandler().error("Error starting connection", var2, (LogEvent)null);
      }

   }

   protected synchronized void closeConnection() {
      try {
         if (null != this.m_publisher) {
            this.m_publisher.close();
         }

         if (null != this.m_session) {
            this.m_session.close();
         }

         if (null != this.m_connection) {
            this.m_connection.close();
         }
      } catch (Exception var2) {
         this.getErrorHandler().error("Error closing connection", var2, (LogEvent)null);
      }

      this.m_publisher = null;
      this.m_session = null;
      this.m_connection = null;
   }
}
