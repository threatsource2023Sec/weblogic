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
import org.python.core.PyException;
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
@Filename("json/tests/test_fail.py")
public class test_fail$py extends PyFunctionTable implements PyRunnable {
   static test_fail$py self;
   static final PyCode f$0;
   static final PyCode TestFail$1;
   static final PyCode test_failures$2;
   static final PyCode test_non_string_keys_dict$3;
   static final PyCode TestPyFail$4;
   static final PyCode TestCFail$5;

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
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("\"A JSON payload should be an object or array, not a string.\""), PyString.fromInterned("[\"Unclosed array\""), PyString.fromInterned("{unquoted_key: \"keys must be quoted\"}"), PyString.fromInterned("[\"extra comma\",]"), PyString.fromInterned("[\"double extra comma\",,]"), PyString.fromInterned("[   , \"<-- missing value\"]"), PyString.fromInterned("[\"Comma after the close\"],"), PyString.fromInterned("[\"Extra close\"]]"), PyString.fromInterned("{\"Extra comma\": true,}"), PyString.fromInterned("{\"Extra value after close\": true} \"misplaced quoted value\""), PyString.fromInterned("{\"Illegal expression\": 1 + 2}"), PyString.fromInterned("{\"Illegal invocation\": alert()}"), PyString.fromInterned("{\"Numbers cannot have leading zeroes\": 013}"), PyString.fromInterned("{\"Numbers cannot be hex\": 0x14}"), PyString.fromInterned("[\"Illegal backslash escape: \\x15\"]"), PyString.fromInterned("[\\naked]"), PyString.fromInterned("[\"Illegal backslash escape: \\017\"]"), PyString.fromInterned("[[[[[[[[[[[[[[[[[[[[\"Too deep\"]]]]]]]]]]]]]]]]]]]]"), PyString.fromInterned("{\"Missing colon\" null}"), PyString.fromInterned("{\"Double colon\":: null}"), PyString.fromInterned("{\"Comma instead of colon\", null}"), PyString.fromInterned("[\"Colon instead of comma\": false]"), PyString.fromInterned("[\"Bad value\", truth]"), PyString.fromInterned("['single quote']"), PyString.fromInterned("[\"\ttab\tcharacter\tin\tstring\t\"]"), PyString.fromInterned("[\"tab\\   character\\   in\\  string\\  \"]"), PyString.fromInterned("[\"line\nbreak\"]"), PyString.fromInterned("[\"line\\\nbreak\"]"), PyString.fromInterned("[0e]"), PyString.fromInterned("[0e+]"), PyString.fromInterned("[0e+-1]"), PyString.fromInterned("{\"Comma instead if closing brace\": true,"), PyString.fromInterned("[\"mismatch\"}"), PyUnicode.fromInterned("[\"A\u001fZ control characters in string\"]")});
      var1.setlocal("JSONDOCS", var6);
      var3 = null;
      var1.setline(75);
      PyDictionary var7 = new PyDictionary(new PyObject[]{Py.newInteger(1), PyString.fromInterned("why not have a string payload?"), Py.newInteger(18), PyString.fromInterned("spec doesn't specify any nesting limitations")});
      var1.setlocal("SKIPS", var7);
      var3 = null;
      var1.setline(80);
      var5 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TestFail", var5, TestFail$1);
      var1.setlocal("TestFail", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(104);
      var5 = new PyObject[]{var1.getname("TestFail"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyFail", var5, TestPyFail$4);
      var1.setlocal("TestPyFail", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(105);
      var5 = new PyObject[]{var1.getname("TestFail"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCFail", var5, TestCFail$5);
      var1.setlocal("TestCFail", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestFail$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(81);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_failures$2, (PyObject)null);
      var1.setlocal("test_failures", var4);
      var3 = null;
      var1.setline(94);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_non_string_keys_dict$3, (PyObject)null);
      var1.setlocal("test_non_string_keys_dict", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_failures$2(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject var3 = var1.getglobal("enumerate").__call__(var2, var1.getglobal("JSONDOCS")).__iter__();

      while(true) {
         while(true) {
            var1.setline(82);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(1, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(2, var6);
            var6 = null;
            var1.setline(83);
            PyObject var8 = var1.getlocal(1)._add(Py.newInteger(1));
            var1.setlocal(1, var8);
            var5 = null;
            var1.setline(84);
            var8 = var1.getlocal(1);
            PyObject var10000 = var8._in(var1.getglobal("SKIPS"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(85);
               var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(2));
            } else {
               try {
                  var1.setline(88);
                  var1.getlocal(0).__getattr__("loads").__call__(var2, var1.getlocal(2));
               } catch (Throwable var7) {
                  PyException var9 = Py.setException(var7, var1);
                  if (var9.match(var1.getglobal("ValueError"))) {
                     var1.setline(90);
                     continue;
                  }

                  throw var9;
               }

               var1.setline(92);
               var1.getlocal(0).__getattr__("fail").__call__(var2, PyString.fromInterned("Expected failure for fail{0}.json: {1!r}").__getattr__("format").__call__(var2, var1.getlocal(1), var1.getlocal(2)));
            }
         }
      }
   }

   public PyObject test_non_string_keys_dict$3(PyFrame var1, ThreadState var2) {
      var1.setline(95);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1), new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2)}), Py.newInteger(2)});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(98);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("TypeError"), var1.getlocal(0).__getattr__("dumps"), var1.getlocal(1));
      var1.setline(101);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertRaises");
      PyObject[] var5 = new PyObject[]{var1.getglobal("TypeError"), var1.getlocal(0).__getattr__("dumps"), var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"indent"};
      var10000.__call__(var2, var5, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPyFail$4(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(104);
      return var1.getf_locals();
   }

   public PyObject TestCFail$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(105);
      return var1.getf_locals();
   }

   public test_fail$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestFail$1 = Py.newCode(0, var2, var1, "TestFail", 80, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "idx", "doc"};
      test_failures$2 = Py.newCode(1, var2, var1, "test_failures", 81, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data"};
      test_non_string_keys_dict$3 = Py.newCode(1, var2, var1, "test_non_string_keys_dict", 94, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestPyFail$4 = Py.newCode(0, var2, var1, "TestPyFail", 104, false, false, self, 4, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCFail$5 = Py.newCode(0, var2, var1, "TestCFail", 105, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_fail$py("json/tests/test_fail$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_fail$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestFail$1(var2, var3);
         case 2:
            return this.test_failures$2(var2, var3);
         case 3:
            return this.test_non_string_keys_dict$3(var2, var3);
         case 4:
            return this.TestPyFail$4(var2, var3);
         case 5:
            return this.TestCFail$5(var2, var3);
         default:
            return null;
      }
   }
}
