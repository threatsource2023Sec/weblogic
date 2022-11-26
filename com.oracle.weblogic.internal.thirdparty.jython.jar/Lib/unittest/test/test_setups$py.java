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
@Filename("unittest/test/test_setups.py")
public class test_setups$py extends PyFunctionTable implements PyRunnable {
   static test_setups$py self;
   static final PyCode f$0;
   static final PyCode resultFactory$1;
   static final PyCode TestSetups$2;
   static final PyCode getRunner$3;
   static final PyCode runTests$4;
   static final PyCode test_setup_class$5;
   static final PyCode Test$6;
   static final PyCode setUpClass$7;
   static final PyCode test_one$8;
   static final PyCode test_two$9;
   static final PyCode test_teardown_class$10;
   static final PyCode Test$11;
   static final PyCode tearDownClass$12;
   static final PyCode test_one$13;
   static final PyCode test_two$14;
   static final PyCode test_teardown_class_two_classes$15;
   static final PyCode Test$16;
   static final PyCode tearDownClass$17;
   static final PyCode test_one$18;
   static final PyCode test_two$19;
   static final PyCode Test2$20;
   static final PyCode tearDownClass$21;
   static final PyCode test_one$22;
   static final PyCode test_two$23;
   static final PyCode test_error_in_setupclass$24;
   static final PyCode BrokenTest$25;
   static final PyCode setUpClass$26;
   static final PyCode test_one$27;
   static final PyCode test_two$28;
   static final PyCode test_error_in_teardown_class$29;
   static final PyCode Test$30;
   static final PyCode tearDownClass$31;
   static final PyCode test_one$32;
   static final PyCode test_two$33;
   static final PyCode Test2$34;
   static final PyCode tearDownClass$35;
   static final PyCode test_one$36;
   static final PyCode test_two$37;
   static final PyCode test_class_not_torndown_when_setup_fails$38;
   static final PyCode Test$39;
   static final PyCode setUpClass$40;
   static final PyCode tearDownClass$41;
   static final PyCode test_one$42;
   static final PyCode test_class_not_setup_or_torndown_when_skipped$43;
   static final PyCode Test$44;
   static final PyCode setUpClass$45;
   static final PyCode tearDownClass$46;
   static final PyCode test_one$47;
   static final PyCode test_setup_teardown_order_with_pathological_suite$48;
   static final PyCode Module1$49;
   static final PyCode setUpModule$50;
   static final PyCode tearDownModule$51;
   static final PyCode Module2$52;
   static final PyCode setUpModule$53;
   static final PyCode tearDownModule$54;
   static final PyCode Test1$55;
   static final PyCode setUpClass$56;
   static final PyCode tearDownClass$57;
   static final PyCode testOne$58;
   static final PyCode testTwo$59;
   static final PyCode Test2$60;
   static final PyCode setUpClass$61;
   static final PyCode tearDownClass$62;
   static final PyCode testOne$63;
   static final PyCode testTwo$64;
   static final PyCode Test3$65;
   static final PyCode setUpClass$66;
   static final PyCode tearDownClass$67;
   static final PyCode testOne$68;
   static final PyCode testTwo$69;
   static final PyCode test_setup_module$70;
   static final PyCode Module$71;
   static final PyCode setUpModule$72;
   static final PyCode Test$73;
   static final PyCode test_one$74;
   static final PyCode test_two$75;
   static final PyCode test_error_in_setup_module$76;
   static final PyCode Module$77;
   static final PyCode setUpModule$78;
   static final PyCode tearDownModule$79;
   static final PyCode Test$80;
   static final PyCode setUpClass$81;
   static final PyCode tearDownClass$82;
   static final PyCode test_one$83;
   static final PyCode test_two$84;
   static final PyCode Test2$85;
   static final PyCode test_one$86;
   static final PyCode test_two$87;
   static final PyCode test_testcase_with_missing_module$88;
   static final PyCode Test$89;
   static final PyCode test_one$90;
   static final PyCode test_two$91;
   static final PyCode test_teardown_module$92;
   static final PyCode Module$93;
   static final PyCode tearDownModule$94;
   static final PyCode Test$95;
   static final PyCode test_one$96;
   static final PyCode test_two$97;
   static final PyCode test_error_in_teardown_module$98;
   static final PyCode Module$99;
   static final PyCode tearDownModule$100;
   static final PyCode Test$101;
   static final PyCode setUpClass$102;
   static final PyCode tearDownClass$103;
   static final PyCode test_one$104;
   static final PyCode test_two$105;
   static final PyCode Test2$106;
   static final PyCode test_one$107;
   static final PyCode test_two$108;
   static final PyCode test_skiptest_in_setupclass$109;
   static final PyCode Test$110;
   static final PyCode setUpClass$111;
   static final PyCode test_one$112;
   static final PyCode test_two$113;
   static final PyCode test_skiptest_in_setupmodule$114;
   static final PyCode Test$115;
   static final PyCode test_one$116;
   static final PyCode test_two$117;
   static final PyCode Module$118;
   static final PyCode setUpModule$119;
   static final PyCode test_suite_debug_executes_setups_and_teardowns$120;
   static final PyCode Module$121;
   static final PyCode setUpModule$122;
   static final PyCode tearDownModule$123;
   static final PyCode Test$124;
   static final PyCode setUpClass$125;
   static final PyCode tearDownClass$126;
   static final PyCode test_something$127;
   static final PyCode test_suite_debug_propagates_exceptions$128;
   static final PyCode Module$129;
   static final PyCode setUpModule$130;
   static final PyCode tearDownModule$131;
   static final PyCode Test$132;
   static final PyCode setUpClass$133;
   static final PyCode tearDownClass$134;
   static final PyCode test_something$135;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(3);
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("cStringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(5);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(8);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, resultFactory$1, (PyObject)null);
      var1.setlocal("resultFactory", var7);
      var3 = null;
      var1.setline(12);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestSetups", var6, TestSetups$2);
      var1.setlocal("TestSetups", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(507);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(508);
         var1.getname("unittest").__getattr__("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject resultFactory$1(PyFrame var1, ThreadState var2) {
      var1.setline(9);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TestSetups$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(14);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, getRunner$3, (PyObject)null);
      var1.setlocal("getRunner", var4);
      var3 = null;
      var1.setline(17);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, runTests$4, (PyObject)null);
      var1.setlocal("runTests", var4);
      var3 = null;
      var1.setline(33);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_setup_class$5, (PyObject)null);
      var1.setlocal("test_setup_class", var4);
      var3 = null;
      var1.setline(51);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_teardown_class$10, (PyObject)null);
      var1.setlocal("test_teardown_class", var4);
      var3 = null;
      var1.setline(69);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_teardown_class_two_classes$15, (PyObject)null);
      var1.setlocal("test_teardown_class_two_classes", var4);
      var3 = null;
      var1.setline(99);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_error_in_setupclass$24, (PyObject)null);
      var1.setlocal("test_error_in_setupclass", var4);
      var3 = null;
      var1.setline(117);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_error_in_teardown_class$29, (PyObject)null);
      var1.setlocal("test_error_in_teardown_class", var4);
      var3 = null;
      var1.setline(150);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_class_not_torndown_when_setup_fails$38, (PyObject)null);
      var1.setlocal("test_class_not_torndown_when_setup_fails", var4);
      var3 = null;
      var1.setline(166);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_class_not_setup_or_torndown_when_skipped$43, (PyObject)null);
      var1.setlocal("test_class_not_setup_or_torndown_when_skipped", var4);
      var3 = null;
      var1.setline(184);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_setup_teardown_order_with_pathological_suite$48, (PyObject)null);
      var1.setlocal("test_setup_teardown_order_with_pathological_suite", var4);
      var3 = null;
      var1.setline(266);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_setup_module$70, (PyObject)null);
      var1.setlocal("test_setup_module", var4);
      var3 = null;
      var1.setline(286);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_error_in_setup_module$76, (PyObject)null);
      var1.setlocal("test_error_in_setup_module", var4);
      var3 = null;
      var1.setline(331);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_testcase_with_missing_module$88, (PyObject)null);
      var1.setlocal("test_testcase_with_missing_module", var4);
      var3 = null;
      var1.setline(343);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_teardown_module$92, (PyObject)null);
      var1.setlocal("test_teardown_module", var4);
      var3 = null;
      var1.setline(363);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_error_in_teardown_module$98, (PyObject)null);
      var1.setlocal("test_error_in_teardown_module", var4);
      var3 = null;
      var1.setline(403);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_skiptest_in_setupclass$109, (PyObject)null);
      var1.setlocal("test_skiptest_in_setupclass", var4);
      var3 = null;
      var1.setline(420);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_skiptest_in_setupmodule$114, (PyObject)null);
      var1.setlocal("test_skiptest_in_setupmodule", var4);
      var3 = null;
      var1.setline(442);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_suite_debug_executes_setups_and_teardowns$120, (PyObject)null);
      var1.setlocal("test_suite_debug_executes_setups_and_teardowns", var4);
      var3 = null;
      var1.setline(471);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_suite_debug_propagates_exceptions$128, (PyObject)null);
      var1.setlocal("test_suite_debug_propagates_exceptions", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject getRunner$3(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("TextTestRunner");
      PyObject[] var3 = new PyObject[]{var1.getglobal("resultFactory"), var1.getglobal("StringIO").__call__(var2)};
      String[] var4 = new String[]{"resultclass", "stream"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject runTests$4(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(19);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(23);
            var3 = var1.getlocal(0).__getattr__("getRunner").__call__(var2);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(26);
            var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(27);
            var1.getlocal(6).__getattr__("addTest").__call__(var2, var1.getlocal(2));
            var1.setline(29);
            var1.getlocal(2).__getattr__("addTest").__call__(var2, var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2));
            var1.setline(30);
            var1.getlocal(6).__getattr__("addTest").__call__(var2, var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2));
            var1.setline(31);
            var3 = var1.getlocal(5).__getattr__("run").__call__(var2, var1.getlocal(6));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(20);
         PyObject var5 = var1.getglobal("unittest").__getattr__("defaultTestLoader").__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(21);
         var1.getlocal(2).__getattr__("addTests").__call__(var2, var1.getlocal(4));
      }
   }

   public PyObject test_setup_class$5(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$6);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(45);
      PyObject var5 = var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getderef(0));
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(47);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("setUpCalled"), (PyObject)Py.newInteger(1));
      var1.setline(48);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("testsRun"), (PyObject)Py.newInteger(2));
      var1.setline(49);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(35);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("setUpCalled", var3);
      var3 = null;
      var1.setline(36);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = setUpClass$7;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      PyObject var6 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("setUpClass", var6);
      var3 = null;
      var1.setline(40);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_one$8, (PyObject)null);
      var1.setlocal("test_one", var5);
      var3 = null;
      var1.setline(42);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_two$9, (PyObject)null);
      var1.setlocal("test_two", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$7(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var10000 = var1.getderef(0);
      String var3 = "setUpCalled";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(39);
      var1.getglobal("unittest").__getattr__("TestCase").__getattr__("setUpClass").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_one$8(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$9(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_teardown_class$10(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$11);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(63);
      PyObject var5 = var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getderef(0));
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(65);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("tearDownCalled"), (PyObject)Py.newInteger(1));
      var1.setline(66);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("testsRun"), (PyObject)Py.newInteger(2));
      var1.setline(67);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$11(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(53);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("tearDownCalled", var3);
      var3 = null;
      var1.setline(54);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = tearDownClass$12;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      PyObject var6 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownClass", var6);
      var3 = null;
      var1.setline(58);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_one$13, (PyObject)null);
      var1.setlocal("test_one", var5);
      var3 = null;
      var1.setline(60);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_two$14, (PyObject)null);
      var1.setlocal("test_two", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tearDownClass$12(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var10000 = var1.getderef(0);
      String var3 = "tearDownCalled";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(57);
      var1.getglobal("unittest").__getattr__("TestCase").__getattr__("tearDownClass").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_one$13(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$14(PyFrame var1, ThreadState var2) {
      var1.setline(61);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_teardown_class_two_classes$15(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$16);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(81);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test2", var3, Test2$20);
      var1.setderef(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(92);
      PyObject var5 = var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getderef(0), var1.getderef(1));
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(94);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("tearDownCalled"), (PyObject)Py.newInteger(1));
      var1.setline(95);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(1).__getattr__("tearDownCalled"), (PyObject)Py.newInteger(1));
      var1.setline(96);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("testsRun"), (PyObject)Py.newInteger(4));
      var1.setline(97);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(71);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("tearDownCalled", var3);
      var3 = null;
      var1.setline(72);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = tearDownClass$17;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      PyObject var6 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownClass", var6);
      var3 = null;
      var1.setline(76);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_one$18, (PyObject)null);
      var1.setlocal("test_one", var5);
      var3 = null;
      var1.setline(78);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_two$19, (PyObject)null);
      var1.setlocal("test_two", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tearDownClass$17(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var10000 = var1.getderef(0);
      String var3 = "tearDownCalled";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(75);
      var1.getglobal("unittest").__getattr__("TestCase").__getattr__("tearDownClass").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_one$18(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$19(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test2$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(82);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("tearDownCalled", var3);
      var3 = null;
      var1.setline(83);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = tearDownClass$21;
      var4 = new PyObject[]{var1.f_back.getclosure(1)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      PyObject var6 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownClass", var6);
      var3 = null;
      var1.setline(87);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_one$22, (PyObject)null);
      var1.setlocal("test_one", var5);
      var3 = null;
      var1.setline(89);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_two$23, (PyObject)null);
      var1.setlocal("test_two", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tearDownClass$21(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyObject var10000 = var1.getderef(0);
      String var3 = "tearDownCalled";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(86);
      var1.getglobal("unittest").__getattr__("TestCase").__getattr__("tearDownClass").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_one$22(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$23(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_error_in_setupclass$24(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("BrokenTest", var3, BrokenTest$25);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(109);
      PyObject var6 = var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(111);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("testsRun"), (PyObject)Py.newInteger(0));
      var1.setline(112);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("errors")), (PyObject)Py.newInteger(1));
      var1.setline(113);
      var6 = var1.getlocal(2).__getattr__("errors").__getitem__(Py.newInteger(0));
      PyObject[] var7 = Py.unpackSequence(var6, 2);
      PyObject var5 = var7[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(114);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(3)), PyString.fromInterned("setUpClass (%s.BrokenTest)")._mod(var1.getglobal("__name__")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BrokenTest$25(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(101);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUpClass$26, (PyObject)null);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpClass", var5);
      var3 = null;
      var1.setline(104);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_one$27, (PyObject)null);
      var1.setlocal("test_one", var4);
      var3 = null;
      var1.setline(106);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_two$28, (PyObject)null);
      var1.setlocal("test_two", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$26(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo")));
   }

   public PyObject test_one$27(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$28(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_error_in_teardown_class$29(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$30);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(129);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test2", var3, Test2$34);
      var1.setderef(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(140);
      PyObject var6 = var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getderef(0), var1.getderef(1));
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(141);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("testsRun"), (PyObject)Py.newInteger(4));
      var1.setline(142);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(1).__getattr__("errors")), (PyObject)Py.newInteger(2));
      var1.setline(143);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("tornDown"), (PyObject)Py.newInteger(1));
      var1.setline(144);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(1).__getattr__("tornDown"), (PyObject)Py.newInteger(1));
      var1.setline(146);
      var6 = var1.getlocal(1).__getattr__("errors").__getitem__(Py.newInteger(0));
      PyObject[] var7 = Py.unpackSequence(var6, 2);
      PyObject var5 = var7[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(147);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(2)), PyString.fromInterned("tearDownClass (%s.Test)")._mod(var1.getglobal("__name__")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$30(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(119);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("tornDown", var3);
      var3 = null;
      var1.setline(120);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = tearDownClass$31;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      PyObject var6 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownClass", var6);
      var3 = null;
      var1.setline(124);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_one$32, (PyObject)null);
      var1.setlocal("test_one", var5);
      var3 = null;
      var1.setline(126);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_two$33, (PyObject)null);
      var1.setlocal("test_two", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tearDownClass$31(PyFrame var1, ThreadState var2) {
      var1.setline(122);
      PyObject var10000 = var1.getderef(0);
      String var3 = "tornDown";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(123);
      throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo")));
   }

   public PyObject test_one$32(PyFrame var1, ThreadState var2) {
      var1.setline(125);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$33(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test2$34(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(130);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("tornDown", var3);
      var3 = null;
      var1.setline(131);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = tearDownClass$35;
      var4 = new PyObject[]{var1.f_back.getclosure(1)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      PyObject var6 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownClass", var6);
      var3 = null;
      var1.setline(135);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_one$36, (PyObject)null);
      var1.setlocal("test_one", var5);
      var3 = null;
      var1.setline(137);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_two$37, (PyObject)null);
      var1.setlocal("test_two", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tearDownClass$35(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyObject var10000 = var1.getderef(0);
      String var3 = "tornDown";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(134);
      throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo")));
   }

   public PyObject test_one$36(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$37(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_class_not_torndown_when_setup_fails$38(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$39);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(163);
      var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getderef(0));
      var1.setline(164);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getderef(0).__getattr__("tornDown"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$39(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(152);
      PyObject var3 = var1.getname("False");
      var1.setlocal("tornDown", var3);
      var3 = null;
      var1.setline(153);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, setUpClass$40, (PyObject)null);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("setUpClass", var3);
      var3 = null;
      var1.setline(156);
      var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = tearDownClass$41;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownClass", var3);
      var3 = null;
      var1.setline(160);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_one$42, (PyObject)null);
      var1.setlocal("test_one", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$40(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      throw Py.makeException(var1.getglobal("TypeError"));
   }

   public PyObject tearDownClass$41(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("tornDown", var3);
      var3 = null;
      var1.setline(159);
      throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo")));
   }

   public PyObject test_one$42(PyFrame var1, ThreadState var2) {
      var1.setline(161);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_class_not_setup_or_torndown_when_skipped$43(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$44);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(179);
      PyObject var5 = var1.getglobal("unittest").__getattr__("skip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hop")).__call__(var2, var1.getderef(0));
      var1.setderef(0, var5);
      var3 = null;
      var1.setline(180);
      var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getderef(0));
      var1.setline(181);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getderef(0).__getattr__("classSetUp"));
      var1.setline(182);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getderef(0).__getattr__("tornDown"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$44(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(168);
      PyObject var3 = var1.getname("False");
      var1.setlocal("classSetUp", var3);
      var3 = null;
      var1.setline(169);
      var3 = var1.getname("False");
      var1.setlocal("tornDown", var3);
      var3 = null;
      var1.setline(170);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = setUpClass$45;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("setUpClass", var3);
      var3 = null;
      var1.setline(173);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = tearDownClass$46;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownClass", var3);
      var3 = null;
      var1.setline(176);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_one$47, (PyObject)null);
      var1.setlocal("test_one", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$45(PyFrame var1, ThreadState var2) {
      var1.setline(172);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("classSetUp", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownClass$46(PyFrame var1, ThreadState var2) {
      var1.setline(175);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("tornDown", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_one$47(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_setup_teardown_order_with_pathological_suite$48(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(187);
      PyObject[] var5 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Module1", var5, Module1$49);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(195);
      var5 = new PyObject[]{var1.getglobal("object")};
      var4 = Py.makeClass("Module2", var5, Module2$52);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(203);
      var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test1", var5, Test1$55);
      var1.setlocal(3, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(215);
      var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test2", var5, Test2$60);
      var1.setlocal(4, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(227);
      var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test3", var5, Test3$65);
      var1.setlocal(5, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(239);
      PyString var6 = PyString.fromInterned("Module");
      var1.getlocal(3).__setattr__((String)"__module__", var6);
      var1.getlocal(4).__setattr__((String)"__module__", var6);
      var1.setline(240);
      var6 = PyString.fromInterned("Module2");
      var1.getlocal(5).__setattr__((String)"__module__", var6);
      var3 = null;
      var1.setline(241);
      PyObject var7 = var1.getlocal(1);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("Module"), var7);
      var3 = null;
      var1.setline(242);
      var7 = var1.getlocal(2);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("Module2"), var7);
      var3 = null;
      var1.setline(244);
      var7 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testOne"))})));
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(245);
      var7 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testTwo"))})));
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(246);
      var7 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testOne"))})));
      var1.setlocal(8, var7);
      var3 = null;
      var1.setline(247);
      var7 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testTwo"))})));
      var1.setlocal(9, var7);
      var3 = null;
      var1.setline(248);
      var7 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testOne"))})));
      var1.setlocal(10, var7);
      var3 = null;
      var1.setline(249);
      var7 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testTwo"))})));
      var1.setlocal(11, var7);
      var3 = null;
      var1.setline(250);
      var7 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11)})));
      var1.setlocal(12, var7);
      var3 = null;
      var1.setline(252);
      var7 = var1.getlocal(0).__getattr__("getRunner").__call__(var2);
      var1.setlocal(13, var7);
      var3 = null;
      var1.setline(253);
      var7 = var1.getlocal(13).__getattr__("run").__call__(var2, var1.getlocal(12));
      var1.setlocal(14, var7);
      var3 = null;
      var1.setline(254);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(14).__getattr__("testsRun"), (PyObject)Py.newInteger(6));
      var1.setline(255);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(14).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.setline(257);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("Module1.setUpModule"), PyString.fromInterned("setup 1"), PyString.fromInterned("Test1.testOne"), PyString.fromInterned("Test1.testTwo"), PyString.fromInterned("teardown 1"), PyString.fromInterned("setup 2"), PyString.fromInterned("Test2.testOne"), PyString.fromInterned("Test2.testTwo"), PyString.fromInterned("teardown 2"), PyString.fromInterned("Module1.tearDownModule"), PyString.fromInterned("Module2.setUpModule"), PyString.fromInterned("setup 3"), PyString.fromInterned("Test3.testOne"), PyString.fromInterned("Test3.testTwo"), PyString.fromInterned("teardown 3"), PyString.fromInterned("Module2.tearDownModule")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module1$49(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(188);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUpModule$50;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpModule", var5);
      var3 = null;
      var1.setline(191);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = tearDownModule$51;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("tearDownModule", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpModule$50(PyFrame var1, ThreadState var2) {
      var1.setline(190);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Module1.setUpModule"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownModule$51(PyFrame var1, ThreadState var2) {
      var1.setline(193);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Module1.tearDownModule"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module2$52(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(196);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUpModule$53;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpModule", var5);
      var3 = null;
      var1.setline(199);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = tearDownModule$54;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("tearDownModule", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpModule$53(PyFrame var1, ThreadState var2) {
      var1.setline(198);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Module2.setUpModule"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownModule$54(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Module2.tearDownModule"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test1$55(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(204);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUpClass$56;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpClass", var5);
      var3 = null;
      var1.setline(207);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = tearDownClass$57;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("tearDownClass", var5);
      var3 = null;
      var1.setline(210);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = testOne$58;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("testOne", var4);
      var3 = null;
      var1.setline(212);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = testTwo$59;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("testTwo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$56(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setup 1"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownClass$57(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("teardown 1"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testOne$58(PyFrame var1, ThreadState var2) {
      var1.setline(211);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Test1.testOne"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testTwo$59(PyFrame var1, ThreadState var2) {
      var1.setline(213);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Test1.testTwo"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test2$60(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(216);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUpClass$61;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpClass", var5);
      var3 = null;
      var1.setline(219);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = tearDownClass$62;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("tearDownClass", var5);
      var3 = null;
      var1.setline(222);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = testOne$63;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("testOne", var4);
      var3 = null;
      var1.setline(224);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = testTwo$64;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("testTwo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$61(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setup 2"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownClass$62(PyFrame var1, ThreadState var2) {
      var1.setline(221);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("teardown 2"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testOne$63(PyFrame var1, ThreadState var2) {
      var1.setline(223);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Test2.testOne"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testTwo$64(PyFrame var1, ThreadState var2) {
      var1.setline(225);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Test2.testTwo"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test3$65(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(228);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUpClass$66;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpClass", var5);
      var3 = null;
      var1.setline(231);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = tearDownClass$67;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("tearDownClass", var5);
      var3 = null;
      var1.setline(234);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = testOne$68;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("testOne", var4);
      var3 = null;
      var1.setline(236);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = testTwo$69;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("testTwo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$66(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setup 3"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownClass$67(PyFrame var1, ThreadState var2) {
      var1.setline(233);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("teardown 3"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testOne$68(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Test3.testOne"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testTwo$69(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Test3.testTwo"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_setup_module$70(PyFrame var1, ThreadState var2) {
      var1.setline(267);
      PyObject[] var3 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Module", var3, Module$71);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(273);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test", var3, Test$73);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(278);
      PyString var5 = PyString.fromInterned("Module");
      var1.getlocal(1).__setattr__((String)"__module__", var5);
      var3 = null;
      var1.setline(279);
      PyObject var6 = var1.getderef(0);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("Module"), var6);
      var3 = null;
      var1.setline(281);
      var6 = var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(282);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("moduleSetup"), (PyObject)Py.newInteger(1));
      var1.setline(283);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("testsRun"), (PyObject)Py.newInteger(2));
      var1.setline(284);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module$71(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(268);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("moduleSetup", var3);
      var3 = null;
      var1.setline(269);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = setUpModule$72;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      PyObject var6 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("setUpModule", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpModule$72(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyObject var10000 = var1.getderef(0);
      String var3 = "moduleSetup";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$73(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(274);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_one$74, (PyObject)null);
      var1.setlocal("test_one", var4);
      var3 = null;
      var1.setline(276);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_two$75, (PyObject)null);
      var1.setlocal("test_two", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_one$74(PyFrame var1, ThreadState var2) {
      var1.setline(275);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$75(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_error_in_setup_module$76(PyFrame var1, ThreadState var2) {
      var1.setline(287);
      PyObject[] var3 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Module", var3, Module$77);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(298);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test", var3, Test$80);
      var1.setderef(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(312);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test2", var3, Test2$85);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(317);
      PyString var6 = PyString.fromInterned("Module");
      var1.getderef(1).__setattr__((String)"__module__", var6);
      var3 = null;
      var1.setline(318);
      var6 = PyString.fromInterned("Module");
      var1.getlocal(1).__setattr__((String)"__module__", var6);
      var3 = null;
      var1.setline(319);
      PyObject var7 = var1.getderef(0);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("Module"), var7);
      var3 = null;
      var1.setline(321);
      var7 = var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getderef(1), var1.getlocal(1));
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(322);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("moduleSetup"), (PyObject)Py.newInteger(1));
      var1.setline(323);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("moduleTornDown"), (PyObject)Py.newInteger(0));
      var1.setline(324);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("testsRun"), (PyObject)Py.newInteger(0));
      var1.setline(325);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getderef(1).__getattr__("classSetUp"));
      var1.setline(326);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getderef(1).__getattr__("classTornDown"));
      var1.setline(327);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("errors")), (PyObject)Py.newInteger(1));
      var1.setline(328);
      var7 = var1.getlocal(2).__getattr__("errors").__getitem__(Py.newInteger(0));
      PyObject[] var8 = Py.unpackSequence(var7, 2);
      PyObject var5 = var8[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(329);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(3)), (PyObject)PyString.fromInterned("setUpModule (Module)"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module$77(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(288);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("moduleSetup", var3);
      var3 = null;
      var1.setline(289);
      var3 = Py.newInteger(0);
      var1.setlocal("moduleTornDown", var3);
      var3 = null;
      var1.setline(290);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = setUpModule$78;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      PyObject var6 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("setUpModule", var6);
      var3 = null;
      var1.setline(294);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = tearDownModule$79;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var6 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownModule", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpModule$78(PyFrame var1, ThreadState var2) {
      var1.setline(292);
      PyObject var10000 = var1.getderef(0);
      String var3 = "moduleSetup";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(293);
      throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo")));
   }

   public PyObject tearDownModule$79(PyFrame var1, ThreadState var2) {
      var1.setline(296);
      PyObject var10000 = var1.getderef(0);
      String var3 = "moduleTornDown";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$80(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(299);
      PyObject var3 = var1.getname("False");
      var1.setlocal("classSetUp", var3);
      var3 = null;
      var1.setline(300);
      var3 = var1.getname("False");
      var1.setlocal("classTornDown", var3);
      var3 = null;
      var1.setline(301);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = setUpClass$81;
      var4 = new PyObject[]{var1.f_back.getclosure(1)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("setUpClass", var3);
      var3 = null;
      var1.setline(304);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = tearDownClass$82;
      var4 = new PyObject[]{var1.f_back.getclosure(1)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownClass", var3);
      var3 = null;
      var1.setline(307);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_one$83, (PyObject)null);
      var1.setlocal("test_one", var5);
      var3 = null;
      var1.setline(309);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_two$84, (PyObject)null);
      var1.setlocal("test_two", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$81(PyFrame var1, ThreadState var2) {
      var1.setline(303);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("classSetUp", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownClass$82(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("classTornDown", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_one$83(PyFrame var1, ThreadState var2) {
      var1.setline(308);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$84(PyFrame var1, ThreadState var2) {
      var1.setline(310);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test2$85(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(313);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_one$86, (PyObject)null);
      var1.setlocal("test_one", var4);
      var3 = null;
      var1.setline(315);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_two$87, (PyObject)null);
      var1.setlocal("test_two", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_one$86(PyFrame var1, ThreadState var2) {
      var1.setline(314);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$87(PyFrame var1, ThreadState var2) {
      var1.setline(316);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_testcase_with_missing_module$88(PyFrame var1, ThreadState var2) {
      var1.setline(332);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$89);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(337);
      PyString var5 = PyString.fromInterned("Module");
      var1.getlocal(1).__setattr__((String)"__module__", var5);
      var3 = null;
      var1.setline(338);
      var1.getglobal("sys").__getattr__("modules").__getattr__("pop").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Module"), (PyObject)var1.getglobal("None"));
      var1.setline(340);
      PyObject var6 = var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(341);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("testsRun"), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$89(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(333);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_one$90, (PyObject)null);
      var1.setlocal("test_one", var4);
      var3 = null;
      var1.setline(335);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_two$91, (PyObject)null);
      var1.setlocal("test_two", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_one$90(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$91(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_teardown_module$92(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      PyObject[] var3 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Module", var3, Module$93);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(350);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test", var3, Test$95);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(355);
      PyString var5 = PyString.fromInterned("Module");
      var1.getlocal(1).__setattr__((String)"__module__", var5);
      var3 = null;
      var1.setline(356);
      PyObject var6 = var1.getderef(0);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("Module"), var6);
      var3 = null;
      var1.setline(358);
      var6 = var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(359);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("moduleTornDown"), (PyObject)Py.newInteger(1));
      var1.setline(360);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("testsRun"), (PyObject)Py.newInteger(2));
      var1.setline(361);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module$93(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(345);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("moduleTornDown", var3);
      var3 = null;
      var1.setline(346);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = tearDownModule$94;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      PyObject var6 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownModule", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tearDownModule$94(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      PyObject var10000 = var1.getderef(0);
      String var3 = "moduleTornDown";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$95(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(351);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_one$96, (PyObject)null);
      var1.setlocal("test_one", var4);
      var3 = null;
      var1.setline(353);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_two$97, (PyObject)null);
      var1.setlocal("test_two", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_one$96(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$97(PyFrame var1, ThreadState var2) {
      var1.setline(354);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_error_in_teardown_module$98(PyFrame var1, ThreadState var2) {
      var1.setline(364);
      PyObject[] var3 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Module", var3, Module$99);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(371);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test", var3, Test$101);
      var1.setderef(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(385);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test2", var3, Test2$106);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(390);
      PyString var6 = PyString.fromInterned("Module");
      var1.getderef(1).__setattr__((String)"__module__", var6);
      var3 = null;
      var1.setline(391);
      var6 = PyString.fromInterned("Module");
      var1.getlocal(1).__setattr__((String)"__module__", var6);
      var3 = null;
      var1.setline(392);
      PyObject var7 = var1.getderef(0);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("Module"), var7);
      var3 = null;
      var1.setline(394);
      var7 = var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getderef(1), var1.getlocal(1));
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(395);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("moduleTornDown"), (PyObject)Py.newInteger(1));
      var1.setline(396);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("testsRun"), (PyObject)Py.newInteger(4));
      var1.setline(397);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getderef(1).__getattr__("classSetUp"));
      var1.setline(398);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getderef(1).__getattr__("classTornDown"));
      var1.setline(399);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("errors")), (PyObject)Py.newInteger(1));
      var1.setline(400);
      var7 = var1.getlocal(2).__getattr__("errors").__getitem__(Py.newInteger(0));
      PyObject[] var8 = Py.unpackSequence(var7, 2);
      PyObject var5 = var8[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(401);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(3)), (PyObject)PyString.fromInterned("tearDownModule (Module)"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module$99(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(365);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal("moduleTornDown", var3);
      var3 = null;
      var1.setline(366);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = tearDownModule$100;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      PyObject var6 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownModule", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject tearDownModule$100(PyFrame var1, ThreadState var2) {
      var1.setline(368);
      PyObject var10000 = var1.getderef(0);
      String var3 = "moduleTornDown";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var3);
      var5 = var5._iadd(Py.newInteger(1));
      var4.__setattr__(var3, var5);
      var1.setline(369);
      throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo")));
   }

   public PyObject Test$101(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(372);
      PyObject var3 = var1.getname("False");
      var1.setlocal("classSetUp", var3);
      var3 = null;
      var1.setline(373);
      var3 = var1.getname("False");
      var1.setlocal("classTornDown", var3);
      var3 = null;
      var1.setline(374);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = setUpClass$102;
      var4 = new PyObject[]{var1.f_back.getclosure(1)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("setUpClass", var3);
      var3 = null;
      var1.setline(377);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = tearDownClass$103;
      var4 = new PyObject[]{var1.f_back.getclosure(1)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var3 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("tearDownClass", var3);
      var3 = null;
      var1.setline(380);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_one$104, (PyObject)null);
      var1.setlocal("test_one", var5);
      var3 = null;
      var1.setline(382);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_two$105, (PyObject)null);
      var1.setlocal("test_two", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$102(PyFrame var1, ThreadState var2) {
      var1.setline(376);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("classSetUp", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownClass$103(PyFrame var1, ThreadState var2) {
      var1.setline(379);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("classTornDown", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_one$104(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$105(PyFrame var1, ThreadState var2) {
      var1.setline(383);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test2$106(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(386);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_one$107, (PyObject)null);
      var1.setlocal("test_one", var4);
      var3 = null;
      var1.setline(388);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_two$108, (PyObject)null);
      var1.setlocal("test_two", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_one$107(PyFrame var1, ThreadState var2) {
      var1.setline(387);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$108(PyFrame var1, ThreadState var2) {
      var1.setline(389);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_skiptest_in_setupclass$109(PyFrame var1, ThreadState var2) {
      var1.setline(404);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$110);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(413);
      PyObject var5 = var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(414);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("testsRun"), (PyObject)Py.newInteger(0));
      var1.setline(415);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.setline(416);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("skipped")), (PyObject)Py.newInteger(1));
      var1.setline(417);
      var5 = var1.getlocal(2).__getattr__("skipped").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(418);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("str").__call__(var2, var1.getlocal(3)), PyString.fromInterned("setUpClass (%s.Test)")._mod(var1.getglobal("__name__")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$110(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(405);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUpClass$111, (PyObject)null);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpClass", var5);
      var3 = null;
      var1.setline(408);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_one$112, (PyObject)null);
      var1.setlocal("test_one", var4);
      var3 = null;
      var1.setline(410);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_two$113, (PyObject)null);
      var1.setlocal("test_two", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$111(PyFrame var1, ThreadState var2) {
      var1.setline(407);
      throw Py.makeException(var1.getglobal("unittest").__getattr__("SkipTest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo")));
   }

   public PyObject test_one$112(PyFrame var1, ThreadState var2) {
      var1.setline(409);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$113(PyFrame var1, ThreadState var2) {
      var1.setline(411);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_skiptest_in_setupmodule$114(PyFrame var1, ThreadState var2) {
      var1.setline(421);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Test", var3, Test$115);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(427);
      var3 = new PyObject[]{var1.getglobal("object")};
      var4 = Py.makeClass("Module", var3, Module$118);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(432);
      PyString var5 = PyString.fromInterned("Module");
      var1.getlocal(1).__setattr__((String)"__module__", var5);
      var3 = null;
      var1.setline(433);
      PyObject var6 = var1.getlocal(2);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("Module"), var6);
      var3 = null;
      var1.setline(435);
      var6 = var1.getlocal(0).__getattr__("runTests").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(436);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("testsRun"), (PyObject)Py.newInteger(0));
      var1.setline(437);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("errors")), (PyObject)Py.newInteger(0));
      var1.setline(438);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("skipped")), (PyObject)Py.newInteger(1));
      var1.setline(439);
      var6 = var1.getlocal(3).__getattr__("skipped").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(440);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(4)), (PyObject)PyString.fromInterned("setUpModule (Module)"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$115(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(422);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_one$116, (PyObject)null);
      var1.setlocal("test_one", var4);
      var3 = null;
      var1.setline(424);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_two$117, (PyObject)null);
      var1.setlocal("test_two", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_one$116(PyFrame var1, ThreadState var2) {
      var1.setline(423);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_two$117(PyFrame var1, ThreadState var2) {
      var1.setline(425);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module$118(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(428);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUpModule$119, (PyObject)null);
      PyObject var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpModule", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpModule$119(PyFrame var1, ThreadState var2) {
      var1.setline(430);
      throw Py.makeException(var1.getglobal("unittest").__getattr__("SkipTest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo")));
   }

   public PyObject test_suite_debug_executes_setups_and_teardowns$120(PyFrame var1, ThreadState var2) {
      var1.setline(443);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(445);
      PyObject[] var5 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Module", var5, Module$121);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(453);
      var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test", var5, Test$124);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(463);
      PyString var6 = PyString.fromInterned("Module");
      var1.getlocal(2).__setattr__((String)"__module__", var6);
      var3 = null;
      var1.setline(464);
      PyObject var7 = var1.getlocal(1);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("Module"), var7);
      var3 = null;
      var1.setline(466);
      var7 = var1.getglobal("unittest").__getattr__("defaultTestLoader").__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(467);
      var1.getlocal(3).__getattr__("debug").__call__(var2);
      var1.setline(468);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("setUpModule"), PyString.fromInterned("setUpClass"), PyString.fromInterned("test_something"), PyString.fromInterned("tearDownClass"), PyString.fromInterned("tearDownModule")});
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(469);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getderef(0), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module$121(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(446);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUpModule$122;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpModule", var5);
      var3 = null;
      var1.setline(449);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = tearDownModule$123;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("tearDownModule", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpModule$122(PyFrame var1, ThreadState var2) {
      var1.setline(448);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setUpModule"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownModule$123(PyFrame var1, ThreadState var2) {
      var1.setline(451);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tearDownModule"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test$124(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(454);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUpClass$125;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpClass", var5);
      var3 = null;
      var1.setline(457);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = tearDownClass$126;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("tearDownClass", var5);
      var3 = null;
      var1.setline(460);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = test_something$127;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("test_something", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$125(PyFrame var1, ThreadState var2) {
      var1.setline(456);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setUpClass"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDownClass$126(PyFrame var1, ThreadState var2) {
      var1.setline(459);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tearDownClass"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_something$127(PyFrame var1, ThreadState var2) {
      var1.setline(461);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_something"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite_debug_propagates_exceptions$128(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(472);
      PyObject[] var3 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Module", var3, Module$129);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(482);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test", var3, Test$132);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(495);
      PyString var8 = PyString.fromInterned("Module");
      var1.getlocal(2).__setattr__((String)"__module__", var8);
      var3 = null;
      var1.setline(496);
      PyObject var9 = var1.getlocal(1);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("Module"), var9);
      var3 = null;
      var1.setline(498);
      var9 = var1.getglobal("unittest").__getattr__("defaultTestLoader").__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(499);
      var9 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(500);
      var1.getlocal(4).__getattr__("addTest").__call__(var2, var1.getlocal(3));
      var1.setline(502);
      PyTuple var11 = new PyTuple(new PyObject[]{PyString.fromInterned("setUpModule"), PyString.fromInterned("tearDownModule"), PyString.fromInterned("setUpClass"), PyString.fromInterned("tearDownClass"), PyString.fromInterned("test_something")});
      var1.setlocal(5, var11);
      var3 = null;
      var1.setline(503);
      var9 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(5)).__iter__();

      while(true) {
         var1.setline(503);
         var4 = var9.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setderef(0, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(6, var6);
         var6 = null;
         ContextManager var10;
         var6 = (var10 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaisesRegexp").__call__(var2, var1.getglobal("Exception"), var1.getlocal(6)))).__enter__(var2);

         try {
            var1.setline(505);
            var1.getlocal(4).__getattr__("debug").__call__(var2);
         } catch (Throwable var7) {
            if (var10.__exit__(var2, Py.setException(var7, var1))) {
               continue;
            }

            throw (Throwable)Py.makeException();
         }

         var10.__exit__(var2, (PyException)null);
      }
   }

   public PyObject Module$129(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(473);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUpModule$130;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpModule", var5);
      var3 = null;
      var1.setline(477);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = tearDownModule$131;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var5 = var1.getname("staticmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("tearDownModule", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpModule$130(PyFrame var1, ThreadState var2) {
      var1.setline(475);
      PyObject var3 = var1.getderef(0);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(476);
         throw Py.makeException(var1.getglobal("Exception").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setUpModule")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject tearDownModule$131(PyFrame var1, ThreadState var2) {
      var1.setline(479);
      PyObject var3 = var1.getderef(0);
      PyObject var10000 = var3._eq(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(480);
         throw Py.makeException(var1.getglobal("Exception").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tearDownModule")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject Test$132(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(483);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = setUpClass$133;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      PyObject var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("setUpClass", var5);
      var3 = null;
      var1.setline(487);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = tearDownClass$134;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var5 = var1.getname("classmethod").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("tearDownClass", var5);
      var3 = null;
      var1.setline(491);
      var3 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var3;
      var10004 = test_something$135;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("test_something", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUpClass$133(PyFrame var1, ThreadState var2) {
      var1.setline(485);
      PyObject var3 = var1.getderef(0);
      PyObject var10000 = var3._eq(Py.newInteger(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(486);
         throw Py.makeException(var1.getglobal("Exception").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("setUpClass")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject tearDownClass$134(PyFrame var1, ThreadState var2) {
      var1.setline(489);
      PyObject var3 = var1.getderef(0);
      PyObject var10000 = var3._eq(Py.newInteger(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(490);
         throw Py.makeException(var1.getglobal("Exception").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tearDownClass")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_something$135(PyFrame var1, ThreadState var2) {
      var1.setline(492);
      PyObject var3 = var1.getderef(0);
      PyObject var10000 = var3._eq(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(493);
         throw Py.makeException(var1.getglobal("Exception").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_something")));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public test_setups$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"_"};
      resultFactory$1 = Py.newCode(1, var2, var1, "resultFactory", 8, true, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestSetups$2 = Py.newCode(0, var2, var1, "TestSetups", 12, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      getRunner$3 = Py.newCode(1, var2, var1, "getRunner", 14, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cases", "suite", "case", "tests", "runner", "realSuite"};
      runTests$4 = Py.newCode(2, var2, var1, "runTests", 17, true, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "Test"};
      String[] var10001 = var2;
      test_setups$py var10007 = self;
      var2 = new String[]{"Test"};
      test_setup_class$5 = Py.newCode(1, var10001, var1, "test_setup_class", 33, false, false, var10007, 5, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Test$6 = Py.newCode(0, var2, var1, "Test", 34, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      setUpClass$7 = Py.newCode(1, var10001, var1, "setUpClass", 36, false, false, var10007, 7, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      test_one$8 = Py.newCode(1, var2, var1, "test_one", 40, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$9 = Py.newCode(1, var2, var1, "test_two", 42, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "Test"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      test_teardown_class$10 = Py.newCode(1, var10001, var1, "test_teardown_class", 51, false, false, var10007, 10, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Test$11 = Py.newCode(0, var2, var1, "Test", 52, false, false, self, 11, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      tearDownClass$12 = Py.newCode(1, var10001, var1, "tearDownClass", 54, false, false, var10007, 12, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      test_one$13 = Py.newCode(1, var2, var1, "test_one", 58, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$14 = Py.newCode(1, var2, var1, "test_two", 60, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "Test", "Test2"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test", "Test2"};
      test_teardown_class_two_classes$15 = Py.newCode(1, var10001, var1, "test_teardown_class_two_classes", 69, false, false, var10007, 15, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      Test$16 = Py.newCode(0, var2, var1, "Test", 70, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      tearDownClass$17 = Py.newCode(1, var10001, var1, "tearDownClass", 72, false, false, var10007, 17, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      test_one$18 = Py.newCode(1, var2, var1, "test_one", 76, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$19 = Py.newCode(1, var2, var1, "test_two", 78, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test2$20 = Py.newCode(0, var2, var1, "Test2", 81, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test2"};
      tearDownClass$21 = Py.newCode(1, var10001, var1, "tearDownClass", 83, false, false, var10007, 21, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      test_one$22 = Py.newCode(1, var2, var1, "test_one", 87, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$23 = Py.newCode(1, var2, var1, "test_two", 89, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "BrokenTest", "result", "error", "_"};
      test_error_in_setupclass$24 = Py.newCode(1, var2, var1, "test_error_in_setupclass", 99, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      BrokenTest$25 = Py.newCode(0, var2, var1, "BrokenTest", 100, false, false, self, 25, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      setUpClass$26 = Py.newCode(1, var2, var1, "setUpClass", 101, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_one$27 = Py.newCode(1, var2, var1, "test_one", 104, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$28 = Py.newCode(1, var2, var1, "test_two", 106, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "error", "_", "Test", "Test2"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test", "Test2"};
      test_error_in_teardown_class$29 = Py.newCode(1, var10001, var1, "test_error_in_teardown_class", 117, false, false, var10007, 29, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      Test$30 = Py.newCode(0, var2, var1, "Test", 118, false, false, self, 30, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      tearDownClass$31 = Py.newCode(1, var10001, var1, "tearDownClass", 120, false, false, var10007, 31, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      test_one$32 = Py.newCode(1, var2, var1, "test_one", 124, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$33 = Py.newCode(1, var2, var1, "test_two", 126, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test2$34 = Py.newCode(0, var2, var1, "Test2", 129, false, false, self, 34, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test2"};
      tearDownClass$35 = Py.newCode(1, var10001, var1, "tearDownClass", 131, false, false, var10007, 35, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      test_one$36 = Py.newCode(1, var2, var1, "test_one", 135, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$37 = Py.newCode(1, var2, var1, "test_two", 137, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      test_class_not_torndown_when_setup_fails$38 = Py.newCode(1, var10001, var1, "test_class_not_torndown_when_setup_fails", 150, false, false, var10007, 38, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Test$39 = Py.newCode(0, var2, var1, "Test", 151, false, false, self, 39, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      setUpClass$40 = Py.newCode(1, var2, var1, "setUpClass", 153, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      tearDownClass$41 = Py.newCode(1, var10001, var1, "tearDownClass", 156, false, false, var10007, 41, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      test_one$42 = Py.newCode(1, var2, var1, "test_one", 160, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      test_class_not_setup_or_torndown_when_skipped$43 = Py.newCode(1, var10001, var1, "test_class_not_setup_or_torndown_when_skipped", 166, false, false, var10007, 43, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Test$44 = Py.newCode(0, var2, var1, "Test", 167, false, false, self, 44, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      setUpClass$45 = Py.newCode(1, var10001, var1, "setUpClass", 170, false, false, var10007, 45, (String[])null, var2, 0, 4097);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      tearDownClass$46 = Py.newCode(1, var10001, var1, "tearDownClass", 173, false, false, var10007, 46, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      test_one$47 = Py.newCode(1, var2, var1, "test_one", 176, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Module1", "Module2", "Test1", "Test2", "Test3", "first", "second", "third", "fourth", "fifth", "sixth", "suite", "runner", "result", "results"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      test_setup_teardown_order_with_pathological_suite$48 = Py.newCode(1, var10001, var1, "test_setup_teardown_order_with_pathological_suite", 184, false, false, var10007, 48, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Module1$49 = Py.newCode(0, var2, var1, "Module1", 187, false, false, self, 49, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      setUpModule$50 = Py.newCode(0, var10001, var1, "setUpModule", 188, false, false, var10007, 50, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      tearDownModule$51 = Py.newCode(0, var10001, var1, "tearDownModule", 191, false, false, var10007, 51, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Module2$52 = Py.newCode(0, var2, var1, "Module2", 195, false, false, self, 52, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      setUpModule$53 = Py.newCode(0, var10001, var1, "setUpModule", 196, false, false, var10007, 53, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      tearDownModule$54 = Py.newCode(0, var10001, var1, "tearDownModule", 199, false, false, var10007, 54, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Test1$55 = Py.newCode(0, var2, var1, "Test1", 203, false, false, self, 55, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      setUpClass$56 = Py.newCode(1, var10001, var1, "setUpClass", 204, false, false, var10007, 56, (String[])null, var2, 0, 4097);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      tearDownClass$57 = Py.newCode(1, var10001, var1, "tearDownClass", 207, false, false, var10007, 57, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      testOne$58 = Py.newCode(1, var10001, var1, "testOne", 210, false, false, var10007, 58, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      testTwo$59 = Py.newCode(1, var10001, var1, "testTwo", 212, false, false, var10007, 59, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Test2$60 = Py.newCode(0, var2, var1, "Test2", 215, false, false, self, 60, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      setUpClass$61 = Py.newCode(1, var10001, var1, "setUpClass", 216, false, false, var10007, 61, (String[])null, var2, 0, 4097);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      tearDownClass$62 = Py.newCode(1, var10001, var1, "tearDownClass", 219, false, false, var10007, 62, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      testOne$63 = Py.newCode(1, var10001, var1, "testOne", 222, false, false, var10007, 63, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      testTwo$64 = Py.newCode(1, var10001, var1, "testTwo", 224, false, false, var10007, 64, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Test3$65 = Py.newCode(0, var2, var1, "Test3", 227, false, false, self, 65, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      setUpClass$66 = Py.newCode(1, var10001, var1, "setUpClass", 228, false, false, var10007, 66, (String[])null, var2, 0, 4097);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      tearDownClass$67 = Py.newCode(1, var10001, var1, "tearDownClass", 231, false, false, var10007, 67, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      testOne$68 = Py.newCode(1, var10001, var1, "testOne", 234, false, false, var10007, 68, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"results"};
      testTwo$69 = Py.newCode(1, var10001, var1, "testTwo", 236, false, false, var10007, 69, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "Test", "result", "Module"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module"};
      test_setup_module$70 = Py.newCode(1, var10001, var1, "test_setup_module", 266, false, false, var10007, 70, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Module$71 = Py.newCode(0, var2, var1, "Module", 267, false, false, self, 71, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module"};
      setUpModule$72 = Py.newCode(0, var10001, var1, "setUpModule", 269, false, false, var10007, 72, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Test$73 = Py.newCode(0, var2, var1, "Test", 273, false, false, self, 73, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_one$74 = Py.newCode(1, var2, var1, "test_one", 274, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$75 = Py.newCode(1, var2, var1, "test_two", 276, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test2", "result", "error", "_", "Module", "Test"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module", "Test"};
      test_error_in_setup_module$76 = Py.newCode(1, var10001, var1, "test_error_in_setup_module", 286, false, false, var10007, 76, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      Module$77 = Py.newCode(0, var2, var1, "Module", 287, false, false, self, 77, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module"};
      setUpModule$78 = Py.newCode(0, var10001, var1, "setUpModule", 290, false, false, var10007, 78, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module"};
      tearDownModule$79 = Py.newCode(0, var10001, var1, "tearDownModule", 294, false, false, var10007, 79, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Test$80 = Py.newCode(0, var2, var1, "Test", 298, false, false, self, 80, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      setUpClass$81 = Py.newCode(1, var10001, var1, "setUpClass", 301, false, false, var10007, 81, (String[])null, var2, 0, 4097);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      tearDownClass$82 = Py.newCode(1, var10001, var1, "tearDownClass", 304, false, false, var10007, 82, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      test_one$83 = Py.newCode(1, var2, var1, "test_one", 307, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$84 = Py.newCode(1, var2, var1, "test_two", 309, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test2$85 = Py.newCode(0, var2, var1, "Test2", 312, false, false, self, 85, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_one$86 = Py.newCode(1, var2, var1, "test_one", 313, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$87 = Py.newCode(1, var2, var1, "test_two", 315, false, false, self, 87, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test", "result"};
      test_testcase_with_missing_module$88 = Py.newCode(1, var2, var1, "test_testcase_with_missing_module", 331, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test$89 = Py.newCode(0, var2, var1, "Test", 332, false, false, self, 89, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_one$90 = Py.newCode(1, var2, var1, "test_one", 333, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$91 = Py.newCode(1, var2, var1, "test_two", 335, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test", "result", "Module"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module"};
      test_teardown_module$92 = Py.newCode(1, var10001, var1, "test_teardown_module", 343, false, false, var10007, 92, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Module$93 = Py.newCode(0, var2, var1, "Module", 344, false, false, self, 93, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module"};
      tearDownModule$94 = Py.newCode(0, var10001, var1, "tearDownModule", 346, false, false, var10007, 94, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Test$95 = Py.newCode(0, var2, var1, "Test", 350, false, false, self, 95, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_one$96 = Py.newCode(1, var2, var1, "test_one", 351, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$97 = Py.newCode(1, var2, var1, "test_two", 353, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test2", "result", "error", "_", "Module", "Test"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module", "Test"};
      test_error_in_teardown_module$98 = Py.newCode(1, var10001, var1, "test_error_in_teardown_module", 363, false, false, var10007, 98, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      Module$99 = Py.newCode(0, var2, var1, "Module", 364, false, false, self, 99, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module"};
      tearDownModule$100 = Py.newCode(0, var10001, var1, "tearDownModule", 366, false, false, var10007, 100, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Test$101 = Py.newCode(0, var2, var1, "Test", 371, false, false, self, 101, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      setUpClass$102 = Py.newCode(1, var10001, var1, "setUpClass", 374, false, false, var10007, 102, (String[])null, var2, 0, 4097);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Test"};
      tearDownClass$103 = Py.newCode(1, var10001, var1, "tearDownClass", 377, false, false, var10007, 103, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      test_one$104 = Py.newCode(1, var2, var1, "test_one", 380, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$105 = Py.newCode(1, var2, var1, "test_two", 382, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test2$106 = Py.newCode(0, var2, var1, "Test2", 385, false, false, self, 106, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_one$107 = Py.newCode(1, var2, var1, "test_one", 386, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$108 = Py.newCode(1, var2, var1, "test_two", 388, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test", "result", "skipped"};
      test_skiptest_in_setupclass$109 = Py.newCode(1, var2, var1, "test_skiptest_in_setupclass", 403, false, false, self, 109, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test$110 = Py.newCode(0, var2, var1, "Test", 404, false, false, self, 110, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      setUpClass$111 = Py.newCode(1, var2, var1, "setUpClass", 405, false, false, self, 111, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_one$112 = Py.newCode(1, var2, var1, "test_one", 408, false, false, self, 112, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$113 = Py.newCode(1, var2, var1, "test_two", 410, false, false, self, 113, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Test", "Module", "result", "skipped"};
      test_skiptest_in_setupmodule$114 = Py.newCode(1, var2, var1, "test_skiptest_in_setupmodule", 420, false, false, self, 114, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Test$115 = Py.newCode(0, var2, var1, "Test", 421, false, false, self, 115, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_one$116 = Py.newCode(1, var2, var1, "test_one", 422, false, false, self, 116, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_two$117 = Py.newCode(1, var2, var1, "test_two", 424, false, false, self, 117, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Module$118 = Py.newCode(0, var2, var1, "Module", 427, false, false, self, 118, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      setUpModule$119 = Py.newCode(0, var2, var1, "setUpModule", 428, false, false, self, 119, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Module", "Test", "suite", "expectedOrder", "ordering"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      test_suite_debug_executes_setups_and_teardowns$120 = Py.newCode(1, var10001, var1, "test_suite_debug_executes_setups_and_teardowns", 442, false, false, var10007, 120, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Module$121 = Py.newCode(0, var2, var1, "Module", 445, false, false, self, 121, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      setUpModule$122 = Py.newCode(0, var10001, var1, "setUpModule", 446, false, false, var10007, 122, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      tearDownModule$123 = Py.newCode(0, var10001, var1, "tearDownModule", 449, false, false, var10007, 123, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Test$124 = Py.newCode(0, var2, var1, "Test", 453, false, false, self, 124, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      setUpClass$125 = Py.newCode(1, var10001, var1, "setUpClass", 454, false, false, var10007, 125, (String[])null, var2, 0, 4097);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      tearDownClass$126 = Py.newCode(1, var10001, var1, "tearDownClass", 457, false, false, var10007, 126, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"ordering"};
      test_something$127 = Py.newCode(1, var10001, var1, "test_something", 460, false, false, var10007, 127, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "Module", "Test", "_suite", "suite", "messages", "msg", "phase"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"phase"};
      test_suite_debug_propagates_exceptions$128 = Py.newCode(1, var10001, var1, "test_suite_debug_propagates_exceptions", 471, false, false, var10007, 128, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Module$129 = Py.newCode(0, var2, var1, "Module", 472, false, false, self, 129, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"phase"};
      setUpModule$130 = Py.newCode(0, var10001, var1, "setUpModule", 473, false, false, var10007, 130, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"phase"};
      tearDownModule$131 = Py.newCode(0, var10001, var1, "tearDownModule", 477, false, false, var10007, 131, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Test$132 = Py.newCode(0, var2, var1, "Test", 482, false, false, self, 132, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"phase"};
      setUpClass$133 = Py.newCode(1, var10001, var1, "setUpClass", 483, false, false, var10007, 133, (String[])null, var2, 0, 4097);
      var2 = new String[]{"cls"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"phase"};
      tearDownClass$134 = Py.newCode(1, var10001, var1, "tearDownClass", 487, false, false, var10007, 134, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"phase"};
      test_something$135 = Py.newCode(1, var10001, var1, "test_something", 491, false, false, var10007, 135, (String[])null, var2, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_setups$py("unittest/test/test_setups$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_setups$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.resultFactory$1(var2, var3);
         case 2:
            return this.TestSetups$2(var2, var3);
         case 3:
            return this.getRunner$3(var2, var3);
         case 4:
            return this.runTests$4(var2, var3);
         case 5:
            return this.test_setup_class$5(var2, var3);
         case 6:
            return this.Test$6(var2, var3);
         case 7:
            return this.setUpClass$7(var2, var3);
         case 8:
            return this.test_one$8(var2, var3);
         case 9:
            return this.test_two$9(var2, var3);
         case 10:
            return this.test_teardown_class$10(var2, var3);
         case 11:
            return this.Test$11(var2, var3);
         case 12:
            return this.tearDownClass$12(var2, var3);
         case 13:
            return this.test_one$13(var2, var3);
         case 14:
            return this.test_two$14(var2, var3);
         case 15:
            return this.test_teardown_class_two_classes$15(var2, var3);
         case 16:
            return this.Test$16(var2, var3);
         case 17:
            return this.tearDownClass$17(var2, var3);
         case 18:
            return this.test_one$18(var2, var3);
         case 19:
            return this.test_two$19(var2, var3);
         case 20:
            return this.Test2$20(var2, var3);
         case 21:
            return this.tearDownClass$21(var2, var3);
         case 22:
            return this.test_one$22(var2, var3);
         case 23:
            return this.test_two$23(var2, var3);
         case 24:
            return this.test_error_in_setupclass$24(var2, var3);
         case 25:
            return this.BrokenTest$25(var2, var3);
         case 26:
            return this.setUpClass$26(var2, var3);
         case 27:
            return this.test_one$27(var2, var3);
         case 28:
            return this.test_two$28(var2, var3);
         case 29:
            return this.test_error_in_teardown_class$29(var2, var3);
         case 30:
            return this.Test$30(var2, var3);
         case 31:
            return this.tearDownClass$31(var2, var3);
         case 32:
            return this.test_one$32(var2, var3);
         case 33:
            return this.test_two$33(var2, var3);
         case 34:
            return this.Test2$34(var2, var3);
         case 35:
            return this.tearDownClass$35(var2, var3);
         case 36:
            return this.test_one$36(var2, var3);
         case 37:
            return this.test_two$37(var2, var3);
         case 38:
            return this.test_class_not_torndown_when_setup_fails$38(var2, var3);
         case 39:
            return this.Test$39(var2, var3);
         case 40:
            return this.setUpClass$40(var2, var3);
         case 41:
            return this.tearDownClass$41(var2, var3);
         case 42:
            return this.test_one$42(var2, var3);
         case 43:
            return this.test_class_not_setup_or_torndown_when_skipped$43(var2, var3);
         case 44:
            return this.Test$44(var2, var3);
         case 45:
            return this.setUpClass$45(var2, var3);
         case 46:
            return this.tearDownClass$46(var2, var3);
         case 47:
            return this.test_one$47(var2, var3);
         case 48:
            return this.test_setup_teardown_order_with_pathological_suite$48(var2, var3);
         case 49:
            return this.Module1$49(var2, var3);
         case 50:
            return this.setUpModule$50(var2, var3);
         case 51:
            return this.tearDownModule$51(var2, var3);
         case 52:
            return this.Module2$52(var2, var3);
         case 53:
            return this.setUpModule$53(var2, var3);
         case 54:
            return this.tearDownModule$54(var2, var3);
         case 55:
            return this.Test1$55(var2, var3);
         case 56:
            return this.setUpClass$56(var2, var3);
         case 57:
            return this.tearDownClass$57(var2, var3);
         case 58:
            return this.testOne$58(var2, var3);
         case 59:
            return this.testTwo$59(var2, var3);
         case 60:
            return this.Test2$60(var2, var3);
         case 61:
            return this.setUpClass$61(var2, var3);
         case 62:
            return this.tearDownClass$62(var2, var3);
         case 63:
            return this.testOne$63(var2, var3);
         case 64:
            return this.testTwo$64(var2, var3);
         case 65:
            return this.Test3$65(var2, var3);
         case 66:
            return this.setUpClass$66(var2, var3);
         case 67:
            return this.tearDownClass$67(var2, var3);
         case 68:
            return this.testOne$68(var2, var3);
         case 69:
            return this.testTwo$69(var2, var3);
         case 70:
            return this.test_setup_module$70(var2, var3);
         case 71:
            return this.Module$71(var2, var3);
         case 72:
            return this.setUpModule$72(var2, var3);
         case 73:
            return this.Test$73(var2, var3);
         case 74:
            return this.test_one$74(var2, var3);
         case 75:
            return this.test_two$75(var2, var3);
         case 76:
            return this.test_error_in_setup_module$76(var2, var3);
         case 77:
            return this.Module$77(var2, var3);
         case 78:
            return this.setUpModule$78(var2, var3);
         case 79:
            return this.tearDownModule$79(var2, var3);
         case 80:
            return this.Test$80(var2, var3);
         case 81:
            return this.setUpClass$81(var2, var3);
         case 82:
            return this.tearDownClass$82(var2, var3);
         case 83:
            return this.test_one$83(var2, var3);
         case 84:
            return this.test_two$84(var2, var3);
         case 85:
            return this.Test2$85(var2, var3);
         case 86:
            return this.test_one$86(var2, var3);
         case 87:
            return this.test_two$87(var2, var3);
         case 88:
            return this.test_testcase_with_missing_module$88(var2, var3);
         case 89:
            return this.Test$89(var2, var3);
         case 90:
            return this.test_one$90(var2, var3);
         case 91:
            return this.test_two$91(var2, var3);
         case 92:
            return this.test_teardown_module$92(var2, var3);
         case 93:
            return this.Module$93(var2, var3);
         case 94:
            return this.tearDownModule$94(var2, var3);
         case 95:
            return this.Test$95(var2, var3);
         case 96:
            return this.test_one$96(var2, var3);
         case 97:
            return this.test_two$97(var2, var3);
         case 98:
            return this.test_error_in_teardown_module$98(var2, var3);
         case 99:
            return this.Module$99(var2, var3);
         case 100:
            return this.tearDownModule$100(var2, var3);
         case 101:
            return this.Test$101(var2, var3);
         case 102:
            return this.setUpClass$102(var2, var3);
         case 103:
            return this.tearDownClass$103(var2, var3);
         case 104:
            return this.test_one$104(var2, var3);
         case 105:
            return this.test_two$105(var2, var3);
         case 106:
            return this.Test2$106(var2, var3);
         case 107:
            return this.test_one$107(var2, var3);
         case 108:
            return this.test_two$108(var2, var3);
         case 109:
            return this.test_skiptest_in_setupclass$109(var2, var3);
         case 110:
            return this.Test$110(var2, var3);
         case 111:
            return this.setUpClass$111(var2, var3);
         case 112:
            return this.test_one$112(var2, var3);
         case 113:
            return this.test_two$113(var2, var3);
         case 114:
            return this.test_skiptest_in_setupmodule$114(var2, var3);
         case 115:
            return this.Test$115(var2, var3);
         case 116:
            return this.test_one$116(var2, var3);
         case 117:
            return this.test_two$117(var2, var3);
         case 118:
            return this.Module$118(var2, var3);
         case 119:
            return this.setUpModule$119(var2, var3);
         case 120:
            return this.test_suite_debug_executes_setups_and_teardowns$120(var2, var3);
         case 121:
            return this.Module$121(var2, var3);
         case 122:
            return this.setUpModule$122(var2, var3);
         case 123:
            return this.tearDownModule$123(var2, var3);
         case 124:
            return this.Test$124(var2, var3);
         case 125:
            return this.setUpClass$125(var2, var3);
         case 126:
            return this.tearDownClass$126(var2, var3);
         case 127:
            return this.test_something$127(var2, var3);
         case 128:
            return this.test_suite_debug_propagates_exceptions$128(var2, var3);
         case 129:
            return this.Module$129(var2, var3);
         case 130:
            return this.setUpModule$130(var2, var3);
         case 131:
            return this.tearDownModule$131(var2, var3);
         case 132:
            return this.Test$132(var2, var3);
         case 133:
            return this.setUpClass$133(var2, var3);
         case 134:
            return this.tearDownClass$134(var2, var3);
         case 135:
            return this.test_something$135(var2, var3);
         default:
            return null;
      }
   }
}
