package org.python.icu.impl.number;

import org.python.icu.text.DecimalFormatSymbols;
import org.python.icu.text.NumberFormat;

public class AffixPatternUtils {
   private static final int STATE_BASE = 0;
   private static final int STATE_FIRST_QUOTE = 1;
   private static final int STATE_INSIDE_QUOTE = 2;
   private static final int STATE_AFTER_QUOTE = 3;
   private static final int STATE_FIRST_CURR = 4;
   private static final int STATE_SECOND_CURR = 5;
   private static final int STATE_THIRD_CURR = 6;
   private static final int STATE_OVERFLOW_CURR = 7;
   private static final int TYPE_CODEPOINT = 0;
   public static final int TYPE_MINUS_SIGN = -1;
   public static final int TYPE_PLUS_SIGN = -2;
   public static final int TYPE_PERCENT = -3;
   public static final int TYPE_PERMILLE = -4;
   public static final int TYPE_CURRENCY_SINGLE = -5;
   public static final int TYPE_CURRENCY_DOUBLE = -6;
   public static final int TYPE_CURRENCY_TRIPLE = -7;
   public static final int TYPE_CURRENCY_OVERFLOW = -15;

   public static int unescapedLength(CharSequence patternString) {
      if (patternString == null) {
         return 0;
      } else {
         int state = 0;
         int offset = 0;

         int length;
         int cp;
         for(length = 0; offset < patternString.length(); offset += Character.charCount(cp)) {
            cp = Character.codePointAt(patternString, offset);
            switch (state) {
               case 0:
                  if (cp == 39) {
                     state = 1;
                  } else {
                     ++length;
                  }
                  break;
               case 1:
                  if (cp == 39) {
                     ++length;
                     state = 0;
                  } else {
                     ++length;
                     state = 2;
                  }
                  break;
               case 2:
                  if (cp == 39) {
                     state = 3;
                  } else {
                     ++length;
                  }
                  break;
               case 3:
                  if (cp == 39) {
                     ++length;
                     state = 2;
                  } else {
                     ++length;
                  }
                  break;
               default:
                  throw new AssertionError();
            }
         }

         switch (state) {
            case 1:
            case 2:
               throw new IllegalArgumentException("Unterminated quote: \"" + patternString + "\"");
            default:
               return length;
         }
      }
   }

   public static int escape(CharSequence input, StringBuilder output) {
      if (input == null) {
         return 0;
      } else {
         int state = 0;
         int offset = 0;

         int startLength;
         int cp;
         for(startLength = output.length(); offset < input.length(); offset += Character.charCount(cp)) {
            cp = Character.codePointAt(input, offset);
            switch (cp) {
               case 37:
               case 43:
               case 45:
               case 164:
               case 8240:
                  if (state == 0) {
                     output.append('\'');
                     output.appendCodePoint(cp);
                     state = 2;
                  } else {
                     output.appendCodePoint(cp);
                  }
                  break;
               case 39:
                  output.append("''");
                  break;
               default:
                  if (state == 2) {
                     output.append('\'');
                     output.appendCodePoint(cp);
                     state = 0;
                  } else {
                     output.appendCodePoint(cp);
                  }
            }
         }

         if (state == 2) {
            output.append('\'');
         }

         return output.length() - startLength;
      }
   }

   public static void unescape(CharSequence affixPattern, DecimalFormatSymbols symbols, String currency1, String currency2, String currency3, String minusSign, NumberStringBuilder output) {
      if (affixPattern != null && affixPattern.length() != 0) {
         if (minusSign == null) {
            minusSign = symbols.getMinusSignString();
         }

         long tag = 0L;

         while(hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            int typeOrCp = getTypeOrCp(tag);
            switch (typeOrCp) {
               case -15:
                  output.append((CharSequence)"�", (NumberFormat.Field)NumberFormat.Field.CURRENCY);
                  break;
               case -14:
               case -13:
               case -12:
               case -11:
               case -10:
               case -9:
               case -8:
               default:
                  output.appendCodePoint(typeOrCp, (NumberFormat.Field)null);
                  break;
               case -7:
                  output.append((CharSequence)currency3, (NumberFormat.Field)NumberFormat.Field.CURRENCY);
                  break;
               case -6:
                  output.append((CharSequence)currency2, (NumberFormat.Field)NumberFormat.Field.CURRENCY);
                  break;
               case -5:
                  output.append((CharSequence)currency1, (NumberFormat.Field)NumberFormat.Field.CURRENCY);
                  break;
               case -4:
                  output.append((CharSequence)symbols.getPerMillString(), (NumberFormat.Field)NumberFormat.Field.PERMILLE);
                  break;
               case -3:
                  output.append((CharSequence)symbols.getPercentString(), (NumberFormat.Field)NumberFormat.Field.PERCENT);
                  break;
               case -2:
                  output.append((CharSequence)symbols.getPlusSignString(), (NumberFormat.Field)NumberFormat.Field.SIGN);
                  break;
               case -1:
                  output.append((CharSequence)minusSign, (NumberFormat.Field)NumberFormat.Field.SIGN);
            }
         }

      }
   }

   public static boolean containsType(CharSequence affixPattern, int type) {
      if (affixPattern != null && affixPattern.length() != 0) {
         long tag = 0L;

         do {
            if (!hasNext(tag, affixPattern)) {
               return false;
            }

            tag = nextToken(tag, affixPattern);
         } while(getTypeOrCp(tag) != type);

         return true;
      } else {
         return false;
      }
   }

   public static boolean hasCurrencySymbols(CharSequence affixPattern) {
      if (affixPattern != null && affixPattern.length() != 0) {
         long tag = 0L;

         int typeOrCp;
         do {
            if (!hasNext(tag, affixPattern)) {
               return false;
            }

            tag = nextToken(tag, affixPattern);
            typeOrCp = getTypeOrCp(tag);
         } while(typeOrCp != -5 && typeOrCp != -6 && typeOrCp != -7 && typeOrCp != -15);

         return true;
      } else {
         return false;
      }
   }

   public static String replaceType(CharSequence affixPattern, int type, char replacementChar) {
      if (affixPattern != null && affixPattern.length() != 0) {
         char[] chars = affixPattern.toString().toCharArray();
         long tag = 0L;

         while(hasNext(tag, affixPattern)) {
            tag = nextToken(tag, affixPattern);
            if (getTypeOrCp(tag) == type) {
               int offset = getOffset(tag);
               chars[offset - 1] = replacementChar;
            }
         }

         return new String(chars);
      } else {
         return "";
      }
   }

   public static long nextToken(long tag, CharSequence patternString) {
      int offset = getOffset(tag);
      int state = getState(tag);

      while(offset < patternString.length()) {
         int cp = Character.codePointAt(patternString, offset);
         int count = Character.charCount(cp);
         switch (state) {
            case 0:
               switch (cp) {
                  case 37:
                     return makeTag(offset + count, -3, 0, 0);
                  case 39:
                     state = 1;
                     offset += count;
                     continue;
                  case 43:
                     return makeTag(offset + count, -2, 0, 0);
                  case 45:
                     return makeTag(offset + count, -1, 0, 0);
                  case 164:
                     state = 4;
                     offset += count;
                     continue;
                  case 8240:
                     return makeTag(offset + count, -4, 0, 0);
                  default:
                     return makeTag(offset + count, 0, 0, cp);
               }
            case 1:
               if (cp == 39) {
                  return makeTag(offset + count, 0, 0, cp);
               }

               return makeTag(offset + count, 0, 2, cp);
            case 2:
               if (cp != 39) {
                  return makeTag(offset + count, 0, 2, cp);
               }

               state = 3;
               offset += count;
               break;
            case 3:
               if (cp == 39) {
                  return makeTag(offset + count, 0, 2, cp);
               }

               state = 0;
               break;
            case 4:
               if (cp != 164) {
                  return makeTag(offset, -5, 0, 0);
               }

               state = 5;
               offset += count;
               break;
            case 5:
               if (cp != 164) {
                  return makeTag(offset, -6, 0, 0);
               }

               state = 6;
               offset += count;
               break;
            case 6:
               if (cp != 164) {
                  return makeTag(offset, -7, 0, 0);
               }

               state = 7;
               offset += count;
               break;
            case 7:
               if (cp != 164) {
                  return makeTag(offset, -15, 0, 0);
               }

               offset += count;
               break;
            default:
               throw new AssertionError();
         }
      }

      switch (state) {
         case 0:
            return -1L;
         case 1:
         case 2:
            throw new IllegalArgumentException("Unterminated quote in pattern affix: \"" + patternString + "\"");
         case 3:
            return -1L;
         case 4:
            return makeTag(offset, -5, 0, 0);
         case 5:
            return makeTag(offset, -6, 0, 0);
         case 6:
            return makeTag(offset, -7, 0, 0);
         case 7:
            return makeTag(offset, -15, 0, 0);
         default:
            throw new AssertionError();
      }
   }

   public static boolean hasNext(long tag, CharSequence string) {
      assert tag >= 0L;

      int state = getState(tag);
      int offset = getOffset(tag);
      if (state == 2 && offset == string.length() - 1 && string.charAt(offset) == '\'') {
         return false;
      } else if (state != 0) {
         return true;
      } else {
         return offset < string.length();
      }
   }

   public static int getTypeOrCp(long tag) {
      assert tag >= 0L;

      int type = getType(tag);
      return type == 0 ? getCodePoint(tag) : -type;
   }

   private static long makeTag(int offset, int type, int state, int cp) {
      long tag = 0L;
      tag |= (long)offset;
      tag |= -((long)type) << 32;
      tag |= (long)state << 36;
      tag |= (long)cp << 40;

      assert tag >= 0L;

      return tag;
   }

   static int getOffset(long tag) {
      return (int)(tag & -1L);
   }

   static int getType(long tag) {
      return (int)(tag >>> 32 & 15L);
   }

   static int getState(long tag) {
      return (int)(tag >>> 36 & 15L);
   }

   static int getCodePoint(long tag) {
      return (int)(tag >>> 40);
   }
}
