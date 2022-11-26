package org.python.icu.impl;

import java.util.Map;
import java.util.MissingResourceException;
import java.util.TreeMap;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;

public final class CalendarUtil {
   private static final String CALKEY = "calendar";
   private static final String DEFCAL = "gregorian";

   public static String getCalendarType(ULocale loc) {
      String calType = loc.getKeywordValue("calendar");
      if (calType != null) {
         return calType;
      } else {
         ULocale canonical = ULocale.createCanonical(loc.toString());
         calType = canonical.getKeywordValue("calendar");
         if (calType != null) {
            return calType;
         } else {
            String region = ULocale.getRegionForSupplementalData(canonical, true);
            return CalendarUtil.CalendarPreferences.INSTANCE.getCalendarTypeForRegion(region);
         }
      }
   }

   private static final class CalendarPreferences extends UResource.Sink {
      private static final CalendarPreferences INSTANCE = new CalendarPreferences();
      Map prefs = new TreeMap();

      CalendarPreferences() {
         try {
            ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "supplementalData");
            rb.getAllItemsWithFallback("calendarPreferenceData", this);
         } catch (MissingResourceException var2) {
         }

      }

      String getCalendarTypeForRegion(String region) {
         String type = (String)this.prefs.get(region);
         return type == null ? "gregorian" : type;
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table calendarPreferenceData = value.getTable();

         for(int i = 0; calendarPreferenceData.getKeyAndValue(i, key, value); ++i) {
            UResource.Array types = value.getArray();
            if (types.getValue(0, value)) {
               String type = value.getString();
               if (!type.equals("gregorian")) {
                  this.prefs.put(key.toString(), type);
               }
            }
         }

      }
   }
}
