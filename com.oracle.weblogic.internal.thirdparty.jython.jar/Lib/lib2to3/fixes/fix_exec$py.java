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
@Filename("lib2to3/fixes/fix_exec.py")
public class fix_exec$py extends PyFunctionTable implements PyRunnable {
   static fix_exec$py self;
   static final PyCode f$0;
   static final PyCode FixExec$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for exec.\n\nThis converts usages of the exec statement into calls to a built-in\nexec() function.\n\nexec code in ns1, ns2 -> exec(code, ns1, ns2)\n"));
      var1.setline(10);
      PyString.fromInterned("Fixer for exec.\n\nThis converts usages of the exec statement into calls to a built-in\nexec() function.\n\nexec code in ns1, ns2 -> exec(code, ns1, ns2)\n");
      var1.setline(13);
      String[] var3 = new String[]{"pytree"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(14);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(15);
      var3 = new String[]{"Comma", "Name", "Call"};
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
      var1.setline(18);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixExec", var5, FixExec$1);
      var1.setlocal("FixExec", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixExec$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(19);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(21);
      PyString var4 = PyString.fromInterned("\n    exec_stmt< 'exec' a=any 'in' b=any [',' c=any] >\n    |\n    exec_stmt< 'exec' (not atom<'(' [any] ')'>) a=any >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(27);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(2).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(29);
         PyObject var3 = var1.getlocal(0).__getattr__("syms");
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(30);
         var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("a"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(31);
         var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("b"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(32);
         var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("c"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(33);
         PyList var5 = new PyList(new PyObject[]{var1.getlocal(4).__getattr__("clone").__call__(var2)});
         var1.setlocal(7, var5);
         var3 = null;
         var1.setline(34);
         PyString var6 = PyString.fromInterned("");
         var1.getlocal(7).__getitem__(Py.newInteger(0)).__setattr__((String)"prefix", var6);
         var3 = null;
         var1.setline(35);
         var3 = var1.getlocal(5);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(36);
            var1.getlocal(7).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("Comma").__call__(var2), var1.getlocal(5).__getattr__("clone").__call__(var2)})));
         }

         var1.setline(37);
         var3 = var1.getlocal(6);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(38);
            var1.getlocal(7).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("Comma").__call__(var2), var1.getlocal(6).__getattr__("clone").__call__(var2)})));
         }

         var1.setline(40);
         var10000 = var1.getglobal("Call");
         PyObject[] var7 = new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("exec")), var1.getlocal(7), var1.getlocal(1).__getattr__("prefix")};
         String[] var4 = new String[]{"prefix"};
         var10000 = var10000.__call__(var2, var7, var4);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public fix_exec$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixExec$1 = Py.newCode(0, var2, var1, "FixExec", 18, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "syms", "a", "b", "c", "args"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 27, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_exec$py("lib2to3/fixes/fix_exec$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_exec$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixExec$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
