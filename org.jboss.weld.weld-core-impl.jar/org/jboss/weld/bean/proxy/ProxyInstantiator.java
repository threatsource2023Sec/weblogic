package org.jboss.weld.bean.proxy;

import java.lang.reflect.Constructor;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.bootstrap.api.Service;
import org.jboss.weld.config.ConfigurationKey;
import org.jboss.weld.config.WeldConfiguration;
import org.jboss.weld.exceptions.UnproxyableResolutionException;
import org.jboss.weld.exceptions.WeldException;
import org.jboss.weld.logging.BootstrapLogger;
import org.jboss.weld.resources.ClassLoaderResourceLoader;
import org.jboss.weld.util.collections.ImmutableList;
import org.jboss.weld.util.reflection.Reflections;

public interface ProxyInstantiator extends Service {
   Object newInstance(Class var1) throws InstantiationException, IllegalAccessException;

   UnproxyableResolutionException validateNoargConstructor(Constructor var1, Class var2, Bean var3) throws UnproxyableResolutionException;

   boolean isUsingConstructor();

   public static class Factory {
      private static final List IMPLEMENTATIONS = ImmutableList.of(UnsafeProxyInstantiator.class.getName(), ReflectionFactoryProxyInstantiator.class.getName());

      private Factory() {
      }

      public static ProxyInstantiator create(boolean relaxedConstruction) {
         ProxyInstantiator result = DefaultProxyInstantiator.INSTANCE;
         if (relaxedConstruction) {
            Iterator var2 = IMPLEMENTATIONS.iterator();

            while(var2.hasNext()) {
               String implementation = (String)var2.next();

               try {
                  result = newInstance(implementation);
                  break;
               } catch (Exception var5) {
                  BootstrapLogger.LOG.catchingDebug(var5);
               } catch (LinkageError var6) {
                  BootstrapLogger.LOG.catchingDebug(var6);
               }
            }
         }

         return result;
      }

      public static ProxyInstantiator create(WeldConfiguration configuration) {
         ProxyInstantiator result = null;
         String instantiator = configuration.getStringProperty(ConfigurationKey.PROXY_INSTANTIATOR);
         if (!instantiator.isEmpty()) {
            if (!DefaultProxyInstantiator.class.getName().equals(instantiator)) {
               try {
                  result = newInstance(instantiator);
               } catch (Exception var4) {
                  throw new WeldException(var4);
               }
            } else {
               result = DefaultProxyInstantiator.INSTANCE;
            }
         } else {
            result = create(configuration.getBooleanProperty(ConfigurationKey.RELAXED_CONSTRUCTION) || configuration.getBooleanProperty(ConfigurationKey.PROXY_UNSAFE));
         }

         BootstrapLogger.LOG.debugv("Using instantiator: {0}", result.getClass().getName());
         return result;
      }

      private static ProxyInstantiator newInstance(String implementation) throws InstantiationException, IllegalAccessException {
         if (DefaultProxyInstantiator.class.getName().equals(implementation)) {
            return DefaultProxyInstantiator.INSTANCE;
         } else {
            Class clazz = Reflections.loadClass(implementation, new ClassLoaderResourceLoader(ProxyInstantiator.class.getClassLoader()));
            if (clazz == null) {
               throw new WeldException("Unable to load ProxyInstantiator implementation: " + implementation);
            } else {
               return (ProxyInstantiator)clazz.newInstance();
            }
         }
      }
   }
}
