package weblogic.application.partition;

import java.security.AccessController;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import weblogic.application.internal.ClassLoaders;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.management.configuration.PartitionMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.work.j2ee.J2EEWorkManager;

public class PartitionUpdateListener implements BeanUpdateListener {
   DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugAppContainer");

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      BeanUpdateEvent.PropertyUpdate[] updated = event.getUpdateList();
      BeanUpdateEvent.PropertyUpdate[] var3 = updated;
      int var4 = updated.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         BeanUpdateEvent.PropertyUpdate propertyUpdate = var3[var5];
         String name;
         PartitionMBean oldPartition;
         switch (propertyUpdate.getUpdateType()) {
            case 2:
               name = propertyUpdate.getPropertyName();
               if ("Partitions".equals(name)) {
                  oldPartition = (PartitionMBean)propertyUpdate.getAddedObject();
                  AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
                  ComponentInvocationContextManager cicManager = ComponentInvocationContextManager.getInstance(KERNEL_ID);
                  ComponentInvocationContext cic = cicManager.createComponentInvocationContext(oldPartition.getName());
                  Context ctx = null;

                  try {
                     ManagedInvocationContext mic = cicManager.setCurrentComponentInvocationContext(cic);
                     Throwable var14 = null;

                     try {
                        ctx = new InitialContext();
                        this.addJavaEEGlobalJNDINode(ctx);
                     } catch (Throwable var37) {
                        var14 = var37;
                        throw var37;
                     } finally {
                        if (mic != null) {
                           if (var14 != null) {
                              try {
                                 mic.close();
                              } catch (Throwable var36) {
                                 var14.addSuppressed(var36);
                              }
                           } else {
                              mic.close();
                           }
                        }

                     }
                  } catch (Exception var39) {
                     throw new BeanUpdateFailedException(var39.getMessage(), var39.getCause());
                  } finally {
                     try {
                        assert ctx != null;

                        ctx.close();
                     } catch (NamingException var35) {
                        this.debugLogger.debug("Exception during context.close()", var35);
                     }

                  }
               }
               break;
            case 3:
               name = propertyUpdate.getPropertyName();
               if ("Partitions".equals(name)) {
                  oldPartition = (PartitionMBean)propertyUpdate.getRemovedObject();
                  if (this.debugLogger.isDebugEnabled()) {
                     this.debugLogger.debug("Removing Partition class loader for " + oldPartition.getName());
                  }

                  if (ClassLoaders.instance.getPartitionClassLoader(oldPartition.getName()) != null) {
                     ClassLoaders.instance.destroyPartitionClassLoader(oldPartition.getName());
                  }
               }
         }
      }

   }

   public void rollbackUpdate(BeanUpdateEvent event) {
   }

   private void addJavaEEGlobalJNDINode(Context globalCtx) throws NamingException {
      try {
         globalCtx.addToEnvironment("weblogic.jndi.createIntermediateContexts", "true");
         Context globalNSCtx = globalCtx.createSubcontext("java:global");
         this.ensureWMAvailabilityOnGlobalCtx(globalNSCtx);
      } finally {
         if (globalCtx != null) {
            try {
               globalCtx.close();
            } catch (NamingException var8) {
               var8.printStackTrace();
            }
         }

      }

   }

   private void ensureWMAvailabilityOnGlobalCtx(Context javaGlobalCtx) throws NamingException {
      Context wm = javaGlobalCtx.createSubcontext("wm");
      wm.addToEnvironment("weblogic.jndi.replicateBindings", "false");

      try {
         wm.bind("default", J2EEWorkManager.getDefault());
      } catch (NameAlreadyBoundException var4) {
      }

   }
}
