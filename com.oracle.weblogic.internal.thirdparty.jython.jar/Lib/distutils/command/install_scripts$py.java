package distutils.command;

import java.util.Arrays;
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
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("distutils/command/install_scripts.py")
public class install_scripts$py extends PyFunctionTable implements PyRunnable {
   static install_scripts$py self;
   static final PyCode f$0;
   static final PyCode install_scripts$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode run$4;
   static final PyCode get_inputs$5;
   static final PyCode get_outputs$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.install_scripts\n\nImplements the Distutils 'install_scripts' command, for installing\nPython scripts."));
      var1.setline(4);
      PyString.fromInterned("distutils.command.install_scripts\n\nImplements the Distutils 'install_scripts' command, for installing\nPython scripts.");
      var1.setline(8);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(10);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(11);
      String[] var6 = new String[]{"Command"};
      PyObject[] var7 = imp.importFrom("distutils.core", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"ST_MODE"};
      var7 = imp.importFrom("stat", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("ST_MODE", var4);
      var4 = null;
      var1.setline(15);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("install_scripts", var7, install_scripts$1);
      var1.setlocal("install_scripts", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject install_scripts$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      PyString var3 = PyString.fromInterned("install scripts (Python or otherwise)");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(19);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("install-dir="), PyString.fromInterned("d"), PyString.fromInterned("directory to install scripts to")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-dir="), PyString.fromInterned("b"), PyString.fromInterned("build directory (where to install from)")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("f"), PyString.fromInterned("force installation (overwrite existing files)")}), new PyTuple(new PyObject[]{PyString.fromInterned("skip-build"), var1.getname("None"), PyString.fromInterned("skip the build steps")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(26);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("skip-build")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(29);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(35);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(43);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$4, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(58);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_inputs$5, (PyObject)null);
      var1.setlocal("get_inputs", var6);
      var3 = null;
      var1.setline(61);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_outputs$6, (PyObject)null);
      var1.setlocal("get_outputs", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_dir", var3);
      var3 = null;
      var1.setline(31);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"force", var4);
      var3 = null;
      var1.setline(32);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_dir", var3);
      var3 = null;
      var1.setline(33);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("skip_build", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("build_scripts"), PyString.fromInterned("build_dir")})));
      var1.setline(37);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__(var2, PyString.fromInterned("install"), new PyTuple(new PyObject[]{PyString.fromInterned("install_scripts"), PyString.fromInterned("install_dir")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("force")}), new PyTuple(new PyObject[]{PyString.fromInterned("skip_build"), PyString.fromInterned("skip_build")}));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$4(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      if (var1.getlocal(0).__getattr__("skip_build").__not__().__nonzero__()) {
         var1.setline(45);
         var1.getlocal(0).__getattr__("run_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_scripts"));
      }

      var1.setline(46);
      PyObject var3 = var1.getlocal(0).__getattr__("copy_tree").__call__(var2, var1.getlocal(0).__getattr__("build_dir"), var1.getlocal(0).__getattr__("install_dir"));
      var1.getlocal(0).__setattr__("outfiles", var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("posix"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(50);
         var3 = var1.getlocal(0).__getattr__("get_outputs").__call__(var2).__iter__();

         while(true) {
            var1.setline(50);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(1, var4);
            var1.setline(51);
            if (var1.getlocal(0).__getattr__("dry_run").__nonzero__()) {
               var1.setline(52);
               var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("changing mode of %s"), (PyObject)var1.getlocal(1));
            } else {
               var1.setline(54);
               PyObject var5 = var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(1)).__getitem__(var1.getglobal("ST_MODE"))._or(Py.newInteger(365))._and(Py.newInteger(4095));
               var1.setlocal(2, var5);
               var5 = null;
               var1.setline(55);
               var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, PyString.fromInterned("changing mode of %s to %o"), (PyObject)var1.getlocal(1), (PyObject)var1.getlocal(2));
               var1.setline(56);
               var1.getglobal("os").__getattr__("chmod").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_inputs$5(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      Object var10000 = var1.getlocal(0).__getattr__("distribution").__getattr__("scripts");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_outputs$6(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      Object var10000 = var1.getlocal(0).__getattr__("outfiles");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public install_scripts$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      install_scripts$1 = Py.newCode(0, var2, var1, "install_scripts", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 29, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 35, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "file", "mode"};
      run$4 = Py.newCode(1, var2, var1, "run", 43, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_inputs$5 = Py.newCode(1, var2, var1, "get_inputs", 58, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_outputs$6 = Py.newCode(1, var2, var1, "get_outputs", 61, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new install_scripts$py("distutils/command/install_scripts$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(install_scripts$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.install_scripts$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.finalize_options$3(var2, var3);
         case 4:
            return this.run$4(var2, var3);
         case 5:
            return this.get_inputs$5(var2, var3);
         case 6:
            return this.get_outputs$6(var2, var3);
         default:
            return null;
      }
   }
}
