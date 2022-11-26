package distutils.tests;

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
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("distutils/tests/test_versionpredicate.py")
public class test_versionpredicate$py extends PyFunctionTable implements PyRunnable {
   static test_versionpredicate$py self;
   static final PyCode f$0;
   static final PyCode test_suite$1;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("Tests harness for distutils.versionpredicate.\n\n"));
      var1.setline(3);
      PyString.fromInterned("Tests harness for distutils.versionpredicate.\n\n");
      var1.setline(5);
      PyObject var3 = imp.importOne("distutils.versionpredicate", var1, -1);
      var1.setlocal("distutils", var3);
      var3 = null;
      var1.setline(6);
      var3 = imp.importOne("doctest", var1, -1);
      var1.setlocal("doctest", var3);
      var3 = null;
      var1.setline(7);
      String[] var5 = new String[]{"run_unittest"};
      PyObject[] var6 = imp.importFrom("test.test_support", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("run_unittest", var4);
      var4 = null;
      var1.setline(9);
      var6 = Py.EmptyObjects;
      PyFunction var7 = new PyFunction(var1.f_globals, var6, test_suite$1, (PyObject)null);
      var1.setlocal("test_suite", var7);
      var3 = null;
      var1.setline(12);
      var3 = var1.getname("__name__");
      PyObject var10000 = var3._eq(PyString.fromInterned("__main__"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(13);
         var1.getname("run_unittest").__call__(var2, var1.getname("test_suite").__call__(var2));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject test_suite$1(PyFrame var1, ThreadState var2) {
      var1.setline(10);
      PyObject var3 = var1.getglobal("doctest").__getattr__("DocTestSuite").__call__(var2, var1.getglobal("distutils").__getattr__("versionpredicate"));
      var1.f_lasti = -1;
      return var3;
   }

   public test_versionpredicate$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[0];
      test_suite$1 = Py.newCode(0, var2, var1, "test_suite", 9, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new test_versionpredicate$py("distutils/tests/test_versionpredicate$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(test_versionpredicate$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.test_suite$1(var2, var3);
         default:
            return null;
      }
   }
}
