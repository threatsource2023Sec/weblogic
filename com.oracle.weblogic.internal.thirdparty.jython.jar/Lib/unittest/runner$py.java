package unittest;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
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
@Filename("unittest/runner.py")
public class runner$py extends PyFunctionTable implements PyRunnable {
   static runner$py self;
   static final PyCode f$0;
   static final PyCode _WritelnDecorator$1;
   static final PyCode __init__$2;
   static final PyCode __getattr__$3;
   static final PyCode writeln$4;
   static final PyCode TextTestResult$5;
   static final PyCode __init__$6;
   static final PyCode getDescription$7;
   static final PyCode startTest$8;
   static final PyCode addSuccess$9;
   static final PyCode addError$10;
   static final PyCode addFailure$11;
   static final PyCode addSkip$12;
   static final PyCode addExpectedFailure$13;
   static final PyCode addUnexpectedSuccess$14;
   static final PyCode printErrors$15;
   static final PyCode printErrorList$16;
   static final PyCode TextTestRunner$17;
   static final PyCode __init__$18;
   static final PyCode _makeResult$19;
   static final PyCode run$20;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Running tests"));
      var1.setline(1);
      PyString.fromInterned("Running tests");
      var1.setline(3);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(6);
      String[] var5 = new String[]{"result"};
      PyObject[] var6 = imp.importFrom("", var5, var1, 1);
      PyObject var4 = var6[0];
      var1.setlocal("result", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"registerResult"};
      var6 = imp.importFrom("signals", var5, var1, 1);
      var4 = var6[0];
      var1.setlocal("registerResult", var4);
      var4 = null;
      var1.setline(9);
      var3 = var1.getname("True");
      var1.setlocal("__unittest", var3);
      var3 = null;
      var1.setline(12);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_WritelnDecorator", var6, _WritelnDecorator$1);
      var1.setlocal("_WritelnDecorator", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(28);
      var6 = new PyObject[]{var1.getname("result").__getattr__("TestResult")};
      var4 = Py.makeClass("TextTestResult", var6, TextTestResult$5);
      var1.setlocal("TextTestResult", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(119);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TextTestRunner", var6, TextTestRunner$17);
      var1.setlocal("TextTestRunner", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _WritelnDecorator$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Used to decorate file-like objects with a handy 'writeln' method"));
      var1.setline(13);
      PyString.fromInterned("Used to decorate file-like objects with a handy 'writeln' method");
      var1.setline(14);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(17);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$3, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(22);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, writeln$4, (PyObject)null);
      var1.setlocal("writeln", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$3(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("stream"), PyString.fromInterned("__getstate__")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(19);
         throw Py.makeException(var1.getglobal("AttributeError").__call__(var2, var1.getlocal(1)));
      } else {
         var1.setline(20);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("stream"), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject writeln$4(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(24);
         var1.getlocal(0).__getattr__("write").__call__(var2, var1.getlocal(1));
      }

      var1.setline(25);
      var1.getlocal(0).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TextTestResult$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A test result class that can print formatted text results to a stream.\n\n    Used by TextTestRunner.\n    "));
      var1.setline(32);
      PyString.fromInterned("A test result class that can print formatted text results to a stream.\n\n    Used by TextTestRunner.\n    ");
      var1.setline(33);
      PyObject var3 = PyString.fromInterned("=")._mul(Py.newInteger(70));
      var1.setlocal("separator1", var3);
      var3 = null;
      var1.setline(34);
      var3 = PyString.fromInterned("-")._mul(Py.newInteger(70));
      var1.setlocal("separator2", var3);
      var3 = null;
      var1.setline(36);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$6, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(43);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, getDescription$7, (PyObject)null);
      var1.setlocal("getDescription", var5);
      var3 = null;
      var1.setline(50);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, startTest$8, (PyObject)null);
      var1.setlocal("startTest", var5);
      var3 = null;
      var1.setline(57);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addSuccess$9, (PyObject)null);
      var1.setlocal("addSuccess", var5);
      var3 = null;
      var1.setline(65);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addError$10, (PyObject)null);
      var1.setlocal("addError", var5);
      var3 = null;
      var1.setline(73);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addFailure$11, (PyObject)null);
      var1.setlocal("addFailure", var5);
      var3 = null;
      var1.setline(81);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addSkip$12, (PyObject)null);
      var1.setlocal("addSkip", var5);
      var3 = null;
      var1.setline(89);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addExpectedFailure$13, (PyObject)null);
      var1.setlocal("addExpectedFailure", var5);
      var3 = null;
      var1.setline(97);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addUnexpectedSuccess$14, (PyObject)null);
      var1.setlocal("addUnexpectedSuccess", var5);
      var3 = null;
      var1.setline(105);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, printErrors$15, (PyObject)null);
      var1.setlocal("printErrors", var5);
      var3 = null;
      var1.setline(111);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, printErrorList$16, (PyObject)null);
      var1.setlocal("printErrorList", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$6(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      var1.getglobal("super").__call__(var2, var1.getglobal("TextTestResult"), var1.getlocal(0)).__getattr__("__init__").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(38);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.setline(39);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("showAll", var3);
      var3 = null;
      var1.setline(40);
      var3 = var1.getlocal(3);
      var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      var3 = var10000;
      var1.getlocal(0).__setattr__("dots", var3);
      var3 = null;
      var1.setline(41);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("descriptions", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getDescription$7(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyObject var3 = var1.getlocal(1).__getattr__("shortDescription").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(45);
      PyObject var10000 = var1.getlocal(0).__getattr__("descriptions");
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2);
      }

      if (var10000.__nonzero__()) {
         var1.setline(46);
         var3 = PyString.fromInterned("\n").__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("str").__call__(var2, var1.getlocal(1)), var1.getlocal(2)})));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(48);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject startTest$8(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      var1.getglobal("super").__call__(var2, var1.getglobal("TextTestResult"), var1.getlocal(0)).__getattr__("startTest").__call__(var2, var1.getlocal(1));
      var1.setline(52);
      if (var1.getlocal(0).__getattr__("showAll").__nonzero__()) {
         var1.setline(53);
         var1.getlocal(0).__getattr__("stream").__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("getDescription").__call__(var2, var1.getlocal(1)));
         var1.setline(54);
         var1.getlocal(0).__getattr__("stream").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" ... "));
         var1.setline(55);
         var1.getlocal(0).__getattr__("stream").__getattr__("flush").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addSuccess$9(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      var1.getglobal("super").__call__(var2, var1.getglobal("TextTestResult"), var1.getlocal(0)).__getattr__("addSuccess").__call__(var2, var1.getlocal(1));
      var1.setline(59);
      if (var1.getlocal(0).__getattr__("showAll").__nonzero__()) {
         var1.setline(60);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ok"));
      } else {
         var1.setline(61);
         if (var1.getlocal(0).__getattr__("dots").__nonzero__()) {
            var1.setline(62);
            var1.getlocal(0).__getattr__("stream").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
            var1.setline(63);
            var1.getlocal(0).__getattr__("stream").__getattr__("flush").__call__(var2);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addError$10(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      var1.getglobal("super").__call__(var2, var1.getglobal("TextTestResult"), var1.getlocal(0)).__getattr__("addError").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(67);
      if (var1.getlocal(0).__getattr__("showAll").__nonzero__()) {
         var1.setline(68);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ERROR"));
      } else {
         var1.setline(69);
         if (var1.getlocal(0).__getattr__("dots").__nonzero__()) {
            var1.setline(70);
            var1.getlocal(0).__getattr__("stream").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("E"));
            var1.setline(71);
            var1.getlocal(0).__getattr__("stream").__getattr__("flush").__call__(var2);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addFailure$11(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      var1.getglobal("super").__call__(var2, var1.getglobal("TextTestResult"), var1.getlocal(0)).__getattr__("addFailure").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(75);
      if (var1.getlocal(0).__getattr__("showAll").__nonzero__()) {
         var1.setline(76);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FAIL"));
      } else {
         var1.setline(77);
         if (var1.getlocal(0).__getattr__("dots").__nonzero__()) {
            var1.setline(78);
            var1.getlocal(0).__getattr__("stream").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("F"));
            var1.setline(79);
            var1.getlocal(0).__getattr__("stream").__getattr__("flush").__call__(var2);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addSkip$12(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      var1.getglobal("super").__call__(var2, var1.getglobal("TextTestResult"), var1.getlocal(0)).__getattr__("addSkip").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(83);
      if (var1.getlocal(0).__getattr__("showAll").__nonzero__()) {
         var1.setline(84);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__(var2, PyString.fromInterned("skipped {0!r}").__getattr__("format").__call__(var2, var1.getlocal(2)));
      } else {
         var1.setline(85);
         if (var1.getlocal(0).__getattr__("dots").__nonzero__()) {
            var1.setline(86);
            var1.getlocal(0).__getattr__("stream").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("s"));
            var1.setline(87);
            var1.getlocal(0).__getattr__("stream").__getattr__("flush").__call__(var2);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addExpectedFailure$13(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      var1.getglobal("super").__call__(var2, var1.getglobal("TextTestResult"), var1.getlocal(0)).__getattr__("addExpectedFailure").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(91);
      if (var1.getlocal(0).__getattr__("showAll").__nonzero__()) {
         var1.setline(92);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("expected failure"));
      } else {
         var1.setline(93);
         if (var1.getlocal(0).__getattr__("dots").__nonzero__()) {
            var1.setline(94);
            var1.getlocal(0).__getattr__("stream").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("x"));
            var1.setline(95);
            var1.getlocal(0).__getattr__("stream").__getattr__("flush").__call__(var2);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addUnexpectedSuccess$14(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      var1.getglobal("super").__call__(var2, var1.getglobal("TextTestResult"), var1.getlocal(0)).__getattr__("addUnexpectedSuccess").__call__(var2, var1.getlocal(1));
      var1.setline(99);
      if (var1.getlocal(0).__getattr__("showAll").__nonzero__()) {
         var1.setline(100);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unexpected success"));
      } else {
         var1.setline(101);
         if (var1.getlocal(0).__getattr__("dots").__nonzero__()) {
            var1.setline(102);
            var1.getlocal(0).__getattr__("stream").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("u"));
            var1.setline(103);
            var1.getlocal(0).__getattr__("stream").__getattr__("flush").__call__(var2);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject printErrors$15(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var10000 = var1.getlocal(0).__getattr__("dots");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("showAll");
      }

      if (var10000.__nonzero__()) {
         var1.setline(107);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__(var2);
      }

      var1.setline(108);
      var1.getlocal(0).__getattr__("printErrorList").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ERROR"), (PyObject)var1.getlocal(0).__getattr__("errors"));
      var1.setline(109);
      var1.getlocal(0).__getattr__("printErrorList").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FAIL"), (PyObject)var1.getlocal(0).__getattr__("failures"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject printErrorList$16(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(112);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(113);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__(var2, var1.getlocal(0).__getattr__("separator1"));
         var1.setline(114);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__(var2, PyString.fromInterned("%s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("getDescription").__call__(var2, var1.getlocal(3))})));
         var1.setline(115);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__(var2, var1.getlocal(0).__getattr__("separator2"));
         var1.setline(116);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__(var2, PyString.fromInterned("%s")._mod(var1.getlocal(4)));
      }
   }

   public PyObject TextTestRunner$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A test runner class that displays results in textual form.\n\n    It prints out the names of tests as they are run, errors as they\n    occur, and a summary of the results at the end of the test run.\n    "));
      var1.setline(124);
      PyString.fromInterned("A test runner class that displays results in textual form.\n\n    It prints out the names of tests as they are run, errors as they\n    occur, and a summary of the results at the end of the test run.\n    ");
      var1.setline(125);
      PyObject var3 = var1.getname("TextTestResult");
      var1.setlocal("resultclass", var3);
      var3 = null;
      var1.setline(127);
      PyObject[] var4 = new PyObject[]{var1.getname("sys").__getattr__("stderr"), var1.getname("True"), Py.newInteger(1), var1.getname("False"), var1.getname("False"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$18, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(137);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _makeResult$19, (PyObject)null);
      var1.setlocal("_makeResult", var5);
      var3 = null;
      var1.setline(140);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, run$20, PyString.fromInterned("Run the given test case or test suite."));
      var1.setlocal("run", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyObject var3 = var1.getglobal("_WritelnDecorator").__call__(var2, var1.getlocal(1));
      var1.getlocal(0).__setattr__("stream", var3);
      var3 = null;
      var1.setline(130);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("descriptions", var3);
      var3 = null;
      var1.setline(131);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("verbosity", var3);
      var3 = null;
      var1.setline(132);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("failfast", var3);
      var3 = null;
      var1.setline(133);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("buffer", var3);
      var3 = null;
      var1.setline(134);
      var3 = var1.getlocal(6);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(135);
         var3 = var1.getlocal(6);
         var1.getlocal(0).__setattr__("resultclass", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _makeResult$19(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3 = var1.getlocal(0).__getattr__("resultclass").__call__(var2, var1.getlocal(0).__getattr__("stream"), var1.getlocal(0).__getattr__("descriptions"), var1.getlocal(0).__getattr__("verbosity"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject run$20(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyString.fromInterned("Run the given test case or test suite.");
      var1.setline(142);
      PyObject var3 = var1.getlocal(0).__getattr__("_makeResult").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(143);
      var1.getglobal("registerResult").__call__(var2, var1.getlocal(2));
      var1.setline(144);
      var3 = var1.getlocal(0).__getattr__("failfast");
      var1.getlocal(2).__setattr__("failfast", var3);
      var3 = null;
      var1.setline(145);
      var3 = var1.getlocal(0).__getattr__("buffer");
      var1.getlocal(2).__setattr__("buffer", var3);
      var3 = null;
      var1.setline(146);
      var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(147);
      var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("startTestRun"), (PyObject)var1.getglobal("None"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(148);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(149);
         var1.getlocal(4).__call__(var2);
      }

      var3 = null;

      PyObject var4;
      try {
         var1.setline(151);
         var1.getlocal(1).__call__(var2, var1.getlocal(2));
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(153);
         var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("stopTestRun"), (PyObject)var1.getglobal("None"));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(154);
         var4 = var1.getlocal(5);
         var10000 = var4._isnot(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(155);
            var1.getlocal(5).__call__(var2);
         }

         throw (Throwable)var7;
      }

      var1.setline(153);
      var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("stopTestRun"), (PyObject)var1.getglobal("None"));
      var1.setlocal(5, var4);
      var4 = null;
      var1.setline(154);
      var4 = var1.getlocal(5);
      var10000 = var4._isnot(var1.getglobal("None"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(155);
         var1.getlocal(5).__call__(var2);
      }

      var1.setline(156);
      var3 = var1.getglobal("time").__getattr__("time").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(157);
      var3 = var1.getlocal(6)._sub(var1.getlocal(3));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(158);
      var1.getlocal(2).__getattr__("printErrors").__call__(var2);
      var1.setline(159);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("separator2")).__nonzero__()) {
         var1.setline(160);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__(var2, var1.getlocal(2).__getattr__("separator2"));
      }

      var1.setline(161);
      var3 = var1.getlocal(2).__getattr__("testsRun");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(162);
      var10000 = var1.getlocal(0).__getattr__("stream").__getattr__("writeln");
      PyString var10002 = PyString.fromInterned("Ran %d test%s in %.3fs");
      PyTuple var10003 = new PyTuple;
      PyObject[] var10005 = new PyObject[]{var1.getlocal(8), null, null};
      var3 = var1.getlocal(8);
      Object var10008 = var3._ne(Py.newInteger(1));
      var3 = null;
      if (((PyObject)var10008).__nonzero__()) {
         var10008 = PyString.fromInterned("s");
      }

      if (!((PyObject)var10008).__nonzero__()) {
         var10008 = PyString.fromInterned("");
      }

      var10005[1] = (PyObject)var10008;
      var10005[2] = var1.getlocal(7);
      var10003.<init>(var10005);
      var10000.__call__(var2, var10002._mod(var10003));
      var1.setline(164);
      var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__(var2);
      var1.setline(166);
      PyInteger var11 = Py.newInteger(0);
      var1.setlocal(9, var11);
      var1.setlocal(10, var11);
      var1.setlocal(11, var11);

      PyObject[] var5;
      label74: {
         try {
            var1.setline(168);
            var3 = var1.getglobal("map").__call__((ThreadState)var2, (PyObject)var1.getglobal("len"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("expectedFailures"), var1.getlocal(2).__getattr__("unexpectedSuccesses"), var1.getlocal(2).__getattr__("skipped")})));
            var1.setlocal(12, var3);
            var3 = null;
         } catch (Throwable var8) {
            PyException var12 = Py.setException(var8, var1);
            if (var12.match(var1.getglobal("AttributeError"))) {
               var1.setline(172);
               break label74;
            }

            throw var12;
         }

         var1.setline(174);
         var4 = var1.getlocal(12);
         var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(9, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(10, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(11, var6);
         var6 = null;
         var4 = null;
      }

      var1.setline(176);
      PyList var13 = new PyList(Py.EmptyObjects);
      var1.setlocal(13, var13);
      var3 = null;
      var1.setline(177);
      if (var1.getlocal(2).__getattr__("wasSuccessful").__call__(var2).__not__().__nonzero__()) {
         var1.setline(178);
         var1.getlocal(0).__getattr__("stream").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FAILED"));
         var1.setline(179);
         var3 = var1.getglobal("map").__call__((ThreadState)var2, (PyObject)var1.getglobal("len"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2).__getattr__("failures"), var1.getlocal(2).__getattr__("errors")})));
         PyObject[] var10 = Py.unpackSequence(var3, 2);
         PyObject var9 = var10[0];
         var1.setlocal(14, var9);
         var5 = null;
         var9 = var10[1];
         var1.setlocal(15, var9);
         var5 = null;
         var3 = null;
         var1.setline(180);
         if (var1.getlocal(14).__nonzero__()) {
            var1.setline(181);
            var1.getlocal(13).__getattr__("append").__call__(var2, PyString.fromInterned("failures=%d")._mod(var1.getlocal(14)));
         }

         var1.setline(182);
         if (var1.getlocal(15).__nonzero__()) {
            var1.setline(183);
            var1.getlocal(13).__getattr__("append").__call__(var2, PyString.fromInterned("errors=%d")._mod(var1.getlocal(15)));
         }
      } else {
         var1.setline(185);
         var1.getlocal(0).__getattr__("stream").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("OK"));
      }

      var1.setline(186);
      if (var1.getlocal(11).__nonzero__()) {
         var1.setline(187);
         var1.getlocal(13).__getattr__("append").__call__(var2, PyString.fromInterned("skipped=%d")._mod(var1.getlocal(11)));
      }

      var1.setline(188);
      if (var1.getlocal(9).__nonzero__()) {
         var1.setline(189);
         var1.getlocal(13).__getattr__("append").__call__(var2, PyString.fromInterned("expected failures=%d")._mod(var1.getlocal(9)));
      }

      var1.setline(190);
      if (var1.getlocal(10).__nonzero__()) {
         var1.setline(191);
         var1.getlocal(13).__getattr__("append").__call__(var2, PyString.fromInterned("unexpected successes=%d")._mod(var1.getlocal(10)));
      }

      var1.setline(192);
      if (var1.getlocal(13).__nonzero__()) {
         var1.setline(193);
         var1.getlocal(0).__getattr__("stream").__getattr__("writeln").__call__(var2, PyString.fromInterned(" (%s)")._mod(new PyTuple(new PyObject[]{PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(13))})));
      } else {
         var1.setline(195);
         var1.getlocal(0).__getattr__("stream").__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      }

      var1.setline(196);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public runner$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _WritelnDecorator$1 = Py.newCode(0, var2, var1, "_WritelnDecorator", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 14, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attr"};
      __getattr__$3 = Py.newCode(2, var2, var1, "__getattr__", 17, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arg"};
      writeln$4 = Py.newCode(2, var2, var1, "writeln", 22, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TextTestResult$5 = Py.newCode(0, var2, var1, "TextTestResult", 28, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "descriptions", "verbosity"};
      __init__$6 = Py.newCode(4, var2, var1, "__init__", 36, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "doc_first_line"};
      getDescription$7 = Py.newCode(2, var2, var1, "getDescription", 43, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      startTest$8 = Py.newCode(2, var2, var1, "startTest", 50, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      addSuccess$9 = Py.newCode(2, var2, var1, "addSuccess", 57, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "err"};
      addError$10 = Py.newCode(3, var2, var1, "addError", 65, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "err"};
      addFailure$11 = Py.newCode(3, var2, var1, "addFailure", 73, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "reason"};
      addSkip$12 = Py.newCode(3, var2, var1, "addSkip", 81, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "err"};
      addExpectedFailure$13 = Py.newCode(3, var2, var1, "addExpectedFailure", 89, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      addUnexpectedSuccess$14 = Py.newCode(2, var2, var1, "addUnexpectedSuccess", 97, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      printErrors$15 = Py.newCode(1, var2, var1, "printErrors", 105, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "flavour", "errors", "test", "err"};
      printErrorList$16 = Py.newCode(3, var2, var1, "printErrorList", 111, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TextTestRunner$17 = Py.newCode(0, var2, var1, "TextTestRunner", 119, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "descriptions", "verbosity", "failfast", "buffer", "resultclass"};
      __init__$18 = Py.newCode(7, var2, var1, "__init__", 127, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      _makeResult$19 = Py.newCode(1, var2, var1, "_makeResult", 137, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "result", "startTime", "startTestRun", "stopTestRun", "stopTime", "timeTaken", "run", "expectedFails", "unexpectedSuccesses", "skipped", "results", "infos", "failed", "errored"};
      run$20 = Py.newCode(2, var2, var1, "run", 140, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new runner$py("unittest/runner$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(runner$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._WritelnDecorator$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__getattr__$3(var2, var3);
         case 4:
            return this.writeln$4(var2, var3);
         case 5:
            return this.TextTestResult$5(var2, var3);
         case 6:
            return this.__init__$6(var2, var3);
         case 7:
            return this.getDescription$7(var2, var3);
         case 8:
            return this.startTest$8(var2, var3);
         case 9:
            return this.addSuccess$9(var2, var3);
         case 10:
            return this.addError$10(var2, var3);
         case 11:
            return this.addFailure$11(var2, var3);
         case 12:
            return this.addSkip$12(var2, var3);
         case 13:
            return this.addExpectedFailure$13(var2, var3);
         case 14:
            return this.addUnexpectedSuccess$14(var2, var3);
         case 15:
            return this.printErrors$15(var2, var3);
         case 16:
            return this.printErrorList$16(var2, var3);
         case 17:
            return this.TextTestRunner$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this._makeResult$19(var2, var3);
         case 20:
            return this.run$20(var2, var3);
         default:
            return null;
      }
   }
}
