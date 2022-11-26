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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_reduce.py")
public class fix_reduce$py extends PyFunctionTable implements PyRunnable {
   static fix_reduce$py self;
   static final PyCode f$0;
   static final PyCode FixReduce$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for reduce().\n\nMakes sure reduce() is imported from the functools module if reduce is\nused in that module.\n"));
      var1.setline(8);
      PyString.fromInterned("Fixer for reduce().\n\nMakes sure reduce() is imported from the functools module if reduce is\nused in that module.\n");
      var1.setline(10);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("lib2to3", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(11);
      var3 = new String[]{"touch_import"};
      var5 = imp.importFrom("lib2to3.fixer_util", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("touch_import", var4);
      var4 = null;
      var1.setline(15);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixReduce", var5, FixReduce$1);
      var1.setlocal("FixReduce", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixReduce$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(18);
      PyString var4 = PyString.fromInterned("pre");
      var1.setlocal("order", var4);
      var3 = null;
      var1.setline(20);
      var4 = PyString.fromInterned("\n    power< 'reduce'\n        trailer< '('\n            arglist< (\n                (not(argument<any '=' any>) any ','\n                 not(argument<any '=' any>) any) |\n                (not(argument<any '=' any>) any ','\n                 not(argument<any '=' any>) any ','\n                 not(argument<any '=' any>) any)\n            ) >\n        ')' >\n    >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(34);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      var1.getglobal("touch_import").__call__((ThreadState)var2, PyUnicode.fromInterned("functools"), (PyObject)PyUnicode.fromInterned("reduce"), (PyObject)var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public fix_reduce$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixReduce$1 = Py.newCode(0, var2, var1, "FixReduce", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 34, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_reduce$py("lib2to3/fixes/fix_reduce$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_reduce$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixReduce$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
