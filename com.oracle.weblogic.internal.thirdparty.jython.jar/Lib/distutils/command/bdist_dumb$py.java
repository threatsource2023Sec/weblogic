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
@Filename("distutils/command/bdist_dumb.py")
public class bdist_dumb$py extends PyFunctionTable implements PyRunnable {
   static bdist_dumb$py self;
   static final PyCode f$0;
   static final PyCode bdist_dumb$1;
   static final PyCode initialize_options$2;
   static final PyCode finalize_options$3;
   static final PyCode run$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.bdist_dumb\n\nImplements the Distutils 'bdist_dumb' command (create a \"dumb\" built\ndistribution -- i.e., just an archive to be unpacked under $prefix or\n$exec_prefix)."));
      var1.setline(5);
      PyString.fromInterned("distutils.command.bdist_dumb\n\nImplements the Distutils 'bdist_dumb' command (create a \"dumb\" built\ndistribution -- i.e., just an archive to be unpacked under $prefix or\n$exec_prefix).");
      var1.setline(7);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(9);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(11);
      String[] var6 = new String[]{"get_python_version"};
      PyObject[] var7 = imp.importFrom("sysconfig", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("get_python_version", var4);
      var4 = null;
      var1.setline(13);
      var6 = new String[]{"get_platform"};
      var7 = imp.importFrom("distutils.util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("get_platform", var4);
      var4 = null;
      var1.setline(14);
      var6 = new String[]{"Command"};
      var7 = imp.importFrom("distutils.core", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(15);
      var6 = new String[]{"remove_tree", "ensure_relative"};
      var7 = imp.importFrom("distutils.dir_util", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("remove_tree", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("ensure_relative", var4);
      var4 = null;
      var1.setline(16);
      var6 = new String[]{"DistutilsPlatformError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var1.setline(17);
      var6 = new String[]{"log"};
      var7 = imp.importFrom("distutils", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(19);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("bdist_dumb", var7, bdist_dumb$1);
      var1.setlocal("bdist_dumb", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject bdist_dumb$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(21);
      PyString var3 = PyString.fromInterned("create a \"dumb\" built distribution");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(23);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("bdist-dir="), PyString.fromInterned("d"), PyString.fromInterned("temporary directory for creating the distribution")}), new PyTuple(new PyObject[]{PyString.fromInterned("plat-name="), PyString.fromInterned("p"), PyString.fromInterned("platform name to embed in generated filenames (default: %s)")._mod(var1.getname("get_platform").__call__(var2))}), new PyTuple(new PyObject[]{PyString.fromInterned("format="), PyString.fromInterned("f"), PyString.fromInterned("archive format to create (tar, ztar, gztar, zip)")}), new PyTuple(new PyObject[]{PyString.fromInterned("keep-temp"), PyString.fromInterned("k"), PyString.fromInterned("keep the pseudo-installation tree around after ")._add(PyString.fromInterned("creating the distribution archive"))}), new PyTuple(new PyObject[]{PyString.fromInterned("dist-dir="), PyString.fromInterned("d"), PyString.fromInterned("directory to put final built distributions in")}), new PyTuple(new PyObject[]{PyString.fromInterned("skip-build"), var1.getname("None"), PyString.fromInterned("skip rebuilding everything (for testing/debugging)")}), new PyTuple(new PyObject[]{PyString.fromInterned("relative"), var1.getname("None"), PyString.fromInterned("build the archive using relative paths(default: false)")}), new PyTuple(new PyObject[]{PyString.fromInterned("owner="), PyString.fromInterned("u"), PyString.fromInterned("Owner name used when creating a tar file [default: current user]")}), new PyTuple(new PyObject[]{PyString.fromInterned("group="), PyString.fromInterned("g"), PyString.fromInterned("Group name used when creating a tar file [default: current group]")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(48);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("keep-temp"), PyString.fromInterned("skip-build"), PyString.fromInterned("relative")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(50);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("posix"), PyString.fromInterned("gztar"), PyString.fromInterned("java"), PyString.fromInterned("gztar"), PyString.fromInterned("nt"), PyString.fromInterned("zip"), PyString.fromInterned("os2"), PyString.fromInterned("zip")});
      var1.setlocal("default_format", var5);
      var3 = null;
      var1.setline(56);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var7);
      var3 = null;
      var1.setline(67);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, finalize_options$3, (PyObject)null);
      var1.setlocal("finalize_options", var7);
      var3 = null;
      var1.setline(85);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, run$4, (PyObject)null);
      var1.setlocal("run", var7);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("bdist_dir", var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("plat_name", var3);
      var3 = null;
      var1.setline(59);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("format", var3);
      var3 = null;
      var1.setline(60);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"keep_temp", var4);
      var3 = null;
      var1.setline(61);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("dist_dir", var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("skip_build", var3);
      var3 = null;
      var1.setline(63);
      var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"relative", var4);
      var3 = null;
      var1.setline(64);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("owner", var3);
      var3 = null;
      var1.setline(65);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("group", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyObject var3 = var1.getlocal(0).__getattr__("bdist_dir");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(69);
         var3 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("bdist")).__getattr__("bdist_base");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(70);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("dumb"));
         var1.getlocal(0).__setattr__("bdist_dir", var3);
         var3 = null;
      }

      var1.setline(72);
      var3 = var1.getlocal(0).__getattr__("format");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(74);
            var3 = var1.getlocal(0).__getattr__("default_format").__getitem__(var1.getglobal("os").__getattr__("name"));
            var1.getlocal(0).__setattr__("format", var3);
            var3 = null;
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("KeyError"))) {
               var1.setline(76);
               throw Py.makeException(var1.getglobal("DistutilsPlatformError"), PyString.fromInterned("don't know how to create dumb built distributions ")._add(PyString.fromInterned("on platform %s"))._mod(var1.getglobal("os").__getattr__("name")));
            }

            throw var5;
         }
      }

      var1.setline(80);
      var1.getlocal(0).__getattr__("set_undefined_options").__call__(var2, PyString.fromInterned("bdist"), new PyTuple(new PyObject[]{PyString.fromInterned("dist_dir"), PyString.fromInterned("dist_dir")}), new PyTuple(new PyObject[]{PyString.fromInterned("plat_name"), PyString.fromInterned("plat_name")}), new PyTuple(new PyObject[]{PyString.fromInterned("skip_build"), PyString.fromInterned("skip_build")}));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$4(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      if (var1.getlocal(0).__getattr__("skip_build").__not__().__nonzero__()) {
         var1.setline(87);
         var1.getlocal(0).__getattr__("run_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build"));
      }

      var1.setline(89);
      PyObject var10000 = var1.getlocal(0).__getattr__("reinitialize_command");
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("install"), Py.newInteger(1)};
      String[] var4 = new String[]{"reinit_subcommands"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(90);
      var5 = var1.getlocal(0).__getattr__("bdist_dir");
      var1.getlocal(1).__setattr__("root", var5);
      var3 = null;
      var1.setline(91);
      var5 = var1.getlocal(0).__getattr__("skip_build");
      var1.getlocal(1).__setattr__("skip_build", var5);
      var3 = null;
      var1.setline(92);
      PyInteger var6 = Py.newInteger(0);
      var1.getlocal(1).__setattr__((String)"warn_dir", var6);
      var3 = null;
      var1.setline(94);
      var1.getglobal("log").__getattr__("info").__call__(var2, PyString.fromInterned("installing to %s")._mod(var1.getlocal(0).__getattr__("bdist_dir")));
      var1.setline(95);
      var1.getlocal(0).__getattr__("run_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("install"));
      var1.setline(99);
      var5 = PyString.fromInterned("%s.%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("distribution").__getattr__("get_fullname").__call__(var2), var1.getlocal(0).__getattr__("plat_name")}));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(104);
      var5 = var1.getglobal("os").__getattr__("name");
      var10000 = var5._eq(PyString.fromInterned("os2"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(105);
         var5 = var1.getlocal(2).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(":"), (PyObject)PyString.fromInterned("-"));
         var1.setlocal(2, var5);
         var3 = null;
      }

      var1.setline(107);
      var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("dist_dir"), var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(108);
      if (var1.getlocal(0).__getattr__("relative").__not__().__nonzero__()) {
         var1.setline(109);
         var5 = var1.getlocal(0).__getattr__("bdist_dir");
         var1.setlocal(4, var5);
         var3 = null;
      } else {
         var1.setline(111);
         var10000 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2);
         if (var10000.__nonzero__()) {
            var5 = var1.getlocal(1).__getattr__("install_base");
            var10000 = var5._ne(var1.getlocal(1).__getattr__("install_platbase"));
            var3 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(113);
            throw Py.makeException(var1.getglobal("DistutilsPlatformError"), PyString.fromInterned("can't make a dumb built distribution where base and platbase are different (%s, %s)")._mod(new PyTuple(new PyObject[]{var1.getglobal("repr").__call__(var2, var1.getlocal(1).__getattr__("install_base")), var1.getglobal("repr").__call__(var2, var1.getlocal(1).__getattr__("install_platbase"))})));
         }

         var1.setline(119);
         var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("bdist_dir"), var1.getglobal("ensure_relative").__call__(var2, var1.getlocal(1).__getattr__("install_base")));
         var1.setlocal(4, var5);
         var3 = null;
      }

      var1.setline(123);
      var10000 = var1.getlocal(0).__getattr__("make_archive");
      var3 = new PyObject[]{var1.getlocal(3), var1.getlocal(0).__getattr__("format"), var1.getlocal(4), var1.getlocal(0).__getattr__("owner"), var1.getlocal(0).__getattr__("group")};
      var4 = new String[]{"root_dir", "owner", "group"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(126);
      if (var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2).__nonzero__()) {
         var1.setline(127);
         var5 = var1.getglobal("get_python_version").__call__(var2);
         var1.setlocal(6, var5);
         var3 = null;
      } else {
         var1.setline(129);
         PyString var7 = PyString.fromInterned("any");
         var1.setlocal(6, var7);
         var3 = null;
      }

      var1.setline(130);
      var1.getlocal(0).__getattr__("distribution").__getattr__("dist_files").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("bdist_dumb"), var1.getlocal(6), var1.getlocal(5)})));
      var1.setline(133);
      if (var1.getlocal(0).__getattr__("keep_temp").__not__().__nonzero__()) {
         var1.setline(134);
         var10000 = var1.getglobal("remove_tree");
         var3 = new PyObject[]{var1.getlocal(0).__getattr__("bdist_dir"), var1.getlocal(0).__getattr__("dry_run")};
         var4 = new String[]{"dry_run"};
         var10000.__call__(var2, var3, var4);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public bdist_dumb$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      bdist_dumb$1 = Py.newCode(0, var2, var1, "bdist_dumb", 19, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 56, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "bdist_base"};
      finalize_options$3 = Py.newCode(1, var2, var1, "finalize_options", 67, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "install", "archive_basename", "pseudoinstall_root", "archive_root", "filename", "pyversion"};
      run$4 = Py.newCode(1, var2, var1, "run", 85, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new bdist_dumb$py("distutils/command/bdist_dumb$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(bdist_dumb$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.bdist_dumb$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.finalize_options$3(var2, var3);
         case 4:
            return this.run$4(var2, var3);
         default:
            return null;
      }
   }
}
