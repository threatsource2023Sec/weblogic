package distutils.tests;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
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
@Filename("distutils/tests/test_build_clib.py")
public class test_build_clib$py extends PyFunctionTable implements PyRunnable {
   static test_build_clib$py self;
   static final PyCode f$0;
   static final PyCode BuildCLibTestCase$1;
   static final PyCode test_check_library_dist$2;
   static final PyCode test_get_source_files$3;
   static final PyCode test_build_libraries$4;
   static final PyCode FakeCompiler$5;
   static final PyCode compile$6;
   static final PyCode test_finalize_options$7;
   static final PyCode test_run$8;
   static final PyCode test_suite$9;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.command.build_clib."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.command.build_clib.");
      var1.setline(2);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(3);
      var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var1.setline(4);
      var3 = imp.importOne("sys", var1, -1);
      var1.setlocal("sys", var3);
      var3 = null;
      var1.setline(6);
      String[] var5 = new String[]{"run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(8);
      var5 = new String[]{"build_clib"};
      var6 = imp.importFrom("distutils.command.build_clib", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("build_clib", var4);
      var4 = null;
      var1.setline(9);
      var5 = new String[]{"DistutilsSetupError"};
      var6 = imp.importFrom("distutils.errors", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("DistutilsSetupError", var4);
      var4 = null;
      var1.setline(10);
      var5 = new String[]{"support"};
      var6 = imp.importFrom("distutils.tests", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("support", var4);
      var4 = null;
      var1.setline(11);
      var5 = new String[]{"find_executable"};
      var6 = imp.importFrom("distutils.spawn", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("find_executable", var4);
      var4 = null;
      var1.setline(13);
      var6 = new PyObject[]{var1.getname("support").__getattr__("TempdirManager"), var1.getname("support").__getattr__("LoggingSilencer"), var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("BuildCLibTestCase", var6, BuildCLibTestCase$1);
      var1.setlocal("BuildCLibTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(142);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$9, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(145);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(146);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject BuildCLibTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(17);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_check_library_dist$2, (PyObject)null);
      var1.setlocal("test_check_library_dist", var4);
      var3 = null;
      var1.setline(47);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_get_source_files$3, (PyObject)null);
      var1.setlocal("test_get_source_files", var4);
      var3 = null;
      var1.setline(69);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_build_libraries$4, (PyObject)null);
      var1.setlocal("test_build_libraries", var4);
      var3 = null;
      var1.setline(90);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_finalize_options$7, (PyObject)null);
      var1.setlocal("test_finalize_options", var4);
      var3 = null;
      var1.setline(105);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_run$8, (PyObject)null);
      var1.setlocal("test_run", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_check_library_dist$2(PyFrame var1, ThreadState var2) {
      var1.setline(18);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(19);
      var3 = var1.getglobal("build_clib").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(22);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsSetupError"), (PyObject)var1.getlocal(3).__getattr__("check_library_list"), (PyObject)PyString.fromInterned("foo"));
      var1.setline(25);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsSetupError"), (PyObject)var1.getlocal(3).__getattr__("check_library_list"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("foo1"), PyString.fromInterned("foo2")})));
      var1.setline(30);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsSetupError"), (PyObject)var1.getlocal(3).__getattr__("check_library_list"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{Py.newInteger(1), PyString.fromInterned("foo1")}), new PyTuple(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("foo2")})})));
      var1.setline(34);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsSetupError"), (PyObject)var1.getlocal(3).__getattr__("check_library_list"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("name"), PyString.fromInterned("foo1")}), new PyTuple(new PyObject[]{PyString.fromInterned("another/name"), PyString.fromInterned("foo2")})})));
      var1.setline(39);
      var1.getlocal(0).__getattr__("assertRaises").__call__((ThreadState)var2, var1.getglobal("DistutilsSetupError"), (PyObject)var1.getlocal(3).__getattr__("check_library_list"), (PyObject)(new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("name"), new PyDictionary(Py.EmptyObjects)}), new PyTuple(new PyObject[]{PyString.fromInterned("another"), PyString.fromInterned("foo2")})})));
      var1.setline(44);
      PyList var6 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("name"), new PyDictionary(Py.EmptyObjects)}), new PyTuple(new PyObject[]{PyString.fromInterned("name"), new PyDictionary(new PyObject[]{PyString.fromInterned("ok"), PyString.fromInterned("good")})})});
      var1.setlocal(4, var6);
      var3 = null;
      var1.setline(45);
      var1.getlocal(3).__getattr__("check_library_list").__call__(var2, var1.getlocal(4));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_get_source_files$3(PyFrame var1, ThreadState var2) {
      var1.setline(48);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(49);
      var3 = var1.getglobal("build_clib").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(53);
      PyList var6 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("name"), new PyDictionary(Py.EmptyObjects)})});
      var1.getlocal(3).__setattr__((String)"libraries", var6);
      var3 = null;
      var1.setline(54);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsSetupError"), var1.getlocal(3).__getattr__("get_source_files"));
      var1.setline(56);
      var6 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("name"), new PyDictionary(new PyObject[]{PyString.fromInterned("sources"), Py.newInteger(1)})})});
      var1.getlocal(3).__setattr__((String)"libraries", var6);
      var3 = null;
      var1.setline(57);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsSetupError"), var1.getlocal(3).__getattr__("get_source_files"));
      var1.setline(59);
      var6 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("name"), new PyDictionary(new PyObject[]{PyString.fromInterned("sources"), new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b")})})})});
      var1.getlocal(3).__setattr__((String)"libraries", var6);
      var3 = null;
      var1.setline(60);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_source_files").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b")})));
      var1.setline(62);
      var6 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("name"), new PyDictionary(new PyObject[]{PyString.fromInterned("sources"), new PyTuple(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b")})})})});
      var1.getlocal(3).__setattr__((String)"libraries", var6);
      var3 = null;
      var1.setline(63);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_source_files").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b")})));
      var1.setline(65);
      var6 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("name"), new PyDictionary(new PyObject[]{PyString.fromInterned("sources"), new PyTuple(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b")})})}), new PyTuple(new PyObject[]{PyString.fromInterned("name2"), new PyDictionary(new PyObject[]{PyString.fromInterned("sources"), new PyList(new PyObject[]{PyString.fromInterned("c"), PyString.fromInterned("d")})})})});
      var1.getlocal(3).__setattr__((String)"libraries", var6);
      var3 = null;
      var1.setline(67);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("get_source_files").__call__(var2), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("a"), PyString.fromInterned("b"), PyString.fromInterned("c"), PyString.fromInterned("d")})));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_build_libraries$4(PyFrame var1, ThreadState var2) {
      var1.setline(71);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(72);
      var3 = var1.getglobal("build_clib").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(73);
      PyObject[] var6 = Py.EmptyObjects;
      PyObject var7 = Py.makeClass("FakeCompiler", var6, FakeCompiler$5);
      var1.setlocal(4, var7);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(78);
      var3 = var1.getlocal(4).__call__(var2);
      var1.getlocal(3).__setattr__("compiler", var3);
      var3 = null;
      var1.setline(81);
      PyList var8 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("name"), new PyDictionary(new PyObject[]{PyString.fromInterned("sources"), PyString.fromInterned("notvalid")})})});
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(82);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsSetupError"), var1.getlocal(3).__getattr__("build_libraries"), var1.getlocal(5));
      var1.setline(84);
      var8 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("name"), new PyDictionary(new PyObject[]{PyString.fromInterned("sources"), var1.getglobal("list").__call__(var2)})})});
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(85);
      var1.getlocal(3).__getattr__("build_libraries").__call__(var2, var1.getlocal(5));
      var1.setline(87);
      var8 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("name"), new PyDictionary(new PyObject[]{PyString.fromInterned("sources"), var1.getglobal("tuple").__call__(var2)})})});
      var1.setlocal(5, var8);
      var3 = null;
      var1.setline(88);
      var1.getlocal(3).__getattr__("build_libraries").__call__(var2, var1.getlocal(5));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject FakeCompiler$5(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(74);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, compile$6, (PyObject)null);
      var1.setlocal("compile", var4);
      var3 = null;
      var1.setline(76);
      PyObject var5 = var1.getname("compile");
      var1.setlocal("create_static_lib", var5);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject compile$6(PyFrame var1, ThreadState var2) {
      var1.setline(75);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_finalize_options$7(PyFrame var1, ThreadState var2) {
      var1.setline(91);
      PyObject var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(1, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(2, var5);
      var5 = null;
      var3 = null;
      var1.setline(92);
      var3 = var1.getglobal("build_clib").__call__(var2, var1.getlocal(2));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(94);
      PyString var6 = PyString.fromInterned("one-dir");
      var1.getlocal(3).__setattr__((String)"include_dirs", var6);
      var3 = null;
      var1.setline(95);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(96);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("include_dirs"), (PyObject)(new PyList(new PyObject[]{PyString.fromInterned("one-dir")})));
      var1.setline(98);
      var3 = var1.getglobal("None");
      var1.getlocal(3).__setattr__("include_dirs", var3);
      var3 = null;
      var1.setline(99);
      var1.getlocal(3).__getattr__("finalize_options").__call__(var2);
      var1.setline(100);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(3).__getattr__("include_dirs"), (PyObject)(new PyList(Py.EmptyObjects)));
      var1.setline(102);
      var6 = PyString.fromInterned("WONTWORK");
      var1.getlocal(3).__getattr__("distribution").__setattr__((String)"libraries", var6);
      var3 = null;
      var1.setline(103);
      var1.getlocal(0).__getattr__("assertRaises").__call__(var2, var1.getglobal("DistutilsSetupError"), var1.getlocal(3).__getattr__("finalize_options"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_run$8(PyFrame var1, ThreadState var2) {
      var1.setline(107);
      PyObject var3 = var1.getglobal("sys").__getattr__("platform");
      PyObject var10000 = var3._eq(PyString.fromInterned("win32"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(108);
         var1.f_lasti = -1;
         return Py.None;
      } else {
         var1.setline(110);
         var3 = var1.getlocal(0).__getattr__("create_dist").__call__(var2);
         PyObject[] var4 = Py.unpackSequence(var3, 2);
         PyObject var5 = var4[0];
         var1.setlocal(1, var5);
         var5 = null;
         var5 = var4[1];
         var1.setlocal(2, var5);
         var5 = null;
         var3 = null;
         var1.setline(111);
         var3 = var1.getglobal("build_clib").__call__(var2, var1.getlocal(2));
         var1.setlocal(3, var3);
         var3 = null;
         var1.setline(113);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("foo.c"));
         var1.setlocal(4, var3);
         var3 = null;
         var1.setline(114);
         var1.getlocal(0).__getattr__("write_file").__call__((ThreadState)var2, (PyObject)var1.getlocal(4), (PyObject)PyString.fromInterned("int main(void) { return 1;}\n"));
         var1.setline(115);
         PyList var7 = new PyList(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("foo"), new PyDictionary(new PyObject[]{PyString.fromInterned("sources"), new PyList(new PyObject[]{var1.getlocal(4)})})})});
         var1.getlocal(3).__setattr__((String)"libraries", var7);
         var3 = null;
         var1.setline(117);
         var3 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("build"));
         var1.setlocal(5, var3);
         var3 = null;
         var1.setline(118);
         var1.getglobal("os").__getattr__("mkdir").__call__(var2, var1.getlocal(5));
         var1.setline(119);
         var3 = var1.getlocal(5);
         var1.getlocal(3).__setattr__("build_temp", var3);
         var3 = null;
         var1.setline(120);
         var3 = var1.getlocal(5);
         var1.getlocal(3).__setattr__("build_clib", var3);
         var3 = null;
         var1.setline(125);
         String[] var8 = new String[]{"new_compiler"};
         PyObject[] var9 = imp.importFrom("distutils.ccompiler", var8, var1, -1);
         PyObject var6 = var9[0];
         var1.setlocal(6, var6);
         var4 = null;
         var1.setline(126);
         var8 = new String[]{"customize_compiler"};
         var9 = imp.importFrom("distutils.sysconfig", var8, var1, -1);
         var6 = var9[0];
         var1.setlocal(7, var6);
         var4 = null;
         var1.setline(128);
         var3 = var1.getlocal(6).__call__(var2);
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(129);
         var1.getlocal(7).__call__(var2, var1.getlocal(8));
         var1.setline(130);
         var3 = var1.getlocal(8).__getattr__("executables").__getattr__("values").__call__(var2).__iter__();

         while(true) {
            var1.setline(130);
            var6 = var3.__iternext__();
            if (var6 == null) {
               var1.setline(137);
               var1.getlocal(3).__getattr__("run").__call__(var2);
               var1.setline(140);
               var10000 = var1.getlocal(0).__getattr__("assertTrue");
               PyString var10 = PyString.fromInterned("libfoo.a");
               PyObject var10002 = var10._in(var1.getglobal("os").__getattr__("listdir").__call__(var2, var1.getlocal(5)));
               var3 = null;
               var10000.__call__(var2, var10002);
               var1.f_lasti = -1;
               return Py.None;
            }

            var1.setlocal(9, var6);
            var1.setline(131);
            var5 = var1.getlocal(9);
            var10000 = var5._is(var1.getglobal("None"));
            var5 = null;
            if (!var10000.__nonzero__()) {
               var1.setline(133);
               var5 = var1.getglobal("find_executable").__call__(var2, var1.getlocal(9).__getitem__(Py.newInteger(0)));
               var10000 = var5._is(var1.getglobal("None"));
               var5 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(134);
                  var1.f_lasti = -1;
                  return Py.None;
               }
            }
         }
      }
   }

   public PyObject test_suite$9(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("BuildCLibTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_build_clib$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      BuildCLibTestCase$1 = Py.newCode(0, var2, var1, "BuildCLibTestCase", 13, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd", "libs"};
      test_check_library_dist$2 = Py.newCode(1, var2, var1, "test_check_library_dist", 17, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd"};
      test_get_source_files$3 = Py.newCode(1, var2, var1, "test_get_source_files", 47, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd", "FakeCompiler", "lib"};
      test_build_libraries$4 = Py.newCode(1, var2, var1, "test_build_libraries", 69, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      FakeCompiler$5 = Py.newCode(0, var2, var1, "FakeCompiler", 73, false, false, self, 5, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"args", "kw"};
      compile$6 = Py.newCode(2, var2, var1, "compile", 74, true, true, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd"};
      test_finalize_options$7 = Py.newCode(1, var2, var1, "test_finalize_options", 90, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "pkg_dir", "dist", "cmd", "foo_c", "build_temp", "new_compiler", "customize_compiler", "compiler", "ccmd"};
      test_run$8 = Py.newCode(1, var2, var1, "test_run", 105, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$9 = Py.newCode(0, var2, var1, "test_suite", 142, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_build_clib$py("distutils/tests/test_build_clib$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_build_clib$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.BuildCLibTestCase$1(var2, var3);
         case 2:
            return this.test_check_library_dist$2(var2, var3);
         case 3:
            return this.test_get_source_files$3(var2, var3);
         case 4:
            return this.test_build_libraries$4(var2, var3);
         case 5:
            return this.FakeCompiler$5(var2, var3);
         case 6:
            return this.compile$6(var2, var3);
         case 7:
            return this.test_finalize_options$7(var2, var3);
         case 8:
            return this.test_run$8(var2, var3);
         case 9:
            return this.test_suite$9(var2, var3);
         default:
            return null;
      }
   }
}
