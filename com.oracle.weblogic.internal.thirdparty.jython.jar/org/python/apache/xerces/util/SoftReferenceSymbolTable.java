package org.python.apache.xerces.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

public class SoftReferenceSymbolTable extends SymbolTable {
   protected SREntry[] fBuckets;
   private final ReferenceQueue fReferenceQueue;

   public SoftReferenceSymbolTable(int var1, float var2) {
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
      int var2 = this.hash(var1) % this.fTableSize;

      for(SREntry var3 = this.fBuckets[var2]; var3 != null; var3 = var3.next) {
         SREntryData var4 = (SREntryData)var3.get();
         if (var4 != null && var4.symbol.equals(var1)) {
            return var4.symbol;
         }
      }

      if (this.fCount >= this.fThreshold) {
         this.rehash();
         var2 = this.hash(var1) % this.fTableSize;
      }

      var1 = var1.intern();
      SREntry var5 = new SREntry(var1, this.fBuckets[var2], var2, this.fReferenceQueue);
      this.fBuckets[var2] = var5;
      ++this.fCount;
      return var1;
   }

   public String addSymbol(char[] var1, int var2, int var3) {
      this.clean();
      int var4 = this.hash(var1, var2, var3) % this.fTableSize;

      label35:
      for(SREntry var5 = this.fBuckets[var4]; var5 != null; var5 = var5.next) {
         SREntryData var6 = (SREntryData)var5.get();
         if (var6 != null && var3 == var6.characters.length) {
            for(int var7 = 0; var7 < var3; ++var7) {
               if (var1[var2 + var7] != var6.characters[var7]) {
                  continue label35;
               }
            }

            return var6.symbol;
         }
      }

      if (this.fCount >= this.fThreshold) {
         this.rehash();
         var4 = this.hash(var1, var2, var3) % this.fTableSize;
      }

      String var8 = (new String(var1, var2, var3)).intern();
      SREntry var9 = new SREntry(var8, var1, var2, var3, this.fBuckets[var4], var4, this.fReferenceQueue);
      this.fBuckets[var4] = var9;
      ++this.fCount;
      return var8;
   }

   protected void rehash() {
      int var1 = this.fBuckets.length;
      SREntry[] var2 = this.fBuckets;
      int var3 = var1 * 2 + 1;
      SREntry[] var4 = new SREntry[var3];
      this.fThreshold = (int)((float)var3 * this.fLoadFactor);
      this.fBuckets = var4;
      this.fTableSize = this.fBuckets.length;
      int var5 = var1;

      while(var5-- > 0) {
         SREntry var6 = var2[var5];

         while(var6 != null) {
            SREntry var7 = var6;
            var6 = var6.next;
            SREntryData var8 = (SREntryData)var7.get();
            if (var8 != null) {
               int var9 = this.hash(var8.characters, 0, var8.characters.length) % var3;
               if (var4[var9] != null) {
                  var4[var9].prev = var7;
               }

               var7.next = var4[var9];
               var7.prev = null;
               var4[var9] = var7;
            } else {
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
      if (var1.next != null) {
         var1.next.prev = var1.prev;
      }

      if (var1.prev != null) {
         var1.prev.next = var1.next;
      } else {
         this.fBuckets[var1.bucket] = var1.next;
      }

      --this.fCount;
   }

   private void clean() {
      for(SREntry var1 = (SREntry)this.fReferenceQueue.poll(); var1 != null; var1 = (SREntry)this.fReferenceQueue.poll()) {
         this.removeEntry(var1);
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
