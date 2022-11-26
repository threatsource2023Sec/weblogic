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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("json/tests/test_decode.py")
public class test_decode$py extends PyFunctionTable implements PyRunnable {
   static test_decode$py self;
   static final PyCode f$0;
   static final PyCode TestDecode$1;
   static final PyCode test_decimal$2;
   static final PyCode test_float$3;
   static final PyCode test_decoder_optimizations$4;
   static final PyCode test_empty_objects$5;
   static final PyCode test_object_pairs_hook$6;
   static final PyCode f$7;
   static final PyCode f$8;
   static final PyCode f$9;
   static final PyCode test_extra_data$10;
   static final PyCode test_invalid_escape$11;
   static final PyCode TestPyDecode$12;
   static final PyCode TestCDecode$13;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("decimal", var1, -1);
      var1.setlocal("decimal", var3);
      var3 = null;
      var1.setline(2);
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("StringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(3);
      var5 = new String[]{"OrderedDict"};
      var6 = imp.importFrom("collections", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("OrderedDict", var4);
      var4 = null;
      var1.setline(4);
      var5 = new String[]{"PyTest", "CTest"};
      var6 = imp.importFrom("json.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("PyTest", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("CTest", var4);
      var4 = null;
      var1.setline(7);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestDecode", var6, TestDecode$1);
      var1.setlocal("TestDecode", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(58);
      var6 = new PyObject[]{var1.getname("TestDecode"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyDecode", var6, TestPyDecode$12);
      var1.setlocal("TestPyDecode", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(59);
      var6 = new PyObject[]{var1.getname("TestDecode"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCDecode", var6, TestCDecode$13);
      var1.setlocal("TestCDecode", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestDecode$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(8);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_decimal$2, (PyObject)null);
      var1.setlocal("test_decimal", var4);
      var3 = null;
      var1.setline(13);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_float$3, (PyObject)null);
      var1.setlocal("test_float", var4);
      var3 = null;
      var1.setline(18);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_decoder_optimizations$4, (PyObject)null);
      var1.setlocal("test_decoder_optimizations", var4);
      var3 = null;
      var1.setline(25);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_empty_objects$5, (PyObject)null);
      var1.setlocal("test_empty_objects", var4);
      var3 = null;
      var1.setline(31);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_object_pairs_hook$6, (PyObject)null);
      var1.setlocal("test_object_pairs_hook", var4);
      var3 = null;
      var1.setline(48);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_extra_data$10, (PyObject)null);
      var1.setlocal("test_extra_data", var4);
      var3 = null;
      var1.setline(53);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_invalid_escape$11, (PyObject)null);
      var1.setlocal("test_invalid_escape", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_decimal$2(PyFrame var1, ThreadState var2) {
      var1.setline(9);
      PyObject var10000 = var1.getlocal(0).__getattr__("loads");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("1.1"), var1.getglobal("decimal").__getattr__("Decimal")};
      String[] var4 = new String[]{"parse_float"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(10);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("decimal").__getattr__("Decimal")));
      var1.setline(11);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getglobal("decimal").__getattr__("Decimal").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("1.1")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_float$3(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      PyObject var10000 = var1.getlocal(0).__getattr__("loads");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("1"), var1.getglobal("float")};
      String[] var4 = new String[]{"parse_int"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(15);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("float")));
      var1.setline(16);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)Py.newFloat(1.0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_decoder_optimizations$4(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyObject var3 = var1.getlocal(0).__getattr__("loads").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("{   \"key\"    :    \"value\"    ,  \"k\":\"v\"    }"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(23);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("key"), PyString.fromInterned("value"), PyString.fromInterned("k"), PyString.fromInterned("v")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_empty_objects$5(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("loads").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("{}")), (PyObject)(new PyDictionary(Py.EmptyObjects)));
      var1.setline(27);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("loads").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[]")), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(28);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("loads").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\"\"")), (PyObject)PyUnicode.fromInterned(""));
      var1.setline(29);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\"\"")), var1.getglobal("unicode"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_object_pairs_hook$6(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyString var3 = PyString.fromInterned("{\"xkd\":1, \"kcw\":2, \"art\":3, \"hxm\":4, \"qrt\":5, \"pad\":6, \"hoy\":7}");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(33);
      PyList var5 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("xkd"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("kcw"), Py.newInteger(2)}), new PyTuple(new PyObject[]{PyString.fromInterned("art"), Py.newInteger(3)}), new PyTuple(new PyObject[]{PyString.fromInterned("hxm"), Py.newInteger(4)}), new PyTuple(new PyObject[]{PyString.fromInterned("qrt"), Py.newInteger(5)}), new PyTuple(new PyObject[]{PyString.fromInterned("pad"), Py.newInteger(6)}), new PyTuple(new PyObject[]{PyString.fromInterned("hoy"), Py.newInteger(7)})});
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(35);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(1)), var1.getglobal("eval").__call__(var2, var1.getlocal(1)));
      var1.setline(36);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getlocal(0).__getattr__("loads");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), null};
      var1.setline(36);
      PyObject[] var4 = Py.EmptyObjects;
      var6[1] = new PyFunction(var1.f_globals, var4, f$7);
      String[] var7 = new String[]{"object_pairs_hook"};
      var10002 = var10002.__call__(var2, var6, var7);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(2));
      var1.setline(37);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      var10002 = var1.getlocal(0).__getattr__("json").__getattr__("load");
      var6 = new PyObject[]{var1.getglobal("StringIO").__call__(var2, var1.getlocal(1)), null};
      var1.setline(38);
      var4 = Py.EmptyObjects;
      var6[1] = new PyFunction(var1.f_globals, var4, f$8);
      var7 = new String[]{"object_pairs_hook"};
      var10002 = var10002.__call__(var2, var6, var7);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(2));
      var1.setline(39);
      var10000 = var1.getlocal(0).__getattr__("loads");
      var6 = new PyObject[]{var1.getlocal(1), var1.getglobal("OrderedDict")};
      var7 = new String[]{"object_pairs_hook"};
      var10000 = var10000.__call__(var2, var6, var7);
      var3 = null;
      PyObject var8 = var10000;
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(40);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3), var1.getglobal("OrderedDict").__call__(var2, var1.getlocal(2)));
      var1.setline(41);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(3)), var1.getglobal("OrderedDict"));
      var1.setline(43);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      var10002 = var1.getlocal(0).__getattr__("loads");
      var6 = new PyObject[]{var1.getlocal(1), var1.getglobal("OrderedDict"), null};
      var1.setline(45);
      var4 = Py.EmptyObjects;
      var6[2] = new PyFunction(var1.f_globals, var4, f$9);
      var7 = new String[]{"object_pairs_hook", "object_hook"};
      var10002 = var10002.__call__(var2, var6, var7);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getglobal("OrderedDict").__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$7(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$8(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$9(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_extra_data$10(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyString var3 = PyString.fromInterned("[1, 2, 3]5");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(50);
      var3 = PyString.fromInterned("Extra data");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(51);
      var1.getlocal(0).__getattr__("assertRaisesRegexp").__call__(var2, var1.getglobal("ValueError"), var1.getlocal(2), var1.getlocal(0).__getattr__("loads"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_invalid_escape$11(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyString var3 = PyString.fromInterned("[\"abc\\y\"]");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(55);
      var3 = PyString.fromInterned("escape");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(56);
      var1.getlocal(0).__getattr__("assertRaisesRegexp").__call__(var2, var1.getglobal("ValueError"), var1.getlocal(2), var1.getlocal(0).__getattr__("loads"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPyDecode$12(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(58);
      return var1.getf_locals();
   }

   public PyObject TestCDecode$13(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(59);
      return var1.getf_locals();
   }

   public test_decode$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestDecode$1 = Py.newCode(0, var2, var1, "TestDecode", 7, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "rval"};
      test_decimal$2 = Py.newCode(1, var2, var1, "test_decimal", 8, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rval"};
      test_float$3 = Py.newCode(1, var2, var1, "test_float", 13, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "rval"};
      test_decoder_optimizations$4 = Py.newCode(1, var2, var1, "test_decoder_optimizations", 18, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_empty_objects$5 = Py.newCode(1, var2, var1, "test_empty_objects", 25, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "p", "od"};
      test_object_pairs_hook$6 = Py.newCode(1, var2, var1, "test_object_pairs_hook", 31, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$7 = Py.newCode(1, var2, var1, "<lambda>", 36, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$8 = Py.newCode(1, var2, var1, "<lambda>", 38, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$9 = Py.newCode(1, var2, var1, "<lambda>", 45, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "msg"};
      test_extra_data$10 = Py.newCode(1, var2, var1, "test_extra_data", 48, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "msg"};
      test_invalid_escape$11 = Py.newCode(1, var2, var1, "test_invalid_escape", 53, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestPyDecode$12 = Py.newCode(0, var2, var1, "TestPyDecode", 58, false, false, self, 12, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCDecode$13 = Py.newCode(0, var2, var1, "TestCDecode", 59, false, false, self, 13, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_decode$py("json/tests/test_decode$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_decode$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestDecode$1(var2, var3);
         case 2:
            return this.test_decimal$2(var2, var3);
         case 3:
            return this.test_float$3(var2, var3);
         case 4:
            return this.test_decoder_optimizations$4(var2, var3);
         case 5:
            return this.test_empty_objects$5(var2, var3);
         case 6:
            return this.test_object_pairs_hook$6(var2, var3);
         case 7:
            return this.f$7(var2, var3);
         case 8:
            return this.f$8(var2, var3);
         case 9:
            return this.f$9(var2, var3);
         case 10:
            return this.test_extra_data$10(var2, var3);
         case 11:
            return this.test_invalid_escape$11(var2, var3);
         case 12:
            return this.TestPyDecode$12(var2, var3);
         case 13:
            return this.TestCDecode$13(var2, var3);
         default:
            return null;
      }
   }
}
