package distutils.command;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyFloat;
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
@Filename("distutils/command/bdist_wininst.py")
public class bdist_wininst$py extends PyFunctionTable implements PyRunnable {
   static bdist_wininst$py self;
   static final PyCode f$0;
   static final PyCode bdist_wininst$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode run$4;
   static final PyCode get_inidata$5;
   static final PyCode escape$6;
   static final PyCode create_exe$7;
   static final PyCode get_installer_filename$8;
   static final PyCode get_exe_bytes$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.bdist_wininst\n\nImplements the Distutils 'bdist_wininst' command: create a windows installer\nexe-program."));
      var1.setline(4);
      PyString.fromInterned("distutils.command.bdist_wininst\n\nImplements the Distutils 'bdist_wininst' command: create a windows installer\nexe-program.");
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
      var5 = imp.importOne("string", var1, -1);
      var1.setlocal("string", var5);
      var3 = null;
      var1.setline(12);
      String[] var6 = new String[]{"get_python_version"};
      PyObject[] var7 = imp.importFrom("sysconfig", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("get_python_version", var4);
      var4 = null;
      var1.setline(14);
      var6 = new String[]{"Command"};
      var7 = imp.importFrom("distutils.core", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(15);
      var6 = new String[]{"remove_tree"};
      var7 = imp.importFrom("distutils.dir_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("remove_tree", var4);
      var4 = null;
      var1.setline(16);
      var6 = new String[]{"DistutilsOptionError", "DistutilsPlatformError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var1.setline(17);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(18);
      var6 = new String[]{"get_platform"};
      var7 = imp.importFrom("distutils.util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("get_platform", var4);
      var4 = null;
      var1.setline(20);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("bdist_wininst", var7, bdist_wininst$1);
      var1.setlocal("bdist_wininst", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject bdist_wininst$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(22);
      PyString var3 = PyString.fromInterned("create an executable installer for MS Windows");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(24);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("bdist-dir="), var1.getname("None"), PyString.fromInterned("temporary directory for creating the distribution")}), new PyTuple(new PyObject[]{PyString.fromInterned("plat-name="), PyString.fromInterned("p"), PyString.fromInterned("platform name to embed in generated filenames (default: %s)")._mod(var1.getname("get_platform").__call__(var2))}), new PyTuple(new PyObject[]{PyString.fromInterned("keep-temp"), PyString.fromInterned("k"), PyString.fromInterned("keep the pseudo-installation tree around after ")._add(PyString.fromInterned("creating the distribution archive"))}), new PyTuple(new PyObject[]{PyString.fromInterned("target-version="), var1.getname("None"), PyString.fromInterned("require a specific python version")._add(PyString.fromInterned(" on the target system"))}), new PyTuple(new PyObject[]{PyString.fromInterned("no-target-compile"), PyString.fromInterned("c"), PyString.fromInterned("do not compile .py to .pyc on the target system")}), new PyTuple(new PyObject[]{PyString.fromInterned("no-target-optimize"), PyString.fromInterned("o"), PyString.fromInterned("do not compile .py to .pyo (optimized)on the target system")}), new PyTuple(new PyObject[]{PyString.fromInterned("dist-dir="), PyString.fromInterned("d"), PyString.fromInterned("directory to put final built distributions in")}), new PyTuple(new PyObject[]{PyString.fromInterned("bitmap="), PyString.fromInterned("b"), PyString.fromInterned("bitmap to use for the installer instead of python-powered logo")}), new PyTuple(new PyObject[]{PyString.fromInterned("title="), PyString.fromInterned("t"), PyString.fromInterned("title to display on the installer background instead of default")}), new PyTuple(new PyObject[]{PyString.fromInterned("skip-build"), var1.getname("None"), PyString.fromInterned("skip rebuilding everything (for testing/debugging)")}), new PyTuple(new PyObject[]{PyString.fromInterned("install-script="), var1.getname("None"), PyString.fromInterned("basename of installation script to be run afterinstallation or before deinstallation")}), new PyTuple(new PyObject[]{PyString.fromInterned("pre-install-script="), var1.getname("None"), PyString.fromInterned("Fully qualified filename of a script to be run before any files are installed.  This script need not be in the distribution")}), new PyTuple(new PyObject[]{PyString.fromInterned("user-access-control="), var1.getname("None"), PyString.fromInterned("specify Vista's UAC handling - 'none'/default=no handling, 'auto'=use UAC if target Python installed for all users, 'force'=always use UAC")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(61);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("keep-temp"), PyString.fromInterned("no-target-compile"), PyString.fromInterned("no-target-optimize"), PyString.fromInterned("skip-build")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(64);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(82);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(122);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$4, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(205);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_inidata$5, (PyObject)null);
      var1.setlocal("get_inidata", var6);
      var3 = null;
      var1.setline(254);
      var5 = new PyObject[]{var1.getname("None")};
      var6 = new PyFunction(var1.f_globals, var5, create_exe$7, (PyObject)null);
      var1.setlocal("create_exe", var6);
      var3 = null;
      var1.setline(309);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_installer_filename$8, (PyObject)null);
      var1.setlocal("get_installer_filename", var6);
      var3 = null;
      var1.setline(323);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_exe_bytes$9, (PyObject)null);
      var1.setlocal("get_exe_bytes", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("bdist_dir", var3);
      var3 = null;
      var1.setline(66);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("plat_name", var3);
      var3 = null;
      var1.setline(67);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"keep_temp", var4);
      var3 = null;
      var1.setline(68);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"no_target_compile", var4);
      var3 = null;
      var1.setline(69);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"no_target_optimize", var4);
      var3 = null;
      var1.setline(70);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("target_version", var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("dist_dir", var3);
      var3 = null;
      var1.setline(72);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("bitmap", var3);
      var3 = null;
      var1.setline(73);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("title", var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("skip_build", var3);
      var3 = null;
      var1.setline(75);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_script", var3);
      var3 = null;
      var1.setline(76);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("pre_install_script", var3);
      var3 = null;
      var1.setline(77);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("user_access_control", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(83);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bdist"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("skip_build"), PyString.fromInterned("skip_build")})));
      var1.setline(85);
      PyObject var3 = var1.getlocal(0).__getattr__("bdist_dir");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(86);
         var10000 = var1.getlocal(0).__getattr__("skip_build");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("plat_name");
         }

         if (var10000.__nonzero__()) {
            var1.setline(89);
            var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_command_obj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bdist"));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(90);
            var3 = var1.getlocal(0).__getattr__("plat_name");
            var1.getlocal(1).__setattr__("plat_name", var3);
            var3 = null;
         }

         var1.setline(92);
         var3 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bdist")).__getattr__("bdist_base");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(93);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("wininst"));
         var1.getlocal(0).__setattr__("bdist_dir", var3);
         var3 = null;
      }

      var1.setline(95);
      if (var1.getlocal(0).__getattr__("target_version").__not__().__nonzero__()) {
         var1.setline(96);
         PyString var6 = PyString.fromInterned("");
         var1.getlocal(0).__setattr__((String)"target_version", var6);
         var3 = null;
      }

      var1.setline(98);
      var10000 = var1.getlocal(0).__getattr__("skip_build").__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2);
      }

      if (var10000.__nonzero__()) {
         var1.setline(99);
         var3 = var1.getglobal("get_python_version").__call__(var2);
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(100);
         var10000 = var1.getlocal(0).__getattr__("target_version");
         if (var10000.__nonzero__()) {
            var3 = var1.getlocal(0).__getattr__("target_version");
            var10000 = var3._ne(var1.getlocal(3));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(101);
            throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("target version can only be %s, or the '--skip-build' option must be specified")._mod(new PyTuple(new PyObject[]{var1.getlocal(3)})));
         }

         var1.setline(104);
         var3 = var1.getlocal(3);
         var1.getlocal(0).__setattr__("target_version", var3);
         var3 = null;
      }

      var1.setline(106);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, PyString.fromInterned("bdist"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("dist_dir"), PyString.fromInterned("dist_dir")})), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("plat_name"), PyString.fromInterned("plat_name")})));
      var1.setline(111);
      if (var1.getlocal(0).__getattr__("install_script").__nonzero__()) {
         var1.setline(112);
         var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("scripts").__iter__();

         do {
            var1.setline(112);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(116);
               throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("install_script '%s' not found in scripts")._mod(var1.getlocal(0).__getattr__("install_script")));
            }

            var1.setlocal(4, var4);
            var1.setline(113);
            PyObject var5 = var1.getlocal(0).__getattr__("install_script");
            var10000 = var5._eq(var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(4)));
            var5 = null;
         } while(!var10000.__nonzero__());
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$4(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._ne(PyString.fromInterned("win32"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2);
         if (!var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_c_libraries").__call__(var2);
         }
      }

      if (var10000.__nonzero__()) {
         var1.setline(126);
         throw Py.makeException(var1.getglobal("DistutilsPlatformError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("distribution contains extensions and/or C libraries; must be compiled on a Windows 32 platform")));
      } else {
         var1.setline(130);
         if (var1.getlocal(0).__getattr__("skip_build").__not__().__nonzero__()) {
            var1.setline(131);
            var1.getlocal(0).__getattr__("run_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build"));
         }

         var1.setline(133);
         var10000 = var1.getlocal(0).__getattr__("reinitialize_command");
         PyObject[] var6 = new PyObject[]{PyString.fromInterned("install"), Py.newInteger(1)};
         String[] var4 = new String[]{"reinit_subcommands"};
         var10000 = var10000.__call__(var2, var6, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(134);
         var3 = var1.getlocal(0).__getattr__("bdist_dir");
         var1.getlocal(1).__setattr__("root", var3);
         var3 = null;
         var1.setline(135);
         var3 = var1.getlocal(0).__getattr__("skip_build");
         var1.getlocal(1).__setattr__("skip_build", var3);
         var3 = null;
         var1.setline(136);
         PyInteger var8 = Py.newInteger(0);
         var1.getlocal(1).__setattr__((String)"warn_dir", var8);
         var3 = null;
         var1.setline(137);
         var3 = var1.getlocal(0).__getattr__("plat_name");
         var1.getlocal(1).__setattr__("plat_name", var3);
         var3 = null;
         var1.setline(139);
         var3 = var1.getlocal(0).__getattr__("reinitialize_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("install_lib"));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(141);
         var8 = Py.newInteger(0);
         var1.getlocal(2).__setattr__((String)"compile", var8);
         var3 = null;
         var1.setline(142);
         var8 = Py.newInteger(0);
         var1.getlocal(2).__setattr__((String)"optimize", var8);
         var3 = null;
         var1.setline(144);
         if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2).__nonzero__()) {
            var1.setline(151);
            var3 = var1.getlocal(0).__getattr__("target_version");
            var1.setlocal(3, var3);
            var3 = null;
            var1.setline(152);
            if (var1.getlocal(3).__not__().__nonzero__()) {
               var1.setline(153);
               if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(0).__getattr__("skip_build").__nonzero__()) {
                  throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Should have already checked this"));
               }

               var1.setline(154);
               var3 = var1.getglobal("sys").__getattr__("version").__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null);
               var1.setlocal(3, var3);
               var3 = null;
            }

            var1.setline(155);
            var3 = PyString.fromInterned(".%s-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("plat_name"), var1.getlocal(3)}));
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(156);
            var3 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build"));
            var1.setlocal(5, var3);
            var3 = null;
            var1.setline(157);
            var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5).__getattr__("build_base"), PyString.fromInterned("lib")._add(var1.getlocal(4)));
            var1.getlocal(5).__setattr__("build_lib", var3);
            var3 = null;
         }

         var1.setline(162);
         var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("purelib"), PyString.fromInterned("platlib"), PyString.fromInterned("headers"), PyString.fromInterned("scripts"), PyString.fromInterned("data")})).__iter__();

         while(true) {
            var1.setline(162);
            PyObject var7 = var3.__iternext__();
            if (var7 == null) {
               var1.setline(170);
               var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("installing to %s"), (PyObject)var1.getlocal(0).__getattr__("bdist_dir"));
               var1.setline(171);
               var1.getlocal(1).__getattr__("ensure_finalized").__call__(var2);
               var1.setline(175);
               var1.getglobal("sys").__getattr__("path").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("bdist_dir"), (PyObject)PyString.fromInterned("PURELIB")));
               var1.setline(177);
               var1.getlocal(1).__getattr__("run").__call__(var2);
               var1.setline(179);
               var1.getglobal("sys").__getattr__("path").__delitem__((PyObject)Py.newInteger(0));
               var1.setline(183);
               String[] var9 = new String[]{"mktemp"};
               var6 = imp.importFrom("tempfile", var9, var1, -1);
               var7 = var6[0];
               var1.setlocal(8, var7);
               var4 = null;
               var1.setline(184);
               var3 = var1.getlocal(8).__call__(var2);
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(185);
               var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_fullname").__call__(var2);
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(186);
               var10000 = var1.getlocal(0).__getattr__("make_archive");
               var6 = new PyObject[]{var1.getlocal(9), PyString.fromInterned("zip"), var1.getlocal(0).__getattr__("bdist_dir")};
               var4 = new String[]{"root_dir"};
               var10000 = var10000.__call__(var2, var6, var4);
               var3 = null;
               var3 = var10000;
               var1.setlocal(11, var3);
               var3 = null;
               var1.setline(189);
               var1.getlocal(0).__getattr__("create_exe").__call__(var2, var1.getlocal(11), var1.getlocal(10), var1.getlocal(0).__getattr__("bitmap"));
               var1.setline(190);
               if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2).__nonzero__()) {
                  var1.setline(191);
                  var3 = var1.getglobal("get_python_version").__call__(var2);
                  var1.setlocal(12, var3);
                  var3 = null;
               } else {
                  var1.setline(193);
                  PyString var10 = PyString.fromInterned("any");
                  var1.setlocal(12, var10);
                  var3 = null;
               }

               var1.setline(194);
               var1.getlocal(0).__getattr__("distribution").__getattr__("dist_files").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("bdist_wininst"), var1.getlocal(12), var1.getlocal(0).__getattr__("get_installer_filename").__call__(var2, var1.getlocal(10))})));
               var1.setline(197);
               var1.getglobal("log").__getattr__("debug").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("removing temporary file '%s'"), (PyObject)var1.getlocal(11));
               var1.setline(198);
               var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(11));
               var1.setline(200);
               if (var1.getlocal(0).__getattr__("keep_temp").__not__().__nonzero__()) {
                  var1.setline(201);
                  var10000 = var1.getglobal("remove_tree");
                  var6 = new PyObject[]{var1.getlocal(0).__getattr__("bdist_dir"), var1.getlocal(0).__getattr__("dry_run")};
                  var4 = new String[]{"dry_run"};
                  var10000.__call__(var2, var6, var4);
                  var3 = null;
               }

               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(6, var7);
            var1.setline(163);
            PyObject var5 = var1.getglobal("string").__getattr__("upper").__call__(var2, var1.getlocal(6));
            var1.setlocal(7, var5);
            var5 = null;
            var1.setline(164);
            var5 = var1.getlocal(6);
            var10000 = var5._eq(PyString.fromInterned("headers"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(165);
               var5 = var1.getlocal(7)._add(PyString.fromInterned("/Include/$dist_name"));
               var1.setlocal(7, var5);
               var5 = null;
            }

            var1.setline(166);
            var1.getglobal("setattr").__call__(var2, var1.getlocal(1), PyString.fromInterned("install_")._add(var1.getlocal(6)), var1.getlocal(7));
         }
      }
   }

   public PyObject get_inidata$5(PyFrame var1, ThreadState var2) {
      var1.setline(208);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(209);
      PyObject var6 = var1.getlocal(0).__getattr__("distribution").__getattr__("metadata");
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(212);
      var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[metadata]"));
      var1.setline(216);
      Object var10000 = var1.getlocal(2).__getattr__("long_description");
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("");
      }

      var6 = ((PyObject)var10000)._add(PyString.fromInterned("\n"));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(219);
      PyObject[] var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, escape$6, (PyObject)null);
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(222);
      var6 = (new PyList(new PyObject[]{PyString.fromInterned("author"), PyString.fromInterned("author_email"), PyString.fromInterned("description"), PyString.fromInterned("maintainer"), PyString.fromInterned("maintainer_email"), PyString.fromInterned("name"), PyString.fromInterned("url"), PyString.fromInterned("version")})).__iter__();

      while(true) {
         var1.setline(222);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(232);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n[Setup]"));
            var1.setline(233);
            if (var1.getlocal(0).__getattr__("install_script").__nonzero__()) {
               var1.setline(234);
               var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("install_script=%s")._mod(var1.getlocal(0).__getattr__("install_script")));
            }

            var1.setline(235);
            var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("info=%s")._mod(var1.getlocal(4).__call__(var2, var1.getlocal(3))));
            var1.setline(236);
            var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("target_compile=%d")._mod(var1.getlocal(0).__getattr__("no_target_compile").__not__()));
            var1.setline(237);
            var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("target_optimize=%d")._mod(var1.getlocal(0).__getattr__("no_target_optimize").__not__()));
            var1.setline(238);
            if (var1.getlocal(0).__getattr__("target_version").__nonzero__()) {
               var1.setline(239);
               var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("target_version=%s")._mod(var1.getlocal(0).__getattr__("target_version")));
            }

            var1.setline(240);
            if (var1.getlocal(0).__getattr__("user_access_control").__nonzero__()) {
               var1.setline(241);
               var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("user_access_control=%s")._mod(var1.getlocal(0).__getattr__("user_access_control")));
            }

            var1.setline(243);
            PyObject var9 = var1.getlocal(0).__getattr__("title");
            if (!var9.__nonzero__()) {
               var9 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_fullname").__call__(var2);
            }

            var6 = var9;
            var1.setlocal(7, var6);
            var3 = null;
            var1.setline(244);
            var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("title=%s")._mod(var1.getlocal(4).__call__(var2, var1.getlocal(7))));
            var1.setline(245);
            var6 = imp.importOne("time", var1, -1);
            var1.setlocal(8, var6);
            var3 = null;
            var1.setline(246);
            var6 = imp.importOne("distutils", var1, -1);
            var1.setlocal(9, var6);
            var3 = null;
            var1.setline(247);
            var6 = PyString.fromInterned("Built %s with distutils-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(8).__getattr__("ctime").__call__(var2, var1.getlocal(8).__getattr__("time").__call__(var2)), var1.getlocal(9).__getattr__("__version__")}));
            var1.setlocal(10, var6);
            var3 = null;
            var1.setline(249);
            var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("build_info=%s")._mod(var1.getlocal(10)));
            var1.setline(250);
            var6 = var1.getglobal("string").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("\n"));
            var1.f_lasti = -1;
            return var6;
         }

         var1.setlocal(5, var4);
         var1.setline(224);
         PyObject var5 = var1.getglobal("getattr").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned(""));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(225);
         if (var1.getlocal(6).__nonzero__()) {
            var1.setline(226);
            var5 = var1.getlocal(3)._add(PyString.fromInterned("\n    %s: %s")._mod(new PyTuple(new PyObject[]{var1.getglobal("string").__getattr__("capitalize").__call__(var2, var1.getlocal(5)), var1.getlocal(4).__call__(var2, var1.getlocal(6))})));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(228);
            var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("%s=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(4).__call__(var2, var1.getlocal(6))})));
         }
      }
   }

   public PyObject escape$6(PyFrame var1, ThreadState var2) {
      var1.setline(220);
      PyObject var3 = var1.getglobal("string").__getattr__("replace").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("\n"), (PyObject)PyString.fromInterned("\\n"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject create_exe$7(PyFrame var1, ThreadState var2) {
      var1.setline(255);
      PyObject var3 = imp.importOne("struct", var1, -1);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(257);
      var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getlocal(0).__getattr__("dist_dir"));
      var1.setline(259);
      var3 = var1.getlocal(0).__getattr__("get_inidata").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(261);
      var3 = var1.getlocal(0).__getattr__("get_installer_filename").__call__(var2, var1.getlocal(2));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(262);
      var1.getlocal(0).__getattr__("announce").__call__(var2, PyString.fromInterned("creating %s")._mod(var1.getlocal(6)));
      var1.setline(264);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(265);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("rb")).__getattr__("read").__call__(var2);
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(266);
         var3 = var1.getglobal("len").__call__(var2, var1.getlocal(7));
         var1.setlocal(8, var3);
         var3 = null;
      } else {
         var1.setline(268);
         PyInteger var6 = Py.newInteger(0);
         var1.setlocal(8, var6);
         var3 = null;
      }

      var1.setline(270);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("wb"));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(271);
      var1.getlocal(9).__getattr__("write").__call__(var2, var1.getlocal(0).__getattr__("get_exe_bytes").__call__(var2));
      var1.setline(272);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(273);
         var1.getlocal(9).__getattr__("write").__call__(var2, var1.getlocal(7));
      }

      label31: {
         try {
            var1.setline(277);
            var1.getglobal("unicode");
         } catch (Throwable var5) {
            PyException var7 = Py.setException(var5, var1);
            if (var7.match(var1.getglobal("NameError"))) {
               var1.setline(279);
               break label31;
            }

            throw var7;
         }

         var1.setline(281);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(5), var1.getglobal("unicode")).__nonzero__()) {
            var1.setline(282);
            PyObject var4 = var1.getlocal(5).__getattr__("encode").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("mbcs"));
            var1.setlocal(5, var4);
            var4 = null;
         }
      }

      var1.setline(285);
      var3 = var1.getlocal(5)._add(PyString.fromInterned("\u0000"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(286);
      if (var1.getlocal(0).__getattr__("pre_install_script").__nonzero__()) {
         var1.setline(287);
         var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("pre_install_script"), (PyObject)PyString.fromInterned("r")).__getattr__("read").__call__(var2);
         var1.setlocal(10, var3);
         var3 = null;
         var1.setline(288);
         var3 = var1.getlocal(5)._add(var1.getlocal(10))._add(PyString.fromInterned("\n\u0000"));
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(291);
         var3 = var1.getlocal(5)._add(PyString.fromInterned("\u0000"));
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(292);
      var1.getlocal(9).__getattr__("write").__call__(var2, var1.getlocal(5));
      var1.setline(299);
      var3 = var1.getlocal(4).__getattr__("pack").__call__(var2, PyString.fromInterned("<iii"), Py.newInteger(305419899), var1.getglobal("len").__call__(var2, var1.getlocal(5)), var1.getlocal(8));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(304);
      var1.getlocal(9).__getattr__("write").__call__(var2, var1.getlocal(11));
      var1.setline(305);
      var1.getlocal(9).__getattr__("write").__call__(var2, var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("rb")).__getattr__("read").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject get_installer_filename$8(PyFrame var1, ThreadState var2) {
      var1.setline(311);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("target_version").__nonzero__()) {
         var1.setline(314);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("dist_dir"), PyString.fromInterned("%s.%s-py%s.exe")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("plat_name"), var1.getlocal(0).__getattr__("target_version")})));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(318);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("dist_dir"), PyString.fromInterned("%s.%s.exe")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("plat_name")})));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(320);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject get_exe_bytes$9(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      String[] var3 = new String[]{"get_build_version"};
      PyObject[] var7 = imp.importFrom("distutils.msvccompiler", var3, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(333);
      PyObject var8 = var1.getglobal("get_python_version").__call__(var2);
      var1.setlocal(2, var8);
      var3 = null;
      var1.setline(334);
      PyObject var10000 = var1.getlocal(0).__getattr__("target_version");
      if (var10000.__nonzero__()) {
         var8 = var1.getlocal(0).__getattr__("target_version");
         var10000 = var8._ne(var1.getlocal(2));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(338);
         var8 = var1.getlocal(0).__getattr__("target_version");
         var10000 = var8._gt(var1.getlocal(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(339);
            var8 = var1.getlocal(1).__call__(var2);
            var1.setlocal(3, var8);
            var3 = null;
         } else {
            var1.setline(341);
            var8 = var1.getlocal(0).__getattr__("target_version");
            var10000 = var8._lt(PyString.fromInterned("2.4"));
            var3 = null;
            PyFloat var9;
            if (var10000.__nonzero__()) {
               var1.setline(342);
               var9 = Py.newFloat(6.0);
               var1.setlocal(3, var9);
               var3 = null;
            } else {
               var1.setline(344);
               var9 = Py.newFloat(7.1);
               var1.setlocal(3, var9);
               var3 = null;
            }
         }
      } else {
         var1.setline(347);
         var8 = var1.getlocal(1).__call__(var2);
         var1.setlocal(3, var8);
         var3 = null;
      }

      var1.setline(350);
      var8 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("__file__"));
      var1.setlocal(4, var8);
      var3 = null;
      var1.setline(357);
      var8 = var1.getlocal(0).__getattr__("plat_name");
      var10000 = var8._ne(PyString.fromInterned("win32"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var8 = var1.getlocal(0).__getattr__("plat_name").__getslice__((PyObject)null, Py.newInteger(3), (PyObject)null);
         var10000 = var8._eq(PyString.fromInterned("win"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(358);
         var8 = var1.getlocal(0).__getattr__("plat_name").__getslice__(Py.newInteger(3), (PyObject)null, (PyObject)null);
         var1.setlocal(5, var8);
         var3 = null;
      } else {
         var1.setline(360);
         PyString var10 = PyString.fromInterned("");
         var1.setlocal(5, var10);
         var3 = null;
      }

      var1.setline(362);
      var8 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), PyString.fromInterned("wininst-%.1f%s.exe")._mod(new PyTuple(new PyObject[]{var1.getlocal(3), var1.getlocal(5)})));
      var1.setlocal(6, var8);
      var3 = null;
      var1.setline(363);
      var8 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(6), (PyObject)PyString.fromInterned("rb"));
      var1.setlocal(7, var8);
      var3 = null;
      var3 = null;

      Throwable var12;
      label50: {
         boolean var10001;
         try {
            var1.setline(365);
            var4 = var1.getlocal(7).__getattr__("read").__call__(var2);
         } catch (Throwable var6) {
            var12 = var6;
            var10001 = false;
            break label50;
         }

         var1.setline(367);
         var1.getlocal(7).__getattr__("close").__call__(var2);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var12 = var5;
            var10001 = false;
         }
      }

      Throwable var11 = var12;
      Py.addTraceback(var11, var1);
      var1.setline(367);
      var1.getlocal(7).__getattr__("close").__call__(var2);
      throw (Throwable)var11;
   }

   public bdist_wininst$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      bdist_wininst$1 = Py.newCode(0, var2, var1, "bdist_wininst", 20, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 64, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bdist", "bdist_base", "short_version", "script"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 82, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "install", "install_lib", "target_version", "plat_specifier", "build", "key", "value", "mktemp", "archive_basename", "fullname", "arcname", "pyversion"};
      run$4 = Py.newCode(1, var2, var1, "run", 122, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lines", "metadata", "info", "escape", "name", "data", "title", "time", "distutils", "build_info"};
      get_inidata$5 = Py.newCode(1, var2, var1, "get_inidata", 205, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"s"};
      escape$6 = Py.newCode(1, var2, var1, "escape", 219, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "arcname", "fullname", "bitmap", "struct", "cfgdata", "installer_name", "bitmapdata", "bitmaplen", "file", "script_data", "header"};
      create_exe$7 = Py.newCode(4, var2, var1, "create_exe", 254, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname", "installer_name"};
      get_installer_filename$8 = Py.newCode(2, var2, var1, "get_installer_filename", 309, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "get_build_version", "cur_version", "bv", "directory", "sfix", "filename", "f"};
      get_exe_bytes$9 = Py.newCode(1, var2, var1, "get_exe_bytes", 323, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new bdist_wininst$py("distutils/command/bdist_wininst$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(bdist_wininst$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.bdist_wininst$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.finalize_options$3(var2, var3);
         case 4:
            return this.run$4(var2, var3);
         case 5:
            return this.get_inidata$5(var2, var3);
         case 6:
            return this.escape$6(var2, var3);
         case 7:
            return this.create_exe$7(var2, var3);
         case 8:
            return this.get_installer_filename$8(var2, var3);
         case 9:
            return this.get_exe_bytes$9(var2, var3);
         default:
            return null;
      }
   }
}
