package weblogic.apache.xerces.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class SoftReferenceSymbolTable extends SymbolTable {
   protected SREntry[] fBuckets;
   private final ReferenceQueue fReferenceQueue;

   public SoftReferenceSymbolTable(int var1, float var2) {
      super(1, var2);
      this.fBuckets = null;
      if (var1 < 0) {
         throw new IllegalArgumentException("Illegal Capacity: " + var1);
      } else if (!(var2 <= 0.0F) && !Float.isNaN(var2)) {
         if (var1 == 0) {
            var1 = 1;
         }

         this.fLoadFactor = var2;
         this.fTableSize = var1;
         this.fBuckets = new SREntry[this.fTableSize];
         this.fThreshold = (int)((float)this.fTableSize * var2);
         this.fCount = 0;
         this.fReferenceQueue = new ReferenceQueue();
      } else {
         throw new IllegalArgumentException("Illegal Load: " + var2);
      }
   }

   public SoftReferenceSymbolTable(int var1) {
      this(var1, 0.75F);
   }

   public SoftReferenceSymbolTable() {
      this(101, 0.75F);
   }

   public String addSymbol(String var1) {
      this.clean();
      int var2 = 0;
      int var3 = this.hash(var1) % this.fTableSize;

      for(SREntry var4 = this.fBuckets[var3]; var4 != null; var4 = var4.next) {
         SREntryData var5 = (SREntryData)var4.get();
         if (var5 != null) {
            if (var5.symbol.equals(var1)) {
               return var5.symbol;
            }

            ++var2;
         }
      }

      return this.addSymbol0(var1, var3, var2);
   }

   private String addSymbol0(String var1, int var2, int var3) {
      if (this.fCount >= this.fThreshold) {
         this.rehash();
         var2 = this.hash(var1) % this.fTableSize;
      } else if (var3 >= this.fCollisionThreshold) {
         this.rebalance();
         var2 = this.hash(var1) % this.fTableSize;
      }

      var1 = var1.intern();
      SREntry var4 = new SREntry(var1, this.fBuckets[var2], var2, this.fReferenceQueue);
      this.fBuckets[var2] = var4;
      ++this.fCount;
      return var1;
   }

   public String addSymbol(char[] var1, int var2, int var3) {
      this.clean();
      int var4 = 0;
      int var5 = this.hash(var1, var2, var3) % this.fTableSize;

      label32:
      for(SREntry var6 = this.fBuckets[var5]; var6 != null; var6 = var6.next) {
         SREntryData var7 = (SREntryData)var6.get();
         if (var7 != null) {
            if (var3 == var7.characters.length) {
               for(int var8 = 0; var8 < var3; ++var8) {
                  if (var1[var2 + var8] != var7.characters[var8]) {
                     ++var4;
                     continue label32;
                  }
               }

               return var7.symbol;
            } else {
               ++var4;
            }
         }
      }

      return this.addSymbol0(var1, var2, var3, var5, var4);
   }

   private String addSymbol0(char[] var1, int var2, int var3, int var4, int var5) {
      if (this.fCount >= this.fThreshold) {
         this.rehash();
         var4 = this.hash(var1, var2, var3) % this.fTableSize;
      } else if (var5 >= this.fCollisionThreshold) {
         this.rebalance();
         var4 = this.hash(var1, var2, var3) % this.fTableSize;
      }

      String var6 = (new String(var1, var2, var3)).intern();
      SREntry var7 = new SREntry(var6, var1, var2, var3, this.fBuckets[var4], var4, this.fReferenceQueue);
      this.fBuckets[var4] = var7;
      ++this.fCount;
      return var6;
   }

   protected void rehash() {
      this.rehashCommon(this.fBuckets.length * 2 + 1);
   }

   protected void compact() {
      this.rehashCommon((int)((float)this.fCount / this.fLoadFactor) * 2 + 1);
   }

   protected void rebalance() {
      if (this.fHashMultipliers == null) {
         this.fHashMultipliers = new int[32];
      }

      PrimeNumberSequenceGenerator.generateSequence(this.fHashMultipliers);
      this.rehashCommon(this.fBuckets.length);
   }

   private void rehashCommon(int var1) {
      int var2 = this.fBuckets.length;
      SREntry[] var3 = this.fBuckets;
      SREntry[] var4 = new SREntry[var1];
      this.fThreshold = (int)((float)var1 * this.fLoadFactor);
      this.fBuckets = var4;
      this.fTableSize = this.fBuckets.length;
      int var5 = var2;

      SREntry var7;
      while(var5-- > 0) {
         for(SREntry var6 = var3[var5]; var6 != null; var7.prev = null) {
            var7 = var6;
            var6 = var6.next;
            SREntryData var8 = (SREntryData)var7.get();
            if (var8 != null) {
               int var9 = this.hash(var8.symbol) % var1;
               if (var4[var9] != null) {
                  var4[var9].prev = var7;
               }

               var7.bucket = var9;
               var7.next = var4[var9];
               var4[var9] = var7;
            } else {
               var7.bucket = -1;
               var7.next = null;
               --this.fCount;
            }
         }
      }

   }

   public boolean containsSymbol(String var1) {
      int var2 = this.hash(var1) % this.fTableSize;
      int var3 = var1.length();

      label31:
      for(SREntry var4 = this.fBuckets[var2]; var4 != null; var4 = var4.next) {
         SREntryData var5 = (SREntryData)var4.get();
         if (var5 != null && var3 == var5.characters.length) {
            for(int var6 = 0; var6 < var3; ++var6) {
               if (var1.charAt(var6) != var5.characters[var6]) {
                  continue label31;
               }
            }

            return true;
         }
      }

      return false;
   }

   public boolean containsSymbol(char[] var1, int var2, int var3) {
      int var4 = this.hash(var1, var2, var3) % this.fTableSize;

      label31:
      for(SREntry var5 = this.fBuckets[var4]; var5 != null; var5 = var5.next) {
         SREntryData var6 = (SREntryData)var5.get();
         if (var6 != null && var3 == var6.characters.length) {
            for(int var7 = 0; var7 < var3; ++var7) {
               if (var1[var2 + var7] != var6.characters[var7]) {
                  continue label31;
               }
            }

            return true;
         }
      }

      return false;
   }

   private void removeEntry(SREntry var1) {
      int var2 = var1.bucket;
      if (var2 >= 0) {
         if (var1.next != null) {
            var1.next.prev = var1.prev;
         }

         if (var1.prev != null) {
            var1.prev.next = var1.next;
         } else {
            this.fBuckets[var2] = var1.next;
         }

         --this.fCount;
      }

   }

   private void clean() {
      SREntry var1 = (SREntry)this.fReferenceQueue.poll();
      if (var1 != null) {
         do {
            this.removeEntry(var1);
            var1 = (SREntry)this.fReferenceQueue.poll();
         } while(var1 != null);

         if (this.fCount < this.fThreshold >> 2) {
            this.compact();
         }
      }

   }

   protected static final class SREntryData {
      public final String symbol;
      public final char[] characters;

      public SREntryData(String var1) {
         this.symbol = var1;
         this.characters = new char[this.symbol.length()];
         this.symbol.getChars(0, this.characters.length, this.characters, 0);
      }

      public SREntryData(String var1, char[] var2, int var3, int var4) {
         this.symbol = var1;
         this.characters = new char[var4];
         System.arraycopy(var2, var3, this.characters, 0, var4);
      }
   }

   protected static final class SREntry extends SoftReference {
      public SREntry next;
      public SREntry prev;
      public int bucket;

      public SREntry(String var1, SREntry var2, int var3, ReferenceQueue var4) {
         super(new SREntryData(var1), var4);
         this.initialize(var2, var3);
      }

      public SREntry(String var1, char[] var2, int var3, int var4, SREntry var5, int var6, ReferenceQueue var7) {
         super(new SREntryData(var1, var2, var3, var4), var7);
         this.initialize(var5, var6);
      }

      private void initialize(SREntry var1, int var2) {
         this.next = var1;
         if (var1 != null) {
            var1.prev = this;
         }

         this.prev = null;
         this.bucket = var2;
      }
   }
}
