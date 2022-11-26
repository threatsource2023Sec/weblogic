package weblogic.jms.common;

import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.kernel.KernelStatus;

public class PartitionUtils {
   public static final String JNDI_NAME_DELIMITER = "!@";
   public static final String PARTITION_NAME_DELIMITER = "$";

   public static String getPartitionName() {
      String partitionName = "DOMAIN";
      if (KernelStatus.isServer()) {
         ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
         if (cic != null && cic.getPartitionName() != null) {
            partitionName = cic.getPartitionName();
         }
      }

      return partitionName;
   }

   public static boolean isSamePartition(String partitionName, String storedPartitionName) {
      if (isDomain(partitionName)) {
         partitionName = "DOMAIN";
      }

      if (isDomain(storedPartitionName)) {
         storedPartitionName = "DOMAIN";
      }

      return partitionName.equals(storedPartitionName);
   }

   public static boolean isDomain(String partitionName) {
      return partitionName == null || partitionName.isEmpty() || partitionName.equalsIgnoreCase("DOMAIN");
   }

   public static String stripDecoratedPartitionName(String partitionName, String name) {
      if (name == null) {
         return name;
      } else {
         if (name.endsWith("$" + partitionName)) {
            int index = name.lastIndexOf("$");
            name = name.substring(0, index);
         }

         return name;
      }
   }

   public static String stripDecoratedPartitionNamesFromCombinedName(String partitionName, String str) {
      return stripDecoratedPartitionNamesFromCombinedName(partitionName, "!@", str);
   }

   public static String stripDecoratedPartitionNamesFromCombinedName(String partitionName, String tokens, String str) {
      if (tokens != null && str != null) {
         StringBuffer result = new StringBuffer(128);
         StringBuffer buf = new StringBuffer(128);
         char[] chStr = str.toCharArray();
         char[] delimiters = tokens.toCharArray();
         char[] var7 = chStr;
         int var8 = chStr.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            char c = var7[var9];
            boolean found = false;
            char[] var12 = delimiters;
            int var13 = delimiters.length;

            for(int var14 = 0; var14 < var13; ++var14) {
               char d = var12[var14];
               if (c == d) {
                  found = true;
                  break;
               }
            }

            if (!found) {
               buf.append(c);
            } else {
               if (buf.length() > 0) {
                  result.append(stripDecoratedPartitionName(partitionName, buf.toString()));
                  buf.delete(0, buf.length());
               }

               result.append(c);
            }
         }

         if (buf.length() > 0) {
            result.append(stripDecoratedPartitionName(partitionName, buf.toString()));
         }

         return result.toString();
      } else {
         return str;
      }
   }
}
