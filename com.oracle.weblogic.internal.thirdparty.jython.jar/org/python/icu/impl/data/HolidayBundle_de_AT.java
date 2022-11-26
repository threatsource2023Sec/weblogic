package org.python.icu.impl.data;

import java.util.ListResourceBundle;
import org.python.icu.util.EasterHoliday;
import org.python.icu.util.Holiday;
import org.python.icu.util.SimpleHoliday;

public class HolidayBundle_de_AT extends ListResourceBundle {
   private static final Holiday[] fHolidays;
   private static final Object[][] fContents;

   public synchronized Object[][] getContents() {
      return fContents;
   }

   static {
      fHolidays = new Holiday[]{SimpleHoliday.NEW_YEARS_DAY, SimpleHoliday.EPIPHANY, EasterHoliday.GOOD_FRIDAY, EasterHoliday.EASTER_SUNDAY, EasterHoliday.EASTER_MONDAY, EasterHoliday.ASCENSION, EasterHoliday.WHIT_SUNDAY, EasterHoliday.WHIT_MONDAY, EasterHoliday.CORPUS_CHRISTI, SimpleHoliday.ASSUMPTION, SimpleHoliday.ALL_SAINTS_DAY, SimpleHoliday.IMMACULATE_CONCEPTION, SimpleHoliday.CHRISTMAS, SimpleHoliday.ST_STEPHENS_DAY, new SimpleHoliday(4, 1, 0, "National Holiday"), new SimpleHoliday(9, 31, -2, "National Holiday")};
      fContents = new Object[][]{{"holidays", fHolidays}, {"Christmas", "Christtag"}, {"New Year's Day", "Neujahrstag"}};
   }
}
