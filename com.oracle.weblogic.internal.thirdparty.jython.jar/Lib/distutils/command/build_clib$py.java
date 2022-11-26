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
@Filename("distutils/command/build_clib.py")
public class build_clib$py extends PyFunctionTable implements PyRunnable {
   static build_clib$py self;
   static final PyCode f$0;
   static final PyCode show_compilers$1;
   static final PyCode build_clib$2;
   static final PyCode initialize_options$3;
   static final PyCode finalize_options$4;
   static final PyCode run$5;
   static final PyCode check_library_list$6;
   static final PyCode get_library_names$7;
   static final PyCode get_source_files$8;
   static final PyCode build_libraries$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.build_clib\n\nImplements the Distutils 'build_clib' command, to build a C/C++ library\nthat is included in the module distribution and needed by an extension\nmodule."));
      var1.setline(5);
      PyString.fromInterned("distutils.command.build_clib\n\nImplements the Distutils 'build_clib' command, to build a C/C++ library\nthat is included in the module distribution and needed by an extension\nmodule.");
      var1.setline(7);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(19);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(20);
      String[] var6 = new String[]{"Command"};
      PyObject[] var7 = imp.importFrom("distutils.core", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(21);
      var6 = new String[]{"DistutilsSetupError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsSetupError", var4);
      var4 = null;
      var1.setline(22);
      var6 = new String[]{"customize_compiler"};
      var7 = imp.importFrom("distutils.sysconfig", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("customize_compiler", var4);
      var4 = null;
      var1.setline(23);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(25);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, show_compilers$1, (PyObject)null);
      var1.setlocal("show_compilers", var8);
      var3 = null;
      var1.setline(30);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("build_clib", var7, build_clib$2);
      var1.setlocal("build_clib", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject show_compilers$1(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      String[] var3 = new String[]{"show_compilers"};
      PyObject[] var5 = imp.importFrom("distutils.ccompiler", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(27);
      var1.getlocal(0).__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject build_clib$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(32);
      PyString var3 = PyString.fromInterned("build C/C++ libraries used by Python extensions");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(34);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("build-clib="), PyString.fromInterned("b"), PyString.fromInterned("directory to build C/C++ libraries to")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-temp="), PyString.fromInterned("t"), PyString.fromInterned("directory to put temporary build by-products")}), new PyTuple(new PyObject[]{PyString.fromInterned("debug"), PyString.fromInterned("g"), PyString.fromInterned("compile with debugging information")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("f"), PyString.fromInterned("forcibly build everything (ignore file timestamps)")}), new PyTuple(new PyObject[]{PyString.fromInterned("compiler="), PyString.fromInterned("c"), PyString.fromInterned("specify the compiler type")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(47);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("debug"), PyString.fromInterned("force")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(49);
      var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("help-compiler"), var1.getname("None"), PyString.fromInterned("list available compilers"), var1.getname("show_compilers")})});
      var1.setlocal("help_options", var4);
      var3 = null;
      var1.setline(54);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$3, (PyObject)null);
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(70);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$4, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(95);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$5, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(119);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, check_library_list$6, PyString.fromInterned("Ensure that the list of libraries is valid.\n\n        `library` is presumably provided as a command option 'libraries'.\n        This method checks that it is a list of 2-tuples, where the tuples\n        are (library_name, build_info_dict).\n\n        Raise DistutilsSetupError if the structure is invalid anywhere;\n        just returns otherwise.\n        "));
      var1.setlocal("check_library_list", var6);
      var3 = null;
      var1.setline(155);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_library_names$7, (PyObject)null);
      var1.setlocal("get_library_names", var6);
      var3 = null;
      var1.setline(167);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_source_files$8, (PyObject)null);
      var1.setlocal("get_source_files", var6);
      var3 = null;
      var1.setline(181);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, build_libraries$9, (PyObject)null);
      var1.setlocal("build_libraries", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_clib", var3);
      var3 = null;
      var1.setline(56);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_temp", var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("libraries", var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("include_dirs", var3);
      var3 = null;
      var1.setline(63);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("define", var3);
      var3 = null;
      var1.setline(64);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("undef", var3);
      var3 = null;
      var1.setline(65);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("debug", var3);
      var3 = null;
      var1.setline(66);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"force", var4);
      var3 = null;
      var1.setline(67);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("compiler", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$4(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var10000 = var1.getlocal(0).__getattr__("set_undefined_options");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("build"), new PyTuple(new PyObject[]{PyString.fromInterned("build_temp"), PyString.fromInterned("build_clib")}), new PyTuple(new PyObject[]{PyString.fromInterned("build_temp"), PyString.fromInterned("build_temp")}), new PyTuple(new PyObject[]{PyString.fromInterned("compiler"), PyString.fromInterned("compiler")}), new PyTuple(new PyObject[]{PyString.fromInterned("debug"), PyString.fromInterned("debug")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("force")})};
      var10000.__call__(var2, var3);
      var1.setline(83);
      PyObject var4 = var1.getlocal(0).__getattr__("distribution").__getattr__("libraries");
      var1.getlocal(0).__setattr__("libraries", var4);
      var3 = null;
      var1.setline(84);
      if (var1.getlocal(0).__getattr__("libraries").__nonzero__()) {
         var1.setline(85);
         var1.getlocal(0).__getattr__("check_library_list").__call__(var2, var1.getlocal(0).__getattr__("libraries"));
      }

      var1.setline(87);
      var4 = var1.getlocal(0).__getattr__("include_dirs");
      var10000 = var4._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(88);
         Object var6 = var1.getlocal(0).__getattr__("distribution").__getattr__("include_dirs");
         if (!((PyObject)var6).__nonzero__()) {
            var6 = new PyList(Py.EmptyObjects);
         }

         Object var5 = var6;
         var1.getlocal(0).__setattr__((String)"include_dirs", (PyObject)var5);
         var3 = null;
      }

      var1.setline(89);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("include_dirs"), var1.getglobal("str")).__nonzero__()) {
         var1.setline(90);
         var4 = var1.getlocal(0).__getattr__("include_dirs").__getattr__("split").__call__(var2, var1.getglobal("os").__getattr__("pathsep"));
         var1.getlocal(0).__setattr__("include_dirs", var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$5(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      if (var1.getlocal(0).__getattr__("libraries").__not__().__nonzero__()) {
         var1.setline(97);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(100);
         String[] var3 = new String[]{"new_compiler"};
         PyObject[] var7 = imp.importFrom("distutils.ccompiler", var3, var1, -1);
         PyObject var4 = var7[0];
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(101);
         PyObject var10000 = var1.getlocal(1);
         var7 = new PyObject[]{var1.getlocal(0).__getattr__("compiler"), var1.getlocal(0).__getattr__("dry_run"), var1.getlocal(0).__getattr__("force")};
         String[] var9 = new String[]{"compiler", "dry_run", "force"};
         var10000 = var10000.__call__(var2, var7, var9);
         var3 = null;
         PyObject var8 = var10000;
         var1.getlocal(0).__setattr__("compiler", var8);
         var3 = null;
         var1.setline(104);
         var1.getglobal("customize_compiler").__call__(var2, var1.getlocal(0).__getattr__("compiler"));
         var1.setline(106);
         var8 = var1.getlocal(0).__getattr__("include_dirs");
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(107);
            var1.getlocal(0).__getattr__("compiler").__getattr__("set_include_dirs").__call__(var2, var1.getlocal(0).__getattr__("include_dirs"));
         }

         var1.setline(108);
         var8 = var1.getlocal(0).__getattr__("define");
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(110);
            var8 = var1.getlocal(0).__getattr__("define").__iter__();

            while(true) {
               var1.setline(110);
               var4 = var8.__iternext__();
               if (var4 == null) {
                  break;
               }

               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(2, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(111);
               var1.getlocal(0).__getattr__("compiler").__getattr__("define_macro").__call__(var2, var1.getlocal(2), var1.getlocal(3));
            }
         }

         var1.setline(112);
         var8 = var1.getlocal(0).__getattr__("undef");
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(113);
            var8 = var1.getlocal(0).__getattr__("undef").__iter__();

            while(true) {
               var1.setline(113);
               var4 = var8.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(4, var4);
               var1.setline(114);
               var1.getlocal(0).__getattr__("compiler").__getattr__("undefine_macro").__call__(var2, var1.getlocal(4));
            }
         }

         var1.setline(116);
         var1.getlocal(0).__getattr__("build_libraries").__call__(var2, var1.getlocal(0).__getattr__("libraries"));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject check_library_list$6(PyFrame var1, ThreadState var2) {
      var1.setline(128);
      PyString.fromInterned("Ensure that the list of libraries is valid.\n\n        `library` is presumably provided as a command option 'libraries'.\n        This method checks that it is a list of 2-tuples, where the tuples\n        are (library_name, build_info_dict).\n\n        Raise DistutilsSetupError if the structure is invalid anywhere;\n        just returns otherwise.\n        ");
      var1.setline(129);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("list")).__not__().__nonzero__()) {
         var1.setline(130);
         throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("'libraries' option must be a list of tuples"));
      } else {
         var1.setline(133);
         PyObject var3 = var1.getlocal(1).__iter__();

         do {
            var1.setline(133);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(2, var4);
            var1.setline(134);
            PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("tuple")).__not__();
            PyObject var5;
            if (var10000.__nonzero__()) {
               var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var5._ne(Py.newInteger(2));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(135);
               throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("each element of 'libraries' must a 2-tuple"));
            }

            var1.setline(138);
            var5 = var1.getlocal(2);
            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(3, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(4, var7);
            var7 = null;
            var5 = null;
            var1.setline(140);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("str")).__not__().__nonzero__()) {
               var1.setline(141);
               throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("first element of each tuple in 'libraries' ")._add(PyString.fromInterned("must be a string (the library name)")));
            }

            var1.setline(144);
            PyString var8 = PyString.fromInterned("/");
            var10000 = var8._in(var1.getlocal(3));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var5 = var1.getglobal("os").__getattr__("sep");
               var10000 = var5._ne(PyString.fromInterned("/"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var5 = var1.getglobal("os").__getattr__("sep");
                  var10000 = var5._in(var1.getlocal(3));
                  var5 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(145);
               throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("bad library name '%s': ")._add(PyString.fromInterned("may not contain directory separators"))._mod(var1.getlocal(2).__getitem__(Py.newInteger(0))));
            }

            var1.setline(150);
         } while(!var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("dict")).__not__().__nonzero__());

         var1.setline(151);
         throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("second element of each tuple in 'libraries' ")._add(PyString.fromInterned("must be a dictionary (build info)")));
      }
   }

   public PyObject get_library_names$7(PyFrame var1, ThreadState var2) {
      var1.setline(158);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("libraries").__not__().__nonzero__()) {
         var1.setline(159);
         var3 = var1.getglobal("None");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(161);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(162);
         PyObject var8 = var1.getlocal(0).__getattr__("libraries").__iter__();

         while(true) {
            var1.setline(162);
            PyObject var5 = var8.__iternext__();
            if (var5 == null) {
               var1.setline(164);
               var3 = var1.getlocal(1);
               var1.f_lasti = -1;
               return var3;
            }

            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(2, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(3, var7);
            var7 = null;
            var1.setline(163);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject get_source_files$8(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      var1.getlocal(0).__getattr__("check_library_list").__call__(var2, var1.getlocal(0).__getattr__("libraries"));
      var1.setline(169);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(170);
      PyObject var7 = var1.getlocal(0).__getattr__("libraries").__iter__();

      while(true) {
         var1.setline(170);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(179);
            var7 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var7;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(171);
         PyObject var8 = var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sources"));
         var1.setlocal(4, var8);
         var5 = null;
         var1.setline(172);
         var8 = var1.getlocal(4);
         PyObject var10000 = var8._is(var1.getglobal("None"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(173);
            throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("in 'libraries' option (library '%s'), 'sources' must be present and must be a list of source filenames")._mod(var1.getlocal(2)));
         }

         var1.setline(178);
         var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(4));
      }
   }

   public PyObject build_libraries$9(PyFrame var1, ThreadState var2) {
      var1.setline(182);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(182);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(183);
         PyObject var7 = var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sources"));
         var1.setlocal(4, var7);
         var5 = null;
         var1.setline(184);
         var7 = var1.getlocal(4);
         PyObject var10000 = var7._is(var1.getglobal("None"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(185);
            throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("in 'libraries' option (library '%s'), ")._add(PyString.fromInterned("'sources' must be present and must be "))._add(PyString.fromInterned("a list of source filenames"))._mod(var1.getlocal(2)));
         }

         var1.setline(189);
         var7 = var1.getglobal("list").__call__(var2, var1.getlocal(4));
         var1.setlocal(4, var7);
         var5 = null;
         var1.setline(191);
         var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("building '%s' library"), (PyObject)var1.getlocal(2));
         var1.setline(196);
         var7 = var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("macros"));
         var1.setlocal(5, var7);
         var5 = null;
         var1.setline(197);
         var7 = var1.getlocal(3).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("include_dirs"));
         var1.setlocal(6, var7);
         var5 = null;
         var1.setline(198);
         var10000 = var1.getlocal(0).__getattr__("compiler").__getattr__("compile");
         var5 = new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("build_temp"), var1.getlocal(5), var1.getlocal(6), var1.getlocal(0).__getattr__("debug")};
         String[] var8 = new String[]{"output_dir", "macros", "include_dirs", "debug"};
         var10000 = var10000.__call__(var2, var5, var8);
         var5 = null;
         var7 = var10000;
         var1.setlocal(7, var7);
         var5 = null;
         var1.setline(207);
         var10000 = var1.getlocal(0).__getattr__("compiler").__getattr__("create_static_lib");
         var5 = new PyObject[]{var1.getlocal(7), var1.getlocal(2), var1.getlocal(0).__getattr__("build_clib"), var1.getlocal(0).__getattr__("debug")};
         var8 = new String[]{"output_dir", "debug"};
         var10000.__call__(var2, var5, var8);
         var5 = null;
      }
   }

   public build_clib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"show_compilers"};
      show_compilers$1 = Py.newCode(0, var2, var1, "show_compilers", 25, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      build_clib$2 = Py.newCode(0, var2, var1, "build_clib", 30, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$3 = Py.newCode(1, var2, var1, "initialize_options", 54, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_options$4 = Py.newCode(1, var2, var1, "finalize_options", 70, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "new_compiler", "name", "value", "macro"};
      run$5 = Py.newCode(1, var2, var1, "run", 95, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "libraries", "lib", "name", "build_info"};
      check_library_list$6 = Py.newCode(2, var2, var1, "check_library_list", 119, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lib_names", "lib_name", "build_info"};
      get_library_names$7 = Py.newCode(1, var2, var1, "get_library_names", 155, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filenames", "lib_name", "build_info", "sources"};
      get_source_files$8 = Py.newCode(1, var2, var1, "get_source_files", 167, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "libraries", "lib_name", "build_info", "sources", "macros", "include_dirs", "objects"};
      build_libraries$9 = Py.newCode(2, var2, var1, "build_libraries", 181, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new build_clib$py("distutils/command/build_clib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(build_clib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.show_compilers$1(var2, var3);
         case 2:
            return this.build_clib$2(var2, var3);
         case 3:
            return this.initialize_options$3(var2, var3);
         case 4:
            return this.finalize_options$4(var2, var3);
         case 5:
            return this.run$5(var2, var3);
         case 6:
            return this.check_library_list$6(var2, var3);
         case 7:
            return this.get_library_names$7(var2, var3);
         case 8:
            return this.get_source_files$8(var2, var3);
         case 9:
            return this.build_libraries$9(var2, var3);
         default:
            return null;
      }
   }
}
