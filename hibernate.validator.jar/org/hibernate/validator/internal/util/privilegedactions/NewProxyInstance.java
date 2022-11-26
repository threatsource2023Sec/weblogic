package org.hibernate.validator.internal.util.privilegedactions;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.security.PrivilegedAction;

public final class NewProxyInstance implements PrivilegedAction {
   private final ClassLoader classLoader;
   private final Class[] interfaces;
   private final InvocationHandler invocationHandler;

   public static NewProxyInstance action(ClassLoader classLoader, Class interfaze, InvocationHandler invocationHandler) {
      return new NewProxyInstance(classLoader, interfaze, invocationHandler);
   }

   public static NewProxyInstance action(ClassLoader classLoader, Class[] interfaces, InvocationHandler invocationHandler) {
      return new NewProxyInstance(classLoader, interfaces, invocationHandler);
   }

   private NewProxyInstance(ClassLoader classLoader, Class[] interfaces, InvocationHandler invocationHandler) {
      this.classLoader = classLoader;
      this.interfaces = interfaces;
      this.invocationHandler = invocationHandler;
   }

   private NewProxyInstance(ClassLoader classLoader, Class interfaze, InvocationHandler invocationHandler) {
      this.classLoader = classLoader;
      this.interfaces = new Class[]{interfaze};
      this.invocationHandler = invocationHandler;
   }

   public Object run() {
      return Proxy.newProxyInstance(this.classLoader, this.interfaces, this.invocationHandler);
   }
}
