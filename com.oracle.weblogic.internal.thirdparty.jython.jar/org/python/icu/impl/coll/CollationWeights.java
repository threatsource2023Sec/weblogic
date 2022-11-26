package org.python.icu.impl.coll;

import java.util.Arrays;

public final class CollationWeights {
   private int middleLength;
   private int[] minBytes = new int[5];
   private int[] maxBytes = new int[5];
   private WeightRange[] ranges = new WeightRange[7];
   private int rangeIndex;
   private int rangeCount;

   public void initForPrimary(boolean compressible) {
      this.middleLength = 1;
      this.minBytes[1] = 3;
      this.maxBytes[1] = 255;
      if (compressible) {
         this.minBytes[2] = 4;
         this.maxBytes[2] = 254;
      } else {
         this.minBytes[2] = 2;
         this.maxBytes[2] = 255;
      }

      this.minBytes[3] = 2;
      this.maxBytes[3] = 255;
      this.minBytes[4] = 2;
      this.maxBytes[4] = 255;
   }

   public void initForSecondary() {
      this.middleLength = 3;
      this.minBytes[1] = 0;
      this.maxBytes[1] = 0;
      this.minBytes[2] = 0;
      this.maxBytes[2] = 0;
      this.minBytes[3] = 2;
      this.maxBytes[3] = 255;
      this.minBytes[4] = 2;
      this.maxBytes[4] = 255;
   }

   public void initForTertiary() {
      this.middleLength = 3;
      this.minBytes[1] = 0;
      this.maxBytes[1] = 0;
      this.minBytes[2] = 0;
      this.maxBytes[2] = 0;
      this.minBytes[3] = 2;
      this.maxBytes[3] = 63;
      this.minBytes[4] = 2;
      this.maxBytes[4] = 63;
   }

   public boolean allocWeights(long lowerLimit, long upperLimit, int n) {
      if (!this.getWeightRanges(lowerLimit, upperLimit)) {
         return false;
      } else {
         while(true) {
            int minLength = this.ranges[0].length;
            if (this.allocWeightsInShortRanges(n, minLength)) {
               break;
            }

            if (minLength == 4) {
               return false;
            }

            if (this.allocWeightsInMinLengthRanges(n, minLength)) {
               break;
            }

            for(int i = 0; this.ranges[i].length == minLength; ++i) {
               this.lengthenRange(this.ranges[i]);
            }
         }

         this.rangeIndex = 0;
         if (this.rangeCount < this.ranges.length) {
            this.ranges[this.rangeCount] = null;
         }

         return true;
      }
   }

   public long nextWeight() {
      if (this.rangeIndex >= this.rangeCount) {
         return 4294967295L;
      } else {
         WeightRange range = this.ranges[this.rangeIndex];
         long weight = range.start;
         if (--range.count == 0) {
            ++this.rangeIndex;
         } else {
            range.start = this.incWeight(weight, range.length);

            assert range.start <= range.end;
         }

         return weight;
      }
   }

   public static int lengthOfWeight(long weight) {
      if ((weight & 16777215L) == 0L) {
         return 1;
      } else if ((weight & 65535L) == 0L) {
         return 2;
      } else {
         return (weight & 255L) == 0L ? 3 : 4;
      }
   }

   private static int getWeightTrail(long weight, int length) {
      return (int)(weight >> 8 * (4 - length)) & 255;
   }

   private static long setWeightTrail(long weight, int length, int trail) {
      length = 8 * (4 - length);
      return weight & 4294967040L << length | (long)trail << length;
   }

   private static int getWeightByte(long weight, int idx) {
      return getWeightTrail(weight, idx);
   }

   private static long setWeightByte(long weight, int idx, int b) {
      idx *= 8;
      long mask;
      if (idx < 32) {
         mask = 4294967295L >> idx;
      } else {
         mask = 0L;
      }

      idx = 32 - idx;
      mask |= 4294967040L << idx;
      return weight & mask | (long)b << idx;
   }

   private static long truncateWeight(long weight, int length) {
      return weight & 4294967295L << 8 * (4 - length);
   }

   private static long incWeightTrail(long weight, int length) {
      return weight + (1L << 8 * (4 - length));
   }

   private static long decWeightTrail(long weight, int length) {
      return weight - (1L << 8 * (4 - length));
   }

   private int countBytes(int idx) {
      return this.maxBytes[idx] - this.minBytes[idx] + 1;
   }

   private long incWeight(long weight, int length) {
      do {
         int b = getWeightByte(weight, length);
         if (b < this.maxBytes[length]) {
            return setWeightByte(weight, length, b + 1);
         }

         weight = setWeightByte(weight, length, this.minBytes[length]);
         --length;
      } while($assertionsDisabled || length > 0);

      throw new AssertionError();
   }

   private long incWeightByOffset(long weight, int length, int offset) {
      do {
         offset += getWeightByte(weight, length);
         if (offset <= this.maxBytes[length]) {
            return setWeightByte(weight, length, offset);
         }

         offset -= this.minBytes[length];
         weight = setWeightByte(weight, length, this.minBytes[length] + offset % this.countBytes(length));
         offset /= this.countBytes(length);
         --length;
      } while($assertionsDisabled || length > 0);

      throw new AssertionError();
   }

   private void lengthenRange(WeightRange range) {
      int length = range.length + 1;
      range.start = setWeightTrail(range.start, length, this.minBytes[length]);
      range.end = setWeightTrail(range.end, length, this.maxBytes[length]);
      range.count *= this.countBytes(length);
      range.length = length;
   }

   private boolean getWeightRanges(long lowerLimit, long upperLimit) {
      assert lowerLimit != 0L;

      assert upperLimit != 0L;

      int lowerLength = lengthOfWeight(lowerLimit);
      int upperLength = lengthOfWeight(upperLimit);

      assert lowerLength >= this.middleLength;

      if (lowerLimit >= upperLimit) {
         return false;
      } else if (lowerLength < upperLength && lowerLimit == truncateWeight(upperLimit, lowerLength)) {
         return false;
      } else {
         WeightRange[] lower = new WeightRange[5];
         WeightRange middle = new WeightRange();
         WeightRange[] upper = new WeightRange[5];
         long weight = lowerLimit;

         int length;
         int trail;
         for(length = lowerLength; length > this.middleLength; --length) {
            trail = getWeightTrail(weight, length);
            if (trail < this.maxBytes[length]) {
               lower[length] = new WeightRange();
               lower[length].start = incWeightTrail(weight, length);
               lower[length].end = setWeightTrail(weight, length, this.maxBytes[length]);
               lower[length].length = length;
               lower[length].count = this.maxBytes[length] - trail;
            }

            weight = truncateWeight(weight, length - 1);
         }

         if (weight < 4278190080L) {
            middle.start = incWeightTrail(weight, this.middleLength);
         } else {
            middle.start = 4294967295L;
         }

         weight = upperLimit;

         for(length = upperLength; length > this.middleLength; --length) {
            trail = getWeightTrail(weight, length);
            if (trail > this.minBytes[length]) {
               upper[length] = new WeightRange();
               upper[length].start = setWeightTrail(weight, length, this.minBytes[length]);
               upper[length].end = decWeightTrail(weight, length);
               upper[length].length = length;
               upper[length].count = trail - this.minBytes[length];
            }

            weight = truncateWeight(weight, length - 1);
         }

         middle.end = decWeightTrail(weight, this.middleLength);
         middle.length = this.middleLength;
         if (middle.end >= middle.start) {
            middle.count = (int)(middle.end - middle.start >> 8 * (4 - this.middleLength)) + 1;
         } else {
            label127:
            for(length = 4; length > this.middleLength; --length) {
               if (lower[length] != null && upper[length] != null && lower[length].count > 0 && upper[length].count > 0) {
                  long lowerEnd = lower[length].end;
                  long upperStart = upper[length].start;
                  boolean merged = false;
                  if (lowerEnd > upperStart) {
                     assert truncateWeight(lowerEnd, length - 1) == truncateWeight(upperStart, length - 1);

                     lower[length].end = upper[length].end;
                     lower[length].count = getWeightTrail(lower[length].end, length) - getWeightTrail(lower[length].start, length) + 1;
                     merged = true;
                  } else if (lowerEnd == upperStart) {
                     assert this.minBytes[length] < this.maxBytes[length];
                  } else if (this.incWeight(lowerEnd, length) == upperStart) {
                     lower[length].end = upper[length].end;
                     lower[length].count += upper[length].count;
                     merged = true;
                  }

                  if (merged) {
                     upper[length].count = 0;

                     while(true) {
                        --length;
                        if (length <= this.middleLength) {
                           break label127;
                        }

                        lower[length] = upper[length] = null;
                     }
                  }
               }
            }
         }

         this.rangeCount = 0;
         if (middle.count > 0) {
            this.ranges[0] = middle;
            this.rangeCount = 1;
         }

         for(length = this.middleLength + 1; length <= 4; ++length) {
            if (upper[length] != null && upper[length].count > 0) {
               this.ranges[this.rangeCount++] = upper[length];
            }

            if (lower[length] != null && lower[length].count > 0) {
               this.ranges[this.rangeCount++] = lower[length];
            }
         }

         return this.rangeCount > 0;
      }
   }

   private boolean allocWeightsInShortRanges(int n, int minLength) {
      for(int i = 0; i < this.rangeCount && this.ranges[i].length <= minLength + 1; ++i) {
         if (n <= this.ranges[i].count) {
            if (this.ranges[i].length > minLength) {
               this.ranges[i].count = n;
            }

            this.rangeCount = i + 1;
            if (this.rangeCount > 1) {
               Arrays.sort(this.ranges, 0, this.rangeCount);
            }

            return true;
         }

         n -= this.ranges[i].count;
      }

      return false;
   }

   private boolean allocWeightsInMinLengthRanges(int n, int minLength) {
      int count = 0;

      int minLengthRangeCount;
      for(minLengthRangeCount = 0; minLengthRangeCount < this.rangeCount && this.ranges[minLengthRangeCount].length == minLength; ++minLengthRangeCount) {
         count += this.ranges[minLengthRangeCount].count;
      }

      int nextCountBytes = this.countBytes(minLength + 1);
      if (n > count * nextCountBytes) {
         return false;
      } else {
         long start = this.ranges[0].start;
         long end = this.ranges[0].end;

         int count2;
         for(count2 = 1; count2 < minLengthRangeCount; ++count2) {
            if (this.ranges[count2].start < start) {
               start = this.ranges[count2].start;
            }

            if (this.ranges[count2].end > end) {
               end = this.ranges[count2].end;
            }
         }

         count2 = (n - count) / (nextCountBytes - 1);
         int count1 = count - count2;
         if (count2 == 0 || count1 + count2 * nextCountBytes < n) {
            ++count2;
            --count1;

            assert count1 + count2 * nextCountBytes >= n;
         }

         this.ranges[0].start = start;
         if (count1 == 0) {
            this.ranges[0].end = end;
            this.ranges[0].count = count;
            this.lengthenRange(this.ranges[0]);
            this.rangeCount = 1;
         } else {
            this.ranges[0].end = this.incWeightByOffset(start, minLength, count1 - 1);
            this.ranges[0].count = count1;
            if (this.ranges[1] == null) {
               this.ranges[1] = new WeightRange();
            }

            this.ranges[1].start = this.incWeight(this.ranges[0].end, minLength);
            this.ranges[1].end = end;
            this.ranges[1].length = minLength;
            this.ranges[1].count = count2;
            this.lengthenRange(this.ranges[1]);
            this.rangeCount = 2;
         }

         return true;
      }
   }

   private static final class WeightRange implements Comparable {
      long start;
      long end;
      int length;
      int count;

      private WeightRange() {
      }

      public int compareTo(WeightRange other) {
         long l = this.start;
         long r = other.start;
         if (l < r) {
            return -1;
         } else {
            return l > r ? 1 : 0;
         }
      }

      // $FF: synthetic method
      WeightRange(Object x0) {
         this();
      }
   }
}
