package distutils.command;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyException;
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
@Filename("distutils/command/build_py.py")
public class build_py$py extends PyFunctionTable implements PyRunnable {
   static build_py$py self;
   static final PyCode f$0;
   static final PyCode build_py$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode run$4;
   static final PyCode get_data_files$5;
   static final PyCode find_data_files$6;
   static final PyCode build_package_data$7;
   static final PyCode get_package_dir$8;
   static final PyCode check_package$9;
   static final PyCode check_module$10;
   static final PyCode find_package_modules$11;
   static final PyCode find_modules$12;
   static final PyCode find_all_modules$13;
   static final PyCode get_source_files$14;
   static final PyCode get_module_outfile$15;
   static final PyCode get_outputs$16;
   static final PyCode build_module$17;
   static final PyCode build_modules$18;
   static final PyCode build_packages$19;
   static final PyCode byte_compile$20;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.build_py\n\nImplements the Distutils 'build_py' command."));
      var1.setline(3);
      PyString.fromInterned("distutils.command.build_py\n\nImplements the Distutils 'build_py' command.");
      var1.setline(5);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(7);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(8);
      var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(9);
      String[] var6 = new String[]{"glob"};
      PyObject[] var7 = imp.importFrom("glob", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("glob", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"Command"};
      var7 = imp.importFrom("distutils.core", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"DistutilsOptionError", "DistutilsFileError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("DistutilsFileError", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"convert_path"};
      var7 = imp.importFrom("distutils.util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("convert_path", var4);
      var4 = null;
      var1.setline(14);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(16);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("build_py", var7, build_py$1);
      var1.setlocal("build_py", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject build_py$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(18);
      PyString var3 = PyString.fromInterned("\"build\" pure Python modules (copy to build directory)");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(20);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("build-lib="), PyString.fromInterned("d"), PyString.fromInterned("directory to \"build\" (copy) to")}), new PyTuple(new PyObject[]{PyString.fromInterned("compile"), PyString.fromInterned("c"), PyString.fromInterned("compile .py to .pyc")}), new PyTuple(new PyObject[]{PyString.fromInterned("no-compile"), var1.getname("None"), PyString.fromInterned("don't compile .py files [default]")}), new PyTuple(new PyObject[]{PyString.fromInterned("optimize="), PyString.fromInterned("O"), PyString.fromInterned("also compile with optimization: -O1 for \"python -O\", -O2 for \"python -OO\", and -O0 to disable [default: -O0]")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("f"), PyString.fromInterned("forcibly build everything (ignore file timestamps)")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(30);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("compile"), PyString.fromInterned("force")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(31);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("no-compile"), PyString.fromInterned("compile")});
      var1.setlocal("negative_opt", var5);
      var3 = null;
      var1.setline(33);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var7);
      var3 = null;
      var1.setline(43);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var7);
      var3 = null;
      var1.setline(68);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, run$4, (PyObject)null);
      var1.setlocal("run", var7);
      var3 = null;
      var1.setline(98);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_data_files$5, PyString.fromInterned("Generate list of '(package,src_dir,build_dir,filenames)' tuples"));
      var1.setlocal("get_data_files", var7);
      var3 = null;
      var1.setline(122);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, find_data_files$6, PyString.fromInterned("Return filenames for package's data files in 'src_dir'"));
      var1.setlocal("find_data_files", var7);
      var3 = null;
      var1.setline(135);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, build_package_data$7, PyString.fromInterned("Copy data files into build directory"));
      var1.setlocal("build_package_data", var7);
      var3 = null;
      var1.setline(144);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_package_dir$8, PyString.fromInterned("Return the directory, relative to the top of the source\n           distribution, where package 'package' should be found\n           (at least according to the 'package_dir' option, if any)."));
      var1.setlocal("get_package_dir", var7);
      var3 = null;
      var1.setline(184);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, check_package$9, (PyObject)null);
      var1.setlocal("check_package", var7);
      var3 = null;
      var1.setline(211);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, check_module$10, (PyObject)null);
      var1.setlocal("check_module", var7);
      var3 = null;
      var1.setline(218);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, find_package_modules$11, (PyObject)null);
      var1.setlocal("find_package_modules", var7);
      var3 = null;
      var1.setline(233);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, find_modules$12, PyString.fromInterned("Finds individually-specified Python modules, ie. those listed by\n        module name in 'self.py_modules'.  Returns a list of tuples (package,\n        module_base, filename): 'package' is a tuple of the path through\n        package-space to the module; 'module_base' is the bare (no\n        packages, no dots) module name, and 'filename' is the path to the\n        \".py\" file (relative to the distribution root) that implements the\n        module.\n        "));
      var1.setlocal("find_modules", var7);
      var3 = null;
      var1.setline(285);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, find_all_modules$13, PyString.fromInterned("Compute the list of all modules that will be built, whether\n        they are specified one-module-at-a-time ('self.py_modules') or\n        by whole packages ('self.packages').  Return a list of tuples\n        (package, module, module_file), just like 'find_modules()' and\n        'find_package_modules()' do."));
      var1.setlocal("find_all_modules", var7);
      var3 = null;
      var1.setline(301);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_source_files$14, (PyObject)null);
      var1.setlocal("get_source_files", var7);
      var3 = null;
      var1.setline(304);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_module_outfile$15, (PyObject)null);
      var1.setlocal("get_module_outfile", var7);
      var3 = null;
      var1.setline(308);
      var6 = new PyObject[]{Py.newInteger(1)};
      var7 = new PyFunction(var1.f_globals, var6, get_outputs$16, (PyObject)null);
      var1.setlocal("get_outputs", var7);
      var3 = null;
      var1.setline(329);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, build_module$17, (PyObject)null);
      var1.setlocal("build_module", var7);
      var3 = null;
      var1.setline(344);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, build_modules$18, (PyObject)null);
      var1.setlocal("build_modules", var7);
      var3 = null;
      var1.setline(354);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, build_packages$19, (PyObject)null);
      var1.setlocal("build_packages", var7);
      var3 = null;
      var1.setline(375);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, byte_compile$20, (PyObject)null);
      var1.setlocal("byte_compile", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_lib", var3);
      var3 = null;
      var1.setline(35);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("py_modules", var3);
      var3 = null;
      var1.setline(36);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("package", var3);
      var3 = null;
      var1.setline(37);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("package_data", var3);
      var3 = null;
      var1.setline(38);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("package_dir", var3);
      var3 = null;
      var1.setline(39);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"compile", var4);
      var3 = null;
      var1.setline(40);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"optimize", var4);
      var3 = null;
      var1.setline(41);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("force", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, PyString.fromInterned("build"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("build_lib"), PyString.fromInterned("build_lib")})), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("force")})));
      var1.setline(50);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("packages");
      var1.getlocal(0).__setattr__("packages", var3);
      var3 = null;
      var1.setline(51);
      var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("py_modules");
      var1.getlocal(0).__setattr__("py_modules", var3);
      var3 = null;
      var1.setline(52);
      var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("package_data");
      var1.getlocal(0).__setattr__("package_data", var3);
      var3 = null;
      var1.setline(53);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"package_dir", var8);
      var3 = null;
      var1.setline(54);
      PyObject var4;
      if (var1.getlocal(0).__getattr__("distribution").__getattr__("package_dir").__nonzero__()) {
         var1.setline(55);
         var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("package_dir").__getattr__("items").__call__(var2).__iter__();

         while(true) {
            var1.setline(55);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(1, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(2, var6);
            var6 = null;
            var1.setline(56);
            PyObject var9 = var1.getglobal("convert_path").__call__(var2, var1.getlocal(2));
            var1.getlocal(0).__getattr__("package_dir").__setitem__(var1.getlocal(1), var9);
            var5 = null;
         }
      }

      var1.setline(57);
      var3 = var1.getlocal(0).__getattr__("get_data_files").__call__(var2);
      var1.getlocal(0).__setattr__("data_files", var3);
      var3 = null;
      var1.setline(61);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("optimize"), var1.getglobal("int")).__not__().__nonzero__()) {
         try {
            var1.setline(63);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("optimize"));
            var1.getlocal(0).__setattr__("optimize", var3);
            var3 = null;
            var1.setline(64);
            if (var1.getglobal("__debug__").__nonzero__()) {
               PyInteger var11 = Py.newInteger(0);
               PyObject var10001 = var1.getlocal(0).__getattr__("optimize");
               PyInteger var10000 = var11;
               var3 = var10001;
               if ((var4 = var10000._le(var10001)).__nonzero__()) {
                  var4 = var3._le(Py.newInteger(2));
               }

               var3 = null;
               if (!var4.__nonzero__()) {
                  PyObject var12 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var12);
               }
            }
         } catch (Throwable var7) {
            PyException var10 = Py.setException(var7, var1);
            if (var10.match(new PyTuple(new PyObject[]{var1.getglobal("ValueError"), var1.getglobal("AssertionError")}))) {
               var1.setline(66);
               throw Py.makeException(var1.getglobal("DistutilsOptionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("optimize must be 0, 1, or 2")));
            }

            throw var10;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$4(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      if (var1.getlocal(0).__getattr__("py_modules").__nonzero__()) {
         var1.setline(91);
         var1.getlocal(0).__getattr__("build_modules").__call__(var2);
      }

      var1.setline(92);
      if (var1.getlocal(0).__getattr__("packages").__nonzero__()) {
         var1.setline(93);
         var1.getlocal(0).__getattr__("build_packages").__call__(var2);
         var1.setline(94);
         var1.getlocal(0).__getattr__("build_package_data").__call__(var2);
      }

      var1.setline(96);
      PyObject var10000 = var1.getlocal(0).__getattr__("byte_compile");
      PyObject var10002 = var1.getlocal(0).__getattr__("get_outputs");
      PyObject[] var3 = new PyObject[]{Py.newInteger(0)};
      String[] var4 = new String[]{"include_bytecode"};
      var10002 = var10002.__call__(var2, var3, var4);
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_data_files$5(PyFrame var1, ThreadState var2) {
      var1.setline(99);
      PyString.fromInterned("Generate list of '(package,src_dir,build_dir,filenames)' tuples");
      var1.setline(100);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(101);
      PyObject var8;
      if (var1.getlocal(0).__getattr__("packages").__not__().__nonzero__()) {
         var1.setline(102);
         var8 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var8;
      } else {
         var1.setline(103);
         PyObject var4 = var1.getlocal(0).__getattr__("packages").__iter__();

         while(true) {
            var1.setline(103);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(120);
               var8 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var8;
            }

            var1.setlocal(2, var5);
            var1.setline(105);
            PyObject var6 = var1.getlocal(0).__getattr__("get_package_dir").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(108);
            PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
            PyObject[] var9 = Py.EmptyObjects;
            String[] var7 = new String[0];
            var10000 = var10000._callextra(var9, var7, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("build_lib")}))._add(var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."))), (PyObject)null);
            var6 = null;
            var6 = var10000;
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(111);
            PyInteger var11 = Py.newInteger(0);
            var1.setlocal(5, var11);
            var6 = null;
            var1.setline(112);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(113);
               var6 = var1.getglobal("len").__call__(var2, var1.getlocal(3))._add(Py.newInteger(1));
               var1.setlocal(5, var6);
               var6 = null;
            }

            var1.setline(116);
            PyList var13 = new PyList();
            var6 = var13.__getattr__("append");
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(117);
            var6 = var1.getlocal(0).__getattr__("find_data_files").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__iter__();

            while(true) {
               var1.setline(117);
               PyObject var10 = var6.__iternext__();
               if (var10 == null) {
                  var1.setline(117);
                  var1.dellocal(7);
                  PyList var12 = var13;
                  var1.setlocal(6, var12);
                  var6 = null;
                  var1.setline(119);
                  var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(6)})));
                  break;
               }

               var1.setlocal(8, var10);
               var1.setline(117);
               var1.getlocal(7).__call__(var2, var1.getlocal(8).__getslice__(var1.getlocal(5), (PyObject)null, (PyObject)null));
            }
         }
      }
   }

   public PyObject find_data_files$6(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyString.fromInterned("Return filenames for package's data files in 'src_dir'");
      var1.setline(124);
      PyObject var3 = var1.getlocal(0).__getattr__("package_data").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""), (PyObject)(new PyList(Py.EmptyObjects)))._add(var1.getlocal(0).__getattr__("package_data").__getattr__("get").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyList(Py.EmptyObjects))));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(126);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(127);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(127);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(133);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(129);
         PyObject var5 = var1.getglobal("glob").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getglobal("convert_path").__call__(var2, var1.getlocal(5))));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(131);
         PyObject var10000 = var1.getlocal(4).__getattr__("extend");
         PyList var10002 = new PyList();
         var5 = var10002.__getattr__("append");
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(131);
         var5 = var1.getlocal(6).__iter__();

         while(true) {
            var1.setline(131);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               var1.setline(131);
               var1.dellocal(7);
               var10000.__call__((ThreadState)var2, (PyObject)var10002);
               break;
            }

            var1.setlocal(8, var6);
            var1.setline(131);
            PyObject var7 = var1.getlocal(8);
            PyObject var10003 = var7._notin(var1.getlocal(4));
            var7 = null;
            if (var10003.__nonzero__()) {
               var10003 = var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(8));
            }

            if (var10003.__nonzero__()) {
               var1.setline(131);
               var1.getlocal(7).__call__(var2, var1.getlocal(8));
            }
         }
      }
   }

   public PyObject build_package_data$7(PyFrame var1, ThreadState var2) {
      var1.setline(136);
      PyString.fromInterned("Copy data files into build directory");
      var1.setline(137);
      PyObject var3 = var1.getlocal(0).__getattr__("data_files").__iter__();

      while(true) {
         var1.setline(137);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 4);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[3];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(138);
         PyObject var9 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(138);
            var6 = var9.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(5, var6);
            var1.setline(139);
            PyObject var7 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(5));
            var1.setlocal(6, var7);
            var7 = null;
            var1.setline(140);
            var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(6)));
            var1.setline(141);
            PyObject var10000 = var1.getlocal(0).__getattr__("copy_file");
            PyObject[] var10 = new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(5)), var1.getlocal(6), var1.getglobal("False")};
            String[] var8 = new String[]{"preserve_mode"};
            var10000.__call__(var2, var10, var8);
            var7 = null;
         }
      }
   }

   public PyObject get_package_dir$8(PyFrame var1, ThreadState var2) {
      var1.setline(147);
      PyString.fromInterned("Return the directory, relative to the top of the source\n           distribution, where package 'package' should be found\n           (at least according to the 'package_dir' option, if any).");
      var1.setline(149);
      PyObject var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(151);
      PyString var8;
      PyObject var10000;
      if (var1.getlocal(0).__getattr__("package_dir").__not__().__nonzero__()) {
         var1.setline(152);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(153);
            var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
            PyObject[] var10 = Py.EmptyObjects;
            String[] var14 = new String[0];
            var10000 = var10000._callextra(var10, var14, var1.getlocal(2), (PyObject)null);
            var3 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(155);
            var8 = PyString.fromInterned("");
            var1.f_lasti = -1;
            return var8;
         }
      } else {
         var1.setline(157);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var4);
         var4 = null;

         while(true) {
            var1.setline(158);
            PyObject var11;
            if (!var1.getlocal(2).__nonzero__()) {
               var1.setline(175);
               var11 = var1.getlocal(0).__getattr__("package_dir").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(""));
               var1.setlocal(4, var11);
               var4 = null;
               var1.setline(176);
               var11 = var1.getlocal(4);
               var10000 = var11._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(177);
                  var1.getlocal(3).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(4));
               }

               var1.setline(179);
               if (var1.getlocal(3).__nonzero__()) {
                  var1.setline(180);
                  var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
                  PyObject[] var13 = Py.EmptyObjects;
                  String[] var12 = new String[0];
                  var10000 = var10000._callextra(var13, var12, var1.getlocal(3), (PyObject)null);
                  var4 = null;
                  var3 = var10000;
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setline(182);
               var8 = PyString.fromInterned("");
               var1.f_lasti = -1;
               return var8;
            }

            try {
               var1.setline(160);
               var11 = var1.getlocal(0).__getattr__("package_dir").__getitem__(PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(2)));
               var1.setlocal(4, var11);
               var4 = null;
            } catch (Throwable var7) {
               PyException var9 = Py.setException(var7, var1);
               if (var9.match(var1.getglobal("KeyError"))) {
                  var1.setline(162);
                  var1.getlocal(3).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(2).__getitem__(Py.newInteger(-1)));
                  var1.setline(163);
                  var1.getlocal(2).__delitem__((PyObject)Py.newInteger(-1));
                  continue;
               }

               throw var9;
            }

            var1.setline(165);
            var1.getlocal(3).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getlocal(4));
            var1.setline(166);
            var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
            PyObject[] var5 = Py.EmptyObjects;
            String[] var6 = new String[0];
            var10000 = var10000._callextra(var5, var6, var1.getlocal(3), (PyObject)null);
            var5 = null;
            var3 = var10000;
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject check_package$9(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._ne(PyString.fromInterned(""));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(190);
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(2)).__not__().__nonzero__()) {
            var1.setline(191);
            throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("package directory '%s' does not exist")._mod(var1.getlocal(2))));
         }

         var1.setline(193);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(2)).__not__().__nonzero__()) {
            var1.setline(194);
            throw Py.makeException(var1.getglobal("DistutilsFileError").__call__(var2, PyString.fromInterned("supposed package directory '%s' exists, but is not a directory")._mod(var1.getlocal(2))));
         }
      }

      var1.setline(199);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(200);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("__init__.py"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(201);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(3)).__nonzero__()) {
            var1.setline(202);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(204);
         var1.getglobal("log").__getattr__("warn").__call__(var2, PyString.fromInterned("package init file '%s' not found ")._add(PyString.fromInterned("(or not a regular file)")), var1.getlocal(3));
      }

      var1.setline(209);
      var3 = var1.getglobal("None");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject check_module$10(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyObject var3;
      if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(2)).__not__().__nonzero__()) {
         var1.setline(213);
         var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, PyString.fromInterned("file %s (for module %s) not found"), (PyObject)var1.getlocal(2), (PyObject)var1.getlocal(1));
         var1.setline(214);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(216);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject find_package_modules$11(PyFrame var1, ThreadState var2) {
      var1.setline(219);
      var1.getlocal(0).__getattr__("check_package").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      var1.setline(220);
      PyObject var3 = var1.getglobal("glob").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("*.py")));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(221);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(222);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(0).__getattr__("distribution").__getattr__("script_name"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(224);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(224);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(231);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(6, var4);
         var1.setline(225);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(6));
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(226);
         var5 = var1.getlocal(7);
         PyObject var10000 = var5._ne(var1.getlocal(5));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(227);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(6))).__getitem__(Py.newInteger(0));
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(228);
            var1.getlocal(4).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(8), var1.getlocal(6)})));
         } else {
            var1.setline(230);
            var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("excluding %s")._mod(var1.getlocal(5)));
         }
      }
   }

   public PyObject find_modules$12(PyFrame var1, ThreadState var2) {
      var1.setline(241);
      PyString.fromInterned("Finds individually-specified Python modules, ie. those listed by\n        module name in 'self.py_modules'.  Returns a list of tuples (package,\n        module_base, filename): 'package' is a tuple of the path through\n        package-space to the module; 'module_base' is the bare (no\n        packages, no dots) module name, and 'filename' is the path to the\n        \".py\" file (relative to the distribution root) that implements the\n        module.\n        ");
      var1.setline(248);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(251);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(257);
      PyObject var10 = var1.getlocal(0).__getattr__("py_modules").__iter__();

      while(true) {
         var1.setline(257);
         PyObject var4 = var10.__iternext__();
         if (var4 == null) {
            var1.setline(283);
            var10 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var10;
         }

         var1.setlocal(3, var4);
         var1.setline(258);
         PyObject var5 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(259);
         var5 = PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(4).__getslice__(Py.newInteger(0), Py.newInteger(-1), (PyObject)null));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(260);
         var5 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
         var1.setlocal(6, var5);
         var5 = null;

         try {
            var1.setline(263);
            var5 = var1.getlocal(1).__getitem__(var1.getlocal(5));
            PyObject[] var13 = Py.unpackSequence(var5, 2);
            PyObject var7 = var13[0];
            var1.setlocal(7, var7);
            var7 = null;
            var7 = var13[1];
            var1.setlocal(8, var7);
            var7 = null;
            var5 = null;
         } catch (Throwable var8) {
            PyException var12 = Py.setException(var8, var1);
            if (!var12.match(var1.getglobal("KeyError"))) {
               throw var12;
            }

            var1.setline(265);
            PyObject var6 = var1.getlocal(0).__getattr__("get_package_dir").__call__(var2, var1.getlocal(5));
            var1.setlocal(7, var6);
            var6 = null;
            var1.setline(266);
            PyInteger var11 = Py.newInteger(0);
            var1.setlocal(8, var11);
            var6 = null;
         }

         var1.setline(268);
         if (var1.getlocal(8).__not__().__nonzero__()) {
            var1.setline(269);
            var5 = var1.getlocal(0).__getattr__("check_package").__call__(var2, var1.getlocal(5), var1.getlocal(7));
            var1.setlocal(9, var5);
            var5 = null;
            var1.setline(270);
            PyTuple var14 = new PyTuple(new PyObject[]{var1.getlocal(7), Py.newInteger(1)});
            var1.getlocal(1).__setitem__((PyObject)var1.getlocal(5), var14);
            var5 = null;
            var1.setline(271);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(272);
               var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), PyString.fromInterned("__init__"), var1.getlocal(9)})));
            }
         }

         var1.setline(277);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(7), var1.getlocal(6)._add(PyString.fromInterned(".py")));
         var1.setlocal(10, var5);
         var5 = null;
         var1.setline(278);
         if (!var1.getlocal(0).__getattr__("check_module").__call__(var2, var1.getlocal(3), var1.getlocal(10)).__not__().__nonzero__()) {
            var1.setline(281);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(10)})));
         }
      }
   }

   public PyObject find_all_modules$13(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      PyString.fromInterned("Compute the list of all modules that will be built, whether\n        they are specified one-module-at-a-time ('self.py_modules') or\n        by whole packages ('self.packages').  Return a list of tuples\n        (package, module, module_file), just like 'find_modules()' and\n        'find_package_modules()' do.");
      var1.setline(291);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(292);
      if (var1.getlocal(0).__getattr__("py_modules").__nonzero__()) {
         var1.setline(293);
         var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("find_modules").__call__(var2));
      }

      var1.setline(294);
      PyObject var6;
      if (var1.getlocal(0).__getattr__("packages").__nonzero__()) {
         var1.setline(295);
         var6 = var1.getlocal(0).__getattr__("packages").__iter__();

         while(true) {
            var1.setline(295);
            PyObject var4 = var6.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(296);
            PyObject var5 = var1.getlocal(0).__getattr__("get_package_dir").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(297);
            var5 = var1.getlocal(0).__getattr__("find_package_modules").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(298);
            var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(4));
         }
      }

      var1.setline(299);
      var6 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject get_source_files$14(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      PyList var10000 = new PyList();
      PyObject var3 = var10000.__getattr__("append");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(302);
      var3 = var1.getlocal(0).__getattr__("find_all_modules").__call__(var2).__iter__();

      while(true) {
         var1.setline(302);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(302);
            var1.dellocal(1);
            PyList var5 = var10000;
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(302);
         var1.getlocal(1).__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(-1)));
      }
   }

   public PyObject get_module_outfile$15(PyFrame var1, ThreadState var2) {
      var1.setline(305);
      PyObject var3 = (new PyList(new PyObject[]{var1.getlocal(1)}))._add(var1.getglobal("list").__call__(var2, var1.getlocal(2)))._add(new PyList(new PyObject[]{var1.getlocal(3)._add(PyString.fromInterned(".py"))}));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(306);
      PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
      PyObject[] var5 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var5, var4, var1.getlocal(4), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_outputs$16(PyFrame var1, ThreadState var2) {
      var1.setline(309);
      PyObject var3 = var1.getlocal(0).__getattr__("find_all_modules").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(310);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(311);
      var3 = var1.getlocal(2).__iter__();

      while(true) {
         var1.setline(311);
         PyObject var4 = var3.__iternext__();
         PyObject var6;
         PyObject var9;
         if (var4 == null) {
            var1.setline(321);
            var3 = var1.getlocal(3);
            PyList var11 = new PyList();
            var4 = var11.__getattr__("append");
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(323);
            var4 = var1.getlocal(0).__getattr__("data_files").__iter__();

            while(true) {
               var1.setline(323);
               var9 = var4.__iternext__();
               if (var9 == null) {
                  var1.setline(323);
                  var1.dellocal(8);
                  var3 = var3._iadd(var11);
                  var1.setlocal(3, var3);
                  var1.setline(327);
                  var3 = var1.getlocal(3);
                  var1.f_lasti = -1;
                  return var3;
               }

               PyObject[] var10 = Py.unpackSequence(var9, 4);
               PyObject var7 = var10[0];
               var1.setlocal(4, var7);
               var7 = null;
               var7 = var10[1];
               var1.setlocal(10, var7);
               var7 = null;
               var7 = var10[2];
               var1.setlocal(9, var7);
               var7 = null;
               var7 = var10[3];
               var1.setlocal(11, var7);
               var7 = null;
               var1.setline(324);
               var6 = var1.getlocal(11).__iter__();

               while(true) {
                  var1.setline(324);
                  var7 = var6.__iternext__();
                  if (var7 == null) {
                     break;
                  }

                  var1.setlocal(7, var7);
                  var1.setline(322);
                  var1.getlocal(8).__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(9), var1.getlocal(7)));
               }
            }
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         var6 = var5[0];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(5, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(6, var6);
         var6 = null;
         var1.setline(312);
         var9 = var1.getlocal(4).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(4, var9);
         var5 = null;
         var1.setline(313);
         var9 = var1.getlocal(0).__getattr__("get_module_outfile").__call__(var2, var1.getlocal(0).__getattr__("build_lib"), var1.getlocal(4), var1.getlocal(5));
         var1.setlocal(7, var9);
         var5 = null;
         var1.setline(314);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(7));
         var1.setline(315);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(316);
            if (var1.getlocal(0).__getattr__("compile").__nonzero__()) {
               var1.setline(317);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(7)._add(PyString.fromInterned("c")));
            }

            var1.setline(318);
            var9 = var1.getlocal(0).__getattr__("optimize");
            PyObject var10000 = var9._gt(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(319);
               var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(7)._add(PyString.fromInterned("o")));
            }
         }
      }
   }

   public PyObject build_module$17(PyFrame var1, ThreadState var2) {
      var1.setline(330);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("str")).__nonzero__()) {
         var1.setline(331);
         var3 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(332);
         if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__not__().__nonzero__()) {
            var1.setline(333);
            throw Py.makeException(var1.getglobal("TypeError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'package' must be a string (dot-separated), list, or tuple")));
         }
      }

      var1.setline(339);
      var3 = var1.getlocal(0).__getattr__("get_module_outfile").__call__(var2, var1.getlocal(0).__getattr__("build_lib"), var1.getlocal(3), var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(340);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(341);
      var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getlocal(5));
      var1.setline(342);
      PyObject var10000 = var1.getlocal(0).__getattr__("copy_file");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2), var1.getlocal(4), Py.newInteger(0)};
      String[] var4 = new String[]{"preserve_mode"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject build_modules$18(PyFrame var1, ThreadState var2) {
      var1.setline(345);
      PyObject var3 = var1.getlocal(0).__getattr__("find_modules").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(346);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(346);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(352);
         var1.getlocal(0).__getattr__("build_module").__call__(var2, var1.getlocal(3), var1.getlocal(4), var1.getlocal(2));
      }
   }

   public PyObject build_packages$19(PyFrame var1, ThreadState var2) {
      var1.setline(355);
      PyObject var3 = var1.getlocal(0).__getattr__("packages").__iter__();

      while(true) {
         var1.setline(355);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(366);
         PyObject var5 = var1.getlocal(0).__getattr__("get_package_dir").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var5);
         var5 = null;
         var1.setline(367);
         var5 = var1.getlocal(0).__getattr__("find_package_modules").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(371);
         var5 = var1.getlocal(3).__iter__();

         while(true) {
            var1.setline(371);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            PyObject[] var7 = Py.unpackSequence(var6, 3);
            PyObject var8 = var7[0];
            var1.setlocal(4, var8);
            var8 = null;
            var8 = var7[1];
            var1.setlocal(5, var8);
            var8 = null;
            var8 = var7[2];
            var1.setlocal(6, var8);
            var8 = null;
            var1.setline(372);
            if (var1.getglobal("__debug__").__nonzero__()) {
               PyObject var9 = var1.getlocal(1);
               PyObject var10000 = var9._eq(var1.getlocal(4));
               var7 = null;
               if (!var10000.__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }
            }

            var1.setline(373);
            var1.getlocal(0).__getattr__("build_module").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(1));
         }
      }
   }

   public PyObject byte_compile$20(PyFrame var1, ThreadState var2) {
      var1.setline(376);
      if (var1.getglobal("sys").__getattr__("dont_write_bytecode").__nonzero__()) {
         var1.setline(377);
         var1.getlocal(0).__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("byte-compiling is disabled, skipping."));
         var1.setline(378);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(380);
         String[] var3 = new String[]{"byte_compile"};
         PyObject[] var5 = imp.importFrom("distutils.util", var3, var1, -1);
         PyObject var4 = var5[0];
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(381);
         PyObject var6 = var1.getlocal(0).__getattr__("build_lib");
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(382);
         var6 = var1.getlocal(3).__getitem__(Py.newInteger(-1));
         PyObject var10000 = var6._ne(var1.getglobal("os").__getattr__("sep"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(383);
            var6 = var1.getlocal(3)._add(var1.getglobal("os").__getattr__("sep"));
            var1.setlocal(3, var6);
            var3 = null;
         }

         var1.setline(389);
         String[] var7;
         if (var1.getlocal(0).__getattr__("compile").__nonzero__()) {
            var1.setline(390);
            var10000 = var1.getlocal(2);
            var5 = new PyObject[]{var1.getlocal(1), Py.newInteger(0), var1.getlocal(0).__getattr__("force"), var1.getlocal(3), var1.getlocal(0).__getattr__("dry_run")};
            var7 = new String[]{"optimize", "force", "prefix", "dry_run"};
            var10000.__call__(var2, var5, var7);
            var3 = null;
         }

         var1.setline(392);
         var6 = var1.getlocal(0).__getattr__("optimize");
         var10000 = var6._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(393);
            var10000 = var1.getlocal(2);
            var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("optimize"), var1.getlocal(0).__getattr__("force"), var1.getlocal(3), var1.getlocal(0).__getattr__("dry_run")};
            var7 = new String[]{"optimize", "force", "prefix", "dry_run"};
            var10000.__call__(var2, var5, var7);
            var3 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public build_py$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      build_py$1 = Py.newCode(0, var2, var1, "build_py", 16, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 33, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "path"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 43, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      run$4 = Py.newCode(1, var2, var1, "run", 68, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "package", "src_dir", "build_dir", "plen", "filenames", "_[117_16]", "file"};
      get_data_files$5 = Py.newCode(1, var2, var1, "get_data_files", 98, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "package", "src_dir", "globs", "files", "pattern", "filelist", "_[131_26]", "fn"};
      find_data_files$6 = Py.newCode(3, var2, var1, "find_data_files", 122, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "package", "src_dir", "build_dir", "filenames", "filename", "target"};
      build_package_data$7 = Py.newCode(1, var2, var1, "build_package_data", 135, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "package", "path", "tail", "pdir"};
      get_package_dir$8 = Py.newCode(2, var2, var1, "get_package_dir", 144, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "package", "package_dir", "init_py"};
      check_package$9 = Py.newCode(3, var2, var1, "check_package", 184, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module", "module_file"};
      check_module$10 = Py.newCode(3, var2, var1, "check_module", 211, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "package", "package_dir", "module_files", "modules", "setup_script", "f", "abs_f", "module"};
      find_package_modules$11 = Py.newCode(3, var2, var1, "find_package_modules", 218, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "packages", "modules", "module", "path", "package", "module_base", "package_dir", "checked", "init_py", "module_file"};
      find_modules$12 = Py.newCode(1, var2, var1, "find_modules", 233, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "modules", "package", "package_dir", "m"};
      find_all_modules$13 = Py.newCode(1, var2, var1, "find_all_modules", 285, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[302_16]", "module"};
      get_source_files$14 = Py.newCode(1, var2, var1, "get_source_files", 301, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "build_dir", "package", "module", "outfile_path"};
      get_module_outfile$15 = Py.newCode(4, var2, var1, "get_module_outfile", 304, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "include_bytecode", "modules", "outputs", "package", "module", "module_file", "filename", "_[322_12]", "build_dir", "src_dir", "filenames"};
      get_outputs$16 = Py.newCode(2, var2, var1, "get_outputs", 308, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "module", "module_file", "package", "outfile", "dir"};
      build_module$17 = Py.newCode(4, var2, var1, "build_module", 329, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "modules", "package", "module", "module_file"};
      build_modules$18 = Py.newCode(1, var2, var1, "build_modules", 344, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "package", "package_dir", "modules", "package_", "module", "module_file"};
      build_packages$19 = Py.newCode(1, var2, var1, "build_packages", 354, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "files", "byte_compile", "prefix"};
      byte_compile$20 = Py.newCode(2, var2, var1, "byte_compile", 375, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new build_py$py("distutils/command/build_py$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(build_py$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.build_py$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.finalize_options$3(var2, var3);
         case 4:
            return this.run$4(var2, var3);
         case 5:
            return this.get_data_files$5(var2, var3);
         case 6:
            return this.find_data_files$6(var2, var3);
         case 7:
            return this.build_package_data$7(var2, var3);
         case 8:
            return this.get_package_dir$8(var2, var3);
         case 9:
            return this.check_package$9(var2, var3);
         case 10:
            return this.check_module$10(var2, var3);
         case 11:
            return this.find_package_modules$11(var2, var3);
         case 12:
            return this.find_modules$12(var2, var3);
         case 13:
            return this.find_all_modules$13(var2, var3);
         case 14:
            return this.get_source_files$14(var2, var3);
         case 15:
            return this.get_module_outfile$15(var2, var3);
         case 16:
            return this.get_outputs$16(var2, var3);
         case 17:
            return this.build_module$17(var2, var3);
         case 18:
            return this.build_modules$18(var2, var3);
         case 19:
            return this.build_packages$19(var2, var3);
         case 20:
            return this.byte_compile$20(var2, var3);
         default:
            return null;
      }
   }
}
