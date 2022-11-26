package com.googlecode.cqengine.query.parser.cqn.support;

import com.googlecode.cqengine.query.parser.common.ValueParser;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateMathParser extends ValueParser {
   final TimeZone timeZone;
   final Locale locale;
   final Date now;

   public DateMathParser() {
      this(ApacheSolrDataMathParser.DEFAULT_MATH_TZ, ApacheSolrDataMathParser.DEFAULT_MATH_LOCALE, (Date)null);
   }

   public DateMathParser(Date now) {
      this(ApacheSolrDataMathParser.DEFAULT_MATH_TZ, ApacheSolrDataMathParser.DEFAULT_MATH_LOCALE, now);
   }

   public DateMathParser(TimeZone timeZone, Locale locale) {
      this(timeZone, locale, (Date)null);
   }

   public DateMathParser(TimeZone timeZone, Locale locale, Date now) {
      this.timeZone = timeZone;
      this.locale = locale;
      this.now = now;
   }

   protected Date parse(Class valueType, String stringValue) {
      try {
         ApacheSolrDataMathParser solrParser = new ApacheSolrDataMathParser();
         if (this.now != null) {
            solrParser.setNow(this.now);
         }

         return solrParser.parseMath(this.stripQuotes(stringValue));
      } catch (Exception var4) {
         throw new IllegalStateException("Failed to parse date math expression: " + stringValue, var4);
      }
   }

   protected String stripQuotes(String stringValue) {
      return StringParser.stripQuotes(stringValue);
   }
}
