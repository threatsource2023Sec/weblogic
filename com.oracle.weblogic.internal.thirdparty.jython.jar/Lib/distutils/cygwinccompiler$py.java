package distutils;

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
@Filename("distutils/cygwinccompiler.py")
public class cygwinccompiler$py extends PyFunctionTable implements PyRunnable {
   static cygwinccompiler$py self;
   static final PyCode f$0;
   static final PyCode get_msvcr$1;
   static final PyCode CygwinCCompiler$2;
   static final PyCode __init__$3;
   static final PyCode _compile$4;
   static final PyCode link$5;
   static final PyCode object_filenames$6;
   static final PyCode Mingw32CCompiler$7;
   static final PyCode __init__$8;
   static final PyCode check_config_h$9;
   static final PyCode get_versions$10;
   static final PyCode is_cygwingcc$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.cygwinccompiler\n\nProvides the CygwinCCompiler class, a subclass of UnixCCompiler that\nhandles the Cygwin port of the GNU C compiler to Windows.  It also contains\nthe Mingw32CCompiler class which handles the mingw32 port of GCC (same as\ncygwin in no-cygwin mode).\n"));
      var1.setline(7);
      PyString.fromInterned("distutils.cygwinccompiler\n\nProvides the CygwinCCompiler class, a subclass of UnixCCompiler that\nhandles the Cygwin port of the GNU C compiler to Windows.  It also contains\nthe Mingw32CCompiler class which handles the mingw32 port of GCC (same as\ncygwin in no-cygwin mode).\n");
      var1.setline(50);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(52);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var5 = imp.importOne("copy", var1, -1);
      var1.setlocal("copy", var5);
      var3 = null;
      var1.setline(53);
      String[] var6 = new String[]{"gen_preprocess_options", "gen_lib_options"};
      PyObject[] var7 = imp.importFrom("distutils.ccompiler", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("gen_preprocess_options", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("gen_lib_options", var4);
      var4 = null;
      var1.setline(54);
      var6 = new String[]{"UnixCCompiler"};
      var7 = imp.importFrom("distutils.unixccompiler", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("UnixCCompiler", var4);
      var4 = null;
      var1.setline(55);
      var6 = new String[]{"write_file"};
      var7 = imp.importFrom("distutils.file_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("write_file", var4);
      var4 = null;
      var1.setline(56);
      var6 = new String[]{"DistutilsExecError", "CompileError", "UnknownFileError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsExecError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("CompileError", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("UnknownFileError", var4);
      var4 = null;
      var1.setline(57);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(59);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, get_msvcr$1, PyString.fromInterned("Include the appropriate MSVC runtime library if Python was built\n    with MSVC 7.0 or later.\n    "));
      var1.setlocal("get_msvcr", var8);
      var3 = null;
      var1.setline(82);
      var7 = new PyObject[]{var1.getname("UnixCCompiler")};
      var4 = Py.makeClass("CygwinCCompiler", var7, CygwinCCompiler$2);
      var1.setlocal("CygwinCCompiler", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(297);
      var7 = new PyObject[]{var1.getname("CygwinCCompiler")};
      var4 = Py.makeClass("Mingw32CCompiler", var7, Mingw32CCompiler$7);
      var1.setlocal("Mingw32CCompiler", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(353);
      var3 = PyString.fromInterned("ok");
      var1.setlocal("CONFIG_H_OK", var3);
      var3 = null;
      var1.setline(354);
      var3 = PyString.fromInterned("not ok");
      var1.setlocal("CONFIG_H_NOTOK", var3);
      var3 = null;
      var1.setline(355);
      var3 = PyString.fromInterned("uncertain");
      var1.setlocal("CONFIG_H_UNCERTAIN", var3);
      var3 = null;
      var1.setline(357);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, check_config_h$9, PyString.fromInterned("Check if the current Python installation (specifically, pyconfig.h)\n    appears amenable to building extensions with GCC.  Returns a tuple\n    (status, details), where 'status' is one of the following constants:\n      CONFIG_H_OK\n        all is well, go ahead and compile\n      CONFIG_H_NOTOK\n        doesn't look good\n      CONFIG_H_UNCERTAIN\n        not sure -- unable to read pyconfig.h\n    'details' is a human-readable string explaining the situation.\n\n    Note there are two ways to conclude \"OK\": either 'sys.version' contains\n    the string \"GCC\" (implying that this Python was built with GCC), or the\n    installed \"pyconfig.h\" contains the string \"__GNUC__\".\n    "));
      var1.setlocal("check_config_h", var8);
      var3 = null;
      var1.setline(410);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_versions$10, PyString.fromInterned(" Try to find out the versions of gcc, ld and dllwrap.\n        If not possible it returns None for it.\n    "));
      var1.setlocal("get_versions", var8);
      var3 = null;
      var1.setline(456);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, is_cygwingcc$11, PyString.fromInterned("Try to determine if the gcc that would be used is from cygwin."));
      var1.setlocal("is_cygwingcc", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_msvcr$1(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyString.fromInterned("Include the appropriate MSVC runtime library if Python was built\n    with MSVC 7.0 or later.\n    ");
      var1.setline(63);
      PyObject var3 = var1.getglobal("sys").__getattr__("version").__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MSC v."));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(64);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._ne(Py.newInteger(-1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(65);
         var3 = var1.getglobal("sys").__getattr__("version").__getslice__(var1.getlocal(0)._add(Py.newInteger(6)), var1.getlocal(0)._add(Py.newInteger(10)), (PyObject)null);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(66);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(PyString.fromInterned("1300"));
         var3 = null;
         PyList var5;
         if (var10000.__nonzero__()) {
            var1.setline(68);
            var5 = new PyList(new PyObject[]{PyString.fromInterned("msvcr70")});
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(69);
            PyObject var4 = var1.getlocal(1);
            var10000 = var4._eq(PyString.fromInterned("1310"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(71);
               var5 = new PyList(new PyObject[]{PyString.fromInterned("msvcr71")});
               var1.f_lasti = -1;
               return var5;
            } else {
               var1.setline(72);
               var4 = var1.getlocal(1);
               var10000 = var4._eq(PyString.fromInterned("1400"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(74);
                  var5 = new PyList(new PyObject[]{PyString.fromInterned("msvcr80")});
                  var1.f_lasti = -1;
                  return var5;
               } else {
                  var1.setline(75);
                  var4 = var1.getlocal(1);
                  var10000 = var4._eq(PyString.fromInterned("1500"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(77);
                     var5 = new PyList(new PyObject[]{PyString.fromInterned("msvcr90")});
                     var1.f_lasti = -1;
                     return var5;
                  } else {
                     var1.setline(79);
                     throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Unknown MS Compiler version %s ")._mod(var1.getlocal(1))));
                  }
               }
            }
         }
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject CygwinCCompiler$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(84);
      PyString var3 = PyString.fromInterned("cygwin");
      var1.setlocal("compiler_type", var3);
      var3 = null;
      var1.setline(85);
      var3 = PyString.fromInterned(".o");
      var1.setlocal("obj_extension", var3);
      var3 = null;
      var1.setline(86);
      var3 = PyString.fromInterned(".a");
      var1.setlocal("static_lib_extension", var3);
      var3 = null;
      var1.setline(87);
      var3 = PyString.fromInterned(".dll");
      var1.setlocal("shared_lib_extension", var3);
      var3 = null;
      var1.setline(88);
      var3 = PyString.fromInterned("lib%s%s");
      var1.setlocal("static_lib_format", var3);
      var3 = null;
      var1.setline(89);
      var3 = PyString.fromInterned("%s%s");
      var1.setlocal("shared_lib_format", var3);
      var3 = null;
      var1.setline(90);
      var3 = PyString.fromInterned(".exe");
      var1.setlocal("exe_extension", var3);
      var3 = null;
      var1.setline(92);
      PyObject[] var4 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$3, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(154);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _compile$4, (PyObject)null);
      var1.setlocal("_compile", var5);
      var3 = null;
      var1.setline(168);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, link$5, (PyObject)null);
      var1.setlocal("link", var5);
      var3 = null;
      var1.setline(267);
      var4 = new PyObject[]{Py.newInteger(0), PyString.fromInterned("")};
      var5 = new PyFunction(var1.f_globals, var4, object_filenames$6, (PyObject)null);
      var1.setlocal("object_filenames", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$3(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      var1.getglobal("UnixCCompiler").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(96);
      PyObject var3 = var1.getglobal("check_config_h").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(97);
      var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("Python's GCC status: %s (details: %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})));
      var1.setline(99);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("CONFIG_H_OK"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(100);
         var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("Python's pyconfig.h doesn't seem to support your compiler. Reason: %s. Compiling may fail because of undefined preprocessor macros.")._mod(var1.getlocal(5)));
      }

      var1.setline(106);
      var3 = var1.getglobal("get_versions").__call__(var2);
      var4 = Py.unpackSequence(var3, 3);
      var5 = var4[0];
      var1.getlocal(0).__setattr__("gcc_version", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("ld_version", var5);
      var5 = null;
      var5 = var4[2];
      var1.getlocal(0).__setattr__("dllwrap_version", var5);
      var5 = null;
      var3 = null;
      var1.setline(108);
      var1.getlocal(0).__getattr__("debug_print").__call__(var2, var1.getlocal(0).__getattr__("compiler_type")._add(PyString.fromInterned(": gcc %s, ld %s, dllwrap %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("gcc_version"), var1.getlocal(0).__getattr__("ld_version"), var1.getlocal(0).__getattr__("dllwrap_version")}))));
      var1.setline(118);
      var3 = var1.getlocal(0).__getattr__("ld_version");
      var10000 = var3._ge(PyString.fromInterned("2.10.90"));
      var3 = null;
      PyString var7;
      if (var10000.__nonzero__()) {
         var1.setline(119);
         var7 = PyString.fromInterned("gcc");
         var1.getlocal(0).__setattr__((String)"linker_dll", var7);
         var3 = null;
      } else {
         var1.setline(121);
         var7 = PyString.fromInterned("dllwrap");
         var1.getlocal(0).__setattr__((String)"linker_dll", var7);
         var3 = null;
      }

      var1.setline(125);
      var3 = var1.getlocal(0).__getattr__("ld_version");
      var10000 = var3._ge(PyString.fromInterned("2.13"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(126);
         var7 = PyString.fromInterned("-shared");
         var1.setlocal(6, var7);
         var3 = null;
      } else {
         var1.setline(128);
         var7 = PyString.fromInterned("-mdll -static");
         var1.setlocal(6, var7);
         var3 = null;
      }

      var1.setline(132);
      var10000 = var1.getlocal(0).__getattr__("set_executables");
      PyObject[] var8 = new PyObject[]{PyString.fromInterned("gcc -mcygwin -O -Wall"), PyString.fromInterned("gcc -mcygwin -mdll -O -Wall"), PyString.fromInterned("g++ -mcygwin -O -Wall"), PyString.fromInterned("gcc -mcygwin"), PyString.fromInterned("%s -mcygwin %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("linker_dll"), var1.getlocal(6)}))};
      String[] var6 = new String[]{"compiler", "compiler_so", "compiler_cxx", "linker_exe", "linker_so"};
      var10000.__call__(var2, var8, var6);
      var3 = null;
      var1.setline(140);
      var3 = var1.getlocal(0).__getattr__("gcc_version");
      var10000 = var3._eq(PyString.fromInterned("2.91.57"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(143);
         PyList var9 = new PyList(new PyObject[]{PyString.fromInterned("msvcrt")});
         var1.getlocal(0).__setattr__((String)"dll_libraries", var9);
         var3 = null;
         var1.setline(144);
         var1.getlocal(0).__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Consider upgrading to a newer version of gcc"));
      } else {
         var1.setline(149);
         var3 = var1.getglobal("get_msvcr").__call__(var2);
         var1.getlocal(0).__setattr__("dll_libraries", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _compile$4(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(PyString.fromInterned(".rc"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._eq(PyString.fromInterned(".res"));
         var3 = null;
      }

      PyObject var4;
      PyException var7;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(158);
            var1.getlocal(0).__getattr__("spawn").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("windres"), PyString.fromInterned("-i"), var1.getlocal(2), PyString.fromInterned("-o"), var1.getlocal(1)})));
         } catch (Throwable var5) {
            var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("DistutilsExecError"))) {
               var4 = var7.value;
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(160);
               throw Py.makeException(var1.getglobal("CompileError"), var1.getlocal(7));
            }

            throw var7;
         }
      } else {
         try {
            var1.setline(163);
            var1.getlocal(0).__getattr__("spawn").__call__(var2, var1.getlocal(0).__getattr__("compiler_so")._add(var1.getlocal(4))._add(new PyList(new PyObject[]{var1.getlocal(2), PyString.fromInterned("-o"), var1.getlocal(1)}))._add(var1.getlocal(5)));
         } catch (Throwable var6) {
            var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("DistutilsExecError"))) {
               var4 = var7.value;
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(166);
               throw Py.makeException(var1.getglobal("CompileError"), var1.getlocal(7));
            }

            throw var7;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject link$5(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyObject var10000 = var1.getglobal("copy").__getattr__("copy");
      Object var10002 = var1.getlocal(10);
      if (!((PyObject)var10002).__nonzero__()) {
         var10002 = new PyList(Py.EmptyObjects);
      }

      PyObject var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(185);
      var10000 = var1.getglobal("copy").__getattr__("copy");
      var10002 = var1.getlocal(5);
      if (!((PyObject)var10002).__nonzero__()) {
         var10002 = new PyList(Py.EmptyObjects);
      }

      var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(186);
      var10000 = var1.getglobal("copy").__getattr__("copy");
      var10002 = var1.getlocal(2);
      if (!((PyObject)var10002).__nonzero__()) {
         var10002 = new PyList(Py.EmptyObjects);
      }

      var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(189);
      var1.getlocal(5).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("dll_libraries"));
      var1.setline(193);
      var3 = var1.getlocal(8);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._ne(var1.getlocal(0).__getattr__("EXECUTABLE"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("linker_dll");
            var10000 = var3._eq(PyString.fromInterned("gcc"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(203);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)));
         var1.setlocal(14, var3);
         var3 = null;
         var1.setline(205);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3)));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(15, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(16, var5);
         var5 = null;
         var3 = null;
         var1.setline(209);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(14), var1.getlocal(15)._add(PyString.fromInterned(".def")));
         var1.setlocal(17, var3);
         var3 = null;
         var1.setline(210);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(14), PyString.fromInterned("lib")._add(var1.getlocal(15))._add(PyString.fromInterned(".a")));
         var1.setlocal(18, var3);
         var3 = null;
         var1.setline(213);
         PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("LIBRARY %s")._mod(var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3))), PyString.fromInterned("EXPORTS")});
         var1.setlocal(19, var7);
         var3 = null;
         var1.setline(216);
         var3 = var1.getlocal(8).__iter__();

         while(true) {
            var1.setline(216);
            PyObject var6 = var3.__iternext__();
            if (var6 == null) {
               var1.setline(218);
               var1.getlocal(0).__getattr__("execute").__call__((ThreadState)var2, var1.getglobal("write_file"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(17), var1.getlocal(19)})), (PyObject)PyString.fromInterned("writing %s")._mod(var1.getlocal(17)));
               var1.setline(224);
               var3 = var1.getlocal(0).__getattr__("linker_dll");
               var10000 = var3._eq(PyString.fromInterned("dllwrap"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(225);
                  var1.getlocal(10).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("--output-lib"), var1.getlocal(18)})));
                  var1.setline(227);
                  var1.getlocal(10).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("--def"), var1.getlocal(17)})));
               } else {
                  var1.setline(233);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(17));
               }
               break;
            }

            var1.setlocal(20, var6);
            var1.setline(217);
            var1.getlocal(19).__getattr__("append").__call__(var2, var1.getlocal(20));
         }
      }

      var1.setline(244);
      if (var1.getlocal(9).__not__().__nonzero__()) {
         var1.setline(245);
         var1.getlocal(10).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-s"));
      }

      var1.setline(247);
      var10000 = var1.getglobal("UnixCCompiler").__getattr__("link");
      PyObject[] var8 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getglobal("None"), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), var1.getlocal(12), var1.getlocal(13)};
      var10000.__call__(var2, var8);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject object_filenames$6(PyFrame var1, ThreadState var2) {
      var1.setline(271);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(271);
         PyString var8 = PyString.fromInterned("");
         var1.setlocal(3, var8);
         var3 = null;
      }

      var1.setline(272);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(273);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(273);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(289);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(275);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(5)));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(7, var7);
         var7 = null;
         var5 = null;
         var1.setline(276);
         var5 = var1.getlocal(7);
         var10000 = var5._notin(var1.getlocal(0).__getattr__("src_extensions")._add(new PyList(new PyObject[]{PyString.fromInterned(".rc"), PyString.fromInterned(".res")})));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(277);
            throw Py.makeException(var1.getglobal("UnknownFileError"), PyString.fromInterned("unknown file type '%s' (from '%s')")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5)})));
         }

         var1.setline(280);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(281);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var5);
            var5 = null;
         }

         var1.setline(282);
         var5 = var1.getlocal(7);
         var10000 = var5._eq(PyString.fromInterned(".res"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var5 = var1.getlocal(7);
            var10000 = var5._eq(PyString.fromInterned(".rc"));
            var5 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(284);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(7))._add(var1.getlocal(0).__getattr__("obj_extension"))));
         } else {
            var1.setline(287);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(0).__getattr__("obj_extension"))));
         }
      }
   }

   public PyObject Mingw32CCompiler$7(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(299);
      PyString var3 = PyString.fromInterned("mingw32");
      var1.setlocal("compiler_type", var3);
      var3 = null;
      var1.setline(301);
      PyObject[] var4 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$8, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$8(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      var1.getglobal("CygwinCCompiler").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(310);
      PyObject var3 = var1.getlocal(0).__getattr__("ld_version");
      PyObject var10000 = var3._ge(PyString.fromInterned("2.13"));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(311);
         var5 = PyString.fromInterned("-shared");
         var1.setlocal(4, var5);
         var3 = null;
      } else {
         var1.setline(313);
         var5 = PyString.fromInterned("-mdll -static");
         var1.setlocal(4, var5);
         var3 = null;
      }

      var1.setline(317);
      var3 = var1.getlocal(0).__getattr__("gcc_version");
      var10000 = var3._le(PyString.fromInterned("2.91.57"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(318);
         var5 = PyString.fromInterned("--entry _DllMain@12");
         var1.setlocal(5, var5);
         var3 = null;
      } else {
         var1.setline(320);
         var5 = PyString.fromInterned("");
         var1.setlocal(5, var5);
         var3 = null;
      }

      var1.setline(322);
      var3 = var1.getlocal(0).__getattr__("gcc_version");
      var10000 = var3._lt(PyString.fromInterned("4"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var10000 = var1.getglobal("is_cygwingcc").__call__(var2);
      }

      if (var10000.__nonzero__()) {
         var1.setline(323);
         var5 = PyString.fromInterned(" -mno-cygwin");
         var1.setlocal(6, var5);
         var3 = null;
      } else {
         var1.setline(325);
         var5 = PyString.fromInterned("");
         var1.setlocal(6, var5);
         var3 = null;
      }

      var1.setline(327);
      var10000 = var1.getlocal(0).__getattr__("set_executables");
      PyObject[] var6 = new PyObject[]{PyString.fromInterned("gcc%s -O -Wall")._mod(var1.getlocal(6)), PyString.fromInterned("gcc%s -mdll -O -Wall")._mod(var1.getlocal(6)), PyString.fromInterned("g++%s -O -Wall")._mod(var1.getlocal(6)), PyString.fromInterned("gcc%s")._mod(var1.getlocal(6)), PyString.fromInterned("%s%s %s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("linker_dll"), var1.getlocal(6), var1.getlocal(4), var1.getlocal(5)}))};
      String[] var4 = new String[]{"compiler", "compiler_so", "compiler_cxx", "linker_exe", "linker_so"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(339);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"dll_libraries", var7);
      var3 = null;
      var1.setline(343);
      var3 = var1.getglobal("get_msvcr").__call__(var2);
      var1.getlocal(0).__setattr__("dll_libraries", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject check_config_h$9(PyFrame var1, ThreadState var2) {
      var1.setline(373);
      PyString.fromInterned("Check if the current Python installation (specifically, pyconfig.h)\n    appears amenable to building extensions with GCC.  Returns a tuple\n    (status, details), where 'status' is one of the following constants:\n      CONFIG_H_OK\n        all is well, go ahead and compile\n      CONFIG_H_NOTOK\n        doesn't look good\n      CONFIG_H_UNCERTAIN\n        not sure -- unable to read pyconfig.h\n    'details' is a human-readable string explaining the situation.\n\n    Note there are two ways to conclude \"OK\": either 'sys.version' contains\n    the string \"GCC\" (implying that this Python was built with GCC), or the\n    installed \"pyconfig.h\" contains the string \"__GNUC__\".\n    ");
      var1.setline(378);
      String[] var3 = new String[]{"sysconfig"};
      PyObject[] var8 = imp.importFrom("distutils", var3, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(379);
      PyObject var9 = imp.importOne("string", var1, -1);
      var1.setlocal(1, var9);
      var3 = null;
      var1.setline(382);
      var9 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("version"), (PyObject)PyString.fromInterned("GCC"));
      PyObject var10000 = var9._ge(Py.newInteger(0));
      var3 = null;
      PyTuple var10;
      if (var10000.__nonzero__()) {
         var1.setline(383);
         var10 = new PyTuple(new PyObject[]{var1.getglobal("CONFIG_H_OK"), PyString.fromInterned("sys.version mentions 'GCC'")});
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(385);
         var4 = var1.getlocal(0).__getattr__("get_config_h_filename").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;

         PyObject var5;
         try {
            var1.setline(389);
            var4 = var1.getglobal("open").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var4);
            var4 = null;
            var4 = null;

            try {
               var1.setline(391);
               var5 = var1.getlocal(3).__getattr__("read").__call__(var2);
               var1.setlocal(4, var5);
               var5 = null;
            } catch (Throwable var6) {
               Py.addTraceback(var6, var1);
               var1.setline(393);
               var1.getlocal(3).__getattr__("close").__call__(var2);
               throw (Throwable)var6;
            }

            var1.setline(393);
            var1.getlocal(3).__getattr__("close").__call__(var2);
         } catch (Throwable var7) {
            PyException var11 = Py.setException(var7, var1);
            if (var11.match(var1.getglobal("IOError"))) {
               var5 = var11.value;
               var1.setlocal(5, var5);
               var5 = null;
               var1.setline(398);
               var10 = new PyTuple(new PyObject[]{var1.getglobal("CONFIG_H_UNCERTAIN"), PyString.fromInterned("couldn't read '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(5).__getattr__("strerror")}))});
               var1.f_lasti = -1;
               return var10;
            }

            throw var11;
         }

         var1.setline(403);
         var5 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("__GNUC__"));
         var10000 = var5._ge(Py.newInteger(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(404);
            var10 = new PyTuple(new PyObject[]{var1.getglobal("CONFIG_H_OK"), PyString.fromInterned("'%s' mentions '__GNUC__'")._mod(var1.getlocal(2))});
            var1.f_lasti = -1;
            return var10;
         } else {
            var1.setline(406);
            var10 = new PyTuple(new PyObject[]{var1.getglobal("CONFIG_H_NOTOK"), PyString.fromInterned("'%s' does not mention '__GNUC__'")._mod(var1.getlocal(2))});
            var1.f_lasti = -1;
            return var10;
         }
      }
   }

   public PyObject get_versions$10(PyFrame var1, ThreadState var2) {
      var1.setline(413);
      PyString.fromInterned(" Try to find out the versions of gcc, ld and dllwrap.\n        If not possible it returns None for it.\n    ");
      var1.setline(414);
      String[] var3 = new String[]{"LooseVersion"};
      PyObject[] var5 = imp.importFrom("distutils.version", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(415);
      var3 = new String[]{"find_executable"};
      var5 = imp.importFrom("distutils.spawn", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(416);
      PyObject var6 = imp.importOne("re", var1, -1);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(418);
      var6 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("gcc"));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(419);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(420);
         var6 = var1.getglobal("os").__getattr__("popen").__call__((ThreadState)var2, (PyObject)var1.getlocal(3)._add(PyString.fromInterned(" -dumpversion")), (PyObject)PyString.fromInterned("r"));
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(421);
         var6 = var1.getlocal(4).__getattr__("read").__call__(var2);
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(422);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         var1.setline(423);
         var6 = var1.getlocal(2).__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\d+\\.\\d+(\\.\\d+)*)"), (PyObject)var1.getlocal(5));
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(424);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(425);
            var6 = var1.getlocal(0).__call__(var2, var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            var1.setlocal(7, var6);
            var3 = null;
         } else {
            var1.setline(427);
            var6 = var1.getglobal("None");
            var1.setlocal(7, var6);
            var3 = null;
         }
      } else {
         var1.setline(429);
         var6 = var1.getglobal("None");
         var1.setlocal(7, var6);
         var3 = null;
      }

      var1.setline(430);
      var6 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ld"));
      var1.setlocal(8, var6);
      var3 = null;
      var1.setline(431);
      if (var1.getlocal(8).__nonzero__()) {
         var1.setline(432);
         var6 = var1.getglobal("os").__getattr__("popen").__call__((ThreadState)var2, (PyObject)var1.getlocal(8)._add(PyString.fromInterned(" -v")), (PyObject)PyString.fromInterned("r"));
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(433);
         var6 = var1.getlocal(4).__getattr__("read").__call__(var2);
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(434);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         var1.setline(435);
         var6 = var1.getlocal(2).__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\d+\\.\\d+(\\.\\d+)*)"), (PyObject)var1.getlocal(5));
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(436);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(437);
            var6 = var1.getlocal(0).__call__(var2, var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            var1.setlocal(9, var6);
            var3 = null;
         } else {
            var1.setline(439);
            var6 = var1.getglobal("None");
            var1.setlocal(9, var6);
            var3 = null;
         }
      } else {
         var1.setline(441);
         var6 = var1.getglobal("None");
         var1.setlocal(9, var6);
         var3 = null;
      }

      var1.setline(442);
      var6 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("dllwrap"));
      var1.setlocal(10, var6);
      var3 = null;
      var1.setline(443);
      if (var1.getlocal(10).__nonzero__()) {
         var1.setline(444);
         var6 = var1.getglobal("os").__getattr__("popen").__call__((ThreadState)var2, (PyObject)var1.getlocal(10)._add(PyString.fromInterned(" --version")), (PyObject)PyString.fromInterned("r"));
         var1.setlocal(4, var6);
         var3 = null;
         var1.setline(445);
         var6 = var1.getlocal(4).__getattr__("read").__call__(var2);
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(446);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         var1.setline(447);
         var6 = var1.getlocal(2).__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" (\\d+\\.\\d+(\\.\\d+)*)"), (PyObject)var1.getlocal(5));
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(448);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(449);
            var6 = var1.getlocal(0).__call__(var2, var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            var1.setlocal(11, var6);
            var3 = null;
         } else {
            var1.setline(451);
            var6 = var1.getglobal("None");
            var1.setlocal(11, var6);
            var3 = null;
         }
      } else {
         var1.setline(453);
         var6 = var1.getglobal("None");
         var1.setlocal(11, var6);
         var3 = null;
      }

      var1.setline(454);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(9), var1.getlocal(11)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject is_cygwingcc$11(PyFrame var1, ThreadState var2) {
      var1.setline(457);
      PyString.fromInterned("Try to determine if the gcc that would be used is from cygwin.");
      var1.setline(458);
      PyObject var3 = var1.getglobal("os").__getattr__("popen").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("gcc -dumpmachine"), (PyObject)PyString.fromInterned("r"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(459);
      var3 = var1.getlocal(0).__getattr__("read").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(460);
      var1.getlocal(0).__getattr__("close").__call__(var2);
      var1.setline(463);
      var3 = var1.getlocal(1).__getattr__("strip").__call__(var2).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cygwin"));
      var1.f_lasti = -1;
      return var3;
   }

   public cygwinccompiler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"msc_pos", "msc_ver"};
      get_msvcr$1 = Py.newCode(0, var2, var1, "get_msvcr", 59, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      CygwinCCompiler$2 = Py.newCode(0, var2, var1, "CygwinCCompiler", 82, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose", "dry_run", "force", "status", "details", "shared_option"};
      __init__$3 = Py.newCode(4, var2, var1, "__init__", 92, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "src", "ext", "cc_args", "extra_postargs", "pp_opts", "msg"};
      _compile$4 = Py.newCode(7, var2, var1, "_compile", 154, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target_desc", "objects", "output_filename", "output_dir", "libraries", "library_dirs", "runtime_library_dirs", "export_symbols", "debug", "extra_preargs", "extra_postargs", "build_temp", "target_lang", "temp_dir", "dll_name", "dll_extension", "def_file", "lib_file", "contents", "sym"};
      link$5 = Py.newCode(14, var2, var1, "link", 168, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source_filenames", "strip_dir", "output_dir", "obj_names", "src_name", "base", "ext"};
      object_filenames$6 = Py.newCode(4, var2, var1, "object_filenames", 267, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Mingw32CCompiler$7 = Py.newCode(0, var2, var1, "Mingw32CCompiler", 297, false, false, self, 7, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose", "dry_run", "force", "shared_option", "entry_point", "no_cygwin"};
      __init__$8 = Py.newCode(4, var2, var1, "__init__", 301, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sysconfig", "string", "fn", "f", "s", "exc"};
      check_config_h$9 = Py.newCode(0, var2, var1, "check_config_h", 357, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"LooseVersion", "find_executable", "re", "gcc_exe", "out", "out_string", "result", "gcc_version", "ld_exe", "ld_version", "dllwrap_exe", "dllwrap_version"};
      get_versions$10 = Py.newCode(0, var2, var1, "get_versions", 410, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"out", "out_string"};
      is_cygwingcc$11 = Py.newCode(0, var2, var1, "is_cygwingcc", 456, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new cygwinccompiler$py("distutils/cygwinccompiler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(cygwinccompiler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.get_msvcr$1(var2, var3);
         case 2:
            return this.CygwinCCompiler$2(var2, var3);
         case 3:
            return this.__init__$3(var2, var3);
         case 4:
            return this._compile$4(var2, var3);
         case 5:
            return this.link$5(var2, var3);
         case 6:
            return this.object_filenames$6(var2, var3);
         case 7:
            return this.Mingw32CCompiler$7(var2, var3);
         case 8:
            return this.__init__$8(var2, var3);
         case 9:
            return this.check_config_h$9(var2, var3);
         case 10:
            return this.get_versions$10(var2, var3);
         case 11:
            return this.is_cygwingcc$11(var2, var3);
         default:
            return null;
      }
   }
}
