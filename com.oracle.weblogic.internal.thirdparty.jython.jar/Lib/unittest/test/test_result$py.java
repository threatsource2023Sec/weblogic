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
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("unittest/test/test_result.py")
public class test_result$py extends PyFunctionTable implements PyRunnable {
   static test_result$py self;
   static final PyCode f$0;
   static final PyCode Test_TestResult$1;
   static final PyCode test_init$2;
   static final PyCode test_stop$3;
   static final PyCode test_startTest$4;
   static final PyCode Foo$5;
   static final PyCode test_1$6;
   static final PyCode test_stopTest$7;
   static final PyCode Foo$8;
   static final PyCode test_1$9;
   static final PyCode test_startTestRun_stopTestRun$10;
   static final PyCode test_addSuccess$11;
   static final PyCode Foo$12;
   static final PyCode test_1$13;
   static final PyCode test_addFailure$14;
   static final PyCode Foo$15;
   static final PyCode test_1$16;
   static final PyCode test_addError$17;
   static final PyCode Foo$18;
   static final PyCode test_1$19;
   static final PyCode testGetDescriptionWithoutDocstring$20;
   static final PyCode testGetDescriptionWithOneLineDocstring$21;
   static final PyCode testGetDescriptionWithMultiLineDocstring$22;
   static final PyCode testStackFrameTrimming$23;
   static final PyCode Frame$24;
   static final PyCode tb_frame$25;
   static final PyCode testFailFast$26;
   static final PyCode f$27;
   static final PyCode f$28;
   static final PyCode f$29;
   static final PyCode testFailFastSetByRunner$30;
   static final PyCode test$31;
   static final PyCode __init__$32;
   static final PyCode Test_OldTestResult$33;
   static final PyCode assertOldResultWarning$34;
   static final PyCode testOldTestResult$35;
   static final PyCode Test$36;
   static final PyCode testSkip$37;
   static final PyCode testExpectedFail$38;
   static final PyCode testUnexpectedSuccess$39;
   static final PyCode testOldTestTesultSetup$40;
   static final PyCode Test$41;
   static final PyCode setUp$42;
   static final PyCode testFoo$43;
   static final PyCode testOldTestResultClass$44;
   static final PyCode Test$45;
   static final PyCode testFoo$46;
   static final PyCode testOldResultWithRunner$47;
   static final PyCode Test$48;
   static final PyCode testFoo$49;
   static final PyCode MockTraceback$50;
   static final PyCode format_exception$51;
   static final PyCode restore_traceback$52;
   static final PyCode TestOutputBuffering$53;
   static final PyCode setUp$54;
   static final PyCode tearDown$55;
   static final PyCode testBufferOutputOff$56;
   static final PyCode testBufferOutputStartTestAddSuccess$57;
   static final PyCode getStartedResult$58;
   static final PyCode testBufferOutputAddErrorOrFailure$59;
   static final PyCode testBufferSetupClass$60;
   static final PyCode Foo$61;
   static final PyCode setUpClass$62;
   static final PyCode test_foo$63;
   static final PyCode testBufferTearDownClass$64;
   static final PyCode Foo$65;
   static final PyCode tearDownClass$66;
   static final PyCode test_foo$67;
   static final PyCode testBufferSetUpModule$68;
   static final PyCode Foo$69;
   static final PyCode test_foo$70;
   static final PyCode Module$71;
   static final PyCode setUpModule$72;
   static final PyCode testBufferTearDownModule$73;
   static final PyCode Foo$74;
   static final PyCode test_foo$75;
   static final PyCode Module$76;
   static final PyCode tearDownModule$77;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("textwrap", var1, -1);
      var1.setlocal("textwrap", var3);
      var3 = null;
      var1.setline(3);
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("StringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(4);
      var5 = new String[]{"test_support"};
      var6 = imp.importFrom("test", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("test_support", var4);
      var4 = null;
      var1.setline(6);
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(10);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test_TestResult", var6, Test_TestResult$1);
      var1.setlocal("Test_TestResult", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(298);
      var3 = var1.getname("dict").__call__(var2, var1.getname("unittest").__getattr__("TestResult").__getattr__("__dict__"));
      var1.setlocal("classDict", var3);
      var3 = null;
      var1.setline(299);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("addSkip"), PyString.fromInterned("addExpectedFailure"), PyString.fromInterned("addUnexpectedSuccess"), PyString.fromInterned("__init__")})).__iter__();

      while(true) {
         var1.setline(299);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(303);
            var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
            PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$32, (PyObject)null);
            var1.setlocal("__init__", var7);
            var3 = null;
            var1.setline(310);
            var3 = var1.getname("__init__");
            var1.getname("classDict").__setitem__((PyObject)PyString.fromInterned("__init__"), var3);
            var3 = null;
            var1.setline(311);
            var3 = var1.getname("type").__call__((ThreadState)var2, PyString.fromInterned("OldResult"), (PyObject)(new PyTuple(new PyObject[]{var1.getname("object")})), (PyObject)var1.getname("classDict"));
            var1.setlocal("OldResult", var3);
            var3 = null;
            var1.setline(313);
            var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
            var4 = Py.makeClass("Test_OldTestResult", var6, Test_OldTestResult$33);
            var1.setlocal("Test_OldTestResult", var4);
            var4 = null;
            Arrays.fill(var6, (Object)null);
            var1.setline(365);
            var6 = new PyObject[]{var1.getname("object")};
            var4 = Py.makeClass("MockTraceback", var6, MockTraceback$50);
            var1.setlocal("MockTraceback", var4);
            var4 = null;
            Arrays.fill(var6, (Object)null);
            var1.setline(370);
            var6 = Py.EmptyObjects;
            var7 = new PyFunction(var1.f_globals, var6, restore_traceback$52, (PyObject)null);
            var1.setlocal("restore_traceback", var7);
            var3 = null;
            var1.setline(374);
            var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
            var4 = Py.makeClass("TestOutputBuffering", var6, TestOutputBuffering$53);
            var1.setlocal("TestOutputBuffering", var4);
            var4 = null;
            Arrays.fill(var6, (Object)null);
            var1.setline(566);
            var3 = var1.getname("__name__");
            PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(567);
               var1.getname("unittest").__getattr__("main").__call__(var2);
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal("m", var4);
         var1.setline(301);
         var1.getname("classDict").__delitem__(var1.getname("m"));
      }
   }

   public PyObject Test_TestResult$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(20);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, test_init$2, (PyObject)null);
      var1.setlocal("test_init", var5);
      var3 = null;
      var1.setline(35);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_stop$3, (PyObject)null);
      var1.setlocal("test_stop", var5);
      var3 = null;
      var1.setline(44);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_startTest$4, (PyObject)null);
      var1.setlocal("test_startTest", var5);
      var3 = null;
      var1.setline(65);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_stopTest$7, (PyObject)null);
      var1.setlocal("test_stopTest", var5);
      var3 = null;
      var1.setline(92);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_startTestRun_stopTestRun$10, (PyObject)null);
      var1.setlocal("test_startTestRun_stopTestRun", var5);
      var3 = null;
      var1.setline(116);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_addSuccess$11, (PyObject)null);
      var1.setlocal("test_addSuccess", var5);
      var3 = null;
      var1.setline(155);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_addFailure$14, (PyObject)null);
      var1.setlocal("test_addFailure", var5);
      var3 = null;
      var1.setline(203);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_addError$17, (PyObject)null);
      var1.setlocal("test_addError", var5);
      var3 = null;
      var1.setline(230);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, testGetDescriptionWithoutDocstring$20, (PyObject)null);
      var1.setlocal("testGetDescriptionWithoutDocstring", var5);
      var3 = null;
      var1.setline(237);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, testGetDescriptionWithOneLineDocstring$21, PyString.fromInterned("Tests getDescription() for a method with a docstring."));
      PyObject var10000 = var1.getname("unittest").__getattr__("skipIf");
      PyObject var4 = var1.getname("sys").__getattr__("flags").__getattr__("optimize");
      PyObject var10002 = var4._ge(Py.newInteger(2));
      var4 = null;
      PyObject var6 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("Docstrings are omitted with -O2 and above")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("testGetDescriptionWithOneLineDocstring", var6);
      var3 = null;
      var1.setline(248);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, testGetDescriptionWithMultiLineDocstring$22, PyString.fromInterned("Tests getDescription() for a method with a longer docstring.\n        The second line of the docstring.\n        "));
      var10000 = var1.getname("unittest").__getattr__("skipIf");
      var4 = var1.getname("sys").__getattr__("flags").__getattr__("optimize");
      var10002 = var4._ge(Py.newInteger(2));
      var4 = null;
      var6 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("Docstrings are omitted with -O2 and above")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("testGetDescriptionWithMultiLineDocstring", var6);
      var3 = null;
      var1.setline(262);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, testStackFrameTrimming$23, (PyObject)null);
      var1.setlocal("testStackFrameTrimming", var5);
      var3 = null;
      var1.setline(272);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, testFailFast$26, (PyObject)null);
      var1.setlocal("testFailFast", var5);
      var3 = null;
      var1.setline(291);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, testFailFastSetByRunner$30, (PyObject)null);
      var1.setlocal("testFailFastSetByRunner", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_init$2(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(23);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("wasSuccessful").__call__(var2));
      var1.setline(24);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.setline(25);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("failures")), (PyObject)Py.newInteger(0));
      var1.setline(26);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("testsRun"), (PyObject)Py.newInteger(0));
      var1.setline(27);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("shouldStop"), var1.getglobal("False"));
      var1.setline(28);
      var1.getlocal(0).__getattr__("assertIsNone").__call__(var2, var1.getlocal(1).__getattr__("_stdout_buffer"));
      var1.setline(29);
      var1.getlocal(0).__getattr__("assertIsNone").__call__(var2, var1.getlocal(1).__getattr__("_stderr_buffer"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_stop$3(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(38);
      var1.getlocal(1).__getattr__("stop").__call__(var2);
      var1.setline(40);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("shouldStop"), var1.getglobal("True"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_startTest$4(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$5);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(49);
      PyObject var5 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(51);
      var5 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(53);
      var1.getlocal(3).__getattr__("startTest").__call__(var2, var1.getlocal(2));
      var1.setline(55);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(3).__getattr__("wasSuccessful").__call__(var2));
      var1.setline(56);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.setline(57);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("failures")), (PyObject)Py.newInteger(0));
      var1.setline(58);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("testsRun"), (PyObject)Py.newInteger(1));
      var1.setline(59);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("shouldStop"), var1.getglobal("False"));
      var1.setline(61);
      var1.getlocal(3).__getattr__("stopTest").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(46);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$6, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$6(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_stopTest$7(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$8);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(70);
      PyObject var5 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(72);
      var5 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(74);
      var1.getlocal(3).__getattr__("startTest").__call__(var2, var1.getlocal(2));
      var1.setline(76);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(3).__getattr__("wasSuccessful").__call__(var2));
      var1.setline(77);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.setline(78);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("failures")), (PyObject)Py.newInteger(0));
      var1.setline(79);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("testsRun"), (PyObject)Py.newInteger(1));
      var1.setline(80);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("shouldStop"), var1.getglobal("False"));
      var1.setline(82);
      var1.getlocal(3).__getattr__("stopTest").__call__(var2, var1.getlocal(2));
      var1.setline(85);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(3).__getattr__("wasSuccessful").__call__(var2));
      var1.setline(86);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.setline(87);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("failures")), (PyObject)Py.newInteger(0));
      var1.setline(88);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("testsRun"), (PyObject)Py.newInteger(1));
      var1.setline(89);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("shouldStop"), var1.getglobal("False"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(67);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$9, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$9(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_startTestRun_stopTestRun$10(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(94);
      var1.getlocal(1).__getattr__("startTestRun").__call__(var2);
      var1.setline(95);
      var1.getlocal(1).__getattr__("stopTestRun").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_addSuccess$11(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$12);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(121);
      PyObject var5 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(123);
      var5 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(125);
      var1.getlocal(3).__getattr__("startTest").__call__(var2, var1.getlocal(2));
      var1.setline(126);
      var1.getlocal(3).__getattr__("addSuccess").__call__(var2, var1.getlocal(2));
      var1.setline(127);
      var1.getlocal(3).__getattr__("stopTest").__call__(var2, var1.getlocal(2));
      var1.setline(129);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(3).__getattr__("wasSuccessful").__call__(var2));
      var1.setline(130);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.setline(131);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("failures")), (PyObject)Py.newInteger(0));
      var1.setline(132);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("testsRun"), (PyObject)Py.newInteger(1));
      var1.setline(133);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("shouldStop"), var1.getglobal("False"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(118);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$13, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$13(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_addFailure$14(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$15);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(160);
      PyObject var7 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"));
      var1.setlocal(2, var7);
      var3 = null;

      try {
         var1.setline(162);
         var1.getlocal(2).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"));
      } catch (Throwable var6) {
         Py.setException(var6, var1);
         var1.setline(164);
         var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
      }

      var1.setline(166);
      var7 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(168);
      var1.getlocal(4).__getattr__("startTest").__call__(var2, var1.getlocal(2));
      var1.setline(169);
      var1.getlocal(4).__getattr__("addFailure").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setline(170);
      var1.getlocal(4).__getattr__("stopTest").__call__(var2, var1.getlocal(2));
      var1.setline(172);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(4).__getattr__("wasSuccessful").__call__(var2));
      var1.setline(173);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.setline(174);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("failures")), (PyObject)Py.newInteger(1));
      var1.setline(175);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("testsRun"), (PyObject)Py.newInteger(1));
      var1.setline(176);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("shouldStop"), var1.getglobal("False"));
      var1.setline(178);
      var7 = var1.getlocal(4).__getattr__("failures").__getitem__(Py.newInteger(0));
      PyObject[] var8 = Py.unpackSequence(var7, 2);
      PyObject var5 = var8[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(179);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var7 = var1.getlocal(5);
      PyObject var10002 = var7._is(var1.getlocal(2));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(180);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(6), var1.getglobal("str"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(157);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$16, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$16(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_addError$17(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$18);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(208);
      PyObject var7 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"));
      var1.setlocal(2, var7);
      var3 = null;

      try {
         var1.setline(210);
         throw Py.makeException(var1.getglobal("TypeError").__call__(var2));
      } catch (Throwable var6) {
         Py.setException(var6, var1);
         var1.setline(212);
         var4 = var1.getglobal("sys").__getattr__("exc_info").__call__(var2);
         var1.setlocal(3, var4);
         var4 = null;
         var1.setline(214);
         var7 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(216);
         var1.getlocal(4).__getattr__("startTest").__call__(var2, var1.getlocal(2));
         var1.setline(217);
         var1.getlocal(4).__getattr__("addError").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setline(218);
         var1.getlocal(4).__getattr__("stopTest").__call__(var2, var1.getlocal(2));
         var1.setline(220);
         var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(4).__getattr__("wasSuccessful").__call__(var2));
         var1.setline(221);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("errors")), (PyObject)Py.newInteger(1));
         var1.setline(222);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(4).__getattr__("failures")), (PyObject)Py.newInteger(0));
         var1.setline(223);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("testsRun"), (PyObject)Py.newInteger(1));
         var1.setline(224);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("shouldStop"), var1.getglobal("False"));
         var1.setline(226);
         var7 = var1.getlocal(4).__getattr__("errors").__getitem__(Py.newInteger(0));
         PyObject[] var8 = Py.unpackSequence(var7, 2);
         PyObject var5 = var8[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal(6, var5);
         var5 = null;
         var3 = null;
         var1.setline(227);
         PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
         var7 = var1.getlocal(5);
         PyObject var10002 = var7._is(var1.getlocal(2));
         var3 = null;
         var10000.__call__(var2, var10002);
         var1.setline(228);
         var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(6), var1.getglobal("str"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject Foo$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(205);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$19, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$19(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testGetDescriptionWithoutDocstring$20(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TextTestResult").__call__((ThreadState)var2, var1.getglobal("None"), (PyObject)var1.getglobal("True"), (PyObject)Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(232);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("getDescription").__call__(var2, var1.getlocal(0)), PyString.fromInterned("testGetDescriptionWithoutDocstring (")._add(var1.getglobal("__name__"))._add(PyString.fromInterned(".Test_TestResult)")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testGetDescriptionWithOneLineDocstring$21(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      PyString.fromInterned("Tests getDescription() for a method with a docstring.");
      var1.setline(241);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TextTestResult").__call__((ThreadState)var2, var1.getglobal("None"), (PyObject)var1.getglobal("True"), (PyObject)Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(242);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("getDescription").__call__(var2, var1.getlocal(0)), PyString.fromInterned("testGetDescriptionWithOneLineDocstring (")._add(var1.getglobal("__name__"))._add(PyString.fromInterned(".Test_TestResult)\nTests getDescription() for a method with a docstring.")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testGetDescriptionWithMultiLineDocstring$22(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyString.fromInterned("Tests getDescription() for a method with a longer docstring.\n        The second line of the docstring.\n        ");
      var1.setline(254);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TextTestResult").__call__((ThreadState)var2, var1.getglobal("None"), (PyObject)var1.getglobal("True"), (PyObject)Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(255);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("getDescription").__call__(var2, var1.getlocal(0)), PyString.fromInterned("testGetDescriptionWithMultiLineDocstring (")._add(var1.getglobal("__name__"))._add(PyString.fromInterned(".Test_TestResult)\nTests getDescription() for a method with a longer docstring.")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testStackFrameTrimming$23(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      PyObject[] var3 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Frame", var3, Frame$24);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(266);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(267);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(2).__getattr__("_is_relevant_tb_level").__call__(var2, var1.getlocal(1)));
      var1.setline(269);
      var5 = var1.getglobal("True");
      var1.getlocal(1).__getattr__("tb_frame").__getattr__("f_globals").__setitem__((PyObject)PyString.fromInterned("__unittest"), var5);
      var3 = null;
      var1.setline(270);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(2).__getattr__("_is_relevant_tb_level").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Frame$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(264);
      PyObject[] var3 = new PyObject[]{var1.getname("object")};
      PyObject var4 = Py.makeClass("tb_frame", var3, tb_frame$25);
      var1.setlocal("tb_frame", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      return var1.getf_locals();
   }

   public PyObject tb_frame$25(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(265);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("f_globals", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testFailFast$26(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(274);
      var1.setline(274);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, f$27);
      var1.getlocal(1).__setattr__((String)"_exc_info_to_string", var5);
      var3 = null;
      var1.setline(275);
      var3 = var1.getglobal("True");
      var1.getlocal(1).__setattr__("failfast", var3);
      var3 = null;
      var1.setline(276);
      var1.getlocal(1).__getattr__("addError").__call__(var2, var1.getglobal("None"), var1.getglobal("None"));
      var1.setline(277);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("shouldStop"));
      var1.setline(279);
      var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(280);
      var1.setline(280);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, f$28);
      var1.getlocal(1).__setattr__((String)"_exc_info_to_string", var5);
      var3 = null;
      var1.setline(281);
      var3 = var1.getglobal("True");
      var1.getlocal(1).__setattr__("failfast", var3);
      var3 = null;
      var1.setline(282);
      var1.getlocal(1).__getattr__("addFailure").__call__(var2, var1.getglobal("None"), var1.getglobal("None"));
      var1.setline(283);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("shouldStop"));
      var1.setline(285);
      var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(286);
      var1.setline(286);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, f$29);
      var1.getlocal(1).__setattr__((String)"_exc_info_to_string", var5);
      var3 = null;
      var1.setline(287);
      var3 = var1.getglobal("True");
      var1.getlocal(1).__setattr__("failfast", var3);
      var3 = null;
      var1.setline(288);
      var1.getlocal(1).__getattr__("addUnexpectedSuccess").__call__(var2, var1.getglobal("None"));
      var1.setline(289);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("shouldStop"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$27(PyFrame var1, ThreadState var2) {
      var1.setline(274);
      PyString var3 = PyString.fromInterned("");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$28(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyString var3 = PyString.fromInterned("");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$29(PyFrame var1, ThreadState var2) {
      var1.setline(286);
      PyString var3 = PyString.fromInterned("");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testFailFastSetByRunner$30(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(292);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("TextTestRunner");
      PyObject[] var3 = new PyObject[]{var1.getglobal("StringIO").__call__(var2), var1.getglobal("True")};
      String[] var4 = new String[]{"stream", "failfast"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(293);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = test$31;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(295);
      var1.getlocal(1).__getattr__("run").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$31(PyFrame var1, ThreadState var2) {
      var1.setline(294);
      var1.getderef(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(0).__getattr__("failfast"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __init__$32(PyFrame var1, ThreadState var2) {
      var1.setline(304);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"failures", var3);
      var3 = null;
      var1.setline(305);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"errors", var3);
      var3 = null;
      var1.setline(306);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"testsRun", var4);
      var3 = null;
      var1.setline(307);
      PyObject var5 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("shouldStop", var5);
      var3 = null;
      var1.setline(308);
      var5 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("buffer", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test_OldTestResult$33(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(315);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, assertOldResultWarning$34, (PyObject)null);
      var1.setlocal("assertOldResultWarning", var4);
      var3 = null;
      var1.setline(322);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testOldTestResult$35, (PyObject)null);
      var1.setlocal("testOldTestResult", var4);
      var3 = null;
      var1.setline(339);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testOldTestTesultSetup$40, (PyObject)null);
      var1.setlocal("testOldTestTesultSetup", var4);
      var3 = null;
      var1.setline(347);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testOldTestResultClass$44, (PyObject)null);
      var1.setlocal("testOldTestResultClass", var4);
      var3 = null;
      var1.setline(354);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testOldResultWithRunner$47, (PyObject)null);
      var1.setlocal("testOldResultWithRunner", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject assertOldResultWarning$34(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("test_support").__getattr__("check_warnings").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("TestResult has no add.+ method,"), var1.getglobal("RuntimeWarning")}))))).__enter__(var2);

      label16: {
         try {
            var1.setline(318);
            var4 = var1.getglobal("OldResult").__call__(var2);
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(319);
            var1.getlocal(1).__getattr__("run").__call__(var2, var1.getlocal(3));
            var1.setline(320);
            var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("failures")), var1.getlocal(2));
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

   public PyObject testOldTestResult$35(PyFrame var1, ThreadState var2) {
      var1.setline(323);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$36);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(333);
      PyObject var7 = (new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("testSkip"), var1.getglobal("True")}), new PyTuple(new PyObject[]{PyString.fromInterned("testExpectedFail"), var1.getglobal("True")}), new PyTuple(new PyObject[]{PyString.fromInterned("testUnexpectedSuccess"), var1.getglobal("False")})})).__iter__();

      while(true) {
         var1.setline(333);
         var4 = var7.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(336);
         PyObject var8 = var1.getlocal(1).__call__(var2, var1.getlocal(2));
         var1.setlocal(4, var8);
         var5 = null;
         var1.setline(337);
         var1.getlocal(0).__getattr__("assertOldResultWarning").__call__(var2, var1.getlocal(4), var1.getglobal("int").__call__(var2, var1.getlocal(3).__not__()));
      }
   }

   public PyObject Test$36(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(324);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, testSkip$37, (PyObject)null);
      var1.setlocal("testSkip", var4);
      var3 = null;
      var1.setline(326);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testExpectedFail$38, (PyObject)null);
      PyObject var5 = var1.getname("unittest").__getattr__("expectedFailure").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("testExpectedFail", var5);
      var3 = null;
      var1.setline(329);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testUnexpectedSuccess$39, (PyObject)null);
      var5 = var1.getname("unittest").__getattr__("expectedFailure").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("testUnexpectedSuccess", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testSkip$37(PyFrame var1, ThreadState var2) {
      var1.setline(325);
      var1.getlocal(0).__getattr__("skipTest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foobar"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testExpectedFail$38(PyFrame var1, ThreadState var2) {
      var1.setline(328);
      throw Py.makeException(var1.getglobal("TypeError"));
   }

   public PyObject testUnexpectedSuccess$39(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testOldTestTesultSetup$40(PyFrame var1, ThreadState var2) {
      var1.setline(340);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$41);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(345);
      var1.getlocal(0).__getattr__("assertOldResultWarning").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testFoo")), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$41(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(341);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$42, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(343);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testFoo$43, (PyObject)null);
      var1.setlocal("testFoo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$42(PyFrame var1, ThreadState var2) {
      var1.setline(342);
      var1.getlocal(0).__getattr__("skipTest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no reason"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testFoo$43(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testOldTestResultClass$44(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$45);
      var4 = var1.getglobal("unittest").__getattr__("skip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("no reason")).__call__(var2, var4);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(352);
      var1.getlocal(0).__getattr__("assertOldResultWarning").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testFoo")), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$45(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(350);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, testFoo$46, (PyObject)null);
      var1.setlocal("testFoo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testFoo$46(PyFrame var1, ThreadState var2) {
      var1.setline(351);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testOldResultWithRunner$47(PyFrame var1, ThreadState var2) {
      var1.setline(355);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$48);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(358);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("TextTestRunner");
      var3 = new PyObject[]{var1.getglobal("OldResult"), var1.getglobal("StringIO").__call__(var2)};
      String[] var6 = new String[]{"resultclass", "stream"};
      var10000 = var10000.__call__(var2, var3, var6);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(362);
      var1.getlocal(2).__getattr__("run").__call__(var2, var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testFoo")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$48(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(356);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, testFoo$49, (PyObject)null);
      var1.setlocal("testFoo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testFoo$49(PyFrame var1, ThreadState var2) {
      var1.setline(357);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MockTraceback$50(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(366);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, format_exception$51, (PyObject)null);
      PyObject var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("format_exception", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject format_exception$51(PyFrame var1, ThreadState var2) {
      var1.setline(368);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("A traceback")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject restore_traceback$52(PyFrame var1, ThreadState var2) {
      var1.setline(371);
      PyObject var3 = var1.getglobal("traceback");
      var1.getglobal("unittest").__getattr__("result").__setattr__("traceback", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestOutputBuffering$53(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(376);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$54, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(380);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$55, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(384);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testBufferOutputOff$56, (PyObject)null);
      var1.setlocal("testBufferOutputOff", var4);
      var3 = null;
      var1.setline(399);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testBufferOutputStartTestAddSuccess$57, (PyObject)null);
      var1.setlocal("testBufferOutputStartTestAddSuccess", var4);
      var3 = null;
      var1.setline(447);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, getStartedResult$58, (PyObject)null);
      var1.setlocal("getStartedResult", var4);
      var3 = null;
      var1.setline(453);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testBufferOutputAddErrorOrFailure$59, (PyObject)null);
      var1.setlocal("testBufferOutputAddErrorOrFailure", var4);
      var3 = null;
      var1.setline(499);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testBufferSetupClass$60, (PyObject)null);
      var1.setlocal("testBufferSetupClass", var4);
      var3 = null;
      var1.setline(513);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testBufferTearDownClass$64, (PyObject)null);
      var1.setlocal("testBufferTearDownClass", var4);
      var3 = null;
      var1.setline(527);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testBufferSetUpModule$68, (PyObject)null);
      var1.setlocal("testBufferSetUpModule", var4);
      var3 = null;
      var1.setline(546);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testBufferTearDownModule$73, (PyObject)null);
      var1.setlocal("testBufferTearDownModule", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$54(PyFrame var1, ThreadState var2) {
      var1.setline(377);
      PyObject var3 = var1.getglobal("sys").__getattr__("stdout");
      var1.getlocal(0).__setattr__("_real_out", var3);
      var3 = null;
      var1.setline(378);
      var3 = var1.getglobal("sys").__getattr__("stderr");
      var1.getlocal(0).__setattr__("_real_err", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$55(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      PyObject var3 = var1.getlocal(0).__getattr__("_real_out");
      var1.getglobal("sys").__setattr__("stdout", var3);
      var3 = null;
      var1.setline(382);
      var3 = var1.getlocal(0).__getattr__("_real_err");
      var1.getglobal("sys").__setattr__("stderr", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testBufferOutputOff$56(PyFrame var1, ThreadState var2) {
      var1.setline(385);
      PyObject var3 = var1.getlocal(0).__getattr__("_real_out");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(386);
      var3 = var1.getlocal(0).__getattr__("_real_err");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(388);
      var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(389);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(3).__getattr__("buffer"));
      var1.setline(391);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(1), var1.getglobal("sys").__getattr__("stdout"));
      var1.setline(392);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(2), var1.getglobal("sys").__getattr__("stderr"));
      var1.setline(394);
      var1.getlocal(3).__getattr__("startTest").__call__(var2, var1.getlocal(0));
      var1.setline(396);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(1), var1.getglobal("sys").__getattr__("stdout"));
      var1.setline(397);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(2), var1.getglobal("sys").__getattr__("stderr"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testBufferOutputStartTestAddSuccess$57(PyFrame var1, ThreadState var2) {
      var1.setline(400);
      PyObject var3 = var1.getlocal(0).__getattr__("_real_out");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(401);
      var3 = var1.getlocal(0).__getattr__("_real_err");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(403);
      var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(404);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(3).__getattr__("buffer"));
      var1.setline(406);
      var3 = var1.getglobal("True");
      var1.getlocal(3).__setattr__("buffer", var3);
      var3 = null;
      var1.setline(408);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(1), var1.getglobal("sys").__getattr__("stdout"));
      var1.setline(409);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(2), var1.getglobal("sys").__getattr__("stderr"));
      var1.setline(411);
      var1.getlocal(3).__getattr__("startTest").__call__(var2, var1.getlocal(0));
      var1.setline(413);
      var1.getlocal(0).__getattr__("assertIsNot").__call__(var2, var1.getlocal(1), var1.getglobal("sys").__getattr__("stdout"));
      var1.setline(414);
      var1.getlocal(0).__getattr__("assertIsNot").__call__(var2, var1.getlocal(2), var1.getglobal("sys").__getattr__("stderr"));
      var1.setline(415);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getglobal("sys").__getattr__("stdout"), var1.getglobal("StringIO"));
      var1.setline(416);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getglobal("sys").__getattr__("stderr"), var1.getglobal("StringIO"));
      var1.setline(417);
      var1.getlocal(0).__getattr__("assertIsNot").__call__(var2, var1.getglobal("sys").__getattr__("stdout"), var1.getglobal("sys").__getattr__("stderr"));
      var1.setline(419);
      var3 = var1.getglobal("sys").__getattr__("stdout");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(420);
      var3 = var1.getglobal("sys").__getattr__("stderr");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(422);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.getlocal(3).__setattr__("_original_stdout", var3);
      var3 = null;
      var1.setline(423);
      var3 = var1.getglobal("StringIO").__call__(var2);
      var1.getlocal(3).__setattr__("_original_stderr", var3);
      var3 = null;
      var1.setline(425);
      Py.println(PyString.fromInterned("foo"));
      var1.setline(426);
      var3 = var1.getglobal("sys").__getattr__("stderr");
      Py.println(var3, PyString.fromInterned("bar"));
      var1.setline(428);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("foo\n"));
      var1.setline(429);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("bar\n"));
      var1.setline(431);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("_original_stdout").__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.setline(432);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("_original_stderr").__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.setline(434);
      var1.getlocal(3).__getattr__("addSuccess").__call__(var2, var1.getlocal(0));
      var1.setline(435);
      var1.getlocal(3).__getattr__("stopTest").__call__(var2, var1.getlocal(0));
      var1.setline(437);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getglobal("sys").__getattr__("stdout"), var1.getlocal(3).__getattr__("_original_stdout"));
      var1.setline(438);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getglobal("sys").__getattr__("stderr"), var1.getlocal(3).__getattr__("_original_stderr"));
      var1.setline(440);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("_original_stdout").__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.setline(441);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("_original_stderr").__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.setline(443);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.setline(444);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getStartedResult$58(PyFrame var1, ThreadState var2) {
      var1.setline(448);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(449);
      var3 = var1.getglobal("True");
      var1.getlocal(1).__setattr__("buffer", var3);
      var3 = null;
      var1.setline(450);
      var1.getlocal(1).__getattr__("startTest").__call__(var2, var1.getlocal(0));
      var1.setline(451);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testBufferOutputAddErrorOrFailure$59(PyFrame var1, ThreadState var2) {
      var1.setline(454);
      PyObject var3 = var1.getglobal("MockTraceback");
      var1.getglobal("unittest").__getattr__("result").__setattr__("traceback", var3);
      var3 = null;
      var1.setline(455);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getglobal("restore_traceback"));
      var1.setline(457);
      var3 = (new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("errors"), PyString.fromInterned("addError"), var1.getglobal("True")}), new PyTuple(new PyObject[]{PyString.fromInterned("failures"), PyString.fromInterned("addFailure"), var1.getglobal("False")}), new PyTuple(new PyObject[]{PyString.fromInterned("errors"), PyString.fromInterned("addError"), var1.getglobal("True")}), new PyTuple(new PyObject[]{PyString.fromInterned("failures"), PyString.fromInterned("addFailure"), var1.getglobal("False")})})).__iter__();

      while(true) {
         var1.setline(457);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(463);
         PyObject var8 = var1.getlocal(0).__getattr__("getStartedResult").__call__(var2);
         var1.setlocal(4, var8);
         var5 = null;
         var1.setline(464);
         var8 = var1.getglobal("sys").__getattr__("stdout");
         var1.setlocal(5, var8);
         var5 = null;
         var1.setline(465);
         var8 = var1.getglobal("sys").__getattr__("stderr");
         var1.setlocal(6, var8);
         var5 = null;
         var1.setline(466);
         var8 = var1.getglobal("StringIO").__call__(var2);
         var1.getlocal(4).__setattr__("_original_stdout", var8);
         var5 = null;
         var1.setline(467);
         var8 = var1.getglobal("StringIO").__call__(var2);
         var1.getlocal(4).__setattr__("_original_stderr", var8);
         var5 = null;
         var1.setline(469);
         var8 = var1.getglobal("sys").__getattr__("stdout");
         Py.println(var8, PyString.fromInterned("foo"));
         var1.setline(470);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(471);
            var8 = var1.getglobal("sys").__getattr__("stderr");
            Py.println(var8, PyString.fromInterned("bar"));
         }

         var1.setline(474);
         var8 = var1.getglobal("getattr").__call__(var2, var1.getlocal(4), var1.getlocal(2));
         var1.setlocal(7, var8);
         var5 = null;
         var1.setline(475);
         var1.getlocal(7).__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getglobal("None"), var1.getglobal("None")})));
         var1.setline(476);
         var1.getlocal(4).__getattr__("stopTest").__call__(var2, var1.getlocal(0));
         var1.setline(478);
         var8 = var1.getglobal("getattr").__call__(var2, var1.getlocal(4), var1.getlocal(1));
         var1.setlocal(8, var8);
         var5 = null;
         var1.setline(479);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(8)), (PyObject)Py.newInteger(1));
         var1.setline(481);
         var8 = var1.getlocal(8).__getitem__(Py.newInteger(0));
         PyObject[] var9 = Py.unpackSequence(var8, 2);
         PyObject var7 = var9[0];
         var1.setlocal(9, var7);
         var7 = null;
         var7 = var9[1];
         var1.setlocal(10, var7);
         var7 = null;
         var5 = null;
         var1.setline(482);
         var8 = var1.getglobal("textwrap").__getattr__("dedent").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n                Stdout:\n                foo\n            "));
         var1.setlocal(11, var8);
         var5 = null;
         var1.setline(486);
         PyString var10 = PyString.fromInterned("");
         var1.setlocal(12, var10);
         var5 = null;
         var1.setline(487);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(488);
            var8 = var1.getglobal("textwrap").__getattr__("dedent").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n                Stderr:\n                bar\n            "));
            var1.setlocal(12, var8);
            var5 = null;
         }

         var1.setline(492);
         var8 = PyString.fromInterned("A traceback%s%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(12)}));
         var1.setlocal(13, var8);
         var5 = null;
         var1.setline(494);
         var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(9), var1.getlocal(0));
         var1.setline(495);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("_original_stdout").__getattr__("getvalue").__call__(var2), var1.getlocal(11));
         var1.setline(496);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("_original_stderr").__getattr__("getvalue").__call__(var2), var1.getlocal(12));
         var1.setline(497);
         var1.getlocal(0).__getattr__("assertMultiLineEqual").__call__(var2, var1.getlocal(10), var1.getlocal(13));
      }
   }

   public PyObject testBufferSetupClass$60(PyFrame var1, ThreadState var2) {
      var1.setline(500);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(501);
      var3 = var1.getglobal("True");
      var1.getlocal(1).__setattr__("buffer", var3);
      var3 = null;
      var1.setline(503);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$61);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(509);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_foo"))})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(510);
      var1.getlocal(3).__call__(var2, var1.getlocal(1));
      var1.setline(511);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("errors")), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$61(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(504);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUpClass$62, (PyObject)null);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpClass", var5);
      var3 = null;
      var1.setline(507);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_foo$63, (PyObject)null);
      var1.setlocal("test_foo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$62(PyFrame var1, ThreadState var2) {
      var1.setline(506);
      Py.newInteger(1)._floordiv(Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_foo$63(PyFrame var1, ThreadState var2) {
      var1.setline(508);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testBufferTearDownClass$64(PyFrame var1, ThreadState var2) {
      var1.setline(514);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(515);
      var3 = var1.getglobal("True");
      var1.getlocal(1).__setattr__("buffer", var3);
      var3 = null;
      var1.setline(517);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$65);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(523);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_foo"))})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(524);
      var1.getlocal(3).__call__(var2, var1.getlocal(1));
      var1.setline(525);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("errors")), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$65(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(518);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, tearDownClass$66, (PyObject)null);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("tearDownClass", var5);
      var3 = null;
      var1.setline(521);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_foo$67, (PyObject)null);
      var1.setlocal("test_foo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tearDownClass$66(PyFrame var1, ThreadState var2) {
      var1.setline(520);
      Py.newInteger(1)._floordiv(Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_foo$67(PyFrame var1, ThreadState var2) {
      var1.setline(522);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testBufferSetUpModule$68(PyFrame var1, ThreadState var2) {
      var1.setline(528);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(529);
      var3 = var1.getglobal("True");
      var1.getlocal(1).__setattr__("buffer", var3);
      var3 = null;
      var1.setline(531);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$69);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(534);
      var5 = new PyObject[]{var1.getglobal("object")};
      var4 = Py.makeClass("Module", var5, Module$71);
      var1.setlocal(3, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(539);
      PyString var6 = PyString.fromInterned("Module");
      var1.getlocal(2).__setattr__((String)"__module__", var6);
      var3 = null;
      var1.setline(540);
      var3 = var1.getlocal(3);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("Module"), var3);
      var3 = null;
      var1.setline(541);
      var1.getlocal(0).__getattr__("addCleanup").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("modules").__getattr__("pop"), (PyObject)PyString.fromInterned("Module"));
      var1.setline(542);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_foo"))})));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(543);
      var1.getlocal(4).__call__(var2, var1.getlocal(1));
      var1.setline(544);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("errors")), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$69(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(532);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_foo$70, (PyObject)null);
      var1.setlocal("test_foo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_foo$70(PyFrame var1, ThreadState var2) {
      var1.setline(533);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module$71(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(535);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUpModule$72, (PyObject)null);
      PyObject var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpModule", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpModule$72(PyFrame var1, ThreadState var2) {
      var1.setline(537);
      Py.newInteger(1)._floordiv(Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testBufferTearDownModule$73(PyFrame var1, ThreadState var2) {
      var1.setline(547);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(548);
      var3 = var1.getglobal("True");
      var1.getlocal(1).__setattr__("buffer", var3);
      var3 = null;
      var1.setline(550);
      PyObject[] var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var5, Foo$74);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(553);
      var5 = new PyObject[]{var1.getglobal("object")};
      var4 = Py.makeClass("Module", var5, Module$76);
      var1.setlocal(3, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(558);
      PyString var6 = PyString.fromInterned("Module");
      var1.getlocal(2).__setattr__((String)"__module__", var6);
      var3 = null;
      var1.setline(559);
      var3 = var1.getlocal(3);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("Module"), var3);
      var3 = null;
      var1.setline(560);
      var1.getlocal(0).__getattr__("addCleanup").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("modules").__getattr__("pop"), (PyObject)PyString.fromInterned("Module"));
      var1.setline(561);
      var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_foo"))})));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(562);
      var1.getlocal(4).__call__(var2, var1.getlocal(1));
      var1.setline(563);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("errors")), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$74(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(551);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_foo$75, (PyObject)null);
      var1.setlocal("test_foo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_foo$75(PyFrame var1, ThreadState var2) {
      var1.setline(552);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module$76(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(554);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, tearDownModule$77, (PyObject)null);
      PyObject var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("tearDownModule", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tearDownModule$77(PyFrame var1, ThreadState var2) {
      var1.setline(556);
      Py.newInteger(1)._floordiv(Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public test_result$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Test_TestResult$1 = Py.newCode(0, var2, var1, "Test_TestResult", 10, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "result"};
      test_init$2 = Py.newCode(1, var2, var1, "test_init", 20, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result"};
      test_stop$3 = Py.newCode(1, var2, var1, "test_stop", 35, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "test", "result"};
      test_startTest$4 = Py.newCode(1, var2, var1, "test_startTest", 44, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$5 = Py.newCode(0, var2, var1, "Foo", 45, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$6 = Py.newCode(1, var2, var1, "test_1", 46, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "test", "result"};
      test_stopTest$7 = Py.newCode(1, var2, var1, "test_stopTest", 65, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$8 = Py.newCode(0, var2, var1, "Foo", 66, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$9 = Py.newCode(1, var2, var1, "test_1", 67, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result"};
      test_startTestRun_stopTestRun$10 = Py.newCode(1, var2, var1, "test_startTestRun_stopTestRun", 92, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "test", "result"};
      test_addSuccess$11 = Py.newCode(1, var2, var1, "test_addSuccess", 116, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$12 = Py.newCode(0, var2, var1, "Foo", 117, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$13 = Py.newCode(1, var2, var1, "test_1", 118, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "test", "exc_info_tuple", "result", "test_case", "formatted_exc"};
      test_addFailure$14 = Py.newCode(1, var2, var1, "test_addFailure", 155, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$15 = Py.newCode(0, var2, var1, "Foo", 156, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$16 = Py.newCode(1, var2, var1, "test_1", 157, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "test", "exc_info_tuple", "result", "test_case", "formatted_exc"};
      test_addError$17 = Py.newCode(1, var2, var1, "test_addError", 203, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$18 = Py.newCode(0, var2, var1, "Foo", 204, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$19 = Py.newCode(1, var2, var1, "test_1", 205, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result"};
      testGetDescriptionWithoutDocstring$20 = Py.newCode(1, var2, var1, "testGetDescriptionWithoutDocstring", 230, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result"};
      testGetDescriptionWithOneLineDocstring$21 = Py.newCode(1, var2, var1, "testGetDescriptionWithOneLineDocstring", 237, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result"};
      testGetDescriptionWithMultiLineDocstring$22 = Py.newCode(1, var2, var1, "testGetDescriptionWithMultiLineDocstring", 248, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Frame", "result"};
      testStackFrameTrimming$23 = Py.newCode(1, var2, var1, "testStackFrameTrimming", 262, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Frame$24 = Py.newCode(0, var2, var1, "Frame", 263, false, false, self, 24, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      tb_frame$25 = Py.newCode(0, var2, var1, "tb_frame", 264, false, false, self, 25, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "result"};
      testFailFast$26 = Py.newCode(1, var2, var1, "testFailFast", 272, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_"};
      f$27 = Py.newCode(1, var2, var1, "<lambda>", 274, true, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_"};
      f$28 = Py.newCode(1, var2, var1, "<lambda>", 280, true, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_"};
      f$29 = Py.newCode(1, var2, var1, "<lambda>", 286, true, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "runner", "test"};
      String[] var10001 = var2;
      test_result$py var10007 = self;
      var2 = new String[]{"self"};
      testFailFastSetByRunner$30 = Py.newCode(1, var10001, var1, "testFailFastSetByRunner", 291, false, false, var10007, 30, var2, (String[])null, 0, 4097);
      var2 = new String[]{"result"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      test$31 = Py.newCode(1, var10001, var1, "test", 293, false, false, var10007, 31, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "stream", "descriptions", "verbosity"};
      __init__$32 = Py.newCode(4, var2, var1, "__init__", 303, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test_OldTestResult$33 = Py.newCode(0, var2, var1, "Test_OldTestResult", 313, false, false, self, 33, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test", "failures", "result"};
      assertOldResultWarning$34 = Py.newCode(3, var2, var1, "assertOldResultWarning", 315, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test", "test_name", "should_pass", "test"};
      testOldTestResult$35 = Py.newCode(1, var2, var1, "testOldTestResult", 322, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test$36 = Py.newCode(0, var2, var1, "Test", 323, false, false, self, 36, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      testSkip$37 = Py.newCode(1, var2, var1, "testSkip", 324, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testExpectedFail$38 = Py.newCode(1, var2, var1, "testExpectedFail", 326, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testUnexpectedSuccess$39 = Py.newCode(1, var2, var1, "testUnexpectedSuccess", 329, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test"};
      testOldTestTesultSetup$40 = Py.newCode(1, var2, var1, "testOldTestTesultSetup", 339, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test$41 = Py.newCode(0, var2, var1, "Test", 340, false, false, self, 41, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$42 = Py.newCode(1, var2, var1, "setUp", 341, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testFoo$43 = Py.newCode(1, var2, var1, "testFoo", 343, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test"};
      testOldTestResultClass$44 = Py.newCode(1, var2, var1, "testOldTestResultClass", 347, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test$45 = Py.newCode(0, var2, var1, "Test", 348, false, false, self, 45, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      testFoo$46 = Py.newCode(1, var2, var1, "testFoo", 350, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test", "runner"};
      testOldResultWithRunner$47 = Py.newCode(1, var2, var1, "testOldResultWithRunner", 354, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test$48 = Py.newCode(0, var2, var1, "Test", 355, false, false, self, 48, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      testFoo$49 = Py.newCode(1, var2, var1, "testFoo", 356, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MockTraceback$50 = Py.newCode(0, var2, var1, "MockTraceback", 365, false, false, self, 50, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"_"};
      format_exception$51 = Py.newCode(1, var2, var1, "format_exception", 366, true, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      restore_traceback$52 = Py.newCode(0, var2, var1, "restore_traceback", 370, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestOutputBuffering$53 = Py.newCode(0, var2, var1, "TestOutputBuffering", 374, false, false, self, 53, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$54 = Py.newCode(1, var2, var1, "setUp", 376, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$55 = Py.newCode(1, var2, var1, "tearDown", 380, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "real_out", "real_err", "result"};
      testBufferOutputOff$56 = Py.newCode(1, var2, var1, "testBufferOutputOff", 384, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "real_out", "real_err", "result", "out_stream", "err_stream"};
      testBufferOutputStartTestAddSuccess$57 = Py.newCode(1, var2, var1, "testBufferOutputStartTestAddSuccess", 399, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result"};
      getStartedResult$58 = Py.newCode(1, var2, var1, "getStartedResult", 447, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message_attr", "add_attr", "include_error", "result", "buffered_out", "buffered_err", "addFunction", "result_list", "test", "message", "expectedOutMessage", "expectedErrMessage", "expectedFullMessage"};
      testBufferOutputAddErrorOrFailure$59 = Py.newCode(1, var2, var1, "testBufferOutputAddErrorOrFailure", 453, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "Foo", "suite"};
      testBufferSetupClass$60 = Py.newCode(1, var2, var1, "testBufferSetupClass", 499, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$61 = Py.newCode(0, var2, var1, "Foo", 503, false, false, self, 61, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      setUpClass$62 = Py.newCode(1, var2, var1, "setUpClass", 504, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_foo$63 = Py.newCode(1, var2, var1, "test_foo", 507, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "Foo", "suite"};
      testBufferTearDownClass$64 = Py.newCode(1, var2, var1, "testBufferTearDownClass", 513, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$65 = Py.newCode(0, var2, var1, "Foo", 517, false, false, self, 65, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      tearDownClass$66 = Py.newCode(1, var2, var1, "tearDownClass", 518, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_foo$67 = Py.newCode(1, var2, var1, "test_foo", 521, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "Foo", "Module", "suite"};
      testBufferSetUpModule$68 = Py.newCode(1, var2, var1, "testBufferSetUpModule", 527, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$69 = Py.newCode(0, var2, var1, "Foo", 531, false, false, self, 69, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_foo$70 = Py.newCode(1, var2, var1, "test_foo", 532, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Module$71 = Py.newCode(0, var2, var1, "Module", 534, false, false, self, 71, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      setUpModule$72 = Py.newCode(0, var2, var1, "setUpModule", 535, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "Foo", "Module", "suite"};
      testBufferTearDownModule$73 = Py.newCode(1, var2, var1, "testBufferTearDownModule", 546, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$74 = Py.newCode(0, var2, var1, "Foo", 550, false, false, self, 74, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_foo$75 = Py.newCode(1, var2, var1, "test_foo", 551, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Module$76 = Py.newCode(0, var2, var1, "Module", 553, false, false, self, 76, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      tearDownModule$77 = Py.newCode(0, var2, var1, "tearDownModule", 554, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_result$py("unittest/test/test_result$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_result$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Test_TestResult$1(var2, var3);
         case 2:
            return this.test_init$2(var2, var3);
         case 3:
            return this.test_stop$3(var2, var3);
         case 4:
            return this.test_startTest$4(var2, var3);
         case 5:
            return this.Foo$5(var2, var3);
         case 6:
            return this.test_1$6(var2, var3);
         case 7:
            return this.test_stopTest$7(var2, var3);
         case 8:
            return this.Foo$8(var2, var3);
         case 9:
            return this.test_1$9(var2, var3);
         case 10:
            return this.test_startTestRun_stopTestRun$10(var2, var3);
         case 11:
            return this.test_addSuccess$11(var2, var3);
         case 12:
            return this.Foo$12(var2, var3);
         case 13:
            return this.test_1$13(var2, var3);
         case 14:
            return this.test_addFailure$14(var2, var3);
         case 15:
            return this.Foo$15(var2, var3);
         case 16:
            return this.test_1$16(var2, var3);
         case 17:
            return this.test_addError$17(var2, var3);
         case 18:
            return this.Foo$18(var2, var3);
         case 19:
            return this.test_1$19(var2, var3);
         case 20:
            return this.testGetDescriptionWithoutDocstring$20(var2, var3);
         case 21:
            return this.testGetDescriptionWithOneLineDocstring$21(var2, var3);
         case 22:
            return this.testGetDescriptionWithMultiLineDocstring$22(var2, var3);
         case 23:
            return this.testStackFrameTrimming$23(var2, var3);
         case 24:
            return this.Frame$24(var2, var3);
         case 25:
            return this.tb_frame$25(var2, var3);
         case 26:
            return this.testFailFast$26(var2, var3);
         case 27:
            return this.f$27(var2, var3);
         case 28:
            return this.f$28(var2, var3);
         case 29:
            return this.f$29(var2, var3);
         case 30:
            return this.testFailFastSetByRunner$30(var2, var3);
         case 31:
            return this.test$31(var2, var3);
         case 32:
            return this.__init__$32(var2, var3);
         case 33:
            return this.Test_OldTestResult$33(var2, var3);
         case 34:
            return this.assertOldResultWarning$34(var2, var3);
         case 35:
            return this.testOldTestResult$35(var2, var3);
         case 36:
            return this.Test$36(var2, var3);
         case 37:
            return this.testSkip$37(var2, var3);
         case 38:
            return this.testExpectedFail$38(var2, var3);
         case 39:
            return this.testUnexpectedSuccess$39(var2, var3);
         case 40:
            return this.testOldTestTesultSetup$40(var2, var3);
         case 41:
            return this.Test$41(var2, var3);
         case 42:
            return this.setUp$42(var2, var3);
         case 43:
            return this.testFoo$43(var2, var3);
         case 44:
            return this.testOldTestResultClass$44(var2, var3);
         case 45:
            return this.Test$45(var2, var3);
         case 46:
            return this.testFoo$46(var2, var3);
         case 47:
            return this.testOldResultWithRunner$47(var2, var3);
         case 48:
            return this.Test$48(var2, var3);
         case 49:
            return this.testFoo$49(var2, var3);
         case 50:
            return this.MockTraceback$50(var2, var3);
         case 51:
            return this.format_exception$51(var2, var3);
         case 52:
            return this.restore_traceback$52(var2, var3);
         case 53:
            return this.TestOutputBuffering$53(var2, var3);
         case 54:
            return this.setUp$54(var2, var3);
         case 55:
            return this.tearDown$55(var2, var3);
         case 56:
            return this.testBufferOutputOff$56(var2, var3);
         case 57:
            return this.testBufferOutputStartTestAddSuccess$57(var2, var3);
         case 58:
            return this.getStartedResult$58(var2, var3);
         case 59:
            return this.testBufferOutputAddErrorOrFailure$59(var2, var3);
         case 60:
            return this.testBufferSetupClass$60(var2, var3);
         case 61:
            return this.Foo$61(var2, var3);
         case 62:
            return this.setUpClass$62(var2, var3);
         case 63:
            return this.test_foo$63(var2, var3);
         case 64:
            return this.testBufferTearDownClass$64(var2, var3);
         case 65:
            return this.Foo$65(var2, var3);
         case 66:
            return this.tearDownClass$66(var2, var3);
         case 67:
            return this.test_foo$67(var2, var3);
         case 68:
            return this.testBufferSetUpModule$68(var2, var3);
         case 69:
            return this.Foo$69(var2, var3);
         case 70:
            return this.test_foo$70(var2, var3);
         case 71:
            return this.Module$71(var2, var3);
         case 72:
            return this.setUpModule$72(var2, var3);
         case 73:
            return this.testBufferTearDownModule$73(var2, var3);
         case 74:
            return this.Foo$74(var2, var3);
         case 75:
            return this.test_foo$75(var2, var3);
         case 76:
            return this.Module$76(var2, var3);
         case 77:
            return this.tearDownModule$77(var2, var3);
         default:
            return null;
      }
   }
}
