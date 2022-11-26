package weblogic.xml.schema.types.util;

import java.util.Calendar;

public class XSDMonthDaySerializer extends XSDDateTimeSerializer {
   public static String getString(Calendar c) {
      StringBuffer buffer = new StringBuffer();
      setDate(c, buffer);
      setTimeZone(c, buffer);
      return buffer.toString();
   }

   protected static void setDate(Calendar c, StringBuffer buffer) {
      buffer.append("--");
      setMonth(c, buffer);
      buffer.append('-');
      setDay(c, buffer);
   }
}
