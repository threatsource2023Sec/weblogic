package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.context.support.EmbeddedValueResolutionSupport;
import com.bea.core.repackaged.springframework.format.AnnotationFormatterFactory;
import com.bea.core.repackaged.springframework.format.Parser;
import com.bea.core.repackaged.springframework.format.Printer;
import com.bea.core.repackaged.springframework.format.annotation.DateTimeFormat;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormatter;

public class JodaDateTimeFormatAnnotationFormatterFactory extends EmbeddedValueResolutionSupport implements AnnotationFormatterFactory {
   private static final Set FIELD_TYPES;

   public final Set getFieldTypes() {
      return FIELD_TYPES;
   }

   public Printer getPrinter(DateTimeFormat annotation, Class fieldType) {
      DateTimeFormatter formatter = this.getFormatter(annotation, fieldType);
      if (ReadablePartial.class.isAssignableFrom(fieldType)) {
         return new ReadablePartialPrinter(formatter);
      } else {
         return (Printer)(!ReadableInstant.class.isAssignableFrom(fieldType) && !Calendar.class.isAssignableFrom(fieldType) ? new MillisecondInstantPrinter(formatter) : new ReadableInstantPrinter(formatter));
      }
   }

   public Parser getParser(DateTimeFormat annotation, Class fieldType) {
      if (LocalDate.class == fieldType) {
         return new LocalDateParser(this.getFormatter(annotation, fieldType));
      } else if (LocalTime.class == fieldType) {
         return new LocalTimeParser(this.getFormatter(annotation, fieldType));
      } else {
         return (Parser)(LocalDateTime.class == fieldType ? new LocalDateTimeParser(this.getFormatter(annotation, fieldType)) : new DateTimeParser(this.getFormatter(annotation, fieldType)));
      }
   }

   protected DateTimeFormatter getFormatter(DateTimeFormat annotation, Class fieldType) {
      DateTimeFormatterFactory factory = new DateTimeFormatterFactory();
      String style = this.resolveEmbeddedValue(annotation.style());
      if (StringUtils.hasLength(style)) {
         factory.setStyle(style);
      }

      factory.setIso(annotation.iso());
      String pattern = this.resolveEmbeddedValue(annotation.pattern());
      if (StringUtils.hasLength(pattern)) {
         factory.setPattern(pattern);
      }

      return factory.createDateTimeFormatter();
   }

   static {
      Set fieldTypes = new HashSet(8);
      fieldTypes.add(ReadableInstant.class);
      fieldTypes.add(LocalDate.class);
      fieldTypes.add(LocalTime.class);
      fieldTypes.add(LocalDateTime.class);
      fieldTypes.add(Date.class);
      fieldTypes.add(Calendar.class);
      fieldTypes.add(Long.class);
      FIELD_TYPES = Collections.unmodifiableSet(fieldTypes);
   }
}
