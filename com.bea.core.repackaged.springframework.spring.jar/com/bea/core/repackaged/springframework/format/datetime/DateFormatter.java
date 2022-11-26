package com.bea.core.repackaged.springframework.format.datetime;

import com.bea.core.repackaged.springframework.format.Formatter;
import com.bea.core.repackaged.springframework.format.annotation.DateTimeFormat;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class DateFormatter implements Formatter {
   private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
   private static final Map ISO_PATTERNS;
   @Nullable
   private String pattern;
   private int style = 2;
   @Nullable
   private String stylePattern;
   @Nullable
   private DateTimeFormat.ISO iso;
   @Nullable
   private TimeZone timeZone;
   private boolean lenient = false;

   public DateFormatter() {
   }

   public DateFormatter(String pattern) {
      this.pattern = pattern;
   }

   public void setPattern(String pattern) {
      this.pattern = pattern;
   }

   public void setIso(DateTimeFormat.ISO iso) {
      this.iso = iso;
   }

   public void setStyle(int style) {
      this.style = style;
   }

   public void setStylePattern(String stylePattern) {
      this.stylePattern = stylePattern;
   }

   public void setTimeZone(TimeZone timeZone) {
      this.timeZone = timeZone;
   }

   public void setLenient(boolean lenient) {
      this.lenient = lenient;
   }

   public String print(Date date, Locale locale) {
      return this.getDateFormat(locale).format(date);
   }

   public Date parse(String text, Locale locale) throws ParseException {
      return this.getDateFormat(locale).parse(text);
   }

   protected DateFormat getDateFormat(Locale locale) {
      DateFormat dateFormat = this.createDateFormat(locale);
      if (this.timeZone != null) {
         dateFormat.setTimeZone(this.timeZone);
      }

      dateFormat.setLenient(this.lenient);
      return dateFormat;
   }

   private DateFormat createDateFormat(Locale locale) {
      if (StringUtils.hasLength(this.pattern)) {
         return new SimpleDateFormat(this.pattern, locale);
      } else if (this.iso != null && this.iso != DateTimeFormat.ISO.NONE) {
         String pattern = (String)ISO_PATTERNS.get(this.iso);
         if (pattern == null) {
            throw new IllegalStateException("Unsupported ISO format " + this.iso);
         } else {
            SimpleDateFormat format = new SimpleDateFormat(pattern);
            format.setTimeZone(UTC);
            return format;
         }
      } else if (StringUtils.hasLength(this.stylePattern)) {
         int dateStyle = this.getStylePatternForChar(0);
         int timeStyle = this.getStylePatternForChar(1);
         if (dateStyle != -1 && timeStyle != -1) {
            return DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);
         } else if (dateStyle != -1) {
            return DateFormat.getDateInstance(dateStyle, locale);
         } else if (timeStyle != -1) {
            return DateFormat.getTimeInstance(timeStyle, locale);
         } else {
            throw new IllegalStateException("Unsupported style pattern '" + this.stylePattern + "'");
         }
      } else {
         return DateFormat.getDateInstance(this.style, locale);
      }
   }

   private int getStylePatternForChar(int index) {
      if (this.stylePattern != null && this.stylePattern.length() > index) {
         switch (this.stylePattern.charAt(index)) {
            case '-':
               return -1;
            case 'F':
               return 0;
            case 'L':
               return 1;
            case 'M':
               return 2;
            case 'S':
               return 3;
         }
      }

      throw new IllegalStateException("Unsupported style pattern '" + this.stylePattern + "'");
   }

   static {
      Map formats = new EnumMap(DateTimeFormat.ISO.class);
      formats.put(DateTimeFormat.ISO.DATE, "yyyy-MM-dd");
      formats.put(DateTimeFormat.ISO.TIME, "HH:mm:ss.SSSXXX");
      formats.put(DateTimeFormat.ISO.DATE_TIME, "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
      ISO_PATTERNS = Collections.unmodifiableMap(formats);
   }
}
