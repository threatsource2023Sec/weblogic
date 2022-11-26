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
@Filename("timeit.py")
public class timeit$py extends PyFunctionTable implements PyRunnable {
   static timeit$py self;
   static final PyCode f$0;
   static final PyCode reindent$1;
   static final PyCode _template_func$2;
   static final PyCode inner$3;
   static final PyCode Timer$4;
   static final PyCode __init__$5;
   static final PyCode setup$6;
   static final PyCode print_exc$7;
   static final PyCode timeit$8;
   static final PyCode repeat$9;
   static final PyCode timeit$10;
   static final PyCode repeat$11;
   static final PyCode main$12;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tool for measuring execution time of small code snippets.\n\nThis module avoids a number of common traps for measuring execution\ntimes.  See also Tim Peters' introduction to the Algorithms chapter in\nthe Python Cookbook, published by O'Reilly.\n\nLibrary usage: see the Timer class.\n\nCommand line usage:\n    python timeit.py [-n N] [-r N] [-s S] [-t] [-c] [-h] [--] [statement]\n\nOptions:\n  -n/--number N: how many times to execute 'statement' (default: see below)\n  -r/--repeat N: how many times to repeat the timer (default 3)\n  -s/--setup S: statement to be executed once initially (default 'pass')\n  -t/--time: use time.time() (default on Unix)\n  -c/--clock: use time.clock() (default on Windows)\n  -v/--verbose: print raw timing results; repeat for more digits precision\n  -h/--help: print this usage message and exit\n  --: separate options from statement, use when statement starts with -\n  statement: statement to be timed (default 'pass')\n\nA multi-line statement may be given by specifying each line as a\nseparate argument; indented lines are possible by enclosing an\nargument in quotes and using leading spaces.  Multiple -s options are\ntreated similarly.\n\nIf -n is not given, a suitable number of loops is calculated by trying\nsuccessive powers of 10 until the total time is at least 0.2 seconds.\n\nThe difference in default timer function is because on Windows,\nclock() has microsecond granularity but time()'s granularity is 1/60th\nof a second; on Unix, clock() has 1/100th of a second granularity and\ntime() is much more precise.  On either platform, the default timer\nfunctions measure wall clock time, not the CPU time.  This means that\nother processes running on the same computer may interfere with the\ntiming.  The best thing to do when accurate timing is necessary is to\nrepeat the timing a few times and use the best time.  The -r option is\ngood for this; the default of 3 repetitions is probably enough in most\ncases.  On Unix, you can use clock() to measure CPU time.\n\nNote: there is a certain baseline overhead associated with executing a\npass statement.  The code here doesn't try to hide it, but you should\nbe aware of it.  The baseline overhead can be measured by invoking the\nprogram without arguments.\n\nThe baseline overhead differs between Python versions!  Also, to\nfairly compare older Python versions to Python 2.3, you may want to\nuse python -O for the older versions to avoid timing SET_LINENO\ninstructions.\n"));
      var1.setline(53);
      PyString.fromInterned("Tool for measuring execution time of small code snippets.\n\nThis module avoids a number of common traps for measuring execution\ntimes.  See also Tim Peters' introduction to the Algorithms chapter in\nthe Python Cookbook, published by O'Reilly.\n\nLibrary usage: see the Timer class.\n\nCommand line usage:\n    python timeit.py [-n N] [-r N] [-s S] [-t] [-c] [-h] [--] [statement]\n\nOptions:\n  -n/--number N: how many times to execute 'statement' (default: see below)\n  -r/--repeat N: how many times to repeat the timer (default 3)\n  -s/--setup S: statement to be executed once initially (default 'pass')\n  -t/--time: use time.time() (default on Unix)\n  -c/--clock: use time.clock() (default on Windows)\n  -v/--verbose: print raw timing results; repeat for more digits precision\n  -h/--help: print this usage message and exit\n  --: separate options from statement, use when statement starts with -\n  statement: statement to be timed (default 'pass')\n\nA multi-line statement may be given by specifying each line as a\nseparate argument; indented lines are possible by enclosing an\nargument in quotes and using leading spaces.  Multiple -s options are\ntreated similarly.\n\nIf -n is not given, a suitable number of loops is calculated by trying\nsuccessive powers of 10 until the total time is at least 0.2 seconds.\n\nThe difference in default timer function is because on Windows,\nclock() has microsecond granularity but time()'s granularity is 1/60th\nof a second; on Unix, clock() has 1/100th of a second granularity and\ntime() is much more precise.  On either platform, the default timer\nfunctions measure wall clock time, not the CPU time.  This means that\nother processes running on the same computer may interfere with the\ntiming.  The best thing to do when accurate timing is necessary is to\nrepeat the timing a few times and use the best time.  The -r option is\ngood for this; the default of 3 repetitions is probably enough in most\ncases.  On Unix, you can use clock() to measure CPU time.\n\nNote: there is a certain baseline overhead associated with executing a\npass statement.  The code here doesn't try to hide it, but you should\nbe aware of it.  The baseline overhead can be measured by invoking the\nprogram without arguments.\n\nThe baseline overhead differs between Python versions!  Also, to\nfairly compare older Python versions to Python 2.3, you may want to\nuse python -O for the older versions to avoid timing SET_LINENO\ninstructions.\n");
      var1.setline(55);
      PyObject var3 = imp.importOne("gc", var1, -1);
      var1.setlocal("gc", var3);
      var3 = null;
      var1.setline(56);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(57);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;

      PyObject var4;
      try {
         var1.setline(59);
         var3 = imp.importOne("itertools", var1, -1);
         var1.setlocal("itertools", var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getname("ImportError"))) {
            throw var6;
         }

         var1.setline(62);
         var4 = var1.getname("None");
         var1.setlocal("itertools", var4);
         var4 = null;
      }

      var1.setline(64);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("Timer")});
      var1.setlocal("__all__", var7);
      var3 = null;
      var1.setline(66);
      PyString var8 = PyString.fromInterned("<timeit-src>");
      var1.setlocal("dummy_src_name", var8);
      var3 = null;
      var1.setline(67);
      PyInteger var9 = Py.newInteger(1000000);
      var1.setlocal("default_number", var9);
      var3 = null;
      var1.setline(68);
      var9 = Py.newInteger(3);
      var1.setlocal("default_repeat", var9);
      var3 = null;
      var1.setline(70);
      var3 = var1.getname("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(72);
         var3 = var1.getname("time").__getattr__("clock");
         var1.setlocal("default_timer", var3);
         var3 = null;
      } else {
         var1.setline(75);
         var3 = var1.getname("time").__getattr__("time");
         var1.setlocal("default_timer", var3);
         var3 = null;
      }

      var1.setline(80);
      var8 = PyString.fromInterned("\ndef inner(_it, _timer):\n    %(setup)s\n    _t0 = _timer()\n    for _i in _it:\n        %(stmt)s\n    _t1 = _timer()\n    return _t1 - _t0\n");
      var1.setlocal("template", var8);
      var3 = null;
      var1.setline(90);
      PyObject[] var10 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var10, reindent$1, PyString.fromInterned("Helper to reindent a multi-line statement."));
      var1.setlocal("reindent", var11);
      var3 = null;
      var1.setline(94);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, _template_func$2, PyString.fromInterned("Create a timer function. Used if the \"statement\" is a callable."));
      var1.setlocal("_template_func", var11);
      var3 = null;
      var1.setline(105);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("Timer", var10, Timer$4);
      var1.setlocal("Timer", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(228);
      var10 = new PyObject[]{PyString.fromInterned("pass"), PyString.fromInterned("pass"), var1.getname("default_timer"), var1.getname("default_number")};
      var11 = new PyFunction(var1.f_globals, var10, timeit$10, PyString.fromInterned("Convenience function to create Timer object and call timeit method."));
      var1.setlocal("timeit", var11);
      var3 = null;
      var1.setline(233);
      var10 = new PyObject[]{PyString.fromInterned("pass"), PyString.fromInterned("pass"), var1.getname("default_timer"), var1.getname("default_repeat"), var1.getname("default_number")};
      var11 = new PyFunction(var1.f_globals, var10, repeat$11, PyString.fromInterned("Convenience function to create Timer object and call repeat method."));
      var1.setlocal("repeat", var11);
      var3 = null;
      var1.setline(238);
      var10 = new PyObject[]{var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var10, main$12, PyString.fromInterned("Main program, used when run as a script.\n\n    The optional argument specifies the command line to be parsed,\n    defaulting to sys.argv[1:].\n\n    The return value is an exit code to be passed to sys.exit(); it\n    may be None to indicate success.\n\n    When an exception happens during timing, a traceback is printed to\n    stderr and the return value is 1.  Exceptions at other times\n    (including the template compilation) are not caught.\n    "));
      var1.setlocal("main", var11);
      var3 = null;
      var1.setline(330);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(331);
         var1.getname("sys").__getattr__("exit").__call__(var2, var1.getname("main").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject reindent$1(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyString.fromInterned("Helper to reindent a multi-line statement.");
      var1.setline(92);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)PyString.fromInterned("\n")._add(PyString.fromInterned(" ")._mul(var1.getlocal(1))));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _template_func$2(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(95);
      PyString.fromInterned("Create a timer function. Used if the \"statement\" is a callable.");
      var1.setline(96);
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = inner$3;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(103);
      PyObject var5 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject inner$3(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      var1.getderef(0).__call__(var2);
      var1.setline(98);
      PyObject var3 = var1.getlocal(1).__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(99);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(99);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(101);
            var3 = var1.getlocal(1).__call__(var2);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(102);
            var3 = var1.getlocal(5)._sub(var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(4, var4);
         var1.setline(100);
         var1.getlocal(2).__call__(var2);
      }
   }

   public PyObject Timer$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class for timing execution speed of small code snippets.\n\n    The constructor takes a statement to be timed, an additional\n    statement used for setup, and a timer function.  Both statements\n    default to 'pass'; the timer function is platform-dependent (see\n    module doc string).\n\n    To measure the execution time of the first statement, use the\n    timeit() method.  The repeat() method is a convenience to call\n    timeit() multiple times and return a list of results.\n\n    The statements may contain newlines, as long as they don't contain\n    multi-line string literals.\n    "));
      var1.setline(119);
      PyString.fromInterned("Class for timing execution speed of small code snippets.\n\n    The constructor takes a statement to be timed, an additional\n    statement used for setup, and a timer function.  Both statements\n    default to 'pass'; the timer function is platform-dependent (see\n    module doc string).\n\n    To measure the execution time of the first statement, use the\n    timeit() method.  The repeat() method is a convenience to call\n    timeit() multiple times and return a list of results.\n\n    The statements may contain newlines, as long as they don't contain\n    multi-line string literals.\n    ");
      var1.setline(121);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("pass"), PyString.fromInterned("pass"), var1.getname("default_timer")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$5, PyString.fromInterned("Constructor.  See class doc string."));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(151);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, print_exc$7, PyString.fromInterned("Helper to print a traceback from the timed code.\n\n        Typical use:\n\n            t = Timer(...)       # outside the try/except\n            try:\n                t.timeit(...)    # or t.repeat(...)\n            except:\n                t.print_exc()\n\n        The advantage over the standard traceback is that source lines\n        in the compiled template will be displayed.\n\n        The optional file argument directs where the traceback is\n        sent; it defaults to sys.stderr.\n        "));
      var1.setlocal("print_exc", var4);
      var3 = null;
      var1.setline(178);
      var3 = new PyObject[]{var1.getname("default_number")};
      var4 = new PyFunction(var1.f_globals, var3, timeit$8, PyString.fromInterned("Time 'number' executions of the main statement.\n\n        To be precise, this executes the setup statement once, and\n        then returns the time it takes to execute the main statement\n        a number of times, as a float measured in seconds.  The\n        argument is the number of times through the loop, defaulting\n        to one million.  The main statement, the setup statement and\n        the timer function to be used are passed to the constructor.\n        "));
      var1.setlocal("timeit", var4);
      var3 = null;
      var1.setline(202);
      var3 = new PyObject[]{var1.getname("default_repeat"), var1.getname("default_number")};
      var4 = new PyFunction(var1.f_globals, var3, repeat$9, PyString.fromInterned("Call timeit() a few times.\n\n        This is a convenience function that calls the timeit()\n        repeatedly, returning a list of results.  The first argument\n        specifies how many times to call timeit(), defaulting to 3;\n        the second argument specifies the timer argument, defaulting\n        to one million.\n\n        Note: it's tempting to calculate mean and standard deviation\n        from the result vector and report these.  However, this is not\n        very useful.  In a typical case, the lowest value gives a\n        lower bound for how fast your machine can run the given code\n        snippet; higher values in the result vector are typically not\n        caused by variability in Python's speed, but by other\n        processes interfering with your timing accuracy.  So the min()\n        of the result is probably the only number you should be\n        interested in.  After that, you should look at the entire\n        vector and apply common sense rather than statistics.\n        "));
      var1.setlocal("repeat", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$5(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      PyString.fromInterned("Constructor.  See class doc string.");
      var1.setline(123);
      PyObject var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("timer", var3);
      var3 = null;
      var1.setline(124);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.setderef(0, var4);
      var3 = null;
      var1.setline(125);
      if (var1.getname("isinstance").__call__(var2, var1.getlocal(1), var1.getname("basestring")).__nonzero__()) {
         var1.setline(126);
         var3 = var1.getname("reindent").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newInteger(8));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(127);
         if (var1.getname("isinstance").__call__(var2, var1.getlocal(2), var1.getname("basestring")).__nonzero__()) {
            var1.setline(128);
            var3 = var1.getname("reindent").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(4));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(129);
            var3 = var1.getname("template")._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("stmt"), var1.getlocal(1), PyString.fromInterned("setup"), var1.getlocal(2)}));
            var1.setlocal(4, var3);
            var3 = null;
         } else {
            var1.setline(130);
            if (!var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("__call__")).__nonzero__()) {
               var1.setline(134);
               throw Py.makeException(var1.getname("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setup is neither a string nor callable")));
            }

            var1.setline(131);
            var3 = var1.getname("template")._mod(new PyDictionary(new PyObject[]{PyString.fromInterned("stmt"), var1.getlocal(1), PyString.fromInterned("setup"), PyString.fromInterned("_setup()")}));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(132);
            var3 = var1.getlocal(2);
            var1.getderef(0).__setitem__((PyObject)PyString.fromInterned("_setup"), var3);
            var3 = null;
         }

         var1.setline(135);
         var3 = var1.getlocal(4);
         var1.getlocal(0).__setattr__("src", var3);
         var3 = null;
         var1.setline(136);
         var3 = var1.getname("compile").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)var1.getname("dummy_src_name"), (PyObject)PyString.fromInterned("exec"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(137);
         Py.exec(var1.getlocal(5), var1.getname("globals").__call__(var2), var1.getderef(0));
         var1.setline(138);
         var3 = var1.getderef(0).__getitem__(PyString.fromInterned("inner"));
         var1.getlocal(0).__setattr__("inner", var3);
         var3 = null;
      } else {
         var1.setline(139);
         if (!var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__call__")).__nonzero__()) {
            var1.setline(149);
            throw Py.makeException(var1.getname("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("stmt is neither a string nor callable")));
         }

         var1.setline(140);
         var3 = var1.getname("None");
         var1.getlocal(0).__setattr__("src", var3);
         var3 = null;
         var1.setline(141);
         if (var1.getname("isinstance").__call__(var2, var1.getlocal(2), var1.getname("basestring")).__nonzero__()) {
            var1.setline(142);
            var3 = var1.getlocal(2);
            var1.setderef(1, var3);
            var3 = null;
            var1.setline(143);
            PyObject[] var5 = Py.EmptyObjects;
            PyObject var10002 = var1.f_globals;
            PyObject[] var10003 = var5;
            PyCode var10004 = setup$6;
            var5 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
            PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
            var1.setlocal(2, var6);
            var3 = null;
         } else {
            var1.setline(145);
            if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("__call__")).__not__().__nonzero__()) {
               var1.setline(146);
               throw Py.makeException(var1.getname("ValueError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setup is neither a string nor callable")));
            }
         }

         var1.setline(147);
         var3 = var1.getname("_template_func").__call__(var2, var1.getlocal(2), var1.getlocal(1));
         var1.getlocal(0).__setattr__("inner", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setup$6(PyFrame var1, ThreadState var2) {
      var1.setline(144);
      Py.exec(var1.getderef(0), var1.getname("globals").__call__(var2), var1.getderef(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject print_exc$7(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      PyString.fromInterned("Helper to print a traceback from the timed code.\n\n        Typical use:\n\n            t = Timer(...)       # outside the try/except\n            try:\n                t.timeit(...)    # or t.repeat(...)\n            except:\n                t.print_exc()\n\n        The advantage over the standard traceback is that source lines\n        in the compiled template will be displayed.\n\n        The optional file argument directs where the traceback is\n        sent; it defaults to sys.stderr.\n        ");
      var1.setline(168);
      PyObject var3 = imp.importOne("linecache", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(169);
      var3 = var1.getlocal(0).__getattr__("src");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(170);
         PyTuple var5 = new PyTuple(new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("src")), var1.getglobal("None"), var1.getlocal(0).__getattr__("src").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")), var1.getglobal("dummy_src_name")});
         var1.getlocal(2).__getattr__("cache").__setitem__((PyObject)var1.getglobal("dummy_src_name"), var5);
         var3 = null;
      }

      var1.setline(176);
      var10000 = var1.getlocal(3).__getattr__("print_exc");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[]{"file"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject timeit$8(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyString.fromInterned("Time 'number' executions of the main statement.\n\n        To be precise, this executes the setup statement once, and\n        then returns the time it takes to execute the main statement\n        a number of times, as a float measured in seconds.  The\n        argument is the number of times through the loop, defaulting\n        to one million.  The main statement, the setup statement and\n        the timer function to be used are passed to the constructor.\n        ");
      var1.setline(188);
      PyObject var3;
      if (var1.getglobal("itertools").__nonzero__()) {
         var1.setline(189);
         var3 = var1.getglobal("itertools").__getattr__("repeat").__call__(var2, var1.getglobal("None"), var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(191);
         var3 = (new PyList(new PyObject[]{var1.getglobal("None")}))._mul(var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(192);
      var3 = var1.getglobal("gc").__getattr__("isenabled").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;

      try {
         var1.setline(194);
         var1.getglobal("gc").__getattr__("disable").__call__(var2);
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(var1.getglobal("NotImplementedError"))) {
            throw var5;
         }

         var1.setline(196);
      }

      var1.setline(197);
      var3 = var1.getlocal(0).__getattr__("inner").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("timer"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(198);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(199);
         var1.getglobal("gc").__getattr__("enable").__call__(var2);
      }

      var1.setline(200);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject repeat$9(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      PyString.fromInterned("Call timeit() a few times.\n\n        This is a convenience function that calls the timeit()\n        repeatedly, returning a list of results.  The first argument\n        specifies how many times to call timeit(), defaulting to 3;\n        the second argument specifies the timer argument, defaulting\n        to one million.\n\n        Note: it's tempting to calculate mean and standard deviation\n        from the result vector and report these.  However, this is not\n        very useful.  In a typical case, the lowest value gives a\n        lower bound for how fast your machine can run the given code\n        snippet; higher values in the result vector are typically not\n        caused by variability in Python's speed, but by other\n        processes interfering with your timing accuracy.  So the min()\n        of the result is probably the only number you should be\n        interested in.  After that, you should look at the entire\n        vector and apply common sense rather than statistics.\n        ");
      var1.setline(222);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(223);
      PyObject var6 = var1.getglobal("range").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(223);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(226);
            var6 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(4, var4);
         var1.setline(224);
         PyObject var5 = var1.getlocal(0).__getattr__("timeit").__call__(var2, var1.getlocal(2));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(225);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(5));
      }
   }

   public PyObject timeit$10(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyString.fromInterned("Convenience function to create Timer object and call timeit method.");
      var1.setline(231);
      PyObject var3 = var1.getglobal("Timer").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)).__getattr__("timeit").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject repeat$11(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      PyString.fromInterned("Convenience function to create Timer object and call repeat method.");
      var1.setline(236);
      PyObject var3 = var1.getglobal("Timer").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)).__getattr__("repeat").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject main$12(PyFrame var1, ThreadState var2) {
      var1.setline(250);
      PyString.fromInterned("Main program, used when run as a script.\n\n    The optional argument specifies the command line to be parsed,\n    defaulting to sys.argv[1:].\n\n    The return value is an exit code to be passed to sys.exit(); it\n    may be None to indicate success.\n\n    When an exception happens during timing, a traceback is printed to\n    stderr and the return value is 1.  Exceptions at other times\n    (including the template compilation) are not caught.\n    ");
      var1.setline(251);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(252);
         var3 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(253);
      var3 = imp.importOne("getopt", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      PyObject var5;
      PyInteger var11;
      try {
         var1.setline(255);
         var3 = var1.getlocal(1).__getattr__("getopt").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("n:s:r:tcvh"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("number="), PyString.fromInterned("setup="), PyString.fromInterned("repeat="), PyString.fromInterned("time"), PyString.fromInterned("clock"), PyString.fromInterned("verbose"), PyString.fromInterned("help")})));
         PyObject[] var12 = Py.unpackSequence(var3, 2);
         var5 = var12[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var12[1];
         var1.setlocal(0, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var10) {
         PyException var13 = Py.setException(var10, var1);
         if (var13.match(var1.getlocal(1).__getattr__("error"))) {
            var4 = var13.value;
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(259);
            Py.println(var1.getlocal(3));
            var1.setline(260);
            Py.println(PyString.fromInterned("use -h/--help for command line help"));
            var1.setline(261);
            var11 = Py.newInteger(2);
            var1.f_lasti = -1;
            return var11;
         }

         throw var13;
      }

      var1.setline(262);
      var3 = var1.getglobal("default_timer");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(263);
      Object var19 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(0));
      if (!((PyObject)var19).__nonzero__()) {
         var19 = PyString.fromInterned("pass");
      }

      Object var15 = var19;
      var1.setlocal(5, (PyObject)var15);
      var3 = null;
      var1.setline(264);
      PyInteger var16 = Py.newInteger(0);
      var1.setlocal(6, var16);
      var3 = null;
      var1.setline(265);
      PyList var17 = new PyList(Py.EmptyObjects);
      var1.setlocal(7, var17);
      var3 = null;
      var1.setline(266);
      var3 = var1.getglobal("default_repeat");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(267);
      var16 = Py.newInteger(0);
      var1.setlocal(9, var16);
      var3 = null;
      var1.setline(268);
      var16 = Py.newInteger(3);
      var1.setlocal(10, var16);
      var3 = null;
      var1.setline(269);
      var3 = var1.getlocal(2).__iter__();

      do {
         var1.setline(269);
         var5 = var3.__iternext__();
         PyObject[] var6;
         PyObject var14;
         if (var5 == null) {
            var1.setline(289);
            var19 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(7));
            if (!((PyObject)var19).__nonzero__()) {
               var19 = PyString.fromInterned("pass");
            }

            var15 = var19;
            var1.setlocal(7, (PyObject)var15);
            var3 = null;
            var1.setline(293);
            var3 = imp.importOne("os", var1, -1);
            var1.setlocal(13, var3);
            var3 = null;
            var1.setline(294);
            var1.getglobal("sys").__getattr__("path").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(13).__getattr__("curdir"));
            var1.setline(295);
            var3 = var1.getglobal("Timer").__call__(var2, var1.getlocal(5), var1.getlocal(7), var1.getlocal(4));
            var1.setlocal(14, var3);
            var3 = null;
            var1.setline(296);
            var3 = var1.getlocal(6);
            var10000 = var3._eq(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(298);
               var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(10)).__iter__();

               do {
                  var1.setline(298);
                  var5 = var3.__iternext__();
                  if (var5 == null) {
                     break;
                  }

                  var1.setlocal(15, var5);
                  var1.setline(299);
                  var14 = Py.newInteger(10)._pow(var1.getlocal(15));
                  var1.setlocal(6, var14);
                  var6 = null;

                  try {
                     var1.setline(301);
                     var14 = var1.getlocal(14).__getattr__("timeit").__call__(var2, var1.getlocal(6));
                     var1.setlocal(16, var14);
                     var6 = null;
                  } catch (Throwable var9) {
                     Py.setException(var9, var1);
                     var1.setline(303);
                     var1.getlocal(14).__getattr__("print_exc").__call__(var2);
                     var1.setline(304);
                     var11 = Py.newInteger(1);
                     var1.f_lasti = -1;
                     return var11;
                  }

                  var1.setline(305);
                  if (var1.getlocal(9).__nonzero__()) {
                     var1.setline(306);
                     Py.println(PyString.fromInterned("%d loops -> %.*g secs")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(10), var1.getlocal(16)})));
                  }

                  var1.setline(307);
                  var14 = var1.getlocal(16);
                  var10000 = var14._ge(Py.newFloat(0.2));
                  var6 = null;
               } while(!var10000.__nonzero__());
            }

            try {
               var1.setline(310);
               var3 = var1.getlocal(14).__getattr__("repeat").__call__(var2, var1.getlocal(8), var1.getlocal(6));
               var1.setlocal(17, var3);
               var3 = null;
            } catch (Throwable var8) {
               Py.setException(var8, var1);
               var1.setline(312);
               var1.getlocal(14).__getattr__("print_exc").__call__(var2);
               var1.setline(313);
               var11 = Py.newInteger(1);
               var1.f_lasti = -1;
               return var11;
            }

            var1.setline(314);
            var3 = var1.getglobal("min").__call__(var2, var1.getlocal(17));
            var1.setlocal(18, var3);
            var3 = null;
            var1.setline(315);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(316);
               Py.printComma(PyString.fromInterned("raw times:"));
               var10000 = PyString.fromInterned(" ").__getattr__("join");
               PyList var10002 = new PyList();
               var3 = var10002.__getattr__("append");
               var1.setlocal(19, var3);
               var3 = null;
               var1.setline(316);
               var3 = var1.getlocal(17).__iter__();

               while(true) {
                  var1.setline(316);
                  var5 = var3.__iternext__();
                  if (var5 == null) {
                     var1.setline(316);
                     var1.dellocal(19);
                     Py.println(var10000.__call__((ThreadState)var2, (PyObject)var10002));
                     break;
                  }

                  var1.setlocal(16, var5);
                  var1.setline(316);
                  var1.getlocal(19).__call__(var2, PyString.fromInterned("%.*g")._mod(new PyTuple(new PyObject[]{var1.getlocal(10), var1.getlocal(16)})));
               }
            }

            var1.setline(317);
            Py.printComma(PyString.fromInterned("%d loops,")._mod(var1.getlocal(6)));
            var1.setline(318);
            var3 = var1.getlocal(18)._mul(Py.newFloat(1000000.0))._div(var1.getlocal(6));
            var1.setlocal(20, var3);
            var3 = null;
            var1.setline(319);
            var3 = var1.getlocal(20);
            var10000 = var3._lt(Py.newInteger(1000));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(320);
               Py.println(PyString.fromInterned("best of %d: %.*g usec per loop")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(10), var1.getlocal(20)})));
            } else {
               var1.setline(322);
               var3 = var1.getlocal(20)._div(Py.newInteger(1000));
               var1.setlocal(21, var3);
               var3 = null;
               var1.setline(323);
               var3 = var1.getlocal(21);
               var10000 = var3._lt(Py.newInteger(1000));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(324);
                  Py.println(PyString.fromInterned("best of %d: %.*g msec per loop")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(10), var1.getlocal(21)})));
               } else {
                  var1.setline(326);
                  var3 = var1.getlocal(21)._div(Py.newInteger(1000));
                  var1.setlocal(22, var3);
                  var3 = null;
                  var1.setline(327);
                  Py.println(PyString.fromInterned("best of %d: %.*g sec per loop")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(10), var1.getlocal(22)})));
               }
            }

            var1.setline(328);
            var4 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var4;
         }

         var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(11, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(12, var7);
         var7 = null;
         var1.setline(270);
         var14 = var1.getlocal(11);
         var10000 = var14._in(new PyTuple(new PyObject[]{PyString.fromInterned("-n"), PyString.fromInterned("--number")}));
         var6 = null;
         if (var10000.__nonzero__()) {
            var1.setline(271);
            var14 = var1.getglobal("int").__call__(var2, var1.getlocal(12));
            var1.setlocal(6, var14);
            var6 = null;
         }

         var1.setline(272);
         var14 = var1.getlocal(11);
         var10000 = var14._in(new PyTuple(new PyObject[]{PyString.fromInterned("-s"), PyString.fromInterned("--setup")}));
         var6 = null;
         if (var10000.__nonzero__()) {
            var1.setline(273);
            var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(12));
         }

         var1.setline(274);
         var14 = var1.getlocal(11);
         var10000 = var14._in(new PyTuple(new PyObject[]{PyString.fromInterned("-r"), PyString.fromInterned("--repeat")}));
         var6 = null;
         if (var10000.__nonzero__()) {
            var1.setline(275);
            var14 = var1.getglobal("int").__call__(var2, var1.getlocal(12));
            var1.setlocal(8, var14);
            var6 = null;
            var1.setline(276);
            var14 = var1.getlocal(8);
            var10000 = var14._le(Py.newInteger(0));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(277);
               PyInteger var18 = Py.newInteger(1);
               var1.setlocal(8, var18);
               var6 = null;
            }
         }

         var1.setline(278);
         var14 = var1.getlocal(11);
         var10000 = var14._in(new PyTuple(new PyObject[]{PyString.fromInterned("-t"), PyString.fromInterned("--time")}));
         var6 = null;
         if (var10000.__nonzero__()) {
            var1.setline(279);
            var14 = var1.getglobal("time").__getattr__("time");
            var1.setlocal(4, var14);
            var6 = null;
         }

         var1.setline(280);
         var14 = var1.getlocal(11);
         var10000 = var14._in(new PyTuple(new PyObject[]{PyString.fromInterned("-c"), PyString.fromInterned("--clock")}));
         var6 = null;
         if (var10000.__nonzero__()) {
            var1.setline(281);
            var14 = var1.getglobal("time").__getattr__("clock");
            var1.setlocal(4, var14);
            var6 = null;
         }

         var1.setline(282);
         var14 = var1.getlocal(11);
         var10000 = var14._in(new PyTuple(new PyObject[]{PyString.fromInterned("-v"), PyString.fromInterned("--verbose")}));
         var6 = null;
         if (var10000.__nonzero__()) {
            var1.setline(283);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(284);
               var14 = var1.getlocal(10);
               var14 = var14._iadd(Py.newInteger(1));
               var1.setlocal(10, var14);
            }

            var1.setline(285);
            var14 = var1.getlocal(9);
            var14 = var14._iadd(Py.newInteger(1));
            var1.setlocal(9, var14);
         }

         var1.setline(286);
         var14 = var1.getlocal(11);
         var10000 = var14._in(new PyTuple(new PyObject[]{PyString.fromInterned("-h"), PyString.fromInterned("--help")}));
         var6 = null;
      } while(!var10000.__nonzero__());

      var1.setline(287);
      Py.printComma(var1.getglobal("__doc__"));
      var1.setline(288);
      var11 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var11;
   }

   public timeit$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"src", "indent"};
      reindent$1 = Py.newCode(2, var2, var1, "reindent", 90, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"setup", "func", "inner"};
      String[] var10001 = var2;
      timeit$py var10007 = self;
      var2 = new String[]{"setup"};
      _template_func$2 = Py.newCode(2, var10001, var1, "_template_func", 94, false, false, var10007, 2, var2, (String[])null, 0, 4097);
      var2 = new String[]{"_it", "_timer", "_func", "_t0", "_i", "_t1"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"setup"};
      inner$3 = Py.newCode(3, var10001, var1, "inner", 96, false, false, var10007, 3, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Timer$4 = Py.newCode(0, var2, var1, "Timer", 105, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stmt", "setup", "timer", "src", "code", "ns", "_setup"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ns", "_setup"};
      __init__$5 = Py.newCode(4, var10001, var1, "__init__", 121, false, false, var10007, 5, var2, (String[])null, 2, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"_setup", "ns"};
      setup$6 = Py.newCode(0, var10001, var1, "setup", 143, false, false, var10007, 6, (String[])null, var2, 0, 4096);
      var2 = new String[]{"self", "file", "linecache", "traceback"};
      print_exc$7 = Py.newCode(2, var2, var1, "print_exc", 151, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "number", "it", "gcold", "timing"};
      timeit$8 = Py.newCode(2, var2, var1, "timeit", 178, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "repeat", "number", "r", "i", "t"};
      repeat$9 = Py.newCode(3, var2, var1, "repeat", 202, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"stmt", "setup", "timer", "number"};
      timeit$10 = Py.newCode(4, var2, var1, "timeit", 228, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"stmt", "setup", "timer", "repeat", "number"};
      repeat$11 = Py.newCode(5, var2, var1, "repeat", 233, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "getopt", "opts", "err", "timer", "stmt", "number", "setup", "repeat", "verbose", "precision", "o", "a", "os", "t", "i", "x", "r", "best", "_[316_38]", "usec", "msec", "sec"};
      main$12 = Py.newCode(1, var2, var1, "main", 238, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new timeit$py("timeit$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(timeit$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.reindent$1(var2, var3);
         case 2:
            return this._template_func$2(var2, var3);
         case 3:
            return this.inner$3(var2, var3);
         case 4:
            return this.Timer$4(var2, var3);
         case 5:
            return this.__init__$5(var2, var3);
         case 6:
            return this.setup$6(var2, var3);
         case 7:
            return this.print_exc$7(var2, var3);
         case 8:
            return this.timeit$8(var2, var3);
         case 9:
            return this.repeat$9(var2, var3);
         case 10:
            return this.timeit$10(var2, var3);
         case 11:
            return this.repeat$11(var2, var3);
         case 12:
            return this.main$12(var2, var3);
         default:
            return null;
      }
   }
}
