package com.bea.core.repackaged.springframework.format.number;

import com.bea.core.repackaged.springframework.context.support.EmbeddedValueResolutionSupport;
import com.bea.core.repackaged.springframework.format.AnnotationFormatterFactory;
import com.bea.core.repackaged.springframework.format.Formatter;
import com.bea.core.repackaged.springframework.format.Parser;
import com.bea.core.repackaged.springframework.format.Printer;
import com.bea.core.repackaged.springframework.format.annotation.NumberFormat;
import com.bea.core.repackaged.springframework.util.NumberUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Set;

public class NumberFormatAnnotationFormatterFactory extends EmbeddedValueResolutionSupport implements AnnotationFormatterFactory {
   public Set getFieldTypes() {
      return NumberUtils.STANDARD_NUMBER_TYPES;
   }

   public Printer getPrinter(NumberFormat annotation, Class fieldType) {
      return this.configureFormatterFrom(annotation);
   }

   public Parser getParser(NumberFormat annotation, Class fieldType) {
      return this.configureFormatterFrom(annotation);
   }

   private Formatter configureFormatterFrom(NumberFormat annotation) {
      String pattern = this.resolveEmbeddedValue(annotation.pattern());
      if (StringUtils.hasLength(pattern)) {
         return new NumberStyleFormatter(pattern);
      } else {
         NumberFormat.Style style = annotation.style();
         if (style == NumberFormat.Style.CURRENCY) {
            return new CurrencyStyleFormatter();
         } else {
            return (Formatter)(style == NumberFormat.Style.PERCENT ? new PercentStyleFormatter() : new NumberStyleFormatter());
         }
      }
   }
}
