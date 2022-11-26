package com.bea.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import weblogic.utils.string.CachingDateFormat;
import weblogic.utils.string.SimpleCachingDateFormat;

public final class DateFormatter {
   private DateFormat dateFormat;
   private CachingDateFormat cachingDateFormat;

   private static CachingDateFormat createCachingDateFormat(DateFormat df) {
      try {
         if (df instanceof SimpleDateFormat) {
            String pattern = ((SimpleDateFormat)df).toPattern();
            return new SimpleCachingDateFormat(pattern);
         } else {
            return null;
         }
      } catch (Exception var2) {
         return null;
      }
   }

   public static final DateFormatter getDefaultInstance() {
      return DateFormatter.DefaultSingletonFactory.DEFAULT_SINGLETON;
   }

   private DateFormatter() {
      this.cachingDateFormat = null;
      this.dateFormat = DateFormat.getDateTimeInstance(2, 0, Locale.getDefault());
      if (this.dateFormat instanceof SimpleDateFormat) {
         SimpleDateFormat sf = (SimpleDateFormat)this.dateFormat;
         String millisDatetimePattern = includeMillis(sf.toPattern());
         this.dateFormat = new SimpleDateFormat(millisDatetimePattern);
      } else {
         this.cachingDateFormat = createCachingDateFormat(this.dateFormat);
      }

   }

   static String includeMillis(String pattern) {
      int secondsIndex = pattern.lastIndexOf(115);
      if (secondsIndex > -1) {
         StringBuilder sb = new StringBuilder(pattern);
         sb.insert(secondsIndex + 1, ",SSS");
         return sb.toString();
      } else {
         return pattern;
      }
   }

   public String formatDate(Date date) {
      return this.cachingDateFormat != null ? this.cachingDateFormat.getDate(date.getTime()) : this.dateFormat.format(date);
   }

   public DateFormatter(String pattern) {
      this.cachingDateFormat = null;
      this.dateFormat = new SimpleDateFormat(pattern);
      this.cachingDateFormat = createCachingDateFormat(this.dateFormat);
   }

   public static String getDefaultDateFormatPattern() {
      return getDefaultInstance().dateFormat instanceof SimpleDateFormat ? ((SimpleDateFormat)getDefaultInstance().dateFormat).toPattern() : "MMM d, yyyy h:mm:ss,S a z";
   }

   public static void validateDateFormatPattern(String pattern) {
      new SimpleDateFormat(pattern);
   }

   // $FF: synthetic method
   DateFormatter(Object x0) {
      this();
   }

   private static class DefaultSingletonFactory {
      private static final DateFormatter DEFAULT_SINGLETON = new DateFormatter();
   }
}
