package distutils.tests;

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
@MTime(1498849383000L)
@Filename("distutils/tests/test_build_ext.py")
public class test_build_ext$py extends PyFunctionTable implements PyRunnable {
   static test_build_ext$py self;
   static final PyCode f$0;
   static final PyCode BuildExtTestCase$1;
   static final PyCode setUp$2;
   static final PyCode tearDown$3;
   static final PyCode test_build_ext$4;
   static final PyCode test_solaris_enable_shared$5;
   static final PyCode test_user_site$6;
   static final PyCode test_finalize_options$7;
   static final PyCode test_check_extensions_list$8;
   static final PyCode test_get_source_files$9;
   static final PyCode test_compiler_option$10;
   static final PyCode test_get_outputs$11;
   static final PyCode test_ext_fullpath$12;
   static final PyCode test_build_ext_inplace$13;
   static final PyCode test_setuptools_compat$14;
   static final PyCode test_build_ext_path_with_os_sep$15;
   static final PyCode test_build_ext_path_cross_platform$16;
   static final PyCode test_deployment_target_default$17;
   static final PyCode test_deployment_target_too_low$18;
   static final PyCode test_deployment_target_higher_ok$19;
   static final PyCode f$20;
   static final PyCode _try_compile_deployment_target$21;
   static final PyCode test_suite$22;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(2);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      String[] var5 = new String[]{"StringIO"};
      PyObject[] var6 = imp.importFrom("StringIO", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringIO", var4);
      var4 = null;
      var1.setline(4);
      var3 = imp.importOne("textwrap", var1, -1);
      var1.setlocal("textwrap", var3);
      var3 = null;
      var1.setline(6);
      var5 = new String[]{"Extension", "Distribution"};
      var6 = imp.importFrom("distutils.core", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Extension", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"build_ext"};
      var6 = imp.importFrom("distutils.command.build_ext", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("build_ext", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"sysconfig"};
      var6 = imp.importFrom("distutils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("sysconfig", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"DistutilsSetupError", "CompileError", "DistutilsPlatformError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsSetupError", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("CompileError", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("DistutilsPlatformError", var4);
      var4 = null;
      var1.setline(13);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(14);
      var5 = new String[]{"test_support"};
      var6 = imp.importFrom("test", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("test_support", var4);
      var4 = null;
      var1.setline(18);
      var3 = var1.getname("False");
      var1.setlocal("ALREADY_TESTED", var3);
      var3 = null;
      var1.setline(21);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("BuildExtTestCase", var6, BuildExtTestCase$1);
      var1.setlocal("BuildExtTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(507);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$22, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(510);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(511);
         var1.getname("test_support").__getattr__("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BuildExtTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(24);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var3, setUp$2, (PyObject)null);
      var1.setlocal("setUp", var5);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, tearDown$3, (PyObject)null);
      var1.setlocal("tearDown", var5);
      var3 = null;
      var1.setline(44);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_build_ext$4, (PyObject)null);
      var1.setlocal("test_build_ext", var5);
      var3 = null;
      var1.setline(86);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_solaris_enable_shared$5, (PyObject)null);
      var1.setlocal("test_solaris_enable_shared", var5);
      var3 = null;
      var1.setline(107);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_user_site$6, (PyObject)null);
      var1.setlocal("test_user_site", var5);
      var3 = null;
      var1.setline(137);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_finalize_options$7, (PyObject)null);
      var1.setlocal("test_finalize_options", var5);
      var3 = null;
      var1.setline(200);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_check_extensions_list$8, (PyObject)null);
      var1.setlocal("test_check_extensions_list", var5);
      var3 = null;
      var1.setline(247);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_get_source_files$9, (PyObject)null);
      var1.setlocal("test_get_source_files", var5);
      var3 = null;
      var1.setline(254);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_compiler_option$10, (PyObject)null);
      var1.setlocal("test_compiler_option", var5);
      var3 = null;
      var1.setline(265);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_get_outputs$11, (PyObject)null);
      var1.setlocal("test_get_outputs", var5);
      var3 = null;
      var1.setline(328);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_ext_fullpath$12, (PyObject)null);
      var1.setlocal("test_ext_fullpath", var5);
      var3 = null;
      var1.setline(362);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_build_ext_inplace$13, (PyObject)null);
      var1.setlocal("test_build_ext_inplace", var5);
      var3 = null;
      var1.setline(377);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_setuptools_compat$14, (PyObject)null);
      var1.setlocal("test_setuptools_compat", var5);
      var3 = null;
      var1.setline(407);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_build_ext_path_with_os_sep$15, (PyObject)null);
      var1.setlocal("test_build_ext_path_with_os_sep", var5);
      var3 = null;
      var1.setline(417);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_build_ext_path_cross_platform$16, (PyObject)null);
      var1.setlocal("test_build_ext_path_cross_platform", var5);
      var3 = null;
      var1.setline(430);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_deployment_target_default$17, (PyObject)null);
      PyObject var10000 = var1.getname("unittest").__getattr__("skipUnless");
      PyObject var4 = var1.getname("sys").__getattr__("platform");
      PyObject var10002 = var4._eq(PyString.fromInterned("darwin"));
      var4 = null;
      PyObject var6 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("test only relevant for MacOSX")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_deployment_target_default", var6);
      var3 = null;
      var1.setline(437);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_deployment_target_too_low$18, (PyObject)null);
      var10000 = var1.getname("unittest").__getattr__("skipUnless");
      var4 = var1.getname("sys").__getattr__("platform");
      var10002 = var4._eq(PyString.fromInterned("darwin"));
      var4 = null;
      var6 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("test only relevant for MacOSX")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_deployment_target_too_low", var6);
      var3 = null;
      var1.setline(444);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, test_deployment_target_higher_ok$19, (PyObject)null);
      var10000 = var1.getname("unittest").__getattr__("skipUnless");
      var4 = var1.getname("sys").__getattr__("platform");
      var10002 = var4._eq(PyString.fromInterned("darwin"));
      var4 = null;
      var6 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)PyString.fromInterned("test only relevant for MacOSX")).__call__((ThreadState)var2, (PyObject)var5);
      var1.setlocal("test_deployment_target_higher_ok", var6);
      var3 = null;
      var1.setline(457);
      var3 = Py.EmptyObjects;
      var5 = new PyFunction(var1.f_globals, var3, _try_compile_deployment_target$21, (PyObject)null);
      var1.setlocal("_try_compile_deployment_target", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$2(PyFrame var1, ThreadState var2) {
      var1.setline(25);
      var1.getglobal("super").__call__(var2, var1.getglobal("BuildExtTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(26);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.getlocal(0).__setattr__("tmp_dir", var3);
      var3 = null;
      var1.setline(27);
      var3 = var1.getglobal("False");
      var1.getlocal(0).__setattr__("xx_created", var3);
      var3 = null;
      var1.setline(28);
      var1.getglobal("sys").__getattr__("path").__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("tmp_dir"));
      var1.setline(29);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getglobal("sys").__getattr__("path").__getattr__("remove"), var1.getlocal(0).__getattr__("tmp_dir"));
      var1.setline(30);
      var3 = var1.getglobal("sys").__getattr__("version");
      PyObject var10000 = var3._gt(PyString.fromInterned("2.6"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(31);
         var3 = imp.importOne("site", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(32);
         var3 = var1.getlocal(1).__getattr__("USER_BASE");
         var1.getlocal(0).__setattr__("old_user_base", var3);
         var3 = null;
         var1.setline(33);
         var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
         var1.getlocal(1).__setattr__("USER_BASE", var3);
         var3 = null;
         var1.setline(34);
         String[] var5 = new String[]{"build_ext"};
         PyObject[] var6 = imp.importFrom("distutils.command", var5, var1, -1);
         PyObject var4 = var6[0];
         var1.setlocal(2, var4);
         var4 = null;
         var1.setline(35);
         var3 = var1.getlocal(1).__getattr__("USER_BASE");
         var1.getlocal(2).__setattr__("USER_BASE", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$3(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      if (var1.getlocal(0).__getattr__("xx_created").__nonzero__()) {
         var1.setline(39);
         var1.getglobal("test_support").__getattr__("unload").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xx"));
      }

      var1.setline(42);
      var1.getglobal("super").__call__(var2, var1.getglobal("BuildExtTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_build_ext$4(PyFrame var1, ThreadState var2) {
      var1.setline(46);
      var1.getglobal("support").__getattr__("copy_xxmodule_c").__call__(var2, var1.getlocal(0).__getattr__("tmp_dir"));
      var1.setline(47);
      PyObject var3 = var1.getglobal("True");
      var1.getlocal(0).__setattr__("xx_created", var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("xxmodule.c"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(49);
      var3 = var1.getglobal("Extension").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xx"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)})));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(50);
      var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("xx"), PyString.fromInterned("ext_modules"), new PyList(new PyObject[]{var1.getlocal(2)})})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(51);
      var3 = var1.getlocal(0).__getattr__("tmp_dir");
      var1.getlocal(3).__setattr__("package_dir", var3);
      var3 = null;
      var1.setline(52);
      var3 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(53);
      var1.getglobal("support").__getattr__("fixup_build_ext").__call__(var2, var1.getlocal(4));
      var1.setline(54);
      var3 = var1.getlocal(0).__getattr__("tmp_dir");
      var1.getlocal(4).__setattr__("build_lib", var3);
      var3 = null;
      var1.setline(55);
      var3 = var1.getlocal(0).__getattr__("tmp_dir");
      var1.getlocal(4).__setattr__("build_temp", var3);
      var3 = null;
      var1.setline(57);
      var3 = var1.getglobal("sys").__getattr__("stdout");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(58);
      if (var1.getglobal("test_support").__getattr__("verbose").__not__().__nonzero__()) {
         var1.setline(60);
         var3 = var1.getglobal("StringIO").__call__(var2);
         var1.getglobal("sys").__setattr__("stdout", var3);
         var3 = null;
      }

      var3 = null;

      PyObject var4;
      try {
         var1.setline(62);
         var1.getlocal(4).__getattr__("ensure_finalized").__call__(var2);
         var1.setline(63);
         var1.getlocal(4).__getattr__("run").__call__(var2);
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(65);
         var4 = var1.getlocal(5);
         var1.getglobal("sys").__setattr__("stdout", var4);
         var4 = null;
         throw (Throwable)var5;
      }

      var1.setline(65);
      var4 = var1.getlocal(5);
      var1.getglobal("sys").__setattr__("stdout", var4);
      var4 = null;
      var1.setline(67);
      if (var1.getglobal("ALREADY_TESTED").__nonzero__()) {
         var1.setline(68);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(70);
         var3 = var1.getglobal("True");
         var1.setglobal("ALREADY_TESTED", var3);
         var3 = null;
         var1.setline(72);
         var3 = imp.importOne("xx", var1, -1);
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(74);
         var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("error"), PyString.fromInterned("foo"), PyString.fromInterned("new"), PyString.fromInterned("roj")})).__iter__();

         while(true) {
            var1.setline(74);
            var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(77);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("foo").__call__((ThreadState)var2, (PyObject)Py.newInteger(2), (PyObject)Py.newInteger(5)), (PyObject)Py.newInteger(7));
               var1.setline(78);
               var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(6).__getattr__("foo").__call__((ThreadState)var2, (PyObject)Py.newInteger(13), (PyObject)Py.newInteger(15)), (PyObject)Py.newInteger(28));
               var1.setline(79);
               var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(6).__getattr__("new").__call__(var2).__getattr__("demo").__call__(var2), var1.getglobal("None"));
               var1.setline(80);
               if (var1.getglobal("test_support").__getattr__("HAVE_DOCSTRINGS").__nonzero__()) {
                  var1.setline(81);
                  PyString var6 = PyString.fromInterned("This is a template module just for instruction.");
                  var1.setlocal(8, var6);
                  var3 = null;
                  var1.setline(82);
                  var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(6).__getattr__("__doc__"), var1.getlocal(8));
               }

               var1.setline(83);
               var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(6).__getattr__("Null").__call__(var2), var1.getlocal(6).__getattr__("Null")));
               var1.setline(84);
               var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(6).__getattr__("Str").__call__(var2), var1.getlocal(6).__getattr__("Str")));
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(7, var4);
            var1.setline(75);
            var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("hasattr").__call__(var2, var1.getlocal(6), var1.getlocal(7)));
         }
      }
   }

   public PyObject test_solaris_enable_shared$5(PyFrame var1, ThreadState var2) {
      var1.setline(87);
      PyObject var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("xx")})));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(89);
      var3 = var1.getglobal("sys").__getattr__("platform");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(91);
      PyString var6 = PyString.fromInterned("sunos");
      var1.getglobal("sys").__setattr__((String)"platform", var6);
      var3 = null;
      var1.setline(92);
      String[] var7 = new String[]{"_config_vars"};
      PyObject[] var8 = imp.importFrom("distutils.sysconfig", var7, var1, -1);
      PyObject var4 = var8[0];
      var1.setlocal(4, var4);
      var4 = null;
      var1.setline(93);
      var3 = var1.getlocal(4).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Py_ENABLE_SHARED"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(94);
      PyInteger var9 = Py.newInteger(1);
      var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("Py_ENABLE_SHARED"), var9);
      var3 = null;
      var3 = null;

      PyObject var10000;
      try {
         var1.setline(96);
         var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(98);
         var4 = var1.getlocal(3);
         var1.getglobal("sys").__setattr__("platform", var4);
         var4 = null;
         var1.setline(99);
         var4 = var1.getlocal(5);
         var10000 = var4._is(var1.getglobal("None"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(100);
            var1.getlocal(4).__delitem__((PyObject)PyString.fromInterned("Py_ENABLE_SHARED"));
         } else {
            var1.setline(102);
            var4 = var1.getlocal(5);
            var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("Py_ENABLE_SHARED"), var4);
            var4 = null;
         }

         throw (Throwable)var5;
      }

      var1.setline(98);
      var4 = var1.getlocal(3);
      var1.getglobal("sys").__setattr__("platform", var4);
      var4 = null;
      var1.setline(99);
      var4 = var1.getlocal(5);
      var10000 = var4._is(var1.getglobal("None"));
      var4 = null;
      if (var10000.__nonzero__()) {
         var1.setline(100);
         var1.getlocal(4).__delitem__((PyObject)PyString.fromInterned("Py_ENABLE_SHARED"));
      } else {
         var1.setline(102);
         var4 = var1.getlocal(5);
         var1.getlocal(4).__setitem__((PyObject)PyString.fromInterned("Py_ENABLE_SHARED"), var4);
         var4 = null;
      }

      var1.setline(105);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2).__getattr__("library_dirs"));
      PyObject var10002 = var3._gt(Py.newInteger(0));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_user_site$6(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyObject var3 = var1.getglobal("sys").__getattr__("version");
      PyObject var10000 = var3._lt(PyString.fromInterned("2.6"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(110);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(112);
         var3 = imp.importOne("site", var1, -1);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(113);
         var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("xx")})));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(114);
         var3 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(117);
         PyList var9 = new PyList();
         var3 = var9.__getattr__("append");
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(117);
         var3 = var1.getlocal(3).__getattr__("user_options").__iter__();

         while(true) {
            var1.setline(117);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(117);
               var1.dellocal(5);
               PyList var7 = var9;
               var1.setlocal(4, var7);
               var3 = null;
               var1.setline(119);
               var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("user"), (PyObject)var1.getlocal(4));
               var1.setline(122);
               PyInteger var8 = Py.newInteger(1);
               var1.getlocal(3).__setattr__((String)"user", var8);
               var3 = null;
               var1.setline(125);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("USER_BASE"), (PyObject)PyString.fromInterned("lib"));
               var1.setlocal(9, var3);
               var3 = null;
               var1.setline(126);
               var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("USER_BASE"), (PyObject)PyString.fromInterned("include"));
               var1.setlocal(10, var3);
               var3 = null;
               var1.setline(127);
               var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(9));
               var1.setline(128);
               var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(10));
               var1.setline(130);
               var1.getlocal(3).__getattr__("ensure_finalized").__call__(var2);
               var1.setline(133);
               var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getlocal(9), var1.getlocal(3).__getattr__("library_dirs"));
               var1.setline(134);
               var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getlocal(9), var1.getlocal(3).__getattr__("rpath"));
               var1.setline(135);
               var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getlocal(10), var1.getlocal(3).__getattr__("include_dirs"));
               var1.f_lasti = -1;
               return Py.None;
            }

            PyObject[] var5 = Py.unpackSequence(var4, 3);
            PyObject var6 = var5[0];
            var1.setlocal(6, var6);
            var6 = null;
            var6 = var5[1];
            var1.setlocal(7, var6);
            var6 = null;
            var6 = var5[2];
            var1.setlocal(8, var6);
            var6 = null;
            var1.setline(117);
            var1.getlocal(5).__call__(var2, var1.getlocal(6));
         }
      }
   }

   public PyObject test_finalize_options$7(PyFrame var1, ThreadState var2) {
      var1.setline(140);
      PyList var3 = new PyList(new PyObject[]{var1.getglobal("Extension").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("xxx")})))});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(141);
      PyObject var5 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("xx"), PyString.fromInterned("ext_modules"), var1.getlocal(1)})));
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(142);
      var5 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(143);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(145);
      var5 = var1.getglobal("sysconfig").__getattr__("get_python_inc").__call__(var2);
      var1.setlocal(4, var5);
      var3 = null;
      var1.setline(146);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var5 = var1.getlocal(4);
      PyObject var10002 = var5._in(var1.getlocal(3).__getattr__("include_dirs"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(148);
      var10000 = var1.getglobal("sysconfig").__getattr__("get_python_inc");
      PyObject[] var6 = new PyObject[]{Py.newInteger(1)};
      String[] var4 = new String[]{"plat_specific"};
      var10000 = var10000.__call__(var2, var6, var4);
      var3 = null;
      var5 = var10000;
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(149);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var5 = var1.getlocal(5);
      var10002 = var5._in(var1.getlocal(3).__getattr__("include_dirs"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(153);
      var5 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(154);
      PyString var7 = PyString.fromInterned("my_lib, other_lib lastlib");
      var1.getlocal(3).__setattr__((String)"libraries", var7);
      var3 = null;
      var1.setline(155);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(156);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("libraries"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("my_lib"), PyString.fromInterned("other_lib"), PyString.fromInterned("lastlib")})));
      var1.setline(160);
      var5 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(161);
      var5 = PyString.fromInterned("my_lib_dir%sother_lib_dir")._mod(var1.getglobal("os").__getattr__("pathsep"));
      var1.getlocal(3).__setattr__("library_dirs", var5);
      var3 = null;
      var1.setline(162);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(163);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("my_lib_dir"), (PyObject)var1.getlocal(3).__getattr__("library_dirs"));
      var1.setline(164);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("other_lib_dir"), (PyObject)var1.getlocal(3).__getattr__("library_dirs"));
      var1.setline(168);
      var5 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(169);
      var5 = PyString.fromInterned("one%stwo")._mod(var1.getglobal("os").__getattr__("pathsep"));
      var1.getlocal(3).__setattr__("rpath", var5);
      var3 = null;
      var1.setline(170);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(171);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("rpath"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("one"), PyString.fromInterned("two")})));
      var1.setline(177);
      var5 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(178);
      var7 = PyString.fromInterned("one,two");
      var1.getlocal(3).__setattr__((String)"define", var7);
      var3 = null;
      var1.setline(179);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(180);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("define"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("one"), PyString.fromInterned("1")}), new PyTuple(new PyObject[]{PyString.fromInterned("two"), PyString.fromInterned("1")})})));
      var1.setline(184);
      var5 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(185);
      var7 = PyString.fromInterned("one,two");
      var1.getlocal(3).__setattr__((String)"undef", var7);
      var3 = null;
      var1.setline(186);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(187);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("undef"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("one"), PyString.fromInterned("two")})));
      var1.setline(190);
      var5 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(191);
      var5 = var1.getglobal("None");
      var1.getlocal(3).__setattr__("swig_opts", var5);
      var3 = null;
      var1.setline(192);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(193);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("swig_opts"), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(195);
      var5 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(196);
      var7 = PyString.fromInterned("1 2");
      var1.getlocal(3).__setattr__((String)"swig_opts", var7);
      var3 = null;
      var1.setline(197);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(198);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("swig_opts"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("1"), PyString.fromInterned("2")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_check_extensions_list$8(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyObject var3 = var1.getglobal("Distribution").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(202);
      var3 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(203);
      var1.getlocal(2).__getattr__("finalize_options").__call__(var2);
      var1.setline(206);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsSetupError"), (PyObject)var1.getlocal(2).__getattr__("check_extensions_list"), (PyObject)PyString.fromInterned("foo"));
      var1.setline(210);
      PyList var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("bar"), PyString.fromInterned("foo"), PyString.fromInterned("bar")}), PyString.fromInterned("foo")});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(211);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsSetupError"), var1.getlocal(2).__getattr__("check_extensions_list"), var1.getlocal(3));
      var1.setline(216);
      var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("foo-bar"), PyString.fromInterned("")})});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(217);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsSetupError"), var1.getlocal(2).__getattr__("check_extensions_list"), var1.getlocal(3));
      var1.setline(221);
      var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("foo.bar"), PyString.fromInterned("")})});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(222);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsSetupError"), var1.getlocal(2).__getattr__("check_extensions_list"), var1.getlocal(3));
      var1.setline(225);
      var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("foo.bar"), new PyDictionary(new PyObject[]{PyString.fromInterned("sources"), new PyList(new PyObject[]{PyString.fromInterned("")}), PyString.fromInterned("libraries"), PyString.fromInterned("foo"), PyString.fromInterned("some"), PyString.fromInterned("bar")})})});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(227);
      var1.getlocal(2).__getattr__("check_extensions_list").__call__(var2, var1.getlocal(3));
      var1.setline(228);
      var3 = var1.getlocal(3).__getitem__(Py.newInteger(0));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(229);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("isinstance").__call__(var2, var1.getlocal(4), var1.getglobal("Extension")));
      var1.setline(234);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(4).__getattr__("libraries"), (PyObject)PyString.fromInterned("foo"));
      var1.setline(235);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("some")).__not__());
      var1.setline(238);
      var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("foo.bar"), new PyDictionary(new PyObject[]{PyString.fromInterned("sources"), new PyList(new PyObject[]{PyString.fromInterned("")}), PyString.fromInterned("libraries"), PyString.fromInterned("foo"), PyString.fromInterned("some"), PyString.fromInterned("bar"), PyString.fromInterned("macros"), new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("1"), PyString.fromInterned("2"), PyString.fromInterned("3")}), PyString.fromInterned("foo")})})})});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(240);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsSetupError"), var1.getlocal(2).__getattr__("check_extensions_list"), var1.getlocal(3));
      var1.setline(242);
      var4 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("1"), PyString.fromInterned("2")}), new PyTuple(new PyObject[]{PyString.fromInterned("3")})});
      var1.getlocal(3).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)).__setitem__((PyObject)PyString.fromInterned("macros"), var4);
      var3 = null;
      var1.setline(243);
      var1.getlocal(2).__getattr__("check_extensions_list").__call__(var2, var1.getlocal(3));
      var1.setline(244);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("undef_macros"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("3")})));
      var1.setline(245);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getitem__(Py.newInteger(0)).__getattr__("define_macros"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("1"), PyString.fromInterned("2")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_source_files$9(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyList var3 = new PyList(new PyObject[]{var1.getglobal("Extension").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("xxx")})))});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(249);
      PyObject var4 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("xx"), PyString.fromInterned("ext_modules"), var1.getlocal(1)})));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(250);
      var4 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(251);
      var1.getlocal(3).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(252);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_source_files").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("xxx")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_compiler_option$10(PyFrame var1, ThreadState var2) {
      var1.setline(258);
      PyObject var3 = var1.getglobal("Distribution").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(259);
      var3 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(260);
      PyString var4 = PyString.fromInterned("unix");
      var1.getlocal(2).__setattr__((String)"compiler", var4);
      var3 = null;
      var1.setline(261);
      var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(262);
      var1.getlocal(2).__getattr__("run").__call__(var2);
      var1.setline(263);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("compiler"), (PyObject)PyString.fromInterned("unix"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_outputs$11(PyFrame var1, ThreadState var2) {
      var1.setline(266);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(267);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo.c"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(268);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("void initfoo(void) {};\n"));
      var1.setline(269);
      var3 = var1.getglobal("Extension").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(2)})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(270);
      var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("xx"), PyString.fromInterned("ext_modules"), new PyList(new PyObject[]{var1.getlocal(3)})})));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(272);
      var3 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(273);
      var1.getglobal("support").__getattr__("fixup_build_ext").__call__(var2, var1.getlocal(5));
      var1.setline(274);
      var1.getlocal(5).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(275);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getlocal(5).__getattr__("get_outputs").__call__(var2)), (PyObject)Py.newInteger(1));
      var1.setline(277);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("build"));
      var1.getlocal(5).__setattr__("build_lib", var3);
      var3 = null;
      var1.setline(278);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("tempt"));
      var1.getlocal(5).__setattr__("build_temp", var3);
      var3 = null;
      var1.setline(282);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("realpath").__call__(var2, var1.getlocal(0).__getattr__("mkdtemp").__call__(var2));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(283);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(284);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(6));
      var3 = null;

      PyInteger var4;
      PyObject var7;
      try {
         var1.setline(286);
         var4 = Py.newInteger(1);
         var1.getlocal(5).__setattr__((String)"inplace", var4);
         var4 = null;
         var1.setline(287);
         var1.getlocal(5).__getattr__("run").__call__(var2);
         var1.setline(288);
         var7 = var1.getlocal(5).__getattr__("get_outputs").__call__(var2).__getitem__(Py.newInteger(0));
         var1.setlocal(8, var7);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(290);
         var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(7));
         throw (Throwable)var6;
      }

      var1.setline(290);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(7));
      var1.setline(291);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(8)));
      var1.setline(292);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(8)).__getitem__(Py.newInteger(-1)), var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SO")));
      var1.setline(294);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(8));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(295);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(9), var1.getlocal(6));
      var1.setline(296);
      var3 = var1.getglobal("None");
      var1.getlocal(5).__setattr__("compiler", var3);
      var3 = null;
      var1.setline(297);
      PyInteger var8 = Py.newInteger(0);
      var1.getlocal(5).__setattr__((String)"inplace", var8);
      var3 = null;
      var1.setline(298);
      var1.getlocal(5).__getattr__("run").__call__(var2);
      var1.setline(299);
      var3 = var1.getlocal(5).__getattr__("get_outputs").__call__(var2).__getitem__(Py.newInteger(0));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(300);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(8)));
      var1.setline(301);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("splitext").__call__(var2, var1.getlocal(8)).__getitem__(Py.newInteger(-1)), var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SO")));
      var1.setline(303);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getlocal(8));
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(304);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(9), var1.getlocal(5).__getattr__("build_lib"));
      var1.setline(307);
      var3 = var1.getlocal(5).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_py"));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(308);
      PyDictionary var9 = new PyDictionary(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("bar")});
      var1.getlocal(10).__setattr__((String)"package_dir", var9);
      var3 = null;
      var1.setline(309);
      var3 = var1.getlocal(5).__getattr__("get_ext_fullpath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(311);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(11)).__getitem__(Py.newInteger(0));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(312);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(11), var1.getlocal(5).__getattr__("build_lib"));
      var1.setline(315);
      var8 = Py.newInteger(1);
      var1.getlocal(5).__setattr__((String)"inplace", var8);
      var3 = null;
      var1.setline(316);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("realpath").__call__(var2, var1.getlocal(0).__getattr__("mkdtemp").__call__(var2));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(317);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(318);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(6));
      var3 = null;

      try {
         var1.setline(320);
         var7 = var1.getlocal(5).__getattr__("get_ext_fullpath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("foo"));
         var1.setlocal(11, var7);
         var4 = null;
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(322);
         var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(7));
         throw (Throwable)var5;
      }

      var1.setline(322);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(7));
      var1.setline(324);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(11)).__getitem__(Py.newInteger(0));
      var1.setlocal(11, var3);
      var3 = null;
      var1.setline(325);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getlocal(11)).__getitem__(Py.newInteger(-1));
      var1.setlocal(12, var3);
      var3 = null;
      var1.setline(326);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(12), (PyObject)PyString.fromInterned("bar"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_ext_fullpath$12(PyFrame var1, ThreadState var2) {
      var1.setline(329);
      PyObject var3 = var1.getglobal("sysconfig").__getattr__("get_config_vars").__call__(var2).__getitem__(PyString.fromInterned("SO"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(330);
      var3 = var1.getglobal("Distribution").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(331);
      var3 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(332);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(3).__setattr__((String)"inplace", var4);
      var3 = null;
      var1.setline(333);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("src")});
      var1.getlocal(3).__getattr__("distribution").__setattr__((String)"package_dir", var5);
      var3 = null;
      var1.setline(334);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("lxml"), PyString.fromInterned("lxml.html")});
      var1.getlocal(3).__getattr__("distribution").__setattr__((String)"packages", var6);
      var3 = null;
      var1.setline(335);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(336);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), PyString.fromInterned("src"), PyString.fromInterned("lxml"), PyString.fromInterned("etree")._add(var1.getlocal(1)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(337);
      var3 = var1.getlocal(3).__getattr__("get_ext_fullpath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lxml.etree"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(338);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5), var1.getlocal(6));
      var1.setline(341);
      var4 = Py.newInteger(0);
      var1.getlocal(3).__setattr__((String)"inplace", var4);
      var3 = null;
      var1.setline(342);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("tmpdir"));
      var1.getlocal(3).__setattr__("build_lib", var3);
      var3 = null;
      var1.setline(343);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), PyString.fromInterned("tmpdir"), PyString.fromInterned("lxml"), PyString.fromInterned("etree")._add(var1.getlocal(1)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(344);
      var3 = var1.getlocal(3).__getattr__("get_ext_fullpath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lxml.etree"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(345);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5), var1.getlocal(6));
      var1.setline(348);
      var3 = var1.getlocal(3).__getattr__("get_finalized_command").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build_py"));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(349);
      var5 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(7).__setattr__((String)"package_dir", var5);
      var3 = null;
      var1.setline(350);
      var6 = new PyList(new PyObject[]{PyString.fromInterned("twisted"), PyString.fromInterned("twisted.runner.portmap")});
      var1.getlocal(3).__getattr__("distribution").__setattr__((String)"packages", var6);
      var3 = null;
      var1.setline(351);
      var3 = var1.getlocal(3).__getattr__("get_ext_fullpath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("twisted.runner.portmap"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(352);
      PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
      PyObject[] var7 = new PyObject[]{var1.getlocal(4), PyString.fromInterned("tmpdir"), PyString.fromInterned("twisted"), PyString.fromInterned("runner"), PyString.fromInterned("portmap")._add(var1.getlocal(1))};
      var3 = var10000.__call__(var2, var7);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(354);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5), var1.getlocal(6));
      var1.setline(357);
      var4 = Py.newInteger(1);
      var1.getlocal(3).__setattr__((String)"inplace", var4);
      var3 = null;
      var1.setline(358);
      var3 = var1.getlocal(3).__getattr__("get_ext_fullpath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("twisted.runner.portmap"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(359);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(4), PyString.fromInterned("twisted"), PyString.fromInterned("runner"), PyString.fromInterned("portmap")._add(var1.getlocal(1)));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(360);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5), var1.getlocal(6));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_build_ext_inplace$13(PyFrame var1, ThreadState var2) {
      var1.setline(363);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("lxml.etree.c"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(364);
      var3 = var1.getglobal("Extension").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lxml.etree"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(1)})));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(365);
      var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("lxml"), PyString.fromInterned("ext_modules"), new PyList(new PyObject[]{var1.getlocal(2)})})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(366);
      var3 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(367);
      var1.getlocal(4).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(368);
      PyInteger var4 = Py.newInteger(1);
      var1.getlocal(4).__setattr__((String)"inplace", var4);
      var3 = null;
      var1.setline(369);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("src")});
      var1.getlocal(4).__getattr__("distribution").__setattr__((String)"package_dir", var5);
      var3 = null;
      var1.setline(370);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("lxml"), PyString.fromInterned("lxml.html")});
      var1.getlocal(4).__getattr__("distribution").__setattr__((String)"packages", var6);
      var3 = null;
      var1.setline(371);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(372);
      var3 = var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SO"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(373);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(5), PyString.fromInterned("src"), PyString.fromInterned("lxml"), PyString.fromInterned("etree")._add(var1.getlocal(6)));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(374);
      var3 = var1.getlocal(4).__getattr__("get_ext_fullpath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lxml.etree"));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(375);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(7), var1.getlocal(8));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_setuptools_compat$14(PyFrame var1, ThreadState var2) {
      var1.setline(378);
      PyObject var3 = imp.importOne("distutils.core", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var3 = imp.importOne("distutils.extension", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var3 = imp.importOne("distutils.command.build_ext", var1, -1);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(379);
      var3 = var1.getlocal(1).__getattr__("extension").__getattr__("Extension");
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(382);
         PyObject var10000 = var1.getglobal("test_support").__getattr__("import_module");
         PyObject[] var8 = new PyObject[]{PyString.fromInterned("setuptools_build_ext"), var1.getglobal("True")};
         String[] var5 = new String[]{"deprecated"};
         var10000.__call__(var2, var8, var5);
         var4 = null;
         var1.setline(385);
         String[] var9 = new String[]{"build_ext"};
         var8 = imp.importFrom("setuptools_build_ext", var9, var1, -1);
         PyObject var7 = var8[0];
         var1.setlocal(3, var7);
         var5 = null;
         var1.setline(386);
         var9 = new String[]{"Extension"};
         var8 = imp.importFrom("setuptools_extension", var9, var1, -1);
         var7 = var8[0];
         var1.setlocal(4, var7);
         var5 = null;
         var1.setline(388);
         var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("lxml.etree.c"));
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(389);
         var4 = var1.getlocal(4).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lxml.etree"), (PyObject)(new PyList(new PyObject[]{var1.getlocal(5)})));
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(390);
         var4 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("lxml"), PyString.fromInterned("ext_modules"), new PyList(new PyObject[]{var1.getlocal(6)})})));
         var1.setlocal(7, var4);
         var4 = null;
         var1.setline(391);
         var4 = var1.getlocal(3).__call__(var2, var1.getlocal(7));
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(392);
         var1.getlocal(8).__getattr__("ensure_finalized").__call__(var2);
         var1.setline(393);
         PyInteger var10 = Py.newInteger(1);
         var1.getlocal(8).__setattr__((String)"inplace", var10);
         var4 = null;
         var1.setline(394);
         PyDictionary var11 = new PyDictionary(new PyObject[]{PyString.fromInterned(""), PyString.fromInterned("src")});
         var1.getlocal(8).__getattr__("distribution").__setattr__((String)"package_dir", var11);
         var4 = null;
         var1.setline(395);
         PyList var12 = new PyList(new PyObject[]{PyString.fromInterned("lxml"), PyString.fromInterned("lxml.html")});
         var1.getlocal(8).__getattr__("distribution").__setattr__((String)"packages", var12);
         var4 = null;
         var1.setline(396);
         var4 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
         var1.setlocal(9, var4);
         var4 = null;
         var1.setline(397);
         var4 = var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SO"));
         var1.setlocal(10, var4);
         var4 = null;
         var1.setline(398);
         var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(9), PyString.fromInterned("src"), PyString.fromInterned("lxml"), PyString.fromInterned("etree")._add(var1.getlocal(10)));
         var1.setlocal(11, var4);
         var4 = null;
         var1.setline(399);
         var4 = var1.getlocal(8).__getattr__("get_ext_fullpath").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("lxml.etree"));
         var1.setlocal(12, var4);
         var4 = null;
         var1.setline(400);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(11), var1.getlocal(12));
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(403);
         var4 = var1.getlocal(2);
         var1.getlocal(1).__getattr__("extension").__setattr__("Extension", var4);
         var4 = null;
         var1.setline(404);
         var4 = var1.getlocal(2);
         var1.getlocal(1).__getattr__("core").__setattr__("Extension", var4);
         var4 = null;
         var1.setline(405);
         var4 = var1.getlocal(2);
         var1.getlocal(1).__getattr__("command").__getattr__("build_ext").__setattr__("Extension", var4);
         var4 = null;
         throw (Throwable)var6;
      }

      var1.setline(403);
      var4 = var1.getlocal(2);
      var1.getlocal(1).__getattr__("extension").__setattr__("Extension", var4);
      var4 = null;
      var1.setline(404);
      var4 = var1.getlocal(2);
      var1.getlocal(1).__getattr__("core").__setattr__("Extension", var4);
      var4 = null;
      var1.setline(405);
      var4 = var1.getlocal(2);
      var1.getlocal(1).__getattr__("command").__getattr__("build_ext").__setattr__("Extension", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_build_ext_path_with_os_sep$15(PyFrame var1, ThreadState var2) {
      var1.setline(408);
      PyObject var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("UpdateManager")})));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(409);
      var3 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(410);
      var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(411);
      var3 = var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SO"));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(412);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UpdateManager"), (PyObject)PyString.fromInterned("fdsend"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(413);
      var3 = var1.getlocal(2).__getattr__("get_ext_fullpath").__call__(var2, var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(414);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(2).__getattr__("build_lib"), (PyObject)PyString.fromInterned("UpdateManager"), (PyObject)PyString.fromInterned("fdsend")._add(var1.getlocal(3)));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(415);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5), var1.getlocal(6));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_build_ext_path_cross_platform$16(PyFrame var1, ThreadState var2) {
      var1.setline(418);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._ne(PyString.fromInterned("win32"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(419);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(420);
         var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("UpdateManager")})));
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(421);
         var3 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(422);
         var1.getlocal(2).__getattr__("ensure_finalized").__call__(var2);
         var1.setline(423);
         var3 = var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SO"));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(425);
         PyString var4 = PyString.fromInterned("UpdateManager/fdsend");
         var1.setlocal(4, var4);
         var3 = null;
         var1.setline(426);
         var3 = var1.getlocal(2).__getattr__("get_ext_fullpath").__call__(var2, var1.getlocal(4));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(427);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(2).__getattr__("build_lib"), (PyObject)PyString.fromInterned("UpdateManager"), (PyObject)PyString.fromInterned("fdsend")._add(var1.getlocal(3)));
         var1.setlocal(6, var3);
         var3 = null;
         var1.setline(428);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5), var1.getlocal(6));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject test_deployment_target_default$17(PyFrame var1, ThreadState var2) {
      var1.setline(435);
      var1.getlocal(0).__getattr__("_try_compile_deployment_target").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("=="), (PyObject)var1.getglobal("None"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_deployment_target_too_low$18(PyFrame var1, ThreadState var2) {
      var1.setline(441);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsPlatformError"), var1.getlocal(0).__getattr__("_try_compile_deployment_target"), PyString.fromInterned(">"), PyString.fromInterned("10.1"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_deployment_target_higher_ok$19(PyFrame var1, ThreadState var2) {
      var1.setline(449);
      PyObject var3 = var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MACOSX_DEPLOYMENT_TARGET"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(450);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(452);
         PyList var10000 = new PyList();
         var3 = var10000.__getattr__("append");
         var1.setlocal(2, var3);
         var3 = null;
         var1.setline(452);
         var3 = var1.getlocal(1).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__iter__();

         while(true) {
            var1.setline(452);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(452);
               var1.dellocal(2);
               PyList var6 = var10000;
               var1.setlocal(1, var6);
               var3 = null;
               var1.setline(453);
               PyObject var10 = var1.getlocal(1);
               PyInteger var8 = Py.newInteger(-1);
               var4 = var10;
               PyObject var5 = var4.__getitem__(var8);
               var5 = var5._iadd(Py.newInteger(1));
               var4.__setitem__((PyObject)var8, var5);
               var1.setline(454);
               var10 = PyString.fromInterned(".").__getattr__("join");
               var1.setline(454);
               PyObject[] var9 = Py.EmptyObjects;
               PyFunction var7 = new PyFunction(var1.f_globals, var9, f$20, (PyObject)null);
               PyObject var10002 = var7.__call__(var2, var1.getlocal(1).__iter__());
               Arrays.fill(var9, (Object)null);
               var3 = var10.__call__(var2, var10002);
               var1.setlocal(1, var3);
               var3 = null;
               var1.setline(455);
               var1.getlocal(0).__getattr__("_try_compile_deployment_target").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<"), (PyObject)var1.getlocal(1));
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(452);
            var1.getlocal(2).__call__(var2, var1.getglobal("int").__call__(var2, var1.getlocal(3)));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$20(PyFrame var1, ThreadState var2) {
      PyObject var3;
      PyObject var4;
      Object[] var5;
      PyObject var6;
      switch (var1.f_lasti) {
         case 0:
         default:
            var1.setline(454);
            var3 = var1.getlocal(0).__iter__();
            break;
         case 1:
            var5 = var1.f_savedlocals;
            var3 = (PyObject)var5[3];
            var4 = (PyObject)var5[4];
            Object var10000 = var1.getGeneratorInput();
            if (var10000 instanceof PyException) {
               throw (Throwable)var10000;
            }

            var6 = (PyObject)var10000;
      }

      var1.setline(454);
      var4 = var3.__iternext__();
      if (var4 == null) {
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setlocal(1, var4);
         var1.setline(454);
         var1.setline(454);
         var6 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
         var1.f_lasti = 1;
         var5 = new Object[]{null, null, null, var3, var4};
         var1.f_savedlocals = var5;
         return var6;
      }
   }

   public PyObject _try_compile_deployment_target$21(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(458);
      PyObject var3 = var1.getglobal("os").__getattr__("environ");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(459);
      var3 = var1.getlocal(3).__getattr__("copy").__call__(var2);
      var1.getglobal("os").__setattr__("environ", var3);
      var3 = null;
      var1.setline(460);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getglobal("setattr"), var1.getglobal("os"), PyString.fromInterned("environ"), var1.getlocal(3));
      var1.setline(462);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(463);
         if (var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MACOSX_DEPLOYMENT_TARGET")).__nonzero__()) {
            var1.setline(464);
            var1.getglobal("os").__getattr__("environ").__delitem__((PyObject)PyString.fromInterned("MACOSX_DEPLOYMENT_TARGET"));
         }
      } else {
         var1.setline(466);
         var3 = var1.getlocal(2);
         var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("MACOSX_DEPLOYMENT_TARGET"), var3);
         var3 = null;
      }

      var1.setline(468);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("tmp_dir"), (PyObject)PyString.fromInterned("deptargetmodule.c"));
      var1.setlocal(4, var3);
      var3 = null;
      ContextManager var8;
      PyObject var4 = (var8 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("w")))).__enter__(var2);

      label38: {
         try {
            var1.setlocal(5, var4);
            var1.setline(471);
            var1.getlocal(5).__getattr__("write").__call__(var2, var1.getglobal("textwrap").__getattr__("dedent").__call__(var2, PyString.fromInterned("                #include <AvailabilityMacros.h>\n\n                int dummy;\n\n                #if TARGET %s MAC_OS_X_VERSION_MIN_REQUIRED\n                #else\n                #error \"Unexpected target\"\n                #endif\n\n            ")._mod(var1.getlocal(1))));
         } catch (Throwable var6) {
            if (var8.__exit__(var2, Py.setException(var6, var1))) {
               break label38;
            }

            throw (Throwable)Py.makeException();
         }

         var8.__exit__(var2, (PyException)null);
      }

      var1.setline(484);
      var3 = var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MACOSX_DEPLOYMENT_TARGET"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(485);
      var3 = var1.getglobal("tuple").__call__(var2, var1.getglobal("map").__call__(var2, var1.getglobal("int"), var1.getlocal(2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."))));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(486);
      var3 = PyString.fromInterned("%02d%01d0")._mod(var1.getlocal(2));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(487);
      var10000 = var1.getglobal("Extension");
      PyObject[] var9 = new PyObject[]{PyString.fromInterned("deptarget"), new PyList(new PyObject[]{var1.getlocal(4)}), new PyList(new PyObject[]{PyString.fromInterned("-DTARGET=%s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2)}))})};
      String[] var7 = new String[]{"extra_compile_args"};
      var10000 = var10000.__call__(var2, var9, var7);
      var3 = null;
      var3 = var10000;
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(492);
      var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("deptarget"), PyString.fromInterned("ext_modules"), new PyList(new PyObject[]{var1.getlocal(6)})})));
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(496);
      var3 = var1.getlocal(0).__getattr__("tmp_dir");
      var1.getlocal(7).__setattr__("package_dir", var3);
      var3 = null;
      var1.setline(497);
      var3 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(7));
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(498);
      var3 = var1.getlocal(0).__getattr__("tmp_dir");
      var1.getlocal(8).__setattr__("build_lib", var3);
      var3 = null;
      var1.setline(499);
      var3 = var1.getlocal(0).__getattr__("tmp_dir");
      var1.getlocal(8).__setattr__("build_temp", var3);
      var3 = null;

      try {
         var1.setline(502);
         var1.getlocal(8).__getattr__("ensure_finalized").__call__(var2);
         var1.setline(503);
         var1.getlocal(8).__getattr__("run").__call__(var2);
      } catch (Throwable var5) {
         PyException var10 = Py.setException(var5, var1);
         if (!var10.match(var1.getglobal("CompileError"))) {
            throw var10;
         }

         var1.setline(505);
         var1.getlocal(0).__getattr__("fail").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Wrong deployment target during compilation"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$22(PyFrame var1, ThreadState var2) {
      var1.setline(508);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("BuildExtTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_build_ext$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BuildExtTestCase$1 = Py.newCode(0, var2, var1, "BuildExtTestCase", 21, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "site", "build_ext"};
      setUp$2 = Py.newCode(1, var2, var1, "setUp", 24, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$3 = Py.newCode(1, var2, var1, "tearDown", 37, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "xx_c", "xx_ext", "dist", "cmd", "old_stdout", "xx", "attr", "doc"};
      test_build_ext$4 = Py.newCode(1, var2, var1, "test_build_ext", 44, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "old", "_config_vars", "old_var"};
      test_solaris_enable_shared$5 = Py.newCode(1, var2, var1, "test_solaris_enable_shared", 86, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "site", "dist", "cmd", "options", "_[117_19]", "name", "short", "label", "lib", "incl"};
      test_user_site$6 = Py.newCode(1, var2, var1, "test_user_site", 107, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "modules", "dist", "cmd", "py_include", "plat_py_include"};
      test_finalize_options$7 = Py.newCode(1, var2, var1, "test_finalize_options", 137, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "exts", "ext"};
      test_check_extensions_list$8 = Py.newCode(1, var2, var1, "test_check_extensions_list", 200, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "modules", "dist", "cmd"};
      test_get_source_files$9 = Py.newCode(1, var2, var1, "test_get_source_files", 247, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd"};
      test_compiler_option$10 = Py.newCode(1, var2, var1, "test_compiler_option", 254, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmp_dir", "c_file", "ext", "dist", "cmd", "other_tmp_dir", "old_wd", "so_file", "so_dir", "build_py", "path", "lastdir"};
      test_get_outputs$11 = Py.newCode(1, var2, var1, "test_get_outputs", 265, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "ext", "dist", "cmd", "curdir", "wanted", "path", "build_py"};
      test_ext_fullpath$12 = Py.newCode(1, var2, var1, "test_ext_fullpath", 328, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "etree_c", "etree_ext", "dist", "cmd", "curdir", "ext", "wanted", "path"};
      test_build_ext_inplace$13 = Py.newCode(1, var2, var1, "test_build_ext_inplace", 362, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "distutils", "saved_ext", "setuptools_build_ext", "Extension", "etree_c", "etree_ext", "dist", "cmd", "curdir", "ext", "wanted", "path"};
      test_setuptools_compat$14 = Py.newCode(1, var2, var1, "test_setuptools_compat", 377, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "ext", "ext_name", "ext_path", "wanted"};
      test_build_ext_path_with_os_sep$15 = Py.newCode(1, var2, var1, "test_build_ext_path_with_os_sep", 407, false, false, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd", "ext", "ext_name", "ext_path", "wanted"};
      test_build_ext_path_cross_platform$16 = Py.newCode(1, var2, var1, "test_build_ext_path_cross_platform", 417, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_deployment_target_default$17 = Py.newCode(1, var2, var1, "test_deployment_target_default", 430, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_deployment_target_too_low$18 = Py.newCode(1, var2, var1, "test_deployment_target_too_low", 437, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "deptarget", "_[452_25]", "x", "_(454_33)"};
      test_deployment_target_higher_ok$19 = Py.newCode(1, var2, var1, "test_deployment_target_higher_ok", 444, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"_(x)", "i"};
      f$20 = Py.newCode(1, var2, var1, "<genexpr>", 454, false, false, self, 20, (String[])null, (String[])null, 0, 4129);
      var2 = new String[]{"self", "operator", "target", "orig_environ", "deptarget_c", "fp", "deptarget_ext", "dist", "cmd"};
      _try_compile_deployment_target$21 = Py.newCode(3, var2, var1, "_try_compile_deployment_target", 457, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$22 = Py.newCode(0, var2, var1, "test_suite", 507, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_build_ext$py("distutils/tests/test_build_ext$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_build_ext$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BuildExtTestCase$1(var2, var3);
         case 2:
            return this.setUp$2(var2, var3);
         case 3:
            return this.tearDown$3(var2, var3);
         case 4:
            return this.test_build_ext$4(var2, var3);
         case 5:
            return this.test_solaris_enable_shared$5(var2, var3);
         case 6:
            return this.test_user_site$6(var2, var3);
         case 7:
            return this.test_finalize_options$7(var2, var3);
         case 8:
            return this.test_check_extensions_list$8(var2, var3);
         case 9:
            return this.test_get_source_files$9(var2, var3);
         case 10:
            return this.test_compiler_option$10(var2, var3);
         case 11:
            return this.test_get_outputs$11(var2, var3);
         case 12:
            return this.test_ext_fullpath$12(var2, var3);
         case 13:
            return this.test_build_ext_inplace$13(var2, var3);
         case 14:
            return this.test_setuptools_compat$14(var2, var3);
         case 15:
            return this.test_build_ext_path_with_os_sep$15(var2, var3);
         case 16:
            return this.test_build_ext_path_cross_platform$16(var2, var3);
         case 17:
            return this.test_deployment_target_default$17(var2, var3);
         case 18:
            return this.test_deployment_target_too_low$18(var2, var3);
         case 19:
            return this.test_deployment_target_higher_ok$19(var2, var3);
         case 20:
            return this.f$20(var2, var3);
         case 21:
            return this._try_compile_deployment_target$21(var2, var3);
         case 22:
            return this.test_suite$22(var2, var3);
         default:
            return null;
      }
   }
}
