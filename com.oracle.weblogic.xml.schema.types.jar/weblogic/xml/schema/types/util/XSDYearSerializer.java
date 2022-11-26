package weblogic.xml.schema.types.util;

import java.util.Calendar;

public class XSDYearSerializer extends XSDDateTimeSerializer {
   public static String getString(Calendar c) {
      StringBuffer buffer = new StringBuffer();
      setEra(c, buffer);
      setDate(c, buffer);
      setTimeZone(c, buffer);
      return buffer.toString();
   }

   protected static void setDate(Calendar c, StringBuffer buffer) {
      setYear(c, buffer);
   }
}
