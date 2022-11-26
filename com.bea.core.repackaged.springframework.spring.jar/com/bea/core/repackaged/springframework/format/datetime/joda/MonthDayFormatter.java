package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.text.ParseException;
import java.util.Locale;
import org.joda.time.MonthDay;

class MonthDayFormatter implements Formatter {
   public MonthDay parse(String text, Locale locale) throws ParseException {
      return MonthDay.parse(text);
   }

   public String print(MonthDay object, Locale locale) {
      return object.toString();
   }
}
