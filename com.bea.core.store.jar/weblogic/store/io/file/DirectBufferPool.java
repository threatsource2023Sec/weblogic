package weblogic.store.io.file;

import java.nio.ByteBuffer;
import weblogic.store.common.StoreDebug;
import weblogic.store.internal.StoreStatisticsImpl;
import weblogic.store.io.file.direct.DirectIOManager;
import weblogic.utils.collections.StackPool;

final class DirectBufferPool {
   private StackPool pool;
   private int bufSize;
   private StoreStatisticsImpl stats;
   private static final int MAX_BUFFERS = 128;

   DirectBufferPool(int bufSize, StoreStatisticsImpl stats) {
      this.bufSize = bufSize;
      this.pool = new StackPool(128);
      this.stats = stats;
   }

   void close() {
      ByteBuffer dead;
      while((dead = (ByteBuffer)this.pool.remove()) != null) {
         DirectIOManager.getFileMemoryManager().freeDirectBuffer(dead);
         if (this.stats != null) {
            this.stats.addIOBufferBytes(-this.bufSize);
         }
      }

   }

   ByteBuffer get() {
      ByteBuffer ret = (ByteBuffer)this.pool.remove();
      if (ret == null) {
         if (StoreDebug.storeIOPhysical.isDebugEnabled()) {
            StoreDebug.storeIOPhysical.debug("Allocating a new direct byte buffer of size " + this.bufSize + " for use in file I/O");
         }

         ret = DirectIOManager.getFileMemoryManager().allocateDirectBuffer(this.bufSize);
         if (this.stats != null) {
            this.stats.addIOBufferBytes(this.bufSize);
         }
      } else {
         ret.clear();
      }

      return ret;
   }

   void put(ByteBuffer buf) {
      assert buf.isDirect();

      if (!this.pool.add(buf)) {
         DirectIOManager.getFileMemoryManager().freeDirectBuffer(buf);
         if (this.stats != null) {
            this.stats.addIOBufferBytes(-this.bufSize);
         }
      }

   }
}
