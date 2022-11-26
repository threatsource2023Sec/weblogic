package org.python.modules.time;

import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import org.python.core.ClassDictInit;
import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.__builtin__;

public class Time implements ClassDictInit {
   public static final PyString __doc__ = new PyString("This module provides various functions to manipulate time values.\n\nThere are two standard representations of time.  One is the number\nof seconds since the Epoch, in UTC (a.k.a. GMT).  It may be an integer\nor a floating point number (to represent fractions of seconds).\nThe Epoch is system-defined; on Unix, it is generally January 1st, 1970.\nThe actual value can be retrieved by calling gmtime(0).\n\nThe other representation is a tuple of 9 integers giving local time.\nThe tuple items are:\n  year (four digits, e.g. 1998)\n  month (1-12)\n  day (1-31)\n  hours (0-23)\n  minutes (0-59)\n  seconds (0-59)\n  weekday (0-6, Monday is 0)\n  Julian day (day in the year, 1-366)\n  DST (Daylight Savings Time) flag (-1, 0 or 1)\nIf the DST flag is 0, the time is given in the regular time zone;\nif it is 1, the time is given in the DST time zone;\nif it is -1, mktime() should guess based on the date and time.\n\nVariables:\n\ntimezone -- difference in seconds between UTC and local standard time\naltzone -- difference in  seconds between UTC and local DST time\ndaylight -- whether local time should reflect DST\ntzname -- tuple of (standard time zone name, DST time zone name)\n\nFunctions:\n\ntime() -- return current time in seconds since the Epoch as a float\nclock() -- return CPU time since process start as a float\nsleep() -- delay for a number of seconds given as a float\ngmtime() -- convert seconds since Epoch to UTC tuple\nlocaltime() -- convert seconds since Epoch to local time tuple\nasctime() -- convert time tuple to string\nctime() -- convert time in seconds to string\nmktime() -- convert local time tuple to seconds since Epoch\nstrftime() -- convert time tuple to string according to format specification\nstrptime() -- parse string to time tuple according to format specification\n");
   private static final double NANOS_PER_SECOND = 1.0E9;
   private static long initialClock;
   private static volatile boolean clockInitialized;
   protected static Locale currentLocale = null;
   protected static DateFormatSymbols datesyms = new DateFormatSymbols();
   protected static String[] shortdays = null;
   protected static String[] shortmonths = null;
   private static String[] enshortdays = new String[]{"Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"};
   private static String[] enshortmonths = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
   public static int timezone;
   public static int altzone = -1;
   public static int daylight;
   public static PyTuple tzname = null;
   public static final int accept2dyear = 0;
   private static final String DEFAULT_FORMAT_PY = "%a %b %d %H:%M:%S %Y";

   public static void classDictInit(PyObject dict) {
      dict.__setitem__((String)"time", new TimeFunctions("time", 0, 0));
      dict.__setitem__((String)"clock", new TimeFunctions("clock", 1, 0));
      dict.__setitem__((String)"struct_time", PyTimeTuple.TYPE);
      dict.__setitem__((String)"__name__", Py.newString("time"));
      TimeZone tz = TimeZone.getDefault();
      tzname = new PyTuple(new PyObject[]{new PyString(tz.getDisplayName(false, 0)), new PyString(tz.getDisplayName(true, 0))});
      daylight = tz.useDaylightTime() ? 1 : 0;
      timezone = -tz.getRawOffset() / 1000;
      altzone = timezone - tz.getDSTSavings() / 1000;
   }

   public static double time() {
      return (double)System.currentTimeMillis() / 1000.0;
   }

   public static double clock() {
      if (!clockInitialized) {
         initialClock = System.nanoTime();
         clockInitialized = true;
         return 0.0;
      } else {
         return (double)(System.nanoTime() - initialClock) / 1.0E9;
      }
   }

   private static void throwValueError(String msg) {
      throw new PyException(Py.ValueError, new PyString(msg));
   }

   private static int item(PyTuple tup, int i) {
      int val = tup.__getitem__(i).asInt();
      boolean valid = true;
      switch (i) {
         case 0:
         default:
            break;
         case 1:
            valid = 0 <= val && val <= 12;
            break;
         case 2:
            valid = 0 <= val && val <= 31;
            break;
         case 3:
            valid = 0 <= val && val <= 23;
            break;
         case 4:
            valid = 0 <= val && val <= 59;
            break;
         case 5:
            valid = 0 <= val && val <= 61;
            break;
         case 6:
            valid = 0 <= val && val <= 6;
            break;
         case 7:
            valid = 0 <= val && val < 367;
            break;
         case 8:
            valid = -1 <= val && val <= 1;
      }

      if (!valid) {
         String msg;
         switch (i) {
            case 1:
               msg = "month out of range (1-12)";
               break;
            case 2:
               msg = "day out of range (1-31)";
               break;
            case 3:
               msg = "hour out of range (0-23)";
               break;
            case 4:
               msg = "minute out of range (0-59)";
               break;
            case 5:
               msg = "second out of range (0-59)";
               break;
            case 6:
               msg = "day of week out of range (0-6)";
               break;
            case 7:
               msg = "day of year out of range (1-366)";
               break;
            case 8:
               msg = "daylight savings flag out of range (-1,0,1)";
               break;
            default:
               msg = "ignore";
         }

         throwValueError(msg);
      }

      switch (i) {
         case 1:
            if (val > 0) {
               --val;
            }
            break;
         case 2:
         case 7:
            if (val == 0) {
               val = 1;
            }
      }

      return val;
   }

   private static GregorianCalendar _tupletocal(PyTuple tup) {
      return new GregorianCalendar(item(tup, 0), item(tup, 1), item(tup, 2), item(tup, 3), item(tup, 4), item(tup, 5));
   }

   public static double mktime(PyTuple tup) {
      GregorianCalendar cal;
      try {
         cal = _tupletocal(tup);
      } catch (PyException var3) {
         var3.type = Py.OverflowError;
         throw var3;
      }

      int dst = item(tup, 8);
      if (dst == 0 || dst == 1) {
         cal.set(16, dst * cal.getTimeZone().getDSTSavings());
      }

      return (double)cal.getTime().getTime() / 1000.0;
   }

   protected static PyTimeTuple _timefields(double secs, TimeZone tz) {
      GregorianCalendar cal = new GregorianCalendar(tz);
      cal.clear();
      secs *= 1000.0;
      if (!(secs < -9.223372036854776E18) && !(secs > 9.223372036854776E18)) {
         cal.setTime(new Date((long)secs));
         int isdst = tz.inDaylightTime(cal.getTime()) ? 1 : 0;
         return toTimeTuple(cal, isdst);
      } else {
         throw Py.ValueError("timestamp out of range for platform time_t");
      }
   }

   private static PyTimeTuple toTimeTuple(Calendar cal, int isdst) {
      int dow = cal.get(7) - 2;
      if (dow < 0) {
         dow += 7;
      }

      return new PyTimeTuple(new PyObject[]{new PyInteger(cal.get(1)), new PyInteger(cal.get(2) + 1), new PyInteger(cal.get(5)), new PyInteger(cal.get(10) + 12 * cal.get(9)), new PyInteger(cal.get(12)), new PyInteger(cal.get(13)), new PyInteger(dow), new PyInteger(cal.get(6)), new PyInteger(isdst)});
   }

   public static double parseTimeDoubleArg(PyObject arg) {
      if (arg == Py.None) {
         return time();
      } else {
         Object result = arg.__tojava__(Double.class);
         if (result == Py.NoConversion) {
            throw Py.TypeError("a float is required");
         } else {
            return (Double)result;
         }
      }
   }

   public static PyTuple localtime() {
      return localtime(Py.None);
   }

   public static PyTuple localtime(PyObject secs) {
      return _timefields(parseTimeDoubleArg(secs), TimeZone.getDefault());
   }

   public static PyTuple gmtime() {
      return gmtime(Py.None);
   }

   public static PyTuple gmtime(PyObject arg) {
      return _timefields(parseTimeDoubleArg(arg), TimeZone.getTimeZone("GMT"));
   }

   public static PyString ctime() {
      return ctime(Py.None);
   }

   public static PyString ctime(PyObject secs) {
      return asctime(localtime(secs));
   }

   private static synchronized String _shortday(int dow) {
      if (shortdays == null) {
         shortdays = new String[7];
         String[] names = datesyms.getShortWeekdays();

         for(int i = 0; i < 6; ++i) {
            shortdays[i] = names[i + 2];
         }

         shortdays[6] = names[1];
      }

      try {
         return shortdays[dow];
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new PyException(Py.ValueError, new PyString("day of week out of range (0-6)"));
      }
   }

   private static synchronized String _shortmonth(int month0to11) {
      if (shortmonths == null) {
         shortmonths = new String[12];
         String[] names = datesyms.getShortMonths();

         for(int i = 0; i < 12; ++i) {
            shortmonths[i] = names[i];
         }
      }

      try {
         return shortmonths[month0to11];
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new PyException(Py.ValueError, new PyString("month out of range (1-12)"));
      }
   }

   private static String _padint(int i, int target) {
      String s = Integer.toString(i);
      int sz = s.length();
      if (target <= sz) {
         return s;
      } else if (target == sz + 1) {
         return "0" + s;
      } else if (target == sz + 2) {
         return "00" + s;
      } else {
         char[] c = new char[target - sz];
         Arrays.fill(c, '0');
         return new String(c) + s;
      }
   }

   private static String _twodigit(int i) {
      return _padint(i, 2);
   }

   private static String _truncyear(int year) {
      String yearstr = _padint(year, 4);
      return yearstr.substring(yearstr.length() - 2, yearstr.length());
   }

   public static PyString asctime() {
      return asctime(localtime());
   }

   public static PyString asctime(PyObject obj) {
      PyTuple tup;
      if (obj instanceof PyTuple) {
         tup = (PyTuple)obj;
      } else {
         tup = PyTuple.fromIterable(obj);
      }

      int len = tup.__len__();
      if (len != 9) {
         throw Py.TypeError(String.format("argument must be sequence of length 9, not %d", len));
      } else {
         StringBuilder buf = new StringBuilder(25);
         buf.append(enshortdays[item(tup, 6)]).append(' ');
         buf.append(enshortmonths[item(tup, 1)]).append(' ');
         int dayOfMonth = item(tup, 2);
         if (dayOfMonth < 10) {
            buf.append(' ');
         }

         buf.append(dayOfMonth).append(' ');
         buf.append(_twodigit(item(tup, 3))).append(':');
         buf.append(_twodigit(item(tup, 4))).append(':');
         buf.append(_twodigit(item(tup, 5))).append(' ');
         return new PyString(buf.append(item(tup, 0)).toString());
      }
   }

   public static String locale_asctime(PyTuple tup) {
      checkLocale();
      int day = item(tup, 6);
      int mon = item(tup, 1);
      return _shortday(day) + " " + _shortmonth(mon) + " " + _twodigit(item(tup, 2)) + " " + _twodigit(item(tup, 3)) + ":" + _twodigit(item(tup, 4)) + ":" + _twodigit(item(tup, 5)) + " " + item(tup, 0);
   }

   public static void sleep(double secs) {
      if (secs == 0.0) {
         Thread.yield();
      } else {
         try {
            Thread.sleep((long)(secs * 1000.0));
         } catch (InterruptedException var3) {
            throw new PyException(Py.KeyboardInterrupt, "interrupted sleep");
         }
      }

   }

   public static PyString strftime(String format) {
      return strftime(format, localtime());
   }

   public static PyString strftime(String format, PyTuple tup) {
      checkLocale();
      int[] items = new int[9];

      for(int i = 0; i < 9; ++i) {
         items[i] = item(tup, i);
      }

      String s = "";
      int lastc = 0;

      int i;
      for(GregorianCalendar cal = null; lastc < format.length(); ++i) {
         i = format.indexOf("%", lastc);
         if (i < 0) {
            s = s + format.substring(lastc);
            break;
         }

         if (i == format.length() - 1) {
            s = s + "%";
            break;
         }

         s = s + format.substring(lastc, i);
         ++i;
         int j;
         String[] syms;
         switch (format.charAt(i)) {
            case '%':
               s = s + "%";
               break;
            case '&':
            case '\'':
            case '(':
            case ')':
            case '*':
            case '+':
            case ',':
            case '-':
            case '.':
            case '/':
            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
            case ':':
            case ';':
            case '<':
            case '=':
            case '>':
            case '?':
            case '@':
            case 'C':
            case 'D':
            case 'E':
            case 'F':
            case 'G':
            case 'J':
            case 'K':
            case 'L':
            case 'N':
            case 'O':
            case 'P':
            case 'Q':
            case 'R':
            case 'T':
            case 'V':
            case '[':
            case '\\':
            case ']':
            case '^':
            case '_':
            case '`':
            case 'e':
            case 'f':
            case 'g':
            case 'h':
            case 'i':
            case 'k':
            case 'l':
            case 'n':
            case 'o':
            case 'q':
            case 'r':
            case 's':
            case 't':
            case 'u':
            case 'v':
            default:
               s = s + "%" + format.charAt(i);
               ++i;
               break;
            case 'A':
               syms = datesyms.getWeekdays();
               j = items[6];
               if (0 <= j && j < 6) {
                  s = s + syms[j + 2];
               } else if (j == 6) {
                  s = s + syms[1];
               } else {
                  throwValueError("day of week out of range (0 - 6)");
               }
               break;
            case 'B':
               syms = datesyms.getMonths();
               j = items[1];
               s = s + syms[j];
               break;
            case 'H':
               s = s + _twodigit(items[3]);
               break;
            case 'I':
               j = items[3] % 12;
               if (j == 0) {
                  j = 12;
               }

               s = s + _twodigit(j);
               break;
            case 'M':
               s = s + _twodigit(items[4]);
               break;
            case 'S':
               s = s + _twodigit(items[5]);
               break;
            case 'U':
               if (cal == null) {
                  cal = _tupletocal(tup);
               }

               cal.setFirstDayOfWeek(1);
               cal.setMinimalDaysInFirstWeek(7);
               j = cal.get(3);
               if (cal.get(2) == 0 && j >= 52) {
                  j = 0;
               }

               s = s + _twodigit(j);
               break;
            case 'W':
               if (cal == null) {
                  cal = _tupletocal(tup);
               }

               cal.setFirstDayOfWeek(2);
               cal.setMinimalDaysInFirstWeek(7);
               j = cal.get(3);
               if (cal.get(2) == 0 && j >= 52) {
                  j = 0;
               }

               s = s + _twodigit(j);
               break;
            case 'X':
               s = s + _twodigit(items[3]) + ":" + _twodigit(items[4]) + ":" + _twodigit(items[5]);
               break;
            case 'Y':
               s = s + _padint(items[0], 4);
               break;
            case 'Z':
               if (cal == null) {
                  cal = _tupletocal(tup);
               }

               s = s + cal.getTimeZone().getDisplayName(items[8] > 0, 0);
               break;
            case 'a':
               j = items[6];
               s = s + _shortday(j);
               break;
            case 'b':
               j = items[1];
               s = s + _shortmonth(j);
               break;
            case 'c':
               s = s + locale_asctime(tup);
               break;
            case 'd':
               s = s + _twodigit(items[2]);
               break;
            case 'j':
               s = s + _padint(items[7], 3);
               break;
            case 'm':
               s = s + _twodigit(items[1] + 1);
               break;
            case 'p':
               j = items[3];
               syms = datesyms.getAmPmStrings();
               if (0 <= j && j < 12) {
                  s = s + syms[0];
               } else if (12 <= j && j < 24) {
                  s = s + syms[1];
               } else {
                  throwValueError("hour out of range (0-23)");
               }
               break;
            case 'w':
               j = (items[6] + 1) % 7;
               s = s + j;
               break;
            case 'x':
               s = s + _twodigit(items[1] + 1) + "/" + _twodigit(items[2]) + "/" + _truncyear(items[0]);
               break;
            case 'y':
               s = s + _truncyear(items[0]);
         }

         lastc = i + 1;
      }

      return Py.newStringUTF8(s);
   }

   private static void checkLocale() {
      if (!Locale.getDefault().equals(currentLocale)) {
         currentLocale = Locale.getDefault();
         datesyms = new DateFormatSymbols(currentLocale);
         shortdays = null;
         shortmonths = null;
      }

   }

   public static PyTuple strptime(String data_string) {
      return strptime(data_string, "%a %b %d %H:%M:%S %Y");
   }

   private static PyTuple pystrptime(String data_string, String format) {
      return (PyTuple)__builtin__.__import__("_strptime").invoke("_strptime_time", (PyObject)Py.newUnicode(data_string), (PyObject)Py.newUnicode(format));
   }

   public static PyTuple strptime(String data_string, String format) {
      return pystrptime(data_string, format);
   }
}
