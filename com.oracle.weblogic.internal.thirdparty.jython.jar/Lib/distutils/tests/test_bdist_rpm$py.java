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
@Filename("distutils/tests/test_bdist_rpm.py")
public class test_bdist_rpm$py extends PyFunctionTable implements PyRunnable {
   static test_bdist_rpm$py self;
   static final PyCode f$0;
   static final PyCode BuildRpmTestCase$1;
   static final PyCode setUp$2;
   static final PyCode tearDown$3;
   static final PyCode test_quiet$4;
   static final PyCode test_no_optimize_flag$5;
   static final PyCode test_suite$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.bdist_rpm."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.bdist_rpm.");
      var1.setline(3);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("tempfile", var1, -1);
      var1.setlocal("tempfile", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var3);
      var3 = null;
      var1.setline(9);
      String[] var5 = new String[]{"run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(11);
      var5 = new String[]{"Distribution"};
      var6 = imp.importFrom("distutils.core", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(12);
      var5 = new String[]{"bdist_rpm"};
      var6 = imp.importFrom("distutils.command.bdist_rpm", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("bdist_rpm", var4);
      var4 = null;
      var1.setline(13);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(14);
      var5 = new String[]{"find_executable"};
      var6 = imp.importFrom("distutils.spawn", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("find_executable", var4);
      var4 = null;
      var1.setline(15);
      var5 = new String[]{"spawn"};
      var6 = imp.importFrom("distutils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("spawn", var4);
      var4 = null;
      var1.setline(16);
      var5 = new String[]{"DistutilsExecError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsExecError", var4);
      var4 = null;
      var1.setline(18);
      PyString var7 = PyString.fromInterned("from distutils.core import setup\nimport foo\n\nsetup(name='foo', version='0.1', py_modules=['foo'],\n      url='xxx', author='xxx', author_email='xxx')\n\n");
      var1.setlocal("SETUP_PY", var7);
      var3 = null;
      var1.setline(27);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("BuildRpmTestCase", var6, BuildRpmTestCase$1);
      var1.setlocal("BuildRpmTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(132);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, test_suite$6, (PyObject)null);
      var1.setlocal("test_suite", var8);
      var3 = null;
      var1.setline(135);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(136);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BuildRpmTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(31);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$2, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(36);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$3, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(42);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_quiet$4, (PyObject)null);
      var1.setlocal("test_quiet", var4);
      var3 = null;
      var1.setline(86);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_no_optimize_flag$5, (PyObject)null);
      var1.setlocal("test_no_optimize_flag", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$2(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      var1.getglobal("super").__call__(var2, var1.getglobal("BuildRpmTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(33);
      PyObject var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.getlocal(0).__setattr__("old_location", var3);
      var3 = null;
      var1.setline(34);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("argv"), var1.getglobal("sys").__getattr__("argv").__getslice__((PyObject)null, (PyObject)null, (PyObject)null)});
      var1.getlocal(0).__setattr__((String)"old_sys_argv", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$3(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(0).__getattr__("old_location"));
      var1.setline(38);
      PyObject var3 = var1.getlocal(0).__getattr__("old_sys_argv").__getitem__(Py.newInteger(0));
      var1.getglobal("sys").__setattr__("argv", var3);
      var3 = null;
      var1.setline(39);
      var3 = var1.getlocal(0).__getattr__("old_sys_argv").__getitem__(Py.newInteger(1));
      var1.getglobal("sys").__getattr__("argv").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var3);
      var3 = null;
      var1.setline(40);
      var1.getglobal("super").__call__(var2, var1.getglobal("BuildRpmTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_quiet$4(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._ne(PyString.fromInterned("linux2"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(47);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(50);
         var3 = var1.getglobal("find_executable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rpm"));
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getglobal("find_executable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rpmbuild"));
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(52);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(55);
            var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(56);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo"));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(57);
            var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(2));
            var1.setline(58);
            var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("setup.py")})), (PyObject)var1.getglobal("SETUP_PY"));
            var1.setline(59);
            var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("foo.py")})), (PyObject)PyString.fromInterned("#"));
            var1.setline(60);
            var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("MANIFEST.in")})), (PyObject)PyString.fromInterned("include foo.py"));
            var1.setline(61);
            var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("README")})), (PyObject)PyString.fromInterned(""));
            var1.setline(63);
            var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("foo"), PyString.fromInterned("version"), PyString.fromInterned("0.1"), PyString.fromInterned("py_modules"), new PyList(new PyObject[]{PyString.fromInterned("foo")}), PyString.fromInterned("url"), PyString.fromInterned("xxx"), PyString.fromInterned("author"), PyString.fromInterned("xxx"), PyString.fromInterned("author_email"), PyString.fromInterned("xxx")})));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(67);
            PyString var4 = PyString.fromInterned("setup.py");
            var1.getlocal(3).__setattr__((String)"script_name", var4);
            var3 = null;
            var1.setline(68);
            var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(2));
            var1.setline(70);
            PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("setup.py")});
            var1.getglobal("sys").__setattr__((String)"argv", var5);
            var3 = null;
            var1.setline(71);
            var3 = var1.getglobal("bdist_rpm").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(72);
            var3 = var1.getglobal("True");
            var1.getlocal(4).__setattr__("fix_python", var3);
            var3 = null;
            var1.setline(75);
            PyInteger var6 = Py.newInteger(1);
            var1.getlocal(4).__setattr__((String)"quiet", var6);
            var3 = null;
            var1.setline(76);
            var1.getlocal(4).__getattr__("ensure_finalized").__call__(var2);
            var1.setline(77);
            var1.getlocal(4).__getattr__("run").__call__(var2);
            var1.setline(79);
            var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("dist")));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(80);
            var10000 = var1.getlocal(0).__getattr__("assertTrue");
            var4 = PyString.fromInterned("foo-0.1-1.noarch.rpm");
            PyObject var10002 = var4._in(var1.getlocal(5));
            var3 = null;
            var10000.__call__(var2, var10002);
            var1.setline(83);
            var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("bdist_rpm"), PyString.fromInterned("any"), PyString.fromInterned("dist/foo-0.1-1.src.rpm")})), (PyObject)var1.getlocal(3).__getattr__("dist_files"));
            var1.setline(84);
            var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("bdist_rpm"), PyString.fromInterned("any"), PyString.fromInterned("dist/foo-0.1-1.noarch.rpm")})), (PyObject)var1.getlocal(3).__getattr__("dist_files"));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject test_no_optimize_flag$5(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._ne(PyString.fromInterned("linux2"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(91);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(95);
         var3 = var1.getglobal("find_executable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rpm"));
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getglobal("find_executable").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rpmbuild"));
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(97);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(100);
            var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(101);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo"));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(102);
            var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(2));
            var1.setline(103);
            var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("setup.py")})), (PyObject)var1.getglobal("SETUP_PY"));
            var1.setline(104);
            var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("foo.py")})), (PyObject)PyString.fromInterned("#"));
            var1.setline(105);
            var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("MANIFEST.in")})), (PyObject)PyString.fromInterned("include foo.py"));
            var1.setline(106);
            var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("README")})), (PyObject)PyString.fromInterned(""));
            var1.setline(108);
            var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("foo"), PyString.fromInterned("version"), PyString.fromInterned("0.1"), PyString.fromInterned("py_modules"), new PyList(new PyObject[]{PyString.fromInterned("foo")}), PyString.fromInterned("url"), PyString.fromInterned("xxx"), PyString.fromInterned("author"), PyString.fromInterned("xxx"), PyString.fromInterned("author_email"), PyString.fromInterned("xxx")})));
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(112);
            PyString var4 = PyString.fromInterned("setup.py");
            var1.getlocal(3).__setattr__((String)"script_name", var4);
            var3 = null;
            var1.setline(113);
            var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(2));
            var1.setline(115);
            PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("setup.py")});
            var1.getglobal("sys").__setattr__((String)"argv", var5);
            var3 = null;
            var1.setline(116);
            var3 = var1.getglobal("bdist_rpm").__call__(var2, var1.getlocal(3));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(117);
            var3 = var1.getglobal("True");
            var1.getlocal(4).__setattr__("fix_python", var3);
            var3 = null;
            var1.setline(119);
            PyInteger var6 = Py.newInteger(1);
            var1.getlocal(4).__setattr__((String)"quiet", var6);
            var3 = null;
            var1.setline(120);
            var1.getlocal(4).__getattr__("ensure_finalized").__call__(var2);
            var1.setline(121);
            var1.getlocal(4).__getattr__("run").__call__(var2);
            var1.setline(123);
            var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("dist")));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(124);
            var10000 = var1.getlocal(0).__getattr__("assertTrue");
            var4 = PyString.fromInterned("foo-0.1-1.noarch.rpm");
            PyObject var10002 = var4._in(var1.getlocal(5));
            var3 = null;
            var10000.__call__(var2, var10002);
            var1.setline(127);
            var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("bdist_rpm"), PyString.fromInterned("any"), PyString.fromInterned("dist/foo-0.1-1.src.rpm")})), (PyObject)var1.getlocal(3).__getattr__("dist_files"));
            var1.setline(128);
            var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("bdist_rpm"), PyString.fromInterned("any"), PyString.fromInterned("dist/foo-0.1-1.noarch.rpm")})), (PyObject)var1.getlocal(3).__getattr__("dist_files"));
            var1.setline(130);
            var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("dist"), (PyObject)PyString.fromInterned("foo-0.1-1.noarch.rpm")));
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject test_suite$6(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("BuildRpmTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_bdist_rpm$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BuildRpmTestCase$1 = Py.newCode(0, var2, var1, "BuildRpmTestCase", 27, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$2 = Py.newCode(1, var2, var1, "setUp", 31, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$3 = Py.newCode(1, var2, var1, "tearDown", 36, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmp_dir", "pkg_dir", "dist", "cmd", "dist_created"};
      test_quiet$4 = Py.newCode(1, var2, var1, "test_quiet", 42, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmp_dir", "pkg_dir", "dist", "cmd", "dist_created"};
      test_no_optimize_flag$5 = Py.newCode(1, var2, var1, "test_no_optimize_flag", 86, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$6 = Py.newCode(0, var2, var1, "test_suite", 132, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_bdist_rpm$py("distutils/tests/test_bdist_rpm$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_bdist_rpm$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BuildRpmTestCase$1(var2, var3);
         case 2:
            return this.setUp$2(var2, var3);
         case 3:
            return this.tearDown$3(var2, var3);
         case 4:
            return this.test_quiet$4(var2, var3);
         case 5:
            return this.test_no_optimize_flag$5(var2, var3);
         case 6:
            return this.test_suite$6(var2, var3);
         default:
            return null;
      }
   }
}
