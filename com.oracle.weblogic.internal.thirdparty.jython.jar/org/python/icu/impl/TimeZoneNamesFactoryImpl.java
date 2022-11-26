package org.python.icu.impl;

import org.python.icu.text.TimeZoneNames;
import org.python.icu.util.ULocale;

public class TimeZoneNamesFactoryImpl extends TimeZoneNames.Factory {
   public TimeZoneNames getTimeZoneNames(ULocale locale) {
      return new TimeZoneNamesImpl(locale);
   }
}
