package weblogic.xml.schema.types.util;

import java.util.Calendar;
import java.util.StringTokenizer;

public class XSDYearDeserializer extends XSDDateDeserializer {
   protected void setDate(Calendar c, StringTokenizer tokens) {
      this.setYear(c, tokens);
   }
}
