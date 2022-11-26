package weblogic.apache.org.apache.log.output.jms;

import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import weblogic.apache.org.apache.log.LogEvent;

public class JMSQueueTarget extends AbstractJMSTarget {
   private QueueConnectionFactory m_factory;
   private Queue m_queue;
   private QueueSession m_session;
   private QueueSender m_sender;
   private QueueConnection m_connection;

   public JMSQueueTarget(MessageBuilder builder, QueueConnectionFactory factory, Queue queue) {
      super(builder);
      this.m_factory = factory;
      this.m_queue = queue;
      this.open();
   }

   protected void send(Message message) {
      try {
         this.m_sender.send(message);
      } catch (Exception var3) {
         this.getErrorHandler().error("Error publishing message", var3, (LogEvent)null);
      }

   }

   protected Session getSession() {
      return this.m_session;
   }

   protected synchronized void openConnection() {
      try {
         this.m_connection = this.m_factory.createQueueConnection();
         this.m_connection.start();
         this.m_session = this.m_connection.createQueueSession(false, 1);
         this.m_sender = this.m_session.createSender(this.m_queue);
      } catch (Exception var2) {
         this.getErrorHandler().error("Error starting connection", var2, (LogEvent)null);
      }

   }

   protected synchronized void closeConnection() {
      try {
         if (null != this.m_sender) {
            this.m_sender.close();
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

      this.m_sender = null;
      this.m_session = null;
      this.m_connection = null;
   }
}
