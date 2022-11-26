package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.format.Printer;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public final class TemporalAccessorPrinter implements Printer {
   private final DateTimeFormatter formatter;

   public TemporalAccessorPrinter(DateTimeFormatter formatter) {
      this.formatter = formatter;
   }

   public String print(TemporalAccessor partial, Locale locale) {
      return DateTimeContextHolder.getFormatter(this.formatter, locale).format(partial);
   }
}
