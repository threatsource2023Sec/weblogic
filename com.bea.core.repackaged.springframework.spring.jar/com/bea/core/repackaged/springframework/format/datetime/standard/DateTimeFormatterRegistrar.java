package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.format.FormatterRegistrar;
import com.bea.core.repackaged.springframework.format.FormatterRegistry;
import com.bea.core.repackaged.springframework.format.annotation.DateTimeFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.EnumMap;
import java.util.Map;

public class DateTimeFormatterRegistrar implements FormatterRegistrar {
   private final Map formatters = new EnumMap(Type.class);
   private final Map factories = new EnumMap(Type.class);

   public DateTimeFormatterRegistrar() {
      Type[] var1 = DateTimeFormatterRegistrar.Type.values();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Type type = var1[var3];
         this.factories.put(type, new DateTimeFormatterFactory());
      }

   }

   public void setUseIsoFormat(boolean useIsoFormat) {
      ((DateTimeFormatterFactory)this.factories.get(DateTimeFormatterRegistrar.Type.DATE)).setIso(useIsoFormat ? DateTimeFormat.ISO.DATE : DateTimeFormat.ISO.NONE);
      ((DateTimeFormatterFactory)this.factories.get(DateTimeFormatterRegistrar.Type.TIME)).setIso(useIsoFormat ? DateTimeFormat.ISO.TIME : DateTimeFormat.ISO.NONE);
      ((DateTimeFormatterFactory)this.factories.get(DateTimeFormatterRegistrar.Type.DATE_TIME)).setIso(useIsoFormat ? DateTimeFormat.ISO.DATE_TIME : DateTimeFormat.ISO.NONE);
   }

   public void setDateStyle(FormatStyle dateStyle) {
      ((DateTimeFormatterFactory)this.factories.get(DateTimeFormatterRegistrar.Type.DATE)).setDateStyle(dateStyle);
   }

   public void setTimeStyle(FormatStyle timeStyle) {
      ((DateTimeFormatterFactory)this.factories.get(DateTimeFormatterRegistrar.Type.TIME)).setTimeStyle(timeStyle);
   }

   public void setDateTimeStyle(FormatStyle dateTimeStyle) {
      ((DateTimeFormatterFactory)this.factories.get(DateTimeFormatterRegistrar.Type.DATE_TIME)).setDateTimeStyle(dateTimeStyle);
   }

   public void setDateFormatter(DateTimeFormatter formatter) {
      this.formatters.put(DateTimeFormatterRegistrar.Type.DATE, formatter);
   }

   public void setTimeFormatter(DateTimeFormatter formatter) {
      this.formatters.put(DateTimeFormatterRegistrar.Type.TIME, formatter);
   }

   public void setDateTimeFormatter(DateTimeFormatter formatter) {
      this.formatters.put(DateTimeFormatterRegistrar.Type.DATE_TIME, formatter);
   }

   public void registerFormatters(FormatterRegistry registry) {
      DateTimeConverters.registerConverters(registry);
      DateTimeFormatter df = this.getFormatter(DateTimeFormatterRegistrar.Type.DATE);
      DateTimeFormatter tf = this.getFormatter(DateTimeFormatterRegistrar.Type.TIME);
      DateTimeFormatter dtf = this.getFormatter(DateTimeFormatterRegistrar.Type.DATE_TIME);
      registry.addFormatterForFieldType(LocalDate.class, new TemporalAccessorPrinter(df == DateTimeFormatter.ISO_DATE ? DateTimeFormatter.ISO_LOCAL_DATE : df), new TemporalAccessorParser(LocalDate.class, df));
      registry.addFormatterForFieldType(LocalTime.class, new TemporalAccessorPrinter(tf == DateTimeFormatter.ISO_TIME ? DateTimeFormatter.ISO_LOCAL_TIME : tf), new TemporalAccessorParser(LocalTime.class, tf));
      registry.addFormatterForFieldType(LocalDateTime.class, new TemporalAccessorPrinter(dtf == DateTimeFormatter.ISO_DATE_TIME ? DateTimeFormatter.ISO_LOCAL_DATE_TIME : dtf), new TemporalAccessorParser(LocalDateTime.class, dtf));
      registry.addFormatterForFieldType(ZonedDateTime.class, new TemporalAccessorPrinter(dtf), new TemporalAccessorParser(ZonedDateTime.class, dtf));
      registry.addFormatterForFieldType(OffsetDateTime.class, new TemporalAccessorPrinter(dtf), new TemporalAccessorParser(OffsetDateTime.class, dtf));
      registry.addFormatterForFieldType(OffsetTime.class, new TemporalAccessorPrinter(tf), new TemporalAccessorParser(OffsetTime.class, tf));
      registry.addFormatterForFieldType(Instant.class, new InstantFormatter());
      registry.addFormatterForFieldType(Period.class, new PeriodFormatter());
      registry.addFormatterForFieldType(Duration.class, new DurationFormatter());
      registry.addFormatterForFieldType(Year.class, new YearFormatter());
      registry.addFormatterForFieldType(Month.class, new MonthFormatter());
      registry.addFormatterForFieldType(YearMonth.class, new YearMonthFormatter());
      registry.addFormatterForFieldType(MonthDay.class, new MonthDayFormatter());
      registry.addFormatterForFieldAnnotation(new Jsr310DateTimeFormatAnnotationFormatterFactory());
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
            return DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
         case TIME:
            return DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
         default:
            return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT);
      }
   }

   private static enum Type {
      DATE,
      TIME,
      DATE_TIME;
   }
}
