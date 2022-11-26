package unittest;

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
@Filename("unittest/__main__.py")
public class __main__$py extends PyFunctionTable implements PyRunnable {
   static __main__$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Main entry point"));
      var1.setline(1);
      PyString.fromInterned("Main entry point");
      var1.setline(3);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(4);
      if (var1.getname("sys").__getattr__("argv").__getitem__(Py.newInteger(0)).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__main__.py")).__nonzero__()) {
         var1.setline(5);
         PyString var5 = PyString.fromInterned("python -m unittest");
         var1.getname("sys").__getattr__("argv").__setitem__((PyObject)Py.newInteger(0), var5);
         var3 = null;
      }

      var1.setline(7);
      var3 = var1.getname("True");
      var1.setlocal("__unittest", var3);
      var3 = null;
      var1.setline(9);
      String[] var6 = new String[]{"main", "TestProgram", "USAGE_AS_MAIN"};
      PyObject[] var7 = imp.importFrom("main", var6, var1, 1);
      PyObject var4 = var7[0];
      var1.setlocal("main", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("TestProgram", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("USAGE_AS_MAIN", var4);
      var4 = null;
      var1.setline(10);
      var3 = var1.getname("USAGE_AS_MAIN");
      var1.getname("TestProgram").__setattr__("USAGE", var3);
      var3 = null;
      var1.setline(12);
      PyObject var10000 = var1.getname("main");
      var7 = new PyObject[]{var1.getname("None")};
      String[] var8 = new String[]{"module"};
      var10000.__call__(var2, var7, var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public __main__$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new __main__$py("unittest/__main__$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(__main__$py.class);
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
