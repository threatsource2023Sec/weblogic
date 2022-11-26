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
@Filename("distutils/tests/test_bdist_wininst.py")
public class test_bdist_wininst$py extends PyFunctionTable implements PyRunnable {
   static test_bdist_wininst$py self;
   static final PyCode f$0;
   static final PyCode BuildWinInstTestCase$1;
   static final PyCode test_get_exe_bytes$2;
   static final PyCode test_suite$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.bdist_wininst."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.bdist_wininst.");
      var1.setline(2);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(4);
      String[] var5 = new String[]{"run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(6);
      var5 = new String[]{"bdist_wininst"};
      var6 = imp.importFrom("distutils.command.bdist_wininst", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("bdist_wininst", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(9);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("BuildWinInstTestCase", var6, BuildWinInstTestCase$1);
      var1.setlocal("BuildWinInstTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(28);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$3, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(31);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(32);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BuildWinInstTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_get_exe_bytes$2, (PyObject)null);
      var1.setlocal("test_get_exe_bytes", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_get_exe_bytes$2(PyFrame var1, ThreadState var2) {
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
      var3 = var1.getglobal("bdist_wininst").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(20);
      var1.getlocal(3).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(25);
      var3 = var1.getlocal(3).__getattr__("get_exe_bytes").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(26);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
      PyObject var10002 = var3._gt(Py.newInteger(10));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$3(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("BuildWinInstTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_bdist_wininst$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BuildWinInstTestCase$1 = Py.newCode(0, var2, var1, "BuildWinInstTestCase", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "pkg_pth", "dist", "cmd", "exe_file"};
      test_get_exe_bytes$2 = Py.newCode(1, var2, var1, "test_get_exe_bytes", 13, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$3 = Py.newCode(0, var2, var1, "test_suite", 28, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_bdist_wininst$py("distutils/tests/test_bdist_wininst$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_bdist_wininst$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BuildWinInstTestCase$1(var2, var3);
         case 2:
            return this.test_get_exe_bytes$2(var2, var3);
         case 3:
            return this.test_suite$3(var2, var3);
         default:
            return null;
      }
   }
}
