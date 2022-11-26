package org.python.icu.impl.number;

import java.math.BigDecimal;
import org.python.icu.impl.number.formatters.PaddingFormat;
import org.python.icu.text.DecimalFormatSymbols;

public class PatternString {
   public static Properties parseToProperties(String pattern, boolean ignoreRounding) {
      Properties properties = new Properties();
      PatternString.LdmlDecimalPatternParser.parse(pattern, properties, ignoreRounding);
      return properties;
   }

   public static Properties parseToProperties(String pattern) {
      return parseToProperties(pattern, false);
   }

   public static void parseToExistingProperties(String pattern, Properties properties, boolean ignoreRounding) {
      PatternString.LdmlDecimalPatternParser.parse(pattern, properties, ignoreRounding);
   }

   public static void parseToExistingProperties(String pattern, Properties properties) {
      parseToExistingProperties(pattern, properties, false);
   }

   public static String propertiesToString(Properties properties) {
      StringBuilder sb = new StringBuilder();
      int dosMax = 100;
      int groupingSize = Math.min(properties.getSecondaryGroupingSize(), dosMax);
      int firstGroupingSize = Math.min(properties.getGroupingSize(), dosMax);
      int paddingWidth = Math.min(properties.getFormatWidth(), dosMax);
      PaddingFormat.PadPosition paddingLocation = properties.getPadPosition();
      String paddingString = properties.getPadString();
      int minInt = Math.max(Math.min(properties.getMinimumIntegerDigits(), dosMax), 0);
      int maxInt = Math.min(properties.getMaximumIntegerDigits(), dosMax);
      int minFrac = Math.max(Math.min(properties.getMinimumFractionDigits(), dosMax), 0);
      int maxFrac = Math.min(properties.getMaximumFractionDigits(), dosMax);
      int minSig = Math.min(properties.getMinimumSignificantDigits(), dosMax);
      int maxSig = Math.min(properties.getMaximumSignificantDigits(), dosMax);
      boolean alwaysShowDecimal = properties.getDecimalSeparatorAlwaysShown();
      int exponentDigits = Math.min(properties.getMinimumExponentDigits(), dosMax);
      boolean exponentShowPlusSign = properties.getExponentSignAlwaysShown();
      String pp = properties.getPositivePrefix();
      String ppp = properties.getPositivePrefixPattern();
      String ps = properties.getPositiveSuffix();
      String psp = properties.getPositiveSuffixPattern();
      String np = properties.getNegativePrefix();
      String npp = properties.getNegativePrefixPattern();
      String ns = properties.getNegativeSuffix();
      String nsp = properties.getNegativeSuffixPattern();
      if (ppp != null) {
         sb.append(ppp);
      }

      AffixPatternUtils.escape(pp, sb);
      int afterPrefixPos = sb.length();
      int grouping;
      int grouping1;
      int grouping2;
      if (groupingSize != Math.min(dosMax, -1) && firstGroupingSize != Math.min(dosMax, -1) && groupingSize != firstGroupingSize) {
         grouping = groupingSize;
         grouping1 = groupingSize;
         grouping2 = firstGroupingSize;
      } else if (groupingSize != Math.min(dosMax, -1)) {
         grouping = groupingSize;
         grouping1 = 0;
         grouping2 = groupingSize;
      } else if (firstGroupingSize != Math.min(dosMax, -1)) {
         grouping = groupingSize;
         grouping1 = 0;
         grouping2 = firstGroupingSize;
      } else {
         grouping = 0;
         grouping1 = 0;
         grouping2 = 0;
      }

      int groupingLength = grouping1 + grouping2 + 1;
      BigDecimal roundingInterval = properties.getRoundingIncrement();
      StringBuilder digitsString = new StringBuilder();
      int digitsStringScale = 0;
      if (maxSig == Math.min(dosMax, -1)) {
         if (roundingInterval != Properties.DEFAULT_ROUNDING_INCREMENT) {
            digitsStringScale = -roundingInterval.scale();
            String str = roundingInterval.scaleByPowerOfTen(roundingInterval.scale()).toPlainString();
            if (str.charAt(0) == '\'') {
               digitsString.append(str, 1, str.length());
            } else {
               digitsString.append(str);
            }
         }
      } else {
         while(digitsString.length() < minSig) {
            digitsString.append('@');
         }

         while(digitsString.length() < maxSig) {
            digitsString.append('#');
         }
      }

      while(digitsString.length() + digitsStringScale < minInt) {
         digitsString.insert(0, '0');
      }

      while(-digitsStringScale < minFrac) {
         digitsString.append('0');
         --digitsStringScale;
      }

      int m0 = Math.max(groupingLength, digitsString.length() + digitsStringScale);
      m0 = maxInt != dosMax ? Math.max(maxInt, m0) - 1 : m0 - 1;
      int mN = maxFrac != dosMax ? Math.min(-maxFrac, digitsStringScale) : digitsStringScale;

      int beforeSuffixPos;
      int addedLength;
      for(beforeSuffixPos = m0; beforeSuffixPos >= mN; --beforeSuffixPos) {
         addedLength = digitsString.length() + digitsStringScale - beforeSuffixPos - 1;
         if (addedLength >= 0 && addedLength < digitsString.length()) {
            sb.append(digitsString.charAt(addedLength));
         } else {
            sb.append('#');
         }

         if (beforeSuffixPos > grouping2 && grouping > 0 && (beforeSuffixPos - grouping2) % grouping == 0) {
            sb.append(',');
         } else if (beforeSuffixPos > 0 && beforeSuffixPos == grouping2) {
            sb.append(',');
         } else if (beforeSuffixPos == 0 && (alwaysShowDecimal || mN < 0)) {
            sb.append('.');
         }
      }

      if (exponentDigits != Math.min(dosMax, -1)) {
         sb.append('E');
         if (exponentShowPlusSign) {
            sb.append('+');
         }

         for(beforeSuffixPos = 0; beforeSuffixPos < exponentDigits; ++beforeSuffixPos) {
            sb.append('0');
         }
      }

      beforeSuffixPos = sb.length();
      if (psp != null) {
         sb.append(psp);
      }

      AffixPatternUtils.escape(ps, sb);
      if (paddingWidth != 0) {
         while(paddingWidth - sb.length() > 0) {
            sb.insert(afterPrefixPos, '#');
            ++beforeSuffixPos;
         }

         switch (paddingLocation) {
            case BEFORE_PREFIX:
               addedLength = escapePaddingString(paddingString, sb, 0);
               sb.insert(0, '*');
               afterPrefixPos += addedLength + 1;
               beforeSuffixPos += addedLength + 1;
               break;
            case AFTER_PREFIX:
               addedLength = escapePaddingString(paddingString, sb, afterPrefixPos);
               sb.insert(afterPrefixPos, '*');
               afterPrefixPos += addedLength + 1;
               beforeSuffixPos += addedLength + 1;
               break;
            case BEFORE_SUFFIX:
               escapePaddingString(paddingString, sb, beforeSuffixPos);
               sb.insert(beforeSuffixPos, '*');
               break;
            case AFTER_SUFFIX:
               sb.append('*');
               escapePaddingString(paddingString, sb, sb.length());
         }
      }

      if (np != null || ns != null || npp == null && nsp != null || npp != null && (npp.length() != 1 || npp.charAt(0) != '-' || nsp.length() != 0)) {
         sb.append(';');
         if (npp != null) {
            sb.append(npp);
         }

         AffixPatternUtils.escape(np, sb);
         sb.append(sb, afterPrefixPos, beforeSuffixPos);
         if (nsp != null) {
            sb.append(nsp);
         }

         AffixPatternUtils.escape(ns, sb);
      }

      return sb.toString();
   }

   private static int escapePaddingString(CharSequence input, StringBuilder output, int startIndex) {
      if (input == null || ((CharSequence)input).length() == 0) {
         input = " ";
      }

      int startLength = output.length();
      if (((CharSequence)input).length() == 1) {
         if (input.equals("'")) {
            output.insert(startIndex, "''");
         } else {
            output.insert(startIndex, (CharSequence)input);
         }
      } else {
         output.insert(startIndex, '\'');
         int offset = 1;

         for(int i = 0; i < ((CharSequence)input).length(); ++i) {
            char ch = ((CharSequence)input).charAt(i);
            if (ch == '\'') {
               output.insert(startIndex + offset, "''");
               offset += 2;
            } else {
               output.insert(startIndex + offset, ch);
               ++offset;
            }
         }

         output.insert(startIndex + offset, '\'');
      }

      return output.length() - startLength;
   }

   /** @deprecated */
   @Deprecated
   public static String convertLocalized(CharSequence input, DecimalFormatSymbols symbols, boolean toLocalized) {
      if (input == null) {
         return null;
      } else {
         int[][] table = new int[6][2];
         int standIdx = toLocalized ? 0 : 1;
         int localIdx = toLocalized ? 1 : 0;
         table[0][standIdx] = 37;
         table[0][localIdx] = symbols.getPercent();
         table[1][standIdx] = 8240;
         table[1][localIdx] = symbols.getPerMill();
         table[2][standIdx] = 46;
         table[2][localIdx] = symbols.getDecimalSeparator();
         table[3][standIdx] = 44;
         table[3][localIdx] = symbols.getGroupingSeparator();
         table[4][standIdx] = 45;
         table[4][localIdx] = symbols.getMinusSign();
         table[5][standIdx] = 43;
         table[5][localIdx] = symbols.getPlusSign();

         int offset;
         for(offset = 0; offset < table.length; ++offset) {
            if (table[offset][localIdx] == 39) {
               table[offset][localIdx] = 8217;
            }
         }

         offset = 0;
         int state = 0;

         StringBuilder result;
         int cp;
         for(result = new StringBuilder(); offset < input.length(); offset += Character.charCount(cp)) {
            cp = Character.codePointAt(input, offset);
            int cpToAppend = cp;
            if (state != 1 && state != 3 && state != 4) {
               if (cp == 39) {
                  if (state == 2 && offset + 1 < input.length()) {
                     int nextCp = Character.codePointAt(input, offset + 1);
                     if (nextCp == 39) {
                        state = 4;
                     } else {
                        state = 3;
                        cpToAppend = -1;
                     }
                  } else {
                     state = 1;
                  }
               } else {
                  boolean needsSpecialQuote = false;

                  for(int i = 0; i < table.length; ++i) {
                     if (table[i][0] == cp) {
                        cpToAppend = table[i][1];
                        needsSpecialQuote = false;
                        break;
                     }

                     if (table[i][1] == cp) {
                        needsSpecialQuote = true;
                     }
                  }

                  if (state == 0 && needsSpecialQuote) {
                     state = 2;
                     result.appendCodePoint(39);
                  } else if (state == 2 && !needsSpecialQuote) {
                     state = 0;
                     result.appendCodePoint(39);
                  }
               }
            } else if (cp == 39) {
               if (state == 1) {
                  state = 0;
               } else if (state == 3) {
                  state = 2;
                  cpToAppend = -1;
               } else {
                  state = 2;
               }
            }

            if (cpToAppend != -1) {
               result.appendCodePoint(cpToAppend);
            }
         }

         if (state == 2) {
            result.appendCodePoint(39);
         }

         return result.toString();
      }
   }

   static class LdmlDecimalPatternParser {
      static void parse(String pattern, Properties properties, boolean ignoreRounding) {
         if (pattern != null && pattern.length() != 0) {
            ParserState state = new ParserState(pattern);
            PatternParseResult result = new PatternParseResult();
            consumePattern(state, result);
            result.saveToProperties(properties, ignoreRounding);
         } else {
            properties.clear();
         }
      }

      private static void consumePattern(ParserState state, PatternParseResult result) {
         consumeSubpattern(state, result.positive);
         if (state.peek() == 59) {
            state.next();
            result.negative = new SubpatternParseResult();
            consumeSubpattern(state, result.negative);
         }

         if (state.peek() != -1) {
            throw state.toParseException("pattern");
         }
      }

      private static void consumeSubpattern(ParserState state, SubpatternParseResult result) {
         consumePadding(state, result, PaddingFormat.PadPosition.BEFORE_PREFIX);
         consumeAffix(state, result, result.prefix);
         consumePadding(state, result, PaddingFormat.PadPosition.AFTER_PREFIX);
         consumeFormat(state, result);
         consumeExponent(state, result);
         consumePadding(state, result, PaddingFormat.PadPosition.BEFORE_SUFFIX);
         consumeAffix(state, result, result.suffix);
         consumePadding(state, result, PaddingFormat.PadPosition.AFTER_SUFFIX);
      }

      private static void consumePadding(ParserState state, SubpatternParseResult result, PaddingFormat.PadPosition paddingLocation) {
         if (state.peek() == 42) {
            result.paddingLocation = paddingLocation;
            state.next();
            consumeLiteral(state, result.padding);
         }
      }

      private static void consumeAffix(ParserState state, SubpatternParseResult result, StringBuilder destination) {
         while(true) {
            switch (state.peek()) {
               case -1:
               case 35:
               case 42:
               case 44:
               case 46:
               case 48:
               case 49:
               case 50:
               case 51:
               case 52:
               case 53:
               case 54:
               case 55:
               case 56:
               case 57:
               case 59:
               case 64:
                  return;
               case 37:
                  result.hasPercentSign = true;
               case 164:
               default:
                  break;
               case 8240:
                  result.hasPerMilleSign = true;
            }

            consumeLiteral(state, destination);
         }
      }

      private static void consumeLiteral(ParserState state, StringBuilder destination) {
         if (state.peek() == -1) {
            throw state.toParseException("expected unquoted literal but found end of string");
         } else {
            if (state.peek() == 39) {
               destination.appendCodePoint(state.next());

               while(state.peek() != 39) {
                  if (state.peek() == -1) {
                     throw state.toParseException("expected quoted literal but found end of string");
                  }

                  destination.appendCodePoint(state.next());
               }

               destination.appendCodePoint(state.next());
            } else {
               destination.appendCodePoint(state.next());
            }

         }
      }

      private static void consumeFormat(ParserState state, SubpatternParseResult result) {
         consumeIntegerFormat(state, result);
         if (state.peek() == 46) {
            state.next();
            result.hasDecimal = true;
            ++result.paddingWidth;
            consumeFractionFormat(state, result);
         }

      }

      private static void consumeIntegerFormat(ParserState state, SubpatternParseResult result) {
         boolean seenSignificantDigitMarker = false;
         boolean seenDigit = false;

         while(true) {
            int var10002;
            switch (state.peek()) {
               case 35:
                  if (seenDigit) {
                     throw state.toParseException("# cannot follow 0 before decimal point");
                  }

                  ++result.paddingWidth;
                  var10002 = result.groupingSizes[0]++;
                  result.totalIntegerDigits += seenSignificantDigitMarker ? 0 : 1;
                  result.maximumSignificantDigits += seenSignificantDigitMarker ? 1 : 0;
                  result.rounding.appendDigit((byte)0, 0, true);
                  break;
               case 36:
               case 37:
               case 38:
               case 39:
               case 40:
               case 41:
               case 42:
               case 43:
               case 45:
               case 46:
               case 47:
               case 58:
               case 59:
               case 60:
               case 61:
               case 62:
               case 63:
               default:
                  return;
               case 44:
                  ++result.paddingWidth;
                  result.groupingSizes[2] = result.groupingSizes[1];
                  result.groupingSizes[1] = result.groupingSizes[0];
                  result.groupingSizes[0] = 0;
                  break;
               case 48:
               case 49:
               case 50:
               case 51:
               case 52:
               case 53:
               case 54:
               case 55:
               case 56:
               case 57:
                  seenDigit = true;
                  if (seenSignificantDigitMarker) {
                     throw state.toParseException("Can't mix @ and 0 in pattern");
                  }

                  ++result.paddingWidth;
                  var10002 = result.groupingSizes[0]++;
                  ++result.totalIntegerDigits;
                  ++result.minimumIntegerDigits;
                  result.rounding.appendDigit((byte)(state.peek() - 48), 0, true);
                  break;
               case 64:
                  seenSignificantDigitMarker = true;
                  if (seenDigit) {
                     throw state.toParseException("Can't mix @ and 0 in pattern");
                  }

                  ++result.paddingWidth;
                  var10002 = result.groupingSizes[0]++;
                  ++result.totalIntegerDigits;
                  ++result.minimumSignificantDigits;
                  ++result.maximumSignificantDigits;
                  result.rounding.appendDigit((byte)0, 0, true);
            }

            state.next();
         }
      }

      private static void consumeFractionFormat(ParserState state, SubpatternParseResult result) {
         int zeroCounter = 0;
         boolean seenHash = false;

         while(true) {
            switch (state.peek()) {
               case 35:
                  seenHash = true;
                  ++result.paddingWidth;
                  ++result.maximumFractionDigits;
                  ++zeroCounter;
                  break;
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
               default:
                  return;
               case 48:
               case 49:
               case 50:
               case 51:
               case 52:
               case 53:
               case 54:
               case 55:
               case 56:
               case 57:
                  if (seenHash) {
                     throw state.toParseException("0 cannot follow # after decimal point");
                  }

                  ++result.paddingWidth;
                  ++result.minimumFractionDigits;
                  ++result.maximumFractionDigits;
                  if (state.peek() == 48) {
                     ++zeroCounter;
                  } else {
                     result.rounding.appendDigit((byte)(state.peek() - 48), zeroCounter, false);
                     zeroCounter = 0;
                  }
            }

            state.next();
         }
      }

      private static void consumeExponent(ParserState state, SubpatternParseResult result) {
         if (state.peek() == 69) {
            state.next();
            ++result.paddingWidth;
            if (state.peek() == 43) {
               state.next();
               result.exponentShowPlusSign = true;
               ++result.paddingWidth;
            }

            while(state.peek() == 48) {
               state.next();
               ++result.exponentDigits;
               ++result.paddingWidth;
            }

         }
      }

      private static class ParserState {
         final String pattern;
         int offset;

         ParserState(String pattern) {
            this.pattern = pattern;
            this.offset = 0;
         }

         int peek() {
            return this.offset == this.pattern.length() ? -1 : this.pattern.codePointAt(this.offset);
         }

         int next() {
            int codePoint = this.peek();
            this.offset += Character.charCount(codePoint);
            return codePoint;
         }

         IllegalArgumentException toParseException(String message) {
            StringBuilder sb = new StringBuilder();
            sb.append("Unexpected character in decimal format pattern: '");
            sb.append(this.pattern);
            sb.append("': ");
            sb.append(message);
            sb.append(": ");
            if (this.peek() == -1) {
               sb.append("EOL");
            } else {
               sb.append("'");
               sb.append(Character.toChars(this.peek()));
               sb.append("'");
            }

            return new IllegalArgumentException(sb.toString());
         }
      }

      private static class SubpatternParseResult {
         int[] groupingSizes;
         int minimumIntegerDigits;
         int totalIntegerDigits;
         int minimumFractionDigits;
         int maximumFractionDigits;
         int minimumSignificantDigits;
         int maximumSignificantDigits;
         boolean hasDecimal;
         int paddingWidth;
         PaddingFormat.PadPosition paddingLocation;
         FormatQuantity4 rounding;
         boolean exponentShowPlusSign;
         int exponentDigits;
         boolean hasPercentSign;
         boolean hasPerMilleSign;
         StringBuilder padding;
         StringBuilder prefix;
         StringBuilder suffix;

         private SubpatternParseResult() {
            this.groupingSizes = new int[]{0, -1, -1};
            this.minimumIntegerDigits = 0;
            this.totalIntegerDigits = 0;
            this.minimumFractionDigits = 0;
            this.maximumFractionDigits = 0;
            this.minimumSignificantDigits = 0;
            this.maximumSignificantDigits = 0;
            this.hasDecimal = false;
            this.paddingWidth = 0;
            this.paddingLocation = null;
            this.rounding = new FormatQuantity4();
            this.exponentShowPlusSign = false;
            this.exponentDigits = 0;
            this.hasPercentSign = false;
            this.hasPerMilleSign = false;
            this.padding = new StringBuilder();
            this.prefix = new StringBuilder();
            this.suffix = new StringBuilder();
         }

         // $FF: synthetic method
         SubpatternParseResult(Object x0) {
            this();
         }
      }

      private static class PatternParseResult {
         SubpatternParseResult positive;
         SubpatternParseResult negative;

         private PatternParseResult() {
            this.positive = new SubpatternParseResult();
            this.negative = null;
         }

         void saveToProperties(Properties properties, boolean ignoreRounding) {
            if (this.positive.groupingSizes[1] != -1) {
               properties.setGroupingSize(this.positive.groupingSizes[0]);
            } else {
               properties.setGroupingSize(-1);
            }

            if (this.positive.groupingSizes[2] != -1) {
               properties.setSecondaryGroupingSize(this.positive.groupingSizes[1]);
            } else {
               properties.setSecondaryGroupingSize(-1);
            }

            int minInt;
            int minFrac;
            if (this.positive.totalIntegerDigits == 0 && this.positive.maximumFractionDigits > 0) {
               minInt = 0;
               minFrac = Math.max(1, this.positive.minimumFractionDigits);
            } else if (this.positive.minimumIntegerDigits == 0 && this.positive.minimumFractionDigits == 0) {
               minInt = 1;
               minFrac = 0;
            } else {
               minInt = this.positive.minimumIntegerDigits;
               minFrac = this.positive.minimumFractionDigits;
            }

            if (this.positive.minimumSignificantDigits > 0) {
               properties.setMinimumFractionDigits(-1);
               properties.setMaximumFractionDigits(-1);
               properties.setRoundingIncrement(Properties.DEFAULT_ROUNDING_INCREMENT);
               properties.setMinimumSignificantDigits(this.positive.minimumSignificantDigits);
               properties.setMaximumSignificantDigits(this.positive.maximumSignificantDigits);
            } else if (!this.positive.rounding.isZero()) {
               if (!ignoreRounding) {
                  properties.setMinimumFractionDigits(minFrac);
                  properties.setMaximumFractionDigits(this.positive.maximumFractionDigits);
                  properties.setRoundingIncrement(this.positive.rounding.toBigDecimal());
               } else {
                  properties.setMinimumFractionDigits(-1);
                  properties.setMaximumFractionDigits(-1);
                  properties.setRoundingIncrement(Properties.DEFAULT_ROUNDING_INCREMENT);
               }

               properties.setMinimumSignificantDigits(-1);
               properties.setMaximumSignificantDigits(-1);
            } else {
               if (!ignoreRounding) {
                  properties.setMinimumFractionDigits(minFrac);
                  properties.setMaximumFractionDigits(this.positive.maximumFractionDigits);
                  properties.setRoundingIncrement(Properties.DEFAULT_ROUNDING_INCREMENT);
               } else {
                  properties.setMinimumFractionDigits(-1);
                  properties.setMaximumFractionDigits(-1);
                  properties.setRoundingIncrement(Properties.DEFAULT_ROUNDING_INCREMENT);
               }

               properties.setMinimumSignificantDigits(-1);
               properties.setMaximumSignificantDigits(-1);
            }

            if (this.positive.hasDecimal && this.positive.maximumFractionDigits == 0) {
               properties.setDecimalSeparatorAlwaysShown(true);
            } else {
               properties.setDecimalSeparatorAlwaysShown(false);
            }

            if (this.positive.exponentDigits > 0) {
               properties.setExponentSignAlwaysShown(this.positive.exponentShowPlusSign);
               properties.setMinimumExponentDigits(this.positive.exponentDigits);
               if (this.positive.minimumSignificantDigits == 0) {
                  properties.setMinimumIntegerDigits(this.positive.minimumIntegerDigits);
                  properties.setMaximumIntegerDigits(this.positive.totalIntegerDigits);
               } else {
                  properties.setMinimumIntegerDigits(1);
                  properties.setMaximumIntegerDigits(-1);
               }
            } else {
               properties.setExponentSignAlwaysShown(false);
               properties.setMinimumExponentDigits(-1);
               properties.setMinimumIntegerDigits(minInt);
               properties.setMaximumIntegerDigits(-1);
            }

            if (this.positive.padding.length() > 0) {
               int paddingWidth = this.positive.paddingWidth + AffixPatternUtils.unescapedLength(this.positive.prefix) + AffixPatternUtils.unescapedLength(this.positive.suffix);
               properties.setFormatWidth(paddingWidth);
               if (this.positive.padding.length() == 1) {
                  properties.setPadString(this.positive.padding.toString());
               } else if (this.positive.padding.length() == 2) {
                  if (this.positive.padding.charAt(0) == '\'') {
                     properties.setPadString("'");
                  } else {
                     properties.setPadString(this.positive.padding.toString());
                  }
               } else {
                  properties.setPadString(this.positive.padding.subSequence(1, this.positive.padding.length() - 1).toString());
               }

               assert this.positive.paddingLocation != null;

               properties.setPadPosition(this.positive.paddingLocation);
            } else {
               properties.setFormatWidth(0);
               properties.setPadString(Properties.DEFAULT_PAD_STRING);
               properties.setPadPosition(Properties.DEFAULT_PAD_POSITION);
            }

            properties.setPositivePrefixPattern(this.positive.prefix.toString());
            properties.setPositiveSuffixPattern(this.positive.suffix.toString());
            if (this.negative != null) {
               properties.setNegativePrefixPattern(this.negative.prefix.toString());
               properties.setNegativeSuffixPattern(this.negative.suffix.toString());
            } else {
               properties.setNegativePrefixPattern((String)null);
               properties.setNegativeSuffixPattern((String)null);
            }

            if (this.positive.hasPercentSign) {
               properties.setMagnitudeMultiplier(2);
            } else if (this.positive.hasPerMilleSign) {
               properties.setMagnitudeMultiplier(3);
            } else {
               properties.setMagnitudeMultiplier(0);
            }

         }

         // $FF: synthetic method
         PatternParseResult(Object x0) {
            this();
         }
      }
   }
}
