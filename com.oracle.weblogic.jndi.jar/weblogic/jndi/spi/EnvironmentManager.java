package weblogic.jndi.spi;

import java.util.Locale;
import javax.naming.NamingException;
import weblogic.jndi.WLInitialContextFactoryDelegate;
import weblogic.utils.collections.ConcurrentHashMap;

public final class EnvironmentManager {
   public static final ConcurrentHashMap registeredFactories = new ConcurrentHashMap(11);
   private static final Object NULL_FACTORY = new Object();

   public static EnvironmentFactory getInstance(String protocol) throws NamingException {
      protocol = protocol.toLowerCase(Locale.ENGLISH);
      Object ef = registeredFactories.get(protocol);
      if (ef == null) {
         try {
            ef = Class.forName("weblogic.factories." + protocol + "." + protocol + "EnvironmentFactory", true, Thread.currentThread().getContextClassLoader()).newInstance();
         } catch (ClassNotFoundException var3) {
            ef = EnvironmentManager.DefaultFactoryMaker.DEFAULT_FACTORY;
         } catch (InstantiationException var4) {
            ef = NULL_FACTORY;
         } catch (IllegalAccessException var5) {
            ef = NULL_FACTORY;
         }

         registeredFactories.put(protocol, ef);
      }

      if (ef == NULL_FACTORY) {
         throw new NamingException("No registered factory for " + protocol);
      } else {
         return (EnvironmentFactory)ef;
      }
   }

   private static final class DefaultFactoryMaker {
      private static final EnvironmentFactory DEFAULT_FACTORY = WLInitialContextFactoryDelegate.theOne();
   }
}
