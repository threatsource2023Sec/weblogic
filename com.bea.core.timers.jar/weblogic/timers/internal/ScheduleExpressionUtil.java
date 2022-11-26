package weblogic.timers.internal;

import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.TimeZone;

public class ScheduleExpressionUtil {
   private static final String[] VALID_MONTHS = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
   private static final String[] VALID_DAY_OF_WEEK = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
   private static final int[] CALENDAR_DAYS_OF_WEEK = new int[]{1, 2, 3, 4, 5, 6, 7, 1};
   private static final String[] VALID_WEEK_OF_MONTH = new String[]{null, "1st", "2nd", "3rd", "4th", "5th"};
   private static final String LAST_STRING = "Last";
   private static final byte NOT_INITIALIZED = -1;
   static final int NOT_FOUND = -100;

   private static void setArray(byte[] values, byte value) {
      for(int i = 0; i < values.length; ++i) {
         values[i] = value;
      }

   }

   private static void setArray(int start, int end, boolean[] values, boolean value) {
      int stopPos = end;
      if (end > values.length - 1) {
         stopPos = values.length - 1;
      }

      for(int i = start; i <= stopPos; ++i) {
         values[i] = value;
      }

   }

   static boolean parseHHMMSSvalue(String userValue, byte[] values, String fieldName) throws IllegalArgumentException {
      if (userValue != null && !userValue.trim().isEmpty()) {
         setArray(values, (byte)-1);
         StringTokenizer st = new StringTokenizer(userValue, ",");
         boolean hasList = userValue.indexOf(",") > -1;
         if (!st.hasMoreTokens()) {
            throw new IllegalArgumentException("Invalid " + fieldName + " value: '" + userValue + "'");
         } else {
            while(st.hasMoreTokens()) {
               String token = st.nextToken().trim();
               if (hasList && "*".equals(token)) {
                  throw new IllegalArgumentException("List items in " + fieldName + " cannot be wildcards");
               }

               int pos = token.indexOf(45);
               if (pos != -1) {
                  fillInRange(token.substring(0, pos), token.substring(pos + 1, token.length()), values, fieldName);
               } else {
                  pos = token.indexOf(47);
                  if (pos != -1) {
                     if (hasList) {
                        throw new IllegalArgumentException("List items cannot be increments in " + fieldName);
                     }

                     fillInIncrement(token.substring(0, pos), token.substring(pos + 1, token.length()), values, fieldName);
                  } else {
                     fillInValue(token, values, fieldName);
                  }
               }
            }

            return true;
         }
      } else {
         throw new IllegalArgumentException(fieldName + " cannot be null or empty");
      }
   }

   private static void updateBefore(int currPos, byte value, byte[] values) {
      if (values[currPos] == -1 || values[currPos] <= currPos || values[currPos] > currPos && values[currPos] > value) {
         values[currPos] = value;
      }

   }

   private static void updateAfter(int currPos, byte value, byte[] values) {
      if (values[currPos] == -1 || values[currPos] < currPos && values[currPos] > value) {
         values[currPos] = value;
      }

   }

   private static void fillInValue(String token, byte[] values, String fieldName) throws IllegalArgumentException {
      byte value = false;
      token = token.trim();
      if ("*".equals(token)) {
         fillInRange((byte)0, (byte)values.length, values);
      } else {
         byte value = parseByte(token);
         if (value >= 0 && value < values.length) {
            fillInValue(value, values);
         } else {
            throw new IllegalArgumentException("Illegal value '" + value + "' in " + fieldName);
         }
      }
   }

   private static void fillInValue(byte value, byte[] values) {
      int max = values.length;

      for(int i = 0; i < max; ++i) {
         if (i < value) {
            updateBefore(i, value, values);
         } else {
            updateAfter(i, value, values);
         }
      }

   }

   private static void fillInRange(String x, String y, byte[] values, String fieldName) throws IllegalArgumentException {
      byte start = parseByte(x.trim());
      byte end = parseByte(y.trim());
      int max = values.length;
      if (start < max && end < max && start >= 0 && end >= 0) {
         if (start <= end) {
            fillInRange(start, end, values);
         } else {
            fillInRange(start, (byte)max, values);
            fillInRange((byte)0, end, values);
         }

      } else {
         throw new IllegalArgumentException("Range value must be in the range of [0," + max + "] in " + fieldName);
      }
   }

   private static void fillInRange(byte start, byte end, byte[] values) {
      int max = values.length;

      for(int i = 0; i < max; ++i) {
         if (i >= start && i < end) {
            values[i] = (byte)((i + 1) % max);
         } else if (i < start) {
            updateBefore(i, start, values);
         } else if (i >= end) {
            updateAfter(i, start, values);
         }
      }

   }

   private static void fillInIncrement(String x, String y, byte[] values, String fieldName) throws IllegalArgumentException {
      x = x.trim();
      byte start = true;
      byte interval = parseByte(y.trim());
      byte start;
      if ("*".equals(x)) {
         start = 0;
      } else {
         start = parseByte(x);
      }

      int max = values.length;
      if (start < max && interval < max && start >= 0 && interval >= 0) {
         for(byte curr = start; curr < max; curr += interval) {
            fillInValue(curr, values);
            if (interval == 0) {
               return;
            }
         }

      } else {
         throw new IllegalArgumentException("Increment value must be in the range of [0," + max + "] in " + fieldName);
      }
   }

   private static byte parseByte(String x) throws IllegalArgumentException {
      try {
         return Byte.parseByte(x);
      } catch (NumberFormatException var2) {
         throw new IllegalArgumentException(var2);
      }
   }

   private static int parseInt(String x) throws IllegalArgumentException {
      try {
         return Integer.parseInt(x.trim());
      } catch (NumberFormatException var2) {
         throw new IllegalArgumentException(var2);
      }
   }

   static boolean parseDayValues(String dayOfMonthAttribute, String dayOfWeekAttribute, byte[] days, int forYear, int forMonth) throws IllegalArgumentException {
      if (days == null) {
         return false;
      } else {
         boolean dayOfMonthSpecified = true;
         boolean dayOfWeekSpecified = true;
         if (dayOfMonthAttribute != null && !dayOfMonthAttribute.trim().isEmpty()) {
            dayOfMonthAttribute = dayOfMonthAttribute.trim();
            if ("*".equals(dayOfMonthAttribute)) {
               dayOfMonthSpecified = false;
            }

            if (dayOfWeekAttribute != null && !dayOfWeekAttribute.trim().isEmpty()) {
               dayOfWeekAttribute = dayOfWeekAttribute.trim();
               if (dayOfWeekAttribute.isEmpty() || "*".equals(dayOfWeekAttribute)) {
                  dayOfWeekSpecified = false;
               }

               if (!dayOfWeekSpecified && !dayOfMonthSpecified) {
                  return false;
               } else {
                  setArray(days, (byte)-1);
                  if (dayOfWeekSpecified) {
                     parseDayOfWeekValue(dayOfWeekAttribute, days, forYear, forMonth);
                  }

                  if (dayOfMonthSpecified) {
                     parseDayOfMonthValue(dayOfMonthAttribute, days, forYear, forMonth);
                  }

                  return true;
               }
            } else {
               throw new IllegalArgumentException("dayOfWeek can not be null or empty!");
            }
         } else {
            throw new IllegalArgumentException("dayOfMonth can not be null or empty!");
         }
      }
   }

   static void parseDayOfWeekValue(String dayOfWeekAttribute, byte[] days, int forYear, int forMonth) throws IllegalArgumentException {
      Calendar aCal = Calendar.getInstance();
      aCal.set(forYear, forMonth, 1);
      int daysInMonth = aCal.getActualMaximum(5);
      StringTokenizer st = new StringTokenizer(dayOfWeekAttribute, ",");
      if (!st.hasMoreTokens()) {
         throw new IllegalArgumentException("Invalid dayOfWeek value: '" + dayOfWeekAttribute + "'");
      } else {
         while(true) {
            while(true) {
               while(st.hasMoreTokens()) {
                  String token = st.nextToken().trim();
                  int pos = token.indexOf(45);
                  if (pos > 0) {
                     int x = getDayOfWeek(token.substring(0, pos));
                     int y = getDayOfWeek(token.substring(pos + 1, token.length()));
                     int i;
                     if (x <= y) {
                        for(i = x; i <= y; ++i) {
                           fillInDayOfWeek(i, days, aCal, daysInMonth);
                        }
                     } else {
                        for(i = x; i < CALENDAR_DAYS_OF_WEEK.length; ++i) {
                           fillInDayOfWeek(i, days, aCal, daysInMonth);
                        }

                        for(i = 1; i <= y; ++i) {
                           fillInDayOfWeek(i, days, aCal, daysInMonth);
                        }
                     }
                  } else {
                     fillInDayOfWeek(getDayOfWeek(token), days, aCal, daysInMonth);
                  }
               }

               return;
            }
         }
      }
   }

   static void fillInDayOfWeek(int dayOfWeek, byte[] days, Calendar cal, int daysInMonth) {
      cal.set(7, CALENDAR_DAYS_OF_WEEK[dayOfWeek]);
      cal.set(8, 1);

      for(int date = cal.get(5); date <= daysInMonth; date += 7) {
         fillInValue((byte)date, days);
      }

   }

   static void parseDayOfMonthValue(String dayOfMonthAttribute, byte[] days, int forYear, int forMonth) throws IllegalArgumentException {
      Calendar aCal = Calendar.getInstance();
      aCal.set(forYear, forMonth, 1);
      int daysInMonth = aCal.getActualMaximum(5);
      StringTokenizer st = new StringTokenizer(dayOfMonthAttribute, ",");
      if (!st.hasMoreTokens()) {
         throw new IllegalArgumentException("Invalid dayOfMonth value: '" + dayOfMonthAttribute + "'");
      } else {
         while(true) {
            while(true) {
               while(st.hasMoreTokens()) {
                  String token = st.nextToken().trim();
                  int pos = token.indexOf(45, 1);
                  int x;
                  if (pos > 0) {
                     x = getDayOfMonth(token.substring(0, pos), aCal, daysInMonth);
                     int y = getDayOfMonth(token.substring(pos + 1, token.length()), aCal, daysInMonth);
                     int i;
                     if (x <= y) {
                        for(i = x; i <= y && i <= daysInMonth; ++i) {
                           fillInValue((byte)i, days);
                        }
                     } else {
                        for(i = x; i <= daysInMonth; ++i) {
                           fillInValue((byte)i, days);
                        }

                        for(i = 0; i <= y; ++i) {
                           fillInValue((byte)i, days);
                        }
                     }
                  } else {
                     x = getDayOfMonth(token, aCal, daysInMonth);
                     if (x <= daysInMonth) {
                        fillInValue((byte)getDayOfMonth(token, aCal, daysInMonth), days);
                     }
                  }
               }

               return;
            }
         }
      }
   }

   static boolean parseMonthValue(String monthAttribute, boolean[] months) throws IllegalArgumentException {
      if (monthAttribute != null && !monthAttribute.trim().isEmpty()) {
         monthAttribute = monthAttribute.trim();
         if (monthAttribute.length() != 0 && !"*".equals(monthAttribute)) {
            StringTokenizer st = new StringTokenizer(monthAttribute, ",");
            if (!st.hasMoreTokens()) {
               throw new IllegalArgumentException("Invalid month value: '" + monthAttribute + "'");
            } else {
               while(st.hasMoreTokens()) {
                  String token = st.nextToken().trim();
                  int pos = token.indexOf(45);
                  if (pos != -1) {
                     int x = getMonth(token.substring(0, pos));
                     int y = getMonth(token.substring(pos + 1, token.length()));
                     if (x <= y) {
                        setArray(x, y, months, true);
                     } else {
                        setArray(x, VALID_MONTHS.length, months, true);
                        setArray(0, y, months, true);
                     }
                  } else {
                     months[getMonth(token)] = true;
                  }
               }

               return true;
            }
         } else {
            setArray(0, months.length - 1, months, true);
            return false;
         }
      } else {
         throw new IllegalArgumentException("Month attribute cannot be null or empty");
      }
   }

   private static int getMonth(String month) throws IllegalArgumentException {
      month = month.trim();
      int monthIndex = -1;

      for(int i = 0; i < VALID_MONTHS.length && monthIndex == -1; ++i) {
         if (VALID_MONTHS[i].equalsIgnoreCase(month)) {
            monthIndex = i;
         }
      }

      if (monthIndex == -1) {
         monthIndex = parseByte(month) - 1;
         if (monthIndex < 0 || monthIndex >= VALID_MONTHS.length) {
            throw new IllegalArgumentException("Invalid value for month attribute: " + month);
         }
      }

      return monthIndex;
   }

   private static int getDayOfWeek(String dayOfWeek) throws IllegalArgumentException {
      dayOfWeek = dayOfWeek.trim();
      int index = -1;

      for(int i = 0; i < VALID_DAY_OF_WEEK.length && index == -1; ++i) {
         if (VALID_DAY_OF_WEEK[i].equalsIgnoreCase(dayOfWeek)) {
            index = i;
         }
      }

      if (index == -1) {
         index = parseByte(dayOfWeek);
         if (index < 0 || index > 7) {
            throw new IllegalArgumentException("dayOfWeek attribute must be in the range of [0, 7]: " + dayOfWeek);
         }
      }

      return index;
   }

   private static int getDayOfMonth(String dayOfMonth, Calendar cal, int daysInMonth) throws IllegalArgumentException {
      dayOfMonth = dayOfMonth.trim();

      int weekOfMonth;
      try {
         weekOfMonth = parseByte(dayOfMonth);
         if (weekOfMonth >= -7 && weekOfMonth <= -1 || weekOfMonth >= 1 && weekOfMonth <= 31) {
            if (weekOfMonth < 0) {
               weekOfMonth += daysInMonth;
            }

            return weekOfMonth;
         }
      } catch (IllegalArgumentException var9) {
      }

      if ("Last".equalsIgnoreCase(dayOfMonth)) {
         return daysInMonth;
      } else {
         weekOfMonth = -100;
         int dayOfWeek = -100;
         int pos = dayOfMonth.lastIndexOf(" ");
         if (pos > 0) {
            String weekOfMonthString = dayOfMonth.substring(0, pos).trim();
            String dayOfWeekString = dayOfMonth.substring(pos + 1, dayOfMonth.length()).trim();
            if ("Last".equalsIgnoreCase(weekOfMonthString)) {
               weekOfMonth = -1;
            }

            int i;
            for(i = 1; i < VALID_WEEK_OF_MONTH.length && weekOfMonth == -100; ++i) {
               if (VALID_WEEK_OF_MONTH[i].equalsIgnoreCase(weekOfMonthString)) {
                  weekOfMonth = i;
               }
            }

            for(i = 0; i < VALID_DAY_OF_WEEK.length && dayOfWeek == -100; ++i) {
               if (VALID_DAY_OF_WEEK[i].equalsIgnoreCase(dayOfWeekString)) {
                  dayOfWeek = i;
               }
            }
         }

         if (weekOfMonth != -100 && dayOfWeek != -100) {
            cal.set(7, CALENDAR_DAYS_OF_WEEK[dayOfWeek]);
            cal.set(8, weekOfMonth);
            return cal.get(5);
         } else {
            throw new IllegalArgumentException("Invalid dayOfMonth value: '" + dayOfMonth + "'");
         }
      }
   }

   static boolean isWildCardYearAttribute(String yearAttribute) {
      if (yearAttribute != null && !yearAttribute.trim().isEmpty()) {
         yearAttribute = yearAttribute.trim();
         return yearAttribute.length() == 0 || "*".equals(yearAttribute);
      } else {
         throw new IllegalArgumentException("year can not be null or empty!");
      }
   }

   static int getNextValidYear(String yearAttribute, int startYear) throws IllegalArgumentException {
      if (isWildCardYearAttribute(yearAttribute)) {
         return startYear;
      } else {
         yearAttribute = yearAttribute.trim();
         int candidate = -100;
         StringTokenizer st = new StringTokenizer(yearAttribute, ",");
         if (!st.hasMoreTokens()) {
            throw new IllegalArgumentException("Invalid year value: '" + yearAttribute + "'");
         } else {
            while(true) {
               int x;
               do {
                  label52:
                  do {
                     while(st.hasMoreTokens()) {
                        String token = st.nextToken().trim();
                        int pos = token.indexOf(45);
                        if (pos != -1) {
                           x = parseInt(token.substring(0, pos));
                           int y = parseInt(token.substring(pos + 1, token.length()));
                           assertValidYear(x);
                           assertValidYear(y);
                           if (x > y) {
                              throw new IllegalArgumentException("start year cannot be greater than start year: " + yearAttribute);
                           }

                           if (x <= startYear && startYear <= y) {
                              return startYear;
                           }
                           continue label52;
                        }

                        x = parseInt(token);
                        assertValidYear(x);
                        if (startYear == x) {
                           return startYear;
                        }

                        if (x > startYear && (x < candidate || candidate == -100)) {
                           candidate = x;
                        }
                     }

                     return candidate;
                  } while(x <= startYear);
               } while(x >= candidate && candidate != -100);

               candidate = x;
            }
         }
      }
   }

   static void assertValidYear(int year) {
      if (year < 1970 || year > 9999) {
         throw new IllegalArgumentException("Invalid year value " + year);
      }
   }

   static TimeZone parseTimeZoneValue(String timeZoneAttribute) throws IllegalArgumentException {
      if (timeZoneAttribute == null) {
         return null;
      } else {
         timeZoneAttribute = timeZoneAttribute.trim();
         if (timeZoneAttribute.length() == 0) {
            return null;
         } else {
            String[] validTimeZones = TimeZone.getAvailableIDs();

            for(int i = 0; i < validTimeZones.length; ++i) {
               if (validTimeZones[i].equals(timeZoneAttribute)) {
                  return TimeZone.getTimeZone(timeZoneAttribute);
               }
            }

            throw new IllegalArgumentException("Time Zone attribute specified is invalid");
         }
      }
   }
}
