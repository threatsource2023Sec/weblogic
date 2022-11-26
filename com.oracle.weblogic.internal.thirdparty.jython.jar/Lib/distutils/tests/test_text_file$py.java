package distutils.tests;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_text_file.py")
public class test_text_file$py extends PyFunctionTable implements PyRunnable {
   static test_text_file$py self;
   static final PyCode f$0;
   static final PyCode TextFileTestCase$1;
   static final PyCode test_class$2;
   static final PyCode test_input$3;
   static final PyCode test_suite$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.text_file."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.text_file.");
      var1.setline(2);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(4);
      String[] var5 = new String[]{"TextFile"};
      PyObject[] var6 = imp.importFrom("distutils.text_file", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("TextFile", var4);
      var4 = null;
      var1.setline(5);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(6);
      var5 = new String[]{"run_unittest"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(8);
      PyString var7 = PyString.fromInterned("# test file\n\nline 3 \\\n# intervening comment\n  continues on next line\n");
      var1.setlocal("TEST_DATA", var7);
      var3 = null;
      var1.setline(15);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TextFileTestCase", var6, TextFileTestCase$1);
      var1.setlocal("TextFileTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(103);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, test_suite$4, (PyObject)null);
      var1.setlocal("test_suite", var8);
      var3 = null;
      var1.setline(106);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(107);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TextFileTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_class$2, (PyObject)null);
      var1.setlocal("test_class", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_class$2(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(22);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("# test file\n"), PyString.fromInterned("\n"), PyString.fromInterned("line 3 \\\n"), PyString.fromInterned("# intervening comment\n"), PyString.fromInterned("  continues on next line\n")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(27);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("\n"), PyString.fromInterned("line 3 \\\n"), PyString.fromInterned("  continues on next line\n")});
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(32);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("# test file\n"), PyString.fromInterned("line 3 \\\n"), PyString.fromInterned("# intervening comment\n"), PyString.fromInterned("  continues on next line\n")});
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(39);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("line 3 \\"), PyString.fromInterned("  continues on next line")});
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(44);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("line 3   continues on next line")});
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(48);
      var3 = new PyList(new PyObject[]{PyString.fromInterned("line 3 continues on next line")});
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(50);
      PyObject[] var12 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var12;
      PyCode var10004 = test_input$3;
      var12 = new PyObject[]{var1.getclosure(0)};
      PyFunction var13 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var12);
      var1.setlocal(7, var13);
      var3 = null;
      var1.setline(54);
      PyObject var14 = var1.getderef(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(8, var14);
      var3 = null;
      var1.setline(55);
      var14 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(8), (PyObject)PyString.fromInterned("test.txt"));
      var1.setlocal(9, var14);
      var3 = null;
      var1.setline(56);
      var14 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(10, var14);
      var3 = null;
      var3 = null;

      try {
         var1.setline(58);
         var1.getlocal(10).__getattr__("write").__call__(var2, var1.getglobal("TEST_DATA"));
      } catch (Throwable var11) {
         Py.addTraceback(var11, var1);
         var1.setline(60);
         var1.getlocal(10).__getattr__("close").__call__(var2);
         throw (Throwable)var11;
      }

      var1.setline(60);
      var1.getlocal(10).__getattr__("close").__call__(var2);
      var1.setline(62);
      PyObject var10000 = var1.getglobal("TextFile");
      var12 = new PyObject[]{var1.getlocal(9), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      String[] var4 = new String[]{"strip_comments", "skip_blanks", "lstrip_ws", "rstrip_ws"};
      var10000 = var10000.__call__(var2, var12, var4);
      var3 = null;
      var14 = var10000;
      var1.setlocal(11, var14);
      var3 = null;
      var3 = null;

      try {
         var1.setline(65);
         var1.getlocal(7).__call__(var2, Py.newInteger(1), PyString.fromInterned("no processing"), var1.getlocal(11), var1.getlocal(1));
      } catch (Throwable var10) {
         Py.addTraceback(var10, var1);
         var1.setline(67);
         var1.getlocal(11).__getattr__("close").__call__(var2);
         throw (Throwable)var10;
      }

      var1.setline(67);
      var1.getlocal(11).__getattr__("close").__call__(var2);
      var1.setline(69);
      var10000 = var1.getglobal("TextFile");
      var12 = new PyObject[]{var1.getlocal(9), Py.newInteger(1), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      var4 = new String[]{"strip_comments", "skip_blanks", "lstrip_ws", "rstrip_ws"};
      var10000 = var10000.__call__(var2, var12, var4);
      var3 = null;
      var14 = var10000;
      var1.setlocal(11, var14);
      var3 = null;
      var3 = null;

      try {
         var1.setline(72);
         var1.getlocal(7).__call__(var2, Py.newInteger(2), PyString.fromInterned("strip comments"), var1.getlocal(11), var1.getlocal(2));
      } catch (Throwable var9) {
         Py.addTraceback(var9, var1);
         var1.setline(74);
         var1.getlocal(11).__getattr__("close").__call__(var2);
         throw (Throwable)var9;
      }

      var1.setline(74);
      var1.getlocal(11).__getattr__("close").__call__(var2);
      var1.setline(76);
      var10000 = var1.getglobal("TextFile");
      var12 = new PyObject[]{var1.getlocal(9), Py.newInteger(0), Py.newInteger(1), Py.newInteger(0), Py.newInteger(0)};
      var4 = new String[]{"strip_comments", "skip_blanks", "lstrip_ws", "rstrip_ws"};
      var10000 = var10000.__call__(var2, var12, var4);
      var3 = null;
      var14 = var10000;
      var1.setlocal(11, var14);
      var3 = null;
      var3 = null;

      try {
         var1.setline(79);
         var1.getlocal(7).__call__(var2, Py.newInteger(3), PyString.fromInterned("strip blanks"), var1.getlocal(11), var1.getlocal(3));
      } catch (Throwable var8) {
         Py.addTraceback(var8, var1);
         var1.setline(81);
         var1.getlocal(11).__getattr__("close").__call__(var2);
         throw (Throwable)var8;
      }

      var1.setline(81);
      var1.getlocal(11).__getattr__("close").__call__(var2);
      var1.setline(83);
      var14 = var1.getglobal("TextFile").__call__(var2, var1.getlocal(9));
      var1.setlocal(11, var14);
      var3 = null;
      var3 = null;

      try {
         var1.setline(85);
         var1.getlocal(7).__call__(var2, Py.newInteger(4), PyString.fromInterned("default processing"), var1.getlocal(11), var1.getlocal(4));
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(87);
         var1.getlocal(11).__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(87);
      var1.getlocal(11).__getattr__("close").__call__(var2);
      var1.setline(89);
      var10000 = var1.getglobal("TextFile");
      var12 = new PyObject[]{var1.getlocal(9), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1)};
      var4 = new String[]{"strip_comments", "skip_blanks", "join_lines", "rstrip_ws"};
      var10000 = var10000.__call__(var2, var12, var4);
      var3 = null;
      var14 = var10000;
      var1.setlocal(11, var14);
      var3 = null;
      var3 = null;

      try {
         var1.setline(92);
         var1.getlocal(7).__call__(var2, Py.newInteger(5), PyString.fromInterned("join lines without collapsing"), var1.getlocal(11), var1.getlocal(5));
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(94);
         var1.getlocal(11).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(94);
      var1.getlocal(11).__getattr__("close").__call__(var2);
      var1.setline(96);
      var10000 = var1.getglobal("TextFile");
      var12 = new PyObject[]{var1.getlocal(9), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1)};
      var4 = new String[]{"strip_comments", "skip_blanks", "join_lines", "rstrip_ws", "collapse_join"};
      var10000 = var10000.__call__(var2, var12, var4);
      var3 = null;
      var14 = var10000;
      var1.setlocal(11, var14);
      var3 = null;
      var3 = null;

      try {
         var1.setline(99);
         var1.getlocal(7).__call__(var2, Py.newInteger(6), PyString.fromInterned("join lines with collapsing"), var1.getlocal(11), var1.getlocal(6));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(101);
         var1.getlocal(11).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(101);
      var1.getlocal(11).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_input$3(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyObject var3 = var1.getlocal(2).__getattr__("readlines").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(52);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$4(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("TextFileTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_text_file$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TextFileTestCase$1 = Py.newCode(0, var2, var1, "TextFileTestCase", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "result1", "result2", "result3", "result4", "result5", "result6", "test_input", "tmpdir", "filename", "out_file", "in_file"};
      String[] var10001 = var2;
      test_text_file$py var10007 = self;
      var2 = new String[]{"self"};
      test_class$2 = Py.newCode(1, var10001, var1, "test_class", 17, false, false, var10007, 2, var2, (String[])null, 0, 4097);
      var2 = new String[]{"count", "description", "file", "expected_result", "result"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      test_input$3 = Py.newCode(4, var10001, var1, "test_input", 50, false, false, var10007, 3, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      test_suite$4 = Py.newCode(0, var2, var1, "test_suite", 103, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_text_file$py("distutils/tests/test_text_file$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_text_file$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TextFileTestCase$1(var2, var3);
         case 2:
            return this.test_class$2(var2, var3);
         case 3:
            return this.test_input$3(var2, var3);
         case 4:
            return this.test_suite$4(var2, var3);
         default:
            return null;
      }
   }
}
