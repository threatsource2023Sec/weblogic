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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("json/tests/test_encode_basestring_ascii.py")
public class test_encode_basestring_ascii$py extends PyFunctionTable implements PyRunnable {
   static test_encode_basestring_ascii$py self;
   static final PyCode f$0;
   static final PyCode TestEncodeBasestringAscii$1;
   static final PyCode test_encode_basestring_ascii$2;
   static final PyCode test_ordered_dict$3;
   static final PyCode TestPyEncodeBasestringAscii$4;
   static final PyCode TestCEncodeBasestringAscii$5;

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
      PyList var6 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyUnicode.fromInterned("/\\\"쫾몾ꮘﳞ볚\uef4a\b\f\n\r\t`1~!@#$%^&*()_+-=[]{}|;:',./<>?"), PyString.fromInterned("\"/\\\\\\\"\\ucafe\\ubabe\\uab98\\ufcde\\ubcda\\uef4a\\b\\f\\n\\r\\t`1~!@#$%^&*()_+-=[]{}|;:',./<>?\"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("ģ䕧覫췯ꯍ\uef4a"), PyString.fromInterned("\"\\u0123\\u4567\\u89ab\\ucdef\\uabcd\\uef4a\"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("controls"), PyString.fromInterned("\"controls\"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("\b\f\n\r\t"), PyString.fromInterned("\"\\b\\f\\n\\r\\t\"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("{\"object with 1 member\":[\"array with 1 element\"]}"), PyString.fromInterned("\"{\\\"object with 1 member\\\":[\\\"array with 1 element\\\"]}\"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned(" s p a c e d "), PyString.fromInterned("\" s p a c e d \"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("\ud834\udd20"), PyString.fromInterned("\"\\ud834\\udd20\"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("αΩ"), PyString.fromInterned("\"\\u03b1\\u03a9\"")}), new PyTuple(new PyObject[]{PyString.fromInterned("Î±Î©"), PyString.fromInterned("\"\\u03b1\\u03a9\"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("αΩ"), PyString.fromInterned("\"\\u03b1\\u03a9\"")}), new PyTuple(new PyObject[]{PyString.fromInterned("Î±Î©"), PyString.fromInterned("\"\\u03b1\\u03a9\"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("αΩ"), PyString.fromInterned("\"\\u03b1\\u03a9\"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("αΩ"), PyString.fromInterned("\"\\u03b1\\u03a9\"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("`1~!@#$%^&*()_+-={':[,]}|;.</>?"), PyString.fromInterned("\"`1~!@#$%^&*()_+-={':[,]}|;.</>?\"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("\b\f\n\r\t"), PyString.fromInterned("\"\\b\\f\\n\\r\\t\"")}), new PyTuple(new PyObject[]{PyUnicode.fromInterned("ģ䕧覫췯ꯍ\uef4a"), PyString.fromInterned("\"\\u0123\\u4567\\u89ab\\ucdef\\uabcd\\uef4a\"")})});
      var1.setlocal("CASES", var6);
      var3 = null;
      var1.setline(24);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestEncodeBasestringAscii", var5, TestEncodeBasestringAscii$1);
      var1.setlocal("TestEncodeBasestringAscii", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(40);
      var5 = new PyObject[]{var1.getname("TestEncodeBasestringAscii"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyEncodeBasestringAscii", var5, TestPyEncodeBasestringAscii$4);
      var1.setlocal("TestPyEncodeBasestringAscii", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(41);
      var5 = new PyObject[]{var1.getname("TestEncodeBasestringAscii"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCEncodeBasestringAscii", var5, TestCEncodeBasestringAscii$5);
      var1.setlocal("TestCEncodeBasestringAscii", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestEncodeBasestringAscii$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(25);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_encode_basestring_ascii$2, (PyObject)null);
      var1.setlocal("test_encode_basestring_ascii", var4);
      var3 = null;
      var1.setline(33);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_ordered_dict$3, (PyObject)null);
      var1.setlocal("test_ordered_dict", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_encode_basestring_ascii$2(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyObject var3 = var1.getlocal(0).__getattr__("json").__getattr__("encoder").__getattr__("encode_basestring_ascii").__getattr__("__name__");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(27);
      var3 = var1.getglobal("CASES").__iter__();

      while(true) {
         var1.setline(27);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(28);
         PyObject var7 = var1.getlocal(0).__getattr__("json").__getattr__("encoder").__getattr__("encode_basestring_ascii").__call__(var2, var1.getlocal(2));
         var1.setlocal(4, var7);
         var5 = null;
         var1.setline(29);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4), var1.getlocal(3), PyString.fromInterned("{0!r} != {1!r} for {2}({3!r})").__getattr__("format").__call__(var2, var1.getlocal(4), var1.getlocal(3), var1.getlocal(1), var1.getlocal(2)));
      }
   }

   public PyObject test_ordered_dict$3(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("one"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("two"), Py.newInteger(2)}), new PyTuple(new PyObject[]{PyString.fromInterned("three"), Py.newInteger(3)}), new PyTuple(new PyObject[]{PyString.fromInterned("four"), Py.newInteger(4)}), new PyTuple(new PyObject[]{PyString.fromInterned("five"), Py.newInteger(5)})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(36);
      PyObject var4 = var1.getlocal(0).__getattr__("dumps").__call__(var2, var1.getglobal("OrderedDict").__call__(var2, var1.getlocal(1)));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(37);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("{\"one\": 1, \"two\": 2, \"three\": 3, \"four\": 4, \"five\": 5}"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPyEncodeBasestringAscii$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(40);
      return var1.getf_locals();
   }

   public PyObject TestCEncodeBasestringAscii$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(41);
      return var1.getf_locals();
   }

   public test_encode_basestring_ascii$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestEncodeBasestringAscii$1 = Py.newCode(0, var2, var1, "TestEncodeBasestringAscii", 24, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "fname", "input_string", "expect", "result"};
      test_encode_basestring_ascii$2 = Py.newCode(1, var2, var1, "test_encode_basestring_ascii", 25, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "items", "s"};
      test_ordered_dict$3 = Py.newCode(1, var2, var1, "test_ordered_dict", 33, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestPyEncodeBasestringAscii$4 = Py.newCode(0, var2, var1, "TestPyEncodeBasestringAscii", 40, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCEncodeBasestringAscii$5 = Py.newCode(0, var2, var1, "TestCEncodeBasestringAscii", 41, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_encode_basestring_ascii$py("json/tests/test_encode_basestring_ascii$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_encode_basestring_ascii$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestEncodeBasestringAscii$1(var2, var3);
         case 2:
            return this.test_encode_basestring_ascii$2(var2, var3);
         case 3:
            return this.test_ordered_dict$3(var2, var3);
         case 4:
            return this.TestPyEncodeBasestringAscii$4(var2, var3);
         case 5:
            return this.TestCEncodeBasestringAscii$5(var2, var3);
         default:
            return null;
      }
   }
}
