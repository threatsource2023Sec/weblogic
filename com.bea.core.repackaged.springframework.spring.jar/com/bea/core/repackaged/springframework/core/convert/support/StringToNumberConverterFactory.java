package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.convert.converter.ConverterFactory;
import com.bea.core.repackaged.springframework.util.NumberUtils;

final class StringToNumberConverterFactory implements ConverterFactory {
   public Converter getConverter(Class targetType) {
      return new StringToNumber(targetType);
   }

   private static final class StringToNumber implements Converter {
      private final Class targetType;

      public StringToNumber(Class targetType) {
         this.targetType = targetType;
      }

      public Number convert(String source) {
         return source.isEmpty() ? null : NumberUtils.parseNumber(source, this.targetType);
      }
   }
}
