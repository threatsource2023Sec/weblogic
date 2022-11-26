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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("future_builtins.py")
public class future_builtins$py extends PyFunctionTable implements PyRunnable {
   static future_builtins$py self;
   static final PyCode f$0;
   static final PyCode hex$1;
   static final PyCode oct$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("This module provides functions that will be builtins in Python 3.0,\nbut that conflict with builtins that already exist in Python 2.x.\n\nFunctions:\n\nhex(arg) -- Returns the hexadecimal representation of an integer\noct(arg) -- Returns the octal representation of an integer\nascii(arg) -- Same as repr(arg)\nmap, filter, zip -- Same as itertools.imap, ifilter, izip\n\nThe typical usage of this module is to replace existing builtins in a\nmodule's namespace:\n\nfrom future_builtins import hex, oct\n"));
      var1.setline(15);
      PyString.fromInterned("This module provides functions that will be builtins in Python 3.0,\nbut that conflict with builtins that already exist in Python 2.x.\n\nFunctions:\n\nhex(arg) -- Returns the hexadecimal representation of an integer\noct(arg) -- Returns the octal representation of an integer\nascii(arg) -- Same as repr(arg)\nmap, filter, zip -- Same as itertools.imap, ifilter, izip\n\nThe typical usage of this module is to replace existing builtins in a\nmodule's namespace:\n\nfrom future_builtins import hex, oct\n");
      var1.setline(17);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("hex"), PyString.fromInterned("oct"), PyString.fromInterned("ascii"), PyString.fromInterned("map"), PyString.fromInterned("filter"), PyString.fromInterned("zip")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(19);
      String[] var5 = new String[]{"imap", "ifilter", "izip"};
      PyObject[] var6 = imp.importFrom("itertools", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("map", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("filter", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("zip", var4);
      var4 = null;
      var1.setline(21);
      PyObject var7 = var1.getname("repr");
      var1.setlocal("ascii", var7);
      var3 = null;
      var1.setline(22);
      var7 = var1.getname("hex");
      var1.setlocal("_builtin_hex", var7);
      var3 = null;
      var1.setline(23);
      var7 = var1.getname("oct");
      var1.setlocal("_builtin_oct", var7);
      var3 = null;
      var1.setline(25);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, hex$1, (PyObject)null);
      var1.setlocal("hex", var8);
      var3 = null;
      var1.setline(28);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, oct$2, (PyObject)null);
      var1.setlocal("oct", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject hex$1(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyObject var3 = var1.getglobal("_builtin_hex").__call__(var2, var1.getlocal(0)).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("L"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject oct$2(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getglobal("_builtin_oct").__call__(var2, var1.getlocal(0)).__getattr__("rstrip").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("L"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("0"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(31);
         PyString var5 = PyString.fromInterned("0o0");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(32);
         PyObject var4 = var1.getlocal(1).__getattr__("index").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("0"))._add(Py.newInteger(1));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(33);
         var3 = var1.getlocal(1).__getslice__((PyObject)null, var1.getlocal(2), (PyObject)null)._add(PyString.fromInterned("o"))._add(var1.getlocal(1).__getslice__(var1.getlocal(2), (PyObject)null, (PyObject)null));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public future_builtins$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"arg"};
      hex$1 = Py.newCode(1, var2, var1, "hex", 25, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"arg", "result", "i"};
      oct$2 = Py.newCode(1, var2, var1, "oct", 28, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new future_builtins$py("future_builtins$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(future_builtins$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.hex$1(var2, var3);
         case 2:
            return this.oct$2(var2, var3);
         default:
            return null;
      }
   }
}
