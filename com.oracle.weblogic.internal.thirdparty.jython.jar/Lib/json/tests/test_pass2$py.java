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
@Filename("json/tests/test_pass2.py")
public class test_pass2$py extends PyFunctionTable implements PyRunnable {
   static test_pass2$py self;
   static final PyCode f$0;
   static final PyCode TestPass2$1;
   static final PyCode test_parse$2;
   static final PyCode TestPyPass2$3;
   static final PyCode TestCPass2$4;

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
      var1.setline(5);
      PyString var6 = PyString.fromInterned("\n[[[[[[[[[[[[[[[[[[[\"Not too deep\"]]]]]]]]]]]]]]]]]]]\n");
      var1.setlocal("JSON", var6);
      var3 = null;
      var1.setline(9);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestPass2", var5, TestPass2$1);
      var1.setlocal("TestPass2", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(17);
      var5 = new PyObject[]{var1.getname("TestPass2"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyPass2", var5, TestPyPass2$3);
      var1.setlocal("TestPyPass2", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(18);
      var5 = new PyObject[]{var1.getname("TestPass2"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCPass2", var5, TestCPass2$4);
      var1.setlocal("TestCPass2", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPass2$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(10);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_parse$2, (PyObject)null);
      var1.setlocal("test_parse", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_parse$2(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      PyObject var3 = var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getglobal("JSON"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(13);
      var3 = var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(14);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPyPass2$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      return var1.getf_locals();
   }

   public PyObject TestCPass2$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(18);
      return var1.getf_locals();
   }

   public test_pass2$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestPass2$1 = Py.newCode(0, var2, var1, "TestPass2", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "res", "out"};
      test_parse$2 = Py.newCode(1, var2, var1, "test_parse", 10, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestPyPass2$3 = Py.newCode(0, var2, var1, "TestPyPass2", 17, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCPass2$4 = Py.newCode(0, var2, var1, "TestCPass2", 18, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_pass2$py("json/tests/test_pass2$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_pass2$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestPass2$1(var2, var3);
         case 2:
            return this.test_parse$2(var2, var3);
         case 3:
            return this.TestPyPass2$3(var2, var3);
         case 4:
            return this.TestCPass2$4(var2, var3);
         default:
            return null;
      }
   }
}
