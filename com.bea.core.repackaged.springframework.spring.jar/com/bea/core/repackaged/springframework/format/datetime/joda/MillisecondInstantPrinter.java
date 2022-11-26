package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.format.Printer;
import java.util.Locale;
import org.joda.time.format.DateTimeFormatter;

public final class MillisecondInstantPrinter implements Printer {
   private final DateTimeFormatter formatter;

   public MillisecondInstantPrinter(DateTimeFormatter formatter) {
      this.formatter = formatter;
   }

   public String print(Long instant, Locale locale) {
      return JodaTimeContextHolder.getFormatter(this.formatter, locale).print(instant);
   }
}
