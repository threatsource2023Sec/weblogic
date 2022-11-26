package com.bea.core.repackaged.springframework.context.i18n;

import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Locale;

public class SimpleLocaleContext implements LocaleContext {
   @Nullable
   private final Locale locale;

   public SimpleLocaleContext(@Nullable Locale locale) {
      this.locale = locale;
   }

   @Nullable
   public Locale getLocale() {
      return this.locale;
   }

   public String toString() {
      return this.locale != null ? this.locale.toString() : "-";
   }
}
