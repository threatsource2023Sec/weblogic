package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.text.ParseException;
import java.time.Period;
import java.util.Locale;

class PeriodFormatter implements Formatter {
   public Period parse(String text, Locale locale) throws ParseException {
      return Period.parse(text);
   }

   public String print(Period object, Locale locale) {
      return object.toString();
   }
}
