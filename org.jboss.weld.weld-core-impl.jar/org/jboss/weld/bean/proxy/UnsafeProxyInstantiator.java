package org.jboss.weld.bean.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.exceptions.UnproxyableResolutionException;
import sun.misc.Unsafe;

class UnsafeProxyInstantiator implements ProxyInstantiator {
   private final Unsafe unsafe;

   UnsafeProxyInstantiator() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
      Field field = Unsafe.class.getDeclaredField("theUnsafe");
      field.setAccessible(true);
      this.unsafe = (Unsafe)field.get((Object)null);
   }

   public Object newInstance(Class clazz) throws InstantiationException {
      return this.unsafe.allocateInstance(clazz);
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
