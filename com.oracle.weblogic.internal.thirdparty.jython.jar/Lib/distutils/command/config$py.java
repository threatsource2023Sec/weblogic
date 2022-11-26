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
@Filename("distutils/command/config.py")
public class config$py extends PyFunctionTable implements PyRunnable {
   static config$py self;
   static final PyCode f$0;
   static final PyCode config$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode run$4;
   static final PyCode _check_compiler$5;
   static final PyCode _gen_temp_sourcefile$6;
   static final PyCode _preprocess$7;
   static final PyCode _compile$8;
   static final PyCode _link$9;
   static final PyCode _clean$10;
   static final PyCode try_cpp$11;
   static final PyCode search_cpp$12;
   static final PyCode try_compile$13;
   static final PyCode try_link$14;
   static final PyCode try_run$15;
   static final PyCode check_func$16;
   static final PyCode check_lib$17;
   static final PyCode check_header$18;
   static final PyCode dump_file$19;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.config\n\nImplements the Distutils 'config' command, a (mostly) empty command class\nthat exists mainly to be sub-classed by specific module distributions and\napplications.  The idea is that while every \"config\" command is different,\nat least they're all named the same, and users always see \"config\" in the\nlist of standard commands.  Also, this is a good place to put common\nconfigure-like tasks: \"try to compile this C code\", or \"figure out where\nthis header file lives\".\n"));
      var1.setline(10);
      PyString.fromInterned("distutils.command.config\n\nImplements the Distutils 'config' command, a (mostly) empty command class\nthat exists mainly to be sub-classed by specific module distributions and\napplications.  The idea is that while every \"config\" command is different,\nat least they're all named the same, and users always see \"config\" in the\nlist of standard commands.  Also, this is a good place to put common\nconfigure-like tasks: \"try to compile this C code\", or \"figure out where\nthis header file lives\".\n");
      var1.setline(12);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(14);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(15);
      var5 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var5);
      var3 = null;
      var1.setline(17);
      String[] var6 = new String[]{"Command"};
      PyObject[] var7 = imp.importFrom("distutils.core", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(18);
      var6 = new String[]{"DistutilsExecError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsExecError", var4);
      var4 = null;
      var1.setline(19);
      var6 = new String[]{"customize_compiler"};
      var7 = imp.importFrom("distutils.sysconfig", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("customize_compiler", var4);
      var4 = null;
      var1.setline(20);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(22);
      PyDictionary var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("c"), PyString.fromInterned(".c"), PyString.fromInterned("c++"), PyString.fromInterned(".cxx")});
      var1.setlocal("LANG_EXT", var8);
      var3 = null;
      var1.setline(24);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("config", var7, config$1);
      var1.setlocal("config", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.setline(344);
      var7 = new PyObject[]{var1.getname("None")};
      PyFunction var9 = new PyFunction(var1.f_globals, var7, dump_file$19, PyString.fromInterned("Dumps a file content into log.info.\n\n    If head is not None, will be dumped before the file content.\n    "));
      var1.setlocal("dump_file", var9);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject config$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(26);
      PyString var3 = PyString.fromInterned("prepare to build");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(28);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("compiler="), var1.getname("None"), PyString.fromInterned("specify the compiler type")}), new PyTuple(new PyObject[]{PyString.fromInterned("cc="), var1.getname("None"), PyString.fromInterned("specify the compiler executable")}), new PyTuple(new PyObject[]{PyString.fromInterned("include-dirs="), PyString.fromInterned("I"), PyString.fromInterned("list of directories to search for header files")}), new PyTuple(new PyObject[]{PyString.fromInterned("define="), PyString.fromInterned("D"), PyString.fromInterned("C preprocessor macros to define")}), new PyTuple(new PyObject[]{PyString.fromInterned("undef="), PyString.fromInterned("U"), PyString.fromInterned("C preprocessor macros to undefine")}), new PyTuple(new PyObject[]{PyString.fromInterned("libraries="), PyString.fromInterned("l"), PyString.fromInterned("external C libraries to link with")}), new PyTuple(new PyObject[]{PyString.fromInterned("library-dirs="), PyString.fromInterned("L"), PyString.fromInterned("directories to search for external C libraries")}), new PyTuple(new PyObject[]{PyString.fromInterned("noisy"), var1.getname("None"), PyString.fromInterned("show every action (compile, link, run, ...) taken")}), new PyTuple(new PyObject[]{PyString.fromInterned("dump-source"), var1.getname("None"), PyString.fromInterned("dump generated source files before attempting to compile them")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(54);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(69);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(85);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$4, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(93);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _check_compiler$5, PyString.fromInterned("Check that 'self.compiler' really is a CCompiler object;\n        if not, make it one.\n        "));
      var1.setlocal("_check_compiler", var6);
      var3 = null;
      var1.setline(112);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _gen_temp_sourcefile$6, (PyObject)null);
      var1.setlocal("_gen_temp_sourcefile", var6);
      var3 = null;
      var1.setline(125);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _preprocess$7, (PyObject)null);
      var1.setlocal("_preprocess", var6);
      var3 = null;
      var1.setline(132);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _compile$8, (PyObject)null);
      var1.setlocal("_compile", var6);
      var3 = null;
      var1.setline(141);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _link$9, (PyObject)null);
      var1.setlocal("_link", var6);
      var3 = null;
      var1.setline(156);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, _clean$10, (PyObject)null);
      var1.setlocal("_clean", var6);
      var3 = null;
      var1.setline(178);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), PyString.fromInterned("c")};
      var6 = new PyFunction(var1.f_globals, var5, try_cpp$11, PyString.fromInterned("Construct a source file from 'body' (a string containing lines\n        of C/C++ code) and 'headers' (a list of header files to include)\n        and run it through the preprocessor.  Return true if the\n        preprocessor succeeded, false if there were any errors.\n        ('body' probably isn't of much use, but what the heck.)\n        "));
      var1.setlocal("try_cpp", var6);
      var3 = null;
      var1.setline(196);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), PyString.fromInterned("c")};
      var6 = new PyFunction(var1.f_globals, var5, search_cpp$12, PyString.fromInterned("Construct a source file (just like 'try_cpp()'), run it through\n        the preprocessor, and return true if any line of the output matches\n        'pattern'.  'pattern' should either be a compiled regex object or a\n        string containing a regex.  If both 'body' and 'headers' are None,\n        preprocesses an empty file -- which can be useful to determine the\n        symbols the preprocessor and compiler set by default.\n        "));
      var1.setlocal("search_cpp", var6);
      var3 = null;
      var1.setline(225);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), PyString.fromInterned("c")};
      var6 = new PyFunction(var1.f_globals, var5, try_compile$13, PyString.fromInterned("Try to compile a source file built from 'body' and 'headers'.\n        Return true on success, false otherwise.\n        "));
      var1.setlocal("try_compile", var6);
      var3 = null;
      var1.setline(241);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), PyString.fromInterned("c")};
      var6 = new PyFunction(var1.f_globals, var5, try_link$14, PyString.fromInterned("Try to compile and link a source file, built from 'body' and\n        'headers', to executable form.  Return true on success, false\n        otherwise.\n        "));
      var1.setlocal("try_link", var6);
      var3 = null;
      var1.setline(260);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), PyString.fromInterned("c")};
      var6 = new PyFunction(var1.f_globals, var5, try_run$15, PyString.fromInterned("Try to compile, link to an executable, and run a program\n        built from 'body' and 'headers'.  Return true on success, false\n        otherwise.\n        "));
      var1.setlocal("try_run", var6);
      var3 = null;
      var1.setline(285);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None"), Py.newInteger(0), Py.newInteger(0)};
      var6 = new PyFunction(var1.f_globals, var5, check_func$16, PyString.fromInterned("Determine if function 'func' is available by constructing a\n        source file that refers to 'func', and compiles and links it.\n        If everything succeeds, returns true; otherwise returns false.\n\n        The constructed source file starts out by including the header\n        files listed in 'headers'.  If 'decl' is true, it then declares\n        'func' (as \"int func()\"); you probably shouldn't supply 'headers'\n        and set 'decl' true in the same call, or you might get errors about\n        a conflicting declarations for 'func'.  Finally, the constructed\n        'main()' function either references 'func' or (if 'call' is true)\n        calls it.  'libraries' and 'library_dirs' are used when\n        linking.\n        "));
      var1.setlocal("check_func", var6);
      var3 = null;
      var1.setline(319);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), new PyList(Py.EmptyObjects)};
      var6 = new PyFunction(var1.f_globals, var5, check_lib$17, PyString.fromInterned("Determine if 'library' is available to be linked against,\n        without actually checking that any particular symbols are provided\n        by it.  'headers' will be used in constructing the source file to\n        be compiled, but the only effect of this is to check if all the\n        header files listed are available.  Any libraries listed in\n        'other_libraries' will be included in the link, in case 'library'\n        has symbols that depend on other libraries.\n        "));
      var1.setlocal("check_lib", var6);
      var3 = null;
      var1.setline(334);
      var5 = new PyObject[]{var1.getname("None"), var1.getname("None"), PyString.fromInterned("c")};
      var6 = new PyFunction(var1.f_globals, var5, check_header$18, PyString.fromInterned("Determine if the system header file named by 'header_file'\n        exists and can be found by the preprocessor; return true if so,\n        false otherwise.\n        "));
      var1.setlocal("check_header", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("compiler", var3);
      var3 = null;
      var1.setline(56);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("cc", var3);
      var3 = null;
      var1.setline(57);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("include_dirs", var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("libraries", var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("library_dirs", var3);
      var3 = null;
      var1.setline(62);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"noisy", var4);
      var3 = null;
      var1.setline(63);
      var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"dump_source", var4);
      var3 = null;
      var1.setline(67);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"temp_files", var5);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyObject var3 = var1.getlocal(0).__getattr__("include_dirs");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(71);
         Object var6 = var1.getlocal(0).__getattr__("distribution").__getattr__("include_dirs");
         if (!((PyObject)var6).__nonzero__()) {
            var6 = new PyList(Py.EmptyObjects);
         }

         Object var4 = var6;
         var1.getlocal(0).__setattr__((String)"include_dirs", (PyObject)var4);
         var3 = null;
      } else {
         var1.setline(72);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("include_dirs"), var1.getglobal("str")).__nonzero__()) {
            var1.setline(73);
            var3 = var1.getlocal(0).__getattr__("include_dirs").__getattr__("split").__call__(var2, var1.getglobal("os").__getattr__("pathsep"));
            var1.getlocal(0).__setattr__("include_dirs", var3);
            var3 = null;
         }
      }

      var1.setline(75);
      var3 = var1.getlocal(0).__getattr__("libraries");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyList var5;
      if (var10000.__nonzero__()) {
         var1.setline(76);
         var5 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"libraries", var5);
         var3 = null;
      } else {
         var1.setline(77);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("libraries"), var1.getglobal("str")).__nonzero__()) {
            var1.setline(78);
            var5 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("libraries")});
            var1.getlocal(0).__setattr__((String)"libraries", var5);
            var3 = null;
         }
      }

      var1.setline(80);
      var3 = var1.getlocal(0).__getattr__("library_dirs");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(81);
         var5 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"library_dirs", var5);
         var3 = null;
      } else {
         var1.setline(82);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("library_dirs"), var1.getglobal("str")).__nonzero__()) {
            var1.setline(83);
            var3 = var1.getlocal(0).__getattr__("library_dirs").__getattr__("split").__call__(var2, var1.getglobal("os").__getattr__("pathsep"));
            var1.getlocal(0).__setattr__("library_dirs", var3);
            var3 = null;
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$4(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _check_compiler$5(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyString.fromInterned("Check that 'self.compiler' really is a CCompiler object;\n        if not, make it one.\n        ");
      var1.setline(99);
      String[] var3 = new String[]{"CCompiler", "new_compiler"};
      PyObject[] var5 = imp.importFrom("distutils.ccompiler", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(1, var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(100);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("compiler"), var1.getlocal(1)).__not__().__nonzero__()) {
         var1.setline(101);
         PyObject var10000 = var1.getlocal(2);
         var5 = new PyObject[]{var1.getlocal(0).__getattr__("compiler"), var1.getlocal(0).__getattr__("dry_run"), Py.newInteger(1)};
         String[] var7 = new String[]{"compiler", "dry_run", "force"};
         var10000 = var10000.__call__(var2, var5, var7);
         var3 = null;
         PyObject var6 = var10000;
         var1.getlocal(0).__setattr__("compiler", var6);
         var3 = null;
         var1.setline(103);
         var1.getglobal("customize_compiler").__call__(var2, var1.getlocal(0).__getattr__("compiler"));
         var1.setline(104);
         if (var1.getlocal(0).__getattr__("include_dirs").__nonzero__()) {
            var1.setline(105);
            var1.getlocal(0).__getattr__("compiler").__getattr__("set_include_dirs").__call__(var2, var1.getlocal(0).__getattr__("include_dirs"));
         }

         var1.setline(106);
         if (var1.getlocal(0).__getattr__("libraries").__nonzero__()) {
            var1.setline(107);
            var1.getlocal(0).__getattr__("compiler").__getattr__("set_libraries").__call__(var2, var1.getlocal(0).__getattr__("libraries"));
         }

         var1.setline(108);
         if (var1.getlocal(0).__getattr__("library_dirs").__nonzero__()) {
            var1.setline(109);
            var1.getlocal(0).__getattr__("compiler").__getattr__("set_library_dirs").__call__(var2, var1.getlocal(0).__getattr__("library_dirs"));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _gen_temp_sourcefile$6(PyFrame var1, ThreadState var2) {
      var1.setline(113);
      PyObject var3 = PyString.fromInterned("_configtest")._add(var1.getglobal("LANG_EXT").__getitem__(var1.getlocal(3)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(114);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(115);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(116);
         var3 = var1.getlocal(2).__iter__();

         while(true) {
            var1.setline(116);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(118);
               var1.getlocal(5).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
               break;
            }

            var1.setlocal(6, var4);
            var1.setline(117);
            var1.getlocal(5).__getattr__("write").__call__(var2, PyString.fromInterned("#include <%s>\n")._mod(var1.getlocal(6)));
         }
      }

      var1.setline(119);
      var1.getlocal(5).__getattr__("write").__call__(var2, var1.getlocal(1));
      var1.setline(120);
      var3 = var1.getlocal(1).__getitem__(Py.newInteger(-1));
      PyObject var10000 = var3._ne(PyString.fromInterned("\n"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(121);
         var1.getlocal(5).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
      }

      var1.setline(122);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var1.setline(123);
      var3 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _preprocess$7(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyObject var3 = var1.getlocal(0).__getattr__("_gen_temp_sourcefile").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(127);
      PyString var5 = PyString.fromInterned("_configtest.i");
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(128);
      var1.getlocal(0).__getattr__("temp_files").__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(5), var1.getlocal(6)})));
      var1.setline(129);
      PyObject var10000 = var1.getlocal(0).__getattr__("compiler").__getattr__("preprocess");
      PyObject[] var6 = new PyObject[]{var1.getlocal(5), var1.getlocal(6), var1.getlocal(3)};
      String[] var4 = new String[]{"include_dirs"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(130);
      PyTuple var7 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)});
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject _compile$8(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      PyObject var3 = var1.getlocal(0).__getattr__("_gen_temp_sourcefile").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(134);
      if (var1.getlocal(0).__getattr__("dump_source").__nonzero__()) {
         var1.setline(135);
         var1.getglobal("dump_file").__call__(var2, var1.getlocal(5), PyString.fromInterned("compiling '%s':")._mod(var1.getlocal(5)));
      }

      var1.setline(136);
      var3 = var1.getlocal(0).__getattr__("compiler").__getattr__("object_filenames").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(5)})));
      PyObject[] var4 = Py.unpackSequence(var3, 1);
      PyObject var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(137);
      var1.getlocal(0).__getattr__("temp_files").__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(5), var1.getlocal(6)})));
      var1.setline(138);
      PyObject var10000 = var1.getlocal(0).__getattr__("compiler").__getattr__("compile");
      PyObject[] var6 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(5)}), var1.getlocal(3)};
      String[] var7 = new String[]{"include_dirs"};
      var10000.__call__(var2, var6, var7);
      var3 = null;
      var1.setline(139);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(6)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject _link$9(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyObject var3 = var1.getlocal(0).__getattr__("_compile").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(6));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(7, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(8, var5);
      var5 = null;
      var3 = null;
      var1.setline(144);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(7))).__getitem__(Py.newInteger(0));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(145);
      PyObject var10000 = var1.getlocal(0).__getattr__("compiler").__getattr__("link_executable");
      PyObject[] var6 = new PyObject[]{new PyList(new PyObject[]{var1.getlocal(8)}), var1.getlocal(9), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
      String[] var7 = new String[]{"libraries", "library_dirs", "target_lang"};
      var10000.__call__(var2, var6, var7);
      var3 = null;
      var1.setline(150);
      var3 = var1.getlocal(0).__getattr__("compiler").__getattr__("exe_extension");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(151);
         var3 = var1.getlocal(9)._add(var1.getlocal(0).__getattr__("compiler").__getattr__("exe_extension"));
         var1.setlocal(9, var3);
         var3 = null;
      }

      var1.setline(152);
      var1.getlocal(0).__getattr__("temp_files").__getattr__("append").__call__(var2, var1.getlocal(9));
      var1.setline(154);
      PyTuple var8 = new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8), var1.getlocal(9)});
      var1.f_lasti = -1;
      return var8;
   }

   public PyObject _clean$10(PyFrame var1, ThreadState var2) {
      var1.setline(157);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(158);
         var3 = var1.getlocal(0).__getattr__("temp_files");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(159);
         PyList var7 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"temp_files", var7);
         var3 = null;
      }

      var1.setline(160);
      var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("removing: %s"), (PyObject)PyString.fromInterned(" ").__getattr__("join").__call__(var2, var1.getlocal(1)));
      var1.setline(161);
      var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(161);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);

         try {
            var1.setline(163);
            var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(2));
         } catch (Throwable var6) {
            PyException var5 = Py.setException(var6, var1);
            if (!var5.match(var1.getglobal("OSError"))) {
               throw var5;
            }

            var1.setline(165);
         }
      }
   }

   public PyObject try_cpp$11(PyFrame var1, ThreadState var2) {
      var1.setline(184);
      PyString.fromInterned("Construct a source file from 'body' (a string containing lines\n        of C/C++ code) and 'headers' (a list of header files to include)\n        and run it through the preprocessor.  Return true if the\n        preprocessor succeeded, false if there were any errors.\n        ('body' probably isn't of much use, but what the heck.)\n        ");
      var1.setline(185);
      String[] var3 = new String[]{"CompileError"};
      PyObject[] var6 = imp.importFrom("distutils.ccompiler", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(5, var4);
      var4 = null;
      var1.setline(186);
      var1.getlocal(0).__getattr__("_check_compiler").__call__(var2);
      var1.setline(187);
      PyInteger var7 = Py.newInteger(1);
      var1.setlocal(6, var7);
      var3 = null;

      try {
         var1.setline(189);
         var1.getlocal(0).__getattr__("_preprocess").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
      } catch (Throwable var5) {
         PyException var8 = Py.setException(var5, var1);
         if (!var8.match(var1.getlocal(5))) {
            throw var8;
         }

         var1.setline(191);
         PyInteger var10 = Py.newInteger(0);
         var1.setlocal(6, var10);
         var4 = null;
      }

      var1.setline(193);
      var1.getlocal(0).__getattr__("_clean").__call__(var2);
      var1.setline(194);
      PyObject var9 = var1.getlocal(6);
      var1.f_lasti = -1;
      return var9;
   }

   public PyObject search_cpp$12(PyFrame var1, ThreadState var2) {
      var1.setline(204);
      PyString.fromInterned("Construct a source file (just like 'try_cpp()'), run it through\n        the preprocessor, and return true if any line of the output matches\n        'pattern'.  'pattern' should either be a compiled regex object or a\n        string containing a regex.  If both 'body' and 'headers' are None,\n        preprocesses an empty file -- which can be useful to determine the\n        symbols the preprocessor and compiler set by default.\n        ");
      var1.setline(205);
      var1.getlocal(0).__getattr__("_check_compiler").__call__(var2);
      var1.setline(206);
      PyObject var3 = var1.getlocal(0).__getattr__("_preprocess").__call__(var2, var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(6, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(7, var5);
      var5 = null;
      var3 = null;
      var1.setline(208);
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("str")).__nonzero__()) {
         var1.setline(209);
         var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(211);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(7));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(212);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(9, var6);
      var3 = null;

      while(true) {
         var1.setline(213);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(214);
         var3 = var1.getlocal(8).__getattr__("readline").__call__(var2);
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(215);
         var3 = var1.getlocal(10);
         PyObject var10000 = var3._eq(PyString.fromInterned(""));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(217);
         if (var1.getlocal(1).__getattr__("search").__call__(var2, var1.getlocal(10)).__nonzero__()) {
            var1.setline(218);
            var6 = Py.newInteger(1);
            var1.setlocal(9, var6);
            var3 = null;
            break;
         }
      }

      var1.setline(221);
      var1.getlocal(8).__getattr__("close").__call__(var2);
      var1.setline(222);
      var1.getlocal(0).__getattr__("_clean").__call__(var2);
      var1.setline(223);
      var3 = var1.getlocal(9);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject try_compile$13(PyFrame var1, ThreadState var2) {
      var1.setline(228);
      PyString.fromInterned("Try to compile a source file built from 'body' and 'headers'.\n        Return true on success, false otherwise.\n        ");
      var1.setline(229);
      String[] var3 = new String[]{"CompileError"};
      PyObject[] var6 = imp.importFrom("distutils.ccompiler", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(5, var4);
      var4 = null;
      var1.setline(230);
      var1.getlocal(0).__getattr__("_check_compiler").__call__(var2);

      try {
         var1.setline(232);
         var1.getlocal(0).__getattr__("_compile").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4));
         var1.setline(233);
         PyInteger var8 = Py.newInteger(1);
         var1.setlocal(6, var8);
         var3 = null;
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(var1.getlocal(5))) {
            throw var7;
         }

         var1.setline(235);
         PyInteger var9 = Py.newInteger(0);
         var1.setlocal(6, var9);
         var4 = null;
      }

      var1.setline(237);
      PyObject var10000 = var1.getglobal("log").__getattr__("info");
      Object var10002 = var1.getlocal(6);
      if (((PyObject)var10002).__nonzero__()) {
         var10002 = PyString.fromInterned("success!");
      }

      if (!((PyObject)var10002).__nonzero__()) {
         var10002 = PyString.fromInterned("failure.");
      }

      var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var1.setline(238);
      var1.getlocal(0).__getattr__("_clean").__call__(var2);
      var1.setline(239);
      PyObject var10 = var1.getlocal(6);
      var1.f_lasti = -1;
      return var10;
   }

   public PyObject try_link$14(PyFrame var1, ThreadState var2) {
      var1.setline(246);
      PyString.fromInterned("Try to compile and link a source file, built from 'body' and\n        'headers', to executable form.  Return true on success, false\n        otherwise.\n        ");
      var1.setline(247);
      String[] var3 = new String[]{"CompileError", "LinkError"};
      PyObject[] var6 = imp.importFrom("distutils.ccompiler", var3, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal(7, var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal(8, var4);
      var4 = null;
      var1.setline(248);
      var1.getlocal(0).__getattr__("_check_compiler").__call__(var2);

      PyObject var10000;
      try {
         var1.setline(250);
         var10000 = var1.getlocal(0).__getattr__("_link");
         var6 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
         var10000.__call__(var2, var6);
         var1.setline(252);
         PyInteger var8 = Py.newInteger(1);
         var1.setlocal(9, var8);
         var3 = null;
      } catch (Throwable var5) {
         PyException var7 = Py.setException(var5, var1);
         if (!var7.match(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8)}))) {
            throw var7;
         }

         var1.setline(254);
         PyInteger var10 = Py.newInteger(0);
         var1.setlocal(9, var10);
         var4 = null;
      }

      var1.setline(256);
      var10000 = var1.getglobal("log").__getattr__("info");
      Object var10002 = var1.getlocal(9);
      if (((PyObject)var10002).__nonzero__()) {
         var10002 = PyString.fromInterned("success!");
      }

      if (!((PyObject)var10002).__nonzero__()) {
         var10002 = PyString.fromInterned("failure.");
      }

      var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var1.setline(257);
      var1.getlocal(0).__getattr__("_clean").__call__(var2);
      var1.setline(258);
      PyObject var9 = var1.getlocal(9);
      var1.f_lasti = -1;
      return var9;
   }

   public PyObject try_run$15(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyString.fromInterned("Try to compile, link to an executable, and run a program\n        built from 'body' and 'headers'.  Return true on success, false\n        otherwise.\n        ");
      var1.setline(266);
      String[] var3 = new String[]{"CompileError", "LinkError"};
      PyObject[] var7 = imp.importFrom("distutils.ccompiler", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(7, var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal(8, var4);
      var4 = null;
      var1.setline(267);
      var1.getlocal(0).__getattr__("_check_compiler").__call__(var2);

      PyObject var9;
      PyObject var10000;
      try {
         var1.setline(269);
         var10000 = var1.getlocal(0).__getattr__("_link");
         var7 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
         var9 = var10000.__call__(var2, var7);
         PyObject[] var12 = Py.unpackSequence(var9, 3);
         PyObject var5 = var12[0];
         var1.setlocal(9, var5);
         var5 = null;
         var5 = var12[1];
         var1.setlocal(10, var5);
         var5 = null;
         var5 = var12[2];
         var1.setlocal(11, var5);
         var5 = null;
         var3 = null;
         var1.setline(271);
         var1.getlocal(0).__getattr__("spawn").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getlocal(11)})));
         var1.setline(272);
         PyInteger var10 = Py.newInteger(1);
         var1.setlocal(12, var10);
         var3 = null;
      } catch (Throwable var6) {
         PyException var8 = Py.setException(var6, var1);
         if (!var8.match(new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(8), var1.getglobal("DistutilsExecError")}))) {
            throw var8;
         }

         var1.setline(274);
         PyInteger var11 = Py.newInteger(0);
         var1.setlocal(12, var11);
         var4 = null;
      }

      var1.setline(276);
      var10000 = var1.getglobal("log").__getattr__("info");
      Object var10002 = var1.getlocal(12);
      if (((PyObject)var10002).__nonzero__()) {
         var10002 = PyString.fromInterned("success!");
      }

      if (!((PyObject)var10002).__nonzero__()) {
         var10002 = PyString.fromInterned("failure.");
      }

      var10000.__call__((ThreadState)var2, (PyObject)var10002);
      var1.setline(277);
      var1.getlocal(0).__getattr__("_clean").__call__(var2);
      var1.setline(278);
      var9 = var1.getlocal(12);
      var1.f_lasti = -1;
      return var9;
   }

   public PyObject check_func$16(PyFrame var1, ThreadState var2) {
      var1.setline(300);
      PyString.fromInterned("Determine if function 'func' is available by constructing a\n        source file that refers to 'func', and compiles and links it.\n        If everything succeeds, returns true; otherwise returns false.\n\n        The constructed source file starts out by including the header\n        files listed in 'headers'.  If 'decl' is true, it then declares\n        'func' (as \"int func()\"); you probably shouldn't supply 'headers'\n        and set 'decl' true in the same call, or you might get errors about\n        a conflicting declarations for 'func'.  Finally, the constructed\n        'main()' function either references 'func' or (if 'call' is true)\n        calls it.  'libraries' and 'library_dirs' are used when\n        linking.\n        ");
      var1.setline(302);
      var1.getlocal(0).__getattr__("_check_compiler").__call__(var2);
      var1.setline(303);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(304);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(305);
         var1.getlocal(8).__getattr__("append").__call__(var2, PyString.fromInterned("int %s ();")._mod(var1.getlocal(1)));
      }

      var1.setline(306);
      var1.getlocal(8).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("int main () {"));
      var1.setline(307);
      if (var1.getlocal(7).__nonzero__()) {
         var1.setline(308);
         var1.getlocal(8).__getattr__("append").__call__(var2, PyString.fromInterned("  %s();")._mod(var1.getlocal(1)));
      } else {
         var1.setline(310);
         var1.getlocal(8).__getattr__("append").__call__(var2, PyString.fromInterned("  %s;")._mod(var1.getlocal(1)));
      }

      var1.setline(311);
      var1.getlocal(8).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("}"));
      var1.setline(312);
      PyObject var4 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(8))._add(PyString.fromInterned("\n"));
      var1.setlocal(8, var4);
      var3 = null;
      var1.setline(314);
      PyObject var10000 = var1.getlocal(0).__getattr__("try_link");
      PyObject[] var5 = new PyObject[]{var1.getlocal(8), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)};
      var4 = var10000.__call__(var2, var5);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject check_lib$17(PyFrame var1, ThreadState var2) {
      var1.setline(328);
      PyString.fromInterned("Determine if 'library' is available to be linked against,\n        without actually checking that any particular symbols are provided\n        by it.  'headers' will be used in constructing the source file to\n        be compiled, but the only effect of this is to check if all the\n        header files listed are available.  Any libraries listed in\n        'other_libraries' will be included in the link, in case 'library'\n        has symbols that depend on other libraries.\n        ");
      var1.setline(329);
      var1.getlocal(0).__getattr__("_check_compiler").__call__(var2);
      var1.setline(330);
      PyObject var10000 = var1.getlocal(0).__getattr__("try_link");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("int main (void) { }"), var1.getlocal(3), var1.getlocal(4), (new PyList(new PyObject[]{var1.getlocal(1)}))._add(var1.getlocal(5)), var1.getlocal(2)};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject check_header$18(PyFrame var1, ThreadState var2) {
      var1.setline(339);
      PyString.fromInterned("Determine if the system header file named by 'header_file'\n        exists and can be found by the preprocessor; return true if so,\n        false otherwise.\n        ");
      var1.setline(340);
      PyObject var10000 = var1.getlocal(0).__getattr__("try_cpp");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("/* No body */"), new PyList(new PyObject[]{var1.getlocal(1)}), var1.getlocal(2)};
      String[] var4 = new String[]{"body", "headers", "include_dirs"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject dump_file$19(PyFrame var1, ThreadState var2) {
      var1.setline(348);
      PyString.fromInterned("Dumps a file content into log.info.\n\n    If head is not None, will be dumped before the file content.\n    ");
      var1.setline(349);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(350);
         var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned("%s")._mod(var1.getlocal(0)));
      } else {
         var1.setline(352);
         var1.getglobal("log").__getattr__("info").__call__(var2, var1.getlocal(1));
      }

      var1.setline(353);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(355);
         var1.getglobal("log").__getattr__("info").__call__(var2, var1.getlocal(2).__getattr__("read").__call__(var2));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(357);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(357);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public config$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      config$1 = Py.newCode(0, var2, var1, "config", 24, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 54, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 69, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      run$4 = Py.newCode(1, var2, var1, "run", 85, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "CCompiler", "new_compiler"};
      _check_compiler$5 = Py.newCode(1, var2, var1, "_check_compiler", 93, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "body", "headers", "lang", "filename", "file", "header"};
      _gen_temp_sourcefile$6 = Py.newCode(4, var2, var1, "_gen_temp_sourcefile", 112, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "body", "headers", "include_dirs", "lang", "src", "out"};
      _preprocess$7 = Py.newCode(5, var2, var1, "_preprocess", 125, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "body", "headers", "include_dirs", "lang", "src", "obj"};
      _compile$8 = Py.newCode(5, var2, var1, "_compile", 132, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "body", "headers", "include_dirs", "libraries", "library_dirs", "lang", "src", "obj", "prog"};
      _link$9 = Py.newCode(7, var2, var1, "_link", 141, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filenames", "filename"};
      _clean$10 = Py.newCode(2, var2, var1, "_clean", 156, true, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "body", "headers", "include_dirs", "lang", "CompileError", "ok"};
      try_cpp$11 = Py.newCode(5, var2, var1, "try_cpp", 178, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pattern", "body", "headers", "include_dirs", "lang", "src", "out", "file", "match", "line"};
      search_cpp$12 = Py.newCode(6, var2, var1, "search_cpp", 196, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "body", "headers", "include_dirs", "lang", "CompileError", "ok"};
      try_compile$13 = Py.newCode(5, var2, var1, "try_compile", 225, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "body", "headers", "include_dirs", "libraries", "library_dirs", "lang", "CompileError", "LinkError", "ok"};
      try_link$14 = Py.newCode(7, var2, var1, "try_link", 241, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "body", "headers", "include_dirs", "libraries", "library_dirs", "lang", "CompileError", "LinkError", "src", "obj", "exe", "ok"};
      try_run$15 = Py.newCode(7, var2, var1, "try_run", 260, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "func", "headers", "include_dirs", "libraries", "library_dirs", "decl", "call", "body"};
      check_func$16 = Py.newCode(8, var2, var1, "check_func", 285, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "library", "library_dirs", "headers", "include_dirs", "other_libraries"};
      check_lib$17 = Py.newCode(6, var2, var1, "check_lib", 319, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "header", "include_dirs", "library_dirs", "lang"};
      check_header$18 = Py.newCode(5, var2, var1, "check_header", 334, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "head", "file"};
      dump_file$19 = Py.newCode(2, var2, var1, "dump_file", 344, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new config$py("distutils/command/config$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(config$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.config$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.finalize_options$3(var2, var3);
         case 4:
            return this.run$4(var2, var3);
         case 5:
            return this._check_compiler$5(var2, var3);
         case 6:
            return this._gen_temp_sourcefile$6(var2, var3);
         case 7:
            return this._preprocess$7(var2, var3);
         case 8:
            return this._compile$8(var2, var3);
         case 9:
            return this._link$9(var2, var3);
         case 10:
            return this._clean$10(var2, var3);
         case 11:
            return this.try_cpp$11(var2, var3);
         case 12:
            return this.search_cpp$12(var2, var3);
         case 13:
            return this.try_compile$13(var2, var3);
         case 14:
            return this.try_link$14(var2, var3);
         case 15:
            return this.try_run$15(var2, var3);
         case 16:
            return this.check_func$16(var2, var3);
         case 17:
            return this.check_lib$17(var2, var3);
         case 18:
            return this.check_header$18(var2, var3);
         case 19:
            return this.dump_file$19(var2, var3);
         default:
            return null;
      }
   }
}
