package distutils;

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
@Filename("distutils/unixccompiler.py")
public class unixccompiler$py extends PyFunctionTable implements PyRunnable {
   static unixccompiler$py self;
   static final PyCode f$0;
   static final PyCode UnixCCompiler$1;
   static final PyCode preprocess$2;
   static final PyCode _compile$3;
   static final PyCode create_static_lib$4;
   static final PyCode link$5;
   static final PyCode library_dir_option$6;
   static final PyCode _is_gcc$7;
   static final PyCode runtime_library_dir_option$8;
   static final PyCode library_option$9;
   static final PyCode find_library_file$10;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.unixccompiler\n\nContains the UnixCCompiler class, a subclass of CCompiler that handles\nthe \"typical\" Unix-style command-line C compiler:\n  * macros defined with -Dname[=value]\n  * macros undefined with -Uname\n  * include search directories specified with -Idir\n  * libraries specified with -lllib\n  * library search directories specified with -Ldir\n  * compile handled by 'cc' (or similar) executable with -c option:\n    compiles .c to .o\n  * link static library handled by 'ar' command (possibly with 'ranlib')\n  * link shared library handled by 'cc -shared'\n"));
      var1.setline(14);
      PyString.fromInterned("distutils.unixccompiler\n\nContains the UnixCCompiler class, a subclass of CCompiler that handles\nthe \"typical\" Unix-style command-line C compiler:\n  * macros defined with -Dname[=value]\n  * macros undefined with -Uname\n  * include search directories specified with -Idir\n  * libraries specified with -lllib\n  * library search directories specified with -Ldir\n  * compile handled by 'cc' (or similar) executable with -c option:\n    compiles .c to .o\n  * link static library handled by 'ar' command (possibly with 'ranlib')\n  * link shared library handled by 'cc -shared'\n");
      var1.setline(16);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(18);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(19);
      String[] var6 = new String[]{"StringType", "NoneType"};
      PyObject[] var7 = imp.importFrom("types", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("StringType", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("NoneType", var4);
      var4 = null;
      var1.setline(21);
      var6 = new String[]{"sysconfig"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("sysconfig", var4);
      var4 = null;
      var1.setline(22);
      var6 = new String[]{"newer"};
      var7 = imp.importFrom("distutils.dep_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("newer", var4);
      var4 = null;
      var1.setline(23);
      var6 = new String[]{"CCompiler", "gen_preprocess_options", "gen_lib_options"};
      var7 = imp.importFrom("distutils.ccompiler", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("CCompiler", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("gen_preprocess_options", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("gen_lib_options", var4);
      var4 = null;
      var1.setline(25);
      var6 = new String[]{"DistutilsExecError", "CompileError", "LibError", "LinkError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsExecError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("CompileError", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("LibError", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("LinkError", var4);
      var4 = null;
      var1.setline(27);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(29);
      var5 = var1.getname("sys").__getattr__("platform");
      PyObject var10000 = var5._eq(PyString.fromInterned("darwin"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(30);
         var5 = imp.importOne("_osx_support", var1, -1);
         var1.setlocal("_osx_support", var5);
         var3 = null;
      }

      var1.setline(48);
      var7 = new PyObject[]{var1.getname("CCompiler")};
      var4 = Py.makeClass("UnixCCompiler", var7, UnixCCompiler$1);
      var1.setlocal("UnixCCompiler", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject UnixCCompiler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(50);
      PyString var3 = PyString.fromInterned("unix");
      var1.setlocal("compiler_type", var3);
      var3 = null;
      var1.setline(58);
      PyDictionary var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("preprocessor"), var1.getname("None"), PyString.fromInterned("compiler"), new PyList(new PyObject[]{PyString.fromInterned("cc")}), PyString.fromInterned("compiler_so"), new PyList(new PyObject[]{PyString.fromInterned("cc")}), PyString.fromInterned("compiler_cxx"), new PyList(new PyObject[]{PyString.fromInterned("cc")}), PyString.fromInterned("linker_so"), new PyList(new PyObject[]{PyString.fromInterned("cc"), PyString.fromInterned("-shared")}), PyString.fromInterned("linker_exe"), new PyList(new PyObject[]{PyString.fromInterned("cc")}), PyString.fromInterned("archiver"), new PyList(new PyObject[]{PyString.fromInterned("ar"), PyString.fromInterned("-cr")}), PyString.fromInterned("ranlib"), var1.getname("None")});
      var1.setlocal("executables", var4);
      var3 = null;
      var1.setline(68);
      PyObject var5 = var1.getname("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
      PyObject var10000 = var5._eq(PyString.fromInterned("darwin"));
      var3 = null;
      PyList var6;
      if (var10000.__nonzero__()) {
         var1.setline(69);
         var6 = new PyList(new PyObject[]{PyString.fromInterned("ranlib")});
         var1.getname("executables").__setitem__((PyObject)PyString.fromInterned("ranlib"), var6);
         var3 = null;
      }

      var1.setline(77);
      var6 = new PyList(new PyObject[]{PyString.fromInterned(".c"), PyString.fromInterned(".C"), PyString.fromInterned(".cc"), PyString.fromInterned(".cxx"), PyString.fromInterned(".cpp"), PyString.fromInterned(".m")});
      var1.setlocal("src_extensions", var6);
      var3 = null;
      var1.setline(78);
      var3 = PyString.fromInterned(".o");
      var1.setlocal("obj_extension", var3);
      var3 = null;
      var1.setline(79);
      var3 = PyString.fromInterned(".a");
      var1.setlocal("static_lib_extension", var3);
      var3 = null;
      var1.setline(80);
      var3 = PyString.fromInterned(".so");
      var1.setlocal("shared_lib_extension", var3);
      var3 = null;
      var1.setline(81);
      var3 = PyString.fromInterned(".dylib");
      var1.setlocal("dylib_lib_extension", var3);
      var3 = null;
      var1.setline(82);
      var3 = PyString.fromInterned("lib%s%s");
      var1.setlocal("static_lib_format", var3);
      var1.setlocal("shared_lib_format", var3);
      var1.setlocal("dylib_lib_format", var3);
      var1.setline(83);
      var5 = var1.getname("sys").__getattr__("platform");
      var10000 = var5._eq(PyString.fromInterned("cygwin"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(84);
         var3 = PyString.fromInterned(".exe");
         var1.setlocal("exe_extension", var3);
         var3 = null;
      }

      var1.setline(86);
      PyObject[] var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var8 = new PyFunction(var1.f_globals, var7, preprocess$2, (PyObject)null);
      var1.setlocal("preprocess", var8);
      var3 = null;
      var1.setline(113);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _compile$3, (PyObject)null);
      var1.setlocal("_compile", var8);
      var3 = null;
      var1.setline(124);
      var7 = new PyObject[]{var1.getname("None"), Py.newInteger(0), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, create_static_lib$4, (PyObject)null);
      var1.setlocal("create_static_lib", var8);
      var3 = null;
      var1.setline(150);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, link$5, (PyObject)null);
      var1.setlocal("link", var8);
      var3 = null;
      var1.setline(208);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, library_dir_option$6, (PyObject)null);
      var1.setlocal("library_dir_option", var8);
      var3 = null;
      var1.setline(211);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _is_gcc$7, (PyObject)null);
      var1.setlocal("_is_gcc", var8);
      var3 = null;
      var1.setline(214);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, runtime_library_dir_option$8, (PyObject)null);
      var1.setlocal("runtime_library_dir_option", var8);
      var3 = null;
      var1.setline(242);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, library_option$9, (PyObject)null);
      var1.setlocal("library_option", var8);
      var3 = null;
      var1.setline(245);
      var7 = new PyObject[]{Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, find_library_file$10, (PyObject)null);
      var1.setlocal("find_library_file", var8);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject preprocess$2(PyFrame var1, ThreadState var2) {
      var1.setline(89);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_compile_args").__call__(var2, var1.getglobal("None"), var1.getlocal(3), var1.getlocal(4));
      PyObject[] var4 = Py.unpackSequence(var3, 3);
      PyObject var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(91);
      var3 = var1.getglobal("gen_preprocess_options").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(92);
      var3 = var1.getlocal(0).__getattr__("preprocessor")._add(var1.getlocal(8));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(93);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(94);
         var1.getlocal(9).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("-o"), var1.getlocal(2)})));
      }

      var1.setline(95);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(96);
         var3 = var1.getlocal(5);
         var1.getlocal(9).__setslice__((PyObject)null, Py.newInteger(0), (PyObject)null, var3);
         var3 = null;
      }

      var1.setline(97);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(98);
         var1.getlocal(9).__getattr__("extend").__call__(var2, var1.getlocal(6));
      }

      var1.setline(99);
      var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(105);
      PyObject var10000 = var1.getlocal(0).__getattr__("force");
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("newer").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(106);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(107);
            var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(2)));
         }

         try {
            var1.setline(109);
            var1.getlocal(0).__getattr__("spawn").__call__(var2, var1.getlocal(9));
         } catch (Throwable var6) {
            PyException var8 = Py.setException(var6, var1);
            if (var8.match(var1.getglobal("DistutilsExecError"))) {
               PyObject var7 = var8.value;
               var1.setlocal(10, var7);
               var4 = null;
               var1.setline(111);
               throw Py.makeException(var1.getglobal("CompileError"), var1.getlocal(10));
            }

            throw var8;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _compile$3(PyFrame var1, ThreadState var2) {
      var1.setline(114);
      PyObject var3 = var1.getlocal(0).__getattr__("compiler_so");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(115);
      var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("darwin"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(116);
         var3 = var1.getglobal("_osx_support").__getattr__("compiler_fixup").__call__(var2, var1.getlocal(7), var1.getlocal(4)._add(var1.getlocal(5)));
         var1.setlocal(7, var3);
         var3 = null;
      }

      try {
         var1.setline(119);
         var1.getlocal(0).__getattr__("spawn").__call__(var2, var1.getlocal(7)._add(var1.getlocal(4))._add(new PyList(new PyObject[]{var1.getlocal(2), PyString.fromInterned("-o"), var1.getlocal(1)}))._add(var1.getlocal(5)));
      } catch (Throwable var5) {
         PyException var6 = Py.setException(var5, var1);
         if (var6.match(var1.getglobal("DistutilsExecError"))) {
            PyObject var4 = var6.value;
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(122);
            throw Py.makeException(var1.getglobal("CompileError"), var1.getlocal(8));
         }

         throw var6;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject create_static_lib$4(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_object_args").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(128);
      PyObject var10000 = var1.getlocal(0).__getattr__("library_filename");
      PyObject[] var7 = new PyObject[]{var1.getlocal(2), var1.getlocal(3)};
      String[] var8 = new String[]{"output_dir"};
      var10000 = var10000.__call__(var2, var7, var8);
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(131);
      if (var1.getlocal(0).__getattr__("_need_link").__call__(var2, var1.getlocal(1), var1.getlocal(6)).__nonzero__()) {
         var1.setline(132);
         var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(6)));
         var1.setline(133);
         var1.getlocal(0).__getattr__("spawn").__call__(var2, var1.getlocal(0).__getattr__("archiver")._add(new PyList(new PyObject[]{var1.getlocal(6)}))._add(var1.getlocal(1))._add(var1.getlocal(0).__getattr__("objects")));
         var1.setline(142);
         if (var1.getlocal(0).__getattr__("ranlib").__nonzero__()) {
            try {
               var1.setline(144);
               var1.getlocal(0).__getattr__("spawn").__call__(var2, var1.getlocal(0).__getattr__("ranlib")._add(new PyList(new PyObject[]{var1.getlocal(6)})));
            } catch (Throwable var6) {
               PyException var10 = Py.setException(var6, var1);
               if (var10.match(var1.getglobal("DistutilsExecError"))) {
                  PyObject var9 = var10.value;
                  var1.setlocal(7, var9);
                  var4 = null;
                  var1.setline(146);
                  throw Py.makeException(var1.getglobal("LibError"), var1.getlocal(7));
               }

               throw var10;
            }
         }
      } else {
         var1.setline(148);
         var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("skipping %s (up-to-date)"), (PyObject)var1.getlocal(6));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject link$5(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_object_args").__call__(var2, var1.getlocal(2), var1.getlocal(4));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(156);
      var3 = var1.getlocal(0).__getattr__("_fix_lib_args").__call__(var2, var1.getlocal(5), var1.getlocal(6), var1.getlocal(7));
      var4 = Py.unpackSequence(var3, 3);
      var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(159);
      var3 = var1.getglobal("gen_lib_options").__call__(var2, var1.getlocal(0), var1.getlocal(6), var1.getlocal(7), var1.getlocal(5));
      var1.setlocal(14, var3);
      var3 = null;
      var1.setline(161);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(4));
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("StringType"), var1.getglobal("NoneType")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(162);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'output_dir' must be a string or None"));
      } else {
         var1.setline(163);
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(164);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(3));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(166);
         if (var1.getlocal(0).__getattr__("_need_link").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
            var1.setline(167);
            var3 = var1.getlocal(2)._add(var1.getlocal(0).__getattr__("objects"))._add(var1.getlocal(14))._add(new PyList(new PyObject[]{PyString.fromInterned("-o"), var1.getlocal(3)}));
            var1.setlocal(15, var3);
            var3 = null;
            var1.setline(169);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(170);
               PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("-g")});
               var1.getlocal(15).__setslice__((PyObject)null, Py.newInteger(0), (PyObject)null, var8);
               var3 = null;
            }

            var1.setline(171);
            if (var1.getlocal(10).__nonzero__()) {
               var1.setline(172);
               var3 = var1.getlocal(10);
               var1.getlocal(15).__setslice__((PyObject)null, Py.newInteger(0), (PyObject)null, var3);
               var3 = null;
            }

            var1.setline(173);
            if (var1.getlocal(11).__nonzero__()) {
               var1.setline(174);
               var1.getlocal(15).__getattr__("extend").__call__(var2, var1.getlocal(11));
            }

            var1.setline(175);
            var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(3)));

            try {
               var1.setline(177);
               var3 = var1.getlocal(1);
               var10000 = var3._eq(var1.getglobal("CCompiler").__getattr__("EXECUTABLE"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(178);
                  var3 = var1.getlocal(0).__getattr__("linker_exe").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
                  var1.setlocal(16, var3);
                  var3 = null;
               } else {
                  var1.setline(180);
                  var3 = var1.getlocal(0).__getattr__("linker_so").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
                  var1.setlocal(16, var3);
                  var3 = null;
               }

               var1.setline(181);
               var3 = var1.getlocal(13);
               var10000 = var3._eq(PyString.fromInterned("c++"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("compiler_cxx");
               }

               if (var10000.__nonzero__()) {
                  var1.setline(187);
                  PyInteger var10 = Py.newInteger(0);
                  var1.setlocal(17, var10);
                  var3 = null;
                  var1.setline(188);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(16).__getitem__(Py.newInteger(0)));
                  var10000 = var3._eq(PyString.fromInterned("env"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(189);
                     var10 = Py.newInteger(1);
                     var1.setlocal(17, var10);
                     var3 = null;

                     while(true) {
                        var1.setline(190);
                        PyString var11 = PyString.fromInterned("=");
                        var10000 = var11._in(var1.getlocal(16).__getitem__(var1.getlocal(17)));
                        var3 = null;
                        if (!var10000.__nonzero__()) {
                           break;
                        }

                        var1.setline(191);
                        var3 = var1.getlocal(17)._add(Py.newInteger(1));
                        var1.setlocal(17, var3);
                        var3 = null;
                     }
                  }

                  var1.setline(193);
                  var3 = var1.getlocal(0).__getattr__("compiler_cxx").__getitem__(var1.getlocal(17));
                  var1.getlocal(16).__setitem__(var1.getlocal(17), var3);
                  var3 = null;
               }

               var1.setline(195);
               var3 = var1.getglobal("sys").__getattr__("platform");
               var10000 = var3._eq(PyString.fromInterned("darwin"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(196);
                  var3 = var1.getglobal("_osx_support").__getattr__("compiler_fixup").__call__(var2, var1.getlocal(16), var1.getlocal(15));
                  var1.setlocal(16, var3);
                  var3 = null;
               }

               var1.setline(198);
               var1.getlocal(0).__getattr__("spawn").__call__(var2, var1.getlocal(16)._add(var1.getlocal(15)));
            } catch (Throwable var6) {
               PyException var9 = Py.setException(var6, var1);
               if (var9.match(var1.getglobal("DistutilsExecError"))) {
                  PyObject var7 = var9.value;
                  var1.setlocal(18, var7);
                  var4 = null;
                  var1.setline(200);
                  throw Py.makeException(var1.getglobal("LinkError"), var1.getlocal(18));
               }

               throw var9;
            }
         } else {
            var1.setline(202);
            var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("skipping %s (up-to-date)"), (PyObject)var1.getlocal(3));
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject library_dir_option$6(PyFrame var1, ThreadState var2) {
      var1.setline(209);
      PyObject var3 = PyString.fromInterned("-L")._add(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _is_gcc$7(PyFrame var1, ThreadState var2) {
      var1.setline(212);
      PyString var3 = PyString.fromInterned("gcc");
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = PyString.fromInterned("g++");
         var10000 = var3._in(var1.getlocal(1));
         var3 = null;
      }

      PyObject var4 = var10000;
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject runtime_library_dir_option$8(PyFrame var1, ThreadState var2) {
      var1.setline(227);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CC")));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(228);
      var3 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("darwin"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(230);
         var3 = PyString.fromInterned("-L")._add(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(231);
         PyObject var4 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
         var10000 = var4._eq(PyString.fromInterned("hp-ux"));
         var4 = null;
         PyList var5;
         if (var10000.__nonzero__()) {
            var1.setline(232);
            if (var1.getlocal(0).__getattr__("_is_gcc").__call__(var2, var1.getlocal(2)).__nonzero__()) {
               var1.setline(233);
               var5 = new PyList(new PyObject[]{PyString.fromInterned("-Wl,+s"), PyString.fromInterned("-L")._add(var1.getlocal(1))});
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(234);
               var5 = new PyList(new PyObject[]{PyString.fromInterned("+s"), PyString.fromInterned("-L")._add(var1.getlocal(1))});
               var1.f_lasti = -1;
               return var5;
            }
         } else {
            var1.setline(235);
            var4 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(7), (PyObject)null);
            var10000 = var4._eq(PyString.fromInterned("irix646"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
               var10000 = var4._eq(PyString.fromInterned("osf1V5"));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(236);
               var5 = new PyList(new PyObject[]{PyString.fromInterned("-rpath"), var1.getlocal(1)});
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(237);
               if (var1.getlocal(0).__getattr__("_is_gcc").__call__(var2, var1.getlocal(2)).__nonzero__()) {
                  var1.setline(238);
                  var3 = PyString.fromInterned("-Wl,-R")._add(var1.getlocal(1));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(240);
                  var3 = PyString.fromInterned("-R")._add(var1.getlocal(1));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      }
   }

   public PyObject library_option$9(PyFrame var1, ThreadState var2) {
      var1.setline(243);
      PyObject var3 = PyString.fromInterned("-l")._add(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject find_library_file$10(PyFrame var1, ThreadState var2) {
      var1.setline(246);
      PyObject var10000 = var1.getlocal(0).__getattr__("library_filename");
      PyObject[] var3 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("shared")};
      String[] var4 = new String[]{"lib_type"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(247);
      var10000 = var1.getlocal(0).__getattr__("library_filename");
      var3 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("dylib")};
      var4 = new String[]{"lib_type"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var6 = var10000;
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(248);
      var10000 = var1.getlocal(0).__getattr__("library_filename");
      var3 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("static")};
      var4 = new String[]{"lib_type"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var6 = var10000;
      var1.setlocal(6, var6);
      var3 = null;
      var1.setline(250);
      var6 = var1.getglobal("sys").__getattr__("platform");
      var10000 = var6._eq(PyString.fromInterned("darwin"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(254);
         var6 = var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CFLAGS"));
         var1.setlocal(7, var6);
         var3 = null;
         var1.setline(255);
         var6 = var1.getglobal("re").__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-isysroot\\s+(\\S+)"), (PyObject)var1.getlocal(7));
         var1.setlocal(8, var6);
         var3 = null;
         var1.setline(256);
         var6 = var1.getlocal(8);
         var10000 = var6._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(257);
            PyString var8 = PyString.fromInterned("/");
            var1.setlocal(9, var8);
            var3 = null;
         } else {
            var1.setline(259);
            var6 = var1.getlocal(8).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            var1.setlocal(9, var6);
            var3 = null;
         }
      }

      var1.setline(263);
      var6 = var1.getlocal(1).__iter__();

      PyObject var5;
      do {
         var1.setline(263);
         PyObject var7 = var6.__iternext__();
         if (var7 == null) {
            var1.setline(288);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(10, var7);
         var1.setline(264);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(10), var1.getlocal(4));
         var1.setlocal(11, var5);
         var5 = null;
         var1.setline(265);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(10), var1.getlocal(5));
         var1.setlocal(12, var5);
         var5 = null;
         var1.setline(266);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(10), var1.getlocal(6));
         var1.setlocal(13, var5);
         var5 = null;
         var1.setline(268);
         var5 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var5._eq(PyString.fromInterned("darwin"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(10).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/System/"));
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(10).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/usr/"));
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(10).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/usr/local/")).__not__();
               }
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(272);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(9), var1.getlocal(10).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), var1.getlocal(4));
            var1.setlocal(11, var5);
            var5 = null;
            var1.setline(273);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(9), var1.getlocal(10).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), var1.getlocal(5));
            var1.setlocal(12, var5);
            var5 = null;
            var1.setline(274);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(9), var1.getlocal(10).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null), var1.getlocal(6));
            var1.setlocal(13, var5);
            var5 = null;
         }

         var1.setline(280);
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(12)).__nonzero__()) {
            var1.setline(281);
            var5 = var1.getlocal(12);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(282);
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(11)).__nonzero__()) {
            var1.setline(283);
            var5 = var1.getlocal(11);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(284);
      } while(!var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(13)).__nonzero__());

      var1.setline(285);
      var5 = var1.getlocal(13);
      var1.f_lasti = -1;
      return var5;
   }

   public unixccompiler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      UnixCCompiler$1 = Py.newCode(0, var2, var1, "UnixCCompiler", 48, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "source", "output_file", "macros", "include_dirs", "extra_preargs", "extra_postargs", "ignore", "pp_opts", "pp_args", "msg"};
      preprocess$2 = Py.newCode(7, var2, var1, "preprocess", 86, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "src", "ext", "cc_args", "extra_postargs", "pp_opts", "compiler_so", "msg"};
      _compile$3 = Py.newCode(7, var2, var1, "_compile", 113, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects", "output_libname", "output_dir", "debug", "target_lang", "output_filename", "msg"};
      create_static_lib$4 = Py.newCode(6, var2, var1, "create_static_lib", 124, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target_desc", "objects", "output_filename", "output_dir", "libraries", "library_dirs", "runtime_library_dirs", "export_symbols", "debug", "extra_preargs", "extra_postargs", "build_temp", "target_lang", "lib_opts", "ld_args", "linker", "i", "msg"};
      link$5 = Py.newCode(14, var2, var1, "link", 150, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir"};
      library_dir_option$6 = Py.newCode(2, var2, var1, "library_dir_option", 208, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "compiler_name"};
      _is_gcc$7 = Py.newCode(2, var2, var1, "_is_gcc", 211, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir", "compiler"};
      runtime_library_dir_option$8 = Py.newCode(2, var2, var1, "runtime_library_dir_option", 214, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lib"};
      library_option$9 = Py.newCode(2, var2, var1, "library_option", 242, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirs", "lib", "debug", "shared_f", "dylib_f", "static_f", "cflags", "m", "sysroot", "dir", "shared", "dylib", "static"};
      find_library_file$10 = Py.newCode(4, var2, var1, "find_library_file", 245, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new unixccompiler$py("distutils/unixccompiler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(unixccompiler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.UnixCCompiler$1(var2, var3);
         case 2:
            return this.preprocess$2(var2, var3);
         case 3:
            return this._compile$3(var2, var3);
         case 4:
            return this.create_static_lib$4(var2, var3);
         case 5:
            return this.link$5(var2, var3);
         case 6:
            return this.library_dir_option$6(var2, var3);
         case 7:
            return this._is_gcc$7(var2, var3);
         case 8:
            return this.runtime_library_dir_option$8(var2, var3);
         case 9:
            return this.library_option$9(var2, var3);
         case 10:
            return this.find_library_file$10(var2, var3);
         default:
            return null;
      }
   }
}
