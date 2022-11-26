package json.tests;

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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("json/tests/test_check_circular.py")
public class test_check_circular$py extends PyFunctionTable implements PyRunnable {
   static test_check_circular$py self;
   static final PyCode f$0;
   static final PyCode default_iterable$1;
   static final PyCode TestCheckCircular$2;
   static final PyCode test_circular_dict$3;
   static final PyCode test_circular_list$4;
   static final PyCode test_circular_composite$5;
   static final PyCode test_circular_default$6;
   static final PyCode test_circular_off_default$7;
   static final PyCode TestPyCheckCircular$8;
   static final PyCode TestCCheckCircular$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      String[] var3 = new String[]{"PyTest", "CTest"};
      PyObject[] var5 = imp.importFrom("json.tests", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("PyTest", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("CTest", var4);
      var4 = null;
      var1.setline(4);
      var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, default_iterable$1, (PyObject)null);
      var1.setlocal("default_iterable", var6);
      var3 = null;
      var1.setline(7);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestCheckCircular", var5, TestCheckCircular$2);
      var1.setlocal("TestCheckCircular", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(33);
      var5 = new PyObject[]{var1.getname("TestCheckCircular"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyCheckCircular", var5, TestPyCheckCircular$8);
      var1.setlocal("TestPyCheckCircular", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(34);
      var5 = new PyObject[]{var1.getname("TestCheckCircular"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCCheckCircular", var5, TestCCheckCircular$9);
      var1.setlocal("TestCCheckCircular", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject default_iterable$1(PyFrame var1, ThreadState var2) {
      var1.setline(5);
      PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject TestCheckCircular$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(8);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_circular_dict$3, (PyObject)null);
      var1.setlocal("test_circular_dict", var4);
      var3 = null;
      var1.setline(13);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_circular_list$4, (PyObject)null);
      var1.setlocal("test_circular_list", var4);
      var3 = null;
      var1.setline(18);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_circular_composite$5, (PyObject)null);
      var1.setlocal("test_circular_composite", var4);
      var3 = null;
      var1.setline(24);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_circular_default$6, (PyObject)null);
      var1.setlocal("test_circular_default", var4);
      var3 = null;
      var1.setline(28);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_circular_off_default$7, (PyObject)null);
      var1.setlocal("test_circular_off_default", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_circular_dict$3(PyFrame var1, ThreadState var2) {
      var1.setline(9);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(10);
      PyObject var4 = var1.getlocal(1);
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("a"), var4);
      var3 = null;
      var1.setline(11);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("ValueError"), var1.getlocal(0).__getattr__("dumps"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_circular_list$4(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(15);
      var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(16);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("ValueError"), var1.getlocal(0).__getattr__("dumps"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_circular_composite$5(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(20);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(1).__setitem__((PyObject)PyString.fromInterned("a"), var4);
      var3 = null;
      var1.setline(21);
      var1.getlocal(1).__getitem__(PyString.fromInterned("a")).__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(22);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("ValueError"), var1.getlocal(0).__getattr__("dumps"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_circular_default$6(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var10000 = var1.getlocal(0).__getattr__("dumps");
      PyObject[] var3 = new PyObject[]{new PyList(new PyObject[]{var1.getglobal("set").__call__(var2)}), var1.getglobal("default_iterable")};
      String[] var4 = new String[]{"default"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(26);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("TypeError"), (PyObject)var1.getlocal(0).__getattr__("dumps"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("set").__call__(var2)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_circular_off_default$7(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var10000 = var1.getlocal(0).__getattr__("dumps");
      PyObject[] var3 = new PyObject[]{new PyList(new PyObject[]{var1.getglobal("set").__call__(var2)}), var1.getglobal("default_iterable"), var1.getglobal("False")};
      String[] var4 = new String[]{"default", "check_circular"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(30);
      var10000 = var1.getlocal(0).__getattr__("assertRaises");
      var3 = new PyObject[]{var1.getglobal("TypeError"), var1.getlocal(0).__getattr__("dumps"), new PyList(new PyObject[]{var1.getglobal("set").__call__(var2)}), var1.getglobal("False")};
      var4 = new String[]{"check_circular"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPyCheckCircular$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(33);
      return var1.getf_locals();
   }

   public PyObject TestCCheckCircular$9(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(34);
      return var1.getf_locals();
   }

   public test_check_circular$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"obj"};
      default_iterable$1 = Py.newCode(1, var2, var1, "default_iterable", 4, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestCheckCircular$2 = Py.newCode(0, var2, var1, "TestCheckCircular", 7, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dct"};
      test_circular_dict$3 = Py.newCode(1, var2, var1, "test_circular_dict", 8, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lst"};
      test_circular_list$4 = Py.newCode(1, var2, var1, "test_circular_list", 13, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dct2"};
      test_circular_composite$5 = Py.newCode(1, var2, var1, "test_circular_composite", 18, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_circular_default$6 = Py.newCode(1, var2, var1, "test_circular_default", 24, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_circular_off_default$7 = Py.newCode(1, var2, var1, "test_circular_off_default", 28, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestPyCheckCircular$8 = Py.newCode(0, var2, var1, "TestPyCheckCircular", 33, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCCheckCircular$9 = Py.newCode(0, var2, var1, "TestCCheckCircular", 34, false, false, self, 9, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_check_circular$py("json/tests/test_check_circular$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_check_circular$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.default_iterable$1(var2, var3);
         case 2:
            return this.TestCheckCircular$2(var2, var3);
         case 3:
            return this.test_circular_dict$3(var2, var3);
         case 4:
            return this.test_circular_list$4(var2, var3);
         case 5:
            return this.test_circular_composite$5(var2, var3);
         case 6:
            return this.test_circular_default$6(var2, var3);
         case 7:
            return this.test_circular_off_default$7(var2, var3);
         case 8:
            return this.TestPyCheckCircular$8(var2, var3);
         case 9:
            return this.TestCCheckCircular$9(var2, var3);
         default:
            return null;
      }
   }
}
