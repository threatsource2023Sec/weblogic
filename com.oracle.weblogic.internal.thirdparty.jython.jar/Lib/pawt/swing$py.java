package pawt;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("pawt/swing.py")
public class swing$py extends PyFunctionTable implements PyRunnable {
   static swing$py self;
   static final PyCode f$0;
   static final PyCode test$1;
   static final PyCode f$2;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nNo longer needed, but keeping for backwards compatibility.\n"));
      var1.setline(3);
      PyString.fromInterned("\nNo longer needed, but keeping for backwards compatibility.\n");
      var1.setline(4);
      String[] var3 = new String[]{"swing"};
      PyObject[] var5 = imp.importFrom("javax", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("swing", var4);
      var4 = null;
      var1.setline(5);
      PyObject var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(7);
      var5 = new PyObject[]{var1.getname("None"), PyString.fromInterned("Swing Tester")};
      PyFunction var7 = new PyFunction(var1.f_globals, var5, test$1, (PyObject)null);
      var1.setlocal("test", var7);
      var3 = null;
      var1.setline(20);
      var6 = var1.getname("swing");
      PyObject var10000 = var6._isnot(var1.getname("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(21);
         var6 = imp.importOne("pawt", var1, -1);
         var1.setlocal("pawt", var6);
         var3 = null;
         var6 = imp.importOne("sys", var1, -1);
         var1.setlocal("sys", var6);
         var3 = null;
         var1.setline(22);
         var6 = var1.getname("swing");
         var1.getname("pawt").__setattr__("swing", var6);
         var3 = null;
         var1.setline(23);
         var6 = var1.getname("swing");
         var1.getname("sys").__getattr__("modules").__setitem__((PyObject)PyString.fromInterned("pawt.swing"), var6);
         var3 = null;
         var1.setline(24);
         var6 = var1.getname("test");
         var1.getname("swing").__getattr__("__dict__").__setitem__((PyObject)PyString.fromInterned("test"), var6);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test$1(PyFrame var1, ThreadState var2) {
      var1.setline(8);
      PyObject var10000 = var1.getglobal("swing").__getattr__("JFrame");
      PyObject[] var3 = new PyObject[]{var1.getlocal(2), null};
      var1.setline(8);
      PyObject[] var4 = Py.EmptyObjects;
      var3[1] = new PyFunction(var1.f_globals, var4, f$2);
      String[] var6 = new String[]{"windowClosing"};
      var10000 = var10000.__call__(var2, var3, var6);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(9);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("init")).__nonzero__()) {
         var1.setline(10);
         var1.getlocal(0).__getattr__("init").__call__(var2);
      }

      var1.setline(12);
      var1.getlocal(3).__getattr__("contentPane").__getattr__("add").__call__(var2, var1.getlocal(0));
      var1.setline(13);
      var1.getlocal(3).__getattr__("pack").__call__(var2);
      var1.setline(14);
      var5 = var1.getlocal(1);
      var10000 = var5._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(15);
         String[] var8 = new String[]{"awt"};
         var3 = imp.importFrom("java", var8, var1, -1);
         PyObject var7 = var3[0];
         var1.setlocal(4, var7);
         var4 = null;
         var1.setline(16);
         var1.getlocal(3).__getattr__("setSize").__call__(var2, var1.getglobal("apply").__call__(var2, var1.getlocal(4).__getattr__("Dimension"), var1.getlocal(1)));
      }

      var1.setline(17);
      var1.getlocal(3).__getattr__("setVisible").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setline(18);
      var5 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject f$2(PyFrame var1, ThreadState var2) {
      var1.setline(8);
      PyObject var3 = var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public swing$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"panel", "size", "name", "f", "awt"};
      test$1 = Py.newCode(3, var2, var1, "test", 7, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"event"};
      f$2 = Py.newCode(1, var2, var1, "<lambda>", 8, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new swing$py("pawt/swing$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(swing$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.test$1(var2, var3);
         case 2:
            return this.f$2(var2, var3);
         default:
            return null;
      }
   }
}
