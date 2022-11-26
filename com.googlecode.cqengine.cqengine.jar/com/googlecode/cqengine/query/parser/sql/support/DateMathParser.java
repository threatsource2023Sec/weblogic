package com.googlecode.cqengine.query.parser.sql.support;

import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateMathParser extends com.googlecode.cqengine.query.parser.cqn.support.DateMathParser {
   public DateMathParser() {
   }

   public DateMathParser(Date now) {
      super(now);
   }

   public DateMathParser(TimeZone timeZone, Locale locale) {
      super(timeZone, locale);
   }

   public DateMathParser(TimeZone timeZone, Locale locale, Date now) {
      super(timeZone, locale, now);
   }

   protected String stripQuotes(String stringValue) {
      return StringParser.stripQuotes(stringValue);
   }
}
