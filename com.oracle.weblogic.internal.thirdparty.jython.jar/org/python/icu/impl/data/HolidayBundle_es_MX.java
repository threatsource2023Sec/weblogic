package org.python.icu.impl.data;

import java.util.ListResourceBundle;
import org.python.icu.util.Holiday;
import org.python.icu.util.SimpleHoliday;

public class HolidayBundle_es_MX extends ListResourceBundle {
   private static final Holiday[] fHolidays;
   private static final Object[][] fContents;

   public synchronized Object[][] getContents() {
      return fContents;
   }

   static {
      fHolidays = new Holiday[]{SimpleHoliday.NEW_YEARS_DAY, new SimpleHoliday(1, 5, 0, "Constitution Day"), new SimpleHoliday(2, 21, 0, "Benito Juárez Day"), SimpleHoliday.MAY_DAY, new SimpleHoliday(4, 5, 0, "Cinco de Mayo"), new SimpleHoliday(5, 1, 0, "Navy Day"), new SimpleHoliday(8, 16, 0, "Independence Day"), new SimpleHoliday(9, 12, 0, "Día de la Raza"), SimpleHoliday.ALL_SAINTS_DAY, new SimpleHoliday(10, 2, 0, "Day of the Dead"), new SimpleHoliday(10, 20, 0, "Revolution Day"), new SimpleHoliday(11, 12, 0, "Flag Day"), SimpleHoliday.CHRISTMAS};
      fContents = new Object[][]{{"holidays", fHolidays}};
   }
}
