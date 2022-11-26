package org.python.icu.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.Locale;
import org.python.icu.text.UTF16;
import org.python.icu.text.UnicodeSet;
import org.python.icu.util.ICUUncheckedIOException;
import org.python.icu.util.ULocale;

public final class UCaseProps {
   private static final byte[] flagsOffset = new byte[]{0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7, 4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8};
   public static final int MAX_STRING_LENGTH = 31;
   public static final int LOC_ROOT = 1;
   private static final int LOC_TURKISH = 2;
   private static final int LOC_LITHUANIAN = 3;
   static final int LOC_GREEK = 4;
   public static final int LOC_DUTCH = 5;
   private static final String iDot = "i̇";
   private static final String jDot = "j̇";
   private static final String iOgonekDot = "į̇";
   private static final String iDotGrave = "i̇̀";
   private static final String iDotAcute = "i̇́";
   private static final String iDotTilde = "i̇̃";
   private static final int FOLD_CASE_OPTIONS_MASK = 255;
   public static final StringBuilder dummyStringBuilder = new StringBuilder();
   private int[] indexes;
   private String exceptions;
   private char[] unfold;
   private Trie2_16 trie;
   private static final String DATA_NAME = "ucase";
   private static final String DATA_TYPE = "icu";
   private static final String DATA_FILE_NAME = "ucase.icu";
   private static final int FMT = 1665225541;
   private static final int IX_TRIE_SIZE = 2;
   private static final int IX_EXC_LENGTH = 3;
   private static final int IX_UNFOLD_LENGTH = 4;
   private static final int IX_TOP = 16;
   public static final int TYPE_MASK = 3;
   public static final int NONE = 0;
   public static final int LOWER = 1;
   public static final int UPPER = 2;
   public static final int TITLE = 3;
   static final int IGNORABLE = 4;
   private static final int SENSITIVE = 8;
   private static final int EXCEPTION = 16;
   private static final int DOT_MASK = 96;
   private static final int SOFT_DOTTED = 32;
   private static final int ABOVE = 64;
   private static final int OTHER_ACCENT = 96;
   private static final int DELTA_SHIFT = 7;
   private static final int EXC_SHIFT = 5;
   private static final int EXC_LOWER = 0;
   private static final int EXC_FOLD = 1;
   private static final int EXC_UPPER = 2;
   private static final int EXC_TITLE = 3;
   private static final int EXC_CLOSURE = 6;
   private static final int EXC_FULL_MAPPINGS = 7;
   private static final int EXC_DOUBLE_SLOTS = 256;
   private static final int EXC_DOT_SHIFT = 7;
   private static final int EXC_CONDITIONAL_SPECIAL = 16384;
   private static final int EXC_CONDITIONAL_FOLD = 32768;
   private static final int FULL_LOWER = 15;
   private static final int CLOSURE_MAX_LENGTH = 15;
   private static final int UNFOLD_ROWS = 0;
   private static final int UNFOLD_ROW_WIDTH = 1;
   private static final int UNFOLD_STRING_WIDTH = 2;
   public static final UCaseProps INSTANCE;

   private UCaseProps() throws IOException {
      ByteBuffer bytes = ICUBinary.getRequiredData("ucase.icu");
      this.readData(bytes);
   }

   private final void readData(ByteBuffer bytes) throws IOException {
      ICUBinary.readHeader(bytes, 1665225541, new IsAcceptable());
      int count = bytes.getInt();
      if (count < 16) {
         throw new IOException("indexes[0] too small in ucase.icu");
      } else {
         this.indexes = new int[count];
         this.indexes[0] = count;

         int expectedTrieLength;
         for(expectedTrieLength = 1; expectedTrieLength < count; ++expectedTrieLength) {
            this.indexes[expectedTrieLength] = bytes.getInt();
         }

         this.trie = Trie2_16.createFromSerialized(bytes);
         expectedTrieLength = this.indexes[2];
         int trieLength = this.trie.getSerializedLength();
         if (trieLength > expectedTrieLength) {
            throw new IOException("ucase.icu: not enough bytes for the trie");
         } else {
            ICUBinary.skipBytes(bytes, expectedTrieLength - trieLength);
            count = this.indexes[3];
            if (count > 0) {
               this.exceptions = ICUBinary.getString(bytes, count, 0);
            }

            count = this.indexes[4];
            if (count > 0) {
               this.unfold = ICUBinary.getChars(bytes, count, 0);
            }

         }
      }
   }

   public final void addPropertyStarts(UnicodeSet set) {
      Iterator trieIterator = this.trie.iterator();

      Trie2.Range range;
      while(trieIterator.hasNext() && !(range = (Trie2.Range)trieIterator.next()).leadSurrogate) {
         set.add(range.startCodePoint);
      }

   }

   private static final int getExceptionsOffset(int props) {
      return props >> 5;
   }

   private static final boolean propsHasException(int props) {
      return (props & 16) != 0;
   }

   private static final boolean hasSlot(int flags, int index) {
      return (flags & 1 << index) != 0;
   }

   private static final byte slotOffset(int flags, int index) {
      return flagsOffset[flags & (1 << index) - 1];
   }

   private final long getSlotValueAndOffset(int excWord, int index, int excOffset) {
      long value;
      if ((excWord & 256) == 0) {
         excOffset += slotOffset(excWord, index);
         value = (long)this.exceptions.charAt(excOffset);
      } else {
         excOffset += 2 * slotOffset(excWord, index);
         value = (long)this.exceptions.charAt(excOffset++);
         value = value << 16 | (long)this.exceptions.charAt(excOffset);
      }

      return value | (long)excOffset << 32;
   }

   private final int getSlotValue(int excWord, int index, int excOffset) {
      int value;
      if ((excWord & 256) == 0) {
         excOffset += slotOffset(excWord, index);
         value = this.exceptions.charAt(excOffset);
      } else {
         excOffset += 2 * slotOffset(excWord, index);
         int value = this.exceptions.charAt(excOffset++);
         value = value << 16 | this.exceptions.charAt(excOffset);
      }

      return value;
   }

   public final int tolower(int c) {
      int props = this.trie.get(c);
      if (!propsHasException(props)) {
         if (getTypeFromProps(props) >= 2) {
            c += getDelta(props);
         }
      } else {
         int excOffset = getExceptionsOffset(props);
         int excWord = this.exceptions.charAt(excOffset++);
         if (hasSlot(excWord, 0)) {
            c = this.getSlotValue(excWord, 0, excOffset);
         }
      }

      return c;
   }

   public final int toupper(int c) {
      int props = this.trie.get(c);
      if (!propsHasException(props)) {
         if (getTypeFromProps(props) == 1) {
            c += getDelta(props);
         }
      } else {
         int excOffset = getExceptionsOffset(props);
         int excWord = this.exceptions.charAt(excOffset++);
         if (hasSlot(excWord, 2)) {
            c = this.getSlotValue(excWord, 2, excOffset);
         }
      }

      return c;
   }

   public final int totitle(int c) {
      int props = this.trie.get(c);
      if (!propsHasException(props)) {
         if (getTypeFromProps(props) == 1) {
            c += getDelta(props);
         }
      } else {
         int excOffset = getExceptionsOffset(props);
         int excWord = this.exceptions.charAt(excOffset++);
         byte index;
         if (hasSlot(excWord, 3)) {
            index = 3;
         } else {
            if (!hasSlot(excWord, 2)) {
               return c;
            }

            index = 2;
         }

         c = this.getSlotValue(excWord, index, excOffset);
      }

      return c;
   }

   public final void addCaseClosure(int c, UnicodeSet set) {
      switch (c) {
         case 73:
            set.add(105);
            return;
         case 105:
            set.add(73);
            return;
         case 304:
            set.add((CharSequence)"i̇");
            return;
         case 305:
            return;
         default:
            int props = this.trie.get(c);
            int excOffset0;
            if (!propsHasException(props)) {
               if (getTypeFromProps(props) != 0) {
                  excOffset0 = getDelta(props);
                  if (excOffset0 != 0) {
                     set.add(c + excOffset0);
                  }
               }
            } else {
               int excOffset = getExceptionsOffset(props);
               int excWord = this.exceptions.charAt(excOffset++);
               excOffset0 = excOffset;

               int index;
               for(index = 0; index <= 3; ++index) {
                  if (hasSlot(excWord, index)) {
                     c = this.getSlotValue(excWord, index, excOffset0);
                     set.add(c);
                  }
               }

               long value;
               int closureLength;
               int closureOffset;
               if (hasSlot(excWord, 6)) {
                  value = this.getSlotValueAndOffset(excWord, 6, excOffset0);
                  closureLength = (int)value & 15;
                  closureOffset = (int)(value >> 32) + 1;
               } else {
                  closureLength = 0;
                  closureOffset = 0;
               }

               if (hasSlot(excWord, 7)) {
                  value = this.getSlotValueAndOffset(excWord, 7, excOffset0);
                  int fullLength = (int)value;
                  excOffset = (int)(value >> 32) + 1;
                  fullLength &= 65535;
                  excOffset += fullLength & 15;
                  fullLength >>= 4;
                  int length = fullLength & 15;
                  if (length != 0) {
                     set.add((CharSequence)this.exceptions.substring(excOffset, excOffset + length));
                     excOffset += length;
                  }

                  fullLength >>= 4;
                  excOffset += fullLength & 15;
                  fullLength >>= 4;
                  excOffset += fullLength;
                  closureOffset = excOffset;
               }

               int limit = closureOffset + closureLength;

               for(index = closureOffset; index < limit; index += UTF16.getCharCount(c)) {
                  c = this.exceptions.codePointAt(index);
                  set.add(c);
               }
            }

      }
   }

   private final int strcmpMax(String s, int unfoldOffset, int max) {
      int length = s.length();
      max -= length;
      int i1 = 0;

      do {
         int c1 = s.charAt(i1++);
         int c2 = this.unfold[unfoldOffset++];
         if (c2 == 0) {
            return 1;
         }

         c1 -= c2;
         if (c1 != 0) {
            return c1;
         }

         --length;
      } while(length > 0);

      if (max != 0 && this.unfold[unfoldOffset] != 0) {
         return -max;
      } else {
         return 0;
      }
   }

   public final boolean addStringCaseClosure(String s, UnicodeSet set) {
      if (this.unfold != null && s != null) {
         int length = s.length();
         if (length <= 1) {
            return false;
         } else {
            int unfoldRows = this.unfold[0];
            int unfoldRowWidth = this.unfold[1];
            int unfoldStringWidth = this.unfold[2];
            if (length > unfoldStringWidth) {
               return false;
            } else {
               int start = 0;
               int limit = unfoldRows;

               while(start < limit) {
                  int i = (start + limit) / 2;
                  int unfoldOffset = (i + 1) * unfoldRowWidth;
                  int result = this.strcmpMax(s, unfoldOffset, unfoldStringWidth);
                  if (result == 0) {
                     int c;
                     for(i = unfoldStringWidth; i < unfoldRowWidth && this.unfold[unfoldOffset + i] != 0; i += UTF16.getCharCount(c)) {
                        c = UTF16.charAt(this.unfold, unfoldOffset, this.unfold.length, i);
                        set.add(c);
                        this.addCaseClosure(c, set);
                     }

                     return true;
                  }

                  if (result < 0) {
                     limit = i;
                  } else {
                     start = i + 1;
                  }
               }

               return false;
            }
         }
      } else {
         return false;
      }
   }

   public final int getType(int c) {
      return getTypeFromProps(this.trie.get(c));
   }

   public final int getTypeOrIgnorable(int c) {
      return getTypeAndIgnorableFromProps(this.trie.get(c));
   }

   public final int getDotType(int c) {
      int props = this.trie.get(c);
      return !propsHasException(props) ? props & 96 : this.exceptions.charAt(getExceptionsOffset(props)) >> 7 & 96;
   }

   public final boolean isSoftDotted(int c) {
      return this.getDotType(c) == 32;
   }

   public final boolean isCaseSensitive(int c) {
      return (this.trie.get(c) & 8) != 0;
   }

   public static final int getCaseLocale(Locale locale) {
      return getCaseLocale(locale.getLanguage());
   }

   public static final int getCaseLocale(ULocale locale) {
      return getCaseLocale(locale.getLanguage());
   }

   private static final int getCaseLocale(String language) {
      if (language.length() == 2) {
         if (language.equals("en") || language.charAt(0) > 't') {
            return 1;
         }

         if (language.equals("tr") || language.equals("az")) {
            return 2;
         }

         if (language.equals("el")) {
            return 4;
         }

         if (language.equals("lt")) {
            return 3;
         }

         if (language.equals("nl")) {
            return 5;
         }
      } else if (language.length() == 3) {
         if (language.equals("tur") || language.equals("aze")) {
            return 2;
         }

         if (language.equals("ell")) {
            return 4;
         }

         if (language.equals("lit")) {
            return 3;
         }

         if (language.equals("nld")) {
            return 5;
         }
      }

      return 1;
   }

   private final boolean isFollowedByCasedLetter(ContextIterator iter, int dir) {
      if (iter == null) {
         return false;
      } else {
         iter.reset(dir);

         int type;
         do {
            int c;
            if ((c = iter.next()) < 0) {
               return false;
            }

            type = this.getTypeOrIgnorable(c);
         } while((type & 4) != 0);

         if (type != 0) {
            return true;
         } else {
            return false;
         }
      }
   }

   private final boolean isPrecededBySoftDotted(ContextIterator iter) {
      if (iter == null) {
         return false;
      } else {
         iter.reset(-1);

         int dotType;
         do {
            int c;
            if ((c = iter.next()) < 0) {
               return false;
            }

            dotType = this.getDotType(c);
            if (dotType == 32) {
               return true;
            }
         } while(dotType == 96);

         return false;
      }
   }

   private final boolean isPrecededBy_I(ContextIterator iter) {
      if (iter == null) {
         return false;
      } else {
         iter.reset(-1);

         int dotType;
         do {
            int c;
            if ((c = iter.next()) < 0) {
               return false;
            }

            if (c == 73) {
               return true;
            }

            dotType = this.getDotType(c);
         } while(dotType == 96);

         return false;
      }
   }

   private final boolean isFollowedByMoreAbove(ContextIterator iter) {
      if (iter == null) {
         return false;
      } else {
         iter.reset(1);

         int dotType;
         do {
            int c;
            if ((c = iter.next()) < 0) {
               return false;
            }

            dotType = this.getDotType(c);
            if (dotType == 64) {
               return true;
            }
         } while(dotType == 96);

         return false;
      }
   }

   private final boolean isFollowedByDotAbove(ContextIterator iter) {
      if (iter == null) {
         return false;
      } else {
         iter.reset(1);

         int dotType;
         do {
            int c;
            if ((c = iter.next()) < 0) {
               return false;
            }

            if (c == 775) {
               return true;
            }

            dotType = this.getDotType(c);
         } while(dotType == 96);

         return false;
      }
   }

   public final int toFullLower(int c, ContextIterator iter, Appendable out, int caseLocale) {
      int result = c;
      int props = this.trie.get(c);
      if (!propsHasException(props)) {
         if (getTypeFromProps(props) >= 2) {
            result = c + getDelta(props);
         }
      } else {
         int excOffset = getExceptionsOffset(props);
         int excWord = this.exceptions.charAt(excOffset++);
         if ((excWord & 16384) != 0) {
            if (caseLocale == 3 && ((c == 73 || c == 74 || c == 302) && this.isFollowedByMoreAbove(iter) || c == 204 || c == 205 || c == 296)) {
               try {
                  switch (c) {
                     case 73:
                        out.append("i̇");
                        return 2;
                     case 74:
                        out.append("j̇");
                        return 2;
                     case 204:
                        out.append("i̇̀");
                        return 3;
                     case 205:
                        out.append("i̇́");
                        return 3;
                     case 296:
                        out.append("i̇̃");
                        return 3;
                     case 302:
                        out.append("į̇");
                        return 2;
                     default:
                        return 0;
                  }
               } catch (IOException var15) {
                  throw new ICUUncheckedIOException(var15);
               }
            }

            if (caseLocale == 2 && c == 304) {
               return 105;
            }

            if (caseLocale == 2 && c == 775 && this.isPrecededBy_I(iter)) {
               return 0;
            }

            if (caseLocale == 2 && c == 73 && !this.isFollowedByDotAbove(iter)) {
               return 305;
            }

            if (c == 304) {
               try {
                  out.append("i̇");
                  return 2;
               } catch (IOException var16) {
                  throw new ICUUncheckedIOException(var16);
               }
            }

            if (c == 931 && !this.isFollowedByCasedLetter(iter, 1) && this.isFollowedByCasedLetter(iter, -1)) {
               return 962;
            }
         } else if (hasSlot(excWord, 7)) {
            long value = this.getSlotValueAndOffset(excWord, 7, excOffset);
            int full = (int)value & 15;
            if (full != 0) {
               excOffset = (int)(value >> 32) + 1;

               try {
                  out.append(this.exceptions, excOffset, excOffset + full);
                  return full;
               } catch (IOException var17) {
                  throw new ICUUncheckedIOException(var17);
               }
            }
         }

         if (hasSlot(excWord, 0)) {
            result = this.getSlotValue(excWord, 0, excOffset);
         }
      }

      return result == c ? ~result : result;
   }

   private final int toUpperOrTitle(int c, ContextIterator iter, Appendable out, int loc, boolean upperNotTitle) {
      int result = c;
      int props = this.trie.get(c);
      if (!propsHasException(props)) {
         if (getTypeFromProps(props) == 1) {
            result = c + getDelta(props);
         }
      } else {
         int excOffset = getExceptionsOffset(props);
         int excWord = this.exceptions.charAt(excOffset++);
         if ((excWord & 16384) != 0) {
            if (loc == 2 && c == 105) {
               return 304;
            }

            if (loc == 3 && c == 775 && this.isPrecededBySoftDotted(iter)) {
               return 0;
            }
         } else if (hasSlot(excWord, 7)) {
            long value = this.getSlotValueAndOffset(excWord, 7, excOffset);
            int full = (int)value & '\uffff';
            excOffset = (int)(value >> 32) + 1;
            excOffset += full & 15;
            full >>= 4;
            excOffset += full & 15;
            full >>= 4;
            if (upperNotTitle) {
               full &= 15;
            } else {
               excOffset += full & 15;
               full = full >> 4 & 15;
            }

            if (full != 0) {
               try {
                  out.append(this.exceptions, excOffset, excOffset + full);
                  return full;
               } catch (IOException var16) {
                  throw new ICUUncheckedIOException(var16);
               }
            }
         }

         byte index;
         if (!upperNotTitle && hasSlot(excWord, 3)) {
            index = 3;
         } else {
            if (!hasSlot(excWord, 2)) {
               return ~c;
            }

            index = 2;
         }

         result = this.getSlotValue(excWord, index, excOffset);
      }

      return result == c ? ~result : result;
   }

   public final int toFullUpper(int c, ContextIterator iter, Appendable out, int caseLocale) {
      return this.toUpperOrTitle(c, iter, out, caseLocale, true);
   }

   public final int toFullTitle(int c, ContextIterator iter, Appendable out, int caseLocale) {
      return this.toUpperOrTitle(c, iter, out, caseLocale, false);
   }

   public final int fold(int c, int options) {
      int props = this.trie.get(c);
      if (!propsHasException(props)) {
         if (getTypeFromProps(props) >= 2) {
            c += getDelta(props);
         }
      } else {
         int excOffset = getExceptionsOffset(props);
         int excWord = this.exceptions.charAt(excOffset++);
         if ((excWord & '耀') != 0) {
            if ((options & 255) == 0) {
               if (c == 73) {
                  return 105;
               }

               if (c == 304) {
                  return c;
               }
            } else {
               if (c == 73) {
                  return 305;
               }

               if (c == 304) {
                  return 105;
               }
            }
         }

         byte index;
         if (hasSlot(excWord, 1)) {
            index = 1;
         } else {
            if (!hasSlot(excWord, 0)) {
               return c;
            }

            index = 0;
         }

         c = this.getSlotValue(excWord, index, excOffset);
      }

      return c;
   }

   public final int toFullFolding(int c, Appendable out, int options) {
      int result = c;
      int props = this.trie.get(c);
      if (!propsHasException(props)) {
         if (getTypeFromProps(props) >= 2) {
            result = c + getDelta(props);
         }
      } else {
         int excOffset = getExceptionsOffset(props);
         int excWord = this.exceptions.charAt(excOffset++);
         if ((excWord & '耀') != 0) {
            if ((options & 255) == 0) {
               if (c == 73) {
                  return 105;
               }

               if (c == 304) {
                  try {
                     out.append("i̇");
                     return 2;
                  } catch (IOException var15) {
                     throw new ICUUncheckedIOException(var15);
                  }
               }
            } else {
               if (c == 73) {
                  return 305;
               }

               if (c == 304) {
                  return 105;
               }
            }
         } else if (hasSlot(excWord, 7)) {
            long value = this.getSlotValueAndOffset(excWord, 7, excOffset);
            int full = (int)value & '\uffff';
            excOffset = (int)(value >> 32) + 1;
            excOffset += full & 15;
            full = full >> 4 & 15;
            if (full != 0) {
               try {
                  out.append(this.exceptions, excOffset, excOffset + full);
                  return full;
               } catch (IOException var16) {
                  throw new ICUUncheckedIOException(var16);
               }
            }
         }

         byte index;
         if (hasSlot(excWord, 1)) {
            index = 1;
         } else {
            if (!hasSlot(excWord, 0)) {
               return ~c;
            }

            index = 0;
         }

         result = this.getSlotValue(excWord, index, excOffset);
      }

      return result == c ? ~result : result;
   }

   public final boolean hasBinaryProperty(int c, int which) {
      switch (which) {
         case 22:
            return 1 == this.getType(c);
         case 23:
         case 24:
         case 25:
         case 26:
         case 28:
         case 29:
         case 31:
         case 32:
         case 33:
         case 35:
         case 36:
         case 37:
         case 38:
         case 39:
         case 40:
         case 41:
         case 42:
         case 43:
         case 44:
         case 45:
         case 46:
         case 47:
         case 48:
         case 54:
         default:
            return false;
         case 27:
            return this.isSoftDotted(c);
         case 30:
            return 2 == this.getType(c);
         case 34:
            return this.isCaseSensitive(c);
         case 49:
            return 0 != this.getType(c);
         case 50:
            return this.getTypeOrIgnorable(c) >> 2 != 0;
         case 51:
            dummyStringBuilder.setLength(0);
            return this.toFullLower(c, (ContextIterator)null, dummyStringBuilder, 1) >= 0;
         case 52:
            dummyStringBuilder.setLength(0);
            return this.toFullUpper(c, (ContextIterator)null, dummyStringBuilder, 1) >= 0;
         case 53:
            dummyStringBuilder.setLength(0);
            return this.toFullTitle(c, (ContextIterator)null, dummyStringBuilder, 1) >= 0;
         case 55:
            dummyStringBuilder.setLength(0);
            return this.toFullLower(c, (ContextIterator)null, dummyStringBuilder, 1) >= 0 || this.toFullUpper(c, (ContextIterator)null, dummyStringBuilder, 1) >= 0 || this.toFullTitle(c, (ContextIterator)null, dummyStringBuilder, 1) >= 0;
      }
   }

   private static final int getTypeFromProps(int props) {
      return props & 3;
   }

   private static final int getTypeAndIgnorableFromProps(int props) {
      return props & 7;
   }

   private static final int getDelta(int props) {
      return (short)props >> 7;
   }

   static {
      try {
         INSTANCE = new UCaseProps();
      } catch (IOException var1) {
         throw new ICUUncheckedIOException(var1);
      }
   }

   public interface ContextIterator {
      void reset(int var1);

      int next();
   }

   private static final class IsAcceptable implements ICUBinary.Authenticate {
      private IsAcceptable() {
      }

      public boolean isDataVersionAcceptable(byte[] version) {
         return version[0] == 3;
      }

      // $FF: synthetic method
      IsAcceptable(Object x0) {
         this();
      }
   }
}
