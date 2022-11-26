package com.bea.core.repackaged.springframework.ejb.access;

import com.bea.core.repackaged.aopalliance.intercept.MethodInterceptor;
import com.bea.core.repackaged.aopalliance.intercept.MethodInvocation;
import com.bea.core.repackaged.springframework.jndi.JndiObjectLocator;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.naming.Context;
import javax.naming.NamingException;

public abstract class AbstractSlsbInvokerInterceptor extends JndiObjectLocator implements MethodInterceptor {
   private boolean lookupHomeOnStartup = true;
   private boolean cacheHome = true;
   private boolean exposeAccessContext = false;
   @Nullable
   private Object cachedHome;
   @Nullable
   private Method createMethod;
   private final Object homeMonitor = new Object();

   public void setLookupHomeOnStartup(boolean lookupHomeOnStartup) {
      this.lookupHomeOnStartup = lookupHomeOnStartup;
   }

   public void setCacheHome(boolean cacheHome) {
      this.cacheHome = cacheHome;
   }

   public void setExposeAccessContext(boolean exposeAccessContext) {
      this.exposeAccessContext = exposeAccessContext;
   }

   public void afterPropertiesSet() throws NamingException {
      super.afterPropertiesSet();
      if (this.lookupHomeOnStartup) {
         this.refreshHome();
      }

   }

   protected void refreshHome() throws NamingException {
      synchronized(this.homeMonitor) {
         Object home = this.lookup();
         if (this.cacheHome) {
            this.cachedHome = home;
            this.createMethod = this.getCreateMethod(home);
         }

      }
   }

   @Nullable
   protected Method getCreateMethod(Object home) throws EjbAccessException {
      try {
         return home.getClass().getMethod("create");
      } catch (NoSuchMethodException var3) {
         throw new EjbAccessException("EJB home [" + home + "] has no no-arg create() method");
      }
   }

   protected Object getHome() throws NamingException {
      if (!this.cacheHome || this.lookupHomeOnStartup && !this.isHomeRefreshable()) {
         return this.cachedHome != null ? this.cachedHome : this.lookup();
      } else {
         synchronized(this.homeMonitor) {
            if (this.cachedHome == null) {
               this.cachedHome = this.lookup();
               this.createMethod = this.getCreateMethod(this.cachedHome);
            }

            return this.cachedHome;
         }
      }
   }

   protected boolean isHomeRefreshable() {
      return false;
   }

   @Nullable
   public Object invoke(MethodInvocation invocation) throws Throwable {
      Context ctx = this.exposeAccessContext ? this.getJndiTemplate().getContext() : null;

      Object var3;
      try {
         var3 = this.invokeInContext(invocation);
      } finally {
         this.getJndiTemplate().releaseContext(ctx);
      }

      return var3;
   }

   @Nullable
   protected abstract Object invokeInContext(MethodInvocation var1) throws Throwable;

   protected Object create() throws NamingException, InvocationTargetException {
      try {
         Object home = this.getHome();
         Method createMethodToUse = this.createMethod;
         if (createMethodToUse == null) {
            createMethodToUse = this.getCreateMethod(home);
         }

         return createMethodToUse == null ? home : createMethodToUse.invoke(home, (Object[])null);
      } catch (IllegalAccessException var3) {
         throw new EjbAccessException("Could not access EJB home create() method", var3);
      }
   }
}
