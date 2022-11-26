package distutils.tests;

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
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_build_scripts.py")
public class test_build_scripts$py extends PyFunctionTable implements PyRunnable {
   static test_build_scripts$py self;
   static final PyCode f$0;
   static final PyCode BuildScriptsTestCase$1;
   static final PyCode test_default_settings$2;
   static final PyCode test_build$3;
   static final PyCode get_build_scripts_cmd$4;
   static final PyCode write_sample_scripts$5;
   static final PyCode write_script$6;
   static final PyCode test_version_int$7;
   static final PyCode test_suite$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.build_scripts."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.build_scripts.");
      var1.setline(3);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(6);
      String[] var5 = new String[]{"build_scripts"};
      PyObject[] var6 = imp.importFrom("distutils.command.build_scripts", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("build_scripts", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"Distribution"};
      var6 = imp.importFrom("distutils.core", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(8);
      var3 = imp.importOne("sysconfig", var1, -1);
      var1.setlocal("sysconfig", var3);
      var3 = null;
      var1.setline(10);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(11);
      var5 = new String[]{"run_unittest"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(14);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("BuildScriptsTestCase", var6, BuildScriptsTestCase$1);
      var1.setlocal("BuildScriptsTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(108);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$8, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(111);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(112);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BuildScriptsTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(18);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_default_settings$2, (PyObject)null);
      var1.setlocal("test_default_settings", var4);
      var3 = null;
      var1.setline(28);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_build$3, (PyObject)null);
      var1.setlocal("test_build", var4);
      var3 = null;
      var1.setline(43);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_build_scripts_cmd$4, (PyObject)null);
      var1.setlocal("get_build_scripts_cmd", var4);
      var3 = null;
      var1.setline(54);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write_sample_scripts$5, (PyObject)null);
      var1.setlocal("write_sample_scripts", var4);
      var3 = null;
      var1.setline(73);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, write_script$6, (PyObject)null);
      var1.setlocal("write_script", var4);
      var3 = null;
      var1.setline(80);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_version_int$7, (PyObject)null);
      var1.setlocal("test_version_int", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_default_settings$2(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyObject var3 = var1.getlocal(0).__getattr__("get_build_scripts_cmd").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/foo/bar"), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(20);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("force").__not__());
      var1.setline(21);
      PyObject var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getlocal(1).__getattr__("build_dir");
      PyObject var10002 = var3._is(var1.getglobal("None"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(23);
      var1.getlocal(1).__getattr__("finalize_options").__call__(var2);
      var1.setline(25);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(1).__getattr__("force"));
      var1.setline(26);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("build_dir"), (PyObject)PyString.fromInterned("/foo/bar"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_build$3(PyFrame var1, ThreadState var2) {
      var1.setline(29);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(30);
      var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(31);
      var3 = var1.getlocal(0).__getattr__("write_sample_scripts").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(33);
      PyObject var10000 = var1.getlocal(0).__getattr__("get_build_scripts_cmd");
      PyObject var10002 = var1.getlocal(2);
      PyList var10003 = new PyList();
      var3 = var10003.__getattr__("append");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(35);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(35);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(35);
            var1.dellocal(5);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(36);
            var1.getlocal(4).__getattr__("finalize_options").__call__(var2);
            var1.setline(37);
            var1.getlocal(4).__getattr__("run").__call__(var2);
            var1.setline(39);
            var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(2));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(40);
            var3 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(40);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(8, var4);
               var1.setline(41);
               var10000 = var1.getlocal(0).__getattr__("assertTrue");
               PyObject var5 = var1.getlocal(8);
               var10002 = var5._in(var1.getlocal(7));
               var5 = null;
               var10000.__call__(var2, var10002);
            }
         }

         var1.setlocal(6, var4);
         var1.setline(34);
         var1.getlocal(5).__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(6)));
      }
   }

   public PyObject get_build_scripts_cmd$4(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      PyObject var3 = imp.importOne("sys", var1, -1);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(45);
      var3 = var1.getglobal("Distribution").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(46);
      var3 = var1.getlocal(2);
      var1.getlocal(4).__setattr__("scripts", var3);
      var3 = null;
      var1.setline(47);
      PyObject var10000 = var1.getglobal("support").__getattr__("DummyCommand");
      PyObject[] var5 = new PyObject[]{var1.getlocal(1), Py.newInteger(1), var1.getlocal(3).__getattr__("executable")};
      String[] var4 = new String[]{"build_scripts", "force", "executable"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(4).__getattr__("command_obj").__setitem__((PyObject)PyString.fromInterned("build"), var3);
      var3 = null;
      var1.setline(52);
      var3 = var1.getglobal("build_scripts").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write_sample_scripts$5(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(56);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("script1.py"));
      var1.setline(57);
      var1.getlocal(0).__getattr__("write_script").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("script1.py"), (PyObject)PyString.fromInterned("#! /usr/bin/env python2.3\n# bogus script w/ Python sh-bang\npass\n"));
      var1.setline(61);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("script2.py"));
      var1.setline(62);
      var1.getlocal(0).__getattr__("write_script").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("script2.py"), (PyObject)PyString.fromInterned("#!/usr/bin/python\n# bogus script w/ Python sh-bang\npass\n"));
      var1.setline(66);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("shell.sh"));
      var1.setline(67);
      var1.getlocal(0).__getattr__("write_script").__call__((ThreadState)var2, var1.getlocal(1), (PyObject)PyString.fromInterned("shell.sh"), (PyObject)PyString.fromInterned("#!/bin/sh\n# bogus shell script w/ sh-bang\nexit 0\n"));
      var1.setline(71);
      PyObject var4 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject write_script$6(PyFrame var1, ThreadState var2) {
      var1.setline(74);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(2)), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(4, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(76);
         var1.getlocal(4).__getattr__("write").__call__(var2, var1.getlocal(3));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(78);
         var1.getlocal(4).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(78);
      var1.getlocal(4).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_version_int$7(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(82);
      var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(83);
      var3 = var1.getlocal(0).__getattr__("write_sample_scripts").__call__(var2, var1.getlocal(1));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(86);
      PyObject var10000 = var1.getlocal(0).__getattr__("get_build_scripts_cmd");
      PyObject var10002 = var1.getlocal(2);
      PyList var10003 = new PyList();
      var3 = var10003.__getattr__("append");
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(88);
      var3 = var1.getlocal(3).__iter__();

      while(true) {
         var1.setline(88);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.setline(88);
            var1.dellocal(5);
            var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003);
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(89);
            var1.getlocal(4).__getattr__("finalize_options").__call__(var2);
            var1.setline(96);
            var3 = var1.getglobal("sysconfig").__getattr__("get_config_vars").__call__(var2).__getattr__("get").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("VERSION"));
            var1.setlocal(7, var3);
            var3 = null;
            var1.setline(97);
            PyInteger var7 = Py.newInteger(4);
            var1.getglobal("sysconfig").__getattr__("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("VERSION"), var7);
            var3 = null;
            var3 = null;

            try {
               var1.setline(99);
               var1.getlocal(4).__getattr__("run").__call__(var2);
            } catch (Throwable var6) {
               Py.addTraceback(var6, var1);
               var1.setline(101);
               var4 = var1.getlocal(7);
               var10000 = var4._isnot(var1.getglobal("None"));
               var4 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(102);
                  var4 = var1.getlocal(7);
                  var1.getglobal("sysconfig").__getattr__("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("VERSION"), var4);
                  var4 = null;
               }

               throw (Throwable)var6;
            }

            var1.setline(101);
            var4 = var1.getlocal(7);
            var10000 = var4._isnot(var1.getglobal("None"));
            var4 = null;
            if (var10000.__nonzero__()) {
               var1.setline(102);
               var4 = var1.getlocal(7);
               var1.getglobal("sysconfig").__getattr__("_CONFIG_VARS").__setitem__((PyObject)PyString.fromInterned("VERSION"), var4);
               var4 = null;
            }

            var1.setline(104);
            var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(2));
            var1.setlocal(8, var3);
            var3 = null;
            var1.setline(105);
            var3 = var1.getlocal(3).__iter__();

            while(true) {
               var1.setline(105);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(9, var4);
               var1.setline(106);
               var10000 = var1.getlocal(0).__getattr__("assertTrue");
               PyObject var5 = var1.getlocal(9);
               var10002 = var5._in(var1.getlocal(8));
               var5 = null;
               var10000.__call__(var2, var10002);
            }
         }

         var1.setlocal(6, var4);
         var1.setline(87);
         var1.getlocal(5).__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(1), var1.getlocal(6)));
      }
   }

   public PyObject test_suite$8(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("BuildScriptsTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_build_scripts$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BuildScriptsTestCase$1 = Py.newCode(0, var2, var1, "BuildScriptsTestCase", 14, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "cmd"};
      test_default_settings$2 = Py.newCode(1, var2, var1, "test_default_settings", 18, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "target", "expected", "cmd", "_[34_42]", "fn", "built", "name"};
      test_build$3 = Py.newCode(1, var2, var1, "test_build", 28, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "target", "scripts", "sys", "dist"};
      get_build_scripts_cmd$4 = Py.newCode(3, var2, var1, "get_build_scripts_cmd", 43, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir", "expected"};
      write_sample_scripts$5 = Py.newCode(2, var2, var1, "write_sample_scripts", 54, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dir", "name", "text", "f"};
      write_script$6 = Py.newCode(4, var2, var1, "write_script", 73, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "source", "target", "expected", "cmd", "_[87_42]", "fn", "old", "built", "name"};
      test_version_int$7 = Py.newCode(1, var2, var1, "test_version_int", 80, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$8 = Py.newCode(0, var2, var1, "test_suite", 108, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_build_scripts$py("distutils/tests/test_build_scripts$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_build_scripts$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BuildScriptsTestCase$1(var2, var3);
         case 2:
            return this.test_default_settings$2(var2, var3);
         case 3:
            return this.test_build$3(var2, var3);
         case 4:
            return this.get_build_scripts_cmd$4(var2, var3);
         case 5:
            return this.write_sample_scripts$5(var2, var3);
         case 6:
            return this.write_script$6(var2, var3);
         case 7:
            return this.test_version_int$7(var2, var3);
         case 8:
            return this.test_suite$8(var2, var3);
         default:
            return null;
      }
   }
}
