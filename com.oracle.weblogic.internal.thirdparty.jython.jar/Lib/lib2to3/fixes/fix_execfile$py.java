package lib2to3.fixes;

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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_execfile.py")
public class fix_execfile$py extends PyFunctionTable implements PyRunnable {
   static fix_execfile$py self;
   static final PyCode f$0;
   static final PyCode FixExecfile$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for execfile.\n\nThis converts usages of the execfile function into calls to the built-in\nexec() function.\n"));
      var1.setline(8);
      PyString.fromInterned("Fixer for execfile.\n\nThis converts usages of the execfile function into calls to the built-in\nexec() function.\n");
      var1.setline(10);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(11);
      var3 = new String[]{"Comma", "Name", "Call", "LParen", "RParen", "Dot", "Node", "ArgList", "String", "syms"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Comma", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("LParen", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("RParen", var4);
      var4 = null;
      var4 = var5[5];
      var1.setlocal("Dot", var4);
      var4 = null;
      var4 = var5[6];
      var1.setlocal("Node", var4);
      var4 = null;
      var4 = var5[7];
      var1.setlocal("ArgList", var4);
      var4 = null;
      var4 = var5[8];
      var1.setlocal("String", var4);
      var4 = null;
      var4 = var5[9];
      var1.setlocal("syms", var4);
      var4 = null;
      var1.setline(15);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixExecfile", var5, FixExecfile$1);
      var1.setlocal("FixExecfile", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixExecfile$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(18);
      PyString var4 = PyString.fromInterned("\n    power< 'execfile' trailer< '(' arglist< filename=any [',' globals=any [',' locals=any ] ] > ')' > >\n    |\n    power< 'execfile' trailer< '(' filename=any ')' > >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(24);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(2).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(26);
         PyObject var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("filename"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(27);
         var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("globals"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(28);
         var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("locals"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(32);
         var3 = var1.getlocal(1).__getattr__("children").__getitem__(Py.newInteger(-1)).__getattr__("children").__getitem__(Py.newInteger(-1)).__getattr__("clone").__call__(var2);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(34);
         var10000 = var1.getglobal("ArgList");
         PyObject[] var5 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(3).__getattr__("clone").__call__(var2)}), var1.getlocal(6)};
         String[] var4 = new String[]{"rparen"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(35);
         var3 = var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("power"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("open")), var1.getlocal(7)})));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(36);
         PyList var6 = new PyList(new PyObject[]{var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("trailer"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("Dot").__call__(var2), var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("read"))}))), var1.getglobal("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("trailer"), (PyObject)(new PyList(new PyObject[]{var1.getglobal("LParen").__call__(var2), var1.getglobal("RParen").__call__(var2)})))});
         var1.setlocal(9, var6);
         var3 = null;
         var1.setline(38);
         var3 = (new PyList(new PyObject[]{var1.getlocal(8)}))._add(var1.getlocal(9));
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(41);
         var3 = var1.getlocal(3).__getattr__("clone").__call__(var2);
         var1.setlocal(11, var3);
         var3 = null;
         var1.setline(42);
         PyUnicode var7 = PyUnicode.fromInterned(" ");
         var1.getlocal(11).__setattr__((String)"prefix", var7);
         var3 = null;
         var1.setline(43);
         var3 = var1.getglobal("String").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("'exec'"), (PyObject)PyUnicode.fromInterned(" "));
         var1.setlocal(12, var3);
         var3 = null;
         var1.setline(44);
         var3 = var1.getlocal(10)._add(new PyList(new PyObject[]{var1.getglobal("Comma").__call__(var2), var1.getlocal(11), var1.getglobal("Comma").__call__(var2), var1.getlocal(12)}));
         var1.setlocal(13, var3);
         var3 = null;
         var1.setline(45);
         var3 = var1.getglobal("Call").__call__((ThreadState)var2, var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("compile")), (PyObject)var1.getlocal(13), (PyObject)PyUnicode.fromInterned(""));
         var1.setlocal(14, var3);
         var3 = null;
         var1.setline(47);
         var6 = new PyList(new PyObject[]{var1.getlocal(14)});
         var1.setlocal(15, var6);
         var3 = null;
         var1.setline(48);
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(49);
            var1.getlocal(15).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("Comma").__call__(var2), var1.getlocal(4).__getattr__("clone").__call__(var2)})));
         }

         var1.setline(50);
         var3 = var1.getlocal(5);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(51);
            var1.getlocal(15).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("Comma").__call__(var2), var1.getlocal(5).__getattr__("clone").__call__(var2)})));
         }

         var1.setline(52);
         var10000 = var1.getglobal("Call");
         var5 = new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("exec")), var1.getlocal(15), var1.getlocal(1).__getattr__("prefix")};
         var4 = new String[]{"prefix"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public fix_execfile$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixExecfile$1 = Py.newCode(0, var2, var1, "FixExecfile", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "filename", "globals", "locals", "execfile_paren", "open_args", "open_call", "read", "open_expr", "filename_arg", "exec_str", "compile_args", "compile_call", "args"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_execfile$py("lib2to3/fixes/fix_execfile$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_execfile$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixExecfile$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
