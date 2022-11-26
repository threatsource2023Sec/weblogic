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
@MTime(1498849383000L)
@Filename("bdb.py")
public class bdb$py extends PyFunctionTable implements PyRunnable {
   static bdb$py self;
   static final PyCode f$0;
   static final PyCode BdbQuit$1;
   static final PyCode Bdb$2;
   static final PyCode __init__$3;
   static final PyCode canonic$4;
   static final PyCode reset$5;
   static final PyCode trace_dispatch$6;
   static final PyCode dispatch_line$7;
   static final PyCode dispatch_call$8;
   static final PyCode dispatch_return$9;
   static final PyCode dispatch_exception$10;
   static final PyCode is_skipped_module$11;
   static final PyCode stop_here$12;
   static final PyCode break_here$13;
   static final PyCode do_clear$14;
   static final PyCode break_anywhere$15;
   static final PyCode user_call$16;
   static final PyCode user_line$17;
   static final PyCode user_return$18;
   static final PyCode user_exception$19;
   static final PyCode _set_stopinfo$20;
   static final PyCode set_until$21;
   static final PyCode set_step$22;
   static final PyCode set_next$23;
   static final PyCode set_return$24;
   static final PyCode set_trace$25;
   static final PyCode set_continue$26;
   static final PyCode set_quit$27;
   static final PyCode set_break$28;
   static final PyCode _prune_breaks$29;
   static final PyCode clear_break$30;
   static final PyCode clear_bpbynumber$31;
   static final PyCode clear_all_file_breaks$32;
   static final PyCode clear_all_breaks$33;
   static final PyCode get_break$34;
   static final PyCode get_breaks$35;
   static final PyCode get_file_breaks$36;
   static final PyCode get_all_breaks$37;
   static final PyCode get_stack$38;
   static final PyCode format_stack_entry$39;
   static final PyCode run$40;
   static final PyCode runeval$41;
   static final PyCode runctx$42;
   static final PyCode runcall$43;
   static final PyCode set_trace$44;
   static final PyCode Breakpoint$45;
   static final PyCode __init__$46;
   static final PyCode deleteMe$47;
   static final PyCode enable$48;
   static final PyCode disable$49;
   static final PyCode bpprint$50;
   static final PyCode checkfuncname$51;
   static final PyCode effective$52;
   static final PyCode Tdb$53;
   static final PyCode user_call$54;
   static final PyCode user_line$55;
   static final PyCode user_return$56;
   static final PyCode user_exception$57;
   static final PyCode foo$58;
   static final PyCode bar$59;
   static final PyCode test$60;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Debugger basics"));
      var1.setline(1);
      PyString.fromInterned("Debugger basics");
      var1.setline(3);
      PyObject var3 = imp.importOne("fnmatch", var1, -1);
      var1.setlocal("fnmatch", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(8);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("BdbQuit"), PyString.fromInterned("Bdb"), PyString.fromInterned("Breakpoint")});
      var1.setlocal("__all__", var5);
      var3 = null;
      var1.setline(10);
      PyObject[] var6 = new PyObject[]{var1.getname("Exception")};
      PyObject var4 = Py.makeClass("BdbQuit", var6, BdbQuit$1);
      var1.setlocal("BdbQuit", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(14);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Bdb", var6, Bdb$2);
      var1.setlocal("Bdb", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(445);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, set_trace$44, (PyObject)null);
      var1.setlocal("set_trace", var7);
      var3 = null;
      var1.setline(449);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Breakpoint", var6, Breakpoint$45);
      var1.setlocal("Breakpoint", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(533);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, checkfuncname$51, PyString.fromInterned("Check whether we should break here because of `b.funcname`."));
      var1.setlocal("checkfuncname", var7);
      var3 = null;
      var1.setline(561);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, effective$52, PyString.fromInterned("Determine which breakpoint for this file:line is to be acted upon.\n\n    Called only if we know there is a bpt at this\n    location.  Returns breakpoint that was triggered and a flag\n    that indicates if it is ok to delete a temporary bp.\n\n    "));
      var1.setlocal("effective", var7);
      var3 = null;
      var1.setline(614);
      var6 = new PyObject[]{var1.getname("Bdb")};
      var4 = Py.makeClass("Tdb", var6, Tdb$53);
      var1.setlocal("Tdb", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(632);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, foo$58, (PyObject)null);
      var1.setlocal("foo", var7);
      var3 = null;
      var1.setline(637);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, bar$59, (PyObject)null);
      var1.setlocal("bar", var7);
      var3 = null;
      var1.setline(641);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, test$60, (PyObject)null);
      var1.setlocal("test", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BdbQuit$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Exception to give up completely"));
      var1.setline(11);
      PyString.fromInterned("Exception to give up completely");
      return var1.getf_locals();
   }

   public PyObject Bdb$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Generic Python debugger base class.\n\n    This class takes care of details of the trace facility;\n    a derived class should implement user interaction.\n    The standard debugger class (pdb.Pdb) is an example.\n    "));
      var1.setline(21);
      PyString.fromInterned("Generic Python debugger base class.\n\n    This class takes care of details of the trace facility;\n    a derived class should implement user interaction.\n    The standard debugger class (pdb.Pdb) is an example.\n    ");
      var1.setline(23);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(29);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, canonic$4, (PyObject)null);
      var1.setlocal("canonic", var4);
      var3 = null;
      var1.setline(39);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, reset$5, (PyObject)null);
      var1.setlocal("reset", var4);
      var3 = null;
      var1.setline(45);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, trace_dispatch$6, (PyObject)null);
      var1.setlocal("trace_dispatch", var4);
      var3 = null;
      var1.setline(65);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dispatch_line$7, (PyObject)null);
      var1.setlocal("dispatch_line", var4);
      var3 = null;
      var1.setline(71);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dispatch_call$8, (PyObject)null);
      var1.setlocal("dispatch_call", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dispatch_return$9, (PyObject)null);
      var1.setlocal("dispatch_return", var4);
      var3 = null;
      var1.setline(94);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, dispatch_exception$10, (PyObject)null);
      var1.setlocal("dispatch_exception", var4);
      var3 = null;
      var1.setline(104);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, is_skipped_module$11, (PyObject)null);
      var1.setlocal("is_skipped_module", var4);
      var3 = null;
      var1.setline(110);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, stop_here$12, (PyObject)null);
      var1.setlocal("stop_here", var4);
      var3 = null;
      var1.setline(126);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, break_here$13, (PyObject)null);
      var1.setlocal("break_here", var4);
      var3 = null;
      var1.setline(148);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, do_clear$14, (PyObject)null);
      var1.setlocal("do_clear", var4);
      var3 = null;
      var1.setline(151);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, break_anywhere$15, (PyObject)null);
      var1.setlocal("break_anywhere", var4);
      var3 = null;
      var1.setline(157);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, user_call$16, PyString.fromInterned("This method is called when there is the remote possibility\n        that we ever need to stop in this function."));
      var1.setlocal("user_call", var4);
      var3 = null;
      var1.setline(162);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, user_line$17, PyString.fromInterned("This method is called when we stop or break at this line."));
      var1.setlocal("user_line", var4);
      var3 = null;
      var1.setline(166);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, user_return$18, PyString.fromInterned("This method is called when a return trap is set here."));
      var1.setlocal("user_return", var4);
      var3 = null;
      var1.setline(170);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, user_exception$19, (PyObject)null);
      var1.setlocal("user_exception", var4);
      var3 = null;
      var1.setline(176);
      var3 = new PyObject[]{Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, _set_stopinfo$20, (PyObject)null);
      var1.setlocal("_set_stopinfo", var4);
      var3 = null;
      var1.setline(187);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_until$21, PyString.fromInterned("Stop when the line with the line no greater than the current one is\n        reached or when returning from current frame"));
      var1.setlocal("set_until", var4);
      var3 = null;
      var1.setline(192);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_step$22, PyString.fromInterned("Stop after one line of code."));
      var1.setlocal("set_step", var4);
      var3 = null;
      var1.setline(204);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_next$23, PyString.fromInterned("Stop on the next line in or below the given frame."));
      var1.setlocal("set_next", var4);
      var3 = null;
      var1.setline(208);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_return$24, PyString.fromInterned("Stop when returning from the given frame."));
      var1.setlocal("set_return", var4);
      var3 = null;
      var1.setline(212);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, set_trace$25, PyString.fromInterned("Start debugging from `frame`.\n\n        If frame is not specified, debugging starts from caller's frame.\n        "));
      var1.setlocal("set_trace", var4);
      var3 = null;
      var1.setline(227);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_continue$26, (PyObject)null);
      var1.setlocal("set_continue", var4);
      var3 = null;
      var1.setline(238);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, set_quit$27, (PyObject)null);
      var1.setlocal("set_quit", var4);
      var3 = null;
      var1.setline(251);
      var3 = new PyObject[]{Py.newInteger(0), var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, set_break$28, (PyObject)null);
      var1.setlocal("set_break", var4);
      var3 = null;
      var1.setline(266);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _prune_breaks$29, (PyObject)null);
      var1.setlocal("_prune_breaks", var4);
      var3 = null;
      var1.setline(272);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear_break$30, (PyObject)null);
      var1.setlocal("clear_break", var4);
      var3 = null;
      var1.setline(285);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear_bpbynumber$31, (PyObject)null);
      var1.setlocal("clear_bpbynumber", var4);
      var3 = null;
      var1.setline(299);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear_all_file_breaks$32, (PyObject)null);
      var1.setlocal("clear_all_file_breaks", var4);
      var3 = null;
      var1.setline(309);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear_all_breaks$33, (PyObject)null);
      var1.setlocal("clear_all_breaks", var4);
      var3 = null;
      var1.setline(317);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_break$34, (PyObject)null);
      var1.setlocal("get_break", var4);
      var3 = null;
      var1.setline(322);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_breaks$35, (PyObject)null);
      var1.setlocal("get_breaks", var4);
      var3 = null;
      var1.setline(328);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_file_breaks$36, (PyObject)null);
      var1.setlocal("get_file_breaks", var4);
      var3 = null;
      var1.setline(335);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_all_breaks$37, (PyObject)null);
      var1.setlocal("get_all_breaks", var4);
      var3 = null;
      var1.setline(341);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_stack$38, (PyObject)null);
      var1.setlocal("get_stack", var4);
      var3 = null;
      var1.setline(361);
      var3 = new PyObject[]{PyString.fromInterned(": ")};
      var4 = new PyFunction(var1.f_globals, var3, format_stack_entry$39, (PyObject)null);
      var1.setlocal("format_stack_entry", var4);
      var3 = null;
      var1.setline(389);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, run$40, (PyObject)null);
      var1.setlocal("run", var4);
      var3 = null;
      var1.setline(407);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, runeval$41, (PyObject)null);
      var1.setlocal("runeval", var4);
      var3 = null;
      var1.setline(425);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, runctx$42, (PyObject)null);
      var1.setlocal("runctx", var4);
      var3 = null;
      var1.setline(431);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, runcall$43, (PyObject)null);
      var1.setlocal("runcall", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      var1.setline(24);
      PyObject var3 = var1.getlocal(1).__nonzero__() ? var1.getglobal("set").__call__(var2, var1.getlocal(1)) : var1.getglobal("None");
      var1.getlocal(0).__setattr__("skip", var3);
      var3 = null;
      var1.setline(25);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"breaks", var4);
      var3 = null;
      var1.setline(26);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"fncache", var4);
      var3 = null;
      var1.setline(27);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("frame_returning", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject canonic$4(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("<")._add(var1.getlocal(1).__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null))._add(PyString.fromInterned(">")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(31);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(32);
         PyObject var4 = var1.getlocal(0).__getattr__("fncache").__getattr__("get").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(33);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(34);
            var4 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(1));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(35);
            var4 = var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(2));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(36);
            var4 = var1.getlocal(2);
            var1.getlocal(0).__getattr__("fncache").__setitem__(var1.getlocal(1), var4);
            var4 = null;
         }

         var1.setline(37);
         var3 = var1.getlocal(2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject reset$5(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyObject var3 = imp.importOne("linecache", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(41);
      var1.getlocal(1).__getattr__("checkcache").__call__(var2);
      var1.setline(42);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("botframe", var3);
      var3 = null;
      var1.setline(43);
      var1.getlocal(0).__getattr__("_set_stopinfo").__call__(var2, var1.getglobal("None"), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject trace_dispatch$6(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      if (var1.getlocal(0).__getattr__("quitting").__nonzero__()) {
         var1.setline(47);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(48);
         PyObject var3 = var1.getlocal(2);
         PyObject var10000 = var3._eq(PyString.fromInterned("line"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(49);
            var3 = var1.getlocal(0).__getattr__("dispatch_line").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(50);
            PyObject var4 = var1.getlocal(2);
            var10000 = var4._eq(PyString.fromInterned("call"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(51);
               var3 = var1.getlocal(0).__getattr__("dispatch_call").__call__(var2, var1.getlocal(1), var1.getlocal(3));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(52);
               var4 = var1.getlocal(2);
               var10000 = var4._eq(PyString.fromInterned("return"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(53);
                  var3 = var1.getlocal(0).__getattr__("dispatch_return").__call__(var2, var1.getlocal(1), var1.getlocal(3));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(54);
                  var4 = var1.getlocal(2);
                  var10000 = var4._eq(PyString.fromInterned("exception"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(55);
                     var3 = var1.getlocal(0).__getattr__("dispatch_exception").__call__(var2, var1.getlocal(1), var1.getlocal(3));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(56);
                     var4 = var1.getlocal(2);
                     var10000 = var4._eq(PyString.fromInterned("c_call"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(57);
                        var3 = var1.getlocal(0).__getattr__("trace_dispatch");
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(58);
                        var4 = var1.getlocal(2);
                        var10000 = var4._eq(PyString.fromInterned("c_exception"));
                        var4 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(59);
                           var3 = var1.getlocal(0).__getattr__("trace_dispatch");
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(60);
                           var4 = var1.getlocal(2);
                           var10000 = var4._eq(PyString.fromInterned("c_return"));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(61);
                              var3 = var1.getlocal(0).__getattr__("trace_dispatch");
                              var1.f_lasti = -1;
                              return var3;
                           } else {
                              var1.setline(62);
                              Py.printComma(PyString.fromInterned("bdb.Bdb.dispatch: unknown debugging event:"));
                              Py.println(var1.getglobal("repr").__call__(var2, var1.getlocal(2)));
                              var1.setline(63);
                              var3 = var1.getlocal(0).__getattr__("trace_dispatch");
                              var1.f_lasti = -1;
                              return var3;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public PyObject dispatch_line$7(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject var10000 = var1.getlocal(0).__getattr__("stop_here").__call__(var2, var1.getlocal(1));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("break_here").__call__(var2, var1.getlocal(1));
      }

      if (var10000.__nonzero__()) {
         var1.setline(67);
         var1.getlocal(0).__getattr__("user_line").__call__(var2, var1.getlocal(1));
         var1.setline(68);
         if (var1.getlocal(0).__getattr__("quitting").__nonzero__()) {
            var1.setline(68);
            throw Py.makeException(var1.getglobal("BdbQuit"));
         }
      }

      var1.setline(69);
      PyObject var3 = var1.getlocal(0).__getattr__("trace_dispatch");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dispatch_call$8(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyObject var3 = var1.getlocal(0).__getattr__("botframe");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(75);
         var3 = var1.getlocal(1).__getattr__("f_back");
         var1.getlocal(0).__setattr__("botframe", var3);
         var3 = null;
         var1.setline(76);
         var3 = var1.getlocal(0).__getattr__("trace_dispatch");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(77);
         var10000 = var1.getlocal(0).__getattr__("stop_here").__call__(var2, var1.getlocal(1));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("break_anywhere").__call__(var2, var1.getlocal(1));
         }

         if (var10000.__not__().__nonzero__()) {
            var1.setline(79);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(80);
            var1.getlocal(0).__getattr__("user_call").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.setline(81);
            if (var1.getlocal(0).__getattr__("quitting").__nonzero__()) {
               var1.setline(81);
               throw Py.makeException(var1.getglobal("BdbQuit"));
            } else {
               var1.setline(82);
               var3 = var1.getlocal(0).__getattr__("trace_dispatch");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject dispatch_return$9(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyObject var10000 = var1.getlocal(0).__getattr__("stop_here").__call__(var2, var1.getlocal(1));
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getlocal(0).__getattr__("returnframe"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var3 = null;

         PyObject var4;
         try {
            var1.setline(87);
            var4 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("frame_returning", var4);
            var4 = null;
            var1.setline(88);
            var1.getlocal(0).__getattr__("user_return").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(90);
            var4 = var1.getglobal("None");
            var1.getlocal(0).__setattr__("frame_returning", var4);
            var4 = null;
            throw (Throwable)var5;
         }

         var1.setline(90);
         var4 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("frame_returning", var4);
         var4 = null;
         var1.setline(91);
         if (var1.getlocal(0).__getattr__("quitting").__nonzero__()) {
            var1.setline(91);
            throw Py.makeException(var1.getglobal("BdbQuit"));
         }
      }

      var1.setline(92);
      var3 = var1.getlocal(0).__getattr__("trace_dispatch");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject dispatch_exception$10(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      if (var1.getlocal(0).__getattr__("stop_here").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(96);
         var1.getlocal(0).__getattr__("user_exception").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setline(97);
         if (var1.getlocal(0).__getattr__("quitting").__nonzero__()) {
            var1.setline(97);
            throw Py.makeException(var1.getglobal("BdbQuit"));
         }
      }

      var1.setline(98);
      PyObject var3 = var1.getlocal(0).__getattr__("trace_dispatch");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject is_skipped_module$11(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyObject var3 = var1.getlocal(0).__getattr__("skip").__iter__();

      PyObject var5;
      do {
         var1.setline(105);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(108);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(106);
      } while(!var1.getglobal("fnmatch").__getattr__("fnmatch").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__nonzero__());

      var1.setline(107);
      var5 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject stop_here$12(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyObject var10000 = var1.getlocal(0).__getattr__("skip");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("is_skipped_module").__call__(var2, var1.getlocal(1).__getattr__("f_globals").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__name__")));
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(115);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(116);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getlocal(0).__getattr__("stopframe"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(117);
            var4 = var1.getlocal(0).__getattr__("stoplineno");
            var10000 = var4._eq(Py.newInteger(-1));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(118);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(119);
               var4 = var1.getlocal(1).__getattr__("f_lineno");
               var10000 = var4._ge(var1.getlocal(0).__getattr__("stoplineno"));
               var4 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            }
         } else {
            while(true) {
               var1.setline(120);
               var4 = var1.getlocal(1);
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(1);
                  var10000 = var4._isnot(var1.getlocal(0).__getattr__("stopframe"));
                  var4 = null;
               }

               if (!var10000.__nonzero__()) {
                  var1.setline(124);
                  var3 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(121);
               var4 = var1.getlocal(1);
               var10000 = var4._is(var1.getlocal(0).__getattr__("botframe"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(122);
                  var3 = var1.getglobal("True");
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(123);
               var4 = var1.getlocal(1).__getattr__("f_back");
               var1.setlocal(1, var4);
               var4 = null;
            }
         }
      }
   }

   public PyObject break_here$13(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyObject var3 = var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(1).__getattr__("f_code").__getattr__("co_filename"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("breaks"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(129);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(130);
         PyObject var4 = var1.getlocal(1).__getattr__("f_lineno");
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(131);
         var4 = var1.getlocal(3);
         var10000 = var4._in(var1.getlocal(0).__getattr__("breaks").__getitem__(var1.getlocal(2)));
         var4 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(134);
            var4 = var1.getlocal(1).__getattr__("f_code").__getattr__("co_firstlineno");
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(135);
            var4 = var1.getlocal(3);
            var10000 = var4._in(var1.getlocal(0).__getattr__("breaks").__getitem__(var1.getlocal(2)));
            var4 = null;
            if (var10000.__not__().__nonzero__()) {
               var1.setline(136);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(139);
         var4 = var1.getglobal("effective").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(1));
         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var4 = null;
         var1.setline(140);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(141);
            var4 = var1.getlocal(4).__getattr__("number");
            var1.getlocal(0).__setattr__("currentbp", var4);
            var4 = null;
            var1.setline(142);
            var10000 = var1.getlocal(5);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(4).__getattr__("temporary");
            }

            if (var10000.__nonzero__()) {
               var1.setline(143);
               var1.getlocal(0).__getattr__("do_clear").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(4).__getattr__("number")));
            }

            var1.setline(144);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(146);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject do_clear$14(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      throw Py.makeException(var1.getglobal("NotImplementedError"), PyString.fromInterned("subclass of bdb must implement do_clear()"));
   }

   public PyObject break_anywhere$15(PyFrame var1, ThreadState var2) {
      var1.setline(152);
      PyObject var3 = var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(1).__getattr__("f_code").__getattr__("co_filename"));
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("breaks"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject user_call$16(PyFrame var1, ThreadState var2) {
      var1.setline(159);
      PyString.fromInterned("This method is called when there is the remote possibility\n        that we ever need to stop in this function.");
      var1.setline(160);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject user_line$17(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyString.fromInterned("This method is called when we stop or break at this line.");
      var1.setline(164);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject user_return$18(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      PyString.fromInterned("This method is called when a return trap is set here.");
      var1.setline(168);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject user_exception$19(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      PyObject var3 = var1.getlocal(2);
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(173);
      PyString.fromInterned("This method is called if an exception occurs,\n        but only if we are to stop at or just below this level.");
      var1.setline(174);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _set_stopinfo$20(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("stopframe", var3);
      var3 = null;
      var1.setline(178);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("returnframe", var3);
      var3 = null;
      var1.setline(179);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"quitting", var4);
      var3 = null;
      var1.setline(182);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("stoplineno", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_until$21(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyString.fromInterned("Stop when the line with the line no greater than the current one is\n        reached or when returning from current frame");
      var1.setline(190);
      var1.getlocal(0).__getattr__("_set_stopinfo").__call__(var2, var1.getlocal(1), var1.getlocal(1), var1.getlocal(1).__getattr__("f_lineno")._add(Py.newInteger(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_step$22(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyString.fromInterned("Stop after one line of code.");
      var1.setline(198);
      if (var1.getlocal(0).__getattr__("frame_returning").__nonzero__()) {
         var1.setline(199);
         PyObject var3 = var1.getlocal(0).__getattr__("frame_returning").__getattr__("f_back");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(200);
         PyObject var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("f_trace").__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(201);
            var3 = var1.getlocal(0).__getattr__("trace_dispatch");
            var1.getlocal(1).__setattr__("f_trace", var3);
            var3 = null;
         }
      }

      var1.setline(202);
      var1.getlocal(0).__getattr__("_set_stopinfo").__call__(var2, var1.getglobal("None"), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_next$23(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      PyString.fromInterned("Stop on the next line in or below the given frame.");
      var1.setline(206);
      var1.getlocal(0).__getattr__("_set_stopinfo").__call__(var2, var1.getlocal(1), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_return$24(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyString.fromInterned("Stop when returning from the given frame.");
      var1.setline(210);
      var1.getlocal(0).__getattr__("_set_stopinfo").__call__(var2, var1.getlocal(1).__getattr__("f_back"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_trace$25(PyFrame var1, ThreadState var2) {
      var1.setline(216);
      PyString.fromInterned("Start debugging from `frame`.\n\n        If frame is not specified, debugging starts from caller's frame.\n        ");
      var1.setline(217);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(218);
         var3 = var1.getglobal("sys").__getattr__("_getframe").__call__(var2).__getattr__("f_back");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(219);
      var1.getlocal(0).__getattr__("reset").__call__(var2);

      while(true) {
         var1.setline(220);
         if (!var1.getlocal(1).__nonzero__()) {
            var1.setline(224);
            var1.getlocal(0).__getattr__("set_step").__call__(var2);
            var1.setline(225);
            var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getlocal(0).__getattr__("trace_dispatch"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(221);
         var3 = var1.getlocal(0).__getattr__("trace_dispatch");
         var1.getlocal(1).__setattr__("f_trace", var3);
         var3 = null;
         var1.setline(222);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("botframe", var3);
         var3 = null;
         var1.setline(223);
         var3 = var1.getlocal(1).__getattr__("f_back");
         var1.setlocal(1, var3);
         var3 = null;
      }
   }

   public PyObject set_continue$26(PyFrame var1, ThreadState var2) {
      var1.setline(229);
      var1.getlocal(0).__getattr__("_set_stopinfo").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("botframe"), (PyObject)var1.getglobal("None"), (PyObject)Py.newInteger(-1));
      var1.setline(230);
      if (var1.getlocal(0).__getattr__("breaks").__not__().__nonzero__()) {
         var1.setline(232);
         var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getglobal("None"));
         var1.setline(233);
         PyObject var3 = var1.getglobal("sys").__getattr__("_getframe").__call__(var2).__getattr__("f_back");
         var1.setlocal(1, var3);
         var3 = null;

         while(true) {
            var1.setline(234);
            PyObject var10000 = var1.getlocal(1);
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(1);
               var10000 = var3._isnot(var1.getlocal(0).__getattr__("botframe"));
               var3 = null;
            }

            if (!var10000.__nonzero__()) {
               break;
            }

            var1.setline(235);
            var1.getlocal(1).__delattr__("f_trace");
            var1.setline(236);
            var3 = var1.getlocal(1).__getattr__("f_back");
            var1.setlocal(1, var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_quit$27(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyObject var3 = var1.getlocal(0).__getattr__("botframe");
      var1.getlocal(0).__setattr__("stopframe", var3);
      var3 = null;
      var1.setline(240);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("returnframe", var3);
      var3 = null;
      var1.setline(241);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"quitting", var4);
      var3 = null;
      var1.setline(242);
      var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_break$28(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyObject var3 = var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(254);
      var3 = imp.importOne("linecache", var1, -1);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(255);
      var3 = var1.getlocal(6).__getattr__("getline").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(256);
      if (var1.getlocal(7).__not__().__nonzero__()) {
         var1.setline(257);
         var3 = PyString.fromInterned("Line %s:%d does not exist")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(259);
         PyObject var4 = var1.getlocal(1);
         PyObject var10000 = var4._in(var1.getlocal(0).__getattr__("breaks"));
         var4 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(260);
            PyList var5 = new PyList(Py.EmptyObjects);
            var1.getlocal(0).__getattr__("breaks").__setitem__((PyObject)var1.getlocal(1), var5);
            var4 = null;
         }

         var1.setline(261);
         var4 = var1.getlocal(0).__getattr__("breaks").__getitem__(var1.getlocal(1));
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(262);
         var4 = var1.getlocal(2);
         var10000 = var4._in(var1.getlocal(8));
         var4 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(263);
            var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(2));
         }

         var1.setline(264);
         var10000 = var1.getglobal("Breakpoint");
         PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
         var4 = var10000.__call__(var2, var6);
         var1.setlocal(9, var4);
         var4 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _prune_breaks$29(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      PyObject var10000 = var3._notin(var1.getglobal("Breakpoint").__getattr__("bplist"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(268);
         var1.getlocal(0).__getattr__("breaks").__getitem__(var1.getlocal(1)).__getattr__("remove").__call__(var2, var1.getlocal(2));
      }

      var1.setline(269);
      if (var1.getlocal(0).__getattr__("breaks").__getitem__(var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(270);
         var1.getlocal(0).__getattr__("breaks").__delitem__(var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clear_break$30(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      PyObject var3 = var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(274);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("breaks"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(275);
         var3 = PyString.fromInterned("There are no breakpoints in %s")._mod(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(276);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._notin(var1.getlocal(0).__getattr__("breaks").__getitem__(var1.getlocal(1)));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(277);
            var3 = PyString.fromInterned("There is no breakpoint at %s:%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(281);
            var4 = var1.getglobal("Breakpoint").__getattr__("bplist").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})).__getslice__((PyObject)null, (PyObject)null, (PyObject)null).__iter__();

            while(true) {
               var1.setline(281);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(283);
                  var1.getlocal(0).__getattr__("_prune_breaks").__call__(var2, var1.getlocal(1), var1.getlocal(2));
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(3, var5);
               var1.setline(282);
               var1.getlocal(3).__getattr__("deleteMe").__call__(var2);
            }
         }
      }
   }

   public PyObject clear_bpbynumber$31(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      try {
         var1.setline(287);
         var3 = var1.getglobal("int").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var5) {
         Py.setException(var5, var1);
         var1.setline(289);
         var4 = PyString.fromInterned("Non-numeric breakpoint number (%s)")._mod(var1.getlocal(1));
         var1.f_lasti = -1;
         return var4;
      }

      try {
         var1.setline(291);
         var3 = var1.getglobal("Breakpoint").__getattr__("bpbynumber").__getitem__(var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (var7.match(var1.getglobal("IndexError"))) {
            var1.setline(293);
            var4 = PyString.fromInterned("Breakpoint number (%d) out of range")._mod(var1.getlocal(2));
            var1.f_lasti = -1;
            return var4;
         }

         throw var7;
      }

      var1.setline(294);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(295);
         var4 = PyString.fromInterned("Breakpoint (%d) already deleted")._mod(var1.getlocal(2));
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(296);
         var1.getlocal(3).__getattr__("deleteMe").__call__(var2);
         var1.setline(297);
         var1.getlocal(0).__getattr__("_prune_breaks").__call__(var2, var1.getlocal(3).__getattr__("file"), var1.getlocal(3).__getattr__("line"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject clear_all_file_breaks$32(PyFrame var1, ThreadState var2) {
      var1.setline(300);
      PyObject var3 = var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(301);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("breaks"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(302);
         var3 = PyString.fromInterned("There are no breakpoints in %s")._mod(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(303);
         PyObject var4 = var1.getlocal(0).__getattr__("breaks").__getitem__(var1.getlocal(1)).__iter__();

         while(true) {
            var1.setline(303);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(307);
               var1.getlocal(0).__getattr__("breaks").__delitem__(var1.getlocal(1));
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var5);
            var1.setline(304);
            PyObject var6 = var1.getglobal("Breakpoint").__getattr__("bplist").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(305);
            var6 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(305);
               PyObject var7 = var6.__iternext__();
               if (var7 == null) {
                  break;
               }

               var1.setlocal(4, var7);
               var1.setline(306);
               var1.getlocal(4).__getattr__("deleteMe").__call__(var2);
            }
         }
      }
   }

   public PyObject clear_all_breaks$33(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      if (var1.getlocal(0).__getattr__("breaks").__not__().__nonzero__()) {
         var1.setline(311);
         PyString var3 = PyString.fromInterned("There are no breakpoints");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(312);
         PyObject var4 = var1.getglobal("Breakpoint").__getattr__("bpbynumber").__iter__();

         while(true) {
            var1.setline(312);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(315);
               PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
               var1.getlocal(0).__setattr__((String)"breaks", var6);
               var4 = null;
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(1, var5);
            var1.setline(313);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(314);
               var1.getlocal(1).__getattr__("deleteMe").__call__(var2);
            }
         }
      }
   }

   public PyObject get_break$34(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      PyObject var3 = var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(319);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("breaks"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._in(var1.getlocal(0).__getattr__("breaks").__getitem__(var1.getlocal(1)));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_breaks$35(PyFrame var1, ThreadState var2) {
      var1.setline(323);
      PyObject var3 = var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(324);
      var3 = var1.getlocal(1);
      Object var10000 = var3._in(var1.getlocal(0).__getattr__("breaks"));
      var3 = null;
      if (((PyObject)var10000).__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._in(var1.getlocal(0).__getattr__("breaks").__getitem__(var1.getlocal(1)));
         var3 = null;
         if (((PyObject)var10000).__nonzero__()) {
            var10000 = var1.getglobal("Breakpoint").__getattr__("bplist").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)}));
         }
      }

      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var4 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var4;
   }

   public PyObject get_file_breaks$36(PyFrame var1, ThreadState var2) {
      var1.setline(329);
      PyObject var3 = var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(330);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(0).__getattr__("breaks"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(331);
         var3 = var1.getlocal(0).__getattr__("breaks").__getitem__(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(333);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject get_all_breaks$37(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      PyObject var3 = var1.getlocal(0).__getattr__("breaks");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_stack$38(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(343);
      PyObject var10000 = var1.getlocal(2);
      PyObject var4;
      if (var10000.__nonzero__()) {
         var4 = var1.getlocal(2).__getattr__("tb_frame");
         var10000 = var4._is(var1.getlocal(1));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(344);
         var4 = var1.getlocal(2).__getattr__("tb_next");
         var1.setlocal(2, var4);
         var3 = null;
      }

      while(true) {
         var1.setline(345);
         var4 = var1.getlocal(1);
         var10000 = var4._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            break;
         }

         var1.setline(346);
         var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(1).__getattr__("f_lineno")})));
         var1.setline(347);
         var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getlocal(0).__getattr__("botframe"));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(349);
         var4 = var1.getlocal(1).__getattr__("f_back");
         var1.setlocal(1, var4);
         var3 = null;
      }

      var1.setline(350);
      var1.getlocal(3).__getattr__("reverse").__call__(var2);
      var1.setline(351);
      var4 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3))._sub(Py.newInteger(1)));
      var1.setlocal(4, var4);
      var3 = null;

      while(true) {
         var1.setline(352);
         var4 = var1.getlocal(2);
         var10000 = var4._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(355);
            var4 = var1.getlocal(1);
            var10000 = var4._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(356);
               var4 = var1.getglobal("max").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3))._sub(Py.newInteger(1)));
               var1.setlocal(4, var4);
               var3 = null;
            }

            var1.setline(357);
            PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)});
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(353);
         var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("tb_frame"), var1.getlocal(2).__getattr__("tb_lineno")})));
         var1.setline(354);
         var4 = var1.getlocal(2).__getattr__("tb_next");
         var1.setlocal(2, var4);
         var3 = null;
      }
   }

   public PyObject format_stack_entry$39(PyFrame var1, ThreadState var2) {
      var1.setline(362);
      PyObject var3 = imp.importOne("linecache", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var3 = imp.importOne("repr", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(363);
      var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(364);
      var3 = var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(5).__getattr__("f_code").__getattr__("co_filename"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(365);
      var3 = PyString.fromInterned("%s(%r)")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(6)}));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(366);
      if (var1.getlocal(5).__getattr__("f_code").__getattr__("co_name").__nonzero__()) {
         var1.setline(367);
         var3 = var1.getlocal(8)._add(var1.getlocal(5).__getattr__("f_code").__getattr__("co_name"));
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(369);
         var3 = var1.getlocal(8)._add(PyString.fromInterned("<lambda>"));
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(370);
      PyString var6 = PyString.fromInterned("__args__");
      PyObject var10000 = var6._in(var1.getlocal(5).__getattr__("f_locals"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(371);
         var3 = var1.getlocal(5).__getattr__("f_locals").__getitem__(PyString.fromInterned("__args__"));
         var1.setlocal(9, var3);
         var3 = null;
      } else {
         var1.setline(373);
         var3 = var1.getglobal("None");
         var1.setlocal(9, var3);
         var3 = null;
      }

      var1.setline(374);
      if (var1.getlocal(9).__nonzero__()) {
         var1.setline(375);
         var3 = var1.getlocal(8)._add(var1.getlocal(4).__getattr__("repr").__call__(var2, var1.getlocal(9)));
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(377);
         var3 = var1.getlocal(8)._add(PyString.fromInterned("()"));
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(378);
      var6 = PyString.fromInterned("__return__");
      var10000 = var6._in(var1.getlocal(5).__getattr__("f_locals"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(379);
         var3 = var1.getlocal(5).__getattr__("f_locals").__getitem__(PyString.fromInterned("__return__"));
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(380);
         var3 = var1.getlocal(8)._add(PyString.fromInterned("->"));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(381);
         var3 = var1.getlocal(8)._add(var1.getlocal(4).__getattr__("repr").__call__(var2, var1.getlocal(10)));
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(382);
      var3 = var1.getlocal(3).__getattr__("getline").__call__(var2, var1.getlocal(7), var1.getlocal(6), var1.getlocal(5).__getattr__("f_globals"));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(383);
      if (var1.getlocal(11).__nonzero__()) {
         var1.setline(383);
         var3 = var1.getlocal(8)._add(var1.getlocal(2))._add(var1.getlocal(11).__getattr__("strip").__call__(var2));
         var1.setlocal(8, var3);
         var3 = null;
      }

      var1.setline(384);
      var3 = var1.getlocal(8);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject run$40(PyFrame var1, ThreadState var2) {
      var1.setline(390);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getname("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(391);
         var3 = imp.importOne("__main__", var1, -1);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(392);
         var3 = var1.getlocal(4).__getattr__("__dict__");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(393);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getname("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(394);
         var3 = var1.getlocal(2);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(395);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.setline(396);
      var1.getname("sys").__getattr__("settrace").__call__(var2, var1.getlocal(0).__getattr__("trace_dispatch"));
      var1.setline(397);
      if (var1.getname("isinstance").__call__(var2, var1.getlocal(1), var1.getname("types").__getattr__("CodeType")).__not__().__nonzero__()) {
         var1.setline(398);
         var3 = var1.getlocal(1)._add(PyString.fromInterned("\n"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var3 = null;

      PyInteger var4;
      try {
         try {
            var1.setline(400);
            Py.exec(var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (!var7.match(var1.getname("BdbQuit"))) {
               throw var7;
            }

            var1.setline(402);
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(404);
         var4 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"quitting", var4);
         var4 = null;
         var1.setline(405);
         var1.getname("sys").__getattr__("settrace").__call__(var2, var1.getname("None"));
         throw (Throwable)var6;
      }

      var1.setline(404);
      var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"quitting", var4);
      var4 = null;
      var1.setline(405);
      var1.getname("sys").__getattr__("settrace").__call__(var2, var1.getname("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject runeval$41(PyFrame param1, ThreadState param2) {
      // $FF: Couldn't be decompiled
   }

   public PyObject runctx$42(PyFrame var1, ThreadState var2) {
      var1.setline(427);
      var1.getlocal(0).__getattr__("run").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject runcall$43(PyFrame var1, ThreadState var2) {
      var1.setline(432);
      var1.getlocal(0).__getattr__("reset").__call__(var2);
      var1.setline(433);
      var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getlocal(0).__getattr__("trace_dispatch"));
      var1.setline(434);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(4, var3);
      var3 = null;
      var3 = null;

      PyInteger var4;
      try {
         try {
            var1.setline(436);
            PyObject var10000 = var1.getlocal(1);
            PyObject[] var9 = Py.EmptyObjects;
            String[] var5 = new String[0];
            var10000 = var10000._callextra(var9, var5, var1.getlocal(2), var1.getlocal(3));
            var4 = null;
            PyObject var10 = var10000;
            var1.setlocal(4, var10);
            var4 = null;
         } catch (Throwable var6) {
            PyException var8 = Py.setException(var6, var1);
            if (!var8.match(var1.getglobal("BdbQuit"))) {
               throw var8;
            }

            var1.setline(438);
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(440);
         var4 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"quitting", var4);
         var4 = null;
         var1.setline(441);
         var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getglobal("None"));
         throw (Throwable)var7;
      }

      var1.setline(440);
      var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"quitting", var4);
      var4 = null;
      var1.setline(441);
      var1.getglobal("sys").__getattr__("settrace").__call__(var2, var1.getglobal("None"));
      var1.setline(442);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject set_trace$44(PyFrame var1, ThreadState var2) {
      var1.setline(446);
      var1.getglobal("Bdb").__call__(var2).__getattr__("set_trace").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Breakpoint$45(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Breakpoint class\n\n    Implements temporary breakpoints, ignore counts, disabling and\n    (re)-enabling, and conditionals.\n\n    Breakpoints are indexed by number through bpbynumber and by\n    the file,line tuple using bplist.  The former points to a\n    single instance of class Breakpoint.  The latter points to a\n    list of such instances since there may be more than one\n    breakpoint per line.\n\n    "));
      var1.setline(462);
      PyString.fromInterned("Breakpoint class\n\n    Implements temporary breakpoints, ignore counts, disabling and\n    (re)-enabling, and conditionals.\n\n    Breakpoints are indexed by number through bpbynumber and by\n    the file,line tuple using bplist.  The former points to a\n    single instance of class Breakpoint.  The latter points to a\n    list of such instances since there may be more than one\n    breakpoint per line.\n\n    ");
      var1.setline(467);
      PyInteger var3 = Py.newInteger(1);
      var1.setlocal("next", var3);
      var3 = null;
      var1.setline(468);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("bplist", var4);
      var3 = null;
      var1.setline(469);
      PyList var5 = new PyList(new PyObject[]{var1.getname("None")});
      var1.setlocal("bpbynumber", var5);
      var3 = null;
      var1.setline(473);
      PyObject[] var6 = new PyObject[]{Py.newInteger(0), var1.getname("None"), var1.getname("None")};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$46, (PyObject)null);
      var1.setlocal("__init__", var7);
      var3 = null;
      var1.setline(494);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, deleteMe$47, (PyObject)null);
      var1.setlocal("deleteMe", var7);
      var3 = null;
      var1.setline(502);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, enable$48, (PyObject)null);
      var1.setlocal("enable", var7);
      var3 = null;
      var1.setline(505);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, disable$49, (PyObject)null);
      var1.setlocal("disable", var7);
      var3 = null;
      var1.setline(508);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, bpprint$50, (PyObject)null);
      var1.setlocal("bpprint", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$46(PyFrame var1, ThreadState var2) {
      var1.setline(474);
      PyObject var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("funcname", var3);
      var3 = null;
      var1.setline(476);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("func_first_executable_line", var3);
      var3 = null;
      var1.setline(477);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("file", var3);
      var3 = null;
      var1.setline(478);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("line", var3);
      var3 = null;
      var1.setline(479);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("temporary", var3);
      var3 = null;
      var1.setline(480);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("cond", var3);
      var3 = null;
      var1.setline(481);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"enabled", var4);
      var3 = null;
      var1.setline(482);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"ignore", var4);
      var3 = null;
      var1.setline(483);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"hits", var4);
      var3 = null;
      var1.setline(484);
      var3 = var1.getglobal("Breakpoint").__getattr__("next");
      var1.getlocal(0).__setattr__("number", var3);
      var3 = null;
      var1.setline(485);
      var3 = var1.getglobal("Breakpoint").__getattr__("next")._add(Py.newInteger(1));
      var1.getglobal("Breakpoint").__setattr__("next", var3);
      var3 = null;
      var1.setline(487);
      var1.getlocal(0).__getattr__("bpbynumber").__getattr__("append").__call__(var2, var1.getlocal(0));
      var1.setline(488);
      PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      PyObject var10000 = var5._in(var1.getlocal(0).__getattr__("bplist"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(489);
         var1.getlocal(0).__getattr__("bplist").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})).__getattr__("append").__call__(var2, var1.getlocal(0));
      } else {
         var1.setline(491);
         PyList var6 = new PyList(new PyObject[]{var1.getlocal(0)});
         var1.getlocal(0).__getattr__("bplist").__setitem__((PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})), var6);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject deleteMe$47(PyFrame var1, ThreadState var2) {
      var1.setline(495);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("file"), var1.getlocal(0).__getattr__("line")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(496);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__getattr__("bpbynumber").__setitem__(var1.getlocal(0).__getattr__("number"), var4);
      var3 = null;
      var1.setline(497);
      var1.getlocal(0).__getattr__("bplist").__getitem__(var1.getlocal(1)).__getattr__("remove").__call__(var2, var1.getlocal(0));
      var1.setline(498);
      if (var1.getlocal(0).__getattr__("bplist").__getitem__(var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(500);
         var1.getlocal(0).__getattr__("bplist").__delitem__(var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject enable$48(PyFrame var1, ThreadState var2) {
      var1.setline(503);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"enabled", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject disable$49(PyFrame var1, ThreadState var2) {
      var1.setline(506);
      PyInteger var3 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"enabled", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject bpprint$50(PyFrame var1, ThreadState var2) {
      var1.setline(509);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(510);
         var3 = var1.getglobal("sys").__getattr__("stdout");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(511);
      PyString var4;
      if (var1.getlocal(0).__getattr__("temporary").__nonzero__()) {
         var1.setline(512);
         var4 = PyString.fromInterned("del  ");
         var1.setlocal(2, var4);
         var3 = null;
      } else {
         var1.setline(514);
         var4 = PyString.fromInterned("keep ");
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(515);
      if (var1.getlocal(0).__getattr__("enabled").__nonzero__()) {
         var1.setline(516);
         var3 = var1.getlocal(2)._add(PyString.fromInterned("yes  "));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(518);
         var3 = var1.getlocal(2)._add(PyString.fromInterned("no   "));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(519);
      var3 = var1.getlocal(1);
      Py.println(var3, PyString.fromInterned("%-4dbreakpoint   %s at %s:%d")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("number"), var1.getlocal(2), var1.getlocal(0).__getattr__("file"), var1.getlocal(0).__getattr__("line")})));
      var1.setline(521);
      if (var1.getlocal(0).__getattr__("cond").__nonzero__()) {
         var1.setline(522);
         var3 = var1.getlocal(1);
         Py.println(var3, PyString.fromInterned("\tstop only if %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("cond")})));
      }

      var1.setline(523);
      if (var1.getlocal(0).__getattr__("ignore").__nonzero__()) {
         var1.setline(524);
         var3 = var1.getlocal(1);
         Py.println(var3, PyString.fromInterned("\tignore next %d hits")._mod(var1.getlocal(0).__getattr__("ignore")));
      }

      var1.setline(525);
      if (var1.getlocal(0).__getattr__("hits").__nonzero__()) {
         var1.setline(526);
         var3 = var1.getlocal(0).__getattr__("hits");
         var10000 = var3._gt(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(526);
            var4 = PyString.fromInterned("s");
            var1.setlocal(3, var4);
            var3 = null;
         } else {
            var1.setline(527);
            var4 = PyString.fromInterned("");
            var1.setlocal(3, var4);
            var3 = null;
         }

         var1.setline(528);
         var3 = var1.getlocal(1);
         Py.println(var3, PyString.fromInterned("\tbreakpoint already hit %d time%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("hits"), var1.getlocal(3)})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject checkfuncname$51(PyFrame var1, ThreadState var2) {
      var1.setline(534);
      PyString.fromInterned("Check whether we should break here because of `b.funcname`.");
      var1.setline(535);
      PyObject var10000;
      PyObject var3;
      if (var1.getlocal(0).__getattr__("funcname").__not__().__nonzero__()) {
         var1.setline(537);
         var3 = var1.getlocal(0).__getattr__("line");
         var10000 = var3._ne(var1.getlocal(1).__getattr__("f_lineno"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(540);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(541);
            var3 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(545);
         PyObject var4 = var1.getlocal(1).__getattr__("f_code").__getattr__("co_name");
         var10000 = var4._ne(var1.getlocal(0).__getattr__("funcname"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(547);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(550);
            if (var1.getlocal(0).__getattr__("func_first_executable_line").__not__().__nonzero__()) {
               var1.setline(552);
               var4 = var1.getlocal(1).__getattr__("f_lineno");
               var1.getlocal(0).__setattr__("func_first_executable_line", var4);
               var4 = null;
            }

            var1.setline(554);
            var4 = var1.getlocal(0).__getattr__("func_first_executable_line");
            var10000 = var4._ne(var1.getlocal(1).__getattr__("f_lineno"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(556);
               var3 = var1.getglobal("False");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(557);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject effective$52(PyFrame var1, ThreadState var2) {
      var1.setline(568);
      PyString.fromInterned("Determine which breakpoint for this file:line is to be acted upon.\n\n    Called only if we know there is a bpt at this\n    location.  Returns breakpoint that was triggered and a flag\n    that indicates if it is ok to delete a temporary bp.\n\n    ");
      var1.setline(569);
      PyObject var3 = var1.getglobal("Breakpoint").__getattr__("bplist").__getitem__(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)}));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(570);
      var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3))).__iter__();

      while(true) {
         var1.setline(570);
         PyObject var4 = var3.__iternext__();
         PyTuple var8;
         if (var4 == null) {
            var1.setline(610);
            var8 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None")});
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(4, var4);
         var1.setline(571);
         PyObject var5 = var1.getlocal(3).__getitem__(var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(572);
         var5 = var1.getlocal(5).__getattr__("enabled");
         PyObject var10000 = var5._eq(Py.newInteger(0));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(574);
            if (!var1.getglobal("checkfuncname").__call__(var2, var1.getlocal(5), var1.getlocal(2)).__not__().__nonzero__()) {
               var1.setline(577);
               var5 = var1.getlocal(5).__getattr__("hits")._add(Py.newInteger(1));
               var1.getlocal(5).__setattr__("hits", var5);
               var5 = null;
               var1.setline(578);
               if (var1.getlocal(5).__getattr__("cond").__not__().__nonzero__()) {
                  var1.setline(581);
                  var5 = var1.getlocal(5).__getattr__("ignore");
                  var10000 = var5._gt(Py.newInteger(0));
                  var5 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(587);
                     var8 = new PyTuple(new PyObject[]{var1.getlocal(5), Py.newInteger(1)});
                     var1.f_lasti = -1;
                     return var8;
                  }

                  var1.setline(582);
                  var5 = var1.getlocal(5).__getattr__("ignore")._sub(Py.newInteger(1));
                  var1.getlocal(5).__setattr__("ignore", var5);
                  var5 = null;
               } else {
                  try {
                     var1.setline(593);
                     PyObject var6 = var1.getglobal("eval").__call__(var2, var1.getlocal(5).__getattr__("cond"), var1.getlocal(2).__getattr__("f_globals"), var1.getlocal(2).__getattr__("f_locals"));
                     var1.setlocal(6, var6);
                     var6 = null;
                     var1.setline(595);
                     if (var1.getlocal(6).__nonzero__()) {
                        var1.setline(596);
                        var6 = var1.getlocal(5).__getattr__("ignore");
                        var10000 = var6._gt(Py.newInteger(0));
                        var6 = null;
                        if (!var10000.__nonzero__()) {
                           var1.setline(600);
                           var8 = new PyTuple(new PyObject[]{var1.getlocal(5), Py.newInteger(1)});
                           var1.f_lasti = -1;
                           return var8;
                        }

                        var1.setline(597);
                        var6 = var1.getlocal(5).__getattr__("ignore")._sub(Py.newInteger(1));
                        var1.getlocal(5).__setattr__("ignore", var6);
                        var6 = null;
                     }
                  } catch (Throwable var7) {
                     Py.setException(var7, var1);
                     var1.setline(609);
                     var8 = new PyTuple(new PyObject[]{var1.getlocal(5), Py.newInteger(0)});
                     var1.f_lasti = -1;
                     return var8;
                  }
               }
            }
         }
      }
   }

   public PyObject Tdb$53(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(615);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, user_call$54, (PyObject)null);
      var1.setlocal("user_call", var4);
      var3 = null;
      var1.setline(619);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, user_line$55, (PyObject)null);
      var1.setlocal("user_line", var4);
      var3 = null;
      var1.setline(626);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, user_return$56, (PyObject)null);
      var1.setlocal("user_return", var4);
      var3 = null;
      var1.setline(628);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, user_exception$57, (PyObject)null);
      var1.setlocal("user_exception", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject user_call$54(PyFrame var1, ThreadState var2) {
      var1.setline(616);
      PyObject var3 = var1.getlocal(1).__getattr__("f_code").__getattr__("co_name");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(617);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(617);
         PyString var4 = PyString.fromInterned("???");
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(618);
      Py.printComma(PyString.fromInterned("+++ call"));
      Py.printComma(var1.getlocal(3));
      Py.println(var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject user_line$55(PyFrame var1, ThreadState var2) {
      var1.setline(620);
      PyObject var3 = imp.importOne("linecache", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(621);
      var3 = var1.getlocal(1).__getattr__("f_code").__getattr__("co_name");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(622);
      if (var1.getlocal(3).__not__().__nonzero__()) {
         var1.setline(622);
         PyString var4 = PyString.fromInterned("???");
         var1.setlocal(3, var4);
         var3 = null;
      }

      var1.setline(623);
      var3 = var1.getlocal(0).__getattr__("canonic").__call__(var2, var1.getlocal(1).__getattr__("f_code").__getattr__("co_filename"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(624);
      var3 = var1.getlocal(2).__getattr__("getline").__call__(var2, var1.getlocal(4), var1.getlocal(1).__getattr__("f_lineno"), var1.getlocal(1).__getattr__("f_globals"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(625);
      Py.printComma(PyString.fromInterned("+++"));
      Py.printComma(var1.getlocal(4));
      Py.printComma(var1.getlocal(1).__getattr__("f_lineno"));
      Py.printComma(var1.getlocal(3));
      Py.printComma(PyString.fromInterned(":"));
      Py.println(var1.getlocal(5).__getattr__("strip").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject user_return$56(PyFrame var1, ThreadState var2) {
      var1.setline(627);
      Py.printComma(PyString.fromInterned("+++ return"));
      Py.println(var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject user_exception$57(PyFrame var1, ThreadState var2) {
      var1.setline(629);
      Py.printComma(PyString.fromInterned("+++ exception"));
      Py.println(var1.getlocal(2));
      var1.setline(630);
      var1.getlocal(0).__getattr__("set_continue").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject foo$58(PyFrame var1, ThreadState var2) {
      var1.setline(633);
      Py.printComma(PyString.fromInterned("foo("));
      Py.printComma(var1.getlocal(0));
      Py.println(PyString.fromInterned(")"));
      var1.setline(634);
      PyObject var3 = var1.getglobal("bar").__call__(var2, var1.getlocal(0)._mul(Py.newInteger(10)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(635);
      Py.printComma(PyString.fromInterned("bar returned"));
      Py.println(var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject bar$59(PyFrame var1, ThreadState var2) {
      var1.setline(638);
      Py.printComma(PyString.fromInterned("bar("));
      Py.printComma(var1.getlocal(0));
      Py.println(PyString.fromInterned(")"));
      var1.setline(639);
      PyObject var3 = var1.getlocal(0)._div(Py.newInteger(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test$60(PyFrame var1, ThreadState var2) {
      var1.setline(642);
      PyObject var3 = var1.getglobal("Tdb").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(643);
      var1.getlocal(0).__getattr__("run").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("import bdb; bdb.foo(10)"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public bdb$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BdbQuit$1 = Py.newCode(0, var2, var1, "BdbQuit", 10, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Bdb$2 = Py.newCode(0, var2, var1, "Bdb", 14, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "skip"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 23, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "canonic"};
      canonic$4 = Py.newCode(2, var2, var1, "canonic", 29, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "linecache"};
      reset$5 = Py.newCode(1, var2, var1, "reset", 39, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "event", "arg"};
      trace_dispatch$6 = Py.newCode(4, var2, var1, "trace_dispatch", 45, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame"};
      dispatch_line$7 = Py.newCode(2, var2, var1, "dispatch_line", 65, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "arg"};
      dispatch_call$8 = Py.newCode(3, var2, var1, "dispatch_call", 71, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "arg"};
      dispatch_return$9 = Py.newCode(3, var2, var1, "dispatch_return", 84, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "arg"};
      dispatch_exception$10 = Py.newCode(3, var2, var1, "dispatch_exception", 94, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module_name", "pattern"};
      is_skipped_module$11 = Py.newCode(2, var2, var1, "is_skipped_module", 104, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame"};
      stop_here$12 = Py.newCode(2, var2, var1, "stop_here", 110, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "filename", "lineno", "bp", "flag"};
      break_here$13 = Py.newCode(2, var2, var1, "break_here", 126, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      do_clear$14 = Py.newCode(2, var2, var1, "do_clear", 148, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame"};
      break_anywhere$15 = Py.newCode(2, var2, var1, "break_anywhere", 151, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "argument_list"};
      user_call$16 = Py.newCode(3, var2, var1, "user_call", 157, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame"};
      user_line$17 = Py.newCode(2, var2, var1, "user_line", 162, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "return_value"};
      user_return$18 = Py.newCode(3, var2, var1, "user_return", 166, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "exc_info", "exc_type", "exc_value", "exc_traceback"};
      user_exception$19 = Py.newCode(3, var2, var1, "user_exception", 170, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stopframe", "returnframe", "stoplineno"};
      _set_stopinfo$20 = Py.newCode(4, var2, var1, "_set_stopinfo", 176, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame"};
      set_until$21 = Py.newCode(2, var2, var1, "set_until", 187, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "caller_frame"};
      set_step$22 = Py.newCode(1, var2, var1, "set_step", 192, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame"};
      set_next$23 = Py.newCode(2, var2, var1, "set_next", 204, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame"};
      set_return$24 = Py.newCode(2, var2, var1, "set_return", 208, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame"};
      set_trace$25 = Py.newCode(2, var2, var1, "set_trace", 212, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame"};
      set_continue$26 = Py.newCode(1, var2, var1, "set_continue", 227, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      set_quit$27 = Py.newCode(1, var2, var1, "set_quit", 238, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "lineno", "temporary", "cond", "funcname", "linecache", "line", "list", "bp"};
      set_break$28 = Py.newCode(6, var2, var1, "set_break", 251, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "lineno"};
      _prune_breaks$29 = Py.newCode(3, var2, var1, "_prune_breaks", 266, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "lineno", "bp"};
      clear_break$30 = Py.newCode(3, var2, var1, "clear_break", 272, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg", "number", "bp"};
      clear_bpbynumber$31 = Py.newCode(2, var2, var1, "clear_bpbynumber", 285, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "line", "blist", "bp"};
      clear_all_file_breaks$32 = Py.newCode(2, var2, var1, "clear_all_file_breaks", 299, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bp"};
      clear_all_breaks$33 = Py.newCode(1, var2, var1, "clear_all_breaks", 309, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "lineno"};
      get_break$34 = Py.newCode(3, var2, var1, "get_break", 317, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "lineno"};
      get_breaks$35 = Py.newCode(3, var2, var1, "get_breaks", 322, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename"};
      get_file_breaks$36 = Py.newCode(2, var2, var1, "get_file_breaks", 328, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_all_breaks$37 = Py.newCode(1, var2, var1, "get_all_breaks", 335, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "t", "stack", "i"};
      get_stack$38 = Py.newCode(3, var2, var1, "get_stack", 341, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame_lineno", "lprefix", "linecache", "repr", "frame", "lineno", "filename", "s", "args", "rv", "line"};
      format_stack_entry$39 = Py.newCode(3, var2, var1, "format_stack_entry", 361, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "globals", "locals", "__main__"};
      run$40 = Py.newCode(4, var2, var1, "run", 389, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expr", "globals", "locals", "__main__"};
      runeval$41 = Py.newCode(4, var2, var1, "runeval", 407, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "globals", "locals"};
      runctx$42 = Py.newCode(4, var2, var1, "runctx", 425, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "func", "args", "kwds", "res"};
      runcall$43 = Py.newCode(4, var2, var1, "runcall", 431, true, true, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      set_trace$44 = Py.newCode(0, var2, var1, "set_trace", 445, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Breakpoint$45 = Py.newCode(0, var2, var1, "Breakpoint", 449, false, false, self, 45, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "file", "line", "temporary", "cond", "funcname"};
      __init__$46 = Py.newCode(6, var2, var1, "__init__", 473, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "index"};
      deleteMe$47 = Py.newCode(1, var2, var1, "deleteMe", 494, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      enable$48 = Py.newCode(1, var2, var1, "enable", 502, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      disable$49 = Py.newCode(1, var2, var1, "disable", 505, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "out", "disp", "ss"};
      bpprint$50 = Py.newCode(2, var2, var1, "bpprint", 508, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"b", "frame"};
      checkfuncname$51 = Py.newCode(2, var2, var1, "checkfuncname", 533, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"file", "line", "frame", "possibles", "i", "b", "val"};
      effective$52 = Py.newCode(3, var2, var1, "effective", 561, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Tdb$53 = Py.newCode(0, var2, var1, "Tdb", 614, false, false, self, 53, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "frame", "args", "name"};
      user_call$54 = Py.newCode(3, var2, var1, "user_call", 615, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "linecache", "name", "fn", "line"};
      user_line$55 = Py.newCode(2, var2, var1, "user_line", 619, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "retval"};
      user_return$56 = Py.newCode(3, var2, var1, "user_return", 626, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "exc_stuff"};
      user_exception$57 = Py.newCode(3, var2, var1, "user_exception", 628, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n", "x"};
      foo$58 = Py.newCode(1, var2, var1, "foo", 632, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"a"};
      bar$59 = Py.newCode(1, var2, var1, "bar", 637, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"t"};
      test$60 = Py.newCode(0, var2, var1, "test", 641, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new bdb$py("bdb$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(bdb$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BdbQuit$1(var2, var3);
         case 2:
            return this.Bdb$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this.canonic$4(var2, var3);
         case 5:
            return this.reset$5(var2, var3);
         case 6:
            return this.trace_dispatch$6(var2, var3);
         case 7:
            return this.dispatch_line$7(var2, var3);
         case 8:
            return this.dispatch_call$8(var2, var3);
         case 9:
            return this.dispatch_return$9(var2, var3);
         case 10:
            return this.dispatch_exception$10(var2, var3);
         case 11:
            return this.is_skipped_module$11(var2, var3);
         case 12:
            return this.stop_here$12(var2, var3);
         case 13:
            return this.break_here$13(var2, var3);
         case 14:
            return this.do_clear$14(var2, var3);
         case 15:
            return this.break_anywhere$15(var2, var3);
         case 16:
            return this.user_call$16(var2, var3);
         case 17:
            return this.user_line$17(var2, var3);
         case 18:
            return this.user_return$18(var2, var3);
         case 19:
            return this.user_exception$19(var2, var3);
         case 20:
            return this._set_stopinfo$20(var2, var3);
         case 21:
            return this.set_until$21(var2, var3);
         case 22:
            return this.set_step$22(var2, var3);
         case 23:
            return this.set_next$23(var2, var3);
         case 24:
            return this.set_return$24(var2, var3);
         case 25:
            return this.set_trace$25(var2, var3);
         case 26:
            return this.set_continue$26(var2, var3);
         case 27:
            return this.set_quit$27(var2, var3);
         case 28:
            return this.set_break$28(var2, var3);
         case 29:
            return this._prune_breaks$29(var2, var3);
         case 30:
            return this.clear_break$30(var2, var3);
         case 31:
            return this.clear_bpbynumber$31(var2, var3);
         case 32:
            return this.clear_all_file_breaks$32(var2, var3);
         case 33:
            return this.clear_all_breaks$33(var2, var3);
         case 34:
            return this.get_break$34(var2, var3);
         case 35:
            return this.get_breaks$35(var2, var3);
         case 36:
            return this.get_file_breaks$36(var2, var3);
         case 37:
            return this.get_all_breaks$37(var2, var3);
         case 38:
            return this.get_stack$38(var2, var3);
         case 39:
            return this.format_stack_entry$39(var2, var3);
         case 40:
            return this.run$40(var2, var3);
         case 41:
            return this.runeval$41(var2, var3);
         case 42:
            return this.runctx$42(var2, var3);
         case 43:
            return this.runcall$43(var2, var3);
         case 44:
            return this.set_trace$44(var2, var3);
         case 45:
            return this.Breakpoint$45(var2, var3);
         case 46:
            return this.__init__$46(var2, var3);
         case 47:
            return this.deleteMe$47(var2, var3);
         case 48:
            return this.enable$48(var2, var3);
         case 49:
            return this.disable$49(var2, var3);
         case 50:
            return this.bpprint$50(var2, var3);
         case 51:
            return this.checkfuncname$51(var2, var3);
         case 52:
            return this.effective$52(var2, var3);
         case 53:
            return this.Tdb$53(var2, var3);
         case 54:
            return this.user_call$54(var2, var3);
         case 55:
            return this.user_line$55(var2, var3);
         case 56:
            return this.user_return$56(var2, var3);
         case 57:
            return this.user_exception$57(var2, var3);
         case 58:
            return this.foo$58(var2, var3);
         case 59:
            return this.bar$59(var2, var3);
         case 60:
            return this.test$60(var2, var3);
         default:
            return null;
      }
   }
}
