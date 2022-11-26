package weblogic.deploy.api.internal.utils;

import java.util.Locale;
import weblogic.deploy.api.internal.SPIDeployerLogger;

public class LocaleManager {
   private static Locale defaultLocale = Locale.getDefault();
   private static Locale[] supportedLocales = null;

   private static void setupLocale(Locale locale) {
      Locale.setDefault(locale);
   }

   public static void setLocale(Locale locale) throws UnsupportedOperationException {
      if (isLocaleSupported(locale)) {
         setupLocale(locale);
      } else {
         throw new UnsupportedOperationException(SPIDeployerLogger.unsupportedLocale(locale.toString()));
      }
   }

   public static boolean isLocaleSupported(Locale locale) {
      Locale[] sl = getSupportedLocales();

      for(int i = 0; i < sl.length; ++i) {
         if (sl[i].equals(locale)) {
            return true;
         }
      }

      return false;
   }

   public static Locale getDefaultLocale() {
      return defaultLocale;
   }

   public static Locale getCurrentLocale() {
      return Locale.getDefault();
   }

   public static Locale[] getSupportedLocales() {
      if (supportedLocales == null) {
         supportedLocales = Locale.getAvailableLocales();
      }

      return supportedLocales;
   }

   static {
      setupLocale(defaultLocale);
   }
}
