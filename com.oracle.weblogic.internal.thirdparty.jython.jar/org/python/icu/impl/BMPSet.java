package org.python.icu.impl;

import org.python.icu.text.UnicodeSet;
import org.python.icu.util.OutputInt;

public final class BMPSet {
   public static int U16_SURROGATE_OFFSET = 56613888;
   private boolean[] latin1Contains;
   private int[] table7FF;
   private int[] bmpBlockBits;
   private int[] list4kStarts;
   private final int[] list;
   private final int listLength;

   public BMPSet(int[] parentList, int parentListLength) {
      this.list = parentList;
      this.listLength = parentListLength;
      this.latin1Contains = new boolean[256];
      this.table7FF = new int[64];
      this.bmpBlockBits = new int[64];
      this.list4kStarts = new int[18];
      this.list4kStarts[0] = this.findCodePoint(2048, 0, this.listLength - 1);

      for(int i = 1; i <= 16; ++i) {
         this.list4kStarts[i] = this.findCodePoint(i << 12, this.list4kStarts[i - 1], this.listLength - 1);
      }

      this.list4kStarts[17] = this.listLength - 1;
      this.initBits();
   }

   public BMPSet(BMPSet otherBMPSet, int[] newParentList, int newParentListLength) {
      this.list = newParentList;
      this.listLength = newParentListLength;
      this.latin1Contains = (boolean[])otherBMPSet.latin1Contains.clone();
      this.table7FF = (int[])otherBMPSet.table7FF.clone();
      this.bmpBlockBits = (int[])otherBMPSet.bmpBlockBits.clone();
      this.list4kStarts = (int[])otherBMPSet.list4kStarts.clone();
   }

   public boolean contains(int c) {
      if (c <= 255) {
         return this.latin1Contains[c];
      } else if (c <= 2047) {
         return (this.table7FF[c & 63] & 1 << (c >> 6)) != 0;
      } else if (c < 55296 || c >= 57344 && c <= 65535) {
         int lead = c >> 12;
         int twoBits = this.bmpBlockBits[c >> 6 & 63] >> lead & 65537;
         if (twoBits <= 1) {
            return 0 != twoBits;
         } else {
            return this.containsSlow(c, this.list4kStarts[lead], this.list4kStarts[lead + 1]);
         }
      } else {
         return c <= 1114111 ? this.containsSlow(c, this.list4kStarts[13], this.list4kStarts[17]) : false;
      }
   }

   public final int span(CharSequence s, int start, UnicodeSet.SpanCondition spanCondition, OutputInt outCount) {
      int i = start;
      int limit = s.length();
      int numSupplementary = 0;
      char c;
      char c2;
      int lead;
      int twoBits;
      if (UnicodeSet.SpanCondition.NOT_CONTAINED != spanCondition) {
         for(; i < limit; ++i) {
            c = s.charAt(i);
            if (c <= 255) {
               if (!this.latin1Contains[c]) {
                  break;
               }
            } else if (c <= 2047) {
               if ((this.table7FF[c & 63] & 1 << (c >> 6)) == 0) {
                  break;
               }
            } else if (c >= '\ud800' && c < '\udc00' && i + 1 != limit && (c2 = s.charAt(i + 1)) >= '\udc00' && c2 < '\ue000') {
               lead = Character.toCodePoint(c, c2);
               if (!this.containsSlow(lead, this.list4kStarts[16], this.list4kStarts[17])) {
                  break;
               }

               ++numSupplementary;
               ++i;
            } else {
               lead = c >> 12;
               twoBits = this.bmpBlockBits[c >> 6 & 63] >> lead & 65537;
               if (twoBits <= 1) {
                  if (twoBits == 0) {
                     break;
                  }
               } else if (!this.containsSlow(c, this.list4kStarts[lead], this.list4kStarts[lead + 1])) {
                  break;
               }
            }
         }
      } else {
         for(; i < limit; ++i) {
            c = s.charAt(i);
            if (c <= 255) {
               if (this.latin1Contains[c]) {
                  break;
               }
            } else if (c <= 2047) {
               if ((this.table7FF[c & 63] & 1 << (c >> 6)) != 0) {
                  break;
               }
            } else if (c >= '\ud800' && c < '\udc00' && i + 1 != limit && (c2 = s.charAt(i + 1)) >= '\udc00' && c2 < '\ue000') {
               lead = Character.toCodePoint(c, c2);
               if (this.containsSlow(lead, this.list4kStarts[16], this.list4kStarts[17])) {
                  break;
               }

               ++numSupplementary;
               ++i;
            } else {
               lead = c >> 12;
               twoBits = this.bmpBlockBits[c >> 6 & 63] >> lead & 65537;
               if (twoBits <= 1) {
                  if (twoBits != 0) {
                     break;
                  }
               } else if (this.containsSlow(c, this.list4kStarts[lead], this.list4kStarts[lead + 1])) {
                  break;
               }
            }
         }
      }

      if (outCount != null) {
         lead = i - start;
         outCount.value = lead - numSupplementary;
      }

      return i;
   }

   public final int spanBack(CharSequence s, int limit, UnicodeSet.SpanCondition spanCondition) {
      char c;
      char c2;
      int lead;
      int twoBits;
      if (UnicodeSet.SpanCondition.NOT_CONTAINED != spanCondition) {
         while(true) {
            --limit;
            c = s.charAt(limit);
            if (c <= 255) {
               if (!this.latin1Contains[c]) {
                  break;
               }
            } else if (c <= 2047) {
               if ((this.table7FF[c & 63] & 1 << (c >> 6)) == 0) {
                  break;
               }
            } else if (c >= '\ud800' && c >= '\udc00' && 0 != limit && (c2 = s.charAt(limit - 1)) >= '\ud800' && c2 < '\udc00') {
               lead = Character.toCodePoint(c2, c);
               if (!this.containsSlow(lead, this.list4kStarts[16], this.list4kStarts[17])) {
                  break;
               }

               --limit;
            } else {
               lead = c >> 12;
               twoBits = this.bmpBlockBits[c >> 6 & 63] >> lead & 65537;
               if (twoBits <= 1) {
                  if (twoBits == 0) {
                     break;
                  }
               } else if (!this.containsSlow(c, this.list4kStarts[lead], this.list4kStarts[lead + 1])) {
                  break;
               }
            }

            if (0 == limit) {
               return 0;
            }
         }
      } else {
         while(true) {
            --limit;
            c = s.charAt(limit);
            if (c <= 255) {
               if (this.latin1Contains[c]) {
                  break;
               }
            } else if (c <= 2047) {
               if ((this.table7FF[c & 63] & 1 << (c >> 6)) != 0) {
                  break;
               }
            } else if (c >= '\ud800' && c >= '\udc00' && 0 != limit && (c2 = s.charAt(limit - 1)) >= '\ud800' && c2 < '\udc00') {
               lead = Character.toCodePoint(c2, c);
               if (this.containsSlow(lead, this.list4kStarts[16], this.list4kStarts[17])) {
                  break;
               }

               --limit;
            } else {
               lead = c >> 12;
               twoBits = this.bmpBlockBits[c >> 6 & 63] >> lead & 65537;
               if (twoBits <= 1) {
                  if (twoBits != 0) {
                     break;
                  }
               } else if (this.containsSlow(c, this.list4kStarts[lead], this.list4kStarts[lead + 1])) {
                  break;
               }
            }

            if (0 == limit) {
               return 0;
            }
         }
      }

      return limit + 1;
   }

   private static void set32x64Bits(int[] table, int start, int limit) {
      assert 64 == table.length;

      int lead = start >> 6;
      int trail = start & 63;
      int bits = 1 << lead;
      if (start + 1 == limit) {
         table[trail] |= bits;
      } else {
         int limitLead = limit >> 6;
         int limitTrail = limit & 63;
         int var10001;
         if (lead == limitLead) {
            while(trail < limitTrail) {
               var10001 = trail++;
               table[var10001] |= bits;
            }
         } else {
            if (trail > 0) {
               do {
                  var10001 = trail++;
                  table[var10001] |= bits;
               } while(trail < 64);

               ++lead;
            }

            if (lead < limitLead) {
               bits = ~((1 << lead) - 1);
               if (limitLead < 32) {
                  bits &= (1 << limitLead) - 1;
               }

               for(trail = 0; trail < 64; ++trail) {
                  table[trail] |= bits;
               }
            }

            bits = 1 << limitLead;

            for(trail = 0; trail < limitTrail; ++trail) {
               table[trail] |= bits;
            }
         }

      }
   }

   private void initBits() {
      int listIndex = 0;

      int start;
      int limit;
      do {
         start = this.list[listIndex++];
         if (listIndex < this.listLength) {
            limit = this.list[listIndex++];
         } else {
            limit = 1114112;
         }

         if (start >= 256) {
            break;
         }

         do {
            this.latin1Contains[start++] = true;
         } while(start < limit && start < 256);
      } while(limit <= 256);

      while(start < 2048) {
         set32x64Bits(this.table7FF, start, limit <= 2048 ? limit : 2048);
         if (limit > 2048) {
            start = 2048;
            break;
         }

         start = this.list[listIndex++];
         if (listIndex < this.listLength) {
            limit = this.list[listIndex++];
         } else {
            limit = 1114112;
         }
      }

      int minStart = 2048;

      while(start < 65536) {
         if (limit > 65536) {
            limit = 65536;
         }

         if (start < minStart) {
            start = minStart;
         }

         if (start < limit) {
            int[] var10000;
            if (0 != (start & 63)) {
               start >>= 6;
               var10000 = this.bmpBlockBits;
               var10000[start & 63] |= 65537 << (start >> 6);
               start = start + 1 << 6;
               minStart = start;
            }

            if (start < limit) {
               if (start < (limit & -64)) {
                  set32x64Bits(this.bmpBlockBits, start >> 6, limit >> 6);
               }

               if (0 != (limit & 63)) {
                  limit >>= 6;
                  var10000 = this.bmpBlockBits;
                  var10000[limit & 63] |= 65537 << (limit >> 6);
                  limit = limit + 1 << 6;
                  minStart = limit;
               }
            }
         }

         if (limit == 65536) {
            break;
         }

         start = this.list[listIndex++];
         if (listIndex < this.listLength) {
            limit = this.list[listIndex++];
         } else {
            limit = 1114112;
         }
      }

   }

   private int findCodePoint(int c, int lo, int hi) {
      if (c < this.list[lo]) {
         return lo;
      } else if (lo < hi && c < this.list[hi - 1]) {
         while(true) {
            int i = lo + hi >>> 1;
            if (i == lo) {
               return hi;
            }

            if (c < this.list[i]) {
               hi = i;
            } else {
               lo = i;
            }
         }
      } else {
         return hi;
      }
   }

   private final boolean containsSlow(int c, int lo, int hi) {
      return 0 != (this.findCodePoint(c, lo, hi) & 1);
   }
}
