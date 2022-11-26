package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.text.ParseException;
import java.time.Month;
import java.util.Locale;

class MonthFormatter implements Formatter {
   public Month parse(String text, Locale locale) throws ParseException {
      return Month.valueOf(text.toUpperCase());
   }

   public String print(Month object, Locale locale) {
      return object.toString();
   }
}
