package com.bea.core.repackaged.springframework.format.datetime;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.convert.converter.ConverterRegistry;
import com.bea.core.repackaged.springframework.format.FormatterRegistrar;
import com.bea.core.repackaged.springframework.format.FormatterRegistry;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import java.util.Calendar;
import java.util.Date;

public class DateFormatterRegistrar implements FormatterRegistrar {
   @Nullable
   private DateFormatter dateFormatter;

   public void setFormatter(DateFormatter dateFormatter) {
      Assert.notNull(dateFormatter, (String)"DateFormatter must not be null");
      this.dateFormatter = dateFormatter;
   }

   public void registerFormatters(FormatterRegistry registry) {
      addDateConverters(registry);
      if (this.dateFormatter != null) {
         registry.addFormatter(this.dateFormatter);
         registry.addFormatterForFieldType(Calendar.class, this.dateFormatter);
      }

      registry.addFormatterForFieldAnnotation(new DateTimeFormatAnnotationFormatterFactory());
   }

   public static void addDateConverters(ConverterRegistry converterRegistry) {
      converterRegistry.addConverter((Converter)(new DateToLongConverter()));
      converterRegistry.addConverter((Converter)(new DateToCalendarConverter()));
      converterRegistry.addConverter((Converter)(new CalendarToDateConverter()));
      converterRegistry.addConverter((Converter)(new CalendarToLongConverter()));
      converterRegistry.addConverter((Converter)(new LongToDateConverter()));
      converterRegistry.addConverter((Converter)(new LongToCalendarConverter()));
   }

   private static class LongToCalendarConverter implements Converter {
      private LongToCalendarConverter() {
      }

      public Calendar convert(Long source) {
         Calendar calendar = Calendar.getInstance();
         calendar.setTimeInMillis(source);
         return calendar;
      }

      // $FF: synthetic method
      LongToCalendarConverter(Object x0) {
         this();
      }
   }

   private static class LongToDateConverter implements Converter {
      private LongToDateConverter() {
      }

      public Date convert(Long source) {
         return new Date(source);
      }

      // $FF: synthetic method
      LongToDateConverter(Object x0) {
         this();
      }
   }

   private static class CalendarToLongConverter implements Converter {
      private CalendarToLongConverter() {
      }

      public Long convert(Calendar source) {
         return source.getTimeInMillis();
      }

      // $FF: synthetic method
      CalendarToLongConverter(Object x0) {
         this();
      }
   }

   private static class CalendarToDateConverter implements Converter {
      private CalendarToDateConverter() {
      }

      public Date convert(Calendar source) {
         return source.getTime();
      }

      // $FF: synthetic method
      CalendarToDateConverter(Object x0) {
         this();
      }
   }

   private static class DateToCalendarConverter implements Converter {
      private DateToCalendarConverter() {
      }

      public Calendar convert(Date source) {
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(source);
         return calendar;
      }

      // $FF: synthetic method
      DateToCalendarConverter(Object x0) {
         this();
      }
   }

   private static class DateToLongConverter implements Converter {
      private DateToLongConverter() {
      }

      public Long convert(Date source) {
         return source.getTime();
      }

      // $FF: synthetic method
      DateToLongConverter(Object x0) {
         this();
      }
   }
}
