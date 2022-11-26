package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.UUID;

final class StringToUUIDConverter implements Converter {
   public UUID convert(String source) {
      return StringUtils.hasLength(source) ? UUID.fromString(source.trim()) : null;
   }
}
