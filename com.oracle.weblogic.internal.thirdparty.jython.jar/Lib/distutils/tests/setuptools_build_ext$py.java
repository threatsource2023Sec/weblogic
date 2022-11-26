package distutils.tests;

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
@Filename("distutils/tests/setuptools_build_ext.py")
public class setuptools_build_ext$py extends PyFunctionTable implements PyRunnable {
   static setuptools_build_ext$py self;
   static final PyCode f$0;
   static final PyCode if_dl$1;
   static final PyCode build_ext$2;
   static final PyCode run$3;
   static final PyCode copy_extensions_to_source$4;
   static final PyCode swig_sources$5;
   static final PyCode get_ext_filename$6;
   static final PyCode initialize_options$7;
   static final PyCode finalize_options$8;
   static final PyCode setup_shlib_compiler$9;
   static final PyCode get_export_symbols$10;
   static final PyCode build_extension$11;
   static final PyCode links_to_dynamic$12;
   static final PyCode get_outputs$13;
   static final PyCode write_stub$14;
   static final PyCode link_shared_object$15;
   static final PyCode link_shared_object$16;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      String[] var3 = new String[]{"build_ext"};
      PyObject[] var7 = imp.importFrom("distutils.command.build_ext", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("_du_build_ext", var4);
      var4 = null;

      PyException var8;
      try {
         var1.setline(4);
         var3 = new String[]{"build_ext"};
         var7 = imp.importFrom("Pyrex.Distutils.build_ext", var3, var1, -1);
         var4 = var7[0];
         var1.setlocal("_build_ext", var4);
         var4 = null;
      } catch (Throwable var6) {
         var8 = Py.setException(var6, var1);
         if (!var8.match(var1.getname("ImportError"))) {
            throw var8;
         }

         var1.setline(6);
         var4 = var1.getname("_du_build_ext");
         var1.setlocal("_build_ext", var4);
         var4 = null;
      }

      var1.setline(8);
      PyObject var9 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var9);
      var3 = null;
      var9 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var9);
      var3 = null;
      var1.setline(9);
      var3 = new String[]{"copy_file"};
      var7 = imp.importFrom("distutils.file_util", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("copy_file", var4);
      var4 = null;
      var1.setline(11);
      var3 = new String[]{"Library"};
      var7 = imp.importFrom("distutils.tests.setuptools_extension", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("Library", var4);
      var4 = null;
      var1.setline(13);
      var3 = new String[]{"new_compiler"};
      var7 = imp.importFrom("distutils.ccompiler", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("new_compiler", var4);
      var4 = null;
      var1.setline(14);
      var3 = new String[]{"customize_compiler", "get_config_var"};
      var7 = imp.importFrom("distutils.sysconfig", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("customize_compiler", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("get_config_var", var4);
      var4 = null;
      var1.setline(15);
      var1.getname("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LDSHARED"));
      var1.setline(16);
      var3 = new String[]{"_config_vars"};
      var7 = imp.importFrom("distutils.sysconfig", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("_config_vars", var4);
      var4 = null;
      var1.setline(17);
      var3 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var3, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(18);
      imp.importAll("distutils.errors", var1, -1);
      var1.setline(20);
      var9 = var1.getname("False");
      var1.setlocal("have_rtld", var9);
      var3 = null;
      var1.setline(21);
      var9 = var1.getname("False");
      var1.setlocal("use_stubs", var9);
      var3 = null;
      var1.setline(22);
      PyString var10 = PyString.fromInterned("shared");
      var1.setlocal("libtype", var10);
      var3 = null;
      var1.setline(24);
      var9 = var1.getname("sys").__getattr__("platform");
      PyObject var10000 = var9._eq(PyString.fromInterned("darwin"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(25);
         var9 = var1.getname("True");
         var1.setlocal("use_stubs", var9);
         var3 = null;
      } else {
         var1.setline(26);
         var9 = var1.getname("os").__getattr__("name");
         var10000 = var9._ne(PyString.fromInterned("nt"));
         var3 = null;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(28);
               var3 = new String[]{"RTLD_NOW"};
               var7 = imp.importFrom("dl", var3, var1, -1);
               var4 = var7[0];
               var1.setlocal("RTLD_NOW", var4);
               var4 = null;
               var1.setline(29);
               var9 = var1.getname("True");
               var1.setlocal("have_rtld", var9);
               var3 = null;
               var1.setline(30);
               var9 = var1.getname("True");
               var1.setlocal("use_stubs", var9);
               var3 = null;
            } catch (Throwable var5) {
               var8 = Py.setException(var5, var1);
               if (!var8.match(var1.getname("ImportError"))) {
                  throw var8;
               }

               var1.setline(32);
            }
         }
      }

      var1.setline(34);
      var7 = Py.EmptyObjects;
      PyFunction var11 = new PyFunction(var1.f_globals, var7, if_dl$1, (PyObject)null);
      var1.setlocal("if_dl", var11);
      var3 = null;
      var1.setline(44);
      var7 = new PyObject[]{var1.getname("_build_ext")};
      var4 = Py.makeClass("build_ext", var7, build_ext$2);
      var1.setlocal("build_ext", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(249);
      var10000 = var1.getname("use_stubs");
      if (!var10000.__nonzero__()) {
         var9 = var1.getname("os").__getattr__("name");
         var10000 = var9._eq(PyString.fromInterned("nt"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(252);
         var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
         var11 = new PyFunction(var1.f_globals, var7, link_shared_object$15, (PyObject)null);
         var1.setlocal("link_shared_object", var11);
         var3 = null;
      } else {
         var1.setline(264);
         var10 = PyString.fromInterned("static");
         var1.setlocal("libtype", var10);
         var3 = null;
         var1.setline(266);
         var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
         var11 = new PyFunction(var1.f_globals, var7, link_shared_object$16, (PyObject)null);
         var1.setlocal("link_shared_object", var11);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject if_dl$1(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      if (var1.getglobal("have_rtld").__nonzero__()) {
         var1.setline(36);
         PyObject var4 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(37);
         PyString var3 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject build_ext$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(45);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, run$3, PyString.fromInterned("Build extensions in build directory, then copy if --inplace"));
      var1.setlocal("run", var4);
      var3 = null;
      var1.setline(53);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, copy_extensions_to_source$4, (PyObject)null);
      var1.setlocal("copy_extensions_to_source", var4);
      var3 = null;
      var1.setline(75);
      PyObject var5 = var1.getname("_build_ext");
      PyObject var10000 = var5._isnot(var1.getname("_du_build_ext"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getname("hasattr").__call__((ThreadState)var2, (PyObject)var1.getname("_build_ext"), (PyObject)PyString.fromInterned("pyrex_sources")).__not__();
      }

      if (var10000.__nonzero__()) {
         var1.setline(77);
         var3 = Py.EmptyObjects;
         var4 = new PyFunction(var1.f_globals, var3, swig_sources$5, (PyObject)null);
         var1.setlocal("swig_sources", var4);
         var3 = null;
      }

      var1.setline(85);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_ext_filename$6, (PyObject)null);
      var1.setlocal("get_ext_filename", var4);
      var3 = null;
      var1.setline(97);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, initialize_options$7, (PyObject)null);
      var1.setlocal("initialize_options", var4);
      var3 = null;
      var1.setline(103);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, finalize_options$8, (PyObject)null);
      var1.setlocal("finalize_options", var4);
      var3 = null;
      var1.setline(126);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setup_shlib_compiler$9, (PyObject)null);
      var1.setlocal("setup_shlib_compiler", var4);
      var3 = null;
      var1.setline(167);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_export_symbols$10, (PyObject)null);
      var1.setlocal("get_export_symbols", var4);
      var3 = null;
      var1.setline(172);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, build_extension$11, (PyObject)null);
      var1.setlocal("build_extension", var4);
      var3 = null;
      var1.setline(185);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, links_to_dynamic$12, PyString.fromInterned("Return true if 'ext' links to a dynamic lib in the same package"));
      var1.setlocal("links_to_dynamic", var4);
      var3 = null;
      var1.setline(196);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_outputs$13, (PyObject)null);
      var1.setlocal("get_outputs", var4);
      var3 = null;
      var1.setline(208);
      var3 = new PyObject[]{var1.getname("False")};
      var4 = new PyFunction(var1.f_globals, var3, write_stub$14, (PyObject)null);
      var1.setlocal("write_stub", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject run$3(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      PyString.fromInterned("Build extensions in build directory, then copy if --inplace");
      var1.setline(47);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("inplace"), Py.newInteger(0)});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("inplace", var5);
      var5 = null;
      var3 = null;
      var1.setline(48);
      var1.getglobal("_build_ext").__getattr__("run").__call__(var2, var1.getlocal(0));
      var1.setline(49);
      PyObject var6 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("inplace", var6);
      var3 = null;
      var1.setline(50);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(51);
         var1.getlocal(0).__getattr__("copy_extensions_to_source").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject copy_extensions_to_source$4(PyFrame var1, ThreadState var2) {
      var1.setline(54);
      PyObject var3 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_py"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(55);
      var3 = var1.getlocal(0).__getattr__("extensions").__iter__();

      while(true) {
         var1.setline(55);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(56);
         PyObject var5 = var1.getlocal(0).__getattr__("get_ext_fullname").__call__(var2, var1.getlocal(2).__getattr__("name"));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(57);
         var5 = var1.getlocal(0).__getattr__("get_ext_filename").__call__(var2, var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(58);
         var5 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(59);
         var5 = PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(60);
         var5 = var1.getlocal(1).__getattr__("get_package_dir").__call__(var2, var1.getlocal(6));
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(61);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(7), var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(4)));
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(62);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("build_lib"), var1.getlocal(4));
         var1.setlocal(9, var5);
         var5 = null;
         var1.setline(67);
         PyObject var10000 = var1.getglobal("copy_file");
         PyObject[] var7 = new PyObject[]{var1.getlocal(9), var1.getlocal(8), var1.getlocal(0).__getattr__("verbose"), var1.getlocal(0).__getattr__("dry_run")};
         String[] var6 = new String[]{"verbose", "dry_run"};
         var10000.__call__(var2, var7, var6);
         var5 = null;
         var1.setline(71);
         if (var1.getlocal(2).__getattr__("_needs_stub").__nonzero__()) {
            var1.setline(72);
            var10000 = var1.getlocal(0).__getattr__("write_stub");
            PyObject var10002 = var1.getlocal(7);
            if (!var10002.__nonzero__()) {
               var10002 = var1.getglobal("os").__getattr__("curdir");
            }

            var10000.__call__(var2, var10002, var1.getlocal(2), var1.getglobal("True"));
         }
      }
   }

   public PyObject swig_sources$5(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var10000 = var1.getglobal("_build_ext").__getattr__("swig_sources").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(1);
      }

      PyObject var3 = var10000;
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(81);
      var10000 = var1.getglobal("_du_build_ext").__getattr__("swig_sources");
      PyObject[] var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var5, var4, var1.getlocal(2), (PyObject)null);
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_ext_filename$6(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyObject var3 = var1.getglobal("_build_ext").__getattr__("get_ext_filename").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(87);
      var3 = var1.getlocal(0).__getattr__("ext_map").__getitem__(var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(88);
      PyObject[] var5;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("Library")).__nonzero__()) {
         var1.setline(89);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(2));
         PyObject[] var7 = Py.unpackSequence(var3, 2);
         PyObject var8 = var7[0];
         var1.setlocal(4, var8);
         var5 = null;
         var8 = var7[1];
         var1.setlocal(3, var8);
         var5 = null;
         var3 = null;
         var1.setline(90);
         var3 = var1.getlocal(0).__getattr__("shlib_compiler").__getattr__("library_filename").__call__(var2, var1.getlocal(4), var1.getglobal("libtype"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(91);
         PyObject var10000 = var1.getglobal("use_stubs");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(3).__getattr__("_links_to_dynamic");
         }

         if (var10000.__nonzero__()) {
            var1.setline(92);
            PyObject var4 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(2));
            var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var4 = null;
            var1.setline(93);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), PyString.fromInterned("dl-")._add(var1.getlocal(4)));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(95);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject initialize_options$7(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      var1.getglobal("_build_ext").__getattr__("initialize_options").__call__(var2, var1.getlocal(0));
      var1.setline(99);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("shlib_compiler", var3);
      var3 = null;
      var1.setline(100);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"shlibs", var4);
      var3 = null;
      var1.setline(101);
      PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"ext_map", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$8(PyFrame var1, ThreadState var2) {
      var1.setline(104);
      var1.getglobal("_build_ext").__getattr__("finalize_options").__call__(var2, var1.getlocal(0));
      var1.setline(105);
      Object var10000 = var1.getlocal(0).__getattr__("extensions");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(Py.EmptyObjects);
      }

      Object var3 = var10000;
      var1.getlocal(0).__setattr__((String)"extensions", (PyObject)var3);
      var3 = null;
      var1.setline(106);
      var1.getlocal(0).__getattr__("check_extensions_list").__call__(var2, var1.getlocal(0).__getattr__("extensions"));
      var1.setline(107);
      PyList var8 = new PyList();
      PyObject var6 = var8.__getattr__("append");
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(107);
      var6 = var1.getlocal(0).__getattr__("extensions").__iter__();

      while(true) {
         var1.setline(107);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(107);
            var1.dellocal(1);
            PyList var7 = var8;
            var1.getlocal(0).__setattr__((String)"shlibs", var7);
            var3 = null;
            var1.setline(109);
            if (var1.getlocal(0).__getattr__("shlibs").__nonzero__()) {
               var1.setline(110);
               var1.getlocal(0).__getattr__("setup_shlib_compiler").__call__(var2);
            }

            var1.setline(111);
            var6 = var1.getlocal(0).__getattr__("extensions").__iter__();

            while(true) {
               var1.setline(111);
               var4 = var6.__iternext__();
               PyObject var5;
               if (var4 == null) {
                  var1.setline(113);
                  var6 = var1.getlocal(0).__getattr__("extensions").__iter__();

                  while(true) {
                     var1.setline(113);
                     var4 = var6.__iternext__();
                     if (var4 == null) {
                        var1.f_lasti = -1;
                        return Py.None;
                     }

                     var1.setlocal(2, var4);
                     var1.setline(114);
                     var5 = var1.getlocal(2).__getattr__("_full_name");
                     var1.setlocal(3, var5);
                     var5 = null;
                     var1.setline(115);
                     var5 = var1.getlocal(2);
                     var1.getlocal(0).__getattr__("ext_map").__setitem__(var1.getlocal(3), var5);
                     var5 = null;
                     var1.setline(116);
                     PyObject var9 = var1.getlocal(0).__getattr__("shlibs");
                     if (var9.__nonzero__()) {
                        var9 = var1.getlocal(0).__getattr__("links_to_dynamic").__call__(var2, var1.getlocal(2));
                     }

                     if (!var9.__nonzero__()) {
                        var9 = var1.getglobal("False");
                     }

                     var5 = var9;
                     var1.setlocal(4, var5);
                     var1.getlocal(2).__setattr__("_links_to_dynamic", var5);
                     var1.setline(118);
                     var9 = var1.getlocal(4);
                     if (var9.__nonzero__()) {
                        var9 = var1.getglobal("use_stubs");
                        if (var9.__nonzero__()) {
                           var9 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("Library")).__not__();
                        }
                     }

                     var5 = var9;
                     var1.getlocal(2).__setattr__("_needs_stub", var5);
                     var5 = null;
                     var1.setline(119);
                     var5 = var1.getlocal(0).__getattr__("get_ext_filename").__call__(var2, var1.getlocal(3));
                     var1.setlocal(5, var5);
                     var1.getlocal(2).__setattr__("_file_name", var5);
                     var1.setline(120);
                     var5 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("build_lib"), var1.getlocal(5)));
                     var1.setlocal(6, var5);
                     var5 = null;
                     var1.setline(121);
                     var9 = var1.getlocal(4);
                     if (var9.__nonzero__()) {
                        var5 = var1.getlocal(6);
                        var9 = var5._notin(var1.getlocal(2).__getattr__("library_dirs"));
                        var5 = null;
                     }

                     if (var9.__nonzero__()) {
                        var1.setline(122);
                        var1.getlocal(2).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getlocal(6));
                     }

                     var1.setline(123);
                     var9 = var1.getlocal(4);
                     if (var9.__nonzero__()) {
                        var9 = var1.getglobal("use_stubs");
                        if (var9.__nonzero__()) {
                           var5 = var1.getglobal("os").__getattr__("curdir");
                           var9 = var5._notin(var1.getlocal(2).__getattr__("runtime_library_dirs"));
                           var5 = null;
                        }
                     }

                     if (var9.__nonzero__()) {
                        var1.setline(124);
                        var1.getlocal(2).__getattr__("runtime_library_dirs").__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("curdir"));
                     }
                  }
               }

               var1.setlocal(2, var4);
               var1.setline(112);
               var5 = var1.getlocal(0).__getattr__("get_ext_fullname").__call__(var2, var1.getlocal(2).__getattr__("name"));
               var1.getlocal(2).__setattr__("_full_name", var5);
               var5 = null;
            }
         }

         var1.setlocal(2, var4);
         var1.setline(108);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("Library")).__nonzero__()) {
            var1.setline(107);
            var1.getlocal(1).__call__(var2, var1.getlocal(2));
         }
      }
   }

   public PyObject setup_shlib_compiler$9(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyObject var10000 = var1.getglobal("new_compiler");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("compiler"), var1.getlocal(0).__getattr__("dry_run"), var1.getlocal(0).__getattr__("force")};
      String[] var4 = new String[]{"compiler", "dry_run", "force"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var8 = var10000;
      var1.setlocal(1, var8);
      var1.getlocal(0).__setattr__("shlib_compiler", var8);
      var1.setline(130);
      var8 = var1.getglobal("sys").__getattr__("platform");
      var10000 = var8._eq(PyString.fromInterned("darwin"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(131);
         var8 = var1.getglobal("_config_vars").__getattr__("copy").__call__(var2);
         var1.setlocal(2, var8);
         var3 = null;
         var3 = null;

         try {
            var1.setline(134);
            PyString var9 = PyString.fromInterned("gcc -Wl,-x -dynamiclib -undefined dynamic_lookup");
            var1.getglobal("_config_vars").__setitem__((PyObject)PyString.fromInterned("LDSHARED"), var9);
            var4 = null;
            var1.setline(135);
            var9 = PyString.fromInterned(" -dynamiclib");
            var1.getglobal("_config_vars").__setitem__((PyObject)PyString.fromInterned("CCSHARED"), var9);
            var4 = null;
            var1.setline(136);
            var9 = PyString.fromInterned(".dylib");
            var1.getglobal("_config_vars").__setitem__((PyObject)PyString.fromInterned("SO"), var9);
            var4 = null;
            var1.setline(137);
            var1.getglobal("customize_compiler").__call__(var2, var1.getlocal(1));
         } catch (Throwable var7) {
            Py.addTraceback(var7, var1);
            var1.setline(139);
            var1.getglobal("_config_vars").__getattr__("clear").__call__(var2);
            var1.setline(140);
            var1.getglobal("_config_vars").__getattr__("update").__call__(var2, var1.getlocal(2));
            throw (Throwable)var7;
         }

         var1.setline(139);
         var1.getglobal("_config_vars").__getattr__("clear").__call__(var2);
         var1.setline(140);
         var1.getglobal("_config_vars").__getattr__("update").__call__(var2, var1.getlocal(2));
      } else {
         var1.setline(142);
         var1.getglobal("customize_compiler").__call__(var2, var1.getlocal(1));
      }

      var1.setline(144);
      var8 = var1.getlocal(0).__getattr__("include_dirs");
      var10000 = var8._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(145);
         var1.getlocal(1).__getattr__("set_include_dirs").__call__(var2, var1.getlocal(0).__getattr__("include_dirs"));
      }

      var1.setline(146);
      var8 = var1.getlocal(0).__getattr__("define");
      var10000 = var8._isnot(var1.getglobal("None"));
      var3 = null;
      PyObject var10;
      if (var10000.__nonzero__()) {
         var1.setline(148);
         var8 = var1.getlocal(0).__getattr__("define").__iter__();

         while(true) {
            var1.setline(148);
            var10 = var8.__iternext__();
            if (var10 == null) {
               break;
            }

            PyObject[] var5 = Py.unpackSequence(var10, 2);
            PyObject var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var1.setline(149);
            var1.getlocal(1).__getattr__("define_macro").__call__(var2, var1.getlocal(3), var1.getlocal(4));
         }
      }

      var1.setline(150);
      var8 = var1.getlocal(0).__getattr__("undef");
      var10000 = var8._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(151);
         var8 = var1.getlocal(0).__getattr__("undef").__iter__();

         while(true) {
            var1.setline(151);
            var10 = var8.__iternext__();
            if (var10 == null) {
               break;
            }

            var1.setlocal(5, var10);
            var1.setline(152);
            var1.getlocal(1).__getattr__("undefine_macro").__call__(var2, var1.getlocal(5));
         }
      }

      var1.setline(153);
      var8 = var1.getlocal(0).__getattr__("libraries");
      var10000 = var8._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(154);
         var1.getlocal(1).__getattr__("set_libraries").__call__(var2, var1.getlocal(0).__getattr__("libraries"));
      }

      var1.setline(155);
      var8 = var1.getlocal(0).__getattr__("library_dirs");
      var10000 = var8._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(156);
         var1.getlocal(1).__getattr__("set_library_dirs").__call__(var2, var1.getlocal(0).__getattr__("library_dirs"));
      }

      var1.setline(157);
      var8 = var1.getlocal(0).__getattr__("rpath");
      var10000 = var8._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(158);
         var1.getlocal(1).__getattr__("set_runtime_library_dirs").__call__(var2, var1.getlocal(0).__getattr__("rpath"));
      }

      var1.setline(159);
      var8 = var1.getlocal(0).__getattr__("link_objects");
      var10000 = var8._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(160);
         var1.getlocal(1).__getattr__("set_link_objects").__call__(var2, var1.getlocal(0).__getattr__("link_objects"));
      }

      var1.setline(163);
      var8 = var1.getglobal("link_shared_object").__getattr__("__get__").__call__(var2, var1.getlocal(1));
      var1.getlocal(1).__setattr__("link_shared_object", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_export_symbols$10(PyFrame var1, ThreadState var2) {
      var1.setline(168);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Library")).__nonzero__()) {
         var1.setline(169);
         var3 = var1.getlocal(1).__getattr__("export_symbols");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(170);
         var3 = var1.getglobal("_build_ext").__getattr__("get_export_symbols").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject build_extension$11(PyFrame var1, ThreadState var2) {
      var1.setline(173);
      PyObject var3 = var1.getlocal(0).__getattr__("compiler");
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(175);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("Library")).__nonzero__()) {
            var1.setline(176);
            var4 = var1.getlocal(0).__getattr__("shlib_compiler");
            var1.getlocal(0).__setattr__("compiler", var4);
            var4 = null;
         }

         var1.setline(177);
         var1.getglobal("_build_ext").__getattr__("build_extension").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.setline(178);
         if (var1.getlocal(1).__getattr__("_needs_stub").__nonzero__()) {
            var1.setline(179);
            var1.getlocal(0).__getattr__("write_stub").__call__(var2, var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_py")).__getattr__("build_lib"), var1.getlocal(1));
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(183);
         var4 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("compiler", var4);
         var4 = null;
         throw (Throwable)var5;
      }

      var1.setline(183);
      var4 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("compiler", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject links_to_dynamic$12(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyString.fromInterned("Return true if 'ext' links to a dynamic lib in the same package");
      var1.setline(190);
      PyObject var10000 = var1.getglobal("dict").__getattr__("fromkeys");
      PyList var10002 = new PyList();
      PyObject var3 = var10002.__getattr__("append");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(190);
      var3 = var1.getlocal(0).__getattr__("shlibs").__iter__();

      while(true) {
         var1.setline(190);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(190);
            var1.dellocal(3);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(191);
            var3 = PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(1).__getattr__("_full_name").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)._add(new PyList(new PyObject[]{PyString.fromInterned("")})));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(192);
            var3 = var1.getlocal(1).__getattr__("libraries").__iter__();

            PyObject var5;
            do {
               var1.setline(192);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(194);
                  var5 = var1.getglobal("False");
                  var1.f_lasti = -1;
                  return var5;
               }

               var1.setlocal(6, var4);
               var1.setline(193);
               var5 = var1.getlocal(5)._add(var1.getlocal(6));
               var10000 = var5._in(var1.getlocal(2));
               var5 = null;
            } while(!var10000.__nonzero__());

            var1.setline(193);
            var5 = var1.getglobal("True");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(4, var4);
         var1.setline(190);
         var1.getlocal(3).__call__(var2, var1.getlocal(4).__getattr__("_full_name"));
      }
   }

   public PyObject get_outputs$13(PyFrame var1, ThreadState var2) {
      var1.setline(197);
      PyObject var3 = var1.getglobal("_build_ext").__getattr__("get_outputs").__call__(var2, var1.getlocal(0));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(198);
      var3 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_py")).__getattr__("optimize");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(199);
      var3 = var1.getlocal(0).__getattr__("extensions").__iter__();

      while(true) {
         var1.setline(199);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(206);
            var3 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(3, var4);
         var1.setline(200);
         if (var1.getlocal(3).__getattr__("_needs_stub").__nonzero__()) {
            var1.setline(201);
            PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
            PyObject[] var5 = new PyObject[]{var1.getlocal(0).__getattr__("build_lib")};
            String[] var6 = new String[0];
            var10000 = var10000._callextra(var5, var6, var1.getlocal(3).__getattr__("_full_name").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")), (PyObject)null);
            var5 = null;
            PyObject var7 = var10000;
            var1.setlocal(4, var7);
            var5 = null;
            var1.setline(202);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4)._add(PyString.fromInterned(".py")));
            var1.setline(203);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4)._add(PyString.fromInterned(".pyc")));
            var1.setline(204);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(205);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4)._add(PyString.fromInterned(".pyo")));
            }
         }
      }
   }

   public PyObject write_stub$14(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, PyString.fromInterned("writing stub loader for %s to %s"), (PyObject)var1.getlocal(2).__getattr__("_full_name"), (PyObject)var1.getlocal(1));
      var1.setline(210);
      PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(2).__getattr__("_full_name").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000._add(PyString.fromInterned(".py"));
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(211);
      var10000 = var1.getlocal(3);
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(4));
      }

      if (var10000.__nonzero__()) {
         var1.setline(212);
         throw Py.makeException(var1.getglobal("DistutilsError").__call__(var2, var1.getlocal(4)._add(PyString.fromInterned(" already exists! Please delete."))));
      } else {
         var1.setline(213);
         if (var1.getlocal(0).__getattr__("dry_run").__not__().__nonzero__()) {
            var1.setline(214);
            var5 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("w"));
            var1.setlocal(5, var5);
            var3 = null;
            var1.setline(215);
            var1.getlocal(5).__getattr__("write").__call__(var2, PyString.fromInterned("\n").__getattr__("join").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("def __bootstrap__():"), PyString.fromInterned("   global __bootstrap__, __file__, __loader__"), PyString.fromInterned("   import sys, os, pkg_resources, imp")._add(var1.getglobal("if_dl").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(", dl"))), PyString.fromInterned("   __file__ = pkg_resources.resource_filename(__name__,%r)")._mod(var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(2).__getattr__("_file_name"))), PyString.fromInterned("   del __bootstrap__"), PyString.fromInterned("   if '__loader__' in globals():"), PyString.fromInterned("       del __loader__"), var1.getglobal("if_dl").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("   old_flags = sys.getdlopenflags()")), PyString.fromInterned("   old_dir = os.getcwd()"), PyString.fromInterned("   try:"), PyString.fromInterned("     os.chdir(os.path.dirname(__file__))"), var1.getglobal("if_dl").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("     sys.setdlopenflags(dl.RTLD_NOW)")), PyString.fromInterned("     imp.load_dynamic(__name__,__file__)"), PyString.fromInterned("   finally:"), var1.getglobal("if_dl").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("     sys.setdlopenflags(old_flags)")), PyString.fromInterned("     os.chdir(old_dir)"), PyString.fromInterned("__bootstrap__()"), PyString.fromInterned("")}))));
            var1.setline(236);
            var1.getlocal(5).__getattr__("close").__call__(var2);
         }

         var1.setline(237);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(238);
            String[] var7 = new String[]{"byte_compile"};
            var3 = imp.importFrom("distutils.util", var7, var1, -1);
            PyObject var6 = var3[0];
            var1.setlocal(6, var6);
            var4 = null;
            var1.setline(239);
            var10000 = var1.getlocal(6);
            var3 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(4)}), Py.newInteger(0), var1.getglobal("True"), var1.getlocal(0).__getattr__("dry_run")};
            var4 = new String[]{"optimize", "force", "dry_run"};
            var10000.__call__(var2, var3, var4);
            var3 = null;
            var1.setline(241);
            var5 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("install_lib")).__getattr__("optimize");
            var1.setlocal(7, var5);
            var3 = null;
            var1.setline(242);
            var5 = var1.getlocal(7);
            var10000 = var5._gt(Py.newInteger(0));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(243);
               var10000 = var1.getlocal(6);
               var3 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(4)}), var1.getlocal(7), var1.getglobal("True"), var1.getlocal(0).__getattr__("dry_run")};
               var4 = new String[]{"optimize", "force", "dry_run"};
               var10000.__call__(var2, var3, var4);
               var3 = null;
            }

            var1.setline(245);
            var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(4));
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("dry_run").__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(246);
               var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(4));
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject link_shared_object$15(PyFrame var1, ThreadState var2) {
      var1.setline(256);
      PyObject var10000 = var1.getlocal(0).__getattr__("link");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("SHARED_LIBRARY"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), var1.getlocal(12)};
      var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject link_shared_object$16(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(278);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(2));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(13, var5);
      var5 = null;
      var3 = null;
      var1.setline(279);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(13));
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.setlocal(14, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(15, var5);
      var5 = null;
      var3 = null;
      var1.setline(280);
      if (var1.getlocal(0).__getattr__("library_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("x")).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lib")).__nonzero__()) {
         var1.setline(283);
         var3 = var1.getlocal(14).__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null);
         var1.setlocal(14, var3);
         var3 = null;
      }

      var1.setline(285);
      var10000 = var1.getlocal(0).__getattr__("create_static_lib");
      PyObject[] var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(14), var1.getlocal(3), var1.getlocal(8), var1.getlocal(12)};
      var10000.__call__(var2, var6);
      var1.f_lasti = -1;
      return Py.None;
   }

   public setuptools_build_ext$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"s"};
      if_dl$1 = Py.newCode(1, var2, var1, "if_dl", 34, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      build_ext$2 = Py.newCode(0, var2, var1, "build_ext", 44, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "old_inplace"};
      run$3 = Py.newCode(1, var2, var1, "run", 45, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "build_py", "ext", "fullname", "filename", "modpath", "package", "package_dir", "dest_filename", "src_filename"};
      copy_extensions_to_source$4 = Py.newCode(1, var2, var1, "copy_extensions_to_source", 53, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sources", "otherargs"};
      swig_sources$5 = Py.newCode(3, var2, var1, "swig_sources", 77, true, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname", "filename", "ext", "fn", "d"};
      get_ext_filename$6 = Py.newCode(2, var2, var1, "get_ext_filename", 85, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      initialize_options$7 = Py.newCode(1, var2, var1, "initialize_options", 97, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_[107_23]", "ext", "fullname", "ltd", "filename", "libdir"};
      finalize_options$8 = Py.newCode(1, var2, var1, "finalize_options", 103, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "compiler", "tmp", "name", "value", "macro"};
      setup_shlib_compiler$9 = Py.newCode(1, var2, var1, "setup_shlib_compiler", 126, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ext"};
      get_export_symbols$10 = Py.newCode(2, var2, var1, "get_export_symbols", 167, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ext", "_compiler"};
      build_extension$11 = Py.newCode(2, var2, var1, "build_extension", 172, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ext", "libnames", "_[190_34]", "lib", "pkg", "libname"};
      links_to_dynamic$12 = Py.newCode(2, var2, var1, "links_to_dynamic", 185, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "outputs", "optimize", "ext", "base"};
      get_outputs$13 = Py.newCode(1, var2, var1, "get_outputs", 196, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "output_dir", "ext", "compile", "stub_file", "f", "byte_compile", "optimize"};
      write_stub$14 = Py.newCode(4, var2, var1, "write_stub", 208, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects", "output_libname", "output_dir", "libraries", "library_dirs", "runtime_library_dirs", "export_symbols", "debug", "extra_preargs", "extra_postargs", "build_temp", "target_lang"};
      link_shared_object$15 = Py.newCode(13, var2, var1, "link_shared_object", 252, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects", "output_libname", "output_dir", "libraries", "library_dirs", "runtime_library_dirs", "export_symbols", "debug", "extra_preargs", "extra_postargs", "build_temp", "target_lang", "filename", "basename", "ext"};
      link_shared_object$16 = Py.newCode(13, var2, var1, "link_shared_object", 266, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new setuptools_build_ext$py("distutils/tests/setuptools_build_ext$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(setuptools_build_ext$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.if_dl$1(var2, var3);
         case 2:
            return this.build_ext$2(var2, var3);
         case 3:
            return this.run$3(var2, var3);
         case 4:
            return this.copy_extensions_to_source$4(var2, var3);
         case 5:
            return this.swig_sources$5(var2, var3);
         case 6:
            return this.get_ext_filename$6(var2, var3);
         case 7:
            return this.initialize_options$7(var2, var3);
         case 8:
            return this.finalize_options$8(var2, var3);
         case 9:
            return this.setup_shlib_compiler$9(var2, var3);
         case 10:
            return this.get_export_symbols$10(var2, var3);
         case 11:
            return this.build_extension$11(var2, var3);
         case 12:
            return this.links_to_dynamic$12(var2, var3);
         case 13:
            return this.get_outputs$13(var2, var3);
         case 14:
            return this.write_stub$14(var2, var3);
         case 15:
            return this.link_shared_object$15(var2, var3);
         case 16:
            return this.link_shared_object$16(var2, var3);
         default:
            return null;
      }
   }
}
