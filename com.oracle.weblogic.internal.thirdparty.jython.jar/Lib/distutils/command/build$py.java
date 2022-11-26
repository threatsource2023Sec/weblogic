package distutils.command;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
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
@Filename("distutils/command/build.py")
public class build$py extends PyFunctionTable implements PyRunnable {
   static build$py self;
   static final PyCode f$0;
   static final PyCode show_compilers$1;
   static final PyCode build$2;
   static final PyCode initialize_options$3;
   static final PyCode finalize_options$4;
   static final PyCode run$5;
   static final PyCode has_pure_modules$6;
   static final PyCode has_c_libraries$7;
   static final PyCode has_ext_modules$8;
   static final PyCode has_scripts$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("distutils.command.build\n\nImplements the Distutils 'build' command."));
      var1.setline(3);
      PyString.fromInterned("distutils.command.build\n\nImplements the Distutils 'build' command.");
      var1.setline(5);
      PyString var3 = PyString.fromInterned("$Id$");
      var1.setlocal("__revision__", var3);
      var3 = null;
      var1.setline(7);
      PyObject var5 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var5);
      var3 = null;
      var5 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var5);
      var3 = null;
      var1.setline(9);
      String[] var6 = new String[]{"get_platform"};
      PyObject[] var7 = imp.importFrom("distutils.util", var6, var1, -1);
      PyObject var4 = var7[0];
      var1.setlocal("get_platform", var4);
      var4 = null;
      var1.setline(10);
      var6 = new String[]{"Command"};
      var7 = imp.importFrom("distutils.core", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(11);
      var6 = new String[]{"DistutilsOptionError"};
      var7 = imp.importFrom("distutils.errors", var6, var1, -1);
      var4 = var7[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var1.setline(13);
      var7 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var7, show_compilers$1, (PyObject)null);
      var1.setlocal("show_compilers", var8);
      var3 = null;
      var1.setline(17);
      var7 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("build", var7, build$2);
      var1.setlocal("build", var4);
      var4 = null;
      Arrays.fill(var7, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject show_compilers$1(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      String[] var3 = new String[]{"show_compilers"};
      PyObject[] var5 = imp.importFrom("distutils.ccompiler", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(0, var4);
      var4 = null;
      var1.setline(15);
      var1.getlocal(0).__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject build$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(19);
      PyString var3 = PyString.fromInterned("build everything needed to install");
      var1.setlocal("description", var3);
      var3 = null;
      var1.setline(21);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("build-base="), PyString.fromInterned("b"), PyString.fromInterned("base directory for build library")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-purelib="), var1.getname("None"), PyString.fromInterned("build directory for platform-neutral distributions")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-platlib="), var1.getname("None"), PyString.fromInterned("build directory for platform-specific distributions")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-lib="), var1.getname("None"), PyString.fromInterned("build directory for all distribution (defaults to either ")._add(PyString.fromInterned("build-purelib or build-platlib"))}), new PyTuple(new PyObject[]{PyString.fromInterned("build-scripts="), var1.getname("None"), PyString.fromInterned("build directory for scripts")}), new PyTuple(new PyObject[]{PyString.fromInterned("build-temp="), PyString.fromInterned("t"), PyString.fromInterned("temporary build directory")}), new PyTuple(new PyObject[]{PyString.fromInterned("plat-name="), PyString.fromInterned("p"), PyString.fromInterned("platform name to build for, if supported (default: %s)")._mod(var1.getname("get_platform").__call__(var2))}), new PyTuple(new PyObject[]{PyString.fromInterned("compiler="), PyString.fromInterned("c"), PyString.fromInterned("specify the compiler type")}), new PyTuple(new PyObject[]{PyString.fromInterned("debug"), PyString.fromInterned("g"), PyString.fromInterned("compile extensions and libraries with debugging information")}), new PyTuple(new PyObject[]{PyString.fromInterned("force"), PyString.fromInterned("f"), PyString.fromInterned("forcibly build everything (ignore file timestamps)")}), new PyTuple(new PyObject[]{PyString.fromInterned("executable="), PyString.fromInterned("e"), PyString.fromInterned("specify final destination interpreter path (build.py)")})});
      var1.setlocal("user_options", var4);
      var3 = null;
      var1.setline(48);
      var4 = new PyList(new PyObject[]{PyString.fromInterned("debug"), PyString.fromInterned("force")});
      var1.setlocal("boolean_options", var4);
      var3 = null;
      var1.setline(50);
      var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("help-compiler"), var1.getname("None"), PyString.fromInterned("list available compilers"), var1.getname("show_compilers")})});
      var1.setlocal("help_options", var4);
      var3 = null;
      var1.setline(55);
      PyObject[] var5 = Py.EmptyObjects;
      PyFunction var6 = new PyFunction(var1.f_globals, var5, initialize_options$3, (PyObject)null);
      var1.setlocal("initialize_options", var6);
      var3 = null;
      var1.setline(70);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, finalize_options$4, (PyObject)null);
      var1.setlocal("finalize_options", var6);
      var3 = null;
      var1.setline(120);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, run$5, (PyObject)null);
      var1.setlocal("run", var6);
      var3 = null;
      var1.setline(131);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, has_pure_modules$6, (PyObject)null);
      var1.setlocal("has_pure_modules", var6);
      var3 = null;
      var1.setline(134);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, has_c_libraries$7, (PyObject)null);
      var1.setlocal("has_c_libraries", var6);
      var3 = null;
      var1.setline(137);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, has_ext_modules$8, (PyObject)null);
      var1.setlocal("has_ext_modules", var6);
      var3 = null;
      var1.setline(140);
      var5 = Py.EmptyObjects;
      var6 = new PyFunction(var1.f_globals, var5, has_scripts$9, (PyObject)null);
      var1.setlocal("has_scripts", var6);
      var3 = null;
      var1.setline(143);
      var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("build_py"), var1.getname("has_pure_modules")}), new PyTuple(new PyObject[]{PyString.fromInterned("build_clib"), var1.getname("has_c_libraries")}), new PyTuple(new PyObject[]{PyString.fromInterned("build_ext"), var1.getname("has_ext_modules")}), new PyTuple(new PyObject[]{PyString.fromInterned("build_scripts"), var1.getname("has_scripts")})});
      var1.setlocal("sub_commands", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$3(PyFrame var1, ThreadState var2) {
      var1.setline(56);
      PyString var3 = PyString.fromInterned("build");
      var1.getlocal(0).__setattr__((String)"build_base", var3);
      var3 = null;
      var1.setline(59);
      PyObject var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_purelib", var4);
      var3 = null;
      var1.setline(60);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_platlib", var4);
      var3 = null;
      var1.setline(61);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_lib", var4);
      var3 = null;
      var1.setline(62);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_temp", var4);
      var3 = null;
      var1.setline(63);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("build_scripts", var4);
      var3 = null;
      var1.setline(64);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("compiler", var4);
      var3 = null;
      var1.setline(65);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("plat_name", var4);
      var3 = null;
      var1.setline(66);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("debug", var4);
      var3 = null;
      var1.setline(67);
      PyInteger var5 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"force", var5);
      var3 = null;
      var1.setline(68);
      var4 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("executable", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject finalize_options$4(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyObject var3 = var1.getlocal(0).__getattr__("plat_name");
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(72);
         var3 = var1.getglobal("get_platform").__call__(var2);
         var1.getlocal(0).__setattr__("plat_name", var3);
         var3 = null;
      } else {
         var1.setline(77);
         var3 = var1.getglobal("os").__getattr__("name");
         var10000 = var3._ne(PyString.fromInterned("nt"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(78);
            throw Py.makeException(var1.getglobal("DistutilsOptionError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("--plat-name only supported on Windows (try using './configure --help' on your platform)")));
         }
      }

      var1.setline(82);
      var3 = PyString.fromInterned(".%s-%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("plat_name"), var1.getglobal("sys").__getattr__("version").__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null)}));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(87);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getglobal("sys"), (PyObject)PyString.fromInterned("gettotalrefcount")).__nonzero__()) {
         var1.setline(88);
         var3 = var1.getlocal(1);
         var3 = var3._iadd(PyString.fromInterned("-pydebug"));
         var1.setlocal(1, var3);
      }

      var1.setline(93);
      var3 = var1.getlocal(0).__getattr__("build_purelib");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(94);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("build_base"), (PyObject)PyString.fromInterned("lib"));
         var1.getlocal(0).__setattr__("build_purelib", var3);
         var3 = null;
      }

      var1.setline(95);
      var3 = var1.getlocal(0).__getattr__("build_platlib");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(96);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("build_base"), PyString.fromInterned("lib")._add(var1.getlocal(1)));
         var1.getlocal(0).__setattr__("build_platlib", var3);
         var3 = null;
      }

      var1.setline(102);
      var3 = var1.getlocal(0).__getattr__("build_lib");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(103);
         if (var1.getlocal(0).__getattr__("distribution").__getattr__("ext_modules").__nonzero__()) {
            var1.setline(104);
            var3 = var1.getlocal(0).__getattr__("build_platlib");
            var1.getlocal(0).__setattr__("build_lib", var3);
            var3 = null;
         } else {
            var1.setline(106);
            var3 = var1.getlocal(0).__getattr__("build_purelib");
            var1.getlocal(0).__setattr__("build_lib", var3);
            var3 = null;
         }
      }

      var1.setline(110);
      var3 = var1.getlocal(0).__getattr__("build_temp");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(111);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("build_base"), PyString.fromInterned("temp")._add(var1.getlocal(1)));
         var1.getlocal(0).__setattr__("build_temp", var3);
         var3 = null;
      }

      var1.setline(113);
      var3 = var1.getlocal(0).__getattr__("build_scripts");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(114);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("build_base"), PyString.fromInterned("scripts-")._add(var1.getglobal("sys").__getattr__("version").__getslice__(Py.newInteger(0), Py.newInteger(3), (PyObject)null)));
         var1.getlocal(0).__setattr__("build_scripts", var3);
         var3 = null;
      }

      var1.setline(117);
      var3 = var1.getlocal(0).__getattr__("executable");
      var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(118);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getglobal("sys").__getattr__("executable"));
         var1.getlocal(0).__setattr__("executable", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject run$5(PyFrame var1, ThreadState var2) {
      var1.setline(126);
      PyObject var3 = var1.getlocal(0).__getattr__("get_sub_commands").__call__(var2).__iter__();

      while(true) {
         var1.setline(126);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(1, var4);
         var1.setline(127);
         var1.getlocal(0).__getattr__("run_command").__call__(var2, var1.getlocal(1));
      }
   }

   public PyObject has_pure_modules$6(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_pure_modules").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_c_libraries$7(PyFrame var1, ThreadState var2) {
      var1.setline(135);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_c_libraries").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_ext_modules$8(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_ext_modules").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject has_scripts$9(PyFrame var1, ThreadState var2) {
      var1.setline(141);
      PyObject var3 = var1.getlocal(0).__getattr__("distribution").__getattr__("has_scripts").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public build$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"show_compilers"};
      show_compilers$1 = Py.newCode(0, var2, var1, "show_compilers", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      build$2 = Py.newCode(0, var2, var1, "build", 17, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$3 = Py.newCode(1, var2, var1, "initialize_options", 55, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "plat_specifier"};
      finalize_options$4 = Py.newCode(1, var2, var1, "finalize_options", 70, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cmd_name"};
      run$5 = Py.newCode(1, var2, var1, "run", 120, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_pure_modules$6 = Py.newCode(1, var2, var1, "has_pure_modules", 131, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_c_libraries$7 = Py.newCode(1, var2, var1, "has_c_libraries", 134, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_ext_modules$8 = Py.newCode(1, var2, var1, "has_ext_modules", 137, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      has_scripts$9 = Py.newCode(1, var2, var1, "has_scripts", 140, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new build$py("distutils/command/build$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(build$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.show_compilers$1(var2, var3);
         case 2:
            return this.build$2(var2, var3);
         case 3:
            return this.initialize_options$3(var2, var3);
         case 4:
            return this.finalize_options$4(var2, var3);
         case 5:
            return this.run$5(var2, var3);
         case 6:
            return this.has_pure_modules$6(var2, var3);
         case 7:
            return this.has_c_libraries$7(var2, var3);
         case 8:
            return this.has_ext_modules$8(var2, var3);
         case 9:
            return this.has_scripts$9(var2, var3);
         default:
            return null;
      }
   }
}
