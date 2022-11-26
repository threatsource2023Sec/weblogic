package weblogic.jndi.factories.sharable;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.ObjectFactory;
import weblogic.jndi.internal.AbstractURLContext;
import weblogic.jndi.internal.NamingDebugLogger;

public class sharableURLContextFactory implements ObjectFactory {
   public sharableURLContextFactory() {
      if (NamingDebugLogger.isDebugEnabled()) {
         p("sharableURLContextFactory()");
      }

   }

   public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable env) throws NamingException {
      if (NamingDebugLogger.isDebugEnabled()) {
         p("getObjectInstance(" + obj + ", " + name + ")");
      }

      return new SharableContext(env);
   }

   private static final void p(String msg) {
      NamingDebugLogger.debug("<sharableURLContextFactory>: " + msg);
   }

   private static class SharableContext extends AbstractURLContext {
      private Hashtable env;

      public SharableContext(Hashtable env) throws InvalidNameException {
         if (null == env) {
            this.env = new Hashtable(5);
         } else {
            this.env = (Hashtable)env.clone();
         }

      }

      protected String removeURL(String name) throws InvalidNameException {
         return name.substring(9);
      }

      protected Context getContext(String url) throws NamingException {
         this.env.put("weblogic.jndi.createUnderSharable", "true");
         return new InitialContext(this.env);
      }
   }
}
