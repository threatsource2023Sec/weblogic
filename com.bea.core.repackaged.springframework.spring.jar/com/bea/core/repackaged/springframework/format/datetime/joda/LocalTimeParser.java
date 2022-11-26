package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.format.Parser;
import java.text.ParseException;
import java.util.Locale;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormatter;

public final class LocalTimeParser implements Parser {
   private final DateTimeFormatter formatter;

   public LocalTimeParser(DateTimeFormatter formatter) {
      this.formatter = formatter;
   }

   public LocalTime parse(String text, Locale locale) throws ParseException {
      return JodaTimeContextHolder.getFormatter(this.formatter, locale).parseLocalTime(text);
   }
}
