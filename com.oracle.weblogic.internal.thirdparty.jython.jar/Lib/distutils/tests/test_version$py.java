package distutils.tests;

import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyException;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_version.py")
public class test_version$py extends PyFunctionTable implements PyRunnable {
   static test_version$py self;
   static final PyCode f$0;
   static final PyCode VersionTestCase$1;
   static final PyCode test_prerelease$2;
   static final PyCode test_cmp_strict$3;
   static final PyCode test_cmp$4;
   static final PyCode test_suite$5;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests for distutils.version."));
      var1.setline(1);
      PyString.fromInterned("Tests for distutils.version.");
      var1.setline(2);
      PyObject var3 = imp.importOne("unittest", var1, -1);
      var1.setlocal("unittest", var3);
      var3 = null;
      var1.setline(3);
      String[] var5 = new String[]{"LooseVersion"};
      PyObject[] var6 = imp.importFrom("distutils.version", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("LooseVersion", var4);
      var4 = null;
      var1.setline(4);
      var5 = new String[]{"StrictVersion"};
      var6 = imp.importFrom("distutils.version", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("StrictVersion", var4);
      var4 = null;
      var1.setline(5);
      var5 = new String[]{"run_unittest"};
      var6 = imp.importFrom("test.test_support", var5, var1, -1);
      var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(7);
      var6 = new PyObject[]{var1.getname("unittest").__getattr__("TestCase")};
      var4 = Py.makeClass("VersionTestCase", var6, VersionTestCase$1);
      var1.setlocal("VersionTestCase", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(67);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$5, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(70);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(71);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject VersionTestCase$1(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(9);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, test_prerelease$2, (PyObject)null);
      var1.setlocal("test_prerelease", var4);
      var3 = null;
      var1.setline(18);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_cmp_strict$3, (PyObject)null);
      var1.setlocal("test_cmp_strict", var4);
      var3 = null;
      var1.setline(50);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, test_cmp$4, (PyObject)null);
      var1.setlocal("test_cmp", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject test_prerelease$2(PyFrame var1, ThreadState var2) {
      var1.setline(10);
      PyObject var3 = var1.getglobal("StrictVersion").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("1.2.3a1"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(11);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("version"), (PyObject)(new PyTuple(new PyObject[]{Py.newInteger(1), Py.newInteger(2), Py.newInteger(3)})));
      var1.setline(12);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getlocal(1).__getattr__("prerelease"), (PyObject)(new PyTuple(new PyObject[]{PyString.fromInterned("a"), Py.newInteger(1)})));
      var1.setline(13);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(1)), (PyObject)PyString.fromInterned("1.2.3a1"));
      var1.setline(15);
      var3 = var1.getglobal("StrictVersion").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("1.2.0"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(16);
      var1.getlocal(0).__getattr__("assertEqual").__call__((ThreadState)var2, (PyObject)var1.getglobal("str").__call__(var2, var1.getlocal(1)), (PyObject)PyString.fromInterned("1.2"));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_cmp_strict$3(PyFrame var1, ThreadState var2) {
      var1.setline(19);
      PyTuple var3 = new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("1.5.1"), PyString.fromInterned("1.5.2b2"), Py.newInteger(-1)}), new PyTuple(new PyObject[]{PyString.fromInterned("161"), PyString.fromInterned("3.10a"), var1.getglobal("ValueError")}), new PyTuple(new PyObject[]{PyString.fromInterned("8.02"), PyString.fromInterned("8.02"), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("3.4j"), PyString.fromInterned("1996.07.12"), var1.getglobal("ValueError")}), new PyTuple(new PyObject[]{PyString.fromInterned("3.2.pl0"), PyString.fromInterned("3.1.1.6"), var1.getglobal("ValueError")}), new PyTuple(new PyObject[]{PyString.fromInterned("2g6"), PyString.fromInterned("11g"), var1.getglobal("ValueError")}), new PyTuple(new PyObject[]{PyString.fromInterned("0.9"), PyString.fromInterned("2.2"), Py.newInteger(-1)}), new PyTuple(new PyObject[]{PyString.fromInterned("1.2.1"), PyString.fromInterned("1.2"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("1.1"), PyString.fromInterned("1.2.2"), Py.newInteger(-1)}), new PyTuple(new PyObject[]{PyString.fromInterned("1.2"), PyString.fromInterned("1.1"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("1.2.1"), PyString.fromInterned("1.2.2"), Py.newInteger(-1)}), new PyTuple(new PyObject[]{PyString.fromInterned("1.2.2"), PyString.fromInterned("1.2"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("1.2"), PyString.fromInterned("1.2.2"), Py.newInteger(-1)}), new PyTuple(new PyObject[]{PyString.fromInterned("0.4.0"), PyString.fromInterned("0.4"), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("1.13++"), PyString.fromInterned("5.5.kw"), var1.getglobal("ValueError")})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(35);
      PyObject var8 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(35);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(4, var6);
         var6 = null;

         try {
            var1.setline(37);
            PyObject var10 = var1.getglobal("StrictVersion").__call__(var2, var1.getlocal(2)).__getattr__("__cmp__").__call__(var2, var1.getglobal("StrictVersion").__call__(var2, var1.getlocal(3)));
            var1.setlocal(5, var10);
            var5 = null;
         } catch (Throwable var7) {
            PyException var9 = Py.setException(var7, var1);
            if (var9.match(var1.getglobal("ValueError"))) {
               var1.setline(39);
               var6 = var1.getlocal(4);
               PyObject var10000 = var6._is(var1.getglobal("ValueError"));
               var6 = null;
               if (!var10000.__nonzero__()) {
                  var1.setline(42);
                  throw Py.makeException(var1.getglobal("AssertionError").__call__(var2, PyString.fromInterned("cmp(%s, %s) shouldn't raise ValueError")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)}))));
               }
               continue;
            }

            throw var9;
         }

         var1.setline(45);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5), var1.getlocal(4), PyString.fromInterned("cmp(%s, %s) should be %s, got %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)})));
      }
   }

   public PyObject test_cmp$4(PyFrame var1, ThreadState var2) {
      var1.setline(51);
      PyTuple var3 = new PyTuple(new PyObject[]{new PyTuple(new PyObject[]{PyString.fromInterned("1.5.1"), PyString.fromInterned("1.5.2b2"), Py.newInteger(-1)}), new PyTuple(new PyObject[]{PyString.fromInterned("161"), PyString.fromInterned("3.10a"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("8.02"), PyString.fromInterned("8.02"), Py.newInteger(0)}), new PyTuple(new PyObject[]{PyString.fromInterned("3.4j"), PyString.fromInterned("1996.07.12"), Py.newInteger(-1)}), new PyTuple(new PyObject[]{PyString.fromInterned("3.2.pl0"), PyString.fromInterned("3.1.1.6"), Py.newInteger(1)}), new PyTuple(new PyObject[]{PyString.fromInterned("2g6"), PyString.fromInterned("11g"), Py.newInteger(-1)}), new PyTuple(new PyObject[]{PyString.fromInterned("0.960923"), PyString.fromInterned("2.2beta29"), Py.newInteger(-1)}), new PyTuple(new PyObject[]{PyString.fromInterned("1.13++"), PyString.fromInterned("5.5.kw"), Py.newInteger(-1)})});
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(61);
      PyObject var7 = var1.getlocal(1).__iter__();

      while(true) {
         var1.setline(61);
         PyObject var4 = var7.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         PyObject[] var5 = Py.unpackSequence(var4, 3);
         PyObject var6 = var5[0];
         var1.setlocal(2, var6);
         var6 = null;
         var6 = var5[1];
         var1.setlocal(3, var6);
         var6 = null;
         var6 = var5[2];
         var1.setlocal(4, var6);
         var6 = null;
         var1.setline(62);
         PyObject var8 = var1.getglobal("LooseVersion").__call__(var2, var1.getlocal(2)).__getattr__("__cmp__").__call__(var2, var1.getglobal("LooseVersion").__call__(var2, var1.getlocal(3)));
         var1.setlocal(5, var8);
         var5 = null;
         var1.setline(63);
         var1.getlocal(0).__getattr__("assertEqual").__call__(var2, var1.getlocal(5), var1.getlocal(4), PyString.fromInterned("cmp(%s, %s) should be %s, got %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5)})));
      }
   }

   public PyObject test_suite$5(PyFrame var1, ThreadState var2) {
      var1.setline(68);
      PyObject var3 = var1.getglobal("unittest").__getattr__("makeSuite").__call__(var2, var1.getglobal("VersionTestCase"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_version$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      VersionTestCase$1 = Py.newCode(0, var2, var1, "VersionTestCase", 7, false, false, self, 1, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "version"};
      test_prerelease$2 = Py.newCode(1, var2, var1, "test_prerelease", 9, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "versions", "v1", "v2", "wanted", "res"};
      test_cmp_strict$3 = Py.newCode(1, var2, var1, "test_cmp_strict", 18, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "versions", "v1", "v2", "wanted", "res"};
      test_cmp$4 = Py.newCode(1, var2, var1, "test_cmp", 50, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      test_suite$5 = Py.newCode(0, var2, var1, "test_suite", 67, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_version$py("distutils/tests/test_version$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_version$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.VersionTestCase$1(var2, var3);
         case 2:
            return this.test_prerelease$2(var2, var3);
         case 3:
            return this.test_cmp_strict$3(var2, var3);
         case 4:
            return this.test_cmp$4(var2, var3);
         case 5:
            return this.test_suite$5(var2, var3);
         default:
            return null;
      }
   }
}
