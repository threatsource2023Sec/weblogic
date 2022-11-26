package weblogic.xml.schema.types.util;

import java.util.Calendar;
import java.util.StringTokenizer;

public class XSDTimeDeserializer extends XSDDateTimeDeserializer {
   private static final String DELIMITERS = "-,+,:,Z";

   protected void setCalendarFields(Calendar c, String s) {
      s = this.setEra(c, s);
      StringTokenizer tokens = new StringTokenizer(s, "-,+,:,Z", true);
      this.setTime(c, tokens);
      this.setTimeZone(c, tokens);
   }
}
