package weblogic.xml.schema.types.util;

import java.util.Calendar;
import java.util.StringTokenizer;

public class XSDMonthDeserializer extends XSDMonthDayDeserializer {
   private static final String DELIMITERS = "-,+,:,Z";

   public Calendar getCalendar(String s) {
      if (s.endsWith("--")) {
         s = s.substring(0, s.length() - 2);
      }

      return super.getCalendar(s);
   }

   protected void setCalendarFields(Calendar c, String s) {
      StringTokenizer tokens = new StringTokenizer(s, "-,+,:,Z", true);
      this.setDate(c, tokens);
      this.fillTimeFields(c);
      this.setTimeZone(c, tokens);
   }

   protected void setDate(Calendar c, StringTokenizer tokens) {
      this.setMonth(c, tokens);
      c.set(5, 10);
   }
}
