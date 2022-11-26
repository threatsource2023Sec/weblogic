package org.python.icu.impl;

import java.util.ArrayList;
import org.python.icu.text.UTF16;
import org.python.icu.text.UnicodeSet;
import org.python.icu.util.OutputInt;

public class UnicodeSetStringSpan {
   public static final int WITH_COUNT = 64;
   public static final int FWD = 32;
   public static final int BACK = 16;
   public static final int CONTAINED = 2;
   public static final int NOT_CONTAINED = 1;
   public static final int ALL = 127;
   public static final int FWD_UTF16_CONTAINED = 34;
   public static final int FWD_UTF16_NOT_CONTAINED = 33;
   public static final int BACK_UTF16_CONTAINED = 18;
   public static final int BACK_UTF16_NOT_CONTAINED = 17;
   static final short ALL_CP_CONTAINED = 255;
   static final short LONG_SPAN = 254;
   private UnicodeSet spanSet;
   private UnicodeSet spanNotSet;
   private ArrayList strings;
   private short[] spanLengths;
   private final int maxLength16;
   private boolean someRelevant;
   private boolean all;
   private OffsetList offsets;

   public UnicodeSetStringSpan(UnicodeSet set, ArrayList setStrings, int which) {
      this.spanSet = new UnicodeSet(0, 1114111);
      this.strings = setStrings;
      this.all = which == 127;
      this.spanSet.retainAll(set);
      if (0 != (which & 1)) {
         this.spanNotSet = this.spanSet;
      }

      this.offsets = new OffsetList();
      int stringsLength = this.strings.size();
      int maxLength16 = 0;
      this.someRelevant = false;

      int i;
      int allocSize;
      int spanLength;
      for(i = 0; i < stringsLength; ++i) {
         String string = (String)this.strings.get(i);
         allocSize = string.length();
         spanLength = this.spanSet.span(string, UnicodeSet.SpanCondition.CONTAINED);
         if (spanLength < allocSize) {
            this.someRelevant = true;
         }

         if (allocSize > maxLength16) {
            maxLength16 = allocSize;
         }
      }

      this.maxLength16 = maxLength16;
      if (this.someRelevant || (which & 64) != 0) {
         if (this.all) {
            this.spanSet.freeze();
         }

         if (this.all) {
            allocSize = stringsLength * 2;
         } else {
            allocSize = stringsLength;
         }

         this.spanLengths = new short[allocSize];
         int spanBackLengthsOffset;
         if (this.all) {
            spanBackLengthsOffset = stringsLength;
         } else {
            spanBackLengthsOffset = 0;
         }

         for(i = 0; i < stringsLength; ++i) {
            String string = (String)this.strings.get(i);
            int length16 = string.length();
            spanLength = this.spanSet.span(string, UnicodeSet.SpanCondition.CONTAINED);
            if (spanLength < length16) {
               if (0 != (which & 2)) {
                  if (0 != (which & 32)) {
                     this.spanLengths[i] = makeSpanLengthByte(spanLength);
                  }

                  if (0 != (which & 16)) {
                     spanLength = length16 - this.spanSet.spanBack(string, length16, UnicodeSet.SpanCondition.CONTAINED);
                     this.spanLengths[spanBackLengthsOffset + i] = makeSpanLengthByte(spanLength);
                  }
               } else {
                  this.spanLengths[i] = this.spanLengths[spanBackLengthsOffset + i] = 0;
               }

               if (0 != (which & 1)) {
                  int c;
                  if (0 != (which & 32)) {
                     c = string.codePointAt(0);
                     this.addToSpanNotSet(c);
                  }

                  if (0 != (which & 16)) {
                     c = string.codePointBefore(length16);
                     this.addToSpanNotSet(c);
                  }
               }
            } else if (this.all) {
               this.spanLengths[i] = this.spanLengths[spanBackLengthsOffset + i] = 255;
            } else {
               this.spanLengths[i] = 255;
            }
         }

         if (this.all) {
            this.spanNotSet.freeze();
         }

      }
   }

   public UnicodeSetStringSpan(UnicodeSetStringSpan otherStringSpan, ArrayList newParentSetStrings) {
      this.spanSet = otherStringSpan.spanSet;
      this.strings = newParentSetStrings;
      this.maxLength16 = otherStringSpan.maxLength16;
      this.someRelevant = otherStringSpan.someRelevant;
      this.all = true;
      if (Utility.sameObjects(otherStringSpan.spanNotSet, otherStringSpan.spanSet)) {
         this.spanNotSet = this.spanSet;
      } else {
         this.spanNotSet = (UnicodeSet)otherStringSpan.spanNotSet.clone();
      }

      this.offsets = new OffsetList();
      this.spanLengths = (short[])otherStringSpan.spanLengths.clone();
   }

   public boolean needsStringSpanUTF16() {
      return this.someRelevant;
   }

   public boolean contains(int c) {
      return this.spanSet.contains(c);
   }

   private void addToSpanNotSet(int c) {
      if (Utility.sameObjects(this.spanNotSet, (Object)null) || Utility.sameObjects(this.spanNotSet, this.spanSet)) {
         if (this.spanSet.contains(c)) {
            return;
         }

         this.spanNotSet = this.spanSet.cloneAsThawed();
      }

      this.spanNotSet.add(c);
   }

   public int span(CharSequence s, int start, UnicodeSet.SpanCondition spanCondition) {
      if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
         return this.spanNot(s, start, (OutputInt)null);
      } else {
         int spanLimit = this.spanSet.span(s, start, UnicodeSet.SpanCondition.CONTAINED);
         return spanLimit == s.length() ? spanLimit : this.spanWithStrings(s, start, spanLimit, spanCondition);
      }
   }

   private synchronized int spanWithStrings(CharSequence s, int start, int spanLimit, UnicodeSet.SpanCondition spanCondition) {
      int initSize = 0;
      if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
         initSize = this.maxLength16;
      }

      this.offsets.setMaxLength(initSize);
      int length = s.length();
      int pos = spanLimit;
      int rest = length - spanLimit;
      int spanLength = spanLimit - start;
      int stringsLength = this.strings.size();

      while(true) {
         while(true) {
            int overlap;
            label121:
            while(true) {
               int i;
               int length16;
               if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
                  i = 0;

                  while(true) {
                     if (i >= stringsLength) {
                        break label121;
                     }

                     overlap = this.spanLengths[i];
                     if (overlap != 255) {
                        String string = (String)this.strings.get(i);
                        length16 = string.length();
                        if (overlap >= 254) {
                           overlap = string.offsetByCodePoints(length16, -1);
                        }

                        if (overlap > spanLength) {
                           overlap = spanLength;
                        }

                        for(int inc = length16 - overlap; inc <= rest; ++inc) {
                           if (!this.offsets.containsOffset(inc) && matches16CPB(s, pos - overlap, length, string, length16)) {
                              if (inc == rest) {
                                 return length;
                              }

                              this.offsets.addOffset(inc);
                           }

                           if (overlap == 0) {
                              break;
                           }

                           --overlap;
                        }
                     }

                     ++i;
                  }
               }

               overlap = 0;
               int maxOverlap = 0;

               for(i = 0; i < stringsLength; ++i) {
                  length16 = this.spanLengths[i];
                  String string = (String)this.strings.get(i);
                  int length16 = string.length();
                  if (length16 >= 254) {
                     length16 = length16;
                  }

                  if (length16 > spanLength) {
                     length16 = spanLength;
                  }

                  for(int inc = length16 - length16; inc <= rest && length16 >= maxOverlap; ++inc) {
                     if ((length16 > maxOverlap || inc > overlap) && matches16CPB(s, pos - length16, length, string, length16)) {
                        overlap = inc;
                        maxOverlap = length16;
                        break;
                     }

                     --length16;
                  }
               }

               if (overlap == 0 && maxOverlap == 0) {
                  break;
               }

               pos += overlap;
               rest -= overlap;
               if (rest == 0) {
                  return length;
               }

               spanLength = 0;
            }

            if (spanLength == 0 && pos != 0) {
               if (this.offsets.isEmpty()) {
                  spanLimit = this.spanSet.span(s, pos, UnicodeSet.SpanCondition.CONTAINED);
                  spanLength = spanLimit - pos;
                  if (spanLength == rest || spanLength == 0) {
                     return spanLimit;
                  }

                  pos += spanLength;
                  rest -= spanLength;
                  continue;
               }

               spanLength = spanOne(this.spanSet, s, pos, rest);
               if (spanLength > 0) {
                  if (spanLength == rest) {
                     return length;
                  }

                  pos += spanLength;
                  rest -= spanLength;
                  this.offsets.shift(spanLength);
                  spanLength = 0;
                  continue;
               }
            } else if (this.offsets.isEmpty()) {
               return pos;
            }

            overlap = this.offsets.popMinimum((OutputInt)null);
            pos += overlap;
            rest -= overlap;
            spanLength = 0;
         }
      }
   }

   public int spanAndCount(CharSequence s, int start, UnicodeSet.SpanCondition spanCondition, OutputInt outCount) {
      if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
         return this.spanNot(s, start, outCount);
      } else if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
         return this.spanContainedAndCount(s, start, outCount);
      } else {
         int stringsLength = this.strings.size();
         int length = s.length();
         int pos = start;
         int rest = length - start;

         int count;
         int maxInc;
         for(count = 0; rest != 0; rest -= maxInc) {
            int cpLength = spanOne(this.spanSet, s, pos, rest);
            maxInc = cpLength > 0 ? cpLength : 0;

            for(int i = 0; i < stringsLength; ++i) {
               String string = (String)this.strings.get(i);
               int length16 = string.length();
               if (maxInc < length16 && length16 <= rest && matches16CPB(s, pos, length, string, length16)) {
                  maxInc = length16;
               }
            }

            if (maxInc == 0) {
               outCount.value = count;
               return pos;
            }

            ++count;
            pos += maxInc;
         }

         outCount.value = count;
         return pos;
      }
   }

   private synchronized int spanContainedAndCount(CharSequence s, int start, OutputInt outCount) {
      this.offsets.setMaxLength(this.maxLength16);
      int stringsLength = this.strings.size();
      int length = s.length();
      int pos = start;
      int rest = length - start;

      int count;
      int minOffset;
      for(count = 0; rest != 0; rest -= minOffset) {
         int cpLength = spanOne(this.spanSet, s, pos, rest);
         if (cpLength > 0) {
            this.offsets.addOffsetAndCount(cpLength, count + 1);
         }

         for(minOffset = 0; minOffset < stringsLength; ++minOffset) {
            String string = (String)this.strings.get(minOffset);
            int length16 = string.length();
            if (length16 <= rest && !this.offsets.hasCountAtOffset(length16, count + 1) && matches16CPB(s, pos, length, string, length16)) {
               this.offsets.addOffsetAndCount(length16, count + 1);
            }
         }

         if (this.offsets.isEmpty()) {
            outCount.value = count;
            return pos;
         }

         minOffset = this.offsets.popMinimum(outCount);
         count = outCount.value;
         pos += minOffset;
      }

      outCount.value = count;
      return pos;
   }

   public synchronized int spanBack(CharSequence s, int length, UnicodeSet.SpanCondition spanCondition) {
      if (spanCondition == UnicodeSet.SpanCondition.NOT_CONTAINED) {
         return this.spanNotBack(s, length);
      } else {
         int pos = this.spanSet.spanBack(s, length, UnicodeSet.SpanCondition.CONTAINED);
         if (pos == 0) {
            return 0;
         } else {
            int spanLength = length - pos;
            int initSize = 0;
            if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
               initSize = this.maxLength16;
            }

            this.offsets.setMaxLength(initSize);
            int stringsLength = this.strings.size();
            int spanBackLengthsOffset = 0;
            if (this.all) {
               spanBackLengthsOffset = stringsLength;
            }

            do {
               while(true) {
                  int overlap;
                  label130:
                  while(true) {
                     int i;
                     int length16;
                     if (spanCondition == UnicodeSet.SpanCondition.CONTAINED) {
                        i = 0;

                        while(true) {
                           if (i >= stringsLength) {
                              break label130;
                           }

                           overlap = this.spanLengths[spanBackLengthsOffset + i];
                           if (overlap != 255) {
                              String string = (String)this.strings.get(i);
                              length16 = string.length();
                              int dec;
                              if (overlap >= 254) {
                                 int len1 = false;
                                 dec = string.offsetByCodePoints(0, 1);
                                 overlap = length16 - dec;
                              }

                              if (overlap > spanLength) {
                                 overlap = spanLength;
                              }

                              for(dec = length16 - overlap; dec <= pos; ++dec) {
                                 if (!this.offsets.containsOffset(dec) && matches16CPB(s, pos - dec, length, string, length16)) {
                                    if (dec == pos) {
                                       return 0;
                                    }

                                    this.offsets.addOffset(dec);
                                 }

                                 if (overlap == 0) {
                                    break;
                                 }

                                 --overlap;
                              }
                           }

                           ++i;
                        }
                     }

                     overlap = 0;
                     int maxOverlap = 0;

                     for(i = 0; i < stringsLength; ++i) {
                        length16 = this.spanLengths[spanBackLengthsOffset + i];
                        String string = (String)this.strings.get(i);
                        int length16 = string.length();
                        if (length16 >= 254) {
                           length16 = length16;
                        }

                        if (length16 > spanLength) {
                           length16 = spanLength;
                        }

                        for(int dec = length16 - length16; dec <= pos && length16 >= maxOverlap; ++dec) {
                           if ((length16 > maxOverlap || dec > overlap) && matches16CPB(s, pos - dec, length, string, length16)) {
                              overlap = dec;
                              maxOverlap = length16;
                              break;
                           }

                           --length16;
                        }
                     }

                     if (overlap == 0 && maxOverlap == 0) {
                        break;
                     }

                     pos -= overlap;
                     if (pos == 0) {
                        return 0;
                     }

                     spanLength = 0;
                  }

                  if (spanLength == 0 && pos != length) {
                     if (this.offsets.isEmpty()) {
                        overlap = pos;
                        pos = this.spanSet.spanBack(s, pos, UnicodeSet.SpanCondition.CONTAINED);
                        spanLength = overlap - pos;
                        break;
                     }

                     spanLength = spanOneBack(this.spanSet, s, pos);
                     if (spanLength > 0) {
                        if (spanLength == pos) {
                           return 0;
                        }

                        pos -= spanLength;
                        this.offsets.shift(spanLength);
                        spanLength = 0;
                        continue;
                     }
                  } else if (this.offsets.isEmpty()) {
                     return pos;
                  }

                  pos -= this.offsets.popMinimum((OutputInt)null);
                  spanLength = 0;
               }
            } while(pos != 0 && spanLength != 0);

            return pos;
         }
      }
   }

   private int spanNot(CharSequence s, int start, OutputInt outCount) {
      int length = s.length();
      int pos = start;
      int var10000 = length - start;
      int stringsLength = this.strings.size();
      int count = 0;

      int rest;
      do {
         int spanLimit;
         if (outCount == null) {
            spanLimit = this.spanNotSet.span(s, pos, UnicodeSet.SpanCondition.NOT_CONTAINED);
         } else {
            spanLimit = this.spanNotSet.spanAndCount(s, pos, UnicodeSet.SpanCondition.NOT_CONTAINED, outCount);
            outCount.value = count += outCount.value;
         }

         if (spanLimit == length) {
            return length;
         }

         pos = spanLimit;
         rest = length - spanLimit;
         int cpLength = spanOne(this.spanSet, s, spanLimit, rest);
         if (cpLength > 0) {
            return spanLimit;
         }

         for(int i = 0; i < stringsLength; ++i) {
            if (this.spanLengths[i] != 255) {
               String string = (String)this.strings.get(i);
               int length16 = string.length();
               if (length16 <= rest && matches16CPB(s, pos, length, string, length16)) {
                  return pos;
               }
            }
         }

         pos -= cpLength;
         rest += cpLength;
         ++count;
      } while(rest != 0);

      if (outCount != null) {
         outCount.value = count;
      }

      return length;
   }

   private int spanNotBack(CharSequence s, int length) {
      int pos = length;
      int stringsLength = this.strings.size();

      do {
         pos = this.spanNotSet.spanBack(s, pos, UnicodeSet.SpanCondition.NOT_CONTAINED);
         if (pos == 0) {
            return 0;
         }

         int cpLength = spanOneBack(this.spanSet, s, pos);
         if (cpLength > 0) {
            return pos;
         }

         for(int i = 0; i < stringsLength; ++i) {
            if (this.spanLengths[i] != 255) {
               String string = (String)this.strings.get(i);
               int length16 = string.length();
               if (length16 <= pos && matches16CPB(s, pos - length16, length, string, length16)) {
                  return pos;
               }
            }
         }

         pos += cpLength;
      } while(pos != 0);

      return 0;
   }

   static short makeSpanLengthByte(int spanLength) {
      return spanLength < 254 ? (short)spanLength : 254;
   }

   private static boolean matches16(CharSequence s, int start, String t, int length) {
      int end = start + length;

      do {
         if (length-- <= 0) {
            return true;
         }

         --end;
      } while(s.charAt(end) == t.charAt(length));

      return false;
   }

   static boolean matches16CPB(CharSequence s, int start, int limit, String t, int tlength) {
      return matches16(s, start, t, tlength) && (0 >= start || !Character.isHighSurrogate(s.charAt(start - 1)) || !Character.isLowSurrogate(s.charAt(start))) && (start + tlength >= limit || !Character.isHighSurrogate(s.charAt(start + tlength - 1)) || !Character.isLowSurrogate(s.charAt(start + tlength)));
   }

   static int spanOne(UnicodeSet set, CharSequence s, int start, int length) {
      char c = s.charAt(start);
      if (c >= '\ud800' && c <= '\udbff' && length >= 2) {
         char c2 = s.charAt(start + 1);
         if (UTF16.isTrailSurrogate(c2)) {
            int supplementary = Character.toCodePoint(c, c2);
            return set.contains(supplementary) ? 2 : -2;
         }
      }

      return set.contains(c) ? 1 : -1;
   }

   static int spanOneBack(UnicodeSet set, CharSequence s, int length) {
      char c = s.charAt(length - 1);
      if (c >= '\udc00' && c <= '\udfff' && length >= 2) {
         char c2 = s.charAt(length - 2);
         if (UTF16.isLeadSurrogate(c2)) {
            int supplementary = Character.toCodePoint(c2, c);
            return set.contains(supplementary) ? 2 : -2;
         }
      }

      return set.contains(c) ? 1 : -1;
   }

   private static final class OffsetList {
      private int[] list = new int[16];
      private int length;
      private int start;

      public OffsetList() {
      }

      public void setMaxLength(int maxLength) {
         if (maxLength > this.list.length) {
            this.list = new int[maxLength];
         }

         this.clear();
      }

      public void clear() {
         for(int i = this.list.length; i-- > 0; this.list[i] = 0) {
         }

         this.start = this.length = 0;
      }

      public boolean isEmpty() {
         return this.length == 0;
      }

      public void shift(int delta) {
         int i = this.start + delta;
         if (i >= this.list.length) {
            i -= this.list.length;
         }

         if (this.list[i] != 0) {
            this.list[i] = 0;
            --this.length;
         }

         this.start = i;
      }

      public void addOffset(int offset) {
         int i = this.start + offset;
         if (i >= this.list.length) {
            i -= this.list.length;
         }

         assert this.list[i] == 0;

         this.list[i] = 1;
         ++this.length;
      }

      public void addOffsetAndCount(int offset, int count) {
         assert count > 0;

         int i = this.start + offset;
         if (i >= this.list.length) {
            i -= this.list.length;
         }

         if (this.list[i] == 0) {
            this.list[i] = count;
            ++this.length;
         } else if (count < this.list[i]) {
            this.list[i] = count;
         }

      }

      public boolean containsOffset(int offset) {
         int i = this.start + offset;
         if (i >= this.list.length) {
            i -= this.list.length;
         }

         return this.list[i] != 0;
      }

      public boolean hasCountAtOffset(int offset, int count) {
         int i = this.start + offset;
         if (i >= this.list.length) {
            i -= this.list.length;
         }

         int oldCount = this.list[i];
         return oldCount != 0 && oldCount <= count;
      }

      public int popMinimum(OutputInt outCount) {
         int i = this.start;

         int count;
         int result;
         do {
            ++i;
            if (i >= this.list.length) {
               result = this.list.length - this.start;

               for(i = 0; (count = this.list[i]) == 0; ++i) {
               }

               this.list[i] = 0;
               --this.length;
               this.start = i;
               if (outCount != null) {
                  outCount.value = count;
               }

               return result + i;
            }

            count = this.list[i];
         } while(count == 0);

         this.list[i] = 0;
         --this.length;
         result = i - this.start;
         this.start = i;
         if (outCount != null) {
            outCount.value = count;
         }

         return result;
      }
   }
}
