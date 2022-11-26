package com.bea.xbean.piccolo.util;

public final class CharStringConverter {
   private static final float DEFAULT_LOAD = 0.7F;
   private float loadFactor;
   private int numEntries;
   private int maxEntries;
   private int hashmask;
   private char[][] keys;
   private String[] values;

   public CharStringConverter(int initialCapacity, float loadFactor) {
      this.numEntries = 0;
      if (initialCapacity < 0) {
         throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
      } else if (!(loadFactor < 0.0F) && !(loadFactor > 1.0F)) {
         int desiredSize = (int)((float)initialCapacity / loadFactor);

         int size;
         for(size = 16; size < desiredSize; size <<= 1) {
         }

         this.hashmask = size - 1;
         this.maxEntries = (int)((float)size * loadFactor);
         this.keys = new char[size][];
         this.values = new String[size];
         this.loadFactor = loadFactor;
      } else {
         throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
      }
   }

   public CharStringConverter() {
      this(0, 0.7F);
   }

   public CharStringConverter(int initialCapacity) {
      this(initialCapacity, 0.7F);
   }

   public int getCacheSize() {
      return this.numEntries;
   }

   public String convert(char[] ch) {
      return this.convert(ch, 0, ch.length);
   }

   public String convert(char[] ch, int start, int length) {
      if (this.numEntries >= this.maxEntries) {
         this.rehash();
      }

      int offset = hashKey(ch, start, length) & this.hashmask;

      char[] k;
      for(char[] k = null; (k = this.keys[offset]) != null && !keysAreEqual(k, 0, k.length, ch, start, length); offset = offset - 1 & this.hashmask) {
      }

      if (k != null) {
         return this.values[offset];
      } else {
         k = new char[length];
         System.arraycopy(ch, start, k, 0, length);
         String v = (new String(k)).intern();
         this.keys[offset] = k;
         this.values[offset] = v;
         ++this.numEntries;
         return v;
      }
   }

   private void rehash() {
      int newlength = this.keys.length << 1;
      char[][] newkeys = new char[newlength][];
      String[] newvalues = new String[newlength];
      int newhashmask = newlength - 1;

      for(int i = 0; i < this.keys.length; ++i) {
         char[] k = this.keys[i];
         String v = this.values[i];
         if (k != null) {
            int newoffset = hashKey(k, 0, k.length) & newhashmask;

            char[] newk;
            for(char[] newk = null; (newk = newkeys[newoffset]) != null && !keysAreEqual(newk, 0, newk.length, k, 0, k.length); newoffset = newoffset - 1 & newhashmask) {
            }

            newkeys[newoffset] = k;
            newvalues[newoffset] = v;
         }
      }

      this.keys = newkeys;
      this.values = newvalues;
      this.maxEntries = (int)((float)newlength * this.loadFactor);
      this.hashmask = newhashmask;
   }

   public void clearCache() {
      for(int i = 0; i < this.keys.length; ++i) {
         this.keys[i] = null;
         this.values[i] = null;
      }

      this.numEntries = 0;
   }

   private static final boolean keysAreEqual(char[] a, int astart, int alength, char[] b, int bstart, int blength) {
      if (alength != blength) {
         return false;
      } else {
         for(int i = 0; i < alength; ++i) {
            if (a[astart + i] != b[bstart + i]) {
               return false;
            }
         }

         return true;
      }
   }

   private static final int hashKey(char[] ch, int start, int length) {
      int hash = 0;

      for(int i = 0; i < length; ++i) {
         hash = (hash << 5) + ch[start + i];
      }

      return hash;
   }
}
