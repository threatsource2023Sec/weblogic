package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.text.ParseException;
import java.time.MonthDay;
import java.util.Locale;

class MonthDayFormatter implements Formatter {
   public MonthDay parse(String text, Locale locale) throws ParseException {
      return MonthDay.parse(text);
   }

   public String print(MonthDay object, Locale locale) {
      return object.toString();
   }
}
