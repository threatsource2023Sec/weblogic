package org.jboss.weld.bean.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.exceptions.UnproxyableResolutionException;
import org.jboss.weld.logging.ValidatorLogger;
import org.jboss.weld.util.Proxies;

public final class DefaultProxyInstantiator implements ProxyInstantiator {
   public static final ProxyInstantiator INSTANCE = new DefaultProxyInstantiator();

   private DefaultProxyInstantiator() {
   }

   public Object newInstance(Class clazz) throws InstantiationException, IllegalAccessException {
      return clazz.newInstance();
   }

   public UnproxyableResolutionException validateNoargConstructor(Constructor constructor, Class clazz, Bean declaringBean) throws UnproxyableResolutionException {
      if (constructor == null) {
         return ValidatorLogger.LOG.notProxyableNoConstructor(clazz, Proxies.getDeclaringBeanInfo(declaringBean));
      } else {
         return Modifier.isPrivate(constructor.getModifiers()) ? new UnproxyableResolutionException(ValidatorLogger.LOG.notProxyablePrivateConstructor(clazz.getName(), constructor, Proxies.getDeclaringBeanInfo(declaringBean))) : null;
      }
   }

   public boolean isUsingConstructor() {
      return true;
   }

   public void cleanup() {
   }
}
