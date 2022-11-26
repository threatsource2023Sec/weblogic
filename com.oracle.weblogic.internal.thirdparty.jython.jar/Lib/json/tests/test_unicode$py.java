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
@Filename("json/tests/test_unicode.py")
public class test_unicode$py extends PyFunctionTable implements PyRunnable {
   static test_unicode$py self;
   static final PyCode f$0;
   static final PyCode TestUnicode$1;
   static final PyCode test_encoding1$2;
   static final PyCode test_encoding2$3;
   static final PyCode test_encoding3$4;
   static final PyCode test_encoding4$5;
   static final PyCode test_encoding5$6;
   static final PyCode test_encoding6$7;
   static final PyCode test_big_unicode_encode$8;
   static final PyCode test_big_unicode_decode$9;
   static final PyCode test_unicode_decode$10;
   static final PyCode test_object_pairs_hook_with_unicode$11;
   static final PyCode f$12;
   static final PyCode f$13;
   static final PyCode test_default_encoding$14;
   static final PyCode test_unicode_preservation$15;
   static final PyCode test_bad_encoding$16;
   static final PyCode TestPyUnicode$17;
   static final PyCode TestCUnicode$18;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      String[] var3 = new String[]{"OrderedDict"};
      PyObject[] var5 = imp.importFrom("collections", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("OrderedDict", var4);
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
      var4 = Py.makeClass("TestUnicode", var5, TestUnicode$1);
      var1.setlocal("TestUnicode", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(88);
      var5 = new PyObject[]{var1.getname("TestUnicode"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyUnicode", var5, TestPyUnicode$17);
      var1.setlocal("TestPyUnicode", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(89);
      var5 = new PyObject[]{var1.getname("TestUnicode"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCUnicode", var5, TestCUnicode$18);
      var1.setlocal("TestCUnicode", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestUnicode$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(6);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_encoding1$2, (PyObject)null);
      var1.setlocal("test_encoding1", var4);
      var3 = null;
      var1.setline(14);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encoding2$3, (PyObject)null);
      var1.setlocal("test_encoding2", var4);
      var3 = null;
      var1.setline(21);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encoding3$4, (PyObject)null);
      var1.setlocal("test_encoding3", var4);
      var3 = null;
      var1.setline(26);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encoding4$5, (PyObject)null);
      var1.setlocal("test_encoding4", var4);
      var3 = null;
      var1.setline(31);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encoding5$6, (PyObject)null);
      var1.setlocal("test_encoding5", var4);
      var3 = null;
      var1.setline(36);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_encoding6$7, (PyObject)null);
      var1.setlocal("test_encoding6", var4);
      var3 = null;
      var1.setline(41);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_big_unicode_encode$8, (PyObject)null);
      var1.setlocal("test_big_unicode_encode", var4);
      var3 = null;
      var1.setline(46);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_big_unicode_decode$9, (PyObject)null);
      var1.setlocal("test_big_unicode_decode", var4);
      var3 = null;
      var1.setline(51);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_unicode_decode$10, (PyObject)null);
      var1.setlocal("test_unicode_decode", var4);
      var3 = null;
      var1.setline(57);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_object_pairs_hook_with_unicode$11, (PyObject)null);
      var1.setlocal("test_object_pairs_hook_with_unicode", var4);
      var3 = null;
      var1.setline(72);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_default_encoding$14, (PyObject)null);
      var1.setlocal("test_default_encoding", var4);
      var3 = null;
      var1.setline(76);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_unicode_preservation$15, (PyObject)null);
      var1.setlocal("test_unicode_preservation", var4);
      var3 = null;
      var1.setline(83);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_bad_encoding$16, (PyObject)null);
      var1.setlocal("test_bad_encoding", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_encoding1$2(PyFrame var1, ThreadState var2) {
      var1.setline(7);
      PyObject var10000 = var1.getlocal(0).__getattr__("json").__getattr__("JSONEncoder");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("utf-8")};
      String[] var4 = new String[]{"encoding"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(8);
      PyUnicode var6 = PyUnicode.fromInterned("αΩ");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(9);
      var5 = var1.getlocal(2).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(10);
      var5 = var1.getlocal(1).__getattr__("encode").__call__(var2, var1.getlocal(2));
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(11);
      var5 = var1.getlocal(1).__getattr__("encode").__call__(var2, var1.getlocal(3));
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(12);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4), var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encoding2$3(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyUnicode var3 = PyUnicode.fromInterned("αΩ");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(16);
      PyObject var5 = var1.getlocal(1).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(17);
      PyObject var10000 = var1.getlocal(0).__getattr__("dumps");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("utf-8")};
      String[] var4 = new String[]{"encoding"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(18);
      var10000 = var1.getlocal(0).__getattr__("dumps");
      var6 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("utf-8")};
      var4 = new String[]{"encoding"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(19);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encoding3$4(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyUnicode var3 = PyUnicode.fromInterned("αΩ");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(23);
      PyObject var4 = var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(24);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("\"\\u03b1\\u03a9\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encoding4$5(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyUnicode var3 = PyUnicode.fromInterned("αΩ");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(28);
      PyObject var4 = var1.getlocal(0).__getattr__("dumps").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)})));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(29);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("[\"\\u03b1\\u03a9\"]"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encoding5$6(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyUnicode var3 = PyUnicode.fromInterned("αΩ");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(33);
      PyObject var10000 = var1.getlocal(0).__getattr__("dumps");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("False")};
      String[] var4 = new String[]{"ensure_ascii"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(34);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2), PyUnicode.fromInterned("\"{0}\"").__getattr__("format").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_encoding6$7(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyUnicode var3 = PyUnicode.fromInterned("αΩ");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(38);
      PyObject var10000 = var1.getlocal(0).__getattr__("dumps");
      PyObject[] var5 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(1)}), var1.getglobal("False")};
      String[] var4 = new String[]{"ensure_ascii"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(39);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2), PyUnicode.fromInterned("[\"{0}\"]").__getattr__("format").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_big_unicode_encode$8(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyUnicode var3 = PyUnicode.fromInterned("\ud834\udd20");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(43);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getlocal(1)), (PyObject)PyString.fromInterned("\"\\ud834\\udd20\""));
      var1.setline(44);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getlocal(0).__getattr__("dumps");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("False")};
      String[] var4 = new String[]{"ensure_ascii"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyUnicode.fromInterned("\"\ud834\udd20\""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_big_unicode_decode$9(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyUnicode var3 = PyUnicode.fromInterned("z\ud834\udd20x");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(48);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__(var2, PyString.fromInterned("\"")._add(var1.getlocal(1))._add(PyString.fromInterned("\""))), var1.getlocal(1));
      var1.setline(49);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\"z\\ud834\\udd20x\"")), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_unicode_decode$10(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getglobal("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)Py.newInteger(55295)).__iter__();

      while(true) {
         var1.setline(52);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(53);
         PyObject var5 = var1.getglobal("unichr").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(54);
         var5 = PyString.fromInterned("\"\\u{0:04x}\"").__getattr__("format").__call__(var2, var1.getlocal(1));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(55);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(3)), var1.getlocal(2));
      }
   }

   public PyObject test_object_pairs_hook_with_unicode$11(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyUnicode var3 = PyUnicode.fromInterned("{\"xkd\":1, \"kcw\":2, \"art\":3, \"hxm\":4, \"qrt\":5, \"pad\":6, \"hoy\":7}");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(59);
      PyList var5 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyUnicode.fromInterned("xkd"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("kcw"), Py.newInteger(2)}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("art"), Py.newInteger(3)}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("hxm"), Py.newInteger(4)}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("qrt"), Py.newInteger(5)}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("pad"), Py.newInteger(6)}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("hoy"), Py.newInteger(7)})});
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(61);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(1)), var1.getglobal("eval").__call__(var2, var1.getlocal(1)));
      var1.setline(62);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertEqual");
      PyObject var10002 = var1.getlocal(0).__getattr__("loads");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), null};
      var1.setline(62);
      PyObject[] var4 = Py.EmptyObjects;
      var6[1] = new PyFunction(var1.f_globals, var4, f$12);
      String[] var7 = new String[]{"object_pairs_hook"};
      var10002 = var10002.__call__(var2, var6, var7);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getlocal(2));
      var1.setline(63);
      var10000 = var1.getlocal(0).__getattr__("loads");
      var6 = new PyObject[]{var1.getlocal(1), var1.getglobal("OrderedDict")};
      var7 = new String[]{"object_pairs_hook"};
      var10000 = var10000.__call__(var2, var6, var7);
      var3 = null;
      PyObject var8 = var10000;
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(64);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3), var1.getglobal("OrderedDict").__call__(var2, var1.getlocal(2)));
      var1.setline(65);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(3)), var1.getglobal("OrderedDict"));
      var1.setline(67);
      var10000 = var1.getlocal(0).__getattr__("assertEqual");
      var10002 = var1.getlocal(0).__getattr__("loads");
      var6 = new PyObject[]{var1.getlocal(1), var1.getglobal("OrderedDict"), null};
      var1.setline(69);
      var4 = Py.EmptyObjects;
      var6[2] = new PyFunction(var1.f_globals, var4, f$13);
      var7 = new String[]{"object_pairs_hook", "object_hook"};
      var10002 = var10002.__call__(var2, var6, var7);
      var3 = null;
      var10000.__call__(var2, var10002, var1.getglobal("OrderedDict").__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$12(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyObject var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$13(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_default_encoding$14(PyFrame var1, ThreadState var2) {
      var1.setline(73);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("loads").__call__(var2, PyUnicode.fromInterned("{\"a\": \"é\"}").__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("utf-8"))), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("a"), PyUnicode.fromInterned("é")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_unicode_preservation$15(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\"\""))), var1.getglobal("unicode"));
      var1.setline(78);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("\"a\""))), var1.getglobal("unicode"));
      var1.setline(79);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("[\"a\"]")).__getitem__(Py.newInteger(0))), var1.getglobal("unicode"));
      var1.setline(81);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("loads").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\"foo\""))), var1.getglobal("unicode"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_bad_encoding$16(PyFrame var1, ThreadState var2) {
      var1.setline(84);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("UnicodeEncodeError"), var1.getlocal(0).__getattr__("loads"), PyString.fromInterned("\"a\""), PyUnicode.fromInterned("raté"));
      var1.setline(85);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("TypeError"), var1.getlocal(0).__getattr__("loads"), PyString.fromInterned("\"a\""), Py.newInteger(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPyUnicode$17(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(88);
      return var1.getf_locals();
   }

   public PyObject TestCUnicode$18(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(89);
      return var1.getf_locals();
   }

   public test_unicode$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestUnicode$1 = Py.newCode(0, var2, var1, "TestUnicode", 5, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "encoder", "u", "s", "ju", "js"};
      test_encoding1$2 = Py.newCode(1, var2, var1, "test_encoding1", 6, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "u", "s", "ju", "js"};
      test_encoding2$3 = Py.newCode(1, var2, var1, "test_encoding2", 14, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "u", "j"};
      test_encoding3$4 = Py.newCode(1, var2, var1, "test_encoding3", 21, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "u", "j"};
      test_encoding4$5 = Py.newCode(1, var2, var1, "test_encoding4", 26, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "u", "j"};
      test_encoding5$6 = Py.newCode(1, var2, var1, "test_encoding5", 31, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "u", "j"};
      test_encoding6$7 = Py.newCode(1, var2, var1, "test_encoding6", 36, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "u"};
      test_big_unicode_encode$8 = Py.newCode(1, var2, var1, "test_big_unicode_encode", 41, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "u"};
      test_big_unicode_decode$9 = Py.newCode(1, var2, var1, "test_big_unicode_decode", 46, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "u", "s"};
      test_unicode_decode$10 = Py.newCode(1, var2, var1, "test_unicode_decode", 51, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "s", "p", "od"};
      test_object_pairs_hook_with_unicode$11 = Py.newCode(1, var2, var1, "test_object_pairs_hook_with_unicode", 57, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$12 = Py.newCode(1, var2, var1, "<lambda>", 62, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$13 = Py.newCode(1, var2, var1, "<lambda>", 69, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_default_encoding$14 = Py.newCode(1, var2, var1, "test_default_encoding", 72, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_unicode_preservation$15 = Py.newCode(1, var2, var1, "test_unicode_preservation", 76, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_bad_encoding$16 = Py.newCode(1, var2, var1, "test_bad_encoding", 83, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestPyUnicode$17 = Py.newCode(0, var2, var1, "TestPyUnicode", 88, false, false, self, 17, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCUnicode$18 = Py.newCode(0, var2, var1, "TestCUnicode", 89, false, false, self, 18, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_unicode$py("json/tests/test_unicode$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_unicode$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestUnicode$1(var2, var3);
         case 2:
            return this.test_encoding1$2(var2, var3);
         case 3:
            return this.test_encoding2$3(var2, var3);
         case 4:
            return this.test_encoding3$4(var2, var3);
         case 5:
            return this.test_encoding4$5(var2, var3);
         case 6:
            return this.test_encoding5$6(var2, var3);
         case 7:
            return this.test_encoding6$7(var2, var3);
         case 8:
            return this.test_big_unicode_encode$8(var2, var3);
         case 9:
            return this.test_big_unicode_decode$9(var2, var3);
         case 10:
            return this.test_unicode_decode$10(var2, var3);
         case 11:
            return this.test_object_pairs_hook_with_unicode$11(var2, var3);
         case 12:
            return this.f$12(var2, var3);
         case 13:
            return this.f$13(var2, var3);
         case 14:
            return this.test_default_encoding$14(var2, var3);
         case 15:
            return this.test_unicode_preservation$15(var2, var3);
         case 16:
            return this.test_bad_encoding$16(var2, var3);
         case 17:
            return this.TestPyUnicode$17(var2, var3);
         case 18:
            return this.TestCUnicode$18(var2, var3);
         default:
            return null;
      }
   }
}
