package unittest;

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
@Filename("unittest/main.py")
public class main$py extends PyFunctionTable implements PyRunnable {
   static main$py self;
   static final PyCode f$0;
   static final PyCode TestProgram$1;
   static final PyCode __init__$2;
   static final PyCode usageExit$3;
   static final PyCode parseArgs$4;
   static final PyCode createTests$5;
   static final PyCode _do_discovery$6;
   static final PyCode f$7;
   static final PyCode runTests$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Unittest main program"));
      var1.setline(1);
      PyString.fromInterned("Unittest main program");
      var1.setline(3);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(7);
      String[] var5 = new String[]{"loader", "runner"};
      PyObject[] var6 = imp.importFrom("", var5, var1, 1);
      PyObject var4 = var6[0];
      var1.setlocal("loader", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("runner", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"installHandler"};
      var6 = imp.importFrom("signals", var5, var1, 1);
      var4 = var6[0];
      var1.setlocal("installHandler", var4);
      var4 = null;
      var1.setline(10);
      var3 = var1.getname("True");
      var1.setlocal("__unittest", var3);
      var3 = null;
      var1.setline(12);
      PyString var7 = PyString.fromInterned("  -f, --failfast   Stop on first failure\n");
      var1.setlocal("FAILFAST", var7);
      var3 = null;
      var1.setline(13);
      var7 = PyString.fromInterned("  -c, --catch      Catch control-C and display results\n");
      var1.setlocal("CATCHBREAK", var7);
      var3 = null;
      var1.setline(14);
      var7 = PyString.fromInterned("  -b, --buffer     Buffer stdout and stderr during test runs\n");
      var1.setlocal("BUFFEROUTPUT", var7);
      var3 = null;
      var1.setline(16);
      var7 = PyString.fromInterned("Usage: %(progName)s [options] [tests]\n\nOptions:\n  -h, --help       Show this message\n  -v, --verbose    Verbose output\n  -q, --quiet      Minimal output\n%(failfast)s%(catchbreak)s%(buffer)s\nExamples:\n  %(progName)s test_module               - run tests from test_module\n  %(progName)s module.TestClass          - run tests from module.TestClass\n  %(progName)s module.Class.test_method  - run specified test method\n\n[tests] can be a list of any number of test modules, classes and test\nmethods.\n\nAlternative Usage: %(progName)s discover [options]\n\nOptions:\n  -v, --verbose    Verbose output\n%(failfast)s%(catchbreak)s%(buffer)s  -s directory     Directory to start discovery ('.' default)\n  -p pattern       Pattern to match test files ('test*.py' default)\n  -t directory     Top level directory of project (default to\n                   start directory)\n\nFor test discovery all test modules must be importable from the top\nlevel directory of the project.\n");
      var1.setlocal("USAGE_AS_MAIN", var7);
      var3 = null;
      var1.setline(45);
      var7 = PyString.fromInterned("Usage: %(progName)s [options] [test] [...]\n\nOptions:\n  -h, --help       Show this message\n  -v, --verbose    Verbose output\n  -q, --quiet      Minimal output\n%(failfast)s%(catchbreak)s%(buffer)s\nExamples:\n  %(progName)s                               - run default set of tests\n  %(progName)s MyTestSuite                   - run suite 'MyTestSuite'\n  %(progName)s MyTestCase.testSomething      - run MyTestCase.testSomething\n  %(progName)s MyTestCase                    - run all 'test*' test methods\n                                               in MyTestCase\n");
      var1.setlocal("USAGE_FROM_MODULE", var7);
      var3 = null;
      var1.setline(63);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestProgram", var6, TestProgram$1);
      var1.setlocal("TestProgram", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(236);
      var3 = var1.getname("TestProgram");
      var1.setlocal("main", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestProgram$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("A command-line program that runs a set of tests; this is primarily\n       for making test modules conveniently executable.\n    "));
      var1.setline(66);
      PyString.fromInterned("A command-line program that runs a set of tests; this is primarily\n       for making test modules conveniently executable.\n    ");
      var1.setline(67);
      PyObject var3 = var1.getname("USAGE_FROM_MODULE");
      var1.setlocal("USAGE", var3);
      var3 = null;
      var1.setline(70);
      var3 = var1.getname("None");
      var1.setlocal("failfast", var3);
      var1.setlocal("catchbreak", var3);
      var1.setlocal("buffer", var3);
      var1.setlocal("progName", var3);
      var1.setline(72);
      PyObject[] var4 = new PyObject[]{PyString.fromInterned("__main__"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("loader").__getattr__("defaultTestLoader"), var1.getname("True"), Py.newInteger(1), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(97);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, usageExit$3, (PyObject)null);
      var1.setlocal("usageExit", var5);
      var3 = null;
      var1.setline(111);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, parseArgs$4, (PyObject)null);
      var1.setlocal("parseArgs", var5);
      var3 = null;
      var1.setline(153);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, createTests$5, (PyObject)null);
      var1.setlocal("createTests", var5);
      var3 = null;
      var1.setline(160);
      var4 = new PyObject[]{var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, _do_discovery$6, (PyObject)null);
      var1.setlocal("_do_discovery", var5);
      var3 = null;
      var1.setline(216);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, runTests$8, (PyObject)null);
      var1.setlocal("runTests", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("basestring")).__nonzero__()) {
         var1.setline(77);
         var3 = var1.getglobal("__import__").__call__(var2, var1.getlocal(1));
         var1.getlocal(0).__setattr__("module", var3);
         var3 = null;
         var1.setline(78);
         var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

         while(true) {
            var1.setline(78);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(11, var4);
            var1.setline(79);
            PyObject var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("module"), var1.getlocal(11));
            var1.getlocal(0).__setattr__("module", var5);
            var5 = null;
         }
      } else {
         var1.setline(81);
         var3 = var1.getlocal(1);
         var1.getlocal(0).__setattr__("module", var3);
         var3 = null;
      }

      var1.setline(82);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(83);
         var3 = var1.getglobal("sys").__getattr__("argv");
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(85);
      var3 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("exit", var3);
      var3 = null;
      var1.setline(86);
      var3 = var1.getlocal(8);
      var1.getlocal(0).__setattr__("failfast", var3);
      var3 = null;
      var1.setline(87);
      var3 = var1.getlocal(9);
      var1.getlocal(0).__setattr__("catchbreak", var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getlocal(7);
      var1.getlocal(0).__setattr__("verbosity", var3);
      var3 = null;
      var1.setline(89);
      var3 = var1.getlocal(10);
      var1.getlocal(0).__setattr__("buffer", var3);
      var3 = null;
      var1.setline(90);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("defaultTest", var3);
      var3 = null;
      var1.setline(91);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("testRunner", var3);
      var3 = null;
      var1.setline(92);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("testLoader", var3);
      var3 = null;
      var1.setline(93);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)));
      var1.getlocal(0).__setattr__("progName", var3);
      var3 = null;
      var1.setline(94);
      var1.getlocal(0).__getattr__("parseArgs").__call__(var2, var1.getlocal(3));
      var1.setline(95);
      var1.getlocal(0).__getattr__("runTests").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject usageExit$3(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(99);
         Py.println(var1.getlocal(1));
      }

      var1.setline(100);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("progName"), var1.getlocal(0).__getattr__("progName"), PyString.fromInterned("catchbreak"), PyString.fromInterned(""), PyString.fromInterned("failfast"), PyString.fromInterned(""), PyString.fromInterned("buffer"), PyString.fromInterned("")});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(102);
      PyObject var4 = var1.getlocal(0).__getattr__("failfast");
      PyObject var10000 = var4._ne(var1.getglobal("False"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(103);
         var4 = var1.getglobal("FAILFAST");
         var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("failfast"), var4);
         var3 = null;
      }

      var1.setline(104);
      var4 = var1.getlocal(0).__getattr__("catchbreak");
      var10000 = var4._ne(var1.getglobal("False"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(105);
         var4 = var1.getglobal("CATCHBREAK");
         var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("catchbreak"), var4);
         var3 = null;
      }

      var1.setline(106);
      var4 = var1.getlocal(0).__getattr__("buffer");
      var10000 = var4._ne(var1.getglobal("False"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(107);
         var4 = var1.getglobal("BUFFEROUTPUT");
         var1.getlocal(2).__setitem__((PyObject)PyString.fromInterned("buffer"), var4);
         var3 = null;
      }

      var1.setline(108);
      Py.println(var1.getlocal(0).__getattr__("USAGE")._mod(var1.getlocal(2)));
      var1.setline(109);
      var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parseArgs$4(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._gt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__getattr__("lower").__call__(var2);
         var10000 = var3._eq(PyString.fromInterned("discover"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(113);
         var1.getlocal(0).__getattr__("_do_discovery").__call__(var2, var1.getlocal(1).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null));
         var1.setline(114);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(116);
         var3 = imp.importOne("getopt", var1, -1);
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(117);
         PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("help"), PyString.fromInterned("verbose"), PyString.fromInterned("quiet"), PyString.fromInterned("failfast"), PyString.fromInterned("catch"), PyString.fromInterned("buffer")});
         var1.setlocal(3, var9);
         var3 = null;

         PyObject var4;
         try {
            var1.setline(119);
            var3 = var1.getlocal(2).__getattr__("getopt").__call__((ThreadState)var2, var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), (PyObject)PyString.fromInterned("hHvqfcb"), (PyObject)var1.getlocal(3));
            PyObject[] var8 = Py.unpackSequence(var3, 2);
            PyObject var5 = var8[0];
            var1.setlocal(4, var5);
            var5 = null;
            var5 = var8[1];
            var1.setlocal(5, var5);
            var5 = null;
            var3 = null;
            var1.setline(120);
            var3 = var1.getlocal(4).__iter__();

            while(true) {
               var1.setline(120);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(139);
                  var3 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
                  var10000 = var3._eq(Py.newInteger(0));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var3 = var1.getlocal(0).__getattr__("defaultTest");
                     var10000 = var3._is(var1.getglobal("None"));
                     var3 = null;
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(141);
                     var3 = var1.getglobal("None");
                     var1.getlocal(0).__setattr__("testNames", var3);
                     var3 = null;
                  } else {
                     var1.setline(142);
                     var3 = var1.getglobal("len").__call__(var2, var1.getlocal(5));
                     var10000 = var3._gt(Py.newInteger(0));
                     var3 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(143);
                        var3 = var1.getlocal(5);
                        var1.getlocal(0).__setattr__("testNames", var3);
                        var3 = null;
                        var1.setline(144);
                        var3 = var1.getglobal("__name__");
                        var10000 = var3._eq(PyString.fromInterned("__main__"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(146);
                           var3 = var1.getglobal("None");
                           var1.getlocal(0).__setattr__("module", var3);
                           var3 = null;
                        }
                     } else {
                        var1.setline(148);
                        PyTuple var13 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("defaultTest")});
                        var1.getlocal(0).__setattr__((String)"testNames", var13);
                        var3 = null;
                     }
                  }

                  var1.setline(149);
                  var1.getlocal(0).__getattr__("createTests").__call__(var2);
                  break;
               }

               PyObject[] var11 = Py.unpackSequence(var4, 2);
               PyObject var6 = var11[0];
               var1.setlocal(6, var6);
               var6 = null;
               var6 = var11[1];
               var1.setlocal(7, var6);
               var6 = null;
               var1.setline(121);
               var5 = var1.getlocal(6);
               var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-h"), PyString.fromInterned("-H"), PyString.fromInterned("--help")}));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(122);
                  var1.getlocal(0).__getattr__("usageExit").__call__(var2);
               }

               var1.setline(123);
               var5 = var1.getlocal(6);
               var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-q"), PyString.fromInterned("--quiet")}));
               var5 = null;
               PyInteger var12;
               if (var10000.__nonzero__()) {
                  var1.setline(124);
                  var12 = Py.newInteger(0);
                  var1.getlocal(0).__setattr__((String)"verbosity", var12);
                  var5 = null;
               }

               var1.setline(125);
               var5 = var1.getlocal(6);
               var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-v"), PyString.fromInterned("--verbose")}));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(126);
                  var12 = Py.newInteger(2);
                  var1.getlocal(0).__setattr__((String)"verbosity", var12);
                  var5 = null;
               }

               var1.setline(127);
               var5 = var1.getlocal(6);
               var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-f"), PyString.fromInterned("--failfast")}));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(128);
                  var5 = var1.getlocal(0).__getattr__("failfast");
                  var10000 = var5._is(var1.getglobal("None"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(129);
                     var5 = var1.getglobal("True");
                     var1.getlocal(0).__setattr__("failfast", var5);
                     var5 = null;
                  }
               }

               var1.setline(131);
               var5 = var1.getlocal(6);
               var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-c"), PyString.fromInterned("--catch")}));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(132);
                  var5 = var1.getlocal(0).__getattr__("catchbreak");
                  var10000 = var5._is(var1.getglobal("None"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(133);
                     var5 = var1.getglobal("True");
                     var1.getlocal(0).__setattr__("catchbreak", var5);
                     var5 = null;
                  }
               }

               var1.setline(135);
               var5 = var1.getlocal(6);
               var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("-b"), PyString.fromInterned("--buffer")}));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(136);
                  var5 = var1.getlocal(0).__getattr__("buffer");
                  var10000 = var5._is(var1.getglobal("None"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(137);
                     var5 = var1.getglobal("True");
                     var1.getlocal(0).__setattr__("buffer", var5);
                     var5 = null;
                  }
               }
            }
         } catch (Throwable var7) {
            PyException var10 = Py.setException(var7, var1);
            if (!var10.match(var1.getlocal(2).__getattr__("error"))) {
               throw var10;
            }

            var4 = var10.value;
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(151);
            var1.getlocal(0).__getattr__("usageExit").__call__(var2, var1.getlocal(8));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject createTests$5(PyFrame var1, ThreadState var2) {
      var1.setline(154);
      PyObject var3 = var1.getlocal(0).__getattr__("testNames");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(155);
         var3 = var1.getlocal(0).__getattr__("testLoader").__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(0).__getattr__("module"));
         var1.getlocal(0).__setattr__("test", var3);
         var3 = null;
      } else {
         var1.setline(157);
         var3 = var1.getlocal(0).__getattr__("testLoader").__getattr__("loadTestsFromNames").__call__(var2, var1.getlocal(0).__getattr__("testNames"), var1.getlocal(0).__getattr__("module"));
         var1.getlocal(0).__setattr__("test", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _do_discovery$6(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(161);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyObject[] var7;
      if (var10000.__nonzero__()) {
         var1.setline(162);
         var1.setline(162);
         var7 = Py.EmptyObjects;
         PyObject[] var10003 = var7;
         PyObject var10002 = var1.f_globals;
         PyCode var10004 = f$7;
         var7 = new PyObject[]{var1.getclosure(0)};
         PyFunction var8 = new PyFunction(var10002, var10003, var10004, var7);
         var1.setlocal(2, var8);
         var3 = null;
      }

      var1.setline(165);
      var3 = PyString.fromInterned("%s discover")._mod(var1.getderef(0).__getattr__("progName"));
      var1.getderef(0).__setattr__("progName", var3);
      var3 = null;
      var1.setline(166);
      var3 = imp.importOne("optparse", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(167);
      var3 = var1.getlocal(3).__getattr__("OptionParser").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(168);
      var3 = var1.getderef(0).__getattr__("progName");
      var1.getlocal(4).__setattr__("prog", var3);
      var3 = null;
      var1.setline(169);
      var10000 = var1.getlocal(4).__getattr__("add_option");
      var7 = new PyObject[]{PyString.fromInterned("-v"), PyString.fromInterned("--verbose"), PyString.fromInterned("verbose"), var1.getglobal("False"), PyString.fromInterned("Verbose output"), PyString.fromInterned("store_true")};
      String[] var4 = new String[]{"dest", "default", "help", "action"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(171);
      var3 = var1.getderef(0).__getattr__("failfast");
      var10000 = var3._ne(var1.getglobal("False"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(172);
         var10000 = var1.getlocal(4).__getattr__("add_option");
         var7 = new PyObject[]{PyString.fromInterned("-f"), PyString.fromInterned("--failfast"), PyString.fromInterned("failfast"), var1.getglobal("False"), PyString.fromInterned("Stop on first fail or error"), PyString.fromInterned("store_true")};
         var4 = new String[]{"dest", "default", "help", "action"};
         var10000.__call__(var2, var7, var4);
         var3 = null;
      }

      var1.setline(175);
      var3 = var1.getderef(0).__getattr__("catchbreak");
      var10000 = var3._ne(var1.getglobal("False"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(176);
         var10000 = var1.getlocal(4).__getattr__("add_option");
         var7 = new PyObject[]{PyString.fromInterned("-c"), PyString.fromInterned("--catch"), PyString.fromInterned("catchbreak"), var1.getglobal("False"), PyString.fromInterned("Catch ctrl-C and display results so far"), PyString.fromInterned("store_true")};
         var4 = new String[]{"dest", "default", "help", "action"};
         var10000.__call__(var2, var7, var4);
         var3 = null;
      }

      var1.setline(179);
      var3 = var1.getderef(0).__getattr__("buffer");
      var10000 = var3._ne(var1.getglobal("False"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(180);
         var10000 = var1.getlocal(4).__getattr__("add_option");
         var7 = new PyObject[]{PyString.fromInterned("-b"), PyString.fromInterned("--buffer"), PyString.fromInterned("buffer"), var1.getglobal("False"), PyString.fromInterned("Buffer stdout and stderr during tests"), PyString.fromInterned("store_true")};
         var4 = new String[]{"dest", "default", "help", "action"};
         var10000.__call__(var2, var7, var4);
         var3 = null;
      }

      var1.setline(183);
      var10000 = var1.getlocal(4).__getattr__("add_option");
      var7 = new PyObject[]{PyString.fromInterned("-s"), PyString.fromInterned("--start-directory"), PyString.fromInterned("start"), PyString.fromInterned("."), PyString.fromInterned("Directory to start discovery ('.' default)")};
      var4 = new String[]{"dest", "default", "help"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(185);
      var10000 = var1.getlocal(4).__getattr__("add_option");
      var7 = new PyObject[]{PyString.fromInterned("-p"), PyString.fromInterned("--pattern"), PyString.fromInterned("pattern"), PyString.fromInterned("test*.py"), PyString.fromInterned("Pattern to match tests ('test*.py' default)")};
      var4 = new String[]{"dest", "default", "help"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(187);
      var10000 = var1.getlocal(4).__getattr__("add_option");
      var7 = new PyObject[]{PyString.fromInterned("-t"), PyString.fromInterned("--top-level-directory"), PyString.fromInterned("top"), var1.getglobal("None"), PyString.fromInterned("Top level directory of project (defaults to start directory)")};
      var4 = new String[]{"dest", "default", "help"};
      var10000.__call__(var2, var7, var4);
      var3 = null;
      var1.setline(190);
      var3 = var1.getlocal(4).__getattr__("parse_args").__call__(var2, var1.getlocal(1));
      PyObject[] var9 = Py.unpackSequence(var3, 2);
      PyObject var5 = var9[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var9[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(191);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
      var10000 = var3._gt(Py.newInteger(3));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(192);
         var1.getderef(0).__getattr__("usageExit").__call__(var2);
      }

      var1.setline(194);
      var3 = var1.getglobal("zip").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("start"), PyString.fromInterned("pattern"), PyString.fromInterned("top")})), (PyObject)var1.getlocal(6)).__iter__();

      while(true) {
         var1.setline(194);
         PyObject var11 = var3.__iternext__();
         if (var11 == null) {
            var1.setline(199);
            var3 = var1.getderef(0).__getattr__("failfast");
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(200);
               var3 = var1.getlocal(5).__getattr__("failfast");
               var1.getderef(0).__setattr__("failfast", var3);
               var3 = null;
            }

            var1.setline(201);
            var3 = var1.getderef(0).__getattr__("catchbreak");
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(202);
               var3 = var1.getlocal(5).__getattr__("catchbreak");
               var1.getderef(0).__setattr__("catchbreak", var3);
               var3 = null;
            }

            var1.setline(203);
            var3 = var1.getderef(0).__getattr__("buffer");
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(204);
               var3 = var1.getlocal(5).__getattr__("buffer");
               var1.getderef(0).__setattr__("buffer", var3);
               var3 = null;
            }

            var1.setline(206);
            if (var1.getlocal(5).__getattr__("verbose").__nonzero__()) {
               var1.setline(207);
               PyInteger var12 = Py.newInteger(2);
               var1.getderef(0).__setattr__((String)"verbosity", var12);
               var3 = null;
            }

            var1.setline(209);
            var3 = var1.getlocal(5).__getattr__("start");
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(210);
            var3 = var1.getlocal(5).__getattr__("pattern");
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(211);
            var3 = var1.getlocal(5).__getattr__("top");
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(213);
            var3 = var1.getlocal(2).__call__(var2);
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(214);
            var3 = var1.getlocal(12).__getattr__("discover").__call__(var2, var1.getlocal(9), var1.getlocal(10), var1.getlocal(11));
            var1.getderef(0).__setattr__("test", var3);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var10 = Py.unpackSequence(var11, 2);
         PyObject var6 = var10[0];
         var1.setlocal(7, var6);
         var6 = null;
         var6 = var10[1];
         var1.setlocal(8, var6);
         var6 = null;
         var1.setline(195);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(5), var1.getlocal(7), var1.getlocal(8));
      }
   }

   public PyObject f$7(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyObject var3 = var1.getderef(0).__getattr__("testLoader");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject runTests$8(PyFrame var1, ThreadState var2) {
      var1.setline(217);
      if (var1.getlocal(0).__getattr__("catchbreak").__nonzero__()) {
         var1.setline(218);
         var1.getglobal("installHandler").__call__(var2);
      }

      var1.setline(219);
      PyObject var3 = var1.getlocal(0).__getattr__("testRunner");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(220);
         var3 = var1.getglobal("runner").__getattr__("TextTestRunner");
         var1.getlocal(0).__setattr__("testRunner", var3);
         var3 = null;
      }

      var1.setline(221);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("testRunner"), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("type"), var1.getglobal("types").__getattr__("ClassType")}))).__nonzero__()) {
         try {
            var1.setline(223);
            var10000 = var1.getlocal(0).__getattr__("testRunner");
            PyObject[] var8 = new PyObject[]{var1.getlocal(0).__getattr__("verbosity"), var1.getlocal(0).__getattr__("failfast"), var1.getlocal(0).__getattr__("buffer")};
            String[] var6 = new String[]{"verbosity", "failfast", "buffer"};
            var10000 = var10000.__call__(var2, var8, var6);
            var3 = null;
            var3 = var10000;
            var1.setlocal(1, var3);
            var3 = null;
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (!var7.match(var1.getglobal("TypeError"))) {
               throw var7;
            }

            var1.setline(228);
            PyObject var4 = var1.getlocal(0).__getattr__("testRunner").__call__(var2);
            var1.setlocal(1, var4);
            var4 = null;
         }
      } else {
         var1.setline(231);
         var3 = var1.getlocal(0).__getattr__("testRunner");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(232);
      var3 = var1.getlocal(1).__getattr__("run").__call__(var2, var1.getlocal(0).__getattr__("test"));
      var1.getlocal(0).__setattr__("result", var3);
      var3 = null;
      var1.setline(233);
      if (var1.getlocal(0).__getattr__("exit").__nonzero__()) {
         var1.setline(234);
         var1.getglobal("sys").__getattr__("exit").__call__(var2, var1.getlocal(0).__getattr__("result").__getattr__("wasSuccessful").__call__(var2).__not__());
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public main$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestProgram$1 = Py.newCode(0, var2, var1, "TestProgram", 63, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "module", "defaultTest", "argv", "testRunner", "testLoader", "exit", "verbosity", "failfast", "catchbreak", "buffer", "part"};
      __init__$2 = Py.newCode(11, var2, var1, "__init__", 72, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "usage"};
      usageExit$3 = Py.newCode(2, var2, var1, "usageExit", 97, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "argv", "getopt", "long_opts", "options", "args", "opt", "value", "msg"};
      parseArgs$4 = Py.newCode(2, var2, var1, "parseArgs", 111, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      createTests$5 = Py.newCode(1, var2, var1, "createTests", 153, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "argv", "Loader", "optparse", "parser", "options", "args", "name", "value", "start_dir", "pattern", "top_level_dir", "loader"};
      String[] var10001 = var2;
      main$py var10007 = self;
      var2 = new String[]{"self"};
      _do_discovery$6 = Py.newCode(3, var10001, var1, "_do_discovery", 160, false, false, var10007, 6, var2, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      f$7 = Py.newCode(0, var10001, var1, "<lambda>", 162, false, false, var10007, 7, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "testRunner"};
      runTests$8 = Py.newCode(1, var2, var1, "runTests", 216, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new main$py("unittest/main$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(main$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestProgram$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.usageExit$3(var2, var3);
         case 4:
            return this.parseArgs$4(var2, var3);
         case 5:
            return this.createTests$5(var2, var3);
         case 6:
            return this._do_discovery$6(var2, var3);
         case 7:
            return this.f$7(var2, var3);
         case 8:
            return this.runTests$8(var2, var3);
         default:
            return null;
      }
   }
}
