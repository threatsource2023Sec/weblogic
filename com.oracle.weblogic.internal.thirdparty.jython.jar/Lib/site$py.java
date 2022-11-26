import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
@Filename("site.py")
public class site$py extends PyFunctionTable implements PyRunnable {
   static site$py self;
   static final PyCode f$0;
   static final PyCode makepath$1;
   static final PyCode abs__file__$2;
   static final PyCode removeduppaths$3;
   static final PyCode addbuilddir$4;
   static final PyCode _init_pathinfo$5;
   static final PyCode addpackage$6;
   static final PyCode addsitedir$7;
   static final PyCode check_enableusersite$8;
   static final PyCode getuserbase$9;
   static final PyCode getusersitepackages$10;
   static final PyCode addusersitepackages$11;
   static final PyCode getsitepackages$12;
   static final PyCode addsitepackages$13;
   static final PyCode setBEGINLIBPATH$14;
   static final PyCode setquit$15;
   static final PyCode Quitter$16;
   static final PyCode __init__$17;
   static final PyCode __repr__$18;
   static final PyCode __call__$19;
   static final PyCode _Printer$20;
   static final PyCode __init__$21;
   static final PyCode _Printer__setup$22;
   static final PyCode __repr__$23;
   static final PyCode __call__$24;
   static final PyCode setcopyright$25;
   static final PyCode _Helper$26;
   static final PyCode __repr__$27;
   static final PyCode __call__$28;
   static final PyCode sethelper$29;
   static final PyCode aliasmbcs$30;
   static final PyCode setencoding$31;
   static final PyCode execsitecustomize$32;
   static final PyCode execusercustomize$33;
   static final PyCode main$34;
   static final PyCode _script$35;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Append module search paths for third-party packages to sys.path.\n\n****************************************************************\n* This module is automatically imported during initialization. *\n****************************************************************\n\nIn earlier versions of Python (up to 1.5a3), scripts or modules that\nneeded to use site-specific modules would place ``import site''\nsomewhere near the top of their code.  Because of the automatic\nimport, this is no longer necessary (but code that does it still\nworks).\n\nThis will append site-specific paths to the module search path.  On\nUnix (including Mac OSX), it starts with sys.prefix and\nsys.exec_prefix (if different) and appends\nlib/python<version>/site-packages as well as lib/site-python.\nOn other platforms (such as Windows), it tries each of the\nprefixes directly, as well as with lib/site-packages appended.  The\nresulting directories, if they exist, are appended to sys.path, and\nalso inspected for path configuration files.\n\nA path configuration file is a file whose name has the form\n<package>.pth; its contents are additional directories (one per line)\nto be added to sys.path.  Non-existing directories (or\nnon-directories) are never added to sys.path; no directory is added to\nsys.path more than once.  Blank lines and lines beginning with\n'#' are skipped. Lines starting with 'import' are executed.\n\nFor example, suppose sys.prefix and sys.exec_prefix are set to\n/usr/local and there is a directory /usr/local/lib/python2.5/site-packages\nwith three subdirectories, foo, bar and spam, and two path\nconfiguration files, foo.pth and bar.pth.  Assume foo.pth contains the\nfollowing:\n\n  # foo package configuration\n  foo\n  bar\n  bletch\n\nand bar.pth contains:\n\n  # bar package configuration\n  bar\n\nThen the following directories are added to sys.path, in this order:\n\n  /usr/local/lib/python2.5/site-packages/bar\n  /usr/local/lib/python2.5/site-packages/foo\n\nNote that bletch is omitted because it doesn't exist; bar precedes foo\nbecause bar.pth comes alphabetically before foo.pth; and spam is\nomitted because it is not mentioned in either path configuration file.\n\nAfter these path manipulations, an attempt is made to import a module\nnamed sitecustomize, which can perform arbitrary additional\nsite-specific customizations.  If this import fails with an\nImportError exception, it is silently ignored.\n\n"));
      var1.setline(59);
      PyString.fromInterned("Append module search paths for third-party packages to sys.path.\n\n****************************************************************\n* This module is automatically imported during initialization. *\n****************************************************************\n\nIn earlier versions of Python (up to 1.5a3), scripts or modules that\nneeded to use site-specific modules would place ``import site''\nsomewhere near the top of their code.  Because of the automatic\nimport, this is no longer necessary (but code that does it still\nworks).\n\nThis will append site-specific paths to the module search path.  On\nUnix (including Mac OSX), it starts with sys.prefix and\nsys.exec_prefix (if different) and appends\nlib/python<version>/site-packages as well as lib/site-python.\nOn other platforms (such as Windows), it tries each of the\nprefixes directly, as well as with lib/site-packages appended.  The\nresulting directories, if they exist, are appended to sys.path, and\nalso inspected for path configuration files.\n\nA path configuration file is a file whose name has the form\n<package>.pth; its contents are additional directories (one per line)\nto be added to sys.path.  Non-existing directories (or\nnon-directories) are never added to sys.path; no directory is added to\nsys.path more than once.  Blank lines and lines beginning with\n'#' are skipped. Lines starting with 'import' are executed.\n\nFor example, suppose sys.prefix and sys.exec_prefix are set to\n/usr/local and there is a directory /usr/local/lib/python2.5/site-packages\nwith three subdirectories, foo, bar and spam, and two path\nconfiguration files, foo.pth and bar.pth.  Assume foo.pth contains the\nfollowing:\n\n  # foo package configuration\n  foo\n  bar\n  bletch\n\nand bar.pth contains:\n\n  # bar package configuration\n  bar\n\nThen the following directories are added to sys.path, in this order:\n\n  /usr/local/lib/python2.5/site-packages/bar\n  /usr/local/lib/python2.5/site-packages/foo\n\nNote that bletch is omitted because it doesn't exist; bar precedes foo\nbecause bar.pth comes alphabetically before foo.pth; and spam is\nomitted because it is not mentioned in either path configuration file.\n\nAfter these path manipulations, an attempt is made to import a module\nnamed sitecustomize, which can perform arbitrary additional\nsite-specific customizations.  If this import fails with an\nImportError exception, it is silently ignored.\n\n");
      var1.setline(63);
      PyObject var3 = imp.importOne("__builtin__", var1, -1);
      var1.setlocal("__builtin__", var3);
      var3 = null;
      var1.setline(64);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(68);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(69);
      var3 = imp.importOne("traceback", var1, -1);
      var1.setlocal("traceback", var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java"));
      var1.setlocal("_is_jython", var3);
      var3 = null;
      var1.setline(72);
      PyObject var4;
      if (var1.getname("_is_jython").__nonzero__()) {
         label32: {
            var1.setline(73);
            var3 = var1.getname("type").__call__(var2, var1.getname("os"));
            var1.setlocal("_ModuleType", var3);
            var3 = null;

            try {
               var1.setline(77);
               var3 = imp.importOne("readline", var1, -1);
               var1.setlocal("readline", var3);
               var3 = null;
            } catch (Throwable var5) {
               PyException var6 = Py.setException(var5, var1);
               if (var6.match(var1.getname("ImportError"))) {
                  var1.setline(79);
                  break label32;
               }

               throw var6;
            }

            var1.setline(81);
            var4 = imp.importOne("rlcompleter", var1, -1);
            var1.setlocal("rlcompleter", var4);
            var4 = null;
            var1.setline(82);
            var1.getname("readline").__getattr__("parse_and_bind").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("tab: complete"));
         }
      }

      var1.setline(85);
      PyList var7 = new PyList(new PyObject[]{var1.getname("sys").__getattr__("prefix"), var1.getname("sys").__getattr__("exec_prefix")});
      var1.setlocal("PREFIXES", var7);
      var3 = null;
      var1.setline(88);
      var3 = var1.getname("None");
      var1.setlocal("ENABLE_USER_SITE", var3);
      var3 = null;
      var1.setline(93);
      var3 = var1.getname("None");
      var1.setlocal("USER_SITE", var3);
      var3 = null;
      var1.setline(94);
      var3 = var1.getname("None");
      var1.setlocal("USER_BASE", var3);
      var3 = null;
      var1.setline(97);
      PyObject[] var8 = Py.EmptyObjects;
      PyFunction var9 = new PyFunction(var1.f_globals, var8, makepath$1, (PyObject)null);
      var1.setlocal("makepath", var9);
      var3 = null;
      var1.setline(109);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, abs__file__$2, PyString.fromInterned("Set all module' __file__ attribute to an absolute path"));
      var1.setlocal("abs__file__", var9);
      var3 = null;
      var1.setline(121);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, removeduppaths$3, PyString.fromInterned(" Remove duplicate entries from sys.path along with making them\n    absolute"));
      var1.setlocal("removeduppaths", var9);
      var3 = null;
      var1.setline(141);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, addbuilddir$4, PyString.fromInterned("Append ./build/lib.<platform> in case we're running in the build dir\n    (especially for Guido :-)"));
      var1.setlocal("addbuilddir", var9);
      var3 = null;
      var1.setline(152);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _init_pathinfo$5, PyString.fromInterned("Return a set containing all existing directory entries from sys.path"));
      var1.setlocal("_init_pathinfo", var9);
      var3 = null;
      var1.setline(165);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, addpackage$6, PyString.fromInterned("Process a .pth file within the site-packages directory:\n       For each line in the file, either combine it with sitedir to a path\n       and add that to known_paths, or execute it if it starts with 'import '.\n    "));
      var1.setlocal("addpackage", var9);
      var3 = null;
      var1.setline(206);
      var8 = new PyObject[]{var1.getname("None")};
      var9 = new PyFunction(var1.f_globals, var8, addsitedir$7, PyString.fromInterned("Add 'sitedir' argument to sys.path if missing and handle .pth files in\n    'sitedir'"));
      var1.setlocal("addsitedir", var9);
      var3 = null;
      var1.setline(230);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, check_enableusersite$8, PyString.fromInterned("Check if user site directory is safe for inclusion\n\n    The function tests for the command line flag (including environment var),\n    process uid/gid equal to effective uid/gid.\n\n    None: Disabled for security reasons\n    False: Disabled by user (command line option)\n    True: Safe and enabled\n    "));
      var1.setlocal("check_enableusersite", var9);
      var3 = null;
      var1.setline(254);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, getuserbase$9, PyString.fromInterned("Returns the `user base` directory path.\n\n    The `user base` directory can be used to store data. If the global\n    variable ``USER_BASE`` is not initialized yet, this function will also set\n    it.\n    "));
      var1.setlocal("getuserbase", var9);
      var3 = null;
      var1.setline(268);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, getusersitepackages$10, PyString.fromInterned("Returns the user-specific site-packages directory path.\n\n    If the global variable ``USER_SITE`` is not initialized yet, this\n    function will also set it.\n    "));
      var1.setlocal("getusersitepackages", var9);
      var3 = null;
      var1.setline(292);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, addusersitepackages$11, PyString.fromInterned("Add a per user site-package to sys.path\n\n    Each user has its own python directory with site-packages in the\n    home directory.\n    "));
      var1.setlocal("addusersitepackages", var9);
      var3 = null;
      var1.setline(306);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, getsitepackages$12, PyString.fromInterned("Returns a list containing all global site-packages directories\n    (and possibly site-python).\n\n    For each directory present in the global ``PREFIXES``, this function\n    will find its `site-packages` subdirectory depending on the system\n    environment, and will return a list of full paths.\n    "));
      var1.setlocal("getsitepackages", var9);
      var3 = null;
      var1.setline(343);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, addsitepackages$13, PyString.fromInterned("Add site-packages (and possibly site-python) to sys.path"));
      var1.setlocal("addsitepackages", var9);
      var3 = null;
      var1.setline(351);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, setBEGINLIBPATH$14, PyString.fromInterned("The OS/2 EMX port has optional extension modules that do double duty\n    as DLLs (and must use the .DLL file extension) for other extensions.\n    The library search path needs to be amended so these will be found\n    during module import.  Use BEGINLIBPATH so that these are at the start\n    of the library search path.\n\n    "));
      var1.setlocal("setBEGINLIBPATH", var9);
      var3 = null;
      var1.setline(368);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, setquit$15, PyString.fromInterned("Define new builtins 'quit' and 'exit'.\n\n    These are objects which make the interpreter exit when called.\n    The repr of each object contains a hint at how it works.\n\n    "));
      var1.setlocal("setquit", var9);
      var3 = null;
      var1.setline(399);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_Printer", var8, _Printer$20);
      var1.setlocal("_Printer", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(460);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, setcopyright$25, PyString.fromInterned("Set 'copyright' and 'credits' in __builtin__"));
      var1.setlocal("setcopyright", var9);
      var3 = null;
      var1.setline(478);
      var8 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("_Helper", var8, _Helper$26);
      var1.setlocal("_Helper", var4);
      var4 = null;
      Arrays.fill(var8, (Object)null);
      var1.setline(491);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, sethelper$29, (PyObject)null);
      var1.setlocal("sethelper", var9);
      var3 = null;
      var1.setline(494);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, aliasmbcs$30, PyString.fromInterned("On Windows, some default encodings are not provided by Python,\n    while they are always available as \"mbcs\" in each locale. Make\n    them usable by aliasing to \"mbcs\" in such a case."));
      var1.setlocal("aliasmbcs", var9);
      var3 = null;
      var1.setline(509);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, setencoding$31, PyString.fromInterned("Set the string encoding used by the Unicode implementation.  The\n    default is 'ascii', but if you're willing to experiment, you can\n    change this."));
      var1.setlocal("setencoding", var9);
      var3 = null;
      var1.setline(529);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, execsitecustomize$32, PyString.fromInterned("Run custom site specific code, if available."));
      var1.setlocal("execsitecustomize", var9);
      var3 = null;
      var1.setline(543);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, execusercustomize$33, PyString.fromInterned("Run custom user specific code, if available."));
      var1.setlocal("execusercustomize", var9);
      var3 = null;
      var1.setline(557);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, main$34, (PyObject)null);
      var1.setlocal("main", var9);
      var3 = null;
      var1.setline(585);
      var1.getname("main").__call__(var2);
      var1.setline(587);
      var8 = Py.EmptyObjects;
      var9 = new PyFunction(var1.f_globals, var8, _script$35, (PyObject)null);
      var1.setlocal("_script", var9);
      var3 = null;
      var1.setline(636);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(637);
         var1.getname("_script").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject makepath$1(PyFrame var1, ThreadState var2) {
      var1.setline(98);
      PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var3, var4, var1.getlocal(0), (PyObject)null);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(99);
      var10000 = var1.getglobal("_is_jython");
      if (var10000.__nonzero__()) {
         var6 = var1.getlocal(1);
         var10000 = var6._eq(PyString.fromInterned("__classpath__"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(1).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("__pyclasspath__"));
         }
      }

      PyTuple var9;
      if (var10000.__nonzero__()) {
         var1.setline(101);
         var9 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(1)});
         var1.f_lasti = -1;
         return var9;
      } else {
         try {
            var1.setline(103);
            PyObject var8 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(1));
            var1.setlocal(1, var8);
            var4 = null;
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (!var7.match(var1.getglobal("OSError"))) {
               throw var7;
            }

            var1.setline(105);
         }

         var1.setline(106);
         var9 = new PyTuple(new PyObject[]{var1.getlocal(1), var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getlocal(1))});
         var1.f_lasti = -1;
         return var9;
      }
   }

   public PyObject abs__file__$2(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyString.fromInterned("Set all module' __file__ attribute to an absolute path");
      var1.setline(111);
      PyObject var3 = var1.getglobal("sys").__getattr__("modules").__getattr__("values").__call__(var2).__iter__();

      while(true) {
         var1.setline(111);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(0, var4);
         var1.setline(112);
         PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("__loader__"));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("_is_jython");
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("_ModuleType")).__not__();
            }
         }

         if (!var10000.__nonzero__()) {
            var1.setline(115);
            PyObject var5 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("__file__"), (PyObject)var1.getglobal("None"));
            var1.setlocal(1, var5);
            var5 = null;
            var1.setline(116);
            var5 = var1.getlocal(1);
            var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(118);
               var5 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(1));
               var1.getlocal(0).__setattr__("__file__", var5);
               var5 = null;
            }
         }
      }
   }

   public PyObject removeduppaths$3(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyString.fromInterned(" Remove duplicate entries from sys.path along with making them\n    absolute");
      var1.setline(126);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(127);
      PyObject var8 = var1.getglobal("set").__call__(var2);
      var1.setlocal(1, var8);
      var3 = null;
      var1.setline(128);
      var8 = var1.getglobal("sys").__getattr__("path").__iter__();

      while(true) {
         var1.setline(128);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(136);
            var8 = var1.getlocal(0);
            var1.getglobal("sys").__getattr__("path").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var8);
            var3 = null;
            var1.setline(137);
            var8 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(2, var4);
         var1.setline(132);
         PyObject var5 = var1.getglobal("makepath").__call__(var2, var1.getlocal(2));
         PyObject[] var6 = Py.unpackSequence(var5, 2);
         PyObject var7 = var6[0];
         var1.setlocal(2, var7);
         var7 = null;
         var7 = var6[1];
         var1.setlocal(3, var7);
         var7 = null;
         var5 = null;
         var1.setline(133);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._in(var1.getlocal(1));
         var5 = null;
         if (var10000.__not__().__nonzero__()) {
            var1.setline(134);
            var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(2));
            var1.setline(135);
            var1.getlocal(1).__getattr__("add").__call__(var2, var1.getlocal(3));
         }
      }
   }

   public PyObject addbuilddir$4(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyString.fromInterned("Append ./build/lib.<platform> in case we're running in the build dir\n    (especially for Guido :-)");
      var1.setline(144);
      String[] var3 = new String[]{"get_platform"};
      PyObject[] var5 = imp.importFrom("sysconfig", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(145);
      PyObject var6 = PyString.fromInterned("build/lib.%s-%.3s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__call__(var2), var1.getglobal("sys").__getattr__("version")}));
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(146);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys"), (PyObject)PyString.fromInterned("gettotalrefcount")).__nonzero__()) {
         var1.setline(147);
         var6 = var1.getlocal(1);
         var6 = var6._iadd(PyString.fromInterned("-pydebug"));
         var1.setlocal(1, var6);
      }

      var1.setline(148);
      var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("sys").__getattr__("path").__getattr__("pop").__call__(var2)), var1.getlocal(1));
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(149);
      var1.getglobal("sys").__getattr__("path").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _init_pathinfo$5(PyFrame var1, ThreadState var2) {
      var1.setline(153);
      PyString.fromInterned("Return a set containing all existing directory entries from sys.path");
      var1.setline(154);
      PyObject var3 = var1.getglobal("set").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(155);
      var3 = var1.getglobal("sys").__getattr__("path").__iter__();

      while(true) {
         var1.setline(155);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(162);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(1, var4);

         PyException var5;
         try {
            var1.setline(157);
            if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__nonzero__()) {
               var1.setline(158);
               PyObject var9 = var1.getglobal("makepath").__call__(var2, var1.getlocal(1));
               PyObject[] var6 = Py.unpackSequence(var9, 2);
               PyObject var7 = var6[0];
               var1.setlocal(1, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(2, var7);
               var7 = null;
               var5 = null;
               var1.setline(159);
               var1.getlocal(0).__getattr__("add").__call__(var2, var1.getlocal(2));
            }
         } catch (Throwable var8) {
            var5 = Py.setException(var8, var1);
            if (!var5.match(var1.getglobal("TypeError"))) {
               throw var5;
            }
         }
      }
   }

   public PyObject addpackage$6(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(169);
      PyString.fromInterned("Process a .pth file within the site-packages directory:\n       For each line in the file, either combine it with sitedir to a path\n       and add that to known_paths, or execute it if it starts with 'import '.\n    ");
      var1.setline(170);
      PyObject var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getname("None"));
      var3 = null;
      PyInteger var15;
      if (var10000.__nonzero__()) {
         var1.setline(171);
         var1.getname("_init_pathinfo").__call__(var2);
         var1.setline(172);
         var15 = Py.newInteger(1);
         var1.setlocal(3, var15);
         var3 = null;
      } else {
         var1.setline(174);
         var15 = Py.newInteger(0);
         var1.setlocal(3, var15);
         var3 = null;
      }

      var1.setline(175);
      var3 = var1.getname("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;

      try {
         var1.setline(177);
         var3 = var1.getname("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("rU"));
         var1.setlocal(5, var3);
         var3 = null;
      } catch (Throwable var12) {
         PyException var16 = Py.setException(var12, var1);
         if (var16.match(var1.getname("IOError"))) {
            var1.setline(179);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var16;
      }

      ContextManager var18;
      PyObject var4 = (var18 = ContextGuard.getManager(var1.getlocal(5))).__enter__(var2);

      label85: {
         try {
            var1.setline(181);
            var4 = var1.getname("enumerate").__call__(var2, var1.getlocal(5)).__iter__();

            label79:
            while(true) {
               var1.setline(181);
               PyObject var5 = var4.__iternext__();
               if (var5 == null) {
                  break;
               }

               PyObject[] var6 = Py.unpackSequence(var5, 2);
               PyObject var7 = var6[0];
               var1.setlocal(6, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(7, var7);
               var7 = null;
               var1.setline(182);
               if (!var1.getlocal(7).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#")).__nonzero__()) {
                  String[] var8;
                  PyObject var20;
                  PyObject[] var21;
                  try {
                     var1.setline(185);
                     if (var1.getlocal(7).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("import "), PyString.fromInterned("import\t")}))).__nonzero__()) {
                        var1.setline(186);
                        Py.exec(var1.getlocal(7), (PyObject)null, (PyObject)null);
                     } else {
                        var1.setline(188);
                        PyObject var19 = var1.getlocal(7).__getattr__("rstrip").__call__(var2);
                        var1.setlocal(7, var19);
                        var6 = null;
                        var1.setline(189);
                        var19 = var1.getname("makepath").__call__(var2, var1.getlocal(0), var1.getlocal(7));
                        var21 = Py.unpackSequence(var19, 2);
                        var20 = var21[0];
                        var1.setlocal(8, var20);
                        var8 = null;
                        var20 = var21[1];
                        var1.setlocal(9, var20);
                        var8 = null;
                        var6 = null;
                        var1.setline(190);
                        var19 = var1.getlocal(9);
                        var10000 = var19._in(var1.getlocal(2));
                        var6 = null;
                        var10000 = var10000.__not__();
                        if (var10000.__nonzero__()) {
                           var10000 = var1.getname("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(8));
                        }

                        if (var10000.__nonzero__()) {
                           var1.setline(191);
                           var1.getname("sys").__getattr__("path").__getattr__("append").__call__(var2, var1.getlocal(8));
                           var1.setline(192);
                           var1.getlocal(2).__getattr__("add").__call__(var2, var1.getlocal(9));
                        }
                     }
                  } catch (Throwable var13) {
                     PyException var17 = Py.setException(var13, var1);
                     if (var17.match(var1.getname("Exception"))) {
                        var7 = var17.value;
                        var1.setlocal(10, var7);
                        var7 = null;
                        var1.setline(194);
                        var7 = var1.getname("sys").__getattr__("stderr");
                        Py.println(var7, PyString.fromInterned("Error processing line {:d} of {}:\n").__getattr__("format").__call__(var2, var1.getlocal(6)._add(Py.newInteger(1)), var1.getlocal(4)));
                        var1.setline(196);
                        var10000 = var1.getname("traceback").__getattr__("format_exception");
                        var21 = Py.EmptyObjects;
                        var8 = new String[0];
                        var10000 = var10000._callextra(var21, var8, var1.getname("sys").__getattr__("exc_info").__call__(var2), (PyObject)null);
                        var7 = null;
                        var7 = var10000.__iter__();

                        while(true) {
                           var1.setline(196);
                           var20 = var7.__iternext__();
                           if (var20 == null) {
                              var1.setline(199);
                              var7 = var1.getname("sys").__getattr__("stderr");
                              Py.println(var7, PyString.fromInterned("\nRemainder of file ignored"));
                              break label79;
                           }

                           var1.setlocal(11, var20);
                           var1.setline(197);
                           PyObject var9 = var1.getlocal(11).__getattr__("splitlines").__call__(var2).__iter__();

                           while(true) {
                              var1.setline(197);
                              PyObject var10 = var9.__iternext__();
                              if (var10 == null) {
                                 break;
                              }

                              var1.setlocal(7, var10);
                              var1.setline(198);
                              PyObject var11 = var1.getname("sys").__getattr__("stderr");
                              Py.println(var11, PyString.fromInterned("  ")._add(var1.getlocal(7)));
                           }
                        }
                     }

                     throw var17;
                  }
               }
            }
         } catch (Throwable var14) {
            if (var18.__exit__(var2, Py.setException(var14, var1))) {
               break label85;
            }

            throw (Throwable)Py.makeException();
         }

         var18.__exit__(var2, (PyException)null);
      }

      var1.setline(201);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(202);
         var3 = var1.getname("None");
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(203);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject addsitedir$7(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyString.fromInterned("Add 'sitedir' argument to sys.path if missing and handle .pth files in\n    'sitedir'");
      var1.setline(209);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyInteger var7;
      if (var10000.__nonzero__()) {
         var1.setline(210);
         var3 = var1.getglobal("_init_pathinfo").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(211);
         var7 = Py.newInteger(1);
         var1.setlocal(2, var7);
         var3 = null;
      } else {
         var1.setline(213);
         var7 = Py.newInteger(0);
         var1.setlocal(2, var7);
         var3 = null;
      }

      var1.setline(214);
      var3 = var1.getglobal("makepath").__call__(var2, var1.getlocal(0));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(0, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(215);
      var3 = var1.getlocal(3);
      var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      if (var10000.__not__().__nonzero__()) {
         var1.setline(216);
         var1.getglobal("sys").__getattr__("path").__getattr__("append").__call__(var2, var1.getlocal(0));
      }

      try {
         var1.setline(218);
         var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0));
         var1.setlocal(4, var3);
         var3 = null;
      } catch (Throwable var6) {
         PyException var9 = Py.setException(var6, var1);
         if (var9.match(var1.getglobal("os").__getattr__("error"))) {
            var1.setline(220);
            var1.f_lasti = -1;
            return Py.None;
         }

         throw var9;
      }

      var1.setline(221);
      var3 = var1.getglobal("os").__getattr__("extsep")._add(PyString.fromInterned("pth"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(222);
      PyList var11 = new PyList();
      var3 = var11.__getattr__("append");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(222);
      var3 = var1.getlocal(4).__iter__();

      while(true) {
         var1.setline(222);
         PyObject var8 = var3.__iternext__();
         if (var8 == null) {
            var1.setline(222);
            var1.dellocal(6);
            PyList var10 = var11;
            var1.setlocal(4, var10);
            var3 = null;
            var1.setline(223);
            var3 = var1.getglobal("sorted").__call__(var2, var1.getlocal(4)).__iter__();

            while(true) {
               var1.setline(223);
               var8 = var3.__iternext__();
               if (var8 == null) {
                  var1.setline(225);
                  if (var1.getlocal(2).__nonzero__()) {
                     var1.setline(226);
                     var3 = var1.getglobal("None");
                     var1.setlocal(1, var3);
                     var3 = null;
                  }

                  var1.setline(227);
                  var3 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var3;
               }

               var1.setlocal(7, var8);
               var1.setline(224);
               var1.getglobal("addpackage").__call__(var2, var1.getlocal(0), var1.getlocal(7), var1.getlocal(1));
            }
         }

         var1.setlocal(7, var8);
         var1.setline(222);
         if (var1.getlocal(7).__getattr__("endswith").__call__(var2, var1.getlocal(5)).__nonzero__()) {
            var1.setline(222);
            var1.getlocal(6).__call__(var2, var1.getlocal(7));
         }
      }
   }

   public PyObject check_enableusersite$8(PyFrame var1, ThreadState var2) {
      var1.setline(239);
      PyString.fromInterned("Check if user site directory is safe for inclusion\n\n    The function tests for the command line flag (including environment var),\n    process uid/gid equal to effective uid/gid.\n\n    None: Disabled for security reasons\n    False: Disabled by user (command line option)\n    True: Safe and enabled\n    ");
      var1.setline(240);
      PyObject var3;
      if (var1.getglobal("sys").__getattr__("flags").__getattr__("no_user_site").__nonzero__()) {
         var1.setline(241);
         var3 = var1.getglobal("False");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(243);
         PyObject var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("getuid"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("geteuid"));
         }

         PyObject var4;
         if (var10000.__nonzero__()) {
            var1.setline(245);
            var4 = var1.getglobal("os").__getattr__("geteuid").__call__(var2);
            var10000 = var4._ne(var1.getglobal("os").__getattr__("getuid").__call__(var2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(246);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(247);
         var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("getgid"));
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("getegid"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(249);
            var4 = var1.getglobal("os").__getattr__("getegid").__call__(var2);
            var10000 = var4._ne(var1.getglobal("os").__getattr__("getgid").__call__(var2));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(250);
               var3 = var1.getglobal("None");
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(252);
         var3 = var1.getglobal("True");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getuserbase$9(PyFrame var1, ThreadState var2) {
      var1.setline(260);
      PyString.fromInterned("Returns the `user base` directory path.\n\n    The `user base` directory can be used to store data. If the global\n    variable ``USER_BASE`` is not initialized yet, this function will also set\n    it.\n    ");
      var1.setline(262);
      PyObject var3 = var1.getglobal("USER_BASE");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(263);
         var3 = var1.getglobal("USER_BASE");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(264);
         String[] var4 = new String[]{"get_config_var"};
         PyObject[] var6 = imp.importFrom("sysconfig", var4, var1, -1);
         PyObject var5 = var6[0];
         var1.setlocal(0, var5);
         var5 = null;
         var1.setline(265);
         PyObject var7 = var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("userbase"));
         var1.setglobal("USER_BASE", var7);
         var4 = null;
         var1.setline(266);
         var3 = var1.getglobal("USER_BASE");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject getusersitepackages$10(PyFrame var1, ThreadState var2) {
      var1.setline(273);
      PyString.fromInterned("Returns the user-specific site-packages directory path.\n\n    If the global variable ``USER_SITE`` is not initialized yet, this\n    function will also set it.\n    ");
      var1.setline(275);
      PyObject var3 = var1.getglobal("getuserbase").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(277);
      var3 = var1.getglobal("USER_SITE");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(278);
         var3 = var1.getglobal("USER_SITE");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(280);
         String[] var4 = new String[]{"get_path"};
         PyObject[] var6 = imp.importFrom("sysconfig", var4, var1, -1);
         PyObject var5 = var6[0];
         var1.setlocal(1, var5);
         var5 = null;
         var1.setline(281);
         PyObject var7 = imp.importOne("os", var1, -1);
         var1.setlocal(2, var7);
         var4 = null;
         var1.setline(283);
         var7 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var7._eq(PyString.fromInterned("darwin"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(284);
            var4 = new String[]{"get_config_var"};
            var6 = imp.importFrom("sysconfig", var4, var1, -1);
            var5 = var6[0];
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(285);
            if (var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PYTHONFRAMEWORK")).__nonzero__()) {
               var1.setline(286);
               var7 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("purelib"), (PyObject)PyString.fromInterned("osx_framework_user"));
               var1.setglobal("USER_SITE", var7);
               var4 = null;
               var1.setline(287);
               var3 = var1.getglobal("USER_SITE");
               var1.f_lasti = -1;
               return var3;
            }
         }

         var1.setline(289);
         var7 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("purelib"), (PyObject)PyString.fromInterned("%s_user")._mod(var1.getlocal(2).__getattr__("name")));
         var1.setglobal("USER_SITE", var7);
         var4 = null;
         var1.setline(290);
         var3 = var1.getglobal("USER_SITE");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject addusersitepackages$11(PyFrame var1, ThreadState var2) {
      var1.setline(297);
      PyString.fromInterned("Add a per user site-package to sys.path\n\n    Each user has its own python directory with site-packages in the\n    home directory.\n    ");
      var1.setline(300);
      PyObject var3 = var1.getglobal("getusersitepackages").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(302);
      PyObject var10000 = var1.getglobal("ENABLE_USER_SITE");
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1));
      }

      if (var10000.__nonzero__()) {
         var1.setline(303);
         var1.getglobal("addsitedir").__call__(var2, var1.getlocal(1), var1.getlocal(0));
      }

      var1.setline(304);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject getsitepackages$12(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      PyString.fromInterned("Returns a list containing all global site-packages directories\n    (and possibly site-python).\n\n    For each directory present in the global ``PREFIXES``, this function\n    will find its `site-packages` subdirectory depending on the system\n    environment, and will return a list of full paths.\n    ");
      var1.setline(314);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(315);
      PyObject var7 = var1.getglobal("set").__call__(var2);
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(317);
      var7 = var1.getglobal("PREFIXES").__iter__();

      while(true) {
         var1.setline(317);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(341);
            var7 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var7;
         }

         var1.setlocal(2, var4);
         var1.setline(318);
         PyObject var10000 = var1.getlocal(2).__not__();
         PyObject var5;
         if (!var10000.__nonzero__()) {
            var5 = var1.getlocal(2);
            var10000 = var5._in(var1.getlocal(1));
            var5 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(320);
            var1.getlocal(1).__getattr__("add").__call__(var2, var1.getlocal(2));
            var1.setline(322);
            var5 = var1.getglobal("sys").__getattr__("platform");
            var10000 = var5._in(new PyTuple(new PyObject[]{PyString.fromInterned("os2emx"), PyString.fromInterned("riscos")}));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var10000 = var1.getglobal("_is_jython");
            }

            if (var10000.__nonzero__()) {
               var1.setline(323);
               var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("Lib"), (PyObject)PyString.fromInterned("site-packages")));
            } else {
               var1.setline(324);
               var5 = var1.getglobal("os").__getattr__("sep");
               var10000 = var5._eq(PyString.fromInterned("/"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(325);
                  var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), PyString.fromInterned("lib"), PyString.fromInterned("python")._add(var1.getglobal("sys").__getattr__("version").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null)), PyString.fromInterned("site-packages")));
                  var1.setline(328);
                  var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("lib"), (PyObject)PyString.fromInterned("site-python")));
               } else {
                  var1.setline(330);
                  var1.getlocal(0).__getattr__("append").__call__(var2, var1.getlocal(2));
                  var1.setline(331);
                  var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("lib"), (PyObject)PyString.fromInterned("site-packages")));
               }
            }

            var1.setline(332);
            var5 = var1.getglobal("sys").__getattr__("platform");
            var10000 = var5._eq(PyString.fromInterned("darwin"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(335);
               String[] var8 = new String[]{"get_config_var"};
               PyObject[] var9 = imp.importFrom("sysconfig", var8, var1, -1);
               PyObject var6 = var9[0];
               var1.setlocal(3, var6);
               var6 = null;
               var1.setline(336);
               var5 = var1.getlocal(3).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PYTHONFRAMEWORK"));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(337);
               if (var1.getlocal(4).__nonzero__()) {
                  var1.setline(338);
                  var1.getlocal(0).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, PyString.fromInterned("/Library"), var1.getlocal(4), var1.getglobal("sys").__getattr__("version").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null), PyString.fromInterned("site-packages")));
               }
            }
         }
      }
   }

   public PyObject addsitepackages$13(PyFrame var1, ThreadState var2) {
      var1.setline(344);
      PyString.fromInterned("Add site-packages (and possibly site-python) to sys.path");
      var1.setline(345);
      PyObject var3 = var1.getglobal("getsitepackages").__call__(var2).__iter__();

      while(true) {
         var1.setline(345);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(349);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         }

         var1.setlocal(1, var4);
         var1.setline(346);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(347);
            var1.getglobal("addsitedir").__call__(var2, var1.getlocal(1), var1.getlocal(0));
         }
      }
   }

   public PyObject setBEGINLIBPATH$14(PyFrame var1, ThreadState var2) {
      var1.setline(358);
      PyString.fromInterned("The OS/2 EMX port has optional extension modules that do double duty\n    as DLLs (and must use the .DLL file extension) for other extensions.\n    The library search path needs to be amended so these will be found\n    during module import.  Use BEGINLIBPATH so that these are at the start\n    of the library search path.\n\n    ");
      var1.setline(359);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("sys").__getattr__("prefix"), (PyObject)PyString.fromInterned("Lib"), (PyObject)PyString.fromInterned("lib-dynload"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(360);
      var3 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("BEGINLIBPATH")).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(";"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(361);
      if (var1.getlocal(1).__getitem__(Py.newInteger(-1)).__nonzero__()) {
         var1.setline(362);
         var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0));
      } else {
         var1.setline(364);
         var3 = var1.getlocal(0);
         var1.getlocal(1).__setitem__((PyObject)Py.newInteger(-1), var3);
         var3 = null;
      }

      var1.setline(365);
      var3 = PyString.fromInterned(";").__getattr__("join").__call__(var2, var1.getlocal(1));
      var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("BEGINLIBPATH"), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setquit$15(PyFrame var1, ThreadState var2) {
      var1.setline(374);
      PyString.fromInterned("Define new builtins 'quit' and 'exit'.\n\n    These are objects which make the interpreter exit when called.\n    The repr of each object contains a hint at how it works.\n\n    ");
      var1.setline(375);
      PyObject var3 = var1.getglobal("os").__getattr__("sep");
      PyObject var10000 = var3._eq(PyString.fromInterned(":"));
      var3 = null;
      PyString var5;
      if (var10000.__nonzero__()) {
         var1.setline(376);
         var5 = PyString.fromInterned("Cmd-Q");
         var1.setderef(0, var5);
         var3 = null;
      } else {
         var1.setline(377);
         var3 = var1.getglobal("os").__getattr__("sep");
         var10000 = var3._eq(PyString.fromInterned("\\"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(378);
            var5 = PyString.fromInterned("Ctrl-D (i.e. EOF)");
            var1.setderef(0, var5);
            var3 = null;
         } else {
            var1.setline(380);
            var5 = PyString.fromInterned("Ctrl-D (i.e. EOF)");
            var1.setderef(0, var5);
            var3 = null;
         }
      }

      var1.setline(382);
      PyObject[] var6 = new PyObject[]{var1.getglobal("object")};
      PyObject var4 = Py.makeClass("Quitter", var6, Quitter$16);
      var1.setlocal(0, var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(395);
      var3 = var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("quit"));
      var1.getglobal("__builtin__").__setattr__("quit", var3);
      var3 = null;
      var1.setline(396);
      var3 = var1.getlocal(0).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("exit"));
      var1.getglobal("__builtin__").__setattr__("exit", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Quitter$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(383);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$17, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(385);
      var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = __repr__$18;
      var3 = new PyObject[]{var1.f_back.getclosure(0)};
      var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(387);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, __call__$19, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$17(PyFrame var1, ThreadState var2) {
      var1.setline(384);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("name", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __repr__$18(PyFrame var1, ThreadState var2) {
      var1.setline(386);
      PyObject var3 = PyString.fromInterned("Use %s() or %s to exit")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("name"), var1.getderef(0)}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __call__$19(PyFrame var1, ThreadState var2) {
      try {
         var1.setline(391);
         var1.getglobal("sys").__getattr__("stdin").__getattr__("close").__call__(var2);
      } catch (Throwable var4) {
         Py.setException(var4, var1);
         var1.setline(393);
      }

      var1.setline(394);
      throw Py.makeException(var1.getglobal("SystemExit").__call__(var2, var1.getlocal(1)));
   }

   public PyObject _Printer$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("interactive prompt objects for printing the license text, a list of\n    contributors and the copyright notice."));
      var1.setline(401);
      PyString.fromInterned("interactive prompt objects for printing the license text, a list of\n    contributors and the copyright notice.");
      var1.setline(403);
      PyInteger var3 = Py.newInteger(23);
      var1.setlocal("MAXLINES", var3);
      var3 = null;
      var1.setline(405);
      PyObject[] var4 = new PyObject[]{new PyTuple(Py.EmptyObjects), new PyTuple(Py.EmptyObjects)};
      PyFunction var5 = new PyFunction(var1.f_globals, var4, __init__$21, (PyObject)null);
      var1.setlocal("__init__", var5);
      var3 = null;
      var1.setline(412);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, _Printer__setup$22, (PyObject)null);
      var1.setlocal("_Printer__setup", var5);
      var3 = null;
      var1.setline(433);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __repr__$23, (PyObject)null);
      var1.setlocal("__repr__", var5);
      var3 = null;
      var1.setline(440);
      var4 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var4, __call__$24, (PyObject)null);
      var1.setlocal("__call__", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$21(PyFrame var1, ThreadState var2) {
      var1.setline(406);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("_Printer__name", var3);
      var3 = null;
      var1.setline(407);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("_Printer__data", var3);
      var3 = null;
      var1.setline(408);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("_Printer__files", var3);
      var3 = null;
      var1.setline(409);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("_Printer__dirs", var3);
      var3 = null;
      var1.setline(410);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("_Printer__lines", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Printer__setup$22(PyFrame var1, ThreadState var2) {
      var1.setline(413);
      if (var1.getlocal(0).__getattr__("_Printer__lines").__nonzero__()) {
         var1.setline(414);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(415);
         PyObject var3 = var1.getglobal("None");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(416);
         var3 = var1.getlocal(0).__getattr__("_Printer__dirs").__iter__();

         do {
            var1.setline(416);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(417);
            PyObject var5 = var1.getlocal(0).__getattr__("_Printer__files").__iter__();

            while(true) {
               var1.setline(417);
               PyObject var6 = var5.__iternext__();
               if (var6 == null) {
                  break;
               }

               var1.setlocal(3, var6);
               var1.setline(418);
               PyObject var7 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(3));
               var1.setlocal(3, var7);
               var7 = null;

               try {
                  var1.setline(420);
                  var7 = var1.getglobal("file").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("rU"));
                  var1.setlocal(4, var7);
                  var7 = null;
                  var1.setline(421);
                  var7 = var1.getlocal(4).__getattr__("read").__call__(var2);
                  var1.setlocal(1, var7);
                  var7 = null;
                  var1.setline(422);
                  var1.getlocal(4).__getattr__("close").__call__(var2);
                  break;
               } catch (Throwable var8) {
                  PyException var9 = Py.setException(var8, var1);
                  if (!var9.match(var1.getglobal("IOError"))) {
                     throw var9;
                  }

                  var1.setline(425);
               }
            }

            var1.setline(426);
         } while(!var1.getlocal(1).__nonzero__());

         var1.setline(428);
         if (var1.getlocal(1).__not__().__nonzero__()) {
            var1.setline(429);
            var3 = var1.getlocal(0).__getattr__("_Printer__data");
            var1.setlocal(1, var3);
            var3 = null;
         }

         var1.setline(430);
         var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n"));
         var1.getlocal(0).__setattr__("_Printer__lines", var3);
         var3 = null;
         var1.setline(431);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_Printer__lines"));
         var1.getlocal(0).__setattr__("_Printer__linecnt", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __repr__$23(PyFrame var1, ThreadState var2) {
      var1.setline(434);
      var1.getlocal(0).__getattr__("_Printer__setup").__call__(var2);
      var1.setline(435);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("_Printer__lines"));
      PyObject var10000 = var3._le(var1.getlocal(0).__getattr__("MAXLINES"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(436);
         var3 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("_Printer__lines"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(438);
         var3 = PyString.fromInterned("Type %s() to see the full %s text")._mod((new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("_Printer__name")}))._mul(Py.newInteger(2)));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject __call__$24(PyFrame var1, ThreadState var2) {
      var1.setline(441);
      var1.getlocal(0).__getattr__("_Printer__setup").__call__(var2);
      var1.setline(442);
      PyString var3 = PyString.fromInterned("Hit Return for more, or q (and Return) to quit: ");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(443);
      PyInteger var6 = Py.newInteger(0);
      var1.setlocal(2, var6);
      var3 = null;

      PyObject var10000;
      do {
         var1.setline(444);
         if (!Py.newInteger(1).__nonzero__()) {
            break;
         }

         PyObject var4;
         try {
            var1.setline(446);
            PyObject var8 = var1.getglobal("range").__call__(var2, var1.getlocal(2), var1.getlocal(2)._add(var1.getlocal(0).__getattr__("MAXLINES"))).__iter__();

            while(true) {
               var1.setline(446);
               var4 = var8.__iternext__();
               if (var4 == null) {
                  break;
               }

               var1.setlocal(3, var4);
               var1.setline(447);
               Py.println(var1.getlocal(0).__getattr__("_Printer__lines").__getitem__(var1.getlocal(3)));
            }
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("IndexError"))) {
               break;
            }

            throw var7;
         }

         var1.setline(451);
         var4 = var1.getlocal(2);
         var4 = var4._iadd(var1.getlocal(0).__getattr__("MAXLINES"));
         var1.setlocal(2, var4);
         var1.setline(452);
         var4 = var1.getglobal("None");
         var1.setlocal(4, var4);
         var4 = null;

         while(true) {
            var1.setline(453);
            var4 = var1.getlocal(4);
            var10000 = var4._is(var1.getglobal("None"));
            var4 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(457);
               var4 = var1.getlocal(4);
               var10000 = var4._eq(PyString.fromInterned("q"));
               var4 = null;
               break;
            }

            var1.setline(454);
            var4 = var1.getglobal("raw_input").__call__(var2, var1.getlocal(1));
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(455);
            var4 = var1.getlocal(4);
            var10000 = var4._notin(new PyTuple(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("q")}));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(456);
               var4 = var1.getglobal("None");
               var1.setlocal(4, var4);
               var4 = null;
            }
         }
      } while(!var10000.__nonzero__());

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setcopyright$25(PyFrame var1, ThreadState var2) {
      var1.setline(461);
      PyString.fromInterned("Set 'copyright' and 'credits' in __builtin__");
      var1.setline(462);
      PyObject var3 = var1.getglobal("_Printer").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("copyright"), (PyObject)var1.getglobal("sys").__getattr__("copyright"));
      var1.getglobal("__builtin__").__setattr__("copyright", var3);
      var3 = null;
      var1.setline(463);
      var3 = var1.getglobal("sys").__getattr__("platform").__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
      PyObject var10000 = var3._eq(PyString.fromInterned("java"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(464);
         var3 = var1.getglobal("_Printer").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("credits"), (PyObject)PyString.fromInterned("Jython is maintained by the Jython developers (www.jython.org)."));
         var1.getglobal("__builtin__").__setattr__("credits", var3);
         var3 = null;
      } else {
         var1.setline(468);
         var3 = var1.getglobal("_Printer").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("credits"), (PyObject)PyString.fromInterned("    Thanks to CWI, CNRI, BeOpen.com, Zope Corporation and a cast of thousands\n    for supporting Python development.  See www.python.org for more information."));
         var1.getglobal("__builtin__").__setattr__("credits", var3);
         var3 = null;
      }

      var1.setline(471);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("os").__getattr__("__file__"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(472);
      var3 = var1.getglobal("_Printer").__call__(var2, PyString.fromInterned("license"), PyString.fromInterned("See http://www.python.org/%.3s/license.html")._mod(var1.getglobal("sys").__getattr__("version")), new PyList(new PyObject[]{PyString.fromInterned("LICENSE.txt"), PyString.fromInterned("LICENSE")}), new PyList(new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0), var1.getglobal("os").__getattr__("pardir")), var1.getlocal(0), var1.getglobal("os").__getattr__("curdir")}));
      var1.getglobal("__builtin__").__setattr__("license", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _Helper$26(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Define the builtin 'help'.\n    This is a wrapper around pydoc.help (with a twist).\n\n    "));
      var1.setline(482);
      PyString.fromInterned("Define the builtin 'help'.\n    This is a wrapper around pydoc.help (with a twist).\n\n    ");
      var1.setline(484);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __repr__$27, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(487);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __call__$28, (PyObject)null);
      var1.setlocal("__call__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __repr__$27(PyFrame var1, ThreadState var2) {
      var1.setline(485);
      PyString var3 = PyString.fromInterned("Type help() for interactive help, or help(object) for help about object.");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __call__$28(PyFrame var1, ThreadState var2) {
      var1.setline(488);
      PyObject var3 = imp.importOne("pydoc", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(489);
      PyObject var10000 = var1.getlocal(3).__getattr__("help");
      PyObject[] var5 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10000 = var10000._callextra(var5, var4, var1.getlocal(1), var1.getlocal(2));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject sethelper$29(PyFrame var1, ThreadState var2) {
      var1.setline(492);
      PyObject var3 = var1.getglobal("_Helper").__call__(var2);
      var1.getglobal("__builtin__").__setattr__("help", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject aliasmbcs$30(PyFrame var1, ThreadState var2) {
      var1.setline(497);
      PyString.fromInterned("On Windows, some default encodings are not provided by Python,\n    while they are always available as \"mbcs\" in each locale. Make\n    them usable by aliasing to \"mbcs\" in such a case.");
      var1.setline(498);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(499);
         var3 = imp.importOne("locale", var1, -1);
         var1.setlocal(0, var3);
         var3 = null;
         var3 = imp.importOne("codecs", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(500);
         var3 = var1.getlocal(0).__getattr__("getdefaultlocale").__call__(var2).__getitem__(Py.newInteger(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(501);
         if (var1.getlocal(2).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cp")).__nonzero__()) {
            try {
               var1.setline(503);
               var1.getlocal(1).__getattr__("lookup").__call__(var2, var1.getlocal(2));
            } catch (Throwable var5) {
               PyException var7 = Py.setException(var5, var1);
               if (!var7.match(var1.getglobal("LookupError"))) {
                  throw var7;
               }

               var1.setline(505);
               PyObject var4 = imp.importOne("encodings", var1, -1);
               var1.setlocal(3, var4);
               var4 = null;
               var1.setline(506);
               var4 = var1.getlocal(3).__getattr__("_unknown");
               var1.getlocal(3).__getattr__("_cache").__setitem__(var1.getlocal(2), var4);
               var4 = null;
               var1.setline(507);
               PyString var6 = PyString.fromInterned("mbcs");
               var1.getlocal(3).__getattr__("aliases").__getattr__("aliases").__setitem__((PyObject)var1.getlocal(2), var6);
               var4 = null;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setencoding$31(PyFrame var1, ThreadState var2) {
      var1.setline(512);
      PyString.fromInterned("Set the string encoding used by the Unicode implementation.  The\n    default is 'ascii', but if you're willing to experiment, you can\n    change this.");
      var1.setline(513);
      PyString var3 = PyString.fromInterned("ascii");
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(514);
      PyObject var4;
      if (Py.newInteger(0).__nonzero__()) {
         var1.setline(516);
         var4 = imp.importOne("locale", var1, -1);
         var1.setlocal(1, var4);
         var3 = null;
         var1.setline(517);
         var4 = var1.getlocal(1).__getattr__("getdefaultlocale").__call__(var2);
         var1.setlocal(2, var4);
         var3 = null;
         var1.setline(518);
         if (var1.getlocal(2).__getitem__(Py.newInteger(1)).__nonzero__()) {
            var1.setline(519);
            var4 = var1.getlocal(2).__getitem__(Py.newInteger(1));
            var1.setlocal(0, var4);
            var3 = null;
         }
      }

      var1.setline(520);
      if (Py.newInteger(0).__nonzero__()) {
         var1.setline(523);
         var3 = PyString.fromInterned("undefined");
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(524);
      var4 = var1.getlocal(0);
      PyObject var10000 = var4._ne(PyString.fromInterned("ascii"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(526);
         var1.getglobal("sys").__getattr__("setdefaultencoding").__call__(var2, var1.getlocal(0));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject execsitecustomize$32(PyFrame var1, ThreadState var2) {
      var1.setline(530);
      PyString.fromInterned("Run custom site specific code, if available.");

      PyException var3;
      try {
         var1.setline(532);
         PyObject var7 = imp.importOne("sitecustomize", var1, -1);
         var1.setlocal(0, var7);
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(var1.getglobal("ImportError"))) {
            var1.setline(534);
         } else {
            if (!var3.match(var1.getglobal("Exception"))) {
               throw var3;
            }

            var1.setline(536);
            if (var1.getglobal("sys").__getattr__("flags").__getattr__("verbose").__nonzero__()) {
               var1.setline(537);
               PyObject var10000 = var1.getglobal("sys").__getattr__("excepthook");
               PyObject[] var4 = Py.EmptyObjects;
               String[] var5 = new String[0];
               var10000._callextra(var4, var5, var1.getglobal("sys").__getattr__("exc_info").__call__(var2), (PyObject)null);
               var4 = null;
            } else {
               var1.setline(539);
               PyObject var8 = var1.getglobal("sys").__getattr__("stderr");
               Py.println(var8, PyString.fromInterned("'import sitecustomize' failed; use -v for traceback"));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject execusercustomize$33(PyFrame var1, ThreadState var2) {
      var1.setline(544);
      PyString.fromInterned("Run custom user specific code, if available.");

      PyException var3;
      try {
         var1.setline(546);
         PyObject var7 = imp.importOne("usercustomize", var1, -1);
         var1.setlocal(0, var7);
         var3 = null;
      } catch (Throwable var6) {
         var3 = Py.setException(var6, var1);
         if (var3.match(var1.getglobal("ImportError"))) {
            var1.setline(548);
         } else {
            if (!var3.match(var1.getglobal("Exception"))) {
               throw var3;
            }

            var1.setline(550);
            if (var1.getglobal("sys").__getattr__("flags").__getattr__("verbose").__nonzero__()) {
               var1.setline(551);
               PyObject var10000 = var1.getglobal("sys").__getattr__("excepthook");
               PyObject[] var4 = Py.EmptyObjects;
               String[] var5 = new String[0];
               var10000._callextra(var4, var5, var1.getglobal("sys").__getattr__("exc_info").__call__(var2), (PyObject)null);
               var4 = null;
            } else {
               var1.setline(553);
               PyObject var8 = var1.getglobal("sys").__getattr__("stderr");
               Py.println(var8, PyString.fromInterned("'import usercustomize' failed; use -v for traceback"));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject main$34(PyFrame var1, ThreadState var2) {
      var1.setline(560);
      var1.getglobal("abs__file__").__call__(var2);
      var1.setline(561);
      PyObject var3 = var1.getglobal("removeduppaths").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(562);
      var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("posix"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("sys").__getattr__("path");
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getglobal("sys").__getattr__("path").__getitem__(Py.newInteger(-1)));
            var10000 = var3._eq(PyString.fromInterned("Modules"));
            var3 = null;
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(564);
         var1.getglobal("addbuilddir").__call__(var2);
      }

      var1.setline(565);
      var3 = var1.getglobal("ENABLE_USER_SITE");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(566);
         var3 = var1.getglobal("check_enableusersite").__call__(var2);
         var1.setglobal("ENABLE_USER_SITE", var3);
         var3 = null;
      }

      var1.setline(567);
      var3 = var1.getglobal("addusersitepackages").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(568);
      var3 = var1.getglobal("addsitepackages").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(569);
      var3 = var1.getglobal("sys").__getattr__("platform");
      var10000 = var3._eq(PyString.fromInterned("os2emx"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(570);
         var1.getglobal("setBEGINLIBPATH").__call__(var2);
      }

      var1.setline(571);
      var1.getglobal("setquit").__call__(var2);
      var1.setline(572);
      var1.getglobal("setcopyright").__call__(var2);
      var1.setline(573);
      var1.getglobal("sethelper").__call__(var2);
      var1.setline(574);
      var1.getglobal("aliasmbcs").__call__(var2);
      var1.setline(575);
      var1.getglobal("setencoding").__call__(var2);
      var1.setline(576);
      var1.getglobal("execsitecustomize").__call__(var2);
      var1.setline(577);
      if (var1.getglobal("ENABLE_USER_SITE").__nonzero__()) {
         var1.setline(578);
         var1.getglobal("execusercustomize").__call__(var2);
      }

      var1.setline(582);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys"), (PyObject)PyString.fromInterned("setdefaultencoding")).__nonzero__()) {
         var1.setline(583);
         var1.getglobal("sys").__delattr__("setdefaultencoding");
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _script$35(PyFrame var1, ThreadState var2) {
      var1.setline(588);
      PyString var3 = PyString.fromInterned("    %s [--user-base] [--user-site]\n\n    Without arguments print some useful information\n    With arguments print the value of USER_BASE and/or USER_SITE separated\n    by '%s'.\n\n    Exit codes with --user-base or --user-site:\n      0 - user site directory is enabled\n      1 - user site directory is disabled by user\n      2 - uses site directory is disabled by super user\n          or for security reasons\n     >2 - unknown error\n    ");
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(602);
      PyObject var5 = var1.getglobal("sys").__getattr__("argv").__getslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(603);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(604);
         Py.println(PyString.fromInterned("sys.path = ["));
         var1.setline(605);
         var5 = var1.getglobal("sys").__getattr__("path").__iter__();

         while(true) {
            var1.setline(605);
            PyObject var4 = var5.__iternext__();
            if (var4 == null) {
               var1.setline(607);
               Py.println(PyString.fromInterned("]"));
               var1.setline(608);
               PyString var10000 = PyString.fromInterned("USER_BASE: %r (%s)");
               PyObject[] var10003 = new PyObject[]{var1.getglobal("USER_BASE"), null};
               var1.setline(609);
               var10003[1] = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getglobal("USER_BASE")).__nonzero__() ? PyString.fromInterned("exists") : PyString.fromInterned("doesn't exist");
               Py.println(var10000._mod(new PyTuple(var10003)));
               var1.setline(610);
               var10000 = PyString.fromInterned("USER_SITE: %r (%s)");
               var10003 = new PyObject[]{var1.getglobal("USER_SITE"), null};
               var1.setline(611);
               var10003[1] = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getglobal("USER_SITE")).__nonzero__() ? PyString.fromInterned("exists") : PyString.fromInterned("doesn't exist");
               Py.println(var10000._mod(new PyTuple(var10003)));
               var1.setline(612);
               Py.println(PyString.fromInterned("ENABLE_USER_SITE: %r")._mod(var1.getglobal("ENABLE_USER_SITE")));
               var1.setline(613);
               var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(606);
            Py.println(PyString.fromInterned("    %r,")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)})));
         }
      }

      var1.setline(615);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(616);
      var3 = PyString.fromInterned("--user-base");
      PyObject var7 = var3._in(var1.getlocal(1));
      var3 = null;
      if (var7.__nonzero__()) {
         var1.setline(617);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("USER_BASE"));
      }

      var1.setline(618);
      var3 = PyString.fromInterned("--user-site");
      var7 = var3._in(var1.getlocal(1));
      var3 = null;
      if (var7.__nonzero__()) {
         var1.setline(619);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("USER_SITE"));
      }

      var1.setline(621);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(622);
         Py.println(var1.getglobal("os").__getattr__("pathsep").__getattr__("join").__call__(var2, var1.getlocal(3)));
         var1.setline(623);
         if (var1.getglobal("ENABLE_USER_SITE").__nonzero__()) {
            var1.setline(624);
            var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         } else {
            var1.setline(625);
            var5 = var1.getglobal("ENABLE_USER_SITE");
            var7 = var5._is(var1.getglobal("False"));
            var3 = null;
            if (var7.__nonzero__()) {
               var1.setline(626);
               var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
            } else {
               var1.setline(627);
               var5 = var1.getglobal("ENABLE_USER_SITE");
               var7 = var5._is(var1.getglobal("None"));
               var3 = null;
               if (var7.__nonzero__()) {
                  var1.setline(628);
                  var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
               } else {
                  var1.setline(630);
                  var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(3));
               }
            }
         }
      } else {
         var1.setline(632);
         var5 = imp.importOne("textwrap", var1, -1);
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(633);
         Py.println(var1.getlocal(4).__getattr__("dedent").__call__(var2, var1.getlocal(0)._mod(new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)), var1.getglobal("os").__getattr__("pathsep")}))));
         var1.setline(634);
         var1.getglobal("sys").__getattr__("exit").__call__((ThreadState)var2, (PyObject)Py.newInteger(10));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public site$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"paths", "dir"};
      makepath$1 = Py.newCode(1, var2, var1, "makepath", 97, true, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"m", "f"};
      abs__file__$2 = Py.newCode(0, var2, var1, "abs__file__", 109, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"L", "known_paths", "dir", "dircase"};
      removeduppaths$3 = Py.newCode(0, var2, var1, "removeduppaths", 121, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"get_platform", "s"};
      addbuilddir$4 = Py.newCode(0, var2, var1, "addbuilddir", 141, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"d", "dir", "dircase"};
      _init_pathinfo$5 = Py.newCode(0, var2, var1, "_init_pathinfo", 152, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sitedir", "name", "known_paths", "reset", "fullname", "f", "n", "line", "dir", "dircase", "err", "record"};
      addpackage$6 = Py.newCode(3, var2, var1, "addpackage", 165, false, false, self, 6, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"sitedir", "known_paths", "reset", "sitedircase", "names", "dotpth", "_[222_13]", "name"};
      addsitedir$7 = Py.newCode(2, var2, var1, "addsitedir", 206, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      check_enableusersite$8 = Py.newCode(0, var2, var1, "check_enableusersite", 230, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"get_config_var"};
      getuserbase$9 = Py.newCode(0, var2, var1, "getuserbase", 254, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"user_base", "get_path", "os", "get_config_var"};
      getusersitepackages$10 = Py.newCode(0, var2, var1, "getusersitepackages", 268, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"known_paths", "user_site"};
      addusersitepackages$11 = Py.newCode(1, var2, var1, "addusersitepackages", 292, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sitepackages", "seen", "prefix", "get_config_var", "framework"};
      getsitepackages$12 = Py.newCode(0, var2, var1, "getsitepackages", 306, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"known_paths", "sitedir"};
      addsitepackages$13 = Py.newCode(1, var2, var1, "addsitepackages", 343, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"dllpath", "libpath"};
      setBEGINLIBPATH$14 = Py.newCode(0, var2, var1, "setBEGINLIBPATH", 351, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"Quitter", "eof"};
      String[] var10001 = var2;
      site$py var10007 = self;
      var2 = new String[]{"eof"};
      setquit$15 = Py.newCode(0, var10001, var1, "setquit", 368, false, false, var10007, 15, var2, (String[])null, 1, 4097);
      var2 = new String[0];
      Quitter$16 = Py.newCode(0, var2, var1, "Quitter", 382, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name"};
      __init__$17 = Py.newCode(2, var2, var1, "__init__", 383, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"eof"};
      __repr__$18 = Py.newCode(1, var10001, var1, "__repr__", 385, false, false, var10007, 18, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "code"};
      __call__$19 = Py.newCode(2, var2, var1, "__call__", 387, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Printer$20 = Py.newCode(0, var2, var1, "_Printer", 399, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "name", "data", "files", "dirs"};
      __init__$21 = Py.newCode(5, var2, var1, "__init__", 405, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "data", "dir", "filename", "fp"};
      _Printer__setup$22 = Py.newCode(1, var2, var1, "_Printer__setup", 412, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$23 = Py.newCode(1, var2, var1, "__repr__", 433, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "prompt", "lineno", "i", "key"};
      __call__$24 = Py.newCode(1, var2, var1, "__call__", 440, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"here"};
      setcopyright$25 = Py.newCode(0, var2, var1, "setcopyright", 460, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _Helper$26 = Py.newCode(0, var2, var1, "_Helper", 478, false, false, self, 26, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      __repr__$27 = Py.newCode(1, var2, var1, "__repr__", 484, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "args", "kwds", "pydoc"};
      __call__$28 = Py.newCode(3, var2, var1, "__call__", 487, true, true, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      sethelper$29 = Py.newCode(0, var2, var1, "sethelper", 491, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"locale", "codecs", "enc", "encodings"};
      aliasmbcs$30 = Py.newCode(0, var2, var1, "aliasmbcs", 494, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"encoding", "locale", "loc"};
      setencoding$31 = Py.newCode(0, var2, var1, "setencoding", 509, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"sitecustomize"};
      execsitecustomize$32 = Py.newCode(0, var2, var1, "execsitecustomize", 529, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"usercustomize"};
      execusercustomize$33 = Py.newCode(0, var2, var1, "execusercustomize", 543, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"known_paths"};
      main$34 = Py.newCode(0, var2, var1, "main", 557, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"help", "args", "dir", "buffer", "textwrap"};
      _script$35 = Py.newCode(0, var2, var1, "_script", 587, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new site$py("site$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(site$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.makepath$1(var2, var3);
         case 2:
            return this.abs__file__$2(var2, var3);
         case 3:
            return this.removeduppaths$3(var2, var3);
         case 4:
            return this.addbuilddir$4(var2, var3);
         case 5:
            return this._init_pathinfo$5(var2, var3);
         case 6:
            return this.addpackage$6(var2, var3);
         case 7:
            return this.addsitedir$7(var2, var3);
         case 8:
            return this.check_enableusersite$8(var2, var3);
         case 9:
            return this.getuserbase$9(var2, var3);
         case 10:
            return this.getusersitepackages$10(var2, var3);
         case 11:
            return this.addusersitepackages$11(var2, var3);
         case 12:
            return this.getsitepackages$12(var2, var3);
         case 13:
            return this.addsitepackages$13(var2, var3);
         case 14:
            return this.setBEGINLIBPATH$14(var2, var3);
         case 15:
            return this.setquit$15(var2, var3);
         case 16:
            return this.Quitter$16(var2, var3);
         case 17:
            return this.__init__$17(var2, var3);
         case 18:
            return this.__repr__$18(var2, var3);
         case 19:
            return this.__call__$19(var2, var3);
         case 20:
            return this._Printer$20(var2, var3);
         case 21:
            return this.__init__$21(var2, var3);
         case 22:
            return this._Printer__setup$22(var2, var3);
         case 23:
            return this.__repr__$23(var2, var3);
         case 24:
            return this.__call__$24(var2, var3);
         case 25:
            return this.setcopyright$25(var2, var3);
         case 26:
            return this._Helper$26(var2, var3);
         case 27:
            return this.__repr__$27(var2, var3);
         case 28:
            return this.__call__$28(var2, var3);
         case 29:
            return this.sethelper$29(var2, var3);
         case 30:
            return this.aliasmbcs$30(var2, var3);
         case 31:
            return this.setencoding$31(var2, var3);
         case 32:
            return this.execsitecustomize$32(var2, var3);
         case 33:
            return this.execusercustomize$33(var2, var3);
         case 34:
            return this.main$34(var2, var3);
         case 35:
            return this._script$35(var2, var3);
         default:
            return null;
      }
   }
}
