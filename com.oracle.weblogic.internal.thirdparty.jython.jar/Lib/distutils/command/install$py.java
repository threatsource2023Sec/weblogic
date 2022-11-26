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
@MTime(1498849384000L)
@Filename("distutils/command/install.py")
public class install$py extends PyFunctionTable implements PyRunnable {
   static install$py self;
   static final PyCode f$0;
   static final PyCode install$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode dump_dirs$4;
   static final PyCode finalize_unix$5;
   static final PyCode finalize_other$6;
   static final PyCode select_scheme$7;
   static final PyCode _expand_attrs$8;
   static final PyCode expand_basedirs$9;
   static final PyCode expand_dirs$10;
   static final PyCode convert_paths$11;
   static final PyCode handle_extra_path$12;
   static final PyCode change_roots$13;
   static final PyCode create_home_path$14;
   static final PyCode run$15;
   static final PyCode create_path_file$16;
   static final PyCode get_outputs$17;
   static final PyCode get_inputs$18;
   static final PyCode has_lib$19;
   static final PyCode has_headers$20;
   static final PyCode has_scripts$21;
   static final PyCode has_data$22;
   static final PyCode f$23;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.install\n\nImplements the Distutils 'install' command."));
      var1.setline(3);
      PyString.fromInterned("distutils.command.install\n\nImplements the Distutils 'install' command.");
      var1.setline(5);
      String[] var3 = new String[]{"log"};
      PyObject[] var5 = imp.importFrom("distutils", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(9);
      PyString var6 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var6);
      var3 = null;
      var1.setline(11);
      PyObject var7 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var7);
      var3 = null;
      var7 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var7);
      var3 = null;
      var7 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var7);
      var3 = null;
      var1.setline(12);
      imp.importAll("types", var1, -1);
      var1.setline(13);
      var3 = new String[]{"Command"};
      var5 = imp.importFrom("distutils.core", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(14);
      var3 = new String[]{"DEBUG"};
      var5 = imp.importFrom("distutils.debug", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("DEBUG", var4);
      var4 = null;
      var1.setline(15);
      var3 = new String[]{"get_config_vars"};
      var5 = imp.importFrom("distutils.sysconfig", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("get_config_vars", var4);
      var4 = null;
      var1.setline(16);
      var3 = new String[]{"DistutilsPlatformError"};
      var5 = imp.importFrom("distutils.errors", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var1.setline(17);
      var3 = new String[]{"write_file"};
      var5 = imp.importFrom("distutils.file_util", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("write_file", var4);
      var4 = null;
      var1.setline(18);
      var3 = new String[]{"convert_path", "subst_vars", "change_root"};
      var5 = imp.importFrom("distutils.util", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("convert_path", var4);
      var4 = null;
      var4 = var5[1];
      var1.setlocal("subst_vars", var4);
      var4 = null;
      var4 = var5[2];
      var1.setlocal("change_root", var4);
      var4 = null;
      var1.setline(19);
      var3 = new String[]{"get_platform"};
      var5 = imp.importFrom("distutils.util", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("get_platform", var4);
      var4 = null;
      var1.setline(20);
      var3 = new String[]{"DistutilsOptionError"};
      var5 = imp.importFrom("distutils.errors", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var1.setline(21);
      var3 = new String[]{"USER_BASE"};
      var5 = imp.importFrom("site", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("USER_BASE", var4);
      var4 = null;
      var1.setline(22);
      var3 = new String[]{"USER_SITE"};
      var5 = imp.importFrom("site", var3, var1, -1);
      var4 = var5[0];
      var1.setlocal("USER_SITE", var4);
      var4 = null;
      var1.setline(25);
      var7 = var1.getname("sys").__getattr__("version");
      PyObject var10000 = var7._lt(PyString.fromInterned("2.2"));
      var3 = null;
      PyDictionary var8;
      if (var10000.__nonzero__()) {
         var1.setline(26);
         var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("purelib"), PyString.fromInterned("$base"), PyString.fromInterned("platlib"), PyString.fromInterned("$base"), PyString.fromInterned("headers"), PyString.fromInterned("$base/Include/$dist_name"), PyString.fromInterned("scripts"), PyString.fromInterned("$base/Scripts"), PyString.fromInterned("data"), PyString.fromInterned("$base")});
         var1.setlocal("WINDOWS_SCHEME", var8);
         var3 = null;
      } else {
         var1.setline(34);
         var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("purelib"), PyString.fromInterned("$base/Lib/site-packages"), PyString.fromInterned("platlib"), PyString.fromInterned("$base/Lib/site-packages"), PyString.fromInterned("headers"), PyString.fromInterned("$base/Include/$dist_name"), PyString.fromInterned("scripts"), PyString.fromInterned("$base/Scripts"), PyString.fromInterned("data"), PyString.fromInterned("$base")});
         var1.setlocal("WINDOWS_SCHEME", var8);
         var3 = null;
      }

      var1.setline(42);
      var8 = new PyDictionary(new PyObject[]{PyString.fromInterned("unix_prefix"), new PyDictionary(new PyObject[]{PyString.fromInterned("purelib"), PyString.fromInterned("$base/lib/python$py_version_short/site-packages"), PyString.fromInterned("platlib"), PyString.fromInterned("$platbase/lib/python$py_version_short/site-packages"), PyString.fromInterned("headers"), PyString.fromInterned("$base/include/python$py_version_short/$dist_name"), PyString.fromInterned("scripts"), PyString.fromInterned("$base/bin"), PyString.fromInterned("data"), PyString.fromInterned("$base")}), PyString.fromInterned("unix_home"), new PyDictionary(new PyObject[]{PyString.fromInterned("purelib"), PyString.fromInterned("$base/lib/python"), PyString.fromInterned("platlib"), PyString.fromInterned("$base/lib/python"), PyString.fromInterned("headers"), PyString.fromInterned("$base/include/python/$dist_name"), PyString.fromInterned("scripts"), PyString.fromInterned("$base/bin"), PyString.fromInterned("data"), PyString.fromInterned("$base")}), PyString.fromInterned("unix_user"), new PyDictionary(new PyObject[]{PyString.fromInterned("purelib"), PyString.fromInterned("$usersite"), PyString.fromInterned("platlib"), PyString.fromInterned("$usersite"), PyString.fromInterned("headers"), PyString.fromInterned("$userbase/include/python$py_version_short/$dist_name"), PyString.fromInterned("scripts"), PyString.fromInterned("$userbase/bin"), PyString.fromInterned("data"), PyString.fromInterned("$userbase")}), PyString.fromInterned("nt"), var1.getname("WINDOWS_SCHEME"), PyString.fromInterned("nt_user"), new PyDictionary(new PyObject[]{PyString.fromInterned("purelib"), PyString.fromInterned("$usersite"), PyString.fromInterned("platlib"), PyString.fromInterned("$usersite"), PyString.fromInterned("headers"), PyString.fromInterned("$userbase/Python$py_version_nodot/Include/$dist_name"), PyString.fromInterned("scripts"), PyString.fromInterned("$userbase/Scripts"), PyString.fromInterned("data"), PyString.fromInterned("$userbase")}), PyString.fromInterned("os2"), new PyDictionary(new PyObject[]{PyString.fromInterned("purelib"), PyString.fromInterned("$base/Lib/site-packages"), PyString.fromInterned("platlib"), PyString.fromInterned("$base/Lib/site-packages"), PyString.fromInterned("headers"), PyString.fromInterned("$base/Include/$dist_name"), PyString.fromInterned("scripts"), PyString.fromInterned("$base/Scripts"), PyString.fromInterned("data"), PyString.fromInterned("$base")}), PyString.fromInterned("os2_home"), new PyDictionary(new PyObject[]{PyString.fromInterned("purelib"), PyString.fromInterned("$usersite"), PyString.fromInterned("platlib"), PyString.fromInterned("$usersite"), PyString.fromInterned("headers"), PyString.fromInterned("$userbase/include/python$py_version_short/$dist_name"), PyString.fromInterned("scripts"), PyString.fromInterned("$userbase/bin"), PyString.fromInterned("data"), PyString.fromInterned("$userbase")}), PyString.fromInterned("java"), new PyDictionary(new PyObject[]{PyString.fromInterned("purelib"), PyString.fromInterned("$base/Lib/site-packages"), PyString.fromInterned("platlib"), PyString.fromInterned("$base/Lib/site-packages"), PyString.fromInterned("headers"), PyString.fromInterned("$base/Include/$dist_name"), PyString.fromInterned("scripts"), PyString.fromInterned("$base/bin"), PyString.fromInterned("data"), PyString.fromInterned("$base")}), PyString.fromInterned("java_user"), new PyDictionary(new PyObject[]{PyString.fromInterned("purelib"), PyString.fromInterned("$usersite"), PyString.fromInterned("platlib"), PyString.fromInterned("$usersite"), PyString.fromInterned("headers"), PyString.fromInterned("$userbase/include/python$py_version_short/$dist_name"), PyString.fromInterned("scripts"), PyString.fromInterned("$userbase/bin"), PyString.fromInterned("data"), PyString.fromInterned("$userbase")})});
      var1.setlocal("INSTALL_SCHEMES", var8);
      var3 = null;
      var1.setline(105);
      PyTuple var9 = new PyTuple(new PyObject[]{PyString.fromInterned("purelib"), PyString.fromInterned("platlib"), PyString.fromInterned("headers"), PyString.fromInterned("scripts"), PyString.fromInterned("data")});
      var1.setlocal("SCHEME_KEYS", var9);
      var3 = null;
      var1.setline(108);
      var5 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("install", var5, install$1);
      var1.setlocal("install", var4);
      var4 = null;
      Arrays.fill(var5, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject install$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(110);
      PyString var3 = PyString.fromInterned("install everything from build directory");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(112);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("prefix="), var1.getname("None"), PyString.fromInterned("installation prefix")}), new PyTuple(new PyObject[]{PyString.fromInterned("exec-prefix="), var1.getname("None"), PyString.fromInterned("(Unix only) prefix for platform-specific files")}), new PyTuple(new PyObject[]{PyString.fromInterned("home="), var1.getname("None"), PyString.fromInterned("(Unix only) home directory to install under")}), new PyTuple(new PyObject[]{PyString.fromInterned("user"), var1.getname("None"), PyString.fromInterned("install in user site-package '%s'")._mod(var1.getname("USER_SITE"))}), new PyTuple(new PyObject[]{PyString.fromInterned("install-base="), var1.getname("None"), PyString.fromInterned("base installation directory (instead of --prefix or --home)")}), new PyTuple(new PyObject[]{PyString.fromInterned("install-platbase="), var1.getname("None"), PyString.fromInterned("base installation directory for platform-specific files ")._add(PyString.fromInterned("(instead of --exec-prefix or --home)"))}), new PyTuple(new PyObject[]{PyString.fromInterned("root="), var1.getname("None"), PyString.fromInterned("install everything relative to this alternate root directory")}), new PyTuple(new PyObject[]{PyString.fromInterned("install-purelib="), var1.getname("None"), PyString.fromInterned("installation directory for pure Python module distributions")}), new PyTuple(new PyObject[]{PyString.fromInterned("install-platlib="), var1.getname("None"), PyString.fromInterned("installation directory for non-pure module distributions")}), new PyTuple(new PyObject[]{PyString.fromInterned("install-lib="), var1.getname("None"), PyString.fromInterned("installation directory for all module distributions ")._add(PyString.fromInterned("(overrides --install-purelib and --install-platlib)"))}), new PyTuple(new PyObject[]{PyString.fromInterned("install-headers="), var1.getname("None"), PyString.fromInterned("installation directory for C/C++ headers")}), new PyTuple(new PyObject[]{PyString.fromInterned("install-scripts="), var1.getname("None"), PyString.fromInterned("installation directory for Python scripts")}), new PyTuple(new PyObject[]{PyString.fromInterned("install-data="), var1.getname("None"), PyString.fromInterned("installation directory for data files")}), new PyTuple(new PyObject[]{PyString.fromInterned("compile"), PyString.fromInterned("c"), PyString.fromInterned("compile .py to .pyc [default]")}), new PyTuple(new PyObject[]{PyString.fromInterned("no-compile"), var1.getname("None"), PyString.fromInterned("don't compile .py files")}), new PyTuple(new PyObject[]{PyString.fromInterned("optimize="), PyString.fromInterned("O"), PyString.fromInterned("also compile with optimization: -O1 for \"python -O\", -O2 for \"python -OO\", and -O0 to disable [default: -O0]")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("f"), PyString.fromInterned("force installation (overwrite any existing files)")}), new PyTuple(new PyObject[]{PyString.fromInterned("skip-build"), var1.getname("None"), PyString.fromInterned("skip rebuilding everything (for testing/debugging)")}), new PyTuple(new PyObject[]{PyString.fromInterned("record="), var1.getname("None"), PyString.fromInterned("filename in which to record list of installed files")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(173);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("compile"), PyString.fromInterned("force"), PyString.fromInterned("skip-build"), PyString.fromInterned("user")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(174);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("no-compile"), PyString.fromInterned("compile")});
      var1.setlocal("negative_opt", var5);
      var3 = null;
      var1.setline(177);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var7);
      var3 = null;
      var1.setline(254);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var7);
      var3 = null;
      var1.setline(396);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, dump_dirs$4, (PyObject)null);
      var1.setlocal("dump_dirs", var7);
      var3 = null;
      var1.setline(414);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, finalize_unix$5, (PyObject)null);
      var1.setlocal("finalize_unix", var7);
      var3 = null;
      var1.setline(457);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, finalize_other$6, (PyObject)null);
      var1.setlocal("finalize_other", var7);
      var3 = null;
      var1.setline(482);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, select_scheme$7, (PyObject)null);
      var1.setlocal("select_scheme", var7);
      var3 = null;
      var1.setline(491);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _expand_attrs$8, (PyObject)null);
      var1.setlocal("_expand_attrs", var7);
      var3 = null;
      var1.setline(501);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, expand_basedirs$9, (PyObject)null);
      var1.setlocal("expand_basedirs", var7);
      var3 = null;
      var1.setline(506);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, expand_dirs$10, (PyObject)null);
      var1.setlocal("expand_dirs", var7);
      var3 = null;
      var1.setline(515);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, convert_paths$11, (PyObject)null);
      var1.setlocal("convert_paths", var7);
      var3 = null;
      var1.setline(521);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, handle_extra_path$12, (PyObject)null);
      var1.setlocal("handle_extra_path", var7);
      var3 = null;
      var1.setline(555);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, change_roots$13, (PyObject)null);
      var1.setlocal("change_roots", var7);
      var3 = null;
      var1.setline(560);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, create_home_path$14, PyString.fromInterned("Create directories under ~\n        "));
      var1.setlocal("create_home_path", var7);
      var3 = null;
      var1.setline(573);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, run$15, (PyObject)null);
      var1.setlocal("run", var7);
      var3 = null;
      var1.setline(619);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, create_path_file$16, (PyObject)null);
      var1.setlocal("create_path_file", var7);
      var3 = null;
      var1.setline(632);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_outputs$17, (PyObject)null);
      var1.setlocal("get_outputs", var7);
      var3 = null;
      var1.setline(649);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, get_inputs$18, (PyObject)null);
      var1.setlocal("get_inputs", var7);
      var3 = null;
      var1.setline(661);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, has_lib$19, PyString.fromInterned("Return true if the current distribution has any Python\n        modules to install."));
      var1.setlocal("has_lib", var7);
      var3 = null;
      var1.setline(667);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, has_headers$20, (PyObject)null);
      var1.setlocal("has_headers", var7);
      var3 = null;
      var1.setline(670);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, has_scripts$21, (PyObject)null);
      var1.setlocal("has_scripts", var7);
      var3 = null;
      var1.setline(673);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, has_data$22, (PyObject)null);
      var1.setlocal("has_data", var7);
      var3 = null;
      var1.setline(679);
      PyObject[] var10002 = new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("install_lib"), var1.getname("has_lib")}), new PyTuple(new PyObject[]{PyString.fromInterned("install_headers"), var1.getname("has_headers")}), new PyTuple(new PyObject[]{PyString.fromInterned("install_scripts"), var1.getname("has_scripts")}), new PyTuple(new PyObject[]{PyString.fromInterned("install_data"), var1.getname("has_data")}), null};
      PyObject[] var10007 = new PyObject[]{PyString.fromInterned("install_egg_info"), null};
      var1.setline(683);
      var6 = Py.EmptyObjects;
      var10007[1] = new PyFunction(var1.f_globals, var6, f$23);
      var10002[4] = new PyTuple(var10007);
      var4 = new PyList(var10002);
      var1.setlocal("sub_commands", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("prefix", var3);
      var3 = null;
      var1.setline(182);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("exec_prefix", var3);
      var3 = null;
      var1.setline(183);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("home", var3);
      var3 = null;
      var1.setline(184);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"user", var4);
      var3 = null;
      var1.setline(189);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_base", var3);
      var3 = null;
      var1.setline(190);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_platbase", var3);
      var3 = null;
      var1.setline(191);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("root", var3);
      var3 = null;
      var1.setline(197);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_purelib", var3);
      var3 = null;
      var1.setline(198);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_platlib", var3);
      var3 = null;
      var1.setline(199);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_headers", var3);
      var3 = null;
      var1.setline(200);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_lib", var3);
      var3 = null;
      var1.setline(201);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_scripts", var3);
      var3 = null;
      var1.setline(202);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_data", var3);
      var3 = null;
      var1.setline(203);
      var3 = var1.getglobal("USER_BASE");
      var1.getlocal(0).__setattr__("install_userbase", var3);
      var3 = null;
      var1.setline(204);
      var3 = var1.getglobal("USER_SITE");
      var1.getlocal(0).__setattr__("install_usersite", var3);
      var3 = null;
      var1.setline(206);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("compile", var3);
      var3 = null;
      var1.setline(207);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("optimize", var3);
      var3 = null;
      var1.setline(217);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("extra_path", var3);
      var3 = null;
      var1.setline(218);
      var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"install_path_file", var4);
      var3 = null;
      var1.setline(226);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"force", var4);
      var3 = null;
      var1.setline(227);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"skip_build", var4);
      var3 = null;
      var1.setline(228);
      var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"warn_dir", var4);
      var3 = null;
      var1.setline(236);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_base", var3);
      var3 = null;
      var1.setline(237);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_lib", var3);
      var3 = null;
      var1.setline(245);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("record", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(270);
      PyObject var10000 = var1.getlocal(0).__getattr__("prefix");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("exec_prefix");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("home");
         }
      }

      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("install_base");
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("install_platbase");
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(272);
         throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("must supply either prefix/exec-prefix/home or ")._add(PyString.fromInterned("install-base/install-platbase -- not both")));
      } else {
         var1.setline(276);
         var10000 = var1.getlocal(0).__getattr__("home");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("prefix");
            if (!var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("exec_prefix");
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(277);
            throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("must supply either home or prefix/exec-prefix -- not both"));
         } else {
            var1.setline(280);
            var10000 = var1.getlocal(0).__getattr__("user");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("prefix");
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("exec_prefix");
                  if (!var10000.__nonzero__()) {
                     var10000 = var1.getlocal(0).__getattr__("home");
                     if (!var10000.__nonzero__()) {
                        var10000 = var1.getlocal(0).__getattr__("install_base");
                        if (!var10000.__nonzero__()) {
                           var10000 = var1.getlocal(0).__getattr__("install_platbase");
                        }
                     }
                  }
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(282);
               throw Py.makeException(var1.getglobal("DistutilsOptionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("can't combine user with prefix, exec_prefix/home, or install_(plat)base")));
            } else {
               var1.setline(286);
               PyObject var3 = var1.getglobal("os").__getattr__("name");
               var10000 = var3._ne(PyString.fromInterned("posix"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(287);
                  if (var1.getlocal(0).__getattr__("exec_prefix").__nonzero__()) {
                     var1.setline(288);
                     var1.getlocal(0).__getattr__("warn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("exec-prefix option ignored on this platform"));
                     var1.setline(289);
                     var3 = var1.getglobal("None");
                     var1.getlocal(0).__setattr__("exec_prefix", var3);
                     var3 = null;
                  }
               }

               var1.setline(299);
               var1.getlocal(0).__getattr__("dump_dirs").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("pre-finalize_{unix,other}"));
               var1.setline(301);
               var3 = var1.getglobal("os").__getattr__("name");
               var10000 = var3._eq(PyString.fromInterned("posix"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(302);
                  var1.getlocal(0).__getattr__("finalize_unix").__call__(var2);
               } else {
                  var1.setline(304);
                  var1.getlocal(0).__getattr__("finalize_other").__call__(var2);
               }

               var1.setline(306);
               var1.getlocal(0).__getattr__("dump_dirs").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("post-finalize_{unix,other}()"));
               var1.setline(313);
               var3 = var1.getglobal("string").__getattr__("split").__call__(var2, var1.getglobal("sys").__getattr__("version")).__getitem__(Py.newInteger(0));
               var1.setlocal(1, var3);
               var3 = null;
               var1.setline(314);
               var3 = var1.getglobal("get_config_vars").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prefix"), (PyObject)PyString.fromInterned("exec_prefix"));
               PyObject[] var4 = Py.unpackSequence(var3, 2);
               PyObject var5 = var4[0];
               var1.setlocal(2, var5);
               var5 = null;
               var5 = var4[1];
               var1.setlocal(3, var5);
               var5 = null;
               var3 = null;
               var1.setline(315);
               PyDictionary var7 = new PyDictionary(new PyObject[]{PyString.fromInterned("dist_name"), var1.getlocal(0).__getattr__("distribution").__getattr__("get_name").__call__(var2), PyString.fromInterned("dist_version"), var1.getlocal(0).__getattr__("distribution").__getattr__("get_version").__call__(var2), PyString.fromInterned("dist_fullname"), var1.getlocal(0).__getattr__("distribution").__getattr__("get_fullname").__call__(var2), PyString.fromInterned("py_version"), var1.getlocal(1), PyString.fromInterned("py_version_short"), var1.getlocal(1).__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null), PyString.fromInterned("py_version_nodot"), var1.getlocal(1).__getitem__(Py.newInteger(0))._add(var1.getlocal(1).__getitem__(Py.newInteger(2))), PyString.fromInterned("sys_prefix"), var1.getlocal(2), PyString.fromInterned("prefix"), var1.getlocal(2), PyString.fromInterned("sys_exec_prefix"), var1.getlocal(3), PyString.fromInterned("exec_prefix"), var1.getlocal(3), PyString.fromInterned("userbase"), var1.getlocal(0).__getattr__("install_userbase"), PyString.fromInterned("usersite"), var1.getlocal(0).__getattr__("install_usersite")});
               var1.getlocal(0).__setattr__((String)"config_vars", var7);
               var3 = null;
               var1.setline(328);
               var1.getlocal(0).__getattr__("expand_basedirs").__call__(var2);
               var1.setline(330);
               var1.getlocal(0).__getattr__("dump_dirs").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("post-expand_basedirs()"));
               var1.setline(334);
               var3 = var1.getlocal(0).__getattr__("install_base");
               var1.getlocal(0).__getattr__("config_vars").__setitem__((PyObject)PyString.fromInterned("base"), var3);
               var3 = null;
               var1.setline(335);
               var3 = var1.getlocal(0).__getattr__("install_platbase");
               var1.getlocal(0).__getattr__("config_vars").__setitem__((PyObject)PyString.fromInterned("platbase"), var3);
               var3 = null;
               var1.setline(337);
               PyObject[] var9;
               if (var1.getglobal("DEBUG").__nonzero__()) {
                  var1.setline(338);
                  String[] var8 = new String[]{"pprint"};
                  var9 = imp.importFrom("pprint", var8, var1, -1);
                  PyObject var6 = var9[0];
                  var1.setlocal(4, var6);
                  var4 = null;
                  var1.setline(339);
                  Py.println(PyString.fromInterned("config vars:"));
                  var1.setline(340);
                  var1.getlocal(4).__call__(var2, var1.getlocal(0).__getattr__("config_vars"));
               }

               var1.setline(344);
               var1.getlocal(0).__getattr__("expand_dirs").__call__(var2);
               var1.setline(346);
               var1.getlocal(0).__getattr__("dump_dirs").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("post-expand_dirs()"));
               var1.setline(349);
               if (var1.getlocal(0).__getattr__("user").__nonzero__()) {
                  var1.setline(350);
                  var1.getlocal(0).__getattr__("create_home_path").__call__(var2);
               }

               var1.setline(356);
               var3 = var1.getlocal(0).__getattr__("install_lib");
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(357);
                  if (var1.getlocal(0).__getattr__("distribution").__getattr__("ext_modules").__nonzero__()) {
                     var1.setline(358);
                     var3 = var1.getlocal(0).__getattr__("install_platlib");
                     var1.getlocal(0).__setattr__("install_lib", var3);
                     var3 = null;
                  } else {
                     var1.setline(360);
                     var3 = var1.getlocal(0).__getattr__("install_purelib");
                     var1.getlocal(0).__setattr__("install_lib", var3);
                     var3 = null;
                  }
               }

               var1.setline(365);
               var10000 = var1.getlocal(0).__getattr__("convert_paths");
               var9 = new PyObject[]{PyString.fromInterned("lib"), PyString.fromInterned("purelib"), PyString.fromInterned("platlib"), PyString.fromInterned("scripts"), PyString.fromInterned("data"), PyString.fromInterned("headers"), PyString.fromInterned("userbase"), PyString.fromInterned("usersite")};
               var10000.__call__(var2, var9);
               var1.setline(373);
               var1.getlocal(0).__getattr__("handle_extra_path").__call__(var2);
               var1.setline(374);
               var3 = var1.getlocal(0).__getattr__("install_lib");
               var1.getlocal(0).__setattr__("install_libbase", var3);
               var3 = null;
               var1.setline(375);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("install_lib"), var1.getlocal(0).__getattr__("extra_dirs"));
               var1.getlocal(0).__setattr__("install_lib", var3);
               var3 = null;
               var1.setline(379);
               var3 = var1.getlocal(0).__getattr__("root");
               var10000 = var3._isnot(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(380);
                  var10000 = var1.getlocal(0).__getattr__("change_roots");
                  var9 = new PyObject[]{PyString.fromInterned("libbase"), PyString.fromInterned("lib"), PyString.fromInterned("purelib"), PyString.fromInterned("platlib"), PyString.fromInterned("scripts"), PyString.fromInterned("data"), PyString.fromInterned("headers")};
                  var10000.__call__(var2, var9);
               }

               var1.setline(383);
               var1.getlocal(0).__getattr__("dump_dirs").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("after prepending root"));
               var1.setline(386);
               var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, PyString.fromInterned("build"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("build_base"), PyString.fromInterned("build_base")})), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("build_lib"), PyString.fromInterned("build_lib")})));
               var1.f_lasti = -1;
               return Py.None;
            }
         }
      }
   }

   public PyObject dump_dirs$4(PyFrame var1, ThreadState var2) {
      var1.setline(397);
      if (var1.getglobal("DEBUG").__nonzero__()) {
         var1.setline(398);
         String[] var3 = new String[]{"longopt_xlate"};
         PyObject[] var6 = imp.importFrom("distutils.fancy_getopt", var3, var1, -1);
         PyObject var4 = var6[0];
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(399);
         Py.println(var1.getlocal(1)._add(PyString.fromInterned(":")));
         var1.setline(400);
         PyObject var7 = var1.getlocal(0).__getattr__("user_options").__iter__();

         while(true) {
            var1.setline(400);
            var4 = var7.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(401);
            PyObject var5 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(402);
            var5 = var1.getlocal(4).__getitem__(Py.newInteger(-1));
            PyObject var10000 = var5._eq(PyString.fromInterned("="));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(403);
               var5 = var1.getlocal(4).__getslice__(Py.newInteger(0), Py.newInteger(-1), (PyObject)null);
               var1.setlocal(4, var5);
               var5 = null;
            }

            var1.setline(404);
            var5 = var1.getlocal(4);
            var10000 = var5._in(var1.getlocal(0).__getattr__("negative_opt"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(405);
               var5 = var1.getglobal("string").__getattr__("translate").__call__(var2, var1.getlocal(0).__getattr__("negative_opt").__getitem__(var1.getlocal(4)), var1.getlocal(2));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(407);
               var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(4)).__not__();
               var1.setlocal(5, var5);
               var5 = null;
            } else {
               var1.setline(409);
               var5 = var1.getglobal("string").__getattr__("translate").__call__(var2, var1.getlocal(4), var1.getlocal(2));
               var1.setlocal(4, var5);
               var5 = null;
               var1.setline(410);
               var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(4));
               var1.setlocal(5, var5);
               var5 = null;
            }

            var1.setline(411);
            Py.println(PyString.fromInterned("  %s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)})));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_unix$5(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyObject var3 = var1.getlocal(0).__getattr__("install_base");
      PyObject var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("install_platbase");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(417);
         var3 = var1.getlocal(0).__getattr__("install_lib");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("install_purelib");
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getattr__("install_platlib");
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
            }
         }

         if (!var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("install_headers");
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getattr__("install_scripts");
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (!var10000.__nonzero__()) {
                  var3 = var1.getlocal(0).__getattr__("install_data");
                  var10000 = var3._is(var1.getglobal("None"));
                  var3 = null;
               }
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(423);
            throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("install-base or install-platbase supplied, but installation scheme is incomplete"));
         } else {
            var1.setline(426);
            var1.f_lasti = -1;
            return Py.None;
         }
      } else {
         var1.setline(428);
         if (var1.getlocal(0).__getattr__("user").__nonzero__()) {
            var1.setline(429);
            var3 = var1.getlocal(0).__getattr__("install_userbase");
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(430);
               throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("User base directory is not specified")));
            }

            var1.setline(432);
            var3 = var1.getlocal(0).__getattr__("install_userbase");
            var1.getlocal(0).__setattr__("install_base", var3);
            var1.getlocal(0).__setattr__("install_platbase", var3);
            var1.setline(433);
            var1.getlocal(0).__getattr__("select_scheme").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unix_user"));
         } else {
            var1.setline(434);
            var3 = var1.getlocal(0).__getattr__("home");
            var10000 = var3._isnot(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(435);
               var3 = var1.getlocal(0).__getattr__("home");
               var1.getlocal(0).__setattr__("install_base", var3);
               var1.getlocal(0).__setattr__("install_platbase", var3);
               var1.setline(436);
               var1.getlocal(0).__getattr__("select_scheme").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unix_home"));
            } else {
               var1.setline(438);
               var3 = var1.getlocal(0).__getattr__("prefix");
               var10000 = var3._is(var1.getglobal("None"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(439);
                  var3 = var1.getlocal(0).__getattr__("exec_prefix");
                  var10000 = var3._isnot(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(440);
                     throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("must not supply exec-prefix without prefix"));
                  }

                  var1.setline(443);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getglobal("sys").__getattr__("prefix"));
                  var1.getlocal(0).__setattr__("prefix", var3);
                  var3 = null;
                  var1.setline(444);
                  var3 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getglobal("sys").__getattr__("exec_prefix"));
                  var1.getlocal(0).__setattr__("exec_prefix", var3);
                  var3 = null;
               } else {
                  var1.setline(447);
                  var3 = var1.getlocal(0).__getattr__("exec_prefix");
                  var10000 = var3._is(var1.getglobal("None"));
                  var3 = null;
                  if (var10000.__nonzero__()) {
                     var1.setline(448);
                     var3 = var1.getlocal(0).__getattr__("prefix");
                     var1.getlocal(0).__setattr__("exec_prefix", var3);
                     var3 = null;
                  }
               }

               var1.setline(450);
               var3 = var1.getlocal(0).__getattr__("prefix");
               var1.getlocal(0).__setattr__("install_base", var3);
               var3 = null;
               var1.setline(451);
               var3 = var1.getlocal(0).__getattr__("exec_prefix");
               var1.getlocal(0).__setattr__("install_platbase", var3);
               var3 = null;
               var1.setline(452);
               var1.getlocal(0).__getattr__("select_scheme").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unix_prefix"));
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject finalize_other$6(PyFrame var1, ThreadState var2) {
      var1.setline(459);
      PyObject var10000;
      PyObject var3;
      if (var1.getlocal(0).__getattr__("user").__nonzero__()) {
         var1.setline(460);
         var3 = var1.getlocal(0).__getattr__("install_userbase");
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(461);
            throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("User base directory is not specified")));
         }

         var1.setline(463);
         var3 = var1.getlocal(0).__getattr__("install_userbase");
         var1.getlocal(0).__setattr__("install_base", var3);
         var1.getlocal(0).__setattr__("install_platbase", var3);
         var1.setline(464);
         var1.getlocal(0).__getattr__("select_scheme").__call__(var2, var1.getglobal("os").__getattr__("name")._add(PyString.fromInterned("_user")));
      } else {
         var1.setline(465);
         var3 = var1.getlocal(0).__getattr__("home");
         var10000 = var3._isnot(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(466);
            var3 = var1.getlocal(0).__getattr__("home");
            var1.getlocal(0).__setattr__("install_base", var3);
            var1.getlocal(0).__setattr__("install_platbase", var3);
            var1.setline(467);
            var1.getlocal(0).__getattr__("select_scheme").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unix_home"));
         } else {
            var1.setline(469);
            var3 = var1.getlocal(0).__getattr__("prefix");
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(470);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getglobal("sys").__getattr__("prefix"));
               var1.getlocal(0).__setattr__("prefix", var3);
               var3 = null;
            }

            var1.setline(472);
            var3 = var1.getlocal(0).__getattr__("prefix");
            var1.getlocal(0).__setattr__("install_base", var3);
            var1.getlocal(0).__setattr__("install_platbase", var3);

            try {
               var1.setline(474);
               var1.getlocal(0).__getattr__("select_scheme").__call__(var2, var1.getglobal("os").__getattr__("name"));
            } catch (Throwable var4) {
               PyException var5 = Py.setException(var4, var1);
               if (var5.match(var1.getglobal("KeyError"))) {
                  var1.setline(476);
                  throw Py.makeException(var1.getglobal("DistutilsPlatformError"), PyString.fromInterned("I don't know how to install stuff on '%s'")._mod(var1.getglobal("os").__getattr__("name")));
               }

               throw var5;
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject select_scheme$7(PyFrame var1, ThreadState var2) {
      var1.setline(484);
      PyObject var3 = var1.getglobal("INSTALL_SCHEMES").__getitem__(var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(485);
      var3 = var1.getglobal("SCHEME_KEYS").__iter__();

      while(true) {
         var1.setline(485);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var4);
         var1.setline(486);
         PyObject var5 = PyString.fromInterned("install_")._add(var1.getlocal(3));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(487);
         var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(4));
         PyObject var10000 = var5._is(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(488);
            var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(4), var1.getlocal(2).__getitem__(var1.getlocal(3)));
         }
      }
   }

   public PyObject _expand_attrs$8(PyFrame var1, ThreadState var2) {
      var1.setline(492);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(492);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(493);
         PyObject var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(494);
         var5 = var1.getlocal(3);
         PyObject var10000 = var5._isnot(var1.getglobal("None"));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(495);
            var5 = var1.getglobal("os").__getattr__("name");
            var10000 = var5._eq(PyString.fromInterned("posix"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var5 = var1.getglobal("os").__getattr__("name");
               var10000 = var5._eq(PyString.fromInterned("nt"));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(496);
               var5 = var1.getglobal("os").__getattr__("path").__getattr__("expanduser").__call__(var2, var1.getlocal(3));
               var1.setlocal(3, var5);
               var5 = null;
            }

            var1.setline(497);
            var5 = var1.getglobal("subst_vars").__call__(var2, var1.getlocal(3), var1.getlocal(0).__getattr__("config_vars"));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(498);
            var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(3));
         }
      }
   }

   public PyObject expand_basedirs$9(PyFrame var1, ThreadState var2) {
      var1.setline(502);
      var1.getlocal(0).__getattr__("_expand_attrs").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("install_base"), PyString.fromInterned("install_platbase"), PyString.fromInterned("root")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject expand_dirs$10(PyFrame var1, ThreadState var2) {
      var1.setline(507);
      var1.getlocal(0).__getattr__("_expand_attrs").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("install_purelib"), PyString.fromInterned("install_platlib"), PyString.fromInterned("install_lib"), PyString.fromInterned("install_headers"), PyString.fromInterned("install_scripts"), PyString.fromInterned("install_data")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject convert_paths$11(PyFrame var1, ThreadState var2) {
      var1.setline(516);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(516);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(517);
         PyObject var5 = PyString.fromInterned("install_")._add(var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(518);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(3), var1.getglobal("convert_path").__call__(var2, var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(3))));
      }
   }

   public PyObject handle_extra_path$12(PyFrame var1, ThreadState var2) {
      var1.setline(523);
      PyObject var3 = var1.getlocal(0).__getattr__("extra_path");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(524);
         var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("extra_path");
         var1.getlocal(0).__setattr__("extra_path", var3);
         var3 = null;
      }

      var1.setline(526);
      var3 = var1.getlocal(0).__getattr__("extra_path");
      var10000 = var3._isnot(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(527);
         var3 = var1.getglobal("type").__call__(var2, var1.getlocal(0).__getattr__("extra_path"));
         var10000 = var3._is(var1.getglobal("StringType"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(528);
            var3 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("extra_path"), (PyObject)PyString.fromInterned(","));
            var1.getlocal(0).__setattr__("extra_path", var3);
            var3 = null;
         }

         var1.setline(530);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("extra_path"));
         var10000 = var3._eq(Py.newInteger(1));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(531);
            var3 = var1.getlocal(0).__getattr__("extra_path").__getitem__(Py.newInteger(0));
            var1.setlocal(1, var3);
            var1.setlocal(2, var3);
         } else {
            var1.setline(532);
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("extra_path"));
            var10000 = var3._eq(Py.newInteger(2));
            var3 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(535);
               throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("'extra_path' option must be a list, tuple, or comma-separated string with 1 or 2 elements"));
            }

            var1.setline(533);
            var3 = var1.getlocal(0).__getattr__("extra_path");
            PyObject[] var4 = Py.unpackSequence(var3, 2);
            PyObject var5 = var4[0];
            var1.setlocal(1, var5);
            var5 = null;
            var5 = var4[1];
            var1.setlocal(2, var5);
            var5 = null;
            var3 = null;
         }

         var1.setline(541);
         var3 = var1.getglobal("convert_path").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(544);
         var3 = var1.getglobal("None");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(545);
         PyString var6 = PyString.fromInterned("");
         var1.setlocal(2, var6);
         var3 = null;
      }

      var1.setline(549);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("path_file", var3);
      var3 = null;
      var1.setline(550);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("extra_dirs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject change_roots$13(PyFrame var1, ThreadState var2) {
      var1.setline(556);
      PyObject var3 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(556);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(557);
         PyObject var5 = PyString.fromInterned("install_")._add(var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(558);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(3), var1.getglobal("change_root").__call__(var2, var1.getlocal(0).__getattr__("root"), var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(3))));
      }
   }

   public PyObject create_home_path$14(PyFrame var1, ThreadState var2) {
      var1.setline(562);
      PyString.fromInterned("Create directories under ~\n        ");
      var1.setline(563);
      if (var1.getlocal(0).__getattr__("user").__not__().__nonzero__()) {
         var1.setline(564);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(565);
         PyObject var3 = var1.getglobal("convert_path").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("expanduser").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("~")));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(566);
         var3 = var1.getlocal(0).__getattr__("config_vars").__getattr__("iteritems").__call__(var2).__iter__();

         while(true) {
            var1.setline(566);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 2);
            PyObject var6 = var5[0];
            var1.setlocal(2, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(567);
            PyObject var10000 = var1.getlocal(3).__getattr__("startswith").__call__(var2, var1.getlocal(1));
            if (var10000.__nonzero__()) {
               var10000 = var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(3)).__not__();
            }

            if (var10000.__nonzero__()) {
               var1.setline(568);
               var1.getlocal(0).__getattr__("debug_print").__call__(var2, PyString.fromInterned("os.makedirs('%s', 0700)")._mod(var1.getlocal(3)));
               var1.setline(569);
               var1.getglobal("os").__getattr__("makedirs").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)Py.newInteger(448));
            }
         }
      }
   }

   public PyObject run$15(PyFrame var1, ThreadState var2) {
      var1.setline(576);
      PyObject var10000;
      PyObject var3;
      if (var1.getlocal(0).__getattr__("skip_build").__not__().__nonzero__()) {
         var1.setline(577);
         var1.getlocal(0).__getattr__("run_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build"));
         var1.setline(579);
         var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_command_obj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build")).__getattr__("plat_name");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(583);
         var10000 = var1.getlocal(0).__getattr__("warn_dir");
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(1);
            var10000 = var3._ne(var1.getglobal("get_platform").__call__(var2));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(584);
            throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Can't install when cross-compiling")));
         }
      }

      var1.setline(588);
      var3 = var1.getlocal(0).__getattr__("get_sub_commands").__call__(var2).__iter__();

      while(true) {
         var1.setline(588);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(591);
            if (var1.getlocal(0).__getattr__("path_file").__nonzero__()) {
               var1.setline(592);
               var1.getlocal(0).__getattr__("create_path_file").__call__(var2);
            }

            var1.setline(595);
            if (var1.getlocal(0).__getattr__("record").__nonzero__()) {
               var1.setline(596);
               var3 = var1.getlocal(0).__getattr__("get_outputs").__call__(var2);
               var1.setlocal(3, var3);
               var3 = null;
               var1.setline(597);
               if (var1.getlocal(0).__getattr__("root").__nonzero__()) {
                  var1.setline(598);
                  var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("root"));
                  var1.setlocal(4, var3);
                  var3 = null;
                  var1.setline(599);
                  var3 = var1.getglobal("xrange").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(3))).__iter__();

                  while(true) {
                     var1.setline(599);
                     var4 = var3.__iternext__();
                     if (var4 == null) {
                        break;
                     }

                     var1.setlocal(5, var4);
                     var1.setline(600);
                     PyObject var5 = var1.getlocal(3).__getitem__(var1.getlocal(5)).__getslice__(var1.getlocal(4), (PyObject)null, (PyObject)null);
                     var1.getlocal(3).__setitem__(var1.getlocal(5), var5);
                     var5 = null;
                  }
               }

               var1.setline(601);
               var1.getlocal(0).__getattr__("execute").__call__((ThreadState)var2, var1.getglobal("write_file"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("record"), var1.getlocal(3)})), (PyObject)PyString.fromInterned("writing list of installed files to '%s'")._mod(var1.getlocal(0).__getattr__("record")));
            }

            var1.setline(606);
            var3 = var1.getglobal("map").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normpath"), var1.getglobal("sys").__getattr__("path"));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(607);
            var3 = var1.getglobal("map").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normcase"), var1.getlocal(6));
            var1.setlocal(6, var3);
            var3 = null;
            var1.setline(608);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("normcase").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(0).__getattr__("install_lib")));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(609);
            var10000 = var1.getlocal(0).__getattr__("warn_dir");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("path_file");
               if (var10000.__nonzero__()) {
                  var10000 = var1.getlocal(0).__getattr__("install_path_file");
               }

               var10000 = var10000.__not__();
               if (var10000.__nonzero__()) {
                  var3 = var1.getlocal(7);
                  var10000 = var3._notin(var1.getlocal(6));
                  var3 = null;
               }
            }

            if (var10000.__nonzero__()) {
               var1.setline(612);
               var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("modules installed to '%s', which is not in Python's module search path (sys.path) -- you'll have to change the search path yourself"), (PyObject)var1.getlocal(0).__getattr__("install_lib"));
            }

            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(589);
         var1.getlocal(0).__getattr__("run_command").__call__(var2, var1.getlocal(2));
      }
   }

   public PyObject create_path_file$16(PyFrame var1, ThreadState var2) {
      var1.setline(620);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("install_libbase"), var1.getlocal(0).__getattr__("path_file")._add(PyString.fromInterned(".pth")));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(622);
      if (var1.getlocal(0).__getattr__("install_path_file").__nonzero__()) {
         var1.setline(623);
         var1.getlocal(0).__getattr__("execute").__call__((ThreadState)var2, var1.getglobal("write_file"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), new PyList(new PyObject[]{var1.getlocal(0).__getattr__("extra_dirs")})})), (PyObject)PyString.fromInterned("creating %s")._mod(var1.getlocal(1)));
      } else {
         var1.setline(627);
         var1.getlocal(0).__getattr__("warn").__call__(var2, PyString.fromInterned("path file '%s' not created")._mod(var1.getlocal(1)));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_outputs$17(PyFrame var1, ThreadState var2) {
      var1.setline(634);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(635);
      PyObject var8 = var1.getlocal(0).__getattr__("get_sub_commands").__call__(var2).__iter__();

      while(true) {
         var1.setline(635);
         PyObject var4 = var8.__iternext__();
         PyObject var10000;
         if (var4 == null) {
            var1.setline(643);
            var10000 = var1.getlocal(0).__getattr__("path_file");
            if (var10000.__nonzero__()) {
               var10000 = var1.getlocal(0).__getattr__("install_path_file");
            }

            if (var10000.__nonzero__()) {
               var1.setline(644);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("install_libbase"), var1.getlocal(0).__getattr__("path_file")._add(PyString.fromInterned(".pth"))));
            }

            var1.setline(647);
            var8 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var8;
         }

         var1.setlocal(2, var4);
         var1.setline(636);
         PyObject var5 = var1.getlocal(0).__getattr__("get_finalized_command").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(639);
         var5 = var1.getlocal(3).__getattr__("get_outputs").__call__(var2).__iter__();

         while(true) {
            var1.setline(639);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal(4, var6);
            var1.setline(640);
            PyObject var7 = var1.getlocal(4);
            var10000 = var7._notin(var1.getlocal(1));
            var7 = null;
            if (var10000.__nonzero__()) {
               var1.setline(641);
               var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(4));
            }
         }
      }
   }

   public PyObject get_inputs$18(PyFrame var1, ThreadState var2) {
      var1.setline(651);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(652);
      PyObject var6 = var1.getlocal(0).__getattr__("get_sub_commands").__call__(var2).__iter__();

      while(true) {
         var1.setline(652);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(656);
            var6 = var1.getlocal(1);
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(2, var4);
         var1.setline(653);
         PyObject var5 = var1.getlocal(0).__getattr__("get_finalized_command").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(654);
         var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(3).__getattr__("get_inputs").__call__(var2));
      }
   }

   public PyObject has_lib$19(PyFrame var1, ThreadState var2) {
      var1.setline(663);
      PyString.fromInterned("Return true if the current distribution has any Python\n        modules to install.");
      var1.setline(664);
      PyObject var10000 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_pure_modules").__call__(var2);
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2);
      }

      PyObject var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_headers$20(PyFrame var1, ThreadState var2) {
      var1.setline(668);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_headers").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_scripts$21(PyFrame var1, ThreadState var2) {
      var1.setline(671);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_scripts").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_data$22(PyFrame var1, ThreadState var2) {
      var1.setline(674);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_data_files").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$23(PyFrame var1, ThreadState var2) {
      var1.setline(683);
      PyObject var3 = var1.getglobal("True");
      var1.f_lasti = -1;
      return var3;
   }

   public install$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      install$1 = Py.newCode(0, var2, var1, "install", 108, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 177, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "py_version", "prefix", "exec_prefix", "pprint"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 254, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "msg", "longopt_xlate", "opt", "opt_name", "val"};
      dump_dirs$4 = Py.newCode(2, var2, var1, "dump_dirs", 396, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_unix$5 = Py.newCode(1, var2, var1, "finalize_unix", 414, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_other$6 = Py.newCode(1, var2, var1, "finalize_other", 457, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "scheme", "key", "attrname"};
      select_scheme$7 = Py.newCode(2, var2, var1, "select_scheme", 482, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "attr", "val"};
      _expand_attrs$8 = Py.newCode(2, var2, var1, "_expand_attrs", 491, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      expand_basedirs$9 = Py.newCode(1, var2, var1, "expand_basedirs", 501, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      expand_dirs$10 = Py.newCode(1, var2, var1, "expand_dirs", 506, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "names", "name", "attr"};
      convert_paths$11 = Py.newCode(2, var2, var1, "convert_paths", 515, true, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path_file", "extra_dirs"};
      handle_extra_path$12 = Py.newCode(1, var2, var1, "handle_extra_path", 521, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "names", "name", "attr"};
      change_roots$13 = Py.newCode(2, var2, var1, "change_roots", 555, true, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "home", "name", "path"};
      create_home_path$14 = Py.newCode(1, var2, var1, "create_home_path", 560, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "build_plat", "cmd_name", "outputs", "root_len", "counter", "sys_path", "install_lib"};
      run$15 = Py.newCode(1, var2, var1, "run", 573, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename"};
      create_path_file$16 = Py.newCode(1, var2, var1, "create_path_file", 619, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "outputs", "cmd_name", "cmd", "filename"};
      get_outputs$17 = Py.newCode(1, var2, var1, "get_outputs", 632, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "inputs", "cmd_name", "cmd"};
      get_inputs$18 = Py.newCode(1, var2, var1, "get_inputs", 649, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_lib$19 = Py.newCode(1, var2, var1, "has_lib", 661, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_headers$20 = Py.newCode(1, var2, var1, "has_headers", 667, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_scripts$21 = Py.newCode(1, var2, var1, "has_scripts", 670, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_data$22 = Py.newCode(1, var2, var1, "has_data", 673, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      f$23 = Py.newCode(1, var2, var1, "<lambda>", 683, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new install$py("distutils/command/install$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(install$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.install$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.finalize_options$3(var2, var3);
         case 4:
            return this.dump_dirs$4(var2, var3);
         case 5:
            return this.finalize_unix$5(var2, var3);
         case 6:
            return this.finalize_other$6(var2, var3);
         case 7:
            return this.select_scheme$7(var2, var3);
         case 8:
            return this._expand_attrs$8(var2, var3);
         case 9:
            return this.expand_basedirs$9(var2, var3);
         case 10:
            return this.expand_dirs$10(var2, var3);
         case 11:
            return this.convert_paths$11(var2, var3);
         case 12:
            return this.handle_extra_path$12(var2, var3);
         case 13:
            return this.change_roots$13(var2, var3);
         case 14:
            return this.create_home_path$14(var2, var3);
         case 15:
            return this.run$15(var2, var3);
         case 16:
            return this.create_path_file$16(var2, var3);
         case 17:
            return this.get_outputs$17(var2, var3);
         case 18:
            return this.get_inputs$18(var2, var3);
         case 19:
            return this.has_lib$19(var2, var3);
         case 20:
            return this.has_headers$20(var2, var3);
         case 21:
            return this.has_scripts$21(var2, var3);
         case 22:
            return this.has_data$22(var2, var3);
         case 23:
            return this.f$23(var2, var3);
         default:
            return null;
      }
   }
}
