package com.bea.core.repackaged.springframework.context.i18n;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Locale;
import java.util.TimeZone;

public class SimpleTimeZoneAwareLocaleContext extends SimpleLocaleContext implements TimeZoneAwareLocaleContext {
   @Nullable
   private final TimeZone timeZone;

   public SimpleTimeZoneAwareLocaleContext(@Nullable Locale locale, @Nullable TimeZone timeZone) {
      super(locale);
      this.timeZone = timeZone;
   }

   @Nullable
   public TimeZone getTimeZone() {
      return this.timeZone;
   }

   public String toString() {
      return super.toString() + " " + (this.timeZone != null ? this.timeZone.toString() : "-");
   }
}
