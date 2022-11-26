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
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
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
@Filename("distutils/tests/support.py")
public class support$py extends PyFunctionTable implements PyRunnable {
   static support$py self;
   static final PyCode f$0;
   static final PyCode capture_warnings$1;
   static final PyCode _capture_warnings$2;
   static final PyCode LoggingSilencer$3;
   static final PyCode setUp$4;
   static final PyCode tearDown$5;
   static final PyCode _log$6;
   static final PyCode get_logs$7;
   static final PyCode _format$8;
   static final PyCode clear_logs$9;
   static final PyCode TempdirManager$10;
   static final PyCode setUp$11;
   static final PyCode tearDown$12;
   static final PyCode mkdtemp$13;
   static final PyCode write_file$14;
   static final PyCode create_dist$15;
   static final PyCode DummyCommand$16;
   static final PyCode __init__$17;
   static final PyCode ensure_finalized$18;
   static final PyCode EnvironGuard$19;
   static final PyCode setUp$20;
   static final PyCode tearDown$21;
   static final PyCode copy_xxmodule_c$22;
   static final PyCode _get_xxmodule_path$23;
   static final PyCode fixup_build_ext$24;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Support code for distutils test cases."));
      var1.setline(1);
      PyString.fromInterned("Support code for distutils test cases.");
      var1.setline(2);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("tempfile", var1, -1);
      var1.setlocal("tempfile", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("sysconfig", var1, -1);
      var1.setlocal("sysconfig", var3);
      var3 = null;
      var1.setline(8);
      String[] var5 = new String[]{"deepcopy"};
      PyObject[] var6 = imp.importFrom("copy", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("deepcopy", var4);
      var4 = null;
      var1.setline(9);
      var3 = imp.importOne("warnings", var1, -1);
      var1.setlocal("warnings", var3);
      var3 = null;
      var1.setline(11);
      var5 = new String[]{"log"};
      var6 = imp.importFrom("distutils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(12);
      var5 = new String[]{"DEBUG", "INFO", "WARN", "ERROR", "FATAL"};
      var6 = imp.importFrom("distutils.log", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DEBUG", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("INFO", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("WARN", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("ERROR", var4);
      var4 = null;
      var4 = var6[4];
      var1.setlocal("FATAL", var4);
      var4 = null;
      var1.setline(13);
      var5 = new String[]{"Distribution"};
      var6 = imp.importFrom("distutils.core", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("Distribution", var4);
      var4 = null;
      var1.setline(16);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, capture_warnings$1, (PyObject)null);
      var1.setlocal("capture_warnings", var7);
      var3 = null;
      var1.setline(24);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("LoggingSilencer", var6, LoggingSilencer$3);
      var1.setlocal("LoggingSilencer", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(58);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("TempdirManager", var6, TempdirManager$10);
      var1.setlocal("TempdirManager", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(119);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("DummyCommand", var6, DummyCommand$16);
      var1.setlocal("DummyCommand", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(130);
      var6 = new PyObject[]{var1.getname("object")};
      var4 = Py.makeClass("EnvironGuard", var6, EnvironGuard$19);
      var1.setlocal("EnvironGuard", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(148);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, copy_xxmodule_c$22, PyString.fromInterned("Helper for tests that need the xxmodule.c source file.\n\n    Example use:\n\n        def test_compile(self):\n            copy_xxmodule_c(self.tmpdir)\n            self.assertIn('xxmodule.c', os.listdir(self.tmpdir))\n\n    If the source file can be found, it will be copied to *directory*.  If not,\n    the test will be skipped.  Errors during copy are not caught.\n    "));
      var1.setlocal("copy_xxmodule_c", var7);
      var3 = null;
      var1.setline(167);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, _get_xxmodule_path$23, (PyObject)null);
      var1.setlocal("_get_xxmodule_path", var7);
      var3 = null;
      var1.setline(186);
      var6 = Py.EmptyObjects;
      var7 = new PyFunction(var1.f_globals, var6, fixup_build_ext$24, PyString.fromInterned("Function needed to make build_ext tests pass.\n\n    When Python was build with --enable-shared on Unix, -L. is not good\n    enough to find the libpython<blah>.so.  This is because regrtest runs\n    it under a tempdir, not in the top level where the .so lives.  By the\n    time we've gotten here, Python's already been chdir'd to the tempdir.\n\n    When Python was built with in debug mode on Windows, build_ext commands\n    need their debug attribute set, and it is not done automatically for\n    some reason.\n\n    This function handles both of these things.  Example use:\n\n        cmd = build_ext(dist)\n        support.fixup_build_ext(cmd)\n        cmd.ensure_finalized()\n\n    Unlike most other Unix platforms, Mac OS X embeds absolute paths\n    to shared libraries into executables, so the fixup is not needed there.\n    "));
      var1.setlocal("fixup_build_ext", var7);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject capture_warnings$1(PyFrame var1, ThreadState var2) {
      var1.to_cell(0, 0);
      var1.setline(17);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var10002 = var1.f_globals;
      PyObject[] var10003 = var3;
      PyCode var10004 = _capture_warnings$2;
      var3 = new PyObject[]{var1.getclosure(0)};
      PyFunction var4 = new PyFunction(var10002, var10003, var10004, (PyObject)null, var3);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(21);
      PyObject var5 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject _capture_warnings$2(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[1];
      ContextManager var3;
      PyObject var4 = (var3 = ContextGuard.getManager(var1.getglobal("warnings").__getattr__("catch_warnings").__call__(var2))).__enter__(var2);

      Throwable var10000;
      label28: {
         boolean var10001;
         try {
            var1.setline(19);
            var1.getglobal("warnings").__getattr__("simplefilter").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ignore"));
            var1.setline(20);
            PyObject var9 = var1.getderef(0);
            PyObject[] var8 = Py.EmptyObjects;
            String[] var5 = new String[0];
            var9 = var9._callextra(var8, var5, var1.getlocal(0), var1.getlocal(1));
            var4 = null;
            var4 = var9;
         } catch (Throwable var7) {
            var10000 = var7;
            var10001 = false;
            break label28;
         }

         var3.__exit__(var2, (PyException)null);

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
         }
      }

      if (!var3.__exit__(var2, Py.setException(var10000, var1))) {
         throw (Throwable)Py.makeException();
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject LoggingSilencer$3(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(26);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$4, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(36);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$5, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(41);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, _log$6, (PyObject)null);
      var1.setlocal("_log", var4);
      var3 = null;
      var1.setline(46);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, get_logs$7, (PyObject)null);
      var1.setlocal("get_logs", var4);
      var3 = null;
      var1.setline(54);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, clear_logs$9, (PyObject)null);
      var1.setlocal("clear_logs", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$4(PyFrame var1, ThreadState var2) {
      var1.setline(27);
      var1.getglobal("super").__call__(var2, var1.getglobal("LoggingSilencer"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(28);
      PyObject var3 = var1.getglobal("log").__getattr__("set_threshold").__call__(var2, var1.getglobal("log").__getattr__("FATAL"));
      var1.getlocal(0).__setattr__("threshold", var3);
      var3 = null;
      var1.setline(32);
      var3 = var1.getglobal("log").__getattr__("Log").__getattr__("_log");
      var1.getlocal(0).__setattr__("_old_log", var3);
      var3 = null;
      var1.setline(33);
      var3 = var1.getlocal(0).__getattr__("_log");
      var1.getglobal("log").__getattr__("Log").__setattr__("_log", var3);
      var3 = null;
      var1.setline(34);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"logs", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$5(PyFrame var1, ThreadState var2) {
      var1.setline(37);
      var1.getglobal("log").__getattr__("set_threshold").__call__(var2, var1.getlocal(0).__getattr__("threshold"));
      var1.setline(38);
      PyObject var3 = var1.getlocal(0).__getattr__("_old_log");
      var1.getglobal("log").__getattr__("Log").__setattr__("_log", var3);
      var3 = null;
      var1.setline(39);
      var1.getglobal("super").__call__(var2, var1.getglobal("LoggingSilencer"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject _log$6(PyFrame var1, ThreadState var2) {
      var1.setline(42);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._notin(new PyTuple(new PyObject[]{var1.getglobal("DEBUG"), var1.getglobal("INFO"), var1.getglobal("WARN"), var1.getglobal("ERROR"), var1.getglobal("FATAL")}));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(43);
         throw Py.makeException(var1.getglobal("ValueError").__call__(var2, PyString.fromInterned("%s wrong log level")._mod(var1.getglobal("str").__call__(var2, var1.getlocal(1)))));
      } else {
         var1.setline(44);
         var1.getlocal(0).__getattr__("logs").__getattr__("append").__call__((ThreadState)var2, (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3)})));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject get_logs$7(PyFrame var1, ThreadState var2) {
      var1.setline(47);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var3, _format$8, (PyObject)null);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(51);
      PyList var10000 = new PyList();
      PyObject var8 = var10000.__getattr__("append");
      var1.setlocal(3, var8);
      var3 = null;
      var1.setline(51);
      var8 = var1.getlocal(0).__getattr__("logs").__iter__();

      while(true) {
         var1.setline(51);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(51);
            var1.dellocal(3);
            PyList var9 = var10000;
            var1.f_lasti = -1;
            return var9;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(6, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(4, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(5, var6);
         var6 = null;
         var1.setline(52);
         PyObject var10 = var1.getlocal(6);
         PyObject var10001 = var10._in(var1.getlocal(1));
         var5 = null;
         if (var10001.__nonzero__()) {
            var1.setline(51);
            var1.getlocal(3).__call__(var2, var1.getlocal(2).__call__(var2, var1.getlocal(4), var1.getlocal(5)));
         }
      }
   }

   public PyObject _format$8(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(49);
         var3 = var1.getlocal(0);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(50);
         var3 = var1.getlocal(0)._mod(var1.getlocal(1));
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject clear_logs$9(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"logs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject TempdirManager$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Mix-in class that handles temporary directories for test cases.\n\n    This is intended to be used with unittest.TestCase.\n    "));
      var1.setline(62);
      PyString.fromInterned("Mix-in class that handles temporary directories for test cases.\n\n    This is intended to be used with unittest.TestCase.\n    ");
      var1.setline(64);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$11, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(69);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$12, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(78);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, mkdtemp$13, PyString.fromInterned("Create a temporary directory that will be cleaned up.\n\n        Returns the path of the directory.\n        "));
      var1.setlocal("mkdtemp", var4);
      var3 = null;
      var1.setline(87);
      var3 = new PyObject[]{PyString.fromInterned("xxx")};
      var4 = new PyFunction(var1.f_globals, var3, write_file$14, PyString.fromInterned("Writes a file in the given path.\n\n\n        path can be a string or a sequence.\n        "));
      var1.setlocal("write_file", var4);
      var3 = null;
      var1.setline(101);
      var3 = new PyObject[]{PyString.fromInterned("foo")};
      var4 = new PyFunction(var1.f_globals, var3, create_dist$15, PyString.fromInterned("Will generate a test environment.\n\n        This function creates:\n         - a Distribution instance using keywords\n         - a temporary directory with a package structure\n\n        It returns the package directory and the distribution\n        instance.\n        "));
      var1.setlocal("create_dist", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$11(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      var1.getglobal("super").__call__(var2, var1.getglobal("TempdirManager"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(66);
      PyObject var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.getlocal(0).__setattr__("old_cwd", var3);
      var3 = null;
      var1.setline(67);
      PyList var4 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"tempdirs", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$12(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      var1.getglobal("os").__getattr__("chdir").__call__(var2, var1.getlocal(0).__getattr__("old_cwd"));
      var1.setline(73);
      var1.getglobal("super").__call__(var2, var1.getglobal("TempdirManager"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);

      while(true) {
         var1.setline(74);
         if (!var1.getlocal(0).__getattr__("tempdirs").__nonzero__()) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setline(75);
         PyObject var3 = var1.getlocal(0).__getattr__("tempdirs").__getattr__("pop").__call__(var2);
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(76);
         PyObject var10000 = var1.getglobal("shutil").__getattr__("rmtree");
         PyObject var10002 = var1.getlocal(1);
         var3 = var1.getglobal("os").__getattr__("name");
         PyObject var10003 = var3._in(new PyTuple(new PyObject[]{PyString.fromInterned("nt"), PyString.fromInterned("cygwin")}));
         var3 = null;
         var10000.__call__(var2, var10002, var10003);
      }
   }

   public PyObject mkdtemp$13(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyString.fromInterned("Create a temporary directory that will be cleaned up.\n\n        Returns the path of the directory.\n        ");
      var1.setline(83);
      PyObject var3 = var1.getglobal("tempfile").__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(84);
      var1.getlocal(0).__getattr__("tempdirs").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(85);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject write_file$14(PyFrame var1, ThreadState var2) {
      var1.setline(92);
      PyString.fromInterned("Writes a file in the given path.\n\n\n        path can be a string or a sequence.\n        ");
      var1.setline(93);
      PyObject[] var3;
      PyObject var6;
      if (var1.getglobal("isinstance").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)(new PyTuple(new PyObject[]{var1.getglobal("list"), var1.getglobal("tuple")}))).__nonzero__()) {
         var1.setline(94);
         PyObject var10000 = var1.getglobal("os").__getattr__("path").__getattr__("join");
         var3 = Py.EmptyObjects;
         String[] var4 = new String[0];
         var10000 = var10000._callextra(var3, var4, var1.getlocal(1), (PyObject)null);
         var3 = null;
         var6 = var10000;
         var1.setlocal(1, var6);
         var3 = null;
      }

      var1.setline(95);
      var6 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(3, var6);
      var3 = null;
      var3 = null;

      try {
         var1.setline(97);
         var1.getlocal(3).__getattr__("write").__call__(var2, var1.getlocal(2));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(99);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(99);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject create_dist$15(PyFrame var1, ThreadState var2) {
      var1.setline(110);
      PyString.fromInterned("Will generate a test environment.\n\n        This function creates:\n         - a Distribution instance using keywords\n         - a temporary directory with a package structure\n\n        It returns the package directory and the distribution\n        instance.\n        ");
      var1.setline(111);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(112);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__(var2, var1.getlocal(3), var1.getlocal(1));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(113);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(4));
      var1.setline(114);
      PyObject var10000 = var1.getglobal("Distribution");
      PyObject[] var5 = new PyObject[]{var1.getlocal(2)};
      String[] var4 = new String[]{"attrs"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(116);
      PyTuple var6 = new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(5)});
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject DummyCommand$16(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Class to store options for retrieval via set_undefined_options()."));
      var1.setline(120);
      PyString.fromInterned("Class to store options for retrieval via set_undefined_options().");
      var1.setline(122);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$17, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(126);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, ensure_finalized$18, (PyObject)null);
      var1.setlocal("ensure_finalized", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$17(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyObject var3 = var1.getlocal(1).__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(123);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var1.setline(124);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(2), var1.getlocal(3));
      }
   }

   public PyObject ensure_finalized$18(PyFrame var1, ThreadState var2) {
      var1.setline(127);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject EnvironGuard$19(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(132);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$20, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(136);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$21, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$20(PyFrame var1, ThreadState var2) {
      var1.setline(133);
      var1.getglobal("super").__call__(var2, var1.getglobal("EnvironGuard"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(134);
      PyObject var3 = var1.getglobal("deepcopy").__call__(var2, var1.getglobal("os").__getattr__("environ"));
      var1.getlocal(0).__setattr__("old_environ", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$21(PyFrame var1, ThreadState var2) {
      var1.setline(137);
      PyObject var3 = var1.getlocal(0).__getattr__("old_environ").__getattr__("items").__call__(var2).__iter__();

      while(true) {
         var1.setline(137);
         PyObject var4 = var3.__iternext__();
         PyObject var10000;
         PyObject[] var5;
         PyObject var7;
         if (var4 == null) {
            var1.setline(141);
            var3 = var1.getglobal("os").__getattr__("environ").__getattr__("keys").__call__(var2).__iter__();

            while(true) {
               var1.setline(141);
               var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(145);
                  var1.getglobal("super").__call__(var2, var1.getglobal("EnvironGuard"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(1, var4);
               var1.setline(142);
               var7 = var1.getlocal(1);
               var10000 = var7._notin(var1.getlocal(0).__getattr__("old_environ"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(143);
                  var1.getglobal("os").__getattr__("environ").__delitem__(var1.getlocal(1));
               }
            }
         }

         var5 = Py.unpackSequence(var4, 2);
         PyObject var6 = var5[0];
         var1.setlocal(1, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(2, var6);
         var6 = null;
         var1.setline(138);
         var7 = var1.getglobal("os").__getattr__("environ").__getattr__("get").__call__(var2, var1.getlocal(1));
         var10000 = var7._ne(var1.getlocal(2));
         var5 = null;
         if (var10000.__nonzero__()) {
            var1.setline(139);
            var7 = var1.getlocal(2);
            var1.getglobal("os").__getattr__("environ").__setitem__(var1.getlocal(1), var7);
            var5 = null;
         }
      }
   }

   public PyObject copy_xxmodule_c$22(PyFrame var1, ThreadState var2) {
      var1.setline(159);
      PyString.fromInterned("Helper for tests that need the xxmodule.c source file.\n\n    Example use:\n\n        def test_compile(self):\n            copy_xxmodule_c(self.tmpdir)\n            self.assertIn('xxmodule.c', os.listdir(self.tmpdir))\n\n    If the source file can be found, it will be copied to *directory*.  If not,\n    the test will be skipped.  Errors during copy are not caught.\n    ");
      var1.setline(160);
      PyObject var3 = var1.getglobal("_get_xxmodule_path").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(161);
      var3 = var1.getlocal(1);
      PyObject var10000 = var3._is(var1.getglobal("None"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(162);
         throw Py.makeException(var1.getglobal("unittest").__getattr__("SkipTest").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("cannot find xxmodule.c (test must run in the python build dir)")));
      } else {
         var1.setline(164);
         var1.getglobal("shutil").__getattr__("copy").__call__(var2, var1.getlocal(1), var1.getlocal(0));
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject _get_xxmodule_path$23(PyFrame var1, ThreadState var2) {
      var1.setline(170);
      PyObject var3 = var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("srcdir"));
      var1.setlocal(0, var3);
      var3 = null;
      var1.setline(171);
      PyObject[] var10002 = new PyObject[]{var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("dirname").__call__(var2, var1.getglobal("__file__")), (PyObject)PyString.fromInterned("xxmodule.c")), var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, var1.getlocal(0), (PyObject)PyString.fromInterned("Modules"), (PyObject)PyString.fromInterned("xxmodule.c")), null};
      PyObject var10005 = var1.getglobal("os").__getattr__("path").__getattr__("join");
      PyObject[] var6 = new PyObject[]{var1.getlocal(0), PyString.fromInterned(".."), PyString.fromInterned(".."), PyString.fromInterned(".."), PyString.fromInterned("Modules"), PyString.fromInterned("xxmodule.c")};
      var10002[2] = var10005.__call__(var2, var6);
      PyList var7 = new PyList(var10002);
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(181);
      var3 = var1.getlocal(1).__iter__();

      do {
         var1.setline(181);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(2, var4);
         var1.setline(182);
      } while(!var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(2)).__nonzero__());

      var1.setline(183);
      PyObject var5 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject fixup_build_ext$24(PyFrame var1, ThreadState var2) {
      var1.setline(206);
      PyString.fromInterned("Function needed to make build_ext tests pass.\n\n    When Python was build with --enable-shared on Unix, -L. is not good\n    enough to find the libpython<blah>.so.  This is because regrtest runs\n    it under a tempdir, not in the top level where the .so lives.  By the\n    time we've gotten here, Python's already been chdir'd to the tempdir.\n\n    When Python was built with in debug mode on Windows, build_ext commands\n    need their debug attribute set, and it is not done automatically for\n    some reason.\n\n    This function handles both of these things.  Example use:\n\n        cmd = build_ext(dist)\n        support.fixup_build_ext(cmd)\n        cmd.ensure_finalized()\n\n    Unlike most other Unix platforms, Mac OS X embeds absolute paths\n    to shared libraries into executables, so the fixup is not needed there.\n    ");
      var1.setline(207);
      PyObject var3 = var1.getglobal("os").__getattr__("name");
      PyObject var10000 = var3._eq(PyString.fromInterned("nt"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(208);
         var3 = var1.getglobal("sys").__getattr__("executable").__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("_d.exe"));
         var1.getlocal(0).__setattr__("debug", var3);
         var3 = null;
      } else {
         var1.setline(209);
         if (var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Py_ENABLE_SHARED")).__nonzero__()) {
            var1.setline(213);
            var3 = var1.getglobal("sysconfig").__getattr__("get_config_var").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RUNSHARED"));
            var1.setlocal(1, var3);
            var3 = null;
            var1.setline(214);
            var3 = var1.getlocal(1);
            var10000 = var3._is(var1.getglobal("None"));
            var3 = null;
            PyList var6;
            if (var10000.__nonzero__()) {
               var1.setline(215);
               var6 = new PyList(new PyObject[]{PyString.fromInterned(".")});
               var1.getlocal(0).__setattr__((String)"library_dirs", var6);
               var3 = null;
            } else {
               var1.setline(217);
               var3 = var1.getglobal("sys").__getattr__("platform");
               var10000 = var3._eq(PyString.fromInterned("darwin"));
               var3 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(218);
                  var6 = new PyList(Py.EmptyObjects);
                  var1.getlocal(0).__setattr__((String)"library_dirs", var6);
                  var3 = null;
               } else {
                  var1.setline(220);
                  var3 = var1.getlocal(1).__getattr__("partition").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("="));
                  PyObject[] var4 = Py.unpackSequence(var3, 3);
                  PyObject var5 = var4[0];
                  var1.setlocal(2, var5);
                  var5 = null;
                  var5 = var4[1];
                  var1.setlocal(3, var5);
                  var5 = null;
                  var5 = var4[2];
                  var1.setlocal(4, var5);
                  var5 = null;
                  var3 = null;
                  var1.setline(221);
                  var3 = var1.getlocal(4).__getattr__("split").__call__(var2, var1.getglobal("os").__getattr__("pathsep"));
                  var1.getlocal(0).__setattr__("library_dirs", var3);
                  var3 = null;
               }
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public support$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"func", "_capture_warnings"};
      String[] var10001 = var2;
      support$py var10007 = self;
      var2 = new String[]{"func"};
      capture_warnings$1 = Py.newCode(1, var10001, var1, "capture_warnings", 16, false, false, var10007, 1, var2, (String[])null, 0, 4097);
      var2 = new String[]{"args", "kw"};
      var10001 = var2;
      var10007 = self;
      var2 = new String[]{"func"};
      _capture_warnings$2 = Py.newCode(2, var10001, var1, "_capture_warnings", 17, true, true, var10007, 2, (String[])null, var2, 0, 4097);
      var2 = new String[0];
      LoggingSilencer$3 = Py.newCode(0, var2, var1, "LoggingSilencer", 24, false, false, self, 3, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$4 = Py.newCode(1, var2, var1, "setUp", 26, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$5 = Py.newCode(1, var2, var1, "tearDown", 36, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "level", "msg", "args"};
      _log$6 = Py.newCode(4, var2, var1, "_log", 41, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "levels", "_format", "_[51_16]", "msg", "args", "level"};
      get_logs$7 = Py.newCode(2, var2, var1, "get_logs", 46, true, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"msg", "args"};
      _format$8 = Py.newCode(2, var2, var1, "_format", 47, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      clear_logs$9 = Py.newCode(1, var2, var1, "clear_logs", 54, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      TempdirManager$10 = Py.newCode(0, var2, var1, "TempdirManager", 58, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$11 = Py.newCode(1, var2, var1, "setUp", 64, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d"};
      tearDown$12 = Py.newCode(1, var2, var1, "tearDown", 69, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d"};
      mkdtemp$13 = Py.newCode(1, var2, var1, "mkdtemp", 78, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path", "content", "f"};
      write_file$14 = Py.newCode(3, var2, var1, "write_file", 87, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_name", "kw", "tmp_dir", "pkg_dir", "dist"};
      create_dist$15 = Py.newCode(3, var2, var1, "create_dist", 101, false, true, self, 15, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      DummyCommand$16 = Py.newCode(0, var2, var1, "DummyCommand", 119, false, false, self, 16, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "kwargs", "kw", "val"};
      __init__$17 = Py.newCode(2, var2, var1, "__init__", 122, false, true, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      ensure_finalized$18 = Py.newCode(1, var2, var1, "ensure_finalized", 126, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      EnvironGuard$19 = Py.newCode(0, var2, var1, "EnvironGuard", 130, false, false, self, 19, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$20 = Py.newCode(1, var2, var1, "setUp", 132, false, false, self, 20, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "key", "value"};
      tearDown$21 = Py.newCode(1, var2, var1, "tearDown", 136, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"directory", "filename"};
      copy_xxmodule_c$22 = Py.newCode(1, var2, var1, "copy_xxmodule_c", 148, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"srcdir", "candidates", "path"};
      _get_xxmodule_path$23 = Py.newCode(0, var2, var1, "_get_xxmodule_path", 167, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"cmd", "runshared", "name", "equals", "value"};
      fixup_build_ext$24 = Py.newCode(1, var2, var1, "fixup_build_ext", 186, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new support$py("distutils/tests/support$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(support$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.capture_warnings$1(var2, var3);
         case 2:
            return this._capture_warnings$2(var2, var3);
         case 3:
            return this.LoggingSilencer$3(var2, var3);
         case 4:
            return this.setUp$4(var2, var3);
         case 5:
            return this.tearDown$5(var2, var3);
         case 6:
            return this._log$6(var2, var3);
         case 7:
            return this.get_logs$7(var2, var3);
         case 8:
            return this._format$8(var2, var3);
         case 9:
            return this.clear_logs$9(var2, var3);
         case 10:
            return this.TempdirManager$10(var2, var3);
         case 11:
            return this.setUp$11(var2, var3);
         case 12:
            return this.tearDown$12(var2, var3);
         case 13:
            return this.mkdtemp$13(var2, var3);
         case 14:
            return this.write_file$14(var2, var3);
         case 15:
            return this.create_dist$15(var2, var3);
         case 16:
            return this.DummyCommand$16(var2, var3);
         case 17:
            return this.__init__$17(var2, var3);
         case 18:
            return this.ensure_finalized$18(var2, var3);
         case 19:
            return this.EnvironGuard$19(var2, var3);
         case 20:
            return this.setUp$20(var2, var3);
         case 21:
            return this.tearDown$21(var2, var3);
         case 22:
            return this.copy_xxmodule_c$22(var2, var3);
         case 23:
            return this._get_xxmodule_path$23(var2, var3);
         case 24:
            return this.fixup_build_ext$24(var2, var3);
         default:
            return null;
      }
   }
}
