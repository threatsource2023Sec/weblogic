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
@Filename("json/tests/test_dump.py")
public class test_dump$py extends PyFunctionTable implements PyRunnable {
   static test_dump$py self;
   static final PyCode f$0;
   static final PyCode TestDump$1;
   static final PyCode test_dump$2;
   static final PyCode test_dumps$3;
   static final PyCode test_encode_truefalse$4;
   static final PyCode test_encode_mutated$5;
   static final PyCode crasher$6;
   static final PyCode TestPyDump$7;
   static final PyCode TestCDump$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      String[] var3 = new String[]{"StringIO"};
      PyObject[] var5 = imp.importFrom("cStringIO", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(2);
      var3 = new String[]{"PyTest", "CTest"};
      var5 = imp.importFrom("json.tests", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("PyTest", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("CTest", var4);
      var4 = null;
      var1.setline(5);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestDump", var5, TestDump$1);
      var1.setlocal("TestDump", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(31);
      var5 = new PyObject[]{var1.getname("TestDump"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyDump", var5, TestPyDump$7);
      var1.setlocal("TestPyDump", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(32);
      var5 = new PyObject[]{var1.getname("TestDump"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCDump", var5, TestCDump$8);
      var1.setlocal("TestCDump", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestDump$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(6);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_dump$2, (PyObject)null);
      var1.setlocal("test_dump", var4);
      var3 = null;
      var1.setline(11);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_dumps$3, (PyObject)null);
      var1.setlocal("test_dumps", var4);
      var3 = null;
      var1.setline(14);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encode_truefalse$4, (PyObject)null);
      var1.setlocal("test_encode_truefalse", var4);
      var3 = null;
      var1.setline(23);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encode_mutated$5, (PyObject)null);
      var1.setlocal("test_encode_mutated", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_dump$2(PyFrame var1, ThreadState var2) {
      var1.setline(7);
      PyObject var3 = var1.getglobal("StringIO").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(8);
      var1.getlocal(0).__getattr__("json").__getattr__("dump").__call__((ThreadState)var2, (PyObject)(new PyDictionary(Py.EmptyObjects)), (PyObject)var1.getlocal(1));
      var1.setline(9);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("getvalue").__call__(var2), (PyObject)PyString.fromInterned("{}"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dumps$3(PyFrame var1, ThreadState var2) {
      var1.setline(12);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("dumps").__call__((ThreadState)var2, (PyObject)(new PyDictionary(Py.EmptyObjects))), (PyObject)PyString.fromInterned("{}"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encode_truefalse$4(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getlocal(0).__getattr__("dumps");
      PyObject[] var3 = new PyObject[]{new PyDictionary(new PyObject[]{var1.getglobal("True"), var1.getglobal("False"), var1.getglobal("False"), var1.getglobal("True")}), var1.getglobal("True")};
      String[] var4 = new String[]{"sort_keys"};
      var10002 = var10002.__call__(var2, var3, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("{\"false\": true, \"true\": false}"));
      var1.setline(18);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      var10002 = var1.getlocal(0).__getattr__("dumps");
      var3 = new PyObject[]{new PyDictionary(new PyObject[]{Py.newInteger(2), Py.newFloat(3.0), Py.newFloat(4.0), Py.newLong("5"), var1.getglobal("False"), Py.newInteger(1), Py.newLong("6"), var1.getglobal("True")}), var1.getglobal("True")};
      var4 = new String[]{"sort_keys"};
      var10002 = var10002.__call__(var2, var3, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("{\"false\": 1, \"2\": 3.0, \"4.0\": 5, \"6\": true}"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encode_mutated$5(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      PyObject var3 = (new PyList(new PyObject[]{var1.getglobal("object").__call__(var2)}))._mul(Py.newInteger(10));
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(25);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = crasher$6;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(27);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      var10002 = var1.getlocal(0).__getattr__("dumps");
      var5 = new PyObject[]{var1.getderef(0), var1.getlocal(1)};
      String[] var4 = new String[]{"default"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("[null, null, null, null, null]"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject crasher$6(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      var1.getderef(0).__delitem__((PyObject)Py.newInteger(-1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPyDump$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(31);
      return var1.getf_locals();
   }

   public PyObject TestCDump$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(32);
      return var1.getf_locals();
   }

   public test_dump$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestDump$1 = Py.newCode(0, var2, var1, "TestDump", 5, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "sio"};
      test_dump$2 = Py.newCode(1, var2, var1, "test_dump", 6, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_dumps$3 = Py.newCode(1, var2, var1, "test_dumps", 11, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_encode_truefalse$4 = Py.newCode(1, var2, var1, "test_encode_truefalse", 14, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "crasher", "a"};
      String[] var10001 = var2;
      test_dump$py var10007 = self;
      var2 = new String[]{"a"};
      test_encode_mutated$5 = Py.newCode(1, var10001, var1, "test_encode_mutated", 23, false, false, var10007, 5, var2, (String[])null, 1, 4097);
      var2 = new String[]{"obj"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"a"};
      crasher$6 = Py.newCode(1, var10001, var1, "crasher", 25, false, false, var10007, 6, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      TestPyDump$7 = Py.newCode(0, var2, var1, "TestPyDump", 31, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCDump$8 = Py.newCode(0, var2, var1, "TestCDump", 32, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_dump$py("json/tests/test_dump$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_dump$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestDump$1(var2, var3);
         case 2:
            return this.test_dump$2(var2, var3);
         case 3:
            return this.test_dumps$3(var2, var3);
         case 4:
            return this.test_encode_truefalse$4(var2, var3);
         case 5:
            return this.test_encode_mutated$5(var2, var3);
         case 6:
            return this.crasher$6(var2, var3);
         case 7:
            return this.TestPyDump$7(var2, var3);
         case 8:
            return this.TestCDump$8(var2, var3);
         default:
            return null;
      }
   }
}
