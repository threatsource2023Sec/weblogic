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
@Filename("lib2to3/fixes/fix_intern.py")
public class fix_intern$py extends PyFunctionTable implements PyRunnable {
   static fix_intern$py self;
   static final PyCode f$0;
   static final PyCode FixIntern$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for intern().\n\nintern(s) -> sys.intern(s)"));
      var1.setline(6);
      PyString.fromInterned("Fixer for intern().\n\nintern(s) -> sys.intern(s)");
      var1.setline(9);
      String[] var3 = new String[]{"pytree"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(10);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(11);
      var3 = new String[]{"Name", "Attr", "touch_import"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Attr", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("touch_import", var4);
      var4 = null;
      var1.setline(14);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixIntern", var5, FixIntern$1);
      var1.setlocal("FixIntern", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixIntern$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(15);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(16);
      PyString var4 = PyString.fromInterned("pre");
      var1.setlocal("order", var4);
      var3 = null;
      var1.setline(18);
      var4 = PyString.fromInterned("\n    power< 'intern'\n           trailer< lpar='('\n                    ( not(arglist | argument<any '=' any>) obj=any\n                      | obj=arglist<(not argument<any '=' any>) any ','> )\n                    rpar=')' >\n           after=any*\n    >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(28);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getlocal(0).__getattr__("syms");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("obj")).__getattr__("clone").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(31);
      var3 = var1.getlocal(4).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getlocal(3).__getattr__("arglist"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(32);
         var3 = var1.getlocal(4).__getattr__("clone").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(34);
         var3 = var1.getglobal("pytree").__getattr__("Node").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("arglist"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(4).__getattr__("clone").__call__(var2)})));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(35);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("after"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(36);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(37);
         PyList var6 = new PyList();
         var3 = var6.__getattr__("append");
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(37);
         var3 = var1.getlocal(6).__iter__();

         while(true) {
            var1.setline(37);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(37);
               var1.dellocal(7);
               PyList var5 = var6;
               var1.setlocal(6, var5);
               var3 = null;
               break;
            }

            var1.setlocal(8, var4);
            var1.setline(37);
            var1.getlocal(7).__call__(var2, var1.getlocal(8).__getattr__("clone").__call__(var2));
         }
      }

      var1.setline(38);
      var3 = var1.getglobal("pytree").__getattr__("Node").__call__(var2, var1.getlocal(3).__getattr__("power"), var1.getglobal("Attr").__call__(var2, var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("sys")), var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("intern")))._add(new PyList(new PyObject[]{var1.getglobal("pytree").__getattr__("Node").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("trailer"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(2).__getitem__(PyString.fromInterned("lpar")).__getattr__("clone").__call__(var2), var1.getlocal(5), var1.getlocal(2).__getitem__(PyString.fromInterned("rpar")).__getattr__("clone").__call__(var2)})))}))._add(var1.getlocal(6)));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(44);
      var3 = var1.getlocal(1).__getattr__("prefix");
      var1.getlocal(9).__setattr__("prefix", var3);
      var3 = null;
      var1.setline(45);
      var1.getglobal("touch_import").__call__((ThreadState)var2, var1.getglobal("None"), (PyObject)PyUnicode.fromInterned("sys"), (PyObject)var1.getlocal(1));
      var1.setline(46);
      var3 = var1.getlocal(9);
      var1.f_lasti = -1;
      return var3;
   }

   public fix_intern$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixIntern$1 = Py.newCode(0, var2, var1, "FixIntern", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "syms", "obj", "newarglist", "after", "_[37_21]", "n", "new"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 28, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_intern$py("lib2to3/fixes/fix_intern$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_intern$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixIntern$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
