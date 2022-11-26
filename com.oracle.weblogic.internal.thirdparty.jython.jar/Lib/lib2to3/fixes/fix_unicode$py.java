package lib2to3.fixes;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
@Filename("lib2to3/fixes/fix_unicode.py")
public class fix_unicode$py extends PyFunctionTable implements PyRunnable {
   static fix_unicode$py self;
   static final PyCode f$0;
   static final PyCode FixUnicode$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer that changes unicode to str, unichr to chr, and u\"...\" into \"...\".\n\n"));
      var1.setline(3);
      PyString.fromInterned("Fixer that changes unicode to str, unichr to chr, and u\"...\" into \"...\".\n\n");
      var1.setline(5);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(6);
      String[] var5 = new String[]{"token"};
      PyObject[] var6 = imp.importFrom("pgen2", var5, var1, 2);
      PyObject var4 = var6[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"fixer_base"};
      var6 = imp.importFrom("", var5, var1, 2);
      var4 = var6[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(9);
      PyDictionary var7 = new PyDictionary(new PyObject[]{PyUnicode.fromInterned("unichr"), PyUnicode.fromInterned("chr"), PyUnicode.fromInterned("unicode"), PyUnicode.fromInterned("str")});
      var1.setlocal("_mapping", var7);
      var3 = null;
      var1.setline(10);
      var3 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyUnicode.fromInterned("[uU][rR]?[\\'\\\"]"));
      var1.setlocal("_literal_re", var3);
      var3 = null;
      var1.setline(12);
      var6 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixUnicode", var6, FixUnicode$1);
      var1.setlocal("FixUnicode", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixUnicode$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(14);
      PyString var4 = PyString.fromInterned("STRING | 'unicode' | 'unichr'");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(16);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, transform$2, (PyObject)null);
      var1.setlocal("transform", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyObject var3 = var1.getlocal(1).__getattr__("type");
      PyObject var10000 = var3._eq(var1.getglobal("token").__getattr__("NAME"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(18);
         var3 = var1.getlocal(1).__getattr__("clone").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(19);
         var3 = var1.getglobal("_mapping").__getitem__(var1.getlocal(1).__getattr__("value"));
         var1.getlocal(3).__setattr__("value", var3);
         var3 = null;
         var1.setline(20);
         var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(21);
         PyObject var4 = var1.getlocal(1).__getattr__("type");
         var10000 = var4._eq(var1.getglobal("token").__getattr__("STRING"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(22);
            if (var1.getglobal("_literal_re").__getattr__("match").__call__(var2, var1.getlocal(1).__getattr__("value")).__nonzero__()) {
               var1.setline(23);
               var4 = var1.getlocal(1).__getattr__("clone").__call__(var2);
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(24);
               var4 = var1.getlocal(3).__getattr__("value").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.getlocal(3).__setattr__("value", var4);
               var4 = null;
               var1.setline(25);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public fix_unicode$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixUnicode$1 = Py.newCode(0, var2, var1, "FixUnicode", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "new"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 16, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_unicode$py("lib2to3/fixes/fix_unicode$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_unicode$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixUnicode$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
