package org.glassfish.grizzly.asyncqueue;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.WriteResult;
import org.glassfish.grizzly.utils.Holder;

public final class RecordWriteResult extends WriteResult {
   private long lastWrittenBytes;
   private long bytesToReleaseAfterLastWrite;
   private final SettableHolder dstAddressHolder = new SettableHolder();

   protected void set(Connection connection, Object message, Object dstAddress, long writtenSize) {
      super.set(connection, message, dstAddress, writtenSize);
   }

   protected Holder createAddrHolder(Object dstAddress) {
      return this.dstAddressHolder.set(dstAddress);
   }

   public long lastWrittenBytes() {
      return this.lastWrittenBytes;
   }

   public long bytesToReleaseAfterLastWrite() {
      return this.bytesToReleaseAfterLastWrite;
   }

   public RecordWriteResult lastWriteResult(long lastWrittenBytes, long bytesToReleaseAfterLastWrite) {
      this.lastWrittenBytes = lastWrittenBytes;
      this.bytesToReleaseAfterLastWrite = bytesToReleaseAfterLastWrite;
      return this;
   }

   public void recycle() {
      this.lastWrittenBytes = 0L;
      this.bytesToReleaseAfterLastWrite = 0L;
      this.dstAddressHolder.obj = null;
      this.reset();
   }

   private static class SettableHolder extends Holder {
      private Object obj;

      private SettableHolder() {
      }

      public SettableHolder set(Object obj) {
         this.obj = obj;
         return this;
      }

      public Object get() {
         return this.obj;
      }

      // $FF: synthetic method
      SettableHolder(Object x0) {
         this();
      }
   }
}
