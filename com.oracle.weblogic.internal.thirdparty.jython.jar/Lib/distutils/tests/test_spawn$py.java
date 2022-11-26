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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_spawn.py")
public class test_spawn$py extends PyFunctionTable implements PyRunnable {
   static test_spawn$py self;
   static final PyCode f$0;
   static final PyCode SpawnTestCase$1;
   static final PyCode test_nt_quote_args$2;
   static final PyCode test_spawn$3;
   static final PyCode test_suite$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.spawn."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.spawn.");
      var1.setline(2);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(5);
      String[] var5 = new String[]{"captured_stdout", "run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("captured_stdout", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"_nt_quote_args"};
      var6 = imp.importFrom("distutils.spawn", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("_nt_quote_args", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"spawn", "find_executable"};
      var6 = imp.importFrom("distutils.spawn", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("spawn", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("find_executable", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"DistutilsExecError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsExecError", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(12);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("SpawnTestCase", var6, SpawnTestCase$1);
      var1.setlocal("SpawnTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(56);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$4, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(59);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(60);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject SpawnTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, test_nt_quote_args$2, (PyObject)null);
      var1.setlocal("test_nt_quote_args", var5);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_spawn$3, (PyObject)null);
      PyObject var10000 = var1.getname("unittest").__getattr__("skipUnless");
      PyObject var4 = var1.getname("os").__getattr__("name");
      PyObject var10002 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("nt"), PyString.fromInterned("posix")}));
      var4 = null;
      PyObject var6 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("Runs only under posix or nt")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_spawn", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_nt_quote_args$2(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = (new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("with space"), PyString.fromInterned("nospace")}), new PyList(new PyObject[]{PyString.fromInterned("\"with space\""), PyString.fromInterned("nospace")})}), new PyTuple(new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("nochange"), PyString.fromInterned("nospace")}), new PyList(new PyObject[]{PyString.fromInterned("nochange"), PyString.fromInterned("nospace")})})})).__iter__();

      while(true) {
         var1.setline(18);
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
         var1.setline(22);
         PyObject var7 = var1.getglobal("_nt_quote_args").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var7);
         var5 = null;
         var1.setline(23);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3), var1.getlocal(2));
      }
   }

   public PyObject test_spawn$3(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(33);
      var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("posix"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(34);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo.sh"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(35);
         var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("#!/bin/sh\nexit 1"));
         var1.setline(36);
         var1.getglobal("os").__getattr__("chmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(511));
      } else {
         var1.setline(38);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo.bat"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(39);
         var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("exit 1"));
      }

      var1.setline(41);
      var1.getglobal("os").__getattr__("chmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(511));
      var1.setline(42);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsExecError"), (PyObject)var1.getglobal("spawn"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(2)})));
      var1.setline(45);
      var3 = var1.getglobal("os").__getattr__("name");
      var10000 = var3._eq(PyString.fromInterned("posix"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(46);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo.sh"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(47);
         var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("#!/bin/sh\nexit 0"));
         var1.setline(48);
         var1.getglobal("os").__getattr__("chmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(511));
      } else {
         var1.setline(50);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo.bat"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(51);
         var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("exit 0"));
      }

      var1.setline(53);
      var1.getglobal("os").__getattr__("chmod").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)Py.newInteger(511));
      var1.setline(54);
      var1.getglobal("spawn").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$4(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("SpawnTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_spawn$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      SpawnTestCase$1 = Py.newCode(0, var2, var1, "SpawnTestCase", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "wanted", "res"};
      test_nt_quote_args$2 = Py.newCode(1, var2, var1, "test_nt_quote_args", 16, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmpdir", "exe"};
      test_spawn$3 = Py.newCode(1, var2, var1, "test_spawn", 26, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$4 = Py.newCode(0, var2, var1, "test_suite", 56, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_spawn$py("distutils/tests/test_spawn$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_spawn$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.SpawnTestCase$1(var2, var3);
         case 2:
            return this.test_nt_quote_args$2(var2, var3);
         case 3:
            return this.test_spawn$3(var2, var3);
         case 4:
            return this.test_suite$4(var2, var3);
         default:
            return null;
      }
   }
}
