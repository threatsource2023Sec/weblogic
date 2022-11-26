package org.antlr.misc;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.antlr.tool.Grammar;

public class BitSet implements IntSet, Cloneable {
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

   public BitSet(int nbits) {
      this.bits = new long[(nbits - 1 >> 6) + 1];
   }

   public void add(int el) {
      int n = wordNumber(el);
      if (n >= this.bits.length) {
         this.growToInclude(el);
      }

      long[] var10000 = this.bits;
      var10000[n] |= bitMask(el);
   }

   public void addAll(IntSet set) {
      if (set instanceof BitSet) {
         this.orInPlace((BitSet)set);
      } else {
         if (!(set instanceof IntervalSet)) {
            throw new IllegalArgumentException("can't add " + set.getClass().getName() + " to BitSet");
         }

         IntervalSet other = (IntervalSet)set;
         Iterator i$ = other.intervals.iterator();

         while(i$.hasNext()) {
            Interval I = (Interval)i$.next();
            this.orInPlace(range(I.a, I.b));
         }
      }

   }

   public void addAll(int[] elements) {
      if (elements != null) {
         for(int i = 0; i < elements.length; ++i) {
            int e = elements[i];
            this.add(e);
         }

      }
   }

   public void addAll(Iterable elements) {
      if (elements != null) {
         Iterator i$ = elements.iterator();

         while(i$.hasNext()) {
            Integer element = (Integer)i$.next();
            this.add(element);
         }

      }
   }

   public IntSet and(IntSet a) {
      BitSet s = (BitSet)this.clone();
      s.andInPlace((BitSet)a);
      return s;
   }

   public void andInPlace(BitSet a) {
      int min = Math.min(this.bits.length, a.bits.length);

      int i;
      for(i = min - 1; i >= 0; --i) {
         long[] var10000 = this.bits;
         var10000[i] &= a.bits[i];
      }

      for(i = min; i < this.bits.length; ++i) {
         this.bits[i] = 0L;
      }

   }

   private static long bitMask(int bitNumber) {
      int bitPosition = bitNumber & 63;
      return 1L << bitPosition;
   }

   public void clear() {
      for(int i = this.bits.length - 1; i >= 0; --i) {
         this.bits[i] = 0L;
      }

   }

   public void clear(int el) {
      int n = wordNumber(el);
      if (n >= this.bits.length) {
         this.growToInclude(el);
      }

      long[] var10000 = this.bits;
      var10000[n] &= ~bitMask(el);
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

   public void growToInclude(int bit) {
      int newSize = Math.max(this.bits.length << 1, this.numWordsToHold(bit));
      long[] newbits = new long[newSize];
      System.arraycopy(this.bits, 0, newbits, 0, this.bits.length);
      this.bits = newbits;
   }

   public boolean member(int el) {
      int n = wordNumber(el);
      if (n >= this.bits.length) {
         return false;
      } else {
         return (this.bits[n] & bitMask(el)) != 0L;
      }
   }

   public int getSingleElement() {
      for(int i = 0; i < this.bits.length << 6; ++i) {
         if (this.member(i)) {
            return i;
         }
      }

      return -7;
   }

   public boolean isNil() {
      for(int i = this.bits.length - 1; i >= 0; --i) {
         if (this.bits[i] != 0L) {
            return false;
         }
      }

      return true;
   }

   public IntSet complement() {
      BitSet s = (BitSet)this.clone();
      s.notInPlace();
      return s;
   }

   public IntSet complement(IntSet set) {
      return set == null ? this.complement() : set.subtract(this);
   }

   public void notInPlace() {
      for(int i = this.bits.length - 1; i >= 0; --i) {
         this.bits[i] = ~this.bits[i];
      }

   }

   public void notInPlace(int maxBit) {
      this.notInPlace(0, maxBit);
   }

   public void notInPlace(int minBit, int maxBit) {
      this.growToInclude(maxBit);

      for(int i = minBit; i <= maxBit; ++i) {
         int n = wordNumber(i);
         long[] var10000 = this.bits;
         var10000[n] ^= bitMask(i);
      }

   }

   private int numWordsToHold(int el) {
      return (el >> 6) + 1;
   }

   public static BitSet of(int el) {
      BitSet s = new BitSet(el + 1);
      s.add(el);
      return s;
   }

   public static BitSet of(Collection elements) {
      BitSet s = new BitSet();
      Iterator i$ = elements.iterator();

      while(i$.hasNext()) {
         Integer el = (Integer)i$.next();
         s.add(el);
      }

      return s;
   }

   public static BitSet of(IntSet set) {
      if (set == null) {
         return null;
      } else if (set instanceof BitSet) {
         return (BitSet)set;
      } else if (set instanceof IntervalSet) {
         BitSet s = new BitSet();
         s.addAll(set);
         return s;
      } else {
         throw new IllegalArgumentException("can't create BitSet from " + set.getClass().getName());
      }
   }

   public static BitSet of(Map elements) {
      return of((Collection)elements.keySet());
   }

   public static BitSet range(int a, int b) {
      BitSet s = new BitSet(b + 1);

      for(int i = a; i <= b; ++i) {
         int n = wordNumber(i);
         long[] var10000 = s.bits;
         var10000[n] |= bitMask(i);
      }

      return s;
   }

   public IntSet or(IntSet a) {
      if (a == null) {
         return this;
      } else {
         BitSet s = (BitSet)this.clone();
         s.orInPlace((BitSet)a);
         return s;
      }
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

   public void remove(int el) {
      int n = wordNumber(el);
      if (n >= this.bits.length) {
         this.growToInclude(el);
      }

      long[] var10000 = this.bits;
      var10000[n] &= ~bitMask(el);
   }

   private void setSize(int nwords) {
      long[] newbits = new long[nwords];
      int n = Math.min(nwords, this.bits.length);
      System.arraycopy(this.bits, 0, newbits, 0, n);
      this.bits = newbits;
   }

   public int numBits() {
      return this.bits.length << 6;
   }

   public int lengthInLongWords() {
      return this.bits.length;
   }

   public boolean subset(BitSet a) {
      return a == null ? false : this.and(a).equals(this);
   }

   public void subtractInPlace(BitSet a) {
      if (a != null) {
         for(int i = 0; i < this.bits.length && i < a.bits.length; ++i) {
            long[] var10000 = this.bits;
            var10000[i] &= ~a.bits[i];
         }

      }
   }

   public IntSet subtract(IntSet a) {
      if (a != null && a instanceof BitSet) {
         BitSet s = (BitSet)this.clone();
         s.subtractInPlace((BitSet)a);
         return s;
      } else {
         return null;
      }
   }

   public List toList() {
      throw new NoSuchMethodError("BitSet.toList() unimplemented");
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

   public String toString() {
      return this.toString((Grammar)null);
   }

   public String toString(Grammar g) {
      StringBuilder buf = new StringBuilder();
      String separator = ",";
      boolean havePrintedAnElement = false;
      buf.append('{');

      for(int i = 0; i < this.bits.length << 6; ++i) {
         if (this.member(i)) {
            if (i > 0 && havePrintedAnElement) {
               buf.append(separator);
            }

            if (g != null) {
               buf.append(g.getTokenDisplayName(i));
            } else {
               buf.append(i);
            }

            havePrintedAnElement = true;
         }
      }

      buf.append('}');
      return buf.toString();
   }

   public String toString(String separator, List vocabulary) {
      if (vocabulary == null) {
         return this.toString((Grammar)null);
      } else {
         String str = "";

         for(int i = 0; i < this.bits.length << 6; ++i) {
            if (this.member(i)) {
               if (str.length() > 0) {
                  str = str + separator;
               }

               if (i >= vocabulary.size()) {
                  str = str + "'" + (char)i + "'";
               } else if (vocabulary.get(i) == null) {
                  str = str + "'" + (char)i + "'";
               } else {
                  str = str + (String)vocabulary.get(i);
               }
            }
         }

         return str;
      }
   }

   public String toStringOfHalfWords() {
      StringBuilder s = new StringBuilder();

      for(int i = 0; i < this.bits.length; ++i) {
         if (i != 0) {
            s.append(", ");
         }

         long tmp = this.bits[i];
         tmp &= 4294967295L;
         s.append(tmp);
         s.append("UL");
         s.append(", ");
         tmp = this.bits[i] >>> 32;
         tmp &= 4294967295L;
         s.append(tmp);
         s.append("UL");
      }

      return s.toString();
   }

   public String toStringOfWords() {
      StringBuilder s = new StringBuilder();

      for(int i = 0; i < this.bits.length; ++i) {
         if (i != 0) {
            s.append(", ");
         }

         s.append(this.bits[i]);
         s.append("L");
      }

      return s.toString();
   }

   public String toStringWithRanges() {
      return this.toString();
   }

   private static int wordNumber(int bit) {
      return bit >> 6;
   }
}
