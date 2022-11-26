package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.format.Parser;
import java.text.ParseException;
import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;

public final class DateTimeParser implements Parser {
   private final DateTimeFormatter formatter;

   public DateTimeParser(DateTimeFormatter formatter) {
      this.formatter = formatter;
   }

   public DateTime parse(String text, Locale locale) throws ParseException {
      return JodaTimeContextHolder.getFormatter(this.formatter, locale).parseDateTime(text);
   }
}
