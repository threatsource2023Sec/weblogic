package org.python.icu.impl.data;

import java.util.ListResourceBundle;
import org.python.icu.util.Holiday;
import org.python.icu.util.SimpleHoliday;

public class HolidayBundle_en_CA extends ListResourceBundle {
   private static final Holiday[] fHolidays;
   private static final Object[][] fContents;

   public synchronized Object[][] getContents() {
      return fContents;
   }

   static {
      fHolidays = new Holiday[]{SimpleHoliday.NEW_YEARS_DAY, new SimpleHoliday(4, 19, 0, "Victoria Day"), new SimpleHoliday(6, 1, 0, "Canada Day"), new SimpleHoliday(7, 1, 2, "Civic Holiday"), new SimpleHoliday(8, 1, 2, "Labor Day"), new SimpleHoliday(9, 8, 2, "Thanksgiving"), new SimpleHoliday(10, 11, 0, "Remembrance Day"), SimpleHoliday.CHRISTMAS, SimpleHoliday.BOXING_DAY, SimpleHoliday.NEW_YEARS_EVE};
      fContents = new Object[][]{{"holidays", fHolidays}, {"Labor Day", "Labour Day"}};
   }
}
