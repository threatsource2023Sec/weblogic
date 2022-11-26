package distutils;

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
@Filename("distutils/sysconfig.py")
public class sysconfig$py extends PyFunctionTable implements PyRunnable {
   static distutils.sysconfig$py self;
   static final PyCode f$0;
   static final PyCode getJythonBinDir$1;
   static final PyCode _python_build$2;
   static final PyCode get_python_version$3;
   static final PyCode get_python_inc$4;
   static final PyCode get_python_lib$5;
   static final PyCode customize_compiler$6;
   static final PyCode get_config_h_filename$7;
   static final PyCode get_makefile_filename$8;
   static final PyCode parse_config_h$9;
   static final PyCode parse_makefile$10;
   static final PyCode expand_makefile_vars$11;
   static final PyCode _init_posix$12;
   static final PyCode _init_nt$13;
   static final PyCode _init_os2$14;
   static final PyCode _init_jython$15;
   static final PyCode get_config_vars$16;
   static final PyCode get_config_var$17;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Provide access to Python's configuration information.  The specific\nconfiguration variables available depend heavily on the platform and\nconfiguration.  The values may be retrieved using\nget_config_var(name), and the list of variables is available via\nget_config_vars().keys().  Additional convenience functions are also\navailable.\n\nWritten by:   Fred L. Drake, Jr.\nEmail:        <fdrake@acm.org>\n"));
      var1.setline(10);
      PyString.fromInterned("Provide access to Python's configuration information.  The specific\nconfiguration variables available depend heavily on the platform and\nconfiguration.  The values may be retrieved using\nget_config_var(name), and the list of variables is available via\nget_config_vars().keys().  Additional convenience functions are also\navailable.\n\nWritten by:   Fred L. Drake, Jr.\nEmail:        <fdrake@acm.org>\n");
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
      var1.setline(16);
      var5 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var5);
      var3 = null;
      var1.setline(17);
      var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var1.setline(18);
      String[] var6 = new String[]{"Py"};
      PyObject[] var7 = imp.importFrom("org.python.core", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("Py", var4);
      var4 = null;
      var1.setline(20);
      var6 = new String[]{"DistutilsPlatformError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var1.setline(23);
      var5 = var1.getname("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getname("sys").__getattr__("prefix"));
      var1.setlocal("PREFIX", var5);
      var3 = null;
      var1.setline(24);
      var5 = var1.getname("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getname("sys").__getattr__("exec_prefix"));
      var1.setlocal("EXEC_PREFIX", var5);
      var3 = null;
      var1.setline(31);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, getJythonBinDir$1, (PyObject)null);
      var1.setlocal("getJythonBinDir", var8);
      var3 = null;
      var1.setline(37);
      var5 = var1.getname("getJythonBinDir").__call__(var2);
      var1.setlocal("project_base", var5);
      var3 = null;
      var1.setline(38);
      var5 = var1.getname("os").__getattr__("name");
      PyObject var10000 = var5._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = PyString.fromInterned("pcbuild");
         var10000 = var3._in(var1.getname("project_base").__getslice__(Py.newInteger(-8), (PyObject)null, (PyObject)null).__getattr__("lower").__call__(var2));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(39);
         var5 = var1.getname("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getname("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getname("project_base"), var1.getname("os").__getattr__("path").__getattr__("pardir")));
         var1.setlocal("project_base", var5);
         var3 = null;
      }

      var1.setline(41);
      var5 = var1.getname("os").__getattr__("name");
      var10000 = var5._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = PyString.fromInterned("\\pc\\v");
         var10000 = var3._in(var1.getname("project_base").__getslice__(Py.newInteger(-10), (PyObject)null, (PyObject)null).__getattr__("lower").__call__(var2));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(42);
         var5 = var1.getname("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getname("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getname("project_base"), var1.getname("os").__getattr__("path").__getattr__("pardir"), var1.getname("os").__getattr__("path").__getattr__("pardir")));
         var1.setlocal("project_base", var5);
         var3 = null;
      }

      var1.setline(45);
      var5 = var1.getname("os").__getattr__("name");
      var10000 = var5._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = PyString.fromInterned("\\pcbuild\\amd64");
         var10000 = var3._in(var1.getname("project_base").__getslice__(Py.newInteger(-14), (PyObject)null, (PyObject)null).__getattr__("lower").__call__(var2));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(46);
         var5 = var1.getname("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getname("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getname("project_base"), var1.getname("os").__getattr__("path").__getattr__("pardir"), var1.getname("os").__getattr__("path").__getattr__("pardir")));
         var1.setlocal("project_base", var5);
         var3 = null;
      }

      var1.setline(50);
      var3 = PyString.fromInterned("_PYTHON_PROJECT_BASE");
      var10000 = var3._in(var1.getname("os").__getattr__("environ"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(52);
         var5 = var1.getname("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getname("os").__getattr__("environ").__getitem__(PyString.fromInterned("_PYTHON_PROJECT_BASE")));
         var1.setlocal("project_base", var5);
         var3 = null;
      }

      var1.setline(59);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _python_build$2, (PyObject)null);
      var1.setlocal("_python_build", var8);
      var3 = null;
      var1.setline(64);
      var5 = var1.getname("_python_build").__call__(var2);
      var1.setlocal("python_build", var5);
      var3 = null;
      var1.setline(67);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_python_version$3, PyString.fromInterned("Return a string containing the major and minor Python version,\n    leaving off the patchlevel.  Sample return values could be '1.5'\n    or '2.2'.\n    "));
      var1.setlocal("get_python_version", var8);
      var3 = null;
      var1.setline(75);
      var7 = new PyObject[]{Py.newInteger(0), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, get_python_inc$4, PyString.fromInterned("Return the directory containing installed Python header files.\n\n    If 'plat_specific' is false (the default), this is the path to the\n    non-platform-specific header files, i.e. Python.h and so on;\n    otherwise, this is the path to platform-specific header files\n    (namely pyconfig.h).\n\n    If 'prefix' is supplied, use it instead of sys.prefix or\n    sys.exec_prefix -- i.e., ignore 'plat_specific'.\n    "));
      var1.setlocal("get_python_inc", var8);
      var3 = null;
      var1.setline(113);
      var7 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, get_python_lib$5, PyString.fromInterned("Return the directory containing the Python library (standard or\n    site additions).\n\n    If 'plat_specific' is true, return the directory containing\n    platform-specific modules, i.e. any module from a non-pure-Python\n    module distribution; otherwise, return the platform-shared library\n    directory.  If 'standard_lib' is true, return the directory\n    containing standard Python library modules; otherwise, return the\n    directory for site-specific modules.\n\n    If 'prefix' is supplied, use it instead of sys.prefix or\n    sys.exec_prefix -- i.e., ignore 'plat_specific'.\n    "));
      var1.setlocal("get_python_lib", var8);
      var3 = null;
      var1.setline(160);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, customize_compiler$6, PyString.fromInterned("Do any platform-specific customization of a CCompiler instance.\n\n    Mainly needed on Unix, so we can plug in the information that\n    varies across Unices and is stored in Python's Makefile.\n    "));
      var1.setlocal("customize_compiler", var8);
      var3 = null;
      var1.setline(234);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_config_h_filename$7, PyString.fromInterned("Return full pathname of installed pyconfig.h file."));
      var1.setlocal("get_config_h_filename", var8);
      var3 = null;
      var1.setline(251);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_makefile_filename$8, PyString.fromInterned("Return full pathname of installed Makefile from the Python build."));
      var1.setlocal("get_makefile_filename", var8);
      var3 = null;
      var1.setline(259);
      var7 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, parse_config_h$9, PyString.fromInterned("Parse a config.h-style file.\n\n    A dictionary containing name/value pairs is returned.  If an\n    optional dictionary is passed in as the second argument, it is\n    used instead of a new dictionary.\n    "));
      var1.setlocal("parse_config_h", var8);
      var3 = null;
      var1.setline(290);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([a-zA-Z][a-zA-Z0-9_]+)\\s*=\\s*(.*)"));
      var1.setlocal("_variable_rx", var5);
      var3 = null;
      var1.setline(291);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\$\\(([A-Za-z][A-Za-z0-9_]*)\\)"));
      var1.setlocal("_findvar1_rx", var5);
      var3 = null;
      var1.setline(292);
      var5 = var1.getname("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\${([A-Za-z][A-Za-z0-9_]*)}"));
      var1.setlocal("_findvar2_rx", var5);
      var3 = null;
      var1.setline(294);
      var7 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, parse_makefile$10, PyString.fromInterned("Parse a Makefile-style file.\n\n    A dictionary containing name/value pairs is returned.  If an\n    optional dictionary is passed in as the second argument, it is\n    used instead of a new dictionary.\n    "));
      var1.setlocal("parse_makefile", var8);
      var3 = null;
      var1.setline(377);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, expand_makefile_vars$11, PyString.fromInterned("Expand Makefile-style variables -- \"${foo}\" or \"$(foo)\" -- in\n    'string' according to 'vars' (a dictionary mapping variable names to\n    values).  Variables not present in 'vars' are silently expanded to the\n    empty string.  The variable values in 'vars' should not contain further\n    variable expansions; if 'vars' is the output of 'parse_makefile()',\n    you're fine.  Returns a variable-expanded version of 's'.\n    "));
      var1.setlocal("expand_makefile_vars", var8);
      var3 = null;
      var1.setline(402);
      var5 = var1.getname("None");
      var1.setlocal("_config_vars", var5);
      var3 = null;
      var1.setline(404);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _init_posix$12, PyString.fromInterned("Initialize the module as appropriate for POSIX systems."));
      var1.setlocal("_init_posix", var8);
      var3 = null;
      var1.setline(413);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _init_nt$13, PyString.fromInterned("Initialize the module as appropriate for NT"));
      var1.setlocal("_init_nt", var8);
      var3 = null;
      var1.setline(432);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _init_os2$14, PyString.fromInterned("Initialize the module as appropriate for OS/2"));
      var1.setlocal("_init_os2", var8);
      var3 = null;
      var1.setline(449);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _init_jython$15, PyString.fromInterned("Initialize the module as appropriate for Jython"));
      var1.setlocal("_init_jython", var8);
      var3 = null;
      var1.setline(456);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_config_vars$16, PyString.fromInterned("With no arguments, return a dictionary of all configuration\n    variables relevant for the current platform.  Generally this includes\n    everything needed to build extensions and install both pure modules and\n    extensions.  On Unix, this means every variable defined in Python's\n    installed Makefile; on Windows and Mac OS it's a much smaller set.\n\n    With arguments, return a list of values that result from looking up\n    each argument in the configuration variable dictionary.\n    "));
      var1.setlocal("get_config_vars", var8);
      var3 = null;
      var1.setline(499);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_config_var$17, PyString.fromInterned("Return the value of a single variable using the dictionary\n    returned by 'get_config_vars()'.  Equivalent to\n    get_config_vars().get(name)\n    "));
      var1.setlocal("get_config_var", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject getJythonBinDir$1(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      PyObject var3 = var1.getglobal("sys").__getattr__("executable");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(33);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("realpath").__call__(var2, var1.getglobal("sys").__getattr__("executable")));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(35);
         var3 = var1.getglobal("Py").__getattr__("getDefaultBinDir").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _python_build$2(PyFrame var1, ThreadState var2) {
      var1.setline(60);
      PyObject var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("Setup.dist"), PyString.fromInterned("Setup.local")})).__iter__();

      PyObject var5;
      do {
         var1.setline(60);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(63);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(0, var4);
         var1.setline(61);
      } while(!var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("project_base"), (PyObject)PyString.fromInterned("Modules"), (PyObject)var1.getlocal(0))).__nonzero__());

      var1.setline(62);
      var5 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject get_python_version$3(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyString.fromInterned("Return a string containing the major and minor Python version,\n    leaving off the patchlevel.  Sample return values could be '1.5'\n    or '2.2'.\n    ");
      var1.setline(72);
      PyObject var3 = var1.getglobal("sys").__getattr__("version").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_python_inc$4(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyString.fromInterned("Return the directory containing installed Python header files.\n\n    If 'plat_specific' is false (the default), this is the path to the\n    non-platform-specific header files, i.e. Python.h and so on;\n    otherwise, this is the path to platform-specific header files\n    (namely pyconfig.h).\n\n    If 'prefix' is supplied, use it instead of sys.prefix or\n    sys.exec_prefix -- i.e., ignore 'plat_specific'.\n    ");
      var1.setline(86);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(87);
         var10000 = var1.getlocal(0);
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("EXEC_PREFIX");
         }

         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("PREFIX");
         }

         var3 = var10000;
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(89);
      var3 = var1.getglobal("os").__getattr__("name");
      var10000 = var3._eq(PyString.fromInterned("posix"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(90);
         if (var1.getglobal("python_build").__nonzero__()) {
            var1.setline(91);
            var3 = var1.getglobal("getJythonBinDir").__call__(var2);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(92);
            if (var1.getlocal(0).__nonzero__()) {
               var1.setline(94);
               var3 = var1.getlocal(2);
               var1.setlocal(3, var3);
               var3 = null;
            } else {
               var1.setline(97);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getglobal("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("srcdir"))));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(100);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("Include"));
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(101);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(102);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("include"), (PyObject)PyString.fromInterned("python")._add(var1.getglobal("get_python_version").__call__(var2)));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(103);
         PyObject var4 = var1.getglobal("os").__getattr__("name");
         var10000 = var4._eq(PyString.fromInterned("nt"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(104);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("include"));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(105);
            var4 = var1.getglobal("os").__getattr__("name");
            var10000 = var4._eq(PyString.fromInterned("os2"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getglobal("os").__getattr__("name");
               var10000 = var4._eq(PyString.fromInterned("java"));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(106);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("Include"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(108);
               throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__(var2, PyString.fromInterned("I don't know where Python installs its C header files on platform '%s'")._mod(var1.getglobal("os").__getattr__("name"))));
            }
         }
      }
   }

   public PyObject get_python_lib$5(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyString.fromInterned("Return the directory containing the Python library (standard or\n    site additions).\n\n    If 'plat_specific' is true, return the directory containing\n    platform-specific modules, i.e. any module from a non-pure-Python\n    module distribution; otherwise, return the platform-shared library\n    directory.  If 'standard_lib' is true, return the directory\n    containing standard Python library modules; otherwise, return the\n    directory for site-specific modules.\n\n    If 'prefix' is supplied, use it instead of sys.prefix or\n    sys.exec_prefix -- i.e., ignore 'plat_specific'.\n    ");
      var1.setline(127);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(128);
         var10000 = var1.getlocal(0);
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("EXEC_PREFIX");
         }

         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("PREFIX");
         }

         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(130);
      var3 = var1.getglobal("os").__getattr__("name");
      var10000 = var3._eq(PyString.fromInterned("posix"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(131);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("lib"), (PyObject)PyString.fromInterned("python")._add(var1.getglobal("get_python_version").__call__(var2)));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(133);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(134);
            var3 = var1.getlocal(3);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(136);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("site-packages"));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(138);
         PyObject var4 = var1.getglobal("os").__getattr__("name");
         var10000 = var4._eq(PyString.fromInterned("nt"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(139);
            if (var1.getlocal(1).__nonzero__()) {
               var1.setline(140);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("Lib"));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(142);
               var4 = var1.getglobal("get_python_version").__call__(var2);
               var10000 = var4._lt(PyString.fromInterned("2.2"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(143);
                  var3 = var1.getlocal(2);
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(145);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("Lib"), (PyObject)PyString.fromInterned("site-packages"));
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         } else {
            var1.setline(147);
            var4 = var1.getglobal("os").__getattr__("name");
            var10000 = var4._eq(PyString.fromInterned("os2"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var4 = var1.getglobal("os").__getattr__("name");
               var10000 = var4._eq(PyString.fromInterned("java"));
               var4 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(148);
               if (var1.getlocal(1).__nonzero__()) {
                  var1.setline(149);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("Lib"));
                  var1.f_lasti = -1;
                  return var3;
               } else {
                  var1.setline(151);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("Lib"), (PyObject)PyString.fromInterned("site-packages"));
                  var1.f_lasti = -1;
                  return var3;
               }
            } else {
               var1.setline(154);
               throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__(var2, PyString.fromInterned("I don't know where Python installs its library on platform '%s'")._mod(var1.getglobal("os").__getattr__("name"))));
            }
         }
      }
   }

   public PyObject customize_compiler$6(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      PyString.fromInterned("Do any platform-specific customization of a CCompiler instance.\n\n    Mainly needed on Unix, so we can plug in the information that\n    varies across Unices and is stored in Python's Makefile.\n    ");
      var1.setline(166);
      PyObject var3 = var1.getlocal(0).__getattr__("compiler_type");
      PyObject var10000 = var3._eq(PyString.fromInterned("unix"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(167);
         var3 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var3._eq(PyString.fromInterned("darwin"));
         var3 = null;
         PyString var7;
         if (var10000.__nonzero__()) {
            var1.setline(178);
            if (var1.getglobal("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CUSTOMIZED_OSX_COMPILER")).__not__().__nonzero__()) {
               var1.setline(179);
               var3 = imp.importOne("_osx_support", var1, -1);
               var1.setlocal(1, var3);
               var3 = null;
               var1.setline(180);
               var1.getlocal(1).__getattr__("customize_compiler").__call__(var2, var1.getglobal("_config_vars"));
               var1.setline(181);
               var7 = PyString.fromInterned("True");
               var1.getglobal("_config_vars").__setitem__((PyObject)PyString.fromInterned("CUSTOMIZED_OSX_COMPILER"), var7);
               var3 = null;
            }
         }

         var1.setline(183);
         var10000 = var1.getglobal("get_config_vars");
         PyObject[] var8 = new PyObject[]{PyString.fromInterned("CC"), PyString.fromInterned("CXX"), PyString.fromInterned("OPT"), PyString.fromInterned("CFLAGS"), PyString.fromInterned("CCSHARED"), PyString.fromInterned("LDSHARED"), PyString.fromInterned("SO"), PyString.fromInterned("AR"), PyString.fromInterned("ARFLAGS")};
         var3 = var10000.__call__(var2, var8);
         PyObject[] var4 = Py.unpackSequence(var3, 9);
         PyObject var5 = var4[0];
         var1.setlocal(2, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[2];
         var1.setlocal(4, var5);
         var5 = null;
         var5 = var4[3];
         var1.setlocal(5, var5);
         var5 = null;
         var5 = var4[4];
         var1.setlocal(6, var5);
         var5 = null;
         var5 = var4[5];
         var1.setlocal(7, var5);
         var5 = null;
         var5 = var4[6];
         var1.setlocal(8, var5);
         var5 = null;
         var5 = var4[7];
         var1.setlocal(9, var5);
         var5 = null;
         var5 = var4[8];
         var1.setlocal(10, var5);
         var5 = null;
         var3 = null;
         var1.setline(188);
         var7 = PyString.fromInterned("CC");
         var10000 = var7._in(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(189);
            var3 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("CC"));
            var1.setlocal(11, var3);
            var3 = null;
            var1.setline(190);
            var3 = var1.getglobal("sys").__getattr__("platform");
            var10000 = var3._eq(PyString.fromInterned("darwin"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var7 = PyString.fromInterned("LDSHARED");
               var10000 = var7._notin(var1.getglobal("os").__getattr__("environ"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(7).__getattr__("startswith").__call__(var2, var1.getlocal(2));
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(195);
               var3 = var1.getlocal(11)._add(var1.getlocal(7).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(2)), (PyObject)null, (PyObject)null));
               var1.setlocal(7, var3);
               var3 = null;
            }

            var1.setline(196);
            var3 = var1.getlocal(11);
            var1.setlocal(2, var3);
            var3 = null;
         }

         var1.setline(197);
         var7 = PyString.fromInterned("CXX");
         var10000 = var7._in(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(198);
            var3 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("CXX"));
            var1.setlocal(3, var3);
            var3 = null;
         }

         var1.setline(199);
         var7 = PyString.fromInterned("LDSHARED");
         var10000 = var7._in(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(200);
            var3 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("LDSHARED"));
            var1.setlocal(7, var3);
            var3 = null;
         }

         var1.setline(201);
         var7 = PyString.fromInterned("CPP");
         var10000 = var7._in(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(202);
            var3 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("CPP"));
            var1.setlocal(12, var3);
            var3 = null;
         } else {
            var1.setline(204);
            var3 = var1.getlocal(2)._add(PyString.fromInterned(" -E"));
            var1.setlocal(12, var3);
            var3 = null;
         }

         var1.setline(205);
         var7 = PyString.fromInterned("LDFLAGS");
         var10000 = var7._in(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(206);
            var3 = var1.getlocal(7)._add(PyString.fromInterned(" "))._add(var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("LDFLAGS")));
            var1.setlocal(7, var3);
            var3 = null;
         }

         var1.setline(207);
         var7 = PyString.fromInterned("CFLAGS");
         var10000 = var7._in(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(208);
            var3 = var1.getlocal(4)._add(PyString.fromInterned(" "))._add(var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("CFLAGS")));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(209);
            var3 = var1.getlocal(7)._add(PyString.fromInterned(" "))._add(var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("CFLAGS")));
            var1.setlocal(7, var3);
            var3 = null;
         }

         var1.setline(210);
         var7 = PyString.fromInterned("CPPFLAGS");
         var10000 = var7._in(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(211);
            var3 = var1.getlocal(12)._add(PyString.fromInterned(" "))._add(var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("CPPFLAGS")));
            var1.setlocal(12, var3);
            var3 = null;
            var1.setline(212);
            var3 = var1.getlocal(5)._add(PyString.fromInterned(" "))._add(var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("CPPFLAGS")));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(213);
            var3 = var1.getlocal(7)._add(PyString.fromInterned(" "))._add(var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("CPPFLAGS")));
            var1.setlocal(7, var3);
            var3 = null;
         }

         var1.setline(214);
         var7 = PyString.fromInterned("AR");
         var10000 = var7._in(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(215);
            var3 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("AR"));
            var1.setlocal(9, var3);
            var3 = null;
         }

         var1.setline(216);
         var7 = PyString.fromInterned("ARFLAGS");
         var10000 = var7._in(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(217);
            var3 = var1.getlocal(9)._add(PyString.fromInterned(" "))._add(var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("ARFLAGS")));
            var1.setlocal(13, var3);
            var3 = null;
         } else {
            var1.setline(219);
            var3 = var1.getlocal(9)._add(PyString.fromInterned(" "))._add(var1.getlocal(10));
            var1.setlocal(13, var3);
            var3 = null;
         }

         var1.setline(221);
         var3 = var1.getlocal(2)._add(PyString.fromInterned(" "))._add(var1.getlocal(5));
         var1.setlocal(14, var3);
         var3 = null;
         var1.setline(222);
         var10000 = var1.getlocal(0).__getattr__("set_executables");
         var8 = new PyObject[]{var1.getlocal(12), var1.getlocal(14), var1.getlocal(14)._add(PyString.fromInterned(" "))._add(var1.getlocal(6)), var1.getlocal(3), var1.getlocal(7), var1.getlocal(2), var1.getlocal(13)};
         String[] var6 = new String[]{"preprocessor", "compiler", "compiler_so", "compiler_cxx", "linker_so", "linker_exe", "archiver"};
         var10000.__call__(var2, var8, var6);
         var3 = null;
         var1.setline(231);
         var3 = var1.getlocal(8);
         var1.getlocal(0).__setattr__("shared_lib_extension", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_config_h_filename$7(PyFrame var1, ThreadState var2) {
      var1.setline(235);
      PyString.fromInterned("Return full pathname of installed pyconfig.h file.");
      var1.setline(236);
      PyObject var10000;
      PyObject var3;
      if (var1.getglobal("python_build").__nonzero__()) {
         var1.setline(237);
         var3 = var1.getglobal("os").__getattr__("name");
         var10000 = var3._eq(PyString.fromInterned("nt"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(238);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("project_base"), (PyObject)PyString.fromInterned("PC"));
            var1.setlocal(0, var3);
            var3 = null;
         } else {
            var1.setline(240);
            var3 = var1.getglobal("project_base");
            var1.setlocal(0, var3);
            var3 = null;
         }
      } else {
         var1.setline(242);
         var10000 = var1.getglobal("get_python_inc");
         PyObject[] var5 = new PyObject[]{Py.newInteger(1)};
         String[] var4 = new String[]{"plat_specific"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(243);
      var3 = var1.getglobal("get_python_version").__call__(var2);
      var10000 = var3._lt(PyString.fromInterned("2.2"));
      var3 = null;
      PyString var6;
      if (var10000.__nonzero__()) {
         var1.setline(244);
         var6 = PyString.fromInterned("config.h");
         var1.setlocal(1, var6);
         var3 = null;
      } else {
         var1.setline(247);
         var6 = PyString.fromInterned("pyconfig.h");
         var1.setlocal(1, var6);
         var3 = null;
      }

      var1.setline(248);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_makefile_filename$8(PyFrame var1, ThreadState var2) {
      var1.setline(252);
      PyString.fromInterned("Return full pathname of installed Makefile from the Python build.");
      var1.setline(253);
      PyObject var3;
      if (var1.getglobal("python_build").__nonzero__()) {
         var1.setline(254);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("getJythonBinDir").__call__(var2), (PyObject)PyString.fromInterned("Makefile"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(255);
         PyObject var10000 = var1.getglobal("get_python_lib");
         PyObject[] var4 = new PyObject[]{Py.newInteger(1), Py.newInteger(1)};
         String[] var5 = new String[]{"plat_specific", "standard_lib"};
         var10000 = var10000.__call__(var2, var4, var5);
         var4 = null;
         PyObject var6 = var10000;
         var1.setlocal(0, var6);
         var4 = null;
         var1.setline(256);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("config"), (PyObject)PyString.fromInterned("Makefile"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject parse_config_h$9(PyFrame var1, ThreadState var2) {
      var1.setline(265);
      PyString.fromInterned("Parse a config.h-style file.\n\n    A dictionary containing name/value pairs is returned.  If an\n    optional dictionary is passed in as the second argument, it is\n    used instead of a new dictionary.\n    ");
      var1.setline(266);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(267);
         PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(1, var7);
         var3 = null;
      }

      var1.setline(268);
      var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#define ([A-Z][A-Za-z0-9_]+) (.*)\n"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(269);
      var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/[*] #undef ([A-Z][A-Za-z0-9_]+) [*]/\n"));
      var1.setlocal(3, var3);
      var3 = null;

      while(true) {
         var1.setline(271);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(272);
         var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(273);
         if (var1.getlocal(4).__not__().__nonzero__()) {
            break;
         }

         var1.setline(275);
         var3 = var1.getlocal(2).__getattr__("match").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(276);
         if (var1.getlocal(5).__nonzero__()) {
            var1.setline(277);
            var3 = var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.setlocal(6, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(7, var5);
            var5 = null;
            var3 = null;

            try {
               var1.setline(278);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(7));
               var1.setlocal(7, var3);
               var3 = null;
            } catch (Throwable var6) {
               PyException var9 = Py.setException(var6, var1);
               if (!var9.match(var1.getglobal("ValueError"))) {
                  throw var9;
               }

               var1.setline(279);
            }

            var1.setline(280);
            var3 = var1.getlocal(7);
            var1.getlocal(1).__setitem__(var1.getlocal(6), var3);
            var3 = null;
         } else {
            var1.setline(282);
            var3 = var1.getlocal(3).__getattr__("match").__call__(var2, var1.getlocal(4));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(283);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(284);
               PyInteger var8 = Py.newInteger(0);
               var1.getlocal(1).__setitem__((PyObject)var1.getlocal(5).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var8);
               var3 = null;
            }
         }
      }

      var1.setline(285);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject parse_makefile$10(PyFrame var1, ThreadState var2) {
      var1.setline(300);
      PyString.fromInterned("Parse a Makefile-style file.\n\n    A dictionary containing name/value pairs is returned.  If an\n    optional dictionary is passed in as the second argument, it is\n    used instead of a new dictionary.\n    ");
      var1.setline(301);
      String[] var3 = new String[]{"TextFile"};
      PyObject[] var9 = imp.importFrom("distutils.text_file", var3, var1, -1);
      PyObject var4 = var9[0];
      var1.setlocal(2, var4);
      var4 = null;
      var1.setline(302);
      PyObject var10000 = var1.getlocal(2);
      var9 = new PyObject[]{var1.getlocal(0), Py.newInteger(1), Py.newInteger(1), Py.newInteger(1)};
      String[] var11 = new String[]{"strip_comments", "skip_blanks", "join_lines"};
      var10000 = var10000.__call__(var2, var9, var11);
      var3 = null;
      PyObject var10 = var10000;
      var1.setlocal(3, var10);
      var3 = null;
      var1.setline(304);
      var10 = var1.getlocal(1);
      var10000 = var10._is(var1.getglobal("None"));
      var3 = null;
      PyDictionary var13;
      if (var10000.__nonzero__()) {
         var1.setline(305);
         var13 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(1, var13);
         var3 = null;
      }

      var1.setline(306);
      var13 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(4, var13);
      var3 = null;
      var1.setline(307);
      var13 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(5, var13);
      var3 = null;

      PyObject var5;
      while(true) {
         var1.setline(309);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(310);
         var10 = var1.getlocal(3).__getattr__("readline").__call__(var2);
         var1.setlocal(6, var10);
         var3 = null;
         var1.setline(311);
         var10 = var1.getlocal(6);
         var10000 = var10._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            break;
         }

         var1.setline(313);
         var10 = var1.getglobal("_variable_rx").__getattr__("match").__call__(var2, var1.getlocal(6));
         var1.setlocal(7, var10);
         var3 = null;
         var1.setline(314);
         if (var1.getlocal(7).__nonzero__()) {
            var1.setline(315);
            var10 = var1.getlocal(7).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
            PyObject[] var12 = Py.unpackSequence(var10, 2);
            var5 = var12[0];
            var1.setlocal(8, var5);
            var5 = null;
            var5 = var12[1];
            var1.setlocal(9, var5);
            var5 = null;
            var3 = null;
            var1.setline(316);
            var10 = var1.getlocal(9).__getattr__("strip").__call__(var2);
            var1.setlocal(9, var10);
            var3 = null;
            var1.setline(318);
            var10 = var1.getlocal(9).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$$"), (PyObject)PyString.fromInterned(""));
            var1.setlocal(10, var10);
            var3 = null;
            var1.setline(320);
            PyString var14 = PyString.fromInterned("$");
            var10000 = var14._in(var1.getlocal(10));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(321);
               var10 = var1.getlocal(9);
               var1.getlocal(5).__setitem__(var1.getlocal(8), var10);
               var3 = null;
            } else {
               try {
                  var1.setline(324);
                  var10 = var1.getglobal("int").__call__(var2, var1.getlocal(9));
                  var1.setlocal(9, var10);
                  var3 = null;
               } catch (Throwable var8) {
                  PyException var15 = Py.setException(var8, var1);
                  if (var15.match(var1.getglobal("ValueError"))) {
                     var1.setline(327);
                     var4 = var1.getlocal(9).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$$"), (PyObject)PyString.fromInterned("$"));
                     var1.getlocal(4).__setitem__(var1.getlocal(8), var4);
                     var4 = null;
                     continue;
                  }

                  throw var15;
               }

               var1.setline(329);
               var4 = var1.getlocal(9);
               var1.getlocal(4).__setitem__(var1.getlocal(8), var4);
               var4 = null;
            }
         }
      }

      label97:
      while(true) {
         var1.setline(332);
         PyObject var6;
         if (!var1.getlocal(5).__nonzero__()) {
            var1.setline(365);
            var1.getlocal(3).__getattr__("close").__call__(var2);
            var1.setline(368);
            var10 = var1.getlocal(4).__getattr__("items").__call__(var2).__iter__();

            while(true) {
               var1.setline(368);
               var4 = var10.__iternext__();
               if (var4 == null) {
                  var1.setline(373);
                  var1.getlocal(1).__getattr__("update").__call__(var2, var1.getlocal(4));
                  var1.setline(374);
                  var10 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var10;
               }

               PyObject[] var18 = Py.unpackSequence(var4, 2);
               var6 = var18[0];
               var1.setlocal(16, var6);
               var6 = null;
               var6 = var18[1];
               var1.setlocal(9, var6);
               var6 = null;
               var1.setline(369);
               if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(9), var1.getglobal("str")).__nonzero__()) {
                  var1.setline(370);
                  var5 = var1.getlocal(9).__getattr__("strip").__call__(var2);
                  var1.getlocal(4).__setitem__(var1.getlocal(16), var5);
                  var5 = null;
               }
            }
         }

         var1.setline(333);
         var10 = var1.getlocal(5).__getattr__("keys").__call__(var2).__iter__();

         while(true) {
            while(true) {
               PyString var16;
               do {
                  while(true) {
                     var1.setline(333);
                     var4 = var10.__iternext__();
                     if (var4 == null) {
                        continue label97;
                     }

                     var1.setlocal(11, var4);
                     var1.setline(334);
                     var5 = var1.getlocal(5).__getitem__(var1.getlocal(11));
                     var1.setlocal(12, var5);
                     var5 = null;
                     var1.setline(335);
                     var10000 = var1.getglobal("_findvar1_rx").__getattr__("search").__call__(var2, var1.getlocal(12));
                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getglobal("_findvar2_rx").__getattr__("search").__call__(var2, var1.getlocal(12));
                     }

                     var5 = var10000;
                     var1.setlocal(7, var5);
                     var5 = null;
                     var1.setline(336);
                     if (var1.getlocal(7).__nonzero__()) {
                        var1.setline(337);
                        var5 = var1.getlocal(7).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                        var1.setlocal(8, var5);
                        var5 = null;
                        var1.setline(338);
                        var5 = var1.getglobal("True");
                        var1.setlocal(13, var5);
                        var5 = null;
                        var1.setline(339);
                        var5 = var1.getlocal(8);
                        var10000 = var5._in(var1.getlocal(4));
                        var5 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(340);
                           var5 = var1.getglobal("str").__call__(var2, var1.getlocal(4).__getitem__(var1.getlocal(8)));
                           var1.setlocal(14, var5);
                           var5 = null;
                        } else {
                           var1.setline(341);
                           var5 = var1.getlocal(8);
                           var10000 = var5._in(var1.getlocal(5));
                           var5 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(343);
                              var5 = var1.getglobal("False");
                              var1.setlocal(13, var5);
                              var5 = null;
                           } else {
                              var1.setline(344);
                              var5 = var1.getlocal(8);
                              var10000 = var5._in(var1.getglobal("os").__getattr__("environ"));
                              var5 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(346);
                                 var5 = var1.getglobal("os").__getattr__("environ").__getitem__(var1.getlocal(8));
                                 var1.setlocal(14, var5);
                                 var5 = null;
                              } else {
                                 var1.setline(348);
                                 var16 = PyString.fromInterned("");
                                 var1.getlocal(4).__setitem__((PyObject)var1.getlocal(8), var16);
                                 var1.setlocal(14, var16);
                              }
                           }
                        }

                        var1.setline(349);
                        break;
                     }

                     var1.setline(363);
                     var1.getlocal(5).__delitem__(var1.getlocal(11));
                  }
               } while(!var1.getlocal(13).__nonzero__());

               var1.setline(350);
               var5 = var1.getlocal(12).__getslice__(var1.getlocal(7).__getattr__("end").__call__(var2), (PyObject)null, (PyObject)null);
               var1.setlocal(15, var5);
               var5 = null;
               var1.setline(351);
               var5 = var1.getlocal(12).__getslice__((PyObject)null, var1.getlocal(7).__getattr__("start").__call__(var2), (PyObject)null)._add(var1.getlocal(14))._add(var1.getlocal(15));
               var1.setlocal(12, var5);
               var5 = null;
               var1.setline(352);
               var16 = PyString.fromInterned("$");
               var10000 = var16._in(var1.getlocal(15));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(353);
                  var5 = var1.getlocal(12);
                  var1.getlocal(5).__setitem__(var1.getlocal(11), var5);
                  var5 = null;
               } else {
                  label90: {
                     try {
                        var1.setline(355);
                        var5 = var1.getglobal("int").__call__(var2, var1.getlocal(12));
                        var1.setlocal(12, var5);
                        var5 = null;
                     } catch (Throwable var7) {
                        PyException var17 = Py.setException(var7, var1);
                        if (var17.match(var1.getglobal("ValueError"))) {
                           var1.setline(357);
                           var6 = var1.getlocal(12).__getattr__("strip").__call__(var2);
                           var1.getlocal(4).__setitem__(var1.getlocal(11), var6);
                           var6 = null;
                           break label90;
                        }

                        throw var17;
                     }

                     var1.setline(359);
                     var6 = var1.getlocal(12);
                     var1.getlocal(4).__setitem__(var1.getlocal(11), var6);
                     var6 = null;
                  }

                  var1.setline(360);
                  var1.getlocal(5).__delitem__(var1.getlocal(11));
               }
            }
         }
      }
   }

   public PyObject expand_makefile_vars$11(PyFrame var1, ThreadState var2) {
      var1.setline(384);
      PyString.fromInterned("Expand Makefile-style variables -- \"${foo}\" or \"$(foo)\" -- in\n    'string' according to 'vars' (a dictionary mapping variable names to\n    values).  Variables not present in 'vars' are silently expanded to the\n    empty string.  The variable values in 'vars' should not contain further\n    variable expansions; if 'vars' is the output of 'parse_makefile()',\n    you're fine.  Returns a variable-expanded version of 's'.\n    ");

      PyObject var3;
      while(true) {
         var1.setline(392);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         var1.setline(393);
         PyObject var10000 = var1.getglobal("_findvar1_rx").__getattr__("search").__call__(var2, var1.getlocal(0));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("_findvar2_rx").__getattr__("search").__call__(var2, var1.getlocal(0));
         }

         var3 = var10000;
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(394);
         if (!var1.getlocal(2).__nonzero__()) {
            break;
         }

         var1.setline(395);
         var3 = var1.getlocal(2).__getattr__("span").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(3, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(4, var5);
         var5 = null;
         var3 = null;
         var1.setline(396);
         var3 = var1.getlocal(0).__getslice__(Py.newInteger(0), var1.getlocal(3), (PyObject)null)._add(var1.getlocal(1).__getattr__("get").__call__(var2, var1.getlocal(2).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1))))._add(var1.getlocal(0).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(399);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _init_posix$12(PyFrame var1, ThreadState var2) {
      var1.setline(405);
      PyString.fromInterned("Initialize the module as appropriate for POSIX systems.");
      var1.setline(407);
      String[] var3 = new String[]{"build_time_vars"};
      PyObject[] var5 = imp.importFrom("_sysconfigdata", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(409);
      PyDictionary var6 = new PyDictionary(Py.EmptyObjects);
      var1.setglobal("_config_vars", var6);
      var3 = null;
      var1.setline(410);
      var1.getglobal("_config_vars").__getattr__("update").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _init_nt$13(PyFrame var1, ThreadState var2) {
      var1.setline(414);
      PyString.fromInterned("Initialize the module as appropriate for NT");
      var1.setline(415);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(417);
      PyObject var10000 = var1.getglobal("get_python_lib");
      PyObject[] var5 = new PyObject[]{Py.newInteger(0), Py.newInteger(1)};
      String[] var4 = new String[]{"plat_specific", "standard_lib"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("LIBDEST"), var6);
      var3 = null;
      var1.setline(418);
      var10000 = var1.getglobal("get_python_lib");
      var5 = new PyObject[]{Py.newInteger(1), Py.newInteger(1)};
      var4 = new String[]{"plat_specific", "standard_lib"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var6 = var10000;
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("BINLIBDEST"), var6);
      var3 = null;
      var1.setline(421);
      var10000 = var1.getglobal("get_python_inc");
      var5 = new PyObject[]{Py.newInteger(0)};
      var4 = new String[]{"plat_specific"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var6 = var10000;
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("INCLUDEPY"), var6);
      var3 = null;
      var1.setline(423);
      PyString var7 = PyString.fromInterned(".pyd");
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("SO"), var7);
      var3 = null;
      var1.setline(424);
      var7 = PyString.fromInterned(".exe");
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("EXE"), var7);
      var3 = null;
      var1.setline(425);
      var6 = var1.getglobal("get_python_version").__call__(var2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."), (PyObject)PyString.fromInterned(""));
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("VERSION"), var6);
      var3 = null;
      var1.setline(426);
      var6 = var1.getglobal("getJythonBinDir").__call__(var2);
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("BINDIR"), var6);
      var3 = null;
      var1.setline(429);
      var6 = var1.getlocal(0);
      var1.setglobal("_config_vars", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _init_os2$14(PyFrame var1, ThreadState var2) {
      var1.setline(433);
      PyString.fromInterned("Initialize the module as appropriate for OS/2");
      var1.setline(434);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(436);
      PyObject var10000 = var1.getglobal("get_python_lib");
      PyObject[] var5 = new PyObject[]{Py.newInteger(0), Py.newInteger(1)};
      String[] var4 = new String[]{"plat_specific", "standard_lib"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("LIBDEST"), var6);
      var3 = null;
      var1.setline(437);
      var10000 = var1.getglobal("get_python_lib");
      var5 = new PyObject[]{Py.newInteger(1), Py.newInteger(1)};
      var4 = new String[]{"plat_specific", "standard_lib"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var6 = var10000;
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("BINLIBDEST"), var6);
      var3 = null;
      var1.setline(440);
      var10000 = var1.getglobal("get_python_inc");
      var5 = new PyObject[]{Py.newInteger(0)};
      var4 = new String[]{"plat_specific"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var6 = var10000;
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("INCLUDEPY"), var6);
      var3 = null;
      var1.setline(442);
      PyString var7 = PyString.fromInterned(".pyd");
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("SO"), var7);
      var3 = null;
      var1.setline(443);
      var7 = PyString.fromInterned(".exe");
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("EXE"), var7);
      var3 = null;
      var1.setline(446);
      var6 = var1.getlocal(0);
      var1.setglobal("_config_vars", var6);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _init_jython$15(PyFrame var1, ThreadState var2) {
      var1.setline(450);
      PyString.fromInterned("Initialize the module as appropriate for Jython");
      var1.setline(453);
      var1.getglobal("_init_os2").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_config_vars$16(PyFrame var1, ThreadState var2) {
      var1.setline(465);
      PyString.fromInterned("With no arguments, return a dictionary of all configuration\n    variables relevant for the current platform.  Generally this includes\n    everything needed to build extensions and install both pure modules and\n    extensions.  On Unix, this means every variable defined in Python's\n    installed Makefile; on Windows and Mac OS it's a much smaller set.\n\n    With arguments, return a list of values that result from looking up\n    each argument in the configuration variable dictionary.\n    ");
      var1.setline(467);
      PyObject var3 = var1.getglobal("_config_vars");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(468);
         if (var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
            var1.setline(471);
            var3 = var1.getglobal("_init_jython");
            var1.setlocal(1, var3);
            var3 = null;
         } else {
            var1.setline(473);
            var3 = var1.getglobal("globals").__call__(var2).__getattr__("get").__call__(var2, PyString.fromInterned("_init_")._add(var1.getglobal("os").__getattr__("name")));
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(474);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(475);
            var1.getlocal(1).__call__(var2);
         } else {
            var1.setline(477);
            PyDictionary var5 = new PyDictionary(Py.EmptyObjects);
            var1.setglobal("_config_vars", var5);
            var3 = null;
         }

         var1.setline(482);
         var3 = var1.getglobal("PREFIX");
         var1.getglobal("_config_vars").__setitem__((PyObject)PyString.fromInterned("prefix"), var3);
         var3 = null;
         var1.setline(483);
         var3 = var1.getglobal("EXEC_PREFIX");
         var1.getglobal("_config_vars").__setitem__((PyObject)PyString.fromInterned("exec_prefix"), var3);
         var3 = null;
         var1.setline(487);
         var3 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var3._eq(PyString.fromInterned("darwin"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(488);
            var3 = imp.importOne("_osx_support", var1, -1);
            var1.setlocal(2, var3);
            var3 = null;
            var1.setline(489);
            var1.getlocal(2).__getattr__("customize_config_vars").__call__(var2, var1.getglobal("_config_vars"));
         }
      }

      var1.setline(491);
      if (!var1.getlocal(0).__nonzero__()) {
         var1.setline(497);
         var3 = var1.getglobal("_config_vars");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(492);
         PyList var6 = new PyList(Py.EmptyObjects);
         var1.setlocal(3, var6);
         var3 = null;
         var1.setline(493);
         var3 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(493);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(495);
               var3 = var1.getlocal(3);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(4, var4);
            var1.setline(494);
            var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("_config_vars").__getattr__("get").__call__(var2, var1.getlocal(4)));
         }
      }
   }

   public PyObject get_config_var$17(PyFrame var1, ThreadState var2) {
      var1.setline(503);
      PyString.fromInterned("Return the value of a single variable using the dictionary\n    returned by 'get_config_vars()'.  Equivalent to\n    get_config_vars().get(name)\n    ");
      var1.setline(504);
      PyObject var3 = var1.getglobal("get_config_vars").__call__(var2).__getattr__("get").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public sysconfig$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      getJythonBinDir$1 = Py.newCode(0, var2, var1, "getJythonBinDir", 31, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fn"};
      _python_build$2 = Py.newCode(0, var2, var1, "_python_build", 59, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      get_python_version$3 = Py.newCode(0, var2, var1, "get_python_version", 67, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"plat_specific", "prefix", "buildir", "inc_dir", "srcdir"};
      get_python_inc$4 = Py.newCode(2, var2, var1, "get_python_inc", 75, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"plat_specific", "standard_lib", "prefix", "libpython"};
      get_python_lib$5 = Py.newCode(3, var2, var1, "get_python_lib", 113, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"compiler", "_osx_support", "cc", "cxx", "opt", "cflags", "ccshared", "ldshared", "so_ext", "ar", "ar_flags", "newcc", "cpp", "archiver", "cc_cmd"};
      customize_compiler$6 = Py.newCode(1, var2, var1, "customize_compiler", 160, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"inc_dir", "config_h"};
      get_config_h_filename$7 = Py.newCode(0, var2, var1, "get_config_h_filename", 234, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"lib_dir"};
      get_makefile_filename$8 = Py.newCode(0, var2, var1, "get_makefile_filename", 251, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fp", "g", "define_rx", "undef_rx", "line", "m", "n", "v"};
      parse_config_h$9 = Py.newCode(2, var2, var1, "parse_config_h", 259, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fn", "g", "TextFile", "fp", "done", "notdone", "line", "m", "n", "v", "tmpv", "name", "value", "found", "item", "after", "k"};
      parse_makefile$10 = Py.newCode(2, var2, var1, "parse_makefile", 294, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "vars", "m", "beg", "end"};
      expand_makefile_vars$11 = Py.newCode(2, var2, var1, "expand_makefile_vars", 377, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"build_time_vars"};
      _init_posix$12 = Py.newCode(0, var2, var1, "_init_posix", 404, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"g"};
      _init_nt$13 = Py.newCode(0, var2, var1, "_init_nt", 413, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"g"};
      _init_os2$14 = Py.newCode(0, var2, var1, "_init_os2", 432, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _init_jython$15 = Py.newCode(0, var2, var1, "_init_jython", 449, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "func", "_osx_support", "vals", "name"};
      get_config_vars$16 = Py.newCode(1, var2, var1, "get_config_vars", 456, true, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      get_config_var$17 = Py.newCode(1, var2, var1, "get_config_var", 499, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new distutils.sysconfig$py("distutils/sysconfig$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(distutils.sysconfig$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.getJythonBinDir$1(var2, var3);
         case 2:
            return this._python_build$2(var2, var3);
         case 3:
            return this.get_python_version$3(var2, var3);
         case 4:
            return this.get_python_inc$4(var2, var3);
         case 5:
            return this.get_python_lib$5(var2, var3);
         case 6:
            return this.customize_compiler$6(var2, var3);
         case 7:
            return this.get_config_h_filename$7(var2, var3);
         case 8:
            return this.get_makefile_filename$8(var2, var3);
         case 9:
            return this.parse_config_h$9(var2, var3);
         case 10:
            return this.parse_makefile$10(var2, var3);
         case 11:
            return this.expand_makefile_vars$11(var2, var3);
         case 12:
            return this._init_posix$12(var2, var3);
         case 13:
            return this._init_nt$13(var2, var3);
         case 14:
            return this._init_os2$14(var2, var3);
         case 15:
            return this._init_jython$15(var2, var3);
         case 16:
            return this.get_config_vars$16(var2, var3);
         case 17:
            return this.get_config_var$17(var2, var3);
         default:
            return null;
      }
   }
}
