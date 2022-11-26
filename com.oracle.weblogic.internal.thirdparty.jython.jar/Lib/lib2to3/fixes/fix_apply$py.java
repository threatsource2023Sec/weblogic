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
import org.python.core.PyTuple;
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_apply.py")
public class fix_apply$py extends PyFunctionTable implements PyRunnable {
   static fix_apply$py self;
   static final PyCode f$0;
   static final PyCode FixApply$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for apply().\n\nThis converts apply(func, v, k) into (func)(*v, **k)."));
      var1.setline(6);
      PyString.fromInterned("Fixer for apply().\n\nThis converts apply(func, v, k) into (func)(*v, **k).");
      var1.setline(9);
      String[] var3 = new String[]{"pytree"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(10);
      var3 = new String[]{"token"};
      var5 = imp.importFrom("pgen2", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(11);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(12);
      var3 = new String[]{"Call", "Comma", "parenthesize"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Comma", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("parenthesize", var4);
      var4 = null;
      var1.setline(14);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixApply", var5, FixApply$1);
      var1.setlocal("FixApply", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixApply$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(15);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(17);
      PyString var4 = PyString.fromInterned("\n    power< 'apply'\n        trailer<\n            '('\n            arglist<\n                (not argument<NAME '=' any>) func=any ','\n                (not argument<NAME '=' any>) args=any [','\n                (not argument<NAME '=' any>) kwds=any] [',']\n            >\n            ')'\n        >\n    >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(31);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyObject var3 = var1.getlocal(0).__getattr__("syms");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(33);
      PyObject var10000;
      if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(2).__nonzero__()) {
         var10000 = Py.None;
         throw Py.makeException(var1.getglobal("AssertionError"), var10000);
      } else {
         var1.setline(34);
         var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("func"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(35);
         var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("args"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(36);
         var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("kwds"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(37);
         var3 = var1.getlocal(1).__getattr__("prefix");
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(38);
         var3 = var1.getlocal(4).__getattr__("clone").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(39);
         var3 = var1.getlocal(4).__getattr__("type");
         var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("token").__getattr__("NAME"), var1.getlocal(3).__getattr__("atom")}));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(4).__getattr__("type");
            var10000 = var3._ne(var1.getlocal(3).__getattr__("power"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(4).__getattr__("children").__getitem__(Py.newInteger(-2)).__getattr__("type");
               var10000 = var3._eq(var1.getglobal("token").__getattr__("DOUBLESTAR"));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(43);
            var3 = var1.getglobal("parenthesize").__call__(var2, var1.getlocal(4));
            var1.setlocal(4, var3);
            var3 = null;
         }

         var1.setline(44);
         PyString var5 = PyString.fromInterned("");
         var1.getlocal(4).__setattr__((String)"prefix", var5);
         var3 = null;
         var1.setline(45);
         var3 = var1.getlocal(5).__getattr__("clone").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(46);
         var5 = PyString.fromInterned("");
         var1.getlocal(5).__setattr__((String)"prefix", var5);
         var3 = null;
         var1.setline(47);
         var3 = var1.getlocal(6);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(48);
            var3 = var1.getlocal(6).__getattr__("clone").__call__(var2);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(49);
            var5 = PyString.fromInterned("");
            var1.getlocal(6).__setattr__((String)"prefix", var5);
            var3 = null;
         }

         var1.setline(50);
         PyList var6 = new PyList(new PyObject[]{var1.getglobal("pytree").__getattr__("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("STAR"), (PyObject)PyUnicode.fromInterned("*")), var1.getlocal(5)});
         var1.setlocal(8, var6);
         var3 = null;
         var1.setline(51);
         var3 = var1.getlocal(6);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(52);
            var1.getlocal(8).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("Comma").__call__(var2), var1.getglobal("pytree").__getattr__("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("DOUBLESTAR"), (PyObject)PyUnicode.fromInterned("**")), var1.getlocal(6)})));
            var1.setline(55);
            PyUnicode var7 = PyUnicode.fromInterned(" ");
            var1.getlocal(8).__getitem__(Py.newInteger(-2)).__setattr__((String)"prefix", var7);
            var3 = null;
         }

         var1.setline(59);
         var10000 = var1.getglobal("Call");
         PyObject[] var8 = new PyObject[]{var1.getlocal(4), var1.getlocal(8), var1.getlocal(7)};
         String[] var4 = new String[]{"prefix"};
         var10000 = var10000.__call__(var2, var8, var4);
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      }
   }

   public fix_apply$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixApply$1 = Py.newCode(0, var2, var1, "FixApply", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "syms", "func", "args", "kwds", "prefix", "l_newargs"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 31, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_apply$py("lib2to3/fixes/fix_apply$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_apply$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixApply$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
