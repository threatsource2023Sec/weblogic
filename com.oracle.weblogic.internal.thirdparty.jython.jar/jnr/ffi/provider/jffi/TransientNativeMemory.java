package jnr.ffi.provider.jffi;

import com.kenai.jffi.PageManager;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jnr.ffi.Runtime;
import jnr.ffi.util.ref.FinalizablePhantomReference;
import jnr.ffi.util.ref.FinalizableReferenceQueue;

public class TransientNativeMemory extends DirectMemoryIO {
   private static final Map referenceSet = new ConcurrentHashMap();
   private static final ThreadLocal currentMagazine = new ThreadLocal();
   private static final int PAGES_PER_MAGAZINE = 2;
   private final Sentinel sentinel;
   private final int size;

   public static DirectMemoryIO allocate(Runtime runtime, int size, int align, boolean clear) {
      if (size < 0) {
         throw new IllegalArgumentException("negative size: " + size);
      } else if (size > 256) {
         return new AllocatedDirectMemoryIO(runtime, size, clear);
      } else {
         Magazine magazine = (Magazine)currentMagazine.get();
         Sentinel sentinel = magazine != null ? magazine.sentinel() : null;
         long address;
         if (sentinel == null || (address = magazine.allocate(size, align)) == 0L) {
            PageManager pm = PageManager.getInstance();

            while(true) {
               long memory = pm.allocatePages(2, 3);
               if (memory != 0L && memory != -1L) {
                  referenceSet.put(magazine = new Magazine(sentinel = new Sentinel(), pm, memory, 2), Boolean.TRUE);
                  currentMagazine.set(magazine);
                  address = magazine.allocate(size, align);
                  break;
               }

               System.gc();
               FinalizableReferenceQueue.cleanUpAll();
            }
         }

         return new TransientNativeMemory(runtime, sentinel, address, size);
      }
   }

   TransientNativeMemory(Runtime runtime, Sentinel sentinel, long address, int size) {
      super(runtime, address);
      this.sentinel = sentinel;
      this.size = size;
   }

   private static long align(long offset, long align) {
      return offset + align - 1L & ~(align - 1L);
   }

   public long size() {
      return (long)this.size;
   }

   public int hashCode() {
      return super.hashCode();
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof TransientNativeMemory)) {
         return super.equals(obj);
      } else {
         TransientNativeMemory mem = (TransientNativeMemory)obj;
         return mem.size == this.size && mem.address() == this.address();
      }
   }

   public final void dispose() {
   }

   private static final class Magazine extends FinalizablePhantomReference {
      private final Reference sentinelReference;
      private final PageManager pm;
      private final long page;
      private final long end;
      private final int pageCount;
      private long memory;

      Magazine(Sentinel sentinel, PageManager pm, long page, int pageCount) {
         super(sentinel, NativeFinalizer.getInstance().getFinalizerQueue());
         this.sentinelReference = new WeakReference(sentinel);
         this.pm = pm;
         this.memory = this.page = page;
         this.pageCount = pageCount;
         this.end = this.memory + (long)pageCount * pm.pageSize();
      }

      Sentinel sentinel() {
         return (Sentinel)this.sentinelReference.get();
      }

      long allocate(int size, int align) {
         long address = TransientNativeMemory.align(this.memory, (long)align);
         if (address + (long)size <= this.end) {
            this.memory = address + (long)size;
            return address;
         } else {
            return 0L;
         }
      }

      public final void finalizeReferent() {
         this.pm.freePages(this.page, this.pageCount);
         TransientNativeMemory.referenceSet.remove(this);
      }
   }

   private static final class Sentinel {
      private Sentinel() {
      }

      // $FF: synthetic method
      Sentinel(Object x0) {
         this();
      }
   }
}
