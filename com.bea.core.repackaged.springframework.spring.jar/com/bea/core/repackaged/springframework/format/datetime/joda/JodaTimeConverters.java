package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.convert.converter.ConverterRegistry;
import com.bea.core.repackaged.springframework.format.datetime.DateFormatterRegistrar;
import java.util.Calendar;
import java.util.Date;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.MutableDateTime;
import org.joda.time.ReadableInstant;

final class JodaTimeConverters {
   private JodaTimeConverters() {
   }

   public static void registerConverters(ConverterRegistry registry) {
      DateFormatterRegistrar.addDateConverters(registry);
      registry.addConverter((Converter)(new DateTimeToLocalDateConverter()));
      registry.addConverter((Converter)(new DateTimeToLocalTimeConverter()));
      registry.addConverter((Converter)(new DateTimeToLocalDateTimeConverter()));
      registry.addConverter((Converter)(new DateTimeToDateMidnightConverter()));
      registry.addConverter((Converter)(new DateTimeToMutableDateTimeConverter()));
      registry.addConverter((Converter)(new DateTimeToInstantConverter()));
      registry.addConverter((Converter)(new DateTimeToDateConverter()));
      registry.addConverter((Converter)(new DateTimeToCalendarConverter()));
      registry.addConverter((Converter)(new DateTimeToLongConverter()));
      registry.addConverter((Converter)(new DateToReadableInstantConverter()));
      registry.addConverter((Converter)(new CalendarToReadableInstantConverter()));
      registry.addConverter((Converter)(new LongToReadableInstantConverter()));
      registry.addConverter((Converter)(new LocalDateTimeToLocalDateConverter()));
      registry.addConverter((Converter)(new LocalDateTimeToLocalTimeConverter()));
   }

   private static class LocalDateTimeToLocalTimeConverter implements Converter {
      private LocalDateTimeToLocalTimeConverter() {
      }

      public LocalTime convert(LocalDateTime source) {
         return source.toLocalTime();
      }

      // $FF: synthetic method
      LocalDateTimeToLocalTimeConverter(Object x0) {
         this();
      }
   }

   private static class LocalDateTimeToLocalDateConverter implements Converter {
      private LocalDateTimeToLocalDateConverter() {
      }

      public LocalDate convert(LocalDateTime source) {
         return source.toLocalDate();
      }

      // $FF: synthetic method
      LocalDateTimeToLocalDateConverter(Object x0) {
         this();
      }
   }

   private static class LongToReadableInstantConverter implements Converter {
      private LongToReadableInstantConverter() {
      }

      public ReadableInstant convert(Long source) {
         return new DateTime(source);
      }

      // $FF: synthetic method
      LongToReadableInstantConverter(Object x0) {
         this();
      }
   }

   private static class CalendarToReadableInstantConverter implements Converter {
      private CalendarToReadableInstantConverter() {
      }

      public ReadableInstant convert(Calendar source) {
         return new DateTime(source);
      }

      // $FF: synthetic method
      CalendarToReadableInstantConverter(Object x0) {
         this();
      }
   }

   private static class DateToReadableInstantConverter implements Converter {
      private DateToReadableInstantConverter() {
      }

      public ReadableInstant convert(Date source) {
         return new DateTime(source);
      }

      // $FF: synthetic method
      DateToReadableInstantConverter(Object x0) {
         this();
      }
   }

   private static class DateTimeToLongConverter implements Converter {
      private DateTimeToLongConverter() {
      }

      public Long convert(DateTime source) {
         return source.getMillis();
      }

      // $FF: synthetic method
      DateTimeToLongConverter(Object x0) {
         this();
      }
   }

   private static class DateTimeToCalendarConverter implements Converter {
      private DateTimeToCalendarConverter() {
      }

      public Calendar convert(DateTime source) {
         return source.toGregorianCalendar();
      }

      // $FF: synthetic method
      DateTimeToCalendarConverter(Object x0) {
         this();
      }
   }

   private static class DateTimeToDateConverter implements Converter {
      private DateTimeToDateConverter() {
      }

      public Date convert(DateTime source) {
         return source.toDate();
      }

      // $FF: synthetic method
      DateTimeToDateConverter(Object x0) {
         this();
      }
   }

   private static class DateTimeToInstantConverter implements Converter {
      private DateTimeToInstantConverter() {
      }

      public Instant convert(DateTime source) {
         return source.toInstant();
      }

      // $FF: synthetic method
      DateTimeToInstantConverter(Object x0) {
         this();
      }
   }

   private static class DateTimeToMutableDateTimeConverter implements Converter {
      private DateTimeToMutableDateTimeConverter() {
      }

      public MutableDateTime convert(DateTime source) {
         return source.toMutableDateTime();
      }

      // $FF: synthetic method
      DateTimeToMutableDateTimeConverter(Object x0) {
         this();
      }
   }

   /** @deprecated */
   @Deprecated
   private static class DateTimeToDateMidnightConverter implements Converter {
      private DateTimeToDateMidnightConverter() {
      }

      public DateMidnight convert(DateTime source) {
         return source.toDateMidnight();
      }

      // $FF: synthetic method
      DateTimeToDateMidnightConverter(Object x0) {
         this();
      }
   }

   private static class DateTimeToLocalDateTimeConverter implements Converter {
      private DateTimeToLocalDateTimeConverter() {
      }

      public LocalDateTime convert(DateTime source) {
         return source.toLocalDateTime();
      }

      // $FF: synthetic method
      DateTimeToLocalDateTimeConverter(Object x0) {
         this();
      }
   }

   private static class DateTimeToLocalTimeConverter implements Converter {
      private DateTimeToLocalTimeConverter() {
      }

      public LocalTime convert(DateTime source) {
         return source.toLocalTime();
      }

      // $FF: synthetic method
      DateTimeToLocalTimeConverter(Object x0) {
         this();
      }
   }

   private static class DateTimeToLocalDateConverter implements Converter {
      private DateTimeToLocalDateConverter() {
      }

      public LocalDate convert(DateTime source) {
         return source.toLocalDate();
      }

      // $FF: synthetic method
      DateTimeToLocalDateConverter(Object x0) {
         this();
      }
   }
}
