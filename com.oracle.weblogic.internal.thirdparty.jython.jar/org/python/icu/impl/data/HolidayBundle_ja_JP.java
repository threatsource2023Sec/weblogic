package org.python.icu.impl.data;

import java.util.ListResourceBundle;
import org.python.icu.util.Holiday;
import org.python.icu.util.SimpleHoliday;

public class HolidayBundle_ja_JP extends ListResourceBundle {
   private static final Holiday[] fHolidays = new Holiday[]{new SimpleHoliday(1, 11, 0, "National Foundation Day")};
   private static final Object[][] fContents;

   public synchronized Object[][] getContents() {
      return fContents;
   }

   static {
      fContents = new Object[][]{{"holidays", fHolidays}};
   }
}
