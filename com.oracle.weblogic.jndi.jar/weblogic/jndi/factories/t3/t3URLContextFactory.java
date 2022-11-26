package weblogic.jndi.factories.t3;

import java.net.MalformedURLException;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.ObjectFactory;
import weblogic.jndi.WLInitialContextFactory;
import weblogic.jndi.internal.AbstractURLContext;
import weblogic.jndi.internal.NamingDebugLogger;
import weblogic.protocol.ServerURL;

public class t3URLContextFactory implements ObjectFactory {
   public t3URLContextFactory() {
      if (NamingDebugLogger.isDebugEnabled()) {
         p("t3URLContextFactory()");
      }

   }

   public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable env) throws NamingException {
      if (NamingDebugLogger.isDebugEnabled()) {
         p("getObjectInstance(" + obj + ", " + name + ")");
      }

      String url = null;
      if (obj instanceof String) {
         url = (String)obj;
      }

      return new T3Context(env, url);
   }

   private static final void p(String msg) {
      NamingDebugLogger.debug("<t3URLContextFactory>: " + msg);
   }

   private static class T3Context extends AbstractURLContext {
      private Hashtable env;
      private ServerURL url;

      public T3Context(Hashtable env, String url) throws InvalidNameException {
         this.env = env;
         if (url != null) {
            try {
               this.url = new ServerURL(ServerURL.DEFAULT_URL, url);
            } catch (MalformedURLException var5) {
               InvalidNameException ne = new InvalidNameException();
               ne.setRootCause(var5);
               throw ne;
            }
         } else {
            this.url = ServerURL.DEFAULT_URL;
         }

      }

      protected String getURL(String name) throws InvalidNameException {
         try {
            return name.indexOf(":") < 0 ? this.url.asUnsyncStringBuffer().append(name).toString() : (new ServerURL(this.url, name)).asUnsyncStringBuffer().toString();
         } catch (MalformedURLException var4) {
            InvalidNameException ne = new InvalidNameException();
            ne.setRootCause(var4);
            throw ne;
         }
      }

      protected Context getContext(String url) throws NamingException {
         if (this.env == null) {
            this.env = new Hashtable(5);
         }

         this.env.put("java.naming.provider.url", this.getURL(url));
         this.env.put("java.naming.factory.initial", WLInitialContextFactory.class.getName());
         return new InitialContext(this.env);
      }
   }
}
