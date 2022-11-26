package weblogic.apache.xerces.util;

public class SymbolTable {
   protected static final int TABLE_SIZE = 101;
   protected static final int MAX_HASH_COLLISIONS = 40;
   protected static final int MULTIPLIERS_SIZE = 32;
   protected static final int MULTIPLIERS_MASK = 31;
   protected Entry[] fBuckets;
   protected int fTableSize;
   protected transient int fCount;
   protected int fThreshold;
   protected float fLoadFactor;
   protected final int fCollisionThreshold;
   protected int[] fHashMultipliers;

   public SymbolTable(int var1, float var2) {
      this.fBuckets = null;
      if (var1 < 0) {
         throw new IllegalArgumentException("Illegal Capacity: " + var1);
      } else if (!(var2 <= 0.0F) && !Float.isNaN(var2)) {
         if (var1 == 0) {
            var1 = 1;
         }

         this.fLoadFactor = var2;
         this.fTableSize = var1;
         this.fBuckets = new Entry[this.fTableSize];
         this.fThreshold = (int)((float)this.fTableSize * var2);
         this.fCollisionThreshold = (int)(40.0F * var2);
         this.fCount = 0;
      } else {
         throw new IllegalArgumentException("Illegal Load: " + var2);
      }
   }

   public SymbolTable(int var1) {
      this(var1, 0.75F);
   }

   public SymbolTable() {
      this(101, 0.75F);
   }

   public String addSymbol(String var1) {
      int var2 = 0;
      int var3 = this.hash(var1) % this.fTableSize;

      for(Entry var4 = this.fBuckets[var3]; var4 != null; var4 = var4.next) {
         if (var4.symbol.equals(var1)) {
            return var4.symbol;
         }

         ++var2;
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

      Entry var4 = new Entry(var1, this.fBuckets[var2]);
      this.fBuckets[var2] = var4;
      ++this.fCount;
      return var4.symbol;
   }

   public String addSymbol(char[] var1, int var2, int var3) {
      int var4 = 0;
      int var5 = this.hash(var1, var2, var3) % this.fTableSize;

      label28:
      for(Entry var6 = this.fBuckets[var5]; var6 != null; var6 = var6.next) {
         if (var3 == var6.characters.length) {
            for(int var7 = 0; var7 < var3; ++var7) {
               if (var1[var2 + var7] != var6.characters[var7]) {
                  ++var4;
                  continue label28;
               }
            }

            return var6.symbol;
         } else {
            ++var4;
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

      Entry var6 = new Entry(var1, var2, var3, this.fBuckets[var4]);
      this.fBuckets[var4] = var6;
      ++this.fCount;
      return var6.symbol;
   }

   public int hash(String var1) {
      return this.fHashMultipliers == null ? var1.hashCode() & Integer.MAX_VALUE : this.hash0(var1);
   }

   private int hash0(String var1) {
      int var2 = 0;
      int var3 = var1.length();
      int[] var4 = this.fHashMultipliers;

      for(int var5 = 0; var5 < var3; ++var5) {
         var2 = var2 * var4[var5 & 31] + var1.charAt(var5);
      }

      return var2 & Integer.MAX_VALUE;
   }

   public int hash(char[] var1, int var2, int var3) {
      if (this.fHashMultipliers != null) {
         return this.hash0(var1, var2, var3);
      } else {
         int var4 = 0;

         for(int var5 = 0; var5 < var3; ++var5) {
            var4 = var4 * 31 + var1[var2 + var5];
         }

         return var4 & Integer.MAX_VALUE;
      }
   }

   private int hash0(char[] var1, int var2, int var3) {
      int var4 = 0;
      int[] var5 = this.fHashMultipliers;

      for(int var6 = 0; var6 < var3; ++var6) {
         var4 = var4 * var5[var6 & 31] + var1[var2 + var6];
      }

      return var4 & Integer.MAX_VALUE;
   }

   protected void rehash() {
      this.rehashCommon(this.fBuckets.length * 2 + 1);
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
      Entry[] var3 = this.fBuckets;
      Entry[] var4 = new Entry[var1];
      this.fThreshold = (int)((float)var1 * this.fLoadFactor);
      this.fBuckets = var4;
      this.fTableSize = this.fBuckets.length;
      int var5 = var2;

      Entry var7;
      int var8;
      while(var5-- > 0) {
         for(Entry var6 = var3[var5]; var6 != null; var4[var8] = var7) {
            var7 = var6;
            var6 = var6.next;
            var8 = this.hash(var7.symbol) % var1;
            var7.next = var4[var8];
         }
      }

   }

   public boolean containsSymbol(String var1) {
      int var2 = this.hash(var1) % this.fTableSize;
      int var3 = var1.length();

      label27:
      for(Entry var4 = this.fBuckets[var2]; var4 != null; var4 = var4.next) {
         if (var3 == var4.characters.length) {
            for(int var5 = 0; var5 < var3; ++var5) {
               if (var1.charAt(var5) != var4.characters[var5]) {
                  continue label27;
               }
            }

            return true;
         }
      }

      return false;
   }

   public boolean containsSymbol(char[] var1, int var2, int var3) {
      int var4 = this.hash(var1, var2, var3) % this.fTableSize;

      label27:
      for(Entry var5 = this.fBuckets[var4]; var5 != null; var5 = var5.next) {
         if (var3 == var5.characters.length) {
            for(int var6 = 0; var6 < var3; ++var6) {
               if (var1[var2 + var6] != var5.characters[var6]) {
                  continue label27;
               }
            }

            return true;
         }
      }

      return false;
   }

   protected static final class Entry {
      public final String symbol;
      public final char[] characters;
      public Entry next;

      public Entry(String var1, Entry var2) {
         this.symbol = var1.intern();
         this.characters = new char[var1.length()];
         var1.getChars(0, this.characters.length, this.characters, 0);
         this.next = var2;
      }

      public Entry(char[] var1, int var2, int var3, Entry var4) {
         this.characters = new char[var3];
         System.arraycopy(var1, var2, this.characters, 0, var3);
         this.symbol = (new String(this.characters)).intern();
         this.next = var4;
      }
   }
}
