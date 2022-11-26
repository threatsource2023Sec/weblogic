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
@Filename("lib2to3/fixes/fix_throw.py")
public class fix_throw$py extends PyFunctionTable implements PyRunnable {
   static fix_throw$py self;
   static final PyCode f$0;
   static final PyCode FixThrow$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer for generator.throw(E, V, T).\n\ng.throw(E)       -> g.throw(E)\ng.throw(E, V)    -> g.throw(E(V))\ng.throw(E, V, T) -> g.throw(E(V).with_traceback(T))\n\ng.throw(\"foo\"[, V[, T]]) will warn about string exceptions."));
      var1.setline(7);
      PyString.fromInterned("Fixer for generator.throw(E, V, T).\n\ng.throw(E)       -> g.throw(E)\ng.throw(E, V)    -> g.throw(E(V))\ng.throw(E, V, T) -> g.throw(E(V).with_traceback(T))\n\ng.throw(\"foo\"[, V[, T]]) will warn about string exceptions.");
      var1.setline(11);
      String[] var3 = new String[]{"pytree"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("pytree", var4);
      var4 = null;
      var1.setline(12);
      var3 = new String[]{"token"};
      var5 = imp.importFrom("pgen2", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(13);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(14);
      var3 = new String[]{"Name", "Call", "ArgList", "Attr", "is_tuple"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Name", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("Call", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("ArgList", var4);
      var4 = null;
      var4 = var5[3];
      var1.setlocal("Attr", var4);
      var4 = null;
      var4 = var5[4];
      var1.setlocal("is_tuple", var4);
      var4 = null;
      var1.setline(16);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixThrow", var5, FixThrow$1);
      var1.setlocal("FixThrow", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixThrow$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(18);
      PyString var4 = PyString.fromInterned("\n    power< any trailer< '.' 'throw' >\n           trailer< '(' args=arglist< exc=any ',' val=any [',' tb=any] > ')' >\n    >\n    |\n    power< any trailer< '.' 'throw' > trailer< '(' exc=any ')' > >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(26);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      PyObject var3 = var1.getlocal(0).__getattr__("syms");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(29);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("exc")).__getattr__("clone").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getlocal(4).__getattr__("type");
      PyObject var10000 = var3._is(var1.getglobal("token").__getattr__("STRING"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(31);
         var1.getlocal(0).__getattr__("cannot_convert").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("Python 3 does not support string exceptions"));
         var1.setline(32);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(35);
         var3 = var1.getlocal(2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("val"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(36);
         var3 = var1.getlocal(5);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(37);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(39);
            var3 = var1.getlocal(5).__getattr__("clone").__call__(var2);
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(40);
            PyList var5;
            PyUnicode var6;
            if (var1.getglobal("is_tuple").__call__(var2, var1.getlocal(5)).__nonzero__()) {
               var1.setline(41);
               PyList var8 = new PyList();
               var3 = var8.__getattr__("append");
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(41);
               var3 = var1.getlocal(5).__getattr__("children").__getslice__(Py.newInteger(1), Py.newInteger(-1), (PyObject)null).__iter__();

               while(true) {
                  var1.setline(41);
                  PyObject var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(41);
                     var1.dellocal(7);
                     var5 = var8;
                     var1.setlocal(6, var5);
                     var3 = null;
                     break;
                  }

                  var1.setlocal(8, var4);
                  var1.setline(41);
                  var1.getlocal(7).__call__(var2, var1.getlocal(8).__getattr__("clone").__call__(var2));
               }
            } else {
               var1.setline(43);
               var6 = PyUnicode.fromInterned("");
               var1.getlocal(5).__setattr__((String)"prefix", var6);
               var3 = null;
               var1.setline(44);
               var5 = new PyList(new PyObject[]{var1.getlocal(5)});
               var1.setlocal(6, var5);
               var3 = null;
            }

            var1.setline(46);
            var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("args"));
            var1.setlocal(9, var3);
            var3 = null;
            var1.setline(48);
            PyString var7 = PyString.fromInterned("tb");
            var10000 = var7._in(var1.getlocal(2));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(49);
               var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("tb")).__getattr__("clone").__call__(var2);
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(50);
               var6 = PyUnicode.fromInterned("");
               var1.getlocal(10).__setattr__((String)"prefix", var6);
               var3 = null;
               var1.setline(52);
               var3 = var1.getglobal("Call").__call__(var2, var1.getlocal(4), var1.getlocal(6));
               var1.setlocal(11, var3);
               var3 = null;
               var1.setline(53);
               var3 = var1.getglobal("Attr").__call__(var2, var1.getlocal(11), var1.getglobal("Name").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("with_traceback")))._add(new PyList(new PyObject[]{var1.getglobal("ArgList").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(10)})))}));
               var1.setlocal(12, var3);
               var3 = null;
               var1.setline(54);
               var1.getlocal(9).__getattr__("replace").__call__(var2, var1.getglobal("pytree").__getattr__("Node").__call__(var2, var1.getlocal(3).__getattr__("power"), var1.getlocal(12)));
            } else {
               var1.setline(56);
               var1.getlocal(9).__getattr__("replace").__call__(var2, var1.getglobal("Call").__call__(var2, var1.getlocal(4), var1.getlocal(6)));
            }

            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public fix_throw$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixThrow$1 = Py.newCode(0, var2, var1, "FixThrow", 16, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "syms", "exc", "val", "args", "_[41_20]", "c", "throw_args", "tb", "e", "with_tb"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 26, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_throw$py("lib2to3/fixes/fix_throw$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_throw$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixThrow$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
