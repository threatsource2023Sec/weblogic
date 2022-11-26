package com.kenai.jffi;

public abstract class PageManager {
   public static final int PROT_EXEC = 4;
   public static final int PROT_READ = 1;
   public static final int PROT_WRITE = 2;
   private final Foreign foreign = Foreign.getInstance();
   private int pageSize;

   public static PageManager getInstance() {
      return PageManager.SingletonHolder.INSTANCE;
   }

   public final long pageSize() {
      return this.pageSize != 0 ? (long)this.pageSize : this.calculatePageSize();
   }

   private long calculatePageSize() {
      long pgSize = Foreign.pageSize();
      return pgSize < 2147483647L ? (long)(this.pageSize = (int)pgSize) : pgSize;
   }

   public abstract long allocatePages(int var1, int var2);

   public abstract void freePages(long var1, int var3);

   public abstract void protectPages(long var1, int var3, int var4);

   static final class Windows extends PageManager {
      public Windows() {
      }

      public long allocatePages(int pageCount, int protection) {
         return Foreign.VirtualAlloc(0L, (int)this.pageSize() * pageCount, 12288, w32prot(protection));
      }

      public void freePages(long address, int pageCount) {
         Foreign.VirtualFree(address, 0, 32768);
      }

      public void protectPages(long address, int pageCount, int protection) {
         Foreign.VirtualProtect(address, (int)this.pageSize() * pageCount, w32prot(protection));
      }

      private static int w32prot(int p) {
         int w32 = 1;
         if ((p & 3) == 3) {
            w32 = 4;
         } else if ((p & 1) == 1) {
            w32 = 2;
         }

         if ((p & 4) == 4) {
            w32 <<= 4;
         }

         return w32;
      }
   }

   static final class Unix extends PageManager {
      public long allocatePages(int npages, int protection) {
         long sz = (long)npages * this.pageSize();
         long memory = Foreign.mmap(0L, sz, protection, 258, -1, 0L);
         return memory != -1L ? memory : 0L;
      }

      public void freePages(long address, int npages) {
         Foreign.munmap(address, (long)npages * this.pageSize());
      }

      public void protectPages(long address, int npages, int protection) {
         Foreign.mprotect(address, (long)npages * this.pageSize(), protection);
      }
   }

   private static final class SingletonHolder {
      public static final PageManager INSTANCE;

      static {
         INSTANCE = (PageManager)(Platform.getPlatform().getOS() == Platform.OS.WINDOWS ? new Windows() : new Unix());
      }
   }
}
