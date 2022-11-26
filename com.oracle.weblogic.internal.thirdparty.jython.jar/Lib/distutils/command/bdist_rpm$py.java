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
@Filename("distutils/command/bdist_rpm.py")
public class bdist_rpm$py extends PyFunctionTable implements PyRunnable {
   static bdist_rpm$py self;
   static final PyCode f$0;
   static final PyCode bdist_rpm$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode finalize_package_data$4;
   static final PyCode run$5;
   static final PyCode _dist_path$6;
   static final PyCode _make_spec_file$7;
   static final PyCode _format_changelog$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.bdist_rpm\n\nImplements the Distutils 'bdist_rpm' command (create RPM source and binary\ndistributions)."));
      var1.setline(4);
      PyString.fromInterned("distutils.command.bdist_rpm\n\nImplements the Distutils 'bdist_rpm' command (create RPM source and binary\ndistributions).");
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
      String[] var6 = new String[]{"Command"};
      PyObject[] var7 = imp.importFrom("distutils.core", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"DEBUG"};
      var7 = imp.importFrom("distutils.debug", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DEBUG", var4);
      var4 = null;
      var1.setline(14);
      var6 = new String[]{"write_file"};
      var7 = imp.importFrom("distutils.file_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("write_file", var4);
      var4 = null;
      var1.setline(15);
      var6 = new String[]{"get_python_version"};
      var7 = imp.importFrom("distutils.sysconfig", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("get_python_version", var4);
      var4 = null;
      var1.setline(16);
      var6 = new String[]{"DistutilsOptionError", "DistutilsPlatformError", "DistutilsFileError", "DistutilsExecError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var4 = var7[2];
      var1.setlocal("DistutilsFileError", var4);
      var4 = null;
      var4 = var7[3];
      var1.setlocal("DistutilsExecError", var4);
      var4 = null;
      var1.setline(18);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(20);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("bdist_rpm", var7, bdist_rpm$1);
      var1.setlocal("bdist_rpm", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject bdist_rpm$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(22);
      PyString var3 = PyString.fromInterned("create an RPM distribution");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(24);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("bdist-base="), var1.getname("None"), PyString.fromInterned("base directory for creating built distributions")}), new PyTuple(new PyObject[]{PyString.fromInterned("rpm-base="), var1.getname("None"), PyString.fromInterned("base directory for creating RPMs (defaults to \"rpm\" under --bdist-base; must be specified for RPM 2)")}), new PyTuple(new PyObject[]{PyString.fromInterned("dist-dir="), PyString.fromInterned("d"), PyString.fromInterned("directory to put final RPM files in (and .spec files if --spec-only)")}), new PyTuple(new PyObject[]{PyString.fromInterned("python="), var1.getname("None"), PyString.fromInterned("path to Python interpreter to hard-code in the .spec file (default: \"python\")")}), new PyTuple(new PyObject[]{PyString.fromInterned("fix-python"), var1.getname("None"), PyString.fromInterned("hard-code the exact path to the current Python interpreter in the .spec file")}), new PyTuple(new PyObject[]{PyString.fromInterned("spec-only"), var1.getname("None"), PyString.fromInterned("only regenerate spec file")}), new PyTuple(new PyObject[]{PyString.fromInterned("source-only"), var1.getname("None"), PyString.fromInterned("only generate source RPM")}), new PyTuple(new PyObject[]{PyString.fromInterned("binary-only"), var1.getname("None"), PyString.fromInterned("only generate binary RPM")}), new PyTuple(new PyObject[]{PyString.fromInterned("use-bzip2"), var1.getname("None"), PyString.fromInterned("use bzip2 instead of gzip to create source distribution")}), new PyTuple(new PyObject[]{PyString.fromInterned("distribution-name="), var1.getname("None"), PyString.fromInterned("name of the (Linux) distribution to which this RPM applies (*not* the name of the module distribution!)")}), new PyTuple(new PyObject[]{PyString.fromInterned("group="), var1.getname("None"), PyString.fromInterned("package classification [default: \"Development/Libraries\"]")}), new PyTuple(new PyObject[]{PyString.fromInterned("release="), var1.getname("None"), PyString.fromInterned("RPM release number")}), new PyTuple(new PyObject[]{PyString.fromInterned("serial="), var1.getname("None"), PyString.fromInterned("RPM serial number")}), new PyTuple(new PyObject[]{PyString.fromInterned("vendor="), var1.getname("None"), PyString.fromInterned("RPM \"vendor\" (eg. \"Joe Blow <joe@example.com>\") [default: maintainer or author from setup script]")}), new PyTuple(new PyObject[]{PyString.fromInterned("packager="), var1.getname("None"), PyString.fromInterned("RPM packager (eg. \"Jane Doe <jane@example.net>\")[default: vendor]")}), new PyTuple(new PyObject[]{PyString.fromInterned("doc-files="), var1.getname("None"), PyString.fromInterned("list of documentation files (space or comma-separated)")}), new PyTuple(new PyObject[]{PyString.fromInterned("changelog="), var1.getname("None"), PyString.fromInterned("RPM changelog")}), new PyTuple(new PyObject[]{PyString.fromInterned("icon="), var1.getname("None"), PyString.fromInterned("name of icon file")}), new PyTuple(new PyObject[]{PyString.fromInterned("provides="), var1.getname("None"), PyString.fromInterned("capabilities provided by this package")}), new PyTuple(new PyObject[]{PyString.fromInterned("requires="), var1.getname("None"), PyString.fromInterned("capabilities required by this package")}), new PyTuple(new PyObject[]{PyString.fromInterned("conflicts="), var1.getname("None"), PyString.fromInterned("capabilities which conflict with this package")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-requires="), var1.getname("None"), PyString.fromInterned("capabilities required to build this package")}), new PyTuple(new PyObject[]{PyString.fromInterned("obsoletes="), var1.getname("None"), PyString.fromInterned("capabilities made obsolete by this package")}), new PyTuple(new PyObject[]{PyString.fromInterned("no-autoreq"), var1.getname("None"), PyString.fromInterned("do not automatically calculate dependencies")}), new PyTuple(new PyObject[]{PyString.fromInterned("keep-temp"), PyString.fromInterned("k"), PyString.fromInterned("don't clean up RPM build directory")}), new PyTuple(new PyObject[]{PyString.fromInterned("no-keep-temp"), var1.getname("None"), PyString.fromInterned("clean up RPM build directory [default]")}), new PyTuple(new PyObject[]{PyString.fromInterned("use-rpm-opt-flags"), var1.getname("None"), PyString.fromInterned("compile with RPM_OPT_FLAGS when building from source RPM")}), new PyTuple(new PyObject[]{PyString.fromInterned("no-rpm-opt-flags"), var1.getname("None"), PyString.fromInterned("do not pass any RPM CFLAGS to compiler")}), new PyTuple(new PyObject[]{PyString.fromInterned("rpm3-mode"), var1.getname("None"), PyString.fromInterned("RPM 3 compatibility mode (default)")}), new PyTuple(new PyObject[]{PyString.fromInterned("rpm2-mode"), var1.getname("None"), PyString.fromInterned("RPM 2 compatibility mode")}), new PyTuple(new PyObject[]{PyString.fromInterned("prep-script="), var1.getname("None"), PyString.fromInterned("Specify a script for the PREP phase of RPM building")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-script="), var1.getname("None"), PyString.fromInterned("Specify a script for the BUILD phase of RPM building")}), new PyTuple(new PyObject[]{PyString.fromInterned("pre-install="), var1.getname("None"), PyString.fromInterned("Specify a script for the pre-INSTALL phase of RPM building")}), new PyTuple(new PyObject[]{PyString.fromInterned("install-script="), var1.getname("None"), PyString.fromInterned("Specify a script for the INSTALL phase of RPM building")}), new PyTuple(new PyObject[]{PyString.fromInterned("post-install="), var1.getname("None"), PyString.fromInterned("Specify a script for the post-INSTALL phase of RPM building")}), new PyTuple(new PyObject[]{PyString.fromInterned("pre-uninstall="), var1.getname("None"), PyString.fromInterned("Specify a script for the pre-UNINSTALL phase of RPM building")}), new PyTuple(new PyObject[]{PyString.fromInterned("post-uninstall="), var1.getname("None"), PyString.fromInterned("Specify a script for the post-UNINSTALL phase of RPM building")}), new PyTuple(new PyObject[]{PyString.fromInterned("clean-script="), var1.getname("None"), PyString.fromInterned("Specify a script for the CLEAN phase of RPM building")}), new PyTuple(new PyObject[]{PyString.fromInterned("verify-script="), var1.getname("None"), PyString.fromInterned("Specify a script for the VERIFY phase of the RPM build")}), new PyTuple(new PyObject[]{PyString.fromInterned("force-arch="), var1.getname("None"), PyString.fromInterned("Force an architecture onto the RPM build process")}), new PyTuple(new PyObject[]{PyString.fromInterned("quiet"), PyString.fromInterned("q"), PyString.fromInterned("Run the INSTALL phase of RPM building in quiet mode")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(133);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("keep-temp"), PyString.fromInterned("use-rpm-opt-flags"), PyString.fromInterned("rpm3-mode"), PyString.fromInterned("no-autoreq"), PyString.fromInterned("quiet")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(136);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("no-keep-temp"), PyString.fromInterned("keep-temp"), PyString.fromInterned("no-rpm-opt-flags"), PyString.fromInterned("use-rpm-opt-flags"), PyString.fromInterned("rpm2-mode"), PyString.fromInterned("rpm3-mode")});
      var1.setlocal("negative_opt", var5);
      var3 = null;
      var1.setline(141);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var7);
      var3 = null;
      var1.setline(189);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var7);
      var3 = null;
      var1.setline(223);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, finalize_package_data$4, (PyObject)null);
      var1.setlocal("finalize_package_data", var7);
      var3 = null;
      var1.setline(270);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, run$5, (PyObject)null);
      var1.setlocal("run", var7);
      var3 = null;
      var1.setline(407);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _dist_path$6, (PyObject)null);
      var1.setlocal("_dist_path", var7);
      var3 = null;
      var1.setline(410);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _make_spec_file$7, PyString.fromInterned("Generate the text of an RPM spec file and return it as a\n        list of strings (one per line).\n        "));
      var1.setlocal("_make_spec_file", var7);
      var3 = null;
      var1.setline(565);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _format_changelog$8, PyString.fromInterned("Format the changelog correctly and convert it to a list of strings\n        "));
      var1.setlocal("_format_changelog", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(142);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("bdist_base", var3);
      var3 = null;
      var1.setline(143);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("rpm_base", var3);
      var3 = null;
      var1.setline(144);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("dist_dir", var3);
      var3 = null;
      var1.setline(145);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("python", var3);
      var3 = null;
      var1.setline(146);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("fix_python", var3);
      var3 = null;
      var1.setline(147);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("spec_only", var3);
      var3 = null;
      var1.setline(148);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("binary_only", var3);
      var3 = null;
      var1.setline(149);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("source_only", var3);
      var3 = null;
      var1.setline(150);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("use_bzip2", var3);
      var3 = null;
      var1.setline(152);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("distribution_name", var3);
      var3 = null;
      var1.setline(153);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("group", var3);
      var3 = null;
      var1.setline(154);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("release", var3);
      var3 = null;
      var1.setline(155);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("serial", var3);
      var3 = null;
      var1.setline(156);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("vendor", var3);
      var3 = null;
      var1.setline(157);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("packager", var3);
      var3 = null;
      var1.setline(158);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("doc_files", var3);
      var3 = null;
      var1.setline(159);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("changelog", var3);
      var3 = null;
      var1.setline(160);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("icon", var3);
      var3 = null;
      var1.setline(162);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("prep_script", var3);
      var3 = null;
      var1.setline(163);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_script", var3);
      var3 = null;
      var1.setline(164);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("install_script", var3);
      var3 = null;
      var1.setline(165);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("clean_script", var3);
      var3 = null;
      var1.setline(166);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("verify_script", var3);
      var3 = null;
      var1.setline(167);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("pre_install", var3);
      var3 = null;
      var1.setline(168);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("post_install", var3);
      var3 = null;
      var1.setline(169);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("pre_uninstall", var3);
      var3 = null;
      var1.setline(170);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("post_uninstall", var3);
      var3 = null;
      var1.setline(171);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("prep", var3);
      var3 = null;
      var1.setline(172);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("provides", var3);
      var3 = null;
      var1.setline(173);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("requires", var3);
      var3 = null;
      var1.setline(174);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("conflicts", var3);
      var3 = null;
      var1.setline(175);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_requires", var3);
      var3 = null;
      var1.setline(176);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("obsoletes", var3);
      var3 = null;
      var1.setline(178);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"keep_temp", var4);
      var3 = null;
      var1.setline(179);
      var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"use_rpm_opt_flags", var4);
      var3 = null;
      var1.setline(180);
      var4 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"rpm3_mode", var4);
      var3 = null;
      var1.setline(181);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"no_autoreq", var4);
      var3 = null;
      var1.setline(183);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("force_arch", var3);
      var3 = null;
      var1.setline(184);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"quiet", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(190);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bdist"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("bdist_base"), PyString.fromInterned("bdist_base")})));
      var1.setline(191);
      PyObject var3 = var1.getlocal(0).__getattr__("rpm_base");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(192);
         if (var1.getlocal(0).__getattr__("rpm3_mode").__not__().__nonzero__()) {
            var1.setline(193);
            throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("you must specify --rpm-base in RPM 2 mode"));
         }

         var1.setline(195);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("bdist_base"), (PyObject)PyString.fromInterned("rpm"));
         var1.getlocal(0).__setattr__("rpm_base", var3);
         var3 = null;
      }

      var1.setline(197);
      var3 = var1.getlocal(0).__getattr__("python");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(198);
         if (var1.getlocal(0).__getattr__("fix_python").__nonzero__()) {
            var1.setline(199);
            var3 = var1.getglobal("sys").__getattr__("executable");
            var1.getlocal(0).__setattr__("python", var3);
            var3 = null;
         } else {
            var1.setline(201);
            PyString var4 = PyString.fromInterned("python");
            var1.getlocal(0).__setattr__((String)"python", var4);
            var3 = null;
         }
      } else {
         var1.setline(202);
         if (var1.getlocal(0).__getattr__("fix_python").__nonzero__()) {
            var1.setline(203);
            throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("--python and --fix-python are mutually exclusive options"));
         }
      }

      var1.setline(206);
      var3 = var1.getglobal("os").__getattr__("name");
      var10000 = var3._ne(PyString.fromInterned("posix"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(207);
         throw Py.makeException(var1.getglobal("DistutilsPlatformError"), PyString.fromInterned("don't know how to create RPM distributions on platform %s")._mod(var1.getglobal("os").__getattr__("name")));
      } else {
         var1.setline(210);
         var10000 = var1.getlocal(0).__getattr__("binary_only");
         if (var10000.__nonzero__()) {
            var10000 = var1.getlocal(0).__getattr__("source_only");
         }

         if (var10000.__nonzero__()) {
            var1.setline(211);
            throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("cannot supply both '--source-only' and '--binary-only'"));
         } else {
            var1.setline(215);
            if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2).__not__().__nonzero__()) {
               var1.setline(216);
               PyInteger var5 = Py.newInteger(0);
               var1.getlocal(0).__setattr__((String)"use_rpm_opt_flags", var5);
               var3 = null;
            }

            var1.setline(218);
            var1.getlocal(0).__getattr__("set_undefined_options").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bdist"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("dist_dir"), PyString.fromInterned("dist_dir")})));
            var1.setline(219);
            var1.getlocal(0).__getattr__("finalize_package_data").__call__(var2);
            var1.f_lasti = -1;
            return Py.None;
         }
      }
   }

   public PyObject finalize_package_data$4(PyFrame var1, ThreadState var2) {
      var1.setline(224);
      var1.getlocal(0).__getattr__("ensure_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("group"), (PyObject)PyString.fromInterned("Development/Libraries"));
      var1.setline(225);
      var1.getlocal(0).__getattr__("ensure_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("vendor"), (PyObject)PyString.fromInterned("%s <%s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("distribution").__getattr__("get_contact").__call__(var2), var1.getlocal(0).__getattr__("distribution").__getattr__("get_contact_email").__call__(var2)})));
      var1.setline(228);
      var1.getlocal(0).__getattr__("ensure_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("packager"));
      var1.setline(229);
      var1.getlocal(0).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("doc_files"));
      var1.setline(230);
      PyObject var3;
      if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(0).__getattr__("doc_files"), var1.getglobal("list")).__nonzero__()) {
         var1.setline(231);
         var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("README"), PyString.fromInterned("README.txt")})).__iter__();

         while(true) {
            var1.setline(231);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(1, var4);
            var1.setline(232);
            PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(1));
            if (var10000.__nonzero__()) {
               PyObject var5 = var1.getlocal(1);
               var10000 = var5._notin(var1.getlocal(0).__getattr__("doc_files"));
               var5 = null;
            }

            if (var10000.__nonzero__()) {
               var1.setline(233);
               var1.getlocal(0).__getattr__("doc_files").__getattr__("append").__call__(var2, var1.getlocal(1));
            }
         }
      }

      var1.setline(235);
      var1.getlocal(0).__getattr__("ensure_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("release"), (PyObject)PyString.fromInterned("1"));
      var1.setline(236);
      var1.getlocal(0).__getattr__("ensure_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("serial"));
      var1.setline(238);
      var1.getlocal(0).__getattr__("ensure_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("distribution_name"));
      var1.setline(240);
      var1.getlocal(0).__getattr__("ensure_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("changelog"));
      var1.setline(242);
      var3 = var1.getlocal(0).__getattr__("_format_changelog").__call__(var2, var1.getlocal(0).__getattr__("changelog"));
      var1.getlocal(0).__setattr__("changelog", var3);
      var3 = null;
      var1.setline(244);
      var1.getlocal(0).__getattr__("ensure_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("icon"));
      var1.setline(246);
      var1.getlocal(0).__getattr__("ensure_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("prep_script"));
      var1.setline(247);
      var1.getlocal(0).__getattr__("ensure_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_script"));
      var1.setline(248);
      var1.getlocal(0).__getattr__("ensure_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("install_script"));
      var1.setline(249);
      var1.getlocal(0).__getattr__("ensure_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("clean_script"));
      var1.setline(250);
      var1.getlocal(0).__getattr__("ensure_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("verify_script"));
      var1.setline(251);
      var1.getlocal(0).__getattr__("ensure_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("pre_install"));
      var1.setline(252);
      var1.getlocal(0).__getattr__("ensure_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("post_install"));
      var1.setline(253);
      var1.getlocal(0).__getattr__("ensure_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("pre_uninstall"));
      var1.setline(254);
      var1.getlocal(0).__getattr__("ensure_filename").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("post_uninstall"));
      var1.setline(260);
      var1.getlocal(0).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("provides"));
      var1.setline(261);
      var1.getlocal(0).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("requires"));
      var1.setline(262);
      var1.getlocal(0).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("conflicts"));
      var1.setline(263);
      var1.getlocal(0).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_requires"));
      var1.setline(264);
      var1.getlocal(0).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("obsoletes"));
      var1.setline(266);
      var1.getlocal(0).__getattr__("ensure_string").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("force_arch"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$5(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      if (var1.getglobal("DEBUG").__nonzero__()) {
         var1.setline(273);
         Py.println(PyString.fromInterned("before _get_package_data():"));
         var1.setline(274);
         Py.printComma(PyString.fromInterned("vendor ="));
         Py.println(var1.getlocal(0).__getattr__("vendor"));
         var1.setline(275);
         Py.printComma(PyString.fromInterned("packager ="));
         Py.println(var1.getlocal(0).__getattr__("packager"));
         var1.setline(276);
         Py.printComma(PyString.fromInterned("doc_files ="));
         Py.println(var1.getlocal(0).__getattr__("doc_files"));
         var1.setline(277);
         Py.printComma(PyString.fromInterned("changelog ="));
         Py.println(var1.getlocal(0).__getattr__("changelog"));
      }

      var1.setline(280);
      PyObject var3;
      PyObject var4;
      PyObject var5;
      if (var1.getlocal(0).__getattr__("spec_only").__nonzero__()) {
         var1.setline(281);
         var3 = var1.getlocal(0).__getattr__("dist_dir");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(282);
         var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(284);
         PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
         var1.setlocal(2, var7);
         var3 = null;
         var1.setline(285);
         var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("SOURCES"), PyString.fromInterned("SPECS"), PyString.fromInterned("BUILD"), PyString.fromInterned("RPMS"), PyString.fromInterned("SRPMS")})).__iter__();

         while(true) {
            var1.setline(285);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(288);
               var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("SPECS"));
               var1.setlocal(1, var3);
               var3 = null;
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(286);
            var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("rpm_base"), var1.getlocal(3));
            var1.getlocal(2).__setitem__(var1.getlocal(3), var5);
            var5 = null;
            var1.setline(287);
            var1.getlocal(0).__getattr__("mkpath").__call__(var2, var1.getlocal(2).__getitem__(var1.getlocal(3)));
         }
      }

      var1.setline(292);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), PyString.fromInterned("%s.spec")._mod(var1.getlocal(0).__getattr__("distribution").__getattr__("get_name").__call__(var2)));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(294);
      var1.getlocal(0).__getattr__("execute").__call__((ThreadState)var2, var1.getglobal("write_file"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(0).__getattr__("_make_spec_file").__call__(var2)})), (PyObject)PyString.fromInterned("writing '%s'")._mod(var1.getlocal(4)));
      var1.setline(299);
      if (var1.getlocal(0).__getattr__("spec_only").__nonzero__()) {
         var1.setline(300);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(304);
         var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("dist_files").__getslice__((PyObject)null, (PyObject)null, (PyObject)null);
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(305);
         var3 = var1.getlocal(0).__getattr__("reinitialize_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sdist"));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(306);
         PyList var9;
         if (var1.getlocal(0).__getattr__("use_bzip2").__nonzero__()) {
            var1.setline(307);
            var9 = new PyList(new PyObject[]{PyString.fromInterned("bztar")});
            var1.getlocal(6).__setattr__((String)"formats", var9);
            var3 = null;
         } else {
            var1.setline(309);
            var9 = new PyList(new PyObject[]{PyString.fromInterned("gztar")});
            var1.getlocal(6).__setattr__((String)"formats", var9);
            var3 = null;
         }

         var1.setline(310);
         var1.getlocal(0).__getattr__("run_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sdist"));
         var1.setline(311);
         var3 = var1.getlocal(5);
         var1.getlocal(0).__getattr__("distribution").__setattr__("dist_files", var3);
         var3 = null;
         var1.setline(313);
         var3 = var1.getlocal(6).__getattr__("get_archive_files").__call__(var2).__getitem__(Py.newInteger(0));
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(314);
         var3 = var1.getlocal(2).__getitem__(PyString.fromInterned("SOURCES"));
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(315);
         var1.getlocal(0).__getattr__("copy_file").__call__(var2, var1.getlocal(7), var1.getlocal(8));
         var1.setline(317);
         if (var1.getlocal(0).__getattr__("icon").__nonzero__()) {
            var1.setline(318);
            if (!var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(0).__getattr__("icon")).__nonzero__()) {
               var1.setline(321);
               throw Py.makeException(var1.getglobal("DistutilsFileError"), PyString.fromInterned("icon file '%s' does not exist")._mod(var1.getlocal(0).__getattr__("icon")));
            }

            var1.setline(319);
            var1.getlocal(0).__getattr__("copy_file").__call__(var2, var1.getlocal(0).__getattr__("icon"), var1.getlocal(8));
         }

         var1.setline(326);
         var1.getglobal("log").__getattr__("info").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("building RPMs"));
         var1.setline(327);
         var9 = new PyList(new PyObject[]{PyString.fromInterned("rpm")});
         var1.setlocal(9, var9);
         var3 = null;
         var1.setline(328);
         PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/usr/bin/rpmbuild"));
         if (!var10000.__nonzero__()) {
            var10000 = var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/bin/rpmbuild"));
         }

         if (var10000.__nonzero__()) {
            var1.setline(330);
            var9 = new PyList(new PyObject[]{PyString.fromInterned("rpmbuild")});
            var1.setlocal(9, var9);
            var3 = null;
         }

         var1.setline(332);
         if (var1.getlocal(0).__getattr__("source_only").__nonzero__()) {
            var1.setline(333);
            var1.getlocal(9).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-bs"));
         } else {
            var1.setline(334);
            if (var1.getlocal(0).__getattr__("binary_only").__nonzero__()) {
               var1.setline(335);
               var1.getlocal(9).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-bb"));
            } else {
               var1.setline(337);
               var1.getlocal(9).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-ba"));
            }
         }

         var1.setline(338);
         if (var1.getlocal(0).__getattr__("rpm3_mode").__nonzero__()) {
            var1.setline(339);
            var1.getlocal(9).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("--define"), PyString.fromInterned("_topdir %s")._mod(var1.getglobal("os").__getattr__("path").__getattr__("abspath").__call__(var2, var1.getlocal(0).__getattr__("rpm_base")))})));
         }

         var1.setline(341);
         if (var1.getlocal(0).__getattr__("keep_temp").__not__().__nonzero__()) {
            var1.setline(342);
            var1.getlocal(9).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--clean"));
         }

         var1.setline(344);
         if (var1.getlocal(0).__getattr__("quiet").__nonzero__()) {
            var1.setline(345);
            var1.getlocal(9).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--quiet"));
         }

         var1.setline(347);
         var1.getlocal(9).__getattr__("append").__call__(var2, var1.getlocal(4));
         var1.setline(352);
         PyString var10 = PyString.fromInterned("%{name}-%{version}-%{release}");
         var1.setlocal(10, var10);
         var3 = null;
         var1.setline(353);
         var3 = var1.getlocal(10)._add(PyString.fromInterned(".src.rpm"));
         var1.setlocal(11, var3);
         var3 = null;
         var1.setline(354);
         var3 = PyString.fromInterned("%{arch}/")._add(var1.getlocal(10))._add(PyString.fromInterned(".%{arch}.rpm"));
         var1.setlocal(12, var3);
         var3 = null;
         var1.setline(355);
         var3 = PyString.fromInterned("rpm -q --qf '%s %s\\n' --specfile '%s'")._mod(new PyTuple(new PyObject[]{var1.getlocal(11), var1.getlocal(12), var1.getlocal(4)}));
         var1.setlocal(13, var3);
         var3 = null;
         var1.setline(358);
         var3 = var1.getglobal("os").__getattr__("popen").__call__(var2, var1.getlocal(13));
         var1.setlocal(14, var3);
         var3 = null;
         var3 = null;

         try {
            var1.setline(360);
            PyList var8 = new PyList(Py.EmptyObjects);
            var1.setlocal(15, var8);
            var4 = null;
            var1.setline(361);
            var4 = var1.getglobal("None");
            var1.setlocal(16, var4);
            var4 = null;

            while(true) {
               var1.setline(362);
               if (Py.newInteger(1).__nonzero__()) {
                  var1.setline(363);
                  var4 = var1.getlocal(14).__getattr__("readline").__call__(var2);
                  var1.setlocal(17, var4);
                  var4 = null;
                  var1.setline(364);
                  if (!var1.getlocal(17).__not__().__nonzero__()) {
                     var1.setline(366);
                     var4 = var1.getglobal("string").__getattr__("split").__call__(var2, var1.getglobal("string").__getattr__("strip").__call__(var2, var1.getlocal(17)));
                     var1.setlocal(18, var4);
                     var4 = null;
                     var1.setline(367);
                     if (var1.getglobal("__debug__").__nonzero__()) {
                        var4 = var1.getglobal("len").__call__(var2, var1.getlocal(18));
                        var10000 = var4._eq(Py.newInteger(2));
                        var4 = null;
                        if (!var10000.__nonzero__()) {
                           var10000 = Py.None;
                           throw Py.makeException(var1.getglobal("AssertionError"), var10000);
                        }
                     }

                     var1.setline(368);
                     var1.getlocal(15).__getattr__("append").__call__(var2, var1.getlocal(18).__getitem__(Py.newInteger(1)));
                     var1.setline(370);
                     var4 = var1.getlocal(16);
                     var10000 = var4._is(var1.getglobal("None"));
                     var4 = null;
                     if (var10000.__nonzero__()) {
                        var1.setline(371);
                        var4 = var1.getlocal(18).__getitem__(Py.newInteger(0));
                        var1.setlocal(16, var4);
                        var4 = null;
                     }
                     continue;
                  }
               }

               var1.setline(373);
               var4 = var1.getlocal(14).__getattr__("close").__call__(var2);
               var1.setlocal(19, var4);
               var4 = null;
               var1.setline(374);
               if (var1.getlocal(19).__nonzero__()) {
                  var1.setline(375);
                  throw Py.makeException(var1.getglobal("DistutilsExecError").__call__(var2, PyString.fromInterned("Failed to execute: %s")._mod(var1.getglobal("repr").__call__(var2, var1.getlocal(13)))));
               }
               break;
            }
         } catch (Throwable var6) {
            Py.addTraceback(var6, var1);
            var1.setline(378);
            var1.getlocal(14).__getattr__("close").__call__(var2);
            throw (Throwable)var6;
         }

         var1.setline(378);
         var1.getlocal(14).__getattr__("close").__call__(var2);
         var1.setline(380);
         var1.getlocal(0).__getattr__("spawn").__call__(var2, var1.getlocal(9));
         var1.setline(382);
         if (var1.getlocal(0).__getattr__("dry_run").__not__().__nonzero__()) {
            var1.setline(383);
            if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2).__nonzero__()) {
               var1.setline(384);
               var3 = var1.getglobal("get_python_version").__call__(var2);
               var1.setlocal(20, var3);
               var3 = null;
            } else {
               var1.setline(386);
               var10 = PyString.fromInterned("any");
               var1.setlocal(20, var10);
               var3 = null;
            }

            var1.setline(388);
            if (var1.getlocal(0).__getattr__("binary_only").__not__().__nonzero__()) {
               var1.setline(389);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2).__getitem__(PyString.fromInterned("SRPMS")), var1.getlocal(16));
               var1.setlocal(21, var3);
               var3 = null;
               var1.setline(390);
               if (var1.getglobal("__debug__").__nonzero__() && !var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(21)).__nonzero__()) {
                  var10000 = Py.None;
                  throw Py.makeException(var1.getglobal("AssertionError"), var10000);
               }

               var1.setline(391);
               var1.getlocal(0).__getattr__("move_file").__call__(var2, var1.getlocal(21), var1.getlocal(0).__getattr__("dist_dir"));
               var1.setline(392);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("dist_dir"), var1.getlocal(16));
               var1.setlocal(22, var3);
               var3 = null;
               var1.setline(393);
               var1.getlocal(0).__getattr__("distribution").__getattr__("dist_files").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("bdist_rpm"), var1.getlocal(20), var1.getlocal(22)})));
            }

            var1.setline(396);
            if (var1.getlocal(0).__getattr__("source_only").__not__().__nonzero__()) {
               var1.setline(397);
               var3 = var1.getlocal(15).__iter__();

               while(true) {
                  var1.setline(397);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(23, var4);
                  var1.setline(398);
                  var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2).__getitem__(PyString.fromInterned("RPMS")), var1.getlocal(23));
                  var1.setlocal(23, var5);
                  var5 = null;
                  var1.setline(399);
                  if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(23)).__nonzero__()) {
                     var1.setline(400);
                     var1.getlocal(0).__getattr__("move_file").__call__(var2, var1.getlocal(23), var1.getlocal(0).__getattr__("dist_dir"));
                     var1.setline(401);
                     var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("dist_dir"), var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(23)));
                     var1.setlocal(22, var5);
                     var5 = null;
                     var1.setline(403);
                     var1.getlocal(0).__getattr__("distribution").__getattr__("dist_files").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("bdist_rpm"), var1.getlocal(20), var1.getlocal(22)})));
                  }
               }
            }
         }

         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _dist_path$6(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("dist_dir"), var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject _make_spec_file$7(PyFrame var1, ThreadState var2) {
      var1.setline(413);
      PyString.fromInterned("Generate the text of an RPM spec file and return it as a\n        list of strings (one per line).\n        ");
      var1.setline(415);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("%define name ")._add(var1.getlocal(0).__getattr__("distribution").__getattr__("get_name").__call__(var2)), PyString.fromInterned("%define version ")._add(var1.getlocal(0).__getattr__("distribution").__getattr__("get_version").__call__(var2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_"))), PyString.fromInterned("%define unmangled_version ")._add(var1.getlocal(0).__getattr__("distribution").__getattr__("get_version").__call__(var2)), PyString.fromInterned("%define release ")._add(var1.getlocal(0).__getattr__("release").__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"), (PyObject)PyString.fromInterned("_"))), PyString.fromInterned(""), PyString.fromInterned("Summary: ")._add(var1.getlocal(0).__getattr__("distribution").__getattr__("get_description").__call__(var2))});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(431);
      var1.getlocal(1).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("Name: %{name}"), PyString.fromInterned("Version: %{version}"), PyString.fromInterned("Release: %{release}")})));
      var1.setline(439);
      if (var1.getlocal(0).__getattr__("use_bzip2").__nonzero__()) {
         var1.setline(440);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Source0: %{name}-%{unmangled_version}.tar.bz2"));
      } else {
         var1.setline(442);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Source0: %{name}-%{unmangled_version}.tar.gz"));
      }

      var1.setline(444);
      var1.getlocal(1).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("License: ")._add(var1.getlocal(0).__getattr__("distribution").__getattr__("get_license").__call__(var2)), PyString.fromInterned("Group: ")._add(var1.getlocal(0).__getattr__("group")), PyString.fromInterned("BuildRoot: %{_tmppath}/%{name}-%{version}-%{release}-buildroot"), PyString.fromInterned("Prefix: %{_prefix}")})));
      var1.setline(450);
      if (var1.getlocal(0).__getattr__("force_arch").__not__().__nonzero__()) {
         var1.setline(452);
         if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2).__not__().__nonzero__()) {
            var1.setline(453);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BuildArch: noarch"));
         }
      } else {
         var1.setline(455);
         var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("BuildArch: %s")._mod(var1.getlocal(0).__getattr__("force_arch")));
      }

      var1.setline(457);
      PyObject var7 = (new PyTuple(new PyObject[]{PyString.fromInterned("Vendor"), PyString.fromInterned("Packager"), PyString.fromInterned("Provides"), PyString.fromInterned("Requires"), PyString.fromInterned("Conflicts"), PyString.fromInterned("Obsoletes")})).__iter__();

      while(true) {
         var1.setline(457);
         PyObject var4 = var7.__iternext__();
         PyObject var10000;
         PyObject var5;
         if (var4 == null) {
            var1.setline(471);
            var7 = var1.getlocal(0).__getattr__("distribution").__getattr__("get_url").__call__(var2);
            var10000 = var7._ne(PyString.fromInterned("UNKNOWN"));
            var3 = null;
            if (var10000.__nonzero__()) {
               var1.setline(472);
               var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("Url: ")._add(var1.getlocal(0).__getattr__("distribution").__getattr__("get_url").__call__(var2)));
            }

            var1.setline(474);
            if (var1.getlocal(0).__getattr__("distribution_name").__nonzero__()) {
               var1.setline(475);
               var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("Distribution: ")._add(var1.getlocal(0).__getattr__("distribution_name")));
            }

            var1.setline(477);
            if (var1.getlocal(0).__getattr__("build_requires").__nonzero__()) {
               var1.setline(478);
               var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("BuildRequires: ")._add(var1.getglobal("string").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("build_requires"))));
            }

            var1.setline(481);
            if (var1.getlocal(0).__getattr__("icon").__nonzero__()) {
               var1.setline(482);
               var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("Icon: ")._add(var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(0).__getattr__("icon"))));
            }

            var1.setline(484);
            if (var1.getlocal(0).__getattr__("no_autoreq").__nonzero__()) {
               var1.setline(485);
               var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("AutoReq: 0"));
            }

            var1.setline(487);
            var1.getlocal(1).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("%description"), var1.getlocal(0).__getattr__("distribution").__getattr__("get_long_description").__call__(var2)})));
            var1.setline(505);
            var7 = PyString.fromInterned("%s %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("python"), var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getglobal("sys").__getattr__("argv").__getitem__(Py.newInteger(0)))}));
            var1.setlocal(4, var7);
            var3 = null;
            var1.setline(506);
            var7 = PyString.fromInterned("%s build")._mod(var1.getlocal(4));
            var1.setlocal(5, var7);
            var3 = null;
            var1.setline(507);
            if (var1.getlocal(0).__getattr__("use_rpm_opt_flags").__nonzero__()) {
               var1.setline(508);
               var7 = PyString.fromInterned("env CFLAGS=\"$RPM_OPT_FLAGS\" ")._add(var1.getlocal(5));
               var1.setlocal(5, var7);
               var3 = null;
            }

            var1.setline(516);
            var7 = PyString.fromInterned("%s install -O1 --root=$RPM_BUILD_ROOT --record=INSTALLED_FILES")._mod(var1.getlocal(4));
            var1.setlocal(6, var7);
            var3 = null;
            var1.setline(519);
            var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("prep"), PyString.fromInterned("prep_script"), PyString.fromInterned("%setup -n %{name}-%{unmangled_version}")}), new PyTuple(new PyObject[]{PyString.fromInterned("build"), PyString.fromInterned("build_script"), var1.getlocal(5)}), new PyTuple(new PyObject[]{PyString.fromInterned("install"), PyString.fromInterned("install_script"), var1.getlocal(6)}), new PyTuple(new PyObject[]{PyString.fromInterned("clean"), PyString.fromInterned("clean_script"), PyString.fromInterned("rm -rf $RPM_BUILD_ROOT")}), new PyTuple(new PyObject[]{PyString.fromInterned("verifyscript"), PyString.fromInterned("verify_script"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("pre"), PyString.fromInterned("pre_install"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("post"), PyString.fromInterned("post_install"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("preun"), PyString.fromInterned("pre_uninstall"), var1.getglobal("None")}), new PyTuple(new PyObject[]{PyString.fromInterned("postun"), PyString.fromInterned("post_uninstall"), var1.getglobal("None")})});
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(531);
            var7 = var1.getlocal(7).__iter__();

            while(true) {
               var1.setline(531);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.setline(546);
                  var1.getlocal(1).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("%files -f INSTALLED_FILES"), PyString.fromInterned("%defattr(-,root,root)")})));
                  var1.setline(552);
                  if (var1.getlocal(0).__getattr__("doc_files").__nonzero__()) {
                     var1.setline(553);
                     var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("%doc ")._add(var1.getglobal("string").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("doc_files"))));
                  }

                  var1.setline(555);
                  if (var1.getlocal(0).__getattr__("changelog").__nonzero__()) {
                     var1.setline(556);
                     var1.getlocal(1).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("%changelog")})));
                     var1.setline(559);
                     var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getlocal(0).__getattr__("changelog"));
                  }

                  var1.setline(561);
                  var7 = var1.getlocal(1);
                  var1.f_lasti = -1;
                  return var7;
               }

               PyObject[] var8 = Py.unpackSequence(var4, 3);
               PyObject var6 = var8[0];
               var1.setlocal(8, var6);
               var6 = null;
               var6 = var8[1];
               var1.setlocal(9, var6);
               var6 = null;
               var6 = var8[2];
               var1.setlocal(10, var6);
               var6 = null;
               var1.setline(534);
               var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getlocal(9));
               var1.setlocal(3, var5);
               var5 = null;
               var1.setline(535);
               var10000 = var1.getlocal(3);
               if (!var10000.__nonzero__()) {
                  var10000 = var1.getlocal(10);
               }

               if (var10000.__nonzero__()) {
                  var1.setline(536);
                  var1.getlocal(1).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("%")._add(var1.getlocal(8))})));
                  var1.setline(539);
                  if (var1.getlocal(3).__nonzero__()) {
                     var1.setline(540);
                     var1.getlocal(1).__getattr__("extend").__call__(var2, var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("r")).__getattr__("read").__call__(var2), (PyObject)PyString.fromInterned("\n")));
                  } else {
                     var1.setline(542);
                     var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(10));
                  }
               }
            }
         }

         var1.setlocal(2, var4);
         var1.setline(464);
         var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0), var1.getglobal("string").__getattr__("lower").__call__(var2, var1.getlocal(2)));
         var1.setlocal(3, var5);
         var5 = null;
         var1.setline(465);
         if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(3), var1.getglobal("list")).__nonzero__()) {
            var1.setline(466);
            var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("%s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getglobal("string").__getattr__("join").__call__(var2, var1.getlocal(3))})));
         } else {
            var1.setline(467);
            var5 = var1.getlocal(3);
            var10000 = var5._isnot(var1.getglobal("None"));
            var5 = null;
            if (var10000.__nonzero__()) {
               var1.setline(468);
               var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("%s: %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
            }
         }
      }
   }

   public PyObject _format_changelog$8(PyFrame var1, ThreadState var2) {
      var1.setline(567);
      PyString.fromInterned("Format the changelog correctly and convert it to a list of strings\n        ");
      var1.setline(568);
      PyObject var3;
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(569);
         var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(570);
         PyList var4 = new PyList(Py.EmptyObjects);
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(571);
         PyObject var7 = var1.getglobal("string").__getattr__("split").__call__((ThreadState)var2, (PyObject)var1.getglobal("string").__getattr__("strip").__call__(var2, var1.getlocal(1)), (PyObject)PyString.fromInterned("\n")).__iter__();

         while(true) {
            var1.setline(571);
            PyObject var5 = var7.__iternext__();
            if (var5 == null) {
               var1.setline(581);
               if (var1.getlocal(2).__getitem__(Py.newInteger(0)).__not__().__nonzero__()) {
                  var1.setline(582);
                  var1.getlocal(2).__delitem__((PyObject)Py.newInteger(0));
               }

               var1.setline(584);
               var3 = var1.getlocal(2);
               var1.f_lasti = -1;
               return var3;
            }

            var1.setlocal(3, var5);
            var1.setline(572);
            PyObject var6 = var1.getglobal("string").__getattr__("strip").__call__(var2, var1.getlocal(3));
            var1.setlocal(3, var6);
            var6 = null;
            var1.setline(573);
            var6 = var1.getlocal(3).__getitem__(Py.newInteger(0));
            PyObject var10000 = var6._eq(PyString.fromInterned("*"));
            var6 = null;
            if (var10000.__nonzero__()) {
               var1.setline(574);
               var1.getlocal(2).__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned(""), var1.getlocal(3)})));
            } else {
               var1.setline(575);
               var6 = var1.getlocal(3).__getitem__(Py.newInteger(0));
               var10000 = var6._eq(PyString.fromInterned("-"));
               var6 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(576);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(3));
               } else {
                  var1.setline(578);
                  var1.getlocal(2).__getattr__("append").__call__(var2, PyString.fromInterned("  ")._add(var1.getlocal(3)));
               }
            }
         }
      }
   }

   public bdist_rpm$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      bdist_rpm$1 = Py.newCode(0, var2, var1, "bdist_rpm", 20, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 141, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 189, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "readme"};
      finalize_package_data$4 = Py.newCode(1, var2, var1, "finalize_package_data", 223, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "spec_dir", "rpm_dir", "d", "spec_path", "saved_dist_files", "sdist", "source", "source_dir", "rpm_cmd", "nvr_string", "src_rpm", "non_src_rpm", "q_cmd", "out", "binary_rpms", "source_rpm", "line", "l", "status", "pyversion", "srpm", "filename", "rpm"};
      run$5 = Py.newCode(1, var2, var1, "run", 270, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path"};
      _dist_path$6 = Py.newCode(2, var2, var1, "_dist_path", 407, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "spec_file", "field", "val", "def_setup_call", "def_build", "install_cmd", "script_options", "rpm_opt", "attr", "default"};
      _make_spec_file$7 = Py.newCode(1, var2, var1, "_make_spec_file", 410, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "changelog", "new_changelog", "line"};
      _format_changelog$8 = Py.newCode(2, var2, var1, "_format_changelog", 565, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new bdist_rpm$py("distutils/command/bdist_rpm$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(bdist_rpm$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.bdist_rpm$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.finalize_options$3(var2, var3);
         case 4:
            return this.finalize_package_data$4(var2, var3);
         case 5:
            return this.run$5(var2, var3);
         case 6:
            return this._dist_path$6(var2, var3);
         case 7:
            return this._make_spec_file$7(var2, var3);
         case 8:
            return this._format_changelog$8(var2, var3);
         default:
            return null;
      }
   }
}
