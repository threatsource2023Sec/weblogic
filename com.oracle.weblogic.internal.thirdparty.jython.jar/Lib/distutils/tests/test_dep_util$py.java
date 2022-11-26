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
@Filename("distutils/tests/test_dep_util.py")
public class test_dep_util$py extends PyFunctionTable implements PyRunnable {
   static test_dep_util$py self;
   static final PyCode f$0;
   static final PyCode DepUtilTestCase$1;
   static final PyCode test_newer$2;
   static final PyCode test_newer_pairwise$3;
   static final PyCode test_newer_group$4;
   static final PyCode test_suite$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.dep_util."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.dep_util.");
      var1.setline(2);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("time", var1, -1);
      var1.setlocal("time", var3);
      var3 = null;
      var1.setline(6);
      String[] var5 = new String[]{"newer", "newer_pairwise", "newer_group"};
      PyObject[] var6 = imp.importFrom("distutils.dep_util", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("newer", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("newer_pairwise", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("newer_group", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"DistutilsFileError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsFileError", var4);
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
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("DepUtilTestCase", var6, DepUtilTestCase$1);
      var1.setlocal("DepUtilTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(77);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$5, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(80);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(81);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DepUtilTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_newer$2, (PyObject)null);
      var1.setlocal("test_newer", var4);
      var3 = null;
      var1.setline(32);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_newer_pairwise$3, (PyObject)null);
      var1.setlocal("test_newer_pairwise", var4);
      var3 = null;
      var1.setline(49);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_newer_group$4, (PyObject)null);
      var1.setlocal("test_newer_group", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_newer$2(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(16);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("new"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(17);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getglobal("__file__"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(20);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsFileError"), var1.getglobal("newer"), var1.getlocal(2), var1.getlocal(3));
      var1.setline(24);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(2));
      var1.setline(25);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("newer").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("I_dont_exist")));
      var1.setline(26);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("newer").__call__(var2, var1.getlocal(2), var1.getlocal(3)));
      var1.setline(30);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("newer").__call__(var2, var1.getlocal(3), var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_newer_pairwise$3(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(34);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("sources"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(35);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("targets"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(36);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(2));
      var1.setline(37);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(3));
      var1.setline(38);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("one"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(39);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("two"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(40);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getglobal("__file__"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(41);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("four"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(42);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(4));
      var1.setline(43);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(5));
      var1.setline(44);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(7));
      var1.setline(46);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("newer_pairwise").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})), (PyObject)(new PyList(new PyObject[]{var1.getlocal(6), var1.getlocal(7)}))), (PyObject)(new PyTuple(new PyObject[]{new PyList(new PyObject[]{var1.getlocal(4)}), new PyList(new PyObject[]{var1.getlocal(6)})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_newer_group$4(PyFrame var1, ThreadState var2) {
      var1.setline(50);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(51);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("sources"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(52);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(2));
      var1.setline(53);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("one"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(54);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("two"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(55);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("three"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(56);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getglobal("__file__"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(60);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(3));
      var1.setline(61);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(4));
      var1.setline(62);
      var1.getlocal(0).__getattr__("write_file").__call__(var2, var1.getlocal(5));
      var1.setline(63);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("newer_group").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)})), (PyObject)var1.getlocal(6)));
      var1.setline(64);
      var1.getlocal(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("newer_group").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(6)})), (PyObject)var1.getlocal(5)));
      var1.setline(67);
      var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(3));
      var1.setline(68);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("OSError"), var1.getglobal("newer_group"), new PyList(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(6)}), var1.getlocal(5));
      var1.setline(70);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertFalse");
      PyObject var10002 = var1.getglobal("newer_group");
      PyObject[] var5 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(6)}), var1.getlocal(5), PyString.fromInterned("ignore")};
      String[] var4 = new String[]{"missing"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(73);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var10002 = var1.getglobal("newer_group");
      var5 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(3), var1.getlocal(4), var1.getlocal(6)}), var1.getlocal(5), PyString.fromInterned("newer")};
      var4 = new String[]{"missing"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$5(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("DepUtilTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_dep_util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DepUtilTestCase$1 = Py.newCode(0, var2, var1, "DepUtilTestCase", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "tmpdir", "new_file", "old_file"};
      test_newer$2 = Py.newCode(1, var2, var1, "test_newer", 13, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmpdir", "sources", "targets", "one", "two", "three", "four"};
      test_newer_pairwise$3 = Py.newCode(1, var2, var1, "test_newer_pairwise", 32, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmpdir", "sources", "one", "two", "three", "old_file"};
      test_newer_group$4 = Py.newCode(1, var2, var1, "test_newer_group", 49, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$5 = Py.newCode(0, var2, var1, "test_suite", 77, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_dep_util$py("distutils/tests/test_dep_util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_dep_util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.DepUtilTestCase$1(var2, var3);
         case 2:
            return this.test_newer$2(var2, var3);
         case 3:
            return this.test_newer_pairwise$3(var2, var3);
         case 4:
            return this.test_newer_group$4(var2, var3);
         case 5:
            return this.test_suite$5(var2, var3);
         default:
            return null;
      }
   }
}
