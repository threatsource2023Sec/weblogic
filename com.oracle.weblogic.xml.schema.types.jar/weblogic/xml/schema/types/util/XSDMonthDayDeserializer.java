package weblogic.xml.schema.types.util;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class XSDMonthDayDeserializer extends XSDDateDeserializer {
   private static final String DELIMITERS = "-,+,:,Z";
   private static final String MINUS = "-";

   protected void init(Calendar c) {
      super.init(c);
      this.initDate(c);
   }

   protected void setCalendarFields(Calendar c, String s) {
      StringTokenizer tokens = new StringTokenizer(s, "-,+,:,Z", true);
      this.setDate(c, tokens);
      this.fillTimeFields(c);
      this.setTimeZone(c, tokens);
   }

   protected void setDate(Calendar c, StringTokenizer tokens) {
      try {
         this.setMonth(c, tokens);
         if (!tokens.nextToken().equals("-")) {
            throw new IllegalArgumentException("Invalid month / day delimiter.");
         } else {
            this.setDay(c, tokens);
         }
      } catch (NoSuchElementException var4) {
         throw new IllegalArgumentException("Missing element in date fields.");
      }
   }

   protected void initDate(Calendar c) {
      c.set(1, 1972);
   }

   protected void setMonth(Calendar c, StringTokenizer tokens) {
      try {
         String token = tokens.nextToken();
         if (token.equals("-")) {
            token = tokens.nextToken();
            if (!token.equals("-")) {
               throw new IllegalArgumentException("Invalid delimiter.");
            }

            token = tokens.nextToken();
         }

         if (token.length() != 2) {
            throw new IllegalArgumentException("Month has invalid length.");
         } else {
            c.set(2, Integer.parseInt(token) - 1);
         }
      } catch (NoSuchElementException var4) {
         throw new IllegalArgumentException("Missing month.");
      } catch (NumberFormatException var5) {
         throw new IllegalArgumentException("Month is not an int.");
      }
   }
}
