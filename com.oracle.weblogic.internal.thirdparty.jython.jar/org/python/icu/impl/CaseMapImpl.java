package org.python.icu.impl;

import java.io.IOException;
import org.python.icu.text.BreakIterator;
import org.python.icu.text.Edits;
import org.python.icu.util.ICUUncheckedIOException;

public final class CaseMapImpl {
   public static final int OMIT_UNCHANGED_TEXT = 16384;

   private static int appendCodePoint(Appendable a, int c) throws IOException {
      if (c <= 65535) {
         a.append((char)c);
         return 1;
      } else {
         a.append((char)('ퟀ' + (c >> 10)));
         a.append((char)('\udc00' + (c & 1023)));
         return 2;
      }
   }

   private static void appendResult(int result, Appendable dest, int cpLength, int options, Edits edits) throws IOException {
      if (result < 0) {
         if (edits != null) {
            edits.addUnchanged(cpLength);
            if ((options & 16384) != 0) {
               return;
            }
         }

         appendCodePoint(dest, ~result);
      } else if (result <= 31) {
         if (edits != null) {
            edits.addReplace(cpLength, result);
         }
      } else {
         int length = appendCodePoint(dest, result);
         if (edits != null) {
            edits.addReplace(cpLength, length);
         }
      }

   }

   private static final void appendUnchanged(CharSequence src, int start, int length, Appendable dest, int options, Edits edits) throws IOException {
      if (length > 0) {
         if (edits != null) {
            edits.addUnchanged(length);
            if ((options & 16384) != 0) {
               return;
            }
         }

         dest.append(src, start, start + length);
      }

   }

   private static void internalToLower(int caseLocale, int options, StringContextIterator iter, Appendable dest, Edits edits) throws IOException {
      int c;
      while((c = iter.nextCaseMapCP()) >= 0) {
         c = UCaseProps.INSTANCE.toFullLower(c, iter, dest, caseLocale);
         appendResult(c, dest, iter.getCPLength(), options, edits);
      }

   }

   public static Appendable toLower(int caseLocale, int options, CharSequence src, Appendable dest, Edits edits) {
      try {
         if (edits != null) {
            edits.reset();
         }

         StringContextIterator iter = new StringContextIterator(src);
         internalToLower(caseLocale, options, iter, dest, edits);
         return dest;
      } catch (IOException var6) {
         throw new ICUUncheckedIOException(var6);
      }
   }

   public static Appendable toUpper(int caseLocale, int options, CharSequence src, Appendable dest, Edits edits) {
      try {
         if (edits != null) {
            edits.reset();
         }

         if (caseLocale == 4) {
            return CaseMapImpl.GreekUpper.toUpper(options, src, dest, edits);
         } else {
            StringContextIterator iter = new StringContextIterator(src);

            int c;
            while((c = iter.nextCaseMapCP()) >= 0) {
               c = UCaseProps.INSTANCE.toFullUpper(c, iter, dest, caseLocale);
               appendResult(c, dest, iter.getCPLength(), options, edits);
            }

            return dest;
         }
      } catch (IOException var7) {
         throw new ICUUncheckedIOException(var7);
      }
   }

   public static Appendable toTitle(int caseLocale, int options, BreakIterator titleIter, CharSequence src, Appendable dest, Edits edits) {
      try {
         if (edits != null) {
            edits.reset();
         }

         StringContextIterator iter = new StringContextIterator(src);
         int srcLength = src.length();
         int prev = 0;

         int index;
         for(boolean isFirstIndex = true; prev < srcLength; prev = index) {
            if (isFirstIndex) {
               isFirstIndex = false;
               index = titleIter.first();
            } else {
               index = titleIter.next();
            }

            if (index == -1 || index > srcLength) {
               index = srcLength;
            }

            if (prev < index) {
               int titleStart = prev;
               iter.setLimit(index);
               int c = iter.nextCaseMapCP();
               if ((options & 512) == 0 && 0 == UCaseProps.INSTANCE.getType(c)) {
                  while((c = iter.nextCaseMapCP()) >= 0 && 0 == UCaseProps.INSTANCE.getType(c)) {
                  }

                  titleStart = iter.getCPStart();
                  appendUnchanged(src, prev, titleStart - prev, dest, options, edits);
               }

               if (titleStart < index) {
                  int titleLimit = iter.getCPLimit();
                  c = UCaseProps.INSTANCE.toFullTitle(c, iter, dest, caseLocale);
                  appendResult(c, dest, iter.getCPLength(), options, edits);
                  if (titleStart + 1 < index && caseLocale == 5) {
                     char c1 = src.charAt(titleStart);
                     if (c1 == 'i' || c1 == 'I') {
                        char c2 = src.charAt(titleStart + 1);
                        if (c2 == 'j') {
                           dest.append('J');
                           if (edits != null) {
                              edits.addReplace(1, 1);
                           }

                           c = iter.nextCaseMapCP();
                           ++titleLimit;

                           assert c == c2;

                           assert titleLimit == iter.getCPLimit();
                        } else if (c2 == 'J') {
                           appendUnchanged(src, titleStart + 1, 1, dest, options, edits);
                           c = iter.nextCaseMapCP();
                           ++titleLimit;

                           assert c == c2;

                           assert titleLimit == iter.getCPLimit();
                        }
                     }
                  }

                  if (titleLimit < index) {
                     if ((options & 256) == 0) {
                        internalToLower(caseLocale, options, iter, dest, edits);
                     } else {
                        appendUnchanged(src, titleLimit, index - titleLimit, dest, options, edits);
                        iter.moveToLimit();
                     }
                  }
               }
            }
         }

         return dest;
      } catch (IOException var16) {
         throw new ICUUncheckedIOException(var16);
      }
   }

   public static Appendable fold(int options, CharSequence src, Appendable dest, Edits edits) {
      try {
         if (edits != null) {
            edits.reset();
         }

         int length = src.length();
         int i = 0;

         while(i < length) {
            int c = Character.codePointAt(src, i);
            int cpLength = Character.charCount(c);
            i += cpLength;
            c = UCaseProps.INSTANCE.toFullFolding(c, dest, options);
            appendResult(c, dest, cpLength, options, edits);
         }

         return dest;
      } catch (IOException var8) {
         throw new ICUUncheckedIOException(var8);
      }
   }

   private static final class GreekUpper {
      private static final int UPPER_MASK = 1023;
      private static final int HAS_VOWEL = 4096;
      private static final int HAS_YPOGEGRAMMENI = 8192;
      private static final int HAS_ACCENT = 16384;
      private static final int HAS_DIALYTIKA = 32768;
      private static final int HAS_COMBINING_DIALYTIKA = 65536;
      private static final int HAS_OTHER_GREEK_DIACRITIC = 131072;
      private static final int HAS_VOWEL_AND_ACCENT = 20480;
      private static final int HAS_VOWEL_AND_ACCENT_AND_DIALYTIKA = 53248;
      private static final int HAS_EITHER_DIALYTIKA = 98304;
      private static final int AFTER_CASED = 1;
      private static final int AFTER_VOWEL_WITH_ACCENT = 2;
      private static final char[] data0370 = new char[]{'Ͱ', 'Ͱ', 'Ͳ', 'Ͳ', '\u0000', '\u0000', 'Ͷ', 'Ͷ', '\u0000', '\u0000', 'ͺ', 'Ͻ', 'Ͼ', 'Ͽ', '\u0000', 'Ϳ', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '厑', '\u0000', '厕', '厗', '厙', '\u0000', '原', '\u0000', '厥', '厩', '펙', '᎑', 'Β', 'Γ', 'Δ', '᎕', 'Ζ', '᎗', 'Θ', '᎙', 'Κ', 'Λ', 'Μ', 'Ν', 'Ξ', '\u139f', 'Π', 'Ρ', '\u0000', 'Σ', 'Τ', 'Ꭵ', 'Φ', 'Χ', 'Ψ', 'Ꭹ', '鎙', '鎥', '厑', '厕', '厗', '厙', '펥', '᎑', 'Β', 'Γ', 'Δ', '᎕', 'Ζ', '᎗', 'Θ', '᎙', 'Κ', 'Λ', 'Μ', 'Ν', 'Ξ', '\u139f', 'Π', 'Ρ', 'Σ', 'Σ', 'Τ', 'Ꭵ', 'Φ', 'Χ', 'Ψ', 'Ꭹ', '鎙', '鎥', '原', '厥', '厩', 'Ϗ', 'Β', 'Θ', 'ϒ', '䏒', '菒', 'Φ', 'Π', 'Ϗ', 'Ϙ', 'Ϙ', 'Ϛ', 'Ϛ', 'Ϝ', 'Ϝ', 'Ϟ', 'Ϟ', 'Ϡ', 'Ϡ', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', 'Κ', 'Ρ', 'Ϲ', 'Ϳ', 'ϴ', '᎕', '\u0000', 'Ϸ', 'Ϸ', 'Ϲ', 'Ϻ', 'Ϻ', 'ϼ', 'Ͻ', 'Ͼ', 'Ͽ'};
      private static final char[] data1F00 = new char[]{'᎑', '᎑', '厑', '厑', '厑', '厑', '厑', '厑', '᎑', '᎑', '厑', '厑', '厑', '厑', '厑', '厑', '᎕', '᎕', '厕', '厕', '厕', '厕', '\u0000', '\u0000', '᎕', '᎕', '厕', '厕', '厕', '厕', '\u0000', '\u0000', '᎗', '᎗', '厗', '厗', '厗', '厗', '厗', '厗', '᎗', '᎗', '厗', '厗', '厗', '厗', '厗', '厗', '᎙', '᎙', '厙', '厙', '厙', '厙', '厙', '厙', '᎙', '᎙', '厙', '厙', '厙', '厙', '厙', '厙', '\u139f', '\u139f', '原', '原', '原', '原', '\u0000', '\u0000', '\u139f', '\u139f', '原', '原', '原', '原', '\u0000', '\u0000', 'Ꭵ', 'Ꭵ', '厥', '厥', '厥', '厥', '厥', '厥', '\u0000', 'Ꭵ', '\u0000', '厥', '\u0000', '厥', '\u0000', '厥', 'Ꭹ', 'Ꭹ', '厩', '厩', '厩', '厩', '厩', '厩', 'Ꭹ', 'Ꭹ', '厩', '厩', '厩', '厩', '厩', '厩', '厑', '厑', '厕', '厕', '厗', '厗', '厙', '厙', '原', '原', '厥', '厥', '厩', '厩', '\u0000', '\u0000', '㎑', '㎑', '玑', '玑', '玑', '玑', '玑', '玑', '㎑', '㎑', '玑', '玑', '玑', '玑', '玑', '玑', '㎗', '㎗', '玗', '玗', '玗', '玗', '玗', '玗', '㎗', '㎗', '玗', '玗', '玗', '玗', '玗', '玗', '㎩', '㎩', '玩', '玩', '玩', '玩', '玩', '玩', '㎩', '㎩', '玩', '玩', '玩', '玩', '玩', '玩', '᎑', '᎑', '玑', '㎑', '玑', '\u0000', '厑', '玑', '᎑', '᎑', '厑', '厑', '㎑', '\u0000', '᎙', '\u0000', '\u0000', '\u0000', '玗', '㎗', '玗', '\u0000', '厗', '玗', '厕', '厕', '厗', '厗', '㎗', '\u0000', '\u0000', '\u0000', '᎙', '᎙', '펙', '펙', '\u0000', '\u0000', '厙', '펙', '᎙', '᎙', '厙', '厙', '\u0000', '\u0000', '\u0000', '\u0000', 'Ꭵ', 'Ꭵ', '펥', '펥', 'Ρ', 'Ρ', '厥', '펥', 'Ꭵ', 'Ꭵ', '厥', '厥', 'Ρ', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '玩', '㎩', '玩', '\u0000', '厩', '玩', '原', '原', '厩', '厩', '㎩', '\u0000', '\u0000', '\u0000'};
      private static final char data2126 = 'Ꭹ';

      private static final int getLetterData(int c) {
         if (c >= 880 && 8486 >= c && (1023 >= c || c >= 7936)) {
            if (c <= 1023) {
               return data0370[c - 880];
            } else if (c <= 8191) {
               return data1F00[c - 7936];
            } else {
               return c == 8486 ? 5033 : 0;
            }
         } else {
            return 0;
         }
      }

      private static final int getDiacriticData(int c) {
         switch (c) {
            case 768:
            case 769:
            case 770:
            case 771:
            case 785:
            case 834:
               return 16384;
            case 772:
            case 774:
            case 787:
            case 788:
            case 835:
               return 131072;
            case 776:
               return 65536;
            case 836:
               return 81920;
            case 837:
               return 8192;
            default:
               return 0;
         }
      }

      private static boolean isFollowedByCasedLetter(CharSequence s, int i) {
         while(true) {
            if (i < s.length()) {
               int c = Character.codePointAt(s, i);
               int type = UCaseProps.INSTANCE.getTypeOrIgnorable(c);
               if ((type & 4) != 0) {
                  continue;
               }

               if (type != 0) {
                  return true;
               }

               return false;
            }

            return false;
         }
      }

      private static Appendable toUpper(int options, CharSequence src, Appendable dest, Edits edits) throws IOException {
         int state = 0;

         int nextState;
         for(int i = 0; i < src.length(); state = nextState) {
            int c = Character.codePointAt(src, i);
            int nextIndex = i + Character.charCount(c);
            nextState = 0;
            int type = UCaseProps.INSTANCE.getTypeOrIgnorable(c);
            if ((type & 4) != 0) {
               nextState |= state & 1;
            } else if (type != 0) {
               nextState |= 1;
            }

            int data = getLetterData(c);
            if (data <= 0) {
               c = UCaseProps.INSTANCE.toFullUpper(c, (UCaseProps.ContextIterator)null, dest, 4);
               CaseMapImpl.appendResult(c, dest, nextIndex - i, options, edits);
            } else {
               int upper = data & 1023;
               if ((data & 4096) != 0 && (state & 2) != 0 && (upper == 921 || upper == 933)) {
                  data |= 32768;
               }

               int numYpogegrammeni = 0;
               if ((data & 8192) != 0) {
                  numYpogegrammeni = 1;
               }

               for(; nextIndex < src.length(); ++nextIndex) {
                  int diacriticData = getDiacriticData(src.charAt(nextIndex));
                  if (diacriticData == 0) {
                     break;
                  }

                  data |= diacriticData;
                  if ((diacriticData & 8192) != 0) {
                     ++numYpogegrammeni;
                  }
               }

               if ((data & '퀀') == 20480) {
                  nextState |= 2;
               }

               boolean addTonos = false;
               if (upper == 919 && (data & 16384) != 0 && numYpogegrammeni == 0 && (state & 1) == 0 && !isFollowedByCasedLetter(src, nextIndex)) {
                  if (i == nextIndex) {
                     upper = 905;
                  } else {
                     addTonos = true;
                  }
               } else if ((data & '耀') != 0) {
                  if (upper == 921) {
                     upper = 938;
                     data &= -98305;
                  } else if (upper == 933) {
                     upper = 939;
                     data &= -98305;
                  }
               }

               boolean change;
               if (edits == null) {
                  change = true;
               } else {
                  change = src.charAt(i) != upper || numYpogegrammeni > 0;
                  int i2 = i + 1;
                  if ((data & 98304) != 0) {
                     change |= i2 >= nextIndex || src.charAt(i2) != 776;
                     ++i2;
                  }

                  if (addTonos) {
                     change |= i2 >= nextIndex || src.charAt(i2) != 769;
                     ++i2;
                  }

                  int oldLength = nextIndex - i;
                  int newLength = i2 - i + numYpogegrammeni;
                  change |= oldLength != newLength;
                  if (change) {
                     if (edits != null) {
                        edits.addReplace(oldLength, newLength);
                     }
                  } else {
                     if (edits != null) {
                        edits.addUnchanged(oldLength);
                     }

                     change = (options & 16384) == 0;
                  }
               }

               if (change) {
                  dest.append((char)upper);
                  if ((data & 98304) != 0) {
                     dest.append('̈');
                  }

                  if (addTonos) {
                     dest.append('́');
                  }

                  while(numYpogegrammeni > 0) {
                     dest.append('Ι');
                     --numYpogegrammeni;
                  }
               }
            }

            i = nextIndex;
         }

         return dest;
      }
   }

   public static final class StringContextIterator implements UCaseProps.ContextIterator {
      protected CharSequence s;
      protected int index;
      protected int limit;
      protected int cpStart;
      protected int cpLimit;
      protected int dir;

      public StringContextIterator(CharSequence src) {
         this.s = src;
         this.limit = src.length();
         this.cpStart = this.cpLimit = this.index = 0;
         this.dir = 0;
      }

      public void setLimit(int lim) {
         if (0 <= lim && lim <= this.s.length()) {
            this.limit = lim;
         } else {
            this.limit = this.s.length();
         }

      }

      public void moveToLimit() {
         this.cpStart = this.cpLimit = this.limit;
      }

      public int nextCaseMapCP() {
         this.cpStart = this.cpLimit;
         if (this.cpLimit < this.limit) {
            int c = Character.codePointAt(this.s, this.cpLimit);
            this.cpLimit += Character.charCount(c);
            return c;
         } else {
            return -1;
         }
      }

      public int getCPStart() {
         return this.cpStart;
      }

      public int getCPLimit() {
         return this.cpLimit;
      }

      public int getCPLength() {
         return this.cpLimit - this.cpStart;
      }

      public void reset(int direction) {
         if (direction > 0) {
            this.dir = 1;
            this.index = this.cpLimit;
         } else if (direction < 0) {
            this.dir = -1;
            this.index = this.cpStart;
         } else {
            this.dir = 0;
            this.index = 0;
         }

      }

      public int next() {
         int c;
         if (this.dir > 0 && this.index < this.s.length()) {
            c = Character.codePointAt(this.s, this.index);
            this.index += Character.charCount(c);
            return c;
         } else if (this.dir < 0 && this.index > 0) {
            c = Character.codePointBefore(this.s, this.index);
            this.index -= Character.charCount(c);
            return c;
         } else {
            return -1;
         }
      }
   }
}
