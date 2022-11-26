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
@Filename("distutils/tests/test_core.py")
public class test_core$py extends PyFunctionTable implements PyRunnable {
   static test_core$py self;
   static final PyCode f$0;
   static final PyCode CoreTestCase$1;
   static final PyCode setUp$2;
   static final PyCode tearDown$3;
   static final PyCode cleanup_testfn$4;
   static final PyCode write_setup$5;
   static final PyCode test_run_setup_provides_file$6;
   static final PyCode test_run_setup_uses_current_dir$7;
   static final PyCode test_debug_mode$8;
   static final PyCode test_suite$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.core."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.core.");
      var1.setline(3);
      PyObject var3 = imp.importOne("StringIO", var1, -1);
      var1.setlocal("StringIO", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("distutils.core", var1, -1);
      var1.setlocal("distutils", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var3);
      var3 = null;
      var1.setline(7);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(8);
      var3 = imp.importOne("test.test_support", var1, -1);
      var1.setlocal("test", var3);
      var3 = null;
      var1.setline(9);
      String[] var5 = new String[]{"captured_stdout", "run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("captured_stdout", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(10);
      var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(11);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(14);
      PyString var7 = PyString.fromInterned("\n__file__\n\nfrom distutils.core import setup\nsetup()\n");
      var1.setlocal("setup_using___file__", var7);
      var3 = null;
      var1.setline(22);
      var7 = PyString.fromInterned("\nimport os\nprint os.getcwd()\n\nfrom distutils.core import setup\nsetup()\n");
      var1.setlocal("setup_prints_cwd", var7);
      var3 = null;
      var1.setline(32);
      var6 = new PyObject[]{var1.getname("support").__getattr__("EnvironGuard"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("CoreTestCase", var6, CoreTestCase$1);
      var1.setlocal("CoreTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(104);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, test_suite$9, (PyObject)null);
      var1.setlocal("test_suite", var8);
      var3 = null;
      var1.setline(107);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(108);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject CoreTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(34);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, setUp$2, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(40);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$3, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(47);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, cleanup_testfn$4, (PyObject)null);
      var1.setlocal("cleanup_testfn", var4);
      var3 = null;
      var1.setline(54);
      var3 = new PyObject[]{var1.getname("test").__getattr__("test_support").__getattr__("TESTFN")};
      var4 = new PyFunction(var1.f_globals, var3, write_setup$5, (PyObject)null);
      var1.setlocal("write_setup", var4);
      var3 = null;
      var1.setline(62);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_run_setup_provides_file$6, (PyObject)null);
      var1.setlocal("test_run_setup_provides_file", var4);
      var3 = null;
      var1.setline(68);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_run_setup_uses_current_dir$7, (PyObject)null);
      var1.setlocal("test_run_setup_uses_current_dir", var4);
      var3 = null;
      var1.setline(86);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_debug_mode$8, (PyObject)null);
      var1.setlocal("test_debug_mode", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject setUp$2(PyFrame var1, ThreadState var2) {
      var1.setline(35);
      var1.getglobal("super").__call__(var2, var1.getglobal("CoreTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(36);
      PyObject var3 = var1.getglobal("sys").__getattr__("stdout");
      var1.getlocal(0).__setattr__("old_stdout", var3);
      var3 = null;
      var1.setline(37);
      var1.getlocal(0).__getattr__("cleanup_testfn").__call__(var2);
      var1.setline(38);
      PyTuple var4 = new PyTuple(new PyObject[]{var1.getglobal("sys").__getattr__("argv"), var1.getglobal("sys").__getattr__("argv").__getslice__((PyObject)null, (PyObject)null, (PyObject)null)});
      var1.getlocal(0).__setattr__((String)"old_argv", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$3(PyFrame var1, ThreadState var2) {
      var1.setline(41);
      PyObject var3 = var1.getlocal(0).__getattr__("old_stdout");
      var1.getglobal("sys").__setattr__("stdout", var3);
      var3 = null;
      var1.setline(42);
      var1.getlocal(0).__getattr__("cleanup_testfn").__call__(var2);
      var1.setline(43);
      var3 = var1.getlocal(0).__getattr__("old_argv").__getitem__(Py.newInteger(0));
      var1.getglobal("sys").__setattr__("argv", var3);
      var3 = null;
      var1.setline(44);
      var3 = var1.getlocal(0).__getattr__("old_argv").__getitem__(Py.newInteger(1));
      var1.getglobal("sys").__getattr__("argv").__setslice__((PyObject)null, (PyObject)null, (PyObject)null, var3);
      var3 = null;
      var1.setline(45);
      var1.getglobal("super").__call__(var2, var1.getglobal("CoreTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject cleanup_testfn$4(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyObject var3 = var1.getglobal("test").__getattr__("test_support").__getattr__("TESTFN");
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(49);
      if (var1.getglobal("os").__getattr__("path").__getattr__("isfile").__call__(var2, var1.getlocal(1)).__nonzero__()) {
         var1.setline(50);
         var1.getglobal("os").__getattr__("remove").__call__(var2, var1.getlocal(1));
      } else {
         var1.setline(51);
         if (var1.getglobal("os").__getattr__("path").__getattr__("isdir").__call__(var2, var1.getlocal(1)).__nonzero__()) {
            var1.setline(52);
            var1.getglobal("shutil").__getattr__("rmtree").__call__(var2, var1.getlocal(1));
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject write_setup$5(PyFrame var1, ThreadState var2) {
      var1.setline(55);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(3, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(57);
         var1.getlocal(3).__getattr__("write").__call__(var2, var1.getlocal(1));
      } catch (Throwable var4) {
         Py.addTraceback(var4, var1);
         var1.setline(59);
         var1.getlocal(3).__getattr__("close").__call__(var2);
         throw (Throwable)var4;
      }

      var1.setline(59);
      var1.getlocal(3).__getattr__("close").__call__(var2);
      var1.setline(60);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject test_run_setup_provides_file$6(PyFrame var1, ThreadState var2) {
      var1.setline(65);
      var1.getglobal("distutils").__getattr__("core").__getattr__("run_setup").__call__(var2, var1.getlocal(0).__getattr__("write_setup").__call__(var2, var1.getglobal("setup_using___file__")));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_run_setup_uses_current_dir$7(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyObject var3 = var1.getglobal("StringIO").__getattr__("StringIO").__call__(var2);
      var1.getglobal("sys").__setattr__("stdout", var3);
      var3 = null;
      var1.setline(73);
      var3 = var1.getglobal("os").__getattr__("getcwd").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(76);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getglobal("test").__getattr__("test_support").__getattr__("TESTFN"));
      var1.setline(77);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("test").__getattr__("test_support").__getattr__("TESTFN"), (PyObject)PyString.fromInterned("setup.py"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(78);
      PyObject var10000 = var1.getglobal("distutils").__getattr__("core").__getattr__("run_setup");
      PyObject var10002 = var1.getlocal(0).__getattr__("write_setup");
      PyObject[] var5 = new PyObject[]{var1.getglobal("setup_prints_cwd"), var1.getlocal(2)};
      String[] var4 = new String[]{"path"};
      var10002 = var10002.__call__(var2, var5, var4);
      var3 = null;
      var10000.__call__(var2, var10002);
      var1.setline(81);
      var3 = var1.getglobal("sys").__getattr__("stdout").__getattr__("getvalue").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(82);
      if (var1.getlocal(3).__getattr__("endswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\n")).__nonzero__()) {
         var1.setline(83);
         var3 = var1.getlocal(3).__getslice__((PyObject)null, Py.newInteger(-1), (PyObject)null);
         var1.setlocal(3, var3);
         var3 = null;
      }

      var1.setline(84);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_debug_mode$8(PyFrame var1, ThreadState var2) {
      var1.f_exits = new PyObject[2];
      var1.setline(88);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("setup.py"), PyString.fromInterned("--name")});
      var1.getglobal("sys").__setattr__((String)"argv", var3);
      var3 = null;
      ContextManager var10;
      PyObject var4 = (var10 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

      String[] var5;
      PyObject var10000;
      label35: {
         try {
            var1.setlocal(1, var4);
            var1.setline(90);
            var10000 = var1.getglobal("distutils").__getattr__("core").__getattr__("setup");
            PyObject[] var12 = new PyObject[]{PyString.fromInterned("bar")};
            var5 = new String[]{"name"};
            var10000.__call__(var2, var12, var5);
            var4 = null;
         } catch (Throwable var9) {
            if (var10.__exit__(var2, Py.setException(var9, var1))) {
               break label35;
            }

            throw (Throwable)Py.makeException();
         }

         var10.__exit__(var2, (PyException)null);
      }

      var1.setline(91);
      var1.getlocal(1).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(92);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("read").__call__(var2), (PyObject)PyString.fromInterned("bar\n"));
      var1.setline(94);
      PyObject var11 = var1.getglobal("True");
      var1.getglobal("distutils").__getattr__("core").__setattr__("DEBUG", var11);
      var3 = null;
      var3 = null;

      try {
         label39: {
            ContextManager var15;
            PyObject var14 = (var15 = ContextGuard.getManager(var1.getglobal("captured_stdout").__call__(var2))).__enter__(var2);

            try {
               var1.setlocal(1, var14);
               var1.setline(97);
               var10000 = var1.getglobal("distutils").__getattr__("core").__getattr__("setup");
               PyObject[] var16 = new PyObject[]{PyString.fromInterned("bar")};
               String[] var6 = new String[]{"name"};
               var10000.__call__(var2, var16, var6);
               var5 = null;
            } catch (Throwable var7) {
               if (var15.__exit__(var2, Py.setException(var7, var1))) {
                  break label39;
               }

               throw (Throwable)Py.makeException();
            }

            var15.__exit__(var2, (PyException)null);
         }
      } catch (Throwable var8) {
         Py.addTraceback(var8, var1);
         var1.setline(99);
         var4 = var1.getglobal("False");
         var1.getglobal("distutils").__getattr__("core").__setattr__("DEBUG", var4);
         var4 = null;
         throw (Throwable)var8;
      }

      var1.setline(99);
      var4 = var1.getglobal("False");
      var1.getglobal("distutils").__getattr__("core").__setattr__("DEBUG", var4);
      var4 = null;
      var1.setline(100);
      var1.getlocal(1).__getattr__("seek").__call__((ThreadState)var2, (PyObject)Py.newInteger(0));
      var1.setline(101);
      PyString var13 = PyString.fromInterned("options (after parsing config files):\n");
      var1.setlocal(2, var13);
      var3 = null;
      var1.setline(102);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(1).__getattr__("readlines").__call__(var2).__getitem__(Py.newInteger(0)), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$9(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("CoreTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_core$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      CoreTestCase$1 = Py.newCode(0, var2, var1, "CoreTestCase", 32, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self"};
      setUp$2 = Py.newCode(1, var2, var1, "setUp", 34, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$3 = Py.newCode(1, var2, var1, "tearDown", 40, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "path"};
      cleanup_testfn$4 = Py.newCode(1, var2, var1, "cleanup_testfn", 47, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "text", "path", "f"};
      write_setup$5 = Py.newCode(3, var2, var1, "write_setup", 54, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_run_setup_provides_file$6 = Py.newCode(1, var2, var1, "test_run_setup_provides_file", 62, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cwd", "setup_py", "output"};
      test_run_setup_uses_current_dir$7 = Py.newCode(1, var2, var1, "test_run_setup_uses_current_dir", 68, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "stdout", "wanted"};
      test_debug_mode$8 = Py.newCode(1, var2, var1, "test_debug_mode", 86, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$9 = Py.newCode(0, var2, var1, "test_suite", 104, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_core$py("distutils/tests/test_core$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_core$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.CoreTestCase$1(var2, var3);
         case 2:
            return this.setUp$2(var2, var3);
         case 3:
            return this.tearDown$3(var2, var3);
         case 4:
            return this.cleanup_testfn$4(var2, var3);
         case 5:
            return this.write_setup$5(var2, var3);
         case 6:
            return this.test_run_setup_provides_file$6(var2, var3);
         case 7:
            return this.test_run_setup_uses_current_dir$7(var2, var3);
         case 8:
            return this.test_debug_mode$8(var2, var3);
         case 9:
            return this.test_suite$9(var2, var3);
         default:
            return null;
      }
   }
}
