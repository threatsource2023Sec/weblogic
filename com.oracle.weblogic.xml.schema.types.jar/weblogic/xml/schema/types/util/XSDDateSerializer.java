package weblogic.xml.schema.types.util;

import java.util.Calendar;

public class XSDDateSerializer extends XSDDateTimeSerializer {
   public static String getString(Calendar c) {
      StringBuffer buffer = new StringBuffer();
      setEra(c, buffer);
      setDate(c, buffer);
      setTimeZone(c, buffer);
      return buffer.toString();
   }
}
