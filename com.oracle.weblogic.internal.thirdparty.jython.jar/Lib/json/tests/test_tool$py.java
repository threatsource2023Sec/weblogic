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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("json/tests/test_tool.py")
public class test_tool$py extends PyFunctionTable implements PyRunnable {
   static test_tool$py self;
   static final PyCode f$0;
   static final PyCode TestTool$1;
   static final PyCode test_stdin_stdout$2;
   static final PyCode _create_infile$3;
   static final PyCode test_infile_stdout$4;
   static final PyCode test_infile_outfile$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("textwrap", var1, -1);
      var1.setlocal("textwrap", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("subprocess", var1, -1);
      var1.setlocal("subprocess", var3);
      var3 = null;
      var1.setline(6);
      String[] var5 = new String[]{"test_support"};
      PyObject[] var6 = imp.importFrom("test", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("test_support", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"assert_python_ok"};
      var6 = imp.importFrom("test.script_helper", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("assert_python_ok", var4);
      var4 = null;
      var1.setline(9);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("TestTool", var6, TestTool$1);
      var1.setlocal("TestTool", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestTool$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(10);
      PyString var3 = PyString.fromInterned("\n\n        [[\"blorpie\"],[ \"whoops\" ] , [\n                                 ],\t\"d-shtaeou\",\r\"d-nthiouh\",\n        \"i-vhbjkhnth\", {\"nifty\":87}, {\"morefield\" :\tfalse,\"field\"\n            :\"yes\"}  ]\n           ");
      var1.setlocal("data", var3);
      var3 = null;
      var1.setline(18);
      PyObject var4 = var1.getname("textwrap").__getattr__("dedent").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("    [\n        [\n            \"blorpie\"\n        ],\n        [\n            \"whoops\"\n        ],\n        [],\n        \"d-shtaeou\",\n        \"d-nthiouh\",\n        \"i-vhbjkhnth\",\n        {\n            \"nifty\": 87\n        },\n        {\n            \"field\": \"yes\",\n            \"morefield\": false\n        }\n    ]\n    "));
      var1.setlocal("expect", var4);
      var3 = null;
      var1.setline(40);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, test_stdin_stdout$2, (PyObject)null);
      var4 = var1.getname("unittest").__getattr__("skipIf").__call__((ThreadState)var2, (PyObject)var1.getname("test_support").__getattr__("is_jython"), (PyObject)PyString.fromInterned("Revisit when http://bugs.jython.org/issue695383 is fixed")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("test_stdin_stdout", var4);
      var3 = null;
      var1.setline(49);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _create_infile$3, (PyObject)null);
      var1.setlocal("_create_infile", var6);
      var3 = null;
      var1.setline(60);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_infile_stdout$4, (PyObject)null);
      var4 = var1.getname("unittest").__getattr__("skipIf").__call__((ThreadState)var2, (PyObject)var1.getname("test_support").__getattr__("is_jython"), (PyObject)PyString.fromInterned("Revisit when http://bugs.jython.org/issue695383 is fixed")).__call__((ThreadState)var2, (PyObject)var6);
      var1.setlocal("test_infile_stdout", var4);
      var3 = null;
      var1.setline(67);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, test_infile_outfile$5, (PyObject)null);
      var1.setlocal("test_infile_outfile", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_stdin_stdout$2(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var10000 = var1.getglobal("subprocess").__getattr__("Popen");
      PyObject[] var3 = new PyObject[]{new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("executable"), PyString.fromInterned("-m"), PyString.fromInterned("json.tool")}), var1.getglobal("subprocess").__getattr__("PIPE"), var1.getglobal("subprocess").__getattr__("PIPE")};
      String[] var4 = new String[]{"stdin", "stdout"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(45);
      var6 = var1.getlocal(1).__getattr__("communicate").__call__(var2, var1.getlocal(0).__getattr__("data").__getattr__("encode").__call__(var2));
      PyObject[] var7 = Py.unpackSequence(var6, 2);
      PyObject var5 = var7[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var7[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(46);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("splitlines").__call__(var2), var1.getlocal(0).__getattr__("expect").__getattr__("encode").__call__(var2).__getattr__("splitlines").__call__(var2));
      var1.setline(47);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3), var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _create_infile$3(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(50);
      PyObject var3 = var1.getglobal("test_support").__getattr__("TESTFN");
      var1.setlocal(1, var3);
      var3 = null;
      ContextManager var6;
      PyObject var4 = (var6 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w")))).__enter__(var2);

      label16: {
         try {
            var1.setlocal(2, var4);
            var1.setline(52);
            var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getglobal("os").__getattr__("remove"), var1.getlocal(1));
            var1.setline(53);
            var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("data"));
         } catch (Throwable var5) {
            if (var6.__exit__(var2, Py.setException(var5, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var6.__exit__(var2, (PyException)null);
      }

      var1.setline(54);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_infile_stdout$4(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyObject var3 = var1.getlocal(0).__getattr__("_create_infile").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(63);
      var3 = var1.getglobal("assert_python_ok").__call__((ThreadState)var2, PyString.fromInterned("-m"), (PyObject)PyString.fromInterned("json.tool"), (PyObject)var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(64);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("splitlines").__call__(var2), var1.getlocal(0).__getattr__("expect").__getattr__("encode").__call__(var2).__getattr__("splitlines").__call__(var2));
      var1.setline(65);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_infile_outfile$5(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(68);
      PyObject var3 = var1.getlocal(0).__getattr__("_create_infile").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(69);
      var3 = var1.getglobal("test_support").__getattr__("TESTFN")._add(PyString.fromInterned(".out"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(70);
      var3 = var1.getglobal("assert_python_ok").__call__(var2, PyString.fromInterned("-m"), PyString.fromInterned("json.tool"), var1.getlocal(1), var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(71);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getglobal("os").__getattr__("remove"), var1.getlocal(2));
      ContextManager var8;
      PyObject var7 = (var8 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("r")))).__enter__(var2);

      label16: {
         try {
            var1.setlocal(6, var7);
            var1.setline(73);
            var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(6).__getattr__("read").__call__(var2), var1.getlocal(0).__getattr__("expect"));
         } catch (Throwable var6) {
            if (var8.__exit__(var2, Py.setException(var6, var1))) {
               break label16;
            }

            throw (Throwable)Py.makeException();
         }

         var8.__exit__(var2, (PyException)null);
      }

      var1.setline(74);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned(""));
      var1.setline(75);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned(""));
      var1.f_lasti = -1;
      return Py.None;
   }

   public test_tool$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      TestTool$1 = Py.newCode(0, var2, var1, "TestTool", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "proc", "out", "err"};
      test_stdin_stdout$2 = Py.newCode(1, var2, var1, "test_stdin_stdout", 40, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "infile", "fp"};
      _create_infile$3 = Py.newCode(1, var2, var1, "_create_infile", 49, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "infile", "rc", "out", "err"};
      test_infile_stdout$4 = Py.newCode(1, var2, var1, "test_infile_stdout", 60, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "infile", "outfile", "rc", "out", "err", "fp"};
      test_infile_outfile$5 = Py.newCode(1, var2, var1, "test_infile_outfile", 67, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_tool$py("json/tests/test_tool$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_tool$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.TestTool$1(var2, var3);
         case 2:
            return this.test_stdin_stdout$2(var2, var3);
         case 3:
            return this._create_infile$3(var2, var3);
         case 4:
            return this.test_infile_stdout$4(var2, var3);
         case 5:
            return this.test_infile_outfile$5(var2, var3);
         default:
            return null;
      }
   }
}
