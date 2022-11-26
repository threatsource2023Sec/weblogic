package org.jboss.weld.bean.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.exceptions.UnproxyableResolutionException;
import org.jboss.weld.exceptions.WeldException;
import sun.reflect.ReflectionFactory;

class ReflectionFactoryProxyInstantiator implements ProxyInstantiator {
   private final ReflectionFactory factory = ReflectionFactory.getReflectionFactory();
   private final Constructor constructor;

   ReflectionFactoryProxyInstantiator() {
      try {
         this.constructor = Object.class.getConstructor();
      } catch (NoSuchMethodException var2) {
         throw new WeldException(var2);
      }
   }

   public Object newInstance(Class clazz) throws InstantiationException, IllegalAccessException {
      try {
         return this.factory.newConstructorForSerialization(clazz, this.constructor).newInstance();
      } catch (IllegalArgumentException var3) {
         throw new WeldException(var3);
      } catch (InvocationTargetException var4) {
         throw new WeldException(var4);
      }
   }

   public boolean isUsingConstructor() {
      return false;
   }

   public void cleanup() {
   }

   public UnproxyableResolutionException validateNoargConstructor(Constructor constructor, Class clazz, Bean declaringBean) throws UnproxyableResolutionException {
      return null;
   }
}
