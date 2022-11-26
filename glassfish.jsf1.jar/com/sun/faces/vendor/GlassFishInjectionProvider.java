package com.sun.faces.vendor;

import com.sun.enterprise.ComponentInvocation;
import com.sun.enterprise.InjectionException;
import com.sun.enterprise.InjectionManager;
import com.sun.enterprise.InvocationManager;
import com.sun.enterprise.Switch;
import com.sun.enterprise.deployment.InjectionInfo;
import com.sun.enterprise.deployment.JndiNameEnvironment;
import com.sun.faces.spi.DiscoverableInjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import com.sun.faces.util.FacesLogger;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedExceptionAction;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;

public class GlassFishInjectionProvider extends DiscoverableInjectionProvider {
   private static final Logger LOGGER;
   private InjectionManager injectionManager;
   private Switch theSwitch = Switch.getSwitch();
   private InvocationManager invokeMgr;
   private Method getInjectionInfoMethod;
   private boolean usingNewAPI;

   public GlassFishInjectionProvider() {
      this.invokeMgr = this.theSwitch.getInvocationManager();
      this.injectionManager = this.theSwitch.getInjectionManager();
   }

   public void inject(Object managedBean) throws InjectionProviderException {
      try {
         this.injectionManager.injectInstance(managedBean, this.getNamingEnvironment(), false);
      } catch (InjectionException var3) {
         throw new InjectionProviderException(var3);
      }
   }

   public void invokePreDestroy(Object managedBean) throws InjectionProviderException {
      try {
         this.injectionManager.invokeInstancePreDestroy(managedBean);
      } catch (InjectionException var3) {
         throw new InjectionProviderException(var3);
      }
   }

   public void invokePostConstruct(Object managedBean) throws InjectionProviderException {
      try {
         this.invokePostConstruct(managedBean, this.getNamingEnvironment());
      } catch (InjectionException var3) {
         throw new InjectionProviderException(var3);
      }
   }

   private JndiNameEnvironment getNamingEnvironment() throws InjectionException {
      ComponentInvocation inv = this.invokeMgr.getCurrentInvocation();
      if (inv != null) {
         JndiNameEnvironment componentEnv = (JndiNameEnvironment)this.theSwitch.getDescriptorFor(inv.getContainerContext());
         if (componentEnv != null) {
            return componentEnv;
         } else {
            throw new InjectionException("No descriptor registered for  current invocation : " + inv);
         }
      } else {
         throw new InjectionException("null invocation context");
      }
   }

   private void invokePostConstruct(Object instance, JndiNameEnvironment envDescriptor) throws InjectionException {
      LinkedList postConstructMethods = new LinkedList();

      for(Class nextClass = instance.getClass(); !Object.class.equals(nextClass) && nextClass != null; nextClass = nextClass.getSuperclass()) {
         Object argument = this.usingNewAPI(envDescriptor) ? nextClass : nextClass.getName();

         InjectionInfo injInfo;
         try {
            injInfo = (InjectionInfo)this.getInjectionInfoMethod.invoke(envDescriptor, argument);
         } catch (Exception var8) {
            throw new InjectionException(var8.getMessage());
         }

         if (injInfo.getPostConstructMethodName() != null) {
            Method postConstructMethod = this.getPostConstructMethod(injInfo, nextClass);
            postConstructMethods.addFirst(postConstructMethod);
         }
      }

      Iterator i$ = postConstructMethods.iterator();

      while(i$.hasNext()) {
         Method postConstructMethod = (Method)i$.next();
         this.invokeLifecycleMethod(postConstructMethod, instance);
      }

   }

   private Method getPostConstructMethod(InjectionInfo injInfo, Class resourceClass) throws InjectionException {
      Method m = injInfo.getPostConstructMethod();
      if (m == null) {
         String postConstructMethodName = injInfo.getPostConstructMethodName();
         Method[] arr$ = resourceClass.getDeclaredMethods();
         int len$ = arr$.length;

         for(int i$ = 0; i$ < len$; ++i$) {
            Method next = arr$[i$];
            if (next.getName().equals(postConstructMethodName) && next.getParameterTypes().length == 0) {
               m = next;
               injInfo.setPostConstructMethod(next);
               break;
            }
         }
      }

      if (m == null) {
         throw new InjectionException("InjectionManager exception. PostConstruct method " + injInfo.getPostConstructMethodName() + " could not be found in class " + injInfo.getClassName());
      } else {
         return m;
      }
   }

   private void invokeLifecycleMethod(final Method lifecycleMethod, final Object instance) throws InjectionException {
      try {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("Calling lifecycle method " + lifecycleMethod + " on class " + lifecycleMethod.getDeclaringClass());
         }

         AccessController.doPrivileged(new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               if (!lifecycleMethod.isAccessible()) {
                  lifecycleMethod.setAccessible(true);
               }

               lifecycleMethod.invoke(instance);
               return null;
            }
         });
      } catch (Exception var7) {
         String msg = "Exception attempting invoke lifecycle  method " + lifecycleMethod;
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, msg, var7);
         }

         InjectionException ie = new InjectionException(msg);
         Throwable cause = var7 instanceof InvocationTargetException ? var7.getCause() : var7;
         ie.initCause((Throwable)cause);
         throw ie;
      }
   }

   private boolean usingNewAPI(JndiNameEnvironment envDescriptor) {
      if (this.getInjectionInfoMethod == null) {
         try {
            this.getInjectionInfoMethod = envDescriptor.getClass().getMethod("getInjectionInfoByClass", String.class);
            this.usingNewAPI = false;
         } catch (NoSuchMethodException var5) {
            try {
               this.getInjectionInfoMethod = envDescriptor.getClass().getMethod("getInjectionInfoByClass", Class.class);
               this.usingNewAPI = true;
            } catch (NoSuchMethodException var4) {
               throw new FacesException(var4);
            }
         }
      }

      return this.usingNewAPI;
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }
}
