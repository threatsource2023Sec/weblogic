package org.python.icu.text;

import java.text.AttributedCharacterIterator;
import java.util.Map;
import org.python.icu.impl.number.Parse;
import org.python.icu.lang.UCharacter;
import org.python.icu.util.ULocale;

public final class ScientificNumberFormatter {
   private final String preExponent;
   private final DecimalFormat fmt;
   private final Style style;
   private static final Style SUPER_SCRIPT = new SuperscriptStyle();

   public static ScientificNumberFormatter getSuperscriptInstance(ULocale locale) {
      return getInstanceForLocale(locale, SUPER_SCRIPT);
   }

   public static ScientificNumberFormatter getSuperscriptInstance(DecimalFormat df) {
      return getInstance(df, SUPER_SCRIPT);
   }

   public static ScientificNumberFormatter getMarkupInstance(ULocale locale, String beginMarkup, String endMarkup) {
      return getInstanceForLocale(locale, new MarkupStyle(beginMarkup, endMarkup));
   }

   public static ScientificNumberFormatter getMarkupInstance(DecimalFormat df, String beginMarkup, String endMarkup) {
      return getInstance(df, new MarkupStyle(beginMarkup, endMarkup));
   }

   public String format(Object number) {
      synchronized(this.fmt) {
         return this.style.format(this.fmt.formatToCharacterIterator(number), this.preExponent);
      }
   }

   private static String getPreExponent(DecimalFormatSymbols dfs) {
      StringBuilder preExponent = new StringBuilder();
      preExponent.append(dfs.getExponentMultiplicationSign());
      char[] digits = dfs.getDigits();
      preExponent.append(digits[1]).append(digits[0]);
      return preExponent.toString();
   }

   private static ScientificNumberFormatter getInstance(DecimalFormat decimalFormat, Style style) {
      DecimalFormatSymbols dfs = decimalFormat.getDecimalFormatSymbols();
      return new ScientificNumberFormatter((DecimalFormat)decimalFormat.clone(), getPreExponent(dfs), style);
   }

   private static ScientificNumberFormatter getInstanceForLocale(ULocale locale, Style style) {
      DecimalFormat decimalFormat = (DecimalFormat)DecimalFormat.getScientificInstance(locale);
      return new ScientificNumberFormatter(decimalFormat, getPreExponent(decimalFormat.getDecimalFormatSymbols()), style);
   }

   private ScientificNumberFormatter(DecimalFormat decimalFormat, String preExponent, Style style) {
      this.fmt = decimalFormat;
      this.preExponent = preExponent;
      this.style = style;
   }

   private static class SuperscriptStyle extends Style {
      private static final char[] SUPERSCRIPT_DIGITS = new char[]{'⁰', '¹', '²', '³', '⁴', '⁵', '⁶', '⁷', '⁸', '⁹'};
      private static final char SUPERSCRIPT_PLUS_SIGN = '⁺';
      private static final char SUPERSCRIPT_MINUS_SIGN = '⁻';

      private SuperscriptStyle() {
         super(null);
      }

      String format(AttributedCharacterIterator iterator, String preExponent) {
         int copyFromOffset = 0;
         StringBuilder result = new StringBuilder();
         iterator.first();

         while(iterator.current() != '\uffff') {
            Map attributeSet = iterator.getAttributes();
            if (attributeSet.containsKey(NumberFormat.Field.EXPONENT_SYMBOL)) {
               append(iterator, copyFromOffset, iterator.getRunStart(NumberFormat.Field.EXPONENT_SYMBOL), result);
               copyFromOffset = iterator.getRunLimit(NumberFormat.Field.EXPONENT_SYMBOL);
               iterator.setIndex(copyFromOffset);
               result.append(preExponent);
            } else {
               int start;
               int limit;
               if (attributeSet.containsKey(NumberFormat.Field.EXPONENT_SIGN)) {
                  start = iterator.getRunStart(NumberFormat.Field.EXPONENT_SIGN);
                  limit = iterator.getRunLimit(NumberFormat.Field.EXPONENT_SIGN);
                  int aChar = char32AtAndAdvance(iterator);
                  if (Parse.UNISET_MINUS.contains(aChar)) {
                     append(iterator, copyFromOffset, start, result);
                     result.append('⁻');
                  } else {
                     if (!Parse.UNISET_PLUS.contains(aChar)) {
                        throw new IllegalArgumentException();
                     }

                     append(iterator, copyFromOffset, start, result);
                     result.append('⁺');
                  }

                  copyFromOffset = limit;
                  iterator.setIndex(limit);
               } else if (attributeSet.containsKey(NumberFormat.Field.EXPONENT)) {
                  start = iterator.getRunStart(NumberFormat.Field.EXPONENT);
                  limit = iterator.getRunLimit(NumberFormat.Field.EXPONENT);
                  append(iterator, copyFromOffset, start, result);
                  copyAsSuperscript(iterator, start, limit, result);
                  copyFromOffset = limit;
                  iterator.setIndex(limit);
               } else {
                  iterator.next();
               }
            }
         }

         append(iterator, copyFromOffset, iterator.getEndIndex(), result);
         return result.toString();
      }

      private static void copyAsSuperscript(AttributedCharacterIterator iterator, int start, int limit, StringBuilder result) {
         int oldIndex = iterator.getIndex();
         iterator.setIndex(start);

         while(iterator.getIndex() < limit) {
            int aChar = char32AtAndAdvance(iterator);
            int digit = UCharacter.digit(aChar);
            if (digit < 0) {
               throw new IllegalArgumentException();
            }

            result.append(SUPERSCRIPT_DIGITS[digit]);
         }

         iterator.setIndex(oldIndex);
      }

      private static int char32AtAndAdvance(AttributedCharacterIterator iterator) {
         char c1 = iterator.current();
         char c2 = iterator.next();
         if (UCharacter.isHighSurrogate(c1) && UCharacter.isLowSurrogate(c2)) {
            iterator.next();
            return UCharacter.toCodePoint(c1, c2);
         } else {
            return c1;
         }
      }

      // $FF: synthetic method
      SuperscriptStyle(Object x0) {
         this();
      }
   }

   private static class MarkupStyle extends Style {
      private final String beginMarkup;
      private final String endMarkup;

      MarkupStyle(String beginMarkup, String endMarkup) {
         super(null);
         this.beginMarkup = beginMarkup;
         this.endMarkup = endMarkup;
      }

      String format(AttributedCharacterIterator iterator, String preExponent) {
         int copyFromOffset = 0;
         StringBuilder result = new StringBuilder();
         iterator.first();

         while(iterator.current() != '\uffff') {
            Map attributeSet = iterator.getAttributes();
            if (attributeSet.containsKey(NumberFormat.Field.EXPONENT_SYMBOL)) {
               append(iterator, copyFromOffset, iterator.getRunStart(NumberFormat.Field.EXPONENT_SYMBOL), result);
               copyFromOffset = iterator.getRunLimit(NumberFormat.Field.EXPONENT_SYMBOL);
               iterator.setIndex(copyFromOffset);
               result.append(preExponent);
               result.append(this.beginMarkup);
            } else if (attributeSet.containsKey(NumberFormat.Field.EXPONENT)) {
               int limit = iterator.getRunLimit(NumberFormat.Field.EXPONENT);
               append(iterator, copyFromOffset, limit, result);
               copyFromOffset = limit;
               iterator.setIndex(limit);
               result.append(this.endMarkup);
            } else {
               iterator.next();
            }
         }

         append(iterator, copyFromOffset, iterator.getEndIndex(), result);
         return result.toString();
      }
   }

   private abstract static class Style {
      private Style() {
      }

      abstract String format(AttributedCharacterIterator var1, String var2);

      static void append(AttributedCharacterIterator iterator, int start, int limit, StringBuilder result) {
         int oldIndex = iterator.getIndex();
         iterator.setIndex(start);

         for(int i = start; i < limit; ++i) {
            result.append(iterator.current());
            iterator.next();
         }

         iterator.setIndex(oldIndex);
      }

      // $FF: synthetic method
      Style(Object x0) {
         this();
      }
   }
}
