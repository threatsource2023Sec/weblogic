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
@Filename("unittest/test/test_discovery.py")
public class test_discovery$py extends PyFunctionTable implements PyRunnable {
   static test_discovery$py self;
   static final PyCode f$0;
   static final PyCode TestDiscovery$1;
   static final PyCode test_get_name_from_path$2;
   static final PyCode test_find_tests$3;
   static final PyCode restore_listdir$4;
   static final PyCode restore_isfile$5;
   static final PyCode restore_isdir$6;
   static final PyCode f$7;
   static final PyCode isdir$8;
   static final PyCode isfile$9;
   static final PyCode f$10;
   static final PyCode f$11;
   static final PyCode test_find_tests_with_package$12;
   static final PyCode restore_listdir$13;
   static final PyCode restore_isfile$14;
   static final PyCode restore_isdir$15;
   static final PyCode f$16;
   static final PyCode f$17;
   static final PyCode f$18;
   static final PyCode Module$19;
   static final PyCode __init__$20;
   static final PyCode load_tests$21;
   static final PyCode __eq__$22;
   static final PyCode f$23;
   static final PyCode loadTestsFromModule$24;
   static final PyCode test_discover$25;
   static final PyCode restore_isfile$26;
   static final PyCode f$27;
   static final PyCode restore_path$28;
   static final PyCode f$29;
   static final PyCode f$30;
   static final PyCode restore_isdir$31;
   static final PyCode _find_tests$32;
   static final PyCode test_discover_with_modules_that_fail_to_import$33;
   static final PyCode f$34;
   static final PyCode f$35;
   static final PyCode restore$36;
   static final PyCode test_command_line_handling_parseArgs$37;
   static final PyCode do_discovery$38;
   static final PyCode test_command_line_handling_do_discovery_too_many_arguments$39;
   static final PyCode Stop$40;
   static final PyCode usageExit$41;
   static final PyCode test_command_line_handling_do_discovery_uses_default_loader$42;
   static final PyCode Loader$43;
   static final PyCode discover$44;
   static final PyCode test_command_line_handling_do_discovery_calls_loader$45;
   static final PyCode Loader$46;
   static final PyCode discover$47;
   static final PyCode test_detect_module_clash$48;
   static final PyCode Module$49;
   static final PyCode cleanup$50;
   static final PyCode listdir$51;
   static final PyCode isfile$52;
   static final PyCode isdir$53;
   static final PyCode test_discovery_from_dotted_path$54;
   static final PyCode _find_tests$55;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(8);
      PyObject[] var5 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      PyObject var4 = Py.makeClass("TestDiscovery", var5, TestDiscovery$1);
      var1.setlocal("TestDiscovery", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(375);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(376);
         var1.getname("unittest").__getattr__("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestDiscovery$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(11);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_get_name_from_path$2, (PyObject)null);
      var1.setlocal("test_get_name_from_path", var4);
      var3 = null;
      var1.setline(25);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_find_tests$3, (PyObject)null);
      var1.setlocal("test_find_tests", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_find_tests_with_package$12, (PyObject)null);
      var1.setlocal("test_find_tests_with_package", var4);
      var3 = null;
      var1.setline(133);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_discover$25, (PyObject)null);
      var1.setlocal("test_discover", var4);
      var3 = null;
      var1.setline(179);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_discover_with_modules_that_fail_to_import$33, (PyObject)null);
      var1.setlocal("test_discover_with_modules_that_fail_to_import", var4);
      var3 = null;
      var1.setline(201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_command_line_handling_parseArgs$37, (PyObject)null);
      var1.setlocal("test_command_line_handling_parseArgs", var4);
      var3 = null;
      var1.setline(215);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_command_line_handling_do_discovery_too_many_arguments$39, (PyObject)null);
      var1.setlocal("test_command_line_handling_do_discovery_too_many_arguments", var4);
      var3 = null;
      var1.setline(230);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_command_line_handling_do_discovery_uses_default_loader$42, (PyObject)null);
      var1.setlocal("test_command_line_handling_do_discovery_uses_default_loader", var4);
      var3 = null;
      var1.setline(243);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_command_line_handling_do_discovery_calls_loader$45, (PyObject)null);
      var1.setlocal("test_command_line_handling_do_discovery_calls_loader", var4);
      var3 = null;
      var1.setline(317);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_detect_module_clash$48, (PyObject)null);
      var1.setlocal("test_detect_module_clash", var4);
      var3 = null;
      var1.setline(358);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_discovery_from_dotted_path$54, (PyObject)null);
      var1.setlocal("test_discovery_from_dotted_path", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_get_name_from_path$2(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(12);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(14);
      PyString var6 = PyString.fromInterned("/foo");
      var1.getlocal(1).__setattr__((String)"_top_level_dir", var6);
      var3 = null;
      var1.setline(15);
      var3 = var1.getlocal(1).__getattr__("_get_name_from_path").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/foo/bar/baz.py"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(16);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("bar.baz"));
      var1.setline(18);
      if (var1.getglobal("__debug__").__not__().__nonzero__()) {
         var1.setline(20);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         ContextManager var7;
         PyObject var4 = (var7 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("AssertionError")))).__enter__(var2);

         label18: {
            try {
               var1.setline(23);
               var1.getlocal(1).__getattr__("_get_name_from_path").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/bar/baz.py"));
            } catch (Throwable var5) {
               if (var7.__exit__(var2, Py.setException(var5, var1))) {
                  break label18;
               }

               throw (Throwable)Py.makeException();
            }

            var7.__exit__(var2, (PyException)null);
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_find_tests$3(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(28);
      var3 = var1.getglobal("os").__getattr__("listdir");
      var1.setderef(3, var3);
      var3 = null;
      var1.setline(29);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = restore_listdir$4;
      var5 = new PyObject[]{var1.getclosure(3)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(31);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("isfile");
      var1.setderef(2, var3);
      var3 = null;
      var1.setline(32);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = restore_isfile$5;
      var5 = new PyObject[]{var1.getclosure(2)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(34);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("isdir");
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(35);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = restore_isdir$6;
      var5 = new PyObject[]{var1.getclosure(0)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(38);
      PyList var7 = new PyList(new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("test1.py"), PyString.fromInterned("test2.py"), PyString.fromInterned("not_a_test.py"), PyString.fromInterned("test_dir"), PyString.fromInterned("test.foo"), PyString.fromInterned("test-not-a-module.py"), PyString.fromInterned("another_dir")}), new PyList(new PyObject[]{PyString.fromInterned("test3.py"), PyString.fromInterned("test4.py")})});
      var1.setderef(1, var7);
      var3 = null;
      var1.setline(41);
      var1.setline(41);
      var5 = Py.EmptyObjects;
      var10003 = var5;
      var10002 = var1.f_globals;
      var10004 = f$7;
      var5 = new PyObject[]{var1.getclosure(1)};
      var6 = new PyFunction(var10002, var10003, var10004, var5);
      var1.getglobal("os").__setattr__((String)"listdir", var6);
      var3 = null;
      var1.setline(42);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getlocal(2));
      var1.setline(44);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, isdir$8, (PyObject)null);
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(46);
      var3 = var1.getlocal(5);
      var1.getglobal("os").__getattr__("path").__setattr__("isdir", var3);
      var3 = null;
      var1.setline(47);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getlocal(4));
      var1.setline(49);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, isfile$9, (PyObject)null);
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(52);
      var3 = var1.getlocal(6);
      var1.getglobal("os").__getattr__("path").__setattr__("isfile", var3);
      var3 = null;
      var1.setline(53);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getlocal(3));
      var1.setline(55);
      var1.setline(55);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, f$10);
      var1.getlocal(1).__setattr__((String)"_get_module_from_name", var6);
      var3 = null;
      var1.setline(56);
      var1.setline(56);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, f$11);
      var1.getlocal(1).__setattr__((String)"loadTestsFromModule", var6);
      var3 = null;
      var1.setline(58);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/foo"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getlocal(7);
      var1.getlocal(1).__setattr__("_top_level_dir", var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getglobal("list").__call__(var2, var1.getlocal(1).__getattr__("_find_tests").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("test*.py")));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(62);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(62);
      var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("test1"), PyString.fromInterned("test2")})).__iter__();

      while(true) {
         var1.setline(62);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(62);
            var1.dellocal(10);
            var7 = var10000;
            var1.setlocal(9, var7);
            var3 = null;
            var1.setline(64);
            PyObject var8 = var1.getlocal(9).__getattr__("extend");
            PyList var9 = new PyList();
            var3 = var9.__getattr__("append");
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(64);
            var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("test3"), PyString.fromInterned("test4")})).__iter__();

            while(true) {
               var1.setline(64);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(64);
                  var1.dellocal(12);
                  var8.__call__((ThreadState)var2, (PyObject)var9);
                  var1.setline(66);
                  var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(8), var1.getlocal(9));
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(11, var4);
               var1.setline(64);
               var1.getlocal(12).__call__(var2, PyString.fromInterned("test_dir.%s")._mod(var1.getlocal(11))._add(PyString.fromInterned(" module tests")));
            }
         }

         var1.setlocal(11, var4);
         var1.setline(62);
         var1.getlocal(10).__call__(var2, var1.getlocal(11)._add(PyString.fromInterned(" module tests")));
      }
   }

   public PyObject restore_listdir$4(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("os").__setattr__("listdir", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject restore_isfile$5(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("os").__getattr__("path").__setattr__("isfile", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject restore_isdir$6(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("os").__getattr__("path").__setattr__("isdir", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$7(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3 = var1.getderef(0).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isdir$8(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject var3 = var1.getlocal(0).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dir"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isfile$9(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyObject var10000 = var1.getlocal(0).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dir")).__not__();
      if (var10000.__nonzero__()) {
         PyString var3 = PyString.fromInterned("another_dir");
         var10000 = var3._in(var1.getlocal(0));
         var3 = null;
         var10000 = var10000.__not__();
      }

      PyObject var4 = var10000;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject f$10(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyObject var3 = var1.getlocal(0)._add(PyString.fromInterned(" module"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$11(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3 = var1.getlocal(0)._add(PyString.fromInterned(" tests"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_find_tests_with_package$12(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 6);
      var1.setline(69);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getglobal("os").__getattr__("listdir");
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(72);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = restore_listdir$13;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(74);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("isfile");
      var1.setderef(4, var3);
      var3 = null;
      var1.setline(75);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = restore_isfile$14;
      var5 = new PyObject[]{var1.getclosure(4)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(77);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("isdir");
      var1.setderef(3, var3);
      var3 = null;
      var1.setline(78);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = restore_isdir$15;
      var5 = new PyObject[]{var1.getclosure(3)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(81);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("a_directory"), PyString.fromInterned("test_directory"), PyString.fromInterned("test_directory2")});
      var1.setderef(5, var7);
      var3 = null;
      var1.setline(82);
      var7 = new PyList(new PyObject[]{var1.getderef(5), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects)});
      var1.setderef(2, var7);
      var3 = null;
      var1.setline(83);
      var1.setline(83);
      var5 = Py.EmptyObjects;
      var10003 = var5;
      var10002 = var1.f_globals;
      var10004 = f$16;
      var5 = new PyObject[]{var1.getclosure(2)};
      var6 = new PyFunction(var10002, var10003, var10004, var5);
      var1.getglobal("os").__setattr__((String)"listdir", var6);
      var3 = null;
      var1.setline(84);
      var1.getderef(6).__getattr__("addCleanup").__call__(var2, var1.getlocal(2));
      var1.setline(86);
      var1.setline(86);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, f$17);
      var1.getglobal("os").__getattr__("path").__setattr__((String)"isdir", var6);
      var3 = null;
      var1.setline(87);
      var1.getderef(6).__getattr__("addCleanup").__call__(var2, var1.getlocal(4));
      var1.setline(89);
      var1.setline(89);
      var5 = Py.EmptyObjects;
      var10003 = var5;
      var10002 = var1.f_globals;
      var10004 = f$18;
      var5 = new PyObject[]{var1.getclosure(5)};
      var6 = new PyFunction(var10002, var10003, var10004, var5);
      var1.getglobal("os").__getattr__("path").__setattr__((String)"isfile", var6);
      var3 = null;
      var1.setline(90);
      var1.getderef(6).__getattr__("addCleanup").__call__(var2, var1.getlocal(3));
      var1.setline(92);
      var5 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Module", var5, Module$19);
      var1.setderef(1, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(111);
      var1.setline(111);
      var5 = Py.EmptyObjects;
      var10003 = var5;
      var10002 = var1.f_globals;
      var10004 = f$23;
      var5 = new PyObject[]{var1.getclosure(1)};
      var6 = new PyFunction(var10002, var10003, var10004, var5);
      var1.getlocal(1).__setattr__((String)"_get_module_from_name", var6);
      var3 = null;
      var1.setline(112);
      var5 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var5;
      var10004 = loadTestsFromModule$24;
      var5 = new PyObject[]{var1.getclosure(6)};
      var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(116);
      var3 = var1.getlocal(5);
      var1.getlocal(1).__setattr__("loadTestsFromModule", var3);
      var3 = null;
      var1.setline(118);
      PyString var8 = PyString.fromInterned("/foo");
      var1.getlocal(1).__setattr__((String)"_top_level_dir", var8);
      var3 = null;
      var1.setline(121);
      var3 = var1.getglobal("list").__call__(var2, var1.getlocal(1).__getattr__("_find_tests").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/foo"), (PyObject)PyString.fromInterned("test*")));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(125);
      var1.getderef(6).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("load_tests"), PyString.fromInterned("test_directory2")._add(PyString.fromInterned(" module tests"))})));
      var1.setline(127);
      var1.getderef(6).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(1).__getattr__("paths"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("test_directory"), PyString.fromInterned("test_directory2")})));
      var1.setline(130);
      var1.getderef(6).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(1).__getattr__("load_tests_args"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(1), PyString.fromInterned("test_directory")._add(PyString.fromInterned(" module tests")), PyString.fromInterned("test*")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject restore_listdir$13(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("os").__setattr__("listdir", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject restore_isfile$14(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("os").__getattr__("path").__setattr__("isfile", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject restore_isdir$15(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("os").__getattr__("path").__setattr__("isdir", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$16(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      PyObject var3 = var1.getderef(0).__getattr__("pop").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$17(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$18(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._notin(var1.getderef(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Module$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(93);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("paths", var3);
      var3 = null;
      var1.setline(94);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("load_tests_args", var3);
      var3 = null;
      var1.setline(96);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$20, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(105);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __eq__$22, (PyObject)null);
      var1.setlocal("__eq__", var5);
      var3 = null;
      var1.setline(109);
      PyObject var6 = var1.getname("None");
      var1.setlocal("__hash__", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$20(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(97);
      PyObject var3 = var1.getlocal(1);
      var1.getderef(0).__setattr__("path", var3);
      var3 = null;
      var1.setline(98);
      var1.getderef(0).__getattr__("paths").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(99);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(PyString.fromInterned("test_directory"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(100);
         PyObject[] var4 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var4;
         PyCode var10004 = load_tests$21;
         var4 = new PyObject[]{var1.getclosure(0)};
         PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
         var1.setlocal(2, var5);
         var3 = null;
         var1.setline(103);
         var3 = var1.getlocal(2);
         var1.getderef(0).__setattr__("load_tests", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject load_tests$21(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      var1.getderef(0).__getattr__("load_tests_args").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2)})));
      var1.setline(102);
      PyString var3 = PyString.fromInterned("load_tests");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __eq__$22(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3 = var1.getlocal(0).__getattr__("path");
      PyObject var10000 = var3._eq(var1.getlocal(1).__getattr__("path"));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$23(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyObject var3 = var1.getderef(0).__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject loadTestsFromModule$24(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(114);
         throw Py.makeException(var1.getderef(0).__getattr__("failureException").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("use_load_tests should be False for packages")));
      } else {
         var1.setline(115);
         PyObject var3 = var1.getlocal(0).__getattr__("path")._add(PyString.fromInterned(" module tests"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject test_discover$25(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(134);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(136);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("isfile");
      var1.setderef(3, var3);
      var3 = null;
      var1.setline(137);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("isdir");
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(138);
      PyObject[] var8 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var8;
      PyCode var10004 = restore_isfile$26;
      var8 = new PyObject[]{var1.getclosure(3)};
      PyFunction var9 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var8);
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(141);
      var1.setline(141);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, f$27);
      var1.getglobal("os").__getattr__("path").__setattr__((String)"isfile", var9);
      var3 = null;
      var1.setline(142);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getlocal(2));
      var1.setline(144);
      var3 = var1.getglobal("sys").__getattr__("path").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setderef(2, var3);
      var3 = null;
      var1.setline(145);
      var8 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var8;
      var10004 = restore_path$28;
      var8 = new PyObject[]{var1.getclosure(2)};
      var9 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var8);
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(147);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getlocal(3));
      var1.setline(149);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/foo")));
      var1.setlocal(4, var3);
      var3 = null;
      ContextManager var10;
      PyObject var4 = (var10 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("ImportError")))).__enter__(var2);

      label16: {
         try {
            var1.setline(151);
            PyObject var10000 = var1.getlocal(1).__getattr__("discover");
            PyObject[] var7 = new PyObject[]{PyString.fromInterned("/foo/bar"), PyString.fromInterned("/foo")};
            String[] var5 = new String[]{"top_level_dir"};
            var10000.__call__(var2, var7, var5);
            var4 = null;
         } catch (Throwable var6) {
            if (var10.__exit__(var2, Py.setException(var6, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var10.__exit__(var2, (PyException)null);
      }

      var1.setline(153);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("_top_level_dir"), var1.getlocal(4));
      var1.setline(154);
      var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getlocal(4), var1.getglobal("sys").__getattr__("path"));
      var1.setline(156);
      var1.setline(156);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, f$29);
      var1.getglobal("os").__getattr__("path").__setattr__((String)"isfile", var9);
      var3 = null;
      var1.setline(157);
      var1.setline(157);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, f$30);
      var1.getglobal("os").__getattr__("path").__setattr__((String)"isdir", var9);
      var3 = null;
      var1.setline(159);
      var8 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var8;
      var10004 = restore_isdir$31;
      var8 = new PyObject[]{var1.getclosure(1)};
      var9 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var8);
      var1.setlocal(5, var9);
      var3 = null;
      var1.setline(161);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getlocal(5));
      var1.setline(163);
      PyList var11 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var11);
      var3 = null;
      var1.setline(164);
      var8 = Py.EmptyObjects;
      var10002 = var1.f_globals;
      var10003 = var8;
      var10004 = _find_tests$32;
      var8 = new PyObject[]{var1.getclosure(0)};
      var9 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var8);
      var1.setlocal(6, var9);
      var3 = null;
      var1.setline(167);
      var3 = var1.getlocal(6);
      var1.getlocal(1).__setattr__("_find_tests", var3);
      var3 = null;
      var1.setline(168);
      var3 = var1.getglobal("str");
      var1.getlocal(1).__setattr__("suiteClass", var3);
      var3 = null;
      var1.setline(170);
      var3 = var1.getlocal(1).__getattr__("discover").__call__((ThreadState)var2, PyString.fromInterned("/foo/bar/baz"), (PyObject)PyString.fromInterned("pattern"), (PyObject)PyString.fromInterned("/foo/bar"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(172);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/foo/bar"));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(173);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/foo/bar/baz"));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(174);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("['tests']"));
      var1.setline(175);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("_top_level_dir"), var1.getlocal(8));
      var1.setline(176);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(9), PyString.fromInterned("pattern")})})));
      var1.setline(177);
      var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getlocal(8), var1.getglobal("sys").__getattr__("path"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject restore_isfile$26(PyFrame var1, ThreadState var2) {
      var1.setline(139);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("os").__getattr__("path").__setattr__("isfile", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$27(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var3 = var1.getglobal("False");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject restore_path$28(PyFrame var1, ThreadState var2) {
      var1.setline(146);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("sys").__getattr__("path").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$29(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$30(PyFrame var1, ThreadState var2) {
      var1.setline(157);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject restore_isdir$31(PyFrame var1, ThreadState var2) {
      var1.setline(160);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("os").__getattr__("path").__setattr__("isdir", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _find_tests$32(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      var1.getderef(0).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0), var1.getlocal(1)})));
      var1.setline(166);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("tests")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_discover_with_modules_that_fail_to_import$33(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(180);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(182);
      var3 = var1.getglobal("os").__getattr__("listdir");
      var1.setderef(2, var3);
      var3 = null;
      var1.setline(183);
      var1.setline(183);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, f$34);
      var1.getglobal("os").__setattr__((String)"listdir", var7);
      var3 = null;
      var1.setline(184);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("isfile");
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(185);
      var1.setline(185);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, f$35);
      var1.getglobal("os").__getattr__("path").__setattr__((String)"isfile", var7);
      var3 = null;
      var1.setline(186);
      var3 = var1.getglobal("sys").__getattr__("path").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(187);
      var6 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var6;
      PyCode var10004 = restore$36;
      var6 = new PyObject[]{var1.getclosure(1), var1.getclosure(2), var1.getclosure(0)};
      var7 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var6);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(191);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getlocal(2));
      var1.setline(193);
      var3 = var1.getlocal(1).__getattr__("discover").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(194);
      var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getglobal("os").__getattr__("getcwd").__call__(var2), var1.getglobal("sys").__getattr__("path"));
      var1.setline(195);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("countTestCases").__call__(var2), (PyObject)Py.newInteger(1));
      var1.setline(196);
      var3 = var1.getglobal("list").__call__(var2, var1.getglobal("list").__call__(var2, var1.getlocal(3)).__getitem__(Py.newInteger(0))).__getitem__(Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      ContextManager var8;
      PyObject var4 = (var8 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("ImportError")))).__enter__(var2);

      label16: {
         try {
            var1.setline(199);
            var1.getlocal(4).__getattr__("test_this_does_not_exist").__call__(var2);
         } catch (Throwable var5) {
            if (var8.__exit__(var2, Py.setException(var5, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var8.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$34(PyFrame var1, ThreadState var2) {
      var1.setline(183);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("test_this_does_not_exist.py")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$35(PyFrame var1, ThreadState var2) {
      var1.setline(185);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject restore$36(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("os").__getattr__("path").__setattr__("isfile", var3);
      var3 = null;
      var1.setline(189);
      var3 = var1.getderef(1);
      var1.getglobal("os").__setattr__("listdir", var3);
      var3 = null;
      var1.setline(190);
      var3 = var1.getderef(2);
      var1.getglobal("sys").__getattr__("path").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_command_line_handling_parseArgs$37(PyFrame var1, ThreadState var2) {
      var1.setline(203);
      PyObject var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(205);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var4);
      var3 = null;
      var1.setline(206);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = do_discovery$38;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(208);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("_do_discovery", var3);
      var3 = null;
      var1.setline(209);
      var1.getlocal(1).__getattr__("parseArgs").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("something"), PyString.fromInterned("discover")})));
      var1.setline(210);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(212);
      var1.getlocal(1).__getattr__("parseArgs").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("something"), PyString.fromInterned("discover"), PyString.fromInterned("foo"), PyString.fromInterned("bar")})));
      var1.setline(213);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getderef(0), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("foo"), PyString.fromInterned("bar")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject do_discovery$38(PyFrame var1, ThreadState var2) {
      var1.setline(207);
      var1.getderef(0).__getattr__("extend").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_command_line_handling_do_discovery_too_many_arguments$39(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(216);
      PyObject[] var3 = new PyObject[]{var1.getglobal("Exception")};
      PyObject var4 = Py.makeClass("Stop", var3, Stop$40);
      var1.setderef(0, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(218);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = usageExit$41;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(221);
      PyObject var7 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(222);
      var7 = var1.getlocal(1);
      var1.getlocal(2).__setattr__("usageExit", var7);
      var3 = null;
      var1.setline(223);
      var7 = var1.getglobal("None");
      var1.getlocal(2).__setattr__("testLoader", var7);
      var3 = null;
      ContextManager var8;
      var4 = (var8 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getderef(0)))).__enter__(var2);

      label16: {
         try {
            var1.setline(227);
            var1.getlocal(2).__getattr__("_do_discovery").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("one"), PyString.fromInterned("two"), PyString.fromInterned("three"), PyString.fromInterned("four")})));
         } catch (Throwable var5) {
            if (var8.__exit__(var2, Py.setException(var5, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var8.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Stop$40(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(217);
      return var1.getf_locals();
   }

   public PyObject usageExit$41(PyFrame var1, ThreadState var2) {
      var1.setline(219);
      throw Py.makeException(var1.getderef(0));
   }

   public PyObject test_command_line_handling_do_discovery_uses_default_loader$42(PyFrame var1, ThreadState var2) {
      var1.setline(231);
      PyObject var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(233);
      PyObject[] var5 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Loader", var5, Loader$43);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(239);
      var3 = var1.getlocal(2).__call__(var2);
      var1.getlocal(1).__setattr__("testLoader", var3);
      var3 = null;
      var1.setline(240);
      var1.getlocal(1).__getattr__("_do_discovery").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("-v")})));
      var1.setline(241);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("args"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("."), PyString.fromInterned("test*.py"), var1.getglobal("None")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Loader$43(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(234);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("args", var3);
      var3 = null;
      var1.setline(235);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, discover$44, (PyObject)null);
      var1.setlocal("discover", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject discover$44(PyFrame var1, ThreadState var2) {
      var1.setline(236);
      var1.getlocal(0).__getattr__("args").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)})));
      var1.setline(237);
      PyString var3 = PyString.fromInterned("tests");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_command_line_handling_do_discovery_calls_loader$45(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyObject var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(246);
      PyObject[] var5 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Loader", var5, Loader$46);
      var1.setlocal(2, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(252);
      PyObject var10000 = var1.getlocal(1).__getattr__("_do_discovery");
      var5 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("-v")}), var1.getlocal(2)};
      String[] var6 = new String[]{"Loader"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.setline(253);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("verbosity"), (PyObject)Py.newInteger(2));
      var1.setline(254);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("test"), (PyObject)PyString.fromInterned("tests"));
      var1.setline(255);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("args"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("."), PyString.fromInterned("test*.py"), var1.getglobal("None")})})));
      var1.setline(257);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(2).__setattr__((String)"args", var7);
      var3 = null;
      var1.setline(258);
      var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(259);
      var10000 = var1.getlocal(1).__getattr__("_do_discovery");
      var5 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("--verbose")}), var1.getlocal(2)};
      var6 = new String[]{"Loader"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.setline(260);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("test"), (PyObject)PyString.fromInterned("tests"));
      var1.setline(261);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("args"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("."), PyString.fromInterned("test*.py"), var1.getglobal("None")})})));
      var1.setline(263);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(2).__setattr__((String)"args", var7);
      var3 = null;
      var1.setline(264);
      var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(265);
      var10000 = var1.getlocal(1).__getattr__("_do_discovery");
      var5 = new PyObject[]{new PyList(Py.EmptyObjects), var1.getlocal(2)};
      var6 = new String[]{"Loader"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.setline(266);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("test"), (PyObject)PyString.fromInterned("tests"));
      var1.setline(267);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("args"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("."), PyString.fromInterned("test*.py"), var1.getglobal("None")})})));
      var1.setline(269);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(2).__setattr__((String)"args", var7);
      var3 = null;
      var1.setline(270);
      var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(271);
      var10000 = var1.getlocal(1).__getattr__("_do_discovery");
      var5 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("fish")}), var1.getlocal(2)};
      var6 = new String[]{"Loader"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.setline(272);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("test"), (PyObject)PyString.fromInterned("tests"));
      var1.setline(273);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("args"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("fish"), PyString.fromInterned("test*.py"), var1.getglobal("None")})})));
      var1.setline(275);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(2).__setattr__((String)"args", var7);
      var3 = null;
      var1.setline(276);
      var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(277);
      var10000 = var1.getlocal(1).__getattr__("_do_discovery");
      var5 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("fish"), PyString.fromInterned("eggs")}), var1.getlocal(2)};
      var6 = new String[]{"Loader"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.setline(278);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("test"), (PyObject)PyString.fromInterned("tests"));
      var1.setline(279);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("args"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("fish"), PyString.fromInterned("eggs"), var1.getglobal("None")})})));
      var1.setline(281);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(2).__setattr__((String)"args", var7);
      var3 = null;
      var1.setline(282);
      var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(283);
      var10000 = var1.getlocal(1).__getattr__("_do_discovery");
      var5 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("fish"), PyString.fromInterned("eggs"), PyString.fromInterned("ham")}), var1.getlocal(2)};
      var6 = new String[]{"Loader"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.setline(284);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("test"), (PyObject)PyString.fromInterned("tests"));
      var1.setline(285);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("args"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("fish"), PyString.fromInterned("eggs"), PyString.fromInterned("ham")})})));
      var1.setline(287);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(2).__setattr__((String)"args", var7);
      var3 = null;
      var1.setline(288);
      var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(289);
      var10000 = var1.getlocal(1).__getattr__("_do_discovery");
      var5 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("-s"), PyString.fromInterned("fish")}), var1.getlocal(2)};
      var6 = new String[]{"Loader"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.setline(290);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("test"), (PyObject)PyString.fromInterned("tests"));
      var1.setline(291);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("args"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("fish"), PyString.fromInterned("test*.py"), var1.getglobal("None")})})));
      var1.setline(293);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(2).__setattr__((String)"args", var7);
      var3 = null;
      var1.setline(294);
      var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(295);
      var10000 = var1.getlocal(1).__getattr__("_do_discovery");
      var5 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("-t"), PyString.fromInterned("fish")}), var1.getlocal(2)};
      var6 = new String[]{"Loader"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.setline(296);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("test"), (PyObject)PyString.fromInterned("tests"));
      var1.setline(297);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("args"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("."), PyString.fromInterned("test*.py"), PyString.fromInterned("fish")})})));
      var1.setline(299);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(2).__setattr__((String)"args", var7);
      var3 = null;
      var1.setline(300);
      var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(301);
      var10000 = var1.getlocal(1).__getattr__("_do_discovery");
      var5 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("-p"), PyString.fromInterned("fish")}), var1.getlocal(2)};
      var6 = new String[]{"Loader"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.setline(302);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("test"), (PyObject)PyString.fromInterned("tests"));
      var1.setline(303);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("args"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("."), PyString.fromInterned("fish"), var1.getglobal("None")})})));
      var1.setline(304);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(1).__getattr__("failfast"));
      var1.setline(305);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getlocal(1).__getattr__("catchbreak"));
      var1.setline(307);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(2).__setattr__((String)"args", var7);
      var3 = null;
      var1.setline(308);
      var3 = var1.getglobal("object").__getattr__("__new__").__call__(var2, var1.getglobal("unittest").__getattr__("TestProgram"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(309);
      var10000 = var1.getlocal(1).__getattr__("_do_discovery");
      var5 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("-p"), PyString.fromInterned("eggs"), PyString.fromInterned("-s"), PyString.fromInterned("fish"), PyString.fromInterned("-v"), PyString.fromInterned("-f"), PyString.fromInterned("-c")}), var1.getlocal(2)};
      var6 = new String[]{"Loader"};
      var10000.__call__(var2, var5, var6);
      var3 = null;
      var1.setline(311);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("test"), (PyObject)PyString.fromInterned("tests"));
      var1.setline(312);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("args"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("fish"), PyString.fromInterned("eggs"), var1.getglobal("None")})})));
      var1.setline(313);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("verbosity"), (PyObject)Py.newInteger(2));
      var1.setline(314);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("failfast"));
      var1.setline(315);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("catchbreak"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Loader$46(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(247);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("args", var3);
      var3 = null;
      var1.setline(248);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, discover$47, (PyObject)null);
      var1.setlocal("discover", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject discover$47(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      var1.getlocal(0).__getattr__("args").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)})));
      var1.setline(250);
      PyString var3 = PyString.fromInterned("tests");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_detect_module_clash$48(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      PyObject[] var3 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Module", var3, Module$49);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var3, (Object)null);
      var1.setline(320);
      PyObject var5 = var1.getlocal(1);
      var1.getglobal("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("foo"), var5);
      var3 = null;
      var1.setline(321);
      var5 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"));
      var1.setderef(0, var5);
      var3 = null;
      var1.setline(322);
      var5 = var1.getglobal("os").__getattr__("listdir");
      var1.setderef(3, var5);
      var3 = null;
      var1.setline(323);
      var5 = var1.getglobal("os").__getattr__("path").__getattr__("isfile");
      var1.setderef(2, var5);
      var3 = null;
      var1.setline(324);
      var5 = var1.getglobal("os").__getattr__("path").__getattr__("isdir");
      var1.setderef(1, var5);
      var3 = null;
      var1.setline(326);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = cleanup$50;
      var3 = new PyObject[]{var1.getclosure(3), var1.getclosure(2), var1.getclosure(1), var1.getclosure(0)};
      PyFunction var7 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(333);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getlocal(2));
      var1.setline(335);
      var3 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var3, listdir$51, (PyObject)null);
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(337);
      var3 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var3, isfile$52, (PyObject)null);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(339);
      var3 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var3, isdir$53, (PyObject)null);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(341);
      var5 = var1.getlocal(3);
      var1.getglobal("os").__setattr__("listdir", var5);
      var3 = null;
      var1.setline(342);
      var5 = var1.getlocal(4);
      var1.getglobal("os").__getattr__("path").__setattr__("isfile", var5);
      var3 = null;
      var1.setline(343);
      var5 = var1.getlocal(5);
      var1.getglobal("os").__getattr__("path").__setattr__("isdir", var5);
      var3 = null;
      var1.setline(345);
      var5 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(347);
      var5 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bar"));
      var1.setlocal(7, var5);
      var3 = null;
      var1.setline(348);
      var5 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"));
      var1.setlocal(8, var5);
      var3 = null;
      var1.setline(349);
      var5 = var1.getglobal("re").__getattr__("escape").__call__(var2, PyString.fromInterned("'foo' module incorrectly imported from %r. Expected %r. Is this module globally installed?")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8)})));
      var1.setlocal(9, var5);
      var3 = null;
      var1.setline(351);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertRaisesRegexp");
      var3 = new PyObject[]{var1.getglobal("ImportError"), PyString.fromInterned("^%s$")._mod(var1.getlocal(9)), var1.getlocal(6).__getattr__("discover"), PyString.fromInterned("foo"), PyString.fromInterned("foo.py")};
      String[] var6 = new String[]{"start_dir", "pattern"};
      var10000.__call__(var2, var3, var6);
      var3 = null;
      var1.setline(355);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("sys").__getattr__("path").__getitem__(Py.newInteger(0)), var1.getderef(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Module$49(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(319);
      PyString var3 = PyString.fromInterned("bar/foo.py");
      var1.setlocal("__file__", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject cleanup$50(PyFrame var1, ThreadState var2) {
      var1.setline(327);
      PyObject var3 = var1.getderef(0);
      var1.getglobal("os").__setattr__("listdir", var3);
      var3 = null;
      var1.setline(328);
      var3 = var1.getderef(1);
      var1.getglobal("os").__getattr__("path").__setattr__("isfile", var3);
      var3 = null;
      var1.setline(329);
      var3 = var1.getderef(2);
      var1.getglobal("os").__getattr__("path").__setattr__("isdir", var3);
      var3 = null;
      var1.setline(330);
      var1.getglobal("sys").__getattr__("modules").__delitem__((PyObject)PyString.fromInterned("foo"));
      var1.setline(331);
      var3 = var1.getderef(3);
      PyObject var10000 = var3._in(var1.getglobal("sys").__getattr__("path"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(332);
         var1.getglobal("sys").__getattr__("path").__getattr__("remove").__call__(var2, var1.getderef(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject listdir$51(PyFrame var1, ThreadState var2) {
      var1.setline(336);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("foo.py")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isfile$52(PyFrame var1, ThreadState var2) {
      var1.setline(338);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject isdir$53(PyFrame var1, ThreadState var2) {
      var1.setline(340);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_discovery_from_dotted_path$54(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(359);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(361);
      PyList var4 = new PyList(new PyObject[]{var1.getderef(0)});
      var1.setderef(1, var4);
      var3 = null;
      var1.setline(362);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("unittest").__getattr__("test").__getattr__("__file__")));
      var1.setderef(2, var3);
      var3 = null;
      var1.setline(364);
      var3 = var1.getglobal("False");
      var1.getderef(0).__setattr__("wasRun", var3);
      var3 = null;
      var1.setline(365);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = _find_tests$55;
      var5 = new PyObject[]{var1.getclosure(0), var1.getclosure(2), var1.getclosure(1)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(369);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__setattr__("_find_tests", var3);
      var3 = null;
      var1.setline(370);
      var3 = var1.getlocal(1).__getattr__("discover").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unittest.test"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(371);
      var1.getderef(0).__getattr__("assertTrue").__call__(var2, var1.getderef(0).__getattr__("wasRun"));
      var1.setline(372);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("_tests"), var1.getderef(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _find_tests$55(PyFrame var1, ThreadState var2) {
      var1.setline(366);
      PyObject var3 = var1.getglobal("True");
      var1.getderef(0).__setattr__("wasRun", var3);
      var3 = null;
      var1.setline(367);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0), var1.getderef(1));
      var1.setline(368);
      var3 = var1.getderef(2);
      var1.f_lasti = -1;
      return var3;
   }

   public test_discovery$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestDiscovery$1 = Py.newCode(0, var2, var1, "TestDiscovery", 8, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "loader", "name"};
      test_get_name_from_path$2 = Py.newCode(1, var2, var1, "test_get_name_from_path", 11, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "restore_listdir", "restore_isfile", "restore_isdir", "isdir", "isfile", "top_level", "suite", "expected", "_[62_20]", "name", "_[64_25]", "original_isdir", "path_lists", "original_isfile", "original_listdir"};
      String[] var10001 = var2;
      test_discovery$py var10007 = self;
      var2 = new String[]{"original_isdir", "path_lists", "original_isfile", "original_listdir"};
      test_find_tests$3 = Py.newCode(1, var10001, var1, "test_find_tests", 25, false, false, var10007, 3, var2, (String[])null, 4, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"original_listdir"};
      restore_listdir$4 = Py.newCode(0, var10001, var1, "restore_listdir", 29, false, false, var10007, 4, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"original_isfile"};
      restore_isfile$5 = Py.newCode(0, var10001, var1, "restore_isfile", 32, false, false, var10007, 5, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"original_isdir"};
      restore_isdir$6 = Py.newCode(0, var10001, var1, "restore_isdir", 35, false, false, var10007, 6, (String[])null, var2, 0, 4097);
      var2 = new String[]{"path"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"path_lists"};
      f$7 = Py.newCode(1, var10001, var1, "<lambda>", 41, false, false, var10007, 7, (String[])null, var2, 0, 4097);
      var2 = new String[]{"path"};
      isdir$8 = Py.newCode(1, var2, var1, "isdir", 44, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      isfile$9 = Py.newCode(1, var2, var1, "isfile", 49, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      f$10 = Py.newCode(1, var2, var1, "<lambda>", 55, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"module"};
      f$11 = Py.newCode(1, var2, var1, "<lambda>", 56, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "restore_listdir", "restore_isfile", "restore_isdir", "loadTestsFromModule", "suite", "original_listdir", "Module", "path_lists", "original_isdir", "original_isfile", "directories"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"original_listdir", "Module", "path_lists", "original_isdir", "original_isfile", "directories", "self"};
      test_find_tests_with_package$12 = Py.newCode(1, var10001, var1, "test_find_tests_with_package", 68, false, false, var10007, 12, var2, (String[])null, 6, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"original_listdir"};
      restore_listdir$13 = Py.newCode(0, var10001, var1, "restore_listdir", 72, false, false, var10007, 13, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"original_isfile"};
      restore_isfile$14 = Py.newCode(0, var10001, var1, "restore_isfile", 75, false, false, var10007, 14, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"original_isdir"};
      restore_isdir$15 = Py.newCode(0, var10001, var1, "restore_isdir", 78, false, false, var10007, 15, (String[])null, var2, 0, 4097);
      var2 = new String[]{"path"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"path_lists"};
      f$16 = Py.newCode(1, var10001, var1, "<lambda>", 83, false, false, var10007, 16, (String[])null, var2, 0, 4097);
      var2 = new String[]{"path"};
      f$17 = Py.newCode(1, var2, var1, "<lambda>", 86, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"directories"};
      f$18 = Py.newCode(1, var10001, var1, "<lambda>", 89, false, false, var10007, 18, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      Module$19 = Py.newCode(0, var2, var1, "Module", 92, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "path", "load_tests"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      __init__$20 = Py.newCode(2, var10001, var1, "__init__", 96, false, false, var10007, 20, var2, (String[])null, 0, 4097);
      var2 = new String[]{"loader", "tests", "pattern"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      load_tests$21 = Py.newCode(3, var10001, var1, "load_tests", 100, false, false, var10007, 21, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "other"};
      __eq__$22 = Py.newCode(2, var2, var1, "__eq__", 105, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Module"};
      f$23 = Py.newCode(1, var10001, var1, "<lambda>", 111, false, false, var10007, 23, (String[])null, var2, 0, 4097);
      var2 = new String[]{"module", "use_load_tests"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      loadTestsFromModule$24 = Py.newCode(2, var10001, var1, "loadTestsFromModule", 112, false, false, var10007, 24, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "loader", "restore_isfile", "restore_path", "full_path", "restore_isdir", "_find_tests", "suite", "top_level_dir", "start_dir", "_find_tests_args", "original_isdir", "orig_sys_path", "original_isfile"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"_find_tests_args", "original_isdir", "orig_sys_path", "original_isfile"};
      test_discover$25 = Py.newCode(1, var10001, var1, "test_discover", 133, false, false, var10007, 25, var2, (String[])null, 4, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"original_isfile"};
      restore_isfile$26 = Py.newCode(0, var10001, var1, "restore_isfile", 138, false, false, var10007, 26, (String[])null, var2, 0, 4097);
      var2 = new String[]{"path"};
      f$27 = Py.newCode(1, var2, var1, "<lambda>", 141, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"orig_sys_path"};
      restore_path$28 = Py.newCode(0, var10001, var1, "restore_path", 145, false, false, var10007, 28, (String[])null, var2, 0, 4097);
      var2 = new String[]{"path"};
      f$29 = Py.newCode(1, var2, var1, "<lambda>", 156, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      f$30 = Py.newCode(1, var2, var1, "<lambda>", 157, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"original_isdir"};
      restore_isdir$31 = Py.newCode(0, var10001, var1, "restore_isdir", 159, false, false, var10007, 31, (String[])null, var2, 0, 4097);
      var2 = new String[]{"start_dir", "pattern"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"_find_tests_args"};
      _find_tests$32 = Py.newCode(2, var10001, var1, "_find_tests", 164, false, false, var10007, 32, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "loader", "restore", "suite", "test", "orig_sys_path", "isfile", "listdir"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"orig_sys_path", "isfile", "listdir"};
      test_discover_with_modules_that_fail_to_import$33 = Py.newCode(1, var10001, var1, "test_discover_with_modules_that_fail_to_import", 179, false, false, var10007, 33, var2, (String[])null, 3, 4097);
      var2 = new String[]{"_"};
      f$34 = Py.newCode(1, var2, var1, "<lambda>", 183, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_"};
      f$35 = Py.newCode(1, var2, var1, "<lambda>", 185, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"isfile", "listdir", "orig_sys_path"};
      restore$36 = Py.newCode(0, var10001, var1, "restore", 187, false, false, var10007, 36, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "program", "do_discovery", "args"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"args"};
      test_command_line_handling_parseArgs$37 = Py.newCode(1, var10001, var1, "test_command_line_handling_parseArgs", 201, false, false, var10007, 37, var2, (String[])null, 1, 4097);
      var2 = new String[]{"argv"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"args"};
      do_discovery$38 = Py.newCode(1, var10001, var1, "do_discovery", 206, false, false, var10007, 38, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "usageExit", "program", "Stop"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Stop"};
      test_command_line_handling_do_discovery_too_many_arguments$39 = Py.newCode(1, var10001, var1, "test_command_line_handling_do_discovery_too_many_arguments", 215, false, false, var10007, 39, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Stop$40 = Py.newCode(0, var2, var1, "Stop", 216, false, false, self, 40, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"Stop"};
      usageExit$41 = Py.newCode(0, var10001, var1, "usageExit", 218, false, false, var10007, 41, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "program", "Loader"};
      test_command_line_handling_do_discovery_uses_default_loader$42 = Py.newCode(1, var2, var1, "test_command_line_handling_do_discovery_uses_default_loader", 230, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Loader$43 = Py.newCode(0, var2, var1, "Loader", 233, false, false, self, 43, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "start_dir", "pattern", "top_level_dir"};
      discover$44 = Py.newCode(4, var2, var1, "discover", 235, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "program", "Loader"};
      test_command_line_handling_do_discovery_calls_loader$45 = Py.newCode(1, var2, var1, "test_command_line_handling_do_discovery_calls_loader", 243, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Loader$46 = Py.newCode(0, var2, var1, "Loader", 246, false, false, self, 46, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "start_dir", "pattern", "top_level_dir"};
      discover$47 = Py.newCode(4, var2, var1, "discover", 248, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "Module", "cleanup", "listdir", "isfile", "isdir", "loader", "mod_dir", "expected_dir", "msg", "full_path", "original_isdir", "original_isfile", "original_listdir"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"full_path", "original_isdir", "original_isfile", "original_listdir"};
      test_detect_module_clash$48 = Py.newCode(1, var10001, var1, "test_detect_module_clash", 317, false, false, var10007, 48, var2, (String[])null, 4, 4097);
      var2 = new String[0];
      Module$49 = Py.newCode(0, var2, var1, "Module", 318, false, false, self, 49, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"original_listdir", "original_isfile", "original_isdir", "full_path"};
      cleanup$50 = Py.newCode(0, var10001, var1, "cleanup", 326, false, false, var10007, 50, (String[])null, var2, 0, 4097);
      var2 = new String[]{"_"};
      listdir$51 = Py.newCode(1, var2, var1, "listdir", 335, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_"};
      isfile$52 = Py.newCode(1, var2, var1, "isfile", 337, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_"};
      isdir$53 = Py.newCode(1, var2, var1, "isdir", 339, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "loader", "_find_tests", "suite", "tests", "expectedPath"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "tests", "expectedPath"};
      test_discovery_from_dotted_path$54 = Py.newCode(1, var10001, var1, "test_discovery_from_dotted_path", 358, false, false, var10007, 54, var2, (String[])null, 2, 4097);
      var2 = new String[]{"start_dir", "pattern"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "expectedPath", "tests"};
      _find_tests$55 = Py.newCode(2, var10001, var1, "_find_tests", 365, false, false, var10007, 55, (String[])null, var2, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_discovery$py("unittest/test/test_discovery$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_discovery$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestDiscovery$1(var2, var3);
         case 2:
            return this.test_get_name_from_path$2(var2, var3);
         case 3:
            return this.test_find_tests$3(var2, var3);
         case 4:
            return this.restore_listdir$4(var2, var3);
         case 5:
            return this.restore_isfile$5(var2, var3);
         case 6:
            return this.restore_isdir$6(var2, var3);
         case 7:
            return this.f$7(var2, var3);
         case 8:
            return this.isdir$8(var2, var3);
         case 9:
            return this.isfile$9(var2, var3);
         case 10:
            return this.f$10(var2, var3);
         case 11:
            return this.f$11(var2, var3);
         case 12:
            return this.test_find_tests_with_package$12(var2, var3);
         case 13:
            return this.restore_listdir$13(var2, var3);
         case 14:
            return this.restore_isfile$14(var2, var3);
         case 15:
            return this.restore_isdir$15(var2, var3);
         case 16:
            return this.f$16(var2, var3);
         case 17:
            return this.f$17(var2, var3);
         case 18:
            return this.f$18(var2, var3);
         case 19:
            return this.Module$19(var2, var3);
         case 20:
            return this.__init__$20(var2, var3);
         case 21:
            return this.load_tests$21(var2, var3);
         case 22:
            return this.__eq__$22(var2, var3);
         case 23:
            return this.f$23(var2, var3);
         case 24:
            return this.loadTestsFromModule$24(var2, var3);
         case 25:
            return this.test_discover$25(var2, var3);
         case 26:
            return this.restore_isfile$26(var2, var3);
         case 27:
            return this.f$27(var2, var3);
         case 28:
            return this.restore_path$28(var2, var3);
         case 29:
            return this.f$29(var2, var3);
         case 30:
            return this.f$30(var2, var3);
         case 31:
            return this.restore_isdir$31(var2, var3);
         case 32:
            return this._find_tests$32(var2, var3);
         case 33:
            return this.test_discover_with_modules_that_fail_to_import$33(var2, var3);
         case 34:
            return this.f$34(var2, var3);
         case 35:
            return this.f$35(var2, var3);
         case 36:
            return this.restore$36(var2, var3);
         case 37:
            return this.test_command_line_handling_parseArgs$37(var2, var3);
         case 38:
            return this.do_discovery$38(var2, var3);
         case 39:
            return this.test_command_line_handling_do_discovery_too_many_arguments$39(var2, var3);
         case 40:
            return this.Stop$40(var2, var3);
         case 41:
            return this.usageExit$41(var2, var3);
         case 42:
            return this.test_command_line_handling_do_discovery_uses_default_loader$42(var2, var3);
         case 43:
            return this.Loader$43(var2, var3);
         case 44:
            return this.discover$44(var2, var3);
         case 45:
            return this.test_command_line_handling_do_discovery_calls_loader$45(var2, var3);
         case 46:
            return this.Loader$46(var2, var3);
         case 47:
            return this.discover$47(var2, var3);
         case 48:
            return this.test_detect_module_clash$48(var2, var3);
         case 49:
            return this.Module$49(var2, var3);
         case 50:
            return this.cleanup$50(var2, var3);
         case 51:
            return this.listdir$51(var2, var3);
         case 52:
            return this.isfile$52(var2, var3);
         case 53:
            return this.isdir$53(var2, var3);
         case 54:
            return this.test_discovery_from_dotted_path$54(var2, var3);
         case 55:
            return this._find_tests$55(var2, var3);
         default:
            return null;
      }
   }
}
