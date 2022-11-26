package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.text.ParseException;
import java.time.YearMonth;
import java.util.Locale;

class YearMonthFormatter implements Formatter {
   public YearMonth parse(String text, Locale locale) throws ParseException {
      return YearMonth.parse(text);
   }

   public String print(YearMonth object, Locale locale) {
      return object.toString();
   }
}
