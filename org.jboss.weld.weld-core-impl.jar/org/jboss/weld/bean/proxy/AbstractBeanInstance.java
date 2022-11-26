package org.jboss.weld.bean.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.util.Proxies;

public abstract class AbstractBeanInstance implements BeanInstance {
   public Object invoke(Object instance, Method method, Object... arguments) throws Throwable {
      Object result = null;

      try {
         SecurityActions.ensureAccessible(method);
         result = method.invoke(instance, arguments);
         return result;
      } catch (InvocationTargetException var6) {
         throw var6.getCause();
      }
   }

   protected Class computeInstanceType(Bean bean) {
      return computeInstanceType(bean.getTypes());
   }

   protected static Class computeInstanceType(Set types) {
      Proxies.TypeInfo typeInfo = Proxies.TypeInfo.of(types);
      Class superClass = typeInfo.getSuperClass();
      if (superClass.equals(Object.class)) {
         superClass = typeInfo.getSuperInterface();
      }

      return superClass;
   }
}
