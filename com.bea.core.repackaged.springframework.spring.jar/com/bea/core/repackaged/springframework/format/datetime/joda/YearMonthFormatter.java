package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.text.ParseException;
import java.util.Locale;
import org.joda.time.YearMonth;

class YearMonthFormatter implements Formatter {
   public YearMonth parse(String text, Locale locale) throws ParseException {
      return YearMonth.parse(text);
   }

   public String print(YearMonth object, Locale locale) {
      return object.toString();
   }
}
