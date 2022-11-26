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
@Filename("distutils/command/build_ext.py")
public class build_ext$py extends PyFunctionTable implements PyRunnable {
   static build_ext$py self;
   static final PyCode f$0;
   static final PyCode show_compilers$1;
   static final PyCode build_ext$2;
   static final PyCode initialize_options$3;
   static final PyCode finalize_options$4;
   static final PyCode f$5;
   static final PyCode run$6;
   static final PyCode check_extensions_list$7;
   static final PyCode get_source_files$8;
   static final PyCode get_outputs$9;
   static final PyCode build_extensions$10;
   static final PyCode build_extension$11;
   static final PyCode swig_sources$12;
   static final PyCode find_swig$13;
   static final PyCode get_ext_fullpath$14;
   static final PyCode get_ext_fullname$15;
   static final PyCode get_ext_filename$16;
   static final PyCode get_export_symbols$17;
   static final PyCode get_libraries$18;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.build_ext\n\nImplements the Distutils 'build_ext' command, for building extension\nmodules (currently limited to C extensions, should accommodate C++\nextensions ASAP)."));
      var1.setline(5);
      PyString.fromInterned("distutils.command.build_ext\n\nImplements the Distutils 'build_ext' command, for building extension\nmodules (currently limited to C extensions, should accommodate C++\nextensions ASAP).");
      var1.setline(9);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(11);
      PyObject var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var5 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var5);
      var3 = null;
      var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(12);
      imp.importAll("types", var1, -1);
      var1.setline(13);
      String[] var6 = new String[]{"USER_BASE", "USER_SITE"};
      PyObject[] var7 = imp.importFrom("site", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("USER_BASE", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("USER_SITE", var4);
      var4 = null;
      var1.setline(14);
      var6 = new String[]{"Command"};
      var7 = imp.importFrom("distutils.core", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(15);
      imp.importAll("distutils.errors", var1, -1);
      var1.setline(16);
      var6 = new String[]{"customize_compiler", "get_python_version"};
      var7 = imp.importFrom("distutils.sysconfig", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("customize_compiler", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("get_python_version", var4);
      var4 = null;
      var1.setline(17);
      var6 = new String[]{"newer_group"};
      var7 = imp.importFrom("distutils.dep_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("newer_group", var4);
      var4 = null;
      var1.setline(18);
      var6 = new String[]{"Extension"};
      var7 = imp.importFrom("distutils.extension", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Extension", var4);
      var4 = null;
      var1.setline(19);
      var6 = new String[]{"get_platform"};
      var7 = imp.importFrom("distutils.util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("get_platform", var4);
      var4 = null;
      var1.setline(20);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(22);
      var5 = var1.getname("os").__getattr__("name");
      PyObject var10000 = var5._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(23);
         var6 = new String[]{"get_build_version"};
         var7 = imp.importFrom("distutils.msvccompiler", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal("get_build_version", var4);
         var4 = null;
         var1.setline(24);
         var5 = var1.getname("int").__call__(var2, var1.getname("get_build_version").__call__(var2));
         var1.setlocal("MSVC_VERSION", var5);
         var3 = null;
      }

      var1.setline(28);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("^[a-zA-Z_][a-zA-Z_0-9]*(\\.[a-zA-Z_][a-zA-Z_0-9]*)*$"));
      var1.setlocal("extension_name_re", var5);
      var3 = null;
      var1.setline(32);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, show_compilers$1, (PyObject)null);
      var1.setlocal("show_compilers", var8);
      var3 = null;
      var1.setline(37);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("build_ext", var7, build_ext$2);
      var1.setlocal("build_ext", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject show_compilers$1(PyFrame var1, ThreadState var2) {
      var1.setline(33);
      String[] var3 = new String[]{"show_compilers"};
      PyObject[] var5 = imp.importFrom("distutils.ccompiler", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(34);
      var1.getlocal(0).__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject build_ext$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(39);
      PyString var3 = PyString.fromInterned("build C/C++ extensions (compile/link to build directory)");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(59);
      PyObject var4 = PyString.fromInterned(" (separated by '%s')")._mod(var1.getname("os").__getattr__("pathsep"));
      var1.setlocal("sep_by", var4);
      var3 = null;
      var1.setline(60);
      PyList var5 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("build-lib="), PyString.fromInterned("b"), PyString.fromInterned("directory for compiled extension modules")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-temp="), PyString.fromInterned("t"), PyString.fromInterned("directory for temporary files (build by-products)")}), new PyTuple(new PyObject[]{PyString.fromInterned("plat-name="), PyString.fromInterned("p"), PyString.fromInterned("platform name to cross-compile for, if supported (default: %s)")._mod(var1.getname("get_platform").__call__(var2))}), new PyTuple(new PyObject[]{PyString.fromInterned("inplace"), PyString.fromInterned("i"), PyString.fromInterned("ignore build-lib and put compiled extensions into the source ")._add(PyString.fromInterned("directory alongside your pure Python modules"))}), new PyTuple(new PyObject[]{PyString.fromInterned("include-dirs="), PyString.fromInterned("I"), PyString.fromInterned("list of directories to search for header files")._add(var1.getname("sep_by"))}), new PyTuple(new PyObject[]{PyString.fromInterned("define="), PyString.fromInterned("D"), PyString.fromInterned("C preprocessor macros to define")}), new PyTuple(new PyObject[]{PyString.fromInterned("undef="), PyString.fromInterned("U"), PyString.fromInterned("C preprocessor macros to undefine")}), new PyTuple(new PyObject[]{PyString.fromInterned("libraries="), PyString.fromInterned("l"), PyString.fromInterned("external C libraries to link with")}), new PyTuple(new PyObject[]{PyString.fromInterned("library-dirs="), PyString.fromInterned("L"), PyString.fromInterned("directories to search for external C libraries")._add(var1.getname("sep_by"))}), new PyTuple(new PyObject[]{PyString.fromInterned("rpath="), PyString.fromInterned("R"), PyString.fromInterned("directories to search for shared C libraries at runtime")}), new PyTuple(new PyObject[]{PyString.fromInterned("link-objects="), PyString.fromInterned("O"), PyString.fromInterned("extra explicit link objects to include in the link")}), new PyTuple(new PyObject[]{PyString.fromInterned("debug"), PyString.fromInterned("g"), PyString.fromInterned("compile/link with debugging information")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("f"), PyString.fromInterned("forcibly build everything (ignore file timestamps)")}), new PyTuple(new PyObject[]{PyString.fromInterned("compiler="), PyString.fromInterned("c"), PyString.fromInterned("specify the compiler type")}), new PyTuple(new PyObject[]{PyString.fromInterned("swig-cpp"), var1.getname("None"), PyString.fromInterned("make SWIG create C++ files (default is C)")}), new PyTuple(new PyObject[]{PyString.fromInterned("swig-opts="), var1.getname("None"), PyString.fromInterned("list of SWIG command line options")}), new PyTuple(new PyObject[]{PyString.fromInterned("swig="), var1.getname("None"), PyString.fromInterned("path to the SWIG executable")}), new PyTuple(new PyObject[]{PyString.fromInterned("user"), var1.getname("None"), PyString.fromInterned("add user include, library and rpath")})});
      var1.setlocal("user_options", var5);
      var3 = null;
      var1.setline(101);
      var5 = new PyList(new PyObject[]{PyString.fromInterned("inplace"), PyString.fromInterned("debug"), PyString.fromInterned("force"), PyString.fromInterned("swig-cpp"), PyString.fromInterned("user")});
      var1.setlocal("boolean_options", var5);
      var3 = null;
      var1.setline(103);
      var5 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("help-compiler"), var1.getname("None"), PyString.fromInterned("list available compilers"), var1.getname("show_compilers")})});
      var1.setlocal("help_options", var5);
      var3 = null;
      var1.setline(108);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, initialize_options$3, (PyObject)null);
      var1.setlocal("initialize_options", var7);
      var3 = null;
      var1.setline(131);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, finalize_options$4, (PyObject)null);
      var1.setlocal("finalize_options", var7);
      var3 = null;
      var1.setline(275);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, run$6, (PyObject)null);
      var1.setlocal("run", var7);
      var3 = null;
      var1.setline(339);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, check_extensions_list$7, PyString.fromInterned("Ensure that the list of extensions (presumably provided as a\n        command option 'extensions') is valid, i.e. it is a list of\n        Extension objects.  We also support the old-style list of 2-tuples,\n        where the tuples are (ext_name, build_info), which are converted to\n        Extension instances here.\n\n        Raise DistutilsSetupError if the structure is invalid anywhere;\n        just returns otherwise.\n        "));
      var1.setlocal("check_extensions_list", var7);
      var3 = null;
      var1.setline(417);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_source_files$8, (PyObject)null);
      var1.setlocal("get_source_files", var7);
      var3 = null;
      var1.setline(427);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_outputs$9, (PyObject)null);
      var1.setlocal("get_outputs", var7);
      var3 = null;
      var1.setline(441);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, build_extensions$10, (PyObject)null);
      var1.setlocal("build_extensions", var7);
      var3 = null;
      var1.setline(448);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, build_extension$11, (PyObject)null);
      var1.setlocal("build_extension", var7);
      var3 = null;
      var1.setline(531);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, swig_sources$12, PyString.fromInterned("Walk the list of source files in 'sources', looking for SWIG\n        interface (.i) files.  Run SWIG on all that are found, and\n        return a modified 'sources' list with SWIG source files replaced\n        by the generated C (or C++) files.\n        "));
      var1.setlocal("swig_sources", var7);
      var3 = null;
      var1.setline(589);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, find_swig$13, PyString.fromInterned("Return the name of the SWIG executable.  On Unix, this is\n        just \"swig\" -- it should be in the PATH.  Tries a bit harder on\n        Windows.\n        "));
      var1.setlocal("find_swig", var7);
      var3 = null;
      var1.setline(622);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_ext_fullpath$14, PyString.fromInterned("Returns the path of the filename for a given extension.\n\n        The file is located in `build_lib` or directly in the package\n        (inplace option).\n        "));
      var1.setlocal("get_ext_fullpath", var7);
      var3 = null;
      var1.setline(654);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_ext_fullname$15, PyString.fromInterned("Returns the fullname of a given extension name.\n\n        Adds the `package.` prefix"));
      var1.setlocal("get_ext_fullname", var7);
      var3 = null;
      var1.setline(663);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_ext_filename$16, PyString.fromInterned("Convert the name of an extension (eg. \"foo.bar\") into the name\n        of the file from which it will be loaded (eg. \"foo/bar.so\", or\n        \"foo\\bar.pyd\").\n        "));
      var1.setlocal("get_ext_filename", var7);
      var3 = null;
      var1.setline(679);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_export_symbols$17, PyString.fromInterned("Return the list of symbols that a shared extension has to\n        export.  This either uses 'ext.export_symbols' or, if it's not\n        provided, \"init\" + module_name.  Only relevant on Windows, where\n        the .pyd file (DLL) must export the module \"init\" function.\n        "));
      var1.setlocal("get_export_symbols", var7);
      var3 = null;
      var1.setline(690);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_libraries$18, PyString.fromInterned("Return the list of libraries to link against when building a\n        shared extension.  On most platforms, this is just 'ext.libraries';\n        on Windows and OS/2, we add the Python library (eg. python20.dll).\n        "));
      var1.setlocal("get_libraries", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("extensions", var3);
      var3 = null;
      var1.setline(110);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_lib", var3);
      var3 = null;
      var1.setline(111);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("plat_name", var3);
      var3 = null;
      var1.setline(112);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_temp", var3);
      var3 = null;
      var1.setline(113);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"inplace", var4);
      var3 = null;
      var1.setline(114);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("package", var3);
      var3 = null;
      var1.setline(116);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("include_dirs", var3);
      var3 = null;
      var1.setline(117);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("define", var3);
      var3 = null;
      var1.setline(118);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("undef", var3);
      var3 = null;
      var1.setline(119);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("libraries", var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("library_dirs", var3);
      var3 = null;
      var1.setline(121);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("rpath", var3);
      var3 = null;
      var1.setline(122);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("link_objects", var3);
      var3 = null;
      var1.setline(123);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("debug", var3);
      var3 = null;
      var1.setline(124);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("force", var3);
      var3 = null;
      var1.setline(125);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("compiler", var3);
      var3 = null;
      var1.setline(126);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("swig", var3);
      var3 = null;
      var1.setline(127);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("swig_cpp", var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("swig_opts", var3);
      var3 = null;
      var1.setline(129);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("user", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$4(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      String[] var3 = new String[]{"sysconfig"};
      PyObject[] var5 = imp.importFrom("distutils", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(134);
      PyObject var10000 = var1.getlocal(0).__getattr__("set_undefined_options");
      var5 = new PyObject[]{PyString.fromInterned("build"), new PyTuple(new PyObject[]{PyString.fromInterned("build_lib"), PyString.fromInterned("build_lib")}), new PyTuple(new PyObject[]{PyString.fromInterned("build_temp"), PyString.fromInterned("build_temp")}), new PyTuple(new PyObject[]{PyString.fromInterned("compiler"), PyString.fromInterned("compiler")}), new PyTuple(new PyObject[]{PyString.fromInterned("debug"), PyString.fromInterned("debug")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("force")}), new PyTuple(new PyObject[]{PyString.fromInterned("plat_name"), PyString.fromInterned("plat_name")})};
      var10000.__call__(var2, var5);
      var1.setline(143);
      PyObject var6 = var1.getlocal(0).__getattr__("package");
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(144);
         var6 = var1.getlocal(0).__getattr__("distribution").__getattr__("ext_package");
         var1.getlocal(0).__setattr__("package", var6);
         var3 = null;
      }

      var1.setline(146);
      var6 = var1.getlocal(0).__getattr__("distribution").__getattr__("ext_modules");
      var1.getlocal(0).__setattr__("extensions", var6);
      var3 = null;
      var1.setline(150);
      var6 = var1.getlocal(1).__getattr__("get_python_inc").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(151);
      var10000 = var1.getlocal(1).__getattr__("get_python_inc");
      var5 = new PyObject[]{Py.newInteger(1)};
      String[] var7 = new String[]{"plat_specific"};
      var10000 = var10000.__call__(var2, var5, var7);
      var3 = null;
      var6 = var10000;
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(152);
      var6 = var1.getlocal(0).__getattr__("include_dirs");
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(153);
         Object var11 = var1.getlocal(0).__getattr__("distribution").__getattr__("include_dirs");
         if (!((PyObject)var11).__nonzero__()) {
            var11 = new PyList(Py.EmptyObjects);
         }

         Object var8 = var11;
         var1.getlocal(0).__setattr__((String)"include_dirs", (PyObject)var8);
         var3 = null;
      }

      var1.setline(154);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("include_dirs"), var1.getglobal("str")).__nonzero__()) {
         var1.setline(155);
         var6 = var1.getlocal(0).__getattr__("include_dirs").__getattr__("split").__call__(var2, var1.getglobal("os").__getattr__("pathsep"));
         var1.getlocal(0).__setattr__("include_dirs", var6);
         var3 = null;
      }

      var1.setline(159);
      var1.getlocal(0).__getattr__("include_dirs").__getattr__("append").__call__(var2, var1.getlocal(2));
      var1.setline(160);
      var6 = var1.getlocal(3);
      var10000 = var6._ne(var1.getlocal(2));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(161);
         var1.getlocal(0).__getattr__("include_dirs").__getattr__("append").__call__(var2, var1.getlocal(3));
      }

      var1.setline(163);
      var1.getlocal(0).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("libraries"));
      var1.setline(167);
      var6 = var1.getlocal(0).__getattr__("libraries");
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      PyList var9;
      if (var10000.__nonzero__()) {
         var1.setline(168);
         var9 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"libraries", var9);
         var3 = null;
      }

      var1.setline(169);
      var6 = var1.getlocal(0).__getattr__("library_dirs");
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(170);
         var9 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"library_dirs", var9);
         var3 = null;
      } else {
         var1.setline(171);
         var6 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("library_dirs"));
         var10000 = var6._is(var1.getglobal("StringType"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(172);
            var6 = var1.getglobal("string").__getattr__("split").__call__(var2, var1.getlocal(0).__getattr__("library_dirs"), var1.getglobal("os").__getattr__("pathsep"));
            var1.getlocal(0).__setattr__("library_dirs", var6);
            var3 = null;
         }
      }

      var1.setline(174);
      var6 = var1.getlocal(0).__getattr__("rpath");
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(175);
         var9 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"rpath", var9);
         var3 = null;
      } else {
         var1.setline(176);
         var6 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("rpath"));
         var10000 = var6._is(var1.getglobal("StringType"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(177);
            var6 = var1.getglobal("string").__getattr__("split").__call__(var2, var1.getlocal(0).__getattr__("rpath"), var1.getglobal("os").__getattr__("pathsep"));
            var1.getlocal(0).__setattr__("rpath", var6);
            var3 = null;
         }
      }

      var1.setline(182);
      var6 = var1.getglobal("os").__getattr__("name");
      var10000 = var6._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(186);
         var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("exec_prefix"), (PyObject)PyString.fromInterned("libs")));
         var1.setline(187);
         if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
            var1.setline(188);
            var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("build_temp"), (PyObject)PyString.fromInterned("Debug"));
            var1.getlocal(0).__setattr__("build_temp", var6);
            var3 = null;
         } else {
            var1.setline(190);
            var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("build_temp"), (PyObject)PyString.fromInterned("Release"));
            var1.getlocal(0).__setattr__("build_temp", var6);
            var3 = null;
         }

         var1.setline(194);
         var1.getlocal(0).__getattr__("include_dirs").__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("exec_prefix"), (PyObject)PyString.fromInterned("PC")));
         var1.setline(195);
         var6 = var1.getglobal("MSVC_VERSION");
         var10000 = var6._eq(Py.newInteger(9));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(197);
            var6 = var1.getlocal(0).__getattr__("plat_name");
            var10000 = var6._eq(PyString.fromInterned("win32"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(198);
               PyString var10 = PyString.fromInterned("");
               var1.setlocal(4, var10);
               var3 = null;
            } else {
               var1.setline(201);
               var6 = var1.getlocal(0).__getattr__("plat_name").__getslice__(Py.newInteger(4), (PyObject)null, (PyObject)null);
               var1.setlocal(4, var6);
               var3 = null;
            }

            var1.setline(202);
            var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("exec_prefix"), (PyObject)PyString.fromInterned("PCbuild"));
            var1.setlocal(5, var6);
            var3 = null;
            var1.setline(203);
            if (var1.getlocal(4).__nonzero__()) {
               var1.setline(204);
               var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), var1.getlocal(4));
               var1.setlocal(5, var6);
               var3 = null;
            }

            var1.setline(205);
            var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getlocal(5));
         } else {
            var1.setline(207);
            var6 = var1.getglobal("MSVC_VERSION");
            var10000 = var6._eq(Py.newInteger(8));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(208);
               var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("exec_prefix"), (PyObject)PyString.fromInterned("PC"), (PyObject)PyString.fromInterned("VS8.0")));
            } else {
               var1.setline(210);
               var6 = var1.getglobal("MSVC_VERSION");
               var10000 = var6._eq(Py.newInteger(7));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(211);
                  var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("exec_prefix"), (PyObject)PyString.fromInterned("PC"), (PyObject)PyString.fromInterned("VS7.1")));
               } else {
                  var1.setline(214);
                  var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("exec_prefix"), (PyObject)PyString.fromInterned("PC"), (PyObject)PyString.fromInterned("VC6")));
               }
            }
         }
      }

      var1.setline(219);
      var6 = var1.getglobal("os").__getattr__("name");
      var10000 = var6._eq(PyString.fromInterned("os2"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(220);
         var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("exec_prefix"), (PyObject)PyString.fromInterned("Config")));
      }

      var1.setline(224);
      var6 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
      var10000 = var6._eq(PyString.fromInterned("cygwin"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var6 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
         var10000 = var6._eq(PyString.fromInterned("atheos"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(225);
         if (var1.getglobal("sys").__getattr__("executable").__getattr__("startswith").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys").__getattr__("exec_prefix"), (PyObject)PyString.fromInterned("bin"))).__nonzero__()) {
            var1.setline(227);
            var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getglobal("sys").__getattr__("prefix"), PyString.fromInterned("lib"), PyString.fromInterned("python")._add(var1.getglobal("get_python_version").__call__(var2)), PyString.fromInterned("config")));
         } else {
            var1.setline(232);
            var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         }
      }

      var1.setline(237);
      if (var1.getlocal(1).__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Py_ENABLE_SHARED")).__nonzero__()) {
         var1.setline(238);
         if (var1.getlocal(1).__getattr__("python_build").__not__().__nonzero__()) {
            var1.setline(240);
            var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getlocal(1).__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LIBDIR")));
         } else {
            var1.setline(243);
            var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
         }
      }

      var1.setline(250);
      if (var1.getlocal(0).__getattr__("define").__nonzero__()) {
         var1.setline(251);
         var6 = var1.getlocal(0).__getattr__("define").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(252);
         var10000 = var1.getglobal("map");
         var1.setline(252);
         var5 = Py.EmptyObjects;
         var6 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var5, f$5)), (PyObject)var1.getlocal(6));
         var1.getlocal(0).__setattr__("define", var6);
         var3 = null;
      }

      var1.setline(257);
      if (var1.getlocal(0).__getattr__("undef").__nonzero__()) {
         var1.setline(258);
         var6 = var1.getlocal(0).__getattr__("undef").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
         var1.getlocal(0).__setattr__("undef", var6);
         var3 = null;
      }

      var1.setline(260);
      var6 = var1.getlocal(0).__getattr__("swig_opts");
      var10000 = var6._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(261);
         var9 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"swig_opts", var9);
         var3 = null;
      } else {
         var1.setline(263);
         var6 = var1.getlocal(0).__getattr__("swig_opts").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "));
         var1.getlocal(0).__setattr__("swig_opts", var6);
         var3 = null;
      }

      var1.setline(266);
      if (var1.getlocal(0).__getattr__("user").__nonzero__()) {
         var1.setline(267);
         var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("USER_BASE"), (PyObject)PyString.fromInterned("include"));
         var1.setlocal(7, var6);
         var3 = null;
         var1.setline(268);
         var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("USER_BASE"), (PyObject)PyString.fromInterned("lib"));
         var1.setlocal(8, var6);
         var3 = null;
         var1.setline(269);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(7)).__nonzero__()) {
            var1.setline(270);
            var1.getlocal(0).__getattr__("include_dirs").__getattr__("append").__call__(var2, var1.getlocal(7));
         }

         var1.setline(271);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(8)).__nonzero__()) {
            var1.setline(272);
            var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getlocal(8));
            var1.setline(273);
            var1.getlocal(0).__getattr__("rpath").__getattr__("append").__call__(var2, var1.getlocal(8));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$5(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0), PyString.fromInterned("1")});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject run$6(PyFrame var1, ThreadState var2) {
      var1.setline(276);
      String[] var3 = new String[]{"new_compiler"};
      PyObject[] var7 = imp.importFrom("distutils.ccompiler", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(290);
      if (var1.getlocal(0).__getattr__("extensions").__not__().__nonzero__()) {
         var1.setline(291);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(296);
         PyObject var10000;
         PyObject var8;
         if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_c_libraries").__call__(var2).__nonzero__()) {
            var1.setline(297);
            var8 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_clib"));
            var1.setlocal(2, var8);
            var3 = null;
            var1.setline(298);
            var10000 = var1.getlocal(0).__getattr__("libraries").__getattr__("extend");
            Object var10002 = var1.getlocal(2).__getattr__("get_library_names").__call__(var2);
            if (!((PyObject)var10002).__nonzero__()) {
               var10002 = new PyList(Py.EmptyObjects);
            }

            var10000.__call__((ThreadState)var2, (PyObject)var10002);
            var1.setline(299);
            var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getlocal(2).__getattr__("build_clib"));
         }

         var1.setline(303);
         var10000 = var1.getlocal(1);
         var7 = new PyObject[]{var1.getlocal(0).__getattr__("compiler"), var1.getlocal(0).__getattr__("verbose"), var1.getlocal(0).__getattr__("dry_run"), var1.getlocal(0).__getattr__("force")};
         String[] var9 = new String[]{"compiler", "verbose", "dry_run", "force"};
         var10000 = var10000.__call__(var2, var7, var9);
         var3 = null;
         var8 = var10000;
         var1.getlocal(0).__setattr__("compiler", var8);
         var3 = null;
         var1.setline(307);
         var1.getglobal("customize_compiler").__call__(var2, var1.getlocal(0).__getattr__("compiler"));
         var1.setline(311);
         var8 = var1.getglobal("os").__getattr__("name");
         var10000 = var8._eq(PyString.fromInterned("nt"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var8 = var1.getlocal(0).__getattr__("plat_name");
            var10000 = var8._ne(var1.getglobal("get_platform").__call__(var2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(312);
            var1.getlocal(0).__getattr__("compiler").__getattr__("initialize").__call__(var2, var1.getlocal(0).__getattr__("plat_name"));
         }

         var1.setline(318);
         var8 = var1.getlocal(0).__getattr__("include_dirs");
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(319);
            var1.getlocal(0).__getattr__("compiler").__getattr__("set_include_dirs").__call__(var2, var1.getlocal(0).__getattr__("include_dirs"));
         }

         var1.setline(320);
         var8 = var1.getlocal(0).__getattr__("define");
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(322);
            var8 = var1.getlocal(0).__getattr__("define").__iter__();

            while(true) {
               var1.setline(322);
               var4 = var8.__iternext__();
               if (var4 == null) {
                  break;
               }

               PyObject[] var5 = Py.unpackSequence(var4, 2);
               PyObject var6 = var5[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(4, var6);
               var6 = null;
               var1.setline(323);
               var1.getlocal(0).__getattr__("compiler").__getattr__("define_macro").__call__(var2, var1.getlocal(3), var1.getlocal(4));
            }
         }

         var1.setline(324);
         var8 = var1.getlocal(0).__getattr__("undef");
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(325);
            var8 = var1.getlocal(0).__getattr__("undef").__iter__();

            while(true) {
               var1.setline(325);
               var4 = var8.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(5, var4);
               var1.setline(326);
               var1.getlocal(0).__getattr__("compiler").__getattr__("undefine_macro").__call__(var2, var1.getlocal(5));
            }
         }

         var1.setline(327);
         var8 = var1.getlocal(0).__getattr__("libraries");
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(328);
            var1.getlocal(0).__getattr__("compiler").__getattr__("set_libraries").__call__(var2, var1.getlocal(0).__getattr__("libraries"));
         }

         var1.setline(329);
         var8 = var1.getlocal(0).__getattr__("library_dirs");
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(330);
            var1.getlocal(0).__getattr__("compiler").__getattr__("set_library_dirs").__call__(var2, var1.getlocal(0).__getattr__("library_dirs"));
         }

         var1.setline(331);
         var8 = var1.getlocal(0).__getattr__("rpath");
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(332);
            var1.getlocal(0).__getattr__("compiler").__getattr__("set_runtime_library_dirs").__call__(var2, var1.getlocal(0).__getattr__("rpath"));
         }

         var1.setline(333);
         var8 = var1.getlocal(0).__getattr__("link_objects");
         var10000 = var8._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(334);
            var1.getlocal(0).__getattr__("compiler").__getattr__("set_link_objects").__call__(var2, var1.getlocal(0).__getattr__("link_objects"));
         }

         var1.setline(337);
         var1.getlocal(0).__getattr__("build_extensions").__call__(var2);
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject check_extensions_list$7(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      PyString.fromInterned("Ensure that the list of extensions (presumably provided as a\n        command option 'extensions') is valid, i.e. it is a list of\n        Extension objects.  We also support the old-style list of 2-tuples,\n        where the tuples are (ext_name, build_info), which are converted to\n        Extension instances here.\n\n        Raise DistutilsSetupError if the structure is invalid anywhere;\n        just returns otherwise.\n        ");
      var1.setline(349);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("list")).__not__().__nonzero__()) {
         var1.setline(350);
         throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("'ext_modules' option must be a list of Extension instances"));
      } else {
         var1.setline(353);
         PyObject var3 = var1.getglobal("enumerate").__call__(var2, var1.getlocal(1)).__iter__();

         while(true) {
            PyObject[] var5;
            PyObject var6;
            do {
               var1.setline(353);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(2, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(354);
            } while(var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("Extension")).__nonzero__());

            var1.setline(358);
            PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("tuple")).__not__();
            PyObject var8;
            if (!var10000.__nonzero__()) {
               var8 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
               var10000 = var8._ne(Py.newInteger(2));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(359);
               throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("each element of 'ext_modules' option must be an Extension instance or 2-tuple"));
            }

            var1.setline(363);
            var8 = var1.getlocal(3);
            PyObject[] var9 = Py.unpackSequence(var8, 2);
            PyObject var7 = var9[0];
            var1.setlocal(4, var7);
            var7 = null;
            var7 = var9[1];
            var1.setlocal(5, var7);
            var7 = null;
            var5 = null;
            var1.setline(365);
            var1.getglobal("log").__getattr__("warn").__call__(var2, PyString.fromInterned("old-style (ext_name, build_info) tuple found in ext_modules for extension '%s'-- please convert to Extension instance")._mod(var1.getlocal(4)));
            var1.setline(369);
            var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("str"));
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("extension_name_re").__getattr__("match").__call__(var2, var1.getlocal(4));
            }

            if (var10000.__not__().__nonzero__()) {
               var1.setline(371);
               throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("first element of each tuple in 'ext_modules' must be the extension name (a string)"));
            }

            var1.setline(375);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("dict")).__not__().__nonzero__()) {
               var1.setline(376);
               throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("second element of each tuple in 'ext_modules' must be a dictionary (build info)"));
            }

            var1.setline(382);
            var8 = var1.getglobal("Extension").__call__(var2, var1.getlocal(4), var1.getlocal(5).__getitem__(PyString.fromInterned("sources")));
            var1.setlocal(3, var8);
            var5 = null;
            var1.setline(386);
            var8 = (new PyTuple(new PyObject[]{PyString.fromInterned("include_dirs"), PyString.fromInterned("library_dirs"), PyString.fromInterned("libraries"), PyString.fromInterned("extra_objects"), PyString.fromInterned("extra_compile_args"), PyString.fromInterned("extra_link_args")})).__iter__();

            while(true) {
               var1.setline(386);
               var6 = var8.__iternext__();
               if (var6 == null) {
                  var1.setline(394);
                  var8 = var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rpath"));
                  var1.getlocal(3).__setattr__("runtime_library_dirs", var8);
                  var5 = null;
                  var1.setline(395);
                  PyString var10 = PyString.fromInterned("def_file");
                  var10000 = var10._in(var1.getlocal(5));
                  var5 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(396);
                     var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'def_file' element of build info dict no longer supported"));
                  }

                  var1.setline(401);
                  var8 = var1.getlocal(5).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("macros"));
                  var1.setlocal(8, var8);
                  var5 = null;
                  var1.setline(402);
                  if (var1.getlocal(8).__nonzero__()) {
                     var1.setline(403);
                     PyList var11 = new PyList(Py.EmptyObjects);
                     var1.getlocal(3).__setattr__((String)"define_macros", var11);
                     var5 = null;
                     var1.setline(404);
                     var11 = new PyList(Py.EmptyObjects);
                     var1.getlocal(3).__setattr__((String)"undef_macros", var11);
                     var5 = null;
                     var1.setline(405);
                     var8 = var1.getlocal(8).__iter__();

                     while(true) {
                        var1.setline(405);
                        var6 = var8.__iternext__();
                        if (var6 == null) {
                           break;
                        }

                        var1.setlocal(9, var6);
                        var1.setline(406);
                        var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(9), var1.getglobal("tuple"));
                        if (var10000.__nonzero__()) {
                           var7 = var1.getglobal("len").__call__(var2, var1.getlocal(9));
                           var10000 = var7._in(new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2)}));
                           var7 = null;
                        }

                        if (var10000.__not__().__nonzero__()) {
                           var1.setline(407);
                           throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("'macros' element of build info dict must be 1- or 2-tuple"));
                        }

                        var1.setline(410);
                        var7 = var1.getglobal("len").__call__(var2, var1.getlocal(9));
                        var10000 = var7._eq(Py.newInteger(1));
                        var7 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(411);
                           var1.getlocal(3).__getattr__("undef_macros").__getattr__("append").__call__(var2, var1.getlocal(9).__getitem__(Py.newInteger(0)));
                        } else {
                           var1.setline(412);
                           var7 = var1.getglobal("len").__call__(var2, var1.getlocal(9));
                           var10000 = var7._eq(Py.newInteger(2));
                           var7 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(413);
                              var1.getlocal(3).__getattr__("define_macros").__getattr__("append").__call__(var2, var1.getlocal(9));
                           }
                        }
                     }
                  }

                  var1.setline(415);
                  var8 = var1.getlocal(3);
                  var1.getlocal(1).__setitem__(var1.getlocal(2), var8);
                  var5 = null;
                  break;
               }

               var1.setlocal(6, var6);
               var1.setline(389);
               var7 = var1.getlocal(5).__getattr__("get").__call__(var2, var1.getlocal(6));
               var1.setlocal(7, var7);
               var7 = null;
               var1.setline(390);
               var7 = var1.getlocal(7);
               var10000 = var7._isnot(var1.getglobal("None"));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(391);
                  var1.getglobal("setattr").__call__(var2, var1.getlocal(3), var1.getlocal(6), var1.getlocal(7));
               }
            }
         }
      }
   }

   public PyObject get_source_files$8(PyFrame var1, ThreadState var2) {
      var1.setline(418);
      var1.getlocal(0).__getattr__("check_extensions_list").__call__(var2, var1.getlocal(0).__getattr__("extensions"));
      var1.setline(419);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(422);
      PyObject var5 = var1.getlocal(0).__getattr__("extensions").__iter__();

      while(true) {
         var1.setline(422);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(425);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(423);
         var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(2).__getattr__("sources"));
      }
   }

   public PyObject get_outputs$9(PyFrame var1, ThreadState var2) {
      var1.setline(431);
      var1.getlocal(0).__getattr__("check_extensions_list").__call__(var2, var1.getlocal(0).__getattr__("extensions"));
      var1.setline(436);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(437);
      PyObject var5 = var1.getlocal(0).__getattr__("extensions").__iter__();

      while(true) {
         var1.setline(437);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(439);
            var5 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(2, var4);
         var1.setline(438);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("get_ext_fullpath").__call__(var2, var1.getlocal(2).__getattr__("name")));
      }
   }

   public PyObject build_extensions$10(PyFrame var1, ThreadState var2) {
      var1.setline(443);
      var1.getlocal(0).__getattr__("check_extensions_list").__call__(var2, var1.getlocal(0).__getattr__("extensions"));
      var1.setline(445);
      PyObject var3 = var1.getlocal(0).__getattr__("extensions").__iter__();

      while(true) {
         var1.setline(445);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(446);
         var1.getlocal(0).__getattr__("build_extension").__call__(var2, var1.getlocal(1));
      }
   }

   public PyObject build_extension$11(PyFrame var1, ThreadState var2) {
      var1.setline(449);
      PyObject var3 = var1.getlocal(1).__getattr__("sources");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(450);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
         var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("ListType"), var1.getglobal("TupleType")}));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(451);
         throw Py.makeException(var1.getglobal("DistutilsSetupError"), PyString.fromInterned("in 'ext_modules' option (extension '%s'), ")._add(PyString.fromInterned("'sources' must be present and must be "))._add(PyString.fromInterned("a list of source filenames"))._mod(var1.getlocal(1).__getattr__("name")));
      } else {
         var1.setline(455);
         var3 = var1.getglobal("list").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(457);
         var3 = var1.getlocal(0).__getattr__("get_ext_fullpath").__call__(var2, var1.getlocal(1).__getattr__("name"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(458);
         var3 = var1.getlocal(2)._add(var1.getlocal(1).__getattr__("depends"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(459);
         var10000 = var1.getlocal(0).__getattr__("force");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("newer_group").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("newer"));
         }

         if (var10000.__not__().__nonzero__()) {
            var1.setline(460);
            var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("skipping '%s' extension (up-to-date)"), (PyObject)var1.getlocal(1).__getattr__("name"));
            var1.setline(461);
            var1.f_lasti = -1;
            return Py.None;
         } else {
            var1.setline(463);
            var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("building '%s' extension"), (PyObject)var1.getlocal(1).__getattr__("name"));
            var1.setline(468);
            var3 = var1.getlocal(0).__getattr__("swig_sources").__call__(var2, var1.getlocal(2), var1.getlocal(1));
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(484);
            Object var8 = var1.getlocal(1).__getattr__("extra_compile_args");
            if (!((PyObject)var8).__nonzero__()) {
               var8 = new PyList(Py.EmptyObjects);
            }

            Object var6 = var8;
            var1.setlocal(5, (PyObject)var6);
            var3 = null;
            var1.setline(486);
            var3 = var1.getlocal(1).__getattr__("define_macros").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(487);
            var3 = var1.getlocal(1).__getattr__("undef_macros").__iter__();

            while(true) {
               var1.setline(487);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(490);
                  var10000 = var1.getlocal(0).__getattr__("compiler").__getattr__("compile");
                  PyObject[] var7 = new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("build_temp"), var1.getlocal(6), var1.getlocal(1).__getattr__("include_dirs"), var1.getlocal(0).__getattr__("debug"), var1.getlocal(5), var1.getlocal(1).__getattr__("depends")};
                  String[] var5 = new String[]{"output_dir", "macros", "include_dirs", "debug", "extra_postargs", "depends"};
                  var10000 = var10000.__call__(var2, var7, var5);
                  var3 = null;
                  var3 = var10000;
                  var1.setlocal(8, var3);
                  var3 = null;
                  var1.setline(507);
                  var3 = var1.getlocal(8).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
                  var1.getlocal(0).__setattr__("_built_objects", var3);
                  var3 = null;
                  var1.setline(512);
                  if (var1.getlocal(1).__getattr__("extra_objects").__nonzero__()) {
                     var1.setline(513);
                     var1.getlocal(8).__getattr__("extend").__call__(var2, var1.getlocal(1).__getattr__("extra_objects"));
                  }

                  var1.setline(514);
                  var8 = var1.getlocal(1).__getattr__("extra_link_args");
                  if (!((PyObject)var8).__nonzero__()) {
                     var8 = new PyList(Py.EmptyObjects);
                  }

                  var6 = var8;
                  var1.setlocal(5, (PyObject)var6);
                  var3 = null;
                  var1.setline(517);
                  var10000 = var1.getlocal(1).__getattr__("language");
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(0).__getattr__("compiler").__getattr__("detect_language").__call__(var2, var1.getlocal(2));
                  }

                  var3 = var10000;
                  var1.setlocal(9, var3);
                  var3 = null;
                  var1.setline(519);
                  var10000 = var1.getlocal(0).__getattr__("compiler").__getattr__("link_shared_object");
                  var7 = new PyObject[]{var1.getlocal(8), var1.getlocal(3), var1.getlocal(0).__getattr__("get_libraries").__call__(var2, var1.getlocal(1)), var1.getlocal(1).__getattr__("library_dirs"), var1.getlocal(1).__getattr__("runtime_library_dirs"), var1.getlocal(5), var1.getlocal(0).__getattr__("get_export_symbols").__call__(var2, var1.getlocal(1)), var1.getlocal(0).__getattr__("debug"), var1.getlocal(0).__getattr__("build_temp"), var1.getlocal(9)};
                  var5 = new String[]{"libraries", "library_dirs", "runtime_library_dirs", "extra_postargs", "export_symbols", "debug", "build_temp", "target_lang"};
                  var10000.__call__(var2, var7, var5);
                  var3 = null;
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(7, var4);
               var1.setline(488);
               var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(7)})));
            }
         }
      }
   }

   public PyObject swig_sources$12(PyFrame var1, ThreadState var2) {
      var1.setline(537);
      PyString.fromInterned("Walk the list of source files in 'sources', looking for SWIG\n        interface (.i) files.  Run SWIG on all that are found, and\n        return a modified 'sources' list with SWIG source files replaced\n        by the generated C (or C++) files.\n        ");
      var1.setline(539);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(540);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(541);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(548);
      if (var1.getlocal(0).__getattr__("swig_cpp").__nonzero__()) {
         var1.setline(549);
         var1.getglobal("log").__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--swig-cpp is deprecated - use --swig-opts=-c++"));
      }

      var1.setline(551);
      PyObject var10000 = var1.getlocal(0).__getattr__("swig_cpp");
      PyString var9;
      if (!var10000.__nonzero__()) {
         var9 = PyString.fromInterned("-c++");
         var10000 = var9._in(var1.getlocal(0).__getattr__("swig_opts"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var9 = PyString.fromInterned("-c++");
            var10000 = var9._in(var1.getlocal(2).__getattr__("swig_opts"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(553);
         var9 = PyString.fromInterned(".cpp");
         var1.setlocal(6, var9);
         var3 = null;
      } else {
         var1.setline(555);
         var9 = PyString.fromInterned(".c");
         var1.setlocal(6, var9);
         var3 = null;
      }

      var1.setline(557);
      PyObject var12 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(557);
         PyObject var4 = var12.__iternext__();
         PyObject var5;
         PyObject[] var6;
         if (var4 == null) {
            var1.setline(566);
            if (var1.getlocal(4).__not__().__nonzero__()) {
               var1.setline(567);
               var12 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var12;
            } else {
               var1.setline(569);
               var10000 = var1.getlocal(0).__getattr__("swig");
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("find_swig").__call__(var2);
               }

               var4 = var10000;
               var1.setlocal(10, var4);
               var4 = null;
               var1.setline(570);
               PyList var10 = new PyList(new PyObject[]{var1.getlocal(10), PyString.fromInterned("-python")});
               var1.setlocal(11, var10);
               var4 = null;
               var1.setline(571);
               var1.getlocal(11).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("swig_opts"));
               var1.setline(572);
               if (var1.getlocal(0).__getattr__("swig_cpp").__nonzero__()) {
                  var1.setline(573);
                  var1.getlocal(11).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-c++"));
               }

               var1.setline(576);
               if (var1.getlocal(0).__getattr__("swig_opts").__not__().__nonzero__()) {
                  var1.setline(577);
                  var4 = var1.getlocal(2).__getattr__("swig_opts").__iter__();

                  while(true) {
                     var1.setline(577);
                     var5 = var4.__iternext__();
                     if (var5 == null) {
                        break;
                     }

                     var1.setlocal(12, var5);
                     var1.setline(578);
                     var1.getlocal(11).__getattr__("append").__call__(var2, var1.getlocal(12));
                  }
               }

               var1.setline(580);
               var4 = var1.getlocal(4).__iter__();

               while(true) {
                  var1.setline(580);
                  var5 = var4.__iternext__();
                  if (var5 == null) {
                     var1.setline(585);
                     var12 = var1.getlocal(3);
                     var1.f_lasti = -1;
                     return var12;
                  }

                  var1.setlocal(7, var5);
                  var1.setline(581);
                  PyObject var11 = var1.getlocal(5).__getitem__(var1.getlocal(7));
                  var1.setlocal(13, var11);
                  var6 = null;
                  var1.setline(582);
                  var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, PyString.fromInterned("swigging %s to %s"), (PyObject)var1.getlocal(7), (PyObject)var1.getlocal(13));
                  var1.setline(583);
                  var1.getlocal(0).__getattr__("spawn").__call__(var2, var1.getlocal(11)._add(new PyList(new PyObject[]{PyString.fromInterned("-o"), var1.getlocal(13), var1.getlocal(7)})));
               }
            }
         }

         var1.setlocal(7, var4);
         var1.setline(558);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(7));
         var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(8, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(9, var7);
         var7 = null;
         var5 = null;
         var1.setline(559);
         var5 = var1.getlocal(9);
         var10000 = var5._eq(PyString.fromInterned(".i"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(560);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(8)._add(PyString.fromInterned("_wrap"))._add(var1.getlocal(6)));
            var1.setline(561);
            var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(7));
            var1.setline(562);
            var5 = var1.getlocal(3).__getitem__(Py.newInteger(-1));
            var1.getlocal(5).__setitem__(var1.getlocal(7), var5);
            var5 = null;
         } else {
            var1.setline(564);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getlocal(7));
         }
      }
   }

   public PyObject find_swig$13(PyFrame var1, ThreadState var2) {
      var1.setline(593);
      PyString.fromInterned("Return the name of the SWIG executable.  On Unix, this is\n        just \"swig\" -- it should be in the PATH.  Tries a bit harder on\n        Windows.\n        ");
      var1.setline(595);
      PyObject var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("posix"));
      var3 = null;
      PyString var7;
      if (var10000.__nonzero__()) {
         var1.setline(596);
         var7 = PyString.fromInterned("swig");
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(597);
         PyObject var4 = var1.getglobal("os").__getattr__("name");
         var10000 = var4._eq(PyString.fromInterned("nt"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(609);
            var4 = var1.getglobal("os").__getattr__("name");
            var10000 = var4._eq(PyString.fromInterned("os2"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(611);
               var7 = PyString.fromInterned("swig.exe");
               var1.f_lasti = -1;
               return var7;
            } else {
               var1.setline(614);
               throw Py.makeException(var1.getglobal("DistutilsPlatformError"), PyString.fromInterned("I don't know how to find (much less run) SWIG on platform '%s'")._mod(var1.getglobal("os").__getattr__("name")));
            }
         } else {
            var1.setline(602);
            var4 = (new PyTuple(new PyObject[]{PyString.fromInterned("1.3"), PyString.fromInterned("1.2"), PyString.fromInterned("1.1")})).__iter__();

            do {
               var1.setline(602);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  var1.setline(607);
                  var7 = PyString.fromInterned("swig.exe");
                  var1.f_lasti = -1;
                  return var7;
               }

               var1.setlocal(1, var5);
               var1.setline(603);
               PyObject var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("c:\\swig%s")._mod(var1.getlocal(1)), (PyObject)PyString.fromInterned("swig.exe"));
               var1.setlocal(2, var6);
               var6 = null;
               var1.setline(604);
            } while(!var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(2)).__nonzero__());

            var1.setline(605);
            var3 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject get_ext_fullpath$14(PyFrame var1, ThreadState var2) {
      var1.setline(627);
      PyString.fromInterned("Returns the path of the filename for a given extension.\n\n        The file is located in `build_lib` or directly in the package\n        (inplace option).\n        ");
      var1.setline(629);
      PyObject var3 = var1.getglobal("string").__getattr__("maketrans").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/")._add(var1.getglobal("os").__getattr__("sep")), (PyObject)PyString.fromInterned(".."));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(630);
      var3 = var1.getlocal(1).__getattr__("translate").__call__(var2, var1.getlocal(2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(632);
      var3 = var1.getlocal(0).__getattr__("get_ext_fullname").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(633);
      var3 = var1.getlocal(3).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(634);
      var3 = var1.getlocal(0).__getattr__("get_ext_filename").__call__(var2, var1.getlocal(1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(635);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(5)).__getitem__(Py.newInteger(-1));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(637);
      if (var1.getlocal(0).__getattr__("inplace").__not__().__nonzero__()) {
         var1.setline(641);
         PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
         PyObject[] var6 = Py.EmptyObjects;
         String[] var5 = new String[0];
         var10000 = var10000._callextra(var6, var5, var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null)._add(new PyList(new PyObject[]{var1.getlocal(5)})), (PyObject)null);
         var3 = null;
         var3 = var10000;
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(642);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("build_lib"), var1.getlocal(5));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(646);
         PyObject var4 = PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(4).__getslice__(Py.newInteger(0), Py.newInteger(-1), (PyObject)null));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(647);
         var4 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_py"));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(648);
         var4 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(7).__getattr__("get_package_dir").__call__(var2, var1.getlocal(6)));
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(652);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(8), var1.getlocal(5));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get_ext_fullname$15(PyFrame var1, ThreadState var2) {
      var1.setline(657);
      PyString.fromInterned("Returns the fullname of a given extension name.\n\n        Adds the `package.` prefix");
      var1.setline(658);
      PyObject var3 = var1.getlocal(0).__getattr__("package");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(659);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(661);
         var3 = var1.getlocal(0).__getattr__("package")._add(PyString.fromInterned("."))._add(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get_ext_filename$16(PyFrame var1, ThreadState var2) {
      var1.setline(667);
      PyString.fromInterned("Convert the name of an extension (eg. \"foo.bar\") into the name\n        of the file from which it will be loaded (eg. \"foo/bar.so\", or\n        \"foo\\bar.pyd\").\n        ");
      var1.setline(668);
      String[] var3 = new String[]{"get_config_var"};
      PyObject[] var6 = imp.importFrom("distutils.sysconfig", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(669);
      PyObject var7 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("."));
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(671);
      var7 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var7._eq(PyString.fromInterned("os2"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(672);
         var7 = var1.getlocal(3).__getitem__(var1.getglobal("len").__call__(var2, var1.getlocal(3))._sub(Py.newInteger(1))).__getslice__((PyObject)null, Py.newInteger(8), (PyObject)null);
         var1.getlocal(3).__setitem__(var1.getglobal("len").__call__(var2, var1.getlocal(3))._sub(Py.newInteger(1)), var7);
         var3 = null;
      }

      var1.setline(674);
      var7 = var1.getlocal(2).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SO"));
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(675);
      var7 = var1.getglobal("os").__getattr__("name");
      var10000 = var7._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("debug");
      }

      if (var10000.__nonzero__()) {
         var1.setline(676);
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
         var6 = Py.EmptyObjects;
         String[] var9 = new String[0];
         var10000 = var10000._callextra(var6, var9, var1.getlocal(3), (PyObject)null);
         var3 = null;
         var7 = var10000._add(PyString.fromInterned("_d"))._add(var1.getlocal(4));
         var1.f_lasti = -1;
         return var7;
      } else {
         var1.setline(677);
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
         PyObject[] var8 = Py.EmptyObjects;
         String[] var5 = new String[0];
         var10000 = var10000._callextra(var8, var5, var1.getlocal(3), (PyObject)null);
         var4 = null;
         var7 = var10000._add(var1.getlocal(4));
         var1.f_lasti = -1;
         return var7;
      }
   }

   public PyObject get_export_symbols$17(PyFrame var1, ThreadState var2) {
      var1.setline(684);
      PyString.fromInterned("Return the list of symbols that a shared extension has to\n        export.  This either uses 'ext.export_symbols' or, if it's not\n        provided, \"init\" + module_name.  Only relevant on Windows, where\n        the .pyd file (DLL) must export the module \"init\" function.\n        ");
      var1.setline(685);
      PyObject var3 = PyString.fromInterned("init")._add(var1.getlocal(1).__getattr__("name").__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getitem__(Py.newInteger(-1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(686);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._notin(var1.getlocal(1).__getattr__("export_symbols"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(687);
         var1.getlocal(1).__getattr__("export_symbols").__getattr__("append").__call__(var2, var1.getlocal(2));
      }

      var1.setline(688);
      var3 = var1.getlocal(1).__getattr__("export_symbols");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_libraries$18(PyFrame var1, ThreadState var2) {
      var1.setline(694);
      PyString.fromInterned("Return the list of libraries to link against when building a\n        shared extension.  On most platforms, this is just 'ext.libraries';\n        on Windows and OS/2, we add the Python library (eg. python20.dll).\n        ");
      var1.setline(700);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(701);
         String[] var6 = new String[]{"MSVCCompiler"};
         PyObject[] var7 = imp.importFrom("distutils.msvccompiler", var6, var1, -1);
         var4 = var7[0];
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(702);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("compiler"), var1.getlocal(2)).__not__().__nonzero__()) {
            var1.setline(703);
            PyString var8 = PyString.fromInterned("python%d%d");
            var1.setlocal(3, var8);
            var3 = null;
            var1.setline(704);
            if (var1.getlocal(0).__getattr__("debug").__nonzero__()) {
               var1.setline(705);
               var3 = var1.getlocal(3)._add(PyString.fromInterned("_d"));
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(706);
            var3 = var1.getlocal(3)._mod(new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("hexversion")._rshift(Py.newInteger(24)), var1.getglobal("sys").__getattr__("hexversion")._rshift(Py.newInteger(16))._and(Py.newInteger(255))}));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(710);
            var3 = var1.getlocal(1).__getattr__("libraries")._add(new PyList(new PyObject[]{var1.getlocal(4)}));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(712);
            var3 = var1.getlocal(1).__getattr__("libraries");
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(713);
         var4 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var4._eq(PyString.fromInterned("os2emx"));
         var4 = null;
         PyString var11;
         if (var10000.__nonzero__()) {
            var1.setline(716);
            var11 = PyString.fromInterned("python%d%d");
            var1.setlocal(3, var11);
            var4 = null;
            var1.setline(721);
            var4 = var1.getlocal(3)._mod(new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("hexversion")._rshift(Py.newInteger(24)), var1.getglobal("sys").__getattr__("hexversion")._rshift(Py.newInteger(16))._and(Py.newInteger(255))}));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(725);
            var3 = var1.getlocal(1).__getattr__("libraries")._add(new PyList(new PyObject[]{var1.getlocal(4)}));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(726);
            var4 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
            var10000 = var4._eq(PyString.fromInterned("cygwin"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(727);
               var11 = PyString.fromInterned("python%d.%d");
               var1.setlocal(3, var11);
               var4 = null;
               var1.setline(728);
               var4 = var1.getlocal(3)._mod(new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("hexversion")._rshift(Py.newInteger(24)), var1.getglobal("sys").__getattr__("hexversion")._rshift(Py.newInteger(16))._and(Py.newInteger(255))}));
               var1.setlocal(4, var4);
               var4 = null;
               var1.setline(732);
               var3 = var1.getlocal(1).__getattr__("libraries")._add(new PyList(new PyObject[]{var1.getlocal(4)}));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(733);
               var4 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
               var10000 = var4._eq(PyString.fromInterned("atheos"));
               var4 = null;
               PyObject var5;
               String[] var9;
               PyObject[] var10;
               if (var10000.__nonzero__()) {
                  var1.setline(734);
                  var9 = new String[]{"sysconfig"};
                  var10 = imp.importFrom("distutils", var9, var1, -1);
                  var5 = var10[0];
                  var1.setlocal(5, var5);
                  var5 = null;
                  var1.setline(736);
                  var11 = PyString.fromInterned("python%d.%d");
                  var1.setlocal(3, var11);
                  var4 = null;
                  var1.setline(737);
                  var4 = var1.getlocal(3)._mod(new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("hexversion")._rshift(Py.newInteger(24)), var1.getglobal("sys").__getattr__("hexversion")._rshift(Py.newInteger(16))._and(Py.newInteger(255))}));
                  var1.setlocal(4, var4);
                  var4 = null;
                  var1.setline(740);
                  PyList var12 = new PyList(Py.EmptyObjects);
                  var1.setlocal(6, var12);
                  var4 = null;
                  var1.setline(741);
                  var4 = var1.getlocal(5).__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SHLIBS")).__getattr__("split").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(741);
                     var5 = var4.__iternext__();
                     if (var5 == null) {
                        var1.setline(748);
                        var3 = var1.getlocal(1).__getattr__("libraries")._add(new PyList(new PyObject[]{var1.getlocal(4), PyString.fromInterned("m")}))._add(var1.getlocal(6));
                        var1.f_lasti = -1;
                        return var3;
                     }

                     var1.setlocal(7, var5);
                     var1.setline(742);
                     if (var1.getlocal(7).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-l")).__nonzero__()) {
                        var1.setline(743);
                        var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(7).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null));
                     } else {
                        var1.setline(745);
                        var1.getlocal(6).__getattr__("append").__call__(var2, var1.getlocal(7));
                     }
                  }
               } else {
                  var1.setline(750);
                  var4 = var1.getglobal("sys").__getattr__("platform");
                  var10000 = var4._eq(PyString.fromInterned("darwin"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(752);
                     var3 = var1.getlocal(1).__getattr__("libraries");
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(753);
                     var4 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
                     var10000 = var4._eq(PyString.fromInterned("aix"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(755);
                        var3 = var1.getlocal(1).__getattr__("libraries");
                        var1.f_lasti = -1;
                        return var3;
                     } else {
                        var1.setline(757);
                        var9 = new String[]{"sysconfig"};
                        var10 = imp.importFrom("distutils", var9, var1, -1);
                        var5 = var10[0];
                        var1.setlocal(5, var5);
                        var5 = null;
                        var1.setline(758);
                        if (var1.getlocal(5).__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Py_ENABLE_SHARED")).__nonzero__()) {
                           var1.setline(759);
                           var11 = PyString.fromInterned("python%d.%d");
                           var1.setlocal(3, var11);
                           var4 = null;
                           var1.setline(760);
                           var4 = var1.getlocal(3)._mod(new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("hexversion")._rshift(Py.newInteger(24)), var1.getglobal("sys").__getattr__("hexversion")._rshift(Py.newInteger(16))._and(Py.newInteger(255))}));
                           var1.setlocal(4, var4);
                           var4 = null;
                           var1.setline(762);
                           var3 = var1.getlocal(1).__getattr__("libraries")._add(new PyList(new PyObject[]{var1.getlocal(4)}));
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(764);
                           var3 = var1.getlocal(1).__getattr__("libraries");
                           var1.f_lasti = -1;
                           return var3;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public build_ext$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"show_compilers"};
      show_compilers$1 = Py.newCode(0, var2, var1, "show_compilers", 32, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      build_ext$2 = Py.newCode(0, var2, var1, "build_ext", 37, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$3 = Py.newCode(1, var2, var1, "initialize_options", 108, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sysconfig", "py_include", "plat_py_include", "suffix", "new_lib", "defines", "user_include", "user_lib"};
      finalize_options$4 = Py.newCode(1, var2, var1, "finalize_options", 131, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"symbol"};
      f$5 = Py.newCode(1, var2, var1, "<lambda>", 252, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "new_compiler", "build_clib", "name", "value", "macro"};
      run$6 = Py.newCode(1, var2, var1, "run", 275, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "extensions", "i", "ext", "ext_name", "build_info", "key", "val", "macros", "macro"};
      check_extensions_list$7 = Py.newCode(2, var2, var1, "check_extensions_list", 339, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filenames", "ext"};
      get_source_files$8 = Py.newCode(1, var2, var1, "get_source_files", 417, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "outputs", "ext"};
      get_outputs$9 = Py.newCode(1, var2, var1, "get_outputs", 427, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ext"};
      build_extensions$10 = Py.newCode(1, var2, var1, "build_extensions", 441, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ext", "sources", "ext_path", "depends", "extra_args", "macros", "undef", "objects", "language"};
      build_extension$11 = Py.newCode(2, var2, var1, "build_extension", 448, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sources", "extension", "new_sources", "swig_sources", "swig_targets", "target_ext", "source", "base", "ext", "swig", "swig_cmd", "o", "target"};
      swig_sources$12 = Py.newCode(3, var2, var1, "swig_sources", 531, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "vers", "fn"};
      find_swig$13 = Py.newCode(1, var2, var1, "find_swig", 589, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ext_name", "all_dots", "fullname", "modpath", "filename", "package", "build_py", "package_dir"};
      get_ext_fullpath$14 = Py.newCode(2, var2, var1, "get_ext_fullpath", 622, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ext_name"};
      get_ext_fullname$15 = Py.newCode(2, var2, var1, "get_ext_fullname", 654, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ext_name", "get_config_var", "ext_path", "so_ext"};
      get_ext_filename$16 = Py.newCode(2, var2, var1, "get_ext_filename", 663, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ext", "initfunc_name"};
      get_export_symbols$17 = Py.newCode(2, var2, var1, "get_export_symbols", 679, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ext", "MSVCCompiler", "template", "pythonlib", "sysconfig", "extra", "lib"};
      get_libraries$18 = Py.newCode(2, var2, var1, "get_libraries", 690, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new build_ext$py("distutils/command/build_ext$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(build_ext$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.show_compilers$1(var2, var3);
         case 2:
            return this.build_ext$2(var2, var3);
         case 3:
            return this.initialize_options$3(var2, var3);
         case 4:
            return this.finalize_options$4(var2, var3);
         case 5:
            return this.f$5(var2, var3);
         case 6:
            return this.run$6(var2, var3);
         case 7:
            return this.check_extensions_list$7(var2, var3);
         case 8:
            return this.get_source_files$8(var2, var3);
         case 9:
            return this.get_outputs$9(var2, var3);
         case 10:
            return this.build_extensions$10(var2, var3);
         case 11:
            return this.build_extension$11(var2, var3);
         case 12:
            return this.swig_sources$12(var2, var3);
         case 13:
            return this.find_swig$13(var2, var3);
         case 14:
            return this.get_ext_fullpath$14(var2, var3);
         case 15:
            return this.get_ext_fullname$15(var2, var3);
         case 16:
            return this.get_ext_filename$16(var2, var3);
         case 17:
            return this.get_export_symbols$17(var2, var3);
         case 18:
            return this.get_libraries$18(var2, var3);
         default:
            return null;
      }
   }
}
