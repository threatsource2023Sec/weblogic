package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;

final class ObjectToStringConverter implements Converter {
   public String convert(Object source) {
      return source.toString();
   }
}
