package weblogic.cluster.singleton;

import org.jvnet.hk2.annotations.Contract;
import weblogic.management.DeploymentException;
import weblogic.work.StackableThreadContext;

@Contract
public interface SingletonServicesManager {
   void addConfiguredService(String var1, SingletonService var2) throws IllegalArgumentException;

   void addConfiguredService(String var1, SingletonService var2, StackableThreadContext var3) throws IllegalArgumentException;

   void add(String var1, SingletonService var2) throws IllegalArgumentException;

   void add(String var1, SingletonService var2, StackableThreadContext var3) throws IllegalArgumentException;

   void remove(String var1);

   SingletonService constructSingletonService(String var1, ClassLoader var2) throws DeploymentException;

   SingletonService constructSingletonService(String var1, ClassLoader var2, Object var3) throws DeploymentException;

   public abstract static class Util {
      static final String DELIMITER = "#";

      public static String getAppscopedSingletonServiceName(String serviceName, String versionId, String partitionName) {
         String sName = serviceName;
         if (partitionName != null && partitionName.length() > 0) {
            sName = partitionName + "." + serviceName;
         }

         return versionId != null && versionId.length() != 0 ? sName + "#" + versionId : sName;
      }
   }
}
