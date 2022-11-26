package org.python.icu.impl.data;

import java.util.ListResourceBundle;
import org.python.icu.util.HebrewHoliday;
import org.python.icu.util.Holiday;

public class HolidayBundle_iw_IL extends ListResourceBundle {
   private static final Holiday[] fHolidays;
   private static final Object[][] fContents;

   public synchronized Object[][] getContents() {
      return fContents;
   }

   static {
      fHolidays = new Holiday[]{HebrewHoliday.ROSH_HASHANAH, HebrewHoliday.YOM_KIPPUR, HebrewHoliday.HANUKKAH, HebrewHoliday.PURIM, HebrewHoliday.PASSOVER, HebrewHoliday.SHAVUOT, HebrewHoliday.SELIHOT};
      fContents = new Object[][]{{"holidays", fHolidays}};
   }
}
