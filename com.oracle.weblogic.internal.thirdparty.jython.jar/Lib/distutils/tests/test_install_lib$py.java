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
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_install_lib.py")
public class test_install_lib$py extends PyFunctionTable implements PyRunnable {
   static test_install_lib$py self;
   static final PyCode f$0;
   static final PyCode InstallLibTestCase$1;
   static final PyCode test_finalize_options$2;
   static final PyCode _setup_byte_compile$3;
   static final PyCode test_byte_compile$4;
   static final PyCode test_get_outputs$5;
   static final PyCode test_get_inputs$6;
   static final PyCode test_dont_write_bytecode$7;
   static final PyCode test_suite$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.install_data."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.install_data.");
      var1.setline(2);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(6);
      String[] var5 = new String[]{"install_lib"};
      PyObject[] var6 = imp.importFrom("distutils.command.install_lib", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("install_lib", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"Extension"};
      var6 = imp.importFrom("distutils.extension", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Extension", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"DistutilsOptionError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"run_unittest"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(12);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("support").__getattr__("EnvironGuard"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("InstallLibTestCase", var6, InstallLibTestCase$1);
      var1.setlocal("InstallLibTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(103);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$8, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(106);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(107);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject InstallLibTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_finalize_options$2, (PyObject)null);
      var1.setlocal("test_finalize_options", var4);
      var3 = null;
      var1.setline(35);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _setup_byte_compile$3, (PyObject)null);
      var1.setlocal("_setup_byte_compile", var4);
      var3 = null;
      var1.setline(45);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_byte_compile$4, (PyObject)null);
      PyObject var5 = var1.getname("unittest").__getattr__("skipIf").__call__((ThreadState)var2, (PyObject)var1.getname("sys").__getattr__("dont_write_bytecode"), (PyObject)PyString.fromInterned("byte-compile not enabled")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_byte_compile", var5);
      var3 = null;
      var1.setline(53);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_outputs$5, (PyObject)null);
      var1.setlocal("test_get_outputs", var4);
      var3 = null;
      var1.setline(70);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_inputs$6, (PyObject)null);
      var1.setlocal("test_get_inputs", var4);
      var3 = null;
      var1.setline(87);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dont_write_bytecode$7, (PyObject)null);
      var1.setlocal("test_dont_write_bytecode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_finalize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(19);
      var3 = var1.getglobal("install_lib").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(21);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(22);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("compile"), (PyObject)Py.newInteger(1));
      var1.setline(23);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("optimize"), (PyObject)Py.newInteger(0));
      var1.setline(26);
      PyString var6 = PyString.fromInterned("foo");
      var1.getlocal(3).__setattr__((String)"optimize", var6);
      var3 = null;
      var1.setline(27);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsOptionError"), var1.getlocal(3).__getattr__("finalize_options"));
      var1.setline(28);
      var6 = PyString.fromInterned("4");
      var1.getlocal(3).__setattr__((String)"optimize", var6);
      var3 = null;
      var1.setline(29);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsOptionError"), var1.getlocal(3).__getattr__("finalize_options"));
      var1.setline(31);
      var6 = PyString.fromInterned("2");
      var1.getlocal(3).__setattr__((String)"optimize", var6);
      var3 = null;
      var1.setline(32);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(33);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("optimize"), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _setup_byte_compile$3(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(37);
      var3 = var1.getglobal("install_lib").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(38);
      PyInteger var6 = Py.newInteger(1);
      var1.getlocal(3).__setattr__((String)"compile", var6);
      var1.getlocal(3).__setattr__((String)"optimize", var6);
      var1.setline(40);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo.py"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(41);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("# python file"));
      var1.setline(42);
      var1.getlocal(3).__getattr__("byte_compile").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(4)})));
      var1.setline(43);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_byte_compile$4(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject var3 = var1.getlocal(0).__getattr__("_setup_byte_compile").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getglobal("sys").__getattr__("flags").__getattr__("optimize");
      PyObject var10000 = var3._lt(Py.newInteger(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(49);
         var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo.pyc"))));
      } else {
         var1.setline(51);
         var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo.pyo"))));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_outputs$5(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(55);
      var3 = var1.getglobal("install_lib").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(58);
      PyInteger var6 = Py.newInteger(1);
      var1.getlocal(3).__setattr__((String)"compile", var6);
      var1.getlocal(3).__setattr__((String)"optimize", var6);
      var1.setline(59);
      var3 = var1.getlocal(1);
      var1.getlocal(3).__setattr__("install_dir", var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo.py"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(61);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("# python file"));
      var1.setline(62);
      PyList var7 = new PyList(new PyObject[]{var1.getlocal(1)});
      var1.getlocal(3).__getattr__("distribution").__setattr__((String)"py_modules", var7);
      var3 = null;
      var1.setline(63);
      var7 = new PyList(new PyObject[]{var1.getglobal("Extension").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("xxx")})))});
      var1.getlocal(3).__getattr__("distribution").__setattr__((String)"ext_modules", var7);
      var3 = null;
      var1.setline(64);
      var7 = new PyList(new PyObject[]{var1.getlocal(1)});
      var1.getlocal(3).__getattr__("distribution").__setattr__((String)"packages", var7);
      var3 = null;
      var1.setline(65);
      PyString var8 = PyString.fromInterned("setup.py");
      var1.getlocal(3).__getattr__("distribution").__setattr__((String)"script_name", var8);
      var3 = null;
      var1.setline(68);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_outputs").__call__(var2));
      PyObject var10002 = var3._ge(Py.newInteger(2));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_inputs$6(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(72);
      var3 = var1.getglobal("install_lib").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(75);
      PyInteger var6 = Py.newInteger(1);
      var1.getlocal(3).__setattr__((String)"compile", var6);
      var1.getlocal(3).__setattr__((String)"optimize", var6);
      var1.setline(76);
      var3 = var1.getlocal(1);
      var1.getlocal(3).__setattr__("install_dir", var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo.py"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(78);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("# python file"));
      var1.setline(79);
      PyList var7 = new PyList(new PyObject[]{var1.getlocal(1)});
      var1.getlocal(3).__getattr__("distribution").__setattr__((String)"py_modules", var7);
      var3 = null;
      var1.setline(80);
      var7 = new PyList(new PyObject[]{var1.getglobal("Extension").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("xxx")})))});
      var1.getlocal(3).__getattr__("distribution").__setattr__((String)"ext_modules", var7);
      var3 = null;
      var1.setline(81);
      var7 = new PyList(new PyObject[]{var1.getlocal(1)});
      var1.getlocal(3).__getattr__("distribution").__setattr__((String)"packages", var7);
      var3 = null;
      var1.setline(82);
      PyString var8 = PyString.fromInterned("setup.py");
      var1.getlocal(3).__getattr__("distribution").__setattr__((String)"script_name", var8);
      var3 = null;
      var1.setline(85);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_inputs").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dont_write_bytecode$7(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(90);
      var3 = var1.getglobal("install_lib").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(91);
      PyInteger var7 = Py.newInteger(1);
      var1.getlocal(3).__setattr__((String)"compile", var7);
      var3 = null;
      var1.setline(92);
      var7 = Py.newInteger(1);
      var1.getlocal(3).__setattr__((String)"optimize", var7);
      var3 = null;
      var1.setline(94);
      var3 = var1.getglobal("sys").__getattr__("dont_write_bytecode");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(95);
      var3 = var1.getglobal("True");
      var1.getglobal("sys").__setattr__("dont_write_bytecode", var3);
      var3 = null;
      var3 = null;

      PyObject var8;
      try {
         var1.setline(97);
         var1.getlocal(3).__getattr__("byte_compile").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects)));
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(99);
         var8 = var1.getlocal(4);
         var1.getglobal("sys").__setattr__("dont_write_bytecode", var8);
         var4 = null;
         throw (Throwable)var6;
      }

      var1.setline(99);
      var8 = var1.getlocal(4);
      var1.getglobal("sys").__setattr__("dont_write_bytecode", var8);
      var4 = null;
      var1.setline(101);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      PyString var9 = PyString.fromInterned("byte-compiling is disabled");
      PyObject var10002 = var9._in(var1.getlocal(0).__getattr__("logs").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$8(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("InstallLibTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_install_lib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InstallLibTestCase$1 = Py.newCode(0, var2, var1, "InstallLibTestCase", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd"};
      test_finalize_options$2 = Py.newCode(1, var2, var1, "test_finalize_options", 17, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd", "f"};
      _setup_byte_compile$3 = Py.newCode(1, var2, var1, "_setup_byte_compile", 35, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_dir"};
      test_byte_compile$4 = Py.newCode(1, var2, var1, "test_byte_compile", 45, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd", "f"};
      test_get_outputs$5 = Py.newCode(1, var2, var1, "test_get_outputs", 53, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd", "f"};
      test_get_inputs$6 = Py.newCode(1, var2, var1, "test_get_inputs", 70, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd", "old_dont_write_bytecode"};
      test_dont_write_bytecode$7 = Py.newCode(1, var2, var1, "test_dont_write_bytecode", 87, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$8 = Py.newCode(0, var2, var1, "test_suite", 103, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_install_lib$py("distutils/tests/test_install_lib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_install_lib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.InstallLibTestCase$1(var2, var3);
         case 2:
            return this.test_finalize_options$2(var2, var3);
         case 3:
            return this._setup_byte_compile$3(var2, var3);
         case 4:
            return this.test_byte_compile$4(var2, var3);
         case 5:
            return this.test_get_outputs$5(var2, var3);
         case 6:
            return this.test_get_inputs$6(var2, var3);
         case 7:
            return this.test_dont_write_bytecode$7(var2, var3);
         case 8:
            return this.test_suite$8(var2, var3);
         default:
            return null;
      }
   }
}
