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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_install_data.py")
public class test_install_data$py extends PyFunctionTable implements PyRunnable {
   static test_install_data$py self;
   static final PyCode f$0;
   static final PyCode InstallDataTestCase$1;
   static final PyCode test_simple_run$2;
   static final PyCode test_suite$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.install_data."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.install_data.");
      var1.setline(2);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("getpass", var1, -1);
      var1.setlocal("getpass", var3);
      var3 = null;
      var1.setline(7);
      String[] var5 = new String[]{"install_data"};
      PyObject[] var6 = imp.importFrom("distutils.command.install_data", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("install_data", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"run_unittest"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(11);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("support").__getattr__("EnvironGuard"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("InstallDataTestCase", var6, InstallDataTestCase$1);
      var1.setlocal("InstallDataTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(73);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$3, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(76);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(77);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject InstallDataTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_simple_run$2, (PyObject)null);
      var1.setlocal("test_simple_run", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_simple_run$2(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(18);
      var3 = var1.getglobal("install_data").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(19);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("inst"));
      var1.getlocal(3).__setattr__("install_dir", var3);
      var1.setlocal(4, var3);
      var1.setline(24);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("one"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(25);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(26);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("inst2"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(27);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("two"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(28);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(30);
      PyList var6 = new PyList(new PyObject[]{var1.getlocal(5), new PyTuple(new PyObject[]{var1.getlocal(6), new PyList(new PyObject[]{var1.getlocal(7)})})});
      var1.getlocal(3).__setattr__((String)"data_files", var6);
      var3 = null;
      var1.setline(31);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_inputs").__call__(var2), (PyObject)(new PyList(new PyObject[]{var1.getlocal(5), new PyTuple(new PyObject[]{var1.getlocal(6), new PyList(new PyObject[]{var1.getlocal(7)})})})));
      var1.setline(34);
      var1.getlocal(3).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(35);
      var1.getlocal(3).__getattr__("run").__call__(var2);
      var1.setline(38);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_outputs").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(39);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(7)).__getitem__(Py.newInteger(-1));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(40);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(6), var1.getlocal(8))));
      var1.setline(41);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(5)).__getitem__(Py.newInteger(-1));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(42);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(9))));
      var1.setline(43);
      var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(3).__setattr__((String)"outfiles", var6);
      var3 = null;
      var1.setline(46);
      PyInteger var7 = Py.newInteger(1);
      var1.getlocal(3).__setattr__((String)"warn_dir", var7);
      var3 = null;
      var1.setline(47);
      var1.getlocal(3).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(48);
      var1.getlocal(3).__getattr__("run").__call__(var2);
      var1.setline(51);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_outputs").__call__(var2)), (PyObject)Py.newInteger(2));
      var1.setline(52);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(6), var1.getlocal(8))));
      var1.setline(53);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(9))));
      var1.setline(54);
      var6 = new PyList(Py.EmptyObjects);
      var1.getlocal(3).__setattr__((String)"outfiles", var6);
      var3 = null;
      var1.setline(57);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("root"));
      var1.getlocal(3).__setattr__("root", var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("install_dir"), (PyObject)PyString.fromInterned("inst3"));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("inst4"));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("install_dir"), (PyObject)PyString.fromInterned("three"));
      var1.setlocal(12, var3);
      var3 = null;
      var1.setline(61);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(12), (PyObject)PyString.fromInterned("xx"));
      var1.setline(62);
      var6 = new PyList(new PyObject[]{var1.getlocal(5), new PyTuple(new PyObject[]{var1.getlocal(6), new PyList(new PyObject[]{var1.getlocal(7)})}), new PyTuple(new PyObject[]{PyString.fromInterned("inst3"), new PyList(new PyObject[]{var1.getlocal(12)})}), new PyTuple(new PyObject[]{var1.getlocal(11), new PyList(Py.EmptyObjects)})});
      var1.getlocal(3).__setattr__((String)"data_files", var6);
      var3 = null;
      var1.setline(65);
      var1.getlocal(3).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(66);
      var1.getlocal(3).__getattr__("run").__call__(var2);
      var1.setline(69);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3).__getattr__("get_outputs").__call__(var2)), (PyObject)Py.newInteger(4));
      var1.setline(70);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(6), var1.getlocal(8))));
      var1.setline(71);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(9))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$3(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("InstallDataTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_install_data$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InstallDataTestCase$1 = Py.newCode(0, var2, var1, "InstallDataTestCase", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd", "inst", "one", "inst2", "two", "rtwo", "rone", "inst3", "inst4", "three"};
      test_simple_run$2 = Py.newCode(1, var2, var1, "test_simple_run", 16, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$3 = Py.newCode(0, var2, var1, "test_suite", 73, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_install_data$py("distutils/tests/test_install_data$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_install_data$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.InstallDataTestCase$1(var2, var3);
         case 2:
            return this.test_simple_run$2(var2, var3);
         case 3:
            return this.test_suite$3(var2, var3);
         default:
            return null;
      }
   }
}
