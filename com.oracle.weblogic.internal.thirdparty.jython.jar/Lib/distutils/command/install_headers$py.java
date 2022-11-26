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
@MTime(1498849383000L)
@Filename("distutils/command/install_headers.py")
public class install_headers$py extends PyFunctionTable implements PyRunnable {
   static install_headers$py self;
   static final PyCode f$0;
   static final PyCode install_headers$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode run$4;
   static final PyCode get_inputs$5;
   static final PyCode get_outputs$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.install_headers\n\nImplements the Distutils 'install_headers' command, to install C/C++ header\nfiles to the Python include directory."));
      var1.setline(4);
      PyString.fromInterned("distutils.command.install_headers\n\nImplements the Distutils 'install_headers' command, to install C/C++ header\nfiles to the Python include directory.");
      var1.setline(6);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(8);
      String[] var5 = new String[]{"Command"};
      PyObject[] var6 = imp.importFrom("distutils.core", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(12);
      var6 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("install_headers", var6, install_headers$1);
      var1.setlocal("install_headers", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject install_headers$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(14);
      PyString var3 = PyString.fromInterned("install C/C++ header files");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(16);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("install-dir="), PyString.fromInterned("d"), PyString.fromInterned("directory to install header files to")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("f"), PyString.fromInterned("force installation (overwrite existing files)")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(22);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("force")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(24);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(29);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(35);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$4, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(45);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_inputs$5, (PyObject)null);
      var1.setlocal("get_inputs", var6);
      var3 = null;
      var1.setline(48);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_outputs$6, (PyObject)null);
      var1.setlocal("get_outputs", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_dir", var3);
      var3 = null;
      var1.setline(26);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"force", var4);
      var3 = null;
      var1.setline(27);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"outfiles", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, PyString.fromInterned("install"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("install_headers"), PyString.fromInterned("install_dir")})), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("force")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$4(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("headers");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(37);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(38);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(40);
         var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getlocal(0).__getattr__("install_dir"));
         var1.setline(41);
         var3 = var1.getlocal(1).__iter__();

         while(true) {
            var1.setline(41);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(42);
            PyObject var5 = var1.getlocal(0).__getattr__("copy_file").__call__(var2, var1.getlocal(2), var1.getlocal(0).__getattr__("install_dir"));
            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(3, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(4, var7);
            var7 = null;
            var5 = null;
            var1.setline(43);
            var1.getlocal(0).__getattr__("outfiles").__getattr__("append").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject get_inputs$5(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      Object var10000 = var1.getlocal(0).__getattr__("distribution").__getattr__("headers");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_outputs$6(PyFrame var1, ThreadState var2) {
      var1.setline(49);
      PyObject var3 = var1.getlocal(0).__getattr__("outfiles");
      var1.f_lasti = -1;
      return var3;
   }

   public install_headers$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      install_headers$1 = Py.newCode(0, var2, var1, "install_headers", 12, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 29, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "headers", "header", "out", "_"};
      run$4 = Py.newCode(1, var2, var1, "run", 35, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_inputs$5 = Py.newCode(1, var2, var1, "get_inputs", 45, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_outputs$6 = Py.newCode(1, var2, var1, "get_outputs", 48, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new install_headers$py("distutils/command/install_headers$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(install_headers$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.install_headers$1(var2, var3);
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
