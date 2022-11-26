package weblogic.apache.org.apache.log.output.jms;

import javax.jms.Message;
import javax.jms.Session;
import weblogic.apache.org.apache.log.LogEvent;
import weblogic.apache.org.apache.log.output.AbstractTarget;

public abstract class AbstractJMSTarget extends AbstractTarget {
   private MessageBuilder m_builder;

   public AbstractJMSTarget(MessageBuilder builder) {
      this.m_builder = builder;
   }

   protected abstract void send(Message var1);

   protected abstract Session getSession();

   protected void doProcessEvent(LogEvent event) throws Exception {
      Message message = this.m_builder.buildMessage(this.getSession(), event);
      this.send(message);
   }

   protected synchronized void open() {
      if (!this.isOpen()) {
         super.open();
         this.openConnection();
      }

   }

   public synchronized void close() {
      if (this.isOpen()) {
         this.closeConnection();
         super.close();
      }

   }

   protected abstract void openConnection();

   protected abstract void closeConnection();
}
