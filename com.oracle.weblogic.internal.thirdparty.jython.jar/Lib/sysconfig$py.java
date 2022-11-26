import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.ContextGuard;
import org.python.core.ContextManager;
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
@Filename("sysconfig.py")
public class sysconfig$py extends PyFunctionTable implements PyRunnable {
   static sysconfig$py self;
   static final PyCode f$0;
   static final PyCode fileSystemEncode$1;
   static final PyCode _safe_realpath$2;
   static final PyCode is_python_build$3;
   static final PyCode _subst_vars$4;
   static final PyCode _extend_dict$5;
   static final PyCode _expand_vars$6;
   static final PyCode _get_default_scheme$7;
   static final PyCode _getuserbase$8;
   static final PyCode joinuser$9;
   static final PyCode _parse_makefile$10;
   static final PyCode _get_makefile_filename$11;
   static final PyCode _init_posix$12;
   static final PyCode _init_non_posix$13;
   static final PyCode parse_config_h$14;
   static final PyCode get_config_h_filename$15;
   static final PyCode get_scheme_names$16;
   static final PyCode get_path_names$17;
   static final PyCode get_paths$18;
   static final PyCode get_path$19;
   static final PyCode get_config_vars$20;
   static final PyCode get_config_var$21;
   static final PyCode get_platform$22;
   static final PyCode get_python_version$23;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Provide access to Python's configuration information.\n\n"));
      var1.setline(3);
      PyString.fromInterned("Provide access to Python's configuration information.\n\n");
      var1.setline(4);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(6);
      String[] var6 = new String[]{"pardir", "realpath"};
      PyObject[] var7 = imp.importFrom("os.path", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("pardir", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("realpath", var4);
      var4 = null;
      var1.setline(8);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, fileSystemEncode$1, (PyObject)null);
      var1.setlocal("fileSystemEncode", var8);
      var3 = null;
      var1.setline(13);
      PyDictionary var9 = new PyDictionary(new PyObject[]{PyString.fromInterned("posix_prefix"), new PyDictionary(new PyObject[]{PyString.fromInterned("stdlib"), PyString.fromInterned("{base}/lib/python{py_version_short}"), PyString.fromInterned("platstdlib"), PyString.fromInterned("{platbase}/lib/python{py_version_short}"), PyString.fromInterned("purelib"), PyString.fromInterned("{base}/lib/python{py_version_short}/site-packages"), PyString.fromInterned("platlib"), PyString.fromInterned("{platbase}/lib/python{py_version_short}/site-packages"), PyString.fromInterned("include"), PyString.fromInterned("{base}/include/python{py_version_short}"), PyString.fromInterned("platinclude"), PyString.fromInterned("{platbase}/include/python{py_version_short}"), PyString.fromInterned("scripts"), PyString.fromInterned("{base}/bin"), PyString.fromInterned("data"), PyString.fromInterned("{base}")}), PyString.fromInterned("posix_home"), new PyDictionary(new PyObject[]{PyString.fromInterned("stdlib"), PyString.fromInterned("{base}/lib/python"), PyString.fromInterned("platstdlib"), PyString.fromInterned("{base}/lib/python"), PyString.fromInterned("purelib"), PyString.fromInterned("{base}/lib/python"), PyString.fromInterned("platlib"), PyString.fromInterned("{base}/lib/python"), PyString.fromInterned("include"), PyString.fromInterned("{base}/include/python"), PyString.fromInterned("platinclude"), PyString.fromInterned("{base}/include/python"), PyString.fromInterned("scripts"), PyString.fromInterned("{base}/bin"), PyString.fromInterned("data"), PyString.fromInterned("{base}")}), PyString.fromInterned("nt"), new PyDictionary(new PyObject[]{PyString.fromInterned("stdlib"), PyString.fromInterned("{base}/Lib"), PyString.fromInterned("platstdlib"), PyString.fromInterned("{base}/Lib"), PyString.fromInterned("purelib"), PyString.fromInterned("{base}/Lib/site-packages"), PyString.fromInterned("platlib"), PyString.fromInterned("{base}/Lib/site-packages"), PyString.fromInterned("include"), PyString.fromInterned("{base}/Include"), PyString.fromInterned("platinclude"), PyString.fromInterned("{base}/Include"), PyString.fromInterned("scripts"), PyString.fromInterned("{base}/Scripts"), PyString.fromInterned("data"), PyString.fromInterned("{base}")}), PyString.fromInterned("os2"), new PyDictionary(new PyObject[]{PyString.fromInterned("stdlib"), PyString.fromInterned("{base}/Lib"), PyString.fromInterned("platstdlib"), PyString.fromInterned("{base}/Lib"), PyString.fromInterned("purelib"), PyString.fromInterned("{base}/Lib/site-packages"), PyString.fromInterned("platlib"), PyString.fromInterned("{base}/Lib/site-packages"), PyString.fromInterned("include"), PyString.fromInterned("{base}/Include"), PyString.fromInterned("platinclude"), PyString.fromInterned("{base}/Include"), PyString.fromInterned("scripts"), PyString.fromInterned("{base}/Scripts"), PyString.fromInterned("data"), PyString.fromInterned("{base}")}), PyString.fromInterned("os2_home"), new PyDictionary(new PyObject[]{PyString.fromInterned("stdlib"), PyString.fromInterned("{userbase}/lib/python{py_version_short}"), PyString.fromInterned("platstdlib"), PyString.fromInterned("{userbase}/lib/python{py_version_short}"), PyString.fromInterned("purelib"), PyString.fromInterned("{userbase}/lib/python{py_version_short}/site-packages"), PyString.fromInterned("platlib"), PyString.fromInterned("{userbase}/lib/python{py_version_short}/site-packages"), PyString.fromInterned("include"), PyString.fromInterned("{userbase}/include/python{py_version_short}"), PyString.fromInterned("scripts"), PyString.fromInterned("{userbase}/bin"), PyString.fromInterned("data"), PyString.fromInterned("{userbase}")}), PyString.fromInterned("nt_user"), new PyDictionary(new PyObject[]{PyString.fromInterned("stdlib"), PyString.fromInterned("{userbase}/Python{py_version_nodot}"), PyString.fromInterned("platstdlib"), PyString.fromInterned("{userbase}/Python{py_version_nodot}"), PyString.fromInterned("purelib"), PyString.fromInterned("{userbase}/Python{py_version_nodot}/site-packages"), PyString.fromInterned("platlib"), PyString.fromInterned("{userbase}/Python{py_version_nodot}/site-packages"), PyString.fromInterned("include"), PyString.fromInterned("{userbase}/Python{py_version_nodot}/Include"), PyString.fromInterned("scripts"), PyString.fromInterned("{userbase}/Scripts"), PyString.fromInterned("data"), PyString.fromInterned("{userbase}")}), PyString.fromInterned("posix_user"), new PyDictionary(new PyObject[]{PyString.fromInterned("stdlib"), PyString.fromInterned("{userbase}/lib/python{py_version_short}"), PyString.fromInterned("platstdlib"), PyString.fromInterned("{userbase}/lib/python{py_version_short}"), PyString.fromInterned("purelib"), PyString.fromInterned("{userbase}/lib/python{py_version_short}/site-packages"), PyString.fromInterned("platlib"), PyString.fromInterned("{userbase}/lib/python{py_version_short}/site-packages"), PyString.fromInterned("include"), PyString.fromInterned("{userbase}/include/python{py_version_short}"), PyString.fromInterned("scripts"), PyString.fromInterned("{userbase}/bin"), PyString.fromInterned("data"), PyString.fromInterned("{userbase}")}), PyString.fromInterned("osx_framework_user"), new PyDictionary(new PyObject[]{PyString.fromInterned("stdlib"), PyString.fromInterned("{userbase}/lib/python"), PyString.fromInterned("platstdlib"), PyString.fromInterned("{userbase}/lib/python"), PyString.fromInterned("purelib"), PyString.fromInterned("{userbase}/lib/python/site-packages"), PyString.fromInterned("platlib"), PyString.fromInterned("{userbase}/lib/python/site-packages"), PyString.fromInterned("include"), PyString.fromInterned("{userbase}/include"), PyString.fromInterned("scripts"), PyString.fromInterned("{userbase}/bin"), PyString.fromInterned("data"), PyString.fromInterned("{userbase}")}), PyString.fromInterned("java"), new PyDictionary(new PyObject[]{PyString.fromInterned("stdlib"), PyString.fromInterned("{base}/lib/jython"), PyString.fromInterned("platstdlib"), PyString.fromInterned("{base}/lib/jython"), PyString.fromInterned("purelib"), PyString.fromInterned("{base}/lib/jython"), PyString.fromInterned("platlib"), PyString.fromInterned("{base}/lib/jython"), PyString.fromInterned("include"), PyString.fromInterned("{base}/include/jython"), PyString.fromInterned("platinclude"), PyString.fromInterned("{base}/include/jython"), PyString.fromInterned("scripts"), PyString.fromInterned("{base}/bin"), PyString.fromInterned("data"), PyString.fromInterned("{base}")}), PyString.fromInterned("java_user"), new PyDictionary(new PyObject[]{PyString.fromInterned("stdlib"), PyString.fromInterned("{userbase}/lib/jython{py_version_short}"), PyString.fromInterned("platstdlib"), PyString.fromInterned("{userbase}/lib/jython{py_version_short}"), PyString.fromInterned("purelib"), PyString.fromInterned("{userbase}/lib/jython{py_version_short}/site-packages"), PyString.fromInterned("platlib"), PyString.fromInterned("{userbase}/lib/jython{py_version_short}/site-packages"), PyString.fromInterned("include"), PyString.fromInterned("{userbase}/include/jython{py_version_short}"), PyString.fromInterned("scripts"), PyString.fromInterned("{userbase}/bin"), PyString.fromInterned("data"), PyString.fromInterned("{userbase}")})});
      var1.setlocal("_INSTALL_SCHEMES", var9);
      var3 = null;
      var1.setline(112);
      PyTuple var10 = new PyTuple(new PyObject[]{PyString.fromInterned("stdlib"), PyString.fromInterned("platstdlib"), PyString.fromInterned("purelib"), PyString.fromInterned("platlib"), PyString.fromInterned("include"), PyString.fromInterned("scripts"), PyString.fromInterned("data")});
      var1.setlocal("_SCHEME_KEYS", var10);
      var3 = null;
      var1.setline(114);
      var3 = var1.getname("sys").__getattr__("version").__getattr__("split").__call__(var2).__getitem__(Py.newInteger(0));
      var1.setlocal("_PY_VERSION", var3);
      var3 = null;
      var1.setline(115);
      var3 = var1.getname("sys").__getattr__("version").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
      var1.setlocal("_PY_VERSION_SHORT", var3);
      var3 = null;
      var1.setline(116);
      var3 = var1.getname("_PY_VERSION").__getitem__(Py.newInteger(0))._add(var1.getname("_PY_VERSION").__getitem__(Py.newInteger(2)));
      var1.setlocal("_PY_VERSION_SHORT_NO_DOT", var3);
      var3 = null;
      var1.setline(117);
      var1.setline(117);
      var3 = var1.getname("sys").__getattr__("prefix");
      PyObject var10000 = var3._isnot(var1.getname("None"));
      var3 = null;
      var3 = var10000.__nonzero__() ? var1.getname("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getname("sys").__getattr__("prefix")) : var1.getname("None");
      var1.setlocal("_PREFIX", var3);
      var3 = null;
      var1.setline(118);
      var1.setline(118);
      var3 = var1.getname("sys").__getattr__("exec_prefix");
      var10000 = var3._isnot(var1.getname("None"));
      var3 = null;
      var3 = var10000.__nonzero__() ? var1.getname("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getname("sys").__getattr__("exec_prefix")) : var1.getname("None");
      var1.setlocal("_EXEC_PREFIX", var3);
      var3 = null;
      var1.setline(119);
      var3 = var1.getname("None");
      var1.setlocal("_CONFIG_VARS", var3);
      var3 = null;
      var1.setline(120);
      var3 = var1.getname("None");
      var1.setlocal("_USER_BASE", var3);
      var3 = null;
      var1.setline(122);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _safe_realpath$2, (PyObject)null);
      var1.setlocal("_safe_realpath", var8);
      var3 = null;
      var1.setline(129);
      if (var1.getname("sys").__getattr__("executable").__nonzero__()) {
         var1.setline(130);
         var3 = var1.getname("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getname("_safe_realpath").__call__(var2, var1.getname("sys").__getattr__("executable")));
         var1.setlocal("_PROJECT_BASE", var3);
         var3 = null;
      } else {
         var1.setline(134);
         var3 = var1.getname("_safe_realpath").__call__(var2, var1.getname("os").__getattr__("getcwd").__call__(var2));
         var1.setlocal("_PROJECT_BASE", var3);
         var3 = null;
      }

      var1.setline(136);
      var3 = var1.getname("os").__getattr__("name");
      var10000 = var3._ne(PyString.fromInterned("java"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(137);
         var3 = var1.getname("os").__getattr__("name");
         var10000 = var3._eq(PyString.fromInterned("nt"));
         var3 = null;
         PyString var11;
         if (var10000.__nonzero__()) {
            var11 = PyString.fromInterned("pcbuild");
            var10000 = var11._in(var1.getname("_PROJECT_BASE").__getslice__(Py.newInteger(-8), (PyObject)null, (PyObject)null).__getattr__("lower").__call__(var2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(138);
            var3 = var1.getname("_safe_realpath").__call__(var2, var1.getname("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getname("_PROJECT_BASE"), var1.getname("pardir")));
            var1.setlocal("_PROJECT_BASE", var3);
            var3 = null;
         }

         var1.setline(140);
         var3 = var1.getname("os").__getattr__("name");
         var10000 = var3._eq(PyString.fromInterned("nt"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var11 = PyString.fromInterned("\\pc\\v");
            var10000 = var11._in(var1.getname("_PROJECT_BASE").__getslice__(Py.newInteger(-10), (PyObject)null, (PyObject)null).__getattr__("lower").__call__(var2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(141);
            var3 = var1.getname("_safe_realpath").__call__(var2, var1.getname("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getname("_PROJECT_BASE"), var1.getname("pardir"), var1.getname("pardir")));
            var1.setlocal("_PROJECT_BASE", var3);
            var3 = null;
         }

         var1.setline(143);
         var3 = var1.getname("os").__getattr__("name");
         var10000 = var3._eq(PyString.fromInterned("nt"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var11 = PyString.fromInterned("\\pcbuild\\amd64");
            var10000 = var11._in(var1.getname("_PROJECT_BASE").__getslice__(Py.newInteger(-14), (PyObject)null, (PyObject)null).__getattr__("lower").__call__(var2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(144);
            var3 = var1.getname("_safe_realpath").__call__(var2, var1.getname("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getname("_PROJECT_BASE"), var1.getname("pardir"), var1.getname("pardir")));
            var1.setlocal("_PROJECT_BASE", var3);
            var3 = null;
         }
      }

      var1.setline(146);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, is_python_build$3, (PyObject)null);
      var1.setlocal("is_python_build", var8);
      var3 = null;
      var1.setline(152);
      var3 = var1.getname("is_python_build").__call__(var2);
      var1.setlocal("_PYTHON_BUILD", var3);
      var3 = null;
      var1.setline(154);
      if (var1.getname("_PYTHON_BUILD").__nonzero__()) {
         var1.setline(155);
         var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("posix_prefix"), PyString.fromInterned("posix_home")})).__iter__();

         while(true) {
            var1.setline(155);
            var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal("scheme", var4);
            var1.setline(156);
            PyString var5 = PyString.fromInterned("{projectbase}/Include");
            var1.getname("_INSTALL_SCHEMES").__getitem__(var1.getname("scheme")).__setitem__((PyObject)PyString.fromInterned("include"), var5);
            var5 = null;
            var1.setline(157);
            var5 = PyString.fromInterned("{srcdir}");
            var1.getname("_INSTALL_SCHEMES").__getitem__(var1.getname("scheme")).__setitem__((PyObject)PyString.fromInterned("platinclude"), var5);
            var5 = null;
         }
      }

      var1.setline(159);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _subst_vars$4, (PyObject)null);
      var1.setlocal("_subst_vars", var8);
      var3 = null;
      var1.setline(168);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _extend_dict$5, (PyObject)null);
      var1.setlocal("_extend_dict", var8);
      var3 = null;
      var1.setline(175);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _expand_vars$6, (PyObject)null);
      var1.setlocal("_expand_vars", var8);
      var3 = null;
      var1.setline(192);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _get_default_scheme$7, (PyObject)null);
      var1.setlocal("_get_default_scheme", var8);
      var3 = null;
      var1.setline(199);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _getuserbase$8, (PyObject)null);
      var1.setlocal("_getuserbase", var8);
      var3 = null;
      var1.setline(225);
      var7 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, _parse_makefile$10, PyString.fromInterned("Parse a Makefile-style file.\n\n    A dictionary containing name/value pairs is returned.  If an\n    optional dictionary is passed in as the second argument, it is\n    used instead of a new dictionary.\n    "));
      var1.setlocal("_parse_makefile", var8);
      var3 = null;
      var1.setline(311);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _get_makefile_filename$11, (PyObject)null);
      var1.setlocal("_get_makefile_filename", var8);
      var3 = null;
      var1.setline(317);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _init_posix$12, PyString.fromInterned("Initialize the module as appropriate for POSIX systems."));
      var1.setlocal("_init_posix", var8);
      var3 = null;
      var1.setline(346);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, _init_non_posix$13, PyString.fromInterned("Initialize the module as appropriate for NT"));
      var1.setlocal("_init_non_posix", var8);
      var3 = null;
      var1.setline(362);
      var7 = new PyObject[]{var1.getname("None")};
      var8 = new PyFunction(var1.f_globals, var7, parse_config_h$14, PyString.fromInterned("Parse a config.h-style file.\n\n    A dictionary containing name/value pairs is returned.  If an\n    optional dictionary is passed in as the second argument, it is\n    used instead of a new dictionary.\n    "));
      var1.setlocal("parse_config_h", var8);
      var3 = null;
      var1.setline(391);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_config_h_filename$15, PyString.fromInterned("Returns the path of pyconfig.h."));
      var1.setlocal("get_config_h_filename", var8);
      var3 = null;
      var1.setline(403);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_scheme_names$16, PyString.fromInterned("Returns a tuple containing the schemes names."));
      var1.setlocal("get_scheme_names", var8);
      var3 = null;
      var1.setline(409);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_path_names$17, PyString.fromInterned("Returns a tuple containing the paths names."));
      var1.setlocal("get_path_names", var8);
      var3 = null;
      var1.setline(413);
      var7 = new PyObject[]{var1.getname("_get_default_scheme").__call__(var2), var1.getname("None"), var1.getname("True")};
      var8 = new PyFunction(var1.f_globals, var7, get_paths$18, PyString.fromInterned("Returns a mapping containing an install scheme.\n\n    ``scheme`` is the install scheme name. If not provided, it will\n    return the default scheme for the current platform.\n    "));
      var1.setlocal("get_paths", var8);
      var3 = null;
      var1.setline(424);
      var7 = new PyObject[]{var1.getname("_get_default_scheme").__call__(var2), var1.getname("None"), var1.getname("True")};
      var8 = new PyFunction(var1.f_globals, var7, get_path$19, PyString.fromInterned("Returns a path corresponding to the scheme.\n\n    ``scheme`` is the install scheme name.\n    "));
      var1.setlocal("get_path", var8);
      var3 = null;
      var1.setline(431);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_config_vars$20, PyString.fromInterned("With no arguments, return a dictionary of all configuration\n    variables relevant for the current platform.\n\n    On Unix, this means every variable defined in Python's installed Makefile;\n    On Windows and Mac OS it's a much smaller set.\n\n    With arguments, return a list of values that result from looking up\n    each argument in the configuration variable dictionary.\n    "));
      var1.setlocal("get_config_vars", var8);
      var3 = null;
      var1.setline(559);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_config_var$21, PyString.fromInterned("Return the value of a single variable using the dictionary returned by\n    'get_config_vars()'.\n\n    Equivalent to get_config_vars().get(name)\n    "));
      var1.setlocal("get_config_var", var8);
      var3 = null;
      var1.setline(567);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_platform$22, PyString.fromInterned("Return a string that identifies the current platform.\n\n    This is used mainly to distinguish platform-specific build directories and\n    platform-specific built distributions.  Typically includes the OS name\n    and version and the architecture (as supplied by 'os.uname()'),\n    although the exact information included depends on the OS; eg. for IRIX\n    the architecture isn't particularly important (IRIX only runs on SGI\n    hardware), but for Linux the kernel version isn't particularly\n    important.\n\n    Examples of returned values:\n       linux-i586\n       linux-alpha (?)\n       solaris-2.6-sun4u\n       irix-5.3\n       irix64-6.2\n\n    Windows will return one of:\n       win-amd64 (64bit Windows on AMD64 (aka x86_64, Intel64, EM64T, etc)\n       win-ia64 (64bit Windows on Itanium)\n       win32 (all others - specifically, sys.platform is returned)\n\n    For other non-POSIX platforms, currently just returns 'sys.platform'.\n    "));
      var1.setlocal("get_platform", var8);
      var3 = null;
      var1.setline(738);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, get_python_version$23, (PyObject)null);
      var1.setlocal("get_python_version", var8);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fileSystemEncode$1(PyFrame var1, ThreadState var2) {
      var1.setline(9);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0), var1.getglobal("unicode")).__nonzero__()) {
         var1.setline(10);
         var3 = var1.getlocal(0).__getattr__("encode").__call__(var2, var1.getglobal("sys").__getattr__("getfilesystemencoding").__call__(var2));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(11);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _safe_realpath$2(PyFrame var1, ThreadState var2) {
      PyObject var3;
      try {
         var1.setline(124);
         var3 = var1.getglobal("fileSystemEncode").__call__(var2, var1.getlocal(0));
         var1.setlocal(0, var3);
         var3 = null;
         var1.setline(125);
         var3 = var1.getglobal("realpath").__call__(var2, var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var5) {
         PyException var4 = Py.setException(var5, var1);
         if (var4.match(var1.getglobal("OSError"))) {
            var1.setline(127);
            var3 = var1.getlocal(0);
            var1.f_lasti = -1;
            return var3;
         } else {
            throw var4;
         }
      }
   }

   public PyObject is_python_build$3(PyFrame var1, ThreadState var2) {
      var1.setline(147);
      PyObject var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("Setup.dist"), PyString.fromInterned("Setup.local")})).__iter__();

      PyObject var5;
      do {
         var1.setline(147);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(150);
            var5 = var1.getglobal("False");
            var1.f_lasti = -1;
            return var5;
         }

         var1.setlocal(0, var4);
         var1.setline(148);
      } while(!var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("_PROJECT_BASE"), (PyObject)PyString.fromInterned("Modules"), (PyObject)var1.getlocal(0))).__nonzero__());

      var1.setline(149);
      var5 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _subst_vars$4(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var10000;
      try {
         var1.setline(161);
         var10000 = var1.getlocal(0).__getattr__("format");
         PyObject[] var9 = Py.EmptyObjects;
         String[] var10 = new String[0];
         var10000 = var10000._callextra(var9, var10, (PyObject)null, var1.getlocal(1));
         var3 = null;
         var3 = var10000;
         var1.f_lasti = -1;
         return var3;
      } catch (Throwable var8) {
         PyException var4 = Py.setException(var8, var1);
         if (var4.match(var1.getglobal("KeyError"))) {
            PyException var5;
            try {
               var1.setline(164);
               var10000 = var1.getlocal(0).__getattr__("format");
               PyObject[] var11 = Py.EmptyObjects;
               String[] var12 = new String[0];
               var10000 = var10000._callextra(var11, var12, (PyObject)null, var1.getglobal("os").__getattr__("environ"));
               var5 = null;
               var3 = var10000;
               var1.f_lasti = -1;
               return var3;
            } catch (Throwable var7) {
               var5 = Py.setException(var7, var1);
               if (var5.match(var1.getglobal("KeyError"))) {
                  PyObject var6 = var5.value;
                  var1.setlocal(2, var6);
                  var6 = null;
                  var1.setline(166);
                  throw Py.makeException(var1.getglobal("AttributeError").__call__(var2, PyString.fromInterned("{%s}")._mod(var1.getlocal(2))));
               } else {
                  throw var5;
               }
            }
         } else {
            throw var4;
         }
      }
   }

   public PyObject _extend_dict$5(PyFrame var1, ThreadState var2) {
      var1.setline(169);
      PyObject var3 = var1.getlocal(0).__getattr__("keys").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(170);
      var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(170);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(171);
         PyObject var7 = var1.getlocal(3);
         PyObject var10000 = var7._in(var1.getlocal(2));
         var5 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(173);
            var7 = var1.getlocal(4);
            var1.getlocal(0).__setitem__(var1.getlocal(3), var7);
            var5 = null;
         }
      }
   }

   public PyObject _expand_vars$6(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyDictionary var3 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(177);
      PyObject var8 = var1.getlocal(1);
      PyObject var10000 = var8._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(178);
         var3 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(179);
      var1.getglobal("_extend_dict").__call__(var2, var1.getlocal(1), var1.getglobal("get_config_vars").__call__(var2));
      var1.setline(181);
      var8 = var1.getglobal("_INSTALL_SCHEMES").__getitem__(var1.getlocal(0)).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(181);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(190);
            var8 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var8;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(184);
         PyObject var9 = var1.getglobal("os").__getattr__("name");
         var10000 = var9._in(new PyTuple(new PyObject[]{PyString.fromInterned("posix"), PyString.fromInterned("nt"), PyString.fromInterned("java")}));
         var5 = null;
         if (var10000.__nonzero__()) {
            try {
               var1.setline(186);
               var9 = var1.getglobal("os").__getattr__("path").__getattr__("expanduser").__call__(var2, var1.getlocal(4));
               var1.setlocal(4, var9);
               var5 = null;
            } catch (Throwable var7) {
               PyException var10 = Py.setException(var7, var1);
               if (!var10.match(var1.getglobal("ImportError"))) {
                  throw var10;
               }

               var1.setline(188);
            }
         }

         var1.setline(189);
         var9 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getglobal("_subst_vars").__call__(var2, var1.getlocal(4), var1.getlocal(1)));
         var1.getlocal(2).__setitem__(var1.getlocal(3), var9);
         var5 = null;
      }
   }

   public PyObject _get_default_scheme$7(PyFrame var1, ThreadState var2) {
      var1.setline(194);
      PyObject var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._ne(PyString.fromInterned("java"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("os").__getattr__("name");
         var10000 = var3._eq(PyString.fromInterned("posix"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(196);
         PyString var4 = PyString.fromInterned("posix_prefix");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(197);
         var3 = var1.getglobal("os").__getattr__("name");
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _getuserbase$8(PyFrame var1, ThreadState var2) {
      var1.setline(200);
      PyObject var3 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PYTHONUSERBASE"), (PyObject)var1.getglobal("None"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(201);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, joinuser$9, (PyObject)null);
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(205);
      var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._ne(PyString.fromInterned("java"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(207);
         var3 = var1.getglobal("os").__getattr__("name");
         var10000 = var3._eq(PyString.fromInterned("nt"));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var3 = var1.getglobal("os").__getattr__("_name");
            var10000 = var3._eq(PyString.fromInterned("nt"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(208);
            Object var9 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("APPDATA"));
            if (!((PyObject)var9).__nonzero__()) {
               var9 = PyString.fromInterned("~");
            }

            Object var8 = var9;
            var1.setlocal(2, (PyObject)var8);
            var3 = null;
            var1.setline(209);
            var1.setline(209);
            var3 = var1.getlocal(0).__nonzero__() ? var1.getlocal(0) : var1.getlocal(1).__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("Python"));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(211);
         PyObject var4 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var4._eq(PyString.fromInterned("darwin"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(212);
            var4 = var1.getglobal("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PYTHONFRAMEWORK"));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(213);
            if (var1.getlocal(3).__nonzero__()) {
               var1.setline(214);
               var1.setline(214);
               var3 = var1.getlocal(0).__nonzero__() ? var1.getlocal(0) : var1.getlocal(1).__call__(var2, PyString.fromInterned("~"), PyString.fromInterned("Library"), var1.getlocal(3), PyString.fromInterned("%d.%d")._mod(var1.getglobal("sys").__getattr__("version_info").__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null)));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }

      var1.setline(217);
      if (var1.getlocal(0).__nonzero__()) {
         var1.setline(218);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         try {
            var1.setline(220);
            var3 = var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("~"), (PyObject)PyString.fromInterned(".local"));
            var1.f_lasti = -1;
            return var3;
         } catch (Throwable var5) {
            Py.setException(var5, var1);
            var1.setline(222);
            var3 = var1.getglobal("None");
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject joinuser$9(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("expanduser");
      PyObject var10002 = var1.getglobal("os").__getattr__("path").__getattr__("join");
      PyObject[] var3 = Py.EmptyObjects;
      String[] var4 = new String[0];
      var10002 = var10002._callextra(var3, var4, var1.getlocal(0), (PyObject)null);
      var3 = null;
      PyObject var5 = var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _parse_makefile$10(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(231);
      PyString.fromInterned("Parse a Makefile-style file.\n\n    A dictionary containing name/value pairs is returned.  If an\n    optional dictionary is passed in as the second argument, it is\n    used instead of a new dictionary.\n    ");
      var1.setline(232);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(235);
      var3 = var1.getlocal(2).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([a-zA-Z][a-zA-Z0-9_]+)\\s*=\\s*(.*)"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(236);
      var3 = var1.getlocal(2).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\$\\(([A-Za-z][A-Za-z0-9_]*)\\)"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(237);
      var3 = var1.getlocal(2).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\${([A-Za-z][A-Za-z0-9_]*)}"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(239);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyDictionary var11;
      if (var10000.__nonzero__()) {
         var1.setline(240);
         var11 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(1, var11);
         var3 = null;
      }

      var1.setline(241);
      var11 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(6, var11);
      var3 = null;
      var1.setline(242);
      var11 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal(7, var11);
      var3 = null;
      ContextManager var13;
      PyObject var4 = (var13 = ContextGuard.getManager(var1.getglobal("open").__call__(var2, var1.getlocal(0)))).__enter__(var2);

      label126: {
         try {
            var1.setlocal(8, var4);
            var1.setline(245);
            var4 = var1.getlocal(8).__getattr__("readlines").__call__(var2);
            var1.setlocal(9, var4);
            var4 = null;
         } catch (Throwable var10) {
            if (var13.__exit__(var2, Py.setException(var10, var1))) {
               break label126;
            }

            throw (Throwable)Py.makeException();
         }

         var13.__exit__(var2, (PyException)null);
      }

      var1.setline(247);
      var3 = var1.getlocal(9).__iter__();

      while(true) {
         var1.setline(247);
         var4 = var3.__iternext__();
         PyObject var5;
         PyObject[] var6;
         PyObject var12;
         PyString var14;
         PyException var15;
         if (var4 == null) {
            label103:
            while(true) {
               var1.setline(269);
               if (!var1.getlocal(7).__nonzero__()) {
                  var1.setline(302);
                  var3 = var1.getlocal(6).__getattr__("items").__call__(var2).__iter__();

                  while(true) {
                     var1.setline(302);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        var1.setline(307);
                        var1.getlocal(1).__getattr__("update").__call__(var2, var1.getlocal(6));
                        var1.setline(308);
                        var3 = var1.getlocal(1);
                        var1.f_lasti = -1;
                        return var3;
                     }

                     PyObject[] var16 = Py.unpackSequence(var4, 2);
                     var12 = var16[0];
                     var1.setlocal(20, var12);
                     var6 = null;
                     var12 = var16[1];
                     var1.setlocal(13, var12);
                     var6 = null;
                     var1.setline(303);
                     if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(13), var1.getglobal("str")).__nonzero__()) {
                        var1.setline(304);
                        var5 = var1.getlocal(13).__getattr__("strip").__call__(var2);
                        var1.getlocal(6).__setitem__(var1.getlocal(20), var5);
                        var5 = null;
                     }
                  }
               }

               var1.setline(270);
               var3 = var1.getlocal(7).__getattr__("keys").__call__(var2).__iter__();

               while(true) {
                  while(true) {
                     do {
                        while(true) {
                           var1.setline(270);
                           var4 = var3.__iternext__();
                           if (var4 == null) {
                              continue label103;
                           }

                           var1.setlocal(15, var4);
                           var1.setline(271);
                           var5 = var1.getlocal(7).__getitem__(var1.getlocal(15));
                           var1.setlocal(16, var5);
                           var5 = null;
                           var1.setline(272);
                           var10000 = var1.getlocal(4).__getattr__("search").__call__(var2, var1.getlocal(16));
                           if (!var10000.__nonzero__()) {
                              var10000 = var1.getlocal(5).__getattr__("search").__call__(var2, var1.getlocal(16));
                           }

                           var5 = var10000;
                           var1.setlocal(11, var5);
                           var5 = null;
                           var1.setline(273);
                           if (var1.getlocal(11).__nonzero__()) {
                              var1.setline(274);
                              var5 = var1.getlocal(11).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                              var1.setlocal(12, var5);
                              var5 = null;
                              var1.setline(275);
                              var5 = var1.getglobal("True");
                              var1.setlocal(17, var5);
                              var5 = null;
                              var1.setline(276);
                              var5 = var1.getlocal(12);
                              var10000 = var5._in(var1.getlocal(6));
                              var5 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(277);
                                 var5 = var1.getglobal("str").__call__(var2, var1.getlocal(6).__getitem__(var1.getlocal(12)));
                                 var1.setlocal(18, var5);
                                 var5 = null;
                              } else {
                                 var1.setline(278);
                                 var5 = var1.getlocal(12);
                                 var10000 = var5._in(var1.getlocal(7));
                                 var5 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(280);
                                    var5 = var1.getglobal("False");
                                    var1.setlocal(17, var5);
                                    var5 = null;
                                 } else {
                                    var1.setline(281);
                                    var5 = var1.getlocal(12);
                                    var10000 = var5._in(var1.getglobal("os").__getattr__("environ"));
                                    var5 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(283);
                                       var5 = var1.getglobal("os").__getattr__("environ").__getitem__(var1.getlocal(12));
                                       var1.setlocal(18, var5);
                                       var5 = null;
                                    } else {
                                       var1.setline(285);
                                       var14 = PyString.fromInterned("");
                                       var1.getlocal(6).__setitem__((PyObject)var1.getlocal(12), var14);
                                       var1.setlocal(18, var14);
                                    }
                                 }
                              }

                              var1.setline(286);
                              break;
                           }

                           var1.setline(300);
                           var1.getlocal(7).__delitem__(var1.getlocal(15));
                        }
                     } while(!var1.getlocal(17).__nonzero__());

                     var1.setline(287);
                     var5 = var1.getlocal(16).__getslice__(var1.getlocal(11).__getattr__("end").__call__(var2), (PyObject)null, (PyObject)null);
                     var1.setlocal(19, var5);
                     var5 = null;
                     var1.setline(288);
                     var5 = var1.getlocal(16).__getslice__((PyObject)null, var1.getlocal(11).__getattr__("start").__call__(var2), (PyObject)null)._add(var1.getlocal(18))._add(var1.getlocal(19));
                     var1.setlocal(16, var5);
                     var5 = null;
                     var1.setline(289);
                     var14 = PyString.fromInterned("$");
                     var10000 = var14._in(var1.getlocal(19));
                     var5 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(290);
                        var5 = var1.getlocal(16);
                        var1.getlocal(7).__setitem__(var1.getlocal(15), var5);
                        var5 = null;
                     } else {
                        label96: {
                           try {
                              var1.setline(292);
                              var5 = var1.getglobal("int").__call__(var2, var1.getlocal(16));
                              var1.setlocal(16, var5);
                              var5 = null;
                           } catch (Throwable var8) {
                              var15 = Py.setException(var8, var1);
                              if (var15.match(var1.getglobal("ValueError"))) {
                                 var1.setline(294);
                                 var12 = var1.getlocal(16).__getattr__("strip").__call__(var2);
                                 var1.getlocal(6).__setitem__(var1.getlocal(15), var12);
                                 var6 = null;
                                 break label96;
                              }

                              throw var15;
                           }

                           var1.setline(296);
                           var12 = var1.getlocal(16);
                           var1.getlocal(6).__setitem__(var1.getlocal(15), var12);
                           var6 = null;
                        }

                        var1.setline(297);
                        var1.getlocal(7).__delitem__(var1.getlocal(15));
                     }
                  }
               }
            }
         }

         var1.setlocal(10, var4);
         var1.setline(248);
         var10000 = var1.getlocal(10).__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#"));
         if (!var10000.__nonzero__()) {
            var5 = var1.getlocal(10).__getattr__("strip").__call__(var2);
            var10000 = var5._eq(PyString.fromInterned(""));
            var5 = null;
         }

         if (!var10000.__nonzero__()) {
            var1.setline(250);
            var5 = var1.getlocal(3).__getattr__("match").__call__(var2, var1.getlocal(10));
            var1.setlocal(11, var5);
            var5 = null;
            var1.setline(251);
            if (var1.getlocal(11).__nonzero__()) {
               var1.setline(252);
               var5 = var1.getlocal(11).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
               var6 = Py.unpackSequence(var5, 2);
               PyObject var7 = var6[0];
               var1.setlocal(12, var7);
               var7 = null;
               var7 = var6[1];
               var1.setlocal(13, var7);
               var7 = null;
               var5 = null;
               var1.setline(253);
               var5 = var1.getlocal(13).__getattr__("strip").__call__(var2);
               var1.setlocal(13, var5);
               var5 = null;
               var1.setline(255);
               var5 = var1.getlocal(13).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$$"), (PyObject)PyString.fromInterned(""));
               var1.setlocal(14, var5);
               var5 = null;
               var1.setline(257);
               var14 = PyString.fromInterned("$");
               var10000 = var14._in(var1.getlocal(14));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(258);
                  var5 = var1.getlocal(13);
                  var1.getlocal(7).__setitem__(var1.getlocal(12), var5);
                  var5 = null;
               } else {
                  try {
                     var1.setline(261);
                     var5 = var1.getglobal("int").__call__(var2, var1.getlocal(13));
                     var1.setlocal(13, var5);
                     var5 = null;
                  } catch (Throwable var9) {
                     var15 = Py.setException(var9, var1);
                     if (var15.match(var1.getglobal("ValueError"))) {
                        var1.setline(264);
                        var12 = var1.getlocal(13).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("$$"), (PyObject)PyString.fromInterned("$"));
                        var1.getlocal(6).__setitem__(var1.getlocal(12), var12);
                        var6 = null;
                        continue;
                     }

                     throw var15;
                  }

                  var1.setline(266);
                  var12 = var1.getlocal(13);
                  var1.getlocal(6).__setitem__(var1.getlocal(12), var12);
                  var6 = null;
               }
            }
         }
      }
   }

   public PyObject _get_makefile_filename$11(PyFrame var1, ThreadState var2) {
      var1.setline(312);
      PyObject var3;
      if (var1.getglobal("_PYTHON_BUILD").__nonzero__()) {
         var1.setline(313);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("_PROJECT_BASE"), (PyObject)PyString.fromInterned("Makefile"));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(314);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getglobal("get_path").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("platstdlib")), (PyObject)PyString.fromInterned("config"), (PyObject)PyString.fromInterned("Makefile"));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject _init_posix$12(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(318);
      PyString.fromInterned("Initialize the module as appropriate for POSIX systems.");
      var1.setline(320);
      PyObject var3 = var1.getglobal("_get_makefile_filename").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;

      PyObject var4;
      PyException var8;
      try {
         var1.setline(322);
         var1.getglobal("_parse_makefile").__call__(var2, var1.getlocal(1), var1.getlocal(0));
      } catch (Throwable var5) {
         var8 = Py.setException(var5, var1);
         if (var8.match(var1.getglobal("IOError"))) {
            var4 = var8.value;
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(324);
            var4 = PyString.fromInterned("invalid Python installation: unable to open %s")._mod(var1.getlocal(1));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(325);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("strerror")).__nonzero__()) {
               var1.setline(326);
               var4 = var1.getlocal(3)._add(PyString.fromInterned(" (%s)")._mod(var1.getlocal(2).__getattr__("strerror")));
               var1.setlocal(3, var4);
               var4 = null;
            }

            var1.setline(327);
            throw Py.makeException(var1.getglobal("IOError").__call__(var2, var1.getlocal(3)));
         }

         throw var8;
      }

      var1.setline(330);
      var3 = var1.getglobal("get_config_h_filename").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;

      try {
         label58: {
            ContextManager var9;
            var4 = (var9 = ContextGuard.getManager(var1.getglobal("open").__call__(var2, var1.getlocal(4)))).__enter__(var2);

            try {
               var1.setlocal(5, var4);
               var1.setline(333);
               var1.getglobal("parse_config_h").__call__(var2, var1.getlocal(5), var1.getlocal(0));
            } catch (Throwable var6) {
               if (var9.__exit__(var2, Py.setException(var6, var1))) {
                  break label58;
               }

               throw (Throwable)Py.makeException();
            }

            var9.__exit__(var2, (PyException)null);
         }
      } catch (Throwable var7) {
         var8 = Py.setException(var7, var1);
         if (var8.match(var1.getglobal("IOError"))) {
            var4 = var8.value;
            var1.setlocal(2, var4);
            var4 = null;
            var1.setline(335);
            var4 = PyString.fromInterned("invalid Python installation: unable to open %s")._mod(var1.getlocal(4));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(336);
            if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("strerror")).__nonzero__()) {
               var1.setline(337);
               var4 = var1.getlocal(3)._add(PyString.fromInterned(" (%s)")._mod(var1.getlocal(2).__getattr__("strerror")));
               var1.setlocal(3, var4);
               var4 = null;
            }

            var1.setline(338);
            throw Py.makeException(var1.getglobal("IOError").__call__(var2, var1.getlocal(3)));
         }

         throw var8;
      }

      var1.setline(343);
      if (var1.getglobal("_PYTHON_BUILD").__nonzero__()) {
         var1.setline(344);
         var3 = var1.getlocal(0).__getitem__(PyString.fromInterned("BLDSHARED"));
         var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("LDSHARED"), var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _init_non_posix$13(PyFrame var1, ThreadState var2) {
      var1.setline(347);
      PyString.fromInterned("Initialize the module as appropriate for NT");
      var1.setline(349);
      PyObject var3 = var1.getglobal("get_path").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("stdlib"));
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("LIBDEST"), var3);
      var3 = null;
      var1.setline(350);
      var3 = var1.getglobal("get_path").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("platstdlib"));
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("BINLIBDEST"), var3);
      var3 = null;
      var1.setline(351);
      var3 = var1.getglobal("get_path").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("include"));
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("INCLUDEPY"), var3);
      var3 = null;
      var1.setline(352);
      PyString var4 = PyString.fromInterned(".pyd");
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("SO"), var4);
      var3 = null;
      var1.setline(353);
      var4 = PyString.fromInterned(".exe");
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("EXE"), var4);
      var3 = null;
      var1.setline(354);
      var3 = var1.getglobal("_PY_VERSION_SHORT_NO_DOT");
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("VERSION"), var3);
      var3 = null;
      var1.setline(355);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("_safe_realpath").__call__(var2, var1.getglobal("sys").__getattr__("executable")));
      var1.getlocal(0).__setitem__((PyObject)PyString.fromInterned("BINDIR"), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse_config_h$14(PyFrame var1, ThreadState var2) {
      var1.setline(368);
      PyString.fromInterned("Parse a config.h-style file.\n\n    A dictionary containing name/value pairs is returned.  If an\n    optional dictionary is passed in as the second argument, it is\n    used instead of a new dictionary.\n    ");
      var1.setline(369);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(370);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(371);
         PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(1, var7);
         var3 = null;
      }

      var1.setline(372);
      var3 = var1.getlocal(2).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("#define ([A-Z][A-Za-z0-9_]+) (.*)\n"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(373);
      var3 = var1.getlocal(2).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/[*] #undef ([A-Z][A-Za-z0-9_]+) [*]/\n"));
      var1.setlocal(4, var3);
      var3 = null;

      while(true) {
         var1.setline(375);
         if (!var1.getglobal("True").__nonzero__()) {
            break;
         }

         var1.setline(376);
         var3 = var1.getlocal(0).__getattr__("readline").__call__(var2);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(377);
         if (var1.getlocal(5).__not__().__nonzero__()) {
            break;
         }

         var1.setline(379);
         var3 = var1.getlocal(3).__getattr__("match").__call__(var2, var1.getlocal(5));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(380);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(381);
            var3 = var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1), (PyObject)Py.newInteger(2));
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.setlocal(7, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(8, var5);
            var5 = null;
            var3 = null;

            try {
               var1.setline(382);
               var3 = var1.getglobal("int").__call__(var2, var1.getlocal(8));
               var1.setlocal(8, var3);
               var3 = null;
            } catch (Throwable var6) {
               PyException var9 = Py.setException(var6, var1);
               if (!var9.match(var1.getglobal("ValueError"))) {
                  throw var9;
               }

               var1.setline(383);
            }

            var1.setline(384);
            var3 = var1.getlocal(8);
            var1.getlocal(1).__setitem__(var1.getlocal(7), var3);
            var3 = null;
         } else {
            var1.setline(386);
            var3 = var1.getlocal(4).__getattr__("match").__call__(var2, var1.getlocal(5));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(387);
            if (var1.getlocal(6).__nonzero__()) {
               var1.setline(388);
               PyInteger var8 = Py.newInteger(0);
               var1.getlocal(1).__setitem__((PyObject)var1.getlocal(6).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var8);
               var3 = null;
            }
         }
      }

      var1.setline(389);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_config_h_filename$15(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyString.fromInterned("Returns the path of pyconfig.h.");
      var1.setline(393);
      PyObject var3;
      if (var1.getglobal("_PYTHON_BUILD").__nonzero__()) {
         var1.setline(395);
         var3 = var1.getglobal("os").__getattr__("name");
         PyObject var10000 = var3._eq(PyString.fromInterned("nt"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("os").__getattr__("name");
            var10000 = var3._ne(PyString.fromInterned("java"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(396);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("_PROJECT_BASE"), (PyObject)PyString.fromInterned("PC"));
            var1.setlocal(0, var3);
            var3 = null;
         } else {
            var1.setline(398);
            var3 = var1.getglobal("_PROJECT_BASE");
            var1.setlocal(0, var3);
            var3 = null;
         }
      } else {
         var1.setline(400);
         var3 = var1.getglobal("get_path").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("platinclude"));
         var1.setlocal(0, var3);
         var3 = null;
      }

      var1.setline(401);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("pyconfig.h"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_scheme_names$16(PyFrame var1, ThreadState var2) {
      var1.setline(404);
      PyString.fromInterned("Returns a tuple containing the schemes names.");
      var1.setline(405);
      PyObject var3 = var1.getglobal("_INSTALL_SCHEMES").__getattr__("keys").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(406);
      var1.getlocal(0).__getattr__("sort").__call__(var2);
      var1.setline(407);
      var3 = var1.getglobal("tuple").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_path_names$17(PyFrame var1, ThreadState var2) {
      var1.setline(410);
      PyString.fromInterned("Returns a tuple containing the paths names.");
      var1.setline(411);
      PyObject var3 = var1.getglobal("_SCHEME_KEYS");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_paths$18(PyFrame var1, ThreadState var2) {
      var1.setline(418);
      PyString.fromInterned("Returns a mapping containing an install scheme.\n\n    ``scheme`` is the install scheme name. If not provided, it will\n    return the default scheme for the current platform.\n    ");
      var1.setline(419);
      PyObject var3;
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(420);
         var3 = var1.getglobal("_expand_vars").__call__(var2, var1.getlocal(0), var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(422);
         var3 = var1.getglobal("_INSTALL_SCHEMES").__getitem__(var1.getlocal(0));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject get_path$19(PyFrame var1, ThreadState var2) {
      var1.setline(428);
      PyString.fromInterned("Returns a path corresponding to the scheme.\n\n    ``scheme`` is the install scheme name.\n    ");
      var1.setline(429);
      PyObject var3 = var1.getglobal("get_paths").__call__(var2, var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)).__getitem__(var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_config_vars$20(PyFrame var1, ThreadState var2) {
      var1.setline(440);
      PyString.fromInterned("With no arguments, return a dictionary of all configuration\n    variables relevant for the current platform.\n\n    On Unix, this means every variable defined in Python's installed Makefile;\n    On Windows and Mac OS it's a much smaller set.\n\n    With arguments, return a list of values that result from looking up\n    each argument in the configuration variable dictionary.\n    ");
      var1.setline(441);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(443);
      var3 = var1.getglobal("_CONFIG_VARS");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(444);
         PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
         var1.setglobal("_CONFIG_VARS", var7);
         var3 = null;
         var1.setline(448);
         var3 = var1.getglobal("_PREFIX");
         var1.getglobal("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("prefix"), var3);
         var3 = null;
         var1.setline(449);
         var3 = var1.getglobal("_EXEC_PREFIX");
         var1.getglobal("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("exec_prefix"), var3);
         var3 = null;
         var1.setline(450);
         var3 = var1.getglobal("_PY_VERSION");
         var1.getglobal("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("py_version"), var3);
         var3 = null;
         var1.setline(451);
         var3 = var1.getglobal("_PY_VERSION_SHORT");
         var1.getglobal("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("py_version_short"), var3);
         var3 = null;
         var1.setline(452);
         var3 = var1.getglobal("_PY_VERSION").__getitem__(Py.newInteger(0))._add(var1.getglobal("_PY_VERSION").__getitem__(Py.newInteger(2)));
         var1.getglobal("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("py_version_nodot"), var3);
         var3 = null;
         var1.setline(453);
         var3 = var1.getglobal("_PREFIX");
         var1.getglobal("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("base"), var3);
         var3 = null;
         var1.setline(454);
         var3 = var1.getglobal("_EXEC_PREFIX");
         var1.getglobal("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("platbase"), var3);
         var3 = null;
         var1.setline(455);
         var3 = var1.getglobal("_PROJECT_BASE");
         var1.getglobal("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("projectbase"), var3);
         var3 = null;
         var1.setline(457);
         var3 = var1.getglobal("os").__getattr__("name");
         var10000 = var3._ne(PyString.fromInterned("java"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(458);
            var3 = var1.getglobal("os").__getattr__("name");
            var10000 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("nt"), PyString.fromInterned("os2")}));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(459);
               var1.getglobal("_init_non_posix").__call__(var2, var1.getglobal("_CONFIG_VARS"));
            }

            var1.setline(460);
            var3 = var1.getglobal("os").__getattr__("name");
            var10000 = var3._eq(PyString.fromInterned("posix"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(461);
               var1.getglobal("_init_posix").__call__(var2, var1.getglobal("_CONFIG_VARS"));
            }
         }

         var1.setline(466);
         var3 = var1.getglobal("_getuserbase").__call__(var2);
         var1.getglobal("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("userbase"), var3);
         var3 = null;
         var1.setline(468);
         PyString var8 = PyString.fromInterned("srcdir");
         var10000 = var8._notin(var1.getglobal("_CONFIG_VARS"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(469);
            var3 = var1.getglobal("_PROJECT_BASE");
            var1.getglobal("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("srcdir"), var3);
            var3 = null;
         }

         var1.setline(476);
         var10000 = var1.getglobal("_PYTHON_BUILD");
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("os").__getattr__("name");
            var10000 = var3._eq(PyString.fromInterned("posix"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getglobal("os").__getattr__("name");
               var10000 = var3._ne(PyString.fromInterned("java"));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(477);
            var3 = var1.getglobal("_PROJECT_BASE");
            var1.setlocal(2, var3);
            var3 = null;

            try {
               var1.setline(479);
               var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
               var1.setlocal(3, var3);
               var3 = null;
            } catch (Throwable var6) {
               PyException var9 = Py.setException(var6, var1);
               if (!var9.match(var1.getglobal("OSError"))) {
                  throw var9;
               }

               var1.setline(481);
               var4 = var1.getglobal("None");
               var1.setlocal(3, var4);
               var4 = null;
            }

            var1.setline(482);
            var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isabs").__call__(var2, var1.getglobal("_CONFIG_VARS").__getitem__(PyString.fromInterned("srcdir"))).__not__();
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(2);
               var10000 = var3._ne(var1.getlocal(3));
               var3 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(487);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getglobal("_CONFIG_VARS").__getitem__(PyString.fromInterned("srcdir")));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(488);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(4));
               var1.getglobal("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("srcdir"), var3);
               var3 = null;
            }
         }

         var1.setline(492);
         var3 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var3._eq(PyString.fromInterned("darwin"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var10000 = var1.getglobal("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("java")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(493);
            var3 = var1.getglobal("os").__getattr__("uname").__call__(var2).__getitem__(Py.newInteger(2));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(494);
            var3 = var1.getglobal("int").__call__(var2, var1.getlocal(5).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getitem__(Py.newInteger(0)));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(496);
            var3 = var1.getlocal(6);
            var10000 = var3._lt(Py.newInteger(8));
            var3 = null;
            PyObject var5;
            if (var10000.__nonzero__()) {
               var1.setline(501);
               var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("LDFLAGS"), PyString.fromInterned("BASECFLAGS"), PyString.fromInterned("CFLAGS"), PyString.fromInterned("PY_CFLAGS"), PyString.fromInterned("BLDSHARED")})).__iter__();

               while(true) {
                  var1.setline(501);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(7, var4);
                  var1.setline(505);
                  var5 = var1.getglobal("_CONFIG_VARS").__getitem__(var1.getlocal(7));
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(506);
                  var5 = var1.getlocal(1).__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("-arch\\s+\\w+\\s"), (PyObject)PyString.fromInterned(" "), (PyObject)var1.getlocal(8));
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(507);
                  var5 = var1.getlocal(1).__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("-isysroot [^ \t]*"), (PyObject)PyString.fromInterned(" "), (PyObject)var1.getlocal(8));
                  var1.setlocal(8, var5);
                  var5 = null;
                  var1.setline(508);
                  var5 = var1.getlocal(8);
                  var1.getglobal("_CONFIG_VARS").__setitem__(var1.getlocal(7), var5);
                  var5 = null;
               }
            } else {
               var1.setline(515);
               var8 = PyString.fromInterned("ARCHFLAGS");
               var10000 = var8._in(var1.getglobal("os").__getattr__("environ"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(516);
                  var3 = var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("ARCHFLAGS"));
                  var1.setlocal(9, var3);
                  var3 = null;
                  var1.setline(517);
                  var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("LDFLAGS"), PyString.fromInterned("BASECFLAGS"), PyString.fromInterned("CFLAGS"), PyString.fromInterned("PY_CFLAGS"), PyString.fromInterned("BLDSHARED")})).__iter__();

                  while(true) {
                     var1.setline(517);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var1.setlocal(7, var4);
                     var1.setline(522);
                     var5 = var1.getglobal("_CONFIG_VARS").__getitem__(var1.getlocal(7));
                     var1.setlocal(8, var5);
                     var5 = null;
                     var1.setline(523);
                     var5 = var1.getlocal(1).__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("-arch\\s+\\w+\\s"), (PyObject)PyString.fromInterned(" "), (PyObject)var1.getlocal(8));
                     var1.setlocal(8, var5);
                     var5 = null;
                     var1.setline(524);
                     var5 = var1.getlocal(8)._add(PyString.fromInterned(" "))._add(var1.getlocal(9));
                     var1.setlocal(8, var5);
                     var5 = null;
                     var1.setline(525);
                     var5 = var1.getlocal(8);
                     var1.getglobal("_CONFIG_VARS").__setitem__(var1.getlocal(7), var5);
                     var5 = null;
                  }
               }

               var1.setline(537);
               var3 = var1.getglobal("_CONFIG_VARS").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CFLAGS"), (PyObject)PyString.fromInterned(""));
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(538);
               var3 = var1.getlocal(1).__getattr__("search").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-isysroot\\s+(\\S+)"), (PyObject)var1.getlocal(10));
               var1.setlocal(11, var3);
               var3 = null;
               var1.setline(539);
               var3 = var1.getlocal(11);
               var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(540);
                  var3 = var1.getlocal(11).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1));
                  var1.setlocal(12, var3);
                  var3 = null;
                  var1.setline(541);
                  if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(12)).__not__().__nonzero__()) {
                     var1.setline(542);
                     var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("LDFLAGS"), PyString.fromInterned("BASECFLAGS"), PyString.fromInterned("CFLAGS"), PyString.fromInterned("PY_CFLAGS"), PyString.fromInterned("BLDSHARED")})).__iter__();

                     while(true) {
                        var1.setline(542);
                        var4 = var3.__iternext__();
                        if (var4 == null) {
                           break;
                        }

                        var1.setlocal(7, var4);
                        var1.setline(547);
                        var5 = var1.getglobal("_CONFIG_VARS").__getitem__(var1.getlocal(7));
                        var1.setlocal(8, var5);
                        var5 = null;
                        var1.setline(548);
                        var5 = var1.getlocal(1).__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned("-isysroot\\s+\\S+(\\s|$)"), (PyObject)PyString.fromInterned(" "), (PyObject)var1.getlocal(8));
                        var1.setlocal(8, var5);
                        var5 = null;
                        var1.setline(549);
                        var5 = var1.getlocal(8);
                        var1.getglobal("_CONFIG_VARS").__setitem__(var1.getlocal(7), var5);
                        var5 = null;
                     }
                  }
               }
            }
         }
      }

      var1.setline(551);
      if (!var1.getlocal(0).__nonzero__()) {
         var1.setline(557);
         var3 = var1.getglobal("_CONFIG_VARS");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(552);
         PyList var10 = new PyList(Py.EmptyObjects);
         var1.setlocal(13, var10);
         var3 = null;
         var1.setline(553);
         var3 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(553);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(555);
               var3 = var1.getlocal(13);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(14, var4);
            var1.setline(554);
            var1.getlocal(13).__getattr__("append").__call__(var2, var1.getglobal("_CONFIG_VARS").__getattr__("get").__call__(var2, var1.getlocal(14)));
         }
      }
   }

   public PyObject get_config_var$21(PyFrame var1, ThreadState var2) {
      var1.setline(564);
      PyString.fromInterned("Return the value of a single variable using the dictionary returned by\n    'get_config_vars()'.\n\n    Equivalent to get_config_vars().get(name)\n    ");
      var1.setline(565);
      PyObject var3 = var1.getglobal("get_config_vars").__call__(var2).__getattr__("get").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_platform$22(PyFrame var1, ThreadState var2) {
      var1.setline(591);
      PyString.fromInterned("Return a string that identifies the current platform.\n\n    This is used mainly to distinguish platform-specific build directories and\n    platform-specific built distributions.  Typically includes the OS name\n    and version and the architecture (as supplied by 'os.uname()'),\n    although the exact information included depends on the OS; eg. for IRIX\n    the architecture isn't particularly important (IRIX only runs on SGI\n    hardware), but for Linux the kernel version isn't particularly\n    important.\n\n    Examples of returned values:\n       linux-i586\n       linux-alpha (?)\n       solaris-2.6-sun4u\n       irix-5.3\n       irix64-6.2\n\n    Windows will return one of:\n       win-amd64 (64bit Windows on AMD64 (aka x86_64, Intel64, EM64T, etc)\n       win-ia64 (64bit Windows on Itanium)\n       win32 (all others - specifically, sys.platform is returned)\n\n    For other non-POSIX platforms, currently just returns 'sys.platform'.\n    ");
      var1.setline(592);
      PyObject var3 = imp.importOne("re", var1, -1);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(594);
      var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getglobal("os").__getattr__("name");
         var10000 = var3._ne(PyString.fromInterned("java"));
         var3 = null;
      }

      PyObject var4;
      if (var10000.__nonzero__()) {
         var1.setline(596);
         PyString var9 = PyString.fromInterned(" bit (");
         var1.setlocal(1, var9);
         var3 = null;
         var1.setline(597);
         var3 = var1.getglobal("sys").__getattr__("version").__getattr__("find").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(598);
         var3 = var1.getlocal(2);
         var10000 = var3._eq(Py.newInteger(-1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(599);
            var3 = var1.getglobal("sys").__getattr__("platform");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(600);
            var4 = var1.getglobal("sys").__getattr__("version").__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(")"), (PyObject)var1.getlocal(2));
            var1.setlocal(3, var4);
            var4 = null;
            var1.setline(601);
            var4 = var1.getglobal("sys").__getattr__("version").__getslice__(var1.getlocal(2)._add(var1.getglobal("len").__call__(var2, var1.getlocal(1))), var1.getlocal(3), (PyObject)null).__getattr__("lower").__call__(var2);
            var1.setlocal(4, var4);
            var4 = null;
            var1.setline(602);
            var4 = var1.getlocal(4);
            var10000 = var4._eq(PyString.fromInterned("amd64"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(603);
               var9 = PyString.fromInterned("win-amd64");
               var1.f_lasti = -1;
               return var9;
            } else {
               var1.setline(604);
               var4 = var1.getlocal(4);
               var10000 = var4._eq(PyString.fromInterned("itanium"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(605);
                  var9 = PyString.fromInterned("win-ia64");
                  var1.f_lasti = -1;
                  return var9;
               } else {
                  var1.setline(606);
                  var3 = var1.getglobal("sys").__getattr__("platform");
                  var1.f_lasti = -1;
                  return var3;
               }
            }
         }
      } else {
         var1.setline(609);
         var4 = var1.getglobal("os").__getattr__("name");
         var10000 = var4._ne(PyString.fromInterned("posix"));
         var4 = null;
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("os"), (PyObject)PyString.fromInterned("uname")).__not__();
         }

         if (var10000.__nonzero__()) {
            var1.setline(612);
            var3 = var1.getglobal("sys").__getattr__("platform");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(615);
            var4 = var1.getglobal("os").__getattr__("uname").__call__(var2);
            PyObject[] var5 = Py.unpackSequence(var4, 5);
            PyObject var6 = var5[0];
            var1.setlocal(5, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[3];
            var1.setlocal(8, var6);
            var6 = null;
            var6 = var5[4];
            var1.setlocal(9, var6);
            var6 = null;
            var4 = null;
            var1.setline(619);
            var4 = var1.getlocal(5).__getattr__("lower").__call__(var2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)PyString.fromInterned(""));
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(620);
            var4 = var1.getlocal(9).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" "), (PyObject)PyString.fromInterned("_"));
            var1.setlocal(9, var4);
            var4 = null;
            var1.setline(621);
            var4 = var1.getlocal(9).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/"), (PyObject)PyString.fromInterned("-"));
            var1.setlocal(9, var4);
            var4 = null;
            var1.setline(623);
            var4 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
            var10000 = var4._eq(PyString.fromInterned("linux"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(627);
               var3 = PyString.fromInterned("%s-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(9)}));
               var1.f_lasti = -1;
               return var3;
            } else {
               var1.setline(628);
               var4 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(5), (PyObject)null);
               var10000 = var4._eq(PyString.fromInterned("sunos"));
               var4 = null;
               PyString var11;
               if (var10000.__nonzero__()) {
                  var1.setline(629);
                  var4 = var1.getlocal(7).__getitem__(Py.newInteger(0));
                  var10000 = var4._ge(PyString.fromInterned("5"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(630);
                     var11 = PyString.fromInterned("solaris");
                     var1.setlocal(5, var11);
                     var4 = null;
                     var1.setline(631);
                     var4 = PyString.fromInterned("%d.%s")._mod(new PyTuple(new PyObject[]{var1.getglobal("int").__call__(var2, var1.getlocal(7).__getitem__(Py.newInteger(0)))._sub(Py.newInteger(3)), var1.getlocal(7).__getslice__(Py.newInteger(2), (PyObject)null, (PyObject)null)}));
                     var1.setlocal(7, var4);
                     var4 = null;
                     var1.setline(635);
                     PyDictionary var12 = new PyDictionary(new PyObject[]{Py.newInteger(Integer.MAX_VALUE), PyString.fromInterned("32bit"), Py.newLong("9223372036854775807"), PyString.fromInterned("64bit")});
                     var1.setlocal(10, var12);
                     var4 = null;
                     var1.setline(636);
                     var4 = var1.getlocal(9);
                     var4 = var4._iadd(PyString.fromInterned(".%s")._mod(var1.getlocal(10).__getitem__(var1.getglobal("sys").__getattr__("maxint"))));
                     var1.setlocal(9, var4);
                  }
               } else {
                  var1.setline(638);
                  var4 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(4), (PyObject)null);
                  var10000 = var4._eq(PyString.fromInterned("irix"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(639);
                     var3 = PyString.fromInterned("%s-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(7)}));
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(640);
                  var4 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
                  var10000 = var4._eq(PyString.fromInterned("aix"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(641);
                     var3 = PyString.fromInterned("%s-%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(8), var1.getlocal(7)}));
                     var1.f_lasti = -1;
                     return var3;
                  }

                  var1.setline(642);
                  var4 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
                  var10000 = var4._eq(PyString.fromInterned("cygwin"));
                  var4 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(643);
                     var11 = PyString.fromInterned("cygwin");
                     var1.setlocal(5, var11);
                     var4 = null;
                     var1.setline(644);
                     var4 = var1.getlocal(0).__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[\\d.]+"));
                     var1.setlocal(11, var4);
                     var4 = null;
                     var1.setline(645);
                     var4 = var1.getlocal(11).__getattr__("match").__call__(var2, var1.getlocal(7));
                     var1.setlocal(12, var4);
                     var4 = null;
                     var1.setline(646);
                     if (var1.getlocal(12).__nonzero__()) {
                        var1.setline(647);
                        var4 = var1.getlocal(12).__getattr__("group").__call__(var2);
                        var1.setlocal(7, var4);
                        var4 = null;
                     }
                  } else {
                     var1.setline(648);
                     var4 = var1.getlocal(5).__getslice__((PyObject)null, Py.newInteger(6), (PyObject)null);
                     var10000 = var4._eq(PyString.fromInterned("darwin"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(655);
                        var4 = var1.getglobal("get_config_vars").__call__(var2);
                        var1.setlocal(13, var4);
                        var4 = null;
                        var1.setline(656);
                        var4 = var1.getlocal(13).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MACOSX_DEPLOYMENT_TARGET"));
                        var1.setlocal(14, var4);
                        var4 = null;
                        var1.setline(658);
                        if (Py.newInteger(1).__nonzero__()) {
                           label151: {
                              var1.setline(662);
                              var4 = var1.getlocal(14);
                              var1.setlocal(15, var4);
                              var4 = null;

                              try {
                                 var1.setline(667);
                                 var4 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/System/Library/CoreServices/SystemVersion.plist"));
                                 var1.setlocal(16, var4);
                                 var4 = null;
                              } catch (Throwable var8) {
                                 PyException var10 = Py.setException(var8, var1);
                                 if (!var10.match(var1.getglobal("IOError"))) {
                                    throw var10;
                                 }

                                 var1.setline(671);
                                 break label151;
                              }

                              var5 = null;

                              try {
                                 var1.setline(674);
                                 var6 = var1.getlocal(0).__getattr__("search").__call__(var2, PyString.fromInterned("<key>ProductUserVisibleVersion</key>\\s*")._add(PyString.fromInterned("<string>(.*?)</string>")), var1.getlocal(16).__getattr__("read").__call__(var2));
                                 var1.setlocal(12, var6);
                                 var6 = null;
                                 var1.setline(677);
                                 var6 = var1.getlocal(12);
                                 var10000 = var6._isnot(var1.getglobal("None"));
                                 var6 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(678);
                                    var6 = PyString.fromInterned(".").__getattr__("join").__call__(var2, var1.getlocal(12).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null));
                                    var1.setlocal(15, var6);
                                    var6 = null;
                                 }
                              } catch (Throwable var7) {
                                 Py.addTraceback(var7, var1);
                                 var1.setline(681);
                                 var1.getlocal(16).__getattr__("close").__call__(var2);
                                 throw (Throwable)var7;
                              }

                              var1.setline(681);
                              var1.getlocal(16).__getattr__("close").__call__(var2);
                           }
                        }

                        var1.setline(683);
                        if (var1.getlocal(14).__not__().__nonzero__()) {
                           var1.setline(684);
                           var4 = var1.getlocal(15);
                           var1.setlocal(14, var4);
                           var4 = null;
                        }

                        var1.setline(686);
                        if (var1.getlocal(14).__nonzero__()) {
                           var1.setline(687);
                           var4 = var1.getlocal(14);
                           var1.setlocal(7, var4);
                           var4 = null;
                           var1.setline(688);
                           var11 = PyString.fromInterned("macosx");
                           var1.setlocal(5, var11);
                           var4 = null;
                           var1.setline(690);
                           var4 = var1.getlocal(15)._add(PyString.fromInterned("."));
                           var10000 = var4._ge(PyString.fromInterned("10.4."));
                           var4 = null;
                           if (var10000.__nonzero__()) {
                              var11 = PyString.fromInterned("-arch");
                              var10000 = var11._in(var1.getglobal("get_config_vars").__call__(var2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CFLAGS"), (PyObject)PyString.fromInterned("")).__getattr__("strip").__call__(var2));
                              var4 = null;
                           }

                           if (var10000.__nonzero__()) {
                              var1.setline(698);
                              var11 = PyString.fromInterned("fat");
                              var1.setlocal(9, var11);
                              var4 = null;
                              var1.setline(699);
                              var4 = var1.getglobal("get_config_vars").__call__(var2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CFLAGS"));
                              var1.setlocal(17, var4);
                              var4 = null;
                              var1.setline(701);
                              var4 = var1.getlocal(0).__getattr__("findall").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-arch\\s+(\\S+)"), (PyObject)var1.getlocal(17));
                              var1.setlocal(18, var4);
                              var4 = null;
                              var1.setline(702);
                              var4 = var1.getglobal("tuple").__call__(var2, var1.getglobal("sorted").__call__(var2, var1.getglobal("set").__call__(var2, var1.getlocal(18))));
                              var1.setlocal(18, var4);
                              var4 = null;
                              var1.setline(704);
                              var4 = var1.getglobal("len").__call__(var2, var1.getlocal(18));
                              var10000 = var4._eq(Py.newInteger(1));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(705);
                                 var4 = var1.getlocal(18).__getitem__(Py.newInteger(0));
                                 var1.setlocal(9, var4);
                                 var4 = null;
                              } else {
                                 var1.setline(706);
                                 var4 = var1.getlocal(18);
                                 var10000 = var4._eq(new PyTuple(new PyObject[]{PyString.fromInterned("i386"), PyString.fromInterned("ppc")}));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(707);
                                    var11 = PyString.fromInterned("fat");
                                    var1.setlocal(9, var11);
                                    var4 = null;
                                 } else {
                                    var1.setline(708);
                                    var4 = var1.getlocal(18);
                                    var10000 = var4._eq(new PyTuple(new PyObject[]{PyString.fromInterned("i386"), PyString.fromInterned("x86_64")}));
                                    var4 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(709);
                                       var11 = PyString.fromInterned("intel");
                                       var1.setlocal(9, var11);
                                       var4 = null;
                                    } else {
                                       var1.setline(710);
                                       var4 = var1.getlocal(18);
                                       var10000 = var4._eq(new PyTuple(new PyObject[]{PyString.fromInterned("i386"), PyString.fromInterned("ppc"), PyString.fromInterned("x86_64")}));
                                       var4 = null;
                                       if (var10000.__nonzero__()) {
                                          var1.setline(711);
                                          var11 = PyString.fromInterned("fat3");
                                          var1.setlocal(9, var11);
                                          var4 = null;
                                       } else {
                                          var1.setline(712);
                                          var4 = var1.getlocal(18);
                                          var10000 = var4._eq(new PyTuple(new PyObject[]{PyString.fromInterned("ppc64"), PyString.fromInterned("x86_64")}));
                                          var4 = null;
                                          if (var10000.__nonzero__()) {
                                             var1.setline(713);
                                             var11 = PyString.fromInterned("fat64");
                                             var1.setlocal(9, var11);
                                             var4 = null;
                                          } else {
                                             var1.setline(714);
                                             var4 = var1.getlocal(18);
                                             var10000 = var4._eq(new PyTuple(new PyObject[]{PyString.fromInterned("i386"), PyString.fromInterned("ppc"), PyString.fromInterned("ppc64"), PyString.fromInterned("x86_64")}));
                                             var4 = null;
                                             if (!var10000.__nonzero__()) {
                                                var1.setline(717);
                                                throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("Don't know machine value for archs=%r")._mod(new PyTuple(new PyObject[]{var1.getlocal(18)}))));
                                             }

                                             var1.setline(715);
                                             var11 = PyString.fromInterned("universal");
                                             var1.setlocal(9, var11);
                                             var4 = null;
                                          }
                                       }
                                    }
                                 }
                              }
                           } else {
                              var1.setline(720);
                              var4 = var1.getlocal(9);
                              var10000 = var4._eq(PyString.fromInterned("i386"));
                              var4 = null;
                              if (var10000.__nonzero__()) {
                                 var1.setline(724);
                                 var4 = var1.getglobal("sys").__getattr__("maxint");
                                 var10000 = var4._ge(Py.newInteger(2)._pow(Py.newInteger(32)));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(725);
                                    var11 = PyString.fromInterned("x86_64");
                                    var1.setlocal(9, var11);
                                    var4 = null;
                                 }
                              } else {
                                 var1.setline(727);
                                 var4 = var1.getlocal(9);
                                 var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("PowerPC"), PyString.fromInterned("Power_Macintosh")}));
                                 var4 = null;
                                 if (var10000.__nonzero__()) {
                                    var1.setline(730);
                                    var4 = var1.getglobal("sys").__getattr__("maxint");
                                    var10000 = var4._ge(Py.newInteger(2)._pow(Py.newInteger(32)));
                                    var4 = null;
                                    if (var10000.__nonzero__()) {
                                       var1.setline(731);
                                       var11 = PyString.fromInterned("ppc64");
                                       var1.setlocal(9, var11);
                                       var4 = null;
                                    } else {
                                       var1.setline(733);
                                       var11 = PyString.fromInterned("ppc");
                                       var1.setlocal(9, var11);
                                       var4 = null;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }

               var1.setline(735);
               var3 = PyString.fromInterned("%s-%s-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(7), var1.getlocal(9)}));
               var1.f_lasti = -1;
               return var3;
            }
         }
      }
   }

   public PyObject get_python_version$23(PyFrame var1, ThreadState var2) {
      var1.setline(739);
      PyObject var3 = var1.getglobal("_PY_VERSION_SHORT");
      var1.f_lasti = -1;
      return var3;
   }

   public sysconfig$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"path"};
      fileSystemEncode$1 = Py.newCode(1, var2, var1, "fileSystemEncode", 8, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      _safe_realpath$2 = Py.newCode(1, var2, var1, "_safe_realpath", 122, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fn"};
      is_python_build$3 = Py.newCode(0, var2, var1, "is_python_build", 146, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s", "local_vars", "var"};
      _subst_vars$4 = Py.newCode(2, var2, var1, "_subst_vars", 159, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"target_dict", "other_dict", "target_keys", "key", "value"};
      _extend_dict$5 = Py.newCode(2, var2, var1, "_extend_dict", 168, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"scheme", "vars", "res", "key", "value"};
      _expand_vars$6 = Py.newCode(2, var2, var1, "_expand_vars", 175, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _get_default_scheme$7 = Py.newCode(0, var2, var1, "_get_default_scheme", 192, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"env_base", "joinuser", "base", "framework"};
      _getuserbase$8 = Py.newCode(0, var2, var1, "_getuserbase", 199, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args"};
      joinuser$9 = Py.newCode(1, var2, var1, "joinuser", 201, true, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"filename", "vars", "re", "_variable_rx", "_findvar1_rx", "_findvar2_rx", "done", "notdone", "f", "lines", "line", "m", "n", "v", "tmpv", "name", "value", "found", "item", "after", "k"};
      _parse_makefile$10 = Py.newCode(2, var2, var1, "_parse_makefile", 225, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      _get_makefile_filename$11 = Py.newCode(0, var2, var1, "_get_makefile_filename", 311, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"vars", "makefile", "e", "msg", "config_h", "f"};
      _init_posix$12 = Py.newCode(1, var2, var1, "_init_posix", 317, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"vars"};
      _init_non_posix$13 = Py.newCode(1, var2, var1, "_init_non_posix", 346, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"fp", "vars", "re", "define_rx", "undef_rx", "line", "m", "n", "v"};
      parse_config_h$14 = Py.newCode(2, var2, var1, "parse_config_h", 362, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"inc_dir"};
      get_config_h_filename$15 = Py.newCode(0, var2, var1, "get_config_h_filename", 391, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"schemes"};
      get_scheme_names$16 = Py.newCode(0, var2, var1, "get_scheme_names", 403, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      get_path_names$17 = Py.newCode(0, var2, var1, "get_path_names", 409, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"scheme", "vars", "expand"};
      get_paths$18 = Py.newCode(3, var2, var1, "get_paths", 413, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "scheme", "vars", "expand"};
      get_path$19 = Py.newCode(4, var2, var1, "get_path", 424, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"args", "re", "base", "cwd", "srcdir", "kernel_version", "major_version", "key", "flags", "arch", "CFLAGS", "m", "sdk", "vals", "name"};
      get_config_vars$20 = Py.newCode(1, var2, var1, "get_config_vars", 431, true, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name"};
      get_config_var$21 = Py.newCode(1, var2, var1, "get_config_var", 559, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"re", "prefix", "i", "j", "look", "osname", "host", "release", "version", "machine", "bitness", "rel_re", "m", "cfgvars", "macver", "macrelease", "f", "cflags", "archs"};
      get_platform$22 = Py.newCode(0, var2, var1, "get_platform", 567, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      get_python_version$23 = Py.newCode(0, var2, var1, "get_python_version", 738, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new sysconfig$py("sysconfig$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(sysconfig$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.fileSystemEncode$1(var2, var3);
         case 2:
            return this._safe_realpath$2(var2, var3);
         case 3:
            return this.is_python_build$3(var2, var3);
         case 4:
            return this._subst_vars$4(var2, var3);
         case 5:
            return this._extend_dict$5(var2, var3);
         case 6:
            return this._expand_vars$6(var2, var3);
         case 7:
            return this._get_default_scheme$7(var2, var3);
         case 8:
            return this._getuserbase$8(var2, var3);
         case 9:
            return this.joinuser$9(var2, var3);
         case 10:
            return this._parse_makefile$10(var2, var3);
         case 11:
            return this._get_makefile_filename$11(var2, var3);
         case 12:
            return this._init_posix$12(var2, var3);
         case 13:
            return this._init_non_posix$13(var2, var3);
         case 14:
            return this.parse_config_h$14(var2, var3);
         case 15:
            return this.get_config_h_filename$15(var2, var3);
         case 16:
            return this.get_scheme_names$16(var2, var3);
         case 17:
            return this.get_path_names$17(var2, var3);
         case 18:
            return this.get_paths$18(var2, var3);
         case 19:
            return this.get_path$19(var2, var3);
         case 20:
            return this.get_config_vars$20(var2, var3);
         case 21:
            return this.get_config_var$21(var2, var3);
         case 22:
            return this.get_platform$22(var2, var3);
         case 23:
            return this.get_python_version$23(var2, var3);
         default:
            return null;
      }
   }
}
