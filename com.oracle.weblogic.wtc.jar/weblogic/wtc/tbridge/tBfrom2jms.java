package weblogic.wtc.tbridge;

import java.util.Hashtable;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageEOFException;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public final class tBfrom2jms implements MessageListener {
   public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
   public static final String JMS_FACTORY = "weblogic.jms.ConnectionFactory";
   public static final String QUEUE = "weblogic.jms.Tux2JmsQueue";
   private QueueConnectionFactory qconFactory;
   private QueueConnection qcon;
   private QueueSession qsession;
   private QueueReceiver qreceiver;
   private Queue queue;
   private boolean quit = false;
   private static String myqueue = null;

   public void onMessage(Message jmsMsg) {
      try {
         if (jmsMsg instanceof TextMessage) {
            String msgText = ((TextMessage)jmsMsg).getText();
            System.out.println("TextMessage:" + msgText);
            if (msgText.equalsIgnoreCase("quit") || msgText.equalsIgnoreCase("<FML32><STRING>tiuq</STRING></FML32>")) {
               synchronized(this) {
                  this.quit = true;
                  this.notifyAll();
               }
            }
         } else if (jmsMsg instanceof BytesMessage) {
            byte[] carray = new byte[1000];
            int i = 0;

            while(true) {
               try {
                  carray[i] = ((BytesMessage)jmsMsg).readByte();
               } catch (MessageEOFException var6) {
                  System.out.println("BytesMessage: " + i + " bytes.");

                  for(i = 0; i < 10; ++i) {
                     System.out.println("  CArray[" + i + "] = " + carray[i]);
                     if (carray[i] == 0) {
                        return;
                     }
                  }

                  return;
               }

               ++i;
            }
         } else {
            System.out.println("Unsupported message type.");
         }
      } catch (JMSException var7) {
         var7.printStackTrace();
      }

   }

   public void init(Context ctx, String queueName) throws NamingException, JMSException {
      this.qconFactory = (QueueConnectionFactory)ctx.lookup("weblogic.jms.ConnectionFactory");
      this.qcon = this.qconFactory.createQueueConnection();
      this.qsession = this.qcon.createQueueSession(false, 1);

      try {
         this.queue = (Queue)ctx.lookup(queueName);
      } catch (NamingException var4) {
         this.queue = this.qsession.createQueue(queueName);
         ctx.bind(queueName, this.queue);
      }

      String selector = "";
      this.qreceiver = this.qsession.createReceiver(this.queue, selector);
      this.qreceiver.setMessageListener(this);
      this.qcon.start();
   }

   public void close() throws JMSException {
      this.qreceiver.close();
      this.qsession.close();
      this.qcon.close();
   }

   public static void main(String[] args) throws Exception {
      if (args.length >= 1 && args.length <= 2) {
         tBfrom2jms ac = new tBfrom2jms();
         InitialContext ic;
         if (args.length == 1) {
            ic = getInitialContext(args[0]);
            ac.init(ic, "weblogic.jms.Tux2JmsQueue");
            myqueue = "weblogic.jms.Tux2JmsQueue";
         } else {
            ic = getInitialContext(args[1]);
            ac.init(ic, args[0]);
            myqueue = args[0];
         }

         System.out.println("tBfrom2jms ready to recieve messages from: " + myqueue);
         synchronized(ac) {
            while(!ac.quit) {
               try {
                  ac.wait();
               } catch (InterruptedException var5) {
               }
            }
         }

         System.out.println("exiting...quit received.");
         Thread.sleep(2000L);
         ac.close();
      } else {
         System.out.println("Usage: java tBfrom2jms [queue] WebLogicURL");
      }
   }

   private static InitialContext getInitialContext(String url) throws NamingException {
      Hashtable env = new Hashtable();
      env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
      env.put("java.naming.provider.url", url);
      return new InitialContext(env);
   }
}
