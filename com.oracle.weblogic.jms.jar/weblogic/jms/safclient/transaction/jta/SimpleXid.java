package weblogic.jms.safclient.transaction.jta;

import java.nio.ByteBuffer;
import javax.transaction.xa.Xid;

public final class SimpleXid implements Xid {
   private static final int FORMAT_ID = 8675309;
   private static final int DEFAULT_BQUAL = 1;
   private static final IDRoller idRoller = new IDRoller();
   private long timestamp = System.currentTimeMillis();
   private short counter;
   private int branch;

   SimpleXid() {
      this.counter = idRoller.getNextID();
      this.branch = 1;
   }

   public int getFormatId() {
      return 8675309;
   }

   public byte[] getGlobalTransactionId() {
      ByteBuffer buf = ByteBuffer.allocate(10);
      buf.putLong(this.timestamp);
      buf.putShort(this.counter);
      buf.flip();
      return buf.array();
   }

   public byte[] getBranchQualifier() {
      ByteBuffer buf = ByteBuffer.allocate(4);
      buf.putInt(this.branch);
      buf.flip();
      return buf.array();
   }

   public int hashCode() {
      return (int)this.timestamp;
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else {
         try {
            SimpleXid xid = (SimpleXid)o;
            return xid.timestamp == this.timestamp && xid.counter == this.counter && xid.branch == this.branch;
         } catch (ClassCastException var3) {
            return false;
         }
      }
   }

   private static final class IDRoller {
      private short counter;

      private IDRoller() {
      }

      synchronized short getNextID() {
         return ++this.counter;
      }

      // $FF: synthetic method
      IDRoller(Object x0) {
         this();
      }
   }
}
