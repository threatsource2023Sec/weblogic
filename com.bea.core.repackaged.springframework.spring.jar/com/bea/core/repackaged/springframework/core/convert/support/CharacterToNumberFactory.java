package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.core.convert.converter.ConverterFactory;
import com.bea.core.repackaged.springframework.util.NumberUtils;

final class CharacterToNumberFactory implements ConverterFactory {
   public Converter getConverter(Class targetType) {
      return new CharacterToNumber(targetType);
   }

   private static final class CharacterToNumber implements Converter {
      private final Class targetType;

      public CharacterToNumber(Class targetType) {
         this.targetType = targetType;
      }

      public Number convert(Character source) {
         return NumberUtils.convertNumberToTargetClass((short)source, this.targetType);
      }
   }
}
