package weblogic.store.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.Principal;
import weblogic.kernel.KernelStatus;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;

public class PartitionNameUtils extends PartitionNameUtilsClient {
   private static final String DEFAULT_PARTITION_NAME = "DOMAIN";
   private static final String NO_PARTITION_NAME = null;
   private static final String CIC_INTERFACE_CLASS = "weblogic.invocation.ComponentInvocationContext";
   private static final String CIC_MANAGER_CLASS = "weblogic.invocation.ComponentInvocationContextManager";
   private static final Method getPartitionNameMethod;
   private static final Object CICManagerInstance;
   private static final Method getCurrentComponentInvocationContextMethod;

   public static String getPartitionName() {
      String partitionName = null;

      RuntimeException rte;
      try {
         if (KernelStatus.isServer() && CICManagerInstance != null && getCurrentComponentInvocationContextMethod != null && getPartitionNameMethod != null) {
            Object currentCIC = getCurrentComponentInvocationContextMethod.invoke(CICManagerInstance);
            partitionName = (String)getPartitionNameMethod.invoke(currentCIC);
         } else {
            partitionName = NO_PARTITION_NAME;
         }

         return partitionName;
      } catch (IllegalAccessException var3) {
         rte = new RuntimeException(var3);
         throw rte;
      } catch (InvocationTargetException var4) {
         rte = new RuntimeException(var4);
         throw rte;
      }
   }

   public static boolean isInPartition() {
      String partitionName = getPartitionName();
      return partitionName != null && !partitionName.isEmpty() && !partitionName.equals("DOMAIN");
   }

   public static String stripDecoratedPartitionName(String name) {
      return !isInPartition() ? name : stripDecoratedPartitionName(name, getPartitionName());
   }

   public static String stripDecoratedPartitionNamesFromCombinedName(String tokens, String str) {
      return !isInPartition() ? str : stripDecoratedPartitionNamesFromCombinedName(tokens, str, getPartitionName());
   }

   static {
      Class cicManagerClass = null;
      Class tCicInterfaceClass = null;
      Method tGetPartitionNameMethod = null;
      Method tGetCurrentComponentInvocationContextMethod = null;
      Object tCICManagerInstance = null;
      if (KernelStatus.isServer()) {
         try {
            tCicInterfaceClass = Class.forName("weblogic.invocation.ComponentInvocationContext");
            cicManagerClass = Class.forName("weblogic.invocation.ComponentInvocationContextManager");
            if (cicManagerClass != null && tCicInterfaceClass != null) {
               AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
               Method getManagerInstanceMethod = cicManagerClass.getMethod("getInstance", Principal.class);
               tCICManagerInstance = getManagerInstanceMethod.invoke(cicManagerClass, KERNEL_ID);
               tGetCurrentComponentInvocationContextMethod = cicManagerClass.getMethod("getCurrentComponentInvocationContext");
               tGetPartitionNameMethod = tCicInterfaceClass.getMethod("getPartitionName");
            }
         } catch (Exception var7) {
            tCicInterfaceClass = null;
            tCICManagerInstance = null;
            tGetPartitionNameMethod = null;
            tGetCurrentComponentInvocationContextMethod = null;
         }
      }

      CICManagerInstance = tCICManagerInstance;
      getPartitionNameMethod = tGetPartitionNameMethod;
      getCurrentComponentInvocationContextMethod = tGetCurrentComponentInvocationContextMethod;
   }
}
