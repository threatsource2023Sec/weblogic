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
@Filename("json/tests/test_indent.py")
public class test_indent$py extends PyFunctionTable implements PyRunnable {
   static test_indent$py self;
   static final PyCode f$0;
   static final PyCode TestIndent$1;
   static final PyCode test_indent$2;
   static final PyCode test_indent0$3;
   static final PyCode check$4;
   static final PyCode TestPyIndent$5;
   static final PyCode TestCIndent$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("textwrap", var1, -1);
      var1.setlocal("textwrap", var3);
      var3 = null;
      var1.setline(2);
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("StringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(3);
      var5 = new String[]{"PyTest", "CTest"};
      var6 = imp.importFrom("json.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("PyTest", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("CTest", var4);
      var4 = null;
      var1.setline(6);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestIndent", var6, TestIndent$1);
      var1.setlocal("TestIndent", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(59);
      var6 = new PyObject[]{var1.getname("TestIndent"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyIndent", var6, TestPyIndent$5);
      var1.setlocal("TestPyIndent", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(60);
      var6 = new PyObject[]{var1.getname("TestIndent"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCIndent", var6, TestCIndent$6);
      var1.setlocal("TestCIndent", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestIndent$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(7);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_indent$2, (PyObject)null);
      var1.setlocal("test_indent", var4);
      var3 = null;
      var1.setline(43);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_indent0$3, (PyObject)null);
      var1.setlocal("test_indent0", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_indent$2(PyFrame var1, ThreadState var2) {
      var1.setline(8);
      PyList var3 = new PyList(new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("blorpie")}), new PyList(new PyObject[]{PyString.fromInterned("whoops")}), new PyList(Py.EmptyObjects), PyString.fromInterned("d-shtaeou"), PyString.fromInterned("d-nthiouh"), PyString.fromInterned("i-vhbjkhnth"), new PyDictionary(new PyObject[]{PyString.fromInterned("nifty"), Py.newInteger(87)}), new PyDictionary(new PyObject[]{PyString.fromInterned("field"), PyString.fromInterned("yes"), PyString.fromInterned("morefield"), var1.getglobal("False")})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(11);
      PyObject var5 = var1.getglobal("textwrap").__getattr__("dedent").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("        [\n          [\n            \"blorpie\"\n          ],\n          [\n            \"whoops\"\n          ],\n          [],\n          \"d-shtaeou\",\n          \"d-nthiouh\",\n          \"i-vhbjkhnth\",\n          {\n            \"nifty\": 87\n          },\n          {\n            \"field\": \"yes\",\n            \"morefield\": false\n          }\n        ]"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(33);
      var5 = var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(34);
      PyObject var10000 = var1.getlocal(0).__getattr__("dumps");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), Py.newInteger(2), var1.getglobal("True"), new PyTuple(new PyObject[]{PyString.fromInterned(","), PyString.fromInterned(": ")})};
      String[] var4 = new String[]{"indent", "sort_keys", "separators"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(36);
      var5 = var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(3));
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(37);
      var5 = var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(4));
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(39);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5), var1.getlocal(1));
      var1.setline(40);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(6), var1.getlocal(1));
      var1.setline(41);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_indent0$3(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(44);
      PyDictionary var3 = new PyDictionary(new PyObject[]{Py.newInteger(3), Py.newInteger(1)});
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(45);
      PyObject[] var4 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var4;
      PyCode var10004 = check$4;
      var4 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      PyFunction var5 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var4);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(54);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)PyString.fromInterned("{\n\"3\": 1\n}"));
      var1.setline(56);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getglobal("None"), (PyObject)PyString.fromInterned("{\"3\": 1}"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject check$4(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyObject var10000 = var1.getderef(0).__getattr__("dumps");
      PyObject[] var3 = new PyObject[]{var1.getderef(1), var1.getlocal(0)};
      String[] var4 = new String[]{"indent"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(47);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.setline(49);
      var5 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(50);
      var10000 = var1.getderef(0).__getattr__("json").__getattr__("dump");
      var3 = new PyObject[]{var1.getderef(1), var1.getlocal(3), var1.getlocal(0)};
      var4 = new String[]{"indent"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(51);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("getvalue").__call__(var2), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPyIndent$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(59);
      return var1.getf_locals();
   }

   public PyObject TestCIndent$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(60);
      return var1.getf_locals();
   }

   public test_indent$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestIndent$1 = Py.newCode(0, var2, var1, "TestIndent", 6, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "h", "expect", "d1", "d2", "h1", "h2"};
      test_indent$2 = Py.newCode(1, var2, var1, "test_indent", 7, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "check", "h"};
      String[] var10001 = var2;
      test_indent$py var10007 = self;
      var2 = new String[]{"self", "h"};
      test_indent0$3 = Py.newCode(1, var10001, var1, "test_indent0", 43, false, false, var10007, 3, var2, (String[])null, 1, 4097);
      var2 = new String[]{"indent", "expected", "d1", "sio"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self", "h"};
      check$4 = Py.newCode(2, var10001, var1, "check", 45, false, false, var10007, 4, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      TestPyIndent$5 = Py.newCode(0, var2, var1, "TestPyIndent", 59, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCIndent$6 = Py.newCode(0, var2, var1, "TestCIndent", 60, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_indent$py("json/tests/test_indent$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_indent$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestIndent$1(var2, var3);
         case 2:
            return this.test_indent$2(var2, var3);
         case 3:
            return this.test_indent0$3(var2, var3);
         case 4:
            return this.check$4(var2, var3);
         case 5:
            return this.TestPyIndent$5(var2, var3);
         case 6:
            return this.TestCIndent$6(var2, var3);
         default:
            return null;
      }
   }
}
