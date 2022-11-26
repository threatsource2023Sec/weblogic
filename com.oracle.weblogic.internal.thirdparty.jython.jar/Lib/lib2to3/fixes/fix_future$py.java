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
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_future.py")
public class fix_future$py extends PyFunctionTable implements PyRunnable {
   static fix_future$py self;
   static final PyCode f$0;
   static final PyCode FixFuture$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Remove __future__ imports\n\nfrom __future__ import foo is replaced with an empty line.\n"));
      var1.setline(4);
      PyString.fromInterned("Remove __future__ imports\n\nfrom __future__ import foo is replaced with an empty line.\n");
      var1.setline(8);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(9);
      var3 = new String[]{"BlankLine"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("BlankLine", var4);
      var4 = null;
      var1.setline(11);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixFuture", var5, FixFuture$1);
      var1.setlocal("FixFuture", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixFuture$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(12);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(14);
      PyString var4 = PyString.fromInterned("import_from< 'from' module_name=\"__future__\" 'import' any >");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(17);
      PyInteger var5 = Py.newInteger(10);
      var1.setlocal("run_order", var5);
      var3 = null;
      var1.setline(19);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, transform$2, (PyObject)null);
      var1.setlocal("transform", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyObject var3 = var1.getglobal("BlankLine").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(21);
      var3 = var1.getlocal(1).__getattr__("prefix");
      var1.getlocal(3).__setattr__("prefix", var3);
      var3 = null;
      var1.setline(22);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public fix_future$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixFuture$1 = Py.newCode(0, var2, var1, "FixFuture", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "new"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 19, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_future$py("lib2to3/fixes/fix_future$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_future$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixFuture$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
