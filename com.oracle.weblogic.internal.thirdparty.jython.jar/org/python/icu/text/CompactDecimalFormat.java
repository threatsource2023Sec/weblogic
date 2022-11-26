package org.python.icu.text;

import java.text.ParsePosition;
import java.util.Locale;
import org.python.icu.impl.number.Properties;
import org.python.icu.util.CurrencyAmount;
import org.python.icu.util.ULocale;

public class CompactDecimalFormat extends DecimalFormat {
   private static final long serialVersionUID = 4716293295276629682L;

   public static CompactDecimalFormat getInstance(ULocale locale, CompactStyle style) {
      return new CompactDecimalFormat(locale, style);
   }

   public static CompactDecimalFormat getInstance(Locale locale, CompactStyle style) {
      return new CompactDecimalFormat(ULocale.forLocale(locale), style);
   }

   CompactDecimalFormat(ULocale locale, CompactStyle style) {
      String pattern = getPattern(locale, 0);
      this.symbols = DecimalFormatSymbols.getInstance(locale);
      this.properties = new Properties();
      this.properties.setCompactStyle(style);
      this.exportedProperties = new Properties();
      this.setPropertiesFromPattern(pattern, true);
      if (style == CompactDecimalFormat.CompactStyle.SHORT) {
         this.properties.setMinimumGroupingDigits(2);
      }

      this.refreshFormatter();
   }

   public Number parse(String text, ParsePosition parsePosition) {
      throw new UnsupportedOperationException();
   }

   public CurrencyAmount parseCurrency(CharSequence text, ParsePosition parsePosition) {
      throw new UnsupportedOperationException();
   }

   public static enum CompactStyle {
      SHORT,
      LONG;
   }
}
