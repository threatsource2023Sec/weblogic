package com.bea.core.repackaged.springframework.core.convert.support;

import com.bea.core.repackaged.springframework.core.convert.converter.Converter;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;

final class ZonedDateTimeToCalendarConverter implements Converter {
   public Calendar convert(ZonedDateTime source) {
      return GregorianCalendar.from(source);
   }
}
