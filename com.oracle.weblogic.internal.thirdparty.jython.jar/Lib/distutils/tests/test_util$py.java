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
@Filename("distutils/tests/test_util.py")
public class test_util$py extends PyFunctionTable implements PyRunnable {
   static test_util$py self;
   static final PyCode f$0;
   static final PyCode UtilTestCase$1;
   static final PyCode test_dont_write_bytecode$2;
   static final PyCode test_suite$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.util."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.util.");
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
      var5 = new String[]{"DistutilsPlatformError", "DistutilsByteCompileError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("DistutilsByteCompileError", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"byte_compile"};
      var6 = imp.importFrom("distutils.util", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("byte_compile", var4);
      var4 = null;
      var1.setline(9);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("UtilTestCase", var6, UtilTestCase$1);
      var1.setlocal("UtilTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(21);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$3, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(24);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(25);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject UtilTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(11);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_dont_write_bytecode$2, (PyObject)null);
      var1.setlocal("test_dont_write_bytecode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_dont_write_bytecode$2(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      PyObject var3 = var1.getglobal("sys").__getattr__("dont_write_bytecode");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(15);
      var3 = var1.getglobal("True");
      var1.getglobal("sys").__setattr__("dont_write_bytecode", var3);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(17);
         var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsByteCompileError"), (PyObject)var1.getglobal("byte_compile"), (PyObject)(new PyList(Py.EmptyObjects)));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(19);
         var4 = var1.getlocal(1);
         var1.getglobal("sys").__setattr__("dont_write_bytecode", var4);
         var4 = null;
         throw (Throwable)var5;
      }

      var1.setline(19);
      var4 = var1.getlocal(1);
      var1.getglobal("sys").__setattr__("dont_write_bytecode", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$3(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("UtilTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UtilTestCase$1 = Py.newCode(0, var2, var1, "UtilTestCase", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "old_dont_write_bytecode"};
      test_dont_write_bytecode$2 = Py.newCode(1, var2, var1, "test_dont_write_bytecode", 11, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$3 = Py.newCode(0, var2, var1, "test_suite", 21, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_util$py("distutils/tests/test_util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.UtilTestCase$1(var2, var3);
         case 2:
            return this.test_dont_write_bytecode$2(var2, var3);
         case 3:
            return this.test_suite$3(var2, var3);
         default:
            return null;
      }
   }
}
