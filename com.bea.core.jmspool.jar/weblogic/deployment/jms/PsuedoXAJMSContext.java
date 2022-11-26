package weblogic.deployment.jms;

import java.io.Serializable;
import javax.jms.BytesMessage;
import javax.jms.ConnectionMetaData;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.XAConnectionFactory;
import javax.jms.XAJMSContext;
import javax.transaction.xa.XAResource;
import weblogic.utils.LocatorUtilities;

public class PsuedoXAJMSContext implements XAJMSContext {
   private XAConnectionFactory xaConnectionFactory;
   private boolean hasCreds;
   private String userName;
   private String password;
   private String clientID;
   private boolean hasNativeTransactions;
   private boolean isClosed;

   PsuedoXAJMSContext(XAConnectionFactory xaConnectionFactory, boolean hasCreds, String userName, String password) {
      this.xaConnectionFactory = xaConnectionFactory;
      this.hasCreds = hasCreds;
      this.userName = userName;
      this.password = password;
      PrimaryContextHelperServiceGenerator generator = (PrimaryContextHelperServiceGenerator)LocatorUtilities.getService(PrimaryContextHelperServiceGenerator.class);
      XAJMSContext context;
      Throwable var7;
      if (hasCreds) {
         context = xaConnectionFactory.createXAContext(userName, password);
         var7 = null;

         try {
            this.clientID = context.getClientID();
            this.hasNativeTransactions = generator.hasNativeTransactions(context);
         } catch (Throwable var31) {
            var7 = var31;
            throw var31;
         } finally {
            if (context != null) {
               if (var7 != null) {
                  try {
                     context.close();
                  } catch (Throwable var29) {
                     var7.addSuppressed(var29);
                  }
               } else {
                  context.close();
               }
            }

         }
      } else {
         context = xaConnectionFactory.createXAContext();
         var7 = null;

         try {
            this.clientID = context.getClientID();
            this.hasNativeTransactions = generator.hasNativeTransactions(context);
         } catch (Throwable var30) {
            var7 = var30;
            throw var30;
         } finally {
            if (context != null) {
               if (var7 != null) {
                  try {
                     context.close();
                  } catch (Throwable var28) {
                     var7.addSuppressed(var28);
                  }
               } else {
                  context.close();
               }
            }

         }
      }

      this.isClosed = false;
   }

   public boolean isHasNativeTransactions() {
      return this.hasNativeTransactions;
   }

   public XAConnectionFactory getConnectionFactory() {
      return this.xaConnectionFactory;
   }

   public boolean isHasCreds() {
      return this.hasCreds;
   }

   public String getUserName() {
      return this.userName;
   }

   public String getPassword() {
      return this.password;
   }

   public void notYetImplemented() {
      System.out.println("NYI");
   }

   public void acknowledge() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public void close() {
      this.isClosed = true;
   }

   public QueueBrowser createBrowser(Queue arg0) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public QueueBrowser createBrowser(Queue arg0, String arg1) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public BytesMessage createBytesMessage() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public JMSConsumer createConsumer(Destination arg0) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public JMSConsumer createConsumer(Destination arg0, String arg1) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public JMSConsumer createConsumer(Destination arg0, String arg1, boolean arg2) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public JMSContext createContext(int arg0) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public JMSConsumer createDurableConsumer(Topic arg0, String arg1) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public JMSConsumer createDurableConsumer(Topic arg0, String arg1, String arg2, boolean arg3) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public MapMessage createMapMessage() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public Message createMessage() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public ObjectMessage createObjectMessage() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public ObjectMessage createObjectMessage(Serializable arg0) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public JMSProducer createProducer() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public Queue createQueue(String arg0) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public JMSConsumer createSharedConsumer(Topic arg0, String arg1) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public JMSConsumer createSharedConsumer(Topic arg0, String arg1, String arg2) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public JMSConsumer createSharedDurableConsumer(Topic arg0, String arg1) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public JMSConsumer createSharedDurableConsumer(Topic arg0, String arg1, String arg2) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public StreamMessage createStreamMessage() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public TemporaryQueue createTemporaryQueue() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public TemporaryTopic createTemporaryTopic() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public TextMessage createTextMessage() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public TextMessage createTextMessage(String arg0) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public Topic createTopic(String arg0) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public boolean getAutoStart() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public String getClientID() {
      if (this.isClosed) {
         throw new JMSRuntimeException("PsuedoXAJMSContext is closed");
      } else {
         return this.clientID;
      }
   }

   public ExceptionListener getExceptionListener() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public ConnectionMetaData getMetaData() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public int getSessionMode() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public void recover() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public void setAutoStart(boolean arg0) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public void setClientID(String arg0) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public void setExceptionListener(ExceptionListener arg0) {
      if (this.isClosed) {
         throw new JMSRuntimeException("PsuedoXAJMSContext is closed");
      }
   }

   public void start() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public void stop() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public void unsubscribe(String arg0) {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public void commit() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public JMSContext getContext() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public boolean getTransacted() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public XAResource getXAResource() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }

   public void rollback() {
      this.notYetImplemented();
      throw new JMSRuntimeException("Not yet implemented");
   }
}
