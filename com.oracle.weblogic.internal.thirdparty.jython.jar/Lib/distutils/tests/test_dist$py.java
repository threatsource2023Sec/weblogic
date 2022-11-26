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
import org.python.core.PyUnicode;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_dist.py")
public class test_dist$py extends PyFunctionTable implements PyRunnable {
   static test_dist$py self;
   static final PyCode f$0;
   static final PyCode test_dist$1;
   static final PyCode initialize_options$2;
   static final PyCode TestDistribution$3;
   static final PyCode find_config_files$4;
   static final PyCode DistributionTestCase$5;
   static final PyCode setUp$6;
   static final PyCode tearDown$7;
   static final PyCode create_distribution$8;
   static final PyCode test_debug_mode$9;
   static final PyCode test_command_packages_unspecified$10;
   static final PyCode test_command_packages_cmdline$11;
   static final PyCode test_command_packages_configfile$12;
   static final PyCode test_write_pkg_file$13;
   static final PyCode test_empty_options$14;
   static final PyCode _warn$15;
   static final PyCode test_finalize_options$16;
   static final PyCode test_get_command_packages$17;
   static final PyCode test_announce$18;
   static final PyCode test_find_config_files_disable$19;
   static final PyCode _expander$20;
   static final PyCode MetadataTestCase$21;
   static final PyCode setUp$22;
   static final PyCode tearDown$23;
   static final PyCode test_classifier$24;
   static final PyCode test_download_url$25;
   static final PyCode test_long_description$26;
   static final PyCode test_simple_metadata$27;
   static final PyCode test_provides$28;
   static final PyCode test_provides_illegal$29;
   static final PyCode test_requires$30;
   static final PyCode test_requires_illegal$31;
   static final PyCode test_obsoletes$32;
   static final PyCode test_obsoletes_illegal$33;
   static final PyCode format_metadata$34;
   static final PyCode test_custom_pydistutils$35;
   static final PyCode test_fix_help_options$36;
   static final PyCode test_show_help$37;
   static final PyCode test_read_metadata$38;
   static final PyCode test_suite$39;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.dist."));
      var1.setline(3);
      PyString.fromInterned("Tests for distutils.dist.");
      var1.setline(4);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("StringIO", var1, -1);
      var1.setlocal("StringIO", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(9);
      var3 = imp.importOne("textwrap", var1, -1);
      var1.setlocal("textwrap", var3);
      var3 = null;
      var1.setline(11);
      String[] var5 = new String[]{"Distribution", "fix_help_options"};
      PyObject[] var6 = imp.importFrom("distutils.dist", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("fix_help_options", var4);
      var4 = null;
      var1.setline(12);
      var5 = new String[]{"Command"};
      var6 = imp.importFrom("distutils.cmd", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Command", var4);
      var4 = null;
      var1.setline(13);
      var3 = imp.importOne("distutils.dist", var1, -1);
      var1.setlocal("distutils", var3);
      var3 = null;
      var1.setline(14);
      var5 = new String[]{"TESTFN", "captured_stdout", "run_unittest"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("TESTFN", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("captured_stdout", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(15);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(18);
      var6 = new PyObject[]{var1.getname("Command")};
      var4 = Py.makeClass("test_dist", var6, test_dist$1);
      var1.setlocal("test_dist", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(29);
      var6 = new PyObject[]{var1.getname("Distribution")};
      var4 = Py.makeClass("TestDistribution", var6, TestDistribution$3);
      var1.setlocal("TestDistribution", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(41);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("support").__getattr__("EnvironGuard"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("DistributionTestCase", var6, DistributionTestCase$5);
      var1.setlocal("DistributionTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(236);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("EnvironGuard"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("MetadataTestCase", var6, MetadataTestCase$21);
      var1.setlocal("MetadataTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(438);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$39, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(444);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(445);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_dist$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Sample distutils extension command."));
      var1.setline(19);
      PyString.fromInterned("Sample distutils extension command.");
      var1.setline(21);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("sample-option="), PyString.fromInterned("S"), PyString.fromInterned("help text")})});
      var1.setlocal("user_options", var3);
      var3 = null;
      var1.setline(25);
      PyObject[] var4 = Py.EmptyObjects;
      PyFunction var5 = new PyFunction(var1.f_globals, var4, initialize_options$2, (PyObject)null);
      var1.setlocal("initialize_options", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject initialize_options$2(PyFrame var1, ThreadState var2) {
      var1.setline(26);
      PyObject var3 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("sample_option", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TestDistribution$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Distribution subclasses that avoids the default search for\n    configuration files.\n\n    The ._config_files attribute must be set before\n    .parse_config_files() is called.\n    "));
      var1.setline(35);
      PyString.fromInterned("Distribution subclasses that avoids the default search for\n    configuration files.\n\n    The ._config_files attribute must be set before\n    .parse_config_files() is called.\n    ");
      var1.setline(37);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, find_config_files$4, (PyObject)null);
      var1.setlocal("find_config_files", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject find_config_files$4(PyFrame var1, ThreadState var2) {
      var1.setline(38);
      PyObject var3 = var1.getlocal(0).__getattr__("_config_files");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject DistributionTestCase$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(46);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$6, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(51);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$7, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(56);
      var3 = new PyObject[]{new PyTuple(Py.EmptyObjects)};
      var4 = new PyFunction(var1.f_globals, var3, create_distribution$8, (PyObject)null);
      var1.setlocal("create_distribution", var4);
      var3 = null;
      var1.setline(63);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_debug_mode$9, (PyObject)null);
      var1.setlocal("test_debug_mode", var4);
      var3 = null;
      var1.setline(84);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_command_packages_unspecified$10, (PyObject)null);
      var1.setlocal("test_command_packages_unspecified", var4);
      var3 = null;
      var1.setline(89);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_command_packages_cmdline$11, (PyObject)null);
      var1.setlocal("test_command_packages_cmdline", var4);
      var3 = null;
      var1.setline(104);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_command_packages_configfile$12, (PyObject)null);
      var1.setlocal("test_command_packages_configfile", var4);
      var3 = null;
      var1.setline(130);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_write_pkg_file$13, (PyObject)null);
      var1.setlocal("test_write_pkg_file", var4);
      var3 = null;
      var1.setline(157);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_empty_options$14, (PyObject)null);
      var1.setlocal("test_empty_options", var4);
      var3 = null;
      var1.setline(176);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_finalize_options$16, (PyObject)null);
      var1.setlocal("test_finalize_options", var4);
      var3 = null;
      var1.setline(187);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_command_packages$17, (PyObject)null);
      var1.setlocal("test_get_command_packages", var4);
      var3 = null;
      var1.setline(199);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_announce$18, (PyObject)null);
      var1.setlocal("test_announce", var4);
      var3 = null;
      var1.setline(206);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_find_config_files_disable$19, (PyObject)null);
      var1.setlocal("test_find_config_files_disable", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$6(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      var1.getglobal("super").__call__(var2, var1.getglobal("DistributionTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(48);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("argv"), var1.getglobal("sys").__getattr__("argv").__getslice__((PyObject)null, (PyObject)null, (PyObject)null)});
      var1.getlocal(0).__setattr__((String)"argv", var3);
      var3 = null;
      var1.setline(49);
      var1.getglobal("sys").__getattr__("argv").__delslice__(Py.newInteger(1), (PyObject)null, (PyObject)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$7(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      PyObject var3 = var1.getlocal(0).__getattr__("argv").__getitem__(Py.newInteger(0));
      var1.getglobal("sys").__setattr__("argv", var3);
      var3 = null;
      var1.setline(53);
      var3 = var1.getlocal(0).__getattr__("argv").__getitem__(Py.newInteger(1));
      var1.getglobal("sys").__getattr__("argv").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var3);
      var3 = null;
      var1.setline(54);
      var1.getglobal("super").__call__(var2, var1.getglobal("DistributionTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject create_distribution$8(PyFrame var1, ThreadState var2) {
      var1.setline(57);
      PyObject var3 = var1.getglobal("TestDistribution").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(58);
      var3 = var1.getlocal(1);
      var1.getlocal(2).__setattr__("_config_files", var3);
      var3 = null;
      var1.setline(59);
      var1.getlocal(2).__getattr__("parse_config_files").__call__(var2);
      var1.setline(60);
      var1.getlocal(2).__getattr__("parse_command_line").__call__(var2);
      var1.setline(61);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_debug_mode$9(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[3];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("TESTFN"), (PyObject)PyString.fromInterned("w")))).__enter__(var2);

      label50: {
         try {
            var1.setlocal(1, var4);
            var1.setline(65);
            var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[global]\n"));
            var1.setline(66);
            var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("command_packages = foo.bar, splat"));
         } catch (Throwable var9) {
            if (var3.__exit__(var2, Py.setException(var9, var1))) {
               break label50;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.setline(68);
      PyList var10 = new PyList(new PyObject[]{var1.getglobal("TESTFN")});
      var1.setlocal(2, var10);
      var3 = null;
      var1.setline(69);
      var1.getglobal("sys").__getattr__("argv").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build"));
      var4 = (var3 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

      label43: {
         try {
            var1.setlocal(3, var4);
            var1.setline(72);
            var1.getlocal(0).__getattr__("create_distribution").__call__(var2, var1.getlocal(2));
         } catch (Throwable var8) {
            if (var3.__exit__(var2, Py.setException(var8, var1))) {
               break label43;
            }

            throw (Throwable)Py.makeException();
         }

         var3.__exit__(var2, (PyException)null);
      }

      var1.setline(73);
      var1.getlocal(3).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(74);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("read").__call__(var2), (PyObject)PyString.fromInterned(""));
      var1.setline(75);
      PyObject var11 = var1.getglobal("True");
      var1.getglobal("distutils").__getattr__("dist").__setattr__("DEBUG", var11);
      var3 = null;
      var3 = null;

      try {
         ContextManager var12;
         PyObject var5 = (var12 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

         label33: {
            try {
               var1.setlocal(3, var5);
               var1.setline(78);
               var1.getlocal(0).__getattr__("create_distribution").__call__(var2, var1.getlocal(2));
            } catch (Throwable var6) {
               if (var12.__exit__(var2, Py.setException(var6, var1))) {
                  break label33;
               }

               throw (Throwable)Py.makeException();
            }

            var12.__exit__(var2, (PyException)null);
         }

         var1.setline(79);
         var1.getlocal(3).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
         var1.setline(80);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("read").__call__(var2), (PyObject)PyString.fromInterned(""));
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(82);
         var4 = var1.getglobal("False");
         var1.getglobal("distutils").__getattr__("dist").__setattr__("DEBUG", var4);
         var4 = null;
         throw (Throwable)var7;
      }

      var1.setline(82);
      var4 = var1.getglobal("False");
      var1.getglobal("distutils").__getattr__("dist").__setattr__("DEBUG", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_command_packages_unspecified$10(PyFrame var1, ThreadState var2) {
      var1.setline(85);
      var1.getglobal("sys").__getattr__("argv").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build"));
      var1.setline(86);
      PyObject var3 = var1.getlocal(0).__getattr__("create_distribution").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(87);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("get_command_packages").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("distutils.command")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_command_packages_cmdline$11(PyFrame var1, ThreadState var2) {
      var1.setline(90);
      String[] var3 = new String[]{"test_dist"};
      PyObject[] var5 = imp.importFrom("distutils.tests.test_dist", var3, var1, -1);
      PyObject var4 = var5[0];
      var1.setlocal(1, var4);
      var4 = null;
      var1.setline(91);
      var1.getglobal("sys").__getattr__("argv").__getattr__("extend").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("--command-packages"), PyString.fromInterned("foo.bar,distutils.tests"), PyString.fromInterned("test_dist"), PyString.fromInterned("-Ssometext")})));
      var1.setline(96);
      PyObject var6 = var1.getlocal(0).__getattr__("create_distribution").__call__(var2);
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(98);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_command_packages").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("distutils.command"), PyString.fromInterned("foo.bar"), PyString.fromInterned("distutils.tests")})));
      var1.setline(100);
      var6 = var1.getlocal(2).__getattr__("get_command_obj").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("test_dist"));
      var1.setlocal(3, var6);
      var3 = null;
      var1.setline(101);
      var1.getlocal(0).__getattr__("assertIsInstance").__call__(var2, var1.getlocal(3), var1.getlocal(1));
      var1.setline(102);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("sample_option"), (PyObject)PyString.fromInterned("sometext"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_command_packages_configfile$12(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      var1.getglobal("sys").__getattr__("argv").__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("build"));
      var1.setline(106);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getglobal("os").__getattr__("unlink"), var1.getglobal("TESTFN"));
      var1.setline(107);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("TESTFN"), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(109);
         PyObject var4 = var1.getlocal(1);
         Py.println(var4, PyString.fromInterned("[global]"));
         var1.setline(110);
         var4 = var1.getlocal(1);
         Py.println(var4, PyString.fromInterned("command_packages = foo.bar, splat"));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(112);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(112);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setline(114);
      var3 = var1.getlocal(0).__getattr__("create_distribution").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("TESTFN")})));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(115);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_command_packages").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("distutils.command"), PyString.fromInterned("foo.bar"), PyString.fromInterned("splat")})));
      var1.setline(119);
      PyList var6 = new PyList(new PyObject[]{PyString.fromInterned("--command-packages"), PyString.fromInterned("spork"), PyString.fromInterned("build")});
      var1.getglobal("sys").__getattr__("argv").__setslice__(Py.newInteger(1), (PyObject)null, (PyObject)null, var6);
      var3 = null;
      var1.setline(120);
      var3 = var1.getlocal(0).__getattr__("create_distribution").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("TESTFN")})));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(121);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_command_packages").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("distutils.command"), PyString.fromInterned("spork")})));
      var1.setline(126);
      var6 = new PyList(new PyObject[]{PyString.fromInterned("--command-packages"), PyString.fromInterned(""), PyString.fromInterned("build")});
      var1.getglobal("sys").__getattr__("argv").__setslice__(Py.newInteger(1), (PyObject)null, (PyObject)null, var6);
      var3 = null;
      var1.setline(127);
      var3 = var1.getlocal(0).__getattr__("create_distribution").__call__((ThreadState)var2, (PyObject)(new PyList(new PyObject[]{var1.getglobal("TESTFN")})));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(128);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_command_packages").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("distutils.command")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_write_pkg_file$13(PyFrame var1, ThreadState var2) {
      var1.setline(132);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(133);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("f"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(134);
      var3 = var1.getglobal("Distribution");
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(136);
      PyObject var10000 = var1.getlocal(3);
      PyObject[] var5 = new PyObject[]{new PyDictionary(new PyObject[]{PyString.fromInterned("author"), PyUnicode.fromInterned("Mister Café"), PyString.fromInterned("name"), PyString.fromInterned("my.package"), PyString.fromInterned("maintainer"), PyUnicode.fromInterned("Café Junior"), PyString.fromInterned("description"), PyUnicode.fromInterned("Café torréfié"), PyString.fromInterned("long_description"), PyUnicode.fromInterned("Héhéhé")})};
      String[] var4 = new String[]{"attrs"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(145);
      var1.getlocal(4).__getattr__("metadata").__getattr__("write_pkg_file").__call__(var2, var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("w")));
      var1.setline(148);
      var10000 = var1.getlocal(3);
      var5 = new PyObject[]{new PyDictionary(new PyObject[]{PyString.fromInterned("author"), PyString.fromInterned("Mister Cafe"), PyString.fromInterned("name"), PyString.fromInterned("my.package"), PyString.fromInterned("maintainer"), PyString.fromInterned("Cafe Junior"), PyString.fromInterned("description"), PyString.fromInterned("Cafe torrefie"), PyString.fromInterned("long_description"), PyString.fromInterned("Hehehe")})};
      var4 = new String[]{"attrs"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(154);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("f2"));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(155);
      var1.getlocal(4).__getattr__("metadata").__getattr__("write_pkg_file").__call__(var2, var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(5), (PyObject)PyString.fromInterned("w")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_empty_options$14(PyFrame var1, ThreadState var2) {
      var1.setline(162);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(164);
      PyObject[] var5 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var5;
      PyCode var10004 = _warn$15;
      var5 = new PyObject[]{var1.getclosure(0)};
      PyFunction var6 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var5);
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(167);
      var1.getlocal(0).__getattr__("addCleanup").__call__(var2, var1.getglobal("setattr"), var1.getglobal("warnings"), PyString.fromInterned("warn"), var1.getglobal("warnings").__getattr__("warn"));
      var1.setline(168);
      PyObject var7 = var1.getlocal(1);
      var1.getglobal("warnings").__setattr__("warn", var7);
      var3 = null;
      var1.setline(169);
      PyObject var10000 = var1.getglobal("Distribution");
      var5 = new PyObject[]{new PyDictionary(new PyObject[]{PyString.fromInterned("author"), PyString.fromInterned("xxx"), PyString.fromInterned("name"), PyString.fromInterned("xxx"), PyString.fromInterned("version"), PyString.fromInterned("xxx"), PyString.fromInterned("url"), PyString.fromInterned("xxxx"), PyString.fromInterned("options"), new PyDictionary(Py.EmptyObjects)})};
      String[] var4 = new String[]{"attrs"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var7 = var10000;
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(173);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("len").__call__(var2, var1.getderef(0)), (PyObject)Py.newInteger(0));
      var1.setline(174);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("options"), (PyObject)var1.getglobal("dir").__call__(var2, var1.getlocal(2)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _warn$15(PyFrame var1, ThreadState var2) {
      var1.setline(165);
      var1.getderef(0).__getattr__("append").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_finalize_options$16(PyFrame var1, ThreadState var2) {
      var1.setline(177);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("keywords"), PyString.fromInterned("one,two"), PyString.fromInterned("platforms"), PyString.fromInterned("one,two")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(180);
      PyObject var10000 = var1.getglobal("Distribution");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1)};
      String[] var4 = new String[]{"attrs"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      PyObject var6 = var10000;
      var1.setlocal(2, var6);
      var3 = null;
      var1.setline(181);
      var1.getlocal(2).__getattr__("finalize_options").__call__(var2);
      var1.setline(184);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("metadata").__getattr__("platforms"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("one"), PyString.fromInterned("two")})));
      var1.setline(185);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("metadata").__getattr__("keywords"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("one"), PyString.fromInterned("two")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_command_packages$17(PyFrame var1, ThreadState var2) {
      var1.setline(188);
      PyObject var3 = var1.getglobal("Distribution").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(189);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("command_packages"), var1.getglobal("None"));
      var1.setline(190);
      var3 = var1.getlocal(1).__getattr__("get_command_packages").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(191);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("distutils.command")})));
      var1.setline(192);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("command_packages"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("distutils.command")})));
      var1.setline(195);
      PyString var4 = PyString.fromInterned("one,two");
      var1.getlocal(1).__setattr__((String)"command_packages", var4);
      var3 = null;
      var1.setline(196);
      var3 = var1.getlocal(1).__getattr__("get_command_packages").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(197);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("distutils.command"), PyString.fromInterned("one"), PyString.fromInterned("two")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_announce$18(PyFrame var1, ThreadState var2) {
      var1.setline(201);
      PyObject var3 = var1.getglobal("Distribution").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(202);
      PyTuple var4 = new PyTuple(new PyObject[]{PyString.fromInterned("ok")});
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(203);
      PyDictionary var5 = new PyDictionary(new PyObject[]{PyString.fromInterned("level"), PyString.fromInterned("ok2")});
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(204);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("ValueError"), var1.getlocal(1).__getattr__("announce"), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_find_config_files_disable$19(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(208);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setderef(0, var3);
      var3 = null;
      var1.setline(209);
      var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("posix"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(210);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getderef(0), (PyObject)PyString.fromInterned(".pydistutils.cfg"));
         var1.setlocal(1, var3);
         var3 = null;
      } else {
         var1.setline(212);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getderef(0), (PyObject)PyString.fromInterned("pydistutils.cfg"));
         var1.setlocal(1, var3);
         var3 = null;
      }

      ContextManager var8;
      PyObject var4 = (var8 = ContextGuard.getManager(var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w")))).__enter__(var2);

      label29: {
         try {
            var1.setlocal(2, var4);
            var1.setline(215);
            var1.getlocal(2).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("[distutils]\n"));
         } catch (Throwable var7) {
            if (var8.__exit__(var2, Py.setException(var7, var1))) {
               break label29;
            }

            throw (Throwable)Py.makeException();
         }

         var8.__exit__(var2, (PyException)null);
      }

      var1.setline(217);
      PyObject[] var9 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var9;
      PyCode var10004 = _expander$20;
      var9 = new PyObject[]{var1.getclosure(0)};
      PyFunction var11 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var9);
      var1.setlocal(3, var11);
      var3 = null;
      var1.setline(220);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("expanduser");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(221);
      var3 = var1.getlocal(3);
      var1.getglobal("os").__getattr__("path").__setattr__("expanduser", var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(223);
         var4 = var1.getglobal("distutils").__getattr__("dist").__getattr__("Distribution").__call__(var2);
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(224);
         var4 = var1.getlocal(5).__getattr__("find_config_files").__call__(var2);
         var1.setlocal(6, var4);
         var4 = null;
         var1.setline(226);
         var10000 = var1.getglobal("distutils").__getattr__("dist").__getattr__("Distribution");
         PyObject[] var10 = new PyObject[]{new PyDictionary(new PyObject[]{PyString.fromInterned("script_args"), new PyList(new PyObject[]{PyString.fromInterned("--no-user-cfg")})})};
         String[] var5 = new String[]{"attrs"};
         var10000 = var10000.__call__(var2, var10, var5);
         var4 = null;
         var4 = var10000;
         var1.setlocal(5, var4);
         var4 = null;
         var1.setline(228);
         var4 = var1.getlocal(5).__getattr__("find_config_files").__call__(var2);
         var1.setlocal(7, var4);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(230);
         var4 = var1.getlocal(4);
         var1.getglobal("os").__getattr__("path").__setattr__("expanduser", var4);
         var4 = null;
         throw (Throwable)var6;
      }

      var1.setline(230);
      var4 = var1.getlocal(4);
      var1.getglobal("os").__getattr__("path").__setattr__("expanduser", var4);
      var4 = null;
      var1.setline(233);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(6))._sub(Py.newInteger(1)), var1.getglobal("len").__call__(var2, var1.getlocal(7)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _expander$20(PyFrame var1, ThreadState var2) {
      var1.setline(218);
      PyObject var3 = var1.getderef(0);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject MetadataTestCase$21(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(239);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$22, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(243);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$23, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(248);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_classifier$24, (PyObject)null);
      var1.setlocal("test_classifier", var4);
      var3 = null;
      var1.setline(255);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_download_url$25, (PyObject)null);
      var1.setlocal("test_download_url", var4);
      var3 = null;
      var1.setline(262);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_long_description$26, (PyObject)null);
      var1.setlocal("test_long_description", var4);
      var3 = null;
      var1.setline(277);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_simple_metadata$27, (PyObject)null);
      var1.setlocal("test_simple_metadata", var4);
      var3 = null;
      var1.setline(287);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_provides$28, (PyObject)null);
      var1.setlocal("test_provides", var4);
      var3 = null;
      var1.setline(301);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_provides_illegal$29, (PyObject)null);
      var1.setlocal("test_provides_illegal", var4);
      var3 = null;
      var1.setline(307);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_requires$30, (PyObject)null);
      var1.setlocal("test_requires", var4);
      var3 = null;
      var1.setline(323);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_requires_illegal$31, (PyObject)null);
      var1.setlocal("test_requires_illegal", var4);
      var3 = null;
      var1.setline(329);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_obsoletes$32, (PyObject)null);
      var1.setlocal("test_obsoletes", var4);
      var3 = null;
      var1.setline(345);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_obsoletes_illegal$33, (PyObject)null);
      var1.setlocal("test_obsoletes_illegal", var4);
      var3 = null;
      var1.setline(351);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format_metadata$34, (PyObject)null);
      var1.setlocal("format_metadata", var4);
      var3 = null;
      var1.setline(356);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_custom_pydistutils$35, (PyObject)null);
      var1.setlocal("test_custom_pydistutils", var4);
      var3 = null;
      var1.setline(391);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_fix_help_options$36, (PyObject)null);
      var1.setlocal("test_fix_help_options", var4);
      var3 = null;
      var1.setline(397);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_show_help$37, (PyObject)null);
      var1.setlocal("test_show_help", var4);
      var3 = null;
      var1.setline(410);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_read_metadata$38, (PyObject)null);
      var1.setlocal("test_read_metadata", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$22(PyFrame var1, ThreadState var2) {
      var1.setline(240);
      var1.getglobal("super").__call__(var2, var1.getglobal("MetadataTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(241);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("argv"), var1.getglobal("sys").__getattr__("argv").__getslice__((PyObject)null, (PyObject)null, (PyObject)null)});
      var1.getlocal(0).__setattr__((String)"argv", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$23(PyFrame var1, ThreadState var2) {
      var1.setline(244);
      PyObject var3 = var1.getlocal(0).__getattr__("argv").__getitem__(Py.newInteger(0));
      var1.getglobal("sys").__setattr__("argv", var3);
      var3 = null;
      var1.setline(245);
      var3 = var1.getlocal(0).__getattr__("argv").__getitem__(Py.newInteger(1));
      var1.getglobal("sys").__getattr__("argv").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var3);
      var3 = null;
      var1.setline(246);
      var1.getglobal("super").__call__(var2, var1.getglobal("MetadataTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_classifier$24(PyFrame var1, ThreadState var2) {
      var1.setline(249);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("Boa"), PyString.fromInterned("version"), PyString.fromInterned("3.0"), PyString.fromInterned("classifiers"), new PyList(new PyObject[]{PyString.fromInterned("Programming Language :: Python :: 3")})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(251);
      PyObject var4 = var1.getglobal("Distribution").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(252);
      var4 = var1.getlocal(0).__getattr__("format_metadata").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(253);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Metadata-Version: 1.1"), (PyObject)var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_download_url$25(PyFrame var1, ThreadState var2) {
      var1.setline(256);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("Boa"), PyString.fromInterned("version"), PyString.fromInterned("3.0"), PyString.fromInterned("download_url"), PyString.fromInterned("http://example.org/boa")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(258);
      PyObject var4 = var1.getglobal("Distribution").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(259);
      var4 = var1.getlocal(0).__getattr__("format_metadata").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(260);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Metadata-Version: 1.1"), (PyObject)var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_long_description$26(PyFrame var1, ThreadState var2) {
      var1.setline(263);
      PyObject var3 = var1.getglobal("textwrap").__getattr__("dedent").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("        example::\n              We start here\n            and continue here\n          and end here."));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(268);
      PyDictionary var4 = new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("package"), PyString.fromInterned("version"), PyString.fromInterned("1.0"), PyString.fromInterned("long_description"), var1.getlocal(1)});
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(272);
      var3 = var1.getglobal("Distribution").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(273);
      var3 = var1.getlocal(0).__getattr__("format_metadata").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(274);
      var3 = var1.getlocal(4).__getattr__("replace").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")._add(Py.newInteger(8)._mul(PyString.fromInterned(" "))), (PyObject)PyString.fromInterned("\n"));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(275);
      var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getlocal(1), var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_simple_metadata$27(PyFrame var1, ThreadState var2) {
      var1.setline(278);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("package"), PyString.fromInterned("version"), PyString.fromInterned("1.0")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(280);
      PyObject var4 = var1.getglobal("Distribution").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(281);
      var4 = var1.getlocal(0).__getattr__("format_metadata").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(282);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Metadata-Version: 1.0"), (PyObject)var1.getlocal(3));
      var1.setline(283);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("provides:"), (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2));
      var1.setline(284);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("requires:"), (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2));
      var1.setline(285);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("obsoletes:"), (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_provides$28(PyFrame var1, ThreadState var2) {
      var1.setline(288);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("package"), PyString.fromInterned("version"), PyString.fromInterned("1.0"), PyString.fromInterned("provides"), new PyList(new PyObject[]{PyString.fromInterned("package"), PyString.fromInterned("package.sub")})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(291);
      PyObject var4 = var1.getglobal("Distribution").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(292);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("metadata").__getattr__("get_provides").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("package"), PyString.fromInterned("package.sub")})));
      var1.setline(294);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_provides").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("package"), PyString.fromInterned("package.sub")})));
      var1.setline(296);
      var4 = var1.getlocal(0).__getattr__("format_metadata").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(297);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Metadata-Version: 1.1"), (PyObject)var1.getlocal(3));
      var1.setline(298);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("requires:"), (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2));
      var1.setline(299);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("obsoletes:"), (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_provides_illegal$29(PyFrame var1, ThreadState var2) {
      var1.setline(302);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("ValueError"), (PyObject)var1.getglobal("Distribution"), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("package"), PyString.fromInterned("version"), PyString.fromInterned("1.0"), PyString.fromInterned("provides"), new PyList(new PyObject[]{PyString.fromInterned("my.pkg (splat)")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_requires$30(PyFrame var1, ThreadState var2) {
      var1.setline(308);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("package"), PyString.fromInterned("version"), PyString.fromInterned("1.0"), PyString.fromInterned("requires"), new PyList(new PyObject[]{PyString.fromInterned("other"), PyString.fromInterned("another (==1.0)")})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(311);
      PyObject var4 = var1.getglobal("Distribution").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(312);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("metadata").__getattr__("get_requires").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("other"), PyString.fromInterned("another (==1.0)")})));
      var1.setline(314);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_requires").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("other"), PyString.fromInterned("another (==1.0)")})));
      var1.setline(316);
      var4 = var1.getlocal(0).__getattr__("format_metadata").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(317);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Metadata-Version: 1.1"), (PyObject)var1.getlocal(3));
      var1.setline(318);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("provides:"), (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2));
      var1.setline(319);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Requires: other"), (PyObject)var1.getlocal(3));
      var1.setline(320);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Requires: another (==1.0)"), (PyObject)var1.getlocal(3));
      var1.setline(321);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("obsoletes:"), (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_requires_illegal$31(PyFrame var1, ThreadState var2) {
      var1.setline(324);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("ValueError"), (PyObject)var1.getglobal("Distribution"), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("package"), PyString.fromInterned("version"), PyString.fromInterned("1.0"), PyString.fromInterned("requires"), new PyList(new PyObject[]{PyString.fromInterned("my.pkg (splat)")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_obsoletes$32(PyFrame var1, ThreadState var2) {
      var1.setline(330);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("package"), PyString.fromInterned("version"), PyString.fromInterned("1.0"), PyString.fromInterned("obsoletes"), new PyList(new PyObject[]{PyString.fromInterned("other"), PyString.fromInterned("another (<1.0)")})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(333);
      PyObject var4 = var1.getglobal("Distribution").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(334);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("metadata").__getattr__("get_obsoletes").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("other"), PyString.fromInterned("another (<1.0)")})));
      var1.setline(336);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("get_obsoletes").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("other"), PyString.fromInterned("another (<1.0)")})));
      var1.setline(338);
      var4 = var1.getlocal(0).__getattr__("format_metadata").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(339);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Metadata-Version: 1.1"), (PyObject)var1.getlocal(3));
      var1.setline(340);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("provides:"), (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2));
      var1.setline(341);
      var1.getlocal(0).__getattr__("assertNotIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("requires:"), (PyObject)var1.getlocal(3).__getattr__("lower").__call__(var2));
      var1.setline(342);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Obsoletes: other"), (PyObject)var1.getlocal(3));
      var1.setline(343);
      var1.getlocal(0).__getattr__("assertIn").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Obsoletes: another (<1.0)"), (PyObject)var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_obsoletes_illegal$33(PyFrame var1, ThreadState var2) {
      var1.setline(346);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("ValueError"), (PyObject)var1.getglobal("Distribution"), (PyObject)(new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("package"), PyString.fromInterned("version"), PyString.fromInterned("1.0"), PyString.fromInterned("obsoletes"), new PyList(new PyObject[]{PyString.fromInterned("my.pkg (splat)")})})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format_metadata$34(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      PyObject var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(353);
      var1.getlocal(1).__getattr__("metadata").__getattr__("write_pkg_file").__call__(var2, var1.getlocal(2));
      var1.setline(354);
      var3 = var1.getlocal(2).__getattr__("getvalue").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_custom_pydistutils$35(PyFrame var1, ThreadState var2) {
      var1.setline(359);
      PyObject var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("posix"));
      var3 = null;
      PyString var7;
      if (var10000.__nonzero__()) {
         var1.setline(360);
         var7 = PyString.fromInterned(".pydistutils.cfg");
         var1.setlocal(1, var7);
         var3 = null;
      } else {
         var1.setline(362);
         var7 = PyString.fromInterned("pydistutils.cfg");
         var1.setlocal(1, var7);
         var3 = null;
      }

      var1.setline(364);
      var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(365);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(2), var1.getlocal(1));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(366);
      var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(368);
         var1.getlocal(3).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("."));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(370);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(370);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var3 = null;

      try {
         var1.setline(373);
         PyObject var4 = var1.getglobal("Distribution").__call__(var2);
         var1.setlocal(4, var4);
         var4 = null;
         var1.setline(376);
         var4 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var4._in(new PyTuple(new PyObject[]{PyString.fromInterned("linux"), PyString.fromInterned("darwin")}));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(377);
            var4 = var1.getlocal(2);
            var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("HOME"), var4);
            var4 = null;
            var1.setline(378);
            var4 = var1.getlocal(4).__getattr__("find_config_files").__call__(var2);
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(379);
            var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getlocal(1), var1.getlocal(5));
         }

         var1.setline(382);
         var4 = var1.getglobal("sys").__getattr__("platform");
         var10000 = var4._eq(PyString.fromInterned("win32"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(384);
            var4 = var1.getlocal(2);
            var1.getglobal("os").__getattr__("environ").__setitem__((PyObject)PyString.fromInterned("HOME"), var4);
            var4 = null;
            var1.setline(385);
            var4 = var1.getlocal(4).__getattr__("find_config_files").__call__(var2);
            var1.setlocal(5, var4);
            var4 = null;
            var1.setline(386);
            var1.getlocal(0).__getattr__("assertIn").__call__(var2, var1.getlocal(1), var1.getlocal(5), PyString.fromInterned("%r not found in %r")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(5)})));
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(389);
         var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(1));
         throw (Throwable)var6;
      }

      var1.setline(389);
      var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_fix_help_options$36(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyList var3 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b"), PyString.fromInterned("c"), PyString.fromInterned("d")}), new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(3), Py.newInteger(4)})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(393);
      PyObject var4 = var1.getglobal("fix_help_options").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(394);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(Py.newInteger(0)), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b"), PyString.fromInterned("c")})));
      var1.setline(395);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getitem__(Py.newInteger(1)), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(3)})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_show_help$37(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      var1.setline(399);
      PyObject var3 = var1.getglobal("Distribution").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(400);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getglobal("sys").__setattr__((String)"argv", var7);
      var3 = null;
      var1.setline(401);
      PyInteger var8 = Py.newInteger(1);
      var1.getlocal(1).__setattr__((String)"help", var8);
      var3 = null;
      var1.setline(402);
      PyString var9 = PyString.fromInterned("setup.py");
      var1.getlocal(1).__setattr__((String)"script_name", var9);
      var3 = null;
      ContextManager var10;
      PyObject var4 = (var10 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

      label25: {
         try {
            var1.setlocal(2, var4);
            var1.setline(404);
            var1.getlocal(1).__getattr__("parse_command_line").__call__(var2);
         } catch (Throwable var6) {
            if (var10.__exit__(var2, Py.setException(var6, var1))) {
               break label25;
            }

            throw (Throwable)Py.makeException();
         }

         var10.__exit__(var2, (PyException)null);
      }

      var1.setline(406);
      PyList var10000 = new PyList();
      var3 = var10000.__getattr__("append");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(406);
      var3 = var1.getlocal(2).__getattr__("getvalue").__call__(var2).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__iter__();

      while(true) {
         var1.setline(406);
         var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(406);
            var1.dellocal(4);
            var7 = var10000;
            var1.setlocal(3, var7);
            var3 = null;
            var1.setline(408);
            var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(407);
         PyObject var5 = var1.getlocal(5).__getattr__("strip").__call__(var2);
         PyObject var10001 = var5._ne(PyString.fromInterned(""));
         var5 = null;
         if (var10001.__nonzero__()) {
            var1.setline(406);
            var1.getlocal(4).__call__(var2, var1.getlocal(5));
         }
      }
   }

   public PyObject test_read_metadata$38(PyFrame var1, ThreadState var2) {
      var1.setline(411);
      PyDictionary var3 = new PyDictionary(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("package"), PyString.fromInterned("version"), PyString.fromInterned("1.0"), PyString.fromInterned("long_description"), PyString.fromInterned("desc"), PyString.fromInterned("description"), PyString.fromInterned("xxx"), PyString.fromInterned("download_url"), PyString.fromInterned("http://example.com"), PyString.fromInterned("keywords"), new PyList(new PyObject[]{PyString.fromInterned("one"), PyString.fromInterned("two")}), PyString.fromInterned("requires"), new PyList(new PyObject[]{PyString.fromInterned("foo")})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(419);
      PyObject var4 = var1.getglobal("Distribution").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var4);
      var3 = null;
      var1.setline(420);
      var4 = var1.getlocal(2).__getattr__("metadata");
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(423);
      var4 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(424);
      var1.getlocal(3).__getattr__("write_pkg_file").__call__(var2, var1.getlocal(4));
      var1.setline(425);
      var1.getlocal(4).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(426);
      var1.getlocal(3).__getattr__("read_pkg_file").__call__(var2, var1.getlocal(4));
      var1.setline(428);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("name"), (PyObject)PyString.fromInterned("package"));
      var1.setline(429);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("version"), (PyObject)PyString.fromInterned("1.0"));
      var1.setline(430);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("description"), (PyObject)PyString.fromInterned("xxx"));
      var1.setline(431);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("download_url"), (PyObject)PyString.fromInterned("http://example.com"));
      var1.setline(432);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("keywords"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("one"), PyString.fromInterned("two")})));
      var1.setline(433);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("platforms"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("UNKNOWN")})));
      var1.setline(434);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(3).__getattr__("obsoletes"), var1.getglobal("None"));
      var1.setline(435);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("requires"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("foo")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$39(PyFrame var1, ThreadState var2) {
      var1.setline(439);
      PyObject var3 = var1.getglobal("unittest").__getattr__("TestSuite").__call__(var2);
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(440);
      var1.getlocal(0).__getattr__("addTest").__call__(var2, var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("DistributionTestCase")));
      var1.setline(441);
      var1.getlocal(0).__getattr__("addTest").__call__(var2, var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("MetadataTestCase")));
      var1.setline(442);
      var3 = var1.getlocal(0);
      var1.f_lasti = -1;
      return var3;
   }

   public test_dist$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      test_dist$1 = Py.newCode(0, var2, var1, "test_dist", 18, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      initialize_options$2 = Py.newCode(1, var2, var1, "initialize_options", 25, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TestDistribution$3 = Py.newCode(0, var2, var1, "TestDistribution", 29, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      find_config_files$4 = Py.newCode(1, var2, var1, "find_config_files", 37, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DistributionTestCase$5 = Py.newCode(0, var2, var1, "DistributionTestCase", 41, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$6 = Py.newCode(1, var2, var1, "setUp", 46, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$7 = Py.newCode(1, var2, var1, "tearDown", 51, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "configfiles", "d"};
      create_distribution$8 = Py.newCode(2, var2, var1, "create_distribution", 56, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "files", "stdout"};
      test_debug_mode$9 = Py.newCode(1, var2, var1, "test_debug_mode", 63, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d"};
      test_command_packages_unspecified$10 = Py.newCode(1, var2, var1, "test_command_packages_unspecified", 84, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "test_dist", "d", "cmd"};
      test_command_packages_cmdline$11 = Py.newCode(1, var2, var1, "test_command_packages_cmdline", 89, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "d"};
      test_command_packages_configfile$12 = Py.newCode(1, var2, var1, "test_command_packages_configfile", 104, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmp_dir", "my_file", "klass", "dist", "my_file2"};
      test_write_pkg_file$13 = Py.newCode(1, var2, var1, "test_write_pkg_file", 130, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "_warn", "dist", "warns"};
      String[] var10001 = var2;
      test_dist$py var10007 = self;
      var2 = new String[]{"warns"};
      test_empty_options$14 = Py.newCode(1, var10001, var1, "test_empty_options", 157, false, false, var10007, 14, var2, (String[])null, 1, 4097);
      var2 = new String[]{"msg"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"warns"};
      _warn$15 = Py.newCode(1, var10001, var1, "_warn", 164, false, false, var10007, 15, (String[])null, var2, 0, 4097);
      var2 = new String[]{"self", "attrs", "dist"};
      test_finalize_options$16 = Py.newCode(1, var2, var1, "test_finalize_options", 176, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "cmds"};
      test_get_command_packages$17 = Py.newCode(1, var2, var1, "test_get_command_packages", 187, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "args", "kwargs"};
      test_announce$18 = Py.newCode(1, var2, var1, "test_announce", 199, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user_filename", "f", "_expander", "old_expander", "d", "all_files", "files", "temp_home"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"temp_home"};
      test_find_config_files_disable$19 = Py.newCode(1, var10001, var1, "test_find_config_files_disable", 206, false, false, var10007, 19, var2, (String[])null, 1, 4097);
      var2 = new String[]{"path"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"temp_home"};
      _expander$20 = Py.newCode(1, var10001, var1, "_expander", 217, false, false, var10007, 20, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      MetadataTestCase$21 = Py.newCode(0, var2, var1, "MetadataTestCase", 236, false, false, self, 21, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$22 = Py.newCode(1, var2, var1, "setUp", 239, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$23 = Py.newCode(1, var2, var1, "tearDown", 243, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "dist", "meta"};
      test_classifier$24 = Py.newCode(1, var2, var1, "test_classifier", 248, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "dist", "meta"};
      test_download_url$25 = Py.newCode(1, var2, var1, "test_download_url", 255, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "long_desc", "attrs", "dist", "meta"};
      test_long_description$26 = Py.newCode(1, var2, var1, "test_long_description", 262, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "dist", "meta"};
      test_simple_metadata$27 = Py.newCode(1, var2, var1, "test_simple_metadata", 277, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "dist", "meta"};
      test_provides$28 = Py.newCode(1, var2, var1, "test_provides", 287, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_provides_illegal$29 = Py.newCode(1, var2, var1, "test_provides_illegal", 301, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "dist", "meta"};
      test_requires$30 = Py.newCode(1, var2, var1, "test_requires", 307, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_requires_illegal$31 = Py.newCode(1, var2, var1, "test_requires_illegal", 323, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "dist", "meta"};
      test_obsoletes$32 = Py.newCode(1, var2, var1, "test_obsoletes", 329, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_obsoletes_illegal$33 = Py.newCode(1, var2, var1, "test_obsoletes_illegal", 345, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "sio"};
      format_metadata$34 = Py.newCode(2, var2, var1, "format_metadata", 351, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "user_filename", "temp_dir", "f", "dist", "files"};
      test_custom_pydistutils$35 = Py.newCode(1, var2, var1, "test_custom_pydistutils", 356, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "help_tuples", "fancy_options"};
      test_fix_help_options$36 = Py.newCode(1, var2, var1, "test_fix_help_options", 391, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dist", "s", "output", "_[406_18]", "line"};
      test_show_help$37 = Py.newCode(1, var2, var1, "test_show_help", 397, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "attrs", "dist", "metadata", "PKG_INFO"};
      test_read_metadata$38 = Py.newCode(1, var2, var1, "test_read_metadata", 410, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"suite"};
      test_suite$39 = Py.newCode(0, var2, var1, "test_suite", 438, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_dist$py("distutils/tests/test_dist$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_dist$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.test_dist$1(var2, var3);
         case 2:
            return this.initialize_options$2(var2, var3);
         case 3:
            return this.TestDistribution$3(var2, var3);
         case 4:
            return this.find_config_files$4(var2, var3);
         case 5:
            return this.DistributionTestCase$5(var2, var3);
         case 6:
            return this.setUp$6(var2, var3);
         case 7:
            return this.tearDown$7(var2, var3);
         case 8:
            return this.create_distribution$8(var2, var3);
         case 9:
            return this.test_debug_mode$9(var2, var3);
         case 10:
            return this.test_command_packages_unspecified$10(var2, var3);
         case 11:
            return this.test_command_packages_cmdline$11(var2, var3);
         case 12:
            return this.test_command_packages_configfile$12(var2, var3);
         case 13:
            return this.test_write_pkg_file$13(var2, var3);
         case 14:
            return this.test_empty_options$14(var2, var3);
         case 15:
            return this._warn$15(var2, var3);
         case 16:
            return this.test_finalize_options$16(var2, var3);
         case 17:
            return this.test_get_command_packages$17(var2, var3);
         case 18:
            return this.test_announce$18(var2, var3);
         case 19:
            return this.test_find_config_files_disable$19(var2, var3);
         case 20:
            return this._expander$20(var2, var3);
         case 21:
            return this.MetadataTestCase$21(var2, var3);
         case 22:
            return this.setUp$22(var2, var3);
         case 23:
            return this.tearDown$23(var2, var3);
         case 24:
            return this.test_classifier$24(var2, var3);
         case 25:
            return this.test_download_url$25(var2, var3);
         case 26:
            return this.test_long_description$26(var2, var3);
         case 27:
            return this.test_simple_metadata$27(var2, var3);
         case 28:
            return this.test_provides$28(var2, var3);
         case 29:
            return this.test_provides_illegal$29(var2, var3);
         case 30:
            return this.test_requires$30(var2, var3);
         case 31:
            return this.test_requires_illegal$31(var2, var3);
         case 32:
            return this.test_obsoletes$32(var2, var3);
         case 33:
            return this.test_obsoletes_illegal$33(var2, var3);
         case 34:
            return this.format_metadata$34(var2, var3);
         case 35:
            return this.test_custom_pydistutils$35(var2, var3);
         case 36:
            return this.test_fix_help_options$36(var2, var3);
         case 37:
            return this.test_show_help$37(var2, var3);
         case 38:
            return this.test_read_metadata$38(var2, var3);
         case 39:
            return this.test_suite$39(var2, var3);
         default:
            return null;
      }
   }
}
