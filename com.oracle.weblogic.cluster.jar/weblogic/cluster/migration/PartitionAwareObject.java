package weblogic.cluster.migration;

import java.security.AccessController;
import java.util.concurrent.Callable;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class PartitionAwareObject {
   public static final String GLOBAL_PARTITION_NAME = "DOMAIN";
   public static final String DELIMITER = ".";
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private final String partitionName;
   private final Object delegate;

   public PartitionAwareObject(String partitionName, Object delegate) {
      this.partitionName = ensureDomainPartition(partitionName);
      this.delegate = delegate;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public Object getDelegate() {
      return this.delegate;
   }

   public Object runUnderPartition(Callable action) throws Exception {
      return runUnderPartition(this.partitionName, action);
   }

   public void runUnderPartition(Runnable action) {
      runUnderPartition(this.partitionName, action);
   }

   public boolean isSamePartition(String partitionName) {
      return isSamePartition(this.partitionName, partitionName);
   }

   public static Object runUnderPartition(String partitionName, Callable action) throws Exception {
      ManagedInvocationContext mic = setInvocationContext(partitionName);
      Throwable var3 = null;

      Object var4;
      try {
         var4 = action.call();
      } catch (Throwable var13) {
         var3 = var13;
         throw var13;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var12) {
                  var3.addSuppressed(var12);
               }
            } else {
               mic.close();
            }
         }

      }

      return var4;
   }

   public static void runUnderPartition(String partitionName, Runnable action) {
      ManagedInvocationContext mic = setInvocationContext(partitionName);
      Throwable var3 = null;

      try {
         action.run();
      } catch (Throwable var12) {
         var3 = var12;
         throw var12;
      } finally {
         if (mic != null) {
            if (var3 != null) {
               try {
                  mic.close();
               } catch (Throwable var11) {
                  var3.addSuppressed(var11);
               }
            } else {
               mic.close();
            }
         }

      }

   }

   protected static ManagedInvocationContext setInvocationContext(String partitionName) {
      ComponentInvocationContextManager manager = ComponentInvocationContextManager.getInstance(KERNEL_ID);
      String currentPartitionName = manager.getCurrentComponentInvocationContext().getPartitionName();
      if (currentPartitionName.equalsIgnoreCase(partitionName)) {
         return null;
      } else {
         ComponentInvocationContext cic = manager.createComponentInvocationContext(partitionName);
         return manager.setCurrentComponentInvocationContext(cic);
      }
   }

   public static boolean isSamePartition(String partitionName1, String partitionName2) {
      return isGlobalPartition(partitionName1) && isGlobalPartition(partitionName2) || partitionName1 != null && partitionName1.equals(partitionName2);
   }

   public static String ensureDomainPartition(String partitionName) {
      if (partitionName == null) {
         try {
            return ComponentInvocationContextManager.getInstance(KERNEL_ID).createComponentInvocationContext(partitionName).getPartitionName();
         } catch (Throwable var2) {
            return "DOMAIN";
         }
      } else {
         return partitionName;
      }
   }

   public static boolean isGlobalPartition(String partitionName) {
      return partitionName == null || partitionName.equals("DOMAIN");
   }

   public static String qualifyPartitionInfo(String partitionName, String name) {
      return isGlobalPartition(partitionName) ? name : partitionName + "." + name;
   }

   public static String deQualifyServiceName(String serviceName) {
      int idx = serviceName.indexOf(".");
      return idx >= 0 ? serviceName.substring(idx + 1) : serviceName;
   }

   public static String extractPartitionName(String serviceName) {
      int idx = serviceName.indexOf(".");
      return idx >= 0 ? serviceName.substring(0, idx) : "DOMAIN";
   }

   public static String getMessageInPartition(String partitionName) {
      return isGlobalPartition(partitionName) ? " in DOMAIN partition" : " in partition " + partitionName;
   }
}
