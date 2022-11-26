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
@Filename("profile.py")
public class profile$py extends PyFunctionTable implements PyRunnable {
   static profile$py self;
   static final PyCode f$0;
   static final PyCode run$1;
   static final PyCode runctx$2;
   static final PyCode help$3;
   static final PyCode _get_time_times$4;
   static final PyCode f$5;
   static final PyCode _get_time_resource$6;
   static final PyCode Profile$7;
   static final PyCode __init__$8;
   static final PyCode get_time_timer$9;
   static final PyCode trace_dispatch$10;
   static final PyCode trace_dispatch_i$11;
   static final PyCode trace_dispatch_mac$12;
   static final PyCode trace_dispatch_l$13;
   static final PyCode trace_dispatch_exception$14;
   static final PyCode trace_dispatch_call$15;
   static final PyCode trace_dispatch_c_call$16;
   static final PyCode trace_dispatch_return$17;
   static final PyCode set_cmd$18;
   static final PyCode fake_code$19;
   static final PyCode __init__$20;
   static final PyCode __repr__$21;
   static final PyCode fake_frame$22;
   static final PyCode __init__$23;
   static final PyCode simulate_call$24;
   static final PyCode simulate_cmd_complete$25;
   static final PyCode print_stats$26;
   static final PyCode dump_stats$27;
   static final PyCode create_stats$28;
   static final PyCode snapshot_stats$29;
   static final PyCode run$30;
   static final PyCode runctx$31;
   static final PyCode runcall$32;
   static final PyCode calibrate$33;
   static final PyCode _calibrate_inner$34;
   static final PyCode f1$35;
   static final PyCode f$36;
   static final PyCode Stats$37;
   static final PyCode main$38;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Class for profiling Python code."));
      var1.setline(9);
      PyString.fromInterned("Class for profiling Python code.");
      var1.setline(27);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(28);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(29);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(30);
      var3 = imp.importOne("marshal", var1, -1);
      var1.setlocal("marshal", var3);
      var3 = null;
      var1.setline(31);
      String[] var6 = new String[]{"OptionParser"};
      PyObject[] var7 = imp.importFrom("optparse", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("OptionParser", var4);
      var4 = null;
      var1.setline(33);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("run"), PyString.fromInterned("runctx"), PyString.fromInterned("help"), PyString.fromInterned("Profile")});
      var1.setlocal("__all__", var8);
      var3 = null;
      var1.setline(48);
      var7 = new PyObject[]{var1.getname("None"), Py.newInteger(-1)};
      PyFunction var9 = new PyFunction(var1.f_globals, var7, run$1, PyString.fromInterned("Run statement under profiler optionally saving results in filename\n\n    This function takes a single argument that can be passed to the\n    \"exec\" statement, and an optional file name.  In all cases this\n    routine attempts to \"exec\" its first argument and gather profiling\n    statistics from the execution. If no file name is present, then this\n    function automatically prints a simple profiling report, sorted by the\n    standard name string (file/line/function-name) that is presented in\n    each line.\n    "));
      var1.setlocal("run", var9);
      var3 = null;
      var1.setline(69);
      var7 = new PyObject[]{var1.getname("None"), Py.newInteger(-1)};
      var9 = new PyFunction(var1.f_globals, var7, runctx$2, PyString.fromInterned("Run statement under profiler, supplying your own globals and locals,\n    optionally saving results in filename.\n\n    statement and filename have the same semantics as profile.run\n    "));
      var1.setlocal("runctx", var9);
      var3 = null;
      var1.setline(87);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, help$3, (PyObject)null);
      var1.setlocal("help", var9);
      var3 = null;
      var1.setline(91);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("os"), (PyObject)PyString.fromInterned("times")).__nonzero__()) {
         var1.setline(92);
         var7 = new PyObject[]{var1.getname("os").__getattr__("times")};
         var9 = new PyFunction(var1.f_globals, var7, _get_time_times$4, (PyObject)null);
         var1.setlocal("_get_time_times", var9);
         var3 = null;
      }

      var1.setline(100);
      PyInteger var10 = Py.newInteger(0);
      var1.setlocal("_has_res", var10);
      var3 = null;

      try {
         var1.setline(102);
         var3 = imp.importOne("resource", var1, -1);
         var1.setlocal("resource", var3);
         var3 = null;
         var1.setline(103);
         var1.setline(103);
         var7 = Py.EmptyObjects;
         var9 = new PyFunction(var1.f_globals, var7, f$5);
         var1.setlocal("resgetrusage", var9);
         var3 = null;
         var1.setline(104);
         var7 = new PyObject[]{var1.getname("resgetrusage")};
         var9 = new PyFunction(var1.f_globals, var7, _get_time_resource$6, (PyObject)null);
         var1.setlocal("_get_time_resource", var9);
         var3 = null;
         var1.setline(107);
         var10 = Py.newInteger(1);
         var1.setlocal("_has_res", var10);
         var3 = null;
      } catch (Throwable var5) {
         PyException var11 = Py.setException(var5, var1);
         if (!var11.match(var1.getname("ImportError"))) {
            throw var11;
         }

         var1.setline(109);
      }

      var1.setline(111);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("Profile", var7, Profile$7);
      var1.setlocal("Profile", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(573);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, Stats$37, (PyObject)null);
      var1.setlocal("Stats", var9);
      var3 = null;
      var1.setline(576);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, main$38, (PyObject)null);
      var1.setlocal("main", var9);
      var3 = null;
      var1.setline(609);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(610);
         var1.getname("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$1(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyString.fromInterned("Run statement under profiler optionally saving results in filename\n\n    This function takes a single argument that can be passed to the\n    \"exec\" statement, and an optional file name.  In all cases this\n    routine attempts to \"exec\" its first argument and gather profiling\n    statistics from the execution. If no file name is present, then this\n    function automatically prints a simple profiling report, sorted by the\n    standard name string (file/line/function-name) that is presented in\n    each line.\n    ");
      var1.setline(59);
      PyObject var3 = var1.getglobal("Profile").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;

      try {
         var1.setline(61);
         var3 = var1.getlocal(3).__getattr__("run").__call__(var2, var1.getlocal(0));
         var1.setlocal(3, var3);
         var3 = null;
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(var1.getglobal("SystemExit"))) {
            throw var5;
         }

         var1.setline(63);
      }

      var1.setline(64);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(65);
         var1.getlocal(3).__getattr__("dump_stats").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(67);
         var3 = var1.getlocal(3).__getattr__("print_stats").__call__(var2, var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject runctx$2(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyString.fromInterned("Run statement under profiler, supplying your own globals and locals,\n    optionally saving results in filename.\n\n    statement and filename have the same semantics as profile.run\n    ");
      var1.setline(75);
      PyObject var3 = var1.getglobal("Profile").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;

      try {
         var1.setline(77);
         var3 = var1.getlocal(5).__getattr__("runctx").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(5, var3);
         var3 = null;
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(var1.getglobal("SystemExit"))) {
            throw var5;
         }

         var1.setline(79);
      }

      var1.setline(81);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(82);
         var1.getlocal(5).__getattr__("dump_stats").__call__(var2, var1.getlocal(3));
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(84);
         var3 = var1.getlocal(5).__getattr__("print_stats").__call__(var2, var1.getlocal(4));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject help$3(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      Py.println(PyString.fromInterned("Documentation for the profile module can be found "));
      var1.setline(89);
      Py.println(PyString.fromInterned("in the Python Library Reference, section 'The Python Profiler'."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _get_time_times$4(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var3 = var1.getlocal(0).__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(94);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(0))._add(var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$5(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      PyObject var3 = var1.getglobal("resource").__getattr__("getrusage").__call__(var2, var1.getglobal("resource").__getattr__("RUSAGE_SELF"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _get_time_resource$6(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyObject var3 = var1.getlocal(0).__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(106);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(0))._add(var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Profile$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Profiler class.\n\n    self.cur is always a tuple.  Each such tuple corresponds to a stack\n    frame that is currently active (self.cur[-2]).  The following are the\n    definitions of its members.  We use this external \"parallel stack\" to\n    avoid contaminating the program that we are profiling. (old profiler\n    used to write into the frames local dictionary!!) Derived classes\n    can change the definition of some entries, as long as they leave\n    [-2:] intact (frame and previous tuple).  In case an internal error is\n    detected, the -3 element is used as the function name.\n\n    [ 0] = Time that needs to be charged to the parent frame's function.\n           It is used so that a function call will not have to access the\n           timing data for the parent frame.\n    [ 1] = Total time spent in this frame's function, excluding time in\n           subfunctions (this latter is tallied in cur[2]).\n    [ 2] = Total time spent in subfunctions, excluding time executing the\n           frame's function (this latter is tallied in cur[1]).\n    [-3] = Name of the function that corresponds to this frame.\n    [-2] = Actual frame that we correspond to (used to sync exception handling).\n    [-1] = Our parent 6-tuple (corresponds to frame.f_back).\n\n    Timing data for each function is stored as a 5-tuple in the dictionary\n    self.timings[].  The index is always the name stored in self.cur[-3].\n    The following are the definitions of the members:\n\n    [0] = The number of times this function was called, not counting direct\n          or indirect recursion,\n    [1] = Number of times this function appears on the stack, minus one\n    [2] = Total time spent internal to this function\n    [3] = Cumulative time that this function was present on the stack.  In\n          non-recursive functions, this is the total execution time from start\n          to finish of each invocation of a function, including time spent in\n          all subfunctions.\n    [4] = A dictionary indicating for each function name, the number of times\n          it was called by us.\n    "));
      var1.setline(148);
      PyString.fromInterned("Profiler class.\n\n    self.cur is always a tuple.  Each such tuple corresponds to a stack\n    frame that is currently active (self.cur[-2]).  The following are the\n    definitions of its members.  We use this external \"parallel stack\" to\n    avoid contaminating the program that we are profiling. (old profiler\n    used to write into the frames local dictionary!!) Derived classes\n    can change the definition of some entries, as long as they leave\n    [-2:] intact (frame and previous tuple).  In case an internal error is\n    detected, the -3 element is used as the function name.\n\n    [ 0] = Time that needs to be charged to the parent frame's function.\n           It is used so that a function call will not have to access the\n           timing data for the parent frame.\n    [ 1] = Total time spent in this frame's function, excluding time in\n           subfunctions (this latter is tallied in cur[2]).\n    [ 2] = Total time spent in subfunctions, excluding time executing the\n           frame's function (this latter is tallied in cur[1]).\n    [-3] = Name of the function that corresponds to this frame.\n    [-2] = Actual frame that we correspond to (used to sync exception handling).\n    [-1] = Our parent 6-tuple (corresponds to frame.f_back).\n\n    Timing data for each function is stored as a 5-tuple in the dictionary\n    self.timings[].  The index is always the name stored in self.cur[-3].\n    The following are the definitions of the members:\n\n    [0] = The number of times this function was called, not counting direct\n          or indirect recursion,\n    [1] = Number of times this function appears on the stack, minus one\n    [2] = Total time spent internal to this function\n    [3] = Cumulative time that this function was present on the stack.  In\n          non-recursive functions, this is the total execution time from start\n          to finish of each invocation of a function, including time spent in\n          all subfunctions.\n    [4] = A dictionary indicating for each function name, the number of times\n          it was called by us.\n    ");
      var1.setline(150);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("bias", var3);
      var3 = null;
      var1.setline(152);
      PyObject[] var5 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var5, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var6);
      var3 = null;
      var1.setline(203);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, trace_dispatch$10, (PyObject)null);
      var1.setlocal("trace_dispatch", var6);
      var3 = null;
      var1.setline(221);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, trace_dispatch_i$11, (PyObject)null);
      var1.setlocal("trace_dispatch_i", var6);
      var3 = null;
      var1.setline(236);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, trace_dispatch_mac$12, (PyObject)null);
      var1.setlocal("trace_dispatch_mac", var6);
      var3 = null;
      var1.setline(250);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, trace_dispatch_l$13, (PyObject)null);
      var1.setlocal("trace_dispatch_l", var6);
      var3 = null;
      var1.setline(269);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, trace_dispatch_exception$14, (PyObject)null);
      var1.setlocal("trace_dispatch_exception", var6);
      var3 = null;
      var1.setline(277);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, trace_dispatch_call$15, (PyObject)null);
      var1.setlocal("trace_dispatch_call", var6);
      var3 = null;
      var1.setline(299);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, trace_dispatch_c_call$16, (PyObject)null);
      var1.setlocal("trace_dispatch_c_call", var6);
      var3 = null;
      var1.setline(310);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, trace_dispatch_return$17, (PyObject)null);
      var1.setlocal("trace_dispatch_return", var6);
      var3 = null;
      var1.setline(348);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("call"), var1.getname("trace_dispatch_call"), PyString.fromInterned("exception"), var1.getname("trace_dispatch_exception"), PyString.fromInterned("return"), var1.getname("trace_dispatch_return"), PyString.fromInterned("c_call"), var1.getname("trace_dispatch_c_call"), PyString.fromInterned("c_exception"), var1.getname("trace_dispatch_return"), PyString.fromInterned("c_return"), var1.getname("trace_dispatch_return")});
      var1.setlocal("dispatch", var7);
      var3 = null;
      var1.setline(364);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, set_cmd$18, (PyObject)null);
      var1.setlocal("set_cmd", var6);
      var3 = null;
      var1.setline(369);
      var5 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("fake_code", var5, fake_code$19);
      var1.setlocal("fake_code", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(379);
      var5 = Py.EmptyObjects;
      var4 = Py.makeClass("fake_frame", var5, fake_frame$22);
      var1.setlocal("fake_frame", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(384);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, simulate_call$24, (PyObject)null);
      var1.setlocal("simulate_call", var6);
      var3 = null;
      var1.setline(396);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, simulate_cmd_complete$25, (PyObject)null);
      var1.setlocal("simulate_cmd_complete", var6);
      var3 = null;
      var1.setline(407);
      var5 = new PyObject[]{Py.newInteger(-1)};
      var6 = new PyFunction(var1.f_globals, var5, print_stats$26, (PyObject)null);
      var1.setlocal("print_stats", var6);
      var3 = null;
      var1.setline(412);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, dump_stats$27, (PyObject)null);
      var1.setlocal("dump_stats", var6);
      var3 = null;
      var1.setline(418);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, create_stats$28, (PyObject)null);
      var1.setlocal("create_stats", var6);
      var3 = null;
      var1.setline(422);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, snapshot_stats$29, (PyObject)null);
      var1.setlocal("snapshot_stats", var6);
      var3 = null;
      var1.setline(435);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$30, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(440);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, runctx$31, (PyObject)null);
      var1.setlocal("runctx", var6);
      var3 = null;
      var1.setline(450);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, runcall$32, (PyObject)null);
      var1.setlocal("runcall", var6);
      var3 = null;
      var1.setline(499);
      var5 = new PyObject[]{Py.newInteger(0)};
      var6 = new PyFunction(var1.f_globals, var5, calibrate$33, (PyObject)null);
      var1.setlocal("calibrate", var6);
      var3 = null;
      var1.setline(510);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _calibrate_inner$34, (PyObject)null);
      var1.setlocal("_calibrate_inner", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"timings", var3);
      var3 = null;
      var1.setline(154);
      PyObject var6 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("cur", var6);
      var3 = null;
      var1.setline(155);
      PyString var7 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"cmd", var7);
      var3 = null;
      var1.setline(156);
      var7 = PyString.fromInterned("");
      var1.getlocal(0).__setattr__((String)"c_func_name", var7);
      var3 = null;
      var1.setline(158);
      var6 = var1.getlocal(2);
      PyObject var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(159);
         var6 = var1.getlocal(0).__getattr__("bias");
         var1.setlocal(2, var6);
         var3 = null;
      }

      var1.setline(160);
      var6 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("bias", var6);
      var3 = null;
      var1.setline(162);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(163);
         if (var1.getglobal("_has_res").__nonzero__()) {
            var1.setline(164);
            var6 = var1.getglobal("resgetrusage");
            var1.getlocal(0).__setattr__("timer", var6);
            var3 = null;
            var1.setline(165);
            var6 = var1.getlocal(0).__getattr__("trace_dispatch");
            var1.getlocal(0).__setattr__("dispatcher", var6);
            var3 = null;
            var1.setline(166);
            var6 = var1.getglobal("_get_time_resource");
            var1.getlocal(0).__setattr__("get_time", var6);
            var3 = null;
         } else {
            var1.setline(167);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("time"), (PyObject)PyString.fromInterned("clock")).__nonzero__()) {
               var1.setline(168);
               var6 = var1.getglobal("time").__getattr__("clock");
               var1.getlocal(0).__setattr__("timer", var6);
               var1.getlocal(0).__setattr__("get_time", var6);
               var1.setline(169);
               var6 = var1.getlocal(0).__getattr__("trace_dispatch_i");
               var1.getlocal(0).__setattr__("dispatcher", var6);
               var3 = null;
            } else {
               var1.setline(170);
               if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("times")).__nonzero__()) {
                  var1.setline(171);
                  var6 = var1.getglobal("os").__getattr__("times");
                  var1.getlocal(0).__setattr__("timer", var6);
                  var3 = null;
                  var1.setline(172);
                  var6 = var1.getlocal(0).__getattr__("trace_dispatch");
                  var1.getlocal(0).__setattr__("dispatcher", var6);
                  var3 = null;
                  var1.setline(173);
                  var6 = var1.getglobal("_get_time_times");
                  var1.getlocal(0).__setattr__("get_time", var6);
                  var3 = null;
               } else {
                  var1.setline(175);
                  var6 = var1.getglobal("time").__getattr__("time");
                  var1.getlocal(0).__setattr__("timer", var6);
                  var1.getlocal(0).__setattr__("get_time", var6);
                  var1.setline(176);
                  var6 = var1.getlocal(0).__getattr__("trace_dispatch_i");
                  var1.getlocal(0).__setattr__("dispatcher", var6);
                  var3 = null;
               }
            }
         }
      } else {
         label47: {
            var1.setline(178);
            var6 = var1.getlocal(1);
            var1.getlocal(0).__setattr__("timer", var6);
            var3 = null;
            var1.setline(179);
            var6 = var1.getlocal(0).__getattr__("timer").__call__(var2);
            var1.setlocal(3, var6);
            var3 = null;

            PyObject var4;
            try {
               var1.setline(181);
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
               var1.setlocal(4, var6);
               var3 = null;
            } catch (Throwable var5) {
               PyException var10 = Py.setException(var5, var1);
               if (var10.match(var1.getglobal("TypeError"))) {
                  var1.setline(183);
                  var4 = var1.getlocal(1);
                  var1.getlocal(0).__setattr__("get_time", var4);
                  var4 = null;
                  var1.setline(184);
                  var4 = var1.getlocal(0).__getattr__("trace_dispatch_i");
                  var1.getlocal(0).__setattr__("dispatcher", var4);
                  var4 = null;
                  break label47;
               }

               throw var10;
            }

            var1.setline(186);
            var4 = var1.getlocal(4);
            var10000 = var4._eq(Py.newInteger(2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(187);
               var4 = var1.getlocal(0).__getattr__("trace_dispatch");
               var1.getlocal(0).__setattr__("dispatcher", var4);
               var4 = null;
            } else {
               var1.setline(189);
               var4 = var1.getlocal(0).__getattr__("trace_dispatch_l");
               var1.getlocal(0).__setattr__("dispatcher", var4);
               var4 = null;
            }

            var1.setline(195);
            PyObject[] var8 = new PyObject[]{var1.getlocal(1), var1.getglobal("sum")};
            PyFunction var9 = new PyFunction(var1.f_globals, var8, get_time_timer$9, (PyObject)null);
            var1.setlocal(5, var9);
            var4 = null;
            var1.setline(197);
            var4 = var1.getlocal(5);
            var1.getlocal(0).__setattr__("get_time", var4);
            var4 = null;
         }
      }

      var1.setline(198);
      var6 = var1.getlocal(0).__getattr__("get_time").__call__(var2);
      var1.getlocal(0).__setattr__("t", var6);
      var3 = null;
      var1.setline(199);
      var1.getlocal(0).__getattr__("simulate_call").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("profiler"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_time_timer$9(PyFrame var1, ThreadState var2) {
      var1.setline(196);
      PyObject var3 = var1.getlocal(1).__call__(var2, var1.getlocal(0).__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject trace_dispatch$10(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyObject var3 = var1.getlocal(0).__getattr__("timer");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(205);
      var3 = var1.getlocal(4).__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(206);
      var3 = var1.getlocal(5).__getitem__(Py.newInteger(0))._add(var1.getlocal(5).__getitem__(Py.newInteger(1)))._sub(var1.getlocal(0).__getattr__("t"))._sub(var1.getlocal(0).__getattr__("bias"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(208);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("c_call"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(209);
         var3 = var1.getlocal(3).__getattr__("__name__");
         var1.getlocal(0).__setattr__("c_func_name", var3);
         var3 = null;
      }

      var1.setline(211);
      if (var1.getlocal(0).__getattr__("dispatch").__getitem__(var1.getlocal(2)).__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(5)).__nonzero__()) {
         var1.setline(212);
         var3 = var1.getlocal(4).__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(213);
         var3 = var1.getlocal(5).__getitem__(Py.newInteger(0))._add(var1.getlocal(5).__getitem__(Py.newInteger(1)));
         var1.getlocal(0).__setattr__("t", var3);
         var3 = null;
      } else {
         var1.setline(215);
         var3 = var1.getlocal(4).__call__(var2);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(216);
         var3 = var1.getlocal(6).__getitem__(Py.newInteger(0))._add(var1.getlocal(6).__getitem__(Py.newInteger(1)))._sub(var1.getlocal(5));
         var1.getlocal(0).__setattr__("t", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject trace_dispatch_i$11(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      PyObject var3 = var1.getlocal(0).__getattr__("timer");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(223);
      var3 = var1.getlocal(4).__call__(var2)._sub(var1.getlocal(0).__getattr__("t"))._sub(var1.getlocal(0).__getattr__("bias"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(225);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("c_call"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(226);
         var3 = var1.getlocal(3).__getattr__("__name__");
         var1.getlocal(0).__setattr__("c_func_name", var3);
         var3 = null;
      }

      var1.setline(228);
      if (var1.getlocal(0).__getattr__("dispatch").__getitem__(var1.getlocal(2)).__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(5)).__nonzero__()) {
         var1.setline(229);
         var3 = var1.getlocal(4).__call__(var2);
         var1.getlocal(0).__setattr__("t", var3);
         var3 = null;
      } else {
         var1.setline(231);
         var3 = var1.getlocal(4).__call__(var2)._sub(var1.getlocal(5));
         var1.getlocal(0).__setattr__("t", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject trace_dispatch_mac$12(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyObject var3 = var1.getlocal(0).__getattr__("timer");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(238);
      var3 = var1.getlocal(4).__call__(var2)._div(Py.newFloat(60.0))._sub(var1.getlocal(0).__getattr__("t"))._sub(var1.getlocal(0).__getattr__("bias"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(240);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("c_call"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(241);
         var3 = var1.getlocal(3).__getattr__("__name__");
         var1.getlocal(0).__setattr__("c_func_name", var3);
         var3 = null;
      }

      var1.setline(243);
      if (var1.getlocal(0).__getattr__("dispatch").__getitem__(var1.getlocal(2)).__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(5)).__nonzero__()) {
         var1.setline(244);
         var3 = var1.getlocal(4).__call__(var2)._div(Py.newFloat(60.0));
         var1.getlocal(0).__setattr__("t", var3);
         var3 = null;
      } else {
         var1.setline(246);
         var3 = var1.getlocal(4).__call__(var2)._div(Py.newFloat(60.0))._sub(var1.getlocal(5));
         var1.getlocal(0).__setattr__("t", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject trace_dispatch_l$13(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyObject var3 = var1.getlocal(0).__getattr__("get_time");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(252);
      var3 = var1.getlocal(4).__call__(var2)._sub(var1.getlocal(0).__getattr__("t"))._sub(var1.getlocal(0).__getattr__("bias"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(254);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("c_call"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(255);
         var3 = var1.getlocal(3).__getattr__("__name__");
         var1.getlocal(0).__setattr__("c_func_name", var3);
         var3 = null;
      }

      var1.setline(257);
      if (var1.getlocal(0).__getattr__("dispatch").__getitem__(var1.getlocal(2)).__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(5)).__nonzero__()) {
         var1.setline(258);
         var3 = var1.getlocal(4).__call__(var2);
         var1.getlocal(0).__setattr__("t", var3);
         var3 = null;
      } else {
         var1.setline(260);
         var3 = var1.getlocal(4).__call__(var2)._sub(var1.getlocal(5));
         var1.getlocal(0).__setattr__("t", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject trace_dispatch_exception$14(PyFrame var1, ThreadState var2) {
      var1.setline(270);
      PyObject var3 = var1.getlocal(0).__getattr__("cur");
      PyObject[] var4 = Py.unpackSequence(var3, 6);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(271);
      var3 = var1.getlocal(7);
      PyObject var10000 = var3._isnot(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(8);
      }

      if (var10000.__nonzero__()) {
         var1.setline(272);
         var3 = var1.getlocal(0).__getattr__("trace_dispatch_return").__call__(var2, var1.getlocal(7), var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(273);
         PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4)._add(var1.getlocal(2)), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8)});
         var1.getlocal(0).__setattr__((String)"cur", var6);
         var4 = null;
         var1.setline(274);
         PyInteger var7 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject trace_dispatch_call$15(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyObject var10000 = var1.getlocal(0).__getattr__("cur");
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getattr__("f_back");
         var10000 = var3._isnot(var1.getlocal(0).__getattr__("cur").__getitem__(Py.newInteger(-2)));
         var3 = null;
      }

      PyObject[] var4;
      PyObject var5;
      if (var10000.__nonzero__()) {
         var1.setline(279);
         var3 = var1.getlocal(0).__getattr__("cur");
         var4 = Py.unpackSequence(var3, 6);
         var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[4];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[5];
         var1.setlocal(8, var5);
         var5 = null;
         var3 = null;
         var1.setline(280);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("Profile").__getattr__("fake_frame")).__not__().__nonzero__()) {
            var1.setline(281);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var3 = var1.getlocal(7).__getattr__("f_back");
               var10000 = var3._is(var1.getlocal(1).__getattr__("f_back"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{PyString.fromInterned("Bad call"), var1.getlocal(6), var1.getlocal(7), var1.getlocal(7).__getattr__("f_back"), var1.getlocal(1), var1.getlocal(1).__getattr__("f_back")}));
               }
            }

            var1.setline(284);
            var1.getlocal(0).__getattr__("trace_dispatch_return").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)Py.newInteger(0));
            var1.setline(285);
            if (var1.getglobal("__debug__").__nonzero__()) {
               var3 = var1.getlocal(0).__getattr__("cur");
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(1).__getattr__("f_back");
                  var10000 = var3._is(var1.getlocal(0).__getattr__("cur").__getitem__(Py.newInteger(-2)));
                  var3 = null;
               }

               if (!var10000.__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{PyString.fromInterned("Bad call"), var1.getlocal(0).__getattr__("cur").__getitem__(Py.newInteger(-3))}));
               }
            }
         }
      }

      var1.setline(288);
      var3 = var1.getlocal(1).__getattr__("f_code");
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(289);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(9).__getattr__("co_filename"), var1.getlocal(9).__getattr__("co_firstlineno"), var1.getlocal(9).__getattr__("co_name")});
      var1.setlocal(10, var6);
      var3 = null;
      var1.setline(290);
      var6 = new PyTuple(new PyObject[]{var1.getlocal(2), Py.newInteger(0), Py.newInteger(0), var1.getlocal(10), var1.getlocal(1), var1.getlocal(0).__getattr__("cur")});
      var1.getlocal(0).__setattr__((String)"cur", var6);
      var3 = null;
      var1.setline(291);
      var3 = var1.getlocal(0).__getattr__("timings");
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(292);
      var3 = var1.getlocal(10);
      var10000 = var3._in(var1.getlocal(11));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(293);
         var3 = var1.getlocal(11).__getitem__(var1.getlocal(10));
         var4 = Py.unpackSequence(var3, 5);
         var5 = var4[0];
         var1.setlocal(12, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(13, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(14, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(15, var5);
         var5 = null;
         var5 = var4[4];
         var1.setlocal(16, var5);
         var5 = null;
         var3 = null;
         var1.setline(294);
         var6 = new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(13)._add(Py.newInteger(1)), var1.getlocal(14), var1.getlocal(15), var1.getlocal(16)});
         var1.getlocal(11).__setitem__((PyObject)var1.getlocal(10), var6);
         var3 = null;
      } else {
         var1.setline(296);
         var6 = new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), new PyDictionary(Py.EmptyObjects)});
         var1.getlocal(11).__setitem__((PyObject)var1.getlocal(10), var6);
         var3 = null;
      }

      var1.setline(297);
      PyInteger var7 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject trace_dispatch_c_call$16(PyFrame var1, ThreadState var2) {
      var1.setline(300);
      PyTuple var3 = new PyTuple(new PyObject[]{PyString.fromInterned(""), Py.newInteger(0), var1.getlocal(0).__getattr__("c_func_name")});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(301);
      var3 = new PyTuple(new PyObject[]{var1.getlocal(2), Py.newInteger(0), Py.newInteger(0), var1.getlocal(3), var1.getlocal(1), var1.getlocal(0).__getattr__("cur")});
      var1.getlocal(0).__setattr__((String)"cur", var3);
      var3 = null;
      var1.setline(302);
      PyObject var6 = var1.getlocal(0).__getattr__("timings");
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(303);
      var6 = var1.getlocal(3);
      PyObject var10000 = var6._in(var1.getlocal(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(304);
         var6 = var1.getlocal(4).__getitem__(var1.getlocal(3));
         PyObject[] var4 = Py.unpackSequence(var6, 5);
         PyObject var5 = var4[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var4[4];
         var1.setlocal(9, var5);
         var5 = null;
         var3 = null;
         var1.setline(305);
         var3 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)._add(Py.newInteger(1)), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9)});
         var1.getlocal(4).__setitem__((PyObject)var1.getlocal(3), var3);
         var3 = null;
      } else {
         var1.setline(307);
         var3 = new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), new PyDictionary(Py.EmptyObjects)});
         var1.getlocal(4).__setitem__((PyObject)var1.getlocal(3), var3);
         var3 = null;
      }

      var1.setline(308);
      PyInteger var7 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject trace_dispatch_return$17(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getlocal(0).__getattr__("cur").__getitem__(Py.newInteger(-2)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(312);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._is(var1.getlocal(0).__getattr__("cur").__getitem__(Py.newInteger(-2)).__getattr__("f_back"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), new PyTuple(new PyObject[]{PyString.fromInterned("Bad return"), var1.getlocal(0).__getattr__("cur").__getitem__(Py.newInteger(-3))}));
            }
         }

         var1.setline(313);
         var1.getlocal(0).__getattr__("trace_dispatch_return").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cur").__getitem__(Py.newInteger(-2)), (PyObject)Py.newInteger(0));
      }

      var1.setline(318);
      var3 = var1.getlocal(0).__getattr__("cur");
      PyObject[] var4 = Py.unpackSequence(var3, 6);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(319);
      var3 = var1.getlocal(4)._add(var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(320);
      var3 = var1.getlocal(4)._add(var1.getlocal(5));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(322);
      var3 = var1.getlocal(7);
      var4 = Py.unpackSequence(var3, 6);
      var5 = var4[0];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(11, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(12, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(13, var5);
      var5 = null;
      var5 = var4[5];
      var1.setlocal(14, var5);
      var5 = null;
      var3 = null;
      var1.setline(323);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(10)._add(var1.getlocal(3)), var1.getlocal(11)._add(var1.getlocal(8)), var1.getlocal(12), var1.getlocal(13), var1.getlocal(14)});
      var1.getlocal(0).__setattr__((String)"cur", var6);
      var3 = null;
      var1.setline(325);
      var3 = var1.getlocal(0).__getattr__("timings");
      var1.setlocal(15, var3);
      var3 = null;
      var1.setline(326);
      var3 = var1.getlocal(15).__getitem__(var1.getlocal(6));
      var4 = Py.unpackSequence(var3, 5);
      var5 = var4[0];
      var1.setlocal(16, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(17, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(18, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(19, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(20, var5);
      var5 = null;
      var3 = null;
      var1.setline(327);
      if (var1.getlocal(17).__not__().__nonzero__()) {
         var1.setline(332);
         var3 = var1.getlocal(19)._add(var1.getlocal(8));
         var1.setlocal(19, var3);
         var3 = null;
         var1.setline(333);
         var3 = var1.getlocal(16)._add(Py.newInteger(1));
         var1.setlocal(16, var3);
         var3 = null;
      }

      var1.setline(335);
      var3 = var1.getlocal(12);
      var10000 = var3._in(var1.getlocal(20));
      var3 = null;
      PyInteger var7;
      if (var10000.__nonzero__()) {
         var1.setline(336);
         var3 = var1.getlocal(20).__getitem__(var1.getlocal(12))._add(Py.newInteger(1));
         var1.getlocal(20).__setitem__(var1.getlocal(12), var3);
         var3 = null;
      } else {
         var1.setline(341);
         var7 = Py.newInteger(1);
         var1.getlocal(20).__setitem__((PyObject)var1.getlocal(12), var7);
         var3 = null;
      }

      var1.setline(343);
      var6 = new PyTuple(new PyObject[]{var1.getlocal(16), var1.getlocal(17)._sub(Py.newInteger(1)), var1.getlocal(18)._add(var1.getlocal(4)), var1.getlocal(19), var1.getlocal(20)});
      var1.getlocal(15).__setitem__((PyObject)var1.getlocal(6), var6);
      var3 = null;
      var1.setline(345);
      var7 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject set_cmd$18(PyFrame var1, ThreadState var2) {
      var1.setline(365);
      if (var1.getlocal(0).__getattr__("cur").__getitem__(Py.newInteger(-1)).__nonzero__()) {
         var1.setline(365);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(366);
         PyObject var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("cmd", var3);
         var3 = null;
         var1.setline(367);
         var1.getlocal(0).__getattr__("simulate_call").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject fake_code$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(370);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(376);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$21, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.setline(371);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("co_filename", var3);
      var3 = null;
      var1.setline(372);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("co_line", var3);
      var3 = null;
      var1.setline(373);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("co_name", var3);
      var3 = null;
      var1.setline(374);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"co_firstlineno", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$21(PyFrame var1, ThreadState var2) {
      var1.setline(377);
      PyObject var3 = var1.getglobal("repr").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("co_filename"), var1.getlocal(0).__getattr__("co_line"), var1.getlocal(0).__getattr__("co_name")})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fake_frame$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(380);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$23, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$23(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("f_code", var3);
      var3 = null;
      var1.setline(382);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("f_back", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject simulate_call$24(PyFrame var1, ThreadState var2) {
      var1.setline(385);
      PyObject var3 = var1.getlocal(0).__getattr__("fake_code").__call__((ThreadState)var2, PyString.fromInterned("profile"), (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(386);
      if (var1.getlocal(0).__getattr__("cur").__nonzero__()) {
         var1.setline(387);
         var3 = var1.getlocal(0).__getattr__("cur").__getitem__(Py.newInteger(-2));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(389);
         var3 = var1.getglobal("None");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(390);
      var3 = var1.getlocal(0).__getattr__("fake_frame").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(391);
      var1.getlocal(0).__getattr__("dispatch").__getitem__(PyString.fromInterned("call")).__call__((ThreadState)var2, var1.getlocal(0), (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject simulate_cmd_complete$25(PyFrame var1, ThreadState var2) {
      var1.setline(397);
      PyObject var3 = var1.getlocal(0).__getattr__("get_time");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(398);
      var3 = var1.getlocal(1).__call__(var2)._sub(var1.getlocal(0).__getattr__("t"));
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(399);
         if (!var1.getlocal(0).__getattr__("cur").__getitem__(Py.newInteger(-1)).__nonzero__()) {
            var1.setline(404);
            var3 = var1.getlocal(1).__call__(var2)._sub(var1.getlocal(2));
            var1.getlocal(0).__setattr__("t", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(402);
         var1.getlocal(0).__getattr__("dispatch").__getitem__(PyString.fromInterned("return")).__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("cur").__getitem__(Py.newInteger(-2)), var1.getlocal(2));
         var1.setline(403);
         PyInteger var4 = Py.newInteger(0);
         var1.setlocal(2, var4);
         var3 = null;
      }
   }

   public PyObject print_stats$26(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyObject var3 = imp.importOne("pstats", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(409);
      var1.getlocal(2).__getattr__("Stats").__call__(var2, var1.getlocal(0)).__getattr__("strip_dirs").__call__(var2).__getattr__("sort_stats").__call__(var2, var1.getlocal(1)).__getattr__("print_stats").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject dump_stats$27(PyFrame var1, ThreadState var2) {
      var1.setline(413);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("wb"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(414);
      var1.getlocal(0).__getattr__("create_stats").__call__(var2);
      var1.setline(415);
      var1.getglobal("marshal").__getattr__("dump").__call__(var2, var1.getlocal(0).__getattr__("stats"), var1.getlocal(2));
      var1.setline(416);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject create_stats$28(PyFrame var1, ThreadState var2) {
      var1.setline(419);
      var1.getlocal(0).__getattr__("simulate_cmd_complete").__call__(var2);
      var1.setline(420);
      var1.getlocal(0).__getattr__("snapshot_stats").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject snapshot_stats$29(PyFrame var1, ThreadState var2) {
      var1.setline(423);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"stats", var3);
      var3 = null;
      var1.setline(424);
      PyObject var9 = var1.getlocal(0).__getattr__("timings").__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(424);
         PyObject var4 = var9.__iternext__();
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
         var1.setline(425);
         PyObject var10 = var1.getlocal(6).__getattr__("copy").__call__(var2);
         var1.setlocal(6, var10);
         var5 = null;
         var1.setline(426);
         PyInteger var11 = Py.newInteger(0);
         var1.setlocal(7, var11);
         var5 = null;
         var1.setline(427);
         var10 = var1.getlocal(6).__getattr__("itervalues").__call__(var2).__iter__();

         while(true) {
            var1.setline(427);
            var6 = var10.__iternext__();
            if (var6 == null) {
               var1.setline(429);
               PyTuple var12 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(7), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)});
               var1.getlocal(0).__getattr__("stats").__setitem__((PyObject)var1.getlocal(1), var12);
               var5 = null;
               break;
            }

            var1.setlocal(8, var6);
            var1.setline(428);
            PyObject var13 = var1.getlocal(7);
            var13 = var13._iadd(var1.getlocal(8));
            var1.setlocal(7, var13);
         }
      }
   }

   public PyObject run$30(PyFrame var1, ThreadState var2) {
      var1.setline(436);
      PyObject var3 = imp.importOne("__main__", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(437);
      var3 = var1.getlocal(2).__getattr__("__dict__");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(438);
      var3 = var1.getlocal(0).__getattr__("runctx").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject runctx$31(PyFrame var1, ThreadState var2) {
      var1.setline(441);
      var1.getlocal(0).__getattr__("set_cmd").__call__(var2, var1.getlocal(1));
      var1.setline(442);
      var1.getname("sys").__getattr__("setprofile").__call__(var2, var1.getlocal(0).__getattr__("dispatcher"));
      PyObject var3 = null;

      try {
         var1.setline(444);
         Py.exec(var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(446);
         var1.getname("sys").__getattr__("setprofile").__call__(var2, var1.getname("None"));
         throw (Throwable)var4;
      }

      var1.setline(446);
      var1.getname("sys").__getattr__("setprofile").__call__(var2, var1.getname("None"));
      var1.setline(447);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject runcall$32(PyFrame var1, ThreadState var2) {
      var1.setline(451);
      var1.getlocal(0).__getattr__("set_cmd").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(1)));
      var1.setline(452);
      var1.getglobal("sys").__getattr__("setprofile").__call__(var2, var1.getlocal(0).__getattr__("dispatcher"));
      Throwable var3 = null;

      Throwable var10000;
      label25: {
         boolean var10001;
         PyObject var8;
         try {
            var1.setline(454);
            PyObject var9 = var1.getlocal(1);
            PyObject[] var4 = Py.EmptyObjects;
            String[] var5 = new String[0];
            var9 = var9._callextra(var4, var5, var1.getlocal(2), var1.getlocal(3));
            var4 = null;
            var8 = var9;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label25;
         }

         var1.setline(456);
         var1.getglobal("sys").__getattr__("setprofile").__call__(var2, var1.getglobal("None"));

         try {
            var1.f_lasti = -1;
            return var8;
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
         }
      }

      var3 = var10000;
      Py.addTraceback(var3, var1);
      var1.setline(456);
      var1.getglobal("sys").__getattr__("setprofile").__call__(var2, var1.getglobal("None"));
      throw (Throwable)var3;
   }

   public PyObject calibrate$33(PyFrame var1, ThreadState var2) {
      var1.setline(500);
      PyObject var3 = var1.getlocal(0).__getattr__("__class__");
      PyObject var10000 = var3._isnot(var1.getglobal("Profile"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(501);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Subclasses must override .calibrate().")));
      } else {
         var1.setline(503);
         var3 = var1.getlocal(0).__getattr__("bias");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(504);
         PyInteger var8 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"bias", var8);
         var3 = null;
         var3 = null;

         PyObject var5;
         Throwable var10;
         label29: {
            boolean var10001;
            PyObject var4;
            try {
               var1.setline(506);
               var4 = var1.getlocal(0).__getattr__("_calibrate_inner").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            } catch (Throwable var7) {
               var10 = var7;
               var10001 = false;
               break label29;
            }

            var1.setline(508);
            var5 = var1.getlocal(3);
            var1.getlocal(0).__setattr__("bias", var5);
            var5 = null;

            try {
               var1.f_lasti = -1;
               return var4;
            } catch (Throwable var6) {
               var10 = var6;
               var10001 = false;
            }
         }

         Throwable var9 = var10;
         Py.addTraceback(var9, var1);
         var1.setline(508);
         var5 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("bias", var5);
         var5 = null;
         throw (Throwable)var9;
      }
   }

   public PyObject _calibrate_inner$34(PyFrame var1, ThreadState var2) {
      var1.setline(511);
      PyObject var3 = var1.getlocal(0).__getattr__("get_time");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(519);
      PyObject[] var9 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var9, f1$35, (PyObject)null);
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(523);
      var9 = new PyObject[]{var1.getlocal(4)};
      var10 = new PyFunction(var1.f_globals, var9, f$36, (PyObject)null);
      var1.setlocal(5, var10);
      var3 = null;
      var1.setline(527);
      var1.getlocal(5).__call__(var2, var1.getlocal(1));
      var1.setline(530);
      var3 = var1.getlocal(3).__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(531);
      var1.getlocal(5).__call__(var2, var1.getlocal(1));
      var1.setline(532);
      var3 = var1.getlocal(3).__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(533);
      var3 = var1.getlocal(7)._sub(var1.getlocal(6));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(534);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(535);
         Py.printComma(PyString.fromInterned("elapsed time without profiling ="));
         Py.println(var1.getlocal(8));
      }

      var1.setline(540);
      var3 = var1.getglobal("Profile").__call__(var2);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(541);
      var3 = var1.getlocal(3).__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(542);
      var1.getlocal(9).__getattr__("runctx").__call__((ThreadState)var2, PyString.fromInterned("f(m)"), (PyObject)var1.getglobal("globals").__call__(var2), (PyObject)var1.getglobal("locals").__call__(var2));
      var1.setline(543);
      var3 = var1.getlocal(3).__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(544);
      var3 = var1.getlocal(7)._sub(var1.getlocal(6));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(545);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(546);
         Py.printComma(PyString.fromInterned("elapsed time with profiling ="));
         Py.println(var1.getlocal(10));
      }

      var1.setline(549);
      PyFloat var12 = Py.newFloat(0.0);
      var1.setlocal(11, var12);
      var3 = null;
      var1.setline(550);
      var12 = Py.newFloat(0.0);
      var1.setlocal(12, var12);
      var3 = null;
      var1.setline(551);
      var3 = var1.getlocal(9).__getattr__("timings").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(551);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(557);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(558);
               Py.printComma(PyString.fromInterned("'CPU seconds' profiler reported ="));
               Py.println(var1.getlocal(12));
               var1.setline(559);
               Py.printComma(PyString.fromInterned("total # calls ="));
               Py.println(var1.getlocal(11));
            }

            var1.setline(560);
            var3 = var1.getlocal(11);
            var10000 = var3._ne(var1.getlocal(1)._add(Py.newInteger(1)));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(561);
               throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("internal error: total calls = %d")._mod(var1.getlocal(11))));
            }

            var1.setline(567);
            var3 = var1.getlocal(12)._sub(var1.getlocal(8))._div(Py.newFloat(2.0))._div(var1.getlocal(11));
            var1.setlocal(21, var3);
            var3 = null;
            var1.setline(568);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(569);
               Py.printComma(PyString.fromInterned("mean stopwatch overhead per profile event ="));
               Py.println(var1.getlocal(21));
            }

            var1.setline(570);
            var3 = var1.getlocal(21);
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         PyObject[] var7 = Py.unpackSequence(var6, 3);
         PyObject var8 = var7[0];
         var1.setlocal(13, var8);
         var8 = null;
         var8 = var7[1];
         var1.setlocal(14, var8);
         var8 = null;
         var8 = var7[2];
         var1.setlocal(15, var8);
         var8 = null;
         var6 = null;
         var6 = var5[1];
         var7 = Py.unpackSequence(var6, 5);
         var8 = var7[0];
         var1.setlocal(16, var8);
         var8 = null;
         var8 = var7[1];
         var1.setlocal(17, var8);
         var8 = null;
         var8 = var7[2];
         var1.setlocal(18, var8);
         var8 = null;
         var8 = var7[3];
         var1.setlocal(19, var8);
         var8 = null;
         var8 = var7[4];
         var1.setlocal(20, var8);
         var8 = null;
         var6 = null;
         var1.setline(553);
         PyObject var11 = var1.getlocal(15);
         var10000 = var11._in(new PyTuple(new PyObject[]{PyString.fromInterned("f"), PyString.fromInterned("f1")}));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(554);
            var11 = var1.getlocal(11);
            var11 = var11._iadd(var1.getlocal(16));
            var1.setlocal(11, var11);
            var1.setline(555);
            var11 = var1.getlocal(12);
            var11 = var11._iadd(var1.getlocal(18));
            var1.setlocal(12, var11);
         }
      }
   }

   public PyObject f1$35(PyFrame var1, ThreadState var2) {
      var1.setline(520);
      PyObject var3 = var1.getglobal("range").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(520);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(521);
         PyInteger var5 = Py.newInteger(1);
         var1.setlocal(2, var5);
         var5 = null;
      }
   }

   public PyObject f$36(PyFrame var1, ThreadState var2) {
      var1.setline(524);
      PyObject var3 = var1.getglobal("range").__call__(var2, var1.getlocal(0)).__iter__();

      while(true) {
         var1.setline(524);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(525);
         var1.getlocal(1).__call__((ThreadState)var2, (PyObject)Py.newInteger(100));
      }
   }

   public PyObject Stats$37(PyFrame var1, ThreadState var2) {
      var1.setline(574);
      Py.println(PyString.fromInterned("Report generating functions are in the \"pstats\" module\u0007"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject main$38(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(577);
      PyString var3 = PyString.fromInterned("profile.py [-o output_file_path] [-s sort] scriptfile [arg] ...");
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(578);
      PyObject var10000 = var1.getglobal("OptionParser");
      PyObject[] var7 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[]{"usage"};
      var10000 = var10000.__call__(var2, var7, var4);
      var3 = null;
      PyObject var8 = var10000;
      var1.setlocal(1, var8);
      var3 = null;
      var1.setline(579);
      var8 = var1.getglobal("False");
      var1.getlocal(1).__setattr__("allow_interspersed_args", var8);
      var3 = null;
      var1.setline(580);
      var10000 = var1.getlocal(1).__getattr__("add_option");
      var7 = new PyObject[]{PyString.fromInterned("-o"), PyString.fromInterned("--outfile"), PyString.fromInterned("outfile"), PyString.fromInterned("Save stats to <outfile>"), var1.getglobal("None")};
      var4 = new String[]{"dest", "help", "default"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(582);
      var10000 = var1.getlocal(1).__getattr__("add_option");
      var7 = new PyObject[]{PyString.fromInterned("-s"), PyString.fromInterned("--sort"), PyString.fromInterned("sort"), PyString.fromInterned("Sort order when printing to stdout, based on pstats.Stats class"), Py.newInteger(-1)};
      var4 = new String[]{"dest", "help", "default"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(586);
      if (var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__not__().__nonzero__()) {
         var1.setline(587);
         var1.getlocal(1).__getattr__("print_usage").__call__(var2);
         var1.setline(588);
         var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      }

      var1.setline(590);
      var8 = var1.getlocal(1).__getattr__("parse_args").__call__(var2);
      PyObject[] var9 = Py.unpackSequence(var8, 2);
      PyObject var5 = var9[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var9[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(591);
      var8 = var1.getlocal(3);
      var1.getglobal("sys").__getattr__("argv").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var8);
      var3 = null;
      var1.setline(593);
      var8 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
      var10000 = var8._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(594);
         var8 = var1.getlocal(3).__getitem__(Py.newInteger(0));
         var1.setlocal(4, var8);
         var3 = null;
         var1.setline(595);
         var1.getglobal("sys").__getattr__("path").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(4)));
         ContextManager var11;
         PyObject var10 = (var11 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("rb")))).__enter__(var2);

         label23: {
            try {
               var1.setlocal(5, var10);
               var1.setline(597);
               var10 = var1.getglobal("compile").__call__((ThreadState)var2, var1.getlocal(5).__getattr__("read").__call__(var2), (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("exec"));
               var1.setlocal(6, var10);
               var4 = null;
            } catch (Throwable var6) {
               if (var11.__exit__(var2, Py.setException(var6, var1))) {
                  break label23;
               }

               throw (Throwable)Py.makeException();
            }

            var11.__exit__(var2, (PyException)null);
         }

         var1.setline(598);
         PyDictionary var12 = new PyDictionary(new PyObject[]{PyString.fromInterned("__file__"), var1.getlocal(4), PyString.fromInterned("__name__"), PyString.fromInterned("__main__"), PyString.fromInterned("__package__"), var1.getglobal("None")});
         var1.setlocal(7, var12);
         var3 = null;
         var1.setline(603);
         var10000 = var1.getglobal("runctx");
         var7 = new PyObject[]{var1.getlocal(6), var1.getlocal(7), var1.getglobal("None"), var1.getlocal(2).__getattr__("outfile"), var1.getlocal(2).__getattr__("sort")};
         var10000.__call__(var2, var7);
      } else {
         var1.setline(605);
         var1.getlocal(1).__getattr__("print_usage").__call__(var2);
      }

      var1.setline(606);
      var8 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var8;
   }

   public profile$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"statement", "filename", "sort", "prof"};
      run$1 = Py.newCode(3, var2, var1, "run", 48, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"statement", "globals", "locals", "filename", "sort", "prof"};
      runctx$2 = Py.newCode(5, var2, var1, "runctx", 69, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      help$3 = Py.newCode(0, var2, var1, "help", 87, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"timer", "t"};
      _get_time_times$4 = Py.newCode(1, var2, var1, "_get_time_times", 92, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$5 = Py.newCode(0, var2, var1, "<lambda>", 103, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"timer", "t"};
      _get_time_resource$6 = Py.newCode(1, var2, var1, "_get_time_resource", 104, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Profile$7 = Py.newCode(0, var2, var1, "Profile", 111, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "timer", "bias", "t", "length", "get_time_timer"};
      __init__$8 = Py.newCode(3, var2, var1, "__init__", 152, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"timer", "sum"};
      get_time_timer$9 = Py.newCode(2, var2, var1, "get_time_timer", 195, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "event", "arg", "timer", "t", "r"};
      trace_dispatch$10 = Py.newCode(4, var2, var1, "trace_dispatch", 203, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "event", "arg", "timer", "t"};
      trace_dispatch_i$11 = Py.newCode(4, var2, var1, "trace_dispatch_i", 221, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "event", "arg", "timer", "t"};
      trace_dispatch_mac$12 = Py.newCode(4, var2, var1, "trace_dispatch_mac", 236, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "event", "arg", "get_time", "t"};
      trace_dispatch_l$13 = Py.newCode(4, var2, var1, "trace_dispatch_l", 250, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "t", "rpt", "rit", "ret", "rfn", "rframe", "rcur"};
      trace_dispatch_exception$14 = Py.newCode(3, var2, var1, "trace_dispatch_exception", 269, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "t", "rpt", "rit", "ret", "rfn", "rframe", "rcur", "fcode", "fn", "timings", "cc", "ns", "tt", "ct", "callers"};
      trace_dispatch_call$15 = Py.newCode(3, var2, var1, "trace_dispatch_call", 277, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "t", "fn", "timings", "cc", "ns", "tt", "ct", "callers"};
      trace_dispatch_c_call$16 = Py.newCode(3, var2, var1, "trace_dispatch_c_call", 299, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "frame", "t", "rpt", "rit", "ret", "rfn", "rcur", "frame_total", "ppt", "pit", "pet", "pfn", "pframe", "pcur", "timings", "cc", "ns", "tt", "ct", "callers"};
      trace_dispatch_return$17 = Py.newCode(3, var2, var1, "trace_dispatch_return", 310, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd"};
      set_cmd$18 = Py.newCode(2, var2, var1, "set_cmd", 364, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      fake_code$19 = Py.newCode(0, var2, var1, "fake_code", 369, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "filename", "line", "name"};
      __init__$20 = Py.newCode(4, var2, var1, "__init__", 370, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$21 = Py.newCode(1, var2, var1, "__repr__", 376, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      fake_frame$22 = Py.newCode(0, var2, var1, "fake_frame", 379, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "code", "prior"};
      __init__$23 = Py.newCode(3, var2, var1, "__init__", 380, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "code", "pframe", "frame"};
      simulate_call$24 = Py.newCode(2, var2, var1, "simulate_call", 384, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "get_time", "t"};
      simulate_cmd_complete$25 = Py.newCode(1, var2, var1, "simulate_cmd_complete", 396, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sort", "pstats"};
      print_stats$26 = Py.newCode(2, var2, var1, "print_stats", 407, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "f"};
      dump_stats$27 = Py.newCode(2, var2, var1, "dump_stats", 412, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      create_stats$28 = Py.newCode(1, var2, var1, "create_stats", 418, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "func", "cc", "ns", "tt", "ct", "callers", "nc", "callcnt"};
      snapshot_stats$29 = Py.newCode(1, var2, var1, "snapshot_stats", 422, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "__main__", "dict"};
      run$30 = Py.newCode(2, var2, var1, "run", 435, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "globals", "locals"};
      runctx$31 = Py.newCode(4, var2, var1, "runctx", 440, false, false, self, 31, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "func", "args", "kw"};
      runcall$32 = Py.newCode(4, var2, var1, "runcall", 450, true, true, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "verbose", "saved_bias"};
      calibrate$33 = Py.newCode(3, var2, var1, "calibrate", 499, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "m", "verbose", "get_time", "f1", "f", "t0", "t1", "elapsed_noprofile", "p", "elapsed_profile", "total_calls", "reported_time", "filename", "line", "funcname", "cc", "ns", "tt", "ct", "callers", "mean"};
      _calibrate_inner$34 = Py.newCode(3, var2, var1, "_calibrate_inner", 510, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"n", "i", "x"};
      f1$35 = Py.newCode(1, var2, var1, "f1", 519, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"m", "f1", "i"};
      f$36 = Py.newCode(2, var2, var1, "f", 523, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args"};
      Stats$37 = Py.newCode(1, var2, var1, "Stats", 573, true, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"usage", "parser", "options", "args", "progname", "fp", "code", "globs"};
      main$38 = Py.newCode(0, var2, var1, "main", 576, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new profile$py("profile$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(profile$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.run$1(var2, var3);
         case 2:
            return this.runctx$2(var2, var3);
         case 3:
            return this.help$3(var2, var3);
         case 4:
            return this._get_time_times$4(var2, var3);
         case 5:
            return this.f$5(var2, var3);
         case 6:
            return this._get_time_resource$6(var2, var3);
         case 7:
            return this.Profile$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.get_time_timer$9(var2, var3);
         case 10:
            return this.trace_dispatch$10(var2, var3);
         case 11:
            return this.trace_dispatch_i$11(var2, var3);
         case 12:
            return this.trace_dispatch_mac$12(var2, var3);
         case 13:
            return this.trace_dispatch_l$13(var2, var3);
         case 14:
            return this.trace_dispatch_exception$14(var2, var3);
         case 15:
            return this.trace_dispatch_call$15(var2, var3);
         case 16:
            return this.trace_dispatch_c_call$16(var2, var3);
         case 17:
            return this.trace_dispatch_return$17(var2, var3);
         case 18:
            return this.set_cmd$18(var2, var3);
         case 19:
            return this.fake_code$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.__repr__$21(var2, var3);
         case 22:
            return this.fake_frame$22(var2, var3);
         case 23:
            return this.__init__$23(var2, var3);
         case 24:
            return this.simulate_call$24(var2, var3);
         case 25:
            return this.simulate_cmd_complete$25(var2, var3);
         case 26:
            return this.print_stats$26(var2, var3);
         case 27:
            return this.dump_stats$27(var2, var3);
         case 28:
            return this.create_stats$28(var2, var3);
         case 29:
            return this.snapshot_stats$29(var2, var3);
         case 30:
            return this.run$30(var2, var3);
         case 31:
            return this.runctx$31(var2, var3);
         case 32:
            return this.runcall$32(var2, var3);
         case 33:
            return this.calibrate$33(var2, var3);
         case 34:
            return this._calibrate_inner$34(var2, var3);
         case 35:
            return this.f1$35(var2, var3);
         case 36:
            return this.f$36(var2, var3);
         case 37:
            return this.Stats$37(var2, var3);
         case 38:
            return this.main$38(var2, var3);
         default:
            return null;
      }
   }
}
