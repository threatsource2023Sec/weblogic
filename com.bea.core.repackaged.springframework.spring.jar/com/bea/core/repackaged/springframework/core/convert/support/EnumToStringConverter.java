package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.converter.Converter;

final class EnumToStringConverter extends AbstractConditionalEnumConverter implements Converter {
   public EnumToStringConverter(ConversionService conversionService) {
      super(conversionService);
   }

   public String convert(Enum source) {
      return source.name();
   }
}
