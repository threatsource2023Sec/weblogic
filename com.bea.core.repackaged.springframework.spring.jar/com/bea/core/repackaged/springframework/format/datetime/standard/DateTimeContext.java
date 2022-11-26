package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.context.i18n.LocaleContext;
import com.bea.core.repackaged.springframework.context.i18n.LocaleContextHolder;
import com.bea.core.repackaged.springframework.context.i18n.TimeZoneAwareLocaleContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.time.ZoneId;
import java.time.chrono.Chronology;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

public class DateTimeContext {
   @Nullable
   private Chronology chronology;
   @Nullable
   private ZoneId timeZone;

   public void setChronology(@Nullable Chronology chronology) {
      this.chronology = chronology;
   }

   @Nullable
   public Chronology getChronology() {
      return this.chronology;
   }

   public void setTimeZone(@Nullable ZoneId timeZone) {
      this.timeZone = timeZone;
   }

   @Nullable
   public ZoneId getTimeZone() {
      return this.timeZone;
   }

   public DateTimeFormatter getFormatter(DateTimeFormatter formatter) {
      if (this.chronology != null) {
         formatter = formatter.withChronology(this.chronology);
      }

      if (this.timeZone != null) {
         formatter = formatter.withZone(this.timeZone);
      } else {
         LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
         if (localeContext instanceof TimeZoneAwareLocaleContext) {
            TimeZone timeZone = ((TimeZoneAwareLocaleContext)localeContext).getTimeZone();
            if (timeZone != null) {
               formatter = formatter.withZone(timeZone.toZoneId());
            }
         }
      }

      return formatter;
   }
}
