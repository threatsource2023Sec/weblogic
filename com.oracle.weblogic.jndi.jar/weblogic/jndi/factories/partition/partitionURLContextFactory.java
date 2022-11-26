package weblogic.jndi.factories.partition;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.InvalidNameException;
import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.spi.ObjectFactory;
import weblogic.jndi.internal.AbstractURLContext;
import weblogic.jndi.internal.NamingDebugLogger;

public class partitionURLContextFactory implements ObjectFactory {
   public partitionURLContextFactory() {
      if (NamingDebugLogger.isDebugEnabled()) {
         p("partitionURLContextFactory()");
      }

   }

   public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable env) throws NamingException {
      if (NamingDebugLogger.isDebugEnabled()) {
         p("getObjectInstance(" + obj + ", " + name + ")");
      }

      return new PartitionContext(env);
   }

   private static final void p(String msg) {
      NamingDebugLogger.debug("<partitionURLContextFactory>: " + msg);
   }

   private static class PartitionContext extends AbstractURLContext {
      private Hashtable env;
      private static final char PARTITIONID_SEPARATOR = '/';

      public PartitionContext(Hashtable env) throws InvalidNameException {
         if (null == env) {
            this.env = new Hashtable(5);
         } else {
            this.env = (Hashtable)env.clone();
         }

      }

      protected String removeURL(String name) throws InvalidNameException {
         int resolvedEndPos = name.indexOf(47);
         return resolvedEndPos == -1 ? "" : name.substring(resolvedEndPos + 1);
      }

      private String getPartitionInfoFromURL(String url) {
         int resolvedEndPos = url.indexOf(47);
         return resolvedEndPos == -1 ? url.substring(10) : url.substring(10, resolvedEndPos);
      }

      protected Context getContext(String url) throws NamingException {
         String partitionName = this.getPartitionInfoFromURL(url);
         this.env.put("weblogic.jndi.partitionInformation", partitionName);
         return new InitialContext(this.env);
      }
   }
}
