package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.format.Parser;
import java.text.ParseException;
import java.util.Locale;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;

public final class LocalDateTimeParser implements Parser {
   private final DateTimeFormatter formatter;

   public LocalDateTimeParser(DateTimeFormatter formatter) {
      this.formatter = formatter;
   }

   public LocalDateTime parse(String text, Locale locale) throws ParseException {
      return JodaTimeContextHolder.getFormatter(this.formatter, locale).parseLocalDateTime(text);
   }
}
