package com.bea.core.repackaged.springframework.ejb.access;

import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.ejb.CreateException;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import javax.naming.NamingException;

public class LocalSlsbInvokerInterceptor extends AbstractSlsbInvokerInterceptor {
   private volatile boolean homeAsComponent = false;

   @Nullable
   public Object invokeInContext(MethodInvocation invocation) throws Throwable {
      Object ejb = null;

      Object var5;
      try {
         ejb = this.getSessionBeanInstance();
         Method method = invocation.getMethod();
         if (method.getDeclaringClass().isInstance(ejb)) {
            Object var16 = method.invoke(ejb, invocation.getArguments());
            return var16;
         }

         Method ejbMethod = ejb.getClass().getMethod(method.getName(), method.getParameterTypes());
         var5 = ejbMethod.invoke(ejb, invocation.getArguments());
      } catch (InvocationTargetException var11) {
         Throwable targetEx = var11.getTargetException();
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Method of local EJB [" + this.getJndiName() + "] threw exception", targetEx);
         }

         if (targetEx instanceof CreateException) {
            throw new EjbAccessException("Could not create local EJB [" + this.getJndiName() + "]", targetEx);
         }

         throw targetEx;
      } catch (NamingException var12) {
         throw new EjbAccessException("Failed to locate local EJB [" + this.getJndiName() + "]", var12);
      } catch (IllegalAccessException var13) {
         throw new EjbAccessException("Could not access method [" + invocation.getMethod().getName() + "] of local EJB [" + this.getJndiName() + "]", var13);
      } finally {
         if (ejb instanceof EJBLocalObject) {
            this.releaseSessionBeanInstance((EJBLocalObject)ejb);
         }

      }

      return var5;
   }

   protected Method getCreateMethod(Object home) throws EjbAccessException {
      if (this.homeAsComponent) {
         return null;
      } else if (!(home instanceof EJBLocalHome)) {
         this.homeAsComponent = true;
         return null;
      } else {
         return super.getCreateMethod(home);
      }
   }

   protected Object getSessionBeanInstance() throws NamingException, InvocationTargetException {
      return this.newSessionBeanInstance();
   }

   protected void releaseSessionBeanInstance(EJBLocalObject ejb) {
      this.removeSessionBeanInstance(ejb);
   }

   protected Object newSessionBeanInstance() throws NamingException, InvocationTargetException {
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Trying to create reference to local EJB");
      }

      Object ejbInstance = this.create();
      if (this.logger.isDebugEnabled()) {
         this.logger.debug("Obtained reference to local EJB: " + ejbInstance);
      }

      return ejbInstance;
   }

   protected void removeSessionBeanInstance(@Nullable EJBLocalObject ejb) {
      if (ejb != null && !this.homeAsComponent) {
         try {
            ejb.remove();
         } catch (Throwable var3) {
            this.logger.warn("Could not invoke 'remove' on local EJB proxy", var3);
         }
      }

   }
}
