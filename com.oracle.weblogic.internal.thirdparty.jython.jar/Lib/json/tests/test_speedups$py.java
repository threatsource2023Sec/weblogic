package json.tests;

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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("json/tests/test_speedups.py")
public class test_speedups$py extends PyFunctionTable implements PyRunnable {
   static test_speedups$py self;
   static final PyCode f$0;
   static final PyCode TestSpeedups$1;
   static final PyCode test_scanstring$2;
   static final PyCode test_encode_basestring_ascii$3;
   static final PyCode TestDecode$4;
   static final PyCode test_make_scanner$5;
   static final PyCode test_make_encoder$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      String[] var3 = new String[]{"CTest"};
      PyObject[] var5 = imp.importFrom("json.tests", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("CTest", var4);
      var4 = null;
      var1.setline(4);
      var5 = new PyObject[]{var1.getname("CTest")};
      var4 = Py.makeClass("TestSpeedups", var5, TestSpeedups$1);
      var1.setlocal("TestSpeedups", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(15);
      var5 = new PyObject[]{var1.getname("CTest")};
      var4 = Py.makeClass("TestDecode", var5, TestDecode$4);
      var1.setlocal("TestDecode", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestSpeedups$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(5);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_scanstring$2, (PyObject)null);
      var1.setlocal("test_scanstring", var4);
      var3 = null;
      var1.setline(9);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encode_basestring_ascii$3, (PyObject)null);
      var1.setlocal("test_encode_basestring_ascii", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_scanstring$2(PyFrame var1, ThreadState var2) {
      var1.setline(6);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("json").__getattr__("decoder").__getattr__("scanstring").__getattr__("__module__"), (PyObject)PyString.fromInterned("_json"));
      var1.setline(7);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(0).__getattr__("json").__getattr__("decoder").__getattr__("scanstring"), var1.getlocal(0).__getattr__("json").__getattr__("decoder").__getattr__("c_scanstring"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encode_basestring_ascii$3(PyFrame var1, ThreadState var2) {
      var1.setline(10);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("json").__getattr__("encoder").__getattr__("encode_basestring_ascii").__getattr__("__module__"), (PyObject)PyString.fromInterned("_json"));
      var1.setline(12);
      var1.getlocal(0).__getattr__("assertIs").__call__(var2, var1.getlocal(0).__getattr__("json").__getattr__("encoder").__getattr__("encode_basestring_ascii"), var1.getlocal(0).__getattr__("json").__getattr__("encoder").__getattr__("c_encode_basestring_ascii"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestDecode$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_make_scanner$5, (PyObject)null);
      var1.setlocal("test_make_scanner", var4);
      var3 = null;
      var1.setline(19);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_make_encoder$6, (PyObject)null);
      var1.setlocal("test_make_encoder", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_make_scanner$5(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("AttributeError"), (PyObject)var1.getlocal(0).__getattr__("json").__getattr__("scanner").__getattr__("c_make_scanner"), (PyObject)Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_make_encoder$6(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertRaises");
      PyObject[] var3 = new PyObject[]{var1.getglobal("TypeError"), var1.getlocal(0).__getattr__("json").__getattr__("encoder").__getattr__("c_make_encoder"), var1.getglobal("None"), PyString.fromInterned("Í}=N\u0012Lùy×Rº\u0082ò'J} Êu"), var1.getglobal("None")};
      var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public test_speedups$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestSpeedups$1 = Py.newCode(0, var2, var1, "TestSpeedups", 4, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_scanstring$2 = Py.newCode(1, var2, var1, "test_scanstring", 5, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_encode_basestring_ascii$3 = Py.newCode(1, var2, var1, "test_encode_basestring_ascii", 9, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestDecode$4 = Py.newCode(0, var2, var1, "TestDecode", 15, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_make_scanner$5 = Py.newCode(1, var2, var1, "test_make_scanner", 16, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_make_encoder$6 = Py.newCode(1, var2, var1, "test_make_encoder", 19, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_speedups$py("json/tests/test_speedups$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_speedups$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestSpeedups$1(var2, var3);
         case 2:
            return this.test_scanstring$2(var2, var3);
         case 3:
            return this.test_encode_basestring_ascii$3(var2, var3);
         case 4:
            return this.TestDecode$4(var2, var3);
         case 5:
            return this.test_make_scanner$5(var2, var3);
         case 6:
            return this.test_make_encoder$6(var2, var3);
         default:
            return null;
      }
   }
}
