package org.stringtemplate.v4;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DateRenderer implements AttributeRenderer {
   public static final Map formatToInt = new HashMap() {
      {
         this.put("short", 3);
         this.put("medium", 2);
         this.put("long", 1);
         this.put("full", 0);
         this.put("date:short", 3);
         this.put("date:medium", 2);
         this.put("date:long", 1);
         this.put("date:full", 0);
         this.put("time:short", 3);
         this.put("time:medium", 2);
         this.put("time:long", 1);
         this.put("time:full", 0);
      }
   };

   public String toString(Object o, String formatString, Locale locale) {
      if (formatString == null) {
         formatString = "short";
      }

      Date d;
      if (o instanceof Calendar) {
         d = ((Calendar)o).getTime();
      } else {
         d = (Date)o;
      }

      Integer styleI = (Integer)formatToInt.get(formatString);
      Object f;
      if (styleI == null) {
         f = new SimpleDateFormat(formatString, locale);
      } else {
         int style = styleI;
         if (formatString.startsWith("date:")) {
            f = DateFormat.getDateInstance(style, locale);
         } else if (formatString.startsWith("time:")) {
            f = DateFormat.getTimeInstance(style, locale);
         } else {
            f = DateFormat.getDateTimeInstance(style, style, locale);
         }
      }

      return ((DateFormat)f).format(d);
   }
}
