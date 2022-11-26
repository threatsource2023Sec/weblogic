package com.googlecode.cqengine.query.parser.cqn.support;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class ApacheSolrDataMathParser {
   public static TimeZone UTC = TimeZone.getTimeZone("UTC");
   public static final TimeZone DEFAULT_MATH_TZ;
   public static final Locale DEFAULT_MATH_LOCALE;
   public static final Map CALENDAR_UNITS;
   private TimeZone zone;
   private Locale loc;
   private Date now;
   private static Pattern splitter;

   private static Map makeUnitsMap() {
      Map units = new HashMap(13);
      units.put("YEAR", 1);
      units.put("YEARS", 1);
      units.put("MONTH", 2);
      units.put("MONTHS", 2);
      units.put("DAY", 5);
      units.put("DAYS", 5);
      units.put("DATE", 5);
      units.put("HOUR", 11);
      units.put("HOURS", 11);
      units.put("MINUTE", 12);
      units.put("MINUTES", 12);
      units.put("SECOND", 13);
      units.put("SECONDS", 13);
      units.put("MILLI", 14);
      units.put("MILLIS", 14);
      units.put("MILLISECOND", 14);
      units.put("MILLISECONDS", 14);
      return units;
   }

   public static void add(Calendar c, int val, String unit) {
      Integer uu = (Integer)CALENDAR_UNITS.get(unit);
      if (null == uu) {
         throw new IllegalArgumentException("Adding Unit not recognized: " + unit);
      } else {
         c.add(uu, val);
      }
   }

   public static void round(Calendar c, String unit) {
      Integer uu = (Integer)CALENDAR_UNITS.get(unit);
      if (null == uu) {
         throw new IllegalArgumentException("Rounding Unit not recognized: " + unit);
      } else {
         int u = uu;
         switch (u) {
            case 1:
               c.clear(2);
            case 2:
               c.clear(5);
               c.clear(7);
               c.clear(8);
               c.clear(6);
               c.clear(4);
               c.clear(3);
            case 5:
               c.clear(11);
               c.clear(10);
               c.clear(9);
            case 11:
               c.clear(12);
            case 12:
               c.clear(13);
            case 13:
               c.clear(14);
               return;
            case 3:
            case 4:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
               throw new IllegalStateException("No logic for rounding value (" + u + ") " + unit);
         }
      }
   }

   public ApacheSolrDataMathParser() {
      this((TimeZone)null, DEFAULT_MATH_LOCALE);
   }

   public ApacheSolrDataMathParser(TimeZone tz, Locale l) {
      this.loc = l == null ? DEFAULT_MATH_LOCALE : l;
      tz = tz == null ? DEFAULT_MATH_TZ : tz;
      this.zone = tz;
   }

   public void setNow(Date n) {
      this.now = n;
   }

   public Date getNow() {
      if (this.now == null) {
         this.now = new Date();
      }

      return (Date)this.now.clone();
   }

   public Date parseMath(String math) throws ParseException {
      Calendar cal = Calendar.getInstance(this.zone, this.loc);
      cal.setTime(this.getNow());
      if (0 == math.length()) {
         return cal.getTime();
      } else {
         String[] ops = splitter.split(math);
         int pos = 0;

         while(pos < ops.length) {
            if (1 != ops[pos].length()) {
               throw new ParseException("Multi character command found: \"" + ops[pos] + "\"", pos);
            }

            char command = ops[pos++].charAt(0);
            switch (command) {
               case '+':
               case '-':
                  if (ops.length < pos + 2) {
                     throw new ParseException("Need a value and unit for command: \"" + command + "\"", pos);
                  }

                  int val = false;

                  int val;
                  try {
                     val = Integer.valueOf(ops[pos++]);
                  } catch (NumberFormatException var9) {
                     throw new ParseException("Not a Number: \"" + ops[pos - 1] + "\"", pos - 1);
                  }

                  if ('-' == command) {
                     val = 0 - val;
                  }

                  try {
                     String unit = ops[pos++];
                     add(cal, val, unit);
                     break;
                  } catch (IllegalArgumentException var8) {
                     throw new ParseException("Unit not recognized: \"" + ops[pos - 1] + "\"", pos - 1);
                  }
               case ',':
               case '.':
               default:
                  throw new ParseException("Unrecognized command: \"" + command + "\"", pos - 1);
               case '/':
                  if (ops.length < pos + 1) {
                     throw new ParseException("Need a unit after command: \"" + command + "\"", pos);
                  }

                  try {
                     round(cal, ops[pos++]);
                  } catch (IllegalArgumentException var10) {
                     throw new ParseException("Unit not recognized: \"" + ops[pos - 1] + "\"", pos - 1);
                  }
            }
         }

         return cal.getTime();
      }
   }

   static {
      DEFAULT_MATH_TZ = UTC;
      DEFAULT_MATH_LOCALE = Locale.ROOT;
      CALENDAR_UNITS = makeUnitsMap();
      splitter = Pattern.compile("\\b|(?<=\\d)(?=\\D)");
   }
}
