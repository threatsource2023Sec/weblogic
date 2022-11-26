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
@Filename("distutils/command/install_data.py")
public class install_data$py extends PyFunctionTable implements PyRunnable {
   static install_data$py self;
   static final PyCode f$0;
   static final PyCode install_data$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode run$4;
   static final PyCode get_inputs$5;
   static final PyCode get_outputs$6;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.install_data\n\nImplements the Distutils 'install_data' command, for installing\nplatform-independent data files."));
      var1.setline(4);
      PyString.fromInterned("distutils.command.install_data\n\nImplements the Distutils 'install_data' command, for installing\nplatform-independent data files.");
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
      var6 = new String[]{"change_root", "convert_path"};
      var7 = imp.importFrom("distutils.util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("change_root", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("convert_path", var4);
      var4 = null;
      var1.setline(14);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("install_data", var7, install_data$1);
      var1.setlocal("install_data", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject install_data$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(16);
      PyString var3 = PyString.fromInterned("install data files");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(18);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("install-dir="), PyString.fromInterned("d"), PyString.fromInterned("base directory for installing data files (default: installation base dir)")}), new PyTuple(new PyObject[]{PyString.fromInterned("root="), var1.getname("None"), PyString.fromInterned("install everything relative to this alternate root directory")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("f"), PyString.fromInterned("force installation (overwrite existing files)")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(27);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("force")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(29);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(37);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(44);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$4, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(77);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_inputs$5, (PyObject)null);
      var1.setlocal("get_inputs", var6);
      var3 = null;
      var1.setline(80);
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
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"outfiles", var4);
      var3 = null;
      var1.setline(32);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("root", var3);
      var3 = null;
      var1.setline(33);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"force", var5);
      var3 = null;
      var1.setline(34);
      var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("data_files");
      var1.getlocal(0).__setattr__("data_files", var3);
      var3 = null;
      var1.setline(35);
      var5 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"warn_dir", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__(var2, PyString.fromInterned("install"), new PyTuple(new PyObject[]{PyString.fromInterned("install_data"), PyString.fromInterned("install_dir")}), new PyTuple(new PyObject[]{PyString.fromInterned("root"), PyString.fromInterned("root")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("force")}));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$4(PyFrame var1, ThreadState var2) {
      var1.setline(45);
      var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getlocal(0).__getattr__("install_dir"));
      var1.setline(46);
      PyObject var3 = var1.getlocal(0).__getattr__("data_files").__iter__();

      while(true) {
         while(true) {
            var1.setline(46);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(1, var4);
            var1.setline(47);
            PyObject var5;
            PyObject var7;
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
               var1.setline(49);
               var5 = var1.getglobal("convert_path").__call__(var2, var1.getlocal(1));
               var1.setlocal(1, var5);
               var5 = null;
               var1.setline(50);
               if (var1.getlocal(0).__getattr__("warn_dir").__nonzero__()) {
                  var1.setline(51);
                  var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("setup script did not provide a directory for '%s' -- installing right in '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("install_dir")})));
               }

               var1.setline(54);
               var5 = var1.getlocal(0).__getattr__("copy_file").__call__(var2, var1.getlocal(1), var1.getlocal(0).__getattr__("install_dir"));
               PyObject[] var10 = Py.unpackSequence(var5, 2);
               var7 = var10[0];
               var1.setlocal(2, var7);
               var7 = null;
               var7 = var10[1];
               var1.setlocal(3, var7);
               var7 = null;
               var5 = null;
               var1.setline(55);
               var1.getlocal(0).__getattr__("outfiles").__getattr__("append").__call__(var2, var1.getlocal(2));
            } else {
               var1.setline(58);
               var5 = var1.getglobal("convert_path").__call__(var2, var1.getlocal(1).__getitem__(Py.newInteger(0)));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(59);
               if (var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(4)).__not__().__nonzero__()) {
                  var1.setline(60);
                  var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("install_dir"), var1.getlocal(4));
                  var1.setlocal(4, var5);
                  var5 = null;
               } else {
                  var1.setline(61);
                  if (var1.getlocal(0).__getattr__("root").__nonzero__()) {
                     var1.setline(62);
                     var5 = var1.getglobal("change_root").__call__(var2, var1.getlocal(0).__getattr__("root"), var1.getlocal(4));
                     var1.setlocal(4, var5);
                     var5 = null;
                  }
               }

               var1.setline(63);
               var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getlocal(4));
               var1.setline(65);
               var5 = var1.getlocal(1).__getitem__(Py.newInteger(1));
               PyObject var10000 = var5._eq(new PyList(Py.EmptyObjects));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(69);
                  var1.getlocal(0).__getattr__("outfiles").__getattr__("append").__call__(var2, var1.getlocal(4));
               } else {
                  var1.setline(72);
                  var5 = var1.getlocal(1).__getitem__(Py.newInteger(1)).__iter__();

                  while(true) {
                     var1.setline(72);
                     PyObject var6 = var5.__iternext__();
                     if (var6 == null) {
                        break;
                     }

                     var1.setlocal(5, var6);
                     var1.setline(73);
                     var7 = var1.getglobal("convert_path").__call__(var2, var1.getlocal(5));
                     var1.setlocal(5, var7);
                     var7 = null;
                     var1.setline(74);
                     var7 = var1.getlocal(0).__getattr__("copy_file").__call__(var2, var1.getlocal(5), var1.getlocal(4));
                     PyObject[] var8 = Py.unpackSequence(var7, 2);
                     PyObject var9 = var8[0];
                     var1.setlocal(2, var9);
                     var9 = null;
                     var9 = var8[1];
                     var1.setlocal(3, var9);
                     var9 = null;
                     var7 = null;
                     var1.setline(75);
                     var1.getlocal(0).__getattr__("outfiles").__getattr__("append").__call__(var2, var1.getlocal(2));
                  }
               }
            }
         }
      }
   }

   public PyObject get_inputs$5(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      Object var10000 = var1.getlocal(0).__getattr__("data_files");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject get_outputs$6(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = var1.getlocal(0).__getattr__("outfiles");
      var1.f_lasti = -1;
      return var3;
   }

   public install_data$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      install_data$1 = Py.newCode(0, var2, var1, "install_data", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 29, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 37, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "out", "_", "dir", "data"};
      run$4 = Py.newCode(1, var2, var1, "run", 44, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_inputs$5 = Py.newCode(1, var2, var1, "get_inputs", 77, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      get_outputs$6 = Py.newCode(1, var2, var1, "get_outputs", 80, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new install_data$py("distutils/command/install_data$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(install_data$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.install_data$1(var2, var3);
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
