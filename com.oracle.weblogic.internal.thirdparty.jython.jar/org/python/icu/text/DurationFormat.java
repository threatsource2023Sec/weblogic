package org.python.icu.text;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import org.python.icu.impl.duration.BasicDurationFormat;
import org.python.icu.util.ULocale;

/** @deprecated */
@Deprecated
public abstract class DurationFormat extends UFormat {
   private static final long serialVersionUID = -2076961954727774282L;

   /** @deprecated */
   @Deprecated
   public static DurationFormat getInstance(ULocale locale) {
      return BasicDurationFormat.getInstance(locale);
   }

   /** @deprecated */
   @Deprecated
   protected DurationFormat() {
   }

   /** @deprecated */
   @Deprecated
   protected DurationFormat(ULocale locale) {
      this.setLocale(locale, locale);
   }

   /** @deprecated */
   @Deprecated
   public abstract StringBuffer format(Object var1, StringBuffer var2, FieldPosition var3);

   /** @deprecated */
   @Deprecated
   public Object parseObject(String source, ParsePosition pos) {
      throw new UnsupportedOperationException();
   }

   /** @deprecated */
   @Deprecated
   public abstract String formatDurationFromNowTo(Date var1);

   /** @deprecated */
   @Deprecated
   public abstract String formatDurationFromNow(long var1);

   /** @deprecated */
   @Deprecated
   public abstract String formatDurationFrom(long var1, long var3);
}
