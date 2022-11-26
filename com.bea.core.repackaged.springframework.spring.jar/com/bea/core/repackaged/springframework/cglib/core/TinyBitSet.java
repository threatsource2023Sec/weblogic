package com.bea.core.repackaged.springframework.cglib.core;

/** @deprecated */
@Deprecated
public class TinyBitSet {
   private static int[] T = new int[256];
   private int value = 0;

   private static int gcount(int x) {
      int c;
      for(c = 0; x != 0; x &= x - 1) {
         ++c;
      }

      return c;
   }

   private static int topbit(int i) {
      int j;
      for(j = 0; i != 0; i ^= j) {
         j = i & -i;
      }

      return j;
   }

   private static int log2(int i) {
      int j = false;

      int j;
      for(j = 0; i != 0; i >>= 1) {
         ++j;
      }

      return j;
   }

   public int length() {
      return log2(topbit(this.value));
   }

   public int cardinality() {
      int w = this.value;

      int c;
      for(c = 0; w != 0; w >>= 8) {
         c += T[w & 255];
      }

      return c;
   }

   public boolean get(int index) {
      return (this.value & 1 << index) != 0;
   }

   public void set(int index) {
      this.value |= 1 << index;
   }

   public void clear(int index) {
      this.value &= ~(1 << index);
   }

   static {
      for(int j = 0; j < 256; ++j) {
         T[j] = gcount(j);
      }

   }
}
