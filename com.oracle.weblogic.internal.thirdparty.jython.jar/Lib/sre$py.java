import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunctionTable;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("sre.py")
public class sre$py extends PyFunctionTable implements PyRunnable {
   static sre$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("This file is only retained for backwards compatibility.\nIt will be removed in the future.  sre was moved to re in version 2.5.\n"));
      var1.setline(3);
      PyString.fromInterned("This file is only retained for backwards compatibility.\nIt will be removed in the future.  sre was moved to re in version 2.5.\n");
      var1.setline(5);
      PyObject var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(6);
      var1.getname("warnings").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("The sre module is deprecated, please import re."), (PyObject)var1.getname("DeprecationWarning"), (PyObject)Py.newInteger(2));
      var1.setline(9);
      imp.importAll("re", var1, -1);
      var1.setline(10);
      String[] var5 = new String[]{"__all__"};
      PyObject[] var6 = imp.importFrom("re", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("__all__", var4);
      var4 = null;
      var1.setline(13);
      var5 = new String[]{"_compile"};
      var6 = imp.importFrom("re", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("_compile", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public sre$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new sre$py("sre$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(sre$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         default:
            return null;
      }
   }
}
