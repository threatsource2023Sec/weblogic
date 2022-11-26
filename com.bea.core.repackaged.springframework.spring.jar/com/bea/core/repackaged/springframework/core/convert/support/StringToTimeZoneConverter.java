package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.TimeZone;

class StringToTimeZoneConverter implements Converter {
   public TimeZone convert(String source) {
      return StringUtils.parseTimeZoneString(source);
   }
}
