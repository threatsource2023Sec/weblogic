package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;

final class NumberToCharacterConverter implements Converter {
   public Character convert(Number source) {
      return (char)source.shortValue();
   }
}
