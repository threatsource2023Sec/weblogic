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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("json/tests/test_default.py")
public class test_default$py extends PyFunctionTable implements PyRunnable {
   static test_default$py self;
   static final PyCode f$0;
   static final PyCode TestDefault$1;
   static final PyCode test_default$2;
   static final PyCode TestPyDefault$3;
   static final PyCode TestCDefault$4;

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
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestDefault", var5, TestDefault$1);
      var1.setlocal("TestDefault", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(11);
      var5 = new PyObject[]{var1.getname("TestDefault"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyDefault", var5, TestPyDefault$3);
      var1.setlocal("TestPyDefault", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(12);
      var5 = new PyObject[]{var1.getname("TestDefault"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCDefault", var5, TestCDefault$4);
      var1.setlocal("TestCDefault", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestDefault$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(5);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_default$2, (PyObject)null);
      var1.setlocal("test_default", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_default$2(PyFrame var1, ThreadState var2) {
      var1.setline(6);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getlocal(0).__getattr__("dumps");
      PyObject[] var3 = new PyObject[]{var1.getglobal("type"), var1.getglobal("repr")};
      String[] var4 = new String[]{"default"};
      var10002 = var10002.__call__(var2, var3, var4);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getglobal("repr").__call__(var2, var1.getglobal("type"))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPyDefault$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(11);
      return var1.getf_locals();
   }

   public PyObject TestCDefault$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(12);
      return var1.getf_locals();
   }

   public test_default$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestDefault$1 = Py.newCode(0, var2, var1, "TestDefault", 4, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      test_default$2 = Py.newCode(1, var2, var1, "test_default", 5, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestPyDefault$3 = Py.newCode(0, var2, var1, "TestPyDefault", 11, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCDefault$4 = Py.newCode(0, var2, var1, "TestCDefault", 12, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_default$py("json/tests/test_default$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_default$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestDefault$1(var2, var3);
         case 2:
            return this.test_default$2(var2, var3);
         case 3:
            return this.TestPyDefault$3(var2, var3);
         case 4:
            return this.TestCDefault$4(var2, var3);
         default:
            return null;
      }
   }
}
