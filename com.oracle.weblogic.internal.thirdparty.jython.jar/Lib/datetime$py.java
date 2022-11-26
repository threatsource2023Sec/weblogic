import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyFloat;
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
@MTime(1498849384000L)
@Filename("datetime.py")
public class datetime$py extends PyFunctionTable implements PyRunnable {
   static datetime$py self;
   static final PyCode f$0;
   static final PyCode _make_java_utc_calendar$1;
   static final PyCode _make_java_default_calendar$2;
   static final PyCode _make_java_calendar$3;
   static final PyCode _cmp$4;
   static final PyCode _round$5;
   static final PyCode _is_leap$6;
   static final PyCode _days_before_year$7;
   static final PyCode _days_in_month$8;
   static final PyCode _days_before_month$9;
   static final PyCode _ymd2ord$10;
   static final PyCode _ord2ymd$11;
   static final PyCode _build_struct_time$12;
   static final PyCode _format_time$13;
   static final PyCode _wrap_strftime$14;
   static final PyCode _check_tzname$15;
   static final PyCode _check_utc_offset$16;
   static final PyCode _check_int_field$17;
   static final PyCode _check_date_fields$18;
   static final PyCode _check_time_fields$19;
   static final PyCode _check_tzinfo_arg$20;
   static final PyCode _cmperror$21;
   static final PyCode _tmxxx$22;
   static final PyCode __init__$23;
   static final PyCode timedelta$24;
   static final PyCode __new__$25;
   static final PyCode __repr__$26;
   static final PyCode __str__$27;
   static final PyCode plural$28;
   static final PyCode total_seconds$29;
   static final PyCode days$30;
   static final PyCode seconds$31;
   static final PyCode microseconds$32;
   static final PyCode __add__$33;
   static final PyCode __sub__$34;
   static final PyCode __rsub__$35;
   static final PyCode __neg__$36;
   static final PyCode __pos__$37;
   static final PyCode __abs__$38;
   static final PyCode __mul__$39;
   static final PyCode _to_microseconds$40;
   static final PyCode __div__$41;
   static final PyCode __eq__$42;
   static final PyCode __ne__$43;
   static final PyCode __le__$44;
   static final PyCode __lt__$45;
   static final PyCode __ge__$46;
   static final PyCode __gt__$47;
   static final PyCode _cmp$48;
   static final PyCode __hash__$49;
   static final PyCode __nonzero__$50;
   static final PyCode _getstate$51;
   static final PyCode __reduce__$52;
   static final PyCode date$53;
   static final PyCode __new__$54;
   static final PyCode fromtimestamp$55;
   static final PyCode today$56;
   static final PyCode fromordinal$57;
   static final PyCode __repr__$58;
   static final PyCode ctime$59;
   static final PyCode strftime$60;
   static final PyCode __format__$61;
   static final PyCode isoformat$62;
   static final PyCode year$63;
   static final PyCode month$64;
   static final PyCode day$65;
   static final PyCode timetuple$66;
   static final PyCode toordinal$67;
   static final PyCode replace$68;
   static final PyCode __eq__$69;
   static final PyCode __ne__$70;
   static final PyCode __le__$71;
   static final PyCode __lt__$72;
   static final PyCode __ge__$73;
   static final PyCode __gt__$74;
   static final PyCode _cmp$75;
   static final PyCode __hash__$76;
   static final PyCode _checkOverflow$77;
   static final PyCode __add__$78;
   static final PyCode __sub__$79;
   static final PyCode weekday$80;
   static final PyCode isoweekday$81;
   static final PyCode isocalendar$82;
   static final PyCode _getstate$83;
   static final PyCode _date__setstate$84;
   static final PyCode __reduce__$85;
   static final PyCode __tojava__$86;
   static final PyCode tzinfo$87;
   static final PyCode tzname$88;
   static final PyCode utcoffset$89;
   static final PyCode dst$90;
   static final PyCode fromutc$91;
   static final PyCode __reduce__$92;
   static final PyCode time$93;
   static final PyCode __new__$94;
   static final PyCode hour$95;
   static final PyCode minute$96;
   static final PyCode second$97;
   static final PyCode microsecond$98;
   static final PyCode tzinfo$99;
   static final PyCode __eq__$100;
   static final PyCode __ne__$101;
   static final PyCode __le__$102;
   static final PyCode __lt__$103;
   static final PyCode __ge__$104;
   static final PyCode __gt__$105;
   static final PyCode _cmp$106;
   static final PyCode __hash__$107;
   static final PyCode _tzstr$108;
   static final PyCode __repr__$109;
   static final PyCode isoformat$110;
   static final PyCode strftime$111;
   static final PyCode __format__$112;
   static final PyCode utcoffset$113;
   static final PyCode _utcoffset$114;
   static final PyCode tzname$115;
   static final PyCode dst$116;
   static final PyCode _dst$117;
   static final PyCode replace$118;
   static final PyCode __nonzero__$119;
   static final PyCode _getstate$120;
   static final PyCode _time__setstate$121;
   static final PyCode __reduce__$122;
   static final PyCode __tojava__$123;
   static final PyCode datetime$124;
   static final PyCode __new__$125;
   static final PyCode hour$126;
   static final PyCode minute$127;
   static final PyCode second$128;
   static final PyCode microsecond$129;
   static final PyCode tzinfo$130;
   static final PyCode fromtimestamp$131;
   static final PyCode utcfromtimestamp$132;
   static final PyCode now$133;
   static final PyCode utcnow$134;
   static final PyCode combine$135;
   static final PyCode timetuple$136;
   static final PyCode utctimetuple$137;
   static final PyCode date$138;
   static final PyCode time$139;
   static final PyCode timetz$140;
   static final PyCode replace$141;
   static final PyCode astimezone$142;
   static final PyCode ctime$143;
   static final PyCode isoformat$144;
   static final PyCode __repr__$145;
   static final PyCode __str__$146;
   static final PyCode strptime$147;
   static final PyCode utcoffset$148;
   static final PyCode _utcoffset$149;
   static final PyCode tzname$150;
   static final PyCode dst$151;
   static final PyCode _dst$152;
   static final PyCode __eq__$153;
   static final PyCode __ne__$154;
   static final PyCode __le__$155;
   static final PyCode __lt__$156;
   static final PyCode __ge__$157;
   static final PyCode __gt__$158;
   static final PyCode _cmp$159;
   static final PyCode __add__$160;
   static final PyCode __sub__$161;
   static final PyCode __hash__$162;
   static final PyCode _getstate$163;
   static final PyCode _datetime__setstate$164;
   static final PyCode __reduce__$165;
   static final PyCode __tojava__$166;
   static final PyCode _isoweek1monday$167;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Concrete date/time and related types -- prototype implemented in Python.\n\nSee http://www.zope.org/Members/fdrake/DateTimeWiki/FrontPage\n\nSee also http://dir.yahoo.com/Reference/calendars/\n\nFor a primer on DST, including many current DST rules, see\nhttp://webexhibits.org/daylightsaving/\n\nFor more about DST than you ever wanted to know, see\nftp://elsie.nci.nih.gov/pub/\n\nSources for time zone and DST data: http://www.twinsun.com/tz/tz-link.htm\n\nThis was originally copied from the sandbox of the CPython CVS repository.\nThanks to Tim Peters for suggesting using it.\n"));
      var1.setline(17);
      PyString.fromInterned("Concrete date/time and related types -- prototype implemented in Python.\n\nSee http://www.zope.org/Members/fdrake/DateTimeWiki/FrontPage\n\nSee also http://dir.yahoo.com/Reference/calendars/\n\nFor a primer on DST, including many current DST rules, see\nhttp://webexhibits.org/daylightsaving/\n\nFor more about DST than you ever wanted to know, see\nftp://elsie.nci.nih.gov/pub/\n\nSources for time zone and DST data: http://www.twinsun.com/tz/tz-link.htm\n\nThis was originally copied from the sandbox of the CPython CVS repository.\nThanks to Tim Peters for suggesting using it.\n");
      var1.setline(19);
      String[] var3 = new String[]{"division"};
      PyObject[] var6 = imp.importFrom("__future__", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("division", var4);
      var4 = null;
      var1.setline(20);
      PyObject var7 = imp.importOneAs("time", var1, -1);
      var1.setlocal("_time", var7);
      var3 = null;
      var1.setline(21);
      var7 = imp.importOneAs("math", var1, -1);
      var1.setlocal("_math", var7);
      var3 = null;
      var1.setline(22);
      var7 = imp.importOneAs("struct", var1, -1);
      var1.setlocal("_struct", var7);
      var3 = null;
      var1.setline(23);
      var7 = imp.importOneAs("sys", var1, -1);
      var1.setlocal("_sys", var7);
      var3 = null;
      var1.setline(25);
      PyFunction var8;
      if (var1.getname("_sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
         var1.setline(26);
         var3 = new String[]{"Object"};
         var6 = imp.importFrom("java.lang", var3, var1, -1);
         var4 = var6[0];
         var1.setlocal("Object", var4);
         var4 = null;
         var1.setline(27);
         var3 = new String[]{"Date", "Time", "Timestamp"};
         var6 = imp.importFrom("java.sql", var3, var1, -1);
         var4 = var6[0];
         var1.setlocal("Date", var4);
         var4 = null;
         var4 = var6[1];
         var1.setlocal("Time", var4);
         var4 = null;
         var4 = var6[2];
         var1.setlocal("Timestamp", var4);
         var4 = null;
         var1.setline(28);
         var3 = new String[]{"Calendar", "GregorianCalendar", "TimeZone"};
         var6 = imp.importFrom("java.util", var3, var1, -1);
         var4 = var6[0];
         var1.setlocal("Calendar", var4);
         var4 = null;
         var4 = var6[1];
         var1.setlocal("GregorianCalendar", var4);
         var4 = null;
         var4 = var6[2];
         var1.setlocal("TimeZone", var4);
         var4 = null;
         var1.setline(29);
         var3 = new String[]{"Py"};
         var6 = imp.importFrom("org.python.core", var3, var1, -1);
         var4 = var6[0];
         var1.setlocal("Py", var4);
         var4 = null;
         var1.setline(38);
         var7 = var1.getname("TimeZone").__getattr__("getTimeZone").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UTC"));
         var1.setlocal("_utc_timezone", var7);
         var3 = null;
         var1.setline(39);
         var7 = var1.getname("True");
         var1.setlocal("_is_jython", var7);
         var3 = null;
         var1.setline(41);
         var6 = Py.EmptyObjects;
         var8 = new PyFunction(var1.f_globals, var6, _make_java_utc_calendar$1, (PyObject)null);
         var1.setlocal("_make_java_utc_calendar", var8);
         var3 = null;
         var1.setline(46);
         var6 = Py.EmptyObjects;
         var8 = new PyFunction(var1.f_globals, var6, _make_java_default_calendar$2, (PyObject)null);
         var1.setlocal("_make_java_default_calendar", var8);
         var3 = null;
         var1.setline(49);
         var6 = Py.EmptyObjects;
         var8 = new PyFunction(var1.f_globals, var6, _make_java_calendar$3, (PyObject)null);
         var1.setlocal("_make_java_calendar", var8);
         var3 = null;
      } else {
         var1.setline(67);
         var7 = var1.getname("False");
         var1.setlocal("_is_jython", var7);
         var3 = null;
      }

      var1.setline(70);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _cmp$4, (PyObject)null);
      var1.setlocal("_cmp", var8);
      var3 = null;
      var1.setline(73);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, _round$5, (PyObject)null);
      var1.setlocal("_round", var8);
      var3 = null;
      var1.setline(76);
      PyInteger var10 = Py.newInteger(1);
      var1.setlocal("MINYEAR", var10);
      var3 = null;
      var1.setline(77);
      var10 = Py.newInteger(9999);
      var1.setlocal("MAXYEAR", var10);
      var3 = null;
      var1.setline(78);
      var10 = Py.newInteger(1900);
      var1.setlocal("_MINYEARFMT", var10);
      var3 = null;
      var1.setline(89);
      PyList var11 = new PyList(new PyObject[]{Py.newInteger(-1), Py.newInteger(31), Py.newInteger(28), Py.newInteger(31), Py.newInteger(30), Py.newInteger(31), Py.newInteger(30), Py.newInteger(31), Py.newInteger(31), Py.newInteger(30), Py.newInteger(31), Py.newInteger(30), Py.newInteger(31)});
      var1.setlocal("_DAYS_IN_MONTH", var11);
      var3 = null;
      var1.setline(91);
      var11 = new PyList(new PyObject[]{Py.newInteger(-1)});
      var1.setlocal("_DAYS_BEFORE_MONTH", var11);
      var3 = null;
      var1.setline(92);
      var10 = Py.newInteger(0);
      var1.setlocal("dbm", var10);
      var3 = null;
      var1.setline(93);
      var7 = var1.getname("_DAYS_IN_MONTH").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

      while(true) {
         var1.setline(93);
         var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(96);
            var1.dellocal("dbm");
            var1.dellocal("dim");
            var1.setline(98);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _is_leap$6, PyString.fromInterned("year -> 1 if leap year, else 0."));
            var1.setlocal("_is_leap", var8);
            var3 = null;
            var1.setline(102);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _days_before_year$7, PyString.fromInterned("year -> number of days before January 1st of year."));
            var1.setlocal("_days_before_year", var8);
            var3 = null;
            var1.setline(107);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _days_in_month$8, PyString.fromInterned("year, month -> number of days in that month in that year."));
            var1.setlocal("_days_in_month", var8);
            var3 = null;
            var1.setline(114);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _days_before_month$9, PyString.fromInterned("year, month -> number of days in year preceding first day of month."));
            var1.setlocal("_days_before_month", var8);
            var3 = null;
            var1.setline(119);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _ymd2ord$10, PyString.fromInterned("year, month, day -> ordinal, considering 01-Jan-0001 as day 1."));
            var1.setlocal("_ymd2ord", var8);
            var3 = null;
            var1.setline(128);
            var7 = var1.getname("_days_before_year").__call__((ThreadState)var2, (PyObject)Py.newInteger(401));
            var1.setlocal("_DI400Y", var7);
            var3 = null;
            var1.setline(129);
            var7 = var1.getname("_days_before_year").__call__((ThreadState)var2, (PyObject)Py.newInteger(101));
            var1.setlocal("_DI100Y", var7);
            var3 = null;
            var1.setline(130);
            var7 = var1.getname("_days_before_year").__call__((ThreadState)var2, (PyObject)Py.newInteger(5));
            var1.setlocal("_DI4Y", var7);
            var3 = null;
            var1.setline(134);
            PyObject var10000;
            if (var1.getglobal("__debug__").__nonzero__()) {
               var7 = var1.getname("_DI4Y");
               var10000 = var7._eq(Py.newInteger(4)._mul(Py.newInteger(365))._add(Py.newInteger(1)));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(138);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var7 = var1.getname("_DI400Y");
               var10000 = var7._eq(Py.newInteger(4)._mul(var1.getname("_DI100Y"))._add(Py.newInteger(1)));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(142);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var7 = var1.getname("_DI100Y");
               var10000 = var7._eq(Py.newInteger(25)._mul(var1.getname("_DI4Y"))._sub(Py.newInteger(1)));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(144);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _ord2ymd$11, PyString.fromInterned("ordinal -> (year, month, day), considering 01-Jan-0001 as day 1."));
            var1.setlocal("_ord2ymd", var8);
            var3 = null;
            var1.setline(207);
            var11 = new PyList(new PyObject[]{var1.getname("None"), PyString.fromInterned("Jan"), PyString.fromInterned("Feb"), PyString.fromInterned("Mar"), PyString.fromInterned("Apr"), PyString.fromInterned("May"), PyString.fromInterned("Jun"), PyString.fromInterned("Jul"), PyString.fromInterned("Aug"), PyString.fromInterned("Sep"), PyString.fromInterned("Oct"), PyString.fromInterned("Nov"), PyString.fromInterned("Dec")});
            var1.setlocal("_MONTHNAMES", var11);
            var3 = null;
            var1.setline(209);
            var11 = new PyList(new PyObject[]{var1.getname("None"), PyString.fromInterned("Mon"), PyString.fromInterned("Tue"), PyString.fromInterned("Wed"), PyString.fromInterned("Thu"), PyString.fromInterned("Fri"), PyString.fromInterned("Sat"), PyString.fromInterned("Sun")});
            var1.setlocal("_DAYNAMES", var11);
            var3 = null;
            var1.setline(212);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _build_struct_time$12, (PyObject)null);
            var1.setlocal("_build_struct_time", var8);
            var3 = null;
            var1.setline(217);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _format_time$13, (PyObject)null);
            var1.setlocal("_format_time", var8);
            var3 = null;
            var1.setline(225);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _wrap_strftime$14, (PyObject)null);
            var1.setlocal("_wrap_strftime", var8);
            var3 = null;
            var1.setline(286);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _check_tzname$15, (PyObject)null);
            var1.setlocal("_check_tzname", var8);
            var3 = null;
            var1.setline(297);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _check_utc_offset$16, (PyObject)null);
            var1.setlocal("_check_utc_offset", var8);
            var3 = null;
            var1.setline(318);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _check_int_field$17, (PyObject)null);
            var1.setlocal("_check_int_field", var8);
            var3 = null;
            var1.setline(333);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _check_date_fields$18, (PyObject)null);
            var1.setlocal("_check_date_fields", var8);
            var3 = null;
            var1.setline(346);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _check_time_fields$19, (PyObject)null);
            var1.setlocal("_check_time_fields", var8);
            var3 = null;
            var1.setline(361);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _check_tzinfo_arg$20, (PyObject)null);
            var1.setlocal("_check_tzinfo_arg", var8);
            var3 = null;
            var1.setline(389);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _cmperror$21, (PyObject)null);
            var1.setlocal("_cmperror", var8);
            var3 = null;
            var1.setline(402);
            var6 = Py.EmptyObjects;
            var4 = Py.makeClass("_tmxxx", var6, _tmxxx$22);
            var1.setlocal("_tmxxx", var4);
            var4 = null;
            Arrays.fill(var6, (Object)null);
            var1.setline(463);
            var6 = new PyObject[]{var1.getname("object")};
            var4 = Py.makeClass("timedelta", var6, timedelta$24);
            var1.setlocal("timedelta", var4);
            var4 = null;
            Arrays.fill(var6, (Object)null);
            var1.setline(753);
            var7 = var1.getname("timedelta").__call__((ThreadState)var2, (PyObject)Py.newInteger(-999999999));
            var1.getname("timedelta").__setattr__("min", var7);
            var3 = null;
            var1.setline(754);
            var10000 = var1.getname("timedelta");
            var6 = new PyObject[]{Py.newInteger(999999999), Py.newInteger(23), Py.newInteger(59), Py.newInteger(59), Py.newInteger(999999)};
            String[] var9 = new String[]{"days", "hours", "minutes", "seconds", "microseconds"};
            var10000 = var10000.__call__(var2, var6, var9);
            var3 = null;
            var7 = var10000;
            var1.getname("timedelta").__setattr__("max", var7);
            var3 = null;
            var1.setline(756);
            var10000 = var1.getname("timedelta");
            var6 = new PyObject[]{Py.newInteger(1)};
            var9 = new String[]{"microseconds"};
            var10000 = var10000.__call__(var2, var6, var9);
            var3 = null;
            var7 = var10000;
            var1.getname("timedelta").__setattr__("resolution", var7);
            var3 = null;
            var1.setline(758);
            var6 = new PyObject[]{var1.getname("object")};
            var4 = Py.makeClass("date", var6, date$53);
            var1.setlocal("date", var4);
            var4 = null;
            Arrays.fill(var6, (Object)null);
            var1.setline(1089);
            var7 = var1.getname("date");
            var1.setlocal("_date_class", var7);
            var3 = null;
            var1.setline(1091);
            var7 = var1.getname("date").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(1));
            var1.getname("date").__setattr__("min", var7);
            var3 = null;
            var1.setline(1092);
            var7 = var1.getname("date").__call__((ThreadState)var2, Py.newInteger(9999), (PyObject)Py.newInteger(12), (PyObject)Py.newInteger(31));
            var1.getname("date").__setattr__("max", var7);
            var3 = null;
            var1.setline(1093);
            var10000 = var1.getname("timedelta");
            var6 = new PyObject[]{Py.newInteger(1)};
            var9 = new String[]{"days"};
            var10000 = var10000.__call__(var2, var6, var9);
            var3 = null;
            var7 = var10000;
            var1.getname("date").__setattr__("resolution", var7);
            var3 = null;
            var1.setline(1095);
            var6 = new PyObject[]{var1.getname("object")};
            var4 = Py.makeClass("tzinfo", var6, tzinfo$87);
            var1.setlocal("tzinfo", var4);
            var4 = null;
            Arrays.fill(var6, (Object)null);
            var1.setline(1166);
            var7 = var1.getname("tzinfo");
            var1.setlocal("_tzinfo_class", var7);
            var3 = null;
            var1.setline(1168);
            var6 = new PyObject[]{var1.getname("object")};
            var4 = Py.makeClass("time", var6, time$93);
            var1.setlocal("time", var4);
            var4 = null;
            Arrays.fill(var6, (Object)null);
            var1.setline(1517);
            var7 = var1.getname("time");
            var1.setlocal("_time_class", var7);
            var3 = null;
            var1.setline(1519);
            var7 = var1.getname("time").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(0));
            var1.getname("time").__setattr__("min", var7);
            var3 = null;
            var1.setline(1520);
            var7 = var1.getname("time").__call__(var2, Py.newInteger(23), Py.newInteger(59), Py.newInteger(59), Py.newInteger(999999));
            var1.getname("time").__setattr__("max", var7);
            var3 = null;
            var1.setline(1521);
            var10000 = var1.getname("timedelta");
            var6 = new PyObject[]{Py.newInteger(1)};
            var9 = new String[]{"microseconds"};
            var10000 = var10000.__call__(var2, var6, var9);
            var3 = null;
            var7 = var10000;
            var1.getname("time").__setattr__("resolution", var7);
            var3 = null;
            var1.setline(1523);
            var6 = new PyObject[]{var1.getname("date")};
            var4 = Py.makeClass("datetime", var6, datetime$124);
            var1.setlocal("datetime", var4);
            var4 = null;
            Arrays.fill(var6, (Object)null);
            var1.setline(2033);
            var7 = var1.getname("datetime").__call__((ThreadState)var2, Py.newInteger(1), (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(1));
            var1.getname("datetime").__setattr__("min", var7);
            var3 = null;
            var1.setline(2034);
            var10000 = var1.getname("datetime");
            var6 = new PyObject[]{Py.newInteger(9999), Py.newInteger(12), Py.newInteger(31), Py.newInteger(23), Py.newInteger(59), Py.newInteger(59), Py.newInteger(999999)};
            var7 = var10000.__call__(var2, var6);
            var1.getname("datetime").__setattr__("max", var7);
            var3 = null;
            var1.setline(2035);
            var10000 = var1.getname("timedelta");
            var6 = new PyObject[]{Py.newInteger(1)};
            var9 = new String[]{"microseconds"};
            var10000 = var10000.__call__(var2, var6, var9);
            var3 = null;
            var7 = var10000;
            var1.getname("datetime").__setattr__("resolution", var7);
            var3 = null;
            var1.setline(2038);
            var6 = Py.EmptyObjects;
            var8 = new PyFunction(var1.f_globals, var6, _isoweek1monday$167, (PyObject)null);
            var1.setlocal("_isoweek1monday", var8);
            var3 = null;
            var1.setline(2245);
            PyString.fromInterned("\nSome time zone algebra.  For a datetime x, let\n    x.n = x stripped of its timezone -- its naive time.\n    x.o = x.utcoffset(), and assuming that doesn't raise an exception or\n          return None\n    x.d = x.dst(), and assuming that doesn't raise an exception or\n          return None\n    x.s = x's standard offset, x.o - x.d\n\nNow some derived rules, where k is a duration (timedelta).\n\n1. x.o = x.s + x.d\n   This follows from the definition of x.s.\n\n2. If x and y have the same tzinfo member, x.s = y.s.\n   This is actually a requirement, an assumption we need to make about\n   sane tzinfo classes.\n\n3. The naive UTC time corresponding to x is x.n - x.o.\n   This is again a requirement for a sane tzinfo class.\n\n4. (x+k).s = x.s\n   This follows from #2, and that datimetimetz+timedelta preserves tzinfo.\n\n5. (x+k).n = x.n + k\n   Again follows from how arithmetic is defined.\n\nNow we can explain tz.fromutc(x).  Let's assume it's an interesting case\n(meaning that the various tzinfo methods exist, and don't blow up or return\nNone when called).\n\nThe function wants to return a datetime y with timezone tz, equivalent to x.\nx is already in UTC.\n\nBy #3, we want\n\n    y.n - y.o = x.n                             [1]\n\nThe algorithm starts by attaching tz to x.n, and calling that y.  So\nx.n = y.n at the start.  Then it wants to add a duration k to y, so that [1]\nbecomes true; in effect, we want to solve [2] for k:\n\n   (y+k).n - (y+k).o = x.n                      [2]\n\nBy #1, this is the same as\n\n   (y+k).n - ((y+k).s + (y+k).d) = x.n          [3]\n\nBy #5, (y+k).n = y.n + k, which equals x.n + k because x.n=y.n at the start.\nSubstituting that into [3],\n\n   x.n + k - (y+k).s - (y+k).d = x.n; the x.n terms cancel, leaving\n   k - (y+k).s - (y+k).d = 0; rearranging,\n   k = (y+k).s - (y+k).d; by #4, (y+k).s == y.s, so\n   k = y.s - (y+k).d\n\nOn the RHS, (y+k).d can't be computed directly, but y.s can be, and we\napproximate k by ignoring the (y+k).d term at first.  Note that k can't be\nvery large, since all offset-returning methods return a duration of magnitude\nless than 24 hours.  For that reason, if y is firmly in std time, (y+k).d must\nbe 0, so ignoring it has no consequence then.\n\nIn any case, the new value is\n\n    z = y + y.s                                 [4]\n\nIt's helpful to step back at look at [4] from a higher level:  it's simply\nmapping from UTC to tz's standard time.\n\nAt this point, if\n\n    z.n - z.o = x.n                             [5]\n\nwe have an equivalent time, and are almost done.  The insecurity here is\nat the start of daylight time.  Picture US Eastern for concreteness.  The wall\ntime jumps from 1:59 to 3:00, and wall hours of the form 2:MM don't make good\nsense then.  The docs ask that an Eastern tzinfo class consider such a time to\nbe EDT (because it's \"after 2\"), which is a redundant spelling of 1:MM EST\non the day DST starts.  We want to return the 1:MM EST spelling because that's\nthe only spelling that makes sense on the local wall clock.\n\nIn fact, if [5] holds at this point, we do have the standard-time spelling,\nbut that takes a bit of proof.  We first prove a stronger result.  What's the\ndifference between the LHS and RHS of [5]?  Let\n\n    diff = x.n - (z.n - z.o)                    [6]\n\nNow\n    z.n =                       by [4]\n    (y + y.s).n =               by #5\n    y.n + y.s =                 since y.n = x.n\n    x.n + y.s =                 since z and y are have the same tzinfo member,\n                                    y.s = z.s by #2\n    x.n + z.s\n\nPlugging that back into [6] gives\n\n    diff =\n    x.n - ((x.n + z.s) - z.o) =     expanding\n    x.n - x.n - z.s + z.o =         cancelling\n    - z.s + z.o =                   by #2\n    z.d\n\nSo diff = z.d.\n\nIf [5] is true now, diff = 0, so z.d = 0 too, and we have the standard-time\nspelling we wanted in the endcase described above.  We're done.  Contrarily,\nif z.d = 0, then we have a UTC equivalent, and are also done.\n\nIf [5] is not true now, diff = z.d != 0, and z.d is the offset we need to\nadd to z (in effect, z is in tz's standard time, and we need to shift the\nlocal clock into tz's daylight time).\n\nLet\n\n    z' = z + z.d = z + diff                     [7]\n\nand we can again ask whether\n\n    z'.n - z'.o = x.n                           [8]\n\nIf so, we're done.  If not, the tzinfo class is insane, according to the\nassumptions we've made.  This also requires a bit of proof.  As before, let's\ncompute the difference between the LHS and RHS of [8] (and skipping some of\nthe justifications for the kinds of substitutions we've done several times\nalready):\n\n    diff' = x.n - (z'.n - z'.o) =           replacing z'.n via [7]\n            x.n  - (z.n + diff - z'.o) =    replacing diff via [6]\n            x.n - (z.n + x.n - (z.n - z.o) - z'.o) =\n            x.n - z.n - x.n + z.n - z.o + z'.o =    cancel x.n\n            - z.n + z.n - z.o + z'.o =              cancel z.n\n            - z.o + z'.o =                      #1 twice\n            -z.s - z.d + z'.s + z'.d =          z and z' have same tzinfo\n            z'.d - z.d\n\nSo z' is UTC-equivalent to x iff z'.d = z.d at this point.  If they are equal,\nwe've found the UTC-equivalent so are done.  In fact, we stop with [7] and\nreturn z', not bothering to compute z'.d.\n\nHow could z.d and z'd differ?  z' = z + z.d [7], so merely moving z' by\na dst() offset, and starting *from* a time already in DST (we know z.d != 0),\nwould have to change the result dst() returns:  we start in DST, and moving\na little further into it takes us out of DST.\n\nThere isn't a sane case where this can happen.  The closest it gets is at\nthe end of DST, where there's an hour in UTC with no spelling in a hybrid\ntzinfo class.  In US Eastern, that's 5:MM UTC = 0:MM EST = 1:MM EDT.  During\nthat hour, on an Eastern clock 1:MM is taken as being in standard time (6:MM\nUTC) because the docs insist on that, but 0:MM is taken as being in daylight\ntime (4:MM UTC).  There is no local time mapping to 5:MM UTC.  The local\nclock jumps from 1:59 back to 1:00 again, and repeats the 1:MM hour in\nstandard time.  Since that's what the local clock *does*, we want to map both\nUTC hours 5:MM and 6:MM to 1:MM Eastern.  The result is ambiguous\nin local time, but so it goes -- it's the way the local clock works.\n\nWhen x = 5:MM UTC is the input to this algorithm, x.o=0, y.o=-5 and y.d=0,\nso z=0:MM.  z.d=60 (minutes) then, so [5] doesn't hold and we keep going.\nz' = z + z.d = 1:MM then, and z'.d=0, and z'.d - z.d = -60 != 0 so [8]\n(correctly) concludes that z' is not UTC-equivalent to x.\n\nBecause we know z.d said z was in daylight time (else [5] would have held and\nwe would have stopped then), and we know z.d != z'.d (else [8] would have held\nand we have stopped then), and there are only 2 possible values dst() can\nreturn in Eastern, it follows that z'.d must be 0 (which it is in the example,\nbut the reasoning doesn't depend on the example -- it depends on there being\ntwo possible dst() outcomes, one zero and the other non-zero).  Therefore\nz' must be in standard time, and is the spelling we want in this case.\n\nNote again that z' is not UTC-equivalent as far as the hybrid tzinfo class is\nconcerned (because it takes z' as being in standard time rather than the\ndaylight time we intend here), but returning it gives the real-life \"local\nclock repeats an hour\" behavior when mapping the \"unspellable\" UTC hour into\ntz.\n\nWhen the input is 6:MM, z=1:MM and z.d=0, and we stop at once, again with\nthe 1:MM standard time spelling we want.\n\nSo how can this break?  One of the assumptions must be violated.  Two\npossibilities:\n\n1) [2] effectively says that y.s is invariant across all y belong to a given\n   time zone.  This isn't true if, for political reasons or continental drift,\n   a region decides to change its base offset from UTC.\n\n2) There may be versions of \"double daylight\" time where the tail end of\n   the analysis gives up a step too early.  I haven't thought about that\n   enough to say.\n\nIn any case, it's clear that the default fromutc() is strong enough to handle\n\"almost all\" time zones:  so long as the standard offset is invariant, it\ndoesn't matter if daylight time transition points change from year to year, or\nif daylight time is skipped in some years; it doesn't matter how large or\nsmall dst() may get within its bounds; and it doesn't even matter if some\nperverse time zone returns a negative dst()).  So a breaking case must be\npretty bizarre, and a tzinfo subclass can override fromutc() if it is.\n");
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal("dim", var4);
         var1.setline(94);
         var1.getname("_DAYS_BEFORE_MONTH").__getattr__("append").__call__(var2, var1.getname("dbm"));
         var1.setline(95);
         PyObject var5 = var1.getname("dbm");
         var5 = var5._iadd(var1.getname("dim"));
         var1.setlocal("dbm", var5);
      }
   }

   public PyObject _make_java_utc_calendar$1(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getglobal("GregorianCalendar").__call__(var2, var1.getglobal("_utc_timezone"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(43);
      var1.getlocal(0).__getattr__("clear").__call__(var2);
      var1.setline(44);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _make_java_default_calendar$2(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var10000 = var1.getglobal("GregorianCalendar");
      PyObject[] var3 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _make_java_calendar$3(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      PyObject var3 = var1.getlocal(0).__getattr__("tzinfo");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(51);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(52);
         var3 = var1.getglobal("GregorianCalendar").__call__(var2, var1.getglobal("_utc_timezone"));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(54);
         var3 = var1.getlocal(1).__getattr__("tzname").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(55);
         var3 = var1.getlocal(3);
         var10000 = var3._ne(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(58);
            var3 = var1.getglobal("TimeZone").__getattr__("getTimeZone").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(59);
            var3 = var1.getlocal(4).__getattr__("getID").__call__(var2);
            var10000 = var3._ne(var1.getlocal(3));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(4).__getattr__("getID").__call__(var2);
               var10000 = var3._eq(PyString.fromInterned("GMT"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(60);
               var3 = var1.getglobal("GregorianCalendar").__call__(var2, var1.getglobal("_utc_timezone"));
               var1.setlocal(2, var3);
               var3 = null;
               var1.setline(61);
               var1.getlocal(2).__getattr__("set").__call__(var2, var1.getglobal("Calendar").__getattr__("DST_OFFSET"), var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("dst").__call__(var2, var1.getlocal(0)).__getattr__("total_seconds").__call__(var2)._mul(Py.newInteger(1000))));
               var1.setline(62);
               var1.getlocal(2).__getattr__("set").__call__(var2, var1.getglobal("Calendar").__getattr__("ZONE_OFFSET"), var1.getglobal("int").__call__(var2, var1.getlocal(1).__getattr__("utcoffset").__call__(var2, var1.getlocal(0)).__getattr__("total_seconds").__call__(var2)._mul(Py.newInteger(1000))));
            } else {
               var1.setline(64);
               var3 = var1.getglobal("GregorianCalendar").__call__(var2, var1.getlocal(4));
               var1.setlocal(2, var3);
               var3 = null;
            }
         }
      }

      var1.setline(65);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _cmp$4(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      var1.setline(71);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      PyInteger var5;
      if (var10000.__nonzero__()) {
         var5 = Py.newInteger(0);
      } else {
         var1.setline(71);
         var3 = var1.getlocal(0);
         var10000 = var3._gt(var1.getlocal(1));
         var3 = null;
         var5 = var10000.__nonzero__() ? Py.newInteger(1) : Py.newInteger(-1);
      }

      PyInteger var4 = var5;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _round$5(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var10000 = var1.getglobal("int");
      var1.setline(74);
      PyObject var3 = var1.getlocal(0);
      PyObject var10002 = var3._ge(Py.newFloat(0.0));
      var3 = null;
      var3 = var10000.__call__(var2, var10002.__nonzero__() ? var1.getglobal("_math").__getattr__("floor").__call__(var2, var1.getlocal(0)._add(Py.newFloat(0.5))) : var1.getglobal("_math").__getattr__("ceil").__call__(var2, var1.getlocal(0)._sub(Py.newFloat(0.5))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _is_leap$6(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyString.fromInterned("year -> 1 if leap year, else 0.");
      var1.setline(100);
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

   public PyObject _days_before_year$7(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyString.fromInterned("year -> number of days before January 1st of year.");
      var1.setline(104);
      PyObject var3 = var1.getlocal(0)._sub(Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(105);
      var3 = var1.getlocal(1)._mul(Py.newInteger(365))._add(var1.getlocal(1)._floordiv(Py.newInteger(4)))._sub(var1.getlocal(1)._floordiv(Py.newInteger(100)))._add(var1.getlocal(1)._floordiv(Py.newInteger(400)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _days_in_month$8(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      PyString.fromInterned("year, month -> number of days in that month in that year.");
      var1.setline(109);
      PyInteger var3;
      PyObject var5;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = Py.newInteger(1);
         PyObject var10001 = var1.getlocal(1);
         PyInteger var10000 = var3;
         var5 = var10001;
         PyObject var4;
         if ((var4 = var10000._le(var10001)).__nonzero__()) {
            var4 = var5._le(Py.newInteger(12));
         }

         var3 = null;
         if (!var4.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), var1.getlocal(1));
         }
      }

      var1.setline(110);
      var5 = var1.getlocal(1);
      PyObject var6 = var5._eq(Py.newInteger(2));
      var3 = null;
      if (var6.__nonzero__()) {
         var6 = var1.getglobal("_is_leap").__call__(var2, var1.getlocal(0));
      }

      if (var6.__nonzero__()) {
         var1.setline(111);
         var3 = Py.newInteger(29);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(112);
         var5 = var1.getglobal("_DAYS_IN_MONTH").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var5;
      }
   }

   public PyObject _days_before_month$9(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyString.fromInterned("year, month -> number of days in year preceding first day of month.");
      var1.setline(116);
      PyObject var10001;
      PyInteger var3;
      PyObject var5;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = Py.newInteger(1);
         var10001 = var1.getlocal(1);
         PyInteger var10000 = var3;
         var5 = var10001;
         PyObject var4;
         if ((var4 = var10000._le(var10001)).__nonzero__()) {
            var4 = var5._le(Py.newInteger(12));
         }

         var3 = null;
         if (!var4.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("month must be in 1..12"));
         }
      }

      var1.setline(117);
      PyObject var6 = var1.getglobal("_DAYS_BEFORE_MONTH").__getitem__(var1.getlocal(1));
      var5 = var1.getlocal(1);
      var10001 = var5._gt(Py.newInteger(2));
      var3 = null;
      if (var10001.__nonzero__()) {
         var10001 = var1.getglobal("_is_leap").__call__(var2, var1.getlocal(0));
      }

      var5 = var6._add(var10001);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _ymd2ord$10(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyString.fromInterned("year, month, day -> ordinal, considering 01-Jan-0001 as day 1.");
      var1.setline(121);
      PyInteger var10000;
      PyObject var10001;
      PyInteger var3;
      PyObject var4;
      PyObject var5;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = Py.newInteger(1);
         var10001 = var1.getlocal(1);
         var10000 = var3;
         var5 = var10001;
         if ((var4 = var10000._le(var10001)).__nonzero__()) {
            var4 = var5._le(Py.newInteger(12));
         }

         var3 = null;
         if (!var4.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("month must be in 1..12"));
         }
      }

      var1.setline(122);
      var5 = var1.getglobal("_days_in_month").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(123);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = Py.newInteger(1);
         var10001 = var1.getlocal(2);
         var10000 = var3;
         var5 = var10001;
         if ((var4 = var10000._le(var10001)).__nonzero__()) {
            var4 = var5._le(var1.getlocal(3));
         }

         var3 = null;
         if (!var4.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("day must be in 1..%d")._mod(var1.getlocal(3)));
         }
      }

      var1.setline(124);
      var5 = var1.getglobal("_days_before_year").__call__(var2, var1.getlocal(0))._add(var1.getglobal("_days_before_month").__call__(var2, var1.getlocal(0), var1.getlocal(1)))._add(var1.getlocal(2));
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _ord2ymd$11(PyFrame var1, ThreadState var2) {
      var1.setline(145);
      PyString.fromInterned("ordinal -> (year, month, day), considering 01-Jan-0001 as day 1.");
      var1.setline(167);
      PyObject var3 = var1.getlocal(0);
      var3 = var3._isub(Py.newInteger(1));
      var1.setlocal(0, var3);
      var1.setline(168);
      var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(0), var1.getglobal("_DI400Y"));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(0, var5);
      var5 = null;
      var3 = null;
      var1.setline(169);
      var3 = var1.getlocal(1)._mul(Py.newInteger(400))._add(Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(176);
      var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(0), var1.getglobal("_DI100Y"));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(0, var5);
      var5 = null;
      var3 = null;
      var1.setline(179);
      var3 = var1.getglobal("divmod").__call__(var2, var1.getlocal(0), var1.getglobal("_DI4Y"));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(0, var5);
      var5 = null;
      var3 = null;
      var1.setline(183);
      var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(365));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(0, var5);
      var5 = null;
      var3 = null;
      var1.setline(185);
      var3 = var1.getlocal(2);
      var3 = var3._iadd(var1.getlocal(3)._mul(Py.newInteger(100))._add(var1.getlocal(4)._mul(Py.newInteger(4)))._add(var1.getlocal(5)));
      var1.setlocal(2, var3);
      var1.setline(186);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._eq(Py.newInteger(4));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._eq(Py.newInteger(4));
         var3 = null;
      }

      PyTuple var7;
      if (var10000.__nonzero__()) {
         var1.setline(187);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(0);
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(188);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(2)._sub(Py.newInteger(1)), Py.newInteger(12), Py.newInteger(31)});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(192);
         PyObject var6 = var1.getlocal(5);
         var10000 = var6._eq(Py.newInteger(3));
         var4 = null;
         if (var10000.__nonzero__()) {
            var6 = var1.getlocal(4);
            var10000 = var6._ne(Py.newInteger(24));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var6 = var1.getlocal(3);
               var10000 = var6._eq(Py.newInteger(3));
               var4 = null;
            }
         }

         var6 = var10000;
         var1.setlocal(6, var6);
         var4 = null;
         var1.setline(193);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var6 = var1.getlocal(6);
            var10000 = var6._eq(var1.getglobal("_is_leap").__call__(var2, var1.getlocal(2)));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(194);
         var6 = var1.getlocal(0)._add(Py.newInteger(50))._rshift(Py.newInteger(5));
         var1.setlocal(7, var6);
         var4 = null;
         var1.setline(195);
         var10000 = var1.getglobal("_DAYS_BEFORE_MONTH").__getitem__(var1.getlocal(7));
         var6 = var1.getlocal(7);
         PyObject var10001 = var6._gt(Py.newInteger(2));
         var4 = null;
         if (var10001.__nonzero__()) {
            var10001 = var1.getlocal(6);
         }

         var6 = var10000._add(var10001);
         var1.setlocal(8, var6);
         var4 = null;
         var1.setline(196);
         var6 = var1.getlocal(8);
         var10000 = var6._gt(var1.getlocal(0));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(197);
            var6 = var1.getlocal(7);
            var6 = var6._isub(Py.newInteger(1));
            var1.setlocal(7, var6);
            var1.setline(198);
            var6 = var1.getlocal(8);
            var10000 = var1.getglobal("_DAYS_IN_MONTH").__getitem__(var1.getlocal(7));
            var5 = var1.getlocal(7);
            var10001 = var5._eq(Py.newInteger(2));
            var5 = null;
            if (var10001.__nonzero__()) {
               var10001 = var1.getlocal(6);
            }

            var6 = var6._isub(var10000._add(var10001));
            var1.setlocal(8, var6);
         }

         var1.setline(199);
         var6 = var1.getlocal(0);
         var6 = var6._isub(var1.getlocal(8));
         var1.setlocal(0, var6);
         var1.setline(200);
         if (var1.getglobal("__debug__").__nonzero__()) {
            PyInteger var8 = Py.newInteger(0);
            var10001 = var1.getlocal(0);
            PyInteger var9 = var8;
            var6 = var10001;
            if ((var5 = var9._le(var10001)).__nonzero__()) {
               var5 = var6._lt(var1.getglobal("_days_in_month").__call__(var2, var1.getlocal(2), var1.getlocal(7)));
            }

            var4 = null;
            if (!var5.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(204);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(7), var1.getlocal(0)._add(Py.newInteger(1))});
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject _build_struct_time$12(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      PyObject var3 = var1.getglobal("_ymd2ord").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2))._add(Py.newInteger(6))._mod(Py.newInteger(7));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(214);
      var3 = var1.getglobal("_days_before_month").__call__(var2, var1.getlocal(0), var1.getlocal(1))._add(var1.getlocal(2));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(215);
      var3 = var1.getglobal("_time").__getattr__("struct_time").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(7), var1.getlocal(8), var1.getlocal(6)})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _format_time$13(PyFrame var1, ThreadState var2) {
      var1.setline(219);
      PyObject var3 = PyString.fromInterned("%02d:%02d:%02d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)}));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(220);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(221);
         var3 = var1.getlocal(4);
         var3 = var3._iadd(PyString.fromInterned(".%06d")._mod(var1.getlocal(3)));
         var1.setlocal(4, var3);
      }

      var1.setline(222);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _wrap_strftime$14(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      PyObject var3 = var1.getlocal(2).__getitem__(Py.newInteger(0));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(227);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._lt(var1.getglobal("_MINYEARFMT"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(228);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("year=%d is before %d; the datetime strftime() methods require year >= %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getglobal("_MINYEARFMT"), var1.getglobal("_MINYEARFMT")}))));
      } else {
         var1.setline(232);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(233);
         var3 = var1.getglobal("None");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(234);
         var3 = var1.getglobal("None");
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(237);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.setlocal(7, var6);
         var3 = null;
         var1.setline(238);
         var3 = var1.getlocal(7).__getattr__("append");
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(239);
         PyTuple var7 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getglobal("len").__call__(var2, var1.getlocal(1))});
         PyObject[] var4 = Py.unpackSequence(var7, 2);
         PyObject var5 = var4[0];
         var1.setlocal(9, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(10, var5);
         var5 = null;
         var3 = null;

         while(true) {
            var1.setline(240);
            var3 = var1.getlocal(9);
            var10000 = var3._lt(var1.getlocal(10));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(282);
               var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(7));
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(283);
               var3 = var1.getglobal("_time").__getattr__("strftime").__call__(var2, var1.getlocal(7), var1.getlocal(2));
               var1.f_lasti = -1;
               return var3;
            }

            var1.setline(241);
            var3 = var1.getlocal(1).__getitem__(var1.getlocal(9));
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(242);
            var3 = var1.getlocal(9);
            var3 = var3._iadd(Py.newInteger(1));
            var1.setlocal(9, var3);
            var1.setline(243);
            var3 = var1.getlocal(11);
            var10000 = var3._eq(PyString.fromInterned("%"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(244);
               var3 = var1.getlocal(9);
               var10000 = var3._lt(var1.getlocal(10));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(245);
                  var3 = var1.getlocal(1).__getitem__(var1.getlocal(9));
                  var1.setlocal(11, var3);
                  var3 = null;
                  var1.setline(246);
                  var3 = var1.getlocal(9);
                  var3 = var3._iadd(Py.newInteger(1));
                  var1.setlocal(9, var3);
                  var1.setline(247);
                  var3 = var1.getlocal(11);
                  var10000 = var3._eq(PyString.fromInterned("f"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(248);
                     var3 = var1.getlocal(4);
                     var10000 = var3._is(var1.getglobal("None"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(249);
                        var3 = PyString.fromInterned("%06d")._mod(var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("microsecond"), (PyObject)Py.newInteger(0)));
                        var1.setlocal(4, var3);
                        var3 = null;
                     }

                     var1.setline(251);
                     var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(4));
                  } else {
                     var1.setline(252);
                     var3 = var1.getlocal(11);
                     var10000 = var3._eq(PyString.fromInterned("z"));
                     var3 = null;
                     PyString var8;
                     if (var10000.__nonzero__()) {
                        var1.setline(253);
                        var3 = var1.getlocal(5);
                        var10000 = var3._is(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(254);
                           var8 = PyString.fromInterned("");
                           var1.setlocal(5, var8);
                           var3 = null;
                           var1.setline(255);
                           if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("_utcoffset")).__nonzero__()) {
                              var1.setline(256);
                              var3 = var1.getlocal(0).__getattr__("_utcoffset").__call__(var2);
                              var1.setlocal(12, var3);
                              var3 = null;
                              var1.setline(257);
                              var3 = var1.getlocal(12);
                              var10000 = var3._isnot(var1.getglobal("None"));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(258);
                                 var8 = PyString.fromInterned("+");
                                 var1.setlocal(13, var8);
                                 var3 = null;
                                 var1.setline(259);
                                 var3 = var1.getlocal(12);
                                 var10000 = var3._lt(Py.newInteger(0));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(260);
                                    var3 = var1.getlocal(12).__neg__();
                                    var1.setlocal(12, var3);
                                    var3 = null;
                                    var1.setline(261);
                                    var8 = PyString.fromInterned("-");
                                    var1.setlocal(13, var8);
                                    var3 = null;
                                 }

                                 var1.setline(262);
                                 var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(12), (PyObject)Py.newInteger(60));
                                 var4 = Py.unpackSequence(var3, 2);
                                 var5 = var4[0];
                                 var1.setlocal(14, var5);
                                 var5 = null;
                                 var5 = var4[1];
                                 var1.setlocal(15, var5);
                                 var5 = null;
                                 var3 = null;
                                 var1.setline(263);
                                 var3 = PyString.fromInterned("%c%02d%02d")._mod(new PyTuple(new PyObject[]{var1.getlocal(13), var1.getlocal(14), var1.getlocal(15)}));
                                 var1.setlocal(5, var3);
                                 var3 = null;
                              }
                           }
                        }

                        var1.setline(264);
                        if (var1.getglobal("__debug__").__nonzero__()) {
                           var8 = PyString.fromInterned("%");
                           var10000 = var8._notin(var1.getlocal(5));
                           var3 = null;
                           if (!var10000.__nonzero__()) {
                              var10000 = Py.None;
                              throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                           }
                        }

                        var1.setline(265);
                        var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(5));
                     } else {
                        var1.setline(266);
                        var3 = var1.getlocal(11);
                        var10000 = var3._eq(PyString.fromInterned("Z"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(267);
                           var3 = var1.getlocal(6);
                           var10000 = var3._is(var1.getglobal("None"));
                           var3 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(268);
                              var8 = PyString.fromInterned("");
                              var1.setlocal(6, var8);
                              var3 = null;
                              var1.setline(269);
                              if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("tzname")).__nonzero__()) {
                                 var1.setline(270);
                                 var3 = var1.getlocal(0).__getattr__("tzname").__call__(var2);
                                 var1.setlocal(16, var3);
                                 var3 = null;
                                 var1.setline(271);
                                 var3 = var1.getlocal(16);
                                 var10000 = var3._isnot(var1.getglobal("None"));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(273);
                                    var3 = var1.getlocal(16).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"), (PyObject)PyString.fromInterned("%%"));
                                    var1.setlocal(6, var3);
                                    var3 = null;
                                 }
                              }
                           }

                           var1.setline(274);
                           var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(6));
                        } else {
                           var1.setline(276);
                           var1.getlocal(8).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"));
                           var1.setline(277);
                           var1.getlocal(8).__call__(var2, var1.getlocal(11));
                        }
                     }
                  }
               } else {
                  var1.setline(279);
                  var1.getlocal(8).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("%"));
               }
            } else {
               var1.setline(281);
               var1.getlocal(8).__call__(var2, var1.getlocal(11));
            }
         }
      }
   }

   public PyObject _check_tzname$15(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("str")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(288);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("tzinfo.tzname() must return None or string, not '%s'")._mod(var1.getglobal("type").__call__(var2, var1.getlocal(0)))));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _check_utc_offset$16(PyFrame var1, ThreadState var2) {
      var1.setline(298);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(0);
         var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("utcoffset"), PyString.fromInterned("dst")}));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(299);
      var3 = var1.getlocal(1);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(300);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(301);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__not__().__nonzero__()) {
            var1.setline(302);
            throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("tzinfo.%s() must return None or timedelta, not '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("type").__call__(var2, var1.getlocal(1))}))));
         } else {
            var1.setline(304);
            var3 = var1.getlocal(1).__getattr__("days");
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(305);
            var3 = var1.getlocal(2);
            var10000 = var3._lt(Py.newInteger(-1));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(2);
               var10000 = var3._gt(Py.newInteger(0));
               var3 = null;
            }

            PyInteger var7;
            if (var10000.__nonzero__()) {
               var1.setline(306);
               var7 = Py.newInteger(1440);
               var1.setlocal(1, var7);
               var3 = null;
            } else {
               var1.setline(308);
               var3 = var1.getlocal(2)._mul(Py.newInteger(86400))._add(var1.getlocal(1).__getattr__("seconds"));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(309);
               var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(60));
               PyObject[] var4 = Py.unpackSequence(var3, 2);
               PyObject var5 = var4[0];
               var1.setlocal(4, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(3, var5);
               var5 = null;
               var3 = null;
               var1.setline(310);
               var10000 = var1.getlocal(3);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(1).__getattr__("microseconds");
               }

               if (var10000.__nonzero__()) {
                  var1.setline(311);
                  throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("tzinfo.%s() must return a whole number of minutes")._mod(var1.getlocal(0))));
               }

               var1.setline(313);
               var3 = var1.getlocal(4);
               var1.setlocal(1, var3);
               var3 = null;
            }

            var1.setline(314);
            var7 = Py.newInteger(-1440);
            PyObject var10001 = var1.getlocal(1);
            PyInteger var8 = var7;
            var3 = var10001;
            PyObject var6;
            if ((var6 = var8._lt(var10001)).__nonzero__()) {
               var6 = var3._lt(Py.newInteger(1440));
            }

            var3 = null;
            if (var6.__not__().__nonzero__()) {
               var1.setline(315);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("%s()=%d, must be in -1439..1439")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)}))));
            } else {
               var1.setline(316);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _check_int_field$17(PyFrame var1, ThreadState var2) {
      var1.setline(319);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("int")).__nonzero__()) {
         var1.setline(320);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(321);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("float")).__not__().__nonzero__()) {
            PyException var4;
            try {
               var1.setline(323);
               PyObject var6 = var1.getlocal(0).__getattr__("__int__").__call__(var2);
               var1.setlocal(0, var6);
               var4 = null;
            } catch (Throwable var5) {
               var4 = Py.setException(var5, var1);
               if (var4.match(var1.getglobal("AttributeError"))) {
                  var1.setline(325);
                  var1.setline(330);
                  throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("an integer is required")));
               }

               throw var4;
            }

            var1.setline(327);
            if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
               var1.setline(328);
               var3 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(329);
               throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__int__ method should return an integer")));
            }
         } else {
            var1.setline(331);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("integer argument expected, got float")));
         }
      }
   }

   public PyObject _check_date_fields$18(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      PyObject var3 = var1.getglobal("_check_int_field").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(335);
      var3 = var1.getglobal("_check_int_field").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(336);
      var3 = var1.getglobal("_check_int_field").__call__(var2, var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(337);
      var3 = var1.getglobal("MINYEAR");
      PyObject var10001 = var1.getlocal(0);
      PyObject var10000 = var3;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var3._le(var1.getglobal("MAXYEAR"));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(338);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("year must be in %d..%d")._mod(new PyTuple(new PyObject[]{var1.getglobal("MINYEAR"), var1.getglobal("MAXYEAR")})), var1.getlocal(0)));
      } else {
         var1.setline(339);
         PyInteger var5 = Py.newInteger(1);
         var10001 = var1.getlocal(1);
         PyInteger var7 = var5;
         var3 = var10001;
         if ((var4 = var7._le(var10001)).__nonzero__()) {
            var4 = var3._le(Py.newInteger(12));
         }

         var3 = null;
         if (var4.__not__().__nonzero__()) {
            var1.setline(340);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("month must be in 1..12"), (PyObject)var1.getlocal(1)));
         } else {
            var1.setline(341);
            var3 = var1.getglobal("_days_in_month").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(342);
            var5 = Py.newInteger(1);
            var10001 = var1.getlocal(2);
            var7 = var5;
            var3 = var10001;
            if ((var4 = var7._le(var10001)).__nonzero__()) {
               var4 = var3._le(var1.getlocal(3));
            }

            var3 = null;
            if (var4.__not__().__nonzero__()) {
               var1.setline(343);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("day must be in 1..%d")._mod(var1.getlocal(3)), var1.getlocal(2)));
            } else {
               var1.setline(344);
               PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)});
               var1.f_lasti = -1;
               return var6;
            }
         }
      }
   }

   public PyObject _check_time_fields$19(PyFrame var1, ThreadState var2) {
      var1.setline(347);
      PyObject var3 = var1.getglobal("_check_int_field").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(348);
      var3 = var1.getglobal("_check_int_field").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(349);
      var3 = var1.getglobal("_check_int_field").__call__(var2, var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(350);
      var3 = var1.getglobal("_check_int_field").__call__(var2, var1.getlocal(3));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(351);
      PyInteger var5 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(0);
      PyInteger var10000 = var5;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var3._le(Py.newInteger(23));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(352);
         throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hour must be in 0..23"), (PyObject)var1.getlocal(0)));
      } else {
         var1.setline(353);
         var5 = Py.newInteger(0);
         var10001 = var1.getlocal(1);
         var10000 = var5;
         var3 = var10001;
         if ((var4 = var10000._le(var10001)).__nonzero__()) {
            var4 = var3._le(Py.newInteger(59));
         }

         var3 = null;
         if (var4.__not__().__nonzero__()) {
            var1.setline(354);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("minute must be in 0..59"), (PyObject)var1.getlocal(1)));
         } else {
            var1.setline(355);
            var5 = Py.newInteger(0);
            var10001 = var1.getlocal(2);
            var10000 = var5;
            var3 = var10001;
            if ((var4 = var10000._le(var10001)).__nonzero__()) {
               var4 = var3._le(Py.newInteger(59));
            }

            var3 = null;
            if (var4.__not__().__nonzero__()) {
               var1.setline(356);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("second must be in 0..59"), (PyObject)var1.getlocal(2)));
            } else {
               var1.setline(357);
               var5 = Py.newInteger(0);
               var10001 = var1.getlocal(3);
               var10000 = var5;
               var3 = var10001;
               if ((var4 = var10000._le(var10001)).__nonzero__()) {
                  var4 = var3._le(Py.newInteger(999999));
               }

               var3 = null;
               if (var4.__not__().__nonzero__()) {
                  var1.setline(358);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("microsecond must be in 0..999999"), (PyObject)var1.getlocal(3)));
               } else {
                  var1.setline(359);
                  PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
                  var1.f_lasti = -1;
                  return var6;
               }
            }
         }
      }
   }

   public PyObject _check_tzinfo_arg$20(PyFrame var1, ThreadState var2) {
      var1.setline(362);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("tzinfo")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(363);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tzinfo argument must be None or of a tzinfo subclass")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _cmperror$21(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("can't compare '%s' to '%s'")._mod(new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(0)).__getattr__("__name__"), var1.getglobal("type").__call__(var2, var1.getlocal(1)).__getattr__("__name__")}))));
   }

   public PyObject _tmxxx$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(404);
      PyObject var3 = var1.getname("None");
      var1.setlocal("ordinal", var3);
      var3 = null;
      var1.setline(406);
      PyObject[] var4 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$23, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$23(PyFrame var1, ThreadState var2) {
      var1.setline(409);
      PyInteger var3 = Py.newInteger(0);
      PyObject var10001 = var1.getlocal(7);
      PyInteger var10000 = var3;
      PyObject var6 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var6._le(Py.newInteger(999999));
      }

      var3 = null;
      PyObject var5;
      PyObject[] var7;
      if (var4.__not__().__nonzero__()) {
         var1.setline(410);
         var6 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)Py.newInteger(1000000));
         var7 = Py.unpackSequence(var6, 2);
         var5 = var7[0];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var7[1];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
         var1.setline(411);
         var6 = var1.getlocal(6);
         var6 = var6._iadd(var1.getlocal(8));
         var1.setlocal(6, var6);
      }

      var1.setline(412);
      var3 = Py.newInteger(0);
      var10001 = var1.getlocal(6);
      var10000 = var3;
      var6 = var10001;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var6._le(Py.newInteger(59));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(413);
         var6 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)Py.newInteger(60));
         var7 = Py.unpackSequence(var6, 2);
         var5 = var7[0];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var7[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(414);
         var6 = var1.getlocal(5);
         var6 = var6._iadd(var1.getlocal(8));
         var1.setlocal(5, var6);
      }

      var1.setline(415);
      var3 = Py.newInteger(0);
      var10001 = var1.getlocal(5);
      var10000 = var3;
      var6 = var10001;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var6._le(Py.newInteger(59));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(416);
         var6 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)Py.newInteger(60));
         var7 = Py.unpackSequence(var6, 2);
         var5 = var7[0];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var7[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(417);
         var6 = var1.getlocal(4);
         var6 = var6._iadd(var1.getlocal(8));
         var1.setlocal(4, var6);
      }

      var1.setline(418);
      var3 = Py.newInteger(0);
      var10001 = var1.getlocal(4);
      var10000 = var3;
      var6 = var10001;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var6._le(Py.newInteger(23));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(419);
         var6 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(24));
         var7 = Py.unpackSequence(var6, 2);
         var5 = var7[0];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var7[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(420);
         var6 = var1.getlocal(3);
         var6 = var6._iadd(var1.getlocal(8));
         var1.setlocal(3, var6);
      }

      var1.setline(428);
      var3 = Py.newInteger(1);
      var10001 = var1.getlocal(2);
      var10000 = var3;
      var6 = var10001;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var6._le(Py.newInteger(12));
      }

      var3 = null;
      PyObject var9;
      if (var4.__not__().__nonzero__()) {
         var1.setline(429);
         var6 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(2)._sub(Py.newInteger(1)), (PyObject)Py.newInteger(12));
         var7 = Py.unpackSequence(var6, 2);
         var5 = var7[0];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var7[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(430);
         var6 = var1.getlocal(1);
         var6 = var6._iadd(var1.getlocal(8));
         var1.setlocal(1, var6);
         var1.setline(431);
         var6 = var1.getlocal(2);
         var6 = var6._iadd(Py.newInteger(1));
         var1.setlocal(2, var6);
         var1.setline(432);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = Py.newInteger(1);
            var10001 = var1.getlocal(2);
            var10000 = var3;
            var6 = var10001;
            if ((var4 = var10000._le(var10001)).__nonzero__()) {
               var4 = var6._le(Py.newInteger(12));
            }

            var3 = null;
            if (!var4.__nonzero__()) {
               var9 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var9);
            }
         }
      }

      var1.setline(438);
      var6 = var1.getglobal("_days_in_month").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(9, var6);
      var3 = null;
      var1.setline(439);
      var3 = Py.newInteger(1);
      var10001 = var1.getlocal(3);
      var10000 = var3;
      var6 = var10001;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var6._le(var1.getlocal(9));
      }

      var3 = null;
      PyTuple var8;
      if (var4.__not__().__nonzero__()) {
         var1.setline(443);
         var6 = var1.getlocal(3);
         var9 = var6._eq(Py.newInteger(0));
         var3 = null;
         if (var9.__nonzero__()) {
            var1.setline(444);
            var6 = var1.getlocal(2);
            var6 = var6._isub(Py.newInteger(1));
            var1.setlocal(2, var6);
            var1.setline(445);
            var6 = var1.getlocal(2);
            var9 = var6._gt(Py.newInteger(0));
            var3 = null;
            if (var9.__nonzero__()) {
               var1.setline(446);
               var6 = var1.getglobal("_days_in_month").__call__(var2, var1.getlocal(1), var1.getlocal(2));
               var1.setlocal(3, var6);
               var3 = null;
            } else {
               var1.setline(448);
               var8 = new PyTuple(new PyObject[]{var1.getlocal(1)._sub(Py.newInteger(1)), Py.newInteger(12), Py.newInteger(31)});
               var7 = Py.unpackSequence(var8, 3);
               var5 = var7[0];
               var1.setlocal(1, var5);
               var5 = null;
               var5 = var7[1];
               var1.setlocal(2, var5);
               var5 = null;
               var5 = var7[2];
               var1.setlocal(3, var5);
               var5 = null;
               var3 = null;
            }
         } else {
            var1.setline(449);
            var6 = var1.getlocal(3);
            var9 = var6._eq(var1.getlocal(9)._add(Py.newInteger(1)));
            var3 = null;
            if (var9.__nonzero__()) {
               var1.setline(450);
               var6 = var1.getlocal(2);
               var6 = var6._iadd(Py.newInteger(1));
               var1.setlocal(2, var6);
               var1.setline(451);
               var3 = Py.newInteger(1);
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(452);
               var6 = var1.getlocal(2);
               var9 = var6._gt(Py.newInteger(12));
               var3 = null;
               if (var9.__nonzero__()) {
                  var1.setline(453);
                  var3 = Py.newInteger(1);
                  var1.setlocal(2, var3);
                  var3 = null;
                  var1.setline(454);
                  var6 = var1.getlocal(1);
                  var6 = var6._iadd(Py.newInteger(1));
                  var1.setlocal(1, var6);
               }
            } else {
               var1.setline(456);
               var6 = var1.getglobal("_ymd2ord").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(1))._add(var1.getlocal(3)._sub(Py.newInteger(1)));
               var1.getlocal(0).__setattr__("ordinal", var6);
               var3 = null;
               var1.setline(457);
               var6 = var1.getglobal("_ord2ymd").__call__(var2, var1.getlocal(0).__getattr__("ordinal"));
               var7 = Py.unpackSequence(var6, 3);
               var5 = var7[0];
               var1.setlocal(1, var5);
               var5 = null;
               var5 = var7[1];
               var1.setlocal(2, var5);
               var5 = null;
               var5 = var7[2];
               var1.setlocal(3, var5);
               var5 = null;
               var3 = null;
            }
         }
      }

      var1.setline(459);
      var8 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
      var7 = Py.unpackSequence(var8, 3);
      var5 = var7[0];
      var1.getlocal(0).__setattr__("year", var5);
      var5 = null;
      var5 = var7[1];
      var1.getlocal(0).__setattr__("month", var5);
      var5 = null;
      var5 = var7[2];
      var1.getlocal(0).__setattr__("day", var5);
      var5 = null;
      var3 = null;
      var1.setline(460);
      var8 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)});
      var7 = Py.unpackSequence(var8, 3);
      var5 = var7[0];
      var1.getlocal(0).__setattr__("hour", var5);
      var5 = null;
      var5 = var7[1];
      var1.getlocal(0).__setattr__("minute", var5);
      var5 = null;
      var5 = var7[2];
      var1.getlocal(0).__setattr__("second", var5);
      var5 = null;
      var3 = null;
      var1.setline(461);
      var6 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("microsecond", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject timedelta$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Represent the difference between two datetime objects.\n\n    Supported operators:\n\n    - add, subtract timedelta\n    - unary plus, minus, abs\n    - compare to timedelta\n    - multiply, divide by int/long\n\n    In addition, datetime supports subtraction of two datetime objects\n    returning a timedelta, and addition or subtraction of a datetime\n    and a timedelta giving a datetime.\n\n    Representation: (days, seconds, microseconds).  Why?  Because I\n    felt like it.\n    "));
      var1.setline(479);
      PyString.fromInterned("Represent the difference between two datetime objects.\n\n    Supported operators:\n\n    - add, subtract timedelta\n    - unary plus, minus, abs\n    - compare to timedelta\n    - multiply, divide by int/long\n\n    In addition, datetime supports subtraction of two datetime objects\n    returning a timedelta, and addition or subtraction of a datetime\n    and a timedelta giving a datetime.\n\n    Representation: (days, seconds, microseconds).  Why?  Because I\n    felt like it.\n    ");
      var1.setline(480);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("_days"), PyString.fromInterned("_seconds"), PyString.fromInterned("_microseconds"), PyString.fromInterned("_hashcode")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(482);
      PyObject[] var4 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$25, (PyObject)null);
      var1.setlocal("__new__", var5);
      var3 = null;
      var1.setline(584);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$26, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(596);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __str__$27, (PyObject)null);
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(608);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, total_seconds$29, PyString.fromInterned("Total seconds in the duration."));
      var1.setlocal("total_seconds", var5);
      var3 = null;
      var1.setline(614);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, days$30, PyString.fromInterned("days"));
      PyObject var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("days", var6);
      var3 = null;
      var1.setline(619);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, seconds$31, PyString.fromInterned("seconds"));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("seconds", var6);
      var3 = null;
      var1.setline(624);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, microseconds$32, PyString.fromInterned("microseconds"));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("microseconds", var6);
      var3 = null;
      var1.setline(629);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __add__$33, (PyObject)null);
      var1.setlocal("__add__", var5);
      var3 = null;
      var1.setline(638);
      var6 = var1.getname("__add__");
      var1.setlocal("__radd__", var6);
      var3 = null;
      var1.setline(640);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __sub__$34, (PyObject)null);
      var1.setlocal("__sub__", var5);
      var3 = null;
      var1.setline(649);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __rsub__$35, (PyObject)null);
      var1.setlocal("__rsub__", var5);
      var3 = null;
      var1.setline(654);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __neg__$36, (PyObject)null);
      var1.setlocal("__neg__", var5);
      var3 = null;
      var1.setline(661);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __pos__$37, (PyObject)null);
      var1.setlocal("__pos__", var5);
      var3 = null;
      var1.setline(664);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __abs__$38, (PyObject)null);
      var1.setlocal("__abs__", var5);
      var3 = null;
      var1.setline(670);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __mul__$39, (PyObject)null);
      var1.setlocal("__mul__", var5);
      var3 = null;
      var1.setline(679);
      var6 = var1.getname("__mul__");
      var1.setlocal("__rmul__", var6);
      var3 = null;
      var1.setline(681);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _to_microseconds$40, (PyObject)null);
      var1.setlocal("_to_microseconds", var5);
      var3 = null;
      var1.setline(685);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __div__$41, (PyObject)null);
      var1.setlocal("__div__", var5);
      var3 = null;
      var1.setline(691);
      var6 = var1.getname("__div__");
      var1.setlocal("__floordiv__", var6);
      var3 = null;
      var1.setline(695);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __eq__$42, (PyObject)null);
      var1.setlocal("__eq__", var5);
      var3 = null;
      var1.setline(701);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ne__$43, (PyObject)null);
      var1.setlocal("__ne__", var5);
      var3 = null;
      var1.setline(707);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __le__$44, (PyObject)null);
      var1.setlocal("__le__", var5);
      var3 = null;
      var1.setline(713);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __lt__$45, (PyObject)null);
      var1.setlocal("__lt__", var5);
      var3 = null;
      var1.setline(719);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ge__$46, (PyObject)null);
      var1.setlocal("__ge__", var5);
      var3 = null;
      var1.setline(725);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __gt__$47, (PyObject)null);
      var1.setlocal("__gt__", var5);
      var3 = null;
      var1.setline(731);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _cmp$48, (PyObject)null);
      var1.setlocal("_cmp", var5);
      var3 = null;
      var1.setline(735);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __hash__$49, (PyObject)null);
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(740);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __nonzero__$50, (PyObject)null);
      var1.setlocal("__nonzero__", var5);
      var3 = null;
      var1.setline(747);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _getstate$51, (PyObject)null);
      var1.setlocal("_getstate", var5);
      var3 = null;
      var1.setline(750);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __reduce__$52, (PyObject)null);
      var1.setlocal("__reduce__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __new__$25(PyFrame var1, ThreadState var2) {
      var1.setline(496);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(8, var3);
      var1.setlocal(9, var3);
      var1.setlocal(10, var3);
      var1.setline(499);
      PyObject var6 = var1.getlocal(1);
      var6 = var6._iadd(var1.getlocal(7)._mul(Py.newInteger(7)));
      var1.setlocal(1, var6);
      var1.setline(500);
      var6 = var1.getlocal(2);
      var6 = var6._iadd(var1.getlocal(5)._mul(Py.newInteger(60))._add(var1.getlocal(6)._mul(Py.newInteger(3600))));
      var1.setlocal(2, var6);
      var1.setline(501);
      var6 = var1.getlocal(3);
      var6 = var6._iadd(var1.getlocal(4)._mul(Py.newInteger(1000)));
      var1.setlocal(3, var6);
      var1.setline(505);
      PyObject var10000;
      PyObject[] var4;
      PyObject var5;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("float")).__nonzero__()) {
         var1.setline(506);
         var6 = var1.getglobal("_math").__getattr__("modf").__call__(var2, var1.getlocal(1));
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.setlocal(11, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(1, var5);
         var5 = null;
         var3 = null;
         var1.setline(507);
         var6 = var1.getglobal("_math").__getattr__("modf").__call__(var2, var1.getlocal(11)._mul(Py.newFloat(24.0)._mul(Py.newFloat(3600.0))));
         var4 = Py.unpackSequence(var6, 2);
         var5 = var4[0];
         var1.setlocal(12, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(13, var5);
         var5 = null;
         var3 = null;
         var1.setline(508);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var6 = var1.getlocal(13);
            var10000 = var6._eq(var1.getglobal("int").__call__(var2, var1.getlocal(13)));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(509);
         var6 = var1.getglobal("int").__call__(var2, var1.getlocal(13));
         var1.setlocal(9, var6);
         var3 = null;
         var1.setline(510);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var6 = var1.getlocal(1);
            var10000 = var6._eq(var1.getglobal("int").__call__(var2, var1.getlocal(1)));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(511);
         var6 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
         var1.setlocal(8, var6);
         var3 = null;
      } else {
         var1.setline(513);
         PyFloat var8 = Py.newFloat(0.0);
         var1.setlocal(12, var8);
         var3 = null;
         var1.setline(514);
         var6 = var1.getlocal(1);
         var1.setlocal(8, var6);
         var3 = null;
      }

      var1.setline(515);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(12), var1.getglobal("float")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(516);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var6 = var1.getglobal("abs").__call__(var2, var1.getlocal(12));
            var10000 = var6._le(Py.newFloat(1.0));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(517);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         } else {
            var1.setline(518);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var6 = var1.getglobal("abs").__call__(var2, var1.getlocal(9));
               var10000 = var6._le(Py.newInteger(24)._mul(Py.newInteger(3600)));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(521);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("float")).__nonzero__()) {
               var1.setline(522);
               var6 = var1.getglobal("_math").__getattr__("modf").__call__(var2, var1.getlocal(2));
               var4 = Py.unpackSequence(var6, 2);
               var5 = var4[0];
               var1.setlocal(14, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(2, var5);
               var5 = null;
               var3 = null;
               var1.setline(523);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var6 = var1.getlocal(2);
                  var10000 = var6._eq(var1.getglobal("int").__call__(var2, var1.getlocal(2)));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var10000 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                  }
               }

               var1.setline(524);
               var6 = var1.getglobal("int").__call__(var2, var1.getlocal(2));
               var1.setlocal(2, var6);
               var3 = null;
               var1.setline(525);
               var6 = var1.getlocal(14);
               var6 = var6._iadd(var1.getlocal(12));
               var1.setlocal(14, var6);
               var1.setline(526);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var6 = var1.getglobal("abs").__call__(var2, var1.getlocal(14));
                  var10000 = var6._le(Py.newFloat(2.0));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var10000 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                  }
               }
            } else {
               var1.setline(528);
               var6 = var1.getlocal(12);
               var1.setlocal(14, var6);
               var3 = null;
            }

            var1.setline(530);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(14), var1.getglobal("float")).__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            } else {
               var1.setline(531);
               if (var1.getglobal("__debug__").__nonzero__()) {
                  var6 = var1.getglobal("abs").__call__(var2, var1.getlocal(14));
                  var10000 = var6._le(Py.newFloat(2.0));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var10000 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                  }
               }

               var1.setline(533);
               if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               } else {
                  var1.setline(534);
                  var6 = var1.getglobal("divmod").__call__(var2, var1.getlocal(2), Py.newInteger(24)._mul(Py.newInteger(3600)));
                  var4 = Py.unpackSequence(var6, 2);
                  var5 = var4[0];
                  var1.setlocal(1, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(2, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(535);
                  var6 = var1.getlocal(8);
                  var6 = var6._iadd(var1.getlocal(1));
                  var1.setlocal(8, var6);
                  var1.setline(536);
                  var6 = var1.getlocal(9);
                  var6 = var6._iadd(var1.getglobal("int").__call__(var2, var1.getlocal(2)));
                  var1.setlocal(9, var6);
                  var1.setline(537);
                  if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(9), var1.getglobal("int")).__nonzero__()) {
                     var10000 = Py.None;
                     throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                  } else {
                     var1.setline(538);
                     if (var1.getglobal("__debug__").__nonzero__()) {
                        var6 = var1.getglobal("abs").__call__(var2, var1.getlocal(9));
                        var10000 = var6._le(Py.newInteger(2)._mul(Py.newInteger(24))._mul(Py.newInteger(3600)));
                        var3 = null;
                        if (!var10000.__nonzero__()) {
                           var10000 = Py.None;
                           throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                        }
                     }

                     var1.setline(541);
                     var6 = var1.getlocal(14)._mul(Py.newFloat(1000000.0));
                     var1.setlocal(15, var6);
                     var3 = null;
                     var1.setline(542);
                     if (var1.getglobal("__debug__").__nonzero__()) {
                        var6 = var1.getglobal("abs").__call__(var2, var1.getlocal(15));
                        var10000 = var6._lt(Py.newFloat(2100000.0));
                        var3 = null;
                        if (!var10000.__nonzero__()) {
                           var10000 = Py.None;
                           throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                        }
                     }

                     var1.setline(545);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("float")).__nonzero__()) {
                        var1.setline(546);
                        var6 = var1.getglobal("_round").__call__(var2, var1.getlocal(3)._add(var1.getlocal(15)));
                        var1.setlocal(3, var6);
                        var3 = null;
                        var1.setline(547);
                        var6 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(1000000));
                        var4 = Py.unpackSequence(var6, 2);
                        var5 = var4[0];
                        var1.setlocal(2, var5);
                        var5 = null;
                        var5 = var4[1];
                        var1.setlocal(3, var5);
                        var5 = null;
                        var3 = null;
                        var1.setline(548);
                        var6 = var1.getglobal("divmod").__call__(var2, var1.getlocal(2), Py.newInteger(24)._mul(Py.newInteger(3600)));
                        var4 = Py.unpackSequence(var6, 2);
                        var5 = var4[0];
                        var1.setlocal(1, var5);
                        var5 = null;
                        var5 = var4[1];
                        var1.setlocal(2, var5);
                        var5 = null;
                        var3 = null;
                        var1.setline(549);
                        var6 = var1.getlocal(8);
                        var6 = var6._iadd(var1.getlocal(1));
                        var1.setlocal(8, var6);
                        var1.setline(550);
                        var6 = var1.getlocal(9);
                        var6 = var6._iadd(var1.getglobal("int").__call__(var2, var1.getlocal(2)));
                        var1.setlocal(9, var6);
                        var1.setline(551);
                        var6 = var1.getglobal("int").__call__(var2, var1.getlocal(3));
                        var1.setlocal(3, var6);
                        var3 = null;
                     } else {
                        var1.setline(553);
                        var6 = var1.getglobal("int").__call__(var2, var1.getlocal(3));
                        var1.setlocal(3, var6);
                        var3 = null;
                        var1.setline(554);
                        var6 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(1000000));
                        var4 = Py.unpackSequence(var6, 2);
                        var5 = var4[0];
                        var1.setlocal(2, var5);
                        var5 = null;
                        var5 = var4[1];
                        var1.setlocal(3, var5);
                        var5 = null;
                        var3 = null;
                        var1.setline(555);
                        var6 = var1.getglobal("divmod").__call__(var2, var1.getlocal(2), Py.newInteger(24)._mul(Py.newInteger(3600)));
                        var4 = Py.unpackSequence(var6, 2);
                        var5 = var4[0];
                        var1.setlocal(1, var5);
                        var5 = null;
                        var5 = var4[1];
                        var1.setlocal(2, var5);
                        var5 = null;
                        var3 = null;
                        var1.setline(556);
                        var6 = var1.getlocal(8);
                        var6 = var6._iadd(var1.getlocal(1));
                        var1.setlocal(8, var6);
                        var1.setline(557);
                        var6 = var1.getlocal(9);
                        var6 = var6._iadd(var1.getglobal("int").__call__(var2, var1.getlocal(2)));
                        var1.setlocal(9, var6);
                        var1.setline(558);
                        var6 = var1.getglobal("_round").__call__(var2, var1.getlocal(3)._add(var1.getlocal(15)));
                        var1.setlocal(3, var6);
                        var3 = null;
                     }

                     var1.setline(559);
                     if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(9), var1.getglobal("int")).__nonzero__()) {
                        var10000 = Py.None;
                        throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                     } else {
                        var1.setline(560);
                        if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("int")).__nonzero__()) {
                           var10000 = Py.None;
                           throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                        } else {
                           var1.setline(561);
                           if (var1.getglobal("__debug__").__nonzero__()) {
                              var6 = var1.getglobal("abs").__call__(var2, var1.getlocal(9));
                              var10000 = var6._le(Py.newInteger(3)._mul(Py.newInteger(24))._mul(Py.newInteger(3600)));
                              var3 = null;
                              if (!var10000.__nonzero__()) {
                                 var10000 = Py.None;
                                 throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                              }
                           }

                           var1.setline(562);
                           if (var1.getglobal("__debug__").__nonzero__()) {
                              var6 = var1.getglobal("abs").__call__(var2, var1.getlocal(3));
                              var10000 = var6._lt(Py.newFloat(3100000.0));
                              var3 = null;
                              if (!var10000.__nonzero__()) {
                                 var10000 = Py.None;
                                 throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                              }
                           }

                           var1.setline(565);
                           var6 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(1000000));
                           var4 = Py.unpackSequence(var6, 2);
                           var5 = var4[0];
                           var1.setlocal(2, var5);
                           var5 = null;
                           var5 = var4[1];
                           var1.setlocal(10, var5);
                           var5 = null;
                           var3 = null;
                           var1.setline(566);
                           var6 = var1.getlocal(9);
                           var6 = var6._iadd(var1.getlocal(2));
                           var1.setlocal(9, var6);
                           var1.setline(567);
                           var6 = var1.getglobal("divmod").__call__(var2, var1.getlocal(9), Py.newInteger(24)._mul(Py.newInteger(3600)));
                           var4 = Py.unpackSequence(var6, 2);
                           var5 = var4[0];
                           var1.setlocal(1, var5);
                           var5 = null;
                           var5 = var4[1];
                           var1.setlocal(9, var5);
                           var5 = null;
                           var3 = null;
                           var1.setline(568);
                           var6 = var1.getlocal(8);
                           var6 = var6._iadd(var1.getlocal(1));
                           var1.setlocal(8, var6);
                           var1.setline(570);
                           if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
                              var10000 = Py.None;
                              throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                           } else {
                              var1.setline(571);
                              PyObject var10001;
                              PyObject var7;
                              PyInteger var9;
                              if (var1.getglobal("__debug__").__nonzero__()) {
                                 var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(9), var1.getglobal("int"));
                                 if (var10000.__nonzero__()) {
                                    var3 = Py.newInteger(0);
                                    var10001 = var1.getlocal(9);
                                    var9 = var3;
                                    var6 = var10001;
                                    if ((var7 = var9._le(var10001)).__nonzero__()) {
                                       var7 = var6._lt(Py.newInteger(24)._mul(Py.newInteger(3600)));
                                    }

                                    var10000 = var7;
                                    var3 = null;
                                 }

                                 if (!var10000.__nonzero__()) {
                                    var10000 = Py.None;
                                    throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                                 }
                              }

                              var1.setline(572);
                              if (var1.getglobal("__debug__").__nonzero__()) {
                                 var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(10), var1.getglobal("int"));
                                 if (var10000.__nonzero__()) {
                                    var3 = Py.newInteger(0);
                                    var10001 = var1.getlocal(10);
                                    var9 = var3;
                                    var6 = var10001;
                                    if ((var7 = var9._le(var10001)).__nonzero__()) {
                                       var7 = var6._lt(Py.newInteger(1000000));
                                    }

                                    var10000 = var7;
                                    var3 = null;
                                 }

                                 if (!var10000.__nonzero__()) {
                                    var10000 = Py.None;
                                    throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                                 }
                              }

                              var1.setline(574);
                              var6 = var1.getglobal("abs").__call__(var2, var1.getlocal(8));
                              var10000 = var6._gt(Py.newInteger(999999999));
                              var3 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(575);
                                 throw Py.makeException(var1.getglobal("OverflowError").__call__(var2, PyString.fromInterned("timedelta # of days is too large: %d")._mod(var1.getlocal(8))));
                              } else {
                                 var1.setline(577);
                                 var6 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getlocal(0));
                                 var1.setlocal(16, var6);
                                 var3 = null;
                                 var1.setline(578);
                                 var6 = var1.getlocal(8);
                                 var1.getlocal(16).__setattr__("_days", var6);
                                 var3 = null;
                                 var1.setline(579);
                                 var6 = var1.getlocal(9);
                                 var1.getlocal(16).__setattr__("_seconds", var6);
                                 var3 = null;
                                 var1.setline(580);
                                 var6 = var1.getlocal(10);
                                 var1.getlocal(16).__setattr__("_microseconds", var6);
                                 var3 = null;
                                 var1.setline(581);
                                 var3 = Py.newInteger(-1);
                                 var1.getlocal(16).__setattr__((String)"_hashcode", var3);
                                 var3 = null;
                                 var1.setline(582);
                                 var6 = var1.getlocal(16);
                                 var1.f_lasti = -1;
                                 return var6;
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

   public PyObject __repr__$26(PyFrame var1, ThreadState var2) {
      var1.setline(585);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("_microseconds").__nonzero__()) {
         var1.setline(586);
         var3 = PyString.fromInterned("%s(%d, %d, %d)")._mod(new PyTuple(new PyObject[]{PyString.fromInterned("datetime.")._add(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__")), var1.getlocal(0).__getattr__("_days"), var1.getlocal(0).__getattr__("_seconds"), var1.getlocal(0).__getattr__("_microseconds")}));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(590);
         if (var1.getlocal(0).__getattr__("_seconds").__nonzero__()) {
            var1.setline(591);
            var3 = PyString.fromInterned("%s(%d, %d)")._mod(new PyTuple(new PyObject[]{PyString.fromInterned("datetime.")._add(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__")), var1.getlocal(0).__getattr__("_days"), var1.getlocal(0).__getattr__("_seconds")}));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(594);
            var3 = PyString.fromInterned("%s(%d)")._mod(new PyTuple(new PyObject[]{PyString.fromInterned("datetime.")._add(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__")), var1.getlocal(0).__getattr__("_days")}));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __str__$27(PyFrame var1, ThreadState var2) {
      var1.setline(597);
      PyObject var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_seconds"), (PyObject)Py.newInteger(60));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(598);
      var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(60));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(599);
      var3 = PyString.fromInterned("%d:%02d:%02d")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(1), var1.getlocal(2)}));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(600);
      if (var1.getlocal(0).__getattr__("_days").__nonzero__()) {
         var1.setline(601);
         PyObject[] var6 = Py.EmptyObjects;
         PyFunction var7 = new PyFunction(var1.f_globals, var6, plural$28, (PyObject)null);
         var1.setlocal(5, var7);
         var3 = null;
         var1.setline(603);
         var3 = PyString.fromInterned("%d day%s, ")._mod(var1.getlocal(5).__call__(var2, var1.getlocal(0).__getattr__("_days")))._add(var1.getlocal(4));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(604);
      if (var1.getlocal(0).__getattr__("_microseconds").__nonzero__()) {
         var1.setline(605);
         var3 = var1.getlocal(4)._add(PyString.fromInterned(".%06d")._mod(var1.getlocal(0).__getattr__("_microseconds")));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(606);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject plural$28(PyFrame var1, ThreadState var2) {
      var1.setline(602);
      PyTuple var10000 = new PyTuple;
      PyObject[] var10002 = new PyObject[]{var1.getlocal(0), null};
      PyObject var3 = var1.getglobal("abs").__call__(var2, var1.getlocal(0));
      Object var10005 = var3._ne(Py.newInteger(1));
      var3 = null;
      if (((PyObject)var10005).__nonzero__()) {
         var10005 = PyString.fromInterned("s");
      }

      if (!((PyObject)var10005).__nonzero__()) {
         var10005 = PyString.fromInterned("");
      }

      var10002[1] = (PyObject)var10005;
      var10000.<init>(var10002);
      PyTuple var4 = var10000;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject total_seconds$29(PyFrame var1, ThreadState var2) {
      var1.setline(609);
      PyString.fromInterned("Total seconds in the duration.");
      var1.setline(610);
      PyObject var3 = var1.getlocal(0).__getattr__("days")._mul(Py.newInteger(86400))._add(var1.getlocal(0).__getattr__("seconds"))._mul(Py.newInteger(10)._pow(Py.newInteger(6)))._add(var1.getlocal(0).__getattr__("microseconds"))._truediv(Py.newInteger(10)._pow(Py.newInteger(6)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject days$30(PyFrame var1, ThreadState var2) {
      var1.setline(616);
      PyString.fromInterned("days");
      var1.setline(617);
      PyObject var3 = var1.getlocal(0).__getattr__("_days");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject seconds$31(PyFrame var1, ThreadState var2) {
      var1.setline(621);
      PyString.fromInterned("seconds");
      var1.setline(622);
      PyObject var3 = var1.getlocal(0).__getattr__("_seconds");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject microseconds$32(PyFrame var1, ThreadState var2) {
      var1.setline(626);
      PyString.fromInterned("microseconds");
      var1.setline(627);
      PyObject var3 = var1.getlocal(0).__getattr__("_microseconds");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __add__$33(PyFrame var1, ThreadState var2) {
      var1.setline(630);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
         var1.setline(633);
         var3 = var1.getglobal("timedelta").__call__(var2, var1.getlocal(0).__getattr__("_days")._add(var1.getlocal(1).__getattr__("_days")), var1.getlocal(0).__getattr__("_seconds")._add(var1.getlocal(1).__getattr__("_seconds")), var1.getlocal(0).__getattr__("_microseconds")._add(var1.getlocal(1).__getattr__("_microseconds")));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(636);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __sub__$34(PyFrame var1, ThreadState var2) {
      var1.setline(641);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
         var1.setline(644);
         var3 = var1.getglobal("timedelta").__call__(var2, var1.getlocal(0).__getattr__("_days")._sub(var1.getlocal(1).__getattr__("_days")), var1.getlocal(0).__getattr__("_seconds")._sub(var1.getlocal(1).__getattr__("_seconds")), var1.getlocal(0).__getattr__("_microseconds")._sub(var1.getlocal(1).__getattr__("_microseconds")));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(647);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __rsub__$35(PyFrame var1, ThreadState var2) {
      var1.setline(650);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
         var1.setline(651);
         var3 = var1.getlocal(0).__neg__()._add(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(652);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __neg__$36(PyFrame var1, ThreadState var2) {
      var1.setline(657);
      PyObject var3 = var1.getglobal("timedelta").__call__(var2, var1.getlocal(0).__getattr__("_days").__neg__(), var1.getlocal(0).__getattr__("_seconds").__neg__(), var1.getlocal(0).__getattr__("_microseconds").__neg__());
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __pos__$37(PyFrame var1, ThreadState var2) {
      var1.setline(662);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __abs__$38(PyFrame var1, ThreadState var2) {
      var1.setline(665);
      PyObject var3 = var1.getlocal(0).__getattr__("_days");
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(666);
         var3 = var1.getlocal(0).__neg__();
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(668);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __mul__$39(PyFrame var1, ThreadState var2) {
      var1.setline(671);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__nonzero__()) {
         var1.setline(674);
         var3 = var1.getglobal("timedelta").__call__(var2, var1.getlocal(0).__getattr__("_days")._mul(var1.getlocal(1)), var1.getlocal(0).__getattr__("_seconds")._mul(var1.getlocal(1)), var1.getlocal(0).__getattr__("_microseconds")._mul(var1.getlocal(1)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(677);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _to_microseconds$40(PyFrame var1, ThreadState var2) {
      var1.setline(682);
      PyObject var3 = var1.getlocal(0).__getattr__("_days")._mul(Py.newInteger(24)._mul(Py.newInteger(3600)))._add(var1.getlocal(0).__getattr__("_seconds"))._mul(Py.newInteger(1000000))._add(var1.getlocal(0).__getattr__("_microseconds"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __div__$41(PyFrame var1, ThreadState var2) {
      var1.setline(686);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")}))).__not__().__nonzero__()) {
         var1.setline(687);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(688);
         PyObject var4 = var1.getlocal(0).__getattr__("_to_microseconds").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(689);
         var3 = var1.getglobal("timedelta").__call__((ThreadState)var2, Py.newInteger(0), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(2)._floordiv(var1.getlocal(1)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __eq__$42(PyFrame var1, ThreadState var2) {
      var1.setline(696);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
         var1.setline(697);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(699);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$43(PyFrame var1, ThreadState var2) {
      var1.setline(702);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
         var1.setline(703);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(705);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __le__$44(PyFrame var1, ThreadState var2) {
      var1.setline(708);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
         var1.setline(709);
         PyObject var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._le(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(711);
         var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __lt__$45(PyFrame var1, ThreadState var2) {
      var1.setline(714);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
         var1.setline(715);
         PyObject var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(717);
         var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __ge__$46(PyFrame var1, ThreadState var2) {
      var1.setline(720);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
         var1.setline(721);
         PyObject var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(723);
         var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __gt__$47(PyFrame var1, ThreadState var2) {
      var1.setline(726);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
         var1.setline(727);
         PyObject var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(729);
         var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _cmp$48(PyFrame var1, ThreadState var2) {
      var1.setline(732);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
         PyObject var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(733);
         PyObject var3 = var1.getglobal("_cmp").__call__(var2, var1.getlocal(0).__getattr__("_getstate").__call__(var2), var1.getlocal(1).__getattr__("_getstate").__call__(var2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __hash__$49(PyFrame var1, ThreadState var2) {
      var1.setline(736);
      PyObject var3 = var1.getlocal(0).__getattr__("_hashcode");
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(737);
         var3 = var1.getglobal("hash").__call__(var2, var1.getlocal(0).__getattr__("_getstate").__call__(var2));
         var1.getlocal(0).__setattr__("_hashcode", var3);
         var3 = null;
      }

      var1.setline(738);
      var3 = var1.getlocal(0).__getattr__("_hashcode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __nonzero__$50(PyFrame var1, ThreadState var2) {
      var1.setline(741);
      PyObject var3 = var1.getlocal(0).__getattr__("_days");
      PyObject var10000 = var3._ne(Py.newInteger(0));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_seconds");
         var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("_microseconds");
            var10000 = var3._ne(Py.newInteger(0));
            var3 = null;
         }
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _getstate$51(PyFrame var1, ThreadState var2) {
      var1.setline(748);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_days"), var1.getlocal(0).__getattr__("_seconds"), var1.getlocal(0).__getattr__("_microseconds")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __reduce__$52(PyFrame var1, ThreadState var2) {
      var1.setline(751);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), var1.getlocal(0).__getattr__("_getstate").__call__(var2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject date$53(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Concrete date type.\n\n    Constructors:\n\n    __new__()\n    fromtimestamp()\n    today()\n    fromordinal()\n\n    Operators:\n\n    __repr__, __str__\n    __cmp__, __hash__\n    __add__, __radd__, __sub__ (add/radd only with timedelta arg)\n\n    Methods:\n\n    timetuple()\n    toordinal()\n    weekday()\n    isoweekday(), isocalendar(), isoformat()\n    ctime()\n    strftime()\n\n    Properties (readonly):\n    year, month, day\n    "));
      var1.setline(785);
      PyString.fromInterned("Concrete date type.\n\n    Constructors:\n\n    __new__()\n    fromtimestamp()\n    today()\n    fromordinal()\n\n    Operators:\n\n    __repr__, __str__\n    __cmp__, __hash__\n    __add__, __radd__, __sub__ (add/radd only with timedelta arg)\n\n    Methods:\n\n    timetuple()\n    toordinal()\n    weekday()\n    isoweekday(), isocalendar(), isoformat()\n    ctime()\n    strftime()\n\n    Properties (readonly):\n    year, month, day\n    ");
      var1.setline(786);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("_year"), PyString.fromInterned("_month"), PyString.fromInterned("_day"), PyString.fromInterned("_hashcode")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(788);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$54, PyString.fromInterned("Constructor.\n\n        Arguments:\n\n        year, month, day (required, base 1)\n        "));
      var1.setlocal("__new__", var5);
      var3 = null;
      var1.setline(812);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, fromtimestamp$55, PyString.fromInterned("Construct a date from a POSIX timestamp (like time.time())."));
      PyObject var6 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("fromtimestamp", var6);
      var3 = null;
      var1.setline(818);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, today$56, PyString.fromInterned("Construct a date from time.time()."));
      var6 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("today", var6);
      var3 = null;
      var1.setline(824);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, fromordinal$57, PyString.fromInterned("Contruct a date from a proleptic Gregorian ordinal.\n\n        January 1 of year 1 is day 1.  Only the year, month and day are\n        non-zero in the result.\n        "));
      var6 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("fromordinal", var6);
      var3 = null;
      var1.setline(836);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$58, PyString.fromInterned("Convert to formal string, for repr().\n\n        >>> dt = datetime(2010, 1, 1)\n        >>> repr(dt)\n        'datetime.datetime(2010, 1, 1, 0, 0)'\n\n        >>> dt = datetime(2010, 1, 1, tzinfo=timezone.utc)\n        >>> repr(dt)\n        'datetime.datetime(2010, 1, 1, 0, 0, tzinfo=datetime.timezone.utc)'\n        "));
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(857);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, ctime$59, PyString.fromInterned("Return ctime() style string."));
      var1.setlocal("ctime", var5);
      var3 = null;
      var1.setline(865);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, strftime$60, PyString.fromInterned("Format using strftime()."));
      var1.setlocal("strftime", var5);
      var3 = null;
      var1.setline(869);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __format__$61, (PyObject)null);
      var1.setlocal("__format__", var5);
      var3 = null;
      var1.setline(877);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isoformat$62, PyString.fromInterned("Return the date formatted according to ISO.\n\n        This is 'YYYY-MM-DD'.\n\n        References:\n        - http://www.w3.org/TR/NOTE-datetime\n        - http://www.cl.cam.ac.uk/~mgk25/iso-time.html\n        "));
      var1.setlocal("isoformat", var5);
      var3 = null;
      var1.setline(888);
      var6 = var1.getname("isoformat");
      var1.setlocal("__str__", var6);
      var3 = null;
      var1.setline(891);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, year$63, PyString.fromInterned("year (1-9999)"));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("year", var6);
      var3 = null;
      var1.setline(896);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, month$64, PyString.fromInterned("month (1-12)"));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("month", var6);
      var3 = null;
      var1.setline(901);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, day$65, PyString.fromInterned("day (1-31)"));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("day", var6);
      var3 = null;
      var1.setline(908);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, timetuple$66, PyString.fromInterned("Return local time tuple compatible with time.localtime()."));
      var1.setlocal("timetuple", var5);
      var3 = null;
      var1.setline(913);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, toordinal$67, PyString.fromInterned("Return proleptic Gregorian ordinal for the year, month and day.\n\n        January 1 of year 1 is day 1.  Only the year, month and day values\n        contribute to the result.\n        "));
      var1.setlocal("toordinal", var5);
      var3 = null;
      var1.setline(921);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, replace$68, PyString.fromInterned("Return a new date with new values for the specified fields."));
      var1.setlocal("replace", var5);
      var3 = null;
      var1.setline(933);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __eq__$69, (PyObject)null);
      var1.setlocal("__eq__", var5);
      var3 = null;
      var1.setline(941);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ne__$70, (PyObject)null);
      var1.setlocal("__ne__", var5);
      var3 = null;
      var1.setline(949);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __le__$71, (PyObject)null);
      var1.setlocal("__le__", var5);
      var3 = null;
      var1.setline(957);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __lt__$72, (PyObject)null);
      var1.setlocal("__lt__", var5);
      var3 = null;
      var1.setline(965);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ge__$73, (PyObject)null);
      var1.setlocal("__ge__", var5);
      var3 = null;
      var1.setline(973);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __gt__$74, (PyObject)null);
      var1.setlocal("__gt__", var5);
      var3 = null;
      var1.setline(981);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _cmp$75, (PyObject)null);
      var1.setlocal("_cmp", var5);
      var3 = null;
      var1.setline(987);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __hash__$76, PyString.fromInterned("Hash."));
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(995);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _checkOverflow$77, (PyObject)null);
      var1.setlocal("_checkOverflow", var5);
      var3 = null;
      var1.setline(1000);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __add__$78, PyString.fromInterned("Add a date to a timedelta."));
      var1.setlocal("__add__", var5);
      var3 = null;
      var1.setline(1011);
      var6 = var1.getname("__add__");
      var1.setlocal("__radd__", var6);
      var3 = null;
      var1.setline(1013);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __sub__$79, PyString.fromInterned("Subtract two dates, or a date and a timedelta."));
      var1.setlocal("__sub__", var5);
      var3 = null;
      var1.setline(1023);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, weekday$80, PyString.fromInterned("Return day of the week, where Monday == 0 ... Sunday == 6."));
      var1.setlocal("weekday", var5);
      var3 = null;
      var1.setline(1029);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isoweekday$81, PyString.fromInterned("Return day of the week, where Monday == 1 ... Sunday == 7."));
      var1.setlocal("isoweekday", var5);
      var3 = null;
      var1.setline(1034);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isocalendar$82, PyString.fromInterned("Return a 3-tuple containing ISO year, week number, and weekday.\n\n        The first ISO week of the year is the (Mon-Sun) week\n        containing the year's first Thursday; everything else derives\n        from that.\n\n        The first week is 1; Monday is 1 ... Sunday is 7.\n\n        ISO calendar algorithm taken from\n        http://www.phys.uu.nl/~vgent/calendar/isocalendar.htm\n        "));
      var1.setlocal("isocalendar", var5);
      var3 = null;
      var1.setline(1063);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _getstate$83, (PyObject)null);
      var1.setlocal("_getstate", var5);
      var3 = null;
      var1.setline(1067);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _date__setstate$84, (PyObject)null);
      var1.setlocal("_date__setstate", var5);
      var3 = null;
      var1.setline(1072);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __reduce__$85, (PyObject)null);
      var1.setlocal("__reduce__", var5);
      var3 = null;
      var1.setline(1075);
      if (var1.getname("_is_jython").__nonzero__()) {
         var1.setline(1076);
         var4 = Py.EmptyObjects;
         var5 = new PyFunction(var1.f_globals, var4, __tojava__$86, (PyObject)null);
         var1.setlocal("__tojava__", var5);
         var3 = null;
      }

      return var1.getf_locals();
   }

   public PyObject __new__$54(PyFrame var1, ThreadState var2) {
      var1.setline(794);
      PyString.fromInterned("Constructor.\n\n        Arguments:\n\n        year, month, day (required, base 1)\n        ");
      var1.setline(795);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyObject var4;
      PyInteger var7;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("bytes"));
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
            var10000 = var3._eq(Py.newInteger(4));
            var3 = null;
            if (var10000.__nonzero__()) {
               var7 = Py.newInteger(1);
               PyObject var10001 = var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2)));
               PyInteger var9 = var7;
               var3 = var10001;
               if ((var4 = var9._le(var10001)).__nonzero__()) {
                  var4 = var3._le(Py.newInteger(12));
               }

               var10000 = var4;
               var3 = null;
            }
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(798);
         var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getlocal(0));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(799);
         var1.getlocal(4).__getattr__("_date__setstate").__call__(var2, var1.getlocal(1));
         var1.setline(800);
         var7 = Py.newInteger(-1);
         var1.getlocal(4).__setattr__((String)"_hashcode", var7);
         var3 = null;
         var1.setline(801);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(802);
         var4 = var1.getglobal("_check_date_fields").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(3, var6);
         var6 = null;
         var4 = null;
         var1.setline(803);
         var4 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getlocal(0));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(804);
         var4 = var1.getlocal(1);
         var1.getlocal(4).__setattr__("_year", var4);
         var4 = null;
         var1.setline(805);
         var4 = var1.getlocal(2);
         var1.getlocal(4).__setattr__("_month", var4);
         var4 = null;
         var1.setline(806);
         var4 = var1.getlocal(3);
         var1.getlocal(4).__setattr__("_day", var4);
         var4 = null;
         var1.setline(807);
         PyInteger var8 = Py.newInteger(-1);
         var1.getlocal(4).__setattr__((String)"_hashcode", var8);
         var4 = null;
         var1.setline(808);
         var3 = var1.getlocal(4);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject fromtimestamp$55(PyFrame var1, ThreadState var2) {
      var1.setline(814);
      PyString.fromInterned("Construct a date from a POSIX timestamp (like time.time()).");
      var1.setline(815);
      PyObject var3 = var1.getglobal("_time").__getattr__("localtime").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 9);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[6];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[7];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[8];
      var1.setlocal(10, var5);
      var5 = null;
      var3 = null;
      var1.setline(816);
      var3 = var1.getlocal(0).__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject today$56(PyFrame var1, ThreadState var2) {
      var1.setline(820);
      PyString.fromInterned("Construct a date from time.time().");
      var1.setline(821);
      PyObject var3 = var1.getglobal("_time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(822);
      var3 = var1.getlocal(0).__getattr__("fromtimestamp").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fromordinal$57(PyFrame var1, ThreadState var2) {
      var1.setline(830);
      PyString.fromInterned("Contruct a date from a proleptic Gregorian ordinal.\n\n        January 1 of year 1 is day 1.  Only the year, month and day are\n        non-zero in the result.\n        ");
      var1.setline(831);
      PyObject var3 = var1.getglobal("_ord2ymd").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(832);
      var3 = var1.getlocal(0).__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$58(PyFrame var1, ThreadState var2) {
      var1.setline(846);
      PyString.fromInterned("Convert to formal string, for repr().\n\n        >>> dt = datetime(2010, 1, 1)\n        >>> repr(dt)\n        'datetime.datetime(2010, 1, 1, 0, 0)'\n\n        >>> dt = datetime(2010, 1, 1, tzinfo=timezone.utc)\n        >>> repr(dt)\n        'datetime.datetime(2010, 1, 1, 0, 0, tzinfo=datetime.timezone.utc)'\n        ");
      var1.setline(847);
      PyObject var3 = PyString.fromInterned("%s(%d, %d, %d)")._mod(new PyTuple(new PyObject[]{PyString.fromInterned("datetime.")._add(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__")), var1.getlocal(0).__getattr__("_year"), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ctime$59(PyFrame var1, ThreadState var2) {
      var1.setline(858);
      PyString.fromInterned("Return ctime() style string.");
      var1.setline(859);
      Object var10000 = var1.getlocal(0).__getattr__("toordinal").__call__(var2)._mod(Py.newInteger(7));
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = Py.newInteger(7);
      }

      Object var3 = var10000;
      var1.setlocal(1, (PyObject)var3);
      var3 = null;
      var1.setline(860);
      PyObject var4 = PyString.fromInterned("%s %s %2d 00:00:00 %04d")._mod(new PyTuple(new PyObject[]{var1.getglobal("_DAYNAMES").__getitem__(var1.getlocal(1)), var1.getglobal("_MONTHNAMES").__getitem__(var1.getlocal(0).__getattr__("_month")), var1.getlocal(0).__getattr__("_day"), var1.getlocal(0).__getattr__("_year")}));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject strftime$60(PyFrame var1, ThreadState var2) {
      var1.setline(866);
      PyString.fromInterned("Format using strftime().");
      var1.setline(867);
      PyObject var3 = var1.getglobal("_wrap_strftime").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(0).__getattr__("timetuple").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __format__$61(PyFrame var1, ThreadState var2) {
      var1.setline(870);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("str"), var1.getglobal("unicode")}))).__not__().__nonzero__()) {
         var1.setline(871);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("__format__ expects str or unicode, not %s")._mod(var1.getlocal(1).__getattr__("__class__").__getattr__("__name__"))));
      } else {
         var1.setline(873);
         PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(874);
            var3 = var1.getlocal(0).__getattr__("strftime").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(875);
            var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject isoformat$62(PyFrame var1, ThreadState var2) {
      var1.setline(885);
      PyString.fromInterned("Return the date formatted according to ISO.\n\n        This is 'YYYY-MM-DD'.\n\n        References:\n        - http://www.w3.org/TR/NOTE-datetime\n        - http://www.cl.cam.ac.uk/~mgk25/iso-time.html\n        ");
      var1.setline(886);
      PyObject var3 = PyString.fromInterned("%04d-%02d-%02d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_year"), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject year$63(PyFrame var1, ThreadState var2) {
      var1.setline(893);
      PyString.fromInterned("year (1-9999)");
      var1.setline(894);
      PyObject var3 = var1.getlocal(0).__getattr__("_year");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject month$64(PyFrame var1, ThreadState var2) {
      var1.setline(898);
      PyString.fromInterned("month (1-12)");
      var1.setline(899);
      PyObject var3 = var1.getlocal(0).__getattr__("_month");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject day$65(PyFrame var1, ThreadState var2) {
      var1.setline(903);
      PyString.fromInterned("day (1-31)");
      var1.setline(904);
      PyObject var3 = var1.getlocal(0).__getattr__("_day");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject timetuple$66(PyFrame var1, ThreadState var2) {
      var1.setline(909);
      PyString.fromInterned("Return local time tuple compatible with time.localtime().");
      var1.setline(910);
      PyObject var10000 = var1.getglobal("_build_struct_time");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("_year"), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day"), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(-1)};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject toordinal$67(PyFrame var1, ThreadState var2) {
      var1.setline(918);
      PyString.fromInterned("Return proleptic Gregorian ordinal for the year, month and day.\n\n        January 1 of year 1 is day 1.  Only the year, month and day values\n        contribute to the result.\n        ");
      var1.setline(919);
      PyObject var3 = var1.getglobal("_ymd2ord").__call__(var2, var1.getlocal(0).__getattr__("_year"), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject replace$68(PyFrame var1, ThreadState var2) {
      var1.setline(922);
      PyString.fromInterned("Return a new date with new values for the specified fields.");
      var1.setline(923);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(924);
         var3 = var1.getlocal(0).__getattr__("_year");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(925);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(926);
         var3 = var1.getlocal(0).__getattr__("_month");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(927);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(928);
         var3 = var1.getlocal(0).__getattr__("_day");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(929);
      var3 = var1.getglobal("date").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$69(PyFrame var1, ThreadState var2) {
      var1.setline(934);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__nonzero__()) {
         var1.setline(935);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(936);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple")).__nonzero__()) {
            var1.setline(937);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(939);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __ne__$70(PyFrame var1, ThreadState var2) {
      var1.setline(942);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__nonzero__()) {
         var1.setline(943);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(944);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple")).__nonzero__()) {
            var1.setline(945);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(947);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __le__$71(PyFrame var1, ThreadState var2) {
      var1.setline(950);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__nonzero__()) {
         var1.setline(951);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._le(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(952);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple")).__nonzero__()) {
            var1.setline(953);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(955);
            var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __lt__$72(PyFrame var1, ThreadState var2) {
      var1.setline(958);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__nonzero__()) {
         var1.setline(959);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(960);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple")).__nonzero__()) {
            var1.setline(961);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(963);
            var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __ge__$73(PyFrame var1, ThreadState var2) {
      var1.setline(966);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__nonzero__()) {
         var1.setline(967);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(968);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple")).__nonzero__()) {
            var1.setline(969);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(971);
            var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __gt__$74(PyFrame var1, ThreadState var2) {
      var1.setline(974);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__nonzero__()) {
         var1.setline(975);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(976);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple")).__nonzero__()) {
            var1.setline(977);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(979);
            var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _cmp$75(PyFrame var1, ThreadState var2) {
      var1.setline(982);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__nonzero__()) {
         PyObject var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(983);
         PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_year"), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day")});
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(984);
         var3 = new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("_year"), var1.getlocal(1).__getattr__("_month"), var1.getlocal(1).__getattr__("_day")});
         var4 = Py.unpackSequence(var3, 3);
         var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
         var1.setline(985);
         PyObject var6 = var1.getglobal("_cmp").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)})), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)})));
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject __hash__$76(PyFrame var1, ThreadState var2) {
      var1.setline(988);
      PyString.fromInterned("Hash.");
      var1.setline(989);
      PyObject var3 = var1.getlocal(0).__getattr__("_hashcode");
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(990);
         var3 = var1.getglobal("hash").__call__(var2, var1.getlocal(0).__getattr__("_getstate").__call__(var2));
         var1.getlocal(0).__setattr__("_hashcode", var3);
         var3 = null;
      }

      var1.setline(991);
      var3 = var1.getlocal(0).__getattr__("_hashcode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _checkOverflow$77(PyFrame var1, ThreadState var2) {
      var1.setline(996);
      PyObject var3 = var1.getglobal("MINYEAR");
      PyObject var10001 = var1.getlocal(1);
      PyObject var10000 = var3;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._le(var10001)).__nonzero__()) {
         var4 = var3._le(var1.getglobal("MAXYEAR"));
      }

      var3 = null;
      if (var4.__not__().__nonzero__()) {
         var1.setline(997);
         throw Py.makeException(var1.getglobal("OverflowError").__call__(var2, PyString.fromInterned("date +/-: result year %d not in %d..%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("MINYEAR"), var1.getglobal("MAXYEAR")}))));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __add__$78(PyFrame var1, ThreadState var2) {
      var1.setline(1001);
      PyString.fromInterned("Add a date to a timedelta.");
      var1.setline(1002);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
         var1.setline(1003);
         var3 = var1.getglobal("_tmxxx").__call__(var2, var1.getlocal(0).__getattr__("_year"), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day")._add(var1.getlocal(1).__getattr__("days")));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1006);
         var1.getlocal(0).__getattr__("_checkOverflow").__call__(var2, var1.getlocal(2).__getattr__("year"));
         var1.setline(1007);
         var3 = var1.getglobal("date").__call__(var2, var1.getlocal(2).__getattr__("year"), var1.getlocal(2).__getattr__("month"), var1.getlocal(2).__getattr__("day"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1008);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1009);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __sub__$79(PyFrame var1, ThreadState var2) {
      var1.setline(1014);
      PyString.fromInterned("Subtract two dates, or a date and a timedelta.");
      var1.setline(1015);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
         var1.setline(1016);
         var3 = var1.getlocal(0)._add(var1.getglobal("timedelta").__call__(var2, var1.getlocal(1).__getattr__("days").__neg__()));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1017);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__nonzero__()) {
            var1.setline(1018);
            PyObject var4 = var1.getlocal(0).__getattr__("toordinal").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(1019);
            var4 = var1.getlocal(1).__getattr__("toordinal").__call__(var2);
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(1020);
            var3 = var1.getglobal("timedelta").__call__(var2, var1.getlocal(2)._sub(var1.getlocal(3)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1021);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject weekday$80(PyFrame var1, ThreadState var2) {
      var1.setline(1024);
      PyString.fromInterned("Return day of the week, where Monday == 0 ... Sunday == 6.");
      var1.setline(1025);
      PyObject var3 = var1.getlocal(0).__getattr__("toordinal").__call__(var2)._add(Py.newInteger(6))._mod(Py.newInteger(7));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isoweekday$81(PyFrame var1, ThreadState var2) {
      var1.setline(1030);
      PyString.fromInterned("Return day of the week, where Monday == 1 ... Sunday == 7.");
      var1.setline(1032);
      Object var10000 = var1.getlocal(0).__getattr__("toordinal").__call__(var2)._mod(Py.newInteger(7));
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = Py.newInteger(7);
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject isocalendar$82(PyFrame var1, ThreadState var2) {
      var1.setline(1045);
      PyString.fromInterned("Return a 3-tuple containing ISO year, week number, and weekday.\n\n        The first ISO week of the year is the (Mon-Sun) week\n        containing the year's first Thursday; everything else derives\n        from that.\n\n        The first week is 1; Monday is 1 ... Sunday is 7.\n\n        ISO calendar algorithm taken from\n        http://www.phys.uu.nl/~vgent/calendar/isocalendar.htm\n        ");
      var1.setline(1046);
      PyObject var3 = var1.getlocal(0).__getattr__("_year");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1047);
      var3 = var1.getglobal("_isoweek1monday").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1048);
      var3 = var1.getglobal("_ymd2ord").__call__(var2, var1.getlocal(0).__getattr__("_year"), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1050);
      var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(3)._sub(var1.getlocal(2)), (PyObject)Py.newInteger(7));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(1051);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._lt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1052);
         var3 = var1.getlocal(1);
         var3 = var3._isub(Py.newInteger(1));
         var1.setlocal(1, var3);
         var1.setline(1053);
         var3 = var1.getglobal("_isoweek1monday").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1054);
         var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(3)._sub(var1.getlocal(2)), (PyObject)Py.newInteger(7));
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
      } else {
         var1.setline(1055);
         var3 = var1.getlocal(4);
         var10000 = var3._ge(Py.newInteger(52));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1056);
            var3 = var1.getlocal(3);
            var10000 = var3._ge(var1.getglobal("_isoweek1monday").__call__(var2, var1.getlocal(1)._add(Py.newInteger(1))));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1057);
               var3 = var1.getlocal(1);
               var3 = var3._iadd(Py.newInteger(1));
               var1.setlocal(1, var3);
               var1.setline(1058);
               PyInteger var6 = Py.newInteger(0);
               var1.setlocal(4, var6);
               var3 = null;
            }
         }
      }

      var1.setline(1059);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(4)._add(Py.newInteger(1)), var1.getlocal(5)._add(Py.newInteger(1))});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject _getstate$83(PyFrame var1, ThreadState var2) {
      var1.setline(1064);
      PyObject var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_year"), (PyObject)Py.newInteger(256));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1065);
      PyObject[] var10002 = new PyObject[1];
      PyObject var10005 = var1.getglobal("_struct").__getattr__("pack");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("4B"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day")};
      var10002[0] = var10005.__call__(var2, var6);
      PyTuple var7 = new PyTuple(var10002);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject _date__setstate$84(PyFrame var1, ThreadState var2) {
      var1.setline(1068);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(3)))});
      PyObject[] var4 = Py.unpackSequence(var3, 4);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.getlocal(0).__setattr__("_month", var5);
      var5 = null;
      var5 = var4[3];
      var1.getlocal(0).__setattr__("_day", var5);
      var5 = null;
      var3 = null;
      var1.setline(1070);
      PyObject var6 = var1.getlocal(2)._mul(Py.newInteger(256))._add(var1.getlocal(3));
      var1.getlocal(0).__setattr__("_year", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __reduce__$85(PyFrame var1, ThreadState var2) {
      var1.setline(1073);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), var1.getlocal(0).__getattr__("_getstate").__call__(var2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __tojava__$86(PyFrame var1, ThreadState var2) {
      var1.setline(1077);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("Calendar"), var1.getglobal("Date"), var1.getglobal("Object")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1078);
         var3 = var1.getglobal("Py").__getattr__("NoConversion");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1079);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._eq(var1.getglobal("Calendar"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1080);
            var4 = var1.getglobal("_make_java_utc_calendar").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(1081);
            var1.getlocal(2).__getattr__("set").__call__(var2, var1.getlocal(0).__getattr__("year"), var1.getlocal(0).__getattr__("month")._sub(Py.newInteger(1)), var1.getlocal(0).__getattr__("day"));
            var1.setline(1082);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1084);
            var4 = var1.getglobal("_make_java_default_calendar").__call__(var2);
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(1085);
            var1.getlocal(2).__getattr__("set").__call__(var2, var1.getlocal(0).__getattr__("year"), var1.getlocal(0).__getattr__("month")._sub(Py.newInteger(1)), var1.getlocal(0).__getattr__("day"));
            var1.setline(1086);
            var3 = var1.getglobal("Date").__call__(var2, var1.getlocal(2).__getattr__("getTimeInMillis").__call__(var2));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject tzinfo$87(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Abstract base class for time zone info classes.\n\n    Subclasses must override the name(), utcoffset() and dst() methods.\n    "));
      var1.setline(1099);
      PyString.fromInterned("Abstract base class for time zone info classes.\n\n    Subclasses must override the name(), utcoffset() and dst() methods.\n    ");
      var1.setline(1100);
      PyTuple var3 = new PyTuple(Py.EmptyObjects);
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(1102);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, tzname$88, PyString.fromInterned("datetime -> string name of time zone."));
      var1.setlocal("tzname", var5);
      var3 = null;
      var1.setline(1106);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, utcoffset$89, PyString.fromInterned("datetime -> minutes east of UTC (negative for west of UTC)"));
      var1.setlocal("utcoffset", var5);
      var3 = null;
      var1.setline(1110);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, dst$90, PyString.fromInterned("datetime -> DST offset in minutes east of UTC.\n\n        Return 0 if DST not in effect.  utcoffset() must include the DST\n        offset.\n        "));
      var1.setlocal("dst", var5);
      var3 = null;
      var1.setline(1118);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, fromutc$91, PyString.fromInterned("datetime in UTC -> datetime in local time."));
      var1.setlocal("fromutc", var5);
      var3 = null;
      var1.setline(1150);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __reduce__$92, (PyObject)null);
      var1.setlocal("__reduce__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tzname$88(PyFrame var1, ThreadState var2) {
      var1.setline(1103);
      PyString.fromInterned("datetime -> string name of time zone.");
      var1.setline(1104);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tzinfo subclass must override tzname()")));
   }

   public PyObject utcoffset$89(PyFrame var1, ThreadState var2) {
      var1.setline(1107);
      PyString.fromInterned("datetime -> minutes east of UTC (negative for west of UTC)");
      var1.setline(1108);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tzinfo subclass must override utcoffset()")));
   }

   public PyObject dst$90(PyFrame var1, ThreadState var2) {
      var1.setline(1115);
      PyString.fromInterned("datetime -> DST offset in minutes east of UTC.\n\n        Return 0 if DST not in effect.  utcoffset() must include the DST\n        offset.\n        ");
      var1.setline(1116);
      throw Py.makeException(var1.getglobal("NotImplementedError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tzinfo subclass must override dst()")));
   }

   public PyObject fromutc$91(PyFrame var1, ThreadState var2) {
      var1.setline(1119);
      PyString.fromInterned("datetime in UTC -> datetime in local time.");
      var1.setline(1121);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("datetime")).__not__().__nonzero__()) {
         var1.setline(1122);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fromutc() requires a datetime argument")));
      } else {
         var1.setline(1123);
         PyObject var3 = var1.getlocal(1).__getattr__("tzinfo");
         PyObject var10000 = var3._isnot(var1.getlocal(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1124);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dt.tzinfo is not self")));
         } else {
            var1.setline(1126);
            var3 = var1.getlocal(1).__getattr__("utcoffset").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1127);
            var3 = var1.getlocal(2);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1128);
               throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fromutc() requires a non-None utcoffset() result")));
            } else {
               var1.setline(1133);
               var3 = var1.getlocal(1).__getattr__("dst").__call__(var2);
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(1134);
               var3 = var1.getlocal(3);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1135);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fromutc() requires a non-None dst() result")));
               } else {
                  var1.setline(1136);
                  var3 = var1.getlocal(2)._sub(var1.getlocal(3));
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(1137);
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(1138);
                     var3 = var1.getlocal(1);
                     var3 = var3._iadd(var1.getlocal(4));
                     var1.setlocal(1, var3);
                     var1.setline(1139);
                     var3 = var1.getlocal(1).__getattr__("dst").__call__(var2);
                     var1.setlocal(3, var3);
                     var3 = null;
                     var1.setline(1140);
                     var3 = var1.getlocal(3);
                     var10000 = var3._is(var1.getglobal("None"));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1141);
                        throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("fromutc(): dt.dst gave inconsistent results; cannot convert")));
                     }
                  }

                  var1.setline(1143);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(1144);
                     var3 = var1.getlocal(1)._add(var1.getlocal(3));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(1146);
                     var3 = var1.getlocal(1);
                     var1.f_lasti = -1;
                     return var3;
                  }
               }
            }
         }
      }
   }

   public PyObject __reduce__$92(PyFrame var1, ThreadState var2) {
      var1.setline(1151);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__getinitargs__"), (PyObject)var1.getglobal("None"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1152);
      PyTuple var4;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(1153);
         var3 = var1.getlocal(1).__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(1155);
         var4 = new PyTuple(Py.EmptyObjects);
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(1156);
      var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__getstate__"), (PyObject)var1.getglobal("None"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1157);
      PyObject var10000;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(1158);
         var3 = var1.getlocal(3).__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(1160);
         var10000 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__dict__"), (PyObject)var1.getglobal("None"));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("None");
         }

         var3 = var10000;
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(1161);
      var3 = var1.getlocal(4);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1162);
         var4 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(1164);
         var4 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), var1.getlocal(2), var1.getlocal(4)});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject time$93(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Time with time zone.\n\n    Constructors:\n\n    __new__()\n\n    Operators:\n\n    __repr__, __str__\n    __cmp__, __hash__\n\n    Methods:\n\n    strftime()\n    isoformat()\n    utcoffset()\n    tzname()\n    dst()\n\n    Properties (readonly):\n    hour, minute, second, microsecond, tzinfo\n    "));
      var1.setline(1190);
      PyString.fromInterned("Time with time zone.\n\n    Constructors:\n\n    __new__()\n\n    Operators:\n\n    __repr__, __str__\n    __cmp__, __hash__\n\n    Methods:\n\n    strftime()\n    isoformat()\n    utcoffset()\n    tzname()\n    dst()\n\n    Properties (readonly):\n    hour, minute, second, microsecond, tzinfo\n    ");
      var1.setline(1191);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned("_hour"), PyString.fromInterned("_minute"), PyString.fromInterned("_second"), PyString.fromInterned("_microsecond"), PyString.fromInterned("_tzinfo"), PyString.fromInterned("_hashcode")});
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(1193);
      PyObject[] var4 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$94, PyString.fromInterned("Constructor.\n\n        Arguments:\n\n        hour, minute (required)\n        second, microsecond (default to zero)\n        tzinfo (default to None)\n        "));
      var1.setlocal("__new__", var5);
      var3 = null;
      var1.setline(1221);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, hour$95, PyString.fromInterned("hour (0-23)"));
      PyObject var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("hour", var6);
      var3 = null;
      var1.setline(1226);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, minute$96, PyString.fromInterned("minute (0-59)"));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("minute", var6);
      var3 = null;
      var1.setline(1231);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, second$97, PyString.fromInterned("second (0-59)"));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("second", var6);
      var3 = null;
      var1.setline(1236);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, microsecond$98, PyString.fromInterned("microsecond (0-999999)"));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("microsecond", var6);
      var3 = null;
      var1.setline(1241);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tzinfo$99, PyString.fromInterned("timezone info object"));
      var6 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tzinfo", var6);
      var3 = null;
      var1.setline(1250);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __eq__$100, (PyObject)null);
      var1.setlocal("__eq__", var5);
      var3 = null;
      var1.setline(1256);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ne__$101, (PyObject)null);
      var1.setlocal("__ne__", var5);
      var3 = null;
      var1.setline(1262);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __le__$102, (PyObject)null);
      var1.setlocal("__le__", var5);
      var3 = null;
      var1.setline(1268);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __lt__$103, (PyObject)null);
      var1.setlocal("__lt__", var5);
      var3 = null;
      var1.setline(1274);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ge__$104, (PyObject)null);
      var1.setlocal("__ge__", var5);
      var3 = null;
      var1.setline(1280);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __gt__$105, (PyObject)null);
      var1.setlocal("__gt__", var5);
      var3 = null;
      var1.setline(1286);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _cmp$106, (PyObject)null);
      var1.setlocal("_cmp", var5);
      var3 = null;
      var1.setline(1311);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __hash__$107, PyString.fromInterned("Hash."));
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(1327);
      var4 = new PyObject[]{PyString.fromInterned(":")};
      var5 = new PyFunction(var1.f_globals, var4, _tzstr$108, PyString.fromInterned("Return formatted timezone offset (+xx:xx) or None."));
      var1.setlocal("_tzstr", var5);
      var3 = null;
      var1.setline(1341);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$109, PyString.fromInterned("Convert to formal string, for repr()."));
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(1356);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, isoformat$110, PyString.fromInterned("Return the time formatted according to ISO.\n\n        This is 'HH:MM:SS.mmmmmm+zz:zz', or 'HH:MM:SS+zz:zz' if\n        self.microsecond == 0.\n        "));
      var1.setlocal("isoformat", var5);
      var3 = null;
      var1.setline(1369);
      var6 = var1.getname("isoformat");
      var1.setlocal("__str__", var6);
      var3 = null;
      var1.setline(1371);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, strftime$111, PyString.fromInterned("Format using strftime().  The date part of the timestamp passed\n        to underlying strftime should not be used.\n        "));
      var1.setlocal("strftime", var5);
      var3 = null;
      var1.setline(1382);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __format__$112, (PyObject)null);
      var1.setlocal("__format__", var5);
      var3 = null;
      var1.setline(1392);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, utcoffset$113, PyString.fromInterned("Return the timezone offset in minutes east of UTC (negative west of\n        UTC)."));
      var1.setlocal("utcoffset", var5);
      var3 = null;
      var1.setline(1404);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _utcoffset$114, (PyObject)null);
      var1.setlocal("_utcoffset", var5);
      var3 = null;
      var1.setline(1411);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tzname$115, PyString.fromInterned("Return the timezone name.\n\n        Note that the name is 100% informational -- there's no requirement that\n        it mean anything in particular. For example, \"GMT\", \"UTC\", \"-500\",\n        \"-5:00\", \"EDT\", \"US/Eastern\", \"America/New York\" are all valid replies.\n        "));
      var1.setlocal("tzname", var5);
      var3 = null;
      var1.setline(1424);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, dst$116, PyString.fromInterned("Return 0 if DST is not in effect, or the DST offset (in minutes\n        eastward) if DST is in effect.\n\n        This is purely informational; the DST offset has already been added to\n        the UTC offset returned by utcoffset() if applicable, so there's no\n        need to consult dst() unless you're interested in displaying the DST\n        info.\n        "));
      var1.setlocal("dst", var5);
      var3 = null;
      var1.setline(1442);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _dst$117, (PyObject)null);
      var1.setlocal("_dst", var5);
      var3 = null;
      var1.setline(1449);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("True")};
      var5 = new PyFunction(var1.f_globals, var4, replace$118, PyString.fromInterned("Return a new time with new values for the specified fields."));
      var1.setlocal("replace", var5);
      var3 = null;
      var1.setline(1464);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __nonzero__$119, (PyObject)null);
      var1.setlocal("__nonzero__", var5);
      var3 = null;
      var1.setline(1472);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _getstate$120, (PyObject)null);
      var1.setlocal("_getstate", var5);
      var3 = null;
      var1.setline(1482);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _time__setstate$121, (PyObject)null);
      var1.setlocal("_time__setstate", var5);
      var3 = null;
      var1.setline(1491);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __reduce__$122, (PyObject)null);
      var1.setlocal("__reduce__", var5);
      var3 = null;
      var1.setline(1494);
      if (var1.getname("_is_jython").__nonzero__()) {
         var1.setline(1495);
         var4 = Py.EmptyObjects;
         var5 = new PyFunction(var1.f_globals, var4, __tojava__$123, (PyObject)null);
         var1.setlocal("__tojava__", var5);
         var3 = null;
      }

      return var1.getf_locals();
   }

   public PyObject __new__$94(PyFrame var1, ThreadState var2) {
      var1.setline(1201);
      PyString.fromInterned("Constructor.\n\n        Arguments:\n\n        hour, minute (required)\n        second, microsecond (default to zero)\n        tzinfo (default to None)\n        ");
      var1.setline(1202);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("bytes"));
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._eq(Py.newInteger(6));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
            var10000 = var3._lt(Py.newInteger(24));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(1204);
         var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getlocal(0));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(1205);
         var10000 = var1.getlocal(6).__getattr__("_time__setstate");
         PyObject var10002 = var1.getlocal(1);
         PyObject var10003 = var1.getlocal(2);
         if (!var10003.__nonzero__()) {
            var10003 = var1.getglobal("None");
         }

         var10000.__call__(var2, var10002, var10003);
         var1.setline(1206);
         PyInteger var7 = Py.newInteger(-1);
         var1.getlocal(6).__setattr__((String)"_hashcode", var7);
         var3 = null;
         var1.setline(1207);
         var3 = var1.getlocal(6);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1208);
         PyObject var4 = var1.getglobal("_check_time_fields").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
         PyObject[] var5 = Py.unpackSequence(var4, 4);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[3];
         var1.setlocal(4, var6);
         var6 = null;
         var4 = null;
         var1.setline(1210);
         var1.getglobal("_check_tzinfo_arg").__call__(var2, var1.getlocal(5));
         var1.setline(1211);
         var4 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getlocal(0));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(1212);
         var4 = var1.getlocal(1);
         var1.getlocal(6).__setattr__("_hour", var4);
         var4 = null;
         var1.setline(1213);
         var4 = var1.getlocal(2);
         var1.getlocal(6).__setattr__("_minute", var4);
         var4 = null;
         var1.setline(1214);
         var4 = var1.getlocal(3);
         var1.getlocal(6).__setattr__("_second", var4);
         var4 = null;
         var1.setline(1215);
         var4 = var1.getlocal(4);
         var1.getlocal(6).__setattr__("_microsecond", var4);
         var4 = null;
         var1.setline(1216);
         var4 = var1.getlocal(5);
         var1.getlocal(6).__setattr__("_tzinfo", var4);
         var4 = null;
         var1.setline(1217);
         PyInteger var8 = Py.newInteger(-1);
         var1.getlocal(6).__setattr__((String)"_hashcode", var8);
         var4 = null;
         var1.setline(1218);
         var3 = var1.getlocal(6);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject hour$95(PyFrame var1, ThreadState var2) {
      var1.setline(1223);
      PyString.fromInterned("hour (0-23)");
      var1.setline(1224);
      PyObject var3 = var1.getlocal(0).__getattr__("_hour");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject minute$96(PyFrame var1, ThreadState var2) {
      var1.setline(1228);
      PyString.fromInterned("minute (0-59)");
      var1.setline(1229);
      PyObject var3 = var1.getlocal(0).__getattr__("_minute");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject second$97(PyFrame var1, ThreadState var2) {
      var1.setline(1233);
      PyString.fromInterned("second (0-59)");
      var1.setline(1234);
      PyObject var3 = var1.getlocal(0).__getattr__("_second");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject microsecond$98(PyFrame var1, ThreadState var2) {
      var1.setline(1238);
      PyString.fromInterned("microsecond (0-999999)");
      var1.setline(1239);
      PyObject var3 = var1.getlocal(0).__getattr__("_microsecond");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject tzinfo$99(PyFrame var1, ThreadState var2) {
      var1.setline(1243);
      PyString.fromInterned("timezone info object");
      var1.setline(1244);
      PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$100(PyFrame var1, ThreadState var2) {
      var1.setline(1251);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("time")).__nonzero__()) {
         var1.setline(1252);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1254);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$101(PyFrame var1, ThreadState var2) {
      var1.setline(1257);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("time")).__nonzero__()) {
         var1.setline(1258);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1260);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __le__$102(PyFrame var1, ThreadState var2) {
      var1.setline(1263);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("time")).__nonzero__()) {
         var1.setline(1264);
         PyObject var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._le(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1266);
         var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __lt__$103(PyFrame var1, ThreadState var2) {
      var1.setline(1269);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("time")).__nonzero__()) {
         var1.setline(1270);
         PyObject var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1272);
         var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __ge__$104(PyFrame var1, ThreadState var2) {
      var1.setline(1275);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("time")).__nonzero__()) {
         var1.setline(1276);
         PyObject var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1278);
         var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __gt__$105(PyFrame var1, ThreadState var2) {
      var1.setline(1281);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("time")).__nonzero__()) {
         var1.setline(1282);
         PyObject var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1284);
         var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _cmp$106(PyFrame var1, ThreadState var2) {
      var1.setline(1287);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("time")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(1288);
         PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1289);
         var3 = var1.getlocal(1).__getattr__("_tzinfo");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1290);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var1.setlocal(5, var3);
         var1.setline(1292);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1293);
            var3 = var1.getglobal("True");
            var1.setlocal(6, var3);
            var3 = null;
         } else {
            var1.setline(1295);
            var3 = var1.getlocal(0).__getattr__("_utcoffset").__call__(var2);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(1296);
            var3 = var1.getlocal(1).__getattr__("_utcoffset").__call__(var2);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(1297);
            var3 = var1.getlocal(4);
            var10000 = var3._eq(var1.getlocal(5));
            var3 = null;
            var3 = var10000;
            var1.setlocal(6, var3);
            var3 = null;
         }

         var1.setline(1299);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(1300);
            var3 = var1.getglobal("_cmp").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_hour"), var1.getlocal(0).__getattr__("_minute"), var1.getlocal(0).__getattr__("_second"), var1.getlocal(0).__getattr__("_microsecond")})), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("_hour"), var1.getlocal(1).__getattr__("_minute"), var1.getlocal(1).__getattr__("_second"), var1.getlocal(1).__getattr__("_microsecond")})));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1304);
            PyObject var4 = var1.getlocal(4);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(5);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1305);
               throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("can't compare offset-naive and offset-aware times")));
            } else {
               var1.setline(1306);
               var4 = var1.getlocal(0).__getattr__("_hour")._mul(Py.newInteger(60))._add(var1.getlocal(0).__getattr__("_minute"))._sub(var1.getlocal(4));
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(1307);
               var4 = var1.getlocal(1).__getattr__("_hour")._mul(Py.newInteger(60))._add(var1.getlocal(1).__getattr__("_minute"))._sub(var1.getlocal(5));
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(1308);
               var3 = var1.getglobal("_cmp").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(0).__getattr__("_second"), var1.getlocal(0).__getattr__("_microsecond")})), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(1).__getattr__("_second"), var1.getlocal(1).__getattr__("_microsecond")})));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject __hash__$107(PyFrame var1, ThreadState var2) {
      var1.setline(1312);
      PyString.fromInterned("Hash.");
      var1.setline(1313);
      PyObject var3 = var1.getlocal(0).__getattr__("_hashcode");
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1314);
         var3 = var1.getlocal(0).__getattr__("_utcoffset").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1315);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(1316);
            var3 = var1.getglobal("hash").__call__(var2, var1.getlocal(0).__getattr__("_getstate").__call__(var2).__getitem__(Py.newInteger(0)));
            var1.getlocal(0).__setattr__("_hashcode", var3);
            var3 = null;
         } else {
            var1.setline(1318);
            var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("hour")._mul(Py.newInteger(60))._add(var1.getlocal(0).__getattr__("minute"))._sub(var1.getlocal(1)), (PyObject)Py.newInteger(60));
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.setlocal(2, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(3, var5);
            var5 = null;
            var3 = null;
            var1.setline(1319);
            PyInteger var7 = Py.newInteger(0);
            PyObject var10001 = var1.getlocal(2);
            PyInteger var8 = var7;
            var3 = var10001;
            PyObject var6;
            if ((var6 = var8._le(var10001)).__nonzero__()) {
               var6 = var3._lt(Py.newInteger(24));
            }

            var3 = null;
            if (var6.__nonzero__()) {
               var1.setline(1320);
               var3 = var1.getglobal("hash").__call__(var2, var1.getglobal("time").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("second"), var1.getlocal(0).__getattr__("microsecond")));
               var1.getlocal(0).__setattr__("_hashcode", var3);
               var3 = null;
            } else {
               var1.setline(1322);
               var3 = var1.getglobal("hash").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("second"), var1.getlocal(0).__getattr__("microsecond")})));
               var1.getlocal(0).__setattr__("_hashcode", var3);
               var3 = null;
            }
         }
      }

      var1.setline(1323);
      var3 = var1.getlocal(0).__getattr__("_hashcode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _tzstr$108(PyFrame var1, ThreadState var2) {
      var1.setline(1328);
      PyString.fromInterned("Return formatted timezone offset (+xx:xx) or None.");
      var1.setline(1329);
      PyObject var3 = var1.getlocal(0).__getattr__("_utcoffset").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1330);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1331);
         var3 = var1.getlocal(2);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         PyString var7;
         if (var10000.__nonzero__()) {
            var1.setline(1332);
            var7 = PyString.fromInterned("-");
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(1333);
            var3 = var1.getlocal(2).__neg__();
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(1335);
            var7 = PyString.fromInterned("+");
            var1.setlocal(3, var7);
            var3 = null;
         }

         var1.setline(1336);
         var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(60));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(1337);
         if (var1.getglobal("__debug__").__nonzero__()) {
            PyInteger var8 = Py.newInteger(0);
            PyObject var10001 = var1.getlocal(4);
            PyInteger var9 = var8;
            var3 = var10001;
            PyObject var6;
            if ((var6 = var9._le(var10001)).__nonzero__()) {
               var6 = var3._lt(Py.newInteger(24));
            }

            var3 = null;
            if (!var6.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(1338);
         var3 = PyString.fromInterned("%s%02d%s%02d")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(1), var1.getlocal(5)}));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1339);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$109(PyFrame var1, ThreadState var2) {
      var1.setline(1342);
      PyString.fromInterned("Convert to formal string, for repr().");
      var1.setline(1343);
      PyObject var3 = var1.getlocal(0).__getattr__("_microsecond");
      PyObject var10000 = var3._ne(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1344);
         var3 = PyString.fromInterned(", %d, %d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_second"), var1.getlocal(0).__getattr__("_microsecond")}));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(1345);
         var3 = var1.getlocal(0).__getattr__("_second");
         var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1346);
            var3 = PyString.fromInterned(", %d")._mod(var1.getlocal(0).__getattr__("_second"));
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(1348);
            PyString var4 = PyString.fromInterned("");
            var1.setlocal(1, var4);
            var3 = null;
         }
      }

      var1.setline(1349);
      var3 = PyString.fromInterned("%s(%d, %d%s)")._mod(new PyTuple(new PyObject[]{PyString.fromInterned("datetime.")._add(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__")), var1.getlocal(0).__getattr__("_hour"), var1.getlocal(0).__getattr__("_minute"), var1.getlocal(1)}));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1351);
      var3 = var1.getlocal(0).__getattr__("_tzinfo");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1352);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(1).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
            var10000 = var3._eq(PyString.fromInterned(")"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(1353);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)._add(PyString.fromInterned(", tzinfo=%r")._mod(var1.getlocal(0).__getattr__("_tzinfo")))._add(PyString.fromInterned(")"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1354);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isoformat$110(PyFrame var1, ThreadState var2) {
      var1.setline(1361);
      PyString.fromInterned("Return the time formatted according to ISO.\n\n        This is 'HH:MM:SS.mmmmmm+zz:zz', or 'HH:MM:SS+zz:zz' if\n        self.microsecond == 0.\n        ");
      var1.setline(1362);
      PyObject var3 = var1.getglobal("_format_time").__call__(var2, var1.getlocal(0).__getattr__("_hour"), var1.getlocal(0).__getattr__("_minute"), var1.getlocal(0).__getattr__("_second"), var1.getlocal(0).__getattr__("_microsecond"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1364);
      var3 = var1.getlocal(0).__getattr__("_tzstr").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1365);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(1366);
         var3 = var1.getlocal(1);
         var3 = var3._iadd(var1.getlocal(2));
         var1.setlocal(1, var3);
      }

      var1.setline(1367);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject strftime$111(PyFrame var1, ThreadState var2) {
      var1.setline(1374);
      PyString.fromInterned("Format using strftime().  The date part of the timestamp passed\n        to underlying strftime should not be used.\n        ");
      var1.setline(1377);
      PyTuple var3 = new PyTuple(new PyObject[]{Py.newInteger(1900), Py.newInteger(1), Py.newInteger(1), var1.getlocal(0).__getattr__("_hour"), var1.getlocal(0).__getattr__("_minute"), var1.getlocal(0).__getattr__("_second"), Py.newInteger(0), Py.newInteger(1), Py.newInteger(-1)});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1380);
      PyObject var4 = var1.getglobal("_wrap_strftime").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __format__$112(PyFrame var1, ThreadState var2) {
      var1.setline(1383);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("str"), var1.getglobal("unicode")}))).__not__().__nonzero__()) {
         var1.setline(1384);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("__format__ expects str or unicode, not %s")._mod(var1.getlocal(1).__getattr__("__class__").__getattr__("__name__"))));
      } else {
         var1.setline(1386);
         PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1387);
            var3 = var1.getlocal(0).__getattr__("strftime").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1388);
            var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject utcoffset$113(PyFrame var1, ThreadState var2) {
      var1.setline(1394);
      PyString.fromInterned("Return the timezone offset in minutes east of UTC (negative west of\n        UTC).");
      var1.setline(1395);
      PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1396);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1397);
         PyObject var4 = var1.getlocal(0).__getattr__("_tzinfo").__getattr__("utcoffset").__call__(var2, var1.getglobal("None"));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1398);
         var4 = var1.getglobal("_check_utc_offset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utcoffset"), (PyObject)var1.getlocal(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1399);
         var4 = var1.getlocal(1);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1400);
            var10000 = var1.getglobal("timedelta");
            PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
            String[] var5 = new String[]{"minutes"};
            var10000 = var10000.__call__(var2, var6, var5);
            var4 = null;
            var4 = var10000;
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(1401);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _utcoffset$114(PyFrame var1, ThreadState var2) {
      var1.setline(1405);
      PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1406);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1407);
         PyObject var4 = var1.getlocal(0).__getattr__("_tzinfo").__getattr__("utcoffset").__call__(var2, var1.getglobal("None"));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1408);
         var4 = var1.getglobal("_check_utc_offset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utcoffset"), (PyObject)var1.getlocal(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1409);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject tzname$115(PyFrame var1, ThreadState var2) {
      var1.setline(1417);
      PyString.fromInterned("Return the timezone name.\n\n        Note that the name is 100% informational -- there's no requirement that\n        it mean anything in particular. For example, \"GMT\", \"UTC\", \"-500\",\n        \"-5:00\", \"EDT\", \"US/Eastern\", \"America/New York\" are all valid replies.\n        ");
      var1.setline(1418);
      PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1419);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1420);
         PyObject var4 = var1.getlocal(0).__getattr__("_tzinfo").__getattr__("tzname").__call__(var2, var1.getglobal("None"));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1421);
         var1.getglobal("_check_tzname").__call__(var2, var1.getlocal(1));
         var1.setline(1422);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject dst$116(PyFrame var1, ThreadState var2) {
      var1.setline(1432);
      PyString.fromInterned("Return 0 if DST is not in effect, or the DST offset (in minutes\n        eastward) if DST is in effect.\n\n        This is purely informational; the DST offset has already been added to\n        the UTC offset returned by utcoffset() if applicable, so there's no\n        need to consult dst() unless you're interested in displaying the DST\n        info.\n        ");
      var1.setline(1433);
      PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1434);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1435);
         PyObject var4 = var1.getlocal(0).__getattr__("_tzinfo").__getattr__("dst").__call__(var2, var1.getglobal("None"));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1436);
         var4 = var1.getglobal("_check_utc_offset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dst"), (PyObject)var1.getlocal(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1437);
         var4 = var1.getlocal(1);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1438);
            var10000 = var1.getglobal("timedelta");
            PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
            String[] var5 = new String[]{"minutes"};
            var10000 = var10000.__call__(var2, var6, var5);
            var4 = null;
            var4 = var10000;
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(1439);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _dst$117(PyFrame var1, ThreadState var2) {
      var1.setline(1443);
      PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1444);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1445);
         PyObject var4 = var1.getlocal(0).__getattr__("_tzinfo").__getattr__("dst").__call__(var2, var1.getglobal("None"));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1446);
         var4 = var1.getglobal("_check_utc_offset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dst"), (PyObject)var1.getlocal(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1447);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject replace$118(PyFrame var1, ThreadState var2) {
      var1.setline(1451);
      PyString.fromInterned("Return a new time with new values for the specified fields.");
      var1.setline(1452);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1453);
         var3 = var1.getlocal(0).__getattr__("hour");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1454);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1455);
         var3 = var1.getlocal(0).__getattr__("minute");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1456);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1457);
         var3 = var1.getlocal(0).__getattr__("second");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1458);
      var3 = var1.getlocal(4);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1459);
         var3 = var1.getlocal(0).__getattr__("microsecond");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(1460);
      var3 = var1.getlocal(5);
      var10000 = var3._is(var1.getglobal("True"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1461);
         var3 = var1.getlocal(0).__getattr__("tzinfo");
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(1462);
      var10000 = var1.getglobal("time");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
      var3 = var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __nonzero__$119(PyFrame var1, ThreadState var2) {
      var1.setline(1465);
      PyObject var10000 = var1.getlocal(0).__getattr__("second");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("microsecond");
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(1466);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1467);
         Object var6 = var1.getlocal(0).__getattr__("_utcoffset").__call__(var2);
         if (!((PyObject)var6).__nonzero__()) {
            var6 = Py.newInteger(0);
         }

         Object var4 = var6;
         var1.setlocal(1, (PyObject)var4);
         var4 = null;
         var1.setline(1468);
         PyObject var5 = var1.getlocal(0).__getattr__("hour")._mul(Py.newInteger(60))._add(var1.getlocal(0).__getattr__("minute"));
         var10000 = var5._ne(var1.getlocal(1));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _getstate$120(PyFrame var1, ThreadState var2) {
      var1.setline(1473);
      PyObject var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_microsecond"), (PyObject)Py.newInteger(256));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1474);
      var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(256));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(1, var5);
      var5 = null;
      var3 = null;
      var1.setline(1475);
      PyObject var10000 = var1.getglobal("_struct").__getattr__("pack");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("6B"), var1.getlocal(0).__getattr__("_hour"), var1.getlocal(0).__getattr__("_minute"), var1.getlocal(0).__getattr__("_second"), var1.getlocal(3), var1.getlocal(1), var1.getlocal(2)};
      var3 = var10000.__call__(var2, var6);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1477);
      var3 = var1.getlocal(0).__getattr__("_tzinfo");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyTuple var7;
      if (var10000.__nonzero__()) {
         var1.setline(1478);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(4)});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(1480);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("_tzinfo")});
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject _time__setstate$121(PyFrame var1, ThreadState var2) {
      var1.setline(1483);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("_tzinfo_class")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(1484);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bad tzinfo state arg")));
      } else {
         var1.setline(1485);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(3))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(4))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(5)))});
         PyObject[] var4 = Py.unpackSequence(var6, 6);
         PyObject var5 = var4[0];
         var1.getlocal(0).__setattr__("_hour", var5);
         var5 = null;
         var5 = var4[1];
         var1.getlocal(0).__setattr__("_minute", var5);
         var5 = null;
         var5 = var4[2];
         var1.getlocal(0).__setattr__("_second", var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[4];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[5];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
         var1.setline(1488);
         var3 = var1.getlocal(3)._lshift(Py.newInteger(8))._or(var1.getlocal(4))._lshift(Py.newInteger(8))._or(var1.getlocal(5));
         var1.getlocal(0).__setattr__("_microsecond", var3);
         var3 = null;
         var1.setline(1489);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("_tzinfo", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __reduce__$122(PyFrame var1, ThreadState var2) {
      var1.setline(1492);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("time"), var1.getlocal(0).__getattr__("_getstate").__call__(var2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __tojava__$123(PyFrame var1, ThreadState var2) {
      var1.setline(1496);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("Calendar"), var1.getglobal("Time"), var1.getglobal("Object")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1497);
         var3 = var1.getglobal("Py").__getattr__("NoConversion");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1498);
         PyObject var4 = var1.getglobal("_make_java_calendar").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1499);
         var4 = var1.getlocal(2);
         var10000 = var4._eq(var1.getglobal("Py").__getattr__("NoConversion"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1500);
            var3 = var1.getglobal("Py").__getattr__("NoConversion");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1503);
            var1.getlocal(2).__getattr__("setTimeInMillis").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
            var1.setline(1506);
            var1.getlocal(2).__getattr__("set").__call__(var2, var1.getglobal("Calendar").__getattr__("HOUR_OF_DAY"), var1.getlocal(0).__getattr__("hour"));
            var1.setline(1507);
            var1.getlocal(2).__getattr__("set").__call__(var2, var1.getglobal("Calendar").__getattr__("MINUTE"), var1.getlocal(0).__getattr__("minute"));
            var1.setline(1508);
            var1.getlocal(2).__getattr__("set").__call__(var2, var1.getglobal("Calendar").__getattr__("SECOND"), var1.getlocal(0).__getattr__("second"));
            var1.setline(1509);
            var1.getlocal(2).__getattr__("set").__call__(var2, var1.getglobal("Calendar").__getattr__("MILLISECOND"), var1.getlocal(0).__getattr__("microsecond")._floordiv(Py.newInteger(1000)));
            var1.setline(1511);
            var4 = var1.getlocal(1);
            var10000 = var4._eq(var1.getglobal("Calendar"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1512);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1514);
               var3 = var1.getglobal("Time").__call__(var2, var1.getlocal(2).__getattr__("getTimeInMillis").__call__(var2));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject datetime$124(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("datetime(year, month, day[, hour[, minute[, second[, microsecond[,tzinfo]]]]])\n\n    The year, month and day arguments are required. tzinfo may be None, or an\n    instance of a tzinfo subclass. The remaining arguments may be ints or longs.\n    "));
      var1.setline(1528);
      PyString.fromInterned("datetime(year, month, day[, hour[, minute[, second[, microsecond[,tzinfo]]]]])\n\n    The year, month and day arguments are required. tzinfo may be None, or an\n    instance of a tzinfo subclass. The remaining arguments may be ints or longs.\n    ");
      var1.setline(1529);
      PyObject var3 = var1.getname("date").__getattr__("__slots__")._add(var1.getname("time").__getattr__("__slots__"));
      var1.setlocal("__slots__", var3);
      var3 = null;
      var1.setline(1531);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __new__$125, (PyObject)null);
      var1.setlocal("__new__", var5);
      var3 = null;
      var1.setline(1557);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, hour$126, PyString.fromInterned("hour (0-23)"));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("hour", var3);
      var3 = null;
      var1.setline(1562);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, minute$127, PyString.fromInterned("minute (0-59)"));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("minute", var3);
      var3 = null;
      var1.setline(1567);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, second$128, PyString.fromInterned("second (0-59)"));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("second", var3);
      var3 = null;
      var1.setline(1572);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, microsecond$129, PyString.fromInterned("microsecond (0-999999)"));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("microsecond", var3);
      var3 = null;
      var1.setline(1577);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tzinfo$130, PyString.fromInterned("timezone info object"));
      var3 = var1.getname("property").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tzinfo", var3);
      var3 = null;
      var1.setline(1582);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, fromtimestamp$131, PyString.fromInterned("Construct a datetime from a POSIX timestamp (like time.time()).\n\n        A timezone info object may be passed in as well.\n        "));
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("fromtimestamp", var3);
      var3 = null;
      var1.setline(1610);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, utcfromtimestamp$132, PyString.fromInterned("Construct a UTC datetime from a POSIX timestamp (like time.time())."));
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("utcfromtimestamp", var3);
      var3 = null;
      var1.setline(1627);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, now$133, PyString.fromInterned("Construct a datetime from time.time() and optional time zone info."));
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("now", var3);
      var3 = null;
      var1.setline(1633);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, utcnow$134, PyString.fromInterned("Construct a UTC datetime from time.time()."));
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("utcnow", var3);
      var3 = null;
      var1.setline(1639);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, combine$135, PyString.fromInterned("Construct a datetime from a given date and a given time."));
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("combine", var3);
      var3 = null;
      var1.setline(1650);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, timetuple$136, PyString.fromInterned("Return local time tuple compatible with time.localtime()."));
      var1.setlocal("timetuple", var5);
      var3 = null;
      var1.setline(1661);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, utctimetuple$137, PyString.fromInterned("Return UTC time tuple compatible with time.gmtime()."));
      var1.setlocal("utctimetuple", var5);
      var3 = null;
      var1.setline(1672);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, date$138, PyString.fromInterned("Return the date part."));
      var1.setlocal("date", var5);
      var3 = null;
      var1.setline(1676);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, time$139, PyString.fromInterned("Return the time part, with tzinfo None."));
      var1.setlocal("time", var5);
      var3 = null;
      var1.setline(1680);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, timetz$140, PyString.fromInterned("Return the time part, with same tzinfo."));
      var1.setlocal("timetz", var5);
      var3 = null;
      var1.setline(1685);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("True")};
      var5 = new PyFunction(var1.f_globals, var4, replace$141, PyString.fromInterned("Return a new datetime with new values for the specified fields."));
      var1.setlocal("replace", var5);
      var3 = null;
      var1.setline(1707);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, astimezone$142, (PyObject)null);
      var1.setlocal("astimezone", var5);
      var3 = null;
      var1.setline(1729);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, ctime$143, PyString.fromInterned("Return ctime() style string."));
      var1.setlocal("ctime", var5);
      var3 = null;
      var1.setline(1739);
      var4 = new PyObject[]{PyString.fromInterned("T")};
      var5 = new PyFunction(var1.f_globals, var4, isoformat$144, PyString.fromInterned("Return the time formatted according to ISO.\n\n        This is 'YYYY-MM-DD HH:MM:SS.mmmmmm', or 'YYYY-MM-DD HH:MM:SS' if\n        self.microsecond == 0.\n\n        If self.tzinfo is not None, the UTC offset is also attached, giving\n        'YYYY-MM-DD HH:MM:SS.mmmmmm+HH:MM' or 'YYYY-MM-DD HH:MM:SS+HH:MM'.\n\n        Optional argument sep specifies the separator between date and\n        time, default 'T'.\n        "));
      var1.setlocal("isoformat", var5);
      var3 = null;
      var1.setline(1765);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$145, PyString.fromInterned("Convert to formal string, for repr()."));
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(1780);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __str__$146, PyString.fromInterned("Convert to string, for str()."));
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(1784);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, strptime$147, PyString.fromInterned("string, format -> new datetime parsed from a string (like time.strptime())."));
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("strptime", var3);
      var3 = null;
      var1.setline(1794);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, utcoffset$148, PyString.fromInterned("Return the timezone offset in minutes east of UTC (negative west of\n        UTC)."));
      var1.setlocal("utcoffset", var5);
      var3 = null;
      var1.setline(1806);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _utcoffset$149, (PyObject)null);
      var1.setlocal("_utcoffset", var5);
      var3 = null;
      var1.setline(1813);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tzname$150, PyString.fromInterned("Return the timezone name.\n\n        Note that the name is 100% informational -- there's no requirement that\n        it mean anything in particular. For example, \"GMT\", \"UTC\", \"-500\",\n        \"-5:00\", \"EDT\", \"US/Eastern\", \"America/New York\" are all valid replies.\n        "));
      var1.setlocal("tzname", var5);
      var3 = null;
      var1.setline(1826);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, dst$151, PyString.fromInterned("Return 0 if DST is not in effect, or the DST offset (in minutes\n        eastward) if DST is in effect.\n\n        This is purely informational; the DST offset has already been added to\n        the UTC offset returned by utcoffset() if applicable, so there's no\n        need to consult dst() unless you're interested in displaying the DST\n        info.\n        "));
      var1.setlocal("dst", var5);
      var3 = null;
      var1.setline(1844);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _dst$152, (PyObject)null);
      var1.setlocal("_dst", var5);
      var3 = null;
      var1.setline(1853);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __eq__$153, (PyObject)null);
      var1.setlocal("__eq__", var5);
      var3 = null;
      var1.setline(1861);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ne__$154, (PyObject)null);
      var1.setlocal("__ne__", var5);
      var3 = null;
      var1.setline(1869);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __le__$155, (PyObject)null);
      var1.setlocal("__le__", var5);
      var3 = null;
      var1.setline(1877);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __lt__$156, (PyObject)null);
      var1.setlocal("__lt__", var5);
      var3 = null;
      var1.setline(1885);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ge__$157, (PyObject)null);
      var1.setlocal("__ge__", var5);
      var3 = null;
      var1.setline(1893);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __gt__$158, (PyObject)null);
      var1.setlocal("__gt__", var5);
      var3 = null;
      var1.setline(1901);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _cmp$159, (PyObject)null);
      var1.setlocal("_cmp", var5);
      var3 = null;
      var1.setline(1931);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __add__$160, PyString.fromInterned("Add a datetime and a timedelta."));
      var1.setlocal("__add__", var5);
      var3 = null;
      var1.setline(1948);
      var3 = var1.getname("__add__");
      var1.setlocal("__radd__", var3);
      var3 = null;
      var1.setline(1950);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __sub__$161, PyString.fromInterned("Subtract two datetimes, or a datetime and a timedelta."));
      var1.setlocal("__sub__", var5);
      var3 = null;
      var1.setline(1974);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __hash__$162, (PyObject)null);
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(1987);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _getstate$163, (PyObject)null);
      var1.setlocal("_getstate", var5);
      var3 = null;
      var1.setline(1999);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _datetime__setstate$164, (PyObject)null);
      var1.setlocal("_datetime__setstate", var5);
      var3 = null;
      var1.setline(2011);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __reduce__$165, (PyObject)null);
      var1.setlocal("__reduce__", var5);
      var3 = null;
      var1.setline(2014);
      if (var1.getname("_is_jython").__nonzero__()) {
         var1.setline(2015);
         var4 = Py.EmptyObjects;
         var5 = new PyFunction(var1.f_globals, var4, __tojava__$166, (PyObject)null);
         var1.setlocal("__tojava__", var5);
         var3 = null;
      }

      return var1.getf_locals();
   }

   public PyObject __new__$125(PyFrame var1, ThreadState var2) {
      var1.setline(1533);
      PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("bytes"));
      PyObject var3;
      PyObject var4;
      PyInteger var7;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._eq(Py.newInteger(10));
         var3 = null;
         if (var10000.__nonzero__()) {
            var7 = Py.newInteger(1);
            PyObject var10001 = var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2)));
            PyInteger var9 = var7;
            var3 = var10001;
            if ((var4 = var9._le(var10001)).__nonzero__()) {
               var4 = var3._le(Py.newInteger(12));
            }

            var10000 = var4;
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(1536);
         var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getlocal(0));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(1537);
         var1.getlocal(9).__getattr__("_datetime__setstate").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setline(1538);
         var7 = Py.newInteger(-1);
         var1.getlocal(9).__setattr__((String)"_hashcode", var7);
         var3 = null;
         var1.setline(1539);
         var3 = var1.getlocal(9);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1540);
         var4 = var1.getglobal("_check_date_fields").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(3, var6);
         var6 = null;
         var4 = null;
         var1.setline(1541);
         var4 = var1.getglobal("_check_time_fields").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7));
         var5 = Py.unpackSequence(var4, 4);
         var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[3];
         var1.setlocal(7, var6);
         var6 = null;
         var4 = null;
         var1.setline(1543);
         var1.getglobal("_check_tzinfo_arg").__call__(var2, var1.getlocal(8));
         var1.setline(1544);
         var4 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getlocal(0));
         var1.setlocal(9, var4);
         var4 = null;
         var1.setline(1545);
         var4 = var1.getlocal(1);
         var1.getlocal(9).__setattr__("_year", var4);
         var4 = null;
         var1.setline(1546);
         var4 = var1.getlocal(2);
         var1.getlocal(9).__setattr__("_month", var4);
         var4 = null;
         var1.setline(1547);
         var4 = var1.getlocal(3);
         var1.getlocal(9).__setattr__("_day", var4);
         var4 = null;
         var1.setline(1548);
         var4 = var1.getlocal(4);
         var1.getlocal(9).__setattr__("_hour", var4);
         var4 = null;
         var1.setline(1549);
         var4 = var1.getlocal(5);
         var1.getlocal(9).__setattr__("_minute", var4);
         var4 = null;
         var1.setline(1550);
         var4 = var1.getlocal(6);
         var1.getlocal(9).__setattr__("_second", var4);
         var4 = null;
         var1.setline(1551);
         var4 = var1.getlocal(7);
         var1.getlocal(9).__setattr__("_microsecond", var4);
         var4 = null;
         var1.setline(1552);
         var4 = var1.getlocal(8);
         var1.getlocal(9).__setattr__("_tzinfo", var4);
         var4 = null;
         var1.setline(1553);
         PyInteger var8 = Py.newInteger(-1);
         var1.getlocal(9).__setattr__((String)"_hashcode", var8);
         var4 = null;
         var1.setline(1554);
         var3 = var1.getlocal(9);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject hour$126(PyFrame var1, ThreadState var2) {
      var1.setline(1559);
      PyString.fromInterned("hour (0-23)");
      var1.setline(1560);
      PyObject var3 = var1.getlocal(0).__getattr__("_hour");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject minute$127(PyFrame var1, ThreadState var2) {
      var1.setline(1564);
      PyString.fromInterned("minute (0-59)");
      var1.setline(1565);
      PyObject var3 = var1.getlocal(0).__getattr__("_minute");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject second$128(PyFrame var1, ThreadState var2) {
      var1.setline(1569);
      PyString.fromInterned("second (0-59)");
      var1.setline(1570);
      PyObject var3 = var1.getlocal(0).__getattr__("_second");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject microsecond$129(PyFrame var1, ThreadState var2) {
      var1.setline(1574);
      PyString.fromInterned("microsecond (0-999999)");
      var1.setline(1575);
      PyObject var3 = var1.getlocal(0).__getattr__("_microsecond");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject tzinfo$130(PyFrame var1, ThreadState var2) {
      var1.setline(1579);
      PyString.fromInterned("timezone info object");
      var1.setline(1580);
      PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fromtimestamp$131(PyFrame var1, ThreadState var2) {
      var1.setline(1587);
      PyString.fromInterned("Construct a datetime from a POSIX timestamp (like time.time()).\n\n        A timezone info object may be passed in as well.\n        ");
      var1.setline(1589);
      var1.getglobal("_check_tzinfo_arg").__call__(var2, var1.getlocal(2));
      var1.setline(1591);
      var1.setline(1591);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      var3 = var10000.__nonzero__() ? var1.getglobal("_time").__getattr__("localtime") : var1.getglobal("_time").__getattr__("gmtime");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1593);
      var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newFloat(1.0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1594);
      var3 = var1.getglobal("_round").__call__(var2, var1.getlocal(4)._mul(Py.newFloat(1000000.0)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(1600);
      var3 = var1.getlocal(5);
      var10000 = var3._eq(Py.newInteger(1000000));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1601);
         var3 = var1.getlocal(1);
         var3 = var3._iadd(Py.newInteger(1));
         var1.setlocal(1, var3);
         var1.setline(1602);
         PyInteger var6 = Py.newInteger(0);
         var1.setlocal(5, var6);
         var3 = null;
      }

      var1.setline(1603);
      var3 = var1.getlocal(3).__call__(var2, var1.getlocal(1));
      var4 = Py.unpackSequence(var3, 9);
      var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(11, var5);
      var5 = null;
      var5 = var4[6];
      var1.setlocal(12, var5);
      var5 = null;
      var5 = var4[7];
      var1.setlocal(13, var5);
      var5 = null;
      var5 = var4[8];
      var1.setlocal(14, var5);
      var5 = null;
      var3 = null;
      var1.setline(1604);
      var3 = var1.getglobal("min").__call__((ThreadState)var2, (PyObject)var1.getlocal(11), (PyObject)Py.newInteger(59));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(1605);
      var10000 = var1.getlocal(0);
      PyObject[] var7 = new PyObject[]{var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), var1.getlocal(5), var1.getlocal(2)};
      var3 = var10000.__call__(var2, var7);
      var1.setlocal(15, var3);
      var3 = null;
      var1.setline(1606);
      var3 = var1.getlocal(2);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1607);
         var3 = var1.getlocal(2).__getattr__("fromutc").__call__(var2, var1.getlocal(15));
         var1.setlocal(15, var3);
         var3 = null;
      }

      var1.setline(1608);
      var3 = var1.getlocal(15);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject utcfromtimestamp$132(PyFrame var1, ThreadState var2) {
      var1.setline(1612);
      PyString.fromInterned("Construct a UTC datetime from a POSIX timestamp (like time.time()).");
      var1.setline(1613);
      PyObject var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newFloat(1.0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1614);
      var3 = var1.getglobal("_round").__call__(var2, var1.getlocal(2)._mul(Py.newFloat(1000000.0)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1620);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(Py.newInteger(1000000));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1621);
         var3 = var1.getlocal(1);
         var3 = var3._iadd(Py.newInteger(1));
         var1.setlocal(1, var3);
         var1.setline(1622);
         PyInteger var6 = Py.newInteger(0);
         var1.setlocal(3, var6);
         var3 = null;
      }

      var1.setline(1623);
      var3 = var1.getglobal("_time").__getattr__("gmtime").__call__(var2, var1.getlocal(1));
      var4 = Py.unpackSequence(var3, 9);
      var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[6];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[7];
      var1.setlocal(11, var5);
      var5 = null;
      var5 = var4[8];
      var1.setlocal(12, var5);
      var5 = null;
      var3 = null;
      var1.setline(1624);
      var3 = var1.getglobal("min").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)Py.newInteger(59));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(1625);
      var10000 = var1.getlocal(0);
      PyObject[] var7 = new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(3)};
      var3 = var10000.__call__(var2, var7);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject now$133(PyFrame var1, ThreadState var2) {
      var1.setline(1629);
      PyString.fromInterned("Construct a datetime from time.time() and optional time zone info.");
      var1.setline(1630);
      PyObject var3 = var1.getglobal("_time").__getattr__("time").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1631);
      var3 = var1.getlocal(0).__getattr__("fromtimestamp").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject utcnow$134(PyFrame var1, ThreadState var2) {
      var1.setline(1635);
      PyString.fromInterned("Construct a UTC datetime from time.time().");
      var1.setline(1636);
      PyObject var3 = var1.getglobal("_time").__getattr__("time").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1637);
      var3 = var1.getlocal(0).__getattr__("utcfromtimestamp").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject combine$135(PyFrame var1, ThreadState var2) {
      var1.setline(1641);
      PyString.fromInterned("Construct a datetime from a given date and a given time.");
      var1.setline(1642);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_date_class")).__not__().__nonzero__()) {
         var1.setline(1643);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("date argument must be a date instance")));
      } else {
         var1.setline(1644);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("_time_class")).__not__().__nonzero__()) {
            var1.setline(1645);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("time argument must be a time instance")));
         } else {
            var1.setline(1646);
            PyObject var10000 = var1.getlocal(0);
            PyObject[] var3 = new PyObject[]{var1.getlocal(1).__getattr__("year"), var1.getlocal(1).__getattr__("month"), var1.getlocal(1).__getattr__("day"), var1.getlocal(2).__getattr__("hour"), var1.getlocal(2).__getattr__("minute"), var1.getlocal(2).__getattr__("second"), var1.getlocal(2).__getattr__("microsecond"), var1.getlocal(2).__getattr__("tzinfo")};
            PyObject var4 = var10000.__call__(var2, var3);
            var1.f_lasti = -1;
            return var4;
         }
      }
   }

   public PyObject timetuple$136(PyFrame var1, ThreadState var2) {
      var1.setline(1651);
      PyString.fromInterned("Return local time tuple compatible with time.localtime().");
      var1.setline(1652);
      PyObject var3 = var1.getlocal(0).__getattr__("_dst").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1653);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyInteger var4;
      if (var10000.__nonzero__()) {
         var1.setline(1654);
         var4 = Py.newInteger(-1);
         var1.setlocal(1, var4);
         var3 = null;
      } else {
         var1.setline(1655);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(1656);
            var4 = Py.newInteger(1);
            var1.setlocal(1, var4);
            var3 = null;
         }
      }

      var1.setline(1657);
      var10000 = var1.getglobal("_build_struct_time");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("year"), var1.getlocal(0).__getattr__("month"), var1.getlocal(0).__getattr__("day"), var1.getlocal(0).__getattr__("hour"), var1.getlocal(0).__getattr__("minute"), var1.getlocal(0).__getattr__("second"), var1.getlocal(1)};
      var3 = var10000.__call__(var2, var5);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject utctimetuple$137(PyFrame var1, ThreadState var2) {
      var1.setline(1662);
      PyString.fromInterned("Return UTC time tuple compatible with time.gmtime().");
      var1.setline(1663);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("year"), var1.getlocal(0).__getattr__("month"), var1.getlocal(0).__getattr__("day")});
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1664);
      var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("hour"), var1.getlocal(0).__getattr__("minute"), var1.getlocal(0).__getattr__("second")});
      var4 = Py.unpackSequence(var3, 3);
      var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(1665);
      PyObject var6 = var1.getlocal(0).__getattr__("_utcoffset").__call__(var2);
      var1.setlocal(7, var6);
      var3 = null;
      var1.setline(1666);
      PyObject var10000;
      PyObject[] var7;
      if (var1.getlocal(7).__nonzero__()) {
         var1.setline(1667);
         var10000 = var1.getglobal("_tmxxx");
         var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)._sub(var1.getlocal(7))};
         var6 = var10000.__call__(var2, var7);
         var1.setlocal(8, var6);
         var3 = null;
         var1.setline(1668);
         var3 = new PyTuple(new PyObject[]{var1.getlocal(8).__getattr__("year"), var1.getlocal(8).__getattr__("month"), var1.getlocal(8).__getattr__("day")});
         var4 = Py.unpackSequence(var3, 3);
         var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(1669);
         var3 = new PyTuple(new PyObject[]{var1.getlocal(8).__getattr__("hour"), var1.getlocal(8).__getattr__("minute")});
         var4 = Py.unpackSequence(var3, 2);
         var5 = var4[0];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(5, var5);
         var5 = null;
         var3 = null;
      }

      var1.setline(1670);
      var10000 = var1.getglobal("_build_struct_time");
      var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), Py.newInteger(0)};
      var6 = var10000.__call__(var2, var7);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject date$138(PyFrame var1, ThreadState var2) {
      var1.setline(1673);
      PyString.fromInterned("Return the date part.");
      var1.setline(1674);
      PyObject var3 = var1.getglobal("date").__call__(var2, var1.getlocal(0).__getattr__("_year"), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject time$139(PyFrame var1, ThreadState var2) {
      var1.setline(1677);
      PyString.fromInterned("Return the time part, with tzinfo None.");
      var1.setline(1678);
      PyObject var3 = var1.getglobal("time").__call__(var2, var1.getlocal(0).__getattr__("hour"), var1.getlocal(0).__getattr__("minute"), var1.getlocal(0).__getattr__("second"), var1.getlocal(0).__getattr__("microsecond"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject timetz$140(PyFrame var1, ThreadState var2) {
      var1.setline(1681);
      PyString.fromInterned("Return the time part, with same tzinfo.");
      var1.setline(1682);
      PyObject var10000 = var1.getglobal("time");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("hour"), var1.getlocal(0).__getattr__("minute"), var1.getlocal(0).__getattr__("second"), var1.getlocal(0).__getattr__("microsecond"), var1.getlocal(0).__getattr__("_tzinfo")};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject replace$141(PyFrame var1, ThreadState var2) {
      var1.setline(1687);
      PyString.fromInterned("Return a new datetime with new values for the specified fields.");
      var1.setline(1688);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1689);
         var3 = var1.getlocal(0).__getattr__("year");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(1690);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1691);
         var3 = var1.getlocal(0).__getattr__("month");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1692);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1693);
         var3 = var1.getlocal(0).__getattr__("day");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(1694);
      var3 = var1.getlocal(4);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1695);
         var3 = var1.getlocal(0).__getattr__("hour");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(1696);
      var3 = var1.getlocal(5);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1697);
         var3 = var1.getlocal(0).__getattr__("minute");
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(1698);
      var3 = var1.getlocal(6);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1699);
         var3 = var1.getlocal(0).__getattr__("second");
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(1700);
      var3 = var1.getlocal(7);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1701);
         var3 = var1.getlocal(0).__getattr__("microsecond");
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(1702);
      var3 = var1.getlocal(8);
      var10000 = var3._is(var1.getglobal("True"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1703);
         var3 = var1.getlocal(0).__getattr__("tzinfo");
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(1704);
      var10000 = var1.getglobal("datetime");
      PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8)};
      var3 = var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject astimezone$142(PyFrame var1, ThreadState var2) {
      var1.setline(1708);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("tzinfo")).__not__().__nonzero__()) {
         var1.setline(1709);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tz argument must be an instance of tzinfo")));
      } else {
         var1.setline(1711);
         PyObject var3 = var1.getlocal(0).__getattr__("tzinfo");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1712);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1713);
            throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("astimezone() requires an aware datetime")));
         } else {
            var1.setline(1715);
            var3 = var1.getlocal(1);
            var10000 = var3._is(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1716);
               var3 = var1.getlocal(0);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1719);
               PyObject var4 = var1.getlocal(0).__getattr__("utcoffset").__call__(var2);
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(1720);
               var4 = var1.getlocal(3);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1721);
                  throw Py.makeException(var1.getglobal("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("astimezone() requires an aware datetime")));
               } else {
                  var1.setline(1722);
                  var10000 = var1.getlocal(0)._sub(var1.getlocal(3)).__getattr__("replace");
                  PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
                  String[] var5 = new String[]{"tzinfo"};
                  var10000 = var10000.__call__(var2, var6, var5);
                  var4 = null;
                  var4 = var10000;
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(1725);
                  var3 = var1.getlocal(1).__getattr__("fromutc").__call__(var2, var1.getlocal(4));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject ctime$143(PyFrame var1, ThreadState var2) {
      var1.setline(1730);
      PyString.fromInterned("Return ctime() style string.");
      var1.setline(1731);
      Object var10000 = var1.getlocal(0).__getattr__("toordinal").__call__(var2)._mod(Py.newInteger(7));
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = Py.newInteger(7);
      }

      Object var3 = var10000;
      var1.setlocal(1, (PyObject)var3);
      var3 = null;
      var1.setline(1732);
      PyObject var4 = PyString.fromInterned("%s %s %2d %02d:%02d:%02d %04d")._mod(new PyTuple(new PyObject[]{var1.getglobal("_DAYNAMES").__getitem__(var1.getlocal(1)), var1.getglobal("_MONTHNAMES").__getitem__(var1.getlocal(0).__getattr__("_month")), var1.getlocal(0).__getattr__("_day"), var1.getlocal(0).__getattr__("_hour"), var1.getlocal(0).__getattr__("_minute"), var1.getlocal(0).__getattr__("_second"), var1.getlocal(0).__getattr__("_year")}));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject isoformat$144(PyFrame var1, ThreadState var2) {
      var1.setline(1750);
      PyString.fromInterned("Return the time formatted according to ISO.\n\n        This is 'YYYY-MM-DD HH:MM:SS.mmmmmm', or 'YYYY-MM-DD HH:MM:SS' if\n        self.microsecond == 0.\n\n        If self.tzinfo is not None, the UTC offset is also attached, giving\n        'YYYY-MM-DD HH:MM:SS.mmmmmm+HH:MM' or 'YYYY-MM-DD HH:MM:SS+HH:MM'.\n\n        Optional argument sep specifies the separator between date and\n        time, default 'T'.\n        ");
      var1.setline(1751);
      PyObject var3 = PyString.fromInterned("%04d-%02d-%02d%c")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_year"), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day"), var1.getlocal(1)}))._add(var1.getglobal("_format_time").__call__(var2, var1.getlocal(0).__getattr__("_hour"), var1.getlocal(0).__getattr__("_minute"), var1.getlocal(0).__getattr__("_second"), var1.getlocal(0).__getattr__("_microsecond")));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1754);
      var3 = var1.getlocal(0).__getattr__("_utcoffset").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(1755);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1756);
         var3 = var1.getlocal(3);
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         PyString var6;
         if (var10000.__nonzero__()) {
            var1.setline(1757);
            var6 = PyString.fromInterned("-");
            var1.setlocal(4, var6);
            var3 = null;
            var1.setline(1758);
            var3 = var1.getlocal(3).__neg__();
            var1.setlocal(3, var3);
            var3 = null;
         } else {
            var1.setline(1760);
            var6 = PyString.fromInterned("+");
            var1.setlocal(4, var6);
            var3 = null;
         }

         var1.setline(1761);
         var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(60));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(1762);
         var3 = var1.getlocal(2);
         var3 = var3._iadd(PyString.fromInterned("%s%02d:%02d")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)})));
         var1.setlocal(2, var3);
      }

      var1.setline(1763);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$145(PyFrame var1, ThreadState var2) {
      var1.setline(1766);
      PyString.fromInterned("Convert to formal string, for repr().");
      var1.setline(1767);
      PyList var3 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("_year"), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day"), var1.getlocal(0).__getattr__("_hour"), var1.getlocal(0).__getattr__("_minute"), var1.getlocal(0).__getattr__("_second"), var1.getlocal(0).__getattr__("_microsecond")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1769);
      PyObject var4 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
      PyObject var10000 = var4._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1770);
         var1.getlocal(1).__delitem__((PyObject)Py.newInteger(-1));
      }

      var1.setline(1771);
      var4 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
      var10000 = var4._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1772);
         var1.getlocal(1).__delitem__((PyObject)Py.newInteger(-1));
      }

      var1.setline(1773);
      var4 = PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("str"), var1.getlocal(1)));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1774);
      var4 = PyString.fromInterned("%s(%s)")._mod(new PyTuple(new PyObject[]{PyString.fromInterned("datetime.")._add(var1.getlocal(0).__getattr__("__class__").__getattr__("__name__")), var1.getlocal(2)}));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(1775);
      var4 = var1.getlocal(0).__getattr__("_tzinfo");
      var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1776);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var4 = var1.getlocal(2).__getslice__(Py.newInteger(-1), (PyObject)null, (PyObject)null);
            var10000 = var4._eq(PyString.fromInterned(")"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }

         var1.setline(1777);
         var4 = var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)._add(PyString.fromInterned(", tzinfo=%r")._mod(var1.getlocal(0).__getattr__("_tzinfo")))._add(PyString.fromInterned(")"));
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(1778);
      var4 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject __str__$146(PyFrame var1, ThreadState var2) {
      var1.setline(1781);
      PyString.fromInterned("Convert to string, for str().");
      var1.setline(1782);
      PyObject var10000 = var1.getlocal(0).__getattr__("isoformat");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned(" ")};
      String[] var4 = new String[]{"sep"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject strptime$147(PyFrame var1, ThreadState var2) {
      var1.setline(1786);
      PyString.fromInterned("string, format -> new datetime parsed from a string (like time.strptime()).");
      var1.setline(1787);
      String[] var3 = new String[]{"_strptime"};
      PyObject[] var6 = imp.importFrom("_strptime", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(1791);
      PyObject var7 = var1.getlocal(3).__call__(var2, var1.getlocal(1), var1.getlocal(2));
      PyObject[] var8 = Py.unpackSequence(var7, 2);
      PyObject var5 = var8[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(1792);
      PyObject var10000 = var1.getlocal(0);
      var6 = Py.EmptyObjects;
      String[] var9 = new String[0];
      var10000 = var10000._callextra(var6, var9, var1.getlocal(4).__getslice__(Py.newInteger(0), Py.newInteger(6), (PyObject)null)._add(new PyTuple(new PyObject[]{var1.getlocal(5)})), (PyObject)null);
      var3 = null;
      var7 = var10000;
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject utcoffset$148(PyFrame var1, ThreadState var2) {
      var1.setline(1796);
      PyString.fromInterned("Return the timezone offset in minutes east of UTC (negative west of\n        UTC).");
      var1.setline(1797);
      PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1798);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1799);
         PyObject var4 = var1.getlocal(0).__getattr__("_tzinfo").__getattr__("utcoffset").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1800);
         var4 = var1.getglobal("_check_utc_offset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utcoffset"), (PyObject)var1.getlocal(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1801);
         var4 = var1.getlocal(1);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1802);
            var10000 = var1.getglobal("timedelta");
            PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
            String[] var5 = new String[]{"minutes"};
            var10000 = var10000.__call__(var2, var6, var5);
            var4 = null;
            var4 = var10000;
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(1803);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _utcoffset$149(PyFrame var1, ThreadState var2) {
      var1.setline(1807);
      PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1808);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1809);
         PyObject var4 = var1.getlocal(0).__getattr__("_tzinfo").__getattr__("utcoffset").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1810);
         var4 = var1.getglobal("_check_utc_offset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utcoffset"), (PyObject)var1.getlocal(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1811);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject tzname$150(PyFrame var1, ThreadState var2) {
      var1.setline(1819);
      PyString.fromInterned("Return the timezone name.\n\n        Note that the name is 100% informational -- there's no requirement that\n        it mean anything in particular. For example, \"GMT\", \"UTC\", \"-500\",\n        \"-5:00\", \"EDT\", \"US/Eastern\", \"America/New York\" are all valid replies.\n        ");
      var1.setline(1820);
      PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1821);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1822);
         PyObject var4 = var1.getlocal(0).__getattr__("_tzinfo").__getattr__("tzname").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1823);
         var1.getglobal("_check_tzname").__call__(var2, var1.getlocal(1));
         var1.setline(1824);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject dst$151(PyFrame var1, ThreadState var2) {
      var1.setline(1834);
      PyString.fromInterned("Return 0 if DST is not in effect, or the DST offset (in minutes\n        eastward) if DST is in effect.\n\n        This is purely informational; the DST offset has already been added to\n        the UTC offset returned by utcoffset() if applicable, so there's no\n        need to consult dst() unless you're interested in displaying the DST\n        info.\n        ");
      var1.setline(1835);
      PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1836);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1837);
         PyObject var4 = var1.getlocal(0).__getattr__("_tzinfo").__getattr__("dst").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1838);
         var4 = var1.getglobal("_check_utc_offset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dst"), (PyObject)var1.getlocal(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1839);
         var4 = var1.getlocal(1);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1840);
            var10000 = var1.getglobal("timedelta");
            PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
            String[] var5 = new String[]{"minutes"};
            var10000 = var10000.__call__(var2, var6, var5);
            var4 = null;
            var4 = var10000;
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(1841);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _dst$152(PyFrame var1, ThreadState var2) {
      var1.setline(1845);
      PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1846);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1847);
         PyObject var4 = var1.getlocal(0).__getattr__("_tzinfo").__getattr__("dst").__call__(var2, var1.getlocal(0));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1848);
         var4 = var1.getglobal("_check_utc_offset").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dst"), (PyObject)var1.getlocal(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1849);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __eq__$153(PyFrame var1, ThreadState var2) {
      var1.setline(1854);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("datetime")).__nonzero__()) {
         var1.setline(1855);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         var10000 = var3._eq(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1856);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1857);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1859);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __ne__$154(PyFrame var1, ThreadState var2) {
      var1.setline(1862);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("datetime")).__nonzero__()) {
         var1.setline(1863);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         var10000 = var3._ne(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1864);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1865);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1867);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject __le__$155(PyFrame var1, ThreadState var2) {
      var1.setline(1870);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("datetime")).__nonzero__()) {
         var1.setline(1871);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         var10000 = var3._le(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1872);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1873);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1875);
            var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __lt__$156(PyFrame var1, ThreadState var2) {
      var1.setline(1878);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("datetime")).__nonzero__()) {
         var1.setline(1879);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         var10000 = var3._lt(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1880);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1881);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1883);
            var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __ge__$157(PyFrame var1, ThreadState var2) {
      var1.setline(1886);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("datetime")).__nonzero__()) {
         var1.setline(1887);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         var10000 = var3._ge(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1888);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1889);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1891);
            var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject __gt__$158(PyFrame var1, ThreadState var2) {
      var1.setline(1894);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("datetime")).__nonzero__()) {
         var1.setline(1895);
         var3 = var1.getlocal(0).__getattr__("_cmp").__call__(var2, var1.getlocal(1));
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1896);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("timetuple"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("date")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(1897);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1899);
            var1.getglobal("_cmperror").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _cmp$159(PyFrame var1, ThreadState var2) {
      var1.setline(1902);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("datetime")).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(1903);
         PyObject var3 = var1.getlocal(0).__getattr__("_tzinfo");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(1904);
         var3 = var1.getlocal(1).__getattr__("_tzinfo");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1905);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var1.setlocal(5, var3);
         var1.setline(1907);
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1908);
            var3 = var1.getglobal("True");
            var1.setlocal(6, var3);
            var3 = null;
         } else {
            var1.setline(1910);
            var3 = var1.getlocal(2);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1911);
               var3 = var1.getlocal(0).__getattr__("_utcoffset").__call__(var2);
               var1.setlocal(4, var3);
               var3 = null;
            }

            var1.setline(1912);
            var3 = var1.getlocal(3);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1913);
               var3 = var1.getlocal(1).__getattr__("_utcoffset").__call__(var2);
               var1.setlocal(5, var3);
               var3 = null;
            }

            var1.setline(1914);
            var3 = var1.getlocal(4);
            var10000 = var3._eq(var1.getlocal(5));
            var3 = null;
            var3 = var10000;
            var1.setlocal(6, var3);
            var3 = null;
         }

         var1.setline(1916);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(1917);
            var3 = var1.getglobal("_cmp").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_year"), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day"), var1.getlocal(0).__getattr__("_hour"), var1.getlocal(0).__getattr__("_minute"), var1.getlocal(0).__getattr__("_second"), var1.getlocal(0).__getattr__("_microsecond")})), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1).__getattr__("_year"), var1.getlocal(1).__getattr__("_month"), var1.getlocal(1).__getattr__("_day"), var1.getlocal(1).__getattr__("_hour"), var1.getlocal(1).__getattr__("_minute"), var1.getlocal(1).__getattr__("_second"), var1.getlocal(1).__getattr__("_microsecond")})));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1923);
            PyObject var4 = var1.getlocal(4);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getlocal(5);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(1924);
               throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("can't compare offset-naive and offset-aware datetimes")));
            } else {
               var1.setline(1926);
               var4 = var1.getlocal(0)._sub(var1.getlocal(1));
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(1927);
               var4 = var1.getlocal(7).__getattr__("days");
               var10000 = var4._lt(Py.newInteger(0));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1928);
                  PyInteger var6 = Py.newInteger(-1);
                  var1.f_lasti = -1;
                  return var6;
               } else {
                  var1.setline(1929);
                  Object var7 = var1.getlocal(7);
                  if (((PyObject)var7).__nonzero__()) {
                     var7 = Py.newInteger(1);
                  }

                  if (!((PyObject)var7).__nonzero__()) {
                     var7 = Py.newInteger(0);
                  }

                  Object var5 = var7;
                  var1.f_lasti = -1;
                  return (PyObject)var5;
               }
            }
         }
      }
   }

   public PyObject __add__$160(PyFrame var1, ThreadState var2) {
      var1.setline(1932);
      PyString.fromInterned("Add a datetime and a timedelta.");
      var1.setline(1933);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__not__().__nonzero__()) {
         var1.setline(1934);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1935);
         PyObject var10000 = var1.getglobal("_tmxxx");
         PyObject[] var4 = new PyObject[]{var1.getlocal(0).__getattr__("_year"), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day")._add(var1.getlocal(1).__getattr__("days")), var1.getlocal(0).__getattr__("_hour"), var1.getlocal(0).__getattr__("_minute"), var1.getlocal(0).__getattr__("_second")._add(var1.getlocal(1).__getattr__("seconds")), var1.getlocal(0).__getattr__("_microsecond")._add(var1.getlocal(1).__getattr__("microseconds"))};
         PyObject var6 = var10000.__call__(var2, var4);
         var1.setlocal(2, var6);
         var4 = null;
         var1.setline(1942);
         var1.getlocal(0).__getattr__("_checkOverflow").__call__(var2, var1.getlocal(2).__getattr__("year"));
         var1.setline(1943);
         var10000 = var1.getglobal("datetime");
         var4 = new PyObject[]{var1.getlocal(2).__getattr__("year"), var1.getlocal(2).__getattr__("month"), var1.getlocal(2).__getattr__("day"), var1.getlocal(2).__getattr__("hour"), var1.getlocal(2).__getattr__("minute"), var1.getlocal(2).__getattr__("second"), var1.getlocal(2).__getattr__("microsecond"), var1.getlocal(0).__getattr__("_tzinfo")};
         String[] var5 = new String[]{"tzinfo"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         var6 = var10000;
         var1.setlocal(3, var6);
         var4 = null;
         var1.setline(1946);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __sub__$161(PyFrame var1, ThreadState var2) {
      var1.setline(1951);
      PyString.fromInterned("Subtract two datetimes, or a datetime and a timedelta.");
      var1.setline(1952);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("datetime")).__not__().__nonzero__()) {
         var1.setline(1953);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("timedelta")).__nonzero__()) {
            var1.setline(1954);
            var3 = var1.getlocal(0)._add(var1.getlocal(1).__neg__());
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1955);
            var3 = var1.getglobal("NotImplemented");
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(1957);
         PyObject var4 = var1.getlocal(0).__getattr__("toordinal").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(1958);
         var4 = var1.getlocal(1).__getattr__("toordinal").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(1959);
         var4 = var1.getlocal(0).__getattr__("_second")._add(var1.getlocal(0).__getattr__("_minute")._mul(Py.newInteger(60)))._add(var1.getlocal(0).__getattr__("_hour")._mul(Py.newInteger(3600)));
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(1960);
         var4 = var1.getlocal(1).__getattr__("_second")._add(var1.getlocal(1).__getattr__("_minute")._mul(Py.newInteger(60)))._add(var1.getlocal(1).__getattr__("_hour")._mul(Py.newInteger(3600)));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(1961);
         var4 = var1.getglobal("timedelta").__call__(var2, var1.getlocal(2)._sub(var1.getlocal(3)), var1.getlocal(4)._sub(var1.getlocal(5)), var1.getlocal(0).__getattr__("_microsecond")._sub(var1.getlocal(1).__getattr__("_microsecond")));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(1964);
         var4 = var1.getlocal(0).__getattr__("_tzinfo");
         PyObject var10000 = var4._is(var1.getlocal(1).__getattr__("_tzinfo"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1965);
            var3 = var1.getlocal(6);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(1966);
            var4 = var1.getlocal(0).__getattr__("_utcoffset").__call__(var2);
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(1967);
            var4 = var1.getlocal(1).__getattr__("_utcoffset").__call__(var2);
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(1968);
            var4 = var1.getlocal(7);
            var10000 = var4._eq(var1.getlocal(8));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1969);
               var3 = var1.getlocal(6);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(1970);
               var4 = var1.getlocal(7);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               if (!var10000.__nonzero__()) {
                  var4 = var1.getlocal(8);
                  var10000 = var4._is(var1.getglobal("None"));
                  var4 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(1971);
                  throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("can't subtract offset-naive and offset-aware datetimes")));
               } else {
                  var1.setline(1972);
                  var10000 = var1.getlocal(6);
                  PyObject var10001 = var1.getglobal("timedelta");
                  PyObject[] var6 = new PyObject[]{var1.getlocal(8)._sub(var1.getlocal(7))};
                  String[] var5 = new String[]{"minutes"};
                  var10001 = var10001.__call__(var2, var6, var5);
                  var4 = null;
                  var3 = var10000._add(var10001);
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject __hash__$162(PyFrame var1, ThreadState var2) {
      var1.setline(1975);
      PyObject var3 = var1.getlocal(0).__getattr__("_hashcode");
      PyObject var10000 = var3._eq(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1976);
         var3 = var1.getlocal(0).__getattr__("_utcoffset").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(1977);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1978);
            var3 = var1.getglobal("hash").__call__(var2, var1.getlocal(0).__getattr__("_getstate").__call__(var2).__getitem__(Py.newInteger(0)));
            var1.getlocal(0).__setattr__("_hashcode", var3);
            var3 = null;
         } else {
            var1.setline(1980);
            var3 = var1.getglobal("_ymd2ord").__call__(var2, var1.getlocal(0).__getattr__("year"), var1.getlocal(0).__getattr__("month"), var1.getlocal(0).__getattr__("day"));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(1981);
            var3 = var1.getlocal(0).__getattr__("hour")._mul(Py.newInteger(3600))._add(var1.getlocal(0).__getattr__("minute")._sub(var1.getlocal(1))._mul(Py.newInteger(60)))._add(var1.getlocal(0).__getattr__("second"));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(1982);
            var3 = var1.getglobal("hash").__call__(var2, var1.getglobal("timedelta").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("microsecond")));
            var1.getlocal(0).__setattr__("_hashcode", var3);
            var3 = null;
         }
      }

      var1.setline(1983);
      var3 = var1.getlocal(0).__getattr__("_hashcode");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _getstate$163(PyFrame var1, ThreadState var2) {
      var1.setline(1988);
      PyObject var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_year"), (PyObject)Py.newInteger(256));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(1989);
      var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_microsecond"), (PyObject)Py.newInteger(256));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(1990);
      var3 = var1.getglobal("divmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(256));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(1991);
      PyObject var10000 = var1.getglobal("_struct").__getattr__("pack");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("10B"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("_month"), var1.getlocal(0).__getattr__("_day"), var1.getlocal(0).__getattr__("_hour"), var1.getlocal(0).__getattr__("_minute"), var1.getlocal(0).__getattr__("_second"), var1.getlocal(5), var1.getlocal(3), var1.getlocal(4)};
      var3 = var10000.__call__(var2, var6);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(1994);
      var3 = var1.getlocal(0).__getattr__("_tzinfo");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyTuple var7;
      if (var10000.__nonzero__()) {
         var1.setline(1995);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(6)});
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(1997);
         var7 = new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(0).__getattr__("_tzinfo")});
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject _datetime__setstate$164(PyFrame var1, ThreadState var2) {
      var1.setline(2000);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("_tzinfo_class")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(2001);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bad tzinfo state arg")));
      } else {
         var1.setline(2002);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(1))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(2))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(3))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(4))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(5))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(6))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(7))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(8))), var1.getglobal("ord").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(9)))});
         PyObject[] var4 = Py.unpackSequence(var6, 10);
         PyObject var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[2];
         var1.getlocal(0).__setattr__("_month", var5);
         var5 = null;
         var5 = var4[3];
         var1.getlocal(0).__setattr__("_day", var5);
         var5 = null;
         var5 = var4[4];
         var1.getlocal(0).__setattr__("_hour", var5);
         var5 = null;
         var5 = var4[5];
         var1.getlocal(0).__setattr__("_minute", var5);
         var5 = null;
         var5 = var4[6];
         var1.getlocal(0).__setattr__("_second", var5);
         var5 = null;
         var5 = var4[7];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[8];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[9];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
         var1.setline(2007);
         var3 = var1.getlocal(3)._mul(Py.newInteger(256))._add(var1.getlocal(4));
         var1.getlocal(0).__setattr__("_year", var3);
         var3 = null;
         var1.setline(2008);
         var3 = var1.getlocal(5)._lshift(Py.newInteger(8))._or(var1.getlocal(6))._lshift(Py.newInteger(8))._or(var1.getlocal(7));
         var1.getlocal(0).__setattr__("_microsecond", var3);
         var3 = null;
         var1.setline(2009);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("_tzinfo", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __reduce__$165(PyFrame var1, ThreadState var2) {
      var1.setline(2012);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), var1.getlocal(0).__getattr__("_getstate").__call__(var2)});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __tojava__$166(PyFrame var1, ThreadState var2) {
      var1.setline(2016);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("Calendar"), var1.getglobal("Timestamp"), var1.getglobal("Object")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2017);
         var3 = var1.getglobal("Py").__getattr__("NoConversion");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(2018);
         PyObject var4 = var1.getglobal("_make_java_calendar").__call__(var2, var1.getlocal(0));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(2019);
         var4 = var1.getlocal(2);
         var10000 = var4._eq(var1.getglobal("Py").__getattr__("NoConversion"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(2020);
            var3 = var1.getglobal("Py").__getattr__("NoConversion");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(2021);
            var10000 = var1.getlocal(2).__getattr__("set");
            PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("year"), var1.getlocal(0).__getattr__("month")._sub(Py.newInteger(1)), var1.getlocal(0).__getattr__("day"), var1.getlocal(0).__getattr__("hour"), var1.getlocal(0).__getattr__("minute"), var1.getlocal(0).__getattr__("second")};
            var10000.__call__(var2, var5);
            var1.setline(2024);
            var4 = var1.getlocal(1);
            var10000 = var4._eq(var1.getglobal("Calendar"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(2025);
               var1.getlocal(2).__getattr__("set").__call__(var2, var1.getglobal("Calendar").__getattr__("MILLISECOND"), var1.getlocal(0).__getattr__("microsecond")._floordiv(Py.newInteger(1000)));
               var1.setline(2026);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(2028);
               var4 = var1.getglobal("Timestamp").__call__(var2, var1.getlocal(2).__getattr__("getTimeInMillis").__call__(var2));
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(2029);
               var1.getlocal(3).__getattr__("setNanos").__call__(var2, var1.getlocal(0).__getattr__("microsecond")._mul(Py.newInteger(1000)));
               var1.setline(2030);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject _isoweek1monday$167(PyFrame var1, ThreadState var2) {
      var1.setline(2041);
      PyInteger var3 = Py.newInteger(3);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(2042);
      PyObject var4 = var1.getglobal("_ymd2ord").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(2043);
      var4 = var1.getlocal(2)._add(Py.newInteger(6))._mod(Py.newInteger(7));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(2044);
      var4 = var1.getlocal(2)._sub(var1.getlocal(3));
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(2045);
      var4 = var1.getlocal(3);
      PyObject var10000 = var4._gt(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(2046);
         var4 = var1.getlocal(4);
         var4 = var4._iadd(Py.newInteger(7));
         var1.setlocal(4, var4);
      }

      var1.setline(2047);
      var4 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var4;
   }

   public datetime$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"cal"};
      _make_java_utc_calendar$1 = Py.newCode(0, var2, var1, "_make_java_utc_calendar", 41, false, false, self, 1, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      _make_java_default_calendar$2 = Py.newCode(0, var2, var1, "_make_java_default_calendar", 46, false, false, self, 2, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"d", "tzinfo", "cal", "tzname", "tz"};
      _make_java_calendar$3 = Py.newCode(1, var2, var1, "_make_java_calendar", 49, false, false, self, 3, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"x", "y"};
      _cmp$4 = Py.newCode(2, var2, var1, "_cmp", 70, false, false, self, 4, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"x"};
      _round$5 = Py.newCode(1, var2, var1, "_round", 73, false, false, self, 5, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"year"};
      _is_leap$6 = Py.newCode(1, var2, var1, "_is_leap", 98, false, false, self, 6, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"year", "y"};
      _days_before_year$7 = Py.newCode(1, var2, var1, "_days_before_year", 102, false, false, self, 7, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"year", "month"};
      _days_in_month$8 = Py.newCode(2, var2, var1, "_days_in_month", 107, false, false, self, 8, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"year", "month"};
      _days_before_month$9 = Py.newCode(2, var2, var1, "_days_before_month", 114, false, false, self, 9, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"year", "month", "day", "dim"};
      _ymd2ord$10 = Py.newCode(3, var2, var1, "_ymd2ord", 119, false, false, self, 10, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"n", "n400", "year", "n100", "n4", "n1", "leapyear", "month", "preceding"};
      _ord2ymd$11 = Py.newCode(1, var2, var1, "_ord2ymd", 144, false, false, self, 11, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"y", "m", "d", "hh", "mm", "ss", "dstflag", "wday", "dnum"};
      _build_struct_time$12 = Py.newCode(7, var2, var1, "_build_struct_time", 212, false, false, self, 12, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"hh", "mm", "ss", "us", "result"};
      _format_time$13 = Py.newCode(4, var2, var1, "_format_time", 217, false, false, self, 13, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"object", "format", "timetuple", "year", "freplace", "zreplace", "Zreplace", "newformat", "push", "i", "n", "ch", "offset", "sign", "h", "m", "s"};
      _wrap_strftime$14 = Py.newCode(3, var2, var1, "_wrap_strftime", 225, false, false, self, 14, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"name"};
      _check_tzname$15 = Py.newCode(1, var2, var1, "_check_tzname", 286, false, false, self, 15, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"name", "offset", "days", "seconds", "minutes"};
      _check_utc_offset$16 = Py.newCode(2, var2, var1, "_check_utc_offset", 297, false, false, self, 16, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"value"};
      _check_int_field$17 = Py.newCode(1, var2, var1, "_check_int_field", 318, false, false, self, 17, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"year", "month", "day", "dim"};
      _check_date_fields$18 = Py.newCode(3, var2, var1, "_check_date_fields", 333, false, false, self, 18, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"hour", "minute", "second", "microsecond"};
      _check_time_fields$19 = Py.newCode(4, var2, var1, "_check_time_fields", 346, false, false, self, 19, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"tz"};
      _check_tzinfo_arg$20 = Py.newCode(1, var2, var1, "_check_tzinfo_arg", 361, false, false, self, 20, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"x", "y"};
      _cmperror$21 = Py.newCode(2, var2, var1, "_cmperror", 389, false, false, self, 21, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      _tmxxx$22 = Py.newCode(0, var2, var1, "_tmxxx", 402, false, false, self, 22, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"self", "year", "month", "day", "hour", "minute", "second", "microsecond", "carry", "dim"};
      __init__$23 = Py.newCode(8, var2, var1, "__init__", 406, false, false, self, 23, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      timedelta$24 = Py.newCode(0, var2, var1, "timedelta", 463, false, false, self, 24, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"cls", "days", "seconds", "microseconds", "milliseconds", "minutes", "hours", "weeks", "d", "s", "us", "dayfrac", "daysecondsfrac", "daysecondswhole", "secondsfrac", "usdouble", "self"};
      __new__$25 = Py.newCode(8, var2, var1, "__new__", 482, false, false, self, 25, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __repr__$26 = Py.newCode(1, var2, var1, "__repr__", 584, false, false, self, 26, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "mm", "ss", "hh", "s", "plural"};
      __str__$27 = Py.newCode(1, var2, var1, "__str__", 596, false, false, self, 27, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"n"};
      plural$28 = Py.newCode(1, var2, var1, "plural", 601, false, false, self, 28, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      total_seconds$29 = Py.newCode(1, var2, var1, "total_seconds", 608, false, false, self, 29, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      days$30 = Py.newCode(1, var2, var1, "days", 614, false, false, self, 30, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      seconds$31 = Py.newCode(1, var2, var1, "seconds", 619, false, false, self, 31, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      microseconds$32 = Py.newCode(1, var2, var1, "microseconds", 624, false, false, self, 32, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __add__$33 = Py.newCode(2, var2, var1, "__add__", 629, false, false, self, 33, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __sub__$34 = Py.newCode(2, var2, var1, "__sub__", 640, false, false, self, 34, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __rsub__$35 = Py.newCode(2, var2, var1, "__rsub__", 649, false, false, self, 35, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __neg__$36 = Py.newCode(1, var2, var1, "__neg__", 654, false, false, self, 36, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __pos__$37 = Py.newCode(1, var2, var1, "__pos__", 661, false, false, self, 37, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __abs__$38 = Py.newCode(1, var2, var1, "__abs__", 664, false, false, self, 38, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __mul__$39 = Py.newCode(2, var2, var1, "__mul__", 670, false, false, self, 39, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      _to_microseconds$40 = Py.newCode(1, var2, var1, "_to_microseconds", 681, false, false, self, 40, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other", "usec"};
      __div__$41 = Py.newCode(2, var2, var1, "__div__", 685, false, false, self, 41, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __eq__$42 = Py.newCode(2, var2, var1, "__eq__", 695, false, false, self, 42, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __ne__$43 = Py.newCode(2, var2, var1, "__ne__", 701, false, false, self, 43, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __le__$44 = Py.newCode(2, var2, var1, "__le__", 707, false, false, self, 44, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __lt__$45 = Py.newCode(2, var2, var1, "__lt__", 713, false, false, self, 45, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __ge__$46 = Py.newCode(2, var2, var1, "__ge__", 719, false, false, self, 46, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __gt__$47 = Py.newCode(2, var2, var1, "__gt__", 725, false, false, self, 47, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      _cmp$48 = Py.newCode(2, var2, var1, "_cmp", 731, false, false, self, 48, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __hash__$49 = Py.newCode(1, var2, var1, "__hash__", 735, false, false, self, 49, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __nonzero__$50 = Py.newCode(1, var2, var1, "__nonzero__", 740, false, false, self, 50, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      _getstate$51 = Py.newCode(1, var2, var1, "_getstate", 747, false, false, self, 51, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __reduce__$52 = Py.newCode(1, var2, var1, "__reduce__", 750, false, false, self, 52, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      date$53 = Py.newCode(0, var2, var1, "date", 758, false, false, self, 53, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"cls", "year", "month", "day", "self"};
      __new__$54 = Py.newCode(4, var2, var1, "__new__", 788, false, false, self, 54, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"cls", "t", "y", "m", "d", "hh", "mm", "ss", "weekday", "jday", "dst"};
      fromtimestamp$55 = Py.newCode(2, var2, var1, "fromtimestamp", 812, false, false, self, 55, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"cls", "t"};
      today$56 = Py.newCode(1, var2, var1, "today", 818, false, false, self, 56, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"cls", "n", "y", "m", "d"};
      fromordinal$57 = Py.newCode(2, var2, var1, "fromordinal", 824, false, false, self, 57, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __repr__$58 = Py.newCode(1, var2, var1, "__repr__", 836, false, false, self, 58, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "weekday"};
      ctime$59 = Py.newCode(1, var2, var1, "ctime", 857, false, false, self, 59, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "fmt"};
      strftime$60 = Py.newCode(2, var2, var1, "strftime", 865, false, false, self, 60, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "fmt"};
      __format__$61 = Py.newCode(2, var2, var1, "__format__", 869, false, false, self, 61, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      isoformat$62 = Py.newCode(1, var2, var1, "isoformat", 877, false, false, self, 62, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      year$63 = Py.newCode(1, var2, var1, "year", 891, false, false, self, 63, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      month$64 = Py.newCode(1, var2, var1, "month", 896, false, false, self, 64, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      day$65 = Py.newCode(1, var2, var1, "day", 901, false, false, self, 65, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      timetuple$66 = Py.newCode(1, var2, var1, "timetuple", 908, false, false, self, 66, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      toordinal$67 = Py.newCode(1, var2, var1, "toordinal", 913, false, false, self, 67, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "year", "month", "day"};
      replace$68 = Py.newCode(4, var2, var1, "replace", 921, false, false, self, 68, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __eq__$69 = Py.newCode(2, var2, var1, "__eq__", 933, false, false, self, 69, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __ne__$70 = Py.newCode(2, var2, var1, "__ne__", 941, false, false, self, 70, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __le__$71 = Py.newCode(2, var2, var1, "__le__", 949, false, false, self, 71, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __lt__$72 = Py.newCode(2, var2, var1, "__lt__", 957, false, false, self, 72, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __ge__$73 = Py.newCode(2, var2, var1, "__ge__", 965, false, false, self, 73, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __gt__$74 = Py.newCode(2, var2, var1, "__gt__", 973, false, false, self, 74, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other", "y", "m", "d", "y2", "m2", "d2"};
      _cmp$75 = Py.newCode(2, var2, var1, "_cmp", 981, false, false, self, 75, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __hash__$76 = Py.newCode(1, var2, var1, "__hash__", 987, false, false, self, 76, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "year"};
      _checkOverflow$77 = Py.newCode(2, var2, var1, "_checkOverflow", 995, false, false, self, 77, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other", "t", "result"};
      __add__$78 = Py.newCode(2, var2, var1, "__add__", 1000, false, false, self, 78, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other", "days1", "days2"};
      __sub__$79 = Py.newCode(2, var2, var1, "__sub__", 1013, false, false, self, 79, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      weekday$80 = Py.newCode(1, var2, var1, "weekday", 1023, false, false, self, 80, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      isoweekday$81 = Py.newCode(1, var2, var1, "isoweekday", 1029, false, false, self, 81, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "year", "week1monday", "today", "week", "day"};
      isocalendar$82 = Py.newCode(1, var2, var1, "isocalendar", 1034, false, false, self, 82, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "yhi", "ylo"};
      _getstate$83 = Py.newCode(1, var2, var1, "_getstate", 1063, false, false, self, 83, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "string", "yhi", "ylo"};
      _date__setstate$84 = Py.newCode(2, var2, var1, "_date__setstate", 1067, false, false, self, 84, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __reduce__$85 = Py.newCode(1, var2, var1, "__reduce__", 1072, false, false, self, 85, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "java_class", "calendar"};
      __tojava__$86 = Py.newCode(2, var2, var1, "__tojava__", 1076, false, false, self, 86, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      tzinfo$87 = Py.newCode(0, var2, var1, "tzinfo", 1095, false, false, self, 87, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"self", "dt"};
      tzname$88 = Py.newCode(2, var2, var1, "tzname", 1102, false, false, self, 88, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "dt"};
      utcoffset$89 = Py.newCode(2, var2, var1, "utcoffset", 1106, false, false, self, 89, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "dt"};
      dst$90 = Py.newCode(2, var2, var1, "dst", 1110, false, false, self, 90, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "dt", "dtoff", "dtdst", "delta"};
      fromutc$91 = Py.newCode(2, var2, var1, "fromutc", 1118, false, false, self, 91, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "getinitargs", "args", "getstate", "state"};
      __reduce__$92 = Py.newCode(1, var2, var1, "__reduce__", 1150, false, false, self, 92, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      time$93 = Py.newCode(0, var2, var1, "time", 1168, false, false, self, 93, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"cls", "hour", "minute", "second", "microsecond", "tzinfo", "self"};
      __new__$94 = Py.newCode(6, var2, var1, "__new__", 1193, false, false, self, 94, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      hour$95 = Py.newCode(1, var2, var1, "hour", 1221, false, false, self, 95, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      minute$96 = Py.newCode(1, var2, var1, "minute", 1226, false, false, self, 96, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      second$97 = Py.newCode(1, var2, var1, "second", 1231, false, false, self, 97, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      microsecond$98 = Py.newCode(1, var2, var1, "microsecond", 1236, false, false, self, 98, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      tzinfo$99 = Py.newCode(1, var2, var1, "tzinfo", 1241, false, false, self, 99, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __eq__$100 = Py.newCode(2, var2, var1, "__eq__", 1250, false, false, self, 100, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __ne__$101 = Py.newCode(2, var2, var1, "__ne__", 1256, false, false, self, 101, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __le__$102 = Py.newCode(2, var2, var1, "__le__", 1262, false, false, self, 102, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __lt__$103 = Py.newCode(2, var2, var1, "__lt__", 1268, false, false, self, 103, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __ge__$104 = Py.newCode(2, var2, var1, "__ge__", 1274, false, false, self, 104, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __gt__$105 = Py.newCode(2, var2, var1, "__gt__", 1280, false, false, self, 105, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other", "mytz", "ottz", "myoff", "otoff", "base_compare", "myhhmm", "othhmm"};
      _cmp$106 = Py.newCode(2, var2, var1, "_cmp", 1286, false, false, self, 106, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "tzoff", "h", "m"};
      __hash__$107 = Py.newCode(1, var2, var1, "__hash__", 1311, false, false, self, 107, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "sep", "off", "sign", "hh", "mm"};
      _tzstr$108 = Py.newCode(2, var2, var1, "_tzstr", 1327, false, false, self, 108, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "s"};
      __repr__$109 = Py.newCode(1, var2, var1, "__repr__", 1341, false, false, self, 109, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "s", "tz"};
      isoformat$110 = Py.newCode(1, var2, var1, "isoformat", 1356, false, false, self, 110, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "fmt", "timetuple"};
      strftime$111 = Py.newCode(2, var2, var1, "strftime", 1371, false, false, self, 111, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "fmt"};
      __format__$112 = Py.newCode(2, var2, var1, "__format__", 1382, false, false, self, 112, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "offset"};
      utcoffset$113 = Py.newCode(1, var2, var1, "utcoffset", 1392, false, false, self, 113, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "offset"};
      _utcoffset$114 = Py.newCode(1, var2, var1, "_utcoffset", 1404, false, false, self, 114, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "name"};
      tzname$115 = Py.newCode(1, var2, var1, "tzname", 1411, false, false, self, 115, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "offset"};
      dst$116 = Py.newCode(1, var2, var1, "dst", 1424, false, false, self, 116, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "offset"};
      _dst$117 = Py.newCode(1, var2, var1, "_dst", 1442, false, false, self, 117, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "hour", "minute", "second", "microsecond", "tzinfo"};
      replace$118 = Py.newCode(6, var2, var1, "replace", 1449, false, false, self, 118, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "offset"};
      __nonzero__$119 = Py.newCode(1, var2, var1, "__nonzero__", 1464, false, false, self, 119, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "us2", "us3", "us1", "basestate"};
      _getstate$120 = Py.newCode(1, var2, var1, "_getstate", 1472, false, false, self, 120, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "string", "tzinfo", "us1", "us2", "us3"};
      _time__setstate$121 = Py.newCode(3, var2, var1, "_time__setstate", 1482, false, false, self, 121, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __reduce__$122 = Py.newCode(1, var2, var1, "__reduce__", 1491, false, false, self, 122, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "java_class", "calendar"};
      __tojava__$123 = Py.newCode(2, var2, var1, "__tojava__", 1495, false, false, self, 123, (String[])null, (String[])null, 0, 12289);
      var2 = new String[0];
      datetime$124 = Py.newCode(0, var2, var1, "datetime", 1523, false, false, self, 124, (String[])null, (String[])null, 0, 12288);
      var2 = new String[]{"cls", "year", "month", "day", "hour", "minute", "second", "microsecond", "tzinfo", "self"};
      __new__$125 = Py.newCode(9, var2, var1, "__new__", 1531, false, false, self, 125, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      hour$126 = Py.newCode(1, var2, var1, "hour", 1557, false, false, self, 126, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      minute$127 = Py.newCode(1, var2, var1, "minute", 1562, false, false, self, 127, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      second$128 = Py.newCode(1, var2, var1, "second", 1567, false, false, self, 128, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      microsecond$129 = Py.newCode(1, var2, var1, "microsecond", 1572, false, false, self, 129, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      tzinfo$130 = Py.newCode(1, var2, var1, "tzinfo", 1577, false, false, self, 130, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"cls", "t", "tz", "converter", "frac", "us", "y", "m", "d", "hh", "mm", "ss", "weekday", "jday", "dst", "result"};
      fromtimestamp$131 = Py.newCode(3, var2, var1, "fromtimestamp", 1582, false, false, self, 131, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"cls", "t", "frac", "us", "y", "m", "d", "hh", "mm", "ss", "weekday", "jday", "dst"};
      utcfromtimestamp$132 = Py.newCode(2, var2, var1, "utcfromtimestamp", 1610, false, false, self, 132, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"cls", "tz", "t"};
      now$133 = Py.newCode(2, var2, var1, "now", 1627, false, false, self, 133, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"cls", "t"};
      utcnow$134 = Py.newCode(1, var2, var1, "utcnow", 1633, false, false, self, 134, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"cls", "date", "time"};
      combine$135 = Py.newCode(3, var2, var1, "combine", 1639, false, false, self, 135, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "dst"};
      timetuple$136 = Py.newCode(1, var2, var1, "timetuple", 1650, false, false, self, 136, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "y", "m", "d", "hh", "mm", "ss", "offset", "tm"};
      utctimetuple$137 = Py.newCode(1, var2, var1, "utctimetuple", 1661, false, false, self, 137, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      date$138 = Py.newCode(1, var2, var1, "date", 1672, false, false, self, 138, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      time$139 = Py.newCode(1, var2, var1, "time", 1676, false, false, self, 139, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      timetz$140 = Py.newCode(1, var2, var1, "timetz", 1680, false, false, self, 140, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "year", "month", "day", "hour", "minute", "second", "microsecond", "tzinfo"};
      replace$141 = Py.newCode(9, var2, var1, "replace", 1685, false, false, self, 141, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "tz", "mytz", "myoffset", "utc"};
      astimezone$142 = Py.newCode(2, var2, var1, "astimezone", 1707, false, false, self, 142, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "weekday"};
      ctime$143 = Py.newCode(1, var2, var1, "ctime", 1729, false, false, self, 143, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "sep", "s", "off", "sign", "hh", "mm"};
      isoformat$144 = Py.newCode(2, var2, var1, "isoformat", 1739, false, false, self, 144, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "L", "s"};
      __repr__$145 = Py.newCode(1, var2, var1, "__repr__", 1765, false, false, self, 145, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __str__$146 = Py.newCode(1, var2, var1, "__str__", 1780, false, false, self, 146, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"cls", "date_string", "format", "_strptime", "struct", "micros"};
      strptime$147 = Py.newCode(3, var2, var1, "strptime", 1784, false, false, self, 147, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "offset"};
      utcoffset$148 = Py.newCode(1, var2, var1, "utcoffset", 1794, false, false, self, 148, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "offset"};
      _utcoffset$149 = Py.newCode(1, var2, var1, "_utcoffset", 1806, false, false, self, 149, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "name"};
      tzname$150 = Py.newCode(1, var2, var1, "tzname", 1813, false, false, self, 150, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "offset"};
      dst$151 = Py.newCode(1, var2, var1, "dst", 1826, false, false, self, 151, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "offset"};
      _dst$152 = Py.newCode(1, var2, var1, "_dst", 1844, false, false, self, 152, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __eq__$153 = Py.newCode(2, var2, var1, "__eq__", 1853, false, false, self, 153, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __ne__$154 = Py.newCode(2, var2, var1, "__ne__", 1861, false, false, self, 154, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __le__$155 = Py.newCode(2, var2, var1, "__le__", 1869, false, false, self, 155, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __lt__$156 = Py.newCode(2, var2, var1, "__lt__", 1877, false, false, self, 156, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __ge__$157 = Py.newCode(2, var2, var1, "__ge__", 1885, false, false, self, 157, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other"};
      __gt__$158 = Py.newCode(2, var2, var1, "__gt__", 1893, false, false, self, 158, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other", "mytz", "ottz", "myoff", "otoff", "base_compare", "diff"};
      _cmp$159 = Py.newCode(2, var2, var1, "_cmp", 1901, false, false, self, 159, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other", "t", "result"};
      __add__$160 = Py.newCode(2, var2, var1, "__add__", 1931, false, false, self, 160, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "other", "days1", "days2", "secs1", "secs2", "base", "myoff", "otoff"};
      __sub__$161 = Py.newCode(2, var2, var1, "__sub__", 1950, false, false, self, 161, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "tzoff", "days", "seconds"};
      __hash__$162 = Py.newCode(1, var2, var1, "__hash__", 1974, false, false, self, 162, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "yhi", "ylo", "us2", "us3", "us1", "basestate"};
      _getstate$163 = Py.newCode(1, var2, var1, "_getstate", 1987, false, false, self, 163, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "string", "tzinfo", "yhi", "ylo", "us1", "us2", "us3"};
      _datetime__setstate$164 = Py.newCode(3, var2, var1, "_datetime__setstate", 1999, false, false, self, 164, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self"};
      __reduce__$165 = Py.newCode(1, var2, var1, "__reduce__", 2011, false, false, self, 165, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"self", "java_class", "calendar", "timestamp"};
      __tojava__$166 = Py.newCode(2, var2, var1, "__tojava__", 2015, false, false, self, 166, (String[])null, (String[])null, 0, 12289);
      var2 = new String[]{"year", "THURSDAY", "firstday", "firstweekday", "week1monday"};
      _isoweek1monday$167 = Py.newCode(1, var2, var1, "_isoweek1monday", 2038, false, false, self, 167, (String[])null, (String[])null, 0, 12289);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new datetime$py("datetime$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(datetime$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._make_java_utc_calendar$1(var2, var3);
         case 2:
            return this._make_java_default_calendar$2(var2, var3);
         case 3:
            return this._make_java_calendar$3(var2, var3);
         case 4:
            return this._cmp$4(var2, var3);
         case 5:
            return this._round$5(var2, var3);
         case 6:
            return this._is_leap$6(var2, var3);
         case 7:
            return this._days_before_year$7(var2, var3);
         case 8:
            return this._days_in_month$8(var2, var3);
         case 9:
            return this._days_before_month$9(var2, var3);
         case 10:
            return this._ymd2ord$10(var2, var3);
         case 11:
            return this._ord2ymd$11(var2, var3);
         case 12:
            return this._build_struct_time$12(var2, var3);
         case 13:
            return this._format_time$13(var2, var3);
         case 14:
            return this._wrap_strftime$14(var2, var3);
         case 15:
            return this._check_tzname$15(var2, var3);
         case 16:
            return this._check_utc_offset$16(var2, var3);
         case 17:
            return this._check_int_field$17(var2, var3);
         case 18:
            return this._check_date_fields$18(var2, var3);
         case 19:
            return this._check_time_fields$19(var2, var3);
         case 20:
            return this._check_tzinfo_arg$20(var2, var3);
         case 21:
            return this._cmperror$21(var2, var3);
         case 22:
            return this._tmxxx$22(var2, var3);
         case 23:
            return this.__init__$23(var2, var3);
         case 24:
            return this.timedelta$24(var2, var3);
         case 25:
            return this.__new__$25(var2, var3);
         case 26:
            return this.__repr__$26(var2, var3);
         case 27:
            return this.__str__$27(var2, var3);
         case 28:
            return this.plural$28(var2, var3);
         case 29:
            return this.total_seconds$29(var2, var3);
         case 30:
            return this.days$30(var2, var3);
         case 31:
            return this.seconds$31(var2, var3);
         case 32:
            return this.microseconds$32(var2, var3);
         case 33:
            return this.__add__$33(var2, var3);
         case 34:
            return this.__sub__$34(var2, var3);
         case 35:
            return this.__rsub__$35(var2, var3);
         case 36:
            return this.__neg__$36(var2, var3);
         case 37:
            return this.__pos__$37(var2, var3);
         case 38:
            return this.__abs__$38(var2, var3);
         case 39:
            return this.__mul__$39(var2, var3);
         case 40:
            return this._to_microseconds$40(var2, var3);
         case 41:
            return this.__div__$41(var2, var3);
         case 42:
            return this.__eq__$42(var2, var3);
         case 43:
            return this.__ne__$43(var2, var3);
         case 44:
            return this.__le__$44(var2, var3);
         case 45:
            return this.__lt__$45(var2, var3);
         case 46:
            return this.__ge__$46(var2, var3);
         case 47:
            return this.__gt__$47(var2, var3);
         case 48:
            return this._cmp$48(var2, var3);
         case 49:
            return this.__hash__$49(var2, var3);
         case 50:
            return this.__nonzero__$50(var2, var3);
         case 51:
            return this._getstate$51(var2, var3);
         case 52:
            return this.__reduce__$52(var2, var3);
         case 53:
            return this.date$53(var2, var3);
         case 54:
            return this.__new__$54(var2, var3);
         case 55:
            return this.fromtimestamp$55(var2, var3);
         case 56:
            return this.today$56(var2, var3);
         case 57:
            return this.fromordinal$57(var2, var3);
         case 58:
            return this.__repr__$58(var2, var3);
         case 59:
            return this.ctime$59(var2, var3);
         case 60:
            return this.strftime$60(var2, var3);
         case 61:
            return this.__format__$61(var2, var3);
         case 62:
            return this.isoformat$62(var2, var3);
         case 63:
            return this.year$63(var2, var3);
         case 64:
            return this.month$64(var2, var3);
         case 65:
            return this.day$65(var2, var3);
         case 66:
            return this.timetuple$66(var2, var3);
         case 67:
            return this.toordinal$67(var2, var3);
         case 68:
            return this.replace$68(var2, var3);
         case 69:
            return this.__eq__$69(var2, var3);
         case 70:
            return this.__ne__$70(var2, var3);
         case 71:
            return this.__le__$71(var2, var3);
         case 72:
            return this.__lt__$72(var2, var3);
         case 73:
            return this.__ge__$73(var2, var3);
         case 74:
            return this.__gt__$74(var2, var3);
         case 75:
            return this._cmp$75(var2, var3);
         case 76:
            return this.__hash__$76(var2, var3);
         case 77:
            return this._checkOverflow$77(var2, var3);
         case 78:
            return this.__add__$78(var2, var3);
         case 79:
            return this.__sub__$79(var2, var3);
         case 80:
            return this.weekday$80(var2, var3);
         case 81:
            return this.isoweekday$81(var2, var3);
         case 82:
            return this.isocalendar$82(var2, var3);
         case 83:
            return this._getstate$83(var2, var3);
         case 84:
            return this._date__setstate$84(var2, var3);
         case 85:
            return this.__reduce__$85(var2, var3);
         case 86:
            return this.__tojava__$86(var2, var3);
         case 87:
            return this.tzinfo$87(var2, var3);
         case 88:
            return this.tzname$88(var2, var3);
         case 89:
            return this.utcoffset$89(var2, var3);
         case 90:
            return this.dst$90(var2, var3);
         case 91:
            return this.fromutc$91(var2, var3);
         case 92:
            return this.__reduce__$92(var2, var3);
         case 93:
            return this.time$93(var2, var3);
         case 94:
            return this.__new__$94(var2, var3);
         case 95:
            return this.hour$95(var2, var3);
         case 96:
            return this.minute$96(var2, var3);
         case 97:
            return this.second$97(var2, var3);
         case 98:
            return this.microsecond$98(var2, var3);
         case 99:
            return this.tzinfo$99(var2, var3);
         case 100:
            return this.__eq__$100(var2, var3);
         case 101:
            return this.__ne__$101(var2, var3);
         case 102:
            return this.__le__$102(var2, var3);
         case 103:
            return this.__lt__$103(var2, var3);
         case 104:
            return this.__ge__$104(var2, var3);
         case 105:
            return this.__gt__$105(var2, var3);
         case 106:
            return this._cmp$106(var2, var3);
         case 107:
            return this.__hash__$107(var2, var3);
         case 108:
            return this._tzstr$108(var2, var3);
         case 109:
            return this.__repr__$109(var2, var3);
         case 110:
            return this.isoformat$110(var2, var3);
         case 111:
            return this.strftime$111(var2, var3);
         case 112:
            return this.__format__$112(var2, var3);
         case 113:
            return this.utcoffset$113(var2, var3);
         case 114:
            return this._utcoffset$114(var2, var3);
         case 115:
            return this.tzname$115(var2, var3);
         case 116:
            return this.dst$116(var2, var3);
         case 117:
            return this._dst$117(var2, var3);
         case 118:
            return this.replace$118(var2, var3);
         case 119:
            return this.__nonzero__$119(var2, var3);
         case 120:
            return this._getstate$120(var2, var3);
         case 121:
            return this._time__setstate$121(var2, var3);
         case 122:
            return this.__reduce__$122(var2, var3);
         case 123:
            return this.__tojava__$123(var2, var3);
         case 124:
            return this.datetime$124(var2, var3);
         case 125:
            return this.__new__$125(var2, var3);
         case 126:
            return this.hour$126(var2, var3);
         case 127:
            return this.minute$127(var2, var3);
         case 128:
            return this.second$128(var2, var3);
         case 129:
            return this.microsecond$129(var2, var3);
         case 130:
            return this.tzinfo$130(var2, var3);
         case 131:
            return this.fromtimestamp$131(var2, var3);
         case 132:
            return this.utcfromtimestamp$132(var2, var3);
         case 133:
            return this.now$133(var2, var3);
         case 134:
            return this.utcnow$134(var2, var3);
         case 135:
            return this.combine$135(var2, var3);
         case 136:
            return this.timetuple$136(var2, var3);
         case 137:
            return this.utctimetuple$137(var2, var3);
         case 138:
            return this.date$138(var2, var3);
         case 139:
            return this.time$139(var2, var3);
         case 140:
            return this.timetz$140(var2, var3);
         case 141:
            return this.replace$141(var2, var3);
         case 142:
            return this.astimezone$142(var2, var3);
         case 143:
            return this.ctime$143(var2, var3);
         case 144:
            return this.isoformat$144(var2, var3);
         case 145:
            return this.__repr__$145(var2, var3);
         case 146:
            return this.__str__$146(var2, var3);
         case 147:
            return this.strptime$147(var2, var3);
         case 148:
            return this.utcoffset$148(var2, var3);
         case 149:
            return this._utcoffset$149(var2, var3);
         case 150:
            return this.tzname$150(var2, var3);
         case 151:
            return this.dst$151(var2, var3);
         case 152:
            return this._dst$152(var2, var3);
         case 153:
            return this.__eq__$153(var2, var3);
         case 154:
            return this.__ne__$154(var2, var3);
         case 155:
            return this.__le__$155(var2, var3);
         case 156:
            return this.__lt__$156(var2, var3);
         case 157:
            return this.__ge__$157(var2, var3);
         case 158:
            return this.__gt__$158(var2, var3);
         case 159:
            return this._cmp$159(var2, var3);
         case 160:
            return this.__add__$160(var2, var3);
         case 161:
            return this.__sub__$161(var2, var3);
         case 162:
            return this.__hash__$162(var2, var3);
         case 163:
            return this._getstate$163(var2, var3);
         case 164:
            return this._datetime__setstate$164(var2, var3);
         case 165:
            return this.__reduce__$165(var2, var3);
         case 166:
            return this.__tojava__$166(var2, var3);
         case 167:
            return this._isoweek1monday$167(var2, var3);
         default:
            return null;
      }
   }
}
