package json;

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
@Filename("json/tests/__init__.py")
public class tests$py extends PyFunctionTable implements PyRunnable {
   static tests$py self;
   static final PyCode f$0;
   static final PyCode PyTest$1;
   static final PyCode CTest$2;
   static final PyCode TestPyTest$3;
   static final PyCode test_pyjson$4;
   static final PyCode TestCTest$5;
   static final PyCode test_cjson$6;
   static final PyCode test_suite$7;
   static final PyCode additional_tests$8;
   static final PyCode main$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("json", var1, -1);
      var1.setlocal("json", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("doctest", var1, -1);
      var1.setlocal("doctest", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(7);
      String[] var6 = new String[]{"test_support"};
      PyObject[] var7 = imp.importFrom("test", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("test_support", var4);
      var4 = null;
      var1.setline(10);
      PyObject var10000 = var1.getname("test_support").__getattr__("import_fresh_module");
      var7 = new PyObject[]{PyString.fromInterned("json"), new PyList(new PyObject[]{PyString.fromInterned("_json")})};
      String[] var5 = new String[]{"fresh"};
      var10000 = var10000.__call__(var2, var7, var5);
      var3 = null;
      var3 = var10000;
      var1.setlocal("cjson", var3);
      var3 = null;
      var1.setline(11);
      var10000 = var1.getname("test_support").__getattr__("import_fresh_module");
      var7 = new PyObject[]{PyString.fromInterned("json"), new PyList(new PyObject[]{PyString.fromInterned("_json")})};
      var5 = new String[]{"blocked"};
      var10000 = var10000.__call__(var2, var7, var5);
      var3 = null;
      var3 = var10000;
      var1.setlocal("pyjson", var3);
      var3 = null;
      var1.setline(14);
      var7 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("PyTest", var7, PyTest$1);
      var1.setlocal("PyTest", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(19);
      var7 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("CTest", var7, CTest$2);
      var4 = var1.getname("unittest").__getattr__("skipUnless").__call__((ThreadState)var2, (PyObject)var1.getname("cjson"), (PyObject)PyString.fromInterned("requires _json")).__call__(var2, var4);
      var1.setlocal("CTest", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(27);
      var7 = new PyObject[]{var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyTest", var7, TestPyTest$3);
      var1.setlocal("TestPyTest", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(36);
      var7 = new PyObject[]{var1.getname("CTest")};
      var4 = Py.makeClass("TestCTest", var7, TestCTest$5);
      var1.setlocal("TestCTest", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(45);
      var3 = var1.getname("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getname("__file__"));
      var1.setlocal("here", var3);
      var3 = null;
      var1.setline(47);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, test_suite$7, (PyObject)null);
      var1.setlocal("test_suite", var8);
      var3 = null;
      var1.setline(58);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, additional_tests$8, (PyObject)null);
      var1.setlocal("additional_tests", var8);
      var3 = null;
      var1.setline(66);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, main$9, (PyObject)null);
      var1.setlocal("main", var8);
      var3 = null;
      var1.setline(71);
      var3 = var1.getname("__name__");
      var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(72);
         var1.getname("sys").__getattr__("path").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getname("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getname("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getname("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getname("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getname("__file__"))))));
         var1.setline(73);
         var1.getname("main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject PyTest$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(15);
      PyObject var3 = var1.getname("pyjson");
      var1.setlocal("json", var3);
      var3 = null;
      var1.setline(16);
      var3 = var1.getname("staticmethod").__call__(var2, var1.getname("pyjson").__getattr__("loads"));
      var1.setlocal("loads", var3);
      var3 = null;
      var1.setline(17);
      var3 = var1.getname("staticmethod").__call__(var2, var1.getname("pyjson").__getattr__("dumps"));
      var1.setlocal("dumps", var3);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject CTest$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(21);
      PyObject var3 = var1.getname("cjson");
      PyObject var10000 = var3._isnot(var1.getname("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(22);
         var3 = var1.getname("cjson");
         var1.setlocal("json", var3);
         var3 = null;
         var1.setline(23);
         var3 = var1.getname("staticmethod").__call__(var2, var1.getname("cjson").__getattr__("loads"));
         var1.setlocal("loads", var3);
         var3 = null;
         var1.setline(24);
         var3 = var1.getname("staticmethod").__call__(var2, var1.getname("cjson").__getattr__("dumps"));
         var1.setlocal("dumps", var3);
         var3 = null;
      }

      return var1.getf_locals();
   }

   public PyObject TestPyTest$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(28);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_pyjson$4, (PyObject)null);
      var1.setlocal("test_pyjson", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_pyjson$4(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("json").__getattr__("scanner").__getattr__("make_scanner").__getattr__("__module__"), (PyObject)PyString.fromInterned("json.scanner"));
      var1.setline(31);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("json").__getattr__("decoder").__getattr__("scanstring").__getattr__("__module__"), (PyObject)PyString.fromInterned("json.decoder"));
      var1.setline(33);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("json").__getattr__("encoder").__getattr__("encode_basestring_ascii").__getattr__("__module__"), (PyObject)PyString.fromInterned("json.encoder"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestCTest$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(37);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_cjson$6, (PyObject)null);
      var1.setlocal("test_cjson", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_cjson$6(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("json").__getattr__("scanner").__getattr__("make_scanner").__getattr__("__module__"), (PyObject)PyString.fromInterned("_json"));
      var1.setline(39);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("json").__getattr__("decoder").__getattr__("scanstring").__getattr__("__module__"), (PyObject)PyString.fromInterned("_json"));
      var1.setline(40);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("json").__getattr__("encoder").__getattr__("c_make_encoder").__getattr__("__module__"), (PyObject)PyString.fromInterned("_json"));
      var1.setline(41);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("json").__getattr__("encoder").__getattr__("encode_basestring_ascii").__getattr__("__module__"), (PyObject)PyString.fromInterned("_json"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$7(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyObject var3 = var1.getglobal("additional_tests").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(49);
      var3 = var1.getglobal("unittest").__getattr__("TestLoader").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(50);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getglobal("here")).__iter__();

      while(true) {
         var1.setline(50);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(56);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(2, var4);
         var1.setline(51);
         PyObject var10000 = var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(2).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".py"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(52);
            PyObject var5 = PyString.fromInterned("json.tests.")._add(var1.getlocal(2).__getslice__((PyObject)null, Py.newInteger(-3), (PyObject)null));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(53);
            var1.getglobal("__import__").__call__(var2, var1.getlocal(3));
            var1.setline(54);
            var5 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(3));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(55);
            var1.getlocal(0).__getattr__("addTests").__call__(var2, var1.getlocal(1).__getattr__("loadTestsFromModule").__call__(var2, var1.getlocal(4)));
         }
      }
   }

   public PyObject additional_tests$8(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(60);
      var3 = (new PyTuple(new PyObject[]{var1.getglobal("json"), var1.getglobal("json").__getattr__("encoder"), var1.getglobal("json").__getattr__("decoder")})).__iter__();

      while(true) {
         var1.setline(60);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(62);
            var1.getlocal(0).__getattr__("addTest").__call__(var2, var1.getglobal("TestPyTest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_pyjson")));
            var1.setline(63);
            var1.getlocal(0).__getattr__("addTest").__call__(var2, var1.getglobal("TestCTest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_cjson")));
            var1.setline(64);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(1, var4);
         var1.setline(61);
         var1.getlocal(0).__getattr__("addTest").__call__(var2, var1.getglobal("doctest").__getattr__("DocTestSuite").__call__(var2, var1.getlocal(1)));
      }
   }

   public PyObject main$9(PyFrame var1, ThreadState var2) {
      var1.setline(67);
      PyObject var3 = var1.getglobal("test_suite").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(68);
      var3 = var1.getglobal("unittest").__getattr__("TextTestRunner").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(69);
      var1.getlocal(1).__getattr__("run").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public tests$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      PyTest$1 = Py.newCode(0, var2, var1, "PyTest", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CTest$2 = Py.newCode(0, var2, var1, "CTest", 19, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestPyTest$3 = Py.newCode(0, var2, var1, "TestPyTest", 27, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_pyjson$4 = Py.newCode(1, var2, var1, "test_pyjson", 28, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestCTest$5 = Py.newCode(0, var2, var1, "TestCTest", 36, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_cjson$6 = Py.newCode(1, var2, var1, "test_cjson", 37, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"suite", "loader", "fn", "modname", "module"};
      test_suite$7 = Py.newCode(0, var2, var1, "test_suite", 47, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"suite", "mod"};
      additional_tests$8 = Py.newCode(0, var2, var1, "additional_tests", 58, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"suite", "runner"};
      main$9 = Py.newCode(0, var2, var1, "main", 66, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new tests$py("json/tests$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(tests$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.PyTest$1(var2, var3);
         case 2:
            return this.CTest$2(var2, var3);
         case 3:
            return this.TestPyTest$3(var2, var3);
         case 4:
            return this.test_pyjson$4(var2, var3);
         case 5:
            return this.TestCTest$5(var2, var3);
         case 6:
            return this.test_cjson$6(var2, var3);
         case 7:
            return this.test_suite$7(var2, var3);
         case 8:
            return this.additional_tests$8(var2, var3);
         case 9:
            return this.main$9(var2, var3);
         default:
            return null;
      }
   }
}
