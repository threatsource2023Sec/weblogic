package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.format.Printer;
import java.util.Locale;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormatter;

public final class ReadablePartialPrinter implements Printer {
   private final DateTimeFormatter formatter;

   public ReadablePartialPrinter(DateTimeFormatter formatter) {
      this.formatter = formatter;
   }

   public String print(ReadablePartial partial, Locale locale) {
      return JodaTimeContextHolder.getFormatter(this.formatter, locale).print(partial);
   }
}
