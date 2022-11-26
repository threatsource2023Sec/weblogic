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
import org.python.core.PyException;
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
@Filename("distutils/tests/test_bdist_dumb.py")
public class test_bdist_dumb$py extends PyFunctionTable implements PyRunnable {
   static test_bdist_dumb$py self;
   static final PyCode f$0;
   static final PyCode BuildDumbTestCase$1;
   static final PyCode setUp$2;
   static final PyCode tearDown$3;
   static final PyCode test_simple_built$4;
   static final PyCode f$5;
   static final PyCode test_finalize_options$6;
   static final PyCode test_suite$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.bdist_dumb."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.bdist_dumb.");
      var1.setline(3);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("zipfile", var1, -1);
      var1.setlocal("zipfile", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(7);
      String[] var6 = new String[]{"run_unittest"};
      PyObject[] var7 = imp.importFrom("test.test_support", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;

      try {
         var1.setline(12);
         var3 = imp.importOne("zlib", var1, -1);
         var1.setlocal("zlib", var3);
         var3 = null;
      } catch (Throwable var5) {
         PyException var8 = Py.setException(var5, var1);
         if (!var8.match(var1.getname("ImportError"))) {
            throw var8;
         }

         var1.setline(14);
         var4 = var1.getname("None");
         var1.setlocal("zlib", var4);
         var4 = null;
      }

      var1.setline(16);
      var6 = new String[]{"Distribution"};
      var7 = imp.importFrom("distutils.core", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(17);
      var6 = new String[]{"bdist_dumb"};
      var7 = imp.importFrom("distutils.command.bdist_dumb", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("bdist_dumb", var4);
      var4 = null;
      var1.setline(18);
      var6 = new String[]{"support"};
      var7 = imp.importFrom("distutils.tests", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(20);
      PyString var9 = PyString.fromInterned("from distutils.core import setup\nimport foo\n\nsetup(name='foo', version='0.1', py_modules=['foo'],\n      url='xxx', author='xxx', author_email='xxx')\n\n");
      var1.setlocal("SETUP_PY", var9);
      var3 = null;
      var1.setline(29);
      var7 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("support").__getattr__("EnvironGuard"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("BuildDumbTestCase", var7, BuildDumbTestCase$1);
      var1.setlocal("BuildDumbTestCase", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(109);
      var7 = Py.EmptyObjects;
      PyFunction var10 = new PyFunction(var1.f_globals, var7, test_suite$7, (PyObject)null);
      var1.setlocal("test_suite", var10);
      var3 = null;
      var1.setline(112);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(113);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BuildDumbTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(34);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$2, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(39);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$3, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(45);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_simple_built$4, (PyObject)null);
      PyObject var5 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("zlib"), (PyObject)PyString.fromInterned("requires zlib")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_simple_built", var5);
      var3 = null;
      var1.setline(94);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_finalize_options$6, (PyObject)null);
      var1.setlocal("test_finalize_options", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$2(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      var1.getglobal("super").__call__(var2, var1.getglobal("BuildDumbTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(36);
      PyObject var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.getlocal(0).__setattr__("old_location", var3);
      var3 = null;
      var1.setline(37);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("argv"), var1.getglobal("sys").__getattr__("argv").__getslice__((PyObject)null, (PyObject)null, (PyObject)null)});
      var1.getlocal(0).__setattr__((String)"old_sys_argv", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$3(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(0).__getattr__("old_location"));
      var1.setline(41);
      PyObject var3 = var1.getlocal(0).__getattr__("old_sys_argv").__getitem__(Py.newInteger(0));
      var1.getglobal("sys").__setattr__("argv", var3);
      var3 = null;
      var1.setline(42);
      var3 = var1.getlocal(0).__getattr__("old_sys_argv").__getitem__(Py.newInteger(1));
      var1.getglobal("sys").__getattr__("argv").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var3);
      var3 = null;
      var1.setline(43);
      var1.getglobal("super").__call__(var2, var1.getglobal("BuildDumbTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_simple_built$4(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(50);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(51);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(2));
      var1.setline(52);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("setup.py")})), (PyObject)var1.getglobal("SETUP_PY"));
      var1.setline(53);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("foo.py")})), (PyObject)PyString.fromInterned("#"));
      var1.setline(54);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("MANIFEST.in")})), (PyObject)PyString.fromInterned("include foo.py"));
      var1.setline(55);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), PyString.fromInterned("README")})), (PyObject)PyString.fromInterned(""));
      var1.setline(57);
      var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("foo"), PyString.fromInterned("version"), PyString.fromInterned("0.1"), PyString.fromInterned("py_modules"), new PyList(new PyObject[]{PyString.fromInterned("foo")}), PyString.fromInterned("url"), PyString.fromInterned("xxx"), PyString.fromInterned("author"), PyString.fromInterned("xxx"), PyString.fromInterned("author_email"), PyString.fromInterned("xxx")})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(61);
      PyString var7 = PyString.fromInterned("setup.py");
      var1.getlocal(3).__setattr__((String)"script_name", var7);
      var3 = null;
      var1.setline(62);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(2));
      var1.setline(64);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("setup.py")});
      var1.getglobal("sys").__setattr__((String)"argv", var8);
      var3 = null;
      var1.setline(65);
      var3 = var1.getglobal("bdist_dumb").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(69);
      var7 = PyString.fromInterned("zip");
      var1.getlocal(4).__setattr__((String)"format", var7);
      var3 = null;
      var1.setline(71);
      var1.getlocal(4).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(72);
      var1.getlocal(4).__getattr__("run").__call__(var2);
      var1.setline(75);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("dist")));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(76);
      var3 = PyString.fromInterned("%s.%s.zip")._mod(new PyTuple(new PyObject[]{var1.getlocal(3).__getattr__("get_fullname").__call__(var2), var1.getlocal(4).__getattr__("plat_name")}));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("os2"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(78);
         var3 = var1.getlocal(6).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)PyString.fromInterned("-"));
         var1.setlocal(6, var3);
         var3 = null;
      }

      var1.setline(80);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)(new PyList(new PyObject[]{var1.getlocal(6)})));
      var1.setline(83);
      var3 = var1.getglobal("zipfile").__getattr__("ZipFile").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dist"), (PyObject)var1.getlocal(6)));
      var1.setlocal(7, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(85);
         PyObject var4 = var1.getlocal(7).__getattr__("namelist").__call__(var2);
         var1.setlocal(8, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(87);
         var1.getlocal(7).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(87);
      var1.getlocal(7).__getattr__("close").__call__(var2);
      var1.setline(89);
      var10000 = var1.getglobal("sorted");
      var1.setline(89);
      PyObject[] var9 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var9, f$5, (PyObject)null);
      PyObject var10002 = var6.__call__(var2, var1.getlocal(8).__iter__());
      Arrays.fill(var9, (Object)null);
      var3 = var10000.__call__(var2, var10002);
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(90);
      var8 = new PyList(new PyObject[]{PyString.fromInterned("foo-0.1-py%s.%s.egg-info")._mod(var1.getglobal("sys").__getattr__("version_info").__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null)), PyString.fromInterned("foo.py"), PyString.fromInterned("foo.pyc")});
      var1.setlocal(10, var8);
      var3 = null;
      var1.setline(92);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(8), var1.getglobal("sorted").__call__(var2, var1.getlocal(10)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$5(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(89);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(89);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(89);
         var1.setline(89);
         var6 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject test_finalize_options$6(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(96);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(1));
      var1.setline(97);
      var3 = var1.getglobal("bdist_dumb").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(98);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("bdist_dir"), var1.getglobal("None"));
      var1.setline(99);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(102);
      var3 = var1.getlocal(3).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bdist")).__getattr__("bdist_base");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(103);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("bdist_dir"), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("dumb")));
      var1.setline(106);
      var3 = var1.getlocal(3).__getattr__("default_format").__getitem__(var1.getglobal("os").__getattr__("name"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(107);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("format"), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$7(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("BuildDumbTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_bdist_dumb$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BuildDumbTestCase$1 = Py.newCode(0, var2, var1, "BuildDumbTestCase", 29, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$2 = Py.newCode(1, var2, var1, "setUp", 34, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$3 = Py.newCode(1, var2, var1, "tearDown", 39, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmp_dir", "pkg_dir", "dist", "cmd", "dist_created", "base", "fp", "contents", "_(89_26)", "wanted"};
      test_simple_built$4 = Py.newCode(1, var2, var1, "test_simple_built", 45, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "fn"};
      f$5 = Py.newCode(1, var2, var1, "<genexpr>", 89, false, false, self, 5, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd", "base", "default"};
      test_finalize_options$6 = Py.newCode(1, var2, var1, "test_finalize_options", 94, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$7 = Py.newCode(0, var2, var1, "test_suite", 109, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_bdist_dumb$py("distutils/tests/test_bdist_dumb$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_bdist_dumb$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BuildDumbTestCase$1(var2, var3);
         case 2:
            return this.setUp$2(var2, var3);
         case 3:
            return this.tearDown$3(var2, var3);
         case 4:
            return this.test_simple_built$4(var2, var3);
         case 5:
            return this.f$5(var2, var3);
         case 6:
            return this.test_finalize_options$6(var2, var3);
         case 7:
            return this.test_suite$7(var2, var3);
         default:
            return null;
      }
   }
}
