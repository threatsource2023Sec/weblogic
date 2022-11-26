package weblogic.jndi;

import java.io.IOException;
import java.io.InputStream;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Hashtable;
import java.util.Properties;
import java.util.WeakHashMap;
import javax.naming.ConfigurationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;
import javax.naming.spi.InitialContextFactory;

public class InitialContextFactoryWrapper implements InitialContextFactory {
   public static final String DEFAULT_INITIAL_CONTEXT_FACTORY = "weblogic.jndi.WLInitialContextFactory";
   private static final String APP_RESOURCE_FILE_NAME = "jndi.properties";
   private static final WeakHashMap factoryCache = new WeakHashMap(11);

   private ClassLoader getContextClassLoader() {
      return (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return Thread.currentThread().getContextClassLoader();
         }
      });
   }

   private Properties getProperties(final ClassLoader cl, final String name) throws IOException {
      InputStream in = (InputStream)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return cl == null ? ClassLoader.getSystemResourceAsStream(name) : cl.getResourceAsStream(name);
         }
      });
      Properties props = new Properties();
      if (in != null) {
         props.load(in);
      }

      return props;
   }

   private String getInitialContextFactory() throws NamingException {
      ClassLoader cl = this.getContextClassLoader();
      synchronized(factoryCache) {
         String factory = (String)factoryCache.get(cl);
         if (factory != null) {
            return factory;
         } else if (factoryCache.containsKey(cl)) {
            return null;
         } else {
            try {
               factory = this.getProperties(cl, "jndi.properties").getProperty("java.naming.factory.initial");
            } catch (IOException var7) {
               NamingException ne = new ConfigurationException("Error reading application resource file");
               ne.setRootCause(var7);
               throw ne;
            }

            factoryCache.put(cl, factory);
            return factory;
         }
      }
   }

   public final Context getInitialContext(Hashtable env) throws NamingException {
      InitialContextFactory factory = null;
      String className = this.getInitialContextFactory();
      if (className == null) {
         className = "weblogic.jndi.WLInitialContextFactory";
      }

      try {
         ClassLoader cl = this.getContextClassLoader();
         factory = (InitialContextFactory)Class.forName(className, true, cl).newInstance();
      } catch (Exception var6) {
         NoInitialContextException ne = new NoInitialContextException("Cannot instantiate class: " + className);
         ne.setRootCause(var6);
         throw ne;
      }

      return factory.getInitialContext(env);
   }
}
