package org.python.icu.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import org.python.icu.lang.CharSequences;
import org.python.icu.util.ICUException;

public class StringRange {
   private static final boolean DEBUG = false;
   public static final Comparator COMPARE_INT_ARRAYS = new Comparator() {
      public int compare(int[] o1, int[] o2) {
         int minIndex = Math.min(o1.length, o2.length);

         for(int i = 0; i < minIndex; ++i) {
            int diff = o1[i] - o2[i];
            if (diff != 0) {
               return diff;
            }
         }

         return o1.length - o2.length;
      }
   };

   public static void compact(Set source, Adder adder, boolean shorterPairs, boolean moreCompact) {
      Iterator var8;
      if (!moreCompact) {
         String start = null;
         String end = null;
         int lastCp = 0;
         int prefixLen = 0;
         var8 = source.iterator();

         while(true) {
            while(var8.hasNext()) {
               String s = (String)var8.next();
               if (start != null) {
                  if (s.regionMatches(0, start, 0, prefixLen)) {
                     int currentCp = s.codePointAt(prefixLen);
                     if (currentCp == 1 + lastCp && s.length() == prefixLen + Character.charCount(currentCp)) {
                        end = s;
                        lastCp = currentCp;
                        continue;
                     }
                  }

                  adder.add(start, end == null ? null : (!shorterPairs ? end : end.substring(prefixLen, end.length())));
               }

               start = s;
               end = null;
               lastCp = s.codePointBefore(s.length());
               prefixLen = s.length() - Character.charCount(lastCp);
            }

            adder.add(start, end == null ? null : (!shorterPairs ? end : end.substring(prefixLen, end.length())));
            break;
         }
      } else {
         Relation lengthToArrays = Relation.of(new TreeMap(), TreeSet.class);
         Iterator var12 = source.iterator();

         while(var12.hasNext()) {
            String s = (String)var12.next();
            Ranges item = new Ranges(s);
            lengthToArrays.put(item.size(), item);
         }

         var12 = lengthToArrays.keyValuesSet().iterator();

         while(var12.hasNext()) {
            Map.Entry entry = (Map.Entry)var12.next();
            LinkedList compacted = compact((Integer)entry.getKey(), (Set)entry.getValue());
            var8 = compacted.iterator();

            while(var8.hasNext()) {
               Ranges ranges = (Ranges)var8.next();
               adder.add(ranges.start(), ranges.end(shorterPairs));
            }
         }
      }

   }

   public static void compact(Set source, Adder adder, boolean shorterPairs) {
      compact(source, adder, shorterPairs, false);
   }

   private static LinkedList compact(int size, Set inputRanges) {
      LinkedList ranges = new LinkedList(inputRanges);

      for(int i = size - 1; i >= 0; --i) {
         Ranges last = null;
         Iterator it = ranges.iterator();

         while(it.hasNext()) {
            Ranges item = (Ranges)it.next();
            if (last == null) {
               last = item;
            } else if (last.merge(i, item)) {
               it.remove();
            } else {
               last = item;
            }
         }
      }

      return ranges;
   }

   public static Collection expand(String start, String end, boolean requireSameLength, Collection output) {
      if (start != null && end != null) {
         int[] startCps = CharSequences.codePoints(start);
         int[] endCps = CharSequences.codePoints(end);
         int startOffset = startCps.length - endCps.length;
         if (requireSameLength && startOffset != 0) {
            throw new ICUException("Range must have equal-length strings");
         } else if (startOffset < 0) {
            throw new ICUException("Range must have start-length ≥ end-length");
         } else if (endCps.length == 0) {
            throw new ICUException("Range must have end-length > 0");
         } else {
            StringBuilder builder = new StringBuilder();

            for(int i = 0; i < startOffset; ++i) {
               builder.appendCodePoint(startCps[i]);
            }

            add(0, startOffset, startCps, endCps, builder, output);
            return output;
         }
      } else {
         throw new ICUException("Range must have 2 valid strings");
      }
   }

   private static void add(int endIndex, int startOffset, int[] starts, int[] ends, StringBuilder builder, Collection output) {
      int start = starts[endIndex + startOffset];
      int end = ends[endIndex];
      if (start > end) {
         throw new ICUException("Range must have xᵢ ≤ yᵢ for each index i");
      } else {
         boolean last = endIndex == ends.length - 1;
         int startLen = builder.length();

         for(int i = start; i <= end; ++i) {
            builder.appendCodePoint(i);
            if (last) {
               output.add(builder.toString());
            } else {
               add(endIndex + 1, startOffset, starts, ends, builder, output);
            }

            builder.setLength(startLen);
         }

      }
   }

   static final class Ranges implements Comparable {
      private final Range[] ranges;

      public Ranges(String s) {
         int[] array = CharSequences.codePoints(s);
         this.ranges = new Range[array.length];

         for(int i = 0; i < array.length; ++i) {
            this.ranges[i] = new Range(array[i], array[i]);
         }

      }

      public boolean merge(int pivot, Ranges other) {
         for(int i = this.ranges.length - 1; i >= 0; --i) {
            if (i == pivot) {
               if (this.ranges[i].max != other.ranges[i].min - 1) {
                  return false;
               }
            } else if (!this.ranges[i].equals(other.ranges[i])) {
               return false;
            }
         }

         this.ranges[pivot].max = other.ranges[pivot].max;
         return true;
      }

      public String start() {
         StringBuilder result = new StringBuilder();

         for(int i = 0; i < this.ranges.length; ++i) {
            result.appendCodePoint(this.ranges[i].min);
         }

         return result.toString();
      }

      public String end(boolean mostCompact) {
         int firstDiff = this.firstDifference();
         if (firstDiff == this.ranges.length) {
            return null;
         } else {
            StringBuilder result = new StringBuilder();

            for(int i = mostCompact ? firstDiff : 0; i < this.ranges.length; ++i) {
               result.appendCodePoint(this.ranges[i].max);
            }

            return result.toString();
         }
      }

      public int firstDifference() {
         for(int i = 0; i < this.ranges.length; ++i) {
            if (this.ranges[i].min != this.ranges[i].max) {
               return i;
            }
         }

         return this.ranges.length;
      }

      public Integer size() {
         return this.ranges.length;
      }

      public int compareTo(Ranges other) {
         int diff = this.ranges.length - other.ranges.length;
         if (diff != 0) {
            return diff;
         } else {
            for(int i = 0; i < this.ranges.length; ++i) {
               diff = this.ranges[i].compareTo(other.ranges[i]);
               if (diff != 0) {
                  return diff;
               }
            }

            return 0;
         }
      }

      public String toString() {
         String start = this.start();
         String end = this.end(false);
         return end == null ? start : start + "~" + end;
      }
   }

   static final class Range implements Comparable {
      int min;
      int max;

      public Range(int min, int max) {
         this.min = min;
         this.max = max;
      }

      public boolean equals(Object obj) {
         return this == obj || obj != null && obj instanceof Range && this.compareTo((Range)obj) == 0;
      }

      public int compareTo(Range that) {
         int diff = this.min - that.min;
         return diff != 0 ? diff : this.max - that.max;
      }

      public int hashCode() {
         return this.min * 37 + this.max;
      }

      public String toString() {
         StringBuilder result = (new StringBuilder()).appendCodePoint(this.min);
         return this.min == this.max ? result.toString() : result.append('~').appendCodePoint(this.max).toString();
      }
   }

   public interface Adder {
      void add(String var1, String var2);
   }
}
