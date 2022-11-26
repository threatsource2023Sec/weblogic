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
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("distutils/tests/test_build_py.py")
public class test_build_py$py extends PyFunctionTable implements PyRunnable {
   static test_build_py$py self;
   static final PyCode f$0;
   static final PyCode BuildPyTestCase$1;
   static final PyCode test_package_data$2;
   static final PyCode test_empty_package_dir$3;
   static final PyCode test_dont_write_bytecode$4;
   static final PyCode test_suite$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.build_py."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.build_py.");
      var1.setline(3);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("StringIO", var1, -1);
      var1.setlocal("StringIO", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(8);
      String[] var5 = new String[]{"build_py"};
      PyObject[] var6 = imp.importFrom("distutils.command.build_py", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("build_py", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"Distribution"};
      var6 = imp.importFrom("distutils.core", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"DistutilsFileError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsFileError", var4);
      var4 = null;
      var1.setline(12);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(15);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("BuildPyTestCase", var6, BuildPyTestCase$1);
      var1.setlocal("BuildPyTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(112);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, test_suite$5, (PyObject)null);
      var1.setlocal("test_suite", var8);
      var3 = null;
      var1.setline(115);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(116);
         var10000 = var1.getname("unittest").__getattr__("main");
         var6 = new PyObject[]{PyString.fromInterned("test_suite")};
         String[] var7 = new String[]{"defaultTest"};
         var10000.__call__(var2, var6, var7);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BuildPyTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(19);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_package_data$2, (PyObject)null);
      var1.setlocal("test_package_data", var4);
      var3 = null;
      var1.setline(62);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_empty_package_dir$3, (PyObject)null);
      var1.setlocal("test_empty_package_dir", var4);
      var3 = null;
      var1.setline(96);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dont_write_bytecode$4, (PyObject)null);
      var1.setlocal("test_dont_write_bytecode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_package_data$2(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(21);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("__init__.py")), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(22);
      var1.getlocal(2).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("# Pretend this is a package."));
      var1.setline(23);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(24);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("README.txt")), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(25);
      var1.getlocal(2).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Info about this package"));
      var1.setline(26);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(28);
      var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("packages"), new PyList(new PyObject[]{PyString.fromInterned("pkg")}), PyString.fromInterned("package_dir"), new PyDictionary(new PyObject[]{PyString.fromInterned("pkg"), var1.getlocal(1)})})));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(33);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("setup.py"));
      var1.getlocal(4).__setattr__("script_name", var3);
      var3 = null;
      var1.setline(34);
      PyObject var10000 = var1.getglobal("support").__getattr__("DummyCommand");
      PyObject[] var5 = new PyObject[]{Py.newInteger(0), var1.getlocal(3)};
      String[] var4 = new String[]{"force", "build_lib"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(4).__getattr__("command_obj").__setitem__((PyObject)PyString.fromInterned("build"), var3);
      var3 = null;
      var1.setline(37);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("pkg")});
      var1.getlocal(4).__setattr__((String)"packages", var6);
      var3 = null;
      var1.setline(38);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("pkg"), new PyList(new PyObject[]{PyString.fromInterned("README.txt")})});
      var1.getlocal(4).__setattr__((String)"package_data", var7);
      var3 = null;
      var1.setline(39);
      var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("pkg"), var1.getlocal(1)});
      var1.getlocal(4).__setattr__((String)"package_dir", var7);
      var3 = null;
      var1.setline(41);
      var3 = var1.getglobal("build_py").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(42);
      PyInteger var8 = Py.newInteger(1);
      var1.getlocal(5).__setattr__((String)"compile", var8);
      var3 = null;
      var1.setline(43);
      var1.getlocal(5).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(44);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5).__getattr__("package_data"), var1.getlocal(4).__getattr__("package_data"));
      var1.setline(46);
      var1.getlocal(5).__getattr__("run").__call__(var2);
      var1.setline(52);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5).__getattr__("get_outputs").__call__(var2)), (PyObject)Py.newInteger(3));
      var1.setline(53);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("pkg"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(54);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(6));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(55);
      var10000 = var1.getlocal(0).__getattr__("assert_");
      PyString var9 = PyString.fromInterned("__init__.py");
      PyObject var10002 = var9._in(var1.getlocal(7));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(56);
      if (var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
         var1.setline(57);
         var10000 = var1.getlocal(0).__getattr__("assert_");
         var9 = PyString.fromInterned("__init__$py.class");
         var10002 = var9._in(var1.getlocal(7));
         var3 = null;
         var10000.__call__(var2, var10002, var1.getlocal(7));
      } else {
         var1.setline(59);
         var10000 = var1.getlocal(0).__getattr__("assert_");
         var9 = PyString.fromInterned("__init__.pyc");
         var10002 = var9._in(var1.getlocal(7));
         var3 = null;
         var10000.__call__(var2, var10002);
      }

      var1.setline(60);
      var10000 = var1.getlocal(0).__getattr__("assert_");
      var9 = PyString.fromInterned("README.txt");
      var10002 = var9._in(var1.getlocal(7));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_empty_package_dir$3(PyFrame var1, ThreadState var2) {
      var1.setline(64);
      PyObject var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(67);
      var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(68);
      var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("__init__.py")), (PyObject)PyString.fromInterned("w")).__getattr__("close").__call__(var2);
      var1.setline(70);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("doc"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(71);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(3));
      var1.setline(72);
      var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("testfile")), (PyObject)PyString.fromInterned("w")).__getattr__("close").__call__(var2);
      var1.setline(74);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(2));
      var1.setline(75);
      var3 = var1.getglobal("sys").__getattr__("stdout");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(76);
      var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
      var1.getglobal("sys").__setattr__("stdout", var3);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(79);
         var4 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("packages"), new PyList(new PyObject[]{PyString.fromInterned("pkg")}), PyString.fromInterned("package_dir"), new PyDictionary(new PyObject[]{PyString.fromInterned("pkg"), PyString.fromInterned("")}), PyString.fromInterned("package_data"), new PyDictionary(new PyObject[]{PyString.fromInterned("pkg"), new PyList(new PyObject[]{PyString.fromInterned("doc/*")})})})));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(83);
         var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("setup.py"));
         var1.getlocal(5).__setattr__("script_name", var4);
         var4 = null;
         var1.setline(84);
         PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("build")});
         var1.getlocal(5).__setattr__((String)"script_args", var7);
         var4 = null;
         var1.setline(85);
         var1.getlocal(5).__getattr__("parse_command_line").__call__(var2);

         try {
            var1.setline(88);
            var1.getlocal(5).__getattr__("run_commands").__call__(var2);
         } catch (Throwable var5) {
            PyException var8 = Py.setException(var5, var1);
            if (!var8.match(var1.getglobal("DistutilsFileError"))) {
               throw var8;
            }

            var1.setline(90);
            var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("failed package_data test when package_dir is ''"));
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(93);
         var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(1));
         var1.setline(94);
         var4 = var1.getlocal(4);
         var1.getglobal("sys").__setattr__("stdout", var4);
         var4 = null;
         throw (Throwable)var6;
      }

      var1.setline(93);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(1));
      var1.setline(94);
      var4 = var1.getlocal(4);
      var1.getglobal("sys").__setattr__("stdout", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dont_write_bytecode$4(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(99);
      var3 = var1.getglobal("build_py").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(100);
      PyInteger var7 = Py.newInteger(1);
      var1.getlocal(3).__setattr__((String)"compile", var7);
      var3 = null;
      var1.setline(101);
      var7 = Py.newInteger(1);
      var1.getlocal(3).__setattr__((String)"optimize", var7);
      var3 = null;
      var1.setline(103);
      var3 = var1.getglobal("sys").__getattr__("dont_write_bytecode");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(104);
      var3 = var1.getglobal("True");
      var1.getglobal("sys").__setattr__("dont_write_bytecode", var3);
      var3 = null;
      var3 = null;

      PyObject var8;
      try {
         var1.setline(106);
         var1.getlocal(3).__getattr__("byte_compile").__call__((ThreadState)var2, (PyObject)(new PyList(Py.EmptyObjects)));
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(108);
         var8 = var1.getlocal(4);
         var1.getglobal("sys").__setattr__("dont_write_bytecode", var8);
         var4 = null;
         throw (Throwable)var6;
      }

      var1.setline(108);
      var8 = var1.getlocal(4);
      var1.getglobal("sys").__setattr__("dont_write_bytecode", var8);
      var4 = null;
      var1.setline(110);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      PyString var9 = PyString.fromInterned("byte-compiling is disabled");
      PyObject var10002 = var9._in(var1.getlocal(0).__getattr__("logs").__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$5(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("BuildPyTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_build_py$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BuildPyTestCase$1 = Py.newCode(0, var2, var1, "BuildPyTestCase", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "sources", "f", "destination", "dist", "cmd", "pkgdest", "files"};
      test_package_data$2 = Py.newCode(1, var2, var1, "test_package_data", 19, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cwd", "sources", "testdir", "old_stdout", "dist"};
      test_empty_package_dir$3 = Py.newCode(1, var2, var1, "test_empty_package_dir", 62, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd", "old_dont_write_bytecode"};
      test_dont_write_bytecode$4 = Py.newCode(1, var2, var1, "test_dont_write_bytecode", 96, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$5 = Py.newCode(0, var2, var1, "test_suite", 112, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_build_py$py("distutils/tests/test_build_py$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_build_py$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BuildPyTestCase$1(var2, var3);
         case 2:
            return this.test_package_data$2(var2, var3);
         case 3:
            return this.test_empty_package_dir$3(var2, var3);
         case 4:
            return this.test_dont_write_bytecode$4(var2, var3);
         case 5:
            return this.test_suite$5(var2, var3);
         default:
            return null;
      }
   }
}
