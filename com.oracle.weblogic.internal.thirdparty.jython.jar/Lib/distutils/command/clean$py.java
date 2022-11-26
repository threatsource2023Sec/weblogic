package distutils.command;

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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/command/clean.py")
public class clean$py extends PyFunctionTable implements PyRunnable {
   static clean$py self;
   static final PyCode f$0;
   static final PyCode clean$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode run$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.clean\n\nImplements the Distutils 'clean' command."));
      var1.setline(3);
      PyString.fromInterned("distutils.command.clean\n\nImplements the Distutils 'clean' command.");
      var1.setline(7);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(9);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(10);
      String[] var6 = new String[]{"Command"};
      PyObject[] var7 = imp.importFrom("distutils.core", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"remove_tree"};
      var7 = imp.importFrom("distutils.dir_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("remove_tree", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(14);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("clean", var7, clean$1);
      var1.setlocal("clean", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject clean$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      PyString var3 = PyString.fromInterned("clean up temporary files from 'build' command");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(17);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("build-base="), PyString.fromInterned("b"), PyString.fromInterned("base build directory (default: 'build.build-base')")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-lib="), var1.getname("None"), PyString.fromInterned("build directory for all modules (default: 'build.build-lib')")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-temp="), PyString.fromInterned("t"), PyString.fromInterned("temporary build directory (default: 'build.build-temp')")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-scripts="), var1.getname("None"), PyString.fromInterned("build directory for scripts (default: 'build.build-scripts')")}), new PyTuple(new PyObject[]{PyString.fromInterned("bdist-base="), var1.getname("None"), PyString.fromInterned("temporary directory for built distributions")}), new PyTuple(new PyObject[]{PyString.fromInterned("all"), PyString.fromInterned("a"), PyString.fromInterned("remove all build output, not just temporary by-products")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(32);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("all")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(34);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(42);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(51);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$4, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_base", var3);
      var3 = null;
      var1.setline(36);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_lib", var3);
      var3 = null;
      var1.setline(37);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_temp", var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_scripts", var3);
      var3 = null;
      var1.setline(39);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("bdist_base", var3);
      var3 = null;
      var1.setline(40);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("all", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(43);
      PyObject var10000 = var1.getlocal(0).__getattr__("set_undefined_options");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("build"), new PyTuple(new PyObject[]{PyString.fromInterned("build_base"), PyString.fromInterned("build_base")}), new PyTuple(new PyObject[]{PyString.fromInterned("build_lib"), PyString.fromInterned("build_lib")}), new PyTuple(new PyObject[]{PyString.fromInterned("build_scripts"), PyString.fromInterned("build_scripts")}), new PyTuple(new PyObject[]{PyString.fromInterned("build_temp"), PyString.fromInterned("build_temp")})};
      var10000.__call__(var2, var3);
      var1.setline(48);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bdist"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("bdist_base"), PyString.fromInterned("bdist_base")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$4(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyObject var10000;
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(0).__getattr__("build_temp")).__nonzero__()) {
         var1.setline(55);
         var10000 = var1.getglobal("remove_tree");
         PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("build_temp"), var1.getlocal(0).__getattr__("dry_run")};
         String[] var4 = new String[]{"dry_run"};
         var10000.__call__(var2, var3, var4);
         var3 = null;
      } else {
         var1.setline(57);
         var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'%s' does not exist -- can't clean it"), (PyObject)var1.getlocal(0).__getattr__("build_temp"));
      }

      var1.setline(60);
      if (var1.getlocal(0).__getattr__("all").__nonzero__()) {
         var1.setline(62);
         PyObject var8 = (new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("build_lib"), var1.getlocal(0).__getattr__("bdist_base"), var1.getlocal(0).__getattr__("build_scripts")})).__iter__();

         while(true) {
            var1.setline(62);
            PyObject var10 = var8.__iternext__();
            if (var10 == null) {
               break;
            }

            var1.setlocal(1, var10);
            var1.setline(65);
            if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__nonzero__()) {
               var1.setline(66);
               var10000 = var1.getglobal("remove_tree");
               PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("dry_run")};
               String[] var6 = new String[]{"dry_run"};
               var10000.__call__(var2, var5, var6);
               var5 = null;
            } else {
               var1.setline(68);
               var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'%s' does not exist -- can't clean it"), (PyObject)var1.getlocal(1));
            }
         }
      }

      var1.setline(73);
      if (var1.getlocal(0).__getattr__("dry_run").__not__().__nonzero__()) {
         try {
            var1.setline(75);
            var1.getglobal("os").__getattr__("rmdir").__call__(var2, var1.getlocal(0).__getattr__("build_base"));
            var1.setline(76);
            var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("removing '%s'"), (PyObject)var1.getlocal(0).__getattr__("build_base"));
         } catch (Throwable var7) {
            PyException var9 = Py.setException(var7, var1);
            if (!var9.match(var1.getglobal("OSError"))) {
               throw var9;
            }

            var1.setline(78);
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public clean$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      clean$1 = Py.newCode(0, var2, var1, "clean", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 34, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 42, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "directory"};
      run$4 = Py.newCode(1, var2, var1, "run", 51, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new clean$py("distutils/command/clean$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(clean$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.clean$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.finalize_options$3(var2, var3);
         case 4:
            return this.run$4(var2, var3);
         default:
            return null;
      }
   }
}
