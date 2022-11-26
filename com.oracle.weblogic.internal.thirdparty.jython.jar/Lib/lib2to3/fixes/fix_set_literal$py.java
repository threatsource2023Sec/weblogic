package lib2to3.fixes;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
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
@Filename("lib2to3/fixes/fix_set_literal.py")
public class fix_set_literal$py extends PyFunctionTable implements PyRunnable {
   static fix_set_literal$py self;
   static final PyCode f$0;
   static final PyCode FixSetLiteral$1;
   static final PyCode transform$2;
   static final PyCode f$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nOptional fixer to transform set() calls to set literals.\n"));
      var1.setline(3);
      PyString.fromInterned("\nOptional fixer to transform set() calls to set literals.\n");
      var1.setline(7);
      String[] var3 = new String[]{"fixer_base", "pytree"};
      PyObject[] var5 = imp.importFrom("lib2to3", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(8);
      var3 = new String[]{"token", "syms"};
      var5 = imp.importFrom("lib2to3.fixer_util", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("syms", var4);
      var4 = null;
      var1.setline(12);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixSetLiteral", var5, FixSetLiteral$1);
      var1.setlocal("FixSetLiteral", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixSetLiteral$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(14);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(15);
      var3 = var1.getname("True");
      var1.setlocal("explicit", var3);
      var3 = null;
      var1.setline(17);
      PyString var4 = PyString.fromInterned("power< 'set' trailer< '('\n                     (atom=atom< '[' (items=listmaker< any ((',' any)* [',']) >\n                                |\n                                single=any) ']' >\n                     |\n                     atom< '(' items=testlist_gexp< any ((',' any)* [',']) > ')' >\n                     )\n                     ')' > >\n              ");
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
      PyObject var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("single"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(29);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(31);
         var3 = var1.getglobal("pytree").__getattr__("Node").__call__((ThreadState)var2, (PyObject)var1.getglobal("syms").__getattr__("listmaker"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(3).__getattr__("clone").__call__(var2)})));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(32);
         var1.getlocal(3).__getattr__("replace").__call__(var2, var1.getlocal(4));
         var1.setline(33);
         var3 = var1.getlocal(4);
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(35);
         var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("items"));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(38);
      PyList var5 = new PyList(new PyObject[]{var1.getglobal("pytree").__getattr__("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("LBRACE"), (PyObject)PyUnicode.fromInterned("{"))});
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(39);
      PyObject var10000 = var1.getlocal(6).__getattr__("extend");
      var1.setline(39);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var6, f$3, (PyObject)null);
      PyObject var10002 = var4.__call__(var2, var1.getlocal(5).__getattr__("children").__iter__());
      Arrays.fill(var6, (Object)null);
      var10000.__call__(var2, var10002);
      var1.setline(40);
      var1.getlocal(6).__getattr__("append").__call__(var2, var1.getglobal("pytree").__getattr__("Leaf").__call__((ThreadState)var2, (PyObject)var1.getglobal("token").__getattr__("RBRACE"), (PyObject)PyUnicode.fromInterned("}")));
      var1.setline(42);
      var3 = var1.getlocal(5).__getattr__("next_sibling").__getattr__("prefix");
      var1.getlocal(6).__getitem__(Py.newInteger(-1)).__setattr__("prefix", var3);
      var3 = null;
      var1.setline(43);
      var3 = var1.getglobal("pytree").__getattr__("Node").__call__(var2, var1.getglobal("syms").__getattr__("dictsetmaker"), var1.getlocal(6));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(44);
      var3 = var1.getlocal(1).__getattr__("prefix");
      var1.getlocal(8).__setattr__("prefix", var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(8).__getattr__("children"));
      var10000 = var3._eq(Py.newInteger(4));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(48);
         var3 = var1.getlocal(8).__getattr__("children").__getitem__(Py.newInteger(2));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(49);
         var1.getlocal(9).__getattr__("remove").__call__(var2);
         var1.setline(50);
         var3 = var1.getlocal(9).__getattr__("prefix");
         var1.getlocal(8).__getattr__("children").__getitem__(Py.newInteger(-1)).__setattr__("prefix", var3);
         var3 = null;
      }

      var1.setline(53);
      var3 = var1.getlocal(8);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$3(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(39);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(39);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(39);
         var1.setline(39);
         var6 = var1.getlocal(1).__getattr__("clone").__call__(var2);
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public fix_set_literal$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixSetLiteral$1 = Py.newCode(0, var2, var1, "FixSetLiteral", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "single", "fake", "items", "literal", "_(39_23)", "maker", "n"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 27, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "n"};
      f$3 = Py.newCode(1, var2, var1, "<genexpr>", 39, false, false, self, 3, (String[])null, (String[])null, 0, 4129);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_set_literal$py("lib2to3/fixes/fix_set_literal$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_set_literal$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixSetLiteral$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         case 3:
            return this.f$3(var2, var3);
         default:
            return null;
      }
   }
}
