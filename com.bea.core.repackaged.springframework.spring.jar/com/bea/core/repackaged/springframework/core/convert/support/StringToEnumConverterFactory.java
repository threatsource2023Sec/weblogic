package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.convert.converter.ConverterFactory;

final class StringToEnumConverterFactory implements ConverterFactory {
   public Converter getConverter(Class targetType) {
      return new StringToEnum(ConversionUtils.getEnumType(targetType));
   }

   private static class StringToEnum implements Converter {
      private final Class enumType;

      public StringToEnum(Class enumType) {
         this.enumType = enumType;
      }

      public Enum convert(String source) {
         return source.isEmpty() ? null : Enum.valueOf(this.enumType, source.trim());
      }
   }
}
