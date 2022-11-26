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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("lib2to3/fixes/fix_isinstance.py")
public class fix_isinstance$py extends PyFunctionTable implements PyRunnable {
   static fix_isinstance$py self;
   static final PyCode f$0;
   static final PyCode FixIsinstance$1;
   static final PyCode transform$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Fixer that cleans up a tuple argument to isinstance after the tokens\nin it were fixed.  This is mainly used to remove double occurrences of\ntokens as a leftover of the long -> int / unicode -> str conversion.\n\neg.  isinstance(x, (int, long)) -> isinstance(x, (int, int))\n       -> isinstance(x, int)\n"));
      var1.setline(10);
      PyString.fromInterned("Fixer that cleans up a tuple argument to isinstance after the tokens\nin it were fixed.  This is mainly used to remove double occurrences of\ntokens as a leftover of the long -> int / unicode -> str conversion.\n\neg.  isinstance(x, (int, long)) -> isinstance(x, (int, int))\n       -> isinstance(x, int)\n");
      var1.setline(12);
      String[] var3 = new String[]{"fixer_base"};
      PyObject[] var5 = imp.importFrom("", var3, var1, 2);
      PyObject var4 = var5[0];
      var1.setlocal("fixer_base", var4);
      var4 = null;
      var1.setline(13);
      var3 = new String[]{"token"};
      var5 = imp.importFrom("fixer_util", var3, var1, 2);
      var4 = var5[0];
      var1.setlocal("token", var4);
      var4 = null;
      var1.setline(16);
      var5 = new PyObject[]{var1.getname("fixer_base").__getattr__("BaseFix")};
      var4 = Py.makeClass("FixIsinstance", var5, FixIsinstance$1);
      var1.setlocal("FixIsinstance", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FixIsinstance$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      PyObject var3 = var1.getname("True");
      var1.setlocal("BM_compatible", var3);
      var3 = null;
      var1.setline(18);
      PyString var4 = PyString.fromInterned("\n    power<\n        'isinstance'\n        trailer< '(' arglist< any ',' atom< '('\n            args=testlist_gexp< any+ >\n        ')' > > ')' >\n    >\n    ");
      var1.setlocal("PATTERN", var4);
      var3 = null;
      var1.setline(27);
      PyInteger var5 = Py.newInteger(6);
      var1.setlocal("run_order", var5);
      var3 = null;
      var1.setline(29);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, transform$2, (PyObject)null);
      var1.setlocal("transform", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject transform$2(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getglobal("set").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(31);
      var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("args"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(32);
      var3 = var1.getlocal(4).__getattr__("children");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(33);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(6, var7);
      var3 = null;
      var1.setline(34);
      var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(5));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(35);
      var3 = var1.getlocal(7).__iter__();

      while(true) {
         var1.setline(35);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(44);
            var10000 = var1.getlocal(6);
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(6).__getitem__(Py.newInteger(-1)).__getattr__("type");
               var10000 = var3._eq(var1.getglobal("token").__getattr__("COMMA"));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(45);
               var1.getlocal(6).__delitem__((PyObject)Py.newInteger(-1));
            }

            var1.setline(46);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(47);
               var3 = var1.getlocal(4).__getattr__("parent");
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(48);
               var3 = var1.getlocal(10).__getattr__("prefix");
               var1.getlocal(6).__getitem__(Py.newInteger(0)).__setattr__("prefix", var3);
               var3 = null;
               var1.setline(49);
               var1.getlocal(10).__getattr__("replace").__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(0)));
            } else {
               var1.setline(51);
               var3 = var1.getlocal(6);
               var1.getlocal(5).__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var3);
               var3 = null;
               var1.setline(52);
               var1.getlocal(1).__getattr__("changed").__call__(var2);
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(8, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(9, var6);
         var6 = null;
         var1.setline(36);
         PyObject var8 = var1.getlocal(9).__getattr__("type");
         var10000 = var8._eq(var1.getglobal("token").__getattr__("NAME"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var8 = var1.getlocal(9).__getattr__("value");
            var10000 = var8._in(var1.getlocal(3));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(37);
            var8 = var1.getlocal(8);
            var10000 = var8._lt(var1.getglobal("len").__call__(var2, var1.getlocal(5))._sub(Py.newInteger(1)));
            var5 = null;
            if (var10000.__nonzero__()) {
               var8 = var1.getlocal(5).__getitem__(var1.getlocal(8)._add(Py.newInteger(1))).__getattr__("type");
               var10000 = var8._eq(var1.getglobal("token").__getattr__("COMMA"));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(38);
               var1.getlocal(7).__getattr__("next").__call__(var2);
            }
         } else {
            var1.setline(41);
            var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(9));
            var1.setline(42);
            var8 = var1.getlocal(9).__getattr__("type");
            var10000 = var8._eq(var1.getglobal("token").__getattr__("NAME"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(43);
               var1.getlocal(3).__getattr__("add").__call__(var2, var1.getlocal(9).__getattr__("value"));
            }
         }
      }
   }

   public fix_isinstance$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FixIsinstance$1 = Py.newCode(0, var2, var1, "FixIsinstance", 16, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "node", "results", "names_inserted", "testlist", "args", "new_args", "iterator", "idx", "arg", "atom"};
      transform$2 = Py.newCode(3, var2, var1, "transform", 29, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new fix_isinstance$py("lib2to3/fixes/fix_isinstance$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(fix_isinstance$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FixIsinstance$1(var2, var3);
         case 2:
            return this.transform$2(var2, var3);
         default:
            return null;
      }
   }
}
