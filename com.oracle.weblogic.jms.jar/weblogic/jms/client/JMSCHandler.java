package weblogic.jms.client;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public final class JMSCHandler implements ExceptionListener, MessageListener {
   private final int id;

   private native void JmsHandleException(int var1, JMSException var2);

   private native void JmsHandleMessage(int var1, Message var2);

   public JMSCHandler(int id) {
      this.id = id;
   }

   public void onException(JMSException jmse) {
      try {
         this.JmsHandleException(this.id, jmse);
      } catch (Throwable var3) {
         System.out.println("An exception was raised in the native C exception handler: " + var3);
      }

   }

   public void onMessage(Message msg) {
      try {
         this.JmsHandleMessage(this.id, msg);
      } catch (Throwable var3) {
         System.out.println("An exception was raised in the native C message handler: " + var3);
      }

   }

   static {
      try {
         System.loadLibrary("jmsc");
      } catch (Throwable var1) {
         System.out.println("Could not load library jmsc: " + var1);
      }

   }
}
