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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("json/tests/test_separators.py")
public class test_separators$py extends PyFunctionTable implements PyRunnable {
   static test_separators$py self;
   static final PyCode f$0;
   static final PyCode TestSeparators$1;
   static final PyCode test_separators$2;
   static final PyCode TestPySeparators$3;
   static final PyCode TestCSeparators$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("textwrap", var1, -1);
      var1.setlocal("textwrap", var3);
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
      var4 = Py.makeClass("TestSeparators", var6, TestSeparators$1);
      var1.setlocal("TestSeparators", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(43);
      var6 = new PyObject[]{var1.getname("TestSeparators"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPySeparators", var6, TestPySeparators$3);
      var1.setlocal("TestPySeparators", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(44);
      var6 = new PyObject[]{var1.getname("TestSeparators"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCSeparators", var6, TestCSeparators$4);
      var1.setlocal("TestCSeparators", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestSeparators$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(6);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_separators$2, (PyObject)null);
      var1.setlocal("test_separators", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_separators$2(PyFrame var1, ThreadState var2) {
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("blorpie")}), new PyList(new PyObject[]{PyString.fromInterned("whoops")}), new PyList(Py.EmptyObjects), PyString.fromInterned("d-shtaeou"), PyString.fromInterned("d-nthiouh"), PyString.fromInterned("i-vhbjkhnth"), new PyDictionary(new PyObject[]{PyString.fromInterned("nifty"), Py.newInteger(87)}), new PyDictionary(new PyObject[]{PyString.fromInterned("field"), PyString.fromInterned("yes"), PyString.fromInterned("morefield"), var1.getglobal("False")})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(10);
      PyObject var5 = var1.getglobal("textwrap").__getattr__("dedent").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("        [\n          [\n            \"blorpie\"\n          ] ,\n          [\n            \"whoops\"\n          ] ,\n          [] ,\n          \"d-shtaeou\" ,\n          \"d-nthiouh\" ,\n          \"i-vhbjkhnth\" ,\n          {\n            \"nifty\" : 87\n          } ,\n          {\n            \"field\" : \"yes\" ,\n            \"morefield\" : false\n          }\n        ]"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(32);
      var5 = var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(33);
      PyObject var10000 = var1.getlocal(0).__getattr__("dumps");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), Py.newInteger(2), var1.getglobal("True"), new PyTuple(new PyObject[]{PyString.fromInterned(" ,"), PyString.fromInterned(" : ")})};
      String[] var4 = new String[]{"indent", "sort_keys", "separators"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(35);
      var5 = var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(3));
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(36);
      var5 = var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(4));
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(38);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5), var1.getlocal(1));
      var1.setline(39);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(6), var1.getlocal(1));
      var1.setline(40);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPySeparators$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(43);
      return var1.getf_locals();
   }

   public PyObject TestCSeparators$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(44);
      return var1.getf_locals();
   }

   public test_separators$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestSeparators$1 = Py.newCode(0, var2, var1, "TestSeparators", 5, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "h", "expect", "d1", "d2", "h1", "h2"};
      test_separators$2 = Py.newCode(1, var2, var1, "test_separators", 6, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestPySeparators$3 = Py.newCode(0, var2, var1, "TestPySeparators", 43, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCSeparators$4 = Py.newCode(0, var2, var1, "TestCSeparators", 44, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_separators$py("json/tests/test_separators$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_separators$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestSeparators$1(var2, var3);
         case 2:
            return this.test_separators$2(var2, var3);
         case 3:
            return this.TestPySeparators$3(var2, var3);
         case 4:
            return this.TestCSeparators$4(var2, var3);
         default:
            return null;
      }
   }
}
