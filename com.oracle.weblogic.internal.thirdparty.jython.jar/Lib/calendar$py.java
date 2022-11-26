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
@Filename("calendar.py")
public class calendar$py extends PyFunctionTable implements PyRunnable {
   static calendar$py self;
   static final PyCode f$0;
   static final PyCode IllegalMonthError$1;
   static final PyCode __init__$2;
   static final PyCode __str__$3;
   static final PyCode IllegalWeekdayError$4;
   static final PyCode __init__$5;
   static final PyCode __str__$6;
   static final PyCode _localized_month$7;
   static final PyCode f$8;
   static final PyCode __init__$9;
   static final PyCode __getitem__$10;
   static final PyCode __len__$11;
   static final PyCode _localized_day$12;
   static final PyCode __init__$13;
   static final PyCode __getitem__$14;
   static final PyCode __len__$15;
   static final PyCode isleap$16;
   static final PyCode leapdays$17;
   static final PyCode weekday$18;
   static final PyCode monthrange$19;
   static final PyCode Calendar$20;
   static final PyCode __init__$21;
   static final PyCode getfirstweekday$22;
   static final PyCode setfirstweekday$23;
   static final PyCode iterweekdays$24;
   static final PyCode itermonthdates$25;
   static final PyCode itermonthdays2$26;
   static final PyCode itermonthdays$27;
   static final PyCode monthdatescalendar$28;
   static final PyCode monthdays2calendar$29;
   static final PyCode monthdayscalendar$30;
   static final PyCode yeardatescalendar$31;
   static final PyCode yeardays2calendar$32;
   static final PyCode yeardayscalendar$33;
   static final PyCode TextCalendar$34;
   static final PyCode prweek$35;
   static final PyCode formatday$36;
   static final PyCode formatweek$37;
   static final PyCode f$38;
   static final PyCode formatweekday$39;
   static final PyCode formatweekheader$40;
   static final PyCode f$41;
   static final PyCode formatmonthname$42;
   static final PyCode prmonth$43;
   static final PyCode formatmonth$44;
   static final PyCode formatyear$45;
   static final PyCode f$46;
   static final PyCode f$47;
   static final PyCode f$48;
   static final PyCode pryear$49;
   static final PyCode HTMLCalendar$50;
   static final PyCode formatday$51;
   static final PyCode formatweek$52;
   static final PyCode f$53;
   static final PyCode formatweekday$54;
   static final PyCode formatweekheader$55;
   static final PyCode f$56;
   static final PyCode formatmonthname$57;
   static final PyCode formatmonth$58;
   static final PyCode formatyear$59;
   static final PyCode formatyearpage$60;
   static final PyCode TimeEncoding$61;
   static final PyCode __init__$62;
   static final PyCode __enter__$63;
   static final PyCode __exit__$64;
   static final PyCode LocaleTextCalendar$65;
   static final PyCode __init__$66;
   static final PyCode formatweekday$67;
   static final PyCode formatmonthname$68;
   static final PyCode LocaleHTMLCalendar$69;
   static final PyCode __init__$70;
   static final PyCode formatweekday$71;
   static final PyCode formatmonthname$72;
   static final PyCode setfirstweekday$73;
   static final PyCode format$74;
   static final PyCode formatstring$75;
   static final PyCode f$76;
   static final PyCode timegm$77;
   static final PyCode main$78;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Calendar printing functions\n\nNote when comparing these calendars to the ones printed by cal(1): By\ndefault, these calendars have Monday as the first day of the week, and\nSunday as the last (the European convention). Use setfirstweekday() to\nset the first day of the week (0=Monday, 6=Sunday)."));
      var1.setline(6);
      PyString.fromInterned("Calendar printing functions\n\nNote when comparing these calendars to the ones printed by cal(1): By\ndefault, these calendars have Monday as the first day of the week, and\nSunday as the last (the European convention). Use setfirstweekday() to\nset the first day of the week (0=Monday, 6=Sunday).");
      var1.setline(8);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("datetime", var1, -1);
      var1.setlocal("datetime", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOneAs("locale", var1, -1);
      var1.setlocal("_locale", var3);
      var3 = null;
      var1.setline(12);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("IllegalMonthError"), PyString.fromInterned("IllegalWeekdayError"), PyString.fromInterned("setfirstweekday"), PyString.fromInterned("firstweekday"), PyString.fromInterned("isleap"), PyString.fromInterned("leapdays"), PyString.fromInterned("weekday"), PyString.fromInterned("monthrange"), PyString.fromInterned("monthcalendar"), PyString.fromInterned("prmonth"), PyString.fromInterned("month"), PyString.fromInterned("prcal"), PyString.fromInterned("calendar"), PyString.fromInterned("timegm"), PyString.fromInterned("month_name"), PyString.fromInterned("month_abbr"), PyString.fromInterned("day_name"), PyString.fromInterned("day_abbr")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(18);
      var3 = var1.getname("ValueError");
      var1.setlocal("error", var3);
      var3 = null;
      var1.setline(21);
      PyObject[] var7 = new PyObject[]{var1.getname("ValueError")};
      PyObject var4 = Py.makeClass("IllegalMonthError", var7, IllegalMonthError$1);
      var1.setlocal("IllegalMonthError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(28);
      var7 = new PyObject[]{var1.getname("ValueError")};
      var4 = Py.makeClass("IllegalWeekdayError", var7, IllegalWeekdayError$4);
      var1.setlocal("IllegalWeekdayError", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(36);
      PyInteger var9 = Py.newInteger(1);
      var1.setlocal("January", var9);
      var3 = null;
      var1.setline(37);
      var9 = Py.newInteger(2);
      var1.setlocal("February", var9);
      var3 = null;
      var1.setline(40);
      var6 = new PyList(new PyObject[]{Py.newInteger(0), Py.newInteger(31), Py.newInteger(28), Py.newInteger(31), Py.newInteger(30), Py.newInteger(31), Py.newInteger(30), Py.newInteger(31), Py.newInteger(31), Py.newInteger(30), Py.newInteger(31), Py.newInteger(30), Py.newInteger(31)});
      var1.setlocal("mdays", var6);
      var3 = null;
      var1.setline(47);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("_localized_month", var7, _localized_month$7);
      var1.setlocal("_localized_month", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(66);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("_localized_day", var7, _localized_day$12);
      var1.setlocal("_localized_day", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(86);
      var3 = var1.getname("_localized_day").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%A"));
      var1.setlocal("day_name", var3);
      var3 = null;
      var1.setline(87);
      var3 = var1.getname("_localized_day").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%a"));
      var1.setlocal("day_abbr", var3);
      var3 = null;
      var1.setline(90);
      var3 = var1.getname("_localized_month").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%B"));
      var1.setlocal("month_name", var3);
      var3 = null;
      var1.setline(91);
      var3 = var1.getname("_localized_month").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%b"));
      var1.setlocal("month_abbr", var3);
      var3 = null;
      var1.setline(94);
      var3 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(7));
      PyObject[] var8 = Py.unpackSequence(var3, 7);
      PyObject var5 = var8[0];
      var1.setlocal("MONDAY", var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal("TUESDAY", var5);
      var5 = null;
      var5 = var8[2];
      var1.setlocal("WEDNESDAY", var5);
      var5 = null;
      var5 = var8[3];
      var1.setlocal("THURSDAY", var5);
      var5 = null;
      var5 = var8[4];
      var1.setlocal("FRIDAY", var5);
      var5 = null;
      var5 = var8[5];
      var1.setlocal("SATURDAY", var5);
      var5 = null;
      var5 = var8[6];
      var1.setlocal("SUNDAY", var5);
      var5 = null;
      var3 = null;
      var1.setline(97);
      var7 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var7, isleap$16, PyString.fromInterned("Return True for leap years, False for non-leap years."));
      var1.setlocal("isleap", var10);
      var3 = null;
      var1.setline(102);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, leapdays$17, PyString.fromInterned("Return number of leap years in range [y1, y2).\n       Assume y1 <= y2."));
      var1.setlocal("leapdays", var10);
      var3 = null;
      var1.setline(110);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, weekday$18, PyString.fromInterned("Return weekday (0-6 ~ Mon-Sun) for year (1970-...), month (1-12),\n       day (1-31)."));
      var1.setlocal("weekday", var10);
      var3 = null;
      var1.setline(116);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, monthrange$19, PyString.fromInterned("Return weekday (0-6 ~ Mon-Sun) and number of days (28-31) for\n       year, month."));
      var1.setlocal("monthrange", var10);
      var3 = null;
      var1.setline(126);
      var7 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Calendar", var7, Calendar$20);
      var1.setlocal("Calendar", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(259);
      var7 = new PyObject[]{var1.getname("Calendar")};
      var4 = Py.makeClass("TextCalendar", var7, TextCalendar$34);
      var1.setlocal("TextCalendar", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(376);
      var7 = new PyObject[]{var1.getname("Calendar")};
      var4 = Py.makeClass("HTMLCalendar", var7, HTMLCalendar$50);
      var1.setlocal("HTMLCalendar", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(488);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("TimeEncoding", var7, TimeEncoding$61);
      var1.setlocal("TimeEncoding", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(501);
      var7 = new PyObject[]{var1.getname("TextCalendar")};
      var4 = Py.makeClass("LocaleTextCalendar", var7, LocaleTextCalendar$65);
      var1.setlocal("LocaleTextCalendar", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(536);
      var7 = new PyObject[]{var1.getname("HTMLCalendar")};
      var4 = Py.makeClass("LocaleHTMLCalendar", var7, LocaleHTMLCalendar$69);
      var1.setlocal("LocaleHTMLCalendar", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(567);
      var3 = var1.getname("TextCalendar").__call__(var2);
      var1.setlocal("c", var3);
      var3 = null;
      var1.setline(569);
      var3 = var1.getname("c").__getattr__("getfirstweekday");
      var1.setlocal("firstweekday", var3);
      var3 = null;
      var1.setline(571);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, setfirstweekday$73, (PyObject)null);
      var1.setlocal("setfirstweekday", var10);
      var3 = null;
      var1.setline(580);
      var3 = var1.getname("c").__getattr__("monthdayscalendar");
      var1.setlocal("monthcalendar", var3);
      var3 = null;
      var1.setline(581);
      var3 = var1.getname("c").__getattr__("prweek");
      var1.setlocal("prweek", var3);
      var3 = null;
      var1.setline(582);
      var3 = var1.getname("c").__getattr__("formatweek");
      var1.setlocal("week", var3);
      var3 = null;
      var1.setline(583);
      var3 = var1.getname("c").__getattr__("formatweekheader");
      var1.setlocal("weekheader", var3);
      var3 = null;
      var1.setline(584);
      var3 = var1.getname("c").__getattr__("prmonth");
      var1.setlocal("prmonth", var3);
      var3 = null;
      var1.setline(585);
      var3 = var1.getname("c").__getattr__("formatmonth");
      var1.setlocal("month", var3);
      var3 = null;
      var1.setline(586);
      var3 = var1.getname("c").__getattr__("formatyear");
      var1.setlocal("calendar", var3);
      var3 = null;
      var1.setline(587);
      var3 = var1.getname("c").__getattr__("pryear");
      var1.setlocal("prcal", var3);
      var3 = null;
      var1.setline(591);
      var3 = Py.newInteger(7)._mul(Py.newInteger(3))._sub(Py.newInteger(1));
      var1.setlocal("_colwidth", var3);
      var3 = null;
      var1.setline(592);
      var9 = Py.newInteger(6);
      var1.setlocal("_spacing", var9);
      var3 = null;
      var1.setline(595);
      var7 = new PyObject[]{var1.getname("_colwidth"), var1.getname("_spacing")};
      var10 = new PyFunction(var1.f_globals, var7, format$74, PyString.fromInterned("Prints multi-column formatting for year calendars"));
      var1.setlocal("format", var10);
      var3 = null;
      var1.setline(600);
      var7 = new PyObject[]{var1.getname("_colwidth"), var1.getname("_spacing")};
      var10 = new PyFunction(var1.f_globals, var7, formatstring$75, PyString.fromInterned("Returns a string formatted from n strings, centered within n columns."));
      var1.setlocal("formatstring", var10);
      var3 = null;
      var1.setline(606);
      var9 = Py.newInteger(1970);
      var1.setlocal("EPOCH", var9);
      var3 = null;
      var1.setline(607);
      var3 = var1.getname("datetime").__getattr__("date").__call__((ThreadState)var2, var1.getname("EPOCH"), (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(1)).__getattr__("toordinal").__call__(var2);
      var1.setlocal("_EPOCH_ORD", var3);
      var3 = null;
      var1.setline(610);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, timegm$77, PyString.fromInterned("Unrelated but handy function to calculate Unix timestamp from GMT."));
      var1.setlocal("timegm", var10);
      var3 = null;
      var1.setline(620);
      var7 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var7, main$78, (PyObject)null);
      var1.setlocal("main", var10);
      var3 = null;
      var1.setline(712);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(713);
         var1.getname("main").__call__(var2, var1.getname("sys").__getattr__("argv"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject IllegalMonthError$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(22);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(24);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$3, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("month", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$3(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = PyString.fromInterned("bad month number %r; must be 1-12")._mod(var1.getlocal(0).__getattr__("month"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IllegalWeekdayError$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(29);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(31);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$6, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("weekday", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __str__$6(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyObject var3 = PyString.fromInterned("bad weekday number %r; must be 0 (Monday) to 6 (Sunday)")._mod(var1.getlocal(0).__getattr__("weekday"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _localized_month$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(49);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal("_[49_15]", var3);
      var3 = null;
      var1.setline(49);
      var3 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(12)).__iter__();

      while(true) {
         var1.setline(49);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(49);
            var1.dellocal("_[49_15]");
            PyList var5 = var10000;
            var1.setlocal("_months", var5);
            var3 = null;
            var1.setline(50);
            PyObject var8 = var1.getname("_months").__getattr__("insert");
            PyInteger var10002 = Py.newInteger(0);
            var1.setline(50);
            PyObject[] var6 = Py.EmptyObjects;
            var8.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyFunction(var1.f_globals, var6, f$8)));
            var1.setline(52);
            var6 = Py.EmptyObjects;
            PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$9, (PyObject)null);
            var1.setlocal("__init__", var7);
            var3 = null;
            var1.setline(55);
            var6 = Py.EmptyObjects;
            var7 = new PyFunction(var1.f_globals, var6, __getitem__$10, (PyObject)null);
            var1.setlocal("__getitem__", var7);
            var3 = null;
            var1.setline(62);
            var6 = Py.EmptyObjects;
            var7 = new PyFunction(var1.f_globals, var6, __len__$11, (PyObject)null);
            var1.setlocal("__len__", var7);
            var3 = null;
            return var1.getf_locals();
         }

         var1.setlocal("i", var4);
         var1.setline(49);
         var1.getname("_[49_15]").__call__(var2, var1.getname("datetime").__getattr__("date").__call__((ThreadState)var2, Py.newInteger(2001), (PyObject)var1.getname("i")._add(Py.newInteger(1)), (PyObject)Py.newInteger(1)).__getattr__("strftime"));
      }
   }

   public PyObject f$8(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      PyString var3 = PyString.fromInterned("");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __init__$9(PyFrame var1, ThreadState var2) {
      var1.setline(53);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("format", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getitem__$10(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3 = var1.getlocal(0).__getattr__("_months").__getitem__(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(57);
      if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("slice")).__nonzero__()) {
         var1.setline(60);
         var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("format"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(58);
         PyList var10000 = new PyList();
         var3 = var10000.__getattr__("append");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(58);
         var3 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(58);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(58);
               var1.dellocal(3);
               PyList var5 = var10000;
               var1.f_lasti = -1;
               return var5;
            }

            var1.setlocal(4, var4);
            var1.setline(58);
            var1.getlocal(3).__call__(var2, var1.getlocal(4).__call__(var2, var1.getlocal(0).__getattr__("format")));
         }
      }
   }

   public PyObject __len__$11(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyInteger var3 = Py.newInteger(13);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _localized_day$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(69);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal("_[69_13]", var3);
      var3 = null;
      var1.setline(69);
      var3 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(7)).__iter__();

      while(true) {
         var1.setline(69);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(69);
            var1.dellocal("_[69_13]");
            PyList var5 = var10000;
            var1.setlocal("_days", var5);
            var3 = null;
            var1.setline(71);
            PyObject[] var6 = Py.EmptyObjects;
            PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$13, (PyObject)null);
            var1.setlocal("__init__", var7);
            var3 = null;
            var1.setline(74);
            var6 = Py.EmptyObjects;
            var7 = new PyFunction(var1.f_globals, var6, __getitem__$14, (PyObject)null);
            var1.setlocal("__getitem__", var7);
            var3 = null;
            var1.setline(81);
            var6 = Py.EmptyObjects;
            var7 = new PyFunction(var1.f_globals, var6, __len__$15, (PyObject)null);
            var1.setlocal("__len__", var7);
            var3 = null;
            return var1.getf_locals();
         }

         var1.setlocal("i", var4);
         var1.setline(69);
         var1.getname("_[69_13]").__call__(var2, var1.getname("datetime").__getattr__("date").__call__((ThreadState)var2, Py.newInteger(2001), (PyObject)Py.newInteger(1), (PyObject)var1.getname("i")._add(Py.newInteger(1))).__getattr__("strftime"));
      }
   }

   public PyObject __init__$13(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("format", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getitem__$14(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      PyObject var3 = var1.getlocal(0).__getattr__("_days").__getitem__(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(76);
      if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("slice")).__nonzero__()) {
         var1.setline(79);
         var3 = var1.getlocal(2).__call__(var2, var1.getlocal(0).__getattr__("format"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(77);
         PyList var10000 = new PyList();
         var3 = var10000.__getattr__("append");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(77);
         var3 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(77);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(77);
               var1.dellocal(3);
               PyList var5 = var10000;
               var1.f_lasti = -1;
               return var5;
            }

            var1.setlocal(4, var4);
            var1.setline(77);
            var1.getlocal(3).__call__(var2, var1.getlocal(4).__call__(var2, var1.getlocal(0).__getattr__("format")));
         }
      }
   }

   public PyObject __len__$15(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyInteger var3 = Py.newInteger(7);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isleap$16(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyString.fromInterned("Return True for leap years, False for non-leap years.");
      var1.setline(99);
      PyObject var3 = var1.getlocal(0)._mod(Py.newInteger(4));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0)._mod(Py.newInteger(100));
         var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0)._mod(Py.newInteger(400));
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
         }
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject leapdays$17(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyString.fromInterned("Return number of leap years in range [y1, y2).\n       Assume y1 <= y2.");
      var1.setline(105);
      PyObject var3 = var1.getlocal(0);
      var3 = var3._isub(Py.newInteger(1));
      var1.setlocal(0, var3);
      var1.setline(106);
      var3 = var1.getlocal(1);
      var3 = var3._isub(Py.newInteger(1));
      var1.setlocal(1, var3);
      var1.setline(107);
      var3 = var1.getlocal(1)._floordiv(Py.newInteger(4))._sub(var1.getlocal(0)._floordiv(Py.newInteger(4)))._sub(var1.getlocal(1)._floordiv(Py.newInteger(100))._sub(var1.getlocal(0)._floordiv(Py.newInteger(100))))._add(var1.getlocal(1)._floordiv(Py.newInteger(400))._sub(var1.getlocal(0)._floordiv(Py.newInteger(400))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject weekday$18(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyString.fromInterned("Return weekday (0-6 ~ Mon-Sun) for year (1970-...), month (1-12),\n       day (1-31).");
      var1.setline(113);
      PyObject var3 = var1.getglobal("datetime").__getattr__("date").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)).__getattr__("weekday").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject monthrange$19(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyString.fromInterned("Return weekday (0-6 ~ Mon-Sun) and number of days (28-31) for\n       year, month.");
      var1.setline(119);
      PyInteger var3 = Py.newInteger(1);
      PyObject var10001 = var1.getlocal(1);
      PyInteger var10000 = var3;
      PyObject var5 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var5._le(Py.newInteger(12));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(120);
         throw Py.makeException(var1.getglobal("IllegalMonthError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(121);
         var5 = var1.getglobal("weekday").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(1));
         var1.setlocal(2, var5);
         var3 = null;
         var1.setline(122);
         PyObject var7 = var1.getglobal("mdays").__getitem__(var1.getlocal(1));
         var5 = var1.getlocal(1);
         var10001 = var5._eq(var1.getglobal("February"));
         var3 = null;
         if (var10001.__nonzero__()) {
            var10001 = var1.getglobal("isleap").__call__(var2, var1.getlocal(0));
         }

         var5 = var7._add(var10001);
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(123);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)});
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject Calendar$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Base calendar class. This class doesn't do any formatting. It simply\n    provides data to subclasses.\n    "));
      var1.setline(130);
      PyString.fromInterned("\n    Base calendar class. This class doesn't do any formatting. It simply\n    provides data to subclasses.\n    ");
      var1.setline(132);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$21, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(135);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getfirstweekday$22, (PyObject)null);
      var1.setlocal("getfirstweekday", var4);
      var3 = null;
      var1.setline(138);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setfirstweekday$23, (PyObject)null);
      var1.setlocal("setfirstweekday", var4);
      var3 = null;
      var1.setline(141);
      PyObject var5 = var1.getname("property").__call__(var2, var1.getname("getfirstweekday"), var1.getname("setfirstweekday"));
      var1.setlocal("firstweekday", var5);
      var3 = null;
      var1.setline(143);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, iterweekdays$24, PyString.fromInterned("\n        Return a iterator for one week of weekday numbers starting with the\n        configured first one.\n        "));
      var1.setlocal("iterweekdays", var4);
      var3 = null;
      var1.setline(151);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, itermonthdates$25, PyString.fromInterned("\n        Return an iterator for one month. The iterator will yield datetime.date\n        values and will always iterate through complete weeks, so it will yield\n        dates outside the specified month.\n        "));
      var1.setlocal("itermonthdates", var4);
      var3 = null;
      var1.setline(172);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, itermonthdays2$26, PyString.fromInterned("\n        Like itermonthdates(), but will yield (day number, weekday number)\n        tuples. For days outside the specified month the day number is 0.\n        "));
      var1.setlocal("itermonthdays2", var4);
      var3 = null;
      var1.setline(183);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, itermonthdays$27, PyString.fromInterned("\n        Like itermonthdates(), but will yield day numbers. For days outside\n        the specified month the day number is 0.\n        "));
      var1.setlocal("itermonthdays", var4);
      var3 = null;
      var1.setline(194);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, monthdatescalendar$28, PyString.fromInterned("\n        Return a matrix (list of lists) representing a month's calendar.\n        Each row represents a week; week entries are datetime.date values.\n        "));
      var1.setlocal("monthdatescalendar", var4);
      var3 = null;
      var1.setline(202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, monthdays2calendar$29, PyString.fromInterned("\n        Return a matrix representing a month's calendar.\n        Each row represents a week; week entries are\n        (day number, weekday number) tuples. Day numbers outside this month\n        are zero.\n        "));
      var1.setlocal("monthdays2calendar", var4);
      var3 = null;
      var1.setline(212);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, monthdayscalendar$30, PyString.fromInterned("\n        Return a matrix representing a month's calendar.\n        Each row represents a week; days outside this month are zero.\n        "));
      var1.setlocal("monthdayscalendar", var4);
      var3 = null;
      var1.setline(220);
      var3 = new PyObject[]{Py.newInteger(3)};
      var4 = new PyFunction(var1.f_globals, var3, yeardatescalendar$31, PyString.fromInterned("\n        Return the data for the specified year ready for formatting. The return\n        value is a list of month rows. Each month row contains upto width months.\n        Each month contains between 4 and 6 weeks and each week contains 1-7\n        days. Days are datetime.date objects.\n        "));
      var1.setlocal("yeardatescalendar", var4);
      var3 = null;
      var1.setline(233);
      var3 = new PyObject[]{Py.newInteger(3)};
      var4 = new PyFunction(var1.f_globals, var3, yeardays2calendar$32, PyString.fromInterned("\n        Return the data for the specified year ready for formatting (similar to\n        yeardatescalendar()). Entries in the week lists are\n        (day number, weekday number) tuples. Day numbers outside this month are\n        zero.\n        "));
      var1.setlocal("yeardays2calendar", var4);
      var3 = null;
      var1.setline(246);
      var3 = new PyObject[]{Py.newInteger(3)};
      var4 = new PyFunction(var1.f_globals, var3, yeardayscalendar$33, PyString.fromInterned("\n        Return the data for the specified year ready for formatting (similar to\n        yeardatescalendar()). Entries in the week lists are day numbers.\n        Day numbers outside this month are zero.\n        "));
      var1.setlocal("yeardayscalendar", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$21(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("firstweekday", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getfirstweekday$22(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      PyObject var3 = var1.getlocal(0).__getattr__("_firstweekday")._mod(Py.newInteger(7));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setfirstweekday$23(PyFrame var1, ThreadState var2) {
      var1.setline(139);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_firstweekday", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject iterweekdays$24(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(147);
            PyString.fromInterned("\n        Return a iterator for one week of weekday numbers starting with the\n        configured first one.\n        ");
            var1.setline(148);
            var3 = var1.getglobal("range").__call__(var2, var1.getlocal(0).__getattr__("firstweekday"), var1.getlocal(0).__getattr__("firstweekday")._add(Py.newInteger(7))).__iter__();
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

      var1.setline(148);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(149);
         var1.setline(149);
         var6 = var1.getlocal(1)._mod(Py.newInteger(7));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject itermonthdates$25(PyFrame var1, ThreadState var2) {
      label39: {
         Object[] var3;
         PyObject var8;
         PyObject var11;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(156);
               PyString.fromInterned("\n        Return an iterator for one month. The iterator will yield datetime.date\n        values and will always iterate through complete weeks, so it will yield\n        dates outside the specified month.\n        ");
               var1.setline(157);
               var8 = var1.getglobal("datetime").__getattr__("date").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(1));
               var1.setlocal(3, var8);
               var3 = null;
               var1.setline(159);
               var8 = var1.getlocal(3).__getattr__("weekday").__call__(var2)._sub(var1.getlocal(0).__getattr__("firstweekday"))._mod(Py.newInteger(7));
               var1.setlocal(4, var8);
               var3 = null;
               var1.setline(160);
               var8 = var1.getlocal(3);
               var11 = var1.getglobal("datetime").__getattr__("timedelta");
               PyObject[] var4 = new PyObject[]{var1.getlocal(4)};
               String[] var5 = new String[]{"days"};
               var11 = var11.__call__(var2, var4, var5);
               var4 = null;
               var8 = var8._isub(var11);
               var1.setlocal(3, var8);
               var1.setline(161);
               var11 = var1.getglobal("datetime").__getattr__("timedelta");
               PyObject[] var10 = new PyObject[]{Py.newInteger(1)};
               String[] var9 = new String[]{"days"};
               var11 = var11.__call__(var2, var10, var9);
               var3 = null;
               var8 = var11;
               var1.setlocal(5, var8);
               var3 = null;
               break;
            case 1:
               var3 = var1.f_savedlocals;
               Object var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var11 = (PyObject)var10000;

               try {
                  var1.setline(165);
                  var8 = var1.getlocal(3);
                  var8 = var8._iadd(var1.getlocal(5));
                  var1.setlocal(3, var8);
               } catch (Throwable var6) {
                  PyException var7 = Py.setException(var6, var1);
                  if (var7.match(var1.getglobal("OverflowError"))) {
                     break label39;
                  }

                  throw var7;
               }

               var1.setline(169);
               var8 = var1.getlocal(3).__getattr__("month");
               var11 = var8._ne(var1.getlocal(2));
               var3 = null;
               if (var11.__nonzero__()) {
                  var8 = var1.getlocal(3).__getattr__("weekday").__call__(var2);
                  var11 = var8._eq(var1.getlocal(0).__getattr__("firstweekday"));
                  var3 = null;
               }

               if (var11.__nonzero__()) {
                  break label39;
               }
         }

         var1.setline(162);
         if (var1.getglobal("True").__nonzero__()) {
            var1.setline(163);
            var1.setline(163);
            var11 = var1.getlocal(3);
            var1.f_lasti = 1;
            var3 = new Object[6];
            var1.f_savedlocals = var3;
            return var11;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject itermonthdays2$26(PyFrame var1, ThreadState var2) {
      Object var10000;
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(176);
            PyString.fromInterned("\n        Like itermonthdates(), but will yield (day number, weekday number)\n        tuples. For days outside the specified month the day number is 0.\n        ");
            var1.setline(177);
            var3 = var1.getlocal(0).__getattr__("itermonthdates").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
            break;
         case 2:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var8 = (PyObject)var10000;
      }

      var1.setline(177);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(3, var4);
         var1.setline(178);
         PyObject var6 = var1.getlocal(3).__getattr__("month");
         var8 = var6._ne(var1.getlocal(2));
         var5 = null;
         PyObject[] var7;
         PyTuple var9;
         if (var8.__nonzero__()) {
            var1.setline(179);
            var1.setline(179);
            var7 = new PyObject[]{Py.newInteger(0), var1.getlocal(3).__getattr__("weekday").__call__(var2)};
            var9 = new PyTuple(var7);
            Arrays.fill(var7, (Object)null);
            var1.f_lasti = 1;
            var5 = new Object[7];
            var5[3] = var3;
            var5[4] = var4;
            var1.f_savedlocals = var5;
            return var9;
         } else {
            var1.setline(181);
            var1.setline(181);
            var7 = new PyObject[]{var1.getlocal(3).__getattr__("day"), var1.getlocal(3).__getattr__("weekday").__call__(var2)};
            var9 = new PyTuple(var7);
            Arrays.fill(var7, (Object)null);
            var1.f_lasti = 2;
            var5 = new Object[7];
            var5[3] = var3;
            var5[4] = var4;
            var1.f_savedlocals = var5;
            return var9;
         }
      }
   }

   public PyObject itermonthdays$27(PyFrame var1, ThreadState var2) {
      Object var10000;
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(187);
            PyString.fromInterned("\n        Like itermonthdates(), but will yield day numbers. For days outside\n        the specified month the day number is 0.\n        ");
            var1.setline(188);
            var3 = var1.getlocal(0).__getattr__("itermonthdates").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var7 = (PyObject)var10000;
            break;
         case 2:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var7 = (PyObject)var10000;
      }

      var1.setline(188);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(3, var4);
         var1.setline(189);
         PyObject var6 = var1.getlocal(3).__getattr__("month");
         var7 = var6._ne(var1.getlocal(2));
         var5 = null;
         if (var7.__nonzero__()) {
            var1.setline(190);
            var1.setline(190);
            PyInteger var8 = Py.newInteger(0);
            var1.f_lasti = 1;
            var5 = new Object[7];
            var5[3] = var3;
            var5[4] = var4;
            var1.f_savedlocals = var5;
            return var8;
         } else {
            var1.setline(192);
            var1.setline(192);
            var7 = var1.getlocal(3).__getattr__("day");
            var1.f_lasti = 2;
            var5 = new Object[7];
            var5[3] = var3;
            var5[4] = var4;
            var1.f_savedlocals = var5;
            return var7;
         }
      }
   }

   public PyObject monthdatescalendar$28(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      PyString.fromInterned("\n        Return a matrix (list of lists) representing a month's calendar.\n        Each row represents a week; week entries are datetime.date values.\n        ");
      var1.setline(199);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("itermonthdates").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(200);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(200);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(7)).__iter__();

      while(true) {
         var1.setline(200);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(200);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(5, var4);
         var1.setline(200);
         var1.getlocal(4).__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(7)), (PyObject)null));
      }
   }

   public PyObject monthdays2calendar$29(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyString.fromInterned("\n        Return a matrix representing a month's calendar.\n        Each row represents a week; week entries are\n        (day number, weekday number) tuples. Day numbers outside this month\n        are zero.\n        ");
      var1.setline(209);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("itermonthdays2").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(210);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(210);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(7)).__iter__();

      while(true) {
         var1.setline(210);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(210);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(5, var4);
         var1.setline(210);
         var1.getlocal(4).__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(7)), (PyObject)null));
      }
   }

   public PyObject monthdayscalendar$30(PyFrame var1, ThreadState var2) {
      var1.setline(216);
      PyString.fromInterned("\n        Return a matrix representing a month's calendar.\n        Each row represents a week; days outside this month are zero.\n        ");
      var1.setline(217);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("itermonthdays").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(218);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(218);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(7)).__iter__();

      while(true) {
         var1.setline(218);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(218);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(5, var4);
         var1.setline(218);
         var1.getlocal(4).__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(Py.newInteger(7)), (PyObject)null));
      }
   }

   public PyObject yeardatescalendar$31(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      PyString.fromInterned("\n        Return the data for the specified year ready for formatting. The return\n        value is a list of month rows. Each month row contains upto width months.\n        Each month contains between 4 and 6 weeks and each week contains 1-7\n        days. Days are datetime.date objects.\n        ");
      var1.setline(227);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(229);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("January"), var1.getglobal("January")._add(Py.newInteger(12))).__iter__();

      while(true) {
         var1.setline(229);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(229);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(231);
            var10000 = new PyList();
            var3 = var10000.__getattr__("append");
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(231);
            var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)var1.getlocal(2)).__iter__();

            while(true) {
               var1.setline(231);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(231);
                  var1.dellocal(6);
                  var5 = var10000;
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setlocal(5, var4);
               var1.setline(231);
               var1.getlocal(6).__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(var1.getlocal(2)), (PyObject)null));
            }
         }

         var1.setlocal(5, var4);
         var1.setline(228);
         var1.getlocal(4).__call__(var2, var1.getlocal(0).__getattr__("monthdatescalendar").__call__(var2, var1.getlocal(1), var1.getlocal(5)));
      }
   }

   public PyObject yeardays2calendar$32(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyString.fromInterned("\n        Return the data for the specified year ready for formatting (similar to\n        yeardatescalendar()). Entries in the week lists are\n        (day number, weekday number) tuples. Day numbers outside this month are\n        zero.\n        ");
      var1.setline(240);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(242);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("January"), var1.getglobal("January")._add(Py.newInteger(12))).__iter__();

      while(true) {
         var1.setline(242);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(242);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(244);
            var10000 = new PyList();
            var3 = var10000.__getattr__("append");
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(244);
            var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)var1.getlocal(2)).__iter__();

            while(true) {
               var1.setline(244);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(244);
                  var1.dellocal(6);
                  var5 = var10000;
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setlocal(5, var4);
               var1.setline(244);
               var1.getlocal(6).__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(var1.getlocal(2)), (PyObject)null));
            }
         }

         var1.setlocal(5, var4);
         var1.setline(241);
         var1.getlocal(4).__call__(var2, var1.getlocal(0).__getattr__("monthdays2calendar").__call__(var2, var1.getlocal(1), var1.getlocal(5)));
      }
   }

   public PyObject yeardayscalendar$33(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyString.fromInterned("\n        Return the data for the specified year ready for formatting (similar to\n        yeardatescalendar()). Entries in the week lists are day numbers.\n        Day numbers outside this month are zero.\n        ");
      var1.setline(252);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(254);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("January"), var1.getglobal("January")._add(Py.newInteger(12))).__iter__();

      while(true) {
         var1.setline(254);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(254);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(256);
            var10000 = new PyList();
            var3 = var10000.__getattr__("append");
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(256);
            var3 = var1.getglobal("range").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)var1.getlocal(2)).__iter__();

            while(true) {
               var1.setline(256);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(256);
                  var1.dellocal(6);
                  var5 = var10000;
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setlocal(5, var4);
               var1.setline(256);
               var1.getlocal(6).__call__(var2, var1.getlocal(3).__getslice__(var1.getlocal(5), var1.getlocal(5)._add(var1.getlocal(2)), (PyObject)null));
            }
         }

         var1.setlocal(5, var4);
         var1.setline(253);
         var1.getlocal(4).__call__(var2, var1.getlocal(0).__getattr__("monthdayscalendar").__call__(var2, var1.getlocal(1), var1.getlocal(5)));
      }
   }

   public PyObject TextCalendar$34(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Subclass of Calendar that outputs a calendar as a simple plain text\n    similar to the UNIX program cal.\n    "));
      var1.setline(263);
      PyString.fromInterned("\n    Subclass of Calendar that outputs a calendar as a simple plain text\n    similar to the UNIX program cal.\n    ");
      var1.setline(265);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, prweek$35, PyString.fromInterned("\n        Print a single week (no newline).\n        "));
      var1.setlocal("prweek", var4);
      var3 = null;
      var1.setline(271);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, formatday$36, PyString.fromInterned("\n        Returns a formatted day.\n        "));
      var1.setlocal("formatday", var4);
      var3 = null;
      var1.setline(281);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, formatweek$37, PyString.fromInterned("\n        Returns a single week in a string (no newline).\n        "));
      var1.setlocal("formatweek", var4);
      var3 = null;
      var1.setline(287);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, formatweekday$39, PyString.fromInterned("\n        Returns a formatted week day name.\n        "));
      var1.setlocal("formatweekday", var4);
      var3 = null;
      var1.setline(297);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, formatweekheader$40, PyString.fromInterned("\n        Return a header for a week.\n        "));
      var1.setlocal("formatweekheader", var4);
      var3 = null;
      var1.setline(303);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, formatmonthname$42, PyString.fromInterned("\n        Return a formatted month name.\n        "));
      var1.setlocal("formatmonthname", var4);
      var3 = null;
      var1.setline(312);
      var3 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, prmonth$43, PyString.fromInterned("\n        Print a month's calendar.\n        "));
      var1.setlocal("prmonth", var4);
      var3 = null;
      var1.setline(318);
      var3 = new PyObject[]{Py.newInteger(0), Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, formatmonth$44, PyString.fromInterned("\n        Return a month's calendar string (multi-line).\n        "));
      var1.setlocal("formatmonth", var4);
      var3 = null;
      var1.setline(334);
      var3 = new PyObject[]{Py.newInteger(2), Py.newInteger(1), Py.newInteger(6), Py.newInteger(3)};
      var4 = new PyFunction(var1.f_globals, var3, formatyear$45, PyString.fromInterned("\n        Returns a year's calendar as a multi-line string.\n        "));
      var1.setlocal("formatyear", var4);
      var3 = null;
      var1.setline(371);
      var3 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(6), Py.newInteger(3)};
      var4 = new PyFunction(var1.f_globals, var3, pryear$49, PyString.fromInterned("Print a year's calendar."));
      var1.setlocal("pryear", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject prweek$35(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyString.fromInterned("\n        Print a single week (no newline).\n        ");
      var1.setline(269);
      Py.printComma(var1.getlocal(0).__getattr__("formatweek").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject formatday$36(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyString.fromInterned("\n        Returns a formatted day.\n        ");
      var1.setline(275);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(276);
         PyString var4 = PyString.fromInterned("");
         var1.setlocal(4, var4);
         var3 = null;
      } else {
         var1.setline(278);
         var3 = PyString.fromInterned("%2i")._mod(var1.getlocal(1));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(279);
      var3 = var1.getlocal(4).__getattr__("center").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formatweek$37(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.to_cell(2, 1);
      var1.setline(284);
      PyString.fromInterned("\n        Returns a single week in a string (no newline).\n        ");
      var1.setline(285);
      PyObject var10000 = PyString.fromInterned(" ").__getattr__("join");
      var1.setline(285);
      PyObject var10004 = var1.f_globals;
      PyObject[] var3 = Py.EmptyObjects;
      PyCode var10006 = f$38;
      PyObject[] var4 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      PyFunction var6 = new PyFunction(var10004, var3, var10006, (PyObject)null, var4);
      PyObject var10002 = var6.__call__(var2, var1.getlocal(1).__iter__());
      Arrays.fill(var3, (Object)null);
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$38(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(285);
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

            var8 = (PyObject)var10000;
      }

      var1.setline(285);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         PyObject[] var7 = Py.unpackSequence(var4, 2);
         PyObject var6 = var7[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var7[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(285);
         var1.setline(285);
         var8 = var1.getderef(0).__getattr__("formatday").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getderef(1));
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var8;
      }
   }

   public PyObject formatweekday$39(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      PyString.fromInterned("\n        Returns a formatted week day name.\n        ");
      var1.setline(291);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._ge(Py.newInteger(9));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(292);
         var3 = var1.getglobal("day_name");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(294);
         var3 = var1.getglobal("day_abbr");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(295);
      var3 = var1.getlocal(3).__getitem__(var1.getlocal(1)).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null).__getattr__("center").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formatweekheader$40(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.to_cell(1, 1);
      var1.setline(300);
      PyString.fromInterned("\n        Return a header for a week.\n        ");
      var1.setline(301);
      PyObject var10000 = PyString.fromInterned(" ").__getattr__("join");
      var1.setline(301);
      PyObject var10004 = var1.f_globals;
      PyObject[] var3 = Py.EmptyObjects;
      PyCode var10006 = f$41;
      PyObject[] var4 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      PyFunction var6 = new PyFunction(var10004, var3, var10006, (PyObject)null, var4);
      PyObject var10002 = var6.__call__(var2, var1.getderef(0).__getattr__("iterweekdays").__call__(var2).__iter__());
      Arrays.fill(var3, (Object)null);
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$41(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(301);
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

      var1.setline(301);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(301);
         var1.setline(301);
         var6 = var1.getderef(0).__getattr__("formatweekday").__call__(var2, var1.getlocal(1), var1.getderef(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject formatmonthname$42(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyString.fromInterned("\n        Return a formatted month name.\n        ");
      var1.setline(307);
      PyObject var3 = var1.getglobal("month_name").__getitem__(var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(308);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(309);
         var3 = PyString.fromInterned("%s %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(1)}));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(310);
      var3 = var1.getlocal(5).__getattr__("center").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject prmonth$43(PyFrame var1, ThreadState var2) {
      var1.setline(315);
      PyString.fromInterned("\n        Print a month's calendar.\n        ");
      var1.setline(316);
      Py.printComma(var1.getlocal(0).__getattr__("formatmonth").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject formatmonth$44(PyFrame var1, ThreadState var2) {
      var1.setline(321);
      PyString.fromInterned("\n        Return a month's calendar string (multi-line).\n        ");
      var1.setline(322);
      PyObject var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)var1.getlocal(3));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(323);
      var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getlocal(4));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(324);
      var3 = var1.getlocal(0).__getattr__("formatmonthname").__call__(var2, var1.getlocal(1), var1.getlocal(2), Py.newInteger(7)._mul(var1.getlocal(3)._add(Py.newInteger(1)))._sub(Py.newInteger(1)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(325);
      var3 = var1.getlocal(5).__getattr__("rstrip").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(326);
      var3 = var1.getlocal(5);
      var3 = var3._iadd(PyString.fromInterned("\n")._mul(var1.getlocal(4)));
      var1.setlocal(5, var3);
      var1.setline(327);
      var3 = var1.getlocal(5);
      var3 = var3._iadd(var1.getlocal(0).__getattr__("formatweekheader").__call__(var2, var1.getlocal(3)).__getattr__("rstrip").__call__(var2));
      var1.setlocal(5, var3);
      var1.setline(328);
      var3 = var1.getlocal(5);
      var3 = var3._iadd(PyString.fromInterned("\n")._mul(var1.getlocal(4)));
      var1.setlocal(5, var3);
      var1.setline(329);
      var3 = var1.getlocal(0).__getattr__("monthdays2calendar").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(329);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(332);
            var3 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(6, var4);
         var1.setline(330);
         PyObject var5 = var1.getlocal(5);
         var5 = var5._iadd(var1.getlocal(0).__getattr__("formatweek").__call__(var2, var1.getlocal(6), var1.getlocal(3)).__getattr__("rstrip").__call__(var2));
         var1.setlocal(5, var5);
         var1.setline(331);
         var5 = var1.getlocal(5);
         var5 = var5._iadd(PyString.fromInterned("\n")._mul(var1.getlocal(4)));
         var1.setlocal(5, var5);
      }
   }

   public PyObject formatyear$45(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.to_cell(0, 1);
      var1.setline(337);
      PyString.fromInterned("\n        Returns a year's calendar as a multi-line string.\n        ");
      var1.setline(338);
      PyObject var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(339);
      var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)var1.getlocal(3));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(340);
      var3 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)var1.getlocal(4));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(341);
      var3 = var1.getlocal(2)._add(Py.newInteger(1))._mul(Py.newInteger(7))._sub(Py.newInteger(1));
      var1.setderef(3, var3);
      var3 = null;
      var1.setline(342);
      PyList var11 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var11);
      var3 = null;
      var1.setline(343);
      var3 = var1.getlocal(6).__getattr__("append");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(344);
      var1.getlocal(7).__call__(var2, var1.getglobal("repr").__call__(var2, var1.getderef(0)).__getattr__("center").__call__(var2, var1.getderef(3)._mul(var1.getlocal(5))._add(var1.getlocal(4)._mul(var1.getlocal(5)._sub(Py.newInteger(1))))).__getattr__("rstrip").__call__(var2));
      var1.setline(345);
      var1.getlocal(7).__call__(var2, PyString.fromInterned("\n")._mul(var1.getlocal(3)));
      var1.setline(346);
      var3 = var1.getderef(1).__getattr__("formatweekheader").__call__(var2, var1.getlocal(2));
      var1.setderef(2, var3);
      var3 = null;
      var1.setline(347);
      var3 = var1.getglobal("enumerate").__call__(var2, var1.getderef(1).__getattr__("yeardays2calendar").__call__(var2, var1.getderef(0), var1.getlocal(5))).__iter__();

      while(true) {
         var1.setline(347);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(369);
            var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(6));
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(8, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(9, var6);
         var6 = null;
         var1.setline(349);
         PyObject var10 = var1.getglobal("range").__call__(var2, var1.getlocal(5)._mul(var1.getlocal(8))._add(Py.newInteger(1)), var1.getglobal("min").__call__((ThreadState)var2, (PyObject)var1.getlocal(5)._mul(var1.getlocal(8)._add(Py.newInteger(1)))._add(Py.newInteger(1)), (PyObject)Py.newInteger(13)));
         var1.setlocal(10, var10);
         var5 = null;
         var1.setline(350);
         var1.getlocal(7).__call__(var2, PyString.fromInterned("\n")._mul(var1.getlocal(3)));
         var1.setline(351);
         var1.setline(351);
         PyObject var10002 = var1.f_globals;
         var5 = Py.EmptyObjects;
         PyCode var10004 = f$46;
         PyObject[] var12 = new PyObject[]{var1.getclosure(1), var1.getclosure(0), var1.getclosure(3)};
         PyFunction var13 = new PyFunction(var10002, var5, var10004, (PyObject)null, var12);
         PyObject var10000 = var13.__call__(var2, var1.getlocal(10).__iter__());
         Arrays.fill(var5, (Object)null);
         var10 = var10000;
         var1.setlocal(11, var10);
         var5 = null;
         var1.setline(353);
         var1.getlocal(7).__call__(var2, var1.getglobal("formatstring").__call__(var2, var1.getlocal(11), var1.getderef(3), var1.getlocal(4)).__getattr__("rstrip").__call__(var2));
         var1.setline(354);
         var1.getlocal(7).__call__(var2, PyString.fromInterned("\n")._mul(var1.getlocal(3)));
         var1.setline(355);
         var1.setline(355);
         var10002 = var1.f_globals;
         var5 = Py.EmptyObjects;
         var10004 = f$47;
         var12 = new PyObject[]{var1.getclosure(2)};
         var13 = new PyFunction(var10002, var5, var10004, (PyObject)null, var12);
         var10000 = var13.__call__(var2, var1.getlocal(10).__iter__());
         Arrays.fill(var5, (Object)null);
         var10 = var10000;
         var1.setlocal(13, var10);
         var5 = null;
         var1.setline(356);
         var1.getlocal(7).__call__(var2, var1.getglobal("formatstring").__call__(var2, var1.getlocal(13), var1.getderef(3), var1.getlocal(4)).__getattr__("rstrip").__call__(var2));
         var1.setline(357);
         var1.getlocal(7).__call__(var2, PyString.fromInterned("\n")._mul(var1.getlocal(3)));
         var1.setline(359);
         var10000 = var1.getglobal("max");
         var1.setline(359);
         var5 = Py.EmptyObjects;
         var13 = new PyFunction(var1.f_globals, var5, f$48, (PyObject)null);
         var10002 = var13.__call__(var2, var1.getlocal(9).__iter__());
         Arrays.fill(var5, (Object)null);
         var10 = var10000.__call__(var2, var10002);
         var1.setlocal(15, var10);
         var5 = null;
         var1.setline(360);
         var10 = var1.getglobal("range").__call__(var2, var1.getlocal(15)).__iter__();

         while(true) {
            var1.setline(360);
            var6 = var10.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(17, var6);
            var1.setline(361);
            PyList var7 = new PyList(Py.EmptyObjects);
            var1.setlocal(18, var7);
            var7 = null;
            var1.setline(362);
            PyObject var14 = var1.getlocal(9).__iter__();

            while(true) {
               var1.setline(362);
               PyObject var8 = var14.__iternext__();
               if (var8 == null) {
                  var1.setline(367);
                  var1.getlocal(7).__call__(var2, var1.getglobal("formatstring").__call__(var2, var1.getlocal(18), var1.getderef(3), var1.getlocal(4)).__getattr__("rstrip").__call__(var2));
                  var1.setline(368);
                  var1.getlocal(7).__call__(var2, PyString.fromInterned("\n")._mul(var1.getlocal(3)));
                  break;
               }

               var1.setlocal(19, var8);
               var1.setline(363);
               PyObject var9 = var1.getlocal(17);
               var10000 = var9._ge(var1.getglobal("len").__call__(var2, var1.getlocal(19)));
               var9 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(364);
                  var1.getlocal(18).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
               } else {
                  var1.setline(366);
                  var1.getlocal(18).__getattr__("append").__call__(var2, var1.getderef(1).__getattr__("formatweek").__call__(var2, var1.getlocal(19).__getitem__(var1.getlocal(17)), var1.getlocal(2)));
               }
            }
         }
      }
   }

   public PyObject f$46(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(352);
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

      var1.setline(352);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(351);
         var1.setline(351);
         var6 = var1.getderef(0).__getattr__("formatmonthname").__call__(var2, var1.getderef(1), var1.getlocal(1), var1.getderef(2), var1.getglobal("False"));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject f$47(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(355);
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

      var1.setline(355);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(355);
         var1.setline(355);
         var6 = var1.getderef(0);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject f$48(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(359);
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

      var1.setline(359);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(359);
         var1.setline(359);
         var6 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject pryear$49(PyFrame var1, ThreadState var2) {
      var1.setline(372);
      PyString.fromInterned("Print a year's calendar.");
      var1.setline(373);
      PyObject var10000 = var1.getlocal(0).__getattr__("formatyear");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
      Py.println(var10000.__call__(var2, var3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject HTMLCalendar$50(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    This calendar returns complete HTML pages.\n    "));
      var1.setline(379);
      PyString.fromInterned("\n    This calendar returns complete HTML pages.\n    ");
      var1.setline(382);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("mon"), PyString.fromInterned("tue"), PyString.fromInterned("wed"), PyString.fromInterned("thu"), PyString.fromInterned("fri"), PyString.fromInterned("sat"), PyString.fromInterned("sun")});
      var1.setlocal("cssclasses", var3);
      var3 = null;
      var1.setline(384);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, formatday$51, PyString.fromInterned("\n        Return a day as a table cell.\n        "));
      var1.setlocal("formatday", var5);
      var3 = null;
      var1.setline(393);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, formatweek$52, PyString.fromInterned("\n        Return a complete week as a table row.\n        "));
      var1.setlocal("formatweek", var5);
      var3 = null;
      var1.setline(400);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, formatweekday$54, PyString.fromInterned("\n        Return a weekday name as a table header.\n        "));
      var1.setlocal("formatweekday", var5);
      var3 = null;
      var1.setline(406);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, formatweekheader$55, PyString.fromInterned("\n        Return a header for a week as a table row.\n        "));
      var1.setlocal("formatweekheader", var5);
      var3 = null;
      var1.setline(413);
      var4 = new PyObject[]{var1.getname("True")};
      var5 = new PyFunction(var1.f_globals, var4, formatmonthname$57, PyString.fromInterned("\n        Return a month name as a table row.\n        "));
      var1.setlocal("formatmonthname", var5);
      var3 = null;
      var1.setline(423);
      var4 = new PyObject[]{var1.getname("True")};
      var5 = new PyFunction(var1.f_globals, var4, formatmonth$58, PyString.fromInterned("\n        Return a formatted month as a table.\n        "));
      var1.setlocal("formatmonth", var5);
      var3 = null;
      var1.setline(442);
      var4 = new PyObject[]{Py.newInteger(3)};
      var5 = new PyFunction(var1.f_globals, var4, formatyear$59, PyString.fromInterned("\n        Return a formatted year as a table of tables.\n        "));
      var1.setlocal("formatyear", var5);
      var3 = null;
      var1.setline(464);
      var4 = new PyObject[]{Py.newInteger(3), PyString.fromInterned("calendar.css"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, formatyearpage$60, PyString.fromInterned("\n        Return a formatted year as a complete HTML page.\n        "));
      var1.setlocal("formatyearpage", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject formatday$51(PyFrame var1, ThreadState var2) {
      var1.setline(387);
      PyString.fromInterned("\n        Return a day as a table cell.\n        ");
      var1.setline(388);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(389);
         PyString var4 = PyString.fromInterned("<td class=\"noday\">&nbsp;</td>");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(391);
         var3 = PyString.fromInterned("<td class=\"%s\">%d</td>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("cssclasses").__getitem__(var1.getlocal(2)), var1.getlocal(1)}));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject formatweek$52(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(396);
      PyString.fromInterned("\n        Return a complete week as a table row.\n        ");
      var1.setline(397);
      PyObject var10000 = PyString.fromInterned("").__getattr__("join");
      var1.setline(397);
      PyObject var10004 = var1.f_globals;
      PyObject[] var3 = Py.EmptyObjects;
      PyCode var10006 = f$53;
      PyObject[] var4 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10004, var3, var10006, (PyObject)null, var4);
      PyObject var10002 = var6.__call__(var2, var1.getlocal(1).__iter__());
      Arrays.fill(var3, (Object)null);
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(398);
      var5 = PyString.fromInterned("<tr>%s</tr>")._mod(var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$53(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var8;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(397);
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

            var8 = (PyObject)var10000;
      }

      var1.setline(397);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         PyObject[] var7 = Py.unpackSequence(var4, 2);
         PyObject var6 = var7[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var7[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(397);
         var1.setline(397);
         var8 = var1.getderef(0).__getattr__("formatday").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var8;
      }
   }

   public PyObject formatweekday$54(PyFrame var1, ThreadState var2) {
      var1.setline(403);
      PyString.fromInterned("\n        Return a weekday name as a table header.\n        ");
      var1.setline(404);
      PyObject var3 = PyString.fromInterned("<th class=\"%s\">%s</th>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("cssclasses").__getitem__(var1.getlocal(1)), var1.getglobal("day_abbr").__getitem__(var1.getlocal(1))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formatweekheader$55(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(409);
      PyString.fromInterned("\n        Return a header for a week as a table row.\n        ");
      var1.setline(410);
      PyObject var10000 = PyString.fromInterned("").__getattr__("join");
      var1.setline(410);
      PyObject var10004 = var1.f_globals;
      PyObject[] var3 = Py.EmptyObjects;
      PyCode var10006 = f$56;
      PyObject[] var4 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10004, var3, var10006, (PyObject)null, var4);
      PyObject var10002 = var6.__call__(var2, var1.getderef(0).__getattr__("iterweekdays").__call__(var2).__iter__());
      Arrays.fill(var3, (Object)null);
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(411);
      var5 = PyString.fromInterned("<tr>%s</tr>")._mod(var1.getlocal(1));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$56(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(410);
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

      var1.setline(410);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(410);
         var1.setline(410);
         var6 = var1.getderef(0).__getattr__("formatweekday").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject formatmonthname$57(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyString.fromInterned("\n        Return a month name as a table row.\n        ");
      var1.setline(417);
      PyObject var3;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(418);
         var3 = PyString.fromInterned("%s %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("month_name").__getitem__(var1.getlocal(2)), var1.getlocal(1)}));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(420);
         var3 = PyString.fromInterned("%s")._mod(var1.getglobal("month_name").__getitem__(var1.getlocal(2)));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(421);
      var3 = PyString.fromInterned("<tr><th colspan=\"7\" class=\"month\">%s</th></tr>")._mod(var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject formatmonth$58(PyFrame var1, ThreadState var2) {
      var1.setline(426);
      PyString.fromInterned("\n        Return a formatted month as a table.\n        ");
      var1.setline(427);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(428);
      PyObject var5 = var1.getlocal(4).__getattr__("append");
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(429);
      var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"month\">"));
      var1.setline(430);
      var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setline(431);
      PyObject var10000 = var1.getlocal(5);
      PyObject var10002 = var1.getlocal(0).__getattr__("formatmonthname");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
      String[] var4 = new String[]{"withyear"};
      var10002 = var10002.__call__(var2, var6, var4);
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(432);
      var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setline(433);
      var1.getlocal(5).__call__(var2, var1.getlocal(0).__getattr__("formatweekheader").__call__(var2));
      var1.setline(434);
      var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setline(435);
      var5 = var1.getlocal(0).__getattr__("monthdays2calendar").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(435);
         PyObject var7 = var5.__iternext__();
         if (var7 == null) {
            var1.setline(438);
            var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</table>"));
            var1.setline(439);
            var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
            var1.setline(440);
            var5 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(4));
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(6, var7);
         var1.setline(436);
         var1.getlocal(5).__call__(var2, var1.getlocal(0).__getattr__("formatweek").__call__(var2, var1.getlocal(6)));
         var1.setline(437);
         var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      }
   }

   public PyObject formatyear$59(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      PyString.fromInterned("\n        Return a formatted year as a table of tables.\n        ");
      var1.setline(446);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(447);
      PyObject var9 = var1.getlocal(3).__getattr__("append");
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(448);
      var9 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(1));
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(449);
      var1.getlocal(4).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"year\">"));
      var1.setline(450);
      var1.getlocal(4).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.setline(451);
      var1.getlocal(4).__call__(var2, PyString.fromInterned("<tr><th colspan=\"%d\" class=\"year\">%s</th></tr>")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)})));
      var1.setline(452);
      var9 = var1.getglobal("range").__call__(var2, var1.getglobal("January"), var1.getglobal("January")._add(Py.newInteger(12)), var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(452);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(461);
            var1.getlocal(4).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</table>"));
            var1.setline(462);
            var9 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return var9;
         }

         var1.setlocal(5, var4);
         var1.setline(454);
         PyObject var5 = var1.getglobal("range").__call__(var2, var1.getlocal(5), var1.getglobal("min").__call__((ThreadState)var2, (PyObject)var1.getlocal(5)._add(var1.getlocal(2)), (PyObject)Py.newInteger(13)));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(455);
         var1.getlocal(4).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<tr>"));
         var1.setline(456);
         var5 = var1.getlocal(6).__iter__();

         while(true) {
            var1.setline(456);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(460);
               var1.getlocal(4).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</tr>"));
               break;
            }

            var1.setlocal(7, var6);
            var1.setline(457);
            var1.getlocal(4).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<td>"));
            var1.setline(458);
            PyObject var10000 = var1.getlocal(4);
            PyObject var10002 = var1.getlocal(0).__getattr__("formatmonth");
            PyObject[] var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(7), var1.getglobal("False")};
            String[] var8 = new String[]{"withyear"};
            var10002 = var10002.__call__(var2, var7, var8);
            var7 = null;
            var10000.__call__(var2, var10002);
            var1.setline(459);
            var1.getlocal(4).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</td>"));
         }
      }
   }

   public PyObject formatyearpage$60(PyFrame var1, ThreadState var2) {
      var1.setline(467);
      PyString.fromInterned("\n        Return a formatted year as a complete HTML page.\n        ");
      var1.setline(468);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(469);
         var3 = var1.getglobal("sys").__getattr__("getdefaultencoding").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(470);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var4);
      var3 = null;
      var1.setline(471);
      var3 = var1.getlocal(5).__getattr__("append");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(472);
      var1.getlocal(6).__call__(var2, PyString.fromInterned("<?xml version=\"1.0\" encoding=\"%s\"?>\n")._mod(var1.getlocal(4)));
      var1.setline(473);
      var1.getlocal(6).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n"));
      var1.setline(474);
      var1.getlocal(6).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<html>\n"));
      var1.setline(475);
      var1.getlocal(6).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<head>\n"));
      var1.setline(476);
      var1.getlocal(6).__call__(var2, PyString.fromInterned("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=%s\" />\n")._mod(var1.getlocal(4)));
      var1.setline(477);
      var3 = var1.getlocal(3);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(478);
         var1.getlocal(6).__call__(var2, PyString.fromInterned("<link rel=\"stylesheet\" type=\"text/css\" href=\"%s\" />\n")._mod(var1.getlocal(3)));
      }

      var1.setline(479);
      var1.getlocal(6).__call__(var2, PyString.fromInterned("<title>Calendar for %d</title>\n")._mod(var1.getlocal(1)));
      var1.setline(480);
      var1.getlocal(6).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</head>\n"));
      var1.setline(481);
      var1.getlocal(6).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<body>\n"));
      var1.setline(482);
      var1.getlocal(6).__call__(var2, var1.getlocal(0).__getattr__("formatyear").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
      var1.setline(483);
      var1.getlocal(6).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</body>\n"));
      var1.setline(484);
      var1.getlocal(6).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</html>\n"));
      var1.setline(485);
      var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(5)).__getattr__("encode").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("xmlcharrefreplace"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TimeEncoding$61(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(489);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$62, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(492);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$63, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(497);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$64, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$62(PyFrame var1, ThreadState var2) {
      var1.setline(490);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("locale", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __enter__$63(PyFrame var1, ThreadState var2) {
      var1.setline(493);
      PyObject var3 = var1.getglobal("_locale").__getattr__("getlocale").__call__(var2, var1.getglobal("_locale").__getattr__("LC_TIME"));
      var1.getlocal(0).__setattr__("oldlocale", var3);
      var3 = null;
      var1.setline(494);
      var1.getglobal("_locale").__getattr__("setlocale").__call__(var2, var1.getglobal("_locale").__getattr__("LC_TIME"), var1.getlocal(0).__getattr__("locale"));
      var1.setline(495);
      var3 = var1.getglobal("_locale").__getattr__("getlocale").__call__(var2, var1.getglobal("_locale").__getattr__("LC_TIME")).__getitem__(Py.newInteger(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$64(PyFrame var1, ThreadState var2) {
      var1.setline(498);
      var1.getglobal("_locale").__getattr__("setlocale").__call__(var2, var1.getglobal("_locale").__getattr__("LC_TIME"), var1.getlocal(0).__getattr__("oldlocale"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject LocaleTextCalendar$65(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    This class can be passed a locale name in the constructor and will return\n    month and weekday names in the specified locale. If this locale includes\n    an encoding all strings containing month and weekday names will be returned\n    as unicode.\n    "));
      var1.setline(507);
      PyString.fromInterned("\n    This class can be passed a locale name in the constructor and will return\n    month and weekday names in the specified locale. If this locale includes\n    an encoding all strings containing month and weekday names will be returned\n    as unicode.\n    ");
      var1.setline(509);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$66, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(515);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, formatweekday$67, (PyObject)null);
      var1.setlocal("formatweekday", var4);
      var3 = null;
      var1.setline(526);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, formatmonthname$68, (PyObject)null);
      var1.setlocal("formatmonthname", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$66(PyFrame var1, ThreadState var2) {
      var1.setline(510);
      var1.getglobal("TextCalendar").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(511);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(512);
         var3 = var1.getglobal("_locale").__getattr__("getdefaultlocale").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(513);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("locale", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject formatweekday$67(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("TimeEncoding").__call__(var2, var1.getlocal(0).__getattr__("locale")))).__enter__(var2);

      Throwable var10000;
      label38: {
         boolean var10001;
         try {
            var1.setlocal(3, var4);
            var1.setline(517);
            var4 = var1.getlocal(2);
            PyObject var7 = var4._ge(Py.newInteger(9));
            var4 = null;
            if (var7.__nonzero__()) {
               var1.setline(518);
               var4 = var1.getglobal("day_name");
               var1.setlocal(4, var4);
               var4 = null;
            } else {
               var1.setline(520);
               var4 = var1.getglobal("day_abbr");
               var1.setlocal(4, var4);
               var4 = null;
            }

            var1.setline(521);
            var4 = var1.getlocal(4).__getitem__(var1.getlocal(1));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(522);
            var4 = var1.getlocal(3);
            var7 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var7.__nonzero__()) {
               var1.setline(523);
               var4 = var1.getlocal(5).__getattr__("decode").__call__(var2, var1.getlocal(3));
               var1.setlocal(5, var4);
               var4 = null;
            }

            var1.setline(524);
            var4 = var1.getlocal(5).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null).__getattr__("center").__call__(var2, var1.getlocal(2));
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label38;
         }

         var3.__exit__(var2, (PyException)null);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      if (!var3.__exit__(var2, Py.setException(var10000, var1))) {
         throw (Throwable)Py.makeException();
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject formatmonthname$68(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("TimeEncoding").__call__(var2, var1.getlocal(0).__getattr__("locale")))).__enter__(var2);

      Throwable var10000;
      label37: {
         boolean var10001;
         try {
            var1.setlocal(5, var4);
            var1.setline(528);
            var4 = var1.getglobal("month_name").__getitem__(var1.getlocal(2));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(529);
            var4 = var1.getlocal(5);
            PyObject var7 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var7.__nonzero__()) {
               var1.setline(530);
               var4 = var1.getlocal(6).__getattr__("decode").__call__(var2, var1.getlocal(5));
               var1.setlocal(6, var4);
               var4 = null;
            }

            var1.setline(531);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(532);
               var4 = PyString.fromInterned("%s %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(1)}));
               var1.setlocal(6, var4);
               var4 = null;
            }

            var1.setline(533);
            var4 = var1.getlocal(6).__getattr__("center").__call__(var2, var1.getlocal(3));
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label37;
         }

         var3.__exit__(var2, (PyException)null);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      if (!var3.__exit__(var2, Py.setException(var10000, var1))) {
         throw (Throwable)Py.makeException();
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject LocaleHTMLCalendar$69(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    This class can be passed a locale name in the constructor and will return\n    month and weekday names in the specified locale. If this locale includes\n    an encoding all strings containing month and weekday names will be returned\n    as unicode.\n    "));
      var1.setline(542);
      PyString.fromInterned("\n    This class can be passed a locale name in the constructor and will return\n    month and weekday names in the specified locale. If this locale includes\n    an encoding all strings containing month and weekday names will be returned\n    as unicode.\n    ");
      var1.setline(543);
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$70, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(549);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, formatweekday$71, (PyObject)null);
      var1.setlocal("formatweekday", var4);
      var3 = null;
      var1.setline(556);
      var3 = new PyObject[]{var1.getname("True")};
      var4 = new PyFunction(var1.f_globals, var3, formatmonthname$72, (PyObject)null);
      var1.setlocal("formatmonthname", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$70(PyFrame var1, ThreadState var2) {
      var1.setline(544);
      var1.getglobal("HTMLCalendar").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(545);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(546);
         var3 = var1.getglobal("_locale").__getattr__("getdefaultlocale").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(547);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("locale", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject formatweekday$71(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("TimeEncoding").__call__(var2, var1.getlocal(0).__getattr__("locale")))).__enter__(var2);

      Throwable var10000;
      label34: {
         boolean var10001;
         try {
            var1.setlocal(2, var4);
            var1.setline(551);
            var4 = var1.getglobal("day_abbr").__getitem__(var1.getlocal(1));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(552);
            var4 = var1.getlocal(2);
            PyObject var7 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var7.__nonzero__()) {
               var1.setline(553);
               var4 = var1.getlocal(3).__getattr__("decode").__call__(var2, var1.getlocal(2));
               var1.setlocal(3, var4);
               var4 = null;
            }

            var1.setline(554);
            var4 = PyString.fromInterned("<th class=\"%s\">%s</th>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("cssclasses").__getitem__(var1.getlocal(1)), var1.getlocal(3)}));
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label34;
         }

         var3.__exit__(var2, (PyException)null);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      if (!var3.__exit__(var2, Py.setException(var10000, var1))) {
         throw (Throwable)Py.makeException();
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject formatmonthname$72(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("TimeEncoding").__call__(var2, var1.getlocal(0).__getattr__("locale")))).__enter__(var2);

      Throwable var10000;
      label37: {
         boolean var10001;
         try {
            var1.setlocal(4, var4);
            var1.setline(558);
            var4 = var1.getglobal("month_name").__getitem__(var1.getlocal(2));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(559);
            var4 = var1.getlocal(4);
            PyObject var7 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var7.__nonzero__()) {
               var1.setline(560);
               var4 = var1.getlocal(5).__getattr__("decode").__call__(var2, var1.getlocal(4));
               var1.setlocal(5, var4);
               var4 = null;
            }

            var1.setline(561);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(562);
               var4 = PyString.fromInterned("%s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(1)}));
               var1.setlocal(5, var4);
               var4 = null;
            }

            var1.setline(563);
            var4 = PyString.fromInterned("<tr><th colspan=\"7\" class=\"month\">%s</th></tr>")._mod(var1.getlocal(5));
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label37;
         }

         var3.__exit__(var2, (PyException)null);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      if (!var3.__exit__(var2, Py.setException(var10000, var1))) {
         throw (Throwable)Py.makeException();
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject setfirstweekday$73(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(573);
         var1.getlocal(0).__getattr__("__index__");
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("AttributeError"))) {
            var1.setline(575);
            throw Py.makeException(var1.getglobal("IllegalWeekdayError").__call__(var2, var1.getlocal(0)));
         }

         throw var3;
      }

      var1.setline(576);
      PyObject var6 = var1.getglobal("MONDAY");
      PyObject var10001 = var1.getlocal(0);
      PyObject var10000 = var6;
      var6 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var6._le(var1.getglobal("SUNDAY"));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(577);
         throw Py.makeException(var1.getglobal("IllegalWeekdayError").__call__(var2, var1.getlocal(0)));
      } else {
         var1.setline(578);
         var6 = var1.getlocal(0);
         var1.getglobal("c").__setattr__("firstweekday", var6);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject format$74(PyFrame var1, ThreadState var2) {
      var1.setline(596);
      PyString.fromInterned("Prints multi-column formatting for year calendars");
      var1.setline(597);
      Py.println(var1.getglobal("formatstring").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject formatstring$75(PyFrame var1, ThreadState var2) {
      var1.to_cell(1, 0);
      var1.setline(601);
      PyString.fromInterned("Returns a string formatted from n strings, centered within n columns.");
      var1.setline(602);
      PyObject var3 = var1.getlocal(2);
      var3 = var3._imul(PyString.fromInterned(" "));
      var1.setlocal(2, var3);
      var1.setline(603);
      PyObject var10000 = var1.getlocal(2).__getattr__("join");
      var1.setline(603);
      PyObject var10004 = var1.f_globals;
      PyObject[] var5 = Py.EmptyObjects;
      PyCode var10006 = f$76;
      PyObject[] var4 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10004, var5, var10006, (PyObject)null, var4);
      PyObject var10002 = var6.__call__(var2, var1.getlocal(0).__iter__());
      Arrays.fill(var5, (Object)null);
      var3 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$76(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(603);
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

      var1.setline(603);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(603);
         var1.setline(603);
         var6 = var1.getlocal(1).__getattr__("center").__call__(var2, var1.getderef(0));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject timegm$77(PyFrame var1, ThreadState var2) {
      var1.setline(611);
      PyString.fromInterned("Unrelated but handy function to calculate Unix timestamp from GMT.");
      var1.setline(612);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
      PyObject[] var4 = Py.unpackSequence(var3, 6);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(613);
      var3 = var1.getglobal("datetime").__getattr__("date").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(1)).__getattr__("toordinal").__call__(var2)._sub(var1.getglobal("_EPOCH_ORD"))._add(var1.getlocal(3))._sub(Py.newInteger(1));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(614);
      var3 = var1.getlocal(7)._mul(Py.newInteger(24))._add(var1.getlocal(4));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(615);
      var3 = var1.getlocal(8)._mul(Py.newInteger(60))._add(var1.getlocal(5));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(616);
      var3 = var1.getlocal(9)._mul(Py.newInteger(60))._add(var1.getlocal(6));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(617);
      var3 = var1.getlocal(10);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject main$78(PyFrame var1, ThreadState var2) {
      var1.setline(621);
      PyObject var3 = imp.importOne("optparse", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(622);
      PyObject var10000 = var1.getlocal(1).__getattr__("OptionParser");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("usage: %prog [options] [year [month]]")};
      String[] var4 = new String[]{"usage"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(623);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var6 = new PyObject[]{PyString.fromInterned("-w"), PyString.fromInterned("--width"), PyString.fromInterned("width"), PyString.fromInterned("int"), Py.newInteger(2), PyString.fromInterned("width of date column (default 2, text only)")};
      var4 = new String[]{"dest", "type", "default", "help"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(628);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var6 = new PyObject[]{PyString.fromInterned("-l"), PyString.fromInterned("--lines"), PyString.fromInterned("lines"), PyString.fromInterned("int"), Py.newInteger(1), PyString.fromInterned("number of lines for each week (default 1, text only)")};
      var4 = new String[]{"dest", "type", "default", "help"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(633);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var6 = new PyObject[]{PyString.fromInterned("-s"), PyString.fromInterned("--spacing"), PyString.fromInterned("spacing"), PyString.fromInterned("int"), Py.newInteger(6), PyString.fromInterned("spacing between months (default 6, text only)")};
      var4 = new String[]{"dest", "type", "default", "help"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(638);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var6 = new PyObject[]{PyString.fromInterned("-m"), PyString.fromInterned("--months"), PyString.fromInterned("months"), PyString.fromInterned("int"), Py.newInteger(3), PyString.fromInterned("months per row (default 3, text only)")};
      var4 = new String[]{"dest", "type", "default", "help"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(643);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var6 = new PyObject[]{PyString.fromInterned("-c"), PyString.fromInterned("--css"), PyString.fromInterned("css"), PyString.fromInterned("calendar.css"), PyString.fromInterned("CSS to use for page (html only)")};
      var4 = new String[]{"dest", "default", "help"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(648);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var6 = new PyObject[]{PyString.fromInterned("-L"), PyString.fromInterned("--locale"), PyString.fromInterned("locale"), var1.getglobal("None"), PyString.fromInterned("locale to be used from month and weekday names")};
      var4 = new String[]{"dest", "default", "help"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(653);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var6 = new PyObject[]{PyString.fromInterned("-e"), PyString.fromInterned("--encoding"), PyString.fromInterned("encoding"), var1.getglobal("None"), PyString.fromInterned("Encoding to use for output")};
      var4 = new String[]{"dest", "default", "help"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(658);
      var10000 = var1.getlocal(2).__getattr__("add_option");
      var6 = new PyObject[]{PyString.fromInterned("-t"), PyString.fromInterned("--type"), PyString.fromInterned("type"), PyString.fromInterned("text"), new PyTuple(new PyObject[]{PyString.fromInterned("text"), PyString.fromInterned("html")}), PyString.fromInterned("output type (text or html)")};
      var4 = new String[]{"dest", "default", "choices", "help"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(665);
      var3 = var1.getlocal(2).__getattr__("parse_args").__call__(var2, var1.getlocal(0));
      PyObject[] var7 = Py.unpackSequence(var3, 2);
      PyObject var5 = var7[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(0, var5);
      var5 = null;
      var3 = null;
      var1.setline(667);
      var10000 = var1.getlocal(3).__getattr__("locale");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(3).__getattr__("encoding").__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(668);
         var1.getlocal(2).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("if --locale is specified --encoding is required"));
         var1.setline(669);
         var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      }

      var1.setline(671);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("locale"), var1.getlocal(3).__getattr__("encoding")});
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(673);
      var3 = var1.getlocal(3).__getattr__("type");
      var10000 = var3._eq(PyString.fromInterned("html"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(674);
         if (var1.getlocal(3).__getattr__("locale").__nonzero__()) {
            var1.setline(675);
            var10000 = var1.getglobal("LocaleHTMLCalendar");
            var6 = new PyObject[]{var1.getlocal(4)};
            var4 = new String[]{"locale"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(5, var3);
            var3 = null;
         } else {
            var1.setline(677);
            var3 = var1.getglobal("HTMLCalendar").__call__(var2);
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(678);
         var3 = var1.getlocal(3).__getattr__("encoding");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(679);
         var3 = var1.getlocal(6);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(680);
            var3 = var1.getglobal("sys").__getattr__("getdefaultencoding").__call__(var2);
            var1.setlocal(6, var3);
            var3 = null;
         }

         var1.setline(681);
         var10000 = var1.getglobal("dict");
         var6 = new PyObject[]{var1.getlocal(6), var1.getlocal(3).__getattr__("css")};
         var4 = new String[]{"encoding", "css"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(682);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(683);
            var10000 = var1.getlocal(5).__getattr__("formatyearpage");
            var6 = new PyObject[]{var1.getglobal("datetime").__getattr__("date").__getattr__("today").__call__(var2).__getattr__("year")};
            var4 = new String[0];
            var10000 = var10000._callextra(var6, var4, (PyObject)null, var1.getlocal(7));
            var3 = null;
            Py.println(var10000);
         } else {
            var1.setline(684);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var10000 = var3._eq(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(685);
               var10000 = var1.getlocal(5).__getattr__("formatyearpage");
               var6 = new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1)))};
               var4 = new String[0];
               var10000 = var10000._callextra(var6, var4, (PyObject)null, var1.getlocal(7));
               var3 = null;
               Py.println(var10000);
            } else {
               var1.setline(687);
               var1.getlocal(2).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("incorrect number of arguments"));
               var1.setline(688);
               var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            }
         }
      } else {
         var1.setline(690);
         if (var1.getlocal(3).__getattr__("locale").__nonzero__()) {
            var1.setline(691);
            var10000 = var1.getglobal("LocaleTextCalendar");
            var6 = new PyObject[]{var1.getlocal(4)};
            var4 = new String[]{"locale"};
            var10000 = var10000.__call__(var2, var6, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(5, var3);
            var3 = null;
         } else {
            var1.setline(693);
            var3 = var1.getglobal("TextCalendar").__call__(var2);
            var1.setlocal(5, var3);
            var3 = null;
         }

         var1.setline(694);
         var10000 = var1.getglobal("dict");
         var6 = new PyObject[]{var1.getlocal(3).__getattr__("width"), var1.getlocal(3).__getattr__("lines")};
         var4 = new String[]{"w", "l"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(695);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var3._ne(Py.newInteger(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(696);
            var3 = var1.getlocal(3).__getattr__("spacing");
            var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("c"), var3);
            var3 = null;
            var1.setline(697);
            var3 = var1.getlocal(3).__getattr__("months");
            var1.getlocal(7).__setitem__((PyObject)PyString.fromInterned("m"), var3);
            var3 = null;
         }

         var1.setline(698);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(699);
            var10000 = var1.getlocal(5).__getattr__("formatyear");
            var6 = new PyObject[]{var1.getglobal("datetime").__getattr__("date").__getattr__("today").__call__(var2).__getattr__("year")};
            var4 = new String[0];
            var10000 = var10000._callextra(var6, var4, (PyObject)null, var1.getlocal(7));
            var3 = null;
            var3 = var10000;
            var1.setlocal(8, var3);
            var3 = null;
         } else {
            var1.setline(700);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
            var10000 = var3._eq(Py.newInteger(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(701);
               var10000 = var1.getlocal(5).__getattr__("formatyear");
               var6 = new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1)))};
               var4 = new String[0];
               var10000 = var10000._callextra(var6, var4, (PyObject)null, var1.getlocal(7));
               var3 = null;
               var3 = var10000;
               var1.setlocal(8, var3);
               var3 = null;
            } else {
               var1.setline(702);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
               var10000 = var3._eq(Py.newInteger(3));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(703);
                  var10000 = var1.getlocal(5).__getattr__("formatmonth");
                  var6 = new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1))), var1.getglobal("int").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(2)))};
                  var4 = new String[0];
                  var10000 = var10000._callextra(var6, var4, (PyObject)null, var1.getlocal(7));
                  var3 = null;
                  var3 = var10000;
                  var1.setlocal(8, var3);
                  var3 = null;
               } else {
                  var1.setline(705);
                  var1.getlocal(2).__getattr__("error").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("incorrect number of arguments"));
                  var1.setline(706);
                  var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
               }
            }
         }

         var1.setline(707);
         if (var1.getlocal(3).__getattr__("encoding").__nonzero__()) {
            var1.setline(708);
            var3 = var1.getlocal(8).__getattr__("encode").__call__(var2, var1.getlocal(3).__getattr__("encoding"));
            var1.setlocal(8, var3);
            var3 = null;
         }

         var1.setline(709);
         Py.println(var1.getlocal(8));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public calendar$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      IllegalMonthError$1 = Py.newCode(0, var2, var1, "IllegalMonthError", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "month"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 22, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$3 = Py.newCode(1, var2, var1, "__str__", 24, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IllegalWeekdayError$4 = Py.newCode(0, var2, var1, "IllegalWeekdayError", 28, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "weekday"};
      __init__$5 = Py.newCode(2, var2, var1, "__init__", 29, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$6 = Py.newCode(1, var2, var1, "__str__", 31, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _localized_month$7 = Py.newCode(0, var2, var1, "_localized_month", 47, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"x"};
      f$8 = Py.newCode(1, var2, var1, "<lambda>", 50, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "format"};
      __init__$9 = Py.newCode(2, var2, var1, "__init__", 52, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "funcs", "_[58_20]", "f"};
      __getitem__$10 = Py.newCode(2, var2, var1, "__getitem__", 55, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$11 = Py.newCode(1, var2, var1, "__len__", 62, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _localized_day$12 = Py.newCode(0, var2, var1, "_localized_day", 66, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "format"};
      __init__$13 = Py.newCode(2, var2, var1, "__init__", 71, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "funcs", "_[77_20]", "f"};
      __getitem__$14 = Py.newCode(2, var2, var1, "__getitem__", 74, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$15 = Py.newCode(1, var2, var1, "__len__", 81, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"year"};
      isleap$16 = Py.newCode(1, var2, var1, "isleap", 97, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"y1", "y2"};
      leapdays$17 = Py.newCode(2, var2, var1, "leapdays", 102, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"year", "month", "day"};
      weekday$18 = Py.newCode(3, var2, var1, "weekday", 110, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"year", "month", "day1", "ndays"};
      monthrange$19 = Py.newCode(2, var2, var1, "monthrange", 116, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Calendar$20 = Py.newCode(0, var2, var1, "Calendar", 126, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "firstweekday"};
      __init__$21 = Py.newCode(2, var2, var1, "__init__", 132, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      getfirstweekday$22 = Py.newCode(1, var2, var1, "getfirstweekday", 135, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "firstweekday"};
      setfirstweekday$23 = Py.newCode(2, var2, var1, "setfirstweekday", 138, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      iterweekdays$24 = Py.newCode(1, var2, var1, "iterweekdays", 143, false, false, self, 24, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "year", "month", "date", "days", "oneday"};
      itermonthdates$25 = Py.newCode(3, var2, var1, "itermonthdates", 151, false, false, self, 25, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "year", "month", "date"};
      itermonthdays2$26 = Py.newCode(3, var2, var1, "itermonthdays2", 172, false, false, self, 26, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "year", "month", "date"};
      itermonthdays$27 = Py.newCode(3, var2, var1, "itermonthdays", 183, false, false, self, 27, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "year", "month", "dates", "_[200_17]", "i"};
      monthdatescalendar$28 = Py.newCode(3, var2, var1, "monthdatescalendar", 194, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "year", "month", "days", "_[210_17]", "i"};
      monthdays2calendar$29 = Py.newCode(3, var2, var1, "monthdays2calendar", 202, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "year", "month", "days", "_[218_17]", "i"};
      monthdayscalendar$30 = Py.newCode(3, var2, var1, "monthdayscalendar", 212, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "year", "width", "months", "_[228_12]", "i", "_[231_16]"};
      yeardatescalendar$31 = Py.newCode(3, var2, var1, "yeardatescalendar", 220, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "year", "width", "months", "_[241_12]", "i", "_[244_16]"};
      yeardays2calendar$32 = Py.newCode(3, var2, var1, "yeardays2calendar", 233, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "year", "width", "months", "_[253_12]", "i", "_[256_16]"};
      yeardayscalendar$33 = Py.newCode(3, var2, var1, "yeardayscalendar", 246, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TextCalendar$34 = Py.newCode(0, var2, var1, "TextCalendar", 259, false, false, self, 34, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "theweek", "width"};
      prweek$35 = Py.newCode(3, var2, var1, "prweek", 265, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "day", "weekday", "width", "s"};
      formatday$36 = Py.newCode(4, var2, var1, "formatday", 271, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "theweek", "width", "_(285_24)"};
      String[] var10001 = var2;
      calendar$py var10007 = self;
      var2 = new String[]{"self", "width"};
      formatweek$37 = Py.newCode(3, var10001, var1, "formatweek", 281, false, false, var10007, 37, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "d", "wd"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "width"};
      f$38 = Py.newCode(1, var10001, var1, "<genexpr>", 285, false, false, var10007, 38, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self", "day", "width", "names"};
      formatweekday$39 = Py.newCode(3, var2, var1, "formatweekday", 287, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "width", "_(301_24)"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "width"};
      formatweekheader$40 = Py.newCode(2, var10001, var1, "formatweekheader", 297, false, false, var10007, 40, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "i"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "width"};
      f$41 = Py.newCode(1, var10001, var1, "<genexpr>", 301, false, false, var10007, 41, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self", "theyear", "themonth", "width", "withyear", "s"};
      formatmonthname$42 = Py.newCode(5, var2, var1, "formatmonthname", 303, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "theyear", "themonth", "w", "l"};
      prmonth$43 = Py.newCode(5, var2, var1, "prmonth", 312, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "theyear", "themonth", "w", "l", "s", "week"};
      formatmonth$44 = Py.newCode(5, var2, var1, "formatmonth", 318, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "theyear", "w", "l", "c", "m", "v", "a", "i", "row", "months", "names", "_(351_21)", "headers", "_(355_23)", "height", "_(359_25)", "j", "weeks", "cal", "header", "colwidth"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"theyear", "self", "header", "colwidth"};
      formatyear$45 = Py.newCode(6, var10001, var1, "formatyear", 334, false, false, var10007, 45, var2, (String[])null, 2, 4097);
      var2 = new String[]{"_(x)", "k"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "theyear", "colwidth"};
      f$46 = Py.newCode(1, var10001, var1, "<genexpr>", 351, false, false, var10007, 46, (String[])null, var2, 0, 4129);
      var2 = new String[]{"_(x)", "k"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"header"};
      f$47 = Py.newCode(1, var10001, var1, "<genexpr>", 355, false, false, var10007, 47, (String[])null, var2, 0, 4129);
      var2 = new String[]{"_(x)", "cal"};
      f$48 = Py.newCode(1, var2, var1, "<genexpr>", 359, false, false, self, 48, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "theyear", "w", "l", "c", "m"};
      pryear$49 = Py.newCode(6, var2, var1, "pryear", 371, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      HTMLCalendar$50 = Py.newCode(0, var2, var1, "HTMLCalendar", 376, false, false, self, 50, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "day", "weekday"};
      formatday$51 = Py.newCode(3, var2, var1, "formatday", 384, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "theweek", "s", "_(397_20)"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      formatweek$52 = Py.newCode(2, var10001, var1, "formatweek", 393, false, false, var10007, 52, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "d", "wd"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      f$53 = Py.newCode(1, var10001, var1, "<genexpr>", 397, false, false, var10007, 53, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self", "day"};
      formatweekday$54 = Py.newCode(2, var2, var1, "formatweekday", 400, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "_(410_20)"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      formatweekheader$55 = Py.newCode(1, var10001, var1, "formatweekheader", 406, false, false, var10007, 55, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "i"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      f$56 = Py.newCode(1, var10001, var1, "<genexpr>", 410, false, false, var10007, 56, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self", "theyear", "themonth", "withyear", "s"};
      formatmonthname$57 = Py.newCode(4, var2, var1, "formatmonthname", 413, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "theyear", "themonth", "withyear", "v", "a", "week"};
      formatmonth$58 = Py.newCode(4, var2, var1, "formatmonth", 423, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "theyear", "width", "v", "a", "i", "months", "m"};
      formatyear$59 = Py.newCode(3, var2, var1, "formatyear", 442, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "theyear", "width", "css", "encoding", "v", "a"};
      formatyearpage$60 = Py.newCode(5, var2, var1, "formatyearpage", 464, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TimeEncoding$61 = Py.newCode(0, var2, var1, "TimeEncoding", 488, false, false, self, 61, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "locale"};
      __init__$62 = Py.newCode(2, var2, var1, "__init__", 489, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$63 = Py.newCode(1, var2, var1, "__enter__", 492, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      __exit__$64 = Py.newCode(2, var2, var1, "__exit__", 497, true, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LocaleTextCalendar$65 = Py.newCode(0, var2, var1, "LocaleTextCalendar", 501, false, false, self, 65, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "firstweekday", "locale"};
      __init__$66 = Py.newCode(3, var2, var1, "__init__", 509, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "day", "width", "encoding", "names", "name"};
      formatweekday$67 = Py.newCode(3, var2, var1, "formatweekday", 515, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "theyear", "themonth", "width", "withyear", "encoding", "s"};
      formatmonthname$68 = Py.newCode(5, var2, var1, "formatmonthname", 526, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LocaleHTMLCalendar$69 = Py.newCode(0, var2, var1, "LocaleHTMLCalendar", 536, false, false, self, 69, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "firstweekday", "locale"};
      __init__$70 = Py.newCode(3, var2, var1, "__init__", 543, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "day", "encoding", "s"};
      formatweekday$71 = Py.newCode(2, var2, var1, "formatweekday", 549, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "theyear", "themonth", "withyear", "encoding", "s"};
      formatmonthname$72 = Py.newCode(4, var2, var1, "formatmonthname", 556, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"firstweekday"};
      setfirstweekday$73 = Py.newCode(1, var2, var1, "setfirstweekday", 571, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cols", "colwidth", "spacing"};
      format$74 = Py.newCode(3, var2, var1, "format", 595, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cols", "colwidth", "spacing", "_(603_24)"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"colwidth"};
      formatstring$75 = Py.newCode(3, var10001, var1, "formatstring", 600, false, false, var10007, 75, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "c"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"colwidth"};
      f$76 = Py.newCode(1, var10001, var1, "<genexpr>", 603, false, false, var10007, 76, (String[])null, var2, 0, 4129);
      var2 = new String[]{"tuple", "year", "month", "day", "hour", "minute", "second", "days", "hours", "minutes", "seconds"};
      timegm$77 = Py.newCode(1, var2, var1, "timegm", 610, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "optparse", "parser", "options", "locale", "cal", "encoding", "optdict", "result"};
      main$78 = Py.newCode(1, var2, var1, "main", 620, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new calendar$py("calendar$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(calendar$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.IllegalMonthError$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__str__$3(var2, var3);
         case 4:
            return this.IllegalWeekdayError$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.__str__$6(var2, var3);
         case 7:
            return this._localized_month$7(var2, var3);
         case 8:
            return this.f$8(var2, var3);
         case 9:
            return this.__init__$9(var2, var3);
         case 10:
            return this.__getitem__$10(var2, var3);
         case 11:
            return this.__len__$11(var2, var3);
         case 12:
            return this._localized_day$12(var2, var3);
         case 13:
            return this.__init__$13(var2, var3);
         case 14:
            return this.__getitem__$14(var2, var3);
         case 15:
            return this.__len__$15(var2, var3);
         case 16:
            return this.isleap$16(var2, var3);
         case 17:
            return this.leapdays$17(var2, var3);
         case 18:
            return this.weekday$18(var2, var3);
         case 19:
            return this.monthrange$19(var2, var3);
         case 20:
            return this.Calendar$20(var2, var3);
         case 21:
            return this.__init__$21(var2, var3);
         case 22:
            return this.getfirstweekday$22(var2, var3);
         case 23:
            return this.setfirstweekday$23(var2, var3);
         case 24:
            return this.iterweekdays$24(var2, var3);
         case 25:
            return this.itermonthdates$25(var2, var3);
         case 26:
            return this.itermonthdays2$26(var2, var3);
         case 27:
            return this.itermonthdays$27(var2, var3);
         case 28:
            return this.monthdatescalendar$28(var2, var3);
         case 29:
            return this.monthdays2calendar$29(var2, var3);
         case 30:
            return this.monthdayscalendar$30(var2, var3);
         case 31:
            return this.yeardatescalendar$31(var2, var3);
         case 32:
            return this.yeardays2calendar$32(var2, var3);
         case 33:
            return this.yeardayscalendar$33(var2, var3);
         case 34:
            return this.TextCalendar$34(var2, var3);
         case 35:
            return this.prweek$35(var2, var3);
         case 36:
            return this.formatday$36(var2, var3);
         case 37:
            return this.formatweek$37(var2, var3);
         case 38:
            return this.f$38(var2, var3);
         case 39:
            return this.formatweekday$39(var2, var3);
         case 40:
            return this.formatweekheader$40(var2, var3);
         case 41:
            return this.f$41(var2, var3);
         case 42:
            return this.formatmonthname$42(var2, var3);
         case 43:
            return this.prmonth$43(var2, var3);
         case 44:
            return this.formatmonth$44(var2, var3);
         case 45:
            return this.formatyear$45(var2, var3);
         case 46:
            return this.f$46(var2, var3);
         case 47:
            return this.f$47(var2, var3);
         case 48:
            return this.f$48(var2, var3);
         case 49:
            return this.pryear$49(var2, var3);
         case 50:
            return this.HTMLCalendar$50(var2, var3);
         case 51:
            return this.formatday$51(var2, var3);
         case 52:
            return this.formatweek$52(var2, var3);
         case 53:
            return this.f$53(var2, var3);
         case 54:
            return this.formatweekday$54(var2, var3);
         case 55:
            return this.formatweekheader$55(var2, var3);
         case 56:
            return this.f$56(var2, var3);
         case 57:
            return this.formatmonthname$57(var2, var3);
         case 58:
            return this.formatmonth$58(var2, var3);
         case 59:
            return this.formatyear$59(var2, var3);
         case 60:
            return this.formatyearpage$60(var2, var3);
         case 61:
            return this.TimeEncoding$61(var2, var3);
         case 62:
            return this.__init__$62(var2, var3);
         case 63:
            return this.__enter__$63(var2, var3);
         case 64:
            return this.__exit__$64(var2, var3);
         case 65:
            return this.LocaleTextCalendar$65(var2, var3);
         case 66:
            return this.__init__$66(var2, var3);
         case 67:
            return this.formatweekday$67(var2, var3);
         case 68:
            return this.formatmonthname$68(var2, var3);
         case 69:
            return this.LocaleHTMLCalendar$69(var2, var3);
         case 70:
            return this.__init__$70(var2, var3);
         case 71:
            return this.formatweekday$71(var2, var3);
         case 72:
            return this.formatmonthname$72(var2, var3);
         case 73:
            return this.setfirstweekday$73(var2, var3);
         case 74:
            return this.format$74(var2, var3);
         case 75:
            return this.formatstring$75(var2, var3);
         case 76:
            return this.f$76(var2, var3);
         case 77:
            return this.timegm$77(var2, var3);
         case 78:
            return this.main$78(var2, var3);
         default:
            return null;
      }
   }
}
