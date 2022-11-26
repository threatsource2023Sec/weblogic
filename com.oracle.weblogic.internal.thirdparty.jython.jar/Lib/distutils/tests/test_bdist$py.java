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
@Filename("distutils/tests/test_bdist.py")
public class test_bdist$py extends PyFunctionTable implements PyRunnable {
   static test_bdist$py self;
   static final PyCode f$0;
   static final PyCode BuildTestCase$1;
   static final PyCode test_formats$2;
   static final PyCode test_skip_build$3;
   static final PyCode test_suite$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.bdist."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.bdist.");
      var1.setline(2);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(5);
      String[] var5 = new String[]{"run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"bdist"};
      var6 = imp.importFrom("distutils.command.bdist", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("bdist", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(11);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("BuildTestCase", var6, BuildTestCase$1);
      var1.setlocal("BuildTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(48);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$4, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(51);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(52);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BuildTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(14);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_formats$2, (PyObject)null);
      var1.setlocal("test_formats", var4);
      var3 = null;
      var1.setline(29);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_skip_build$3, (PyObject)null);
      var1.setlocal("test_skip_build", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_formats$2(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2).__getitem__(Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(18);
      var3 = var1.getglobal("bdist").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(19);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("msi")});
      var1.getlocal(2).__setattr__((String)"formats", var4);
      var3 = null;
      var1.setline(20);
      var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(21);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("formats"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("msi")})));
      var1.setline(24);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("bztar"), PyString.fromInterned("gztar"), PyString.fromInterned("msi"), PyString.fromInterned("rpm"), PyString.fromInterned("tar"), PyString.fromInterned("wininst"), PyString.fromInterned("zip"), PyString.fromInterned("ztar")});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(26);
      var3 = var1.getglobal("sorted").__call__(var2, var1.getlocal(2).__getattr__("format_command"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(27);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_skip_build$3(PyFrame var1, ThreadState var2) {
      var1.setline(31);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2).__getitem__(Py.newInteger(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(32);
      var3 = var1.getglobal("bdist").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(33);
      PyInteger var6 = Py.newInteger(1);
      var1.getlocal(2).__setattr__((String)"skip_build", var6);
      var3 = null;
      var1.setline(34);
      var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(35);
      var3 = var1.getlocal(2);
      var1.getlocal(1).__getattr__("command_obj").__setitem__((PyObject)PyString.fromInterned("bdist"), var3);
      var3 = null;
      var1.setline(37);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("bdist_dumb"), PyString.fromInterned("bdist_wininst")});
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(39);
      var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(40);
         var1.getlocal(3).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bdist_msi"));
      }

      var1.setline(42);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(42);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(43);
         PyObject var5 = var1.getlocal(2).__getattr__("get_finalized_command").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(44);
         var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(5).__getattr__("skip_build"), PyString.fromInterned("%s should take --skip-build from bdist")._mod(var1.getlocal(4)));
      }
   }

   public PyObject test_suite$4(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("BuildTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_bdist$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BuildTestCase$1 = Py.newCode(0, var2, var1, "BuildTestCase", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dist", "cmd", "formats", "found"};
      test_formats$2 = Py.newCode(1, var2, var1, "test_formats", 14, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "names", "name", "subcmd"};
      test_skip_build$3 = Py.newCode(1, var2, var1, "test_skip_build", 29, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$4 = Py.newCode(0, var2, var1, "test_suite", 48, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_bdist$py("distutils/tests/test_bdist$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_bdist$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BuildTestCase$1(var2, var3);
         case 2:
            return this.test_formats$2(var2, var3);
         case 3:
            return this.test_skip_build$3(var2, var3);
         case 4:
            return this.test_suite$4(var2, var3);
         default:
            return null;
      }
   }
}
