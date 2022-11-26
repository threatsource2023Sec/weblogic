package weblogic.xml.schema.types.util;

import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class XSDDateTimeDeserializer {
   private static final boolean VERBOSE = false;
   private static final String DELIMITERS = "-,+,:,T,Z";
   private static final String MINUS = "-";
   private static final String PLUS = "+";
   private static final String COLON = ":";
   private static final String PERIOD = ".";
   private static final String T = "T";
   private static final String Z = "Z";
   private static final String ZERO = "0";
   private static final String GMT = "GMT";
   private static final String GMT_OFFSET = "+00:00";

   public Calendar getCalendar(String s) {
      Calendar c = Calendar.getInstance();
      this.init(c);

      try {
         this.setCalendarFields(c, s);
         this.validate(c);
         this.wrapup(c);
         return c;
      } catch (IllegalArgumentException var4) {
         throw new IllegalArgumentException("Element of dateTime is not in valid range: " + var4.getMessage());
      } catch (Exception var5) {
         throw new IllegalArgumentException("Could not parse dateTime: " + var5);
      }
   }

   protected void init(Calendar c) {
      c.clear();
      c.setLenient(false);
   }

   protected void validate(Calendar c) {
      c.getTime();
   }

   protected void wrapup(Calendar c) {
      c.setLenient(true);
   }

   protected void setCalendarFields(Calendar c, String s) {
      s = this.setEra(c, s);
      StringTokenizer tokens = new StringTokenizer(s, "-,+,:,T,Z", true);

      try {
         this.setDate(c, tokens);
         if (!tokens.nextToken().equals("T")) {
            throw new IllegalArgumentException("Invalid date / time delimiter.");
         } else {
            this.setTime(c, tokens);
            this.setTimeZone(c, tokens);
         }
      } catch (NoSuchElementException var5) {
         throw new IllegalArgumentException("Missing elements.");
      }
   }

   protected String setEra(Calendar c, String s) {
      String r = s;
      if (s.startsWith("-")) {
         c.set(0, 0);
         r = s.substring(1);
      } else {
         c.set(0, 1);
      }

      return r;
   }

   protected void setDate(Calendar c, StringTokenizer tokens) {
      try {
         this.setYear(c, tokens);
         if (!tokens.nextToken().equals("-")) {
            throw new IllegalArgumentException("Invalid year / month delimiter.");
         } else {
            this.setMonth(c, tokens);
            if (!tokens.nextToken().equals("-")) {
               throw new IllegalArgumentException("Invalid month / day delimiter.");
            } else {
               this.setDay(c, tokens);
            }
         }
      } catch (NoSuchElementException var4) {
         throw new IllegalArgumentException("Missing delimiter in date fields.");
      }
   }

   protected void setYear(Calendar c, StringTokenizer tokens) {
      try {
         String token = tokens.nextToken();
         c.set(1, Integer.parseInt(token));
      } catch (NoSuchElementException var4) {
         throw new IllegalArgumentException("Missing year.");
      } catch (NumberFormatException var5) {
         throw new IllegalArgumentException("Year is not an int.");
      }
   }

   protected void setMonth(Calendar c, StringTokenizer tokens) {
      try {
         String token = tokens.nextToken();
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

   protected void setDay(Calendar c, StringTokenizer tokens) {
      try {
         String token = tokens.nextToken();
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

   protected void setTime(Calendar c, StringTokenizer tokens) {
      try {
         String token = tokens.nextToken();
         if (token.length() != 2) {
            throw new IllegalArgumentException("Hours have invalid length.");
         } else {
            c.set(11, Integer.parseInt(token));
            if (!tokens.nextToken().equals(":")) {
               throw new IllegalArgumentException("Invalid hour / minute delimiter.");
            } else {
               token = tokens.nextToken();
               if (token.length() != 2) {
                  throw new IllegalArgumentException("Minutes have invalid length.");
               } else {
                  c.set(12, Integer.parseInt(token));
                  if (!tokens.nextToken().equals(":")) {
                     throw new IllegalArgumentException("Invalid minute / second delimiter.");
                  } else {
                     token = tokens.nextToken();
                     int tokenLength = token.length();
                     if (tokenLength == 2) {
                        c.set(13, Integer.parseInt(token));
                        c.set(14, 0);
                     } else {
                        if (token.length() < 2) {
                           throw new IllegalArgumentException("Seconds have invalid length.");
                        }

                        int p = token.indexOf(".");
                        if (p != 2) {
                           throw new IllegalArgumentException("Seconds have invalid format.");
                        }

                        String millis = token.substring(3);
                        int millisLength = millis.length();
                        if (millisLength == 0) {
                           throw new IllegalArgumentException("Seconds have invalid format.");
                        }

                        if (millisLength > 3) {
                           millis = millis.substring(0, 3);
                        } else if (millisLength == 1) {
                           millis = millis + "0" + "0";
                        } else if (millisLength == 2) {
                           millis = millis + "0";
                        }

                        c.set(13, Integer.parseInt(token.substring(0, 2)));
                        c.set(14, Integer.parseInt(millis));
                     }

                  }
               }
            }
         }
      } catch (NoSuchElementException var8) {
         throw new IllegalArgumentException("Missing element in time fields.");
      } catch (NumberFormatException var9) {
         throw new IllegalArgumentException("Element in time fields not an int.");
      }
   }

   protected void setTimeZone(Calendar c, StringTokenizer tokens) {
      if (tokens.hasMoreTokens()) {
         String token = tokens.nextToken();
         if (token.equals("Z")) {
            this.setGMT(c);
            if (tokens.hasMoreTokens()) {
               throw new IllegalArgumentException("Invalid timezone format.");
            }
         } else {
            if (!token.equals("-") && !token.equals("+")) {
               throw new IllegalArgumentException("Invalid timezone format.");
            }

            this.setTimeZone(c, tokens, token);
         }

      }
   }

   protected void setTimeZone(Calendar c, StringTokenizer tokens, String sign) {
      try {
         String hours = tokens.nextToken();
         if (hours.length() != 2) {
            throw new IllegalArgumentException("Hours of timezone have invalid length.");
         }

         int h = Integer.parseInt(hours);
         if (!tokens.nextToken().equals(":")) {
            throw new IllegalArgumentException("Invalid timezone hour / minute delimiter.");
         }

         String minutes = tokens.nextToken();
         if (minutes.length() != 2) {
            throw new IllegalArgumentException("Minutes of timezone have invalid length.");
         }

         int m = Integer.parseInt(minutes);
         if (h == 0 && m == 0) {
            this.setGMT(c);
            return;
         }

         int millis = h * 3600000 + m * '\uea60';
         if (sign.equals("-")) {
            millis *= -1;
         }

         TimeZone tz = TimeZone.getTimeZone("GMT" + sign + hours + ":" + minutes);
         c.setTimeZone(tz);
      } catch (NoSuchElementException var10) {
         throw new IllegalArgumentException("Missing element in timezone fields.");
      } catch (NumberFormatException var11) {
         throw new IllegalArgumentException("Element in timezone is not an int.");
      }

      if (tokens.hasMoreTokens()) {
         throw new IllegalArgumentException("Invalid timezone format.");
      }
   }

   protected void setGMT(Calendar c) {
      c.set(15, 0);
      c.setTimeZone(TimeZone.getTimeZone("GMT"));
   }
}
