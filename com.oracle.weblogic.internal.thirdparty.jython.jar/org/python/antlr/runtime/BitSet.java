package org.python.antlr.runtime;

import java.util.List;

public class BitSet implements Cloneable {
   protected static final int BITS = 64;
   protected static final int LOG_BITS = 6;
   protected static final int MOD_MASK = 63;
   protected long[] bits;

   public BitSet() {
      this(64);
   }

   public BitSet(long[] bits_) {
      this.bits = bits_;
   }

   public BitSet(List items) {
      this();

      for(int i = 0; i < items.size(); ++i) {
         Integer v = (Integer)items.get(i);
         this.add(v);
      }

   }

   public BitSet(int nbits) {
      this.bits = new long[(nbits - 1 >> 6) + 1];
   }

   public static BitSet of(int el) {
      BitSet s = new BitSet(el + 1);
      s.add(el);
      return s;
   }

   public static BitSet of(int a, int b) {
      BitSet s = new BitSet(Math.max(a, b) + 1);
      s.add(a);
      s.add(b);
      return s;
   }

   public static BitSet of(int a, int b, int c) {
      BitSet s = new BitSet();
      s.add(a);
      s.add(b);
      s.add(c);
      return s;
   }

   public static BitSet of(int a, int b, int c, int d) {
      BitSet s = new BitSet();
      s.add(a);
      s.add(b);
      s.add(c);
      s.add(d);
      return s;
   }

   public BitSet or(BitSet a) {
      if (a == null) {
         return this;
      } else {
         BitSet s = (BitSet)this.clone();
         s.orInPlace(a);
         return s;
      }
   }

   public void add(int el) {
      int n = wordNumber(el);
      if (n >= this.bits.length) {
         this.growToInclude(el);
      }

      long[] var10000 = this.bits;
      var10000[n] |= bitMask(el);
   }

   public void growToInclude(int bit) {
      int newSize = Math.max(this.bits.length << 1, this.numWordsToHold(bit));
      long[] newbits = new long[newSize];
      System.arraycopy(this.bits, 0, newbits, 0, this.bits.length);
      this.bits = newbits;
   }

   public void orInPlace(BitSet a) {
      if (a != null) {
         if (a.bits.length > this.bits.length) {
            this.setSize(a.bits.length);
         }

         int min = Math.min(this.bits.length, a.bits.length);

         for(int i = min - 1; i >= 0; --i) {
            long[] var10000 = this.bits;
            var10000[i] |= a.bits[i];
         }

      }
   }

   private void setSize(int nwords) {
      long[] newbits = new long[nwords];
      int n = Math.min(nwords, this.bits.length);
      System.arraycopy(this.bits, 0, newbits, 0, n);
      this.bits = newbits;
   }

   private static final long bitMask(int bitNumber) {
      int bitPosition = bitNumber & 63;
      return 1L << bitPosition;
   }

   public Object clone() {
      try {
         BitSet s = (BitSet)super.clone();
         s.bits = new long[this.bits.length];
         System.arraycopy(this.bits, 0, s.bits, 0, this.bits.length);
         return s;
      } catch (CloneNotSupportedException var3) {
         throw new InternalError();
      }
   }

   public int size() {
      int deg = 0;

      for(int i = this.bits.length - 1; i >= 0; --i) {
         long word = this.bits[i];
         if (word != 0L) {
            for(int bit = 63; bit >= 0; --bit) {
               if ((word & 1L << bit) != 0L) {
                  ++deg;
               }
            }
         }
      }

      return deg;
   }

   public boolean equals(Object other) {
      if (other != null && other instanceof BitSet) {
         BitSet otherSet = (BitSet)other;
         int n = Math.min(this.bits.length, otherSet.bits.length);

         int i;
         for(i = 0; i < n; ++i) {
            if (this.bits[i] != otherSet.bits[i]) {
               return false;
            }
         }

         if (this.bits.length > n) {
            for(i = n + 1; i < this.bits.length; ++i) {
               if (this.bits[i] != 0L) {
                  return false;
               }
            }
         } else if (otherSet.bits.length > n) {
            for(i = n + 1; i < otherSet.bits.length; ++i) {
               if (otherSet.bits[i] != 0L) {
                  return false;
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean member(int el) {
      if (el < 0) {
         return false;
      } else {
         int n = wordNumber(el);
         if (n >= this.bits.length) {
            return false;
         } else {
            return (this.bits[n] & bitMask(el)) != 0L;
         }
      }
   }

   public void remove(int el) {
      int n = wordNumber(el);
      if (n < this.bits.length) {
         long[] var10000 = this.bits;
         var10000[n] &= ~bitMask(el);
      }

   }

   public boolean isNil() {
      for(int i = this.bits.length - 1; i >= 0; --i) {
         if (this.bits[i] != 0L) {
            return false;
         }
      }

      return true;
   }

   private final int numWordsToHold(int el) {
      return (el >> 6) + 1;
   }

   public int numBits() {
      return this.bits.length << 6;
   }

   public int lengthInLongWords() {
      return this.bits.length;
   }

   public int[] toArray() {
      int[] elems = new int[this.size()];
      int en = 0;

      for(int i = 0; i < this.bits.length << 6; ++i) {
         if (this.member(i)) {
            elems[en++] = i;
         }
      }

      return elems;
   }

   public long[] toPackedArray() {
      return this.bits;
   }

   private static final int wordNumber(int bit) {
      return bit >> 6;
   }

   public String toString() {
      return this.toString((String[])null);
   }

   public String toString(String[] tokenNames) {
      StringBuffer buf = new StringBuffer();
      String separator = ",";
      boolean havePrintedAnElement = false;
      buf.append('{');

      for(int i = 0; i < this.bits.length << 6; ++i) {
         if (this.member(i)) {
            if (i > 0 && havePrintedAnElement) {
               buf.append(separator);
            }

            if (tokenNames != null) {
               buf.append(tokenNames[i]);
            } else {
               buf.append(i);
            }

            havePrintedAnElement = true;
         }
      }

      buf.append('}');
      return buf.toString();
   }
}
