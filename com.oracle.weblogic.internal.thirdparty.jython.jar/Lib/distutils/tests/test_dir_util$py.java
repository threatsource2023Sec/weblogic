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
@Filename("distutils/tests/test_dir_util.py")
public class test_dir_util$py extends PyFunctionTable implements PyRunnable {
   static test_dir_util$py self;
   static final PyCode f$0;
   static final PyCode DirUtilTestCase$1;
   static final PyCode _log$2;
   static final PyCode setUp$3;
   static final PyCode tearDown$4;
   static final PyCode test_mkpath_remove_tree_verbosity$5;
   static final PyCode test_mkpath_with_custom_mode$6;
   static final PyCode test_create_tree_verbosity$7;
   static final PyCode test_copy_tree_verbosity$8;
   static final PyCode test_copy_tree_skips_nfs_temp_files$9;
   static final PyCode test_ensure_relative$10;
   static final PyCode test_suite$11;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.dir_util."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.dir_util.");
      var1.setline(2);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("stat", var1, -1);
      var1.setlocal("stat", var3);
      var3 = null;
      var1.setline(5);
      var3 = imp.importOne("shutil", var1, -1);
      var1.setlocal("shutil", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(8);
      String[] var5 = new String[]{"mkpath", "remove_tree", "create_tree", "copy_tree", "ensure_relative"};
      PyObject[] var6 = imp.importFrom("distutils.dir_util", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("mkpath", var4);
      var4 = null;
      var4 = var6[1];
      var1.setlocal("remove_tree", var4);
      var4 = null;
      var4 = var6[2];
      var1.setlocal("create_tree", var4);
      var4 = null;
      var4 = var6[3];
      var1.setlocal("copy_tree", var4);
      var4 = null;
      var4 = var6[4];
      var1.setlocal("ensure_relative", var4);
      var4 = null;
      var1.setline(11);
      var5 = new String[]{"log"};
      var6 = imp.importFrom("distutils", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("log", var4);
      var4 = null;
      var1.setline(12);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(13);
      var5 = new String[]{"run_unittest"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(15);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("DirUtilTestCase", var6, DirUtilTestCase$1);
      var1.setlocal("DirUtilTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(130);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$11, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(133);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(134);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject DirUtilTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, _log$2, (PyObject)null);
      var1.setlocal("_log", var4);
      var3 = null;
      var1.setline(23);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, setUp$3, (PyObject)null);
      var1.setlocal("setUp", var4);
      var3 = null;
      var1.setline(33);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tearDown$4, (PyObject)null);
      var1.setlocal("tearDown", var4);
      var3 = null;
      var1.setline(37);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_mkpath_remove_tree_verbosity$5, (PyObject)null);
      var1.setlocal("test_mkpath_remove_tree_verbosity", var4);
      var3 = null;
      var1.setline(54);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_mkpath_with_custom_mode$6, (PyObject)null);
      PyObject var5 = var1.getname("unittest").__getattr__("skipIf").__call__((ThreadState)var2, (PyObject)var1.getname("sys").__getattr__("platform").__getattr__("startswith").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("win")), (PyObject)PyString.fromInterned("This test is only appropriate for POSIX-like systems.")).__call__((ThreadState)var2, (PyObject)var4);
      var1.setlocal("test_mkpath_with_custom_mode", var5);
      var3 = null;
      var1.setline(67);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_create_tree_verbosity$7, (PyObject)null);
      var1.setlocal("test_create_tree_verbosity", var4);
      var3 = null;
      var1.setline(80);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_copy_tree_verbosity$8, (PyObject)null);
      var1.setlocal("test_copy_tree_verbosity", var4);
      var3 = null;
      var1.setline(104);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_copy_tree_skips_nfs_temp_files$9, (PyObject)null);
      var1.setlocal("test_copy_tree_skips_nfs_temp_files", var4);
      var3 = null;
      var1.setline(122);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_ensure_relative$10, (PyObject)null);
      var1.setlocal("test_ensure_relative", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject _log$2(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(19);
         var1.getlocal(0).__getattr__("_logs").__getattr__("append").__call__(var2, var1.getlocal(1)._mod(var1.getlocal(2)));
      } else {
         var1.setline(21);
         var1.getlocal(0).__getattr__("_logs").__getattr__("append").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject setUp$3(PyFrame var1, ThreadState var2) {
      var1.setline(24);
      var1.getglobal("super").__call__(var2, var1.getglobal("DirUtilTestCase"), var1.getlocal(0)).__getattr__("setUp").__call__(var2);
      var1.setline(25);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_logs", var3);
      var3 = null;
      var1.setline(26);
      PyObject var4 = var1.getlocal(0).__getattr__("mkdtemp").__call__(var2);
      var1.setlocal(1, var4);
      var3 = null;
      var1.setline(27);
      var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("deep"));
      var1.getlocal(0).__setattr__("root_target", var4);
      var3 = null;
      var1.setline(28);
      var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("root_target"), (PyObject)PyString.fromInterned("here"));
      var1.getlocal(0).__setattr__("target", var4);
      var3 = null;
      var1.setline(29);
      var4 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("deep2"));
      var1.getlocal(0).__setattr__("target2", var4);
      var3 = null;
      var1.setline(30);
      var4 = var1.getglobal("log").__getattr__("info");
      var1.getlocal(0).__setattr__("old_log", var4);
      var3 = null;
      var1.setline(31);
      var4 = var1.getlocal(0).__getattr__("_log");
      var1.getglobal("log").__setattr__("info", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tearDown$4(PyFrame var1, ThreadState var2) {
      var1.setline(34);
      PyObject var3 = var1.getlocal(0).__getattr__("old_log");
      var1.getglobal("log").__setattr__("info", var3);
      var3 = null;
      var1.setline(35);
      var1.getglobal("super").__call__(var2, var1.getglobal("DirUtilTestCase"), var1.getlocal(0)).__getattr__("tearDown").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_mkpath_remove_tree_verbosity$5(PyFrame var1, ThreadState var2) {
      var1.setline(39);
      PyObject var10000 = var1.getglobal("mkpath");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("target"), Py.newInteger(0)};
      String[] var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(40);
      PyList var5 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(41);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("_logs"), var1.getlocal(1));
      var1.setline(42);
      var10000 = var1.getglobal("remove_tree");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("root_target"), Py.newInteger(0)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(44);
      var10000 = var1.getglobal("mkpath");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("target"), Py.newInteger(1)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(45);
      var5 = new PyList(new PyObject[]{PyString.fromInterned("creating %s")._mod(var1.getlocal(0).__getattr__("root_target")), PyString.fromInterned("creating %s")._mod(var1.getlocal(0).__getattr__("target"))});
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(47);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("_logs"), var1.getlocal(1));
      var1.setline(48);
      var5 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"_logs", var5);
      var3 = null;
      var1.setline(50);
      var10000 = var1.getglobal("remove_tree");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("root_target"), Py.newInteger(1)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(51);
      var5 = new PyList(new PyObject[]{PyString.fromInterned("removing '%s' (and everything under it)")._mod(var1.getlocal(0).__getattr__("root_target"))});
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(52);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("_logs"), var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_mkpath_with_custom_mode$6(PyFrame var1, ThreadState var2) {
      var1.setline(58);
      PyObject var3 = var1.getglobal("os").__getattr__("umask").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(59);
      var1.getglobal("os").__getattr__("umask").__call__(var2, var1.getlocal(1));
      var1.setline(60);
      var1.getglobal("mkpath").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("target"), (PyObject)Py.newInteger(448));
      var1.setline(61);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("stat").__getattr__("S_IMODE").__call__(var2, var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0).__getattr__("target")).__getattr__("st_mode")), Py.newInteger(448)._and(var1.getlocal(1).__invert__()));
      var1.setline(63);
      var1.getglobal("mkpath").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("target2"), (PyObject)Py.newInteger(365));
      var1.setline(64);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getglobal("stat").__getattr__("S_IMODE").__call__(var2, var1.getglobal("os").__getattr__("stat").__call__(var2, var1.getlocal(0).__getattr__("target2")).__getattr__("st_mode")), Py.newInteger(365)._and(var1.getlocal(1).__invert__()));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_create_tree_verbosity$7(PyFrame var1, ThreadState var2) {
      var1.setline(69);
      PyObject var10000 = var1.getglobal("create_tree");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("root_target"), new PyList(new PyObject[]{PyString.fromInterned("one"), PyString.fromInterned("two"), PyString.fromInterned("three")}), Py.newInteger(0)};
      String[] var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(70);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_logs"), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(71);
      var10000 = var1.getglobal("remove_tree");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("root_target"), Py.newInteger(0)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(73);
      PyList var5 = new PyList(new PyObject[]{PyString.fromInterned("creating %s")._mod(var1.getlocal(0).__getattr__("root_target"))});
      var1.setlocal(1, var5);
      var3 = null;
      var1.setline(74);
      var10000 = var1.getglobal("create_tree");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("root_target"), new PyList(new PyObject[]{PyString.fromInterned("one"), PyString.fromInterned("two"), PyString.fromInterned("three")}), Py.newInteger(1)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(75);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("_logs"), var1.getlocal(1));
      var1.setline(77);
      var10000 = var1.getglobal("remove_tree");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("root_target"), Py.newInteger(0)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_copy_tree_verbosity$8(PyFrame var1, ThreadState var2) {
      var1.setline(82);
      PyObject var10000 = var1.getglobal("mkpath");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("target"), Py.newInteger(0)};
      String[] var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(84);
      var10000 = var1.getglobal("copy_tree");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("target"), var1.getlocal(0).__getattr__("target2"), Py.newInteger(0)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(85);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("_logs"), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(87);
      var10000 = var1.getglobal("remove_tree");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("root_target"), Py.newInteger(0)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(89);
      var10000 = var1.getglobal("mkpath");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("target"), Py.newInteger(0)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(90);
      PyObject var6 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("target"), (PyObject)PyString.fromInterned("ok.txt"));
      var1.setlocal(1, var6);
      var3 = null;
      var1.setline(91);
      var6 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("w"));
      var1.setlocal(2, var6);
      var3 = null;
      var3 = null;

      try {
         var1.setline(93);
         var1.getlocal(2).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("some content"));
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(95);
         var1.getlocal(2).__getattr__("close").__call__(var2);
         throw (Throwable)var5;
      }

      var1.setline(95);
      var1.getlocal(2).__getattr__("close").__call__(var2);
      var1.setline(97);
      PyList var7 = new PyList(new PyObject[]{PyString.fromInterned("copying %s -> %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("target2")}))});
      var1.setlocal(3, var7);
      var3 = null;
      var1.setline(98);
      var10000 = var1.getglobal("copy_tree");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("target"), var1.getlocal(0).__getattr__("target2"), Py.newInteger(1)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(99);
      var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(0).__getattr__("_logs"), var1.getlocal(3));
      var1.setline(101);
      var10000 = var1.getglobal("remove_tree");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("root_target"), Py.newInteger(0)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(102);
      var10000 = var1.getglobal("remove_tree");
      var3 = new PyObject[]{var1.getlocal(0).__getattr__("target2"), Py.newInteger(0)};
      var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_copy_tree_skips_nfs_temp_files$9(PyFrame var1, ThreadState var2) {
      var1.setline(105);
      PyObject var10000 = var1.getglobal("mkpath");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("target"), Py.newInteger(0)};
      String[] var4 = new String[]{"verbose"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(107);
      PyObject var7 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("target"), (PyObject)PyString.fromInterned("ok.txt"));
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(108);
      var7 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("target"), (PyObject)PyString.fromInterned(".nfs123abc"));
      var1.setlocal(2, var7);
      var3 = null;
      var1.setline(109);
      var7 = (new PyTuple(new PyObject[]{var1.getlocal(1), var1.getlocal(2)})).__iter__();

      while(true) {
         var1.setline(109);
         PyObject var8 = var7.__iternext__();
         if (var8 == null) {
            var1.setline(116);
            var1.getglobal("copy_tree").__call__(var2, var1.getlocal(0).__getattr__("target"), var1.getlocal(0).__getattr__("target2"));
            var1.setline(117);
            var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(0).__getattr__("target2")), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("ok.txt")})));
            var1.setline(119);
            var10000 = var1.getglobal("remove_tree");
            var3 = new PyObject[]{var1.getlocal(0).__getattr__("root_target"), Py.newInteger(0)};
            var4 = new String[]{"verbose"};
            var10000.__call__(var2, var3, var4);
            var3 = null;
            var1.setline(120);
            var10000 = var1.getglobal("remove_tree");
            var3 = new PyObject[]{var1.getlocal(0).__getattr__("target2"), Py.newInteger(0)};
            var4 = new String[]{"verbose"};
            var10000.__call__(var2, var3, var4);
            var3 = null;
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(3, var8);
         var1.setline(110);
         PyObject var5 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(3), (PyObject)PyString.fromInterned("w"));
         var1.setlocal(4, var5);
         var5 = null;
         var5 = null;

         try {
            var1.setline(112);
            var1.getlocal(4).__getattr__("write").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("some content"));
         } catch (Throwable var6) {
            Py.addTraceback(var6, var1);
            var1.setline(114);
            var1.getlocal(4).__getattr__("close").__call__(var2);
            throw (Throwable)var6;
         }

         var1.setline(114);
         var1.getlocal(4).__getattr__("close").__call__(var2);
      }
   }

   public PyObject test_ensure_relative$10(PyFrame var1, ThreadState var2) {
      var1.setline(123);
      PyObject var3 = var1.getglobal("os").__getattr__("sep");
      PyObject var10000 = var3._eq(PyString.fromInterned("/"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(124);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("ensure_relative").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("/home/foo")), (PyObject)PyString.fromInterned("home/foo"));
         var1.setline(125);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("ensure_relative").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("some/path")), (PyObject)PyString.fromInterned("some/path"));
      } else {
         var1.setline(127);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("ensure_relative").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("c:\\home\\foo")), (PyObject)PyString.fromInterned("c:home\\foo"));
         var1.setline(128);
         var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("ensure_relative").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("home\\foo")), (PyObject)PyString.fromInterned("home\\foo"));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$11(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("DirUtilTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_dir_util$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      DirUtilTestCase$1 = Py.newCode(0, var2, var1, "DirUtilTestCase", 15, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "msg", "args"};
      _log$2 = Py.newCode(3, var2, var1, "_log", 17, true, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "tmp_dir"};
      setUp$3 = Py.newCode(1, var2, var1, "setUp", 23, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      tearDown$4 = Py.newCode(1, var2, var1, "tearDown", 33, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "wanted"};
      test_mkpath_remove_tree_verbosity$5 = Py.newCode(1, var2, var1, "test_mkpath_remove_tree_verbosity", 37, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "umask"};
      test_mkpath_with_custom_mode$6 = Py.newCode(1, var2, var1, "test_mkpath_with_custom_mode", 54, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "wanted"};
      test_create_tree_verbosity$7 = Py.newCode(1, var2, var1, "test_create_tree_verbosity", 67, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a_file", "f", "wanted"};
      test_copy_tree_verbosity$8 = Py.newCode(1, var2, var1, "test_copy_tree_verbosity", 80, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "a_file", "nfs_file", "f", "fh"};
      test_copy_tree_skips_nfs_temp_files$9 = Py.newCode(1, var2, var1, "test_copy_tree_skips_nfs_temp_files", 104, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      test_ensure_relative$10 = Py.newCode(1, var2, var1, "test_ensure_relative", 122, false, false, self, 10, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$11 = Py.newCode(0, var2, var1, "test_suite", 130, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_dir_util$py("distutils/tests/test_dir_util$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_dir_util$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.DirUtilTestCase$1(var2, var3);
         case 2:
            return this._log$2(var2, var3);
         case 3:
            return this.setUp$3(var2, var3);
         case 4:
            return this.tearDown$4(var2, var3);
         case 5:
            return this.test_mkpath_remove_tree_verbosity$5(var2, var3);
         case 6:
            return this.test_mkpath_with_custom_mode$6(var2, var3);
         case 7:
            return this.test_create_tree_verbosity$7(var2, var3);
         case 8:
            return this.test_copy_tree_verbosity$8(var2, var3);
         case 9:
            return this.test_copy_tree_skips_nfs_temp_files$9(var2, var3);
         case 10:
            return this.test_ensure_relative$10(var2, var3);
         case 11:
            return this.test_suite$11(var2, var3);
         default:
            return null;
      }
   }
}
