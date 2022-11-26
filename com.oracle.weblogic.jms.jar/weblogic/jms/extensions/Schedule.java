package weblogic.jms.extensions;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import weblogic.jms.JMSLogger;

public final class Schedule {
   private static final int MILLISECOND = 0;
   private static final int SECOND = 1;
   private static final int MINUTE = 2;
   private static final int HOUR_OF_DAY = 3;
   private static final int DAY_OF_MONTH = 4;
   private static final int MONTH = 5;
   private static final int DAY_OF_WEEK = 6;
   private static final int MILLIDIVISOR = 20;
   private static final String[] CALNAMES = new String[]{"ms", "sec", "min", "hr", "dom", "mth", "dow"};
   private static final int[] MINIMUMS = new int[]{0, 0, 0, 0, 1, 1, 1};
   private static final int[] MAXIMUMS = new int[]{49, 59, 59, 23, 31, 12, 7};
   private static final int[] CALCODES = new int[]{14, 13, 12, 11, 5, 2, 7};

   private static void handleParseError(char[] s, int[] ppos, String err) throws ParseException {
      String spaces = "";

      for(int i = ppos[0]; i > 0; --i) {
         spaces = spaces + ' ';
      }

      throw new ParseException("Parse error at or before position " + ppos[0] + " in scheduling string:\n   \"" + new String(s) + "\"\n    " + spaces + "^ " + err, ppos[0]);
   }

   private static int parseInt(char[] s, int[] ppos, int min, int max, int div) throws ParseException {
      if (ppos[0] >= s.length) {
         handleParseError(s, ppos, "Unexpected end of string. Expected '0'-'9', 'l', or 'last'.");
      }

      char c = s[ppos[0]];
      int var10002;
      if (c != 'l' && c != 'L') {
         if (c < '0' || c > '9') {
            handleParseError(s, ppos, "Expected '0'-'9', 'l', or 'last'.");
         }

         var10002 = ppos[0]++;

         int ret;
         for(ret = c - 48; ppos[0] != s.length && (c = s[ppos[0]]) >= '0' && c <= '9'; ret = ret * 10 + (c - 48)) {
            var10002 = ppos[0]++;
         }

         if (div == 1) {
            if (ret < min || ret > max) {
               handleParseError(s, ppos, "Value " + ret + " out of range, expect " + min + " <= value <= " + max);
            }
         } else {
            if (ret < min || ret >= (max + 1) * div) {
               handleParseError(s, ppos, "Value " + ret + " out of range, expect " + min + " <= value <= " + ((max + 1) * div - 1));
            }

            ret /= div;
         }

         return ret;
      } else {
         if (++ppos[0] != s.length && (s[ppos[0]] == 'a' || s[ppos[0]] == 'A') && ++ppos[0] != s.length && (s[ppos[0]] == 's' || s[ppos[0]] == 'S') && ++ppos[0] != s.length && (s[ppos[0]] == 't' || s[ppos[0]] == 'T')) {
            var10002 = ppos[0]++;
         }

         return max;
      }
   }

   private static long parseRange(char[] s, int[] ppos, int min, int max, int div) throws ParseException {
      long ret = 0L;
      if (ppos[0] >= s.length) {
         handleParseError(s, ppos, "Unexpected end of string.");
      }

      char c = s[ppos[0]];
      int var10002;
      if (c == '*') {
         var10002 = ppos[0]++;
         return -1L;
      } else {
         int start = parseInt(s, ppos, min, max, div);
         ret = 1L << start;
         if (ppos[0] == s.length) {
            return ret;
         } else {
            if (s[ppos[0]] == '-') {
               var10002 = ppos[0]++;
               int end = parseInt(s, ppos, min, max, div);
               if (end < start) {
                  handleParseError(s, ppos, "Start of range (" + start + ") is greater than end of range (" + end + ").");
               }

               while(true) {
                  ++start;
                  if (start > end) {
                     break;
                  }

                  ret |= 1L << start;
               }
            }

            if (ppos[0] == s.length) {
               return ret;
            } else if (s[ppos[0]] == ',') {
               var10002 = ppos[0]++;
               return ret | parseRange(s, ppos, min, max, div);
            } else {
               return ret;
            }
         }
      }
   }

   private static void skipWhiteSpace(char[] s, int[] ppos) {
      while(s.length != ppos[0] && (s[ppos[0]] == ' ' || s[ppos[0]] == '\t')) {
         int var10002 = ppos[0]++;
      }

   }

   private static void parseCron(char[] s, int[] ppos, long[] fields) throws ParseException {
      skipWhiteSpace(s, ppos);

      for(int i = 0; i < MINIMUMS.length; ++i) {
         fields[i] |= parseRange(s, ppos, MINIMUMS[i], MAXIMUMS[i], i == 0 ? 20 : 1);
         if (i < MINIMUMS.length - 1) {
            if (s.length == ppos[0]) {
               handleParseError(s, ppos, "Unexpected end of string.");
            }

            int hold = ppos[0];
            skipWhiteSpace(s, ppos);
            if (ppos[0] == hold) {
               handleParseError(s, ppos, "Expected white space character (tab or space).");
            }
         }
      }

      skipWhiteSpace(s, ppos);
   }

   private static long[][] parseCrons(String str) throws ParseException {
      long[][] schedules = new long[1][MINIMUMS.length];
      long[] fields = schedules[0];
      int[] ppos = new int[1];
      if (str == null) {
         handleParseError("<null>".toCharArray(), ppos, "Attempt to parse null string.");
      }

      char[] s = str.toCharArray();

      try {
         parseCron(s, ppos, fields);

         while(ppos[0] != s.length) {
            if (s[ppos[0]] != ';') {
               handleParseError(s, ppos, "Unexpected character '" + s[ppos[0]] + "', expected ';' or end of string.");
            }

            int var10002 = ppos[0]++;
            long[][] newSchedules = new long[schedules.length + 1][];

            for(int i = 0; i < schedules.length; ++i) {
               newSchedules[i] = schedules[i];
            }

            fields = newSchedules[schedules.length] = new long[MINIMUMS.length];
            schedules = newSchedules;
            parseCron(s, ppos, fields);
         }

         for(int i = 0; i < fields.length; ++i) {
            if (fields[i] == 0L) {
               throw new Throwable("Programming error.");
            }
         }

         return schedules;
      } catch (Throwable var7) {
         if (!(var7 instanceof ParseException)) {
            JMSLogger.logStackTrace(var7);
            handleParseError(s, ppos, "Unhandled exception. " + var7.toString());
         }

         throw (ParseException)var7;
      }
   }

   private static int minimumBitPosition(long l, int min) {
      int pos;
      for(pos = min; (l & 1L << pos) == 0L; ++pos) {
      }

      return pos;
   }

   private static int firstMatchingBitPos(long l, int pos, int min, int max) {
      while(pos <= max) {
         if ((l & 1L << pos) != 0L) {
            return pos;
         }

         ++pos;
      }

      for(pos = min; (l & 1L << pos) == 0L; ++pos) {
      }

      return pos;
   }

   private static int addThis(long l, int pos, int min, int max) {
      int ret;
      for(ret = 0; pos <= max; ++pos) {
         if ((l & 1L << pos) != 0L) {
            return ret;
         }

         ++ret;
      }

      for(pos = min; (l & 1L << pos) == 0L; ++pos) {
         ++ret;
      }

      return ret;
   }

   private static void minimize(int field, Calendar cal, long[] schedules) {
      if (field != 0) {
         if (field == 6) {
            field = 4;
         }

         int milli = minimumBitPosition(schedules[0], MINIMUMS[0]);
         cal.set(14, milli * 20);

         for(int i = 1; i < field; ++i) {
            int min = minimumBitPosition(schedules[i], MINIMUMS[i]);
            if (i == 5) {
               --min;
            } else if (i == 4) {
               int lastDayOfCurrentMonth = cal.getActualMaximum(5);
               if (min > lastDayOfCurrentMonth) {
                  min = lastDayOfCurrentMonth;
               }
            }

            cal.set(CALCODES[i], min);
         }

      }
   }

   private static void scheduleField(int field, Calendar cal, long[] schedules) {
      int val = cal.get(CALCODES[field]);
      if (field == 5) {
         ++val;
      }

      int offset;
      if (field == 0) {
         offset = val % 20;
         if (offset != 0) {
            cal.add(CALCODES[field], 20 - offset);
            val = cal.get(CALCODES[field]);
         }

         val /= 20;
      }

      offset = addThis(schedules[field], val, MINIMUMS[field], MAXIMUMS[field]);
      if (offset != 0) {
         if (field == 0) {
            offset *= 20;
         }

         if (field == 5) {
            cal.set(5, 1);
         }

         cal.add(CALCODES[field], offset);
         minimize(field, cal, schedules);
      }

   }

   private static void nextScheduledTime(long[] schedules, Calendar cal) {
      cal.setFirstDayOfWeek(1);
      cal.add(CALCODES[0], 1);
      if (schedules[0] != -1L) {
         scheduleField(0, cal, schedules);
      }

      if (schedules[1] != -1L) {
         scheduleField(1, cal, schedules);
      }

      if (schedules[2] != -1L) {
         scheduleField(2, cal, schedules);
      }

      if (schedules[3] != -1L) {
         scheduleField(3, cal, schedules);
      }

      int month;
      int dayOfMonth;
      int lastDayOfMonth;
      if (schedules[4] != -1L) {
         month = cal.get(5);
         dayOfMonth = firstMatchingBitPos(schedules[4], month, MINIMUMS[4], MAXIMUMS[4]);
         if (month != dayOfMonth) {
            lastDayOfMonth = cal.getActualMaximum(5);
            if (dayOfMonth > lastDayOfMonth) {
               dayOfMonth = lastDayOfMonth;
            } else if (dayOfMonth < month) {
               cal.set(5, 1);
               cal.add(2, 1);
            }

            cal.set(5, dayOfMonth);
            minimize(4, cal, schedules);
         }
      }

      if (schedules[5] != -1L) {
         scheduleField(5, cal, schedules);
      }

      if (schedules[6] != -1L) {
         scheduleField(6, cal, schedules);
         month = cal.get(2) + 1;
         if ((schedules[5] & 1L << month) == 0L) {
            nextScheduledTime(schedules, cal);
         } else {
            dayOfMonth = cal.get(5);
            if ((schedules[4] & 1L << dayOfMonth) == 0L) {
               lastDayOfMonth = cal.getActualMaximum(5);
               int nextScheduledDayOfMonth = firstMatchingBitPos(schedules[4], dayOfMonth, MINIMUMS[4], MAXIMUMS[4]);
               if (nextScheduledDayOfMonth < lastDayOfMonth || dayOfMonth != lastDayOfMonth) {
                  nextScheduledTime(schedules, cal);
               }
            }
         }
      }

   }

   private static void nextScheduledTime(long[][] schedules, Calendar cal) {
      Calendar calClone = null;
      if (schedules.length > 1) {
         calClone = (Calendar)cal.clone();
      }

      nextScheduledTime(schedules[0], cal);

      for(int i = 1; i < schedules.length; ++i) {
         Calendar calHold = null;
         if (i + 1 < schedules.length) {
            calHold = (Calendar)calClone.clone();
         }

         nextScheduledTime(schedules[i], calClone);
         if (calClone.getTime().getTime() < cal.getTime().getTime()) {
            cal.setTime(calClone.getTime());
         }

         calClone = calHold;
      }

   }

   private static String longAsBitsReversed(long l) {
      int i = 64;
      String s = "";

      while(true) {
         --i;
         if (i < 0) {
            return s;
         }

         if ((l & 1L) != 0L) {
            s = s + '1';
         } else {
            s = s + '0';
         }

         l >>>= 1;
      }
   }

   private static String leftJustify5(String s) {
      while(s.length() < 5) {
         s = ' ' + s;
      }

      return s;
   }

   private static String leftJustify5(int i) {
      return leftJustify5(i + "");
   }

   private static void printCalendar(Calendar calendar, boolean printTitle) {
      String[] s = new String[]{"yr", "dow", "mth", "dom", "hr", "mn", "sec", "ms"};
      int[] i = new int[]{1, 7, 2, 5, 11, 12, 13, 14};
      int j;
      if (printTitle) {
         for(j = i.length - 1; j >= 0; --j) {
            System.out.print(leftJustify5(s[j]));
         }

         System.out.println();
      }

      for(j = i.length - 1; j >= 0; --j) {
         System.out.print(leftJustify5(calendar.get(i[j]) + (i[j] == 2 ? 1 : 0)));
      }

      System.out.println();
   }

   private static void printSchedule(long[][] schedules) {
      System.out.print(leftJustify5("") + " ");

      int i;
      for(i = 0; i < 6; ++i) {
         System.out.print("" + i + "         ");
      }

      System.out.println();
      System.out.print(leftJustify5("") + " ");

      int j;
      for(i = 0; i < 6; ++i) {
         for(j = 0; j <= 9; ++j) {
            System.out.print(j);
         }
      }

      System.out.println();
      System.out.println();

      for(i = 0; i < schedules.length; ++i) {
         if (i > 0) {
            System.out.println();
         }

         for(j = 0; j < schedules[i].length; ++j) {
            System.out.print(leftJustify5(CALNAMES[j]) + " ");
            System.out.println(longAsBitsReversed(schedules[i][j]));
         }
      }

   }

   private static void throwUsage() throws Exception {
      throw new Exception("\nUsage:  java weblogic.jms.extensions.Schedule \"schedule\" [msecs secs mins hrs day mnth yr]\nExample:  java weblogic.jms.extensions.Schedule \"* * * * * * 1\" 0 0 0 0 23 4 2001\n     (first Sunday after 23rd April 2001)");
   }

   private static void testEOM() throws ParseException {
      long[][] schedules = parseCrons("* * * * 31 * *");

      for(int i = 0; i < 367; ++i) {
         Calendar cal = new GregorianCalendar(2001, 0, 1, 0, 0, 0);
         cal.add(6, i);
         printCalendar(cal, true);
         nextScheduledTime((long[][])schedules, cal);
         printCalendar(cal, false);
         System.out.println();
      }

   }

   public static void parseSchedule(String schedule) throws ParseException {
      parseCrons(schedule);
   }

   public static Calendar nextScheduledTime(String schedule, Calendar calendar) throws ParseException {
      int firstDayOfWeek = calendar.getFirstDayOfWeek();
      Calendar calClone = (Calendar)calendar.clone();
      nextScheduledTime(parseCrons(schedule), calClone);
      calClone.setFirstDayOfWeek(firstDayOfWeek);
      return calClone;
   }

   public static Calendar nextScheduledTime(String schedule) throws ParseException {
      return nextScheduledTime((String)schedule, new GregorianCalendar());
   }

   public static long nextScheduledTimeInMillis(String schedule, long timeInMillis) throws ParseException {
      Calendar cal = new GregorianCalendar();
      cal.setTime(new Date(timeInMillis));
      nextScheduledTime((long[][])parseCrons(schedule), cal);
      return cal.getTime().getTime();
   }

   public static long nextScheduledTimeInMillis(String schedule) throws ParseException {
      return nextScheduledTimeInMillis(schedule, System.currentTimeMillis());
   }

   public static long nextScheduledTimeInMillisRelative(String schedule, long timeInMillis) throws ParseException {
      return nextScheduledTimeInMillis(schedule, timeInMillis) - timeInMillis;
   }

   public static long nextScheduledTimeInMillisRelative(String schedule) throws ParseException {
      return nextScheduledTimeInMillisRelative(schedule, System.currentTimeMillis());
   }

   public static void main(String[] args) throws Exception {
      if (args.length == 0) {
         throwUsage();
      }

      Calendar cal = new GregorianCalendar();
      if (args.length > 1) {
         if (args.length != 8) {
            throwUsage();
         }

         int ms = Integer.parseInt(args[1]);
         int secs = Integer.parseInt(args[2]);
         int mins = Integer.parseInt(args[3]);
         int hrs = Integer.parseInt(args[4]);
         int day = Integer.parseInt(args[5]);
         int mnth = Integer.parseInt(args[6]) - 1;
         int yr = Integer.parseInt(args[7]);
         cal = new GregorianCalendar(yr, mnth, day, hrs, mins, secs);
         cal.set(14, ms);
      }

      cal.setFirstDayOfWeek(1);
      long[][] schedules = parseCrons(args[0]);
      printSchedule(schedules);
      System.out.println();
      printCalendar(cal, true);
      nextScheduledTime((long[][])schedules, cal);
      printCalendar(cal, false);
      System.out.println();
   }
}
