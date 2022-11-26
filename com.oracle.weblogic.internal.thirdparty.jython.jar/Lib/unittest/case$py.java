package unittest;

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
@Filename("unittest/case.py")
public class case$py extends PyFunctionTable implements PyRunnable {
   static case$py self;
   static final PyCode f$0;
   static final PyCode SkipTest$1;
   static final PyCode _ExpectedFailure$2;
   static final PyCode __init__$3;
   static final PyCode _UnexpectedSuccess$4;
   static final PyCode _id$5;
   static final PyCode skip$6;
   static final PyCode decorator$7;
   static final PyCode skip_wrapper$8;
   static final PyCode skipIf$9;
   static final PyCode skipUnless$10;
   static final PyCode expectedFailure$11;
   static final PyCode wrapper$12;
   static final PyCode _AssertRaisesContext$13;
   static final PyCode __init__$14;
   static final PyCode __enter__$15;
   static final PyCode __exit__$16;
   static final PyCode TestCase$17;
   static final PyCode __init__$18;
   static final PyCode addTypeEqualityFunc$19;
   static final PyCode addCleanup$20;
   static final PyCode setUp$21;
   static final PyCode tearDown$22;
   static final PyCode setUpClass$23;
   static final PyCode tearDownClass$24;
   static final PyCode countTestCases$25;
   static final PyCode defaultTestResult$26;
   static final PyCode shortDescription$27;
   static final PyCode id$28;
   static final PyCode __eq__$29;
   static final PyCode __ne__$30;
   static final PyCode __hash__$31;
   static final PyCode __str__$32;
   static final PyCode __repr__$33;
   static final PyCode _addSkip$34;
   static final PyCode run$35;
   static final PyCode doCleanups$36;
   static final PyCode __call__$37;
   static final PyCode debug$38;
   static final PyCode skipTest$39;
   static final PyCode fail$40;
   static final PyCode assertFalse$41;
   static final PyCode assertTrue$42;
   static final PyCode _formatMessage$43;
   static final PyCode assertRaises$44;
   static final PyCode _getAssertEqualityFunc$45;
   static final PyCode _baseAssertEqual$46;
   static final PyCode assertEqual$47;
   static final PyCode assertNotEqual$48;
   static final PyCode assertAlmostEqual$49;
   static final PyCode assertNotAlmostEqual$50;
   static final PyCode _deprecate$51;
   static final PyCode deprecated_func$52;
   static final PyCode assertSequenceEqual$53;
   static final PyCode _truncateMessage$54;
   static final PyCode assertListEqual$55;
   static final PyCode assertTupleEqual$56;
   static final PyCode assertSetEqual$57;
   static final PyCode assertIn$58;
   static final PyCode assertNotIn$59;
   static final PyCode assertIs$60;
   static final PyCode assertIsNot$61;
   static final PyCode assertDictEqual$62;
   static final PyCode assertDictContainsSubset$63;
   static final PyCode f$64;
   static final PyCode assertItemsEqual$65;
   static final PyCode assertMultiLineEqual$66;
   static final PyCode assertLess$67;
   static final PyCode assertLessEqual$68;
   static final PyCode assertGreater$69;
   static final PyCode assertGreaterEqual$70;
   static final PyCode assertIsNone$71;
   static final PyCode assertIsNotNone$72;
   static final PyCode assertIsInstance$73;
   static final PyCode assertNotIsInstance$74;
   static final PyCode assertRaisesRegexp$75;
   static final PyCode assertRegexpMatches$76;
   static final PyCode assertNotRegexpMatches$77;
   static final PyCode FunctionTestCase$78;
   static final PyCode __init__$79;
   static final PyCode setUp$80;
   static final PyCode tearDown$81;
   static final PyCode runTest$82;
   static final PyCode id$83;
   static final PyCode __eq__$84;
   static final PyCode __ne__$85;
   static final PyCode __hash__$86;
   static final PyCode __str__$87;
   static final PyCode __repr__$88;
   static final PyCode shortDescription$89;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Test case implementation"));
      var1.setline(1);
      PyString.fromInterned("Test case implementation");
      var1.setline(3);
      PyObject var3 = imp.importOne("collections", var1, -1);
      var1.setlocal("collections", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("functools", var1, -1);
      var1.setlocal("functools", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("difflib", var1, -1);
      var1.setlocal("difflib", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("pprint", var1, -1);
      var1.setlocal("pprint", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(10);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(12);
      String[] var5 = new String[]{"result"};
      PyObject[] var6 = imp.importFrom("", var5, var1, 1);
      PyObject var4 = var6[0];
      var1.setlocal("result", var4);
      var4 = null;
      var1.setline(13);
      var5 = new String[]{"strclass", "safe_repr", "unorderable_list_difference", "_count_diff_all_purpose", "_count_diff_hashable"};
      var6 = imp.importFrom("util", var5, var1, 1);
      var4 = var6[0];
      var1.setlocal("strclass", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("safe_repr", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("unorderable_list_difference", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("_count_diff_all_purpose", var4);
      var4 = null;
      var4 = var6[4];
      var1.setlocal("_count_diff_hashable", var4);
      var4 = null;
      var1.setline(19);
      var3 = var1.getname("True");
      var1.setlocal("__unittest", var3);
      var3 = null;
      var1.setline(22);
      PyString var7 = PyString.fromInterned("\nDiff is %s characters long. Set self.maxDiff to None to see it.");
      var1.setlocal("DIFF_OMITTED", var7);
      var3 = null;
      var1.setline(25);
      var6 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("SkipTest", var6, SkipTest$1);
      var1.setlocal("SkipTest", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(34);
      var6 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("_ExpectedFailure", var6, _ExpectedFailure$2);
      var1.setlocal("_ExpectedFailure", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(45);
      var6 = new PyObject[]{var1.getname("Exception")};
      var4 = Py.makeClass("_UnexpectedSuccess", var6, _UnexpectedSuccess$4);
      var1.setlocal("_UnexpectedSuccess", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(51);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, _id$5, (PyObject)null);
      var1.setlocal("_id", var8);
      var3 = null;
      var1.setline(54);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, skip$6, PyString.fromInterned("\n    Unconditionally skip a test.\n    "));
      var1.setlocal("skip", var8);
      var3 = null;
      var1.setline(70);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, skipIf$9, PyString.fromInterned("\n    Skip a test if the condition is true.\n    "));
      var1.setlocal("skipIf", var8);
      var3 = null;
      var1.setline(78);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, skipUnless$10, PyString.fromInterned("\n    Skip a test unless the condition is true.\n    "));
      var1.setlocal("skipUnless", var8);
      var3 = null;
      var1.setline(87);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, expectedFailure$11, (PyObject)null);
      var1.setlocal("expectedFailure", var8);
      var3 = null;
      var1.setline(98);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_AssertRaisesContext", var6, _AssertRaisesContext$13);
      var1.setlocal("_AssertRaisesContext", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(133);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestCase", var6, TestCase$17);
      var1.setlocal("TestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(1019);
      var6 = new PyObject[]{var1.getname("TestCase")};
      var4 = Py.makeClass("FunctionTestCase", var6, FunctionTestCase$78);
      var1.setlocal("FunctionTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SkipTest$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Raise this exception in a test to skip it.\n\n    Usually you can use TestResult.skip() or one of the skipping decorators\n    instead of raising this directly.\n    "));
      var1.setline(31);
      PyString.fromInterned("\n    Raise this exception in a test to skip it.\n\n    Usually you can use TestResult.skip() or one of the skipping decorators\n    instead of raising this directly.\n    ");
      var1.setline(32);
      return var1.getf_locals();
   }

   public PyObject _ExpectedFailure$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    Raise this when a test is expected to fail.\n\n    This is an implementation detail.\n    "));
      var1.setline(39);
      PyString.fromInterned("\n    Raise this when a test is expected to fail.\n\n    This is an implementation detail.\n    ");
      var1.setline(41);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      var1.getglobal("super").__call__(var2, var1.getglobal("_ExpectedFailure"), var1.getlocal(0)).__getattr__("__init__").__call__(var2);
      var1.setline(43);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("exc_info", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _UnexpectedSuccess$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    The test was supposed to fail, but it didn't!\n    "));
      var1.setline(48);
      PyString.fromInterned("\n    The test was supposed to fail, but it didn't!\n    ");
      var1.setline(49);
      return var1.getf_locals();
   }

   public PyObject _id$5(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject skip$6(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(57);
      PyString.fromInterned("\n    Unconditionally skip a test.\n    ");
      var1.setline(58);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = decorator$7;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(68);
      PyObject var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject decorator$7(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject[] var3;
      PyObject var5;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("type"), var1.getglobal("types").__getattr__("ClassType")}))).__not__().__nonzero__()) {
         var1.setline(60);
         var3 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var3;
         PyCode var10004 = skip_wrapper$8;
         var3 = new PyObject[]{var1.getclosure(0)};
         PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
         var5 = var1.getglobal("functools").__getattr__("wraps").__call__(var2, var1.getlocal(0)).__call__((ThreadState)var2, (PyObject)var4);
         var1.setlocal(1, var5);
         var3 = null;
         var1.setline(63);
         var5 = var1.getlocal(1);
         var1.setlocal(0, var5);
         var3 = null;
      }

      var1.setline(65);
      var5 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("__unittest_skip__", var5);
      var3 = null;
      var1.setline(66);
      var5 = var1.getderef(0);
      var1.getlocal(0).__setattr__("__unittest_skip_why__", var5);
      var3 = null;
      var1.setline(67);
      var5 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject skip_wrapper$8(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      throw Py.makeException(var1.getglobal("SkipTest").__call__(var2, var1.getderef(0)));
   }

   public PyObject skipIf$9(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyString.fromInterned("\n    Skip a test if the condition is true.\n    ");
      var1.setline(74);
      PyObject var3;
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(75);
         var3 = var1.getglobal("skip").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(76);
         var3 = var1.getglobal("_id");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject skipUnless$10(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyString.fromInterned("\n    Skip a test unless the condition is true.\n    ");
      var1.setline(82);
      PyObject var3;
      if (var1.getlocal(0).__not__().__nonzero__()) {
         var1.setline(83);
         var3 = var1.getglobal("skip").__call__(var2, var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(84);
         var3 = var1.getglobal("_id");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject expectedFailure$11(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(88);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = wrapper$12;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getglobal("functools").__getattr__("wraps").__call__(var2, var1.getderef(0)).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(95);
      var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject wrapper$12(PyFrame var1, ThreadState var2) {
      PyException var3;
      try {
         var1.setline(91);
         PyObject var10000 = var1.getderef(0);
         PyObject[] var6 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000._callextra(var6, var4, var1.getlocal(0), var1.getlocal(1));
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("Exception"))) {
            var1.setline(93);
            throw Py.makeException(var1.getglobal("_ExpectedFailure").__call__(var2, var1.getglobal("sys").__getattr__("exc_info").__call__(var2)));
         }

         throw var3;
      }

      var1.setline(94);
      throw Py.makeException(var1.getglobal("_UnexpectedSuccess"));
   }

   public PyObject _AssertRaisesContext$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A context manager used to implement TestCase.assertRaises* methods."));
      var1.setline(99);
      PyString.fromInterned("A context manager used to implement TestCase.assertRaises* methods.");
      var1.setline(101);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$14, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(106);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __enter__$15, (PyObject)null);
      var1.setlocal("__enter__", var4);
      var3 = null;
      var1.setline(109);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __exit__$16, (PyObject)null);
      var1.setlocal("__exit__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$14(PyFrame var1, ThreadState var2) {
      var1.setline(102);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("expected", var3);
      var3 = null;
      var1.setline(103);
      var3 = var1.getlocal(2).__getattr__("failureException");
      var1.getlocal(0).__setattr__("failureException", var3);
      var3 = null;
      var1.setline(104);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("expected_regexp", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __enter__$15(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __exit__$16(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(112);
            var3 = var1.getlocal(0).__getattr__("expected").__getattr__("__name__");
            var1.setlocal(4, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var6 = Py.setException(var5, var1);
            if (!var6.match(var1.getglobal("AttributeError"))) {
               throw var6;
            }

            var1.setline(114);
            var4 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("expected"));
            var1.setlocal(4, var4);
            var4 = null;
         }

         var1.setline(115);
         throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, PyString.fromInterned("{0} not raised").__getattr__("format").__call__(var2, var1.getlocal(4))));
      } else {
         var1.setline(117);
         if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("expected")).__not__().__nonzero__()) {
            var1.setline(119);
            var3 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(120);
            var4 = var1.getlocal(2);
            var1.getlocal(0).__setattr__("exception", var4);
            var4 = null;
            var1.setline(121);
            var4 = var1.getlocal(0).__getattr__("expected_regexp");
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(122);
               var3 = var1.getglobal("True");
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(124);
               var4 = var1.getlocal(0).__getattr__("expected_regexp");
               var1.setlocal(5, var4);
               var4 = null;
               var1.setline(125);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("basestring")).__nonzero__()) {
                  var1.setline(126);
                  var4 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(5));
                  var1.setlocal(5, var4);
                  var4 = null;
               }

               var1.setline(127);
               if (var1.getlocal(5).__getattr__("search").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(2))).__not__().__nonzero__()) {
                  var1.setline(128);
                  throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, PyString.fromInterned("\"%s\" does not match \"%s\"")._mod(new PyTuple(new PyObject[]{var1.getlocal(5).__getattr__("pattern"), var1.getglobal("str").__call__(var2, var1.getlocal(2))}))));
               } else {
                  var1.setline(130);
                  var3 = var1.getglobal("True");
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject TestCase$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A class whose instances are single test cases.\n\n    By default, the test code itself should be placed in a method named\n    'runTest'.\n\n    If the fixture may be used for many test cases, create as\n    many test methods as are needed. When instantiating such a TestCase\n    subclass, specify in the constructor arguments the name of the test method\n    that the instance is to execute.\n\n    Test authors should subclass TestCase for their own tests. Construction\n    and deconstruction of the test's environment ('fixture') can be\n    implemented by overriding the 'setUp' and 'tearDown' methods respectively.\n\n    If it is necessary to override the __init__ method, the base class\n    __init__ method must always be called. It is important that subclasses\n    should not change the signature of their __init__ method, since instances\n    of the classes are instantiated automatically by parts of the framework\n    in order to be run.\n    "));
      var1.setline(153);
      PyString.fromInterned("A class whose instances are single test cases.\n\n    By default, the test code itself should be placed in a method named\n    'runTest'.\n\n    If the fixture may be used for many test cases, create as\n    many test methods as are needed. When instantiating such a TestCase\n    subclass, specify in the constructor arguments the name of the test method\n    that the instance is to execute.\n\n    Test authors should subclass TestCase for their own tests. Construction\n    and deconstruction of the test's environment ('fixture') can be\n    implemented by overriding the 'setUp' and 'tearDown' methods respectively.\n\n    If it is necessary to override the __init__ method, the base class\n    __init__ method must always be called. It is important that subclasses\n    should not change the signature of their __init__ method, since instances\n    of the classes are instantiated automatically by parts of the framework\n    in order to be run.\n    ");
      var1.setline(159);
      PyObject var3 = var1.getname("AssertionError");
      var1.setlocal("failureException", var3);
      var3 = null;
      var1.setline(165);
      var3 = var1.getname("False");
      var1.setlocal("longMessage", var3);
      var3 = null;
      var1.setline(171);
      var3 = Py.newInteger(80)._mul(Py.newInteger(8));
      var1.setlocal("maxDiff", var3);
      var3 = null;
      var1.setline(175);
      var3 = Py.newInteger(2)._pow(Py.newInteger(16));
      var1.setlocal("_diffThreshold", var3);
      var3 = null;
      var1.setline(179);
      var3 = var1.getname("False");
      var1.setlocal("_classSetupFailed", var3);
      var3 = null;
      var1.setline(181);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("runTest")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$18, PyString.fromInterned("Create an instance of the class that will use the named test\n           method when executed. Raises a ValueError if the instance does\n           not have a method with the specified name.\n        "));
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(211);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addTypeEqualityFunc$19, PyString.fromInterned("Add a type specific assertEqual style function to compare a type.\n\n        This method is for use by TestCase subclasses that need to register\n        their own type equality functions to provide nicer error messages.\n\n        Args:\n            typeobj: The data type to call this function on when both values\n                    are of the same type in assertEqual().\n            function: The callable taking two arguments and an optional\n                    msg= argument that raises self.failureException with a\n                    useful error message when the two arguments are not equal.\n        "));
      var1.setlocal("addTypeEqualityFunc", var5);
      var3 = null;
      var1.setline(226);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, addCleanup$20, PyString.fromInterned("Add a function, with arguments, to be called when the test is\n        completed. Functions added are called on a LIFO basis and are\n        called after tearDown on test failure or success.\n\n        Cleanup items are called even if setUp fails (unlike tearDown)."));
      var1.setlocal("addCleanup", var5);
      var3 = null;
      var1.setline(234);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setUp$21, PyString.fromInterned("Hook method for setting up the test fixture before exercising it."));
      var1.setlocal("setUp", var5);
      var3 = null;
      var1.setline(238);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tearDown$22, PyString.fromInterned("Hook method for deconstructing the test fixture after testing it."));
      var1.setlocal("tearDown", var5);
      var3 = null;
      var1.setline(242);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, setUpClass$23, PyString.fromInterned("Hook method for setting up class fixture before running tests in the class."));
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("setUpClass", var3);
      var3 = null;
      var1.setline(246);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tearDownClass$24, PyString.fromInterned("Hook method for deconstructing the class fixture after running all tests in the class."));
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownClass", var3);
      var3 = null;
      var1.setline(250);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, countTestCases$25, (PyObject)null);
      var1.setlocal("countTestCases", var5);
      var3 = null;
      var1.setline(253);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, defaultTestResult$26, (PyObject)null);
      var1.setlocal("defaultTestResult", var5);
      var3 = null;
      var1.setline(256);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, shortDescription$27, PyString.fromInterned("Returns a one-line description of the test, or None if no\n        description has been provided.\n\n        The default implementation of this method returns the first line of\n        the specified test method's docstring.\n        "));
      var1.setlocal("shortDescription", var5);
      var3 = null;
      var1.setline(267);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, id$28, (PyObject)null);
      var1.setlocal("id", var5);
      var3 = null;
      var1.setline(270);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __eq__$29, (PyObject)null);
      var1.setlocal("__eq__", var5);
      var3 = null;
      var1.setline(276);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __ne__$30, (PyObject)null);
      var1.setlocal("__ne__", var5);
      var3 = null;
      var1.setline(279);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __hash__$31, (PyObject)null);
      var1.setlocal("__hash__", var5);
      var3 = null;
      var1.setline(282);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __str__$32, (PyObject)null);
      var1.setlocal("__str__", var5);
      var3 = null;
      var1.setline(285);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$33, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(289);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _addSkip$34, (PyObject)null);
      var1.setlocal("_addSkip", var5);
      var3 = null;
      var1.setline(298);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, run$35, (PyObject)null);
      var1.setlocal("run", var5);
      var3 = null;
      var1.setline(379);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, doCleanups$36, PyString.fromInterned("Execute all cleanup functions. Normally called for you after\n        tearDown."));
      var1.setlocal("doCleanups", var5);
      var3 = null;
      var1.setline(395);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __call__$37, (PyObject)null);
      var1.setlocal("__call__", var5);
      var3 = null;
      var1.setline(398);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, debug$38, PyString.fromInterned("Run the test without collecting errors in a TestResult"));
      var1.setlocal("debug", var5);
      var3 = null;
      var1.setline(407);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, skipTest$39, PyString.fromInterned("Skip this test."));
      var1.setlocal("skipTest", var5);
      var3 = null;
      var1.setline(411);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, fail$40, PyString.fromInterned("Fail immediately, with the given message."));
      var1.setlocal("fail", var5);
      var3 = null;
      var1.setline(415);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertFalse$41, PyString.fromInterned("Check that the expression is false."));
      var1.setlocal("assertFalse", var5);
      var3 = null;
      var1.setline(421);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertTrue$42, PyString.fromInterned("Check that the expression is true."));
      var1.setlocal("assertTrue", var5);
      var3 = null;
      var1.setline(427);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _formatMessage$43, PyString.fromInterned("Honour the longMessage attribute when generating failure messages.\n        If longMessage is False this means:\n        * Use only an explicit message if it is provided\n        * Otherwise use the standard message for the assert\n\n        If longMessage is True:\n        * Use the standard message\n        * If an explicit message is provided, plus ' : ' and the explicit message\n        "));
      var1.setlocal("_formatMessage", var5);
      var3 = null;
      var1.setline(449);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertRaises$44, PyString.fromInterned("Fail unless an exception of class excClass is raised\n           by callableObj when invoked with arguments args and keyword\n           arguments kwargs. If a different type of exception is\n           raised, it will not be caught, and the test case will be\n           deemed to have suffered an error, exactly as for an\n           unexpected exception.\n\n           If called with callableObj omitted or None, will return a\n           context object used like this::\n\n                with self.assertRaises(SomeException):\n                    do_something()\n\n           The context manager keeps a reference to the exception as\n           the 'exception' attribute. This allows you to inspect the\n           exception after the assertion::\n\n               with self.assertRaises(SomeException) as cm:\n                   do_something()\n               the_exception = cm.exception\n               self.assertEqual(the_exception.error_code, 3)\n        "));
      var1.setlocal("assertRaises", var5);
      var3 = null;
      var1.setline(478);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _getAssertEqualityFunc$45, PyString.fromInterned("Get a detailed comparison function for the types of the two args.\n\n        Returns: A callable accepting (first, second, msg=None) that will\n        raise a failure exception if first != second with a useful human\n        readable error message for those types.\n        "));
      var1.setlocal("_getAssertEqualityFunc", var5);
      var3 = null;
      var1.setline(504);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _baseAssertEqual$46, PyString.fromInterned("The default assertEqual implementation, not type specific."));
      var1.setlocal("_baseAssertEqual", var5);
      var3 = null;
      var1.setline(511);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertEqual$47, PyString.fromInterned("Fail if the two objects are unequal as determined by the '=='\n           operator.\n        "));
      var1.setlocal("assertEqual", var5);
      var3 = null;
      var1.setline(518);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertNotEqual$48, PyString.fromInterned("Fail if the two objects are equal as determined by the '!='\n           operator.\n        "));
      var1.setlocal("assertNotEqual", var5);
      var3 = null;
      var1.setline(528);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertAlmostEqual$49, PyString.fromInterned("Fail if the two objects are unequal as determined by their\n           difference rounded to the given number of decimal places\n           (default 7) and comparing to zero, or by comparing that the\n           between the two objects is more than the given delta.\n\n           Note that decimal places (from zero) are usually not the same\n           as significant digits (measured from the most signficant digit).\n\n           If the two objects compare equal then they will automatically\n           compare almost equal.\n        "));
      var1.setlocal("assertAlmostEqual", var5);
      var3 = null;
      var1.setline(566);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertNotAlmostEqual$50, PyString.fromInterned("Fail if the two objects are equal as determined by their\n           difference rounded to the given number of decimal places\n           (default 7) and comparing to zero, or by comparing that the\n           between the two objects is less than the given delta.\n\n           Note that decimal places (from zero) are usually not the same\n           as significant digits (measured from the most signficant digit).\n\n           Objects that are equal automatically fail.\n        "));
      var1.setlocal("assertNotAlmostEqual", var5);
      var3 = null;
      var1.setline(602);
      var3 = var1.getname("assertEqual");
      var1.setlocal("assertEquals", var3);
      var3 = null;
      var1.setline(603);
      var3 = var1.getname("assertNotEqual");
      var1.setlocal("assertNotEquals", var3);
      var3 = null;
      var1.setline(604);
      var3 = var1.getname("assertAlmostEqual");
      var1.setlocal("assertAlmostEquals", var3);
      var3 = null;
      var1.setline(605);
      var3 = var1.getname("assertNotAlmostEqual");
      var1.setlocal("assertNotAlmostEquals", var3);
      var3 = null;
      var1.setline(606);
      var3 = var1.getname("assertTrue");
      var1.setlocal("assert_", var3);
      var3 = null;
      var1.setline(610);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _deprecate$51, (PyObject)null);
      var1.setlocal("_deprecate", var5);
      var3 = null;
      var1.setline(618);
      var3 = var1.getname("_deprecate").__call__(var2, var1.getname("assertEqual"));
      var1.setlocal("failUnlessEqual", var3);
      var3 = null;
      var1.setline(619);
      var3 = var1.getname("_deprecate").__call__(var2, var1.getname("assertNotEqual"));
      var1.setlocal("failIfEqual", var3);
      var3 = null;
      var1.setline(620);
      var3 = var1.getname("_deprecate").__call__(var2, var1.getname("assertAlmostEqual"));
      var1.setlocal("failUnlessAlmostEqual", var3);
      var3 = null;
      var1.setline(621);
      var3 = var1.getname("_deprecate").__call__(var2, var1.getname("assertNotAlmostEqual"));
      var1.setlocal("failIfAlmostEqual", var3);
      var3 = null;
      var1.setline(622);
      var3 = var1.getname("_deprecate").__call__(var2, var1.getname("assertTrue"));
      var1.setlocal("failUnless", var3);
      var3 = null;
      var1.setline(623);
      var3 = var1.getname("_deprecate").__call__(var2, var1.getname("assertRaises"));
      var1.setlocal("failUnlessRaises", var3);
      var3 = null;
      var1.setline(624);
      var3 = var1.getname("_deprecate").__call__(var2, var1.getname("assertFalse"));
      var1.setlocal("failIf", var3);
      var3 = null;
      var1.setline(626);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertSequenceEqual$53, PyString.fromInterned("An equality assertion for ordered sequences (like lists and tuples).\n\n        For the purposes of this function, a valid ordered sequence type is one\n        which can be indexed, has a length, and has an equality operator.\n\n        Args:\n            seq1: The first sequence to compare.\n            seq2: The second sequence to compare.\n            seq_type: The expected datatype of the sequences, or None if no\n                    datatype should be enforced.\n            msg: Optional message to use on failure instead of a list of\n                    differences.\n        "));
      var1.setlocal("assertSequenceEqual", var5);
      var3 = null;
      var1.setline(729);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _truncateMessage$54, (PyObject)null);
      var1.setlocal("_truncateMessage", var5);
      var3 = null;
      var1.setline(735);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertListEqual$55, PyString.fromInterned("A list-specific equality assertion.\n\n        Args:\n            list1: The first list to compare.\n            list2: The second list to compare.\n            msg: Optional message to use on failure instead of a list of\n                    differences.\n\n        "));
      var1.setlocal("assertListEqual", var5);
      var3 = null;
      var1.setline(747);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertTupleEqual$56, PyString.fromInterned("A tuple-specific equality assertion.\n\n        Args:\n            tuple1: The first tuple to compare.\n            tuple2: The second tuple to compare.\n            msg: Optional message to use on failure instead of a list of\n                    differences.\n        "));
      var1.setlocal("assertTupleEqual", var5);
      var3 = null;
      var1.setline(758);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertSetEqual$57, PyString.fromInterned("A set-specific equality assertion.\n\n        Args:\n            set1: The first set to compare.\n            set2: The second set to compare.\n            msg: Optional message to use on failure instead of a list of\n                    differences.\n\n        assertSetEqual uses ducktyping to support different types of sets, and\n        is optimized for sets specifically (parameters must support a\n        difference method).\n        "));
      var1.setlocal("assertSetEqual", var5);
      var3 = null;
      var1.setline(801);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertIn$58, PyString.fromInterned("Just like self.assertTrue(a in b), but with a nicer default message."));
      var1.setlocal("assertIn", var5);
      var3 = null;
      var1.setline(808);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertNotIn$59, PyString.fromInterned("Just like self.assertTrue(a not in b), but with a nicer default message."));
      var1.setlocal("assertNotIn", var5);
      var3 = null;
      var1.setline(815);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertIs$60, PyString.fromInterned("Just like self.assertTrue(a is b), but with a nicer default message."));
      var1.setlocal("assertIs", var5);
      var3 = null;
      var1.setline(822);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertIsNot$61, PyString.fromInterned("Just like self.assertTrue(a is not b), but with a nicer default message."));
      var1.setlocal("assertIsNot", var5);
      var3 = null;
      var1.setline(828);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertDictEqual$62, (PyObject)null);
      var1.setlocal("assertDictEqual", var5);
      var3 = null;
      var1.setline(840);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertDictContainsSubset$63, PyString.fromInterned("Checks whether actual is a superset of expected."));
      var1.setlocal("assertDictContainsSubset", var5);
      var3 = null;
      var1.setline(866);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertItemsEqual$65, PyString.fromInterned("An unordered sequence specific comparison. It asserts that\n        actual_seq and expected_seq have the same element counts.\n        Equivalent to::\n\n            self.assertEqual(Counter(iter(actual_seq)),\n                             Counter(iter(expected_seq)))\n\n        Asserts that each element has the same count in both sequences.\n        Example:\n            - [0, 1, 1] and [1, 0, 1] compare equal.\n            - [0, 0, 1] and [0, 1] compare unequal.\n        "));
      var1.setlocal("assertItemsEqual", var5);
      var3 = null;
      var1.setline(906);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertMultiLineEqual$66, PyString.fromInterned("Assert that two multi-line strings are equal."));
      var1.setlocal("assertMultiLineEqual", var5);
      var3 = null;
      var1.setline(929);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertLess$67, PyString.fromInterned("Just like self.assertTrue(a < b), but with a nicer default message."));
      var1.setlocal("assertLess", var5);
      var3 = null;
      var1.setline(935);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertLessEqual$68, PyString.fromInterned("Just like self.assertTrue(a <= b), but with a nicer default message."));
      var1.setlocal("assertLessEqual", var5);
      var3 = null;
      var1.setline(941);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertGreater$69, PyString.fromInterned("Just like self.assertTrue(a > b), but with a nicer default message."));
      var1.setlocal("assertGreater", var5);
      var3 = null;
      var1.setline(947);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertGreaterEqual$70, PyString.fromInterned("Just like self.assertTrue(a >= b), but with a nicer default message."));
      var1.setlocal("assertGreaterEqual", var5);
      var3 = null;
      var1.setline(953);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertIsNone$71, PyString.fromInterned("Same as self.assertTrue(obj is None), with a nicer default message."));
      var1.setlocal("assertIsNone", var5);
      var3 = null;
      var1.setline(959);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertIsNotNone$72, PyString.fromInterned("Included for symmetry with assertIsNone."));
      var1.setlocal("assertIsNotNone", var5);
      var3 = null;
      var1.setline(965);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertIsInstance$73, PyString.fromInterned("Same as self.assertTrue(isinstance(obj, cls)), with a nicer\n        default message."));
      var1.setlocal("assertIsInstance", var5);
      var3 = null;
      var1.setline(972);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertNotIsInstance$74, PyString.fromInterned("Included for symmetry with assertIsInstance."));
      var1.setlocal("assertNotIsInstance", var5);
      var3 = null;
      var1.setline(978);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertRaisesRegexp$75, PyString.fromInterned("Asserts that the message in a raised exception matches a regexp.\n\n        Args:\n            expected_exception: Exception class expected to be raised.\n            expected_regexp: Regexp (re pattern object or string) expected\n                    to be found in error message.\n            callable_obj: Function to be called.\n            args: Extra args.\n            kwargs: Extra kwargs.\n        "));
      var1.setlocal("assertRaisesRegexp", var5);
      var3 = null;
      var1.setline(996);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertRegexpMatches$76, PyString.fromInterned("Fail the test unless the text matches the regular expression."));
      var1.setlocal("assertRegexpMatches", var5);
      var3 = null;
      var1.setline(1005);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, assertNotRegexpMatches$77, PyString.fromInterned("Fail the test if the text matches the regular expression."));
      var1.setlocal("assertNotRegexpMatches", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$18(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyString.fromInterned("Create an instance of the class that will use the named test\n           method when executed. Raises a ValueError if the instance does\n           not have a method with the specified name.\n        ");
      var1.setline(186);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_testMethodName", var3);
      var3 = null;
      var1.setline(187);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_resultForDoCleanups", var3);
      var3 = null;

      PyException var6;
      try {
         var1.setline(189);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      } catch (Throwable var4) {
         var6 = Py.setException(var4, var1);
         if (var6.match(var1.getglobal("AttributeError"))) {
            var1.setline(191);
            throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("no such test method in %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), var1.getlocal(1)}))));
         }

         throw var6;
      }

      var1.setline(193);
      var3 = var1.getlocal(2).__getattr__("__doc__");
      var1.getlocal(0).__setattr__("_testMethodDoc", var3);
      var3 = null;
      var1.setline(194);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_cleanups", var7);
      var3 = null;
      var1.setline(199);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_type_equality_funcs", var8);
      var3 = null;
      var1.setline(200);
      var1.getlocal(0).__getattr__("addTypeEqualityFunc").__call__((ThreadState)var2, (PyObject)var1.getglobal("dict"), (PyObject)PyString.fromInterned("assertDictEqual"));
      var1.setline(201);
      var1.getlocal(0).__getattr__("addTypeEqualityFunc").__call__((ThreadState)var2, (PyObject)var1.getglobal("list"), (PyObject)PyString.fromInterned("assertListEqual"));
      var1.setline(202);
      var1.getlocal(0).__getattr__("addTypeEqualityFunc").__call__((ThreadState)var2, (PyObject)var1.getglobal("tuple"), (PyObject)PyString.fromInterned("assertTupleEqual"));
      var1.setline(203);
      var1.getlocal(0).__getattr__("addTypeEqualityFunc").__call__((ThreadState)var2, (PyObject)var1.getglobal("set"), (PyObject)PyString.fromInterned("assertSetEqual"));
      var1.setline(204);
      var1.getlocal(0).__getattr__("addTypeEqualityFunc").__call__((ThreadState)var2, (PyObject)var1.getglobal("frozenset"), (PyObject)PyString.fromInterned("assertSetEqual"));

      try {
         var1.setline(206);
         var1.getlocal(0).__getattr__("addTypeEqualityFunc").__call__((ThreadState)var2, (PyObject)var1.getglobal("unicode"), (PyObject)PyString.fromInterned("assertMultiLineEqual"));
      } catch (Throwable var5) {
         var6 = Py.setException(var5, var1);
         if (!var6.match(var1.getglobal("NameError"))) {
            throw var6;
         }

         var1.setline(209);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addTypeEqualityFunc$19(PyFrame var1, ThreadState var2) {
      var1.setline(223);
      PyString.fromInterned("Add a type specific assertEqual style function to compare a type.\n\n        This method is for use by TestCase subclasses that need to register\n        their own type equality functions to provide nicer error messages.\n\n        Args:\n            typeobj: The data type to call this function on when both values\n                    are of the same type in assertEqual().\n            function: The callable taking two arguments and an optional\n                    msg= argument that raises self.failureException with a\n                    useful error message when the two arguments are not equal.\n        ");
      var1.setline(224);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("_type_equality_funcs").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addCleanup$20(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyString.fromInterned("Add a function, with arguments, to be called when the test is\n        completed. Functions added are called on a LIFO basis and are\n        called after tearDown on test failure or success.\n\n        Cleanup items are called even if setUp fails (unlike tearDown).");
      var1.setline(232);
      var1.getlocal(0).__getattr__("_cleanups").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setUp$21(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      PyString.fromInterned("Hook method for setting up the test fixture before exercising it.");
      var1.setline(236);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$22(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyString.fromInterned("Hook method for deconstructing the test fixture after testing it.");
      var1.setline(240);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setUpClass$23(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyString.fromInterned("Hook method for setting up class fixture before running tests in the class.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownClass$24(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyString.fromInterned("Hook method for deconstructing the class fixture after running all tests in the class.");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject countTestCases$25(PyFrame var1, ThreadState var2) {
      var1.setline(251);
      PyInteger var3 = Py.newInteger(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject defaultTestResult$26(PyFrame var1, ThreadState var2) {
      var1.setline(254);
      PyObject var3 = var1.getglobal("result").__getattr__("TestResult").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject shortDescription$27(PyFrame var1, ThreadState var2) {
      var1.setline(262);
      PyString.fromInterned("Returns a one-line description of the test, or None if no\n        description has been provided.\n\n        The default implementation of this method returns the first line of\n        the specified test method's docstring.\n        ");
      var1.setline(263);
      PyObject var3 = var1.getlocal(0).__getattr__("_testMethodDoc");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(264);
      PyObject var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__getitem__(Py.newInteger(0)).__getattr__("strip").__call__(var2);
      }

      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("None");
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject id$28(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyObject var3 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("strclass").__call__(var2, var1.getlocal(0).__getattr__("__class__")), var1.getlocal(0).__getattr__("_testMethodName")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$29(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._isnot(var1.getglobal("type").__call__(var2, var1.getlocal(1)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(272);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(274);
         PyObject var4 = var1.getlocal(0).__getattr__("_testMethodName");
         var10000 = var4._eq(var1.getlocal(1).__getattr__("_testMethodName"));
         var4 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$30(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hash__$31(PyFrame var1, ThreadState var2) {
      var1.setline(280);
      PyObject var3 = var1.getglobal("hash").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(0)), var1.getlocal(0).__getattr__("_testMethodName")})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$32(PyFrame var1, ThreadState var2) {
      var1.setline(283);
      PyObject var3 = PyString.fromInterned("%s (%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_testMethodName"), var1.getglobal("strclass").__call__(var2, var1.getlocal(0).__getattr__("__class__"))}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$33(PyFrame var1, ThreadState var2) {
      var1.setline(286);
      PyObject var3 = PyString.fromInterned("<%s testMethod=%s>")._mod(new PyTuple(new PyObject[]{var1.getglobal("strclass").__call__(var2, var1.getlocal(0).__getattr__("__class__")), var1.getlocal(0).__getattr__("_testMethodName")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _addSkip$34(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      PyObject var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("addSkip"), (PyObject)var1.getglobal("None"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(291);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(292);
         var1.getlocal(3).__call__(var2, var1.getlocal(0), var1.getlocal(2));
      } else {
         var1.setline(294);
         var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("TestResult has no addSkip method, skips not reported"), (PyObject)var1.getglobal("RuntimeWarning"), (PyObject)Py.newInteger(2));
         var1.setline(296);
         var1.getlocal(1).__getattr__("addSuccess").__call__(var2, var1.getlocal(0));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$35(PyFrame var1, ThreadState var2) {
      var1.setline(299);
      PyObject var3 = var1.getlocal(1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(300);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(301);
         var3 = var1.getlocal(0).__getattr__("defaultTestResult").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(302);
         var3 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("startTestRun"), (PyObject)var1.getglobal("None"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(303);
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(304);
            var1.getlocal(3).__call__(var2);
         }
      }

      var1.setline(306);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_resultForDoCleanups", var3);
      var3 = null;
      var1.setline(307);
      var1.getlocal(1).__getattr__("startTest").__call__(var2, var1.getlocal(0));
      var1.setline(309);
      var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("_testMethodName"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(310);
      var10000 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("__class__"), (PyObject)PyString.fromInterned("__unittest_skip__"), (PyObject)var1.getglobal("False"));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)PyString.fromInterned("__unittest_skip__"), (PyObject)var1.getglobal("False"));
      }

      PyObject var4;
      if (var10000.__nonzero__()) {
         var3 = null;

         try {
            var1.setline(314);
            var10000 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("__class__"), (PyObject)PyString.fromInterned("__unittest_skip_why__"), (PyObject)PyString.fromInterned(""));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)PyString.fromInterned("__unittest_skip_why__"), (PyObject)PyString.fromInterned(""));
            }

            var4 = var10000;
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(316);
            var1.getlocal(0).__getattr__("_addSkip").__call__(var2, var1.getlocal(1), var1.getlocal(5));
         } catch (Throwable var7) {
            Py.addTraceback(var7, var1);
            var1.setline(318);
            var1.getlocal(1).__getattr__("stopTest").__call__(var2, var1.getlocal(0));
            throw (Throwable)var7;
         }

         var1.setline(318);
         var1.getlocal(1).__getattr__("stopTest").__call__(var2, var1.getlocal(0));
         var1.setline(319);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var3 = null;

         try {
            label145: {
               var1.setline(321);
               var4 = var1.getglobal("False");
               var1.setlocal(6, var4);
               var4 = null;

               try {
                  var1.setline(323);
                  var1.getlocal(0).__getattr__("setUp").__call__(var2);
               } catch (Throwable var8) {
                  PyException var13 = Py.setException(var8, var1);
                  if (var13.match(var1.getglobal("SkipTest"))) {
                     PyObject var5 = var13.value;
                     var1.setlocal(7, var5);
                     var5 = null;
                     var1.setline(325);
                     var1.getlocal(0).__getattr__("_addSkip").__call__(var2, var1.getlocal(1), var1.getglobal("str").__call__(var2, var1.getlocal(7)));
                     break label145;
                  }

                  if (var13.match(var1.getglobal("KeyboardInterrupt"))) {
                     var1.setline(327);
                     throw Py.makeException();
                  }

                  var1.setline(329);
                  var1.getlocal(1).__getattr__("addError").__call__(var2, var1.getlocal(0), var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
                  break label145;
               }

               PyObject var6;
               PyException var12;
               label126: {
                  try {
                     var1.setline(332);
                     var1.getlocal(4).__call__(var2);
                  } catch (Throwable var10) {
                     var12 = Py.setException(var10, var1);
                     if (var12.match(var1.getglobal("KeyboardInterrupt"))) {
                        var1.setline(334);
                        throw Py.makeException();
                     }

                     if (var12.match(var1.getlocal(0).__getattr__("failureException"))) {
                        var1.setline(336);
                        var1.getlocal(1).__getattr__("addFailure").__call__(var2, var1.getlocal(0), var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
                     } else if (var12.match(var1.getglobal("_ExpectedFailure"))) {
                        var6 = var12.value;
                        var1.setlocal(7, var6);
                        var6 = null;
                        var1.setline(338);
                        var6 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("addExpectedFailure"), (PyObject)var1.getglobal("None"));
                        var1.setlocal(8, var6);
                        var6 = null;
                        var1.setline(339);
                        var6 = var1.getlocal(8);
                        var10000 = var6._isnot(var1.getglobal("None"));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(340);
                           var1.getlocal(8).__call__(var2, var1.getlocal(0), var1.getlocal(7).__getattr__("exc_info"));
                        } else {
                           var1.setline(342);
                           var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestResult has no addExpectedFailure method, reporting as passes"), (PyObject)var1.getglobal("RuntimeWarning"));
                           var1.setline(344);
                           var1.getlocal(1).__getattr__("addSuccess").__call__(var2, var1.getlocal(0));
                        }
                     } else if (var12.match(var1.getglobal("_UnexpectedSuccess"))) {
                        var1.setline(346);
                        var6 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("addUnexpectedSuccess"), (PyObject)var1.getglobal("None"));
                        var1.setlocal(9, var6);
                        var6 = null;
                        var1.setline(347);
                        var6 = var1.getlocal(9);
                        var10000 = var6._isnot(var1.getglobal("None"));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(348);
                           var1.getlocal(9).__call__(var2, var1.getlocal(0));
                        } else {
                           var1.setline(350);
                           var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("TestResult has no addUnexpectedSuccess method, reporting as failures"), (PyObject)var1.getglobal("RuntimeWarning"));
                           var1.setline(352);
                           var1.getlocal(1).__getattr__("addFailure").__call__(var2, var1.getlocal(0), var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
                        }
                     } else if (var12.match(var1.getglobal("SkipTest"))) {
                        var6 = var12.value;
                        var1.setlocal(7, var6);
                        var6 = null;
                        var1.setline(354);
                        var1.getlocal(0).__getattr__("_addSkip").__call__(var2, var1.getlocal(1), var1.getglobal("str").__call__(var2, var1.getlocal(7)));
                     } else {
                        var1.setline(356);
                        var1.getlocal(1).__getattr__("addError").__call__(var2, var1.getlocal(0), var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
                     }
                     break label126;
                  }

                  var1.setline(358);
                  var6 = var1.getglobal("True");
                  var1.setlocal(6, var6);
                  var6 = null;
               }

               try {
                  var1.setline(361);
                  var1.getlocal(0).__getattr__("tearDown").__call__(var2);
               } catch (Throwable var9) {
                  var12 = Py.setException(var9, var1);
                  if (var12.match(var1.getglobal("KeyboardInterrupt"))) {
                     var1.setline(363);
                     throw Py.makeException();
                  }

                  var1.setline(365);
                  var1.getlocal(1).__getattr__("addError").__call__(var2, var1.getlocal(0), var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
                  var1.setline(366);
                  var6 = var1.getglobal("False");
                  var1.setlocal(6, var6);
                  var6 = null;
               }
            }

            var1.setline(368);
            var4 = var1.getlocal(0).__getattr__("doCleanups").__call__(var2);
            var1.setlocal(10, var4);
            var4 = null;
            var1.setline(369);
            var10000 = var1.getlocal(6);
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(10);
            }

            var4 = var10000;
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(370);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(371);
               var1.getlocal(1).__getattr__("addSuccess").__call__(var2, var1.getlocal(0));
            }
         } catch (Throwable var11) {
            Py.addTraceback(var11, var1);
            var1.setline(373);
            var1.getlocal(1).__getattr__("stopTest").__call__(var2, var1.getlocal(0));
            var1.setline(374);
            var4 = var1.getlocal(2);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(375);
               var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("stopTestRun"), (PyObject)var1.getglobal("None"));
               var1.setlocal(11, var4);
               var4 = null;
               var1.setline(376);
               var4 = var1.getlocal(11);
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(377);
                  var1.getlocal(11).__call__(var2);
               }
            }

            throw (Throwable)var11;
         }

         var1.setline(373);
         var1.getlocal(1).__getattr__("stopTest").__call__(var2, var1.getlocal(0));
         var1.setline(374);
         var4 = var1.getlocal(2);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(375);
            var4 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("stopTestRun"), (PyObject)var1.getglobal("None"));
            var1.setlocal(11, var4);
            var4 = null;
            var1.setline(376);
            var4 = var1.getlocal(11);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(377);
               var1.getlocal(11).__call__(var2);
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject doCleanups$36(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      PyString.fromInterned("Execute all cleanup functions. Normally called for you after\n        tearDown.");
      var1.setline(382);
      PyObject var3 = var1.getlocal(0).__getattr__("_resultForDoCleanups");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(383);
      var3 = var1.getglobal("True");
      var1.setlocal(2, var3);
      var3 = null;

      while(true) {
         var1.setline(384);
         if (!var1.getlocal(0).__getattr__("_cleanups").__nonzero__()) {
            var1.setline(393);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(385);
         var3 = var1.getlocal(0).__getattr__("_cleanups").__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(-1));
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

         try {
            var1.setline(387);
            PyObject var10000 = var1.getlocal(3);
            PyObject[] var10 = Py.EmptyObjects;
            String[] var8 = new String[0];
            var10000._callextra(var10, var8, var1.getlocal(4), var1.getlocal(5));
            var3 = null;
         } catch (Throwable var6) {
            PyException var9 = Py.setException(var6, var1);
            if (var9.match(var1.getglobal("KeyboardInterrupt"))) {
               var1.setline(389);
               throw Py.makeException();
            }

            var1.setline(391);
            PyObject var7 = var1.getglobal("False");
            var1.setlocal(2, var7);
            var4 = null;
            var1.setline(392);
            var1.getlocal(1).__getattr__("addError").__call__(var2, var1.getlocal(0), var1.getglobal("sys").__getattr__("exc_info").__call__(var2));
         }
      }
   }

   public PyObject __call__$37(PyFrame var1, ThreadState var2) {
      var1.setline(396);
      PyObject var10000 = var1.getlocal(0).__getattr__("run");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject debug$38(PyFrame var1, ThreadState var2) {
      var1.setline(399);
      PyString.fromInterned("Run the test without collecting errors in a TestResult");
      var1.setline(400);
      var1.getlocal(0).__getattr__("setUp").__call__(var2);
      var1.setline(401);
      var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("_testMethodName")).__call__(var2);
      var1.setline(402);
      var1.getlocal(0).__getattr__("tearDown").__call__(var2);

      while(true) {
         var1.setline(403);
         if (!var1.getlocal(0).__getattr__("_cleanups").__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(404);
         PyObject var3 = var1.getlocal(0).__getattr__("_cleanups").__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(-1));
         PyObject[] var4 = Py.unpackSequence(var3, 3);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(405);
         PyObject var10000 = var1.getlocal(1);
         PyObject[] var6 = Py.EmptyObjects;
         String[] var7 = new String[0];
         var10000._callextra(var6, var7, var1.getlocal(2), var1.getlocal(3));
         var3 = null;
      }
   }

   public PyObject skipTest$39(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyString.fromInterned("Skip this test.");
      var1.setline(409);
      throw Py.makeException(var1.getglobal("SkipTest").__call__(var2, var1.getlocal(1)));
   }

   public PyObject fail$40(PyFrame var1, ThreadState var2) {
      var1.setline(412);
      PyString.fromInterned("Fail immediately, with the given message.");
      var1.setline(413);
      throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, var1.getlocal(1)));
   }

   public PyObject assertFalse$41(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyString.fromInterned("Check that the expression is false.");
      var1.setline(417);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(418);
         PyObject var3 = var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(2), PyString.fromInterned("%s is not false")._mod(var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1))));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(419);
         throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, var1.getlocal(2)));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject assertTrue$42(PyFrame var1, ThreadState var2) {
      var1.setline(422);
      PyString.fromInterned("Check that the expression is true.");
      var1.setline(423);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(424);
         PyObject var3 = var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(2), PyString.fromInterned("%s is not true")._mod(var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1))));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(425);
         throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, var1.getlocal(2)));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _formatMessage$43(PyFrame var1, ThreadState var2) {
      var1.setline(436);
      PyString.fromInterned("Honour the longMessage attribute when generating failure messages.\n        If longMessage is False this means:\n        * Use only an explicit message if it is provided\n        * Otherwise use the standard message for the assert\n\n        If longMessage is True:\n        * Use the standard message\n        * If an explicit message is provided, plus ' : ' and the explicit message\n        ");
      var1.setline(437);
      PyObject var10000;
      PyObject var3;
      if (var1.getlocal(0).__getattr__("longMessage").__not__().__nonzero__()) {
         var1.setline(438);
         var10000 = var1.getlocal(1);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(2);
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(439);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(440);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         } else {
            try {
               var1.setline(444);
               var3 = PyString.fromInterned("%s : %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(1)}));
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var5) {
               PyException var6 = Py.setException(var5, var1);
               if (var6.match(var1.getglobal("UnicodeDecodeError"))) {
                  var1.setline(446);
                  var3 = PyString.fromInterned("%s : %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1))}));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  throw var6;
               }
            }
         }
      }
   }

   public PyObject assertRaises$44(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(471);
      PyString.fromInterned("Fail unless an exception of class excClass is raised\n           by callableObj when invoked with arguments args and keyword\n           arguments kwargs. If a different type of exception is\n           raised, it will not be caught, and the test case will be\n           deemed to have suffered an error, exactly as for an\n           unexpected exception.\n\n           If called with callableObj omitted or None, will return a\n           context object used like this::\n\n                with self.assertRaises(SomeException):\n                    do_something()\n\n           The context manager keeps a reference to the exception as\n           the 'exception' attribute. This allows you to inspect the\n           exception after the assertion::\n\n               with self.assertRaises(SomeException) as cm:\n                   do_something()\n               the_exception = cm.exception\n               self.assertEqual(the_exception.error_code, 3)\n        ");
      var1.setline(472);
      PyObject var3 = var1.getglobal("_AssertRaisesContext").__call__(var2, var1.getlocal(1), var1.getlocal(0));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(473);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(474);
         var3 = var1.getlocal(5);
         var1.f_lasti = -1;
         return var3;
      } else {
         ContextManager var4;
         PyObject var5 = (var4 = ContextGuard.getManager(var1.getlocal(5))).__enter__(var2);

         label18: {
            try {
               var1.setline(476);
               var10000 = var1.getlocal(2);
               PyObject[] var8 = Py.EmptyObjects;
               String[] var6 = new String[0];
               var10000._callextra(var8, var6, var1.getlocal(3), var1.getlocal(4));
               var5 = null;
            } catch (Throwable var7) {
               if (var4.__exit__(var2, Py.setException(var7, var1))) {
                  break label18;
               }

               throw (Throwable)Py.makeException();
            }

            var4.__exit__(var2, (PyException)null);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _getAssertEqualityFunc$45(PyFrame var1, ThreadState var2) {
      var1.setline(484);
      PyString.fromInterned("Get a detailed comparison function for the types of the two args.\n\n        Returns: A callable accepting (first, second, msg=None) that will\n        raise a failure exception if first != second with a useful human\n        readable error message for those types.\n        ");
      var1.setline(495);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._is(var1.getglobal("type").__call__(var2, var1.getlocal(2)));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(496);
         var3 = var1.getlocal(0).__getattr__("_type_equality_funcs").__getattr__("get").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(1)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(497);
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(498);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("basestring")).__nonzero__()) {
               var1.setline(499);
               var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(3));
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(500);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }
      }

      var1.setline(502);
      var3 = var1.getlocal(0).__getattr__("_baseAssertEqual");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _baseAssertEqual$46(PyFrame var1, ThreadState var2) {
      var1.setline(505);
      PyString.fromInterned("The default assertEqual implementation, not type specific.");
      var1.setline(506);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getlocal(2));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(507);
         var3 = PyString.fromInterned("%s != %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2))}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(508);
         var3 = var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(4));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(509);
         throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, var1.getlocal(3)));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject assertEqual$47(PyFrame var1, ThreadState var2) {
      var1.setline(514);
      PyString.fromInterned("Fail if the two objects are unequal as determined by the '=='\n           operator.\n        ");
      var1.setline(515);
      PyObject var3 = var1.getlocal(0).__getattr__("_getAssertEqualityFunc").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(516);
      PyObject var10000 = var1.getlocal(4);
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)};
      String[] var4 = new String[]{"msg"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertNotEqual$48(PyFrame var1, ThreadState var2) {
      var1.setline(521);
      PyString.fromInterned("Fail if the two objects are equal as determined by the '!='\n           operator.\n        ");
      var1.setline(522);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(var1.getlocal(2));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(523);
         var3 = var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), PyString.fromInterned("%s == %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2))})));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(525);
         throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, var1.getlocal(3)));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject assertAlmostEqual$49(PyFrame var1, ThreadState var2) {
      var1.setline(539);
      PyString.fromInterned("Fail if the two objects are unequal as determined by their\n           difference rounded to the given number of decimal places\n           (default 7) and comparing to zero, or by comparing that the\n           between the two objects is more than the given delta.\n\n           Note that decimal places (from zero) are usually not the same\n           as significant digits (measured from the most signficant digit).\n\n           If the two objects compare equal then they will automatically\n           compare almost equal.\n        ");
      var1.setline(540);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(542);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(543);
         var3 = var1.getlocal(5);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(3);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(544);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("specify delta or places not both")));
         } else {
            var1.setline(546);
            var3 = var1.getlocal(5);
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(547);
               var3 = var1.getglobal("abs").__call__(var2, var1.getlocal(1)._sub(var1.getlocal(2)));
               var10000 = var3._le(var1.getlocal(5));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(548);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(550);
               var3 = PyString.fromInterned("%s != %s within %s delta")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(5))}));
               var1.setlocal(6, var3);
               var3 = null;
            } else {
               var1.setline(554);
               var3 = var1.getlocal(3);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(555);
                  PyInteger var4 = Py.newInteger(7);
                  var1.setlocal(3, var4);
                  var3 = null;
               }

               var1.setline(557);
               var3 = var1.getglobal("round").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(2)._sub(var1.getlocal(1))), var1.getlocal(3));
               var10000 = var3._eq(Py.newInteger(0));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(558);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setline(560);
               var3 = PyString.fromInterned("%s != %s within %r places")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2)), var1.getlocal(3)}));
               var1.setlocal(6, var3);
               var3 = null;
            }

            var1.setline(563);
            var3 = var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(4), var1.getlocal(6));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(564);
            throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, var1.getlocal(4)));
         }
      }
   }

   public PyObject assertNotAlmostEqual$50(PyFrame var1, ThreadState var2) {
      var1.setline(576);
      PyString.fromInterned("Fail if the two objects are equal as determined by their\n           difference rounded to the given number of decimal places\n           (default 7) and comparing to zero, or by comparing that the\n           between the two objects is less than the given delta.\n\n           Note that decimal places (from zero) are usually not the same\n           as significant digits (measured from the most signficant digit).\n\n           Objects that are equal automatically fail.\n        ");
      var1.setline(577);
      PyObject var3 = var1.getlocal(5);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(578);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("specify delta or places not both")));
      } else {
         var1.setline(579);
         var3 = var1.getlocal(5);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(580);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(var1.getlocal(2));
            var3 = null;
            var10000 = var10000.__not__();
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("abs").__call__(var2, var1.getlocal(1)._sub(var1.getlocal(2)));
               var10000 = var3._gt(var1.getlocal(5));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(581);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(582);
            var3 = PyString.fromInterned("%s == %s within %s delta")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(5))}));
            var1.setlocal(6, var3);
            var3 = null;
         } else {
            var1.setline(586);
            var3 = var1.getlocal(3);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(587);
               PyInteger var4 = Py.newInteger(7);
               var1.setlocal(3, var4);
               var3 = null;
            }

            var1.setline(588);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(var1.getlocal(2));
            var3 = null;
            var10000 = var10000.__not__();
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("round").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(2)._sub(var1.getlocal(1))), var1.getlocal(3));
               var10000 = var3._ne(Py.newInteger(0));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(589);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(590);
            var3 = PyString.fromInterned("%s == %s within %r places")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2)), var1.getlocal(3)}));
            var1.setlocal(6, var3);
            var3 = null;
         }

         var1.setline(594);
         var3 = var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(4), var1.getlocal(6));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(595);
         throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, var1.getlocal(4)));
      }
   }

   public PyObject _deprecate$51(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(611);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = deprecated_func$52;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(616);
      PyObject var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject deprecated_func$52(PyFrame var1, ThreadState var2) {
      var1.setline(612);
      var1.getglobal("warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("Please use {0} instead.").__getattr__("format").__call__(var2, var1.getderef(0).__getattr__("__name__")), (PyObject)var1.getglobal("PendingDeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(615);
      PyObject var10000 = var1.getderef(0);
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(0), var1.getlocal(1));
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject assertSequenceEqual$53(PyFrame var1, ThreadState var2) {
      var1.setline(639);
      PyString.fromInterned("An equality assertion for ordered sequences (like lists and tuples).\n\n        For the purposes of this function, a valid ordered sequence type is one\n        which can be indexed, has a length, and has an equality operator.\n\n        Args:\n            seq1: The first sequence to compare.\n            seq2: The second sequence to compare.\n            seq_type: The expected datatype of the sequences, or None if no\n                    datatype should be enforced.\n            msg: Optional message to use on failure instead of a list of\n                    differences.\n        ");
      var1.setline(640);
      PyObject var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(641);
         var3 = var1.getlocal(4).__getattr__("__name__");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(642);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getlocal(4)).__not__().__nonzero__()) {
            var1.setline(643);
            throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, PyString.fromInterned("First sequence is not a %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1))}))));
         }

         var1.setline(645);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getlocal(4)).__not__().__nonzero__()) {
            var1.setline(646);
            throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, PyString.fromInterned("Second sequence is not a %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2))}))));
         }
      } else {
         var1.setline(649);
         PyString var13 = PyString.fromInterned("sequence");
         var1.setlocal(5, var13);
         var3 = null;
      }

      var1.setline(651);
      var3 = var1.getglobal("None");
      var1.setlocal(6, var3);
      var3 = null;

      PyObject var4;
      PyException var15;
      try {
         var1.setline(653);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var1.setlocal(7, var3);
         var3 = null;
      } catch (Throwable var12) {
         var15 = Py.setException(var12, var1);
         if (!var15.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("NotImplementedError")}))) {
            throw var15;
         }

         var1.setline(655);
         var4 = PyString.fromInterned("First %s has no length.    Non-sequence?")._mod(var1.getlocal(5));
         var1.setlocal(6, var4);
         var4 = null;
      }

      var1.setline(658);
      var3 = var1.getlocal(6);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(660);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var1.setlocal(8, var3);
            var3 = null;
         } catch (Throwable var11) {
            var15 = Py.setException(var11, var1);
            if (!var15.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("NotImplementedError")}))) {
               throw var15;
            }

            var1.setline(662);
            var4 = PyString.fromInterned("Second %s has no length.    Non-sequence?")._mod(var1.getlocal(5));
            var1.setlocal(6, var4);
            var4 = null;
         }
      }

      var1.setline(665);
      var3 = var1.getlocal(6);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(666);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(667);
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(669);
         var3 = var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(670);
         var3 = var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2));
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(671);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(9));
         var10000 = var3._gt(Py.newInteger(30));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(672);
            var3 = var1.getlocal(9).__getslice__((PyObject)null, Py.newInteger(30), (PyObject)null)._add(PyString.fromInterned("..."));
            var1.setlocal(9, var3);
            var3 = null;
         }

         var1.setline(673);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(10));
         var10000 = var3._gt(Py.newInteger(30));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(674);
            var3 = var1.getlocal(10).__getslice__((PyObject)null, Py.newInteger(30), (PyObject)null)._add(PyString.fromInterned("..."));
            var1.setlocal(10, var3);
            var3 = null;
         }

         var1.setline(675);
         PyTuple var16 = new PyTuple(new PyObject[]{var1.getlocal(5).__getattr__("capitalize").__call__(var2), var1.getlocal(9), var1.getlocal(10)});
         var1.setlocal(11, var16);
         var3 = null;
         var1.setline(676);
         var3 = PyString.fromInterned("%ss differ: %s != %s\n")._mod(var1.getlocal(11));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(678);
         var3 = var1.getglobal("xrange").__call__(var2, var1.getglobal("min").__call__(var2, var1.getlocal(7), var1.getlocal(8))).__iter__();

         while(true) {
            var1.setline(678);
            var4 = var3.__iternext__();
            PyException var5;
            PyObject var14;
            if (var4 == null) {
               var1.setline(698);
               var14 = var1.getlocal(7);
               var10000 = var14._eq(var1.getlocal(8));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var14 = var1.getlocal(4);
                  var10000 = var14._is(var1.getglobal("None"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var14 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
                     var10000 = var14._ne(var1.getglobal("type").__call__(var2, var1.getlocal(2)));
                     var5 = null;
                  }
               }

               if (var10000.__nonzero__()) {
                  var1.setline(701);
                  var1.f_lasti = -1;
                  return Py.None;
               }
               break;
            }

            var1.setlocal(12, var4);

            PyObject var6;
            try {
               var1.setline(680);
               var14 = var1.getlocal(1).__getitem__(var1.getlocal(12));
               var1.setlocal(13, var14);
               var5 = null;
            } catch (Throwable var9) {
               var5 = Py.setException(var9, var1);
               if (var5.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("IndexError"), var1.getglobal("NotImplementedError")}))) {
                  var1.setline(682);
                  var6 = var1.getlocal(6);
                  var6 = var6._iadd(PyString.fromInterned("\nUnable to index element %d of first %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(5)})));
                  var1.setlocal(6, var6);
                  break;
               }

               throw var5;
            }

            try {
               var1.setline(687);
               var14 = var1.getlocal(2).__getitem__(var1.getlocal(12));
               var1.setlocal(14, var14);
               var5 = null;
            } catch (Throwable var10) {
               var5 = Py.setException(var10, var1);
               if (var5.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("IndexError"), var1.getglobal("NotImplementedError")}))) {
                  var1.setline(689);
                  var6 = var1.getlocal(6);
                  var6 = var6._iadd(PyString.fromInterned("\nUnable to index element %d of second %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(5)})));
                  var1.setlocal(6, var6);
                  break;
               }

               throw var5;
            }

            var1.setline(693);
            var14 = var1.getlocal(13);
            var10000 = var14._ne(var1.getlocal(14));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(694);
               var14 = var1.getlocal(6);
               var14 = var14._iadd(PyString.fromInterned("\nFirst differing element %d:\n%s\n%s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(12), var1.getlocal(13), var1.getlocal(14)})));
               var1.setlocal(6, var14);
               break;
            }
         }

         var1.setline(703);
         var3 = var1.getlocal(7);
         var10000 = var3._gt(var1.getlocal(8));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(704);
            var3 = var1.getlocal(6);
            var3 = var3._iadd(PyString.fromInterned("\nFirst %s contains %d additional elements.\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(7)._sub(var1.getlocal(8))})));
            var1.setlocal(6, var3);

            try {
               var1.setline(707);
               var3 = var1.getlocal(6);
               var3 = var3._iadd(PyString.fromInterned("First extra element %d:\n%s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(1).__getitem__(var1.getlocal(8))})));
               var1.setlocal(6, var3);
            } catch (Throwable var7) {
               var15 = Py.setException(var7, var1);
               if (!var15.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("IndexError"), var1.getglobal("NotImplementedError")}))) {
                  throw var15;
               }

               var1.setline(710);
               var4 = var1.getlocal(6);
               var4 = var4._iadd(PyString.fromInterned("Unable to index element %d of first %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(5)})));
               var1.setlocal(6, var4);
            }
         } else {
            var1.setline(712);
            var3 = var1.getlocal(7);
            var10000 = var3._lt(var1.getlocal(8));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(713);
               var3 = var1.getlocal(6);
               var3 = var3._iadd(PyString.fromInterned("\nSecond %s contains %d additional elements.\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(8)._sub(var1.getlocal(7))})));
               var1.setlocal(6, var3);

               try {
                  var1.setline(716);
                  var3 = var1.getlocal(6);
                  var3 = var3._iadd(PyString.fromInterned("First extra element %d:\n%s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(2).__getitem__(var1.getlocal(7))})));
                  var1.setlocal(6, var3);
               } catch (Throwable var8) {
                  var15 = Py.setException(var8, var1);
                  if (!var15.match(new PyTuple(new PyObject[]{var1.getglobal("TypeError"), var1.getglobal("IndexError"), var1.getglobal("NotImplementedError")}))) {
                     throw var15;
                  }

                  var1.setline(719);
                  var4 = var1.getlocal(6);
                  var4 = var4._iadd(PyString.fromInterned("Unable to index element %d of second %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5)})));
                  var1.setlocal(6, var4);
               }
            }
         }
      }

      var1.setline(721);
      var3 = var1.getlocal(6);
      var1.setlocal(15, var3);
      var3 = null;
      var1.setline(722);
      var3 = PyString.fromInterned("\n")._add(PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getglobal("difflib").__getattr__("ndiff").__call__(var2, var1.getglobal("pprint").__getattr__("pformat").__call__(var2, var1.getlocal(1)).__getattr__("splitlines").__call__(var2), var1.getglobal("pprint").__getattr__("pformat").__call__(var2, var1.getlocal(2)).__getattr__("splitlines").__call__(var2))));
      var1.setlocal(16, var3);
      var3 = null;
      var1.setline(725);
      var3 = var1.getlocal(0).__getattr__("_truncateMessage").__call__(var2, var1.getlocal(15), var1.getlocal(16));
      var1.setlocal(15, var3);
      var3 = null;
      var1.setline(726);
      var3 = var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(15));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(727);
      var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _truncateMessage$54(PyFrame var1, ThreadState var2) {
      var1.setline(730);
      PyObject var3 = var1.getlocal(0).__getattr__("maxDiff");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(731);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var3._le(var1.getlocal(3));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(732);
         var3 = var1.getlocal(1)._add(var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(733);
         var3 = var1.getlocal(1)._add(var1.getglobal("DIFF_OMITTED")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(2))));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject assertListEqual$55(PyFrame var1, ThreadState var2) {
      var1.setline(744);
      PyString.fromInterned("A list-specific equality assertion.\n\n        Args:\n            list1: The first list to compare.\n            list2: The second list to compare.\n            msg: Optional message to use on failure instead of a list of\n                    differences.\n\n        ");
      var1.setline(745);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertSequenceEqual");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getglobal("list")};
      String[] var4 = new String[]{"seq_type"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertTupleEqual$56(PyFrame var1, ThreadState var2) {
      var1.setline(755);
      PyString.fromInterned("A tuple-specific equality assertion.\n\n        Args:\n            tuple1: The first tuple to compare.\n            tuple2: The second tuple to compare.\n            msg: Optional message to use on failure instead of a list of\n                    differences.\n        ");
      var1.setline(756);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertSequenceEqual");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getglobal("tuple")};
      String[] var4 = new String[]{"seq_type"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertSetEqual$57(PyFrame var1, ThreadState var2) {
      var1.setline(770);
      PyString.fromInterned("A set-specific equality assertion.\n\n        Args:\n            set1: The first set to compare.\n            set2: The second set to compare.\n            msg: Optional message to use on failure instead of a list of\n                    differences.\n\n        assertSetEqual uses ducktyping to support different types of sets, and\n        is optimized for sets specifically (parameters must support a\n        difference method).\n        ");

      PyException var3;
      PyObject var4;
      PyObject var7;
      try {
         var1.setline(772);
         var7 = var1.getlocal(1).__getattr__("difference").__call__(var2, var1.getlocal(2));
         var1.setlocal(4, var7);
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(var1.getglobal("TypeError"))) {
            var4 = var3.value;
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(774);
            var1.getlocal(0).__getattr__("fail").__call__(var2, PyString.fromInterned("invalid type when attempting set difference: %s")._mod(var1.getlocal(5)));
         } else {
            if (!var3.match(var1.getglobal("AttributeError"))) {
               throw var3;
            }

            var4 = var3.value;
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(776);
            var1.getlocal(0).__getattr__("fail").__call__(var2, PyString.fromInterned("first argument does not support set difference: %s")._mod(var1.getlocal(5)));
         }
      }

      try {
         var1.setline(779);
         var7 = var1.getlocal(2).__getattr__("difference").__call__(var2, var1.getlocal(1));
         var1.setlocal(6, var7);
         var3 = null;
      } catch (Throwable var5) {
         var3 = Py.setException(var5, var1);
         if (var3.match(var1.getglobal("TypeError"))) {
            var4 = var3.value;
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(781);
            var1.getlocal(0).__getattr__("fail").__call__(var2, PyString.fromInterned("invalid type when attempting set difference: %s")._mod(var1.getlocal(5)));
         } else {
            if (!var3.match(var1.getglobal("AttributeError"))) {
               throw var3;
            }

            var4 = var3.value;
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(783);
            var1.getlocal(0).__getattr__("fail").__call__(var2, PyString.fromInterned("second argument does not support set difference: %s")._mod(var1.getlocal(5)));
         }
      }

      var1.setline(785);
      PyObject var10000 = var1.getlocal(4);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(6);
      }

      if (var10000.__not__().__nonzero__()) {
         var1.setline(786);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(788);
         PyList var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(7, var8);
         var3 = null;
         var1.setline(789);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(790);
            var1.getlocal(7).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Items in the first set but not the second:"));
            var1.setline(791);
            var7 = var1.getlocal(4).__iter__();

            while(true) {
               var1.setline(791);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(8, var4);
               var1.setline(792);
               var1.getlocal(7).__getattr__("append").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(8)));
            }
         }

         var1.setline(793);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(794);
            var1.getlocal(7).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Items in the second set but not the first:"));
            var1.setline(795);
            var7 = var1.getlocal(6).__iter__();

            while(true) {
               var1.setline(795);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(8, var4);
               var1.setline(796);
               var1.getlocal(7).__getattr__("append").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getlocal(8)));
            }
         }

         var1.setline(798);
         var7 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(7));
         var1.setlocal(9, var7);
         var3 = null;
         var1.setline(799);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(9)));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject assertIn$58(PyFrame var1, ThreadState var2) {
      var1.setline(802);
      PyString.fromInterned("Just like self.assertTrue(a in b), but with a nicer default message.");
      var1.setline(803);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(804);
         var3 = PyString.fromInterned("%s not found in %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2))}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(806);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertNotIn$59(PyFrame var1, ThreadState var2) {
      var1.setline(809);
      PyString.fromInterned("Just like self.assertTrue(a not in b), but with a nicer default message.");
      var1.setline(810);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._in(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(811);
         var3 = PyString.fromInterned("%s unexpectedly found in %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2))}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(813);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertIs$60(PyFrame var1, ThreadState var2) {
      var1.setline(816);
      PyString.fromInterned("Just like self.assertTrue(a is b), but with a nicer default message.");
      var1.setline(817);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(818);
         var3 = PyString.fromInterned("%s is not %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2))}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(820);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertIsNot$61(PyFrame var1, ThreadState var2) {
      var1.setline(823);
      PyString.fromInterned("Just like self.assertTrue(a is not b), but with a nicer default message.");
      var1.setline(824);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(825);
         var3 = PyString.fromInterned("unexpectedly identical: %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1))}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(826);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertDictEqual$62(PyFrame var1, ThreadState var2) {
      var1.setline(829);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getglobal("dict"), (PyObject)PyString.fromInterned("First argument is not a dictionary"));
      var1.setline(830);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getglobal("dict"), (PyObject)PyString.fromInterned("Second argument is not a dictionary"));
      var1.setline(832);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(833);
         var3 = PyString.fromInterned("%s != %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1), var1.getglobal("True")), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2), var1.getglobal("True"))}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(834);
         var3 = PyString.fromInterned("\n")._add(PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getglobal("difflib").__getattr__("ndiff").__call__(var2, var1.getglobal("pprint").__getattr__("pformat").__call__(var2, var1.getlocal(1)).__getattr__("splitlines").__call__(var2), var1.getglobal("pprint").__getattr__("pformat").__call__(var2, var1.getlocal(2)).__getattr__("splitlines").__call__(var2))));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(837);
         var3 = var1.getlocal(0).__getattr__("_truncateMessage").__call__(var2, var1.getlocal(4), var1.getlocal(5));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(838);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertDictContainsSubset$63(PyFrame var1, ThreadState var2) {
      var1.setline(841);
      PyString.fromInterned("Checks whether actual is a superset of expected.");
      var1.setline(842);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(843);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(844);
      PyObject var7 = var1.getlocal(1).__getattr__("iteritems").__call__(var2).__iter__();

      while(true) {
         var1.setline(844);
         PyObject var4 = var7.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(852);
            var10000 = var1.getlocal(4);
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(5);
            }

            if (var10000.__not__().__nonzero__()) {
               var1.setline(853);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setline(855);
            PyString var9 = PyString.fromInterned("");
            var1.setlocal(8, var9);
            var3 = null;
            var1.setline(856);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(857);
               PyString var12 = PyString.fromInterned("Missing: %s");
               PyObject var10001 = PyString.fromInterned(",").__getattr__("join");
               var1.setline(857);
               PyObject[] var11 = Py.EmptyObjects;
               PyFunction var8 = new PyFunction(var1.f_globals, var11, f$64, (PyObject)null);
               PyObject var10003 = var8.__call__(var2, var1.getlocal(4).__iter__());
               Arrays.fill(var11, (Object)null);
               var7 = var12._mod(var10001.__call__(var2, var10003));
               var1.setlocal(8, var7);
               var3 = null;
            }

            var1.setline(859);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(860);
               if (var1.getlocal(8).__nonzero__()) {
                  var1.setline(861);
                  var7 = var1.getlocal(8);
                  var7 = var7._iadd(PyString.fromInterned("; "));
                  var1.setlocal(8, var7);
               }

               var1.setline(862);
               var7 = var1.getlocal(8);
               var7 = var7._iadd(PyString.fromInterned("Mismatched values: %s")._mod(PyString.fromInterned(",").__getattr__("join").__call__(var2, var1.getlocal(5))));
               var1.setlocal(8, var7);
            }

            var1.setline(864);
            var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(8)));
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(845);
         PyObject var10 = var1.getlocal(6);
         var10000 = var10._notin(var1.getlocal(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(846);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6));
         } else {
            var1.setline(847);
            var10 = var1.getlocal(7);
            var10000 = var10._ne(var1.getlocal(2).__getitem__(var1.getlocal(6)));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(848);
               var1.getlocal(5).__getattr__("append").__call__(var2, PyString.fromInterned("%s, expected: %s, actual: %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(6)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(7)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(6)))})));
            }
         }
      }
   }

   public PyObject f$64(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(857);
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

      var1.setline(857);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(857);
         var1.setline(857);
         var6 = var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject assertItemsEqual$65(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(878);
      PyString.fromInterned("An unordered sequence specific comparison. It asserts that\n        actual_seq and expected_seq have the same element counts.\n        Equivalent to::\n\n            self.assertEqual(Counter(iter(actual_seq)),\n                             Counter(iter(expected_seq)))\n\n        Asserts that each element has the same count in both sequences.\n        Example:\n            - [0, 1, 1] and [1, 0, 1] compare equal.\n            - [0, 0, 1] and [0, 1] compare unequal.\n        ");
      var1.setline(879);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("list").__call__(var2, var1.getlocal(1)), var1.getglobal("list").__call__(var2, var1.getlocal(2))});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      ContextManager var10;
      PyObject var12 = (var10 = ContextGuard.getManager(var1.getglobal("warnings").__getattr__("catch_warnings").__call__(var2))).__enter__(var2);

      label74: {
         label73: {
            Throwable var10000;
            label72: {
               boolean var10001;
               label78: {
                  try {
                     var1.setline(881);
                     if (var1.getglobal("sys").__getattr__("py3kwarning").__nonzero__()) {
                        var1.setline(883);
                        var12 = (new PyList(new PyObject[]{PyString.fromInterned("(code|dict|type) inequality comparisons"), PyString.fromInterned("builtin_function_or_method order comparisons"), PyString.fromInterned("comparing unequal types")})).__iter__();

                        while(true) {
                           var1.setline(883);
                           var5 = var12.__iternext__();
                           if (var5 == null) {
                              break;
                           }

                           var1.setlocal(6, var5);
                           var1.setline(886);
                           var1.getglobal("warnings").__getattr__("filterwarnings").__call__((ThreadState)var2, PyString.fromInterned("ignore"), (PyObject)var1.getlocal(6), (PyObject)var1.getglobal("DeprecationWarning"));
                        }
                     }

                     try {
                        var1.setline(888);
                        var12 = var1.getglobal("collections").__getattr__("Counter").__call__(var2, var1.getlocal(4));
                        var1.setlocal(7, var12);
                        var4 = null;
                        var1.setline(889);
                        var12 = var1.getglobal("collections").__getattr__("Counter").__call__(var2, var1.getlocal(5));
                        var1.setlocal(8, var12);
                        var4 = null;
                     } catch (Throwable var7) {
                        PyException var14 = Py.setException(var7, var1);
                        if (var14.match(var1.getglobal("TypeError"))) {
                           var1.setline(892);
                           var5 = var1.getglobal("_count_diff_all_purpose").__call__(var2, var1.getlocal(4), var1.getlocal(5));
                           var1.setlocal(9, var5);
                           var5 = null;
                           break label73;
                        }

                        throw var14;
                     }

                     var1.setline(894);
                     var5 = var1.getlocal(7);
                     PyObject var16 = var5._eq(var1.getlocal(8));
                     var5 = null;
                     if (!var16.__nonzero__()) {
                        break label78;
                     }

                     var1.setline(895);
                  } catch (Throwable var9) {
                     var10000 = var9;
                     var10001 = false;
                     break label72;
                  }

                  var10.__exit__(var2, (PyException)null);

                  try {
                     var1.f_lasti = -1;
                     return Py.None;
                  } catch (Throwable var6) {
                     var10000 = var6;
                     var10001 = false;
                     break label72;
                  }
               }

               try {
                  var1.setline(896);
                  var5 = var1.getglobal("_count_diff_hashable").__call__(var2, var1.getlocal(4), var1.getlocal(5));
                  var1.setlocal(9, var5);
                  var5 = null;
                  break label73;
               } catch (Throwable var8) {
                  var10000 = var8;
                  var10001 = false;
               }
            }

            if (!var10.__exit__(var2, Py.setException(var10000, var1))) {
               throw (Throwable)Py.makeException();
            }
            break label74;
         }

         var10.__exit__(var2, (PyException)null);
      }

      var1.setline(898);
      if (var1.getlocal(9).__nonzero__()) {
         var1.setline(899);
         PyString var11 = PyString.fromInterned("Element counts were not equal:\n");
         var1.setlocal(10, var11);
         var3 = null;
         var1.setline(900);
         PyList var17 = new PyList();
         PyObject var13 = var17.__getattr__("append");
         var1.setlocal(12, var13);
         var3 = null;
         var1.setline(900);
         var13 = var1.getlocal(9).__iter__();

         while(true) {
            var1.setline(900);
            var12 = var13.__iternext__();
            if (var12 == null) {
               var1.setline(900);
               var1.dellocal(12);
               PyList var15 = var17;
               var1.setlocal(11, var15);
               var3 = null;
               var1.setline(901);
               var13 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(11));
               var1.setlocal(14, var13);
               var3 = null;
               var1.setline(902);
               var13 = var1.getlocal(0).__getattr__("_truncateMessage").__call__(var2, var1.getlocal(10), var1.getlocal(14));
               var1.setlocal(10, var13);
               var3 = null;
               var1.setline(903);
               var13 = var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(10));
               var1.setlocal(3, var13);
               var3 = null;
               var1.setline(904);
               var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(3));
               break;
            }

            var1.setlocal(13, var12);
            var1.setline(900);
            var1.getlocal(12).__call__(var2, PyString.fromInterned("First has %d, Second has %d:  %r")._mod(var1.getlocal(13)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertMultiLineEqual$66(PyFrame var1, ThreadState var2) {
      var1.setline(907);
      PyString.fromInterned("Assert that two multi-line strings are equal.");
      var1.setline(908);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)var1.getglobal("basestring"), (PyObject)PyString.fromInterned("First argument is not a string"));
      var1.setline(910);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getglobal("basestring"), (PyObject)PyString.fromInterned("Second argument is not a string"));
      var1.setline(913);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ne(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(915);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
         var10000 = var3._gt(var1.getlocal(0).__getattr__("_diffThreshold"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._gt(var1.getlocal(0).__getattr__("_diffThreshold"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(917);
            var1.getlocal(0).__getattr__("_baseAssertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
         }

         var1.setline(918);
         var3 = var1.getlocal(1).__getattr__("splitlines").__call__(var2, var1.getglobal("True"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(919);
         var3 = var1.getlocal(2).__getattr__("splitlines").__call__(var2, var1.getglobal("True"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(920);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1).__getattr__("strip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\r\n"));
            var10000 = var3._eq(var1.getlocal(1));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(921);
            PyList var4 = new PyList(new PyObject[]{var1.getlocal(1)._add(PyString.fromInterned("\n"))});
            var1.setlocal(4, var4);
            var3 = null;
            var1.setline(922);
            var4 = new PyList(new PyObject[]{var1.getlocal(2)._add(PyString.fromInterned("\n"))});
            var1.setlocal(5, var4);
            var3 = null;
         }

         var1.setline(923);
         var3 = PyString.fromInterned("%s != %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1), var1.getglobal("True")), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2), var1.getglobal("True"))}));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(925);
         var3 = PyString.fromInterned("\n")._add(PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getglobal("difflib").__getattr__("ndiff").__call__(var2, var1.getlocal(4), var1.getlocal(5))));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(926);
         var3 = var1.getlocal(0).__getattr__("_truncateMessage").__call__(var2, var1.getlocal(6), var1.getlocal(7));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(927);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(6)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertLess$67(PyFrame var1, ThreadState var2) {
      var1.setline(930);
      PyString.fromInterned("Just like self.assertTrue(a < b), but with a nicer default message.");
      var1.setline(931);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._lt(var1.getlocal(2));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(932);
         var3 = PyString.fromInterned("%s not less than %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2))}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(933);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertLessEqual$68(PyFrame var1, ThreadState var2) {
      var1.setline(936);
      PyString.fromInterned("Just like self.assertTrue(a <= b), but with a nicer default message.");
      var1.setline(937);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._le(var1.getlocal(2));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(938);
         var3 = PyString.fromInterned("%s not less than or equal to %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2))}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(939);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertGreater$69(PyFrame var1, ThreadState var2) {
      var1.setline(942);
      PyString.fromInterned("Just like self.assertTrue(a > b), but with a nicer default message.");
      var1.setline(943);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._gt(var1.getlocal(2));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(944);
         var3 = PyString.fromInterned("%s not greater than %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2))}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(945);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertGreaterEqual$70(PyFrame var1, ThreadState var2) {
      var1.setline(948);
      PyString.fromInterned("Just like self.assertTrue(a >= b), but with a nicer default message.");
      var1.setline(949);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._ge(var1.getlocal(2));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(950);
         var3 = PyString.fromInterned("%s not greater than or equal to %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getglobal("safe_repr").__call__(var2, var1.getlocal(2))}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(951);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertIsNone$71(PyFrame var1, ThreadState var2) {
      var1.setline(954);
      PyString.fromInterned("Same as self.assertTrue(obj is None), with a nicer default message.");
      var1.setline(955);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(956);
         var3 = PyString.fromInterned("%s is not None")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1))}));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(957);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(2), var1.getlocal(3)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertIsNotNone$72(PyFrame var1, ThreadState var2) {
      var1.setline(960);
      PyString.fromInterned("Included for symmetry with assertIsNone.");
      var1.setline(961);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(962);
         PyString var4 = PyString.fromInterned("unexpectedly None");
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(963);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(2), var1.getlocal(3)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertIsInstance$73(PyFrame var1, ThreadState var2) {
      var1.setline(967);
      PyString.fromInterned("Same as self.assertTrue(isinstance(obj, cls)), with a nicer\n        default message.");
      var1.setline(968);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__not__().__nonzero__()) {
         var1.setline(969);
         PyObject var3 = PyString.fromInterned("%s is not an instance of %r")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getlocal(2)}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(970);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertNotIsInstance$74(PyFrame var1, ThreadState var2) {
      var1.setline(973);
      PyString.fromInterned("Included for symmetry with assertIsInstance.");
      var1.setline(974);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__nonzero__()) {
         var1.setline(975);
         PyObject var3 = PyString.fromInterned("%s is an instance of %r")._mod(new PyTuple(new PyObject[]{var1.getglobal("safe_repr").__call__(var2, var1.getlocal(1)), var1.getlocal(2)}));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(976);
         var1.getlocal(0).__getattr__("fail").__call__(var2, var1.getlocal(0).__getattr__("_formatMessage").__call__(var2, var1.getlocal(3), var1.getlocal(4)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject assertRaisesRegexp$75(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(989);
      PyString.fromInterned("Asserts that the message in a raised exception matches a regexp.\n\n        Args:\n            expected_exception: Exception class expected to be raised.\n            expected_regexp: Regexp (re pattern object or string) expected\n                    to be found in error message.\n            callable_obj: Function to be called.\n            args: Extra args.\n            kwargs: Extra kwargs.\n        ");
      var1.setline(990);
      PyObject var3 = var1.getglobal("_AssertRaisesContext").__call__(var2, var1.getlocal(1), var1.getlocal(0), var1.getlocal(2));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(991);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(992);
         var3 = var1.getlocal(6);
         var1.f_lasti = -1;
         return var3;
      } else {
         ContextManager var4;
         PyObject var5 = (var4 = ContextGuard.getManager(var1.getlocal(6))).__enter__(var2);

         label18: {
            try {
               var1.setline(994);
               var10000 = var1.getlocal(3);
               PyObject[] var8 = Py.EmptyObjects;
               String[] var6 = new String[0];
               var10000._callextra(var8, var6, var1.getlocal(4), var1.getlocal(5));
               var5 = null;
            } catch (Throwable var7) {
               if (var4.__exit__(var2, Py.setException(var7, var1))) {
                  break label18;
               }

               throw (Throwable)Py.makeException();
            }

            var4.__exit__(var2, (PyException)null);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject assertRegexpMatches$76(PyFrame var1, ThreadState var2) {
      var1.setline(997);
      PyString.fromInterned("Fail the test unless the text matches the regular expression.");
      var1.setline(998);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(999);
         var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1000);
      if (var1.getlocal(2).__getattr__("search").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(1001);
         Object var10000 = var1.getlocal(3);
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("Regexp didn't match");
         }

         Object var4 = var10000;
         var1.setlocal(3, (PyObject)var4);
         var3 = null;
         var1.setline(1002);
         var3 = PyString.fromInterned("%s: %r not found in %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(2).__getattr__("pattern"), var1.getlocal(1)}));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1003);
         throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, var1.getlocal(3)));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject assertNotRegexpMatches$77(PyFrame var1, ThreadState var2) {
      var1.setline(1006);
      PyString.fromInterned("Fail the test if the text matches the regular expression.");
      var1.setline(1007);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(1008);
         var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(1009);
      var3 = var1.getlocal(2).__getattr__("search").__call__(var2, var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1010);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(1011);
         Object var10000 = var1.getlocal(3);
         if (!((PyObject)var10000).__nonzero__()) {
            var10000 = PyString.fromInterned("Regexp matched");
         }

         Object var4 = var10000;
         var1.setlocal(3, (PyObject)var4);
         var3 = null;
         var1.setline(1012);
         var3 = PyString.fromInterned("%s: %r matches %r in %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(1).__getslice__(var1.getlocal(4).__getattr__("start").__call__(var2), var1.getlocal(4).__getattr__("end").__call__(var2), (PyObject)null), var1.getlocal(2).__getattr__("pattern"), var1.getlocal(1)}));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(1016);
         throw Py.makeException(var1.getlocal(0).__getattr__("failureException").__call__(var2, var1.getlocal(3)));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject FunctionTestCase$78(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A test case that wraps a test function.\n\n    This is useful for slipping pre-existing test functions into the\n    unittest framework. Optionally, set-up and tidy-up functions can be\n    supplied. As with TestCase, the tidy-up ('tearDown') function will\n    always be called if the set-up ('setUp') function ran successfully.\n    "));
      var1.setline(1026);
      PyString.fromInterned("A test case that wraps a test function.\n\n    This is useful for slipping pre-existing test functions into the\n    unittest framework. Optionally, set-up and tidy-up functions can be\n    supplied. As with TestCase, the tidy-up ('tearDown') function will\n    always be called if the set-up ('setUp') function ran successfully.\n    ");
      var1.setline(1028);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$79, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(1035);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setUp$80, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(1039);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$81, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(1043);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, runTest$82, (PyObject)null);
      var1.setlocal("runTest", var4);
      var3 = null;
      var1.setline(1046);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, id$83, (PyObject)null);
      var1.setlocal("id", var4);
      var3 = null;
      var1.setline(1049);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __eq__$84, (PyObject)null);
      var1.setlocal("__eq__", var4);
      var3 = null;
      var1.setline(1058);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __ne__$85, (PyObject)null);
      var1.setlocal("__ne__", var4);
      var3 = null;
      var1.setline(1061);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __hash__$86, (PyObject)null);
      var1.setlocal("__hash__", var4);
      var3 = null;
      var1.setline(1065);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$87, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(1069);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$88, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(1073);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, shortDescription$89, (PyObject)null);
      var1.setlocal("shortDescription", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$79(PyFrame var1, ThreadState var2) {
      var1.setline(1029);
      var1.getglobal("super").__call__(var2, var1.getglobal("FunctionTestCase"), var1.getlocal(0)).__getattr__("__init__").__call__(var2);
      var1.setline(1030);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_setUpFunc", var3);
      var3 = null;
      var1.setline(1031);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_tearDownFunc", var3);
      var3 = null;
      var1.setline(1032);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_testFunc", var3);
      var3 = null;
      var1.setline(1033);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_description", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setUp$80(PyFrame var1, ThreadState var2) {
      var1.setline(1036);
      PyObject var3 = var1.getlocal(0).__getattr__("_setUpFunc");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1037);
         var1.getlocal(0).__getattr__("_setUpFunc").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$81(PyFrame var1, ThreadState var2) {
      var1.setline(1040);
      PyObject var3 = var1.getlocal(0).__getattr__("_tearDownFunc");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1041);
         var1.getlocal(0).__getattr__("_tearDownFunc").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject runTest$82(PyFrame var1, ThreadState var2) {
      var1.setline(1044);
      var1.getlocal(0).__getattr__("_testFunc").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject id$83(PyFrame var1, ThreadState var2) {
      var1.setline(1047);
      PyObject var3 = var1.getlocal(0).__getattr__("_testFunc").__getattr__("__name__");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$84(PyFrame var1, ThreadState var2) {
      var1.setline(1050);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("__class__")).__not__().__nonzero__()) {
         var1.setline(1051);
         var3 = var1.getglobal("NotImplemented");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1053);
         PyObject var4 = var1.getlocal(0).__getattr__("_setUpFunc");
         PyObject var10000 = var4._eq(var1.getlocal(1).__getattr__("_setUpFunc"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var4 = var1.getlocal(0).__getattr__("_tearDownFunc");
            var10000 = var4._eq(var1.getlocal(1).__getattr__("_tearDownFunc"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var4 = var1.getlocal(0).__getattr__("_testFunc");
               var10000 = var4._eq(var1.getlocal(1).__getattr__("_testFunc"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var4 = var1.getlocal(0).__getattr__("_description");
                  var10000 = var4._eq(var1.getlocal(1).__getattr__("_description"));
                  var4 = null;
               }
            }
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __ne__$85(PyFrame var1, ThreadState var2) {
      var1.setline(1059);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      var3 = var10000.__not__();
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __hash__$86(PyFrame var1, ThreadState var2) {
      var1.setline(1062);
      PyObject var3 = var1.getglobal("hash").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("type").__call__(var2, var1.getlocal(0)), var1.getlocal(0).__getattr__("_setUpFunc"), var1.getlocal(0).__getattr__("_tearDownFunc"), var1.getlocal(0).__getattr__("_testFunc"), var1.getlocal(0).__getattr__("_description")})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$87(PyFrame var1, ThreadState var2) {
      var1.setline(1066);
      PyObject var3 = PyString.fromInterned("%s (%s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("strclass").__call__(var2, var1.getlocal(0).__getattr__("__class__")), var1.getlocal(0).__getattr__("_testFunc").__getattr__("__name__")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$88(PyFrame var1, ThreadState var2) {
      var1.setline(1070);
      PyObject var3 = PyString.fromInterned("<%s tec=%s>")._mod(new PyTuple(new PyObject[]{var1.getglobal("strclass").__call__(var2, var1.getlocal(0).__getattr__("__class__")), var1.getlocal(0).__getattr__("_testFunc")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject shortDescription$89(PyFrame var1, ThreadState var2) {
      var1.setline(1074);
      PyObject var3 = var1.getlocal(0).__getattr__("_description");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(1075);
         var3 = var1.getlocal(0).__getattr__("_description");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(1076);
         PyObject var4 = var1.getlocal(0).__getattr__("_testFunc").__getattr__("__doc__");
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(1077);
         var10000 = var1.getlocal(1);
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__getitem__(Py.newInteger(0)).__getattr__("strip").__call__(var2);
         }

         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("None");
         }

         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public case$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SkipTest$1 = Py.newCode(0, var2, var1, "SkipTest", 25, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      _ExpectedFailure$2 = Py.newCode(0, var2, var1, "_ExpectedFailure", 34, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "exc_info"};
      __init__$3 = Py.newCode(2, var2, var1, "__init__", 41, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _UnexpectedSuccess$4 = Py.newCode(0, var2, var1, "_UnexpectedSuccess", 45, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"obj"};
      _id$5 = Py.newCode(1, var2, var1, "_id", 51, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"reason", "decorator"};
      String[] var10001 = var2;
      case$py var10007 = self;
      var2 = new String[]{"reason"};
      skip$6 = Py.newCode(1, var10001, var1, "skip", 54, false, false, var10007, 6, var2, (String[])null, 0, 4097);
      var2 = new String[]{"test_item", "skip_wrapper"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"reason"};
      decorator$7 = Py.newCode(1, var10001, var1, "decorator", 58, false, false, var10007, 7, (String[])null, var2, 0, 4097);
      var2 = new String[]{"args", "kwargs"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"reason"};
      skip_wrapper$8 = Py.newCode(2, var10001, var1, "skip_wrapper", 60, true, true, var10007, 8, (String[])null, var2, 0, 4097);
      var2 = new String[]{"condition", "reason"};
      skipIf$9 = Py.newCode(2, var2, var1, "skipIf", 70, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"condition", "reason"};
      skipUnless$10 = Py.newCode(2, var2, var1, "skipUnless", 78, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func", "wrapper"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"func"};
      expectedFailure$11 = Py.newCode(1, var10001, var1, "expectedFailure", 87, false, false, var10007, 11, var2, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kwargs"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"func"};
      wrapper$12 = Py.newCode(2, var10001, var1, "wrapper", 88, true, true, var10007, 12, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      _AssertRaisesContext$13 = Py.newCode(0, var2, var1, "_AssertRaisesContext", 98, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "expected", "test_case", "expected_regexp"};
      __init__$14 = Py.newCode(4, var2, var1, "__init__", 101, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __enter__$15 = Py.newCode(1, var2, var1, "__enter__", 106, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "exc_type", "exc_value", "tb", "exc_name", "expected_regexp"};
      __exit__$16 = Py.newCode(4, var2, var1, "__exit__", 109, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestCase$17 = Py.newCode(0, var2, var1, "TestCase", 133, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "methodName", "testMethod"};
      __init__$18 = Py.newCode(2, var2, var1, "__init__", 181, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "typeobj", "function"};
      addTypeEqualityFunc$19 = Py.newCode(3, var2, var1, "addTypeEqualityFunc", 211, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "function", "args", "kwargs"};
      addCleanup$20 = Py.newCode(4, var2, var1, "addCleanup", 226, true, true, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      setUp$21 = Py.newCode(1, var2, var1, "setUp", 234, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$22 = Py.newCode(1, var2, var1, "tearDown", 238, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls"};
      setUpClass$23 = Py.newCode(1, var2, var1, "setUpClass", 242, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls"};
      tearDownClass$24 = Py.newCode(1, var2, var1, "tearDownClass", 246, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      countTestCases$25 = Py.newCode(1, var2, var1, "countTestCases", 250, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      defaultTestResult$26 = Py.newCode(1, var2, var1, "defaultTestResult", 253, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "doc"};
      shortDescription$27 = Py.newCode(1, var2, var1, "shortDescription", 256, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      id$28 = Py.newCode(1, var2, var1, "id", 267, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$29 = Py.newCode(2, var2, var1, "__eq__", 270, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$30 = Py.newCode(2, var2, var1, "__ne__", 276, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$31 = Py.newCode(1, var2, var1, "__hash__", 279, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$32 = Py.newCode(1, var2, var1, "__str__", 282, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$33 = Py.newCode(1, var2, var1, "__repr__", 285, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "reason", "addSkip"};
      _addSkip$34 = Py.newCode(3, var2, var1, "_addSkip", 289, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "orig_result", "startTestRun", "testMethod", "skip_why", "success", "e", "addExpectedFailure", "addUnexpectedSuccess", "cleanUpSuccess", "stopTestRun"};
      run$35 = Py.newCode(2, var2, var1, "run", 298, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "ok", "function", "args", "kwargs"};
      doCleanups$36 = Py.newCode(1, var2, var1, "doCleanups", 379, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kwds"};
      __call__$37 = Py.newCode(3, var2, var1, "__call__", 395, true, true, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "function", "args", "kwargs"};
      debug$38 = Py.newCode(1, var2, var1, "debug", 398, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "reason"};
      skipTest$39 = Py.newCode(2, var2, var1, "skipTest", 407, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      fail$40 = Py.newCode(2, var2, var1, "fail", 411, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "expr", "msg"};
      assertFalse$41 = Py.newCode(3, var2, var1, "assertFalse", 415, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "expr", "msg"};
      assertTrue$42 = Py.newCode(3, var2, var1, "assertTrue", 421, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "standardMsg"};
      _formatMessage$43 = Py.newCode(3, var2, var1, "_formatMessage", 427, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "excClass", "callableObj", "args", "kwargs", "context"};
      assertRaises$44 = Py.newCode(5, var2, var1, "assertRaises", 449, true, true, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "first", "second", "asserter"};
      _getAssertEqualityFunc$45 = Py.newCode(3, var2, var1, "_getAssertEqualityFunc", 478, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "first", "second", "msg", "standardMsg"};
      _baseAssertEqual$46 = Py.newCode(4, var2, var1, "_baseAssertEqual", 504, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "first", "second", "msg", "assertion_func"};
      assertEqual$47 = Py.newCode(4, var2, var1, "assertEqual", 511, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "first", "second", "msg"};
      assertNotEqual$48 = Py.newCode(4, var2, var1, "assertNotEqual", 518, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "first", "second", "places", "msg", "delta", "standardMsg"};
      assertAlmostEqual$49 = Py.newCode(6, var2, var1, "assertAlmostEqual", 528, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "first", "second", "places", "msg", "delta", "standardMsg"};
      assertNotAlmostEqual$50 = Py.newCode(6, var2, var1, "assertNotAlmostEqual", 566, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"original_func", "deprecated_func"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"original_func"};
      _deprecate$51 = Py.newCode(1, var10001, var1, "_deprecate", 610, false, false, var10007, 51, var2, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kwargs"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"original_func"};
      deprecated_func$52 = Py.newCode(2, var10001, var1, "deprecated_func", 611, true, true, var10007, 52, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "seq1", "seq2", "msg", "seq_type", "seq_type_name", "differing", "len1", "len2", "seq1_repr", "seq2_repr", "elements", "i", "item1", "item2", "standardMsg", "diffMsg"};
      assertSequenceEqual$53 = Py.newCode(5, var2, var1, "assertSequenceEqual", 626, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "message", "diff", "max_diff"};
      _truncateMessage$54 = Py.newCode(3, var2, var1, "_truncateMessage", 729, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "list1", "list2", "msg"};
      assertListEqual$55 = Py.newCode(4, var2, var1, "assertListEqual", 735, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tuple1", "tuple2", "msg"};
      assertTupleEqual$56 = Py.newCode(4, var2, var1, "assertTupleEqual", 747, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "set1", "set2", "msg", "difference1", "e", "difference2", "lines", "item", "standardMsg"};
      assertSetEqual$57 = Py.newCode(4, var2, var1, "assertSetEqual", 758, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "member", "container", "msg", "standardMsg"};
      assertIn$58 = Py.newCode(4, var2, var1, "assertIn", 801, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "member", "container", "msg", "standardMsg"};
      assertNotIn$59 = Py.newCode(4, var2, var1, "assertNotIn", 808, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "expr1", "expr2", "msg", "standardMsg"};
      assertIs$60 = Py.newCode(4, var2, var1, "assertIs", 815, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "expr1", "expr2", "msg", "standardMsg"};
      assertIsNot$61 = Py.newCode(4, var2, var1, "assertIsNot", 822, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d1", "d2", "msg", "standardMsg", "diff"};
      assertDictEqual$62 = Py.newCode(4, var2, var1, "assertDictEqual", 828, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "expected", "actual", "msg", "missing", "mismatched", "key", "value", "standardMsg", "_(857_51)"};
      assertDictContainsSubset$63 = Py.newCode(4, var2, var1, "assertDictContainsSubset", 840, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "m"};
      f$64 = Py.newCode(1, var2, var1, "<genexpr>", 857, false, false, self, 64, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "expected_seq", "actual_seq", "msg", "first_seq", "second_seq", "_msg", "first", "second", "differences", "standardMsg", "lines", "_[900_21]", "diff", "diffMsg"};
      assertItemsEqual$65 = Py.newCode(4, var2, var1, "assertItemsEqual", 866, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "first", "second", "msg", "firstlines", "secondlines", "standardMsg", "diff"};
      assertMultiLineEqual$66 = Py.newCode(4, var2, var1, "assertMultiLineEqual", 906, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "msg", "standardMsg"};
      assertLess$67 = Py.newCode(4, var2, var1, "assertLess", 929, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "msg", "standardMsg"};
      assertLessEqual$68 = Py.newCode(4, var2, var1, "assertLessEqual", 935, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "msg", "standardMsg"};
      assertGreater$69 = Py.newCode(4, var2, var1, "assertGreater", 941, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a", "b", "msg", "standardMsg"};
      assertGreaterEqual$70 = Py.newCode(4, var2, var1, "assertGreaterEqual", 947, false, false, self, 70, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "msg", "standardMsg"};
      assertIsNone$71 = Py.newCode(3, var2, var1, "assertIsNone", 953, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "msg", "standardMsg"};
      assertIsNotNone$72 = Py.newCode(3, var2, var1, "assertIsNotNone", 959, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "cls", "msg", "standardMsg"};
      assertIsInstance$73 = Py.newCode(4, var2, var1, "assertIsInstance", 965, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "cls", "msg", "standardMsg"};
      assertNotIsInstance$74 = Py.newCode(4, var2, var1, "assertNotIsInstance", 972, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "expected_exception", "expected_regexp", "callable_obj", "args", "kwargs", "context"};
      assertRaisesRegexp$75 = Py.newCode(6, var2, var1, "assertRaisesRegexp", 978, true, true, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "expected_regexp", "msg"};
      assertRegexpMatches$76 = Py.newCode(4, var2, var1, "assertRegexpMatches", 996, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "unexpected_regexp", "msg", "match"};
      assertNotRegexpMatches$77 = Py.newCode(4, var2, var1, "assertNotRegexpMatches", 1005, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FunctionTestCase$78 = Py.newCode(0, var2, var1, "FunctionTestCase", 1019, false, false, self, 78, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "testFunc", "setUp", "tearDown", "description"};
      __init__$79 = Py.newCode(5, var2, var1, "__init__", 1028, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      setUp$80 = Py.newCode(1, var2, var1, "setUp", 1035, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$81 = Py.newCode(1, var2, var1, "tearDown", 1039, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      runTest$82 = Py.newCode(1, var2, var1, "runTest", 1043, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      id$83 = Py.newCode(1, var2, var1, "id", 1046, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$84 = Py.newCode(2, var2, var1, "__eq__", 1049, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "other"};
      __ne__$85 = Py.newCode(2, var2, var1, "__ne__", 1058, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __hash__$86 = Py.newCode(1, var2, var1, "__hash__", 1061, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$87 = Py.newCode(1, var2, var1, "__str__", 1065, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$88 = Py.newCode(1, var2, var1, "__repr__", 1069, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "doc"};
      shortDescription$89 = Py.newCode(1, var2, var1, "shortDescription", 1073, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new case$py("unittest/case$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(case$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.SkipTest$1(var2, var3);
         case 2:
            return this._ExpectedFailure$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this._UnexpectedSuccess$4(var2, var3);
         case 5:
            return this._id$5(var2, var3);
         case 6:
            return this.skip$6(var2, var3);
         case 7:
            return this.decorator$7(var2, var3);
         case 8:
            return this.skip_wrapper$8(var2, var3);
         case 9:
            return this.skipIf$9(var2, var3);
         case 10:
            return this.skipUnless$10(var2, var3);
         case 11:
            return this.expectedFailure$11(var2, var3);
         case 12:
            return this.wrapper$12(var2, var3);
         case 13:
            return this._AssertRaisesContext$13(var2, var3);
         case 14:
            return this.__init__$14(var2, var3);
         case 15:
            return this.__enter__$15(var2, var3);
         case 16:
            return this.__exit__$16(var2, var3);
         case 17:
            return this.TestCase$17(var2, var3);
         case 18:
            return this.__init__$18(var2, var3);
         case 19:
            return this.addTypeEqualityFunc$19(var2, var3);
         case 20:
            return this.addCleanup$20(var2, var3);
         case 21:
            return this.setUp$21(var2, var3);
         case 22:
            return this.tearDown$22(var2, var3);
         case 23:
            return this.setUpClass$23(var2, var3);
         case 24:
            return this.tearDownClass$24(var2, var3);
         case 25:
            return this.countTestCases$25(var2, var3);
         case 26:
            return this.defaultTestResult$26(var2, var3);
         case 27:
            return this.shortDescription$27(var2, var3);
         case 28:
            return this.id$28(var2, var3);
         case 29:
            return this.__eq__$29(var2, var3);
         case 30:
            return this.__ne__$30(var2, var3);
         case 31:
            return this.__hash__$31(var2, var3);
         case 32:
            return this.__str__$32(var2, var3);
         case 33:
            return this.__repr__$33(var2, var3);
         case 34:
            return this._addSkip$34(var2, var3);
         case 35:
            return this.run$35(var2, var3);
         case 36:
            return this.doCleanups$36(var2, var3);
         case 37:
            return this.__call__$37(var2, var3);
         case 38:
            return this.debug$38(var2, var3);
         case 39:
            return this.skipTest$39(var2, var3);
         case 40:
            return this.fail$40(var2, var3);
         case 41:
            return this.assertFalse$41(var2, var3);
         case 42:
            return this.assertTrue$42(var2, var3);
         case 43:
            return this._formatMessage$43(var2, var3);
         case 44:
            return this.assertRaises$44(var2, var3);
         case 45:
            return this._getAssertEqualityFunc$45(var2, var3);
         case 46:
            return this._baseAssertEqual$46(var2, var3);
         case 47:
            return this.assertEqual$47(var2, var3);
         case 48:
            return this.assertNotEqual$48(var2, var3);
         case 49:
            return this.assertAlmostEqual$49(var2, var3);
         case 50:
            return this.assertNotAlmostEqual$50(var2, var3);
         case 51:
            return this._deprecate$51(var2, var3);
         case 52:
            return this.deprecated_func$52(var2, var3);
         case 53:
            return this.assertSequenceEqual$53(var2, var3);
         case 54:
            return this._truncateMessage$54(var2, var3);
         case 55:
            return this.assertListEqual$55(var2, var3);
         case 56:
            return this.assertTupleEqual$56(var2, var3);
         case 57:
            return this.assertSetEqual$57(var2, var3);
         case 58:
            return this.assertIn$58(var2, var3);
         case 59:
            return this.assertNotIn$59(var2, var3);
         case 60:
            return this.assertIs$60(var2, var3);
         case 61:
            return this.assertIsNot$61(var2, var3);
         case 62:
            return this.assertDictEqual$62(var2, var3);
         case 63:
            return this.assertDictContainsSubset$63(var2, var3);
         case 64:
            return this.f$64(var2, var3);
         case 65:
            return this.assertItemsEqual$65(var2, var3);
         case 66:
            return this.assertMultiLineEqual$66(var2, var3);
         case 67:
            return this.assertLess$67(var2, var3);
         case 68:
            return this.assertLessEqual$68(var2, var3);
         case 69:
            return this.assertGreater$69(var2, var3);
         case 70:
            return this.assertGreaterEqual$70(var2, var3);
         case 71:
            return this.assertIsNone$71(var2, var3);
         case 72:
            return this.assertIsNotNone$72(var2, var3);
         case 73:
            return this.assertIsInstance$73(var2, var3);
         case 74:
            return this.assertNotIsInstance$74(var2, var3);
         case 75:
            return this.assertRaisesRegexp$75(var2, var3);
         case 76:
            return this.assertRegexpMatches$76(var2, var3);
         case 77:
            return this.assertNotRegexpMatches$77(var2, var3);
         case 78:
            return this.FunctionTestCase$78(var2, var3);
         case 79:
            return this.__init__$79(var2, var3);
         case 80:
            return this.setUp$80(var2, var3);
         case 81:
            return this.tearDown$81(var2, var3);
         case 82:
            return this.runTest$82(var2, var3);
         case 83:
            return this.id$83(var2, var3);
         case 84:
            return this.__eq__$84(var2, var3);
         case 85:
            return this.__ne__$85(var2, var3);
         case 86:
            return this.__hash__$86(var2, var3);
         case 87:
            return this.__str__$87(var2, var3);
         case 88:
            return this.__repr__$88(var2, var3);
         case 89:
            return this.shortDescription$89(var2, var3);
         default:
            return null;
      }
   }
}
