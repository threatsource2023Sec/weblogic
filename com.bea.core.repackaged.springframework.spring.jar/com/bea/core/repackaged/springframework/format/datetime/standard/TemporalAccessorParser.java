package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.format.Parser;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

public final class TemporalAccessorParser implements Parser {
   private final Class temporalAccessorType;
   private final DateTimeFormatter formatter;

   public TemporalAccessorParser(Class temporalAccessorType, DateTimeFormatter formatter) {
      this.temporalAccessorType = temporalAccessorType;
      this.formatter = formatter;
   }

   public TemporalAccessor parse(String text, Locale locale) throws ParseException {
      DateTimeFormatter formatterToUse = DateTimeContextHolder.getFormatter(this.formatter, locale);
      if (LocalDate.class == this.temporalAccessorType) {
         return LocalDate.parse(text, formatterToUse);
      } else if (LocalTime.class == this.temporalAccessorType) {
         return LocalTime.parse(text, formatterToUse);
      } else if (LocalDateTime.class == this.temporalAccessorType) {
         return LocalDateTime.parse(text, formatterToUse);
      } else if (ZonedDateTime.class == this.temporalAccessorType) {
         return ZonedDateTime.parse(text, formatterToUse);
      } else if (OffsetDateTime.class == this.temporalAccessorType) {
         return OffsetDateTime.parse(text, formatterToUse);
      } else if (OffsetTime.class == this.temporalAccessorType) {
         return OffsetTime.parse(text, formatterToUse);
      } else {
         throw new IllegalStateException("Unsupported TemporalAccessor type: " + this.temporalAccessorType);
      }
   }
}
