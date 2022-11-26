package weblogic.invocation;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.ServiceLoader;

public abstract class PartitionTable {
   public static final String GLOBAL_PARTITION_NAME = "DOMAIN";
   public static final String PARTITION_URI_PREFIX = "/partitions/";
   public static final String PARTITION_QUERY_FIELD = "partitionName=";

   public static PartitionTable getInstance() {
      return PartitionTable.SingletonHolder.INSTANCE;
   }

   public abstract PartitionTableEntry lookup(URI var1);

   public abstract PartitionTableEntry lookup(String var1) throws URISyntaxException;

   public abstract PartitionTableEntry lookup(InetSocketAddress var1);

   public abstract PartitionTableEntry lookupByName(String var1);

   public abstract PartitionTableEntry lookupByID(String var1);

   public String getGlobalPartitionName() {
      return "DOMAIN";
   }

   public abstract String getGlobalPartitionId();

   public String getPartitionAdminURL(String hostname, int port, String partitionName) {
      return "t3://" + hostname + ":" + Integer.toString(port) + "/partitions/" + partitionName;
   }

   private static class SingletonHolder {
      private static final PartitionTable INSTANCE = init();

      private static PartitionTable init() {
         Iterator var0 = ServiceLoader.load(PartitionTable.class).iterator();
         if (var0.hasNext()) {
            PartitionTable partitionTable = (PartitionTable)var0.next();
            return partitionTable;
         } else {
            throw new RuntimeException("META-INF/services/" + PartitionTable.class.getName() + " is not found in the search path of TCL");
         }
      }
   }
}
