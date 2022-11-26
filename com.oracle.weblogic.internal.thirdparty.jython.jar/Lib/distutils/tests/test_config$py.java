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
@Filename("distutils/tests/test_config.py")
public class test_config$py extends PyFunctionTable implements PyRunnable {
   static test_config$py self;
   static final PyCode f$0;
   static final PyCode PyPIRCCommandTestCase$1;
   static final PyCode setUp$2;
   static final PyCode command$3;
   static final PyCode __init__$4;
   static final PyCode initialize_options$5;
   static final PyCode tearDown$6;
   static final PyCode test_server_registration$7;
   static final PyCode test_server_empty_registration$8;
   static final PyCode test_suite$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.pypirc.pypirc."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.pypirc.pypirc.");
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
      var3 = imp.importOne("tempfile", var1, -1);
      var1.setlocal("tempfile", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var3);
      var3 = null;
      var1.setline(8);
      String[] var5 = new String[]{"PyPIRCCommand"};
      PyObject[] var6 = imp.importFrom("distutils.core", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("PyPIRCCommand", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"Distribution"};
      var6 = imp.importFrom("distutils.core", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"set_threshold"};
      var6 = imp.importFrom("distutils.log", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("set_threshold", var4);
      var4 = null;
      var1.setline(11);
      var5 = new String[]{"WARN"};
      var6 = imp.importFrom("distutils.log", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("WARN", var4);
      var4 = null;
      var1.setline(13);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(14);
      var5 = new String[]{"run_unittest"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(16);
      PyString var7 = PyString.fromInterned("[distutils]\n\nindex-servers =\n    server1\n    server2\n\n[server1]\nusername:me\npassword:secret\n\n[server2]\nusername:meagain\npassword: secret\nrealm:acme\nrepository:http://another.pypi/\n");
      var1.setlocal("PYPIRC", var7);
      var3 = null;
      var1.setline(34);
      var7 = PyString.fromInterned("[server-login]\nusername:tarek\npassword:secret\n");
      var1.setlocal("PYPIRC_OLD", var7);
      var3 = null;
      var1.setline(40);
      var7 = PyString.fromInterned("[distutils]\nindex-servers =\n    pypi\n\n[pypi]\nusername:tarek\npassword:xxx\n");
      var1.setlocal("WANTED", var7);
      var3 = null;
      var1.setline(51);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("support").__getattr__("EnvironGuard"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("PyPIRCCommandTestCase", var6, PyPIRCCommandTestCase$1);
      var1.setlocal("PyPIRCCommandTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(119);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, test_suite$9, (PyObject)null);
      var1.setlocal("test_suite", var8);
      var3 = null;
      var1.setline(122);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(123);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject PyPIRCCommandTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(56);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$2, PyString.fromInterned("Patches the environment."));
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(74);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$6, PyString.fromInterned("Removes the patch."));
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(79);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_server_registration$7, (PyObject)null);
      var1.setlocal("test_server_registration", var4);
      var3 = null;
      var1.setline(106);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_server_empty_registration$8, (PyObject)null);
      var1.setlocal("test_server_empty_registration", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$2(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyString.fromInterned("Patches the environment.");
      var1.setline(58);
      var1.getglobal("super").__call__(var2, var1.getglobal("PyPIRCCommandTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(59);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.getlocal(0).__setattr__("tmp_dir", var3);
      var3 = null;
      var1.setline(60);
      var3 = var1.getlocal(0).__getattr__("tmp_dir");
      var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("HOME"), var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned(".pypirc"));
      var1.getlocal(0).__setattr__("rc", var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getglobal("Distribution").__call__(var2);
      var1.getlocal(0).__setattr__("dist", var3);
      var3 = null;
      var1.setline(64);
      PyObject[] var5 = new PyObject[]{var1.getglobal("PyPIRCCommand")};
      PyObject var4 = Py.makeClass("command", var5, command$3);
      var1.setlocal(1, var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(71);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_cmd", var3);
      var3 = null;
      var1.setline(72);
      var3 = var1.getglobal("set_threshold").__call__(var2, var1.getglobal("WARN"));
      var1.getlocal(0).__setattr__("old_threshold", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject command$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(65);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$4, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(67);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, initialize_options$5, (PyObject)null);
      var1.setlocal("initialize_options", var4);
      var3 = null;
      var1.setline(69);
      PyObject var5 = var1.getname("initialize_options");
      var1.setlocal("finalize_options", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$4(PyFrame var1, ThreadState var2) {
      var1.setline(66);
      var1.getglobal("PyPIRCCommand").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject initialize_options$5(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$6(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      PyString.fromInterned("Removes the patch.");
      var1.setline(76);
      var1.getglobal("set_threshold").__call__(var2, var1.getlocal(0).__getattr__("old_threshold"));
      var1.setline(77);
      var1.getglobal("super").__call__(var2, var1.getglobal("PyPIRCCommandTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_server_registration$7(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(0).__getattr__("rc"), var1.getglobal("PYPIRC"));
      var1.setline(86);
      PyObject var3 = var1.getlocal(0).__getattr__("_cmd").__call__(var2, var1.getlocal(0).__getattr__("dist"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(87);
      var3 = var1.getlocal(1).__getattr__("_read_pypirc").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(89);
      var3 = var1.getlocal(2).__getattr__("items").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(90);
      var1.getlocal(2).__getattr__("sort").__call__(var2);
      var1.setline(91);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("password"), PyString.fromInterned("secret")}), new PyTuple(new PyObject[]{PyString.fromInterned("realm"), PyString.fromInterned("pypi")}), new PyTuple(new PyObject[]{PyString.fromInterned("repository"), PyString.fromInterned("http://pypi.python.org/pypi")}), new PyTuple(new PyObject[]{PyString.fromInterned("server"), PyString.fromInterned("server1")}), new PyTuple(new PyObject[]{PyString.fromInterned("username"), PyString.fromInterned("me")})});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(94);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setline(97);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(0).__getattr__("rc"), var1.getglobal("PYPIRC_OLD"));
      var1.setline(98);
      var3 = var1.getlocal(1).__getattr__("_read_pypirc").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(99);
      var3 = var1.getlocal(2).__getattr__("items").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(100);
      var1.getlocal(2).__getattr__("sort").__call__(var2);
      var1.setline(101);
      var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("password"), PyString.fromInterned("secret")}), new PyTuple(new PyObject[]{PyString.fromInterned("realm"), PyString.fromInterned("pypi")}), new PyTuple(new PyObject[]{PyString.fromInterned("repository"), PyString.fromInterned("http://pypi.python.org/pypi")}), new PyTuple(new PyObject[]{PyString.fromInterned("server"), PyString.fromInterned("server-login")}), new PyTuple(new PyObject[]{PyString.fromInterned("username"), PyString.fromInterned("tarek")})});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(104);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_server_empty_registration$8(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyObject var3 = var1.getlocal(0).__getattr__("_cmd").__call__(var2, var1.getlocal(0).__getattr__("dist"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(108);
      var3 = var1.getlocal(1).__getattr__("_get_rc_file").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(109);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(2)).__not__());
      var1.setline(110);
      var1.getlocal(1).__getattr__("_store_pypirc").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tarek"), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(111);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(2)));
      var1.setline(112);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(114);
         PyObject var4 = var1.getlocal(3).__getattr__("read").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(115);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4), var1.getglobal("WANTED"));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(117);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(117);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$9(PyFrame var1, ThreadState var2) {
      var1.setline(120);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("PyPIRCCommandTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_config$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      PyPIRCCommandTestCase$1 = Py.newCode(0, var2, var1, "PyPIRCCommandTestCase", 51, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "command"};
      setUp$2 = Py.newCode(1, var2, var1, "setUp", 56, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      command$3 = Py.newCode(0, var2, var1, "command", 64, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dist"};
      __init__$4 = Py.newCode(2, var2, var1, "__init__", 65, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      initialize_options$5 = Py.newCode(1, var2, var1, "initialize_options", 67, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$6 = Py.newCode(1, var2, var1, "tearDown", 74, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "config", "waited"};
      test_server_registration$7 = Py.newCode(1, var2, var1, "test_server_registration", 79, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "rc", "f", "content"};
      test_server_empty_registration$8 = Py.newCode(1, var2, var1, "test_server_empty_registration", 106, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$9 = Py.newCode(0, var2, var1, "test_suite", 119, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_config$py("distutils/tests/test_config$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_config$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.PyPIRCCommandTestCase$1(var2, var3);
         case 2:
            return this.setUp$2(var2, var3);
         case 3:
            return this.command$3(var2, var3);
         case 4:
            return this.__init__$4(var2, var3);
         case 5:
            return this.initialize_options$5(var2, var3);
         case 6:
            return this.tearDown$6(var2, var3);
         case 7:
            return this.test_server_registration$7(var2, var3);
         case 8:
            return this.test_server_empty_registration$8(var2, var3);
         case 9:
            return this.test_suite$9(var2, var3);
         default:
            return null;
      }
   }
}
