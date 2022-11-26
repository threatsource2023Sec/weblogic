package json.tests;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
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
@Filename("json/tests/test_scanstring.py")
public class test_scanstring$py extends PyFunctionTable implements PyRunnable {
   static test_scanstring$py self;
   static final PyCode f$0;
   static final PyCode TestScanstring$1;
   static final PyCode test_scanstring$2;
   static final PyCode test_issue3623$3;
   static final PyCode test_overflow$4;
   static final PyCode TestPyScanstring$5;
   static final PyCode TestCScanstring$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
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
      var4 = Py.makeClass("TestScanstring", var6, TestScanstring$1);
      var1.setlocal("TestScanstring", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(108);
      var6 = new PyObject[]{var1.getname("TestScanstring"), var1.getname("PyTest")};
      var4 = Py.makeClass("TestPyScanstring", var6, TestPyScanstring$5);
      var1.setlocal("TestPyScanstring", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(109);
      var6 = new PyObject[]{var1.getname("TestScanstring"), var1.getname("CTest")};
      var4 = Py.makeClass("TestCScanstring", var6, TestCScanstring$6);
      var1.setlocal("TestCScanstring", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestScanstring$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(6);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_scanstring$2, (PyObject)null);
      var1.setlocal("test_scanstring", var4);
      var3 = null;
      var1.setline(97);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_issue3623$3, (PyObject)null);
      var1.setlocal("test_issue3623", var4);
      var3 = null;
      var1.setline(103);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_overflow$4, (PyObject)null);
      var1.setlocal("test_overflow", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_scanstring$2(PyFrame var1, ThreadState var2) {
      var1.setline(7);
      PyObject var3 = var1.getlocal(0).__getattr__("json").__getattr__("decoder").__getattr__("scanstring");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(8);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("\"z\\ud834\\udd20x\""), Py.newInteger(1), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("z\ud834\udd20x"), Py.newInteger(16)})));
      var1.setline(12);
      var3 = var1.getglobal("sys").__getattr__("maxunicode");
      PyObject var10000 = var3._eq(Py.newInteger(65535));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(13);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyUnicode.fromInterned("\"z\ud834\udd20x\""), Py.newInteger(1), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("z\ud834\udd20x"), Py.newInteger(6)})));
      } else {
         var1.setline(17);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyUnicode.fromInterned("\"z\ud834\udd20x\""), Py.newInteger(1), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("z\ud834\udd20x"), Py.newInteger(5)})));
      }

      var1.setline(21);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("\"\\u007b\""), Py.newInteger(1), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("{"), Py.newInteger(8)})));
      var1.setline(25);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("\"A JSON payload should be an object or array, not a string.\""), Py.newInteger(1), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("A JSON payload should be an object or array, not a string."), Py.newInteger(60)})));
      var1.setline(29);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("[\"Unclosed array\""), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Unclosed array"), Py.newInteger(17)})));
      var1.setline(33);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("[\"extra comma\",]"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("extra comma"), Py.newInteger(14)})));
      var1.setline(37);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("[\"double extra comma\",,]"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("double extra comma"), Py.newInteger(21)})));
      var1.setline(41);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("[\"Comma after the close\"],"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Comma after the close"), Py.newInteger(24)})));
      var1.setline(45);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("[\"Extra close\"]]"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Extra close"), Py.newInteger(14)})));
      var1.setline(49);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("{\"Extra comma\": true,}"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Extra comma"), Py.newInteger(14)})));
      var1.setline(53);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("{\"Extra value after close\": true} \"misplaced quoted value\""), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Extra value after close"), Py.newInteger(26)})));
      var1.setline(57);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("{\"Illegal expression\": 1 + 2}"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Illegal expression"), Py.newInteger(21)})));
      var1.setline(61);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("{\"Illegal invocation\": alert()}"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Illegal invocation"), Py.newInteger(21)})));
      var1.setline(65);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("{\"Numbers cannot have leading zeroes\": 013}"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Numbers cannot have leading zeroes"), Py.newInteger(37)})));
      var1.setline(69);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("{\"Numbers cannot be hex\": 0x14}"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Numbers cannot be hex"), Py.newInteger(24)})));
      var1.setline(73);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("[[[[[[[[[[[[[[[[[[[[\"Too deep\"]]]]]]]]]]]]]]]]]]]]"), Py.newInteger(21), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Too deep"), Py.newInteger(30)})));
      var1.setline(77);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("{\"Missing colon\" null}"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Missing colon"), Py.newInteger(16)})));
      var1.setline(81);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("{\"Double colon\":: null}"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Double colon"), Py.newInteger(15)})));
      var1.setline(85);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("{\"Comma instead of colon\", null}"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Comma instead of colon"), Py.newInteger(25)})));
      var1.setline(89);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("[\"Colon instead of comma\": false]"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Colon instead of comma"), Py.newInteger(25)})));
      var1.setline(93);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__call__(var2, PyString.fromInterned("[\"Bad value\", truth]"), Py.newInteger(2), var1.getglobal("None"), var1.getglobal("True")), (PyObject)(new PyTuple(new PyObject[]{PyUnicode.fromInterned("Bad value"), Py.newInteger(12)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_issue3623$3(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertRaises");
      PyObject[] var3 = new PyObject[]{var1.getglobal("ValueError"), var1.getlocal(0).__getattr__("json").__getattr__("decoder").__getattr__("scanstring"), PyString.fromInterned("xxx"), Py.newInteger(1), PyString.fromInterned("xxx")};
      var10000.__call__(var2, var3);
      var1.setline(100);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("UnicodeDecodeError"), (PyObject)var1.getlocal(0).__getattr__("json").__getattr__("encoder").__getattr__("encode_basestring_ascii"), (PyObject)PyString.fromInterned("xxÿ"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_overflow$4(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("OverflowError")))).__enter__(var2);

      label16: {
         try {
            var1.setline(105);
            var1.getlocal(0).__getattr__("json").__getattr__("decoder").__getattr__("scanstring").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xxx"), (PyObject)var1.getglobal("sys").__getattr__("maxsize")._add(Py.newInteger(1)));
         } catch (Throwable var5) {
            if (var3.__exit__(var2, Py.setException(var5, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestPyScanstring$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(108);
      return var1.getf_locals();
   }

   public PyObject TestCScanstring$6(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(109);
      return var1.getf_locals();
   }

   public test_scanstring$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestScanstring$1 = Py.newCode(0, var2, var1, "TestScanstring", 5, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "scanstring"};
      test_scanstring$2 = Py.newCode(1, var2, var1, "test_scanstring", 6, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_issue3623$3 = Py.newCode(1, var2, var1, "test_issue3623", 97, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_overflow$4 = Py.newCode(1, var2, var1, "test_overflow", 103, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestPyScanstring$5 = Py.newCode(0, var2, var1, "TestPyScanstring", 108, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestCScanstring$6 = Py.newCode(0, var2, var1, "TestCScanstring", 109, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_scanstring$py("json/tests/test_scanstring$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_scanstring$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestScanstring$1(var2, var3);
         case 2:
            return this.test_scanstring$2(var2, var3);
         case 3:
            return this.test_issue3623$3(var2, var3);
         case 4:
            return this.test_overflow$4(var2, var3);
         case 5:
            return this.TestPyScanstring$5(var2, var3);
         case 6:
            return this.TestCScanstring$6(var2, var3);
         default:
            return null;
      }
   }
}
