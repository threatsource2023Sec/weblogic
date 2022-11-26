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
@Filename("trace.py")
public class trace$py extends PyFunctionTable implements PyRunnable {
   static trace$py self;
   static final PyCode f$0;
   static final PyCode _unsettrace$1;
   static final PyCode _settrace$2;
   static final PyCode _unsettrace$3;
   static final PyCode usage$4;
   static final PyCode Ignore$5;
   static final PyCode __init__$6;
   static final PyCode names$7;
   static final PyCode modname$8;
   static final PyCode fullmodname$9;
   static final PyCode CoverageResults$10;
   static final PyCode __init__$11;
   static final PyCode update$12;
   static final PyCode write_results$13;
   static final PyCode write_results_file$14;
   static final PyCode find_lines_from_code$15;
   static final PyCode find_lines$16;
   static final PyCode find_strings$17;
   static final PyCode find_executable_linenos$18;
   static final PyCode Trace$19;
   static final PyCode __init__$20;
   static final PyCode run$21;
   static final PyCode runctx$22;
   static final PyCode runfunc$23;
   static final PyCode file_module_function_of$24;
   static final PyCode globaltrace_trackcallers$25;
   static final PyCode globaltrace_countfuncs$26;
   static final PyCode globaltrace_lt$27;
   static final PyCode localtrace_trace_and_count$28;
   static final PyCode localtrace_trace$29;
   static final PyCode localtrace_count$30;
   static final PyCode results$31;
   static final PyCode _err_exit$32;
   static final PyCode main$33;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("program/module to trace Python program or function execution\n\nSample use, command line:\n  trace.py -c -f counts --ignore-dir '$prefix' spam.py eggs\n  trace.py -t --ignore-dir '$prefix' spam.py eggs\n  trace.py --trackcalls spam.py eggs\n\nSample use, programmatically\n  import sys\n\n  # create a Trace object, telling it what to ignore, and whether to\n  # do tracing or line-counting or both.\n  tracer = trace.Trace(ignoredirs=[sys.prefix, sys.exec_prefix,], trace=0,\n                    count=1)\n  # run the new command using the given tracer\n  tracer.run('main()')\n  # make a report, placing output in /tmp\n  r = tracer.results()\n  r.write_results(show_missing=True, coverdir=\"/tmp\")\n"));
      var1.setline(49);
      PyString.fromInterned("program/module to trace Python program or function execution\n\nSample use, command line:\n  trace.py -c -f counts --ignore-dir '$prefix' spam.py eggs\n  trace.py -t --ignore-dir '$prefix' spam.py eggs\n  trace.py --trackcalls spam.py eggs\n\nSample use, programmatically\n  import sys\n\n  # create a Trace object, telling it what to ignore, and whether to\n  # do tracing or line-counting or both.\n  tracer = trace.Trace(ignoredirs=[sys.prefix, sys.exec_prefix,], trace=0,\n                    count=1)\n  # run the new command using the given tracer\n  tracer.run('main()')\n  # make a report, placing output in /tmp\n  r = tracer.results()\n  r.write_results(show_missing=True, coverdir=\"/tmp\")\n");
      var1.setline(51);
      PyObject var3 = imp.importOne("linecache", var1, -1);
      var1.setlocal("linecache", var3);
      var3 = null;
      var1.setline(52);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(53);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(54);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(55);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(56);
      var3 = imp.importOne("token", var1, -1);
      var1.setlocal("token", var3);
      var3 = null;
      var1.setline(57);
      var3 = imp.importOne("tokenize", var1, -1);
      var1.setlocal("tokenize", var3);
      var3 = null;
      var1.setline(58);
      var3 = imp.importOne("inspect", var1, -1);
      var1.setlocal("inspect", var3);
      var3 = null;
      var1.setline(59);
      var3 = imp.importOne("gc", var1, -1);
      var1.setlocal("gc", var3);
      var3 = null;
      var1.setline(60);
      var3 = imp.importOne("dis", var1, -1);
      var1.setlocal("dis", var3);
      var3 = null;

      PyObject var4;
      PyException var9;
      try {
         var1.setline(62);
         var3 = imp.importOne("cPickle", var1, -1);
         var1.setlocal("cPickle", var3);
         var3 = null;
         var1.setline(63);
         var3 = var1.getname("cPickle");
         var1.setlocal("pickle", var3);
         var3 = null;
      } catch (Throwable var6) {
         var9 = Py.setException(var6, var1);
         if (!var9.match(var1.getname("ImportError"))) {
            throw var9;
         }

         var1.setline(65);
         var4 = imp.importOne("pickle", var1, -1);
         var1.setlocal("pickle", var4);
         var4 = null;
      }

      label29: {
         PyObject[] var7;
         PyFunction var8;
         try {
            var1.setline(68);
            var3 = imp.importOne("threading", var1, -1);
            var1.setlocal("threading", var3);
            var3 = null;
         } catch (Throwable var5) {
            var9 = Py.setException(var5, var1);
            if (var9.match(var1.getname("ImportError"))) {
               var1.setline(70);
               var4 = var1.getname("sys").__getattr__("settrace");
               var1.setlocal("_settrace", var4);
               var4 = null;
               var1.setline(72);
               var7 = Py.EmptyObjects;
               var8 = new PyFunction(var1.f_globals, var7, _unsettrace$1, (PyObject)null);
               var1.setlocal("_unsettrace", var8);
               var4 = null;
               break label29;
            }

            throw var9;
         }

         var1.setline(75);
         var7 = Py.EmptyObjects;
         var8 = new PyFunction(var1.f_globals, var7, _settrace$2, (PyObject)null);
         var1.setlocal("_settrace", var8);
         var4 = null;
         var1.setline(79);
         var7 = Py.EmptyObjects;
         var8 = new PyFunction(var1.f_globals, var7, _unsettrace$3, (PyObject)null);
         var1.setlocal("_unsettrace", var8);
         var4 = null;
      }

      var1.setline(83);
      PyObject[] var10 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var10, usage$4, (PyObject)null);
      var1.setlocal("usage", var11);
      var3 = null;
      var1.setline(128);
      PyString var12 = PyString.fromInterned("#pragma NO COVER");
      var1.setlocal("PRAGMA_NOCOVER", var12);
      var3 = null;
      var1.setline(131);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^\\s*(#.*)?$"));
      var1.setlocal("rx_blank", var3);
      var3 = null;
      var1.setline(133);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("Ignore", var10, Ignore$5);
      var1.setlocal("Ignore", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(186);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, modname$8, PyString.fromInterned("Return a plausible module name for the patch."));
      var1.setlocal("modname", var11);
      var3 = null;
      var1.setline(193);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, fullmodname$9, PyString.fromInterned("Return a plausible module name for the path."));
      var1.setlocal("fullmodname", var11);
      var3 = null;
      var1.setline(221);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("CoverageResults", var10, CoverageResults$10);
      var1.setlocal("CoverageResults", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(393);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, find_lines_from_code$15, PyString.fromInterned("Return dict where keys are lines in the line number table."));
      var1.setlocal("find_lines_from_code", var11);
      var3 = null;
      var1.setline(403);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, find_lines$16, PyString.fromInterned("Return lineno dict for all code objects reachable from code."));
      var1.setlocal("find_lines", var11);
      var3 = null;
      var1.setline(415);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, find_strings$17, PyString.fromInterned("Return a dict of possible docstring positions.\n\n    The dict maps line numbers to strings.  There is an entry for\n    line that contains only a string or a part of a triple-quoted\n    string.\n    "));
      var1.setlocal("find_strings", var11);
      var3 = null;
      var1.setline(438);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, find_executable_linenos$18, PyString.fromInterned("Return dict where keys are line numbers in the line number table."));
      var1.setlocal("find_executable_linenos", var11);
      var3 = null;
      var1.setline(450);
      var10 = Py.EmptyObjects;
      var4 = Py.makeClass("Trace", var10, Trace$19);
      var1.setlocal("Trace", var4);
      var4 = null;
      Arrays.fill(var10, (Object)null);
      var1.setline(655);
      var10 = Py.EmptyObjects;
      var11 = new PyFunction(var1.f_globals, var10, _err_exit$32, (PyObject)null);
      var1.setlocal("_err_exit", var11);
      var3 = null;
      var1.setline(659);
      var10 = new PyObject[]{var1.getname("None")};
      var11 = new PyFunction(var1.f_globals, var10, main$33, (PyObject)null);
      var1.setlocal("main", var11);
      var3 = null;
      var1.setline(818);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(819);
         var1.getname("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _unsettrace$1(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _settrace$2(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      var1.getglobal("threading").__getattr__("settrace").__call__(var2, var1.getlocal(0));
      var1.setline(77);
      var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _unsettrace$3(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getglobal("None"));
      var1.setline(81);
      var1.getglobal("threading").__getattr__("settrace").__call__(var2, var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject usage$4(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      var1.getlocal(0).__getattr__("write").__call__(var2, PyString.fromInterned("Usage: %s [OPTIONS] <file> [ARGS]\n\nMeta-options:\n--help                Display this help then exit.\n--version             Output version information then exit.\n\nOtherwise, exactly one of the following three options must be given:\n-t, --trace           Print each line to sys.stdout before it is executed.\n-c, --count           Count the number of times each line is executed\n                      and write the counts to <module>.cover for each\n                      module executed, in the module's directory.\n                      See also `--coverdir', `--file', `--no-report' below.\n-l, --listfuncs       Keep track of which functions are executed at least\n                      once and write the results to sys.stdout after the\n                      program exits.\n-T, --trackcalls      Keep track of caller/called pairs and write the\n                      results to sys.stdout after the program exits.\n-r, --report          Generate a report from a counts file; do not execute\n                      any code.  `--file' must specify the results file to\n                      read, which must have been created in a previous run\n                      with `--count --file=FILE'.\n\nModifiers:\n-f, --file=<file>     File to accumulate counts over several runs.\n-R, --no-report       Do not generate the coverage report files.\n                      Useful if you want to accumulate over several runs.\n-C, --coverdir=<dir>  Directory where the report files.  The coverage\n                      report for <package>.<module> is written to file\n                      <dir>/<package>/<module>.cover.\n-m, --missing         Annotate executable lines that were not executed\n                      with '>>>>>> '.\n-s, --summary         Write a brief summary on stdout for each file.\n                      (Can only be used with --count or --report.)\n-g, --timing          Prefix each line with the time since the program started.\n                      Only used while tracing.\n\nFilters, may be repeated multiple times:\n--ignore-module=<mod> Ignore the given module(s) and its submodules\n                      (if it is a package).  Accepts comma separated\n                      list of module names\n--ignore-dir=<dir>    Ignore files in the given directory (multiple\n                      directories can be joined by os.pathsep).\n")._mod(var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Ignore$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(134);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(141);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, names$7, (PyObject)null);
      var1.setlocal("names", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      Object var10000 = var1.getlocal(1);
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var3 = var10000;
      var1.getlocal(0).__setattr__((String)"_mods", (PyObject)var3);
      var3 = null;
      var1.setline(136);
      var10000 = var1.getlocal(2);
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      var3 = var10000;
      var1.getlocal(0).__setattr__((String)"_dirs", (PyObject)var3);
      var3 = null;
      var1.setline(138);
      PyObject var4 = var1.getglobal("map").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normpath"), var1.getlocal(0).__getattr__("_dirs"));
      var1.getlocal(0).__setattr__("_dirs", var4);
      var3 = null;
      var1.setline(139);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("<string>"), Py.newInteger(1)});
      var1.getlocal(0).__setattr__((String)"_ignore", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject names$7(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_ignore"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(143);
         var3 = var1.getlocal(0).__getattr__("_ignore").__getitem__(var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(149);
         PyObject var4 = var1.getlocal(0).__getattr__("_mods").__iter__();

         PyObject var6;
         PyInteger var7;
         PyInteger var9;
         do {
            var1.setline(149);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(164);
               var4 = var1.getlocal(1);
               var10000 = var4._is(var1.getglobal("None"));
               var4 = null;
               PyInteger var8;
               if (var10000.__nonzero__()) {
                  var1.setline(166);
                  var8 = Py.newInteger(1);
                  var1.getlocal(0).__getattr__("_ignore").__setitem__((PyObject)var1.getlocal(2), var8);
                  var4 = null;
                  var1.setline(167);
                  var7 = Py.newInteger(1);
                  var1.f_lasti = -1;
                  return var7;
               } else {
                  var1.setline(170);
                  var4 = var1.getlocal(0).__getattr__("_dirs").__iter__();

                  do {
                     var1.setline(170);
                     var5 = var4.__iternext__();
                     if (var5 == null) {
                        var1.setline(183);
                        var8 = Py.newInteger(0);
                        var1.getlocal(0).__getattr__("_ignore").__setitem__((PyObject)var1.getlocal(2), var8);
                        var4 = null;
                        var1.setline(184);
                        var7 = Py.newInteger(0);
                        var1.f_lasti = -1;
                        return var7;
                     }

                     var1.setlocal(5, var5);
                     var1.setline(178);
                  } while(!var1.getlocal(1).__getattr__("startswith").__call__(var2, var1.getlocal(5)._add(var1.getglobal("os").__getattr__("sep"))).__nonzero__());

                  var1.setline(179);
                  var9 = Py.newInteger(1);
                  var1.getlocal(0).__getattr__("_ignore").__setitem__((PyObject)var1.getlocal(2), var9);
                  var6 = null;
                  var1.setline(180);
                  var7 = Py.newInteger(1);
                  var1.f_lasti = -1;
                  return var7;
               }
            }

            var1.setlocal(3, var5);
            var1.setline(150);
            var6 = var1.getlocal(3);
            var10000 = var6._eq(var1.getlocal(2));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(151);
               var9 = Py.newInteger(1);
               var1.getlocal(0).__getattr__("_ignore").__setitem__((PyObject)var1.getlocal(2), var9);
               var6 = null;
               var1.setline(152);
               var7 = Py.newInteger(1);
               var1.f_lasti = -1;
               return var7;
            }

            var1.setline(155);
            var6 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(159);
            var6 = var1.getlocal(3);
            var10000 = var6._eq(var1.getlocal(2).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null));
            var6 = null;
            if (var10000.__nonzero__()) {
               var6 = var1.getlocal(2).__getitem__(var1.getlocal(4));
               var10000 = var6._eq(PyString.fromInterned("."));
               var6 = null;
            }
         } while(!var10000.__nonzero__());

         var1.setline(160);
         var9 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("_ignore").__setitem__((PyObject)var1.getlocal(2), var9);
         var6 = null;
         var1.setline(161);
         var7 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject modname$8(PyFrame var1, ThreadState var2) {
      var1.setline(187);
      PyString.fromInterned("Return a plausible module name for the patch.");
      var1.setline(189);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(190);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(191);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fullmodname$9(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      PyString.fromInterned("Return a plausible module name for the path.");
      var1.setline(201);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(202);
      PyString var6 = PyString.fromInterned("");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(203);
      var3 = var1.getglobal("sys").__getattr__("path").__iter__();

      while(true) {
         var1.setline(203);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(209);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(210);
               var3 = var1.getlocal(0).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(2))._add(Py.newInteger(1)), (PyObject)null, (PyObject)null);
               var1.setlocal(4, var3);
               var3 = null;
            } else {
               var1.setline(212);
               var3 = var1.getlocal(0);
               var1.setlocal(4, var3);
               var3 = null;
            }

            var1.setline(214);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitdrive").__call__(var2, var1.getlocal(4));
            PyObject[] var7 = Py.unpackSequence(var3, 2);
            var5 = var7[0];
            var1.setlocal(5, var5);
            var5 = null;
            var5 = var7[1];
            var1.setlocal(4, var5);
            var5 = null;
            var3 = null;
            var1.setline(215);
            var3 = var1.getlocal(4).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("sep"), (PyObject)PyString.fromInterned("."));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(216);
            if (var1.getglobal("os").__getattr__("altsep").__nonzero__()) {
               var1.setline(217);
               var3 = var1.getlocal(4).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("altsep"), (PyObject)PyString.fromInterned("."));
               var1.setlocal(4, var3);
               var3 = null;
            }

            var1.setline(218);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(4));
            var7 = Py.unpackSequence(var3, 2);
            var5 = var7[0];
            var1.setlocal(6, var5);
            var5 = null;
            var5 = var7[1];
            var1.setlocal(7, var5);
            var5 = null;
            var3 = null;
            var1.setline(219);
            var3 = var1.getlocal(6).__getattr__("lstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(204);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(3));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(205);
         PyObject var10000 = var1.getlocal(1).__getattr__("startswith").__call__(var2, var1.getlocal(3));
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(1).__getitem__(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
            var10000 = var5._eq(var1.getglobal("os").__getattr__("sep"));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(206);
            var5 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var10000 = var5._gt(var1.getglobal("len").__call__(var2, var1.getlocal(2)));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(207);
               var5 = var1.getlocal(3);
               var1.setlocal(2, var5);
               var5 = null;
            }
         }
      }
   }

   public PyObject CoverageResults$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(222);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$11, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(248);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, update$12, PyString.fromInterned("Merge in the data from another CoverageResults"));
      var1.setlocal("update", var4);
      var3 = null;
      var1.setline(266);
      var3 = new PyObject[]{var1.getname("True"), var1.getname("False"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, write_results$13, PyString.fromInterned("\n        @param coverdir\n        "));
      var1.setlocal("write_results", var4);
      var3 = null;
      var1.setline(357);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write_results_file$14, PyString.fromInterned("Return a coverage results file in path."));
      var1.setlocal("write_results_file", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$11(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("counts", var3);
      var3 = null;
      var1.setline(225);
      var3 = var1.getlocal(0).__getattr__("counts");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyDictionary var7;
      if (var10000.__nonzero__()) {
         var1.setline(226);
         var7 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"counts", var7);
         var3 = null;
      }

      var1.setline(227);
      var3 = var1.getlocal(0).__getattr__("counts").__getattr__("copy").__call__(var2);
      var1.getlocal(0).__setattr__("counter", var3);
      var3 = null;
      var1.setline(228);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("calledfuncs", var3);
      var3 = null;
      var1.setline(229);
      var3 = var1.getlocal(0).__getattr__("calledfuncs");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(230);
         var7 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"calledfuncs", var7);
         var3 = null;
      }

      var1.setline(231);
      var3 = var1.getlocal(0).__getattr__("calledfuncs").__getattr__("copy").__call__(var2);
      var1.getlocal(0).__setattr__("calledfuncs", var3);
      var3 = null;
      var1.setline(232);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("callers", var3);
      var3 = null;
      var1.setline(233);
      var3 = var1.getlocal(0).__getattr__("callers");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(234);
         var7 = new PyDictionary(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"callers", var7);
         var3 = null;
      }

      var1.setline(235);
      var3 = var1.getlocal(0).__getattr__("callers").__getattr__("copy").__call__(var2);
      var1.getlocal(0).__setattr__("callers", var3);
      var3 = null;
      var1.setline(236);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("infile", var3);
      var3 = null;
      var1.setline(237);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("outfile", var3);
      var3 = null;
      var1.setline(238);
      if (var1.getlocal(0).__getattr__("infile").__nonzero__()) {
         try {
            var1.setline(241);
            var3 = var1.getglobal("pickle").__getattr__("load").__call__(var2, var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("infile"), (PyObject)PyString.fromInterned("rb")));
            PyObject[] var8 = Py.unpackSequence(var3, 3);
            PyObject var5 = var8[0];
            var1.setlocal(1, var5);
            var5 = null;
            var5 = var8[1];
            var1.setlocal(2, var5);
            var5 = null;
            var5 = var8[2];
            var1.setlocal(4, var5);
            var5 = null;
            var3 = null;
            var1.setline(243);
            var1.getlocal(0).__getattr__("update").__call__(var2, var1.getlocal(0).__getattr__("__class__").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(4)));
         } catch (Throwable var6) {
            PyException var9 = Py.setException(var6, var1);
            if (!var9.match(new PyTuple(new PyObject[]{var1.getglobal("IOError"), var1.getglobal("EOFError"), var1.getglobal("ValueError")}))) {
               throw var9;
            }

            PyObject var4 = var9.value;
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(245);
            var4 = var1.getglobal("sys").__getattr__("stderr");
            Py.println(var4, PyString.fromInterned("Skipping counts file %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("infile"), var1.getlocal(6)})));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject update$12(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyString.fromInterned("Merge in the data from another CoverageResults");
      var1.setline(250);
      PyObject var3 = var1.getlocal(0).__getattr__("counts");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(251);
      var3 = var1.getlocal(0).__getattr__("calledfuncs");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(252);
      var3 = var1.getlocal(0).__getattr__("callers");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(253);
      var3 = var1.getlocal(1).__getattr__("counts");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(254);
      var3 = var1.getlocal(1).__getattr__("calledfuncs");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(255);
      var3 = var1.getlocal(1).__getattr__("callers");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(257);
      var3 = var1.getlocal(5).__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(257);
         PyObject var4 = var3.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(260);
            var3 = var1.getlocal(6).__getattr__("keys").__call__(var2).__iter__();

            while(true) {
               var1.setline(260);
               var4 = var3.__iternext__();
               PyInteger var6;
               if (var4 == null) {
                  var1.setline(263);
                  var3 = var1.getlocal(7).__getattr__("keys").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(263);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(8, var4);
                     var1.setline(264);
                     var6 = Py.newInteger(1);
                     var1.getlocal(4).__setitem__((PyObject)var1.getlocal(8), var6);
                     var5 = null;
                  }
               }

               var1.setlocal(8, var4);
               var1.setline(261);
               var6 = Py.newInteger(1);
               var1.getlocal(3).__setitem__((PyObject)var1.getlocal(8), var6);
               var5 = null;
            }
         }

         var1.setlocal(8, var4);
         var1.setline(258);
         var5 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)Py.newInteger(0))._add(var1.getlocal(5).__getitem__(var1.getlocal(8)));
         var1.getlocal(2).__setitem__(var1.getlocal(8), var5);
         var5 = null;
      }
   }

   public PyObject write_results$13(PyFrame var1, ThreadState var2) {
      var1.setline(269);
      PyString.fromInterned("\n        @param coverdir\n        ");
      var1.setline(270);
      PyObject var3;
      PyObject var4;
      PyObject[] var5;
      PyObject var6;
      if (var1.getlocal(0).__getattr__("calledfuncs").__nonzero__()) {
         var1.setline(271);
         Py.println();
         var1.setline(272);
         Py.println(PyString.fromInterned("functions called:"));
         var1.setline(273);
         var3 = var1.getlocal(0).__getattr__("calledfuncs").__getattr__("keys").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(274);
         var1.getlocal(4).__getattr__("sort").__call__(var2);
         var1.setline(275);
         var3 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(275);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var5 = Py.unpackSequence(var4, 3);
            var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(276);
            Py.println(PyString.fromInterned("filename: %s, modulename: %s, funcname: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(7)})));
         }
      }

      var1.setline(279);
      PyObject[] var7;
      PyObject var12;
      PyObject var10000;
      if (var1.getlocal(0).__getattr__("callers").__nonzero__()) {
         var1.setline(280);
         Py.println();
         var1.setline(281);
         Py.println(PyString.fromInterned("calling relationships:"));
         var1.setline(282);
         var3 = var1.getlocal(0).__getattr__("callers").__getattr__("keys").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(283);
         var1.getlocal(4).__getattr__("sort").__call__(var2);
         var1.setline(284);
         PyString var10 = PyString.fromInterned("");
         var1.setlocal(8, var10);
         var1.setlocal(9, var10);
         var1.setline(285);
         var3 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(285);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var7 = Py.unpackSequence(var6, 3);
            PyObject var8 = var7[0];
            var1.setlocal(10, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(11, var8);
            var8 = null;
            var8 = var7[2];
            var1.setlocal(12, var8);
            var8 = null;
            var6 = null;
            var6 = var5[1];
            var7 = Py.unpackSequence(var6, 3);
            var8 = var7[0];
            var1.setlocal(13, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(14, var8);
            var8 = null;
            var8 = var7[2];
            var1.setlocal(15, var8);
            var8 = null;
            var6 = null;
            var1.setline(286);
            var12 = var1.getlocal(10);
            var10000 = var12._ne(var1.getlocal(8));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(287);
               Py.println();
               var1.setline(288);
               Py.printComma(PyString.fromInterned("***"));
               Py.printComma(var1.getlocal(10));
               Py.println(PyString.fromInterned("***"));
               var1.setline(289);
               var12 = var1.getlocal(10);
               var1.setlocal(8, var12);
               var5 = null;
               var1.setline(290);
               PyString var13 = PyString.fromInterned("");
               var1.setlocal(9, var13);
               var5 = null;
            }

            var1.setline(291);
            var12 = var1.getlocal(13);
            var10000 = var12._ne(var1.getlocal(10));
            var5 = null;
            if (var10000.__nonzero__()) {
               var12 = var1.getlocal(9);
               var10000 = var12._ne(var1.getlocal(13));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(292);
               Py.printComma(PyString.fromInterned("  -->"));
               Py.println(var1.getlocal(13));
               var1.setline(293);
               var12 = var1.getlocal(13);
               var1.setlocal(9, var12);
               var5 = null;
            }

            var1.setline(294);
            Py.println(PyString.fromInterned("    %s.%s -> %s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(12), var1.getlocal(14), var1.getlocal(15)})));
         }
      }

      var1.setline(298);
      PyDictionary var11 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(16, var11);
      var3 = null;
      var1.setline(299);
      var3 = var1.getlocal(0).__getattr__("counts").__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(299);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(304);
            var11 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(19, var11);
            var3 = null;
            var1.setline(306);
            var3 = var1.getlocal(16).__getattr__("iteritems").__call__(var2).__iter__();

            while(true) {
               var1.setline(306);
               var4 = var3.__iternext__();
               PyObject var14;
               PyObject[] var16;
               if (var4 == null) {
                  var1.setline(341);
                  var10000 = var1.getlocal(2);
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getlocal(19);
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(342);
                     var3 = var1.getlocal(19).__getattr__("keys").__call__(var2);
                     var1.setlocal(28, var3);
                     var3 = null;
                     var1.setline(343);
                     var1.getlocal(28).__getattr__("sort").__call__(var2);
                     var1.setline(344);
                     Py.println(PyString.fromInterned("lines   cov%   module   (path)"));
                     var1.setline(345);
                     var3 = var1.getlocal(28).__iter__();

                     while(true) {
                        var1.setline(345);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           break;
                        }

                        var1.setlocal(29, var4);
                        var1.setline(346);
                        var12 = var1.getlocal(19).__getitem__(var1.getlocal(29));
                        var16 = Py.unpackSequence(var12, 4);
                        var14 = var16[0];
                        var1.setlocal(26, var14);
                        var7 = null;
                        var14 = var16[1];
                        var1.setlocal(27, var14);
                        var7 = null;
                        var14 = var16[2];
                        var1.setlocal(6, var14);
                        var7 = null;
                        var14 = var16[3];
                        var1.setlocal(5, var14);
                        var7 = null;
                        var5 = null;
                        var1.setline(347);
                        Py.println(PyString.fromInterned("%5d   %3d%%   %s   (%s)")._mod(var1.getlocal(19).__getitem__(var1.getlocal(29))));
                     }
                  }

                  var1.setline(349);
                  if (var1.getlocal(0).__getattr__("outfile").__nonzero__()) {
                     try {
                        var1.setline(352);
                        var1.getglobal("pickle").__getattr__("dump").__call__((ThreadState)var2, new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("counts"), var1.getlocal(0).__getattr__("calledfuncs"), var1.getlocal(0).__getattr__("callers")}), (PyObject)var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("outfile"), (PyObject)PyString.fromInterned("wb")), (PyObject)Py.newInteger(1));
                     } catch (Throwable var9) {
                        PyException var15 = Py.setException(var9, var1);
                        if (!var15.match(var1.getglobal("IOError"))) {
                           throw var15;
                        }

                        var4 = var15.value;
                        var1.setlocal(30, var4);
                        var4 = null;
                        var1.setline(355);
                        var4 = var1.getglobal("sys").__getattr__("stderr");
                        Py.println(var4, PyString.fromInterned("Can't save counts files because %s")._mod(var1.getlocal(30)));
                     }
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(5, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(20, var6);
               var6 = null;
               var1.setline(308);
               var12 = var1.getlocal(5);
               var10000 = var12._eq(PyString.fromInterned("<string>"));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(310);
                  if (!var1.getlocal(5).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<doctest ")).__nonzero__()) {
                     var1.setline(313);
                     if (var1.getlocal(5).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(".pyc"), PyString.fromInterned(".pyo")}))).__nonzero__()) {
                        var1.setline(314);
                        var12 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
                        var1.setlocal(5, var12);
                        var5 = null;
                     }

                     var1.setline(316);
                     var12 = var1.getlocal(3);
                     var10000 = var12._is(var1.getglobal("None"));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(317);
                        var12 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(5)));
                        var1.setlocal(21, var12);
                        var5 = null;
                        var1.setline(318);
                        var12 = var1.getglobal("modname").__call__(var2, var1.getlocal(5));
                        var1.setlocal(6, var12);
                        var5 = null;
                     } else {
                        var1.setline(320);
                        var12 = var1.getlocal(3);
                        var1.setlocal(21, var12);
                        var5 = null;
                        var1.setline(321);
                        if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(21)).__not__().__nonzero__()) {
                           var1.setline(322);
                           var1.getglobal("os").__getattr__("makedirs").__call__(var2, var1.getlocal(21));
                        }

                        var1.setline(323);
                        var12 = var1.getglobal("fullmodname").__call__(var2, var1.getlocal(5));
                        var1.setlocal(6, var12);
                        var5 = null;
                     }

                     var1.setline(327);
                     if (var1.getlocal(1).__nonzero__()) {
                        var1.setline(328);
                        var12 = var1.getglobal("find_executable_linenos").__call__(var2, var1.getlocal(5));
                        var1.setlocal(22, var12);
                        var5 = null;
                     } else {
                        var1.setline(330);
                        PyDictionary var17 = new PyDictionary(Py.EmptyObjects);
                        var1.setlocal(22, var17);
                        var5 = null;
                     }

                     var1.setline(332);
                     var12 = var1.getglobal("linecache").__getattr__("getlines").__call__(var2, var1.getlocal(5));
                     var1.setlocal(23, var12);
                     var5 = null;
                     var1.setline(333);
                     var12 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(21), var1.getlocal(6)._add(PyString.fromInterned(".cover")));
                     var1.setlocal(24, var12);
                     var5 = null;
                     var1.setline(334);
                     var12 = var1.getlocal(0).__getattr__("write_results_file").__call__(var2, var1.getlocal(24), var1.getlocal(23), var1.getlocal(22), var1.getlocal(20));
                     var16 = Py.unpackSequence(var12, 2);
                     var14 = var16[0];
                     var1.setlocal(25, var14);
                     var7 = null;
                     var14 = var16[1];
                     var1.setlocal(26, var14);
                     var7 = null;
                     var5 = null;
                     var1.setline(337);
                     var10000 = var1.getlocal(2);
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getlocal(26);
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(338);
                        var12 = Py.newInteger(100)._mul(var1.getlocal(25))._floordiv(var1.getlocal(26));
                        var1.setlocal(27, var12);
                        var5 = null;
                        var1.setline(339);
                        PyTuple var18 = new PyTuple(new PyObject[]{var1.getlocal(26), var1.getlocal(27), var1.getlocal(6), var1.getlocal(5)});
                        var1.getlocal(19).__setitem__((PyObject)var1.getlocal(6), var18);
                        var5 = null;
                     }
                  }
               }
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(17, var6);
         var6 = null;
         var1.setline(300);
         var12 = var1.getlocal(16).__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)(new PyDictionary(Py.EmptyObjects)));
         var1.setlocal(18, var12);
         var1.getlocal(16).__setitem__(var1.getlocal(5), var12);
         var1.setline(301);
         var12 = var1.getlocal(0).__getattr__("counts").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(17)}));
         var1.getlocal(18).__setitem__(var1.getlocal(17), var12);
         var5 = null;
      }
   }

   public PyObject write_results_file$14(PyFrame var1, ThreadState var2) {
      var1.setline(358);
      PyString.fromInterned("Return a coverage results file in path.");

      PyException var3;
      PyObject var9;
      PyTuple var11;
      try {
         var1.setline(361);
         var9 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(5, var9);
         var3 = null;
      } catch (Throwable var8) {
         var3 = Py.setException(var8, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            PyObject var4 = var3.value;
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(363);
            var4 = var1.getglobal("sys").__getattr__("stderr");
            Py.println(var4, PyString.fromInterned("trace: Could not open %r for writing: %s- skipping")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(6)})));
            var1.setline(365);
            var11 = new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(0)});
            var1.f_lasti = -1;
            return var11;
         }

         throw var3;
      }

      var1.setline(367);
      PyInteger var10 = Py.newInteger(0);
      var1.setlocal(7, var10);
      var3 = null;
      var1.setline(368);
      var10 = Py.newInteger(0);
      var1.setlocal(8, var10);
      var3 = null;
      var1.setline(369);
      var9 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(2)).__iter__();

      while(true) {
         var1.setline(369);
         PyObject var5 = var9.__iternext__();
         if (var5 == null) {
            var1.setline(389);
            var1.getlocal(5).__getattr__("close").__call__(var2);
            var1.setline(391);
            var11 = new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(7)});
            var1.f_lasti = -1;
            return var11;
         }

         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(9, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(10, var7);
         var7 = null;
         var1.setline(370);
         PyObject var12 = var1.getlocal(9)._add(Py.newInteger(1));
         var1.setlocal(11, var12);
         var6 = null;
         var1.setline(373);
         var12 = var1.getlocal(11);
         PyObject var10000 = var12._in(var1.getlocal(4));
         var6 = null;
         if (var10000.__nonzero__()) {
            var1.setline(374);
            var1.getlocal(5).__getattr__("write").__call__(var2, PyString.fromInterned("%5d: ")._mod(var1.getlocal(4).__getitem__(var1.getlocal(11))));
            var1.setline(375);
            var12 = var1.getlocal(8);
            var12 = var12._iadd(Py.newInteger(1));
            var1.setlocal(8, var12);
            var1.setline(376);
            var12 = var1.getlocal(7);
            var12 = var12._iadd(Py.newInteger(1));
            var1.setlocal(7, var12);
         } else {
            var1.setline(377);
            if (var1.getglobal("rx_blank").__getattr__("match").__call__(var2, var1.getlocal(10)).__nonzero__()) {
               var1.setline(378);
               var1.getlocal(5).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("       "));
            } else {
               var1.setline(383);
               var12 = var1.getlocal(11);
               var10000 = var12._in(var1.getlocal(3));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var12 = var1.getglobal("PRAGMA_NOCOVER");
                  var10000 = var12._in(var1.getlocal(2).__getitem__(var1.getlocal(9)));
                  var6 = null;
                  var10000 = var10000.__not__();
               }

               if (var10000.__nonzero__()) {
                  var1.setline(384);
                  var1.getlocal(5).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(">>>>>> "));
                  var1.setline(385);
                  var12 = var1.getlocal(7);
                  var12 = var12._iadd(Py.newInteger(1));
                  var1.setlocal(7, var12);
               } else {
                  var1.setline(387);
                  var1.getlocal(5).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("       "));
               }
            }
         }

         var1.setline(388);
         var1.getlocal(5).__getattr__("write").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(9)).__getattr__("expandtabs").__call__((ThreadState)var2, (PyObject)Py.newInteger(8)));
      }
   }

   public PyObject find_lines_from_code$15(PyFrame var1, ThreadState var2) {
      var1.setline(394);
      PyString.fromInterned("Return dict where keys are lines in the line number table.");
      var1.setline(395);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(397);
      PyObject var7 = var1.getglobal("dis").__getattr__("findlinestarts").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(397);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(401);
            var7 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(398);
         PyObject var8 = var1.getlocal(4);
         PyObject var10000 = var8._notin(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(399);
            PyInteger var9 = Py.newInteger(1);
            var1.getlocal(2).__setitem__((PyObject)var1.getlocal(4), var9);
            var5 = null;
         }
      }
   }

   public PyObject find_lines$16(PyFrame var1, ThreadState var2) {
      var1.setline(404);
      PyString.fromInterned("Return lineno dict for all code objects reachable from code.");
      var1.setline(406);
      PyObject var3 = var1.getglobal("find_lines_from_code").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(409);
      var3 = var1.getlocal(0).__getattr__("co_consts").__iter__();

      while(true) {
         var1.setline(409);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(413);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(410);
         if (var1.getglobal("inspect").__getattr__("iscode").__call__(var2, var1.getlocal(3)).__nonzero__()) {
            var1.setline(412);
            var1.getlocal(2).__getattr__("update").__call__(var2, var1.getglobal("find_lines").__call__(var2, var1.getlocal(3), var1.getlocal(1)));
         }
      }
   }

   public PyObject find_strings$17(PyFrame var1, ThreadState var2) {
      var1.setline(421);
      PyString.fromInterned("Return a dict of possible docstring positions.\n\n    The dict maps line numbers to strings.  There is an entry for\n    line that contains only a string or a part of a triple-quoted\n    string.\n    ");
      var1.setline(422);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(425);
      PyObject var8 = var1.getglobal("token").__getattr__("INDENT");
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(426);
      var8 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(427);
      var8 = var1.getglobal("tokenize").__getattr__("generate_tokens").__call__(var2, var1.getlocal(3).__getattr__("readline")).__iter__();

      while(true) {
         var1.setline(427);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(435);
            var1.getlocal(3).__getattr__("close").__call__(var2);
            var1.setline(436);
            var8 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var8;
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
         var1.setline(428);
         PyObject var9 = var1.getlocal(4);
         PyObject var10000 = var9._eq(var1.getglobal("token").__getattr__("STRING"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(429);
            var9 = var1.getlocal(2);
            var10000 = var9._eq(var1.getglobal("token").__getattr__("INDENT"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(430);
               var9 = var1.getlocal(6);
               PyObject[] var10 = Py.unpackSequence(var9, 2);
               PyObject var7 = var10[0];
               var1.setlocal(9, var7);
               var7 = null;
               var7 = var10[1];
               var1.setlocal(10, var7);
               var7 = null;
               var5 = null;
               var1.setline(431);
               var9 = var1.getlocal(7);
               var10 = Py.unpackSequence(var9, 2);
               var7 = var10[0];
               var1.setlocal(11, var7);
               var7 = null;
               var7 = var10[1];
               var1.setlocal(12, var7);
               var7 = null;
               var5 = null;
               var1.setline(432);
               var9 = var1.getglobal("range").__call__(var2, var1.getlocal(9), var1.getlocal(11)._add(Py.newInteger(1))).__iter__();

               while(true) {
                  var1.setline(432);
                  var6 = var9.__iternext__();
                  if (var6 == null) {
                     break;
                  }

                  var1.setlocal(13, var6);
                  var1.setline(433);
                  PyInteger var11 = Py.newInteger(1);
                  var1.getlocal(1).__setitem__((PyObject)var1.getlocal(13), var11);
                  var7 = null;
               }
            }
         }

         var1.setline(434);
         var9 = var1.getlocal(4);
         var1.setlocal(2, var9);
         var5 = null;
      }
   }

   public PyObject find_executable_linenos$18(PyFrame var1, ThreadState var2) {
      var1.setline(439);
      PyString.fromInterned("Return dict where keys are line numbers in the line number table.");

      PyException var3;
      PyObject var4;
      PyObject var6;
      try {
         var1.setline(441);
         var6 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("rU")).__getattr__("read").__call__(var2);
         var1.setlocal(1, var6);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("IOError"))) {
            var4 = var3.value;
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(443);
            var4 = var1.getglobal("sys").__getattr__("stderr");
            Py.println(var4, PyString.fromInterned("Not printing coverage data for %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(2)})));
            var1.setline(445);
            PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
            var1.f_lasti = -1;
            return var7;
         }

         throw var3;
      }

      var1.setline(446);
      var6 = var1.getglobal("compile").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("exec"));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(447);
      var6 = var1.getglobal("find_strings").__call__(var2, var1.getlocal(0));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(448);
      var4 = var1.getglobal("find_lines").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject Trace$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(451);
      PyObject[] var3 = new PyObject[]{Py.newInteger(1), Py.newInteger(1), Py.newInteger(0), Py.newInteger(0), new PyTuple(Py.EmptyObjects), new PyTuple(Py.EmptyObjects), var1.getname("None"), var1.getname("None"), var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, PyString.fromInterned("\n        @param count true iff it should count number of times each\n                     line is executed\n        @param trace true iff it should print out each line that is\n                     being counted\n        @param countfuncs true iff it should just output a list of\n                     (filename, modulename, funcname,) for functions\n                     that were called at least once;  This overrides\n                     `count' and `trace'\n        @param ignoremods a list of the names of modules to ignore\n        @param ignoredirs a list of the names of directories to ignore\n                     all of the (recursive) contents of\n        @param infile file from which to read stored counts to be\n                     added into the results\n        @param outfile file in which to write the results\n        @param timing true iff timing information be displayed\n        "));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(502);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, run$21, (PyObject)null);
      var1.setlocal("run", var4);
      var3 = null;
      var1.setline(507);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, runctx$22, (PyObject)null);
      var1.setlocal("runctx", var4);
      var3 = null;
      var1.setline(518);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, runfunc$23, (PyObject)null);
      var1.setlocal("runfunc", var4);
      var3 = null;
      var1.setline(529);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, file_module_function_of$24, (PyObject)null);
      var1.setlocal("file_module_function_of", var4);
      var3 = null;
      var1.setline(570);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, globaltrace_trackcallers$25, PyString.fromInterned("Handler for call events.\n\n        Adds information about who called who to the self._callers dict.\n        "));
      var1.setlocal("globaltrace_trackcallers", var4);
      var3 = null;
      var1.setline(581);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, globaltrace_countfuncs$26, PyString.fromInterned("Handler for call events.\n\n        Adds (filename, modulename, funcname) to the self._calledfuncs dict.\n        "));
      var1.setlocal("globaltrace_countfuncs", var4);
      var3 = null;
      var1.setline(590);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, globaltrace_lt$27, PyString.fromInterned("Handler for call events.\n\n        If the code block being entered is to be ignored, returns `None',\n        else returns self.localtrace.\n        "));
      var1.setlocal("globaltrace_lt", var4);
      var3 = null;
      var1.setline(613);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, localtrace_trace_and_count$28, (PyObject)null);
      var1.setlocal("localtrace_trace_and_count", var4);
      var3 = null;
      var1.setline(628);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, localtrace_trace$29, (PyObject)null);
      var1.setlocal("localtrace_trace", var4);
      var3 = null;
      var1.setline(641);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, localtrace_count$30, (PyObject)null);
      var1.setlocal("localtrace_count", var4);
      var3 = null;
      var1.setline(649);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, results$31, (PyObject)null);
      var1.setlocal("results", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(470);
      PyString.fromInterned("\n        @param count true iff it should count number of times each\n                     line is executed\n        @param trace true iff it should print out each line that is\n                     being counted\n        @param countfuncs true iff it should just output a list of\n                     (filename, modulename, funcname,) for functions\n                     that were called at least once;  This overrides\n                     `count' and `trace'\n        @param ignoremods a list of the names of modules to ignore\n        @param ignoredirs a list of the names of directories to ignore\n                     all of the (recursive) contents of\n        @param infile file from which to read stored counts to be\n                     added into the results\n        @param outfile file in which to write the results\n        @param timing true iff timing information be displayed\n        ");
      var1.setline(471);
      PyObject var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("infile", var3);
      var3 = null;
      var1.setline(472);
      var3 = var1.getlocal(8);
      var1.getlocal(0).__setattr__("outfile", var3);
      var3 = null;
      var1.setline(473);
      var3 = var1.getglobal("Ignore").__call__(var2, var1.getlocal(5), var1.getlocal(6));
      var1.getlocal(0).__setattr__("ignore", var3);
      var3 = null;
      var1.setline(474);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"counts", var4);
      var3 = null;
      var1.setline(475);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"blabbed", var4);
      var3 = null;
      var1.setline(476);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"pathtobasename", var4);
      var3 = null;
      var1.setline(477);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"donothing", var5);
      var3 = null;
      var1.setline(478);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("trace", var3);
      var3 = null;
      var1.setline(479);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_calledfuncs", var4);
      var3 = null;
      var1.setline(480);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_callers", var4);
      var3 = null;
      var1.setline(481);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_caller_cache", var4);
      var3 = null;
      var1.setline(482);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("start_time", var3);
      var3 = null;
      var1.setline(483);
      if (var1.getlocal(9).__nonzero__()) {
         var1.setline(484);
         var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
         var1.getlocal(0).__setattr__("start_time", var3);
         var3 = null;
      }

      var1.setline(485);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(486);
         var3 = var1.getlocal(0).__getattr__("globaltrace_trackcallers");
         var1.getlocal(0).__setattr__("globaltrace", var3);
         var3 = null;
      } else {
         var1.setline(487);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(488);
            var3 = var1.getlocal(0).__getattr__("globaltrace_countfuncs");
            var1.getlocal(0).__setattr__("globaltrace", var3);
            var3 = null;
         } else {
            var1.setline(489);
            PyObject var10000 = var1.getlocal(2);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(1);
            }

            if (var10000.__nonzero__()) {
               var1.setline(490);
               var3 = var1.getlocal(0).__getattr__("globaltrace_lt");
               var1.getlocal(0).__setattr__("globaltrace", var3);
               var3 = null;
               var1.setline(491);
               var3 = var1.getlocal(0).__getattr__("localtrace_trace_and_count");
               var1.getlocal(0).__setattr__("localtrace", var3);
               var3 = null;
            } else {
               var1.setline(492);
               if (var1.getlocal(2).__nonzero__()) {
                  var1.setline(493);
                  var3 = var1.getlocal(0).__getattr__("globaltrace_lt");
                  var1.getlocal(0).__setattr__("globaltrace", var3);
                  var3 = null;
                  var1.setline(494);
                  var3 = var1.getlocal(0).__getattr__("localtrace_trace");
                  var1.getlocal(0).__setattr__("localtrace", var3);
                  var3 = null;
               } else {
                  var1.setline(495);
                  if (var1.getlocal(1).__nonzero__()) {
                     var1.setline(496);
                     var3 = var1.getlocal(0).__getattr__("globaltrace_lt");
                     var1.getlocal(0).__setattr__("globaltrace", var3);
                     var3 = null;
                     var1.setline(497);
                     var3 = var1.getlocal(0).__getattr__("localtrace_count");
                     var1.getlocal(0).__setattr__("localtrace", var3);
                     var3 = null;
                  } else {
                     var1.setline(500);
                     var5 = Py.newInteger(1);
                     var1.getlocal(0).__setattr__((String)"donothing", var5);
                     var3 = null;
                  }
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$21(PyFrame var1, ThreadState var2) {
      var1.setline(503);
      PyObject var3 = imp.importOne("__main__", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(504);
      var3 = var1.getlocal(2).__getattr__("__dict__");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(505);
      var1.getlocal(0).__getattr__("runctx").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject runctx$22(PyFrame var1, ThreadState var2) {
      var1.setline(508);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getname("None"));
      var3 = null;
      PyDictionary var5;
      if (var10000.__nonzero__()) {
         var1.setline(508);
         var5 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(509);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getname("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(509);
         var5 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(3, var5);
         var3 = null;
      }

      var1.setline(510);
      if (var1.getlocal(0).__getattr__("donothing").__not__().__nonzero__()) {
         var1.setline(511);
         var1.getname("_settrace").__call__(var2, var1.getlocal(0).__getattr__("globaltrace"));
      }

      var3 = null;

      try {
         var1.setline(513);
         Py.exec(var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(515);
         if (var1.getlocal(0).__getattr__("donothing").__not__().__nonzero__()) {
            var1.setline(516);
            var1.getname("_unsettrace").__call__(var2);
         }

         throw (Throwable)var4;
      }

      var1.setline(515);
      if (var1.getlocal(0).__getattr__("donothing").__not__().__nonzero__()) {
         var1.setline(516);
         var1.getname("_unsettrace").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject runfunc$23(PyFrame var1, ThreadState var2) {
      var1.setline(519);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(520);
      if (var1.getlocal(0).__getattr__("donothing").__not__().__nonzero__()) {
         var1.setline(521);
         var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getlocal(0).__getattr__("globaltrace"));
      }

      var3 = null;

      try {
         var1.setline(523);
         PyObject var10000 = var1.getlocal(1);
         PyObject[] var4 = Py.EmptyObjects;
         String[] var5 = new String[0];
         var10000 = var10000._callextra(var4, var5, var1.getlocal(2), var1.getlocal(3));
         var4 = null;
         PyObject var7 = var10000;
         var1.setlocal(4, var7);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(525);
         if (var1.getlocal(0).__getattr__("donothing").__not__().__nonzero__()) {
            var1.setline(526);
            var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getglobal("None"));
         }

         throw (Throwable)var6;
      }

      var1.setline(525);
      if (var1.getlocal(0).__getattr__("donothing").__not__().__nonzero__()) {
         var1.setline(526);
         var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getglobal("None"));
      }

      var1.setline(527);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject file_module_function_of$24(PyFrame var1, ThreadState var2) {
      var1.setline(530);
      PyObject var3 = var1.getlocal(1).__getattr__("f_code");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(531);
      var3 = var1.getlocal(2).__getattr__("co_filename");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(532);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(533);
         var3 = var1.getglobal("modname").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(535);
         var3 = var1.getglobal("None");
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(537);
      var3 = var1.getlocal(2).__getattr__("co_name");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(538);
      var3 = var1.getglobal("None");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(539);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("_caller_cache"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(540);
         var3 = var1.getlocal(0).__getattr__("_caller_cache").__getitem__(var1.getlocal(2));
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(541);
            var3 = var1.getlocal(0).__getattr__("_caller_cache").__getitem__(var1.getlocal(2));
            var1.setlocal(6, var3);
            var3 = null;
         }
      } else {
         var1.setline(543);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__getattr__("_caller_cache").__setitem__(var1.getlocal(2), var3);
         var3 = null;
         var1.setline(546);
         PyList var6 = new PyList();
         var3 = var6.__getattr__("append");
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(546);
         var3 = var1.getglobal("gc").__getattr__("get_referrers").__call__(var2, var1.getlocal(2)).__iter__();

         label57:
         while(true) {
            var1.setline(546);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(546);
               var1.dellocal(8);
               PyList var5 = var6;
               var1.setlocal(7, var5);
               var3 = null;
               var1.setline(551);
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
               var10000 = var3._eq(Py.newInteger(1));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(552);
                  var6 = new PyList();
                  var3 = var6.__getattr__("append");
                  var1.setlocal(11, var3);
                  var3 = null;
                  var1.setline(552);
                  var3 = var1.getglobal("gc").__getattr__("get_referrers").__call__(var2, var1.getlocal(7).__getitem__(Py.newInteger(0))).__iter__();

                  while(true) {
                     var1.setline(552);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(552);
                        var1.dellocal(11);
                        var5 = var6;
                        var1.setlocal(10, var5);
                        var3 = null;
                        var1.setline(554);
                        var3 = var1.getglobal("len").__call__(var2, var1.getlocal(10));
                        var10000 = var3._eq(Py.newInteger(1));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(555);
                           var6 = new PyList();
                           var3 = var6.__getattr__("append");
                           var1.setlocal(14, var3);
                           var3 = null;
                           var1.setline(555);
                           var3 = var1.getglobal("gc").__getattr__("get_referrers").__call__(var2, var1.getlocal(10).__getitem__(Py.newInteger(0))).__iter__();

                           while(true) {
                              var1.setline(555);
                              var4 = var3.__iternext__();
                              if (var4 == null) {
                                 var1.setline(555);
                                 var1.dellocal(14);
                                 var5 = var6;
                                 var1.setlocal(13, var5);
                                 var3 = null;
                                 var1.setline(557);
                                 var3 = var1.getglobal("len").__call__(var2, var1.getlocal(13));
                                 var10000 = var3._eq(Py.newInteger(1));
                                 var3 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(559);
                                    var3 = var1.getlocal(13).__getitem__(Py.newInteger(0)).__getattr__("__name__");
                                    var1.setlocal(6, var3);
                                    var3 = null;
                                    var1.setline(564);
                                    var3 = var1.getlocal(6);
                                    var1.getlocal(0).__getattr__("_caller_cache").__setitem__(var1.getlocal(2), var3);
                                    var3 = null;
                                 }
                                 break label57;
                              }

                              var1.setlocal(15, var4);
                              var1.setline(556);
                              if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(15), (PyObject)PyString.fromInterned("__bases__")).__nonzero__()) {
                                 var1.setline(555);
                                 var1.getlocal(14).__call__(var2, var1.getlocal(15));
                              }
                           }
                        }
                        break label57;
                     }

                     var1.setlocal(12, var4);
                     var1.setline(553);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(12), var1.getglobal("dict")).__nonzero__()) {
                        var1.setline(552);
                        var1.getlocal(11).__call__(var2, var1.getlocal(12));
                     }
                  }
               }
               break;
            }

            var1.setlocal(9, var4);
            var1.setline(547);
            if (var1.getglobal("inspect").__getattr__("isfunction").__call__(var2, var1.getlocal(9)).__nonzero__()) {
               var1.setline(546);
               var1.getlocal(8).__call__(var2, var1.getlocal(9));
            }
         }
      }

      var1.setline(565);
      var3 = var1.getlocal(6);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(566);
         var3 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(5)}));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(568);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject globaltrace_trackcallers$25(PyFrame var1, ThreadState var2) {
      var1.setline(574);
      PyString.fromInterned("Handler for call events.\n\n        Adds information about who called who to the self._callers dict.\n        ");
      var1.setline(575);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("call"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(577);
         var3 = var1.getlocal(0).__getattr__("file_module_function_of").__call__(var2, var1.getlocal(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(578);
         var3 = var1.getlocal(0).__getattr__("file_module_function_of").__call__(var2, var1.getlocal(1).__getattr__("f_back"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(579);
         PyInteger var4 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("_callers").__setitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(4)})), var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject globaltrace_countfuncs$26(PyFrame var1, ThreadState var2) {
      var1.setline(585);
      PyString.fromInterned("Handler for call events.\n\n        Adds (filename, modulename, funcname) to the self._calledfuncs dict.\n        ");
      var1.setline(586);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("call"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(587);
         var3 = var1.getlocal(0).__getattr__("file_module_function_of").__call__(var2, var1.getlocal(1));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(588);
         PyInteger var4 = Py.newInteger(1);
         var1.getlocal(0).__getattr__("_calledfuncs").__setitem__((PyObject)var1.getlocal(4), var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject globaltrace_lt$27(PyFrame var1, ThreadState var2) {
      var1.setline(595);
      PyString.fromInterned("Handler for call events.\n\n        If the code block being entered is to be ignored, returns `None',\n        else returns self.localtrace.\n        ");
      var1.setline(596);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("call"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(597);
         var3 = var1.getlocal(1).__getattr__("f_code");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(598);
         var3 = var1.getlocal(1).__getattr__("f_globals").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__file__"), (PyObject)var1.getglobal("None"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(599);
         if (!var1.getlocal(5).__nonzero__()) {
            var1.setline(611);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(602);
         var3 = var1.getglobal("modname").__call__(var2, var1.getlocal(5));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(603);
         var3 = var1.getlocal(6);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(604);
            var3 = var1.getlocal(0).__getattr__("ignore").__getattr__("names").__call__(var2, var1.getlocal(5), var1.getlocal(6));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(605);
            if (var1.getlocal(7).__not__().__nonzero__()) {
               var1.setline(606);
               if (var1.getlocal(0).__getattr__("trace").__nonzero__()) {
                  var1.setline(607);
                  Py.println(PyString.fromInterned(" --- modulename: %s, funcname: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(4).__getattr__("co_name")})));
               }

               var1.setline(609);
               var3 = var1.getlocal(0).__getattr__("localtrace");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject localtrace_trace_and_count$28(PyFrame var1, ThreadState var2) {
      var1.setline(614);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("line"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(616);
         var3 = var1.getlocal(1).__getattr__("f_code").__getattr__("co_filename");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(617);
         var3 = var1.getlocal(1).__getattr__("f_lineno");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(618);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
         var1.setlocal(6, var4);
         var3 = null;
         var1.setline(619);
         var3 = var1.getlocal(0).__getattr__("counts").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)Py.newInteger(0))._add(Py.newInteger(1));
         var1.getlocal(0).__getattr__("counts").__setitem__(var1.getlocal(6), var3);
         var3 = null;
         var1.setline(621);
         if (var1.getlocal(0).__getattr__("start_time").__nonzero__()) {
            var1.setline(622);
            Py.printComma(PyString.fromInterned("%.2f")._mod(var1.getglobal("time").__getattr__("time").__call__(var2)._sub(var1.getlocal(0).__getattr__("start_time"))));
         }

         var1.setline(623);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(4));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(624);
         Py.printComma(PyString.fromInterned("%s(%d): %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5), var1.getglobal("linecache").__getattr__("getline").__call__(var2, var1.getlocal(4), var1.getlocal(5))})));
      }

      var1.setline(626);
      var3 = var1.getlocal(0).__getattr__("localtrace");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject localtrace_trace$29(PyFrame var1, ThreadState var2) {
      var1.setline(629);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("line"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(631);
         var3 = var1.getlocal(1).__getattr__("f_code").__getattr__("co_filename");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(632);
         var3 = var1.getlocal(1).__getattr__("f_lineno");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(634);
         if (var1.getlocal(0).__getattr__("start_time").__nonzero__()) {
            var1.setline(635);
            Py.printComma(PyString.fromInterned("%.2f")._mod(var1.getglobal("time").__getattr__("time").__call__(var2)._sub(var1.getlocal(0).__getattr__("start_time"))));
         }

         var1.setline(636);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(4));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(637);
         Py.printComma(PyString.fromInterned("%s(%d): %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(5), var1.getglobal("linecache").__getattr__("getline").__call__(var2, var1.getlocal(4), var1.getlocal(5))})));
      }

      var1.setline(639);
      var3 = var1.getlocal(0).__getattr__("localtrace");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject localtrace_count$30(PyFrame var1, ThreadState var2) {
      var1.setline(642);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("line"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(643);
         var3 = var1.getlocal(1).__getattr__("f_code").__getattr__("co_filename");
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(644);
         var3 = var1.getlocal(1).__getattr__("f_lineno");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(645);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
         var1.setlocal(6, var4);
         var3 = null;
         var1.setline(646);
         var3 = var1.getlocal(0).__getattr__("counts").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)Py.newInteger(0))._add(Py.newInteger(1));
         var1.getlocal(0).__getattr__("counts").__setitem__(var1.getlocal(6), var3);
         var3 = null;
      }

      var1.setline(647);
      var3 = var1.getlocal(0).__getattr__("localtrace");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject results$31(PyFrame var1, ThreadState var2) {
      var1.setline(650);
      PyObject var10000 = var1.getglobal("CoverageResults");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("counts"), var1.getlocal(0).__getattr__("infile"), var1.getlocal(0).__getattr__("outfile"), var1.getlocal(0).__getattr__("_calledfuncs"), var1.getlocal(0).__getattr__("_callers")};
      String[] var4 = new String[]{"infile", "outfile", "calledfuncs", "callers"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _err_exit$32(PyFrame var1, ThreadState var2) {
      var1.setline(656);
      var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("%s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)), var1.getlocal(0)})));
      var1.setline(657);
      var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject main$33(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(660);
      PyObject var3 = imp.importOne("getopt", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(662);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(663);
         var3 = var1.getglobal("sys").__getattr__("argv");
         var1.setlocal(0, var3);
         var3 = null;
      }

      PyObject var4;
      PyObject var5;
      PyException var12;
      try {
         var1.setline(665);
         var3 = var1.getlocal(1).__getattr__("getopt").__call__((ThreadState)var2, var1.getlocal(0).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("tcrRf:d:msC:lTg"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("help"), PyString.fromInterned("version"), PyString.fromInterned("trace"), PyString.fromInterned("count"), PyString.fromInterned("report"), PyString.fromInterned("no-report"), PyString.fromInterned("summary"), PyString.fromInterned("file="), PyString.fromInterned("missing"), PyString.fromInterned("ignore-module="), PyString.fromInterned("ignore-dir="), PyString.fromInterned("coverdir="), PyString.fromInterned("listfuncs"), PyString.fromInterned("trackcalls"), PyString.fromInterned("timing")})));
         PyObject[] var11 = Py.unpackSequence(var3, 2);
         var5 = var11[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var11[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var10) {
         var12 = Py.setException(var10, var1);
         if (!var12.match(var1.getlocal(1).__getattr__("error"))) {
            throw var12;
         }

         var4 = var12.value;
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(674);
         var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("%s: %s\n")._mod(new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)), var1.getlocal(4)})));
         var1.setline(675);
         var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("Try `%s --help' for more information\n")._mod(var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0))));
         var1.setline(677);
         var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      }

      var1.setline(679);
      PyInteger var14 = Py.newInteger(0);
      var1.setlocal(5, var14);
      var3 = null;
      var1.setline(680);
      var14 = Py.newInteger(0);
      var1.setlocal(6, var14);
      var3 = null;
      var1.setline(681);
      var14 = Py.newInteger(0);
      var1.setlocal(7, var14);
      var3 = null;
      var1.setline(682);
      var14 = Py.newInteger(0);
      var1.setlocal(8, var14);
      var3 = null;
      var1.setline(683);
      var3 = var1.getglobal("None");
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(684);
      var14 = Py.newInteger(0);
      var1.setlocal(10, var14);
      var3 = null;
      var1.setline(685);
      PyList var16 = new PyList(Py.EmptyObjects);
      var1.setlocal(11, var16);
      var3 = null;
      var1.setline(686);
      var16 = new PyList(Py.EmptyObjects);
      var1.setlocal(12, var16);
      var3 = null;
      var1.setline(687);
      var3 = var1.getglobal("None");
      var1.setlocal(13, var3);
      var3 = null;
      var1.setline(688);
      var14 = Py.newInteger(0);
      var1.setlocal(14, var14);
      var3 = null;
      var1.setline(689);
      var3 = var1.getglobal("False");
      var1.setlocal(15, var3);
      var3 = null;
      var1.setline(690);
      var3 = var1.getglobal("False");
      var1.setlocal(16, var3);
      var3 = null;
      var1.setline(691);
      var3 = var1.getglobal("False");
      var1.setlocal(17, var3);
      var3 = null;
      var1.setline(693);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(693);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(768);
            var10000 = var1.getlocal(15);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(6);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(5);
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(769);
               var1.getglobal("_err_exit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot specify both --listfuncs and (--trace or --count)"));
            }

            var1.setline(771);
            var10000 = var1.getlocal(6);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(5);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(7);
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(15);
                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getlocal(16);
                     }
                  }
               }
            }

            if (var10000.__not__().__nonzero__()) {
               var1.setline(772);
               var1.getglobal("_err_exit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("must specify one of --trace, --count, --report, --listfuncs, or --trackcalls"));
            }

            var1.setline(775);
            var10000 = var1.getlocal(7);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(8);
            }

            if (var10000.__nonzero__()) {
               var1.setline(776);
               var1.getglobal("_err_exit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot specify both --report and --no-report"));
            }

            var1.setline(778);
            var10000 = var1.getlocal(7);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(9).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(779);
               var1.getglobal("_err_exit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--report requires a --file"));
            }

            var1.setline(781);
            var10000 = var1.getlocal(8);
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
               var10000 = var3._eq(Py.newInteger(0));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(782);
               var1.getglobal("_err_exit").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("missing name of file to run"));
            }

            var1.setline(785);
            String[] var13;
            PyObject[] var17;
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(786);
               var10000 = var1.getglobal("CoverageResults");
               var17 = new PyObject[]{var1.getlocal(9), var1.getlocal(9)};
               var13 = new String[]{"infile", "outfile"};
               var10000 = var10000.__call__(var2, var17, var13);
               var3 = null;
               var3 = var10000;
               var1.setlocal(22, var3);
               var3 = null;
               var1.setline(787);
               var10000 = var1.getlocal(22).__getattr__("write_results");
               var17 = new PyObject[]{var1.getlocal(10), var1.getlocal(14), var1.getlocal(13)};
               var13 = new String[]{"summary", "coverdir"};
               var10000.__call__(var2, var17, var13);
               var3 = null;
            } else {
               var1.setline(789);
               var3 = var1.getlocal(3);
               var1.getglobal("sys").__setattr__("argv", var3);
               var3 = null;
               var1.setline(790);
               var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
               var1.setlocal(23, var3);
               var3 = null;
               var1.setline(791);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(23)).__getitem__(Py.newInteger(0));
               var1.getglobal("sys").__getattr__("path").__setitem__((PyObject)Py.newInteger(0), var3);
               var3 = null;
               var1.setline(793);
               var10000 = var1.getglobal("Trace");
               var17 = new PyObject[]{var1.getlocal(6), var1.getlocal(5), var1.getlocal(15), var1.getlocal(16), var1.getlocal(11), var1.getlocal(12), var1.getlocal(9), var1.getlocal(9), var1.getlocal(17)};
               var13 = new String[]{"countfuncs", "countcallers", "ignoremods", "ignoredirs", "infile", "outfile", "timing"};
               var10000 = var10000.__call__(var2, var17, var13);
               var3 = null;
               var3 = var10000;
               var1.setlocal(24, var3);
               var3 = null;

               try {
                  ContextManager var18;
                  var4 = (var18 = ContextGuard.getManager(var1.getglobal("open").__call__(var2, var1.getlocal(23)))).__enter__(var2);

                  label154: {
                     try {
                        var1.setlocal(25, var4);
                        var1.setline(799);
                        var4 = var1.getglobal("compile").__call__((ThreadState)var2, var1.getlocal(25).__getattr__("read").__call__(var2), (PyObject)var1.getlocal(23), (PyObject)PyString.fromInterned("exec"));
                        var1.setlocal(26, var4);
                        var4 = null;
                     } catch (Throwable var8) {
                        if (var18.__exit__(var2, Py.setException(var8, var1))) {
                           break label154;
                        }

                        throw (Throwable)Py.makeException();
                     }

                     var18.__exit__(var2, (PyException)null);
                  }

                  var1.setline(801);
                  PyDictionary var19 = new PyDictionary(new PyObject[]{PyString.fromInterned("__file__"), var1.getlocal(23), PyString.fromInterned("__name__"), PyString.fromInterned("__main__"), PyString.fromInterned("__package__"), var1.getglobal("None"), PyString.fromInterned("__cached__"), var1.getglobal("None")});
                  var1.setlocal(27, var19);
                  var3 = null;
                  var1.setline(807);
                  var1.getlocal(24).__getattr__("runctx").__call__(var2, var1.getlocal(26), var1.getlocal(27), var1.getlocal(27));
               } catch (Throwable var9) {
                  var12 = Py.setException(var9, var1);
                  if (var12.match(var1.getglobal("IOError"))) {
                     var4 = var12.value;
                     var1.setlocal(28, var4);
                     var4 = null;
                     var1.setline(809);
                     var1.getglobal("_err_exit").__call__(var2, PyString.fromInterned("Cannot run file %r because: %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)), var1.getlocal(28)})));
                  } else {
                     if (!var12.match(var1.getglobal("SystemExit"))) {
                        throw var12;
                     }

                     var1.setline(811);
                  }
               }

               var1.setline(813);
               var3 = var1.getlocal(24).__getattr__("results").__call__(var2);
               var1.setlocal(22, var3);
               var3 = null;
               var1.setline(815);
               if (var1.getlocal(8).__not__().__nonzero__()) {
                  var1.setline(816);
                  var10000 = var1.getlocal(22).__getattr__("write_results");
                  var17 = new PyObject[]{var1.getlocal(10), var1.getlocal(14), var1.getlocal(13)};
                  var13 = new String[]{"summary", "coverdir"};
                  var10000.__call__(var2, var17, var13);
                  var3 = null;
               }
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var15 = Py.unpackSequence(var4, 2);
         PyObject var6 = var15[0];
         var1.setlocal(18, var6);
         var6 = null;
         var6 = var15[1];
         var1.setlocal(19, var6);
         var6 = null;
         var1.setline(694);
         var5 = var1.getlocal(18);
         var10000 = var5._eq(PyString.fromInterned("--help"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(695);
            var1.getglobal("usage").__call__(var2, var1.getglobal("sys").__getattr__("stdout"));
            var1.setline(696);
            var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         }

         var1.setline(698);
         var5 = var1.getlocal(18);
         var10000 = var5._eq(PyString.fromInterned("--version"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(699);
            var1.getglobal("sys").__getattr__("stdout").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("trace 2.0\n"));
            var1.setline(700);
            var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         }

         var1.setline(702);
         var5 = var1.getlocal(18);
         var10000 = var5._eq(PyString.fromInterned("-T"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var5 = var1.getlocal(18);
            var10000 = var5._eq(PyString.fromInterned("--trackcalls"));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(703);
            var5 = var1.getglobal("True");
            var1.setlocal(16, var5);
            var5 = null;
         } else {
            var1.setline(706);
            var5 = var1.getlocal(18);
            var10000 = var5._eq(PyString.fromInterned("-l"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var5 = var1.getlocal(18);
               var10000 = var5._eq(PyString.fromInterned("--listfuncs"));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(707);
               var5 = var1.getglobal("True");
               var1.setlocal(15, var5);
               var5 = null;
            } else {
               var1.setline(710);
               var5 = var1.getlocal(18);
               var10000 = var5._eq(PyString.fromInterned("-g"));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var5 = var1.getlocal(18);
                  var10000 = var5._eq(PyString.fromInterned("--timing"));
                  var5 = null;
               }

               if (var10000.__nonzero__()) {
                  var1.setline(711);
                  var5 = var1.getglobal("True");
                  var1.setlocal(17, var5);
                  var5 = null;
               } else {
                  var1.setline(714);
                  var5 = var1.getlocal(18);
                  var10000 = var5._eq(PyString.fromInterned("-t"));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var5 = var1.getlocal(18);
                     var10000 = var5._eq(PyString.fromInterned("--trace"));
                     var5 = null;
                  }

                  PyInteger var20;
                  if (var10000.__nonzero__()) {
                     var1.setline(715);
                     var20 = Py.newInteger(1);
                     var1.setlocal(5, var20);
                     var5 = null;
                  } else {
                     var1.setline(718);
                     var5 = var1.getlocal(18);
                     var10000 = var5._eq(PyString.fromInterned("-c"));
                     var5 = null;
                     if (!var10000.__nonzero__()) {
                        var5 = var1.getlocal(18);
                        var10000 = var5._eq(PyString.fromInterned("--count"));
                        var5 = null;
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(719);
                        var20 = Py.newInteger(1);
                        var1.setlocal(6, var20);
                        var5 = null;
                     } else {
                        var1.setline(722);
                        var5 = var1.getlocal(18);
                        var10000 = var5._eq(PyString.fromInterned("-r"));
                        var5 = null;
                        if (!var10000.__nonzero__()) {
                           var5 = var1.getlocal(18);
                           var10000 = var5._eq(PyString.fromInterned("--report"));
                           var5 = null;
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(723);
                           var20 = Py.newInteger(1);
                           var1.setlocal(7, var20);
                           var5 = null;
                        } else {
                           var1.setline(726);
                           var5 = var1.getlocal(18);
                           var10000 = var5._eq(PyString.fromInterned("-R"));
                           var5 = null;
                           if (!var10000.__nonzero__()) {
                              var5 = var1.getlocal(18);
                              var10000 = var5._eq(PyString.fromInterned("--no-report"));
                              var5 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(727);
                              var20 = Py.newInteger(1);
                              var1.setlocal(8, var20);
                              var5 = null;
                           } else {
                              var1.setline(730);
                              var5 = var1.getlocal(18);
                              var10000 = var5._eq(PyString.fromInterned("-f"));
                              var5 = null;
                              if (!var10000.__nonzero__()) {
                                 var5 = var1.getlocal(18);
                                 var10000 = var5._eq(PyString.fromInterned("--file"));
                                 var5 = null;
                              }

                              if (var10000.__nonzero__()) {
                                 var1.setline(731);
                                 var5 = var1.getlocal(19);
                                 var1.setlocal(9, var5);
                                 var5 = null;
                              } else {
                                 var1.setline(734);
                                 var5 = var1.getlocal(18);
                                 var10000 = var5._eq(PyString.fromInterned("-m"));
                                 var5 = null;
                                 if (!var10000.__nonzero__()) {
                                    var5 = var1.getlocal(18);
                                    var10000 = var5._eq(PyString.fromInterned("--missing"));
                                    var5 = null;
                                 }

                                 if (var10000.__nonzero__()) {
                                    var1.setline(735);
                                    var20 = Py.newInteger(1);
                                    var1.setlocal(10, var20);
                                    var5 = null;
                                 } else {
                                    var1.setline(738);
                                    var5 = var1.getlocal(18);
                                    var10000 = var5._eq(PyString.fromInterned("-C"));
                                    var5 = null;
                                    if (!var10000.__nonzero__()) {
                                       var5 = var1.getlocal(18);
                                       var10000 = var5._eq(PyString.fromInterned("--coverdir"));
                                       var5 = null;
                                    }

                                    if (var10000.__nonzero__()) {
                                       var1.setline(739);
                                       var5 = var1.getlocal(19);
                                       var1.setlocal(13, var5);
                                       var5 = null;
                                    } else {
                                       var1.setline(742);
                                       var5 = var1.getlocal(18);
                                       var10000 = var5._eq(PyString.fromInterned("-s"));
                                       var5 = null;
                                       if (!var10000.__nonzero__()) {
                                          var5 = var1.getlocal(18);
                                          var10000 = var5._eq(PyString.fromInterned("--summary"));
                                          var5 = null;
                                       }

                                       if (var10000.__nonzero__()) {
                                          var1.setline(743);
                                          var20 = Py.newInteger(1);
                                          var1.setlocal(14, var20);
                                          var5 = null;
                                       } else {
                                          var1.setline(746);
                                          var5 = var1.getlocal(18);
                                          var10000 = var5._eq(PyString.fromInterned("--ignore-module"));
                                          var5 = null;
                                          if (var10000.__nonzero__()) {
                                             var1.setline(747);
                                             var5 = var1.getlocal(19).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__iter__();

                                             while(true) {
                                                var1.setline(747);
                                                var6 = var5.__iternext__();
                                                if (var6 == null) {
                                                   break;
                                                }

                                                var1.setlocal(20, var6);
                                                var1.setline(748);
                                                var1.getlocal(11).__getattr__("append").__call__(var2, var1.getlocal(20).__getattr__("strip").__call__(var2));
                                             }
                                          } else {
                                             var1.setline(751);
                                             var5 = var1.getlocal(18);
                                             var10000 = var5._eq(PyString.fromInterned("--ignore-dir"));
                                             var5 = null;
                                             if (var10000.__nonzero__()) {
                                                var1.setline(752);
                                                var5 = var1.getlocal(19).__getattr__("split").__call__(var2, var1.getglobal("os").__getattr__("pathsep")).__iter__();

                                                while(true) {
                                                   var1.setline(752);
                                                   var6 = var5.__iternext__();
                                                   if (var6 == null) {
                                                      break;
                                                   }

                                                   var1.setlocal(21, var6);
                                                   var1.setline(753);
                                                   PyObject var7 = var1.getglobal("os").__getattr__("path").__getattr__("expandvars").__call__(var2, var1.getlocal(21));
                                                   var1.setlocal(21, var7);
                                                   var7 = null;
                                                   var1.setline(756);
                                                   var7 = var1.getlocal(21).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$prefix"), (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("prefix"), (PyObject)PyString.fromInterned("lib"), (PyObject)PyString.fromInterned("python")._add(var1.getglobal("sys").__getattr__("version").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null))));
                                                   var1.setlocal(21, var7);
                                                   var7 = null;
                                                   var1.setline(759);
                                                   var7 = var1.getlocal(21).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$exec_prefix"), (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("exec_prefix"), (PyObject)PyString.fromInterned("lib"), (PyObject)PyString.fromInterned("python")._add(var1.getglobal("sys").__getattr__("version").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null))));
                                                   var1.setlocal(21, var7);
                                                   var7 = null;
                                                   var1.setline(762);
                                                   var7 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(21));
                                                   var1.setlocal(21, var7);
                                                   var7 = null;
                                                   var1.setline(763);
                                                   var1.getlocal(12).__getattr__("append").__call__(var2, var1.getlocal(21));
                                                }
                                             } else {
                                                var1.setline(766);
                                                if (var1.getglobal("__debug__").__nonzero__() && !Py.newInteger(0).__nonzero__()) {
                                                   throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Should never get here"));
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

   public trace$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _unsettrace$1 = Py.newCode(0, var2, var1, "_unsettrace", 72, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func"};
      _settrace$2 = Py.newCode(1, var2, var1, "_settrace", 75, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _unsettrace$3 = Py.newCode(0, var2, var1, "_unsettrace", 79, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"outfile"};
      usage$4 = Py.newCode(1, var2, var1, "usage", 83, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Ignore$5 = Py.newCode(0, var2, var1, "Ignore", 133, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "modules", "dirs"};
      __init__$6 = Py.newCode(3, var2, var1, "__init__", 134, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "modulename", "mod", "n", "d"};
      names$7 = Py.newCode(3, var2, var1, "names", 141, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "base", "filename", "ext"};
      modname$8 = Py.newCode(1, var2, var1, "modname", 186, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path", "comparepath", "longest", "dir", "base", "drive", "filename", "ext"};
      fullmodname$9 = Py.newCode(1, var2, var1, "fullmodname", 193, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CoverageResults$10 = Py.newCode(0, var2, var1, "CoverageResults", 221, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "counts", "calledfuncs", "infile", "callers", "outfile", "err"};
      __init__$11 = Py.newCode(6, var2, var1, "__init__", 222, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other", "counts", "calledfuncs", "callers", "other_counts", "other_calledfuncs", "other_callers", "key"};
      update$12 = Py.newCode(2, var2, var1, "update", 248, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "show_missing", "summary", "coverdir", "calls", "filename", "modulename", "funcname", "lastfile", "lastcfile", "pfile", "pmod", "pfunc", "cfile", "cmod", "cfunc", "per_file", "lineno", "lines_hit", "sums", "count", "dir", "lnotab", "source", "coverpath", "n_hits", "n_lines", "percent", "mods", "m", "err"};
      write_results$13 = Py.newCode(4, var2, var1, "write_results", 266, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "lines", "lnotab", "lines_hit", "outfile", "err", "n_lines", "n_hits", "i", "line", "lineno"};
      write_results_file$14 = Py.newCode(5, var2, var1, "write_results_file", 357, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"code", "strs", "linenos", "_", "lineno"};
      find_lines_from_code$15 = Py.newCode(2, var2, var1, "find_lines_from_code", 393, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"code", "strs", "linenos", "c"};
      find_lines$16 = Py.newCode(2, var2, var1, "find_lines", 403, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "d", "prev_ttype", "f", "ttype", "tstr", "start", "end", "line", "sline", "scol", "eline", "ecol", "i"};
      find_strings$17 = Py.newCode(1, var2, var1, "find_strings", 415, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "prog", "err", "code", "strs"};
      find_executable_linenos$18 = Py.newCode(1, var2, var1, "find_executable_linenos", 438, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Trace$19 = Py.newCode(0, var2, var1, "Trace", 450, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "count", "trace", "countfuncs", "countcallers", "ignoremods", "ignoredirs", "infile", "outfile", "timing"};
      __init__$20 = Py.newCode(10, var2, var1, "__init__", 451, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "__main__", "dict"};
      run$21 = Py.newCode(2, var2, var1, "run", 502, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "globals", "locals"};
      runctx$22 = Py.newCode(4, var2, var1, "runctx", 507, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "func", "args", "kw", "result"};
      runfunc$23 = Py.newCode(4, var2, var1, "runfunc", 518, true, true, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "code", "filename", "modulename", "funcname", "clsname", "funcs", "_[546_21]", "f", "dicts", "_[552_25]", "d", "classes", "_[555_31]", "c"};
      file_module_function_of$24 = Py.newCode(2, var2, var1, "file_module_function_of", 529, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "why", "arg", "this_func", "parent_func"};
      globaltrace_trackcallers$25 = Py.newCode(4, var2, var1, "globaltrace_trackcallers", 570, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "why", "arg", "this_func"};
      globaltrace_countfuncs$26 = Py.newCode(4, var2, var1, "globaltrace_countfuncs", 581, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "why", "arg", "code", "filename", "modulename", "ignore_it"};
      globaltrace_lt$27 = Py.newCode(4, var2, var1, "globaltrace_lt", 590, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "why", "arg", "filename", "lineno", "key", "bname"};
      localtrace_trace_and_count$28 = Py.newCode(4, var2, var1, "localtrace_trace_and_count", 613, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "why", "arg", "filename", "lineno", "bname"};
      localtrace_trace$29 = Py.newCode(4, var2, var1, "localtrace_trace", 628, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "why", "arg", "filename", "lineno", "key"};
      localtrace_count$30 = Py.newCode(4, var2, var1, "localtrace_count", 641, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      results$31 = Py.newCode(1, var2, var1, "results", 649, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg"};
      _err_exit$32 = Py.newCode(1, var2, var1, "_err_exit", 655, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"argv", "getopt", "opts", "prog_argv", "msg", "trace", "count", "report", "no_report", "counts_file", "missing", "ignore_modules", "ignore_dirs", "coverdir", "summary", "listfuncs", "countcallers", "timing", "opt", "val", "mod", "s", "results", "progname", "t", "fp", "code", "globs", "err"};
      main$33 = Py.newCode(1, var2, var1, "main", 659, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new trace$py("trace$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(trace$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._unsettrace$1(var2, var3);
         case 2:
            return this._settrace$2(var2, var3);
         case 3:
            return this._unsettrace$3(var2, var3);
         case 4:
            return this.usage$4(var2, var3);
         case 5:
            return this.Ignore$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.names$7(var2, var3);
         case 8:
            return this.modname$8(var2, var3);
         case 9:
            return this.fullmodname$9(var2, var3);
         case 10:
            return this.CoverageResults$10(var2, var3);
         case 11:
            return this.__init__$11(var2, var3);
         case 12:
            return this.update$12(var2, var3);
         case 13:
            return this.write_results$13(var2, var3);
         case 14:
            return this.write_results_file$14(var2, var3);
         case 15:
            return this.find_lines_from_code$15(var2, var3);
         case 16:
            return this.find_lines$16(var2, var3);
         case 17:
            return this.find_strings$17(var2, var3);
         case 18:
            return this.find_executable_linenos$18(var2, var3);
         case 19:
            return this.Trace$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.run$21(var2, var3);
         case 22:
            return this.runctx$22(var2, var3);
         case 23:
            return this.runfunc$23(var2, var3);
         case 24:
            return this.file_module_function_of$24(var2, var3);
         case 25:
            return this.globaltrace_trackcallers$25(var2, var3);
         case 26:
            return this.globaltrace_countfuncs$26(var2, var3);
         case 27:
            return this.globaltrace_lt$27(var2, var3);
         case 28:
            return this.localtrace_trace_and_count$28(var2, var3);
         case 29:
            return this.localtrace_trace$29(var2, var3);
         case 30:
            return this.localtrace_count$30(var2, var3);
         case 31:
            return this.results$31(var2, var3);
         case 32:
            return this._err_exit$32(var2, var3);
         case 33:
            return this.main$33(var2, var3);
         default:
            return null;
      }
   }
}
