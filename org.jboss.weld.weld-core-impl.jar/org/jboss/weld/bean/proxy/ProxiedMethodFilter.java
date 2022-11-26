package org.jboss.weld.bean.proxy;

import java.lang.reflect.Method;

@FunctionalInterface
public interface ProxiedMethodFilter {
   default boolean isEnabled() {
      return true;
   }

   boolean accept(Method var1, Class var2);
}
