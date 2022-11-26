package unittest.test;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
@Filename("unittest/test/test_runner.py")
public class test_runner$py extends PyFunctionTable implements PyRunnable {
   static test_runner$py self;
   static final PyCode f$0;
   static final PyCode TestCleanUp$1;
   static final PyCode testCleanUp$2;
   static final PyCode TestableTest$3;
   static final PyCode testNothing$4;
   static final PyCode cleanup1$5;
   static final PyCode cleanup2$6;
   static final PyCode testCleanUpWithErrors$7;
   static final PyCode TestableTest$8;
   static final PyCode testNothing$9;
   static final PyCode MockResult$10;
   static final PyCode addError$11;
   static final PyCode cleanup1$12;
   static final PyCode cleanup2$13;
   static final PyCode testCleanupInRun$14;
   static final PyCode TestableTest$15;
   static final PyCode setUp$16;
   static final PyCode testNothing$17;
   static final PyCode tearDown$18;
   static final PyCode cleanup1$19;
   static final PyCode cleanup2$20;
   static final PyCode success$21;
   static final PyCode testTestCaseDebugExecutesCleanups$22;
   static final PyCode TestableTest$23;
   static final PyCode setUp$24;
   static final PyCode testNothing$25;
   static final PyCode tearDown$26;
   static final PyCode cleanup1$27;
   static final PyCode cleanup2$28;
   static final PyCode Test_TextTestRunner$29;
   static final PyCode test_init$30;
   static final PyCode test_multiple_inheritance$31;
   static final PyCode AResult$32;
   static final PyCode __init__$33;
   static final PyCode ATextResult$34;
   static final PyCode testBufferAndFailfast$35;
   static final PyCode Test$36;
   static final PyCode testFoo$37;
   static final PyCode f$38;
   static final PyCode testRunnerRegistersResult$39;
   static final PyCode Test$40;
   static final PyCode testFoo$41;
   static final PyCode cleanup$42;
   static final PyCode f$43;
   static final PyCode fakeRegisterResult$44;
   static final PyCode test_works_with_result_without_startTestRun_stopTestRun$45;
   static final PyCode OldTextResult$46;
   static final PyCode printErrors$47;
   static final PyCode Runner$48;
   static final PyCode __init__$49;
   static final PyCode _makeResult$50;
   static final PyCode test_startTestRun_stopTestRun_called$51;
   static final PyCode LoggingTextResult$52;
   static final PyCode printErrors$53;
   static final PyCode LoggingRunner$54;
   static final PyCode __init__$55;
   static final PyCode _makeResult$56;
   static final PyCode test_pickle_unpickle$57;
   static final PyCode test_resultclass$58;
   static final PyCode MockResultClass$59;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(3);
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("cStringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(4);
      var3 = imp.importOne("pickle", var1, -1);
      var1.setlocal("pickle", var3);
      var3 = null;
      var1.setline(6);
      var5 = new String[]{"LoggingResult", "ResultWithNoStartTestRunStopTestRun"};
      var6 = imp.importFrom("support", var5, var1, 1);
      var4 = var6[0];
      var1.setlocal("LoggingResult", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("ResultWithNoStartTestRunStopTestRun", var4);
      var4 = null;
      var1.setline(9);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestCleanUp", var6, TestCleanUp$1);
      var1.setlocal("TestCleanUp", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(140);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test_TextTestRunner", var6, Test_TextTestRunner$29);
      var1.setlocal("Test_TextTestRunner", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(265);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(266);
         var1.getname("unittest").__getattr__("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestCleanUp$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(11);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, testCleanUp$2, (PyObject)null);
      var1.setlocal("testCleanUp", var4);
      var3 = null;
      var1.setline(40);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testCleanUpWithErrors$7, (PyObject)null);
      var1.setlocal("testCleanUpWithErrors", var4);
      var3 = null;
      var1.setline(71);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testCleanupInRun$14, (PyObject)null);
      var1.setlocal("testCleanupInRun", var4);
      var3 = null;
      var1.setline(114);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testTestCaseDebugExecutesCleanups$22, (PyObject)null);
      var1.setlocal("testTestCaseDebugExecutesCleanups", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testCleanUp$2(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("TestableTest", var3, TestableTest$3);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(16);
      PyObject var5 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testNothing"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(17);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("_cleanups"), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(19);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var6);
      var3 = null;
      var1.setline(21);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = cleanup1$5;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(24);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = cleanup2$6;
      var3 = new PyObject[]{var1.getclosure(0)};
      var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(27);
      PyObject var10000 = var1.getlocal(2).__getattr__("addCleanup");
      var3 = new PyObject[]{var1.getlocal(3), Py.newInteger(1), Py.newInteger(2), Py.newInteger(3), PyString.fromInterned("hello"), PyString.fromInterned("goodbye")};
      String[] var7 = new String[]{"four", "five"};
      var10000.__call__(var2, var3, var7);
      var3 = null;
      var1.setline(28);
      var1.getlocal(2).__getattr__("addCleanup").__call__(var2, var1.getlocal(4));
      var1.setline(30);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      var10002 = var1.getlocal(2).__getattr__("_cleanups");
      PyObject[] var10005 = new PyObject[2];
      PyObject[] var10010 = new PyObject[]{var1.getlocal(3), new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(3)}), null};
      PyObject var10013 = var1.getglobal("dict");
      var3 = new PyObject[]{PyString.fromInterned("hello"), PyString.fromInterned("goodbye")};
      var7 = new String[]{"four", "five"};
      var10013 = var10013.__call__(var2, var3, var7);
      var3 = null;
      var10010[2] = var10013;
      var10005[0] = new PyTuple(var10010);
      var10005[1] = new PyTuple(new PyObject[]{var1.getlocal(4), new PyTuple(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)});
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(var10005)));
      var1.setline(34);
      var5 = var1.getlocal(2).__getattr__("doCleanups").__call__(var2);
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(35);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(5));
      var1.setline(37);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      var10002 = var1.getderef(0);
      var10005 = new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(2), new PyTuple(Py.EmptyObjects), new PyDictionary(Py.EmptyObjects)}), null};
      var10010 = new PyObject[]{Py.newInteger(1), new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(3)}), null};
      var10013 = var1.getglobal("dict");
      var3 = new PyObject[]{PyString.fromInterned("hello"), PyString.fromInterned("goodbye")};
      var7 = new String[]{"four", "five"};
      var10013 = var10013.__call__(var2, var3, var7);
      var3 = null;
      var10010[2] = var10013;
      var10005[1] = new PyTuple(var10010);
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)(new PyList(var10005)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestableTest$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, testNothing$4, (PyObject)null);
      var1.setlocal("testNothing", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testNothing$4(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cleanup1$5(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1), var1.getlocal(0), var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cleanup2$6(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(2), var1.getlocal(0), var1.getlocal(1)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testCleanUpWithErrors$7(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("TestableTest", var3, TestableTest$8);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(45);
      var3 = new PyObject[]{var1.getglobal("object")};
      var4 = Py.makeClass("MockResult", var3, MockResult$10);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(50);
      PyObject var10 = var1.getlocal(2).__call__(var2);
      var1.setlocal(3, var10);
      var3 = null;
      var1.setline(51);
      var10 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testNothing"));
      var1.setlocal(4, var10);
      var3 = null;
      var1.setline(52);
      var10 = var1.getlocal(3);
      var1.getlocal(4).__setattr__("_resultForDoCleanups", var10);
      var3 = null;
      var1.setline(54);
      var10 = var1.getglobal("Exception").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"));
      var1.setderef(1, var10);
      var3 = null;
      var1.setline(55);
      var10 = var1.getglobal("Exception").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bar"));
      var1.setderef(0, var10);
      var3 = null;
      var1.setline(56);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = cleanup1$12;
      var3 = new PyObject[]{var1.getclosure(1)};
      PyFunction var12 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(5, var12);
      var3 = null;
      var1.setline(59);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = cleanup2$13;
      var3 = new PyObject[]{var1.getclosure(0)};
      var12 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(6, var12);
      var3 = null;
      var1.setline(62);
      var1.getlocal(4).__getattr__("addCleanup").__call__(var2, var1.getlocal(5));
      var1.setline(63);
      var1.getlocal(4).__getattr__("addCleanup").__call__(var2, var1.getlocal(6));
      var1.setline(65);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(4).__getattr__("doCleanups").__call__(var2));
      var1.setline(67);
      var10 = var1.getglobal("reversed").__call__(var2, var1.getlocal(2).__getattr__("errors"));
      PyObject[] var11 = Py.unpackSequence(var10, 2);
      PyObject var5 = var11[0];
      PyObject[] var6 = Py.unpackSequence(var5, 2);
      PyObject var7 = var6[0];
      var1.setlocal(7, var7);
      var7 = null;
      var7 = var6[1];
      PyObject[] var8 = Py.unpackSequence(var7, 3);
      PyObject var9 = var8[0];
      var1.setlocal(8, var9);
      var9 = null;
      var9 = var8[1];
      var1.setlocal(9, var9);
      var9 = null;
      var9 = var8[2];
      var1.setlocal(10, var9);
      var9 = null;
      var7 = null;
      var5 = null;
      var5 = var11[1];
      var6 = Py.unpackSequence(var5, 2);
      var7 = var6[0];
      var1.setlocal(11, var7);
      var7 = null;
      var7 = var6[1];
      var8 = Py.unpackSequence(var7, 3);
      var9 = var8[0];
      var1.setlocal(12, var9);
      var9 = null;
      var9 = var8[1];
      var1.setlocal(13, var9);
      var9 = null;
      var9 = var8[2];
      var1.setlocal(10, var9);
      var9 = null;
      var7 = null;
      var5 = null;
      var3 = null;
      var1.setline(68);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8), var1.getlocal(9)})), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getglobal("Exception"), var1.getderef(1)})));
      var1.setline(69);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(12), var1.getlocal(13)})), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getglobal("Exception"), var1.getderef(0)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestableTest$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(42);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, testNothing$9, (PyObject)null);
      var1.setlocal("testNothing", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testNothing$9(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MockResult$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(46);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("errors", var3);
      var3 = null;
      var1.setline(47);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, addError$11, (PyObject)null);
      var1.setlocal("addError", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject addError$11(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      var1.getlocal(0).__getattr__("errors").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cleanup1$12(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      throw Py.makeException(var1.getderef(0));
   }

   public PyObject cleanup2$13(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      throw Py.makeException(var1.getderef(0));
   }

   public PyObject testCleanupInRun$14(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(72);
      PyObject var3 = var1.getglobal("False");
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(73);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setderef(3, var5);
      var3 = null;
      var1.setline(75);
      PyObject[] var6 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("TestableTest", var6, TestableTest$15);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(87);
      var3 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testNothing"));
      var1.setderef(2, var3);
      var3 = null;
      var1.setline(89);
      var6 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var6;
      PyCode var10004 = cleanup1$19;
      var6 = new PyObject[]{var1.getclosure(3)};
      PyFunction var7 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var6);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(91);
      var6 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var6;
      var10004 = cleanup2$20;
      var6 = new PyObject[]{var1.getclosure(3)};
      var7 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var6);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(93);
      var1.getderef(2).__getattr__("addCleanup").__call__(var2, var1.getlocal(2));
      var1.setline(94);
      var1.getderef(2).__getattr__("addCleanup").__call__(var2, var1.getlocal(3));
      var1.setline(96);
      var6 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var6;
      var10004 = success$21;
      var6 = new PyObject[]{var1.getclosure(0), var1.getclosure(2), var1.getclosure(3)};
      var7 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var6);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(100);
      var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(101);
      var3 = var1.getlocal(4);
      var1.getlocal(5).__setattr__("addSuccess", var3);
      var3 = null;
      var1.setline(103);
      var1.getderef(2).__getattr__("run").__call__(var2, var1.getlocal(5));
      var1.setline(104);
      var1.getderef(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(3), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("setUp"), PyString.fromInterned("test"), PyString.fromInterned("tearDown"), PyString.fromInterned("cleanup2"), PyString.fromInterned("cleanup1"), PyString.fromInterned("success")})));
      var1.setline(107);
      var3 = var1.getglobal("True");
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(108);
      var5 = new PyList(Py.EmptyObjects);
      var1.setderef(3, var5);
      var3 = null;
      var1.setline(109);
      var3 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testNothing"));
      var1.setderef(2, var3);
      var3 = null;
      var1.setline(110);
      var1.getderef(2).__getattr__("addCleanup").__call__(var2, var1.getlocal(2));
      var1.setline(111);
      var1.getderef(2).__getattr__("run").__call__(var2, var1.getlocal(5));
      var1.setline(112);
      var1.getderef(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(3), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("setUp"), PyString.fromInterned("cleanup1")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestableTest$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(76);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUp$16;
      var3 = new PyObject[]{var1.f_back.getclosure(3), var1.f_back.getclosure(1)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(81);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = testNothing$17;
      var3 = new PyObject[]{var1.f_back.getclosure(3)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("testNothing", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = tearDown$18;
      var3 = new PyObject[]{var1.f_back.getclosure(3)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("tearDown", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$16(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setUp"));
      var1.setline(78);
      if (var1.getderef(1).__nonzero__()) {
         var1.setline(79);
         throw Py.makeException(var1.getglobal("Exception").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject testNothing$17(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$18(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tearDown"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cleanup1$19(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cleanup1"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cleanup2$20(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cleanup2"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject success$21(PyFrame var1, ThreadState var2) {
      var1.setline(97);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0), var1.getderef(1));
      var1.setline(98);
      var1.getderef(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("success"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testTestCaseDebugExecutesCleanups$22(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setderef(3, var3);
      var3 = null;
      var1.setline(117);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("TestableTest", var5, TestableTest$23);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(128);
      PyObject var6 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testNothing"));
      var1.setderef(2, var6);
      var3 = null;
      var1.setline(130);
      var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = cleanup1$27;
      var5 = new PyObject[]{var1.getclosure(3), var1.getclosure(2), var1.getclosure(0)};
      PyFunction var7 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setderef(1, var7);
      var3 = null;
      var1.setline(133);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = cleanup2$28;
      var5 = new PyObject[]{var1.getclosure(3)};
      var7 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setderef(0, var7);
      var3 = null;
      var1.setline(136);
      var1.getderef(2).__getattr__("debug").__call__(var2);
      var1.setline(137);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(3), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("setUp"), PyString.fromInterned("test"), PyString.fromInterned("tearDown"), PyString.fromInterned("cleanup1"), PyString.fromInterned("cleanup2")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestableTest$23(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(118);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUp$24;
      var3 = new PyObject[]{var1.f_back.getclosure(3), var1.f_back.getclosure(1)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = testNothing$25;
      var3 = new PyObject[]{var1.f_back.getclosure(3)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("testNothing", var4);
      var3 = null;
      var1.setline(125);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = tearDown$26;
      var3 = new PyObject[]{var1.f_back.getclosure(3)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("tearDown", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$24(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setUp"));
      var1.setline(120);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getderef(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testNothing$25(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$26(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tearDown"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cleanup1$27(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cleanup1"));
      var1.setline(132);
      var1.getderef(1).__getattr__("addCleanup").__call__(var2, var1.getderef(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cleanup2$28(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cleanup2"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test_TextTestRunner$29(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Tests for TextTestRunner."));
      var1.setline(141);
      PyString.fromInterned("Tests for TextTestRunner.");
      var1.setline(143);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_init$30, (PyObject)null);
      var1.setlocal("test_init", var4);
      var3 = null;
      var1.setline(152);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_multiple_inheritance$31, (PyObject)null);
      var1.setlocal("test_multiple_inheritance", var4);
      var3 = null;
      var1.setline(165);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testBufferAndFailfast$35, (PyObject)null);
      var1.setlocal("testBufferAndFailfast", var4);
      var3 = null;
      var1.setline(179);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testRunnerRegistersResult$39, (PyObject)null);
      var1.setlocal("testRunnerRegistersResult", var4);
      var3 = null;
      var1.setline(202);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_works_with_result_without_startTestRun_stopTestRun$45, (PyObject)null);
      var1.setlocal("test_works_with_result_without_startTestRun_stopTestRun", var4);
      var3 = null;
      var1.setline(218);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_startTestRun_stopTestRun_called$51, (PyObject)null);
      var1.setlocal("test_startTestRun_stopTestRun_called", var4);
      var3 = null;
      var1.setline(238);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_pickle_unpickle$57, (PyObject)null);
      var1.setlocal("test_pickle_unpickle", var4);
      var3 = null;
      var1.setline(251);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_resultclass$58, (PyObject)null);
      var1.setlocal("test_resultclass", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_init$30(PyFrame var1, ThreadState var2) {
      var1.setline(144);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TextTestRunner").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(145);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(1).__getattr__("failfast"));
      var1.setline(146);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(1).__getattr__("buffer"));
      var1.setline(147);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("verbosity"), (PyObject)Py.newInteger(1));
      var1.setline(148);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("descriptions"));
      var1.setline(149);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("resultclass"), var1.getglobal("unittest").__getattr__("TextTestResult"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_multiple_inheritance$31(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestResult")};
      PyObject var4 = Py.makeClass("AResult", var3, AResult$32);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(157);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TextTestResult"), var1.getderef(0)};
      var4 = Py.makeClass("ATextResult", var3, ATextResult$34);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(162);
      var1.getlocal(1).__call__(var2, var1.getglobal("None"), var1.getglobal("None"), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject AResult$32(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(154);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = __init__$33;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$33(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      var1.getglobal("super").__call__(var2, var1.getderef(0), var1.getlocal(0)).__getattr__("__init__").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ATextResult$34(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(158);
      return var1.getf_locals();
   }

   public PyObject testBufferAndFailfast$35(PyFrame var1, ThreadState var2) {
      var1.setline(166);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$36);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(169);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setderef(0, var5);
      var3 = null;
      var1.setline(170);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("TextTestRunner");
      var3 = new PyObject[]{var1.getglobal("StringIO").__call__(var2), var1.getglobal("True"), var1.getglobal("True")};
      String[] var6 = new String[]{"stream", "failfast", "buffer"};
      var10000 = var10000.__call__(var2, var3, var6);
      var3 = null;
      var5 = var10000;
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(173);
      var1.setline(173);
      var3 = Py.EmptyObjects;
      PyObject[] var10003 = var3;
      PyObject var10002 = var1.f_globals;
      PyCode var10004 = f$38;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var7 = new PyFunction(var10002, var10003, var10004, var3);
      var1.getlocal(2).__setattr__((String)"_makeResult", var7);
      var3 = null;
      var1.setline(174);
      var1.getlocal(2).__getattr__("run").__call__(var2, var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testFoo")));
      var1.setline(176);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getderef(0).__getattr__("failfast"));
      var1.setline(177);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getderef(0).__getattr__("buffer"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$36(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(167);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, testFoo$37, (PyObject)null);
      var1.setlocal("testFoo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testFoo$37(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$38(PyFrame var1, ThreadState var2) {
      var1.setline(173);
      PyObject var3 = var1.getderef(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testRunnerRegistersResult$39(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 2);
      var1.setline(180);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$40);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(183);
      PyObject var5 = var1.getglobal("unittest").__getattr__("runner").__getattr__("registerResult");
      var1.setderef(1, var5);
      var3 = null;
      var1.setline(184);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = cleanup$42;
      var3 = new PyObject[]{var1.getclosure(1)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(186);
      var1.getderef(2).__getattr__("addCleanup").__call__(var2, var1.getlocal(2));
      var1.setline(188);
      var5 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setderef(0, var5);
      var3 = null;
      var1.setline(189);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("TextTestRunner");
      var3 = new PyObject[]{var1.getglobal("StringIO").__call__(var2)};
      String[] var7 = new String[]{"stream"};
      var10000 = var10000.__call__(var2, var3, var7);
      var3 = null;
      var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(191);
      var1.setline(191);
      var3 = Py.EmptyObjects;
      var10003 = var3;
      var10002 = var1.f_globals;
      var10004 = f$43;
      var3 = new PyObject[]{var1.getclosure(0)};
      var6 = new PyFunction(var10002, var10003, var10004, var3);
      var1.getlocal(3).__setattr__((String)"_makeResult", var6);
      var3 = null;
      var1.setline(193);
      PyInteger var8 = Py.newInteger(0);
      var1.getderef(2).__setattr__((String)"wasRegistered", var8);
      var3 = null;
      var1.setline(194);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = fakeRegisterResult$44;
      var3 = new PyObject[]{var1.getclosure(2), var1.getclosure(0)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(197);
      var5 = var1.getlocal(4);
      var1.getglobal("unittest").__getattr__("runner").__setattr__("registerResult", var5);
      var3 = null;
      var1.setline(199);
      var1.getlocal(3).__getattr__("run").__call__(var2, var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2));
      var1.setline(200);
      var1.getderef(2).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(2).__getattr__("wasRegistered"), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(181);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, testFoo$41, (PyObject)null);
      var1.setlocal("testFoo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testFoo$41(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cleanup$42(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("unittest").__getattr__("runner").__setattr__("registerResult", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$43(PyFrame var1, ThreadState var2) {
      var1.setline(191);
      PyObject var3 = var1.getderef(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject fakeRegisterResult$44(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      PyObject var10000 = var1.getderef(0);
      String var3 = "wasRegistered";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(196);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0), var1.getderef(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_works_with_result_without_startTestRun_stopTestRun$45(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyObject[] var3 = new PyObject[]{var1.getglobal("ResultWithNoStartTestRunStopTestRun")};
      PyObject var4 = Py.makeClass("OldTextResult", var3, OldTextResult$46);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(208);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TextTestRunner")};
      var4 = Py.makeClass("Runner", var3, Runner$48);
      var1.setderef(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(215);
      PyObject var5 = var1.getderef(1).__call__(var2);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(216);
      var1.getlocal(1).__getattr__("run").__call__(var2, var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject OldTextResult$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(204);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal("separator2", var3);
      var3 = null;
      var1.setline(205);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, printErrors$47, (PyObject)null);
      var1.setlocal("printErrors", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject printErrors$47(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Runner$48(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(209);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = __init__$49;
      var3 = new PyObject[]{var1.f_back.getclosure(1)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(212);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = _makeResult$50;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("_makeResult", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$49(PyFrame var1, ThreadState var2) {
      var1.setline(210);
      var1.getglobal("super").__call__(var2, var1.getderef(0), var1.getlocal(0)).__getattr__("__init__").__call__(var2, var1.getglobal("StringIO").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _makeResult$50(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      PyObject var3 = var1.getderef(0).__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_startTestRun_stopTestRun_called$51(PyFrame var1, ThreadState var2) {
      var1.setline(219);
      PyObject[] var3 = new PyObject[]{var1.getglobal("LoggingResult")};
      PyObject var4 = Py.makeClass("LoggingTextResult", var3, LoggingTextResult$52);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(224);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TextTestRunner")};
      var4 = Py.makeClass("LoggingRunner", var3, LoggingRunner$54);
      var1.setderef(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(232);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(233);
      PyObject var6 = var1.getderef(1).__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(234);
      var1.getlocal(2).__getattr__("run").__call__(var2, var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2));
      var1.setline(235);
      var5 = new PyList(new PyObject[]{PyString.fromInterned("startTestRun"), PyString.fromInterned("stopTestRun")});
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(236);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject LoggingTextResult$52(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(220);
      PyString var3 = PyString.fromInterned("");
      var1.setlocal("separator2", var3);
      var3 = null;
      var1.setline(221);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, printErrors$53, (PyObject)null);
      var1.setlocal("printErrors", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject printErrors$53(PyFrame var1, ThreadState var2) {
      var1.setline(222);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject LoggingRunner$54(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(225);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = __init__$55;
      var3 = new PyObject[]{var1.f_back.getclosure(1)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(229);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = _makeResult$56;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("_makeResult", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$55(PyFrame var1, ThreadState var2) {
      var1.setline(226);
      var1.getglobal("super").__call__(var2, var1.getderef(0), var1.getlocal(0)).__getattr__("__init__").__call__(var2, var1.getglobal("StringIO").__call__(var2));
      var1.setline(227);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_events", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _makeResult$56(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      PyObject var3 = var1.getderef(0).__call__(var2, var1.getlocal(0).__getattr__("_events"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_pickle_unpickle$57(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      String[] var3 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("StringIO", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(243);
      PyObject var8 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"));
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(244);
      var8 = var1.getglobal("unittest").__getattr__("TextTestRunner").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(245);
      var8 = var1.getglobal("range").__call__(var2, var1.getglobal("pickle").__getattr__("HIGHEST_PROTOCOL")._add(Py.newInteger(1))).__iter__();

      while(true) {
         var1.setline(245);
         var4 = var8.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(246);
         PyObject var10000 = var1.getglobal("pickle").__getattr__("dumps");
         PyObject[] var5 = new PyObject[]{var1.getlocal(3), var1.getlocal(4)};
         String[] var6 = new String[]{"protocol"};
         var10000 = var10000.__call__(var2, var5, var6);
         var5 = null;
         PyObject var9 = var10000;
         var1.setlocal(5, var9);
         var5 = null;
         var1.setline(247);
         var9 = var1.getglobal("pickle").__getattr__("loads").__call__(var2, var1.getlocal(5));
         var1.setlocal(6, var9);
         var5 = null;
         var1.setline(249);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(6).__getattr__("stream").__getattr__("getvalue").__call__(var2), var1.getlocal(2).__getattr__("getvalue").__call__(var2));
      }
   }

   public PyObject test_resultclass$58(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, MockResultClass$59, (PyObject)null);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(254);
      PyObject var6 = var1.getglobal("object").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(255);
      var6 = var1.getglobal("object").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(256);
      var6 = var1.getglobal("object").__call__(var2);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(257);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("TextTestRunner");
      var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(1)};
      String[] var4 = new String[]{"resultclass"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var6 = var10000;
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(259);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5).__getattr__("resultclass"), var1.getlocal(1));
      var1.setline(261);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(5).__getattr__("stream"), var1.getlocal(3), var1.getlocal(4)});
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(262);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5).__getattr__("_makeResult").__call__(var2), var1.getlocal(6));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MockResultClass$59(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public test_runner$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCleanUp$1 = Py.newCode(0, var2, var1, "TestCleanUp", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "TestableTest", "test", "cleanup1", "cleanup2", "result", "cleanups"};
      String[] var10001 = var2;
      test_runner$py var10007 = self;
      var2 = new String[]{"cleanups"};
      testCleanUp$2 = Py.newCode(1, var10001, var1, "testCleanUp", 11, false, false, var10007, 2, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      TestableTest$3 = Py.newCode(0, var2, var1, "TestableTest", 12, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      testNothing$4 = Py.newCode(1, var2, var1, "testNothing", 13, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kwargs"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"cleanups"};
      cleanup1$5 = Py.newCode(2, var10001, var1, "cleanup1", 21, true, true, var10007, 5, (String[])null, var2, 0, 4097);
      var2 = new String[]{"args", "kwargs"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"cleanups"};
      cleanup2$6 = Py.newCode(2, var10001, var1, "cleanup2", 24, true, true, var10007, 6, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "TestableTest", "MockResult", "result", "test", "cleanup1", "cleanup2", "test1", "Type1", "instance1", "_", "test2", "Type2", "instance2", "exc2", "exc1"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"exc2", "exc1"};
      testCleanUpWithErrors$7 = Py.newCode(1, var10001, var1, "testCleanUpWithErrors", 40, false, false, var10007, 7, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      TestableTest$8 = Py.newCode(0, var2, var1, "TestableTest", 41, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      testNothing$9 = Py.newCode(1, var2, var1, "testNothing", 42, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MockResult$10 = Py.newCode(0, var2, var1, "MockResult", 45, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test", "exc_info"};
      addError$11 = Py.newCode(3, var2, var1, "addError", 47, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"exc1"};
      cleanup1$12 = Py.newCode(0, var10001, var1, "cleanup1", 56, false, false, var10007, 12, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"exc2"};
      cleanup2$13 = Py.newCode(0, var10001, var1, "cleanup2", 59, false, false, var10007, 13, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "TestableTest", "cleanup1", "cleanup2", "success", "result", "blowUp", "test", "ordering"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "blowUp", "test", "ordering"};
      testCleanupInRun$14 = Py.newCode(1, var10001, var1, "testCleanupInRun", 71, false, false, var10007, 14, var2, (String[])null, 3, 4097);
      var2 = new String[0];
      TestableTest$15 = Py.newCode(0, var2, var1, "TestableTest", 75, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering", "blowUp"};
      setUp$16 = Py.newCode(1, var10001, var1, "setUp", 76, false, false, var10007, 16, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      testNothing$17 = Py.newCode(1, var10001, var1, "testNothing", 81, false, false, var10007, 17, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      tearDown$18 = Py.newCode(1, var10001, var1, "tearDown", 84, false, false, var10007, 18, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      cleanup1$19 = Py.newCode(0, var10001, var1, "cleanup1", 89, false, false, var10007, 19, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      cleanup2$20 = Py.newCode(0, var10001, var1, "cleanup2", 91, false, false, var10007, 20, (String[])null, var2, 0, 4097);
      var2 = new String[]{"some_test"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "test", "ordering"};
      success$21 = Py.newCode(1, var10001, var1, "success", 96, false, false, var10007, 21, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "TestableTest", "cleanup2", "cleanup1", "test", "ordering"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"cleanup2", "cleanup1", "test", "ordering"};
      testTestCaseDebugExecutesCleanups$22 = Py.newCode(1, var10001, var1, "testTestCaseDebugExecutesCleanups", 114, false, false, var10007, 22, var2, (String[])null, 4, 4097);
      var2 = new String[0];
      TestableTest$23 = Py.newCode(0, var2, var1, "TestableTest", 117, false, false, self, 23, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering", "cleanup1"};
      setUp$24 = Py.newCode(1, var10001, var1, "setUp", 118, false, false, var10007, 24, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      testNothing$25 = Py.newCode(1, var10001, var1, "testNothing", 122, false, false, var10007, 25, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      tearDown$26 = Py.newCode(1, var10001, var1, "tearDown", 125, false, false, var10007, 26, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering", "test", "cleanup2"};
      cleanup1$27 = Py.newCode(0, var10001, var1, "cleanup1", 130, false, false, var10007, 27, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      cleanup2$28 = Py.newCode(0, var10001, var1, "cleanup2", 133, false, false, var10007, 28, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Test_TextTestRunner$29 = Py.newCode(0, var2, var1, "Test_TextTestRunner", 140, false, false, self, 29, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "runner"};
      test_init$30 = Py.newCode(1, var2, var1, "test_init", 143, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ATextResult", "AResult"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"AResult"};
      test_multiple_inheritance$31 = Py.newCode(1, var10001, var1, "test_multiple_inheritance", 152, false, false, var10007, 31, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      AResult$32 = Py.newCode(0, var2, var1, "AResult", 153, false, false, self, 32, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "stream", "descriptions", "verbosity"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"AResult"};
      __init__$33 = Py.newCode(4, var10001, var1, "__init__", 154, false, false, var10007, 33, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      ATextResult$34 = Py.newCode(0, var2, var1, "ATextResult", 157, false, false, self, 34, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "Test", "runner", "result"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"result"};
      testBufferAndFailfast$35 = Py.newCode(1, var10001, var1, "testBufferAndFailfast", 165, false, false, var10007, 35, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Test$36 = Py.newCode(0, var2, var1, "Test", 166, false, false, self, 36, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      testFoo$37 = Py.newCode(1, var2, var1, "testFoo", 167, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"result"};
      f$38 = Py.newCode(0, var10001, var1, "<lambda>", 173, false, false, var10007, 38, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "Test", "cleanup", "runner", "fakeRegisterResult", "result", "originalRegisterResult"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"result", "originalRegisterResult", "self"};
      testRunnerRegistersResult$39 = Py.newCode(1, var10001, var1, "testRunnerRegistersResult", 179, false, false, var10007, 39, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      Test$40 = Py.newCode(0, var2, var1, "Test", 180, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      testFoo$41 = Py.newCode(1, var2, var1, "testFoo", 181, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"originalRegisterResult"};
      cleanup$42 = Py.newCode(0, var10001, var1, "cleanup", 184, false, false, var10007, 42, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"result"};
      f$43 = Py.newCode(0, var10001, var1, "<lambda>", 191, false, false, var10007, 43, (String[])null, var2, 0, 4097);
      var2 = new String[]{"thisResult"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "result"};
      fakeRegisterResult$44 = Py.newCode(1, var10001, var1, "fakeRegisterResult", 194, false, false, var10007, 44, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "runner", "OldTextResult", "Runner"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"OldTextResult", "Runner"};
      test_works_with_result_without_startTestRun_stopTestRun$45 = Py.newCode(1, var10001, var1, "test_works_with_result_without_startTestRun_stopTestRun", 202, false, false, var10007, 45, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      OldTextResult$46 = Py.newCode(0, var2, var1, "OldTextResult", 203, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      printErrors$47 = Py.newCode(1, var2, var1, "printErrors", 205, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Runner$48 = Py.newCode(0, var2, var1, "Runner", 208, false, false, self, 48, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Runner"};
      __init__$49 = Py.newCode(1, var10001, var1, "__init__", 209, false, false, var10007, 49, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"OldTextResult"};
      _makeResult$50 = Py.newCode(1, var10001, var1, "_makeResult", 212, false, false, var10007, 50, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "events", "runner", "expected", "LoggingTextResult", "LoggingRunner"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"LoggingTextResult", "LoggingRunner"};
      test_startTestRun_stopTestRun_called$51 = Py.newCode(1, var10001, var1, "test_startTestRun_stopTestRun_called", 218, false, false, var10007, 51, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      LoggingTextResult$52 = Py.newCode(0, var2, var1, "LoggingTextResult", 219, false, false, self, 52, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      printErrors$53 = Py.newCode(1, var2, var1, "printErrors", 221, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LoggingRunner$54 = Py.newCode(0, var2, var1, "LoggingRunner", 224, false, false, self, 54, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "events"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"LoggingRunner"};
      __init__$55 = Py.newCode(2, var10001, var1, "__init__", 225, false, false, var10007, 55, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"LoggingTextResult"};
      _makeResult$56 = Py.newCode(1, var10001, var1, "_makeResult", 229, false, false, var10007, 56, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "PickleableIO", "stream", "runner", "protocol", "s", "obj"};
      test_pickle_unpickle$57 = Py.newCode(1, var2, var1, "test_pickle_unpickle", 238, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "MockResultClass", "STREAM", "DESCRIPTIONS", "VERBOSITY", "runner", "expectedresult"};
      test_resultclass$58 = Py.newCode(1, var2, var1, "test_resultclass", 251, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args"};
      MockResultClass$59 = Py.newCode(1, var2, var1, "MockResultClass", 252, true, false, self, 59, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_runner$py("unittest/test/test_runner$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_runner$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestCleanUp$1(var2, var3);
         case 2:
            return this.testCleanUp$2(var2, var3);
         case 3:
            return this.TestableTest$3(var2, var3);
         case 4:
            return this.testNothing$4(var2, var3);
         case 5:
            return this.cleanup1$5(var2, var3);
         case 6:
            return this.cleanup2$6(var2, var3);
         case 7:
            return this.testCleanUpWithErrors$7(var2, var3);
         case 8:
            return this.TestableTest$8(var2, var3);
         case 9:
            return this.testNothing$9(var2, var3);
         case 10:
            return this.MockResult$10(var2, var3);
         case 11:
            return this.addError$11(var2, var3);
         case 12:
            return this.cleanup1$12(var2, var3);
         case 13:
            return this.cleanup2$13(var2, var3);
         case 14:
            return this.testCleanupInRun$14(var2, var3);
         case 15:
            return this.TestableTest$15(var2, var3);
         case 16:
            return this.setUp$16(var2, var3);
         case 17:
            return this.testNothing$17(var2, var3);
         case 18:
            return this.tearDown$18(var2, var3);
         case 19:
            return this.cleanup1$19(var2, var3);
         case 20:
            return this.cleanup2$20(var2, var3);
         case 21:
            return this.success$21(var2, var3);
         case 22:
            return this.testTestCaseDebugExecutesCleanups$22(var2, var3);
         case 23:
            return this.TestableTest$23(var2, var3);
         case 24:
            return this.setUp$24(var2, var3);
         case 25:
            return this.testNothing$25(var2, var3);
         case 26:
            return this.tearDown$26(var2, var3);
         case 27:
            return this.cleanup1$27(var2, var3);
         case 28:
            return this.cleanup2$28(var2, var3);
         case 29:
            return this.Test_TextTestRunner$29(var2, var3);
         case 30:
            return this.test_init$30(var2, var3);
         case 31:
            return this.test_multiple_inheritance$31(var2, var3);
         case 32:
            return this.AResult$32(var2, var3);
         case 33:
            return this.__init__$33(var2, var3);
         case 34:
            return this.ATextResult$34(var2, var3);
         case 35:
            return this.testBufferAndFailfast$35(var2, var3);
         case 36:
            return this.Test$36(var2, var3);
         case 37:
            return this.testFoo$37(var2, var3);
         case 38:
            return this.f$38(var2, var3);
         case 39:
            return this.testRunnerRegistersResult$39(var2, var3);
         case 40:
            return this.Test$40(var2, var3);
         case 41:
            return this.testFoo$41(var2, var3);
         case 42:
            return this.cleanup$42(var2, var3);
         case 43:
            return this.f$43(var2, var3);
         case 44:
            return this.fakeRegisterResult$44(var2, var3);
         case 45:
            return this.test_works_with_result_without_startTestRun_stopTestRun$45(var2, var3);
         case 46:
            return this.OldTextResult$46(var2, var3);
         case 47:
            return this.printErrors$47(var2, var3);
         case 48:
            return this.Runner$48(var2, var3);
         case 49:
            return this.__init__$49(var2, var3);
         case 50:
            return this._makeResult$50(var2, var3);
         case 51:
            return this.test_startTestRun_stopTestRun_called$51(var2, var3);
         case 52:
            return this.LoggingTextResult$52(var2, var3);
         case 53:
            return this.printErrors$53(var2, var3);
         case 54:
            return this.LoggingRunner$54(var2, var3);
         case 55:
            return this.__init__$55(var2, var3);
         case 56:
            return this._makeResult$56(var2, var3);
         case 57:
            return this.test_pickle_unpickle$57(var2, var3);
         case 58:
            return this.test_resultclass$58(var2, var3);
         case 59:
            return this.MockResultClass$59(var2, var3);
         default:
            return null;
      }
   }
}
