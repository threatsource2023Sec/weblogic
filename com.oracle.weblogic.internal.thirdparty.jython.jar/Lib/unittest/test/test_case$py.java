package unittest.test;

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
import org.python.core.PySet;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("unittest/test/test_case.py")
public class test_case$py extends PyFunctionTable implements PyRunnable {
   static test_case$py self;
   static final PyCode f$0;
   static final PyCode Test$1;
   static final PyCode Foo$2;
   static final PyCode runTest$3;
   static final PyCode test1$4;
   static final PyCode Bar$5;
   static final PyCode test2$6;
   static final PyCode LoggingTestCase$7;
   static final PyCode __init__$8;
   static final PyCode setUp$9;
   static final PyCode test$10;
   static final PyCode tearDown$11;
   static final PyCode Test_TestCase$12;
   static final PyCode test_init__no_test_name$13;
   static final PyCode Test$14;
   static final PyCode runTest$15;
   static final PyCode test$16;
   static final PyCode test_init__test_name__valid$17;
   static final PyCode Test$18;
   static final PyCode runTest$19;
   static final PyCode test$20;
   static final PyCode test_init__test_name__invalid$21;
   static final PyCode Test$22;
   static final PyCode runTest$23;
   static final PyCode test$24;
   static final PyCode test_countTestCases$25;
   static final PyCode Foo$26;
   static final PyCode test$27;
   static final PyCode test_defaultTestResult$28;
   static final PyCode Foo$29;
   static final PyCode runTest$30;
   static final PyCode test_run_call_order__error_in_setUp$31;
   static final PyCode Foo$32;
   static final PyCode setUp$33;
   static final PyCode test_run_call_order__error_in_setUp_default_result$34;
   static final PyCode Foo$35;
   static final PyCode defaultTestResult$36;
   static final PyCode setUp$37;
   static final PyCode test_run_call_order__error_in_test$38;
   static final PyCode Foo$39;
   static final PyCode test$40;
   static final PyCode test_run_call_order__error_in_test_default_result$41;
   static final PyCode Foo$42;
   static final PyCode defaultTestResult$43;
   static final PyCode test$44;
   static final PyCode test_run_call_order__failure_in_test$45;
   static final PyCode Foo$46;
   static final PyCode test$47;
   static final PyCode test_run_call_order__failure_in_test_default_result$48;
   static final PyCode Foo$49;
   static final PyCode defaultTestResult$50;
   static final PyCode test$51;
   static final PyCode test_run_call_order__error_in_tearDown$52;
   static final PyCode Foo$53;
   static final PyCode tearDown$54;
   static final PyCode test_run_call_order__error_in_tearDown_default_result$55;
   static final PyCode Foo$56;
   static final PyCode defaultTestResult$57;
   static final PyCode tearDown$58;
   static final PyCode test_run_call_order_default_result$59;
   static final PyCode Foo$60;
   static final PyCode defaultTestResult$61;
   static final PyCode test$62;
   static final PyCode test_failureException__default$63;
   static final PyCode Foo$64;
   static final PyCode test$65;
   static final PyCode test_failureException__subclassing__explicit_raise$66;
   static final PyCode Foo$67;
   static final PyCode test$68;
   static final PyCode test_failureException__subclassing__implicit_raise$69;
   static final PyCode Foo$70;
   static final PyCode test$71;
   static final PyCode test_setUp$72;
   static final PyCode Foo$73;
   static final PyCode runTest$74;
   static final PyCode test_tearDown$75;
   static final PyCode Foo$76;
   static final PyCode runTest$77;
   static final PyCode test_id$78;
   static final PyCode Foo$79;
   static final PyCode runTest$80;
   static final PyCode test_run__uses_defaultTestResult$81;
   static final PyCode Foo$82;
   static final PyCode test$83;
   static final PyCode defaultTestResult$84;
   static final PyCode testShortDescriptionWithoutDocstring$85;
   static final PyCode testShortDescriptionWithOneLineDocstring$86;
   static final PyCode testShortDescriptionWithMultiLineDocstring$87;
   static final PyCode testAddTypeEqualityFunc$88;
   static final PyCode SadSnake$89;
   static final PyCode AllSnakesCreatedEqual$90;
   static final PyCode testAssertIs$91;
   static final PyCode testAssertIsNot$92;
   static final PyCode testAssertIsInstance$93;
   static final PyCode testAssertNotIsInstance$94;
   static final PyCode testAssertIn$95;
   static final PyCode testAssertDictContainsSubset$96;
   static final PyCode f$97;
   static final PyCode testAssertEqual$98;
   static final PyCode testEquality$99;
   static final PyCode testAssertSequenceEqualMaxDiff$100;
   static final PyCode testTruncateMessage$101;
   static final PyCode testAssertDictEqualTruncates$102;
   static final PyCode truncate$103;
   static final PyCode testAssertMultiLineEqualTruncates$104;
   static final PyCode truncate$105;
   static final PyCode testAssertEqual_diffThreshold$106;
   static final PyCode f$107;
   static final PyCode explodingTruncation$108;
   static final PyCode f$109;
   static final PyCode testAssertItemsEqual$110;
   static final PyCode testAssertSetEqual$111;
   static final PyCode testInequality$112;
   static final PyCode testAssertMultiLineEqual$113;
   static final PyCode f$114;
   static final PyCode f$115;
   static final PyCode testAsertEqualSingleLine$116;
   static final PyCode testAssertIsNone$117;
   static final PyCode testAssertRegexpMatches$118;
   static final PyCode testAssertRaisesRegexp$119;
   static final PyCode ExceptionMock$120;
   static final PyCode Stub$121;
   static final PyCode testAssertNotRaisesRegexp$122;
   static final PyCode f$123;
   static final PyCode f$124;
   static final PyCode f$125;
   static final PyCode testAssertRaisesRegexpMismatch$126;
   static final PyCode Stub$127;
   static final PyCode testAssertRaisesExcValue$128;
   static final PyCode ExceptionMock$129;
   static final PyCode Stub$130;
   static final PyCode testSynonymAssertMethodNames$131;
   static final PyCode testPendingDeprecationMethodNames$132;
   static final PyCode f$133;
   static final PyCode testDeepcopy$134;
   static final PyCode TestableTest$135;
   static final PyCode testNothing$136;
   static final PyCode testKeyboardInterrupt$137;
   static final PyCode _raise$138;
   static final PyCode nothing$139;
   static final PyCode Test1$140;
   static final PyCode Test2$141;
   static final PyCode Test3$142;
   static final PyCode Test4$143;
   static final PyCode test_something$144;
   static final PyCode testSystemExit$145;
   static final PyCode _raise$146;
   static final PyCode nothing$147;
   static final PyCode Test1$148;
   static final PyCode Test2$149;
   static final PyCode Test3$150;
   static final PyCode Test4$151;
   static final PyCode test_something$152;
   static final PyCode testPickle$153;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("difflib", var1, -1);
      var1.setlocal("difflib", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("pprint", var1, -1);
      var1.setlocal("pprint", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("pickle", var1, -1);
      var1.setlocal("pickle", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(7);
      String[] var5 = new String[]{"deepcopy"};
      PyObject[] var6 = imp.importFrom("copy", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("deepcopy", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"test_support"};
      var6 = imp.importFrom("test", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("test_support", var4);
      var4 = null;
      var1.setline(10);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(12);
      var5 = new String[]{"TestEquality", "TestHashing", "LoggingResult", "ResultWithNoStartTestRunStopTestRun"};
      var6 = imp.importFrom("support", var5, var1, 1);
      var4 = var6[0];
      var1.setlocal("TestEquality", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("TestHashing", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("LoggingResult", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("ResultWithNoStartTestRunStopTestRun", var4);
      var4 = null;
      var1.setline(17);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Test", var6, Test$1);
      var1.setlocal("Test", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(44);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase"), var1.getname("TestEquality"), var1.getname("TestHashing")};
      var4 = Py.makeClass("Test_TestCase", var6, Test_TestCase$12);
      var1.setlocal("Test_TestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1123);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1124);
         var1.getname("unittest").__getattr__("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Keep these TestCase classes out of the main namespace"));
      var1.setline(18);
      PyString.fromInterned("Keep these TestCase classes out of the main namespace");
      var1.setline(20);
      PyObject[] var3 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$2);
      var1.setlocal("Foo", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(24);
      var3 = new PyObject[]{var1.getname("Foo")};
      var4 = Py.makeClass("Bar", var3, Bar$5);
      var1.setlocal("Bar", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(27);
      var3 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("LoggingTestCase", var3, LoggingTestCase$7);
      var1.setlocal("LoggingTestCase", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      return var1.getf_locals();
   }

   public PyObject Foo$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(21);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, runTest$3, (PyObject)null);
      var1.setlocal("runTest", var4);
      var3 = null;
      var1.setline(22);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test1$4, (PyObject)null);
      var1.setlocal("test1", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject runTest$3(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test1$4(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Bar$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(25);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test2$6, (PyObject)null);
      var1.setlocal("test2", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test2$6(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject LoggingTestCase$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A test case which logs its calls."));
      var1.setline(28);
      PyString.fromInterned("A test case which logs its calls.");
      var1.setline(30);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(34);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setUp$9, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test$10, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      var1.setline(40);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$11, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      var1.getglobal("super").__call__(var2, var1.getglobal("Test").__getattr__("LoggingTestCase"), var1.getlocal(0)).__getattr__("__init__").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
      var1.setline(32);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("events", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setUp$9(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      var1.getlocal(0).__getattr__("events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setUp"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$10(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      var1.getlocal(0).__getattr__("events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$11(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      var1.getlocal(0).__getattr__("events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tearDown"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test_TestCase$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(50);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("Test").__getattr__("Foo").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test1")), var1.getname("Test").__getattr__("Foo").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test1"))})});
      var1.setlocal("eq_pairs", var3);
      var3 = null;
      var1.setline(53);
      var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("Test").__getattr__("Foo").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test1")), var1.getname("Test").__getattr__("Foo").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("runTest"))}), new PyTuple(new PyObject[]{var1.getname("Test").__getattr__("Foo").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test1")), var1.getname("Test").__getattr__("Bar").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test1"))}), new PyTuple(new PyObject[]{var1.getname("Test").__getattr__("Foo").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test1")), var1.getname("Test").__getattr__("Bar").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test2"))})});
      var1.setlocal("ne_pairs", var3);
      var3 = null;
      var1.setline(70);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, test_init__no_test_name$13, (PyObject)null);
      var1.setlocal("test_init__no_test_name", var6);
      var3 = null;
      var1.setline(81);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_init__test_name__valid$17, (PyObject)null);
      var1.setlocal("test_init__test_name__valid", var6);
      var3 = null;
      var1.setline(92);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_init__test_name__invalid$21, (PyObject)null);
      var1.setlocal("test_init__test_name__invalid", var6);
      var3 = null;
      var1.setline(106);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_countTestCases$25, (PyObject)null);
      var1.setlocal("test_countTestCases", var6);
      var3 = null;
      var1.setline(116);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_defaultTestResult$28, (PyObject)null);
      var1.setlocal("test_defaultTestResult", var6);
      var3 = null;
      var1.setline(131);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_run_call_order__error_in_setUp$31, (PyObject)null);
      var1.setlocal("test_run_call_order__error_in_setUp", var6);
      var3 = null;
      var1.setline(145);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_run_call_order__error_in_setUp_default_result$34, (PyObject)null);
      var1.setlocal("test_run_call_order__error_in_setUp_default_result", var6);
      var3 = null;
      var1.setline(168);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_run_call_order__error_in_test$38, (PyObject)null);
      var1.setlocal("test_run_call_order__error_in_test", var6);
      var3 = null;
      var1.setline(184);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_run_call_order__error_in_test_default_result$41, (PyObject)null);
      var1.setlocal("test_run_call_order__error_in_test_default_result", var6);
      var3 = null;
      var1.setline(207);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_run_call_order__failure_in_test$45, (PyObject)null);
      var1.setlocal("test_run_call_order__failure_in_test", var6);
      var3 = null;
      var1.setline(222);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_run_call_order__failure_in_test_default_result$48, (PyObject)null);
      var1.setlocal("test_run_call_order__failure_in_test_default_result", var6);
      var3 = null;
      var1.setline(244);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_run_call_order__error_in_tearDown$52, (PyObject)null);
      var1.setlocal("test_run_call_order__error_in_tearDown", var6);
      var3 = null;
      var1.setline(259);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_run_call_order__error_in_tearDown_default_result$55, (PyObject)null);
      var1.setlocal("test_run_call_order__error_in_tearDown_default_result", var6);
      var3 = null;
      var1.setline(276);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_run_call_order_default_result$59, (PyObject)null);
      var1.setlocal("test_run_call_order_default_result", var6);
      var3 = null;
      var1.setline(291);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_failureException__default$63, (PyObject)null);
      var1.setlocal("test_failureException__default", var6);
      var3 = null;
      var1.setline(304);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_failureException__subclassing__explicit_raise$66, (PyObject)null);
      var1.setlocal("test_failureException__subclassing__explicit_raise", var6);
      var3 = null;
      var1.setline(327);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_failureException__subclassing__implicit_raise$69, (PyObject)null);
      var1.setlocal("test_failureException__subclassing__implicit_raise", var6);
      var3 = null;
      var1.setline(345);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_setUp$72, (PyObject)null);
      var1.setlocal("test_setUp", var6);
      var3 = null;
      var1.setline(354);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_tearDown$75, (PyObject)null);
      var1.setlocal("test_tearDown", var6);
      var3 = null;
      var1.setline(368);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_id$78, (PyObject)null);
      var1.setlocal("test_id", var6);
      var3 = null;
      var1.setline(379);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_run__uses_defaultTestResult$81, (PyObject)null);
      var1.setlocal("test_run__uses_defaultTestResult", var6);
      var3 = null;
      var1.setline(396);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testShortDescriptionWithoutDocstring$85, (PyObject)null);
      var1.setlocal("testShortDescriptionWithoutDocstring", var6);
      var3 = null;
      var1.setline(399);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testShortDescriptionWithOneLineDocstring$86, PyString.fromInterned("Tests shortDescription() for a method with a docstring."));
      PyObject var10000 = var1.getname("unittest").__getattr__("skipIf");
      PyObject var4 = var1.getname("sys").__getattr__("flags").__getattr__("optimize");
      PyObject var10002 = var4._ge(Py.newInteger(2));
      var4 = null;
      PyObject var7 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("Docstrings are omitted with -O2 and above")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("testShortDescriptionWithOneLineDocstring", var7);
      var3 = null;
      var1.setline(407);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testShortDescriptionWithMultiLineDocstring$87, PyString.fromInterned("Tests shortDescription() for a method with a longer docstring.\n\n        This method ensures that only the first line of a docstring is\n        returned used in the short description, no matter how long the\n        whole thing is.\n        "));
      var10000 = var1.getname("unittest").__getattr__("skipIf");
      var4 = var1.getname("sys").__getattr__("flags").__getattr__("optimize");
      var10002 = var4._ge(Py.newInteger(2));
      var4 = null;
      var7 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("Docstrings are omitted with -O2 and above")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("testShortDescriptionWithMultiLineDocstring", var7);
      var3 = null;
      var1.setline(421);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAddTypeEqualityFunc$88, (PyObject)null);
      var1.setlocal("testAddTypeEqualityFunc", var6);
      var3 = null;
      var1.setline(434);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertIs$91, (PyObject)null);
      var1.setlocal("testAssertIs", var6);
      var3 = null;
      var1.setline(439);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertIsNot$92, (PyObject)null);
      var1.setlocal("testAssertIsNot", var6);
      var3 = null;
      var1.setline(444);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertIsInstance$93, (PyObject)null);
      var1.setlocal("testAssertIsInstance", var6);
      var3 = null;
      var1.setline(450);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertNotIsInstance$94, (PyObject)null);
      var1.setlocal("testAssertNotIsInstance", var6);
      var3 = null;
      var1.setline(456);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertIn$95, (PyObject)null);
      var1.setlocal("testAssertIn", var6);
      var3 = null;
      var1.setline(477);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertDictContainsSubset$96, (PyObject)null);
      var1.setlocal("testAssertDictContainsSubset", var6);
      var3 = null;
      var1.setline(505);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertEqual$98, (PyObject)null);
      var1.setlocal("testAssertEqual", var6);
      var3 = null;
      var1.setline(542);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testEquality$99, (PyObject)null);
      var1.setlocal("testEquality", var6);
      var3 = null;
      var1.setline(594);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertSequenceEqualMaxDiff$100, (PyObject)null);
      var1.setlocal("testAssertSequenceEqualMaxDiff", var6);
      var3 = null;
      var1.setline(633);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testTruncateMessage$101, (PyObject)null);
      var1.setlocal("testTruncateMessage", var6);
      var3 = null;
      var1.setline(647);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertDictEqualTruncates$102, (PyObject)null);
      var1.setlocal("testAssertDictEqualTruncates", var6);
      var3 = null;
      var1.setline(659);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertMultiLineEqualTruncates$104, (PyObject)null);
      var1.setlocal("testAssertMultiLineEqualTruncates", var6);
      var3 = null;
      var1.setline(671);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertEqual_diffThreshold$106, (PyObject)null);
      var1.setlocal("testAssertEqual_diffThreshold", var6);
      var3 = null;
      var1.setline(707);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertItemsEqual$110, (PyObject)null);
      var1.setlocal("testAssertItemsEqual", var6);
      var3 = null;
      var1.setline(769);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertSetEqual$111, (PyObject)null);
      var1.setlocal("testAssertSetEqual", var6);
      var3 = null;
      var1.setline(809);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testInequality$112, (PyObject)null);
      var1.setlocal("testInequality", var6);
      var3 = null;
      var1.setline(895);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertMultiLineEqual$113, (PyObject)null);
      var1.setlocal("testAssertMultiLineEqual", var6);
      var3 = null;
      var1.setline(931);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAsertEqualSingleLine$116, (PyObject)null);
      var1.setlocal("testAsertEqualSingleLine", var6);
      var3 = null;
      var1.setline(946);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertIsNone$117, (PyObject)null);
      var1.setlocal("testAssertIsNone", var6);
      var3 = null;
      var1.setline(952);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertRegexpMatches$118, (PyObject)null);
      var1.setlocal("testAssertRegexpMatches", var6);
      var3 = null;
      var1.setline(957);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertRaisesRegexp$119, (PyObject)null);
      var1.setlocal("testAssertRaisesRegexp", var6);
      var3 = null;
      var1.setline(968);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertNotRaisesRegexp$122, (PyObject)null);
      var1.setlocal("testAssertNotRaisesRegexp", var6);
      var3 = null;
      var1.setline(982);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertRaisesRegexpMismatch$126, (PyObject)null);
      var1.setlocal("testAssertRaisesRegexpMismatch", var6);
      var3 = null;
      var1.setline(1002);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testAssertRaisesExcValue$128, (PyObject)null);
      var1.setlocal("testAssertRaisesExcValue", var6);
      var3 = null;
      var1.setline(1017);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testSynonymAssertMethodNames$131, PyString.fromInterned("Test undocumented method name synonyms.\n\n        Please do not use these methods names in your own code.\n\n        This test confirms their continued existence and functionality\n        in order to avoid breaking existing code.\n        "));
      var1.setlocal("testSynonymAssertMethodNames", var6);
      var3 = null;
      var1.setline(1031);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testPendingDeprecationMethodNames$132, PyString.fromInterned("Test fail* methods pending deprecation, they will warn in 3.2.\n\n        Do not use these methods.  They will go away in 3.3.\n        "));
      var1.setlocal("testPendingDeprecationMethodNames", var6);
      var3 = null;
      var1.setline(1045);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testDeepcopy$134, (PyObject)null);
      var1.setlocal("testDeepcopy", var6);
      var3 = null;
      var1.setline(1056);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testKeyboardInterrupt$137, (PyObject)null);
      var1.setlocal("testKeyboardInterrupt", var6);
      var3 = null;
      var1.setline(1081);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testSystemExit$145, (PyObject)null);
      var1.setlocal("testSystemExit", var6);
      var3 = null;
      var1.setline(1108);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, testPickle$153, (PyObject)null);
      var1.setlocal("testPickle", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_init__no_test_name$13(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$14);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(75);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2).__getattr__("id").__call__(var2).__getslice__(Py.newInteger(-13), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned(".Test.runTest"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$14(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(72);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, runTest$15, (PyObject)null);
      var1.setlocal("runTest", var4);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test$16, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject runTest$15(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      throw Py.makeException(var1.getglobal("TypeError").__call__(var2));
   }

   public PyObject test$16(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_init__test_name__valid$17(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$18);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(86);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test")).__getattr__("id").__call__(var2).__getslice__(Py.newInteger(-10), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned(".Test.test"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(83);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, runTest$19, (PyObject)null);
      var1.setlocal("runTest", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test$20, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject runTest$19(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      throw Py.makeException(var1.getglobal("TypeError").__call__(var2));
   }

   public PyObject test$20(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_init__test_name__invalid$21(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$22);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);

      label19: {
         try {
            var1.setline(98);
            var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testfoo"));
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (var6.match(var1.getglobal("ValueError"))) {
               var1.setline(100);
               break label19;
            }

            throw var6;
         }

         var1.setline(102);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Failed to raise ValueError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(94);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, runTest$23, (PyObject)null);
      var1.setlocal("runTest", var4);
      var3 = null;
      var1.setline(95);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test$24, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject runTest$23(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      throw Py.makeException(var1.getglobal("TypeError").__call__(var2));
   }

   public PyObject test$24(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_countTestCases$25(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$26);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(110);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test")).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$26(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(108);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$27, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$27(PyFrame var1, ThreadState var2) {
      var1.setline(108);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_defaultTestResult$28(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$29);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(121);
      PyObject var5 = var1.getlocal(1).__call__(var2).__getattr__("defaultTestResult").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(122);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(2)), var1.getglobal("unittest").__getattr__("TestResult"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$29(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(118);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, runTest$30, (PyObject)null);
      var1.setlocal("runTest", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject runTest$30(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_run_call_order__error_in_setUp$31(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(133);
      PyObject var5 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(135);
      PyObject[] var6 = new PyObject[]{var1.getglobal("Test").__getattr__("LoggingTestCase")};
      PyObject var4 = Py.makeClass("Foo", var6, Foo$32);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(140);
      var1.getderef(0).__call__(var2, var1.getlocal(1)).__getattr__("run").__call__(var2, var1.getlocal(2));
      var1.setline(141);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("setUp"), PyString.fromInterned("addError"), PyString.fromInterned("stopTest")});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(142);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$32(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(136);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUp$33;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("setUp", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$33(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      var1.getglobal("super").__call__(var2, var1.getderef(0), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(138);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raised by Foo.setUp")));
   }

   public PyObject test_run_call_order__error_in_setUp_default_result$34(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(148);
      PyObject[] var5 = new PyObject[]{var1.getglobal("Test").__getattr__("LoggingTestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$35);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(156);
      var1.getderef(0).__call__(var2, var1.getlocal(1)).__getattr__("run").__call__(var2);
      var1.setline(157);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTestRun"), PyString.fromInterned("startTest"), PyString.fromInterned("setUp"), PyString.fromInterned("addError"), PyString.fromInterned("stopTest"), PyString.fromInterned("stopTestRun")});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(159);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$35(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(149);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, defaultTestResult$36, (PyObject)null);
      var1.setlocal("defaultTestResult", var4);
      var3 = null;
      var1.setline(152);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUp$37;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("setUp", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject defaultTestResult$36(PyFrame var1, ThreadState var2) {
      var1.setline(150);
      PyObject var3 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(0).__getattr__("events"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject setUp$37(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      var1.getglobal("super").__call__(var2, var1.getderef(0), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(154);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raised by Foo.setUp")));
   }

   public PyObject test_run_call_order__error_in_test$38(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(170);
      PyObject var5 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(172);
      PyObject[] var6 = new PyObject[]{var1.getglobal("Test").__getattr__("LoggingTestCase")};
      PyObject var4 = Py.makeClass("Foo", var6, Foo$39);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(177);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("setUp"), PyString.fromInterned("test"), PyString.fromInterned("addError"), PyString.fromInterned("tearDown"), PyString.fromInterned("stopTest")});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(179);
      var1.getderef(0).__call__(var2, var1.getlocal(1)).__getattr__("run").__call__(var2, var1.getlocal(2));
      var1.setline(180);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$39(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(173);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = test$40;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$40(PyFrame var1, ThreadState var2) {
      var1.setline(174);
      var1.getglobal("super").__call__(var2, var1.getderef(0), var1.getlocal(0)).__getattr__("test").__call__(var2);
      var1.setline(175);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raised by Foo.test")));
   }

   public PyObject test_run_call_order__error_in_test_default_result$41(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(187);
      PyObject[] var5 = new PyObject[]{var1.getglobal("Test").__getattr__("LoggingTestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$42);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(195);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTestRun"), PyString.fromInterned("startTest"), PyString.fromInterned("setUp"), PyString.fromInterned("test"), PyString.fromInterned("addError"), PyString.fromInterned("tearDown"), PyString.fromInterned("stopTest"), PyString.fromInterned("stopTestRun")});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(197);
      var1.getderef(0).__call__(var2, var1.getlocal(1)).__getattr__("run").__call__(var2);
      var1.setline(198);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$42(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(188);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, defaultTestResult$43, (PyObject)null);
      var1.setlocal("defaultTestResult", var4);
      var3 = null;
      var1.setline(191);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = test$44;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject defaultTestResult$43(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyObject var3 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(0).__getattr__("events"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test$44(PyFrame var1, ThreadState var2) {
      var1.setline(192);
      var1.getglobal("super").__call__(var2, var1.getderef(0), var1.getlocal(0)).__getattr__("test").__call__(var2);
      var1.setline(193);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raised by Foo.test")));
   }

   public PyObject test_run_call_order__failure_in_test$45(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(209);
      PyObject var5 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(211);
      PyObject[] var6 = new PyObject[]{var1.getglobal("Test").__getattr__("LoggingTestCase")};
      PyObject var4 = Py.makeClass("Foo", var6, Foo$46);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(216);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("setUp"), PyString.fromInterned("test"), PyString.fromInterned("addFailure"), PyString.fromInterned("tearDown"), PyString.fromInterned("stopTest")});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(218);
      var1.getderef(0).__call__(var2, var1.getlocal(1)).__getattr__("run").__call__(var2, var1.getlocal(2));
      var1.setline(219);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(212);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = test$47;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$47(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      var1.getglobal("super").__call__(var2, var1.getderef(0), var1.getlocal(0)).__getattr__("test").__call__(var2);
      var1.setline(214);
      var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raised by Foo.test"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_run_call_order__failure_in_test_default_result$48(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyObject[] var3 = new PyObject[]{var1.getglobal("Test").__getattr__("LoggingTestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$49);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(231);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("startTestRun"), PyString.fromInterned("startTest"), PyString.fromInterned("setUp"), PyString.fromInterned("test"), PyString.fromInterned("addFailure"), PyString.fromInterned("tearDown"), PyString.fromInterned("stopTest"), PyString.fromInterned("stopTestRun")});
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(233);
      var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(234);
      var1.getderef(0).__call__(var2, var1.getlocal(2)).__getattr__("run").__call__(var2);
      var1.setline(235);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$49(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(225);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, defaultTestResult$50, (PyObject)null);
      var1.setlocal("defaultTestResult", var4);
      var3 = null;
      var1.setline(227);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = test$51;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject defaultTestResult$50(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      PyObject var3 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(0).__getattr__("events"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test$51(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      var1.getglobal("super").__call__(var2, var1.getderef(0), var1.getlocal(0)).__getattr__("test").__call__(var2);
      var1.setline(229);
      var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raised by Foo.test"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_run_call_order__error_in_tearDown$52(PyFrame var1, ThreadState var2) {
      var1.setline(245);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(246);
      PyObject var5 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(248);
      PyObject[] var6 = new PyObject[]{var1.getglobal("Test").__getattr__("LoggingTestCase")};
      PyObject var4 = Py.makeClass("Foo", var6, Foo$53);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(253);
      var1.getderef(0).__call__(var2, var1.getlocal(1)).__getattr__("run").__call__(var2, var1.getlocal(2));
      var1.setline(254);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("setUp"), PyString.fromInterned("test"), PyString.fromInterned("tearDown"), PyString.fromInterned("addError"), PyString.fromInterned("stopTest")});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(256);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$53(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(249);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = tearDown$54;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("tearDown", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tearDown$54(PyFrame var1, ThreadState var2) {
      var1.setline(250);
      var1.getglobal("super").__call__(var2, var1.getderef(0), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.setline(251);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raised by Foo.tearDown")));
   }

   public PyObject test_run_call_order__error_in_tearDown_default_result$55(PyFrame var1, ThreadState var2) {
      var1.setline(261);
      PyObject[] var3 = new PyObject[]{var1.getglobal("Test").__getattr__("LoggingTestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$56);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(268);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(269);
      var1.getderef(0).__call__(var2, var1.getlocal(1)).__getattr__("run").__call__(var2);
      var1.setline(270);
      var5 = new PyList(new PyObject[]{PyString.fromInterned("startTestRun"), PyString.fromInterned("startTest"), PyString.fromInterned("setUp"), PyString.fromInterned("test"), PyString.fromInterned("tearDown"), PyString.fromInterned("addError"), PyString.fromInterned("stopTest"), PyString.fromInterned("stopTestRun")});
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(272);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$56(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(262);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, defaultTestResult$57, (PyObject)null);
      var1.setlocal("defaultTestResult", var4);
      var3 = null;
      var1.setline(264);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = tearDown$58;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("tearDown", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject defaultTestResult$57(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      PyObject var3 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(0).__getattr__("events"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject tearDown$58(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      var1.getglobal("super").__call__(var2, var1.getderef(0), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.setline(266);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("raised by Foo.tearDown")));
   }

   public PyObject test_run_call_order_default_result$59(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$60);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(284);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test")).__getattr__("run").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$60(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(279);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, defaultTestResult$61, (PyObject)null);
      var1.setlocal("defaultTestResult", var4);
      var3 = null;
      var1.setline(281);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test$62, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject defaultTestResult$61(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyObject var3 = var1.getglobal("ResultWithNoStartTestRunStopTestRun").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test$62(PyFrame var1, ThreadState var2) {
      var1.setline(282);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_failureException__default$63(PyFrame var1, ThreadState var2) {
      var1.setline(292);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$64);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(296);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      PyObject var5 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test")).__getattr__("failureException");
      PyObject var10002 = var5._is(var1.getglobal("AssertionError"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$64(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(293);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$65, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$65(PyFrame var1, ThreadState var2) {
      var1.setline(294);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_failureException__subclassing__explicit_raise$66(PyFrame var1, ThreadState var2) {
      var1.setline(305);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(306);
      PyObject var5 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(308);
      PyObject[] var6 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var6, Foo$67);
      var1.setlocal(3, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(314);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var5 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test")).__getattr__("failureException");
      PyObject var10002 = var5._is(var1.getglobal("RuntimeError"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(317);
      var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test")).__getattr__("run").__call__(var2, var1.getlocal(2));
      var1.setline(318);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("addFailure"), PyString.fromInterned("stopTest")});
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(319);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$67(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(309);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$68, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      var1.setline(312);
      PyObject var5 = var1.getname("RuntimeError");
      var1.setlocal("failureException", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$68(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      throw Py.makeException(var1.getglobal("RuntimeError").__call__(var2));
   }

   public PyObject test_failureException__subclassing__implicit_raise$69(PyFrame var1, ThreadState var2) {
      var1.setline(328);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(329);
      PyObject var5 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(331);
      PyObject[] var6 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var6, Foo$70);
      var1.setlocal(3, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(337);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var5 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test")).__getattr__("failureException");
      PyObject var10002 = var5._is(var1.getglobal("RuntimeError"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(340);
      var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test")).__getattr__("run").__call__(var2, var1.getlocal(2));
      var1.setline(341);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("addFailure"), PyString.fromInterned("stopTest")});
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(342);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$70(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(332);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$71, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      var1.setline(335);
      PyObject var5 = var1.getname("RuntimeError");
      var1.setlocal("failureException", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$71(PyFrame var1, ThreadState var2) {
      var1.setline(333);
      var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_setUp$72(PyFrame var1, ThreadState var2) {
      var1.setline(346);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$73);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(351);
      var1.getlocal(1).__call__(var2).__getattr__("setUp").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$73(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(347);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, runTest$74, (PyObject)null);
      var1.setlocal("runTest", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject runTest$74(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_tearDown$75(PyFrame var1, ThreadState var2) {
      var1.setline(355);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$76);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(360);
      var1.getlocal(1).__call__(var2).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$76(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(356);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, runTest$77, (PyObject)null);
      var1.setlocal("runTest", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject runTest$77(PyFrame var1, ThreadState var2) {
      var1.setline(357);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_id$78(PyFrame var1, ThreadState var2) {
      var1.setline(369);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$79);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(373);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(1).__call__(var2).__getattr__("id").__call__(var2), var1.getglobal("basestring"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$79(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(370);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, runTest$80, (PyObject)null);
      var1.setlocal("runTest", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject runTest$80(PyFrame var1, ThreadState var2) {
      var1.setline(371);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_run__uses_defaultTestResult$81(PyFrame var1, ThreadState var2) {
      var1.setline(380);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(382);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$82);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(390);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test")).__getattr__("run").__call__(var2);
      var1.setline(392);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("startTestRun"), PyString.fromInterned("startTest"), PyString.fromInterned("test"), PyString.fromInterned("addSuccess"), PyString.fromInterned("stopTest"), PyString.fromInterned("stopTestRun")});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(394);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getderef(0), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$82(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(383);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = test$83;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("test", var4);
      var3 = null;
      var1.setline(386);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = defaultTestResult$84;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("defaultTestResult", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$83(PyFrame var1, ThreadState var2) {
      var1.setline(384);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject defaultTestResult$84(PyFrame var1, ThreadState var2) {
      var1.setline(387);
      PyObject var3 = var1.getglobal("LoggingResult").__call__(var2, var1.getderef(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testShortDescriptionWithoutDocstring$85(PyFrame var1, ThreadState var2) {
      var1.setline(397);
      var1.getlocal(0).__getattr__("assertIsNone").__call__(var2, var1.getlocal(0).__getattr__("shortDescription").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testShortDescriptionWithOneLineDocstring$86(PyFrame var1, ThreadState var2) {
      var1.setline(402);
      PyString.fromInterned("Tests shortDescription() for a method with a docstring.");
      var1.setline(403);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("shortDescription").__call__(var2), (PyObject)PyString.fromInterned("Tests shortDescription() for a method with a docstring."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testShortDescriptionWithMultiLineDocstring$87(PyFrame var1, ThreadState var2) {
      var1.setline(415);
      PyString.fromInterned("Tests shortDescription() for a method with a longer docstring.\n\n        This method ensures that only the first line of a docstring is\n        returned used in the short description, no matter how long the\n        whole thing is.\n        ");
      var1.setline(416);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("shortDescription").__call__(var2), (PyObject)PyString.fromInterned("Tests shortDescription() for a method with a longer docstring."));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAddTypeEqualityFunc$88(PyFrame var1, ThreadState var2) {
      var1.setline(422);
      PyObject[] var3 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("SadSnake", var3, SadSnake$89);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(424);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getderef(0).__call__(var2), var1.getderef(0).__call__(var2)});
      PyObject[] var7 = Py.unpackSequence(var6, 2);
      PyObject var5 = var7[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(425);
      var1.getlocal(0).__getattr__("assertNotEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(426);
      var3 = new PyObject[]{var1.getglobal("None")};
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = AllSnakesCreatedEqual$90;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(428);
      var1.getlocal(0).__getattr__("addTypeEqualityFunc").__call__(var2, var1.getderef(0), var1.getlocal(3));
      var1.setline(429);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SadSnake$89(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Dummy class for test_addTypeEqualityFunc."));
      var1.setline(423);
      PyString.fromInterned("Dummy class for test_addTypeEqualityFunc.");
      return var1.getf_locals();
   }

   public PyObject AllSnakesCreatedEqual$90(PyFrame var1, ThreadState var2) {
      var1.setline(427);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10001 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3;
      var3 = var10001;
      PyObject var4;
      if ((var4 = var10000._is(var10001)).__nonzero__()) {
         var4 = var3._is(var1.getderef(0));
      }

      var3 = null;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject testAssertIs$91(PyFrame var1, ThreadState var2) {
      var1.setline(435);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(436);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(1), var1.getlocal(1));
      var1.setline(437);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertIs"), var1.getlocal(1), var1.getglobal("object").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertIsNot$92(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(441);
      var1.getlocal(0).__getattr__("assertIsNot").__call__(var2, var1.getlocal(1), var1.getglobal("object").__call__(var2));
      var1.setline(442);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertIsNot"), var1.getlocal(1), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertIsInstance$93(PyFrame var1, ThreadState var2) {
      var1.setline(445);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(446);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(1), var1.getglobal("list"));
      var1.setline(447);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertIsInstance"), var1.getlocal(1), var1.getglobal("dict"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertNotIsInstance$94(PyFrame var1, ThreadState var2) {
      var1.setline(451);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(452);
      var1.getlocal(0).__getattr__("assertNotIsInstance").__call__(var2, var1.getlocal(1), var1.getglobal("dict"));
      var1.setline(453);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertNotIsInstance"), var1.getlocal(1), var1.getglobal("list"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertIn$95(PyFrame var1, ThreadState var2) {
      var1.setline(457);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("monkey"), PyString.fromInterned("banana"), PyString.fromInterned("cow"), PyString.fromInterned("grass"), PyString.fromInterned("seal"), PyString.fromInterned("fish")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(459);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("a"), (PyObject)PyString.fromInterned("abc"));
      var1.setline(460);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)(new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(3)})));
      var1.setline(461);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("monkey"), (PyObject)var1.getlocal(1));
      var1.setline(463);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("d"), (PyObject)PyString.fromInterned("abc"));
      var1.setline(464);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)(new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(3)})));
      var1.setline(465);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("otter"), (PyObject)var1.getlocal(1));
      var1.setline(467);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertIn"), PyString.fromInterned("x"), PyString.fromInterned("abc"));
      var1.setline(468);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertIn"), Py.newInteger(4), new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(3)}));
      var1.setline(469);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertIn"), PyString.fromInterned("elephant"), var1.getlocal(1));
      var1.setline(472);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertNotIn"), PyString.fromInterned("c"), PyString.fromInterned("abc"));
      var1.setline(473);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertNotIn"), Py.newInteger(1), new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(3)}));
      var1.setline(474);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertNotIn"), PyString.fromInterned("cow"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertDictContainsSubset$96(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[7];
      var1.setline(478);
      var1.getlocal(0).__getattr__("assertDictContainsSubset").__call__((ThreadState)var2, (PyObject)(new PyDictionary(Py.EmptyObjects)), (PyObject)(new PyDictionary(Py.EmptyObjects)));
      var1.setline(479);
      var1.getlocal(0).__getattr__("assertDictContainsSubset").__call__((ThreadState)var2, (PyObject)(new PyDictionary(Py.EmptyObjects)), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1)})));
      var1.setline(480);
      var1.getlocal(0).__getattr__("assertDictContainsSubset").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1)})), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1)})));
      var1.setline(481);
      var1.getlocal(0).__getattr__("assertDictContainsSubset").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1)})), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1), PyString.fromInterned("b"), Py.newInteger(2)})));
      var1.setline(482);
      var1.getlocal(0).__getattr__("assertDictContainsSubset").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1), PyString.fromInterned("b"), Py.newInteger(2)})), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1), PyString.fromInterned("b"), Py.newInteger(2)})));
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException")))).__enter__(var2);

      label92: {
         try {
            var1.setline(485);
            var1.getlocal(0).__getattr__("assertDictContainsSubset").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{Py.newInteger(1), PyString.fromInterned("one")})), (PyObject)(new PyDictionary(Py.EmptyObjects)));
         } catch (Throwable var12) {
            if (var3.__exit__(var2, Py.setException(var12, var1))) {
               break label92;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException")))).__enter__(var2);

      label85: {
         try {
            var1.setline(488);
            var1.getlocal(0).__getattr__("assertDictContainsSubset").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(2)})), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1)})));
         } catch (Throwable var11) {
            if (var3.__exit__(var2, Py.setException(var11, var1))) {
               break label85;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException")))).__enter__(var2);

      label78: {
         try {
            var1.setline(491);
            var1.getlocal(0).__getattr__("assertDictContainsSubset").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("c"), Py.newInteger(1)})), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1)})));
         } catch (Throwable var10) {
            if (var3.__exit__(var2, Py.setException(var10, var1))) {
               break label78;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException")))).__enter__(var2);

      label71: {
         try {
            var1.setline(494);
            var1.getlocal(0).__getattr__("assertDictContainsSubset").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1), PyString.fromInterned("c"), Py.newInteger(1)})), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1)})));
         } catch (Throwable var9) {
            if (var3.__exit__(var2, Py.setException(var9, var1))) {
               break label71;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException")))).__enter__(var2);

      label64: {
         try {
            var1.setline(497);
            var1.getlocal(0).__getattr__("assertDictContainsSubset").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1), PyString.fromInterned("c"), Py.newInteger(1)})), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1)})));
         } catch (Throwable var8) {
            if (var3.__exit__(var2, Py.setException(var8, var1))) {
               break label64;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var4 = (var3 = ContextGuard.getManager(var1.getglobal("test_support").__getattr__("check_warnings").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned(""), var1.getglobal("UnicodeWarning")}))))).__enter__(var2);

      label57: {
         try {
            label96: {
               var1.setline(500);
               PyObject var10000 = PyString.fromInterned("").__getattr__("join");
               var1.setline(500);
               PyObject[] var14 = Py.EmptyObjects;
               PyFunction var5 = new PyFunction(var1.f_globals, var14, f$97, (PyObject)null);
               PyObject var10002 = var5.__call__(var2, var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(255)).__iter__());
               Arrays.fill(var14, (Object)null);
               var4 = var10000.__call__(var2, var10002);
               var1.setlocal(1, var4);
               var4 = null;
               ContextManager var15;
               PyObject var13 = (var15 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException")))).__enter__(var2);

               try {
                  var1.setline(503);
                  var1.getlocal(0).__getattr__("assertDictContainsSubset").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("foo"), var1.getlocal(1)})), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("foo"), PyUnicode.fromInterned("�")})));
               } catch (Throwable var6) {
                  if (var15.__exit__(var2, Py.setException(var6, var1))) {
                     break label96;
                  }

                  throw (Throwable)Py.makeException();
               }

               var15.__exit__(var2, (PyException)null);
            }
         } catch (Throwable var7) {
            if (var3.__exit__(var2, Py.setException(var7, var1))) {
               break label57;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$97(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(500);
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

            var6 = (PyObject)var10000;
      }

      var1.setline(500);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(500);
         var1.setline(500);
         var6 = var1.getglobal("chr").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject testAssertEqual$98(PyFrame var1, ThreadState var2) {
      var1.setline(506);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(Py.EmptyObjects), new PyTuple(Py.EmptyObjects)}), new PyTuple(new PyObject[]{new PyDictionary(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)}), new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects)}), new PyTuple(new PyObject[]{var1.getglobal("set").__call__(var2), var1.getglobal("set").__call__(var2)}), new PyTuple(new PyObject[]{var1.getglobal("frozenset").__call__(var2), var1.getglobal("frozenset").__call__(var2)})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(512);
      PyObject var10 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(512);
         PyObject var4 = var10.__iternext__();
         PyObject[] var5;
         PyObject var6;
         String[] var12;
         PyObject var10000;
         if (var4 == null) {
            var1.setline(529);
            var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(Py.EmptyObjects), new PyList(Py.EmptyObjects)}), new PyTuple(new PyObject[]{new PyDictionary(Py.EmptyObjects), var1.getglobal("set").__call__(var2)}), new PyTuple(new PyObject[]{var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{Py.newInteger(4), Py.newInteger(1)}))), var1.getglobal("frozenset").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{Py.newInteger(4), Py.newInteger(2)})))}), new PyTuple(new PyObject[]{var1.getglobal("frozenset").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{Py.newInteger(4), Py.newInteger(5)}))), var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{Py.newInteger(2), Py.newInteger(3)})))}), new PyTuple(new PyObject[]{var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{Py.newInteger(3), Py.newInteger(4)}))), var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{Py.newInteger(5), Py.newInteger(4)})))})});
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(535);
            var10 = var1.getlocal(4).__iter__();

            while(true) {
               var1.setline(535);
               var4 = var10.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(2, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(536);
               var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertEqual"), var1.getlocal(2), var1.getlocal(3));
               var1.setline(537);
               var10000 = var1.getlocal(0).__getattr__("assertRaises");
               var5 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertEqual"), var1.getlocal(2), var1.getlocal(3), PyString.fromInterned("foo")};
               var10000.__call__(var2, var5);
               var1.setline(539);
               var10000 = var1.getlocal(0).__getattr__("assertRaises");
               var5 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertEqual"), var1.getlocal(2), var1.getlocal(3), PyString.fromInterned("foo")};
               var12 = new String[]{"msg"};
               var10000.__call__(var2, var5, var12);
               var5 = null;
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;

         PyException var11;
         try {
            var1.setline(516);
            var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         } catch (Throwable var9) {
            var11 = Py.setException(var9, var1);
            if (!var11.match(var1.getlocal(0).__getattr__("failureException"))) {
               throw var11;
            }

            var1.setline(518);
            var1.getlocal(0).__getattr__("fail").__call__(var2, PyString.fromInterned("assertEqual(%r, %r) failed")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
         }

         try {
            var1.setline(520);
            var10000 = var1.getlocal(0).__getattr__("assertEqual");
            var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), PyString.fromInterned("foo")};
            var12 = new String[]{"msg"};
            var10000.__call__(var2, var5, var12);
            var5 = null;
         } catch (Throwable var8) {
            var11 = Py.setException(var8, var1);
            if (!var11.match(var1.getlocal(0).__getattr__("failureException"))) {
               throw var11;
            }

            var1.setline(522);
            var1.getlocal(0).__getattr__("fail").__call__(var2, PyString.fromInterned("assertEqual(%r, %r) with msg= failed")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
         }

         try {
            var1.setline(524);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("foo"));
         } catch (Throwable var7) {
            var11 = Py.setException(var7, var1);
            if (!var11.match(var1.getlocal(0).__getattr__("failureException"))) {
               throw var11;
            }

            var1.setline(526);
            var1.getlocal(0).__getattr__("fail").__call__(var2, PyString.fromInterned("assertEqual(%r, %r) with third parameter failed")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
         }
      }
   }

   public PyObject testEquality$99(PyFrame var1, ThreadState var2) {
      var1.setline(543);
      var1.getlocal(0).__getattr__("assertListEqual").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects)), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(544);
      var1.getlocal(0).__getattr__("assertTupleEqual").__call__((ThreadState)var2, (PyObject)(new PyTuple(Py.EmptyObjects)), (PyObject)(new PyTuple(Py.EmptyObjects)));
      var1.setline(545);
      var1.getlocal(0).__getattr__("assertSequenceEqual").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects)), (PyObject)(new PyTuple(Py.EmptyObjects)));
      var1.setline(547);
      PyList var3 = new PyList(new PyObject[]{Py.newInteger(0), PyString.fromInterned("a"), new PyList(Py.EmptyObjects)});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(548);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(549);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("unittest").__getattr__("TestCase").__getattr__("failureException"), var1.getlocal(0).__getattr__("assertListEqual"), var1.getlocal(1), var1.getlocal(2));
      var1.setline(551);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("unittest").__getattr__("TestCase").__getattr__("failureException"), var1.getlocal(0).__getattr__("assertListEqual"), var1.getglobal("tuple").__call__(var2, var1.getlocal(1)), var1.getglobal("tuple").__call__(var2, var1.getlocal(2)));
      var1.setline(553);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("unittest").__getattr__("TestCase").__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSequenceEqual"), var1.getlocal(1), var1.getglobal("tuple").__call__(var2, var1.getlocal(2)));
      var1.setline(556);
      var1.getlocal(2).__getattr__("extend").__call__(var2, var1.getlocal(1));
      var1.setline(557);
      var1.getlocal(0).__getattr__("assertListEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(558);
      var1.getlocal(0).__getattr__("assertTupleEqual").__call__(var2, var1.getglobal("tuple").__call__(var2, var1.getlocal(1)), var1.getglobal("tuple").__call__(var2, var1.getlocal(2)));
      var1.setline(559);
      var1.getlocal(0).__getattr__("assertSequenceEqual").__call__(var2, var1.getlocal(1), var1.getglobal("tuple").__call__(var2, var1.getlocal(2)));
      var1.setline(560);
      var1.getlocal(0).__getattr__("assertSequenceEqual").__call__(var2, var1.getglobal("tuple").__call__(var2, var1.getlocal(1)), var1.getlocal(2));
      var1.setline(562);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertListEqual"), var1.getlocal(1), var1.getglobal("tuple").__call__(var2, var1.getlocal(2)));
      var1.setline(564);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertTupleEqual"), var1.getglobal("tuple").__call__(var2, var1.getlocal(1)), var1.getlocal(2));
      var1.setline(566);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertListEqual"), var1.getglobal("None"), var1.getlocal(2));
      var1.setline(567);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertTupleEqual"), var1.getglobal("None"), var1.getglobal("tuple").__call__(var2, var1.getlocal(2)));
      var1.setline(569);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSequenceEqual"), var1.getglobal("None"), var1.getglobal("tuple").__call__(var2, var1.getlocal(2)));
      var1.setline(571);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertListEqual"), Py.newInteger(1), Py.newInteger(1));
      var1.setline(572);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertTupleEqual"), Py.newInteger(1), Py.newInteger(1));
      var1.setline(573);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSequenceEqual"), Py.newInteger(1), Py.newInteger(1));
      var1.setline(576);
      var1.getlocal(0).__getattr__("assertDictEqual").__call__((ThreadState)var2, (PyObject)(new PyDictionary(Py.EmptyObjects)), (PyObject)(new PyDictionary(Py.EmptyObjects)));
      var1.setline(578);
      PyDictionary var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("x"), Py.newInteger(1)});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(579);
      var4 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(580);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("unittest").__getattr__("TestCase").__getattr__("failureException"), var1.getlocal(0).__getattr__("assertDictEqual"), var1.getlocal(3), var1.getlocal(4));
      var1.setline(583);
      var1.getlocal(4).__getattr__("update").__call__(var2, var1.getlocal(3));
      var1.setline(584);
      var1.getlocal(0).__getattr__("assertDictEqual").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.setline(586);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("x"), var5);
      var3 = null;
      var1.setline(587);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertRaises");
      PyObject[] var6 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase").__getattr__("failureException"), var1.getlocal(0).__getattr__("assertDictEqual"), var1.getlocal(3), var1.getlocal(4), PyString.fromInterned("These are unequal")};
      var10000.__call__(var2, var6);
      var1.setline(590);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertDictEqual"), var1.getglobal("None"), var1.getlocal(4));
      var1.setline(591);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertDictEqual"), new PyList(Py.EmptyObjects), var1.getlocal(4));
      var1.setline(592);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertDictEqual"), Py.newInteger(1), Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertSequenceEqualMaxDiff$100(PyFrame var1, ThreadState var2) {
      var1.setline(595);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("maxDiff"), Py.newInteger(80)._mul(Py.newInteger(8)));
      var1.setline(596);
      PyObject var3 = PyString.fromInterned("a")._add(PyString.fromInterned("x")._mul(Py.newInteger(80)._pow(Py.newInteger(2))));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(597);
      var3 = PyString.fromInterned("b")._add(PyString.fromInterned("x")._mul(Py.newInteger(80)._pow(Py.newInteger(2))));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(598);
      var3 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getglobal("difflib").__getattr__("ndiff").__call__(var2, var1.getglobal("pprint").__getattr__("pformat").__call__(var2, var1.getlocal(1)).__getattr__("splitlines").__call__(var2), var1.getglobal("pprint").__getattr__("pformat").__call__(var2, var1.getlocal(2)).__getattr__("splitlines").__call__(var2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(601);
      var3 = var1.getglobal("unittest").__getattr__("case").__getattr__("DIFF_OMITTED")._mod(new PyTuple(new PyObject[]{var1.getglobal("len").__call__(var2, var1.getlocal(3))._add(Py.newInteger(1))}));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(603);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3))._floordiv(Py.newInteger(2));
      var1.getlocal(0).__setattr__("maxDiff", var3);
      var3 = null;

      PyObject var4;
      PyException var8;
      label51: {
         try {
            var1.setline(605);
            var1.getlocal(0).__getattr__("assertSequenceEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         } catch (Throwable var7) {
            var8 = Py.setException(var7, var1);
            if (var8.match(var1.getlocal(0).__getattr__("failureException"))) {
               var4 = var8.value;
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(607);
               var4 = var1.getlocal(5).__getattr__("args").__getitem__(Py.newInteger(0));
               var1.setlocal(6, var4);
               var4 = null;
               break label51;
            }

            throw var8;
         }

         var1.setline(609);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("assertSequenceEqual did not fail."));
      }

      var1.setline(610);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
      PyObject var10002 = var3._lt(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(611);
      var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getlocal(4), var1.getlocal(6));
      var1.setline(613);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3))._mul(Py.newInteger(2));
      var1.getlocal(0).__setattr__("maxDiff", var3);
      var3 = null;

      label43: {
         try {
            var1.setline(615);
            var1.getlocal(0).__getattr__("assertSequenceEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         } catch (Throwable var6) {
            var8 = Py.setException(var6, var1);
            if (var8.match(var1.getlocal(0).__getattr__("failureException"))) {
               var4 = var8.value;
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(617);
               var4 = var1.getlocal(5).__getattr__("args").__getitem__(Py.newInteger(0));
               var1.setlocal(6, var4);
               var4 = null;
               break label43;
            }

            throw var8;
         }

         var1.setline(619);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("assertSequenceEqual did not fail."));
      }

      var1.setline(620);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
      var10002 = var3._gt(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(621);
      var1.getlocal(0).__getattr__("assertNotIn").__call__(var2, var1.getlocal(4), var1.getlocal(6));
      var1.setline(623);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("maxDiff", var3);
      var3 = null;

      label35: {
         try {
            var1.setline(625);
            var1.getlocal(0).__getattr__("assertSequenceEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         } catch (Throwable var5) {
            var8 = Py.setException(var5, var1);
            if (var8.match(var1.getlocal(0).__getattr__("failureException"))) {
               var4 = var8.value;
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(627);
               var4 = var1.getlocal(5).__getattr__("args").__getitem__(Py.newInteger(0));
               var1.setlocal(6, var4);
               var4 = null;
               break label35;
            }

            throw var8;
         }

         var1.setline(629);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("assertSequenceEqual did not fail."));
      }

      var1.setline(630);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
      var10002 = var3._gt(var1.getglobal("len").__call__(var2, var1.getlocal(3)));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(631);
      var1.getlocal(0).__getattr__("assertNotIn").__call__(var2, var1.getlocal(4), var1.getlocal(6));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testTruncateMessage$101(PyFrame var1, ThreadState var2) {
      var1.setline(634);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"maxDiff", var3);
      var3 = null;
      var1.setline(635);
      PyObject var4 = var1.getlocal(0).__getattr__("_truncateMessage").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)PyString.fromInterned("bar"));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(636);
      var4 = var1.getglobal("unittest").__getattr__("case").__getattr__("DIFF_OMITTED")._mod(var1.getglobal("len").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bar")));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(637);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), PyString.fromInterned("foo")._add(var1.getlocal(2)));
      var1.setline(639);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("maxDiff", var4);
      var3 = null;
      var1.setline(640);
      var4 = var1.getlocal(0).__getattr__("_truncateMessage").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)PyString.fromInterned("bar"));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(641);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foobar"));
      var1.setline(643);
      var3 = Py.newInteger(4);
      var1.getlocal(0).__setattr__((String)"maxDiff", var3);
      var3 = null;
      var1.setline(644);
      var4 = var1.getlocal(0).__getattr__("_truncateMessage").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)PyString.fromInterned("bar"));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(645);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foobar"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertDictEqualTruncates$102(PyFrame var1, ThreadState var2) {
      var1.setline(648);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestCase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("assertEqual"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(649);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, truncate$103, (PyObject)null);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(651);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("_truncateMessage", var3);
      var3 = null;

      label19: {
         try {
            var1.setline(653);
            var1.getlocal(1).__getattr__("assertDictEqual").__call__((ThreadState)var2, (PyObject)(new PyDictionary(Py.EmptyObjects)), (PyObject)(new PyDictionary(new PyObject[]{Py.newInteger(1), Py.newInteger(0)})));
         } catch (Throwable var5) {
            PyException var8 = Py.setException(var5, var1);
            if (var8.match(var1.getlocal(0).__getattr__("failureException"))) {
               PyObject var4 = var8.value;
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(655);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(3)), (PyObject)PyString.fromInterned("foo"));
               break label19;
            }

            throw var8;
         }

         var1.setline(657);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("assertDictEqual did not fail"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject truncate$103(PyFrame var1, ThreadState var2) {
      var1.setline(650);
      PyString var3 = PyString.fromInterned("foo");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testAssertMultiLineEqualTruncates$104(PyFrame var1, ThreadState var2) {
      var1.setline(660);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestCase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("assertEqual"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(661);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, truncate$105, (PyObject)null);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(663);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("_truncateMessage", var3);
      var3 = null;

      label19: {
         try {
            var1.setline(665);
            var1.getlocal(1).__getattr__("assertMultiLineEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)PyString.fromInterned("bar"));
         } catch (Throwable var5) {
            PyException var8 = Py.setException(var5, var1);
            if (var8.match(var1.getlocal(0).__getattr__("failureException"))) {
               PyObject var4 = var8.value;
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(667);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(3)), (PyObject)PyString.fromInterned("foo"));
               break label19;
            }

            throw var8;
         }

         var1.setline(669);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("assertMultiLineEqual did not fail"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject truncate$105(PyFrame var1, ThreadState var2) {
      var1.setline(662);
      PyString var3 = PyString.fromInterned("foo");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testAssertEqual_diffThreshold$106(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.f_exits = new PyObject[2];
      var1.setline(673);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getderef(0).__getattr__("_diffThreshold"), Py.newInteger(2)._pow(Py.newInteger(16)));
      var1.setline(675);
      PyObject var3 = var1.getglobal("None");
      var1.getderef(0).__setattr__("maxDiff", var3);
      var3 = null;
      var1.setline(678);
      var3 = var1.getderef(0).__getattr__("_diffThreshold");
      var1.setderef(2, var3);
      var3 = null;
      var1.setline(679);
      var3 = Py.newInteger(2)._pow(Py.newInteger(8));
      var1.getderef(0).__setattr__("_diffThreshold", var3);
      var3 = null;
      var1.setline(680);
      PyObject var10000 = var1.getderef(0).__getattr__("addCleanup");
      var1.setline(680);
      PyObject[] var9 = Py.EmptyObjects;
      PyObject[] var10005 = var9;
      PyObject var10004 = var1.f_globals;
      PyCode var10006 = f$107;
      var9 = new PyObject[]{var1.getclosure(0), var1.getclosure(2)};
      var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var10004, var10005, var10006, var9)));
      var1.setline(683);
      var3 = PyUnicode.fromInterned("x")._mul(Py.newInteger(2)._pow(Py.newInteger(7)));
      var1.setlocal(1, var3);
      var3 = null;
      ContextManager var10;
      PyObject var4 = (var10 = ContextGuard.getManager(var1.getderef(0).__getattr__("assertRaises").__call__(var2, var1.getderef(0).__getattr__("failureException")))).__enter__(var2);

      label29: {
         try {
            var1.setlocal(2, var4);
            var1.setline(685);
            var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned("a")), var1.getlocal(1)._add(PyString.fromInterned("b")));
         } catch (Throwable var7) {
            if (var10.__exit__(var2, Py.setException(var7, var1))) {
               break label29;
            }

            throw (Throwable)Py.makeException();
         }

         var10.__exit__(var2, (PyException)null);
      }

      var1.setline(686);
      var1.getderef(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^"), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(2).__getattr__("exception")));
      var1.setline(687);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned("a")), var1.getlocal(1)._add(PyString.fromInterned("a")));
      var1.setline(690);
      var3 = PyUnicode.fromInterned("x")._mul(Py.newInteger(2)._pow(Py.newInteger(9)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(694);
      var9 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var9, explodingTruncation$108, (PyObject)null);
      var1.setlocal(3, var11);
      var3 = null;
      var1.setline(696);
      var3 = var1.getderef(0).__getattr__("_truncateMessage");
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(697);
      var3 = var1.getlocal(3);
      var1.getderef(0).__setattr__("_truncateMessage", var3);
      var3 = null;
      var1.setline(698);
      var10000 = var1.getderef(0).__getattr__("addCleanup");
      var1.setline(698);
      var9 = Py.EmptyObjects;
      var10005 = var9;
      var10004 = var1.f_globals;
      var10006 = f$109;
      var9 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var10004, var10005, var10006, var9)));
      var1.setline(700);
      PyTuple var12 = new PyTuple(new PyObject[]{var1.getlocal(1)._add(PyString.fromInterned("a")), var1.getlocal(1)._add(PyString.fromInterned("b"))});
      PyObject[] var8 = Py.unpackSequence(var12, 2);
      PyObject var5 = var8[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var4 = (var10 = ContextGuard.getManager(var1.getderef(0).__getattr__("assertRaises").__call__(var2, var1.getderef(0).__getattr__("failureException")))).__enter__(var2);

      label22: {
         try {
            var1.setlocal(2, var4);
            var1.setline(702);
            var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4), var1.getlocal(5));
         } catch (Throwable var6) {
            if (var10.__exit__(var2, Py.setException(var6, var1))) {
               break label22;
            }

            throw (Throwable)Py.makeException();
         }

         var10.__exit__(var2, (PyException)null);
      }

      var1.setline(703);
      var1.getderef(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^"), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(2).__getattr__("exception")));
      var1.setline(704);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(2).__getattr__("exception")), PyString.fromInterned("%r != %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})));
      var1.setline(705);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1)._add(PyString.fromInterned("a")), var1.getlocal(1)._add(PyString.fromInterned("a")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$107(PyFrame var1, ThreadState var2) {
      var1.setline(680);
      PyObject var3 = var1.getglobal("setattr").__call__((ThreadState)var2, var1.getderef(0), (PyObject)PyString.fromInterned("_diffThreshold"), (PyObject)var1.getderef(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject explodingTruncation$108(PyFrame var1, ThreadState var2) {
      var1.setline(695);
      throw Py.makeException(var1.getglobal("SystemError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("this should not be raised")));
   }

   public PyObject f$109(PyFrame var1, ThreadState var2) {
      var1.setline(698);
      PyObject var3 = var1.getglobal("setattr").__call__((ThreadState)var2, var1.getderef(0), (PyObject)PyString.fromInterned("_truncateMessage"), (PyObject)var1.getderef(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testAssertItemsEqual$110(PyFrame var1, ThreadState var2) {
      var1.setline(708);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(709);
      var1.getlocal(0).__getattr__("assertItemsEqual").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(3)})), (PyObject)(new PyList(new PyObject[]{Py.newInteger(3), Py.newInteger(2), Py.newInteger(1)})));
      var1.setline(710);
      var1.getlocal(0).__getattr__("assertItemsEqual").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("bar"), PyString.fromInterned("baz")})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("bar"), PyString.fromInterned("baz"), PyString.fromInterned("foo")})));
      var1.setline(711);
      var1.getlocal(0).__getattr__("assertItemsEqual").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1), var1.getlocal(1), Py.newInteger(2), Py.newInteger(2), Py.newInteger(3)})), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), Py.newInteger(2), Py.newInteger(3), var1.getlocal(1), Py.newInteger(2)})));
      var1.setline(712);
      var1.getlocal(0).__getattr__("assertItemsEqual").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{Py.newInteger(1), PyString.fromInterned("2"), PyString.fromInterned("a"), PyString.fromInterned("a")})), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("2"), var1.getglobal("True"), PyString.fromInterned("a")})));
      var1.setline(713);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertItemsEqual"), (new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(2)}))._add((new PyList(new PyObject[]{Py.newInteger(3)}))._mul(Py.newInteger(100))), (new PyList(new PyObject[]{Py.newInteger(1)}))._mul(Py.newInteger(100))._add(new PyList(new PyObject[]{Py.newInteger(2), Py.newInteger(3)})));
      var1.setline(715);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertItemsEqual"), new PyList(new PyObject[]{Py.newInteger(1), PyString.fromInterned("2"), PyString.fromInterned("a"), PyString.fromInterned("a")}), new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("2"), var1.getglobal("True"), Py.newInteger(1)}));
      var1.setline(717);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertItemsEqual"), new PyList(new PyObject[]{Py.newInteger(10)}), new PyList(new PyObject[]{Py.newInteger(10), Py.newInteger(11)}));
      var1.setline(719);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertItemsEqual"), new PyList(new PyObject[]{Py.newInteger(10), Py.newInteger(11)}), new PyList(new PyObject[]{Py.newInteger(10)}));
      var1.setline(721);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertItemsEqual"), new PyList(new PyObject[]{Py.newInteger(10), Py.newInteger(11), Py.newInteger(10)}), new PyList(new PyObject[]{Py.newInteger(10), Py.newInteger(11)}));
      var1.setline(725);
      var1.getlocal(0).__getattr__("assertItemsEqual").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(2)}), new PyList(new PyObject[]{Py.newInteger(3), Py.newInteger(4)}), Py.newInteger(0)})), (PyObject)(new PyList(new PyObject[]{var1.getglobal("False"), new PyList(new PyObject[]{Py.newInteger(3), Py.newInteger(4)}), new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(2)})})));
      var1.setline(727);
      var1.getlocal(0).__getattr__("assertItemsEqual").__call__(var2, var1.getglobal("iter").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(2), new PyList(Py.EmptyObjects), Py.newInteger(3), Py.newInteger(4)}))), var1.getglobal("iter").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(2), new PyList(Py.EmptyObjects), Py.newInteger(3), Py.newInteger(4)}))));
      var1.setline(731);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertItemsEqual"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getglobal("divmod"), PyString.fromInterned("x"), Py.newInteger(1), Py.newImaginary(5.0), Py.newImaginary(2.0), var1.getglobal("frozenset").__call__(var2)}));
      var1.setline(734);
      var1.getlocal(0).__getattr__("assertItemsEqual").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1)}), new PyDictionary(new PyObject[]{PyString.fromInterned("b"), Py.newInteger(2)})})), (PyObject)(new PyList(new PyObject[]{new PyDictionary(new PyObject[]{PyString.fromInterned("b"), Py.newInteger(2)}), new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1)})})));
      var1.setline(736);
      var1.getlocal(0).__getattr__("assertItemsEqual").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{Py.newInteger(1), PyString.fromInterned("x"), var1.getglobal("divmod"), new PyList(Py.EmptyObjects)})), (PyObject)(new PyList(new PyObject[]{var1.getglobal("divmod"), new PyList(Py.EmptyObjects), PyString.fromInterned("x"), Py.newInteger(1)})));
      var1.setline(737);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertItemsEqual"), new PyList(Py.EmptyObjects), new PyList(new PyObject[]{var1.getglobal("divmod"), new PyList(Py.EmptyObjects), PyString.fromInterned("x"), Py.newInteger(1), Py.newImaginary(5.0), Py.newImaginary(2.0), var1.getglobal("set").__call__(var2)}));
      var1.setline(739);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertItemsEqual"), new PyList(new PyObject[]{new PyList(new PyObject[]{Py.newInteger(1)})}), new PyList(new PyObject[]{new PyList(new PyObject[]{Py.newInteger(2)})}));
      var1.setline(743);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertItemsEqual"), new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(1), Py.newInteger(2)}), new PyList(new PyObject[]{Py.newInteger(2), Py.newInteger(1)}));
      var1.setline(745);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertItemsEqual"), new PyList(new PyObject[]{Py.newInteger(1), Py.newInteger(1), PyString.fromInterned("2"), PyString.fromInterned("a"), PyString.fromInterned("a")}), new PyList(new PyObject[]{PyString.fromInterned("2"), PyString.fromInterned("2"), var1.getglobal("True"), PyString.fromInterned("a")}));
      var1.setline(747);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertItemsEqual"), new PyList(new PyObject[]{Py.newInteger(1), new PyDictionary(new PyObject[]{PyString.fromInterned("b"), Py.newInteger(2)}), var1.getglobal("None"), var1.getglobal("True")}), new PyList(new PyObject[]{new PyDictionary(new PyObject[]{PyString.fromInterned("b"), Py.newInteger(2)}), var1.getglobal("True"), var1.getglobal("None")}));
      var1.setline(752);
      PyList var4 = new PyList(new PyObject[]{new PySet(new PyObject[]{Py.newInteger(2), Py.newInteger(4)}), new PySet(new PyObject[]{Py.newInteger(1), Py.newInteger(2)})});
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(753);
      var3 = var1.getlocal(1).__getslice__((PyObject)null, (PyObject)null, Py.newInteger(-1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(754);
      var1.getlocal(0).__getattr__("assertItemsEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(758);
      var3 = var1.getglobal("set").__call__(var2, var1.getglobal("unittest").__getattr__("util").__getattr__("_count_diff_all_purpose").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aaabccd"), (PyObject)PyString.fromInterned("abbbcce")));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(759);
      PySet var5 = new PySet(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(1), PyString.fromInterned("a")}), new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(3), PyString.fromInterned("b")}), new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(0), PyString.fromInterned("d")}), new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(1), PyString.fromInterned("e")})});
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(760);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.setline(762);
      var3 = var1.getglobal("unittest").__getattr__("util").__getattr__("_count_diff_all_purpose").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{new PyList(Py.EmptyObjects)})), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(763);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(0), new PyList(Py.EmptyObjects)})})));
      var1.setline(765);
      var3 = var1.getglobal("set").__call__(var2, var1.getglobal("unittest").__getattr__("util").__getattr__("_count_diff_hashable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("aaabccd"), (PyObject)PyString.fromInterned("abbbcce")));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(766);
      var5 = new PySet(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(3), Py.newInteger(1), PyString.fromInterned("a")}), new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(3), PyString.fromInterned("b")}), new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(0), PyString.fromInterned("d")}), new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(1), PyString.fromInterned("e")})});
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(767);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertSetEqual$111(PyFrame var1, ThreadState var2) {
      var1.setline(770);
      PyObject var3 = var1.getglobal("set").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(771);
      var3 = var1.getglobal("set").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(772);
      var1.getlocal(0).__getattr__("assertSetEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(774);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSetEqual"), var1.getglobal("None"), var1.getlocal(2));
      var1.setline(775);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSetEqual"), new PyList(Py.EmptyObjects), var1.getlocal(2));
      var1.setline(776);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSetEqual"), var1.getlocal(1), var1.getglobal("None"));
      var1.setline(777);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSetEqual"), var1.getlocal(1), new PyList(Py.EmptyObjects));
      var1.setline(779);
      var3 = var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a")})));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(780);
      var3 = var1.getglobal("set").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(781);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSetEqual"), var1.getlocal(1), var1.getlocal(2));
      var1.setline(783);
      var3 = var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a")})));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(784);
      var3 = var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a")})));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(785);
      var1.getlocal(0).__getattr__("assertSetEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(787);
      var3 = var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a")})));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(788);
      var3 = var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b")})));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(789);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSetEqual"), var1.getlocal(1), var1.getlocal(2));
      var1.setline(791);
      var3 = var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a")})));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(792);
      var3 = var1.getglobal("frozenset").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b")})));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(793);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSetEqual"), var1.getlocal(1), var1.getlocal(2));
      var1.setline(795);
      var3 = var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b")})));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(796);
      var3 = var1.getglobal("frozenset").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b")})));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(797);
      var1.getlocal(0).__getattr__("assertSetEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(799);
      var3 = var1.getglobal("set").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(800);
      PyString var4 = PyString.fromInterned("foo");
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(801);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSetEqual"), var1.getlocal(1), var1.getlocal(2));
      var1.setline(802);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSetEqual"), var1.getlocal(2), var1.getlocal(1));
      var1.setline(805);
      var3 = var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(1)}), new PyTuple(new PyObject[]{Py.newInteger(2), Py.newInteger(3)})})));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(806);
      var3 = var1.getglobal("set").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(4), Py.newInteger(5)})})));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(807);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertSetEqual"), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testInequality$112(PyFrame var1, ThreadState var2) {
      var1.setline(811);
      var1.getlocal(0).__getattr__("assertGreater").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)Py.newInteger(1));
      var1.setline(812);
      var1.getlocal(0).__getattr__("assertGreaterEqual").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)Py.newInteger(1));
      var1.setline(813);
      var1.getlocal(0).__getattr__("assertGreaterEqual").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(1));
      var1.setline(814);
      var1.getlocal(0).__getattr__("assertLess").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
      var1.setline(815);
      var1.getlocal(0).__getattr__("assertLessEqual").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
      var1.setline(816);
      var1.getlocal(0).__getattr__("assertLessEqual").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(1));
      var1.setline(817);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreater"), Py.newInteger(1), Py.newInteger(2));
      var1.setline(818);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreater"), Py.newInteger(1), Py.newInteger(1));
      var1.setline(819);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreaterEqual"), Py.newInteger(1), Py.newInteger(2));
      var1.setline(820);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLess"), Py.newInteger(2), Py.newInteger(1));
      var1.setline(821);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLess"), Py.newInteger(1), Py.newInteger(1));
      var1.setline(822);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLessEqual"), Py.newInteger(2), Py.newInteger(1));
      var1.setline(825);
      var1.getlocal(0).__getattr__("assertGreater").__call__((ThreadState)var2, (PyObject)Py.newFloat(1.1), (PyObject)Py.newFloat(1.0));
      var1.setline(826);
      var1.getlocal(0).__getattr__("assertGreaterEqual").__call__((ThreadState)var2, (PyObject)Py.newFloat(1.1), (PyObject)Py.newFloat(1.0));
      var1.setline(827);
      var1.getlocal(0).__getattr__("assertGreaterEqual").__call__((ThreadState)var2, (PyObject)Py.newFloat(1.0), (PyObject)Py.newFloat(1.0));
      var1.setline(828);
      var1.getlocal(0).__getattr__("assertLess").__call__((ThreadState)var2, (PyObject)Py.newFloat(1.0), (PyObject)Py.newFloat(1.1));
      var1.setline(829);
      var1.getlocal(0).__getattr__("assertLessEqual").__call__((ThreadState)var2, (PyObject)Py.newFloat(1.0), (PyObject)Py.newFloat(1.1));
      var1.setline(830);
      var1.getlocal(0).__getattr__("assertLessEqual").__call__((ThreadState)var2, (PyObject)Py.newFloat(1.0), (PyObject)Py.newFloat(1.0));
      var1.setline(831);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreater"), Py.newFloat(1.0), Py.newFloat(1.1));
      var1.setline(832);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreater"), Py.newFloat(1.0), Py.newFloat(1.0));
      var1.setline(833);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreaterEqual"), Py.newFloat(1.0), Py.newFloat(1.1));
      var1.setline(834);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLess"), Py.newFloat(1.1), Py.newFloat(1.0));
      var1.setline(835);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLess"), Py.newFloat(1.0), Py.newFloat(1.0));
      var1.setline(836);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLessEqual"), Py.newFloat(1.1), Py.newFloat(1.0));
      var1.setline(839);
      var1.getlocal(0).__getattr__("assertGreater").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bug"), (PyObject)PyString.fromInterned("ant"));
      var1.setline(840);
      var1.getlocal(0).__getattr__("assertGreaterEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bug"), (PyObject)PyString.fromInterned("ant"));
      var1.setline(841);
      var1.getlocal(0).__getattr__("assertGreaterEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ant"), (PyObject)PyString.fromInterned("ant"));
      var1.setline(842);
      var1.getlocal(0).__getattr__("assertLess").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ant"), (PyObject)PyString.fromInterned("bug"));
      var1.setline(843);
      var1.getlocal(0).__getattr__("assertLessEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ant"), (PyObject)PyString.fromInterned("bug"));
      var1.setline(844);
      var1.getlocal(0).__getattr__("assertLessEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ant"), (PyObject)PyString.fromInterned("ant"));
      var1.setline(845);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreater"), PyString.fromInterned("ant"), PyString.fromInterned("bug"));
      var1.setline(846);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreater"), PyString.fromInterned("ant"), PyString.fromInterned("ant"));
      var1.setline(847);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreaterEqual"), PyString.fromInterned("ant"), PyString.fromInterned("bug"));
      var1.setline(848);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLess"), PyString.fromInterned("bug"), PyString.fromInterned("ant"));
      var1.setline(849);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLess"), PyString.fromInterned("ant"), PyString.fromInterned("ant"));
      var1.setline(850);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLessEqual"), PyString.fromInterned("bug"), PyString.fromInterned("ant"));
      var1.setline(853);
      var1.getlocal(0).__getattr__("assertGreater").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("bug"), (PyObject)PyUnicode.fromInterned("ant"));
      var1.setline(854);
      var1.getlocal(0).__getattr__("assertGreaterEqual").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("bug"), (PyObject)PyUnicode.fromInterned("ant"));
      var1.setline(855);
      var1.getlocal(0).__getattr__("assertGreaterEqual").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("ant"), (PyObject)PyUnicode.fromInterned("ant"));
      var1.setline(856);
      var1.getlocal(0).__getattr__("assertLess").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("ant"), (PyObject)PyUnicode.fromInterned("bug"));
      var1.setline(857);
      var1.getlocal(0).__getattr__("assertLessEqual").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("ant"), (PyObject)PyUnicode.fromInterned("bug"));
      var1.setline(858);
      var1.getlocal(0).__getattr__("assertLessEqual").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("ant"), (PyObject)PyUnicode.fromInterned("ant"));
      var1.setline(859);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreater"), PyUnicode.fromInterned("ant"), PyUnicode.fromInterned("bug"));
      var1.setline(860);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreater"), PyUnicode.fromInterned("ant"), PyUnicode.fromInterned("ant"));
      var1.setline(861);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreaterEqual"), PyUnicode.fromInterned("ant"), PyUnicode.fromInterned("bug"));
      var1.setline(863);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLess"), PyUnicode.fromInterned("bug"), PyUnicode.fromInterned("ant"));
      var1.setline(864);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLess"), PyUnicode.fromInterned("ant"), PyUnicode.fromInterned("ant"));
      var1.setline(865);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLessEqual"), PyUnicode.fromInterned("bug"), PyUnicode.fromInterned("ant"));
      var1.setline(868);
      var1.getlocal(0).__getattr__("assertGreater").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bug"), (PyObject)PyUnicode.fromInterned("ant"));
      var1.setline(869);
      var1.getlocal(0).__getattr__("assertGreater").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("bug"), (PyObject)PyString.fromInterned("ant"));
      var1.setline(870);
      var1.getlocal(0).__getattr__("assertGreaterEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bug"), (PyObject)PyUnicode.fromInterned("ant"));
      var1.setline(871);
      var1.getlocal(0).__getattr__("assertGreaterEqual").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("bug"), (PyObject)PyString.fromInterned("ant"));
      var1.setline(872);
      var1.getlocal(0).__getattr__("assertGreaterEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ant"), (PyObject)PyUnicode.fromInterned("ant"));
      var1.setline(873);
      var1.getlocal(0).__getattr__("assertGreaterEqual").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("ant"), (PyObject)PyString.fromInterned("ant"));
      var1.setline(874);
      var1.getlocal(0).__getattr__("assertLess").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ant"), (PyObject)PyUnicode.fromInterned("bug"));
      var1.setline(875);
      var1.getlocal(0).__getattr__("assertLess").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("ant"), (PyObject)PyString.fromInterned("bug"));
      var1.setline(876);
      var1.getlocal(0).__getattr__("assertLessEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ant"), (PyObject)PyUnicode.fromInterned("bug"));
      var1.setline(877);
      var1.getlocal(0).__getattr__("assertLessEqual").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("ant"), (PyObject)PyString.fromInterned("bug"));
      var1.setline(878);
      var1.getlocal(0).__getattr__("assertLessEqual").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ant"), (PyObject)PyUnicode.fromInterned("ant"));
      var1.setline(879);
      var1.getlocal(0).__getattr__("assertLessEqual").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("ant"), (PyObject)PyString.fromInterned("ant"));
      var1.setline(880);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreater"), PyString.fromInterned("ant"), PyUnicode.fromInterned("bug"));
      var1.setline(881);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreater"), PyUnicode.fromInterned("ant"), PyString.fromInterned("bug"));
      var1.setline(882);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreater"), PyString.fromInterned("ant"), PyUnicode.fromInterned("ant"));
      var1.setline(883);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreater"), PyUnicode.fromInterned("ant"), PyString.fromInterned("ant"));
      var1.setline(884);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreaterEqual"), PyString.fromInterned("ant"), PyUnicode.fromInterned("bug"));
      var1.setline(886);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertGreaterEqual"), PyUnicode.fromInterned("ant"), PyString.fromInterned("bug"));
      var1.setline(888);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLess"), PyString.fromInterned("bug"), PyUnicode.fromInterned("ant"));
      var1.setline(889);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLess"), PyUnicode.fromInterned("bug"), PyString.fromInterned("ant"));
      var1.setline(890);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLess"), PyString.fromInterned("ant"), PyUnicode.fromInterned("ant"));
      var1.setline(891);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLess"), PyUnicode.fromInterned("ant"), PyString.fromInterned("ant"));
      var1.setline(892);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLessEqual"), PyString.fromInterned("bug"), PyUnicode.fromInterned("ant"));
      var1.setline(893);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertLessEqual"), PyUnicode.fromInterned("bug"), PyString.fromInterned("ant"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertMultiLineEqual$113(PyFrame var1, ThreadState var2) {
      var1.setline(896);
      PyString var3 = PyString.fromInterned("http://www.python.org/doc/2.3/lib/module-unittest.html\ntest case\n    A test case is the smallest unit of testing. [...]\n");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(901);
      var3 = PyString.fromInterned("http://www.python.org/doc/2.4.1/lib/module-unittest.html\ntest case\n    A test case is the smallest unit of testing. [...] You may provide your\n    own implementation that does not subclass from TestCase, of course.\n");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(907);
      var3 = PyString.fromInterned("- http://www.python.org/doc/2.3/lib/module-unittest.html\n?                             ^\n+ http://www.python.org/doc/2.4.1/lib/module-unittest.html\n?                             ^^^\n  test case\n-     A test case is the smallest unit of testing. [...]\n+     A test case is the smallest unit of testing. [...] You may provide your\n?                                                       +++++++++++++++++++++\n+     own implementation that does not subclass from TestCase, of course.\n");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(918);
      PyObject var8 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("maxDiff", var8);
      var3 = null;
      var1.setline(919);
      PyObject[] var10002 = new PyObject[2];
      var1.setline(919);
      PyObject[] var9 = Py.EmptyObjects;
      var10002[0] = new PyFunction(var1.f_globals, var9, f$114);
      var1.setline(919);
      var9 = Py.EmptyObjects;
      var10002[1] = new PyFunction(var1.f_globals, var9, f$115);
      var8 = (new PyTuple(var10002)).__iter__();

      while(true) {
         var1.setline(919);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);

         try {
            var1.setline(921);
            var1.getlocal(0).__getattr__("assertMultiLineEqual").__call__(var2, var1.getlocal(4).__call__(var2, var1.getlocal(1)), var1.getlocal(4).__call__(var2, var1.getlocal(2)));
         } catch (Throwable var7) {
            PyException var5 = Py.setException(var7, var1);
            if (!var5.match(var1.getlocal(0).__getattr__("failureException"))) {
               throw var5;
            }

            PyObject var6 = var5.value;
            var1.setlocal(5, var6);
            var6 = null;
            var1.setline(925);
            var6 = var1.getglobal("str").__call__(var2, var1.getlocal(5)).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf8")).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(1));
            var1.setlocal(6, var6);
            var6 = null;
            var1.setline(929);
            PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
            var6 = var1.getlocal(3);
            PyObject var10 = var6._eq(var1.getlocal(6));
            var6 = null;
            var10000.__call__(var2, var10);
         }
      }
   }

   public PyObject f$114(PyFrame var1, ThreadState var2) {
      var1.setline(919);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$115(PyFrame var1, ThreadState var2) {
      var1.setline(919);
      PyObject var3 = var1.getlocal(0).__getattr__("decode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf8"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testAsertEqualSingleLine$116(PyFrame var1, ThreadState var2) {
      var1.setline(932);
      PyUnicode var3 = PyUnicode.fromInterned("laden swallows fly slowly");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(933);
      var3 = PyUnicode.fromInterned("unladen swallows fly quickly");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(934);
      PyString var6 = PyString.fromInterned("- laden swallows fly slowly\n?                    ^^^^\n+ unladen swallows fly quickly\n? ++                   ^^^^^\n");
      var1.setlocal(3, var6);
      var3 = null;

      try {
         var1.setline(941);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(var1.getlocal(0).__getattr__("failureException"))) {
            throw var7;
         }

         PyObject var4 = var7.value;
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(943);
         var4 = var1.getglobal("str").__call__(var2, var1.getlocal(4)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"), (PyObject)Py.newInteger(1)).__getitem__(Py.newInteger(1));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(944);
         PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
         var4 = var1.getlocal(3);
         PyObject var10002 = var4._eq(var1.getlocal(5));
         var4 = null;
         var10000.__call__(var2, var10002);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertIsNone$117(PyFrame var1, ThreadState var2) {
      var1.setline(947);
      var1.getlocal(0).__getattr__("assertIsNone").__call__(var2, var1.getglobal("None"));
      var1.setline(948);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertIsNone"), var1.getglobal("False"));
      var1.setline(949);
      var1.getlocal(0).__getattr__("assertIsNotNone").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DjZoPloGears on Rails"));
      var1.setline(950);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertIsNotNone"), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertRegexpMatches$118(PyFrame var1, ThreadState var2) {
      var1.setline(953);
      var1.getlocal(0).__getattr__("assertRegexpMatches").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("asdfabasdf"), (PyObject)PyString.fromInterned("ab+"));
      var1.setline(954);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getlocal(0).__getattr__("failureException"), var1.getlocal(0).__getattr__("assertRegexpMatches"), PyString.fromInterned("saaas"), PyString.fromInterned("aaaa"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testAssertRaisesRegexp$119(PyFrame var1, ThreadState var2) {
      var1.setline(958);
      PyObject[] var3 = new PyObject[]{var1.getglobal("Exception")};
      PyObject var4 = Py.makeClass("ExceptionMock", var3, ExceptionMock$120);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(961);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = Stub$121;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(964);
      var1.getlocal(0).__getattr__("assertRaisesRegexp").__call__(var2, var1.getderef(0), var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("expect$")), var1.getlocal(1));
      var1.setline(965);
      var1.getlocal(0).__getattr__("assertRaisesRegexp").__call__((ThreadState)var2, var1.getderef(0), (PyObject)PyString.fromInterned("expect$"), (PyObject)var1.getlocal(1));
      var1.setline(966);
      var1.getlocal(0).__getattr__("assertRaisesRegexp").__call__((ThreadState)var2, var1.getderef(0), (PyObject)PyUnicode.fromInterned("expect$"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ExceptionMock$120(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(959);
      return var1.getf_locals();
   }

   public PyObject Stub$121(PyFrame var1, ThreadState var2) {
      var1.setline(962);
      throw Py.makeException(var1.getderef(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("We expect")));
   }

   public PyObject testAssertNotRaisesRegexp$122(PyFrame var1, ThreadState var2) {
      var1.setline(969);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertRaisesRegexp");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), PyString.fromInterned("^Exception not raised$"), var1.getlocal(0).__getattr__("assertRaisesRegexp"), var1.getglobal("Exception"), var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("x")), null};
      var1.setline(972);
      PyObject[] var4 = Py.EmptyObjects;
      var3[5] = new PyFunction(var1.f_globals, var4, f$123);
      var10000.__call__(var2, var3);
      var1.setline(973);
      var10000 = var1.getlocal(0).__getattr__("assertRaisesRegexp");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), PyString.fromInterned("^Exception not raised$"), var1.getlocal(0).__getattr__("assertRaisesRegexp"), var1.getglobal("Exception"), PyString.fromInterned("x"), null};
      var1.setline(976);
      var4 = Py.EmptyObjects;
      var3[5] = new PyFunction(var1.f_globals, var4, f$124);
      var10000.__call__(var2, var3);
      var1.setline(977);
      var10000 = var1.getlocal(0).__getattr__("assertRaisesRegexp");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), PyString.fromInterned("^Exception not raised$"), var1.getlocal(0).__getattr__("assertRaisesRegexp"), var1.getglobal("Exception"), PyUnicode.fromInterned("x"), null};
      var1.setline(980);
      var4 = Py.EmptyObjects;
      var3[5] = new PyFunction(var1.f_globals, var4, f$125);
      var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$123(PyFrame var1, ThreadState var2) {
      var1.setline(972);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$124(PyFrame var1, ThreadState var2) {
      var1.setline(976);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$125(PyFrame var1, ThreadState var2) {
      var1.setline(980);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testAssertRaisesRegexpMismatch$126(PyFrame var1, ThreadState var2) {
      var1.setline(983);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, Stub$127, (PyObject)null);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(986);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertRaisesRegexp");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), PyString.fromInterned("\"\\^Expected\\$\" does not match \"Unexpected\""), var1.getlocal(0).__getattr__("assertRaisesRegexp"), var1.getglobal("Exception"), PyString.fromInterned("^Expected$"), var1.getlocal(1)};
      var10000.__call__(var2, var3);
      var1.setline(991);
      var10000 = var1.getlocal(0).__getattr__("assertRaisesRegexp");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), PyString.fromInterned("\"\\^Expected\\$\" does not match \"Unexpected\""), var1.getlocal(0).__getattr__("assertRaisesRegexp"), var1.getglobal("Exception"), PyUnicode.fromInterned("^Expected$"), var1.getlocal(1)};
      var10000.__call__(var2, var3);
      var1.setline(996);
      var10000 = var1.getlocal(0).__getattr__("assertRaisesRegexp");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("failureException"), PyString.fromInterned("\"\\^Expected\\$\" does not match \"Unexpected\""), var1.getlocal(0).__getattr__("assertRaisesRegexp"), var1.getglobal("Exception"), var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^Expected$")), var1.getlocal(1)};
      var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Stub$127(PyFrame var1, ThreadState var2) {
      var1.setline(984);
      throw Py.makeException(var1.getglobal("Exception").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Unexpected")));
   }

   public PyObject testAssertRaisesExcValue$128(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1003);
      PyObject[] var3 = new PyObject[]{var1.getglobal("Exception")};
      PyObject var4 = Py.makeClass("ExceptionMock", var3, ExceptionMock$129);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1006);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = Stub$130;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(1008);
      PyString var7 = PyString.fromInterned("particular value");
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(1010);
      PyObject var8 = var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getderef(0));
      var1.setlocal(3, var8);
      var3 = null;
      ContextManager var9;
      var4 = (var9 = ContextGuard.getManager(var1.getlocal(3))).__enter__(var2);

      label16: {
         try {
            var1.setline(1012);
            var1.getlocal(1).__call__(var2, var1.getlocal(2));
         } catch (Throwable var5) {
            if (var9.__exit__(var2, Py.setException(var5, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var9.__exit__(var2, (PyException)null);
      }

      var1.setline(1013);
      var8 = var1.getlocal(3).__getattr__("exception");
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(1014);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(4), var1.getderef(0));
      var1.setline(1015);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("args").__getitem__(Py.newInteger(0)), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ExceptionMock$129(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1004);
      return var1.getf_locals();
   }

   public PyObject Stub$130(PyFrame var1, ThreadState var2) {
      var1.setline(1007);
      throw Py.makeException(var1.getderef(0).__call__(var2, var1.getlocal(0)));
   }

   public PyObject testSynonymAssertMethodNames$131(PyFrame var1, ThreadState var2) {
      var1.setline(1024);
      PyString.fromInterned("Test undocumented method name synonyms.\n\n        Please do not use these methods names in your own code.\n\n        This test confirms their continued existence and functionality\n        in order to avoid breaking existing code.\n        ");
      var1.setline(1025);
      var1.getlocal(0).__getattr__("assertNotEquals").__call__((ThreadState)var2, (PyObject)Py.newInteger(3), (PyObject)Py.newInteger(5));
      var1.setline(1026);
      var1.getlocal(0).__getattr__("assertEquals").__call__((ThreadState)var2, (PyObject)Py.newInteger(3), (PyObject)Py.newInteger(3));
      var1.setline(1027);
      var1.getlocal(0).__getattr__("assertAlmostEquals").__call__((ThreadState)var2, (PyObject)Py.newFloat(2.0), (PyObject)Py.newFloat(2.0));
      var1.setline(1028);
      var1.getlocal(0).__getattr__("assertNotAlmostEquals").__call__((ThreadState)var2, (PyObject)Py.newFloat(3.0), (PyObject)Py.newFloat(5.0));
      var1.setline(1029);
      var1.getlocal(0).__getattr__("assert_").__call__(var2, var1.getglobal("True"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testPendingDeprecationMethodNames$132(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1035);
      PyString.fromInterned("Test fail* methods pending deprecation, they will warn in 3.2.\n\n        Do not use these methods.  They will go away in 3.3.\n        ");
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("test_support").__getattr__("check_warnings").__call__(var2))).__enter__(var2);

      label16: {
         try {
            var1.setline(1037);
            var1.getlocal(0).__getattr__("failIfEqual").__call__((ThreadState)var2, (PyObject)Py.newInteger(3), (PyObject)Py.newInteger(5));
            var1.setline(1038);
            var1.getlocal(0).__getattr__("failUnlessEqual").__call__((ThreadState)var2, (PyObject)Py.newInteger(3), (PyObject)Py.newInteger(3));
            var1.setline(1039);
            var1.getlocal(0).__getattr__("failUnlessAlmostEqual").__call__((ThreadState)var2, (PyObject)Py.newFloat(2.0), (PyObject)Py.newFloat(2.0));
            var1.setline(1040);
            var1.getlocal(0).__getattr__("failIfAlmostEqual").__call__((ThreadState)var2, (PyObject)Py.newFloat(3.0), (PyObject)Py.newFloat(5.0));
            var1.setline(1041);
            var1.getlocal(0).__getattr__("failUnless").__call__(var2, var1.getglobal("True"));
            var1.setline(1042);
            PyObject var10000 = var1.getlocal(0).__getattr__("failUnlessRaises");
            PyObject var10002 = var1.getglobal("TypeError");
            var1.setline(1042);
            PyObject[] var6 = Py.EmptyObjects;
            var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyFunction(var1.f_globals, var6, f$133)));
            var1.setline(1043);
            var1.getlocal(0).__getattr__("failIf").__call__(var2, var1.getglobal("False"));
         } catch (Throwable var5) {
            if (var3.__exit__(var2, Py.setException(var5, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$133(PyFrame var1, ThreadState var2) {
      var1.setline(1042);
      PyObject var3 = Py.newFloat(3.14)._add(PyUnicode.fromInterned("spam"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testDeepcopy$134(PyFrame var1, ThreadState var2) {
      var1.setline(1047);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("TestableTest", var3, TestableTest$135);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1051);
      PyObject var5 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testNothing"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(1054);
      var1.getglobal("deepcopy").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestableTest$135(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1048);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, testNothing$136, (PyObject)null);
      var1.setlocal("testNothing", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testNothing$136(PyFrame var1, ThreadState var2) {
      var1.setline(1049);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testKeyboardInterrupt$137(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(1057);
      PyObject[] var3 = new PyObject[]{var1.getglobal("None")};
      PyFunction var8 = new PyFunction(var1.f_globals, var3, _raise$138, (PyObject)null);
      var1.setderef(1, var8);
      var3 = null;
      var1.setline(1059);
      var3 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var3, nothing$139, (PyObject)null);
      var1.setderef(0, var8);
      var3 = null;
      var1.setline(1062);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyCode var10002 = Test1$140;
      PyObject[] var4 = new PyObject[]{var1.getclosure(1)};
      PyObject var9 = Py.makeClass("Test1", var3, var10002, var4);
      var1.setlocal(1, var9);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1065);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var10002 = Test2$141;
      var4 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
      var9 = Py.makeClass("Test2", var3, var10002, var4);
      var1.setlocal(2, var9);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1069);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var10002 = Test3$142;
      var4 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      var9 = Py.makeClass("Test3", var3, var10002, var4);
      var1.setlocal(3, var9);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1073);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var9 = Py.makeClass("Test4", var3, Test4$143);
      var1.setlocal(4, var9);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1077);
      PyObject var10 = (new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)})).__iter__();

      while(true) {
         var1.setline(1077);
         var9 = var10.__iternext__();
         if (var9 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var9);
         ContextManager var5;
         PyObject var6 = (var5 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("KeyboardInterrupt")))).__enter__(var2);

         try {
            var1.setline(1079);
            var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_something")).__getattr__("run").__call__(var2);
         } catch (Throwable var7) {
            if (var5.__exit__(var2, Py.setException(var7, var1))) {
               continue;
            }

            throw (Throwable)Py.makeException();
         }

         var5.__exit__(var2, (PyException)null);
      }
   }

   public PyObject _raise$138(PyFrame var1, ThreadState var2) {
      var1.setline(1058);
      throw Py.makeException(var1.getglobal("KeyboardInterrupt"));
   }

   public PyObject nothing$139(PyFrame var1, ThreadState var2) {
      var1.setline(1060);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test1$140(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1063);
      PyObject var3 = var1.getderef(0);
      var1.setlocal("test_something", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject Test2$141(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1066);
      PyObject var3 = var1.getderef(0);
      var1.setlocal("setUp", var3);
      var3 = null;
      var1.setline(1067);
      var3 = var1.getderef(1);
      var1.setlocal("test_something", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject Test3$142(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1070);
      PyObject var3 = var1.getderef(0);
      var1.setlocal("test_something", var3);
      var3 = null;
      var1.setline(1071);
      var3 = var1.getderef(1);
      var1.setlocal("tearDown", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject Test4$143(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1074);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = test_something$144;
      var3 = new PyObject[]{var1.f_back.getclosure(1)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("test_something", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_something$144(PyFrame var1, ThreadState var2) {
      var1.setline(1075);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getderef(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testSystemExit$145(PyFrame var1, ThreadState var2) {
      var1.setline(1082);
      PyObject[] var3 = new PyObject[]{var1.getglobal("None")};
      PyFunction var6 = new PyFunction(var1.f_globals, var3, _raise$146, (PyObject)null);
      var1.setderef(1, var6);
      var3 = null;
      var1.setline(1084);
      var3 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var3, nothing$147, (PyObject)null);
      var1.setderef(0, var6);
      var3 = null;
      var1.setline(1087);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyCode var10002 = Test1$148;
      PyObject[] var4 = new PyObject[]{var1.getclosure(1)};
      PyObject var7 = Py.makeClass("Test1", var3, var10002, var4);
      var1.setlocal(1, var7);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1090);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var10002 = Test2$149;
      var4 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
      var7 = Py.makeClass("Test2", var3, var10002, var4);
      var1.setlocal(2, var7);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1094);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var10002 = Test3$150;
      var4 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      var7 = Py.makeClass("Test3", var3, var10002, var4);
      var1.setlocal(3, var7);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1098);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var7 = Py.makeClass("Test4", var3, Test4$151);
      var1.setlocal(4, var7);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(1102);
      PyObject var8 = (new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)})).__iter__();

      while(true) {
         var1.setline(1102);
         var7 = var8.__iternext__();
         if (var7 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var7);
         var1.setline(1103);
         PyObject var5 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(1104);
         var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_something")).__getattr__("run").__call__(var2, var1.getlocal(6));
         var1.setline(1105);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(6).__getattr__("errors")), (PyObject)Py.newInteger(1));
         var1.setline(1106);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("testsRun"), (PyObject)Py.newInteger(1));
      }
   }

   public PyObject _raise$146(PyFrame var1, ThreadState var2) {
      var1.setline(1083);
      throw Py.makeException(var1.getglobal("SystemExit"));
   }

   public PyObject nothing$147(PyFrame var1, ThreadState var2) {
      var1.setline(1085);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test1$148(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1088);
      PyObject var3 = var1.getderef(0);
      var1.setlocal("test_something", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject Test2$149(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1091);
      PyObject var3 = var1.getderef(0);
      var1.setlocal("setUp", var3);
      var3 = null;
      var1.setline(1092);
      var3 = var1.getderef(1);
      var1.setlocal("test_something", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject Test3$150(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1095);
      PyObject var3 = var1.getderef(0);
      var1.setlocal("test_something", var3);
      var3 = null;
      var1.setline(1096);
      var3 = var1.getderef(1);
      var1.setlocal("tearDown", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject Test4$151(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(1099);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = test_something$152;
      var3 = new PyObject[]{var1.f_back.getclosure(1)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("test_something", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_something$152(PyFrame var1, ThreadState var2) {
      var1.setline(1100);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getderef(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testPickle$153(PyFrame var1, ThreadState var2) {
      var1.setline(1113);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestCase").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("run"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(1114);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("pickle").__getattr__("HIGHEST_PROTOCOL")._add(Py.newInteger(1))).__iter__();

      while(true) {
         var1.setline(1114);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(1117);
         PyObject var10000 = var1.getglobal("pickle").__getattr__("dumps");
         PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2)};
         String[] var6 = new String[]{"protocol"};
         var10000 = var10000.__call__(var2, var5, var6);
         var5 = null;
         PyObject var7 = var10000;
         var1.setlocal(3, var7);
         var5 = null;
         var1.setline(1119);
         var7 = var1.getglobal("pickle").__getattr__("loads").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var7);
         var5 = null;
         var1.setline(1120);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(4));
      }
   }

   public test_case$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Test$1 = Py.newCode(0, var2, var1, "Test", 17, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Foo$2 = Py.newCode(0, var2, var1, "Foo", 20, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      runTest$3 = Py.newCode(1, var2, var1, "runTest", 21, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test1$4 = Py.newCode(1, var2, var1, "test1", 22, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Bar$5 = Py.newCode(0, var2, var1, "Bar", 24, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test2$6 = Py.newCode(1, var2, var1, "test2", 25, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LoggingTestCase$7 = Py.newCode(0, var2, var1, "LoggingTestCase", 27, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "events"};
      __init__$8 = Py.newCode(2, var2, var1, "__init__", 30, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      setUp$9 = Py.newCode(1, var2, var1, "setUp", 34, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test$10 = Py.newCode(1, var2, var1, "test", 37, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$11 = Py.newCode(1, var2, var1, "tearDown", 40, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test_TestCase$12 = Py.newCode(0, var2, var1, "Test_TestCase", 44, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "Test"};
      test_init__no_test_name$13 = Py.newCode(1, var2, var1, "test_init__no_test_name", 70, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test$14 = Py.newCode(0, var2, var1, "Test", 71, false, false, self, 14, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      runTest$15 = Py.newCode(1, var2, var1, "runTest", 72, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test$16 = Py.newCode(1, var2, var1, "test", 73, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test"};
      test_init__test_name__valid$17 = Py.newCode(1, var2, var1, "test_init__test_name__valid", 81, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test$18 = Py.newCode(0, var2, var1, "Test", 82, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      runTest$19 = Py.newCode(1, var2, var1, "runTest", 83, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test$20 = Py.newCode(1, var2, var1, "test", 84, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test"};
      test_init__test_name__invalid$21 = Py.newCode(1, var2, var1, "test_init__test_name__invalid", 92, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test$22 = Py.newCode(0, var2, var1, "Test", 93, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      runTest$23 = Py.newCode(1, var2, var1, "runTest", 94, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test$24 = Py.newCode(1, var2, var1, "test", 95, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo"};
      test_countTestCases$25 = Py.newCode(1, var2, var1, "test_countTestCases", 106, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$26 = Py.newCode(0, var2, var1, "Foo", 107, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$27 = Py.newCode(1, var2, var1, "test", 108, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "result"};
      test_defaultTestResult$28 = Py.newCode(1, var2, var1, "test_defaultTestResult", 116, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$29 = Py.newCode(0, var2, var1, "Foo", 117, false, false, self, 29, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      runTest$30 = Py.newCode(1, var2, var1, "runTest", 118, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "events", "result", "expected", "Foo"};
      String[] var10001 = var2;
      test_case$py var10007 = self;
      var2 = new String[]{"Foo"};
      test_run_call_order__error_in_setUp$31 = Py.newCode(1, var10001, var1, "test_run_call_order__error_in_setUp", 131, false, false, var10007, 31, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Foo$32 = Py.newCode(0, var2, var1, "Foo", 135, false, false, self, 32, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      setUp$33 = Py.newCode(1, var10001, var1, "setUp", 136, false, false, var10007, 33, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "events", "expected", "Foo"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      test_run_call_order__error_in_setUp_default_result$34 = Py.newCode(1, var10001, var1, "test_run_call_order__error_in_setUp_default_result", 145, false, false, var10007, 34, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Foo$35 = Py.newCode(0, var2, var1, "Foo", 148, false, false, self, 35, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      defaultTestResult$36 = Py.newCode(1, var2, var1, "defaultTestResult", 149, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      setUp$37 = Py.newCode(1, var10001, var1, "setUp", 152, false, false, var10007, 37, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "events", "result", "expected", "Foo"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      test_run_call_order__error_in_test$38 = Py.newCode(1, var10001, var1, "test_run_call_order__error_in_test", 168, false, false, var10007, 38, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Foo$39 = Py.newCode(0, var2, var1, "Foo", 172, false, false, self, 39, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      test$40 = Py.newCode(1, var10001, var1, "test", 173, false, false, var10007, 40, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "events", "expected", "Foo"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      test_run_call_order__error_in_test_default_result$41 = Py.newCode(1, var10001, var1, "test_run_call_order__error_in_test_default_result", 184, false, false, var10007, 41, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Foo$42 = Py.newCode(0, var2, var1, "Foo", 187, false, false, self, 42, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      defaultTestResult$43 = Py.newCode(1, var2, var1, "defaultTestResult", 188, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      test$44 = Py.newCode(1, var10001, var1, "test", 191, false, false, var10007, 44, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "events", "result", "expected", "Foo"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      test_run_call_order__failure_in_test$45 = Py.newCode(1, var10001, var1, "test_run_call_order__failure_in_test", 207, false, false, var10007, 45, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Foo$46 = Py.newCode(0, var2, var1, "Foo", 211, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      test$47 = Py.newCode(1, var10001, var1, "test", 212, false, false, var10007, 47, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "expected", "events", "Foo"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      test_run_call_order__failure_in_test_default_result$48 = Py.newCode(1, var10001, var1, "test_run_call_order__failure_in_test_default_result", 222, false, false, var10007, 48, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Foo$49 = Py.newCode(0, var2, var1, "Foo", 224, false, false, self, 49, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      defaultTestResult$50 = Py.newCode(1, var2, var1, "defaultTestResult", 225, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      test$51 = Py.newCode(1, var10001, var1, "test", 227, false, false, var10007, 51, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "events", "result", "expected", "Foo"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      test_run_call_order__error_in_tearDown$52 = Py.newCode(1, var10001, var1, "test_run_call_order__error_in_tearDown", 244, false, false, var10007, 52, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Foo$53 = Py.newCode(0, var2, var1, "Foo", 248, false, false, self, 53, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      tearDown$54 = Py.newCode(1, var10001, var1, "tearDown", 249, false, false, var10007, 54, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "events", "expected", "Foo"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      test_run_call_order__error_in_tearDown_default_result$55 = Py.newCode(1, var10001, var1, "test_run_call_order__error_in_tearDown_default_result", 259, false, false, var10007, 55, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Foo$56 = Py.newCode(0, var2, var1, "Foo", 261, false, false, self, 56, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      defaultTestResult$57 = Py.newCode(1, var2, var1, "defaultTestResult", 262, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      tearDown$58 = Py.newCode(1, var10001, var1, "tearDown", 264, false, false, var10007, 58, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "Foo"};
      test_run_call_order_default_result$59 = Py.newCode(1, var2, var1, "test_run_call_order_default_result", 276, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$60 = Py.newCode(0, var2, var1, "Foo", 278, false, false, self, 60, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      defaultTestResult$61 = Py.newCode(1, var2, var1, "defaultTestResult", 279, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test$62 = Py.newCode(1, var2, var1, "test", 281, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo"};
      test_failureException__default$63 = Py.newCode(1, var2, var1, "test_failureException__default", 291, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$64 = Py.newCode(0, var2, var1, "Foo", 292, false, false, self, 64, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$65 = Py.newCode(1, var2, var1, "test", 293, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "events", "result", "Foo", "expected"};
      test_failureException__subclassing__explicit_raise$66 = Py.newCode(1, var2, var1, "test_failureException__subclassing__explicit_raise", 304, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$67 = Py.newCode(0, var2, var1, "Foo", 308, false, false, self, 67, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$68 = Py.newCode(1, var2, var1, "test", 309, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "events", "result", "Foo", "expected"};
      test_failureException__subclassing__implicit_raise$69 = Py.newCode(1, var2, var1, "test_failureException__subclassing__implicit_raise", 327, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$70 = Py.newCode(0, var2, var1, "Foo", 331, false, false, self, 70, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$71 = Py.newCode(1, var2, var1, "test", 332, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo"};
      test_setUp$72 = Py.newCode(1, var2, var1, "test_setUp", 345, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$73 = Py.newCode(0, var2, var1, "Foo", 346, false, false, self, 73, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      runTest$74 = Py.newCode(1, var2, var1, "runTest", 347, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo"};
      test_tearDown$75 = Py.newCode(1, var2, var1, "test_tearDown", 354, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$76 = Py.newCode(0, var2, var1, "Foo", 355, false, false, self, 76, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      runTest$77 = Py.newCode(1, var2, var1, "runTest", 356, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo"};
      test_id$78 = Py.newCode(1, var2, var1, "test_id", 368, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$79 = Py.newCode(0, var2, var1, "Foo", 369, false, false, self, 79, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      runTest$80 = Py.newCode(1, var2, var1, "runTest", 370, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "expected", "events"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      test_run__uses_defaultTestResult$81 = Py.newCode(1, var10001, var1, "test_run__uses_defaultTestResult", 379, false, false, var10007, 81, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Foo$82 = Py.newCode(0, var2, var1, "Foo", 382, false, false, self, 82, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      test$83 = Py.newCode(1, var10001, var1, "test", 383, false, false, var10007, 83, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      defaultTestResult$84 = Py.newCode(1, var10001, var1, "defaultTestResult", 386, false, false, var10007, 84, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      testShortDescriptionWithoutDocstring$85 = Py.newCode(1, var2, var1, "testShortDescriptionWithoutDocstring", 396, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testShortDescriptionWithOneLineDocstring$86 = Py.newCode(1, var2, var1, "testShortDescriptionWithOneLineDocstring", 399, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testShortDescriptionWithMultiLineDocstring$87 = Py.newCode(1, var2, var1, "testShortDescriptionWithMultiLineDocstring", 407, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s1", "s2", "AllSnakesCreatedEqual", "SadSnake"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"SadSnake"};
      testAddTypeEqualityFunc$88 = Py.newCode(1, var10001, var1, "testAddTypeEqualityFunc", 421, false, false, var10007, 88, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      SadSnake$89 = Py.newCode(0, var2, var1, "SadSnake", 422, false, false, self, 89, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"a", "b", "msg"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"SadSnake"};
      AllSnakesCreatedEqual$90 = Py.newCode(3, var10001, var1, "AllSnakesCreatedEqual", 426, false, false, var10007, 90, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "thing"};
      testAssertIs$91 = Py.newCode(1, var2, var1, "testAssertIs", 434, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "thing"};
      testAssertIsNot$92 = Py.newCode(1, var2, var1, "testAssertIsNot", 439, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "thing"};
      testAssertIsInstance$93 = Py.newCode(1, var2, var1, "testAssertIsInstance", 444, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "thing"};
      testAssertNotIsInstance$94 = Py.newCode(1, var2, var1, "testAssertNotIsInstance", 450, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "animals"};
      testAssertIn$95 = Py.newCode(1, var2, var1, "testAssertIn", 456, false, false, self, 95, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "one", "_(500_26)"};
      testAssertDictContainsSubset$96 = Py.newCode(1, var2, var1, "testAssertDictContainsSubset", 477, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "i"};
      f$97 = Py.newCode(1, var2, var1, "<genexpr>", 500, false, false, self, 97, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "equal_pairs", "a", "b", "unequal_pairs"};
      testAssertEqual$98 = Py.newCode(1, var2, var1, "testAssertEqual", 505, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "c", "d"};
      testEquality$99 = Py.newCode(1, var2, var1, "testEquality", 542, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "seq1", "seq2", "diff", "omitted", "e", "msg"};
      testAssertSequenceEqualMaxDiff$100 = Py.newCode(1, var2, var1, "testAssertSequenceEqualMaxDiff", 594, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "omitted"};
      testTruncateMessage$101 = Py.newCode(1, var2, var1, "testTruncateMessage", 633, false, false, self, 101, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "truncate", "e"};
      testAssertDictEqualTruncates$102 = Py.newCode(1, var2, var1, "testAssertDictEqualTruncates", 647, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "diff"};
      truncate$103 = Py.newCode(2, var2, var1, "truncate", 649, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "truncate", "e"};
      testAssertMultiLineEqualTruncates$104 = Py.newCode(1, var2, var1, "testAssertMultiLineEqualTruncates", 659, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "diff"};
      truncate$105 = Py.newCode(2, var2, var1, "truncate", 661, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "cm", "explodingTruncation", "s1", "s2", "old_truncate", "old_threshold"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "old_truncate", "old_threshold"};
      testAssertEqual_diffThreshold$106 = Py.newCode(1, var10001, var1, "testAssertEqual_diffThreshold", 671, false, false, var10007, 106, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "old_threshold"};
      f$107 = Py.newCode(0, var10001, var1, "<lambda>", 680, false, false, var10007, 107, (String[])null, var2, 0, 4097);
      var2 = new String[]{"message", "diff"};
      explodingTruncation$108 = Py.newCode(2, var2, var1, "explodingTruncation", 694, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "old_truncate"};
      f$109 = Py.newCode(0, var10001, var1, "<lambda>", 698, false, false, var10007, 109, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "a", "b", "diffs", "expected"};
      testAssertItemsEqual$110 = Py.newCode(1, var2, var1, "testAssertItemsEqual", 707, false, false, self, 110, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "set1", "set2"};
      testAssertSetEqual$111 = Py.newCode(1, var2, var1, "testAssertSetEqual", 769, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testInequality$112 = Py.newCode(1, var2, var1, "testInequality", 809, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sample_text", "revised_sample_text", "sample_text_error", "type_changer", "e", "error"};
      testAssertMultiLineEqual$113 = Py.newCode(1, var2, var1, "testAssertMultiLineEqual", 895, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$114 = Py.newCode(1, var2, var1, "<lambda>", 919, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$115 = Py.newCode(1, var2, var1, "<lambda>", 919, false, false, self, 115, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sample_text", "revised_sample_text", "sample_text_error", "e", "error"};
      testAsertEqualSingleLine$116 = Py.newCode(1, var2, var1, "testAsertEqualSingleLine", 931, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertIsNone$117 = Py.newCode(1, var2, var1, "testAssertIsNone", 946, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testAssertRegexpMatches$118 = Py.newCode(1, var2, var1, "testAssertRegexpMatches", 952, false, false, self, 118, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Stub", "ExceptionMock"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ExceptionMock"};
      testAssertRaisesRegexp$119 = Py.newCode(1, var10001, var1, "testAssertRaisesRegexp", 957, false, false, var10007, 119, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      ExceptionMock$120 = Py.newCode(0, var2, var1, "ExceptionMock", 958, false, false, self, 120, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ExceptionMock"};
      Stub$121 = Py.newCode(0, var10001, var1, "Stub", 961, false, false, var10007, 121, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      testAssertNotRaisesRegexp$122 = Py.newCode(1, var2, var1, "testAssertNotRaisesRegexp", 968, false, false, self, 122, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$123 = Py.newCode(0, var2, var1, "<lambda>", 972, false, false, self, 123, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$124 = Py.newCode(0, var2, var1, "<lambda>", 976, false, false, self, 124, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$125 = Py.newCode(0, var2, var1, "<lambda>", 980, false, false, self, 125, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Stub"};
      testAssertRaisesRegexpMismatch$126 = Py.newCode(1, var2, var1, "testAssertRaisesRegexpMismatch", 982, false, false, self, 126, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Stub$127 = Py.newCode(0, var2, var1, "Stub", 983, false, false, self, 127, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Stub", "v", "ctx", "e", "ExceptionMock"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ExceptionMock"};
      testAssertRaisesExcValue$128 = Py.newCode(1, var10001, var1, "testAssertRaisesExcValue", 1002, false, false, var10007, 128, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      ExceptionMock$129 = Py.newCode(0, var2, var1, "ExceptionMock", 1003, false, false, self, 129, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"foo"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ExceptionMock"};
      Stub$130 = Py.newCode(1, var10001, var1, "Stub", 1006, false, false, var10007, 130, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      testSynonymAssertMethodNames$131 = Py.newCode(1, var2, var1, "testSynonymAssertMethodNames", 1017, false, false, self, 131, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testPendingDeprecationMethodNames$132 = Py.newCode(1, var2, var1, "testPendingDeprecationMethodNames", 1031, false, false, self, 132, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_"};
      f$133 = Py.newCode(1, var2, var1, "<lambda>", 1042, false, false, self, 133, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "TestableTest", "test"};
      testDeepcopy$134 = Py.newCode(1, var2, var1, "testDeepcopy", 1045, false, false, self, 134, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestableTest$135 = Py.newCode(0, var2, var1, "TestableTest", 1047, false, false, self, 135, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      testNothing$136 = Py.newCode(1, var2, var1, "testNothing", 1048, false, false, self, 136, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test1", "Test2", "Test3", "Test4", "klass", "nothing", "_raise"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"nothing", "_raise"};
      testKeyboardInterrupt$137 = Py.newCode(1, var10001, var1, "testKeyboardInterrupt", 1056, false, false, var10007, 137, var2, (String[])null, 2, 4097);
      var2 = new String[]{"self"};
      _raise$138 = Py.newCode(1, var2, var1, "_raise", 1057, false, false, self, 138, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      nothing$139 = Py.newCode(1, var2, var1, "nothing", 1059, false, false, self, 139, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"_raise"};
      Test1$140 = Py.newCode(0, var10001, var1, "Test1", 1062, false, false, var10007, 140, (String[])null, var2, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"_raise", "nothing"};
      Test2$141 = Py.newCode(0, var10001, var1, "Test2", 1065, false, false, var10007, 141, (String[])null, var2, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"nothing", "_raise"};
      Test3$142 = Py.newCode(0, var10001, var1, "Test3", 1069, false, false, var10007, 142, (String[])null, var2, 0, 4096);
      var2 = new String[0];
      Test4$143 = Py.newCode(0, var2, var1, "Test4", 1073, false, false, self, 143, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"_raise"};
      test_something$144 = Py.newCode(1, var10001, var1, "test_something", 1074, false, false, var10007, 144, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "Test1", "Test2", "Test3", "Test4", "klass", "result", "nothing", "_raise"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"nothing", "_raise"};
      testSystemExit$145 = Py.newCode(1, var10001, var1, "testSystemExit", 1081, false, false, var10007, 145, var2, (String[])null, 2, 4097);
      var2 = new String[]{"self"};
      _raise$146 = Py.newCode(1, var2, var1, "_raise", 1082, false, false, self, 146, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      nothing$147 = Py.newCode(1, var2, var1, "nothing", 1084, false, false, self, 147, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"_raise"};
      Test1$148 = Py.newCode(0, var10001, var1, "Test1", 1087, false, false, var10007, 148, (String[])null, var2, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"_raise", "nothing"};
      Test2$149 = Py.newCode(0, var10001, var1, "Test2", 1090, false, false, var10007, 149, (String[])null, var2, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"nothing", "_raise"};
      Test3$150 = Py.newCode(0, var10001, var1, "Test3", 1094, false, false, var10007, 150, (String[])null, var2, 0, 4096);
      var2 = new String[0];
      Test4$151 = Py.newCode(0, var2, var1, "Test4", 1098, false, false, self, 151, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"_raise"};
      test_something$152 = Py.newCode(1, var10001, var1, "test_something", 1099, false, false, var10007, 152, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "test", "protocol", "pickled_test", "unpickled_test"};
      testPickle$153 = Py.newCode(1, var2, var1, "testPickle", 1108, false, false, self, 153, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_case$py("unittest/test/test_case$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_case$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Test$1(var2, var3);
         case 2:
            return this.Foo$2(var2, var3);
         case 3:
            return this.runTest$3(var2, var3);
         case 4:
            return this.test1$4(var2, var3);
         case 5:
            return this.Bar$5(var2, var3);
         case 6:
            return this.test2$6(var2, var3);
         case 7:
            return this.LoggingTestCase$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.setUp$9(var2, var3);
         case 10:
            return this.test$10(var2, var3);
         case 11:
            return this.tearDown$11(var2, var3);
         case 12:
            return this.Test_TestCase$12(var2, var3);
         case 13:
            return this.test_init__no_test_name$13(var2, var3);
         case 14:
            return this.Test$14(var2, var3);
         case 15:
            return this.runTest$15(var2, var3);
         case 16:
            return this.test$16(var2, var3);
         case 17:
            return this.test_init__test_name__valid$17(var2, var3);
         case 18:
            return this.Test$18(var2, var3);
         case 19:
            return this.runTest$19(var2, var3);
         case 20:
            return this.test$20(var2, var3);
         case 21:
            return this.test_init__test_name__invalid$21(var2, var3);
         case 22:
            return this.Test$22(var2, var3);
         case 23:
            return this.runTest$23(var2, var3);
         case 24:
            return this.test$24(var2, var3);
         case 25:
            return this.test_countTestCases$25(var2, var3);
         case 26:
            return this.Foo$26(var2, var3);
         case 27:
            return this.test$27(var2, var3);
         case 28:
            return this.test_defaultTestResult$28(var2, var3);
         case 29:
            return this.Foo$29(var2, var3);
         case 30:
            return this.runTest$30(var2, var3);
         case 31:
            return this.test_run_call_order__error_in_setUp$31(var2, var3);
         case 32:
            return this.Foo$32(var2, var3);
         case 33:
            return this.setUp$33(var2, var3);
         case 34:
            return this.test_run_call_order__error_in_setUp_default_result$34(var2, var3);
         case 35:
            return this.Foo$35(var2, var3);
         case 36:
            return this.defaultTestResult$36(var2, var3);
         case 37:
            return this.setUp$37(var2, var3);
         case 38:
            return this.test_run_call_order__error_in_test$38(var2, var3);
         case 39:
            return this.Foo$39(var2, var3);
         case 40:
            return this.test$40(var2, var3);
         case 41:
            return this.test_run_call_order__error_in_test_default_result$41(var2, var3);
         case 42:
            return this.Foo$42(var2, var3);
         case 43:
            return this.defaultTestResult$43(var2, var3);
         case 44:
            return this.test$44(var2, var3);
         case 45:
            return this.test_run_call_order__failure_in_test$45(var2, var3);
         case 46:
            return this.Foo$46(var2, var3);
         case 47:
            return this.test$47(var2, var3);
         case 48:
            return this.test_run_call_order__failure_in_test_default_result$48(var2, var3);
         case 49:
            return this.Foo$49(var2, var3);
         case 50:
            return this.defaultTestResult$50(var2, var3);
         case 51:
            return this.test$51(var2, var3);
         case 52:
            return this.test_run_call_order__error_in_tearDown$52(var2, var3);
         case 53:
            return this.Foo$53(var2, var3);
         case 54:
            return this.tearDown$54(var2, var3);
         case 55:
            return this.test_run_call_order__error_in_tearDown_default_result$55(var2, var3);
         case 56:
            return this.Foo$56(var2, var3);
         case 57:
            return this.defaultTestResult$57(var2, var3);
         case 58:
            return this.tearDown$58(var2, var3);
         case 59:
            return this.test_run_call_order_default_result$59(var2, var3);
         case 60:
            return this.Foo$60(var2, var3);
         case 61:
            return this.defaultTestResult$61(var2, var3);
         case 62:
            return this.test$62(var2, var3);
         case 63:
            return this.test_failureException__default$63(var2, var3);
         case 64:
            return this.Foo$64(var2, var3);
         case 65:
            return this.test$65(var2, var3);
         case 66:
            return this.test_failureException__subclassing__explicit_raise$66(var2, var3);
         case 67:
            return this.Foo$67(var2, var3);
         case 68:
            return this.test$68(var2, var3);
         case 69:
            return this.test_failureException__subclassing__implicit_raise$69(var2, var3);
         case 70:
            return this.Foo$70(var2, var3);
         case 71:
            return this.test$71(var2, var3);
         case 72:
            return this.test_setUp$72(var2, var3);
         case 73:
            return this.Foo$73(var2, var3);
         case 74:
            return this.runTest$74(var2, var3);
         case 75:
            return this.test_tearDown$75(var2, var3);
         case 76:
            return this.Foo$76(var2, var3);
         case 77:
            return this.runTest$77(var2, var3);
         case 78:
            return this.test_id$78(var2, var3);
         case 79:
            return this.Foo$79(var2, var3);
         case 80:
            return this.runTest$80(var2, var3);
         case 81:
            return this.test_run__uses_defaultTestResult$81(var2, var3);
         case 82:
            return this.Foo$82(var2, var3);
         case 83:
            return this.test$83(var2, var3);
         case 84:
            return this.defaultTestResult$84(var2, var3);
         case 85:
            return this.testShortDescriptionWithoutDocstring$85(var2, var3);
         case 86:
            return this.testShortDescriptionWithOneLineDocstring$86(var2, var3);
         case 87:
            return this.testShortDescriptionWithMultiLineDocstring$87(var2, var3);
         case 88:
            return this.testAddTypeEqualityFunc$88(var2, var3);
         case 89:
            return this.SadSnake$89(var2, var3);
         case 90:
            return this.AllSnakesCreatedEqual$90(var2, var3);
         case 91:
            return this.testAssertIs$91(var2, var3);
         case 92:
            return this.testAssertIsNot$92(var2, var3);
         case 93:
            return this.testAssertIsInstance$93(var2, var3);
         case 94:
            return this.testAssertNotIsInstance$94(var2, var3);
         case 95:
            return this.testAssertIn$95(var2, var3);
         case 96:
            return this.testAssertDictContainsSubset$96(var2, var3);
         case 97:
            return this.f$97(var2, var3);
         case 98:
            return this.testAssertEqual$98(var2, var3);
         case 99:
            return this.testEquality$99(var2, var3);
         case 100:
            return this.testAssertSequenceEqualMaxDiff$100(var2, var3);
         case 101:
            return this.testTruncateMessage$101(var2, var3);
         case 102:
            return this.testAssertDictEqualTruncates$102(var2, var3);
         case 103:
            return this.truncate$103(var2, var3);
         case 104:
            return this.testAssertMultiLineEqualTruncates$104(var2, var3);
         case 105:
            return this.truncate$105(var2, var3);
         case 106:
            return this.testAssertEqual_diffThreshold$106(var2, var3);
         case 107:
            return this.f$107(var2, var3);
         case 108:
            return this.explodingTruncation$108(var2, var3);
         case 109:
            return this.f$109(var2, var3);
         case 110:
            return this.testAssertItemsEqual$110(var2, var3);
         case 111:
            return this.testAssertSetEqual$111(var2, var3);
         case 112:
            return this.testInequality$112(var2, var3);
         case 113:
            return this.testAssertMultiLineEqual$113(var2, var3);
         case 114:
            return this.f$114(var2, var3);
         case 115:
            return this.f$115(var2, var3);
         case 116:
            return this.testAsertEqualSingleLine$116(var2, var3);
         case 117:
            return this.testAssertIsNone$117(var2, var3);
         case 118:
            return this.testAssertRegexpMatches$118(var2, var3);
         case 119:
            return this.testAssertRaisesRegexp$119(var2, var3);
         case 120:
            return this.ExceptionMock$120(var2, var3);
         case 121:
            return this.Stub$121(var2, var3);
         case 122:
            return this.testAssertNotRaisesRegexp$122(var2, var3);
         case 123:
            return this.f$123(var2, var3);
         case 124:
            return this.f$124(var2, var3);
         case 125:
            return this.f$125(var2, var3);
         case 126:
            return this.testAssertRaisesRegexpMismatch$126(var2, var3);
         case 127:
            return this.Stub$127(var2, var3);
         case 128:
            return this.testAssertRaisesExcValue$128(var2, var3);
         case 129:
            return this.ExceptionMock$129(var2, var3);
         case 130:
            return this.Stub$130(var2, var3);
         case 131:
            return this.testSynonymAssertMethodNames$131(var2, var3);
         case 132:
            return this.testPendingDeprecationMethodNames$132(var2, var3);
         case 133:
            return this.f$133(var2, var3);
         case 134:
            return this.testDeepcopy$134(var2, var3);
         case 135:
            return this.TestableTest$135(var2, var3);
         case 136:
            return this.testNothing$136(var2, var3);
         case 137:
            return this.testKeyboardInterrupt$137(var2, var3);
         case 138:
            return this._raise$138(var2, var3);
         case 139:
            return this.nothing$139(var2, var3);
         case 140:
            return this.Test1$140(var2, var3);
         case 141:
            return this.Test2$141(var2, var3);
         case 142:
            return this.Test3$142(var2, var3);
         case 143:
            return this.Test4$143(var2, var3);
         case 144:
            return this.test_something$144(var2, var3);
         case 145:
            return this.testSystemExit$145(var2, var3);
         case 146:
            return this._raise$146(var2, var3);
         case 147:
            return this.nothing$147(var2, var3);
         case 148:
            return this.Test1$148(var2, var3);
         case 149:
            return this.Test2$149(var2, var3);
         case 150:
            return this.Test3$150(var2, var3);
         case 151:
            return this.Test4$151(var2, var3);
         case 152:
            return this.test_something$152(var2, var3);
         case 153:
            return this.testPickle$153(var2, var3);
         default:
            return null;
      }
   }
}
