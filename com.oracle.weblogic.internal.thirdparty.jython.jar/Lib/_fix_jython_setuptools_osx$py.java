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
@Filename("_fix_jython_setuptools_osx.py")
public class _fix_jython_setuptools_osx$py extends PyFunctionTable implements PyRunnable {
   static _fix_jython_setuptools_osx$py self;
   static final PyCode f$0;
   static final PyCode _jython_as_header$1;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nImport of this module is triggered by org.python.core.imp.import_next\non first import of setuptools.command. It essentially restores a\nJython specific fix for OSX shebang line via monkeypatching.\n\nSee http://bugs.jython.org/issue2570\nRelated: http://bugs.jython.org/issue1112\n"));
      var1.setline(8);
      PyString.fromInterned("\nImport of this module is triggered by org.python.core.imp.import_next\non first import of setuptools.command. It essentially restores a\nJython specific fix for OSX shebang line via monkeypatching.\n\nSee http://bugs.jython.org/issue2570\nRelated: http://bugs.jython.org/issue1112\n");
      var1.setline(10);
      String[] var3 = new String[]{"easy_install"};
      PyObject[] var5 = imp.importFrom("setuptools.command", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("ez", var4);
      var4 = null;
      var1.setline(12);
      PyObject var6 = var1.getname("ez").__getattr__("CommandSpec").__getattr__("as_header");
      var1.setlocal("_as_header", var6);
      var3 = null;
      var1.setline(14);
      var5 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var5, _jython_as_header$1, PyString.fromInterned("Workaround Jython's sys.executable being a .sh (an invalid\n    shebang line interpreter)\n    "));
      var1.setlocal("_jython_as_header", var7);
      var3 = null;
      var1.setline(33);
      var6 = var1.getname("_jython_as_header");
      var1.getname("ez").__getattr__("CommandSpec").__setattr__("as_header", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _jython_as_header$1(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyString.fromInterned("Workaround Jython's sys.executable being a .sh (an invalid\n    shebang line interpreter)\n    ");
      var1.setline(18);
      PyObject var3;
      if (var1.getglobal("ez").__getattr__("is_sh").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0))).__not__().__nonzero__()) {
         var1.setline(19);
         var3 = var1.getglobal("_as_header").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(21);
         if (var1.getlocal(0).__getattr__("options").__nonzero__()) {
            var1.setline(23);
            var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("WARNING: Unable to adapt shebang line for Jython, the following script is NOT executable\n         see http://bugs.jython.org/issue1112 for more information."));
            var1.setline(28);
            var3 = var1.getglobal("_as_header").__call__(var2, var1.getlocal(0));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(30);
            PyObject var4 = (new PyList(new PyObject[]{PyString.fromInterned("/usr/bin/env")}))._add(var1.getlocal(0))._add(var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("options")));
            var1.setlocal(1, var4);
            var4 = null;
            var1.setline(31);
            var3 = var1.getlocal(0).__getattr__("_render").__call__(var2, var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public _fix_jython_setuptools_osx$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "items"};
      _jython_as_header$1 = Py.newCode(1, var2, var1, "_jython_as_header", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new _fix_jython_setuptools_osx$py("_fix_jython_setuptools_osx$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(_fix_jython_setuptools_osx$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._jython_as_header$1(var2, var3);
         default:
            return null;
      }
   }
}
