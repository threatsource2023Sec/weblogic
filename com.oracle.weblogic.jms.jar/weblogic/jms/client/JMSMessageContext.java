package weblogic.jms.client;

import javax.jms.MessageListener;

public class JMSMessageContext extends JMSContext {
   private MessageListener listener = null;

   public JMSMessageContext() {
   }

   public JMSMessageContext(MessageListener ml) {
      if (ml != null) {
         this.setClassLoader(ml.getClass().getClassLoader());
      }

      this.listener = ml;
   }

   public JMSMessageContext(MessageListener ml, ClassLoader cl) {
      this.setClassLoader(cl);
      this.listener = ml;
   }

   public void setMessageListener(MessageListener ml) {
      this.listener = ml;
   }

   public MessageListener getMessageListener() {
      return this.listener;
   }
}
