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
@Filename("unittest/test/test_break.py")
public class test_break$py extends PyFunctionTable implements PyRunnable {
   static test_break$py self;
   static final PyCode f$0;
   static final PyCode TestBreak$1;
   static final PyCode setUp$2;
   static final PyCode tearDown$3;
   static final PyCode testInstallHandler$4;
   static final PyCode testRegisterResult$5;
   static final PyCode testInterruptCaught$6;
   static final PyCode test$7;
   static final PyCode testSecondInterrupt$8;
   static final PyCode test$9;
   static final PyCode testTwoResults$10;
   static final PyCode test$11;
   static final PyCode testHandlerReplacedButCalled$12;
   static final PyCode new_handler$13;
   static final PyCode testRunner$14;
   static final PyCode testWeakReferences$15;
   static final PyCode testRemoveResult$16;
   static final PyCode testMainInstallsHandler$17;
   static final PyCode FakeRunner$18;
   static final PyCode __init__$19;
   static final PyCode run$20;
   static final PyCode Program$21;
   static final PyCode __init__$22;
   static final PyCode testRemoveHandler$23;
   static final PyCode testRemoveHandlerAsDecorator$24;
   static final PyCode test$25;
   static final PyCode TestBreakDefaultIntHandler$26;
   static final PyCode TestBreakSignalIgnored$27;
   static final PyCode TestBreakSignalDefault$28;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("gc", var1, -1);
      var1.setlocal("gc", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("signal", var1, -1);
      var1.setlocal("signal", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("weakref", var1, -1);
      var1.setlocal("weakref", var3);
      var3 = null;
      var1.setline(7);
      String[] var6 = new String[]{"StringIO"};
      PyObject[] var7 = imp.importFrom("cStringIO", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(10);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(13);
      var7 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestBreak", var7, TestBreak$1);
      PyObject var10000 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("os"), (PyObject)PyString.fromInterned("kill")), (PyObject)PyString.fromInterned("Test requires os.kill"));
      PyObject var10001 = var1.getname("unittest").__getattr__("skipIf");
      PyObject var5 = var1.getname("sys").__getattr__("platform");
      PyObject var10003 = var5._eq(PyString.fromInterned("win32"));
      var5 = null;
      var10001 = var10001.__call__((ThreadState)var2, (PyObject)var10003, (PyObject)PyString.fromInterned("Test cannot run on Windows"));
      PyObject var10002 = var1.getname("unittest").__getattr__("skipIf");
      var5 = var1.getname("sys").__getattr__("platform");
      PyObject var10004 = var5._eq(PyString.fromInterned("freebsd6"));
      var5 = null;
      var4 = var10002.__call__((ThreadState)var2, (PyObject)var10004, (PyObject)PyString.fromInterned("Test kills regrtest on freebsd6 if threads have been used")).__call__(var2, var4);
      var4 = var10001.__call__(var2, var4);
      var4 = var10000.__call__(var2, var4);
      var1.setlocal("TestBreak", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(265);
      var7 = new PyObject[]{var1.getname("TestBreak")};
      var4 = Py.makeClass("TestBreakDefaultIntHandler", var7, TestBreakDefaultIntHandler$26);
      var10000 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("os"), (PyObject)PyString.fromInterned("kill")), (PyObject)PyString.fromInterned("Test requires os.kill"));
      var10001 = var1.getname("unittest").__getattr__("skipIf");
      var5 = var1.getname("sys").__getattr__("platform");
      var10003 = var5._eq(PyString.fromInterned("win32"));
      var5 = null;
      var10001 = var10001.__call__((ThreadState)var2, (PyObject)var10003, (PyObject)PyString.fromInterned("Test cannot run on Windows"));
      var10002 = var1.getname("unittest").__getattr__("skipIf");
      var5 = var1.getname("sys").__getattr__("platform");
      var10004 = var5._eq(PyString.fromInterned("freebsd6"));
      var5 = null;
      var4 = var10002.__call__((ThreadState)var2, (PyObject)var10004, (PyObject)PyString.fromInterned("Test kills regrtest on freebsd6 if threads have been used")).__call__(var2, var4);
      var4 = var10001.__call__(var2, var4);
      var4 = var10000.__call__(var2, var4);
      var1.setlocal("TestBreakDefaultIntHandler", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(272);
      var7 = new PyObject[]{var1.getname("TestBreak")};
      var4 = Py.makeClass("TestBreakSignalIgnored", var7, TestBreakSignalIgnored$27);
      var10000 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("os"), (PyObject)PyString.fromInterned("kill")), (PyObject)PyString.fromInterned("Test requires os.kill"));
      var10001 = var1.getname("unittest").__getattr__("skipIf");
      var5 = var1.getname("sys").__getattr__("platform");
      var10003 = var5._eq(PyString.fromInterned("win32"));
      var5 = null;
      var10001 = var10001.__call__((ThreadState)var2, (PyObject)var10003, (PyObject)PyString.fromInterned("Test cannot run on Windows"));
      var10002 = var1.getname("unittest").__getattr__("skipIf");
      var5 = var1.getname("sys").__getattr__("platform");
      var10004 = var5._eq(PyString.fromInterned("freebsd6"));
      var5 = null;
      var4 = var10002.__call__((ThreadState)var2, (PyObject)var10004, (PyObject)PyString.fromInterned("Test kills regrtest on freebsd6 if threads have been used")).__call__(var2, var4);
      var4 = var10001.__call__(var2, var4);
      var4 = var10000.__call__(var2, var4);
      var1.setlocal("TestBreakSignalIgnored", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(279);
      var7 = new PyObject[]{var1.getname("TestBreak")};
      var4 = Py.makeClass("TestBreakSignalDefault", var7, TestBreakSignalDefault$28);
      var10000 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("os"), (PyObject)PyString.fromInterned("kill")), (PyObject)PyString.fromInterned("Test requires os.kill"));
      var10001 = var1.getname("unittest").__getattr__("skipIf");
      var5 = var1.getname("sys").__getattr__("platform");
      var10003 = var5._eq(PyString.fromInterned("win32"));
      var5 = null;
      var10001 = var10001.__call__((ThreadState)var2, (PyObject)var10003, (PyObject)PyString.fromInterned("Test cannot run on Windows"));
      var10002 = var1.getname("unittest").__getattr__("skipIf");
      var5 = var1.getname("sys").__getattr__("platform");
      var10004 = var5._eq(PyString.fromInterned("freebsd6"));
      var5 = null;
      var4 = var10002.__call__((ThreadState)var2, (PyObject)var10004, (PyObject)PyString.fromInterned("Test kills regrtest on freebsd6 if threads have been used")).__call__(var2, var4);
      var4 = var10001.__call__(var2, var4);
      var4 = var10000.__call__(var2, var4);
      var1.setlocal("TestBreakSignalDefault", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestBreak$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(18);
      PyObject var3 = var1.getname("None");
      var1.setlocal("int_handler", var3);
      var3 = null;
      var1.setline(20);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, setUp$2, (PyObject)null);
      var1.setlocal("setUp", var5);
      var3 = null;
      var1.setline(25);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, tearDown$3, (PyObject)null);
      var1.setlocal("tearDown", var5);
      var3 = null;
      var1.setline(31);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testInstallHandler$4, (PyObject)null);
      var1.setlocal("testInstallHandler", var5);
      var3 = null;
      var1.setline(44);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testRegisterResult$5, (PyObject)null);
      var1.setlocal("testRegisterResult", var5);
      var3 = null;
      var1.setline(57);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testInterruptCaught$6, (PyObject)null);
      var1.setlocal("testInterruptCaught", var5);
      var3 = null;
      var1.setline(79);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testSecondInterrupt$8, (PyObject)null);
      var1.setlocal("testSecondInterrupt", var5);
      var3 = null;
      var1.setline(105);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testTwoResults$10, (PyObject)null);
      var1.setlocal("testTwoResults", var5);
      var3 = null;
      var1.setline(132);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testHandlerReplacedButCalled$12, (PyObject)null);
      var1.setlocal("testHandlerReplacedButCalled", var5);
      var3 = null;
      var1.setline(155);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testRunner$14, (PyObject)null);
      var1.setlocal("testRunner", var5);
      var3 = null;
      var1.setline(163);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testWeakReferences$15, (PyObject)null);
      var1.setlocal("testWeakReferences", var5);
      var3 = null;
      var1.setline(176);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testRemoveResult$16, (PyObject)null);
      var1.setlocal("testRemoveResult", var5);
      var3 = null;
      var1.setline(194);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testMainInstallsHandler$17, (PyObject)null);
      var1.setlocal("testMainInstallsHandler", var5);
      var3 = null;
      var1.setline(244);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testRemoveHandler$23, (PyObject)null);
      var1.setlocal("testRemoveHandler", var5);
      var3 = null;
      var1.setline(254);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, testRemoveHandlerAsDecorator$24, (PyObject)null);
      var1.setlocal("testRemoveHandlerAsDecorator", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$2(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
      var1.getlocal(0).__setattr__("_default_handler", var3);
      var3 = null;
      var1.setline(22);
      var3 = var1.getlocal(0).__getattr__("int_handler");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(23);
         var1.getglobal("signal").__getattr__("signal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"), var1.getlocal(0).__getattr__("int_handler"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$3(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      var1.getglobal("signal").__getattr__("signal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"), var1.getlocal(0).__getattr__("_default_handler"));
      var1.setline(27);
      PyObject var3 = var1.getglobal("weakref").__getattr__("WeakKeyDictionary").__call__(var2);
      var1.getglobal("unittest").__getattr__("signals").__setattr__("_results", var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getglobal("None");
      var1.getglobal("unittest").__getattr__("signals").__setattr__("_interrupt_handler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testInstallHandler$4(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyObject var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(33);
      var1.getglobal("unittest").__getattr__("installHandler").__call__(var2);
      var1.setline(34);
      var1.getlocal(0).__getattr__("assertNotEqual").__call__(var2, var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT")), var1.getlocal(1));

      try {
         var1.setline(37);
         var3 = var1.getglobal("os").__getattr__("getpid").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(38);
         var1.getglobal("os").__getattr__("kill").__call__(var2, var1.getlocal(2), var1.getglobal("signal").__getattr__("SIGINT"));
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(var1.getglobal("KeyboardInterrupt"))) {
            throw var5;
         }

         var1.setline(40);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("KeyboardInterrupt not handled"));
      }

      var1.setline(42);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("unittest").__getattr__("signals").__getattr__("_interrupt_handler").__getattr__("called"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testRegisterResult$5(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(46);
      var1.getglobal("unittest").__getattr__("registerResult").__call__(var2, var1.getlocal(1));
      var1.setline(48);
      var3 = var1.getglobal("unittest").__getattr__("signals").__getattr__("_results").__iter__();

      while(true) {
         var1.setline(48);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(54);
            var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("result not found"));
            break;
         }

         var1.setlocal(2, var4);
         var1.setline(49);
         PyObject var5 = var1.getlocal(2);
         PyObject var10000 = var5._is(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(51);
         var5 = var1.getlocal(2);
         var10000 = var5._isnot(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(52);
            var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("odd object in result set"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testInterruptCaught$6(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(58);
      PyObject var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(61);
      var1.getglobal("unittest").__getattr__("installHandler").__call__(var2);
      var1.setline(62);
      var1.getglobal("unittest").__getattr__("registerResult").__call__(var2, var1.getlocal(2));
      var1.setline(64);
      var1.getderef(0).__getattr__("assertNotEqual").__call__(var2, var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT")), var1.getlocal(1));
      var1.setline(66);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = test$7;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(3, var6);
      var3 = null;

      try {
         var1.setline(73);
         var1.getlocal(3).__call__(var2, var1.getlocal(2));
      } catch (Throwable var4) {
         PyException var7 = Py.setException(var4, var1);
         if (!var7.match(var1.getglobal("KeyboardInterrupt"))) {
            throw var7;
         }

         var1.setline(75);
         var1.getderef(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("KeyboardInterrupt not handled"));
      }

      var1.setline(76);
      var1.getderef(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(2).__getattr__("breakCaught"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$7(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyObject var3 = var1.getglobal("os").__getattr__("getpid").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(68);
      var1.getglobal("os").__getattr__("kill").__call__(var2, var1.getlocal(1), var1.getglobal("signal").__getattr__("SIGINT"));
      var1.setline(69);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("breakCaught", var3);
      var3 = null;
      var1.setline(70);
      var1.getderef(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(0).__getattr__("shouldStop"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testSecondInterrupt$8(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(82);
      PyObject var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
      PyObject var10000 = var3._eq(var1.getglobal("signal").__getattr__("SIG_IGN"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(83);
         var1.getderef(0).__getattr__("skipTest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test requires SIGINT to not be ignored"));
      }

      var1.setline(84);
      var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(85);
      var1.getglobal("unittest").__getattr__("installHandler").__call__(var2);
      var1.setline(86);
      var1.getglobal("unittest").__getattr__("registerResult").__call__(var2, var1.getlocal(1));
      var1.setline(88);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = test$9;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(2, var6);
      var3 = null;

      label21: {
         try {
            var1.setline(97);
            var1.getlocal(2).__call__(var2, var1.getlocal(1));
         } catch (Throwable var4) {
            PyException var7 = Py.setException(var4, var1);
            if (var7.match(var1.getglobal("KeyboardInterrupt"))) {
               var1.setline(99);
               break label21;
            }

            throw var7;
         }

         var1.setline(101);
         var1.getderef(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Second KeyboardInterrupt not raised"));
      }

      var1.setline(102);
      var1.getderef(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("breakCaught"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$9(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyObject var3 = var1.getglobal("os").__getattr__("getpid").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(90);
      var1.getglobal("os").__getattr__("kill").__call__(var2, var1.getlocal(1), var1.getglobal("signal").__getattr__("SIGINT"));
      var1.setline(91);
      var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("breakCaught", var3);
      var3 = null;
      var1.setline(92);
      var1.getderef(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(0).__getattr__("shouldStop"));
      var1.setline(93);
      var1.getglobal("os").__getattr__("kill").__call__(var2, var1.getlocal(1), var1.getglobal("signal").__getattr__("SIGINT"));
      var1.setline(94);
      var1.getderef(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Second KeyboardInterrupt not raised"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testTwoResults$10(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      var1.getglobal("unittest").__getattr__("installHandler").__call__(var2);
      var1.setline(108);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(109);
      var1.getglobal("unittest").__getattr__("registerResult").__call__(var2, var1.getlocal(1));
      var1.setline(110);
      var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(112);
      var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(113);
      var1.getglobal("unittest").__getattr__("registerResult").__call__(var2, var1.getlocal(3));
      var1.setline(114);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT")), var1.getlocal(2));
      var1.setline(116);
      var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(118);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, test$11, (PyObject)null);
      var1.setlocal(5, var6);
      var3 = null;

      try {
         var1.setline(123);
         var1.getlocal(5).__call__(var2, var1.getlocal(1));
      } catch (Throwable var4) {
         PyException var7 = Py.setException(var4, var1);
         if (!var7.match(var1.getglobal("KeyboardInterrupt"))) {
            throw var7;
         }

         var1.setline(125);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("KeyboardInterrupt not handled"));
      }

      var1.setline(127);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("shouldStop"));
      var1.setline(128);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(3).__getattr__("shouldStop"));
      var1.setline(129);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(4).__getattr__("shouldStop"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$11(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyObject var3 = var1.getglobal("os").__getattr__("getpid").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(120);
      var1.getglobal("os").__getattr__("kill").__call__(var2, var1.getlocal(1), var1.getglobal("signal").__getattr__("SIGINT"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testHandlerReplacedButCalled$12(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      PyObject var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
      PyObject var10000 = var3._eq(var1.getglobal("signal").__getattr__("SIG_IGN"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(136);
         var1.getlocal(0).__getattr__("skipTest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test requires SIGINT to not be ignored"));
      }

      var1.setline(140);
      var1.getglobal("unittest").__getattr__("installHandler").__call__(var2);
      var1.setline(142);
      var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(143);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = new_handler$13;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(145);
      var1.getglobal("signal").__getattr__("signal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"), var1.getlocal(1));

      label21: {
         try {
            var1.setline(148);
            var3 = var1.getglobal("os").__getattr__("getpid").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(149);
            var1.getglobal("os").__getattr__("kill").__call__(var2, var1.getlocal(2), var1.getglobal("signal").__getattr__("SIGINT"));
         } catch (Throwable var4) {
            PyException var7 = Py.setException(var4, var1);
            if (var7.match(var1.getglobal("KeyboardInterrupt"))) {
               var1.setline(151);
               break label21;
            }

            throw var7;
         }

         var1.setline(153);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("replaced but delegated handler doesn't raise interrupt"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject new_handler$13(PyFrame var1, ThreadState var2) {
      var1.setline(144);
      var1.getderef(0).__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testRunner$14(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("TextTestRunner");
      PyObject[] var3 = new PyObject[]{var1.getglobal("StringIO").__call__(var2)};
      String[] var4 = new String[]{"stream"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(160);
      var5 = var1.getlocal(1).__getattr__("run").__call__(var2, var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(161);
      var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getlocal(2), var1.getglobal("unittest").__getattr__("signals").__getattr__("_results"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testWeakReferences$15(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(166);
      var1.getglobal("unittest").__getattr__("registerResult").__call__(var2, var1.getlocal(1));
      var1.setline(168);
      var3 = var1.getglobal("weakref").__getattr__("ref").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(169);
      var1.dellocal(1);
      var1.setline(172);
      var1.getglobal("gc").__getattr__("collect").__call__(var2);
      var1.setline(172);
      var1.getglobal("gc").__getattr__("collect").__call__(var2);
      var1.setline(173);
      var1.getlocal(0).__getattr__("assertIsNone").__call__(var2, var1.getlocal(2).__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testRemoveResult$16(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestResult").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(178);
      var1.getglobal("unittest").__getattr__("registerResult").__call__(var2, var1.getlocal(1));
      var1.setline(180);
      var1.getglobal("unittest").__getattr__("installHandler").__call__(var2);
      var1.setline(181);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("unittest").__getattr__("removeResult").__call__(var2, var1.getlocal(1)));
      var1.setline(184);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("unittest").__getattr__("removeResult").__call__(var2, var1.getglobal("unittest").__getattr__("TestResult").__call__(var2)));

      try {
         var1.setline(187);
         var3 = var1.getglobal("os").__getattr__("getpid").__call__(var2);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(188);
         var1.getglobal("os").__getattr__("kill").__call__(var2, var1.getlocal(2), var1.getglobal("signal").__getattr__("SIGINT"));
      } catch (Throwable var4) {
         PyException var5 = Py.setException(var4, var1);
         if (!var5.match(var1.getglobal("KeyboardInterrupt"))) {
            throw var5;
         }

         var1.setline(190);
      }

      var1.setline(192);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(1).__getattr__("shouldStop"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testMainInstallsHandler$17(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(196);
      var3 = var1.getglobal("object").__call__(var2);
      var1.setderef(4, var3);
      var3 = null;
      var1.setline(197);
      var3 = var1.getglobal("object").__call__(var2);
      var1.setderef(3, var3);
      var3 = null;
      var1.setline(198);
      var3 = var1.getglobal("object").__call__(var2);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(199);
      var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(201);
      PyObject[] var5 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("FakeRunner", var5, FakeRunner$18);
      var1.setderef(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(210);
      var5 = new PyObject[]{var1.getglobal("unittest").__getattr__("TestProgram")};
      var4 = Py.makeClass("Program", var5, Program$21);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(220);
      var3 = var1.getlocal(2).__call__(var2, var1.getglobal("False"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(221);
      var1.getlocal(3).__getattr__("runTests").__call__(var2);
      var1.setline(223);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(2).__getattr__("initArgs"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(Py.EmptyObjects), new PyDictionary(new PyObject[]{PyString.fromInterned("buffer"), var1.getglobal("None"), PyString.fromInterned("verbosity"), var1.getderef(3), PyString.fromInterned("failfast"), var1.getderef(1)})})})));
      var1.setline(226);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(2).__getattr__("runArgs"), (PyObject)(new PyList(new PyObject[]{var1.getderef(4)})));
      var1.setline(227);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("result"), var1.getderef(0));
      var1.setline(229);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT")), var1.getlocal(1));
      var1.setline(231);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.getderef(2).__setattr__((String)"initArgs", var6);
      var3 = null;
      var1.setline(232);
      var6 = new PyList(Py.EmptyObjects);
      var1.getderef(2).__setattr__((String)"runArgs", var6);
      var3 = null;
      var1.setline(233);
      var3 = var1.getlocal(2).__call__(var2, var1.getglobal("True"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(234);
      var1.getlocal(3).__getattr__("runTests").__call__(var2);
      var1.setline(236);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(2).__getattr__("initArgs"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{new PyTuple(Py.EmptyObjects), new PyDictionary(new PyObject[]{PyString.fromInterned("buffer"), var1.getglobal("None"), PyString.fromInterned("verbosity"), var1.getderef(3), PyString.fromInterned("failfast"), var1.getderef(1)})})})));
      var1.setline(239);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(2).__getattr__("runArgs"), (PyObject)(new PyList(new PyObject[]{var1.getderef(4)})));
      var1.setline(240);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("result"), var1.getderef(0));
      var1.setline(242);
      var1.getlocal(0).__getattr__("assertNotEqual").__call__(var2, var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT")), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FakeRunner$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(202);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("initArgs", var3);
      var3 = null;
      var1.setline(203);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("runArgs", var3);
      var3 = null;
      var1.setline(204);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$19, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(206);
      var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = run$20;
      var4 = new PyObject[]{var1.f_back.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal("run", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$19(PyFrame var1, ThreadState var2) {
      var1.setline(205);
      var1.getlocal(0).__getattr__("initArgs").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$20(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      var1.getlocal(0).__getattr__("runArgs").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(208);
      PyObject var3 = var1.getderef(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Program$21(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(211);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = __init__$22;
      var3 = new PyObject[]{var1.f_back.getclosure(3), var1.f_back.getclosure(1), var1.f_back.getclosure(2), var1.f_back.getclosure(4)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$22(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyObject var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("exit", var3);
      var3 = null;
      var1.setline(213);
      var3 = var1.getderef(0);
      var1.getlocal(0).__setattr__("verbosity", var3);
      var3 = null;
      var1.setline(214);
      var3 = var1.getderef(1);
      var1.getlocal(0).__setattr__("failfast", var3);
      var3 = null;
      var1.setline(215);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("catchbreak", var3);
      var3 = null;
      var1.setline(216);
      var3 = var1.getderef(2);
      var1.getlocal(0).__setattr__("testRunner", var3);
      var3 = null;
      var1.setline(217);
      var3 = var1.getderef(3);
      var1.getlocal(0).__setattr__("test", var3);
      var3 = null;
      var1.setline(218);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("result", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testRemoveHandler$23(PyFrame var1, ThreadState var2) {
      var1.setline(245);
      PyObject var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(246);
      var1.getglobal("unittest").__getattr__("installHandler").__call__(var2);
      var1.setline(247);
      var1.getglobal("unittest").__getattr__("removeHandler").__call__(var2);
      var1.setline(248);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT")), var1.getlocal(1));
      var1.setline(251);
      var1.getglobal("unittest").__getattr__("removeHandler").__call__(var2);
      var1.setline(252);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT")), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testRemoveHandlerAsDecorator$24(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 1);
      var1.setline(255);
      PyObject var3 = var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT"));
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(256);
      var1.getglobal("unittest").__getattr__("installHandler").__call__(var2);
      var1.setline(258);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = test$25;
      var4 = new PyObject[]{var1.getclosure(1), var1.getclosure(0)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var3 = var1.getglobal("unittest").__getattr__("removeHandler").__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(262);
      var1.getlocal(1).__call__(var2);
      var1.setline(263);
      var1.getderef(1).__getattr__("assertNotEqual").__call__(var2, var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT")), var1.getderef(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$25(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("signal").__getattr__("getsignal").__call__(var2, var1.getglobal("signal").__getattr__("SIGINT")), var1.getderef(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestBreakDefaultIntHandler$26(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(270);
      PyObject var3 = var1.getname("signal").__getattr__("default_int_handler");
      var1.setlocal("int_handler", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject TestBreakSignalIgnored$27(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(277);
      PyObject var3 = var1.getname("signal").__getattr__("SIG_IGN");
      var1.setlocal("int_handler", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject TestBreakSignalDefault$28(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(284);
      PyObject var3 = var1.getname("signal").__getattr__("SIG_DFL");
      var1.setlocal("int_handler", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public test_break$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestBreak$1 = Py.newCode(0, var2, var1, "TestBreak", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$2 = Py.newCode(1, var2, var1, "setUp", 20, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$3 = Py.newCode(1, var2, var1, "tearDown", 25, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "default_handler", "pid"};
      testInstallHandler$4 = Py.newCode(1, var2, var1, "testInstallHandler", 31, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "ref"};
      testRegisterResult$5 = Py.newCode(1, var2, var1, "testRegisterResult", 44, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "default_handler", "result", "test"};
      String[] var10001 = var2;
      test_break$py var10007 = self;
      var2 = new String[]{"self"};
      testInterruptCaught$6 = Py.newCode(1, var10001, var1, "testInterruptCaught", 57, false, false, var10007, 6, var2, (String[])null, 0, 4097);
      var2 = new String[]{"result", "pid"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      test$7 = Py.newCode(1, var10001, var1, "test", 66, false, false, var10007, 7, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "result", "test"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      testSecondInterrupt$8 = Py.newCode(1, var10001, var1, "testSecondInterrupt", 79, false, false, var10007, 8, var2, (String[])null, 0, 4097);
      var2 = new String[]{"result", "pid"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      test$9 = Py.newCode(1, var10001, var1, "test", 88, false, false, var10007, 9, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "result", "new_handler", "result2", "result3", "test"};
      testTwoResults$10 = Py.newCode(1, var2, var1, "testTwoResults", 105, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"result", "pid"};
      test$11 = Py.newCode(1, var2, var1, "test", 118, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "new_handler", "pid", "handler"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"handler"};
      testHandlerReplacedButCalled$12 = Py.newCode(1, var10001, var1, "testHandlerReplacedButCalled", 132, false, false, var10007, 12, var2, (String[])null, 1, 4097);
      var2 = new String[]{"frame", "signum"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"handler"};
      new_handler$13 = Py.newCode(2, var10001, var1, "new_handler", 143, false, false, var10007, 13, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "runner", "result"};
      testRunner$14 = Py.newCode(1, var2, var1, "testRunner", 155, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "ref"};
      testWeakReferences$15 = Py.newCode(1, var2, var1, "testWeakReferences", 163, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "result", "pid"};
      testRemoveResult$16 = Py.newCode(1, var2, var1, "testRemoveResult", 176, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "default_handler", "Program", "p", "result", "failfast", "FakeRunner", "verbosity", "test"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"result", "failfast", "FakeRunner", "verbosity", "test"};
      testMainInstallsHandler$17 = Py.newCode(1, var10001, var1, "testMainInstallsHandler", 194, false, false, var10007, 17, var2, (String[])null, 5, 4097);
      var2 = new String[0];
      FakeRunner$18 = Py.newCode(0, var2, var1, "FakeRunner", 201, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "kwargs"};
      __init__$19 = Py.newCode(3, var2, var1, "__init__", 204, true, true, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"result"};
      run$20 = Py.newCode(2, var10001, var1, "run", 206, false, false, var10007, 20, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Program$21 = Py.newCode(0, var2, var1, "Program", 210, false, false, self, 21, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "catchbreak"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"verbosity", "failfast", "FakeRunner", "test"};
      __init__$22 = Py.newCode(2, var10001, var1, "__init__", 211, false, false, var10007, 22, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "default_handler"};
      testRemoveHandler$23 = Py.newCode(1, var2, var1, "testRemoveHandler", 244, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test", "default_handler"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"default_handler", "self"};
      testRemoveHandlerAsDecorator$24 = Py.newCode(1, var10001, var1, "testRemoveHandlerAsDecorator", 254, false, false, var10007, 24, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "default_handler"};
      test$25 = Py.newCode(0, var10001, var1, "test", 258, false, false, var10007, 25, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      TestBreakDefaultIntHandler$26 = Py.newCode(0, var2, var1, "TestBreakDefaultIntHandler", 265, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestBreakSignalIgnored$27 = Py.newCode(0, var2, var1, "TestBreakSignalIgnored", 272, false, false, self, 27, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestBreakSignalDefault$28 = Py.newCode(0, var2, var1, "TestBreakSignalDefault", 279, false, false, self, 28, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_break$py("unittest/test/test_break$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_break$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestBreak$1(var2, var3);
         case 2:
            return this.setUp$2(var2, var3);
         case 3:
            return this.tearDown$3(var2, var3);
         case 4:
            return this.testInstallHandler$4(var2, var3);
         case 5:
            return this.testRegisterResult$5(var2, var3);
         case 6:
            return this.testInterruptCaught$6(var2, var3);
         case 7:
            return this.test$7(var2, var3);
         case 8:
            return this.testSecondInterrupt$8(var2, var3);
         case 9:
            return this.test$9(var2, var3);
         case 10:
            return this.testTwoResults$10(var2, var3);
         case 11:
            return this.test$11(var2, var3);
         case 12:
            return this.testHandlerReplacedButCalled$12(var2, var3);
         case 13:
            return this.new_handler$13(var2, var3);
         case 14:
            return this.testRunner$14(var2, var3);
         case 15:
            return this.testWeakReferences$15(var2, var3);
         case 16:
            return this.testRemoveResult$16(var2, var3);
         case 17:
            return this.testMainInstallsHandler$17(var2, var3);
         case 18:
            return this.FakeRunner$18(var2, var3);
         case 19:
            return this.__init__$19(var2, var3);
         case 20:
            return this.run$20(var2, var3);
         case 21:
            return this.Program$21(var2, var3);
         case 22:
            return this.__init__$22(var2, var3);
         case 23:
            return this.testRemoveHandler$23(var2, var3);
         case 24:
            return this.testRemoveHandlerAsDecorator$24(var2, var3);
         case 25:
            return this.test$25(var2, var3);
         case 26:
            return this.TestBreakDefaultIntHandler$26(var2, var3);
         case 27:
            return this.TestBreakSignalIgnored$27(var2, var3);
         case 28:
            return this.TestBreakSignalDefault$28(var2, var3);
         default:
            return null;
      }
   }
}
