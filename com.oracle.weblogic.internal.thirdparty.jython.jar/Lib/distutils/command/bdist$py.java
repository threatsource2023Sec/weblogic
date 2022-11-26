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
@Filename("distutils/command/bdist.py")
public class bdist$py extends PyFunctionTable implements PyRunnable {
   static bdist$py self;
   static final PyCode f$0;
   static final PyCode show_formats$1;
   static final PyCode bdist$2;
   static final PyCode initialize_options$3;
   static final PyCode finalize_options$4;
   static final PyCode run$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.bdist\n\nImplements the Distutils 'bdist' command (create a built [binary]\ndistribution)."));
      var1.setline(4);
      PyString.fromInterned("distutils.command.bdist\n\nImplements the Distutils 'bdist' command (create a built [binary]\ndistribution).");
      var1.setline(6);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(8);
      PyObject var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(10);
      String[] var6 = new String[]{"get_platform"};
      PyObject[] var7 = imp.importFrom("distutils.util", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("get_platform", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"Command"};
      var7 = imp.importFrom("distutils.core", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(12);
      var6 = new String[]{"DistutilsPlatformError", "DistutilsOptionError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var4 = var7[1];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var1.setline(15);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, show_formats$1, PyString.fromInterned("Print list of available formats (arguments to \"--format\" option).\n    "));
      var1.setlocal("show_formats", var8);
      var3 = null;
      var1.setline(27);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("bdist", var7, bdist$2);
      var1.setlocal("bdist", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject show_formats$1(PyFrame var1, ThreadState var2) {
      var1.setline(17);
      PyString.fromInterned("Print list of available formats (arguments to \"--format\" option).\n    ");
      var1.setline(18);
      String[] var3 = new String[]{"FancyGetopt"};
      PyObject[] var5 = imp.importFrom("distutils.fancy_getopt", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(19);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(20);
      PyObject var7 = var1.getglobal("bdist").__getattr__("format_commands").__iter__();

      while(true) {
         var1.setline(20);
         var4 = var7.__iternext__();
         if (var4 == null) {
            var1.setline(23);
            var7 = var1.getlocal(0).__call__(var2, var1.getlocal(1));
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(24);
            var1.getlocal(3).__getattr__("print_help").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("List of available distribution formats:"));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(21);
         var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("formats=")._add(var1.getlocal(2)), var1.getglobal("None"), var1.getglobal("bdist").__getattr__("format_command").__getitem__(var1.getlocal(2)).__getitem__(Py.newInteger(1))})));
      }
   }

   public PyObject bdist$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(29);
      PyString var3 = PyString.fromInterned("create a built (binary) distribution");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(31);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("bdist-base="), PyString.fromInterned("b"), PyString.fromInterned("temporary directory for creating built distributions")}), new PyTuple(new PyObject[]{PyString.fromInterned("plat-name="), PyString.fromInterned("p"), PyString.fromInterned("platform name to embed in generated filenames (default: %s)")._mod(var1.getname("get_platform").__call__(var2))}), new PyTuple(new PyObject[]{PyString.fromInterned("formats="), var1.getname("None"), PyString.fromInterned("formats for distribution (comma-separated list)")}), new PyTuple(new PyObject[]{PyString.fromInterned("dist-dir="), PyString.fromInterned("d"), PyString.fromInterned("directory to put final built distributions in [default: dist]")}), new PyTuple(new PyObject[]{PyString.fromInterned("skip-build"), var1.getname("None"), PyString.fromInterned("skip rebuilding everything (for testing/debugging)")}), new PyTuple(new PyObject[]{PyString.fromInterned("owner="), PyString.fromInterned("u"), PyString.fromInterned("Owner name used when creating a tar file [default: current user]")}), new PyTuple(new PyObject[]{PyString.fromInterned("group="), PyString.fromInterned("g"), PyString.fromInterned("Group name used when creating a tar file [default: current group]")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(51);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("skip-build")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(53);
      var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("help-formats"), var1.getname("None"), PyString.fromInterned("lists available distribution formats"), var1.getname("show_formats")})});
      var1.setlocal("help_options", var4);
      var3 = null;
      var1.setline(59);
      PyTuple var5 = new PyTuple(new PyObject[]{PyString.fromInterned("bdist_rpm")});
      var1.setlocal("no_format_option", var5);
      var3 = null;
      var1.setline(63);
      PyDictionary var6 = new PyDictionary(new PyObject[]{PyString.fromInterned("posix"), PyString.fromInterned("gztar"), PyString.fromInterned("java"), PyString.fromInterned("gztar"), PyString.fromInterned("nt"), PyString.fromInterned("zip"), PyString.fromInterned("os2"), PyString.fromInterned("zip")});
      var1.setlocal("default_format", var6);
      var3 = null;
      var1.setline(69);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("rpm"), PyString.fromInterned("gztar"), PyString.fromInterned("bztar"), PyString.fromInterned("ztar"), PyString.fromInterned("tar"), PyString.fromInterned("wininst"), PyString.fromInterned("zip"), PyString.fromInterned("msi")});
      var1.setlocal("format_commands", var4);
      var3 = null;
      var1.setline(73);
      var6 = new PyDictionary(new PyObject[]{PyString.fromInterned("rpm"), new PyTuple(new PyObject[]{PyString.fromInterned("bdist_rpm"), PyString.fromInterned("RPM distribution")}), PyString.fromInterned("gztar"), new PyTuple(new PyObject[]{PyString.fromInterned("bdist_dumb"), PyString.fromInterned("gzip'ed tar file")}), PyString.fromInterned("bztar"), new PyTuple(new PyObject[]{PyString.fromInterned("bdist_dumb"), PyString.fromInterned("bzip2'ed tar file")}), PyString.fromInterned("ztar"), new PyTuple(new PyObject[]{PyString.fromInterned("bdist_dumb"), PyString.fromInterned("compressed tar file")}), PyString.fromInterned("tar"), new PyTuple(new PyObject[]{PyString.fromInterned("bdist_dumb"), PyString.fromInterned("tar file")}), PyString.fromInterned("wininst"), new PyTuple(new PyObject[]{PyString.fromInterned("bdist_wininst"), PyString.fromInterned("Windows executable installer")}), PyString.fromInterned("zip"), new PyTuple(new PyObject[]{PyString.fromInterned("bdist_dumb"), PyString.fromInterned("ZIP file")}), PyString.fromInterned("msi"), new PyTuple(new PyObject[]{PyString.fromInterned("bdist_msi"), PyString.fromInterned("Microsoft Installer")})});
      var1.setlocal("format_command", var6);
      var3 = null;
      var1.setline(85);
      PyObject[] var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, initialize_options$3, (PyObject)null);
      var1.setlocal("initialize_options", var8);
      var3 = null;
      var1.setline(94);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, finalize_options$4, (PyObject)null);
      var1.setlocal("finalize_options", var8);
      var3 = null;
      var1.setline(122);
      var7 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var7, run$5, (PyObject)null);
      var1.setlocal("run", var8);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(86);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("bdist_base", var3);
      var3 = null;
      var1.setline(87);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("plat_name", var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("formats", var3);
      var3 = null;
      var1.setline(89);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("dist_dir", var3);
      var3 = null;
      var1.setline(90);
      PyInteger var4 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"skip_build", var4);
      var3 = null;
      var1.setline(91);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("group", var3);
      var3 = null;
      var1.setline(92);
      var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("owner", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$4(PyFrame var1, ThreadState var2) {
      var1.setline(96);
      PyObject var3 = var1.getlocal(0).__getattr__("plat_name");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(97);
         if (var1.getlocal(0).__getattr__("skip_build").__nonzero__()) {
            var1.setline(98);
            var3 = var1.getglobal("get_platform").__call__(var2);
            var1.getlocal(0).__setattr__("plat_name", var3);
            var3 = null;
         } else {
            var1.setline(100);
            var3 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build")).__getattr__("plat_name");
            var1.getlocal(0).__setattr__("plat_name", var3);
            var3 = null;
         }
      }

      var1.setline(105);
      var3 = var1.getlocal(0).__getattr__("bdist_base");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(106);
         var3 = var1.getlocal(0).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build")).__getattr__("build_base");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(107);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), PyString.fromInterned("bdist.")._add(var1.getlocal(0).__getattr__("plat_name")));
         var1.getlocal(0).__setattr__("bdist_base", var3);
         var3 = null;
      }

      var1.setline(110);
      var1.getlocal(0).__getattr__("ensure_string_list").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("formats"));
      var1.setline(111);
      var3 = var1.getlocal(0).__getattr__("formats");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         try {
            var1.setline(113);
            PyList var6 = new PyList(new PyObject[]{var1.getlocal(0).__getattr__("default_format").__getitem__(var1.getglobal("os").__getattr__("name"))});
            var1.getlocal(0).__setattr__((String)"formats", var6);
            var3 = null;
         } catch (Throwable var4) {
            PyException var5 = Py.setException(var4, var1);
            if (var5.match(var1.getglobal("KeyError"))) {
               var1.setline(115);
               throw Py.makeException(var1.getglobal("DistutilsPlatformError"), PyString.fromInterned("don't know how to create built distributions ")._add(PyString.fromInterned("on platform %s")._mod(var1.getglobal("os").__getattr__("name"))));
            }

            throw var5;
         }
      }

      var1.setline(119);
      var3 = var1.getlocal(0).__getattr__("dist_dir");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(120);
         PyString var7 = PyString.fromInterned("dist");
         var1.getlocal(0).__setattr__((String)"dist_dir", var7);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$5(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(125);
      PyObject var7 = var1.getlocal(0).__getattr__("formats").__iter__();

      while(true) {
         var1.setline(125);
         PyObject var4 = var7.__iternext__();
         PyException var5;
         if (var4 == null) {
            var1.setline(132);
            var7 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("formats"))).__iter__();

            while(true) {
               var1.setline(132);
               var4 = var7.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(3, var4);
               var1.setline(133);
               PyObject var8 = var1.getlocal(1).__getitem__(var1.getlocal(3));
               var1.setlocal(4, var8);
               var5 = null;
               var1.setline(134);
               var8 = var1.getlocal(0).__getattr__("reinitialize_command").__call__(var2, var1.getlocal(4));
               var1.setlocal(5, var8);
               var5 = null;
               var1.setline(135);
               var8 = var1.getlocal(4);
               PyObject var10000 = var8._notin(var1.getlocal(0).__getattr__("no_format_option"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(136);
                  var8 = var1.getlocal(0).__getattr__("formats").__getitem__(var1.getlocal(3));
                  var1.getlocal(5).__setattr__("format", var8);
                  var5 = null;
               }

               var1.setline(139);
               var8 = var1.getlocal(4);
               var10000 = var8._eq(PyString.fromInterned("bdist_dumb"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(140);
                  var8 = var1.getlocal(0).__getattr__("owner");
                  var1.getlocal(5).__setattr__("owner", var8);
                  var5 = null;
                  var1.setline(141);
                  var8 = var1.getlocal(0).__getattr__("group");
                  var1.getlocal(5).__setattr__("group", var8);
                  var5 = null;
               }

               var1.setline(145);
               var8 = var1.getlocal(4);
               var10000 = var8._in(var1.getlocal(1).__getslice__(var1.getlocal(3)._add(Py.newInteger(1)), (PyObject)null, (PyObject)null));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(146);
                  PyInteger var9 = Py.newInteger(1);
                  var1.getlocal(5).__setattr__((String)"keep_temp", var9);
                  var5 = null;
               }

               var1.setline(147);
               var1.getlocal(0).__getattr__("run_command").__call__(var2, var1.getlocal(4));
            }
         }

         var1.setlocal(2, var4);

         try {
            var1.setline(127);
            var1.getlocal(1).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("format_command").__getitem__(var1.getlocal(2)).__getitem__(Py.newInteger(0)));
         } catch (Throwable var6) {
            var5 = Py.setException(var6, var1);
            if (var5.match(var1.getglobal("KeyError"))) {
               var1.setline(129);
               throw Py.makeException(var1.getglobal("DistutilsOptionError"), PyString.fromInterned("invalid format '%s'")._mod(var1.getlocal(2)));
            }

            throw var5;
         }
      }
   }

   public bdist$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"FancyGetopt", "formats", "format", "pretty_printer"};
      show_formats$1 = Py.newCode(0, var2, var1, "show_formats", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      bdist$2 = Py.newCode(0, var2, var1, "bdist", 27, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$3 = Py.newCode(1, var2, var1, "initialize_options", 85, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "build_base"};
      finalize_options$4 = Py.newCode(1, var2, var1, "finalize_options", 94, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "commands", "format", "i", "cmd_name", "sub_cmd"};
      run$5 = Py.newCode(1, var2, var1, "run", 122, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new bdist$py("distutils/command/bdist$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(bdist$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.show_formats$1(var2, var3);
         case 2:
            return this.bdist$2(var2, var3);
         case 3:
            return this.initialize_options$3(var2, var3);
         case 4:
            return this.finalize_options$4(var2, var3);
         case 5:
            return this.run$5(var2, var3);
         default:
            return null;
      }
   }
}
