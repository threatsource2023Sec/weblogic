package weblogic.connector.utils;

import weblogic.connector.ConnectorLogger;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.PartitionTable;

public class PartitionUtils {
   public static final String DOMAIN_SCOPE = PartitionTable.getInstance().getGlobalPartitionName();
   private static final String PTN_DELIMITER = "$";
   private static ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance();

   public static String getCurrentCICInfo() {
      return cicm.getCurrentComponentInvocationContext().toString();
   }

   public static String getPartitionName() {
      String name = cicm.getCurrentComponentInvocationContext().getPartitionName();
      return name != null && name.length() != 0 ? name : DOMAIN_SCOPE;
   }

   public static boolean isDomainScope(String partitionName) {
      return partitionName == null || partitionName.length() == 0 || partitionName.equals(DOMAIN_SCOPE);
   }

   public static boolean isDomainScope() {
      return isDomainScope(getPartitionName());
   }

   public static String appendPartitionName(String name, String partitionName) {
      if (isDomainScope(partitionName)) {
         return name;
      } else {
         StringBuilder sb = new StringBuilder();
         sb.append(name).append("$").append(partitionName);
         return sb.toString();
      }
   }

   public static String getPartitionNameFromLoggerKey(String key) {
      int hash = key.indexOf("$");
      return hash > 0 ? key.substring(hash + 1) : DOMAIN_SCOPE;
   }

   public static String getJNDINameFromLoggerKey(String key) {
      int hash = key.indexOf("$");
      return hash > 0 ? key.substring(0, hash) : key;
   }

   public static void checkPartition(String partitionName) {
      String currentPartition = getPartitionName();
      if (!currentPartition.equals(partitionName)) {
         throw new IllegalStateException(ConnectorLogger.getAccessOutsidePartition(partitionName, currentPartition));
      }
   }
}
