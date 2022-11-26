package weblogic.store.io.jdbc;

import java.lang.management.ManagementFactory;
import java.nio.ByteBuffer;
import java.util.Random;
import weblogic.store.common.StoreDebug;

class TableLockRecord {
   static final int ALREADY_OWNER = 1;
   static final int CAN_BE_OWNER = 2;
   static final int CANNOT_BE_OWNER = 3;
   static final Random TableRowRandomSource = new Random();
   private final String name;
   private final long random;
   private long timestamp;

   TableLockRecord(JDBCStoreIO jio) {
      RjvmInfo rjvmInfo = RjvmInfo.getRjvmInfo();
      StringBuilder sb = new StringBuilder();
      sb.append("(server=").append(jio.getServerName());
      sb.append("!host=").append(rjvmInfo != null ? rjvmInfo.getAddress() : "client");
      sb.append("!process=").append(this.getProcessName());
      sb.append("!domain=").append(rjvmInfo != null ? rjvmInfo.getDomainName() : "client");
      sb.append("!store=").append(jio.getStoreName());
      sb.append("!table=").append(jio.getTableRef()).append(")");
      this.name = sb.toString();
      this.random = TableRowRandomSource.nextLong();
      this.timestamp = System.currentTimeMillis();
   }

   TableLockRecord(String name, long random, long timestamp) {
      this.name = name;
      this.random = random;
      this.timestamp = timestamp;
   }

   synchronized void setTimeStamp(long timestamp) {
      this.timestamp = timestamp;
   }

   synchronized long getTimeStamp() {
      return this.timestamp;
   }

   String getName() {
      return this.name;
   }

   long getRandom() {
      return this.random;
   }

   synchronized ByteBuffer toBB() {
      byte[] b = this.name.getBytes();
      ByteBuffer bb = ByteBuffer.allocate(b.length + 32);
      bb = bb.putInt(b.length).put(b).putLong(this.random).putLong(this.timestamp);
      bb.flip();
      return bb;
   }

   static TableLockRecord fromBB(ByteBuffer bb) {
      if (bb != null && bb.limit() != 1) {
         int len = bb.getInt();
         byte[] b = new byte[len];
         bb.get(b);
         String name = new String(b);
         long random = bb.getLong();
         long timestamp = bb.getLong();
         return new TableLockRecord(name, random, timestamp);
      } else {
         return null;
      }
   }

   private String getProcessName() {
      String name;
      try {
         name = ManagementFactory.getRuntimeMXBean().getName();
      } catch (Throwable var3) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("Process name retrieval failed. Using \"Unknown\".", var3);
         }

         name = "UnknownPid@UnknownHost";
      }

      return name;
   }

   public synchronized String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("[name=").append(this.name);
      sb.append(":random=").append(this.random);
      sb.append(":timestamp=").append(this.timestamp).append("]");
      return sb.toString();
   }
}
