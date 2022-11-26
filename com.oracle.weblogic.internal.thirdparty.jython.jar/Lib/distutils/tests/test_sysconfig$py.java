package distutils.tests;

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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_sysconfig.py")
public class test_sysconfig$py extends PyFunctionTable implements PyRunnable {
   static test_sysconfig$py self;
   static final PyCode f$0;
   static final PyCode SysconfigTestCase$1;
   static final PyCode setUp$2;
   static final PyCode tearDown$3;
   static final PyCode cleanup_testfn$4;
   static final PyCode test_get_python_lib$5;
   static final PyCode test_get_python_inc$6;
   static final PyCode test_parse_makefile_base$7;
   static final PyCode test_parse_makefile_literal_dollar$8;
   static final PyCode test_sysconfig_module$9;
   static final PyCode test_sysconfig_compiler_vars$10;
   static final PyCode test_suite$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.sysconfig."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.sysconfig.");
      var1.setline(2);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("test", var1, -1);
      var1.setlocal("test", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var3);
      var3 = null;
      var1.setline(7);
      String[] var5 = new String[]{"sysconfig"};
      PyObject[] var6 = imp.importFrom("distutils", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("sysconfig", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"TESTFN"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("TESTFN", var4);
      var4 = null;
      var1.setline(11);
      var6 = new PyObject[]{var1.getname("support").__getattr__("EnvironGuard"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("SysconfigTestCase", var6, SysconfigTestCase$1);
      var1.setlocal("SysconfigTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(104);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$11, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(110);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(111);
         var1.getname("test").__getattr__("test_support").__getattr__("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SysconfigTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$2, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(17);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$3, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(23);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, cleanup_testfn$4, (PyObject)null);
      var1.setlocal("cleanup_testfn", var4);
      var3 = null;
      var1.setline(30);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_python_lib$5, (PyObject)null);
      var1.setlocal("test_get_python_lib", var4);
      var3 = null;
      var1.setline(41);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_python_inc$6, (PyObject)null);
      var1.setlocal("test_get_python_inc", var4);
      var3 = null;
      var1.setline(50);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parse_makefile_base$7, (PyObject)null);
      var1.setlocal("test_parse_makefile_base", var4);
      var3 = null;
      var1.setline(62);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_parse_makefile_literal_dollar$8, (PyObject)null);
      var1.setlocal("test_parse_makefile_literal_dollar", var4);
      var3 = null;
      var1.setline(75);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_sysconfig_module$9, (PyObject)null);
      var1.setlocal("test_sysconfig_module", var4);
      var3 = null;
      var1.setline(80);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_sysconfig_compiler_vars$10, (PyObject)null);
      PyObject var5 = var1.getname("unittest").__getattr__("skipIf").__call__((ThreadState)var2, (PyObject)var1.getname("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CUSTOMIZED_OSX_COMPILER")), (PyObject)PyString.fromInterned("compiler flags customized")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_sysconfig_compiler_vars", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$2(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      var1.getglobal("super").__call__(var2, var1.getglobal("SysconfigTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(15);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("makefile", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$3(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getlocal(0).__getattr__("makefile");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(19);
         var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(0).__getattr__("makefile"));
      }

      var1.setline(20);
      var1.getlocal(0).__getattr__("cleanup_testfn").__call__(var2);
      var1.setline(21);
      var1.getglobal("super").__call__(var2, var1.getglobal("SysconfigTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cleanup_testfn$4(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = var1.getglobal("test").__getattr__("test_support").__getattr__("TESTFN");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(25);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(26);
         var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(27);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(28);
            var1.getglobal("shutil").__getattr__("rmtree").__call__(var2, var1.getlocal(1));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_python_lib$5(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var3 = var1.getglobal("sysconfig").__getattr__("get_python_lib").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(35);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertNotEqual");
      PyObject var10002 = var1.getglobal("sysconfig").__getattr__("get_python_lib").__call__(var2);
      PyObject var10003 = var1.getglobal("sysconfig").__getattr__("get_python_lib");
      PyObject[] var5 = new PyObject[]{var1.getglobal("TESTFN")};
      String[] var4 = new String[]{"prefix"};
      var10003 = var10003.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var10003);
      var1.setline(37);
      var3 = var1.getglobal("__import__").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sysconfig"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getglobal("sysconfig").__getattr__("get_python_lib").__call__(var2, var1.getglobal("True"), var1.getglobal("True"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(39);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("get_path").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("platstdlib")), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_python_inc$6(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getglobal("sysconfig").__getattr__("get_python_inc").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(46);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)), var1.getlocal(1));
      var1.setline(47);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("Python.h"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(48);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(2)), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parse_makefile_base$7(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyObject var3 = var1.getglobal("test").__getattr__("test_support").__getattr__("TESTFN");
      var1.getlocal(0).__setattr__("makefile", var3);
      var3 = null;
      var1.setline(52);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("makefile"), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(54);
         var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CONFIG_ARGS=  '--arg1=optarg1' 'ENV=LIB'\n"));
         var1.setline(55);
         var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("VAR=$OTHER\nOTHER=foo"));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(57);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(57);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setline(58);
      var3 = var1.getglobal("sysconfig").__getattr__("parse_makefile").__call__(var2, var1.getlocal(0).__getattr__("makefile"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(59);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("CONFIG_ARGS"), PyString.fromInterned("'--arg1=optarg1' 'ENV=LIB'"), PyString.fromInterned("OTHER"), PyString.fromInterned("foo")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_parse_makefile_literal_dollar$8(PyFrame var1, ThreadState var2) {
      var1.setline(63);
      PyObject var3 = var1.getglobal("test").__getattr__("test_support").__getattr__("TESTFN");
      var1.getlocal(0).__setattr__("makefile", var3);
      var3 = null;
      var1.setline(64);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("makefile"), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(66);
         var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CONFIG_ARGS=  '--arg1=optarg1' 'ENV=\\$$LIB'\n"));
         var1.setline(67);
         var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("VAR=$OTHER\nOTHER=foo"));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(69);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(69);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setline(70);
      var3 = var1.getglobal("sysconfig").__getattr__("parse_makefile").__call__(var2, var1.getlocal(0).__getattr__("makefile"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(71);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("CONFIG_ARGS"), PyString.fromInterned("'--arg1=optarg1' 'ENV=\\$LIB'"), PyString.fromInterned("OTHER"), PyString.fromInterned("foo")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_sysconfig_module$9(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = imp.importOneAs("sysconfig", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(77);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CFLAGS")), var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CFLAGS")));
      var1.setline(78);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LDFLAGS")), var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LDFLAGS")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_sysconfig_compiler_vars$10(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyObject var3 = imp.importOneAs("sysconfig", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(97);
      if (var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CUSTOMIZED_OSX_COMPILER")).__nonzero__()) {
         var1.setline(98);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(99);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LDSHARED")), var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LDSHARED")));
         var1.setline(100);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CC")), var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CC")));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_suite$11(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(106);
      var1.getlocal(0).__getattr__("addTest").__call__(var2, var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("SysconfigTestCase")));
      var1.setline(107);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public test_sysconfig$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SysconfigTestCase$1 = Py.newCode(0, var2, var1, "SysconfigTestCase", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$2 = Py.newCode(1, var2, var1, "setUp", 13, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$3 = Py.newCode(1, var2, var1, "tearDown", 17, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path"};
      cleanup_testfn$4 = Py.newCode(1, var2, var1, "cleanup_testfn", 23, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lib_dir", "_sysconfig", "res"};
      test_get_python_lib$5 = Py.newCode(1, var2, var1, "test_get_python_lib", 30, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "inc_dir", "python_h"};
      test_get_python_inc$6 = Py.newCode(1, var2, var1, "test_get_python_inc", 41, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fd", "d"};
      test_parse_makefile_base$7 = Py.newCode(1, var2, var1, "test_parse_makefile_base", 50, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fd", "d"};
      test_parse_makefile_literal_dollar$8 = Py.newCode(1, var2, var1, "test_parse_makefile_literal_dollar", 62, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "global_sysconfig"};
      test_sysconfig_module$9 = Py.newCode(1, var2, var1, "test_sysconfig_module", 75, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "global_sysconfig"};
      test_sysconfig_compiler_vars$10 = Py.newCode(1, var2, var1, "test_sysconfig_compiler_vars", 80, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"suite"};
      test_suite$11 = Py.newCode(0, var2, var1, "test_suite", 104, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_sysconfig$py("distutils/tests/test_sysconfig$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_sysconfig$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.SysconfigTestCase$1(var2, var3);
         case 2:
            return this.setUp$2(var2, var3);
         case 3:
            return this.tearDown$3(var2, var3);
         case 4:
            return this.cleanup_testfn$4(var2, var3);
         case 5:
            return this.test_get_python_lib$5(var2, var3);
         case 6:
            return this.test_get_python_inc$6(var2, var3);
         case 7:
            return this.test_parse_makefile_base$7(var2, var3);
         case 8:
            return this.test_parse_makefile_literal_dollar$8(var2, var3);
         case 9:
            return this.test_sysconfig_module$9(var2, var3);
         case 10:
            return this.test_sysconfig_compiler_vars$10(var2, var3);
         case 11:
            return this.test_suite$11(var2, var3);
         default:
            return null;
      }
   }
}
