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
@Filename("distutils/extension.py")
public class extension$py extends PyFunctionTable implements PyRunnable {
   static extension$py self;
   static final PyCode f$0;
   static final PyCode Extension$1;
   static final PyCode __init__$2;
   static final PyCode read_setup_file$3;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.extension\n\nProvides the Extension class, used to describe C/C++ extension\nmodules in setup scripts."));
      var1.setline(4);
      PyString.fromInterned("distutils.extension\n\nProvides the Extension class, used to describe C/C++ extension\nmodules in setup scripts.");
      var1.setline(6);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(8);
      PyObject var6 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var6);
      var3 = null;
      var6 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var6);
      var3 = null;
      var6 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var6);
      var3 = null;
      var1.setline(9);
      imp.importAll("types", var1, -1);

      PyObject var4;
      try {
         var1.setline(12);
         var6 = imp.importOne("warnings", var1, -1);
         var1.setlocal("warnings", var6);
         var3 = null;
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(var1.getname("ImportError"))) {
            throw var7;
         }

         var1.setline(14);
         var4 = var1.getname("None");
         var1.setlocal("warnings", var4);
         var4 = null;
      }

      var1.setline(26);
      PyObject[] var8 = Py.EmptyObjects;
      var4 = Py.makeClass("Extension", var8, Extension$1);
      var1.setlocal("Extension", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(139);
      var8 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var8, read_setup_file$3, (PyObject)null);
      var1.setlocal("read_setup_file", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Extension$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Just a collection of attributes that describes an extension\n    module and everything needed to build it (hopefully in a portable\n    way, but there are hooks that let you be as unportable as you need).\n\n    Instance attributes:\n      name : string\n        the full name of the extension, including any packages -- ie.\n        *not* a filename or pathname, but Python dotted name\n      sources : [string]\n        list of source filenames, relative to the distribution root\n        (where the setup script lives), in Unix form (slash-separated)\n        for portability.  Source files may be C, C++, SWIG (.i),\n        platform-specific resource files, or whatever else is recognized\n        by the \"build_ext\" command as source for a Python extension.\n      include_dirs : [string]\n        list of directories to search for C/C++ header files (in Unix\n        form for portability)\n      define_macros : [(name : string, value : string|None)]\n        list of macros to define; each macro is defined using a 2-tuple,\n        where 'value' is either the string to define it to or None to\n        define it without a particular value (equivalent of \"#define\n        FOO\" in source or -DFOO on Unix C compiler command line)\n      undef_macros : [string]\n        list of macros to undefine explicitly\n      library_dirs : [string]\n        list of directories to search for C/C++ libraries at link time\n      libraries : [string]\n        list of library names (not filenames or paths) to link against\n      runtime_library_dirs : [string]\n        list of directories to search for C/C++ libraries at run time\n        (for shared extensions, this is when the extension is loaded)\n      extra_objects : [string]\n        list of extra files to link with (eg. object files not implied\n        by 'sources', static library that must be explicitly specified,\n        binary resource files, etc.)\n      extra_compile_args : [string]\n        any extra platform- and compiler-specific information to use\n        when compiling the source files in 'sources'.  For platforms and\n        compilers where \"command line\" makes sense, this is typically a\n        list of command-line arguments, but for other platforms it could\n        be anything.\n      extra_link_args : [string]\n        any extra platform- and compiler-specific information to use\n        when linking object files together to create the extension (or\n        to create a new static Python interpreter).  Similar\n        interpretation as for 'extra_compile_args'.\n      export_symbols : [string]\n        list of symbols to be exported from a shared extension.  Not\n        used on all platforms, and not generally necessary for Python\n        extensions, which typically export exactly one symbol: \"init\" +\n        extension_name.\n      swig_opts : [string]\n        any extra options to pass to SWIG if a source file has the .i\n        extension.\n      depends : [string]\n        list of files that the extension depends on\n      language : string\n        extension language (i.e. \"c\", \"c++\", \"objc\"). Will be detected\n        from the source extensions if not provided.\n    "));
      var1.setline(86);
      PyString.fromInterned("Just a collection of attributes that describes an extension\n    module and everything needed to build it (hopefully in a portable\n    way, but there are hooks that let you be as unportable as you need).\n\n    Instance attributes:\n      name : string\n        the full name of the extension, including any packages -- ie.\n        *not* a filename or pathname, but Python dotted name\n      sources : [string]\n        list of source filenames, relative to the distribution root\n        (where the setup script lives), in Unix form (slash-separated)\n        for portability.  Source files may be C, C++, SWIG (.i),\n        platform-specific resource files, or whatever else is recognized\n        by the \"build_ext\" command as source for a Python extension.\n      include_dirs : [string]\n        list of directories to search for C/C++ header files (in Unix\n        form for portability)\n      define_macros : [(name : string, value : string|None)]\n        list of macros to define; each macro is defined using a 2-tuple,\n        where 'value' is either the string to define it to or None to\n        define it without a particular value (equivalent of \"#define\n        FOO\" in source or -DFOO on Unix C compiler command line)\n      undef_macros : [string]\n        list of macros to undefine explicitly\n      library_dirs : [string]\n        list of directories to search for C/C++ libraries at link time\n      libraries : [string]\n        list of library names (not filenames or paths) to link against\n      runtime_library_dirs : [string]\n        list of directories to search for C/C++ libraries at run time\n        (for shared extensions, this is when the extension is loaded)\n      extra_objects : [string]\n        list of extra files to link with (eg. object files not implied\n        by 'sources', static library that must be explicitly specified,\n        binary resource files, etc.)\n      extra_compile_args : [string]\n        any extra platform- and compiler-specific information to use\n        when compiling the source files in 'sources'.  For platforms and\n        compilers where \"command line\" makes sense, this is typically a\n        list of command-line arguments, but for other platforms it could\n        be anything.\n      extra_link_args : [string]\n        any extra platform- and compiler-specific information to use\n        when linking object files together to create the extension (or\n        to create a new static Python interpreter).  Similar\n        interpretation as for 'extra_compile_args'.\n      export_symbols : [string]\n        list of symbols to be exported from a shared extension.  Not\n        used on all platforms, and not generally necessary for Python\n        extensions, which typically export exactly one symbol: \"init\" +\n        extension_name.\n      swig_opts : [string]\n        any extra options to pass to SWIG if a source file has the .i\n        extension.\n      depends : [string]\n        list of files that the extension depends on\n      language : string\n        extension language (i.e. \"c\", \"c++\", \"objc\"). Will be detected\n        from the source extensions if not provided.\n    ");
      var1.setline(90);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(106);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         var10000 = var3._is(var1.getglobal("StringType"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("'name' must be a string"));
         }
      }

      var1.setline(107);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
         var10000 = var3._is(var1.getglobal("ListType"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("map").__call__(var2, var1.getglobal("type"), var1.getlocal(2));
            var10000 = var3._eq((new PyList(new PyObject[]{var1.getglobal("StringType")}))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(2))));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("'sources' must be a list of strings"));
         }
      }

      var1.setline(111);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.setline(112);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("sources", var3);
      var3 = null;
      var1.setline(113);
      Object var5 = var1.getlocal(3);
      if (!((PyObject)var5).__nonzero__()) {
         var5 = new PyList(Py.EmptyObjects);
      }

      Object var4 = var5;
      var1.getlocal(0).__setattr__((String)"include_dirs", (PyObject)var4);
      var3 = null;
      var1.setline(114);
      var5 = var1.getlocal(4);
      if (!((PyObject)var5).__nonzero__()) {
         var5 = new PyList(Py.EmptyObjects);
      }

      var4 = var5;
      var1.getlocal(0).__setattr__((String)"define_macros", (PyObject)var4);
      var3 = null;
      var1.setline(115);
      var5 = var1.getlocal(5);
      if (!((PyObject)var5).__nonzero__()) {
         var5 = new PyList(Py.EmptyObjects);
      }

      var4 = var5;
      var1.getlocal(0).__setattr__((String)"undef_macros", (PyObject)var4);
      var3 = null;
      var1.setline(116);
      var5 = var1.getlocal(6);
      if (!((PyObject)var5).__nonzero__()) {
         var5 = new PyList(Py.EmptyObjects);
      }

      var4 = var5;
      var1.getlocal(0).__setattr__((String)"library_dirs", (PyObject)var4);
      var3 = null;
      var1.setline(117);
      var5 = var1.getlocal(7);
      if (!((PyObject)var5).__nonzero__()) {
         var5 = new PyList(Py.EmptyObjects);
      }

      var4 = var5;
      var1.getlocal(0).__setattr__((String)"libraries", (PyObject)var4);
      var3 = null;
      var1.setline(118);
      var5 = var1.getlocal(8);
      if (!((PyObject)var5).__nonzero__()) {
         var5 = new PyList(Py.EmptyObjects);
      }

      var4 = var5;
      var1.getlocal(0).__setattr__((String)"runtime_library_dirs", (PyObject)var4);
      var3 = null;
      var1.setline(119);
      var5 = var1.getlocal(9);
      if (!((PyObject)var5).__nonzero__()) {
         var5 = new PyList(Py.EmptyObjects);
      }

      var4 = var5;
      var1.getlocal(0).__setattr__((String)"extra_objects", (PyObject)var4);
      var3 = null;
      var1.setline(120);
      var5 = var1.getlocal(10);
      if (!((PyObject)var5).__nonzero__()) {
         var5 = new PyList(Py.EmptyObjects);
      }

      var4 = var5;
      var1.getlocal(0).__setattr__((String)"extra_compile_args", (PyObject)var4);
      var3 = null;
      var1.setline(121);
      var5 = var1.getlocal(11);
      if (!((PyObject)var5).__nonzero__()) {
         var5 = new PyList(Py.EmptyObjects);
      }

      var4 = var5;
      var1.getlocal(0).__setattr__((String)"extra_link_args", (PyObject)var4);
      var3 = null;
      var1.setline(122);
      var5 = var1.getlocal(12);
      if (!((PyObject)var5).__nonzero__()) {
         var5 = new PyList(Py.EmptyObjects);
      }

      var4 = var5;
      var1.getlocal(0).__setattr__((String)"export_symbols", (PyObject)var4);
      var3 = null;
      var1.setline(123);
      var5 = var1.getlocal(13);
      if (!((PyObject)var5).__nonzero__()) {
         var5 = new PyList(Py.EmptyObjects);
      }

      var4 = var5;
      var1.getlocal(0).__setattr__((String)"swig_opts", (PyObject)var4);
      var3 = null;
      var1.setline(124);
      var5 = var1.getlocal(14);
      if (!((PyObject)var5).__nonzero__()) {
         var5 = new PyList(Py.EmptyObjects);
      }

      var4 = var5;
      var1.getlocal(0).__setattr__((String)"depends", (PyObject)var4);
      var3 = null;
      var1.setline(125);
      var3 = var1.getlocal(15);
      var1.getlocal(0).__setattr__("language", var3);
      var3 = null;
      var1.setline(128);
      if (var1.getglobal("len").__call__(var2, var1.getlocal(16)).__nonzero__()) {
         var1.setline(129);
         var3 = var1.getlocal(16).__getattr__("keys").__call__(var2);
         var1.setlocal(17, var3);
         var3 = null;
         var1.setline(129);
         var1.getlocal(17).__getattr__("sort").__call__(var2);
         var1.setline(130);
         var3 = var1.getglobal("map").__call__(var2, var1.getglobal("repr"), var1.getlocal(17));
         var1.setlocal(17, var3);
         var3 = null;
         var1.setline(131);
         var3 = PyString.fromInterned("Unknown Extension options: ")._add(var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(17), (PyObject)PyString.fromInterned(", ")));
         var1.setlocal(18, var3);
         var3 = null;
         var1.setline(132);
         var3 = var1.getglobal("warnings");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(133);
            var1.getglobal("warnings").__getattr__("warn").__call__(var2, var1.getlocal(18));
         } else {
            var1.setline(135);
            var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, var1.getlocal(18)._add(PyString.fromInterned("\n")));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject read_setup_file$3(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      String[] var3 = new String[]{"parse_makefile", "expand_makefile_vars", "_variable_rx"};
      PyObject[] var8 = imp.importFrom("distutils.sysconfig", var3, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal(1, var4);
      var4 = null;
      var4 = var8[1];
      var1.setlocal(2, var4);
      var4 = null;
      var4 = var8[2];
      var1.setlocal(3, var4);
      var4 = null;
      var1.setline(142);
      var3 = new String[]{"TextFile"};
      var8 = imp.importFrom("distutils.text_file", var3, var1, -1);
      var4 = var8[0];
      var1.setlocal(4, var4);
      var4 = null;
      var1.setline(143);
      var3 = new String[]{"split_quoted"};
      var8 = imp.importFrom("distutils.util", var3, var1, -1);
      var4 = var8[0];
      var1.setlocal(5, var4);
      var4 = null;
      var1.setline(146);
      PyObject var9 = var1.getlocal(1).__call__(var2, var1.getlocal(0));
      var1.setlocal(6, var9);
      var3 = null;
      var1.setline(150);
      PyObject var10000 = var1.getlocal(4);
      var8 = new PyObject[]{var1.getlocal(0), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1)};
      String[] var10 = new String[]{"strip_comments", "skip_blanks", "join_lines", "lstrip_ws", "rstrip_ws"};
      var10000 = var10000.__call__(var2, var8, var10);
      var3 = null;
      var9 = var10000;
      var1.setlocal(7, var9);
      var3 = null;
      var3 = null;

      try {
         var1.setline(154);
         PyList var11 = new PyList(Py.EmptyObjects);
         var1.setlocal(8, var11);
         var4 = null;

         while(true) {
            var1.setline(156);
            if (!Py.newInteger(1).__nonzero__()) {
               break;
            }

            var1.setline(157);
            var4 = var1.getlocal(7).__getattr__("readline").__call__(var2);
            var1.setlocal(9, var4);
            var4 = null;
            var1.setline(158);
            var4 = var1.getlocal(9);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               break;
            }

            var1.setline(160);
            if (!var1.getlocal(3).__getattr__("match").__call__(var2, var1.getlocal(9)).__nonzero__()) {
               var1.setline(168);
               var4 = var1.getlocal(2).__call__(var2, var1.getlocal(9), var1.getlocal(6));
               var1.setlocal(9, var4);
               var4 = null;
               var1.setline(169);
               var4 = var1.getlocal(5).__call__(var2, var1.getlocal(9));
               var1.setlocal(10, var4);
               var4 = null;
               var1.setline(178);
               var4 = var1.getlocal(10).__getitem__(Py.newInteger(0));
               var1.setlocal(11, var4);
               var4 = null;
               var1.setline(179);
               var4 = var1.getglobal("Extension").__call__((ThreadState)var2, (PyObject)var1.getlocal(11), (PyObject)(new PyList(Py.EmptyObjects)));
               var1.setlocal(12, var4);
               var4 = null;
               var1.setline(180);
               var4 = var1.getglobal("None");
               var1.setlocal(13, var4);
               var4 = null;
               var1.setline(182);
               var4 = var1.getlocal(10).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null).__iter__();

               while(true) {
                  var1.setline(182);
                  PyObject var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(240);
                     var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(12));
                     break;
                  }

                  var1.setlocal(14, var5);
                  var1.setline(183);
                  PyObject var6 = var1.getlocal(13);
                  var10000 = var6._isnot(var1.getglobal("None"));
                  var6 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(184);
                     var1.getlocal(13).__getattr__("append").__call__(var2, var1.getlocal(14));
                     var1.setline(185);
                     var6 = var1.getglobal("None");
                     var1.setlocal(13, var6);
                     var6 = null;
                  } else {
                     var1.setline(188);
                     var6 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(14)).__getitem__(Py.newInteger(1));
                     var1.setlocal(15, var6);
                     var6 = null;
                     var1.setline(189);
                     var6 = var1.getlocal(14).__getslice__(Py.newInteger(0), Py.newInteger(2), (PyObject)null);
                     var1.setlocal(16, var6);
                     var6 = null;
                     var1.setline(189);
                     var6 = var1.getlocal(14).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null);
                     var1.setlocal(17, var6);
                     var6 = null;
                     var1.setline(191);
                     var6 = var1.getlocal(15);
                     var10000 = var6._in(new PyTuple(new PyObject[]{PyString.fromInterned(".c"), PyString.fromInterned(".cc"), PyString.fromInterned(".cpp"), PyString.fromInterned(".cxx"), PyString.fromInterned(".c++"), PyString.fromInterned(".m"), PyString.fromInterned(".mm")}));
                     var6 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(195);
                        var1.getlocal(12).__getattr__("sources").__getattr__("append").__call__(var2, var1.getlocal(14));
                     } else {
                        var1.setline(196);
                        var6 = var1.getlocal(16);
                        var10000 = var6._eq(PyString.fromInterned("-I"));
                        var6 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(197);
                           var1.getlocal(12).__getattr__("include_dirs").__getattr__("append").__call__(var2, var1.getlocal(17));
                        } else {
                           var1.setline(198);
                           var6 = var1.getlocal(16);
                           var10000 = var6._eq(PyString.fromInterned("-D"));
                           var6 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(199);
                              var6 = var1.getglobal("string").__getattr__("find").__call__((ThreadState)var2, (PyObject)var1.getlocal(17), (PyObject)PyString.fromInterned("="));
                              var1.setlocal(18, var6);
                              var6 = null;
                              var1.setline(200);
                              var6 = var1.getlocal(18);
                              var10000 = var6._eq(Py.newInteger(-1));
                              var6 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(201);
                                 var1.getlocal(12).__getattr__("define_macros").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(17), var1.getglobal("None")})));
                              } else {
                                 var1.setline(203);
                                 var1.getlocal(12).__getattr__("define_macros").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(17).__getslice__(Py.newInteger(0), var1.getlocal(18), (PyObject)null), var1.getlocal(17).__getslice__(var1.getlocal(18)._add(Py.newInteger(2)), (PyObject)null, (PyObject)null)})));
                              }
                           } else {
                              var1.setline(205);
                              var6 = var1.getlocal(16);
                              var10000 = var6._eq(PyString.fromInterned("-U"));
                              var6 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(206);
                                 var1.getlocal(12).__getattr__("undef_macros").__getattr__("append").__call__(var2, var1.getlocal(17));
                              } else {
                                 var1.setline(207);
                                 var6 = var1.getlocal(16);
                                 var10000 = var6._eq(PyString.fromInterned("-C"));
                                 var6 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(208);
                                    var1.getlocal(12).__getattr__("extra_compile_args").__getattr__("append").__call__(var2, var1.getlocal(14));
                                 } else {
                                    var1.setline(209);
                                    var6 = var1.getlocal(16);
                                    var10000 = var6._eq(PyString.fromInterned("-l"));
                                    var6 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(210);
                                       var1.getlocal(12).__getattr__("libraries").__getattr__("append").__call__(var2, var1.getlocal(17));
                                    } else {
                                       var1.setline(211);
                                       var6 = var1.getlocal(16);
                                       var10000 = var6._eq(PyString.fromInterned("-L"));
                                       var6 = null;
                                       if (var10000.__nonzero__()) {
                                          var1.setline(212);
                                          var1.getlocal(12).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getlocal(17));
                                       } else {
                                          var1.setline(213);
                                          var6 = var1.getlocal(16);
                                          var10000 = var6._eq(PyString.fromInterned("-R"));
                                          var6 = null;
                                          if (var10000.__nonzero__()) {
                                             var1.setline(214);
                                             var1.getlocal(12).__getattr__("runtime_library_dirs").__getattr__("append").__call__(var2, var1.getlocal(17));
                                          } else {
                                             var1.setline(215);
                                             var6 = var1.getlocal(14);
                                             var10000 = var6._eq(PyString.fromInterned("-rpath"));
                                             var6 = null;
                                             if (var10000.__nonzero__()) {
                                                var1.setline(216);
                                                var6 = var1.getlocal(12).__getattr__("runtime_library_dirs");
                                                var1.setlocal(13, var6);
                                                var6 = null;
                                             } else {
                                                var1.setline(217);
                                                var6 = var1.getlocal(14);
                                                var10000 = var6._eq(PyString.fromInterned("-Xlinker"));
                                                var6 = null;
                                                if (var10000.__nonzero__()) {
                                                   var1.setline(218);
                                                   var6 = var1.getlocal(12).__getattr__("extra_link_args");
                                                   var1.setlocal(13, var6);
                                                   var6 = null;
                                                } else {
                                                   var1.setline(219);
                                                   var6 = var1.getlocal(14);
                                                   var10000 = var6._eq(PyString.fromInterned("-Xcompiler"));
                                                   var6 = null;
                                                   if (var10000.__nonzero__()) {
                                                      var1.setline(220);
                                                      var6 = var1.getlocal(12).__getattr__("extra_compile_args");
                                                      var1.setlocal(13, var6);
                                                      var6 = null;
                                                   } else {
                                                      var1.setline(221);
                                                      var6 = var1.getlocal(16);
                                                      var10000 = var6._eq(PyString.fromInterned("-u"));
                                                      var6 = null;
                                                      if (var10000.__nonzero__()) {
                                                         var1.setline(222);
                                                         var1.getlocal(12).__getattr__("extra_link_args").__getattr__("append").__call__(var2, var1.getlocal(14));
                                                         var1.setline(223);
                                                         if (var1.getlocal(17).__not__().__nonzero__()) {
                                                            var1.setline(224);
                                                            var6 = var1.getlocal(12).__getattr__("extra_link_args");
                                                            var1.setlocal(13, var6);
                                                            var6 = null;
                                                         }
                                                      } else {
                                                         var1.setline(225);
                                                         var6 = var1.getlocal(14);
                                                         var10000 = var6._eq(PyString.fromInterned("-Xcompiler"));
                                                         var6 = null;
                                                         if (var10000.__nonzero__()) {
                                                            var1.setline(226);
                                                            var6 = var1.getlocal(12).__getattr__("extra_compile_args");
                                                            var1.setlocal(13, var6);
                                                            var6 = null;
                                                         } else {
                                                            var1.setline(227);
                                                            var6 = var1.getlocal(16);
                                                            var10000 = var6._eq(PyString.fromInterned("-u"));
                                                            var6 = null;
                                                            if (var10000.__nonzero__()) {
                                                               var1.setline(228);
                                                               var1.getlocal(12).__getattr__("extra_link_args").__getattr__("append").__call__(var2, var1.getlocal(14));
                                                               var1.setline(229);
                                                               if (var1.getlocal(17).__not__().__nonzero__()) {
                                                                  var1.setline(230);
                                                                  var6 = var1.getlocal(12).__getattr__("extra_link_args");
                                                                  var1.setlocal(13, var6);
                                                                  var6 = null;
                                                               }
                                                            } else {
                                                               var1.setline(231);
                                                               var6 = var1.getlocal(15);
                                                               var10000 = var6._in(new PyTuple(new PyObject[]{PyString.fromInterned(".a"), PyString.fromInterned(".so"), PyString.fromInterned(".sl"), PyString.fromInterned(".o"), PyString.fromInterned(".dylib")}));
                                                               var6 = null;
                                                               if (var10000.__nonzero__()) {
                                                                  var1.setline(236);
                                                                  var1.getlocal(12).__getattr__("extra_objects").__getattr__("append").__call__(var2, var1.getlocal(14));
                                                               } else {
                                                                  var1.setline(238);
                                                                  var1.getlocal(7).__getattr__("warn").__call__(var2, PyString.fromInterned("unrecognized argument '%s'")._mod(var1.getlocal(14)));
                                                               }
                                                            }
                                                         }
                                                      }
                                                   }
                                                }
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(242);
         var1.getlocal(7).__getattr__("close").__call__(var2);
         throw (Throwable)var7;
      }

      var1.setline(242);
      var1.getlocal(7).__getattr__("close").__call__(var2);
      var1.setline(253);
      var9 = var1.getlocal(8);
      var1.f_lasti = -1;
      return var9;
   }

   public extension$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      Extension$1 = Py.newCode(0, var2, var1, "Extension", 26, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "sources", "include_dirs", "define_macros", "undef_macros", "library_dirs", "libraries", "runtime_library_dirs", "extra_objects", "extra_compile_args", "extra_link_args", "export_symbols", "swig_opts", "depends", "language", "kw", "L", "msg"};
      __init__$2 = Py.newCode(17, var2, var1, "__init__", 90, false, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "parse_makefile", "expand_makefile_vars", "_variable_rx", "TextFile", "split_quoted", "vars", "file", "extensions", "line", "words", "module", "ext", "append_next_word", "word", "suffix", "switch", "value", "equals"};
      read_setup_file$3 = Py.newCode(1, var2, var1, "read_setup_file", 139, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new extension$py("distutils/extension$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(extension$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.Extension$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.read_setup_file$3(var2, var3);
         default:
            return null;
      }
   }
}
