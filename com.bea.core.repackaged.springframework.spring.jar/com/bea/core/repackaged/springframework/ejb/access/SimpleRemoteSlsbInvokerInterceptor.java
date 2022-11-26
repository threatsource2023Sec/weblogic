package com.bea.core.repackaged.springframework.ejb.access;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.beans.factory.DisposableBean;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.remoting.RemoteLookupFailureException;
import com.bea.core.repackaged.springframework.remoting.rmi.RmiClientInterceptorUtils;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import javax.ejb.CreateException;
import javax.ejb.EJBObject;
import javax.naming.NamingException;

public class SimpleRemoteSlsbInvokerInterceptor extends AbstractRemoteSlsbInvokerInterceptor implements DisposableBean {
   private boolean cacheSessionBean = false;
   @Nullable
   private Object beanInstance;
   private final Object beanInstanceMonitor = new Object();

   public void setCacheSessionBean(boolean cacheSessionBean) {
      this.cacheSessionBean = cacheSessionBean;
   }

   @Nullable
   protected Object doInvoke(MethodInvocation invocation) throws Throwable {
      Object ejb = null;

      Object var3;
      try {
         ejb = this.getSessionBeanInstance();
         var3 = RmiClientInterceptorUtils.invokeRemoteMethod(invocation, ejb);
      } catch (NamingException var10) {
         throw new RemoteLookupFailureException("Failed to locate remote EJB [" + this.getJndiName() + "]", var10);
      } catch (InvocationTargetException var11) {
         Throwable targetEx = var11.getTargetException();
         if (targetEx instanceof RemoteException) {
            RemoteException rex = (RemoteException)targetEx;
            throw RmiClientInterceptorUtils.convertRmiAccessException(invocation.getMethod(), rex, this.isConnectFailure(rex), this.getJndiName());
         }

         if (targetEx instanceof CreateException) {
            throw RmiClientInterceptorUtils.convertRmiAccessException(invocation.getMethod(), targetEx, "Could not create remote EJB [" + this.getJndiName() + "]");
         }

         throw targetEx;
      } finally {
         if (ejb instanceof EJBObject) {
            this.releaseSessionBeanInstance((EJBObject)ejb);
         }

      }

      return var3;
   }

   protected Object getSessionBeanInstance() throws NamingException, InvocationTargetException {
      if (this.cacheSessionBean) {
         synchronized(this.beanInstanceMonitor) {
            if (this.beanInstance == null) {
               this.beanInstance = this.newSessionBeanInstance();
            }

            return this.beanInstance;
         }
      } else {
         return this.newSessionBeanInstance();
      }
   }

   protected void releaseSessionBeanInstance(EJBObject ejb) {
      if (!this.cacheSessionBean) {
         this.removeSessionBeanInstance(ejb);
      }

   }

   protected void refreshHome() throws NamingException {
      super.refreshHome();
      if (this.cacheSessionBean) {
         synchronized(this.beanInstanceMonitor) {
            this.beanInstance = null;
         }
      }

   }

   public void destroy() {
      if (this.cacheSessionBean) {
         synchronized(this.beanInstanceMonitor) {
            if (this.beanInstance instanceof EJBObject) {
               this.removeSessionBeanInstance((EJBObject)this.beanInstance);
            }
         }
      }

   }
}
