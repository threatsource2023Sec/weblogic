package org.python.icu.text;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Iterator;
import org.python.icu.impl.StandardPlural;
import org.python.icu.util.Freezable;
import org.python.icu.util.Output;

/** @deprecated */
@Deprecated
public final class PluralRanges implements Freezable, Comparable {
   private volatile boolean isFrozen;
   private Matrix matrix = new Matrix();
   private boolean[] explicit;

   /** @deprecated */
   @Deprecated
   public PluralRanges() {
      this.explicit = new boolean[StandardPlural.COUNT];
   }

   /** @deprecated */
   @Deprecated
   public void add(StandardPlural rangeStart, StandardPlural rangeEnd, StandardPlural result) {
      if (this.isFrozen) {
         throw new UnsupportedOperationException();
      } else {
         this.explicit[result.ordinal()] = true;
         StandardPlural[] var4;
         int var5;
         int var6;
         StandardPlural rs;
         if (rangeStart == null) {
            var4 = StandardPlural.values();
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               rs = var4[var6];
               if (rangeEnd == null) {
                  StandardPlural[] var8 = StandardPlural.values();
                  int var9 = var8.length;

                  for(int var10 = 0; var10 < var9; ++var10) {
                     StandardPlural re = var8[var10];
                     this.matrix.setIfNew(rs, re, result);
                  }
               } else {
                  this.explicit[rangeEnd.ordinal()] = true;
                  this.matrix.setIfNew(rs, rangeEnd, result);
               }
            }
         } else if (rangeEnd == null) {
            this.explicit[rangeStart.ordinal()] = true;
            var4 = StandardPlural.values();
            var5 = var4.length;

            for(var6 = 0; var6 < var5; ++var6) {
               rs = var4[var6];
               this.matrix.setIfNew(rangeStart, rs, result);
            }
         } else {
            this.explicit[rangeStart.ordinal()] = true;
            this.explicit[rangeEnd.ordinal()] = true;
            this.matrix.setIfNew(rangeStart, rangeEnd, result);
         }

      }
   }

   /** @deprecated */
   @Deprecated
   public StandardPlural get(StandardPlural start, StandardPlural end) {
      StandardPlural result = this.matrix.get(start, end);
      return result == null ? end : result;
   }

   /** @deprecated */
   @Deprecated
   public boolean isExplicit(StandardPlural start, StandardPlural end) {
      return this.matrix.get(start, end) != null;
   }

   /** @deprecated */
   @Deprecated
   public boolean isExplicitlySet(StandardPlural count) {
      return this.explicit[count.ordinal()];
   }

   /** @deprecated */
   @Deprecated
   public boolean equals(Object other) {
      if (this == other) {
         return true;
      } else if (!(other instanceof PluralRanges)) {
         return false;
      } else {
         PluralRanges otherPR = (PluralRanges)other;
         return this.matrix.equals(otherPR.matrix) && Arrays.equals(this.explicit, otherPR.explicit);
      }
   }

   /** @deprecated */
   @Deprecated
   public int hashCode() {
      return this.matrix.hashCode();
   }

   /** @deprecated */
   @Deprecated
   public int compareTo(PluralRanges that) {
      return this.matrix.compareTo(that.matrix);
   }

   /** @deprecated */
   @Deprecated
   public boolean isFrozen() {
      return this.isFrozen;
   }

   /** @deprecated */
   @Deprecated
   public PluralRanges freeze() {
      this.isFrozen = true;
      return this;
   }

   /** @deprecated */
   @Deprecated
   public PluralRanges cloneAsThawed() {
      PluralRanges result = new PluralRanges();
      result.explicit = (boolean[])this.explicit.clone();
      result.matrix = this.matrix.clone();
      return result;
   }

   /** @deprecated */
   @Deprecated
   public String toString() {
      return this.matrix.toString();
   }

   private static final class Matrix implements Comparable, Cloneable {
      private byte[] data;

      Matrix() {
         this.data = new byte[StandardPlural.COUNT * StandardPlural.COUNT];

         for(int i = 0; i < this.data.length; ++i) {
            this.data[i] = -1;
         }

      }

      void set(StandardPlural start, StandardPlural end, StandardPlural result) {
         this.data[start.ordinal() * StandardPlural.COUNT + end.ordinal()] = result == null ? -1 : (byte)result.ordinal();
      }

      void setIfNew(StandardPlural start, StandardPlural end, StandardPlural result) {
         byte old = this.data[start.ordinal() * StandardPlural.COUNT + end.ordinal()];
         if (old >= 0) {
            throw new IllegalArgumentException("Previously set value for <" + start + ", " + end + ", " + StandardPlural.VALUES.get(old) + ">");
         } else {
            this.data[start.ordinal() * StandardPlural.COUNT + end.ordinal()] = result == null ? -1 : (byte)result.ordinal();
         }
      }

      StandardPlural get(StandardPlural start, StandardPlural end) {
         byte result = this.data[start.ordinal() * StandardPlural.COUNT + end.ordinal()];
         return result < 0 ? null : (StandardPlural)StandardPlural.VALUES.get(result);
      }

      StandardPlural endSame(StandardPlural end) {
         StandardPlural first = null;
         Iterator var3 = StandardPlural.VALUES.iterator();

         while(var3.hasNext()) {
            StandardPlural start = (StandardPlural)var3.next();
            StandardPlural item = this.get(start, end);
            if (item != null) {
               if (first == null) {
                  first = item;
               } else if (first != item) {
                  return null;
               }
            }
         }

         return first;
      }

      StandardPlural startSame(StandardPlural start, EnumSet endDone, Output emit) {
         emit.value = false;
         StandardPlural first = null;
         Iterator var5 = StandardPlural.VALUES.iterator();

         while(var5.hasNext()) {
            StandardPlural end = (StandardPlural)var5.next();
            StandardPlural item = this.get(start, end);
            if (item != null) {
               if (first == null) {
                  first = item;
               } else {
                  if (first != item) {
                     return null;
                  }

                  if (!endDone.contains(end)) {
                     emit.value = true;
                  }
               }
            }
         }

         return first;
      }

      public int hashCode() {
         int result = 0;

         for(int i = 0; i < this.data.length; ++i) {
            result = result * 37 + this.data[i];
         }

         return result;
      }

      public boolean equals(Object other) {
         if (!(other instanceof Matrix)) {
            return false;
         } else {
            return 0 == this.compareTo((Matrix)other);
         }
      }

      public int compareTo(Matrix o) {
         for(int i = 0; i < this.data.length; ++i) {
            int diff = this.data[i] - o.data[i];
            if (diff != 0) {
               return diff;
            }
         }

         return 0;
      }

      public Matrix clone() {
         Matrix result = new Matrix();
         result.data = (byte[])this.data.clone();
         return result;
      }

      public String toString() {
         StringBuilder result = new StringBuilder();
         StandardPlural[] var2 = StandardPlural.values();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            StandardPlural i = var2[var4];
            StandardPlural[] var6 = StandardPlural.values();
            int var7 = var6.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               StandardPlural j = var6[var8];
               StandardPlural x = this.get(i, j);
               if (x != null) {
                  result.append(i + " & " + j + " → " + x + ";\n");
               }
            }
         }

         return result.toString();
      }
   }
}
