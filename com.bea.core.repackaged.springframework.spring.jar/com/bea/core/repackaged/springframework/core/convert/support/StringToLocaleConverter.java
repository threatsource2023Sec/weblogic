package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Locale;

final class StringToLocaleConverter implements Converter {
   @Nullable
   public Locale convert(String source) {
      return StringUtils.parseLocale(source);
   }
}
