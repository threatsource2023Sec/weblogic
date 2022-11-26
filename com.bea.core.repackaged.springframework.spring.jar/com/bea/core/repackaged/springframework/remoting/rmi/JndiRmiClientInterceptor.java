package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jndi.JndiObjectLocator;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.remoting.RemoteConnectFailureException;
import com.bea.core.repackaged.springframework.remoting.RemoteInvocationFailureException;
import com.bea.core.repackaged.springframework.remoting.RemoteLookupFailureException;
import com.bea.core.repackaged.springframework.remoting.support.DefaultRemoteInvocationFactory;
import com.bea.core.repackaged.springframework.remoting.support.RemoteInvocation;
import com.bea.core.repackaged.springframework.remoting.support.RemoteInvocationFactory;
import com.bea.core.repackaged.springframework.util.Assert;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.naming.Context;
import javax.naming.NamingException;

public class JndiRmiClientInterceptor extends JndiObjectLocator implements MethodInterceptor, InitializingBean {
   private Class serviceInterface;
   private RemoteInvocationFactory remoteInvocationFactory = new DefaultRemoteInvocationFactory();
   private boolean lookupStubOnStartup = true;
   private boolean cacheStub = true;
   private boolean refreshStubOnConnectFailure = false;
   private boolean exposeAccessContext = false;
   private Object cachedStub;
   private final Object stubMonitor = new Object();

   public void setServiceInterface(Class serviceInterface) {
      Assert.notNull(serviceInterface, (String)"'serviceInterface' must not be null");
      Assert.isTrue(serviceInterface.isInterface(), "'serviceInterface' must be an interface");
      this.serviceInterface = serviceInterface;
   }

   public Class getServiceInterface() {
      return this.serviceInterface;
   }

   public void setRemoteInvocationFactory(RemoteInvocationFactory remoteInvocationFactory) {
      this.remoteInvocationFactory = remoteInvocationFactory;
   }

   public RemoteInvocationFactory getRemoteInvocationFactory() {
      return this.remoteInvocationFactory;
   }

   public void setLookupStubOnStartup(boolean lookupStubOnStartup) {
      this.lookupStubOnStartup = lookupStubOnStartup;
   }

   public void setCacheStub(boolean cacheStub) {
      this.cacheStub = cacheStub;
   }

   public void setRefreshStubOnConnectFailure(boolean refreshStubOnConnectFailure) {
      this.refreshStubOnConnectFailure = refreshStubOnConnectFailure;
   }

   public void setExposeAccessContext(boolean exposeAccessContext) {
      this.exposeAccessContext = exposeAccessContext;
   }

   public void afterPropertiesSet() throws NamingException {
      super.afterPropertiesSet();
      this.prepare();
   }

   public void prepare() throws RemoteLookupFailureException {
      if (this.lookupStubOnStartup) {
         Object remoteObj = this.lookupStub();
         if (this.logger.isDebugEnabled()) {
            if (remoteObj instanceof RmiInvocationHandler) {
               this.logger.debug("JNDI RMI object [" + this.getJndiName() + "] is an RMI invoker");
            } else if (this.getServiceInterface() != null) {
               boolean isImpl = this.getServiceInterface().isInstance(remoteObj);
               this.logger.debug("Using service interface [" + this.getServiceInterface().getName() + "] for JNDI RMI object [" + this.getJndiName() + "] - " + (!isImpl ? "not " : "") + "directly implemented");
            }
         }

         if (this.cacheStub) {
            this.cachedStub = remoteObj;
         }
      }

   }

   protected Object lookupStub() throws RemoteLookupFailureException {
      try {
         return this.lookup();
      } catch (NamingException var2) {
         throw new RemoteLookupFailureException("JNDI lookup for RMI service [" + this.getJndiName() + "] failed", var2);
      }
   }

   protected Object getStub() throws NamingException, RemoteLookupFailureException {
      if (!this.cacheStub || this.lookupStubOnStartup && !this.refreshStubOnConnectFailure) {
         return this.cachedStub != null ? this.cachedStub : this.lookupStub();
      } else {
         synchronized(this.stubMonitor) {
            if (this.cachedStub == null) {
               this.cachedStub = this.lookupStub();
            }

            return this.cachedStub;
         }
      }
   }

   public Object invoke(MethodInvocation invocation) throws Throwable {
      Object stub;
      try {
         stub = this.getStub();
      } catch (NamingException var11) {
         throw new RemoteLookupFailureException("JNDI lookup for RMI service [" + this.getJndiName() + "] failed", var11);
      }

      Context ctx = this.exposeAccessContext ? this.getJndiTemplate().getContext() : null;

      Object var5;
      try {
         Object var4 = this.doInvoke(invocation, stub);
         return var4;
      } catch (RemoteConnectFailureException var12) {
         var5 = this.handleRemoteConnectFailure(invocation, var12);
         return var5;
      } catch (RemoteException var13) {
         if (!this.isConnectFailure(var13)) {
            throw var13;
         }

         var5 = this.handleRemoteConnectFailure(invocation, var13);
      } finally {
         this.getJndiTemplate().releaseContext(ctx);
      }

      return var5;
   }

   protected boolean isConnectFailure(RemoteException ex) {
      return RmiClientInterceptorUtils.isConnectFailure(ex);
   }

   private Object handleRemoteConnectFailure(MethodInvocation invocation, Exception ex) throws Throwable {
      if (this.refreshStubOnConnectFailure) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Could not connect to RMI service [" + this.getJndiName() + "] - retrying", ex);
         } else if (this.logger.isInfoEnabled()) {
            this.logger.info("Could not connect to RMI service [" + this.getJndiName() + "] - retrying");
         }

         return this.refreshAndRetry(invocation);
      } else {
         throw ex;
      }
   }

   @Nullable
   protected Object refreshAndRetry(MethodInvocation invocation) throws Throwable {
      Object freshStub;
      synchronized(this.stubMonitor) {
         this.cachedStub = null;
         freshStub = this.lookupStub();
         if (this.cacheStub) {
            this.cachedStub = freshStub;
         }
      }

      return this.doInvoke(invocation, freshStub);
   }

   @Nullable
   protected Object doInvoke(MethodInvocation invocation, Object stub) throws Throwable {
      if (stub instanceof RmiInvocationHandler) {
         try {
            return this.doInvoke(invocation, (RmiInvocationHandler)stub);
         } catch (RemoteException var5) {
            throw this.convertRmiAccessException(var5, invocation.getMethod());
         } catch (InvocationTargetException var6) {
            throw var6.getTargetException();
         } catch (Throwable var7) {
            throw new RemoteInvocationFailureException("Invocation of method [" + invocation.getMethod() + "] failed in RMI service [" + this.getJndiName() + "]", var7);
         }
      } else {
         try {
            return RmiClientInterceptorUtils.invokeRemoteMethod(invocation, stub);
         } catch (InvocationTargetException var8) {
            Throwable targetEx = var8.getTargetException();
            if (targetEx instanceof RemoteException) {
               throw this.convertRmiAccessException((RemoteException)targetEx, invocation.getMethod());
            } else {
               throw targetEx;
            }
         }
      }
   }

   protected Object doInvoke(MethodInvocation methodInvocation, RmiInvocationHandler invocationHandler) throws RemoteException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      return AopUtils.isToStringMethod(methodInvocation.getMethod()) ? "RMI invoker proxy for service URL [" + this.getJndiName() + "]" : invocationHandler.invoke(this.createRemoteInvocation(methodInvocation));
   }

   protected RemoteInvocation createRemoteInvocation(MethodInvocation methodInvocation) {
      return this.getRemoteInvocationFactory().createRemoteInvocation(methodInvocation);
   }

   private Exception convertRmiAccessException(RemoteException ex, Method method) {
      return RmiClientInterceptorUtils.convertRmiAccessException(method, ex, this.isConnectFailure(ex), this.getJndiName());
   }
}
