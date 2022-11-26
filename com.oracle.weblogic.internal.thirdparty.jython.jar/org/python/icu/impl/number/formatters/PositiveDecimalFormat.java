package org.python.icu.impl.number.formatters;

import org.python.icu.impl.number.Format;
import org.python.icu.impl.number.FormatQuantity;
import org.python.icu.impl.number.NumberStringBuilder;
import org.python.icu.impl.number.Properties;
import org.python.icu.text.DecimalFormatSymbols;
import org.python.icu.text.NumberFormat;

public class PositiveDecimalFormat implements Format.TargetFormat {
   private final boolean alwaysShowDecimal;
   private final int primaryGroupingSize;
   private final int secondaryGroupingSize;
   private final int minimumGroupingDigits;
   private final String infinityString;
   private final String nanString;
   private final String groupingSeparator;
   private final String decimalSeparator;
   private final String[] digitStrings;
   private final int codePointZero;

   public static boolean useGrouping(IProperties properties) {
      return properties.getGroupingSize() != -1 || properties.getSecondaryGroupingSize() != -1;
   }

   public static boolean allowsDecimalPoint(IProperties properties) {
      return properties.getDecimalSeparatorAlwaysShown() || properties.getMaximumFractionDigits() != 0;
   }

   public PositiveDecimalFormat(DecimalFormatSymbols symbols, IProperties properties) {
      int _primary = properties.getGroupingSize();
      int _secondary = properties.getSecondaryGroupingSize();
      this.primaryGroupingSize = _primary > 0 ? _primary : (_secondary > 0 ? _secondary : 0);
      this.secondaryGroupingSize = _secondary > 0 ? _secondary : this.primaryGroupingSize;
      this.minimumGroupingDigits = properties.getMinimumGroupingDigits();
      this.alwaysShowDecimal = properties.getDecimalSeparatorAlwaysShown();
      this.infinityString = symbols.getInfinity();
      this.nanString = symbols.getNaN();
      if (CurrencyFormat.useCurrency(properties)) {
         this.groupingSeparator = symbols.getMonetaryGroupingSeparatorString();
         this.decimalSeparator = symbols.getMonetaryDecimalSeparatorString();
      } else {
         this.groupingSeparator = symbols.getGroupingSeparatorString();
         this.decimalSeparator = symbols.getDecimalSeparatorString();
      }

      int _codePointZero = -1;
      String[] _digitStrings = symbols.getDigitStringsLocal();

      for(int i = 0; i < _digitStrings.length; ++i) {
         int cp = Character.codePointAt(_digitStrings[i], 0);
         int cc = Character.charCount(cp);
         if (cc != _digitStrings[i].length()) {
            _codePointZero = -1;
            break;
         }

         if (i == 0) {
            _codePointZero = cp;
         } else if (cp != _codePointZero + i) {
            _codePointZero = -1;
            break;
         }
      }

      if (_codePointZero != -1) {
         this.digitStrings = null;
         this.codePointZero = _codePointZero;
      } else {
         this.digitStrings = symbols.getDigitStrings();
         this.codePointZero = -1;
      }

   }

   public int target(FormatQuantity input, NumberStringBuilder string, int startIndex) {
      int length = 0;
      if (input.isInfinite()) {
         length += string.insert(startIndex, (CharSequence)this.infinityString, (NumberFormat.Field)NumberFormat.Field.INTEGER);
      } else if (input.isNaN()) {
         length += string.insert(startIndex, (CharSequence)this.nanString, (NumberFormat.Field)NumberFormat.Field.INTEGER);
      } else {
         length += this.addIntegerDigits(input, string, startIndex);
         if (input.getLowerDisplayMagnitude() < 0 || this.alwaysShowDecimal) {
            length += string.insert(startIndex + length, (CharSequence)this.decimalSeparator, (NumberFormat.Field)NumberFormat.Field.DECIMAL_SEPARATOR);
         }

         length += this.addFractionDigits(input, string, startIndex + length);
      }

      return length;
   }

   private int addIntegerDigits(FormatQuantity input, NumberStringBuilder string, int startIndex) {
      int length = 0;
      int integerCount = input.getUpperDisplayMagnitude() + 1;

      for(int i = 0; i < integerCount; ++i) {
         if (this.primaryGroupingSize > 0 && i == this.primaryGroupingSize && integerCount - i >= this.minimumGroupingDigits) {
            length += string.insert(startIndex, (CharSequence)this.groupingSeparator, (NumberFormat.Field)NumberFormat.Field.GROUPING_SEPARATOR);
         } else if (this.secondaryGroupingSize > 0 && i > this.primaryGroupingSize && (i - this.primaryGroupingSize) % this.secondaryGroupingSize == 0) {
            length += string.insert(startIndex, (CharSequence)this.groupingSeparator, (NumberFormat.Field)NumberFormat.Field.GROUPING_SEPARATOR);
         }

         byte nextDigit = input.getDigit(i);
         length += this.addDigit(nextDigit, string, startIndex, NumberFormat.Field.INTEGER);
      }

      return length;
   }

   private int addFractionDigits(FormatQuantity input, NumberStringBuilder string, int index) {
      int length = 0;
      int fractionCount = -input.getLowerDisplayMagnitude();

      for(int i = 0; i < fractionCount; ++i) {
         byte nextDigit = input.getDigit(-i - 1);
         length += this.addDigit(nextDigit, string, index + length, NumberFormat.Field.FRACTION);
      }

      return length;
   }

   private int addDigit(byte digit, NumberStringBuilder outputString, int index, NumberFormat.Field field) {
      return this.codePointZero != -1 ? outputString.insertCodePoint(index, this.codePointZero + digit, field) : outputString.insert(index, (CharSequence)this.digitStrings[digit], (NumberFormat.Field)field);
   }

   public void export(Properties properties) {
      int effectiveSecondaryGroupingSize = this.secondaryGroupingSize == this.primaryGroupingSize ? 0 : this.secondaryGroupingSize;
      properties.setDecimalSeparatorAlwaysShown(this.alwaysShowDecimal);
      properties.setGroupingSize(this.primaryGroupingSize);
      properties.setSecondaryGroupingSize(effectiveSecondaryGroupingSize);
      properties.setMinimumGroupingDigits(this.minimumGroupingDigits);
   }

   public interface IProperties extends CurrencyFormat.IProperties {
      int DEFAULT_GROUPING_SIZE = -1;
      int DEFAULT_SECONDARY_GROUPING_SIZE = -1;
      boolean DEFAULT_DECIMAL_SEPARATOR_ALWAYS_SHOWN = false;
      int DEFAULT_MINIMUM_GROUPING_DIGITS = 1;

      int getGroupingSize();

      IProperties setGroupingSize(int var1);

      int getSecondaryGroupingSize();

      IProperties setSecondaryGroupingSize(int var1);

      boolean getDecimalSeparatorAlwaysShown();

      IProperties setDecimalSeparatorAlwaysShown(boolean var1);

      int getMinimumGroupingDigits();

      IProperties setMinimumGroupingDigits(int var1);
   }
}
