package weblogic.jms.utils;

import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.invocation.PartitionTable;

public class PartitionUtils {
   public static final String DOMAIN_SCOPE = PartitionTable.getInstance().getGlobalPartitionName();
   private static final String PTN_DELIMITER = "$";
   private static ComponentInvocationContextManager cicm = ComponentInvocationContextManager.getInstance();

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
}
