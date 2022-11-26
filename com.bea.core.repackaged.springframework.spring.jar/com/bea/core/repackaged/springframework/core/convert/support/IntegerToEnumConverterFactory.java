package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.convert.converter.ConverterFactory;

final class IntegerToEnumConverterFactory implements ConverterFactory {
   public Converter getConverter(Class targetType) {
      return new IntegerToEnum(ConversionUtils.getEnumType(targetType));
   }

   private static class IntegerToEnum implements Converter {
      private final Class enumType;

      public IntegerToEnum(Class enumType) {
         this.enumType = enumType;
      }

      public Enum convert(Integer source) {
         return ((Enum[])this.enumType.getEnumConstants())[source];
      }
   }
}
