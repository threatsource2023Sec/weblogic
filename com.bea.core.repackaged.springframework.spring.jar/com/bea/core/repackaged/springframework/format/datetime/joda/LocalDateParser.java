package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.format.Parser;
import java.text.ParseException;
import java.util.Locale;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

public final class LocalDateParser implements Parser {
   private final DateTimeFormatter formatter;

   public LocalDateParser(DateTimeFormatter formatter) {
      this.formatter = formatter;
   }

   public LocalDate parse(String text, Locale locale) throws ParseException {
      return JodaTimeContextHolder.getFormatter(this.formatter, locale).parseLocalDate(text);
   }
}
