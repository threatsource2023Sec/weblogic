package com.bea.core.repackaged.springframework.format.number;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class PercentStyleFormatter extends AbstractNumberFormatter {
   protected NumberFormat getNumberFormat(Locale locale) {
      NumberFormat format = NumberFormat.getPercentInstance(locale);
      if (format instanceof DecimalFormat) {
         ((DecimalFormat)format).setParseBigDecimal(true);
      }

      return format;
   }
}
