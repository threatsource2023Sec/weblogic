package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.text.ParseException;
import java.time.Duration;
import java.util.Locale;

class DurationFormatter implements Formatter {
   public Duration parse(String text, Locale locale) throws ParseException {
      return Duration.parse(text);
   }

   public String print(Duration object, Locale locale) {
      return object.toString();
   }
}
