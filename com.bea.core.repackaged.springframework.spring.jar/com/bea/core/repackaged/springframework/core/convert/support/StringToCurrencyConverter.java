package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import java.util.Currency;

class StringToCurrencyConverter implements Converter {
   public Currency convert(String source) {
      return Currency.getInstance(source);
   }
}
