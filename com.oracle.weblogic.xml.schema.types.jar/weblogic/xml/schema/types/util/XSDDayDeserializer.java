package weblogic.xml.schema.types.util;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class XSDDayDeserializer extends XSDMonthDayDeserializer {
   private static final String MINUS = "-";

   protected void setDate(Calendar c, StringTokenizer tokens) {
      this.setDay(c, tokens);
   }

   protected void setDay(Calendar c, StringTokenizer tokens) {
      try {
         String token = tokens.nextToken();
         if (token.equals("-")) {
            token = tokens.nextToken();
            if (!token.equals("-")) {
               throw new IllegalArgumentException("Invalid delimiter: " + token);
            }

            token = tokens.nextToken();
            if (!token.equals("-")) {
               throw new IllegalArgumentException("Invalid delimiter: " + token);
            }

            token = tokens.nextToken();
         }

         if (token.length() != 2) {
            throw new IllegalArgumentException("Day has invalid length.");
         } else {
            c.set(5, Integer.parseInt(token));
         }
      } catch (NoSuchElementException var4) {
         throw new IllegalArgumentException("Missing day.");
      } catch (NumberFormatException var5) {
         throw new IllegalArgumentException("Day is not an int.");
      }
   }
}
