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
@Filename("unittest/suite.py")
public class suite$py extends PyFunctionTable implements PyRunnable {
   static suite$py self;
   static final PyCode f$0;
   static final PyCode _call_if_exists$1;
   static final PyCode f$2;
   static final PyCode BaseTestSuite$3;
   static final PyCode __init__$4;
   static final PyCode __repr__$5;
   static final PyCode __eq__$6;
   static final PyCode __ne__$7;
   static final PyCode __iter__$8;
   static final PyCode countTestCases$9;
   static final PyCode addTest$10;
   static final PyCode addTests$11;
   static final PyCode run$12;
   static final PyCode __call__$13;
   static final PyCode debug$14;
   static final PyCode TestSuite$15;
   static final PyCode run$16;
   static final PyCode debug$17;
   static final PyCode _handleClassSetUp$18;
   static final PyCode _get_previous_module$19;
   static final PyCode _handleModuleFixture$20;
   static final PyCode _addClassOrModuleLevelException$21;
   static final PyCode _handleModuleTearDown$22;
   static final PyCode _tearDownPreviousClass$23;
   static final PyCode _ErrorHolder$24;
   static final PyCode __init__$25;
   static final PyCode id$26;
   static final PyCode shortDescription$27;
   static final PyCode __repr__$28;
   static final PyCode __str__$29;
   static final PyCode run$30;
   static final PyCode __call__$31;
   static final PyCode countTestCases$32;
   static final PyCode _isnotsuite$33;
   static final PyCode _DebugResult$34;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("TestSuite"));
      var1.setline(1);
      PyString.fromInterned("TestSuite");
      var1.setline(3);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      String[] var5 = new String[]{"case"};
      PyObject[] var6 = imp.importFrom("", var5, var1, 1);
      PyObject var4 = var6[0];
      var1.setlocal("case", var4);
      var4 = null;
      var1.setline(6);
      var5 = new String[]{"util"};
      var6 = imp.importFrom("", var5, var1, 1);
      var4 = var6[0];
      var1.setlocal("util", var4);
      var4 = null;
      var1.setline(8);
      var3 = var1.getname("True");
      var1.setlocal("__unittest", var3);
      var3 = null;
      var1.setline(11);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, _call_if_exists$1, (PyObject)null);
      var1.setlocal("_call_if_exists", var7);
      var3 = null;
      var1.setline(16);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("BaseTestSuite", var6, BaseTestSuite$3);
      var1.setlocal("BaseTestSuite", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(78);
      var6 = new PyObject[]{var1.getname("BaseTestSuite")};
      var4 = Py.makeClass("TestSuite", var6, TestSuite$15);
      var1.setlocal("TestSuite", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(252);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_ErrorHolder", var6, _ErrorHolder$24);
      var1.setlocal("_ErrorHolder", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(290);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _isnotsuite$33, PyString.fromInterned("A crude way to tell apart testcases and suites with duck-typing"));
      var1.setlocal("_isnotsuite", var7);
      var3 = null;
      var1.setline(299);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_DebugResult", var6, _DebugResult$34);
      var1.setlocal("_DebugResult", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _call_if_exists$1(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      PyObject var10000 = var1.getglobal("getattr");
      PyObject var10002 = var1.getlocal(0);
      PyObject var10003 = var1.getlocal(1);
      var1.setline(12);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, var10002, (PyObject)var10003, (PyObject)(new PyFunction(var1.f_globals, var3, f$2)));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(13);
      var1.getlocal(2).__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$2(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject BaseTestSuite$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A simple test suite that doesn't provide class or module shared fixtures.\n    "));
      var1.setline(18);
      PyString.fromInterned("A simple test suite that doesn't provide class or module shared fixtures.\n    ");
      var1.setline(19);
      PyObject[] var3 = new PyObject[]{new PyTuple(Py.EmptyObjects)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(23);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$5, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$6, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(31);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$7, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(35);
      PyObject var5 = var1.getname("None");
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __iter__$8, (PyObject)null);
      var1.setlocal("__iter__", var4);
      var3 = null;
      var1.setline(40);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, countTestCases$9, (PyObject)null);
      var1.setlocal("countTestCases", var4);
      var3 = null;
      var1.setline(46);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addTest$10, (PyObject)null);
      var1.setlocal("addTest", var4);
      var3 = null;
      var1.setline(56);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addTests$11, (PyObject)null);
      var1.setlocal("addTests", var4);
      var3 = null;
      var1.setline(62);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, run$12, (PyObject)null);
      var1.setlocal("run", var4);
      var3 = null;
      var1.setline(69);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$13, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      var1.setline(72);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, debug$14, PyString.fromInterned("Run the tests without collecting errors in a TestResult"));
      var1.setlocal("debug", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_tests", var3);
      var3 = null;
      var1.setline(21);
      var1.getlocal(0).__getattr__("addTests").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$5(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = PyString.fromInterned("<%s tests=%s>")._mod(new PyTuple(new PyObject[]{var1.getglobal("util").__getattr__("strclass").__call__(var2, var1.getlocal(0).__getattr__("__class__")), var1.getglobal("list").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$6(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("__class__")).__not__().__nonzero__()) {
         var1.setline(28);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(29);
         PyObject var4 = var1.getglobal("list").__call__(var2, var1.getlocal(0));
         PyObject var10000 = var4._eq(var1.getglobal("list").__call__(var2, var1.getlocal(1)));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$7(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __iter__$8(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var3 = var1.getglobal("iter").__call__(var2, var1.getlocal(0).__getattr__("_tests"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject countTestCases$9(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(42);
      PyObject var6 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(42);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(44);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(43);
         PyObject var5 = var1.getlocal(1);
         var5 = var5._iadd(var1.getlocal(2).__getattr__("countTestCases").__call__(var2));
         var1.setlocal(1, var5);
      }
   }

   public PyObject addTest$10(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__call__")).__not__().__nonzero__()) {
         var1.setline(49);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("{} is not callable").__getattr__("format").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(1)))));
      } else {
         var1.setline(50);
         PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("type"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("issubclass").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("case").__getattr__("TestCase"), var1.getglobal("TestSuite")})));
         }

         if (var10000.__nonzero__()) {
            var1.setline(52);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestCases and TestSuites must be instantiated before passing them to addTest()")));
         } else {
            var1.setline(54);
            var1.getlocal(0).__getattr__("_tests").__getattr__("append").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject addTests$11(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(58);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tests must be an iterable of tests, not a string")));
      } else {
         var1.setline(59);
         PyObject var3 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(59);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(60);
            var1.getlocal(0).__getattr__("addTest").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject run$12(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyObject var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(63);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(2, var4);
         var1.setline(64);
         if (var1.getlocal(1).__getattr__("shouldStop").__nonzero__()) {
            break;
         }

         var1.setline(66);
         var1.getlocal(2).__call__(var2, var1.getlocal(1));
      }

      var1.setline(67);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __call__$13(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyObject var10000 = var1.getlocal(0).__getattr__("run");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject debug$14(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyString.fromInterned("Run the tests without collecting errors in a TestResult");
      var1.setline(74);
      PyObject var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(74);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(75);
         var1.getlocal(1).__getattr__("debug").__call__(var2);
      }
   }

   public PyObject TestSuite$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A test suite is a composite test consisting of a number of TestCases.\n\n    For use, create an instance of TestSuite, then add test case instances.\n    When all tests have been added, the suite can be passed to a test\n    runner, such as TextTestRunner. It will run the individual test cases\n    in the order in which they were added, aggregating the results. When\n    subclassing, do not forget to call the base class constructor.\n    "));
      var1.setline(86);
      PyString.fromInterned("A test suite is a composite test consisting of a number of TestCases.\n\n    For use, create an instance of TestSuite, then add test case instances.\n    When all tests have been added, the suite can be passed to a test\n    runner, such as TextTestRunner. It will run the individual test cases\n    in the order in which they were added, aggregating the results. When\n    subclassing, do not forget to call the base class constructor.\n    ");
      var1.setline(88);
      PyObject[] var3 = new PyObject[]{var1.getname("False")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, run$16, (PyObject)null);
      var1.setlocal("run", var4);
      var3 = null;
      var1.setline(118);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, debug$17, PyString.fromInterned("Run the tests without collecting errors in a TestResult"));
      var1.setlocal("debug", var4);
      var3 = null;
      var1.setline(125);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handleClassSetUp$18, (PyObject)null);
      var1.setlocal("_handleClassSetUp", var4);
      var3 = null;
      var1.setline(157);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _get_previous_module$19, (PyObject)null);
      var1.setlocal("_get_previous_module", var4);
      var3 = null;
      var1.setline(165);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handleModuleFixture$20, (PyObject)null);
      var1.setlocal("_handleModuleFixture", var4);
      var3 = null;
      var1.setline(192);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _addClassOrModuleLevelException$21, (PyObject)null);
      var1.setlocal("_addClassOrModuleLevelException", var4);
      var3 = null;
      var1.setline(200);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _handleModuleTearDown$22, (PyObject)null);
      var1.setlocal("_handleModuleTearDown", var4);
      var3 = null;
      var1.setline(225);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _tearDownPreviousClass$23, (PyObject)null);
      var1.setlocal("_tearDownPreviousClass", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject run$16(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyObject var3 = var1.getglobal("False");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(90);
      var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("_testRunEntered"), (PyObject)var1.getglobal("False"));
      PyObject var10000 = var3._is(var1.getglobal("False"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(91);
         var3 = var1.getglobal("True");
         var1.getlocal(1).__setattr__("_testRunEntered", var3);
         var1.setlocal(3, var3);
      }

      var1.setline(93);
      var3 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(93);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            break;
         }

         var1.setlocal(4, var4);
         var1.setline(94);
         if (var1.getlocal(1).__getattr__("shouldStop").__nonzero__()) {
            break;
         }

         var1.setline(97);
         if (var1.getglobal("_isnotsuite").__call__(var2, var1.getlocal(4)).__nonzero__()) {
            var1.setline(98);
            var1.getlocal(0).__getattr__("_tearDownPreviousClass").__call__(var2, var1.getlocal(4), var1.getlocal(1));
            var1.setline(99);
            var1.getlocal(0).__getattr__("_handleModuleFixture").__call__(var2, var1.getlocal(4), var1.getlocal(1));
            var1.setline(100);
            var1.getlocal(0).__getattr__("_handleClassSetUp").__call__(var2, var1.getlocal(4), var1.getlocal(1));
            var1.setline(101);
            PyObject var5 = var1.getlocal(4).__getattr__("__class__");
            var1.getlocal(1).__setattr__("_previousTestClass", var5);
            var5 = null;
            var1.setline(103);
            var10000 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(4).__getattr__("__class__"), (PyObject)PyString.fromInterned("_classSetupFailed"), (PyObject)var1.getglobal("False"));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("_moduleSetUpFailed"), (PyObject)var1.getglobal("False"));
            }

            if (var10000.__nonzero__()) {
               continue;
            }
         }

         var1.setline(107);
         if (var1.getlocal(2).__not__().__nonzero__()) {
            var1.setline(108);
            var1.getlocal(4).__call__(var2, var1.getlocal(1));
         } else {
            var1.setline(110);
            var1.getlocal(4).__getattr__("debug").__call__(var2);
         }
      }

      var1.setline(112);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(113);
         var1.getlocal(0).__getattr__("_tearDownPreviousClass").__call__(var2, var1.getglobal("None"), var1.getlocal(1));
         var1.setline(114);
         var1.getlocal(0).__getattr__("_handleModuleTearDown").__call__(var2, var1.getlocal(1));
         var1.setline(115);
         var3 = var1.getglobal("False");
         var1.getlocal(1).__setattr__("_testRunEntered", var3);
         var3 = null;
      }

      var1.setline(116);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject debug$17(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyString.fromInterned("Run the tests without collecting errors in a TestResult");
      var1.setline(120);
      PyObject var3 = var1.getglobal("_DebugResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(121);
      var1.getlocal(0).__getattr__("run").__call__(var2, var1.getlocal(1), var1.getglobal("True"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _handleClassSetUp$18(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("_previousTestClass"), (PyObject)var1.getglobal("None"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(127);
      var3 = var1.getlocal(1).__getattr__("__class__");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._eq(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(129);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(130);
         if (var1.getlocal(2).__getattr__("_moduleSetUpFailed").__nonzero__()) {
            var1.setline(131);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(132);
            if (var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)PyString.fromInterned("__unittest_skip__"), (PyObject)var1.getglobal("False")).__nonzero__()) {
               var1.setline(133);
               var1.f_lasti = -1;
               return Py.None;
            } else {
               try {
                  var1.setline(136);
                  var3 = var1.getglobal("False");
                  var1.getlocal(4).__setattr__("_classSetupFailed", var3);
                  var3 = null;
               } catch (Throwable var8) {
                  PyException var9 = Py.setException(var8, var1);
                  if (!var9.match(var1.getglobal("TypeError"))) {
                     throw var9;
                  }

                  var1.setline(140);
               }

               var1.setline(142);
               var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)PyString.fromInterned("setUpClass"), (PyObject)var1.getglobal("None"));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(143);
               var3 = var1.getlocal(5);
               var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(144);
                  var1.getglobal("_call_if_exists").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("_setupStdout"));
                  var3 = null;

                  try {
                     try {
                        var1.setline(146);
                        var1.getlocal(5).__call__(var2);
                     } catch (Throwable var6) {
                        PyException var4 = Py.setException(var6, var1);
                        if (!var4.match(var1.getglobal("Exception"))) {
                           throw var4;
                        }

                        PyObject var5 = var4.value;
                        var1.setlocal(6, var5);
                        var5 = null;
                        var1.setline(148);
                        if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("_DebugResult")).__nonzero__()) {
                           var1.setline(149);
                           throw Py.makeException();
                        }

                        var1.setline(150);
                        var5 = var1.getglobal("True");
                        var1.getlocal(4).__setattr__("_classSetupFailed", var5);
                        var5 = null;
                        var1.setline(151);
                        var5 = var1.getglobal("util").__getattr__("strclass").__call__(var2, var1.getlocal(4));
                        var1.setlocal(7, var5);
                        var5 = null;
                        var1.setline(152);
                        var5 = PyString.fromInterned("setUpClass (%s)")._mod(var1.getlocal(7));
                        var1.setlocal(8, var5);
                        var5 = null;
                        var1.setline(153);
                        var1.getlocal(0).__getattr__("_addClassOrModuleLevelException").__call__(var2, var1.getlocal(2), var1.getlocal(6), var1.getlocal(8));
                     }
                  } catch (Throwable var7) {
                     Py.addTraceback(var7, var1);
                     var1.setline(155);
                     var1.getglobal("_call_if_exists").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("_restoreStdout"));
                     throw (Throwable)var7;
                  }

                  var1.setline(155);
                  var1.getglobal("_call_if_exists").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("_restoreStdout"));
               }

               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject _get_previous_module$19(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var3 = var1.getglobal("None");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(159);
      var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("_previousTestClass"), (PyObject)var1.getglobal("None"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(160);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(161);
         var3 = var1.getlocal(3).__getattr__("__module__");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(162);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _handleModuleFixture$20(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_previous_module").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(167);
      var3 = var1.getlocal(1).__getattr__("__class__").__getattr__("__module__");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(168);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._eq(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(169);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(171);
         var1.getlocal(0).__getattr__("_handleModuleTearDown").__call__(var2, var1.getlocal(2));
         var1.setline(173);
         var3 = var1.getglobal("False");
         var1.getlocal(2).__setattr__("_moduleSetUpFailed", var3);
         var3 = null;

         try {
            var1.setline(175);
            var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(4));
            var1.setlocal(5, var3);
            var3 = null;
         } catch (Throwable var6) {
            PyException var9 = Py.setException(var6, var1);
            if (var9.match(var1.getglobal("KeyError"))) {
               var1.setline(177);
               var1.f_lasti = -1;
               return Py.None;
            }

            throw var9;
         }

         var1.setline(178);
         var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(5), (PyObject)PyString.fromInterned("setUpModule"), (PyObject)var1.getglobal("None"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(179);
         var3 = var1.getlocal(6);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(180);
            var1.getglobal("_call_if_exists").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("_setupStdout"));
            var3 = null;

            try {
               try {
                  var1.setline(182);
                  var1.getlocal(6).__call__(var2);
               } catch (Throwable var7) {
                  PyException var4 = Py.setException(var7, var1);
                  if (!var4.match(var1.getglobal("Exception"))) {
                     throw var4;
                  }

                  PyObject var5 = var4.value;
                  var1.setlocal(7, var5);
                  var5 = null;
                  var1.setline(184);
                  if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("_DebugResult")).__nonzero__()) {
                     var1.setline(185);
                     throw Py.makeException();
                  }

                  var1.setline(186);
                  var5 = var1.getglobal("True");
                  var1.getlocal(2).__setattr__("_moduleSetUpFailed", var5);
                  var5 = null;
                  var1.setline(187);
                  var5 = PyString.fromInterned("setUpModule (%s)")._mod(var1.getlocal(4));
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(188);
                  var1.getlocal(0).__getattr__("_addClassOrModuleLevelException").__call__(var2, var1.getlocal(2), var1.getlocal(7), var1.getlocal(8));
               }
            } catch (Throwable var8) {
               Py.addTraceback(var8, var1);
               var1.setline(190);
               var1.getglobal("_call_if_exists").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("_restoreStdout"));
               throw (Throwable)var8;
            }

            var1.setline(190);
            var1.getglobal("_call_if_exists").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("_restoreStdout"));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _addClassOrModuleLevelException$21(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyObject var3 = var1.getglobal("_ErrorHolder").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(194);
      var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("addSkip"), (PyObject)var1.getglobal("None"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(195);
      var3 = var1.getlocal(5);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("case").__getattr__("SkipTest"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(196);
         var1.getlocal(5).__call__(var2, var1.getlocal(4), var1.getglobal("str").__call__(var2, var1.getlocal(2)));
      } else {
         var1.setline(198);
         var1.getlocal(1).__getattr__("addError").__call__(var2, var1.getlocal(4), var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _handleModuleTearDown$22(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyObject var3 = var1.getlocal(0).__getattr__("_get_previous_module").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(202);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(203);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(204);
         if (var1.getlocal(1).__getattr__("_moduleSetUpFailed").__nonzero__()) {
            var1.setline(205);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            try {
               var1.setline(208);
               var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(2));
               var1.setlocal(3, var3);
               var3 = null;
            } catch (Throwable var6) {
               PyException var9 = Py.setException(var6, var1);
               if (var9.match(var1.getglobal("KeyError"))) {
                  var1.setline(210);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               throw var9;
            }

            var1.setline(212);
            var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("tearDownModule"), (PyObject)var1.getglobal("None"));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(213);
            var3 = var1.getlocal(4);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(214);
               var1.getglobal("_call_if_exists").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("_setupStdout"));
               var3 = null;

               try {
                  try {
                     var1.setline(216);
                     var1.getlocal(4).__call__(var2);
                  } catch (Throwable var7) {
                     PyException var4 = Py.setException(var7, var1);
                     if (!var4.match(var1.getglobal("Exception"))) {
                        throw var4;
                     }

                     PyObject var5 = var4.value;
                     var1.setlocal(5, var5);
                     var5 = null;
                     var1.setline(218);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("_DebugResult")).__nonzero__()) {
                        var1.setline(219);
                        throw Py.makeException();
                     }

                     var1.setline(220);
                     var5 = PyString.fromInterned("tearDownModule (%s)")._mod(var1.getlocal(2));
                     var1.setlocal(6, var5);
                     var5 = null;
                     var1.setline(221);
                     var1.getlocal(0).__getattr__("_addClassOrModuleLevelException").__call__(var2, var1.getlocal(1), var1.getlocal(5), var1.getlocal(6));
                  }
               } catch (Throwable var8) {
                  Py.addTraceback(var8, var1);
                  var1.setline(223);
                  var1.getglobal("_call_if_exists").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("_restoreStdout"));
                  throw (Throwable)var8;
               }

               var1.setline(223);
               var1.getglobal("_call_if_exists").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("_restoreStdout"));
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject _tearDownPreviousClass$23(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("_previousTestClass"), (PyObject)var1.getglobal("None"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(227);
      var3 = var1.getlocal(1).__getattr__("__class__");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(228);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._eq(var1.getlocal(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(229);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(230);
         if (var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("_classSetupFailed"), (PyObject)var1.getglobal("False")).__nonzero__()) {
            var1.setline(231);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(232);
            if (var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("_moduleSetUpFailed"), (PyObject)var1.getglobal("False")).__nonzero__()) {
               var1.setline(233);
               var1.f_lasti = -1;
               return Py.None;
            } else {
               var1.setline(234);
               if (var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("__unittest_skip__"), (PyObject)var1.getglobal("False")).__nonzero__()) {
                  var1.setline(235);
                  var1.f_lasti = -1;
                  return Py.None;
               } else {
                  var1.setline(237);
                  var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(3), (PyObject)PyString.fromInterned("tearDownClass"), (PyObject)var1.getglobal("None"));
                  var1.setlocal(5, var3);
                  var3 = null;
                  var1.setline(238);
                  var3 = var1.getlocal(5);
                  var10000 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(239);
                     var1.getglobal("_call_if_exists").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("_setupStdout"));
                     var3 = null;

                     try {
                        try {
                           var1.setline(241);
                           var1.getlocal(5).__call__(var2);
                        } catch (Throwable var6) {
                           PyException var4 = Py.setException(var6, var1);
                           if (!var4.match(var1.getglobal("Exception"))) {
                              throw var4;
                           }

                           PyObject var5 = var4.value;
                           var1.setlocal(6, var5);
                           var5 = null;
                           var1.setline(243);
                           if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("_DebugResult")).__nonzero__()) {
                              var1.setline(244);
                              throw Py.makeException();
                           }

                           var1.setline(245);
                           var5 = var1.getglobal("util").__getattr__("strclass").__call__(var2, var1.getlocal(3));
                           var1.setlocal(7, var5);
                           var5 = null;
                           var1.setline(246);
                           var5 = PyString.fromInterned("tearDownClass (%s)")._mod(var1.getlocal(7));
                           var1.setlocal(8, var5);
                           var5 = null;
                           var1.setline(247);
                           var1.getlocal(0).__getattr__("_addClassOrModuleLevelException").__call__(var2, var1.getlocal(2), var1.getlocal(6), var1.getlocal(8));
                        }
                     } catch (Throwable var7) {
                        Py.addTraceback(var7, var1);
                        var1.setline(249);
                        var1.getglobal("_call_if_exists").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("_restoreStdout"));
                        throw (Throwable)var7;
                     }

                     var1.setline(249);
                     var1.getglobal("_call_if_exists").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("_restoreStdout"));
                  }

                  var1.f_lasti = -1;
                  return Py.None;
               }
            }
         }
      }
   }

   public PyObject _ErrorHolder$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Placeholder for a TestCase inside a result. As far as a TestResult\n    is concerned, this looks exactly like a unit test. Used to insert\n    arbitrary errors into a test suite run.\n    "));
      var1.setline(257);
      PyString.fromInterned("\n    Placeholder for a TestCase inside a result. As far as a TestResult\n    is concerned, this looks exactly like a unit test. Used to insert\n    arbitrary errors into a test suite run.\n    ");
      var1.setline(262);
      PyObject var3 = var1.getname("None");
      var1.setlocal("failureException", var3);
      var3 = null;
      var1.setline(264);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$25, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(267);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, id$26, (PyObject)null);
      var1.setlocal("id", var5);
      var3 = null;
      var1.setline(270);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, shortDescription$27, (PyObject)null);
      var1.setlocal("shortDescription", var5);
      var3 = null;
      var1.setline(273);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$28, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(276);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __str__$29, (PyObject)null);
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(279);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, run$30, (PyObject)null);
      var1.setlocal("run", var5);
      var3 = null;
      var1.setline(284);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __call__$31, (PyObject)null);
      var1.setlocal("__call__", var5);
      var3 = null;
      var1.setline(287);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, countTestCases$32, (PyObject)null);
      var1.setlocal("countTestCases", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$25(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("description", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject id$26(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyObject var3 = var1.getlocal(0).__getattr__("description");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject shortDescription$27(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$28(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyObject var3 = PyString.fromInterned("<ErrorHolder description=%r>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("description")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$29(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      PyObject var3 = var1.getlocal(0).__getattr__("id").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject run$30(PyFrame var1, ThreadState var2) {
      var1.setline(282);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$31(PyFrame var1, ThreadState var2) {
      var1.setline(285);
      PyObject var3 = var1.getlocal(0).__getattr__("run").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject countTestCases$32(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyInteger var3 = Py.newInteger(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _isnotsuite$33(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyString.fromInterned("A crude way to tell apart testcases and suites with duck-typing");

      PyObject var4;
      try {
         var1.setline(293);
         var1.getglobal("iter").__call__(var2, var1.getlocal(0));
      } catch (Throwable var5) {
         PyException var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("TypeError"))) {
            var1.setline(295);
            var4 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var4;
         }

         throw var3;
      }

      var1.setline(296);
      var4 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _DebugResult$34(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Used by the TestSuite to hold previous class when running in debug."));
      var1.setline(300);
      PyString.fromInterned("Used by the TestSuite to hold previous class when running in debug.");
      var1.setline(301);
      PyObject var3 = var1.getname("None");
      var1.setlocal("_previousTestClass", var3);
      var3 = null;
      var1.setline(302);
      var3 = var1.getname("False");
      var1.setlocal("_moduleSetUpFailed", var3);
      var3 = null;
      var1.setline(303);
      var3 = var1.getname("False");
      var1.setlocal("shouldStop", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public suite$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"parent", "attr", "func"};
      _call_if_exists$1 = Py.newCode(2, var2, var1, "_call_if_exists", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$2 = Py.newCode(0, var2, var1, "<lambda>", 12, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BaseTestSuite$3 = Py.newCode(0, var2, var1, "BaseTestSuite", 16, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tests"};
      __init__$4 = Py.newCode(2, var2, var1, "__init__", 19, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$5 = Py.newCode(1, var2, var1, "__repr__", 23, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$6 = Py.newCode(2, var2, var1, "__eq__", 26, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$7 = Py.newCode(2, var2, var1, "__ne__", 31, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __iter__$8 = Py.newCode(1, var2, var1, "__iter__", 37, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cases", "test"};
      countTestCases$9 = Py.newCode(1, var2, var1, "countTestCases", 40, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      addTest$10 = Py.newCode(2, var2, var1, "addTest", 46, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tests", "test"};
      addTests$11 = Py.newCode(2, var2, var1, "addTests", 56, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "test"};
      run$12 = Py.newCode(2, var2, var1, "run", 62, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kwds"};
      __call__$13 = Py.newCode(3, var2, var1, "__call__", 69, true, true, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      debug$14 = Py.newCode(1, var2, var1, "debug", 72, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestSuite$15 = Py.newCode(0, var2, var1, "TestSuite", 78, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "result", "debug", "topLevel", "test"};
      run$16 = Py.newCode(3, var2, var1, "run", 88, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "debug"};
      debug$17 = Py.newCode(1, var2, var1, "debug", 118, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "result", "previousClass", "currentClass", "setUpClass", "e", "className", "errorName"};
      _handleClassSetUp$18 = Py.newCode(3, var2, var1, "_handleClassSetUp", 125, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "previousModule", "previousClass"};
      _get_previous_module$19 = Py.newCode(2, var2, var1, "_get_previous_module", 157, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "result", "previousModule", "currentModule", "module", "setUpModule", "e", "errorName"};
      _handleModuleFixture$20 = Py.newCode(3, var2, var1, "_handleModuleFixture", 165, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "exception", "errorName", "error", "addSkip"};
      _addClassOrModuleLevelException$21 = Py.newCode(4, var2, var1, "_addClassOrModuleLevelException", 192, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "previousModule", "module", "tearDownModule", "e", "errorName"};
      _handleModuleTearDown$22 = Py.newCode(2, var2, var1, "_handleModuleTearDown", 200, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "result", "previousClass", "currentClass", "tearDownClass", "e", "className", "errorName"};
      _tearDownPreviousClass$23 = Py.newCode(3, var2, var1, "_tearDownPreviousClass", 225, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _ErrorHolder$24 = Py.newCode(0, var2, var1, "_ErrorHolder", 252, false, false, self, 24, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "description"};
      __init__$25 = Py.newCode(2, var2, var1, "__init__", 264, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      id$26 = Py.newCode(1, var2, var1, "id", 267, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      shortDescription$27 = Py.newCode(1, var2, var1, "shortDescription", 270, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$28 = Py.newCode(1, var2, var1, "__repr__", 273, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$29 = Py.newCode(1, var2, var1, "__str__", 276, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result"};
      run$30 = Py.newCode(2, var2, var1, "run", 279, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result"};
      __call__$31 = Py.newCode(2, var2, var1, "__call__", 284, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      countTestCases$32 = Py.newCode(1, var2, var1, "countTestCases", 287, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"test"};
      _isnotsuite$33 = Py.newCode(1, var2, var1, "_isnotsuite", 290, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _DebugResult$34 = Py.newCode(0, var2, var1, "_DebugResult", 299, false, false, self, 34, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new suite$py("unittest/suite$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(suite$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._call_if_exists$1(var2, var3);
         case 2:
            return this.f$2(var2, var3);
         case 3:
            return this.BaseTestSuite$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.__repr__$5(var2, var3);
         case 6:
            return this.__eq__$6(var2, var3);
         case 7:
            return this.__ne__$7(var2, var3);
         case 8:
            return this.__iter__$8(var2, var3);
         case 9:
            return this.countTestCases$9(var2, var3);
         case 10:
            return this.addTest$10(var2, var3);
         case 11:
            return this.addTests$11(var2, var3);
         case 12:
            return this.run$12(var2, var3);
         case 13:
            return this.__call__$13(var2, var3);
         case 14:
            return this.debug$14(var2, var3);
         case 15:
            return this.TestSuite$15(var2, var3);
         case 16:
            return this.run$16(var2, var3);
         case 17:
            return this.debug$17(var2, var3);
         case 18:
            return this._handleClassSetUp$18(var2, var3);
         case 19:
            return this._get_previous_module$19(var2, var3);
         case 20:
            return this._handleModuleFixture$20(var2, var3);
         case 21:
            return this._addClassOrModuleLevelException$21(var2, var3);
         case 22:
            return this._handleModuleTearDown$22(var2, var3);
         case 23:
            return this._tearDownPreviousClass$23(var2, var3);
         case 24:
            return this._ErrorHolder$24(var2, var3);
         case 25:
            return this.__init__$25(var2, var3);
         case 26:
            return this.id$26(var2, var3);
         case 27:
            return this.shortDescription$27(var2, var3);
         case 28:
            return this.__repr__$28(var2, var3);
         case 29:
            return this.__str__$29(var2, var3);
         case 30:
            return this.run$30(var2, var3);
         case 31:
            return this.__call__$31(var2, var3);
         case 32:
            return this.countTestCases$32(var2, var3);
         case 33:
            return this._isnotsuite$33(var2, var3);
         case 34:
            return this._DebugResult$34(var2, var3);
         default:
            return null;
      }
   }
}
