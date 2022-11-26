package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.convert.converter.ConverterRegistry;
import com.bea.core.repackaged.springframework.format.datetime.DateFormatterRegistrar;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

final class DateTimeConverters {
   private DateTimeConverters() {
   }

   public static void registerConverters(ConverterRegistry registry) {
      DateFormatterRegistrar.addDateConverters(registry);
      registry.addConverter((Converter)(new LocalDateTimeToLocalDateConverter()));
      registry.addConverter((Converter)(new LocalDateTimeToLocalTimeConverter()));
      registry.addConverter((Converter)(new ZonedDateTimeToLocalDateConverter()));
      registry.addConverter((Converter)(new ZonedDateTimeToLocalTimeConverter()));
      registry.addConverter((Converter)(new ZonedDateTimeToLocalDateTimeConverter()));
      registry.addConverter((Converter)(new ZonedDateTimeToOffsetDateTimeConverter()));
      registry.addConverter((Converter)(new ZonedDateTimeToInstantConverter()));
      registry.addConverter((Converter)(new OffsetDateTimeToLocalDateConverter()));
      registry.addConverter((Converter)(new OffsetDateTimeToLocalTimeConverter()));
      registry.addConverter((Converter)(new OffsetDateTimeToLocalDateTimeConverter()));
      registry.addConverter((Converter)(new OffsetDateTimeToZonedDateTimeConverter()));
      registry.addConverter((Converter)(new OffsetDateTimeToInstantConverter()));
      registry.addConverter((Converter)(new CalendarToZonedDateTimeConverter()));
      registry.addConverter((Converter)(new CalendarToOffsetDateTimeConverter()));
      registry.addConverter((Converter)(new CalendarToLocalDateConverter()));
      registry.addConverter((Converter)(new CalendarToLocalTimeConverter()));
      registry.addConverter((Converter)(new CalendarToLocalDateTimeConverter()));
      registry.addConverter((Converter)(new CalendarToInstantConverter()));
      registry.addConverter((Converter)(new LongToInstantConverter()));
      registry.addConverter((Converter)(new InstantToLongConverter()));
   }

   private static ZonedDateTime calendarToZonedDateTime(Calendar source) {
      return source instanceof GregorianCalendar ? ((GregorianCalendar)source).toZonedDateTime() : ZonedDateTime.ofInstant(Instant.ofEpochMilli(source.getTimeInMillis()), source.getTimeZone().toZoneId());
   }

   private static class InstantToLongConverter implements Converter {
      private InstantToLongConverter() {
      }

      public Long convert(Instant source) {
         return source.toEpochMilli();
      }

      // $FF: synthetic method
      InstantToLongConverter(Object x0) {
         this();
      }
   }

   private static class LongToInstantConverter implements Converter {
      private LongToInstantConverter() {
      }

      public Instant convert(Long source) {
         return Instant.ofEpochMilli(source);
      }

      // $FF: synthetic method
      LongToInstantConverter(Object x0) {
         this();
      }
   }

   private static class CalendarToInstantConverter implements Converter {
      private CalendarToInstantConverter() {
      }

      public Instant convert(Calendar source) {
         return DateTimeConverters.calendarToZonedDateTime(source).toInstant();
      }

      // $FF: synthetic method
      CalendarToInstantConverter(Object x0) {
         this();
      }
   }

   private static class CalendarToLocalDateTimeConverter implements Converter {
      private CalendarToLocalDateTimeConverter() {
      }

      public LocalDateTime convert(Calendar source) {
         return DateTimeConverters.calendarToZonedDateTime(source).toLocalDateTime();
      }

      // $FF: synthetic method
      CalendarToLocalDateTimeConverter(Object x0) {
         this();
      }
   }

   private static class CalendarToLocalTimeConverter implements Converter {
      private CalendarToLocalTimeConverter() {
      }

      public LocalTime convert(Calendar source) {
         return DateTimeConverters.calendarToZonedDateTime(source).toLocalTime();
      }

      // $FF: synthetic method
      CalendarToLocalTimeConverter(Object x0) {
         this();
      }
   }

   private static class CalendarToLocalDateConverter implements Converter {
      private CalendarToLocalDateConverter() {
      }

      public LocalDate convert(Calendar source) {
         return DateTimeConverters.calendarToZonedDateTime(source).toLocalDate();
      }

      // $FF: synthetic method
      CalendarToLocalDateConverter(Object x0) {
         this();
      }
   }

   private static class CalendarToOffsetDateTimeConverter implements Converter {
      private CalendarToOffsetDateTimeConverter() {
      }

      public OffsetDateTime convert(Calendar source) {
         return DateTimeConverters.calendarToZonedDateTime(source).toOffsetDateTime();
      }

      // $FF: synthetic method
      CalendarToOffsetDateTimeConverter(Object x0) {
         this();
      }
   }

   private static class CalendarToZonedDateTimeConverter implements Converter {
      private CalendarToZonedDateTimeConverter() {
      }

      public ZonedDateTime convert(Calendar source) {
         return DateTimeConverters.calendarToZonedDateTime(source);
      }

      // $FF: synthetic method
      CalendarToZonedDateTimeConverter(Object x0) {
         this();
      }
   }

   private static class OffsetDateTimeToInstantConverter implements Converter {
      private OffsetDateTimeToInstantConverter() {
      }

      public Instant convert(OffsetDateTime source) {
         return source.toInstant();
      }

      // $FF: synthetic method
      OffsetDateTimeToInstantConverter(Object x0) {
         this();
      }
   }

   private static class OffsetDateTimeToZonedDateTimeConverter implements Converter {
      private OffsetDateTimeToZonedDateTimeConverter() {
      }

      public ZonedDateTime convert(OffsetDateTime source) {
         return source.toZonedDateTime();
      }

      // $FF: synthetic method
      OffsetDateTimeToZonedDateTimeConverter(Object x0) {
         this();
      }
   }

   private static class OffsetDateTimeToLocalDateTimeConverter implements Converter {
      private OffsetDateTimeToLocalDateTimeConverter() {
      }

      public LocalDateTime convert(OffsetDateTime source) {
         return source.toLocalDateTime();
      }

      // $FF: synthetic method
      OffsetDateTimeToLocalDateTimeConverter(Object x0) {
         this();
      }
   }

   private static class OffsetDateTimeToLocalTimeConverter implements Converter {
      private OffsetDateTimeToLocalTimeConverter() {
      }

      public LocalTime convert(OffsetDateTime source) {
         return source.toLocalTime();
      }

      // $FF: synthetic method
      OffsetDateTimeToLocalTimeConverter(Object x0) {
         this();
      }
   }

   private static class OffsetDateTimeToLocalDateConverter implements Converter {
      private OffsetDateTimeToLocalDateConverter() {
      }

      public LocalDate convert(OffsetDateTime source) {
         return source.toLocalDate();
      }

      // $FF: synthetic method
      OffsetDateTimeToLocalDateConverter(Object x0) {
         this();
      }
   }

   private static class ZonedDateTimeToInstantConverter implements Converter {
      private ZonedDateTimeToInstantConverter() {
      }

      public Instant convert(ZonedDateTime source) {
         return source.toInstant();
      }

      // $FF: synthetic method
      ZonedDateTimeToInstantConverter(Object x0) {
         this();
      }
   }

   private static class ZonedDateTimeToOffsetDateTimeConverter implements Converter {
      private ZonedDateTimeToOffsetDateTimeConverter() {
      }

      public OffsetDateTime convert(ZonedDateTime source) {
         return source.toOffsetDateTime();
      }

      // $FF: synthetic method
      ZonedDateTimeToOffsetDateTimeConverter(Object x0) {
         this();
      }
   }

   private static class ZonedDateTimeToLocalDateTimeConverter implements Converter {
      private ZonedDateTimeToLocalDateTimeConverter() {
      }

      public LocalDateTime convert(ZonedDateTime source) {
         return source.toLocalDateTime();
      }

      // $FF: synthetic method
      ZonedDateTimeToLocalDateTimeConverter(Object x0) {
         this();
      }
   }

   private static class ZonedDateTimeToLocalTimeConverter implements Converter {
      private ZonedDateTimeToLocalTimeConverter() {
      }

      public LocalTime convert(ZonedDateTime source) {
         return source.toLocalTime();
      }

      // $FF: synthetic method
      ZonedDateTimeToLocalTimeConverter(Object x0) {
         this();
      }
   }

   private static class ZonedDateTimeToLocalDateConverter implements Converter {
      private ZonedDateTimeToLocalDateConverter() {
      }

      public LocalDate convert(ZonedDateTime source) {
         return source.toLocalDate();
      }

      // $FF: synthetic method
      ZonedDateTimeToLocalDateConverter(Object x0) {
         this();
      }
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
}
