package com.bea.core.repackaged.springframework.format.datetime;

import com.bea.core.repackaged.springframework.context.support.EmbeddedValueResolutionSupport;
import com.bea.core.repackaged.springframework.format.AnnotationFormatterFactory;
import com.bea.core.repackaged.springframework.format.Formatter;
import com.bea.core.repackaged.springframework.format.Parser;
import com.bea.core.repackaged.springframework.format.Printer;
import com.bea.core.repackaged.springframework.format.annotation.DateTimeFormat;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class DateTimeFormatAnnotationFormatterFactory extends EmbeddedValueResolutionSupport implements AnnotationFormatterFactory {
   private static final Set FIELD_TYPES;

   public Set getFieldTypes() {
      return FIELD_TYPES;
   }

   public Printer getPrinter(DateTimeFormat annotation, Class fieldType) {
      return this.getFormatter(annotation, fieldType);
   }

   public Parser getParser(DateTimeFormat annotation, Class fieldType) {
      return this.getFormatter(annotation, fieldType);
   }

   protected Formatter getFormatter(DateTimeFormat annotation, Class fieldType) {
      DateFormatter formatter = new DateFormatter();
      String style = this.resolveEmbeddedValue(annotation.style());
      if (StringUtils.hasLength(style)) {
         formatter.setStylePattern(style);
      }

      formatter.setIso(annotation.iso());
      String pattern = this.resolveEmbeddedValue(annotation.pattern());
      if (StringUtils.hasLength(pattern)) {
         formatter.setPattern(pattern);
      }

      return formatter;
   }

   static {
      Set fieldTypes = new HashSet(4);
      fieldTypes.add(Date.class);
      fieldTypes.add(Calendar.class);
      fieldTypes.add(Long.class);
      FIELD_TYPES = Collections.unmodifiableSet(fieldTypes);
   }
}
