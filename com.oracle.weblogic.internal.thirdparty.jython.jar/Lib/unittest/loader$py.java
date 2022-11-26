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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("unittest/loader.py")
public class loader$py extends PyFunctionTable implements PyRunnable {
   static loader$py self;
   static final PyCode f$0;
   static final PyCode _make_failed_import_test$1;
   static final PyCode _make_failed_load_tests$2;
   static final PyCode _make_failed_test$3;
   static final PyCode testFailure$4;
   static final PyCode TestLoader$5;
   static final PyCode loadTestsFromTestCase$6;
   static final PyCode loadTestsFromModule$7;
   static final PyCode loadTestsFromName$8;
   static final PyCode loadTestsFromNames$9;
   static final PyCode getTestCaseNames$10;
   static final PyCode isTestMethod$11;
   static final PyCode discover$12;
   static final PyCode _get_directory_containing_module$13;
   static final PyCode _get_name_from_path$14;
   static final PyCode _get_module_from_name$15;
   static final PyCode _match_path$16;
   static final PyCode _find_tests$17;
   static final PyCode _makeLoader$18;
   static final PyCode getTestCaseNames$19;
   static final PyCode makeSuite$20;
   static final PyCode findTestCases$21;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Loading unittests."));
      var1.setline(1);
      PyString.fromInterned("Loading unittests.");
      var1.setline(3);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(9);
      String[] var5 = new String[]{"cmp_to_key"};
      PyObject[] var6 = imp.importFrom("functools", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("_CmpToKey", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"fnmatch"};
      var6 = imp.importFrom("fnmatch", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("fnmatch", var4);
      var4 = null;
      var1.setline(12);
      var5 = new String[]{"case", "suite"};
      var6 = imp.importFrom("", var5, var1, 1);
      var4 = var6[0];
      var1.setlocal("case", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("suite", var4);
      var4 = null;
      var1.setline(14);
      var3 = var1.getname("True");
      var1.setlocal("__unittest", var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[_a-z]\\w*\\.py$"), (PyObject)var1.getname("re").__getattr__("IGNORECASE"));
      var1.setlocal("VALID_MODULE_NAME", var3);
      var3 = null;
      var1.setline(22);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, _make_failed_import_test$1, (PyObject)null);
      var1.setlocal("_make_failed_import_test", var7);
      var3 = null;
      var1.setline(27);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _make_failed_load_tests$2, (PyObject)null);
      var1.setlocal("_make_failed_load_tests", var7);
      var3 = null;
      var1.setline(30);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _make_failed_test$3, (PyObject)null);
      var1.setlocal("_make_failed_test", var7);
      var3 = null;
      var1.setline(38);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestLoader", var6, TestLoader$5);
      var1.setlocal("TestLoader", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(296);
      var3 = var1.getname("TestLoader").__call__(var2);
      var1.setlocal("defaultTestLoader", var3);
      var3 = null;
      var1.setline(299);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, _makeLoader$18, (PyObject)null);
      var1.setlocal("_makeLoader", var7);
      var3 = null;
      var1.setline(307);
      var6 = new PyObject[]{var1.getname("cmp")};
      var7 = new PyFunction(var1.f_globals, var6, getTestCaseNames$19, (PyObject)null);
      var1.setlocal("getTestCaseNames", var7);
      var3 = null;
      var1.setline(310);
      var6 = new PyObject[]{PyString.fromInterned("test"), var1.getname("cmp"), var1.getname("suite").__getattr__("TestSuite")};
      var7 = new PyFunction(var1.f_globals, var6, makeSuite$20, (PyObject)null);
      var1.setlocal("makeSuite", var7);
      var3 = null;
      var1.setline(314);
      var6 = new PyObject[]{PyString.fromInterned("test"), var1.getname("cmp"), var1.getname("suite").__getattr__("TestSuite")};
      var7 = new PyFunction(var1.f_globals, var6, findTestCases$21, (PyObject)null);
      var1.setlocal("findTestCases", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _make_failed_import_test$1(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyObject var3 = PyString.fromInterned("Failed to import test module: %s\n%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getglobal("traceback").__getattr__("format_exc").__call__(var2)}));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(24);
      var3 = var1.getglobal("_make_failed_test").__call__(var2, PyString.fromInterned("ModuleImportFailure"), var1.getlocal(0), var1.getglobal("ImportError").__call__(var2, var1.getlocal(2)), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _make_failed_load_tests$2(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var3 = var1.getglobal("_make_failed_test").__call__(var2, PyString.fromInterned("LoadTestsFailure"), var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _make_failed_test$3(PyFrame var1, ThreadState var2) {
      var1.to_cell(2, 0);
      var1.setline(31);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = testFailure$4;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(33);
      PyDictionary var5 = new PyDictionary(new PyObject[]{var1.getlocal(1), var1.getlocal(4)});
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(34);
      PyObject var6 = var1.getglobal("type").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("case").__getattr__("TestCase")})), (PyObject)var1.getlocal(5));
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(35);
      var6 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(6).__call__(var2, var1.getlocal(1))})));
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject testFailure$4(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      throw Py.makeException(var1.getderef(0));
   }

   public PyObject TestLoader$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("\n    This class is responsible for loading tests according to various criteria\n    and returning them wrapped in a TestSuite\n    "));
      var1.setline(42);
      PyString.fromInterned("\n    This class is responsible for loading tests according to various criteria\n    and returning them wrapped in a TestSuite\n    ");
      var1.setline(43);
      PyString var3 = PyString.fromInterned("test");
      var1.setlocal("testMethodPrefix", var3);
      var3 = null;
      var1.setline(44);
      PyObject var4 = var1.getname("cmp");
      var1.setlocal("sortTestMethodsUsing", var4);
      var3 = null;
      var1.setline(45);
      var4 = var1.getname("suite").__getattr__("TestSuite");
      var1.setlocal("suiteClass", var4);
      var3 = null;
      var1.setline(46);
      var4 = var1.getname("None");
      var1.setlocal("_top_level_dir", var4);
      var3 = null;
      var1.setline(48);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, loadTestsFromTestCase$6, PyString.fromInterned("Return a suite of all tests cases contained in testCaseClass"));
      var1.setlocal("loadTestsFromTestCase", var6);
      var3 = null;
      var1.setline(59);
      var5 = new PyObject[]{var1.getname("True")};
      var6 = new PyFunction(var1.f_globals, var5, loadTestsFromModule$7, PyString.fromInterned("Return a suite of all tests cases contained in the given module"));
      var1.setlocal("loadTestsFromModule", var6);
      var3 = null;
      var1.setline(77);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, loadTestsFromName$8, PyString.fromInterned("Return a suite of all tests cases given a string specifier.\n\n        The name may resolve either to a module, a test case class, a\n        test method within a test case class, or a callable object which\n        returns a TestCase or TestSuite instance.\n\n        The method optionally resolves the names relative to a given module.\n        "));
      var1.setlocal("loadTestsFromName", var6);
      var3 = null;
      var1.setline(124);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, loadTestsFromNames$9, PyString.fromInterned("Return a suite of all tests cases found using the given sequence\n        of string specifiers. See 'loadTestsFromName()'.\n        "));
      var1.setlocal("loadTestsFromNames", var6);
      var3 = null;
      var1.setline(131);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, getTestCaseNames$10, PyString.fromInterned("Return a sorted sequence of method names found within testCaseClass\n        "));
      var1.setlocal("getTestCaseNames", var6);
      var3 = null;
      var1.setline(143);
      var5 = new PyObject[]{PyString.fromInterned("test*.py"), var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, discover$12, PyString.fromInterned("Find and return all test modules from the specified start\n        directory, recursing into subdirectories to find them. Only test files\n        that match the pattern will be loaded. (Using shell style pattern\n        matching.)\n\n        All test modules must be importable from the top level of the project.\n        If the start directory is not the top level directory then the top\n        level directory must be specified separately.\n\n        If a test package name (directory with '__init__.py') matches the\n        pattern then the package will be checked for a 'load_tests' function. If\n        this exists then it will be called with loader, tests, pattern.\n\n        If load_tests exists then discovery does  *not* recurse into the package,\n        load_tests is responsible for loading all tests in the package.\n\n        The pattern is deliberately not stored as a loader attribute so that\n        packages can continue discovery themselves. top_level_dir is stored so\n        load_tests does not need to pass this argument in to loader.discover().\n        "));
      var1.setlocal("discover", var6);
      var3 = null;
      var1.setline(207);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _get_directory_containing_module$13, (PyObject)null);
      var1.setlocal("_get_directory_containing_module", var6);
      var3 = null;
      var1.setline(219);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _get_name_from_path$14, (PyObject)null);
      var1.setlocal("_get_name_from_path", var6);
      var3 = null;
      var1.setline(229);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _get_module_from_name$15, (PyObject)null);
      var1.setlocal("_get_module_from_name", var6);
      var3 = null;
      var1.setline(233);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _match_path$16, (PyObject)null);
      var1.setlocal("_match_path", var6);
      var3 = null;
      var1.setline(237);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _find_tests$17, PyString.fromInterned("Used by discovery. Yields test suites it loads."));
      var1.setlocal("_find_tests", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject loadTestsFromTestCase$6(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyString.fromInterned("Return a suite of all tests cases contained in testCaseClass");
      var1.setline(50);
      if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(1), var1.getglobal("suite").__getattr__("TestSuite")).__nonzero__()) {
         var1.setline(51);
         throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Test cases should not be derived from TestSuite. Maybe you meant to derive from TestCase?")));
      } else {
         var1.setline(53);
         PyObject var3 = var1.getlocal(0).__getattr__("getTestCaseNames").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(54);
         PyObject var10000 = var1.getlocal(2).__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("runTest"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(55);
            PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("runTest")});
            var1.setlocal(2, var4);
            var3 = null;
         }

         var1.setline(56);
         var3 = var1.getlocal(0).__getattr__("suiteClass").__call__(var2, var1.getglobal("map").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(57);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject loadTestsFromModule$7(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyString.fromInterned("Return a suite of all tests cases contained in the given module");
      var1.setline(61);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(62);
      PyObject var7 = var1.getglobal("dir").__call__(var2, var1.getlocal(1)).__iter__();

      while(true) {
         var1.setline(62);
         PyObject var4 = var7.__iternext__();
         PyObject var10000;
         PyObject var5;
         if (var4 == null) {
            var1.setline(67);
            var7 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("load_tests"), (PyObject)var1.getglobal("None"));
            var1.setlocal(6, var7);
            var3 = null;
            var1.setline(68);
            var7 = var1.getlocal(0).__getattr__("suiteClass").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(69);
            var10000 = var1.getlocal(2);
            if (var10000.__nonzero__()) {
               var7 = var1.getlocal(6);
               var10000 = var7._isnot(var1.getglobal("None"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               try {
                  var1.setline(71);
                  var7 = var1.getlocal(6).__call__(var2, var1.getlocal(0), var1.getlocal(3), var1.getglobal("None"));
                  var1.f_lasti = -1;
                  return var7;
               } catch (Throwable var6) {
                  PyException var8 = Py.setException(var6, var1);
                  if (var8.match(var1.getglobal("Exception"))) {
                     var5 = var8.value;
                     var1.setlocal(7, var5);
                     var5 = null;
                     var1.setline(73);
                     var7 = var1.getglobal("_make_failed_load_tests").__call__(var2, var1.getlocal(1).__getattr__("__name__"), var1.getlocal(7), var1.getlocal(0).__getattr__("suiteClass"));
                     var1.f_lasti = -1;
                     return var7;
                  } else {
                     throw var8;
                  }
               }
            } else {
               var1.setline(75);
               var7 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var7;
            }
         }

         var1.setlocal(4, var4);
         var1.setline(63);
         var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(1), var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(64);
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("type"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(5), var1.getglobal("case").__getattr__("TestCase"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(65);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(5)));
         }
      }
   }

   public PyObject loadTestsFromName$8(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyString.fromInterned("Return a suite of all tests cases given a string specifier.\n\n        The name may resolve either to a module, a test case class, a\n        test method within a test case class, or a callable object which\n        returns a TestCase or TestSuite instance.\n\n        The method optionally resolves the names relative to a given module.\n        ");
      var1.setline(86);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(87);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(88);
         var3 = var1.getlocal(3).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
         var1.setlocal(4, var3);
         var3 = null;

         while(true) {
            var1.setline(89);
            if (var1.getlocal(4).__nonzero__()) {
               try {
                  var1.setline(91);
                  var3 = var1.getglobal("__import__").__call__(var2, PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(4)));
                  var1.setlocal(2, var3);
                  var3 = null;
               } catch (Throwable var8) {
                  PyException var9 = Py.setException(var8, var1);
                  if (var9.match(var1.getglobal("ImportError"))) {
                     var1.setline(94);
                     var1.getlocal(4).__delitem__((PyObject)Py.newInteger(-1));
                     var1.setline(95);
                     if (var1.getlocal(4).__not__().__nonzero__()) {
                        var1.setline(96);
                        throw Py.makeException();
                     }
                     continue;
                  }

                  throw var9;
               }
            }

            var1.setline(97);
            var3 = var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
            var1.setlocal(3, var3);
            var3 = null;
            break;
         }
      }

      var1.setline(98);
      var3 = var1.getlocal(2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(99);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(99);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(102);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("types").__getattr__("ModuleType")).__nonzero__()) {
               var1.setline(103);
               var3 = var1.getlocal(0).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(5));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(104);
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("type"));
               if (var10000.__nonzero__()) {
                  var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(5), var1.getglobal("case").__getattr__("TestCase"));
               }

               if (var10000.__nonzero__()) {
                  var1.setline(105);
                  var3 = var1.getlocal(0).__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(5));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(106);
                  var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("types").__getattr__("UnboundMethodType"));
                  if (var10000.__nonzero__()) {
                     var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(7), var1.getglobal("type"));
                     if (var10000.__nonzero__()) {
                        var10000 = var1.getglobal("issubclass").__call__(var2, var1.getlocal(7), var1.getglobal("case").__getattr__("TestCase"));
                     }
                  }

                  if (var10000.__nonzero__()) {
                     var1.setline(109);
                     var3 = var1.getlocal(0).__getattr__("suiteClass").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(7).__call__(var2, var1.getlocal(5).__getattr__("__name__"))})));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(110);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("suite").__getattr__("TestSuite")).__nonzero__()) {
                        var1.setline(111);
                        var3 = var1.getlocal(5);
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(112);
                        if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("__call__")).__nonzero__()) {
                           var1.setline(113);
                           var4 = var1.getlocal(5).__call__(var2);
                           var1.setlocal(8, var4);
                           var4 = null;
                           var1.setline(114);
                           if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(8), var1.getglobal("suite").__getattr__("TestSuite")).__nonzero__()) {
                              var1.setline(115);
                              var3 = var1.getlocal(8);
                              var1.f_lasti = -1;
                              return var3;
                           } else {
                              var1.setline(116);
                              if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(8), var1.getglobal("case").__getattr__("TestCase")).__nonzero__()) {
                                 var1.setline(117);
                                 var3 = var1.getlocal(0).__getattr__("suiteClass").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(8)})));
                                 var1.f_lasti = -1;
                                 return var3;
                              } else {
                                 var1.setline(119);
                                 throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("calling %s returned %s, not a test")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(8)}))));
                              }
                           }
                        } else {
                           var1.setline(122);
                           throw Py.makeException(var1.getglobal("TypeError").__call__(var2, PyString.fromInterned("don't know how to make test from: %s")._mod(var1.getlocal(5))));
                        }
                     }
                  }
               }
            }
         }

         var1.setlocal(6, var4);
         var1.setline(100);
         PyTuple var5 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getglobal("getattr").__call__(var2, var1.getlocal(5), var1.getlocal(6))});
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(7, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(5, var7);
         var7 = null;
         var5 = null;
      }
   }

   public PyObject loadTestsFromNames$9(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyString.fromInterned("Return a suite of all tests cases found using the given sequence\n        of string specifiers. See 'loadTestsFromName()'.\n        ");
      var1.setline(128);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(128);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(128);
            var1.dellocal(4);
            PyList var5 = var10000;
            var1.setlocal(3, var5);
            var3 = null;
            var1.setline(129);
            var3 = var1.getlocal(0).__getattr__("suiteClass").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(128);
         var1.getlocal(4).__call__(var2, var1.getlocal(0).__getattr__("loadTestsFromName").__call__(var2, var1.getlocal(5), var1.getlocal(2)));
      }
   }

   public PyObject getTestCaseNames$10(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyString.fromInterned("Return a sorted sequence of method names found within testCaseClass\n        ");
      var1.setline(134);
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("testMethodPrefix")};
      PyFunction var5 = new PyFunction(var1.f_globals, var3, isTestMethod$11, (PyObject)null);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(138);
      PyObject var6 = var1.getglobal("filter").__call__(var2, var1.getlocal(2), var1.getglobal("dir").__call__(var2, var1.getlocal(1)));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(139);
      if (var1.getlocal(0).__getattr__("sortTestMethodsUsing").__nonzero__()) {
         var1.setline(140);
         PyObject var10000 = var1.getlocal(3).__getattr__("sort");
         var3 = new PyObject[]{var1.getglobal("_CmpToKey").__call__(var2, var1.getlocal(0).__getattr__("sortTestMethodsUsing"))};
         String[] var4 = new String[]{"key"};
         var10000.__call__(var2, var3, var4);
         var3 = null;
      }

      var1.setline(141);
      var6 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject isTestMethod$11(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      PyObject var10000 = var1.getlocal(0).__getattr__("startswith").__call__(var2, var1.getlocal(2));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("getattr").__call__(var2, var1.getlocal(1), var1.getlocal(0)), (PyObject)PyString.fromInterned("__call__"));
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject discover$12(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyString.fromInterned("Find and return all test modules from the specified start\n        directory, recursing into subdirectories to find them. Only test files\n        that match the pattern will be loaded. (Using shell style pattern\n        matching.)\n\n        All test modules must be importable from the top level of the project.\n        If the start directory is not the top level directory then the top\n        level directory must be specified separately.\n\n        If a test package name (directory with '__init__.py') matches the\n        pattern then the package will be checked for a 'load_tests' function. If\n        this exists then it will be called with loader, tests, pattern.\n\n        If load_tests exists then discovery does  *not* recurse into the package,\n        load_tests is responsible for loading all tests in the package.\n\n        The pattern is deliberately not stored as a loader attribute so that\n        packages can continue discovery themselves. top_level_dir is stored so\n        load_tests does not need to pass this argument in to loader.discover().\n        ");
      var1.setline(164);
      PyObject var3 = var1.getglobal("False");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(165);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("_top_level_dir");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(167);
         var3 = var1.getlocal(0).__getattr__("_top_level_dir");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(168);
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(169);
            var3 = var1.getglobal("True");
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(170);
            var3 = var1.getlocal(1);
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(172);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(3));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(174);
      var3 = var1.getlocal(3);
      var10000 = var3._in(var1.getglobal("sys").__getattr__("path"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(179);
         var1.getglobal("sys").__getattr__("path").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(3));
      }

      var1.setline(180);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_top_level_dir", var3);
      var3 = null;
      var1.setline(182);
      var3 = var1.getglobal("False");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(183);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(1))).__nonzero__()) {
         var1.setline(184);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(185);
         var3 = var1.getlocal(1);
         var10000 = var3._ne(var1.getlocal(3));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(186);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__init__.py"))).__not__();
            var1.setlocal(5, var3);
            var3 = null;
         }
      } else {
         label50: {
            PyObject var4;
            try {
               var1.setline(190);
               var1.getglobal("__import__").__call__(var2, var1.getlocal(1));
            } catch (Throwable var5) {
               PyException var6 = Py.setException(var5, var1);
               if (var6.match(var1.getglobal("ImportError"))) {
                  var1.setline(192);
                  var4 = var1.getglobal("True");
                  var1.setlocal(5, var4);
                  var4 = null;
                  break label50;
               }

               throw var6;
            }

            var1.setline(194);
            var4 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(1));
            var1.setlocal(6, var4);
            var4 = null;
            var1.setline(195);
            var4 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getitem__(Py.newInteger(0));
            var1.setlocal(7, var4);
            var4 = null;
            var1.setline(196);
            var4 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(6).__getattr__("__file__")));
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(197);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(198);
               var4 = var1.getlocal(0).__getattr__("_get_directory_containing_module").__call__(var2, var1.getlocal(7));
               var1.getlocal(0).__setattr__("_top_level_dir", var4);
               var4 = null;
               var1.setline(199);
               var1.getglobal("sys").__getattr__("path").__getattr__("remove").__call__(var2, var1.getlocal(3));
            }
         }
      }

      var1.setline(201);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(202);
         throw Py.makeException(var1.getglobal("ImportError").__call__(var2, PyString.fromInterned("Start directory is not importable: %r")._mod(var1.getlocal(1))));
      } else {
         var1.setline(204);
         var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("_find_tests").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(205);
         var3 = var1.getlocal(0).__getattr__("suiteClass").__call__(var2, var1.getlocal(8));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _get_directory_containing_module$13(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyObject var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(209);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(2).__getattr__("__file__"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(211);
      if (var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3)).__getattr__("lower").__call__(var2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__init__.py")).__nonzero__()) {
         var1.setline(212);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(3)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(217);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _get_name_from_path$14(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(1))).__getitem__(Py.newInteger(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(222);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("relpath").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("_top_level_dir"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(223);
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(2)).__not__().__nonzero__()) {
         throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Path must be within the project"));
      } else {
         var1.setline(224);
         if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("..")).__not__().__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Path must be within the project"));
         } else {
            var1.setline(226);
            var3 = var1.getlocal(2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("sep"), (PyObject)PyString.fromInterned("."));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(227);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject _get_module_from_name$15(PyFrame var1, ThreadState var2) {
      var1.setline(230);
      var1.getglobal("__import__").__call__(var2, var1.getlocal(1));
      var1.setline(231);
      PyObject var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _match_path$16(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      PyObject var3 = var1.getglobal("fnmatch").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _find_tests$17(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var6;
      PyObject var12;
      PyObject var18;
      Throwable var19;
      label126: {
         Object[] var5;
         Object[] var7;
         PyObject var11;
         Object var10000;
         boolean var10001;
         switch (var1.f_lasti) {
            case 0:
            default:
               var1.setline(238);
               PyString.fromInterned("Used by discovery. Yields test suites it loads.");
               var1.setline(239);
               var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(1));
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(241);
               var3 = var1.getlocal(3).__iter__();
               break;
            case 1:
               var6 = var1.f_savedlocals;
               var3 = (PyObject)var6[3];
               var4 = (PyObject)var6[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var18 = (PyObject)var10000;
               break;
            case 2:
               var6 = var1.f_savedlocals;
               var3 = (PyObject)var6[3];
               var4 = (PyObject)var6[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var18 = (PyObject)var10000;
               break;
            case 3:
               var5 = var1.f_savedlocals;
               var3 = (PyObject)var5[3];
               var4 = (PyObject)var5[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var18 = (PyObject)var10000;
               var1.setline(287);
               var11 = var1.getlocal(0).__getattr__("_find_tests").__call__(var2, var1.getlocal(5), var1.getlocal(2)).__iter__();
               var1.setline(287);
               var12 = var11.__iternext__();
               if (var12 != null) {
                  var1.setlocal(18, var12);
                  var1.setline(288);
                  var1.setline(288);
                  var18 = var1.getlocal(18);
                  var1.f_lasti = 4;
                  var7 = new Object[8];
                  var7[3] = var3;
                  var7[4] = var4;
                  var7[5] = var11;
                  var7[6] = var12;
                  var1.f_savedlocals = var7;
                  return var18;
               }
               break;
            case 4:
               var7 = var1.f_savedlocals;
               var3 = (PyObject)var7[3];
               var4 = (PyObject)var7[4];
               var11 = (PyObject)var7[5];
               var12 = (PyObject)var7[6];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var18 = (PyObject)var10000;
               var1.setline(287);
               var12 = var11.__iternext__();
               if (var12 != null) {
                  var1.setlocal(18, var12);
                  var1.setline(288);
                  var1.setline(288);
                  var18 = var1.getlocal(18);
                  var1.f_lasti = 4;
                  var7 = new Object[8];
                  var7[3] = var3;
                  var7[4] = var4;
                  var7[5] = var11;
                  var7[6] = var12;
                  var1.f_savedlocals = var7;
                  return var18;
               }
               break;
            case 5:
               var5 = var1.f_savedlocals;
               var3 = (PyObject)var5[3];
               var4 = (PyObject)var5[4];

               try {
                  var10000 = var1.getGeneratorInput();
                  if (var10000 instanceof PyException) {
                     throw (Throwable)var10000;
                  }

                  var18 = (PyObject)var10000;
                  break;
               } catch (Throwable var10) {
                  var19 = var10;
                  var10001 = false;
                  break label126;
               }
            case 6:
               var6 = var1.f_savedlocals;
               var3 = (PyObject)var6[3];
               var4 = (PyObject)var6[4];
               var10000 = var1.getGeneratorInput();
               if (var10000 instanceof PyException) {
                  throw (Throwable)var10000;
               }

               var18 = (PyObject)var10000;
         }

         while(true) {
            var1.setline(241);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(4, var4);
            var1.setline(242);
            var11 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(4));
            var1.setlocal(5, var11);
            var5 = null;
            var1.setline(243);
            if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(5)).__nonzero__()) {
               var1.setline(244);
               if (!var1.getglobal("VALID_MODULE_NAME").__getattr__("match").__call__(var2, var1.getlocal(4)).__not__().__nonzero__()) {
                  var1.setline(247);
                  if (!var1.getlocal(0).__getattr__("_match_path").__call__(var2, var1.getlocal(4), var1.getlocal(5), var1.getlocal(2)).__not__().__nonzero__()) {
                     var1.setline(250);
                     var11 = var1.getlocal(0).__getattr__("_get_name_from_path").__call__(var2, var1.getlocal(5));
                     var1.setlocal(6, var11);
                     var5 = null;

                     try {
                        var1.setline(252);
                        var11 = var1.getlocal(0).__getattr__("_get_module_from_name").__call__(var2, var1.getlocal(6));
                        var1.setlocal(7, var11);
                        var5 = null;
                     } catch (Throwable var8) {
                        Py.setException(var8, var1);
                        var1.setline(254);
                        var1.setline(254);
                        var18 = var1.getglobal("_make_failed_import_test").__call__(var2, var1.getlocal(6), var1.getlocal(0).__getattr__("suiteClass"));
                        var1.f_lasti = 1;
                        var6 = new Object[]{null, null, null, var3, var4, null};
                        var1.f_savedlocals = var6;
                        return var18;
                     }

                     var1.setline(256);
                     var12 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(7), (PyObject)PyString.fromInterned("__file__"), (PyObject)var1.getlocal(5)));
                     var1.setlocal(8, var12);
                     var6 = null;
                     var1.setline(257);
                     var12 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(8)).__getitem__(Py.newInteger(0));
                     var1.setlocal(9, var12);
                     var6 = null;
                     var1.setline(258);
                     if (var1.getlocal(9).__getattr__("lower").__call__(var2).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$py")).__nonzero__()) {
                        var1.setline(259);
                        var12 = var1.getlocal(9).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null);
                        var1.setlocal(9, var12);
                        var6 = null;
                     }

                     var1.setline(260);
                     var12 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(5)).__getitem__(Py.newInteger(0));
                     var1.setlocal(10, var12);
                     var6 = null;
                     var1.setline(261);
                     var12 = var1.getlocal(9).__getattr__("lower").__call__(var2);
                     var18 = var12._ne(var1.getlocal(10).__getattr__("lower").__call__(var2));
                     var6 = null;
                     if (var18.__nonzero__()) {
                        var1.setline(262);
                        var12 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(9));
                        var1.setlocal(11, var12);
                        var6 = null;
                        var1.setline(263);
                        var12 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(5))).__getitem__(Py.newInteger(0));
                        var1.setlocal(12, var12);
                        var6 = null;
                        var1.setline(264);
                        var12 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(5));
                        var1.setlocal(13, var12);
                        var6 = null;
                        var1.setline(265);
                        PyString var14 = PyString.fromInterned("%r module incorrectly imported from %r. Expected %r. Is this module globally installed?");
                        var1.setlocal(14, var14);
                        var6 = null;
                        var1.setline(267);
                        var18 = var1.getglobal("ImportError");
                        PyObject var10002 = var1.getlocal(14);
                        PyObject[] var15 = new PyObject[]{var1.getlocal(12), var1.getlocal(11), var1.getlocal(13)};
                        PyTuple var10003 = new PyTuple(var15);
                        Arrays.fill(var15, (Object)null);
                        throw Py.makeException(var18.__call__(var2, var10002._mod(var10003)));
                     }

                     var1.setline(268);
                     var1.setline(268);
                     var18 = var1.getlocal(0).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(7));
                     var1.f_lasti = 2;
                     var6 = new Object[8];
                     var6[3] = var3;
                     var6[4] = var4;
                     var1.f_savedlocals = var6;
                     return var18;
                  }
               }
            } else {
               var1.setline(269);
               if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(5)).__nonzero__()) {
                  var1.setline(270);
                  if (!var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("__init__.py"))).__not__().__nonzero__()) {
                     var1.setline(273);
                     var11 = var1.getglobal("None");
                     var1.setlocal(15, var11);
                     var5 = null;
                     var1.setline(274);
                     var11 = var1.getglobal("None");
                     var1.setlocal(16, var11);
                     var5 = null;
                     var1.setline(275);
                     if (var1.getglobal("fnmatch").__call__(var2, var1.getlocal(4), var1.getlocal(2)).__nonzero__()) {
                        var1.setline(277);
                        var11 = var1.getlocal(0).__getattr__("_get_name_from_path").__call__(var2, var1.getlocal(5));
                        var1.setlocal(6, var11);
                        var5 = null;
                        var1.setline(278);
                        var11 = var1.getlocal(0).__getattr__("_get_module_from_name").__call__(var2, var1.getlocal(6));
                        var1.setlocal(17, var11);
                        var5 = null;
                        var1.setline(279);
                        var11 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(17), (PyObject)PyString.fromInterned("load_tests"), (PyObject)var1.getglobal("None"));
                        var1.setlocal(15, var11);
                        var5 = null;
                        var1.setline(280);
                        var18 = var1.getlocal(0).__getattr__("loadTestsFromModule");
                        PyObject[] var13 = new PyObject[]{var1.getlocal(17), var1.getglobal("False")};
                        String[] var17 = new String[]{"use_load_tests"};
                        var18 = var18.__call__(var2, var13, var17);
                        var5 = null;
                        var11 = var18;
                        var1.setlocal(16, var11);
                        var5 = null;
                     }

                     var1.setline(282);
                     var11 = var1.getlocal(15);
                     var18 = var11._is(var1.getglobal("None"));
                     var5 = null;
                     if (!var18.__nonzero__()) {
                        try {
                           var1.setline(291);
                           var1.setline(291);
                           var18 = var1.getlocal(15).__call__(var2, var1.getlocal(0), var1.getlocal(16), var1.getlocal(2));
                           var1.f_lasti = 5;
                           var5 = new Object[8];
                           var5[3] = var3;
                           var5[4] = var4;
                           var1.f_savedlocals = var5;
                           return var18;
                        } catch (Throwable var9) {
                           var19 = var9;
                           var10001 = false;
                           break;
                        }
                     }

                     var1.setline(283);
                     var11 = var1.getlocal(16);
                     var18 = var11._isnot(var1.getglobal("None"));
                     var5 = null;
                     if (var18.__nonzero__()) {
                        var1.setline(285);
                        var1.setline(285);
                        var18 = var1.getlocal(16);
                        var1.f_lasti = 3;
                        var5 = new Object[8];
                        var5[3] = var3;
                        var5[4] = var4;
                        var1.f_savedlocals = var5;
                        return var18;
                     }

                     var1.setline(287);
                     var11 = var1.getlocal(0).__getattr__("_find_tests").__call__(var2, var1.getlocal(5), var1.getlocal(2)).__iter__();
                     var1.setline(287);
                     var12 = var11.__iternext__();
                     if (var12 != null) {
                        var1.setlocal(18, var12);
                        var1.setline(288);
                        var1.setline(288);
                        var18 = var1.getlocal(18);
                        var1.f_lasti = 4;
                        var7 = new Object[8];
                        var7[3] = var3;
                        var7[4] = var4;
                        var7[5] = var11;
                        var7[6] = var12;
                        var1.f_savedlocals = var7;
                        return var18;
                     }
                  }
               }
            }
         }
      }

      PyException var16 = Py.setException(var19, var1);
      if (var16.match(var1.getglobal("Exception"))) {
         var12 = var16.value;
         var1.setlocal(19, var12);
         var6 = null;
         var1.setline(293);
         var1.setline(293);
         var18 = var1.getglobal("_make_failed_load_tests").__call__(var2, var1.getlocal(17).__getattr__("__name__"), var1.getlocal(19), var1.getlocal(0).__getattr__("suiteClass"));
         var1.f_lasti = 6;
         var6 = new Object[8];
         var6[3] = var3;
         var6[4] = var4;
         var1.f_savedlocals = var6;
         return var18;
      } else {
         throw var16;
      }
   }

   public PyObject _makeLoader$18(PyFrame var1, ThreadState var2) {
      var1.setline(300);
      PyObject var3 = var1.getglobal("TestLoader").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(301);
      var3 = var1.getlocal(1);
      var1.getlocal(3).__setattr__("sortTestMethodsUsing", var3);
      var3 = null;
      var1.setline(302);
      var3 = var1.getlocal(0);
      var1.getlocal(3).__setattr__("testMethodPrefix", var3);
      var3 = null;
      var1.setline(303);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(304);
         var3 = var1.getlocal(2);
         var1.getlocal(3).__setattr__("suiteClass", var3);
         var3 = null;
      }

      var1.setline(305);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getTestCaseNames$19(PyFrame var1, ThreadState var2) {
      var1.setline(308);
      PyObject var3 = var1.getglobal("_makeLoader").__call__(var2, var1.getlocal(1), var1.getlocal(2)).__getattr__("getTestCaseNames").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject makeSuite$20(PyFrame var1, ThreadState var2) {
      var1.setline(312);
      PyObject var3 = var1.getglobal("_makeLoader").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)).__getattr__("loadTestsFromTestCase").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject findTestCases$21(PyFrame var1, ThreadState var2) {
      var1.setline(316);
      PyObject var3 = var1.getglobal("_makeLoader").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public loader$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"name", "suiteClass", "message"};
      _make_failed_import_test$1 = Py.newCode(2, var2, var1, "_make_failed_import_test", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "exception", "suiteClass"};
      _make_failed_load_tests$2 = Py.newCode(3, var2, var1, "_make_failed_load_tests", 27, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"classname", "methodname", "exception", "suiteClass", "testFailure", "attrs", "TestClass"};
      String[] var10001 = var2;
      loader$py var10007 = self;
      var2 = new String[]{"exception"};
      _make_failed_test$3 = Py.newCode(4, var10001, var1, "_make_failed_test", 30, false, false, var10007, 3, var2, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"exception"};
      testFailure$4 = Py.newCode(1, var10001, var1, "testFailure", 31, false, false, var10007, 4, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      TestLoader$5 = Py.newCode(0, var2, var1, "TestLoader", 38, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "testCaseClass", "testCaseNames", "loaded_suite"};
      loadTestsFromTestCase$6 = Py.newCode(2, var2, var1, "loadTestsFromTestCase", 48, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module", "use_load_tests", "tests", "name", "obj", "load_tests", "e"};
      loadTestsFromModule$7 = Py.newCode(3, var2, var1, "loadTestsFromModule", 59, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "module", "parts", "parts_copy", "obj", "part", "parent", "test"};
      loadTestsFromName$8 = Py.newCode(3, var2, var1, "loadTestsFromName", 77, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "names", "module", "suites", "_[128_18]", "name"};
      loadTestsFromNames$9 = Py.newCode(3, var2, var1, "loadTestsFromNames", 124, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "testCaseClass", "isTestMethod", "testFnNames"};
      getTestCaseNames$10 = Py.newCode(2, var2, var1, "getTestCaseNames", 131, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"attrname", "testCaseClass", "prefix"};
      isTestMethod$11 = Py.newCode(3, var2, var1, "isTestMethod", 134, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "start_dir", "pattern", "top_level_dir", "set_implicit_top", "is_not_importable", "the_module", "top_part", "tests"};
      discover$12 = Py.newCode(4, var2, var1, "discover", 143, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module_name", "module", "full_path"};
      _get_directory_containing_module$13 = Py.newCode(2, var2, var1, "_get_directory_containing_module", 207, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "_relpath", "name"};
      _get_name_from_path$14 = Py.newCode(2, var2, var1, "_get_name_from_path", 219, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      _get_module_from_name$15 = Py.newCode(2, var2, var1, "_get_module_from_name", 229, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "full_path", "pattern"};
      _match_path$16 = Py.newCode(4, var2, var1, "_match_path", 233, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "start_dir", "pattern", "paths", "path", "full_path", "name", "module", "mod_file", "realpath", "fullpath_noext", "module_dir", "mod_name", "expected_dir", "msg", "load_tests", "tests", "package", "test", "e"};
      _find_tests$17 = Py.newCode(3, var2, var1, "_find_tests", 237, false, false, self, 17, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"prefix", "sortUsing", "suiteClass", "loader"};
      _makeLoader$18 = Py.newCode(3, var2, var1, "_makeLoader", 299, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"testCaseClass", "prefix", "sortUsing"};
      getTestCaseNames$19 = Py.newCode(3, var2, var1, "getTestCaseNames", 307, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"testCaseClass", "prefix", "sortUsing", "suiteClass"};
      makeSuite$20 = Py.newCode(4, var2, var1, "makeSuite", 310, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module", "prefix", "sortUsing", "suiteClass"};
      findTestCases$21 = Py.newCode(4, var2, var1, "findTestCases", 314, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new loader$py("unittest/loader$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(loader$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._make_failed_import_test$1(var2, var3);
         case 2:
            return this._make_failed_load_tests$2(var2, var3);
         case 3:
            return this._make_failed_test$3(var2, var3);
         case 4:
            return this.testFailure$4(var2, var3);
         case 5:
            return this.TestLoader$5(var2, var3);
         case 6:
            return this.loadTestsFromTestCase$6(var2, var3);
         case 7:
            return this.loadTestsFromModule$7(var2, var3);
         case 8:
            return this.loadTestsFromName$8(var2, var3);
         case 9:
            return this.loadTestsFromNames$9(var2, var3);
         case 10:
            return this.getTestCaseNames$10(var2, var3);
         case 11:
            return this.isTestMethod$11(var2, var3);
         case 12:
            return this.discover$12(var2, var3);
         case 13:
            return this._get_directory_containing_module$13(var2, var3);
         case 14:
            return this._get_name_from_path$14(var2, var3);
         case 15:
            return this._get_module_from_name$15(var2, var3);
         case 16:
            return this._match_path$16(var2, var3);
         case 17:
            return this._find_tests$17(var2, var3);
         case 18:
            return this._makeLoader$18(var2, var3);
         case 19:
            return this.getTestCaseNames$19(var2, var3);
         case 20:
            return this.makeSuite$20(var2, var3);
         case 21:
            return this.findTestCases$21(var2, var3);
         default:
            return null;
      }
   }
}
