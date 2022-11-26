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
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_file_util.py")
public class test_file_util$py extends PyFunctionTable implements PyRunnable {
   static test_file_util$py self;
   static final PyCode f$0;
   static final PyCode FileUtilTestCase$1;
   static final PyCode _log$2;
   static final PyCode setUp$3;
   static final PyCode tearDown$4;
   static final PyCode test_move_file_verbosity$5;
   static final PyCode test_write_file$6;
   static final PyCode test_copy_file$7;
   static final PyCode test_suite$8;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.file_util."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.file_util.");
      var1.setline(2);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var3);
      var3 = null;
      var1.setline(6);
      String[] var5 = new String[]{"move_file", "write_file", "copy_file"};
      PyObject[] var6 = imp.importFrom("distutils.file_util", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("move_file", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("write_file", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("copy_file", var4);
      var4 = null;
      var1.setline(7);
      var5 = new String[]{"log"};
      var6 = imp.importFrom("distutils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"run_unittest"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(11);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("FileUtilTestCase", var6, FileUtilTestCase$1);
      var1.setlocal("FileUtilTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(77);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$8, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(80);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(81);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FileUtilTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(13);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _log$2, (PyObject)null);
      var1.setlocal("_log", var4);
      var3 = null;
      var1.setline(19);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setUp$3, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(29);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$4, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(33);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_move_file_verbosity$5, (PyObject)null);
      var1.setlocal("test_move_file_verbosity", var4);
      var3 = null;
      var1.setline(61);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_write_file$6, (PyObject)null);
      var1.setlocal("test_write_file", var4);
      var3 = null;
      var1.setline(69);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_copy_file$7, (PyObject)null);
      var1.setlocal("test_copy_file", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _log$2(PyFrame var1, ThreadState var2) {
      var1.setline(14);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(15);
         var1.getlocal(0).__getattr__("_logs").__getattr__("append").__call__(var2, var1.getlocal(1)._mod(var1.getlocal(2)));
      } else {
         var1.setline(17);
         var1.getlocal(0).__getattr__("_logs").__getattr__("append").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setUp$3(PyFrame var1, ThreadState var2) {
      var1.setline(20);
      var1.getglobal("super").__call__(var2, var1.getglobal("FileUtilTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(21);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_logs", var3);
      var3 = null;
      var1.setline(22);
      PyObject var4 = var1.getglobal("log").__getattr__("info");
      var1.getlocal(0).__setattr__("old_log", var4);
      var3 = null;
      var1.setline(23);
      var4 = var1.getlocal(0).__getattr__("_log");
      var1.getglobal("log").__setattr__("info", var4);
      var3 = null;
      var1.setline(24);
      var4 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(25);
      var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("f1"));
      var1.getlocal(0).__setattr__("source", var4);
      var3 = null;
      var1.setline(26);
      var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("f2"));
      var1.getlocal(0).__setattr__("target", var4);
      var3 = null;
      var1.setline(27);
      var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("d1"));
      var1.getlocal(0).__setattr__("target_dir", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$4(PyFrame var1, ThreadState var2) {
      var1.setline(30);
      PyObject var3 = var1.getlocal(0).__getattr__("old_log");
      var1.getglobal("log").__setattr__("info", var3);
      var3 = null;
      var1.setline(31);
      var1.getglobal("super").__call__(var2, var1.getglobal("FileUtilTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_move_file_verbosity$5(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("source"), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(1, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(36);
         var1.getlocal(1).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("some content"));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(38);
         var1.getlocal(1).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(38);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setline(40);
      PyObject var10000 = var1.getglobal("move_file");
      PyObject[] var6 = new PyObject[]{var1.getlocal(0).__getattr__("source"), var1.getlocal(0).__getattr__("target"), Py.newInteger(0)};
      String[] var4 = new String[]{"verbose"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(41);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(42);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("_logs"), var1.getlocal(2));
      var1.setline(45);
      var10000 = var1.getglobal("move_file");
      var6 = new PyObject[]{var1.getlocal(0).__getattr__("target"), var1.getlocal(0).__getattr__("source"), Py.newInteger(0)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(47);
      var10000 = var1.getglobal("move_file");
      var6 = new PyObject[]{var1.getlocal(0).__getattr__("source"), var1.getlocal(0).__getattr__("target"), Py.newInteger(1)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(48);
      var7 = new PyList(new PyObject[]{PyString.fromInterned("moving %s -> %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("source"), var1.getlocal(0).__getattr__("target")}))});
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(49);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("_logs"), var1.getlocal(2));
      var1.setline(52);
      var10000 = var1.getglobal("move_file");
      var6 = new PyObject[]{var1.getlocal(0).__getattr__("target"), var1.getlocal(0).__getattr__("source"), Py.newInteger(0)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(54);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_logs", var7);
      var3 = null;
      var1.setline(56);
      var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(0).__getattr__("target_dir"));
      var1.setline(57);
      var10000 = var1.getglobal("move_file");
      var6 = new PyObject[]{var1.getlocal(0).__getattr__("source"), var1.getlocal(0).__getattr__("target_dir"), Py.newInteger(1)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var6, var4);
      var3 = null;
      var1.setline(58);
      var7 = new PyList(new PyObject[]{PyString.fromInterned("moving %s -> %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("source"), var1.getlocal(0).__getattr__("target_dir")}))});
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(59);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("_logs"), var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_write_file$6(PyFrame var1, ThreadState var2) {
      var1.setline(62);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b"), PyString.fromInterned("c")});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(63);
      PyObject var5 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(2, var5);
      var3 = null;
      var1.setline(64);
      var5 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("foo"));
      var1.setlocal(3, var5);
      var3 = null;
      var1.setline(65);
      var1.getglobal("write_file").__call__(var2, var1.getlocal(3), var1.getlocal(1));
      var1.setline(66);
      PyList var10000 = new PyList();
      var5 = var10000.__getattr__("append");
      var1.setlocal(5, var5);
      var3 = null;
      var1.setline(66);
      var5 = var1.getglobal("open").__call__(var2, var1.getlocal(3)).__getattr__("readlines").__call__(var2).__iter__();

      while(true) {
         var1.setline(66);
         PyObject var4 = var5.__iternext__();
         if (var4 == null) {
            var1.setline(66);
            var1.dellocal(5);
            var3 = var10000;
            var1.setlocal(4, var3);
            var3 = null;
            var1.setline(67);
            var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(4), var1.getlocal(1));
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(6, var4);
         var1.setline(66);
         var1.getlocal(5).__call__(var2, var1.getlocal(6).__getattr__("strip").__call__(var2));
      }
   }

   public PyObject test_copy_file$7(PyFrame var1, ThreadState var2) {
      var1.setline(70);
      PyObject var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(71);
      var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo"));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(72);
      var1.getglobal("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(2), (PyObject)PyString.fromInterned("content"));
      var1.setline(73);
      var3 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(74);
      var1.getglobal("copy_file").__call__(var2, var1.getlocal(2), var1.getlocal(3));
      var1.setline(75);
      var1.getlocal(0).__getattr__("assertTrue").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("foo"))));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$8(PyFrame var1, ThreadState var2) {
      var1.setline(78);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("FileUtilTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_file_util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      FileUtilTestCase$1 = Py.newCode(0, var2, var1, "FileUtilTestCase", 11, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "msg", "args"};
      _log$2 = Py.newCode(3, var2, var1, "_log", 13, true, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmp_dir"};
      setUp$3 = Py.newCode(1, var2, var1, "setUp", 19, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$4 = Py.newCode(1, var2, var1, "tearDown", 29, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "f", "wanted"};
      test_move_file_verbosity$5 = Py.newCode(1, var2, var1, "test_move_file_verbosity", 33, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "lines", "dir", "foo", "content", "_[66_19]", "line"};
      test_write_file$6 = Py.newCode(1, var2, var1, "test_write_file", 61, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "src_dir", "foo", "dst_dir"};
      test_copy_file$7 = Py.newCode(1, var2, var1, "test_copy_file", 69, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$8 = Py.newCode(0, var2, var1, "test_suite", 77, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_file_util$py("distutils/tests/test_file_util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_file_util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.FileUtilTestCase$1(var2, var3);
         case 2:
            return this._log$2(var2, var3);
         case 3:
            return this.setUp$3(var2, var3);
         case 4:
            return this.tearDown$4(var2, var3);
         case 5:
            return this.test_move_file_verbosity$5(var2, var3);
         case 6:
            return this.test_write_file$6(var2, var3);
         case 7:
            return this.test_copy_file$7(var2, var3);
         case 8:
            return this.test_suite$8(var2, var3);
         default:
            return null;
      }
   }
}
