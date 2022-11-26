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
@Filename("distutils/emxccompiler.py")
public class emxccompiler$py extends PyFunctionTable implements PyRunnable {
   static emxccompiler$py self;
   static final PyCode f$0;
   static final PyCode EMXCCompiler$1;
   static final PyCode __init__$2;
   static final PyCode _compile$3;
   static final PyCode link$4;
   static final PyCode object_filenames$5;
   static final PyCode find_library_file$6;
   static final PyCode check_config_h$7;
   static final PyCode get_versions$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.emxccompiler\n\nProvides the EMXCCompiler class, a subclass of UnixCCompiler that\nhandles the EMX port of the GNU C compiler to OS/2.\n"));
      var1.setline(5);
      PyString.fromInterned("distutils.emxccompiler\n\nProvides the EMXCCompiler class, a subclass of UnixCCompiler that\nhandles the EMX port of the GNU C compiler to OS/2.\n");
      var1.setline(22);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(24);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var5 = imp.importOne("copy", var1, -1);
      var1.setlocal("copy", var5);
      var3 = null;
      var1.setline(25);
      String[] var6 = new String[]{"gen_preprocess_options", "gen_lib_options"};
      PyObject[] var7 = imp.importFrom("distutils.ccompiler", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("gen_preprocess_options", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("gen_lib_options", var4);
      var4 = null;
      var1.setline(26);
      var6 = new String[]{"UnixCCompiler"};
      var7 = imp.importFrom("distutils.unixccompiler", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("UnixCCompiler", var4);
      var4 = null;
      var1.setline(27);
      var6 = new String[]{"write_file"};
      var7 = imp.importFrom("distutils.file_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("write_file", var4);
      var4 = null;
      var1.setline(28);
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
      var1.setline(29);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(31);
      var7 = new PyObject[]{var1.getname("UnixCCompiler")};
      var4 = Py.makeClass("EMXCCompiler", var7, EMXCCompiler$1);
      var1.setlocal("EMXCCompiler", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(238);
      var3 = PyString.fromInterned("ok");
      var1.setlocal("CONFIG_H_OK", var3);
      var3 = null;
      var1.setline(239);
      var3 = PyString.fromInterned("not ok");
      var1.setlocal("CONFIG_H_NOTOK", var3);
      var3 = null;
      var1.setline(240);
      var3 = PyString.fromInterned("uncertain");
      var1.setlocal("CONFIG_H_UNCERTAIN", var3);
      var3 = null;
      var1.setline(242);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, check_config_h$7, PyString.fromInterned("Check if the current Python installation (specifically, pyconfig.h)\n    appears amenable to building extensions with GCC.  Returns a tuple\n    (status, details), where 'status' is one of the following constants:\n      CONFIG_H_OK\n        all is well, go ahead and compile\n      CONFIG_H_NOTOK\n        doesn't look good\n      CONFIG_H_UNCERTAIN\n        not sure -- unable to read pyconfig.h\n    'details' is a human-readable string explaining the situation.\n\n    Note there are two ways to conclude \"OK\": either 'sys.version' contains\n    the string \"GCC\" (implying that this Python was built with GCC), or the\n    installed \"pyconfig.h\" contains the string \"__GNUC__\".\n    "));
      var1.setlocal("check_config_h", var8);
      var3 = null;
      var1.setline(294);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_versions$8, PyString.fromInterned(" Try to find out the versions of gcc and ld.\n        If not possible it returns None for it.\n    "));
      var1.setlocal("get_versions", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject EMXCCompiler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(33);
      PyString var3 = PyString.fromInterned("emx");
      var1.setlocal("compiler_type", var3);
      var3 = null;
      var1.setline(34);
      var3 = PyString.fromInterned(".obj");
      var1.setlocal("obj_extension", var3);
      var3 = null;
      var1.setline(35);
      var3 = PyString.fromInterned(".lib");
      var1.setlocal("static_lib_extension", var3);
      var3 = null;
      var1.setline(36);
      var3 = PyString.fromInterned(".dll");
      var1.setlocal("shared_lib_extension", var3);
      var3 = null;
      var1.setline(37);
      var3 = PyString.fromInterned("%s%s");
      var1.setlocal("static_lib_format", var3);
      var3 = null;
      var1.setline(38);
      var3 = PyString.fromInterned("%s%s");
      var1.setlocal("shared_lib_format", var3);
      var3 = null;
      var1.setline(39);
      var3 = PyString.fromInterned(".res");
      var1.setlocal("res_extension", var3);
      var3 = null;
      var1.setline(40);
      var3 = PyString.fromInterned(".exe");
      var1.setlocal("exe_extension", var3);
      var3 = null;
      var1.setline(42);
      PyObject[] var4 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(77);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _compile$3, (PyObject)null);
      var1.setlocal("_compile", var5);
      var3 = null;
      var1.setline(91);
      var4 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var5 = new PyFunction(var1.f_globals, var4, link$4, (PyObject)null);
      var1.setlocal("link", var5);
      var3 = null;
      var1.setline(182);
      var4 = new PyObject[]{Py.newInteger(0), PyString.fromInterned("")};
      var5 = new PyFunction(var1.f_globals, var4, object_filenames$5, (PyObject)null);
      var1.setlocal("object_filenames", var5);
      var3 = null;
      var1.setline(210);
      var4 = new PyObject[]{Py.newInteger(0)};
      var5 = new PyFunction(var1.f_globals, var4, find_library_file$6, (PyObject)null);
      var1.setlocal("find_library_file", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      var1.getglobal("UnixCCompiler").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(49);
      PyObject var3 = var1.getglobal("check_config_h").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(4, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(5, var5);
      var5 = null;
      var3 = null;
      var1.setline(50);
      var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("Python's GCC status: %s (details: %s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})));
      var1.setline(52);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("CONFIG_H_OK"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(53);
         var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("Python's pyconfig.h doesn't seem to support your compiler.  ")._add(PyString.fromInterned("Reason: %s.")._mod(var1.getlocal(5)))._add(PyString.fromInterned("Compiling may fail because of undefined preprocessor macros.")));
      }

      var1.setline(58);
      var3 = var1.getglobal("get_versions").__call__(var2);
      var4 = Py.unpackSequence(var3, 2);
      var5 = var4[0];
      var1.getlocal(0).__setattr__("gcc_version", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("ld_version", var5);
      var5 = null;
      var3 = null;
      var1.setline(60);
      var1.getlocal(0).__getattr__("debug_print").__call__(var2, var1.getlocal(0).__getattr__("compiler_type")._add(PyString.fromInterned(": gcc %s, ld %s\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("gcc_version"), var1.getlocal(0).__getattr__("ld_version")}))));
      var1.setline(66);
      var10000 = var1.getlocal(0).__getattr__("set_executables");
      PyObject[] var7 = new PyObject[]{PyString.fromInterned("gcc -Zomf -Zmt -O3 -fomit-frame-pointer -mprobe -Wall"), PyString.fromInterned("gcc -Zomf -Zmt -O3 -fomit-frame-pointer -mprobe -Wall"), PyString.fromInterned("gcc -Zomf -Zmt -Zcrtdll"), PyString.fromInterned("gcc -Zomf -Zmt -Zcrtdll -Zdll")};
      String[] var6 = new String[]{"compiler", "compiler_so", "linker_exe", "linker_so"};
      var10000.__call__(var2, var7, var6);
      var3 = null;
      var1.setline(73);
      PyList var8 = new PyList(new PyObject[]{PyString.fromInterned("gcc")});
      var1.getlocal(0).__setattr__((String)"dll_libraries", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _compile$3(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(PyString.fromInterned(".rc"));
      var3 = null;
      PyObject var4;
      PyException var7;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(81);
            var1.getlocal(0).__getattr__("spawn").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("rc"), PyString.fromInterned("-r"), var1.getlocal(2)})));
         } catch (Throwable var5) {
            var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("DistutilsExecError"))) {
               var4 = var7.value;
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(83);
               throw Py.makeException(var1.getglobal("CompileError"), var1.getlocal(7));
            }

            throw var7;
         }
      } else {
         try {
            var1.setline(86);
            var1.getlocal(0).__getattr__("spawn").__call__(var2, var1.getlocal(0).__getattr__("compiler_so")._add(var1.getlocal(4))._add(new PyList(new PyObject[]{var1.getlocal(2), PyString.fromInterned("-o"), var1.getlocal(1)}))._add(var1.getlocal(5)));
         } catch (Throwable var6) {
            var7 = Py.setException(var6, var1);
            if (var7.match(var1.getglobal("DistutilsExecError"))) {
               var4 = var7.value;
               var1.setlocal(7, var4);
               var4 = null;
               var1.setline(89);
               throw Py.makeException(var1.getglobal("CompileError"), var1.getlocal(7));
            }

            throw var7;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject link$4(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyObject var10000 = var1.getglobal("copy").__getattr__("copy");
      Object var10002 = var1.getlocal(10);
      if (!((PyObject)var10002).__nonzero__()) {
         var10002 = new PyList(Py.EmptyObjects);
      }

      PyObject var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(108);
      var10000 = var1.getglobal("copy").__getattr__("copy");
      var10002 = var1.getlocal(5);
      if (!((PyObject)var10002).__nonzero__()) {
         var10002 = new PyList(Py.EmptyObjects);
      }

      var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(109);
      var10000 = var1.getglobal("copy").__getattr__("copy");
      var10002 = var1.getlocal(2);
      if (!((PyObject)var10002).__nonzero__()) {
         var10002 = new PyList(Py.EmptyObjects);
      }

      var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(112);
      var1.getlocal(5).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("dll_libraries"));
      var1.setline(116);
      var3 = var1.getlocal(8);
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._ne(var1.getlocal(0).__getattr__("EXECUTABLE"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(126);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)));
         var1.setlocal(14, var3);
         var3 = null;
         var1.setline(128);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3)));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(15, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(16, var5);
         var5 = null;
         var3 = null;
         var1.setline(132);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(14), var1.getlocal(15)._add(PyString.fromInterned(".def")));
         var1.setlocal(17, var3);
         var3 = null;
         var1.setline(135);
         PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("LIBRARY %s INITINSTANCE TERMINSTANCE")._mod(var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3))).__getitem__(Py.newInteger(0))), PyString.fromInterned("DATA MULTIPLE NONSHARED"), PyString.fromInterned("EXPORTS")});
         var1.setlocal(18, var7);
         var3 = null;
         var1.setline(140);
         var3 = var1.getlocal(8).__iter__();

         while(true) {
            var1.setline(140);
            PyObject var6 = var3.__iternext__();
            if (var6 == null) {
               var1.setline(142);
               var1.getlocal(0).__getattr__("execute").__call__((ThreadState)var2, var1.getglobal("write_file"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(17), var1.getlocal(18)})), (PyObject)PyString.fromInterned("writing %s")._mod(var1.getlocal(17)));
               var1.setline(147);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(17));
               break;
            }

            var1.setlocal(19, var6);
            var1.setline(141);
            var1.getlocal(18).__getattr__("append").__call__(var2, PyString.fromInterned("  \"%s\"")._mod(var1.getlocal(19)));
         }
      }

      var1.setline(158);
      if (var1.getlocal(9).__not__().__nonzero__()) {
         var1.setline(159);
         var1.getlocal(10).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-s"));
      }

      var1.setline(161);
      var10000 = var1.getglobal("UnixCCompiler").__getattr__("link");
      PyObject[] var8 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getglobal("None"), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), var1.getlocal(12), var1.getlocal(13)};
      var10000.__call__(var2, var8);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject object_filenames$5(PyFrame var1, ThreadState var2) {
      var1.setline(186);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(186);
         PyString var8 = PyString.fromInterned("");
         var1.setlocal(3, var8);
         var3 = null;
      }

      var1.setline(187);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(188);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(188);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(204);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(190);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(5)));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(7, var7);
         var7 = null;
         var5 = null;
         var1.setline(191);
         var5 = var1.getlocal(7);
         var10000 = var5._notin(var1.getlocal(0).__getattr__("src_extensions")._add(new PyList(new PyObject[]{PyString.fromInterned(".rc")})));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(192);
            throw Py.makeException(var1.getglobal("UnknownFileError"), PyString.fromInterned("unknown file type '%s' (from '%s')")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5)})));
         }

         var1.setline(195);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(196);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var5);
            var5 = null;
         }

         var1.setline(197);
         var5 = var1.getlocal(7);
         var10000 = var5._eq(PyString.fromInterned(".rc"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(199);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(0).__getattr__("res_extension"))));
         } else {
            var1.setline(202);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(0).__getattr__("obj_extension"))));
         }
      }
   }

   public PyObject find_library_file$6(PyFrame var1, ThreadState var2) {
      var1.setline(211);
      PyObject var3 = PyString.fromInterned("%s.lib")._mod(var1.getlocal(2));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(212);
      var3 = PyString.fromInterned("lib%s.lib")._mod(var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;

      try {
         var1.setline(216);
         var3 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("LIBRARY_PATH")).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
         var1.setlocal(6, var3);
         var3 = null;
      } catch (Throwable var6) {
         PyException var7 = Py.setException(var6, var1);
         if (!var7.match(var1.getglobal("KeyError"))) {
            throw var7;
         }

         var1.setline(218);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(6, var4);
         var4 = null;
      }

      var1.setline(220);
      var3 = var1.getlocal(1)._add(var1.getlocal(6)).__iter__();

      PyObject var5;
      do {
         var1.setline(220);
         PyObject var8 = var3.__iternext__();
         if (var8 == null) {
            var1.setline(229);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(7, var8);
         var1.setline(221);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(7), var1.getlocal(4));
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(222);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(7), var1.getlocal(5));
         var1.setlocal(9, var5);
         var5 = null;
         var1.setline(223);
         if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(8)).__nonzero__()) {
            var1.setline(224);
            var5 = var1.getlocal(8);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(225);
      } while(!var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(9)).__nonzero__());

      var1.setline(226);
      var5 = var1.getlocal(9);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject check_config_h$7(PyFrame var1, ThreadState var2) {
      var1.setline(258);
      PyString.fromInterned("Check if the current Python installation (specifically, pyconfig.h)\n    appears amenable to building extensions with GCC.  Returns a tuple\n    (status, details), where 'status' is one of the following constants:\n      CONFIG_H_OK\n        all is well, go ahead and compile\n      CONFIG_H_NOTOK\n        doesn't look good\n      CONFIG_H_UNCERTAIN\n        not sure -- unable to read pyconfig.h\n    'details' is a human-readable string explaining the situation.\n\n    Note there are two ways to conclude \"OK\": either 'sys.version' contains\n    the string \"GCC\" (implying that this Python was built with GCC), or the\n    installed \"pyconfig.h\" contains the string \"__GNUC__\".\n    ");
      var1.setline(263);
      String[] var3 = new String[]{"sysconfig"};
      PyObject[] var8 = imp.importFrom("distutils", var3, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(264);
      PyObject var9 = imp.importOne("string", var1, -1);
      var1.setlocal(1, var9);
      var3 = null;
      var1.setline(267);
      var9 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("version"), (PyObject)PyString.fromInterned("GCC"));
      PyObject var10000 = var9._ge(Py.newInteger(0));
      var3 = null;
      PyTuple var10;
      if (var10000.__nonzero__()) {
         var1.setline(268);
         var10 = new PyTuple(new PyObject[]{var1.getglobal("CONFIG_H_OK"), PyString.fromInterned("sys.version mentions 'GCC'")});
         var1.f_lasti = -1;
         return var10;
      } else {
         var1.setline(270);
         var4 = var1.getlocal(0).__getattr__("get_config_h_filename").__call__(var2);
         var1.setlocal(2, var4);
         var4 = null;

         PyObject var5;
         try {
            var1.setline(274);
            var4 = var1.getglobal("open").__call__(var2, var1.getlocal(2));
            var1.setlocal(3, var4);
            var4 = null;
            var4 = null;

            try {
               var1.setline(276);
               var5 = var1.getlocal(3).__getattr__("read").__call__(var2);
               var1.setlocal(4, var5);
               var5 = null;
            } catch (Throwable var6) {
               Py.addTraceback(var6, var1);
               var1.setline(278);
               var1.getlocal(3).__getattr__("close").__call__(var2);
               throw (Throwable)var6;
            }

            var1.setline(278);
            var1.getlocal(3).__getattr__("close").__call__(var2);
         } catch (Throwable var7) {
            PyException var11 = Py.setException(var7, var1);
            if (var11.match(var1.getglobal("IOError"))) {
               var5 = var11.value;
               var1.setlocal(5, var5);
               var5 = null;
               var1.setline(283);
               var10 = new PyTuple(new PyObject[]{var1.getglobal("CONFIG_H_UNCERTAIN"), PyString.fromInterned("couldn't read '%s': %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(5).__getattr__("strerror")}))});
               var1.f_lasti = -1;
               return var10;
            }

            throw var11;
         }

         var1.setline(288);
         var5 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("__GNUC__"));
         var10000 = var5._ge(Py.newInteger(0));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(289);
            var10 = new PyTuple(new PyObject[]{var1.getglobal("CONFIG_H_OK"), PyString.fromInterned("'%s' mentions '__GNUC__'")._mod(var1.getlocal(2))});
            var1.f_lasti = -1;
            return var10;
         } else {
            var1.setline(291);
            var10 = new PyTuple(new PyObject[]{var1.getglobal("CONFIG_H_NOTOK"), PyString.fromInterned("'%s' does not mention '__GNUC__'")._mod(var1.getlocal(2))});
            var1.f_lasti = -1;
            return var10;
         }
      }
   }

   public PyObject get_versions$8(PyFrame var1, ThreadState var2) {
      var1.setline(297);
      PyString.fromInterned(" Try to find out the versions of gcc and ld.\n        If not possible it returns None for it.\n    ");
      var1.setline(298);
      String[] var3 = new String[]{"StrictVersion"};
      PyObject[] var6 = imp.importFrom("distutils.version", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(299);
      var3 = new String[]{"find_executable"};
      var6 = imp.importFrom("distutils.spawn", var3, var1, -1);
      var4 = var6[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(300);
      PyObject var7 = imp.importOne("re", var1, -1);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(302);
      var7 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("gcc"));
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(303);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(304);
         var7 = var1.getglobal("os").__getattr__("popen").__call__((ThreadState)var2, (PyObject)var1.getlocal(3)._add(PyString.fromInterned(" -dumpversion")), (PyObject)PyString.fromInterned("r"));
         var1.setlocal(4, var7);
         var3 = null;
         var3 = null;

         try {
            var1.setline(306);
            var4 = var1.getlocal(4).__getattr__("read").__call__(var2);
            var1.setlocal(5, var4);
            var4 = null;
         } catch (Throwable var5) {
            Py.addTraceback(var5, var1);
            var1.setline(308);
            var1.getlocal(4).__getattr__("close").__call__(var2);
            throw (Throwable)var5;
         }

         var1.setline(308);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         var1.setline(309);
         var7 = var1.getlocal(2).__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("(\\d+\\.\\d+\\.\\d+)"), (PyObject)var1.getlocal(5));
         var1.setlocal(6, var7);
         var3 = null;
         var1.setline(310);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(311);
            var7 = var1.getlocal(0).__call__(var2, var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)));
            var1.setlocal(7, var7);
            var3 = null;
         } else {
            var1.setline(313);
            var7 = var1.getglobal("None");
            var1.setlocal(7, var7);
            var3 = null;
         }
      } else {
         var1.setline(315);
         var7 = var1.getglobal("None");
         var1.setlocal(7, var7);
         var3 = null;
      }

      var1.setline(318);
      var7 = var1.getglobal("None");
      var1.setlocal(8, var7);
      var3 = null;
      var1.setline(319);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8)});
      var1.f_lasti = -1;
      return var8;
   }

   public emxccompiler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      EMXCCompiler$1 = Py.newCode(0, var2, var1, "EMXCCompiler", 31, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose", "dry_run", "force", "status", "details"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 42, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "src", "ext", "cc_args", "extra_postargs", "pp_opts", "msg"};
      _compile$3 = Py.newCode(7, var2, var1, "_compile", 77, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target_desc", "objects", "output_filename", "output_dir", "libraries", "library_dirs", "runtime_library_dirs", "export_symbols", "debug", "extra_preargs", "extra_postargs", "build_temp", "target_lang", "temp_dir", "dll_name", "dll_extension", "def_file", "contents", "sym"};
      link$4 = Py.newCode(14, var2, var1, "link", 91, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source_filenames", "strip_dir", "output_dir", "obj_names", "src_name", "base", "ext"};
      object_filenames$5 = Py.newCode(4, var2, var1, "object_filenames", 182, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirs", "lib", "debug", "shortlib", "longlib", "emx_dirs", "dir", "shortlibp", "longlibp"};
      find_library_file$6 = Py.newCode(4, var2, var1, "find_library_file", 210, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sysconfig", "string", "fn", "f", "s", "exc"};
      check_config_h$7 = Py.newCode(0, var2, var1, "check_config_h", 242, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"StrictVersion", "find_executable", "re", "gcc_exe", "out", "out_string", "result", "gcc_version", "ld_version"};
      get_versions$8 = Py.newCode(0, var2, var1, "get_versions", 294, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new emxccompiler$py("distutils/emxccompiler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(emxccompiler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.EMXCCompiler$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this._compile$3(var2, var3);
         case 4:
            return this.link$4(var2, var3);
         case 5:
            return this.object_filenames$5(var2, var3);
         case 6:
            return this.find_library_file$6(var2, var3);
         case 7:
            return this.check_config_h$7(var2, var3);
         case 8:
            return this.get_versions$8(var2, var3);
         default:
            return null;
      }
   }
}
