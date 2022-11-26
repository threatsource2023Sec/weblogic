package distutils;

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
@Filename("distutils/util.py")
public class util$py extends PyFunctionTable implements PyRunnable {
   static util$py self;
   static final PyCode f$0;
   static final PyCode get_platform$1;
   static final PyCode convert_path$2;
   static final PyCode change_root$3;
   static final PyCode check_environ$4;
   static final PyCode subst_vars$5;
   static final PyCode _subst$6;
   static final PyCode grok_environment_error$7;
   static final PyCode _init_regex$8;
   static final PyCode split_quoted$9;
   static final PyCode execute$10;
   static final PyCode strtobool$11;
   static final PyCode byte_compile$12;
   static final PyCode rfc822_escape$13;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.util\n\nMiscellaneous utility functions -- anything that doesn't fit into\none of the other *util.py modules.\n"));
      var1.setline(5);
      PyString.fromInterned("distutils.util\n\nMiscellaneous utility functions -- anything that doesn't fit into\none of the other *util.py modules.\n");
      var1.setline(7);
      PyString var3 = PyString.fromInterned("$Id: util.py 83588 2010-08-02 21:35:06Z ezio.melotti $");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(9);
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
      var1.setline(10);
      String[] var6 = new String[]{"DistutilsPlatformError"};
      PyObject[] var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"newer"};
      var7 = imp.importFrom("distutils.dep_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("newer", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"spawn"};
      var7 = imp.importFrom("distutils.spawn", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("spawn", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(14);
      var6 = new String[]{"DistutilsByteCompileError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsByteCompileError", var4);
      var4 = null;
      var1.setline(15);
      if (var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
         var1.setline(16);
         var5 = imp.importOne("_imp", var1, -1);
         var1.setlocal("_imp", var5);
         var3 = null;
      }

      var1.setline(18);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, get_platform$1, PyString.fromInterned("Return a string that identifies the current platform.  This is used\n    mainly to distinguish platform-specific build directories and\n    platform-specific built distributions.  Typically includes the OS name\n    and version and the architecture (as supplied by 'os.uname()'),\n    although the exact information included depends on the OS; eg. for IRIX\n    the architecture isn't particularly important (IRIX only runs on SGI\n    hardware), but for Linux the kernel version isn't particularly\n    important.\n\n    Examples of returned values:\n       linux-i586\n       linux-alpha (?)\n       solaris-2.6-sun4u\n       irix-5.3\n       irix64-6.2\n\n    Windows will return one of:\n       win-amd64 (64bit Windows on AMD64 (aka x86_64, Intel64, EM64T, etc)\n       win-ia64 (64bit Windows on Itanium)\n       win32 (all others - specifically, sys.platform is returned)\n\n    For other non-POSIX platforms, currently just returns 'sys.platform'.\n    "));
      var1.setlocal("get_platform", var8);
      var3 = null;
      var1.setline(187);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, convert_path$2, PyString.fromInterned("Return 'pathname' as a name that will work on the native filesystem,\n    i.e. split it on '/' and put it back together again using the current\n    directory separator.  Needed because filenames in the setup script are\n    always supplied in Unix style, and have to be converted to the local\n    convention before we can actually use them in the filesystem.  Raises\n    ValueError on non-Unix-ish systems if 'pathname' either starts or\n    ends with a slash.\n    "));
      var1.setlocal("convert_path", var8);
      var3 = null;
      var1.setline(215);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, change_root$3, PyString.fromInterned("Return 'pathname' with 'new_root' prepended.  If 'pathname' is\n    relative, this is equivalent to \"os.path.join(new_root,pathname)\".\n    Otherwise, it requires making 'pathname' relative and then joining the\n    two, which is tricky on DOS/Windows and Mac OS.\n    "));
      var1.setlocal("change_root", var8);
      var3 = null;
      var1.setline(254);
      PyInteger var9 = Py.newInteger(0);
      var1.setlocal("_environ_checked", var9);
      var3 = null;
      var1.setline(255);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, check_environ$4, PyString.fromInterned("Ensure that 'os.environ' has all the environment variables we\n    guarantee that users can use in config files, command-line options,\n    etc.  Currently this includes:\n      HOME - user's home directory (Unix only)\n      PLAT - description of the current platform, including hardware\n             and OS (see 'get_platform()')\n    "));
      var1.setlocal("check_environ", var8);
      var3 = null;
      var1.setline(277);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, subst_vars$5, PyString.fromInterned("Perform shell/Perl-style variable substitution on 'string'.  Every\n    occurrence of '$' followed by a name is considered a variable, and\n    variable is substituted by the value found in the 'local_vars'\n    dictionary, or in 'os.environ' if it's not in 'local_vars'.\n    'os.environ' is first checked/augmented to guarantee that it contains\n    certain values: see 'check_environ()'.  Raise ValueError for any\n    variables not found in either 'local_vars' or 'os.environ'.\n    "));
      var1.setlocal("subst_vars", var8);
      var3 = null;
      var1.setline(302);
      var7 = new PyObject[]{PyString.fromInterned("error: ")};
      var8 = new PyFunction(var1.f_globals, var7, grok_environment_error$7, PyString.fromInterned("Generate a useful error message from an EnvironmentError (IOError or\n    OSError) exception object.  Handles Python 1.5.1 and 1.5.2 styles, and\n    does what it can to deal with exception objects that don't have a\n    filename (which happens when the error is due to a two-file operation,\n    such as 'rename()' or 'link()'.  Returns the error message as a string\n    prefixed with 'prefix'.\n    "));
      var1.setlocal("grok_environment_error", var8);
      var3 = null;
      var1.setline(325);
      var5 = var1.getname("None");
      var1.setlocal("_wordchars_re", var5);
      var1.setlocal("_squote_re", var5);
      var1.setlocal("_dquote_re", var5);
      var1.setline(326);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _init_regex$8, (PyObject)null);
      var1.setlocal("_init_regex", var8);
      var3 = null;
      var1.setline(332);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, split_quoted$9, PyString.fromInterned("Split a string up according to Unix shell-like rules for quotes and\n    backslashes.  In short: words are delimited by spaces, as long as those\n    spaces are not escaped by a backslash, or inside a quoted string.\n    Single and double quotes are equivalent, and the quote characters can\n    be backslash-escaped.  The backslash is stripped from any two-character\n    escape sequence, leaving only the escaped character.  The quote\n    characters are stripped from any quoted string.  Returns a list of\n    words.\n    "));
      var1.setlocal("split_quoted", var8);
      var3 = null;
      var1.setline(395);
      var7 = new PyObject[]{var1.getname("None"), Py.newInteger(0), Py.newInteger(0)};
      var8 = new PyFunction(var1.f_globals, var7, execute$10, PyString.fromInterned("Perform some action that affects the outside world (eg.  by\n    writing to the filesystem).  Such actions are special because they\n    are disabled by the 'dry_run' flag.  This method takes care of all\n    that bureaucracy for you; all you have to do is supply the\n    function to call and an argument tuple for it (to embody the\n    \"external action\" being performed), and an optional message to\n    print.\n    "));
      var1.setlocal("execute", var8);
      var3 = null;
      var1.setline(414);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, strtobool$11, PyString.fromInterned("Convert a string representation of truth to true (1) or false (0).\n\n    True values are 'y', 'yes', 't', 'true', 'on', and '1'; false values\n    are 'n', 'no', 'f', 'false', 'off', and '0'.  Raises ValueError if\n    'val' is anything else.\n    "));
      var1.setlocal("strtobool", var8);
      var3 = null;
      var1.setline(430);
      var7 = new PyObject[]{Py.newInteger(0), Py.newInteger(0), var1.getname("None"), var1.getname("None"), Py.newInteger(1), Py.newInteger(0), var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, byte_compile$12, PyString.fromInterned("Byte-compile a collection of Python source files to either .pyc\n    or .pyo files in the same directory.  'py_files' is a list of files\n    to compile; any files that don't end in \".py\" are silently skipped.\n    'optimize' must be one of the following:\n      0 - don't optimize (generate .pyc)\n      1 - normal optimization (like \"python -O\")\n      2 - extra optimization (like \"python -OO\")\n    If 'force' is true, all files are recompiled regardless of\n    timestamps.\n\n    The source filename encoded in each bytecode file defaults to the\n    filenames listed in 'py_files'; you can modify these with 'prefix' and\n    'basedir'.  'prefix' is a string that will be stripped off of each\n    source filename, and 'base_dir' is a directory name that will be\n    prepended (after 'prefix' is stripped).  You can supply either or both\n    (or neither) of 'prefix' and 'base_dir', as you wish.\n\n    If 'dry_run' is true, doesn't actually do anything that would\n    affect the filesystem.\n\n    Byte-compilation is either done directly in this interpreter process\n    with the standard py_compile module, or indirectly by writing a\n    temporary script and executing it.  Normally, you should let\n    'byte_compile()' figure out to use direct compilation or not (see\n    the source for details).  The 'direct' flag is used by the script\n    generated in indirect mode; unless you know what you're doing, leave\n    it set to None.\n    "));
      var1.setlocal("byte_compile", var8);
      var3 = null;
      var1.setline(576);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, rfc822_escape$13, PyString.fromInterned("Return a version of the string escaped for inclusion in an\n    RFC-822 header, by ensuring there are 8 spaces space after each newline.\n    "));
      var1.setlocal("rfc822_escape", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_platform$1(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyString.fromInterned("Return a string that identifies the current platform.  This is used\n    mainly to distinguish platform-specific build directories and\n    platform-specific built distributions.  Typically includes the OS name\n    and version and the architecture (as supplied by 'os.uname()'),\n    although the exact information included depends on the OS; eg. for IRIX\n    the architecture isn't particularly important (IRIX only runs on SGI\n    hardware), but for Linux the kernel version isn't particularly\n    important.\n\n    Examples of returned values:\n       linux-i586\n       linux-alpha (?)\n       solaris-2.6-sun4u\n       irix-5.3\n       irix64-6.2\n\n    Windows will return one of:\n       win-amd64 (64bit Windows on AMD64 (aka x86_64, Intel64, EM64T, etc)\n       win-ia64 (64bit Windows on Itanium)\n       win32 (all others - specifically, sys.platform is returned)\n\n    For other non-POSIX platforms, currently just returns 'sys.platform'.\n    ");
      var1.setline(42);
      PyObject var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("nt"));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(44);
         PyString var8 = PyString.fromInterned(" bit (");
         var1.setlocal(0, var8);
         var3 = null;
         var1.setline(45);
         var3 = var1.getglobal("string").__getattr__("find").__call__(var2, var1.getglobal("sys").__getattr__("version"), var1.getlocal(0));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(46);
         var3 = var1.getlocal(1);
         var10000 = var3._eq(Py.newInteger(-1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(47);
            var3 = var1.getglobal("sys").__getattr__("platform");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(48);
            var4 = var1.getglobal("string").__getattr__("find").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("version"), (PyObject)PyString.fromInterned(")"), (PyObject)var1.getlocal(1));
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(49);
            var4 = var1.getglobal("sys").__getattr__("version").__getslice__(var1.getlocal(1)._add(var1.getglobal("len").__call__(var2, var1.getlocal(0))), var1.getlocal(2), (PyObject)null).__getattr__("lower").__call__(var2);
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(50);
            var4 = var1.getlocal(3);
            var10000 = var4._eq(PyString.fromInterned("amd64"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(51);
               var8 = PyString.fromInterned("win-amd64");
               var1.f_lasti = -1;
               return var8;
            } else {
               var1.setline(52);
               var4 = var1.getlocal(3);
               var10000 = var4._eq(PyString.fromInterned("itanium"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(53);
                  var8 = PyString.fromInterned("win-ia64");
                  var1.f_lasti = -1;
                  return var8;
               } else {
                  var1.setline(54);
                  var3 = var1.getglobal("sys").__getattr__("platform");
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      } else {
         var1.setline(56);
         var4 = var1.getglobal("os").__getattr__("name");
         var10000 = var4._ne(PyString.fromInterned("posix"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("uname")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(59);
            var3 = var1.getglobal("sys").__getattr__("platform");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(63);
            var4 = var1.getglobal("os").__getattr__("uname").__call__(var2);
            PyObject[] var5 = Py.unpackSequence(var4, 5);
            PyObject var6 = var5[0];
            var1.setlocal(4, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[3];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[4];
            var1.setlocal(8, var6);
            var6 = null;
            var4 = null;
            var1.setline(67);
            var4 = var1.getglobal("string").__getattr__("lower").__call__(var2, var1.getlocal(4));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(68);
            var4 = var1.getglobal("string").__getattr__("replace").__call__((ThreadState)var2, var1.getlocal(4), (PyObject)PyString.fromInterned("/"), (PyObject)PyString.fromInterned(""));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(69);
            var4 = var1.getglobal("string").__getattr__("replace").__call__((ThreadState)var2, var1.getlocal(8), (PyObject)PyString.fromInterned(" "), (PyObject)PyString.fromInterned("_"));
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(70);
            var4 = var1.getglobal("string").__getattr__("replace").__call__((ThreadState)var2, var1.getlocal(8), (PyObject)PyString.fromInterned("/"), (PyObject)PyString.fromInterned("-"));
            var1.setlocal(8, var4);
            var4 = null;
            var1.setline(72);
            var4 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
            var10000 = var4._eq(PyString.fromInterned("linux"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(76);
               var3 = PyString.fromInterned("%s-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(8)}));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(77);
               var4 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
               var10000 = var4._eq(PyString.fromInterned("sunos"));
               var4 = null;
               PyString var13;
               if (var10000.__nonzero__()) {
                  var1.setline(78);
                  var4 = var1.getlocal(6).__getitem__(Py.newInteger(0));
                  var10000 = var4._ge(PyString.fromInterned("5"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(79);
                     var13 = PyString.fromInterned("solaris");
                     var1.setlocal(4, var13);
                     var4 = null;
                     var1.setline(80);
                     var4 = PyString.fromInterned("%d.%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(6).__getitem__(Py.newInteger(0)))._sub(Py.newInteger(3)), var1.getlocal(6).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null)}));
                     var1.setlocal(6, var4);
                     var4 = null;
                  }
               } else {
                  var1.setline(82);
                  var4 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
                  var10000 = var4._eq(PyString.fromInterned("irix"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(83);
                     var3 = PyString.fromInterned("%s-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(6)}));
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(84);
                  var4 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
                  var10000 = var4._eq(PyString.fromInterned("aix"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(85);
                     var3 = PyString.fromInterned("%s-%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(7), var1.getlocal(6)}));
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(86);
                  var4 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
                  var10000 = var4._eq(PyString.fromInterned("cygwin"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(87);
                     var13 = PyString.fromInterned("cygwin");
                     var1.setlocal(4, var13);
                     var4 = null;
                     var1.setline(88);
                     var4 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[\\d.]+"));
                     var1.setlocal(9, var4);
                     var4 = null;
                     var1.setline(89);
                     var4 = var1.getlocal(9).__getattr__("match").__call__(var2, var1.getlocal(6));
                     var1.setlocal(10, var4);
                     var4 = null;
                     var1.setline(90);
                     if (var1.getlocal(10).__nonzero__()) {
                        var1.setline(91);
                        var4 = var1.getlocal(10).__getattr__("group").__call__(var2);
                        var1.setlocal(6, var4);
                        var4 = null;
                     }
                  } else {
                     var1.setline(92);
                     var4 = var1.getlocal(4).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
                     var10000 = var4._eq(PyString.fromInterned("darwin"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(99);
                        String[] var10 = new String[]{"get_config_vars"};
                        PyObject[] var11 = imp.importFrom("distutils.sysconfig", var10, var1, -1);
                        PyObject var9 = var11[0];
                        var1.setlocal(11, var9);
                        var5 = null;
                        var1.setline(100);
                        var4 = var1.getlocal(11).__call__(var2);
                        var1.setlocal(12, var4);
                        var4 = null;
                        var1.setline(102);
                        var4 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MACOSX_DEPLOYMENT_TARGET"));
                        var1.setlocal(13, var4);
                        var4 = null;
                        var1.setline(103);
                        if (var1.getlocal(13).__not__().__nonzero__()) {
                           var1.setline(104);
                           var4 = var1.getlocal(12).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MACOSX_DEPLOYMENT_TARGET"));
                           var1.setlocal(13, var4);
                           var4 = null;
                        }

                        var1.setline(106);
                        if (Py.newInteger(1).__nonzero__()) {
                           label141: {
                              var1.setline(110);
                              var4 = var1.getlocal(13);
                              var1.setlocal(14, var4);
                              var4 = null;

                              try {
                                 var1.setline(115);
                                 var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/System/Library/CoreServices/SystemVersion.plist"));
                                 var1.setlocal(15, var4);
                                 var4 = null;
                              } catch (Throwable var7) {
                                 PyException var12 = Py.setException(var7, var1);
                                 if (!var12.match(var1.getglobal("IOError"))) {
                                    throw var12;
                                 }

                                 var1.setline(119);
                                 break label141;
                              }

                              var1.setline(121);
                              var9 = var1.getglobal("re").__getattr__("search").__call__(var2, PyString.fromInterned("<key>ProductUserVisibleVersion</key>\\s*")._add(PyString.fromInterned("<string>(.*?)</string>")), var1.getlocal(15).__getattr__("read").__call__(var2));
                              var1.setlocal(10, var9);
                              var5 = null;
                              var1.setline(124);
                              var1.getlocal(15).__getattr__("close").__call__(var2);
                              var1.setline(125);
                              var9 = var1.getlocal(10);
                              var10000 = var9._isnot(var1.getglobal("None"));
                              var5 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(126);
                                 var9 = PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(10).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null));
                                 var1.setlocal(14, var9);
                                 var5 = null;
                              }
                           }
                        }

                        var1.setline(129);
                        if (var1.getlocal(13).__not__().__nonzero__()) {
                           var1.setline(130);
                           var4 = var1.getlocal(14);
                           var1.setlocal(13, var4);
                           var4 = null;
                        }

                        var1.setline(132);
                        if (var1.getlocal(13).__nonzero__()) {
                           var1.setline(133);
                           var10 = new String[]{"get_config_vars"};
                           var11 = imp.importFrom("distutils.sysconfig", var10, var1, -1);
                           var9 = var11[0];
                           var1.setlocal(11, var9);
                           var5 = null;
                           var1.setline(134);
                           var4 = var1.getlocal(13);
                           var1.setlocal(6, var4);
                           var4 = null;
                           var1.setline(135);
                           var13 = PyString.fromInterned("macosx");
                           var1.setlocal(4, var13);
                           var4 = null;
                           var1.setline(137);
                           var4 = var1.getlocal(14)._add(PyString.fromInterned("."));
                           var10000 = var4._ge(PyString.fromInterned("10.4."));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var13 = PyString.fromInterned("-arch");
                              var10000 = var13._in(var1.getlocal(11).__call__(var2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CFLAGS"), (PyObject)PyString.fromInterned("")).__getattr__("strip").__call__(var2));
                              var4 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(145);
                              var13 = PyString.fromInterned("fat");
                              var1.setlocal(8, var13);
                              var4 = null;
                              var1.setline(146);
                              var4 = var1.getlocal(11).__call__(var2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CFLAGS"));
                              var1.setlocal(16, var4);
                              var4 = null;
                              var1.setline(148);
                              var4 = var1.getglobal("re").__getattr__("findall").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-arch\\s+(\\S+)"), (PyObject)var1.getlocal(16));
                              var1.setlocal(17, var4);
                              var4 = null;
                              var1.setline(149);
                              var4 = var1.getglobal("tuple").__call__(var2, var1.getglobal("sorted").__call__(var2, var1.getglobal("set").__call__(var2, var1.getlocal(17))));
                              var1.setlocal(17, var4);
                              var4 = null;
                              var1.setline(151);
                              var4 = var1.getglobal("len").__call__(var2, var1.getlocal(17));
                              var10000 = var4._eq(Py.newInteger(1));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(152);
                                 var4 = var1.getlocal(17).__getitem__(Py.newInteger(0));
                                 var1.setlocal(8, var4);
                                 var4 = null;
                              } else {
                                 var1.setline(153);
                                 var4 = var1.getlocal(17);
                                 var10000 = var4._eq(new PyTuple(new PyObject[]{PyString.fromInterned("i386"), PyString.fromInterned("ppc")}));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(154);
                                    var13 = PyString.fromInterned("fat");
                                    var1.setlocal(8, var13);
                                    var4 = null;
                                 } else {
                                    var1.setline(155);
                                    var4 = var1.getlocal(17);
                                    var10000 = var4._eq(new PyTuple(new PyObject[]{PyString.fromInterned("i386"), PyString.fromInterned("x86_64")}));
                                    var4 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(156);
                                       var13 = PyString.fromInterned("intel");
                                       var1.setlocal(8, var13);
                                       var4 = null;
                                    } else {
                                       var1.setline(157);
                                       var4 = var1.getlocal(17);
                                       var10000 = var4._eq(new PyTuple(new PyObject[]{PyString.fromInterned("i386"), PyString.fromInterned("ppc"), PyString.fromInterned("x86_64")}));
                                       var4 = null;
                                       if (var10000.__nonzero__()) {
                                          var1.setline(158);
                                          var13 = PyString.fromInterned("fat3");
                                          var1.setlocal(8, var13);
                                          var4 = null;
                                       } else {
                                          var1.setline(159);
                                          var4 = var1.getlocal(17);
                                          var10000 = var4._eq(new PyTuple(new PyObject[]{PyString.fromInterned("ppc64"), PyString.fromInterned("x86_64")}));
                                          var4 = null;
                                          if (var10000.__nonzero__()) {
                                             var1.setline(160);
                                             var13 = PyString.fromInterned("fat64");
                                             var1.setlocal(8, var13);
                                             var4 = null;
                                          } else {
                                             var1.setline(161);
                                             var4 = var1.getlocal(17);
                                             var10000 = var4._eq(new PyTuple(new PyObject[]{PyString.fromInterned("i386"), PyString.fromInterned("ppc"), PyString.fromInterned("ppc64"), PyString.fromInterned("x86_64")}));
                                             var4 = null;
                                             if (!var10000.__nonzero__()) {
                                                var1.setline(164);
                                                throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Don't know machine value for archs=%r")._mod(new PyTuple(new PyObject[]{var1.getlocal(17)}))));
                                             }

                                             var1.setline(162);
                                             var13 = PyString.fromInterned("universal");
                                             var1.setlocal(8, var13);
                                             var4 = null;
                                          }
                                       }
                                    }
                                 }
                              }
                           } else {
                              var1.setline(167);
                              var4 = var1.getlocal(8);
                              var10000 = var4._eq(PyString.fromInterned("i386"));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(171);
                                 var4 = var1.getglobal("sys").__getattr__("maxint");
                                 var10000 = var4._ge(Py.newInteger(2)._pow(Py.newInteger(32)));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(172);
                                    var13 = PyString.fromInterned("x86_64");
                                    var1.setlocal(8, var13);
                                    var4 = null;
                                 }
                              } else {
                                 var1.setline(174);
                                 var4 = var1.getlocal(8);
                                 var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("PowerPC"), PyString.fromInterned("Power_Macintosh")}));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(176);
                                    var13 = PyString.fromInterned("ppc");
                                    var1.setlocal(8, var13);
                                    var4 = null;
                                    var1.setline(179);
                                    var4 = var1.getglobal("sys").__getattr__("maxint");
                                    var10000 = var4._ge(Py.newInteger(2)._pow(Py.newInteger(32)));
                                    var4 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(180);
                                       var13 = PyString.fromInterned("ppc64");
                                       var1.setlocal(8, var13);
                                       var4 = null;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               var1.setline(182);
               var3 = PyString.fromInterned("%s-%s-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(6), var1.getlocal(8)}));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject convert_path$2(PyFrame var1, ThreadState var2) {
      var1.setline(195);
      PyString.fromInterned("Return 'pathname' as a name that will work on the native filesystem,\n    i.e. split it on '/' and put it back together again using the current\n    directory separator.  Needed because filenames in the setup script are\n    always supplied in Unix style, and have to be converted to the local\n    convention before we can actually use them in the filesystem.  Raises\n    ValueError on non-Unix-ish systems if 'pathname' either starts or\n    ends with a slash.\n    ");
      var1.setline(196);
      PyObject var3 = var1.getglobal("os").__getattr__("sep");
      PyObject var10000 = var3._eq(PyString.fromInterned("/"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(197);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(198);
         if (var1.getlocal(0).__not__().__nonzero__()) {
            var1.setline(199);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(200);
            PyObject var4 = var1.getlocal(0).__getitem__(Py.newInteger(0));
            var10000 = var4._eq(PyString.fromInterned("/"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(201);
               throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("path '%s' cannot be absolute")._mod(var1.getlocal(0)));
            } else {
               var1.setline(202);
               var4 = var1.getlocal(0).__getitem__(Py.newInteger(-1));
               var10000 = var4._eq(PyString.fromInterned("/"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(203);
                  throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("path '%s' cannot end with '/'")._mod(var1.getlocal(0)));
               } else {
                  var1.setline(205);
                  var4 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("/"));
                  var1.setlocal(1, var4);
                  var4 = null;

                  while(true) {
                     var1.setline(206);
                     PyString var6 = PyString.fromInterned(".");
                     var10000 = var6._in(var1.getlocal(1));
                     var4 = null;
                     if (!var10000.__nonzero__()) {
                        var1.setline(208);
                        if (var1.getlocal(1).__not__().__nonzero__()) {
                           var1.setline(209);
                           var3 = var1.getglobal("os").__getattr__("curdir");
                           var1.f_lasti = -1;
                           return var3;
                        } else {
                           var1.setline(210);
                           var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
                           PyObject[] var7 = Py.EmptyObjects;
                           String[] var5 = new String[0];
                           var10000 = var10000._callextra(var7, var5, var1.getlocal(1), (PyObject)null);
                           var4 = null;
                           var3 = var10000;
                           var1.f_lasti = -1;
                           return var3;
                        }
                     }

                     var1.setline(207);
                     var1.getlocal(1).__getattr__("remove").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
                  }
               }
            }
         }
      }
   }

   public PyObject change_root$3(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      PyString.fromInterned("Return 'pathname' with 'new_root' prepended.  If 'pathname' is\n    relative, this is equivalent to \"os.path.join(new_root,pathname)\".\n    Otherwise, it requires making 'pathname' relative and then joining the\n    two, which is tricky on DOS/Windows and Mac OS.\n    ");
      var1.setline(221);
      var1.setline(221);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__() ? var1.getglobal("os").__getattr__("_name") : var1.getglobal("os").__getattr__("name");
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(222);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(PyString.fromInterned("posix"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(223);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
            var1.setline(224);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(226);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(1).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null));
            var1.f_lasti = -1;
            return var3;
         }
      } else {
         var1.setline(228);
         PyObject var4 = var1.getlocal(2);
         var10000 = var4._eq(PyString.fromInterned("nt"));
         var4 = null;
         PyObject[] var5;
         PyObject var6;
         if (var10000.__nonzero__()) {
            var1.setline(229);
            var4 = var1.getglobal("os").__getattr__("path").__getattr__("splitdrive").__call__(var2, var1.getlocal(1));
            var5 = Py.unpackSequence(var4, 2);
            var6 = var5[0];
            var1.setlocal(3, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(4, var6);
            var6 = null;
            var4 = null;
            var1.setline(230);
            var4 = var1.getlocal(4).__getitem__(Py.newInteger(0));
            var10000 = var4._eq(PyString.fromInterned("\\"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(231);
               var4 = var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
               var1.setlocal(4, var4);
               var4 = null;
            }

            var1.setline(232);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(4));
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(234);
            var4 = var1.getlocal(2);
            var10000 = var4._eq(PyString.fromInterned("os2"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(235);
               var4 = var1.getglobal("os").__getattr__("path").__getattr__("splitdrive").__call__(var2, var1.getlocal(1));
               var5 = Py.unpackSequence(var4, 2);
               var6 = var5[0];
               var1.setlocal(3, var6);
               var6 = null;
               var6 = var5[1];
               var1.setlocal(4, var6);
               var6 = null;
               var4 = null;
               var1.setline(236);
               var4 = var1.getlocal(4).__getitem__(Py.newInteger(0));
               var10000 = var4._eq(var1.getglobal("os").__getattr__("sep"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(237);
                  var4 = var1.getlocal(4).__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
                  var1.setlocal(4, var4);
                  var4 = null;
               }

               var1.setline(238);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(4));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(240);
               var4 = var1.getlocal(2);
               var10000 = var4._eq(PyString.fromInterned("mac"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(241);
                  if (var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getlocal(1)).__not__().__nonzero__()) {
                     var1.setline(242);
                     var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(1));
                     var1.f_lasti = -1;
                     return var3;
                  } else {
                     var1.setline(245);
                     var4 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned(":"), (PyObject)Py.newInteger(1));
                     var1.setlocal(5, var4);
                     var4 = null;
                     var1.setline(246);
                     var4 = PyString.fromInterned(":")._add(var1.getlocal(5).__getitem__(Py.newInteger(1)));
                     var1.setlocal(1, var4);
                     var4 = null;
                     var1.setline(247);
                     var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(1));
                     var1.f_lasti = -1;
                     return var3;
                  }
               } else {
                  var1.setline(250);
                  throw Py.makeException(var1.getglobal("DistutilsPlatformError"), PyString.fromInterned("nothing known about platform '%s'")._mod(var1.getlocal(2)));
               }
            }
         }
      }
   }

   public PyObject check_environ$4(PyFrame var1, ThreadState var2) {
      var1.setline(262);
      PyString.fromInterned("Ensure that 'os.environ' has all the environment variables we\n    guarantee that users can use in config files, command-line options,\n    etc.  Currently this includes:\n      HOME - user's home directory (Unix only)\n      PLAT - description of the current platform, including hardware\n             and OS (see 'get_platform()')\n    ");
      var1.setline(264);
      if (var1.getglobal("_environ_checked").__nonzero__()) {
         var1.setline(265);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(267);
         PyObject var3 = var1.getglobal("os").__getattr__("name");
         PyObject var10000 = var3._eq(PyString.fromInterned("posix"));
         var3 = null;
         PyString var4;
         if (var10000.__nonzero__()) {
            var4 = PyString.fromInterned("HOME");
            var10000 = var4._notin(var1.getglobal("os").__getattr__("environ"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(268);
            var3 = imp.importOne("pwd", var1, -1);
            var1.setlocal(0, var3);
            var3 = null;
            var1.setline(269);
            var3 = var1.getlocal(0).__getattr__("getpwuid").__call__(var2, var1.getglobal("os").__getattr__("getuid").__call__(var2)).__getitem__(Py.newInteger(5));
            var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("HOME"), var3);
            var3 = null;
         }

         var1.setline(271);
         var4 = PyString.fromInterned("PLAT");
         var10000 = var4._notin(var1.getglobal("os").__getattr__("environ"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(272);
            var3 = var1.getglobal("get_platform").__call__(var2);
            var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("PLAT"), var3);
            var3 = null;
         }

         var1.setline(274);
         PyInteger var5 = Py.newInteger(1);
         var1.setglobal("_environ_checked", var5);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject subst_vars$5(PyFrame var1, ThreadState var2) {
      var1.setline(285);
      PyString.fromInterned("Perform shell/Perl-style variable substitution on 'string'.  Every\n    occurrence of '$' followed by a name is considered a variable, and\n    variable is substituted by the value found in the 'local_vars'\n    dictionary, or in 'os.environ' if it's not in 'local_vars'.\n    'os.environ' is first checked/augmented to guarantee that it contains\n    certain values: see 'check_environ()'.  Raise ValueError for any\n    variables not found in either 'local_vars' or 'os.environ'.\n    ");
      var1.setline(286);
      var1.getglobal("check_environ").__call__(var2);
      var1.setline(287);
      PyObject[] var3 = new PyObject[]{var1.getlocal(1)};
      PyFunction var7 = new PyFunction(var1.f_globals, var3, _subst$6, (PyObject)null);
      var1.setlocal(2, var7);
      var3 = null;

      try {
         var1.setline(295);
         PyObject var8 = var1.getglobal("re").__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("\\$([a-zA-Z_][a-zA-Z_0-9]*)"), (PyObject)var1.getlocal(2), (PyObject)var1.getlocal(0));
         var1.f_lasti = -1;
         return var8;
      } catch (Throwable var6) {
         PyException var4 = Py.setException(var6, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            PyObject var5 = var4.value;
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(297);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("invalid variable '$%s'")._mod(var1.getlocal(3)));
         } else {
            throw var4;
         }
      }
   }

   public PyObject _subst$6(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyObject var3 = var1.getlocal(0).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(289);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(290);
         var3 = var1.getglobal("str").__call__(var2, var1.getlocal(1).__getitem__(var1.getlocal(2)));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(292);
         var3 = var1.getglobal("os").__getattr__("environ").__getitem__(var1.getlocal(2));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject grok_environment_error$7(PyFrame var1, ThreadState var2) {
      var1.setline(309);
      PyString.fromInterned("Generate a useful error message from an EnvironmentError (IOError or\n    OSError) exception object.  Handles Python 1.5.1 and 1.5.2 styles, and\n    does what it can to deal with exception objects that don't have a\n    filename (which happens when the error is due to a two-file operation,\n    such as 'rename()' or 'link()'.  Returns the error message as a string\n    prefixed with 'prefix'.\n    ");
      var1.setline(311);
      PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("filename"));
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("strerror"));
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(312);
         if (var1.getlocal(0).__getattr__("filename").__nonzero__()) {
            var1.setline(313);
            var3 = var1.getlocal(1)._add(PyString.fromInterned("%s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("filename"), var1.getlocal(0).__getattr__("strerror")})));
            var1.setlocal(2, var3);
            var3 = null;
         } else {
            var1.setline(317);
            var3 = var1.getlocal(1)._add(PyString.fromInterned("%s")._mod(var1.getlocal(0).__getattr__("strerror")));
            var1.setlocal(2, var3);
            var3 = null;
         }
      } else {
         var1.setline(319);
         var3 = var1.getlocal(1)._add(var1.getglobal("str").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(-1))));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(321);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _init_regex$8(PyFrame var1, ThreadState var2) {
      var1.setline(328);
      PyObject var3 = var1.getglobal("re").__getattr__("compile").__call__(var2, PyString.fromInterned("[^\\\\\\'\\\"%s ]*")._mod(var1.getglobal("string").__getattr__("whitespace")));
      var1.setglobal("_wordchars_re", var3);
      var3 = null;
      var1.setline(329);
      var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("'(?:[^'\\\\]|\\\\.)*'"));
      var1.setglobal("_squote_re", var3);
      var3 = null;
      var1.setline(330);
      var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\"(?:[^\"\\\\]|\\\\.)*\""));
      var1.setglobal("_dquote_re", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject split_quoted$9(PyFrame var1, ThreadState var2) {
      var1.setline(341);
      PyString.fromInterned("Split a string up according to Unix shell-like rules for quotes and\n    backslashes.  In short: words are delimited by spaces, as long as those\n    spaces are not escaped by a backslash, or inside a quoted string.\n    Single and double quotes are equivalent, and the quote characters can\n    be backslash-escaped.  The backslash is stripped from any two-character\n    escape sequence, leaving only the escaped character.  The quote\n    characters are stripped from any quoted string.  Returns a list of\n    words.\n    ");
      var1.setline(346);
      PyObject var3 = var1.getglobal("_wordchars_re");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(346);
         var1.getglobal("_init_regex").__call__(var2);
      }

      var1.setline(348);
      var3 = var1.getglobal("string").__getattr__("strip").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(349);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(350);
      PyInteger var7 = Py.newInteger(0);
      var1.setlocal(2, var7);
      var3 = null;

      while(true) {
         var1.setline(352);
         if (!var1.getlocal(0).__nonzero__()) {
            break;
         }

         var1.setline(353);
         var3 = var1.getglobal("_wordchars_re").__getattr__("match").__call__(var2, var1.getlocal(0), var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(354);
         var3 = var1.getlocal(3).__getattr__("end").__call__(var2);
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(355);
         var3 = var1.getlocal(4);
         var10000 = var3._eq(var1.getglobal("len").__call__(var2, var1.getlocal(0)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(356);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null));
            break;
         }

         var1.setline(359);
         var3 = var1.getlocal(0).__getitem__(var1.getlocal(4));
         var10000 = var3._in(var1.getglobal("string").__getattr__("whitespace"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(360);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null));
            var1.setline(361);
            var3 = var1.getglobal("string").__getattr__("lstrip").__call__(var2, var1.getlocal(0).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null));
            var1.setlocal(0, var3);
            var3 = null;
            var1.setline(362);
            var7 = Py.newInteger(0);
            var1.setlocal(2, var7);
            var3 = null;
         } else {
            var1.setline(364);
            var3 = var1.getlocal(0).__getitem__(var1.getlocal(4));
            var10000 = var3._eq(PyString.fromInterned("\\"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(366);
               var3 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(4), (PyObject)null)._add(var1.getlocal(0).__getslice__(var1.getlocal(4)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null));
               var1.setlocal(0, var3);
               var3 = null;
               var1.setline(367);
               var3 = var1.getlocal(4)._add(Py.newInteger(1));
               var1.setlocal(2, var3);
               var3 = null;
            } else {
               var1.setline(370);
               var3 = var1.getlocal(0).__getitem__(var1.getlocal(4));
               var10000 = var3._eq(PyString.fromInterned("'"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(371);
                  var3 = var1.getglobal("_squote_re").__getattr__("match").__call__(var2, var1.getlocal(0), var1.getlocal(4));
                  var1.setlocal(3, var3);
                  var3 = null;
               } else {
                  var1.setline(372);
                  var3 = var1.getlocal(0).__getitem__(var1.getlocal(4));
                  var10000 = var3._eq(PyString.fromInterned("\""));
                  var3 = null;
                  if (!var10000.__nonzero__()) {
                     var1.setline(375);
                     throw Py.makeException(var1.getglobal("RuntimeError"), PyString.fromInterned("this can't happen (bad char '%c')")._mod(var1.getlocal(0).__getitem__(var1.getlocal(4))));
                  }

                  var1.setline(373);
                  var3 = var1.getglobal("_dquote_re").__getattr__("match").__call__(var2, var1.getlocal(0), var1.getlocal(4));
                  var1.setlocal(3, var3);
                  var3 = null;
               }

               var1.setline(378);
               var3 = var1.getlocal(3);
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(379);
                  throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("bad string (mismatched %s quotes?)")._mod(var1.getlocal(0).__getitem__(var1.getlocal(4))));
               }

               var1.setline(382);
               var3 = var1.getlocal(3).__getattr__("span").__call__(var2);
               PyObject[] var4 = Py.unpackSequence(var3, 2);
               PyObject var5 = var4[0];
               var1.setlocal(5, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(4, var5);
               var5 = null;
               var3 = null;
               var1.setline(383);
               var3 = var1.getlocal(0).__getslice__((PyObject)null, var1.getlocal(5), (PyObject)null)._add(var1.getlocal(0).__getslice__(var1.getlocal(5)._add(Py.newInteger(1)), var1.getlocal(4)._sub(Py.newInteger(1)), (PyObject)null))._add(var1.getlocal(0).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null));
               var1.setlocal(0, var3);
               var3 = null;
               var1.setline(384);
               var3 = var1.getlocal(3).__getattr__("end").__call__(var2)._sub(Py.newInteger(2));
               var1.setlocal(2, var3);
               var3 = null;
            }
         }

         var1.setline(386);
         var3 = var1.getlocal(2);
         var10000 = var3._ge(var1.getglobal("len").__call__(var2, var1.getlocal(0)));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(387);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0));
            break;
         }
      }

      var1.setline(390);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject execute$10(PyFrame var1, ThreadState var2) {
      var1.setline(403);
      PyString.fromInterned("Perform some action that affects the outside world (eg.  by\n    writing to the filesystem).  Such actions are special because they\n    are disabled by the 'dry_run' flag.  This method takes care of all\n    that bureaucracy for you; all you have to do is supply the\n    function to call and an argument tuple for it (to embody the\n    \"external action\" being performed), and an optional message to\n    print.\n    ");
      var1.setline(404);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(405);
         var3 = PyString.fromInterned("%s%r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__name__"), var1.getlocal(1)}));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(406);
         var3 = var1.getlocal(2).__getslice__(Py.newInteger(-2), (PyObject)null, (PyObject)null);
         var10000 = var3._eq(PyString.fromInterned(",)"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(407);
            var3 = var1.getlocal(2).__getslice__(Py.newInteger(0), Py.newInteger(-2), (PyObject)null)._add(PyString.fromInterned(")"));
            var1.setlocal(2, var3);
            var3 = null;
         }
      }

      var1.setline(409);
      var1.getglobal("log").__getattr__("info").__call__(var2, var1.getlocal(2));
      var1.setline(410);
      if (var1.getlocal(4).__not__().__nonzero__()) {
         var1.setline(411);
         var10000 = var1.getlocal(0);
         PyObject[] var5 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000._callextra(var5, var4, var1.getlocal(1), (PyObject)null);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject strtobool$11(PyFrame var1, ThreadState var2) {
      var1.setline(420);
      PyString.fromInterned("Convert a string representation of truth to true (1) or false (0).\n\n    True values are 'y', 'yes', 't', 'true', 'on', and '1'; false values\n    are 'n', 'no', 'f', 'false', 'off', and '0'.  Raises ValueError if\n    'val' is anything else.\n    ");
      var1.setline(421);
      PyObject var3 = var1.getglobal("string").__getattr__("lower").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(422);
      var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("y"), PyString.fromInterned("yes"), PyString.fromInterned("t"), PyString.fromInterned("true"), PyString.fromInterned("on"), PyString.fromInterned("1")}));
      var3 = null;
      PyInteger var5;
      if (var10000.__nonzero__()) {
         var1.setline(423);
         var5 = Py.newInteger(1);
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(424);
         PyObject var4 = var1.getlocal(0);
         var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("n"), PyString.fromInterned("no"), PyString.fromInterned("f"), PyString.fromInterned("false"), PyString.fromInterned("off"), PyString.fromInterned("0")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(425);
            var5 = Py.newInteger(0);
            var1.f_lasti = -1;
            return var5;
         } else {
            var1.setline(427);
            throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("invalid truth value %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(0)})));
         }
      }
   }

   public PyObject byte_compile$12(PyFrame var1, ThreadState var2) {
      var1.setline(462);
      PyString.fromInterned("Byte-compile a collection of Python source files to either .pyc\n    or .pyo files in the same directory.  'py_files' is a list of files\n    to compile; any files that don't end in \".py\" are silently skipped.\n    'optimize' must be one of the following:\n      0 - don't optimize (generate .pyc)\n      1 - normal optimization (like \"python -O\")\n      2 - extra optimization (like \"python -OO\")\n    If 'force' is true, all files are recompiled regardless of\n    timestamps.\n\n    The source filename encoded in each bytecode file defaults to the\n    filenames listed in 'py_files'; you can modify these with 'prefix' and\n    'basedir'.  'prefix' is a string that will be stripped off of each\n    source filename, and 'base_dir' is a directory name that will be\n    prepended (after 'prefix' is stripped).  You can supply either or both\n    (or neither) of 'prefix' and 'base_dir', as you wish.\n\n    If 'dry_run' is true, doesn't actually do anything that would\n    affect the filesystem.\n\n    Byte-compilation is either done directly in this interpreter process\n    with the standard py_compile module, or indirectly by writing a\n    temporary script and executing it.  Normally, you should let\n    'byte_compile()' figure out to use direct compilation or not (see\n    the source for details).  The 'direct' flag is used by the script\n    generated in indirect mode; unless you know what you're doing, leave\n    it set to None.\n    ");
      var1.setline(464);
      if (var1.getglobal("sys").__getattr__("dont_write_bytecode").__nonzero__()) {
         var1.setline(465);
         throw Py.makeException(var1.getglobal("DistutilsByteCompileError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("byte-compiling is disabled.")));
      } else {
         var1.setline(477);
         PyObject var3 = var1.getlocal(7);
         PyObject var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(478);
            var10000 = var1.getglobal("__debug__");
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(1);
               var10000 = var3._eq(Py.newInteger(0));
               var3 = null;
            }

            var3 = var10000;
            var1.setlocal(7, var3);
            var3 = null;
         }

         var1.setline(482);
         PyObject var4;
         PyObject var5;
         if (var1.getlocal(7).__not__().__nonzero__()) {
            String[] var8;
            PyObject[] var9;
            PyObject[] var13;
            try {
               var1.setline(484);
               String[] var12 = new String[]{"mkstemp"};
               var13 = imp.importFrom("tempfile", var12, var1, -1);
               var4 = var13[0];
               var1.setlocal(8, var4);
               var4 = null;
               var1.setline(485);
               var3 = var1.getlocal(8).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".py"));
               var9 = Py.unpackSequence(var3, 2);
               var5 = var9[0];
               var1.setlocal(9, var5);
               var5 = null;
               var5 = var9[1];
               var1.setlocal(10, var5);
               var5 = null;
               var3 = null;
            } catch (Throwable var7) {
               PyException var11 = Py.setException(var7, var1);
               if (!var11.match(var1.getglobal("ImportError"))) {
                  throw var11;
               }

               var1.setline(487);
               var8 = new String[]{"mktemp"};
               var9 = imp.importFrom("tempfile", var8, var1, -1);
               var5 = var9[0];
               var1.setlocal(11, var5);
               var5 = null;
               var1.setline(488);
               PyTuple var10 = new PyTuple(new PyObject[]{var1.getglobal("None"), var1.getlocal(11).__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".py"))});
               PyObject[] var15 = Py.unpackSequence(var10, 2);
               PyObject var6 = var15[0];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var15[1];
               var1.setlocal(10, var6);
               var6 = null;
               var4 = null;
            }

            var1.setline(489);
            var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("writing byte-compilation script '%s'"), (PyObject)var1.getlocal(10));
            var1.setline(490);
            if (var1.getlocal(6).__not__().__nonzero__()) {
               var1.setline(491);
               var3 = var1.getlocal(9);
               var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(492);
                  var3 = var1.getglobal("os").__getattr__("fdopen").__call__((ThreadState)var2, (PyObject)var1.getlocal(9), (PyObject)PyString.fromInterned("w"));
                  var1.setlocal(12, var3);
                  var3 = null;
               } else {
                  var1.setline(494);
                  var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(10), (PyObject)PyString.fromInterned("w"));
                  var1.setlocal(12, var3);
                  var3 = null;
               }

               var1.setline(496);
               var1.getlocal(12).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("from distutils.util import byte_compile\nfiles = [\n"));
               var1.setline(515);
               var1.getlocal(12).__getattr__("write").__call__(var2, var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("map").__call__(var2, var1.getglobal("repr"), var1.getlocal(0)), (PyObject)PyString.fromInterned(",\n"))._add(PyString.fromInterned("]\n")));
               var1.setline(516);
               var1.getlocal(12).__getattr__("write").__call__(var2, PyString.fromInterned("\nbyte_compile(files, optimize=%r, force=%r,\n             prefix=%r, base_dir=%r,\n             verbose=%r, dry_run=0,\n             direct=1)\n")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)})));
               var1.setline(523);
               var1.getlocal(12).__getattr__("close").__call__(var2);
            }

            var1.setline(525);
            PyList var14 = new PyList(new PyObject[]{var1.getglobal("sys").__getattr__("executable"), var1.getlocal(10)});
            var1.setlocal(13, var14);
            var3 = null;
            var1.setline(526);
            var3 = var1.getlocal(1);
            var10000 = var3._eq(Py.newInteger(1));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(527);
               var1.getlocal(13).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("-O"));
            } else {
               var1.setline(528);
               var3 = var1.getlocal(1);
               var10000 = var3._eq(Py.newInteger(2));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(529);
                  var1.getlocal(13).__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)PyString.fromInterned("-OO"));
               }
            }

            var1.setline(530);
            var10000 = var1.getglobal("spawn");
            var13 = new PyObject[]{var1.getlocal(13), var1.getlocal(6)};
            var8 = new String[]{"dry_run"};
            var10000.__call__(var2, var13, var8);
            var3 = null;
            var1.setline(531);
            var10000 = var1.getglobal("execute");
            var13 = new PyObject[]{var1.getglobal("os").__getattr__("remove"), new PyTuple(new PyObject[]{var1.getlocal(10)}), PyString.fromInterned("removing %s")._mod(var1.getlocal(10)), var1.getlocal(6)};
            var8 = new String[]{"dry_run"};
            var10000.__call__(var2, var13, var8);
            var3 = null;
         } else {
            var1.setline(539);
            var3 = imp.importOne("py_compile", var1, -1);
            var1.setlocal(14, var3);
            var3 = null;
            var1.setline(541);
            var3 = var1.getlocal(0).__iter__();

            while(true) {
               var1.setline(541);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(15, var4);
               var1.setline(542);
               var5 = var1.getlocal(15).__getslice__(Py.newInteger(-3), (PyObject)null, (PyObject)null);
               var10000 = var5._ne(PyString.fromInterned(".py"));
               var5 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(550);
                  if (var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__nonzero__()) {
                     var1.setline(551);
                     var5 = var1.getglobal("_imp").__getattr__("makeCompiledFilename").__call__(var2, var1.getlocal(15));
                     var1.setlocal(16, var5);
                     var5 = null;
                  } else {
                     var1.setline(553);
                     var10000 = var1.getlocal(15);
                     Object var10001 = var1.getglobal("__debug__");
                     if (((PyObject)var10001).__nonzero__()) {
                        var10001 = PyString.fromInterned("c");
                     }

                     if (!((PyObject)var10001).__nonzero__()) {
                        var10001 = PyString.fromInterned("o");
                     }

                     var5 = var10000._add((PyObject)var10001);
                     var1.setlocal(16, var5);
                     var5 = null;
                  }

                  var1.setline(554);
                  var5 = var1.getlocal(15);
                  var1.setlocal(17, var5);
                  var5 = null;
                  var1.setline(555);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(556);
                     var5 = var1.getlocal(15).__getslice__((PyObject)null, var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)null);
                     var10000 = var5._ne(var1.getlocal(3));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(557);
                        throw Py.makeException(var1.getglobal("ValueError"), PyString.fromInterned("invalid prefix: filename %r doesn't start with %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(15), var1.getlocal(3)})));
                     }

                     var1.setline(560);
                     var5 = var1.getlocal(17).__getslice__(var1.getglobal("len").__call__(var2, var1.getlocal(3)), (PyObject)null, (PyObject)null);
                     var1.setlocal(17, var5);
                     var5 = null;
                  }

                  var1.setline(561);
                  if (var1.getlocal(4).__nonzero__()) {
                     var1.setline(562);
                     var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), var1.getlocal(17));
                     var1.setlocal(17, var5);
                     var5 = null;
                  }

                  var1.setline(564);
                  var5 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(16));
                  var1.setlocal(18, var5);
                  var5 = null;
                  var1.setline(565);
                  if (var1.getlocal(7).__nonzero__()) {
                     var1.setline(566);
                     var10000 = var1.getlocal(2);
                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getglobal("newer").__call__(var2, var1.getlocal(15), var1.getlocal(16));
                     }

                     if (var10000.__nonzero__()) {
                        var1.setline(567);
                        var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, PyString.fromInterned("byte-compiling %s to %s"), (PyObject)var1.getlocal(15), (PyObject)var1.getlocal(18));
                        var1.setline(568);
                        if (var1.getlocal(6).__not__().__nonzero__()) {
                           var1.setline(569);
                           var1.getlocal(14).__getattr__("compile").__call__(var2, var1.getlocal(15), var1.getlocal(16), var1.getlocal(17));
                        }
                     } else {
                        var1.setline(571);
                        var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, PyString.fromInterned("skipping byte-compilation of %s to %s"), (PyObject)var1.getlocal(15), (PyObject)var1.getlocal(18));
                     }
                  }
               }
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject rfc822_escape$13(PyFrame var1, ThreadState var2) {
      var1.setline(579);
      PyString.fromInterned("Return a version of the string escaped for inclusion in an\n    RFC-822 header, by ensuring there are 8 spaces space after each newline.\n    ");
      var1.setline(580);
      PyObject var3 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(581);
      var3 = var1.getglobal("string").__getattr__("join").__call__(var2, var1.getlocal(1), PyString.fromInterned("\n")._add(Py.newInteger(8)._mul(PyString.fromInterned(" "))));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(582);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"prefix", "i", "j", "look", "osname", "host", "release", "version", "machine", "rel_re", "m", "get_config_vars", "cfgvars", "macver", "macrelease", "f", "cflags", "archs"};
      get_platform$1 = Py.newCode(0, var2, var1, "get_platform", 18, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pathname", "paths"};
      convert_path$2 = Py.newCode(1, var2, var1, "convert_path", 187, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"new_root", "pathname", "os_name", "drive", "path", "elements"};
      change_root$3 = Py.newCode(2, var2, var1, "change_root", 215, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"pwd"};
      check_environ$4 = Py.newCode(0, var2, var1, "check_environ", 255, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "local_vars", "_subst", "var"};
      subst_vars$5 = Py.newCode(2, var2, var1, "subst_vars", 277, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"match", "local_vars", "var_name"};
      _subst$6 = Py.newCode(2, var2, var1, "_subst", 287, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"exc", "prefix", "error"};
      grok_environment_error$7 = Py.newCode(2, var2, var1, "grok_environment_error", 302, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _init_regex$8 = Py.newCode(0, var2, var1, "_init_regex", 326, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "words", "pos", "m", "end", "beg"};
      split_quoted$9 = Py.newCode(1, var2, var1, "split_quoted", 332, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"func", "args", "msg", "verbose", "dry_run"};
      execute$10 = Py.newCode(5, var2, var1, "execute", 395, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"val"};
      strtobool$11 = Py.newCode(1, var2, var1, "strtobool", 414, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"py_files", "optimize", "force", "prefix", "base_dir", "verbose", "dry_run", "direct", "mkstemp", "script_fd", "script_name", "mktemp", "script", "cmd", "py_compile", "file", "cfile", "dfile", "cfile_base"};
      byte_compile$12 = Py.newCode(8, var2, var1, "byte_compile", 430, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"header", "lines"};
      rfc822_escape$13 = Py.newCode(1, var2, var1, "rfc822_escape", 576, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new util$py("distutils/util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.get_platform$1(var2, var3);
         case 2:
            return this.convert_path$2(var2, var3);
         case 3:
            return this.change_root$3(var2, var3);
         case 4:
            return this.check_environ$4(var2, var3);
         case 5:
            return this.subst_vars$5(var2, var3);
         case 6:
            return this._subst$6(var2, var3);
         case 7:
            return this.grok_environment_error$7(var2, var3);
         case 8:
            return this._init_regex$8(var2, var3);
         case 9:
            return this.split_quoted$9(var2, var3);
         case 10:
            return this.execute$10(var2, var3);
         case 11:
            return this.strtobool$11(var2, var3);
         case 12:
            return this.byte_compile$12(var2, var3);
         case 13:
            return this.rfc822_escape$13(var2, var3);
         default:
            return null;
      }
   }
}
