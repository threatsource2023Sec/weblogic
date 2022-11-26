package weblogic.management.remote.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

class InvocationContextProxyHandler implements InvocationHandler {
   private Object connectionWrapper;
   private ComponentInvocationContext cic;

   public InvocationContextProxyHandler(Object connectionWrapper) {
      this(connectionWrapper, (ComponentInvocationContext)null);
   }

   public InvocationContextProxyHandler(Object connectionWrapper, ComponentInvocationContext cic) {
      this.connectionWrapper = connectionWrapper;
      this.cic = cic;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      switch (method.getName()) {
         case "hashCode":
            return this.connectionWrapper.hashCode();
         case "equals":
            return proxy == args[0];
         case "toString":
            return this.connectionWrapper.toString() + " for partition " + (this.cic == null ? "DOMAIN" : this.cic.getPartitionName());
         default:
            try {
               if (this.cic == null) {
                  return method.invoke(this.connectionWrapper, args);
               } else {
                  AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
                  ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance(KERNEL_ID);
                  ManagedInvocationContext mic = cicm.setCurrentComponentInvocationContext(this.cic);
                  Throwable var7 = null;

                  Object var8;
                  try {
                     var8 = method.invoke(this.connectionWrapper, args);
                  } catch (Throwable var19) {
                     var7 = var19;
                     throw var19;
                  } finally {
                     if (mic != null) {
                        if (var7 != null) {
                           try {
                              mic.close();
                           } catch (Throwable var18) {
                              var7.addSuppressed(var18);
                           }
                        } else {
                           mic.close();
                        }
                     }

                  }

                  return var8;
               }
            } catch (InvocationTargetException var21) {
               throw var21.getCause();
            } catch (IllegalAccessException var22) {
               throw var22.getCause();
            }
      }
   }
}
