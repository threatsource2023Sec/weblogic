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
@Filename("crypt.py")
public class crypt$py extends PyFunctionTable implements PyRunnable {
   static crypt$py self;
   static final PyCode f$0;
   static final PyCode crypt$1;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      String[] var3 = new String[]{"POSIXFactory"};
      PyObject[] var5 = imp.importFrom("jnr.posix", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("POSIXFactory", var4);
      var4 = null;
      var1.setline(2);
      var3 = new String[]{"PythonPOSIXHandler"};
      var5 = imp.importFrom("org.python.modules.posix", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("PythonPOSIXHandler", var4);
      var4 = null;
      var1.setline(5);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("crypt")});
      var1.setlocal("__all__", var6);
      var3 = null;
      var1.setline(6);
      PyObject var7 = var1.getname("POSIXFactory").__getattr__("getPOSIX").__call__(var2, var1.getname("PythonPOSIXHandler").__call__(var2), var1.getname("True"));
      var1.setlocal("_posix", var7);
      var3 = null;
      var1.setline(9);
      var5 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var5, crypt$1, (PyObject)null);
      var1.setlocal("crypt", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject crypt$1(PyFrame var1, ThreadState var2) {
      var1.setline(10);
      PyObject var3 = var1.getglobal("_posix").__getattr__("crypt").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public crypt$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"word", "salt"};
      crypt$1 = Py.newCode(2, var2, var1, "crypt", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new crypt$py("crypt$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(crypt$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.crypt$1(var2, var3);
         default:
            return null;
      }
   }
}
