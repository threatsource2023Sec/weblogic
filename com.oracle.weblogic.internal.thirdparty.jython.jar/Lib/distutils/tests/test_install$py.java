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
@Filename("distutils/tests/test_install.py")
public class test_install$py extends PyFunctionTable implements PyRunnable {
   static test_install$py self;
   static final PyCode f$0;
   static final PyCode _make_ext_name$1;
   static final PyCode InstallTestCase$2;
   static final PyCode test_home_installation_scheme$3;
   static final PyCode check_path$4;
   static final PyCode test_user_site$5;
   static final PyCode _expanduser$6;
   static final PyCode cleanup$7;
   static final PyCode test_handle_extra_path$8;
   static final PyCode test_finalize_options$9;
   static final PyCode test_record$10;
   static final PyCode test_record_extensions$11;
   static final PyCode test_debug_mode$12;
   static final PyCode test_suite$13;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.install."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.install.");
      var1.setline(3);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("site", var1, -1);
      var1.setlocal("site", var3);
      var3 = null;
      var1.setline(8);
      String[] var5 = new String[]{"captured_stdout", "run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("captured_stdout", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"sysconfig"};
      var6 = imp.importFrom("distutils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("sysconfig", var4);
      var4 = null;
      var1.setline(11);
      var5 = new String[]{"install"};
      var6 = imp.importFrom("distutils.command.install", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("install", var4);
      var4 = null;
      var1.setline(12);
      var5 = new String[]{"install"};
      var6 = imp.importFrom("distutils.command", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("install_module", var4);
      var4 = null;
      var1.setline(13);
      var5 = new String[]{"build_ext"};
      var6 = imp.importFrom("distutils.command.build_ext", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("build_ext", var4);
      var4 = null;
      var1.setline(14);
      var5 = new String[]{"INSTALL_SCHEMES"};
      var6 = imp.importFrom("distutils.command.install", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("INSTALL_SCHEMES", var4);
      var4 = null;
      var1.setline(15);
      var5 = new String[]{"Distribution"};
      var6 = imp.importFrom("distutils.core", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(16);
      var5 = new String[]{"DistutilsOptionError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsOptionError", var4);
      var4 = null;
      var1.setline(17);
      var5 = new String[]{"Extension"};
      var6 = imp.importFrom("distutils.extension", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Extension", var4);
      var4 = null;
      var1.setline(19);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(22);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, _make_ext_name$1, (PyObject)null);
      var1.setlocal("_make_ext_name", var7);
      var3 = null;
      var1.setline(28);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("InstallTestCase", var6, InstallTestCase$2);
      var1.setlocal("InstallTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(243);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, test_suite$13, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(246);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(247);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _make_ext_name$1(PyFrame var1, ThreadState var2) {
      var1.setline(23);
      PyObject var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var10000 = var1.getglobal("sys").__getattr__("executable").__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_d.exe"));
      }

      if (var10000.__nonzero__()) {
         var1.setline(24);
         var3 = var1.getlocal(0);
         var3 = var3._iadd(PyString.fromInterned("_d"));
         var1.setlocal(0, var3);
      }

      var1.setline(25);
      var3 = var1.getlocal(0)._add(var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SO")));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject InstallTestCase$2(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(32);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_home_installation_scheme$3, (PyObject)null);
      var1.setlocal("test_home_installation_scheme", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_user_site$5, (PyObject)null);
      var1.setlocal("test_user_site", var4);
      var3 = null;
      var1.setline(126);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_handle_extra_path$8, (PyObject)null);
      var1.setlocal("test_handle_extra_path", var4);
      var3 = null;
      var1.setline(154);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_finalize_options$9, (PyObject)null);
      var1.setlocal("test_finalize_options", var4);
      var3 = null;
      var1.setline(175);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_record$10, (PyObject)null);
      var1.setlocal("test_record", var4);
      var3 = null;
      var1.setline(201);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_record_extensions$11, (PyObject)null);
      var1.setlocal("test_record_extensions", var4);
      var3 = null;
      var1.setline(231);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_debug_mode$12, (PyObject)null);
      var1.setlocal("test_debug_mode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_home_installation_scheme$3(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(36);
      PyObject var3 = var1.getderef(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(37);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("installation"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(39);
      var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("foopkg")})));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(41);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("setup.py"));
      var1.getlocal(3).__setattr__("script_name", var3);
      var3 = null;
      var1.setline(42);
      PyObject var10000 = var1.getglobal("support").__getattr__("DummyCommand");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("lib"))};
      String[] var4 = new String[]{"build_base", "build_lib"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(3).__getattr__("command_obj").__setitem__((PyObject)PyString.fromInterned("build"), var3);
      var3 = null;
      var1.setline(47);
      var3 = var1.getglobal("install").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(48);
      var3 = var1.getlocal(2);
      var1.getlocal(4).__setattr__("home", var3);
      var3 = null;
      var1.setline(49);
      var1.getlocal(4).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(51);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("install_base"), var1.getlocal(2));
      var1.setline(52);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4).__getattr__("install_platbase"), var1.getlocal(2));
      var1.setline(54);
      var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = check_path$4;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(5, var6);
      var3 = null;
      var1.setline(59);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(2), (PyObject)PyString.fromInterned("lib"), (PyObject)PyString.fromInterned("python"));
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(60);
      var1.getlocal(5).__call__(var2, var1.getlocal(4).__getattr__("install_lib"), var1.getlocal(6));
      var1.setline(61);
      var1.getlocal(5).__call__(var2, var1.getlocal(4).__getattr__("install_platlib"), var1.getlocal(6));
      var1.setline(62);
      var1.getlocal(5).__call__(var2, var1.getlocal(4).__getattr__("install_purelib"), var1.getlocal(6));
      var1.setline(63);
      var1.getlocal(5).__call__(var2, var1.getlocal(4).__getattr__("install_headers"), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), PyString.fromInterned("include"), PyString.fromInterned("python"), PyString.fromInterned("foopkg")));
      var1.setline(65);
      var1.getlocal(5).__call__(var2, var1.getlocal(4).__getattr__("install_scripts"), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("bin")));
      var1.setline(66);
      var1.getlocal(5).__call__(var2, var1.getlocal(4).__getattr__("install_data"), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject check_path$4(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyObject var3 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(0));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(56);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("normpath").__call__(var2, var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(57);
      var1.getderef(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_user_site$5(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(70);
      PyObject var3 = var1.getglobal("sys").__getattr__("version");
      PyObject var10000 = var3._lt(PyString.fromInterned("2.6"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(71);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(74);
         var3 = var1.getglobal("site").__getattr__("USER_BASE");
         var1.getderef(0).__setattr__("old_user_base", var3);
         var3 = null;
         var1.setline(75);
         var3 = var1.getglobal("site").__getattr__("USER_SITE");
         var1.getderef(0).__setattr__("old_user_site", var3);
         var3 = null;
         var1.setline(76);
         var3 = var1.getderef(0).__getattr__("mkdtemp").__call__(var2);
         var1.getderef(0).__setattr__("tmpdir", var3);
         var3 = null;
         var1.setline(77);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("tmpdir"), (PyObject)PyString.fromInterned("B"));
         var1.getderef(0).__setattr__("user_base", var3);
         var3 = null;
         var1.setline(78);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getderef(0).__getattr__("tmpdir"), (PyObject)PyString.fromInterned("S"));
         var1.getderef(0).__setattr__("user_site", var3);
         var3 = null;
         var1.setline(79);
         var3 = var1.getderef(0).__getattr__("user_base");
         var1.getglobal("site").__setattr__("USER_BASE", var3);
         var3 = null;
         var1.setline(80);
         var3 = var1.getderef(0).__getattr__("user_site");
         var1.getglobal("site").__setattr__("USER_SITE", var3);
         var3 = null;
         var1.setline(81);
         var3 = var1.getderef(0).__getattr__("user_base");
         var1.getglobal("install_module").__setattr__("USER_BASE", var3);
         var3 = null;
         var1.setline(82);
         var3 = var1.getderef(0).__getattr__("user_site");
         var1.getglobal("install_module").__setattr__("USER_SITE", var3);
         var3 = null;
         var1.setline(84);
         PyObject[] var7 = Py.EmptyObjects;
         PyObject var10002 = var1.f_globals;
         PyObject[] var10003 = var7;
         PyCode var10004 = _expanduser$6;
         var7 = new PyObject[]{var1.getclosure(0)};
         PyFunction var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var7);
         var1.setlocal(1, var8);
         var3 = null;
         var1.setline(86);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("expanduser");
         var1.getderef(0).__setattr__("old_expand", var3);
         var3 = null;
         var1.setline(87);
         var3 = var1.getlocal(1);
         var1.getglobal("os").__getattr__("path").__setattr__("expanduser", var3);
         var3 = null;
         var1.setline(89);
         var7 = Py.EmptyObjects;
         var10002 = var1.f_globals;
         var10003 = var7;
         var10004 = cleanup$7;
         var7 = new PyObject[]{var1.getclosure(0)};
         var8 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var7);
         var1.setlocal(2, var8);
         var3 = null;
         var1.setline(96);
         var1.getderef(0).__getattr__("addCleanup").__call__(var2, var1.getlocal(2));
         var1.setline(98);
         var3 = (new PyTuple(new PyObject[]{PyString.fromInterned("nt_user"), PyString.fromInterned("unix_user"), PyString.fromInterned("os2_home")})).__iter__();

         while(true) {
            var1.setline(98);
            PyObject var4 = var3.__iternext__();
            if (var4 == null) {
               var1.setline(101);
               var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("xx")})));
               var1.setlocal(4, var3);
               var3 = null;
               var1.setline(102);
               var3 = var1.getglobal("install").__call__(var2, var1.getlocal(4));
               var1.setlocal(5, var3);
               var3 = null;
               var1.setline(105);
               PyList var9 = new PyList();
               var3 = var9.__getattr__("append");
               var1.setlocal(7, var3);
               var3 = null;
               var1.setline(105);
               var3 = var1.getlocal(5).__getattr__("user_options").__iter__();

               while(true) {
                  var1.setline(105);
                  var4 = var3.__iternext__();
                  if (var4 == null) {
                     var1.setline(105);
                     var1.dellocal(7);
                     PyList var10 = var9;
                     var1.setlocal(6, var10);
                     var3 = null;
                     var1.setline(107);
                     var1.getderef(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("user"), (PyObject)var1.getlocal(6));
                     var1.setline(110);
                     PyInteger var11 = Py.newInteger(1);
                     var1.getlocal(5).__setattr__((String)"user", var11);
                     var3 = null;
                     var1.setline(113);
                     var1.getderef(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getderef(0).__getattr__("user_base")));
                     var1.setline(114);
                     var1.getderef(0).__getattr__("assertFalse").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getderef(0).__getattr__("user_site")));
                     var1.setline(117);
                     var1.getlocal(5).__getattr__("ensure_finalized").__call__(var2);
                     var1.setline(120);
                     var1.getderef(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getderef(0).__getattr__("user_base")));
                     var1.setline(121);
                     var1.getderef(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getderef(0).__getattr__("user_site")));
                     var1.setline(123);
                     var1.getderef(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("userbase"), (PyObject)var1.getlocal(5).__getattr__("config_vars"));
                     var1.setline(124);
                     var1.getderef(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("usersite"), (PyObject)var1.getlocal(5).__getattr__("config_vars"));
                     var1.f_lasti = -1;
                     return Py.None;
                  }

                  PyObject[] var5 = Py.unpackSequence(var4, 3);
                  PyObject var6 = var5[0];
                  var1.setlocal(8, var6);
                  var6 = null;
                  var6 = var5[1];
                  var1.setlocal(9, var6);
                  var6 = null;
                  var6 = var5[2];
                  var1.setlocal(10, var6);
                  var6 = null;
                  var1.setline(105);
                  var1.getlocal(7).__call__(var2, var1.getlocal(8));
               }
            }

            var1.setlocal(3, var4);
            var1.setline(99);
            var1.getderef(0).__getattr__("assertIn").__call__(var2, var1.getlocal(3), var1.getglobal("INSTALL_SCHEMES"));
         }
      }
   }

   public PyObject _expanduser$6(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      PyObject var3 = var1.getderef(0).__getattr__("tmpdir");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject cleanup$7(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      PyObject var3 = var1.getderef(0).__getattr__("old_user_base");
      var1.getglobal("site").__setattr__("USER_BASE", var3);
      var3 = null;
      var1.setline(91);
      var3 = var1.getderef(0).__getattr__("old_user_site");
      var1.getglobal("site").__setattr__("USER_SITE", var3);
      var3 = null;
      var1.setline(92);
      var3 = var1.getderef(0).__getattr__("old_user_base");
      var1.getglobal("install_module").__setattr__("USER_BASE", var3);
      var3 = null;
      var1.setline(93);
      var3 = var1.getderef(0).__getattr__("old_user_site");
      var1.getglobal("install_module").__setattr__("USER_SITE", var3);
      var3 = null;
      var1.setline(94);
      var3 = var1.getderef(0).__getattr__("old_expand");
      var1.getglobal("os").__getattr__("path").__setattr__("expanduser", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_handle_extra_path$8(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      PyObject var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("xx"), PyString.fromInterned("extra_path"), PyString.fromInterned("path,dirs")})));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(128);
      var3 = var1.getglobal("install").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(131);
      var1.getlocal(2).__getattr__("handle_extra_path").__call__(var2);
      var1.setline(132);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("extra_path"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("path"), PyString.fromInterned("dirs")})));
      var1.setline(133);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("extra_dirs"), (PyObject)PyString.fromInterned("dirs"));
      var1.setline(134);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("path_file"), (PyObject)PyString.fromInterned("path"));
      var1.setline(137);
      PyList var4 = new PyList(new PyObject[]{PyString.fromInterned("path")});
      var1.getlocal(2).__setattr__((String)"extra_path", var4);
      var3 = null;
      var1.setline(138);
      var1.getlocal(2).__getattr__("handle_extra_path").__call__(var2);
      var1.setline(139);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("extra_path"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("path")})));
      var1.setline(140);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("extra_dirs"), (PyObject)PyString.fromInterned("path"));
      var1.setline(141);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("path_file"), (PyObject)PyString.fromInterned("path"));
      var1.setline(144);
      var3 = var1.getglobal("None");
      var1.getlocal(1).__setattr__("extra_path", var3);
      var1.getlocal(2).__setattr__("extra_path", var3);
      var1.setline(145);
      var1.getlocal(2).__getattr__("handle_extra_path").__call__(var2);
      var1.setline(146);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("extra_path"), var1.getglobal("None"));
      var1.setline(147);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("extra_dirs"), (PyObject)PyString.fromInterned(""));
      var1.setline(148);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(2).__getattr__("path_file"), var1.getglobal("None"));
      var1.setline(151);
      PyString var5 = PyString.fromInterned("path,dirs,again");
      var1.getlocal(2).__setattr__((String)"extra_path", var5);
      var3 = null;
      var1.setline(152);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsOptionError"), var1.getlocal(2).__getattr__("handle_extra_path"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_finalize_options$9(PyFrame var1, ThreadState var2) {
      var1.setline(155);
      PyObject var3 = var1.getglobal("Distribution").__call__((ThreadState)var2, (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("xx")})));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(156);
      var3 = var1.getglobal("install").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(160);
      PyString var4 = PyString.fromInterned("prefix");
      var1.getlocal(2).__setattr__((String)"prefix", var4);
      var3 = null;
      var1.setline(161);
      var4 = PyString.fromInterned("base");
      var1.getlocal(2).__setattr__((String)"install_base", var4);
      var3 = null;
      var1.setline(162);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsOptionError"), var1.getlocal(2).__getattr__("finalize_options"));
      var1.setline(165);
      var3 = var1.getglobal("None");
      var1.getlocal(2).__setattr__("install_base", var3);
      var3 = null;
      var1.setline(166);
      var4 = PyString.fromInterned("home");
      var1.getlocal(2).__setattr__((String)"home", var4);
      var3 = null;
      var1.setline(167);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsOptionError"), var1.getlocal(2).__getattr__("finalize_options"));
      var1.setline(171);
      var3 = var1.getglobal("None");
      var1.getlocal(2).__setattr__("prefix", var3);
      var3 = null;
      var1.setline(172);
      var4 = PyString.fromInterned("user");
      var1.getlocal(2).__setattr__((String)"user", var4);
      var3 = null;
      var1.setline(173);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsOptionError"), var1.getlocal(2).__getattr__("finalize_options"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_record$10(PyFrame var1, ThreadState var2) {
      var1.setline(176);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(177);
      PyObject var10000 = var1.getlocal(0).__getattr__("create_dist");
      PyObject[] var7 = new PyObject[]{new PyList(new PyObject[]{PyString.fromInterned("hello")}), new PyList(new PyObject[]{PyString.fromInterned("sayhi")})};
      String[] var4 = new String[]{"py_modules", "scripts"};
      var10000 = var10000.__call__(var2, var7, var4);
      var3 = null;
      var3 = var10000;
      PyObject[] var8 = Py.unpackSequence(var3, 2);
      PyObject var5 = var8[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(179);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(2));
      var1.setline(180);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("hello.py"), (PyObject)PyString.fromInterned("def main(): print 'o hai'"));
      var1.setline(181);
      var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("sayhi"), (PyObject)PyString.fromInterned("from hello import main; main()"));
      var1.setline(183);
      var3 = var1.getglobal("install").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(184);
      var3 = var1.getlocal(4);
      var1.getlocal(3).__getattr__("command_obj").__setitem__((PyObject)PyString.fromInterned("install"), var3);
      var3 = null;
      var1.setline(185);
      var3 = var1.getlocal(1);
      var1.getlocal(4).__setattr__("root", var3);
      var3 = null;
      var1.setline(186);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("filelist"));
      var1.getlocal(4).__setattr__("record", var3);
      var3 = null;
      var1.setline(187);
      var1.getlocal(4).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(188);
      var1.getlocal(4).__getattr__("run").__call__(var2);
      var1.setline(190);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(4).__getattr__("record"));
      var1.setlocal(5, var3);
      var3 = null;
      var3 = null;

      PyObject var9;
      try {
         var1.setline(192);
         var9 = var1.getlocal(5).__getattr__("read").__call__(var2);
         var1.setlocal(6, var9);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(194);
         var1.getlocal(5).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(194);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var1.setline(196);
      PyList var11 = new PyList();
      var3 = var11.__getattr__("append");
      var1.setlocal(8, var3);
      var3 = null;
      var1.setline(196);
      var3 = var1.getlocal(6).__getattr__("splitlines").__call__(var2).__iter__();

      while(true) {
         var1.setline(196);
         var9 = var3.__iternext__();
         if (var9 == null) {
            var1.setline(196);
            var1.dellocal(8);
            PyList var10 = var11;
            var1.setlocal(7, var10);
            var3 = null;
            var1.setline(197);
            var10 = new PyList(new PyObject[]{PyString.fromInterned("hello.py"), PyString.fromInterned("hello.pyc"), PyString.fromInterned("sayhi"), PyString.fromInterned("UNKNOWN-0.0.0-py%s.%s.egg-info")._mod(var1.getglobal("sys").__getattr__("version_info").__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null))});
            var1.setlocal(10, var10);
            var3 = null;
            var1.setline(199);
            var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(7), var1.getlocal(10));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(9, var9);
         var1.setline(196);
         var1.getlocal(8).__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(9)));
      }
   }

   public PyObject test_record_extensions$11(PyFrame var1, ThreadState var2) {
      var1.setline(202);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(203);
      PyObject var10000 = var1.getlocal(0).__getattr__("create_dist");
      PyObject[] var7 = new PyObject[]{new PyList(new PyObject[]{var1.getglobal("Extension").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xx"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("xxmodule.c")})))})};
      String[] var4 = new String[]{"ext_modules"};
      var10000 = var10000.__call__(var2, var7, var4);
      var3 = null;
      var3 = var10000;
      PyObject[] var8 = Py.unpackSequence(var3, 2);
      PyObject var5 = var8[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var8[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(205);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(2));
      var1.setline(206);
      var1.getglobal("support").__getattr__("copy_xxmodule_c").__call__(var2, var1.getlocal(2));
      var1.setline(208);
      var3 = var1.getglobal("build_ext").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(209);
      var1.getglobal("support").__getattr__("fixup_build_ext").__call__(var2, var1.getlocal(4));
      var1.setline(210);
      var1.getlocal(4).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(212);
      var3 = var1.getglobal("install").__call__(var2, var1.getlocal(3));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(213);
      var3 = var1.getlocal(5);
      var1.getlocal(3).__getattr__("command_obj").__setitem__((PyObject)PyString.fromInterned("install"), var3);
      var3 = null;
      var1.setline(214);
      var3 = var1.getlocal(4);
      var1.getlocal(3).__getattr__("command_obj").__setitem__((PyObject)PyString.fromInterned("build_ext"), var3);
      var3 = null;
      var1.setline(215);
      var3 = var1.getlocal(1);
      var1.getlocal(5).__setattr__("root", var3);
      var3 = null;
      var1.setline(216);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("filelist"));
      var1.getlocal(5).__setattr__("record", var3);
      var3 = null;
      var1.setline(217);
      var1.getlocal(5).__getattr__("ensure_finalized").__call__(var2);
      var1.setline(218);
      var1.getlocal(5).__getattr__("run").__call__(var2);
      var1.setline(220);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(5).__getattr__("record"));
      var1.setlocal(6, var3);
      var3 = null;
      var3 = null;

      PyObject var9;
      try {
         var1.setline(222);
         var9 = var1.getlocal(6).__getattr__("read").__call__(var2);
         var1.setlocal(7, var9);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(224);
         var1.getlocal(6).__getattr__("close").__call__(var2);
         throw (Throwable)var6;
      }

      var1.setline(224);
      var1.getlocal(6).__getattr__("close").__call__(var2);
      var1.setline(226);
      PyList var11 = new PyList();
      var3 = var11.__getattr__("append");
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(226);
      var3 = var1.getlocal(7).__getattr__("splitlines").__call__(var2).__iter__();

      while(true) {
         var1.setline(226);
         var9 = var3.__iternext__();
         if (var9 == null) {
            var1.setline(226);
            var1.dellocal(9);
            PyList var10 = var11;
            var1.setlocal(8, var10);
            var3 = null;
            var1.setline(227);
            var10 = new PyList(new PyObject[]{var1.getglobal("_make_ext_name").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("xx")), PyString.fromInterned("UNKNOWN-0.0.0-py%s.%s.egg-info")._mod(var1.getglobal("sys").__getattr__("version_info").__getslice__((PyObject)null, Py.newInteger(2), (PyObject)null))});
            var1.setlocal(11, var10);
            var3 = null;
            var1.setline(229);
            var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(8), var1.getlocal(11));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(10, var9);
         var1.setline(226);
         var1.getlocal(9).__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("basename").__call__(var2, var1.getlocal(10)));
      }
   }

   public PyObject test_debug_mode$12(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(233);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("logs"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(234);
      var3 = var1.getglobal("True");
      var1.getglobal("install_module").__setattr__("DEBUG", var3);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         label26: {
            ContextManager var8;
            PyObject var5 = (var8 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

            try {
               var1.setline(237);
               var1.getlocal(0).__getattr__("test_record").__call__(var2);
            } catch (Throwable var6) {
               if (var8.__exit__(var2, Py.setException(var6, var1))) {
                  break label26;
               }

               throw (Throwable)Py.makeException();
            }

            var8.__exit__(var2, (PyException)null);
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(239);
         var4 = var1.getglobal("False");
         var1.getglobal("install_module").__setattr__("DEBUG", var4);
         var4 = null;
         throw (Throwable)var7;
      }

      var1.setline(239);
      var4 = var1.getglobal("False");
      var1.getglobal("install_module").__setattr__("DEBUG", var4);
      var4 = null;
      var1.setline(240);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("logs"));
      PyObject var10002 = var3._gt(var1.getlocal(1));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$13(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("InstallTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_install$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"modname"};
      _make_ext_name$1 = Py.newCode(1, var2, var1, "_make_ext_name", 22, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      InstallTestCase$2 = Py.newCode(0, var2, var1, "InstallTestCase", 28, false, false, self, 2, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "builddir", "destination", "dist", "cmd", "check_path", "libdir"};
      String[] var10001 = var2;
      test_install$py var10007 = self;
      var2 = new String[]{"self"};
      test_home_installation_scheme$3 = Py.newCode(1, var10001, var1, "test_home_installation_scheme", 32, false, false, var10007, 3, var2, (String[])null, 0, 4097);
      var2 = new String[]{"got", "expected"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      check_path$4 = Py.newCode(2, var10001, var1, "check_path", 54, false, false, var10007, 4, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "_expanduser", "cleanup", "key", "dist", "cmd", "options", "_[105_19]", "name", "short", "lable"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      test_user_site$5 = Py.newCode(1, var10001, var1, "test_user_site", 68, false, false, var10007, 5, var2, (String[])null, 0, 4097);
      var2 = new String[]{"path"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      _expanduser$6 = Py.newCode(1, var10001, var1, "_expanduser", 84, false, false, var10007, 6, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"self"};
      cleanup$7 = Py.newCode(0, var10001, var1, "cleanup", 89, false, false, var10007, 7, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd"};
      test_handle_extra_path$8 = Py.newCode(1, var2, var1, "test_handle_extra_path", 126, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmd"};
      test_finalize_options$9 = Py.newCode(1, var2, var1, "test_finalize_options", 154, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "install_dir", "project_dir", "dist", "cmd", "f", "content", "found", "_[196_17]", "line", "expected"};
      test_record$10 = Py.newCode(1, var2, var1, "test_record", 175, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "install_dir", "project_dir", "dist", "buildextcmd", "cmd", "f", "content", "found", "_[226_17]", "line", "expected"};
      test_record_extensions$11 = Py.newCode(1, var2, var1, "test_record_extensions", 201, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "old_logs_len"};
      test_debug_mode$12 = Py.newCode(1, var2, var1, "test_debug_mode", 231, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$13 = Py.newCode(0, var2, var1, "test_suite", 243, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_install$py("distutils/tests/test_install$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_install$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this._make_ext_name$1(var2, var3);
         case 2:
            return this.InstallTestCase$2(var2, var3);
         case 3:
            return this.test_home_installation_scheme$3(var2, var3);
         case 4:
            return this.check_path$4(var2, var3);
         case 5:
            return this.test_user_site$5(var2, var3);
         case 6:
            return this._expanduser$6(var2, var3);
         case 7:
            return this.cleanup$7(var2, var3);
         case 8:
            return this.test_handle_extra_path$8(var2, var3);
         case 9:
            return this.test_finalize_options$9(var2, var3);
         case 10:
            return this.test_record$10(var2, var3);
         case 11:
            return this.test_record_extensions$11(var2, var3);
         case 12:
            return this.test_debug_mode$12(var2, var3);
         case 13:
            return this.test_suite$13(var2, var3);
         default:
            return null;
      }
   }
}
