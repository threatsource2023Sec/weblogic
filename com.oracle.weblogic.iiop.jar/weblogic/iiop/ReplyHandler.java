package weblogic.iiop;

import weblogic.iiop.messages.Message;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.work.WorkAdapter;
import weblogic.work.WorkManagerFactory;

public final class ReplyHandler extends WorkAdapter {
   private final EndPoint endpoint;
   private final Message m;

   static void p(String s) {
      System.err.println("<ReplyHandler>: " + s);
   }

   public ReplyHandler(EndPoint endpoint, Message m) {
      this.endpoint = endpoint;
      this.m = m;
      WorkManagerFactory.getInstance().getSystem().schedule(this);
   }

   public void run() {
      try {
         CorbaOutputStream os = this.m.getOutputStream();
         this.endpoint.send(os);
         os.close();
      } catch (Throwable var2) {
         this.endpoint.handleProtocolException(this.m, var2);
      }

   }
}
