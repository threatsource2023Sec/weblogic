package com.bea.core.repackaged.springframework.jca.cci.connection;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.spi.IllegalStateException;

public class TransactionAwareConnectionFactoryProxy extends DelegatingConnectionFactory {
   public TransactionAwareConnectionFactoryProxy() {
   }

   public TransactionAwareConnectionFactoryProxy(ConnectionFactory targetConnectionFactory) {
      this.setTargetConnectionFactory(targetConnectionFactory);
      this.afterPropertiesSet();
   }

   public Connection getConnection() throws ResourceException {
      ConnectionFactory targetConnectionFactory = this.obtainTargetConnectionFactory();
      Connection con = ConnectionFactoryUtils.doGetConnection(targetConnectionFactory);
      return this.getTransactionAwareConnectionProxy(con, targetConnectionFactory);
   }

   protected Connection getTransactionAwareConnectionProxy(Connection target, ConnectionFactory cf) {
      return (Connection)Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[]{Connection.class}, new TransactionAwareInvocationHandler(target, cf));
   }

   private static class TransactionAwareInvocationHandler implements InvocationHandler {
      private final Connection target;
      private final ConnectionFactory connectionFactory;

      public TransactionAwareInvocationHandler(Connection target, ConnectionFactory cf) {
         this.target = target;
         this.connectionFactory = cf;
      }

      @Nullable
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         if (method.getName().equals("equals")) {
            return proxy == args[0];
         } else if (method.getName().equals("hashCode")) {
            return System.identityHashCode(proxy);
         } else {
            if (method.getName().equals("getLocalTransaction")) {
               if (ConnectionFactoryUtils.isConnectionTransactional(this.target, this.connectionFactory)) {
                  throw new IllegalStateException("Local transaction handling not allowed within a managed transaction");
               }
            } else if (method.getName().equals("close")) {
               ConnectionFactoryUtils.doReleaseConnection(this.target, this.connectionFactory);
               return null;
            }

            try {
               return method.invoke(this.target, args);
            } catch (InvocationTargetException var5) {
               throw var5.getTargetException();
            }
         }
      }
   }
}
