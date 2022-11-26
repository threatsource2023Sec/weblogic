package modjy;

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
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("modjy/modjy_write.py")
public class modjy_write$py extends PyFunctionTable implements PyRunnable {
   static modjy_write$py self;
   static final PyCode f$0;
   static final PyCode write_object$1;
   static final PyCode __init__$2;
   static final PyCode __call__$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(21);
      PyObject var3 = imp.importOne("types", var1, -1);
      var1.setlocal("types", var3);
      var3 = null;
      var1.setline(23);
      imp.importAll("modjy_exceptions", var1, -1);
      var1.setline(25);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var4 = Py.makeClass("write_object", var5, write_object$1);
      var1.setlocal("write_object", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write_object$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(27);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(31);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$3, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("ostream", var3);
      var3 = null;
      var1.setline(29);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"num_writes", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __call__$3(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._ne(Py.newInteger(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)), var1.getglobal("types").__getattr__("StringTypes")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(33);
         throw Py.makeException(var1.getglobal("NonStringOutput").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Invocation of write callable requires exactly one string argument")));
      } else {
         PyObject var4;
         try {
            var1.setline(35);
            var1.getlocal(0).__getattr__("ostream").__getattr__("write").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
            var1.setline(40);
            var1.getlocal(0).__getattr__("ostream").__getattr__("flush").__call__(var2);
            var1.setline(41);
            var10000 = var1.getlocal(0);
            String var8 = "num_writes";
            var4 = var10000;
            PyObject var5 = var4.__getattr__(var8);
            var5 = var5._iadd(Py.newInteger(1));
            var4.__setattr__(var8, var5);
         } catch (Throwable var6) {
            PyException var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("Exception"))) {
               var4 = var7.value;
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(43);
               throw Py.makeException(var1.getglobal("ModjyIOException").__call__(var2, var1.getlocal(3)));
            }

            throw var7;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public modjy_write$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      write_object$1 = Py.newCode(0, var2, var1, "write_object", 25, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "ostream"};
      __init__$2 = Py.newCode(2, var2, var1, "__init__", 27, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "keywords", "x"};
      __call__$3 = Py.newCode(3, var2, var1, "__call__", 31, true, true, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new modjy_write$py("modjy/modjy_write$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(modjy_write$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.write_object$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.__call__$3(var2, var3);
         default:
            return null;
      }
   }
}
