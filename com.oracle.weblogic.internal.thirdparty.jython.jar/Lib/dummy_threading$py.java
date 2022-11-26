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
@Filename("dummy_threading.py")
public class dummy_threading$py extends PyFunctionTable implements PyRunnable {
   static dummy_threading$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Faux ``threading`` version using ``dummy_thread`` instead of ``thread``.\n\nThe module ``_dummy_threading`` is added to ``sys.modules`` in order\nto not have ``threading`` considered imported.  Had ``threading`` been\ndirectly imported it would have made all subsequent imports succeed\nregardless of whether ``thread`` was available which is not desired.\n\n"));
      var1.setline(8);
      PyString.fromInterned("Faux ``threading`` version using ``dummy_thread`` instead of ``thread``.\n\nThe module ``_dummy_threading`` is added to ``sys.modules`` in order\nto not have ``threading`` considered imported.  Had ``threading`` been\ndirectly imported it would have made all subsequent imports succeed\nregardless of whether ``thread`` was available which is not desired.\n\n");
      var1.setline(9);
      String[] var3 = new String[]{"modules"};
      PyObject[] var7 = imp.importFrom("sys", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("sys_modules", var4);
      var4 = null;
      var1.setline(11);
      PyObject var8 = imp.importOne("dummy_thread", var1, -1);
      var1.setlocal("dummy_thread", var8);
      var3 = null;
      var1.setline(14);
      var8 = var1.getname("False");
      var1.setlocal("holding_thread", var8);
      var3 = null;
      var1.setline(15);
      var8 = var1.getname("False");
      var1.setlocal("holding_threading", var8);
      var3 = null;
      var1.setline(16);
      var8 = var1.getname("False");
      var1.setlocal("holding__threading_local", var8);
      var3 = null;
      var3 = null;

      try {
         var1.setline(22);
         PyString var9 = PyString.fromInterned("thread");
         PyObject var10000 = var9._in(var1.getname("sys_modules"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(23);
            var4 = var1.getname("sys_modules").__getitem__(PyString.fromInterned("thread"));
            var1.setlocal("held_thread", var4);
            var4 = null;
            var1.setline(24);
            var4 = var1.getname("True");
            var1.setlocal("holding_thread", var4);
            var4 = null;
         }

         var1.setline(27);
         var4 = var1.getname("sys_modules").__getitem__(PyString.fromInterned("dummy_thread"));
         var1.getname("sys_modules").__setitem__((PyObject)PyString.fromInterned("thread"), var4);
         var4 = null;
         var1.setline(29);
         var9 = PyString.fromInterned("threading");
         var10000 = var9._in(var1.getname("sys_modules"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(33);
            var4 = var1.getname("sys_modules").__getitem__(PyString.fromInterned("threading"));
            var1.setlocal("held_threading", var4);
            var4 = null;
            var1.setline(34);
            var4 = var1.getname("True");
            var1.setlocal("holding_threading", var4);
            var4 = null;
            var1.setline(35);
            var1.getname("sys_modules").__delitem__((PyObject)PyString.fromInterned("threading"));
         }

         var1.setline(37);
         var9 = PyString.fromInterned("_threading_local");
         var10000 = var9._in(var1.getname("sys_modules"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(41);
            var4 = var1.getname("sys_modules").__getitem__(PyString.fromInterned("_threading_local"));
            var1.setlocal("held__threading_local", var4);
            var4 = null;
            var1.setline(42);
            var4 = var1.getname("True");
            var1.setlocal("holding__threading_local", var4);
            var4 = null;
            var1.setline(43);
            var1.getname("sys_modules").__delitem__((PyObject)PyString.fromInterned("_threading_local"));
         }

         var1.setline(45);
         var4 = imp.importOne("threading", var1, -1);
         var1.setlocal("threading", var4);
         var4 = null;
         var1.setline(47);
         var4 = var1.getname("sys_modules").__getitem__(PyString.fromInterned("threading"));
         var1.getname("sys_modules").__setitem__((PyObject)PyString.fromInterned("_dummy_threading"), var4);
         var4 = null;
         var1.setline(48);
         var1.getname("sys_modules").__delitem__((PyObject)PyString.fromInterned("threading"));
         var1.setline(49);
         var4 = var1.getname("sys_modules").__getitem__(PyString.fromInterned("_threading_local"));
         var1.getname("sys_modules").__setitem__((PyObject)PyString.fromInterned("_dummy__threading_local"), var4);
         var4 = null;
         var1.setline(50);
         var1.getname("sys_modules").__delitem__((PyObject)PyString.fromInterned("_threading_local"));
         var1.setline(51);
         imp.importAll("_dummy_threading", var1, -1);
         var1.setline(52);
         String[] var10 = new String[]{"__all__"};
         PyObject[] var11 = imp.importFrom("_dummy_threading", var10, var1, -1);
         PyObject var5 = var11[0];
         var1.setlocal("__all__", var5);
         var5 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(57);
         if (var1.getname("holding_threading").__nonzero__()) {
            var1.setline(58);
            var4 = var1.getname("held_threading");
            var1.getname("sys_modules").__setitem__((PyObject)PyString.fromInterned("threading"), var4);
            var4 = null;
            var1.setline(59);
            var1.dellocal("held_threading");
         }

         var1.setline(60);
         var1.dellocal("holding_threading");
         var1.setline(64);
         if (var1.getname("holding__threading_local").__nonzero__()) {
            var1.setline(65);
            var4 = var1.getname("held__threading_local");
            var1.getname("sys_modules").__setitem__((PyObject)PyString.fromInterned("_threading_local"), var4);
            var4 = null;
            var1.setline(66);
            var1.dellocal("held__threading_local");
         }

         var1.setline(67);
         var1.dellocal("holding__threading_local");
         var1.setline(70);
         if (var1.getname("holding_thread").__nonzero__()) {
            var1.setline(71);
            var4 = var1.getname("held_thread");
            var1.getname("sys_modules").__setitem__((PyObject)PyString.fromInterned("thread"), var4);
            var4 = null;
            var1.setline(72);
            var1.dellocal("held_thread");
         } else {
            var1.setline(74);
            var1.getname("sys_modules").__delitem__((PyObject)PyString.fromInterned("thread"));
         }

         var1.setline(75);
         var1.dellocal("holding_thread");
         var1.setline(77);
         var1.dellocal("dummy_thread");
         var1.setline(78);
         var1.dellocal("sys_modules");
         throw (Throwable)var6;
      }

      var1.setline(57);
      if (var1.getname("holding_threading").__nonzero__()) {
         var1.setline(58);
         var4 = var1.getname("held_threading");
         var1.getname("sys_modules").__setitem__((PyObject)PyString.fromInterned("threading"), var4);
         var4 = null;
         var1.setline(59);
         var1.dellocal("held_threading");
      }

      var1.setline(60);
      var1.dellocal("holding_threading");
      var1.setline(64);
      if (var1.getname("holding__threading_local").__nonzero__()) {
         var1.setline(65);
         var4 = var1.getname("held__threading_local");
         var1.getname("sys_modules").__setitem__((PyObject)PyString.fromInterned("_threading_local"), var4);
         var4 = null;
         var1.setline(66);
         var1.dellocal("held__threading_local");
      }

      var1.setline(67);
      var1.dellocal("holding__threading_local");
      var1.setline(70);
      if (var1.getname("holding_thread").__nonzero__()) {
         var1.setline(71);
         var4 = var1.getname("held_thread");
         var1.getname("sys_modules").__setitem__((PyObject)PyString.fromInterned("thread"), var4);
         var4 = null;
         var1.setline(72);
         var1.dellocal("held_thread");
      } else {
         var1.setline(74);
         var1.getname("sys_modules").__delitem__((PyObject)PyString.fromInterned("thread"));
      }

      var1.setline(75);
      var1.dellocal("holding_thread");
      var1.setline(77);
      var1.dellocal("dummy_thread");
      var1.setline(78);
      var1.dellocal("sys_modules");
      var1.f_lasti = -1;
      return Py.None;
   }

   public dummy_threading$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new dummy_threading$py("dummy_threading$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(dummy_threading$py.class);
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
