package com.bea.core.repackaged.springframework.remoting.rmi;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.remoting.RemoteConnectFailureException;
import com.bea.core.repackaged.springframework.remoting.RemoteInvocationFailureException;
import com.bea.core.repackaged.springframework.remoting.RemoteLookupFailureException;
import com.bea.core.repackaged.springframework.remoting.support.RemoteInvocationBasedAccessor;
import com.bea.core.repackaged.springframework.remoting.support.RemoteInvocationUtils;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;

public class RmiClientInterceptor extends RemoteInvocationBasedAccessor implements MethodInterceptor {
   private boolean lookupStubOnStartup = true;
   private boolean cacheStub = true;
   private boolean refreshStubOnConnectFailure = false;
   private RMIClientSocketFactory registryClientSocketFactory;
   private Remote cachedStub;
   private final Object stubMonitor = new Object();

   public void setLookupStubOnStartup(boolean lookupStubOnStartup) {
      this.lookupStubOnStartup = lookupStubOnStartup;
   }

   public void setCacheStub(boolean cacheStub) {
      this.cacheStub = cacheStub;
   }

   public void setRefreshStubOnConnectFailure(boolean refreshStubOnConnectFailure) {
      this.refreshStubOnConnectFailure = refreshStubOnConnectFailure;
   }

   public void setRegistryClientSocketFactory(RMIClientSocketFactory registryClientSocketFactory) {
      this.registryClientSocketFactory = registryClientSocketFactory;
   }

   public void afterPropertiesSet() {
      super.afterPropertiesSet();
      this.prepare();
   }

   public void prepare() throws RemoteLookupFailureException {
      if (this.lookupStubOnStartup) {
         Remote remoteObj = this.lookupStub();
         if (this.logger.isDebugEnabled()) {
            if (remoteObj instanceof RmiInvocationHandler) {
               this.logger.debug("RMI stub [" + this.getServiceUrl() + "] is an RMI invoker");
            } else if (this.getServiceInterface() != null) {
               boolean isImpl = this.getServiceInterface().isInstance(remoteObj);
               this.logger.debug("Using service interface [" + this.getServiceInterface().getName() + "] for RMI stub [" + this.getServiceUrl() + "] - " + (!isImpl ? "not " : "") + "directly implemented");
            }
         }

         if (this.cacheStub) {
            this.cachedStub = remoteObj;
         }
      }

   }

   protected Remote lookupStub() throws RemoteLookupFailureException {
      try {
         Remote stub = null;
         if (this.registryClientSocketFactory != null) {
            URL url = new URL((URL)null, this.getServiceUrl(), new DummyURLStreamHandler());
            String protocol = url.getProtocol();
            if (protocol != null && !"rmi".equals(protocol)) {
               throw new MalformedURLException("Invalid URL scheme '" + protocol + "'");
            }

            String host = url.getHost();
            int port = url.getPort();
            String name = url.getPath();
            if (name != null && name.startsWith("/")) {
               name = name.substring(1);
            }

            Registry registry = LocateRegistry.getRegistry(host, port, this.registryClientSocketFactory);
            stub = registry.lookup(name);
         } else {
            stub = Naming.lookup(this.getServiceUrl());
         }

         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Located RMI stub with URL [" + this.getServiceUrl() + "]");
         }

         return stub;
      } catch (MalformedURLException var8) {
         throw new RemoteLookupFailureException("Service URL [" + this.getServiceUrl() + "] is invalid", var8);
      } catch (NotBoundException var9) {
         throw new RemoteLookupFailureException("Could not find RMI service [" + this.getServiceUrl() + "] in RMI registry", var9);
      } catch (RemoteException var10) {
         throw new RemoteLookupFailureException("Lookup of RMI stub failed", var10);
      }
   }

   protected Remote getStub() throws RemoteLookupFailureException {
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
      Remote stub = this.getStub();

      try {
         return this.doInvoke(invocation, stub);
      } catch (RemoteConnectFailureException var4) {
         return this.handleRemoteConnectFailure(invocation, var4);
      } catch (RemoteException var5) {
         if (this.isConnectFailure(var5)) {
            return this.handleRemoteConnectFailure(invocation, var5);
         } else {
            throw var5;
         }
      }
   }

   protected boolean isConnectFailure(RemoteException ex) {
      return RmiClientInterceptorUtils.isConnectFailure(ex);
   }

   @Nullable
   private Object handleRemoteConnectFailure(MethodInvocation invocation, Exception ex) throws Throwable {
      if (this.refreshStubOnConnectFailure) {
         String msg = "Could not connect to RMI service [" + this.getServiceUrl() + "] - retrying";
         if (this.logger.isDebugEnabled()) {
            this.logger.warn(msg, ex);
         } else if (this.logger.isWarnEnabled()) {
            this.logger.warn(msg);
         }

         return this.refreshAndRetry(invocation);
      } else {
         throw ex;
      }
   }

   @Nullable
   protected Object refreshAndRetry(MethodInvocation invocation) throws Throwable {
      Remote freshStub = null;
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
   protected Object doInvoke(MethodInvocation invocation, Remote stub) throws Throwable {
      Throwable targetEx;
      if (stub instanceof RmiInvocationHandler) {
         try {
            return this.doInvoke(invocation, (RmiInvocationHandler)stub);
         } catch (RemoteException var6) {
            throw RmiClientInterceptorUtils.convertRmiAccessException(invocation.getMethod(), var6, this.isConnectFailure(var6), this.getServiceUrl());
         } catch (InvocationTargetException var7) {
            targetEx = var7.getTargetException();
            RemoteInvocationUtils.fillInClientStackTraceIfPossible(targetEx);
            throw targetEx;
         } catch (Throwable var8) {
            throw new RemoteInvocationFailureException("Invocation of method [" + invocation.getMethod() + "] failed in RMI service [" + this.getServiceUrl() + "]", var8);
         }
      } else {
         try {
            return RmiClientInterceptorUtils.invokeRemoteMethod(invocation, stub);
         } catch (InvocationTargetException var9) {
            targetEx = var9.getTargetException();
            if (targetEx instanceof RemoteException) {
               RemoteException rex = (RemoteException)targetEx;
               throw RmiClientInterceptorUtils.convertRmiAccessException(invocation.getMethod(), rex, this.isConnectFailure(rex), this.getServiceUrl());
            } else {
               throw targetEx;
            }
         }
      }
   }

   @Nullable
   protected Object doInvoke(MethodInvocation methodInvocation, RmiInvocationHandler invocationHandler) throws RemoteException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
      return AopUtils.isToStringMethod(methodInvocation.getMethod()) ? "RMI invoker proxy for service URL [" + this.getServiceUrl() + "]" : invocationHandler.invoke(this.createRemoteInvocation(methodInvocation));
   }

   private static class DummyURLStreamHandler extends URLStreamHandler {
      private DummyURLStreamHandler() {
      }

      protected URLConnection openConnection(URL url) throws IOException {
         throw new UnsupportedOperationException();
      }

      // $FF: synthetic method
      DummyURLStreamHandler(Object x0) {
         this();
      }
   }
}
