package com.bea.core.repackaged.springframework.format.datetime.standard;

import com.bea.core.repackaged.springframework.format.annotation.DateTimeFormat;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.ResolverStyle;
import java.util.TimeZone;

public class DateTimeFormatterFactory {
   @Nullable
   private String pattern;
   @Nullable
   private DateTimeFormat.ISO iso;
   @Nullable
   private FormatStyle dateStyle;
   @Nullable
   private FormatStyle timeStyle;
   @Nullable
   private TimeZone timeZone;

   public DateTimeFormatterFactory() {
   }

   public DateTimeFormatterFactory(String pattern) {
      this.pattern = pattern;
   }

   public void setPattern(String pattern) {
      this.pattern = pattern;
   }

   public void setIso(DateTimeFormat.ISO iso) {
      this.iso = iso;
   }

   public void setDateStyle(FormatStyle dateStyle) {
      this.dateStyle = dateStyle;
   }

   public void setTimeStyle(FormatStyle timeStyle) {
      this.timeStyle = timeStyle;
   }

   public void setDateTimeStyle(FormatStyle dateTimeStyle) {
      this.dateStyle = dateTimeStyle;
      this.timeStyle = dateTimeStyle;
   }

   public void setStylePattern(String style) {
      Assert.isTrue(style.length() == 2, "Style pattern must consist of two characters");
      this.dateStyle = this.convertStyleCharacter(style.charAt(0));
      this.timeStyle = this.convertStyleCharacter(style.charAt(1));
   }

   @Nullable
   private FormatStyle convertStyleCharacter(char c) {
      switch (c) {
         case '-':
            return null;
         case 'F':
            return FormatStyle.FULL;
         case 'L':
            return FormatStyle.LONG;
         case 'M':
            return FormatStyle.MEDIUM;
         case 'S':
            return FormatStyle.SHORT;
         default:
            throw new IllegalArgumentException("Invalid style character '" + c + "'");
      }
   }

   public void setTimeZone(TimeZone timeZone) {
      this.timeZone = timeZone;
   }

   public DateTimeFormatter createDateTimeFormatter() {
      return this.createDateTimeFormatter(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
   }

   public DateTimeFormatter createDateTimeFormatter(DateTimeFormatter fallbackFormatter) {
      DateTimeFormatter dateTimeFormatter = null;
      if (StringUtils.hasLength(this.pattern)) {
         String patternToUse = StringUtils.replace(this.pattern, "yy", "uu");
         dateTimeFormatter = DateTimeFormatter.ofPattern(patternToUse).withResolverStyle(ResolverStyle.STRICT);
      } else if (this.iso != null && this.iso != DateTimeFormat.ISO.NONE) {
         switch (this.iso) {
            case DATE:
               dateTimeFormatter = DateTimeFormatter.ISO_DATE;
               break;
            case TIME:
               dateTimeFormatter = DateTimeFormatter.ISO_TIME;
               break;
            case DATE_TIME:
               dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
               break;
            default:
               throw new IllegalStateException("Unsupported ISO format: " + this.iso);
         }
      } else if (this.dateStyle != null && this.timeStyle != null) {
         dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(this.dateStyle, this.timeStyle);
      } else if (this.dateStyle != null) {
         dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(this.dateStyle);
      } else if (this.timeStyle != null) {
         dateTimeFormatter = DateTimeFormatter.ofLocalizedTime(this.timeStyle);
      }

      if (dateTimeFormatter != null && this.timeZone != null) {
         dateTimeFormatter = dateTimeFormatter.withZone(this.timeZone.toZoneId());
      }

      return dateTimeFormatter != null ? dateTimeFormatter : fallbackFormatter;
   }
}
