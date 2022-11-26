package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.format.FormatterRegistrar;
import com.bea.core.repackaged.springframework.format.FormatterRegistry;
import com.bea.core.repackaged.springframework.format.Parser;
import com.bea.core.repackaged.springframework.format.Printer;
import com.bea.core.repackaged.springframework.format.annotation.DateTimeFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.Map;
import org.joda.time.Duration;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.MonthDay;
import org.joda.time.Period;
import org.joda.time.ReadableInstant;
import org.joda.time.YearMonth;
import org.joda.time.format.DateTimeFormatter;

public class JodaTimeFormatterRegistrar implements FormatterRegistrar {
   private final Map formatters = new EnumMap(Type.class);
   private final Map factories = new EnumMap(Type.class);

   public JodaTimeFormatterRegistrar() {
      Type[] var1 = JodaTimeFormatterRegistrar.Type.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Type type = var1[var3];
         this.factories.put(type, new DateTimeFormatterFactory());
      }

   }

   public void setUseIsoFormat(boolean useIsoFormat) {
      ((DateTimeFormatterFactory)this.factories.get(JodaTimeFormatterRegistrar.Type.DATE)).setIso(useIsoFormat ? DateTimeFormat.ISO.DATE : DateTimeFormat.ISO.NONE);
      ((DateTimeFormatterFactory)this.factories.get(JodaTimeFormatterRegistrar.Type.TIME)).setIso(useIsoFormat ? DateTimeFormat.ISO.TIME : DateTimeFormat.ISO.NONE);
      ((DateTimeFormatterFactory)this.factories.get(JodaTimeFormatterRegistrar.Type.DATE_TIME)).setIso(useIsoFormat ? DateTimeFormat.ISO.DATE_TIME : DateTimeFormat.ISO.NONE);
   }

   public void setDateStyle(String dateStyle) {
      ((DateTimeFormatterFactory)this.factories.get(JodaTimeFormatterRegistrar.Type.DATE)).setStyle(dateStyle + "-");
   }

   public void setTimeStyle(String timeStyle) {
      ((DateTimeFormatterFactory)this.factories.get(JodaTimeFormatterRegistrar.Type.TIME)).setStyle("-" + timeStyle);
   }

   public void setDateTimeStyle(String dateTimeStyle) {
      ((DateTimeFormatterFactory)this.factories.get(JodaTimeFormatterRegistrar.Type.DATE_TIME)).setStyle(dateTimeStyle);
   }

   public void setDateFormatter(DateTimeFormatter formatter) {
      this.formatters.put(JodaTimeFormatterRegistrar.Type.DATE, formatter);
   }

   public void setTimeFormatter(DateTimeFormatter formatter) {
      this.formatters.put(JodaTimeFormatterRegistrar.Type.TIME, formatter);
   }

   public void setDateTimeFormatter(DateTimeFormatter formatter) {
      this.formatters.put(JodaTimeFormatterRegistrar.Type.DATE_TIME, formatter);
   }

   public void registerFormatters(FormatterRegistry registry) {
      JodaTimeConverters.registerConverters(registry);
      DateTimeFormatter dateFormatter = this.getFormatter(JodaTimeFormatterRegistrar.Type.DATE);
      DateTimeFormatter timeFormatter = this.getFormatter(JodaTimeFormatterRegistrar.Type.TIME);
      DateTimeFormatter dateTimeFormatter = this.getFormatter(JodaTimeFormatterRegistrar.Type.DATE_TIME);
      this.addFormatterForFields(registry, new ReadablePartialPrinter(dateFormatter), new LocalDateParser(dateFormatter), LocalDate.class);
      this.addFormatterForFields(registry, new ReadablePartialPrinter(timeFormatter), new LocalTimeParser(timeFormatter), LocalTime.class);
      this.addFormatterForFields(registry, new ReadablePartialPrinter(dateTimeFormatter), new LocalDateTimeParser(dateTimeFormatter), LocalDateTime.class);
      this.addFormatterForFields(registry, new ReadableInstantPrinter(dateTimeFormatter), new DateTimeParser(dateTimeFormatter), ReadableInstant.class);
      if (this.formatters.containsKey(JodaTimeFormatterRegistrar.Type.DATE_TIME)) {
         this.addFormatterForFields(registry, new ReadableInstantPrinter(dateTimeFormatter), new DateTimeParser(dateTimeFormatter), Date.class, Calendar.class);
      }

      registry.addFormatterForFieldType(Period.class, new PeriodFormatter());
      registry.addFormatterForFieldType(Duration.class, new DurationFormatter());
      registry.addFormatterForFieldType(YearMonth.class, new YearMonthFormatter());
      registry.addFormatterForFieldType(MonthDay.class, new MonthDayFormatter());
      registry.addFormatterForFieldAnnotation(new JodaDateTimeFormatAnnotationFormatterFactory());
   }

   private DateTimeFormatter getFormatter(Type type) {
      DateTimeFormatter formatter = (DateTimeFormatter)this.formatters.get(type);
      if (formatter != null) {
         return formatter;
      } else {
         DateTimeFormatter fallbackFormatter = this.getFallbackFormatter(type);
         return ((DateTimeFormatterFactory)this.factories.get(type)).createDateTimeFormatter(fallbackFormatter);
      }
   }

   private DateTimeFormatter getFallbackFormatter(Type type) {
      switch (type) {
         case DATE:
            return org.joda.time.format.DateTimeFormat.shortDate();
         case TIME:
            return org.joda.time.format.DateTimeFormat.shortTime();
         default:
            return org.joda.time.format.DateTimeFormat.shortDateTime();
      }
   }

   private void addFormatterForFields(FormatterRegistry registry, Printer printer, Parser parser, Class... fieldTypes) {
      Class[] var5 = fieldTypes;
      int var6 = fieldTypes.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Class fieldType = var5[var7];
         registry.addFormatterForFieldType(fieldType, printer, parser);
      }

   }

   private static enum Type {
      DATE,
      TIME,
      DATE_TIME;
   }
}
