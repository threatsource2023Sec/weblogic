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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("_fsum.py")
public class _fsum$py extends PyFunctionTable implements PyRunnable {
   static _fsum$py self;
   static final PyCode f$0;
   static final PyCode fsum$1;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(3);
      String[] var3 = new String[]{"float_info"};
      PyObject[] var5 = imp.importFrom("sys", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("float_info", var4);
      var4 = null;
      var1.setline(4);
      PyObject var6 = imp.importOne("math", var1, -1);
      var1.setlocal("math", var6);
      var3 = null;
      var1.setline(7);
      var6 = var1.getname("float_info").__getattr__("mant_dig");
      var1.setlocal("mant_dig", var6);
      var3 = null;
      var1.setline(8);
      var6 = var1.getname("float_info").__getattr__("min_exp")._sub(var1.getname("mant_dig"));
      var1.setlocal("etiny", var6);
      var3 = null;
      var1.setline(10);
      var5 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var5, fsum$1, PyString.fromInterned("Full precision summation.  Compute sum(iterable) without any\n    intermediate accumulation of error.  Based on the 'lsum' function\n    at http://code.activestate.com/recipes/393090/\n\n    "));
      var1.setlocal("fsum", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fsum$1(PyFrame var1, ThreadState var2) {
      var1.setline(15);
      PyString.fromInterned("Full precision summation.  Compute sum(iterable) without any\n    intermediate accumulation of error.  Based on the 'lsum' function\n    at http://code.activestate.com/recipes/393090/\n\n    ");
      var1.setline(16);
      PyTuple var3 = new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(0)});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(17);
      PyObject var8 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(17);
         PyObject var9 = var8.__iternext__();
         PyObject var10000;
         if (var9 == null) {
            var1.setline(31);
            var8 = var1.getglobal("max").__call__(var2, var1.getglobal("len").__call__(var2, var1.getglobal("bin").__call__(var2, var1.getglobal("abs").__call__(var2, var1.getlocal(1))))._sub(Py.newInteger(2))._sub(var1.getglobal("mant_dig")), var1.getglobal("etiny")._sub(var1.getlocal(2)));
            var1.setlocal(6, var8);
            var3 = null;
            var1.setline(32);
            var8 = var1.getlocal(6);
            var10000 = var8._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(33);
               var8 = Py.newInteger(1)._lshift(var1.getlocal(6)._sub(Py.newInteger(1)));
               var1.setlocal(7, var8);
               var3 = null;
               var1.setline(34);
               var10000 = var1.getlocal(1)._floordiv(Py.newInteger(2)._mul(var1.getlocal(7)));
               PyObject var10001 = var1.getglobal("bool");
               PyObject var10003 = var1.getlocal(1)._and(var1.getlocal(7));
               if (var10003.__nonzero__()) {
                  var10003 = var1.getlocal(1)._and(Py.newInteger(3)._mul(var1.getlocal(7))._sub(Py.newInteger(1)));
               }

               var8 = var10000._add(var10001.__call__(var2, var10003));
               var1.setlocal(1, var8);
               var3 = null;
               var1.setline(35);
               var8 = var1.getlocal(2);
               var8 = var8._iadd(var1.getlocal(6));
               var1.setlocal(2, var8);
            }

            var1.setline(36);
            var8 = var1.getglobal("math").__getattr__("ldexp").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(3, var9);
         var1.setline(18);
         var5 = var1.getglobal("math").__getattr__("frexp").__call__(var2, var1.getlocal(3));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(4, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(5, var7);
         var7 = null;
         var5 = null;
         var1.setline(19);
         PyTuple var10 = new PyTuple(new PyObject[]{var1.getglobal("int").__call__(var2, var1.getglobal("math").__getattr__("ldexp").__call__(var2, var1.getlocal(4), var1.getglobal("mant_dig"))), var1.getlocal(5)._sub(var1.getglobal("mant_dig"))});
         var6 = Py.unpackSequence(var10, 2);
         var7 = var6[0];
         var1.setlocal(4, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(5, var7);
         var7 = null;
         var5 = null;
         var1.setline(20);
         var5 = var1.getlocal(2);
         var10000 = var5._gt(var1.getlocal(5));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(21);
            var5 = var1.getlocal(1);
            var5 = var5._ilshift(var1.getlocal(2)._sub(var1.getlocal(5)));
            var1.setlocal(1, var5);
            var1.setline(22);
            var5 = var1.getlocal(5);
            var1.setlocal(2, var5);
            var5 = null;
         } else {
            var1.setline(24);
            var5 = var1.getlocal(4);
            var5 = var5._ilshift(var1.getlocal(5)._sub(var1.getlocal(2)));
            var1.setlocal(4, var5);
         }

         var1.setline(25);
         var5 = var1.getlocal(1);
         var5 = var5._iadd(var1.getlocal(4));
         var1.setlocal(1, var5);
      }
   }

   public _fsum$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"iterable", "tmant", "texp", "x", "mant", "exp", "tail", "h"};
      fsum$1 = Py.newCode(1, var2, var1, "fsum", 10, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _fsum$py("_fsum$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_fsum$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.fsum$1(var2, var3);
         default:
            return null;
      }
   }
}
