import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
@MTime(1498849383000L)
@Filename("pstats.py")
public class pstats$py extends PyFunctionTable implements PyRunnable {
   static pstats$py self;
   static final PyCode f$0;
   static final PyCode Stats$1;
   static final PyCode __init__$2;
   static final PyCode init$3;
   static final PyCode load_stats$4;
   static final PyCode get_top_level_stats$5;
   static final PyCode add$6;
   static final PyCode dump_stats$7;
   static final PyCode get_sort_arg_defs$8;
   static final PyCode sort_stats$9;
   static final PyCode reverse_order$10;
   static final PyCode strip_dirs$11;
   static final PyCode calc_callees$12;
   static final PyCode eval_print_amount$13;
   static final PyCode get_print_list$14;
   static final PyCode print_stats$15;
   static final PyCode print_callees$16;
   static final PyCode print_callers$17;
   static final PyCode print_call_heading$18;
   static final PyCode print_call_line$19;
   static final PyCode print_title$20;
   static final PyCode print_line$21;
   static final PyCode TupleComp$22;
   static final PyCode __init__$23;
   static final PyCode compare$24;
   static final PyCode func_strip_path$25;
   static final PyCode func_get_function_name$26;
   static final PyCode func_std_string$27;
   static final PyCode add_func_stats$28;
   static final PyCode add_callers$29;
   static final PyCode count_calls$30;
   static final PyCode f8$31;
   static final PyCode ProfileBrowser$32;
   static final PyCode __init__$33;
   static final PyCode generic$34;
   static final PyCode generic_help$35;
   static final PyCode do_add$36;
   static final PyCode help_add$37;
   static final PyCode do_callees$38;
   static final PyCode help_callees$39;
   static final PyCode do_callers$40;
   static final PyCode help_callers$41;
   static final PyCode do_EOF$42;
   static final PyCode help_EOF$43;
   static final PyCode do_quit$44;
   static final PyCode help_quit$45;
   static final PyCode do_read$46;
   static final PyCode help_read$47;
   static final PyCode do_reverse$48;
   static final PyCode help_reverse$49;
   static final PyCode do_sort$50;
   static final PyCode f$51;
   static final PyCode help_sort$52;
   static final PyCode complete_sort$53;
   static final PyCode do_stats$54;
   static final PyCode help_stats$55;
   static final PyCode do_strip$56;
   static final PyCode help_strip$57;
   static final PyCode help_help$58;
   static final PyCode postcmd$59;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Class for printing reports on profiled python code."));
      var1.setline(1);
      PyString.fromInterned("Class for printing reports on profiled python code.");
      var1.setline(23);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(24);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(25);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(26);
      var3 = imp.importOne("marshal", var1, -1);
      var1.setlocal("marshal", var3);
      var3 = null;
      var1.setline(27);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(28);
      String[] var7 = new String[]{"cmp_to_key"};
      PyObject[] var8 = imp.importFrom("functools", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal("cmp_to_key", var4);
      var4 = null;
      var1.setline(30);
      PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("Stats")});
      var1.setlocal("__all__", var9);
      var3 = null;
      var1.setline(32);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("Stats", var8, Stats$1);
      var1.setlocal("Stats", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(451);
      var8 = Py.EmptyObjects;
      var4 = Py.makeClass("TupleComp", var8, TupleComp$22);
      var1.setlocal("TupleComp", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(475);
      var8 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var8, func_strip_path$25, (PyObject)null);
      var1.setlocal("func_strip_path", var10);
      var3 = null;
      var1.setline(479);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, func_get_function_name$26, (PyObject)null);
      var1.setlocal("func_get_function_name", var10);
      var3 = null;
      var1.setline(482);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, func_std_string$27, (PyObject)null);
      var1.setlocal("func_std_string", var10);
      var3 = null;
      var1.setline(499);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, add_func_stats$28, PyString.fromInterned("Add together all the stats for two profile entries."));
      var1.setlocal("add_func_stats", var10);
      var3 = null;
      var1.setline(506);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, add_callers$29, PyString.fromInterned("Combine two caller lists in a single list."));
      var1.setlocal("add_callers", var10);
      var3 = null;
      var1.setline(524);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, count_calls$30, PyString.fromInterned("Sum the caller statistics to get total number of calls received."));
      var1.setlocal("count_calls", var10);
      var3 = null;
      var1.setline(535);
      var8 = Py.EmptyObjects;
      var10 = new PyFunction(var1.f_globals, var8, f8$31, (PyObject)null);
      var1.setlocal("f8", var10);
      var3 = null;
      var1.setline(542);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(543);
         var3 = imp.importOne("cmd", var1, -1);
         var1.setlocal("cmd", var3);
         var3 = null;

         PyException var11;
         try {
            var1.setline(545);
            var3 = imp.importOne("readline", var1, -1);
            var1.setlocal("readline", var3);
            var3 = null;
         } catch (Throwable var6) {
            var11 = Py.setException(var6, var1);
            if (!var11.match(var1.getname("ImportError"))) {
               throw var11;
            }

            var1.setline(547);
         }

         var1.setline(549);
         var8 = new PyObject[]{var1.getname("cmd").__getattr__("Cmd")};
         var4 = Py.makeClass("ProfileBrowser", var8, ProfileBrowser$32);
         var1.setlocal("ProfileBrowser", var4);
         var4 = null;
         Arrays.fill(var8, (Object)null);
         var1.setline(692);
         var3 = imp.importOne("sys", var1, -1);
         var1.setlocal("sys", var3);
         var3 = null;
         var1.setline(693);
         var3 = var1.getname("len").__call__(var2, var1.getname("sys").__getattr__("argv"));
         var10000 = var3._gt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(694);
            var3 = var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(1));
            var1.setlocal("initprofile", var3);
            var3 = null;
         } else {
            var1.setline(696);
            var3 = var1.getname("None");
            var1.setlocal("initprofile", var3);
            var3 = null;
         }

         try {
            var1.setline(698);
            var3 = var1.getname("ProfileBrowser").__call__(var2, var1.getname("initprofile"));
            var1.setlocal("browser", var3);
            var3 = null;
            var1.setline(699);
            var3 = var1.getname("browser").__getattr__("stream");
            Py.println(var3, PyString.fromInterned("Welcome to the profile statistics browser."));
            var1.setline(700);
            var1.getname("browser").__getattr__("cmdloop").__call__(var2);
            var1.setline(701);
            var3 = var1.getname("browser").__getattr__("stream");
            Py.println(var3, PyString.fromInterned("Goodbye."));
         } catch (Throwable var5) {
            var11 = Py.setException(var5, var1);
            if (!var11.match(var1.getname("KeyboardInterrupt"))) {
               throw var11;
            }

            var1.setline(703);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Stats$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class is used for creating reports from data generated by the\n    Profile class.  It is a \"friend\" of that class, and imports data either\n    by direct access to members of Profile class, or by reading in a dictionary\n    that was emitted (via marshal) from the Profile class.\n\n    The big change from the previous Profiler (in terms of raw functionality)\n    is that an \"add()\" method has been provided to combine Stats from\n    several distinct profile runs.  Both the constructor and the add()\n    method now take arbitrarily many file names as arguments.\n\n    All the print methods now take an argument that indicates how many lines\n    to print.  If the arg is a floating point number between 0 and 1.0, then\n    it is taken as a decimal percentage of the available lines to be printed\n    (e.g., .1 means print 10% of all available lines).  If it is an integer,\n    it is taken to mean the number of lines of data that you wish to have\n    printed.\n\n    The sort_stats() method now processes some additional options (i.e., in\n    addition to the old -1, 0, 1, or 2).  It takes an arbitrary number of\n    quoted strings to select the sort order.  For example sort_stats('time',\n    'name') sorts on the major key of 'internal function time', and on the\n    minor key of 'the name of the function'.  Look at the two tables in\n    sort_stats() and get_sort_arg_defs(self) for more examples.\n\n    All methods return self, so you can string together commands like:\n        Stats('foo', 'goo').strip_dirs().sort_stats('calls').                            print_stats(5).print_callers(5)\n    "));
      var1.setline(60);
      PyString.fromInterned("This class is used for creating reports from data generated by the\n    Profile class.  It is a \"friend\" of that class, and imports data either\n    by direct access to members of Profile class, or by reading in a dictionary\n    that was emitted (via marshal) from the Profile class.\n\n    The big change from the previous Profiler (in terms of raw functionality)\n    is that an \"add()\" method has been provided to combine Stats from\n    several distinct profile runs.  Both the constructor and the add()\n    method now take arbitrarily many file names as arguments.\n\n    All the print methods now take an argument that indicates how many lines\n    to print.  If the arg is a floating point number between 0 and 1.0, then\n    it is taken as a decimal percentage of the available lines to be printed\n    (e.g., .1 means print 10% of all available lines).  If it is an integer,\n    it is taken to mean the number of lines of data that you wish to have\n    printed.\n\n    The sort_stats() method now processes some additional options (i.e., in\n    addition to the old -1, 0, 1, or 2).  It takes an arbitrary number of\n    quoted strings to select the sort order.  For example sort_stats('time',\n    'name') sorts on the major key of 'internal function time', and on the\n    minor key of 'the name of the function'.  Look at the two tables in\n    sort_stats() and get_sort_arg_defs(self) for more examples.\n\n    All methods return self, so you can string together commands like:\n        Stats('foo', 'goo').strip_dirs().sort_stats('calls').                            print_stats(5).print_callers(5)\n    ");
      var1.setline(62);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, init$3, (PyObject)null);
      var1.setlocal("init", var4);
      var3 = null;
      var1.setline(106);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, load_stats$4, (PyObject)null);
      var1.setlocal("load_stats", var4);
      var3 = null;
      var1.setline(127);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_top_level_stats$5, (PyObject)null);
      var1.setlocal("get_top_level_stats", var4);
      var3 = null;
      var1.setline(137);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, add$6, (PyObject)null);
      var1.setlocal("add", var4);
      var3 = null;
      var1.setline(163);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dump_stats$7, PyString.fromInterned("Write the profile data to a file we know how to load back."));
      var1.setlocal("dump_stats", var4);
      var3 = null;
      var1.setline(173);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("calls"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(-1)})}), PyString.fromInterned("call count")}), PyString.fromInterned("ncalls"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(-1)})}), PyString.fromInterned("call count")}), PyString.fromInterned("cumtime"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(-1)})}), PyString.fromInterned("cumulative time")}), PyString.fromInterned("cumulative"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(-1)})}), PyString.fromInterned("cumulative time")}), PyString.fromInterned("file"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(4), Py.newInteger(1)})}), PyString.fromInterned("file name")}), PyString.fromInterned("filename"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(4), Py.newInteger(1)})}), PyString.fromInterned("file name")}), PyString.fromInterned("line"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(5), Py.newInteger(1)})}), PyString.fromInterned("line number")}), PyString.fromInterned("module"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(4), Py.newInteger(1)})}), PyString.fromInterned("file name")}), PyString.fromInterned("name"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(6), Py.newInteger(1)})}), PyString.fromInterned("function name")}), PyString.fromInterned("nfl"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(6), Py.newInteger(1)}), new PyTuple(new PyObject[]{Py.newInteger(4), Py.newInteger(1)}), new PyTuple(new PyObject[]{Py.newInteger(5), Py.newInteger(1)})}), PyString.fromInterned("name/file/line")}), PyString.fromInterned("pcalls"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(-1)})}), PyString.fromInterned("primitive call count")}), PyString.fromInterned("stdname"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(7), Py.newInteger(1)})}), PyString.fromInterned("standard name")}), PyString.fromInterned("time"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(-1)})}), PyString.fromInterned("internal time")}), PyString.fromInterned("tottime"), new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(-1)})}), PyString.fromInterned("internal time")})});
      var1.setlocal("sort_arg_dict_default", var5);
      var3 = null;
      var1.setline(190);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_sort_arg_defs$8, PyString.fromInterned("Expand all abbreviations that are unique."));
      var1.setlocal("get_sort_arg_defs", var4);
      var3 = null;
      var1.setline(209);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, sort_stats$9, (PyObject)null);
      var1.setlocal("sort_stats", var4);
      var3 = null;
      var1.setline(241);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reverse_order$10, (PyObject)null);
      var1.setlocal("reverse_order", var4);
      var3 = null;
      var1.setline(246);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, strip_dirs$11, (PyObject)null);
      var1.setlocal("strip_dirs", var4);
      var3 = null;
      var1.setline(275);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, calc_callees$12, (PyObject)null);
      var1.setlocal("calc_callees", var4);
      var3 = null;
      var1.setline(293);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, eval_print_amount$13, (PyObject)null);
      var1.setlocal("eval_print_amount", var4);
      var3 = null;
      var1.setline(319);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_print_list$14, (PyObject)null);
      var1.setlocal("get_print_list", var4);
      var3 = null;
      var1.setline(343);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, print_stats$15, (PyObject)null);
      var1.setlocal("print_stats", var4);
      var3 = null;
      var1.setline(365);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, print_callees$16, (PyObject)null);
      var1.setlocal("print_callees", var4);
      var3 = null;
      var1.setline(380);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, print_callers$17, (PyObject)null);
      var1.setlocal("print_callers", var4);
      var3 = null;
      var1.setline(391);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, print_call_heading$18, (PyObject)null);
      var1.setlocal("print_call_heading", var4);
      var3 = null;
      var1.setline(403);
      var3 = new PyObject[]{PyString.fromInterned("->")};
      var4 = new PyFunction(var1.f_globals, var3, print_call_line$19, (PyObject)null);
      var1.setlocal("print_call_line", var4);
      var3 = null;
      var1.setline(429);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, print_title$20, (PyObject)null);
      var1.setlocal("print_title", var4);
      var3 = null;
      var1.setline(433);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, print_line$21, (PyObject)null);
      var1.setlocal("print_line", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyObject var3 = var1.getglobal("sys").__getattr__("stdout");
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.setline(68);
      PyString var5 = PyString.fromInterned("stream");
      PyObject var10000 = var5._in(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(69);
         var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("stream"));
         var1.getlocal(0).__setattr__("stream", var3);
         var3 = null;
         var1.setline(70);
         var1.getlocal(2).__delitem__((PyObject)PyString.fromInterned("stream"));
      }

      var1.setline(71);
      if (!var1.getlocal(2).__nonzero__()) {
         var1.setline(76);
         if (var1.getglobal("len").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(77);
            var3 = var1.getglobal("None");
            var1.setlocal(7, var3);
            var3 = null;
         } else {
            var1.setline(79);
            var3 = var1.getlocal(1).__getitem__(Py.newInteger(0));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(80);
            var3 = var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(81);
         var1.getlocal(0).__getattr__("init").__call__(var2, var1.getlocal(7));
         var1.setline(82);
         var10000 = var1.getlocal(0).__getattr__("add");
         PyObject[] var7 = Py.EmptyObjects;
         String[] var6 = new String[0];
         var10000._callextra(var7, var6, var1.getlocal(1), (PyObject)null);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(72);
         var3 = var1.getlocal(2).__getattr__("keys").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(73);
         var1.getlocal(3).__getattr__("sort").__call__(var2);
         var1.setline(74);
         var10000 = PyString.fromInterned(", ").__getattr__("join");
         PyList var10002 = new PyList();
         var3 = var10002.__getattr__("append");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(74);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(74);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(74);
               var1.dellocal(5);
               var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(75);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unrecognized keyword args: %s")._mod(var1.getlocal(4)));
            }

            var1.setlocal(6, var4);
            var1.setline(74);
            var1.getlocal(5).__call__(var2, PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(2).__getitem__(var1.getlocal(6))})));
         }
      }
   }

   public PyObject init$3(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("all_callees", var3);
      var3 = null;
      var1.setline(86);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"files", var6);
      var3 = null;
      var1.setline(87);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("fcn_list", var3);
      var3 = null;
      var1.setline(88);
      PyInteger var7 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"total_tt", var7);
      var3 = null;
      var1.setline(89);
      var7 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"total_calls", var7);
      var3 = null;
      var1.setline(90);
      var7 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"prim_calls", var7);
      var3 = null;
      var1.setline(91);
      var7 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"max_name_len", var7);
      var3 = null;
      var1.setline(92);
      PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"top_level", var9);
      var3 = null;
      var1.setline(93);
      var9 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"stats", var9);
      var3 = null;
      var1.setline(94);
      var9 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"sort_arg_dict", var9);
      var3 = null;
      var1.setline(95);
      var1.getlocal(0).__getattr__("load_stats").__call__(var2, var1.getlocal(1));
      var1.setline(96);
      var7 = Py.newInteger(1);
      var1.setlocal(2, var7);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(98);
         var1.getlocal(0).__getattr__("get_top_level_stats").__call__(var2);
         var1.setline(99);
         PyInteger var8 = Py.newInteger(0);
         var1.setlocal(2, var8);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(101);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(102);
            var4 = var1.getlocal(0).__getattr__("stream");
            Py.printComma(var4, PyString.fromInterned("Invalid timing data"));
            var1.setline(103);
            if (var1.getlocal(0).__getattr__("files").__nonzero__()) {
               var1.setline(103);
               var4 = var1.getlocal(0).__getattr__("stream");
               Py.printComma(var4, var1.getlocal(0).__getattr__("files").__getitem__(Py.newInteger(-1)));
            }

            var1.setline(104);
            var4 = var1.getlocal(0).__getattr__("stream");
            Py.printlnv(var4);
         }

         throw (Throwable)var5;
      }

      var1.setline(101);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(102);
         var4 = var1.getlocal(0).__getattr__("stream");
         Py.printComma(var4, PyString.fromInterned("Invalid timing data"));
         var1.setline(103);
         if (var1.getlocal(0).__getattr__("files").__nonzero__()) {
            var1.setline(103);
            var4 = var1.getlocal(0).__getattr__("stream");
            Py.printComma(var4, var1.getlocal(0).__getattr__("files").__getitem__(Py.newInteger(-1)));
         }

         var1.setline(104);
         var4 = var1.getlocal(0).__getattr__("stream");
         Py.printlnv(var4);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_stats$4(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyDictionary var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(107);
         var3 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"stats", var3);
         var3 = null;
      } else {
         var1.setline(108);
         PyObject var5;
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
            var1.setline(109);
            var5 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("rb"));
            var1.setlocal(2, var5);
            var3 = null;
            var1.setline(110);
            var5 = var1.getglobal("marshal").__getattr__("load").__call__(var2, var1.getlocal(2));
            var1.getlocal(0).__setattr__("stats", var5);
            var3 = null;
            var1.setline(111);
            var1.getlocal(2).__getattr__("close").__call__(var2);

            try {
               var1.setline(113);
               var5 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(1));
               var1.setlocal(3, var5);
               var3 = null;
               var1.setline(114);
               var5 = var1.getglobal("time").__getattr__("ctime").__call__(var2, var1.getlocal(3).__getattr__("st_mtime"))._add(PyString.fromInterned("    "))._add(var1.getlocal(1));
               var1.setlocal(1, var5);
               var3 = null;
            } catch (Throwable var4) {
               Py.setException(var4, var1);
               var1.setline(116);
            }

            var1.setline(117);
            PyList var6 = new PyList(new PyObject[]{var1.getlocal(1)});
            var1.getlocal(0).__setattr__((String)"files", var6);
            var3 = null;
         } else {
            var1.setline(118);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("create_stats")).__nonzero__()) {
               var1.setline(119);
               var1.getlocal(1).__getattr__("create_stats").__call__(var2);
               var1.setline(120);
               var5 = var1.getlocal(1).__getattr__("stats");
               var1.getlocal(0).__setattr__("stats", var5);
               var3 = null;
               var1.setline(121);
               var3 = new PyDictionary(Py.EmptyObjects);
               var1.getlocal(1).__setattr__((String)"stats", var3);
               var3 = null;
            }
         }
      }

      var1.setline(122);
      if (var1.getlocal(0).__getattr__("stats").__not__().__nonzero__()) {
         var1.setline(123);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("Cannot create or construct a %r object from %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), var1.getlocal(1)}))));
      } else {
         var1.setline(125);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject get_top_level_stats$5(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyObject var3 = var1.getlocal(0).__getattr__("stats").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(128);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         PyObject[] var7 = Py.unpackSequence(var6, 5);
         PyObject var8 = var7[0];
         var1.setlocal(2, var8);
         var8 = null;
         var8 = var7[1];
         var1.setlocal(3, var8);
         var8 = null;
         var8 = var7[2];
         var1.setlocal(4, var8);
         var8 = null;
         var8 = var7[3];
         var1.setlocal(5, var8);
         var8 = null;
         var8 = var7[4];
         var1.setlocal(6, var8);
         var8 = null;
         var6 = null;
         var1.setline(129);
         PyObject var10000 = var1.getlocal(0);
         String var9 = "total_calls";
         var6 = var10000;
         PyObject var12 = var6.__getattr__(var9);
         var12 = var12._iadd(var1.getlocal(3));
         var6.__setattr__(var9, var12);
         var1.setline(130);
         var10000 = var1.getlocal(0);
         var9 = "prim_calls";
         var6 = var10000;
         var12 = var6.__getattr__(var9);
         var12 = var12._iadd(var1.getlocal(2));
         var6.__setattr__(var9, var12);
         var1.setline(131);
         var10000 = var1.getlocal(0);
         var9 = "total_tt";
         var6 = var10000;
         var12 = var6.__getattr__(var9);
         var12 = var12._iadd(var1.getlocal(4));
         var6.__setattr__(var9, var12);
         var1.setline(132);
         PyTuple var10 = new PyTuple(new PyObject[]{PyString.fromInterned("jprofile"), Py.newInteger(0), PyString.fromInterned("profiler")});
         var10000 = var10._in(var1.getlocal(6));
         var5 = null;
         PyObject var11;
         if (var10000.__nonzero__()) {
            var1.setline(133);
            var11 = var1.getglobal("None");
            var1.getlocal(0).__getattr__("top_level").__setitem__(var1.getlocal(1), var11);
            var5 = null;
         }

         var1.setline(134);
         var11 = var1.getglobal("len").__call__(var2, var1.getglobal("func_std_string").__call__(var2, var1.getlocal(1)));
         var10000 = var11._gt(var1.getlocal(0).__getattr__("max_name_len"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(135);
            var11 = var1.getglobal("len").__call__(var2, var1.getglobal("func_std_string").__call__(var2, var1.getlocal(1)));
            var1.getlocal(0).__setattr__("max_name_len", var11);
            var5 = null;
         }
      }
   }

   public PyObject add$6(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(138);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(139);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var4._gt(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(139);
            var10000 = var1.getlocal(0).__getattr__("add");
            PyObject[] var8 = Py.EmptyObjects;
            String[] var5 = new String[0];
            var10000._callextra(var8, var5, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)null);
            var4 = null;
         }

         var1.setline(140);
         var4 = var1.getlocal(1).__getitem__(Py.newInteger(0));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(141);
         var4 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
         var10000 = var4._ne(var1.getglobal("type").__call__(var2, var1.getlocal(2)));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("__class__");
            var10000 = var4._ne(var1.getlocal(2).__getattr__("__class__"));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(142);
            var4 = var1.getglobal("Stats").__call__(var2, var1.getlocal(2));
            var1.setlocal(2, var4);
            var4 = null;
         }

         var1.setline(143);
         var10000 = var1.getlocal(0);
         String var10 = "files";
         PyObject var9 = var10000;
         PyObject var6 = var9.__getattr__(var10);
         var6 = var6._iadd(var1.getlocal(2).__getattr__("files"));
         var9.__setattr__(var10, var6);
         var1.setline(144);
         var10000 = var1.getlocal(0);
         var10 = "total_calls";
         var9 = var10000;
         var6 = var9.__getattr__(var10);
         var6 = var6._iadd(var1.getlocal(2).__getattr__("total_calls"));
         var9.__setattr__(var10, var6);
         var1.setline(145);
         var10000 = var1.getlocal(0);
         var10 = "prim_calls";
         var9 = var10000;
         var6 = var9.__getattr__(var10);
         var6 = var6._iadd(var1.getlocal(2).__getattr__("prim_calls"));
         var9.__setattr__(var10, var6);
         var1.setline(146);
         var10000 = var1.getlocal(0);
         var10 = "total_tt";
         var9 = var10000;
         var6 = var9.__getattr__(var10);
         var6 = var6._iadd(var1.getlocal(2).__getattr__("total_tt"));
         var9.__setattr__(var10, var6);
         var1.setline(147);
         var4 = var1.getlocal(2).__getattr__("top_level").__iter__();

         while(true) {
            var1.setline(147);
            var9 = var4.__iternext__();
            if (var9 == null) {
               var1.setline(150);
               var4 = var1.getlocal(0).__getattr__("max_name_len");
               var10000 = var4._lt(var1.getlocal(2).__getattr__("max_name_len"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(151);
                  var4 = var1.getlocal(2).__getattr__("max_name_len");
                  var1.getlocal(0).__setattr__("max_name_len", var4);
                  var4 = null;
               }

               var1.setline(153);
               var4 = var1.getglobal("None");
               var1.getlocal(0).__setattr__("fcn_list", var4);
               var4 = null;
               var1.setline(155);
               var4 = var1.getlocal(2).__getattr__("stats").__getattr__("iteritems").__call__(var2).__iter__();

               while(true) {
                  var1.setline(155);
                  var9 = var4.__iternext__();
                  if (var9 == null) {
                     var1.setline(161);
                     var3 = var1.getlocal(0);
                     var1.f_lasti = -1;
                     return var3;
                  }

                  PyObject[] var11 = Py.unpackSequence(var9, 2);
                  PyObject var7 = var11[0];
                  var1.setlocal(3, var7);
                  var7 = null;
                  var7 = var11[1];
                  var1.setlocal(4, var7);
                  var7 = null;
                  var1.setline(156);
                  var6 = var1.getlocal(3);
                  var10000 = var6._in(var1.getlocal(0).__getattr__("stats"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(157);
                     var6 = var1.getlocal(0).__getattr__("stats").__getitem__(var1.getlocal(3));
                     var1.setlocal(5, var6);
                     var6 = null;
                  } else {
                     var1.setline(159);
                     PyTuple var12 = new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), new PyDictionary(Py.EmptyObjects)});
                     var1.setlocal(5, var12);
                     var6 = null;
                  }

                  var1.setline(160);
                  var6 = var1.getglobal("add_func_stats").__call__(var2, var1.getlocal(5), var1.getlocal(4));
                  var1.getlocal(0).__getattr__("stats").__setitem__(var1.getlocal(3), var6);
                  var6 = null;
               }
            }

            var1.setlocal(3, var9);
            var1.setline(148);
            var6 = var1.getglobal("None");
            var1.getlocal(0).__getattr__("top_level").__setitem__(var1.getlocal(3), var6);
            var6 = null;
         }
      }
   }

   public PyObject dump_stats$7(PyFrame var1, ThreadState var2) {
      var1.setline(164);
      PyString.fromInterned("Write the profile data to a file we know how to load back.");
      var1.setline(165);
      PyObject var3 = var1.getglobal("file").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("wb"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(167);
         var1.getglobal("marshal").__getattr__("dump").__call__(var2, var1.getlocal(0).__getattr__("stats"), var1.getlocal(2));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(169);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(169);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_sort_arg_defs$8(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      PyString.fromInterned("Expand all abbreviations that are unique.");
      var1.setline(192);
      PyObject var7;
      if (var1.getlocal(0).__getattr__("sort_arg_dict").__not__().__nonzero__()) {
         var1.setline(193);
         PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"sort_arg_dict", var3);
         var1.setlocal(1, var3);
         var1.setline(194);
         var3 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(195);
         var7 = var1.getlocal(0).__getattr__("sort_arg_dict_default").__getattr__("iteritems").__call__(var2).__iter__();

         label40:
         while(true) {
            while(true) {
               var1.setline(195);
               PyObject var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.setline(205);
                  var7 = var1.getlocal(2).__iter__();

                  while(true) {
                     var1.setline(205);
                     var4 = var7.__iternext__();
                     if (var4 == null) {
                        break label40;
                     }

                     var1.setlocal(3, var4);
                     var1.setline(206);
                     var1.getlocal(1).__delitem__(var1.getlocal(3));
                  }
               }

               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(4, var6);
               var6 = null;
               var1.setline(196);
               PyObject var8 = var1.getlocal(3);
               var1.setlocal(5, var8);
               var5 = null;

               while(true) {
                  var1.setline(197);
                  if (!var1.getlocal(5).__nonzero__()) {
                     break;
                  }

                  var1.setline(198);
                  if (var1.getlocal(5).__not__().__nonzero__()) {
                     break;
                  }

                  var1.setline(200);
                  var8 = var1.getlocal(5);
                  PyObject var10000 = var8._in(var1.getlocal(1));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(201);
                     PyInteger var9 = Py.newInteger(0);
                     var1.getlocal(2).__setitem__((PyObject)var1.getlocal(5), var9);
                     var5 = null;
                     break;
                  }

                  var1.setline(203);
                  var8 = var1.getlocal(4);
                  var1.getlocal(1).__setitem__(var1.getlocal(5), var8);
                  var5 = null;
                  var1.setline(204);
                  var8 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                  var1.setlocal(5, var8);
                  var5 = null;
               }
            }
         }
      }

      var1.setline(207);
      var7 = var1.getlocal(0).__getattr__("sort_arg_dict");
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject sort_stats$9(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(211);
         PyInteger var10 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"fcn_list", var10);
         var3 = null;
         var1.setline(212);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(213);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         PyObject var10000 = var4._eq(Py.newInteger(1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getitem__(Py.newInteger(0)), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")})));
         }

         PyList var11;
         if (var10000.__nonzero__()) {
            var1.setline(215);
            var11 = new PyList(new PyObject[]{(new PyDictionary(new PyObject[]{Py.newInteger(-1), PyString.fromInterned("stdname"), Py.newInteger(0), PyString.fromInterned("calls"), Py.newInteger(1), PyString.fromInterned("time"), Py.newInteger(2), PyString.fromInterned("cumulative")})).__getitem__(var1.getlocal(1).__getitem__(Py.newInteger(0)))});
            var1.setlocal(1, var11);
            var4 = null;
         }

         var1.setline(220);
         var4 = var1.getlocal(0).__getattr__("get_sort_arg_defs").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(221);
         PyTuple var13 = new PyTuple(Py.EmptyObjects);
         var1.setlocal(3, var13);
         var4 = null;
         var1.setline(222);
         PyString var14 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"sort_type", var14);
         var4 = null;
         var1.setline(223);
         var14 = PyString.fromInterned("");
         var1.setlocal(4, var14);
         var4 = null;
         var1.setline(224);
         var4 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(224);
            PyObject var5 = var4.__iternext__();
            PyObject var7;
            if (var5 == null) {
               var1.setline(229);
               var11 = new PyList(Py.EmptyObjects);
               var1.setlocal(6, var11);
               var4 = null;
               var1.setline(230);
               var4 = var1.getlocal(0).__getattr__("stats").__getattr__("iteritems").__call__(var2).__iter__();

               while(true) {
                  var1.setline(230);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(234);
                     var10000 = var1.getlocal(6).__getattr__("sort");
                     PyObject[] var19 = new PyObject[]{var1.getglobal("cmp_to_key").__call__(var2, var1.getglobal("TupleComp").__call__(var2, var1.getlocal(3)).__getattr__("compare"))};
                     String[] var12 = new String[]{"key"};
                     var10000.__call__(var2, var19, var12);
                     var4 = null;
                     var1.setline(236);
                     var11 = new PyList(Py.EmptyObjects);
                     var1.getlocal(0).__setattr__((String)"fcn_list", var11);
                     var1.setlocal(13, var11);
                     var1.setline(237);
                     var4 = var1.getlocal(6).__iter__();

                     while(true) {
                        var1.setline(237);
                        var5 = var4.__iternext__();
                        if (var5 == null) {
                           var1.setline(239);
                           var3 = var1.getlocal(0);
                           var1.f_lasti = -1;
                           return var3;
                        }

                        var1.setlocal(14, var5);
                        var1.setline(238);
                        var1.getlocal(13).__getattr__("append").__call__(var2, var1.getlocal(14).__getitem__(Py.newInteger(-1)));
                     }
                  }

                  PyObject[] var17 = Py.unpackSequence(var5, 2);
                  var7 = var17[0];
                  var1.setlocal(7, var7);
                  var7 = null;
                  var7 = var17[1];
                  PyObject[] var18 = Py.unpackSequence(var7, 5);
                  PyObject var9 = var18[0];
                  var1.setlocal(8, var9);
                  var9 = null;
                  var9 = var18[1];
                  var1.setlocal(9, var9);
                  var9 = null;
                  var9 = var18[2];
                  var1.setlocal(10, var9);
                  var9 = null;
                  var9 = var18[3];
                  var1.setlocal(11, var9);
                  var9 = null;
                  var9 = var18[4];
                  var1.setlocal(12, var9);
                  var9 = null;
                  var7 = null;
                  var1.setline(231);
                  var1.getlocal(6).__getattr__("append").__call__(var2, (new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11)}))._add(var1.getlocal(7))._add(new PyTuple(new PyObject[]{var1.getglobal("func_std_string").__call__(var2, var1.getlocal(7)), var1.getlocal(7)})));
               }
            }

            var1.setlocal(5, var5);
            var1.setline(225);
            PyObject var6 = var1.getlocal(3)._add(var1.getlocal(2).__getitem__(var1.getlocal(5)).__getitem__(Py.newInteger(0)));
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(226);
            var10000 = var1.getlocal(0);
            String var15 = "sort_type";
            var7 = var10000;
            PyObject var8 = var7.__getattr__(var15);
            var8 = var8._iadd(var1.getlocal(4)._add(var1.getlocal(2).__getitem__(var1.getlocal(5)).__getitem__(Py.newInteger(1))));
            var7.__setattr__(var15, var8);
            var1.setline(227);
            PyString var16 = PyString.fromInterned(", ");
            var1.setlocal(4, var16);
            var6 = null;
         }
      }
   }

   public PyObject reverse_order$10(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      if (var1.getlocal(0).__getattr__("fcn_list").__nonzero__()) {
         var1.setline(243);
         var1.getlocal(0).__getattr__("fcn_list").__getattr__("reverse").__call__(var2);
      }

      var1.setline(244);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject strip_dirs$11(PyFrame var1, ThreadState var2) {
      var1.setline(247);
      PyObject var3 = var1.getlocal(0).__getattr__("stats");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(248);
      PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"stats", var9);
      var1.setlocal(2, var9);
      var1.setline(249);
      PyInteger var10 = Py.newInteger(0);
      var1.setlocal(3, var10);
      var3 = null;
      var1.setline(250);
      var3 = var1.getlocal(1).__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(250);
         PyObject var4 = var3.__iternext__();
         PyObject[] var5;
         PyObject var11;
         if (var4 == null) {
            var1.setline(264);
            var3 = var1.getlocal(0).__getattr__("top_level");
            var1.setlocal(14, var3);
            var3 = null;
            var1.setline(265);
            var9 = new PyDictionary(Py.EmptyObjects);
            var1.getlocal(0).__setattr__((String)"top_level", var9);
            var1.setlocal(15, var9);
            var1.setline(266);
            var3 = var1.getlocal(14).__iter__();

            while(true) {
               var1.setline(266);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(269);
                  var3 = var1.getlocal(3);
                  var1.getlocal(0).__setattr__("max_name_len", var3);
                  var3 = null;
                  var1.setline(271);
                  var3 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("fcn_list", var3);
                  var3 = null;
                  var1.setline(272);
                  var3 = var1.getglobal("None");
                  var1.getlocal(0).__setattr__("all_callees", var3);
                  var3 = null;
                  var1.setline(273);
                  var3 = var1.getlocal(0);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(4, var4);
               var1.setline(267);
               var11 = var1.getglobal("None");
               var1.getlocal(15).__setitem__(var1.getglobal("func_strip_path").__call__(var2, var1.getlocal(4)), var11);
               var5 = null;
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         PyObject[] var7 = Py.unpackSequence(var6, 5);
         PyObject var8 = var7[0];
         var1.setlocal(5, var8);
         var8 = null;
         var8 = var7[1];
         var1.setlocal(6, var8);
         var8 = null;
         var8 = var7[2];
         var1.setlocal(7, var8);
         var8 = null;
         var8 = var7[3];
         var1.setlocal(8, var8);
         var8 = null;
         var8 = var7[4];
         var1.setlocal(9, var8);
         var8 = null;
         var6 = null;
         var1.setline(251);
         var11 = var1.getglobal("func_strip_path").__call__(var2, var1.getlocal(4));
         var1.setlocal(10, var11);
         var5 = null;
         var1.setline(252);
         var11 = var1.getglobal("len").__call__(var2, var1.getglobal("func_std_string").__call__(var2, var1.getlocal(10)));
         PyObject var10000 = var11._gt(var1.getlocal(3));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(253);
            var11 = var1.getglobal("len").__call__(var2, var1.getglobal("func_std_string").__call__(var2, var1.getlocal(10)));
            var1.setlocal(3, var11);
            var5 = null;
         }

         var1.setline(254);
         PyDictionary var12 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(11, var12);
         var5 = null;
         var1.setline(255);
         var11 = var1.getlocal(9).__getattr__("iteritems").__call__(var2).__iter__();

         while(true) {
            var1.setline(255);
            var6 = var11.__iternext__();
            if (var6 == null) {
               var1.setline(258);
               var11 = var1.getlocal(10);
               var10000 = var11._in(var1.getlocal(2));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(259);
                  var11 = var1.getglobal("add_func_stats").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(var1.getlocal(10)), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(11)})));
                  var1.getlocal(2).__setitem__(var1.getlocal(10), var11);
                  var5 = null;
               } else {
                  var1.setline(263);
                  PyTuple var14 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(11)});
                  var1.getlocal(2).__setitem__((PyObject)var1.getlocal(10), var14);
                  var5 = null;
               }
               break;
            }

            var7 = Py.unpackSequence(var6, 2);
            var8 = var7[0];
            var1.setlocal(12, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(13, var8);
            var8 = null;
            var1.setline(256);
            PyObject var13 = var1.getlocal(13);
            var1.getlocal(11).__setitem__(var1.getglobal("func_strip_path").__call__(var2, var1.getlocal(12)), var13);
            var7 = null;
         }
      }
   }

   public PyObject calc_callees$12(PyFrame var1, ThreadState var2) {
      var1.setline(276);
      if (var1.getlocal(0).__getattr__("all_callees").__nonzero__()) {
         var1.setline(276);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(277);
         PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"all_callees", var3);
         var1.setlocal(1, var3);
         var1.setline(278);
         PyObject var9 = var1.getlocal(0).__getattr__("stats").__getattr__("iteritems").__call__(var2).__iter__();

         while(true) {
            var1.setline(278);
            PyObject var4 = var9.__iternext__();
            if (var4 == null) {
               var1.setline(285);
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            PyObject[] var7 = Py.unpackSequence(var6, 5);
            PyObject var8 = var7[0];
            var1.setlocal(3, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(4, var8);
            var8 = null;
            var8 = var7[2];
            var1.setlocal(5, var8);
            var8 = null;
            var8 = var7[3];
            var1.setlocal(6, var8);
            var8 = null;
            var8 = var7[4];
            var1.setlocal(7, var8);
            var8 = null;
            var6 = null;
            var1.setline(279);
            PyObject var10 = var1.getlocal(2);
            PyObject var10000 = var10._in(var1.getlocal(1));
            var5 = null;
            if (var10000.__not__().__nonzero__()) {
               var1.setline(280);
               PyDictionary var11 = new PyDictionary(Py.EmptyObjects);
               var1.getlocal(1).__setitem__((PyObject)var1.getlocal(2), var11);
               var5 = null;
            }

            var1.setline(281);
            var10 = var1.getlocal(7).__getattr__("iteritems").__call__(var2).__iter__();

            while(true) {
               var1.setline(281);
               var6 = var10.__iternext__();
               if (var6 == null) {
                  break;
               }

               var7 = Py.unpackSequence(var6, 2);
               var8 = var7[0];
               var1.setlocal(8, var8);
               var8 = null;
               var8 = var7[1];
               var1.setlocal(9, var8);
               var8 = null;
               var1.setline(282);
               PyObject var12 = var1.getlocal(8);
               var10000 = var12._in(var1.getlocal(1));
               var7 = null;
               if (var10000.__not__().__nonzero__()) {
                  var1.setline(283);
                  PyDictionary var13 = new PyDictionary(Py.EmptyObjects);
                  var1.getlocal(1).__setitem__((PyObject)var1.getlocal(8), var13);
                  var7 = null;
               }

               var1.setline(284);
               var12 = var1.getlocal(9);
               var1.getlocal(1).__getitem__(var1.getlocal(8)).__setitem__(var1.getlocal(2), var12);
               var7 = null;
            }
         }
      }
   }

   public PyObject eval_print_amount$13(PyFrame var1, ThreadState var2) {
      var1.setline(294);
      PyObject var3 = var1.getlocal(2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(295);
      PyObject var5;
      PyTuple var9;
      PyObject var10000;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         try {
            var1.setline(297);
            var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(1));
            var1.setlocal(5, var3);
            var3 = null;
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("re").__getattr__("error"))) {
               var1.setline(299);
               PyObject var4 = var1.getlocal(3);
               var4 = var4._iadd(PyString.fromInterned("   <Invalid regular expression %r>\n")._mod(var1.getlocal(1)));
               var1.setlocal(3, var4);
               var1.setline(300);
               var9 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(3)});
               var1.f_lasti = -1;
               return var9;
            }

            throw var7;
         }

         var1.setline(301);
         PyList var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var8);
         var3 = null;
         var1.setline(302);
         var3 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(302);
            var5 = var3.__iternext__();
            if (var5 == null) {
               break;
            }

            var1.setlocal(6, var5);
            var1.setline(303);
            if (var1.getlocal(5).__getattr__("search").__call__(var2, var1.getglobal("func_std_string").__call__(var2, var1.getlocal(6))).__nonzero__()) {
               var1.setline(304);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6));
            }
         }
      } else {
         var1.setline(306);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(307);
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("float"));
         PyObject var10001;
         if (var10000.__nonzero__()) {
            PyFloat var10 = Py.newFloat(0.0);
            var10001 = var1.getlocal(1);
            PyFloat var12 = var10;
            var3 = var10001;
            if ((var5 = var12._le(var10001)).__nonzero__()) {
               var5 = var3._lt(Py.newFloat(1.0));
            }

            var10000 = var5;
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(308);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(7)._mul(var1.getlocal(1))._add(Py.newFloat(0.5)));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(309);
            var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(7), (PyObject)null);
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(310);
            var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("int"), var1.getglobal("long")})));
            if (var10000.__nonzero__()) {
               PyInteger var11 = Py.newInteger(0);
               var10001 = var1.getlocal(1);
               PyInteger var13 = var11;
               var3 = var10001;
               if ((var5 = var13._le(var10001)).__nonzero__()) {
                  var5 = var3._lt(var1.getlocal(7));
               }

               var10000 = var5;
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(311);
               var3 = var1.getlocal(1);
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(312);
               var3 = var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(7), (PyObject)null);
               var1.setlocal(4, var3);
               var3 = null;
            }
         }
      }

      var1.setline(313);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var10000 = var3._ne(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(314);
         var3 = var1.getlocal(3);
         var3 = var3._iadd(PyString.fromInterned("   List reduced from %r to %r due to restriction <%r>\n")._mod(new PyTuple(new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(2)), var1.getglobal("len").__call__(var2, var1.getlocal(4)), var1.getlocal(1)})));
         var1.setlocal(3, var3);
      }

      var1.setline(317);
      var9 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var9;
   }

   public PyObject get_print_list$14(PyFrame var1, ThreadState var2) {
      var1.setline(320);
      PyObject var3 = var1.getlocal(0).__getattr__("max_name_len");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(321);
      if (var1.getlocal(0).__getattr__("fcn_list").__nonzero__()) {
         var1.setline(322);
         var3 = var1.getlocal(0).__getattr__("fcn_list").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(323);
         var3 = PyString.fromInterned("   Ordered by: ")._add(var1.getlocal(0).__getattr__("sort_type"))._add(PyString.fromInterned("\n"));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(325);
         var3 = var1.getlocal(0).__getattr__("stats").__getattr__("keys").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(326);
         PyString var9 = PyString.fromInterned("   Random listing order was used\n");
         var1.setlocal(4, var9);
         var3 = null;
      }

      var1.setline(328);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(328);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         PyObject[] var6;
         if (var4 == null) {
            var1.setline(331);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(333);
            PyTuple var11;
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(334);
               var11 = new PyTuple(new PyObject[]{Py.newInteger(0), var1.getlocal(3)});
               var1.f_lasti = -1;
               return var11;
            } else {
               var1.setline(335);
               var4 = var1.getlocal(0).__getattr__("stream");
               Py.println(var4, var1.getlocal(4));
               var1.setline(336);
               var4 = var1.getlocal(6);
               PyObject var10000 = var4._lt(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("stats")));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(337);
                  PyInteger var8 = Py.newInteger(0);
                  var1.setlocal(2, var8);
                  var4 = null;
                  var1.setline(338);
                  var4 = var1.getlocal(3).__iter__();

                  while(true) {
                     var1.setline(338);
                     var5 = var4.__iternext__();
                     if (var5 == null) {
                        break;
                     }

                     var1.setlocal(7, var5);
                     var1.setline(339);
                     PyObject var10 = var1.getglobal("len").__call__(var2, var1.getglobal("func_std_string").__call__(var2, var1.getlocal(7)));
                     var10000 = var10._gt(var1.getlocal(2));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(340);
                        var10 = var1.getglobal("len").__call__(var2, var1.getglobal("func_std_string").__call__(var2, var1.getlocal(7)));
                        var1.setlocal(2, var10);
                        var6 = null;
                     }
                  }
               }

               var1.setline(341);
               var11 = new PyTuple(new PyObject[]{var1.getlocal(2)._add(Py.newInteger(2)), var1.getlocal(3)});
               var1.f_lasti = -1;
               return var11;
            }
         }

         var1.setlocal(5, var4);
         var1.setline(329);
         var5 = var1.getlocal(0).__getattr__("eval_print_amount").__call__(var2, var1.getlocal(5), var1.getlocal(3), var1.getlocal(4));
         var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(3, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(4, var7);
         var7 = null;
         var5 = null;
      }
   }

   public PyObject print_stats$15(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      PyObject var3 = var1.getlocal(0).__getattr__("files").__iter__();

      while(true) {
         var1.setline(344);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(346);
            if (var1.getlocal(0).__getattr__("files").__nonzero__()) {
               var1.setline(346);
               var3 = var1.getlocal(0).__getattr__("stream");
               Py.printlnv(var3);
            }

            var1.setline(347);
            var3 = PyString.fromInterned(" ")._mul(Py.newInteger(8));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(348);
            var3 = var1.getlocal(0).__getattr__("top_level").__iter__();

            while(true) {
               var1.setline(348);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(351);
                  var3 = var1.getlocal(0).__getattr__("stream");
                  Py.printComma(var3, var1.getlocal(3));
                  Py.printComma(var3, var1.getlocal(0).__getattr__("total_calls"));
                  Py.printComma(var3, PyString.fromInterned("function calls"));
                  var1.setline(352);
                  var3 = var1.getlocal(0).__getattr__("total_calls");
                  PyObject var10000 = var3._ne(var1.getlocal(0).__getattr__("prim_calls"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(353);
                     var3 = var1.getlocal(0).__getattr__("stream");
                     Py.printComma(var3, PyString.fromInterned("(%d primitive calls)")._mod(var1.getlocal(0).__getattr__("prim_calls")));
                  }

                  var1.setline(354);
                  var3 = var1.getlocal(0).__getattr__("stream");
                  Py.println(var3, PyString.fromInterned("in %.3f seconds")._mod(var1.getlocal(0).__getattr__("total_tt")));
                  var1.setline(355);
                  var3 = var1.getlocal(0).__getattr__("stream");
                  Py.printlnv(var3);
                  var1.setline(356);
                  var3 = var1.getlocal(0).__getattr__("get_print_list").__call__(var2, var1.getlocal(1));
                  PyObject[] var6 = Py.unpackSequence(var3, 2);
                  var5 = var6[0];
                  var1.setlocal(5, var5);
                  var5 = null;
                  var5 = var6[1];
                  var1.setlocal(6, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(357);
                  if (var1.getlocal(6).__nonzero__()) {
                     var1.setline(358);
                     var1.getlocal(0).__getattr__("print_title").__call__(var2);
                     var1.setline(359);
                     var3 = var1.getlocal(6).__iter__();

                     while(true) {
                        var1.setline(359);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           var1.setline(361);
                           var3 = var1.getlocal(0).__getattr__("stream");
                           Py.printlnv(var3);
                           var1.setline(362);
                           var3 = var1.getlocal(0).__getattr__("stream");
                           Py.printlnv(var3);
                           break;
                        }

                        var1.setlocal(4, var4);
                        var1.setline(360);
                        var1.getlocal(0).__getattr__("print_line").__call__(var2, var1.getlocal(4));
                     }
                  }

                  var1.setline(363);
                  var3 = var1.getlocal(0);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(4, var4);
               var1.setline(349);
               var5 = var1.getlocal(0).__getattr__("stream");
               Py.printComma(var5, var1.getlocal(3));
               Py.println(var5, var1.getglobal("func_get_function_name").__call__(var2, var1.getlocal(4)));
            }
         }

         var1.setlocal(2, var4);
         var1.setline(345);
         var5 = var1.getlocal(0).__getattr__("stream");
         Py.println(var5, var1.getlocal(2));
      }
   }

   public PyObject print_callees$16(PyFrame var1, ThreadState var2) {
      var1.setline(366);
      PyObject var3 = var1.getlocal(0).__getattr__("get_print_list").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(367);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(368);
         var1.getlocal(0).__getattr__("calc_callees").__call__(var2);
         var1.setline(370);
         var1.getlocal(0).__getattr__("print_call_heading").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("called..."));
         var1.setline(371);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(371);
            PyObject var6 = var3.__iternext__();
            if (var6 == null) {
               var1.setline(376);
               var3 = var1.getlocal(0).__getattr__("stream");
               Py.printlnv(var3);
               var1.setline(377);
               var3 = var1.getlocal(0).__getattr__("stream");
               Py.printlnv(var3);
               break;
            }

            var1.setlocal(4, var6);
            var1.setline(372);
            var5 = var1.getlocal(4);
            PyObject var10000 = var5._in(var1.getlocal(0).__getattr__("all_callees"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(373);
               var1.getlocal(0).__getattr__("print_call_line").__call__(var2, var1.getlocal(2), var1.getlocal(4), var1.getlocal(0).__getattr__("all_callees").__getitem__(var1.getlocal(4)));
            } else {
               var1.setline(375);
               var1.getlocal(0).__getattr__("print_call_line").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(4), (PyObject)(new PyDictionary(Py.EmptyObjects)));
            }
         }
      }

      var1.setline(378);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject print_callers$17(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      PyObject var3 = var1.getlocal(0).__getattr__("get_print_list").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(382);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(383);
         var1.getlocal(0).__getattr__("print_call_heading").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("was called by..."));
         var1.setline(384);
         var3 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(384);
            PyObject var8 = var3.__iternext__();
            if (var8 == null) {
               var1.setline(387);
               var3 = var1.getlocal(0).__getattr__("stream");
               Py.printlnv(var3);
               var1.setline(388);
               var3 = var1.getlocal(0).__getattr__("stream");
               Py.printlnv(var3);
               break;
            }

            var1.setlocal(4, var8);
            var1.setline(385);
            var5 = var1.getlocal(0).__getattr__("stats").__getitem__(var1.getlocal(4));
            PyObject[] var6 = Py.unpackSequence(var5, 5);
            PyObject var7 = var6[0];
            var1.setlocal(5, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(6, var7);
            var7 = null;
            var7 = var6[2];
            var1.setlocal(7, var7);
            var7 = null;
            var7 = var6[3];
            var1.setlocal(8, var7);
            var7 = null;
            var7 = var6[4];
            var1.setlocal(9, var7);
            var7 = null;
            var5 = null;
            var1.setline(386);
            var1.getlocal(0).__getattr__("print_call_line").__call__(var2, var1.getlocal(2), var1.getlocal(4), var1.getlocal(9), PyString.fromInterned("<-"));
         }
      }

      var1.setline(389);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject print_call_heading$18(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Function ").__getattr__("ljust").__call__(var2, var1.getlocal(1))._add(var1.getlocal(2)));
      var1.setline(394);
      var3 = var1.getglobal("False");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(395);
      var3 = var1.getlocal(0).__getattr__("stats").__getattr__("itervalues").__call__(var2).__iter__();

      while(true) {
         var1.setline(395);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 5);
         PyObject var6 = var5[0];
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
         var6 = var5[4];
         var1.setlocal(8, var6);
         var6 = null;
         var1.setline(396);
         if (var1.getlocal(8).__nonzero__()) {
            var1.setline(397);
            PyObject var7 = var1.getlocal(8).__getattr__("itervalues").__call__(var2).__getattr__("next").__call__(var2);
            var1.setlocal(9, var7);
            var5 = null;
            var1.setline(398);
            var7 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(9), var1.getglobal("tuple"));
            var1.setlocal(3, var7);
            var5 = null;
            break;
         }
      }

      var1.setline(400);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(401);
         var3 = var1.getlocal(0).__getattr__("stream");
         Py.println(var3, PyString.fromInterned(" ")._mul(var1.getlocal(1))._add(PyString.fromInterned("    ncalls  tottime  cumtime")));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_call_line$19(PyFrame var1, ThreadState var2) {
      var1.setline(404);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.printComma(var3, var1.getglobal("func_std_string").__call__(var2, var1.getlocal(2)).__getattr__("ljust").__call__(var2, var1.getlocal(1))._add(var1.getlocal(4)));
      var1.setline(405);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(406);
         var3 = var1.getlocal(0).__getattr__("stream");
         Py.printlnv(var3);
         var1.setline(407);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(408);
         var3 = var1.getlocal(3).__getattr__("keys").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(409);
         var1.getlocal(5).__getattr__("sort").__call__(var2);
         var1.setline(410);
         PyString var8 = PyString.fromInterned("");
         var1.setlocal(6, var8);
         var3 = null;
         var1.setline(411);
         var3 = var1.getlocal(5).__iter__();

         while(true) {
            var1.setline(411);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(7, var4);
            var1.setline(412);
            PyObject var5 = var1.getglobal("func_std_string").__call__(var2, var1.getlocal(7));
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(413);
            var5 = var1.getlocal(3).__getitem__(var1.getlocal(7));
            var1.setlocal(9, var5);
            var5 = null;
            var1.setline(414);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(9), var1.getglobal("tuple")).__nonzero__()) {
               var1.setline(415);
               var5 = var1.getlocal(9);
               PyObject[] var6 = Py.unpackSequence(var5, 4);
               PyObject var7 = var6[0];
               var1.setlocal(10, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(11, var7);
               var7 = null;
               var7 = var6[2];
               var1.setlocal(12, var7);
               var7 = null;
               var7 = var6[3];
               var1.setlocal(13, var7);
               var7 = null;
               var5 = null;
               var1.setline(416);
               var5 = var1.getlocal(10);
               PyObject var10000 = var5._ne(var1.getlocal(11));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(417);
                  var5 = PyString.fromInterned("%d/%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(11)}));
                  var1.setlocal(14, var5);
                  var5 = null;
               } else {
                  var1.setline(419);
                  var5 = PyString.fromInterned("%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(10)}));
                  var1.setlocal(14, var5);
                  var5 = null;
               }

               var1.setline(420);
               var5 = PyString.fromInterned("%s %s %s  %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(14).__getattr__("rjust").__call__(var2, Py.newInteger(7)._add(Py.newInteger(2)._mul(var1.getglobal("len").__call__(var2, var1.getlocal(6))))), var1.getglobal("f8").__call__(var2, var1.getlocal(12)), var1.getglobal("f8").__call__(var2, var1.getlocal(13)), var1.getlocal(8)}));
               var1.setlocal(14, var5);
               var5 = null;
               var1.setline(422);
               var5 = var1.getlocal(1)._add(Py.newInteger(1));
               var1.setlocal(15, var5);
               var5 = null;
            } else {
               var1.setline(424);
               var5 = PyString.fromInterned("%s(%r) %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(9), var1.getglobal("f8").__call__(var2, var1.getlocal(0).__getattr__("stats").__getitem__(var1.getlocal(7)).__getitem__(Py.newInteger(3)))}));
               var1.setlocal(14, var5);
               var5 = null;
               var1.setline(425);
               var5 = var1.getlocal(1)._add(Py.newInteger(3));
               var1.setlocal(15, var5);
               var5 = null;
            }

            var1.setline(426);
            var5 = var1.getlocal(0).__getattr__("stream");
            Py.println(var5, var1.getlocal(6)._mul(var1.getlocal(15))._add(var1.getlocal(14)));
            var1.setline(427);
            PyString var9 = PyString.fromInterned(" ");
            var1.setlocal(6, var9);
            var5 = null;
         }
      }
   }

   public PyObject print_title$20(PyFrame var1, ThreadState var2) {
      var1.setline(430);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.printComma(var3, PyString.fromInterned("   ncalls  tottime  percall  cumtime  percall"));
      var1.setline(431);
      var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("filename:lineno(function)"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_line$21(PyFrame var1, ThreadState var2) {
      var1.setline(434);
      PyObject var3 = var1.getlocal(0).__getattr__("stats").__getitem__(var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 5);
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
      var3 = null;
      var1.setline(435);
      var3 = var1.getglobal("str").__call__(var2, var1.getlocal(3));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(436);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._ne(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(437);
         var3 = var1.getlocal(7)._add(PyString.fromInterned("/"))._add(var1.getglobal("str").__call__(var2, var1.getlocal(2)));
         var1.setlocal(7, var3);
         var3 = null;
      }

      var1.setline(438);
      var3 = var1.getlocal(0).__getattr__("stream");
      Py.printComma(var3, var1.getlocal(7).__getattr__("rjust").__call__((ThreadState)var2, (PyObject)Py.newInteger(9)));
      var1.setline(439);
      var3 = var1.getlocal(0).__getattr__("stream");
      Py.printComma(var3, var1.getglobal("f8").__call__(var2, var1.getlocal(4)));
      var1.setline(440);
      var3 = var1.getlocal(3);
      var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(441);
         var3 = var1.getlocal(0).__getattr__("stream");
         Py.printComma(var3, PyString.fromInterned(" ")._mul(Py.newInteger(8)));
      } else {
         var1.setline(443);
         var3 = var1.getlocal(0).__getattr__("stream");
         Py.printComma(var3, var1.getglobal("f8").__call__(var2, var1.getglobal("float").__call__(var2, var1.getlocal(4))._div(var1.getlocal(3))));
      }

      var1.setline(444);
      var3 = var1.getlocal(0).__getattr__("stream");
      Py.printComma(var3, var1.getglobal("f8").__call__(var2, var1.getlocal(5)));
      var1.setline(445);
      var3 = var1.getlocal(2);
      var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(446);
         var3 = var1.getlocal(0).__getattr__("stream");
         Py.printComma(var3, PyString.fromInterned(" ")._mul(Py.newInteger(8)));
      } else {
         var1.setline(448);
         var3 = var1.getlocal(0).__getattr__("stream");
         Py.printComma(var3, var1.getglobal("f8").__call__(var2, var1.getglobal("float").__call__(var2, var1.getlocal(5))._div(var1.getlocal(2))));
      }

      var1.setline(449);
      var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, var1.getglobal("func_std_string").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TupleComp$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("This class provides a generic function for comparing any two tuples.\n    Each instance records a list of tuple-indices (from most significant\n    to least significant), and sort direction (ascending or decending) for\n    each tuple-index.  The compare functions can then be used as the function\n    argument to the system sort() function when a list of tuples need to be\n    sorted in the instances order."));
      var1.setline(457);
      PyString.fromInterned("This class provides a generic function for comparing any two tuples.\n    Each instance records a list of tuple-indices (from most significant\n    to least significant), and sort direction (ascending or decending) for\n    each tuple-index.  The compare functions can then be used as the function\n    argument to the system sort() function when a list of tuples need to be\n    sorted in the instances order.");
      var1.setline(459);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$23, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(462);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, compare$24, (PyObject)null);
      var1.setlocal("compare", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$23(PyFrame var1, ThreadState var2) {
      var1.setline(460);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("comp_select_list", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject compare$24(PyFrame var1, ThreadState var2) {
      var1.setline(463);
      PyObject var3 = var1.getlocal(0).__getattr__("comp_select_list").__iter__();

      PyObject var10000;
      PyObject var7;
      do {
         var1.setline(463);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(470);
            PyInteger var8 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var8;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(464);
         var7 = var1.getlocal(1).__getitem__(var1.getlocal(3));
         var1.setlocal(5, var7);
         var5 = null;
         var1.setline(465);
         var7 = var1.getlocal(2).__getitem__(var1.getlocal(3));
         var1.setlocal(6, var7);
         var5 = null;
         var1.setline(466);
         var7 = var1.getlocal(5);
         var10000 = var7._lt(var1.getlocal(6));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(467);
            var7 = var1.getlocal(4).__neg__();
            var1.f_lasti = -1;
            return var7;
         }

         var1.setline(468);
         var6 = var1.getlocal(5);
         var10000 = var6._gt(var1.getlocal(6));
         var6 = null;
      } while(!var10000.__nonzero__());

      var1.setline(469);
      var7 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject func_strip_path$25(PyFrame var1, ThreadState var2) {
      var1.setline(476);
      PyObject var3 = var1.getlocal(0);
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
      var1.setline(477);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(1)), var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject func_get_function_name$26(PyFrame var1, ThreadState var2) {
      var1.setline(480);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject func_std_string$27(PyFrame var1, ThreadState var2) {
      var1.setline(483);
      PyObject var3 = var1.getlocal(0).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null);
      PyObject var10000 = var3._eq(new PyTuple(new PyObject[]{PyString.fromInterned("~"), Py.newInteger(0)}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(485);
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(2));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(486);
         var10000 = var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(487);
            var3 = PyString.fromInterned("{%s}")._mod(var1.getlocal(1).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(489);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(491);
         var3 = PyString.fromInterned("%s:%d(%s)")._mod(var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject add_func_stats$28(PyFrame var1, ThreadState var2) {
      var1.setline(500);
      PyString.fromInterned("Add together all the stats for two profile entries.");
      var1.setline(501);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 5);
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
      var3 = null;
      var1.setline(502);
      var3 = var1.getlocal(0);
      var4 = Py.unpackSequence(var3, 5);
      var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(8, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(11, var5);
      var5 = null;
      var3 = null;
      var1.setline(503);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(2)._add(var1.getlocal(7)), var1.getlocal(3)._add(var1.getlocal(8)), var1.getlocal(4)._add(var1.getlocal(9)), var1.getlocal(5)._add(var1.getlocal(10)), var1.getglobal("add_callers").__call__(var2, var1.getlocal(11), var1.getlocal(6))});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject add_callers$29(PyFrame var1, ThreadState var2) {
      var1.setline(507);
      PyString.fromInterned("Combine two caller lists in a single list.");
      var1.setline(508);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(509);
      PyObject var8 = var1.getlocal(0).__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(509);
         PyObject var4 = var8.__iternext__();
         PyObject[] var5;
         PyObject var6;
         PyObject var9;
         if (var4 == null) {
            var1.setline(511);
            var8 = var1.getlocal(1).__getattr__("iteritems").__call__(var2).__iter__();

            while(true) {
               while(true) {
                  while(true) {
                     var1.setline(511);
                     var4 = var8.__iternext__();
                     if (var4 == null) {
                        var1.setline(522);
                        var8 = var1.getlocal(2);
                        var1.f_lasti = -1;
                        return var8;
                     }

                     var5 = Py.unpackSequence(var4, 2);
                     var6 = var5[0];
                     var1.setlocal(3, var6);
                     var6 = null;
                     var6 = var5[1];
                     var1.setlocal(4, var6);
                     var6 = null;
                     var1.setline(512);
                     var9 = var1.getlocal(3);
                     PyObject var10000 = var9._in(var1.getlocal(2));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(513);
                        if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("tuple")).__nonzero__()) {
                           var1.setline(515);
                           var10000 = var1.getglobal("tuple");
                           PyList var10002 = new PyList();
                           var9 = var10002.__getattr__("append");
                           var1.setlocal(5, var9);
                           var5 = null;
                           var1.setline(515);
                           var9 = var1.getglobal("zip").__call__(var2, var1.getlocal(4), var1.getlocal(2).__getitem__(var1.getlocal(3))).__iter__();

                           while(true) {
                              var1.setline(515);
                              var6 = var9.__iternext__();
                              if (var6 == null) {
                                 var1.setline(515);
                                 var1.dellocal(5);
                                 var9 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
                                 var1.getlocal(2).__setitem__(var1.getlocal(3), var9);
                                 var5 = null;
                                 break;
                              }

                              var1.setlocal(6, var6);
                              var1.setline(515);
                              var1.getlocal(5).__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(0))._add(var1.getlocal(6).__getitem__(Py.newInteger(1))));
                           }
                        } else {
                           var1.setline(519);
                           var10000 = var1.getlocal(2);
                           var9 = var1.getlocal(3);
                           var6 = var10000;
                           PyObject var7 = var6.__getitem__(var9);
                           var7 = var7._iadd(var1.getlocal(4));
                           var6.__setitem__(var9, var7);
                        }
                     } else {
                        var1.setline(521);
                        var9 = var1.getlocal(4);
                        var1.getlocal(2).__setitem__(var1.getlocal(3), var9);
                        var5 = null;
                     }
                  }
               }
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(510);
         var9 = var1.getlocal(4);
         var1.getlocal(2).__setitem__(var1.getlocal(3), var9);
         var5 = null;
      }
   }

   public PyObject count_calls$30(PyFrame var1, ThreadState var2) {
      var1.setline(525);
      PyString.fromInterned("Sum the caller statistics to get total number of calls received.");
      var1.setline(526);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(527);
      PyObject var6 = var1.getlocal(0).__getattr__("itervalues").__call__(var2).__iter__();

      while(true) {
         var1.setline(527);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(529);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(528);
         PyObject var5 = var1.getlocal(1);
         var5 = var5._iadd(var1.getlocal(2));
         var1.setlocal(1, var5);
      }
   }

   public PyObject f8$31(PyFrame var1, ThreadState var2) {
      var1.setline(536);
      PyObject var3 = PyString.fromInterned("%8.3f")._mod(var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ProfileBrowser$32(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(550);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$33, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(558);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, generic$34, (PyObject)null);
      var1.setlocal("generic", var4);
      var3 = null;
      var1.setline(582);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, generic_help$35, (PyObject)null);
      var1.setlocal("generic_help", var4);
      var3 = null;
      var1.setline(590);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_add$36, (PyObject)null);
      var1.setlocal("do_add", var4);
      var3 = null;
      var1.setline(596);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_add$37, (PyObject)null);
      var1.setlocal("help_add", var4);
      var3 = null;
      var1.setline(599);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_callees$38, (PyObject)null);
      var1.setlocal("do_callees", var4);
      var3 = null;
      var1.setline(601);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_callees$39, (PyObject)null);
      var1.setlocal("help_callees", var4);
      var3 = null;
      var1.setline(605);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_callers$40, (PyObject)null);
      var1.setlocal("do_callers", var4);
      var3 = null;
      var1.setline(607);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_callers$41, (PyObject)null);
      var1.setlocal("help_callers", var4);
      var3 = null;
      var1.setline(611);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_EOF$42, (PyObject)null);
      var1.setlocal("do_EOF", var4);
      var3 = null;
      var1.setline(614);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_EOF$43, (PyObject)null);
      var1.setlocal("help_EOF", var4);
      var3 = null;
      var1.setline(617);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_quit$44, (PyObject)null);
      var1.setlocal("do_quit", var4);
      var3 = null;
      var1.setline(619);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_quit$45, (PyObject)null);
      var1.setlocal("help_quit", var4);
      var3 = null;
      var1.setline(622);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_read$46, (PyObject)null);
      var1.setlocal("do_read", var4);
      var3 = null;
      var1.setline(639);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_read$47, (PyObject)null);
      var1.setlocal("help_read", var4);
      var3 = null;
      var1.setline(643);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_reverse$48, (PyObject)null);
      var1.setlocal("do_reverse", var4);
      var3 = null;
      var1.setline(649);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_reverse$49, (PyObject)null);
      var1.setlocal("help_reverse", var4);
      var3 = null;
      var1.setline(652);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_sort$50, (PyObject)null);
      var1.setlocal("do_sort", var4);
      var3 = null;
      var1.setline(664);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_sort$52, (PyObject)null);
      var1.setlocal("help_sort", var4);
      var3 = null;
      var1.setline(667);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, complete_sort$53, (PyObject)null);
      var1.setlocal("complete_sort", var4);
      var3 = null;
      var1.setline(670);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_stats$54, (PyObject)null);
      var1.setlocal("do_stats", var4);
      var3 = null;
      var1.setline(672);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_stats$55, (PyObject)null);
      var1.setlocal("help_stats", var4);
      var3 = null;
      var1.setline(676);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_strip$56, (PyObject)null);
      var1.setlocal("do_strip", var4);
      var3 = null;
      var1.setline(681);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_strip$57, (PyObject)null);
      var1.setlocal("help_strip", var4);
      var3 = null;
      var1.setline(684);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, help_help$58, (PyObject)null);
      var1.setlocal("help_help", var4);
      var3 = null;
      var1.setline(687);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, postcmd$59, (PyObject)null);
      var1.setlocal("postcmd", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$33(PyFrame var1, ThreadState var2) {
      var1.setline(551);
      var1.getglobal("cmd").__getattr__("Cmd").__getattr__("__init__").__call__(var2, var1.getlocal(0));
      var1.setline(552);
      PyString var3 = PyString.fromInterned("% ");
      var1.getlocal(0).__setattr__((String)"prompt", var3);
      var3 = null;
      var1.setline(553);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("stats", var4);
      var3 = null;
      var1.setline(554);
      var4 = var1.getglobal("sys").__getattr__("stdout");
      var1.getlocal(0).__setattr__("stream", var4);
      var3 = null;
      var1.setline(555);
      var4 = var1.getlocal(1);
      PyObject var10000 = var4._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(556);
         var1.getlocal(0).__getattr__("do_read").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject generic$34(PyFrame var1, ThreadState var2) {
      var1.setline(559);
      PyObject var3 = var1.getlocal(2).__getattr__("split").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(560);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(561);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(561);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(577);
            if (var1.getlocal(0).__getattr__("stats").__nonzero__()) {
               var1.setline(578);
               var10000 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("stats"), var1.getlocal(1));
               PyObject[] var10 = Py.EmptyObjects;
               String[] var9 = new String[0];
               var10000._callextra(var10, var9, var1.getlocal(4), (PyObject)null);
               var3 = null;
            } else {
               var1.setline(580);
               var3 = var1.getlocal(0).__getattr__("stream");
               Py.println(var3, PyString.fromInterned("No statistics object is loaded."));
            }

            var1.setline(581);
            PyInteger var11 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var11;
         }

         var1.setlocal(5, var4);

         try {
            var1.setline(563);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(5)));
         } catch (Throwable var7) {
            PyException var5 = Py.setException(var7, var1);
            if (!var5.match(var1.getglobal("ValueError"))) {
               throw var5;
            }

            var1.setline(566);

            try {
               var1.setline(568);
               PyObject var12 = var1.getglobal("float").__call__(var2, var1.getlocal(5));
               var1.setlocal(6, var12);
               var5 = null;
               var1.setline(569);
               var12 = var1.getlocal(6);
               var10000 = var12._gt(Py.newInteger(1));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var12 = var1.getlocal(6);
                  var10000 = var12._lt(Py.newInteger(0));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(570);
                  var12 = var1.getlocal(0).__getattr__("stream");
                  Py.println(var12, PyString.fromInterned("Fraction argument must be in [0, 1]"));
               } else {
                  var1.setline(572);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6));
               }
            } catch (Throwable var6) {
               var5 = Py.setException(var6, var1);
               if (!var5.match(var1.getglobal("ValueError"))) {
                  throw var5;
               }

               var1.setline(575);
               var1.setline(576);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(5));
            }
         }
      }
   }

   public PyObject generic_help$35(PyFrame var1, ThreadState var2) {
      var1.setline(583);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Arguments may be:"));
      var1.setline(584);
      var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("* An integer maximum number of entries to print."));
      var1.setline(585);
      var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("* A decimal fractional number between 0 and 1, controlling"));
      var1.setline(586);
      var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("  what fraction of selected entries to print."));
      var1.setline(587);
      var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("* A regular expression; only entries with function names"));
      var1.setline(588);
      var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("  that match it are printed."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_add$36(PyFrame var1, ThreadState var2) {
      var1.setline(591);
      if (var1.getlocal(0).__getattr__("stats").__nonzero__()) {
         var1.setline(592);
         var1.getlocal(0).__getattr__("stats").__getattr__("add").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(594);
         PyObject var3 = var1.getlocal(0).__getattr__("stream");
         Py.println(var3, PyString.fromInterned("No statistics object is loaded."));
      }

      var1.setline(595);
      PyInteger var4 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject help_add$37(PyFrame var1, ThreadState var2) {
      var1.setline(597);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Add profile info from given file to current statistics object."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_callees$38(PyFrame var1, ThreadState var2) {
      var1.setline(600);
      PyObject var3 = var1.getlocal(0).__getattr__("generic").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("print_callees"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject help_callees$39(PyFrame var1, ThreadState var2) {
      var1.setline(602);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Print callees statistics from the current stat object."));
      var1.setline(603);
      var1.getlocal(0).__getattr__("generic_help").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_callers$40(PyFrame var1, ThreadState var2) {
      var1.setline(606);
      PyObject var3 = var1.getlocal(0).__getattr__("generic").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("print_callers"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject help_callers$41(PyFrame var1, ThreadState var2) {
      var1.setline(608);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Print callers statistics from the current stat object."));
      var1.setline(609);
      var1.getlocal(0).__getattr__("generic_help").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_EOF$42(PyFrame var1, ThreadState var2) {
      var1.setline(612);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned(""));
      var1.setline(613);
      PyInteger var4 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject help_EOF$43(PyFrame var1, ThreadState var2) {
      var1.setline(615);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Leave the profile brower."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_quit$44(PyFrame var1, ThreadState var2) {
      var1.setline(618);
      PyInteger var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject help_quit$45(PyFrame var1, ThreadState var2) {
      var1.setline(620);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Leave the profile brower."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_read$46(PyFrame var1, ThreadState var2) {
      var1.setline(623);
      PyException var3;
      PyObject var6;
      if (var1.getlocal(1).__nonzero__()) {
         try {
            var1.setline(625);
            var6 = var1.getglobal("Stats").__call__(var2, var1.getlocal(1));
            var1.getlocal(0).__setattr__("stats", var6);
            var3 = null;
         } catch (Throwable var5) {
            var3 = Py.setException(var5, var1);
            PyObject var4;
            if (var3.match(var1.getglobal("IOError"))) {
               var4 = var3.value;
               var1.setlocal(2, var4);
               var4 = null;
               var1.setline(627);
               var4 = var1.getlocal(0).__getattr__("stream");
               Py.println(var4, var1.getlocal(2).__getitem__(Py.newInteger(1)));
               var1.setline(628);
               var1.f_lasti = -1;
               return Py.None;
            }

            if (var3.match(var1.getglobal("Exception"))) {
               var4 = var3.value;
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(630);
               var4 = var1.getlocal(0).__getattr__("stream");
               Py.printComma(var4, var1.getlocal(3).__getattr__("__class__").__getattr__("__name__")._add(PyString.fromInterned(":")));
               Py.println(var4, var1.getlocal(3));
               var1.setline(631);
               var1.f_lasti = -1;
               return Py.None;
            }

            throw var3;
         }

         var1.setline(632);
         var6 = var1.getlocal(1)._add(PyString.fromInterned("% "));
         var1.getlocal(0).__setattr__("prompt", var6);
         var3 = null;
      } else {
         var1.setline(633);
         var6 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("prompt"));
         PyObject var10000 = var6._gt(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(634);
            var6 = var1.getlocal(0).__getattr__("prompt").__getslice__((PyObject)null, Py.newInteger(-2), (PyObject)null);
            var1.setlocal(1, var6);
            var3 = null;
            var1.setline(635);
            var1.getlocal(0).__getattr__("do_read").__call__(var2, var1.getlocal(1));
         } else {
            var1.setline(637);
            var6 = var1.getlocal(0).__getattr__("stream");
            Py.println(var6, PyString.fromInterned("No statistics object is current -- cannot reload."));
         }
      }

      var1.setline(638);
      PyInteger var7 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject help_read$47(PyFrame var1, ThreadState var2) {
      var1.setline(640);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Read in profile data from a specified file."));
      var1.setline(641);
      var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Without argument, reload the current file."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_reverse$48(PyFrame var1, ThreadState var2) {
      var1.setline(644);
      if (var1.getlocal(0).__getattr__("stats").__nonzero__()) {
         var1.setline(645);
         var1.getlocal(0).__getattr__("stats").__getattr__("reverse_order").__call__(var2);
      } else {
         var1.setline(647);
         PyObject var3 = var1.getlocal(0).__getattr__("stream");
         Py.println(var3, PyString.fromInterned("No statistics object is loaded."));
      }

      var1.setline(648);
      PyInteger var4 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject help_reverse$49(PyFrame var1, ThreadState var2) {
      var1.setline(650);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Reverse the sort order of the profiling report."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_sort$50(PyFrame var1, ThreadState var2) {
      var1.setline(653);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("stats").__not__().__nonzero__()) {
         var1.setline(654);
         var3 = var1.getlocal(0).__getattr__("stream");
         Py.println(var3, PyString.fromInterned("No statistics object is loaded."));
         var1.setline(655);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(656);
         var3 = var1.getlocal(0).__getattr__("stats").__getattr__("get_sort_arg_defs").__call__(var2);
         var1.setderef(0, var3);
         var3 = null;
         var1.setline(657);
         PyObject var10000 = var1.getlocal(1);
         PyObject[] var7;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("all");
            var1.setline(657);
            PyObject var10004 = var1.f_globals;
            var7 = Py.EmptyObjects;
            PyCode var10006 = f$51;
            PyObject[] var4 = new PyObject[]{var1.getclosure(0)};
            PyFunction var8 = new PyFunction(var10004, var7, var10006, (PyObject)null, var4);
            PyObject var10002 = var8.__call__(var2, var1.getlocal(1).__getattr__("split").__call__(var2).__iter__());
            Arrays.fill(var7, (Object)null);
            var10000 = var10000.__call__(var2, var10002);
         }

         if (var10000.__nonzero__()) {
            var1.setline(658);
            var10000 = var1.getlocal(0).__getattr__("stats").__getattr__("sort_stats");
            var7 = Py.EmptyObjects;
            String[] var9 = new String[0];
            var10000._callextra(var7, var9, var1.getlocal(1).__getattr__("split").__call__(var2), (PyObject)null);
            var3 = null;
         } else {
            var1.setline(660);
            var3 = var1.getlocal(0).__getattr__("stream");
            Py.println(var3, PyString.fromInterned("Valid sort keys (unique prefixes are accepted):"));
            var1.setline(661);
            var3 = var1.getglobal("Stats").__getattr__("sort_arg_dict_default").__getattr__("iteritems").__call__(var2).__iter__();

            while(true) {
               var1.setline(661);
               PyObject var10 = var3.__iternext__();
               if (var10 == null) {
                  break;
               }

               PyObject[] var5 = Py.unpackSequence(var10, 2);
               PyObject var6 = var5[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(4, var6);
               var6 = null;
               var1.setline(662);
               PyObject var12 = var1.getlocal(0).__getattr__("stream");
               Py.println(var12, PyString.fromInterned("%s -- %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4).__getitem__(Py.newInteger(1))})));
            }
         }

         var1.setline(663);
         PyInteger var11 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var11;
      }
   }

   public PyObject f$51(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var7;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(657);
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

            var7 = (PyObject)var10000;
      }

      var1.setline(657);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(657);
         var1.setline(657);
         PyObject var6 = var1.getlocal(1);
         var7 = var6._in(var1.getderef(0));
         var5 = null;
         var1.f_lasti = 1;
         var5 = new Object[7];
         var5[3] = var3;
         var5[4] = var4;
         var1.f_savedlocals = var5;
         return var7;
      }
   }

   public PyObject help_sort$52(PyFrame var1, ThreadState var2) {
      var1.setline(665);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Sort profile data according to specified keys."));
      var1.setline(666);
      var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("(Typing `sort' without arguments lists valid keys.)"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject complete_sort$53(PyFrame var1, ThreadState var2) {
      var1.setline(668);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(668);
      var3 = var1.getglobal("Stats").__getattr__("sort_arg_dict_default").__iter__();

      while(true) {
         var1.setline(668);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(668);
            var1.dellocal(3);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(4, var4);
         var1.setline(668);
         if (var1.getlocal(4).__getattr__("startswith").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(668);
            var1.getlocal(3).__call__(var2, var1.getlocal(4));
         }
      }
   }

   public PyObject do_stats$54(PyFrame var1, ThreadState var2) {
      var1.setline(671);
      PyObject var3 = var1.getlocal(0).__getattr__("generic").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("print_stats"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject help_stats$55(PyFrame var1, ThreadState var2) {
      var1.setline(673);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Print statistics from the current stat object."));
      var1.setline(674);
      var1.getlocal(0).__getattr__("generic_help").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_strip$56(PyFrame var1, ThreadState var2) {
      var1.setline(677);
      if (var1.getlocal(0).__getattr__("stats").__nonzero__()) {
         var1.setline(678);
         var1.getlocal(0).__getattr__("stats").__getattr__("strip_dirs").__call__(var2);
      } else {
         var1.setline(680);
         PyObject var3 = var1.getlocal(0).__getattr__("stream");
         Py.println(var3, PyString.fromInterned("No statistics object is loaded."));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_strip$57(PyFrame var1, ThreadState var2) {
      var1.setline(682);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Strip leading path information from filenames in the report."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject help_help$58(PyFrame var1, ThreadState var2) {
      var1.setline(685);
      PyObject var3 = var1.getlocal(0).__getattr__("stream");
      Py.println(var3, PyString.fromInterned("Show help for a given command."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject postcmd$59(PyFrame var1, ThreadState var2) {
      var1.setline(688);
      PyObject var3;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(689);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(690);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public pstats$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Stats$1 = Py.newCode(0, var2, var1, "Stats", 32, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "kwds", "keys", "extras", "_[74_32]", "k", "arg"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 62, true, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "trouble"};
      init$3 = Py.newCode(2, var2, var1, "init", 84, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "f", "file_stats"};
      load_stats$4 = Py.newCode(2, var2, var1, "load_stats", 106, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "func", "cc", "nc", "tt", "ct", "callers"};
      get_top_level_stats$5 = Py.newCode(1, var2, var1, "get_top_level_stats", 127, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg_list", "other", "func", "stat", "old_func_stat"};
      add$6 = Py.newCode(2, var2, var1, "add", 137, true, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "f"};
      dump_stats$7 = Py.newCode(2, var2, var1, "dump_stats", 163, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dict", "bad_list", "word", "tup", "fragment"};
      get_sort_arg_defs$8 = Py.newCode(1, var2, var1, "get_sort_arg_defs", 190, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "field", "sort_arg_defs", "sort_tuple", "connector", "word", "stats_list", "func", "cc", "nc", "tt", "ct", "callers", "fcn_list", "tuple"};
      sort_stats$9 = Py.newCode(2, var2, var1, "sort_stats", 209, true, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      reverse_order$10 = Py.newCode(1, var2, var1, "reverse_order", 241, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "oldstats", "newstats", "max_name_len", "func", "cc", "nc", "tt", "ct", "callers", "newfunc", "newcallers", "func2", "caller", "old_top", "new_top"};
      strip_dirs$11 = Py.newCode(1, var2, var1, "strip_dirs", 246, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "all_callees", "func", "cc", "nc", "tt", "ct", "callers", "func2", "caller"};
      calc_callees$12 = Py.newCode(1, var2, var1, "calc_callees", 275, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sel", "list", "msg", "new_list", "rex", "func", "count"};
      eval_print_amount$13 = Py.newCode(4, var2, var1, "eval_print_amount", 293, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sel_list", "width", "stat_list", "msg", "selection", "count", "func"};
      get_print_list$14 = Py.newCode(2, var2, var1, "get_print_list", 319, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "amount", "filename", "indent", "func", "width", "list"};
      print_stats$15 = Py.newCode(2, var2, var1, "print_stats", 343, true, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "amount", "width", "list", "func"};
      print_callees$16 = Py.newCode(2, var2, var1, "print_callees", 365, true, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "amount", "width", "list", "func", "cc", "nc", "tt", "ct", "callers"};
      print_callers$17 = Py.newCode(2, var2, var1, "print_callers", 380, true, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name_size", "column_title", "subheader", "cc", "nc", "tt", "ct", "callers", "value"};
      print_call_heading$18 = Py.newCode(3, var2, var1, "print_call_heading", 391, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name_size", "source", "call_dict", "arrow", "clist", "indent", "func", "name", "value", "nc", "cc", "tt", "ct", "substats", "left_width"};
      print_call_line$19 = Py.newCode(5, var2, var1, "print_call_line", 403, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      print_title$20 = Py.newCode(1, var2, var1, "print_title", 429, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "func", "cc", "nc", "tt", "ct", "callers", "c"};
      print_line$21 = Py.newCode(2, var2, var1, "print_line", 433, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TupleComp$22 = Py.newCode(0, var2, var1, "TupleComp", 451, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "comp_select_list"};
      __init__$23 = Py.newCode(2, var2, var1, "__init__", 459, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "left", "right", "index", "direction", "l", "r"};
      compare$24 = Py.newCode(3, var2, var1, "compare", 462, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func_name", "filename", "line", "name"};
      func_strip_path$25 = Py.newCode(1, var2, var1, "func_strip_path", 475, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func"};
      func_get_function_name$26 = Py.newCode(1, var2, var1, "func_get_function_name", 479, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func_name", "name"};
      func_std_string$27 = Py.newCode(1, var2, var1, "func_std_string", 482, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"target", "source", "cc", "nc", "tt", "ct", "callers", "t_cc", "t_nc", "t_tt", "t_ct", "t_callers"};
      add_func_stats$28 = Py.newCode(2, var2, var1, "add_func_stats", 499, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"target", "source", "new_callers", "func", "caller", "_[515_43]", "i"};
      add_callers$29 = Py.newCode(2, var2, var1, "add_callers", 506, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"callers", "nc", "calls"};
      count_calls$30 = Py.newCode(1, var2, var1, "count_calls", 524, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f8$31 = Py.newCode(1, var2, var1, "f8", 535, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ProfileBrowser$32 = Py.newCode(0, var2, var1, "ProfileBrowser", 549, false, false, self, 32, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "profile"};
      __init__$33 = Py.newCode(2, var2, var1, "__init__", 550, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fn", "line", "args", "processed", "term", "frac"};
      generic$34 = Py.newCode(3, var2, var1, "generic", 558, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      generic_help$35 = Py.newCode(1, var2, var1, "generic_help", 582, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      do_add$36 = Py.newCode(2, var2, var1, "do_add", 590, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_add$37 = Py.newCode(1, var2, var1, "help_add", 596, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      do_callees$38 = Py.newCode(2, var2, var1, "do_callees", 599, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_callees$39 = Py.newCode(1, var2, var1, "help_callees", 601, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      do_callers$40 = Py.newCode(2, var2, var1, "do_callers", 605, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_callers$41 = Py.newCode(1, var2, var1, "help_callers", 607, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      do_EOF$42 = Py.newCode(2, var2, var1, "do_EOF", 611, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_EOF$43 = Py.newCode(1, var2, var1, "help_EOF", 614, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      do_quit$44 = Py.newCode(2, var2, var1, "do_quit", 617, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_quit$45 = Py.newCode(1, var2, var1, "help_quit", 619, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "args", "err"};
      do_read$46 = Py.newCode(2, var2, var1, "do_read", 622, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_read$47 = Py.newCode(1, var2, var1, "help_read", 639, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      do_reverse$48 = Py.newCode(2, var2, var1, "do_reverse", 643, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_reverse$49 = Py.newCode(1, var2, var1, "help_reverse", 649, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line", "_(657_28)", "key", "value", "abbrevs"};
      String[] var10001 = var2;
      pstats$py var10007 = self;
      var2 = new String[]{"abbrevs"};
      do_sort$50 = Py.newCode(2, var10001, var1, "do_sort", 652, false, false, var10007, 50, var2, (String[])null, 1, 4097);
      var2 = new String[]{"_(x)", "x"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"abbrevs"};
      f$51 = Py.newCode(1, var10001, var1, "<genexpr>", 657, false, false, var10007, 51, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self"};
      help_sort$52 = Py.newCode(1, var2, var1, "help_sort", 664, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "args", "_[668_20]", "a"};
      complete_sort$53 = Py.newCode(3, var2, var1, "complete_sort", 667, true, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      do_stats$54 = Py.newCode(2, var2, var1, "do_stats", 670, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_stats$55 = Py.newCode(1, var2, var1, "help_stats", 672, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      do_strip$56 = Py.newCode(2, var2, var1, "do_strip", 676, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_strip$57 = Py.newCode(1, var2, var1, "help_strip", 681, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      help_help$58 = Py.newCode(1, var2, var1, "help_help", 684, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stop", "line"};
      postcmd$59 = Py.newCode(3, var2, var1, "postcmd", 687, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new pstats$py("pstats$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(pstats$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Stats$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.init$3(var2, var3);
         case 4:
            return this.load_stats$4(var2, var3);
         case 5:
            return this.get_top_level_stats$5(var2, var3);
         case 6:
            return this.add$6(var2, var3);
         case 7:
            return this.dump_stats$7(var2, var3);
         case 8:
            return this.get_sort_arg_defs$8(var2, var3);
         case 9:
            return this.sort_stats$9(var2, var3);
         case 10:
            return this.reverse_order$10(var2, var3);
         case 11:
            return this.strip_dirs$11(var2, var3);
         case 12:
            return this.calc_callees$12(var2, var3);
         case 13:
            return this.eval_print_amount$13(var2, var3);
         case 14:
            return this.get_print_list$14(var2, var3);
         case 15:
            return this.print_stats$15(var2, var3);
         case 16:
            return this.print_callees$16(var2, var3);
         case 17:
            return this.print_callers$17(var2, var3);
         case 18:
            return this.print_call_heading$18(var2, var3);
         case 19:
            return this.print_call_line$19(var2, var3);
         case 20:
            return this.print_title$20(var2, var3);
         case 21:
            return this.print_line$21(var2, var3);
         case 22:
            return this.TupleComp$22(var2, var3);
         case 23:
            return this.__init__$23(var2, var3);
         case 24:
            return this.compare$24(var2, var3);
         case 25:
            return this.func_strip_path$25(var2, var3);
         case 26:
            return this.func_get_function_name$26(var2, var3);
         case 27:
            return this.func_std_string$27(var2, var3);
         case 28:
            return this.add_func_stats$28(var2, var3);
         case 29:
            return this.add_callers$29(var2, var3);
         case 30:
            return this.count_calls$30(var2, var3);
         case 31:
            return this.f8$31(var2, var3);
         case 32:
            return this.ProfileBrowser$32(var2, var3);
         case 33:
            return this.__init__$33(var2, var3);
         case 34:
            return this.generic$34(var2, var3);
         case 35:
            return this.generic_help$35(var2, var3);
         case 36:
            return this.do_add$36(var2, var3);
         case 37:
            return this.help_add$37(var2, var3);
         case 38:
            return this.do_callees$38(var2, var3);
         case 39:
            return this.help_callees$39(var2, var3);
         case 40:
            return this.do_callers$40(var2, var3);
         case 41:
            return this.help_callers$41(var2, var3);
         case 42:
            return this.do_EOF$42(var2, var3);
         case 43:
            return this.help_EOF$43(var2, var3);
         case 44:
            return this.do_quit$44(var2, var3);
         case 45:
            return this.help_quit$45(var2, var3);
         case 46:
            return this.do_read$46(var2, var3);
         case 47:
            return this.help_read$47(var2, var3);
         case 48:
            return this.do_reverse$48(var2, var3);
         case 49:
            return this.help_reverse$49(var2, var3);
         case 50:
            return this.do_sort$50(var2, var3);
         case 51:
            return this.f$51(var2, var3);
         case 52:
            return this.help_sort$52(var2, var3);
         case 53:
            return this.complete_sort$53(var2, var3);
         case 54:
            return this.do_stats$54(var2, var3);
         case 55:
            return this.help_stats$55(var2, var3);
         case 56:
            return this.do_strip$56(var2, var3);
         case 57:
            return this.help_strip$57(var2, var3);
         case 58:
            return this.help_help$58(var2, var3);
         case 59:
            return this.postcmd$59(var2, var3);
         default:
            return null;
      }
   }
}
