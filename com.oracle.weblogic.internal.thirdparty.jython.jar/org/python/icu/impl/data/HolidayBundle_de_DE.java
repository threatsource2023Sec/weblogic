package org.python.icu.impl.data;

import java.util.ListResourceBundle;
import org.python.icu.util.EasterHoliday;
import org.python.icu.util.Holiday;
import org.python.icu.util.SimpleHoliday;

public class HolidayBundle_de_DE extends ListResourceBundle {
   private static final Holiday[] fHolidays;
   private static final Object[][] fContents;

   public synchronized Object[][] getContents() {
      return fContents;
   }

   static {
      fHolidays = new Holiday[]{SimpleHoliday.NEW_YEARS_DAY, SimpleHoliday.MAY_DAY, new SimpleHoliday(5, 15, 4, "Memorial Day"), new SimpleHoliday(9, 3, 0, "Unity Day"), SimpleHoliday.ALL_SAINTS_DAY, new SimpleHoliday(10, 18, 0, "Day of Prayer and Repentance"), SimpleHoliday.CHRISTMAS, SimpleHoliday.BOXING_DAY, EasterHoliday.GOOD_FRIDAY, EasterHoliday.EASTER_SUNDAY, EasterHoliday.EASTER_MONDAY, EasterHoliday.ASCENSION, EasterHoliday.WHIT_SUNDAY, EasterHoliday.WHIT_MONDAY};
      fContents = new Object[][]{{"holidays", fHolidays}};
   }
}
