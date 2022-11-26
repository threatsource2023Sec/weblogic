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
@Filename("distutils/command/install_egg_info.py")
public class install_egg_info$py extends PyFunctionTable implements PyRunnable {
   static install_egg_info$py self;
   static final PyCode f$0;
   static final PyCode install_egg_info$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode run$4;
   static final PyCode get_outputs$5;
   static final PyCode safe_name$6;
   static final PyCode safe_version$7;
   static final PyCode to_filename$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.install_egg_info\n\nImplements the Distutils 'install_egg_info' command, for installing\na package's PKG-INFO metadata."));
      var1.setline(4);
      PyString.fromInterned("distutils.command.install_egg_info\n\nImplements the Distutils 'install_egg_info' command, for installing\na package's PKG-INFO metadata.");
      var1.setline(7);
      String[] var3 = new String[]{"Command"};
      PyObject[] var5 = imp.importFrom("distutils.cmd", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(8);
      var3 = new String[]{"log", "dir_util"};
      var5 = imp.importFrom("distutils", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("log", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("dir_util", var4);
      var4 = null;
      var1.setline(9);
      PyObject var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var6 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var6);
      var3 = null;
      var1.setline(11);
      var5 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("install_egg_info", var5, install_egg_info$1);
      var1.setlocal("install_egg_info", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.setline(55);
      var5 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var5, safe_name$6, PyString.fromInterned("Convert an arbitrary string to a standard distribution name\n\n    Any runs of non-alphanumeric/. characters are replaced with a single '-'.\n    "));
      var1.setlocal("safe_name", var7);
      var3 = null;
      var1.setline(63);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, safe_version$7, PyString.fromInterned("Convert an arbitrary string to a standard version string\n\n    Spaces become dots, and all other non-alphanumeric characters become\n    dashes, with runs of multiple dashes condensed to a single dash.\n    "));
      var1.setlocal("safe_version", var7);
      var3 = null;
      var1.setline(73);
      var5 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var5, to_filename$8, PyString.fromInterned("Convert a project or version name to its filename-escaped form\n\n    Any '-' characters are currently replaced with '_'.\n    "));
      var1.setlocal("to_filename", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject install_egg_info$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Install an .egg-info file for the package"));
      var1.setline(12);
      PyString.fromInterned("Install an .egg-info file for the package");
      var1.setline(14);
      PyString var3 = PyString.fromInterned("Install package's PKG-INFO metadata as an .egg-info file");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(15);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("install-dir="), PyString.fromInterned("d"), PyString.fromInterned("directory to install to")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(19);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(22);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(32);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$4, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(47);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_outputs$5, (PyObject)null);
      var1.setlocal("get_outputs", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_dir", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("install_lib"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("install_dir"), PyString.fromInterned("install_dir")})));
      var1.setline(24);
      PyObject var3 = PyString.fromInterned("%s-%s-py%s.egg-info")._mod(new PyTuple(new PyObject[]{var1.getglobal("to_filename").__call__(var2, var1.getglobal("safe_name").__call__(var2, var1.getlocal(0).__getattr__("distribution").__getattr__("get_name").__call__(var2))), var1.getglobal("to_filename").__call__(var2, var1.getglobal("safe_version").__call__(var2, var1.getlocal(0).__getattr__("distribution").__getattr__("get_version").__call__(var2))), var1.getglobal("sys").__getattr__("version").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null)}));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(29);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("install_dir"), var1.getlocal(1));
      var1.getlocal(0).__setattr__("target", var3);
      var3 = null;
      var1.setline(30);
      PyList var4 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("target")});
      var1.getlocal(0).__setattr__((String)"outputs", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$4(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      PyObject var3 = var1.getlocal(0).__getattr__("target");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(34);
      PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("islink").__call__(var2, var1.getlocal(1)).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(35);
         var10000 = var1.getglobal("dir_util").__getattr__("remove_tree");
         PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("dry_run")};
         String[] var4 = new String[]{"dry_run"};
         var10000.__call__(var2, var5, var4);
         var3 = null;
      } else {
         var1.setline(36);
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(37);
            var1.getlocal(0).__getattr__("execute").__call__((ThreadState)var2, var1.getglobal("os").__getattr__("unlink"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("target")})), (PyObject)PyString.fromInterned("Removing ")._add(var1.getlocal(1)));
         } else {
            var1.setline(38);
            if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(0).__getattr__("install_dir")).__not__().__nonzero__()) {
               var1.setline(39);
               var1.getlocal(0).__getattr__("execute").__call__((ThreadState)var2, var1.getglobal("os").__getattr__("makedirs"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("install_dir")})), (PyObject)PyString.fromInterned("Creating ")._add(var1.getlocal(0).__getattr__("install_dir")));
            }
         }
      }

      var1.setline(41);
      var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Writing %s"), (PyObject)var1.getlocal(1));
      var1.setline(42);
      if (var1.getlocal(0).__getattr__("dry_run").__not__().__nonzero__()) {
         var1.setline(43);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(44);
         var1.getlocal(0).__getattr__("distribution").__getattr__("metadata").__getattr__("write_pkg_file").__call__(var2, var1.getlocal(2));
         var1.setline(45);
         var1.getlocal(2).__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_outputs$5(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyObject var3 = var1.getlocal(0).__getattr__("outputs");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject safe_name$6(PyFrame var1, ThreadState var2) {
      var1.setline(59);
      PyString.fromInterned("Convert an arbitrary string to a standard distribution name\n\n    Any runs of non-alphanumeric/. characters are replaced with a single '-'.\n    ");
      var1.setline(60);
      PyObject var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("[^A-Za-z0-9.]+"), (PyObject)PyString.fromInterned("-"), (PyObject)var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject safe_version$7(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyString.fromInterned("Convert an arbitrary string to a standard version string\n\n    Spaces become dots, and all other non-alphanumeric characters become\n    dashes, with runs of multiple dashes condensed to a single dash.\n    ");
      var1.setline(69);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)PyString.fromInterned("."));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(70);
      var3 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("[^A-Za-z0-9.]+"), (PyObject)PyString.fromInterned("-"), (PyObject)var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject to_filename$8(PyFrame var1, ThreadState var2) {
      var1.setline(77);
      PyString.fromInterned("Convert a project or version name to its filename-escaped form\n\n    Any '-' characters are currently replaced with '_'.\n    ");
      var1.setline(78);
      PyObject var3 = var1.getlocal(0).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_"));
      var1.f_lasti = -1;
      return var3;
   }

   public install_egg_info$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      install_egg_info$1 = Py.newCode(0, var2, var1, "install_egg_info", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 19, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "basename"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 22, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "f"};
      run$4 = Py.newCode(1, var2, var1, "run", 32, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_outputs$5 = Py.newCode(1, var2, var1, "get_outputs", 47, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      safe_name$6 = Py.newCode(1, var2, var1, "safe_name", 55, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"version"};
      safe_version$7 = Py.newCode(1, var2, var1, "safe_version", 63, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      to_filename$8 = Py.newCode(1, var2, var1, "to_filename", 73, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new install_egg_info$py("distutils/command/install_egg_info$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(install_egg_info$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.install_egg_info$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.finalize_options$3(var2, var3);
         case 4:
            return this.run$4(var2, var3);
         case 5:
            return this.get_outputs$5(var2, var3);
         case 6:
            return this.safe_name$6(var2, var3);
         case 7:
            return this.safe_version$7(var2, var3);
         case 8:
            return this.to_filename$8(var2, var3);
         default:
            return null;
      }
   }
}
