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
@MTime(1498849384000L)
@Filename("distutils/ccompiler.py")
public class ccompiler$py extends PyFunctionTable implements PyRunnable {
   static ccompiler$py self;
   static final PyCode f$0;
   static final PyCode CCompiler$1;
   static final PyCode __init__$2;
   static final PyCode set_executables$3;
   static final PyCode set_executable$4;
   static final PyCode _find_macro$5;
   static final PyCode _check_macro_definitions$6;
   static final PyCode define_macro$7;
   static final PyCode undefine_macro$8;
   static final PyCode add_include_dir$9;
   static final PyCode set_include_dirs$10;
   static final PyCode add_library$11;
   static final PyCode set_libraries$12;
   static final PyCode add_library_dir$13;
   static final PyCode set_library_dirs$14;
   static final PyCode add_runtime_library_dir$15;
   static final PyCode set_runtime_library_dirs$16;
   static final PyCode add_link_object$17;
   static final PyCode set_link_objects$18;
   static final PyCode _setup_compile$19;
   static final PyCode _get_cc_args$20;
   static final PyCode _fix_compile_args$21;
   static final PyCode _fix_object_args$22;
   static final PyCode _fix_lib_args$23;
   static final PyCode _need_link$24;
   static final PyCode detect_language$25;
   static final PyCode preprocess$26;
   static final PyCode compile$27;
   static final PyCode _compile$28;
   static final PyCode create_static_lib$29;
   static final PyCode link$30;
   static final PyCode link_shared_lib$31;
   static final PyCode link_shared_object$32;
   static final PyCode link_executable$33;
   static final PyCode library_dir_option$34;
   static final PyCode runtime_library_dir_option$35;
   static final PyCode library_option$36;
   static final PyCode has_function$37;
   static final PyCode find_library_file$38;
   static final PyCode object_filenames$39;
   static final PyCode shared_object_filename$40;
   static final PyCode executable_filename$41;
   static final PyCode library_filename$42;
   static final PyCode announce$43;
   static final PyCode debug_print$44;
   static final PyCode warn$45;
   static final PyCode execute$46;
   static final PyCode spawn$47;
   static final PyCode move_file$48;
   static final PyCode mkpath$49;
   static final PyCode get_default_compiler$50;
   static final PyCode show_compilers$51;
   static final PyCode new_compiler$52;
   static final PyCode gen_preprocess_options$53;
   static final PyCode gen_lib_options$54;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.ccompiler\n\nContains CCompiler, an abstract base class that defines the interface\nfor the Distutils compiler abstraction model."));
      var1.setline(4);
      PyString.fromInterned("distutils.ccompiler\n\nContains CCompiler, an abstract base class that defines the interface\nfor the Distutils compiler abstraction model.");
      var1.setline(6);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(8);
      PyObject var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(9);
      var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(10);
      var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(12);
      String[] var6 = new String[]{"CompileError", "LinkError", "UnknownFileError", "DistutilsPlatformError", "DistutilsModuleError"};
      PyObject[] var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("CompileError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("LinkError", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("UnknownFileError", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var4 = var7[4];
      var1.setlocal("DistutilsModuleError", var4);
      var4 = null;
      var1.setline(14);
      var6 = new String[]{"spawn"};
      var7 = imp.importFrom("distutils.spawn", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("spawn", var4);
      var4 = null;
      var1.setline(15);
      var6 = new String[]{"move_file"};
      var7 = imp.importFrom("distutils.file_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("move_file", var4);
      var4 = null;
      var1.setline(16);
      var6 = new String[]{"mkpath"};
      var7 = imp.importFrom("distutils.dir_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("mkpath", var4);
      var4 = null;
      var1.setline(17);
      var6 = new String[]{"newer_group"};
      var7 = imp.importFrom("distutils.dep_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("newer_group", var4);
      var4 = null;
      var1.setline(18);
      var6 = new String[]{"split_quoted", "execute"};
      var7 = imp.importFrom("distutils.util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("split_quoted", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("execute", var4);
      var4 = null;
      var1.setline(19);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(21);
      var6 = new String[]{"customize_compiler"};
      var7 = imp.importFrom("distutils.sysconfig", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("customize_compiler", var4);
      var4 = null;
      var1.setline(23);
      var7 = Py.EmptyObjects;
      var4 = Py.makeClass("CCompiler", var7, CCompiler$1);
      var1.setlocal("CCompiler", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(891);
      PyTuple var8 = new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("cygwin.*"), PyString.fromInterned("unix")}), new PyTuple(new PyObject[]{PyString.fromInterned("os2emx"), PyString.fromInterned("emx")}), new PyTuple(new PyObject[]{PyString.fromInterned("java.*"), PyString.fromInterned("jython")}), new PyTuple(new PyObject[]{PyString.fromInterned("posix"), PyString.fromInterned("unix")}), new PyTuple(new PyObject[]{PyString.fromInterned("nt"), PyString.fromInterned("msvc")})});
      var1.setlocal("_default_compilers", var8);
      var3 = null;
      var1.setline(907);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      PyFunction var9 = new PyFunction(var1.f_globals, var7, get_default_compiler$50, PyString.fromInterned(" Determine the default compiler to use for the given platform.\n\n        osname should be one of the standard Python OS names (i.e. the\n        ones returned by os.name) and platform the common value\n        returned by sys.platform for the platform in question.\n\n        The default values are os.name and sys.platform in case the\n        parameters are not given.\n\n    "));
      var1.setlocal("get_default_compiler", var9);
      var3 = null;
      var1.setline(932);
      PyDictionary var10 = new PyDictionary(new PyObject[]{PyString.fromInterned("unix"), new PyTuple(new PyObject[]{PyString.fromInterned("unixccompiler"), PyString.fromInterned("UnixCCompiler"), PyString.fromInterned("standard UNIX-style compiler")}), PyString.fromInterned("msvc"), new PyTuple(new PyObject[]{PyString.fromInterned("msvccompiler"), PyString.fromInterned("MSVCCompiler"), PyString.fromInterned("Microsoft Visual C++")}), PyString.fromInterned("cygwin"), new PyTuple(new PyObject[]{PyString.fromInterned("cygwinccompiler"), PyString.fromInterned("CygwinCCompiler"), PyString.fromInterned("Cygwin port of GNU C Compiler for Win32")}), PyString.fromInterned("mingw32"), new PyTuple(new PyObject[]{PyString.fromInterned("cygwinccompiler"), PyString.fromInterned("Mingw32CCompiler"), PyString.fromInterned("Mingw32 port of GNU C Compiler for Win32")}), PyString.fromInterned("bcpp"), new PyTuple(new PyObject[]{PyString.fromInterned("bcppcompiler"), PyString.fromInterned("BCPPCompiler"), PyString.fromInterned("Borland C++ Compiler")}), PyString.fromInterned("emx"), new PyTuple(new PyObject[]{PyString.fromInterned("emxccompiler"), PyString.fromInterned("EMXCCompiler"), PyString.fromInterned("EMX port of GNU C Compiler for OS/2")}), PyString.fromInterned("jython"), new PyTuple(new PyObject[]{PyString.fromInterned("jythoncompiler"), PyString.fromInterned("JythonCompiler"), PyString.fromInterned("Compiling is not supported on Jython")})});
      var1.setlocal("compiler_class", var10);
      var3 = null;
      var1.setline(948);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, show_compilers$51, PyString.fromInterned("Print list of available compilers (used by the \"--help-compiler\"\n    options to \"build\", \"build_ext\", \"build_clib\").\n    "));
      var1.setlocal("show_compilers", var9);
      var3 = null;
      var1.setline(965);
      var7 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      var9 = new PyFunction(var1.f_globals, var7, new_compiler$52, PyString.fromInterned("Generate an instance of some CCompiler subclass for the supplied\n    platform/compiler combination.  'plat' defaults to 'os.name'\n    (eg. 'posix', 'nt'), and 'compiler' defaults to the default compiler\n    for that platform.  Currently only 'posix' and 'nt' are supported, and\n    the default compilers are \"traditional Unix interface\" (UnixCCompiler\n    class) and Visual C++ (MSVCCompiler class).  Note that it's perfectly\n    possible to ask for a Unix compiler object under Windows, and a\n    Microsoft compiler object under Unix -- if you supply a value for\n    'compiler', 'plat' is ignored.\n    "));
      var1.setlocal("new_compiler", var9);
      var3 = null;
      var1.setline(1010);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, gen_preprocess_options$53, PyString.fromInterned("Generate C pre-processor options (-D, -U, -I) as used by at least\n    two types of compilers: the typical Unix compiler and Visual C++.\n    'macros' is the usual thing, a list of 1- or 2-tuples, where (name,)\n    means undefine (-U) macro 'name', and (name,value) means define (-D)\n    macro 'name' to 'value'.  'include_dirs' is just a list of directory\n    names to be added to the header file search path (-I).  Returns a list\n    of command-line options suitable for either Unix compilers or Visual\n    C++.\n    "));
      var1.setlocal("gen_preprocess_options", var9);
      var3 = null;
      var1.setline(1059);
      var7 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var7, gen_lib_options$54, PyString.fromInterned("Generate linker options for searching library directories and\n    linking with specific libraries.\n\n    'libraries' and 'library_dirs' are, respectively, lists of library names\n    (not filenames!) and search directories.  Returns a list of command-line\n    options suitable for use with some compiler (depending on the two format\n    strings passed in).\n    "));
      var1.setlocal("gen_lib_options", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject CCompiler$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Abstract base class to define the interface that must be implemented\n    by real compiler classes.  Also has some utility methods used by\n    several compiler classes.\n\n    The basic idea behind a compiler abstraction class is that each\n    instance can be used for all the compile/link steps in building a\n    single project.  Thus, attributes common to all of those compile and\n    link steps -- include directories, macros to define, libraries to link\n    against, etc. -- are attributes of the compiler instance.  To allow for\n    variability in how individual files are treated, most of those\n    attributes may be varied on a per-compilation or per-link basis.\n    "));
      var1.setline(35);
      PyString.fromInterned("Abstract base class to define the interface that must be implemented\n    by real compiler classes.  Also has some utility methods used by\n    several compiler classes.\n\n    The basic idea behind a compiler abstraction class is that each\n    instance can be used for all the compile/link steps in building a\n    single project.  Thus, attributes common to all of those compile and\n    link steps -- include directories, macros to define, libraries to link\n    against, etc. -- are attributes of the compiler instance.  To allow for\n    variability in how individual files are treated, most of those\n    attributes may be varied on a per-compilation or per-link basis.\n    ");
      var1.setline(45);
      PyObject var3 = var1.getname("None");
      var1.setlocal("compiler_type", var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getname("None");
      var1.setlocal("src_extensions", var3);
      var3 = null;
      var1.setline(72);
      var3 = var1.getname("None");
      var1.setlocal("obj_extension", var3);
      var3 = null;
      var1.setline(73);
      var3 = var1.getname("None");
      var1.setlocal("static_lib_extension", var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getname("None");
      var1.setlocal("shared_lib_extension", var3);
      var3 = null;
      var1.setline(75);
      var3 = var1.getname("None");
      var1.setlocal("static_lib_format", var3);
      var3 = null;
      var1.setline(76);
      var3 = var1.getname("None");
      var1.setlocal("shared_lib_format", var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getname("None");
      var1.setlocal("exe_extension", var3);
      var3 = null;
      var1.setline(85);
      PyDictionary var4 = new PyDictionary(new PyObject[]{PyString.fromInterned(".c"), PyString.fromInterned("c"), PyString.fromInterned(".cc"), PyString.fromInterned("c++"), PyString.fromInterned(".cpp"), PyString.fromInterned("c++"), PyString.fromInterned(".cxx"), PyString.fromInterned("c++"), PyString.fromInterned(".m"), PyString.fromInterned("objc")});
      var1.setlocal("language_map", var4);
      var3 = null;
      var1.setline(91);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("c++"), PyString.fromInterned("objc"), PyString.fromInterned("c")});
      var1.setlocal("language_order", var5);
      var3 = null;
      var1.setline(93);
      PyObject[] var6 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), Py.newInteger(0)};
      PyFunction var7 = new PyFunction(var1.f_globals, var6, __init__$2, (PyObject)null);
      var1.setlocal("__init__", var7);
      var3 = null;
      var1.setline(129);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_executables$3, PyString.fromInterned("Define the executables (and options for them) that will be run\n        to perform the various stages of compilation.  The exact set of\n        executables that may be specified here depends on the compiler\n        class (via the 'executables' class attribute), but most will have:\n          compiler      the C/C++ compiler\n          linker_so     linker used to create shared objects and libraries\n          linker_exe    linker used to create binary executables\n          archiver      static library creator\n\n        On platforms with a command-line (Unix, DOS/Windows), each of these\n        is a string that will be split into executable name and (optional)\n        list of arguments.  (Splitting the string is done similarly to how\n        Unix shells operate: words are delimited by spaces, but quotes and\n        backslashes can override this.  See\n        'distutils.util.split_quoted()'.)\n        "));
      var1.setlocal("set_executables", var7);
      var3 = null;
      var1.setline(162);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_executable$4, (PyObject)null);
      var1.setlocal("set_executable", var7);
      var3 = null;
      var1.setline(168);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _find_macro$5, (PyObject)null);
      var1.setlocal("_find_macro", var7);
      var3 = null;
      var1.setline(176);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _check_macro_definitions$6, PyString.fromInterned("Ensures that every element of 'definitions' is a valid macro\n        definition, ie. either (name,value) 2-tuple or a (name,) tuple.  Do\n        nothing if all definitions are OK, raise TypeError otherwise.\n        "));
      var1.setlocal("_check_macro_definitions", var7);
      var3 = null;
      var1.setline(195);
      var6 = new PyObject[]{var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, define_macro$7, PyString.fromInterned("Define a preprocessor macro for all compilations driven by this\n        compiler object.  The optional parameter 'value' should be a\n        string; if it is not supplied, then the macro will be defined\n        without an explicit value and the exact outcome depends on the\n        compiler used (XXX true? does ANSI say anything about this?)\n        "));
      var1.setlocal("define_macro", var7);
      var3 = null;
      var1.setline(211);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, undefine_macro$8, PyString.fromInterned("Undefine a preprocessor macro for all compilations driven by\n        this compiler object.  If the same macro is defined by\n        'define_macro()' and undefined by 'undefine_macro()' the last call\n        takes precedence (including multiple redefinitions or\n        undefinitions).  If the macro is redefined/undefined on a\n        per-compilation basis (ie. in the call to 'compile()'), then that\n        takes precedence.\n        "));
      var1.setlocal("undefine_macro", var7);
      var3 = null;
      var1.setline(229);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, add_include_dir$9, PyString.fromInterned("Add 'dir' to the list of directories that will be searched for\n        header files.  The compiler is instructed to search directories in\n        the order in which they are supplied by successive calls to\n        'add_include_dir()'.\n        "));
      var1.setlocal("add_include_dir", var7);
      var3 = null;
      var1.setline(237);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_include_dirs$10, PyString.fromInterned("Set the list of directories that will be searched to 'dirs' (a\n        list of strings).  Overrides any preceding calls to\n        'add_include_dir()'; subsequence calls to 'add_include_dir()' add\n        to the list passed to 'set_include_dirs()'.  This does not affect\n        any list of standard include directories that the compiler may\n        search by default.\n        "));
      var1.setlocal("set_include_dirs", var7);
      var3 = null;
      var1.setline(247);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, add_library$11, PyString.fromInterned("Add 'libname' to the list of libraries that will be included in\n        all links driven by this compiler object.  Note that 'libname'\n        should *not* be the name of a file containing a library, but the\n        name of the library itself: the actual filename will be inferred by\n        the linker, the compiler, or the compiler class (depending on the\n        platform).\n\n        The linker will be instructed to link against libraries in the\n        order they were supplied to 'add_library()' and/or\n        'set_libraries()'.  It is perfectly valid to duplicate library\n        names; the linker will be instructed to link against libraries as\n        many times as they are mentioned.\n        "));
      var1.setlocal("add_library", var7);
      var3 = null;
      var1.setline(263);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_libraries$12, PyString.fromInterned("Set the list of libraries to be included in all links driven by\n        this compiler object to 'libnames' (a list of strings).  This does\n        not affect any standard system libraries that the linker may\n        include by default.\n        "));
      var1.setlocal("set_libraries", var7);
      var3 = null;
      var1.setline(272);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, add_library_dir$13, PyString.fromInterned("Add 'dir' to the list of directories that will be searched for\n        libraries specified to 'add_library()' and 'set_libraries()'.  The\n        linker will be instructed to search for libraries in the order they\n        are supplied to 'add_library_dir()' and/or 'set_library_dirs()'.\n        "));
      var1.setlocal("add_library_dir", var7);
      var3 = null;
      var1.setline(280);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_library_dirs$14, PyString.fromInterned("Set the list of library search directories to 'dirs' (a list of\n        strings).  This does not affect any standard library search path\n        that the linker may search by default.\n        "));
      var1.setlocal("set_library_dirs", var7);
      var3 = null;
      var1.setline(287);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, add_runtime_library_dir$15, PyString.fromInterned("Add 'dir' to the list of directories that will be searched for\n        shared libraries at runtime.\n        "));
      var1.setlocal("add_runtime_library_dir", var7);
      var3 = null;
      var1.setline(293);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_runtime_library_dirs$16, PyString.fromInterned("Set the list of directories to search for shared libraries at\n        runtime to 'dirs' (a list of strings).  This does not affect any\n        standard search path that the runtime linker may search by\n        default.\n        "));
      var1.setlocal("set_runtime_library_dirs", var7);
      var3 = null;
      var1.setline(301);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, add_link_object$17, PyString.fromInterned("Add 'object' to the list of object files (or analogues, such as\n        explicitly named library files or the output of \"resource\n        compilers\") to be included in every link driven by this compiler\n        object.\n        "));
      var1.setlocal("add_link_object", var7);
      var3 = null;
      var1.setline(309);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, set_link_objects$18, PyString.fromInterned("Set the list of object files (or analogues) to be included in\n        every link to 'objects'.  This does not affect any standard object\n        files that the linker may include by default (such as system\n        libraries).\n        "));
      var1.setlocal("set_link_objects", var7);
      var3 = null;
      var1.setline(323);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _setup_compile$19, PyString.fromInterned("Process arguments and decide which source files to compile."));
      var1.setlocal("_setup_compile", var7);
      var3 = null;
      var1.setline(367);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _get_cc_args$20, (PyObject)null);
      var1.setlocal("_get_cc_args", var7);
      var3 = null;
      var1.setline(376);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _fix_compile_args$21, PyString.fromInterned("Typecheck and fix-up some of the arguments to the 'compile()'\n        method, and return fixed-up values.  Specifically: if 'output_dir'\n        is None, replaces it with 'self.output_dir'; ensures that 'macros'\n        is a list, and augments it with 'self.macros'; ensures that\n        'include_dirs' is a list, and augments it with 'self.include_dirs'.\n        Guarantees that the returned values are of the correct type,\n        i.e. for 'output_dir' either string or None, and for 'macros' and\n        'include_dirs' either list or None.\n        "));
      var1.setlocal("_fix_compile_args", var7);
      var3 = null;
      var1.setline(408);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _fix_object_args$22, PyString.fromInterned("Typecheck and fix up some arguments supplied to various methods.\n        Specifically: ensure that 'objects' is a list; if output_dir is\n        None, replace with self.output_dir.  Return fixed versions of\n        'objects' and 'output_dir'.\n        "));
      var1.setlocal("_fix_object_args", var7);
      var3 = null;
      var1.setline(426);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _fix_lib_args$23, PyString.fromInterned("Typecheck and fix up some of the arguments supplied to the\n        'link_*' methods.  Specifically: ensure that all arguments are\n        lists, and augment them with their permanent versions\n        (eg. 'self.libraries' augments 'libraries').  Return a tuple with\n        fixed versions of all arguments.\n        "));
      var1.setlocal("_fix_lib_args", var7);
      var3 = null;
      var1.setline(461);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _need_link$24, PyString.fromInterned("Return true if we need to relink the files listed in 'objects'\n        to recreate 'output_file'.\n        "));
      var1.setlocal("_need_link", var7);
      var3 = null;
      var1.setline(474);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, detect_language$25, PyString.fromInterned("Detect the language of a given file, or list of files. Uses\n        language_map, and language_order to do the job.\n        "));
      var1.setlocal("detect_language", var7);
      var3 = null;
      var1.setline(497);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, preprocess$26, PyString.fromInterned("Preprocess a single C/C++ source file, named in 'source'.\n        Output will be written to file named 'output_file', or stdout if\n        'output_file' not supplied.  'macros' is a list of macro\n        definitions as for 'compile()', which will augment the macros set\n        with 'define_macro()' and 'undefine_macro()'.  'include_dirs' is a\n        list of directory names that will be added to the default list.\n\n        Raises PreprocessError on failure.\n        "));
      var1.setlocal("preprocess", var7);
      var3 = null;
      var1.setline(510);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, compile$27, PyString.fromInterned("Compile one or more source files.\n\n        'sources' must be a list of filenames, most likely C/C++\n        files, but in reality anything that can be handled by a\n        particular compiler and compiler class (eg. MSVCCompiler can\n        handle resource files in 'sources').  Return a list of object\n        filenames, one per source filename in 'sources'.  Depending on\n        the implementation, not all source files will necessarily be\n        compiled, but all corresponding object filenames will be\n        returned.\n\n        If 'output_dir' is given, object files will be put under it, while\n        retaining their original path component.  That is, \"foo/bar.c\"\n        normally compiles to \"foo/bar.o\" (for a Unix implementation); if\n        'output_dir' is \"build\", then it would compile to\n        \"build/foo/bar.o\".\n\n        'macros', if given, must be a list of macro definitions.  A macro\n        definition is either a (name, value) 2-tuple or a (name,) 1-tuple.\n        The former defines a macro; if the value is None, the macro is\n        defined without an explicit value.  The 1-tuple case undefines a\n        macro.  Later definitions/redefinitions/ undefinitions take\n        precedence.\n\n        'include_dirs', if given, must be a list of strings, the\n        directories to add to the default include file search path for this\n        compilation only.\n\n        'debug' is a boolean; if true, the compiler will be instructed to\n        output debug symbols in (or alongside) the object file(s).\n\n        'extra_preargs' and 'extra_postargs' are implementation- dependent.\n        On platforms that have the notion of a command-line (e.g. Unix,\n        DOS/Windows), they are most likely lists of strings: extra\n        command-line arguments to prepand/append to the compiler command\n        line.  On other platforms, consult the implementation class\n        documentation.  In any event, they are intended as an escape hatch\n        for those occasions when the abstract compiler framework doesn't\n        cut the mustard.\n\n        'depends', if given, is a list of filenames that all targets\n        depend on.  If a source file is older than any file in\n        depends, then the source file will be recompiled.  This\n        supports dependency tracking, but only at a coarse\n        granularity.\n\n        Raises CompileError on failure.\n        "));
      var1.setlocal("compile", var7);
      var3 = null;
      var1.setline(579);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _compile$28, PyString.fromInterned("Compile 'src' to product 'obj'."));
      var1.setlocal("_compile", var7);
      var3 = null;
      var1.setline(586);
      var6 = new PyObject[]{var1.getname("None"), Py.newInteger(0), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, create_static_lib$29, PyString.fromInterned("Link a bunch of stuff together to create a static library file.\n        The \"bunch of stuff\" consists of the list of object files supplied\n        as 'objects', the extra object files supplied to\n        'add_link_object()' and/or 'set_link_objects()', the libraries\n        supplied to 'add_library()' and/or 'set_libraries()', and the\n        libraries supplied as 'libraries' (if any).\n\n        'output_libname' should be a library name, not a filename; the\n        filename will be inferred from the library name.  'output_dir' is\n        the directory where the library file will be put.\n\n        'debug' is a boolean; if true, debugging information will be\n        included in the library (note that on most platforms, it is the\n        compile step where this matters: the 'debug' flag is included here\n        just for consistency).\n\n        'target_lang' is the target language for which the given objects\n        are being compiled. This allows specific linkage time treatment of\n        certain languages.\n\n        Raises LibError on failure.\n        "));
      var1.setlocal("create_static_lib", var7);
      var3 = null;
      var1.setline(613);
      PyString var8 = PyString.fromInterned("shared_object");
      var1.setlocal("SHARED_OBJECT", var8);
      var3 = null;
      var1.setline(614);
      var8 = PyString.fromInterned("shared_library");
      var1.setlocal("SHARED_LIBRARY", var8);
      var3 = null;
      var1.setline(615);
      var8 = PyString.fromInterned("executable");
      var1.setlocal("EXECUTABLE", var8);
      var3 = null;
      var1.setline(617);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, link$30, PyString.fromInterned("Link a bunch of stuff together to create an executable or\n        shared library file.\n\n        The \"bunch of stuff\" consists of the list of object files supplied\n        as 'objects'.  'output_filename' should be a filename.  If\n        'output_dir' is supplied, 'output_filename' is relative to it\n        (i.e. 'output_filename' can provide directory components if\n        needed).\n\n        'libraries' is a list of libraries to link against.  These are\n        library names, not filenames, since they're translated into\n        filenames in a platform-specific way (eg. \"foo\" becomes \"libfoo.a\"\n        on Unix and \"foo.lib\" on DOS/Windows).  However, they can include a\n        directory component, which means the linker will look in that\n        specific directory rather than searching all the normal locations.\n\n        'library_dirs', if supplied, should be a list of directories to\n        search for libraries that were specified as bare library names\n        (ie. no directory component).  These are on top of the system\n        default and those supplied to 'add_library_dir()' and/or\n        'set_library_dirs()'.  'runtime_library_dirs' is a list of\n        directories that will be embedded into the shared library and used\n        to search for other shared libraries that *it* depends on at\n        run-time.  (This may only be relevant on Unix.)\n\n        'export_symbols' is a list of symbols that the shared library will\n        export.  (This appears to be relevant only on Windows.)\n\n        'debug' is as for 'compile()' and 'create_static_lib()', with the\n        slight distinction that it actually matters on most platforms (as\n        opposed to 'create_static_lib()', which includes a 'debug' flag\n        mostly for form's sake).\n\n        'extra_preargs' and 'extra_postargs' are as for 'compile()' (except\n        of course that they supply command-line arguments for the\n        particular linker being used).\n\n        'target_lang' is the target language for which the given objects\n        are being compiled. This allows specific linkage time treatment of\n        certain languages.\n\n        Raises LinkError on failure.\n        "));
      var1.setlocal("link", var7);
      var3 = null;
      var1.setline(669);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, link_shared_lib$31, (PyObject)null);
      var1.setlocal("link_shared_lib", var7);
      var3 = null;
      var1.setline(682);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, link_shared_object$32, (PyObject)null);
      var1.setlocal("link_shared_object", var7);
      var3 = null;
      var1.setline(693);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, link_executable$33, (PyObject)null);
      var1.setlocal("link_executable", var7);
      var3 = null;
      var1.setline(708);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, library_dir_option$34, PyString.fromInterned("Return the compiler option to add 'dir' to the list of\n        directories searched for libraries.\n        "));
      var1.setlocal("library_dir_option", var7);
      var3 = null;
      var1.setline(714);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, runtime_library_dir_option$35, PyString.fromInterned("Return the compiler option to add 'dir' to the list of\n        directories searched for runtime libraries.\n        "));
      var1.setlocal("runtime_library_dir_option", var7);
      var3 = null;
      var1.setline(720);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, library_option$36, PyString.fromInterned("Return the compiler option to add 'dir' to the list of libraries\n        linked into the shared library or executable.\n        "));
      var1.setlocal("library_option", var7);
      var3 = null;
      var1.setline(726);
      var6 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var7 = new PyFunction(var1.f_globals, var6, has_function$37, PyString.fromInterned("Return a boolean indicating whether funcname is supported on\n        the current platform.  The optional arguments can be used to\n        augment the compilation environment.\n        "));
      var1.setlocal("has_function", var7);
      var3 = null;
      var1.setline(770);
      var6 = new PyObject[]{Py.newInteger(0)};
      var7 = new PyFunction(var1.f_globals, var6, find_library_file$38, PyString.fromInterned("Search the specified list of directories for a static or shared\n        library file 'lib' and return the full path to that file.  If\n        'debug' true, look for a debugging version (if that makes sense on\n        the current platform).  Return None if 'lib' wasn't found in any of\n        the specified directories.\n        "));
      var1.setlocal("find_library_file", var7);
      var3 = null;
      var1.setline(813);
      var6 = new PyObject[]{Py.newInteger(0), PyString.fromInterned("")};
      var7 = new PyFunction(var1.f_globals, var6, object_filenames$39, (PyObject)null);
      var1.setlocal("object_filenames", var7);
      var3 = null;
      var1.setline(830);
      var6 = new PyObject[]{Py.newInteger(0), PyString.fromInterned("")};
      var7 = new PyFunction(var1.f_globals, var6, shared_object_filename$40, (PyObject)null);
      var1.setlocal("shared_object_filename", var7);
      var3 = null;
      var1.setline(836);
      var6 = new PyObject[]{Py.newInteger(0), PyString.fromInterned("")};
      var7 = new PyFunction(var1.f_globals, var6, executable_filename$41, (PyObject)null);
      var1.setlocal("executable_filename", var7);
      var3 = null;
      var1.setline(842);
      var6 = new PyObject[]{PyString.fromInterned("static"), Py.newInteger(0), PyString.fromInterned("")};
      var7 = new PyFunction(var1.f_globals, var6, library_filename$42, (PyObject)null);
      var1.setlocal("library_filename", var7);
      var3 = null;
      var1.setline(860);
      var6 = new PyObject[]{Py.newInteger(1)};
      var7 = new PyFunction(var1.f_globals, var6, announce$43, (PyObject)null);
      var1.setlocal("announce", var7);
      var3 = null;
      var1.setline(863);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, debug_print$44, (PyObject)null);
      var1.setlocal("debug_print", var7);
      var3 = null;
      var1.setline(868);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, warn$45, (PyObject)null);
      var1.setlocal("warn", var7);
      var3 = null;
      var1.setline(871);
      var6 = new PyObject[]{var1.getname("None"), Py.newInteger(1)};
      var7 = new PyFunction(var1.f_globals, var6, execute$46, (PyObject)null);
      var1.setlocal("execute", var7);
      var3 = null;
      var1.setline(874);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, spawn$47, (PyObject)null);
      var1.setlocal("spawn", var7);
      var3 = null;
      var1.setline(877);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, move_file$48, (PyObject)null);
      var1.setlocal("move_file", var7);
      var3 = null;
      var1.setline(880);
      var6 = new PyObject[]{Py.newInteger(511)};
      var7 = new PyFunction(var1.f_globals, var6, mkpath$49, (PyObject)null);
      var1.setlocal("mkpath", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(94);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("dry_run", var3);
      var3 = null;
      var1.setline(95);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("force", var3);
      var3 = null;
      var1.setline(96);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("verbose", var3);
      var3 = null;
      var1.setline(100);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("output_dir", var3);
      var3 = null;
      var1.setline(106);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"macros", var5);
      var3 = null;
      var1.setline(109);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"include_dirs", var5);
      var3 = null;
      var1.setline(113);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"libraries", var5);
      var3 = null;
      var1.setline(116);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"library_dirs", var5);
      var3 = null;
      var1.setline(120);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"runtime_library_dirs", var5);
      var3 = null;
      var1.setline(124);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"objects", var5);
      var3 = null;
      var1.setline(126);
      var3 = var1.getlocal(0).__getattr__("executables").__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(126);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(4, var4);
         var1.setline(127);
         var1.getlocal(0).__getattr__("set_executable").__call__(var2, var1.getlocal(4), var1.getlocal(0).__getattr__("executables").__getitem__(var1.getlocal(4)));
      }
   }

   public PyObject set_executables$3(PyFrame var1, ThreadState var2) {
      var1.setline(145);
      PyString.fromInterned("Define the executables (and options for them) that will be run\n        to perform the various stages of compilation.  The exact set of\n        executables that may be specified here depends on the compiler\n        class (via the 'executables' class attribute), but most will have:\n          compiler      the C/C++ compiler\n          linker_so     linker used to create shared objects and libraries\n          linker_exe    linker used to create binary executables\n          archiver      static library creator\n\n        On platforms with a command-line (Unix, DOS/Windows), each of these\n        is a string that will be split into executable name and (optional)\n        list of arguments.  (Splitting the string is done similarly to how\n        Unix shells operate: words are delimited by spaces, but quotes and\n        backslashes can override this.  See\n        'distutils.util.split_quoted()'.)\n        ");
      var1.setline(155);
      PyObject var3 = var1.getlocal(1).__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(155);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(156);
         PyObject var5 = var1.getlocal(2);
         PyObject var10000 = var5._notin(var1.getlocal(0).__getattr__("executables"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(157);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("unknown executable '%s' for class %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(0).__getattr__("__class__").__getattr__("__name__")})));
         }

         var1.setline(160);
         var1.getlocal(0).__getattr__("set_executable").__call__(var2, var1.getlocal(2), var1.getlocal(1).__getitem__(var1.getlocal(2)));
      }
   }

   public PyObject set_executable$4(PyFrame var1, ThreadState var2) {
      var1.setline(163);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("str")).__nonzero__()) {
         var1.setline(164);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getglobal("split_quoted").__call__(var2, var1.getlocal(2)));
      } else {
         var1.setline(166);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _find_macro$5(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyInteger var3 = Py.newInteger(0);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(170);
      PyObject var7 = var1.getlocal(0).__getattr__("macros").__iter__();

      while(true) {
         var1.setline(170);
         PyObject var4 = var7.__iternext__();
         PyObject var5;
         if (var4 == null) {
            var1.setline(174);
            var5 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(3, var4);
         var1.setline(171);
         var5 = var1.getlocal(3).__getitem__(Py.newInteger(0));
         PyObject var10000 = var5._eq(var1.getlocal(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(172);
            var5 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var5;
         }

         var1.setline(173);
         PyObject var6 = var1.getlocal(2)._add(Py.newInteger(1));
         var1.setlocal(2, var6);
         var6 = null;
      }
   }

   public PyObject _check_macro_definitions$6(PyFrame var1, ThreadState var2) {
      var1.setline(180);
      PyString.fromInterned("Ensures that every element of 'definitions' is a valid macro\n        definition, ie. either (name,value) 2-tuple or a (name,) tuple.  Do\n        nothing if all definitions are OK, raise TypeError otherwise.\n        ");
      var1.setline(181);
      PyObject var3 = var1.getlocal(1).__iter__();

      PyObject var10000;
      do {
         var1.setline(181);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(182);
         var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("tuple"));
         if (var10000.__nonzero__()) {
            PyObject var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var5._eq(Py.newInteger(1));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var5 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
               var10000 = var5._eq(Py.newInteger(2));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(1)), var1.getglobal("str"));
                  if (!var10000.__nonzero__()) {
                     var5 = var1.getlocal(2).__getitem__(Py.newInteger(1));
                     var10000 = var5._is(var1.getglobal("None"));
                     var5 = null;
                  }
               }
            }

            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)), var1.getglobal("str"));
            }
         }
      } while(!var10000.__not__().__nonzero__());

      var1.setline(187);
      throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("invalid macro definition '%s': ")._mod(var1.getlocal(2))._add(PyString.fromInterned("must be tuple (string,), (string, string), or "))._add(PyString.fromInterned("(string, None)")));
   }

   public PyObject define_macro$7(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyString.fromInterned("Define a preprocessor macro for all compilations driven by this\n        compiler object.  The optional parameter 'value' should be a\n        string; if it is not supplied, then the macro will be defined\n        without an explicit value and the exact outcome depends on the\n        compiler used (XXX true? does ANSI say anything about this?)\n        ");
      var1.setline(204);
      PyObject var3 = var1.getlocal(0).__getattr__("_find_macro").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(205);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(206);
         var1.getlocal(0).__getattr__("macros").__delitem__(var1.getlocal(3));
      }

      var1.setline(208);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(209);
      var1.getlocal(0).__getattr__("macros").__getattr__("append").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject undefine_macro$8(PyFrame var1, ThreadState var2) {
      var1.setline(219);
      PyString.fromInterned("Undefine a preprocessor macro for all compilations driven by\n        this compiler object.  If the same macro is defined by\n        'define_macro()' and undefined by 'undefine_macro()' the last call\n        takes precedence (including multiple redefinitions or\n        undefinitions).  If the macro is redefined/undefined on a\n        per-compilation basis (ie. in the call to 'compile()'), then that\n        takes precedence.\n        ");
      var1.setline(222);
      PyObject var3 = var1.getlocal(0).__getattr__("_find_macro").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(223);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(224);
         var1.getlocal(0).__getattr__("macros").__delitem__(var1.getlocal(2));
      }

      var1.setline(226);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1)});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(227);
      var1.getlocal(0).__getattr__("macros").__getattr__("append").__call__(var2, var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_include_dir$9(PyFrame var1, ThreadState var2) {
      var1.setline(234);
      PyString.fromInterned("Add 'dir' to the list of directories that will be searched for\n        header files.  The compiler is instructed to search directories in\n        the order in which they are supplied by successive calls to\n        'add_include_dir()'.\n        ");
      var1.setline(235);
      var1.getlocal(0).__getattr__("include_dirs").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_include_dirs$10(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyString.fromInterned("Set the list of directories that will be searched to 'dirs' (a\n        list of strings).  Overrides any preceding calls to\n        'add_include_dir()'; subsequence calls to 'add_include_dir()' add\n        to the list passed to 'set_include_dirs()'.  This does not affect\n        any list of standard include directories that the compiler may\n        search by default.\n        ");
      var1.setline(245);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("include_dirs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_library$11(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyString.fromInterned("Add 'libname' to the list of libraries that will be included in\n        all links driven by this compiler object.  Note that 'libname'\n        should *not* be the name of a file containing a library, but the\n        name of the library itself: the actual filename will be inferred by\n        the linker, the compiler, or the compiler class (depending on the\n        platform).\n\n        The linker will be instructed to link against libraries in the\n        order they were supplied to 'add_library()' and/or\n        'set_libraries()'.  It is perfectly valid to duplicate library\n        names; the linker will be instructed to link against libraries as\n        many times as they are mentioned.\n        ");
      var1.setline(261);
      var1.getlocal(0).__getattr__("libraries").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_libraries$12(PyFrame var1, ThreadState var2) {
      var1.setline(268);
      PyString.fromInterned("Set the list of libraries to be included in all links driven by\n        this compiler object to 'libnames' (a list of strings).  This does\n        not affect any standard system libraries that the linker may\n        include by default.\n        ");
      var1.setline(269);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("libraries", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_library_dir$13(PyFrame var1, ThreadState var2) {
      var1.setline(277);
      PyString.fromInterned("Add 'dir' to the list of directories that will be searched for\n        libraries specified to 'add_library()' and 'set_libraries()'.  The\n        linker will be instructed to search for libraries in the order they\n        are supplied to 'add_library_dir()' and/or 'set_library_dirs()'.\n        ");
      var1.setline(278);
      var1.getlocal(0).__getattr__("library_dirs").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_library_dirs$14(PyFrame var1, ThreadState var2) {
      var1.setline(284);
      PyString.fromInterned("Set the list of library search directories to 'dirs' (a list of\n        strings).  This does not affect any standard library search path\n        that the linker may search by default.\n        ");
      var1.setline(285);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("library_dirs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_runtime_library_dir$15(PyFrame var1, ThreadState var2) {
      var1.setline(290);
      PyString.fromInterned("Add 'dir' to the list of directories that will be searched for\n        shared libraries at runtime.\n        ");
      var1.setline(291);
      var1.getlocal(0).__getattr__("runtime_library_dirs").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_runtime_library_dirs$16(PyFrame var1, ThreadState var2) {
      var1.setline(298);
      PyString.fromInterned("Set the list of directories to search for shared libraries at\n        runtime to 'dirs' (a list of strings).  This does not affect any\n        standard search path that the runtime linker may search by\n        default.\n        ");
      var1.setline(299);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("runtime_library_dirs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_link_object$17(PyFrame var1, ThreadState var2) {
      var1.setline(306);
      PyString.fromInterned("Add 'object' to the list of object files (or analogues, such as\n        explicitly named library files or the output of \"resource\n        compilers\") to be included in every link driven by this compiler\n        object.\n        ");
      var1.setline(307);
      var1.getlocal(0).__getattr__("objects").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject set_link_objects$18(PyFrame var1, ThreadState var2) {
      var1.setline(314);
      PyString.fromInterned("Set the list of object files (or analogues) to be included in\n        every link to 'objects'.  This does not affect any standard object\n        files that the linker may include by default (such as system\n        libraries).\n        ");
      var1.setline(315);
      PyObject var3 = var1.getlocal(1).__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
      var1.getlocal(0).__setattr__("objects", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _setup_compile$19(PyFrame var1, ThreadState var2) {
      var1.setline(325);
      PyString.fromInterned("Process arguments and decide which source files to compile.");
      var1.setline(326);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(327);
         var3 = var1.getlocal(0).__getattr__("output_dir");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(328);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__not__().__nonzero__()) {
            var1.setline(329);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'output_dir' must be a string or None"));
         }
      }

      var1.setline(331);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      Object var10001;
      if (var10000.__nonzero__()) {
         var1.setline(332);
         var3 = var1.getlocal(0).__getattr__("macros");
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(333);
         if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("list")).__nonzero__()) {
            var1.setline(336);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'macros' (if supplied) must be a list of tuples"));
         }

         var1.setline(334);
         var10000 = var1.getlocal(2);
         var10001 = var1.getlocal(0).__getattr__("macros");
         if (!((PyObject)var10001).__nonzero__()) {
            var10001 = new PyList(Py.EmptyObjects);
         }

         var3 = var10000._add((PyObject)var10001);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(338);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(339);
         var3 = var1.getlocal(0).__getattr__("include_dirs");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(340);
         if (!var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__nonzero__()) {
            var1.setline(343);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'include_dirs' (if supplied) must be a list of strings"));
         }

         var1.setline(341);
         var10000 = var1.getglobal("list").__call__(var2, var1.getlocal(3));
         var10001 = var1.getlocal(0).__getattr__("include_dirs");
         if (!((PyObject)var10001).__nonzero__()) {
            var10001 = new PyList(Py.EmptyObjects);
         }

         var3 = var10000._add((PyObject)var10001);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(346);
      var3 = var1.getlocal(6);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(347);
         PyList var8 = new PyList(Py.EmptyObjects);
         var1.setlocal(6, var8);
         var3 = null;
      }

      var1.setline(350);
      var10000 = var1.getlocal(0).__getattr__("object_filenames");
      PyObject[] var9 = new PyObject[]{var1.getlocal(4), Py.newInteger(0), var1.getlocal(1)};
      String[] var4 = new String[]{"strip_dir", "output_dir"};
      var10000 = var10000.__call__(var2, var9, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(353);
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
         var10000 = var3._eq(var1.getglobal("len").__call__(var2, var1.getlocal(4)));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(355);
      var3 = var1.getglobal("gen_preprocess_options").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(357);
      PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(9, var10);
      var3 = null;
      var1.setline(358);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(4))).__iter__();

      while(true) {
         var1.setline(358);
         PyObject var6 = var3.__iternext__();
         if (var6 == null) {
            var1.setline(365);
            PyTuple var11 = new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(7), var1.getlocal(6), var1.getlocal(8), var1.getlocal(9)});
            var1.f_lasti = -1;
            return var11;
         }

         var1.setlocal(10, var6);
         var1.setline(359);
         PyObject var5 = var1.getlocal(4).__getitem__(var1.getlocal(10));
         var1.setlocal(11, var5);
         var5 = null;
         var1.setline(360);
         var5 = var1.getlocal(7).__getitem__(var1.getlocal(10));
         var1.setlocal(12, var5);
         var5 = null;
         var1.setline(361);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(11)).__getitem__(Py.newInteger(1));
         var1.setlocal(13, var5);
         var5 = null;
         var1.setline(362);
         var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(12)));
         var1.setline(363);
         PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(13)});
         var1.getlocal(9).__setitem__((PyObject)var1.getlocal(12), var7);
         var5 = null;
      }
   }

   public PyObject _get_cc_args$20(PyFrame var1, ThreadState var2) {
      var1.setline(369);
      PyObject var3 = var1.getlocal(1)._add(new PyList(new PyObject[]{PyString.fromInterned("-c")}));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(370);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(371);
         PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("-g")});
         var1.getlocal(4).__setslice__((PyObject)null, Py.newInteger(0), (PyObject)null, var4);
         var3 = null;
      }

      var1.setline(372);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(373);
         var3 = var1.getlocal(3);
         var1.getlocal(4).__setslice__((PyObject)null, Py.newInteger(0), (PyObject)null, var3);
         var3 = null;
      }

      var1.setline(374);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _fix_compile_args$21(PyFrame var1, ThreadState var2) {
      var1.setline(385);
      PyString.fromInterned("Typecheck and fix-up some of the arguments to the 'compile()'\n        method, and return fixed-up values.  Specifically: if 'output_dir'\n        is None, replaces it with 'self.output_dir'; ensures that 'macros'\n        is a list, and augments it with 'self.macros'; ensures that\n        'include_dirs' is a list, and augments it with 'self.include_dirs'.\n        Guarantees that the returned values are of the correct type,\n        i.e. for 'output_dir' either string or None, and for 'macros' and\n        'include_dirs' either list or None.\n        ");
      var1.setline(386);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(387);
         var3 = var1.getlocal(0).__getattr__("output_dir");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(388);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__not__().__nonzero__()) {
            var1.setline(389);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'output_dir' must be a string or None"));
         }
      }

      var1.setline(391);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      Object var10001;
      if (var10000.__nonzero__()) {
         var1.setline(392);
         var3 = var1.getlocal(0).__getattr__("macros");
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(393);
         if (!var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("list")).__nonzero__()) {
            var1.setline(396);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'macros' (if supplied) must be a list of tuples"));
         }

         var1.setline(394);
         var10000 = var1.getlocal(2);
         var10001 = var1.getlocal(0).__getattr__("macros");
         if (!((PyObject)var10001).__nonzero__()) {
            var10001 = new PyList(Py.EmptyObjects);
         }

         var3 = var10000._add((PyObject)var10001);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(398);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(399);
         var3 = var1.getlocal(0).__getattr__("include_dirs");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(400);
         if (!var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__nonzero__()) {
            var1.setline(403);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'include_dirs' (if supplied) must be a list of strings"));
         }

         var1.setline(401);
         var10000 = var1.getglobal("list").__call__(var2, var1.getlocal(3));
         var10001 = var1.getlocal(0).__getattr__("include_dirs");
         if (!((PyObject)var10001).__nonzero__()) {
            var10001 = new PyList(Py.EmptyObjects);
         }

         var3 = var10000._add((PyObject)var10001);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(406);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _fix_object_args$22(PyFrame var1, ThreadState var2) {
      var1.setline(413);
      PyString.fromInterned("Typecheck and fix up some arguments supplied to various methods.\n        Specifically: ensure that 'objects' is a list; if output_dir is\n        None, replace with self.output_dir.  Return fixed versions of\n        'objects' and 'output_dir'.\n        ");
      var1.setline(414);
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__not__().__nonzero__()) {
         var1.setline(415);
         throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'objects' must be a list or tuple of strings"));
      } else {
         var1.setline(417);
         PyObject var3 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(419);
         var3 = var1.getlocal(2);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(420);
            var3 = var1.getlocal(0).__getattr__("output_dir");
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(421);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("str")).__not__().__nonzero__()) {
               var1.setline(422);
               throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'output_dir' must be a string or None"));
            }
         }

         var1.setline(424);
         PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)});
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject _fix_lib_args$23(PyFrame var1, ThreadState var2) {
      var1.setline(432);
      PyString.fromInterned("Typecheck and fix up some of the arguments supplied to the\n        'link_*' methods.  Specifically: ensure that all arguments are\n        lists, and augment them with their permanent versions\n        (eg. 'self.libraries' augments 'libraries').  Return a tuple with\n        fixed versions of all arguments.\n        ");
      var1.setline(433);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      Object var10001;
      if (var10000.__nonzero__()) {
         var1.setline(434);
         var3 = var1.getlocal(0).__getattr__("libraries");
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(435);
         if (!var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__nonzero__()) {
            var1.setline(438);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'libraries' (if supplied) must be a list of strings"));
         }

         var1.setline(436);
         var10000 = var1.getglobal("list").__call__(var2, var1.getlocal(1));
         var10001 = var1.getlocal(0).__getattr__("libraries");
         if (!((PyObject)var10001).__nonzero__()) {
            var10001 = new PyList(Py.EmptyObjects);
         }

         var3 = var10000._add((PyObject)var10001);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(441);
      var3 = var1.getlocal(2);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(442);
         var3 = var1.getlocal(0).__getattr__("library_dirs");
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(443);
         if (!var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__nonzero__()) {
            var1.setline(446);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'library_dirs' (if supplied) must be a list of strings"));
         }

         var1.setline(444);
         var10000 = var1.getglobal("list").__call__(var2, var1.getlocal(2));
         var10001 = var1.getlocal(0).__getattr__("library_dirs");
         if (!((PyObject)var10001).__nonzero__()) {
            var10001 = new PyList(Py.EmptyObjects);
         }

         var3 = var10000._add((PyObject)var10001);
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(449);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(450);
         var3 = var1.getlocal(0).__getattr__("runtime_library_dirs");
         var1.setlocal(3, var3);
         var3 = null;
      } else {
         var1.setline(451);
         if (!var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__nonzero__()) {
            var1.setline(455);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("'runtime_library_dirs' (if supplied) ")._add(PyString.fromInterned("must be a list of strings")));
         }

         var1.setline(452);
         var10000 = var1.getglobal("list").__call__(var2, var1.getlocal(3));
         var10001 = var1.getlocal(0).__getattr__("runtime_library_dirs");
         if (!((PyObject)var10001).__nonzero__()) {
            var10001 = new PyList(Py.EmptyObjects);
         }

         var3 = var10000._add((PyObject)var10001);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(459);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)});
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject _need_link$24(PyFrame var1, ThreadState var2) {
      var1.setline(464);
      PyString.fromInterned("Return true if we need to relink the files listed in 'objects'\n        to recreate 'output_file'.\n        ");
      var1.setline(465);
      if (var1.getlocal(0).__getattr__("force").__nonzero__()) {
         var1.setline(466);
         PyInteger var6 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var6;
      } else {
         var1.setline(468);
         PyObject[] var4;
         PyObject var7;
         if (var1.getlocal(0).__getattr__("dry_run").__nonzero__()) {
            var1.setline(469);
            PyObject var10000 = var1.getglobal("newer_group");
            var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), PyString.fromInterned("newer")};
            String[] var5 = new String[]{"missing"};
            var10000 = var10000.__call__(var2, var4, var5);
            var4 = null;
            var7 = var10000;
            var1.setlocal(3, var7);
            var4 = null;
         } else {
            var1.setline(471);
            var7 = var1.getglobal("newer_group").__call__(var2, var1.getlocal(1), var1.getlocal(2));
            var1.setlocal(3, var7);
            var4 = null;
         }

         var1.setline(472);
         PyObject var3 = var1.getlocal(3);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject detect_language$25(PyFrame var1, ThreadState var2) {
      var1.setline(477);
      PyString.fromInterned("Detect the language of a given file, or list of files. Uses\n        language_map, and language_order to do the job.\n        ");
      var1.setline(478);
      PyList var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("list")).__not__().__nonzero__()) {
         var1.setline(479);
         var3 = new PyList(new PyObject[]{var1.getlocal(1)});
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(480);
      PyObject var9 = var1.getglobal("None");
      var1.setlocal(2, var9);
      var3 = null;
      var1.setline(481);
      var9 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("language_order"));
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(482);
      var9 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(482);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(492);
            var9 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var9;
         }

         var1.setlocal(4, var4);
         var1.setline(483);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(4));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(5, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(6, var7);
         var7 = null;
         var5 = null;
         var1.setline(484);
         var5 = var1.getlocal(0).__getattr__("language_map").__getattr__("get").__call__(var2, var1.getlocal(6));
         var1.setlocal(7, var5);
         var5 = null;

         try {
            var1.setline(486);
            var5 = var1.getlocal(0).__getattr__("language_order").__getattr__("index").__call__(var2, var1.getlocal(7));
            var1.setlocal(8, var5);
            var5 = null;
            var1.setline(487);
            var5 = var1.getlocal(8);
            PyObject var10000 = var5._lt(var1.getlocal(3));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(488);
               var5 = var1.getlocal(7);
               var1.setlocal(2, var5);
               var5 = null;
               var1.setline(489);
               var5 = var1.getlocal(8);
               var1.setlocal(3, var5);
               var5 = null;
            }
         } catch (Throwable var8) {
            PyException var10 = Py.setException(var8, var1);
            if (!var10.match(var1.getglobal("ValueError"))) {
               throw var10;
            }

            var1.setline(491);
         }
      }
   }

   public PyObject preprocess$26(PyFrame var1, ThreadState var2) {
      var1.setline(507);
      PyString.fromInterned("Preprocess a single C/C++ source file, named in 'source'.\n        Output will be written to file named 'output_file', or stdout if\n        'output_file' not supplied.  'macros' is a list of macro\n        definitions as for 'compile()', which will augment the macros set\n        with 'define_macro()' and 'undefine_macro()'.  'include_dirs' is a\n        list of directory names that will be added to the default list.\n\n        Raises PreprocessError on failure.\n        ");
      var1.setline(508);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject compile$27(PyFrame var1, ThreadState var2) {
      var1.setline(560);
      PyString.fromInterned("Compile one or more source files.\n\n        'sources' must be a list of filenames, most likely C/C++\n        files, but in reality anything that can be handled by a\n        particular compiler and compiler class (eg. MSVCCompiler can\n        handle resource files in 'sources').  Return a list of object\n        filenames, one per source filename in 'sources'.  Depending on\n        the implementation, not all source files will necessarily be\n        compiled, but all corresponding object filenames will be\n        returned.\n\n        If 'output_dir' is given, object files will be put under it, while\n        retaining their original path component.  That is, \"foo/bar.c\"\n        normally compiles to \"foo/bar.o\" (for a Unix implementation); if\n        'output_dir' is \"build\", then it would compile to\n        \"build/foo/bar.o\".\n\n        'macros', if given, must be a list of macro definitions.  A macro\n        definition is either a (name, value) 2-tuple or a (name,) 1-tuple.\n        The former defines a macro; if the value is None, the macro is\n        defined without an explicit value.  The 1-tuple case undefines a\n        macro.  Later definitions/redefinitions/ undefinitions take\n        precedence.\n\n        'include_dirs', if given, must be a list of strings, the\n        directories to add to the default include file search path for this\n        compilation only.\n\n        'debug' is a boolean; if true, the compiler will be instructed to\n        output debug symbols in (or alongside) the object file(s).\n\n        'extra_preargs' and 'extra_postargs' are implementation- dependent.\n        On platforms that have the notion of a command-line (e.g. Unix,\n        DOS/Windows), they are most likely lists of strings: extra\n        command-line arguments to prepand/append to the compiler command\n        line.  On other platforms, consult the implementation class\n        documentation.  In any event, they are intended as an escape hatch\n        for those occasions when the abstract compiler framework doesn't\n        cut the mustard.\n\n        'depends', if given, is a list of filenames that all targets\n        depend on.  If a source file is older than any file in\n        depends, then the source file will be recompiled.  This\n        supports dependency tracking, but only at a coarse\n        granularity.\n\n        Raises CompileError on failure.\n        ");
      var1.setline(564);
      PyObject var10000 = var1.getlocal(0).__getattr__("_setup_compile");
      PyObject[] var3 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(1), var1.getlocal(8), var1.getlocal(7)};
      PyObject var9 = var10000.__call__(var2, var3);
      PyObject[] var4 = Py.unpackSequence(var9, 5);
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
      var1.setline(567);
      var9 = var1.getlocal(0).__getattr__("_get_cc_args").__call__(var2, var1.getlocal(10), var1.getlocal(5), var1.getlocal(6));
      var1.setlocal(12, var9);
      var3 = null;
      var1.setline(569);
      var9 = var1.getlocal(9).__iter__();

      while(true) {
         var1.setline(569);
         PyObject var10 = var9.__iternext__();
         if (var10 == null) {
            var1.setline(577);
            var9 = var1.getlocal(9);
            var1.f_lasti = -1;
            return var9;
         }

         var1.setlocal(13, var10);

         try {
            var1.setline(571);
            var5 = var1.getlocal(11).__getitem__(var1.getlocal(13));
            PyObject[] var6 = Py.unpackSequence(var5, 2);
            PyObject var7 = var6[0];
            var1.setlocal(14, var7);
            var7 = null;
            var7 = var6[1];
            var1.setlocal(15, var7);
            var7 = null;
            var5 = null;
         } catch (Throwable var8) {
            PyException var11 = Py.setException(var8, var1);
            if (var11.match(var1.getglobal("KeyError"))) {
               continue;
            }

            throw var11;
         }

         var1.setline(574);
         var10000 = var1.getlocal(0).__getattr__("_compile");
         PyObject[] var12 = new PyObject[]{var1.getlocal(13), var1.getlocal(14), var1.getlocal(15), var1.getlocal(12), var1.getlocal(7), var1.getlocal(10)};
         var10000.__call__(var2, var12);
      }
   }

   public PyObject _compile$28(PyFrame var1, ThreadState var2) {
      var1.setline(580);
      PyString.fromInterned("Compile 'src' to product 'obj'.");
      var1.setline(584);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject create_static_lib$29(PyFrame var1, ThreadState var2) {
      var1.setline(609);
      PyString.fromInterned("Link a bunch of stuff together to create a static library file.\n        The \"bunch of stuff\" consists of the list of object files supplied\n        as 'objects', the extra object files supplied to\n        'add_link_object()' and/or 'set_link_objects()', the libraries\n        supplied to 'add_library()' and/or 'set_libraries()', and the\n        libraries supplied as 'libraries' (if any).\n\n        'output_libname' should be a library name, not a filename; the\n        filename will be inferred from the library name.  'output_dir' is\n        the directory where the library file will be put.\n\n        'debug' is a boolean; if true, debugging information will be\n        included in the library (note that on most platforms, it is the\n        compile step where this matters: the 'debug' flag is included here\n        just for consistency).\n\n        'target_lang' is the target language for which the given objects\n        are being compiled. This allows specific linkage time treatment of\n        certain languages.\n\n        Raises LibError on failure.\n        ");
      var1.setline(610);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject link$30(PyFrame var1, ThreadState var2) {
      var1.setline(663);
      PyString.fromInterned("Link a bunch of stuff together to create an executable or\n        shared library file.\n\n        The \"bunch of stuff\" consists of the list of object files supplied\n        as 'objects'.  'output_filename' should be a filename.  If\n        'output_dir' is supplied, 'output_filename' is relative to it\n        (i.e. 'output_filename' can provide directory components if\n        needed).\n\n        'libraries' is a list of libraries to link against.  These are\n        library names, not filenames, since they're translated into\n        filenames in a platform-specific way (eg. \"foo\" becomes \"libfoo.a\"\n        on Unix and \"foo.lib\" on DOS/Windows).  However, they can include a\n        directory component, which means the linker will look in that\n        specific directory rather than searching all the normal locations.\n\n        'library_dirs', if supplied, should be a list of directories to\n        search for libraries that were specified as bare library names\n        (ie. no directory component).  These are on top of the system\n        default and those supplied to 'add_library_dir()' and/or\n        'set_library_dirs()'.  'runtime_library_dirs' is a list of\n        directories that will be embedded into the shared library and used\n        to search for other shared libraries that *it* depends on at\n        run-time.  (This may only be relevant on Unix.)\n\n        'export_symbols' is a list of symbols that the shared library will\n        export.  (This appears to be relevant only on Windows.)\n\n        'debug' is as for 'compile()' and 'create_static_lib()', with the\n        slight distinction that it actually matters on most platforms (as\n        opposed to 'create_static_lib()', which includes a 'debug' flag\n        mostly for form's sake).\n\n        'extra_preargs' and 'extra_postargs' are as for 'compile()' (except\n        of course that they supply command-line arguments for the\n        particular linker being used).\n\n        'target_lang' is the target language for which the given objects\n        are being compiled. This allows specific linkage time treatment of\n        certain languages.\n\n        Raises LinkError on failure.\n        ");
      var1.setline(664);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject link_shared_lib$31(PyFrame var1, ThreadState var2) {
      var1.setline(674);
      PyObject var10000 = var1.getlocal(0).__getattr__("link");
      PyObject[] var3 = new PyObject[13];
      var3[0] = var1.getglobal("CCompiler").__getattr__("SHARED_LIBRARY");
      var3[1] = var1.getlocal(1);
      PyObject var10002 = var1.getlocal(0).__getattr__("library_filename");
      PyObject[] var4 = new PyObject[]{var1.getlocal(2), PyString.fromInterned("shared")};
      String[] var5 = new String[]{"lib_type"};
      var10002 = var10002.__call__(var2, var4, var5);
      var4 = null;
      var3[2] = var10002;
      var3[3] = var1.getlocal(3);
      var3[4] = var1.getlocal(4);
      var3[5] = var1.getlocal(5);
      var3[6] = var1.getlocal(6);
      var3[7] = var1.getlocal(7);
      var3[8] = var1.getlocal(8);
      var3[9] = var1.getlocal(9);
      var3[10] = var1.getlocal(10);
      var3[11] = var1.getlocal(11);
      var3[12] = var1.getlocal(12);
      var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject link_shared_object$32(PyFrame var1, ThreadState var2) {
      var1.setline(687);
      PyObject var10000 = var1.getlocal(0).__getattr__("link");
      PyObject[] var3 = new PyObject[]{var1.getglobal("CCompiler").__getattr__("SHARED_OBJECT"), var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getlocal(10), var1.getlocal(11), var1.getlocal(12)};
      var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject link_executable$33(PyFrame var1, ThreadState var2) {
      var1.setline(697);
      PyObject var10000 = var1.getlocal(0).__getattr__("link");
      PyObject[] var3 = new PyObject[]{var1.getglobal("CCompiler").__getattr__("EXECUTABLE"), var1.getlocal(1), var1.getlocal(0).__getattr__("executable_filename").__call__(var2, var1.getlocal(2)), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6), var1.getglobal("None"), var1.getlocal(7), var1.getlocal(8), var1.getlocal(9), var1.getglobal("None"), var1.getlocal(10)};
      var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject library_dir_option$34(PyFrame var1, ThreadState var2) {
      var1.setline(711);
      PyString.fromInterned("Return the compiler option to add 'dir' to the list of\n        directories searched for libraries.\n        ");
      var1.setline(712);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject runtime_library_dir_option$35(PyFrame var1, ThreadState var2) {
      var1.setline(717);
      PyString.fromInterned("Return the compiler option to add 'dir' to the list of\n        directories searched for runtime libraries.\n        ");
      var1.setline(718);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject library_option$36(PyFrame var1, ThreadState var2) {
      var1.setline(723);
      PyString.fromInterned("Return the compiler option to add 'dir' to the list of libraries\n        linked into the shared library or executable.\n        ");
      var1.setline(724);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject has_function$37(PyFrame var1, ThreadState var2) {
      var1.setline(731);
      PyString.fromInterned("Return a boolean indicating whether funcname is supported on\n        the current platform.  The optional arguments can be used to\n        augment the compilation environment.\n        ");
      var1.setline(736);
      PyObject var3 = imp.importOne("tempfile", var1, -1);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(737);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyList var9;
      if (var10000.__nonzero__()) {
         var1.setline(738);
         var9 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var9);
         var3 = null;
      }

      var1.setline(739);
      var3 = var1.getlocal(3);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(740);
         var9 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var9);
         var3 = null;
      }

      var1.setline(741);
      var3 = var1.getlocal(4);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(742);
         var9 = new PyList(Py.EmptyObjects);
         var1.setlocal(4, var9);
         var3 = null;
      }

      var1.setline(743);
      var3 = var1.getlocal(5);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(744);
         var9 = new PyList(Py.EmptyObjects);
         var1.setlocal(5, var9);
         var3 = null;
      }

      var1.setline(745);
      var10000 = var1.getlocal(6).__getattr__("mkstemp");
      PyObject[] var13 = new PyObject[]{PyString.fromInterned(".c"), var1.getlocal(1), var1.getglobal("True")};
      String[] var4 = new String[]{"text"};
      var10000 = var10000.__call__(var2, var13, var4);
      var3 = null;
      var3 = var10000;
      PyObject[] var10 = Py.unpackSequence(var3, 2);
      PyObject var5 = var10[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var10[1];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(746);
      var3 = var1.getglobal("os").__getattr__("fdopen").__call__((ThreadState)var2, (PyObject)var1.getlocal(7), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(9, var3);
      var3 = null;
      var3 = null;

      PyObject var11;
      try {
         var1.setline(748);
         var11 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(748);
            var5 = var11.__iternext__();
            if (var5 == null) {
               var1.setline(750);
               var1.getlocal(9).__getattr__("write").__call__(var2, PyString.fromInterned("main (int argc, char **argv) {\n    %s();\n}\n")._mod(var1.getlocal(1)));
               break;
            }

            var1.setlocal(10, var5);
            var1.setline(749);
            var1.getlocal(9).__getattr__("write").__call__(var2, PyString.fromInterned("#include \"%s\"\n")._mod(var1.getlocal(10)));
         }
      } catch (Throwable var8) {
         Py.addTraceback(var8, var1);
         var1.setline(756);
         var1.getlocal(9).__getattr__("close").__call__(var2);
         throw (Throwable)var8;
      }

      var1.setline(756);
      var1.getlocal(9).__getattr__("close").__call__(var2);

      PyException var14;
      try {
         var1.setline(758);
         var10000 = var1.getlocal(0).__getattr__("compile");
         var13 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(8)}), var1.getlocal(3)};
         var4 = new String[]{"include_dirs"};
         var10000 = var10000.__call__(var2, var13, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(11, var3);
         var3 = null;
      } catch (Throwable var6) {
         var14 = Py.setException(var6, var1);
         if (var14.match(var1.getglobal("CompileError"))) {
            var1.setline(760);
            var11 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var11;
         }

         throw var14;
      }

      try {
         var1.setline(763);
         var10000 = var1.getlocal(0).__getattr__("link_executable");
         var13 = new PyObject[]{var1.getlocal(11), PyString.fromInterned("a.out"), var1.getlocal(4), var1.getlocal(5)};
         String[] var12 = new String[]{"libraries", "library_dirs"};
         var10000.__call__(var2, var13, var12);
         var3 = null;
      } catch (Throwable var7) {
         var14 = Py.setException(var7, var1);
         if (var14.match(new PyTuple(new PyObject[]{var1.getglobal("LinkError"), var1.getglobal("TypeError")}))) {
            var1.setline(767);
            var11 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var11;
         }

         throw var14;
      }

      var1.setline(768);
      var11 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var11;
   }

   public PyObject find_library_file$38(PyFrame var1, ThreadState var2) {
      var1.setline(776);
      PyString.fromInterned("Search the specified list of directories for a static or shared\n        library file 'lib' and return the full path to that file.  If\n        'debug' true, look for a debugging version (if that makes sense on\n        the current platform).  Return None if 'lib' wasn't found in any of\n        the specified directories.\n        ");
      var1.setline(777);
      throw Py.makeException(var1.getglobal("NotImplementedError"));
   }

   public PyObject object_filenames$39(PyFrame var1, ThreadState var2) {
      var1.setline(814);
      PyObject var3 = var1.getlocal(3);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(815);
         PyString var8 = PyString.fromInterned("");
         var1.setlocal(3, var8);
         var3 = null;
      }

      var1.setline(816);
      PyList var9 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var9);
      var3 = null;
      var1.setline(817);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(817);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(828);
            var3 = var1.getlocal(4);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(5, var4);
         var1.setline(818);
         PyObject var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(5));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(6, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(7, var7);
         var7 = null;
         var5 = null;
         var1.setline(819);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("splitdrive").__call__(var2, var1.getlocal(6)).__getitem__(Py.newInteger(1));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(820);
         var5 = var1.getlocal(6).__getslice__(var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(6)), (PyObject)null, (PyObject)null);
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(821);
         var5 = var1.getlocal(7);
         var10000 = var5._notin(var1.getlocal(0).__getattr__("src_extensions"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(822);
            throw Py.makeException(var1.getglobal("UnknownFileError"), PyString.fromInterned("unknown file type '%s' (from '%s')")._mod(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5)})));
         }

         var1.setline(824);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(825);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(6));
            var1.setlocal(6, var5);
            var5 = null;
         }

         var1.setline(826);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(6)._add(var1.getlocal(0).__getattr__("obj_extension"))));
      }
   }

   public PyObject shared_object_filename$40(PyFrame var1, ThreadState var2) {
      var1.setline(831);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(3);
         PyObject var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(832);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(833);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(834);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(1)._add(var1.getlocal(0).__getattr__("shared_lib_extension")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject executable_filename$41(PyFrame var1, ThreadState var2) {
      var1.setline(837);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(3);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(838);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(839);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(840);
      var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
      PyObject var10002 = var1.getlocal(3);
      PyObject var10003 = var1.getlocal(1);
      Object var10004 = var1.getlocal(0).__getattr__("exe_extension");
      if (!((PyObject)var10004).__nonzero__()) {
         var10004 = PyString.fromInterned("");
      }

      var3 = var10000.__call__(var2, var10002, var10003._add((PyObject)var10004));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject library_filename$42(PyFrame var1, ThreadState var2) {
      var1.setline(844);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         var3 = var1.getlocal(4);
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = Py.None;
            throw Py.makeException(var1.getglobal("AssertionError"), var10000);
         }
      }

      var1.setline(845);
      var3 = var1.getlocal(2);
      var10000 = var3._notin(new PyTuple(new PyObject[]{PyString.fromInterned("static"), PyString.fromInterned("shared"), PyString.fromInterned("dylib")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(846);
         throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("'lib_type' must be \"static\", \"shared\" or \"dylib\""));
      } else {
         var1.setline(847);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2)._add(PyString.fromInterned("_lib_format")));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(848);
         var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2)._add(PyString.fromInterned("_lib_extension")));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(850);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(1));
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(8, var5);
         var5 = null;
         var3 = null;
         var1.setline(851);
         var3 = var1.getlocal(5)._mod(new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(6)}));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(852);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(853);
            PyString var6 = PyString.fromInterned("");
            var1.setlocal(7, var6);
            var3 = null;
         }

         var1.setline(855);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(7), var1.getlocal(9));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject announce$43(PyFrame var1, ThreadState var2) {
      var1.setline(861);
      var1.getglobal("log").__getattr__("debug").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject debug_print$44(PyFrame var1, ThreadState var2) {
      var1.setline(864);
      String[] var3 = new String[]{"DEBUG"};
      PyObject[] var5 = imp.importFrom("distutils.debug", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(865);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(866);
         Py.println(var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject warn$45(PyFrame var1, ThreadState var2) {
      var1.setline(869);
      var1.getglobal("sys").__getattr__("stderr").__getattr__("write").__call__(var2, PyString.fromInterned("warning: %s\n")._mod(var1.getlocal(1)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject execute$46(PyFrame var1, ThreadState var2) {
      var1.setline(872);
      var1.getglobal("execute").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(0).__getattr__("dry_run"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject spawn$47(PyFrame var1, ThreadState var2) {
      var1.setline(875);
      PyObject var10000 = var1.getglobal("spawn");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("dry_run")};
      String[] var4 = new String[]{"dry_run"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject move_file$48(PyFrame var1, ThreadState var2) {
      var1.setline(878);
      PyObject var10000 = var1.getglobal("move_file");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("dry_run")};
      String[] var4 = new String[]{"dry_run"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject mkpath$49(PyFrame var1, ThreadState var2) {
      var1.setline(881);
      PyObject var10000 = var1.getglobal("mkpath");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(0).__getattr__("dry_run")};
      String[] var4 = new String[]{"dry_run"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_default_compiler$50(PyFrame var1, ThreadState var2) {
      var1.setline(917);
      PyString.fromInterned(" Determine the default compiler to use for the given platform.\n\n        osname should be one of the standard Python OS names (i.e. the\n        ones returned by os.name) and platform the common value\n        returned by sys.platform for the platform in question.\n\n        The default values are os.name and sys.platform in case the\n        parameters are not given.\n\n    ");
      var1.setline(918);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(919);
         var3 = var1.getglobal("os").__getattr__("name");
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(920);
      var3 = var1.getlocal(1);
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(921);
         var3 = var1.getglobal("sys").__getattr__("platform");
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(922);
      var3 = var1.getglobal("_default_compilers").__iter__();

      PyObject var7;
      do {
         var1.setline(922);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(927);
            PyString var8 = PyString.fromInterned("unix");
            var1.f_lasti = -1;
            return var8;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(923);
         var7 = var1.getglobal("re").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(1));
         var10000 = var7._isnot(var1.getglobal("None"));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var7 = var1.getglobal("re").__getattr__("match").__call__(var2, var1.getlocal(2), var1.getlocal(0));
            var10000 = var7._isnot(var1.getglobal("None"));
            var5 = null;
         }
      } while(!var10000.__nonzero__());

      var1.setline(925);
      var7 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject show_compilers$51(PyFrame var1, ThreadState var2) {
      var1.setline(951);
      PyString.fromInterned("Print list of available compilers (used by the \"--help-compiler\"\n    options to \"build\", \"build_ext\", \"build_clib\").\n    ");
      var1.setline(955);
      String[] var3 = new String[]{"FancyGetopt"};
      PyObject[] var5 = imp.importFrom("distutils.fancy_getopt", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(956);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(957);
      PyObject var7 = var1.getglobal("compiler_class").__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(957);
         var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(960);
            var1.getlocal(1).__getattr__("sort").__call__(var2);
            var1.setline(961);
            var7 = var1.getlocal(0).__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(962);
            var1.getlocal(3).__getattr__("print_help").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("List of available compilers:"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(958);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("compiler=")._add(var1.getlocal(2)), var1.getglobal("None"), var1.getglobal("compiler_class").__getitem__(var1.getlocal(2)).__getitem__(Py.newInteger(2))})));
      }
   }

   public PyObject new_compiler$52(PyFrame var1, ThreadState var2) {
      var1.setline(975);
      PyString.fromInterned("Generate an instance of some CCompiler subclass for the supplied\n    platform/compiler combination.  'plat' defaults to 'os.name'\n    (eg. 'posix', 'nt'), and 'compiler' defaults to the default compiler\n    for that platform.  Currently only 'posix' and 'nt' are supported, and\n    the default compilers are \"traditional Unix interface\" (UnixCCompiler\n    class) and Visual C++ (MSVCCompiler class).  Note that it's perfectly\n    possible to ask for a Unix compiler object under Windows, and a\n    Microsoft compiler object under Unix -- if you supply a value for\n    'compiler', 'plat' is ignored.\n    ");
      var1.setline(976);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(977);
         var3 = var1.getglobal("os").__getattr__("name");
         var1.setlocal(0, var3);
         var3 = null;
      }

      PyException var8;
      try {
         var1.setline(980);
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(981);
            var3 = var1.getglobal("get_default_compiler").__call__(var2, var1.getlocal(0));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(983);
         var3 = var1.getglobal("compiler_class").__getitem__(var1.getlocal(1));
         PyObject[] var9 = Py.unpackSequence(var3, 3);
         PyObject var5 = var9[0];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var9[1];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var9[2];
         var1.setlocal(7, var5);
         var5 = null;
         var3 = null;
      } catch (Throwable var7) {
         var8 = Py.setException(var7, var1);
         if (var8.match(var1.getglobal("KeyError"))) {
            var1.setline(985);
            PyObject var4 = PyString.fromInterned("don't know how to compile C/C++ code on platform '%s'")._mod(var1.getlocal(0));
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(986);
            var4 = var1.getlocal(1);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(987);
               var4 = var1.getlocal(8)._add(PyString.fromInterned(" with '%s' compiler")._mod(var1.getlocal(1)));
               var1.setlocal(8, var4);
               var4 = null;
            }

            var1.setline(988);
            throw Py.makeException(var1.getglobal("DistutilsPlatformError"), var1.getlocal(8));
         }

         throw var8;
      }

      try {
         var1.setline(991);
         var3 = PyString.fromInterned("distutils.")._add(var1.getlocal(5));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(992);
         var1.getglobal("__import__").__call__(var2, var1.getlocal(5));
         var1.setline(993);
         var3 = var1.getglobal("sys").__getattr__("modules").__getitem__(var1.getlocal(5));
         var1.setlocal(9, var3);
         var3 = null;
         var1.setline(994);
         var3 = var1.getglobal("vars").__call__(var2, var1.getlocal(9)).__getitem__(var1.getlocal(6));
         var1.setlocal(10, var3);
         var3 = null;
      } catch (Throwable var6) {
         var8 = Py.setException(var6, var1);
         if (var8.match(var1.getglobal("ImportError"))) {
            var1.setline(996);
            throw Py.makeException(var1.getglobal("DistutilsModuleError"), PyString.fromInterned("can't compile C/C++ code: unable to load module '%s'")._mod(var1.getlocal(5)));
         }

         if (var8.match(var1.getglobal("KeyError"))) {
            var1.setline(1000);
            throw Py.makeException(var1.getglobal("DistutilsModuleError"), PyString.fromInterned("can't compile C/C++ code: unable to find class '%s' ")._add(PyString.fromInterned("in module '%s'"))._mod(new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(5)})));
         }

         throw var8;
      }

      var1.setline(1007);
      var3 = var1.getlocal(10).__call__(var2, var1.getglobal("None"), var1.getlocal(3), var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject gen_preprocess_options$53(PyFrame var1, ThreadState var2) {
      var1.setline(1019);
      PyString.fromInterned("Generate C pre-processor options (-D, -U, -I) as used by at least\n    two types of compilers: the typical Unix compiler and Visual C++.\n    'macros' is the usual thing, a list of 1- or 2-tuples, where (name,)\n    means undefine (-U) macro 'name', and (name,value) means define (-D)\n    macro 'name' to 'value'.  'include_dirs' is just a list of directory\n    names to be added to the header file search path (-I).  Returns a list\n    of command-line options suitable for either Unix compilers or Visual\n    C++.\n    ");
      var1.setline(1032);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(1033);
      PyObject var7 = var1.getlocal(0).__iter__();

      while(true) {
         var1.setline(1033);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(1053);
            var7 = var1.getlocal(1).__iter__();

            while(true) {
               var1.setline(1053);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.setline(1056);
                  var7 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var7;
               }

               var1.setlocal(4, var4);
               var1.setline(1054);
               var1.getlocal(2).__getattr__("append").__call__(var2, PyString.fromInterned("-I%s")._mod(var1.getlocal(4)));
            }
         }

         var1.setlocal(3, var4);
         var1.setline(1035);
         PyObject var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("tuple"));
         PyInteger var5;
         PyObject var8;
         if (var10000.__nonzero__()) {
            var5 = Py.newInteger(1);
            PyObject var10001 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            PyInteger var9 = var5;
            var8 = var10001;
            PyObject var6;
            if ((var6 = var9._le(var10001)).__nonzero__()) {
               var6 = var8._le(Py.newInteger(2));
            }

            var10000 = var6;
            var5 = null;
         }

         if (var10000.__not__().__nonzero__()) {
            var1.setline(1037);
            throw Py.makeException(var1.getglobal("TypeError"), PyString.fromInterned("bad macro definition '%s': ")._add(PyString.fromInterned("each element of 'macros' list must be a 1- or 2-tuple"))._mod(var1.getlocal(3)));
         }

         var1.setline(1042);
         var8 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
         var10000 = var8._eq(Py.newInteger(1));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(1043);
            var1.getlocal(2).__getattr__("append").__call__(var2, PyString.fromInterned("-U%s")._mod(var1.getlocal(3).__getitem__(Py.newInteger(0))));
         } else {
            var1.setline(1044);
            var8 = var1.getglobal("len").__call__(var2, var1.getlocal(3));
            var10000 = var8._eq(Py.newInteger(2));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(1045);
               var8 = var1.getlocal(3).__getitem__(Py.newInteger(1));
               var10000 = var8._is(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(1046);
                  var1.getlocal(2).__getattr__("append").__call__(var2, PyString.fromInterned("-D%s")._mod(var1.getlocal(3).__getitem__(Py.newInteger(0))));
               } else {
                  var1.setline(1051);
                  var1.getlocal(2).__getattr__("append").__call__(var2, PyString.fromInterned("-D%s=%s")._mod(var1.getlocal(3)));
               }
            }
         }
      }
   }

   public PyObject gen_lib_options$54(PyFrame var1, ThreadState var2) {
      var1.setline(1067);
      PyString.fromInterned("Generate linker options for searching library directories and\n    linking with specific libraries.\n\n    'libraries' and 'library_dirs' are, respectively, lists of library names\n    (not filenames!) and search directories.  Returns a list of command-line\n    options suitable for use with some compiler (depending on the two format\n    strings passed in).\n    ");
      var1.setline(1068);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(1070);
      PyObject var8 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(1070);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(1073);
            var8 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(1073);
               var4 = var8.__iternext__();
               PyObject var5;
               if (var4 == null) {
                  var1.setline(1086);
                  var8 = var1.getlocal(3).__iter__();

                  while(true) {
                     var1.setline(1086);
                     var4 = var8.__iternext__();
                     if (var4 == null) {
                        var1.setline(1098);
                        var8 = var1.getlocal(4);
                        var1.f_lasti = -1;
                        return var8;
                     }

                     var1.setlocal(7, var4);
                     var1.setline(1087);
                     var5 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(7));
                     PyObject[] var6 = Py.unpackSequence(var5, 2);
                     PyObject var7 = var6[0];
                     var1.setlocal(8, var7);
                     var7 = null;
                     var7 = var6[1];
                     var1.setlocal(9, var7);
                     var7 = null;
                     var5 = null;
                     var1.setline(1088);
                     var5 = var1.getlocal(8);
                     PyObject var10000 = var5._ne(PyString.fromInterned(""));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(1089);
                        var5 = var1.getlocal(0).__getattr__("find_library_file").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(8)})), (PyObject)var1.getlocal(9));
                        var1.setlocal(10, var5);
                        var5 = null;
                        var1.setline(1090);
                        var5 = var1.getlocal(10);
                        var10000 = var5._isnot(var1.getglobal("None"));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(1091);
                           var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(10));
                        } else {
                           var1.setline(1093);
                           var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("no library file corresponding to '%s' found (skipping)")._mod(var1.getlocal(7)));
                        }
                     } else {
                        var1.setline(1096);
                        var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("library_option").__call__(var2, var1.getlocal(7)));
                     }
                  }
               }

               var1.setlocal(5, var4);
               var1.setline(1074);
               var5 = var1.getlocal(0).__getattr__("runtime_library_dir_option").__call__(var2, var1.getlocal(5));
               var1.setlocal(6, var5);
               var5 = null;
               var1.setline(1075);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(6), var1.getglobal("list")).__nonzero__()) {
                  var1.setline(1076);
                  var1.getlocal(4).__getattr__("extend").__call__(var2, var1.getlocal(6));
               } else {
                  var1.setline(1078);
                  var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(6));
               }
            }
         }

         var1.setlocal(5, var4);
         var1.setline(1071);
         var1.getlocal(4).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("library_dir_option").__call__(var2, var1.getlocal(5)));
      }
   }

   public ccompiler$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CCompiler$1 = Py.newCode(0, var2, var1, "CCompiler", 23, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "verbose", "dry_run", "force", "key"};
      __init__$2 = Py.newCode(4, var2, var1, "__init__", 93, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "key"};
      set_executables$3 = Py.newCode(2, var2, var1, "set_executables", 129, false, true, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value"};
      set_executable$4 = Py.newCode(3, var2, var1, "set_executable", 162, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "i", "defn"};
      _find_macro$5 = Py.newCode(2, var2, var1, "_find_macro", 168, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "definitions", "defn"};
      _check_macro_definitions$6 = Py.newCode(2, var2, var1, "_check_macro_definitions", 176, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "value", "i", "defn"};
      define_macro$7 = Py.newCode(3, var2, var1, "define_macro", 195, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "i", "undefn"};
      undefine_macro$8 = Py.newCode(2, var2, var1, "undefine_macro", 211, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir"};
      add_include_dir$9 = Py.newCode(2, var2, var1, "add_include_dir", 229, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirs"};
      set_include_dirs$10 = Py.newCode(2, var2, var1, "set_include_dirs", 237, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "libname"};
      add_library$11 = Py.newCode(2, var2, var1, "add_library", 247, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "libnames"};
      set_libraries$12 = Py.newCode(2, var2, var1, "set_libraries", 263, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir"};
      add_library_dir$13 = Py.newCode(2, var2, var1, "add_library_dir", 272, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirs"};
      set_library_dirs$14 = Py.newCode(2, var2, var1, "set_library_dirs", 280, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir"};
      add_runtime_library_dir$15 = Py.newCode(2, var2, var1, "add_runtime_library_dir", 287, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirs"};
      set_runtime_library_dirs$16 = Py.newCode(2, var2, var1, "set_runtime_library_dirs", 293, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "object"};
      add_link_object$17 = Py.newCode(2, var2, var1, "add_link_object", 301, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects"};
      set_link_objects$18 = Py.newCode(2, var2, var1, "set_link_objects", 309, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "outdir", "macros", "incdirs", "sources", "depends", "extra", "objects", "pp_opts", "build", "i", "src", "obj", "ext"};
      _setup_compile$19 = Py.newCode(7, var2, var1, "_setup_compile", 323, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pp_opts", "debug", "before", "cc_args"};
      _get_cc_args$20 = Py.newCode(4, var2, var1, "_get_cc_args", 367, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "output_dir", "macros", "include_dirs"};
      _fix_compile_args$21 = Py.newCode(4, var2, var1, "_fix_compile_args", 376, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects", "output_dir"};
      _fix_object_args$22 = Py.newCode(3, var2, var1, "_fix_object_args", 408, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "libraries", "library_dirs", "runtime_library_dirs"};
      _fix_lib_args$23 = Py.newCode(4, var2, var1, "_fix_lib_args", 426, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects", "output_file", "newer"};
      _need_link$24 = Py.newCode(3, var2, var1, "_need_link", 461, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sources", "lang", "index", "source", "base", "ext", "extlang", "extindex"};
      detect_language$25 = Py.newCode(2, var2, var1, "detect_language", 474, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "output_file", "macros", "include_dirs", "extra_preargs", "extra_postargs"};
      preprocess$26 = Py.newCode(7, var2, var1, "preprocess", 497, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sources", "output_dir", "macros", "include_dirs", "debug", "extra_preargs", "extra_postargs", "depends", "objects", "pp_opts", "build", "cc_args", "obj", "src", "ext"};
      compile$27 = Py.newCode(9, var2, var1, "compile", 510, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "obj", "src", "ext", "cc_args", "extra_postargs", "pp_opts"};
      _compile$28 = Py.newCode(7, var2, var1, "_compile", 579, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects", "output_libname", "output_dir", "debug", "target_lang"};
      create_static_lib$29 = Py.newCode(6, var2, var1, "create_static_lib", 586, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target_desc", "objects", "output_filename", "output_dir", "libraries", "library_dirs", "runtime_library_dirs", "export_symbols", "debug", "extra_preargs", "extra_postargs", "build_temp", "target_lang"};
      link$30 = Py.newCode(14, var2, var1, "link", 617, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects", "output_libname", "output_dir", "libraries", "library_dirs", "runtime_library_dirs", "export_symbols", "debug", "extra_preargs", "extra_postargs", "build_temp", "target_lang"};
      link_shared_lib$31 = Py.newCode(13, var2, var1, "link_shared_lib", 669, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects", "output_filename", "output_dir", "libraries", "library_dirs", "runtime_library_dirs", "export_symbols", "debug", "extra_preargs", "extra_postargs", "build_temp", "target_lang"};
      link_shared_object$32 = Py.newCode(13, var2, var1, "link_shared_object", 682, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "objects", "output_progname", "output_dir", "libraries", "library_dirs", "runtime_library_dirs", "debug", "extra_preargs", "extra_postargs", "target_lang"};
      link_executable$33 = Py.newCode(11, var2, var1, "link_executable", 693, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir"};
      library_dir_option$34 = Py.newCode(2, var2, var1, "library_dir_option", 708, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir"};
      runtime_library_dir_option$35 = Py.newCode(2, var2, var1, "runtime_library_dir_option", 714, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lib"};
      library_option$36 = Py.newCode(2, var2, var1, "library_option", 720, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "funcname", "includes", "include_dirs", "libraries", "library_dirs", "tempfile", "fd", "fname", "f", "incl", "objects"};
      has_function$37 = Py.newCode(6, var2, var1, "has_function", 726, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dirs", "lib", "debug"};
      find_library_file$38 = Py.newCode(4, var2, var1, "find_library_file", 770, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source_filenames", "strip_dir", "output_dir", "obj_names", "src_name", "base", "ext"};
      object_filenames$39 = Py.newCode(4, var2, var1, "object_filenames", 813, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "basename", "strip_dir", "output_dir"};
      shared_object_filename$40 = Py.newCode(4, var2, var1, "shared_object_filename", 830, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "basename", "strip_dir", "output_dir"};
      executable_filename$41 = Py.newCode(4, var2, var1, "executable_filename", 836, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "libname", "lib_type", "strip_dir", "output_dir", "fmt", "ext", "dir", "base", "filename"};
      library_filename$42 = Py.newCode(5, var2, var1, "library_filename", 842, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "level"};
      announce$43 = Py.newCode(3, var2, var1, "announce", 860, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "DEBUG"};
      debug_print$44 = Py.newCode(2, var2, var1, "debug_print", 863, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg"};
      warn$45 = Py.newCode(2, var2, var1, "warn", 868, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "func", "args", "msg", "level"};
      execute$46 = Py.newCode(5, var2, var1, "execute", 871, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd"};
      spawn$47 = Py.newCode(2, var2, var1, "spawn", 874, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "src", "dst"};
      move_file$48 = Py.newCode(3, var2, var1, "move_file", 877, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "mode"};
      mkpath$49 = Py.newCode(3, var2, var1, "mkpath", 880, false, false, self, 49, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"osname", "platform", "pattern", "compiler"};
      get_default_compiler$50 = Py.newCode(2, var2, var1, "get_default_compiler", 907, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"FancyGetopt", "compilers", "compiler", "pretty_printer"};
      show_compilers$51 = Py.newCode(0, var2, var1, "show_compilers", 948, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"plat", "compiler", "verbose", "dry_run", "force", "module_name", "class_name", "long_description", "msg", "module", "klass"};
      new_compiler$52 = Py.newCode(5, var2, var1, "new_compiler", 965, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"macros", "include_dirs", "pp_opts", "macro", "dir"};
      gen_preprocess_options$53 = Py.newCode(2, var2, var1, "gen_preprocess_options", 1010, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"compiler", "library_dirs", "runtime_library_dirs", "libraries", "lib_opts", "dir", "opt", "lib", "lib_dir", "lib_name", "lib_file"};
      gen_lib_options$54 = Py.newCode(4, var2, var1, "gen_lib_options", 1059, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new ccompiler$py("distutils/ccompiler$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(ccompiler$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.CCompiler$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.set_executables$3(var2, var3);
         case 4:
            return this.set_executable$4(var2, var3);
         case 5:
            return this._find_macro$5(var2, var3);
         case 6:
            return this._check_macro_definitions$6(var2, var3);
         case 7:
            return this.define_macro$7(var2, var3);
         case 8:
            return this.undefine_macro$8(var2, var3);
         case 9:
            return this.add_include_dir$9(var2, var3);
         case 10:
            return this.set_include_dirs$10(var2, var3);
         case 11:
            return this.add_library$11(var2, var3);
         case 12:
            return this.set_libraries$12(var2, var3);
         case 13:
            return this.add_library_dir$13(var2, var3);
         case 14:
            return this.set_library_dirs$14(var2, var3);
         case 15:
            return this.add_runtime_library_dir$15(var2, var3);
         case 16:
            return this.set_runtime_library_dirs$16(var2, var3);
         case 17:
            return this.add_link_object$17(var2, var3);
         case 18:
            return this.set_link_objects$18(var2, var3);
         case 19:
            return this._setup_compile$19(var2, var3);
         case 20:
            return this._get_cc_args$20(var2, var3);
         case 21:
            return this._fix_compile_args$21(var2, var3);
         case 22:
            return this._fix_object_args$22(var2, var3);
         case 23:
            return this._fix_lib_args$23(var2, var3);
         case 24:
            return this._need_link$24(var2, var3);
         case 25:
            return this.detect_language$25(var2, var3);
         case 26:
            return this.preprocess$26(var2, var3);
         case 27:
            return this.compile$27(var2, var3);
         case 28:
            return this._compile$28(var2, var3);
         case 29:
            return this.create_static_lib$29(var2, var3);
         case 30:
            return this.link$30(var2, var3);
         case 31:
            return this.link_shared_lib$31(var2, var3);
         case 32:
            return this.link_shared_object$32(var2, var3);
         case 33:
            return this.link_executable$33(var2, var3);
         case 34:
            return this.library_dir_option$34(var2, var3);
         case 35:
            return this.runtime_library_dir_option$35(var2, var3);
         case 36:
            return this.library_option$36(var2, var3);
         case 37:
            return this.has_function$37(var2, var3);
         case 38:
            return this.find_library_file$38(var2, var3);
         case 39:
            return this.object_filenames$39(var2, var3);
         case 40:
            return this.shared_object_filename$40(var2, var3);
         case 41:
            return this.executable_filename$41(var2, var3);
         case 42:
            return this.library_filename$42(var2, var3);
         case 43:
            return this.announce$43(var2, var3);
         case 44:
            return this.debug_print$44(var2, var3);
         case 45:
            return this.warn$45(var2, var3);
         case 46:
            return this.execute$46(var2, var3);
         case 47:
            return this.spawn$47(var2, var3);
         case 48:
            return this.move_file$48(var2, var3);
         case 49:
            return this.mkpath$49(var2, var3);
         case 50:
            return this.get_default_compiler$50(var2, var3);
         case 51:
            return this.show_compilers$51(var2, var3);
         case 52:
            return this.new_compiler$52(var2, var3);
         case 53:
            return this.gen_preprocess_options$53(var2, var3);
         case 54:
            return this.gen_lib_options$54(var2, var3);
         default:
            return null;
      }
   }
}
