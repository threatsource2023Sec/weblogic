package unittest;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("unittest/result.py")
public class result$py extends PyFunctionTable implements PyRunnable {
   static result$py self;
   static final PyCode f$0;
   static final PyCode failfast$1;
   static final PyCode inner$2;
   static final PyCode TestResult$3;
   static final PyCode __init__$4;
   static final PyCode printErrors$5;
   static final PyCode startTest$6;
   static final PyCode _setupStdout$7;
   static final PyCode startTestRun$8;
   static final PyCode stopTest$9;
   static final PyCode _restoreStdout$10;
   static final PyCode stopTestRun$11;
   static final PyCode addError$12;
   static final PyCode addFailure$13;
   static final PyCode addSuccess$14;
   static final PyCode addSkip$15;
   static final PyCode addExpectedFailure$16;
   static final PyCode addUnexpectedSuccess$17;
   static final PyCode wasSuccessful$18;
   static final PyCode stop$19;
   static final PyCode _exc_info_to_string$20;
   static final PyCode _is_relevant_tb_level$21;
   static final PyCode _count_relevant_tb_levels$22;
   static final PyCode __repr__$23;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Test result object"));
      var1.setline(1);
      PyString.fromInterned("Test result object");
      var1.setline(3);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var1.setline(7);
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("StringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"util"};
      var6 = imp.importFrom("", var5, var1, 1);
      var4 = var6[0];
      var1.setlocal("util", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"wraps"};
      var6 = imp.importFrom("functools", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("wraps", var4);
      var4 = null;
      var1.setline(12);
      var3 = var1.getname("True");
      var1.setlocal("__unittest", var3);
      var3 = null;
      var1.setline(14);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, failfast$1, (PyObject)null);
      var1.setlocal("failfast", var7);
      var3 = null;
      var1.setline(22);
      PyString var8 = PyString.fromInterned("\nStdout:\n%s");
      var1.setlocal("STDOUT_LINE", var8);
      var3 = null;
      var1.setline(23);
      var8 = PyString.fromInterned("\nStderr:\n%s");
      var1.setlocal("STDERR_LINE", var8);
      var3 = null;
      var1.setline(26);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestResult", var6, TestResult$3);
      var1.setlocal("TestResult", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject failfast$1(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(15);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = inner$2;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getglobal("wraps").__call__(var2, var1.getderef(0)).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(20);
      var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject inner$2(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      if (var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("failfast"), (PyObject)var1.getglobal("False")).__nonzero__()) {
         var1.setline(18);
         var1.getlocal(0).__getattr__("stop").__call__(var2);
      }

      var1.setline(19);
      PyObject var10000 = var1.getderef(0);
      PyObject[] var3 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject TestResult$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Holder for test result information.\n\n    Test results are automatically managed by the TestCase and TestSuite\n    classes, and do not need to be explicitly manipulated by writers of tests.\n\n    Each instance holds the total number of tests run, and collections of\n    failures and errors that occurred among those test runs. The collections\n    contain tuples of (testcase, exceptioninfo), where exceptioninfo is the\n    formatted traceback of the error that occurred.\n    "));
      var1.setline(36);
      PyString.fromInterned("Holder for test result information.\n\n    Test results are automatically managed by the TestCase and TestSuite\n    classes, and do not need to be explicitly manipulated by writers of tests.\n\n    Each instance holds the total number of tests run, and collections of\n    failures and errors that occurred among those test runs. The collections\n    contain tuples of (testcase, exceptioninfo), where exceptioninfo is the\n    formatted traceback of the error that occurred.\n    ");
      var1.setline(37);
      PyObject var3 = var1.getname("None");
      var1.setlocal("_previousTestClass", var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getname("False");
      var1.setlocal("_testRunEntered", var3);
      var3 = null;
      var1.setline(39);
      var3 = var1.getname("False");
      var1.setlocal("_moduleSetUpFailed", var3);
      var3 = null;
      var1.setline(40);
      PyObject[] var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(56);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, printErrors$5, PyString.fromInterned("Called by TestRunner after test run"));
      var1.setlocal("printErrors", var5);
      var3 = null;
      var1.setline(59);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, startTest$6, PyString.fromInterned("Called when the given test is about to be run"));
      var1.setlocal("startTest", var5);
      var3 = null;
      var1.setline(65);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _setupStdout$7, (PyObject)null);
      var1.setlocal("_setupStdout", var5);
      var3 = null;
      var1.setline(73);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, startTestRun$8, PyString.fromInterned("Called once before any tests are executed.\n\n        See startTest for a method called before each test.\n        "));
      var1.setlocal("startTestRun", var5);
      var3 = null;
      var1.setline(79);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, stopTest$9, PyString.fromInterned("Called when the given test has been run"));
      var1.setlocal("stopTest", var5);
      var3 = null;
      var1.setline(84);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _restoreStdout$10, (PyObject)null);
      var1.setlocal("_restoreStdout", var5);
      var3 = null;
      var1.setline(105);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, stopTestRun$11, PyString.fromInterned("Called once after all tests are executed.\n\n        See stopTest for a method called after each test.\n        "));
      var1.setlocal("stopTestRun", var5);
      var3 = null;
      var1.setline(111);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addError$12, PyString.fromInterned("Called when an error has occurred. 'err' is a tuple of values as\n        returned by sys.exc_info().\n        "));
      var3 = var1.getname("failfast").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("addError", var3);
      var3 = null;
      var1.setline(119);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addFailure$13, PyString.fromInterned("Called when an error has occurred. 'err' is a tuple of values as\n        returned by sys.exc_info()."));
      var3 = var1.getname("failfast").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("addFailure", var3);
      var3 = null;
      var1.setline(126);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addSuccess$14, PyString.fromInterned("Called when a test has completed successfully"));
      var1.setlocal("addSuccess", var5);
      var3 = null;
      var1.setline(130);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addSkip$15, PyString.fromInterned("Called when a test is skipped."));
      var1.setlocal("addSkip", var5);
      var3 = null;
      var1.setline(134);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addExpectedFailure$16, PyString.fromInterned("Called when an expected failure/error occured."));
      var1.setlocal("addExpectedFailure", var5);
      var3 = null;
      var1.setline(139);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addUnexpectedSuccess$17, PyString.fromInterned("Called when a test was expected to fail, but succeed."));
      var3 = var1.getname("failfast").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("addUnexpectedSuccess", var3);
      var3 = null;
      var1.setline(144);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, wasSuccessful$18, PyString.fromInterned("Tells whether or not this result was a success"));
      var1.setlocal("wasSuccessful", var5);
      var3 = null;
      var1.setline(148);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, stop$19, PyString.fromInterned("Indicates that the tests should be aborted"));
      var1.setlocal("stop", var5);
      var3 = null;
      var1.setline(152);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _exc_info_to_string$20, PyString.fromInterned("Converts a sys.exc_info()-style tuple of values into a string."));
      var1.setlocal("_exc_info_to_string", var5);
      var3 = null;
      var1.setline(180);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _is_relevant_tb_level$21, (PyObject)null);
      var1.setlocal("_is_relevant_tb_level", var5);
      var3 = null;
      var1.setline(183);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _count_relevant_tb_levels$22, (PyObject)null);
      var1.setlocal("_count_relevant_tb_levels", var5);
      var3 = null;
      var1.setline(190);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$23, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("failfast", var3);
      var3 = null;
      var1.setline(42);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"failures", var4);
      var3 = null;
      var1.setline(43);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"errors", var4);
      var3 = null;
      var1.setline(44);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"testsRun", var5);
      var3 = null;
      var1.setline(45);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"skipped", var4);
      var3 = null;
      var1.setline(46);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"expectedFailures", var4);
      var3 = null;
      var1.setline(47);
      var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"unexpectedSuccesses", var4);
      var3 = null;
      var1.setline(48);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("shouldStop", var3);
      var3 = null;
      var1.setline(49);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("buffer", var3);
      var3 = null;
      var1.setline(50);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_stdout_buffer", var3);
      var3 = null;
      var1.setline(51);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_stderr_buffer", var3);
      var3 = null;
      var1.setline(52);
      var3 = var1.getglobal("sys").__getattr__("stdout");
      var1.getlocal(0).__setattr__("_original_stdout", var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getglobal("sys").__getattr__("stderr");
      var1.getlocal(0).__setattr__("_original_stderr", var3);
      var3 = null;
      var1.setline(54);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_mirrorOutput", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject printErrors$5(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyString.fromInterned("Called by TestRunner after test run");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startTest$6(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned("Called when the given test is about to be run");
      var1.setline(61);
      PyObject var10000 = var1.getlocal(0);
      String var3 = "testsRun";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(62);
      PyObject var6 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_mirrorOutput", var6);
      var3 = null;
      var1.setline(63);
      var1.getlocal(0).__getattr__("_setupStdout").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _setupStdout$7(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      if (var1.getlocal(0).__getattr__("buffer").__nonzero__()) {
         var1.setline(67);
         PyObject var3 = var1.getlocal(0).__getattr__("_stderr_buffer");
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(68);
            var3 = var1.getglobal("StringIO").__call__(var2);
            var1.getlocal(0).__setattr__("_stderr_buffer", var3);
            var3 = null;
            var1.setline(69);
            var3 = var1.getglobal("StringIO").__call__(var2);
            var1.getlocal(0).__setattr__("_stdout_buffer", var3);
            var3 = null;
         }

         var1.setline(70);
         var3 = var1.getlocal(0).__getattr__("_stdout_buffer");
         var1.getglobal("sys").__setattr__("stdout", var3);
         var3 = null;
         var1.setline(71);
         var3 = var1.getlocal(0).__getattr__("_stderr_buffer");
         var1.getglobal("sys").__setattr__("stderr", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startTestRun$8(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyString.fromInterned("Called once before any tests are executed.\n\n        See startTest for a method called before each test.\n        ");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject stopTest$9(PyFrame var1, ThreadState var2) {
      var1.setline(80);
      PyString.fromInterned("Called when the given test has been run");
      var1.setline(81);
      var1.getlocal(0).__getattr__("_restoreStdout").__call__(var2);
      var1.setline(82);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("_mirrorOutput", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _restoreStdout$10(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      if (var1.getlocal(0).__getattr__("buffer").__nonzero__()) {
         var1.setline(86);
         PyObject var3;
         if (var1.getlocal(0).__getattr__("_mirrorOutput").__nonzero__()) {
            var1.setline(87);
            var3 = var1.getglobal("sys").__getattr__("stdout").__getattr__("getvalue").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(88);
            var3 = var1.getglobal("sys").__getattr__("stderr").__getattr__("getvalue").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(89);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(90);
               if (var1.getlocal(1).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__not__().__nonzero__()) {
                  var1.setline(91);
                  var3 = var1.getlocal(1);
                  var3 = var3._iadd(PyString.fromInterned("\n"));
                  var1.setlocal(1, var3);
               }

               var1.setline(92);
               var1.getlocal(0).__getattr__("_original_stdout").__getattr__("write").__call__(var2, var1.getglobal("STDOUT_LINE")._mod(var1.getlocal(1)));
            }

            var1.setline(93);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(94);
               if (var1.getlocal(2).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__not__().__nonzero__()) {
                  var1.setline(95);
                  var3 = var1.getlocal(2);
                  var3 = var3._iadd(PyString.fromInterned("\n"));
                  var1.setlocal(2, var3);
               }

               var1.setline(96);
               var1.getlocal(0).__getattr__("_original_stderr").__getattr__("write").__call__(var2, var1.getglobal("STDERR_LINE")._mod(var1.getlocal(2)));
            }
         }

         var1.setline(98);
         var3 = var1.getlocal(0).__getattr__("_original_stdout");
         var1.getglobal("sys").__setattr__("stdout", var3);
         var3 = null;
         var1.setline(99);
         var3 = var1.getlocal(0).__getattr__("_original_stderr");
         var1.getglobal("sys").__setattr__("stderr", var3);
         var3 = null;
         var1.setline(100);
         var1.getlocal(0).__getattr__("_stdout_buffer").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(101);
         var1.getlocal(0).__getattr__("_stdout_buffer").__getattr__("truncate").__call__(var2);
         var1.setline(102);
         var1.getlocal(0).__getattr__("_stderr_buffer").__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(103);
         var1.getlocal(0).__getattr__("_stderr_buffer").__getattr__("truncate").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject stopTestRun$11(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyString.fromInterned("Called once after all tests are executed.\n\n        See stopTest for a method called after each test.\n        ");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addError$12(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyString.fromInterned("Called when an error has occurred. 'err' is a tuple of values as\n        returned by sys.exc_info().\n        ");
      var1.setline(116);
      var1.getlocal(0).__getattr__("errors").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("_exc_info_to_string").__call__(var2, var1.getlocal(2), var1.getlocal(1))})));
      var1.setline(117);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_mirrorOutput", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addFailure$13(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      PyString.fromInterned("Called when an error has occurred. 'err' is a tuple of values as\n        returned by sys.exc_info().");
      var1.setline(123);
      var1.getlocal(0).__getattr__("failures").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("_exc_info_to_string").__call__(var2, var1.getlocal(2), var1.getlocal(1))})));
      var1.setline(124);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("_mirrorOutput", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addSuccess$14(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyString.fromInterned("Called when a test has completed successfully");
      var1.setline(128);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addSkip$15(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyString.fromInterned("Called when a test is skipped.");
      var1.setline(132);
      var1.getlocal(0).__getattr__("skipped").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addExpectedFailure$16(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      PyString.fromInterned("Called when an expected failure/error occured.");
      var1.setline(136);
      var1.getlocal(0).__getattr__("expectedFailures").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("_exc_info_to_string").__call__(var2, var1.getlocal(2), var1.getlocal(1))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addUnexpectedSuccess$17(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyString.fromInterned("Called when a test was expected to fail, but succeed.");
      var1.setline(142);
      var1.getlocal(0).__getattr__("unexpectedSuccesses").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject wasSuccessful$18(PyFrame var1, ThreadState var2) {
      var1.setline(145);
      PyString.fromInterned("Tells whether or not this result was a success");
      var1.setline(146);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("failures"));
      PyObject var10001 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("errors"));
      PyObject var10000 = var3;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._eq(var10001)).__nonzero__()) {
         var4 = var3._eq(Py.newInteger(0));
      }

      var3 = null;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject stop$19(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyString.fromInterned("Indicates that the tests should be aborted");
      var1.setline(150);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("shouldStop", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _exc_info_to_string$20(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyString.fromInterned("Converts a sys.exc_info()-style tuple of values into a string.");
      var1.setline(154);
      PyObject var3 = var1.getlocal(1);
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

      while(true) {
         var1.setline(156);
         PyObject var10000 = var1.getlocal(5);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_is_relevant_tb_level").__call__(var2, var1.getlocal(5));
         }

         if (!var10000.__nonzero__()) {
            var1.setline(159);
            var3 = var1.getlocal(3);
            var10000 = var3._is(var1.getlocal(2).__getattr__("failureException"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(161);
               var3 = var1.getlocal(0).__getattr__("_count_relevant_tb_levels").__call__(var2, var1.getlocal(5));
               var1.setlocal(6, var3);
               var3 = null;
               var1.setline(162);
               var3 = var1.getglobal("traceback").__getattr__("format_exception").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6));
               var1.setlocal(7, var3);
               var3 = null;
            } else {
               var1.setline(164);
               var3 = var1.getglobal("traceback").__getattr__("format_exception").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(5));
               var1.setlocal(7, var3);
               var3 = null;
            }

            var1.setline(166);
            if (var1.getlocal(0).__getattr__("buffer").__nonzero__()) {
               var1.setline(167);
               var3 = var1.getglobal("sys").__getattr__("stdout").__getattr__("getvalue").__call__(var2);
               var1.setlocal(8, var3);
               var3 = null;
               var1.setline(168);
               var3 = var1.getglobal("sys").__getattr__("stderr").__getattr__("getvalue").__call__(var2);
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(169);
               if (var1.getlocal(8).__nonzero__()) {
                  var1.setline(170);
                  if (var1.getlocal(8).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__not__().__nonzero__()) {
                     var1.setline(171);
                     var3 = var1.getlocal(8);
                     var3 = var3._iadd(PyString.fromInterned("\n"));
                     var1.setlocal(8, var3);
                  }

                  var1.setline(172);
                  var1.getlocal(7).__getattr__("append").__call__(var2, var1.getglobal("STDOUT_LINE")._mod(var1.getlocal(8)));
               }

               var1.setline(173);
               if (var1.getlocal(9).__nonzero__()) {
                  var1.setline(174);
                  if (var1.getlocal(9).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__not__().__nonzero__()) {
                     var1.setline(175);
                     var3 = var1.getlocal(9);
                     var3 = var3._iadd(PyString.fromInterned("\n"));
                     var1.setlocal(9, var3);
                  }

                  var1.setline(176);
                  var1.getlocal(7).__getattr__("append").__call__(var2, var1.getglobal("STDERR_LINE")._mod(var1.getlocal(9)));
               }
            }

            var1.setline(177);
            var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(7));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(157);
         var3 = var1.getlocal(5).__getattr__("tb_next");
         var1.setlocal(5, var3);
         var3 = null;
      }
   }

   public PyObject _is_relevant_tb_level$21(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyString var3 = PyString.fromInterned("__unittest");
      PyObject var10000 = var3._in(var1.getlocal(1).__getattr__("tb_frame").__getattr__("f_globals"));
      var3 = null;
      PyObject var4 = var10000;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _count_relevant_tb_levels$22(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(185);
         PyObject var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("_is_relevant_tb_level").__call__(var2, var1.getlocal(1)).__not__();
         }

         PyObject var4;
         if (!var10000.__nonzero__()) {
            var1.setline(188);
            var4 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var4;
         }

         var1.setline(186);
         var4 = var1.getlocal(2);
         var4 = var4._iadd(Py.newInteger(1));
         var1.setlocal(2, var4);
         var1.setline(187);
         var4 = var1.getlocal(1).__getattr__("tb_next");
         var1.setlocal(1, var4);
         var3 = null;
      }
   }

   public PyObject __repr__$23(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      PyObject var3 = PyString.fromInterned("<%s run=%i errors=%i failures=%i>")._mod(new PyTuple(new PyObject[]{var1.getglobal("util").__getattr__("strclass").__call__(var2, var1.getlocal(0).__getattr__("__class__")), var1.getlocal(0).__getattr__("testsRun"), var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("errors")), var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("failures"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public result$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"method", "inner"};
      String[] var10001 = var2;
      result$py var10007 = self;
      var2 = new String[]{"method"};
      failfast$1 = Py.newCode(1, var10001, var1, "failfast", 14, false, false, var10007, 1, var2, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kw"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"method"};
      inner$2 = Py.newCode(3, var10001, var1, "inner", 15, true, true, var10007, 2, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      TestResult$3 = Py.newCode(0, var2, var1, "TestResult", 26, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "descriptions", "verbosity"};
      __init__$4 = Py.newCode(4, var2, var1, "__init__", 40, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      printErrors$5 = Py.newCode(1, var2, var1, "printErrors", 56, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      startTest$6 = Py.newCode(2, var2, var1, "startTest", 59, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _setupStdout$7 = Py.newCode(1, var2, var1, "_setupStdout", 65, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      startTestRun$8 = Py.newCode(1, var2, var1, "startTestRun", 73, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      stopTest$9 = Py.newCode(2, var2, var1, "stopTest", 79, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "output", "error"};
      _restoreStdout$10 = Py.newCode(1, var2, var1, "_restoreStdout", 84, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      stopTestRun$11 = Py.newCode(1, var2, var1, "stopTestRun", 105, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "err"};
      addError$12 = Py.newCode(3, var2, var1, "addError", 111, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "err"};
      addFailure$13 = Py.newCode(3, var2, var1, "addFailure", 119, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      addSuccess$14 = Py.newCode(2, var2, var1, "addSuccess", 126, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "reason"};
      addSkip$15 = Py.newCode(3, var2, var1, "addSkip", 130, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "err"};
      addExpectedFailure$16 = Py.newCode(3, var2, var1, "addExpectedFailure", 134, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      addUnexpectedSuccess$17 = Py.newCode(2, var2, var1, "addUnexpectedSuccess", 139, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      wasSuccessful$18 = Py.newCode(1, var2, var1, "wasSuccessful", 144, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      stop$19 = Py.newCode(1, var2, var1, "stop", 148, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "err", "test", "exctype", "value", "tb", "length", "msgLines", "output", "error"};
      _exc_info_to_string$20 = Py.newCode(3, var2, var1, "_exc_info_to_string", 152, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tb"};
      _is_relevant_tb_level$21 = Py.newCode(2, var2, var1, "_is_relevant_tb_level", 180, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tb", "length"};
      _count_relevant_tb_levels$22 = Py.newCode(2, var2, var1, "_count_relevant_tb_levels", 183, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$23 = Py.newCode(1, var2, var1, "__repr__", 190, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new result$py("unittest/result$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(result$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.failfast$1(var2, var3);
         case 2:
            return this.inner$2(var2, var3);
         case 3:
            return this.TestResult$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.printErrors$5(var2, var3);
         case 6:
            return this.startTest$6(var2, var3);
         case 7:
            return this._setupStdout$7(var2, var3);
         case 8:
            return this.startTestRun$8(var2, var3);
         case 9:
            return this.stopTest$9(var2, var3);
         case 10:
            return this._restoreStdout$10(var2, var3);
         case 11:
            return this.stopTestRun$11(var2, var3);
         case 12:
            return this.addError$12(var2, var3);
         case 13:
            return this.addFailure$13(var2, var3);
         case 14:
            return this.addSuccess$14(var2, var3);
         case 15:
            return this.addSkip$15(var2, var3);
         case 16:
            return this.addExpectedFailure$16(var2, var3);
         case 17:
            return this.addUnexpectedSuccess$17(var2, var3);
         case 18:
            return this.wasSuccessful$18(var2, var3);
         case 19:
            return this.stop$19(var2, var3);
         case 20:
            return this._exc_info_to_string$20(var2, var3);
         case 21:
            return this._is_relevant_tb_level$21(var2, var3);
         case 22:
            return this._count_relevant_tb_levels$22(var2, var3);
         case 23:
            return this.__repr__$23(var2, var3);
         default:
            return null;
      }
   }
}
