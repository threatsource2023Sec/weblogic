package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.core.convert.converter.ConditionalConverter;
import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.convert.converter.ConverterFactory;
import com.bea.core.repackaged.springframework.util.NumberUtils;

final class NumberToNumberConverterFactory implements ConverterFactory, ConditionalConverter {
   public Converter getConverter(Class targetType) {
      return new NumberToNumber(targetType);
   }

   public boolean matches(TypeDescriptor sourceType, TypeDescriptor targetType) {
      return !sourceType.equals(targetType);
   }

   private static final class NumberToNumber implements Converter {
      private final Class targetType;

      public NumberToNumber(Class targetType) {
         this.targetType = targetType;
      }

      public Number convert(Number source) {
         return NumberUtils.convertNumberToTargetClass(source, this.targetType);
      }
   }
}
