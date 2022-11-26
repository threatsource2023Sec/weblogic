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
@Filename("json/tests/test_float.py")
public class test_float$py extends PyFunctionTable implements PyRunnable {
   static test_float$py self;
   static final PyCode f$0;
   static final PyCode TestFloat$1;
   static final PyCode test_floats$2;
   static final PyCode test_ints$3;
   static final PyCode test_out_of_range$4;
   static final PyCode test_allow_nan$5;
   static final PyCode TestPyFloat$6;
   static final PyCode TestCFloat$7;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("math", var1, -1);
      var1.setlocal("math", var3);
      var3 = null;
      var1.setline(2);
      String[] var5 = new String[]{"PyTest", "CTest"};
      PyObject[] var6 = imp.importFrom("json.tests", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("PyTest", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("CTest", var4);
      var4 = null;
      var1.setline(5);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestFloat", var6, TestFloat$1);
      var1.setlocal("TestFloat", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(36);
      var6 = new PyObject[]{var1.getname("TestFloat"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyFloat", var6, TestPyFloat$6);
      var1.setlocal("TestPyFloat", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(37);
      var6 = new PyObject[]{var1.getname("TestFloat"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCFloat", var6, TestCFloat$7);
      var1.setlocal("TestCFloat", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestFloat$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(6);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_floats$2, (PyObject)null);
      var1.setlocal("test_floats", var4);
      var3 = null;
      var1.setline(13);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_ints$3, (PyObject)null);
      var1.setlocal("test_ints", var4);
      var3 = null;
      var1.setline(20);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_out_of_range$4, (PyObject)null);
      var1.setlocal("test_out_of_range", var4);
      var3 = null;
      var1.setline(24);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_allow_nan$5, (PyObject)null);
      var1.setlocal("test_allow_nan", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_floats$2(PyFrame var1, ThreadState var2) {
      var1.setline(7);
      PyObject var3 = (new PyList(new PyObject[]{Py.newFloat(1.617161771765E9), var1.getglobal("math").__getattr__("pi"), var1.getglobal("math").__getattr__("pi")._pow(Py.newInteger(100)), var1.getglobal("math").__getattr__("pi")._pow(Py.newInteger(-100)), Py.newFloat(3.1)})).__iter__();

      while(true) {
         var1.setline(7);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(9);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("float").__call__(var2, var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1))), var1.getlocal(1));
         var1.setline(10);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1))), var1.getlocal(1));
         var1.setline(11);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getglobal("unicode").__call__(var2, var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1)))), var1.getlocal(1));
      }
   }

   public PyObject test_ints$3(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      PyObject var3 = (new PyList(new PyObject[]{Py.newInteger(1), Py.newLong("1"), Py.newInteger(1)._lshift(Py.newInteger(32)), Py.newInteger(1)._lshift(Py.newInteger(64))})).__iter__();

      while(true) {
         var1.setline(14);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(15);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1)), var1.getglobal("str").__call__(var2, var1.getlocal(1)));
         var1.setline(16);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1))), var1.getlocal(1));
         var1.setline(17);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1))), var1.getlocal(1));
         var1.setline(18);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getglobal("unicode").__call__(var2, var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1)))), var1.getlocal(1));
      }
   }

   public PyObject test_out_of_range$4(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("loads").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[23456789012E666]")), (PyObject)(new PyList(new PyObject[]{var1.getglobal("float").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("inf"))})));
      var1.setline(22);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("loads").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[-23456789012E666]")), (PyObject)(new PyList(new PyObject[]{var1.getglobal("float").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-inf"))})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_allow_nan$5(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = (new PyTuple(new PyObject[]{var1.getglobal("float").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("inf")), var1.getglobal("float").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-inf")), var1.getglobal("float").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("nan"))})).__iter__();

      while(true) {
         var1.setline(25);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(26);
         PyObject var5 = var1.getlocal(0).__getattr__("dumps").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)})));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(27);
         var5 = var1.getlocal(1);
         PyObject var10000 = var5._eq(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(28);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(2)), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)})));
         } else {
            var1.setline(30);
            var5 = var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(31);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)Py.newInteger(1));
            var1.setline(32);
            var1.getlocal(0).__getattr__("assertNotEqual").__call__(var2, var1.getlocal(3).__getitem__(Py.newInteger(0)), var1.getlocal(3).__getitem__(Py.newInteger(0)));
         }

         var1.setline(33);
         var10000 = var1.getlocal(0).__getattr__("assertRaises");
         PyObject[] var7 = new PyObject[]{var1.getglobal("ValueError"), var1.getlocal(0).__getattr__("dumps"), new PyList(new PyObject[]{var1.getlocal(1)}), var1.getglobal("False")};
         String[] var6 = new String[]{"allow_nan"};
         var10000.__call__(var2, var7, var6);
         var5 = null;
      }
   }

   public PyObject TestPyFloat$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(36);
      return var1.getf_locals();
   }

   public PyObject TestCFloat$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(37);
      return var1.getf_locals();
   }

   public test_float$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestFloat$1 = Py.newCode(0, var2, var1, "TestFloat", 5, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "num"};
      test_floats$2 = Py.newCode(1, var2, var1, "test_floats", 6, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "num"};
      test_ints$3 = Py.newCode(1, var2, var1, "test_ints", 13, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_out_of_range$4 = Py.newCode(1, var2, var1, "test_out_of_range", 20, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "val", "out", "res"};
      test_allow_nan$5 = Py.newCode(1, var2, var1, "test_allow_nan", 24, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestPyFloat$6 = Py.newCode(0, var2, var1, "TestPyFloat", 36, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCFloat$7 = Py.newCode(0, var2, var1, "TestCFloat", 37, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_float$py("json/tests/test_float$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_float$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestFloat$1(var2, var3);
         case 2:
            return this.test_floats$2(var2, var3);
         case 3:
            return this.test_ints$3(var2, var3);
         case 4:
            return this.test_out_of_range$4(var2, var3);
         case 5:
            return this.test_allow_nan$5(var2, var3);
         case 6:
            return this.TestPyFloat$6(var2, var3);
         case 7:
            return this.TestCFloat$7(var2, var3);
         default:
            return null;
      }
   }
}
