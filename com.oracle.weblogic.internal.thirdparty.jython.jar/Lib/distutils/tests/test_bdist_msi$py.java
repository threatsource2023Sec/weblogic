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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_bdist_msi.py")
public class test_bdist_msi$py extends PyFunctionTable implements PyRunnable {
   static test_bdist_msi$py self;
   static final PyCode f$0;
   static final PyCode BDistMSITestCase$1;
   static final PyCode test_minimal$2;
   static final PyCode test_suite$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.bdist_msi."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.bdist_msi.");
      var1.setline(2);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(4);
      String[] var6 = new String[]{"run_unittest"};
      PyObject[] var7 = imp.importFrom("test.test_support", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(5);
      var6 = new String[]{"support"};
      var7 = imp.importFrom("distutils.tests", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(8);
      var7 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("BDistMSITestCase", var7, BDistMSITestCase$1);
      PyObject var10000 = var1.getname("unittest").__getattr__("skipUnless");
      PyObject var5 = var1.getname("sys").__getattr__("platform");
      PyObject var10002 = var5._eq(PyString.fromInterned("win32"));
      var5 = null;
      var4 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("these tests require Windows")).__call__(var2, var4);
      var1.setlocal("BDistMSITestCase", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(21);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, test_suite$3, (PyObject)null);
      var1.setlocal("test_suite", var8);
      var3 = null;
      var1.setline(24);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(25);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BDistMSITestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_minimal$2, (PyObject)null);
      var1.setlocal("test_minimal", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_minimal$2(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      String[] var3 = new String[]{"bdist_msi"};
      PyObject[] var6 = imp.importFrom("distutils.command.bdist_msi", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(16);
      PyObject var7 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var8 = Py.unpackSequence(var7, 2);
      PyObject var5 = var8[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(17);
      var7 = var1.getlocal(1).__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(18);
      var1.getlocal(4).__getattr__("ensure_finalized").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$3(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("BDistMSITestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_bdist_msi$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BDistMSITestCase$1 = Py.newCode(0, var2, var1, "BDistMSITestCase", 8, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "bdist_msi", "project_dir", "dist", "cmd"};
      test_minimal$2 = Py.newCode(1, var2, var1, "test_minimal", 13, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$3 = Py.newCode(0, var2, var1, "test_suite", 21, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_bdist_msi$py("distutils/tests/test_bdist_msi$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_bdist_msi$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BDistMSITestCase$1(var2, var3);
         case 2:
            return this.test_minimal$2(var2, var3);
         case 3:
            return this.test_suite$3(var2, var3);
         default:
            return null;
      }
   }
}
