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
@Filename("unittest/test/test_program.py")
public class test_program$py extends PyFunctionTable implements PyRunnable {
   static test_program$py self;
   static final PyCode f$0;
   static final PyCode Test_TestProgram$1;
   static final PyCode test_discovery_from_dotted_path$2;
   static final PyCode _find_tests$3;
   static final PyCode testNoExit$4;
   static final PyCode FakeRunner$5;
   static final PyCode run$6;
   static final PyCode restoreParseArgs$7;
   static final PyCode f$8;
   static final PyCode removeTest$9;
   static final PyCode FooBar$10;
   static final PyCode testPass$11;
   static final PyCode testFail$12;
   static final PyCode FooBarLoader$13;
   static final PyCode loadTestsFromModule$14;
   static final PyCode test_NonExit$15;
   static final PyCode test_Exit$16;
   static final PyCode test_ExitAsDefault$17;
   static final PyCode InitialisableProgram$18;
   static final PyCode __init__$19;
   static final PyCode FakeRunner$20;
   static final PyCode __init__$21;
   static final PyCode run$22;
   static final PyCode TestCommandLineArgs$23;
   static final PyCode setUp$24;
   static final PyCode f$25;
   static final PyCode testHelpAndUnknown$26;
   static final PyCode usageExit$27;
   static final PyCode testVerbosity$28;
   static final PyCode testBufferCatchFailfast$29;
   static final PyCode testRunTestsRunnerClass$30;
   static final PyCode testRunTestsRunnerInstance$31;
   static final PyCode testRunTestsOldRunnerClass$32;
   static final PyCode testCatchBreakInstallsHandler$33;
   static final PyCode restore$34;
   static final PyCode fakeInstallHandler$35;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      String[] var3 = new String[]{"StringIO"};
      PyObject[] var5 = imp.importFrom("cStringIO", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(3);
      PyObject var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var1.setline(4);
      var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(5);
      var6 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var6);
      var3 = null;
      var1.setline(8);
      var5 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("Test_TestProgram", var5, Test_TestProgram$1);
      var1.setlocal("Test_TestProgram", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(95);
      var5 = new PyObject[]{var1.getname("unittest").__getattr__("TestProgram")};
      var4 = Py.makeClass("InitialisableProgram", var5, InitialisableProgram$18);
      var1.setlocal("InitialisableProgram", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(107);
      var6 = var1.getname("object").__call__(var2);
      var1.setlocal("RESULT", var6);
      var3 = null;
      var1.setline(109);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("FakeRunner", var5, FakeRunner$20);
      var1.setlocal("FakeRunner", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(124);
      var5 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestCommandLineArgs", var5, TestCommandLineArgs$23);
      var1.setlocal("TestCommandLineArgs", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(254);
      var6 = var1.getname("__name__");
      PyObject var10000 = var6._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(255);
         var1.getname("unittest").__getattr__("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Test_TestProgram$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(10);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, test_discovery_from_dotted_path$2, (PyObject)null);
      var1.setlocal("test_discovery_from_dotted_path", var5);
      var3 = null;
      var1.setline(27);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, testNoExit$4, (PyObject)null);
      var1.setlocal("testNoExit", var5);
      var3 = null;
      var1.setline(55);
      var3 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("FooBar", var3, FooBar$10);
      var1.setlocal("FooBar", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(61);
      var3 = new PyObject[]{var1.getname("unittest").__getattr__("TestLoader")};
      var4 = Py.makeClass("FooBarLoader", var3, FooBarLoader$13);
      var1.setlocal("FooBarLoader", var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_NonExit$15, (PyObject)null);
      var1.setlocal("test_NonExit", var5);
      var3 = null;
      var1.setline(76);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_Exit$16, (PyObject)null);
      var1.setlocal("test_Exit", var5);
      var3 = null;
      var1.setline(86);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_ExitAsDefault$17, (PyObject)null);
      var1.setlocal("test_ExitAsDefault", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_discovery_from_dotted_path$2(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(11);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(13);
      PyList var4 = new PyList(new PyObject[]{var1.getderef(0)});
      var1.setderef(1, var4);
      var3 = null;
      var1.setline(14);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("unittest").__getattr__("test").__getattr__("__file__")));
      var1.setderef(2, var3);
      var3 = null;
      var1.setline(16);
      var3 = var1.getglobal("False");
      var1.getderef(0).__setattr__("wasRun", var3);
      var3 = null;
      var1.setline(17);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = _find_tests$3;
      var5 = new PyObject[]{var1.getclosure(0), var1.getclosure(2), var1.getclosure(1)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(21);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("_find_tests", var3);
      var3 = null;
      var1.setline(22);
      var3 = var1.getlocal(1).__getattr__("discover").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unittest.test"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(23);
      var1.getderef(0).__getattr__("assertTrue").__call__(var2, var1.getderef(0).__getattr__("wasRun"));
      var1.setline(24);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("_tests"), var1.getderef(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _find_tests$3(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("wasRun", var3);
      var3 = null;
      var1.setline(19);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0), var1.getderef(1));
      var1.setline(20);
      var3 = var1.getderef(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testNoExit$4(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var3 = var1.getglobal("object").__call__(var2);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(29);
      var3 = var1.getglobal("object").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(31);
      PyObject[] var5 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("FakeRunner", var5, FakeRunner$5);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(36);
      var3 = var1.getlocal(2).__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getglobal("unittest").__getattr__("TestProgram").__getattr__("parseArgs");
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(39);
      var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = restoreParseArgs$7;
      var5 = new PyObject[]{var1.getclosure(1)};
      PyFunction var7 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(41);
      var1.setline(41);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, f$8);
      var1.getglobal("unittest").__getattr__("TestProgram").__setattr__((String)"parseArgs", var7);
      var3 = null;
      var1.setline(42);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getlocal(4));
      var1.setline(44);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, removeTest$9, (PyObject)null);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(46);
      var3 = var1.getlocal(1);
      var1.getglobal("unittest").__getattr__("TestProgram").__setattr__("test", var3);
      var3 = null;
      var1.setline(47);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getlocal(5));
      var1.setline(49);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("TestProgram");
      var5 = new PyObject[]{var1.getlocal(3), var1.getglobal("False"), Py.newInteger(2)};
      String[] var6 = new String[]{"testRunner", "exit", "verbosity"};
      var10000 = var10000.__call__(var2, var5, var6);
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(51);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(6).__getattr__("result"), var1.getderef(0));
      var1.setline(52);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("test"), var1.getlocal(1));
      var1.setline(53);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("verbosity"), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FakeRunner$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(32);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = run$6;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("run", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject run$6(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("test", var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getderef(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject restoreParseArgs$7(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("unittest").__getattr__("TestProgram").__setattr__("parseArgs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$8(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject removeTest$9(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      var1.getglobal("unittest").__getattr__("TestProgram").__delattr__("test");
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FooBar$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(56);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, testPass$11, (PyObject)null);
      var1.setlocal("testPass", var4);
      var3 = null;
      var1.setline(58);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testFail$12, (PyObject)null);
      var1.setlocal("testFail", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject testPass$11(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("True").__nonzero__()) {
         PyObject var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject testFail$12(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("False").__nonzero__()) {
         PyObject var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject FooBarLoader$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Test loader that returns a suite containing FooBar."));
      var1.setline(62);
      PyString.fromInterned("Test loader that returns a suite containing FooBar.");
      var1.setline(63);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, loadTestsFromModule$14, (PyObject)null);
      var1.setlocal("loadTestsFromModule", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject loadTestsFromModule$14(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyObject var3 = var1.getlocal(0).__getattr__("suiteClass").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(0).__getattr__("loadTestsFromTestCase").__call__(var2, var1.getglobal("Test_TestProgram").__getattr__("FooBar"))})));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_NonExit$15(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var10000 = var1.getglobal("unittest").__getattr__("main");
      PyObject[] var3 = new PyObject[]{var1.getglobal("False"), new PyList(new PyObject[]{PyString.fromInterned("foobar")}), null, null};
      PyObject var10002 = var1.getglobal("unittest").__getattr__("TextTestRunner");
      PyObject[] var4 = new PyObject[]{var1.getglobal("StringIO").__call__(var2)};
      String[] var5 = new String[]{"stream"};
      var10002 = var10002.__call__(var2, var4, var5);
      var4 = null;
      var3[2] = var10002;
      var3[3] = var1.getlocal(0).__getattr__("FooBarLoader").__call__(var2);
      String[] var7 = new String[]{"exit", "argv", "testRunner", "testLoader"};
      var10000 = var10000.__call__(var2, var3, var7);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(73);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("result")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_Exit$16(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertRaises");
      PyObject[] var3 = new PyObject[]{var1.getglobal("SystemExit"), var1.getglobal("unittest").__getattr__("main"), new PyList(new PyObject[]{PyString.fromInterned("foobar")}), null, null, null};
      PyObject var10002 = var1.getglobal("unittest").__getattr__("TextTestRunner");
      PyObject[] var4 = new PyObject[]{var1.getglobal("StringIO").__call__(var2)};
      String[] var5 = new String[]{"stream"};
      var10002 = var10002.__call__(var2, var4, var5);
      var4 = null;
      var3[3] = var10002;
      var3[4] = var1.getglobal("True");
      var3[5] = var1.getlocal(0).__getattr__("FooBarLoader").__call__(var2);
      String[] var6 = new String[]{"argv", "testRunner", "exit", "testLoader"};
      var10000.__call__(var2, var3, var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_ExitAsDefault$17(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertRaises");
      PyObject[] var3 = new PyObject[]{var1.getglobal("SystemExit"), var1.getglobal("unittest").__getattr__("main"), new PyList(new PyObject[]{PyString.fromInterned("foobar")}), null, null};
      PyObject var10002 = var1.getglobal("unittest").__getattr__("TextTestRunner");
      PyObject[] var4 = new PyObject[]{var1.getglobal("StringIO").__call__(var2)};
      String[] var5 = new String[]{"stream"};
      var10002 = var10002.__call__(var2, var4, var5);
      var4 = null;
      var3[3] = var10002;
      var3[4] = var1.getlocal(0).__getattr__("FooBarLoader").__call__(var2);
      String[] var6 = new String[]{"argv", "testRunner", "testLoader"};
      var10000.__call__(var2, var3, var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject InitialisableProgram$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(96);
      PyObject var3 = var1.getname("False");
      var1.setlocal("exit", var3);
      var3 = null;
      var1.setline(97);
      var3 = var1.getname("None");
      var1.setlocal("result", var3);
      var3 = null;
      var1.setline(98);
      PyInteger var4 = Py.newInteger(1);
      var1.setlocal("verbosity", var4);
      var3 = null;
      var1.setline(99);
      var3 = var1.getname("None");
      var1.setlocal("defaultTest", var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getname("None");
      var1.setlocal("testRunner", var3);
      var3 = null;
      var1.setline(101);
      var3 = var1.getname("unittest").__getattr__("defaultTestLoader");
      var1.setlocal("testLoader", var3);
      var3 = null;
      var1.setline(102);
      PyString var5 = PyString.fromInterned("test");
      var1.setlocal("progName", var5);
      var3 = null;
      var1.setline(103);
      var5 = PyString.fromInterned("test");
      var1.setlocal("test", var5);
      var3 = null;
      var1.setline(104);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$19, (PyObject)null);
      var1.setlocal("__init__", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$19(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FakeRunner$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(110);
      PyObject var3 = var1.getname("None");
      var1.setlocal("initArgs", var3);
      var3 = null;
      var1.setline(111);
      var3 = var1.getname("None");
      var1.setlocal("test", var3);
      var3 = null;
      var1.setline(112);
      var3 = var1.getname("False");
      var1.setlocal("raiseError", var3);
      var3 = null;
      var1.setline(114);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$21, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(120);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, run$22, (PyObject)null);
      var1.setlocal("run", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$21(PyFrame var1, ThreadState var2) {
      var1.setline(115);
      PyObject var3 = var1.getlocal(1);
      var1.getglobal("FakeRunner").__setattr__("initArgs", var3);
      var3 = null;
      var1.setline(116);
      if (var1.getglobal("FakeRunner").__getattr__("raiseError").__nonzero__()) {
         var1.setline(117);
         var3 = var1.getglobal("False");
         var1.getglobal("FakeRunner").__setattr__("raiseError", var3);
         var3 = null;
         var1.setline(118);
         throw Py.makeException(var1.getglobal("TypeError"));
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject run$22(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyObject var3 = var1.getlocal(1);
      var1.getglobal("FakeRunner").__setattr__("test", var3);
      var3 = null;
      var1.setline(122);
      var3 = var1.getglobal("RESULT");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TestCommandLineArgs$23(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(126);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$24, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(133);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testHelpAndUnknown$26, (PyObject)null);
      var1.setlocal("testHelpAndUnknown", var4);
      var3 = null;
      var1.setline(150);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testVerbosity$28, (PyObject)null);
      var1.setlocal("testVerbosity", var4);
      var3 = null;
      var1.setline(163);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testBufferCatchFailfast$29, (PyObject)null);
      var1.setlocal("testBufferCatchFailfast", var4);
      var3 = null;
      var1.setline(185);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testRunTestsRunnerClass$30, (PyObject)null);
      var1.setlocal("testRunTestsRunnerClass", var4);
      var3 = null;
      var1.setline(201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testRunTestsRunnerInstance$31, (PyObject)null);
      var1.setlocal("testRunTestsRunnerInstance", var4);
      var3 = null;
      var1.setline(215);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testRunTestsOldRunnerClass$32, (PyObject)null);
      var1.setlocal("testRunTestsOldRunnerClass", var4);
      var3 = null;
      var1.setline(233);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, testCatchBreakInstallsHandler$33, (PyObject)null);
      var1.setlocal("testCatchBreakInstallsHandler", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$24(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyObject var3 = var1.getglobal("InitialisableProgram").__call__(var2);
      var1.getlocal(0).__setattr__("program", var3);
      var3 = null;
      var1.setline(128);
      var1.setline(128);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, f$25);
      var1.getlocal(0).__getattr__("program").__setattr__((String)"createTests", var5);
      var3 = null;
      var1.setline(129);
      var3 = var1.getglobal("None");
      var1.getglobal("FakeRunner").__setattr__("initArgs", var3);
      var3 = null;
      var1.setline(130);
      var3 = var1.getglobal("None");
      var1.getglobal("FakeRunner").__setattr__("test", var3);
      var3 = null;
      var1.setline(131);
      var3 = var1.getglobal("False");
      var1.getglobal("FakeRunner").__setattr__("raiseError", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$25(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject testHelpAndUnknown$26(PyFrame var1, ThreadState var2) {
      var1.setline(134);
      PyObject var3 = var1.getlocal(0).__getattr__("program");
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(135);
      PyObject[] var6 = new PyObject[]{var1.getglobal("None")};
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var6;
      PyCode var10004 = usageExit$27;
      var6 = new PyObject[]{var1.getclosure(0)};
      PyFunction var7 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var6);
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(138);
      var3 = var1.getlocal(1);
      var1.getderef(0).__setattr__("usageExit", var3);
      var3 = null;
      var1.setline(140);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("-h"), PyString.fromInterned("-H"), PyString.fromInterned("--help")})).__iter__();

      while(true) {
         var1.setline(140);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(146);
            var1.getderef(0).__getattr__("parseArgs").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("None"), PyString.fromInterned("-$")})));
            var1.setline(147);
            var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getderef(0).__getattr__("exit"));
            var1.setline(148);
            var1.getlocal(0).__getattr__("assertIsNotNone").__call__(var2, var1.getderef(0).__getattr__("msg"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(141);
         PyObject var5 = var1.getglobal("False");
         var1.getderef(0).__setattr__("exit", var5);
         var5 = null;
         var1.setline(142);
         var1.getderef(0).__getattr__("parseArgs").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("None"), var1.getlocal(2)})));
         var1.setline(143);
         var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getderef(0).__getattr__("exit"));
         var1.setline(144);
         var1.getlocal(0).__getattr__("assertIsNone").__call__(var2, var1.getderef(0).__getattr__("msg"));
      }
   }

   public PyObject usageExit$27(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      PyObject var3 = var1.getlocal(0);
      var1.getderef(0).__setattr__("msg", var3);
      var3 = null;
      var1.setline(137);
      var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("exit", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testVerbosity$28(PyFrame var1, ThreadState var2) {
      var1.setline(151);
      PyObject var3 = var1.getlocal(0).__getattr__("program");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(153);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("-q"), PyString.fromInterned("--quiet")})).__iter__();

      while(true) {
         var1.setline(153);
         PyObject var4 = var3.__iternext__();
         PyInteger var5;
         if (var4 == null) {
            var1.setline(158);
            var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("-v"), PyString.fromInterned("--verbose")})).__iter__();

            while(true) {
               var1.setline(158);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(2, var4);
               var1.setline(159);
               var5 = Py.newInteger(1);
               var1.getlocal(1).__setattr__((String)"verbosity", var5);
               var5 = null;
               var1.setline(160);
               var1.getlocal(1).__getattr__("parseArgs").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("None"), var1.getlocal(2)})));
               var1.setline(161);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("verbosity"), (PyObject)Py.newInteger(2));
            }
         }

         var1.setlocal(2, var4);
         var1.setline(154);
         var5 = Py.newInteger(1);
         var1.getlocal(1).__setattr__((String)"verbosity", var5);
         var5 = null;
         var1.setline(155);
         var1.getlocal(1).__getattr__("parseArgs").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("None"), var1.getlocal(2)})));
         var1.setline(156);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("verbosity"), (PyObject)Py.newInteger(0));
      }
   }

   public PyObject testBufferCatchFailfast$29(PyFrame var1, ThreadState var2) {
      var1.setline(164);
      PyObject var3 = var1.getlocal(0).__getattr__("program");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(165);
      var3 = (new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("buffer"), PyString.fromInterned("buffer")}), new PyTuple(new PyObject[]{PyString.fromInterned("failfast"), PyString.fromInterned("failfast")}), new PyTuple(new PyObject[]{PyString.fromInterned("catch"), PyString.fromInterned("catchbreak")})})).__iter__();

      label36:
      while(true) {
         PyObject var10000;
         PyObject[] var5;
         PyObject var6;
         PyObject var8;
         do {
            var1.setline(165);
            PyObject var4 = var3.__iternext__();
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
            var1.setline(167);
            var8 = var1.getlocal(3);
            var10000 = var8._eq(PyString.fromInterned("catch"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("hasInstallHandler").__not__();
            }
         } while(var10000.__nonzero__());

         var1.setline(170);
         var8 = PyString.fromInterned("-%s")._mod(var1.getlocal(2).__getitem__(Py.newInteger(0)));
         var1.setlocal(4, var8);
         var5 = null;
         var1.setline(171);
         var8 = PyString.fromInterned("--%s")._mod(var1.getlocal(2));
         var1.setlocal(5, var8);
         var5 = null;
         var1.setline(172);
         var8 = (new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})).__iter__();

         while(true) {
            var1.setline(172);
            var6 = var8.__iternext__();
            if (var6 == null) {
               var1.setline(178);
               var8 = (new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})).__iter__();

               while(true) {
                  var1.setline(178);
                  var6 = var8.__iternext__();
                  if (var6 == null) {
                     continue label36;
                  }

                  var1.setlocal(6, var6);
                  var1.setline(179);
                  PyObject var7 = var1.getglobal("object").__call__(var2);
                  var1.setlocal(7, var7);
                  var7 = null;
                  var1.setline(180);
                  var1.getglobal("setattr").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(7));
                  var1.setline(182);
                  var1.getlocal(1).__getattr__("parseArgs").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("None"), var1.getlocal(6)})));
                  var1.setline(183);
                  var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getlocal(1), var1.getlocal(3)), var1.getlocal(7));
               }
            }

            var1.setlocal(6, var6);
            var1.setline(173);
            var1.getglobal("setattr").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getglobal("None"));
            var1.setline(175);
            var1.getlocal(1).__getattr__("parseArgs").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("None"), var1.getlocal(6)})));
            var1.setline(176);
            var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getlocal(1), var1.getlocal(3)));
         }
      }
   }

   public PyObject testRunTestsRunnerClass$30(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getlocal(0).__getattr__("program");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(188);
      var3 = var1.getglobal("FakeRunner");
      var1.getlocal(1).__setattr__("testRunner", var3);
      var3 = null;
      var1.setline(189);
      PyString var4 = PyString.fromInterned("verbosity");
      var1.getlocal(1).__setattr__((String)"verbosity", var4);
      var3 = null;
      var1.setline(190);
      var4 = PyString.fromInterned("failfast");
      var1.getlocal(1).__setattr__((String)"failfast", var4);
      var3 = null;
      var1.setline(191);
      var4 = PyString.fromInterned("buffer");
      var1.getlocal(1).__setattr__((String)"buffer", var4);
      var3 = null;
      var1.setline(193);
      var1.getlocal(1).__getattr__("runTests").__call__(var2);
      var1.setline(195);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("FakeRunner").__getattr__("initArgs"), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("verbosity"), PyString.fromInterned("verbosity"), PyString.fromInterned("failfast"), PyString.fromInterned("failfast"), PyString.fromInterned("buffer"), PyString.fromInterned("buffer")})));
      var1.setline(198);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("FakeRunner").__getattr__("test"), (PyObject)PyString.fromInterned("test"));
      var1.setline(199);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(1).__getattr__("result"), var1.getglobal("RESULT"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testRunTestsRunnerInstance$31(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      PyObject var3 = var1.getlocal(0).__getattr__("program");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(204);
      var3 = var1.getglobal("FakeRunner").__call__(var2);
      var1.getlocal(1).__setattr__("testRunner", var3);
      var3 = null;
      var1.setline(205);
      var3 = var1.getglobal("None");
      var1.getglobal("FakeRunner").__setattr__("initArgs", var3);
      var3 = null;
      var1.setline(207);
      var1.getlocal(1).__getattr__("runTests").__call__(var2);
      var1.setline(210);
      var1.getlocal(0).__getattr__("assertIsNone").__call__(var2, var1.getglobal("FakeRunner").__getattr__("initArgs"));
      var1.setline(212);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("FakeRunner").__getattr__("test"), (PyObject)PyString.fromInterned("test"));
      var1.setline(213);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(1).__getattr__("result"), var1.getglobal("RESULT"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testRunTestsOldRunnerClass$32(PyFrame var1, ThreadState var2) {
      var1.setline(216);
      PyObject var3 = var1.getlocal(0).__getattr__("program");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(218);
      var3 = var1.getglobal("True");
      var1.getglobal("FakeRunner").__setattr__("raiseError", var3);
      var3 = null;
      var1.setline(219);
      var3 = var1.getglobal("FakeRunner");
      var1.getlocal(1).__setattr__("testRunner", var3);
      var3 = null;
      var1.setline(220);
      PyString var4 = PyString.fromInterned("verbosity");
      var1.getlocal(1).__setattr__((String)"verbosity", var4);
      var3 = null;
      var1.setline(221);
      var4 = PyString.fromInterned("failfast");
      var1.getlocal(1).__setattr__((String)"failfast", var4);
      var3 = null;
      var1.setline(222);
      var4 = PyString.fromInterned("buffer");
      var1.getlocal(1).__setattr__((String)"buffer", var4);
      var3 = null;
      var1.setline(223);
      var4 = PyString.fromInterned("test");
      var1.getlocal(1).__setattr__((String)"test", var4);
      var3 = null;
      var1.setline(225);
      var1.getlocal(1).__getattr__("runTests").__call__(var2);
      var1.setline(229);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("FakeRunner").__getattr__("initArgs"), (PyObject)(new PyDictionary(Py.EmptyObjects)));
      var1.setline(230);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("FakeRunner").__getattr__("test"), (PyObject)PyString.fromInterned("test"));
      var1.setline(231);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(1).__getattr__("result"), var1.getglobal("RESULT"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject testCatchBreakInstallsHandler$33(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(234);
      PyObject var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(PyString.fromInterned("unittest.main"));
      var1.setderef(2, var3);
      var3 = null;
      var1.setline(235);
      var3 = var1.getderef(2).__getattr__("installHandler");
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(236);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = restore$34;
      var4 = new PyObject[]{var1.getclosure(2), var1.getclosure(1)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(238);
      var1.getderef(0).__getattr__("addCleanup").__call__(var2, var1.getlocal(1));
      var1.setline(240);
      var3 = var1.getglobal("False");
      var1.getderef(0).__setattr__("installed", var3);
      var3 = null;
      var1.setline(241);
      var4 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var4;
      var10004 = fakeInstallHandler$35;
      var4 = new PyObject[]{var1.getclosure(0)};
      var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(243);
      var3 = var1.getlocal(2);
      var1.getderef(2).__setattr__("installHandler", var3);
      var3 = null;
      var1.setline(245);
      var3 = var1.getderef(0).__getattr__("program");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(246);
      var3 = var1.getglobal("True");
      var1.getlocal(3).__setattr__("catchbreak", var3);
      var3 = null;
      var1.setline(248);
      var3 = var1.getglobal("FakeRunner");
      var1.getlocal(3).__setattr__("testRunner", var3);
      var3 = null;
      var1.setline(250);
      var1.getlocal(3).__getattr__("runTests").__call__(var2);
      var1.setline(251);
      var1.getderef(0).__getattr__("assertTrue").__call__(var2, var1.getderef(0).__getattr__("installed"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject restore$34(PyFrame var1, ThreadState var2) {
      var1.setline(237);
      PyObject var3 = var1.getderef(1);
      var1.getderef(0).__setattr__("installHandler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fakeInstallHandler$35(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("installed", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public test_program$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Test_TestProgram$1 = Py.newCode(0, var2, var1, "Test_TestProgram", 8, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "loader", "_find_tests", "suite", "tests", "expectedPath"};
      String[] var10001 = var2;
      test_program$py var10007 = self;
      var2 = new String[]{"self", "tests", "expectedPath"};
      test_discovery_from_dotted_path$2 = Py.newCode(1, var10001, var1, "test_discovery_from_dotted_path", 10, false, false, var10007, 2, var2, (String[])null, 2, 4097);
      var2 = new String[]{"start_dir", "pattern"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "expectedPath", "tests"};
      _find_tests$3 = Py.newCode(2, var10001, var1, "_find_tests", 17, false, false, var10007, 3, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "test", "FakeRunner", "runner", "restoreParseArgs", "removeTest", "program", "result", "oldParseArgs"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"result", "oldParseArgs"};
      testNoExit$4 = Py.newCode(1, var10001, var1, "testNoExit", 27, false, false, var10007, 4, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      FakeRunner$5 = Py.newCode(0, var2, var1, "FakeRunner", 31, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "test"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"result"};
      run$6 = Py.newCode(2, var10001, var1, "run", 32, false, false, var10007, 6, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"oldParseArgs"};
      restoreParseArgs$7 = Py.newCode(0, var10001, var1, "restoreParseArgs", 39, false, false, var10007, 7, (String[])null, var2, 0, 4097);
      var2 = new String[]{"args"};
      f$8 = Py.newCode(1, var2, var1, "<lambda>", 41, true, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      removeTest$9 = Py.newCode(0, var2, var1, "removeTest", 44, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FooBar$10 = Py.newCode(0, var2, var1, "FooBar", 55, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      testPass$11 = Py.newCode(1, var2, var1, "testPass", 56, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      testFail$12 = Py.newCode(1, var2, var1, "testFail", 58, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FooBarLoader$13 = Py.newCode(0, var2, var1, "FooBarLoader", 61, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "module"};
      loadTestsFromModule$14 = Py.newCode(2, var2, var1, "loadTestsFromModule", 63, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "program"};
      test_NonExit$15 = Py.newCode(1, var2, var1, "test_NonExit", 68, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_Exit$16 = Py.newCode(1, var2, var1, "test_Exit", 76, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_ExitAsDefault$17 = Py.newCode(1, var2, var1, "test_ExitAsDefault", 86, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      InitialisableProgram$18 = Py.newCode(0, var2, var1, "InitialisableProgram", 95, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args"};
      __init__$19 = Py.newCode(2, var2, var1, "__init__", 104, true, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FakeRunner$20 = Py.newCode(0, var2, var1, "FakeRunner", 109, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "kwargs"};
      __init__$21 = Py.newCode(2, var2, var1, "__init__", 114, false, true, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test"};
      run$22 = Py.newCode(2, var2, var1, "run", 120, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestCommandLineArgs$23 = Py.newCode(0, var2, var1, "TestCommandLineArgs", 124, false, false, self, 23, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$24 = Py.newCode(1, var2, var1, "setUp", 126, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      f$25 = Py.newCode(0, var2, var1, "<lambda>", 128, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "usageExit", "opt", "program"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"program"};
      testHelpAndUnknown$26 = Py.newCode(1, var10001, var1, "testHelpAndUnknown", 133, false, false, var10007, 26, var2, (String[])null, 1, 4097);
      var2 = new String[]{"msg"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"program"};
      usageExit$27 = Py.newCode(1, var10001, var1, "usageExit", 135, false, false, var10007, 27, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "program", "opt"};
      testVerbosity$28 = Py.newCode(1, var2, var1, "testVerbosity", 150, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "program", "arg", "attr", "short_opt", "long_opt", "opt", "not_none"};
      testBufferCatchFailfast$29 = Py.newCode(1, var2, var1, "testBufferCatchFailfast", 163, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "program"};
      testRunTestsRunnerClass$30 = Py.newCode(1, var2, var1, "testRunTestsRunnerClass", 185, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "program"};
      testRunTestsRunnerInstance$31 = Py.newCode(1, var2, var1, "testRunTestsRunnerInstance", 201, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "program"};
      testRunTestsOldRunnerClass$32 = Py.newCode(1, var2, var1, "testRunTestsOldRunnerClass", 215, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "restore", "fakeInstallHandler", "program", "original", "module"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "original", "module"};
      testCatchBreakInstallsHandler$33 = Py.newCode(1, var10001, var1, "testCatchBreakInstallsHandler", 233, false, false, var10007, 33, var2, (String[])null, 2, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"module", "original"};
      restore$34 = Py.newCode(0, var10001, var1, "restore", 236, false, false, var10007, 34, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      fakeInstallHandler$35 = Py.newCode(0, var10001, var1, "fakeInstallHandler", 241, false, false, var10007, 35, (String[])null, var2, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_program$py("unittest/test/test_program$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_program$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Test_TestProgram$1(var2, var3);
         case 2:
            return this.test_discovery_from_dotted_path$2(var2, var3);
         case 3:
            return this._find_tests$3(var2, var3);
         case 4:
            return this.testNoExit$4(var2, var3);
         case 5:
            return this.FakeRunner$5(var2, var3);
         case 6:
            return this.run$6(var2, var3);
         case 7:
            return this.restoreParseArgs$7(var2, var3);
         case 8:
            return this.f$8(var2, var3);
         case 9:
            return this.removeTest$9(var2, var3);
         case 10:
            return this.FooBar$10(var2, var3);
         case 11:
            return this.testPass$11(var2, var3);
         case 12:
            return this.testFail$12(var2, var3);
         case 13:
            return this.FooBarLoader$13(var2, var3);
         case 14:
            return this.loadTestsFromModule$14(var2, var3);
         case 15:
            return this.test_NonExit$15(var2, var3);
         case 16:
            return this.test_Exit$16(var2, var3);
         case 17:
            return this.test_ExitAsDefault$17(var2, var3);
         case 18:
            return this.InitialisableProgram$18(var2, var3);
         case 19:
            return this.__init__$19(var2, var3);
         case 20:
            return this.FakeRunner$20(var2, var3);
         case 21:
            return this.__init__$21(var2, var3);
         case 22:
            return this.run$22(var2, var3);
         case 23:
            return this.TestCommandLineArgs$23(var2, var3);
         case 24:
            return this.setUp$24(var2, var3);
         case 25:
            return this.f$25(var2, var3);
         case 26:
            return this.testHelpAndUnknown$26(var2, var3);
         case 27:
            return this.usageExit$27(var2, var3);
         case 28:
            return this.testVerbosity$28(var2, var3);
         case 29:
            return this.testBufferCatchFailfast$29(var2, var3);
         case 30:
            return this.testRunTestsRunnerClass$30(var2, var3);
         case 31:
            return this.testRunTestsRunnerInstance$31(var2, var3);
         case 32:
            return this.testRunTestsOldRunnerClass$32(var2, var3);
         case 33:
            return this.testCatchBreakInstallsHandler$33(var2, var3);
         case 34:
            return this.restore$34(var2, var3);
         case 35:
            return this.fakeInstallHandler$35(var2, var3);
         default:
            return null;
      }
   }
}
