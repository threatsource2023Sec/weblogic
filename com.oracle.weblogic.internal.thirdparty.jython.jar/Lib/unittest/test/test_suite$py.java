package unittest.test;

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
@Filename("unittest/test/test_suite.py")
public class test_suite$py extends PyFunctionTable implements PyRunnable {
   static test_suite$py self;
   static final PyCode f$0;
   static final PyCode Test$1;
   static final PyCode Foo$2;
   static final PyCode test_1$3;
   static final PyCode test_2$4;
   static final PyCode test_3$5;
   static final PyCode runTest$6;
   static final PyCode _mk_TestSuite$7;
   static final PyCode f$8;
   static final PyCode Test_TestSuite$9;
   static final PyCode test_init__tests_optional$10;
   static final PyCode test_init__empty_tests$11;
   static final PyCode test_init__tests_from_any_iterable$12;
   static final PyCode tests$13;
   static final PyCode f$14;
   static final PyCode f$15;
   static final PyCode test_init__TestSuite_instances_in_tests$16;
   static final PyCode tests$17;
   static final PyCode f$18;
   static final PyCode f$19;
   static final PyCode test_iter$20;
   static final PyCode f$21;
   static final PyCode f$22;
   static final PyCode test_countTestCases_zero_simple$23;
   static final PyCode test_countTestCases_zero_nested$24;
   static final PyCode Test1$25;
   static final PyCode test$26;
   static final PyCode test_countTestCases_simple$27;
   static final PyCode f$28;
   static final PyCode f$29;
   static final PyCode test_countTestCases_nested$30;
   static final PyCode Test1$31;
   static final PyCode test1$32;
   static final PyCode test2$33;
   static final PyCode f$34;
   static final PyCode f$35;
   static final PyCode test_run__empty_suite$36;
   static final PyCode test_run__requires_result$37;
   static final PyCode test_run$38;
   static final PyCode LoggingCase$39;
   static final PyCode run$40;
   static final PyCode test1$41;
   static final PyCode test2$42;
   static final PyCode test_addTest__TestCase$43;
   static final PyCode Foo$44;
   static final PyCode test$45;
   static final PyCode test_addTest__TestSuite$46;
   static final PyCode Foo$47;
   static final PyCode test$48;
   static final PyCode test_addTests$49;
   static final PyCode Foo$50;
   static final PyCode test_1$51;
   static final PyCode test_2$52;
   static final PyCode gen$53;
   static final PyCode test_addTest__noniterable$54;
   static final PyCode test_addTest__noncallable$55;
   static final PyCode test_addTest__casesuiteclass$56;
   static final PyCode test_addTests__string$57;
   static final PyCode test_function_in_suite$58;
   static final PyCode f$59;
   static final PyCode test_basetestsuite$60;
   static final PyCode Test$61;
   static final PyCode setUpClass$62;
   static final PyCode tearDownClass$63;
   static final PyCode testPass$64;
   static final PyCode testFail$65;
   static final PyCode Module$66;
   static final PyCode setUpModule$67;
   static final PyCode tearDownModule$68;
   static final PyCode test_overriding_call$69;
   static final PyCode MySuite$70;
   static final PyCode __call__$71;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(4);
      String[] var5 = new String[]{"LoggingResult", "TestEquality"};
      PyObject[] var6 = imp.importFrom("support", var5, var1, 1);
      PyObject var4 = var6[0];
      var1.setlocal("LoggingResult", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("TestEquality", var4);
      var4 = null;
      var1.setline(10);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("Test", var6, Test$1);
      var1.setlocal("Test", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(17);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, _mk_TestSuite$7, (PyObject)null);
      var1.setlocal("_mk_TestSuite", var7);
      var3 = null;
      var1.setline(23);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase"), var1.getname("TestEquality")};
      var4 = Py.makeClass("Test_TestSuite", var6, Test_TestSuite$9);
      var1.setlocal("Test_TestSuite", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(366);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(367);
         var1.getname("unittest").__getattr__("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(11);
      PyObject[] var3 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$2);
      var1.setlocal("Foo", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      return var1.getf_locals();
   }

   public PyObject Foo$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(12);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$3, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(13);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$4, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      var1.setline(14);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_3$5, (PyObject)null);
      var1.setlocal("test_3", var4);
      var3 = null;
      var1.setline(15);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, runTest$6, (PyObject)null);
      var1.setlocal("runTest", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$3(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$4(PyFrame var1, ThreadState var2) {
      var1.setline(13);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_3$5(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject runTest$6(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _mk_TestSuite$7(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("TestSuite");
      var1.setline(18);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, f$8, (PyObject)null);
      PyObject var10002 = var4.__call__(var2, var1.getlocal(0).__iter__());
      Arrays.fill(var3, (Object)null);
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$8(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(18);
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

      var1.setline(18);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(18);
         var1.setline(18);
         var6 = var1.getglobal("Test").__getattr__("Foo").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject Test_TestSuite$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(29);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("unittest").__getattr__("TestSuite").__call__(var2), var1.getname("unittest").__getattr__("TestSuite").__call__(var2)}), new PyTuple(new PyObject[]{var1.getname("unittest").__getattr__("TestSuite").__call__(var2), var1.getname("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects)))}), new PyTuple(new PyObject[]{var1.getname("_mk_TestSuite").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1")), var1.getname("_mk_TestSuite").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"))})});
      var1.setlocal("eq_pairs", var3);
      var3 = null;
      var1.setline(34);
      var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getname("unittest").__getattr__("TestSuite").__call__(var2), var1.getname("_mk_TestSuite").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"))}), new PyTuple(new PyObject[]{var1.getname("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects))), var1.getname("_mk_TestSuite").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"))}), new PyTuple(new PyObject[]{var1.getname("_mk_TestSuite").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"), (PyObject)PyString.fromInterned("test_2")), var1.getname("_mk_TestSuite").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"), (PyObject)PyString.fromInterned("test_3"))}), new PyTuple(new PyObject[]{var1.getname("_mk_TestSuite").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1")), var1.getname("_mk_TestSuite").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2"))})});
      var1.setlocal("ne_pairs", var3);
      var3 = null;
      var1.setline(48);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, test_init__tests_optional$10, (PyObject)null);
      var1.setlocal("test_init__tests_optional", var5);
      var3 = null;
      var1.setline(60);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_init__empty_tests$11, (PyObject)null);
      var1.setlocal("test_init__empty_tests", var5);
      var3 = null;
      var1.setline(71);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_init__tests_from_any_iterable$12, (PyObject)null);
      var1.setlocal("test_init__tests_from_any_iterable", var5);
      var3 = null;
      var1.setline(92);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_init__TestSuite_instances_in_tests$16, (PyObject)null);
      var1.setlocal("test_init__TestSuite_instances_in_tests", var5);
      var3 = null;
      var1.setline(105);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_iter$20, (PyObject)null);
      var1.setlocal("test_iter", var5);
      var3 = null;
      var1.setline(117);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_countTestCases_zero_simple$23, (PyObject)null);
      var1.setlocal("test_countTestCases_zero_simple", var5);
      var3 = null;
      var1.setline(128);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_countTestCases_zero_nested$24, (PyObject)null);
      var1.setlocal("test_countTestCases_zero_nested", var5);
      var3 = null;
      var1.setline(140);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_countTestCases_simple$27, (PyObject)null);
      var1.setlocal("test_countTestCases_simple", var5);
      var3 = null;
      var1.setline(152);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_countTestCases_nested$30, (PyObject)null);
      var1.setlocal("test_countTestCases_nested", var5);
      var3 = null;
      var1.setline(168);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_run__empty_suite$36, (PyObject)null);
      var1.setlocal("test_run__empty_suite", var5);
      var3 = null;
      var1.setline(180);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_run__requires_result$37, (PyObject)null);
      var1.setlocal("test_run__requires_result", var5);
      var3 = null;
      var1.setline(192);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_run$38, (PyObject)null);
      var1.setlocal("test_run", var5);
      var3 = null;
      var1.setline(210);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_addTest__TestCase$43, (PyObject)null);
      var1.setlocal("test_addTest__TestCase", var5);
      var3 = null;
      var1.setline(223);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_addTest__TestSuite$46, (PyObject)null);
      var1.setlocal("test_addTest__TestSuite", var5);
      var3 = null;
      var1.setline(240);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_addTests$49, (PyObject)null);
      var1.setlocal("test_addTests", var5);
      var3 = null;
      var1.setline(271);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_addTest__noniterable$54, (PyObject)null);
      var1.setlocal("test_addTest__noniterable", var5);
      var3 = null;
      var1.setline(281);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_addTest__noncallable$55, (PyObject)null);
      var1.setlocal("test_addTest__noncallable", var5);
      var3 = null;
      var1.setline(285);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_addTest__casesuiteclass$56, (PyObject)null);
      var1.setlocal("test_addTest__casesuiteclass", var5);
      var3 = null;
      var1.setline(290);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_addTests__string$57, (PyObject)null);
      var1.setlocal("test_addTests__string", var5);
      var3 = null;
      var1.setline(294);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_function_in_suite$58, (PyObject)null);
      var1.setlocal("test_function_in_suite", var5);
      var3 = null;
      var1.setline(305);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_basetestsuite$60, (PyObject)null);
      var1.setlocal("test_basetestsuite", var5);
      var3 = null;
      var1.setline(348);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_overriding_call$69, (PyObject)null);
      var1.setlocal("test_overriding_call", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_init__tests_optional$10(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(51);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_init__empty_tests$11(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(63);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_init__tests_from_any_iterable$12(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, tests$13, (PyObject)null);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(76);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2, var1.getlocal(1).__call__(var2));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(77);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(2));
      var1.setline(79);
      var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(80);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(2));
      var1.setline(82);
      var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2, var1.getglobal("set").__call__(var2, var1.getlocal(2)));
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(83);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tests$13(PyFrame var1, ThreadState var2) {
      Object var10000;
      Object[] var3;
      PyObject[] var4;
      PyObject var5;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(73);
            var1.setline(73);
            var5 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
            var1.setline(73);
            var4 = Py.EmptyObjects;
            var5 = var5.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$14)));
            var1.f_lasti = 1;
            var3 = new Object[4];
            var1.f_savedlocals = var3;
            return var5;
         case 1:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var5 = (PyObject)var10000;
            var1.setline(74);
            var1.setline(74);
            var5 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
            var1.setline(74);
            var4 = Py.EmptyObjects;
            var5 = var5.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$15)));
            var1.f_lasti = 2;
            var3 = new Object[4];
            var1.f_savedlocals = var3;
            return var5;
         case 2:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var5 = (PyObject)var10000;
               var1.f_lasti = -1;
               return Py.None;
            }
      }
   }

   public PyObject f$14(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$15(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_init__TestSuite_instances_in_tests$16(PyFrame var1, ThreadState var2) {
      var1.setline(93);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, tests$17, (PyObject)null);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(98);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2, var1.getlocal(1).__call__(var2));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(99);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tests$17(PyFrame var1, ThreadState var2) {
      Object var10000;
      Object[] var3;
      PyObject[] var4;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(94);
            var6 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
            var1.setline(94);
            var4 = Py.EmptyObjects;
            PyObject var5 = var6.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$18)));
            var1.setlocal(0, var5);
            var3 = null;
            var1.setline(95);
            var1.setline(95);
            var6 = var1.getglobal("unittest").__getattr__("TestSuite");
            var4 = new PyObject[]{var1.getlocal(0)};
            PyList var10002 = new PyList(var4);
            Arrays.fill(var4, (Object)null);
            var6 = var6.__call__((ThreadState)var2, (PyObject)var10002);
            var1.f_lasti = 1;
            var3 = new Object[4];
            var1.f_savedlocals = var3;
            return var6;
         case 1:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
            var1.setline(96);
            var1.setline(96);
            var6 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
            var1.setline(96);
            var4 = Py.EmptyObjects;
            var6 = var6.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$19)));
            var1.f_lasti = 2;
            var3 = new Object[4];
            var1.f_savedlocals = var3;
            return var6;
         case 2:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var6 = (PyObject)var10000;
               var1.f_lasti = -1;
               return Py.None;
            }
      }
   }

   public PyObject f$18(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$19(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_iter$20(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(106);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$21)));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(107);
      var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(107);
      var3 = Py.EmptyObjects;
      var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$22)));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(108);
      var4 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(110);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(3)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$21(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$22(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_countTestCases_zero_simple$23(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(120);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_countTestCases_zero_nested$24(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test1", var3, Test1$25);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(133);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2)})));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(135);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test1$25(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(130);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$26, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$26(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_countTestCases_simple$27(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(141);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$28)));
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(142);
      var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(142);
      var3 = Py.EmptyObjects;
      var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$29)));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(143);
      var4 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(145);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$28(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$29(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_countTestCases_nested$30(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test1", var3, Test1$31);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(157);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(157);
      var3 = Py.EmptyObjects;
      PyObject var5 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$34)));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(158);
      var10000 = var1.getglobal("unittest").__getattr__("FunctionTestCase");
      var1.setline(158);
      var3 = Py.EmptyObjects;
      var5 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$35)));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(159);
      var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test2")), var1.getlocal(2)})));
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(160);
      var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test1"))})));
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(162);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(5).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test1$31(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(154);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test1$32, (PyObject)null);
      var1.setlocal("test1", var4);
      var3 = null;
      var1.setline(155);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test2$33, (PyObject)null);
      var1.setlocal("test2", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test1$32(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test2$33(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$34(PyFrame var1, ThreadState var2) {
      var1.setline(157);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$35(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_run__empty_suite$36(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(170);
      PyObject var4 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(172);
      var4 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(174);
      var1.getlocal(3).__getattr__("run").__call__(var2, var1.getlocal(2));
      var1.setline(176);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_run__requires_result$37(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(184);
            var1.getlocal(1).__getattr__("run").__call__(var2);
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("TypeError"))) {
               var1.setline(186);
               break label19;
            }

            throw var5;
         }

         var1.setline(188);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Failed to raise TypeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_run$38(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(194);
      PyObject var5 = var1.getglobal("LoggingResult").__call__(var2, var1.getderef(0));
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(196);
      PyObject[] var6 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("LoggingCase", var6, LoggingCase$39);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(203);
      var3 = new PyList(new PyObject[]{var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test1")), var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test2"))});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(205);
      var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2, var1.getlocal(3)).__getattr__("run").__call__(var2, var1.getlocal(1));
      var1.setline(207);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("run test1"), PyString.fromInterned("run test2")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject LoggingCase$39(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(197);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = run$40;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("run", var4);
      var3 = null;
      var1.setline(200);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test1$41, (PyObject)null);
      var1.setlocal("test1", var4);
      var3 = null;
      var1.setline(201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test2$42, (PyObject)null);
      var1.setlocal("test2", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject run$40(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      var1.getderef(0).__getattr__("append").__call__(var2, PyString.fromInterned("run %s")._mod(var1.getlocal(0).__getattr__("_testMethodName")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test1$41(PyFrame var1, ThreadState var2) {
      var1.setline(200);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test2$42(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_addTest__TestCase$43(PyFrame var1, ThreadState var2) {
      var1.setline(211);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$44);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(214);
      PyObject var5 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(215);
      var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(217);
      var1.getlocal(3).__getattr__("addTest").__call__(var2, var1.getlocal(2));
      var1.setline(219);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(1));
      var1.setline(220);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(3)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$44(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(212);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$45, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$45(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_addTest__TestSuite$46(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$47);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(227);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"))})));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(229);
      var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(230);
      var1.getlocal(3).__getattr__("addTest").__call__(var2, var1.getlocal(2));
      var1.setline(232);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(1));
      var1.setline(233);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(3)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$47(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(225);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test$48, (PyObject)null);
      var1.setlocal("test", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test$48(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_addTests$49(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$50);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(245);
      PyObject var5 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"));
      var1.setderef(2, var5);
      var3 = null;
      var1.setline(246);
      var5 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_2"));
      var1.setderef(1, var5);
      var3 = null;
      var1.setline(247);
      var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getderef(1)})));
      var1.setderef(0, var5);
      var3 = null;
      var1.setline(249);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = gen$53;
      var3 = new PyObject[]{var1.getclosure(2), var1.getclosure(1), var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(254);
      var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(255);
      var1.getlocal(3).__getattr__("addTests").__call__(var2, var1.getlocal(2).__call__(var2));
      var1.setline(257);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(3)), var1.getglobal("list").__call__(var2, var1.getlocal(2).__call__(var2)));
      var1.setline(261);
      var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(262);
      var5 = var1.getlocal(2).__call__(var2).__iter__();

      while(true) {
         var1.setline(262);
         var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(265);
            var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3), var1.getlocal(4));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(263);
         var1.getlocal(4).__getattr__("addTest").__call__(var2, var1.getlocal(5));
      }
   }

   public PyObject Foo$50(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(242);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$51, (PyObject)null);
      var1.setlocal("test_1", var4);
      var3 = null;
      var1.setline(243);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_2$52, (PyObject)null);
      var1.setlocal("test_2", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$51(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_2$52(PyFrame var1, ThreadState var2) {
      var1.setline(243);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject gen$53(PyFrame var1, ThreadState var2) {
      Object var10000;
      Object[] var3;
      PyObject var4;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(250);
            var1.setline(250);
            var4 = var1.getderef(0);
            var1.f_lasti = 1;
            var3 = new Object[3];
            var1.f_savedlocals = var3;
            return var4;
         case 1:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var4 = (PyObject)var10000;
            var1.setline(251);
            var1.setline(251);
            var4 = var1.getderef(1);
            var1.f_lasti = 2;
            var3 = new Object[4];
            var1.f_savedlocals = var3;
            return var4;
         case 2:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var4 = (PyObject)var10000;
            var1.setline(252);
            var1.setline(252);
            var4 = var1.getderef(2);
            var1.f_lasti = 3;
            var3 = new Object[4];
            var1.f_savedlocals = var3;
            return var4;
         case 3:
            var3 = var1.f_savedlocals;
            var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            } else {
               var4 = (PyObject)var10000;
               var1.f_lasti = -1;
               return Py.None;
            }
      }
   }

   public PyObject test_addTest__noniterable$54(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      label19: {
         try {
            var1.setline(275);
            var1.getlocal(1).__getattr__("addTests").__call__((ThreadState)var2, (PyObject)Py.newInteger(5));
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("TypeError"))) {
               var1.setline(277);
               break label19;
            }

            throw var5;
         }

         var1.setline(279);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Failed to raise TypeError"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_addTest__noncallable$55(PyFrame var1, ThreadState var2) {
      var1.setline(282);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(283);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("TypeError"), (PyObject)var1.getlocal(1).__getattr__("addTest"), (PyObject)Py.newInteger(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_addTest__casesuiteclass$56(PyFrame var1, ThreadState var2) {
      var1.setline(286);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(287);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("TypeError"), var1.getlocal(1).__getattr__("addTest"), var1.getglobal("Test_TestSuite"));
      var1.setline(288);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("TypeError"), var1.getlocal(1).__getattr__("addTest"), var1.getglobal("unittest").__getattr__("TestSuite"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_addTests__string$57(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(292);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("TypeError"), (PyObject)var1.getlocal(1).__getattr__("addTests"), (PyObject)PyString.fromInterned("foo"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_function_in_suite$58(PyFrame var1, ThreadState var2) {
      var1.setline(295);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, f$59, (PyObject)null);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(297);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(298);
      var1.getlocal(2).__getattr__("addTest").__call__(var2, var1.getlocal(1));
      var1.setline(301);
      var1.getlocal(2).__getattr__("run").__call__(var2, var1.getglobal("unittest").__getattr__("TestResult").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$59(PyFrame var1, ThreadState var2) {
      var1.setline(296);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_basetestsuite$60(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$61);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(319);
      var3 = new PyObject[]{var1.getglobal("object")};
      var4 = Py.makeClass("Module", var3, Module$66);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(329);
      PyString var5 = PyString.fromInterned("Module");
      var1.getlocal(1).__setattr__((String)"__module__", var5);
      var3 = null;
      var1.setline(330);
      PyObject var6 = var1.getderef(0);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("Module"), var6);
      var3 = null;
      var1.setline(331);
      var1.getlocal(0).__getattr__("addCleanup").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("modules").__getattr__("pop"), (PyObject)PyString.fromInterned("Module"));
      var1.setline(333);
      var6 = var1.getglobal("unittest").__getattr__("BaseTestSuite").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(334);
      var1.getlocal(2).__getattr__("addTests").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testPass")), var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testFail"))})));
      var1.setline(335);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(2));
      var1.setline(337);
      var6 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(338);
      var1.getlocal(2).__getattr__("run").__call__(var2, var1.getlocal(3));
      var1.setline(339);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getderef(0).__getattr__("wasSetUp"));
      var1.setline(340);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getderef(0).__getattr__("wasTornDown"));
      var1.setline(341);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(1).__getattr__("wasSetUp"));
      var1.setline(342);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(1).__getattr__("wasTornDown"));
      var1.setline(343);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("errors")), (PyObject)Py.newInteger(1));
      var1.setline(344);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("failures")), (PyObject)Py.newInteger(0));
      var1.setline(345);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("testsRun"), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$61(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(307);
      PyObject var3 = var1.getname("False");
      var1.setlocal("wasSetUp", var3);
      var3 = null;
      var1.setline(308);
      var3 = var1.getname("False");
      var1.setlocal("wasTornDown", var3);
      var3 = null;
      var1.setline(309);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, setUpClass$62, (PyObject)null);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("setUpClass", var3);
      var3 = null;
      var1.setline(312);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tearDownClass$63, (PyObject)null);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownClass", var3);
      var3 = null;
      var1.setline(315);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testPass$64, (PyObject)null);
      var1.setlocal("testPass", var5);
      var3 = null;
      var1.setline(317);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testFail$65, (PyObject)null);
      var1.setlocal("testFail", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$62(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("wasSetUp", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownClass$63(PyFrame var1, ThreadState var2) {
      var1.setline(314);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("wasTornDown", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testPass$64(PyFrame var1, ThreadState var2) {
      var1.setline(316);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testFail$65(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      var1.getglobal("fail");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module$66(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(320);
      PyObject var3 = var1.getname("False");
      var1.setlocal("wasSetUp", var3);
      var3 = null;
      var1.setline(321);
      var3 = var1.getname("False");
      var1.setlocal("wasTornDown", var3);
      var3 = null;
      var1.setline(322);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = setUpModule$67;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var3 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("setUpModule", var3);
      var3 = null;
      var1.setline(325);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = tearDownModule$68;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var3 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownModule", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpModule$67(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("wasSetUp", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownModule$68(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("wasTornDown", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_overriding_call$69(PyFrame var1, ThreadState var2) {
      var1.setline(349);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestSuite")};
      PyObject var4 = Py.makeClass("MySuite", var3, MySuite$70);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(355);
      PyObject var5 = var1.getlocal(1).__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(356);
      var5 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(357);
      var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(358);
      var1.getlocal(4).__getattr__("addTest").__call__(var2, var1.getlocal(2));
      var1.setline(359);
      var1.getlocal(4).__call__(var2, var1.getlocal(3));
      var1.setline(360);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(2).__getattr__("called"));
      var1.setline(363);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(3).__getattr__("_testRunEntered"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject MySuite$70(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(350);
      PyObject var3 = var1.getname("False");
      var1.setlocal("called", var3);
      var3 = null;
      var1.setline(351);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __call__$71, (PyObject)null);
      var1.setlocal("__call__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __call__$71(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("called", var3);
      var3 = null;
      var1.setline(353);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("TestSuite").__getattr__("__call__");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public test_suite$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Test$1 = Py.newCode(0, var2, var1, "Test", 10, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Foo$2 = Py.newCode(0, var2, var1, "Foo", 11, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$3 = Py.newCode(1, var2, var1, "test_1", 12, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$4 = Py.newCode(1, var2, var1, "test_2", 13, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_3$5 = Py.newCode(1, var2, var1, "test_3", 14, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      runTest$6 = Py.newCode(1, var2, var1, "runTest", 15, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"names", "_(18_30)"};
      _mk_TestSuite$7 = Py.newCode(1, var2, var1, "_mk_TestSuite", 17, true, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "n"};
      f$8 = Py.newCode(1, var2, var1, "<genexpr>", 18, false, false, self, 8, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      Test_TestSuite$9 = Py.newCode(0, var2, var1, "Test_TestSuite", 23, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "suite"};
      test_init__tests_optional$10 = Py.newCode(1, var2, var1, "test_init__tests_optional", 48, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "suite"};
      test_init__empty_tests$11 = Py.newCode(1, var2, var1, "test_init__empty_tests", 60, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tests", "suite_1", "suite_2", "suite_3"};
      test_init__tests_from_any_iterable$12 = Py.newCode(1, var2, var1, "test_init__tests_from_any_iterable", 71, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      tests$13 = Py.newCode(0, var2, var1, "tests", 72, false, false, self, 13, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      f$14 = Py.newCode(0, var2, var1, "<lambda>", 73, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$15 = Py.newCode(0, var2, var1, "<lambda>", 74, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tests", "suite"};
      test_init__TestSuite_instances_in_tests$16 = Py.newCode(1, var2, var1, "test_init__TestSuite_instances_in_tests", 92, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"ftc"};
      tests$17 = Py.newCode(0, var2, var1, "tests", 93, false, false, self, 17, (String[])null, (String[])null, 0, 4129);
      var2 = new String[0];
      f$18 = Py.newCode(0, var2, var1, "<lambda>", 94, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$19 = Py.newCode(0, var2, var1, "<lambda>", 96, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test1", "test2", "suite"};
      test_iter$20 = Py.newCode(1, var2, var1, "test_iter", 105, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$21 = Py.newCode(0, var2, var1, "<lambda>", 106, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$22 = Py.newCode(0, var2, var1, "<lambda>", 107, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "suite"};
      test_countTestCases_zero_simple$23 = Py.newCode(1, var2, var1, "test_countTestCases_zero_simple", 117, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test1", "suite"};
      test_countTestCases_zero_nested$24 = Py.newCode(1, var2, var1, "test_countTestCases_zero_nested", 128, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test1$25 = Py.newCode(0, var2, var1, "Test1", 129, false, false, self, 25, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$26 = Py.newCode(1, var2, var1, "test", 130, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test1", "test2", "suite"};
      test_countTestCases_simple$27 = Py.newCode(1, var2, var1, "test_countTestCases_simple", 140, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$28 = Py.newCode(0, var2, var1, "<lambda>", 141, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$29 = Py.newCode(0, var2, var1, "<lambda>", 142, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test1", "test2", "test3", "child", "parent"};
      test_countTestCases_nested$30 = Py.newCode(1, var2, var1, "test_countTestCases_nested", 152, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test1$31 = Py.newCode(0, var2, var1, "Test1", 153, false, false, self, 31, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test1$32 = Py.newCode(1, var2, var1, "test1", 154, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test2$33 = Py.newCode(1, var2, var1, "test2", 155, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$34 = Py.newCode(0, var2, var1, "<lambda>", 157, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$35 = Py.newCode(0, var2, var1, "<lambda>", 158, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "events", "result", "suite"};
      test_run__empty_suite$36 = Py.newCode(1, var2, var1, "test_run__empty_suite", 168, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "suite"};
      test_run__requires_result$37 = Py.newCode(1, var2, var1, "test_run__requires_result", 180, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "LoggingCase", "tests", "events"};
      String[] var10001 = var2;
      test_suite$py var10007 = self;
      var2 = new String[]{"events"};
      test_run$38 = Py.newCode(1, var10001, var1, "test_run", 192, false, false, var10007, 38, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      LoggingCase$39 = Py.newCode(0, var2, var1, "LoggingCase", 196, false, false, self, 39, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "result"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"events"};
      run$40 = Py.newCode(2, var10001, var1, "run", 197, false, false, var10007, 40, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      test1$41 = Py.newCode(1, var2, var1, "test1", 200, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test2$42 = Py.newCode(1, var2, var1, "test2", 201, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "test", "suite"};
      test_addTest__TestCase$43 = Py.newCode(1, var2, var1, "test_addTest__TestCase", 210, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$44 = Py.newCode(0, var2, var1, "Foo", 211, false, false, self, 44, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$45 = Py.newCode(1, var2, var1, "test", 212, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "suite_2", "suite"};
      test_addTest__TestSuite$46 = Py.newCode(1, var2, var1, "test_addTest__TestSuite", 223, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$47 = Py.newCode(0, var2, var1, "Foo", 224, false, false, self, 47, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test$48 = Py.newCode(1, var2, var1, "test", 225, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "gen", "suite_1", "suite_2", "t", "inner_suite", "test_2", "test_1"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"inner_suite", "test_2", "test_1"};
      test_addTests$49 = Py.newCode(1, var10001, var1, "test_addTests", 240, false, false, var10007, 49, var2, (String[])null, 3, 4097);
      var2 = new String[0];
      Foo$50 = Py.newCode(0, var2, var1, "Foo", 241, false, false, self, 50, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_1$51 = Py.newCode(1, var2, var1, "test_1", 242, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_2$52 = Py.newCode(1, var2, var1, "test_2", 243, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"test_1", "test_2", "inner_suite"};
      gen$53 = Py.newCode(0, var10001, var1, "gen", 249, false, false, var10007, 53, (String[])null, var2, 0, 4129);
      var2 = new String[]{"self", "suite"};
      test_addTest__noniterable$54 = Py.newCode(1, var2, var1, "test_addTest__noniterable", 271, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "suite"};
      test_addTest__noncallable$55 = Py.newCode(1, var2, var1, "test_addTest__noncallable", 281, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "suite"};
      test_addTest__casesuiteclass$56 = Py.newCode(1, var2, var1, "test_addTest__casesuiteclass", 285, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "suite"};
      test_addTests__string$57 = Py.newCode(1, var2, var1, "test_addTests__string", 290, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "suite"};
      test_function_in_suite$58 = Py.newCode(1, var2, var1, "test_function_in_suite", 294, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_"};
      f$59 = Py.newCode(1, var2, var1, "f", 295, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test", "suite", "result", "Module"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module"};
      test_basetestsuite$60 = Py.newCode(1, var10001, var1, "test_basetestsuite", 305, false, false, var10007, 60, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Test$61 = Py.newCode(0, var2, var1, "Test", 306, false, false, self, 61, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      setUpClass$62 = Py.newCode(1, var2, var1, "setUpClass", 309, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls"};
      tearDownClass$63 = Py.newCode(1, var2, var1, "tearDownClass", 312, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testPass$64 = Py.newCode(1, var2, var1, "testPass", 315, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testFail$65 = Py.newCode(1, var2, var1, "testFail", 317, false, false, self, 65, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Module$66 = Py.newCode(0, var2, var1, "Module", 319, false, false, self, 66, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module"};
      setUpModule$67 = Py.newCode(0, var10001, var1, "setUpModule", 322, false, false, var10007, 67, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module"};
      tearDownModule$68 = Py.newCode(0, var10001, var1, "tearDownModule", 325, false, false, var10007, 68, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "MySuite", "suite", "result", "wrapper"};
      test_overriding_call$69 = Py.newCode(1, var2, var1, "test_overriding_call", 348, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      MySuite$70 = Py.newCode(0, var2, var1, "MySuite", 349, false, false, self, 70, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "kw"};
      __call__$71 = Py.newCode(3, var2, var1, "__call__", 351, true, true, self, 71, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_suite$py("unittest/test/test_suite$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_suite$py.class);
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
            return this.test_1$3(var2, var3);
         case 4:
            return this.test_2$4(var2, var3);
         case 5:
            return this.test_3$5(var2, var3);
         case 6:
            return this.runTest$6(var2, var3);
         case 7:
            return this._mk_TestSuite$7(var2, var3);
         case 8:
            return this.f$8(var2, var3);
         case 9:
            return this.Test_TestSuite$9(var2, var3);
         case 10:
            return this.test_init__tests_optional$10(var2, var3);
         case 11:
            return this.test_init__empty_tests$11(var2, var3);
         case 12:
            return this.test_init__tests_from_any_iterable$12(var2, var3);
         case 13:
            return this.tests$13(var2, var3);
         case 14:
            return this.f$14(var2, var3);
         case 15:
            return this.f$15(var2, var3);
         case 16:
            return this.test_init__TestSuite_instances_in_tests$16(var2, var3);
         case 17:
            return this.tests$17(var2, var3);
         case 18:
            return this.f$18(var2, var3);
         case 19:
            return this.f$19(var2, var3);
         case 20:
            return this.test_iter$20(var2, var3);
         case 21:
            return this.f$21(var2, var3);
         case 22:
            return this.f$22(var2, var3);
         case 23:
            return this.test_countTestCases_zero_simple$23(var2, var3);
         case 24:
            return this.test_countTestCases_zero_nested$24(var2, var3);
         case 25:
            return this.Test1$25(var2, var3);
         case 26:
            return this.test$26(var2, var3);
         case 27:
            return this.test_countTestCases_simple$27(var2, var3);
         case 28:
            return this.f$28(var2, var3);
         case 29:
            return this.f$29(var2, var3);
         case 30:
            return this.test_countTestCases_nested$30(var2, var3);
         case 31:
            return this.Test1$31(var2, var3);
         case 32:
            return this.test1$32(var2, var3);
         case 33:
            return this.test2$33(var2, var3);
         case 34:
            return this.f$34(var2, var3);
         case 35:
            return this.f$35(var2, var3);
         case 36:
            return this.test_run__empty_suite$36(var2, var3);
         case 37:
            return this.test_run__requires_result$37(var2, var3);
         case 38:
            return this.test_run$38(var2, var3);
         case 39:
            return this.LoggingCase$39(var2, var3);
         case 40:
            return this.run$40(var2, var3);
         case 41:
            return this.test1$41(var2, var3);
         case 42:
            return this.test2$42(var2, var3);
         case 43:
            return this.test_addTest__TestCase$43(var2, var3);
         case 44:
            return this.Foo$44(var2, var3);
         case 45:
            return this.test$45(var2, var3);
         case 46:
            return this.test_addTest__TestSuite$46(var2, var3);
         case 47:
            return this.Foo$47(var2, var3);
         case 48:
            return this.test$48(var2, var3);
         case 49:
            return this.test_addTests$49(var2, var3);
         case 50:
            return this.Foo$50(var2, var3);
         case 51:
            return this.test_1$51(var2, var3);
         case 52:
            return this.test_2$52(var2, var3);
         case 53:
            return this.gen$53(var2, var3);
         case 54:
            return this.test_addTest__noniterable$54(var2, var3);
         case 55:
            return this.test_addTest__noncallable$55(var2, var3);
         case 56:
            return this.test_addTest__casesuiteclass$56(var2, var3);
         case 57:
            return this.test_addTests__string$57(var2, var3);
         case 58:
            return this.test_function_in_suite$58(var2, var3);
         case 59:
            return this.f$59(var2, var3);
         case 60:
            return this.test_basetestsuite$60(var2, var3);
         case 61:
            return this.Test$61(var2, var3);
         case 62:
            return this.setUpClass$62(var2, var3);
         case 63:
            return this.tearDownClass$63(var2, var3);
         case 64:
            return this.testPass$64(var2, var3);
         case 65:
            return this.testFail$65(var2, var3);
         case 66:
            return this.Module$66(var2, var3);
         case 67:
            return this.setUpModule$67(var2, var3);
         case 68:
            return this.tearDownModule$68(var2, var3);
         case 69:
            return this.test_overriding_call$69(var2, var3);
         case 70:
            return this.MySuite$70(var2, var3);
         case 71:
            return this.__call__$71(var2, var3);
         default:
            return null;
      }
   }
}
