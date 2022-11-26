package org.python.icu.impl.data;

import java.util.ListResourceBundle;
import org.python.icu.util.EasterHoliday;
import org.python.icu.util.Holiday;
import org.python.icu.util.SimpleHoliday;

public class HolidayBundle_en_GB extends ListResourceBundle {
   private static final Holiday[] fHolidays;
   private static final Object[][] fContents;

   public synchronized Object[][] getContents() {
      return fContents;
   }

   static {
      fHolidays = new Holiday[]{SimpleHoliday.NEW_YEARS_DAY, SimpleHoliday.MAY_DAY, new SimpleHoliday(4, 31, -2, "Spring Holiday"), new SimpleHoliday(7, 31, -2, "Summer Bank Holiday"), SimpleHoliday.CHRISTMAS, SimpleHoliday.BOXING_DAY, new SimpleHoliday(11, 31, -2, "Christmas Holiday"), EasterHoliday.GOOD_FRIDAY, EasterHoliday.EASTER_SUNDAY, EasterHoliday.EASTER_MONDAY};
      fContents = new Object[][]{{"holidays", fHolidays}, {"Labor Day", "Labour Day"}};
   }
}
