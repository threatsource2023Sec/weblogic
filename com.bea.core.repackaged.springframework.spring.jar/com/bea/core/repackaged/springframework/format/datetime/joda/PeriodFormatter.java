package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.text.ParseException;
import java.util.Locale;
import org.joda.time.Period;

class PeriodFormatter implements Formatter {
   public Period parse(String text, Locale locale) throws ParseException {
      return Period.parse(text);
   }

   public String print(Period object, Locale locale) {
      return object.toString();
   }
}
