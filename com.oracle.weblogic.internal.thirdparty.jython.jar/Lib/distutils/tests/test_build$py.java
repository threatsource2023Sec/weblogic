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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_build.py")
public class test_build$py extends PyFunctionTable implements PyRunnable {
   static test_build$py self;
   static final PyCode f$0;
   static final PyCode BuildTestCase$1;
   static final PyCode test_finalize_options$2;
   static final PyCode test_suite$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.build."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.build.");
      var1.setline(2);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      String[] var5 = new String[]{"run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"build"};
      var6 = imp.importFrom("distutils.command.build", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("build", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"get_platform"};
      var6 = imp.importFrom("sysconfig", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("get_platform", var4);
      var4 = null;
      var1.setline(11);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("BuildTestCase", var6, BuildTestCase$1);
      var1.setlocal("BuildTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(51);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$3, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(54);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(55);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BuildTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(15);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_finalize_options$2, (PyObject)null);
      var1.setlocal("test_finalize_options", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_finalize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(16);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(17);
      var3 = var1.getglobal("build").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(18);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(21);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("plat_name"), var1.getglobal("get_platform").__call__(var2));
      var1.setline(24);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("build_base"), (PyObject)PyString.fromInterned("lib"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(25);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("build_purelib"), var1.getlocal(4));
      var1.setline(30);
      var3 = PyString.fromInterned(".%s-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("plat_name"), var1.getglobal("sys").__getattr__("version").__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null)}));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(31);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys"), (PyObject)PyString.fromInterned("gettotalrefcount")).__nonzero__()) {
         var1.setline(32);
         var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(3).__getattr__("build_platlib").__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-pydebug")));
         var1.setline(33);
         var3 = var1.getlocal(5);
         var3 = var3._iadd(PyString.fromInterned("-pydebug"));
         var1.setlocal(5, var3);
      }

      var1.setline(34);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3).__getattr__("build_base"), PyString.fromInterned("lib")._add(var1.getlocal(5)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(35);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("build_platlib"), var1.getlocal(4));
      var1.setline(38);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("build_lib"), var1.getlocal(3).__getattr__("build_purelib"));
      var1.setline(41);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3).__getattr__("build_base"), PyString.fromInterned("temp")._add(var1.getlocal(5)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(42);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("build_temp"), var1.getlocal(4));
      var1.setline(45);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3).__getattr__("build_base"), PyString.fromInterned("scripts-")._add(var1.getglobal("sys").__getattr__("version").__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(46);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("build_scripts"), var1.getlocal(4));
      var1.setline(49);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("executable"), var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getglobal("sys").__getattr__("executable")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$3(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("BuildTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_build$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BuildTestCase$1 = Py.newCode(0, var2, var1, "BuildTestCase", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd", "wanted", "plat_spec"};
      test_finalize_options$2 = Py.newCode(1, var2, var1, "test_finalize_options", 15, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$3 = Py.newCode(0, var2, var1, "test_suite", 51, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_build$py("distutils/tests/test_build$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_build$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BuildTestCase$1(var2, var3);
         case 2:
            return this.test_finalize_options$2(var2, var3);
         case 3:
            return this.test_suite$3(var2, var3);
         default:
            return null;
      }
   }
}
