package org.python.icu.impl.coll;

public final class CollationRootElements {
   public static final long PRIMARY_SENTINEL = 4294967040L;
   public static final int SEC_TER_DELTA_FLAG = 128;
   public static final int PRIMARY_STEP_MASK = 127;
   public static final int IX_FIRST_TERTIARY_INDEX = 0;
   static final int IX_FIRST_SECONDARY_INDEX = 1;
   static final int IX_FIRST_PRIMARY_INDEX = 2;
   static final int IX_COMMON_SEC_AND_TER_CE = 3;
   static final int IX_SEC_TER_BOUNDARIES = 4;
   static final int IX_COUNT = 5;
   private long[] elements;

   public CollationRootElements(long[] rootElements) {
      this.elements = rootElements;
   }

   public int getTertiaryBoundary() {
      return (int)this.elements[4] << 8 & '\uff00';
   }

   long getFirstTertiaryCE() {
      return this.elements[(int)this.elements[0]] & -129L;
   }

   long getLastTertiaryCE() {
      return this.elements[(int)this.elements[1] - 1] & -129L;
   }

   public int getLastCommonSecondary() {
      return (int)this.elements[4] >> 16 & '\uff00';
   }

   public int getSecondaryBoundary() {
      return (int)this.elements[4] >> 8 & '\uff00';
   }

   long getFirstSecondaryCE() {
      return this.elements[(int)this.elements[1]] & -129L;
   }

   long getLastSecondaryCE() {
      return this.elements[(int)this.elements[2] - 1] & -129L;
   }

   long getFirstPrimary() {
      return this.elements[(int)this.elements[2]];
   }

   long getFirstPrimaryCE() {
      return Collation.makeCE(this.getFirstPrimary());
   }

   long lastCEWithPrimaryBefore(long p) {
      if (p == 0L) {
         return 0L;
      } else {
         assert p > this.elements[(int)this.elements[2]];

         int index = this.findP(p);
         long q = this.elements[index];
         long secTer;
         if (p == (q & 4294967040L)) {
            assert (q & 127L) == 0L;

            secTer = this.elements[index - 1];
            if ((secTer & 128L) == 0L) {
               p = secTer & 4294967040L;
               secTer = 83887360L;
            } else {
               index -= 2;

               while(true) {
                  p = this.elements[index];
                  if ((p & 128L) == 0L) {
                     p &= 4294967040L;
                     break;
                  }

                  --index;
               }
            }
         } else {
            p = q & 4294967040L;
            secTer = 83887360L;

            while(true) {
               ++index;
               q = this.elements[index];
               if ((q & 128L) == 0L) {
                  assert (q & 127L) == 0L;
                  break;
               }

               secTer = q;
            }
         }

         return p << 32 | secTer & -129L;
      }
   }

   long firstCEWithPrimaryAtLeast(long p) {
      if (p == 0L) {
         return 0L;
      } else {
         int index = this.findP(p);
         if (p != (this.elements[index] & 4294967040L)) {
            do {
               ++index;
               p = this.elements[index];
            } while((p & 128L) != 0L);

            assert (p & 127L) == 0L;
         }

         return p << 32 | 83887360L;
      }
   }

   long getPrimaryBefore(long p, boolean isCompressible) {
      int index = this.findPrimary(p);
      long q = this.elements[index];
      int step;
      if (p == (q & 4294967040L)) {
         step = (int)q & 127;
         if (step == 0) {
            do {
               --index;
               p = this.elements[index];
            } while((p & 128L) != 0L);

            return p & 4294967040L;
         }
      } else {
         long nextElement = this.elements[index + 1];

         assert isEndOfPrimaryRange(nextElement);

         step = (int)nextElement & 127;
      }

      return (p & 65535L) == 0L ? Collation.decTwoBytePrimaryByOneStep(p, isCompressible, step) : Collation.decThreeBytePrimaryByOneStep(p, isCompressible, step);
   }

   int getSecondaryBefore(long p, int s) {
      int index;
      int previousSec;
      int sec;
      if (p == 0L) {
         index = (int)this.elements[1];
         previousSec = 0;
         sec = (int)(this.elements[index] >> 16);
      } else {
         index = this.findPrimary(p) + 1;
         previousSec = 256;
         sec = (int)this.getFirstSecTerForPrimary(index) >>> 16;
      }

      assert s >= sec;

      while(s > sec) {
         previousSec = sec;

         assert (this.elements[index] & 128L) != 0L;

         sec = (int)(this.elements[index++] >> 16);
      }

      assert sec == s;

      return previousSec;
   }

   int getTertiaryBefore(long p, int s, int t) {
      assert (t & -16192) == 0;

      int index;
      int previousTer;
      long secTer;
      if (p == 0L) {
         if (s == 0) {
            index = (int)this.elements[0];
            previousTer = 0;
         } else {
            index = (int)this.elements[1];
            previousTer = 256;
         }

         secTer = this.elements[index] & -129L;
      } else {
         index = this.findPrimary(p) + 1;
         previousTer = 256;
         secTer = this.getFirstSecTerForPrimary(index);
      }

      long st;
      for(st = (long)s << 16 | (long)t; st > secTer; secTer = this.elements[index++] & -129L) {
         if ((int)(secTer >> 16) == s) {
            previousTer = (int)secTer;
         }

         assert (this.elements[index] & 128L) != 0L;
      }

      assert secTer == st;

      return previousTer & '\uffff';
   }

   int findPrimary(long p) {
      assert (p & 255L) == 0L;

      int index = this.findP(p);

      assert isEndOfPrimaryRange(this.elements[index + 1]) || p == (this.elements[index] & 4294967040L);

      return index;
   }

   long getPrimaryAfter(long p, int index, boolean isCompressible) {
      assert p == (this.elements[index] & 4294967040L) || isEndOfPrimaryRange(this.elements[index + 1]);

      ++index;
      long q = this.elements[index];
      int step;
      if ((q & 128L) == 0L && (step = (int)q & 127) != 0) {
         return (p & 65535L) == 0L ? Collation.incTwoBytePrimaryByOffset(p, isCompressible, step) : Collation.incThreeBytePrimaryByOffset(p, isCompressible, step);
      } else {
         while((q & 128L) != 0L) {
            ++index;
            q = this.elements[index];
         }

         assert (q & 127L) == 0L;

         return q;
      }
   }

   int getSecondaryAfter(int index, int s) {
      long secTer;
      int secLimit;
      if (index == 0) {
         assert s != 0;

         index = (int)this.elements[1];
         secTer = this.elements[index];
         secLimit = 65536;
      } else {
         assert index >= (int)this.elements[2];

         secTer = this.getFirstSecTerForPrimary(index + 1);
         secLimit = this.getSecondaryBoundary();
      }

      do {
         int sec = (int)(secTer >> 16);
         if (sec > s) {
            return sec;
         }

         ++index;
         secTer = this.elements[index];
      } while((secTer & 128L) != 0L);

      return secLimit;
   }

   int getTertiaryAfter(int index, int s, int t) {
      int terLimit;
      long secTer;
      if (index == 0) {
         if (s == 0) {
            assert t != 0;

            index = (int)this.elements[0];
            terLimit = 16384;
         } else {
            index = (int)this.elements[1];
            terLimit = this.getTertiaryBoundary();
         }

         secTer = this.elements[index] & -129L;
      } else {
         assert index >= (int)this.elements[2];

         secTer = this.getFirstSecTerForPrimary(index + 1);
         terLimit = this.getTertiaryBoundary();
      }

      for(long st = ((long)s & 4294967295L) << 16 | (long)t; secTer <= st; secTer &= -129L) {
         ++index;
         secTer = this.elements[index];
         if ((secTer & 128L) == 0L || secTer >> 16 > (long)s) {
            return terLimit;
         }
      }

      assert secTer >> 16 == (long)s;

      return (int)secTer & '\uffff';
   }

   private long getFirstSecTerForPrimary(int index) {
      long secTer = this.elements[index];
      if ((secTer & 128L) == 0L) {
         return 83887360L;
      } else {
         secTer &= -129L;
         return secTer > 83887360L ? 83887360L : secTer;
      }
   }

   private int findP(long p) {
      assert p >> 24 != 254L;

      int start = (int)this.elements[2];

      assert p >= this.elements[start];

      int limit = this.elements.length - 1;

      assert this.elements[limit] >= 4294967040L;

      assert p < this.elements[limit];

      while(start + 1 < limit) {
         int i = (int)(((long)start + (long)limit) / 2L);
         long q = this.elements[i];
         if ((q & 128L) != 0L) {
            int j;
            for(j = i + 1; j != limit; ++j) {
               q = this.elements[j];
               if ((q & 128L) == 0L) {
                  i = j;
                  break;
               }
            }

            if ((q & 128L) != 0L) {
               for(j = i - 1; j != start; --j) {
                  q = this.elements[j];
                  if ((q & 128L) == 0L) {
                     i = j;
                     break;
                  }
               }

               if ((q & 128L) != 0L) {
                  break;
               }
            }
         }

         if (p < (q & 4294967040L)) {
            limit = i;
         } else {
            start = i;
         }
      }

      return start;
   }

   private static boolean isEndOfPrimaryRange(long q) {
      return (q & 128L) == 0L && (q & 127L) != 0L;
   }
}
