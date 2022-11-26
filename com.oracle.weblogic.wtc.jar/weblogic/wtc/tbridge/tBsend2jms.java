package weblogic.wtc.tbridge;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Hashtable;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public final class tBsend2jms {
   public static final String JNDI_FACTORY = "weblogic.jndi.WLInitialContextFactory";
   public static final String JMS_FACTORY = "weblogic.jms.ConnectionFactory";
   public static final String QUEUE = "weblogic.jms.Jms2TuxQueue";
   private QueueConnectionFactory qconFactory;
   private QueueConnection qcon;
   private QueueSession qsession;
   private QueueSender qsender;
   private Queue queue;
   private TextMessage textmsg;
   private BytesMessage bytesmsg;
   private static byte[] canofdata = new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 0};

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

      this.qsender = this.qsession.createSender(this.queue);
      this.textmsg = this.qsession.createTextMessage();
      this.bytesmsg = this.qsession.createBytesMessage();
      this.qcon.start();
   }

   public void close() throws JMSException {
      this.qsender.close();
      this.qsession.close();
      this.qcon.close();
   }

   public static void main(String[] args) throws Exception {
      if (args.length >= 1 && args.length <= 2) {
         tBsend2jms omy = new tBsend2jms();
         InitialContext ic;
         if (args.length == 1) {
            ic = getInitialContext(args[0]);
            omy.init(ic, "weblogic.jms.Jms2TuxQueue");
         } else {
            ic = getInitialContext(args[1]);
            omy.init(ic, args[0]);
         }

         BufferedReader msgStream = new BufferedReader(new InputStreamReader(System.in));
         String line = null;
         boolean quitNow = false;

         do {
            System.out.println("Enter message (\"quit\" to quit): ");
            line = msgStream.readLine();
            if (line != null && line.trim().length() != 0) {
               quitNow = line.equalsIgnoreCase("quit") || line.trim().equalsIgnoreCase("<XML><STRING>quit</STRING></XML>");
               if (line.equalsIgnoreCase("bytes")) {
                  omy.sendbytes(canofdata);
               } else {
                  if (line.equalsIgnoreCase("null")) {
                     line = null;
                  }

                  omy.send(line);
               }
            }
         } while(!quitNow);

         omy.close();
      } else {
         System.out.println("Usage: tBsend2jms [queue] WebLogicURL");
      }
   }

   private static InitialContext getInitialContext(String url) throws NamingException {
      Hashtable env = new Hashtable();
      env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
      env.put("java.naming.provider.url", url);
      return new InitialContext(env);
   }

   public void send(String mymessage) throws JMSException {
      try {
         this.textmsg.setText(mymessage);
         this.qsender.send(this.textmsg);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }

   public void sendbytes(byte[] mydata) throws JMSException {
      try {
         this.bytesmsg.clearBody();
         this.bytesmsg.writeBytes(mydata);
         this.qsender.send(this.bytesmsg);
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }
}
