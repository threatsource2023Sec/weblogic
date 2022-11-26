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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("unittest/test/test_skipping.py")
public class test_skipping$py extends PyFunctionTable implements PyRunnable {
   static test_skipping$py self;
   static final PyCode f$0;
   static final PyCode Test_TestSkipping$1;
   static final PyCode test_skipping$2;
   static final PyCode Foo$3;
   static final PyCode test_skip_me$4;
   static final PyCode Foo$5;
   static final PyCode setUp$6;
   static final PyCode test_nothing$7;
   static final PyCode test_skipping_decorators$8;
   static final PyCode Foo$9;
   static final PyCode test_skip$10;
   static final PyCode test_dont_skip$11;
   static final PyCode test_skip_class$12;
   static final PyCode Foo$13;
   static final PyCode test_1$14;
   static final PyCode test_skip_non_unittest_class_old_style$15;
   static final PyCode Mixin$16;
   static final PyCode test_1$17;
   static final PyCode Foo$18;
   static final PyCode test_skip_non_unittest_class_new_style$19;
   static final PyCode Mixin$20;
   static final PyCode test_1$21;
   static final PyCode Foo$22;
   static final PyCode test_expected_failure$23;
   static final PyCode Foo$24;
   static final PyCode test_die$25;
   static final PyCode test_unexpected_success$26;
   static final PyCode Foo$27;
   static final PyCode test_die$28;
   static final PyCode test_skip_doesnt_run_setup$29;
   static final PyCode Foo$30;
   static final PyCode setUp$31;
   static final PyCode tornDown$32;
   static final PyCode test_1$33;
   static final PyCode test_decorated_skip$34;
   static final PyCode decorator$35;
   static final PyCode inner$36;
   static final PyCode Foo$37;
   static final PyCode test_1$38;

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
      var4 = Py.makeClass("Test_TestSkipping", var6, Test_TestSkipping$1);
      var1.setlocal("Test_TestSkipping", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(167);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(168);
         var1.getname("unittest").__getattr__("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test_TestSkipping$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(8);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_skipping$2, (PyObject)null);
      var1.setlocal("test_skipping", var4);
      var3 = null;
      var1.setline(32);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_skipping_decorators$8, (PyObject)null);
      var1.setlocal("test_skipping_decorators", var4);
      var3 = null;
      var1.setline(56);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_skip_class$12, (PyObject)null);
      var1.setlocal("test_skip_class", var4);
      var3 = null;
      var1.setline(69);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_skip_non_unittest_class_old_style$15, (PyObject)null);
      var1.setlocal("test_skip_non_unittest_class_old_style", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_skip_non_unittest_class_new_style$19, (PyObject)null);
      var1.setlocal("test_skip_non_unittest_class_new_style", var4);
      var3 = null;
      var1.setline(99);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_expected_failure$23, (PyObject)null);
      var1.setlocal("test_expected_failure", var4);
      var3 = null;
      var1.setline(113);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_unexpected_success$26, (PyObject)null);
      var1.setlocal("test_unexpected_success", var4);
      var3 = null;
      var1.setline(128);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_skip_doesnt_run_setup$29, (PyObject)null);
      var1.setlocal("test_skip_doesnt_run_setup", var4);
      var3 = null;
      var1.setline(148);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_decorated_skip$34, (PyObject)null);
      var1.setlocal("test_decorated_skip", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_skipping$2(PyFrame var1, ThreadState var2) {
      var1.setline(9);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$3);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(12);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(13);
      PyObject var6 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(14);
      var6 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_skip_me"));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(15);
      var1.getlocal(4).__getattr__("run").__call__(var2, var1.getlocal(3));
      var1.setline(16);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("addSkip"), PyString.fromInterned("stopTest")})));
      var1.setline(17);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("skipped"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(4), PyString.fromInterned("skip")})})));
      var1.setline(20);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Foo", var3, Foo$5);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(24);
      var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(25);
      var6 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(26);
      var6 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_nothing"));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(27);
      var1.getlocal(4).__getattr__("run").__call__(var2, var1.getlocal(3));
      var1.setline(28);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("addSkip"), PyString.fromInterned("stopTest")})));
      var1.setline(29);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("skipped"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(4), PyString.fromInterned("testing")})})));
      var1.setline(30);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("testsRun"), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(10);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_skip_me$4, (PyObject)null);
      var1.setlocal("test_skip_me", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_skip_me$4(PyFrame var1, ThreadState var2) {
      var1.setline(11);
      var1.getlocal(0).__getattr__("skipTest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("skip"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(21);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$6, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(23);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_nothing$7, (PyObject)null);
      var1.setlocal("test_nothing", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$6(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      var1.getlocal(0).__getattr__("skipTest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testing"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_nothing$7(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_skipping_decorators$8(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyTuple var3 = new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("unittest").__getattr__("skipUnless"), var1.getglobal("False"), var1.getglobal("True")}), new PyTuple(new PyObject[]{var1.getglobal("unittest").__getattr__("skipIf"), var1.getglobal("True"), var1.getglobal("False")})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(35);
      PyObject var7 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(35);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setderef(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setderef(0, var6);
         var6 = null;
         var6 = var5[2];
         var1.setderef(1, var6);
         var6 = null;
         var1.setline(36);
         var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
         PyCode var10002 = Foo$9;
         PyObject[] var10 = new PyObject[]{var1.getclosure(2), var1.getclosure(0), var1.getclosure(1)};
         var6 = Py.makeClass("Foo", var5, var10002, var10);
         var1.setlocal(2, var6);
         var6 = null;
         Arrays.fill(var5, (Object)null);
         var1.setline(42);
         PyObject var8 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_skip"));
         var1.setlocal(3, var8);
         var5 = null;
         var1.setline(43);
         var8 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_dont_skip"));
         var1.setlocal(4, var8);
         var5 = null;
         var1.setline(44);
         var8 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(3), var1.getlocal(4)})));
         var1.setlocal(5, var8);
         var5 = null;
         var1.setline(45);
         PyList var9 = new PyList(Py.EmptyObjects);
         var1.setlocal(6, var9);
         var5 = null;
         var1.setline(46);
         var8 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(6));
         var1.setlocal(7, var8);
         var5 = null;
         var1.setline(47);
         var1.getlocal(5).__getattr__("run").__call__(var2, var1.getlocal(7));
         var1.setline(48);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(7).__getattr__("skipped")), (PyObject)Py.newInteger(1));
         var1.setline(49);
         var9 = new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("addSkip"), PyString.fromInterned("stopTest"), PyString.fromInterned("startTest"), PyString.fromInterned("addSuccess"), PyString.fromInterned("stopTest")});
         var1.setlocal(8, var9);
         var5 = null;
         var1.setline(51);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(6), var1.getlocal(8));
         var1.setline(52);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("testsRun"), (PyObject)Py.newInteger(2));
         var1.setline(53);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(7).__getattr__("skipped"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(3), PyString.fromInterned("testing")})})));
         var1.setline(54);
         var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(7).__getattr__("wasSuccessful").__call__(var2));
      }
   }

   public PyObject Foo$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(37);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_skip$10, (PyObject)null);
      PyObject var5 = var1.getderef(0).__call__((ThreadState)var2, (PyObject)var1.getderef(1), (PyObject)PyString.fromInterned("testing")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_skip", var5);
      var3 = null;
      var1.setline(40);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dont_skip$11, (PyObject)null);
      var5 = var1.getderef(0).__call__((ThreadState)var2, (PyObject)var1.getderef(2), (PyObject)PyString.fromInterned("testing")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_dont_skip", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_skip$10(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dont_skip$11(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_skip_class$12(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$13);
      var4 = var1.getglobal("unittest").__getattr__("skip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testing")).__call__(var2, var4);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(61);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var5);
      var3 = null;
      var1.setline(62);
      PyObject var6 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(63);
      var6 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(64);
      var6 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(3)})));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(65);
      var1.getlocal(4).__getattr__("run").__call__(var2, var1.getlocal(2));
      var1.setline(66);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("skipped"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(3), PyString.fromInterned("testing")})})));
      var1.setline(67);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(59);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = test_1$14;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("test_1", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$14(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_skip_non_unittest_class_old_style$15(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("Mixin", var3, Mixin$16);
      var4 = var1.getglobal("unittest").__getattr__("skip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testing")).__call__(var2, var4);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(74);
      var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Foo", var3, Foo$18);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(76);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var5);
      var3 = null;
      var1.setline(77);
      PyObject var6 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(78);
      var6 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(79);
      var6 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(4)})));
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(80);
      var1.getlocal(5).__getattr__("run").__call__(var2, var1.getlocal(3));
      var1.setline(81);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("skipped"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(4), PyString.fromInterned("testing")})})));
      var1.setline(82);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Mixin$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(72);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = test_1$17;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("test_1", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$17(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(75);
      return var1.getf_locals();
   }

   public PyObject test_skip_non_unittest_class_new_style$19(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyObject[] var3 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Mixin", var3, Mixin$20);
      var4 = var1.getglobal("unittest").__getattr__("skip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testing")).__call__(var2, var4);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(89);
      var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Foo", var3, Foo$22);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(91);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var5);
      var3 = null;
      var1.setline(92);
      PyObject var6 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(93);
      var6 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(94);
      var6 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(4)})));
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(95);
      var1.getlocal(5).__getattr__("run").__call__(var2, var1.getlocal(3));
      var1.setline(96);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("skipped"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(4), PyString.fromInterned("testing")})})));
      var1.setline(97);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Mixin$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(87);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = test_1$21;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("test_1", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$21(PyFrame var1, ThreadState var2) {
      var1.setline(88);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$22(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(90);
      return var1.getf_locals();
   }

   public PyObject test_expected_failure$23(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$24);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(104);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(105);
      PyObject var6 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(106);
      var6 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_die"));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(107);
      var1.getlocal(4).__getattr__("run").__call__(var2, var1.getlocal(3));
      var1.setline(108);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("addExpectedFailure"), PyString.fromInterned("stopTest")})));
      var1.setline(110);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("expectedFailures").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0)), var1.getlocal(4));
      var1.setline(111);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(3).__getattr__("wasSuccessful").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$24(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(101);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_die$25, (PyObject)null);
      PyObject var5 = var1.getname("unittest").__getattr__("expectedFailure").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_die", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_die$25(PyFrame var1, ThreadState var2) {
      var1.setline(103);
      var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("help me!"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_unexpected_success$26(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$27);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(118);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(119);
      PyObject var6 = var1.getglobal("LoggingResult").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(120);
      var6 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_die"));
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(121);
      var1.getlocal(4).__getattr__("run").__call__(var2, var1.getlocal(3));
      var1.setline(122);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("startTest"), PyString.fromInterned("addUnexpectedSuccess"), PyString.fromInterned("stopTest")})));
      var1.setline(124);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(3).__getattr__("failures"));
      var1.setline(125);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("unexpectedSuccesses"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(4)})));
      var1.setline(126);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(3).__getattr__("wasSuccessful").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$27(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(115);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_die$28, (PyObject)null);
      PyObject var5 = var1.getname("unittest").__getattr__("expectedFailure").__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_die", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_die$28(PyFrame var1, ThreadState var2) {
      var1.setline(117);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_skip_doesnt_run_setup$29(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyObject[] var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("Foo", var3, Foo$30);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(140);
      PyObject var5 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(141);
      var5 = var1.getderef(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(142);
      var5 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2)})));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(143);
      var1.getlocal(3).__getattr__("run").__call__(var2, var1.getlocal(1));
      var1.setline(144);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("skipped"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("testing")})})));
      var1.setline(145);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getderef(0).__getattr__("wasSetUp"));
      var1.setline(146);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getderef(0).__getattr__("wasTornDown"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Foo$30(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(130);
      PyObject var3 = var1.getname("False");
      var1.setlocal("wasSetUp", var3);
      var3 = null;
      var1.setline(131);
      var3 = var1.getname("False");
      var1.setlocal("wasTornDown", var3);
      var3 = null;
      var1.setline(132);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = setUp$31;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal("setUp", var5);
      var3 = null;
      var1.setline(134);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = tornDown$32;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal("tornDown", var5);
      var3 = null;
      var1.setline(136);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, test_1$33, (PyObject)null);
      var3 = var1.getname("unittest").__getattr__("skip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testing")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_1", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$31(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("wasSetUp", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tornDown$32(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("wasTornDown", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_1$33(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_decorated_skip$34(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, decorator$35, (PyObject)null);
      var1.setderef(0, var5);
      var3 = null;
      var1.setline(154);
      var3 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestCase")};
      PyCode var10002 = Foo$37;
      PyObject[] var4 = new PyObject[]{var1.getclosure(0)};
      PyObject var6 = Py.makeClass("Foo", var3, var10002, var4);
      var1.setlocal(1, var6);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(160);
      PyObject var7 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(161);
      var7 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_1"));
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(162);
      var7 = var1.getglobal("unittest").__getattr__("TestSuite").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(3)})));
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(163);
      var1.getlocal(4).__getattr__("run").__call__(var2, var1.getlocal(2));
      var1.setline(164);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("skipped"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(3), PyString.fromInterned("testing")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject decorator$35(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(150);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = inner$36;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(152);
      PyObject var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject inner$36(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyObject var10000 = var1.getderef(0);
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(0), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject Foo$37(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(155);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_1$38, (PyObject)null);
      PyObject var10000 = var1.getderef(0);
      PyObject var5 = var1.getname("unittest").__getattr__("skip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("testing")).__call__((ThreadState)var2, (PyObject)var4);
      var5 = var10000.__call__(var2, var5);
      var1.setlocal("test_1", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_1$38(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      var1.f_lasti = -1;
      return Py.None;
   }

   public test_skipping$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Test_TestSkipping$1 = Py.newCode(0, var2, var1, "Test_TestSkipping", 6, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "Foo", "events", "result", "test"};
      test_skipping$2 = Py.newCode(1, var2, var1, "test_skipping", 8, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$3 = Py.newCode(0, var2, var1, "Foo", 9, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_skip_me$4 = Py.newCode(1, var2, var1, "test_skip_me", 10, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$5 = Py.newCode(0, var2, var1, "Foo", 20, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$6 = Py.newCode(1, var2, var1, "setUp", 21, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_nothing$7 = Py.newCode(1, var2, var1, "test_nothing", 23, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "op_table", "Foo", "test_do_skip", "test_dont_skip", "suite", "events", "result", "expected", "do_skip", "dont_skip", "deco"};
      String[] var10001 = var2;
      test_skipping$py var10007 = self;
      var2 = new String[]{"do_skip", "dont_skip", "deco"};
      test_skipping_decorators$8 = Py.newCode(1, var10001, var1, "test_skipping_decorators", 32, false, false, var10007, 8, var2, (String[])null, 3, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"deco", "do_skip", "dont_skip"};
      Foo$9 = Py.newCode(0, var10001, var1, "Foo", 36, false, false, var10007, 9, (String[])null, var2, 0, 4096);
      var2 = new String[]{"self"};
      test_skip$10 = Py.newCode(1, var2, var1, "test_skip", 37, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_dont_skip$11 = Py.newCode(1, var2, var1, "test_dont_skip", 40, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "result", "test", "suite", "record"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"record"};
      test_skip_class$12 = Py.newCode(1, var10001, var1, "test_skip_class", 56, false, false, var10007, 12, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Foo$13 = Py.newCode(0, var2, var1, "Foo", 57, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"record"};
      test_1$14 = Py.newCode(1, var10001, var1, "test_1", 59, false, false, var10007, 14, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "Mixin", "Foo", "result", "test", "suite", "record"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"record"};
      test_skip_non_unittest_class_old_style$15 = Py.newCode(1, var10001, var1, "test_skip_non_unittest_class_old_style", 69, false, false, var10007, 15, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Mixin$16 = Py.newCode(0, var2, var1, "Mixin", 70, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"record"};
      test_1$17 = Py.newCode(1, var10001, var1, "test_1", 72, false, false, var10007, 17, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Foo$18 = Py.newCode(0, var2, var1, "Foo", 74, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "Mixin", "Foo", "result", "test", "suite", "record"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"record"};
      test_skip_non_unittest_class_new_style$19 = Py.newCode(1, var10001, var1, "test_skip_non_unittest_class_new_style", 84, false, false, var10007, 19, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Mixin$20 = Py.newCode(0, var2, var1, "Mixin", 85, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"record"};
      test_1$21 = Py.newCode(1, var10001, var1, "test_1", 87, false, false, var10007, 21, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Foo$22 = Py.newCode(0, var2, var1, "Foo", 89, false, false, self, 22, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "Foo", "events", "result", "test"};
      test_expected_failure$23 = Py.newCode(1, var2, var1, "test_expected_failure", 99, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$24 = Py.newCode(0, var2, var1, "Foo", 100, false, false, self, 24, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_die$25 = Py.newCode(1, var2, var1, "test_die", 101, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "events", "result", "test"};
      test_unexpected_success$26 = Py.newCode(1, var2, var1, "test_unexpected_success", 113, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Foo$27 = Py.newCode(0, var2, var1, "Foo", 114, false, false, self, 27, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_die$28 = Py.newCode(1, var2, var1, "test_die", 115, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "test", "suite", "Foo"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      test_skip_doesnt_run_setup$29 = Py.newCode(1, var10001, var1, "test_skip_doesnt_run_setup", 128, false, false, var10007, 29, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Foo$30 = Py.newCode(0, var2, var1, "Foo", 129, false, false, self, 30, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      setUp$31 = Py.newCode(1, var10001, var1, "setUp", 132, false, false, var10007, 31, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Foo"};
      tornDown$32 = Py.newCode(1, var10001, var1, "tornDown", 134, false, false, var10007, 32, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self"};
      test_1$33 = Py.newCode(1, var2, var1, "test_1", 136, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Foo", "result", "test", "suite", "decorator"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"decorator"};
      test_decorated_skip$34 = Py.newCode(1, var10001, var1, "test_decorated_skip", 148, false, false, var10007, 34, var2, (String[])null, 1, 4097);
      var2 = new String[]{"func", "inner"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"func"};
      decorator$35 = Py.newCode(1, var10001, var1, "decorator", 149, false, false, var10007, 35, var2, (String[])null, 0, 4097);
      var2 = new String[]{"a"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"func"};
      inner$36 = Py.newCode(1, var10001, var1, "inner", 150, true, false, var10007, 36, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"decorator"};
      Foo$37 = Py.newCode(0, var10001, var1, "Foo", 154, false, false, var10007, 37, (String[])null, var2, 0, 4096);
      var2 = new String[]{"self"};
      test_1$38 = Py.newCode(1, var2, var1, "test_1", 155, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_skipping$py("unittest/test/test_skipping$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_skipping$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Test_TestSkipping$1(var2, var3);
         case 2:
            return this.test_skipping$2(var2, var3);
         case 3:
            return this.Foo$3(var2, var3);
         case 4:
            return this.test_skip_me$4(var2, var3);
         case 5:
            return this.Foo$5(var2, var3);
         case 6:
            return this.setUp$6(var2, var3);
         case 7:
            return this.test_nothing$7(var2, var3);
         case 8:
            return this.test_skipping_decorators$8(var2, var3);
         case 9:
            return this.Foo$9(var2, var3);
         case 10:
            return this.test_skip$10(var2, var3);
         case 11:
            return this.test_dont_skip$11(var2, var3);
         case 12:
            return this.test_skip_class$12(var2, var3);
         case 13:
            return this.Foo$13(var2, var3);
         case 14:
            return this.test_1$14(var2, var3);
         case 15:
            return this.test_skip_non_unittest_class_old_style$15(var2, var3);
         case 16:
            return this.Mixin$16(var2, var3);
         case 17:
            return this.test_1$17(var2, var3);
         case 18:
            return this.Foo$18(var2, var3);
         case 19:
            return this.test_skip_non_unittest_class_new_style$19(var2, var3);
         case 20:
            return this.Mixin$20(var2, var3);
         case 21:
            return this.test_1$21(var2, var3);
         case 22:
            return this.Foo$22(var2, var3);
         case 23:
            return this.test_expected_failure$23(var2, var3);
         case 24:
            return this.Foo$24(var2, var3);
         case 25:
            return this.test_die$25(var2, var3);
         case 26:
            return this.test_unexpected_success$26(var2, var3);
         case 27:
            return this.Foo$27(var2, var3);
         case 28:
            return this.test_die$28(var2, var3);
         case 29:
            return this.test_skip_doesnt_run_setup$29(var2, var3);
         case 30:
            return this.Foo$30(var2, var3);
         case 31:
            return this.setUp$31(var2, var3);
         case 32:
            return this.tornDown$32(var2, var3);
         case 33:
            return this.test_1$33(var2, var3);
         case 34:
            return this.test_decorated_skip$34(var2, var3);
         case 35:
            return this.decorator$35(var2, var3);
         case 36:
            return this.inner$36(var2, var3);
         case 37:
            return this.Foo$37(var2, var3);
         case 38:
            return this.test_1$38(var2, var3);
         default:
            return null;
      }
   }
}
