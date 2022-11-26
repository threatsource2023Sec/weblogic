package weblogic.diagnostics.harvester.internal;

import java.beans.BeanInfo;
import javax.management.ObjectName;
import weblogic.diagnostics.harvester.HarvesterRuntimeException;
import weblogic.management.mbeanservers.runtime.internal.DiagnosticSupportService;

public final class TreeBeanHarvestableDataProviderHelper {
   public static boolean isAvailable() {
      return TreeBeanHarvestableDataProviderHelper.DiagnosticSupportServiceInitializer.isAvailable();
   }

   public static BeanInfo getBeanInfo(String typeName) {
      try {
         return TreeBeanHarvestableDataProviderHelper.DiagnosticSupportServiceInitializer.getService().getBeanInfo(typeName);
      } catch (Exception var2) {
         throw new HarvesterRuntimeException(var2);
      }
   }

   public static String getObjectNameForBean(Object instance) {
      try {
         return TreeBeanHarvestableDataProviderHelper.DiagnosticSupportServiceInitializer.getService().getObjectIdentifier(instance);
      } catch (Exception var2) {
         throw new HarvesterRuntimeException(var2);
      }
   }

   public static ObjectName getObjectNameForInstance(Object instance) {
      try {
         return TreeBeanHarvestableDataProviderHelper.DiagnosticSupportServiceInitializer.getService().getObjectNameForInstance(instance);
      } catch (Exception var2) {
         throw new HarvesterRuntimeException(var2);
      }
   }

   public static String getRegisteredObjectNameForBean(Object instance) {
      try {
         return TreeBeanHarvestableDataProviderHelper.DiagnosticSupportServiceInitializer.getService().getRegisteredObjectIdentifier(instance);
      } catch (Exception var2) {
         throw new HarvesterRuntimeException(var2);
      }
   }

   public static ObjectName getRegisteredObjectNameForInstance(Object instance) {
      try {
         return TreeBeanHarvestableDataProviderHelper.DiagnosticSupportServiceInitializer.getService().getRegisteredObjectName(instance);
      } catch (Exception var2) {
         throw new HarvesterRuntimeException(var2);
      }
   }

   static void unregisterInstance(Object instance) {
      try {
         TreeBeanHarvestableDataProviderHelper.DiagnosticSupportServiceInitializer.getService().unregisterInstance(instance);
      } catch (Exception var2) {
         throw new HarvesterRuntimeException(var2);
      }
   }

   public static String getTypeNameForInstance(String instanceNameToFindTypeFor) {
      try {
         return TreeBeanHarvestableDataProviderHelper.DiagnosticSupportServiceInitializer.getService().getInterfaceClassForObjectIdentifier(instanceNameToFindTypeFor);
      } catch (Exception var2) {
         throw new HarvesterRuntimeException(var2);
      }
   }

   public static String getTypeNameForInstance(ObjectName instanceNameToFindTypeFor) {
      try {
         return TreeBeanHarvestableDataProviderHelper.DiagnosticSupportServiceInitializer.getService().getInterfaceClassForObjectIdentifier(instanceNameToFindTypeFor);
      } catch (Exception var2) {
         throw new HarvesterRuntimeException(var2);
      }
   }

   public static Object getInstanceForObjectIdentifier(String instanceName) {
      try {
         return TreeBeanHarvestableDataProviderHelper.DiagnosticSupportServiceInitializer.getService().getInstanceForObjectIdentifier(instanceName);
      } catch (Exception var2) {
         throw new HarvesterRuntimeException(var2);
      }
   }

   private static class DiagnosticSupportServiceInitializer {
      private static DiagnosticSupportService DIAG_SUPPORT_SVC;

      public static DiagnosticSupportService getService() {
         if (DIAG_SUPPORT_SVC == null) {
            DIAG_SUPPORT_SVC = DiagnosticSupportService.getDiagnosticSupportService();
         }

         return DIAG_SUPPORT_SVC;
      }

      public static boolean isAvailable() {
         return getService() != null;
      }
   }
}
