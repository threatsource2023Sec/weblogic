package com.bea.core.repackaged.springframework.format.datetime.joda;

import com.bea.core.repackaged.springframework.core.NamedThreadLocal;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Locale;
import org.joda.time.format.DateTimeFormatter;

public final class JodaTimeContextHolder {
   private static final ThreadLocal jodaTimeContextHolder = new NamedThreadLocal("JodaTimeContext");

   private JodaTimeContextHolder() {
   }

   public static void resetJodaTimeContext() {
      jodaTimeContextHolder.remove();
   }

   public static void setJodaTimeContext(@Nullable JodaTimeContext jodaTimeContext) {
      if (jodaTimeContext == null) {
         resetJodaTimeContext();
      } else {
         jodaTimeContextHolder.set(jodaTimeContext);
      }

   }

   @Nullable
   public static JodaTimeContext getJodaTimeContext() {
      return (JodaTimeContext)jodaTimeContextHolder.get();
   }

   public static DateTimeFormatter getFormatter(DateTimeFormatter formatter, @Nullable Locale locale) {
      DateTimeFormatter formatterToUse = locale != null ? formatter.withLocale(locale) : formatter;
      JodaTimeContext context = getJodaTimeContext();
      return context != null ? context.getFormatter(formatterToUse) : formatterToUse;
   }
}
