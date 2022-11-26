import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("_strptime.py")
public class _strptime$py extends PyFunctionTable implements PyRunnable {
   static _strptime$py self;
   static final PyCode f$0;
   static final PyCode _getlang$1;
   static final PyCode LocaleTime$2;
   static final PyCode __init__$3;
   static final PyCode _LocaleTime__pad$4;
   static final PyCode _LocaleTime__calc_weekday$5;
   static final PyCode _LocaleTime__calc_month$6;
   static final PyCode _LocaleTime__calc_am_pm$7;
   static final PyCode _LocaleTime__calc_date_time$8;
   static final PyCode _LocaleTime__calc_timezone$9;
   static final PyCode TimeRE$10;
   static final PyCode __init__$11;
   static final PyCode f$12;
   static final PyCode _TimeRE__seqToRE$13;
   static final PyCode f$14;
   static final PyCode pattern$15;
   static final PyCode compile$16;
   static final PyCode _calc_julian_from_U_or_W$17;
   static final PyCode _strptime$18;
   static final PyCode _strptime_time$19;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Strptime-related classes and functions.\n\nCLASSES:\n    LocaleTime -- Discovers and stores locale-specific time information\n    TimeRE -- Creates regexes for pattern matching a string of text containing\n                time information\n\nFUNCTIONS:\n    _getlang -- Figure out what language is being used for the locale\n    strptime -- Calculates the time struct represented by the passed-in string\n\n"));
      var1.setline(12);
      PyString.fromInterned("Strptime-related classes and functions.\n\nCLASSES:\n    LocaleTime -- Discovers and stores locale-specific time information\n    TimeRE -- Creates regexes for pattern matching a string of text containing\n                time information\n\nFUNCTIONS:\n    _getlang -- Figure out what language is being used for the locale\n    strptime -- Calculates the time struct represented by the passed-in string\n\n");
      var1.setline(13);
      PyObject var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(14);
      var3 = imp.importOne("locale", var1, -1);
      var1.setlocal("locale", var3);
      var3 = null;
      var1.setline(15);
      var3 = imp.importOne("calendar", var1, -1);
      var1.setlocal("calendar", var3);
      var3 = null;
      var1.setline(16);
      String[] var7 = new String[]{"compile"};
      PyObject[] var8 = imp.importFrom("re", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("re_compile", var4);
      var4 = null;
      var1.setline(17);
      var7 = new String[]{"IGNORECASE"};
      var8 = imp.importFrom("re", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("IGNORECASE", var4);
      var4 = null;
      var1.setline(18);
      var7 = new String[]{"escape"};
      var8 = imp.importFrom("re", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("re_escape", var4);
      var4 = null;
      var1.setline(19);
      var7 = new String[]{"date"};
      var8 = imp.importFrom("datetime", var7, var1, -1);
      var4 = var8[0];
      var1.setlocal("datetime_date", var4);
      var4 = null;

      try {
         var1.setline(21);
         var7 = new String[]{"allocate_lock"};
         var8 = imp.importFrom("thread", var7, var1, -1);
         var4 = var8[0];
         var1.setlocal("_thread_allocate_lock", var4);
         var4 = null;
      } catch (Throwable var6) {
         Py.setException(var6, var1);
         var1.setline(23);
         String[] var9 = new String[]{"allocate_lock"};
         PyObject[] var10 = imp.importFrom("dummy_thread", var9, var1, -1);
         PyObject var5 = var10[0];
         var1.setlocal("_thread_allocate_lock", var5);
         var5 = null;
      }

      var1.setline(25);
      PyList var11 = new PyList(Py.EmptyObjects);
      var1.setlocal("__all__", var11);
      var3 = null;
      var1.setline(27);
      var8 = Py.EmptyObjects;
      PyFunction var12 = new PyFunction(var1.f_globals, var8, _getlang$1, (PyObject)null);
      var1.setlocal("_getlang", var12);
      var3 = null;
      var1.setline(31);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("LocaleTime", var8, LocaleTime$2);
      var1.setlocal("LocaleTime", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(176);
      var8 = new PyObject[]{var1.getname("dict")};
      var4 = Py.makeClass("TimeRE", var8, TimeRE$10);
      var1.setlocal("TimeRE", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(267);
      var3 = var1.getname("_thread_allocate_lock").__call__(var2);
      var1.setlocal("_cache_lock", var3);
      var3 = null;
      var1.setline(270);
      var3 = var1.getname("TimeRE").__call__(var2);
      var1.setlocal("_TimeRE_cache", var3);
      var3 = null;
      var1.setline(271);
      PyInteger var13 = Py.newInteger(5);
      var1.setlocal("_CACHE_MAX_SIZE", var13);
      var3 = null;
      var1.setline(272);
      PyDictionary var14 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("_regex_cache", var14);
      var3 = null;
      var1.setline(274);
      var8 = Py.EmptyObjects;
      var12 = new PyFunction(var1.f_globals, var8, _calc_julian_from_U_or_W$17, PyString.fromInterned("Calculate the Julian day based on the year, week of the year, and day of\n    the week, with week_start_day representing whether the week of the year\n    assumes the week starts on Sunday or Monday (6 or 0)."));
      var1.setlocal("_calc_julian_from_U_or_W", var12);
      var3 = null;
      var1.setline(295);
      var8 = new PyObject[]{PyString.fromInterned("%a %b %d %H:%M:%S %Y")};
      var12 = new PyFunction(var1.f_globals, var8, _strptime$18, PyString.fromInterned("Return a time struct based on the input string and the format string."));
      var1.setlocal("_strptime", var12);
      var3 = null;
      var1.setline(466);
      var8 = new PyObject[]{PyString.fromInterned("%a %b %d %H:%M:%S %Y")};
      var12 = new PyFunction(var1.f_globals, var8, _strptime_time$19, (PyObject)null);
      var1.setlocal("_strptime_time", var12);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _getlang$1(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getglobal("locale").__getattr__("getlocale").__call__(var2, var1.getglobal("locale").__getattr__("LC_TIME"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject LocaleTime$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Stores and handles locale-specific information related to time.\n\n    ATTRIBUTES:\n        f_weekday -- full weekday names (7-item list)\n        a_weekday -- abbreviated weekday names (7-item list)\n        f_month -- full month names (13-item list; dummy value in [0], which\n                    is added by code)\n        a_month -- abbreviated month names (13-item list, dummy value in\n                    [0], which is added by code)\n        am_pm -- AM/PM representation (2-item list)\n        LC_date_time -- format string for date/time representation (string)\n        LC_date -- format string for date representation (string)\n        LC_time -- format string for time representation (string)\n        timezone -- daylight- and non-daylight-savings timezone representation\n                    (2-item list of sets)\n        lang -- Language used by instance (2-item tuple)\n    "));
      var1.setline(48);
      PyString.fromInterned("Stores and handles locale-specific information related to time.\n\n    ATTRIBUTES:\n        f_weekday -- full weekday names (7-item list)\n        a_weekday -- abbreviated weekday names (7-item list)\n        f_month -- full month names (13-item list; dummy value in [0], which\n                    is added by code)\n        a_month -- abbreviated month names (13-item list, dummy value in\n                    [0], which is added by code)\n        am_pm -- AM/PM representation (2-item list)\n        LC_date_time -- format string for date/time representation (string)\n        LC_date -- format string for date representation (string)\n        LC_time -- format string for time representation (string)\n        timezone -- daylight- and non-daylight-savings timezone representation\n                    (2-item list of sets)\n        lang -- Language used by instance (2-item tuple)\n    ");
      var1.setline(50);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, PyString.fromInterned("Set all attributes.\n\n        Order of methods called matters for dependency reasons.\n\n        The locale language is set at the offset and then checked again before\n        exiting.  This is to make sure that the attributes were not set with a\n        mix of information from more than one locale.  This would most likely\n        happen when using threads where one thread calls a locale-dependent\n        function while another thread changes the locale while the function in\n        the other thread is still running.  Proper coding would call for\n        locks to prevent changing the locale while locale-dependent code is\n        running.  The check here is done in case someone does not think about\n        doing this.\n\n        Only other possible issue is if someone changed the timezone and did\n        not call tz.tzset .  That is an issue for the programmer, though,\n        since changing the timezone is worthless without that call.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(79);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _LocaleTime__pad$4, (PyObject)null);
      var1.setlocal("_LocaleTime__pad", var4);
      var3 = null;
      var1.setline(88);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _LocaleTime__calc_weekday$5, (PyObject)null);
      var1.setlocal("_LocaleTime__calc_weekday", var4);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _LocaleTime__calc_month$6, (PyObject)null);
      var1.setlocal("_LocaleTime__calc_month", var4);
      var3 = null;
      var1.setline(103);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _LocaleTime__calc_am_pm$7, (PyObject)null);
      var1.setlocal("_LocaleTime__calc_am_pm", var4);
      var3 = null;
      var1.setline(115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _LocaleTime__calc_date_time$8, (PyObject)null);
      var1.setlocal("_LocaleTime__calc_date_time", var4);
      var3 = null;
      var1.setline(160);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _LocaleTime__calc_timezone$9, (PyObject)null);
      var1.setlocal("_LocaleTime__calc_timezone", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyString.fromInterned("Set all attributes.\n\n        Order of methods called matters for dependency reasons.\n\n        The locale language is set at the offset and then checked again before\n        exiting.  This is to make sure that the attributes were not set with a\n        mix of information from more than one locale.  This would most likely\n        happen when using threads where one thread calls a locale-dependent\n        function while another thread changes the locale while the function in\n        the other thread is still running.  Proper coding would call for\n        locks to prevent changing the locale while locale-dependent code is\n        running.  The check here is done in case someone does not think about\n        doing this.\n\n        Only other possible issue is if someone changed the timezone and did\n        not call tz.tzset .  That is an issue for the programmer, though,\n        since changing the timezone is worthless without that call.\n\n        ");
      var1.setline(70);
      PyObject var3 = var1.getglobal("_getlang").__call__(var2);
      var1.getlocal(0).__setattr__("lang", var3);
      var3 = null;
      var1.setline(71);
      var1.getlocal(0).__getattr__("_LocaleTime__calc_weekday").__call__(var2);
      var1.setline(72);
      var1.getlocal(0).__getattr__("_LocaleTime__calc_month").__call__(var2);
      var1.setline(73);
      var1.getlocal(0).__getattr__("_LocaleTime__calc_am_pm").__call__(var2);
      var1.setline(74);
      var1.getlocal(0).__getattr__("_LocaleTime__calc_timezone").__call__(var2);
      var1.setline(75);
      var1.getlocal(0).__getattr__("_LocaleTime__calc_date_time").__call__(var2);
      var1.setline(76);
      var3 = var1.getglobal("_getlang").__call__(var2);
      PyObject var10000 = var3._ne(var1.getlocal(0).__getattr__("lang"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(77);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("locale changed during initialization")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _LocaleTime__pad$4(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(82);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(83);
         var1.getlocal(1).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned(""));
      } else {
         var1.setline(85);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
      }

      var1.setline(86);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _LocaleTime__calc_weekday$5(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(91);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(7)).__iter__();

      while(true) {
         var1.setline(91);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(91);
            var1.dellocal(2);
            PyList var5 = var10000;
            var1.setlocal(1, var5);
            var3 = null;
            var1.setline(92);
            var10000 = new PyList();
            var3 = var10000.__getattr__("append");
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(92);
            var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(7)).__iter__();

            while(true) {
               var1.setline(92);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(92);
                  var1.dellocal(5);
                  var5 = var10000;
                  var1.setlocal(4, var5);
                  var3 = null;
                  var1.setline(93);
                  var3 = var1.getlocal(1);
                  var1.getlocal(0).__setattr__("a_weekday", var3);
                  var3 = null;
                  var1.setline(94);
                  var3 = var1.getlocal(4);
                  var1.getlocal(0).__setattr__("f_weekday", var3);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(3, var4);
               var1.setline(92);
               var1.getlocal(5).__call__(var2, var1.getglobal("calendar").__getattr__("day_name").__getitem__(var1.getlocal(3)).__getattr__("lower").__call__(var2));
            }
         }

         var1.setlocal(3, var4);
         var1.setline(91);
         var1.getlocal(2).__call__(var2, var1.getglobal("calendar").__getattr__("day_abbr").__getitem__(var1.getlocal(3)).__getattr__("lower").__call__(var2));
      }
   }

   public PyObject _LocaleTime__calc_month$6(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(98);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(13)).__iter__();

      while(true) {
         var1.setline(98);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(98);
            var1.dellocal(2);
            PyList var5 = var10000;
            var1.setlocal(1, var5);
            var3 = null;
            var1.setline(99);
            var10000 = new PyList();
            var3 = var10000.__getattr__("append");
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(99);
            var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(13)).__iter__();

            while(true) {
               var1.setline(99);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(99);
                  var1.dellocal(5);
                  var5 = var10000;
                  var1.setlocal(4, var5);
                  var3 = null;
                  var1.setline(100);
                  var3 = var1.getlocal(1);
                  var1.getlocal(0).__setattr__("a_month", var3);
                  var3 = null;
                  var1.setline(101);
                  var3 = var1.getlocal(4);
                  var1.getlocal(0).__setattr__("f_month", var3);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(3, var4);
               var1.setline(99);
               var1.getlocal(5).__call__(var2, var1.getglobal("calendar").__getattr__("month_name").__getitem__(var1.getlocal(3)).__getattr__("lower").__call__(var2));
            }
         }

         var1.setlocal(3, var4);
         var1.setline(98);
         var1.getlocal(2).__call__(var2, var1.getglobal("calendar").__getattr__("month_abbr").__getitem__(var1.getlocal(3)).__getattr__("lower").__call__(var2));
      }
   }

   public PyObject _LocaleTime__calc_am_pm$7(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(110);
      PyObject var6 = (new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(22)})).__iter__();

      while(true) {
         var1.setline(110);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(113);
            var6 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("am_pm", var6);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(111);
         PyObject var5 = var1.getglobal("time").__getattr__("struct_time").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1999), Py.newInteger(3), Py.newInteger(17), var1.getlocal(2), Py.newInteger(44), Py.newInteger(55), Py.newInteger(2), Py.newInteger(76), Py.newInteger(0)})));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(112);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%p"), (PyObject)var1.getlocal(3)).__getattr__("lower").__call__(var2));
      }
   }

   public PyObject _LocaleTime__calc_date_time$8(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyObject var3 = var1.getglobal("time").__getattr__("struct_time").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1999), Py.newInteger(3), Py.newInteger(17), Py.newInteger(22), Py.newInteger(44), Py.newInteger(55), Py.newInteger(2), Py.newInteger(76), Py.newInteger(0)})));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(124);
      PyList var9 = new PyList(new PyObject[]{var1.getglobal("None"), var1.getglobal("None"), var1.getglobal("None")});
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(125);
      var3 = var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%c"), (PyObject)var1.getlocal(1)).__getattr__("lower").__call__(var2);
      var1.getlocal(2).__setitem__((PyObject)Py.newInteger(0), var3);
      var3 = null;
      var1.setline(126);
      var3 = var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%x"), (PyObject)var1.getlocal(1)).__getattr__("lower").__call__(var2);
      var1.getlocal(2).__setitem__((PyObject)Py.newInteger(1), var3);
      var3 = null;
      var1.setline(127);
      var3 = var1.getglobal("time").__getattr__("strftime").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%X"), (PyObject)var1.getlocal(1)).__getattr__("lower").__call__(var2);
      var1.getlocal(2).__setitem__((PyObject)Py.newInteger(2), var3);
      var3 = null;
      var1.setline(128);
      var9 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("%"), PyString.fromInterned("%%")}), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("f_weekday").__getitem__(Py.newInteger(2)), PyString.fromInterned("%A")}), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("f_month").__getitem__(Py.newInteger(3)), PyString.fromInterned("%B")}), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("a_weekday").__getitem__(Py.newInteger(2)), PyString.fromInterned("%a")}), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("a_month").__getitem__(Py.newInteger(3)), PyString.fromInterned("%b")}), new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("am_pm").__getitem__(Py.newInteger(1)), PyString.fromInterned("%p")}), new PyTuple(new PyObject[]{PyString.fromInterned("1999"), PyString.fromInterned("%Y")}), new PyTuple(new PyObject[]{PyString.fromInterned("99"), PyString.fromInterned("%y")}), new PyTuple(new PyObject[]{PyString.fromInterned("22"), PyString.fromInterned("%H")}), new PyTuple(new PyObject[]{PyString.fromInterned("44"), PyString.fromInterned("%M")}), new PyTuple(new PyObject[]{PyString.fromInterned("55"), PyString.fromInterned("%S")}), new PyTuple(new PyObject[]{PyString.fromInterned("76"), PyString.fromInterned("%j")}), new PyTuple(new PyObject[]{PyString.fromInterned("17"), PyString.fromInterned("%d")}), new PyTuple(new PyObject[]{PyString.fromInterned("03"), PyString.fromInterned("%m")}), new PyTuple(new PyObject[]{PyString.fromInterned("3"), PyString.fromInterned("%m")}), new PyTuple(new PyObject[]{PyString.fromInterned("2"), PyString.fromInterned("%w")}), new PyTuple(new PyObject[]{PyString.fromInterned("10"), PyString.fromInterned("%I")})});
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(136);
      PyObject var10000 = var1.getlocal(3).__getattr__("extend");
      PyList var10002 = new PyList();
      var3 = var10002.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(136);
      var3 = var1.getlocal(0).__getattr__("timezone").__iter__();

      while(true) {
         var1.setline(136);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         PyObject var6;
         if (var4 == null) {
            var1.setline(136);
            var1.dellocal(4);
            var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.setline(138);
            var3 = (new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(0), PyString.fromInterned("%c")}), new PyTuple(new PyObject[]{Py.newInteger(1), PyString.fromInterned("%x")}), new PyTuple(new PyObject[]{Py.newInteger(2), PyString.fromInterned("%X")})})).__iter__();

            while(true) {
               var1.setline(138);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(156);
                  var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
                  var1.getlocal(0).__setattr__("LC_date_time", var3);
                  var3 = null;
                  var1.setline(157);
                  var3 = var1.getlocal(2).__getitem__(Py.newInteger(1));
                  var1.getlocal(0).__setattr__("LC_date", var3);
                  var3 = null;
                  var1.setline(158);
                  var3 = var1.getlocal(2).__getitem__(Py.newInteger(2));
                  var1.getlocal(0).__setattr__("LC_time", var3);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               PyObject[] var10 = Py.unpackSequence(var4, 2);
               var6 = var10[0];
               var1.setlocal(7, var6);
               var6 = null;
               var6 = var10[1];
               var1.setlocal(8, var6);
               var6 = null;
               var1.setline(139);
               var5 = var1.getlocal(2).__getitem__(var1.getlocal(7));
               var1.setlocal(9, var5);
               var5 = null;
               var1.setline(140);
               var5 = var1.getlocal(3).__iter__();

               while(true) {
                  var1.setline(140);
                  var6 = var5.__iternext__();
                  if (var6 == null) {
                     var1.setline(150);
                     var5 = var1.getglobal("time").__getattr__("struct_time").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1999), Py.newInteger(1), Py.newInteger(3), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1), Py.newInteger(6), Py.newInteger(3), Py.newInteger(0)})));
                     var1.setlocal(1, var5);
                     var5 = null;
                     var1.setline(151);
                     PyString var11 = PyString.fromInterned("00");
                     var10000 = var11._in(var1.getglobal("time").__getattr__("strftime").__call__(var2, var1.getlocal(8), var1.getlocal(1)));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(152);
                        var11 = PyString.fromInterned("%W");
                        var1.setlocal(12, var11);
                        var5 = null;
                     } else {
                        var1.setline(154);
                        var11 = PyString.fromInterned("%U");
                        var1.setlocal(12, var11);
                        var5 = null;
                     }

                     var1.setline(155);
                     var5 = var1.getlocal(9).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("11"), (PyObject)var1.getlocal(12));
                     var1.getlocal(2).__setitem__(var1.getlocal(7), var5);
                     var5 = null;
                     break;
                  }

                  PyObject[] var7 = Py.unpackSequence(var6, 2);
                  PyObject var8 = var7[0];
                  var1.setlocal(10, var8);
                  var8 = null;
                  var8 = var7[1];
                  var1.setlocal(11, var8);
                  var8 = null;
                  var1.setline(145);
                  if (var1.getlocal(10).__nonzero__()) {
                     var1.setline(146);
                     PyObject var12 = var1.getlocal(9).__getattr__("replace").__call__(var2, var1.getlocal(10), var1.getlocal(11));
                     var1.setlocal(9, var12);
                     var7 = null;
                  }
               }
            }
         }

         var1.setlocal(6, var4);
         var1.setline(137);
         var5 = var1.getlocal(6).__iter__();

         while(true) {
            var1.setline(137);
            var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(5, var6);
            var1.setline(136);
            var1.getlocal(4).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), PyString.fromInterned("%Z")})));
         }
      }
   }

   public PyObject _LocaleTime__calc_timezone$9(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(165);
         var1.getglobal("time").__getattr__("tzset").__call__(var2);
      } catch (Throwable var4) {
         var3 = Py.setException(var4, var1);
         if (!var3.match(var1.getglobal("AttributeError"))) {
            throw var3;
         }

         var1.setline(167);
      }

      var1.setline(168);
      PyObject var5 = var1.getglobal("frozenset").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("utc"), PyString.fromInterned("gmt"), var1.getglobal("time").__getattr__("tzname").__getitem__(Py.newInteger(0)).__getattr__("lower").__call__(var2)})));
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(169);
      if (var1.getglobal("time").__getattr__("daylight").__nonzero__()) {
         var1.setline(170);
         var5 = var1.getglobal("frozenset").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("time").__getattr__("tzname").__getitem__(Py.newInteger(1)).__getattr__("lower").__call__(var2)})));
         var1.setlocal(2, var5);
         var3 = null;
      } else {
         var1.setline(172);
         var5 = var1.getglobal("frozenset").__call__(var2);
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(173);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.getlocal(0).__setattr__((String)"timezone", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TimeRE$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Handle conversion from format directives to regexes."));
      var1.setline(177);
      PyString.fromInterned("Handle conversion from format directives to regexes.");
      var1.setline(179);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$11, PyString.fromInterned("Create keys/values.\n\n        Order of execution is important for dependency reasons.\n\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(221);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _TimeRE__seqToRE$13, PyString.fromInterned("Convert a list to a regex string for matching a directive.\n\n        Want possible matching values to be from longest to shortest.  This\n        prevents the possibility of a match occuring for a value that also\n        a substring of a larger value that should have matched (e.g., 'abc'\n        matching when 'abcdef' should have been the match).\n\n        "));
      var1.setlocal("_TimeRE__seqToRE", var4);
      var3 = null;
      var1.setline(240);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, pattern$15, PyString.fromInterned("Return regex pattern for the format string.\n\n        Need to make sure that any characters that might be interpreted as\n        regex syntax are escaped.\n\n        "));
      var1.setlocal("pattern", var4);
      var3 = null;
      var1.setline(263);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, compile$16, PyString.fromInterned("Return a compiled re object for the format string."));
      var1.setlocal("compile", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$11(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyString.fromInterned("Create keys/values.\n\n        Order of execution is important for dependency reasons.\n\n        ");
      var1.setline(185);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(186);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("locale_time", var3);
         var3 = null;
      } else {
         var1.setline(188);
         var3 = var1.getglobal("LocaleTime").__call__(var2);
         var1.getlocal(0).__setattr__("locale_time", var3);
         var3 = null;
      }

      var1.setline(189);
      var3 = var1.getglobal("super").__call__(var2, var1.getglobal("TimeRE"), var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(190);
      PyObject var10000 = var1.getlocal(2).__getattr__("__init__");
      PyObject[] var10004 = new PyObject[]{PyString.fromInterned("d"), PyString.fromInterned("(?P<d>3[0-1]|[1-2]\\d|0[1-9]|[1-9]| [1-9])"), PyString.fromInterned("f"), PyString.fromInterned("(?P<f>[0-9]{1,6})"), PyString.fromInterned("H"), PyString.fromInterned("(?P<H>2[0-3]|[0-1]\\d|\\d)"), PyString.fromInterned("I"), PyString.fromInterned("(?P<I>1[0-2]|0[1-9]|[1-9])"), PyString.fromInterned("j"), PyString.fromInterned("(?P<j>36[0-6]|3[0-5]\\d|[1-2]\\d\\d|0[1-9]\\d|00[1-9]|[1-9]\\d|0[1-9]|[1-9])"), PyString.fromInterned("m"), PyString.fromInterned("(?P<m>1[0-2]|0[1-9]|[1-9])"), PyString.fromInterned("M"), PyString.fromInterned("(?P<M>[0-5]\\d|\\d)"), PyString.fromInterned("S"), PyString.fromInterned("(?P<S>6[0-1]|[0-5]\\d|\\d)"), PyString.fromInterned("U"), PyString.fromInterned("(?P<U>5[0-3]|[0-4]\\d|\\d)"), PyString.fromInterned("w"), PyString.fromInterned("(?P<w>[0-6])"), PyString.fromInterned("y"), PyString.fromInterned("(?P<y>\\d\\d)"), PyString.fromInterned("Y"), PyString.fromInterned("(?P<Y>\\d\\d\\d\\d)"), PyString.fromInterned("A"), var1.getlocal(0).__getattr__("_TimeRE__seqToRE").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("locale_time").__getattr__("f_weekday"), (PyObject)PyString.fromInterned("A")), PyString.fromInterned("a"), var1.getlocal(0).__getattr__("_TimeRE__seqToRE").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("locale_time").__getattr__("a_weekday"), (PyObject)PyString.fromInterned("a")), PyString.fromInterned("B"), var1.getlocal(0).__getattr__("_TimeRE__seqToRE").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("locale_time").__getattr__("f_month").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("B")), PyString.fromInterned("b"), var1.getlocal(0).__getattr__("_TimeRE__seqToRE").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("locale_time").__getattr__("a_month").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("b")), PyString.fromInterned("p"), var1.getlocal(0).__getattr__("_TimeRE__seqToRE").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("locale_time").__getattr__("am_pm"), (PyObject)PyString.fromInterned("p")), PyString.fromInterned("Z"), null, null, null};
      PyObject var10007 = var1.getlocal(0).__getattr__("_TimeRE__seqToRE");
      var1.setline(212);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var5, f$12, (PyObject)null);
      PyObject var10009 = var4.__call__(var2, var1.getlocal(0).__getattr__("locale_time").__getattr__("timezone").__iter__());
      Arrays.fill(var5, (Object)null);
      var10004[35] = var10007.__call__((ThreadState)var2, (PyObject)var10009, (PyObject)PyString.fromInterned("Z"));
      var10004[36] = PyString.fromInterned("%");
      var10004[37] = PyString.fromInterned("%");
      var10000.__call__((ThreadState)var2, (PyObject)(new PyDictionary(var10004)));
      var1.setline(216);
      var1.getlocal(2).__getattr__("__setitem__").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("W"), (PyObject)var1.getlocal(2).__getattr__("__getitem__").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("U")).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("U"), (PyObject)PyString.fromInterned("W")));
      var1.setline(217);
      var1.getlocal(2).__getattr__("__setitem__").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("c"), (PyObject)var1.getlocal(0).__getattr__("pattern").__call__(var2, var1.getlocal(0).__getattr__("locale_time").__getattr__("LC_date_time")));
      var1.setline(218);
      var1.getlocal(2).__getattr__("__setitem__").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("x"), (PyObject)var1.getlocal(0).__getattr__("pattern").__call__(var2, var1.getlocal(0).__getattr__("locale_time").__getattr__("LC_date")));
      var1.setline(219);
      var1.getlocal(2).__getattr__("__setitem__").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("X"), (PyObject)var1.getlocal(0).__getattr__("pattern").__call__(var2, var1.getlocal(0).__getattr__("locale_time").__getattr__("LC_time")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$12(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      PyObject var5;
      PyObject var6;
      Object[] var7;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(212);
            var3 = var1.getlocal(0).__iter__();
            var1.setline(212);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(213);
            var5 = var1.getlocal(2).__iter__();
            break;
         case 1:
            var7 = var1.f_savedlocals;
            var3 = (PyObject)var7[3];
            var4 = (PyObject)var7[4];
            var5 = (PyObject)var7[5];
            var6 = (PyObject)var7[6];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
      }

      while(true) {
         var1.setline(213);
         var6 = var5.__iternext__();
         if (var6 != null) {
            var1.setlocal(1, var6);
            var1.setline(212);
            var1.setline(212);
            var8 = var1.getlocal(1);
            var1.f_lasti = 1;
            var7 = new Object[]{null, null, null, var3, var4, var5, var6};
            var1.f_savedlocals = var7;
            return var8;
         }

         var1.setline(212);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(213);
         var5 = var1.getlocal(2).__iter__();
      }
   }

   public PyObject _TimeRE__seqToRE$13(PyFrame var1, ThreadState var2) {
      var1.setline(229);
      PyString.fromInterned("Convert a list to a regex string for matching a directive.\n\n        Want possible matching values to be from longest to shortest.  This\n        prevents the possibility of a match occuring for a value that also\n        a substring of a larger value that should have matched (e.g., 'abc'\n        matching when 'abcdef' should have been the match).\n\n        ");
      var1.setline(230);
      PyObject var10000 = var1.getglobal("sorted");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("len"), var1.getglobal("True")};
      String[] var4 = new String[]{"key", "reverse"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(231);
      var6 = var1.getlocal(1).__iter__();

      PyObject var5;
      do {
         var1.setline(231);
         PyObject var7 = var6.__iternext__();
         if (var7 == null) {
            var1.setline(235);
            PyString var9 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var9;
         }

         var1.setlocal(3, var7);
         var1.setline(232);
         var5 = var1.getlocal(3);
         var10000 = var5._ne(PyString.fromInterned(""));
         var5 = null;
      } while(!var10000.__nonzero__());

      var1.setline(236);
      var10000 = PyString.fromInterned("|").__getattr__("join");
      var1.setline(236);
      var3 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var3, f$14, (PyObject)null);
      PyObject var10002 = var8.__call__(var2, var1.getlocal(1).__iter__());
      Arrays.fill(var3, (Object)null);
      var6 = var10000.__call__(var2, var10002);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(237);
      var6 = PyString.fromInterned("(?P<%s>%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(4)}));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(238);
      var5 = PyString.fromInterned("%s)")._mod(var1.getlocal(4));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$14(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(236);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(236);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(236);
         var1.setline(236);
         var6 = var1.getglobal("re_escape").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject pattern$15(PyFrame var1, ThreadState var2) {
      var1.setline(246);
      PyString.fromInterned("Return regex pattern for the format string.\n\n        Need to make sure that any characters that might be interpreted as\n        regex syntax are escaped.\n\n        ");
      var1.setline(247);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(251);
      PyObject var4 = var1.getglobal("re_compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([\\\\.^$*+?\\(\\){}\\[\\]|])"));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(252);
      var4 = var1.getlocal(3).__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\\\\\1"), (PyObject)var1.getlocal(1));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(253);
      var4 = var1.getglobal("re_compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s+"));
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(254);
      var4 = var1.getlocal(4).__getattr__("sub").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\s+"), (PyObject)var1.getlocal(1));
      var1.setlocal(1, var4);
      var3 = null;

      while(true) {
         var1.setline(255);
         var3 = PyString.fromInterned("%");
         PyObject var10000 = var3._in(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(261);
            var4 = PyString.fromInterned("%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)}));
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(256);
         var4 = var1.getlocal(1).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"))._add(Py.newInteger(1));
         var1.setlocal(5, var4);
         var3 = null;
         var1.setline(257);
         var4 = PyString.fromInterned("%s%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(5)._sub(Py.newInteger(1)), (PyObject)null), var1.getlocal(0).__getitem__(var1.getlocal(1).__getitem__(var1.getlocal(5)))}));
         var1.setlocal(2, var4);
         var3 = null;
         var1.setline(260);
         var4 = var1.getlocal(1).__getslice__(var1.getlocal(5)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
         var1.setlocal(1, var4);
         var3 = null;
      }
   }

   public PyObject compile$16(PyFrame var1, ThreadState var2) {
      var1.setline(264);
      PyString.fromInterned("Return a compiled re object for the format string.");
      var1.setline(265);
      PyObject var3 = var1.getglobal("re_compile").__call__(var2, var1.getlocal(0).__getattr__("pattern").__call__(var2, var1.getlocal(1)), var1.getglobal("IGNORECASE"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _calc_julian_from_U_or_W$17(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      PyString.fromInterned("Calculate the Julian day based on the year, week of the year, and day of\n    the week, with week_start_day representing whether the week of the year\n    assumes the week starts on Sunday or Monday (6 or 0).");
      var1.setline(278);
      PyObject var3 = var1.getglobal("datetime_date").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(1)).__getattr__("weekday").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(282);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(283);
         var3 = var1.getlocal(4)._add(Py.newInteger(1))._mod(Py.newInteger(7));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(284);
         var3 = var1.getlocal(2)._add(Py.newInteger(1))._mod(Py.newInteger(7));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(287);
      var3 = Py.newInteger(7)._sub(var1.getlocal(4))._mod(Py.newInteger(7));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(288);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(289);
         var3 = Py.newInteger(1)._add(var1.getlocal(2))._sub(var1.getlocal(4));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(291);
         PyObject var4 = var1.getlocal(5)._add(Py.newInteger(7)._mul(var1.getlocal(1)._sub(Py.newInteger(1))));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(292);
         var3 = Py.newInteger(1)._add(var1.getlocal(6))._add(var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _strptime$18(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(296);
      PyString.fromInterned("Return a time struct based on the input string and the format string.");
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("_cache_lock"))).__enter__(var2);

      PyObject var5;
      PyObject var10000;
      label223: {
         try {
            var1.setline(299);
            var4 = var1.getglobal("_getlang").__call__(var2);
            var10000 = var4._ne(var1.getglobal("_TimeRE_cache").__getattr__("locale_time").__getattr__("lang"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(300);
               var4 = var1.getglobal("TimeRE").__call__(var2);
               var1.setglobal("_TimeRE_cache", var4);
               var4 = null;
               var1.setline(301);
               var1.getglobal("_regex_cache").__getattr__("clear").__call__(var2);
            }

            var1.setline(302);
            var4 = var1.getglobal("len").__call__(var2, var1.getglobal("_regex_cache"));
            var10000 = var4._gt(var1.getglobal("_CACHE_MAX_SIZE"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(303);
               var1.getglobal("_regex_cache").__getattr__("clear").__call__(var2);
            }

            var1.setline(304);
            var4 = var1.getglobal("_TimeRE_cache").__getattr__("locale_time");
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(305);
            var4 = var1.getglobal("_regex_cache").__getattr__("get").__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(306);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               try {
                  var1.setline(308);
                  var4 = var1.getglobal("_TimeRE_cache").__getattr__("compile").__call__(var2, var1.getlocal(1));
                  var1.setlocal(3, var4);
                  var4 = null;
               } catch (Throwable var9) {
                  PyException var15 = Py.setException(var9, var1);
                  if (var15.match(var1.getglobal("KeyError"))) {
                     var5 = var15.value;
                     var1.setlocal(4, var5);
                     var5 = null;
                     var1.setline(312);
                     var5 = var1.getlocal(4).__getattr__("args").__getitem__(Py.newInteger(0));
                     var1.setlocal(5, var5);
                     var5 = null;
                     var1.setline(313);
                     var5 = var1.getlocal(5);
                     var10000 = var5._eq(PyString.fromInterned("\\"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(314);
                        PyString var13 = PyString.fromInterned("%");
                        var1.setlocal(5, var13);
                        var5 = null;
                     }

                     var1.setline(315);
                     var1.dellocal(4);
                     var1.setline(316);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("'%s' is a bad directive in format '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(1)}))));
                  }

                  if (var15.match(var1.getglobal("IndexError"))) {
                     var1.setline(320);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("stray %% in format '%s'")._mod(var1.getlocal(1))));
                  }

                  throw var15;
               }

               var1.setline(321);
               var4 = var1.getlocal(3);
               var1.getglobal("_regex_cache").__setitem__(var1.getlocal(1), var4);
               var4 = null;
            }
         } catch (Throwable var10) {
            if (var3.__exit__(var2, Py.setException(var10, var1))) {
               break label223;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.setline(322);
      PyObject var11 = var1.getlocal(3).__getattr__("match").__call__(var2, var1.getlocal(0));
      var1.setlocal(6, var11);
      var3 = null;
      var1.setline(323);
      if (var1.getlocal(6).__not__().__nonzero__()) {
         var1.setline(324);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("time data %r does not match format %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)}))));
      } else {
         var1.setline(326);
         var11 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var11._ne(var1.getlocal(6).__getattr__("end").__call__(var2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(327);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("unconverted data remains: %s")._mod(var1.getlocal(0).__getslice__(var1.getlocal(6).__getattr__("end").__call__(var2), (PyObject)null, (PyObject)null))));
         } else {
            var1.setline(330);
            var11 = var1.getglobal("None");
            var1.setlocal(7, var11);
            var3 = null;
            var1.setline(331);
            PyInteger var12 = Py.newInteger(1);
            var1.setlocal(8, var12);
            var1.setlocal(9, var12);
            var1.setline(332);
            var12 = Py.newInteger(0);
            var1.setlocal(10, var12);
            var1.setlocal(11, var12);
            var1.setlocal(12, var12);
            var1.setlocal(13, var12);
            var1.setline(333);
            var12 = Py.newInteger(-1);
            var1.setlocal(14, var12);
            var3 = null;
            var1.setline(336);
            var12 = Py.newInteger(-1);
            var1.setlocal(15, var12);
            var3 = null;
            var1.setline(337);
            var12 = Py.newInteger(-1);
            var1.setlocal(16, var12);
            var3 = null;
            var1.setline(340);
            var12 = Py.newInteger(-1);
            var1.setlocal(17, var12);
            var1.setlocal(18, var12);
            var1.setline(341);
            var11 = var1.getlocal(6).__getattr__("groupdict").__call__(var2);
            var1.setlocal(19, var11);
            var3 = null;
            var1.setline(342);
            var11 = var1.getlocal(19).__getattr__("iterkeys").__call__(var2).__iter__();

            while(true) {
               label202:
               while(true) {
                  var1.setline(342);
                  var4 = var11.__iternext__();
                  if (var4 == null) {
                     var1.setline(429);
                     var11 = var1.getglobal("False");
                     var1.setlocal(26, var11);
                     var3 = null;
                     var1.setline(430);
                     var11 = var1.getlocal(7);
                     var10000 = var11._is(var1.getglobal("None"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var11 = var1.getlocal(8);
                        var10000 = var11._eq(Py.newInteger(2));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var11 = var1.getlocal(9);
                           var10000 = var11._eq(Py.newInteger(29));
                           var3 = null;
                        }
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(431);
                        var12 = Py.newInteger(1904);
                        var1.setlocal(7, var12);
                        var3 = null;
                        var1.setline(432);
                        var11 = var1.getglobal("True");
                        var1.setlocal(26, var11);
                        var3 = null;
                     } else {
                        var1.setline(433);
                        var11 = var1.getlocal(7);
                        var10000 = var11._is(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(434);
                           var12 = Py.newInteger(1900);
                           var1.setlocal(7, var12);
                           var3 = null;
                        }
                     }

                     var1.setline(437);
                     var11 = var1.getlocal(18);
                     var10000 = var11._eq(Py.newInteger(-1));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var11 = var1.getlocal(15);
                        var10000 = var11._ne(Py.newInteger(-1));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var11 = var1.getlocal(17);
                           var10000 = var11._ne(Py.newInteger(-1));
                           var3 = null;
                        }
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(438);
                        var1.setline(438);
                        var11 = var1.getlocal(16);
                        var10000 = var11._eq(Py.newInteger(0));
                        var3 = null;
                        var11 = var10000.__nonzero__() ? var1.getglobal("True") : var1.getglobal("False");
                        var1.setlocal(27, var11);
                        var3 = null;
                        var1.setline(439);
                        var11 = var1.getglobal("_calc_julian_from_U_or_W").__call__(var2, var1.getlocal(7), var1.getlocal(15), var1.getlocal(17), var1.getlocal(27));
                        var1.setlocal(18, var11);
                        var3 = null;
                     }

                     var1.setline(444);
                     var11 = var1.getlocal(18);
                     var10000 = var11._eq(Py.newInteger(-1));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(446);
                        var11 = var1.getglobal("datetime_date").__call__(var2, var1.getlocal(7), var1.getlocal(8), var1.getlocal(9)).__getattr__("toordinal").__call__(var2)._sub(var1.getglobal("datetime_date").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(1)).__getattr__("toordinal").__call__(var2))._add(Py.newInteger(1));
                        var1.setlocal(18, var11);
                        var3 = null;
                     } else {
                        var1.setline(450);
                        var11 = var1.getglobal("datetime_date").__getattr__("fromordinal").__call__(var2, var1.getlocal(18)._sub(Py.newInteger(1))._add(var1.getglobal("datetime_date").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(1)).__getattr__("toordinal").__call__(var2)));
                        var1.setlocal(28, var11);
                        var3 = null;
                        var1.setline(451);
                        var11 = var1.getlocal(28).__getattr__("year");
                        var1.setlocal(7, var11);
                        var3 = null;
                        var1.setline(452);
                        var11 = var1.getlocal(28).__getattr__("month");
                        var1.setlocal(8, var11);
                        var3 = null;
                        var1.setline(453);
                        var11 = var1.getlocal(28).__getattr__("day");
                        var1.setlocal(9, var11);
                        var3 = null;
                     }

                     var1.setline(454);
                     var11 = var1.getlocal(17);
                     var10000 = var11._eq(Py.newInteger(-1));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(455);
                        var11 = var1.getglobal("datetime_date").__call__(var2, var1.getlocal(7), var1.getlocal(8), var1.getlocal(9)).__getattr__("weekday").__call__(var2);
                        var1.setlocal(17, var11);
                        var3 = null;
                     }

                     var1.setline(456);
                     if (var1.getlocal(26).__nonzero__()) {
                        var1.setline(460);
                        var12 = Py.newInteger(1900);
                        var1.setlocal(7, var12);
                        var3 = null;
                     }

                     var1.setline(462);
                     PyTuple var17 = new PyTuple(new PyObject[]{var1.getglobal("time").__getattr__("struct_time").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), var1.getlocal(12), var1.getlocal(17), var1.getlocal(18), var1.getlocal(14)}))), var1.getlocal(13)});
                     var1.f_lasti = -1;
                     return var17;
                  }

                  var1.setlocal(20, var4);
                  var1.setline(348);
                  var5 = var1.getlocal(20);
                  var10000 = var5._eq(PyString.fromInterned("y"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(349);
                     var5 = var1.getglobal("int").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("y")));
                     var1.setlocal(7, var5);
                     var5 = null;
                     var1.setline(353);
                     var5 = var1.getlocal(7);
                     var10000 = var5._le(Py.newInteger(68));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(354);
                        var5 = var1.getlocal(7);
                        var5 = var5._iadd(Py.newInteger(2000));
                        var1.setlocal(7, var5);
                     } else {
                        var1.setline(356);
                        var5 = var1.getlocal(7);
                        var5 = var5._iadd(Py.newInteger(1900));
                        var1.setlocal(7, var5);
                     }
                  } else {
                     var1.setline(357);
                     var5 = var1.getlocal(20);
                     var10000 = var5._eq(PyString.fromInterned("Y"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(358);
                        var5 = var1.getglobal("int").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("Y")));
                        var1.setlocal(7, var5);
                        var5 = null;
                     } else {
                        var1.setline(359);
                        var5 = var1.getlocal(20);
                        var10000 = var5._eq(PyString.fromInterned("m"));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(360);
                           var5 = var1.getglobal("int").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("m")));
                           var1.setlocal(8, var5);
                           var5 = null;
                        } else {
                           var1.setline(361);
                           var5 = var1.getlocal(20);
                           var10000 = var5._eq(PyString.fromInterned("B"));
                           var5 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(362);
                              var5 = var1.getlocal(2).__getattr__("f_month").__getattr__("index").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("B")).__getattr__("lower").__call__(var2));
                              var1.setlocal(8, var5);
                              var5 = null;
                           } else {
                              var1.setline(363);
                              var5 = var1.getlocal(20);
                              var10000 = var5._eq(PyString.fromInterned("b"));
                              var5 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(364);
                                 var5 = var1.getlocal(2).__getattr__("a_month").__getattr__("index").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("b")).__getattr__("lower").__call__(var2));
                                 var1.setlocal(8, var5);
                                 var5 = null;
                              } else {
                                 var1.setline(365);
                                 var5 = var1.getlocal(20);
                                 var10000 = var5._eq(PyString.fromInterned("d"));
                                 var5 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(366);
                                    var5 = var1.getglobal("int").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("d")));
                                    var1.setlocal(9, var5);
                                    var5 = null;
                                 } else {
                                    var1.setline(367);
                                    var5 = var1.getlocal(20);
                                    var10000 = var5._eq(PyString.fromInterned("H"));
                                    var5 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(368);
                                       var5 = var1.getglobal("int").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("H")));
                                       var1.setlocal(10, var5);
                                       var5 = null;
                                    } else {
                                       var1.setline(369);
                                       var5 = var1.getlocal(20);
                                       var10000 = var5._eq(PyString.fromInterned("I"));
                                       var5 = null;
                                       PyInteger var16;
                                       if (var10000.__nonzero__()) {
                                          var1.setline(370);
                                          var5 = var1.getglobal("int").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("I")));
                                          var1.setlocal(10, var5);
                                          var5 = null;
                                          var1.setline(371);
                                          var5 = var1.getlocal(19).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("p"), (PyObject)PyString.fromInterned("")).__getattr__("lower").__call__(var2);
                                          var1.setlocal(21, var5);
                                          var5 = null;
                                          var1.setline(373);
                                          var5 = var1.getlocal(21);
                                          var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getlocal(2).__getattr__("am_pm").__getitem__(Py.newInteger(0))}));
                                          var5 = null;
                                          if (var10000.__nonzero__()) {
                                             var1.setline(377);
                                             var5 = var1.getlocal(10);
                                             var10000 = var5._eq(Py.newInteger(12));
                                             var5 = null;
                                             if (var10000.__nonzero__()) {
                                                var1.setline(378);
                                                var16 = Py.newInteger(0);
                                                var1.setlocal(10, var16);
                                                var5 = null;
                                             }
                                          } else {
                                             var1.setline(379);
                                             var5 = var1.getlocal(21);
                                             var10000 = var5._eq(var1.getlocal(2).__getattr__("am_pm").__getitem__(Py.newInteger(1)));
                                             var5 = null;
                                             if (var10000.__nonzero__()) {
                                                var1.setline(383);
                                                var5 = var1.getlocal(10);
                                                var10000 = var5._ne(Py.newInteger(12));
                                                var5 = null;
                                                if (var10000.__nonzero__()) {
                                                   var1.setline(384);
                                                   var5 = var1.getlocal(10);
                                                   var5 = var5._iadd(Py.newInteger(12));
                                                   var1.setlocal(10, var5);
                                                }
                                             }
                                          }
                                       } else {
                                          var1.setline(385);
                                          var5 = var1.getlocal(20);
                                          var10000 = var5._eq(PyString.fromInterned("M"));
                                          var5 = null;
                                          if (var10000.__nonzero__()) {
                                             var1.setline(386);
                                             var5 = var1.getglobal("int").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("M")));
                                             var1.setlocal(11, var5);
                                             var5 = null;
                                          } else {
                                             var1.setline(387);
                                             var5 = var1.getlocal(20);
                                             var10000 = var5._eq(PyString.fromInterned("S"));
                                             var5 = null;
                                             if (var10000.__nonzero__()) {
                                                var1.setline(388);
                                                var5 = var1.getglobal("int").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("S")));
                                                var1.setlocal(12, var5);
                                                var5 = null;
                                             } else {
                                                var1.setline(389);
                                                var5 = var1.getlocal(20);
                                                var10000 = var5._eq(PyString.fromInterned("f"));
                                                var5 = null;
                                                if (var10000.__nonzero__()) {
                                                   var1.setline(390);
                                                   var5 = var1.getlocal(19).__getitem__(PyString.fromInterned("f"));
                                                   var1.setlocal(22, var5);
                                                   var5 = null;
                                                   var1.setline(392);
                                                   var5 = var1.getlocal(22);
                                                   var5 = var5._iadd(PyString.fromInterned("0")._mul(Py.newInteger(6)._sub(var1.getglobal("len").__call__(var2, var1.getlocal(22)))));
                                                   var1.setlocal(22, var5);
                                                   var1.setline(393);
                                                   var5 = var1.getglobal("int").__call__(var2, var1.getlocal(22));
                                                   var1.setlocal(13, var5);
                                                   var5 = null;
                                                } else {
                                                   var1.setline(394);
                                                   var5 = var1.getlocal(20);
                                                   var10000 = var5._eq(PyString.fromInterned("A"));
                                                   var5 = null;
                                                   if (var10000.__nonzero__()) {
                                                      var1.setline(395);
                                                      var5 = var1.getlocal(2).__getattr__("f_weekday").__getattr__("index").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("A")).__getattr__("lower").__call__(var2));
                                                      var1.setlocal(17, var5);
                                                      var5 = null;
                                                   } else {
                                                      var1.setline(396);
                                                      var5 = var1.getlocal(20);
                                                      var10000 = var5._eq(PyString.fromInterned("a"));
                                                      var5 = null;
                                                      if (var10000.__nonzero__()) {
                                                         var1.setline(397);
                                                         var5 = var1.getlocal(2).__getattr__("a_weekday").__getattr__("index").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("a")).__getattr__("lower").__call__(var2));
                                                         var1.setlocal(17, var5);
                                                         var5 = null;
                                                      } else {
                                                         var1.setline(398);
                                                         var5 = var1.getlocal(20);
                                                         var10000 = var5._eq(PyString.fromInterned("w"));
                                                         var5 = null;
                                                         if (var10000.__nonzero__()) {
                                                            var1.setline(399);
                                                            var5 = var1.getglobal("int").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("w")));
                                                            var1.setlocal(17, var5);
                                                            var5 = null;
                                                            var1.setline(400);
                                                            var5 = var1.getlocal(17);
                                                            var10000 = var5._eq(Py.newInteger(0));
                                                            var5 = null;
                                                            if (var10000.__nonzero__()) {
                                                               var1.setline(401);
                                                               var16 = Py.newInteger(6);
                                                               var1.setlocal(17, var16);
                                                               var5 = null;
                                                            } else {
                                                               var1.setline(403);
                                                               var5 = var1.getlocal(17);
                                                               var5 = var5._isub(Py.newInteger(1));
                                                               var1.setlocal(17, var5);
                                                            }
                                                         } else {
                                                            var1.setline(404);
                                                            var5 = var1.getlocal(20);
                                                            var10000 = var5._eq(PyString.fromInterned("j"));
                                                            var5 = null;
                                                            if (var10000.__nonzero__()) {
                                                               var1.setline(405);
                                                               var5 = var1.getglobal("int").__call__(var2, var1.getlocal(19).__getitem__(PyString.fromInterned("j")));
                                                               var1.setlocal(18, var5);
                                                               var5 = null;
                                                            } else {
                                                               var1.setline(406);
                                                               var5 = var1.getlocal(20);
                                                               var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("U"), PyString.fromInterned("W")}));
                                                               var5 = null;
                                                               if (var10000.__nonzero__()) {
                                                                  var1.setline(407);
                                                                  var5 = var1.getglobal("int").__call__(var2, var1.getlocal(19).__getitem__(var1.getlocal(20)));
                                                                  var1.setlocal(15, var5);
                                                                  var5 = null;
                                                                  var1.setline(408);
                                                                  var5 = var1.getlocal(20);
                                                                  var10000 = var5._eq(PyString.fromInterned("U"));
                                                                  var5 = null;
                                                                  if (var10000.__nonzero__()) {
                                                                     var1.setline(410);
                                                                     var16 = Py.newInteger(6);
                                                                     var1.setlocal(16, var16);
                                                                     var5 = null;
                                                                  } else {
                                                                     var1.setline(413);
                                                                     var16 = Py.newInteger(0);
                                                                     var1.setlocal(16, var16);
                                                                     var5 = null;
                                                                  }
                                                               } else {
                                                                  var1.setline(414);
                                                                  var5 = var1.getlocal(20);
                                                                  var10000 = var5._eq(PyString.fromInterned("Z"));
                                                                  var5 = null;
                                                                  if (var10000.__nonzero__()) {
                                                                     var1.setline(417);
                                                                     var5 = var1.getlocal(19).__getitem__(PyString.fromInterned("Z")).__getattr__("lower").__call__(var2);
                                                                     var1.setlocal(23, var5);
                                                                     var5 = null;
                                                                     var1.setline(418);
                                                                     var5 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(2).__getattr__("timezone")).__iter__();

                                                                     PyObject[] var7;
                                                                     PyObject var14;
                                                                     do {
                                                                        var1.setline(418);
                                                                        PyObject var6 = var5.__iternext__();
                                                                        if (var6 == null) {
                                                                           continue label202;
                                                                        }

                                                                        var7 = Py.unpackSequence(var6, 2);
                                                                        PyObject var8 = var7[0];
                                                                        var1.setlocal(24, var8);
                                                                        var8 = null;
                                                                        var8 = var7[1];
                                                                        var1.setlocal(25, var8);
                                                                        var8 = null;
                                                                        var1.setline(419);
                                                                        var14 = var1.getlocal(23);
                                                                        var10000 = var14._in(var1.getlocal(25));
                                                                        var7 = null;
                                                                     } while(!var10000.__nonzero__());

                                                                     var1.setline(423);
                                                                     var14 = var1.getglobal("time").__getattr__("tzname").__getitem__(Py.newInteger(0));
                                                                     var10000 = var14._eq(var1.getglobal("time").__getattr__("tzname").__getitem__(Py.newInteger(1)));
                                                                     var7 = null;
                                                                     if (var10000.__nonzero__()) {
                                                                        var10000 = var1.getglobal("time").__getattr__("daylight");
                                                                        if (var10000.__nonzero__()) {
                                                                           var14 = var1.getlocal(23);
                                                                           var10000 = var14._notin(new PyTuple(new PyObject[]{PyString.fromInterned("utc"), PyString.fromInterned("gmt")}));
                                                                           var7 = null;
                                                                        }
                                                                     }

                                                                     if (!var10000.__nonzero__()) {
                                                                        var1.setline(427);
                                                                        var14 = var1.getlocal(24);
                                                                        var1.setlocal(14, var14);
                                                                        var7 = null;
                                                                     }
                                                                  }
                                                               }
                                                            }
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject _strptime_time$19(PyFrame var1, ThreadState var2) {
      var1.setline(467);
      PyObject var3 = var1.getglobal("_strptime").__call__(var2, var1.getlocal(0), var1.getlocal(1)).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public _strptime$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _getlang$1 = Py.newCode(0, var2, var1, "_getlang", 27, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LocaleTime$2 = Py.newCode(0, var2, var1, "LocaleTime", 31, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$3 = Py.newCode(1, var2, var1, "__init__", 50, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "seq", "front"};
      _LocaleTime__pad$4 = Py.newCode(3, var2, var1, "_LocaleTime__pad", 79, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a_weekday", "_[91_21]", "i", "f_weekday", "_[92_21]"};
      _LocaleTime__calc_weekday$5 = Py.newCode(1, var2, var1, "_LocaleTime__calc_weekday", 88, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a_month", "_[98_19]", "i", "f_month", "_[99_19]"};
      _LocaleTime__calc_month$6 = Py.newCode(1, var2, var1, "_LocaleTime__calc_month", 96, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "am_pm", "hour", "time_tuple"};
      _LocaleTime__calc_am_pm$7 = Py.newCode(1, var2, var1, "_LocaleTime__calc_am_pm", 103, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "time_tuple", "date_time", "replacement_pairs", "_[136_34]", "tz", "tz_values", "offset", "directive", "current_format", "old", "new", "U_W"};
      _LocaleTime__calc_date_time$8 = Py.newCode(1, var2, var1, "_LocaleTime__calc_date_time", 115, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "no_saving", "has_saving"};
      _LocaleTime__calc_timezone$9 = Py.newCode(1, var2, var1, "_LocaleTime__calc_timezone", 160, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TimeRE$10 = Py.newCode(0, var2, var1, "TimeRE", 176, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "locale_time", "base", "_(212_33)"};
      __init__$11 = Py.newCode(2, var2, var1, "__init__", 179, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "tz", "tz_names"};
      f$12 = Py.newCode(1, var2, var1, "<genexpr>", 212, false, false, self, 12, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "to_convert", "directive", "value", "regex", "_(236_25)"};
      _TimeRE__seqToRE$13 = Py.newCode(3, var2, var1, "_TimeRE__seqToRE", 221, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "stuff"};
      f$14 = Py.newCode(1, var2, var1, "<genexpr>", 236, false, false, self, 14, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "format", "processed_format", "regex_chars", "whitespace_replacement", "directive_index"};
      pattern$15 = Py.newCode(2, var2, var1, "pattern", 240, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format"};
      compile$16 = Py.newCode(2, var2, var1, "compile", 263, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"year", "week_of_year", "day_of_week", "week_starts_Mon", "first_weekday", "week_0_length", "days_to_week"};
      _calc_julian_from_U_or_W$17 = Py.newCode(4, var2, var1, "_calc_julian_from_U_or_W", 274, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data_string", "format", "locale_time", "format_regex", "err", "bad_directive", "found", "year", "month", "day", "hour", "minute", "second", "fraction", "tz", "week_of_year", "week_of_year_start", "weekday", "julian", "found_dict", "group_key", "ampm", "s", "found_zone", "value", "tz_values", "leap_year_fix", "week_starts_Mon", "datetime_result"};
      _strptime$18 = Py.newCode(2, var2, var1, "_strptime", 295, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"data_string", "format"};
      _strptime_time$19 = Py.newCode(2, var2, var1, "_strptime_time", 466, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _strptime$py("_strptime$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_strptime$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._getlang$1(var2, var3);
         case 2:
            return this.LocaleTime$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this._LocaleTime__pad$4(var2, var3);
         case 5:
            return this._LocaleTime__calc_weekday$5(var2, var3);
         case 6:
            return this._LocaleTime__calc_month$6(var2, var3);
         case 7:
            return this._LocaleTime__calc_am_pm$7(var2, var3);
         case 8:
            return this._LocaleTime__calc_date_time$8(var2, var3);
         case 9:
            return this._LocaleTime__calc_timezone$9(var2, var3);
         case 10:
            return this.TimeRE$10(var2, var3);
         case 11:
            return this.__init__$11(var2, var3);
         case 12:
            return this.f$12(var2, var3);
         case 13:
            return this._TimeRE__seqToRE$13(var2, var3);
         case 14:
            return this.f$14(var2, var3);
         case 15:
            return this.pattern$15(var2, var3);
         case 16:
            return this.compile$16(var2, var3);
         case 17:
            return this._calc_julian_from_U_or_W$17(var2, var3);
         case 18:
            return this._strptime$18(var2, var3);
         case 19:
            return this._strptime_time$19(var2, var3);
         default:
            return null;
      }
   }
}
