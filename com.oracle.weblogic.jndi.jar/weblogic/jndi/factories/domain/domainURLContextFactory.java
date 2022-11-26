package weblogic.jndi.factories.domain;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.ObjectFactory;
import weblogic.jndi.internal.AbstractURLContext;
import weblogic.jndi.internal.NamingDebugLogger;

public class domainURLContextFactory implements ObjectFactory {
   public domainURLContextFactory() {
      if (NamingDebugLogger.isDebugEnabled()) {
         p("domainURLContextFactory()");
      }

   }

   public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable env) throws NamingException {
      if (NamingDebugLogger.isDebugEnabled()) {
         p("getObjectInstance(" + obj + ", " + name + ")");
      }

      return new DomainContext(env);
   }

   private static final void p(String msg) {
      NamingDebugLogger.debug("<domainURLContextFactory>: " + msg);
   }

   private static class DomainContext extends AbstractURLContext {
      private Hashtable env;

      public DomainContext(Hashtable env) throws InvalidNameException {
         if (null == env) {
            this.env = new Hashtable(5);
         } else {
            this.env = (Hashtable)env.clone();
         }

      }

      protected String removeURL(String name) throws InvalidNameException {
         return name.substring(7);
      }

      protected Context getContext(String url) throws NamingException {
         this.env.put("weblogic.jndi.partitionInformation", "DOMAIN");
         return new InitialContext(this.env);
      }
   }
}
