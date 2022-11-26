package com.bea.core.repackaged.springframework.format.number;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class NumberStyleFormatter extends AbstractNumberFormatter {
   @Nullable
   private String pattern;

   public NumberStyleFormatter() {
   }

   public NumberStyleFormatter(String pattern) {
      this.pattern = pattern;
   }

   public void setPattern(String pattern) {
      this.pattern = pattern;
   }

   public NumberFormat getNumberFormat(Locale locale) {
      NumberFormat format = NumberFormat.getInstance(locale);
      if (!(format instanceof DecimalFormat)) {
         if (this.pattern != null) {
            throw new IllegalStateException("Cannot support pattern for non-DecimalFormat: " + format);
         } else {
            return format;
         }
      } else {
         DecimalFormat decimalFormat = (DecimalFormat)format;
         decimalFormat.setParseBigDecimal(true);
         if (this.pattern != null) {
            decimalFormat.applyPattern(this.pattern);
         }

         return decimalFormat;
      }
   }
}
