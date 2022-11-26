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
@Filename("distutils/command/bdist_msi.py")
public class bdist_msi$py extends PyFunctionTable implements PyRunnable {
   static bdist_msi$py self;
   static final PyCode f$0;
   static final PyCode PyDialog$1;
   static final PyCode __init__$2;
   static final PyCode title$3;
   static final PyCode back$4;
   static final PyCode cancel$5;
   static final PyCode next$6;
   static final PyCode xbutton$7;
   static final PyCode bdist_msi$8;
   static final PyCode initialize_options$9;
   static final PyCode finalize_options$10;
   static final PyCode run$11;
   static final PyCode add_files$12;
   static final PyCode add_find_python$13;
   static final PyCode add_scripts$14;
   static final PyCode add_ui$15;
   static final PyCode get_installer_filename$16;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nImplements the bdist_msi command.\n"));
      var1.setline(8);
      PyString.fromInterned("\nImplements the bdist_msi command.\n");
      var1.setline(9);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(10);
      String[] var5 = new String[]{"get_python_version"};
      PyObject[] var6 = imp.importFrom("sysconfig", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("get_python_version", var4);
      var4 = null;
      var1.setline(12);
      var5 = new String[]{"Command"};
      var6 = imp.importFrom("distutils.core", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(13);
      var5 = new String[]{"remove_tree"};
      var6 = imp.importFrom("distutils.dir_util", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("remove_tree", var4);
      var4 = null;
      var1.setline(14);
      var5 = new String[]{"StrictVersion"};
      var6 = imp.importFrom("distutils.version", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("StrictVersion", var4);
      var4 = null;
      var1.setline(15);
      var5 = new String[]{"DistutilsOptionError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var1.setline(16);
      var5 = new String[]{"log"};
      var6 = imp.importFrom("distutils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(17);
      var5 = new String[]{"get_platform"};
      var6 = imp.importFrom("distutils.util", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("get_platform", var4);
      var4 = null;
      var1.setline(19);
      var3 = imp.importOne("msilib", var1, -1);
      var1.setlocal("msilib", var3);
      var3 = null;
      var1.setline(20);
      var5 = new String[]{"schema", "sequence", "text"};
      var6 = imp.importFrom("msilib", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("schema", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("sequence", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("text", var4);
      var4 = null;
      var1.setline(21);
      var5 = new String[]{"Directory", "Feature", "Dialog", "add_data"};
      var6 = imp.importFrom("msilib", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Directory", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("Feature", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("Dialog", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("add_data", var4);
      var4 = null;
      var1.setline(23);
      var6 = new PyObject[]{var1.getname("Dialog")};
      var4 = Py.makeClass("PyDialog", var6, PyDialog$1);
      var1.setlocal("PyDialog", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(84);
      var6 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("bdist_msi", var6, bdist_msi$8);
      var1.setlocal("bdist_msi", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject PyDialog$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Dialog class with a fixed layout: controls at the top, then a ruler,\n    then a list of buttons: back, next, cancel. Optionally a bitmap at the\n    left."));
      var1.setline(26);
      PyString.fromInterned("Dialog class with a fixed layout: controls at the top, then a ruler,\n    then a list of buttons: back, next, cancel. Optionally a bitmap at the\n    left.");
      var1.setline(27);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$2, PyString.fromInterned("Dialog(database, name, x, y, w, h, attributes, title, first,\n        default, cancel, bitmap=true)"));
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(36);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, title$3, PyString.fromInterned("Set the title text of the dialog at the top."));
      var1.setlocal("title", var4);
      var3 = null;
      var1.setline(43);
      var3 = new PyObject[]{PyString.fromInterned("Back"), Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, back$4, PyString.fromInterned("Add a back button with a given title, the tab-next button,\n        its name in the Control table, possibly initially disabled.\n\n        Return the button, so that events can be associated"));
      var1.setlocal("back", var4);
      var3 = null;
      var1.setline(54);
      var3 = new PyObject[]{PyString.fromInterned("Cancel"), Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, cancel$5, PyString.fromInterned("Add a cancel button with a given title, the tab-next button,\n        its name in the Control table, possibly initially disabled.\n\n        Return the button, so that events can be associated"));
      var1.setlocal("cancel", var4);
      var3 = null;
      var1.setline(65);
      var3 = new PyObject[]{PyString.fromInterned("Next"), Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, next$6, PyString.fromInterned("Add a Next button with a given title, the tab-next button,\n        its name in the Control table, possibly initially disabled.\n\n        Return the button, so that events can be associated"));
      var1.setlocal("next", var4);
      var3 = null;
      var1.setline(76);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, xbutton$7, PyString.fromInterned("Add a button with a given title, the tab-next button,\n        its name in the Control table, giving its x position; the\n        y-position is aligned with the other buttons.\n\n        Return the button, so that events can be associated"));
      var1.setlocal("xbutton", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$2(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyString.fromInterned("Dialog(database, name, x, y, w, h, attributes, title, first,\n        default, cancel, bitmap=true)");
      var1.setline(30);
      PyObject var10000 = var1.getglobal("Dialog").__getattr__("__init__");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[0];
      var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
      var3 = null;
      var1.setline(31);
      PyObject var5 = var1.getlocal(0).__getattr__("h")._sub(Py.newInteger(36));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(34);
      var10000 = var1.getlocal(0).__getattr__("line");
      var3 = new PyObject[]{PyString.fromInterned("BottomLine"), Py.newInteger(0), var1.getlocal(3), var1.getlocal(0).__getattr__("w"), Py.newInteger(0)};
      var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject title$3(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      PyString.fromInterned("Set the title text of the dialog at the top.");
      var1.setline(40);
      PyObject var10000 = var1.getlocal(0).__getattr__("text");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("Title"), Py.newInteger(15), Py.newInteger(10), Py.newInteger(320), Py.newInteger(60), Py.newInteger(196611), PyString.fromInterned("{\\VerdanaBold10}%s")._mod(var1.getlocal(1))};
      var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject back$4(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyString.fromInterned("Add a back button with a given title, the tab-next button,\n        its name in the Control table, possibly initially disabled.\n\n        Return the button, so that events can be associated");
      var1.setline(48);
      PyInteger var3;
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(49);
         var3 = Py.newInteger(3);
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(51);
         var3 = Py.newInteger(1);
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(52);
      PyObject var10000 = var1.getlocal(0).__getattr__("pushbutton");
      PyObject[] var4 = new PyObject[]{var1.getlocal(3), Py.newInteger(180), var1.getlocal(0).__getattr__("h")._sub(Py.newInteger(27)), Py.newInteger(56), Py.newInteger(17), var1.getlocal(5), var1.getlocal(1), var1.getlocal(2)};
      PyObject var5 = var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject cancel$5(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyString.fromInterned("Add a cancel button with a given title, the tab-next button,\n        its name in the Control table, possibly initially disabled.\n\n        Return the button, so that events can be associated");
      var1.setline(59);
      PyInteger var3;
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(60);
         var3 = Py.newInteger(3);
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(62);
         var3 = Py.newInteger(1);
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(63);
      PyObject var10000 = var1.getlocal(0).__getattr__("pushbutton");
      PyObject[] var4 = new PyObject[]{var1.getlocal(3), Py.newInteger(304), var1.getlocal(0).__getattr__("h")._sub(Py.newInteger(27)), Py.newInteger(56), Py.newInteger(17), var1.getlocal(5), var1.getlocal(1), var1.getlocal(2)};
      PyObject var5 = var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject next$6(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyString.fromInterned("Add a Next button with a given title, the tab-next button,\n        its name in the Control table, possibly initially disabled.\n\n        Return the button, so that events can be associated");
      var1.setline(70);
      PyInteger var3;
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(71);
         var3 = Py.newInteger(3);
         var1.setlocal(5, var3);
         var3 = null;
      } else {
         var1.setline(73);
         var3 = Py.newInteger(1);
         var1.setlocal(5, var3);
         var3 = null;
      }

      var1.setline(74);
      PyObject var10000 = var1.getlocal(0).__getattr__("pushbutton");
      PyObject[] var4 = new PyObject[]{var1.getlocal(3), Py.newInteger(236), var1.getlocal(0).__getattr__("h")._sub(Py.newInteger(27)), Py.newInteger(56), Py.newInteger(17), var1.getlocal(5), var1.getlocal(1), var1.getlocal(2)};
      PyObject var5 = var10000.__call__(var2, var4);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject xbutton$7(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyString.fromInterned("Add a button with a given title, the tab-next button,\n        its name in the Control table, giving its x position; the\n        y-position is aligned with the other buttons.\n\n        Return the button, so that events can be associated");
      var1.setline(82);
      PyObject var10000 = var1.getlocal(0).__getattr__("pushbutton");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getglobal("int").__call__(var2, var1.getlocal(0).__getattr__("w")._mul(var1.getlocal(4))._sub(Py.newInteger(28))), var1.getlocal(0).__getattr__("h")._sub(Py.newInteger(27)), Py.newInteger(56), Py.newInteger(17), Py.newInteger(3), var1.getlocal(2), var1.getlocal(3)};
      PyObject var4 = var10000.__call__(var2, var3);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject bdist_msi$8(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(86);
      PyString var3 = PyString.fromInterned("create a Microsoft Installer (.msi) binary distribution");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(88);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("bdist-dir="), var1.getname("None"), PyString.fromInterned("temporary directory for creating the distribution")}), new PyTuple(new PyObject[]{PyString.fromInterned("plat-name="), PyString.fromInterned("p"), PyString.fromInterned("platform name to embed in generated filenames (default: %s)")._mod(var1.getname("get_platform").__call__(var2))}), new PyTuple(new PyObject[]{PyString.fromInterned("keep-temp"), PyString.fromInterned("k"), PyString.fromInterned("keep the pseudo-installation tree around after ")._add(PyString.fromInterned("creating the distribution archive"))}), new PyTuple(new PyObject[]{PyString.fromInterned("target-version="), var1.getname("None"), PyString.fromInterned("require a specific python version")._add(PyString.fromInterned(" on the target system"))}), new PyTuple(new PyObject[]{PyString.fromInterned("no-target-compile"), PyString.fromInterned("c"), PyString.fromInterned("do not compile .py to .pyc on the target system")}), new PyTuple(new PyObject[]{PyString.fromInterned("no-target-optimize"), PyString.fromInterned("o"), PyString.fromInterned("do not compile .py to .pyo (optimized)on the target system")}), new PyTuple(new PyObject[]{PyString.fromInterned("dist-dir="), PyString.fromInterned("d"), PyString.fromInterned("directory to put final built distributions in")}), new PyTuple(new PyObject[]{PyString.fromInterned("skip-build"), var1.getname("None"), PyString.fromInterned("skip rebuilding everything (for testing/debugging)")}), new PyTuple(new PyObject[]{PyString.fromInterned("install-script="), var1.getname("None"), PyString.fromInterned("basename of installation script to be run afterinstallation or before deinstallation")}), new PyTuple(new PyObject[]{PyString.fromInterned("pre-install-script="), var1.getname("None"), PyString.fromInterned("Fully qualified filename of a script to be run before any files are installed.  This script need not be in the distribution")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(117);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("keep-temp"), PyString.fromInterned("no-target-compile"), PyString.fromInterned("no-target-optimize"), PyString.fromInterned("skip-build")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(120);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("2.0"), PyString.fromInterned("2.1"), PyString.fromInterned("2.2"), PyString.fromInterned("2.3"), PyString.fromInterned("2.4"), PyString.fromInterned("2.5"), PyString.fromInterned("2.6"), PyString.fromInterned("2.7"), PyString.fromInterned("2.8"), PyString.fromInterned("2.9"), PyString.fromInterned("3.0"), PyString.fromInterned("3.1"), PyString.fromInterned("3.2"), PyString.fromInterned("3.3"), PyString.fromInterned("3.4"), PyString.fromInterned("3.5"), PyString.fromInterned("3.6"), PyString.fromInterned("3.7"), PyString.fromInterned("3.8"), PyString.fromInterned("3.9")});
      var1.setlocal("all_versions", var4);
      var3 = null;
      var1.setline(124);
      var3 = PyString.fromInterned("X");
      var1.setlocal("other_version", var3);
      var3 = null;
      var1.setline(126);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$9, (PyObject)null);
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(139);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$10, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(180);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$11, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(271);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, add_files$12, (PyObject)null);
      var1.setlocal("add_files", var6);
      var3 = null;
      var1.setline(325);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, add_find_python$13, PyString.fromInterned("Adds code to the installer to compute the location of Python.\n\n        Properties PYTHON.MACHINE.X.Y and PYTHON.USER.X.Y will be set from the\n        registry for each version of Python.\n\n        Properties TARGETDIRX.Y will be set from PYTHON.USER.X.Y if defined,\n        else from PYTHON.MACHINE.X.Y.\n\n        Properties PYTHONX.Y will be set to TARGETDIRX.Y\\python.exe"));
      var1.setlocal("add_find_python", var6);
      var3 = null;
      var1.setline(379);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, add_scripts$14, (PyObject)null);
      var1.setlocal("add_scripts", var6);
      var3 = null;
      var1.setline(417);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, add_ui$15, (PyObject)null);
      var1.setlocal("add_ui", var6);
      var3 = null;
      var1.setline(734);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, get_installer_filename$16, (PyObject)null);
      var1.setlocal("get_installer_filename", var6);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$9(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("bdist_dir", var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("plat_name", var3);
      var3 = null;
      var1.setline(129);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"keep_temp", var4);
      var3 = null;
      var1.setline(130);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"no_target_compile", var4);
      var3 = null;
      var1.setline(131);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"no_target_optimize", var4);
      var3 = null;
      var1.setline(132);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("target_version", var3);
      var3 = null;
      var1.setline(133);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("dist_dir", var3);
      var3 = null;
      var1.setline(134);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("skip_build", var3);
      var3 = null;
      var1.setline(135);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_script", var3);
      var3 = null;
      var1.setline(136);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("pre_install_script", var3);
      var3 = null;
      var1.setline(137);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("versions", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$10(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bdist"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("skip_build"), PyString.fromInterned("skip_build")})));
      var1.setline(142);
      PyObject var3 = var1.getlocal(0).__getattr__("bdist_dir");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(143);
         var3 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bdist")).__getattr__("bdist_base");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(144);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("msi"));
         var1.getlocal(0).__setattr__("bdist_dir", var3);
         var3 = null;
      }

      var1.setline(146);
      var3 = var1.getglobal("get_python_version").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(147);
      var10000 = var1.getlocal(0).__getattr__("target_version").__not__();
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2);
      }

      if (var10000.__nonzero__()) {
         var1.setline(148);
         var3 = var1.getlocal(2);
         var1.getlocal(0).__setattr__("target_version", var3);
         var3 = null;
      }

      var1.setline(150);
      if (var1.getlocal(0).__getattr__("target_version").__nonzero__()) {
         var1.setline(151);
         PyList var6 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("target_version")});
         var1.getlocal(0).__setattr__((String)"versions", var6);
         var3 = null;
         var1.setline(152);
         var10000 = var1.getlocal(0).__getattr__("skip_build").__not__();
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2);
            if (var10000.__nonzero__()) {
               var3 = var1.getlocal(0).__getattr__("target_version");
               var10000 = var3._ne(var1.getlocal(2));
               var3 = null;
            }
         }

         if (var10000.__nonzero__()) {
            var1.setline(154);
            throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("target version can only be %s, or the '--skip-build' option must be specified")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)})));
         }
      } else {
         var1.setline(158);
         var3 = var1.getglobal("list").__call__(var2, var1.getlocal(0).__getattr__("all_versions"));
         var1.getlocal(0).__setattr__("versions", var3);
         var3 = null;
      }

      var1.setline(160);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, PyString.fromInterned("bdist"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("dist_dir"), PyString.fromInterned("dist_dir")})), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("plat_name"), PyString.fromInterned("plat_name")})));
      var1.setline(165);
      if (var1.getlocal(0).__getattr__("pre_install_script").__nonzero__()) {
         var1.setline(166);
         throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("the pre-install-script feature is not yet implemented"));
      } else {
         var1.setline(168);
         if (var1.getlocal(0).__getattr__("install_script").__nonzero__()) {
            var1.setline(169);
            var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("scripts").__iter__();

            do {
               var1.setline(169);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(173);
                  throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("install_script '%s' not found in scripts")._mod(var1.getlocal(0).__getattr__("install_script")));
               }

               var1.setlocal(3, var4);
               var1.setline(170);
               PyObject var5 = var1.getlocal(0).__getattr__("install_script");
               var10000 = var5._eq(var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(3)));
               var5 = null;
            } while(!var10000.__nonzero__());
         }

         var1.setline(176);
         var3 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("install_script_key", var3);
         var3 = null;
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject run$11(PyFrame var1, ThreadState var2) {
      var1.setline(181);
      if (var1.getlocal(0).__getattr__("skip_build").__not__().__nonzero__()) {
         var1.setline(182);
         var1.getlocal(0).__getattr__("run_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build"));
      }

      var1.setline(184);
      PyObject var10000 = var1.getlocal(0).__getattr__("reinitialize_command");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("install"), Py.newInteger(1)};
      String[] var4 = new String[]{"reinit_subcommands"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(185);
      var5 = var1.getlocal(0).__getattr__("bdist_dir");
      var1.getlocal(1).__setattr__("prefix", var5);
      var3 = null;
      var1.setline(186);
      var5 = var1.getlocal(0).__getattr__("skip_build");
      var1.getlocal(1).__setattr__("skip_build", var5);
      var3 = null;
      var1.setline(187);
      PyInteger var6 = Py.newInteger(0);
      var1.getlocal(1).__setattr__((String)"warn_dir", var6);
      var3 = null;
      var1.setline(189);
      var5 = var1.getlocal(0).__getattr__("reinitialize_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("install_lib"));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(191);
      var6 = Py.newInteger(0);
      var1.getlocal(2).__setattr__((String)"compile", var6);
      var3 = null;
      var1.setline(192);
      var6 = Py.newInteger(0);
      var1.getlocal(2).__setattr__((String)"optimize", var6);
      var3 = null;
      var1.setline(194);
      if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2).__nonzero__()) {
         var1.setline(201);
         var5 = var1.getlocal(0).__getattr__("target_version");
         var1.setlocal(3, var5);
         var3 = null;
         var1.setline(202);
         if (var1.getlocal(3).__not__().__nonzero__()) {
            var1.setline(203);
            if (var1.getglobal("__debug__").__nonzero__() && !var1.getlocal(0).__getattr__("skip_build").__nonzero__()) {
               throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("Should have already checked this"));
            }

            var1.setline(204);
            var5 = var1.getglobal("sys").__getattr__("version").__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null);
            var1.setlocal(3, var5);
            var3 = null;
         }

         var1.setline(205);
         var5 = PyString.fromInterned(".%s-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("plat_name"), var1.getlocal(3)}));
         var1.setlocal(4, var5);
         var3 = null;
         var1.setline(206);
         var5 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build"));
         var1.setlocal(5, var5);
         var3 = null;
         var1.setline(207);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5).__getattr__("build_base"), PyString.fromInterned("lib")._add(var1.getlocal(4)));
         var1.getlocal(5).__setattr__("build_lib", var5);
         var3 = null;
      }

      var1.setline(210);
      var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("installing to %s"), (PyObject)var1.getlocal(0).__getattr__("bdist_dir"));
      var1.setline(211);
      var1.getlocal(1).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(215);
      var1.getglobal("sys").__getattr__("path").__getattr__("insert").__call__((ThreadState)var2, (PyObject)Py.newInteger(0), (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("bdist_dir"), (PyObject)PyString.fromInterned("PURELIB")));
      var1.setline(217);
      var1.getlocal(1).__getattr__("run").__call__(var2);
      var1.setline(219);
      var1.getglobal("sys").__getattr__("path").__delitem__((PyObject)Py.newInteger(0));
      var1.setline(221);
      var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getlocal(0).__getattr__("dist_dir"));
      var1.setline(222);
      var5 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_fullname").__call__(var2);
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(223);
      var5 = var1.getlocal(0).__getattr__("get_installer_filename").__call__(var2, var1.getlocal(6));
      var1.setlocal(7, var5);
      var3 = null;
      var1.setline(224);
      var5 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(7));
      var1.setlocal(7, var5);
      var3 = null;
      var1.setline(225);
      if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(7)).__nonzero__()) {
         var1.setline(225);
         var1.getglobal("os").__getattr__("unlink").__call__(var2, var1.getlocal(7));
      }

      var1.setline(227);
      var5 = var1.getlocal(0).__getattr__("distribution").__getattr__("metadata");
      var1.setlocal(8, var5);
      var3 = null;
      var1.setline(228);
      var5 = var1.getlocal(8).__getattr__("author");
      var1.setlocal(9, var5);
      var3 = null;
      var1.setline(229);
      if (var1.getlocal(9).__not__().__nonzero__()) {
         var1.setline(230);
         var5 = var1.getlocal(8).__getattr__("maintainer");
         var1.setlocal(9, var5);
         var3 = null;
      }

      var1.setline(231);
      if (var1.getlocal(9).__not__().__nonzero__()) {
         var1.setline(232);
         PyString var7 = PyString.fromInterned("UNKNOWN");
         var1.setlocal(9, var7);
         var3 = null;
      }

      var1.setline(233);
      var5 = var1.getlocal(8).__getattr__("get_version").__call__(var2);
      var1.setlocal(10, var5);
      var3 = null;
      var1.setline(236);
      var5 = PyString.fromInterned("%d.%d.%d")._mod(var1.getglobal("StrictVersion").__call__(var2, var1.getlocal(10)).__getattr__("version"));
      var1.setlocal(11, var5);
      var3 = null;
      var1.setline(240);
      var5 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_fullname").__call__(var2);
      var1.setlocal(6, var5);
      var3 = null;
      var1.setline(241);
      if (var1.getlocal(0).__getattr__("target_version").__nonzero__()) {
         var1.setline(242);
         var5 = PyString.fromInterned("Python %s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("target_version"), var1.getlocal(6)}));
         var1.setlocal(12, var5);
         var3 = null;
      } else {
         var1.setline(244);
         var5 = PyString.fromInterned("Python %s")._mod(var1.getlocal(6));
         var1.setlocal(12, var5);
         var3 = null;
      }

      var1.setline(245);
      var10000 = var1.getglobal("msilib").__getattr__("init_database");
      var3 = new PyObject[]{var1.getlocal(7), var1.getglobal("schema"), var1.getlocal(12), var1.getglobal("msilib").__getattr__("gen_uuid").__call__(var2), var1.getlocal(11), var1.getlocal(9)};
      var5 = var10000.__call__(var2, var3);
      var1.getlocal(0).__setattr__("db", var5);
      var3 = null;
      var1.setline(248);
      var1.getglobal("msilib").__getattr__("add_tables").__call__(var2, var1.getlocal(0).__getattr__("db"), var1.getglobal("sequence"));
      var1.setline(249);
      PyList var8 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("DistVersion"), var1.getlocal(10)})});
      var1.setlocal(13, var8);
      var3 = null;
      var1.setline(250);
      var10000 = var1.getlocal(8).__getattr__("author_email");
      if (!var10000.__nonzero__()) {
         var10000 = var1.getlocal(8).__getattr__("maintainer_email");
      }

      var5 = var10000;
      var1.setlocal(14, var5);
      var3 = null;
      var1.setline(251);
      if (var1.getlocal(14).__nonzero__()) {
         var1.setline(252);
         var1.getlocal(13).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("ARPCONTACT"), var1.getlocal(14)})));
      }

      var1.setline(253);
      if (var1.getlocal(8).__getattr__("url").__nonzero__()) {
         var1.setline(254);
         var1.getlocal(13).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("ARPURLINFOABOUT"), var1.getlocal(8).__getattr__("url")})));
      }

      var1.setline(255);
      if (var1.getlocal(13).__nonzero__()) {
         var1.setline(256);
         var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("Property"), (PyObject)var1.getlocal(13));
      }

      var1.setline(258);
      var1.getlocal(0).__getattr__("add_find_python").__call__(var2);
      var1.setline(259);
      var1.getlocal(0).__getattr__("add_files").__call__(var2);
      var1.setline(260);
      var1.getlocal(0).__getattr__("add_scripts").__call__(var2);
      var1.setline(261);
      var1.getlocal(0).__getattr__("add_ui").__call__(var2);
      var1.setline(262);
      var1.getlocal(0).__getattr__("db").__getattr__("Commit").__call__(var2);
      var1.setline(264);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("distribution"), (PyObject)PyString.fromInterned("dist_files")).__nonzero__()) {
         var1.setline(265);
         PyTuple var10 = new PyTuple;
         PyObject[] var10002 = new PyObject[]{PyString.fromInterned("bdist_msi"), null, null};
         Object var10005 = var1.getlocal(0).__getattr__("target_version");
         if (!((PyObject)var10005).__nonzero__()) {
            var10005 = PyString.fromInterned("any");
         }

         var10002[1] = (PyObject)var10005;
         var10002[2] = var1.getlocal(6);
         var10.<init>(var10002);
         PyTuple var9 = var10;
         var1.setlocal(15, var9);
         var3 = null;
         var1.setline(266);
         var1.getlocal(0).__getattr__("distribution").__getattr__("dist_files").__getattr__("append").__call__(var2, var1.getlocal(15));
      }

      var1.setline(268);
      if (var1.getlocal(0).__getattr__("keep_temp").__not__().__nonzero__()) {
         var1.setline(269);
         var10000 = var1.getglobal("remove_tree");
         var3 = new PyObject[]{var1.getlocal(0).__getattr__("bdist_dir"), var1.getlocal(0).__getattr__("dry_run")};
         var4 = new String[]{"dry_run"};
         var10000.__call__(var2, var3, var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_files$12(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyObject var3 = var1.getlocal(0).__getattr__("db");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(273);
      var3 = var1.getglobal("msilib").__getattr__("CAB").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("distfiles"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(274);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(0).__getattr__("bdist_dir"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(276);
      PyObject var10000 = var1.getglobal("Directory");
      PyObject[] var9 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getglobal("None"), var1.getlocal(3), PyString.fromInterned("TARGETDIR"), PyString.fromInterned("SourceDir")};
      var3 = var10000.__call__(var2, var9);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(277);
      var10000 = var1.getglobal("Feature");
      var9 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("Python"), PyString.fromInterned("Python"), PyString.fromInterned("Everything"), Py.newInteger(0), Py.newInteger(1), PyString.fromInterned("TARGETDIR")};
      String[] var4 = new String[]{"directory"};
      var10000 = var10000.__call__(var2, var9, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(280);
      PyList var12 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(4), PyString.fromInterned("")})});
      var1.setlocal(6, var12);
      var3 = null;
      var1.setline(281);
      var3 = var1.getlocal(0).__getattr__("versions")._add(new PyList(new PyObject[]{var1.getlocal(0).__getattr__("other_version")})).__iter__();

      while(true) {
         var1.setline(281);
         PyObject var8 = var3.__iternext__();
         PyObject var5;
         String[] var6;
         PyObject[] var16;
         if (var8 == null) {
            var1.setline(294);
            var1.getlocal(1).__getattr__("Commit").__call__(var2);
            var1.setline(296);
            PyDictionary var14 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(15, var14);
            var3 = null;
            var1.setline(297);
            var3 = var1.getlocal(6).__iter__();

            while(true) {
               var1.setline(297);
               var8 = var3.__iternext__();
               if (var8 == null) {
                  var1.setline(323);
                  var1.getlocal(2).__getattr__("commit").__call__(var2, var1.getlocal(1));
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var16 = Py.unpackSequence(var8, 3);
               PyObject var11 = var16[0];
               var1.setlocal(16, var11);
               var6 = null;
               var11 = var16[1];
               var1.setlocal(14, var11);
               var6 = null;
               var11 = var16[2];
               var1.setlocal(7, var11);
               var6 = null;
               var1.setline(298);
               PyList var17 = new PyList(new PyObject[]{var1.getlocal(14)});
               var1.setlocal(17, var17);
               var5 = null;

               while(true) {
                  var1.setline(299);
                  if (!var1.getlocal(17).__nonzero__()) {
                     var1.setline(322);
                     var1.getlocal(1).__getattr__("Commit").__call__(var2);
                     break;
                  }

                  var1.setline(300);
                  var5 = var1.getlocal(17).__getattr__("pop").__call__(var2);
                  var1.setlocal(14, var5);
                  var5 = null;
                  var1.setline(301);
                  var5 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(14).__getattr__("absolute")).__iter__();

                  while(true) {
                     var1.setline(301);
                     var11 = var5.__iternext__();
                     if (var11 == null) {
                        break;
                     }

                     var1.setlocal(18, var11);
                     var1.setline(302);
                     PyObject var7 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(14).__getattr__("absolute"), var1.getlocal(18));
                     var1.setlocal(19, var7);
                     var7 = null;
                     var1.setline(303);
                     if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(19)).__nonzero__()) {
                        var1.setline(304);
                        var7 = PyString.fromInterned("%s|%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(14).__getattr__("make_short").__call__(var2, var1.getlocal(18)), var1.getlocal(18)}));
                        var1.setlocal(20, var7);
                        var7 = null;
                        var1.setline(305);
                        var7 = var1.getlocal(18)._add(var1.getlocal(7));
                        var1.setlocal(10, var7);
                        var7 = null;
                        var1.setline(306);
                        var10000 = var1.getglobal("Directory");
                        PyObject[] var15 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(14), var1.getlocal(18), var1.getlocal(10), var1.getlocal(20)};
                        var7 = var10000.__call__(var2, var15);
                        var1.setlocal(21, var7);
                        var7 = null;
                        var1.setline(307);
                        var1.getlocal(17).__getattr__("append").__call__(var2, var1.getlocal(21));
                     } else {
                        var1.setline(309);
                        if (var1.getlocal(14).__getattr__("component").__not__().__nonzero__()) {
                           var1.setline(310);
                           var1.getlocal(14).__getattr__("start_component").__call__((ThreadState)var2, var1.getlocal(14).__getattr__("logical"), (PyObject)var1.getlocal(16), (PyObject)Py.newInteger(0));
                        }

                        var1.setline(311);
                        var7 = var1.getlocal(19);
                        var10000 = var7._notin(var1.getlocal(15));
                        var7 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(312);
                           var7 = var1.getlocal(14).__getattr__("add_file").__call__(var2, var1.getlocal(18));
                           var1.setlocal(22, var7);
                           var1.getlocal(15).__setitem__(var1.getlocal(19), var7);
                           var1.setline(313);
                           var7 = var1.getlocal(18);
                           var10000 = var7._eq(var1.getlocal(0).__getattr__("install_script"));
                           var7 = null;
                           if (var10000.__nonzero__()) {
                              var1.setline(314);
                              if (var1.getlocal(0).__getattr__("install_script_key").__nonzero__()) {
                                 var1.setline(315);
                                 throw Py.makeException(var1.getglobal("DistutilsOptionError").__call__(var2, PyString.fromInterned("Multiple files with name %s")._mod(var1.getlocal(18))));
                              }

                              var1.setline(317);
                              var7 = PyString.fromInterned("[#%s]")._mod(var1.getlocal(22));
                              var1.getlocal(0).__setattr__("install_script_key", var7);
                              var7 = null;
                           }
                        } else {
                           var1.setline(319);
                           var7 = var1.getlocal(15).__getitem__(var1.getlocal(19));
                           var1.setlocal(22, var7);
                           var7 = null;
                           var1.setline(320);
                           var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("DuplicateFile"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(22)._add(var1.getlocal(7)), var1.getlocal(14).__getattr__("component"), var1.getlocal(22), var1.getglobal("None"), var1.getlocal(14).__getattr__("logical")})})));
                        }
                     }
                  }
               }
            }
         }

         var1.setlocal(7, var8);
         var1.setline(282);
         var5 = PyString.fromInterned("TARGETDIR")._add(var1.getlocal(7));
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(283);
         var5 = PyString.fromInterned("Python")._add(var1.getlocal(7));
         var1.setlocal(9, var5);
         var1.setlocal(10, var5);
         var1.setline(284);
         PyString var10 = PyString.fromInterned("Everything");
         var1.setlocal(11, var10);
         var5 = null;
         var1.setline(285);
         var5 = var1.getlocal(7);
         var10000 = var5._is(var1.getlocal(0).__getattr__("other_version"));
         var5 = null;
         PyInteger var13;
         if (var10000.__nonzero__()) {
            var1.setline(286);
            var10 = PyString.fromInterned("Python from another location");
            var1.setlocal(12, var10);
            var5 = null;
            var1.setline(287);
            var13 = Py.newInteger(2);
            var1.setlocal(13, var13);
            var5 = null;
         } else {
            var1.setline(289);
            var5 = PyString.fromInterned("Python %s from registry")._mod(var1.getlocal(7));
            var1.setlocal(12, var5);
            var5 = null;
            var1.setline(290);
            var13 = Py.newInteger(1);
            var1.setlocal(13, var13);
            var5 = null;
         }

         var1.setline(291);
         var10000 = var1.getglobal("Feature");
         var16 = new PyObject[]{var1.getlocal(1), var1.getlocal(9), var1.getlocal(12), var1.getlocal(11), Py.newInteger(1), var1.getlocal(13), var1.getlocal(8)};
         var6 = new String[]{"directory"};
         var10000 = var10000.__call__(var2, var16, var6);
         var5 = null;
         var5 = var10000;
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(292);
         var10000 = var1.getglobal("Directory");
         var16 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(4), var1.getlocal(3), var1.getlocal(8), var1.getlocal(10)};
         var5 = var10000.__call__(var2, var16);
         var1.setlocal(14, var5);
         var5 = null;
         var1.setline(293);
         var1.getlocal(6).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(5), var1.getlocal(14), var1.getlocal(7)})));
      }
   }

   public PyObject add_find_python$13(PyFrame var1, ThreadState var2) {
      var1.setline(334);
      PyString.fromInterned("Adds code to the installer to compute the location of Python.\n\n        Properties PYTHON.MACHINE.X.Y and PYTHON.USER.X.Y will be set from the\n        registry for each version of Python.\n\n        Properties TARGETDIRX.Y will be set from PYTHON.USER.X.Y if defined,\n        else from PYTHON.MACHINE.X.Y.\n\n        Properties PYTHONX.Y will be set to TARGETDIRX.Y\\python.exe");
      var1.setline(336);
      PyInteger var3 = Py.newInteger(402);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(337);
      PyObject var6 = var1.getlocal(0).__getattr__("versions").__iter__();

      while(true) {
         var1.setline(337);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(338);
         PyObject var5 = PyString.fromInterned("SOFTWARE\\Python\\PythonCore\\%s\\InstallPath")._mod(var1.getlocal(2));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(339);
         var5 = PyString.fromInterned("python.machine.")._add(var1.getlocal(2));
         var1.setlocal(4, var5);
         var5 = null;
         var1.setline(340);
         var5 = PyString.fromInterned("python.user.")._add(var1.getlocal(2));
         var1.setlocal(5, var5);
         var5 = null;
         var1.setline(341);
         var5 = PyString.fromInterned("PYTHON.MACHINE.")._add(var1.getlocal(2));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(342);
         var5 = PyString.fromInterned("PYTHON.USER.")._add(var1.getlocal(2));
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(343);
         var5 = PyString.fromInterned("PythonFromMachine")._add(var1.getlocal(2));
         var1.setlocal(8, var5);
         var5 = null;
         var1.setline(344);
         var5 = PyString.fromInterned("PythonFromUser")._add(var1.getlocal(2));
         var1.setlocal(9, var5);
         var5 = null;
         var1.setline(345);
         var5 = PyString.fromInterned("PythonExe")._add(var1.getlocal(2));
         var1.setlocal(10, var5);
         var5 = null;
         var1.setline(346);
         var5 = PyString.fromInterned("TARGETDIR")._add(var1.getlocal(2));
         var1.setlocal(11, var5);
         var5 = null;
         var1.setline(347);
         var5 = PyString.fromInterned("PYTHON")._add(var1.getlocal(2));
         var1.setlocal(12, var5);
         var5 = null;
         var1.setline(348);
         if (var1.getglobal("msilib").__getattr__("Win64").__nonzero__()) {
            var1.setline(350);
            var5 = Py.newInteger(2)._add(Py.newInteger(16));
            var1.setlocal(13, var5);
            var5 = null;
         } else {
            var1.setline(352);
            PyInteger var7 = Py.newInteger(2);
            var1.setlocal(13, var7);
            var5 = null;
         }

         var1.setline(353);
         var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("RegLocator"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(4), Py.newInteger(2), var1.getlocal(3), var1.getglobal("None"), var1.getlocal(13)}), new PyTuple(new PyObject[]{var1.getlocal(5), Py.newInteger(1), var1.getlocal(3), var1.getglobal("None"), var1.getlocal(13)})})));
         var1.setline(356);
         var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("AppSearch"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(6), var1.getlocal(4)}), new PyTuple(new PyObject[]{var1.getlocal(7), var1.getlocal(5)})})));
         var1.setline(359);
         var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("CustomAction"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(8), Py.newInteger(51)._add(Py.newInteger(256)), var1.getlocal(11), PyString.fromInterned("[")._add(var1.getlocal(6))._add(PyString.fromInterned("]"))}), new PyTuple(new PyObject[]{var1.getlocal(9), Py.newInteger(51)._add(Py.newInteger(256)), var1.getlocal(11), PyString.fromInterned("[")._add(var1.getlocal(7))._add(PyString.fromInterned("]"))}), new PyTuple(new PyObject[]{var1.getlocal(10), Py.newInteger(51)._add(Py.newInteger(256)), var1.getlocal(12), PyString.fromInterned("[")._add(var1.getlocal(11))._add(PyString.fromInterned("]\\python.exe"))})})));
         var1.setline(364);
         var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("InstallExecuteSequence"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(6), var1.getlocal(1)}), new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(7), var1.getlocal(1)._add(Py.newInteger(1))}), new PyTuple(new PyObject[]{var1.getlocal(10), var1.getglobal("None"), var1.getlocal(1)._add(Py.newInteger(2))})})));
         var1.setline(369);
         var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("InstallUISequence"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(8), var1.getlocal(6), var1.getlocal(1)}), new PyTuple(new PyObject[]{var1.getlocal(9), var1.getlocal(7), var1.getlocal(1)._add(Py.newInteger(1))}), new PyTuple(new PyObject[]{var1.getlocal(10), var1.getglobal("None"), var1.getlocal(1)._add(Py.newInteger(2))})})));
         var1.setline(374);
         var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("Condition"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("Python")._add(var1.getlocal(2)), Py.newInteger(0), PyString.fromInterned("NOT TARGETDIR")._add(var1.getlocal(2))})})));
         var1.setline(376);
         var5 = var1.getlocal(1);
         var5 = var5._iadd(Py.newInteger(4));
         var1.setlocal(1, var5);
         var1.setline(377);
         if (var1.getglobal("__debug__").__nonzero__()) {
            var5 = var1.getlocal(1);
            PyObject var10000 = var5._lt(Py.newInteger(500));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var10000 = Py.None;
               throw Py.makeException(var1.getglobal("AssertionError"), var10000);
            }
         }
      }
   }

   public PyObject add_scripts$14(PyFrame var1, ThreadState var2) {
      var1.setline(380);
      PyInteger var3;
      PyObject var6;
      if (var1.getlocal(0).__getattr__("install_script").__nonzero__()) {
         var1.setline(381);
         var3 = Py.newInteger(6800);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(382);
         var6 = var1.getlocal(0).__getattr__("versions")._add(new PyList(new PyObject[]{var1.getlocal(0).__getattr__("other_version")})).__iter__();

         while(true) {
            var1.setline(382);
            PyObject var4 = var6.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(2, var4);
            var1.setline(383);
            PyObject var5 = PyString.fromInterned("install_script.")._add(var1.getlocal(2));
            var1.setlocal(3, var5);
            var5 = null;
            var1.setline(384);
            var5 = PyString.fromInterned("PYTHON")._add(var1.getlocal(2));
            var1.setlocal(4, var5);
            var5 = null;
            var1.setline(385);
            var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("CustomAction"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(3), Py.newInteger(50), var1.getlocal(4), var1.getlocal(0).__getattr__("install_script_key")})})));
            var1.setline(387);
            var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("InstallExecuteSequence"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{var1.getlocal(3), PyString.fromInterned("&Python%s=3")._mod(var1.getlocal(2)), var1.getlocal(1)})})));
            var1.setline(389);
            var5 = var1.getlocal(1);
            var5 = var5._iadd(Py.newInteger(1));
            var1.setlocal(1, var5);
         }
      }

      var1.setline(393);
      if (var1.getlocal(0).__getattr__("pre_install_script").__nonzero__()) {
         var1.setline(394);
         var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("bdist_dir"), (PyObject)PyString.fromInterned("preinstall.bat"));
         var1.setlocal(5, var6);
         var3 = null;
         var1.setline(395);
         var6 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(6, var6);
         var3 = null;
         var1.setline(404);
         var1.getlocal(6).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("rem =\"\"\"\n%1 %0\nexit\n\"\"\"\n"));
         var1.setline(405);
         var1.getlocal(6).__getattr__("write").__call__(var2, var1.getglobal("open").__call__(var2, var1.getlocal(0).__getattr__("pre_install_script")).__getattr__("read").__call__(var2));
         var1.setline(406);
         var1.getlocal(6).__getattr__("close").__call__(var2);
         var1.setline(407);
         var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("Binary"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("PreInstall"), var1.getglobal("msilib").__getattr__("Binary").__call__(var2, var1.getlocal(5))})})));
         var1.setline(410);
         var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("CustomAction"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("PreInstall"), Py.newInteger(2), PyString.fromInterned("PreInstall"), var1.getglobal("None")})})));
         var1.setline(413);
         var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(0).__getattr__("db"), (PyObject)PyString.fromInterned("InstallExecuteSequence"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("PreInstall"), PyString.fromInterned("NOT Installed"), Py.newInteger(450)})})));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject add_ui$15(PyFrame var1, ThreadState var2) {
      var1.setline(418);
      PyObject var3 = var1.getlocal(0).__getattr__("db");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(419);
      PyInteger var7 = Py.newInteger(50);
      var1.setlocal(2, var7);
      var1.setlocal(3, var7);
      var1.setline(420);
      var7 = Py.newInteger(370);
      var1.setlocal(4, var7);
      var3 = null;
      var1.setline(421);
      var7 = Py.newInteger(300);
      var1.setlocal(5, var7);
      var3 = null;
      var1.setline(422);
      PyString var8 = PyString.fromInterned("[ProductName] Setup");
      var1.setlocal(6, var8);
      var3 = null;
      var1.setline(425);
      var7 = Py.newInteger(3);
      var1.setlocal(7, var7);
      var3 = null;
      var1.setline(426);
      var7 = Py.newInteger(1);
      var1.setlocal(8, var7);
      var3 = null;
      var1.setline(429);
      var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("Property"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("DefaultUIFont"), PyString.fromInterned("DlgFont8")}), new PyTuple(new PyObject[]{PyString.fromInterned("ErrorDialog"), PyString.fromInterned("ErrorDlg")}), new PyTuple(new PyObject[]{PyString.fromInterned("Progress1"), PyString.fromInterned("Install")}), new PyTuple(new PyObject[]{PyString.fromInterned("Progress2"), PyString.fromInterned("installs")}), new PyTuple(new PyObject[]{PyString.fromInterned("MaintenanceForm_Action"), PyString.fromInterned("Repair")}), new PyTuple(new PyObject[]{PyString.fromInterned("WhichUsers"), PyString.fromInterned("ALL")})})));
      var1.setline(442);
      var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("TextStyle"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("DlgFont8"), PyString.fromInterned("Tahoma"), Py.newInteger(9), var1.getglobal("None"), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("DlgFontBold8"), PyString.fromInterned("Tahoma"), Py.newInteger(8), var1.getglobal("None"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("VerdanaBold10"), PyString.fromInterned("Verdana"), Py.newInteger(10), var1.getglobal("None"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("VerdanaRed9"), PyString.fromInterned("Verdana"), Py.newInteger(9), Py.newInteger(255), Py.newInteger(0)})})));
      var1.setline(451);
      var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("InstallUISequence"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("PrepareDlg"), PyString.fromInterned("Not Privileged or Windows9x or Installed"), Py.newInteger(140)}), new PyTuple(new PyObject[]{PyString.fromInterned("WhichUsersDlg"), PyString.fromInterned("Privileged and not Windows9x and not Installed"), Py.newInteger(141)}), new PyTuple(new PyObject[]{PyString.fromInterned("SelectFeaturesDlg"), PyString.fromInterned("Not Installed"), Py.newInteger(1230)}), new PyTuple(new PyObject[]{PyString.fromInterned("MaintenanceTypeDlg"), PyString.fromInterned("Installed AND NOT RESUME AND NOT Preselected"), Py.newInteger(1250)}), new PyTuple(new PyObject[]{PyString.fromInterned("ProgressDlg"), var1.getglobal("None"), Py.newInteger(1280)})})));
      var1.setline(461);
      var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("ActionText"), (PyObject)var1.getglobal("text").__getattr__("ActionText"));
      var1.setline(462);
      var1.getglobal("add_data").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("UIText"), (PyObject)var1.getglobal("text").__getattr__("UIText"));
      var1.setline(465);
      PyObject var10000 = var1.getglobal("PyDialog");
      PyObject[] var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("FatalError"), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(7), var1.getlocal(6), PyString.fromInterned("Finish"), PyString.fromInterned("Finish"), PyString.fromInterned("Finish")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(467);
      var1.getlocal(9).__getattr__("title").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[ProductName] Installer ended prematurely"));
      var1.setline(468);
      var10000 = var1.getlocal(9).__getattr__("back");
      var10 = new PyObject[]{PyString.fromInterned("< Back"), PyString.fromInterned("Finish"), Py.newInteger(0)};
      String[] var4 = new String[]{"active"};
      var10000.__call__(var2, var10, var4);
      var3 = null;
      var1.setline(469);
      var10000 = var1.getlocal(9).__getattr__("cancel");
      var10 = new PyObject[]{PyString.fromInterned("Cancel"), PyString.fromInterned("Back"), Py.newInteger(0)};
      var4 = new String[]{"active"};
      var10000.__call__(var2, var10, var4);
      var3 = null;
      var1.setline(470);
      var10000 = var1.getlocal(9).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("Description1"), Py.newInteger(15), Py.newInteger(70), Py.newInteger(320), Py.newInteger(80), Py.newInteger(196611), PyString.fromInterned("[ProductName] setup ended prematurely because of an error.  Your system has not been modified.  To install this program at a later time, please run the installation again.")};
      var10000.__call__(var2, var10);
      var1.setline(472);
      var10000 = var1.getlocal(9).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("Description2"), Py.newInteger(15), Py.newInteger(155), Py.newInteger(320), Py.newInteger(20), Py.newInteger(196611), PyString.fromInterned("Click the Finish button to exit the Installer.")};
      var10000.__call__(var2, var10);
      var1.setline(474);
      var10000 = var1.getlocal(9).__getattr__("next");
      var10 = new PyObject[]{PyString.fromInterned("Finish"), PyString.fromInterned("Cancel"), PyString.fromInterned("Finish")};
      var4 = new String[]{"name"};
      var10000 = var10000.__call__(var2, var10, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(475);
      var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("Exit"));
      var1.setline(477);
      var10000 = var1.getglobal("PyDialog");
      var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("UserExit"), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(7), var1.getlocal(6), PyString.fromInterned("Finish"), PyString.fromInterned("Finish"), PyString.fromInterned("Finish")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(479);
      var1.getlocal(11).__getattr__("title").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[ProductName] Installer was interrupted"));
      var1.setline(480);
      var10000 = var1.getlocal(11).__getattr__("back");
      var10 = new PyObject[]{PyString.fromInterned("< Back"), PyString.fromInterned("Finish"), Py.newInteger(0)};
      var4 = new String[]{"active"};
      var10000.__call__(var2, var10, var4);
      var3 = null;
      var1.setline(481);
      var10000 = var1.getlocal(11).__getattr__("cancel");
      var10 = new PyObject[]{PyString.fromInterned("Cancel"), PyString.fromInterned("Back"), Py.newInteger(0)};
      var4 = new String[]{"active"};
      var10000.__call__(var2, var10, var4);
      var3 = null;
      var1.setline(482);
      var10000 = var1.getlocal(11).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("Description1"), Py.newInteger(15), Py.newInteger(70), Py.newInteger(320), Py.newInteger(80), Py.newInteger(196611), PyString.fromInterned("[ProductName] setup was interrupted.  Your system has not been modified.  To install this program at a later time, please run the installation again.")};
      var10000.__call__(var2, var10);
      var1.setline(485);
      var10000 = var1.getlocal(11).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("Description2"), Py.newInteger(15), Py.newInteger(155), Py.newInteger(320), Py.newInteger(20), Py.newInteger(196611), PyString.fromInterned("Click the Finish button to exit the Installer.")};
      var10000.__call__(var2, var10);
      var1.setline(487);
      var10000 = var1.getlocal(11).__getattr__("next");
      var10 = new PyObject[]{PyString.fromInterned("Finish"), PyString.fromInterned("Cancel"), PyString.fromInterned("Finish")};
      var4 = new String[]{"name"};
      var10000 = var10000.__call__(var2, var10, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(488);
      var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("Exit"));
      var1.setline(490);
      var10000 = var1.getglobal("PyDialog");
      var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("ExitDialog"), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(7), var1.getlocal(6), PyString.fromInterned("Finish"), PyString.fromInterned("Finish"), PyString.fromInterned("Finish")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(12, var3);
      var3 = null;
      var1.setline(492);
      var1.getlocal(12).__getattr__("title").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Completing the [ProductName] Installer"));
      var1.setline(493);
      var10000 = var1.getlocal(12).__getattr__("back");
      var10 = new PyObject[]{PyString.fromInterned("< Back"), PyString.fromInterned("Finish"), Py.newInteger(0)};
      var4 = new String[]{"active"};
      var10000.__call__(var2, var10, var4);
      var3 = null;
      var1.setline(494);
      var10000 = var1.getlocal(12).__getattr__("cancel");
      var10 = new PyObject[]{PyString.fromInterned("Cancel"), PyString.fromInterned("Back"), Py.newInteger(0)};
      var4 = new String[]{"active"};
      var10000.__call__(var2, var10, var4);
      var3 = null;
      var1.setline(495);
      var10000 = var1.getlocal(12).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("Description"), Py.newInteger(15), Py.newInteger(235), Py.newInteger(320), Py.newInteger(20), Py.newInteger(196611), PyString.fromInterned("Click the Finish button to exit the Installer.")};
      var10000.__call__(var2, var10);
      var1.setline(497);
      var10000 = var1.getlocal(12).__getattr__("next");
      var10 = new PyObject[]{PyString.fromInterned("Finish"), PyString.fromInterned("Cancel"), PyString.fromInterned("Finish")};
      var4 = new String[]{"name"};
      var10000 = var10000.__call__(var2, var10, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(498);
      var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("Return"));
      var1.setline(502);
      var10000 = var1.getglobal("PyDialog");
      var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("FilesInUse"), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), Py.newInteger(19), var1.getlocal(6), PyString.fromInterned("Retry"), PyString.fromInterned("Retry"), PyString.fromInterned("Retry"), var1.getglobal("False")};
      var4 = new String[]{"bitmap"};
      var10000 = var10000.__call__(var2, var10, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(13, var3);
      var3 = null;
      var1.setline(507);
      var10000 = var1.getlocal(13).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("Title"), Py.newInteger(15), Py.newInteger(6), Py.newInteger(200), Py.newInteger(15), Py.newInteger(196611), PyString.fromInterned("{\\DlgFontBold8}Files in Use")};
      var10000.__call__(var2, var10);
      var1.setline(509);
      var10000 = var1.getlocal(13).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("Description"), Py.newInteger(20), Py.newInteger(23), Py.newInteger(280), Py.newInteger(20), Py.newInteger(196611), PyString.fromInterned("Some files that need to be updated are currently in use.")};
      var10000.__call__(var2, var10);
      var1.setline(511);
      var10000 = var1.getlocal(13).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("Text"), Py.newInteger(20), Py.newInteger(55), Py.newInteger(330), Py.newInteger(50), Py.newInteger(3), PyString.fromInterned("The following applications are using files that need to be updated by this setup. Close these applications and then click Retry to continue the installation or Cancel to exit it.")};
      var10000.__call__(var2, var10);
      var1.setline(513);
      var10000 = var1.getlocal(13).__getattr__("control");
      var10 = new PyObject[]{PyString.fromInterned("List"), PyString.fromInterned("ListBox"), Py.newInteger(20), Py.newInteger(107), Py.newInteger(330), Py.newInteger(130), Py.newInteger(7), PyString.fromInterned("FileInUseProcess"), var1.getglobal("None"), var1.getglobal("None"), var1.getglobal("None")};
      var10000.__call__(var2, var10);
      var1.setline(515);
      var10000 = var1.getlocal(13).__getattr__("back");
      var10 = new PyObject[]{PyString.fromInterned("Exit"), PyString.fromInterned("Ignore"), PyString.fromInterned("Exit")};
      var4 = new String[]{"name"};
      var10000 = var10000.__call__(var2, var10, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(516);
      var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("Exit"));
      var1.setline(517);
      var10000 = var1.getlocal(13).__getattr__("next");
      var10 = new PyObject[]{PyString.fromInterned("Ignore"), PyString.fromInterned("Retry"), PyString.fromInterned("Ignore")};
      var4 = new String[]{"name"};
      var10000 = var10000.__call__(var2, var10, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(518);
      var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("Ignore"));
      var1.setline(519);
      var10000 = var1.getlocal(13).__getattr__("cancel");
      var10 = new PyObject[]{PyString.fromInterned("Retry"), PyString.fromInterned("Exit"), PyString.fromInterned("Retry")};
      var4 = new String[]{"name"};
      var10000 = var10000.__call__(var2, var10, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(520);
      var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("Retry"));
      var1.setline(523);
      var10000 = var1.getglobal("Dialog");
      var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("ErrorDlg"), Py.newInteger(50), Py.newInteger(10), Py.newInteger(330), Py.newInteger(101), Py.newInteger(65543), var1.getlocal(6), PyString.fromInterned("ErrorText"), var1.getglobal("None"), var1.getglobal("None")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(14, var3);
      var3 = null;
      var1.setline(528);
      var10000 = var1.getlocal(14).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("ErrorText"), Py.newInteger(50), Py.newInteger(9), Py.newInteger(280), Py.newInteger(48), Py.newInteger(3), PyString.fromInterned("")};
      var10000.__call__(var2, var10);
      var1.setline(530);
      var10000 = var1.getlocal(14).__getattr__("pushbutton");
      var10 = new PyObject[]{PyString.fromInterned("N"), Py.newInteger(120), Py.newInteger(72), Py.newInteger(81), Py.newInteger(21), Py.newInteger(3), PyString.fromInterned("No"), var1.getglobal("None")};
      var10000.__call__(var2, var10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("ErrorNo"));
      var1.setline(531);
      var10000 = var1.getlocal(14).__getattr__("pushbutton");
      var10 = new PyObject[]{PyString.fromInterned("Y"), Py.newInteger(240), Py.newInteger(72), Py.newInteger(81), Py.newInteger(21), Py.newInteger(3), PyString.fromInterned("Yes"), var1.getglobal("None")};
      var10000.__call__(var2, var10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("ErrorYes"));
      var1.setline(532);
      var10000 = var1.getlocal(14).__getattr__("pushbutton");
      var10 = new PyObject[]{PyString.fromInterned("A"), Py.newInteger(0), Py.newInteger(72), Py.newInteger(81), Py.newInteger(21), Py.newInteger(3), PyString.fromInterned("Abort"), var1.getglobal("None")};
      var10000.__call__(var2, var10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("ErrorAbort"));
      var1.setline(533);
      var10000 = var1.getlocal(14).__getattr__("pushbutton");
      var10 = new PyObject[]{PyString.fromInterned("C"), Py.newInteger(42), Py.newInteger(72), Py.newInteger(81), Py.newInteger(21), Py.newInteger(3), PyString.fromInterned("Cancel"), var1.getglobal("None")};
      var10000.__call__(var2, var10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("ErrorCancel"));
      var1.setline(534);
      var10000 = var1.getlocal(14).__getattr__("pushbutton");
      var10 = new PyObject[]{PyString.fromInterned("I"), Py.newInteger(81), Py.newInteger(72), Py.newInteger(81), Py.newInteger(21), Py.newInteger(3), PyString.fromInterned("Ignore"), var1.getglobal("None")};
      var10000.__call__(var2, var10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("ErrorIgnore"));
      var1.setline(535);
      var10000 = var1.getlocal(14).__getattr__("pushbutton");
      var10 = new PyObject[]{PyString.fromInterned("O"), Py.newInteger(159), Py.newInteger(72), Py.newInteger(81), Py.newInteger(21), Py.newInteger(3), PyString.fromInterned("Ok"), var1.getglobal("None")};
      var10000.__call__(var2, var10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("ErrorOk"));
      var1.setline(536);
      var10000 = var1.getlocal(14).__getattr__("pushbutton");
      var10 = new PyObject[]{PyString.fromInterned("R"), Py.newInteger(198), Py.newInteger(72), Py.newInteger(81), Py.newInteger(21), Py.newInteger(3), PyString.fromInterned("Retry"), var1.getglobal("None")};
      var10000.__call__(var2, var10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("ErrorRetry"));
      var1.setline(540);
      var10000 = var1.getglobal("Dialog");
      var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("CancelDlg"), Py.newInteger(50), Py.newInteger(10), Py.newInteger(260), Py.newInteger(85), Py.newInteger(3), var1.getlocal(6), PyString.fromInterned("No"), PyString.fromInterned("No"), PyString.fromInterned("No")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(15, var3);
      var3 = null;
      var1.setline(542);
      var10000 = var1.getlocal(15).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("Text"), Py.newInteger(48), Py.newInteger(15), Py.newInteger(194), Py.newInteger(30), Py.newInteger(3), PyString.fromInterned("Are you sure you want to cancel [ProductName] installation?")};
      var10000.__call__(var2, var10);
      var1.setline(546);
      var10000 = var1.getlocal(15).__getattr__("pushbutton");
      var10 = new PyObject[]{PyString.fromInterned("Yes"), Py.newInteger(72), Py.newInteger(57), Py.newInteger(56), Py.newInteger(17), Py.newInteger(3), PyString.fromInterned("Yes"), PyString.fromInterned("No")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(547);
      var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("Exit"));
      var1.setline(549);
      var10000 = var1.getlocal(15).__getattr__("pushbutton");
      var10 = new PyObject[]{PyString.fromInterned("No"), Py.newInteger(132), Py.newInteger(57), Py.newInteger(56), Py.newInteger(17), Py.newInteger(3), PyString.fromInterned("No"), PyString.fromInterned("Yes")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(550);
      var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("Return"));
      var1.setline(554);
      var10000 = var1.getglobal("Dialog");
      var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("WaitForCostingDlg"), Py.newInteger(50), Py.newInteger(10), Py.newInteger(260), Py.newInteger(85), var1.getlocal(7), var1.getlocal(6), PyString.fromInterned("Return"), PyString.fromInterned("Return"), PyString.fromInterned("Return")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(16, var3);
      var3 = null;
      var1.setline(556);
      var10000 = var1.getlocal(16).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("Text"), Py.newInteger(48), Py.newInteger(15), Py.newInteger(194), Py.newInteger(30), Py.newInteger(3), PyString.fromInterned("Please wait while the installer finishes determining your disk space requirements.")};
      var10000.__call__(var2, var10);
      var1.setline(558);
      var10000 = var1.getlocal(16).__getattr__("pushbutton");
      var10 = new PyObject[]{PyString.fromInterned("Return"), Py.newInteger(102), Py.newInteger(57), Py.newInteger(56), Py.newInteger(17), Py.newInteger(3), PyString.fromInterned("Return"), var1.getglobal("None")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(559);
      var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("Exit"));
      var1.setline(563);
      var10000 = var1.getglobal("PyDialog");
      var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("PrepareDlg"), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(8), var1.getlocal(6), PyString.fromInterned("Cancel"), PyString.fromInterned("Cancel"), PyString.fromInterned("Cancel")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(17, var3);
      var3 = null;
      var1.setline(565);
      var10000 = var1.getlocal(17).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("Description"), Py.newInteger(15), Py.newInteger(70), Py.newInteger(320), Py.newInteger(40), Py.newInteger(196611), PyString.fromInterned("Please wait while the Installer prepares to guide you through the installation.")};
      var10000.__call__(var2, var10);
      var1.setline(567);
      var1.getlocal(17).__getattr__("title").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Welcome to the [ProductName] Installer"));
      var1.setline(568);
      var10000 = var1.getlocal(17).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("ActionText"), Py.newInteger(15), Py.newInteger(110), Py.newInteger(320), Py.newInteger(20), Py.newInteger(196611), PyString.fromInterned("Pondering...")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(569);
      var1.getlocal(10).__getattr__("mapping").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ActionText"), (PyObject)PyString.fromInterned("Text"));
      var1.setline(570);
      var10000 = var1.getlocal(17).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("ActionData"), Py.newInteger(15), Py.newInteger(135), Py.newInteger(320), Py.newInteger(30), Py.newInteger(196611), var1.getglobal("None")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(571);
      var1.getlocal(10).__getattr__("mapping").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ActionData"), (PyObject)PyString.fromInterned("Text"));
      var1.setline(572);
      var10000 = var1.getlocal(17).__getattr__("back");
      var10 = new PyObject[]{PyString.fromInterned("Back"), var1.getglobal("None"), Py.newInteger(0)};
      var4 = new String[]{"active"};
      var10000.__call__(var2, var10, var4);
      var3 = null;
      var1.setline(573);
      var10000 = var1.getlocal(17).__getattr__("next");
      var10 = new PyObject[]{PyString.fromInterned("Next"), var1.getglobal("None"), Py.newInteger(0)};
      var4 = new String[]{"active"};
      var10000.__call__(var2, var10, var4);
      var3 = null;
      var1.setline(574);
      var3 = var1.getlocal(17).__getattr__("cancel").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cancel"), (PyObject)var1.getglobal("None"));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(575);
      var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SpawnDialog"), (PyObject)PyString.fromInterned("CancelDlg"));
      var1.setline(579);
      var10000 = var1.getglobal("PyDialog");
      var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("SelectFeaturesDlg"), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(7), var1.getlocal(6), PyString.fromInterned("Next"), PyString.fromInterned("Next"), PyString.fromInterned("Cancel")};
      var3 = var10000.__call__(var2, var10);
      var1.setlocal(18, var3);
      var3 = null;
      var1.setline(581);
      var1.getlocal(18).__getattr__("title").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Select Python Installations"));
      var1.setline(583);
      var10000 = var1.getlocal(18).__getattr__("text");
      var10 = new PyObject[]{PyString.fromInterned("Hint"), Py.newInteger(15), Py.newInteger(30), Py.newInteger(300), Py.newInteger(20), Py.newInteger(3), PyString.fromInterned("Select the Python locations where %s should be installed.")._mod(var1.getlocal(0).__getattr__("distribution").__getattr__("get_fullname").__call__(var2))};
      var10000.__call__(var2, var10);
      var1.setline(587);
      var10000 = var1.getlocal(18).__getattr__("back");
      var10 = new PyObject[]{PyString.fromInterned("< Back"), var1.getglobal("None"), Py.newInteger(0)};
      var4 = new String[]{"active"};
      var10000.__call__(var2, var10, var4);
      var3 = null;
      var1.setline(588);
      var3 = var1.getlocal(18).__getattr__("next").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Next >"), (PyObject)PyString.fromInterned("Cancel"));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(589);
      var7 = Py.newInteger(1);
      var1.setlocal(19, var7);
      var3 = null;
      var1.setline(590);
      var10000 = var1.getlocal(10).__getattr__("event");
      var10 = new PyObject[]{PyString.fromInterned("[TARGETDIR]"), PyString.fromInterned("[SourceDir]"), var1.getlocal(19)};
      var4 = new String[]{"ordering"};
      var10000.__call__(var2, var10, var4);
      var3 = null;
      var1.setline(591);
      var3 = var1.getlocal(0).__getattr__("versions")._add(new PyList(new PyObject[]{var1.getlocal(0).__getattr__("other_version")})).__iter__();

      while(true) {
         var1.setline(591);
         PyObject var11 = var3.__iternext__();
         if (var11 == null) {
            var1.setline(596);
            var10000 = var1.getlocal(10).__getattr__("event");
            var10 = new PyObject[]{PyString.fromInterned("SpawnWaitDialog"), PyString.fromInterned("WaitForCostingDlg"), var1.getlocal(19)._add(Py.newInteger(1))};
            var4 = new String[]{"ordering"};
            var10000.__call__(var2, var10, var4);
            var3 = null;
            var1.setline(597);
            var10000 = var1.getlocal(10).__getattr__("event");
            var10 = new PyObject[]{PyString.fromInterned("EndDialog"), PyString.fromInterned("Return"), var1.getlocal(19)._add(Py.newInteger(2))};
            var4 = new String[]{"ordering"};
            var10000.__call__(var2, var10, var4);
            var3 = null;
            var1.setline(598);
            var3 = var1.getlocal(18).__getattr__("cancel").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cancel"), (PyObject)PyString.fromInterned("Features"));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(599);
            var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SpawnDialog"), (PyObject)PyString.fromInterned("CancelDlg"));
            var1.setline(601);
            var10000 = var1.getlocal(18).__getattr__("control");
            var10 = new PyObject[]{PyString.fromInterned("Features"), PyString.fromInterned("SelectionTree"), Py.newInteger(15), Py.newInteger(60), Py.newInteger(300), Py.newInteger(120), Py.newInteger(3), PyString.fromInterned("FEATURE"), var1.getglobal("None"), PyString.fromInterned("PathEdit"), var1.getglobal("None")};
            var3 = var10000.__call__(var2, var10);
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(603);
            var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[FEATURE_SELECTED]"), (PyObject)PyString.fromInterned("1"));
            var1.setline(604);
            var3 = var1.getlocal(0).__getattr__("other_version");
            var1.setlocal(21, var3);
            var3 = null;
            var1.setline(605);
            var3 = PyString.fromInterned("FEATURE_SELECTED AND &Python%s=3")._mod(var1.getlocal(21));
            var1.setlocal(22, var3);
            var3 = null;
            var1.setline(606);
            var3 = PyString.fromInterned("FEATURE_SELECTED AND &Python%s<>3")._mod(var1.getlocal(21));
            var1.setlocal(23, var3);
            var3 = null;
            var1.setline(608);
            var10000 = var1.getlocal(18).__getattr__("text");
            var10 = new PyObject[]{PyString.fromInterned("Other"), Py.newInteger(15), Py.newInteger(200), Py.newInteger(300), Py.newInteger(15), Py.newInteger(3), PyString.fromInterned("Provide an alternate Python location")};
            var3 = var10000.__call__(var2, var10);
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(610);
            var1.getlocal(10).__getattr__("condition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Enable"), (PyObject)var1.getlocal(22));
            var1.setline(611);
            var1.getlocal(10).__getattr__("condition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Show"), (PyObject)var1.getlocal(22));
            var1.setline(612);
            var1.getlocal(10).__getattr__("condition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Disable"), (PyObject)var1.getlocal(23));
            var1.setline(613);
            var1.getlocal(10).__getattr__("condition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Hide"), (PyObject)var1.getlocal(23));
            var1.setline(615);
            var10000 = var1.getlocal(18).__getattr__("control");
            var10 = new PyObject[]{PyString.fromInterned("PathEdit"), PyString.fromInterned("PathEdit"), Py.newInteger(15), Py.newInteger(215), Py.newInteger(300), Py.newInteger(16), Py.newInteger(1), PyString.fromInterned("TARGETDIR")._add(var1.getlocal(21)), var1.getglobal("None"), PyString.fromInterned("Next"), var1.getglobal("None")};
            var3 = var10000.__call__(var2, var10);
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(617);
            var1.getlocal(10).__getattr__("condition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Enable"), (PyObject)var1.getlocal(22));
            var1.setline(618);
            var1.getlocal(10).__getattr__("condition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Show"), (PyObject)var1.getlocal(22));
            var1.setline(619);
            var1.getlocal(10).__getattr__("condition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Disable"), (PyObject)var1.getlocal(23));
            var1.setline(620);
            var1.getlocal(10).__getattr__("condition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Hide"), (PyObject)var1.getlocal(23));
            var1.setline(624);
            var10000 = var1.getglobal("PyDialog");
            var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("DiskCostDlg"), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(7), var1.getlocal(6), PyString.fromInterned("OK"), PyString.fromInterned("OK"), PyString.fromInterned("OK"), var1.getglobal("False")};
            var4 = new String[]{"bitmap"};
            var10000 = var10000.__call__(var2, var10, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(24, var3);
            var3 = null;
            var1.setline(626);
            var10000 = var1.getlocal(24).__getattr__("text");
            var10 = new PyObject[]{PyString.fromInterned("Title"), Py.newInteger(15), Py.newInteger(6), Py.newInteger(200), Py.newInteger(15), Py.newInteger(196611), PyString.fromInterned("{\\DlgFontBold8}Disk Space Requirements")};
            var10000.__call__(var2, var10);
            var1.setline(628);
            var10000 = var1.getlocal(24).__getattr__("text");
            var10 = new PyObject[]{PyString.fromInterned("Description"), Py.newInteger(20), Py.newInteger(20), Py.newInteger(280), Py.newInteger(20), Py.newInteger(196611), PyString.fromInterned("The disk space required for the installation of the selected features.")};
            var10000.__call__(var2, var10);
            var1.setline(630);
            var10000 = var1.getlocal(24).__getattr__("text");
            var10 = new PyObject[]{PyString.fromInterned("Text"), Py.newInteger(20), Py.newInteger(53), Py.newInteger(330), Py.newInteger(60), Py.newInteger(3), PyString.fromInterned("The highlighted volumes (if any) do not have enough disk space available for the currently selected features.  You can either remove some files from the highlighted volumes, or choose to install less features onto local drive(s), or select different destination drive(s).")};
            var10000.__call__(var2, var10);
            var1.setline(636);
            var10000 = var1.getlocal(24).__getattr__("control");
            var10 = new PyObject[]{PyString.fromInterned("VolumeList"), PyString.fromInterned("VolumeCostList"), Py.newInteger(20), Py.newInteger(100), Py.newInteger(330), Py.newInteger(150), Py.newInteger(393223), var1.getglobal("None"), PyString.fromInterned("{120}{70}{70}{70}{70}"), var1.getglobal("None"), var1.getglobal("None")};
            var10000.__call__(var2, var10);
            var1.setline(638);
            var1.getlocal(24).__getattr__("xbutton").__call__(var2, PyString.fromInterned("OK"), PyString.fromInterned("Ok"), var1.getglobal("None"), Py.newFloat(0.5)).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EndDialog"), (PyObject)PyString.fromInterned("Return"));
            var1.setline(651);
            var10000 = var1.getglobal("PyDialog");
            var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("WhichUsersDlg"), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(7), var1.getlocal(6), PyString.fromInterned("AdminInstall"), PyString.fromInterned("Next"), PyString.fromInterned("Cancel")};
            var3 = var10000.__call__(var2, var10);
            var1.setlocal(25, var3);
            var3 = null;
            var1.setline(653);
            var1.getlocal(25).__getattr__("title").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Select whether to install [ProductName] for all users of this computer."));
            var1.setline(655);
            var10000 = var1.getlocal(25).__getattr__("radiogroup");
            var10 = new PyObject[]{PyString.fromInterned("AdminInstall"), Py.newInteger(15), Py.newInteger(60), Py.newInteger(260), Py.newInteger(50), Py.newInteger(3), PyString.fromInterned("WhichUsers"), PyString.fromInterned(""), PyString.fromInterned("Next")};
            var3 = var10000.__call__(var2, var10);
            var1.setlocal(26, var3);
            var3 = null;
            var1.setline(657);
            var10000 = var1.getlocal(26).__getattr__("add");
            var10 = new PyObject[]{PyString.fromInterned("ALL"), Py.newInteger(0), Py.newInteger(5), Py.newInteger(150), Py.newInteger(20), PyString.fromInterned("Install for all users")};
            var10000.__call__(var2, var10);
            var1.setline(658);
            var10000 = var1.getlocal(26).__getattr__("add");
            var10 = new PyObject[]{PyString.fromInterned("JUSTME"), Py.newInteger(0), Py.newInteger(25), Py.newInteger(150), Py.newInteger(20), PyString.fromInterned("Install just for me")};
            var10000.__call__(var2, var10);
            var1.setline(660);
            var10000 = var1.getlocal(25).__getattr__("back");
            var10 = new PyObject[]{PyString.fromInterned("Back"), var1.getglobal("None"), Py.newInteger(0)};
            var4 = new String[]{"active"};
            var10000.__call__(var2, var10, var4);
            var3 = null;
            var1.setline(662);
            var3 = var1.getlocal(25).__getattr__("next").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Next >"), (PyObject)PyString.fromInterned("Cancel"));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(663);
            var1.getlocal(10).__getattr__("event").__call__(var2, PyString.fromInterned("[ALLUSERS]"), PyString.fromInterned("1"), PyString.fromInterned("WhichUsers=\"ALL\""), Py.newInteger(1));
            var1.setline(664);
            var10000 = var1.getlocal(10).__getattr__("event");
            var10 = new PyObject[]{PyString.fromInterned("EndDialog"), PyString.fromInterned("Return"), Py.newInteger(2)};
            var4 = new String[]{"ordering"};
            var10000.__call__(var2, var10, var4);
            var3 = null;
            var1.setline(666);
            var3 = var1.getlocal(25).__getattr__("cancel").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cancel"), (PyObject)PyString.fromInterned("AdminInstall"));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(667);
            var1.getlocal(10).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SpawnDialog"), (PyObject)PyString.fromInterned("CancelDlg"));
            var1.setline(671);
            var10000 = var1.getglobal("PyDialog");
            var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("ProgressDlg"), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(8), var1.getlocal(6), PyString.fromInterned("Cancel"), PyString.fromInterned("Cancel"), PyString.fromInterned("Cancel"), var1.getglobal("False")};
            var4 = new String[]{"bitmap"};
            var10000 = var10000.__call__(var2, var10, var4);
            var3 = null;
            var3 = var10000;
            var1.setlocal(27, var3);
            var3 = null;
            var1.setline(673);
            var10000 = var1.getlocal(27).__getattr__("text");
            var10 = new PyObject[]{PyString.fromInterned("Title"), Py.newInteger(20), Py.newInteger(15), Py.newInteger(200), Py.newInteger(15), Py.newInteger(196611), PyString.fromInterned("{\\DlgFontBold8}[Progress1] [ProductName]")};
            var10000.__call__(var2, var10);
            var1.setline(675);
            var10000 = var1.getlocal(27).__getattr__("text");
            var10 = new PyObject[]{PyString.fromInterned("Text"), Py.newInteger(35), Py.newInteger(65), Py.newInteger(300), Py.newInteger(30), Py.newInteger(3), PyString.fromInterned("Please wait while the Installer [Progress2] [ProductName]. This may take several minutes.")};
            var10000.__call__(var2, var10);
            var1.setline(678);
            var10000 = var1.getlocal(27).__getattr__("text");
            var10 = new PyObject[]{PyString.fromInterned("StatusLabel"), Py.newInteger(35), Py.newInteger(100), Py.newInteger(35), Py.newInteger(20), Py.newInteger(3), PyString.fromInterned("Status:")};
            var10000.__call__(var2, var10);
            var1.setline(680);
            var10000 = var1.getlocal(27).__getattr__("text");
            var10 = new PyObject[]{PyString.fromInterned("ActionText"), Py.newInteger(70), Py.newInteger(100), var1.getlocal(4)._sub(Py.newInteger(70)), Py.newInteger(20), Py.newInteger(3), PyString.fromInterned("Pondering...")};
            var3 = var10000.__call__(var2, var10);
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(681);
            var1.getlocal(10).__getattr__("mapping").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ActionText"), (PyObject)PyString.fromInterned("Text"));
            var1.setline(686);
            var10000 = var1.getlocal(27).__getattr__("control");
            var10 = new PyObject[]{PyString.fromInterned("ProgressBar"), PyString.fromInterned("ProgressBar"), Py.newInteger(35), Py.newInteger(120), Py.newInteger(300), Py.newInteger(10), Py.newInteger(65537), var1.getglobal("None"), PyString.fromInterned("Progress done"), var1.getglobal("None"), var1.getglobal("None")};
            var3 = var10000.__call__(var2, var10);
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(688);
            var1.getlocal(10).__getattr__("mapping").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SetProgress"), (PyObject)PyString.fromInterned("Progress"));
            var1.setline(690);
            var10000 = var1.getlocal(27).__getattr__("back");
            var10 = new PyObject[]{PyString.fromInterned("< Back"), PyString.fromInterned("Next"), var1.getglobal("False")};
            var4 = new String[]{"active"};
            var10000.__call__(var2, var10, var4);
            var3 = null;
            var1.setline(691);
            var10000 = var1.getlocal(27).__getattr__("next");
            var10 = new PyObject[]{PyString.fromInterned("Next >"), PyString.fromInterned("Cancel"), var1.getglobal("False")};
            var4 = new String[]{"active"};
            var10000.__call__(var2, var10, var4);
            var3 = null;
            var1.setline(692);
            var1.getlocal(27).__getattr__("cancel").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cancel"), (PyObject)PyString.fromInterned("Back")).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SpawnDialog"), (PyObject)PyString.fromInterned("CancelDlg"));
            var1.setline(696);
            var10000 = var1.getglobal("PyDialog");
            var10 = new PyObject[]{var1.getlocal(1), PyString.fromInterned("MaintenanceTypeDlg"), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(7), var1.getlocal(6), PyString.fromInterned("Next"), PyString.fromInterned("Next"), PyString.fromInterned("Cancel")};
            var3 = var10000.__call__(var2, var10);
            var1.setlocal(28, var3);
            var3 = null;
            var1.setline(698);
            var1.getlocal(28).__getattr__("title").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Welcome to the [ProductName] Setup Wizard"));
            var1.setline(699);
            var10000 = var1.getlocal(28).__getattr__("text");
            var10 = new PyObject[]{PyString.fromInterned("BodyText"), Py.newInteger(15), Py.newInteger(63), Py.newInteger(330), Py.newInteger(42), Py.newInteger(3), PyString.fromInterned("Select whether you want to repair or remove [ProductName].")};
            var10000.__call__(var2, var10);
            var1.setline(701);
            var10000 = var1.getlocal(28).__getattr__("radiogroup");
            var10 = new PyObject[]{PyString.fromInterned("RepairRadioGroup"), Py.newInteger(15), Py.newInteger(108), Py.newInteger(330), Py.newInteger(60), Py.newInteger(3), PyString.fromInterned("MaintenanceForm_Action"), PyString.fromInterned(""), PyString.fromInterned("Next")};
            var3 = var10000.__call__(var2, var10);
            var1.setlocal(26, var3);
            var3 = null;
            var1.setline(704);
            var10000 = var1.getlocal(26).__getattr__("add");
            var10 = new PyObject[]{PyString.fromInterned("Repair"), Py.newInteger(0), Py.newInteger(18), Py.newInteger(200), Py.newInteger(17), PyString.fromInterned("&Repair [ProductName]")};
            var10000.__call__(var2, var10);
            var1.setline(705);
            var10000 = var1.getlocal(26).__getattr__("add");
            var10 = new PyObject[]{PyString.fromInterned("Remove"), Py.newInteger(0), Py.newInteger(36), Py.newInteger(200), Py.newInteger(17), PyString.fromInterned("Re&move [ProductName]")};
            var10000.__call__(var2, var10);
            var1.setline(707);
            var10000 = var1.getlocal(28).__getattr__("back");
            var10 = new PyObject[]{PyString.fromInterned("< Back"), var1.getglobal("None"), var1.getglobal("False")};
            var4 = new String[]{"active"};
            var10000.__call__(var2, var10, var4);
            var3 = null;
            var1.setline(708);
            var3 = var1.getlocal(28).__getattr__("next").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Finish"), (PyObject)PyString.fromInterned("Cancel"));
            var1.setlocal(10, var3);
            var3 = null;
            var1.setline(716);
            var1.getlocal(10).__getattr__("event").__call__(var2, PyString.fromInterned("[REINSTALL]"), PyString.fromInterned("ALL"), PyString.fromInterned("MaintenanceForm_Action=\"Repair\""), Py.newInteger(5));
            var1.setline(717);
            var1.getlocal(10).__getattr__("event").__call__(var2, PyString.fromInterned("[Progress1]"), PyString.fromInterned("Repairing"), PyString.fromInterned("MaintenanceForm_Action=\"Repair\""), Py.newInteger(6));
            var1.setline(718);
            var1.getlocal(10).__getattr__("event").__call__(var2, PyString.fromInterned("[Progress2]"), PyString.fromInterned("repairs"), PyString.fromInterned("MaintenanceForm_Action=\"Repair\""), Py.newInteger(7));
            var1.setline(719);
            var1.getlocal(10).__getattr__("event").__call__(var2, PyString.fromInterned("Reinstall"), PyString.fromInterned("ALL"), PyString.fromInterned("MaintenanceForm_Action=\"Repair\""), Py.newInteger(8));
            var1.setline(723);
            var1.getlocal(10).__getattr__("event").__call__(var2, PyString.fromInterned("[REMOVE]"), PyString.fromInterned("ALL"), PyString.fromInterned("MaintenanceForm_Action=\"Remove\""), Py.newInteger(11));
            var1.setline(724);
            var1.getlocal(10).__getattr__("event").__call__(var2, PyString.fromInterned("[Progress1]"), PyString.fromInterned("Removing"), PyString.fromInterned("MaintenanceForm_Action=\"Remove\""), Py.newInteger(12));
            var1.setline(725);
            var1.getlocal(10).__getattr__("event").__call__(var2, PyString.fromInterned("[Progress2]"), PyString.fromInterned("removes"), PyString.fromInterned("MaintenanceForm_Action=\"Remove\""), Py.newInteger(13));
            var1.setline(726);
            var1.getlocal(10).__getattr__("event").__call__(var2, PyString.fromInterned("Remove"), PyString.fromInterned("ALL"), PyString.fromInterned("MaintenanceForm_Action=\"Remove\""), Py.newInteger(14));
            var1.setline(729);
            var1.getlocal(10).__getattr__("event").__call__(var2, PyString.fromInterned("EndDialog"), PyString.fromInterned("Return"), PyString.fromInterned("MaintenanceForm_Action<>\"Change\""), Py.newInteger(20));
            var1.setline(732);
            var1.getlocal(28).__getattr__("cancel").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Cancel"), (PyObject)PyString.fromInterned("RepairRadioGroup")).__getattr__("event").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SpawnDialog"), (PyObject)PyString.fromInterned("CancelDlg"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(20, var11);
         var1.setline(592);
         PyObject var5 = var1.getlocal(19);
         var5 = var5._iadd(Py.newInteger(1));
         var1.setlocal(19, var5);
         var1.setline(593);
         var10000 = var1.getlocal(10).__getattr__("event");
         PyObject[] var9 = new PyObject[]{PyString.fromInterned("[TARGETDIR]"), PyString.fromInterned("[TARGETDIR%s]")._mod(var1.getlocal(20)), PyString.fromInterned("FEATURE_SELECTED AND &Python%s=3")._mod(var1.getlocal(20)), var1.getlocal(19)};
         String[] var6 = new String[]{"ordering"};
         var10000.__call__(var2, var9, var6);
         var5 = null;
      }
   }

   public PyObject get_installer_filename$16(PyFrame var1, ThreadState var2) {
      var1.setline(736);
      PyObject var3;
      if (var1.getlocal(0).__getattr__("target_version").__nonzero__()) {
         var1.setline(737);
         var3 = PyString.fromInterned("%s.%s-py%s.msi")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("plat_name"), var1.getlocal(0).__getattr__("target_version")}));
         var1.setlocal(2, var3);
         var3 = null;
      } else {
         var1.setline(740);
         var3 = PyString.fromInterned("%s.%s.msi")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("plat_name")}));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(741);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("dist_dir"), var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(742);
      var3 = var1.getlocal(3);
      var1.f_lasti = -1;
      return var3;
   }

   public bdist_msi$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      PyDialog$1 = Py.newCode(0, var2, var1, "PyDialog", 23, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "args", "kw", "ruler"};
      __init__$2 = Py.newCode(3, var2, var1, "__init__", 27, true, true, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "title"};
      title$3 = Py.newCode(2, var2, var1, "title", 36, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "title", "next", "name", "active", "flags"};
      back$4 = Py.newCode(5, var2, var1, "back", 43, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "title", "next", "name", "active", "flags"};
      cancel$5 = Py.newCode(5, var2, var1, "cancel", 54, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "title", "next", "name", "active", "flags"};
      next$6 = Py.newCode(5, var2, var1, "next", 65, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name", "title", "next", "xpos"};
      xbutton$7 = Py.newCode(5, var2, var1, "xbutton", 76, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      bdist_msi$8 = Py.newCode(0, var2, var1, "bdist_msi", 84, false, false, self, 8, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$9 = Py.newCode(1, var2, var1, "initialize_options", 126, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bdist_base", "short_version", "script"};
      finalize_options$10 = Py.newCode(1, var2, var1, "finalize_options", 139, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "install", "install_lib", "target_version", "plat_specifier", "build", "fullname", "installer_name", "metadata", "author", "version", "sversion", "product_name", "props", "email", "tup"};
      run$11 = Py.newCode(1, var2, var1, "run", 180, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "db", "cab", "rootdir", "root", "f", "items", "version", "target", "name", "default", "desc", "title", "level", "dir", "seen", "feature", "todo", "file", "afile", "short", "newdir", "key"};
      add_files$12 = Py.newCode(1, var2, var1, "add_files", 271, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "start", "ver", "install_path", "machine_reg", "user_reg", "machine_prop", "user_prop", "machine_action", "user_action", "exe_action", "target_dir_prop", "exe_prop", "Type"};
      add_find_python$13 = Py.newCode(1, var2, var1, "add_find_python", 325, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "start", "ver", "install_action", "exe_prop", "scriptfn", "f"};
      add_scripts$14 = Py.newCode(1, var2, var1, "add_scripts", 379, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "db", "x", "y", "w", "h", "title", "modal", "modeless", "fatal", "c", "user_exit", "exit_dialog", "inuse", "error", "cancel", "costing", "prep", "seldlg", "order", "version", "ver", "install_other_cond", "dont_install_other_cond", "cost", "whichusers", "g", "progress", "maint"};
      add_ui$15 = Py.newCode(1, var2, var1, "add_ui", 417, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fullname", "base_name", "installer_name"};
      get_installer_filename$16 = Py.newCode(2, var2, var1, "get_installer_filename", 734, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new bdist_msi$py("distutils/command/bdist_msi$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(bdist_msi$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.PyDialog$1(var2, var3);
         case 2:
            return this.__init__$2(var2, var3);
         case 3:
            return this.title$3(var2, var3);
         case 4:
            return this.back$4(var2, var3);
         case 5:
            return this.cancel$5(var2, var3);
         case 6:
            return this.next$6(var2, var3);
         case 7:
            return this.xbutton$7(var2, var3);
         case 8:
            return this.bdist_msi$8(var2, var3);
         case 9:
            return this.initialize_options$9(var2, var3);
         case 10:
            return this.finalize_options$10(var2, var3);
         case 11:
            return this.run$11(var2, var3);
         case 12:
            return this.add_files$12(var2, var3);
         case 13:
            return this.add_find_python$13(var2, var3);
         case 14:
            return this.add_scripts$14(var2, var3);
         case 15:
            return this.add_ui$15(var2, var3);
         case 16:
            return this.get_installer_filename$16(var2, var3);
         default:
            return null;
      }
   }
}
