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
@Filename("lib2to3/fixes/fix_input.py")
public class fix_input$py extends PyFunctionTable implements PyRunnable {
   static fix_input$py self;
   static final PyCode f$0;
   static final PyCode FixInput$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer that changes input(...) into eval(input(...))."));
      var1.setline(1);
      PyString.fromInterned("Fixer that changes input(...) into eval(input(...)).");
      var1.setline(5);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(6);
      var3 = new String[]{"Call", "Name"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Name", var4);
      var4 = null;
      var1.setline(7);
      var3 = new String[]{"patcomp"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("patcomp", var4);
      var4 = null;
      var1.setline(10);
      PyObject var6 = var1.getname("patcomp").__getattr__("compile_pattern").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("power< 'eval' trailer< '(' any ')' > >"));
      var1.setlocal("context", var6);
      var3 = null;
      var1.setline(13);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixInput", var5, FixInput$1);
      var1.setlocal("FixInput", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixInput$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(14);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(15);
      PyString var4 = PyString.fromInterned("\n              power< 'input' args=trailer< '(' [any] ')' > >\n              ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(19);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      if (var1.getglobal("context").__getattr__("match").__call__(var2, var1.getlocal(1).__getattr__("parent").__getattr__("parent")).__nonzero__()) {
         var1.setline(22);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(24);
         PyObject var3 = var1.getlocal(1).__getattr__("clone").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(25);
         PyUnicode var5 = PyUnicode.fromInterned("");
         var1.getlocal(3).__setattr__((String)"prefix", var5);
         var3 = null;
         var1.setline(26);
         PyObject var10000 = var1.getglobal("Call");
         PyObject[] var6 = new PyObject[]{var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("eval")), new PyList(new PyObject[]{var1.getlocal(3)}), var1.getlocal(1).__getattr__("prefix")};
         String[] var4 = new String[]{"prefix"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public fix_input$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixInput$1 = Py.newCode(0, var2, var1, "FixInput", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "new"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 19, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_input$py("lib2to3/fixes/fix_input$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_input$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixInput$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
