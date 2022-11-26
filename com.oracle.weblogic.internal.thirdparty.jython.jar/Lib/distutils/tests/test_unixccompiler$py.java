package distutils.tests;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_unixccompiler.py")
public class test_unixccompiler$py extends PyFunctionTable implements PyRunnable {
   static test_unixccompiler$py self;
   static final PyCode f$0;
   static final PyCode UnixCCompilerTestCase$1;
   static final PyCode setUp$2;
   static final PyCode CompilerWrapper$3;
   static final PyCode rpath_foo$4;
   static final PyCode tearDown$5;
   static final PyCode test_runtime_libdir_option$6;
   static final PyCode gcv$7;
   static final PyCode gcv$8;
   static final PyCode gcv$9;
   static final PyCode gcv$10;
   static final PyCode gcv$11;
   static final PyCode gcv$12;
   static final PyCode gcv$13;
   static final PyCode gcv$14;
   static final PyCode gcv$15;
   static final PyCode test_suite$16;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.unixccompiler."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.unixccompiler.");
      var1.setline(2);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(4);
      String[] var5 = new String[]{"run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(6);
      var5 = new String[]{"sysconfig"};
      var6 = imp.importFrom("distutils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("sysconfig", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"UnixCCompiler"};
      var6 = imp.importFrom("distutils.unixccompiler", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("UnixCCompiler", var4);
      var4 = null;
      var1.setline(9);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("UnixCCompilerTestCase", var6, UnixCCompilerTestCase$1);
      var1.setlocal("UnixCCompilerTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(126);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$16, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(129);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(130);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject UnixCCompilerTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(11);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$2, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(19);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$5, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(23);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_runtime_libdir_option$6, (PyObject)null);
      var1.setlocal("test_runtime_libdir_option", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$2(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      var1.getlocal(0).__setattr__("_backup_platform", var3);
      var3 = null;
      var1.setline(13);
      var3 = var1.getglobal("sysconfig").__getattr__("get_config_var");
      var1.getlocal(0).__setattr__("_backup_get_config_var", var3);
      var3 = null;
      var1.setline(14);
      PyObject[] var5 = new PyObject[]{var1.getglobal("UnixCCompiler")};
      PyObject var4 = Py.makeClass("CompilerWrapper", var5, CompilerWrapper$3);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(17);
      var3 = var1.getlocal(1).__call__(var2);
      var1.getlocal(0).__setattr__("cc", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject CompilerWrapper$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(15);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, rpath_foo$4, (PyObject)null);
      var1.setlocal("rpath_foo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject rpath_foo$4(PyFrame var1, ThreadState var2) {
      var1.setline(16);
      PyObject var3 = var1.getlocal(0).__getattr__("runtime_library_dir_option").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/foo"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject tearDown$5(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyObject var3 = var1.getlocal(0).__getattr__("_backup_platform");
      var1.getglobal("sys").__setattr__("platform", var3);
      var3 = null;
      var1.setline(21);
      var3 = var1.getlocal(0).__getattr__("_backup_get_config_var");
      var1.getglobal("sysconfig").__setattr__("get_config_var", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_runtime_libdir_option$6(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(27);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(35);
         PyString var4 = PyString.fromInterned("darwin");
         var1.getglobal("sys").__setattr__((String)"platform", var4);
         var3 = null;
         var1.setline(36);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cc").__getattr__("rpath_foo").__call__(var2), (PyObject)PyString.fromInterned("-L/foo"));
         var1.setline(39);
         var4 = PyString.fromInterned("hp-ux");
         var1.getglobal("sys").__setattr__((String)"platform", var4);
         var3 = null;
         var1.setline(40);
         var3 = var1.getglobal("sysconfig").__getattr__("get_config_var");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(41);
         PyObject[] var5 = Py.EmptyObjects;
         PyFunction var6 = new PyFunction(var1.f_globals, var5, gcv$7, (PyObject)null);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(43);
         var3 = var1.getlocal(2);
         var1.getglobal("sysconfig").__setattr__("get_config_var", var3);
         var3 = null;
         var1.setline(44);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cc").__getattr__("rpath_foo").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("+s"), PyString.fromInterned("-L/foo")})));
         var1.setline(46);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, gcv$8, (PyObject)null);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(48);
         var3 = var1.getlocal(2);
         var1.getglobal("sysconfig").__setattr__("get_config_var", var3);
         var3 = null;
         var1.setline(49);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cc").__getattr__("rpath_foo").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("-Wl,+s"), PyString.fromInterned("-L/foo")})));
         var1.setline(51);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, gcv$9, (PyObject)null);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(53);
         var3 = var1.getlocal(2);
         var1.getglobal("sysconfig").__setattr__("get_config_var", var3);
         var3 = null;
         var1.setline(54);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cc").__getattr__("rpath_foo").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("-Wl,+s"), PyString.fromInterned("-L/foo")})));
         var1.setline(56);
         var3 = var1.getlocal(1);
         var1.getglobal("sysconfig").__setattr__("get_config_var", var3);
         var3 = null;
         var1.setline(59);
         var4 = PyString.fromInterned("irix646");
         var1.getglobal("sys").__setattr__((String)"platform", var4);
         var3 = null;
         var1.setline(60);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cc").__getattr__("rpath_foo").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("-rpath"), PyString.fromInterned("/foo")})));
         var1.setline(63);
         var4 = PyString.fromInterned("osf1V5");
         var1.getglobal("sys").__setattr__((String)"platform", var4);
         var3 = null;
         var1.setline(64);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cc").__getattr__("rpath_foo").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("-rpath"), PyString.fromInterned("/foo")})));
         var1.setline(67);
         var4 = PyString.fromInterned("bar");
         var1.getglobal("sys").__setattr__((String)"platform", var4);
         var3 = null;
         var1.setline(68);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, gcv$10, (PyObject)null);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(73);
         var3 = var1.getlocal(2);
         var1.getglobal("sysconfig").__setattr__("get_config_var", var3);
         var3 = null;
         var1.setline(74);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cc").__getattr__("rpath_foo").__call__(var2), (PyObject)PyString.fromInterned("-Wl,-R/foo"));
         var1.setline(77);
         var4 = PyString.fromInterned("bar");
         var1.getglobal("sys").__setattr__((String)"platform", var4);
         var3 = null;
         var1.setline(78);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, gcv$11, (PyObject)null);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(83);
         var3 = var1.getlocal(2);
         var1.getglobal("sysconfig").__setattr__("get_config_var", var3);
         var3 = null;
         var1.setline(84);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cc").__getattr__("rpath_foo").__call__(var2), (PyObject)PyString.fromInterned("-Wl,-R/foo"));
         var1.setline(88);
         var4 = PyString.fromInterned("bar");
         var1.getglobal("sys").__setattr__((String)"platform", var4);
         var3 = null;
         var1.setline(89);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, gcv$12, (PyObject)null);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(94);
         var3 = var1.getlocal(2);
         var1.getglobal("sysconfig").__setattr__("get_config_var", var3);
         var3 = null;
         var1.setline(95);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cc").__getattr__("rpath_foo").__call__(var2), (PyObject)PyString.fromInterned("-Wl,-R/foo"));
         var1.setline(99);
         var4 = PyString.fromInterned("bar");
         var1.getglobal("sys").__setattr__((String)"platform", var4);
         var3 = null;
         var1.setline(100);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, gcv$13, (PyObject)null);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(105);
         var3 = var1.getlocal(2);
         var1.getglobal("sysconfig").__setattr__("get_config_var", var3);
         var3 = null;
         var1.setline(106);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cc").__getattr__("rpath_foo").__call__(var2), (PyObject)PyString.fromInterned("-R/foo"));
         var1.setline(109);
         var4 = PyString.fromInterned("bar");
         var1.getglobal("sys").__setattr__((String)"platform", var4);
         var3 = null;
         var1.setline(110);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, gcv$14, (PyObject)null);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(115);
         var3 = var1.getlocal(2);
         var1.getglobal("sysconfig").__setattr__("get_config_var", var3);
         var3 = null;
         var1.setline(116);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cc").__getattr__("rpath_foo").__call__(var2), (PyObject)PyString.fromInterned("-R/foo"));
         var1.setline(119);
         var4 = PyString.fromInterned("aix");
         var1.getglobal("sys").__setattr__((String)"platform", var4);
         var3 = null;
         var1.setline(120);
         var5 = Py.EmptyObjects;
         var6 = new PyFunction(var1.f_globals, var5, gcv$15, (PyObject)null);
         var1.setlocal(2, var6);
         var3 = null;
         var1.setline(122);
         var3 = var1.getlocal(2);
         var1.getglobal("sysconfig").__setattr__("get_config_var", var3);
         var3 = null;
         var1.setline(123);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cc").__getattr__("rpath_foo").__call__(var2), (PyObject)PyString.fromInterned("-R/foo"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject gcv$7(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyString var3 = PyString.fromInterned("xxx");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject gcv$8(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyString var3 = PyString.fromInterned("gcc");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject gcv$9(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyString var3 = PyString.fromInterned("g++");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject gcv$10(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(PyString.fromInterned("CC"));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(70);
         var5 = PyString.fromInterned("gcc");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(71);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._eq(PyString.fromInterned("GNULD"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(72);
            var5 = PyString.fromInterned("yes");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject gcv$11(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(PyString.fromInterned("CC"));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(80);
         var5 = PyString.fromInterned("gcc");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(81);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._eq(PyString.fromInterned("GNULD"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(82);
            var5 = PyString.fromInterned("no");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject gcv$12(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(PyString.fromInterned("CC"));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(91);
         var5 = PyString.fromInterned("x86_64-pc-linux-gnu-gcc-4.4.2");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(92);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._eq(PyString.fromInterned("GNULD"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(93);
            var5 = PyString.fromInterned("yes");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject gcv$13(PyFrame var1, ThreadState var2) {
      var1.setline(101);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(PyString.fromInterned("CC"));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(102);
         var5 = PyString.fromInterned("cc");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(103);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._eq(PyString.fromInterned("GNULD"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(104);
            var5 = PyString.fromInterned("yes");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject gcv$14(PyFrame var1, ThreadState var2) {
      var1.setline(111);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._eq(PyString.fromInterned("CC"));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(112);
         var5 = PyString.fromInterned("cc");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(113);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._eq(PyString.fromInterned("GNULD"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(114);
            var5 = PyString.fromInterned("no");
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject gcv$15(PyFrame var1, ThreadState var2) {
      var1.setline(121);
      PyString var3 = PyString.fromInterned("xxx");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_suite$16(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("UnixCCompilerTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_unixccompiler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UnixCCompilerTestCase$1 = Py.newCode(0, var2, var1, "UnixCCompilerTestCase", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "CompilerWrapper"};
      setUp$2 = Py.newCode(1, var2, var1, "setUp", 11, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CompilerWrapper$3 = Py.newCode(0, var2, var1, "CompilerWrapper", 14, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      rpath_foo$4 = Py.newCode(1, var2, var1, "rpath_foo", 15, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$5 = Py.newCode(1, var2, var1, "tearDown", 19, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "old_gcv", "gcv"};
      test_runtime_libdir_option$6 = Py.newCode(1, var2, var1, "test_runtime_libdir_option", 23, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"v"};
      gcv$7 = Py.newCode(1, var2, var1, "gcv", 41, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"v"};
      gcv$8 = Py.newCode(1, var2, var1, "gcv", 46, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"v"};
      gcv$9 = Py.newCode(1, var2, var1, "gcv", 51, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"v"};
      gcv$10 = Py.newCode(1, var2, var1, "gcv", 68, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"v"};
      gcv$11 = Py.newCode(1, var2, var1, "gcv", 78, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"v"};
      gcv$12 = Py.newCode(1, var2, var1, "gcv", 89, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"v"};
      gcv$13 = Py.newCode(1, var2, var1, "gcv", 100, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"v"};
      gcv$14 = Py.newCode(1, var2, var1, "gcv", 110, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"v"};
      gcv$15 = Py.newCode(1, var2, var1, "gcv", 120, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$16 = Py.newCode(0, var2, var1, "test_suite", 126, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_unixccompiler$py("distutils/tests/test_unixccompiler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_unixccompiler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.UnixCCompilerTestCase$1(var2, var3);
         case 2:
            return this.setUp$2(var2, var3);
         case 3:
            return this.CompilerWrapper$3(var2, var3);
         case 4:
            return this.rpath_foo$4(var2, var3);
         case 5:
            return this.tearDown$5(var2, var3);
         case 6:
            return this.test_runtime_libdir_option$6(var2, var3);
         case 7:
            return this.gcv$7(var2, var3);
         case 8:
            return this.gcv$8(var2, var3);
         case 9:
            return this.gcv$9(var2, var3);
         case 10:
            return this.gcv$10(var2, var3);
         case 11:
            return this.gcv$11(var2, var3);
         case 12:
            return this.gcv$12(var2, var3);
         case 13:
            return this.gcv$13(var2, var3);
         case 14:
            return this.gcv$14(var2, var3);
         case 15:
            return this.gcv$15(var2, var3);
         case 16:
            return this.test_suite$16(var2, var3);
         default:
            return null;
      }
   }
}
