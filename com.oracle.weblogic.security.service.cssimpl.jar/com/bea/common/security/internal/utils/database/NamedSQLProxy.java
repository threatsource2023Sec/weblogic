package com.bea.common.security.internal.utils.database;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.AccessController;
import java.sql.SQLException;

public class NamedSQLProxy implements InvocationHandler {
   private Object target = null;
   private ASIDBPoolConnection conn = null;
   private Interceptor interceptor = null;

   private NamedSQLProxy(Interceptor interceptor) {
      this.interceptor = interceptor;
   }

   private Object proxy(Object delegate, Class[] intfs, ASIDBPoolConnection conn) {
      this.target = delegate;
      this.conn = conn;
      Object proxy = Proxy.newProxyInstance(conn.getClass().getClassLoader(), intfs, this);
      return proxy;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      if (AccessController.class.isAssignableFrom(method.getDeclaringClass())) {
         throw new IllegalArgumentException("Unable to invoke method");
      } else {
         try {
            return this.interceptor != null && this.interceptor.handle(method) ? this.interceptor.invoke(proxy, method, args) : method.invoke(this.target, args);
         } catch (Throwable var5) {
            if (var5.getCause() instanceof SQLException && this.conn.isConnectionError((SQLException)var5.getCause()) && this.conn.isConnectionErrorOnExceptionCount()) {
               this.conn.getPool().setPoolState(0);
            }

            throw var5.getCause();
         }
      }
   }

   static Object createProxy(ASIDBPoolConnection conn, Object target, Class[] intfs, Interceptor interceptor) {
      NamedSQLProxy delegate = new NamedSQLProxy(interceptor);
      return delegate.proxy(target, intfs, conn);
   }

   interface Interceptor {
      boolean handle(Method var1);

      Object invoke(Object var1, Method var2, Object[] var3) throws Throwable;
   }
}
