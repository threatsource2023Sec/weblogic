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
@Filename("distutils/bcppcompiler.py")
public class bcppcompiler$py extends PyFunctionTable implements PyRunnable {
   static bcppcompiler$py self;
   static final PyCode f$0;
   static final PyCode BCPPCompiler$1;
   static final PyCode __init__$2;
   static final PyCode compile$3;
   static final PyCode create_static_lib$4;
   static final PyCode link$5;
   static final PyCode find_library_file$6;
   static final PyCode object_filenames$7;
   static final PyCode preprocess$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.bcppcompiler\n\nContains BorlandCCompiler, an implementation of the abstract CCompiler class\nfor the Borland C++ compiler.\n"));
      var1.setline(5);
      PyString.fromInterned("distutils.bcppcompiler\n\nContains BorlandCCompiler, an implementation of the abstract CCompiler class\nfor the Borland C++ compiler.\n");
      var1.setline(14);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(16);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(18);
      String[] var6 = new String[]{"DistutilsExecError", "CompileError", "LibError", "LinkError", "UnknownFileError"};
      PyObject[] var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      PyObject var4 = var7[0];
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
      var4 = var7[4];
      var1.setlocal("UnknownFileError", var4);
      var4 = null;
      var1.setline(20);
      var6 = new String[]{"CCompiler", "gen_preprocess_options"};
      var7 = imp.importFrom("distutils.ccompiler", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("CCompiler", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("gen_preprocess_options", var4);
      var4 = null;
      var1.setline(21);
      var6 = new String[]{"write_file"};
      var7 = imp.importFrom("distutils.file_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("write_file", var4);
      var4 = null;
      var1.setline(22);
      var6 = new String[]{"newer"};
      var7 = imp.importFrom("distutils.dep_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("newer", var4);
      var4 = null;
      var1.setline(23);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(25);
      var7 = new PyObject[]{var1.getname("CCompiler")};
      var4 = Py.makeClass("BCPPCompiler", var7, BCPPCompiler$1);
      var1.setlocal("BCPPCompiler", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BCPPCompiler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Concrete class that implements an interface to the Borland C/C++\n    compiler, as defined by the CCompiler abstract class.\n    "));
      var1.setline(28);
      PyString.fromInterned("Concrete class that implements an interface to the Borland C/C++\n    compiler, as defined by the CCompiler abstract class.\n    ");
      var1.setline(30);
      PyString var3 = PyString.fromInterned("bcpp");
      var1.setlocal("compiler_type", var3);
      var3 = null;
      var1.setline(37);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("executables", var4);
      var3 = null;
      var1.setline(40);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned(".c")});
      var1.setlocal("_c_extensions", var5);
      var3 = null;
      var1.setline(41);
      var5 = new PyList(new PyObject[]{PyString.fromInterned(".cc"), PyString.fromInterned(".cpp"), PyString.fromInterned(".cxx")});
      var1.setlocal("_cpp_extensions", var5);
      var3 = null;
      var1.setline(45);
      PyObject var6 = var1.getname("_c_extensions")._add(var1.getname("_cpp_extensions"));
      var1.setlocal("src_extensions", var6);
      var3 = null;
      var1.setline(46);
      var3 = PyString.fromInterned(".obj");
      var1.setlocal("obj_extension", var3);
      var3 = null;
      var1.setline(47);
      var3 = PyString.fromInterned(".lib");
      var1.setlocal("static_lib_extension", var3);
      var3 = null;
      var1.setline(48);
      var3 = PyString.fromInterned(".dll");
      var1.setlocal("shared_lib_extension", var3);
      var3 = null;
      var1.setline(49);
      var3 = PyString.fromInterned("%s%s");
      var1.setlocal("static_lib_format", var3);
      var1.setlocal("shared_lib_format", var3);
      var1.setline(50);
      var3 = PyString.fromInterned(".exe");
      var1.setlocal("exe_extension", var3);
      var3 = null;
      var1.setline(53);
      PyObject[] var7 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var8 = new PyFunction(var1.f_globals, var7, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var8);
      var3 = null;
      var1.setline(81);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, compile$3, (PyObject)null);
      var1.setlocal("compile", var8);
      var3 = null;
      var1.setline(146);
      var7 = new PyObject[]{var1.getname("None"), Py.newInteger(0), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, create_static_lib$4, (PyObject)null);
      var1.setlocal("create_static_lib", var8);
      var3 = null;
      var1.setline(171);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, link$5, (PyObject)null);
      var1.setlocal("link", var8);
      var3 = null;
      var1.setline(308);
      var7 = new PyObject[]{Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, find_library_file$6, (PyObject)null);
      var1.setlocal("find_library_file", var8);
      var3 = null;
      var1.setline(334);
      var7 = new PyObject[]{Py.newInteger(0), PyString.fromInterned("")};
      var8 = new PyFunction(var1.f_globals, var7, object_filenames$7, (PyObject)null);
      var1.setlocal("object_filenames", var8);
      var3 = null;
      var1.setline(362);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, preprocess$8, (PyObject)null);
      var1.setlocal("preprocess", var8);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      var1.getglobal("CCompiler").__getattr__("__init__").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3));
      var1.setline(64);
      PyString var3 = PyString.fromInterned("bcc32.exe");
      var1.getlocal(0).__setattr__((String)"cc", var3);
      var3 = null;
      var1.setline(65);
      var3 = PyString.fromInterned("ilink32.exe");
      var1.getlocal(0).__setattr__((String)"linker", var3);
      var3 = null;
      var1.setline(66);
      var3 = PyString.fromInterned("tlib.exe");
      var1.getlocal(0).__setattr__((String)"lib", var3);
      var3 = null;
      var1.setline(68);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("preprocess_options", var4);
      var3 = null;
      var1.setline(69);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("/tWM"), PyString.fromInterned("/O2"), PyString.fromInterned("/q"), PyString.fromInterned("/g0")});
      var1.getlocal(0).__setattr__((String)"compile_options", var5);
      var3 = null;
      var1.setline(70);
      var5 = new PyList(new PyObject[]{PyString.fromInterned("/tWM"), PyString.fromInterned("/Od"), PyString.fromInterned("/q"), PyString.fromInterned("/g0")});
      var1.getlocal(0).__setattr__((String)"compile_options_debug", var5);
      var3 = null;
      var1.setline(72);
      var5 = new PyList(new PyObject[]{PyString.fromInterned("/Tpd"), PyString.fromInterned("/Gn"), PyString.fromInterned("/q"), PyString.fromInterned("/x")});
      var1.getlocal(0).__setattr__((String)"ldflags_shared", var5);
      var3 = null;
      var1.setline(73);
      var5 = new PyList(new PyObject[]{PyString.fromInterned("/Tpd"), PyString.fromInterned("/Gn"), PyString.fromInterned("/q"), PyString.fromInterned("/x")});
      var1.getlocal(0).__setattr__((String)"ldflags_shared_debug", var5);
      var3 = null;
      var1.setline(74);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"ldflags_static", var5);
      var3 = null;
      var1.setline(75);
      var5 = new PyList(new PyObject[]{PyString.fromInterned("/Gn"), PyString.fromInterned("/q"), PyString.fromInterned("/x")});
      var1.getlocal(0).__setattr__((String)"ldflags_exe", var5);
      var3 = null;
      var1.setline(76);
      var5 = new PyList(new PyObject[]{PyString.fromInterned("/Gn"), PyString.fromInterned("/q"), PyString.fromInterned("/x"), PyString.fromInterned("/r")});
      var1.getlocal(0).__setattr__((String)"ldflags_exe_debug", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject compile$3(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyObject var10000 = var1.getlocal(0).__getattr__("_setup_compile");
      PyObject[] var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(1), var1.getlocal(8), var1.getlocal(7)};
      PyObject var11 = var10000.__call__(var2, var3);
      PyObject[] var4 = Py.unpackSequence(var11, 5);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(9, var5);
      var5 = null;
      var5 = var4[2];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[3];
      var1.setlocal(10, var5);
      var5 = null;
      var5 = var4[4];
      var1.setlocal(11, var5);
      var5 = null;
      var3 = null;
      var1.setline(88);
      Object var17 = var1.getlocal(6);
      if (!((PyObject)var17).__nonzero__()) {
         var17 = new PyList(Py.EmptyObjects);
      }

      Object var12 = var17;
      var1.setlocal(12, (PyObject)var12);
      var3 = null;
      var1.setline(89);
      var1.getlocal(12).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-c"));
      var1.setline(90);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(91);
         var1.getlocal(12).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("compile_options_debug"));
      } else {
         var1.setline(93);
         var1.getlocal(12).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("compile_options"));
      }

      var1.setline(95);
      var11 = var1.getlocal(9).__iter__();

      while(true) {
         var1.setline(95);
         PyObject var13 = var11.__iternext__();
         if (var13 == null) {
            var1.setline(141);
            var11 = var1.getlocal(9);
            var1.f_lasti = -1;
            return var11;
         }

         var1.setlocal(13, var13);

         PyObject[] var6;
         PyException var15;
         try {
            var1.setline(97);
            var5 = var1.getlocal(11).__getitem__(var1.getlocal(13));
            var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(14, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(15, var7);
            var7 = null;
            var5 = null;
         } catch (Throwable var10) {
            var15 = Py.setException(var10, var1);
            if (var15.match(var1.getglobal("KeyError"))) {
               continue;
            }

            throw var15;
         }

         var1.setline(101);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(14));
         var1.setlocal(14, var5);
         var5 = null;
         var1.setline(102);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(13));
         var1.setlocal(13, var5);
         var5 = null;
         var1.setline(105);
         var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(13)));
         var1.setline(107);
         var5 = var1.getlocal(15);
         var10000 = var5._eq(PyString.fromInterned(".res"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(110);
            var5 = var1.getlocal(15);
            var10000 = var5._eq(PyString.fromInterned(".rc"));
            var5 = null;
            PyObject var14;
            if (var10000.__nonzero__()) {
               try {
                  var1.setline(113);
                  var1.getlocal(0).__getattr__("spawn").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("brcc32"), PyString.fromInterned("-fo"), var1.getlocal(13), var1.getlocal(14)})));
               } catch (Throwable var8) {
                  var15 = Py.setException(var8, var1);
                  if (var15.match(var1.getglobal("DistutilsExecError"))) {
                     var14 = var15.value;
                     var1.setlocal(16, var14);
                     var6 = null;
                     var1.setline(115);
                     throw Py.makeException(var1.getglobal("CompileError"), var1.getlocal(16));
                  }

                  throw var15;
               }
            } else {
               var1.setline(119);
               var5 = var1.getlocal(15);
               var10000 = var5._in(var1.getlocal(0).__getattr__("_c_extensions"));
               var5 = null;
               PyString var16;
               if (var10000.__nonzero__()) {
                  var1.setline(120);
                  var16 = PyString.fromInterned("");
                  var1.setlocal(17, var16);
                  var5 = null;
               } else {
                  var1.setline(121);
                  var5 = var1.getlocal(15);
                  var10000 = var5._in(var1.getlocal(0).__getattr__("_cpp_extensions"));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(122);
                     var16 = PyString.fromInterned("-P");
                     var1.setlocal(17, var16);
                     var5 = null;
                  } else {
                     var1.setline(127);
                     var16 = PyString.fromInterned("");
                     var1.setlocal(17, var16);
                     var5 = null;
                  }
               }

               var1.setline(129);
               var5 = PyString.fromInterned("-o")._add(var1.getlocal(13));
               var1.setlocal(18, var5);
               var5 = null;

               try {
                  var1.setline(135);
                  var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("cc")}))._add(var1.getlocal(12))._add(var1.getlocal(10))._add(new PyList(new PyObject[]{var1.getlocal(17), var1.getlocal(18)}))._add(var1.getlocal(7))._add(new PyList(new PyObject[]{var1.getlocal(14)})));
               } catch (Throwable var9) {
                  var15 = Py.setException(var9, var1);
                  if (var15.match(var1.getglobal("DistutilsExecError"))) {
                     var14 = var15.value;
                     var1.setlocal(16, var14);
                     var6 = null;
                     var1.setline(139);
                     throw Py.makeException(var1.getglobal("CompileError"), var1.getlocal(16));
                  }

                  throw var15;
               }
            }
         }
      }
   }

   public PyObject create_static_lib$4(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_object_args").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(154);
      PyObject var10000 = var1.getlocal(0).__getattr__("library_filename");
      PyObject[] var7 = new PyObject[]{var1.getlocal(2), var1.getlocal(3)};
      String[] var8 = new String[]{"output_dir"};
      var10000 = var10000.__call__(var2, var7, var8);
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(157);
      if (var1.getlocal(0).__getattr__("_need_link").__call__(var2, var1.getlocal(1), var1.getlocal(6)).__nonzero__()) {
         var1.setline(158);
         var3 = (new PyList(new PyObject[]{var1.getlocal(6), PyString.fromInterned("/u")}))._add(var1.getlocal(1));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(159);
         if (var1.getlocal(4).__nonzero__()) {
            var1.setline(160);
         }

         try {
            var1.setline(162);
            var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("lib")}))._add(var1.getlocal(7)));
         } catch (Throwable var6) {
            PyException var10 = Py.setException(var6, var1);
            if (var10.match(var1.getglobal("DistutilsExecError"))) {
               PyObject var9 = var10.value;
               var1.setlocal(8, var9);
               var4 = null;
               var1.setline(164);
               throw Py.makeException(var1.getglobal("LibError"), var1.getlocal(8));
            }

            throw var10;
         }
      } else {
         var1.setline(166);
         var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("skipping %s (up-to-date)"), (PyObject)var1.getlocal(6));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject link$5(PyFrame var1, ThreadState var2) {
      var1.setline(189);
      PyObject var3 = var1.getlocal(0).__getattr__("_fix_object_args").__call__(var2, var1.getlocal(2), var1.getlocal(4));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(190);
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
      var1.setline(193);
      if (var1.getlocal(7).__nonzero__()) {
         var1.setline(194);
         var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("I don't know what to do with 'runtime_library_dirs': %s"), (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(7)));
      }

      var1.setline(197);
      var3 = var1.getlocal(4);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(198);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(3));
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(200);
      if (var1.getlocal(0).__getattr__("_need_link").__call__(var2, var1.getlocal(2), var1.getlocal(3)).__nonzero__()) {
         var1.setline(203);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(var1.getglobal("CCompiler").__getattr__("EXECUTABLE"));
         var3 = null;
         PyString var10;
         if (var10000.__nonzero__()) {
            var1.setline(204);
            var10 = PyString.fromInterned("c0w32");
            var1.setlocal(14, var10);
            var3 = null;
            var1.setline(205);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(206);
               var3 = var1.getlocal(0).__getattr__("ldflags_exe_debug").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
               var1.setlocal(15, var3);
               var3 = null;
            } else {
               var1.setline(208);
               var3 = var1.getlocal(0).__getattr__("ldflags_exe").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
               var1.setlocal(15, var3);
               var3 = null;
            }
         } else {
            var1.setline(210);
            var10 = PyString.fromInterned("c0d32");
            var1.setlocal(14, var10);
            var3 = null;
            var1.setline(211);
            if (var1.getlocal(9).__nonzero__()) {
               var1.setline(212);
               var3 = var1.getlocal(0).__getattr__("ldflags_shared_debug").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
               var1.setlocal(15, var3);
               var3 = null;
            } else {
               var1.setline(214);
               var3 = var1.getlocal(0).__getattr__("ldflags_shared").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
               var1.setlocal(15, var3);
               var3 = null;
            }
         }

         var1.setline(218);
         var3 = var1.getlocal(8);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         PyObject var9;
         PyList var11;
         if (var10000.__nonzero__()) {
            var1.setline(219);
            var10 = PyString.fromInterned("");
            var1.setlocal(16, var10);
            var3 = null;
         } else {
            var1.setline(221);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(3));
            var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(17, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(18, var5);
            var5 = null;
            var3 = null;
            var1.setline(222);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(18));
            var4 = Py.unpackSequence(var3, 2);
            var5 = var4[0];
            var1.setlocal(19, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(20, var5);
            var5 = null;
            var3 = null;
            var1.setline(223);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)));
            var1.setlocal(21, var3);
            var3 = null;
            var1.setline(224);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(21), PyString.fromInterned("%s.def")._mod(var1.getlocal(19)));
            var1.setlocal(16, var3);
            var3 = null;
            var1.setline(225);
            var11 = new PyList(new PyObject[]{PyString.fromInterned("EXPORTS")});
            var1.setlocal(22, var11);
            var3 = null;
            var1.setline(226);
            Object var13 = var1.getlocal(8);
            if (!((PyObject)var13).__nonzero__()) {
               var13 = new PyList(Py.EmptyObjects);
            }

            var3 = ((PyObject)var13).__iter__();

            while(true) {
               var1.setline(226);
               var9 = var3.__iternext__();
               if (var9 == null) {
                  var1.setline(228);
                  var1.getlocal(0).__getattr__("execute").__call__((ThreadState)var2, var1.getglobal("write_file"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(16), var1.getlocal(22)})), (PyObject)PyString.fromInterned("writing %s")._mod(var1.getlocal(16)));
                  break;
               }

               var1.setlocal(23, var9);
               var1.setline(227);
               var1.getlocal(22).__getattr__("append").__call__(var2, PyString.fromInterned("  %s=_%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(23), var1.getlocal(23)})));
            }
         }

         var1.setline(232);
         var3 = var1.getglobal("map").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normpath"), var1.getlocal(2));
         var1.setlocal(24, var3);
         var3 = null;
         var1.setline(235);
         var11 = new PyList(new PyObject[]{var1.getlocal(14)});
         var1.setlocal(2, var11);
         var3 = null;
         var1.setline(236);
         var11 = new PyList(Py.EmptyObjects);
         var1.setlocal(25, var11);
         var3 = null;
         var1.setline(237);
         var3 = var1.getlocal(24).__iter__();

         label86:
         while(true) {
            var1.setline(237);
            var9 = var3.__iternext__();
            if (var9 == null) {
               var1.setline(245);
               var3 = var1.getlocal(6).__iter__();

               while(true) {
                  var1.setline(245);
                  var9 = var3.__iternext__();
                  if (var9 == null) {
                     var1.setline(247);
                     var1.getlocal(15).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/L."));
                     var1.setline(250);
                     var1.getlocal(15).__getattr__("extend").__call__(var2, var1.getlocal(2));
                     var1.setline(263);
                     var1.getlocal(15).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned(","), var1.getlocal(3)})));
                     var1.setline(265);
                     var1.getlocal(15).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",,"));
                     var1.setline(267);
                     var3 = var1.getlocal(5).__iter__();

                     while(true) {
                        var1.setline(267);
                        var9 = var3.__iternext__();
                        if (var9 == null) {
                           var1.setline(279);
                           var1.getlocal(15).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("import32"));
                           var1.setline(280);
                           var1.getlocal(15).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cw32mt"));
                           var1.setline(283);
                           var1.getlocal(15).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned(","), var1.getlocal(16)})));
                           var1.setline(285);
                           var1.getlocal(15).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
                           var1.setline(286);
                           var1.getlocal(15).__getattr__("extend").__call__(var2, var1.getlocal(25));
                           var1.setline(289);
                           if (var1.getlocal(10).__nonzero__()) {
                              var1.setline(290);
                              var3 = var1.getlocal(10);
                              var1.getlocal(15).__setslice__((PyObject)null, Py.newInteger(0), (PyObject)null, var3);
                              var3 = null;
                           }

                           var1.setline(291);
                           if (var1.getlocal(11).__nonzero__()) {
                              var1.setline(292);
                              var1.getlocal(15).__getattr__("extend").__call__(var2, var1.getlocal(11));
                           }

                           var1.setline(294);
                           var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(3)));

                           try {
                              var1.setline(296);
                              var1.getlocal(0).__getattr__("spawn").__call__(var2, (new PyList(new PyObject[]{var1.getlocal(0).__getattr__("linker")}))._add(var1.getlocal(15)));
                              break label86;
                           } catch (Throwable var8) {
                              PyException var12 = Py.setException(var8, var1);
                              if (var12.match(var1.getglobal("DistutilsExecError"))) {
                                 var9 = var12.value;
                                 var1.setlocal(31, var9);
                                 var4 = null;
                                 var1.setline(298);
                                 throw Py.makeException(var1.getglobal("LinkError"), var1.getlocal(31));
                              }

                              throw var12;
                           }
                        }

                        var1.setlocal(29, var9);
                        var1.setline(270);
                        var5 = var1.getlocal(0).__getattr__("find_library_file").__call__(var2, var1.getlocal(6), var1.getlocal(29), var1.getlocal(9));
                        var1.setlocal(30, var5);
                        var5 = null;
                        var1.setline(271);
                        var5 = var1.getlocal(30);
                        var10000 = var5._is(var1.getglobal("None"));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(272);
                           var1.getlocal(15).__getattr__("append").__call__(var2, var1.getlocal(29));
                        } else {
                           var1.setline(276);
                           var1.getlocal(15).__getattr__("append").__call__(var2, var1.getlocal(30));
                        }
                     }
                  }

                  var1.setlocal(28, var9);
                  var1.setline(246);
                  var1.getlocal(15).__getattr__("append").__call__(var2, PyString.fromInterned("/L%s")._mod(var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(28))));
               }
            }

            var1.setlocal(26, var9);
            var1.setline(238);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(26)));
            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(27, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(20, var7);
            var7 = null;
            var5 = null;
            var1.setline(239);
            var5 = var1.getlocal(20);
            var10000 = var5._eq(PyString.fromInterned(".res"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(240);
               var1.getlocal(25).__getattr__("append").__call__(var2, var1.getlocal(26));
            } else {
               var1.setline(242);
               var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(26));
            }
         }
      } else {
         var1.setline(301);
         var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("skipping %s (up-to-date)"), (PyObject)var1.getlocal(3));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject find_library_file$6(PyFrame var1, ThreadState var2) {
      var1.setline(318);
      PyObject var3;
      PyTuple var8;
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(319);
         var3 = var1.getlocal(2)._add(PyString.fromInterned("_d"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(320);
         var8 = new PyTuple(new PyObject[]{var1.getlocal(4)._add(PyString.fromInterned("_bcpp")), var1.getlocal(2)._add(PyString.fromInterned("_bcpp")), var1.getlocal(4), var1.getlocal(2)});
         var1.setlocal(5, var8);
         var3 = null;
      } else {
         var1.setline(322);
         var8 = new PyTuple(new PyObject[]{var1.getlocal(2)._add(PyString.fromInterned("_bcpp")), var1.getlocal(2)});
         var1.setlocal(5, var8);
         var3 = null;
      }

      var1.setline(324);
      var3 = var1.getlocal(1).__iter__();

      label24:
      while(true) {
         var1.setline(324);
         PyObject var4 = var3.__iternext__();
         PyObject var7;
         if (var4 == null) {
            var1.setline(331);
            var7 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(6, var4);
         var1.setline(325);
         PyObject var5 = var1.getlocal(5).__iter__();

         do {
            var1.setline(325);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               continue label24;
            }

            var1.setlocal(7, var6);
            var1.setline(326);
            var7 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(6), var1.getlocal(0).__getattr__("library_filename").__call__(var2, var1.getlocal(7)));
            var1.setlocal(8, var7);
            var7 = null;
            var1.setline(327);
         } while(!var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(8)).__nonzero__());

         var1.setline(328);
         var7 = var1.getlocal(8);
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject object_filenames$7(PyFrame var1, ThreadState var2) {
      var1.setline(338);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(338);
         PyString var8 = PyString.fromInterned("");
         var1.setlocal(3, var8);
         var3 = null;
      }

      var1.setline(339);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(340);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(340);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(358);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(342);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(5)));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(7, var7);
         var7 = null;
         var5 = null;
         var1.setline(343);
         var5 = var1.getlocal(7);
         var10000 = var5._notin(var1.getlocal(0).__getattr__("src_extensions")._add(new PyList(new PyObject[]{PyString.fromInterned(".rc"), PyString.fromInterned(".res")})));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(344);
            throw Py.makeException(var1.getglobal("UnknownFileError"), PyString.fromInterned("unknown file type '%s' (from '%s')")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5)})));
         }

         var1.setline(347);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(348);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var5);
            var5 = null;
         }

         var1.setline(349);
         var5 = var1.getlocal(7);
         var10000 = var5._eq(PyString.fromInterned(".res"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(351);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(7))));
         } else {
            var1.setline(352);
            var5 = var1.getlocal(7);
            var10000 = var5._eq(PyString.fromInterned(".rc"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(354);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(PyString.fromInterned(".res"))));
            } else {
               var1.setline(356);
               var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(0).__getattr__("obj_extension"))));
            }
         }
      }
   }

   public PyObject preprocess$8(PyFrame var1, ThreadState var2) {
      var1.setline(370);
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
      var1.setline(372);
      var3 = var1.getglobal("gen_preprocess_options").__call__(var2, var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(373);
      var3 = (new PyList(new PyObject[]{PyString.fromInterned("cpp32.exe")}))._add(var1.getlocal(8));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(374);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(375);
         var1.getlocal(9).__getattr__("append").__call__(var2, PyString.fromInterned("-o")._add(var1.getlocal(2)));
      }

      var1.setline(376);
      if (var1.getlocal(5).__nonzero__()) {
         var1.setline(377);
         var3 = var1.getlocal(5);
         var1.getlocal(9).__setslice__((PyObject)null, Py.newInteger(0), (PyObject)null, var3);
         var3 = null;
      }

      var1.setline(378);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(379);
         var1.getlocal(9).__getattr__("extend").__call__(var2, var1.getlocal(6));
      }

      var1.setline(380);
      var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(385);
      var10000 = var1.getlocal(0).__getattr__("force");
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(2);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("newer").__call__(var2, var1.getlocal(1), var1.getlocal(2));
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(386);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(387);
            var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(2)));
         }

         try {
            var1.setline(389);
            var1.getlocal(0).__getattr__("spawn").__call__(var2, var1.getlocal(9));
         } catch (Throwable var6) {
            PyException var8 = Py.setException(var6, var1);
            if (var8.match(var1.getglobal("DistutilsExecError"))) {
               PyObject var7 = var8.value;
               var1.setlocal(10, var7);
               var4 = null;
               var1.setline(391);
               Py.println(var1.getlocal(10));
               var1.setline(392);
               throw Py.makeException(var1.getglobal("CompileError"), var1.getlocal(10));
            }

            throw var8;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public bcppcompiler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BCPPCompiler$1 = Py.newCode(0, var2, var1, "BCPPCompiler", 25, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose", "dry_run", "force"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 53, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sources", "output_dir", "macros", "include_dirs", "debug", "extra_preargs", "extra_postargs", "depends", "objects", "pp_opts", "build", "compile_opts", "obj", "src", "ext", "msg", "input_opt", "output_opt"};
      compile$3 = Py.newCode(9, var2, var1, "compile", 81, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects", "output_libname", "output_dir", "debug", "target_lang", "output_filename", "lib_args", "msg"};
      create_static_lib$4 = Py.newCode(6, var2, var1, "create_static_lib", 146, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target_desc", "objects", "output_filename", "output_dir", "libraries", "library_dirs", "runtime_library_dirs", "export_symbols", "debug", "extra_preargs", "extra_postargs", "build_temp", "target_lang", "startup_obj", "ld_args", "def_file", "head", "tail", "modname", "ext", "temp_dir", "contents", "sym", "objects2", "resources", "file", "base", "l", "lib", "libfile", "msg"};
      link$5 = Py.newCode(14, var2, var1, "link", 171, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirs", "lib", "debug", "dlib", "try_names", "dir", "name", "libfile"};
      find_library_file$6 = Py.newCode(4, var2, var1, "find_library_file", 308, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source_filenames", "strip_dir", "output_dir", "obj_names", "src_name", "base", "ext"};
      object_filenames$7 = Py.newCode(4, var2, var1, "object_filenames", 334, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "output_file", "macros", "include_dirs", "extra_preargs", "extra_postargs", "_", "pp_opts", "pp_args", "msg"};
      preprocess$8 = Py.newCode(7, var2, var1, "preprocess", 362, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new bcppcompiler$py("distutils/bcppcompiler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(bcppcompiler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BCPPCompiler$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.compile$3(var2, var3);
         case 4:
            return this.create_static_lib$4(var2, var3);
         case 5:
            return this.link$5(var2, var3);
         case 6:
            return this.find_library_file$6(var2, var3);
         case 7:
            return this.object_filenames$7(var2, var3);
         case 8:
            return this.preprocess$8(var2, var3);
         default:
            return null;
      }
   }
}
