package org.python.icu.impl.data;

import java.util.ListResourceBundle;
import org.python.icu.util.EasterHoliday;
import org.python.icu.util.Holiday;
import org.python.icu.util.SimpleHoliday;

public class HolidayBundle_da_DK extends ListResourceBundle {
   private static final Holiday[] fHolidays;
   private static final Object[][] fContents;

   public synchronized Object[][] getContents() {
      return fContents;
   }

   static {
      fHolidays = new Holiday[]{SimpleHoliday.NEW_YEARS_DAY, new SimpleHoliday(3, 30, -6, "General Prayer Day"), new SimpleHoliday(5, 5, "Constitution Day"), SimpleHoliday.CHRISTMAS_EVE, SimpleHoliday.CHRISTMAS, SimpleHoliday.BOXING_DAY, SimpleHoliday.NEW_YEARS_EVE, EasterHoliday.MAUNDY_THURSDAY, EasterHoliday.GOOD_FRIDAY, EasterHoliday.EASTER_SUNDAY, EasterHoliday.EASTER_MONDAY, EasterHoliday.ASCENSION, EasterHoliday.WHIT_MONDAY};
      fContents = new Object[][]{{"holidays", fHolidays}};
   }
}
