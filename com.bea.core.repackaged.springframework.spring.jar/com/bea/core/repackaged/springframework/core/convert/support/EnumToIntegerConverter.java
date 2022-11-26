package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.converter.Converter;

final class EnumToIntegerConverter extends AbstractConditionalEnumConverter implements Converter {
   public EnumToIntegerConverter(ConversionService conversionService) {
      super(conversionService);
   }

   public Integer convert(Enum source) {
      return source.ordinal();
   }
}
