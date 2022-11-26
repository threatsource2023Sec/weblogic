package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.format.Printer;
import java.util.Locale;
import org.joda.time.ReadableInstant;
import org.joda.time.format.DateTimeFormatter;

public final class ReadableInstantPrinter implements Printer {
   private final DateTimeFormatter formatter;

   public ReadableInstantPrinter(DateTimeFormatter formatter) {
      this.formatter = formatter;
   }

   public String print(ReadableInstant instant, Locale locale) {
      return JodaTimeContextHolder.getFormatter(this.formatter, locale).print(instant);
   }
}
