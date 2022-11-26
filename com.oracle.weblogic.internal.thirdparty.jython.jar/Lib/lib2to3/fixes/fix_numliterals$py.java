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
@Filename("lib2to3/fixes/fix_numliterals.py")
public class fix_numliterals$py extends PyFunctionTable implements PyRunnable {
   static fix_numliterals$py self;
   static final PyCode f$0;
   static final PyCode FixNumliterals$1;
   static final PyCode match$2;
   static final PyCode transform$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer that turns 1L into 1, 0755 into 0o755.\n"));
      var1.setline(2);
      PyString.fromInterned("Fixer that turns 1L into 1, 0755 into 0o755.\n");
      var1.setline(7);
      String[] var3 = new String[]{"token"};
      PyObject[] var5 = imp.importFrom("pgen2", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(8);
      var3 = new String[]{"fixer_base"};
      var5 = imp.importFrom("", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(9);
      var3 = new String[]{"Number"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("Number", var4);
      var4 = null;
      var1.setline(12);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixNumliterals", var5, FixNumliterals$1);
      var1.setlocal("FixNumliterals", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixNumliterals$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(15);
      PyObject var3 = var1.getname("token").__getattr__("NUMBER");
      var1.setlocal("_accept_type", var3);
      var3 = null;
      var1.setline(17);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, match$2, (PyObject)null);
      var1.setlocal("match", var5);
      var3 = null;
      var1.setline(21);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, transform$3, (PyObject)null);
      var1.setlocal("transform", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject match$2(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyObject var10000 = var1.getlocal(1).__getattr__("value").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("0"));
      PyObject var3;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1).__getattr__("value").__getitem__(Py.newInteger(-1));
         var10000 = var3._in(PyUnicode.fromInterned("Ll"));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject transform$3(PyFrame var1, ThreadState var2) {
      var1.setline(22);
      PyObject var3 = var1.getlocal(1).__getattr__("value");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(23);
      var3 = var1.getlocal(3).__getitem__(Py.newInteger(-1));
      PyObject var10000 = var3._in(PyUnicode.fromInterned("Ll"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(24);
         var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(25);
         var10000 = var1.getlocal(3).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("0"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__getattr__("isdigit").__call__(var2);
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("len").__call__(var2, var1.getglobal("set").__call__(var2, var1.getlocal(3)));
               var10000 = var3._gt(Py.newInteger(1));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(26);
            var3 = PyUnicode.fromInterned("0o")._add(var1.getlocal(3).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var1.setlocal(3, var3);
            var3 = null;
         }
      }

      var1.setline(28);
      var10000 = var1.getglobal("Number");
      PyObject[] var5 = new PyObject[]{var1.getlocal(3), var1.getlocal(1).__getattr__("prefix")};
      String[] var4 = new String[]{"prefix"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public fix_numliterals$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixNumliterals$1 = Py.newCode(0, var2, var1, "FixNumliterals", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node"};
      match$2 = Py.newCode(2, var2, var1, "match", 17, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "node", "results", "val"};
      transform$3 = Py.newCode(3, var2, var1, "transform", 21, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_numliterals$py("lib2to3/fixes/fix_numliterals$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_numliterals$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixNumliterals$1(var2, var3);
         case 2:
            return this.match$2(var2, var3);
         case 3:
            return this.transform$3(var2, var3);
         default:
            return null;
      }
   }
}
