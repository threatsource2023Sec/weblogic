package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import java.time.ZoneId;
import java.util.TimeZone;

final class ZoneIdToTimeZoneConverter implements Converter {
   public TimeZone convert(ZoneId source) {
      return TimeZone.getTimeZone(source);
   }
}
