package weblogic.servlet.internal;

import java.io.IOException;
import java.security.PrivilegedAction;
import weblogic.servlet.security.internal.WebAppSecurity;

abstract class IOEventProcessor implements Runnable {
   private WebAppServletContext context = null;

   public IOEventProcessor(WebAppServletContext context) {
      this.context = context;
   }

   public void run() {
      PrivilegedAction action = new PrivilegedAction() {
         public Object run() {
            Thread thread = Thread.currentThread();
            ClassLoader oldClassLoader = IOEventProcessor.this.context.pushEnvironment(thread);

            Throwable var4;
            try {
               IOEventProcessor.this.handleEvent();
               return null;
            } catch (Throwable var8) {
               IOEventProcessor.this.handleError(var8);
               var4 = var8;
            } finally {
               WebAppServletContext.popEnvironment(thread, oldClassLoader);
            }

            return var4;
         }
      };
      WebAppSecurity.getProvider().getAnonymousSubject().run(action);
   }

   abstract void handleEvent() throws IOException;

   abstract void handleError(Throwable var1);
}
