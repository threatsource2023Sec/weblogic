package com.bea.core.repackaged.springframework.ejb.access;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.remoting.RemoteConnectFailureException;
import com.bea.core.repackaged.springframework.remoting.RemoteLookupFailureException;
import com.bea.core.repackaged.springframework.remoting.rmi.RmiClientInterceptorUtils;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.naming.NamingException;

public abstract class AbstractRemoteSlsbInvokerInterceptor extends AbstractSlsbInvokerInterceptor {
   private boolean refreshHomeOnConnectFailure = false;
   private volatile boolean homeAsComponent = false;

   public void setRefreshHomeOnConnectFailure(boolean refreshHomeOnConnectFailure) {
      this.refreshHomeOnConnectFailure = refreshHomeOnConnectFailure;
   }

   protected boolean isHomeRefreshable() {
      return this.refreshHomeOnConnectFailure;
   }

   protected Method getCreateMethod(Object home) throws EjbAccessException {
      if (this.homeAsComponent) {
         return null;
      } else if (!(home instanceof EJBHome)) {
         this.homeAsComponent = true;
         return null;
      } else {
         return super.getCreateMethod(home);
      }
   }

   @Nullable
   public Object invokeInContext(MethodInvocation invocation) throws Throwable {
      try {
         return this.doInvoke(invocation);
      } catch (RemoteConnectFailureException var3) {
         return this.handleRemoteConnectFailure(invocation, var3);
      } catch (RemoteException var4) {
         if (this.isConnectFailure(var4)) {
            return this.handleRemoteConnectFailure(invocation, var4);
         } else {
            throw var4;
         }
      }
   }

   protected boolean isConnectFailure(RemoteException ex) {
      return RmiClientInterceptorUtils.isConnectFailure(ex);
   }

   @Nullable
   private Object handleRemoteConnectFailure(MethodInvocation invocation, Exception ex) throws Throwable {
      if (this.refreshHomeOnConnectFailure) {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Could not connect to remote EJB [" + this.getJndiName() + "] - retrying", ex);
         } else if (this.logger.isWarnEnabled()) {
            this.logger.warn("Could not connect to remote EJB [" + this.getJndiName() + "] - retrying");
         }

         return this.refreshAndRetry(invocation);
      } else {
         throw ex;
      }
   }

   @Nullable
   protected Object refreshAndRetry(MethodInvocation invocation) throws Throwable {
      try {
         this.refreshHome();
      } catch (NamingException var3) {
         throw new RemoteLookupFailureException("Failed to locate remote EJB [" + this.getJndiName() + "]", var3);
      }

      return this.doInvoke(invocation);
   }

   @Nullable
   protected abstract Object doInvoke(MethodInvocation var1) throws Throwable;

   protected Object newSessionBeanInstance() throws NamingException, InvocationTargetException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Trying to create reference to remote EJB");
      }

      Object ejbInstance = this.create();
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Obtained reference to remote EJB: " + ejbInstance);
      }

      return ejbInstance;
   }

   protected void removeSessionBeanInstance(@Nullable EJBObject ejb) {
      if (ejb != null && !this.homeAsComponent) {
         try {
            ejb.remove();
         } catch (Throwable var3) {
            this.logger.warn("Could not invoke 'remove' on remote EJB proxy", var3);
         }
      }

   }
}
