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
@Filename("distutils/command/install_lib.py")
public class install_lib$py extends PyFunctionTable implements PyRunnable {
   static install_lib$py self;
   static final PyCode f$0;
   static final PyCode install_lib$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode run$4;
   static final PyCode build$5;
   static final PyCode install$6;
   static final PyCode byte_compile$7;
   static final PyCode _mutate_outputs$8;
   static final PyCode _bytecode_filenames$9;
   static final PyCode get_outputs$10;
   static final PyCode get_inputs$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.install_lib\n\nImplements the Distutils 'install_lib' command\n(install all Python modules)."));
      var1.setline(4);
      PyString.fromInterned("distutils.command.install_lib\n\nImplements the Distutils 'install_lib' command\n(install all Python modules).");
      var1.setline(6);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(8);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(9);
      var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(11);
      String[] var6 = new String[]{"Command"};
      PyObject[] var7 = imp.importFrom("distutils.core", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"DistutilsOptionError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var1.setline(16);
      if (var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("os"), (PyObject)PyString.fromInterned("extsep")).__nonzero__()) {
         var1.setline(17);
         var5 = var1.getname("os").__getattr__("extsep")._add(PyString.fromInterned("py"));
         var1.setlocal("PYTHON_SOURCE_EXTENSION", var5);
         var3 = null;
      } else {
         var1.setline(19);
         var3 = PyString.fromInterned(".py");
         var1.setlocal("PYTHON_SOURCE_EXTENSION", var3);
         var3 = null;
      }

      var1.setline(21);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("install_lib", var7, install_lib$1);
      var1.setlocal("install_lib", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject install_lib$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(23);
      PyString var3 = PyString.fromInterned("install all Python modules (extensions and pure Python)");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(40);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("install-dir="), PyString.fromInterned("d"), PyString.fromInterned("directory to install to")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-dir="), PyString.fromInterned("b"), PyString.fromInterned("build directory (where to install from)")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("f"), PyString.fromInterned("force installation (overwrite existing files)")}), new PyTuple(new PyObject[]{PyString.fromInterned("compile"), PyString.fromInterned("c"), PyString.fromInterned("compile .py to .pyc [default]")}), new PyTuple(new PyObject[]{PyString.fromInterned("no-compile"), var1.getname("None"), PyString.fromInterned("don't compile .py files")}), new PyTuple(new PyObject[]{PyString.fromInterned("optimize="), PyString.fromInterned("O"), PyString.fromInterned("also compile with optimization: -O1 for \"python -O\", -O2 for \"python -OO\", and -O0 to disable [default: -O0]")}), new PyTuple(new PyObject[]{PyString.fromInterned("skip-build"), var1.getname("None"), PyString.fromInterned("skip the build steps")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(52);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("compile"), PyString.fromInterned("skip-build")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(53);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("no-compile"), PyString.fromInterned("compile")});
      var1.setlocal("negative_opt", var5);
      var3 = null;
      var1.setline(55);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var7);
      var3 = null;
      var1.setline(64);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var7);
      var3 = null;
      var1.setline(90);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, run$4, (PyObject)null);
      var1.setlocal("run", var7);
      var3 = null;
      var1.setline(106);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, build$5, (PyObject)null);
      var1.setlocal("build", var7);
      var3 = null;
      var1.setline(113);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, install$6, (PyObject)null);
      var1.setlocal("install", var7);
      var3 = null;
      var1.setline(122);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, byte_compile$7, (PyObject)null);
      var1.setlocal("byte_compile", var7);
      var3 = null;
      var1.setline(147);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _mutate_outputs$8, (PyObject)null);
      var1.setlocal("_mutate_outputs", var7);
      var3 = null;
      var1.setline(162);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _bytecode_filenames$9, (PyObject)null);
      var1.setlocal("_bytecode_filenames", var7);
      var3 = null;
      var1.setline(182);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_outputs$10, PyString.fromInterned("Return the list of files that would be installed if this command\n        were actually run.  Not affected by the \"dry-run\" flag or whether\n        modules have actually been built yet.\n        "));
      var1.setlocal("get_outputs", var7);
      var3 = null;
      var1.setline(203);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_inputs$11, PyString.fromInterned("Get the list of files that are input to this command, ie. the\n        files that get installed as they are named in the build tree.\n        The files in this list correspond one-to-one to the output\n        filenames returned by 'get_outputs()'.\n        "));
      var1.setlocal("get_inputs", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_dir", var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_dir", var3);
      var3 = null;
      var1.setline(59);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"force", var4);
      var3 = null;
      var1.setline(60);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("compile", var3);
      var3 = null;
      var1.setline(61);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("optimize", var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("skip_build", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyObject var10000 = var1.getlocal(0).__getattr__("set_undefined_options");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("install"), new PyTuple(new PyObject[]{PyString.fromInterned("build_lib"), PyString.fromInterned("build_dir")}), new PyTuple(new PyObject[]{PyString.fromInterned("install_lib"), PyString.fromInterned("install_dir")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("force")}), new PyTuple(new PyObject[]{PyString.fromInterned("compile"), PyString.fromInterned("compile")}), new PyTuple(new PyObject[]{PyString.fromInterned("optimize"), PyString.fromInterned("optimize")}), new PyTuple(new PyObject[]{PyString.fromInterned("skip_build"), PyString.fromInterned("skip_build")})};
      var10000.__call__(var2, var3);
      var1.setline(77);
      PyObject var5 = var1.getlocal(0).__getattr__("compile");
      var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      PyInteger var6;
      if (var10000.__nonzero__()) {
         var1.setline(78);
         var6 = Py.newInteger(1);
         var1.getlocal(0).__setattr__((String)"compile", var6);
         var3 = null;
      }

      var1.setline(79);
      var5 = var1.getlocal(0).__getattr__("optimize");
      var10000 = var5._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(80);
         var6 = Py.newInteger(0);
         var1.getlocal(0).__setattr__((String)"optimize", var6);
         var3 = null;
      }

      var1.setline(82);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("optimize"), var1.getglobal("int")).__not__().__nonzero__()) {
         try {
            var1.setline(84);
            var5 = var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("optimize"));
            var1.getlocal(0).__setattr__("optimize", var5);
            var3 = null;
            var1.setline(85);
            var5 = var1.getlocal(0).__getattr__("optimize");
            var10000 = var5._notin(new PyTuple(new PyObject[]{Py.newInteger(0), Py.newInteger(1), Py.newInteger(2)}));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(86);
               throw Py.makeException(var1.getglobal("AssertionError"));
            }
         } catch (Throwable var4) {
            PyException var7 = Py.setException(var4, var1);
            if (var7.match(new PyTuple(new PyObject[]{var1.getglobal("ValueError"), var1.getglobal("AssertionError")}))) {
               var1.setline(88);
               throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("optimize must be 0, 1, or 2"));
            }

            throw var7;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$4(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      var1.getlocal(0).__getattr__("build").__call__(var2);
      var1.setline(97);
      PyObject var3 = var1.getlocal(0).__getattr__("install").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_pure_modules").__call__(var2);
      }

      if (var10000.__nonzero__()) {
         var1.setline(101);
         var1.getlocal(0).__getattr__("byte_compile").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject build$5(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      if (var1.getlocal(0).__getattr__("skip_build").__not__().__nonzero__()) {
         var1.setline(108);
         if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_pure_modules").__call__(var2).__nonzero__()) {
            var1.setline(109);
            var1.getlocal(0).__getattr__("run_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_py"));
         }

         var1.setline(110);
         if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2).__nonzero__()) {
            var1.setline(111);
            var1.getlocal(0).__getattr__("run_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_ext"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject install$6(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(0).__getattr__("build_dir")).__nonzero__()) {
         var1.setline(115);
         PyObject var3 = var1.getlocal(0).__getattr__("copy_tree").__call__(var2, var1.getlocal(0).__getattr__("build_dir"), var1.getlocal(0).__getattr__("install_dir"));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(120);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(117);
         var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("'%s' does not exist -- no Python modules to install")._mod(var1.getlocal(0).__getattr__("build_dir")));
         var1.setline(119);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject byte_compile$7(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      if (var1.getglobal("sys").__getattr__("dont_write_bytecode").__nonzero__()) {
         var1.setline(124);
         var1.getlocal(0).__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("byte-compiling is disabled, skipping."));
         var1.setline(125);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(127);
         String[] var3 = new String[]{"byte_compile"};
         PyObject[] var5 = imp.importFrom("distutils.util", var3, var1, -1);
         PyObject var4 = var5[0];
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(133);
         PyObject var6 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("install")).__getattr__("root");
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(135);
         PyObject var10000;
         String[] var7;
         if (var1.getlocal(0).__getattr__("compile").__nonzero__()) {
            var1.setline(136);
            var10000 = var1.getlocal(2);
            var5 = new PyObject[]{var1.getlocal(1), Py.newInteger(0), var1.getlocal(0).__getattr__("force"), var1.getlocal(3), var1.getlocal(0).__getattr__("dry_run")};
            var7 = new String[]{"optimize", "force", "prefix", "dry_run"};
            var10000.__call__(var2, var5, var7);
            var3 = null;
         }

         var1.setline(139);
         var6 = var1.getlocal(0).__getattr__("optimize");
         var10000 = var6._gt(Py.newInteger(0));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(140);
            var10000 = var1.getlocal(2);
            var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("optimize"), var1.getlocal(0).__getattr__("force"), var1.getlocal(3), var1.getlocal(0).__getattr__("verbose"), var1.getlocal(0).__getattr__("dry_run")};
            var7 = new String[]{"optimize", "force", "prefix", "verbose", "dry_run"};
            var10000.__call__(var2, var5, var7);
            var3 = null;
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _mutate_outputs$8(PyFrame var1, ThreadState var2) {
      var1.setline(148);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(149);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(151);
         PyObject var4 = var1.getlocal(0).__getattr__("get_finalized_command").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(152);
         var4 = var1.getlocal(2).__getattr__("get_outputs").__call__(var2);
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(153);
         var4 = var1.getglobal("getattr").__call__(var2, var1.getlocal(2), var1.getlocal(3));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(155);
         var4 = var1.getglobal("len").__call__(var2, var1.getlocal(6))._add(var1.getglobal("len").__call__(var2, var1.getglobal("os").__getattr__("sep")));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(156);
         PyList var7 = new PyList(Py.EmptyObjects);
         var1.setlocal(8, var7);
         var4 = null;
         var1.setline(157);
         var4 = var1.getlocal(5).__iter__();

         while(true) {
            var1.setline(157);
            PyObject var5 = var4.__iternext__();
            if (var5 == null) {
               var1.setline(160);
               PyObject var3 = var1.getlocal(8);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(9, var5);
            var1.setline(158);
            var1.getlocal(8).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(9).__getslice__(var1.getlocal(7), (PyObject)null, (PyObject)null)));
         }
      }
   }

   public PyObject _bytecode_filenames$9(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(164);
      PyObject var6 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(164);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(176);
            var6 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(3, var4);
         var1.setline(168);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(3))).__getitem__(Py.newInteger(1));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(169);
         var5 = var1.getlocal(4);
         PyObject var10000 = var5._ne(var1.getglobal("PYTHON_SOURCE_EXTENSION"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(171);
            if (var1.getlocal(0).__getattr__("compile").__nonzero__()) {
               var1.setline(172);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3)._add(PyString.fromInterned("c")));
            }

            var1.setline(173);
            var5 = var1.getlocal(0).__getattr__("optimize");
            var10000 = var5._gt(Py.newInteger(0));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(174);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3)._add(PyString.fromInterned("o")));
            }
         }
      }
   }

   public PyObject get_outputs$10(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyString.fromInterned("Return the list of files that would be installed if this command\n        were actually run.  Not affected by the \"dry-run\" flag or whether\n        modules have actually been built yet.\n        ");
      var1.setline(187);
      PyObject var3 = var1.getlocal(0).__getattr__("_mutate_outputs").__call__(var2, var1.getlocal(0).__getattr__("distribution").__getattr__("has_pure_modules").__call__(var2), PyString.fromInterned("build_py"), PyString.fromInterned("build_lib"), var1.getlocal(0).__getattr__("install_dir"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(191);
      if (var1.getlocal(0).__getattr__("compile").__nonzero__()) {
         var1.setline(192);
         var3 = var1.getlocal(0).__getattr__("_bytecode_filenames").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(194);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var4);
         var3 = null;
      }

      var1.setline(196);
      var3 = var1.getlocal(0).__getattr__("_mutate_outputs").__call__(var2, var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2), PyString.fromInterned("build_ext"), PyString.fromInterned("build_lib"), var1.getlocal(0).__getattr__("install_dir"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(201);
      var3 = var1.getlocal(1)._add(var1.getlocal(2))._add(var1.getlocal(3));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_inputs$11(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyString.fromInterned("Get the list of files that are input to this command, ie. the\n        files that get installed as they are named in the build tree.\n        The files in this list correspond one-to-one to the output\n        filenames returned by 'get_outputs()'.\n        ");
      var1.setline(209);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(211);
      PyObject var4;
      if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_pure_modules").__call__(var2).__nonzero__()) {
         var1.setline(212);
         var4 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_py"));
         var1.setlocal(2, var4);
         var3 = null;
         var1.setline(213);
         var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(2).__getattr__("get_outputs").__call__(var2));
      }

      var1.setline(215);
      if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2).__nonzero__()) {
         var1.setline(216);
         var4 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_ext"));
         var1.setlocal(3, var4);
         var3 = null;
         var1.setline(217);
         var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(3).__getattr__("get_outputs").__call__(var2));
      }

      var1.setline(219);
      var4 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var4;
   }

   public install_lib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      install_lib$1 = Py.newCode(0, var2, var1, "install_lib", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 55, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 64, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "outfiles"};
      run$4 = Py.newCode(1, var2, var1, "run", 90, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      build$5 = Py.newCode(1, var2, var1, "build", 106, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "outfiles"};
      install$6 = Py.newCode(1, var2, var1, "install", 113, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "files", "byte_compile", "install_root"};
      byte_compile$7 = Py.newCode(2, var2, var1, "byte_compile", 122, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "has_any", "build_cmd", "cmd_option", "output_dir", "build_files", "build_dir", "prefix_len", "outputs", "file"};
      _mutate_outputs$8 = Py.newCode(5, var2, var1, "_mutate_outputs", 147, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "py_filenames", "bytecode_files", "py_file", "ext"};
      _bytecode_filenames$9 = Py.newCode(2, var2, var1, "_bytecode_filenames", 162, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pure_outputs", "bytecode_outputs", "ext_outputs"};
      get_outputs$10 = Py.newCode(1, var2, var1, "get_outputs", 182, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "inputs", "build_py", "build_ext"};
      get_inputs$11 = Py.newCode(1, var2, var1, "get_inputs", 203, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new install_lib$py("distutils/command/install_lib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(install_lib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.install_lib$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.finalize_options$3(var2, var3);
         case 4:
            return this.run$4(var2, var3);
         case 5:
            return this.build$5(var2, var3);
         case 6:
            return this.install$6(var2, var3);
         case 7:
            return this.byte_compile$7(var2, var3);
         case 8:
            return this._mutate_outputs$8(var2, var3);
         case 9:
            return this._bytecode_filenames$9(var2, var3);
         case 10:
            return this.get_outputs$10(var2, var3);
         case 11:
            return this.get_inputs$11(var2, var3);
         default:
            return null;
      }
   }
}
