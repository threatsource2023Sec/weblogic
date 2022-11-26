package weblogic.xml.schema.types.util;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class XSDYearMonthDeserializer extends XSDDateDeserializer {
   private static final String MINUS = "-";

   protected void setDate(Calendar c, StringTokenizer tokens) {
      try {
         this.setYear(c, tokens);
         if (!tokens.nextToken().equals("-")) {
            throw new IllegalArgumentException("Invalid year / month delimiter.");
         } else {
            this.setMonth(c, tokens);
         }
      } catch (NoSuchElementException var4) {
         throw new IllegalArgumentException("Missing element in date fields.");
      }
   }
}
