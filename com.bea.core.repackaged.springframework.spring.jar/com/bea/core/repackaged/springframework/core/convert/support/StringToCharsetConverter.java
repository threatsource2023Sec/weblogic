package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import java.nio.charset.Charset;

class StringToCharsetConverter implements Converter {
   public Charset convert(String source) {
      return Charset.forName(source);
   }
}
