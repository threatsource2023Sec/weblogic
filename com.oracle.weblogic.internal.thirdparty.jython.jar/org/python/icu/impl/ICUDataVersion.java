package org.python.icu.impl;

import java.util.MissingResourceException;
import org.python.icu.util.UResourceBundle;
import org.python.icu.util.VersionInfo;

public final class ICUDataVersion {
   private static final String U_ICU_VERSION_BUNDLE = "icuver";
   private static final String U_ICU_DATA_KEY = "DataVersion";

   public static VersionInfo getDataVersion() {
      UResourceBundle icudatares = null;

      try {
         icudatares = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "icuver", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
         icudatares = icudatares.get("DataVersion");
      } catch (MissingResourceException var2) {
         return null;
      }

      return VersionInfo.getInstance(icudatares.getString());
   }
}
