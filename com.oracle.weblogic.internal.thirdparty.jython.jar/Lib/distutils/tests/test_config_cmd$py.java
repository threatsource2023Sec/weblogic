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
@Filename("distutils/tests/test_config_cmd.py")
public class test_config_cmd$py extends PyFunctionTable implements PyRunnable {
   static test_config_cmd$py self;
   static final PyCode f$0;
   static final PyCode ConfigTestCase$1;
   static final PyCode _info$2;
   static final PyCode setUp$3;
   static final PyCode tearDown$4;
   static final PyCode test_dump_file$5;
   static final PyCode test_search_cpp$6;
   static final PyCode test_finalize_options$7;
   static final PyCode test_clean$8;
   static final PyCode test_suite$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.config."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.config.");
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
      var5 = new String[]{"dump_file", "config"};
      var6 = imp.importFrom("distutils.command.config", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("dump_file", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("config", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"log"};
      var6 = imp.importFrom("distutils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(11);
      var6 = new PyObject[]{var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("support").__getattr__("TempdirManager"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("ConfigTestCase", var6, ConfigTestCase$1);
      var1.setlocal("ConfigTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(86);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$9, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(89);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(90);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject ConfigTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(15);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _info$2, (PyObject)null);
      var1.setlocal("_info", var4);
      var3 = null;
      var1.setline(19);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setUp$3, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(25);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$4, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(29);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dump_file$5, (PyObject)null);
      var1.setlocal("test_dump_file", var4);
      var3 = null;
      var1.setline(40);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_search_cpp$6, (PyObject)null);
      var1.setlocal("test_search_cpp", var4);
      var3 = null;
      var1.setline(53);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_finalize_options$7, (PyObject)null);
      var1.setlocal("test_finalize_options", var4);
      var3 = null;
      var1.setline(67);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_clean$8, (PyObject)null);
      var1.setlocal("test_clean", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _info$2(PyFrame var1, ThreadState var2) {
      var1.setline(16);
      PyObject var3 = var1.getlocal(1).__getattr__("splitlines").__call__(var2).__iter__();

      while(true) {
         var1.setline(16);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(17);
         var1.getlocal(0).__getattr__("_logs").__getattr__("append").__call__(var2, var1.getlocal(3));
      }
   }

   public PyObject setUp$3(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      var1.getglobal("super").__call__(var2, var1.getglobal("ConfigTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(21);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_logs", var3);
      var3 = null;
      var1.setline(22);
      PyObject var4 = var1.getglobal("log").__getattr__("info");
      var1.getlocal(0).__setattr__("old_log", var4);
      var3 = null;
      var1.setline(23);
      var4 = var1.getlocal(0).__getattr__("_info");
      var1.getglobal("log").__setattr__("info", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$4(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyObject var3 = var1.getlocal(0).__getattr__("old_log");
      var1.getglobal("log").__setattr__("info", var3);
      var3 = null;
      var1.setline(27);
      var1.getglobal("super").__call__(var2, var1.getglobal("ConfigTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dump_file$5(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("__file__")).__getitem__(Py.newInteger(0))._add(PyString.fromInterned(".py"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(31);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(33);
         PyObject var4 = var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("readlines").__call__(var2));
         var1.setlocal(3, var4);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(35);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(35);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(37);
      var1.getglobal("dump_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("I am the header"));
      var1.setline(38);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_logs")), var1.getlocal(3)._add(Py.newInteger(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_search_cpp$6(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(42);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(43);
         var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(44);
         var3 = var1.getglobal("config").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(47);
         var10000 = var1.getlocal(3).__getattr__("search_cpp");
         PyObject[] var7 = new PyObject[]{PyString.fromInterned("xxx"), PyString.fromInterned("/* xxx */")};
         String[] var6 = new String[]{"pattern", "body"};
         var10000 = var10000.__call__(var2, var7, var6);
         var3 = null;
         var3 = var10000;
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(48);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(0));
         var1.setline(50);
         var10000 = var1.getlocal(3).__getattr__("search_cpp");
         var7 = new PyObject[]{PyString.fromInterned("_configtest"), PyString.fromInterned("/* xxx */")};
         var6 = new String[]{"pattern", "body"};
         var10000 = var10000.__call__(var2, var7, var6);
         var3 = null;
         var3 = var10000;
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(51);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)Py.newInteger(1));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_finalize_options$7(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(57);
      var3 = var1.getglobal("config").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(58);
      var3 = PyString.fromInterned("one%stwo")._mod(var1.getglobal("os").__getattr__("pathsep"));
      var1.getlocal(3).__setattr__("include_dirs", var3);
      var3 = null;
      var1.setline(59);
      PyString var6 = PyString.fromInterned("one");
      var1.getlocal(3).__setattr__((String)"libraries", var6);
      var3 = null;
      var1.setline(60);
      var3 = PyString.fromInterned("three%sfour")._mod(var1.getglobal("os").__getattr__("pathsep"));
      var1.getlocal(3).__setattr__("library_dirs", var3);
      var3 = null;
      var1.setline(61);
      var1.getlocal(3).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(63);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("include_dirs"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("one"), PyString.fromInterned("two")})));
      var1.setline(64);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("libraries"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("one")})));
      var1.setline(65);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("library_dirs"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("three"), PyString.fromInterned("four")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_clean$8(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(70);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("one"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("two"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(73);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(74);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(76);
      var3 = (new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})).__iter__();

      while(true) {
         var1.setline(76);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(79);
            var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
            PyObject[] var6 = Py.unpackSequence(var3, 2);
            PyObject var5 = var6[0];
            var1.setlocal(5, var5);
            var5 = null;
            var5 = var6[1];
            var1.setlocal(6, var5);
            var5 = null;
            var3 = null;
            var1.setline(80);
            var3 = var1.getglobal("config").__call__(var2, var1.getlocal(6));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(81);
            var1.getlocal(7).__getattr__("_clean").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            var1.setline(83);
            var3 = (new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})).__iter__();

            while(true) {
               var1.setline(83);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(4, var4);
               var1.setline(84);
               var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(4)).__not__());
            }
         }

         var1.setlocal(4, var4);
         var1.setline(77);
         var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(4)));
      }
   }

   public PyObject test_suite$9(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("ConfigTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_config_cmd$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      ConfigTestCase$1 = Py.newCode(0, var2, var1, "ConfigTestCase", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "msg", "args", "line"};
      _info$2 = Py.newCode(3, var2, var1, "_info", 15, true, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      setUp$3 = Py.newCode(1, var2, var1, "setUp", 19, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$4 = Py.newCode(1, var2, var1, "tearDown", 25, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "this_file", "f", "numlines"};
      test_dump_file$5 = Py.newCode(1, var2, var1, "test_dump_file", 29, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd", "match"};
      test_search_cpp$6 = Py.newCode(1, var2, var1, "test_search_cpp", 40, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd"};
      test_finalize_options$7 = Py.newCode(1, var2, var1, "test_finalize_options", 53, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmp_dir", "f1", "f2", "f", "pkg_dir", "dist", "cmd"};
      test_clean$8 = Py.newCode(1, var2, var1, "test_clean", 67, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$9 = Py.newCode(0, var2, var1, "test_suite", 86, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_config_cmd$py("distutils/tests/test_config_cmd$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_config_cmd$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.ConfigTestCase$1(var2, var3);
         case 2:
            return this._info$2(var2, var3);
         case 3:
            return this.setUp$3(var2, var3);
         case 4:
            return this.tearDown$4(var2, var3);
         case 5:
            return this.test_dump_file$5(var2, var3);
         case 6:
            return this.test_search_cpp$6(var2, var3);
         case 7:
            return this.test_finalize_options$7(var2, var3);
         case 8:
            return this.test_clean$8(var2, var3);
         case 9:
            return this.test_suite$9(var2, var3);
         default:
            return null;
      }
   }
}
