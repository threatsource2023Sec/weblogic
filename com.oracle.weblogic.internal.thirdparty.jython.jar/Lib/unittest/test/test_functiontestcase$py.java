package unittest.test;

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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("unittest/test/test_functiontestcase.py")
public class test_functiontestcase$py extends PyFunctionTable implements PyRunnable {
   static test_functiontestcase$py self;
   static final PyCode f$0;
   static final PyCode Test_FunctionTestCase$1;
   static final PyCode test_countTestCases$2;
   static final PyCode f$3;
   static final PyCode test_run_call_order__error_in_setUp$4;
   static final PyCode setUp$5;
   static final PyCode test$6;
   static final PyCode tearDown$7;
   static final PyCode test_run_call_order__error_in_test$8;
   static final PyCode setUp$9;
   static final PyCode test$10;
   static final PyCode tearDown$11;
   static final PyCode test_run_call_order__failure_in_test$12;
   static final PyCode setUp$13;
   static final PyCode test$14;
   static final PyCode tearDown$15;
   static final PyCode test_run_call_order__error_in_tearDown$16;
   static final PyCode setUp$17;
   static final PyCode test$18;
   static final PyCode tearDown$19;
   static final PyCode test_id$20;
   static final PyCode f$21;
   static final PyCode test_shortDescription__no_docstring$22;
   static final PyCode f$23;
   static final PyCode test_shortDescription__singleline_docstring$24;
   static final PyCode f$25;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(3);
      String[] var5 = new String[]{"LoggingResult"};
      PyObject[] var6 = imp.importFrom("support", var5, var1, 1);
      PyObject var4 = var6[0];
      var1.setlocal("LoggingResult", var4);
      var4 = null;
      var1.setline(6);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test_FunctionTestCase", var6, Test_FunctionTestCase$1);
      var1.setlocal("Test_FunctionTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(147);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(148);
         var1.getname("unittest").__getattr__("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test_FunctionTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(10);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_countTestCases$2, (PyObject)null);
      var1.setlocal("test_countTestCases", var4);
      var3 = null;
      var1.setline(22);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_run_call_order__error_in_setUp$4, (PyObject)null);
      var1.setlocal("test_run_call_order__error_in_setUp", var4);
      var3 = null;
      var1.setline(47);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_run_call_order__error_in_test$8, (PyObject)null);
      var1.setlocal("test_run_call_order__error_in_test", var4);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_run_call_order__failure_in_test$12, (PyObject)null);
      var1.setlocal("test_run_call_order__failure_in_test", var4);
      var3 = null;
      var1.setline(99);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_run_call_order__error_in_tearDown$16, (PyObject)null);
      var1.setlocal("test_run_call_order__error_in_tearDown", var4);
      var3 = null;
      var1.setline(124);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_id$20, (PyObject)null);
      var1.setlocal("test_id", var4);
      var3 = null;
      var1.setline(132);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_shortDescription__no_docstring$22, (PyObject)null);
      var1.setlocal("test_shortDescription__no_docstring", var4);
      var3 = null;
      var1.setline(140);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_shortDescription__singleline_docstring$24, (PyObject)null);
      var1.setlocal("test_shortDescription__singleline_docstring", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_countTestCases$2(PyFrame var1, ThreadState var2) {
      var1.setline(11);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(11);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$3)));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(13);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$3(PyFrame var1, ThreadState var2) {
      var1.setline(11);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_run_call_order__error_in_setUp$4(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(24);
      PyObject var4 = var1.getglobal("LoggingResult").__call__(var2, var1.getderef(0));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(26);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = setUp$5;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(30);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = test$6;
      var5 = new PyObject[]{var1.getclosure(0)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(33);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = tearDown$7;
      var5 = new PyObject[]{var1.getclosure(0)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(36);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("setUp"), PyString.fromInterned("addError"), PyString.fromInterned("stopTest")});
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(37);
      var1.getglobal("unittest").__getattr__("FunctionTestCase").__call__(var2, var1.getlocal(3), var1.getlocal(2), var1.getlocal(4)).__getattr__("run").__call__(var2, var1.getlocal(1));
      var1.setline(38);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getderef(0), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setUp$5(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setUp"));
      var1.setline(28);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raised by setUp")));
   }

   public PyObject test$6(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$7(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tearDown"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_run_call_order__error_in_test$8(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(49);
      PyObject var4 = var1.getglobal("LoggingResult").__call__(var2, var1.getderef(0));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(51);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = setUp$9;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(54);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = test$10;
      var5 = new PyObject[]{var1.getclosure(0)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(58);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = tearDown$11;
      var5 = new PyObject[]{var1.getclosure(0)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(61);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("setUp"), PyString.fromInterned("test"), PyString.fromInterned("addError"), PyString.fromInterned("tearDown"), PyString.fromInterned("stopTest")});
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(63);
      var1.getglobal("unittest").__getattr__("FunctionTestCase").__call__(var2, var1.getlocal(3), var1.getlocal(2), var1.getlocal(4)).__getattr__("run").__call__(var2, var1.getlocal(1));
      var1.setline(64);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getderef(0), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setUp$9(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setUp"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$10(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
      var1.setline(56);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raised by test")));
   }

   public PyObject tearDown$11(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tearDown"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_run_call_order__failure_in_test$12(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(74);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(75);
      PyObject var4 = var1.getglobal("LoggingResult").__call__(var2, var1.getderef(1));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(77);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = setUp$13;
      var5 = new PyObject[]{var1.getclosure(1)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(80);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = test$14;
      var5 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(84);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = tearDown$15;
      var5 = new PyObject[]{var1.getclosure(1)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(87);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("setUp"), PyString.fromInterned("test"), PyString.fromInterned("addFailure"), PyString.fromInterned("tearDown"), PyString.fromInterned("stopTest")});
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(89);
      var1.getglobal("unittest").__getattr__("FunctionTestCase").__call__(var2, var1.getlocal(3), var1.getlocal(2), var1.getlocal(4)).__getattr__("run").__call__(var2, var1.getlocal(1));
      var1.setline(90);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getderef(1), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setUp$13(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setUp"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$14(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
      var1.setline(82);
      var1.getderef(1).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raised by test"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$15(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tearDown"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_run_call_order__error_in_tearDown$16(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(101);
      PyObject var4 = var1.getglobal("LoggingResult").__call__(var2, var1.getderef(0));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(103);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = setUp$17;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(106);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = test$18;
      var5 = new PyObject[]{var1.getclosure(0)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(109);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = tearDown$19;
      var5 = new PyObject[]{var1.getclosure(0)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(113);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("setUp"), PyString.fromInterned("test"), PyString.fromInterned("tearDown"), PyString.fromInterned("addError"), PyString.fromInterned("stopTest")});
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(115);
      var1.getglobal("unittest").__getattr__("FunctionTestCase").__call__(var2, var1.getlocal(3), var1.getlocal(2), var1.getlocal(4)).__getattr__("run").__call__(var2, var1.getlocal(1));
      var1.setline(116);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getderef(0), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setUp$17(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setUp"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$18(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$19(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tearDown"));
      var1.setline(111);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raised by tearDown")));
   }

   public PyObject test_id$20(PyFrame var1, ThreadState var2) {
      var1.setline(125);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(125);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$21)));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(127);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(1).__getattr__("id").__call__(var2), var1.getglobal("basestring"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$21(PyFrame var1, ThreadState var2) {
      var1.setline(125);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_shortDescription__no_docstring$22(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(133);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$23)));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(135);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("shortDescription").__call__(var2), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$23(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_shortDescription__singleline_docstring$24(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyString var3 = PyString.fromInterned("this tests foo");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(142);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      PyObject[] var5 = new PyObject[2];
      var1.setline(142);
      PyObject[] var4 = Py.EmptyObjects;
      var5[0] = new PyFunction(var1.f_globals, var4, f$25);
      var5[1] = var1.getlocal(1);
      String[] var6 = new String[]{"description"};
      var10000 = var10000.__call__(var2, var5, var6);
      var3 = null;
      PyObject var7 = var10000;
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(144);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("shortDescription").__call__(var2), (PyObject)PyString.fromInterned("this tests foo"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$25(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public test_functiontestcase$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Test_FunctionTestCase$1 = Py.newCode(0, var2, var1, "Test_FunctionTestCase", 6, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test"};
      test_countTestCases$2 = Py.newCode(1, var2, var1, "test_countTestCases", 10, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$3 = Py.newCode(0, var2, var1, "<lambda>", 11, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "setUp", "test", "tearDown", "expected", "events"};
      String[] var10001 = var2;
      test_functiontestcase$py var10007 = self;
      var2 = new String[]{"events"};
      test_run_call_order__error_in_setUp$4 = Py.newCode(1, var10001, var1, "test_run_call_order__error_in_setUp", 22, false, false, var10007, 4, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      setUp$5 = Py.newCode(0, var10001, var1, "setUp", 26, false, false, var10007, 5, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      test$6 = Py.newCode(0, var10001, var1, "test", 30, false, false, var10007, 6, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      tearDown$7 = Py.newCode(0, var10001, var1, "tearDown", 33, false, false, var10007, 7, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "result", "setUp", "test", "tearDown", "expected", "events"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      test_run_call_order__error_in_test$8 = Py.newCode(1, var10001, var1, "test_run_call_order__error_in_test", 47, false, false, var10007, 8, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      setUp$9 = Py.newCode(0, var10001, var1, "setUp", 51, false, false, var10007, 9, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      test$10 = Py.newCode(0, var10001, var1, "test", 54, false, false, var10007, 10, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      tearDown$11 = Py.newCode(0, var10001, var1, "tearDown", 58, false, false, var10007, 11, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "result", "setUp", "test", "tearDown", "expected", "events"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "events"};
      test_run_call_order__failure_in_test$12 = Py.newCode(1, var10001, var1, "test_run_call_order__failure_in_test", 73, false, false, var10007, 12, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      setUp$13 = Py.newCode(0, var10001, var1, "setUp", 77, false, false, var10007, 13, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events", "self"};
      test$14 = Py.newCode(0, var10001, var1, "test", 80, false, false, var10007, 14, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      tearDown$15 = Py.newCode(0, var10001, var1, "tearDown", 84, false, false, var10007, 15, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "result", "setUp", "test", "tearDown", "expected", "events"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      test_run_call_order__error_in_tearDown$16 = Py.newCode(1, var10001, var1, "test_run_call_order__error_in_tearDown", 99, false, false, var10007, 16, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      setUp$17 = Py.newCode(0, var10001, var1, "setUp", 103, false, false, var10007, 17, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      test$18 = Py.newCode(0, var10001, var1, "test", 106, false, false, var10007, 18, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      tearDown$19 = Py.newCode(0, var10001, var1, "tearDown", 109, false, false, var10007, 19, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "test"};
      test_id$20 = Py.newCode(1, var2, var1, "test_id", 124, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$21 = Py.newCode(0, var2, var1, "<lambda>", 125, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      test_shortDescription__no_docstring$22 = Py.newCode(1, var2, var1, "test_shortDescription__no_docstring", 132, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$23 = Py.newCode(0, var2, var1, "<lambda>", 133, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "desc", "test"};
      test_shortDescription__singleline_docstring$24 = Py.newCode(1, var2, var1, "test_shortDescription__singleline_docstring", 140, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$25 = Py.newCode(0, var2, var1, "<lambda>", 142, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_functiontestcase$py("unittest/test/test_functiontestcase$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_functiontestcase$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Test_FunctionTestCase$1(var2, var3);
         case 2:
            return this.test_countTestCases$2(var2, var3);
         case 3:
            return this.f$3(var2, var3);
         case 4:
            return this.test_run_call_order__error_in_setUp$4(var2, var3);
         case 5:
            return this.setUp$5(var2, var3);
         case 6:
            return this.test$6(var2, var3);
         case 7:
            return this.tearDown$7(var2, var3);
         case 8:
            return this.test_run_call_order__error_in_test$8(var2, var3);
         case 9:
            return this.setUp$9(var2, var3);
         case 10:
            return this.test$10(var2, var3);
         case 11:
            return this.tearDown$11(var2, var3);
         case 12:
            return this.test_run_call_order__failure_in_test$12(var2, var3);
         case 13:
            return this.setUp$13(var2, var3);
         case 14:
            return this.test$14(var2, var3);
         case 15:
            return this.tearDown$15(var2, var3);
         case 16:
            return this.test_run_call_order__error_in_tearDown$16(var2, var3);
         case 17:
            return this.setUp$17(var2, var3);
         case 18:
            return this.test$18(var2, var3);
         case 19:
            return this.tearDown$19(var2, var3);
         case 20:
            return this.test_id$20(var2, var3);
         case 21:
            return this.f$21(var2, var3);
         case 22:
            return this.test_shortDescription__no_docstring$22(var2, var3);
         case 23:
            return this.f$23(var2, var3);
         case 24:
            return this.test_shortDescription__singleline_docstring$24(var2, var3);
         case 25:
            return this.f$25(var2, var3);
         default:
            return null;
      }
   }
}
