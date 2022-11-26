package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.text.ParseException;
import java.time.Year;
import java.util.Locale;

class YearFormatter implements Formatter {
   public Year parse(String text, Locale locale) throws ParseException {
      return Year.parse(text);
   }

   public String print(Year object, Locale locale) {
      return object.toString();
   }
}
