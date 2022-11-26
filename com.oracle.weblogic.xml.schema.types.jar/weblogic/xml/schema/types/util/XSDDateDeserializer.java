package weblogic.xml.schema.types.util;

import java.util.Calendar;
import java.util.StringTokenizer;

public class XSDDateDeserializer extends XSDDateTimeDeserializer {
   private static final String DELIMITERS = "-,+,:,Z";

   protected void setCalendarFields(Calendar c, String s) {
      s = this.setEra(c, s);
      StringTokenizer tokens = new StringTokenizer(s, "-,+,:,Z", true);
      this.setDate(c, tokens);
      this.fillTimeFields(c);
      this.setTimeZone(c, tokens);
   }

   protected void fillTimeFields(Calendar c) {
      c.set(11, 0);
      c.set(12, 0);
      c.set(13, 0);
      c.set(14, 0);
   }
}
