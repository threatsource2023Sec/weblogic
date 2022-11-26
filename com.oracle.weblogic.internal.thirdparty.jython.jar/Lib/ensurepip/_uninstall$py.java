package ensurepip;

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
@MTime(1498849383000L)
@Filename("ensurepip/_uninstall.py")
public class _uninstall$py extends PyFunctionTable implements PyRunnable {
   static _uninstall$py self;
   static final PyCode f$0;
   static final PyCode _main$1;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Basic pip uninstallation support, helper for the Windows uninstaller"));
      var1.setline(1);
      PyString.fromInterned("Basic pip uninstallation support, helper for the Windows uninstaller");
      var1.setline(3);
      PyObject var3 = imp.importOne("argparse", var1, -1);
      var1.setlocal("argparse", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("ensurepip", var1, -1);
      var1.setlocal("ensurepip", var3);
      var3 = null;
      var1.setline(7);
      PyObject[] var4 = new PyObject[]{var1.getname("None")};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, _main$1, (PyObject)null);
      var1.setlocal("_main", var5);
      var3 = null;
      var1.setline(29);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(30);
         var1.getname("_main").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _main$1(PyFrame var1, ThreadState var2) {
      var1.setline(8);
      PyObject var10000 = var1.getglobal("argparse").__getattr__("ArgumentParser");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("python -m ensurepip._uninstall")};
      String[] var4 = new String[]{"prog"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(9);
      var10000 = var1.getlocal(1).__getattr__("add_argument");
      var3 = new PyObject[]{PyString.fromInterned("--version"), PyString.fromInterned("version"), PyString.fromInterned("pip {}").__getattr__("format").__call__(var2, var1.getglobal("ensurepip").__getattr__("version").__call__(var2)), PyString.fromInterned("Show the version of pip this will attempt to uninstall.")};
      var4 = new String[]{"action", "version", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(15);
      var10000 = var1.getlocal(1).__getattr__("add_argument");
      var3 = new PyObject[]{PyString.fromInterned("-v"), PyString.fromInterned("--verbose"), PyString.fromInterned("count"), Py.newInteger(0), PyString.fromInterned("verbosity"), PyString.fromInterned("Give more output. Option is additive, and can be used up to 3 times.")};
      var4 = new String[]{"action", "default", "dest", "help"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(24);
      var5 = var1.getlocal(1).__getattr__("parse_args").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(26);
      var10000 = var1.getglobal("ensurepip").__getattr__("_uninstall_helper");
      var3 = new PyObject[]{var1.getlocal(2).__getattr__("verbosity")};
      var4 = new String[]{"verbosity"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public _uninstall$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"argv", "parser", "args"};
      _main$1 = Py.newCode(1, var2, var1, "_main", 7, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _uninstall$py("ensurepip/_uninstall$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_uninstall$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._main$1(var2, var3);
         default:
            return null;
      }
   }
}
