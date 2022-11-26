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
@Filename("unittest/test/support.py")
public class support$py extends PyFunctionTable implements PyRunnable {
   static support$py self;
   static final PyCode f$0;
   static final PyCode TestHashing$1;
   static final PyCode test_hash$2;
   static final PyCode TestEquality$3;
   static final PyCode test_eq$4;
   static final PyCode test_ne$5;
   static final PyCode LoggingResult$6;
   static final PyCode __init__$7;
   static final PyCode startTest$8;
   static final PyCode startTestRun$9;
   static final PyCode stopTest$10;
   static final PyCode stopTestRun$11;
   static final PyCode addFailure$12;
   static final PyCode addSuccess$13;
   static final PyCode addError$14;
   static final PyCode addSkip$15;
   static final PyCode addExpectedFailure$16;
   static final PyCode addUnexpectedSuccess$17;
   static final PyCode ResultWithNoStartTestRunStopTestRun$18;
   static final PyCode __init__$19;
   static final PyCode startTest$20;
   static final PyCode stopTest$21;
   static final PyCode addError$22;
   static final PyCode addFailure$23;
   static final PyCode addSuccess$24;
   static final PyCode wasSuccessful$25;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(4);
      PyObject[] var5 = new PyObject[]{var1.getname("object")};
      PyObject var4 = Py.makeClass("TestHashing", var5, TestHashing$1);
      var1.setlocal("TestHashing", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(29);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestEquality", var5, TestEquality$3);
      var1.setlocal("TestEquality", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(45);
      var5 = new PyObject[]{var1.getname("unittest").__getattr__("TestResult")};
      var4 = Py.makeClass("LoggingResult", var5, LoggingResult$6);
      var1.setlocal("LoggingResult", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(91);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("ResultWithNoStartTestRunStopTestRun", var5, ResultWithNoStartTestRunStopTestRun$18);
      var1.setlocal("ResultWithNoStartTestRunStopTestRun", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestHashing$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Used as a mixin for TestCase"));
      var1.setline(5);
      PyString.fromInterned("Used as a mixin for TestCase");
      var1.setline(8);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_hash$2, (PyObject)null);
      var1.setlocal("test_hash", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_hash$2(PyFrame var1, ThreadState var2) {
      var1.setline(9);
      PyObject var3 = var1.getlocal(0).__getattr__("eq_pairs").__iter__();

      while(true) {
         var1.setline(9);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         PyObject[] var5;
         PyObject var6;
         PyException var9;
         PyObject var10;
         if (var4 == null) {
            var1.setline(18);
            var3 = var1.getlocal(0).__getattr__("ne_pairs").__iter__();

            while(true) {
               var1.setline(18);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(1, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(2, var6);
               var6 = null;

               try {
                  var1.setline(20);
                  var10 = var1.getglobal("hash").__call__(var2, var1.getlocal(1));
                  var10000 = var10._eq(var1.getglobal("hash").__call__(var2, var1.getlocal(2)));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(21);
                     var1.getlocal(0).__getattr__("fail").__call__(var2, PyString.fromInterned("%s and %s hash equal, but shouldn't")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
                  }
               } catch (Throwable var7) {
                  var9 = Py.setException(var7, var1);
                  if (var9.match(var1.getglobal("KeyboardInterrupt"))) {
                     var1.setline(24);
                     throw Py.makeException();
                  }

                  if (!var9.match(var1.getglobal("Exception"))) {
                     throw var9;
                  }

                  var6 = var9.value;
                  var1.setlocal(3, var6);
                  var6 = null;
                  var1.setline(26);
                  var1.getlocal(0).__getattr__("fail").__call__(var2, PyString.fromInterned("Problem hashing %s and %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)})));
               }
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;

         try {
            var1.setline(11);
            var10 = var1.getglobal("hash").__call__(var2, var1.getlocal(1));
            var10000 = var10._eq(var1.getglobal("hash").__call__(var2, var1.getlocal(2)));
            var5 = null;
            if (var10000.__not__().__nonzero__()) {
               var1.setline(12);
               var1.getlocal(0).__getattr__("fail").__call__(var2, PyString.fromInterned("%r and %r do not hash equal")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})));
            }
         } catch (Throwable var8) {
            var9 = Py.setException(var8, var1);
            if (var9.match(var1.getglobal("KeyboardInterrupt"))) {
               var1.setline(14);
               throw Py.makeException();
            }

            if (!var9.match(var1.getglobal("Exception"))) {
               throw var9;
            }

            var6 = var9.value;
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(16);
            var1.getlocal(0).__getattr__("fail").__call__(var2, PyString.fromInterned("Problem hashing %r and %r: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)})));
         }
      }
   }

   public PyObject TestEquality$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Used as a mixin for TestCase"));
      var1.setline(30);
      PyString.fromInterned("Used as a mixin for TestCase");
      var1.setline(33);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_eq$4, (PyObject)null);
      var1.setlocal("test_eq", var4);
      var3 = null;
      var1.setline(39);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_ne$5, (PyObject)null);
      var1.setlocal("test_ne", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_eq$4(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      PyObject var3 = var1.getlocal(0).__getattr__("eq_pairs").__iter__();

      while(true) {
         var1.setline(34);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(35);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setline(36);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      }
   }

   public PyObject test_ne$5(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyObject var3 = var1.getlocal(0).__getattr__("ne_pairs").__iter__();

      while(true) {
         var1.setline(40);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(41);
         var1.getlocal(0).__getattr__("assertNotEqual").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setline(42);
         var1.getlocal(0).__getattr__("assertNotEqual").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      }
   }

   public PyObject LoggingResult$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(46);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$7, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(50);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startTest$8, (PyObject)null);
      var1.setlocal("startTest", var4);
      var3 = null;
      var1.setline(54);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startTestRun$9, (PyObject)null);
      var1.setlocal("startTestRun", var4);
      var3 = null;
      var1.setline(58);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, stopTest$10, (PyObject)null);
      var1.setlocal("stopTest", var4);
      var3 = null;
      var1.setline(62);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, stopTestRun$11, (PyObject)null);
      var1.setlocal("stopTestRun", var4);
      var3 = null;
      var1.setline(66);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addFailure$12, (PyObject)null);
      var1.setlocal("addFailure", var4);
      var3 = null;
      var1.setline(70);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addSuccess$13, (PyObject)null);
      var1.setlocal("addSuccess", var4);
      var3 = null;
      var1.setline(74);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addError$14, (PyObject)null);
      var1.setlocal("addError", var4);
      var3 = null;
      var1.setline(78);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addSkip$15, (PyObject)null);
      var1.setlocal("addSkip", var4);
      var3 = null;
      var1.setline(82);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addExpectedFailure$16, (PyObject)null);
      var1.setlocal("addExpectedFailure", var4);
      var3 = null;
      var1.setline(86);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addUnexpectedSuccess$17, (PyObject)null);
      var1.setlocal("addUnexpectedSuccess", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$7(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_events", var3);
      var3 = null;
      var1.setline(48);
      var1.getglobal("super").__call__(var2, var1.getglobal("LoggingResult"), var1.getlocal(0)).__getattr__("__init__").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startTest$8(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      var1.getlocal(0).__getattr__("_events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("startTest"));
      var1.setline(52);
      var1.getglobal("super").__call__(var2, var1.getglobal("LoggingResult"), var1.getlocal(0)).__getattr__("startTest").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startTestRun$9(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      var1.getlocal(0).__getattr__("_events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("startTestRun"));
      var1.setline(56);
      var1.getglobal("super").__call__(var2, var1.getglobal("LoggingResult"), var1.getlocal(0)).__getattr__("startTestRun").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject stopTest$10(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      var1.getlocal(0).__getattr__("_events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("stopTest"));
      var1.setline(60);
      var1.getglobal("super").__call__(var2, var1.getglobal("LoggingResult"), var1.getlocal(0)).__getattr__("stopTest").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject stopTestRun$11(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      var1.getlocal(0).__getattr__("_events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("stopTestRun"));
      var1.setline(64);
      var1.getglobal("super").__call__(var2, var1.getglobal("LoggingResult"), var1.getlocal(0)).__getattr__("stopTestRun").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addFailure$12(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      var1.getlocal(0).__getattr__("_events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("addFailure"));
      var1.setline(68);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("LoggingResult"), var1.getlocal(0)).__getattr__("addFailure");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addSuccess$13(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      var1.getlocal(0).__getattr__("_events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("addSuccess"));
      var1.setline(72);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("LoggingResult"), var1.getlocal(0)).__getattr__("addSuccess");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addError$14(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      var1.getlocal(0).__getattr__("_events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("addError"));
      var1.setline(76);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("LoggingResult"), var1.getlocal(0)).__getattr__("addError");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addSkip$15(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      var1.getlocal(0).__getattr__("_events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("addSkip"));
      var1.setline(80);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("LoggingResult"), var1.getlocal(0)).__getattr__("addSkip");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addExpectedFailure$16(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      var1.getlocal(0).__getattr__("_events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("addExpectedFailure"));
      var1.setline(84);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("LoggingResult"), var1.getlocal(0)).__getattr__("addExpectedFailure");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addUnexpectedSuccess$17(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      var1.getlocal(0).__getattr__("_events").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("addUnexpectedSuccess"));
      var1.setline(88);
      PyObject var10000 = var1.getglobal("super").__call__(var2, var1.getglobal("LoggingResult"), var1.getlocal(0)).__getattr__("addUnexpectedSuccess");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ResultWithNoStartTestRunStopTestRun$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("An object honouring TestResult before startTestRun/stopTestRun."));
      var1.setline(92);
      PyString.fromInterned("An object honouring TestResult before startTestRun/stopTestRun.");
      var1.setline(94);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$19, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(103);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, startTest$20, (PyObject)null);
      var1.setlocal("startTest", var4);
      var3 = null;
      var1.setline(106);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, stopTest$21, (PyObject)null);
      var1.setlocal("stopTest", var4);
      var3 = null;
      var1.setline(109);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addError$22, (PyObject)null);
      var1.setlocal("addError", var4);
      var3 = null;
      var1.setline(112);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addFailure$23, (PyObject)null);
      var1.setlocal("addFailure", var4);
      var3 = null;
      var1.setline(115);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, addSuccess$24, (PyObject)null);
      var1.setlocal("addSuccess", var4);
      var3 = null;
      var1.setline(118);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, wasSuccessful$25, (PyObject)null);
      var1.setlocal("wasSuccessful", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$19(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"failures", var3);
      var3 = null;
      var1.setline(96);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"errors", var3);
      var3 = null;
      var1.setline(97);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"testsRun", var4);
      var3 = null;
      var1.setline(98);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"skipped", var3);
      var3 = null;
      var1.setline(99);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"expectedFailures", var3);
      var3 = null;
      var1.setline(100);
      var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"unexpectedSuccesses", var3);
      var3 = null;
      var1.setline(101);
      PyObject var5 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("shouldStop", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject startTest$20(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject stopTest$21(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addError$22(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addFailure$23(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject addSuccess$24(PyFrame var1, ThreadState var2) {
      var1.setline(116);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject wasSuccessful$25(PyFrame var1, ThreadState var2) {
      var1.setline(119);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public support$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestHashing$1 = Py.newCode(0, var2, var1, "TestHashing", 4, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "obj_1", "obj_2", "e"};
      test_hash$2 = Py.newCode(1, var2, var1, "test_hash", 8, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestEquality$3 = Py.newCode(0, var2, var1, "TestEquality", 29, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "obj_1", "obj_2"};
      test_eq$4 = Py.newCode(1, var2, var1, "test_eq", 33, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj_1", "obj_2"};
      test_ne$5 = Py.newCode(1, var2, var1, "test_ne", 39, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      LoggingResult$6 = Py.newCode(0, var2, var1, "LoggingResult", 45, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "log"};
      __init__$7 = Py.newCode(2, var2, var1, "__init__", 46, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      startTest$8 = Py.newCode(2, var2, var1, "startTest", 50, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      startTestRun$9 = Py.newCode(1, var2, var1, "startTestRun", 54, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      stopTest$10 = Py.newCode(2, var2, var1, "stopTest", 58, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      stopTestRun$11 = Py.newCode(1, var2, var1, "stopTestRun", 62, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      addFailure$12 = Py.newCode(2, var2, var1, "addFailure", 66, true, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      addSuccess$13 = Py.newCode(2, var2, var1, "addSuccess", 70, true, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      addError$14 = Py.newCode(2, var2, var1, "addError", 74, true, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      addSkip$15 = Py.newCode(2, var2, var1, "addSkip", 78, true, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      addExpectedFailure$16 = Py.newCode(2, var2, var1, "addExpectedFailure", 82, true, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args"};
      addUnexpectedSuccess$17 = Py.newCode(2, var2, var1, "addUnexpectedSuccess", 86, true, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ResultWithNoStartTestRunStopTestRun$18 = Py.newCode(0, var2, var1, "ResultWithNoStartTestRunStopTestRun", 91, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __init__$19 = Py.newCode(1, var2, var1, "__init__", 94, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      startTest$20 = Py.newCode(2, var2, var1, "startTest", 103, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      stopTest$21 = Py.newCode(2, var2, var1, "stopTest", 106, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      addError$22 = Py.newCode(2, var2, var1, "addError", 109, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      addFailure$23 = Py.newCode(2, var2, var1, "addFailure", 112, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      addSuccess$24 = Py.newCode(2, var2, var1, "addSuccess", 115, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      wasSuccessful$25 = Py.newCode(1, var2, var1, "wasSuccessful", 118, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new support$py("unittest/test/support$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(support$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestHashing$1(var2, var3);
         case 2:
            return this.test_hash$2(var2, var3);
         case 3:
            return this.TestEquality$3(var2, var3);
         case 4:
            return this.test_eq$4(var2, var3);
         case 5:
            return this.test_ne$5(var2, var3);
         case 6:
            return this.LoggingResult$6(var2, var3);
         case 7:
            return this.__init__$7(var2, var3);
         case 8:
            return this.startTest$8(var2, var3);
         case 9:
            return this.startTestRun$9(var2, var3);
         case 10:
            return this.stopTest$10(var2, var3);
         case 11:
            return this.stopTestRun$11(var2, var3);
         case 12:
            return this.addFailure$12(var2, var3);
         case 13:
            return this.addSuccess$13(var2, var3);
         case 14:
            return this.addError$14(var2, var3);
         case 15:
            return this.addSkip$15(var2, var3);
         case 16:
            return this.addExpectedFailure$16(var2, var3);
         case 17:
            return this.addUnexpectedSuccess$17(var2, var3);
         case 18:
            return this.ResultWithNoStartTestRunStopTestRun$18(var2, var3);
         case 19:
            return this.__init__$19(var2, var3);
         case 20:
            return this.startTest$20(var2, var3);
         case 21:
            return this.stopTest$21(var2, var3);
         case 22:
            return this.addError$22(var2, var3);
         case 23:
            return this.addFailure$23(var2, var3);
         case 24:
            return this.addSuccess$24(var2, var3);
         case 25:
            return this.wasSuccessful$25(var2, var3);
         default:
            return null;
      }
   }
}
