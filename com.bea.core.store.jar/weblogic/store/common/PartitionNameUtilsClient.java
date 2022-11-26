package weblogic.store.common;

public class PartitionNameUtilsClient {
   public static final String COMBINED_NAME_DELIMITER = "!@";
   public static final String PERSISTENTSTORE_CONNECTION_NAME_DELIMITER = ".";
   public static final String WORKMANAGER_NAME_DELIMITER = "@";
   public static final String PARTITION_NAME_DELIMITER = "$";

   public static String stripDecoratedPartitionName(String name, String partitionName) {
      if (partitionName == null) {
         return name;
      } else {
         return name.endsWith("$" + partitionName) ? name.substring(0, name.lastIndexOf("$" + partitionName)) : name;
      }
   }

   public static String stripDecoratedPartitionNamesFromCombinedName(String tokens, String str, String partitionName) {
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
               result.append(stripDecoratedPartitionName(buf.toString(), partitionName));
               buf.delete(0, buf.length());
            }

            result.append(c);
         }
      }

      if (buf.length() > 0) {
         result.append(stripDecoratedPartitionName(buf.toString(), partitionName));
      }

      return result.toString();
   }
}
