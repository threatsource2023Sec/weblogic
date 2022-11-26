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
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_install_scripts.py")
public class test_install_scripts$py extends PyFunctionTable implements PyRunnable {
   static test_install_scripts$py self;
   static final PyCode f$0;
   static final PyCode InstallScriptsTestCase$1;
   static final PyCode test_default_settings$2;
   static final PyCode test_installation$3;
   static final PyCode write_script$4;
   static final PyCode test_suite$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.install_scripts."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.install_scripts.");
      var1.setline(3);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(6);
      String[] var5 = new String[]{"install_scripts"};
      PyObject[] var6 = imp.importFrom("distutils.command.install_scripts", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("install_scripts", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"Distribution"};
      var6 = imp.importFrom("distutils.core", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"run_unittest"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(13);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("InstallScriptsTestCase", var6, InstallScriptsTestCase$1);
      var1.setlocal("InstallScriptsTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(78);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$5, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(81);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(82);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject InstallScriptsTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_default_settings$2, (PyObject)null);
      var1.setlocal("test_default_settings", var4);
      var3 = null;
      var1.setline(39);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_installation$3, (PyObject)null);
      var1.setlocal("test_installation", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_default_settings$2(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getglobal("Distribution").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(19);
      PyObject var10000 = var1.getglobal("support").__getattr__("DummyCommand");
      PyObject[] var5 = new PyObject[]{PyString.fromInterned("/foo/bar")};
      String[] var4 = new String[]{"build_scripts"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(1).__getattr__("command_obj").__setitem__((PyObject)PyString.fromInterned("build"), var3);
      var3 = null;
      var1.setline(21);
      var10000 = var1.getglobal("support").__getattr__("DummyCommand");
      var5 = new PyObject[]{PyString.fromInterned("/splat/funk"), Py.newInteger(1), Py.newInteger(1)};
      var4 = new String[]{"install_scripts", "force", "skip_build"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(1).__getattr__("command_obj").__setitem__((PyObject)PyString.fromInterned("install"), var3);
      var3 = null;
      var1.setline(26);
      var3 = var1.getglobal("install_scripts").__call__(var2, var1.getlocal(1));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(27);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(2).__getattr__("force").__not__());
      var1.setline(28);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(2).__getattr__("skip_build").__not__());
      var1.setline(29);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getlocal(2).__getattr__("build_dir");
      PyObject var10002 = var3._is(var1.getglobal("None"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(30);
      var10000 = var1.getlocal(0).__getattr__("assertTrue");
      var3 = var1.getlocal(2).__getattr__("install_dir");
      var10002 = var3._is(var1.getglobal("None"));
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(32);
      var1.getlocal(2).__getattr__("finalize_options").__call__(var2);
      var1.setline(34);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(2).__getattr__("force"));
      var1.setline(35);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getlocal(2).__getattr__("skip_build"));
      var1.setline(36);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("build_dir"), (PyObject)PyString.fromInterned("/foo/bar"));
      var1.setline(37);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(2).__getattr__("install_dir"), (PyObject)PyString.fromInterned("/splat/funk"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_installation$3(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setderef(1, var3);
      var3 = null;
      var1.setline(41);
      PyList var6 = new PyList(Py.EmptyObjects);
      var1.setderef(0, var6);
      var3 = null;
      var1.setline(43);
      PyObject[] var7 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var7;
      PyCode var10004 = write_script$4;
      var7 = new PyObject[]{var1.getclosure(0), var1.getclosure(1)};
      PyFunction var9 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var7);
      var1.setlocal(1, var9);
      var3 = null;
      var1.setline(51);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("script1.py"), (PyObject)PyString.fromInterned("#! /usr/bin/env python2.3\n# bogus script w/ Python sh-bang\npass\n"));
      var1.setline(54);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("script2.py"), (PyObject)PyString.fromInterned("#!/usr/bin/python\n# bogus script w/ Python sh-bang\npass\n"));
      var1.setline(57);
      var1.getlocal(1).__call__((ThreadState)var2, (PyObject)PyString.fromInterned("shell.sh"), (PyObject)PyString.fromInterned("#!/bin/sh\n# bogus shell script w/ sh-bang\nexit 0\n"));
      var1.setline(61);
      var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(62);
      var3 = var1.getglobal("Distribution").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(63);
      PyObject var10000 = var1.getglobal("support").__getattr__("DummyCommand");
      var7 = new PyObject[]{var1.getderef(1)};
      String[] var4 = new String[]{"build_scripts"};
      var10000 = var10000.__call__(var2, var7, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(3).__getattr__("command_obj").__setitem__((PyObject)PyString.fromInterned("build"), var3);
      var3 = null;
      var1.setline(64);
      var10000 = var1.getglobal("support").__getattr__("DummyCommand");
      var7 = new PyObject[]{var1.getlocal(2), Py.newInteger(1), Py.newInteger(1)};
      var4 = new String[]{"install_scripts", "force", "skip_build"};
      var10000 = var10000.__call__(var2, var7, var4);
      var3 = null;
      var3 = var10000;
      var1.getlocal(3).__getattr__("command_obj").__setitem__((PyObject)PyString.fromInterned("install"), var3);
      var3 = null;
      var1.setline(69);
      var3 = var1.getglobal("install_scripts").__call__(var2, var1.getlocal(3));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(70);
      var1.getlocal(4).__getattr__("finalize_options").__call__(var2);
      var1.setline(71);
      var1.getlocal(4).__getattr__("run").__call__(var2);
      var1.setline(73);
      var3 = var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(74);
      var3 = var1.getderef(0).__iter__();

      while(true) {
         var1.setline(74);
         PyObject var8 = var3.__iternext__();
         if (var8 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var8);
         var1.setline(75);
         var10000 = var1.getlocal(0).__getattr__("assertTrue");
         PyObject var5 = var1.getlocal(6);
         var10002 = var5._in(var1.getlocal(5));
         var5 = null;
         var10000.__call__(var2, var10002);
      }
   }

   public PyObject write_script$4(PyFrame var1, ThreadState var2) {
      var1.setline(44);
      var1.getderef(0).__getattr__("append").__call__(var2, var1.getlocal(0));
      var1.setline(45);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getderef(1), var1.getlocal(0)), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(47);
         var1.getlocal(2).__getattr__("write").__call__(var2, var1.getlocal(1));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(49);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(49);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$5(PyFrame var1, ThreadState var2) {
      var1.setline(79);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("InstallScriptsTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_install_scripts$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      InstallScriptsTestCase$1 = Py.newCode(0, var2, var1, "InstallScriptsTestCase", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dist", "cmd"};
      test_default_settings$2 = Py.newCode(1, var2, var1, "test_default_settings", 17, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "write_script", "target", "dist", "cmd", "installed", "name", "expected", "source"};
      String[] var10001 = var2;
      test_install_scripts$py var10007 = self;
      var2 = new String[]{"expected", "source"};
      test_installation$3 = Py.newCode(1, var10001, var1, "test_installation", 39, false, false, var10007, 3, var2, (String[])null, 2, 4097);
      var2 = new String[]{"name", "text", "f"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"expected", "source"};
      write_script$4 = Py.newCode(2, var10001, var1, "write_script", 43, false, false, var10007, 4, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      test_suite$5 = Py.newCode(0, var2, var1, "test_suite", 78, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_install_scripts$py("distutils/tests/test_install_scripts$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_install_scripts$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.InstallScriptsTestCase$1(var2, var3);
         case 2:
            return this.test_default_settings$2(var2, var3);
         case 3:
            return this.test_installation$3(var2, var3);
         case 4:
            return this.write_script$4(var2, var3);
         case 5:
            return this.test_suite$5(var2, var3);
         default:
            return null;
      }
   }
}
