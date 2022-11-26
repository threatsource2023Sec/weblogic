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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_check.py")
public class test_check$py extends PyFunctionTable implements PyRunnable {
   static test_check$py self;
   static final PyCode f$0;
   static final PyCode CheckTestCase$1;
   static final PyCode _run$2;
   static final PyCode test_check_metadata$3;
   static final PyCode test_check_document$4;
   static final PyCode test_check_restructuredtext$5;
   static final PyCode test_check_all$6;
   static final PyCode test_suite$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.check."));
      var1.setline(2);
      PyString.fromInterned("Tests for distutils.command.check.");
      var1.setline(3);
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
      var5 = new String[]{"check", "HAS_DOCUTILS"};
      var6 = imp.importFrom("distutils.command.check", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("check", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("HAS_DOCUTILS", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"DistutilsSetupError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsSetupError", var4);
      var4 = null;
      var1.setline(10);
      var6 = new PyObject[]{var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("support").__getattr__("TempdirManager"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("CheckTestCase", var6, CheckTestCase$1);
      var1.setlocal("CheckTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(105);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$7, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(108);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(109);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject CheckTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(14);
      PyObject[] var3 = new PyObject[]{var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _run$2, (PyObject)null);
      var1.setlocal("_run", var4);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_check_metadata$3, (PyObject)null);
      var1.setlocal("test_check_metadata", var4);
      var3 = null;
      var1.setline(59);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_check_document$4, (PyObject)null);
      var1.setlocal("test_check_document", var4);
      var3 = null;
      var1.setline(75);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_check_restructuredtext$5, (PyObject)null);
      var1.setlocal("test_check_restructuredtext", var4);
      var3 = null;
      var1.setline(98);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_check_all$6, (PyObject)null);
      var1.setlocal("test_check_all", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _run$2(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(16);
         PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(1, var7);
         var3 = null;
      }

      var1.setline(17);
      var10000 = var1.getlocal(0).__getattr__("create_dist");
      PyObject[] var8 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var8, var4, (PyObject)null, var1.getlocal(1));
      var3 = null;
      var3 = var10000;
      PyObject[] var9 = Py.unpackSequence(var3, 2);
      PyObject var5 = var9[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var9[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(18);
      var3 = var1.getglobal("check").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(19);
      var1.getlocal(5).__getattr__("initialize_options").__call__(var2);
      var1.setline(20);
      var3 = var1.getlocal(2).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(20);
         PyObject var10 = var3.__iternext__();
         if (var10 == null) {
            var1.setline(22);
            var1.getlocal(5).__getattr__("ensure_finalized").__call__(var2);
            var1.setline(23);
            var1.getlocal(5).__getattr__("run").__call__(var2);
            var1.setline(24);
            var3 = var1.getlocal(5);
            var1.f_lasti = -1;
            return var3;
         }

         PyObject[] var11 = Py.unpackSequence(var10, 2);
         PyObject var6 = var11[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var11[1];
         var1.setlocal(7, var6);
         var6 = null;
         var1.setline(21);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(7));
      }
   }

   public PyObject test_check_metadata$3(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getlocal(0).__getattr__("_run").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(31);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("_warnings"), (PyObject)Py.newInteger(2));
      var1.setline(36);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("url"), PyString.fromInterned("xxx"), PyString.fromInterned("author"), PyString.fromInterned("xxx"), PyString.fromInterned("author_email"), PyString.fromInterned("xxx"), PyString.fromInterned("name"), PyString.fromInterned("xxx"), PyString.fromInterned("version"), PyString.fromInterned("xxx")});
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(39);
      var3 = var1.getlocal(0).__getattr__("_run").__call__(var2, var1.getlocal(2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(40);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("_warnings"), (PyObject)Py.newInteger(0));
      var1.setline(44);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertRaises");
      PyObject[] var6 = new PyObject[]{var1.getglobal("DistutilsSetupError"), var1.getlocal(0).__getattr__("_run"), new PyDictionary(Py.EmptyObjects)};
      String[] var4 = new String[0];
      var10000._callextra(var6, var4, (PyObject)null, new PyDictionary(new PyObject[]{PyString.fromInterned("strict"), Py.newInteger(1)}));
      var3 = null;
      var1.setline(47);
      var10000 = var1.getlocal(0).__getattr__("_run");
      var6 = new PyObject[]{var1.getlocal(2), Py.newInteger(1)};
      var4 = new String[]{"strict"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(48);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("_warnings"), (PyObject)Py.newInteger(0));
      var1.setline(51);
      var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("url"), PyUnicode.fromInterned("xxx"), PyString.fromInterned("author"), PyUnicode.fromInterned("Éric"), PyString.fromInterned("author_email"), PyUnicode.fromInterned("xxx"), PyUnicode.fromInterned("name"), PyString.fromInterned("xxx"), PyString.fromInterned("version"), PyUnicode.fromInterned("xxx"), PyString.fromInterned("description"), PyUnicode.fromInterned("Something about esszet ß"), PyString.fromInterned("long_description"), PyUnicode.fromInterned("More things about esszet ß")});
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(56);
      var3 = var1.getlocal(0).__getattr__("_run").__call__(var2, var1.getlocal(2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(57);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("_warnings"), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_check_document$4(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      if (var1.getglobal("HAS_DOCUTILS").__not__().__nonzero__()) {
         var1.setline(61);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(62);
         PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(63);
         var3 = var1.getglobal("check").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(66);
         PyString var6 = PyString.fromInterned("title\n===\n\ntest");
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(67);
         var3 = var1.getlocal(3).__getattr__("_check_rst_data").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(68);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5)), (PyObject)Py.newInteger(1));
         var1.setline(71);
         var6 = PyString.fromInterned("title\n=====\n\ntest");
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(72);
         var3 = var1.getlocal(3).__getattr__("_check_rst_data").__call__(var2, var1.getlocal(6));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(73);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5)), (PyObject)Py.newInteger(0));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_check_restructuredtext$5(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      if (var1.getglobal("HAS_DOCUTILS").__not__().__nonzero__()) {
         var1.setline(77);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(79);
         PyString var3 = PyString.fromInterned("title\n===\n\ntest");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(80);
         PyObject var10000 = var1.getlocal(0).__getattr__("create_dist");
         PyObject[] var6 = new PyObject[]{var1.getlocal(1)};
         String[] var4 = new String[]{"long_description"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         PyObject var7 = var10000;
         PyObject[] var8 = Py.unpackSequence(var7, 2);
         PyObject var5 = var8[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var8[1];
         var1.setlocal(3, var5);
         var5 = null;
         var3 = null;
         var1.setline(81);
         var7 = var1.getglobal("check").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(82);
         var1.getlocal(4).__getattr__("check_restructuredtext").__call__(var2);
         var1.setline(83);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("_warnings"), (PyObject)Py.newInteger(1));
         var1.setline(86);
         PyDictionary var9 = new PyDictionary(new PyObject[]{PyString.fromInterned("url"), PyString.fromInterned("xxx"), PyString.fromInterned("author"), PyString.fromInterned("xxx"), PyString.fromInterned("author_email"), PyString.fromInterned("xxx"), PyString.fromInterned("name"), PyString.fromInterned("xxx"), PyString.fromInterned("version"), PyString.fromInterned("xxx"), PyString.fromInterned("long_description"), var1.getlocal(1)});
         var1.setlocal(5, var9);
         var3 = null;
         var1.setline(90);
         var10000 = var1.getlocal(0).__getattr__("assertRaises");
         var6 = new PyObject[]{var1.getglobal("DistutilsSetupError"), var1.getlocal(0).__getattr__("_run"), var1.getlocal(5)};
         var4 = new String[0];
         var10000._callextra(var6, var4, (PyObject)null, new PyDictionary(new PyObject[]{PyString.fromInterned("strict"), Py.newInteger(1), PyString.fromInterned("restructuredtext"), Py.newInteger(1)}));
         var3 = null;
         var1.setline(94);
         PyUnicode var10 = PyUnicode.fromInterned("title\n=====\n\ntest ß");
         var1.getlocal(5).__setitem__((PyObject)PyString.fromInterned("long_description"), var10);
         var3 = null;
         var1.setline(95);
         var10000 = var1.getlocal(0).__getattr__("_run");
         var6 = new PyObject[]{var1.getlocal(5), Py.newInteger(1), Py.newInteger(1)};
         var4 = new String[]{"strict", "restructuredtext"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var7 = var10000;
         var1.setlocal(4, var7);
         var3 = null;
         var1.setline(96);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("_warnings"), (PyObject)Py.newInteger(0));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_check_all$6(PyFrame var1, ThreadState var2) {
      var1.setline(100);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("url"), PyString.fromInterned("xxx"), PyString.fromInterned("author"), PyString.fromInterned("xxx")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(101);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertRaises");
      PyObject[] var5 = new PyObject[]{var1.getglobal("DistutilsSetupError"), var1.getlocal(0).__getattr__("_run"), new PyDictionary(Py.EmptyObjects)};
      String[] var4 = new String[0];
      var10000._callextra(var5, var4, (PyObject)null, new PyDictionary(new PyObject[]{PyString.fromInterned("strict"), Py.newInteger(1), PyString.fromInterned("restructuredtext"), Py.newInteger(1)}));
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$7(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("CheckTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_check$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CheckTestCase$1 = Py.newCode(0, var2, var1, "CheckTestCase", 10, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "metadata", "options", "pkg_info", "dist", "cmd", "name", "value"};
      _run$2 = Py.newCode(3, var2, var1, "_run", 14, false, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd", "metadata"};
      test_check_metadata$3 = Py.newCode(1, var2, var1, "test_check_metadata", 26, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_info", "dist", "cmd", "broken_rest", "msgs", "rest"};
      test_check_document$4 = Py.newCode(1, var2, var1, "test_check_document", 59, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "broken_rest", "pkg_info", "dist", "cmd", "metadata"};
      test_check_restructuredtext$5 = Py.newCode(1, var2, var1, "test_check_restructuredtext", 75, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "metadata"};
      test_check_all$6 = Py.newCode(1, var2, var1, "test_check_all", 98, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$7 = Py.newCode(0, var2, var1, "test_suite", 105, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_check$py("distutils/tests/test_check$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_check$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.CheckTestCase$1(var2, var3);
         case 2:
            return this._run$2(var2, var3);
         case 3:
            return this.test_check_metadata$3(var2, var3);
         case 4:
            return this.test_check_document$4(var2, var3);
         case 5:
            return this.test_check_restructuredtext$5(var2, var3);
         case 6:
            return this.test_check_all$6(var2, var3);
         case 7:
            return this.test_suite$7(var2, var3);
         default:
            return null;
      }
   }
}
