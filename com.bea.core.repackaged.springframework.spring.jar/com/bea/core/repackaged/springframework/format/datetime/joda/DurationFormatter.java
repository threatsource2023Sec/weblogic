package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.format.Formatter;
import java.text.ParseException;
import java.util.Locale;
import org.joda.time.Duration;

class DurationFormatter implements Formatter {
   public Duration parse(String text, Locale locale) throws ParseException {
      return Duration.parse(text);
   }

   public String print(Duration object, Locale locale) {
      return object.toString();
   }
}
