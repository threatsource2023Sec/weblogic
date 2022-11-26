package weblogic.xml.schema.types.util;

import java.util.Calendar;

public class XSDTimeSerializer extends XSDDateTimeSerializer {
   public static String getString(Calendar c) {
      StringBuffer buffer = new StringBuffer();
      setEra(c, buffer);
      setTime(c, buffer);
      setTimeZone(c, buffer);
      return buffer.toString();
   }
}
