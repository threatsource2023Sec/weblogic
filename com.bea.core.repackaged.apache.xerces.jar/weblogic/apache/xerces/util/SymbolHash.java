package weblogic.apache.xerces.util;

public class SymbolHash {
   protected static final int TABLE_SIZE = 101;
   protected static final int MAX_HASH_COLLISIONS = 40;
   protected static final int MULTIPLIERS_SIZE = 32;
   protected static final int MULTIPLIERS_MASK = 31;
   protected int fTableSize;
   protected Entry[] fBuckets;
   protected int fNum;
   protected int[] fHashMultipliers;

   public SymbolHash() {
      this(101);
   }

   public SymbolHash(int var1) {
      this.fNum = 0;
      this.fTableSize = var1;
      this.fBuckets = new Entry[this.fTableSize];
   }

   public void put(Object var1, Object var2) {
      int var3 = 0;
      int var4 = this.hash(var1);
      int var5 = var4 % this.fTableSize;

      for(Entry var6 = this.fBuckets[var5]; var6 != null; var6 = var6.next) {
         if (var1.equals(var6.key)) {
            var6.value = var2;
            return;
         }

         ++var3;
      }

      if (this.fNum >= this.fTableSize) {
         this.rehash();
         var5 = var4 % this.fTableSize;
      } else if (var3 >= 40 && var1 instanceof String) {
         this.rebalance();
         var5 = this.hash(var1) % this.fTableSize;
      }

      Entry var7 = new Entry(var1, var2, this.fBuckets[var5]);
      this.fBuckets[var5] = var7;
      ++this.fNum;
   }

   public Object get(Object var1) {
      int var2 = this.hash(var1) % this.fTableSize;
      Entry var3 = this.search(var1, var2);
      return var3 != null ? var3.value : null;
   }

   public int getLength() {
      return this.fNum;
   }

   public int getValues(Object[] var1, int var2) {
      int var3 = 0;

      for(int var4 = 0; var3 < this.fTableSize && var4 < this.fNum; ++var3) {
         for(Entry var5 = this.fBuckets[var3]; var5 != null; var5 = var5.next) {
            var1[var2 + var4] = var5.value;
            ++var4;
         }
      }

      return this.fNum;
   }

   public Object[] getEntries() {
      Object[] var1 = new Object[this.fNum << 1];
      int var2 = 0;

      for(int var3 = 0; var2 < this.fTableSize && var3 < this.fNum << 1; ++var2) {
         for(Entry var4 = this.fBuckets[var2]; var4 != null; var4 = var4.next) {
            var1[var3] = var4.key;
            ++var3;
            var1[var3] = var4.value;
            ++var3;
         }
      }

      return var1;
   }

   public SymbolHash makeClone() {
      SymbolHash var1 = new SymbolHash(this.fTableSize);
      var1.fNum = this.fNum;
      var1.fHashMultipliers = this.fHashMultipliers != null ? (int[])this.fHashMultipliers.clone() : null;

      for(int var2 = 0; var2 < this.fTableSize; ++var2) {
         if (this.fBuckets[var2] != null) {
            var1.fBuckets[var2] = this.fBuckets[var2].makeClone();
         }
      }

      return var1;
   }

   public void clear() {
      for(int var1 = 0; var1 < this.fTableSize; ++var1) {
         this.fBuckets[var1] = null;
      }

      this.fNum = 0;
      this.fHashMultipliers = null;
   }

   protected Entry search(Object var1, int var2) {
      for(Entry var3 = this.fBuckets[var2]; var3 != null; var3 = var3.next) {
         if (var1.equals(var3.key)) {
            return var3;
         }
      }

      return null;
   }

   protected int hash(Object var1) {
      return this.fHashMultipliers != null && var1 instanceof String ? this.hash0((String)var1) : var1.hashCode() & Integer.MAX_VALUE;
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

   protected void rehash() {
      this.rehashCommon((this.fBuckets.length << 1) + 1);
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
      this.fBuckets = var4;
      this.fTableSize = this.fBuckets.length;
      int var5 = var2;

      Entry var7;
      int var8;
      while(var5-- > 0) {
         for(Entry var6 = var3[var5]; var6 != null; var4[var8] = var7) {
            var7 = var6;
            var6 = var6.next;
            var8 = this.hash(var7.key) % var1;
            var7.next = var4[var8];
         }
      }

   }

   protected static final class Entry {
      public Object key;
      public Object value;
      public Entry next;

      public Entry() {
         this.key = null;
         this.value = null;
         this.next = null;
      }

      public Entry(Object var1, Object var2, Entry var3) {
         this.key = var1;
         this.value = var2;
         this.next = var3;
      }

      public Entry makeClone() {
         Entry var1 = new Entry();
         var1.key = this.key;
         var1.value = this.value;
         if (this.next != null) {
            var1.next = this.next.makeClone();
         }

         return var1;
      }
   }
}
