package com.bea.core.repackaged.springframework.format.number;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Locale;

public abstract class AbstractNumberFormatter implements Formatter {
   private boolean lenient = false;

   public void setLenient(boolean lenient) {
      this.lenient = lenient;
   }

   public String print(Number number, Locale locale) {
      return this.getNumberFormat(locale).format(number);
   }

   public Number parse(String text, Locale locale) throws ParseException {
      NumberFormat format = this.getNumberFormat(locale);
      ParsePosition position = new ParsePosition(0);
      Number number = format.parse(text, position);
      if (position.getErrorIndex() != -1) {
         throw new ParseException(text, position.getIndex());
      } else if (!this.lenient && text.length() != position.getIndex()) {
         throw new ParseException(text, position.getIndex());
      } else {
         return number;
      }
   }

   protected abstract NumberFormat getNumberFormat(Locale var1);
}
