package com.bea.core.repackaged.springframework.jca.cci.connection;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.cci.Connection;
import javax.resource.cci.ConnectionFactory;
import javax.resource.cci.ConnectionSpec;

public class SingleConnectionFactory extends DelegatingConnectionFactory implements DisposableBean {
   protected final Log logger = LogFactory.getLog(this.getClass());
   @Nullable
   private Connection target;
   @Nullable
   private Connection connection;
   private final Object connectionMonitor = new Object();

   public SingleConnectionFactory() {
   }

   public SingleConnectionFactory(Connection target) {
      Assert.notNull(target, (String)"Target Connection must not be null");
      this.target = target;
      this.connection = this.getCloseSuppressingConnectionProxy(target);
   }

   public SingleConnectionFactory(ConnectionFactory targetConnectionFactory) {
      Assert.notNull(targetConnectionFactory, (String)"Target ConnectionFactory must not be null");
      this.setTargetConnectionFactory(targetConnectionFactory);
   }

   public void afterPropertiesSet() {
      if (this.connection == null && this.getTargetConnectionFactory() == null) {
         throw new IllegalArgumentException("Connection or 'targetConnectionFactory' is required");
      }
   }

   public Connection getConnection() throws ResourceException {
      synchronized(this.connectionMonitor) {
         if (this.connection == null) {
            this.initConnection();
         }

         return this.connection;
      }
   }

   public Connection getConnection(ConnectionSpec connectionSpec) throws ResourceException {
      throw new NotSupportedException("SingleConnectionFactory does not support custom ConnectionSpec");
   }

   public void destroy() {
      this.resetConnection();
   }

   public void initConnection() throws ResourceException {
      if (this.getTargetConnectionFactory() == null) {
         throw new IllegalStateException("'targetConnectionFactory' is required for lazily initializing a Connection");
      } else {
         synchronized(this.connectionMonitor) {
            if (this.target != null) {
               this.closeConnection(this.target);
            }

            this.target = this.doCreateConnection();
            this.prepareConnection(this.target);
            if (this.logger.isDebugEnabled()) {
               this.logger.debug("Established shared CCI Connection: " + this.target);
            }

            this.connection = this.getCloseSuppressingConnectionProxy(this.target);
         }
      }
   }

   public void resetConnection() {
      synchronized(this.connectionMonitor) {
         if (this.target != null) {
            this.closeConnection(this.target);
         }

         this.target = null;
         this.connection = null;
      }
   }

   protected Connection doCreateConnection() throws ResourceException {
      ConnectionFactory connectionFactory = this.getTargetConnectionFactory();
      Assert.state(connectionFactory != null, "No 'targetConnectionFactory' set");
      return connectionFactory.getConnection();
   }

   protected void prepareConnection(Connection con) throws ResourceException {
   }

   protected void closeConnection(Connection con) {
      try {
         con.close();
      } catch (Throwable var3) {
         this.logger.warn("Could not close shared CCI Connection", var3);
      }

   }

   protected Connection getCloseSuppressingConnectionProxy(Connection target) {
      return (Connection)Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[]{Connection.class}, new CloseSuppressingInvocationHandler(target));
   }

   private static final class CloseSuppressingInvocationHandler implements InvocationHandler {
      private final Connection target;

      private CloseSuppressingInvocationHandler(Connection target) {
         this.target = target;
      }

      @Nullable
      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         if (method.getName().equals("equals")) {
            return proxy == args[0];
         } else if (method.getName().equals("hashCode")) {
            return System.identityHashCode(proxy);
         } else if (method.getName().equals("close")) {
            return null;
         } else {
            try {
               return method.invoke(this.target, args);
            } catch (InvocationTargetException var5) {
               throw var5.getTargetException();
            }
         }
      }

      // $FF: synthetic method
      CloseSuppressingInvocationHandler(Connection x0, Object x1) {
         this(x0);
      }
   }
}
